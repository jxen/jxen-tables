package com.github.jxen.tables.csv;

import com.github.jxen.tables.api.TableReader;
import com.github.jxen.tables.spi.ServiceProvider;

/**
 * {@code CsvServiceProvider} class is service provider for Open CSV.
 *
 * @author Denis Murashev
 *
 * @since Tables 0.1
 */
public class CsvServiceProvider implements ServiceProvider {

	@Override
	public TableReader getReader(String ext) {
		if ("csv".equalsIgnoreCase(ext)) {
			return new CsvTableReader();
		}
		return null;
	}
}
