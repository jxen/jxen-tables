package com.github.jxen.tables.api;

import com.github.jxen.tables.spi.ServiceProvider;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ServiceLoader;

/**
 * {@code TableFactory} class provides common interfaces implementations.
 *
 * @author Denis Murashev
 *
 * @since Tables 0.1
 */
public final class TableFactory {

	private static final Map<String, TableReader> READER_CACHE = new HashMap<>();

	private TableFactory() {
	}

	/**
	 * @param ext file extension
	 * @return {@link TableReader} implementation
	 */
	public static TableReader getReader(String ext) {
		if (READER_CACHE.containsKey(ext)) {
			return READER_CACHE.get(ext);
		}
		for (ServiceProvider serviceProvider : ServiceLoader.load(ServiceProvider.class)) {
			TableReader reader = serviceProvider.getReader(ext);
			if (Objects.nonNull(reader)) {
				READER_CACHE.put(ext, reader);
				return reader;
			}
		}
		throw new TableException("File type is not supported: " + ext);
	}
}
