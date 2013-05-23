package com.appsolution.omegafi;

import com.appsolution.layouts.RowInformation;
import com.appsolution.layouts.SectionOmegaFi;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.LinearLayout;

public class SetupAutoPay extends OmegaFiActivity {

	private SectionOmegaFi sectionSelectCard;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_auto_pay);
		sectionSelectCard=(SectionOmegaFi)findViewById(R.id.selectCreditCardSetup);
		this.completeSelectCreditCard();
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setTitle("Set Up Autopay");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
	}
	
	private void completeSelectCreditCard(){
		LinearLayout content=(LinearLayout)sectionSelectCard.findViewById(R.id.contentSectionOmegaFi);
		RowInformation row=new RowInformation(this);
		row.setNameInfo("Mastercard (1730)");
		row.setValueInfo("/");
		content.addView(row);
		
		RowInformation row1=new RowInformation(this);
		row1.setNameInfo("Mastercard (2586)");
		row1.setValueInfo("");
		content.addView(row1);
	}

}
