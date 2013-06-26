package com.appsolution.omegafi;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.appsolution.layouts.DialogContactAccount;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.QuickContactBadge;

public class OmegaFiLoginActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_omega_fi_login);
	}
	
	public void backToLogin(View button) {
		Intent backToLogin=new Intent(this, MainActivity.class);
		backToLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(backToLogin);
	}
	
	public boolean isOnline() {
	    ConnectivityManager cm = 
	         (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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

}
