package com.appsolution.omegafi;

import java.util.ArrayList;

import com.appsolution.layouts.ContentAnnouncement;
import com.appsolution.logic.SimpleAnnouncement;
import com.appsolution.services.Server;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class AnnouncementsActivity extends OmegaFiActivity {

	private ListView listAnnouncements;
	private AnnouncementsAdapter adapterAnnouncements=null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_announcements);
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
				startProgressDialog("Loading Announcements...", getResources().getString(R.string.please_wait));
			}
			
			@Override
			protected Boolean doInBackground(Void... params) {
				Object[] statusAnnouncements=Server.getServer().getHome().getStatusArrayAnnouncements();
				status=(Integer)statusAnnouncements[0];
				announcements=(ArrayList<SimpleAnnouncement>)statusAnnouncements[1];
				Server.getServer().getHome().updateAnnouncementsView();
				Server.getServer().getHome().getProfile().updateProfileIfNecessary();
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
					OmegaFiActivity.showErrorConection(AnnouncementsActivity.this, status, getResources().getString(R.string.object_not_found),false);
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
					
				}
				else{
					contentAnnouncement=(ContentAnnouncement)convertView;
				}
				contentAnnouncement.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						Server.getServer().getHome().setAnnouncementSelected(actualAnnouncement);
						goToAnnouncemenDetail();
					}
				});
				contentAnnouncement.setTitleAnnouncement(actualAnnouncement.getSubject());
				contentAnnouncement.setDateAnnouncement(actualAnnouncement.getDateCreate());
				contentAnnouncement.setDescriptionAnnouncement(actualAnnouncement.getPreviewAnnoncement());
				contentAnnouncement.setSourceAnnouncement(actualAnnouncement.getSource());
				if(position<Server.getServer().getHome().getProfile().getProfile().getAnnouncementsCount()){
						contentAnnouncement.setBackgroundNewAnnoncement();
				}
				if(position==announcements.size()-1){
					contentAnnouncement.setBackgroundResource(0);
					contentAnnouncement.setBackgroundColor(Color.WHITE);
				}
				return convertView;
		}
        
	}
	
	private void goToAnnouncemenDetail(){
		Intent announcementDetails=new Intent(this, AnnouncementDetailActivity.class);
		startActivityForResult(announcementDetails,OmegaFiActivity.ACTIVITY_ANNOUNCEMENTS);
	}
	
	@Override
	public void onBackPressed() {
		goToHome();
		super.onBackPressed();
	}

}