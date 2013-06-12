package com.appsolution.omegafi;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

public class SplashOmegaFiActivity extends OmegaFiLoginActivity {

	private Handler handlerProgress;
	private ChargerSplash barSplash;
	private TextView textPercentaje;
	
	private android.view.ViewGroup.LayoutParams params1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_omega_fi);
		textPercentaje=(TextView)findViewById(R.id.percentajeLoading);
		handlerProgress=new Handler(){
			@Override
			public void handleMessage(android.os.Message msg) {
				textPercentaje.setText(msg.obj+"%");
			};
		};
		barSplash=new ChargerSplash();
		barSplash.execute();
	}
	
	
	private class ChargerSplash extends AsyncTask<Void, Integer, Boolean>{

		@Override
		protected Boolean doInBackground(Void... params) {
			for (int i = 0; i < 100; i+=5) {
				try {
					Thread.sleep(110);
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
