package com.appsolution.layouts;

import com.appsolution.omegafi.OmegaFiActivity;
import com.appsolution.omegafi.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UserContactLayout extends RelativeLayout {

	private TextView textNameUser;
	private TextView textSubtitleProfile;
	private ImageView imageProfile;
	
	public UserContactLayout(Context context){
		super(context);
		this.initialize();
	}
	
	public UserContactLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initialize();
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.UserContactLayout);
		a.recycle();
	}
	
	public void initialize(){
		LayoutInflater inflate= (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflate.inflate(R.layout.user_contact_layout, this, true);
		textNameUser=(TextView)findViewById(R.id.nameUserProfile);
		textSubtitleProfile=(TextView)findViewById(R.id.initiateUserProfile);
		imageProfile=(ImageView)findViewById(R.id.imageUserContact);
	}
	
	public void setNameUserProfile(String text){
		textNameUser.setText(text);
	}
	
	public void setSubTitleProfile(String subtitle){
		textSubtitleProfile.setText(subtitle);
	}
	
	public void chargeImageFromUrl(String url){
		OmegaFiActivity.loadImageFromURL(url, imageProfile);
	}
	
}