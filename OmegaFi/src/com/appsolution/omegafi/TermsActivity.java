package com.appsolution.omegafi;

import com.appsolution.logic.Server;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.webkit.WebView;

public class TermsActivity extends OmegaFiActivity {

	private WebView webViewTerms;
	private ProgressDialog progress;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_terms);
		webViewTerms=(WebView)findViewById(R.id.webViewTerms);
		webViewTerms.getSettings().setDisplayZoomControls(true);
		webViewTerms.getSettings().setBuiltInZoomControls(true);
		this.loadTerms();
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setTitle("Terms of Use");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
	}
	
	private void loadTerms(){
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
				html=OmegaFiActivity.servicesOmegaFi.getTermsOmegaFi();
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				webViewTerms.loadData(html, "text/html; charset=UTF-8", null);
				progress.dismiss();
			}
			
		};
		async.execute();
	}

}
