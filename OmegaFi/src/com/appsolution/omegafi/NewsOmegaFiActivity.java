package com.appsolution.omegafi;

import com.appsolution.layouts.EventNewsContent;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;

public class NewsOmegaFiActivity extends OmegaFiActivity {

	private LinearLayout linearNews;
	private OnClickListener listener;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_omega_fi);
		
		listener=new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("Browser lauch", "Launched!!!");
				Intent i = new Intent(Intent.ACTION_VIEW,  Uri.parse("http://omegafi.com"));
				startActivity(i);
			}
		};
		
		linearNews=(LinearLayout)findViewById(R.id.linearNewsOmegaFi);
		this.completeNews();
	}

	@Override
	protected void optionsActionBar() {
		actionBar.setTitle("Alpha Eta News");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
	}
	
	private void completeNews(){
		int padding=(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
		for (int i = 0; i < 10; i++) {
			EventNewsContent news=new EventNewsContent(this);
			news.setTitleNewEvent("Subject goes here");
			news.setDateEventNew("04/07/2013");
			news.setBorderBottom(true);
			news.setPadding(padding);
			news.setOnClickListener(listener);
			news.setDescriptionNewEvent("Lorem ipsum dolor sit amet, consectetur " +
					"adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
					"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. ");
			linearNews.addView(news);
		}
	}
	
}