package com.github.jxen.tables.test;

import com.github.jxen.tables.api.AbstractTableWriter;
import java.io.OutputStream;

/**
 * {@code AbstractTableWriterImpl} class is test implementation of {@link AbstractTableWriter} for unit tests.
 *
 * @author Denis Murashev
 *
 * @since Tables 0.1
 */
public class AbstractTableWriterImpl extends AbstractTableWriter {

	@Override
	protected void init(OutputStream output, int index) {
	}

	@Override
	protected void nextLine() {
	}

	@Override
	protected void writeValue(int index, Object value) {
	}

	@Override
	protected void close() {
	}
}
