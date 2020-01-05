package com.github.jxen.tables.api;

import java.io.InputStream;
import java.util.List;
import java.util.function.Function;

/**
 * {@code TableReader} interface is a general contract for table data reader.
 *
 * @author Denis Murashev
 *
 * @since Tables 0.1
 */
public interface TableReader extends Converter {

	/**
	 * Reads data according given configuration.
	 *
	 * @param config configuration
	 * @param input  input stream
	 * @param <T> data type
	 * @param <U> header type
	 * @return model read from table input stream
	 */
	<T, U> TableModel<T, U> read(TableConfig<T, U> config, InputStream input);

	/**
	 * Reads data according given configuration.
	 *
	 * @param mapping mapping configuration
	 * @param index   table index
	 * @param input  input stream
	 * @param <T> data type
	 * @return model read from table input stream
	 */
	<T> List<T> read(Mapping<T> mapping, int index, InputStream input);

	/**
	 * Reads data according given configuration.
	 *
	 * @param mapping mapping configuration
	 * @param input  input stream
	 * @param <T> data type
	 * @return model read from table input stream
	 */
	default <T> List<T> read(Mapping<T> mapping, InputStream input) {
		return read(mapping, 0, input);
	}

	/**
	 * Adds support for converting values from {@code fromClass} to {@code toClass}.
	 *
	 * @param fromClass from class
	 * @param toClass   to class
	 * @param converter converter
	 */
	void addConverter(Class<?> fromClass, Class<?> toClass, Function<Object, Object> converter);
}
