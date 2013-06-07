package com.appsolution.omegafi;

import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.appsolution.logic.Server;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;


public class OmegaFiActivity extends SherlockFragmentActivity {

	
	protected com.actionbarsherlock.app.ActionBar actionBar;
	private ArrayAdapter<String> optionsUser;
	private OnNavigationListener navigation;
	public Server servicesOmegaFi=new Server();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		actionBar = getSupportActionBar();
		this.optionsActionBar();
	}
	
	protected void optionsActionBar(){
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);
	}
	
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		com.actionbarsherlock.view.MenuInflater inflater= getSupportMenuInflater();
		inflater.inflate(R.menu.menu_action_bar, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.e("Id item seleccionado: ",item.getOrder()+"");
		Intent nextActivity=null;
		switch (item.getOrder()) {
		case 0:
				onBackPressed();
			break;
		case 1:
			nextActivity=new Intent(getApplicationContext(), HomeActivity.class);
			nextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			break;
		case 2:
			nextActivity=new Intent(getApplicationContext(), AnnouncementsActivity.class);
			break;
		case 3:
			nextActivity=new Intent(getApplicationContext(), MyProfileActivity.class);
			break;
		default:
			break;
		}
		if(nextActivity!=null)
			startActivity(nextActivity);
		return true;
	}
	
	public void myAccountActivity(){
		Intent viewAccount=new Intent(getApplicationContext(), AccountActivity.class);
		startActivity(viewAccount);
	}
}