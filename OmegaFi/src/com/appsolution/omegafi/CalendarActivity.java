package com.appsolution.omegafi;

import com.appsolution.layouts.CalendarContentDay;
import com.appsolution.layouts.RowInfoDescription;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class CalendarActivity extends OmegaFiActivity {

	private LinearLayout linearCalendar;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);
		linearCalendar=(LinearLayout)findViewById(R.id.linearContentCalendar);
		this.completeLinearCalendar();
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
			day.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent i = new Intent(Intent.ACTION_VIEW,  Uri.parse("http://omegafi.com"));
					CalendarActivity.this.startActivity(i);
				}
			});
			linearCalendar.addView(day);
		}
	}

}
