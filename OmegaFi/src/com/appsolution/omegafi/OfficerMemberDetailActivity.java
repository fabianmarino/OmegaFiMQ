package com.appsolution.omegafi;
import com.appsolution.layouts.IconLabelVertical;
import com.appsolution.layouts.LabelInfoVertical;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class OfficerMemberDetailActivity extends OmegaFiActivity {

	
	private LabelInfoVertical infoMemberInitiate;
	private IconLabelVertical phoneIcon;
	private IconLabelVertical emailIcon;
	private IconLabelVertical addresseIcon;
	
	private LinearLayout linearPhone;
	private LinearLayout linearEmail;
	private LinearLayout linearAddress;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_officer_member_detail);
		
		infoMemberInitiate=(LabelInfoVertical)findViewById(R.id.labelInfoMemberInitiate);
		infoMemberInitiate.setTypeFaceValueInfo(OmegaFiActivity.getFont(getApplicationContext(), 0));
		
		phoneIcon=(IconLabelVertical)findViewById(R.id.phoneIconMember);
		phoneIcon.setBackgroundColor(this.getResources().getColor(R.color.blue_marine));
		emailIcon=(IconLabelVertical)findViewById(R.id.emailIconMember);
		addresseIcon=(IconLabelVertical)findViewById(R.id.addressIconMember);
		
		linearPhone=(LinearLayout)findViewById(R.id.linearPhoneNumberO);
		linearEmail=(LinearLayout)findViewById(R.id.linearEmailProfileO);
		linearAddress=(LinearLayout)findViewById(R.id.linearAddressProfileO);
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setTitle("Last, FirstName");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
	}
	

	public void phoneClick(View view){
		setUnClickAll();
		phoneIcon.setBackgroundColor(this.getResources().getColor(R.color.blue_marine));
		linearPhone.setVisibility(LinearLayout.VISIBLE);
		linearEmail.setVisibility(LinearLayout.GONE);
		linearAddress.setVisibility(LinearLayout.GONE);
	}
	
	public void emailClick(View view){
		setUnClickAll();	
		emailIcon.setBackgroundColor(this.getResources().getColor(R.color.blue_marine));
		linearPhone.setVisibility(LinearLayout.GONE);
		linearEmail.setVisibility(LinearLayout.VISIBLE);
		linearAddress.setVisibility(LinearLayout.GONE);
	}
	
	public void addressClick(View view){
		setUnClickAll();
		addresseIcon.setBackgroundColor(this.getResources().getColor(R.color.blue_marine));
		linearPhone.setVisibility(LinearLayout.GONE);
		linearEmail.setVisibility(LinearLayout.GONE);
		linearAddress.setVisibility(LinearLayout.VISIBLE);
	}
	
	private void setUnClickAll(){
		phoneIcon.setBackgroundColor(Color.TRANSPARENT);
		emailIcon.setBackgroundColor(Color.TRANSPARENT);
		addresseIcon.setBackgroundColor(Color.TRANSPARENT);
	}


}
