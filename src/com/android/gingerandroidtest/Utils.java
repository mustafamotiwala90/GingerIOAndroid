package com.android.gingerandroidtest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Utils {

	public static String UtilsLogTag = "UTILS_TAG";
	public static String JSON_NAME = "name";
	public static String extraVendorName = "vendor_name";


	public static String descriptionTag = "description";
	public static String startTimeTag = "start_time";

	public static HashMap<String,VendorData> vendorMap = new HashMap<String,VendorData>();
	public static ArrayList<String> idListVendors = new ArrayList<String>();
	public static HashMap<String,Boolean> idChecked = new HashMap<String,Boolean>();

	public static String[] getEventName(JSONObject obj) throws JSONException{

		String[] result = obj.getString(JSON_NAME).split(":");
		String[] detailResult = new String[2];
		if(result.length==1){
			detailResult[0] = obj.getString(JSON_NAME);
			detailResult[1] = "";
			return detailResult;
		}
		String returnStr = result.length>2 ? result[1]+result[2] : result[1];
		String restaurantName = returnStr.substring(0, returnStr.indexOf("("));
		String eventName = returnStr.substring(returnStr.indexOf("(")+1, returnStr.indexOf(")"));
		Log.d(UtilsLogTag, "restaurantName : "+restaurantName);
		Log.d(UtilsLogTag, "eventName : "+eventName);
		detailResult[0] = restaurantName;
		detailResult[1] = eventName;
		return detailResult;
	}

	public static Date getDate(String dateString) throws ParseException{
		String tempDateString = dateString.substring(0,dateString.indexOf("T"));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
		Date date = formatter.parse(tempDateString);
		return date;
	}

	public static boolean checkIfIdExists(String name){
		if(idChecked.containsKey(name))
			return true;
		return false;
	}

	public static void addToIdMap(String name){
		idChecked.put(name,Boolean.TRUE);
	}

	public static List<VendorData> sortVendorMap(){
		List<VendorData> sortedList = (List<VendorData>) vendorMap.values();
		Collections.sort(sortedList,new VendorComparator());

		return sortedList;
	}

	public static class VendorComparator implements Comparator<VendorData>
	{
		@Override
		public int compare(VendorData c1, VendorData c2)
		{
			return c1.getPastEvents().get(0).compareTo(c2.getPastEvents().get(0));
		}
	}

	public static void addToVendorMap(String name,Date date){
		VendorData vendor;
		if(name.trim().length()== 0|| name.length()>40)
			return;

		if(vendorMap.containsKey(name)){
			vendor = vendorMap.get(name);
			vendor.setPastEvents(date);
		}else{
			vendor = new VendorData(name);
			vendor.setPastEvents(date);
			vendorMap.put(name,vendor);
		}
	}

	public static ArrayList<String> parseJsonFromString(String response)
			throws JSONException, ParseException {
		ArrayList<String> vendorData = new ArrayList<String>();
		JSONObject jsonObj = new JSONObject(response);
		String lines[] = jsonObj.getString(descriptionTag).split("\\r?\\n");
		Date date = Utils.getDate(jsonObj.getString(startTimeTag));
		int startCount = 0;
		for (int i = 2; i < lines.length; i++) {
			if (lines[i].endsWith(":") || lines[i].contains("Lineup:")) {
				startCount = i;
				break;
			}
		}

		for (int j = startCount + 1; j < lines.length; j++) {
			if (lines[j].length() == 0)
				break;
			String name = lines[j].replaceAll("\\s+", " ").trim();
			addToVendorMap(name, date);
			vendorData.add(name);
		}
		return vendorData;
	}


}
