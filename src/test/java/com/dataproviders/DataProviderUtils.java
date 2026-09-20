
package com.dataproviders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.UserCredentials;
import com.api.utils.CSVReaderUtility;
import com.api.utils.CreateJobBeanMapper;
import com.api.utils.ExcelReaderUtil;
import com.api.utils.FakerDataGenerator;
import com.api.utils.JsonReaderUtil;
import com.dataproviders.api.bean.CreateJobBean;
import com.dataproviders.api.bean.UserBean;

public class DataProviderUtils {

	@DataProvider(name = "loginAPIDataProvider", parallel = true)
	public static Iterator<UserBean> loginAPIDataProvider() {
		// data Providers can usually return [], [][], Iterator<>

		return CSVReaderUtility.loadCSV("testData/logincreds.csv", UserBean.class);

	}

	@DataProvider(name = "loginAPIJsonDataProvider", parallel = true)
	public static Iterator<UserCredentials> loginAPIJsonsDataProvider() {
		// data Providers can usually return [], [][], Iterator<>

		return JsonReaderUtil.loadJSON("testData/loginAPIData.json", UserCredentials[].class);

	}
	
	@DataProvider(name = "loginAPIExcelDataProvider", parallel = true)
	public static Iterator<UserBean> loginAPIExcelDataProvider() {
		// data Providers can usually return [], [][], Iterator<>

		return ExcelReaderUtil.loadExcelTestData("testData/PhoenixTestData.xlsx","LoginTestData",UserBean.class);

	}

	@DataProvider(name = "CreateJobAPIDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobAPIDataProvider() {
		// data Providers can usually return [], [][], Iterator<>

		Iterator<CreateJobBean> itr = CSVReaderUtility.loadCSV("testData/CreateJobData.csv", CreateJobBean.class);

		List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();
		CreateJobBean tempBean;
		CreateJobPayload tempPayload;
		while (itr.hasNext()) {
			tempBean = itr.next();
			tempPayload = CreateJobBeanMapper.mapper(tempBean);
			payloadList.add(tempPayload);

		}
		return payloadList.iterator();
	}

	@DataProvider(name = "CreateJobAPIJsonDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobAPIJsonDataProvider() {
		// data Providers can usually return [], [][], Iterator<>

		return JsonReaderUtil.loadJSON("testData/CreateJobAPIData.json", CreateJobPayload[].class);

	}

	@DataProvider(name = "CreateJobAPIFakerDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobAPIFakerDataProvider() {
		String fakerCount = System.getProperty("fakerCount", "5");
		int fakeCount = Integer.parseInt(fakerCount);
		Iterator<CreateJobPayload> payloadIterator = FakerDataGenerator.generateFakeCreateJobData(fakeCount);
		return payloadIterator;
	}
	
	@DataProvider(name = "CreateJobAPIExcelDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobAPIExcelDataProvider() {
		// data Providers can usually return [], [][], Iterator<>

		Iterator<CreateJobBean> itr = ExcelReaderUtil.loadExcelTestData("testData/PhoenixTestData.xlsx",
				"CreateJobTestData", CreateJobBean.class);
		List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();
		CreateJobBean tempBean;
		CreateJobPayload tempPayload;
		while (itr.hasNext()) {
			tempBean = itr.next();
			tempPayload = CreateJobBeanMapper.mapper(tempBean);
			payloadList.add(tempPayload);

		}
		return payloadList.iterator();
	}

}
