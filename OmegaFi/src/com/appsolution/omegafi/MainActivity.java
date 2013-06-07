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
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends OmegaFiLoginActivity {

	private EditText textUser;
	private EditText textPassword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		textUser = (EditText)findViewById(R.id.email);
		textUser.setText( "parent001");
		textPassword = (EditText)findViewById(R.id.password);
		textPassword.setText("1234##");
		textUser.setError("Aqui va el nombre de usuario");
		textPassword.setError("Aqui va la contraseña");
		textUser.setError(null);
	}
	
	public void nextHome(View boton){
		AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>() {

			@Override
			protected Boolean doInBackground(Void... params) {
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		        nameValuePairs.add(new BasicNameValuePair("UserName",textUser.getText().toString()));
		        nameValuePairs.add(new BasicNameValuePair("Password", textPassword.getText().toString()));
		        nameValuePairs.add(new BasicNameValuePair("pipe", "mobile"));
				JSONObject respose=servicesOmegaFi.makeRequestPost(Server.LOGIN_SERVICE, nameValuePairs);
				if(respose==null){
					Toast.makeText(getParent(), "Error al conectar, datos invalidos", 1000).show();
				}
				else{
					Log.d("Response", respose.toString());
					Intent splashView=new Intent(getApplicationContext(), SplashOmegaFiActivity.class);
					startActivity(splashView);
				}
				return true;
			}
		};
		task.execute();
	}
	
	public void activityForgotLogin(View textview){
		Intent activityForgotLogin=new Intent(this,ForgotLoginActivity.class );
		startActivity(activityForgotLogin);
	}
}