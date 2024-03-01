package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.custom.library.BasePage;

public class ChooseProductFormatPage extends BasePage{
	
	public ChooseProductFormatPage waitUntilPageLoadComplete()
	{ 	
		WebElement elem = myLib.waitUntilPageLoadComplete(By.id("media-format-radio"));
		Assert.assertNotNull(elem);
		return this;
	}
	
	public ChooseProductFormatPage click_videoDownloadRadioBtn()
	{
				
		return this;
	}
	
}
