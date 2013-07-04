package com.appsolution.omegafi;

import org.json.JSONException;
import org.json.JSONObject;

import com.appsolution.layouts.DialogInformationOF;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;

public class SplashOmegaFiActivity extends OmegaFiLoginActivity {

	private Handler handlerProgress;
	private ChargerSplash barSplash;
	private TextView textUserName;
	private TextView textWelcomeOmega;
	private TextView textPercentaje;
	private TextView textLoading;
	private ImageView imageContact;
	
	private android.view.ViewGroup.LayoutParams params1;
	
	/**
	 * JSONs services to Home
	 */
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_omega_fi);
		Typeface bold=Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
		imageContact=(ImageView)findViewById(R.id.photoProfileSplash);
		textUserName=(TextView)findViewById(R.id.textHelloUser);
		textUserName.setTypeface(bold);
		
		textWelcomeOmega=(TextView)findViewById(R.id.textWelcomeUser);
		textWelcomeOmega.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf"));
		textPercentaje=(TextView)findViewById(R.id.percentajeLoading);
		textPercentaje.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Condensed.ttf"));
		handlerProgress=new Handler(){
			@Override
			public void handleMessage(android.os.Message msg) {
				textPercentaje.setText(msg.obj+"%");
			};
		};
		textLoading=(TextView)findViewById(R.id.labelLoadingSplash);
		textLoading.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf"));
		
		textUserName.setText("Hello, "+OmegaFiActivity.servicesOmegaFi.getForgotLogin().getFirstName());
		barSplash=new ChargerSplash();
		barSplash.execute();
	}
	
	
	private class ChargerSplash extends AsyncTask<Void, Integer, Boolean>{
		
		int status=0;
		
		@Override
		protected Boolean doInBackground(Void... params) {
			status=(Integer)OmegaFiActivity.servicesOmegaFi.getHome().getProfile().chargeProfileData()[0];
			if(status==200){
				Message msg=new Message();
				msg.obj=10;
				handlerProgress.sendMessage(msg);
				OmegaFiActivity.loadImageFromURL(OmegaFiActivity.servicesOmegaFi.getHome().getProfile().getUrlPhotoProfile(), imageContact);
				msg=new Message();
				msg.obj=20;
				handlerProgress.sendMessage(msg);
				OmegaFiActivity.servicesOmegaFi.getHome().getAccounts().chargeAccounts();
				msg=new Message();
				msg.obj=50;
				handlerProgress.sendMessage(msg);
				OmegaFiActivity.servicesOmegaFi.getHome().getChapters().chargeChapters();
				Log.d("first chapter", OmegaFiActivity.servicesOmegaFi.getHome().getChapters().getIdChapter(0)+"");
				OmegaFiActivity.servicesOmegaFi.getHome().getOfficers().chargeOfficers(
						OmegaFiActivity.servicesOmegaFi.getHome().getChapters().getIdChapter(0));			
				msg=new Message();
				msg.obj=50;
				handlerProgress.sendMessage(msg);
				OmegaFiActivity.servicesOmegaFi.getHome().getCalendar().chargeEvents();
				msg=new Message();
				msg.obj=60;
				handlerProgress.sendMessage(msg);
				Log.d("url", OmegaFiActivity.servicesOmegaFi.getForgotLogin().getUrlFeed());
				OmegaFiActivity.servicesOmegaFi.getHome().getFeeds().chargeNewsFeed(OmegaFiActivity.servicesOmegaFi.getForgotLogin().getUrlFeed());
				msg=new Message();
				msg.obj=60;
				handlerProgress.sendMessage(msg);
				
				msg=new Message();
				msg.obj=99;
				handlerProgress.sendMessage(msg);
			}
//			for (int i = 0; i < 100; i+=5) {
//				try {
//					Thread.sleep(150);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				Message msg=new Message();
//				msg.obj=i;
//				handlerProgress.sendMessage(msg);
//			}
			return true;
		}
		
		@Override
		protected void onPreExecute() {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
//			Intent homeActivity=new Intent(getApplication(), HomeActivity.class);
//			finish();
//			startActivity(homeActivity);
			if(status!=200){
				final DialogInformationOF of=new DialogInformationOF(SplashOmegaFiActivity.this);
				of.setMessageDialog("Web service is temporarily unavailable");
				of.setButtonListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						of.dismissDialog();
						finish();
						backToLogin();
					}
					
				});
				of.showDialog();
			}
			else{
				Intent homeActivity=new Intent(getApplication(), HomeActivity.class);
				finish();
				startActivity(homeActivity);
			}
		}
	}
	
	private void backToLogin(){
		Intent backToLogin=new Intent(getApplicationContext(), MainActivity.class);
		backToLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(backToLogin);
		finish();
	}
	
}