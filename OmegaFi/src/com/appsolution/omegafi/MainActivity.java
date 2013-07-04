package com.appsolution.omegafi;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.appsolution.layouts.DialogInformationOF;
import com.appsolution.logic.Server;
import com.appsolution.omegafi.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends OmegaFiLoginActivity {

	private EditText textUser;
	private EditText textPassword;
	private ProgressDialog progress;
	private Activity thisActivity;
	private CheckBox saveUsername;
	private TextView textForgot;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		textUser = (EditText)findViewById(R.id.email);
		String username=getSaveUsername();
		textUser.setText(username);
		textUser.setTypeface(OmegaFiActivity.getFont(getApplicationContext(), 3));
		textPassword = (EditText)findViewById(R.id.password);
		textPassword.setText("1234##");
		saveUsername=(CheckBox)findViewById(R.id.check_save);
		saveUsername.setButtonDrawable(R.drawable.radio_button);
		saveUsername.setTypeface(OmegaFiActivity.getFont(getApplicationContext(), 3));
		if(!username.isEmpty()){
			saveUsername.setChecked(true);
		}
		else{
			saveUsername.setChecked(false);
		}
		textForgot=(TextView)findViewById(R.id.text_forgot);
		textForgot.setTypeface(OmegaFiActivity.getFont(getApplicationContext(), 3));
		thisActivity=this;
	}
	
	public void nextHome(View boton){
		Intent splashView=new Intent(getApplicationContext(), SplashOmegaFiActivity.class);
		startActivity(splashView);
		finish();
//		if(this.validateDataLogin()){
//			final Activity activity=this;
//			AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>() {
//				int status=-1;
//				@Override
//				protected void onPreExecute() {
//					startProgressDialog("Accessing", "Please wait...");
//					if(saveUsername.isChecked()){
//						saveUsername(textUser.getText().toString());
//					}
//					else{
//						saveUsername("");
//					}
//				}
//				@Override
//				protected Boolean doInBackground(Void... params) {
//					status=(Integer)OmegaFiActivity.servicesOmegaFi.getForgotLogin().loginUser(textUser.getText().toString(), textPassword.getText().toString())[0];
//					return true;
//				}
//				@Override
//				protected void onPostExecute(Boolean result) {
//					stopProgressDialog();
//					if(status==200){
//						Intent splashView=new Intent(getApplicationContext(), SplashOmegaFiActivity.class);
//						startActivity(splashView);
//						finish();
//					}
//					else{
//						OmegaFiActivity.showErrorConection(thisActivity, status, "Web service not found.");
//					}
//				}
//			};
//			task.execute();
//			}
		}
	
	public void activityForgotLogin(View textview){
		Intent activityForgotLogin=new Intent(this,ForgotLoginActivity.class );
		startActivity(activityForgotLogin);
	}
	
	public boolean validateDataLogin(){
		if(textUser.getText().toString().isEmpty()){
			textUser.setError("User empty!");
			return false;
		}
		else{
			textUser.setError(null);
		}
		if(textPassword.getText().toString().isEmpty()){
			textPassword.setError("Password Empty!");
			return false;
		}
		else{
			textPassword.setError(null);
		}
		return true;
	}
}