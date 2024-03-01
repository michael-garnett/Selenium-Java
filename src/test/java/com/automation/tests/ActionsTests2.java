package com.automation.tests;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import com.custom.library.BasePage;

public class ActionsTests2 extends BasePage{

	final static Logger logger = Logger.getLogger(ActionsTests2.class);
	
	@Test
	public void testing_mouse_hoverover() throws Exception
	{
		driver.get("https://www.usps.com/");
		logger.info("go to - https://www.usps.com/");
		WebElement mailShipMenu = driver.findElement(By.cssSelector("#global-menu > div > nav > ol > li.menu--tier-one-category--mail-ship > a"));
		logger.info("txt: " + mailShipMenu.getText());
		
		myLib.moveMouseToElement(mailShipMenu);
		WebElement subMenu = driver.findElement(By.partialLinkText("Look Up a ZIP Code"));		
		subMenu.click();
		
		Actions action = new Actions(driver);
		action.moveToElement(mailShipMenu).perform();
		//action.build().perform();		
		myLib.customWait(1);
		/*WebElement subMenu = driver.findElement(By.partialLinkText("Look Up a ZIP Code"));
		System.out.println("txt: " + subMenu.getText());
		action.moveToElement(subMenu).perform();*/
		myLib.customWait(1);
		
	}
	
	//@Test
	public void testing()
	{
		driver.get("https://www.usps.com/");
		//step1 - locate the parent menu
		WebElement menu = driver.findElement(By.className("menu--tier-one-category--mail-ship"));
		System.out.println("text: " + menu.getText());
		
		myLib.moveMouseToElement(menu);
		
		//step2 - locate 'li' sub-mune options
		List<WebElement> submenu = menu.findElements(By.tagName("li"));
		System.out.println("size: " + submenu.size());
		
		String text2 = submenu.get(5).findElement(By.tagName("a")).getText();
		System.out.println("text2: "+ text2);
	}
	
	//@Test
	public void testing_page_scrolling() throws Exception
	{
		String url = "http://jqueryui.com/";
		driver.get(url);
		
		WebElement book = myLib.fluentWait(By.partialLinkText("jQuery UI Cookbook"));
		System.out.println("book text: " + book.getText());
		
		//((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", book);
		myLib.scrollByOffset("300");
		myLib.customWait(5);
		String firstWindowHandle = driver.getWindowHandle();
		
		((JavascriptExecutor)driver).executeScript("window.open()");
		//WebDriver driver = myLib.startChromeBrowser();
		String url2 = "http://www.amazon.com/";
		myLib.switchWindows("about:blank");	 
		driver.get(url2);
		myLib.customWait(2);
		//driver.close();
		myLib.switchWindows(url);
		myLib.customWait(3);
		driver.navigate().to("http://www.walmart.com/");
		System.out.println("Browser: "+ ", Window Handle: " +firstWindowHandle + ", title: " + driver.getTitle() );		
	}
	
	//@Test
	public void openBrowser()
	{		
		String url = "http://jqueryui.com/";
		driver.get(url);
		((JavascriptExecutor)driver).executeScript("window.open()");	
		Set<String> windows = driver.getWindowHandles();
		System.out.println("window handle size: " + windows.size());
		int incrementor = 1; 
		for(String singleWindow : windows)
		{
			driver.switchTo().window(singleWindow);
			System.out.println("Browser: "+ incrementor + ", Window Handle: " +singleWindow + ", title: " + driver.getTitle() );
			incrementor ++;
		}				
	}	
}
