package com.dataproviders;

import java.util.Iterator;

import org.testng.annotations.DataProvider;

import com.api.utils.CSVReaderUtility;
import com.dataproviders.api.bean.UserBean;

public class DataProviderUtils {

	@DataProvider(name = "loginAPIDataProvider",parallel = true)
	public static Iterator<UserBean> loginAPIDataProvider() {
		// data Providers can usually return [], [][], Iterator<>

		return CSVReaderUtility.loadCSV("testData/logincreds.csv");

	}

}
