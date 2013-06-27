package com.appsolution.omegafi;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AutoPayPaymentDateActivity extends OmegaFiActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auto_pay_payment_date);
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBarCustom.setTitle("PAYMENT DATE");
		actionBar.setCustomView(actionBarCustom);
	}

	

}
