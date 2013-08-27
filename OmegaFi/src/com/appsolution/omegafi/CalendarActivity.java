package com.appsolution.omegafi;

import java.util.ArrayList;
import com.appsolution.layouts.CalendarContentDay;
import com.appsolution.logic.CalendarDay;
import com.appsolution.logic.CalendarEvent;
import com.appsolution.services.Server;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

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
				startProgressDialog("Loading Calendar...",getResources().getString(R.string.please_wait));
			}
			
			@Override
			protected Boolean doInBackground(Void... params) {
				Object[] statusCalendar=Server.getServer().getHome().getCalendar().getArrayCalendarEvents();
//				Object[] statusCalendar=MainActivity.servicesOmegaFi.getHome().getCalendar().getArrayCalendarEventsTest(getApplicationContext());
				status=(Integer)statusCalendar[0];
				calendarItems=(ArrayList<CalendarEvent>)statusCalendar[1];
				Server.getServer().getHome().getProfile().updateProfileIfNecessary();
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				if(Server.isStatusOk(status)){
					adapterCalendar=new CalendarAdapter(CalendarActivity.this, CalendarActivity.ordenateItemsCalendar(calendarItems));
					listCalendar.setAdapter(adapterCalendar);
				}
				else{
					OmegaFiActivity.showErrorConection(CalendarActivity.this, status, getResources().getString(R.string.object_not_found),false);
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