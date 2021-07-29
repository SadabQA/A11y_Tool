package com.pearson.utils;

import java.io.File;
import java.util.ArrayList;

public class FileHandling {
	
/*File Handling*/
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ArrayList getfileNamesFromFolder(String path) 
	{
		ArrayList listOfFilesArray = new ArrayList<>();
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) 
		{
			if(listOfFiles[i].isFile()) 
			{
				System.out.println("File " + listOfFiles[i].getName());
				listOfFilesArray.add(listOfFiles[i].getName());
			}
			else if(listOfFiles[i].isDirectory()) 
			{
				System.out.println("Directory " + listOfFiles[i].getName());
			}
		}

		System.out.println(listOfFilesArray);
		return listOfFilesArray;

	}
	
	public void deletefilesfromfolder(String filepath)
	{
		File file=new File(filepath);
		String[] entries = file.list();
		for(String s: entries)
		{
			File currentFile = new File(file.getPath(),s);
			currentFile.delete();
		}
	}

}
