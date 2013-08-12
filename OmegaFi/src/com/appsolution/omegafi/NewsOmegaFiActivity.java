package com.appsolution.omegafi;

import java.util.ArrayList;


import com.appsolution.layouts.EventNewsContent;
import com.appsolution.logic.CalendarEvent;
import com.appsolution.services.Server;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class NewsOmegaFiActivity extends OmegaFiActivity {

	private ListView listNews;
	private NewsOmegaFiAdapter newsAdapter=null;
	private OnClickListener listener;
	private int padding;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_omega_fi);
		padding=(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
		listNews=(ListView)findViewById(R.id.listNewsOmegaFi);
		this.chargeNewsOmegaFi();
	}

	@Override
	protected void optionsActionBar() {
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBarCustom.setTitle("ALPHA ETA NEWS");
		actionBar.setCustomView(actionBarCustom);
	}
	
	
	
	private void chargeNewsOmegaFi(){
		AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>() {
			
			private ArrayList<CalendarEvent> newsArray=null;
			
			@Override
			protected void onPreExecute() {
				startProgressDialog("Charging News...", getResources().getString(R.string.please_wait));
			}
			
			@Override
			protected Boolean doInBackground(Void... params) {
				newsArray=Server.getServer().getHome().getFeeds().getNewsFeed
						(Server.getServer().getForgotLogin().getUrlFeed(NewsOmegaFiActivity.this));
				Server.getServer().getHome().getProfile().updateProfileIfNecessary();
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				actionBarCustom.setTitle(Server.getServer().getForgotLogin().getTitleFeed(NewsOmegaFiActivity.this).toUpperCase());
				if(newsArray!=null){
					newsAdapter=new NewsOmegaFiAdapter(NewsOmegaFiActivity.this, newsArray);
					listNews.setAdapter(newsAdapter);
				}
				stopProgressDialog();
				refreshActivity();
			}
		};
		task.execute();
	}
	
	private class NewsOmegaFiAdapter extends BaseAdapter {

		private Activity activity;
		private ArrayList<CalendarEvent> news=null;
		
        public NewsOmegaFiAdapter(Activity activity,ArrayList<CalendarEvent> news){
        	this.news=news;
        	this.activity=activity;
        }

		@Override
		public int getCount() {
			return	news.size();
		}

		@Override
		public Object getItem(int arg0) {
			return news.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			EventNewsContent newContent=null;
			final CalendarEvent actualEvent=(CalendarEvent)getItem(position);
				if(convertView==null){
					convertView=new EventNewsContent(activity);
					newContent=(EventNewsContent)convertView;
				}
				else{
					newContent=(EventNewsContent)convertView;
				}
				newContent.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						Intent i = new Intent(Intent.ACTION_VIEW,  Uri.parse(actualEvent.getLinkUrl()));
						activity.startActivity(i);
					}
				});
				newContent.setTitleNewEvent(actualEvent.getTitle());
				newContent.setDateEventNew(actualEvent.getBeginDate());
				newContent.setBorderBottom(true);
				newContent.setPadding(padding);
				newContent.setDescriptionHtmlComplete(actualEvent.getDescription());
				return convertView;
		}
        
	}
	

	
}