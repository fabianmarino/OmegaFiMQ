package com.appsolution.omegafi;

import java.util.ArrayList;
import java.util.List;

import com.appsolution.layouts.DialogInformationOF;
import com.appsolution.layouts.DialogSelectableOF;
import com.appsolution.layouts.RowEditInformation;
import com.appsolution.layouts.RowEditNameTopInfo;
import com.appsolution.layouts.RowEditTextOmegaFi;
import com.appsolution.layouts.RowInformation;
import com.appsolution.layouts.RowToogleOmegaFi;
import com.appsolution.layouts.SectionOmegaFi;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class AddNewPaymentActivity extends OmegaFiActivity {

	private Spinner typeNewPayment;
	
	private LinearLayout linearCreditDebit;
	private RowEditTextOmegaFi rowTextNameOnCard;
	private RowInformation rowSelectCardType;
	private RowEditTextOmegaFi rowTextNumberCard;
	private RowInformation rowEditExpirationDate;
	private RowEditTextOmegaFi rowTextZipCode;
	private RowToogleOmegaFi rowSaveForFutureUse;
	
	private LinearLayout linearChekingAccount;
	
	private SectionOmegaFi sectionAddress;
	private RowEditNameTopInfo rowEditAddres1;
	private RowEditNameTopInfo rowEditAddres2;
	
	private Button buttonAddContinue;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_payment);
		typeNewPayment=(Spinner)findViewById(R.id.spinnerCreditECheck);
		this.completeSpinnerNewPayment();
		linearCreditDebit=(LinearLayout)findViewById(R.id.linearCreditDebitCard);
		linearChekingAccount=(LinearLayout)findViewById(R.id.linearCheckingAccount);
		rowTextNameOnCard=(RowEditTextOmegaFi)findViewById(R.id.textNameOnCard);
		rowSelectCardType=(RowInformation)findViewById(R.id.selectCardType);
		rowEditExpirationDate=(RowInformation)findViewById(R.id.editExpirationDate);
		rowSaveForFutureUse=(RowToogleOmegaFi)findViewById(R.id.rowSaveForFutureUse);
		
		sectionAddress=(SectionOmegaFi)findViewById(R.id.sectionAddresAddPayment);
		
		buttonAddContinue=(Button)findViewById(R.id.buttonAddContinue);
		
		this.completeFields();
	}
	
	private void completeFields(){
		rowSaveForFutureUse.setOnChangeListenerToogle(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					buttonAddContinue.setText("Add");
				}
				else{
					buttonAddContinue.setText("Continue");
				}
			}
		});
		
		rowEditAddres1=new RowEditNameTopInfo(this);
		rowEditAddres1.setWidthFillParent(true);
		rowEditAddres1.setNameInfoTop("Address Line 1");
		
		rowEditAddres2=new RowEditNameTopInfo(this);
		rowEditAddres2.setWidthFillParent(true);
		rowEditAddres2.setNameInfoTop("Address Line 1");
		
		sectionAddress.addView(rowEditAddres1);
		sectionAddress.addView(rowEditAddres2);
		
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setTitle("Add New Payment");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
	}
	
	public void changeTypePayment(View view){
		if(linearCreditDebit.getVisibility()==LinearLayout.VISIBLE){
			linearCreditDebit.setVisibility(LinearLayout.GONE);
			linearChekingAccount.setVisibility(LinearLayout.VISIBLE);
		}
		else{
			linearChekingAccount.setVisibility(LinearLayout.GONE);
			linearCreditDebit.setVisibility(LinearLayout.VISIBLE);
		}

	}
	
	public void selectCardType(View view){
		DialogSelectableOF selectable=new DialogSelectableOF(this);
		selectable.setTitleDialog("Select Card Type");
		selectable.setTextButton(null);
		selectable.showDialog();
	}
	
	public void addContinueNewPayment(View view){
		final DialogInformationOF dialog=new DialogInformationOF(this);
		dialog.setMessageDialog("You've successfuly added a payment method");
		dialog.setButtonListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismissDialog();
			}
		});
		dialog.showDialog();
	}
	
	private void completeSpinnerNewPayment(){
		List<String> list = new ArrayList<String>();
		list.add("Credit/Debit Card");
		list.add("E-Check");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			R.layout.spinner_omegafi, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		typeNewPayment.setAdapter(dataAdapter);
		typeNewPayment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				switch (position) {
				case 0:
					linearChekingAccount.setVisibility(LinearLayout.GONE);
					linearCreditDebit.setVisibility(LinearLayout.VISIBLE);
					break;
				case 1:
					linearCreditDebit.setVisibility(LinearLayout.GONE);
					linearChekingAccount.setVisibility(LinearLayout.VISIBLE);
					break;

				default:
					break;
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub		
			}
		});
	}
}