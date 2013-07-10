package com.appsolution.logic;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

public class CalendarService extends ServerContext {

	private ArrayList<CalendarEvent> listEvents;
	
	public CalendarService(Server server) {
		super(server);
		listEvents=new ArrayList<CalendarEvent>();
	}
	
	public Object[] chargeEvents(){
		Object[] response=server.makeRequestGetJSONArray(Server.CALENDAR_SERVICE);
		JSONArray array=(JSONArray)response[1];
		if(array!=null){
			for (int i = 0; i < 6 && i < array.length(); i++) {
				try {
					listEvents.add(new CalendarEvent(array.getJSONObject(i).getJSONObject("calendar_event")));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return response;
	}

	public ArrayList<CalendarEvent> getListEvents() {
		return listEvents;
	}
	
	public boolean isEmpty(){
		return listEvents.isEmpty();
	}
	

}
