package com.github.jxen.tables.api;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * {@code AbstractTableReader} class is general table data reader.
 *
 * @author Denis Murashev
 *
 * @since Tables 0.1
 */
public abstract class AbstractTableReader implements TableReader {

	private final Map<ConverterType, Function<Object, Object>> converters = new HashMap<>();

	/**
	 * Initializes with default values.
	 */
	protected AbstractTableReader() {
		addConverter(Object.class, String.class, Object::toString);
		addConverter(String.class, short.class, v -> Short.parseShort((String) v));
		addConverter(Number.class, short.class, v -> ((Number) v).shortValue());
		addConverter(String.class, int.class, v -> Integer.parseInt((String) v));
		addConverter(Number.class, int.class, v -> ((Number) v).intValue());
		addConverter(String.class, long.class, v -> Long.parseLong((String) v));
		addConverter(Number.class, long.class, v -> ((Number) v).longValue());
		addConverter(String.class, float.class, v -> Float.parseFloat((String) v));
		addConverter(Number.class, float.class, v -> ((Number) v).floatValue());
		addConverter(String.class, double.class, v -> Double.parseDouble((String) v));
		addConverter(Number.class, double.class, v -> ((Number) v).doubleValue());
		addConverter(String.class, LocalDate.class, v -> LocalDate.parse((String) v));
	}

	@Override
	public <T, U> TableModel<T, U> read(TableConfig<T, U> config, InputStream input) {
		init(input, config.getIndex());
		U header = readHeader(config.getHeader());
		List<T> data = readData(config.getData());
		return new TableModel<>(data, header);
	}

	@Override
	public <T> List<T> read(Mapping<T> mapping, int index, InputStream input) {
		init(input, index);
		return readData(mapping);
	}

	private <U> U readHeader(Mapping<U> mapping) {
		if (Objects.isNull(mapping)) {
			return null;
		}
		for (int i = 0; i < mapping.getSkipRows(); i++) {
			nextLine();
		}
		U header = mapping.createObject();
		for (int i = 0; i <= mapping.size(); i++) {
			nextLine();
			mapping.setValue(header, i, nextValue(1), this);
		}
		return header;
	}

	private <T> List<T> readData(Mapping<T> mapping) {
		List<T> table = new ArrayList<>();
		int skipRows = mapping.getSkipRows();
		for (int i = 0; i < skipRows; i++) {
			nextLine();
		}
		while (hasNextLine()) {
			nextLine();
			T row = mapping.createObject();
			for (int i = 0; i <= mapping.size(); i++) {
				mapping.setValue(row, i, nextValue(i), this);
			}
			table.add(row);
		}
		return table;
	}

	@Override
	public Object convert(Object value, Class<?> toClass) {
		Class<?> fromClass = value.getClass();
		if (fromClass.equals(toClass)) {
			return value;
		}
		ConverterType t = new ConverterType(fromClass, toClass);
		Function<Object, Object> converter = converters.get(t);
		if (converter != null) {
			return converter.apply(value);
		}
		Class<?> superClass = fromClass.getSuperclass();
		while (!Object.class.equals(superClass)) {
			t = new ConverterType(superClass, toClass);
			converter = converters.get(t);
			if (converter != null) {
				return converter.apply(value);
			}
			superClass = superClass.getSuperclass();
		}
		for (Class<?> i : fromClass.getInterfaces()) {
			t = new ConverterType(i, toClass);
			converter = converters.get(t);
			if (converter != null) {
				return converter.apply(value);
			}
		}
		throw new TableException("Cannot conver value from " + fromClass + " to " + toClass);
	}

	@Override
	public final void addConverter(Class<?> fromClass, Class<?> toClass, Function<Object, Object> converter) {
		converters.put(new ConverterType(fromClass, toClass), converter);
	}

	/**
	 * Data reader initialization.
	 *
	 * @param input input stream
	 * @param index table index
	 */
	protected abstract void init(InputStream input, int index);

	/**
	 * Moves reader to next line of data.
	 */
	protected abstract void nextLine();

	/**
	 * @return <code>true</code> if next line of data can be read
	 */
	protected abstract boolean hasNextLine();

	/**
	 * Reads next value.
	 *
	 * @param index value index
	 * @return next value
	 */
	protected abstract Object nextValue(int index);

	private static final class ConverterType {

		private final Class<?> fromClass;
		private final Class<?> toClass;

		private ConverterType(Class<?> fromClass, Class<?> toClass) {
			this.fromClass = fromClass;
			this.toClass = toClass;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = prime + fromClass.hashCode();
			return prime * result + toClass.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			ConverterType other = (ConverterType) obj;
			return fromClass.equals(other.fromClass) && toClass.equals(other.toClass);
		}
	}
}
