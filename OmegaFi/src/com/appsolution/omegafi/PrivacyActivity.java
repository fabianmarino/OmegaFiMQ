package com.appsolution.omegafi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;

public class PrivacyActivity extends OmegaFiActivity {

	private WebView webPrivacy;
	private ProgressDialog progress;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_privacy);
		webPrivacy=(WebView)findViewById(R.id.webViewPrivacy);
		webPrivacy.getSettings().setDisplayZoomControls(true);
		webPrivacy.getSettings().setBuiltInZoomControls(true);
		this.loadPrivacyHtml();
	}

	
	@Override
	protected void optionsActionBar() {
		actionBar.setTitle("Privacy Policy");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
	}
	
	private void loadPrivacyHtml(){
		final Activity activity=this;
		AsyncTask<Void, Integer, Boolean> async=new AsyncTask<Void, Integer, Boolean>(){

			private String html="";
			
			@Override
			protected void onPreExecute() {
				progress=new ProgressDialog(activity);
				progress.setTitle("Charging...");
				progress.setMessage("Wait please");
				progress.setCancelable(false);
				progress.setIndeterminate(true);
				progress.show();
			}
			
			@Override
			protected Boolean doInBackground(Void... params) {
				html=OmegaFiActivity.servicesOmegaFi.getPrivacyOmegaFi();
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				webPrivacy.loadData(html, "text/html; charset=UTF-8", null);
				progress.dismiss();
			}
			
		};
		async.execute();
	}

}
