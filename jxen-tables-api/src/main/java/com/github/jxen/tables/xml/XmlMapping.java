package com.github.jxen.tables.xml;

import com.github.jxen.tables.api.Mapping;
import com.github.jxen.tables.api.TableException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * {@code XmlMapping} class is used for mapping between Java Bean properties and columns of table using XML mapping.
 *
 * @author Denis Murashev
 *
 * @since Tables 0.1
 */
public final class XmlMapping {

	private XmlMapping() {
	}

	/**
	 * Reads mapping from given XML stream.
	 *
	 * @param input input stream
	 * @param <T> mapping type
	 * @return mapping read from stream
	 */
	public static <T> Mapping<T> fromXml(InputStream input) {
		try {
			Document document = XmlUtil.open(input);
			String className = XmlUtil.getText(document, "/table/@class");
			@SuppressWarnings("unchecked")
			Class<T> type = (Class<T>) Class.forName(className);
			Map<Integer, Field> fields = new HashMap<>();
			Map<Integer, String> names = new HashMap<>();
			Map<Integer, String> defaultValues = new HashMap<>();
			Element[] columns = XmlUtil.getElements(document, "/table/column");
			for (Element c : columns) {
				int index = XmlUtil.getNumber(c, "@index").intValue();
				String fieldName = XmlUtil.getText(c, "@field");
				Field field = type.getDeclaredField(fieldName);
				fields.put(index, field);
				names.put(index, Optional.ofNullable(XmlUtil.getText(c, "@name")).orElse(fieldName));
				String value = XmlUtil.getText(c, "@default");
				if (Objects.nonNull(value)) {
					defaultValues.put(index, value);
				}
			}
			String skipRowsPath = "/table/@skipRows";
			if (XmlUtil.exists(document, skipRowsPath)) {
				int skipRows = XmlUtil.getNumber(document, skipRowsPath).intValue();
				return new Mapping<>(type, fields, names, defaultValues, skipRows);
			}
			return new Mapping<>(type, fields, names, defaultValues);
		} catch (ClassNotFoundException | SecurityException | NoSuchFieldException e) {
			throw new TableException("Unable to read mapping config", e);
		}
	}
}
