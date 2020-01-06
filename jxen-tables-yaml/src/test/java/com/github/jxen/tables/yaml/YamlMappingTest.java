package com.github.jxen.tables.yaml;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.github.jxen.tables.api.Mapping;
import com.github.jxen.tables.test.SampleEntity;
import org.junit.jupiter.api.Test;

class YamlMappingTest {

	@Test
	void testFromYaml() {
		Mapping<SampleEntity> actual = YamlMapping
				.fromYaml(YamlMappingTest.class.getResourceAsStream("/SampleEntity.map.yaml"));
		assertNotNull(actual);
	}
}
