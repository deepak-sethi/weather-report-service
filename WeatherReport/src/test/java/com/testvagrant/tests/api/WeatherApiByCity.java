package com.testvagrant.tests.api;

import java.net.URLEncoder;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.testvagrant.common.CommonMethods;
import com.testvagrant.common.Constants;
import com.testvagrant.http.HttpService;
import com.testvagrant.tests.common.BaseTest;
import com.testvagrant.tests.common.TestDataProvider;
import com.testvagrant.utils.ReadConfigJson;

public class WeatherApiByCity implements BaseTest {

	public static final String CITY_ENDPOINT = "data/2.5/weather?q=cityName&appid=";

	@Test(groups = { "comparePrerequisites",
			"smoke" }, dataProvider = "getCityNames", dataProviderClass = TestDataProvider.class)
	public void getWeatherDataByCity(String cityName) throws Exception {
		SoftAssert assertObj = new SoftAssert();
		HttpResponse response = HttpService.get(ReadConfigJson.getApiBaseURL()
				+ CITY_ENDPOINT.replace("cityName", URLEncoder.encode(cityName, "UTF-8")) + Constants.API_KEY, headers);
		int apiStatusCode = response.getStatusLine().getStatusCode();
		if (apiStatusCode == 200) {
			HttpEntity entityObj = response.getEntity();
			String responseContent = EntityUtils.toString(entityObj, Charset.defaultCharset());
			JsonObject jsonObj = JsonParser.parseString(responseContent).getAsJsonObject();
			if (jsonObj.isJsonNull())
				assertObj.fail("No Weather Data found for City: " + cityName);
			String fileNameAndPath = Constants.TEST_RESOURCES_DIR + cityName + "_API_TempFile.json";
			CommonMethods.writeToFile(jsonObj.toString(), fileNameAndPath);
		} else
			assertObj.fail(
					"Invalid Response from API: " + apiStatusCode + " | " + response.getStatusLine().getReasonPhrase());

		assertObj.assertAll();
	}

}
