
package com.dataproviders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.api.request.model.CreateJobPayload;
import com.api.utils.CSVReaderUtility;
import com.api.utils.CreateJobBeanMapper;
import com.dataproviders.api.bean.CreateJobBean;
import com.dataproviders.api.bean.UserBean;

public class DataProviderUtils {

	@DataProvider(name = "loginAPIDataProvider", parallel = true)
	public static Iterator<UserBean> loginAPIDataProvider() {
		// data Providers can usually return [], [][], Iterator<>

		return CSVReaderUtility.loadCSV("testData/logincreds.csv", UserBean.class);

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

}
