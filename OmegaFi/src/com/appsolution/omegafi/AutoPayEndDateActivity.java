package com.appsolution.omegafi;

import java.util.Calendar;
import java.util.Date;

import com.appsolution.interfaces.OnRowCheckListener;
import com.appsolution.layouts.RowCheckGroup;
import com.appsolution.layouts.RowCheckOmegaFi;
import com.appsolution.layouts.RowInformation;
import com.appsolution.logic.CalendarEvent;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;

public class AutoPayEndDateActivity extends OmegaFiActivity {

	private LinearLayout linearContentDate;
	private RowCheckOmegaFi checkNone;
	private RowInformation infoDate;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auto_pay_end_date);
		linearContentDate=(LinearLayout)findViewById(R.id.linearContentEndDate);
		this.completeFields();
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBarCustom.setTitle("END DATE");
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
				if(!group.getRowSelected().isChecked()){
					AutoPayActivity.configAutoPay.setEndDate(CalendarEvent.getFormatDate(5, infoDate.getValueInfo(), "MM/dd/yyyy"));
				}
				else{
					AutoPayActivity.configAutoPay.setEndDate(null);
				}
			}
		});
		group.setSingleCheckUnCheck(true);
		checkNone=new RowCheckOmegaFi(this, group);
		checkNone.getTextNameInfo().setTextColor(Color.BLACK);
		checkNone.setNameInfo("None");
		
		int padding=getResources().getDimensionPixelSize(R.dimen.padding_6dp);
		checkNone.setPaddingRow(padding,0,0,0);
		infoDate=new RowInformation(this);
		infoDate.setNameInfo("Specific End Date");
		Date today=Calendar.getInstance().getTime();
		infoDate.setValueInfo(today.getMonth()+1+"/"+today.getDate()+"/"+(today.getYear()+1900));
		infoDate.setBackgroundValueInfo(R.drawable.white_input_spinner);
		infoDate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				selectEndDate();
			}
		});
		
		linearContentDate.addView(checkNone);
		linearContentDate.addView(infoDate);
		if(AutoPayActivity.configAutoPay.getEndDate()==null){
			checkNone.setChecked(true);
		}
		else{
			infoDate.setValueInfo(CalendarEvent.getFormatDate(3, AutoPayActivity.configAutoPay.getEndDate(), "yyyy-MM-dd"));
		}
	}
	
	private void selectEndDate(){
		int[] dayMonthYear=infoDate.getDayMonthYear();
		DatePickerDialog date=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				checkNone.setChecked(false);
				infoDate.setValueInfo((monthOfYear+1)+"/"+dayOfMonth+"/"+year);	
				AutoPayActivity.configAutoPay.setEndDate(CalendarEvent.getFormatDate(6, infoDate.getValueInfo(), "MM/dd/yyyy"));
			}
		}, dayMonthYear[2],  dayMonthYear[0]-1,dayMonthYear[1]);
		date.getDatePicker().setCalendarViewShown(false);
		date.show();
	}

	@Override
	public void onBackPressed() {
		finishActivity(OmegaFiActivity.ACTIVITY_AUTO_PAY_END_DATE);
		super.onBackPressed();
	}

}
