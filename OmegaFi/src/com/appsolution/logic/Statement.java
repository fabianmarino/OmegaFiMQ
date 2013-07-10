package com.appsolution.logic;

import org.json.JSONException;
import org.json.JSONObject;

public class Statement {

	private int id;
	private String dateClose; 
	
	public Statement(JSONObject statement) {
		try {
			id=statement.getInt("statement_id");
			dateClose=statement.getString("closing_date");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getId() {
		return id;
	}

	public String getDateClose() {
		if(dateClose.length()>=10){
			return CalendarEvent.getFormatDate(0, dateClose.substring(0, 10), "yyyy-MM-dd");
		}
		else{
			return null;
		}
	}
	
	

}
