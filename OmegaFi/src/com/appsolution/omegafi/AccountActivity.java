package com.appsolution.omegafi;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.appsolution.layouts.DialogContactAccount;
import com.appsolution.layouts.RowInformation;
import com.appsolution.layouts.SectionAccountUser;
import com.appsolution.layouts.SectionOmegaFi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;

public class AccountActivity extends OmegaFiActivity implements OnClickListener{

	private SectionOmegaFi accountContacts;
	private SectionOmegaFi accountDetails;
	private SectionOmegaFi accountSummary;
	private Activity activity;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account);
		activity=this;
		accountContacts=(SectionOmegaFi)findViewById(R.id.sectionAccountContacts);
		accountDetails=(SectionOmegaFi)findViewById(R.id.sectionAccountDetails);
		accountSummary=(SectionOmegaFi)findViewById(R.id.sectionAccountSummary);
		this.completeAccountSummary();
		this.completeAccountContacts();
		this.completeAccountDetails();
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setTitle("My Account");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
	}
	
	
	public void completeAccountContacts(){
		int sizeTitle=(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 13, getApplicationContext().getResources().getDisplayMetrics());
		int sizeSubtitle=(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 11, getApplicationContext().getResources().getDisplayMetrics());
		for (int i = 0; i < 5; i++) {
			SectionOmegaFi section=new SectionOmegaFi(this);
			section.setTitleSection("Contact #"+(i+1));
			section.setSizeTitle(sizeTitle);
			section.setSizeSubTitle(sizeSubtitle);
			section.setVisibleSubTitle(true);
			section.setSubtitleSection("Info description");
			section.setPutBorderBottom(true);
			section.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					DialogContactAccount diag=new DialogContactAccount(activity,false);
					diag.showDialog();
					
				}
			});
			accountContacts.addView(section);
		}
	}
	
	public void completeAccountDetails(){
			RowInformation section3=new RowInformation(getApplicationContext());
			section3.setNameInfo("Sheduled Payments");
			section3.setColorFontRowInformation(Color.GRAY);
			section3.setVisibleArrow(true);
			section3.setTextSizeInformation(14f);
			section3.setId(104);
			section3.setOnClickListener(this);
			accountDetails.addView(section3);
			RowInformation section=new RowInformation(getApplicationContext());
			section.setNameInfo("History");
			section.setColorFontRowInformation(Color.GRAY);
			section.setVisibleArrow(true);
			section.setTextSizeInformation(14f);
			section.setId(101);
			section.setOnClickListener(this);
			accountDetails.addView(section);
			RowInformation section1=new RowInformation(getApplicationContext());
			section1.setNameInfo("Sheduled Charges");
			section1.setColorFontRowInformation(Color.GRAY);
			section1.setVisibleArrow(true);
			section1.setTextSizeInformation(14f);
			section1.setOnClickListener(this);
			section1.setId(106);
			accountDetails.addView(section1);
			RowInformation section2=new RowInformation(getApplicationContext());
			section2.setNameInfo("Statements");
			section2.setVisibleArrow(true);
			section2.setColorFontRowInformation(Color.GRAY);
			section2.setTextSizeInformation(14f);
			section2.setId(107);
			section2.setOnClickListener(this);
			accountDetails.addView(section2);
			RowInformation section4=new RowInformation(getApplicationContext());
			section4.setNameInfo("Payment Method");
			section4.setVisibleArrow(true);
			section4.setColorFontRowInformation(Color.GRAY);
			section4.setTextSizeInformation(14f);
			section4.setId(105);
			section4.setOnClickListener(this);
			accountDetails.addView(section4);
	}
	
	public void completeAccountSummary(){
		
		float size=15f;
		for (int i = 1; i < 4; i++) {
			RowInformation row=new RowInformation(this);
			row.setNameInfo("Information "+i);
			row.setValueInfo("Value "+i);
			accountSummary.addView(row);
		}
		for (int i = 1; i < 3; i++) {
			RowInformation row=new RowInformation(this);
			row.setTextSizeInformation(11f);
			row.setBorderBottom(false);
			row.setSubInformation(true);
			row.setNameInfo("SubInformation "+i);
			row.setValueInfo("Value "+i);
			accountSummary.addView(row);
		}
		
		RowInformation currentBalance=new RowInformation(getApplicationContext());
		currentBalance.setNameInfo("Current Balance");
		currentBalance.setValueInfo("$ 335.00");
		accountSummary.addView(currentBalance);
		
	}
	
	public void activityPayNow(View button){
		Intent viewPayNow=new Intent(getApplicationContext(), PayNowActivity.class);
		startActivity(viewPayNow);
	}
	
	public void activityAutoPay(View button){
		Intent activityAuto=new Intent(this, AutoPayActivity.class);
		startActivity(activityAuto);
	}

	@Override
	public void onClick(View v) {
		Log.e("Account -> ", v.getId()+"");
		switch (v.getId()) {
		case 100:
			Intent viewMake=new Intent(this, MakePaymentActivity.class);
			startActivity(viewMake);
			break;
		case 101:
			Intent viewHistory=new Intent(this, HistoryActivity.class);
			startActivity(viewHistory);
			break;
		case 104:
			Intent viewScheduledPayments=new Intent(this, ScheduledPaymentsActivity.class);
			startActivity(viewScheduledPayments);
			break;
		case 105:
			
			break;
		case 106:
			Intent viewScheduleCharges=new Intent(this, ScheduleChargesActivity.class);
			startActivity(viewScheduleCharges);
			break;
		case 107:
			Intent viewStatements=new Intent(this, StatementsActivity.class);
			startActivity(viewStatements);
			break;
		default:
			break;
		}
		
	}
}
