package com.api.utils;

import java.io.InputStreamReader;
import java.util.List;

import com.dataproviders.api.bean.UserBean;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CSVReaderUtility {

	private CSVReaderUtility() {

	}

	public static void loadCSV() {
		CSVReader csvReader = new CSVReader(new InputStreamReader(
				Thread.currentThread().getContextClassLoader().getResourceAsStream("testData/logincreads.csv")));

		CsvToBean<UserBean> csvToBean = new CsvToBeanBuilder(csvReader)
				.withIgnoreEmptyLine(true)
				.withType(UserBean.class)
				.build();
		
		List<UserBean> userList = csvToBean.parse();
		
		System.out.println(userList);

	}

}
