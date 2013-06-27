package com.appsolution.omegafi;

import com.appsolution.layouts.DialogInformationOF;
import com.appsolution.layouts.DialogSelectableOF;
import com.appsolution.layouts.DialogTwoOptionsOF;
import com.appsolution.layouts.RowEditTextOmegaFi;
import com.appsolution.layouts.RowInformation;
import com.appsolution.layouts.SectionOmegaFi;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.support.v4.app.FragmentActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

public class ScheduledPaymentsDetailActivity extends OmegaFiActivity {

	private SectionOmegaFi sectionDetails;
	private SectionOmegaFi sectionMethod;
	
	
	private RowEditTextOmegaFi rowEditAmount;
	private RowInformation rowPaymentDate;
	private RowInformation rowPaymentMethod;
	
	private Button buttonState;
	private Button buttonSave;
	private Button buttonDelete;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scheduled_payments_detail);
		sectionDetails=(SectionOmegaFi)findViewById(R.id.sectionScheduledPaymentDetails);
		sectionMethod=(SectionOmegaFi)findViewById(R.id.sectionScheduledPaymentMethod);
		buttonState=(Button)findViewById(R.id.buttonStateScheduled);
		buttonSave=(Button)findViewById(R.id.buttonSaveScheduled);
		buttonDelete=(Button)findViewById(R.id.buttonDeleteScheduled);
		
		this.completePaymentDetails();
		this.completePaymentMethod();
		
		Bundle bundle=getIntent().getExtras();
		if(!bundle.getBoolean("editable")){
			this.setEditableActivity(false);
		}
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBarCustom.setTitle("SCHEDULED PAYMENTS");
		actionBar.setCustomView(actionBarCustom);
	}
	
	private void completePaymentDetails(){
		rowEditAmount=new RowEditTextOmegaFi(this);
		rowEditAmount.setNameInfo("Amount");
		rowEditAmount.setTextEdit("$335.00");
		rowEditAmount.setTypeInputEditText(2);
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
		rowPaymentMethod=new RowInformation(this);
		rowPaymentMethod.setNameInfo("Winston Smith - Mastercard (4751)");
		rowPaymentMethod.setVisibleArrow(true);
		rowPaymentMethod.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				selectPayMethod();
				
			}
		});
		sectionMethod.addView(rowPaymentMethod);
	}
	
	public void saveSchedulePayment(View button){
		final DialogInformationOF saved=new DialogInformationOF(this);
		saved.setMessageDialog("Your payment has been saved.");
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
				confirm.setMessageDialog("Your payment has been deleted.");
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
	
	private void selectPayMethod(){
		final DialogSelectableOF selectable=new DialogSelectableOF(this);
		selectable.setOptionsSelectables(selectable.getOptionsTest());
		selectable.setTitleDialog("Select Payment Method");
		selectable.setTextButton("Save");
		selectable.setCloseOnSelectedItem(false);
		selectable.setButtonListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				selectable.dismissDialog();
			}
		});
		selectable.showDialog();
	}
	
	public void setEditableActivity(boolean editable){
		if(!editable){
			rowEditAmount.setBackgroundInputsRes(R.drawable.background_white_pure);
			rowEditAmount.setGravityEditText(Gravity.RIGHT);
			rowEditAmount.setEditable(false);
			rowEditAmount.setPaddingRow(10,15, 10, 15);
			rowPaymentDate.setBackgroundValueInfo(0);
			rowPaymentDate.setGravityValueInfo(Gravity.RIGHT);
			rowPaymentDate.setOnClickListener(null);
			rowPaymentMethod.setOnClickListener(null);
			buttonState.setText(getResources().getString(R.string.procesing_state));
			buttonSave.setVisibility(View.GONE);
			buttonDelete.setVisibility(View.GONE);
		}
	}


}
