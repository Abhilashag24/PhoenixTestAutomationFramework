package com.demo.csv;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;

public class ReadCSVFile_MapToPOJO {

	public static void main(String[] args) throws IOException, CsvException {
		// code to read the CSV file in Java

		InputStreamReader isr = new InputStreamReader(
				Thread.currentThread().getContextClassLoader().getResourceAsStream("testData/logincreds.csv"));
		CSVReader csvReader = new CSVReader(isr);

		CsvToBean<UserPOJO> csvToBean = new CsvToBeanBuilder(csvReader)
				.withType(UserPOJO.class)
				.withIgnoreEmptyLine(true)
				.build();
		
		
		List<UserPOJO> userList = csvToBean.parse();
		
		System.out.println(userList.get(1).toString());
	}

}
