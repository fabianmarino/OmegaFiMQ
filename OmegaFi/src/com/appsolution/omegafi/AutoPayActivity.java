package com.appsolution.omegafi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import com.appsolution.layouts.DialogInformationOF;
import com.appsolution.layouts.DialogTwoOptionsOF;
import com.appsolution.layouts.RowInformation;
import com.appsolution.logic.Account;
import com.appsolution.logic.AutoPayConfig;
import com.appsolution.logic.CalendarEvent;
import com.appsolution.logic.PaymentMethod;
import com.appsolution.logic.Server;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

public class AutoPayActivity extends OmegaFiActivity {

	private ArrayList<PaymentMethod> methodsPayment=null;
	private RowInformation rowInfoBeginDate;
	private Spinner spinnerMethods;
	private int idAccount=-1;
	public static final AutoPayConfig configAutoPay=new AutoPayConfig();
	private RowInformation rowInfoEndDate;
	private RowInformation rowPaymentDate;
	private RowInformation rowPaymentAmount;
	private boolean existAutoPay;
	private Button buttonDeactivate;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auto_pay);
		spinnerMethods=(Spinner)findViewById(R.id.spinnerPaymentMethods);
		buttonDeactivate=(Button)findViewById(R.id.buttonDeactivateAutoPay);
		rowInfoBeginDate=(RowInformation)findViewById(R.id.rowBeginDate);
		rowInfoEndDate=(RowInformation)findViewById(R.id.rowEndDate);
		rowPaymentDate=(RowInformation)findViewById(R.id.rowPaymentDate);
		rowPaymentAmount=(RowInformation)findViewById(R.id.rowPaymentAmount);
		Calendar calToday=Calendar.getInstance();
		calToday.add(Calendar.DAY_OF_MONTH, 1);
		Date today=calToday.getTime();
		rowInfoBeginDate.setValueInfo(today.getMonth()+1+"/"+today.getDate()+"/"+(today.getYear()+1900));
		idAccount=getIntent().getExtras().getInt("id");
		configAutoPay.setBeginDate(CalendarEvent.getFormatDate(6, rowInfoBeginDate.getValueInfo(), "MM/dd/yyyy"));
		existAutoPay=getIntent().getExtras().getBoolean("exist");
		chargePaymentMethods();
	}

	@Override
	protected void optionsActionBar() {
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBarCustom.setTitle("AUTOPAY");
		actionBar.setCustomView(actionBarCustom);
	}
	
	public void selectBeginDate(View view){
		int[] dayMonthYear=rowInfoBeginDate.getDayMonthYear();
		DatePickerDialog date=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				rowInfoBeginDate.setValueInfo((monthOfYear+1)+"/"+dayOfMonth+"/"+year);
				configAutoPay.setBeginDate(CalendarEvent.getFormatDate(6, rowInfoBeginDate.getValueInfo(), "MM/dd/yyyy"));
			}
		}, dayMonthYear[2],  dayMonthYear[0]-1,dayMonthYear[1]);
		date.getDatePicker().setCalendarViewShown(false);
		date.show();
	}
	
	private void completeSpinnerMethods(){
		List<String> list = new ArrayList<String>();
		for (PaymentMethod method:methodsPayment) {
			list.add(method.getNameTypeNumber());
		}	
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			R.layout.spinner_omegafi, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerMethods.setAdapter(dataAdapter); 
		spinnerMethods.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				configAutoPay.seteCheck(methodsPayment.get(position).isEChecked());
				configAutoPay.setIdCreditOrECheck(methodsPayment.get(position).getId());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		refreshActivity();
	}
	
	public void autoPayEndDate(View button){
		Intent activityEndDate=new Intent(this, AutoPayEndDateActivity.class);
		startActivityForResult(activityEndDate,OmegaFiActivity.ACTIVITY_AUTO_PAY_END_DATE);
	}
	
	public void autoPaymentDate(View button){
		Intent activityPaymentDate=new Intent(this, AutoPayPaymentDateActivity.class);
		startActivityForResult(activityPaymentDate,OmegaFiActivity.ACTIVITY_AUTO_PAY_PAYMENT_DATE);
	}
	
	public void autoPaymentAmount(View button){
		Intent activityPaymentAmount=new Intent(this, AutoPaymentAmountActivity.class);
		startActivityForResult(activityPaymentAmount,OmegaFiActivity.ACTIVITY_AUTO_PAY_PAYMENT_AMOUNT);
	}
	
	public void activeAutoPay(View button){
		if(existAutoPay){
			String messsage=validateInformation();
			if(messsage==null){
				updateAutoPayAsync();
			}
			else{
				OmegaFiActivity.showAlertMessage(messsage, AutoPayActivity.this);
			}
		}
		else{
			String messsage=validateInformation();
			if(messsage==null){
				createAutoPayAsync();
			}
			else{
				OmegaFiActivity.showAlertMessage(messsage, AutoPayActivity.this);
			}
		}
		
	}
	
	private void createAutoPayAsync(){
		AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>() {
			
			private int status=0;
			private String message=null;
			
			@Override
			protected void onPreExecute() {
				startProgressDialog("Creating AutoPay...", getResources().getString(R.string.please_wait));
			}
			
			@Override
			protected Boolean doInBackground(Void... params) {
				Object[] statusJson=Server.getServer().getHome().getAccounts().createAutoPay(idAccount, configAutoPay);
				status=(Integer)statusJson[0];
				if(statusJson[1]!=null)
					message=statusJson[1].toString();
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				if(status==200){
					showAutoPayActive();
				}
				else if(status==422){
					OmegaFiActivity.showAlertMessage(message, AutoPayActivity.this);
				}
				else{
					OmegaFiActivity.showErrorConection(AutoPayActivity.this, status, "Not Found!");
				}
				stopProgressDialog();
			}
		};
		task.execute();
	}
	
	private void updateAutoPayAsync(){
		AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>() {
			
			private int status=0;
			private String message=null;
			
			@Override
			protected void onPreExecute() {
				startProgressDialog("Updating AutoPay...", getResources().getString(R.string.please_wait));
			}
			
			@Override
			protected Boolean doInBackground(Void... params) {
				Object[] statusJson=Server.getServer().getHome().getAccounts().updateAutoPay(idAccount, configAutoPay);
				status=(Integer)statusJson[0];
				if(statusJson[1]!=null)
					message=statusJson[1].toString();
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				if(status==200){
					showAutoPayUpdate();
				}
				else if(status==422){
					OmegaFiActivity.showAlertMessage(message, AutoPayActivity.this);
				}
				else{
					OmegaFiActivity.showErrorConection(AutoPayActivity.this, status, "Not Found!");
				}
				stopProgressDialog();
			}
		};
		task.execute();
	}
	
	private void showAutoPayActive(){
		final DialogInformationOF dialog=new DialogInformationOF(this);
		dialog.setMessageDialog("AutoPay is now active");
		dialog.setButtonListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismissDialog();
				finishActivity(OmegaFiActivity.ACTIVITY_VIEW_ACCOUNT);
				finishActivity(OmegaFiActivity.ACTIVITY_VIEW_ACCOUNT);
				Intent intent=new Intent(AutoPayActivity.this, AccountActivity.class);
				intent.putExtra("id", idAccount);
				startActivityForResult(intent, OmegaFiActivity.ACTIVITY_VIEW_ACCOUNT);
				AutoPayActivity.this.finish();
				configAutoPay.clearInformation();
			}
		});
		dialog.showDialog();
	}
	
	private void showAutoPayUpdate(){
		final DialogInformationOF dialog=new DialogInformationOF(this);
		dialog.setMessageDialog("Your AutoPay have been updated.");
		dialog.setButtonListener(new View.OnClickListener() {
			
			@Override 
			public void onClick(View v) {
				dialog.dismissDialog();
				finishActivity(OmegaFiActivity.ACTIVITY_VIEW_ACCOUNT);
				finishActivity(OmegaFiActivity.ACTIVITY_VIEW_ACCOUNT);
				Intent intent=new Intent(AutoPayActivity.this, AccountActivity.class);
				intent.putExtra("id", idAccount);
				startActivityForResult(intent, OmegaFiActivity.ACTIVITY_VIEW_ACCOUNT);
				AutoPayActivity.this.finish();
				configAutoPay.clearInformation();
			}
		});
		dialog.showDialog();
	}
	
	public void deactiveAutoPay(View button){
		final DialogTwoOptionsOF of=new DialogTwoOptionsOF(this);
		of.setMessageDialog("Are you sure you want to delete your AutoPay settings?");
		of.setOption1("YES");
		of.setOption2("NO");
		of.setListenerOption1(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				removeAutoPaySettings();
			}
		});
		of.setListenerOption2(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				of.dismissDialog();
			}
		});
		of.showDialog();
	}
	
	private void chargePaymentMethods(){
		AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>(){
			
			private int statusMethods;
			private int statusAutoPay;
			
			@Override
			protected void onPreExecute() {
				startProgressDialog("Charging...", getResources().getString(R.string.please_wait));
			}
			
			@Override
			protected Boolean doInBackground(Void... params) {
				Object[] statusMethods=Server.getServer().getHome().getPaymentMethods(idAccount);
				this.statusMethods=(Integer)statusMethods[0];
				methodsPayment=(ArrayList<PaymentMethod>)statusMethods[1];
				if(existAutoPay){
					Object[] auxAutoPay=Server.getServer().getHome().getAccounts().getStatusAutoPay(idAccount);
					statusAutoPay=(Integer)auxAutoPay[0];
					configAutoPay.setAutoPayConfig((AutoPayConfig)auxAutoPay[1]);
				}
				else{
					configAutoPay.clearInformation();
				}
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				if(idAccount!=-1){
					if(!methodsPayment.isEmpty()){
						completeSpinnerMethods();
						completeAutoPayExist();
					}
				}
				stopProgressDialog();
				configAutoPay.setBeginDate(CalendarEvent.getFormatDate(6, rowInfoBeginDate.getValueInfo(), "MM/dd/yyyy"));
			}
		};
		
		task.execute();
	}
	
	@Override
	protected void onResume() {
		int index=getIndexPaymentMethod(configAutoPay.getIdCreditOrECheck());
		if(index!=-1){
			spinnerMethods.setSelection(index);
		}
		else{
			spinnerMethods.setSelection(0);
		}
		if(configAutoPay.getBeginDate()!=null){
			rowInfoBeginDate.setValueInfo(configAutoPay.getBeginDatePretty());
		}
		if(configAutoPay.getEndDate()==null){
			rowInfoEndDate.setValueInfo("None");
		}
		else{
			rowInfoEndDate.setValueInfo(configAutoPay.getEndDatePretty());
		}
		if(configAutoPay.getPaymentDayOfMonth()==-1){
			rowPaymentDate.setValueInfo("Pay on Due Date");
		}
		else{
			rowPaymentDate.setValueInfo(""+configAutoPay.getPaymentDayOfMonth());
		}
		if(configAutoPay.getTypePaymenthAmount()==AutoPayConfig.PAY_AMOUNT_DUE){
			rowPaymentAmount.setValueInfo("Pay Amount Due");
			if(configAutoPay.getAmountEnterMax()>-1){
				rowPaymentAmount.setValueInfo("Pay Amount Due ($"+configAutoPay.getAmountEnterMax()+")");
			}
		}
		else{
			rowPaymentAmount.setValueInfo("Specific Amount($"+configAutoPay.getAmountEnterMax()+")");
		}
		super.onResume();
	}
	
	private void completeAutoPayExist(){
		if(configAutoPay.getId()!=-1){
			onResume();
			buttonDeactivate.setVisibility(View.VISIBLE);
		}
		else{
			buttonDeactivate.setVisibility(View.GONE);
		}
	}
	
	private int getIndexPaymentMethod(int idCreditEcheck){
		int index=-1;
		if(methodsPayment!=null){
			for (int i = 0; i < methodsPayment.size()&&index==-1; i++) {
				if(methodsPayment.get(i).getId()==idCreditEcheck){
					index=i;
				}
			}
		}
		return index;
	}
	
	private void removeAutoPaySettings(){
		AsyncTask< Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>() {
			
			private int status=0;
			
			@Override
			protected void onPreExecute() {
				startProgressDialog("Deleting AutoPay", getResources().getString(R.string.please_wait));
			}
			
			@Override
			protected Boolean doInBackground(Void... params) {
				status=Server.getServer().getHome().getAccounts().removeAutoPaySettings(idAccount, configAutoPay.getId());
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				if(status==200){
					final DialogInformationOF of=new DialogInformationOF(AutoPayActivity.this);
					of.setMessageDialog("Your AutoPay settings have been deleted.");
					of.setButtonListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							AutoPayActivity.this.finish();
							finishActivity(OmegaFiActivity.ACTIVITY_VIEW_ACCOUNT);
							Intent viewAccount=new Intent(getApplicationContext(), AccountActivity.class);
							viewAccount.putExtra("id", idAccount);
							startActivityForResult(viewAccount, OmegaFiActivity.ACTIVITY_VIEW_ACCOUNT);
							of.dismissDialog();
							configAutoPay.clearInformation();
						}
					});
					of.showDialog();
				}
				else{
					OmegaFiActivity.showErrorConection(AutoPayActivity.this, status, "Not Found");
				}
				stopProgressDialog();
				
			}
		};
		task.execute();
	}
	
	
	private String validateInformation(){
		String message=null;
		if(AutoPayActivity.configAutoPay.getAmountEnterMax()<=0){
			message="The payment amount must be greater than 0.";
		}
		Calendar calToday=Calendar.getInstance();
		calToday.add(Calendar.DAY_OF_MONTH, 1);
		Date tomorrow=calToday.getTime();
		tomorrow.setHours(0);
		tomorrow.setMinutes(0);
		tomorrow.setSeconds(0);
		if(CalendarEvent.getDateFromString(AutoPayActivity.configAutoPay.getBeginDatePretty(), "MM/dd/yyyy").before(tomorrow)){
			message="The min begin date must be tomorrow.";
		}
			return message;
	}
	
	@Override
	public void onBackPressed() {
		Intent viewAccount=new Intent(getApplicationContext(), AccountActivity.class);
		viewAccount.putExtra("id", idAccount);
		startActivityForResult(viewAccount, OmegaFiActivity.ACTIVITY_VIEW_ACCOUNT);
		super.onBackPressed();
	}
	

}