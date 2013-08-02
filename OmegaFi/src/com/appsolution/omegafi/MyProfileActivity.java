package com.appsolution.omegafi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.appsolution.layouts.DialogInformationOF;
import com.appsolution.layouts.DialogOptionsImage;
import com.appsolution.layouts.IconLabelVertical;
import com.appsolution.layouts.RowEditNameTopInfo;
import com.appsolution.layouts.RowEditTextOmegaFi;
import com.appsolution.layouts.RowInformation;
import com.appsolution.layouts.RowToogleOmegaFi;
import com.appsolution.layouts.UserContactLayout;
import com.appsolution.logic.Profile;
import com.appsolution.logic.Server;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
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
	
	private EditText editMainNumber;
	private EditText editSecondaryNumber;
	private EditText editMainEmail;
	private EditText editSecondaryEmail;
	private RowEditNameTopInfo editTopHomeLine1;
	private RowEditNameTopInfo editTopHomeLine2;
	private RowEditNameTopInfo editTopSchoolLine1;
	private RowEditNameTopInfo editTopSchoolLine2;
	
	
	private Spinner spinnerPrefix;
	private EditText editFirstName;
	private EditText middleName;
	private EditText lastName;
	private EditText informalFirst;
	private EditText parentsName;
	private EditText editTravelVisa;
	private TextView editCollegeEntry;
	private EditText editSuffix;
	private Spinner graduationYear;
	private RowToogleOmegaFi tooglePublish;
	
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
		
		editMainNumber=(EditText)findViewById(R.id.editMainNumber);
		editSecondaryNumber=(EditText)findViewById(R.id.editSecondaryNumber);
		editMainEmail=(EditText)findViewById(R.id.editMainEmail);
		editSecondaryEmail=(EditText)findViewById(R.id.editSecondaryEmail);
		editSuffix=(EditText)findViewById(R.id.editSufixProfile);
		editTopHomeLine1=(RowEditNameTopInfo)findViewById(R.id.editTopHomeLine1);
		editTopHomeLine2=(RowEditNameTopInfo)findViewById(R.id.editTopHomeLine2);
		editTopSchoolLine1=(RowEditNameTopInfo)findViewById(R.id.editTopSchoolLine1);
		editTopSchoolLine2=(RowEditNameTopInfo)findViewById(R.id.editTopSchoolLine2);
		tooglePublish=(RowToogleOmegaFi)findViewById(R.id.publishProfile);
		editFirstName=(EditText)findViewById(R.id.editFirstName);
		middleName=(EditText)findViewById(R.id.editMiddleName);
		lastName=(EditText)findViewById(R.id.editLastName);
		informalFirst=(EditText)findViewById(R.id.editInformalFirst);
		parentsName=(EditText)findViewById(R.id.editParentsName);
		spinnerPrefix=(Spinner)findViewById(R.id.spinnerPrefixProfile);
		editTravelVisa=(EditText)findViewById(R.id.travelVisaNumber);
		editCollegeEntry=(TextView)findViewById(R.id.editCollegeEntry);
		graduationYear=(Spinner)findViewById(R.id.spinnerGraduationYear);
		
		this.completeSpinnerProfile();
		this.completeImageProfile();
		this.completeSectionEmail();
		this.completeSectionPhone();
		this.completeSectionAddress();
		chargeProfile();
		}
	
	private void completeSpinnerProfile(){
		List<String> list = new ArrayList<String>();
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
	
	private void completeSpinnerGraduationYear(int year){
		if(year>1900){
			List<String> yearsGraduation=new ArrayList<String>();
			int yearFuture=year+10;
			int yearPast=year-15;
			for (int i = yearPast; i <= yearFuture; i++) {
				yearsGraduation.add(i+"");
			}
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
					R.layout.spinner_omegafi, yearsGraduation);
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				graduationYear.setAdapter(dataAdapter);
				graduationYear.setSelection(year-yearPast);
			}
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
	
	private int[] getDayMonthYearEntryCollege(){
		int[] date=new int[3];
		date[0]=3;
		date[1]=6;
		date[2]=2013;
		return date;
	}
	
	public void showCalendarEntryDate(View button){
		int[] dayMonthYear=this.getDayMonthYearEntryCollege();
		
		DatePickerDialog date=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				editCollegeEntry.setText((monthOfYear+1)+"/"+dayOfMonth+"/"+year);	
			}
		}, dayMonthYear[2],  dayMonthYear[0]-1,dayMonthYear[1]);
		date.getDatePicker().setCalendarViewShown(false);
		date.show();
	}
	
	private void chargeProfile(){
		AsyncTask< Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>() {
			
			private int status=0;
			private Profile profile=null;
			private List<String> prefixes=null;
			
			@Override
			protected void onPreExecute() {
				startProgressDialog("Charging Profile...", getResources().getString(R.string.please_wait));
			}
			
			@Override
			protected Boolean doInBackground(Void... params) {
				Object[] statusProfile=Server.getServer().getHome().getProfile().getStatusProfile();
				status=(Integer)statusProfile[0];
				profile=(Profile)statusProfile[1];
				prefixes=Server.getServer().getHome().getProfile().getPrefixesGender(profile.getGender());
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				if(status==200){
					userHeader.setNameUserProfile(profile.getFirstLastName());
					userHeader.setSubTitleProfile(profile.getDateInitiatePretty());
					
					completePhones(profile.getPhones());
					completeEmails(profile.getEmails());
					completeAddresses(profile.getAddresses());
					
					editFirstName.setText(profile.getFirstName());
					middleName.setText(profile.getMiddleName());
					lastName.setText(profile.getLastName());
					informalFirst.setText(profile.getInformalFirstName());
					parentsName.setText(profile.getParentsName());
					editTravelVisa.setText(profile.getTravelVisaNumber());
					if(profile.getDateCollegeEntryPretty()!=null){
						if(!profile.getDateCollegeEntryPretty().isEmpty())
							editCollegeEntry.setText(profile.getDateCollegeEntryPretty());
					}
					else{
						editCollegeEntry.setText("College Entry Date");
						editCollegeEntry.setTextColor(Color.GRAY);
					}
					completeSpinnerGraduationYear(profile.getGraduationYear());
					tooglePublish.setActivateOn(profile.isPublishProfile());
					editSuffix.setText(profile.getSuffix());
					userHeader.chargeImageFromUrlAsync(profile.getSource(), profile.getUrlPhoto());
					ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(MyProfileActivity.this,
							R.layout.spinner_omegafi, prefixes);
							dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
							spinnerPrefix.setAdapter(dataAdapter);
				}
				else{
					OmegaFiActivity.showErrorConection(MyProfileActivity.this, status, "Not Found!");
				}
				stopProgressDialog();
			}
		};
		task.execute();
	}
	
	private void completePhones(String[] phones){
		editMainNumber.setText(phones[0]);
		editSecondaryNumber.setText(phones[1]);
	}
	
	private void completeEmails(String[] emails){
		editMainEmail.setText(emails[0]);
		editSecondaryEmail.setText(emails[1]);
	}
	
	private void completeAddresses(String[] addresses){
		if(addresses[0]!=null){
			String[] home=addresses[0].split("¿");
			if(home.length>1){
				editTopHomeLine1.setValueInfo(home[0]);
				if(home[1].equalsIgnoreCase("null")){
					editTopHomeLine2.setValueInfo("");
				}
				else{
					editTopHomeLine2.setValueInfo(home[1]);
				}
			}
		}
		if(addresses[1]!=null){
			String[] school=addresses[1].split("¿");
			if(school.length>1){
				editTopSchoolLine1.setValueInfo(school[0]);
				if(school[1].equalsIgnoreCase("null")){
					editTopSchoolLine2.setValueInfo("");
				}
				else{
					editTopSchoolLine2.setValueInfo(school[1]);
				}
			}
		}
	}
	
	private void selectedPrefixe(String prefix){
		boolean found=false;
		for (int i = 0; i < spinnerPrefix.getCount()&&!found; i++) {
			if(spinnerPrefix.getItemAtPosition(i).toString().equalsIgnoreCase(prefix)){
				spinnerPrefix.setSelection(i);
				found=true;
			}
		}
	}
	
	@Override
	public void onBackPressed() {
		goToHome();
		super.onBackPressed();
	}
	
}