package com.github.jxen.tables.api;

/**
 * {@code TableConfig} class is used for mapping between Java Bean properties and columns of table.
 *
 * @author Denis Murashev
 *
 * @param <T> Java Bean type
 * @param <U> Table header type
 *
 * @since Tables 0.1
 */
public class TableConfig<T, U> {

	private final Mapping<T> data;
	private final Mapping<U> header;
	private final int index;

	/**
	 * @param data   table data mapping
	 * @param header table header mapping
	 * @param index  table index
	 */
	public TableConfig(Mapping<T> data, Mapping<U> header, int index) {
		this.data = data;
		this.header = header;
		this.index = index;
	}

	/**
	 * @param data   table data mapping
	 * @param header table header mapping
	 */
	public TableConfig(Mapping<T> data, Mapping<U> header) {
		this(data, header, 0);
	}

	/**
	 * @param data  table data mapping
	 * @param index table index
	 */
	public TableConfig(Mapping<T> data, int index) {
		this(data, null, index);
	}

	/**
	 * @param data  table data mapping
	 */
	public TableConfig(Mapping<T> data) {
		this(data, null);
	}

	/**
	 * @return the data
	 */
	public Mapping<T> getData() {
		return data;
	}

	/**
	 * @return the header
	 */
	public Mapping<U> getHeader() {
		return header;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}
}
