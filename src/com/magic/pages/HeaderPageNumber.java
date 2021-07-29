package com.magic.pages;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.pearson.common.TestBase;
import com.pearson.utils.ExcelUtil;
import com.pearson.utils.Selenium;

public class HeaderPageNumber {

	public void page() throws Exception
	{
		Selenium.click(By.xpath("(//*[text()='eBook']/..//a[contains(@class,'textbook-title')])[2]"));
		Thread.sleep(4000);

		TestBase.switchtoWindow(Selenium.getWebDriver(), 1);
		Selenium.checkPageIsReady();

		Thread.sleep(10000);
		Map<Integer, List<String>> read = ExcelUtil.readAllData("dorwick", "toc", 1);
		
		for (Integer key : read.keySet()) {
			
			List<String> data = read.get(key);
			if(data.get(0).trim().length()>0)
			{
				String ss=String.format("//span[contains(text(),'%s')]/parent::button", data.get(0).trim());
				Selenium.JSClick(By.xpath(ss));
				Selenium.checkPageIsReady();
				Thread.sleep(2000);
				Selenium.blankClick();
				Thread.sleep(2000);
				
				WebElement iframe_element = Selenium.getWebElement(By.xpath("//iframe"));
				Selenium.switchToiFrameWithWebElement(By.xpath("//iframe"));
				
				String page="";
				try {
					page = "";
					
					try {
						Selenium.getWebElement(By.xpath("(//span[@role='doc-pagebreak'])[1]"));
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					int pages_size = Selenium.getWebElements(By.xpath("//span[@role='doc-pagebreak']")).size();
					if(pages_size==1)
					{
						page=Selenium.getWebElement(By.xpath("//span[@role='doc-pagebreak']")).getText();
					}
					else if(pages_size>1)
					{
						String first = Selenium.getWebElement(By.xpath("(//span[@role='doc-pagebreak'])[1]")).getText();
						String last = Selenium.getWebElement(By.xpath("(//span[@role='doc-pagebreak'])["+pages_size+"]")).getText();
						page=first+"-"+last;
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				Selenium.getWebDriver().switchTo().defaultContent();
				ExcelUtil.writeDataExistingSheet("dorwick", "toc", (key+1), 3, page);
				
				System.out.println(page);
			}
		}
	}
	
}

