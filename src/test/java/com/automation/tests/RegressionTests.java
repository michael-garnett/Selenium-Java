package com.automation.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.automation.pages.ChooseProductFormatPage;
import com.automation.pages.HomePage;
import com.automation.pages.ScienceCoursesPage;
import com.custom.library.BasePage;

public class RegressionTests extends BasePage{

	HomePage myHomePage = new HomePage();
	ScienceCoursesPage mySCpage = new ScienceCoursesPage();
	ChooseProductFormatPage myCPFpage = new ChooseProductFormatPage();
	
	@Test(priority =1, enabled = true)
	public void buyACourseTest() throws Exception
	{
		myHomePage.goto_theGreatCoursesWebsite();
		myHomePage.click_CategoryScience();
		
		mySCpage.waitUntilPageLoadComplete();
		mySCpage.select_A_Course("Our Night Sky");
		
		myCPFpage.waitUntilPageLoadComplete();
		myCPFpage.click_videoDownloadRadioBtn();
	}
	
	@Test(priority =1, enabled = false)
	public void fileUpload_test() throws InterruptedException
	{
		driver.get("http://demo.guru99.com/selenium/upload/");
		String filePath = "src/test/resources/screenshots/test1.png";
		WebElement fileUploadElem = driver.findElement(By.id("uploadfile_0"));
		fileUploadElem.sendKeys(filePath);
		Thread.sleep(5000);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
