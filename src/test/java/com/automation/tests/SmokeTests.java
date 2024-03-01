package com.automation.tests;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.custom.library.BasePage;

public class SmokeTests extends BasePage {

	@Test
	public void searchAndBuyACourse() throws Exception {
		driver.get("https://www.thegreatcourses.com/");
		String actualWebsiteTitle = driver.getTitle();
		String expectedTitle = "Online Courses & Lectures for Home Study and Lifelong Learning";
		System.out.println("actual title: " + actualWebsiteTitle);
		assertEquals(actualWebsiteTitle, expectedTitle);

		// locating navigation next icon
		WebElement navElem = driver.findElement(By.cssSelector(
				"div.itemslider-wrapper:nth-child(4) > div:nth-child(1) > ul:nth-child(2) > li:nth-child(2) > a:nth-child(1)"));
		for (int i = 1; i <= 3; i++) {
			navElem.click();
			myLib.customWait(0.5);
		}
		// select the science category
		WebElement scienceElem = driver
				.findElement(By.cssSelector("li.category-slide:nth-child(12) > a:nth-child(1) > img:nth-child(1)"));
		myLib.clickInvisibleWebElement(scienceElem);

		// Synchronize the page load
		WebElement categoryElem = myLib.fluentWait(By.className("category-products"));
		Assert.assertNotNull(categoryElem);

		// select the course
		WebElement ourNightSkyElem = driver.findElement(By.partialLinkText("Our Night Sky"));
		ourNightSkyElem.click();

		// Synchronize the page load
		WebElement chooseFormatElem = myLib.fluentWait(By.id("product-options-wrapper"));
		Assert.assertNotNull(chooseFormatElem);

		WebElement videoDownloadElem = driver
				.findElement(By.cssSelector("div.format-block:nth-child(1) > label:nth-child(2)"));
		videoDownloadElem.click();

		WebElement addToCartBtnElem = driver.findElement(By.id("add-to-cart-btn"));
		addToCartBtnElem.click();

		// Synchronize the page load
		WebElement proceedToCheckOutElem = myLib
				.fluentWait(By.cssSelector("div.block-content:nth-child(1) > div:nth-child(3) > button:nth-child(2)"));
		Assert.assertNotNull(proceedToCheckOutElem);

		WebElement cartElem = driver
				.findElement(By.cssSelector(".mini-cart-js > span:nth-child(1) > span:nth-child(2)"));
		String text = cartElem.getText();
		assertEquals(text.trim(), "1");
		proceedToCheckOutElem.click();

		// Synchronize the page load
		WebElement checkOutProgressElem = myLib.fluentWait(By.cssSelector("#login-email"));
		Assert.assertNotNull(checkOutProgressElem);

		String uniquTimeStamp = myLib.getCurrentTimeStamp(); // timestamp

		myLib.enterTextField(checkOutProgressElem, "test" + uniquTimeStamp + "@333.com");
		WebElement noRadioBtn = driver.findElement(
				By.cssSelector(".checkout_login_content_cont_pwd > div:nth-child(1) > label:nth-child(2)"));
		noRadioBtn.click();

		WebElement continueElem = driver.findElement(By.cssSelector("#checkout-sigin"));
		continueElem.click();

		/*
		 * WebElement dynamicElement =
		 * myLib.dynamicWait_presenceOfElementLocated(By.id("billing:firstname"));
		 * Assert.assertNotNull(dynamicElement);
		 */
		WebElement myDynamicElement = (new WebDriverWait(driver, 60))
				.until(ExpectedConditions.visibilityOf((driver.findElement(By.id("billing:firstname")))));
		Assert.assertNotNull(myDynamicElement);

		// complete profile section
		myLib.enterTextField(By.id("billing:firstname"), "frank1");
		myLib.enterTextField(By.id("billing:lastname"), "frank2");
		myLib.enterTextField(By.id("billing:street1"), "address 1");
		myLib.enterTextField(By.id("billing:city"), "apple city");
		myLib.selectDropDown(By.id("billing:region_id"), "Virginia");
		myLib.enterTextField(By.id("billing:postcode"), "789456");

		myLib.enterTextField(By.id("billing:customer_password"), "123456789");
		myLib.enterTextField(By.id("billing:confirm_password"), "123456789");

		WebElement continueBtn = driver.findElement(By.cssSelector("#billing-buttons-container > button:nth-child(1)"));
		continueBtn.click();

		/*
		 * WebElement next = navNextElem.findElement(By.tagName("a")); for(int i = 1; i
		 * <=3; i++){ myLib.clickInvisibleWebElement(next); myLib.customWait(0.5);
		 */

	}
}
