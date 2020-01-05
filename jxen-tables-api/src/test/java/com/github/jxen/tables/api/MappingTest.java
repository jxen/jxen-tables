package com.github.jxen.tables.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.github.jxen.tables.test.AbstractTableReaderImpl;
import com.github.jxen.tables.test.SampleEntity;
import org.junit.jupiter.api.Test;

class MappingTest {

	private final Mapping<SampleEntity> mapping = Mapping.fromClass(SampleEntity.class);
	private final Converter converter = new AbstractTableReaderImpl(new String[][]{});

	@Test
	void testCreateObject() {
		assertNotNull(mapping.createObject());
	}

	@Test
	void testCreateObjectFailure() {
		assertThrows(TableException.class, () -> Mapping.fromClass(Integer.class).createObject());
	}

	@Test
	void testGetName() {
		assertEquals("text", mapping.getName(0));
	}

	@Test
	void testGetValue() {
		SampleEntity entity = new SampleEntity();
		entity.setNumber(1);
		assertEquals(1, mapping.getValue(entity,1));
	}

	@Test
	void testGetValueNull() {
		SampleEntity entity = new SampleEntity();
		assertNull(mapping.getValue(entity,-1));
	}

	@Test
	void testSetValueCase1() {
		SampleEntity entity = new SampleEntity();
		mapping.setValue(entity, 2, 1.0, converter);
		assertEquals(1.0, entity.getValue());
	}

	@Test
	void testSetValueCase2() {
		SampleEntity entity = new SampleEntity();
		mapping.setValue(entity, -2, 1.0, converter);
		assertEquals(0.0, entity.getValue());
	}

	@Test
	void testSetValueCase3() {
		SampleEntity entity = new SampleEntity();
		mapping.setValue(entity, 2, null, converter);
		assertEquals(1.0, entity.getValue());
	}

	@Test
	void testSetValueCase4() {
		SampleEntity entity = new SampleEntity();
		mapping.setValue(entity, 2, "", converter);
		assertEquals(1.0, entity.getValue());
	}

	@Test
	void testSetValueCase5() {
		SampleEntity entity = new SampleEntity();
		assertThrows(TableException.class, () ->  mapping.setValue(entity, 2, "x", converter));
	}
}
