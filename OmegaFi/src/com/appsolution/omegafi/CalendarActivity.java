package com.appsolution.omegafi;

import java.util.ArrayList;

import com.appsolution.layouts.CalendarContentDay;
import com.appsolution.layouts.EventNewsContent;
import com.appsolution.layouts.RowInfoDescription;
import com.appsolution.logic.CalendarDay;
import com.appsolution.logic.CalendarEvent;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.LinearLayout.LayoutParams;

public class CalendarActivity extends OmegaFiActivity {

	private ListView listCalendar;
	private CalendarAdapter adapterCalendar;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);
		listCalendar=(ListView)findViewById(R.id.listCalendarOmegaFi);
		this.chargeCalendarItems();
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBarCustom.setTitle("CALENDAR");
		actionBar.setCustomView(actionBarCustom);
	}
	
	private void completeLinearCalendar(){
		LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 10, 0, 0);
		for (int i = 0; i < 10; i++) {
			CalendarContentDay day=new CalendarContentDay(this);
			day.setLayoutParams(params);
			for (int j = 0; j < 4; j++) {
				RowInfoDescription rowDes=new RowInfoDescription(this);
				rowDes.setInfoDescription("09:30 AM", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do" +
						" eiusmod tempor incididunt ut labore et dolore magna aliqua.");
				day.addRowDescription(rowDes);
			}
//			linearCalendar.addView(day);
		}
	}
	
	public static ArrayList<CalendarDay> ordenateItemsCalendar(ArrayList<CalendarEvent> events){
		ArrayList<CalendarDay> days=new ArrayList<CalendarDay>();
		for (CalendarEvent event:events) {
			if(days.isEmpty()){
				CalendarDay day=new CalendarDay();
				day.addCalendarItem(event);
				days.add(day);
			}
			else{
				CalendarDay back=days.get(days.size()-1);
				Log.d("Result comparate", CalendarEvent.comparateDateOnlyDay(back.getDateDayReal(), event.getBeginDateReal())+"");
				if(CalendarEvent.comparateDateOnlyDay(back.getDateDayReal(), event.getBeginDateReal())==0){
					back.addCalendarItem(event);
				}
				else{
					CalendarDay day=new CalendarDay();
					day.addCalendarItem(event);
					days.add(day);
				}
			}
		}
		return days;
	}
	
	
	private void chargeCalendarItems(){
		AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>() {
			
			private int status=0;
			private ArrayList<CalendarEvent> calendarItems;
			
			@Override
			protected void onPreExecute() {
				startProgressDialog("Charging Calendar...",getResources().getString(R.string.please_wait));
			}
			
			@Override
			protected Boolean doInBackground(Void... params) {
				Object[] statusCalendar=MainActivity.servicesOmegaFi.getHome().getCalendar().getArrayCalendarEvents();
//				Object[] statusCalendar=MainActivity.servicesOmegaFi.getHome().getCalendar().getArrayCalendarEventsTest(getApplicationContext());
				status=(Integer)statusCalendar[0];
				calendarItems=(ArrayList<CalendarEvent>)statusCalendar[1];
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				if(status==200){
					adapterCalendar=new CalendarAdapter(CalendarActivity.this, CalendarActivity.ordenateItemsCalendar(calendarItems));
					listCalendar.setAdapter(adapterCalendar);
				}
				else{
					OmegaFiActivity.showErrorConection(CalendarActivity.this, status, "Not found!");
				}
				stopProgressDialog();
			}
			
		};
		task.execute();
	}
	
	
	private class CalendarAdapter extends BaseAdapter {

		private Activity activity;
		private ArrayList<CalendarDay> days=null;
		
        public CalendarAdapter(Activity activity,ArrayList<CalendarDay> days){
        	this.days=days;
        	this.activity=activity;
        }

		@Override
		public int getCount() {
			return	days.size();
		}

		@Override
		public Object getItem(int arg0) {
			return days.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			CalendarContentDay newContent=null;
			final CalendarDay actualDay=(CalendarDay)getItem(position);
				if(convertView==null){
					convertView=new CalendarContentDay(activity);
					newContent=(CalendarContentDay)convertView;
				}
				else{
					newContent=(CalendarContentDay)convertView;
				}
				newContent.setDayDateEvents(actualDay.getDayOfWeek(), actualDay.getDateDayPretty());
				newContent.setEventsDay(actualDay.getCalendarItems());
				
				return convertView;
		}      
	}
	
	
}