package com.github.jxen.tables.poi;

import com.github.jxen.tables.api.TableReader;
import com.github.jxen.tables.spi.ServiceProvider;

/**
 * {@code PoiServiceProvider} class is service provider for Apache POI.
 *
 * @author Denis Murashev
 *
 * @since Tables 0.1
 */
public class PoiServiceProvider implements ServiceProvider {

	@Override
	public TableReader getReader(String ext) {
		if ("xls".equalsIgnoreCase(ext)) {
			return new XlsTableReader();
		}
		if ("xlsx".equalsIgnoreCase(ext)) {
			return new XlsxTableReader();
		}
		return null;
	}
}
