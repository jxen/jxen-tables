package com.github.jxen.tables.poi;

import com.github.jxen.tables.api.TableReader;
import com.github.jxen.tables.api.TableWriter;
import com.github.jxen.tables.spi.ServiceProvider;

/**
 * {@code PoiServiceProvider} class is service provider for Apache POI.
 *
 * @author Denis Murashev
 *
 * @since Tables 0.1
 */
public class PoiServiceProvider implements ServiceProvider {

	private static final String XLS = "xls";
	private static final String XLSX = "xlsx";

	@Override
	public TableReader getReader(String ext) {
		if (XLS.equalsIgnoreCase(ext)) {
			return new XlsTableReader();
		}
		if (XLSX.equalsIgnoreCase(ext)) {
			return new XlsxTableReader();
		}
		return null;
	}

	@Override
	public TableWriter getWriter(String ext) {
		if (XLS.equalsIgnoreCase(ext)) {
			return new XlsTableWriter();
		}
		if (XLSX.equalsIgnoreCase(ext)) {
			return new XlsxTableWriter();
		}
		return null;
	}
}
