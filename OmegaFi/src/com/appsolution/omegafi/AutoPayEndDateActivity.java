package com.appsolution.omegafi;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AutoPayEndDateActivity extends OmegaFiActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auto_pay_end_date);
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBarCustom.setTitle("END DATE");
		actionBar.setCustomView(actionBarCustom);
	}

	

}
