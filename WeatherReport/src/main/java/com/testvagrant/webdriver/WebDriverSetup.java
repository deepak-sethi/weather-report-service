package com.testvagrant.webdriver;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.testvagrant.common.Constants;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverSetup {

	static Logger logger = (Logger) LogManager.getLogger(WebDriverSetup.class);
	private static WebDriverSetup instance = null;
	public WebDriver webDriver = null;

	public static WebDriverSetup getInstance() {
		if (instance == null) {
			instance = new WebDriverSetup();
		}
		return instance;
	}

	public WebDriver setUpWebDriverBasedOnBrowser() {
		try {
			System.setProperty("wdm.targetPath", System.getProperty("user.dir") + File.separator + "webdriverbinaries");
			String downloadedVersion = null;
			switch (Constants.BROWSER_NAME.toLowerCase()) {
			case Constants.BROWSER_CHROME:
				WebDriverManager.chromedriver().clearDriverCache();
				WebDriverManager.chromedriver().setup();
				downloadedVersion = WebDriverManager.chromedriver().getDownloadedDriverVersion();
				setWebDriverInstance(new ChromeDriver(setChromeOptions()));
				break;

			case Constants.BROWSER_FIREFOX:
				WebDriverManager.firefoxdriver().clearDriverCache();
				WebDriverManager.firefoxdriver().setup();
				downloadedVersion = WebDriverManager.firefoxdriver().getDownloadedDriverVersion();
				setWebDriverInstance(new FirefoxDriver());
				break;
			case Constants.BROWSER_IE:
				WebDriverManager.iedriver().clearDriverCache();
				WebDriverManager.iedriver().setup();
				downloadedVersion = WebDriverManager.iedriver().getDownloadedDriverVersion();
				setWebDriverInstance(new InternetExplorerDriver());
				break;
			default:
				throw new IllegalArgumentException("The Browser Name is Undefined");
			}

			logger.info("Webdriver Binary Downloaded Version : " + downloadedVersion);

		} catch (Exception e) {
			logger.error("Error While Initializing the Webdriver " + e);
		}
		return webDriver;
	}

	public void setWebDriverInstance(WebDriver driver) {
		this.webDriver = driver;
	}

	public ChromeOptions setChromeOptions() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		return options;
	}

}
