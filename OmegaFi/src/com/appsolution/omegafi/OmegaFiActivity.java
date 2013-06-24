package com.appsolution.omegafi;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import com.actionbarsherlock.view.MenuItem;
import com.appsolution.layouts.ItemMenuSliding;
import com.appsolution.layouts.UserContactLayout;
import com.appsolution.logic.Server;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.QuickContactBadge;

public class OmegaFiActivity extends SlidingFragmentActivity {
	
	protected com.actionbarsherlock.app.ActionBar actionBar;
	public static final Server servicesOmegaFi=new Server();
	protected SlidingMenu slidingMenu;
	private UserContactLayout userContact;
	private ItemMenuSliding itemAnnouncements;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.showSlidingMenu();
		this.optionsActionBar();
	}
	
	public void showSlidingMenu(){
		setBehindContentView(R.layout.sliding_menu);
		setSlidingActionBarEnabled(false);
		slidingMenu = getSlidingMenu();
		slidingMenu.setMode(SlidingMenu.RIGHT);
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		slidingMenu.setShadowWidthRes(R.dimen.shadow_width);
        slidingMenu.setShadowDrawable(R.drawable.shadow);
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        slidingMenu.setFadeDegree(0.35f);
        actionBar = getSupportActionBar();
        userContact=(UserContactLayout)findViewById(R.id.userContactSliding);
        itemAnnouncements=(ItemMenuSliding)findViewById(R.id.menuItemAnnouncements);
        this.loadSlidingMenu();
	}
	
	public static boolean loadImageFromURL(String fileUrl, 
			QuickContactBadge iv){
			  try {
			    URL myFileUrl = new URL (fileUrl);
			    HttpURLConnection conn =
			      (HttpURLConnection) myFileUrl.openConnection();
			    conn.setDoInput(true);
			    conn.connect();
			    InputStream is = conn.getInputStream();
			    iv.setImageBitmap(BitmapFactory.decodeStream(is));
			    return true;
			  } catch (MalformedURLException e) {
			    e.printStackTrace();
			  } catch (Exception e) {
			    e.printStackTrace();
			  }
			  return false;
			}
	
	public static boolean loadImageFromURL(String fileUrl, 
			ImageView iv){
			  try {
			    URL myFileUrl = new URL (fileUrl);
			    HttpURLConnection conn =
			      (HttpURLConnection) myFileUrl.openConnection();
			    conn.setDoInput(true);
			    conn.connect();
			    InputStream is = conn.getInputStream();
			    iv.setImageBitmap(BitmapFactory.decodeStream(is));
			    return true;
			  } catch (MalformedURLException e) {
			    e.printStackTrace();
			  } catch (Exception e) {
			    e.printStackTrace();
			  }
			  return false;
			}
	
	protected void optionsActionBar(){
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setIcon(R.drawable.logo_omega);
        actionBar.setTitle("");
	}
	
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		com.actionbarsherlock.view.MenuInflater inflater= getSupportMenuInflater();
		inflater.inflate(R.menu.menu_action_bar, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
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
	
	private void loadImageSlidingMenu(final String url){
		AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>() {
			@Override
			protected Boolean doInBackground(Void... params) {
				userContact.chargeImageFromUrl(url);
				return true;
			}
		};
		task.execute();
	}
	
	private void loadSlidingMenu(){
//		loadImageSlidingMenu(OmegaFiActivity.servicesOmegaFi.getURLProfilePhoto());
//		userContact.setNameUserProfile(OmegaFiActivity.servicesOmegaFi.getCompleteName());
		itemAnnouncements.setNumberNotifications(2);
	}
	
	public void goToHome(View item){
		if(this.getClass() != HomeActivity.class){
			Intent goToHome=new Intent(this, HomeActivity.class);
			goToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(goToHome);
		}
		else{
			slidingMenu.showContent(true);
		}
	}
	
	public void goToAnnouncements(View item){
		if(this.getClass() != AnnouncementsActivity.class){
			Intent goToAnnouncements=new Intent(this, AnnouncementsActivity.class);
			startActivity(goToAnnouncements);
		}
		else if(this.getClass() != AnnouncementDetailActivity.class){
			slidingMenu.showContent(true);
			onBackPressed();
		}
		else{
			slidingMenu.showContent(true);
		}
	}
	
	public void goToLogout(View item){
		this.showConfirmateExit();
	}
	
	public void goToMyProfile(View item){
		if(this.getClass()!=MyProfileActivity.class){
			Intent goToMyProfile=new Intent(this, MyProfileActivity.class);
			startActivity(goToMyProfile);
		}
		else{
			slidingMenu.showContent(true);
		}
	}
	
	public void showConfirmateExit(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("¿Logout OmegaFi Profile?")
		        .setTitle("Exit")
		        .setCancelable(false)
		        .setNegativeButton("No",
		                new DialogInterface.OnClickListener() {
		                    public void onClick(DialogInterface dialog, int id) {
		                        dialog.cancel();
		                    }
		                })
		        .setPositiveButton("Yes",
		                new DialogInterface.OnClickListener() {
		                    public void onClick(DialogInterface dialog, int id) {
		                    	Intent backToLogin=new Intent(getApplicationContext(), MainActivity.class);
		                		backToLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		                		startActivity(backToLogin);
		                    }
		                });
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	public static void showAlertMessage(String msg, Activity activity){
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage(msg)
		       .setCancelable(false)
		       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                //do things
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	public static int getWidthPercentageDisplay(Context context,float percentaje){
		WindowManager wm=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		Display display=wm.getDefaultDisplay();
		return (int)(display.getWidth()*percentaje);
	}
	
}