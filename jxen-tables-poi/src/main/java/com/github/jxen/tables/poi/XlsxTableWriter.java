package com.github.jxen.tables.poi;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * {@code XlsxTableWriter} class provides data write feature from Excel 2007 file.
 *
 * @author Denis Murashev
 *
 * @since Tables 0.1
 */
public class XlsxTableWriter extends ExcelTableWriter {

	@Override
	protected Workbook createWorkbook() {
		return new XSSFWorkbook();
	}
}
