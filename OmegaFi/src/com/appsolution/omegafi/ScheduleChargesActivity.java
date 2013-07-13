package com.appsolution.omegafi;

import com.appsolution.layouts.CycleCharge;
import com.appsolution.layouts.RowInformation;
import com.appsolution.logic.BillingCycle;
import com.appsolution.logic.ScheduledOfCharges;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class ScheduleChargesActivity extends OmegaFiActivity {

	private RowInformation rowInterval;
	private LinearLayout linearCycles;
	private int idAccount;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule_charges);
		rowInterval=(RowInformation)findViewById(R.id.rowTotalAllCycles);
		rowInterval.setVisibility(View.INVISIBLE);
		linearCycles=(LinearLayout)findViewById(R.id.linearCyclesCharges);
		idAccount=getIntent().getExtras().getInt("id");
		this.chargeScheduledCharges();
	}

	@Override
	protected void optionsActionBar() {
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBarCustom.setTitle("SCHEDULED OF CHARGES");
		actionBar.setCustomView(actionBarCustom);
	}
	
	private void completeCyclesCharges(ScheduledOfCharges scheduled){
		if(scheduled!=null){
			rowInterval.setVisibility(View.VISIBLE);
			rowInterval.setNameInfo(scheduled.getInterval());
			rowInterval.setValueInfo("$"+scheduled.getGrandTotal());
			for (BillingCycle cycleBilling:scheduled.getBillingCycles()) {
				CycleCharge cycle=new CycleCharge(this);
				LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				params.setMargins(0, 0, 0, 10);
				cycle.setLayoutParams(params);
				cycle.setNamesInfo("Cycle "+cycleBilling.getCycleNumber(), "Billed: "+cycleBilling.getDateBillOn(),
						"Due: "+cycleBilling.getDateDueOn());
				cycle.setValueInfo("$"+cycleBilling.getTotalAmount());
				cycle.setListCharges(cycleBilling.getListCharges());
				linearCycles.addView(cycle);
			}
		}
	}
	
	private void chargeScheduledCharges(){
		AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>() {
			
			int status=0;
			private ScheduledOfCharges scheduled=null; 
			
			@Override
			protected void onPreExecute() {
				startProgressDialog("Scheduled of Charges", "Charging cycles...");
			}
			
			@Override
			protected Boolean doInBackground(Void... params) {
				Object[] statusScheduled=MainActivity.servicesOmegaFi.getHome().getScheduledOfCharges(idAccount);
				status=(Integer)statusScheduled[0];
				scheduled=(ScheduledOfCharges)statusScheduled[1];
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				completeCyclesCharges(scheduled);
				stopProgressDialog();
				refreshActivity();
			}
		};
		task.execute();
	}
	

}
