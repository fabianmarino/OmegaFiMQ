package com.appsolution.omegafi;

import java.util.ArrayList;

import com.appsolution.layouts.RowInformation;
import com.appsolution.logic.SimpleScheduledPayment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

public class ScheduledPaymentsActivity extends OmegaFiActivity {
	
	
	private LinearLayout linearScheduledPayments;
	private int idAccount=-1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scheduled_payments);
		linearScheduledPayments=(LinearLayout)findViewById(R.id.linearScheduledPayments);
		idAccount=getIntent().getExtras().getInt("id");
		chargeScheduledPayments();
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBarCustom.setTitle(getResources().getString(R.string.scheduled_payments).toUpperCase());
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
	
	private void completeLinearSheduled(ArrayList<SimpleScheduledPayment> scheduleds){
		for (final SimpleScheduledPayment scheduled:scheduleds) {
			RowInformation rowInfo=new RowInformation(getApplicationContext());
			rowInfo.setNameInfo(scheduled.getPaymentDate()+" - "+scheduled.getStateScheduled());
			rowInfo.setValueInfo("$"+scheduled.getPaymentAmount());
			rowInfo.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					MainActivity.servicesOmegaFi.getHome().getAccounts().setSelected(scheduled);
					Intent viewScheduledDetails=new Intent(getApplicationContext(), ScheduledPaymentsDetailActivity.class);
					viewScheduledDetails.putExtra("id", idAccount);
					viewScheduledDetails.putExtra("editable", false);
					startActivityForResult(viewScheduledDetails, OmegaFiActivity.ACTIVITY_SCHEDULED_PAYMENT_DETAIL);
				}
			});
			linearScheduledPayments.addView(rowInfo);
		}
		refreshActivity();
	}
	

	private void chargeScheduledPayments(){
		AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>() {
			
			private int status=0;
			private ArrayList<SimpleScheduledPayment> scheduleds=null;
			
			@Override
			protected void onPreExecute() {
				startProgressDialog("Charging Scheduled Payments", getResources().getString(R.string.please_wait));
			}
			
			@Override
			protected Boolean doInBackground(Void... arg0) {
				Object[] statusScheduleds=MainActivity.servicesOmegaFi.getHome().getAccounts().getScheduledPayments(idAccount);
				status=(Integer)statusScheduleds[0];
				scheduleds=(ArrayList<SimpleScheduledPayment>)statusScheduleds[1];
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				stopProgressDialog();
				if(status==200){
					completeLinearSheduled(scheduleds);
				}
				else{
					OmegaFiActivity.showErrorConection(ScheduledPaymentsActivity.this, status, "Object not found");
				}
			}
			
		};
		task.execute();
	}
}
