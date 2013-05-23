package com.appsolution.omegafi;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AutoPayEndDateActivity extends OmegaFiActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auto_pay_end_date);
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setTitle("End Date");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
	}

	

}
