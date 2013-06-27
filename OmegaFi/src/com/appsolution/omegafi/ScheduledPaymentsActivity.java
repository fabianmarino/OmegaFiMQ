package com.appsolution.omegafi;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;

public class ScheduledPaymentsActivity extends OmegaFiActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scheduled_payments);
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBarCustom.setTitle("SCHEDULED PAYMENTS");
		actionBar.setCustomView(actionBarCustom);
	}
	
	public void seeScheduleDetails(View button){
		Intent scheduledDetails=new Intent(this, ScheduledPaymentsDetailActivity.class);
		scheduledDetails.putExtra("editable", true);
		startActivity(scheduledDetails);
	}
	
	public void seeScheduleDetailsProcessing(View button){
		Intent scheduledDetails=new Intent(this, ScheduledPaymentsDetailActivity.class);
		scheduledDetails.putExtra("editable", false);
		startActivity(scheduledDetails);
	}


}
