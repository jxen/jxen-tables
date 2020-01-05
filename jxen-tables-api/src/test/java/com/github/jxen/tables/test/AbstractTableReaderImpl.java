package com.github.jxen.tables.test;

import com.github.jxen.tables.api.AbstractTableReader;
import java.io.InputStream;

/**
 * {@code AbstractTableReaderImpl} class is test implementation of {@link AbstractTableReader} for unit tests.
 *
 * @author Denis Murashev
 *
 * @since Tables 0.1
 */
public class AbstractTableReaderImpl extends AbstractTableReader {

	private final String[][] data;
	private int row = -1;

	/**
	 * @param data data to be provided by reader
	 */
	public AbstractTableReaderImpl(String[][] data) {
		this.data = data;
	}

	@Override
	protected void init(InputStream input, int index) {
	}

	@Override
	protected void nextLine() {
		row++;
	}

	@Override
	protected boolean hasNextLine() {
		return row < data.length - 1;
	}

	@Override
	protected Object nextValue(int index) {
		return data[row][index];
	}
}
