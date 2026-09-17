package com.api.utils;

import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CSVReaderUtility {

	private CSVReaderUtility() {

	}

	public static <T> Iterator<T> loadCSV(String pathOfCSVFile,Class<T> bean) {
		CSVReader csvReader = new CSVReader(new InputStreamReader(
				Thread.currentThread().getContextClassLoader().getResourceAsStream(pathOfCSVFile)));

		CsvToBean<T> csvToBean = new CsvToBeanBuilder(csvReader)
				.withIgnoreEmptyLine(true)
				.withType(bean)
				.build();
		
		List<T> list = csvToBean.parse();
		
	return list.iterator();

	}

}
