package com.testvagrant.tests.common;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;

import com.testvagrant.common.Constants;
import com.testvagrant.utils.ReadConfigJson;

public interface BaseTest {

	public static HashMap<String, String> headers = null;

	@BeforeTest(alwaysRun = true)
	public default void setupEnvironment(@Optional("QA") String env,
			@Optional("src/main/resources/Config.json") String filePath) {
		ReadConfigJson.setupEnvironmentProperties(env, filePath);
	}

	@AfterSuite(alwaysRun = true)
	public default void clearTestDir() throws IOException {
		FileUtils.cleanDirectory(new File(Constants.TEST_RESOURCES_DIR));
	}
}
