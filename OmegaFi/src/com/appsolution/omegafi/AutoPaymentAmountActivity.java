package com.appsolution.omegafi;

import java.text.DecimalFormat;

import com.appsolution.layouts.RowCheckBoxOmegaFi;
import com.appsolution.layouts.RowEditTextOmegaFi;
import com.appsolution.logic.AutoPayConfig;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class AutoPaymentAmountActivity extends OmegaFiActivity {

	
	private Spinner spinnerTypeAmmount;
	private RowCheckBoxOmegaFi checkOnDueDate;
	private RowEditTextOmegaFi rowTextMaximum;
	private LinearLayout linearPaymentDue;
	private LinearLayout linearSpecificAmount;
	private RowEditTextOmegaFi rowEditText;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auto_payment_amount);
		linearPaymentDue=(LinearLayout)findViewById(R.id.linearPayAmountDue);
		linearSpecificAmount=(LinearLayout)findViewById(R.id.linearSpecificAmount);
		rowEditText=(RowEditTextOmegaFi)findViewById(R.id.textSpecificAmount);
		spinnerTypeAmmount=(Spinner)findViewById(R.id.spinnerMenuSpecificPay);
//		spinnerTypeAmmount.performClick();
		spinnerTypeAmmount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				switch (position) {
				case 0:
					linearPaymentDue.setVisibility(LinearLayout.VISIBLE);
					linearSpecificAmount.setVisibility(LinearLayout.GONE);
					AutoPayActivity.configAutoPay.setTypePaymenthAmount(AutoPayConfig.PAY_AMOUNT_DUE);
					break;
				case 1:
					linearPaymentDue.setVisibility(LinearLayout.GONE);
					linearSpecificAmount.setVisibility(LinearLayout.VISIBLE);
					AutoPayActivity.configAutoPay.setTypePaymenthAmount(AutoPayConfig.PAY_SPECIFIC_AMOUNT);
					break;

				default:
					break;
				}
				
			}

			
			public void onNothingSelected(AdapterView<?> arg0) {
				refreshActivity();
				
			}
		});
		
		this.completeFields();
		linearPaymentDue.addView(checkOnDueDate);
		linearPaymentDue.addView(rowTextMaximum);
		if(AutoPayActivity.configAutoPay.getTypePaymenthAmount()==AutoPayConfig.PAY_AMOUNT_DUE){
			spinnerTypeAmmount.setSelection(0);
			if(AutoPayActivity.configAutoPay.getAmountEnterMax()>=0){
				rowTextMaximum.setTextEdit(String.format("%.2f",AutoPayActivity.configAutoPay.getAmountEnterMax()).replace(",", "."));
			}
			else{
				rowTextMaximum.setTextEdit("");
				rowTextMaximum.setEditable(false);
				checkOnDueDate.setChecked(true);
			}
		}
		else{
			spinnerTypeAmmount.setSelection(1);
			if(AutoPayActivity.configAutoPay.getAmountEnterMax()<0){
				rowEditText.setTextEdit("0.00");
			}
			else{
				rowEditText.setTextEdit(String.format("%.2f",AutoPayActivity.configAutoPay.getAmountEnterMax()).replace(",", "."));
			}
		}
		refreshAtTime(100);
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBarCustom.setTitle("PAYMENT AMOUNT");
		actionBar.setCustomView(actionBarCustom);
	}
	
	private void completeFields(){
		checkOnDueDate=new RowCheckBoxOmegaFi(this);
		checkOnDueDate.getTextNameInfo().setTextColor(Color.BLACK);
		checkOnDueDate.setNameInfo("No Maximum Amount");
		checkOnDueDate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				rowTextMaximum.setEditable(!isChecked);
			}
		});
		int padding=getResources().getDimensionPixelSize(R.dimen.padding_6dp);
		checkOnDueDate.setPaddingRow(padding,0,0,0);
		rowTextMaximum=new RowEditTextOmegaFi(this);
		rowTextMaximum.setNameInfo("Maximum Amount");
		rowTextMaximum.setWidthEditPercentaje(0.40f);
		rowTextMaximum.getEditText().setKeyListener(DigitsKeyListener.getInstance(true, true));
		refreshAtTime(1000);
	}
	
	private void saveChanges(){
		if(spinnerTypeAmmount.getSelectedItemPosition()==1){
			if(rowEditText.getValueInfo1().isEmpty()){
				AutoPayActivity.configAutoPay.setAmountEnterMax(0f);
			}
			else{
				String amount=rowEditText.getValueInfo1();
				if(RowEditTextOmegaFi.isDecimal(amount))
					AutoPayActivity.configAutoPay.setAmountEnterMax(Float.parseFloat(amount));
				else
					OmegaFiActivity.showAlertMessage("Your amount number is invalid.", this);
			}
		}
		else{
			if(!checkOnDueDate.isChecked()){
				if(rowTextMaximum.getValueInfo1().isEmpty()){
					AutoPayActivity.configAutoPay.setAmountEnterMax(0f);
				}
				else{
					String amount=rowTextMaximum.getValueInfo1();
					if(RowEditTextOmegaFi.isDecimal(amount))
						AutoPayActivity.configAutoPay.setAmountEnterMax(Float.parseFloat(amount));
					else
						OmegaFiActivity.showAlertMessage("Your amount number is invalid.", this);
				}
			}
			else{
				AutoPayActivity.configAutoPay.setAmountEnterMax(-1f);
			}
		}
	}
	
	public void saveAmountAutoPay(View button){
		saveChanges();
		finish();
	}

}
