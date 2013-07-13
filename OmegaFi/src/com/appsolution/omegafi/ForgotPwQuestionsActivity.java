package com.appsolution.omegafi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.appsolution.layouts.DialogInformationOF;
import com.appsolution.layouts.RowQuestionEditText;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

public class ForgotPwQuestionsActivity extends OmegaFiLoginActivity {

	private RowQuestionEditText question1;
	private RowQuestionEditText question2;
	private RowQuestionEditText question3;
	private JSONObject jsonQuestions;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_pw_questions);
		question1=(RowQuestionEditText)findViewById(R.id.questionMotherName);
		question2=(RowQuestionEditText)findViewById(R.id.questionSchoolAttend);
		question3=(RowQuestionEditText)findViewById(R.id.questionSchoolMascot);
		jsonQuestions=MainActivity.servicesOmegaFi.getForgotLogin().getJsonQuestionResetPassword();
		this.completeFormQuestions();
	}
	
	private void completeFormQuestions(){
		try {
			JSONArray array=jsonQuestions.getJSONArray("securityquestions");
			JSONObject aux1=array.getJSONObject(0);
			question1.setQuestionRow(aux1.getString("securityquestion"));
//			question1.setQuestionRow("What is your Mother's maiden name?");
			JSONObject aux2=array.getJSONObject(1);
			question2.setQuestionRow(aux2.getString("securityquestion"));
//			question2.setQuestionRow("In what city were you born? ");
			JSONObject aux3=array.getJSONObject(2);
			question3.setQuestionRow(aux3.getString("securityquestion"));
//			question3.setQuestionRow("What was your first pet's name?");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void resetPasswordActivity(View button){
		if(this.validateAnswers()){
			AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>(){
	
				int status=-1;
				
				@Override
				protected void onPreExecute() {
					startProgressDialog("Validating answers", getResources().getString(R.string.please_wait));
				}
				
				@Override
				protected Boolean doInBackground(Void... params) {
					status=(Integer)MainActivity.servicesOmegaFi.getForgotLogin().sendQuestionResetPassword
							(question1.getTextQuestionEdit(), question2.getTextQuestionEdit(), question3.getTextQuestionEdit())[0];
					return true;
				}
				
				@Override
				protected void onPostExecute(Boolean result) {
					stopProgressDialog();
					if(status==200){
						Intent activityResetPassword=new Intent(ForgotPwQuestionsActivity.this, ResetPasswordActivity.class);
						startActivity(activityResetPassword);
					}
					else if(status==422){
						final DialogInformationOF dia=new DialogInformationOF(ForgotPwQuestionsActivity.this);
						dia.setMessageDialog("Security answers do not match");
						dia.setButtonListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								dia.dismissDialog();		
							}
						});
						dia.showDialog();
					}
					else{
						OmegaFiActivity.showErrorConection(ForgotPwQuestionsActivity.this, status, null);
					}
					}
				
			};
			task.execute();
		}
	}
	
	private boolean validateAnswers(){
		boolean validate=true;
		question1.setError(null);
		question2.setError(null);
		question3.setError(null);
		if(question1.getTextQuestionEdit().isEmpty()){
			question1.setError(getResources().getString(R.string.field_not_empty));
			validate=false;
		}
		else if(question2.getTextQuestionEdit().isEmpty()){
			question2.setError(getResources().getString(R.string.field_not_empty));
			validate=false;
		}
		else if(question3.getTextQuestionEdit().isEmpty()){
			question3.setError(getResources().getString(R.string.field_not_empty));
			validate=false;
		}
		return validate;
	}
	
}