package com.api.utils;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.poiji.bind.Poiji;

public class ExcelReaderUtil2 {

	
	private ExcelReaderUtil2() {
		
	}
	public static <T>Iterator<T> loadExcelTestData(String xlsxFile ,String sheetName, Class<T> bean){
		XSSFWorkbook workbook = null;
		XSSFSheet sheet;
		
			try {
				workbook = new XSSFWorkbook(
						Thread.currentThread().getContextClassLoader().getResourceAsStream(xlsxFile));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sheet = workbook.getSheet(sheetName);

	List<T> dataList =	 Poiji.fromExcel(sheet,bean);		 
	 
	return dataList.iterator();

}
}
