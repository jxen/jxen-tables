package com.github.jxen.tables.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.github.jxen.tables.test.AbstractTableReaderImpl;
import com.github.jxen.tables.test.SampleEntity;
import com.github.jxen.tables.xml.XmlMapping;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

class AbstractTableReaderTest {

	@Test
	void testReadCase1() {
		String[][] data = {
				{"A", "1", "1.0", "", "2020-01-01"},
				{"B", "2", "2.0", "", "2020-01-02"},
				{"C", "3", "3.0", "", "2020-01-03"},
		};
		TableConfig<SampleEntity, SampleEntity> config = new TableConfig<>(Mapping.fromClass(SampleEntity.class));
		TableModel<SampleEntity, SampleEntity> model = new AbstractTableReaderImpl(data).read(config, null);
		assertEquals(new SampleEntity("C", 3, 3.0, LocalDate.parse("2020-01-03")), model.getData().get(2));
	}

	@Test
	void testReadCase2() {
		String[][] data = {
				{"name", "Aa"},
				{"number", "10"},
				{"value", "11.0"},
				{"", ""},
				{"date", "2020-02-02"},
				{},
				{"A", "1", "1.0", "", "2020-01-01"},
				{"B", "2", "2.0", "", "2020-01-02"},
				{"C", "3", "3.0", "", "2020-01-03"},
		};
		Mapping<SampleEntity> mapping = XmlMapping
				.fromXml(AbstractTableReaderTest.class.getResourceAsStream("/SampleEntity.map.xml"));
		Mapping<SampleEntity> headerMapping = Mapping.fromClass(SampleEntity.class);
		TableConfig<SampleEntity, SampleEntity> config = new TableConfig<>(mapping, headerMapping);
		TableModel<SampleEntity, SampleEntity> model = new AbstractTableReaderImpl(data).read(config, null);
		assertEquals(new SampleEntity("Aa", 10, 11.0, LocalDate.parse("2020-02-02")), model.getHeader());
		assertEquals(new SampleEntity("C", 3, 3.0, LocalDate.parse("2020-01-03")), model.getData().get(2));
	}

	@Test
	void testReadCase3() {
		String[][] data = {
				{"A", "1", "1.0", "", "2020-01-01"},
				{"B", "2", "2.0", "", "2020-01-02"},
				{"C", "3", "3.0", "", "2020-01-03"},
		};
		List<SampleEntity> list = new AbstractTableReaderImpl(data).read(Mapping.fromClass(SampleEntity.class), null);
		assertEquals(new SampleEntity("C", 3, 3.0, LocalDate.parse("2020-01-03")), list.get(2));
	}

	@Test
	void testConvertPrimitiveCase1() {
		short actual = (short) new AbstractTableReaderImpl(new String[][]{}).convert("1", short.class);
		assertEquals((short) 1, actual);
	}

	@Test
	void testConvertPrimitiveCase2() {
		short actual = (short) new AbstractTableReaderImpl(new String[][]{}).convert(1.0, short.class);
		assertEquals((short) 1, actual);
	}

	@Test
	void testConvertPrimitiveCase3() {
		int actual = (int) new AbstractTableReaderImpl(new String[][]{}).convert("1", int.class);
		assertEquals(1, actual);
	}

	@Test
	void testConvertPrimitiveCase4() {
		int actual = (int) new AbstractTableReaderImpl(new String[][]{}).convert(1.0, int.class);
		assertEquals(1, actual);
	}

	@Test
	void testConvertPrimitiveCase5() {
		long actual = (long) new AbstractTableReaderImpl(new String[][]{}).convert("1", long.class);
		assertEquals(1L, actual);
	}

	@Test
	void testConvertPrimitiveCase6() {
		long actual = (long) new AbstractTableReaderImpl(new String[][]{}).convert(1.0, long.class);
		assertEquals(1L, actual);
	}

	@Test
	void testConvertPrimitiveCase7() {
		float actual = (float) new AbstractTableReaderImpl(new String[][]{}).convert("1", float.class);
		assertEquals(1F, actual);
	}

	@Test
	void testConvertPrimitiveCase8() {
		float actual = (float) new AbstractTableReaderImpl(new String[][]{}).convert(1.0, float.class);
		assertEquals(1F, actual);
	}

	@Test
	void testConvertCase1() {
		AbstractTableReaderImpl converter = new AbstractTableReaderImpl(new String[][]{});
		converter.addConverter(Serializable.class, String.class, Object::toString);
		String actual = (String) converter.convert(LocalDate.parse("2020-01-01"), String.class);
		assertEquals("2020-01-01", actual);
	}

	@Test
	void testConvertCase2() {
		AbstractTableReaderImpl converter = new AbstractTableReaderImpl(new String[][]{});
		assertThrows(TableException.class, () -> converter.convert(BigDecimal.TEN, String.class));
	}
}
