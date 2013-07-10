package com.appsolution.omegafi;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MakePaymentActivity extends OmegaFiActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_make_payment);
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBarCustom.setTitle("MAKE PAYMENT");
		actionBar.setCustomView(actionBarCustom);
	}
	
	public void viewActivityCards(View layout){
		Intent viewCards=new Intent(this, SelectCredCardActivity.class);
		startActivity(viewCards);
	}
	
	public void viewActivityDetailsCards(View layout){
		Intent viewCards=new Intent(this, CardDetailsActivity.class);
		startActivity(viewCards);
	}
	
	public void viewActivitySetuAutoPay(View layout){
		Intent viewSetup=new Intent(this,SetupAutoPay.class);
		startActivity(viewSetup);
	}
	
	public void viewActivityEditCardInfo(View layout){
		Intent viewEdit=new Intent(this,EditCardInformation.class);
		startActivity(viewEdit);
	}
	
	public void viewPayNow(View button){
		Intent viewPay=new Intent(this,PayNowActivity.class);
		startActivity(viewPay);
	}
}