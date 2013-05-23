package com.appsolution.omegafi;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

public class SplashOmegaFiActivity extends OmegaFiLoginActivity {

	private ChargerSplash barSplash;
	private View barView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_omega_fi);
		barView=(View)findViewById(R.id.barSplash);
		barSplash=new ChargerSplash();
		barSplash.execute();
	}
	
	
	private class ChargerSplash extends AsyncTask<Void, Integer, Boolean>{

		@Override
		protected Boolean doInBackground(Void... params) {
			android.view.ViewGroup.LayoutParams params1=barView.getLayoutParams();
			for (int i = 0; i < 200; i++) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				params1.width=i;
				barView.setLayoutParams(params1);
			}
			return true;
		}
		
		@Override
		protected void onPreExecute() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			Intent homeActivity=new Intent(getApplication(), HomeActivity.class);
			startActivity(homeActivity);
		}
	}
	

}
