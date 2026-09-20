package com.api.utils;

import java.util.Iterator;

import com.api.request.model.CreateJobPayload;
import com.dataproviders.api.bean.CreateJobBean;

public class ExcelReaderUtil3 {

	public static void main(String[] args) {
		Iterator<CreateJobBean> itr = ExcelReaderUtil2.loadExcelTestData("testData/PhoenixTestData.xlsx",
				"CreateJobTestData", CreateJobBean.class);
		
		while (itr.hasNext()) {

			CreateJobBean bean = itr.next();
			CreateJobPayload createJobPayload = CreateJobBeanMapper.mapper(bean);
			System.out.println(createJobPayload);
		}
		
	}
}
