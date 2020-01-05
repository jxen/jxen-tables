package com.github.jxen.tables.csv;

import com.github.jxen.tables.api.AbstractTableReader;
import com.github.jxen.tables.api.TableException;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * {@code CsvTableReader} class provides data read from CSV file.
 *
 * @author Denis Murashev
 *
 * @since Tables 0.1
 */
public class CsvTableReader extends AbstractTableReader {

	private CSVReader reader;

	private String[] currentRow = new String[0];
	private String[] nextRow;

	@Override
	protected void init(InputStream input, int index) {
		try {
			reader = new CSVReader(new InputStreamReader(input, StandardCharsets.UTF_8));
			nextRow = reader.readNext();
		} catch (IOException | CsvValidationException e) {
			throw new TableException("Unable to read input", e);
		}
	}

	@Override
	protected void nextLine() {
		try {
			currentRow = nextRow;
			nextRow = reader.readNext();
		} catch (IOException | CsvValidationException e) {
			throw new TableException("Unable to move to next line", e);
		}
	}

	@Override
	protected boolean hasNextLine() {
		return nextRow != null;
	}

	@Override
	protected Object nextValue(int index) {
		if (index < currentRow.length) {
			return currentRow[index];
		}
		return null;
	}
}
