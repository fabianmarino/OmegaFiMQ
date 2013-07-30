package com.appsolution.logic;

import java.util.ArrayList;
import java.util.Date;

public class CalendarDay {

	private String dateDay;
	private ArrayList<CalendarEvent> calendarItems;
	
	public CalendarDay() {
	calendarItems=new ArrayList<CalendarEvent>();
	dateDay=null;
	}
	
	public void addCalendarItem(CalendarEvent calendar){
		if(calendarItems.isEmpty())
			dateDay=calendar.getBeginDate();
		calendarItems.add(calendar);
	}

	public String getDateDay() {
		return dateDay;
	}
	
	public Date getDateDayReal() {
		if(dateDay!=null){
			return CalendarEvent.getDateFromString(dateDay.substring(0, 10), "yyyy/MM/dd");
		}
		else{
			return null;
		}
	}

	public ArrayList<CalendarEvent> getCalendarItems() {
		return calendarItems;
	}
	
	public String getDayOfWeek(){
		String dayBegin=null;
		if(dateDay!=null){
			if (dateDay.length()>=10) {
				dayBegin=CalendarEvent.getFormatDate(8, dateDay.substring(0, 10), "yyyy/MM/dd");
			}
		}
		return dayBegin;
	}
	
	public String getDateDayPretty(){
		String dateBegin=null;
		if(dateDay!=null){
			if (dateDay.length()>=10) {
				dateBegin=CalendarEvent.getFormatDate(0, dateDay.substring(0, 10), "yyyy/MM/dd");
			}
		}
		return dateBegin;
	}

}
