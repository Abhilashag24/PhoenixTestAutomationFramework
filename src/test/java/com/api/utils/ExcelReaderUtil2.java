package com.api.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.api.request.model.UserCredentials;

public class ExcelReaderUtil2 {

	
	private ExcelReaderUtil2() {
		
	}
	public static Iterator<UserCredentials> loadExcelTestData(String filePath){

		try (XSSFWorkbook workbook = new XSSFWorkbook(
				Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath))) {
			XSSFSheet sheet = workbook.getSheet("LoginTestData");

			XSSFCell mycell;

			// Objective : Read the Excel file and Store data from excel in
			// ArrayList<UserCredentails>

			// 1. Know the indexes of username and password in our sheet
			XSSFRow headerRows = sheet.getRow(0);

			int userNameIndex = -1;
			int passwordIndex = -1;

			for (Cell cell : headerRows) {
				if (cell.getStringCellValue().trim().equalsIgnoreCase("username")) {
					userNameIndex = cell.getColumnIndex();
				}
				if (cell.getStringCellValue().trim().equalsIgnoreCase("password")) {
					passwordIndex = cell.getColumnIndex();
				}
			}

			XSSFRow myRow;
			UserCredentials userCredentials ;
			ArrayList<UserCredentials> userList=new ArrayList<UserCredentials>();
			for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				myRow = sheet.getRow(rowIndex);

				userCredentials = new UserCredentials(myRow.getCell(userNameIndex).getStringCellValue(),
						myRow.getCell(passwordIndex).getStringCellValue());
				
				userList.add(userCredentials);

			}

return userList.iterator();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
