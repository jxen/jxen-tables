package com.github.jxen.tables.poi;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.github.jxen.tables.api.Mapping;
import com.github.jxen.tables.api.TableConfig;
import com.github.jxen.tables.api.TableFactory;
import com.github.jxen.tables.api.TableModel;
import com.github.jxen.tables.test.SampleEntity;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ExcelTableWriterTest {

	@ParameterizedTest
	@ValueSource(strings = {"xls", "xlsx"})
	void testWriteXls(String type) {
		TableConfig<SampleEntity, SampleEntity> config = new TableConfig<>(Mapping.fromClass(SampleEntity.class));
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		SampleEntity entity = new SampleEntity("a", 1, 1.0, LocalDate.parse("2020-01-01"), false,
				LocalDateTime.parse("2020-01-01T00:00:00"));
		TableModel<SampleEntity, SampleEntity> model = new TableModel<>(Collections.singletonList(entity), entity);
		TableFactory.getWriter(type).write(model, config, output);
		assertNotEquals(0, output.size());
	}
}
