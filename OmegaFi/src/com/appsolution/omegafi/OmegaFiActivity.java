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
import com.appsolution.logic.CachingImage;
import com.appsolution.logic.Profile;
import com.appsolution.services.Server;
import com.google.analytics.tracking.android.EasyTracker;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.RelativeLayout;

public class OmegaFiActivity extends SlidingFragmentActivity {
	
	public static final int THIN_FONT_OMEGAFI=0;
	public static final int CONDENSED_FONT_OMEGAFI=1;
	public static final int BOLD_FONT_OMEGAFI=2;
	
	public static final int ACTIVITY_HOME=2;
	public static final int ACTIVITY_VIEW_ACCOUNT=3;
	public static final int ACTIVITY_PAY_NOW=4;
	public static final int ACTIVITY_LIST_MEMBERS=5;
	public static final int ACTIVITY_MEMBER_DETAIL=6;
	public static final int ACTIVITY_SCHEDULED_PAYMENTS=7;
	public static final int ACTIVITY_SCHEDULED_PAYMENT_DETAIL=8;
	public static final int ACTIVITY_HISTORY=9;
	public static final int ACTIVITY_SCHEDULED_OF_CHARGES=10;
	public static final int ACTIVITY_STATEMENTS=11;
	public static final int ACTIVITY_PAYMENT_METHODS=12;
	public static final int ACTIVITY_AUTO_PAY=13;
	public static final int ACTIVITY_AUTO_PAY_END_DATE=14;
	public static final int ACTIVITY_AUTO_PAY_PAYMENT_DATE=15;
	public static final int ACTIVITY_AUTO_PAY_PAYMENT_AMOUNT=16;
	public static final int ACTIVITY_MY_PROFILE=17;
	public static final int ACTIVITY_ANNOUNCEMENTS=18;
	public static final int ACTIVITY_TERMS=19;
	public static final int ACTIVITY_PRIVACY=20;
	public static final int ACTIVITY_CALENDAR=21;
	public static final int ACTIVITY_NEWS=22;
	public static final int ACTIVITY_ADD_NEW_PAYMENT=23;
	public static final int ACTIVITY_LOGOUT=24;
	
	protected com.actionbarsherlock.app.ActionBar actionBar;
	
	protected SlidingMenu slidingMenu;
	private UserContactLayout userContact;
	private ItemMenuSliding itemAnnouncements;
	protected LayoutActionBar actionBarCustom;
	private ProgressDialog progressDiag;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CookieSyncManager.createInstance(this);
		Server.getServer().restoreCookies();
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
	
	public static boolean showErrorConection(final Activity activity,final int httpCode,String error404,final boolean login){
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
				if(login)
					dia.setMessageDialog("Incorrect Username or Password.");
				else
					dia.setMessageDialog("Not logged in or your session has expired.\nPlease login to continue");
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
				if(!login&&httpCode==401){
					restartApp(activity);
				}
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
			slidingMenu.toggle();
			break;
		default:
			break;
		}
		return true;
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
	
	
	
	private void loadImageSlidingMenu(){
		if(Server.getServer().getHome().getProfile().getProfile()!=null){
			userContact.chargeImageFromUrlAsync(Server.getServer().getHome().getProfile().getProfile().getSource(), 
					Server.getServer().getHome().getProfile().getProfile().getUrlPhoto());
		}
		else{
			userContact.chargeImageTest();
		}
	}
	
	private void loadSlidingMenu(){
			loadImageSlidingMenu();
			if(Server.getServer().getHome().getProfile().getProfile()!=null){
				userContact.setNameUserProfile(Server.getServer().getHome().getProfile().getProfile().getFirstLastName());
				String nationalStatusName=Server.getServer().getHome().getProfile().getProfile().getNationalStatusName().
						equalsIgnoreCase(Profile.NATIONAL_STATUS_NAME_NONE) ? Server.getServer().getHome().getProfile().getProfile().getNationalStatusName() : "";
				userContact.setSubTitleProfile(nationalStatusName);
				itemAnnouncements.setNumberNotifications(Server.getServer().getHome().getProfile().getProfile().getAnnouncementsCount());
			}
		
	}
	
	public void goToHome(View item){
		if(this.getClass() != HomeActivity.class){
			goToHome();
		}
		else{
			slidingMenu.toggle();
		}
		
	}
	
	public void goToAnnouncements(View item){
		if(this.getClass() != AnnouncementsActivity.class){
			Intent goToAnnouncements=new Intent(getApplicationContext(), AnnouncementsActivity.class);
			startActivityForResult(goToAnnouncements,OmegaFiActivity.ACTIVITY_ANNOUNCEMENTS);
		}
		else if(this.getClass() != AnnouncementDetailActivity.class){
			slidingMenu.showContent(true);
			onBackPressed();
		}
		else{
			slidingMenu.toggle();
		}
	}
	
