package com.appsolution.omegafi;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.appsolution.logic.Server;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;


public class OmegaFiActivity extends SlidingFragmentActivity {
	
	protected com.actionbarsherlock.app.ActionBar actionBar;
	private ArrayAdapter<String> optionsUser;
	private OnNavigationListener navigation;
	public static final Server servicesOmegaFi=new Server();
	protected SlidingMenu slidingMenu;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBehindContentView(R.layout.sliding_menu);
		setSlidingActionBarEnabled(false);
		slidingMenu = getSlidingMenu();
		slidingMenu.setMode(SlidingMenu.RIGHT);
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		slidingMenu.setShadowWidthRes(R.dimen.shadow_width);
        slidingMenu.setShadowDrawable(R.drawable.shadow);
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        slidingMenu.setFadeDegree(0.35f);
//        menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
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
		Intent nextActivity=null;
		switch (item.getOrder()) {
		case 0:
				onBackPressed();
			break;
		case 1:
			if(slidingMenu.isMenuShowing()){
				slidingMenu.showContent(true);
			}
			else{
				slidingMenu.showMenu(true);
			}
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
	
	public boolean isOnline() {
	    ConnectivityManager cm = 
	         (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnected()) {
	        return true;
	    }
	    return false;
	}
}