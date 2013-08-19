package com.appsolution.omegafi;

import com.appsolution.layouts.RowEditTextSubmit;
import com.appsolution.services.Server;

import android.content.Intent;
import android.os.AsyncTask;
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
				sendUserName();
			}
		});
	}
	
	private void sendUserName(){
		if(rowSubmitUsername.isEmpty()){
			rowSubmitUsername.setErrorEditText(getResources().getString(R.string.field_not_empty));
		}
		else{
			AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>(){
	
				Object[] response=null;
				int status=-1;
				
				@Override
				protected void onPreExecute() {
					rowSubmitUsername.closeKeyBoard();
					startProgressDialog("Validating username", getResources().getString(R.string.please_wait));
				}
				
				@Override
				protected Boolean doInBackground(Void... params) {
					response=Server.getServer().getForgotLogin().forgotPassword(rowSubmitUsername.getTextEditSubmit());
					status=(Integer)response[0];
					return true;
				}
				
				@Override
				protected void onPostExecute(Boolean result) {
					stopProgressDialog();
					if(status==200){
						Intent activityPwQuestion=new Intent(getApplication(), ForgotPwQuestionsActivity.class);
						startActivity(activityPwQuestion);
					}
					else{
						OmegaFiActivity.showErrorConection(ForgotPasswordActivity.this, status, "Your username has not found",true);
					}
				}
				
			};
			task.execute();
		}
	}

}
