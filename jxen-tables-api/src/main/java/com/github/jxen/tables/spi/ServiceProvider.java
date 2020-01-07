package com.github.jxen.tables.spi;

import com.github.jxen.tables.api.TableReader;
import com.github.jxen.tables.api.TableWriter;

/**
 * {@code ServiceProvider} interface is common extension point for Tables API.
 *
 * @author Denis Murashev
 *
 * @since Tables 0.1
 */
public interface ServiceProvider {

	/**
	 * @param ext file extension
	 * @return {@link TableReader} implementation if {@code ext} supported or null otherwise
	 */
	TableReader getReader(String ext);

	/**
	 * @param ext file extension
	 * @return {@link TableWriter} implementation if {@code ext} supported or null otherwise
	 */
	TableWriter getWriter(String ext);
}
