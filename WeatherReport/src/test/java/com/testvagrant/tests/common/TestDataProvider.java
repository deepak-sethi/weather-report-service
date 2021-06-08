package com.testvagrant.tests.common;

import org.testng.annotations.DataProvider;

public class TestDataProvider {

	// This will be the data source for the given test cases
	@DataProvider
	public Object[] getCityNames() {
		Object[] cityNames = { "New Delhi", "Bengaluru" };
		return cityNames;
	}

}
