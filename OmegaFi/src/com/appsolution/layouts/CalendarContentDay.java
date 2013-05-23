package com.appsolution.layouts;

import com.appsolution.omegafi.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class CalendarContentDay extends LinearLayout {
	
	private RowInformation titleContentDay;
	private LinearLayout linearContentDay;
	
	public CalendarContentDay(Context context){
		super(context);
		this.initialize();
	}

	public CalendarContentDay(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initialize();
		
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CalendarContentDay);
		
		String dayEvents=a.getString(R.styleable.CalendarContentDay_day_event_calendar);
		setDayEvents(dayEvents);
		
		String dateEvents=a.getString(R.styleable.CalendarContentDay_date_event_calendar);
		setDateEvents(dateEvents);
		
		a.recycle();
	}
	
	private void initialize(){
		LayoutInflater inflate= (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflate.inflate(R.layout.calendar_content_day, this, true);
		titleContentDay=(RowInformation)findViewById(R.id.rowTitleCalendarContent);
		titleContentDay.setVisibleArrow(false);
		linearContentDay=(LinearLayout)findViewById(R.id.linearContentEventsDay);
	}
	
	public void setDayEvents(String day){
		titleContentDay.setNameInfo(day);
	}
	
	public void setDateEvents(String date){
		titleContentDay.setValueInfo(date);
	}
	
	public void setDayDateEvents(String day, String date){
		setDayEvents(day);
		setDateEvents(date);
	}
	
	public void addRowDescription(RowInfoDescription rowDesc){
		linearContentDay.addView(rowDesc);
	}

}
