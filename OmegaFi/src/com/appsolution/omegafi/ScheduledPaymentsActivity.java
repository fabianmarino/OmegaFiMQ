package com.appsolution.omegafi;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;

public class ScheduledPaymentsActivity extends OmegaFiActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scheduled_payments);
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setTitle("Scheduled Payments");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
	}
	
	public void seeScheduleDetails(View button){
		Intent scheduledDetails=new Intent(this, ScheduledPaymentsDetailActivity.class);
		startActivity(scheduledDetails);
	}


}
