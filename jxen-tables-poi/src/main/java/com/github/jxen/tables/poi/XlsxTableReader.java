package com.github.jxen.tables.poi;

import com.github.jxen.tables.api.TableException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * {@code XlsxTableReader} class provides data read feature from Excel 2007 file.
 *
 * @author Denis Murashev
 *
 * @since Tables 0.1
 */
public class XlsxTableReader extends ExcelTableReader {

	/**
	 * Initializes with appropriate cell type.
	 */
	public XlsxTableReader() {
		super(XSSFCell.class);
	}

	@Override
	protected void init(InputStream input, int index) {
		try {
			XSSFWorkbook workBook = new XSSFWorkbook(OPCPackage.open(input));
			Sheet sheet = workBook.getSheetAt(index);
			setRows(sheet.rowIterator());
		} catch (IOException | InvalidFormatException e) {
			throw new TableException("Unable to read input stream", e);
		}
	}
}
