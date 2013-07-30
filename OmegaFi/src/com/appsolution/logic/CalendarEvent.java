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
	private String endDate;
	private String linkUrl=null;
	private String source;
	private String beginTime;
	private String endTime;
	
	
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
			
			if(!json.isNull("event_begin_date"))
				beginDate=json.getString("event_begin_date");
			
			if(!json.isNull("event_end_date"))
				endDate=json.getString("event_end_date");
			
			if(!json.isNull("event_begin_time"))
				beginTime=json.getString("event_begin_time");
			
			if(!json.isNull("event_end_time"))
				endTime=json.getString("event_end_time");
			
			if(!json.isNull("event_source"))
				source=json.getString("event_source");
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
			stringFormat=new SimpleDateFormat("MMMM dd, yyyy");
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
		case 6:
			stringFormat=new SimpleDateFormat("yyyy/MM/dd");
			fechaAux=stringFormat.format(dateFecha);
			break;
		case 7:
			stringFormat=new SimpleDateFormat("hh:mm a");
			fechaAux=stringFormat.format(dateFecha);
			fechaAux=fechaAux.replace("a.m.", "AM").replace("p.m.","PM");
			break;
		case 8:
			stringFormat=new SimpleDateFormat("EEE");
			fechaAux=stringFormat.format(dateFecha);
			fechaAux = fechaAux.substring(0, 1).toUpperCase() + fechaAux.substring(1, fechaAux.length());
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

	public String getEndDate() {
		return endDate;
	}

	public String getSource() {
		return source;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public String getEndTime() {
		return endTime;
	}
	
/**
 * 
 * @param date1
 * @return 1 si la fecha1 es menor que la fecha2
 * @return 0 si las fechas son iguales
 * @return -1 si la fecha1 es mayor que la fecha2
 */
	public static int comparateDateOnlyDay(Date date1, Date date2){
		Date aux1=new Date(date1.getYear(),date1.getMonth(),date1.getDate());
		Date aux2=new Date(date2.getYear(),date2.getMonth(),date2.getDate());

		return aux1.compareTo(aux2);
	}
	
	public static int comparateDateOnlyHour(Date date1, Date date2){
		int equals=1;
		Date aux1=new Date(date1.getHours(),date1.getMinutes(),date1.getDate(),date1.getHours(),date1.getMinutes());
		Date aux2=new Date(date2.getHours(),date2.getMinutes(),date2.getDate(),date2.getHours(),date2.getMinutes());
		return aux1.compareTo(aux2);
	}
	
	public Date getBeginDateReal(){
		if(beginDate!=null){
			return CalendarEvent.getDateFromString(beginDate.substring(0, 10), "yyyy/MM/dd");
		}
		else{
			return null;
		}
	}
	
	public Date getEndDateReal(){
		if(endDate!=null){
			return CalendarEvent.getDateFromString(endDate.substring(0, 10), "yyyy/MM/dd");
		}
		else{
			return null;
		}
	}
	
	public Date getBeginTimeReal(){
		if(beginTime!=null){
			return CalendarEvent.getDateFromString(beginTime.substring(0, 19), "yyyy/MM/dd hh:mm:ss");
		}
		else{
			return null;
		}
	}
	

	public Date getEndTimeReal(){
		if(endTime!=null){
			return CalendarEvent.getDateFromString(endTime.substring(0, 19), "yyyy/MM/dd hh:mm:ss");
		}
		else{
			return null;
		}
	}


	public String getBeginTimeHourDayAMPM(){
		String begin=null;
		if(beginTime!=null){
			if (beginTime.length()>=19) {
				begin=CalendarEvent.getFormatDate(7, beginTime.substring(0, 19), "yyyy/MM/dd hh:mm:ss");
			}
		}
		return begin;
	}
	
	public String getEndTimeHourDayAMPM(){
		String end=null;
		if(endTime!=null){
			if (endTime.length()>=19) {
				end=CalendarEvent.getFormatDate(7, endTime.substring(0, 19), "yyyy/MM/dd hh:mm:ss");
			}
		}
		return end;
	}

}