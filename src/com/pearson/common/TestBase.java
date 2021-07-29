package com.pearson.common;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;


public class TestBase
{  
	protected static WebDriver driver;
	
	static
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hhmmss");
		System.setProperty("current.date", dateFormat.format(new Date()));
	}
	
	private static Logger log=Logger.getLogger(TestBase.class);
	
	@AfterTest
	public void closeBrowser()
	{
		//driver.quit();
	}
	
	
	public static void mouseHover(String elementXpath, WebDriver driver)
	{
		Actions act=new Actions(driver);
		act.moveToElement(driver.findElement(By.xpath(elementXpath))).build().perform();
		
	}
	
	public static void selectvalue(String elementXpath, String value, WebDriver driver)
	{
		Select sel=new Select(driver.findElement(By.xpath(elementXpath)));
		sel.selectByVisibleText(value);
	}
	
	public static void waitforElementVisible(String elementXpath, WebDriver driver, long time)
	{
		WebDriverWait wd=new WebDriverWait(driver, time);
		wd.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(elementXpath)));
	}
	
	public static void handleAlert(WebDriver driver)
	{
		Alert alr=driver.switchTo().alert();
		alr.accept();
		
	}
	
	public static void waitforAlert(WebDriver driver, long time)
	{
		WebDriverWait wait=new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.alertIsPresent());
	}
	
	public static void waitforElementClickable(WebDriver driver, long time, String element)
	{
		WebDriverWait wait=new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(element))));
	}
	
	public static void switchtoWindow(WebDriver driver, int index)
	{
		Set<String> windowHandles = driver.getWindowHandles();
		driver.switchTo().window(windowHandles.toArray()[index].toString());
	}
	
	public static void captureScreenShot( WebDriver driver, String filename) throws IOException
	{
          try {
			File file=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			  FileUtils.copyFile(file, new File("./screenshots/"+filename+"_"+System.getProperty("current.date")+".png"));
		} catch (WebDriverException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          
	}


	public static void waitforElementInvisibility(WebDriver driver2, long l, String search_img_xpath) {
		// TODO Auto-generated method stub
		WebDriverWait wd=new WebDriverWait(driver, l);
		wd.until(ExpectedConditions.invisibilityOfElementLocated((By.xpath(search_img_xpath))));
	}
	
	
	


}
