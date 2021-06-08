package com.testvagrant.common;

public final class Constants {

	// This will throw exception if try to create the object of this class
	public Constants() throws Exception {
		throw new Exception("Util Class, Object creation is not allowed!");
	}

	// All Fields are used as constant values
	public static final String API_KEY = "f75bfdc771223fe744c321b19d9d405d";
	public static final String BROWSER_NAME = System.getProperty("BrowserName");
	public static final String TEMP_MAGNITUDE_DIFF = System.getProperty("TempDiff");
	public static final String BROWSER_FIREFOX = "firefox";
	public static final String BROWSER_CHROME = "chrome";
	public static final String BROWSER_IE = "ie";
	public static final String TEST_RESOURCES_DIR = "src/test/resources/";
	public static final String LOG4J_FILE_DIR = "src/main/resources/";

}
