package com.appsolution.omegafi;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.appsolution.layouts.DialogContactAccount;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.QuickContactBadge;

public class OmegaFiLoginActivity extends Activity {

	private ProgressDialog progressDiag;
	private final static String OMEGAFI_PREFERENCES="OmegaFiPref";
	private final static String OMEGAFI_PREF_USERNAME="username";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_omega_fi_login);
	}
	
	public void backToLogin(View button) {
		OmegaFiActivity.servicesOmegaFi.getForgotLogin().clearForgotLoginService();
		Intent backToLogin=new Intent(this, MainActivity.class);
		backToLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(backToLogin);
	}
	
	public static boolean isOnline(Context context) {
	    ConnectivityManager cm = 
	         (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnected()) {
	        return true;
	    }
	    return false;
	}
	
	public void showContactusButton(View button){
		final DialogContactAccount diag=new DialogContactAccount(this,false);
		diag.setNameContact("Omega Fi");
		diag.setOnOpenRequest(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				diag.dimissDialog();
				Intent openRequest=new Intent(getApplicationContext(), OpenRequestActivity.class);
				startActivity(openRequest);
			}
		});
		diag.showDialog();
	}
	
	protected void startProgressDialog(String title, String msg){
		progressDiag=new ProgressDialog(this);
		progressDiag.setTitle(title);
		progressDiag.setMessage(msg);
		progressDiag.setCancelable(false);
		progressDiag.setIndeterminate(true);
		progressDiag.show();
	}
	
	protected void stopProgressDialog(){
		progressDiag.dismiss();
		progressDiag=null;
	}
	
	protected String getSaveUsername(){
		 return this.getSharedPreferences(OMEGAFI_PREFERENCES, 0).getString(OMEGAFI_PREF_USERNAME, "parent001");
	}
	
	protected void saveUsername(String username){
		SharedPreferences prefs=this.getSharedPreferences(OMEGAFI_PREFERENCES, MODE_PRIVATE);
		SharedPreferences.Editor editor=prefs.edit();
		editor.putString(OMEGAFI_PREF_USERNAME, username);
		editor.commit();
	}

}
