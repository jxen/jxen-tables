package com.github.jxen.tables.poi;

import com.github.jxen.tables.api.AbstractTableWriter;
import com.github.jxen.tables.api.TableException;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * {@code ExcelTableWriter} class provides data write feature from Excel file.
 *
 * @author Denis Murashev
 *
 * @since Tables 0.1
 */
public abstract class ExcelTableWriter extends AbstractTableWriter {

	private OutputStream output;
	private Workbook workbook;
	private Sheet sheet;
	private Row row;
	private int rowIndex;

	@Override
	protected void init(OutputStream output, int index) {
		this.output = output;
		workbook = createWorkbook();
		sheet = workbook.createSheet();
		row = sheet.createRow(rowIndex);
	}

	@Override
	protected void nextLine() {
		rowIndex++;
		row = sheet.createRow(rowIndex);
	}

	@Override
	protected void writeValue(int index, Object value) {
		row.createCell(index);
		if (Objects.isNull(value)) {
			return;
		}
		if (value instanceof Number) {
			row.getCell(index).setCellValue(((Number) value).doubleValue());
		} else if (value instanceof Boolean) {
			row.getCell(index).setCellValue((Boolean) value);
		} else if (value instanceof Date) {
			row.getCell(index).setCellValue((Date) value);
			row.getCell(index).setCellStyle(getDateStyle());
		} else if (value instanceof Calendar) {
			row.getCell(index).setCellValue((Calendar) value);
			row.getCell(index).setCellStyle(getDateStyle());
		} else if (value instanceof LocalDate) {
			row.getCell(index).setCellValue((LocalDate) value);
			row.getCell(index).setCellStyle(getDateStyle());
		} else if (value instanceof LocalDateTime) {
			row.getCell(index).setCellValue((LocalDateTime) value);
			row.getCell(index).setCellStyle(getDateStyle());
		} else {
			row.getCell(index).setCellValue(String.valueOf(value));
		}
	}

	private CellStyle getDateStyle() {
		CellStyle cellStyle = workbook.createCellStyle();
		final int fmt = 14;
		cellStyle.setDataFormat((short) fmt);
		return cellStyle;
	}

	@Override
	protected void close() {
		try {
			workbook.write(output);
			workbook.close();
		} catch (IOException e) {
			throw new TableException("Unable to close file", e);
		}
	}

	/**
	 * @return new created workbook
	 */
	protected abstract Workbook createWorkbook();
}
