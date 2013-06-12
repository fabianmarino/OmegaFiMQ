package com.appsolution.omegafi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ForgotLoginActivity extends OmegaFiLoginActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_login);
	}
	
	public void activityForgotUser(View button){
		Intent activityForgotUser=new Intent(this, ForgotUserNameActivity.class);
		startActivity(activityForgotUser);
	}
	
	public void activityForgotPassword(View button){
		Intent activityForgotPassword=new Intent(this, ForgotPasswordActivity.class);
		startActivity(activityForgotPassword);
	}
	
}
