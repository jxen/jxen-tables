package com.github.jxen.tables.yaml;

import com.github.jxen.tables.api.Mapping;
import com.github.jxen.tables.api.TableException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.yaml.snakeyaml.Yaml;

/**
 * {@code YamlMapping} class is used for mapping between Java Bean properties and columns of table using YAML mapping.
 *
 * @author Denis Murashev
 *
 * @since Tables 0.1
 */
public final class YamlMapping {

	private YamlMapping() {
	}

	/**
	 * Reads mapping from given XML stream.
	 *
	 * @param input input stream
	 * @param <T> mapping type
	 * @return mapping read from stream
	 */
	public static <T> Mapping<T> fromYaml(InputStream input) {
		try {
			Map<String, Object> map = new Yaml().load(input);
			String className = (String) map.get("class");
			@SuppressWarnings("unchecked")
			Class<T> type = (Class<T>) Class.forName(className);
			Map<Integer, Field> fields = new HashMap<>();
			Map<Integer, String> names = new HashMap<>();
			Map<Integer, String> defaultValues = new HashMap<>();
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> columns = (List<Map<String, Object>>) map.get("columns");
			for (Map<String, Object> item : columns) {
				int index = (int) item.get("index");
				String fieldName = (String) item.get("field");
				Field field = type.getDeclaredField(fieldName);
				fields.put(index, field);
				names.put(index, Optional.ofNullable(item.get("name")).map(Object::toString).orElse(fieldName));
				Object value = item.get("default");
				if (Objects.nonNull(value)) {
					defaultValues.put(index, value.toString());
				}
			}
			Number skipRows = (Number) map.get("skipRows");
			if (Objects.nonNull(skipRows)) {
				return new Mapping<>(type, fields, names, defaultValues, skipRows.intValue());
			}
			return new Mapping<>(type, fields, names, defaultValues);
		} catch (ClassNotFoundException | SecurityException | NoSuchFieldException e) {
			throw new TableException("Unable to read mapping config", e);
		}
	}
}
