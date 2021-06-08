package com.testvagrant.tests.common;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;

import com.testvagrant.webdriver.WebDriverSetup;

public class WebDriverBaseTest implements BaseTest {

	protected WebDriver driver = null;

	@BeforeTest(alwaysRun = true)
	public void setupDriver() {
		driver = WebDriverSetup.getInstance().setUpWebDriverBasedOnBrowser();
	}

	@AfterSuite(alwaysRun = true)
	public void quitDriver() {
		driver.quit();
	}

}
