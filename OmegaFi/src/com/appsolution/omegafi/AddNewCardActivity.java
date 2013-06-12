package com.appsolution.omegafi;

import com.appsolution.layouts.RowEditInformation;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ToggleButton;


public class AddNewCardActivity extends OmegaFiActivity {

	private RowEditInformation addNameCard;
	private RowEditInformation addZipCode;
	private RowEditInformation addNumberCard;
	private RowEditInformation addExpirationCard;
	private RowEditInformation addSaveFuture;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_card);
		addNameCard=(RowEditInformation)findViewById(R.id.rowEditAddNameOnCard);
		addZipCode=(RowEditInformation)findViewById(R.id.rowEditAddZipCode);
		addNumberCard=(RowEditInformation)findViewById(R.id.rowEditAddNumberCard);
		addExpirationCard=(RowEditInformation)findViewById(R.id.rowEditAddExpirationDate);
		addSaveFuture=(RowEditInformation)findViewById(R.id.rowEditAddSaveFutureUse);
		this.completeRowEditInfo();
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setTitle("Add New Card");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
	}
	
	private void completeRowEditInfo(){
		ToggleButton onOffSave=new ToggleButton(this);
		onOffSave.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		addSaveFuture.addViewRight(onOffSave);
		
	}
}
