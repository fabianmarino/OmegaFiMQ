package com.appsolution.omegafi;

import com.appsolution.layouts.HeaderOmegaFi;
import com.appsolution.layouts.RowEditTextSubmit;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class ForgotUserNameActivity extends OmegaFiLoginActivity {

	private RowEditTextSubmit rowEmail;
	private HeaderOmegaFi headerOmegaFi;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_user_name);
		rowEmail=(RowEditTextSubmit)findViewById(R.id.rowEditSubmitEmail);
//		rowEmail.setTextRowEditSubmit("jwoolbright@omegafi.com");
		headerOmegaFi=(HeaderOmegaFi)findViewById(R.id.headerForgotUserName);
		rowEmail.onSubmit(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendEmail();
			}
		});
	}
	
	private void sendEmail(){
		if(rowEmail.isValidEmail()){
			AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>() {
				
				int status=-1;
				
				@Override
				protected void onPreExecute() {
					rowEmail.closeKeyBoard();
					startProgressDialog("Sending Email", getResources().getString(R.string.please_wait));
				}
				@Override
				protected Boolean doInBackground(Void... params) {
					status=(Integer)MainActivity.servicesOmegaFi.getForgotLogin().forgotUserName(rowEmail.getTextEditSubmit())[0];
					return true;
				}
				
				@Override
				protected void onPostExecute(Boolean result) {
					stopProgressDialog();
					if(status==200){
						rowEmail.setVisibility(LinearLayout.GONE);
						headerOmegaFi.setMessageForForm("Your username has been sent to "+rowEmail.getTextEditSubmit());
					}
					else{
						OmegaFiActivity.showErrorConection(ForgotUserNameActivity.this, status, "Your email was not found");
					}
				}
			};
			task.execute();
		}
		else{
			rowEmail.setErrorEditText("Your email syntax is invalid");
		}
	}
	
	

}
