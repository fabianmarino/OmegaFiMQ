package com.appsolution.layouts;

import com.appsolution.omegafi.OmegaFiActivity;
import com.appsolution.omegafi.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UserContactLayout extends RelativeLayout {

	private TextView textNameUser;
	private TextView textSubtitleProfile;
	private TextView textThirdLineProfile;
	private ImageView imageProfile;
	private RelativeLayout contentAll;
	
	public UserContactLayout(Context context){
		super(context);
		this.initialize();
	}
	
	public UserContactLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initialize();
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.UserContactLayout);
		
		int color=a.getColor(R.styleable.UserContactLayout_color_font_text, Color.WHITE);
		setFontColor(color);
		
		String third=a.getString(R.styleable.UserContactLayout_third_line_text);
		setThirdLine(third);
		a.recycle();
	}
	
	public void initialize(){
		LayoutInflater inflate= (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflate.inflate(R.layout.user_contact_layout, this, true);
		textNameUser=(TextView)findViewById(R.id.nameUserProfile);
		textSubtitleProfile=(TextView)findViewById(R.id.initiateUserProfile);
		textThirdLineProfile=(TextView)findViewById(R.id.initiateUniversity);
		
		imageProfile=(ImageView)findViewById(R.id.imageUserContact);
		contentAll=(RelativeLayout)findViewById(R.id.contentUserContact);
	}
	
	public void setNameUserProfile(String text){
		textNameUser.setText(text);
	}
	
	public void setSubTitleProfile(String subtitle){
		textSubtitleProfile.setText(subtitle);
	}
	
	public void setThirdLine(String text){
		if(text!=null){
			textThirdLineProfile.setVisibility(View.VISIBLE);
			textThirdLineProfile.setText(text);
		}
		else{
			textThirdLineProfile.setVisibility(View.GONE);
		}
	}
	
	public void chargeImageFromUrl(String url){
		OmegaFiActivity.loadImageFromURL(url, imageProfile);
	}
	
	public void chargeImageFromUrlAsync(final String url){
		AsyncTask<Void, Integer, Boolean> async=new AsyncTask<Void, Integer, Boolean>() {

			@Override
			protected Boolean doInBackground(Void... params) {
				OmegaFiActivity.loadImageFromURL(url, imageProfile);
				return true;
			}

		
		};
	}
	
	public void chargeImageTest(){
		imageProfile.setImageResource(R.drawable.image_photo_member);
	}
	
	public void setWidthImagContact(int width){
		imageProfile.getLayoutParams().width=width;
	}
	
	public void setBackgroundColor(int color){
		contentAll.setBackgroundColor(color);
	}
	
	public void setFontColor(int color){
			textNameUser.setTextColor(color);
			textSubtitleProfile.setTextColor(color);
			textThirdLineProfile.setTextColor(color);
	}
	
}