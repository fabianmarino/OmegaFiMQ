package com.appsolution.omegafi;

import java.util.ArrayList;

import com.appsolution.layouts.DialogInformationOF;
import com.appsolution.layouts.DialogSelectableOF;
import com.appsolution.layouts.DialogTwoOptionsOF;
import com.appsolution.layouts.RowEditTextOmegaFi;
import com.appsolution.layouts.RowInformation;
import com.appsolution.layouts.SectionOmegaFi;
import com.appsolution.logic.CalendarEvent;
import com.appsolution.logic.PaymentMethod;
import com.appsolution.logic.SimpleScheduledPayment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
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
	
	private SimpleScheduledPayment selected;
	private ArrayList<PaymentMethod> methods=null;
	private int idAccount=-1;
	private boolean editableScheduled=false;
	private int indexMethod=-1;
	
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
		idAccount=bundle.getInt("id");
		editableScheduled=bundle.getBoolean("editable");
		if (!editableScheduled) {
			this.setEditableActivity(false);
		}
		selected=MainActivity.servicesOmegaFi.getHome().getAccounts().getSelected();
		chargeInformationPayment();
		refreshAtTime(100);
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
		rowEditAmount.setTextEdit("");
		RowEditTextOmegaFi.setTypeInputTextFromAttrs(4, rowEditAmount.getEditText());
		rowEditAmount.setWidthEditPercentaje(0.4f);
		rowEditAmount.setBorderBottom(true);
		 
		
		rowPaymentDate=new RowInformation(this);
		rowPaymentDate.setNameInfo("Payment Date");
		rowPaymentDate.setValueInfo("");
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
		rowPaymentMethod.setNameInfo("");
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
		String validate=validateSaveScheduled();
		if(validate==null){
			updateScheduledPayment();
		}
		else{
			OmegaFiActivity.showAlertMessage(validate, ScheduledPaymentsDetailActivity.this);
		}
	}
	
	private String validateSaveScheduled(){
		String validate=null;
		if(OmegaFiActivity.isDouble(rowEditAmount.getValueInfo1())){
			validate="Number amount invalid.";
		}
		else if(indexMethod==-1){
			validate="Paymenth method not selected.";
		}
		return validate;
	}
	
	public static int getNumCharacter(String cadena,char character){
		int veces=0;
		for (int i = 0; i <cadena.length(); i++) {
			if(cadena.charAt(i)==character){
				veces++;
			}
		}
		return veces;
	}
	
	private void showConfirmSavedPayment(){
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
				deleteScheduledPayment();
			}
		});
		yesNo.showDialog();
	}
	
	private void showConfirmDeletedScheduled(){
		final DialogInformationOF confirm=new DialogInformationOF(ScheduledPaymentsDetailActivity.this);
		confirm.setMessageDialog("Your payment has been deleted.");
		confirm.setButtonListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				confirm.dismissDialog();
				onBackPressed();
			}
		});
		confirm.showDialog();
	}
	
	
	private void deleteScheduledPayment(){
		AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>() {
			
			private int status=0;
			
			@Override
			protected void onPreExecute() {
				startProgressDialog("Deleting scheduled payment...", getResources().getString(R.string.please_wait));
			}
			
			@Override
			protected Boolean doInBackground(Void... params) {
				status=MainActivity.servicesOmegaFi.getHome().getAccounts().removeScheduledPayments(idAccount, selected.getId());
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				if(status==200){
					showConfirmDeletedScheduled();
				}
				else{
					OmegaFiActivity.showErrorConection(ScheduledPaymentsDetailActivity.this, status, "Not Found!");
				}
				stopProgressDialog();
			}
		};
		task.execute();
	}
	
	private void selectPayMethod(){
		if(!methods.isEmpty()){
			final DialogSelectableOF selectable=new DialogSelectableOF(this);
			selectable.setOptionsSelectables(AccountActivity.getPaymentMethodsList(methods));
			selectable.setTitleDialog("Select Payment Method");
			selectable.setTextButton("Save");
			selectable.setCloseOnSelectedItem(false);
			selectable.setButtonListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					indexMethod=selectable.getIndexSelected();
					rowPaymentMethod.setNameInfo(methods.get(indexMethod).getNameTypeNumber());
					selectable.dismissDialog();
				}
			});
			selectable.showDialog();
		}
		else{
			OmegaFiActivity.showAlertMessage("No payment methods", ScheduledPaymentsDetailActivity.this);
		}
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
			rowPaymentDate.setPaddingRow(10,5, 5, 5);
			rowPaymentDate.setGravityValueInfo(Gravity.CENTER_VERTICAL|Gravity.RIGHT);
			rowPaymentMethod.setOnClickListener(null);
			rowPaymentMethod.setVisibleArrow(false);
			buttonState.setText(getResources().getString(R.string.procesing_state));
			buttonSave.setVisibility(View.GONE);
			buttonDelete.setVisibility(View.GONE);
		}
	}
	
	private void chargeInformationPayment(){
		if(editableScheduled){
			chargePaymentMethods();
		}
		else{
			rowEditAmount.setTextEdit("$"+selected.getPaymentAmount());
			rowPaymentDate.setValueInfo(selected.getPaymentDate());
			rowPaymentMethod.setNameInfo(selected.getNameTypeMethodPayment());
		}
	}
	
	private void chargePaymentMethods(){
		AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>() {
			
			private int status=0;
			
			
			@Override
			protected void onPreExecute() {
				startProgressDialog("Charging...", getResources().getString(R.string.please_wait));
			}
			
			@Override
			protected Boolean doInBackground(Void... arg0) {
				Object[] statusMethods=MainActivity.servicesOmegaFi.getHome().getPaymentMethods(idAccount);
				status=(Integer)statusMethods[0];
				methods=(ArrayList<PaymentMethod>)statusMethods[1];
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				stopProgressDialog();
				if(status==200){
					rowEditAmount.setTextEdit(selected.getPaymentAmount());
					rowPaymentDate.setValueInfo(selected.getPaymentDate());
					getIndexPaymenthMethod();
					if(indexMethod!=-1)
						rowPaymentMethod.setNameInfo(methods.get(indexMethod).getNameTypeNumber());
				}
				else{
					OmegaFiActivity.showErrorConection(ScheduledPaymentsDetailActivity.this, status, "Object not found");
				}
			}
		};
		
		task.execute();
	}
	
	private void getIndexPaymenthMethod(){
		boolean found=false;
		for (int i = 0; i < methods.size()&&!found; i++) {
			if(methods.get(i).getId()==selected.getIdProfilepayment()){
				found=true;
				indexMethod=i;
			}
		}
		
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent scheduledPayments=new Intent(ScheduledPaymentsDetailActivity.this, ScheduledPaymentsActivity.class);
		scheduledPayments.putExtra("id", idAccount);
		startActivityForResult(scheduledPayments, OmegaFiActivity.ACTIVITY_SCHEDULED_PAYMENTS);
	}
	
	private void updateScheduledPayment(){
		AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>() {
			
			private int status=0;
			
			@Override
			protected void onPreExecute() {
				startProgressDialog("Updating scheduled", getResources().getString(R.string.please_wait));
			}
			
			@Override
			protected Boolean doInBackground(Void... params) {
				Object[] statusUpdated=MainActivity.servicesOmegaFi.getHome().getAccounts().updateScheduledPament(idAccount
						, selected.getId(), rowEditAmount.getValueInfo1(),
						CalendarEvent.getFormatDate(5, rowPaymentDate.getValueInfo(), "MM/dd/yyyy"), 
						methods.get(indexMethod));
				status=(Integer)statusUpdated[0];
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				if(status==200||status==201){
					showConfirmSavedPayment();
				}
				else if(status==422){
					OmegaFiActivity.showAlertMessage("Invalid request.", ScheduledPaymentsDetailActivity.this);
				}
				else{
					OmegaFiActivity.showErrorConection(ScheduledPaymentsDetailActivity.this, status, "Not found!");
				}
				stopProgressDialog();
			}
		};
		task.execute();
	}

}
