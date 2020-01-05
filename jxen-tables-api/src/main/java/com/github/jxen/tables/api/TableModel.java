package com.github.jxen.tables.api;

import java.util.List;

/**
 * {@code TableModel} contains model of table to be read or written.
 *
 * @author Denis Murashev
 *
 * @param <T> Java Bean class
 * @param <U> table header class
 *
 * @since Tables 0.1
 */
public class TableModel<T, U> {

	private final List<T> data;
	private final U header;

	/**
	 * @param data   table data
	 * @param header table header
	 */
	public TableModel(List<T> data, U header) {
		this.data = data;
		this.header = header;
	}

	/**
	 * @return the data
	 */
	public List<T> getData() {
		return data;
	}

	/**
	 * @return the header
	 */
	public U getHeader() {
		return header;
	}
}
