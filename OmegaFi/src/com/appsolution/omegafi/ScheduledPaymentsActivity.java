package com.appsolution.omegafi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.appsolution.layouts.CycleCharge;
import com.appsolution.layouts.RowInformation;
import com.appsolution.logic.BillingCycle;
import com.appsolution.logic.ScheduledOfCharges;
import com.appsolution.logic.SimpleScheduledPayment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class ScheduledPaymentsActivity extends OmegaFiActivity {
	
	private PaymentsAdapter adapterPayment;
	private ListView listScheduledPayments;
	private int idAccount=-1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scheduled_payments);
		listScheduledPayments=(ListView)findViewById(R.id.listViewScheduledPayments);
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
	
	private void getListStringScheduledPayments(ArrayList<SimpleScheduledPayment> scheduleds){
		List<String> listPayments=new ArrayList<String>();
		for (final SimpleScheduledPayment scheduled:scheduleds) {
		}
	}
	

	private void chargeScheduledPayments(){
		AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>() {
			
			private int status=0;
			private ArrayList<SimpleScheduledPayment> scheduleds=null;
			private ArrayList<SimpleScheduledPayment> processing=null;
			
			@Override
			protected void onPreExecute() {
				startProgressDialog("Charging Scheduled Payments", getResources().getString(R.string.please_wait));
			}
			
			@Override
			protected Boolean doInBackground(Void... arg0) {
				Object[] statusScheduleds=MainActivity.servicesOmegaFi.getHome().getAccounts().getScheduledPayments(idAccount);
				status=(Integer)statusScheduleds[0];
				scheduleds=(ArrayList<SimpleScheduledPayment>)statusScheduleds[1];
				Object[] statusProcessing=MainActivity.servicesOmegaFi.getHome().getAccounts().getProcessingPayments(idAccount);
				status=(Integer)statusProcessing[0];
				processing=(ArrayList<SimpleScheduledPayment>)statusProcessing[1];
				scheduleds.addAll(processing);
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				stopProgressDialog();
				if(status==200&&scheduleds!=null){
					adapterPayment=new PaymentsAdapter(ScheduledPaymentsActivity.this, scheduleds);
					listScheduledPayments.setAdapter(adapterPayment);
				}
				else{
					OmegaFiActivity.showErrorConection(ScheduledPaymentsActivity.this, status, "Object not found");
				}
				refreshActivity();
			}
			
		};
		task.execute();
	}
	
	private class PaymentsAdapter extends BaseAdapter {

		private Activity activity;
		private ArrayList<SimpleScheduledPayment> payments=null;
		
        public PaymentsAdapter(Activity activity,ArrayList<SimpleScheduledPayment> payments){
        	this.payments=payments;
        	this.activity=activity;
        }

		@Override
		public int getCount() {
			return	payments.size();
		}

		@Override
		public Object getItem(int arg0) {
			return payments.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			RowInformation rowPayment=null;
			final SimpleScheduledPayment actualPayment=(SimpleScheduledPayment)getItem(position);
				if(convertView==null){
					convertView=new RowInformation(activity);
					rowPayment=(RowInformation)convertView;
				}
				else{
					rowPayment=(RowInformation)convertView;
				}
				rowPayment.setNameInfo(actualPayment.getPaymentDate()+" - "+actualPayment.getStateScheduledWord());
				rowPayment.setValueInfo("$"+actualPayment.getPaymentAmount());
				rowPayment.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						finish();
						MainActivity.servicesOmegaFi.getHome().getAccounts().setSelected(actualPayment);
						Intent viewScheduledDetails=new Intent(getApplicationContext(), ScheduledPaymentsDetailActivity.class);
						viewScheduledDetails.putExtra("id", idAccount);
						if(actualPayment.getStateScheduled()==SimpleScheduledPayment.STATE_PROCESSING)
							viewScheduledDetails.putExtra("editable", false);
						else
							viewScheduledDetails.putExtra("editable", true);
						
						startActivityForResult(viewScheduledDetails, OmegaFiActivity.ACTIVITY_SCHEDULED_PAYMENT_DETAIL);
					}
				});
			return convertView;
		}
        
	}
	
}