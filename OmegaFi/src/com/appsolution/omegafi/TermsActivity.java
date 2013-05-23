package com.appsolution.omegafi;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TermsActivity extends OmegaFiActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_terms);
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setTitle("Terms of Use");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
	}

}
