package com.appsolution.omegafi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.appsolution.layouts.CustomDatePickerDialog;
import com.appsolution.layouts.DialogInformationOF;
import com.appsolution.layouts.DialogSelectableOF;
import com.appsolution.layouts.RowEditInformation;
import com.appsolution.layouts.RowEditNameTopInfo;
import com.appsolution.layouts.RowEditTextOmegaFi;
import com.appsolution.layouts.RowInformation;
import com.appsolution.layouts.RowSpinnerNameTopInfo;
import com.appsolution.layouts.RowToogleOmegaFi;
import com.appsolution.layouts.SectionOmegaFi;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Spinner;

public class AddNewPaymentActivity extends OmegaFiActivity {

	private Spinner typeNewPayment;
	
	private LinearLayout linearCreditDebit;
	private RowEditTextOmegaFi rowTextNameOnCard;
	private RowInformation rowSelectCardType;
	private RowInformation rowExpirationDate;
	private RowEditTextOmegaFi rowTextNumberCard;
	private RowEditTextOmegaFi rowTextZipCode;
	private RowToogleOmegaFi rowSaveForFutureUse;
	
	private LinearLayout linearChekingAccount;
	
	private SectionOmegaFi sectionAddress;
	private RowEditNameTopInfo rowEditAddres1;
	private RowEditNameTopInfo rowEditAddres2;
	
	private LinearLayout linearCityStateZIP;
	private RowEditNameTopInfo rowCity;
	private RowSpinnerNameTopInfo rowSpinner;
	private RowEditNameTopInfo rowZIP;
	
	
	private Button buttonAddContinue;
	private CustomDatePickerDialog mDialog;
	
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
		rowSaveForFutureUse=(RowToogleOmegaFi)findViewById(R.id.rowSaveForFutureUse);
		rowExpirationDate=(RowInformation)findViewById(R.id.rowSelectExpirationDate);
		rowExpirationDate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				showExpirationDate(null);
			}
		});
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
		rowEditAddres2.setNameInfoTop("Address Line 2");
		
		linearCityStateZIP=new LinearLayout(getApplicationContext());
		linearCityStateZIP.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 
				LayoutParams.WRAP_CONTENT));
		linearCityStateZIP.setOrientation(LinearLayout.HORIZONTAL);
		linearCityStateZIP.setWeightSum(10);
		rowCity=new RowEditNameTopInfo(getApplicationContext());
		
		rowCity.setNameInfoTop("City");
		LayoutParams paramCity=new LayoutParams(0, LayoutParams.WRAP_CONTENT, 5);
		rowCity.setLayoutParams(paramCity);
		
		rowSpinner=new RowSpinnerNameTopInfo(getApplicationContext());
		rowSpinner.setNameInfoTop("State");
		LayoutParams paramsState=new LayoutParams(0, LayoutParams.WRAP_CONTENT,2);
		rowSpinner.setLayoutParams(paramsState);
		
		rowZIP=new RowEditNameTopInfo(getApplicationContext());
		rowZIP.setTypeInputEditText(InputType.TYPE_CLASS_NUMBER);
		rowZIP.setNameInfoTop("ZIP");
		LayoutParams paramsZIP=new LayoutParams(0, LayoutParams.WRAP_CONTENT,3);
		rowZIP.setLayoutParams(paramsZIP);
		
		linearCityStateZIP.addView(rowCity);
		linearCityStateZIP.addView(rowSpinner);
		linearCityStateZIP.addView(rowZIP);
		
		sectionAddress.addView(rowEditAddres1);
		sectionAddress.addView(rowEditAddres2);
		sectionAddress.addView(linearCityStateZIP);
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
	
	public void showExpirationDate(View view){
		int[] dayMonthYear=rowExpirationDate.getDayMonthYear();
		DatePickerDialog date=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				rowExpirationDate.setValueInfo(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);	
			}
		}, dayMonthYear[2], dayMonthYear[1]-1, dayMonthYear[0]);
		date.getDatePicker().setCalendarViewShown(false);
		date.show();
	}
	
	private DatePicker findDatePicker(ViewGroup group) {
        if (group != null) {
            for (int i = 0, j = group.getChildCount(); i < j; i++) {
                View child = group.getChildAt(i);
                if (child instanceof DatePicker) {
                    return (DatePicker) child;
                } else if (child instanceof ViewGroup) {
                    DatePicker result = findDatePicker((ViewGroup) child);
                    if (result != null)
                        return result;
                }
            }
        }
        return null;
    } 
}