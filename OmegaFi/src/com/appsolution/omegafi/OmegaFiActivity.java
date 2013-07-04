package com.appsolution.omegafi;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import com.actionbarsherlock.view.MenuItem;
import com.appsolution.layouts.DialogInformationOF;
import com.appsolution.layouts.ItemMenuSliding;
import com.appsolution.layouts.LayoutActionBar;
import com.appsolution.layouts.UserContactLayout;
import com.appsolution.logic.Server;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.RelativeLayout;

public class OmegaFiActivity extends SlidingFragmentActivity {
	
	public static final int THIN_FONT_OMEGAFI=0;
	public static final int CONDENSED_FONT_OMEGAFI=1;
	public static final int BOLD_FONT_OMEGAFI=2;
	
	protected com.actionbarsherlock.app.ActionBar actionBar;
	public static final Server servicesOmegaFi=new Server();
	protected SlidingMenu slidingMenu;
	private UserContactLayout userContact;
	private ItemMenuSliding itemAnnouncements;
	protected LayoutActionBar actionBarCustom;
	private ProgressDialog progressDiag;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.showSlidingMenu();
		 actionBar = getSupportActionBar();
		 actionBarCustom=new LayoutActionBar(getApplicationContext());
		 actionBarCustom.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
				 RelativeLayout.LayoutParams.WRAP_CONTENT));
		 actionBarCustom.setArrowClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
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
        slidingMenu.setActivated(false);
        userContact=(UserContactLayout)findViewById(R.id.userContactSliding);
        itemAnnouncements=(ItemMenuSliding)findViewById(R.id.menuItemAnnouncements);
        this.loadSlidingMenu();
	}
	
	public static boolean showErrorConection(Activity activity,int httpCode,String error404){
		if(error404==null){
			error404="The web service has  not found: 404 error";
		}
		final DialogInformationOF dia=new DialogInformationOF(activity);
		if(!OmegaFiLoginActivity.isOnline(activity)){
			dia.setMessageDialog("You must be connected to Internet");
		}
		else{
			switch (httpCode) {
			case 0:
				dia.setMessageDialog("Could not get any response");
				break;
			case 106:
				dia.setMessageDialog("Lost Conection");
				break;
			case 137:
				dia.setMessageDialog("Name resolution failed");
				break;
			case 401:
				dia.setMessageDialog("Incorrect Username or Password");
				break;
			case 404:
				dia.setMessageDialog(error404);
				break;
			case 422:
				dia.setMessageDialog("Passwords do not match.");
				break;
			case 500:
				dia.setMessageDialog("Internal server error.");
				break;
			case 502:
				dia.setMessageDialog("Web service is temporarily unavailable");
				break;
			default:
				dia.setMessageDialog("An error occurred: "+httpCode+" error.");
				break;
			}
		}
		dia.setButtonListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dia.dismissDialog();
				
			}
		});
		dia.showDialog();
		return true;
	}
	
	public static boolean loadImageFromURL(String fileUrl, 
			QuickContactBadge iv){
			  try {
			    URL myFileUrl = new URL (fileUrl);
			    HttpURLConnection conn =
			      (HttpURLConnection) myFileUrl.openConnection();
			    conn.setConnectTimeout(5000);
				conn.setReadTimeout(5000);
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
	
	public static Bitmap loadImageFromURL(String fileUrl ){
		Bitmap bitmap=null;
		if(fileUrl!=null){
			  try {
				    URL myFileUrl = new URL(fileUrl);
				    HttpURLConnection conn =
				      (HttpURLConnection) myFileUrl.openConnection();
				    conn.setConnectTimeout(Server.TIME_OUT);
					conn.setReadTimeout(Server.TIME_OUT);
				    conn.setDoInput(true);
				    conn.connect();
				    InputStream is = conn.getInputStream();
				    bitmap=BitmapFactory.decodeStream(is);
			  } catch (MalformedURLException e) {
				    e.printStackTrace();
				    bitmap=null;
			  } catch (Exception e) {
				    e.printStackTrace();
				    bitmap=null;
			  }
	}
			  return bitmap;
	}
	
	public static boolean loadImageFromURL(String fileUrl, 
			ImageView iv){
		if(fileUrl!=null){
				  try {
				    URL myFileUrl = new URL (fileUrl);
				    HttpURLConnection conn =
				      (HttpURLConnection) myFileUrl.openConnection();
				   conn.setConnectTimeout(5000);
				   conn.setReadTimeout(5000);
				   conn.setDoInput(true);
				    conn.connect();
				    InputStream is = conn.getInputStream();
				    iv.setImageBitmap(BitmapFactory.decodeStream(is));
				    return true;
				  } catch (MalformedURLException e) {
//				    e.printStackTrace();
				  } catch (Exception e) {
//				    e.printStackTrace();
				  }
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
		loadImageSlidingMenu(OmegaFiActivity.servicesOmegaFi.getHome().getProfile().getUrlPhotoProfile());
		userContact.setNameUserProfile(OmegaFiActivity.servicesOmegaFi.getHome().getProfile().getCompleteName());
		itemAnnouncements.setNumberNotifications(OmegaFiActivity.servicesOmegaFi.getHome().getProfile().getAnnouncementsCount());
	}
	
	public void goToHome(View item){
		if(this.getClass() != HomeActivity.class){
			Intent goToHome=new Intent(this, HomeActivity.class);
			goToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			finish();
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
		                		finish();
		                		OmegaFiActivity.servicesOmegaFi.getHome().clearHomeServices();
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
	
	public static Typeface getFont(Context context,int tipe){
		Typeface font=Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");
		switch (tipe) {
		case 0:
			font=Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");
			break;
		case 2:
			font=Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Bold.ttf");
			break;
		case 3:
			font=Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
		default:
			break;
		}
		return font;
	}
	
	public static String getStringFile(Context context, String ruta){
		StringBuilder cons=new StringBuilder();
		String linea=null;
		try {
		     InputStream in = context.getAssets().open(ruta);
		     if (in != null) {
		      InputStreamReader input = new InputStreamReader(in);
		      BufferedReader buffreader = new BufferedReader(input);
		      while ((linea = buffreader.readLine()) != null) {
		       cons.append(linea.toString());
		      }
		      in.close();
		     }
		    } catch (IOException e) {
		     e.printStackTrace();
		    }
		return cons.toString();
	}
	
	public void backActivity(View arrow){
		this.onBackPressed();
	}
	
	protected void startProgressDialog(String title, String msg){
		progressDiag=new ProgressDialog(this);
		progressDiag.setTitle(title);
		progressDiag.setMessage(msg);
		progressDiag.setCancelable(false);
		progressDiag.setIndeterminate(true);
		progressDiag.show();
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
	
	protected void stopProgressDialog(){
		progressDiag.dismiss();
		progressDiag=null;
	}
	
}