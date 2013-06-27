package com.appsolution.omegafi;

import java.util.ArrayList;
import java.util.List;

import com.appsolution.layouts.DialogInformationOF;
import com.appsolution.layouts.DialogOptionsImage;
import com.appsolution.layouts.IconLabelVertical;
import com.appsolution.layouts.ImageMemberTemplate;
import com.appsolution.layouts.RowEditTextOmegaFi;
import com.appsolution.layouts.RowInformation;
import com.appsolution.layouts.SectionOmegaFi;
import com.appsolution.layouts.UserContactLayout;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class MyProfileActivity extends OmegaFiActivity {
	
	private UserContactLayout userHeader;
	
	private IconLabelVertical phoneIcon;
	private IconLabelVertical emailIcon;
	private IconLabelVertical addresseIcon;
	private ImageView arrowLeft;
	private ImageView arrowCenter;
	private ImageView arrowRight;
	
	private LinearLayout linearPhone;
	private LinearLayout linearEmail;
	private LinearLayout linearAddress;
	
	private Spinner spinnerPrefix;
	private EditText editTravelVisa;
	
	private static final int RESULT_LOAD_IMAGE=1;
	private static final int CAMERA_REQUEST = 1888; 
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_profile);
		
		userHeader=(UserContactLayout)findViewById(R.id.userContactMyProfile);
		
		phoneIcon=(IconLabelVertical)findViewById(R.id.phoneIconMyProfile);
		phoneIcon.setBackgroundColor(this.getResources().getColor(R.color.blue_marine));
		emailIcon=(IconLabelVertical)findViewById(R.id.emailIconMyProfile);
		addresseIcon=(IconLabelVertical)findViewById(R.id.addressIconMyProfile);
		
		linearPhone=(LinearLayout)findViewById(R.id.linearPhoneNumber);
		linearEmail=(LinearLayout)findViewById(R.id.linearEmailProfile);
		linearAddress=(LinearLayout)findViewById(R.id.linearAddressProfile);
		
		arrowLeft=(ImageView)findViewById(R.id.arrow_left_off_prof);
		arrowCenter=(ImageView)findViewById(R.id.arrow_center_off_prof);
		arrowRight=(ImageView)findViewById(R.id.arrow_right_off_prof);
		
		spinnerPrefix=(Spinner)findViewById(R.id.spinnerPrefixProfile);
		editTravelVisa=(EditText)findViewById(R.id.travelVisaNumber);
		Log.d("Travel", editTravelVisa.getInputType()+" numberdecimal");
		
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
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBarCustom.setTitle("MY PROFILE");
		actionBar.setCustomView(actionBarCustom);
	}
	
	private void completeImageProfile(){
		final Activity activity=this;
		userHeader.setOnClickPhoto(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final DialogOptionsImage imageOptions=new DialogOptionsImage(activity);
				imageOptions.setOnClickCamera(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						 imageOptions.dismissDialog();
						Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
		                startActivityForResult(cameraIntent, CAMERA_REQUEST); 
					}
				});
				imageOptions.setOnClickLibrary(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						 imageOptions.dismissDialog();
						Intent i = new Intent(
		                        Intent.ACTION_PICK,
		                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		                startActivityForResult(i, RESULT_LOAD_IMAGE);
					}
				});
				imageOptions.showDialog();
			}
		});
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
		arrowLeft.setVisibility(View.VISIBLE);
		arrowCenter.setVisibility(View.INVISIBLE);
		arrowRight.setVisibility(View.INVISIBLE);
	}
	
	public void emailClick(View view){
		setUnClickAll();	
		emailIcon.setBackgroundColor(this.getResources().getColor(R.color.blue_marine));
		linearPhone.setVisibility(LinearLayout.GONE);
		linearEmail.setVisibility(LinearLayout.VISIBLE);
		linearAddress.setVisibility(LinearLayout.GONE);
		arrowLeft.setVisibility(View.INVISIBLE);
		arrowCenter.setVisibility(View.VISIBLE);
		arrowRight.setVisibility(View.INVISIBLE);
	}
	
	public void addressClick(View view){
		setUnClickAll();
		addresseIcon.setBackgroundColor(this.getResources().getColor(R.color.blue_marine));
		linearPhone.setVisibility(LinearLayout.GONE);
		linearEmail.setVisibility(LinearLayout.GONE);
		linearAddress.setVisibility(LinearLayout.VISIBLE);
		arrowLeft.setVisibility(View.INVISIBLE);
		arrowCenter.setVisibility(View.INVISIBLE);
		arrowRight.setVisibility(View.VISIBLE);
	}
	
	private void setUnClickAll(){
		phoneIcon.setBackgroundColor(Color.TRANSPARENT);
		emailIcon.setBackgroundColor(Color.TRANSPARENT);
		addresseIcon.setBackgroundColor(Color.TRANSPARENT);
	}
	
	public void onSaveMyProfile(View button){
		final DialogInformationOF info=new DialogInformationOF(this);
		info.setMessageDialog("Your profile changes have been successfully");
		info.setButtonListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				info.dismissDialog();
			}
		});
		
		info.showDialog();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		 if (requestCode ==RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
	         Uri selectedImage = data.getData();
	         String[] filePathColumn = { MediaStore.Images.Media.DATA };
	 
	         Cursor cursor = getContentResolver().query(selectedImage,
	                 filePathColumn, null, null, null);
	         cursor.moveToFirst();
	         int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	         String picturePath = cursor.getString(columnIndex);
	         cursor.close();
	         userHeader.getImageUser().setImageBitmap(BitmapFactory.decodeFile(picturePath));
		 }
		 if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {  
	            Bitmap photo = (Bitmap) data.getExtras().get("data"); 
	            userHeader.getImageUser().setImageBitmap(photo);
	        }  
	}
	
	

}
