package com.testvagrant.weblocators;

import static com.testvagrant.common.CommonMethods.waitForElementToDisplay;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.google.gson.JsonObject;

public class WeatherPage {

	WebDriver driver;
	String navigateToWeatherXpath = "//a[text()='WEATHER']";
	String expandNavMenuByClass = "topnavmore";
	String searchBoxByClass = "searchBox";
	String identifyEnteredCityXpath = "//input[@id='cityName']";
	String clickOnSearchCityTempInfoXpath = "//div[@class='cityText'][text()='cityName']";
	String validateCorrectCityPaletXpath = "//div//span[contains(text(),'cityName')]";
	String cityDataByXpath = "//div[@class='leaflet-popup-content']//span";

	public WeatherPage(WebDriver driver) {
		this.driver = driver;
	}

	public void navigateToWeatherPage() {
		driver.findElement(By.xpath(navigateToWeatherXpath)).click();
	}

	public void expandNavigationMenu() {
		driver.findElement(By.className(expandNavMenuByClass)).click();
	}

	public void isSearchTextBoxDisplayed() {
		waitForElementToDisplay(driver, By.className(searchBoxByClass), 30, 5);
	}

	public void searchAndSelectCityByName(String cityName) {
		driver.findElement(By.className(searchBoxByClass)).sendKeys(cityName);
		waitForElementToDisplay(driver, By.xpath(identifyEnteredCityXpath.replace("cityName", cityName)), 30, 10);
		WebElement enteredCity = driver.findElement(By.xpath(identifyEnteredCityXpath.replace("cityName", cityName)));
		if (!enteredCity.isSelected()) {
			enteredCity.click();
		}
		driver.findElement(By.xpath(clickOnSearchCityTempInfoXpath.replace("cityName", cityName))).click();
	}

	public JsonObject validateAndCollectTempInfo(String cityName) throws Exception {
		WebElement searchedCity = driver
				.findElement(By.xpath(validateCorrectCityPaletXpath.replace("cityName", cityName)));
		if (searchedCity.isDisplayed()) {
			List<WebElement> webElementList = driver.findElements(By.xpath(cityDataByXpath));
			if (!webElementList.isEmpty()) {
				JsonObject cityJson = new JsonObject();
				for (WebElement element : webElementList) {
					if (!element.getText().isEmpty()) {
						if (element.getText().contains(cityName))
							cityJson.addProperty("City", element.getText().trim());
						else {
							String[] data = element.getText().split(":");
							if (!data[0].trim().isEmpty() && !data[1].trim().isEmpty())
								cityJson.addProperty(data[0].trim(), data[1].trim());
							else
								throw new NullPointerException(
										"Value is not getting displayed for " + element.getText());
						}
					}
				}
				return cityJson;
			} else
				throw new Exception("No Weather Data Found for Searched City: " + cityName);
		} else
			throw new NoSuchElementException(
					"Either Searched City is not displayed Or Wrong City Name is getting displayed");
	}
}
