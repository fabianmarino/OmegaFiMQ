package com.appsolution.omegafi;

import org.json.JSONObject;

import com.appsolution.layouts.DialogInformationOF;
import com.appsolution.layouts.RowEditTextSubmit;

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
		rowSubmitUsername.setTextRowEditSubmit("parent001");
		rowSubmitUsername.onSubmit(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendUserName();
			}
		});
	}
	
	private void sendUserName(){
		if(rowSubmitUsername.isEmpty()){
			rowSubmitUsername.setErrorEditText("Field Empty");
		}
		else{
			AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>(){
	
				Object[] response=null;
				int status=-1;
				
				@Override
				protected void onPreExecute() {
					rowSubmitUsername.closeKeyBoard();
					startProgressDialog("Validating username", "Wait Please");
				}
				
				@Override
				protected Boolean doInBackground(Void... params) {
					response=OmegaFiActivity.servicesOmegaFi.getForgotLogin().forgotPassword(rowSubmitUsername.getTextEditSubmit());
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
						OmegaFiActivity.showErrorConection(ForgotPasswordActivity.this, status, "Your username has not found");
					}
				}
				
			};
			task.execute();
		}
	}

}
