package com.appsolution.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import nl.matshofman.saxrssreader.RssItem;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class CalendarEvent {

	private int id;
	private String title;
	private String description;
	private String beginDate;
	private String linkUrl=null;
	
	public CalendarEvent(){
		id=0;
		title="Title Calendar Event";
		beginDate="04/03/2014";
		description="Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. ";
		linkUrl=null;
	}
	
	public CalendarEvent(JSONObject json) {
		try {
		if(json!=null){
			id=json.getInt("event_id");
			title=json.getString("event_title");
			if(json.isNull("event_description")){
				description="";
			}
			else{
				description=json.getString("event_description");
			}
			beginDate=json.getString("event_begin_date");
		}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public CalendarEvent(RssItem item) {
		if(item!=null){
			id=-1;
			title=item.getTitle();
			description=item.getDescription();
			beginDate="";
			linkUrl=item.getLink();
		}
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getBeginDate() {
		return beginDate;
	}
	
	public static Date getDateFromString(String date, String format){
		SimpleDateFormat dateFormat=new SimpleDateFormat(format);
		SimpleDateFormat stringFormat=null;
		Date dateFecha=null;
		try {
			dateFecha=dateFormat.parse(date);
			dateFecha.setSeconds(1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dateFecha;
	}
	
	public static String getFormatDate(int type,String fecha,String format){
		String fechaAux=fecha;
		SimpleDateFormat dateFormat=new SimpleDateFormat(format);
		SimpleDateFormat stringFormat=null;
		Date dateFecha=null;
		try {
			dateFecha=dateFormat.parse(fecha);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch (type) {
		case 0:
			stringFormat=new SimpleDateFormat("MMMM dd,yyyy");
			fechaAux=stringFormat.format(dateFecha);
			fechaAux = fechaAux.substring(0, 1).toUpperCase() + fechaAux.substring(1, fechaAux.length());
			break;
		case 1:
			stringFormat=new SimpleDateFormat("dd/MM/yyyy");
			fechaAux=stringFormat.format(dateFecha);
			break;
		case 3:
			stringFormat=new SimpleDateFormat("MM/dd/yyyy");
			fechaAux=stringFormat.format(dateFecha);
			break;
		case 4:
			stringFormat=new SimpleDateFormat("MMMM yyyy");
			fechaAux=stringFormat.format(dateFecha);
			fechaAux = fechaAux.substring(0, 1).toUpperCase() + fechaAux.substring(1, fechaAux.length());
			break;
		case 5:
			stringFormat=new SimpleDateFormat("yyyy-MM-dd");
			fechaAux=stringFormat.format(dateFecha);
			break;
		default:
			break;
		}
		return fechaAux;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	
	

}
