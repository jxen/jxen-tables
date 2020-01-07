package com.github.jxen.tables.api;

import java.io.OutputStream;
import java.util.List;
import java.util.Objects;

/**
 * {@code AbstractTableWriter} class is general table data reader.
 *
 * @author Denis Murashev
 *
 * @since Tables 0.1
 */
public abstract class AbstractTableWriter implements TableWriter {

	@Override
	public <T, U> void write(TableModel<T, U> model, TableConfig<T, U> config, OutputStream output) {
		init(output, config.getIndex());
		writeHeader(model.getHeader(), config.getHeader());
		writeData(model.getData(), config.getData());
		close();
	}

	@Override
	public <T> void write(List<T> data, Mapping<T> mapping, int index, OutputStream output) {
		init(output, index);
		writeData(data, mapping);
		close();
	}

	private <U> void writeHeader(U header, Mapping<U> mapping) {
		if (Objects.isNull(header) || Objects.isNull(mapping)) {
			return;
		}
		for (int i = 0; i < mapping.getSkipRows(); i++) {
			nextLine();
		}
		for (int i = 0; i <= mapping.size(); i++) {
			writeValue(1, mapping.getValue(header, i));
			nextLine();
		}
	}

	private <T> void writeData(List<T> data, Mapping<T> mapping) {
		int skipRows = mapping.getSkipRows();
		for (int i = 0; i < skipRows; i++) {
			nextLine();
		}
		for (T item : data) {
			for (int i = 0; i <= mapping.size(); i++) {
				writeValue(i, mapping.getValue(item, i));
			}
			nextLine();
		}
	}

	/**
	 * Data writer initialization.
	 *
	 * @param output output stream
	 * @param index  table index
	 */
	protected abstract void init(OutputStream output, int index);

	/**
	 * Moves writer to next line of data.
	 */
	protected abstract void nextLine();

	/**
	 * Writes next value.
	 *
	 * @param index column index
	 * @param value value to be written
	 */
	protected abstract void writeValue(int index, Object value);

	/**
	 * Finishes operations and closes output stream.
	 */
	protected abstract void close();
}
