package com.appsolution.omegafi;

import com.appsolution.services.Server;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;

public class PrivacyActivity extends OmegaFiActivity {

	private WebView webPrivacy;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_privacy);
		webPrivacy=(WebView)findViewById(R.id.webViewPrivacy);
		webPrivacy.getSettings().setDisplayZoomControls(false);
		webPrivacy.getSettings().setBuiltInZoomControls(true);
		this.loadPrivacyHtml();
	}

	
	@Override
	protected void optionsActionBar() {
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBarCustom.setTitle("PRIVACY POLICY");
		actionBar.setCustomView(actionBarCustom);
	}
	
	private void loadPrivacyHtml(){
		AsyncTask<Void, Integer, Boolean> async=new AsyncTask<Void, Integer, Boolean>(){

			private int status=0;
			private String html="";
			
			@Override
			protected void onPreExecute() {
				startProgressDialog("Loading Privacy...", getString(R.string.please_wait));
			}
			
			@Override
			protected Boolean doInBackground(Void... params) {
				Object[] statusHtml=Server.getServer().getPrivacyOmegaFi();
				status=(Integer)statusHtml[0];
				html=(String)statusHtml[1];
				Server.getServer().getHome().getProfile().updateProfileIfNecessary();
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				if(Server.isStatusOk(status)){
					webPrivacy.loadData(html, "text/html; charset=UTF-8", null);
				}
				stopProgressDialog();
			}
			
		};
		async.execute();
	}

}
