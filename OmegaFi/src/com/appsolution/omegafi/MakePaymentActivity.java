package com.appsolution.omegafi;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MakePaymentActivity extends OmegaFiActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_make_payment);
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setTitle("Make Payment");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
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
	
	public void viewAddNewCard(View button){
		Intent viewAdd=new Intent(this,AddNewCardActivity.class);
		startActivity(viewAdd);
	}
	
	public void viewPayNow(View button){
		Intent viewPay=new Intent(this,PayNowActivity.class);
		startActivity(viewPay);
	}
}