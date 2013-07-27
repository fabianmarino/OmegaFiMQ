package com.appsolution.omegafi;

import java.util.ArrayList;
import java.util.List;
import com.appsolution.interfaces.OnRowCheckListener;
import com.appsolution.layouts.RowCheckGroup;
import com.appsolution.layouts.RowCheckOmegaFi;
import com.appsolution.layouts.RowEditTextOmegaFi;
import com.appsolution.logic.AutoPayConfig;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class AutoPaymentAmountActivity extends OmegaFiActivity {

	
	private Spinner spinnerTypeAmmount;
	private RowCheckOmegaFi checkOnDueDate;
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
				rowTextMaximum.setTextEdit(AutoPayActivity.configAutoPay.getAmountEnterMax()+"");
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
				rowEditText.setTextEdit("0");
			}
			else{
				rowEditText.setTextEdit(AutoPayActivity.configAutoPay.getAmountEnterMax()+"");
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
		final RowCheckGroup group=new RowCheckGroup();
		group.setOnCheckedListener(new OnRowCheckListener() {
			
			@Override
			public void actionBeforeChecked() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void actionAfterChecked() {
				if(group.getRowSelected().isChecked()){
					Log.d("editable", "false");
					rowTextMaximum.setEditable(false);
					rowTextMaximum.setTextEdit("0");
					AutoPayActivity.configAutoPay.setAmountEnterMax(-1f);
				}
				else{
					Log.d("editable", "true");
					rowTextMaximum.setEditable(true);
					if(!rowTextMaximum.getValueInfo1().isEmpty()){
						AutoPayActivity.configAutoPay.setAmountEnterMax(Float.parseFloat(rowTextMaximum.getValueInfo1()));
					}
					else{
						rowTextMaximum.setTextEdit("0");
					}
				}
			}
		});
		group.setSingleCheckUnCheck(true);
		checkOnDueDate=new RowCheckOmegaFi(this, group);
		checkOnDueDate.getTextNameInfo().setTextColor(Color.BLACK);
		checkOnDueDate.setNameInfo("No Maximum Amount");
		
		int padding=getResources().getDimensionPixelSize(R.dimen.padding_6dp);
		checkOnDueDate.setPaddingRow(padding,0,0,0);
		rowTextMaximum=new RowEditTextOmegaFi(this);
		rowTextMaximum.setNameInfo("Maximum Amount");
		rowTextMaximum.setWidthEditPercentaje(0.40f);
		rowTextMaximum.getEditText().setKeyListener(DigitsKeyListener.getInstance(true, true));
		refreshAtTime(1000);
	}
	
	@Override
	public void onBackPressed() {
		if(spinnerTypeAmmount.getSelectedItemPosition()==1){
			if(rowEditText.getValueInfo1().isEmpty()){
				AutoPayActivity.configAutoPay.setAmountEnterMax(0f);
			}
			else{
				AutoPayActivity.configAutoPay.setAmountEnterMax(Float.parseFloat(rowEditText.getValueInfo1()));
			}
		}
		else{
			if(!checkOnDueDate.isChecked()){
				if(rowTextMaximum.getValueInfo1().isEmpty()){
					AutoPayActivity.configAutoPay.setAmountEnterMax(0f);
				}
				else{
					AutoPayActivity.configAutoPay.setAmountEnterMax(Float.parseFloat(rowTextMaximum.getValueInfo1()));
				}
			}
		}
		super.onBackPressed();
	}

}
