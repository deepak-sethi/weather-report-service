package com.testvagrant.tests.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.NoAlertPresentException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.google.gson.JsonObject;
import com.testvagrant.common.CommonMethods;
import com.testvagrant.common.Constants;
import com.testvagrant.tests.common.TestDataProvider;
import com.testvagrant.tests.common.WebDriverBaseTest;
import com.testvagrant.utils.ReadConfigJson;
import com.testvagrant.weblocators.WeatherPage;

public class WeatherPageTest extends WebDriverBaseTest {
	static Logger logger = (Logger) LogManager.getLogger(WeatherPageTest.class);

	WeatherPage obj;

	@BeforeTest(alwaysRun = true)
	public void setDriverInstance() {
		obj = new WeatherPage(driver);
	}

	@Test(groups = { "comparePrerequisites",
			"smoke" }, dataProvider = "getCityNames", dataProviderClass = TestDataProvider.class)
	public void getWeatherDataByCityName(String cityName) {
		SoftAssert assertObj = new SoftAssert();
		try {
			String url = ReadConfigJson.getWebBaseURL();
			driver.get(url);
			try {
				driver.switchTo().alert().dismiss();
				logger.info("Alert Was Present");
			} catch (NoAlertPresentException e) {
				logger.info("No Alert Present");
			}

			assertObj.assertEquals("Get Latest News, India News, Breaking News, Today's News - NDTV.com",
					driver.getTitle());

			obj.expandNavigationMenu();
			obj.navigateToWeatherPage();
			obj.isSearchTextBoxDisplayed();
			obj.searchAndSelectCityByName(cityName);
			JsonObject cityTempData = obj.validateAndCollectTempInfo(cityName);
			assertObj.assertNotNull(cityTempData, "Getting Null Object for City Data");
			String fileNameAndPath = Constants.TEST_RESOURCES_DIR + cityName + "_WEB_TempFile.json";
			CommonMethods.writeToFile(cityTempData.toString(), fileNameAndPath);
		} catch (Exception e) {
			assertObj.fail(e.getMessage());
			e.printStackTrace();
		}
		assertObj.assertAll();
	}
}
