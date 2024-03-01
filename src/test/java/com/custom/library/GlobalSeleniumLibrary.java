package com.custom.library;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;
import com.google.common.io.Files;

/***
 * 
 * @author Musabay Technologies This class is used all the selenium related
 *         web-elements and make it wrapper methods
 */
public class GlobalSeleniumLibrary {
	final static Logger logger = Logger.getLogger(GlobalSeleniumLibrary.class);

	private WebDriver driver;
	public boolean isDemoMode = false;
	public boolean isRemote = false;
	public List<String> errorScreenshots;

	/***
	 * This is the constructor method
	 * 
	 * @param _driver
	 */
	public GlobalSeleniumLibrary(WebDriver _driver) {
		driver = _driver;
	}

	/***
	 * This method starts Chrome Browser
	 * 
	 * @return driver which is instance of Chrome Browser
	 */
	public WebDriver startChromeBrowser() {
		try {
			System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
			//System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver-01062018.exe");
			driver = new ChromeDriver(); // open Chrome browser
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			driver.manage().window().maximize();
		} catch (Exception e) {
			logger.error("Error- ", e);
			assertTrue(false);
		}
		return driver;
	}

	@SuppressWarnings("deprecation")
	public WebDriver startIEBrowser() {
		try {
			System.setProperty("webdriver.ie.driver", "src/test/resources/IEDriverServer.exe");
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
			driver = new InternetExplorerDriver(cap);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			driver.manage().window().maximize();
		} catch (Exception e) {
			logger.error("Error- ", e);
			assertTrue(false);
		}
		return driver;
	}

	public WebDriver startFirefoxBrowser() {
		try {
			System.setProperty("webdriver.gecko.driver", "src/test/resources/geckodriver.exe");
			driver = new FirefoxDriver();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			driver.manage().window().maximize();
		} catch (Exception e) {
			logger.error("Error- ", e);
			assertTrue(false);
		}
		return driver;
	}

	/***
	 * This method handles the checkbox & radio - buttons
	 * 
	 * @param by
	 * @param isCheck
	 * @throws Exception
	 */
	public void handleCheckBox(By by, boolean isCheck) throws Exception {
		WebElement element = driver.findElement(by);
		boolean checkBoxState = element.isSelected();
		if (isCheck == true) // user wanted to select the check-box
		{
			if (checkBoxState == true) // if box is checked by default
			{
				// do nothing
			} else // box is Not checked by default
			{
				element.click();
			}
		} else { // user Do not want to select the check- box
			if (checkBoxState == true) {
				element.click();
			} else {
				// Do nothing
			}
		}
		Thread.sleep(500);
	}

	/***
	 * This method stopping java thread by given seconds
	 * 
	 * @param inSeconds
	 * @throws Exception
	 */
	public void customWait(double inSeconds) throws Exception {
		Thread.sleep((long) (inSeconds * 1000));
	}

	/***
	 * This method selects drop-down elements using visible text option
	 * 
	 * @param by
	 * @param userOptionValue
	 * @throws Exception
	 */
	public void selectDropDown(By by, String userOptionValue) throws Exception {
		Select dropdown = new Select(driver.findElement(by));
		dropdown.selectByVisibleText(userOptionValue);
		System.out.println("Selecting '" + userOptionValue + "'");
		customWait(2);
	}

	/***
	 * This method selects drop-down elements using visible text option
	 * 
	 * @param element
	 * @param userInputValue
	 */
	public void enterTextField(WebElement element, String userInputValue) {
		element.clear(); // clear the text box and delete the default value
		element.sendKeys(userInputValue);
		System.out.println("entering '" + userInputValue + "' to text-field");
	}

	/***
	 * This method enters given input to the text fields
	 * 
	 * @param by
	 * @param userInputValue
	 */
	public void enterTextField(By by, String userInputValue) {
		WebElement element = driver.findElement(by);
		element.clear(); // clear the text box and delete the default value
		element.sendKeys(userInputValue);
		System.out.println("entering '" + userInputValue + "' to text-field");
	}

	/***
	 * This method explicitly waits for an element until presence or timeout
	 * after given seconds
	 * 
	 * @param by
	 * @return WebElement if found it else return null
	 */
	public WebElement dynamicWait_presenceOfElementLocated(By by) {
		WebElement myDynamicElement = (new WebDriverWait(driver, 60))
				.until(ExpectedConditions.presenceOfElementLocated(by));
		return myDynamicElement;
	}

