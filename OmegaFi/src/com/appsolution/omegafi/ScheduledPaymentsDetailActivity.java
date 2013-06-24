package com.appsolution.omegafi;

import com.appsolution.layouts.DialogInformationOF;
import com.appsolution.layouts.DialogTwoOptionsOF;
import com.appsolution.layouts.RowEditTextOmegaFi;
import com.appsolution.layouts.RowInformation;
import com.appsolution.layouts.SectionOmegaFi;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.support.v4.app.FragmentActivity;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;

public class ScheduledPaymentsDetailActivity extends OmegaFiActivity {

	private SectionOmegaFi sectionDetails;
	private SectionOmegaFi sectionMethod;
	
	
	private RowEditTextOmegaFi rowEditAmount;
	private RowInformation rowPaymentDate;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scheduled_payments_detail);
		sectionDetails=(SectionOmegaFi)findViewById(R.id.sectionScheduledPaymentDetails);
		sectionMethod=(SectionOmegaFi)findViewById(R.id.sectionScheduledPaymentMethod);
		
		this.completePaymentDetails();
		this.completePaymentMethod();
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setTitle("Scheduled Payments");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
	}
	
	private void completePaymentDetails(){
		rowEditAmount=new RowEditTextOmegaFi(this);
		rowEditAmount.setNameInfo("Amount");
		rowEditAmount.setTextEdit("$335.00");
		rowEditAmount.setWidthEditPercentaje(0.4f);
		rowEditAmount.setBorderBottom(true);
		 
		
		rowPaymentDate=new RowInformation(this);
		rowPaymentDate.setNameInfo("Payment Date");
		rowPaymentDate.setValueInfo("4/5/2013");
		rowPaymentDate.setBackgroundValueInfo(R.drawable.spinner_large);
		rowPaymentDate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				showDatePayment();		
			}
		});
		
		sectionDetails.addView(rowEditAmount);
		sectionDetails.addView(rowPaymentDate);
	}
	
	private void showDatePayment(){
		int[] dayMonthYear=rowPaymentDate.getDayMonthYear();
		DatePickerDialog date=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				rowPaymentDate.setValueInfo(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);	
			}
		}, dayMonthYear[2], dayMonthYear[1]-1, dayMonthYear[0]);
		date.getDatePicker().setCalendarViewShown(false);
		date.show();
	}
	
	private void completePaymentMethod(){
		int padding=(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
		
		RowInformation row=new RowInformation(this);
		row.setNameInfo("Winston Smith - Mastercard (4751)");
		row.setBorderBottom(true);
		row.setVisibleArrow(true);
		row.setPaddingRow(padding, padding, padding, padding);
		sectionMethod.addView(row);
	}
	
	public void saveSchedulePayment(View button){
		final DialogInformationOF saved=new DialogInformationOF(this);
		saved.setMessageDialog("Your paymenth has been saved");
		saved.setButtonListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				saved.dismissDialog();
			}
		});
		saved.showDialog();
	}
	
	public void deleteSchedulePayment(View button){
		final OmegaFiActivity omega=this;
		final DialogTwoOptionsOF yesNo=new DialogTwoOptionsOF(this);
		yesNo.setMessageDialog("Are you sure you want to delete this payment?");
		yesNo.setOption1("Yes");
		yesNo.setOption2("No");
		yesNo.setListenerOption1(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				yesNo.dismissDialog();
				final DialogInformationOF confirm=new DialogInformationOF(omega);
				confirm.setMessageDialog("Your payment has been deleted");
				confirm.setButtonListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						confirm.dismissDialog();
					}
				});
				confirm.showDialog();
			}
		});
		yesNo.showDialog();
	}


}
