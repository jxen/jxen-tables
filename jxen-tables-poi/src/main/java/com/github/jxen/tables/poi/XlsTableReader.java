package com.github.jxen.tables.poi;

import com.github.jxen.tables.api.TableException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * {@code XlsTableReader} class provides data read feature from old Excel 2003 file.
 *
 * @author Denis Murashev
 *
 * @since Tables 0.1
 */
public class XlsTableReader extends ExcelTableReader {

	/**
	 * Initializes with appropriate cell type.
	 */
	public XlsTableReader() {
		super(HSSFCell.class);
	}

	@Override
	protected void init(InputStream input, int index) {
		try {
			POIFSFileSystem fileSystem = new POIFSFileSystem(input);
			HSSFWorkbook workBook = new HSSFWorkbook(fileSystem);
			Sheet sheet = workBook.getSheetAt(index);
			setRows(sheet.rowIterator());
		} catch (IOException e) {
			throw new TableException("Unable to read input stream", e);
		}
	}
}
