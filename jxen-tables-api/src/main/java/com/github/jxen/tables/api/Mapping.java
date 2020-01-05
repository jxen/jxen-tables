package com.github.jxen.tables.api;

import com.github.jxen.tables.annotations.ColumnIndex;
import com.github.jxen.tables.annotations.SkipRows;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;

/**
 * {@code Mapping} class is used for mapping between Java Bean properties and columns of table.
 *
 * @author Denis Murashev
 *
 * @param <T> Java Bean type
 *
 * @since Tables 0.1
 */
public final class Mapping<T> {

	private final Class<T> type;

	private final NavigableMap<Integer, Field> fields;

	private final Map<Integer, String> names;

	private final Map<Integer, String> defaultValues;

	private final int skipRows;

	/**
	 * @param type          bean type
	 * @param fields        bean fields
	 * @param names         bean fields names
	 * @param defaultValues bean fields default values
	 * @param skipRows      skip rows
	 */
	public Mapping(Class<T> type, Map<Integer, Field> fields, Map<Integer, String> names,
			Map<Integer, String> defaultValues, int skipRows) {
		this.type = type;
		this.fields = new TreeMap<>(fields);
		this.names = names;
		this.defaultValues = defaultValues;
		this.skipRows = skipRows;
	}

	/**
	 * @param type          bean type
	 * @param fields        bean fields
	 * @param names         bean fields names
	 * @param defaultValues bean fields default values
	 */
	public Mapping(Class<T> type, Map<Integer, Field> fields, Map<Integer, String> names,
			Map<Integer, String> defaultValues) {
		this(type, fields, names, defaultValues, 0);
	}

	/**
	 * @return created bean
	 */
	public T createObject() {
		try {
			return type.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new TableException("Unable to create object", e);
		}
	}

	/**
	 * @param index field index
	 * @return field's name
	 */
	public String getName(int index) {
		return names.get(index);
	}

	/**
	 * @param object object
	 * @param index  field index
	 * @return field's value
	 */
	public Object getValue(T object, int index) {
		Field field = fields.get(index);
		if (Objects.isNull(field)) {
			return null;
		}
		return AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
			try {
				field.setAccessible(true);
				return field.get(object);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new TableException("Unable to get value", e);
			}
		});
	}

	/**
	 * @param object    object
	 * @param index     field's index
	 * @param value     value to set
	 * @param converter converter
	 */
	public void setValue(T object, int index, Object value, Converter converter) {
		Field field = fields.get(index);
		if (field != null) {
			AccessController.doPrivileged((PrivilegedAction<?>) () -> {
				try {
					field.setAccessible(true);
					Class<?> toClass = field.getType();
					if (value == null || "".equals(value.toString())) {
						String defaultValue = defaultValues.get(index);
						field.set(object, converter.convert(defaultValue, toClass));
					} else {
						field.set(object, converter.convert(value, toClass));
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new TableException("Unable to set value", e);
				}
				return null;
			});
		}
	}

	/**
	 * @return the skipRows
	 */
	public int getSkipRows() {
		return skipRows;
	}

	/**
	 * @return bean size
	 */
	public int size() {
		return Optional.ofNullable(fields.lastKey()).orElse(-1);
	}

	/**
	 * @param type type to be read
	 * @param <U> class type
	 * @return bean mapping
	 */
	public static <U> Mapping<U> fromClass(Class<U> type) {
		Map<Integer, Field> fields = new HashMap<>();
		Map<Integer, String> names = new HashMap<>();
		Map<Integer, String> defaultValues = new HashMap<>();
		for (Field field : type.getDeclaredFields()) {
			if (field.isAnnotationPresent(ColumnIndex.class)) {
				ColumnIndex annotation = field.getAnnotation(ColumnIndex.class);
				int index = annotation.index();
				fields.put(index, field);
				names.put(index, annotation.name());
				defaultValues.put(index, annotation.defaultValue());
			}
		}
		if (type.isAnnotationPresent(SkipRows.class)) {
			SkipRows skipRows = type.getAnnotation(SkipRows.class);
			return new Mapping<>(type, fields, names, defaultValues, skipRows.value());
		}
		return new Mapping<>(type, fields, names, defaultValues);
	}
}
