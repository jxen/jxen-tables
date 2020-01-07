package com.github.jxen.tables.poi;

import com.github.jxen.tables.api.AbstractTableReader;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

/**
 * {@code ExcelTableReader} class provides data read feature from Excel file.
 *
 * @author Denis Murashev
 *
 * @since Tables 0.1
 */
abstract class ExcelTableReader extends AbstractTableReader {

	/**
	 * Rows iterator.
	 */
	private Iterator<Row> rows;

	/**
	 * Current row.
	 */
	private Row row;

	ExcelTableReader(Class<? extends Cell> type) {
		addConverter(type, String.class, v -> {
			Cell cell = (Cell) v;
			if (cell.getCellType() == CellType.NUMERIC) {
				return String.valueOf(cell.getNumericCellValue());
			}
			return cell.getStringCellValue();
		});
		addConverter(type, short.class, v -> ((Number) ((Cell) v).getNumericCellValue()).shortValue());
		addConverter(type, int.class, v -> ((Number) ((Cell) v).getNumericCellValue()).intValue());
		addConverter(type, long.class, v -> ((Number) ((Cell) v).getNumericCellValue()).longValue());
		addConverter(type, float.class, v -> ((Number) ((Cell) v).getNumericCellValue()).floatValue());
		addConverter(type, double.class, v -> ((Number) ((Cell) v).getNumericCellValue()).doubleValue());
		addConverter(type, boolean.class, v -> ((Cell) v).getBooleanCellValue());
		addConverter(type, LocalDate.class, v -> {
			Cell cell = (Cell) v;
			if (cell.getCellType() == CellType.NUMERIC) {
				return cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			}
			return LocalDate.parse(cell.getStringCellValue());
		});
		addConverter(type, Date.class, v -> {
			Cell cell = (Cell) v;
			if (cell.getCellType() == CellType.STRING) {
				return convert(cell.getStringCellValue(), Date.class);
			}
			return cell.getDateCellValue();
		});
	}

	@Override
	protected void nextLine() {
		row = rows.next();
	}

	@Override
	protected boolean hasNextLine() {
		return rows.hasNext();
	}

	@Override
	protected Cell nextValue(int index) {
		return row.getCell(index);
	}

	/**
	 * Sets rows data.
	 *
	 * @param rows the rows to set
	 */
	void setRows(Iterator<Row> rows) {
		this.rows = rows;
	}
}
