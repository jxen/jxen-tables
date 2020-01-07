package com.github.jxen.tables.csv;

import com.github.jxen.tables.api.AbstractTableWriter;
import com.github.jxen.tables.api.TableException;
import com.opencsv.CSVWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * {@code CsvTableWriter} class provides data read from CSV file.
 *
 * @author Denis Murashev
 *
 * @since Tables 0.1
 */
public class CsvTableWriter extends AbstractTableWriter {

	private CSVWriter writer;
	private List<String> current;

	@Override
	protected void init(OutputStream output, int index) {
		writer = new CSVWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
		current = new ArrayList<>();
	}

	@Override
	protected void nextLine() {
		writer.writeNext(current.toArray(new String[0]), false);
		current = new ArrayList<>();
	}

	@Override
	protected void writeValue(int index, Object value) {
		if (current.size() <= index) {
			for (int i = current.size(); i <= index; i++) {
				current.add("");
			}
		}
		if (Objects.nonNull(value)) {
			current.set(index, String.valueOf(value));
		}
	}

	@Override
	protected void close() {
		try {
			writer.writeNext(current.toArray(new String[0]), false);
			writer.close();
		} catch (IOException e) {
			throw new TableException("Unable to close the stream", e);
		}
	}
}
