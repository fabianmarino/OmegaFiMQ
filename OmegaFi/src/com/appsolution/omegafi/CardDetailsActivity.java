package com.appsolution.omegafi;

import com.appsolution.layouts.RowInformation;
import com.appsolution.layouts.SectionOmegaFi;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

public class CardDetailsActivity extends OmegaFiActivity {

	private SectionOmegaFi sectionCardDetails;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card_details);
		sectionCardDetails=(SectionOmegaFi)findViewById(R.id.creditCardDetails);
		this.completeCardDetails();
	}
	
	
	protected void optionsActionBar() {
		actionBar.setTitle("View Card Details");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
	}
	
	private void completeCardDetails(){
		LinearLayout content=(LinearLayout)sectionCardDetails.findViewById(R.id.contentSectionOmegaFi);
		RowInformation row=new RowInformation(this);
		row.setNameInfo("Name");
		row.setValueInfo("Winston Smith");
		content.addView(row);
		
		RowInformation row1=new RowInformation(this);
		row1.setNameInfo("Credit Card #");
		row1.setValueInfo("**** **** **** 1730");
		content.addView(row1);
		
		RowInformation row2=new RowInformation(this);
		row2.setNameInfo("Expiration Date");
		row2.setValueInfo("MM/DD/YYYY");
		content.addView(row2);
		
		RowInformation row3=new RowInformation(this);
		row3.setNameInfo("Billing Address");
		row3.setValueInfo("");
		row3.setBorderBottom(false);
		content.addView(row3);
		
		RowInformation row4=new RowInformation(this);
		row4.setNameInfo("363 Cranfield Rd");
		row4.setPaddingRow(10, 0, 0, 0);
		row4.setValueInfo("");
		row4.setTextSizeInformation(11f);
		row4.setBorderBottom(false);
		content.addView(row4);
		
		RowInformation row5=new RowInformation(this);
		row5.setNameInfo("Roxie, MS (Mississippi) 39661-9621");
		row5.setValueInfo("");
		row5.setPaddingRow(10, 0, 0, 10);
		row5.setTextSizeInformation(11f);
		row5.setBorderBottom(false);
		content.addView(row5);
		
	}
	
	public void editCardActivity(View button){
		Intent editCardActivity=new Intent(this, EditCardInformation.class);
		startActivity(editCardActivity);
	}
}
