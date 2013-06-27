package com.appsolution.omegafi;

import com.appsolution.layouts.DialogInformationOF;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class AutoPayActivity extends OmegaFiActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auto_pay);
	}

	
	@Override
	protected void optionsActionBar() {
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBarCustom.setTitle("AUTO PAY");
		actionBar.setCustomView(actionBarCustom);
	}
	
	public void autoPayEndDate(View button){
		Intent activityEndDate=new Intent(this, AutoPayEndDateActivity.class);
		startActivity(activityEndDate);
	}
	
	public void autoPaymentDate(View button){
		Intent activityPaymentDate=new Intent(this, AutoPayPaymentDateActivity.class);
		startActivity(activityPaymentDate);
	}
	
	public void autoPaymentAmount(View button){
		Intent activityPaymentAmount=new Intent(this, AutoPaymentAmountActivity.class);
		startActivity(activityPaymentAmount);
	}
	
	public void activeAutoPay(View button){
		final DialogInformationOF dialog=new DialogInformationOF(this);
		dialog.setMessageDialog("AutoPay is now active");
		dialog.setButtonListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismissDialog();
				
			}
		});
		dialog.showDialog();
	}

}
