package com.magic.pages;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;

import com.deque.axe.AXE;

public class ColorContrastChecker
{

	//private static final URL scriptUrl = ColorContrastChecker.class.getClassLoader().getResource("axe.min.js");

	public List<String> getColorContrastResult(WebDriver driver, URL scriptUrl)
	{
		List<String> list = new ArrayList<String>();
		try
		{

			JSONObject responseJSON = new AXE.Builder(driver, scriptUrl).options("{runOnly: { type:'rules',values:['color-contrast']} }").analyze();
					JSONArray violations = responseJSON.getJSONArray("violations");

			for(int i = 0; i<violations.length(); i++)
			{
				JSONObject jsonObject = violations.getJSONObject(i);
				//System.out.println(jsonObject);
				JSONArray jsonarray_nodes = (JSONArray) jsonObject.get("nodes");

				for(int j = 0; j<jsonarray_nodes.length(); j++)
				{

					JSONObject jsonObject2 = jsonarray_nodes.getJSONObject(j);
					String failureSummary = jsonObject2.optString("failureSummary").replaceAll("Fix any of the following:", "").trim();
					//System.out.println(failureSummary);
					String html = jsonObject2.optString("html");
					list.add(failureSummary+"_magic_"+html);
					//System.out.println("color "+failureSummary+"_magic_"+html);
				}
			}
		}
		catch(JSONException e)
		{
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return list;
	}

}
