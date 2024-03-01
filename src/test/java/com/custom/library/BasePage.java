package com.custom.library;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class BasePage {
	final static Logger logger = Logger.getLogger(BasePage.class);

	public static WebDriver driver;
	public static GlobalSeleniumLibrary myLib;
	private static JavaPropertiesManager configProperty;
	private static JavaPropertiesManager property2;
	private static String browser;
	private static String isAutoEmail;
	private static boolean isRemoteRun = false;

	@BeforeMethod
	public void beforeEachTestStart() throws Exception {

		if (isRemoteRun == true) {
			driver = myLib.startRemoteBrowser("http://192.168.1.161:4857/wd/hub/", browser);
		} else {
			driver = myLib.startLocalBrowser(browser);
		}

	}

	@AfterMethod
	public void afterEachTestComplete(ITestResult result) {
		try {
			if (ITestResult.FAILURE == result.getStatus()) {
				myLib.captureScreenshot(result.getName(), "target/images/");
			}
			Thread.sleep(5 * 1000); // stops the test for 5 seconds
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver.close(); // close the browser
			// driver.quit(); // kill the driver process
		}
	}

	@AfterClass
	public void afterAllTestsComplete() {
		if (driver != null) // if there is any open browser left, close it.
		{
			// driver.close();
			driver.quit();
		}
		// Sending all the reports, screenshots, and log files via email
		List<String> screenshots = new ArrayList<>();
		EmailManager emailSender = new EmailManager();
		emailSender.attachmentFiles.add("extent.html");
		emailSender.attachmentFiles.add("target/logs/log4j-selenium.log");
		screenshots = myLib.automaticallyAttachErrorImgToEmail();
		if (screenshots.size() != 0) {
			for (String file : screenshots) {
				emailSender.attachmentFiles.add(file);
			}
		}
		if (isAutoEmail.contains("true")) {
			emailSender.sendEmail(screenshots);
		}
	}

	@BeforeClass
	public void beforeAllTestStart() {

		configProperty = new JavaPropertiesManager("src/test/resources/config.properties");
		browser = configProperty.readProperty("browserType");
		isAutoEmail = configProperty.readProperty("isAutoSendEmail");
		myLib = new GlobalSeleniumLibrary(driver);
		property2 = new JavaPropertiesManager("src/test/resources/dynamicConfig.properties");
		property2.setProperty("sessionTime", myLib.getCurrentTime());

		if (configProperty.readProperty("demoMode").contains("true")) {
			myLib.isDemoMode = true;
			System.out.println("Test is running Demo mode = ON ...");
		} else {
			System.out.println("Test is running Demo mode = OFF ...");
		}
		if (configProperty.readProperty("isRemoteTest").contains("true")) {
			myLib.isRemote = true;
			isRemoteRun = true;
			System.out.println("Test is running remote mode = ON ...");
		} else {
			System.out.println("Test is running local mode = OFF ...");
		}

	}

}
