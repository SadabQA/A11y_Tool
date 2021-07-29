package com.magic.pages;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import com.pearson.utils.Selenium;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class StudentViewTesting {

	public int imageCount=0;
	int counter=0;


	public List<String> altdetailList=new ArrayList<>();

	public List<String> altDataList=new ArrayList<>();

	public List<String> presenceOfLangAttr_list=new ArrayList<>();

	public List<String> pagenumber_list=new ArrayList<>();

	public List<String> pageBreak_list=new ArrayList<>();
	public List<String> videoTrack_list=new ArrayList<>();
	public List<String> marqueeList=new ArrayList<>();
	public List<String> blinkTagList=new ArrayList<>();

	public List<String> headingHierarchy_list=new ArrayList<>();
	public List<String> blankHeading_list=new ArrayList<>();

	public List<String> datamathml_list=new ArrayList<>();

	public List<String> arialabelledbyAttrForBlankAltList=new ArrayList<>();

	public List<String> altTextLength_list=new ArrayList<>();

	public List<String> mathOperator_list=new ArrayList<>();
	
	public List<String> audioAndVideo_list=new ArrayList<>();
	
	public List<String> validation_fontSize_list=new ArrayList<>();

	public List<String> longdesc_list=new ArrayList<>();



	int counterNav=1;


	public void test() throws Exception {

		Selenium.click(By.xpath("//a[text()='Student view']"));
		Thread.sleep(5000);
		Selenium.switchToiFrameWithWebElement(By.xpath("//iframe[@id='student_preview_frame']"));
		Selenium.switchToiFrameWithWebElement(By.xpath("//iframe[@title='Class in Connect']"));

		Selenium.click(By.xpath("//span[text()='Introduction to Managerial Accounting']"));

		Thread.sleep(2000);

		Selenium.switchToWindowByNumber(1);

		Selenium.switchOutFromFrame();


		Selenium.click(By.xpath("//button[@aria-label='Cancel']"));



		while(true)
		{

			Selenium.getWebDriver().switchTo().frame("clo-iframe");
			Thread.sleep(1000);

			String page="";

			String pageHeading="";

			try {
				page = "";
				int pages_size = Selenium.getWebDriver().findElements(By.xpath("//span[@role='doc-pagebreak' or @class='page-number' or @class='page-number dynamic-page']")).size();
				if(pages_size==1)
				{
					page=Selenium.getWebDriver().findElement(By.xpath("//span[@role='doc-pagebreak' or @class='page-number' or @class='page-number dynamic-page']")).getText();
				}
				else if(pages_size>1)
				{
					String first = Selenium.getWebDriver().findElement(By.xpath("(//span[@role='doc-pagebreak' or @class='page-number' or @class='page-number dynamic-page'])[1]")).getText();
					String last = Selenium.getWebDriver().findElement(By.xpath("(//span[@role='doc-pagebreak' or @class='page-number' or @class='page-number dynamic-page'])["+pages_size+"]")).getText();
					page=first+"-"+last;
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				pageHeading=Selenium.getWebElement(By.xpath("(//h1 | //h2)[1]")).getText();
			}
			catch (Exception e) {
				pageHeading="";
				e.printStackTrace();
			}


			System.out.println("Heading text "+pageHeading);

			Thread.sleep(1250);

			collectDataforMissingAlt(pageHeading, page);
			validationFontSize(pageHeading, page);
			trackTagInVideo(pageHeading, page);
			marqueeAndBlinkTag(pageHeading, page);
			pageBreak(pageHeading, page);
			headingValidation(pageHeading, page);
			dataMathMl(pageHeading, page);
			mathOperatorTag(pageHeading, page);

			longDescriptionForImage(pageHeading, page);

			audioAndVideoTagPresence(pageHeading, page);

			Selenium.switchOutFromFrame();
		//	presenceOfLangAttributeOnPage(pageHeading,page);

			pagenumber_list.add(pageHeading+"@#"+page);

			if(Selenium.getWebElements(By.xpath("//button[@id='next-button' and @disabled='disabled']")).size()>0)
			{
				break;
			}
			else
			{
				Selenium.click(By.id("next-button"));
				Thread.sleep(2000);
				counterNav++;
			}
			if(counterNav==20)
			{
				break;
			}
		}
	}





	private void audioAndVideoTagPresence(String pageHeading, String page) 
	{



		System.out.println("Testing checkpoint : audioAndVideoTagPresence");
		try
		{

			List<WebElement> elements = Selenium.getWebElements(By.xpath("//audio | //video"));


			for(WebElement e : elements)
			{
				
				audioAndVideo_list.add(pageHeading+"@#"+page+"@#"+e.getAttribute("outerHTML"));
			}

		}
		catch(Exception e)
		{}

	
		
	}


	private void longDescriptionForImage(String pageHeading, String page) 
	{

		System.out.println("Testing checkpoint : longDescriptionForImage");
		try
		{

			String xpathLongdesc = "(//img[not(@role='presentation') and not(@class='longdesc-icon')])";
			List<WebElement> elements = Selenium.getWebElements(By.xpath(xpathLongdesc));


			for(int i=1;i<=elements.size();i++)
			{

				try {
					
					if(Selenium.getWebElements(By.xpath(xpathLongdesc+"["+i+"]/ancestor::figure//following-sibling::a//img[@class='longdesc-icon']")).size()>0)
					{
						WebElement longdescEle=Selenium.getWebElement(By.xpath(xpathLongdesc+"["+i+"]/ancestor::figure//following-sibling::a//img[@class='longdesc-icon']"));
						String longdescOuter = longdescEle.getAttribute("outerHTML");
						Selenium.click(longdescEle);
						Thread.sleep(1500);
						String longdescText="";
						try {
							longdescText = Selenium.getWebElement(By.xpath("//body")).getText();
						} catch (Exception e) {
							Thread.sleep(1500);
							
							longdescText = Selenium.getWebElement(By.xpath("//body")).getText();
						}

						System.out.println("Long description text "+longdescText);
						longdesc_list.add(pageHeading+"@#"+page+"@#"+longdescOuter+"@#"+longdescText);

						Selenium.switchOutFromFrame();
						Selenium.getWebDriver().switchTo().defaultContent();
						Thread.sleep(1000);
						Selenium.click(By.xpath("//button[@id='back-button']"));

						Thread.sleep(2000);

						Selenium.getWebDriver().switchTo().frame("clo-iframe");

					}
				} catch (Exception e) {
					Selenium.getWebDriver().switchTo().defaultContent();
					Thread.sleep(1000);
					Selenium.click(By.xpath("//button[@id='back-button']"));

					Thread.sleep(2000);

					Selenium.getWebDriver().switchTo().frame("clo-iframe");
					e.printStackTrace();
				}
			}

		}
		catch(Exception e)
		{}
	}





	private void mathOperatorTag(String pageHeading, String page) {


		System.out.println("Testing checkpoint : mathOperatorTag");
		
		try
		{

			List<WebElement> elements = Selenium.getWebElements(By.xpath("//*[text()='+']|//*[text()='-']|//*[text()='%'] |//*[text()='/']"));


			for(WebElement e : elements)
			{
				if(!e.getTagName().equalsIgnoreCase("mo"))
				{
					mathOperator_list.add(pageHeading+"@#"+page+"@#"+e.getAttribute("outerHTML"));
				}
			}
		}
		catch(Exception e)
		{}

	}





	private void dataMathMl(String heading, String page) 
	{

		System.out.println("Testing checkpoint : dataMathMl");
		try
		{

			List<WebElement> elements = Selenium.getWebElements(By.xpath("//*[@data-mathml]"));


			for(WebElement e : elements)
			{
				datamathml_list.add(heading+"@#"+page+"@#"+e.getAttribute("outerHTML"));	
			}

		}
		catch(Exception e)
		{}



	}





	/**1.3.1
	 * This method is working for all the heading checkpoint like heading hierarchy of heading blank heading, heading role, presence of h1 on the page
	 *
	 *Page does not start with a Heading 1 or Heading 2. from sa11y
	 */

	public void headingValidation(String heading, String page) 
	{	
		System.out.println("Testing checkpoint : headingValidation");
		try
		{

			List<WebElement> elements = Selenium.getWebElements(By.xpath("//h1 | //h2 | //h3 | //h4 | //h5 | //h6"));
			boolean h1_status = false;
			boolean h1Orh2_status=false;
			List<String> list = new ArrayList<>();
			list.add("h1");
			list.add("h2");
			list.add("h3");
			list.add("h4");
			list.add("h3");
			list.add("h6");
			int counterForHeading=0;
			List<String> headinglist = new ArrayList<>();

			for(WebElement e : elements)
			{
				String tagname = e.getTagName();
				if(list.contains(tagname))
				{
					headinglist.add(tagname+"_"+e.getAttribute("outerHTML"));
					counterForHeading++;

					if(e.getText().length()==0)
					{
						blankHeading_list.add(heading+"@#"+page+"@#"+e.getAttribute("outerHTML"));
					}

					//heading role checking

					/*if(e.getAttribute("role")!=null && e.getAttribute("role").equalsIgnoreCase("heading"))
					{
						heading_role.add(page_details+"@#"+e.getAttribute("outerHTML"));
					}*/
					//checking first heading of the page is h1 or not

					if(tagname.equalsIgnoreCase("h1"))
					{
						h1_status = true;
					}

					//page heading should h1 or h2 

					if(counterForHeading==1&&(tagname.equalsIgnoreCase("h1")||tagname.equalsIgnoreCase("h2")))
					{
						h1Orh2_status=true;
					}

				}
			}
			/*if(h1_status==false)
			{
				headingLevel_1_List.add(page_details);
			}

			if(h1Orh2_status==false)
			{
				pageDoesNotStartWithH1OrH2_list.add(page_details);
			}*/

			headingHierarchy(headinglist,heading,page);
		}
		catch(Exception e)
		{}
	}



	public void headingHierarchy(List<String> headinglist,String pageHeading,String page) 
	{
		System.out.println("Testing checkpoint : headingHierarchy");

		for(int i = 0; i<headinglist.size(); i++)
		{

			String heading_code = headinglist.get(i);
			String heading="";
			String codesnippet="";
			String nextCodesnippet="";
			try {
				String[] data = heading_code.split("_");
				heading=data[0];
				codesnippet = data[1];
			}catch (Exception e) {

			}
			String nextheading = "";
			try {
				String nextHeading_code = headinglist.get(i+1);
				String[] data2 = nextHeading_code.split("_");
				nextheading=data2[0];
				nextCodesnippet = data2[1];
			}catch (Exception e) {

			}


			if(heading.equalsIgnoreCase("h1"))
			{
				if(!(nextheading.equalsIgnoreCase("h1")||nextheading.equalsIgnoreCase("h2")||nextheading.equalsIgnoreCase("")))
				{
					headingHierarchy_list.add(pageHeading+"@#"+page+"@# "+heading+"---> "+nextCodesnippet);
				}
			}
			else if(heading.equalsIgnoreCase("h2"))
			{
				if(!(nextheading.equalsIgnoreCase("h2")||nextheading.equalsIgnoreCase("h3")||nextheading.equalsIgnoreCase("")))
				{
					headingHierarchy_list.add(pageHeading+"@#"+page+"@# "+heading+"---> "+nextCodesnippet);
				}
			}
			else if(heading.equalsIgnoreCase("h3"))
			{
				if(!(nextheading.equalsIgnoreCase("h3")||nextheading.equalsIgnoreCase("h4")||nextheading.equalsIgnoreCase("")))
				{
					headingHierarchy_list.add(pageHeading+"@#"+page+"@# "+heading+"---> "+nextCodesnippet);
				}
			}
			else if(heading.equalsIgnoreCase("h4"))
			{
				if(!(nextheading.equalsIgnoreCase("h4")||nextheading.equalsIgnoreCase("h3")||nextheading.equalsIgnoreCase("")))
				{
					headingHierarchy_list.add(pageHeading+"@#"+page+"@# "+heading+"---> "+nextCodesnippet);
				}
			}
			else if(heading.equalsIgnoreCase("h3"))
			{
				if(!(nextheading.equalsIgnoreCase("h3")||nextheading.equalsIgnoreCase("h6")||nextheading.equalsIgnoreCase("")))
				{
					headingHierarchy_list.add(pageHeading+"@#"+page+"@# "+heading+"---> "+nextCodesnippet);
				}
			}
			else if(heading.equalsIgnoreCase("h6"))
			{
				if(!(nextheading.equalsIgnoreCase("h6")||nextheading.equalsIgnoreCase("")))
				{
					headingHierarchy_list.add(pageHeading+"@#"+page+"@# "+heading+"---> "+nextCodesnippet);
				}
			}
		}

	}


	private void trackTagInVideo(String heading, String page) {



		System.out.println("Testing checkpoint : trackTagInVideo");

		List<WebElement> elist = new ArrayList<>();

		try 
		{
			elist = Selenium.getWebElements(By.xpath("//video"));
		} catch (Exception e1) {}

		for (int i=1;i<=elist.size();i++)
		{

			try {
				if(Selenium.getWebElements(By.xpath("(//video)["+i+"]//track")).size()>0)
				{

				}else
				{
					videoTrack_list.add(heading+"@#"+page+"@#"+Selenium.getWebElement(By.xpath("(//video)["+i+"]")).getAttribute("outerHTML"));			
				}
			} catch (Exception e) {

			}
		}
	}





	private void pageBreak(String heading, String page)
	{

		System.out.println("Testing checkpoint : pageBreak");

		List<WebElement> elist = new ArrayList<>();
		try 
		{
			elist = Selenium.getWebElements(By.xpath("//span[@class='page-number']"));
		} catch (Exception e1) {}
		String allPages="";
		for (WebElement e : elist)
		{
			if(allPages.length()>0)
			{
				allPages=allPages+"@#"+e.getText();
			}
			else
			{
				allPages=e.getText();
			}

			
			//System.out.println("Inside of loop "+allPages);
		}
		//System.out.println("Outside of loop "+allPages);
		pageBreak_list.add(heading+"@#"+allPages);
		//System.out.println("List of loop "+pageBreak_list);
	}





	/**
	 * marquee and blink tag checking 2.2.2
	 * @param page 
	 * @param heading 
	 */
	private void marqueeAndBlinkTag(String heading, String page) 
	{
		System.out.println("Testing checkpoint : marqueeAndBlinkTag");

		try {
			List<WebElement> blinktag = new ArrayList<>();
			List<WebElement> marqueetag = new ArrayList<>();
			By marquee=By.xpath("//marquee");
			By blink=By.xpath("//blink");

			try 
			{
				blinktag = Selenium.getWebElements(blink);
			} catch (Exception e1) {}

			try 
			{
				marqueetag = Selenium.getWebElements(marquee);

			} catch (Exception e1) {}

			for (WebElement webElement : marqueetag) 
			{
				marqueeList.add(heading+"@#"+page+"@#"+webElement.getAttribute("outerHTML"));
			}
			for (WebElement webElement : blinktag) 
			{
				blinkTagList.add(heading+"@#"+page+"@#"+webElement.getAttribute("outerHTML"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}










	private void validationFontSize(String heading, String page) throws Exception {

		System.out.println("Testing checkpoint : validationFontSize");

		List<WebElement> elist = new ArrayList<>();
		try {
			elist = Selenium.getWebElements(By.xpath("//p | //span"));
		} catch (Exception e1) {}

		
		for (int i=1;i<elist.size();i++)
		{
			
			
			String fontSize =Selenium.getWebElement(By.xpath("(//p | //span)["+i+"]")).getCssValue("font-size");
			String getAttributeStyle =Selenium.getWebElement(By.xpath("(//p | //span)["+i+"]")).getAttribute("style");
			String OUTER =Selenium.getWebElement(By.xpath("(//p | //span)["+i+"]")).getAttribute("outerHTML");
			
			System.out.println("style"+getAttributeStyle);
			
			if(fontSize.contains("px") ||fontSize.contains("pt") )
			{
			System.out.println("Font size "+fontSize);
			validation_fontSize_list.add(heading+"@#"+page+"@#"+ OUTER+"@#"+fontSize);
			}
		}	
	}


	public void collectDataforMissingAlt(String chapter,String page) throws Exception {

		System.out.println("Testing checkpoint : collectDataforMissingAlt");

		List<WebElement> imglist = new ArrayList<>();
		try {
			imglist = Selenium.getWebElements(By.xpath("//img[not(@role='presentation')]"));
		} catch (Exception e1) {}

		//System.out.println(imglist);
		for (WebElement e : imglist)
		{
			imageCount++;

			System.out.println("Src of images "+e.getAttribute("src"));


			if(e.getAttribute("alt").trim().length()==0) 
			{
				String codesnippet = e.getAttribute("outerHTML");
				codesnippet = codesnippet.substring(0, codesnippet.indexOf(">")+1);
				altdetailList.add(chapter+"@#"+page+"@#"+codesnippet);
			}
			else
			{
				if(e.getAttribute("alt").length()>155)
				{
					altTextLength_list.add(chapter+"@#"+page+"@#"+ e.getAttribute("outerHTML")+"@#"+Integer.toString(e.getAttribute("alt").length()));
				}
				altDataList.add(chapter+"@#"+page+"@#"+ e.getAttribute("outerHTML")+"@#"+e.getAttribute("alt"));
			}
			
		/*if(e.getAttribute("alt").isEmpty()||e.getAttribute("alt").isEmpty()) 
			{
				if(e.getAttribute("aria-labelledby") == null)
				{
					arialabelledbyAttrForBlankAltList.add(chapter+"@#"+page+"@#"+e.getAttribute("outerHTML"));
				}
			}*/ 
			//getTextOfImages(e.getAttribute("src"));

		}	
	}




	/**
	 * Checking the presence of html lang attribute guideline 3.1.1
	 * @param question 
	 * @param chapter 
	 * 
	 */

	//default content
	private void presenceOfLangAttributeOnPage(String chapter, String question) 
	{
		String htmlCode="";
		System.out.println("Testing checkpoint : presenceOfLangAttributeOnPage");
		try 
		{
			htmlCode = Selenium.getWebElement(By.xpath("//html")).getAttribute("outerHTML");

			htmlCode = htmlCode.substring(1, htmlCode.indexOf(">"));
			if(Selenium.getWebElements(By.xpath("//html[@lang]"))==null ||Selenium.getWebElements(By.xpath("//html[@lang]")).size()==0)
			{
				presenceOfLangAttr_list.add(chapter+"@#"+question+"@#"+htmlCode);
			}
		}
		catch (Exception e) 
		{
			presenceOfLangAttr_list.add(chapter+"@#"+question+"@#"+htmlCode);
		}
	}

	/**
	 * Get text of images 
	 * @param e
	 * @throws Exception 
	 */
	private void getTextOfImages(String src) throws Exception 
	{
		URL url = new URL(src);
		String fileName = url.getFile();
		String destName = "./images/" + fileName.substring(fileName.lastIndexOf("/"));

		JavascriptExecutor jse = ((JavascriptExecutor)Selenium.getWebDriver());
		jse.executeScript("window.open()");

		Selenium.switchToWindowByNumber(2);

		Selenium.getWebDriver().get(src);

		File screenshot = ((TakesScreenshot)Selenium.getWebDriver()).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshot, new File(destName));

		Selenium.getWebDriver().close();

		Selenium.switchToWindowByNumber(1);

		Selenium.getWebDriver().switchTo().frame("clo-iframe");


		Tesseract tesseract = new Tesseract();
		try {

			tesseract.setDatapath("D:/Tess4J/tessdata");

			// the path of your tess data folder
			// inside the extracted file
			String text
			= tesseract.doOCR(new File(destName));

			// path of your image file
			System.out.print("Images of text "+text);
			Thread.sleep(1000);
		}
		catch (TesseractException e) {
			e.printStackTrace();
		}




		/*	try {
			 url = new URL(src);
			 fileName = url.getFile();
			 destName = "./images/" + fileName.substring(fileName.lastIndexOf("/"));

			InputStream is = url.openStream();
			OutputStream os = new FileOutputStream(destName);

			byte[] b = new byte[2048];
			int length;

			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}

			is.close();
			os.close();
		}catch (Exception e) {
			e.printStackTrace();
		}*/
	}
}

