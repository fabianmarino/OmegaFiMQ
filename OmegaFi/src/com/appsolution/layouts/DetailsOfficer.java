package com.appsolution.layouts;

import com.appsolution.omegafi.OmegaFiActivity;
import com.appsolution.omegafi.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DetailsOfficer extends LinearLayout {

	private TextView titleOfficer;
	private TextView subTitleOfficer;
	private IconLabelVertical iconPhone;
	private IconLabelVertical iconEmail;
	
	
	public DetailsOfficer(Context context){
		super(context);
		this.initializate();
	}
	
	public DetailsOfficer(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initializate();
	}
	
	
	private void initializate(){
		LayoutInflater inflate= (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflate.inflate(R.layout.details_officer, this, true);
		
		titleOfficer=(TextView)findViewById(R.id.nameOfficerMemberRoster);
		titleOfficer.setTypeface(OmegaFiActivity.getFont(getContext(), 2));
		subTitleOfficer=(TextView)findViewById(R.id.subTitleOfficerMemberRoster);
		subTitleOfficer.setTypeface(OmegaFiActivity.getFont(getContext(), 0));
		iconPhone=(IconLabelVertical)findViewById(R.id.iconPhoneRooster);
		iconEmail=(IconLabelVertical)findViewById(R.id.iconEmailRooster);
	}
	
	public void setNameRooster(String name){
		titleOfficer.setText(name);
	}
	
	public void setPositionRooster(String text){
		subTitleOfficer.setText(text);
	}
	
	public void setPhoneRooster(String phone){
		iconPhone.setTextLabel(phone);
	}
	
	public void setEmailRooster(String email){
		iconEmail.setTextLabel(email);
	}
	
	public String getPhoneCall(){
		return iconPhone.getTextLabel();
	}
	
	public String getEmailRooster(){
		return iconEmail.getTextLabel();
	}
	

}
