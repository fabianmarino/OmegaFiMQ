package com.appsolution.omegafi;

import com.appsolution.layouts.RowEditTextOmegaFi;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

public class AutoPaymentAmountActivity extends OmegaFiActivity {

	private LinearLayout linearPayment;
	private RowEditTextOmegaFi rowEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auto_payment_amount);
		linearPayment=(LinearLayout)findViewById(R.id.linearPayAmount);
		rowEditText=(RowEditTextOmegaFi)findViewById(R.id.textSpecificAmount);
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setTitle("Payment Amount");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
	}
	
	public void changePaymentAmount(View button){
		if(linearPayment.getVisibility()==LinearLayout.GONE){
			linearPayment.setVisibility(LinearLayout.VISIBLE);
			rowEditText.setVisibility(LinearLayout.GONE);
		}
		else{
			linearPayment.setVisibility(LinearLayout.GONE);
			rowEditText.setVisibility(LinearLayout.VISIBLE);
		}
	}

	

}
