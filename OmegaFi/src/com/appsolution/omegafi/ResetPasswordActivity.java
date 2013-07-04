package com.appsolution.omegafi;

import com.appsolution.layouts.DialogInformationOF;
import com.appsolution.layouts.HeaderOmegaFi;
import com.appsolution.layouts.RowQuestionEditText;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class ResetPasswordActivity extends OmegaFiLoginActivity {

	private HeaderOmegaFi header;
	private LinearLayout linearQuestions;
	private RowQuestionEditText rowNewPassword;
	private RowQuestionEditText rowConfirmPassword;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_password);
		header=(HeaderOmegaFi)findViewById(R.id.headerOmegaResetPw);
		linearQuestions=(LinearLayout)findViewById(R.id.linearRowQuestionsPw);
		rowNewPassword=(RowQuestionEditText)findViewById(R.id.rowForgotNewPassword);
		rowConfirmPassword=(RowQuestionEditText)findViewById(R.id.rowForgotConfirmPassword);
	}
	
	public void resetPassword(View button){
		if(rowNewPassword.getTextQuestionEdit().isEmpty()){
			rowNewPassword.setError(getResources().getString(R.string.field_not_empty));
		}
		else if(rowConfirmPassword.getTextQuestionEdit().isEmpty()){
			rowConfirmPassword.setError(getResources().getString(R.string.field_not_empty));
		}
		else if(!rowConfirmPassword.getTextQuestionEdit().equals(rowNewPassword.getTextQuestionEdit())){
			final DialogInformationOF dialog=new DialogInformationOF(this);
			dialog.setMessageDialog("Your passwords do not match.");
			dialog.setButtonListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					dialog.dismissDialog();
				}
			});
			dialog.showDialog();
		}
		else{
			this.sendChangePassword();
		}
	}
	
	private void sendChangePassword(){
//		AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>() {
//			int status=0;
//			
//			@Override
//			protected void onPreExecute() {
//				startProgressDialog("Changing password", getResources().getString(R.string.please_wait));
//			}
//			
//			@Override
//			protected Boolean doInBackground(Void... params) {
//				status=(Integer)OmegaFiActivity.servicesOmegaFi.getForgotLogin().changePassword(rowNewPassword.getTextQuestionEdit(), 
//						rowConfirmPassword.getTextQuestionEdit())[0];
//				return true;
//			}
//			
//			@Override
//			protected void onPostExecute(Boolean result) {
//				stopProgressDialog();
//				if(status==200){
					linearQuestions.setVisibility(LinearLayout.GONE);
					header.setMessageForForm(getResources().getString(R.string.changed_password_sucessfully));
//				}
//				else{
//					OmegaFiActivity.showErrorConection(ResetPasswordActivity.this, status, null);
//				}
//			}
//		};
//		task.execute();
	}

}
