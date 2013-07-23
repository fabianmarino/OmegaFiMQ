package com.appsolution.omegafi;
import com.appsolution.layouts.IconLabelVertical;
import com.appsolution.layouts.LabelInfoVertical;
import com.appsolution.layouts.RowInformation;
import com.appsolution.logic.MemberRooster;
import com.appsolution.logic.OfficerRooster;
import com.appsolution.logic.Server;

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
	private MemberRooster memberRooster;
	
	private RowInformation phoneMain;
	private RowInformation phoneSecundary;
	
	private RowInformation emailMain;
	private RowInformation emailSecundary;
	
	private RowInformation addressMain;
	private RowInformation addressSecundary;
	
	public static final int MEMBER_ROOSTER=0;
	public static final int OFFICER_ROOSTER=1;
	public static final String TYPE_ROOSTER="typeRooster";
	
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
		actionBarCustom.setTitle("BILLY BOB SMITHHEIMER");
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
				startProgressDialog("Charging Member", getResources().getString(R.string.please_wait));
			}
			
			@Override
			protected Boolean doInBackground(Void... params) {
				Object[] statusMember=MainActivity.servicesOmegaFi.getHome().getChapters().
						getStatusMemberRooster(idChapter, idMember);
				status=(Integer)statusMember[0];
				member=(MemberRooster)statusMember[1];
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				stopProgressDialog();
				if(status==200){
					completeFieldsMember(member);
				}
				else{
					OmegaFiActivity.showErrorConection(OfficerMemberDetailActivity.this, status, "Object not found");
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
				startProgressDialog("Charging Officer...", getResources().getString(R.string.please_wait));
			}
			
			@Override
			protected Boolean doInBackground(Void... params) {
				Object[] statusMember=MainActivity.servicesOmegaFi.getHome().getChapters().
						getStatusOfficerRooster(idChapter, idMember);
				status=(Integer)statusMember[0];
				member=(OfficerRooster)statusMember[1];
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				stopProgressDialog();
				if(status==200){
					completeFieldsMember(member);
				}
				else{
					OmegaFiActivity.showErrorConection(OfficerMemberDetailActivity.this, status, "Object not found");
				}
			}
			
		};
		task.execute();
	}
	
	private void completeFieldsMember(MemberRooster member){
		actionBarCustom.setTitle(member.getCompleteName().toUpperCase());
		
		infoMemberInitiate.setTitleLabel(member.getStatusName());
		infoMemberInitiate.setValueLabel(member.getInitiationDate());
		
		String[] phones=member.getPhones();
		String[] emails=member.getEmails();
		String[] adresses=member.getAdresses();
		
		phoneMain.setValueInfo(phones[0]);
		phoneSecundary.setValueInfo(phones[1]);
		
		emailMain.setValueInfo(emails[0]);
		emailSecundary.setValueInfo(emails[1]);
		
		
		addressMain.setValueInfo(adresses[0]);
		addressMain.setValueInfo2(adresses[0]);
		addressSecundary.setValueInfo(adresses[1]);
		addressSecundary.setValueInfo2(adresses[1]);
		
		Server.chargeBitmapInImageView(member.getSourcePhoto(), member.getUrlPhoto(), photoMember);
	}
	
	
	public void onFacebookIcon(View icon){
		Intent i = new Intent(Intent.ACTION_VIEW,  Uri.parse("https://www.facebook.com"));
		startActivity(i);
	}
	
	public void onTwitterIcon(View icon){
		Intent i = new Intent(Intent.ACTION_VIEW,  Uri.parse("https://www.twitter.com"));
		startActivity(i);
	}
	
	public void onLinkedIcon(View icon){
		Intent i = new Intent(Intent.ACTION_VIEW,  Uri.parse("http://www.linkedin.com"));
		startActivity(i);
	}


}
