package com.github.jxen.tables.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.jxen.tables.api.Mapping;
import com.github.jxen.tables.api.TableConfig;
import com.github.jxen.tables.api.TableModel;
import com.github.jxen.tables.test.SampleEntity;
import java.io.InputStream;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class CsvTableReaderTest {

	@Test
	void testRead() {
		TableConfig<SampleEntity, SampleEntity> config = new TableConfig<>(Mapping.fromClass(SampleEntity.class));
		InputStream resource = CsvTableReaderTest.class.getResourceAsStream("/test.csv");
		TableModel<SampleEntity, SampleEntity> model = new CsvTableReader().read(config, resource);
		assertEquals(new SampleEntity("12.0", 3, 3, LocalDate.parse("2020-01-01")), model.getData().get(2));
	}
}
