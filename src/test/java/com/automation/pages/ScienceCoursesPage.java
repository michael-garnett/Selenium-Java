package com.automation.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.custom.library.BasePage;

public class ScienceCoursesPage extends BasePage{

	public ScienceCoursesPage waitUntilPageLoadComplete()
	{ 	
		WebElement elem = myLib.waitUntilPageLoadComplete(By.className("category-products"));
		Assert.assertNotNull(elem);
		return this;
	}
	
	public ScienceCoursesPage select_A_Course(String courseTitleName)
	{
		click_Course(courseTitleName);
		return this;
	}
	
	//////////////// helper method section //////////////////////
	private void click_Course(String courseTitleNameText)
	{
		List<WebElement> products = driver.findElements(By.className("product-name"));
		for(WebElement singleCourse : products)
		{
			if(singleCourse.getText().contains(courseTitleNameText))
			{
				singleCourse.click();
				break;
			}
		}
	}
}




















