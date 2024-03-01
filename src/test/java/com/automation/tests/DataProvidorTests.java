package com.automation.tests;

import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.custom.library.BasePage;
import com.custom.library.ExcelManager;

public class DataProvidorTests extends BasePage{

	@DataProvider(name = "Login")
	public static Object[][] credentials()
	{
		ExcelManager reader = new ExcelManager("src/test/resources/testData.xls");
		return reader.getExcelData("Sheet1");
	}
	
	@Test (dataProvider = "Login")
	public void loginTest(String userName, String userPassword) throws InterruptedException
	{
		driver.get("http://www.gmail.com/");
		
		driver.findElement(By.id("identifierId")).clear();
		driver.findElement(By.id("identifierId")).sendKeys(userName);
		driver.findElement(By.id("identifierNext")).click();
		Thread.sleep(3*1000);
		
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys(userPassword);
		Thread.sleep(3*1000);
	}
	
	
}
