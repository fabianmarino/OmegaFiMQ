package com.appsolution.omegafi;

import com.appsolution.layouts.DialogOptionsImage;
import com.appsolution.layouts.ImageMemberTemplate;
import com.appsolution.layouts.RowEditTextOmegaFi;
import com.appsolution.layouts.RowInformation;
import com.appsolution.layouts.SectionOmegaFi;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;

public class MyProfileActivity extends OmegaFiActivity {

	private ImageMemberTemplate imageMyProfile;
	private SectionOmegaFi sectionPersonalInformation;
	private SectionOmegaFi sectionPhoneNumber;
	private SectionOmegaFi sectionEmail;
	private SectionOmegaFi sectionAddress;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_profile);
		imageMyProfile=(ImageMemberTemplate)findViewById(R.id.headMyProfile);
		final Activity activity=this;
		imageMyProfile.setImageClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DialogOptionsImage doptions=new DialogOptionsImage(activity);
				doptions.showDialog();
			}
		});
		sectionPersonalInformation=(SectionOmegaFi)findViewById(R.id.sectionPersonalInformation);
		sectionPhoneNumber=(SectionOmegaFi)findViewById(R.id.sectionMyProfilePhone);
		sectionEmail=(SectionOmegaFi)findViewById(R.id.sectionMyProfileEmail);
		sectionAddress=(SectionOmegaFi)findViewById(R.id.sectionMyProfileAddress);
		this.completeImageProfile();
		this.completePersonalInformation();
		this.completeSectionEmail();
		this.completeSectionPhone();
		this.completeSectionAddress();
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setTitle("My profile");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
	}
	
	private void completeImageProfile(){
		TextView textName=new TextView(this);
		textName.setText("Winston Smith");
		textName.setTextSize(20f);
		
		TextView textInitiate=new TextView(this);
		textInitiate.setText("Initiate 12/04/2013");
		
		imageMyProfile.addViewToBasic(textName);
		imageMyProfile.addViewToBasic(textInitiate);
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
		
		
		sectionPersonalInformation.addView(row1);
		sectionPersonalInformation.addView(rowName);
		sectionPersonalInformation.addView(rowName2);
		sectionPersonalInformation.addView(rowSuffix);
		sectionPersonalInformation.addView(informalFirst);
		sectionPersonalInformation.addView(parentsName);
		sectionPersonalInformation.addView(travelVisa);
		sectionPersonalInformation.addView(dateCollege);
		sectionPersonalInformation.addView(graduationYear);
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
		
		sectionPhoneNumber.addView(mainPhone);
		sectionPhoneNumber.addView(otherPhone);
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
		
		sectionEmail.addView(mainEmail);
		sectionEmail.addView(otherEmail);
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
		
		sectionAddress.addView(homeAddress);
		sectionAddress.addView(homeSchool);
	}
	

}
