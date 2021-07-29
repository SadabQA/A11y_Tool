package com.pearson.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.yaml.snakeyaml.parser.ParserImpl;

public class ExcelUtil {   

	public static String TEST_DATA_SHEET_LOCATION = "./src/test/resources/testdata";

	private static Workbook wb;
	private static Sheet sh;
	private static Row rw;

	public static Object[][] getData(String sheetName) throws InvalidFormatException {

		Object data[][] = null;      

		try {
			FileInputStream ip = new FileInputStream(TEST_DATA_SHEET_LOCATION);
			wb = WorkbookFactory.create(ip);
			sh = wb.getSheet(sheetName);
			data = new Object[sh.getLastRowNum()][sh.getRow(0).getLastCellNum()];

			for (int i = 0; i < sh.getLastRowNum(); i++) {
				for (int j = 0; j < sh.getRow(0).getLastCellNum(); j++) {
					data[i][j] = sh.getRow(i+1).getCell(j).toString();
				}
			}  

		}   
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (NullPointerException e) {
			e.printStackTrace();
		}

		return data;

	}
	public static void createExcelFile(String folderNameAndfileName,String sheetName) throws IOException, InterruptedException
	{
		Thread.sleep(500);
		FileOutputStream fileOutputStream = new FileOutputStream(new File("./"+folderNameAndfileName+".xlsx"));
		wb = new XSSFWorkbook();
		sh = wb.createSheet(sheetName);
		wb.write(fileOutputStream);
		fileOutputStream.flush();
		fileOutputStream.close();
	}
	
	public static void createSheetInExcel(String filename,String sheetName) throws IOException, InterruptedException
	{
		Thread.sleep(500);
		//FileOutputStream fileOutputStream = new FileOutputStream(new File("./result/"+filename+".xlsx"));
		wb = new XSSFWorkbook(new FileInputStream(filename+".xlsx"));
		sh = wb.createSheet(sheetName);
		FileOutputStream fos= new FileOutputStream(filename+".xlsx");
		wb.write(fos);
	}
	
