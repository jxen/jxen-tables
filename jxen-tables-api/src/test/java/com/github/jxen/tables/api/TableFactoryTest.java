package com.github.jxen.tables.api;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class TableFactoryTest {

	@Test
	void testFailure() {
		assertThrows(TableException.class, () -> TableFactory.getReader("unsupported"));
	}

	@Test
	void testSuccess() {
		TableFactory.getReader("test");
		assertNotNull(TableFactory.getReader("test"));
	}
}
