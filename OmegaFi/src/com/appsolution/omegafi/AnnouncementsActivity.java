package com.appsolution.omegafi;

import java.util.ArrayList;

import com.appsolution.layouts.ContentAnnouncement;
import com.appsolution.layouts.EventNewsContent;
import com.appsolution.layouts.RowInformation;
import com.appsolution.logic.SimpleAnnouncement;
import com.appsolution.logic.SimpleScheduledPayment;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class AnnouncementsActivity extends OmegaFiActivity {

	private ListView listAnnouncements;
	private AnnouncementsAdapter adapterAnnouncements=null;
	private OnClickListener listener;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_announcements);
		listener=new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent detailAnnouncement=new Intent(getApplication(), AnnouncementDetailActivity.class);
				startActivity(detailAnnouncement);
			}
		};
		listAnnouncements=(ListView)findViewById(R.id.listContentAnnouncements);
		chargeAnnouncementsList();
	}

	@Override
	protected void optionsActionBar() {
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBarCustom.setTitle("ANNOUNCEMENTS");
		actionBar.setCustomView(actionBarCustom);
	}
	
	
	private void chargeAnnouncementsList(){
		AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>() {
			
			private int status=0;
			private ArrayList<SimpleAnnouncement> announcements=null;
			
			@Override
			protected void onPreExecute() {
				startProgressDialog("Charging announcements", getResources().getString(R.string.please_wait));
			}
			
			@Override
			protected Boolean doInBackground(Void... params) {
				Object[] statusAnnouncements=MainActivity.servicesOmegaFi.getHome().getStatusArrayAnnouncements();
				status=(Integer)statusAnnouncements[0];
				announcements=(ArrayList<SimpleAnnouncement>)statusAnnouncements[1];
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				if(status==200){
					adapterAnnouncements=new AnnouncementsAdapter(AnnouncementsActivity.this, announcements);
					listAnnouncements.setAdapter(adapterAnnouncements);
					refreshActivity();
				}
				else{
					OmegaFiActivity.showErrorConection(AnnouncementsActivity.this, status, getResources().getString(R.string.object_not_found));
				}
				stopProgressDialog();
			}
		};
		task.execute();
	}
	
	private class AnnouncementsAdapter extends BaseAdapter {

		private Activity activity;
		private ArrayList<SimpleAnnouncement> announcements=null;
		
        public AnnouncementsAdapter(Activity activity,ArrayList<SimpleAnnouncement> announcements){
        	this.announcements=announcements;
        	this.activity=activity;
        }

		@Override
		public int getCount() {
			return	announcements.size();
		}

		@Override
		public Object getItem(int arg0) {
			return announcements.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ContentAnnouncement contentAnnouncement=null;
			final SimpleAnnouncement actualAnnouncement=(SimpleAnnouncement)getItem(position);
				if(convertView==null){
					convertView=new ContentAnnouncement(activity);
					contentAnnouncement=(ContentAnnouncement)convertView;
					contentAnnouncement.setOnClickListener(listener);
				}
				else{
					contentAnnouncement=(ContentAnnouncement)convertView;
				}
				contentAnnouncement.setTitleAnnouncement(actualAnnouncement.getSubject());
				contentAnnouncement.setDateAnnouncement(actualAnnouncement.getDateCreate());
				contentAnnouncement.setDescriptionAnnouncement(actualAnnouncement.getDescription());
				return convertView;
		}
        
	}
	

}