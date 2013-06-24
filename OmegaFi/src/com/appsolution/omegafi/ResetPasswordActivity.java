package com.appsolution.omegafi;

import com.appsolution.layouts.DialogInformationOF;
import com.appsolution.layouts.HeaderOmegaFi;
import com.appsolution.layouts.RowQuestionEditText;

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
			rowNewPassword.setError("This field is empty");
		}
		else if(rowConfirmPassword.getTextQuestionEdit().isEmpty()){
			rowConfirmPassword.setError("This field is empty");
		}
		else if(!rowConfirmPassword.getTextQuestionEdit().equals(rowNewPassword.getTextQuestionEdit())){
			final DialogInformationOF dialog=new DialogInformationOF(this);
			dialog.setMessageDialog("The fields to be equals");
			dialog.setButtonListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					dialog.dismissDialog();
				}
			});
			dialog.showDialog();
		}
		else{
			linearQuestions.setVisibility(LinearLayout.GONE);
			header.setMessageForForm("Your password has been reset successfully. \nPlease return to the home screen to login.");
		}
	}

}
