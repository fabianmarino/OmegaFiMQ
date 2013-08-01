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
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_terms);
		webViewTerms=(WebView)findViewById(R.id.webViewTerms);
		webViewTerms.getSettings().setDisplayZoomControls(false);
		webViewTerms.getSettings().setBuiltInZoomControls(true);
		this.loadTerms();
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBarCustom.setTitle("TERMS OF USE");
		actionBar.setCustomView(actionBarCustom);
	}
	
	private void loadTerms(){
		final Activity activity=this;
		AsyncTask<Void, Integer, Boolean> async=new AsyncTask<Void, Integer, Boolean>(){

			private int status=0;
			private String html="";
			
			@Override
			protected void onPreExecute() {
				startProgressDialog("Charging Terms...", getResources().getString(R.string.please_wait));
			}
			
			@Override
			protected Boolean doInBackground(Void... params) {
				Object[] statusHtml=Server.getServer().getTermsOmegaFi();
				status=(Integer)statusHtml[0];
				html=(String)statusHtml[1];
//				html=OmegaFiActivity.getStringFile(getApplicationContext(), "txt/terms.txt");
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				if(status==200){
					webViewTerms.loadData(html, "text/html; charset=UTF-8", null);
				}
				stopProgressDialog();
			}
			
		};
		async.execute();
	}

}
