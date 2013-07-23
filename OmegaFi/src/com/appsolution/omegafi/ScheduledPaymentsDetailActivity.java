package com.appsolution.omegafi;

import java.util.ArrayList;

import com.appsolution.layouts.DialogInformationOF;
import com.appsolution.layouts.DialogSelectableOF;
import com.appsolution.layouts.DialogTwoOptionsOF;
import com.appsolution.layouts.RowEditTextOmegaFi;
import com.appsolution.layouts.RowInformation;
import com.appsolution.layouts.SectionOmegaFi;
import com.appsolution.logic.PaymentMethod;
import com.appsolution.logic.SimpleScheduledPayment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.DatePickerDialog;
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
		if(!bundle.getBoolean("editable")){
			this.setEditableActivity(false);
		}
		selected=MainActivity.servicesOmegaFi.getHome().getAccounts().getSelected();
		chargePaymentMethods();
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
		rowEditAmount.setTypeInputEditText(2);
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
			rowPaymentMethod.setVisibleArrow(false);
			buttonState.setText(getResources().getString(R.string.procesing_state));
			buttonSave.setVisibility(View.GONE);
			buttonDelete.setVisibility(View.GONE);
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
					rowEditAmount.setTextEdit("$"+selected.getPaymentAmount());
					rowPaymentDate.setValueInfo(selected.getPaymentDate());
					rowPaymentMethod.setNameInfo(methods.get(getIndexPaymenthMethod()).getCardNameNumber());
				}
				else{
					OmegaFiActivity.showErrorConection(ScheduledPaymentsDetailActivity.this, status, "Object not found");
				}
			}
		};
		
		task.execute();
	}
	
	private int getIndexPaymenthMethod(){
		boolean found=false;
		int id=-1;
		for (int i = 0; i < methods.size()&&!found; i++) {
			Log.d("method", methods.get(i).getCardNameNumber());
			Log.d("methods", methods.get(i).getId()+"");
			Log.d("selected", selected.getIdProfilepayment()+"");
			if(methods.get(i).getId()==selected.getIdProfilepayment()){
				Log.d("entra a la escojencia", methods.get(i).getCardNameNumber());
				found=true;
				id=i;
			}
		}
		return id;
	}


}
