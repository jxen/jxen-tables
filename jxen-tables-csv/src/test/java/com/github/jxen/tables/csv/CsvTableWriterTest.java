package com.github.jxen.tables.csv;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.github.jxen.tables.api.Mapping;
import com.github.jxen.tables.api.TableConfig;
import com.github.jxen.tables.api.TableModel;
import com.github.jxen.tables.test.SampleEntity;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.Collections;
import org.junit.jupiter.api.Test;

class CsvTableWriterTest {

	@Test
	void testWrite() {
		TableConfig<SampleEntity, SampleEntity> config = new TableConfig<>(Mapping.fromClass(SampleEntity.class));
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		SampleEntity entity = new SampleEntity("a", 1, 1.0, LocalDate.parse("2020-01-01"), false, null);
		TableModel<SampleEntity, SampleEntity> model = new TableModel<>(Collections.singletonList(entity), entity);
		new CsvTableWriter().write(model, config, output);
		assertNotEquals(0, output.size());
	}
}
