package com.appsolution.omegafi;

import java.util.ArrayList;
import java.util.List;
import com.appsolution.layouts.RowCheckBoxOmegaFi;
import com.appsolution.layouts.RowEditInformation;
import android.os.Bundle;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Spinner;

public class AutoPayPaymentDateActivity extends OmegaFiActivity {

	private LinearLayout linearPaymentDate;
	private RowCheckBoxOmegaFi checkOnDueDate;
	private Spinner spinnerDateMonth;
	private RowEditInformation rowSpinner;
	private int activateSpinner=-1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auto_pay_payment_date);
		linearPaymentDate=(LinearLayout)findViewById(R.id.linearPaymentDate);
		rowSpinner=new RowEditInformation(getApplicationContext());
		rowSpinner.setNameInfo("Pay specific Day of Month");
		int padding=getResources().getDimensionPixelSize(R.dimen.padding_6dp);
		rowSpinner.setPaddingRow(padding, padding, padding, padding);
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				R.layout.spinner_omegafi_small, getDateOfMonth());
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		dataAdapter.setNotifyOnChange(true);
		spinnerDateMonth=new Spinner(this);
		spinnerDateMonth.setLayoutParams(new LayoutParams(getResources().getDimensionPixelSize(R.dimen.width_70dp_spinner),
				getResources().getDimensionPixelSize(R.dimen.height_edit_submit)));
		spinnerDateMonth.setDropDownWidth(getResources().getDimensionPixelSize(R.dimen.width_70dp_spinner));
		spinnerDateMonth.setBackgroundResource(R.drawable.white_input_spinner);
		spinnerDateMonth.setAdapter(dataAdapter);
		rowSpinner.addViewRight(spinnerDateMonth);
		this.completeFields();
		linearPaymentDate.addView(checkOnDueDate);
		linearPaymentDate.addView(rowSpinner);
		
		spinnerDateMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int day, long arg3) {
				if(activateSpinner>=0){
					AutoPayActivity.configAutoPay.setPaymentDayOfMonth(day+1);
				}
				else{
					activateSpinner=0;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		linearPaymentDate.postInvalidate();
		spinnerDateMonth.setSelection(AutoPayActivity.configAutoPay.getPaymentDayOfMonth()-1);
		checkOnDueDate.setChecked(AutoPayActivity.configAutoPay.isPayOnDueDate());
		
		
		refreshAtTime(100);
	}
	
	
	@Override
	protected void optionsActionBar() {
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBarCustom.setTitle("PAYMENT DATE");
		actionBar.setCustomView(actionBarCustom);
	}
	
	public List<String> getDateOfMonth(){
    	List<String> list=new ArrayList<String>();
    	for (int i = 1; i < 31; i++) 
			list.add(""+i);
    	return list;	
    }
	
	private void completeFields(){
		checkOnDueDate=new RowCheckBoxOmegaFi(this);
		checkOnDueDate.getTextNameInfo().setTextColor(Color.BLACK);
		checkOnDueDate.setNameInfo("Pay on Due Date");
		int padding=getResources().getDimensionPixelSize(R.dimen.padding_6dp);
		checkOnDueDate.setPaddingRow(padding,0,0,0);
		
	}

	
	
}
