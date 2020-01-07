package com.github.jxen.tables.api;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class TableFactoryTest {

	@Test
	void testReaderFailure() {
		assertThrows(TableException.class, () -> TableFactory.getReader("unsupported"));
	}

	@Test
	void testReaderSuccess() {
		TableFactory.getReader("test");
		assertNotNull(TableFactory.getReader("test"));
	}

	@Test
	void testWriterFailure() {
		assertThrows(TableException.class, () -> TableFactory.getWriter("unsupported"));
	}

	@Test
	void testWriterSuccess() {
		TableFactory.getWriter("test");
		assertNotNull(TableFactory.getWriter("test"));
	}
}