	public void goToLogout(View item){
		this.showConfirmateExit();
	}
	
	public void goToMyProfile(View item){
		if(this.getClass()!=MyProfileActivity.class){
			Intent goToMyProfile=new Intent(this, MyProfileActivity.class);
			startActivityForResult(goToMyProfile,OmegaFiActivity.ACTIVITY_MY_PROFILE);
		}
		else{
			slidingMenu.toggle();
		}
	}
	
	public void showConfirmateExit(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Logout of myOmegaFi?")
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
		                		logoutApp();
		                    }
		                });
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	public static void showAlertMessage(String msg, Activity activity){
		DialogInformationOF of=new DialogInformationOF(activity);
		of.setMessageDialog(msg);
		of.setButtonListener(DialogInformationOF.getDismissListener(of));
		of.showDialog();
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
		this.finish();
		try {
			this.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		super.onBackPressed();
	}
	
	protected void stopProgressDialog(){
		this.loadSlidingMenu();
		progressDiag.dismiss();
		progressDiag=null;
	}
	
	public static double getSizeInInchesScreen(Context context){
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager wm=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
	    wm.getDefaultDisplay().getMetrics(dm);
	    double x = Math.pow(dm.widthPixels/dm.xdpi,2);
	    double y = Math.pow(dm.heightPixels/dm.ydpi,2);
	    double screenInches = Math.sqrt(x+y);
	    return screenInches;
	}
	
	protected void refreshActivity(){
		int orientation=getRequestedOrientation();
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setRequestedOrientation (orientation);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		slidingMenu.showContent(true);
		EasyTracker.getInstance().activityStart(this);
	}
	
	@Override
	protected void onPause() {
		CookieSyncManager.getInstance().startSync();
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		EasyTracker.getInstance().activityStop(this);
	}
	
	@Override
	protected void onResume() {
		CookieSyncManager.getInstance().stopSync();
		super.onResume();
	}
	
	@Override
	protected void onPostResume() {
		super.onPostResume();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Runtime.getRuntime().gc();
	}
	
	protected void refreshAtTime(final int millis){
		AsyncTask<Void, Integer, Boolean> sleep=new AsyncTask<Void, Integer, Boolean>() {
			
			@Override
			protected Boolean doInBackground(Void... params) {
				try {
					Thread.sleep(millis);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				refreshActivity();
			}
		};
		sleep.execute();
	}
	
	public static  void closeAllActivities(Activity activity){
		for (int i = OmegaFiActivity.ACTIVITY_HOME; i <=23; i++) {
			activity.finishActivity(i);
			activity.finishActivityFromChild(activity, i);
		}
		
	}
	
	public void closeActivities(){
		for (int i = OmegaFiActivity.ACTIVITY_HOME; i <=23; i++) {
			finishActivity(i);
		}
	}
	public static boolean isDouble(String cad)
	 {
		
	 try
	 {
	   Double.parseDouble(cad);
	   return true;
	 }
	 catch(NumberFormatException nfe)
	 {
	   return false;
	 }
	 }
	
	private void restartApp(){
		CookieManager.getInstance().removeAllCookie();
		finish();
		closeActivities();
//		AlarmManager alm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		Intent mainActivity=new Intent(this, MainActivity.class);
		mainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//		alm.set(AlarmManager.RTC, System.currentTimeMillis() + 700, PendingIntent.getActivity(this, 0,mainActivity , 0));
		startActivity(mainActivity);
		Runtime.getRuntime().gc();
		System.gc();
		System.runFinalization();
	}
	
	
	private static void restartApp(Activity context){
		context.finish();
		Intent mainActivity=new Intent(context, MainActivity.class);
		mainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		mainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		mainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		System.runFinalization();
		Runtime.getRuntime().gc();
		System.gc();
		context.startActivity(mainActivity);
	}
	
	protected void goToHome(){
		finishActivity(OmegaFiActivity.ACTIVITY_HOME);
		finish();
		Intent home=new Intent(this, HomeActivity.class);
		home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivityForResult(home,OmegaFiActivity.ACTIVITY_HOME);
		System.runFinalization();
		Runtime.getRuntime().gc();
		System.gc();
	}
	
	protected void logoutApp(){
		Server.getServer().getHome().getProfile().setPhotoTemp(userContact.getImageUser().getDrawable());
		finish();
		Intent logout=new Intent(this, LogoutOmegaFiActivity.class);
		startActivityForResult(logout, OmegaFiActivity.ACTIVITY_LOGOUT);
	}
	
	
}