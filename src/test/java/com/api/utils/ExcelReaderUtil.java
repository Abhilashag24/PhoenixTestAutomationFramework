package com.api.utils;

import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReaderUtil {

	public static void main(String[] args) throws IOException {

	try (XSSFWorkbook workbook = new XSSFWorkbook(
			Thread.currentThread().getContextClassLoader().getResourceAsStream("testData/PhoenixTestData.xlsx"))) {
		XSSFSheet sheet = workbook.getSheet("LoginTestData");
		 
		XSSFRow row;
		XSSFCell cell; 
		
		int rowlength = sheet.getLastRowNum();
		System.out.println(rowlength);
		int columnLength = (sheet.getRow(1).getLastCellNum())-1;
		System.out.println(columnLength);
		
		for(int rowIndex = 0;rowIndex<=rowlength ;rowIndex++) {
			for(int colIndex=0;colIndex<=columnLength;colIndex++) {
				row = sheet.getRow(rowIndex);
				cell = row.getCell(colIndex);
				System.out.print(cell.getStringCellValue()+" "); 
				 
			}
			System.out.println();
		}
	}
	}

}
