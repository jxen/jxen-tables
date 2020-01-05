package com.github.jxen.tables.api;

/**
 * {@code Converter} interface describes converting the value to the class.
 *
 * @author Denis Murashev
 *
 * @since Tables 0.1
 */
public interface Converter {

	/**
	 * Provides desired converter.
	 *
	 * @param value   value to be converted
	 * @param toClass to class
	 * @return converted value
	 */
	Object convert(Object value, Class<?> toClass);
}
