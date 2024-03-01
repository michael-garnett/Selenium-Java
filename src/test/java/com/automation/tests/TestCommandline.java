package com.automation.tests;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class TestCommandline {

	WebDriver driver;
	
	@Test
	public void Test1() throws InterruptedException{
		
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://www.google.com/");
		Thread.sleep(3 * 1000);
	}
	
	@Test
	public void Test2() throws InterruptedException{
		
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://www.google.com/");
		String title = driver.getTitle();
		assertEquals(title, "aaaa");
		Thread.sleep(3 * 1000);
	}
}




















