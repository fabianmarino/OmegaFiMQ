package com.appsolution.omegafi;

import java.util.Calendar;
import java.util.Date;

import com.appsolution.layouts.RowCheckBoxOmegaFi;
import com.appsolution.layouts.RowInformation;
import com.appsolution.logic.CalendarEvent;

import android.os.Bundle;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;

public class AutoPayEndDateActivity extends OmegaFiActivity {

	private LinearLayout linearContentDate;
	private RowCheckBoxOmegaFi checkNone;
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
		checkNone=new RowCheckBoxOmegaFi(this);
		checkNone.getTextNameInfo().setTextColor(Color.BLACK);
		checkNone.setNameInfo("None");
		int padding=getResources().getDimensionPixelSize(R.dimen.padding_6dp);
		checkNone.setPaddingRow(padding,0,0,0);
		infoDate=new RowInformation(this);
		infoDate.setNameInfo("Specific End Date");
		Calendar calToday=Calendar.getInstance();
		calToday.set(calToday.get(Calendar.YEAR), calToday.get(Calendar.MONTH), calToday.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		calToday.add(Calendar.DAY_OF_MONTH, 1);
		Date today=calToday.getTime();
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
			Log.d("Cambiado", "a null");
		}
		else{
			Log.d("end date", AutoPayActivity.configAutoPay.getEndDate());
			infoDate.setValueInfo(CalendarEvent.getFormatDate(3, AutoPayActivity.configAutoPay.getEndDateRequest(), "yyyy-MM-dd"));
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
		Calendar calToday=Calendar.getInstance();
		calToday.set(calToday.get(Calendar.YEAR), calToday.get(Calendar.MONTH), calToday.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		calToday.add(Calendar.DAY_OF_MONTH, 1);
		date.getDatePicker().setMinDate(calToday.getTimeInMillis());
		date.show();
	}

	@Override
	public void onBackPressed() {
		if(!checkNone.isChecked()){
			Log.d("Establecida", " no está seleccionado");
			AutoPayActivity.configAutoPay.setEndDate(CalendarEvent.getFormatDate(6, infoDate.getValueInfo(), "MM/dd/yyyy"));
		}
		else{
			Log.d("Nula", "no está seleccionado");
			AutoPayActivity.configAutoPay.setEndDate(null);
		}
		super.onBackPressed();
	}

}
