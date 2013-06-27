package com.appsolution.omegafi;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
	private Bundle bundleLogin;
	private ImageView imageContact;
	
	private android.view.ViewGroup.LayoutParams params1;
	
	/**
	 * JSONs services to Home
	 */
	
	private JSONObject jsonProfile;
	private JSONObject jsonAccounts;
	private JSONObject jsonChapters;
	
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
		
		bundleLogin=getIntent().getExtras();
//		textUserName.setText("Hello, "+bundleLogin.getString("NameUser"));
		barSplash=new ChargerSplash();
		barSplash.execute();
	}
	
	
	private class ChargerSplash extends AsyncTask<Void, Integer, Boolean>{

		@Override
		protected Boolean doInBackground(Void... params) {
//			jsonProfile=OmegaFiActivity.servicesOmegaFi.getProfileUser();
//			Message msg=new Message();
//			msg.obj=10;
//			handlerProgress.sendMessage(msg);
//			try {
//				if(!jsonProfile.getJSONObject("individual").getJSONObject("profile_picture").isNull("url")){
//					OmegaFiActivity.loadImageFromURL(jsonProfile.getJSONObject("individual").getJSONObject("profile_picture").getString("url"), imageContact);
//				}
//				msg=new Message();
//				msg.obj=20;
//				handlerProgress.sendMessage(msg);
//			} catch (JSONException e1) {
//				System.err.println("No se puede obtener el profile");
//			}
//			
//			jsonAccounts=OmegaFiActivity.servicesOmegaFi.getAccountsUser();
//			msg=new Message();
//			msg.obj=50;
//			handlerProgress.sendMessage(msg);
//			jsonChapters=OmegaFiActivity.servicesOmegaFi.getChaptersUser();
//			msg=new Message();
//			msg.obj=99;
//			handlerProgress.sendMessage(msg);
			for (int i = 0; i < 100; i+=5) {
				try {
					Thread.sleep(150);
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
