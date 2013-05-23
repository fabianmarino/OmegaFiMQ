package com.appsolution.omegafi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ForgotPwQuestionsActivity extends OmegaFiLoginActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_pw_questions);
	}
	
	public void resetPasswordActivity(View button){
		Intent activityResetPassword=new Intent(this, ResetPasswordActivity.class);
		startActivity(activityResetPassword);
	}
	
}
