package com.github.jxen.tables.csv;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.github.jxen.tables.api.TableException;
import com.github.jxen.tables.api.TableFactory;
import javafx.scene.control.Tab;
import org.junit.jupiter.api.Test;

class TableFactoryTest {

	@Test
	void testTxtReader() {
		assertThrows(TableException.class, () -> TableFactory.getReader("txt"));
	}

	@Test
	void testCsvReader() {
		TableFactory.getReader("csv");
		assertNotNull(TableFactory.getReader("csv"));
	}

	@Test
	void testTxtWriter() {
		assertThrows(TableException.class, () -> TableFactory.getWriter("txt"));
	}

	@Test
	void testCsvWriter() {
		TableFactory.getWriter("csv");
		assertNotNull(TableFactory.getWriter("csv"));
	}
}
