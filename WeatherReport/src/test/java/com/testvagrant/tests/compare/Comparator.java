package com.testvagrant.tests.compare;

import java.io.File;
import java.io.IOException;

import org.apache.commons.math3.util.Precision;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.reporters.Files;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.testvagrant.common.Constants;
import com.testvagrant.tests.common.TestDataProvider;

public class Comparator {

	final float conversion = (float) 273.15;

	@Test(groups = { "smoke" }, dependsOnGroups = {
			"comparePrerequisites" }, dataProvider = "getCityNames", dataProviderClass = TestDataProvider.class)

	public void tempCompareUiAndAPI(String cityName) throws IOException {
		SoftAssert assertObj = new SoftAssert();
		File testDir = new File(Constants.TEST_RESOURCES_DIR);
		if (testDir.listFiles().length > 0) {
			float apiTempInKelvin = 0;
			float uiTempInCelsius = 0;
			float uiTempInFahrenheit = 0;
			File[] files = testDir.listFiles((dir1, name) -> name.startsWith(cityName) && name.endsWith(".json"));
			for (File file : files) {
				if (file.toString().contains("API")) {
					JsonObject parentObj = JsonParser.parseString(Files.readFile(file)).getAsJsonObject();
					apiTempInKelvin = parentObj.getAsJsonObject("main").get("temp").getAsFloat();
				} else if (file.toString().contains("Web")) {
					JsonObject parentObj = JsonParser.parseString(Files.readFile(file)).getAsJsonObject();
					uiTempInCelsius = parentObj.get("Temp in Degrees").getAsFloat();
					uiTempInFahrenheit = parentObj.get("Temp in Fahrenheit").getAsFloat();
					float uiTempInFahrenheitToCelsius = Precision.round((uiTempInFahrenheit - 32) * 5 / 9, 0);
					if (uiTempInFahrenheitToCelsius != uiTempInCelsius)
						assertObj.fail("Degree Celsius & Fahrenheit Tempreture are not Techinically Same for City: " + cityName);
				}
			}
			float apiValueInCelsius = (apiTempInKelvin - conversion);
			apiValueInCelsius = Precision.round(apiValueInCelsius, 0);
			uiTempInCelsius = Precision.round(uiTempInCelsius, 0);
			uiTempInFahrenheit = Precision.round(uiTempInFahrenheit, 0);
			int tempDiff = (int) Math.abs(apiValueInCelsius - uiTempInCelsius);
			if (tempDiff > Integer.parseInt(Constants.TEMP_MAGNITUDE_DIFF)) // Temp_Diff is taken from user input using the system.getproperty method, with this can change value without checking the code
				assertObj.fail("Tempreture Difference is more 2 degree Celsius for City: " + cityName);

		} else
			assertObj.fail("Directory Is Empty, No Comparison Possible");
	}

}
