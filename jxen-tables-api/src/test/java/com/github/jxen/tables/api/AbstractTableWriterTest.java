package com.github.jxen.tables.api;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.github.jxen.tables.test.AbstractTableWriterImpl;
import com.github.jxen.tables.test.SampleEntity;
import java.util.Collections;
import org.junit.jupiter.api.Test;

class AbstractTableWriterTest {

	@Test
	void testWriteCase1() {
		Mapping<SampleEntity> mapping = Mapping.fromClass(SampleEntity.class);
		TableConfig<SampleEntity, SampleEntity> config = new TableConfig<>(mapping, mapping);
		SampleEntity entity = new SampleEntity();
		TableModel<SampleEntity, SampleEntity> model = new TableModel<>(Collections.singletonList(entity), entity);
		assertDoesNotThrow(() -> new AbstractTableWriterImpl().write(model, config, null));
	}

	@Test
	void testWriteCase2() {
		Mapping<SampleEntity> mapping = Mapping.fromClass(SampleEntity.class);
		SampleEntity entity = new SampleEntity();
		assertDoesNotThrow(() -> new AbstractTableWriterImpl().write(Collections.singletonList(entity), mapping, null));
	}
}
