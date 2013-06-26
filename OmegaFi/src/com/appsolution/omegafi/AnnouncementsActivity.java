package com.appsolution.omegafi;

import com.appsolution.layouts.ContentAnnouncement;
import com.appsolution.layouts.EventNewsContent;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class AnnouncementsActivity extends OmegaFiActivity {

	private LinearLayout linearAnnouncements;
	private OnClickListener listener;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_announcements);
		listener=new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent detailAnnouncement=new Intent(getApplication(), AnnouncementDetailActivity.class);
				startActivity(detailAnnouncement);
			}
		};
		linearAnnouncements=(LinearLayout)findViewById(R.id.linearContentAnnouncements);
		this.completeAnnouncements();
	}

	@Override
	protected void optionsActionBar() {
		actionBar.setTitle("Announcements");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
	}
	
	private void completeAnnouncements(){
		int padding=(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
		for (int i = 0; i < 13; i++){
			ContentAnnouncement announcement=new ContentAnnouncement(getApplicationContext());
			if(i<3){
				announcement.setBackgroundNewAnnoncement();
			}
			announcement.setTitleAnnouncement("Subjects goes here");
			announcement.setDateAnnouncement("04/12/2013");
			announcement.setDescriptionAnnouncement("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod " +
					"tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris.");
			announcement.setOnClickListener(listener);
			linearAnnouncements.addView(announcement);
		}
	}

}
