package com.magic.test;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import org.testng.annotations.Test;

import com.magic.pages.LoginPage;
import com.magic.pages.StudentViewTesting;
import com.magic.pages.WriteDataInExcel;

public class MainTest 
{


	WriteDataInExcel excelWriting=new WriteDataInExcel();
	StudentViewTesting page=new StudentViewTesting();


	@Test(priority=1)
	public void navigationAndImplementation() throws Exception 
	{	
		LoginPage login=new LoginPage();
		login.login();
		page.test();
		
		FileUtils.copyFile(new File("./input/MHE_Results.xlsx"), new File("./result/MHE_Results.xlsx"));
		excelWriting.writingDataForPage(page.pagenumber_list);
	}
	


	@Test(priority=2)
	public void missingAlt() throws IOException
	{
		System.out.println("Data writing in progress");

		String rationale="User would be unable to understand the purpose of the image if the alternate text is missing for the images.";
		String recommendation="Alternative text should be provided to the image. Use \"alt\" attribute within the IMG tag.";
		excelWriting.writingDataForFailureInstance("There are non-decorative images or video. (Do NOT select \"partially...\" for components.)", "missingAlt", page.altdetailList,rationale ,recommendation,"visual");
	}
	@Test(priority=3)
	public void pageBreak() throws IOException
	{
		excelWriting.writingForPageBreak("Static print page break locations are included.","pageBreak", page.pageBreak_list);
	}
	
	@Test(priority=4)
	public void marqueeOrBlankTagPresence() throws IOException
	{

		String rationale="Marquee or blink element presence on the page";
		String recommendation="Remove it from the page";
		excelWriting.writingDataForFailureInstance("If any video or other moving element does not pass the no flashing standard, we have to report.", "marqueeOrBlankTagPresence", page.blinkTagList,rationale ,recommendation,"flashing");
		excelWriting.writingDataForFailureInstance("If any video or other moving element does not pass the no flashing standard, we have to report.", "marqueeOrBlankTagPresence", page.marqueeList,rationale ,recommendation,"flashing");

	}
	@Test(priority=5)
	public void trackTagInsideVideo() throws IOException
	{
	
		String rationale="Track tag not found in video";
		String recommendation="Add track tag into video or equivalent";
		excelWriting.writingDataForFailureInstance("There are non-decorative images or video. (Do NOT select \"partially...\" for components.)", "trackTagInsideVideo", page.videoTrack_list,rationale ,recommendation,"visual");
	}
	
	@Test(priority=6)
	public void headingHierarchy() throws IOException
	{
	
		String rationale="Heading hierarchy validation";
		String recommendation="use heading is in sequence";
		excelWriting.writingDataForFailureInstance("Headings marked up properly. All headings, all levels", "headingHierarchy", page.headingHierarchy_list,rationale ,recommendation,"StructuralNavigation");
	}
	
	
	@Test(priority=7)
	public void blankHeading() throws IOException
	{
		String rationale="Heading tag is blank";
		String recommendation="Give text into it or remove it";
		excelWriting.writingDataForFailureInstance("Headings marked up properly. All headings, all levels", "blankHeading", page.blankHeading_list,rationale ,recommendation,"StructuralNavigation");
	}
	
	@Test(priority=8)
	public void dataMathMl() throws IOException
	{
		String message="dataMathml";
		excelWriting.writingDataForDataExtraction("describedMath","We generally don’t do this as we rely on MathML", "dataMathMl", page.datamathml_list,message);
	}

	@Test(priority=9)
	public void altTextPresent() throws IOException
	{
		String message="Alt text present need manual validation for accuracy";
		excelWriting.writingDataForDataExtraction("alternativeText_data","ALL non-decorative images need to have appropriate alt text. This must be verified", "altTextPresent", page.altDataList,message);
	}


	@Test(priority=10)
	public void missingAltAndArialabelledby() throws IOException
	{
		
		String rationale="User would be unable to understand the purpose of the image if the alternate text is missing for the images.";
		String recommendation="Alternative text should be provided to the image. Use \"alt\" attribute within the IMG tag.";
	//	excelWriting.writingDataForFailureInstance("There are non-decorative images or video. (Do NOT select \"partially...\" for components.)", "missingAltAndArialabelledby", page.arialabelledbyAttrForBlankAltList,rationale ,recommendation);
	}
	
	@Test(priority=11)
	public void altTextLengthGreaterthan155() throws IOException
	{
		String message="Alt text length is exceeding from 155 character";
		excelWriting.writingDataForDataExtraction("alternativeText_length","ALL non-decorative images need to have appropriate alt text. This must be verified", "altTextLengthGreaterthan155", page.altTextLength_list,message);
	}

	@Test(priority=12)
	public void mathOperatorNotInMoTag() throws IOException
	{
		String message="Math operator tag is not in MO tag";
		excelWriting.writingDataForDataExtraction("MathML","When we use it, we can use this tag.", "mathOperatorNotInMoTag", page.mathOperator_list,message);
	}
	
	@Test(priority=13)
	public void longdescriptionText() throws IOException
	{
		String message="Long description text";
		excelWriting.writingDataForDataExtraction("longDescription","ALL complex images have an extended description. This must be verified, it cannot be just assumed.", "longdescriptionText", page.longdesc_list,message);
	}
	
	@Test(priority=14)
	public void audioOrVideoExistOnThePage() throws IOException
	{
		String message="Video or audio found on the page.";
		excelWriting.writingDataForDataExtraction("auditory","There are audio or video files. (Do NOT select \"partially...\" for components.)", "audioOrVideoExistOnThePage", page.audioAndVideo_list,message);
	}
	@Test(priority=15)
	public void fontSizeDefinedInPxOrPt() throws IOException
	{
		//String message="Font size defined in px or pt";
		excelWriting.writingDataForFontSizeStyle("CSS and other styling verified to only set font sizes as “em” or %s rather than “px” or “pt”", "fontSizeDefinedInPxOrPt", page.validation_fontSize_list);
	}
	
	/*@Test(priority=6)
	public void lagnAttributeValidation() throws IOException
	{
		String rationale="When configuring a screen reader, users select a default language. If the language of a webpage is not specified, the screen reader assumes the default language set by the user";
		String recommendation="Add a lang attribute to the html element (e.g. <html lang=\" \">) whose value represents the primary language of document";
		String message="Lang attribute is not defined in the html tag";
		excelWriting.writingDataForFailureInstance("3.1.1", "lagnAttributeValidation", page.presenceOfLangAttr_list,message,rationale ,recommendation );
	}
	 */

}
