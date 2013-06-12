package com.appsolution.omegafi;

import android.os.Bundle;

public class PrivacyActivity extends OmegaFiActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_privacy);
	}

	
	@Override
	protected void optionsActionBar() {
		actionBar.setTitle("Privacy Policy");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
	}

}
