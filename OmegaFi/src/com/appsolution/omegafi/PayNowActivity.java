package com.appsolution.omegafi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.appsolution.layouts.DialogInformationOF;
import com.appsolution.layouts.DialogSelectableOF;
import com.appsolution.layouts.DialogTwoOptionsOF;
import com.appsolution.layouts.LabelInfoVertical;
import com.appsolution.layouts.RowEditTextOmegaFi;
import com.appsolution.layouts.RowInformation;
import com.appsolution.logic.Account;
import com.appsolution.logic.CalendarEvent;
import com.appsolution.logic.PaymentMethod;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;

public class PayNowActivity extends OmegaFiActivity {

//	private int enteroTest=0;
	private RowEditTextOmegaFi rowAmount;
	private RowInformation rowDate;
	private RowInformation rowPaymentMethod;
	private LabelInfoVertical infoCurrent;
	private LabelInfoVertical infoDueOn;
	private int idAccount;
	private ArrayList<PaymentMethod> methodsPayment=null;
	private int indexMethodSelected=-1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay_now);
		rowAmount=(RowEditTextOmegaFi)findViewById(R.id.rowEditTextAmount);
		rowDate=(RowInformation)findViewById(R.id.rowSelectDatePay);
		Date today=Calendar.getInstance().getTime();
		rowDate.setValueInfo(today.getMonth()+1+"/"+today.getDate()+"/"+(today.getYear()+1900));
		rowPaymentMethod=(RowInformation)findViewById(R.id.selectPaymentMethod);
		infoCurrent=(LabelInfoVertical)findViewById(R.id.currentBalancePayNow);
		infoDueOn=(LabelInfoVertical)findViewById(R.id.dueOnPayNow);
		idAccount=getIntent().getExtras().getInt("id");
		if(idAccount!=-1){
			this.chargePayNow();
		}
	}

	@Override
	protected void optionsActionBar() {
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBarCustom.setTitle("PAY NOW");
		actionBar.setCustomView(actionBarCustom);
	}
	
	public void selectDatePayment(View view){
		int[] dayMonthYear=rowDate.getDayMonthYear();
		DatePickerDialog date=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				rowDate.setValueInfo((monthOfYear+1)+"/"+dayOfMonth+"/"+year);	
			}
		}, dayMonthYear[2],  dayMonthYear[0]-1,dayMonthYear[1]);
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
		if(!methodsPayment.isEmpty()){
			final DialogSelectableOF selectable=new DialogSelectableOF(this);
			selectable.setOptionsSelectables(AccountActivity.getPaymentMethodsList(methodsPayment));
			selectable.setTitleDialog("Select Payment Method");
			selectable.setTextButton("Save");
			selectable.setCloseOnSelectedItem(false);
			selectable.setButtonListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					rowPaymentMethod.setValueInfo(selectable.getItemSelectedSubInfo());
					rowPaymentMethod.invalidate();
					rowPaymentMethod.postInvalidate();
					indexMethodSelected=selectable.getIndexSelected();
					selectable.dismissDialog();
				}
			});
			if(indexMethodSelected!=-1){
				selectable.setSelectedIndex(indexMethodSelected);
			}
			selectable.showDialog();
		}
		else{
			this.showDialogAddNewPaymenth();
		}
	}
	
	private void showDialogAddNewPaymenth(){
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
	
	
	private void showConfirmationECheckPaymenth(){
		final DialogTwoOptionsOF info=new DialogTwoOptionsOF(this);
		info.setMessageDialog(getResources().getString(R.string.dialog_confirmation_echeck));
		info.setOption1(getResources().getString(R.string.yes));
		info.setOption2(getResources().getString(R.string.no));
		info.setListenerOption1(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				submitAsyncPayment();
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
	
	private void showThankForYourPaymenth(){
		final DialogTwoOptionsOF info=new DialogTwoOptionsOF(this);
		info.setMessageDialog(getResources().getString(R.string.thanks_payment));
		info.setOption1(getResources().getString(R.string.ok));
		info.setOption2("Make Another Payment.");
		info.setOrientationButtons(LinearLayout.VERTICAL);
		info.setListenerOption1(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				info.dismissDialog();
				onBackPressed();
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
	
	public void onSubmitPayment(View view){
		if(indexMethodSelected!=-1){
			if(this.validateFieldsPayNow()){
				PaymentMethod method=methodsPayment.get(indexMethodSelected);
				if(method.isEChecked()){
					this.showConfirmationECheckPaymenth();
				}
				else{
					this.showConfirmationCardPaymenth();
				}
			}
			else{
				OmegaFiActivity.showAlertMessage("The information you've entered is incorrect. Please review and try again", this);
			}
		}
		else{
			this.showDialogAddNewPaymenth();
		}
	}
	
	private void showConfirmationCardPaymenth(){
		final DialogTwoOptionsOF of=new DialogTwoOptionsOF(this);
		of.setMessageDialog("Are you sure you want to submit?");
		of.setOption1(getString(R.string.yes));
		of.setOption2(getString(R.string.no));
		of.setListenerOption1(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				of.dismissDialog();
				submitAsyncPayment();
			}
		});
		of.setListenerOption2(DialogTwoOptionsOF.getListenerDismiss(of));
		of.showDialog();
	}
	
	public void addNewPaymentMethod(View view){
		Intent activityAddNewPayment=new Intent(this, AddNewPaymentActivity.class);
		activityAddNewPayment.putExtra("id", idAccount);
		startActivity(activityAddNewPayment);
//		enteroTest=1;
	}
	
	
	private boolean validateFieldsPayNow(){
		boolean validate=true;
		Calendar cal=Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		
		if(rowAmount.getValueInfo1().isEmpty()){
			validate=false;
		}
		else if(!CalendarEvent.getDateFromString(rowDate.getValueInfo(), "MM/dd/yyyy").
				after(cal.getTime())){
			validate=false;
		}
		return validate;
	}
	
	private void chargePayNow(){
		AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>(){
			
			private Account actualAccount=null;
			private int statusAcccount;
			private int statusMethods;
			
			@Override
			protected void onPreExecute() {
				startProgressDialog("Charging...", getResources().getString(R.string.please_wait));
			}
			
			@Override
			protected Boolean doInBackground(Void... params) {
				Object[] account=MainActivity.servicesOmegaFi.getHome().getAccounts().getStatusAccount(idAccount);
				statusAcccount=(Integer)account[0];
				actualAccount=(Account)account[1];
				Object[] statusMethods=MainActivity.servicesOmegaFi.getHome().getPaymentMethods(idAccount);
				this.statusMethods=(Integer)statusMethods[0];
				methodsPayment=(ArrayList<PaymentMethod>)statusMethods[1];
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				stopProgressDialog();
				if(actualAccount!=null){
					infoCurrent.setValueLabel("$"+actualAccount.getCurrentBalance());
					infoDueOn.setValueLabel(actualAccount.getDueOn());
					if(!methodsPayment.isEmpty()){
						indexMethodSelected=methodsPayment.size()-1;
						PaymentMethod last=methodsPayment.get(indexMethodSelected);
						rowPaymentMethod.setValueInfo(last.getProfileType()+" ("+last.getId()+")");
						rowPaymentMethod.postInvalidate();
					}
				}
			}
		};
		
		task.execute();
	}
	
	private void submitAsyncPayment(){
		AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>(){

			int status=0;
			
			@Override
			protected void onPreExecute() {
				startProgressDialog("Submit paymenth", getResources().getString(R.string.please_wait));
			}
			
			@Override
			protected Boolean doInBackground(Void... params) {
				PaymentMethod selected=methodsPayment.get(indexMethodSelected);
				status=MainActivity.servicesOmegaFi.getHome().getAccounts().submitPayNow
						(idAccount, rowAmount.getValueInfo1(), CalendarEvent.getFormatDate(5, rowDate.getValueInfo(), "MM/dd/yyyy"),
								selected.getProfileType(), selected.getId());
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				stopProgressDialog();
				if(status==200){
					showThankForYourPaymenth();
				}
				else if(status==422){
					showAlertMessage("You can't select dates before today.", PayNowActivity.this);
				}
				else{
					showErrorConection(PayNowActivity.this, status, "Object not found");
				}
			}
		};
		task.execute();
	}
}