package com.appsolution.omegafi;
import com.appsolution.services.Server;
import com.google.android.gms.analytics.internal.IAnalyticsService;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class LogoutOmegaFiActivity extends OmegaFiLoginActivity {

	
	private TextView textUserName;
	private ImageView imageContact;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logout_omega_fi);
		Typeface bold=Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
		imageContact=(ImageView)findViewById(R.id.photoProfileLogout);
		imageContact.setImageDrawable(Server.getServer().getHome().getProfile().getPhotoTemp());
		textUserName=(TextView)findViewById(R.id.textByeUser);
		textUserName.setTypeface(bold);
		textUserName.setText("Goodbye, "+Server.getServer().getForgotLogin().getFirstName(LogoutOmegaFiActivity.this));
		chargeLogout();
	}
	
	private void chargeLogout(){
		AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>(){
			@Override
			protected Boolean doInBackground(Void... params) {
				Server.clearServer();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				finish();
				backToLogin();
			}
		};
		task.execute();
	}
	
	private void backToLogin(){
		finish();
		OmegaFiActivity.closeAllActivities(this);
		Intent backToLogin=new Intent(getApplicationContext(), MainActivity.class);
		backToLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		backToLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		backToLogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		System.runFinalization();
		Runtime.getRuntime().gc();
		System.gc();
		startActivity(backToLogin);
	}
	
	
}