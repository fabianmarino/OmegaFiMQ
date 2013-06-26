package com.appsolution.omegafi;

import com.appsolution.layouts.DatePickerFragment;
import com.appsolution.layouts.DialogInformationOF;
import com.appsolution.layouts.DialogSelectableOF;
import com.appsolution.layouts.DialogTwoOptionsOF;
import com.appsolution.layouts.RowEditTextOmegaFi;
import com.appsolution.layouts.RowInformation;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

public class PayNowActivity extends OmegaFiActivity {

	private int enteroTest=0;
	private RowInformation rowDate;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay_now);
		rowDate=(RowInformation)findViewById(R.id.rowSelectDatePay);
	}

	@Override
	protected void optionsActionBar() {
		actionBar.setTitle("Pay Now");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
	}
	
	public void selectDatePayment(View view){
		int[] dayMonthYear=rowDate.getDayMonthYear();
		DatePickerDialog date=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				rowDate.setValueInfo(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);	
			}
		}, dayMonthYear[2], dayMonthYear[1]-1, dayMonthYear[0]);
		date.getDatePicker().setCalendarViewShown(false);
		date.show();
	}
	
	public void onBackToHome(View button){
		Intent intent=new Intent(this, HomeActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}
	
	public void selectPayMethod(View view){
		final DialogSelectableOF selectable=new DialogSelectableOF(this);
		selectable.setOptionsSelectables(selectable.getOptionsTest());
		selectable.setTitleDialog("Select Payment Method");
		selectable.setTextButton("Save");
		selectable.setButtonListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				selectable.dismissDialog();
			}
		});
		selectable.showDialog();
	}
	
	public void onSubmitPayment(View view){
		if(enteroTest==0){
			final DialogTwoOptionsOF options=new DialogTwoOptionsOF(this);
			options.setMessageDialog("You have not added a payment method");
			options.setOption1("OK");
			options.setOption2("Add New Payment Method");
			options.setListenerOption2(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					options.dismissDialog();
					addNewPaymentMethod(null);
				}
			});
			options.setListenerOption1(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					options.dismissDialog();
					
				}
			});
			options.showDialog();
		}
		else{
			final DialogTwoOptionsOF info=new DialogTwoOptionsOF(this);
			info.setMessageDialog("I am autorizing to Omega Fi to electronically debit my account for my payment. " +
					"Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore " +
					"et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut " +
					"aliquip ex ea commodo consequat.");
			info.setOption1("YES");
			info.setOption2("NO");
			info.setListenerOption1(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					info.dismissDialog();
					
				}
			});
			info.setListenerOption2(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					info.dismissDialog();
					
				}
			});
			info.showDialog();
		}
	}
	
	public void addNewPaymentMethod(View view){
		Intent activityAddNewPayment=new Intent(this, AddNewPaymentActivity.class);
		startActivity(activityAddNewPayment);
		enteroTest=1;
	}

}
