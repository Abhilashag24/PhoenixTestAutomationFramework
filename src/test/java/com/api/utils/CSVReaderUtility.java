package com.api.utils;

import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import io.qameta.allure.Step;

public class CSVReaderUtility {

	private static final Logger LOGGER = LogManager.getLogger(CSVReaderUtility.class);

	
	private CSVReaderUtility() {

	}

	@Step("Loading test data from the CSV File")
	public static <T> Iterator<T> loadCSV(String pathOfCSVFile,Class<T> bean) {
		LOGGER.info("Loading the csv file from the path {}",pathOfCSVFile);
		
		CSVReader csvReader = new CSVReader(new InputStreamReader(
				Thread.currentThread().getContextClassLoader().getResourceAsStream(pathOfCSVFile)));

		LOGGER.info("Converting the CSV to the bean class {}",bean);
		
		
		CsvToBean<T> csvToBean = new CsvToBeanBuilder(csvReader)
				.withIgnoreEmptyLine(true)
				.withType(bean)
				.build();
		
		List<T> list = csvToBean.parse();
		
	return list.iterator();

	}

}
