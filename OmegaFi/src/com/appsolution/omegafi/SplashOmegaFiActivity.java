package com.appsolution.omegafi;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

public class SplashOmegaFiActivity extends OmegaFiLoginActivity {

	private Handler handlerProgress;
	private ChargerSplash barSplash;
	private View barView;
	private android.view.ViewGroup.LayoutParams params1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_omega_fi);
		barView=(View)findViewById(R.id.barSplash);
		params1=barView.getLayoutParams();
		handlerProgress=new Handler(){
			@Override
			public void handleMessage(android.os.Message msg) {
				params1.width=(Integer)msg.obj;
				barView.setLayoutParams(params1);	
			};
		};
		barSplash=new ChargerSplash();
		barSplash.execute();
	}
	
	
	private class ChargerSplash extends AsyncTask<Void, Integer, Boolean>{

		@Override
		protected Boolean doInBackground(Void... params) {
			
			for (int i = 0; i < 200; i+=5) {
				try {
					Thread.sleep(80);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Message msg=new Message();
				msg.obj=i;
				handlerProgress.sendMessage(msg);
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
