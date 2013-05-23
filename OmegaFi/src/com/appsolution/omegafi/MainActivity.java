package com.appsolution.omegafi;

import com.appsolution.omegafi.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

public class MainActivity extends OmegaFiLoginActivity {

	private EditText textUser;
	private EditText textPassword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		textUser = (EditText)findViewById(R.id.email);
		textPassword = (EditText)findViewById(R.id.password);
		textUser.setError("Aqui va el nombre de usuario");
		textPassword.setError("Aqui va la contraseña");
		textUser.setError(null);
	}
	
	public void nextHome(View boton){
//		Intent homeView=new Intent(this,HomeActivity.class );
//		startActivity(homeView);
		Intent splashView=new Intent(this, SplashOmegaFiActivity.class);
		startActivity(splashView);
	}
	
	public void activityForgotLogin(View textview){
		Intent activityForgotLogin=new Intent(this,ForgotLoginActivity.class );
		startActivity(activityForgotLogin);
	}

}
