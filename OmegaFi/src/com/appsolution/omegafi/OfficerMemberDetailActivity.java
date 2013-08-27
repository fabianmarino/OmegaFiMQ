package com.appsolution.omegafi;
import com.appsolution.layouts.IconLabelVertical;
import com.appsolution.layouts.LabelInfoVertical;
import com.appsolution.layouts.RowInformation;
import com.appsolution.logic.AddressContact;
import com.appsolution.logic.EmailContact;
import com.appsolution.logic.MemberRooster;
import com.appsolution.logic.OfficerRooster;
import com.appsolution.logic.PhoneContact;
import com.appsolution.services.Server;
import com.google.analytics.tracking.android.Log;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class OfficerMemberDetailActivity extends OmegaFiActivity {

	private ImageView photoMember;
	private LabelInfoVertical infoMemberInitiate;
	private IconLabelVertical phoneIcon;
	private IconLabelVertical emailIcon;
	private IconLabelVertical addresseIcon;
	private ImageView arrowLeft;
	private ImageView arrowCenter;
	private ImageView arrowRight;
	
	private LinearLayout linearPhone;
	private LinearLayout linearEmail;
	private LinearLayout linearAddress;
	private int idChapter=-1;
	private int idMember=-1;
	
	private RowInformation phoneMain;
	private RowInformation phoneSecundary;
	
	private RowInformation emailMain;
	private RowInformation emailSecundary;
	
	private RowInformation addressMain;
	private RowInformation addressSecundary;
	
	public static final int MEMBER_ROOSTER=0;
	public static final int OFFICER_ROOSTER=1;
	public static final String TYPE_ROOSTER="typeRooster";
	
	private String urlFacebook;
	private String urlTwitter;
	private String urlLinkedIn;
	
	private ImageView iconFace;
	private ImageView iconLinked;
	private ImageView iconTwitter;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_officer_member_detail);
		
		photoMember=(ImageView)findViewById(R.id.photoMemberDetail);
		
		infoMemberInitiate=(LabelInfoVertical)findViewById(R.id.labelInfoMemberInitiate);
		infoMemberInitiate.setTypeFaceValueInfo(OmegaFiActivity.getFont(getApplicationContext(), 0));
		
		phoneIcon=(IconLabelVertical)findViewById(R.id.phoneIconMember);
		phoneIcon.setBackgroundColor(this.getResources().getColor(R.color.blue_marine));
		emailIcon=(IconLabelVertical)findViewById(R.id.emailIconMember);
		addresseIcon=(IconLabelVertical)findViewById(R.id.addressIconMember);
		
		arrowLeft=(ImageView)findViewById(R.id.arrow_left_off);
		arrowCenter=(ImageView)findViewById(R.id.arrow_center_off);
		arrowRight=(ImageView)findViewById(R.id.arrow_right_off);
		
		linearPhone=(LinearLayout)findViewById(R.id.linearPhoneNumberO);
		linearEmail=(LinearLayout)findViewById(R.id.linearEmailProfileO);
		linearAddress=(LinearLayout)findViewById(R.id.linearAddressProfileO);
		
		phoneMain=(RowInformation)findViewById(R.id.mainPhone);
		phoneSecundary=(RowInformation)findViewById(R.id.secundaryPhone);
		
		emailMain=(RowInformation)findViewById(R.id.mainEmail);
		emailSecundary=(RowInformation)findViewById(R.id.secondaryEmail);
		
		addressMain=(RowInformation)findViewById(R.id.mainAddress);
		addressSecundary=(RowInformation)findViewById(R.id.secondaryAddress);
		
		iconFace=(ImageView)findViewById(R.id.iconFacebook);
		iconLinked=(ImageView)findViewById(R.id.iconLinkedin);
		iconTwitter=(ImageView)findViewById(R.id.iconTwitter);
		
		Bundle extras=getIntent().getExtras();
		idChapter=extras.getInt("idc");
		idMember=extras.getInt("idm");
		if(extras.getInt(OfficerMemberDetailActivity.TYPE_ROOSTER)==0){
			chargeDetailsMemberInformation();
		}
		else{
			chargeDetailsOfficerInformation();
		}
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBarCustom.setTitle("");
		actionBar.setCustomView(actionBarCustom);
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
	
	private void chargeDetailsMemberInformation(){
		AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>(){

			int status=0;
			private MemberRooster member=null;
			
			@Override
			protected void onPreExecute() {
				startProgressDialog("Loading Member...", getResources().getString(R.string.please_wait));
			}
			
			@Override
			protected Boolean doInBackground(Void... params) {
				Object[] statusMember=Server.getServer().getHome().getChapters().
						getStatusMemberRooster(idChapter, idMember);
				status=(Integer)statusMember[0];
				member=(MemberRooster)statusMember[1];
				Server.getServer().getHome().getProfile().updateProfileIfNecessary();
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				stopProgressDialog();
				if(Server.isStatusOk(status)){
					completeFieldsMember(member);
				}
				else{
					OmegaFiActivity.showErrorConection(OfficerMemberDetailActivity.this, status, getResources().getString(R.string.object_not_found),false);
				}
			}
			
		};
		task.execute();
	}
	
	private void chargeDetailsOfficerInformation(){
		AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>(){

			int status=0;
			private OfficerRooster member=null;
			
			@Override
			protected void onPreExecute() {
				startProgressDialog("Loading Officer...", getResources().getString(R.string.please_wait));
			}
			
			@Override
			protected Boolean doInBackground(Void... params) {
				Object[] statusMember=Server.getServer().getHome().getChapters().
						getStatusOfficerRooster(idChapter, idMember);
				status=(Integer)statusMember[0];
				member=(OfficerRooster)statusMember[1];
				Server.getServer().getHome().getProfile().updateProfileIfNecessary();
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				if(Server.isStatusOk(status)){
					completeFieldsMember(member);
				}
				else{
					OmegaFiActivity.showErrorConection(OfficerMemberDetailActivity.this, status, getResources().getString(R.string.object_not_found),false);
				}
				stopProgressDialog();
			}
			
		};
		task.execute();
	}
	
	private void completeFieldsMember(MemberRooster member){
		Server.chargeBitmapInImageViewAsync(member.getSourcePhoto(), member.getUrlPhoto(), photoMember);
		actionBarCustom.setTitle(member.getCompleteName().toUpperCase());
		infoMemberInitiate.setTitleLabel(member.getStatusName());
		infoMemberInitiate.setValueLabel(member.getInitiationDate());
		
		PhoneContact[] phones=member.getPhones();
		EmailContact[] emails=member.getEmails();
		AddressContact[] adresses=member.getAddresses();
		
		if(phones[0]!=null){
			phoneMain.setNameInfo(phones[0].getType().toUpperCase());
			phoneMain.setValueInfo(phones[0].getNumber());
		}
		if(phones[1]!=null){
			phoneSecundary.setNameInfo(phones[1].getType().toUpperCase());
			phoneSecundary.setValueInfo(phones[1].getNumber());
		}
		if(emails[0]!=null){
			emailMain.setNameInfo(emails[0].getType().toUpperCase());
			emailMain.setValueInfo(emails[0].getEmail());
			}
		if(emails[1]!=null){
			emailSecundary.setNameInfo(emails[1].getType().toUpperCase());
			emailSecundary.setValueInfo(emails[1].getEmail());
			
		}
		if(adresses[0]!=null){
			addressMain.setNameInfo(adresses[0].getType().toUpperCase());
			addressMain.setValueInfo(adresses[0].getLine1());
			addressMain.setValueInfo2(adresses[0].getLine2());
		}
		if(adresses[1]!=null){
			addressSecundary.setNameInfo(adresses[1].getType().toUpperCase());
			addressSecundary.setValueInfo(adresses[1].getLine1());
			addressSecundary.setValueInfo2(adresses[1].getLine2());
		}	
		configSocialButtons(member);
	}
	
	private void configSocialButtons(MemberRooster member){
		urlFacebook=member.getProfFacebook();
		urlLinkedIn=member.getProfLinked();
		urlTwitter=member.getProfTwitter();
		if(urlFacebook!=null)
			iconFace.setImageResource(R.drawable.icon_face);
		if(urlTwitter!=null)
			iconTwitter.setImageResource(R.drawable.icon_twitter);
		if(urlLinkedIn!=null)
			iconLinked.setImageResource(R.drawable.icon_linked);
		
	}
	
	
	public void onFacebookIcon(View icon){
		if(urlFacebook!=null){
			Intent i = new Intent(Intent.ACTION_VIEW,  Uri.parse(urlFacebook));
			startActivity(i);
		}
	}
	
	public void onTwitterIcon(View icon){
		if(urlTwitter!=null){
			Intent i = new Intent(Intent.ACTION_VIEW,  Uri.parse(urlTwitter));
			startActivity(i);
		}
	}
	
	public void onLinkedIcon(View icon){
		if(urlLinkedIn!=null){
			Intent i = new Intent(Intent.ACTION_VIEW,  Uri.parse(urlLinkedIn));
			startActivity(i);
		}
	}


}
