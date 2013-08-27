package com.appsolution.omegafi;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.appsolution.layouts.DialogInformationOF;
import com.appsolution.layouts.DialogOptionsImage;
import com.appsolution.layouts.IconLabelVertical;
import com.appsolution.layouts.RowEditNameTopInfo;
import com.appsolution.layouts.RowEditTextOmegaFi;
import com.appsolution.layouts.RowEditTextSubmit;
import com.appsolution.layouts.RowInformation;
import com.appsolution.layouts.RowToogleOmegaFi;
import com.appsolution.layouts.UserContactLayout;
import com.appsolution.logic.AddressContact;
import com.appsolution.logic.CalendarEvent;
import com.appsolution.logic.EmailContact;
import com.appsolution.logic.PhoneContact;
import com.appsolution.logic.Profile;
import com.appsolution.logic.TypeFormContact;
import com.appsolution.services.Server;

import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class MyProfileActivity extends OmegaFiActivity {
	
	private static final String namePhotoTemp="omegaFiTemp.jpg";
	
	private UserContactLayout userHeader;
	private TextView labelChangesPending;
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
	private EditText editMiddleName;
	private EditText editLastName;
	private EditText editInformalFirst;
	private EditText editParentsName;
	private EditText editTravelVisa;
	private TextView editCollegeEntry;
	private EditText editSuffix;
	private Spinner graduationYear;
	private RowToogleOmegaFi tooglePublish;
	private Profile profile;
	private Bitmap imageSelected=null;
	
	private static final int RESULT_LOAD_IMAGE=1;
	private static final int CAMERA_REQUEST = 1888;
	
	private TypeFormContact[] typePhones=new TypeFormContact[2];
	private TypeFormContact[] typeEmails=new TypeFormContact[2];
	private TypeFormContact[] typeAddresses=new TypeFormContact[2];
	private List<String> prefixes=null;
	
	private TextView labelMainNumber;
	private TextView labelSecondaryNumber;
	private TextView labelMainEmail;
	private TextView labelSecondaryEmail;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_profile);
		
		userHeader=(UserContactLayout)findViewById(R.id.userContactMyProfile);
		labelChangesPending=(TextView)findViewById(R.id.textChangesPending);
		labelChangesPending.setTypeface(OmegaFiActivity.getFont(getApplicationContext(), 3));
		phoneIcon=(IconLabelVertical)findViewById(R.id.phoneIconMyProfile);
		phoneIcon.setBackgroundColor(this.getResources().getColor(R.color.blue_marine));
		emailIcon=(IconLabelVertical)findViewById(R.id.emailIconMyProfile);
		addresseIcon=(IconLabelVertical)findViewById(R.id.addressIconMyProfile);
		
		linearPhone=(LinearLayout)findViewById(R.id.linearPhoneNumber);
		linearEmail=(LinearLayout)findViewById(R.id.linearEmailProfile);
		linearAddress=(LinearLayout)findViewById(R.id.linearAddressProfile);
		
		labelMainNumber=(TextView)findViewById(R.id.labelMainNumber);
		labelSecondaryNumber=(TextView)findViewById(R.id.labelSecondaryNumber);
		labelMainEmail=(TextView)findViewById(R.id.labelMainEmail);
		labelSecondaryEmail=(TextView)findViewById(R.id.labelSecondaryEmail);
		
		
		arrowLeft=(ImageView)findViewById(R.id.arrow_left_off_prof);
		arrowCenter=(ImageView)findViewById(R.id.arrow_center_off_prof);
		arrowRight=(ImageView)findViewById(R.id.arrow_right_off_prof);
		
		editMainNumber=(EditText)findViewById(R.id.editMainNumber);
		editSecondaryNumber=(EditText)findViewById(R.id.editSecondaryNumber);
		editMainEmail=(EditText)findViewById(R.id.editMainEmail);
		editSecondaryEmail=(EditText)findViewById(R.id.editSecondaryEmail);
		editSuffix=(EditText)findViewById(R.id.editSufixProfile);
		editTopHomeLine1=(RowEditNameTopInfo)findViewById(R.id.editTopHomeLine1);
		editTopHomeLine1.setTextSizeTop(getResources().getDimensionPixelSize(R.dimen.text_11sp));
		editTopHomeLine1.setTextTypeFaceTop(Typeface.DEFAULT);
		editTopHomeLine1.setTextColorTop(Color.GRAY);
		editTopHomeLine1.setEnabledEditText(false);
		editTopHomeLine2=(RowEditNameTopInfo)findViewById(R.id.editTopHomeLine2);
		editTopHomeLine2.setEnabledEditText(false);
		editTopSchoolLine1=(RowEditNameTopInfo)findViewById(R.id.editTopSchoolLine1);
		editTopSchoolLine1.setTextSizeTop(getResources().getDimensionPixelSize(R.dimen.text_11sp));
		editTopSchoolLine1.setTextTypeFaceTop(Typeface.DEFAULT);
		editTopSchoolLine1.setTextColorTop(Color.GRAY);
		editTopSchoolLine1.setEnabledEditText(false);
		editTopSchoolLine2=(RowEditNameTopInfo)findViewById(R.id.editTopSchoolLine2);
		editTopSchoolLine2.setEnabledEditText(false);
		tooglePublish=(RowToogleOmegaFi)findViewById(R.id.publishProfile);
		editFirstName=(EditText)findViewById(R.id.editFirstName);
		editMiddleName=(EditText)findViewById(R.id.editMiddleName);
		editLastName=(EditText)findViewById(R.id.editLastName);
		editInformalFirst=(EditText)findViewById(R.id.editInformalFirst);
		editParentsName=(EditText)findViewById(R.id.editParentsName);
		spinnerPrefix=(Spinner)findViewById(R.id.spinnerPrefixProfile);
		editTravelVisa=(EditText)findViewById(R.id.travelVisaNumber);
		editCollegeEntry=(TextView)findViewById(R.id.editCollegeEntry);
		clearCollegeEntryDate();
		graduationYear=(Spinner)findViewById(R.id.spinnerGraduationYear);
		
		this.completeSpinnerProfile();
		this.completeImageProfile();
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
//						Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
//		                startActivityForResult(cameraIntent, CAMERA_REQUEST);
						 Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
						 File file = new File(Environment.getExternalStorageDirectory()+File.separator + namePhotoTemp);
						 intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
						 startActivityForResult(intent, CAMERA_REQUEST);
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
		List<String> yearsGraduation=new ArrayList<String>();
		yearsGraduation.add("Graduation Year");
		int yearPast=0;
		if(year>1900){
			int yearFuture=year+20;
			yearPast=year-20;
			for (int i = yearPast; i <= yearFuture; i++) {
				yearsGraduation.add(i+"");
			}
		}
		else{
			int actualYear=Calendar.getInstance().getTime().getYear()+1900;
			for (int i = actualYear-20; i <= actualYear+20; i++) {
				yearsGraduation.add(i+"");
			}
		}
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
					R.layout.spinner_omegafi, yearsGraduation);
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				graduationYear.setAdapter(dataAdapter);
				if(yearPast!=0){
					graduationYear.setSelection(year-yearPast+1);
				}
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
		String errors=validateInformation();
		if(errors==null){
			updateProfile();
		}
		else{
			OmegaFiActivity.showAlertMessage(errors, this);
		}
	}
	
	private void showSucessfullUpdated(String errors){
		final DialogInformationOF info=new DialogInformationOF(MyProfileActivity.this);
		String message="Your profile changes have been successfully updated.";
		if(errors!=null){
			message=message+"\n"+errors;
		}
		info.setMessageDialog(message);
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
		BitmapFactory.Options options=new BitmapFactory.Options();
        options.inSampleSize = 8;
		 if (requestCode ==RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
	         Uri selectedImage = data.getData();
	         String[] filePathColumn = { MediaStore.Images.Media.DATA };
	         Cursor cursor = getContentResolver().query(selectedImage,
	                 filePathColumn, null, null, null);
	         cursor.moveToFirst();
	         int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	         String picturePath = cursor.getString(columnIndex);
	         cursor.close();
	         
	         imageSelected=decodeSampledBitmapFromFile(picturePath);
	         userHeader.getImageUser().setImageBitmap(imageSelected);
		 }
		 if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
	            File file = new File(Environment.getExternalStorageDirectory()+File.separator + namePhotoTemp);
	            imageSelected=decodeSampledBitmapFromFile(file.getAbsolutePath());
	            userHeader.getImageUser().setImageBitmap(imageSelected);
	        }  
	}
	
	public static Bitmap decodeSampledBitmapFromFile(String path) {
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    options.inPreferredConfig = Bitmap.Config.RGB_565;
	   	options.inJustDecodeBounds = false;
	    options.inSampleSize=8;
        Bitmap aux=BitmapFactory.decodeFile(path, options);
        ExifInterface exif=null;
		try {
			exif = new ExifInterface(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        int exifOrientation = exif.getAttributeInt(
        ExifInterface.TAG_ORIENTATION,
        ExifInterface.ORIENTATION_NORMAL);

        int rotate = 0;

        switch (exifOrientation) {
        case ExifInterface.ORIENTATION_ROTATE_90:
        rotate = 90;
        break; 

       case ExifInterface.ORIENTATION_ROTATE_180:
       rotate = 180;
       break;

       case ExifInterface.ORIENTATION_ROTATE_270:
       rotate = 270;
       break;
       }

         if (rotate != 0) {
        int w = aux.getWidth();
        int h = aux.getHeight();

//Setting pre rotate
        Matrix mtx = new Matrix();
        mtx.preRotate(rotate);

       // Rotating Bitmap & convert to ARGB_8888, required by tess
       aux = Bitmap.createBitmap(aux, 0, 0, w, h, mtx, false);
       aux = aux.copy(Bitmap.Config.ARGB_8888, true);
         }
        return aux;
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
		date.setOnCancelListener(new DatePickerDialog.OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface arg0) {
				clearCollegeEntryDate();
			}
		});
		date.getDatePicker().setCalendarViewShown(false);
		date.show();
	}
	
	private void chargeProfile(){
		AsyncTask< Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>() {
			
			private int status=0;
			private Profile prof=null;
			
			@Override
			protected void onPreExecute() {
				startProgressDialog("Loading Profile...", getResources().getString(R.string.please_wait));
			}
			
			@Override
			protected Boolean doInBackground(Void... params) {
			Object[] statusProfile=Server.getServer().getHome().getProfile().getStatusProfile();
			status=(Integer)statusProfile[0];
			prof=(Profile)statusProfile[1];
			prefixes=Server.getServer().getHome().getProfile().getPrefixesGender(prof.getGender());
			Server.getServer().getHome().getProfile().updateProfileIfNecessary();
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				if(Server.isStatusOk(status)){
					completeMyProfileActivity(prof, prefixes);
				}
				else{
					OmegaFiActivity.showErrorConection(MyProfileActivity.this, status, getResources().getString(R.string.object_not_found),false);
				}
				refreshActivity();
				stopProgressDialog();
				
			}
		};
		task.execute();
	}
	
	private void completeMyProfileActivity(Profile prof,List<String> prefixes){
		MyProfileActivity.this.profile=prof;
		userHeader.setNameUserProfile(prof.getFirstLastName()+" "+prof.getStatusName());
		String secondLine=prof.getNationalStatusName();
		if(prof.getOrganizationMember()!=null){
			secondLine=secondLine+=" - "+prof.getOrganizationMember().getNational()+"\n"
					+prof.getOrganizationMember().getChapterDesignation()+"\n"
					+prof.getOrganizationMember().getUniversity();
		}
		userHeader.setSubTitleProfile(secondLine);
		if(prof.getDateInitiatePretty()!=null)
			userHeader.setThirdLine("Initiated - "+prof.getDateInitiatePretty());
		
		completePhones(prof.getPhones());
		completeEmails(prof.getEmails());
		completeAddresses(prof.getAddresses());
		
		setChangesPending(prof.isChangesPending());
		editFirstName.setText(prof.getFirstName());
		editMiddleName.setText(prof.getMiddleName());
		editLastName.setText(prof.getLastName());
		editInformalFirst.setText(prof.getInformalFirstName());
		editParentsName.setText(prof.getParentsName());
		editTravelVisa.setText(prof.getTravelVisaNumber());
		if(prof.getDateCollegeEntryPretty()!=null){
			if(!prof.getDateCollegeEntryPretty().isEmpty())
				editCollegeEntry.setText(prof.getDateCollegeEntryPretty());
		}
		else{
			editCollegeEntry.setText("College Entry Date");
			editCollegeEntry.setTextColor(Color.GRAY);
		}
		completeSpinnerGraduationYear(prof.getGraduationYear());
		tooglePublish.setActivateOn(prof.isPublishProfile());
		editSuffix.setText(prof.getSuffix());
		userHeader.chargeImageFromUrlAsync(prof.getSource(), prof.getUrlPhoto());
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(MyProfileActivity.this,
				R.layout.spinner_omegafi, prefixes);
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinnerPrefix.setAdapter(dataAdapter);
		selectedPrefix(prof.getPrefix());
	}
	
	private void completePhones(PhoneContact[] phones){
		if(phones[0]!=null){
			labelMainNumber.setText(phones[0].getType()+" Number");
			editMainNumber.setText(phones[0].getNumber());
			}
		if(phones[1]!=null){
			labelSecondaryNumber.setText(phones[1].getType()+" Number");
			editSecondaryNumber.setText(phones[1].getNumber());
			}
	}
	
	private void completeEmails(EmailContact[] emails){
		if(emails[0]!=null){
			labelMainEmail.setText(emails[0].getType()+" Email");
			editMainEmail.setText(emails[0].getEmail());
			}
		if(emails[1]!=null){
			labelSecondaryEmail.setText(emails[1].getType()+" Email");
			editSecondaryEmail.setText(emails[1].getEmail());
			}
	}
	
	private void completeAddresses(AddressContact[] addresses){
		if(addresses[0]!=null){
			editTopHomeLine1.setNameInfoTop(addresses[0].getType()+" Address");
			editTopHomeLine1.setValueInfo(addresses[0].getLine1());
			editTopHomeLine2.setValueInfo(addresses[0].getLine2());
		}
		if(addresses[1]!=null){
			editTopSchoolLine1.setNameInfoTop(addresses[1].getType()+" Address");
			editTopSchoolLine1.setValueInfo(addresses[1].getLine1());
			editTopSchoolLine2.setValueInfo(addresses[1].getLine2());
		}
			
	}
	
	private void selectedPrefix(String prefix){
		boolean found=false;
		if(prefix!=null){
			for (int i = 0; i < spinnerPrefix.getCount()&&!found; i++) {
				if(spinnerPrefix.getItemAtPosition(i).toString().equalsIgnoreCase(prefix)){
					spinnerPrefix.setSelection(i);
					found=true;
				}
			}
		}
	}
	
	private void clearCollegeEntryDate(){
		editCollegeEntry.setText("College Entry Date");
	}
	
	private void updateProfile(){
		AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>() {
			
			int status=0;
			private String errors=null;
			
			@Override
			protected void onPreExecute() {
				startProgressDialog("Updating profile...", getResources().getString(R.string.please_wait));
			}
			
			@Override
			protected Boolean doInBackground(Void... arg0) {
				Object[] statusTypePhones=Server.getServer().getHome().getProfile().getTypePhones();
				Object[] statusTypeEmails=Server.getServer().getHome().getProfile().getTypeEmails();
				Object[] statusTypeAddresses=Server.getServer().getHome().getProfile().getTypeAddresses();
				completeTypeInformation((ArrayList<TypeFormContact>)statusTypePhones[1], 
						(ArrayList<TypeFormContact>)statusTypeEmails[1], (ArrayList<TypeFormContact>)statusTypeAddresses[1]);
				int graduationyear=(graduationYear.getSelectedItemPosition()==0) ? 0 :Integer.parseInt(graduationYear.getSelectedItem().toString());
				String dateCollege=editCollegeEntry.getText().toString().contains("/") ? 
						CalendarEvent.getFormatDate(5, editCollegeEntry.getText().toString(), "MM/dd/yyyy") : null ;
				String prefix=spinnerPrefix.getSelectedItem()!=null ? spinnerPrefix.getSelectedItem().toString() : null; 
				Object[] statusJson=Server.getServer().getHome().getProfile().updateProfileBasic
						(editFirstName.getText().toString(),editLastName.getText().toString() , editMiddleName.getText().toString(),
								prefix, editSuffix.getText().toString(), editInformalFirst.getText().toString(), 
								editParentsName.getText().toString(), graduationyear, editTravelVisa.getText().toString(), dateCollege,tooglePublish.isActivatedOn());
				status=(Integer)statusJson[0];
				if(imageSelected!=null){
					int statusImage=(Integer)Server.getServer().uploadImageProfile("picture[filename]", imageSelected)[0];
					errors=(statusImage!=200 && statusImage!=201) ? "Error("+statusImage+"): Upload Image":null;
				}
				if(Server.isStatusOk(status)){
					//updatePhoneNumbers();
					//updateEmails();
					//updateAdresses();
					profile=(Profile)Server.getServer().getHome().getProfile().getStatusProfile()[1];
				}
				Server.getServer().getHome().getProfile().updateProfileIfNecessary();
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				if(Server.isStatusOk(status)){
					completeMyProfileActivity(profile, prefixes);
					showSucessfullUpdated(errors);
				}
				else{
					OmegaFiActivity.showAlertMessage(status+" "+errors, MyProfileActivity.this);
				}
				refreshActivity();
				stopProgressDialog();
				
			}
		};
		task.execute();
	}
	
	private void completeTypeInformation(ArrayList<TypeFormContact> tPhones, ArrayList<TypeFormContact> tEmails,
			ArrayList<TypeFormContact> tAddresses){
		completeTypePhones(tPhones);
		completeTypemails(tEmails);
		completeTypeAddresses(tAddresses);
	}
	
	private void completeTypePhones(ArrayList<TypeFormContact> types){
		if(types!=null){
			for (int i = 0; i < types.size()&&i<2; i++) {
				typePhones[i]=types.get(i);
			}
		}
	}
	
	private void completeTypemails(ArrayList<TypeFormContact> types){
		if(types!=null){
			for (int i = 0; i < types.size()&&i<2; i++) {
				typeEmails[i]=types.get(i);
			}
		}
	}
	
	private void completeTypeAddresses(ArrayList<TypeFormContact> types){
		if(types!=null){
			for (int i = 0; i < types.size()&&i<2; i++) {
				typeAddresses[i]=types.get(i);
			}
		}
	}
	
	private void updatePhoneNumbers(){
		if(profile.getPhones()[0]!=null){
			if(editMainNumber.getText().toString().isEmpty()){
				Server.getServer().getHome().getProfile().deletePhoneNumber(profile.getPhones()[0].getId());
			}
			else if(!editMainNumber.getText().toString().equals((profile.getPhones()[0].getNumber()))){
				Server.getServer().getHome().getProfile().updatePhoneNumber
				(profile.getPhones()[0].getId(), editMainNumber.getText().toString(), true);
			}
		}
		else{
			if(!editMainNumber.getText().toString().isEmpty()){
				if(typePhones[0]!=null)
					Server.getServer().getHome().getProfile().createPhoneNumber(typePhones[0].getId(),
							editMainNumber.getText().toString(), true);
			}
		}
		
		if(profile.getPhones()[1]!=null){
			if(editSecondaryNumber.getText().toString().isEmpty()){
				Server.getServer().getHome().getProfile().deletePhoneNumber(profile.getPhones()[1].getId());
			}
			else if(!editSecondaryNumber.getText().toString().equals((profile.getPhones()[1].getNumber()))){
				Server.getServer().getHome().getProfile().updatePhoneNumber
				(profile.getPhones()[1].getId(), editSecondaryNumber.getText().toString(), false);
			}
		}
		else{
			if(!editSecondaryNumber.getText().toString().isEmpty()){
				if(typePhones[1]!=null)
					Server.getServer().getHome().getProfile().createPhoneNumber(typePhones[1].getId(),
							editSecondaryNumber.getText().toString(), false);
			}
		}
		
	}
	
	private void updateEmails(){
		if(profile.getEmails()[0]!=null){
			if(editMainEmail.getText().toString().isEmpty()){
				Server.getServer().getHome().getProfile().deleteEmail(profile.getEmails()[0].getId());
			}
			else if(!editMainEmail.getText().toString().equals(profile.getEmails()[0].getEmail())){
				Server.getServer().getHome().getProfile().updateEmailAddress
				(profile.getEmails()[0].getId(), editMainEmail.getText().toString(), true);
			}
		}
		else{
			if(!editMainEmail.getText().toString().isEmpty()){
				if(typeEmails[0]!=null)
					Server.getServer().getHome().getProfile().createEmail(typeEmails[0].getId(),
							editMainEmail.getText().toString(), true);
			}
		}
		
		if(profile.getEmails()[1]!=null){
			if(editSecondaryEmail.getText().toString().isEmpty()){
				Server.getServer().getHome().getProfile().deleteEmail(profile.getEmails()[1].getId());
			}
			else if(!editSecondaryEmail.getText().toString().equals(profile.getEmails()[1].getEmail())){
				Server.getServer().getHome().getProfile().updateEmailAddress
				(profile.getEmails()[1].getId(), editSecondaryEmail.getText().toString(), false);
			}
		}
		else{
			if(!editSecondaryEmail.getText().toString().isEmpty()){
				if(typeEmails[1]!=null)
					Server.getServer().getHome().getProfile().createEmail(typeEmails[1].getId(),
							editSecondaryEmail.getText().toString(), false);
			}
		}
	}
	
	private void updateAdresses(){
		if(profile.getAddresses()[0]!=null){
			if(editTopHomeLine1.getValueInfo().isEmpty()&&editTopHomeLine2.getValueInfo().isEmpty()){
				Server.getServer().getHome().getProfile().deleteAddress(profile.getAddresses()[0].getId());
			}
			else{
				if(typeAddresses[0]!=null){
					if((!editTopHomeLine1.getValueInfo().equals(profile.getAddresses()[0].getLine1())||
							!editTopHomeLine2.getValueInfo().equals(profile.getAddresses()[0].getLine2()))){
						Server.getServer().getHome().getProfile().updateAddress
						(profile.getAddresses()[0].getId(), typeAddresses[0].getId(), editTopHomeLine1.getValueInfo(), editTopHomeLine2.getValueInfo(), 
								true);
					}
				}
			}	
		}
		else{
			if(!editTopHomeLine1.getValueInfo().isEmpty()||!editTopHomeLine2.getValueInfo().isEmpty()){
				if(typeAddresses[0]!=null){
					Server.getServer().getHome().getProfile().createAddress
					(typeAddresses[0].getId(), editTopHomeLine1.getValueInfo(), editTopHomeLine2.getValueInfo(), 
							true);
				}
			}
		}
		//-----
		if(profile.getAddresses()[1]!=null){
			if(editTopSchoolLine1.getValueInfo().isEmpty()&&editTopSchoolLine2.getValueInfo().isEmpty()){
				Server.getServer().getHome().getProfile().deleteAddress(profile.getAddresses()[1].getId());
			}
			else{
				if(typeAddresses[1]!=null){
					if((!editTopSchoolLine1.getValueInfo().equals(profile.getAddresses()[1].getLine1())||
							!editTopSchoolLine2.getValueInfo().equals(profile.getAddresses()[1].getLine2()))){
						Server.getServer().getHome().getProfile().updateAddress
						(profile.getAddresses()[1].getId(), typeAddresses[1].getId(), editTopSchoolLine1.getValueInfo(), editTopSchoolLine2.getValueInfo(), 
								true);
					}
				}
			}	
		}
		else{
			if(!editTopSchoolLine1.getValueInfo().isEmpty()||!editTopSchoolLine2.getValueInfo().isEmpty()){
				if(typeAddresses[1]!=null){
					Server.getServer().getHome().getProfile().createAddress
					(typeAddresses[1].getId(), editTopSchoolLine1.getValueInfo(), editTopSchoolLine2.getValueInfo(), 
							true);
				}
			}
		}		
	}
	
	private String validateInformation(){
		String error=null;
		if(!editMainEmail.getText().toString().isEmpty()&&!RowEditTextSubmit.isValidEmail(editMainEmail.getText().toString())){
			error="Your Main Email is incorrect syntax.";
		}
		else if(!editSecondaryEmail.getText().toString().isEmpty()&&!RowEditTextSubmit.isValidEmail(editSecondaryEmail.getText().toString())){
			error="Your Secondary Email is incorrect syntax.";
		}
		else if(profile.getPhones()[0]!=null&&editMainNumber.getText().toString().isEmpty()){
			error="You cannot delete the primary phone number.";
		}
		else if(profile.getEmails()[0]!=null&&editMainEmail.getText().toString().isEmpty()){
			error="You cannot delete the primary email address.";
		}
		else if(profile.getAddresses()[0]!=null&&editTopHomeLine1.getValueInfo().isEmpty()&&editTopHomeLine2.getValueInfo().isEmpty()){
			error="You cannot delete the primary address.";
		}
		return error;
	}
	
	private void setChangesPending(boolean pending){
		int visibility=pending ? View.VISIBLE : View.GONE;
		labelChangesPending.setVisibility(visibility);
	}
	
	
}