package com.magic.pages;

import java.io.IOException;
import java.util.List;

import com.pearson.utils.ExcelUtil;



public class WriteDataInExcel 
{

	public boolean writingDataForFailureInstance(String criteria,String checkpoint,List<String> list,String rationale,String recommendation) throws IOException 
	{
		String filename="./result/MHE_Results";
		int rownum = ExcelUtil.getRowCount(filename, "Bug")+1;
		boolean test_status=true;

		for (String string : list) {

			String[] page_code=string.split("@#");
			ExcelUtil.writeDataExistingSheet(filename, "Bug", rownum, 0, criteria);
			ExcelUtil.writeDataExistingSheet(filename, "Bug", rownum, 1, checkpoint);
			ExcelUtil.writeDataExistingSheet(filename, "Bug", rownum, 2, page_code[0]);
			ExcelUtil.writeDataExistingSheet(filename, "Bug", rownum, 3, page_code[1]);
			ExcelUtil.writeDataExistingSheet(filename, "Bug", rownum, 4, page_code[2]);
			if(page_code[2].length()>32000)
			{
				page_code[2]=page_code[2].substring(0, 32000);
			}
			ExcelUtil.writeDataExistingSheet(filename, "Bug", rownum, 5, rationale);
			ExcelUtil.writeDataExistingSheet(filename, "Bug", rownum, 6, recommendation);

			rownum++;
		}

		if(list.size()==0)
		{
			test_status=false;
		}
		return test_status;
	}

	public void writingDataForDataExtraction(String criteria,String checkpoint,List<String> list,String message) throws IOException 
	{
		String filename="./result/MHE_Results";
		int rownum = ExcelUtil.getRowCount(filename, "Extracted Data")+1;
		boolean test_status=true;

		for (String string : list) 
		{
			String[] page_code=string.split("@#");
			ExcelUtil.writeDataExistingSheet(filename, "Extracted Data", rownum, 0, criteria);
			ExcelUtil.writeDataExistingSheet(filename, "Extracted Data", rownum, 1, checkpoint);
			ExcelUtil.writeDataExistingSheet(filename, "Extracted Data", rownum, 2, page_code[0]);
			ExcelUtil.writeDataExistingSheet(filename, "Extracted Data", rownum, 3, page_code[1]);
			if(page_code[2].length()>32000)
			{
				page_code[2]=page_code[2].substring(0, 32000);
			}
			ExcelUtil.writeDataExistingSheet(filename, "Extracted Data", rownum, 4, page_code[2]);

			if(page_code.length>3)
			{
				ExcelUtil.writeDataExistingSheet(filename, "Extracted Data", rownum, 5, page_code[3]);

			}
			
			ExcelUtil.writeDataExistingSheet(filename, "Extracted Data", rownum, 6, message);
			rownum++;
		}
	}

	public boolean writingDataForPage(List<String> list) throws IOException 
	{
		String filename="./result/MHE_Results";
		
		int rownum = ExcelUtil.getRowCount(filename, "Page Number")+1;
		
		boolean test_status=true;

		for (String string : list) 
		{	
			String[] page_code=string.split("@#");
			
			ExcelUtil.writeDataExistingSheet(filename, "Page Number", rownum, 0, page_code[0]);
			
			if(page_code.length>1)
			{
				ExcelUtil.writeDataExistingSheet(filename, "Page Number", rownum, 1, page_code[1]);	
			}
			
			rownum++;
		}

		if(list.size()==0)
		{
			test_status=false;
		}
		return test_status;
	}
	
	public boolean writingForPageBreak(String Criteria, String checkpoint,List<String> list) throws IOException 
	{
		String filename="./result/MHE_Results";
		
		int rownum =1;
		
		boolean test_status=true;

		for (String string : list) 
		{	
			String[] page_code=string.split("@#");
			
			ExcelUtil.writeDataExistingSheet(filename, "Page break", rownum, 0, Criteria);
			ExcelUtil.writeDataExistingSheet(filename, "Page break", rownum, 1, checkpoint);
			ExcelUtil.writeDataExistingSheet(filename, "Page break", rownum, 2, page_code[0]);
			
			for (int i = 1; i < page_code.length; i++) 
			{
				ExcelUtil.writeDataExistingSheet(filename, "Page break", rownum, (2+i), page_code[i]);
			}
			
			rownum++;
		}

		if(list.size()==0)
		{
			test_status=false;
		}
		return test_status;
	}
	
	public boolean writingDataForFontSizeStyle(String criteria, String checkpoint,List<String> list) throws IOException 
	{
		String filename="./result/MHE_Results";
		
		int rownum = ExcelUtil.getRowCount(filename, "Text Font")+1;
		
		boolean test_status=true;

		for (String string : list) 
		{
			String[] page_code=string.split("@#");
			ExcelUtil.writeDataExistingSheet(filename, "Text Font", rownum, 0, criteria);
			ExcelUtil.writeDataExistingSheet(filename, "Text Font", rownum, 1, checkpoint);
			ExcelUtil.writeDataExistingSheet(filename, "Text Font", rownum, 2, page_code[0]);
			ExcelUtil.writeDataExistingSheet(filename, "Text Font", rownum, 3, page_code[1]);
			if(page_code[2].length()>32000)
			{
				page_code[2]=page_code[2].substring(0, 32000);
			}
			ExcelUtil.writeDataExistingSheet(filename, "Text Font", rownum, 4, page_code[2]);

			if(page_code.length>3)
			{
				ExcelUtil.writeDataExistingSheet(filename, "Text Font", rownum, 5, page_code[3]);

			}
			
			rownum++;
		}

		if(list.size()==0)
		{
			test_status=false;
		}
		return test_status;
	}
	

}
