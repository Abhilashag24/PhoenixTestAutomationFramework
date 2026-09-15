package com.demo.csv;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class ReadCSVFile {

	public static void main(String[] args) throws IOException, CsvException {
		// code to read the CSV file in Java

		InputStreamReader isr = new InputStreamReader(
				Thread.currentThread().getContextClassLoader().getResourceAsStream("testData/logincreds.csv"));
		CSVReader csvReader = new CSVReader(isr);

		List<String[]> dataList = csvReader.readAll();
		for (String[] dataArray : dataList) {
			for (String data : dataArray) {
				System.out.print(data + " ");
			}
			System.out.println();
		}

	}

}
