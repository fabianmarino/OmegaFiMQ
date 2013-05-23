package com.appsolution.omegafi;

import android.os.Bundle;

public class AnnouncementDetailActivity extends OmegaFiActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_announcement_detail);
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setTitle("Announcements");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
	}

}