	public static void writeDataExistingSheet(String filename, String sheetname, int rownum, int column, String value) throws FileNotFoundException, IOException, NullPointerException
	{
		try
		{
			wb=new XSSFWorkbook(new FileInputStream(filename+".xlsx"));
			sh=wb.getSheet(sheetname);

			sh.getRow(rownum).createCell(column).setCellValue(value);


			FileOutputStream fos= new FileOutputStream(filename+".xlsx");
			wb.write(fos);
		}
		catch(Exception e)
		{

			sh.createRow(rownum).createCell(column).setCellValue(value);

			FileOutputStream fos= new FileOutputStream(filename+".xlsx");
			wb.write(fos);
			//e.printStackTrace();
			//log.error("error writing data to file "+filename+ "and sheetname "+sheetname+ " "+e);
		}
	}
	private static boolean isNumber(String str) 
	{

		String regex = "[0-9]+"; 


		Pattern p = Pattern.compile(regex); 

		if (str == null) 
		{ 
			return false; 
		}
		else 
		{

			Matcher m = p.matcher(str);
			return m.matches(); 
		} 

	}
	public static void generateheader(String filename, String sheetname,int rowNum, String multipleHeaderUseComma) throws FileNotFoundException, IOException
	{
		wb=new XSSFWorkbook(new FileInputStream(filename+".xlsx"));
		sh=wb.getSheet(sheetname);
		try {
			rw=sh.createRow(rowNum);

		} catch (Exception e) 
		{
			rw=sh.getRow(rowNum);
			e.printStackTrace();
		}

		CellStyle style = wb.createCellStyle();//Create style
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		Font font = wb.createFont();//Create font
		font.setColor(IndexedColors.BLACK.index);
		font.setBold(true);//Make font bold
		style.setFont(font);//set it to bold
		String[] headers = multipleHeaderUseComma.split(",");
		for (int i = 0; i < headers.length; i++) 
		{
			rw.createCell(i).setCellStyle(style);
			rw.getCell(i).setCellValue(headers[i]);
		}
		for(int i = 0; i<headers.length; i++)
		{
			sh.autoSizeColumn(i);
		}
		FileOutputStream fos= new FileOutputStream(filename+".xlsx");
		wb.write(fos);
		fos.close();
	}
	public static int getRowCount(String fileName, String sheet) throws IOException
	{
		int numOFRows = 0;
		try
		{
			File file = new File(fileName+".xlsx");
			FileInputStream fileInputStream = new FileInputStream(file);
			ZipSecureFile.setMinInflateRatio(-1.0d);
			@SuppressWarnings("resource")
			Workbook wb = new XSSFWorkbook(fileInputStream);
			numOFRows = wb.getSheet(sheet).getLastRowNum();
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
		}
		catch(EncryptedDocumentException e)
		{
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return (numOFRows);
	}

	public static void writeDataExistingSheetOneRowMultipleColumn(String filename, String sheetname, int rownum, List<String> valueList) throws FileNotFoundException, IOException, NullPointerException
	{
		try
		{
			wb=new XSSFWorkbook(new FileInputStream(filename+".xlsx"));
			sh=wb.getSheet(sheetname);
			sh.getRow(rownum);
			for (int i = 0; i < valueList.size(); i++) 
			{
				sh.getRow(rownum).createCell(i).setCellValue(valueList.get(i));

			}	
			FileOutputStream fos= new FileOutputStream(filename+".xlsx");
			wb.write(fos);
		}
		catch(Exception e)
		{
			sh.getRow(rownum);
			for (int i = 0; i < valueList.size(); i++) 
			{
				sh.getRow(rownum).createCell(i).setCellValue(valueList.get(i));

			}	
			FileOutputStream fos= new FileOutputStream(filename+".xlsx");
			wb.write(fos);
			//e.printStackTrace();
			//log.error("error writing data to file "+filename+ "and sheetname "+sheetname+ " "+e);
		}

	}
	@SuppressWarnings("deprecation")
	public static Map<Integer, String> readColumnData(String filename, String sheetname, String columnname) throws Exception
	{

		wb = new XSSFWorkbook(new FileInputStream(filename+".xlsx"));
		sh = wb.getSheet(sheetname);
		int lastrow = sh.getLastRowNum();
		int counter = 0;
		int column = 0;
		boolean flag = false;
		rw = sh.getRow(0);
		int lastcellno = rw.getLastCellNum();
		for(int j = 0; j<lastcellno; j++)
		{
			if(rw.getCell(j)==null)
			{
				//System.out.println("Cell is Empty in Column:" + columnname);

			}
			else if(rw.getCell(j).getStringCellValue().equalsIgnoreCase(columnname))
			{
				flag = true;
				column = j;
				break;
			}
			/*if(rw.getCell(j).getStringCellValue().equalsIgnoreCase(columnname))
			{
				flag=true;
				column=j;
				break;
			}*/
		}

		if(flag)
		{
			Map<Integer, String> values = new LinkedHashMap<Integer, String>();
			for(int i = 1; i<=lastrow; i++)
			{

				try
				{
					if(sh.getRow(i).getCell(column).getCellType()==CellType.STRING)
					{
						String assetName = sh.getRow(i).getCell(column).getStringCellValue();
						values.put(counter, assetName);
						//System.out.println(String.valueOf(assetName));

					}
					else if(sh.getRow(i).getCell(column).getCellType()==CellType.NUMERIC)
					{
						if(DateUtil.isCellDateFormatted(sh.getRow(i).getCell(column)))
						{
							SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
							String datanew = sdf.format(sh.getRow(i).getCell(column).getDateCellValue());
							//System.out.println(datanew);
							values.put(counter, String.valueOf(datanew));

						}
						else
						{
							double ndata = sh.getRow(i).getCell(column).getNumericCellValue();
							if(ndata%1==0)
							{
								long datanew = (long) ndata;
								String data = String.valueOf(datanew);
								values.put(counter, String.valueOf(data));
							}
							else
							{
								values.put(counter, String.valueOf(ndata));
							}
						}
					}
					else if(sh.getRow(i).getCell(column).getCellType()==CellType.BLANK)
					{
						values.put(counter, sh.getRow(i).getCell(column).getStringCellValue());
					}
					else
					{
						values.put(counter, "");
					}
				}
				catch(Exception e)
				{
					values.put(counter, "");
					//e.printStackTrace();

				}
				counter++;
			}
			wb.close();
			return values;

		}
		else
		{
			throw new Exception(columnname+" not found in excel "+filename+" and "+sheetname);

		}

	}
	@SuppressWarnings("deprecation")
	public static Map<Integer, List<String>> readAllData(String filename, String sheetname, int startrow) throws FileNotFoundException, IOException, InterruptedException
	{
		Map<Integer, List<String>> alldata = new HashMap<Integer, List<String>>();
		wb = new XSSFWorkbook(new FileInputStream(filename+".xlsx"));
		sh = wb.getSheet(sheetname);
		int lastrow = sh.getLastRowNum();
		int counter = 0;
		for(int i = startrow; i<=lastrow; i++)
		{
			List<String> values = new ArrayList<String>();
			int cellno = sh.getRow(i).getLastCellNum();
			for(int j = 0; j<cellno; j++)
			{
				if(sh.getRow(i).getCell(j)!=null)
				{
					if(sh.getRow(i).getCell(j).getCellType()==CellType.STRING)
					{
						values.add(sh.getRow(i).getCell(j).getStringCellValue());
					}
					else if(sh.getRow(i).getCell(j).getCellType()==CellType.NUMERIC)
					{
						if(DateUtil.isCellDateFormatted(sh.getRow(i).getCell(j)))
						{
							SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
							String date = sdf.format(sh.getRow(i).getCell(j).getDateCellValue());
							System.out.println(date);
							values.add(date);
						}
						else
						{
							double data = sh.getRow(i).getCell(j).getNumericCellValue();
							if(data%1==0)
							{
								long datanew = (long) data;
								values.add(String.valueOf(datanew));
							}
							else
							{
								values.add(String.valueOf(data));
							}
						}
					}
					else if(sh.getRow(i).getCell(j).getCellType()==CellType.BLANK)
					{
						values.add(sh.getRow(i).getCell(j).getStringCellValue());
					}
					else if(sh.getRow(i).getCell(j).getCellType()==CellType.BLANK)
					{
						values.add(sh.getRow(i).getCell(j).getStringCellValue());
					}
				}
				else
				{
					values.add("");
				}
			}

			alldata.put(counter, values);
			counter++;

		}
		wb.close();
		return alldata;
	}
}
