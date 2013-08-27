package com.appsolution.omegafi;

import com.appsolution.logic.CachingImage;
import com.appsolution.omegafi.R;
import com.appsolution.services.Server;

import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends OmegaFiLoginActivity {

	private EditText textUser;
	private EditText textPassword;
	private CheckBox saveUsername;
	private TextView textForgot;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Runtime.getRuntime().gc();
		System.gc();
		CookieSyncManager.createInstance(this);
		CookieSyncManager.getInstance().startSync();
		CookieManager.getInstance().removeAllCookie();
		textUser = (EditText)findViewById(R.id.email);
		textUser.setTypeface(OmegaFiActivity.getFont(getApplicationContext(), 3));
		textPassword = (EditText)findViewById(R.id.password);
		textPassword.setTypeface(OmegaFiActivity.getFont(getApplicationContext(), 3));
		saveUsername=(CheckBox)findViewById(R.id.check_save);
		if(OmegaFiActivity.getWidthPercentageDisplay(getApplicationContext(), 1)>730){
			saveUsername.setPadding(getResources().getDimensionPixelSize(R.dimen.spacing_inches_checkbox), 
					saveUsername.getPaddingTop(), saveUsername.getPaddingRight(), saveUsername.getPaddingBottom());
		}
		clearUserNameCheckBox();
		saveUsername.setButtonDrawable(R.drawable.radio_button);
		saveUsername.setTypeface(OmegaFiActivity.getFont(getApplicationContext(), 3));
		getUserNameSaved();
		textForgot=(TextView)findViewById(R.id.text_forgot);
		textForgot.setTypeface(OmegaFiActivity.getFont(getApplicationContext(), 3));
	}
	
	public void nextHome(View boton){
		if(this.validateDataLogin()){
			AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>() {
				int status=-1;
				@Override
				protected void onPreExecute() {
					startProgressDialog("Accessing", "Please wait...");
					saveUsername();
				}
				@Override
				protected Boolean doInBackground(Void... params) {
					status=(Integer)Server.getServer().getForgotLogin().loginUser(textUser.getText().toString(),
							textPassword.getText().toString(),MainActivity.this)[0];
					return true;
				}
				@Override
				protected void onPostExecute(Boolean result) {
					stopProgressDialog();
					textPassword.setText("");
					if(Server.isStatusOk(status)){
						Intent splashView=new Intent(getApplicationContext(), SplashOmegaFiActivity.class);
						startActivity(splashView);
						finish();
					}
					else{
						OmegaFiActivity.showErrorConection(MainActivity.this, status, "Web service not found.",true);
					}
				}
			};
			task.execute();
			}
		}
	
	public void activityForgotLogin(View textview){
		Intent activityForgotLogin=new Intent(this,ForgotLoginActivity.class );
		startActivity(activityForgotLogin);
	}
	
	private void clearUserNameCheckBox(){
		saveUsername.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(!isChecked){
					saveUsername("");
				}
			}
		});
	}
	
	private void getUserNameSaved(){
		String username=getSaveUsername();
		if(!username.isEmpty()){
			saveUsername.setChecked(true);
			textUser.setText(username);
		}
		else{
			saveUsername.setChecked(false);
		}
	}
	
	@Override
	protected void onResume() {
		CookieSyncManager.getInstance().stopSync();
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		CookieSyncManager.getInstance().startSync();
		super.onPause();
	}
	
	private void saveUsername(){
		if(saveUsername.isChecked()){
			saveUsername(textUser.getText().toString());
		}
		else{
			saveUsername("");
		}
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
	
	@Override
	public void onBackPressed() {
		finish();
		OmegaFiActivity.closeAllActivities(this);
		System.runFinalization();
		super.onBackPressed();
	}
}