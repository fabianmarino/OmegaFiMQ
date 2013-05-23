package com.appsolution.omegafi;

import com.actionbarsherlock.view.MenuItem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class OmegaFiLoginActivity extends OmegaFiActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_omega_fi_login);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return false;
	}
	
	public void backToLogin(View button) {
		Intent backToLogin=new Intent(this, MainActivity.class);
		backToLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(backToLogin);
	}

}
