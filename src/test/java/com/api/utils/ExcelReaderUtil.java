package com.api.utils;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.poiji.bind.Poiji;

public class ExcelReaderUtil {

	private static final Logger LOGGER = LogManager.getLogger(ExcelReaderUtil.class);

	private ExcelReaderUtil() {

	}

	public static <T> Iterator<T> loadExcelTestData(String xlsxFile, String sheetName, Class<T> bean) {
		
		LOGGER.info("Reading the test data from xlsx file {} and the sheet name is ",xlsxFile,sheetName);
		XSSFWorkbook workbook = null;
		XSSFSheet sheet;

		try {
			workbook = new XSSFWorkbook(Thread.currentThread().getContextClassLoader().getResourceAsStream(xlsxFile));
		} catch (IOException e) {
		
			e.printStackTrace();
			LOGGER.error("Cannot read the xlsx file {}",xlsxFile,e);
		}
		sheet = workbook.getSheet(sheetName);

		LOGGER.info("Coverting the xlsx sheet {} to a POJO class of type {} ",sheetName, bean);
	
		List<T> dataList = Poiji.fromExcel(sheet, bean);

		return dataList.iterator();

	}
}
