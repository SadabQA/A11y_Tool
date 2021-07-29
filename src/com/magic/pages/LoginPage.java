package com.magic.pages;

import org.openqa.selenium.By;

import com.pearson.utils.Selenium;

public class LoginPage {
	
	
	public void login() throws Exception
	{
		Selenium.get("https://connectdemo.mheducation.com/");
		Selenium.sendKeys(By.xpath("//input[@id='userName']"), "a11y.qa1@mh.com");
		Selenium.sendKeys(By.xpath("//input[@id='password']"), "A11yQuality");
		Selenium.click(By.xpath("//button[text()='SIGN IN']"));
		
		
		try 
		{
			Selenium.click(By.xpath("//a[@title='close window']"));
			
		}catch (Exception e) 
		{
			
		}
		
		Selenium.click(By.xpath("//h4//span[text()='Brewer, Introduction to Managerial Accounting']/../../../following-sibling::div//a"));
		
		
		
		
	}
}
