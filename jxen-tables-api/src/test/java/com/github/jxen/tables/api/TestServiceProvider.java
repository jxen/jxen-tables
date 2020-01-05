package com.github.jxen.tables.api;

import com.github.jxen.tables.spi.ServiceProvider;
import com.github.jxen.tables.test.AbstractTableReaderImpl;

public class TestServiceProvider implements ServiceProvider {

	@Override
	public TableReader getReader(String ext) {
		return "test".equalsIgnoreCase(ext) ? new AbstractTableReaderImpl(new String[][]{}) : null;
	}
}