	/***
	 * This method captures screenshot and saves it to resources folder path
	 * 
	 * @param screenshotFileName
	 * @param filePath
	 * @return full path to the screenshot file taken
	 * @throws IOException
	 */
	public String captureScreenshot(String screenshotFileName, String filePath) throws IOException {
		String screenCaptureFile = null;
		try {
			if(isRemote == true)
			{
				driver = new Augmenter().augment(driver);
			}
			String tempTime = getCurrentTime();
			File tempFile = new File(filePath);
			if (!tempFile.isDirectory()) {
				tempFile.mkdir(); // create the folder
			}
			if (!filePath.isEmpty()) {
				screenCaptureFile = filePath + screenshotFileName + tempTime + ".png";
			} else {
				screenCaptureFile = "src/test/resources/screenshots/" + screenshotFileName + tempTime + ".png";
			}
			File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			Files.copy(srcFile, new File(screenCaptureFile));
			File absFile = new File(screenCaptureFile);
			String absPath = absFile.getAbsolutePath();
			System.out.println("Screenshot Path: " + absPath);
		} catch (Exception e) {
			logger.error("Error- ", e);
			assertTrue(false);
		}
		return screenCaptureFile;
	}

	/***
	 * This method gets the current time
	 * 
	 * @return String of current time-stamp with underscores
	 */
	public String getCurrentTime() {
		Date date = new Date();
		String tempTime = new Timestamp(date.getTime()).toString();
		String finalTimeStamp = tempTime.replace(":", "_").replace("-", "_").replace(" ", "_").replace(".", "_");
		// System.out.println("TempTime: " + tempTime);
		// System.out.println("FinalTime: " + finalTimeStamp);
		return finalTimeStamp;
	}

	public String getCurrentTimeStamp() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
		String dateToStr = format.format(date).replace("-", "").replace(" ", "").replace(":", "");
		System.out.println(dateToStr);

