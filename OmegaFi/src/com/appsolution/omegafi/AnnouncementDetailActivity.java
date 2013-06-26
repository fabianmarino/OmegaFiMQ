package com.appsolution.omegafi;

import com.appsolution.layouts.ContentAnnouncement;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class AnnouncementDetailActivity extends OmegaFiActivity {

	private ContentAnnouncement announcement;
	private TextView textSource;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_announcement_detail);
		announcement=(ContentAnnouncement)findViewById(R.id.contentAnnouncementDetail);
		textSource=(TextView)findViewById(R.id.textSourceAnnouncementActivity);
		announcement.setDescriptionAnnouncement("Lorem ipsum dolor sit amet, consectetur adipisicing"+ 
           " elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim"+ 
           " ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea"+ 
            "commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse"+
             "cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident,"+
              "sunt in culpa qui officia deserunt mollit anim id est laborum.");
		announcement.setBackgroundResource(R.drawable.background_white_pure);
		textSource.setText(Html.fromHtml("<b>Source: </b>Lorem ipsum dolor"));
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setTitle("Announcements");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
	}

}
