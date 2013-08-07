package com.appsolution.omegafi;

import com.appsolution.layouts.DialogContactAccount;
import com.appsolution.logic.Server;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

public class OmegaFiLoginActivity extends Activity {

	private ProgressDialog progressDiag;
	private final static String OMEGAFI_PREFERENCES="OmegaFiPref";
	private final static String OMEGAFI_PREF_USERNAME="username";
	public final static String OMEGAFI_PREF_URL_NEW_FEEDS="urlFeeds";
	public final static String OMEGAFI_PREF_TITLE_NEW_FEEDS="titleFeeds";
	public final static String OMEGAFI_PREF_FIRST_NAME="firstName";
	public final static int DIALOG_CONTACT_OMEGAFI_LOGIN=-2;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_omega_fi_login);
	}
	
	public void backToLogin(View button) {
		Server.getServer().getForgotLogin().clearForgotLoginService();
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
		final DialogContactAccount diag=new DialogContactAccount(this,false,DIALOG_CONTACT_OMEGAFI_LOGIN,430);
		diag.setNameContact("Omega Fi");
		diag.setPhoneNumberExtern("18886913021");
		diag.setOnOpenRequest(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				diag.dimissDialog();
				Intent openRequest=new Intent(getApplicationContext(), OpenRequestActivity.class);
				openRequest.putExtra("id", DIALOG_CONTACT_OMEGAFI_LOGIN);
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
		 return OmegaFiLoginActivity.getPreferenceSaved(OMEGAFI_PREF_USERNAME, this);
	}
	
	protected void saveUsername(String username){
		OmegaFiLoginActivity.savePreference(OMEGAFI_PREF_USERNAME, username, this);
	}
	
	@Override
	public void onBackPressed() {
		finish();
		super.onBackPressed();
	}
	
	public static String getPreferenceSaved(String preference, Context context){
		return context.getSharedPreferences(OMEGAFI_PREFERENCES, 0).getString(preference, "");
	}
	
	public static void savePreference(String preference, String value, Context contex){
		SharedPreferences prefs=contex.getSharedPreferences(OMEGAFI_PREFERENCES, MODE_PRIVATE);
		SharedPreferences.Editor editor=prefs.edit();
		editor.putString(preference, value);
		editor.commit();
	}
	
	public static void setFirstNameTitleUrlFeeds(String name,String title, String url,Context context){
		savePreference(OMEGAFI_PREF_FIRST_NAME, name, context);
		savePreference(OMEGAFI_PREF_TITLE_NEW_FEEDS,title, context);
		savePreference(OMEGAFI_PREF_URL_NEW_FEEDS, url, context);
	}

}
