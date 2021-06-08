package com.testvagrant.utils;

import java.io.FileReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.testvagrant.common.Constants;

public class ReadConfigJson {

	private static String apiBaseUrl;
	private static String webBaseUrl;
	static Logger logger = (Logger) LogManager.getLogger(ReadConfigJson.class);

	//This will setup the Environment i.e. Required properties which will be used by Test Methods
	public static void setupEnvironmentProperties(String environment, String filePath) {
		try {
			System.setProperty("log4j.configurationFile", Constants.LOG4J_FILE_DIR);
			FileReader fileReaderObj = new FileReader(filePath);
			JsonObject obj = JsonParser.parseReader(fileReaderObj).getAsJsonObject();
			JsonObject parentArray = obj.getAsJsonObject(environment);
			String apiBaseUrl = parentArray.get("API_BASE_URL").getAsString();
			String webBaseUrl = parentArray.get("WEB_BASE_URL").getAsString();
			logger.info("API Server Url is : " + apiBaseUrl);
			logger.info("Web Server Url is : " + webBaseUrl);
			setApiBaseURL(apiBaseUrl);
			setWebBaseURL(webBaseUrl);
		} catch (Exception e) {
			logger.error("Error While Setting Up Environment: " + e);
		}
	}

	public static String getApiBaseURL() {
		return apiBaseUrl;
	}

	private static void setApiBaseURL(String url) {
		ReadConfigJson.apiBaseUrl = url;
	}

	public static String getWebBaseURL() {
		return webBaseUrl;
	}

	private static void setWebBaseURL(String url) {
		ReadConfigJson.webBaseUrl = url;
	}
}
