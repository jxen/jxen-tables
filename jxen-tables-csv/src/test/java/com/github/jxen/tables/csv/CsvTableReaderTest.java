package com.github.jxen.tables.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.jxen.tables.api.Mapping;
import com.github.jxen.tables.api.TableConfig;
import com.github.jxen.tables.api.TableModel;
import com.github.jxen.tables.test.SampleEntity;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class CsvTableReaderTest {

	@Test
	void testRead() {
		TableConfig<SampleEntity, SampleEntity> config = new TableConfig<>(Mapping.fromClass(SampleEntity.class));
		InputStream resource = CsvTableReaderTest.class.getResourceAsStream("/test.csv");
		TableModel<SampleEntity, SampleEntity> model = new CsvTableReader().read(config, resource);
		SampleEntity expected = new SampleEntity("12.0", 3, 3, LocalDate.parse("2020-01-01"), false,
				LocalDateTime.parse("2020-01-01T10:00"));
		assertEquals(expected, model.getData().get(2));
	}
}
