package com.appsolution.omegafi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import com.appsolution.layouts.DialogSelectableOF;
import com.appsolution.layouts.DialogTwoOptionsOF;
import com.appsolution.layouts.LabelInfoVertical;
import com.appsolution.layouts.RowEditTextOmegaFi;
import com.appsolution.layouts.RowInformation;
import com.appsolution.logic.Account;
import com.appsolution.logic.CalendarEvent;
import com.appsolution.logic.PaymentMethod;
import com.appsolution.services.Server;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;

public class PayNowActivity extends OmegaFiActivity {

	private RowEditTextOmegaFi rowAmount;
	private RowInformation rowDate;
	private RowInformation rowPaymentMethod;
	private LabelInfoVertical infoCurrent;
	private LabelInfoVertical infoDueOn;
	private int idAccount;
	private ArrayList<PaymentMethod> methodsPayment=null;
	private int indexMethodSelected=-1;
	private boolean backIsHome;
	
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
		backIsHome=getIntent().getExtras().getBoolean("home");
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
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		date.getDatePicker().setMinDate(cal.getTimeInMillis());
		date.show();
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
					indexMethodSelected=selectable.getIndexSelected();
					rowPaymentMethod.setNameInfo(methodsPayment.get(indexMethodSelected).getProfileTypeNumber());
					selectable.dismissDialog();
					refreshActivity();
				}
			});
			if(indexMethodSelected!=-1){
				selectable.setSelectedIndex(indexMethodSelected);
			}
			selectable.showDialog();
		}
		else{
			this.showDialogAddNewPayment();
		}
	}
	
	private void showDialogAddNewPayment(){
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
	
	
	private void showConfirmationECheckPayment(){
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
	
	private void showThankForYourPayment(){
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
					this.showConfirmationECheckPayment();
				}
				else{
					this.showConfirmationCardPayment();
				}
			}
			else{
				OmegaFiActivity.showAlertMessage("The information you've entered is incorrect. Please review and try again", this);
			}
		}
		else{
			this.showDialogAddNewPayment();
		}
	}
	
	private void showConfirmationCardPayment(){
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
		startActivityForResult(activityAddNewPayment,OmegaFiActivity.ACTIVITY_ADD_NEW_PAYMENT);
	}
	
	
	private boolean validateFieldsPayNow(){
		boolean validate=true;
		Calendar cal=Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		
		if(rowAmount.getValueInfo1().isEmpty()||!OmegaFiActivity.isDouble(rowAmount.getValueInfo1())){
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
				startProgressDialog("Loading...", getResources().getString(R.string.please_wait));
			}
			
			@Override
			protected Boolean doInBackground(Void... params) {
				Object[] account=Server.getServer().getHome().getAccounts().getStatusAccount(idAccount);
				statusAcccount=(Integer)account[0];
				actualAccount=(Account)account[1];
				Object[] statusMethods=Server.getServer().getHome().getPaymentMethods(idAccount);
				this.statusMethods=(Integer)statusMethods[0];
				methodsPayment=(ArrayList<PaymentMethod>)statusMethods[1];
				Server.getServer().getHome().getProfile().updateProfileIfNecessary();
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				stopProgressDialog();
				if(actualAccount!=null){
					infoCurrent.setValueLabel(actualAccount.getCurrentBalance());
					infoDueOn.setValueLabel(actualAccount.getDueOn());
					if(!methodsPayment.isEmpty()){
						indexMethodSelected=0;
						rowPaymentMethod.setNameInfo(methodsPayment.get(indexMethodSelected).getNameTypeNumber());
						if(methodsPayment.size()==1){
							rowPaymentMethod.setVisibleArrow(false);
							rowPaymentMethod.setOnClickListener(null);
						}
						refreshActivity();
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
				startProgressDialog("Submit payment...", getResources().getString(R.string.please_wait));
			}
			
			@Override
			protected Boolean doInBackground(Void... params) {
				PaymentMethod selected=methodsPayment.get(indexMethodSelected);
				status=Server.getServer().getHome().getAccounts().submitPayNow
						(idAccount, rowAmount.getValueInfo1(), CalendarEvent.getFormatDate(5, rowDate.getValueInfo(), "MM/dd/yyyy"),
								selected.getProfileType(), selected.getId());
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				stopProgressDialog();
				if(status==200){
					showThankForYourPayment();
				}
				else if(status==422){
					showAlertMessage("You can't select dates before today.", PayNowActivity.this);
				}
				else{
					showErrorConection(PayNowActivity.this, status, getResources().getString(R.string.object_not_found),false);
				}
			}
		};
		task.execute();
	}
	
	@Override
	public void onBackPressed() {
		if(backIsHome){
			goToHome();
		}
		super.onBackPressed();
	}
}