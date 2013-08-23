package com.appsolution.omegafi;

import com.appsolution.layouts.CycleCharge;
import com.appsolution.layouts.RowInformation;
import com.appsolution.logic.BillingCycle;
import com.appsolution.logic.ScheduledOfCharges;
import com.appsolution.services.Server;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class ScheduleChargesActivity extends OmegaFiActivity {

	private ListView listCycles;
	private int idAccount;
	private ScheduledChargesAdapter chargesAdapter=null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule_charges);
		listCycles=(ListView)findViewById(R.id.listCyclesCharges);
		idAccount=getIntent().getExtras().getInt("id");
		this.chargeScheduledCharges();
	}

	@Override
	protected void optionsActionBar() {
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBarCustom.setTitle("SCHEDULE OF CHARGES");
		actionBar.setCustomView(actionBarCustom);
	}
	
	private void chargeScheduledCharges(){
		AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>() {
			
			int status=0;
			private ScheduledOfCharges scheduled=null; 
			
			@Override
			protected void onPreExecute() {
				startProgressDialog("Loading Schedule of Charges...", getResources().getString(R.string.please_wait));
			}
			
			@Override
			protected Boolean doInBackground(Void... params) {
				Object[] statusScheduled=Server.getServer().getHome().getScheduledOfCharges(idAccount);
				status=(Integer)statusScheduled[0];
				scheduled=(ScheduledOfCharges)statusScheduled[1];
				Server.getServer().getHome().getProfile().updateProfileIfNecessary();
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				if(Server.isStatusOk(status)){
					if(scheduled!=null){
						chargesAdapter=new ScheduledChargesAdapter(ScheduleChargesActivity.this, scheduled);
						listCycles.setAdapter(chargesAdapter);
					}
				}
				else{
					OmegaFiActivity.showErrorConection
					(ScheduleChargesActivity.this, status, getResources().getString(R.string.object_not_found), false);
				}
				stopProgressDialog();
				refreshActivity();
			}
		};
		task.execute();
	}
	
	private class ScheduledChargesAdapter extends BaseAdapter {

		private Activity activity;
		private ScheduledOfCharges scheduled;
		
        public ScheduledChargesAdapter(Activity activity,ScheduledOfCharges scheduled){
        	this.scheduled=scheduled;
        	this.activity=activity;
        }

		@Override
		public int getCount() {
			return	scheduled.getBillingCycles().size();
		}

		@Override
		public Object getItem(int arg0) {
			return scheduled.getBillingCycles().get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			CycleCharge cycle=null;
			if(position!=0){
				BillingCycle cycleBilling=(BillingCycle)getItem(position);
				if(convertView==null){
					convertView=new CycleCharge(activity);
					cycle=(CycleCharge)convertView;
				}
				else{
					cycle=(CycleCharge)convertView;
				}
				cycle.setNamesInfo("Cycle "+cycleBilling.getCycleNumber(), "Billed: "+cycleBilling.getDateBillOn(),
						"Due: "+cycleBilling.getDateDueOn());
				cycle.setValueInfo(cycleBilling.getTotalAmount());
				cycle.setListCharges(cycleBilling.getListCharges());
			}
			else{
				RowInformation rowInfo=null;
				if(convertView==null){
					convertView=new RowInformation(activity);
					rowInfo=(RowInformation)convertView;
				}
				else{
					rowInfo=(RowInformation)convertView;
				}
				rowInfo.setNameInfo(scheduled.getInterval());
				rowInfo.setValueInfo(scheduled.getGrandTotal());
			}
			return convertView;
		}
        
	}
	
}