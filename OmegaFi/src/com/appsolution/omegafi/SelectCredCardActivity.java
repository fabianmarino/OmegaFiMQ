package com.appsolution.omegafi;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SelectCredCardActivity extends OmegaFiActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_cred_card);
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBarCustom.setTitle("SELECT CREDIT CARD");
		actionBar.setCustomView(actionBarCustom);
	}

}
