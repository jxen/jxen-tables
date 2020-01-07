package com.github.jxen.tables.poi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.github.jxen.tables.api.Mapping;
import com.github.jxen.tables.api.TableConfig;
import com.github.jxen.tables.api.TableException;
import com.github.jxen.tables.api.TableModel;
import com.github.jxen.tables.test.SampleEntity;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

class ExcelTableReaderTest {

	@Test
	void testReadXls() {
		TableConfig<SampleEntity, SampleEntity> config = new TableConfig<>(Mapping.fromClass(SampleEntity.class));
		InputStream resource = ExcelTableReaderTest.class.getResourceAsStream("/test.xls");
		TableModel<SampleEntity, SampleEntity> model = new XlsTableReader().read(config, resource);
		SampleEntity expected = new SampleEntity("12.0", 3, 3, LocalDate.parse("2020-01-01"), false,
				LocalDateTime.parse("2020-01-01T00:00"));
		assertEquals(expected, model.getData().get(2));
	}

	@Test
	void testReadXlsx() {
		TableConfig<SampleEntity, SampleEntity> config = new TableConfig<>(Mapping.fromClass(SampleEntity.class));
		InputStream resource = ExcelTableReaderTest.class.getResourceAsStream("/test.xlsx");
		TableModel<SampleEntity, SampleEntity> model = new XlsxTableReader().read(config, resource);
		SampleEntity expected = new SampleEntity("12.0", 3, 3, LocalDate.parse("2020-01-01"), false,
				LocalDateTime.parse("2020-01-01T00:00"));
		assertEquals(expected, model.getData().get(2));
	}

	@Test
	void testConvertDateCase1() {
		try {
			InputStream resource = ExcelTableReaderTest.class.getResourceAsStream("/test.xls");
			POIFSFileSystem fileSystem = new POIFSFileSystem(resource);
			HSSFWorkbook workBook = new HSSFWorkbook(fileSystem);
			Sheet sheet = workBook.getSheetAt(0);
			Cell cell = sheet.getRow(0).getCell(4);
			Date date = (Date) new XlsTableReader().convert(cell, Date.class);
			assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-01"), date);
		} catch (ParseException | IOException e) {
			throw new TableException("Unexpected error", e);
		}
	}

	@Test
	void testConvertDateCase2() {
		try {
			InputStream resource = ExcelTableReaderTest.class.getResourceAsStream("/test.xlsx");
			XSSFWorkbook workBook = new XSSFWorkbook(OPCPackage.open(resource));
			Sheet sheet = workBook.getSheetAt(0);
			Cell cell = sheet.getRow(2).getCell(4);
			assertThrows(TableException.class, () -> new XlsxTableReader().convert(cell, Date.class));
		} catch (IOException | InvalidFormatException e) {
			throw new TableException("Unexpected error", e);
		}
	}
}
