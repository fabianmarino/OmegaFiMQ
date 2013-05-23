package com.appsolution.omegafi;

import com.appsolution.layouts.HeaderOmegaFi;
import com.appsolution.layouts.RowEditTextSubmit;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class ForgotUserNameActivity extends OmegaFiLoginActivity {

	private RowEditTextSubmit rowEmail;
	private HeaderOmegaFi headerOmegaFi;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_user_name);
		rowEmail=(RowEditTextSubmit)findViewById(R.id.rowEditSubmitEmail);
		headerOmegaFi=(HeaderOmegaFi)findViewById(R.id.headerForgotUserName);
		rowEmail.onSubmit(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				rowEmail.setVisibility(LinearLayout.GONE);
				headerOmegaFi.setMessageForForm("Your username has been sent to name@mail.com.");
			}
		});
	}
	
	
	
	

}
