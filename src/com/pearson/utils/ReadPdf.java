package com.pearson.utils;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

public class ReadPdf 
{

	public static String read(String filename) throws IOException
	{
		String text=null;
		
		try (PDDocument document = PDDocument.load(new File(filename)))
		{
			//document.getClass();
			if (!document.isEncrypted()) 
			{
                
				PDFTextStripperByArea stripper = new PDFTextStripperByArea();
				stripper.setSortByPosition(true);
				PDFTextStripper tStripper = new PDFTextStripper();
			    tStripper.setStartPage(1);
			    tStripper.setEndPage(2);
				text = tStripper.getText(document);
				//System.out.println("Text:" + st);

				// split by whitespace	
			}
		}
		return text;

	}
	
	
	public static String readlastPage(String filename) throws IOException
	{
		String text=null;
		
		try (PDDocument document = PDDocument.load(new File(filename+".pdf")))
		{
			//document.getClass();
			if (!document.isEncrypted()) 
			{
                
				PDFTextStripperByArea stripper = new PDFTextStripperByArea();
				stripper.setSortByPosition(true);
				PDFTextStripper tStripper = new PDFTextStripper();
				tStripper.setStartPage(document.getPages().getCount());
			    tStripper.setEndPage(document.getPages().getCount());
				 text = tStripper.getText(document);
				//System.out.println("Text:" + st);

				// split by whitespace	
			}
		}
		return text;

	}
	
	
}