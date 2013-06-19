package com.appsolution.omegafi;

import java.util.ArrayList;
import java.util.List;

import com.appsolution.layouts.DialogOptionsImage;
import com.appsolution.layouts.IconLabelVertical;
import com.appsolution.layouts.ImageMemberTemplate;
import com.appsolution.layouts.RowEditTextOmegaFi;
import com.appsolution.layouts.RowInformation;
import com.appsolution.layouts.SectionOmegaFi;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class MyProfileActivity extends OmegaFiActivity {
	
	private IconLabelVertical phoneIcon;
	private IconLabelVertical emailIcon;
	private IconLabelVertical addresseIcon;
	
	private LinearLayout linearPhone;
	private LinearLayout linearEmail;
	private LinearLayout linearAddress;
	
	private Spinner spinnerPrefix;

	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_profile);
		
		phoneIcon=(IconLabelVertical)findViewById(R.id.phoneIconMyProfile);
		phoneIcon.setBackgroundColor(this.getResources().getColor(R.color.blue_marine));
		emailIcon=(IconLabelVertical)findViewById(R.id.emailIconMyProfile);
		addresseIcon=(IconLabelVertical)findViewById(R.id.addressIconMyProfile);
		
		linearPhone=(LinearLayout)findViewById(R.id.linearPhoneNumber);
		linearEmail=(LinearLayout)findViewById(R.id.linearEmailProfile);
		linearAddress=(LinearLayout)findViewById(R.id.linearAddressProfile);
		
		spinnerPrefix=(Spinner)findViewById(R.id.spinnerPrefixProfile);
		
		final Activity activity=this;
		
		this.completeSpinnerProfile();
		this.completeImageProfile();
		this.completePersonalInformation();
		this.completeSectionEmail();
		this.completeSectionPhone();
		this.completeSectionAddress();
	}
	
	private void completeSpinnerProfile(){
		List<String> list = new ArrayList<String>();
		list.add("Mr.");
		list.add("Miss.");
		list.add("Dr.");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			R.layout.spinner_omegafi, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerPrefix.setAdapter(dataAdapter);
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setTitle("My profile");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
	}
	
	private void completeImageProfile(){
		
	}
	
	private void completePersonalInformation(){
		RowInformation row1=new RowInformation(this);
		row1.setNameInfo("Prefix");
		row1.setValueInfo("Mr");
		
		RowEditTextOmegaFi rowName=new RowEditTextOmegaFi(this);
		rowName.setNameInfo("First Name");
		rowName.setTextEdit("Winston");
		rowName.setEditable(false);
		
		RowEditTextOmegaFi rowName2=new RowEditTextOmegaFi(this);
		rowName2.setNameInfo("Middle Name");
		rowName2.setTextEdit("");
		rowName2.setEditable(false);
		
		RowInformation rowSuffix=new RowInformation(this);
		rowSuffix.setNameInfo("Suffix");
		rowSuffix.setValueInfo("");
		
		RowEditTextOmegaFi informalFirst=new RowEditTextOmegaFi(this);
		informalFirst.setNameInfo("Informal First Name");
		informalFirst.setTextEdit("Will");
		informalFirst.setEditable(false);
		
		RowEditTextOmegaFi parentsName=new RowEditTextOmegaFi(this);
		parentsName.setNameInfo("Parent's Name");
		parentsName.setTextEdit("Wanda Smith");
		parentsName.setEditable(false);
		
		RowEditTextOmegaFi travelVisa=new RowEditTextOmegaFi(this);
		travelVisa.setNameInfo("Travel Visa Number");
		travelVisa.setTextEdit("");
		travelVisa.setEditable(false);
		
		RowInformation dateCollege=new RowInformation(this);
		dateCollege.setNameInfo("Date of College Entry");
		dateCollege.setValueInfo("");
		dateCollege.setImageIcon(R.drawable.calendar_1);
		
		RowEditTextOmegaFi graduationYear=new RowEditTextOmegaFi(this);
		graduationYear.setNameInfo("Graduation Year");
		graduationYear.setTextEdit("2015");
		graduationYear.setEditable(false);
		
	}
	
	private void completeSectionPhone(){
		RowEditTextOmegaFi mainPhone=new RowEditTextOmegaFi(this);
		mainPhone.setNameInfo("Main");
		mainPhone.setTextEdit("(856) 977-8768");
		mainPhone.setEditable(false);
		
		RowEditTextOmegaFi otherPhone=new RowEditTextOmegaFi(this);
		otherPhone.setNameInfo("Other");
		otherPhone.setTextEdit("(856) 977-8768");
		otherPhone.setEditable(false);
	}
	
	private void completeSectionEmail(){
		RowEditTextOmegaFi mainEmail=new RowEditTextOmegaFi(this);
		mainEmail.setNameInfo("Mail");
		mainEmail.setTextEdit("wsmith@miamiuni.edu");
		mainEmail.setEditable(false);
		
		RowEditTextOmegaFi otherEmail=new RowEditTextOmegaFi(this);
		otherEmail.setNameInfo("Other");
		otherEmail.setTextEdit("otherwsmith@miamiuni.edu");
		otherEmail.setEditable(false);
		
	}
	
	private void completeSectionAddress(){
		RowInformation homeAddress=new RowInformation(this);
		homeAddress.setNameInfo("Home");
		homeAddress.setValueInfo("2218 Burdett Ave");
		homeAddress.setValueInfo2("Troy, NY 23234-3422");
		
		RowInformation homeSchool=new RowInformation(this);
		homeSchool.setNameInfo("School");
		homeSchool.setValueInfo("2218 Burdett Ave");
		homeSchool.setValueInfo2("West Sunbury, NY 23234-3422");
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