		return dateToStr;
	}

	public WebDriver startLocalBrowser(String browser) {

		switch (browser) {
		case "IE":
			driver = startIEBrowser();
			System.out.println("Starting 'IE' browser !");
			break;
		case "Chrome":
			driver = startChromeBrowser();
			System.out.println("Starting 'Chrome' browser !");
			break;
		case "Firefox":
			driver = startFirefoxBrowser();
			System.out.println("Starting 'Firefox' browser !");
			break;
		default:
			driver = startChromeBrowser();
			System.out.println("User selected browser: " + browser + ", Starting default browser - Chrome!");
		}
		return driver;
	}

	public WebDriver startRemoteBrowser(String hubURL, String browserName) {
		//DesiredCapabilities cap = new DesiredCapabilities();
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		DesiredCapabilities cap = null;
		try {
			if (browserName.contains("Chrome")) {
				 cap = DesiredCapabilities.chrome();
			} else if (browserName.contains("IE")) {
				//DesiredCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				cap = DesiredCapabilities.internetExplorer();
			}
			driver = new RemoteWebDriver(new URL(hubURL), cap);
			isRemote = true;
		} catch (Exception e) {
			logger.error("Error- ", e);
			assertTrue(false);
		}
		return driver;
	}

	public void highlightElement(WebElement element) throws Exception {

		if (isDemoMode == true) {
			for (int i = 0; i < 4; i++) {
				WrapsDriver wrappedElement = (WrapsDriver) element;
				JavascriptExecutor js = (JavascriptExecutor) wrappedElement.getWrappedDriver();
				customWait(0.5);
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
						"color: red; border: 2px solid yellow;");
				customWait(0.5);
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
			}
		}
	}

	public void highlightElement(By by) throws Exception {

		WebElement element = driver.findElement(by);
		if (isDemoMode == true) {
			for (int i = 0; i < 4; i++) {
				WrapsDriver wrappedElement = (WrapsDriver) element;
				JavascriptExecutor js = (JavascriptExecutor) wrappedElement.getWrappedDriver();
				customWait(0.5);
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
						"color: red; border: 2px solid yellow;");
				customWait(0.5);
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
			}
		}

	}

	/***
	 * This method clicks any given invisible web-element using java-script
	 * executor
	 * 
	 * @param by
	 */
	public void clickInvisibleWebElement(By by) {
		try {
			WebElement invisibleElem = driver.findElement(by);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", invisibleElem);
		} catch (Exception e) {
			logger.error("Error- ", e);
			assertTrue(false);
		}
	}

	/***
	 * This method clicks any given invisible web-element using java-script
	 * executor
	 * 
	 * @param by
	 */
	public void clickInvisibleWebElement(WebElement element) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", element);
		} catch (Exception e) {
			logger.error("Error- ", e);
			assertTrue(false);
		}
	}

	public WebElement fluentWait(By by) {
		WebElement targetElem = null;
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(60, TimeUnit.SECONDS)
				.pollingEvery(3, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
		targetElem = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(by);
			}
		});
		return targetElem;
	}

	public WebElement waitUntilPageLoadComplete(By by) {
		WebElement element = fluentWait(by);
		return element;
	}

	public void switchToIframe(By by) {
		WebElement iframeElem = driver.findElement(by);
		driver.switchTo().frame(iframeElem);
	}

	public void switchToDefault() {
		driver.switchTo().defaultContent();
	}

	public void moveMouseToElement(WebElement toElement) {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(toElement).build().perform();
		} catch (Exception e) {
			logger.error("Error- ", e);
			assertTrue(false);
		}
	}

	public void moveMouseToElement(WebElement firstElem, WebElement secondElem) {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(firstElem).build().perform();
			customWait(1);
			action.moveToElement(secondElem).build().perform();
			customWait(1);
		} catch (Exception e) {
			logger.error("Error- ", e);
			assertTrue(false);
		}
	}

	public void scrollToElement(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
	}

	public void scrollByOffset(String verticalPixel) {
		((JavascriptExecutor) driver).executeScript("scroll(0," + verticalPixel + ")");
	}

	public WebDriver switchWindows(String browserUrl) {
		try {
			Set<String> windows = driver.getWindowHandles();
			for (String window : windows) {
				customWait(1);
				driver.switchTo().window(window);
				String currentUrl = driver.getCurrentUrl();
				if (browserUrl.contains(currentUrl)) {
					break;
				}
			}
		} catch (Exception e) {
			logger.error("Error- ", e);
			assertTrue(false);
		}
		return driver;
	}
	
	public void fileUpload(By by, String filePath)
	{
		try{
			WebElement fileUploadElem = driver.findElement(by);
			File file = new File(filePath);
			String fullPath = file.getAbsolutePath();
			if(isRemote == true)
			{
				((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
			}
			fileUploadElem.sendKeys(fullPath);
		} catch (Exception e) {
			logger.error("Error- Remote file upload failed: ", e);
			assertTrue(false);
		}
	}
	
	public List<String> automaticallyAttachErrorImgToEmail() {
		List<String> fileNames = new ArrayList<>();
		JavaPropertiesManager propertyReader = new JavaPropertiesManager("src/test/resources/dynamicConfig.properties");
		String tempTimeStamp = propertyReader.readProperty("sessionTime");
		String numberTimeStamp = tempTimeStamp.replaceAll("_", "");
		long testStartTime = Long.parseLong(numberTimeStamp);

		// first check if error-screenshot folder has file
		File file = new File("target/images");
		if (file.isDirectory()) {
			if (file.list().length > 0) {
				File[] screenshotFiles = file.listFiles();
				for (int i = 0; i < screenshotFiles.length; i++) {
					// checking if file is a file, not a folder
					if (screenshotFiles[i].isFile()) {
						String eachFileName = screenshotFiles[i].getName();
						logger.info("Testing file names: " + eachFileName);
						int indexOf20 = seachStringInString("20", eachFileName);
						String timeStampFromScreenshotFile = eachFileName.substring(indexOf20,
								eachFileName.length() - 4);
						logger.info("Testing file timestamp: " + timeStampFromScreenshotFile);
						String fileNumberStamp = timeStampFromScreenshotFile.replaceAll("_", "");
						long screenshotfileTime = Long.parseLong(fileNumberStamp);

						testStartTime = Long.parseLong(numberTimeStamp.substring(0, 14));
						screenshotfileTime = Long.parseLong(fileNumberStamp.substring(0, 14));
						if (screenshotfileTime > testStartTime) {
							fileNames.add("target/images/" + eachFileName);
						}
					}
				}

			}
		}
		errorScreenshots = fileNames;
		return fileNames;
	}

	public int seachStringInString(String target, String message) {
		int targetIndex = 0;
		for (int i = -1; (i = message.indexOf(target, i + 1)) != -1;) {
			targetIndex = i;
			break;
		}
		return targetIndex;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	/*
	 * if (browser.contains("ie") || browser.contains("Firefox") ||
	 * browser.contains("Chrome")) { System.out.println("Starting " + browser +
	 * " browser !"); }else { System.out.println("User selected broser: "+
	 * browser+", Starting default browser - Chrome!"); }
	 */

}
