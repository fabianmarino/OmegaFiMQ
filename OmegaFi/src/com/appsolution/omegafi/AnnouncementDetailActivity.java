package com.appsolution.omegafi;

import com.appsolution.layouts.ContentAnnouncement;
import com.appsolution.logic.SimpleAnnouncement;
import com.appsolution.services.Server;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class AnnouncementDetailActivity extends OmegaFiActivity {

	private ContentAnnouncement announcement;
	private TextView textSource;
	private SimpleAnnouncement selected;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_announcement_detail);
		selected=Server.getServer().getHome().getAnnouncementSelected();
		announcement=(ContentAnnouncement)findViewById(R.id.contentAnnouncementDetail);
		textSource=(TextView)findViewById(R.id.textSourceAnnouncementActivity);
		announcement.setBackgroundResource(R.drawable.background_white_pure);
		completeAnnouncementDetail();
	}
	
	private void completeAnnouncementDetail(){
		if(selected!=null){
			announcement.setTitleAnnouncement(selected.getSubject());
			announcement.setDateAnnouncement(selected.getDateCreate());
			announcement.setDescriptionAnnouncement(selected.getAnnouncement());
			setSourceAnnouncement(selected.getSource());
		}
		else{
			this.finish();
		}
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBarCustom.setTitle("ANNOUNCEMENTS");
		actionBar.setCustomView(actionBarCustom);
	}
	
	private void setSourceAnnouncement(String source){
		textSource.setText(Html.fromHtml("<b>Source: </b>"));
		if(source!=null){
			textSource.setText(Html.fromHtml("<b>Source: </b>"+source));
		}
	}

}
