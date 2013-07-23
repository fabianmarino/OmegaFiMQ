package com.appsolution.logic;

import org.json.JSONException;
import org.json.JSONObject;

public class SimpleAnnouncement {

	private int id;
	private String subject;
	private String dateCreate;
	private String description;
	
	public SimpleAnnouncement(JSONObject jsonAnnouncement) {
		try {
			if(jsonAnnouncement!=null){	
					id=jsonAnnouncement.getInt("announcement_id");
					subject=jsonAnnouncement.getString("subject");
					description=jsonAnnouncement.getString("announcement");
					dateCreate=jsonAnnouncement.getString("created_on");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getId() {
		return id;
	}

	public String getSubject() {
		return subject;
	}

	public String getDateCreate() {
		String date=null;
		if(dateCreate!=null){
			if(dateCreate.length()>=10){
				date=CalendarEvent.getFormatDate(3, dateCreate.substring(0, 10), "yyyy/MM/dd");
			}
		}
		return date;
	}

	public String getDescription() {
		return description;
	}
	
	

}
