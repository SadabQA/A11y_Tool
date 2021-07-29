package com.magic.pages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.openqa.selenium.By;

import com.pearson.utils.ExcelUtil;
import com.pearson.utils.Selenium;

public class CreateAssignments {

	HashMap<Integer, List<String>> map=new LinkedHashMap<>();

	public void createassignments() throws Exception
	{
		//ExcelUtil.createExcelFile("results", "createassignment", "data");
		
		String refreshlink = Selenium.getAttribute(By.xpath("//a[@title='section home page']"), "href");
		Selenium.click(By.xpath("//ul[@class='tabs']//*[contains(text(),'Add assignment')]"));

		Selenium.click(By.xpath("//div[@id='addassignment_icon_ctr']//*[contains(text(),'Question Bank')]"));

		try {
			Selenium.waitForElementPresence(By.xpath("//div[@class='modalHeader row collapse']//a[@title='close window']"),52);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int size=Selenium.getWebElements(By.xpath("//ul[@id='sourcessection']//li//a")).size();
		System.out.println(size);

		int key=0;
		for (int i = 1; i <= size; i++) 
		{
			try {
				List<String> list=new ArrayList<>();
				String firstlevel_txtvalue=Selenium.getText(By.xpath("(//ul[@id='sourcessection']//li//a)["+i+"]")).trim();
				System.out.println("Main Text:"+firstlevel_txtvalue);
				if(firstlevel_txtvalue.equalsIgnoreCase("select"))
				{
					String assignmet_text=Selenium.getText(By.xpath("((//ul[@id='sourcessection']//li)["+i+"]//span)[1]"));
					System.out.println("Text :"+assignmet_text);
					Selenium.click(By.xpath("(//ul[@id='sourcessection']//li//a)["+i+"]"));
					Thread.sleep(2500);
					
					String question = createLogic(assignmet_text);
					
					ExcelUtil.writeDataExistingSheet("createassignment", "data", (key+1), 0, assignmet_text);
					ExcelUtil.writeDataExistingSheet("createassignment", "data", (key+1), 1, "");
					ExcelUtil.writeDataExistingSheet("createassignment", "data", (key+1), 2, question);
					/*list.add(assignmet_text);
					list.add("");
					list.add(question);
					map.put(key, list);*/
					key++;
				}else
				{
					Selenium.click(By.xpath("(//ul[@id='sourcessection']//li//a)["+i+"]"));
					Thread.sleep(2500);
					int size1=Selenium.getWebElements(By.xpath("//ul[@id='sourcessection']//li//a")).size();

					for (int j = 1; j <= size1; j++) 
					{
						list=new ArrayList<>();
						String txtvalue1=Selenium.getText(By.xpath("(//ul[@id='sourcessection']//li//a)["+j+"]"));
						String assignment_txt="";
						String question="";
						if(txtvalue1.equalsIgnoreCase("select"))
						{
							assignment_txt = Selenium.getText(By.xpath("((//ul[@id='sourcessection']//li)["+j+"]//span)[1]"));
							System.out.println("-----Text :"+assignment_txt);
							Selenium.click(By.xpath("(//ul[@id='sourcessection']//li//a)["+j+"]"));
							question = createLogic(assignment_txt);
							Selenium.click(By.xpath("(//ul[@id='sourcessection']//li//a)["+i+"]"));
						}
						if(j==1)
						{
							ExcelUtil.writeDataExistingSheet("createassignment", "data", (key+1), 0, firstlevel_txtvalue);
							ExcelUtil.writeDataExistingSheet("createassignment", "data", (key+1), 1, assignment_txt);
							ExcelUtil.writeDataExistingSheet("createassignment", "data", (key+1), 2, question);
							/*list.add(firstlevel_txtvalue);
							list.add(assignment_txt);
							list.add(question);*/
						}else
						{
							ExcelUtil.writeDataExistingSheet("createassignment", "data", (key+1), 0, "");
							ExcelUtil.writeDataExistingSheet("createassignment", "data", (key+1), 1, assignment_txt);
							ExcelUtil.writeDataExistingSheet("createassignment", "data", (key+1), 2, question);
							/*list.add("");
							list.add(assignment_txt);
							list.add(question);*/
						}

						//map.put(key, list);
						key++;
					}

					Selenium.click(By.xpath("//span[@id='main_content']//a"));
				}
			} catch (Exception e) {
				ExcelUtil.writeDataExistingSheet("createassignment", "data", (key+1), 2, "Error in creating");
				ExcelUtil.writeDataExistingSheet("createassignment", "data", (key+1), 2, e.getMessage());
				Selenium.get(refreshlink);
				Selenium.checkPageIsReady();
				e.printStackTrace();
			}
		}

		List<String> header=new ArrayList<>();
		header.add("First Level");
		header.add("Second Level");
		header.add("Total no. Questions");
		//ExcelUtil.writeAllData(map, header, "assignment_result", "data");
	}



	public String createLogic(String assignment_txt) throws Exception
	{
		Selenium.getWebElement(By.xpath("//div[@id='QcontentContainer']//span[@class='chptheading']"));
		Selenium.sendKeys(By.xpath("//input[@id='title']"), assignment_txt);
		Selenium.click(By.xpath("//th//input[@name='cbox']"));
		Thread.sleep(1000);
		Selenium.JSClick(By.xpath("//a[@id='bottomaddQuestion']"));
		Thread.sleep(1000);
		Selenium.click(By.xpath("//a[@id='bottomaddQuestion']/following-sibling::div//a[text()='add as individual questions']"));
		Thread.sleep(2500);
		Selenium.click(By.xpath("//a[@id='create_Cont']"));
		Thread.sleep(2000);
		if(Selenium.getWebElements(By.xpath("//a[@class='closeIcon closeModal']")).size()>0)
		{
			Selenium.sendKeys(By.xpath("//input[@id='pointValue']"), "500");
			Selenium.click(By.xpath("//div[@id='saveSetpoint']"));
			Thread.sleep(2500);
			Selenium.click(By.xpath("//a[@id='create_Cont']"));
			Thread.sleep(2000);
		}
		String next = getNextDate(60);
		Selenium.sendKeys(By.xpath("//input[@id='scheduleDateEnd']"), next);
		Selenium.click(By.xpath("//a[@id='reviewAndAssignBtn']"));
		Selenium.click(By.xpath("//a[@id='reviewAssign_Assign']"));

		
		Thread.sleep(2500);
		String s="//a[@title='%s']";
		Selenium.click(By.xpath(s.format(s, assignment_txt)));
		
		String question = Selenium.getText(By.xpath("//span[@id='questionAndPointId']"));
		System.out.println(question);
		
		Selenium.click(By.xpath("//a[@class='home_button_e']"));
		
		Selenium.click(By.xpath("//ul[@class='tabs']//*[contains(text(),'Add assignment')]"));

		Selenium.click(By.xpath("//div[@id='addassignment_icon_ctr']//*[contains(text(),'Question Bank')]"));

		try {
			Selenium.waitForElementPresence(By.xpath("//div[@class='modalHeader row collapse']//a[@title='close window']"),52);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return question;
		
	}

	
	public  String getNextDate(int day) throws ParseException 
	{
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");  
		Date date = new Date();
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, day);
		String ss = format.format(calendar.getTime());
		return ss;
	}
}
