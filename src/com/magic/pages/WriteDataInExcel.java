package com.magic.pages;

import java.io.IOException;
import java.util.List;

import com.pearson.utils.ExcelUtil;



public class WriteDataInExcel 
{

	public boolean writingDataForFailureInstance(String criteria,String checkpoint,List<String> list,String rationale,String recommendation,String sheetName) throws IOException 
	{
		String filename="./result/MHE_Results";
		int rownum = ExcelUtil.getRowCount(filename, sheetName)+1;
		boolean test_status=true;

		for (String string : list) {

			String[] page_code=string.split("@#");
			ExcelUtil.writeDataExistingSheet(filename, sheetName, rownum, 0, criteria);
			ExcelUtil.writeDataExistingSheet(filename, sheetName, rownum, 1, checkpoint);
			ExcelUtil.writeDataExistingSheet(filename, sheetName, rownum, 2, page_code[0]);
			ExcelUtil.writeDataExistingSheet(filename, sheetName, rownum, 3, page_code[1]);
			ExcelUtil.writeDataExistingSheet(filename, sheetName, rownum, 4, page_code[2]);
			if(page_code[2].length()>32000)
			{
				page_code[2]=page_code[2].substring(0, 32000);
			}
			ExcelUtil.writeDataExistingSheet(filename, sheetName, rownum, 5, rationale);
			ExcelUtil.writeDataExistingSheet(filename, sheetName, rownum, 6, recommendation);

			rownum++;
		}

		if(list.size()==0)
		{
			test_status=false;
		}
		return test_status;
	}

	public void writingDataForDataExtraction(String sheetname,String criteria,String checkpoint,List<String> list,String message) throws IOException 
	{
		String filename="./result/MHE_Results";
		int rownum = ExcelUtil.getRowCount(filename, sheetname)+1;
		boolean test_status=true;

		for (String string : list) 
		{
			String[] page_code=string.split("@#");
			ExcelUtil.writeDataExistingSheet(filename, sheetname, rownum, 0, criteria);
			ExcelUtil.writeDataExistingSheet(filename, sheetname, rownum, 1, checkpoint);
			ExcelUtil.writeDataExistingSheet(filename, sheetname, rownum, 2, page_code[0]);
			ExcelUtil.writeDataExistingSheet(filename, sheetname, rownum, 3, page_code[1]);
			if(page_code[2].length()>32000)
			{
				page_code[2]=page_code[2].substring(0, 32000);
			}
			ExcelUtil.writeDataExistingSheet(filename, sheetname, rownum, 4, page_code[2]);

			if(page_code.length>3)
			{
				ExcelUtil.writeDataExistingSheet(filename, sheetname, rownum, 5, page_code[3]);

			}
			
			ExcelUtil.writeDataExistingSheet(filename,sheetname, rownum, 6, message);
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
			
			ExcelUtil.writeDataExistingSheet(filename, "printPageNumbers", rownum, 0, Criteria);
			ExcelUtil.writeDataExistingSheet(filename, "printPageNumbers", rownum, 1, checkpoint);
			ExcelUtil.writeDataExistingSheet(filename, "printPageNumbers", rownum, 2, page_code[0]);
			
			for (int i = 1; i < page_code.length; i++) 
			{
				ExcelUtil.writeDataExistingSheet(filename, "printPageNumbers", rownum, (2+i), page_code[i]);
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
