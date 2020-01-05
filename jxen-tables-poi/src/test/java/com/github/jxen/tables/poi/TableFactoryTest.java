package com.github.jxen.tables.poi;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.github.jxen.tables.api.TableException;
import com.github.jxen.tables.api.TableFactory;
import org.junit.jupiter.api.Test;

class TableFactoryTest {

	@Test
	void testCsv() {
		assertThrows(TableException.class, () -> TableFactory.getReader("csv"));
	}

	@Test
	void testXls() {
		TableFactory.getReader("xls");
		assertNotNull(TableFactory.getReader("xls"));
	}

	@Test
	void testXlsx() {
		TableFactory.getReader("xlsx");
		assertNotNull(TableFactory.getReader("xlsx"));
	}
}
