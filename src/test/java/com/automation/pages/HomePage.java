package com.automation.pages;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.custom.library.BasePage;

public class HomePage extends BasePage {

	private String homepageURL = "http://www.thegreatcourses.com/";

	public HomePage goto_theGreatCoursesWebsite() {
		driver.get(homepageURL);
		String actualPageTitle = driver.getTitle();
		System.out.println("Website title: " + actualPageTitle);
		assertEquals(actualPageTitle, "Online Courses & Lectures for Home Study and Lifelong Learning");
		return this;
	}

	public HomePage click_CategoryScience() throws Exception {
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

		return this;
	}
}