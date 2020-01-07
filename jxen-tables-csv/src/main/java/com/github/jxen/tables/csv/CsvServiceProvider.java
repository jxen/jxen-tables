package com.github.jxen.tables.csv;

import com.github.jxen.tables.api.TableReader;
import com.github.jxen.tables.api.TableWriter;
import com.github.jxen.tables.spi.ServiceProvider;

/**
 * {@code CsvServiceProvider} class is service provider for Open CSV.
 *
 * @author Denis Murashev
 *
 * @since Tables 0.1
 */
public class CsvServiceProvider implements ServiceProvider {

	private static final String CSV = "csv";

	@Override
	public TableReader getReader(String ext) {
		if (CSV.equalsIgnoreCase(ext)) {
			return new CsvTableReader();
		}
		return null;
	}

	@Override
	public TableWriter getWriter(String ext) {
		if (CSV.equalsIgnoreCase(ext)) {
			return new CsvTableWriter();
		}
		return null;
	}
}
