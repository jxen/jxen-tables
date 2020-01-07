package com.github.jxen.tables.poi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * {@code XlsTableWriter} class provides data write feature from old Excel 2003 file.
 *
 * @author Denis Murashev
 *
 * @since Tables 0.1
 */
public class XlsTableWriter extends ExcelTableWriter {

	@Override
	protected Workbook createWorkbook() {
		return new HSSFWorkbook();
	}
}
