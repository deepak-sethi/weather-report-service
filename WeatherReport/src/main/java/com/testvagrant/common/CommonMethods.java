package com.testvagrant.common;

import java.io.FileWriter;
import java.time.Duration;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;

public class CommonMethods {
	static Logger logger = (Logger) LogManager.getLogger(CommonMethods.class);

	// Wait for element to display by user given time out
	public static void waitForElementToDisplay(WebDriver driver, By by, long timeOut, long pollingTime) {

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(NoSuchElementException.class);
		Function<WebDriver, Boolean> function = new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
				WebElement searchElement = driver.findElement(by);
				if (searchElement.isDisplayed()) {
					return true;
				}
				return false;
			}
		};
		wait.until(function);
	}

	public static void quitWebDriver(WebDriver driver) {
		driver.quit();
	}

	// Write the given content to specified file, along with the path
	public static void writeToFile(String content, String filePath) throws Exception {
		try {
			FileWriter file = new FileWriter(filePath);
			file.write(content);
			file.close();
			logger.info("Successfully File Created: " + filePath);
		} catch (Exception e) {
			logger.error("Exception While Writing to file: " + filePath);
			logger.error(ExceptionUtils.getStackTrace(e));
			throw e;
		}
	}
}
