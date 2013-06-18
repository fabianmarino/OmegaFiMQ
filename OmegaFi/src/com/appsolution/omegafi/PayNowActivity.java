package com.appsolution.omegafi;

import com.appsolution.layouts.DatePickerFragment;
import com.appsolution.layouts.DialogSelectableOF;
import com.appsolution.layouts.DialogTwoOptionsOF;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.DatePicker;

public class PayNowActivity extends OmegaFiActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay_now);
	}

	@Override
	protected void optionsActionBar() {
		actionBar.setTitle("Pay Now");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
	}
	
	public void selectDatePayment(View view){
		DatePickerDialog date=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				
			}
		}, 2011, 0, 1);
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
		final DialogTwoOptionsOF options=new DialogTwoOptionsOF(this);
		options.setMessageDialog("You have not added a payment method");
		options.setOption1("OK");
		options.setOption2("Add New Payment Method");
		options.setListenerOption1(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				options.dismissDialog();
				
			}
		});
		options.showDialog();
	}
	
	public void addNewPaymentMethod(View view){
//		android.support.v4.app.DialogFragment newFragment = new DatePickerFragment();
//		   newFragment.show(getSupportFragmentManager(), "datePicker");
		Intent activityAddNewPayment=new Intent(this, AddNewPaymentActivity.class);
		startActivity(activityAddNewPayment);
	}

}
