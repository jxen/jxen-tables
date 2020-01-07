package com.github.jxen.tables.api;

import java.io.OutputStream;
import java.util.List;

/**
 * {@code TableWriter} interface is a general contract for table data writer.
 *
 * @author Denis Murashev
 *
 * @since Tables 0.1
 */
public interface TableWriter {

	/**
	 * Reads data according given configuration.
	 *
	 * @param model  table model
	 * @param config configuration
	 * @param output output stream
	 * @param <T> data type
	 * @param <U> header type
	 */
	<T, U> void write(TableModel<T, U> model, TableConfig<T, U> config, OutputStream output);

	/**
	 * Reads data according given configuration.
	 *
	 * @param data    data to be written
	 * @param mapping mapping configuration
	 * @param index   table index
	 * @param output  output stream
	 * @param <T> data type
	 */
	<T> void write(List<T> data, Mapping<T> mapping, int index, OutputStream output);

	/**
	 * Reads data according given configuration.
	 *
	 * @param data    data to be written
	 * @param mapping mapping configuration
	 * @param output  output stream
	 * @param <T> data type
	 */
	default <T> void write(List<T> data, Mapping<T> mapping, OutputStream output) {
		write(data, mapping, 0, output);
	}
}
