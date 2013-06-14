package com.appsolution.omegafi;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.appsolution.logic.Server;
import com.appsolution.omegafi.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends OmegaFiLoginActivity {

	private EditText textUser;
	private EditText textPassword;
	private ProgressDialog progress;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		textUser = (EditText)findViewById(R.id.email);
		textUser.setText( "parent001");
		textPassword = (EditText)findViewById(R.id.password);
		textPassword.setText("1234##");
	}
	
	public void nextHome(View boton){
//		Intent splashView=new Intent(getApplicationContext(), SplashOmegaFiActivity.class);
//		startActivity(splashView);
		if(this.validateDataLogin()){
			if(this.isOnline()){
			final Activity activity=this;
			AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>() {
				JSONObject response=null;
				@Override
				protected void onPreExecute() {
					progress=new ProgressDialog(activity);
					progress.setTitle("Accesing...");
					progress.setMessage("Wait please");
					progress.setCancelable(false);
					progress.setIndeterminate(true);
					progress.show();
				}
				@Override
				protected Boolean doInBackground(Void... params) {
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			        nameValuePairs.add(new BasicNameValuePair("UserName",textUser.getText().toString()));
			        nameValuePairs.add(new BasicNameValuePair("Password", textPassword.getText().toString()));
			        nameValuePairs.add(new BasicNameValuePair("pipe", "mobile"));
					response=OmegaFiActivity.servicesOmegaFi.makeRequestPost(Server.LOGIN_SERVICE, nameValuePairs);
					return true;
				}
				@Override
				protected void onPostExecute(Boolean result) {
					progress.dismiss();
					if(response==null){
						Toast.makeText(getApplicationContext(), "Error al conectar, datos invalidos", 1000).show();
					}
					else{
						Log.d("Response", response.toString());
						Intent splashView=new Intent(getApplicationContext(), SplashOmegaFiActivity.class);
						try {
							splashView.putExtra("NameUser", response.getString("FirstName"));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						startActivity(splashView);
					}
				}
			};
			task.execute();
			}
			else{
				Toast.makeText(getApplicationContext(), "Debe estar conectado para usar la app", 1000).show();
			}
		}
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