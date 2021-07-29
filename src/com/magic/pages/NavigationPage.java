package com.magic.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.pearson.common.TestBase;
import com.pearson.utils.ExcelUtil;
import com.pearson.utils.Selenium;

public class NavigationPage {

	HashMap<Integer, List<String>> map=new LinkedHashMap<>();
	HashMap<Integer, List<String>> map_page=new LinkedHashMap<>();

	public void navigate() throws Exception {


		
		Selenium.click(By.xpath("(//*[text()='eBook']/..//a[contains(@class,'textbook-title')])[2]"));
		Thread.sleep(4000);

		TestBase.switchtoWindow(Selenium.getWebDriver(), 1);
		Selenium.checkPageIsReady();

		Thread.sleep(10000);

		Selenium.click(By.xpath("//button[@id='toc-toggle']"));

		int tocheader=Selenium.getWebElements(By.xpath("//ul[@class='epub-toc-container']/li")).size();
		int count=0;

		for (int i = 1; i <=tocheader;  i++) {

			int tocheader_2=Selenium.getWebElements(By.xpath("(//ul[@class='epub-toc-container']/li)["+i+"]//span")).size();

			String aria = Selenium.getAttribute(By.xpath("(//ul[@class='epub-toc-container']/li)["+i+"]//button[contains(@class,'md-button-toggle md-button md-ink-ripple layout-row')]"), "aria-expanded");
			if(aria.equalsIgnoreCase("false"))
			{
				Selenium.click(By.xpath("(//ul[@class='epub-toc-container']/li)["+i+"]//button[@class='md-button-toggle md-button md-ink-ripple layout-row']"));
			}
			for (int j = 1; j <= tocheader_2; j++) {

				List<String> list=new ArrayList<>();
				String text=Selenium.getWebElement(By.xpath("((//ul[@class='epub-toc-container']/li)["+i+"]//span)["+j+"]")).getText();
				System.out.println("Text:"+text);

				if(j==1)
				{
					list.add(text);
				}else
				{
					list.add("");
					list.add(text);
				}

				map.put(count, list);
				count++;
			}
			aria = Selenium.getAttribute(By.xpath("(//ul[@class='epub-toc-container']/li)["+i+"]//button[contains(@class,'md-button-toggle md-button md-ink-ripple layout-row')]"), "aria-expanded");
			if(aria.equalsIgnoreCase("true"))
			{
				Selenium.click(By.xpath("(//ul[@class='epub-toc-container']/li)["+i+"]//button[contains(@class,'md-button-toggle md-button md-ink-ripple layout-row')]"));
			}
		}

		Selenium.blankClick();
		Thread.sleep(5000);

		List<String> header_list=new ArrayList<>();
		header_list.add("TOC Structure");
	//	ExcelUtil.writeAllData(map, header_list, "result", "toc");

		boolean status=true;
		int pagecount=1;
		String chapter="";
		int countt=0;
		int pagebrlcount=0;

		while(status)
		{
			List<String> list=new ArrayList<>();
			String header = "";

			WebElement iframe_element = Selenium.getWebElement(By.xpath("//iframe"));
			Selenium.switchToiFrameWithWebElement(By.xpath("//iframe"));
			try 
			{	
				header = Selenium.getWebElement(By.xpath("(//header)[1]")).getText();
				String s=header.toLowerCase();
				if(s.contains("chapter"))
				{
					System.out.println(s.substring(s.indexOf("chapter"),s.indexOf("chapter")+11));
					chapter=s.substring(s.indexOf("chapter"),s.indexOf("chapter")+11);
				}

			} catch (Exception e1) {

				if(Selenium.getWebElements(By.xpath("(//h1)[1]")).size()>0)
				{
					String txt = Selenium.getWebElement(By.xpath("(//h1)[1]")).getText();
					if(txt.contains("Text Alternative"))
					{
						pagebrlcount++;
					}
				}
			}

			String page="";
			try {
				page = "";
				
				try {
					Selenium.getWebElement(By.xpath("(//span[@role='doc-pagebreak'])[1]"));
				} catch (Exception e) {}
				
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


			if(Selenium.getWebElements(By.xpath("//button[@aria-label='Next' and @disabled]")).size()>0)
			{
				status=false;
				break;
			}
			if(pagebrlcount==5)
			{
				break;
			}
			try {
				Selenium.getWebElement(By.xpath("//button[@aria-label='Next']")).click();
				Thread.sleep(5000);
				Selenium.checkPageIsReady();
				pagecount++;

			} catch (Exception e) {
			}

			System.out.println("Header: "+header);
			System.out.println("Page: "+page);
			list.add(page);
			list.add(header.replaceAll(page, ""));
			list.add(chapter);

			map_page.put(countt, list);
			countt++;
		}

		List<String> header_lst=new ArrayList<>();
		header_lst.add("Page");
		header_lst.add("Header");
	//	ExcelUtil.writeAllDataInNewsheetInexistingfile("result", "page", map_page, header_lst);
	}

}
