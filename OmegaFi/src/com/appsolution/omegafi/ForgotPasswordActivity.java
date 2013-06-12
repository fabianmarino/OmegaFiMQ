package com.appsolution.omegafi;

import com.appsolution.layouts.RowEditTextSubmit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ForgotPasswordActivity extends OmegaFiLoginActivity {

	private RowEditTextSubmit rowSubmitUsername;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_password);
		rowSubmitUsername=(RowEditTextSubmit)findViewById(R.id.rowSubmitForgotPassword);
		rowSubmitUsername.onSubmit(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent activityPwQuestion=new Intent(getApplication(), ForgotPwQuestionsActivity.class);
				startActivity(activityPwQuestion);
			}
		});
	}

}
