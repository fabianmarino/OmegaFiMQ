package com.appsolution.omegafi;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import com.appsolution.layouts.ContactInformation;
import com.appsolution.layouts.DialogInformationOF;
import com.appsolution.layouts.DialogSelectableOF;
import com.appsolution.layouts.DialogTwoOptionsOF;
import com.appsolution.layouts.RowEditNameTopInfo;
import com.appsolution.layouts.RowEditTextOmegaFi;
import com.appsolution.layouts.RowInformation;
import com.appsolution.layouts.RowToogleOmegaFi;
import com.appsolution.layouts.SectionOmegaFi;
import com.appsolution.layouts.SpinnerNameTopInfo;
import com.appsolution.logic.Account;
import com.appsolution.logic.CalendarEvent;
import com.appsolution.logic.PaymentMethod;
import com.appsolution.logic.PaymentMethodTemp;
import com.appsolution.services.Server;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Spinner;

public class AddNewPaymentActivity extends OmegaFiActivity {

	private Spinner typeNewPayment;
	
	private LinearLayout linearCreditDebit;
	private RowEditTextOmegaFi rowTextNameOnCard;
	private RowInformation rowSelectCardType;
	private RowInformation rowExpirationDate;
	private RowEditTextOmegaFi rowTextNumberCard;
	private RowEditTextOmegaFi rowTextZipCode;
	private RowToogleOmegaFi rowSaveForFutureUse;
	private RowToogleOmegaFi rowSaveForFutureUse2;
	
	private LinearLayout linearChekingAccount;
	private RowEditTextOmegaFi rowTextNameOnAccount;
	private RowEditTextOmegaFi rowTextRoutingNumber;
	private RowEditTextOmegaFi rowTextAccountNumber;
	
	private SectionOmegaFi sectionAddress;
	private RowEditNameTopInfo rowEditAddres1;
	private RowEditNameTopInfo rowEditAddres2;
	
	private LinearLayout linearCityStateZIP;
	private RowEditNameTopInfo rowCity;
	private SpinnerNameTopInfo rowSpinner;
	private RowEditNameTopInfo rowZIP;
	private ContactInformation contact;
	
	private Button buttonAddContinue;
	private LinearLayout linearSaveDelete;
	private DatePickerDialog mDialog;
	
	static final int DATE_DIALOG_ID = 1;
    private int mYear = 2013;
    private int mMonth = 5;
    private int mDay = 30;
    
    private int idAccount;
    private int indexCardType=-1;
    private ArrayList<String> listCreditCards=new ArrayList<String>();
    private boolean createNewPayment=false;
    private ArrayList<PaymentMethod> methodsPayment=null;
    private PaymentMethodTemp methodTemp=null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_payment);
		listCreditCards.add("Visa");
		listCreditCards.add("MasterCard");
		listCreditCards.add("Discover");
		listCreditCards.add("American Express");
		typeNewPayment=(Spinner)findViewById(R.id.spinnerCreditECheck);
		this.completeSpinnerNewPayment();
		linearCreditDebit=(LinearLayout)findViewById(R.id.linearCreditDebitCard);
		linearChekingAccount=(LinearLayout)findViewById(R.id.linearCheckingAccount);
		rowTextNameOnCard=(RowEditTextOmegaFi)findViewById(R.id.textNameOnCard);
		rowTextNameOnAccount=(RowEditTextOmegaFi)findViewById(R.id.textNameOnAccount);
		rowTextRoutingNumber=(RowEditTextOmegaFi)findViewById(R.id.textRoutingNumber);
		rowTextNumberCard=(RowEditTextOmegaFi)findViewById(R.id.textNumberCard);
		rowTextAccountNumber=(RowEditTextOmegaFi)findViewById(R.id.textAccountNumber);
		rowTextZipCode=(RowEditTextOmegaFi)findViewById(R.id.textZipCode);
		rowSelectCardType=(RowInformation)findViewById(R.id.selectCardType);
		rowSaveForFutureUse=(RowToogleOmegaFi)findViewById(R.id.rowSaveForFutureUse);
		rowSaveForFutureUse2=(RowToogleOmegaFi)findViewById(R.id.rowSaveForFutureUse2);
		
		rowExpirationDate=(RowInformation)findViewById(R.id.rowSelectExpirationDate);
		contact=(ContactInformation)findViewById(R.id.contactInfoCreditDebitCard);
		rowExpirationDate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				showExpirationDate(null);
			}
		});
		sectionAddress=(SectionOmegaFi)findViewById(R.id.sectionAddresAddPayment);
		buttonAddContinue=(Button)findViewById(R.id.buttonAddContinue);
		linearSaveDelete=(LinearLayout)findViewById(R.id.linearSaveDeleteMethod);
		this.completeFields();

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        idAccount=getIntent().getExtras().getInt("id");
        createNewPayment=getIntent().getExtras().getBoolean("create");
        activateButtonsCreate(createNewPayment);
        if(!createNewPayment){
        	configureUpdatePayment();
        }
	}
	
	private void configureUpdatePayment(){
		actionBarCustom.setTitle("PAYMENT METHOD");
		actionBar.setCustomView(actionBarCustom);
		chargePaymentMethods();
	}
	
	private void completeFields(){
		Date today=Calendar.getInstance().getTime();
		rowExpirationDate.setValueInfo(today.getMonth()+2+"/"+(today.getYear()+1900));
		
		
		CompoundButton.OnCheckedChangeListener listenerToogle=new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					buttonAddContinue.setText(getResources().getString(R.string.new_payment_add));
				}
				else{
					buttonAddContinue.setText(getResources().getString(R.string.new_payment_continue));
				}
			}
		};
		rowSaveForFutureUse.setOnChangeListenerToogle(listenerToogle);
		rowSaveForFutureUse2.setOnChangeListenerToogle(listenerToogle);
		rowEditAddres1=new RowEditNameTopInfo(this);
		rowEditAddres1.setWidthFillParent(true);
		rowEditAddres1.setNameInfoTop("Address Line 1");
		
		rowEditAddres2=new RowEditNameTopInfo(this);
		rowEditAddres2.setWidthFillParent(true);
		rowEditAddres2.setNameInfoTop("Address Line 2");
		
		linearCityStateZIP=new LinearLayout(getApplicationContext());
		linearCityStateZIP.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 
				LayoutParams.WRAP_CONTENT));
		linearCityStateZIP.setOrientation(LinearLayout.HORIZONTAL);
		linearCityStateZIP.setWeightSum(10);
		rowCity=new RowEditNameTopInfo(getApplicationContext());
		rowCity.setNameInfoTop("City");
		LayoutParams paramCity=new LayoutParams(0, LayoutParams.WRAP_CONTENT, 5);
		rowCity.setLayoutParams(paramCity);
		
		rowSpinner=new SpinnerNameTopInfo(getApplicationContext());
		rowSpinner.setNameInfoTop("State");
		rowSpinner.setListSpinner(getArrayStates());
		LayoutParams paramsState=new LayoutParams(0, LayoutParams.FILL_PARENT,2);
		rowSpinner.setLayoutParams(paramsState);
		
		rowZIP=new RowEditNameTopInfo(getApplicationContext());
		rowZIP.setTypeInputEditText(InputType.TYPE_CLASS_PHONE);
		rowZIP.setNameInfoTop("ZIP");
		LayoutParams paramsZIP=new LayoutParams(0, LayoutParams.WRAP_CONTENT,3);
		rowZIP.setLayoutParams(paramsZIP);
		
		linearCityStateZIP.addView(rowCity);
		linearCityStateZIP.addView(rowSpinner);
		linearCityStateZIP.addView(rowZIP);
		
		sectionAddress.addView(rowEditAddres1);
		sectionAddress.addView(rowEditAddres2);
		sectionAddress.addView(linearCityStateZIP);
		
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBarCustom.setTitle("ADD NEW PAYMENT");
		actionBar.setCustomView(actionBarCustom);
	}
	
	public void selectCardType(View view){
		final DialogSelectableOF selectable=new DialogSelectableOF(this);
		selectable.setTitleDialog("Select Card Type");
		selectable.setOptionsSelectables(listCreditCards);
		selectable.setTextButton("Save");
		selectable.setCloseOnSelectedItem(false);
		selectable.setSelectedItem(rowSelectCardType.getValueInfo());
		selectable.setButtonListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				rowSelectCardType.setValueInfo(selectable.getItemSelected());
				indexCardType=selectable.getIndexSelected();
				selectable.dismissDialog();
			}
		});
		selectable.showDialog();
	}
	
	public void addContinueNewPayment(View view){
		if(typeNewPayment.getSelectedItemPosition()==0){
			addCreditCard();
		}
		else if(typeNewPayment.getSelectedItemPosition()==1){
			addECheck();
		}
	}
	
	private void chargePaymentMethods(){
		AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>(){
			
			private int statusMethods;
			
			@Override
			protected void onPreExecute() {
				startProgressDialog("Loading...", getResources().getString(R.string.please_wait));
			}
			
			@Override
			protected Boolean doInBackground(Void... params) {
				Object[] statusMethods=Server.getServer().getHome().getPaymentMethods(idAccount);
				this.statusMethods=(Integer)statusMethods[0];
				methodsPayment=(ArrayList<PaymentMethod>)statusMethods[1];
				Server.getServer().getHome().getProfile().updateProfileIfNecessary();
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				if(Server.isStatusOk(statusMethods)){
					completeSpinnerUpdatePayment();
				}
				else{
					OmegaFiActivity.showErrorConection(AddNewPaymentActivity.this, statusMethods, getResources().getString(R.string.object_not_found), false);
				}
				stopProgressDialog();
			}
		};
		
		task.execute();
	}
	
	private List<String> getListStringForPayMethods(){
		List<String> list=new ArrayList<String>();
		if(methodsPayment!=null){
			for (PaymentMethod method:methodsPayment) {
				list.add(method.getNameTypeNumber());
			}
		}
		return list;
	}
	
	private void completeSpinnerUpdatePayment(){
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				R.layout.spinner_omegafi, getListStringForPayMethods());
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			typeNewPayment.setAdapter(dataAdapter);
			typeNewPayment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					completeFieldsByPaymentMethod(methodsPayment.get(position));
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub		
				}
			});
	}
	
	private void getIndexTypeCard(PaymentMethod method){
		for (int i = 0; i < listCreditCards.size(); i++) {
			if(listCreditCards.get(i).equalsIgnoreCase(method.getProfileType()))
				indexCardType=i;
		}
	}
	
	private void completeFieldsByPaymentMethod(PaymentMethod method){
		if(method.isEChecked()){
			rowTextNameOnAccount.setTextEdit(method.getCardName());
			rowTextRoutingNumber.setTextEdit(method.getRoutingNumber());
			rowTextAccountNumber.setTextEdit(method.getCardAccountNumber());
			
			rowSaveForFutureUse2.setActivateOn(method.isProfile());
			rowEditAddres1.setValueInfo(method.getAddressLine1());
			rowEditAddres2.setValueInfo(method.getAddressLine2());
			rowCity.setValueInfo(method.getCity());
			rowSpinner.setSelectedItem(method.getState());
			rowZIP.setValueInfo(method.getZipCode());
			linearChekingAccount.setVisibility(View.VISIBLE);
			linearCreditDebit.setVisibility(View.GONE);
		}
		else{
			rowTextNameOnCard.setTextEdit(method.getCardName());
			rowSelectCardType.setValueInfo(method.getProfileType());
			getIndexTypeCard(method);
			rowTextNumberCard.setTextEdit(method.getCardAccountNumber());
			rowExpirationDate.setValueInfo(method.getMonthYear());
			rowSaveForFutureUse.setActivateOn(method.isProfile());
			rowTextZipCode.setTextEdit(method.getZipCode());
			linearChekingAccount.setVisibility(View.GONE);
			linearCreditDebit.setVisibility(View.VISIBLE);
		}
		contact.setEmail(method.getEmailAddres());
		contact.setPhone(method.getPhoneNumber());
		refreshActivity();
	}
	
	
	private void completeSpinnerNewPayment(){
		List<String> list = new ArrayList<String>();
		list.add("Credit/Debit Card");
		list.add("E-Check");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			R.layout.spinner_omegafi, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		typeNewPayment.setAdapter(dataAdapter);
		typeNewPayment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				switch (position) {
				case 0:
					linearChekingAccount.setVisibility(LinearLayout.GONE);
					linearCreditDebit.setVisibility(LinearLayout.VISIBLE);
					if(rowSaveForFutureUse.isActivatedOn()){
						buttonAddContinue.setText(getResources().getString(R.string.new_payment_add));
					}
					else{
						buttonAddContinue.setText(getResources().getString(R.string.new_payment_continue));
					}
					break;
				case 1:
					linearCreditDebit.setVisibility(LinearLayout.GONE);
					linearChekingAccount.setVisibility(LinearLayout.VISIBLE);
					if(rowSaveForFutureUse2.isActivatedOn()){
						buttonAddContinue.setText(getResources().getString(R.string.new_payment_add));
					}
					else{
						buttonAddContinue.setText(getResources().getString(R.string.new_payment_continue));
					}
					break;

				default:
					break;
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub		
			}
		});
	}
	
	public void showExpirationDate(View view){
		int[] monthYear=rowExpirationDate.getMonthYear();
		mDay=0;
		mMonth=monthYear[0];
		mYear=monthYear[1];
		showDialog(DATE_DIALOG_ID);
		mDialog.setTitle(CalendarEvent.getFormatDate(4, (mMonth)+"/"+mYear, "MM/yyyy"));
	}
	
	
	DatePickerDialog.OnDateSetListener mDateSetListner = new DatePickerDialog.OnDateSetListener() {
		
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDate();
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DATE_DIALOG_ID:
            mDialog = this.customDatePicker();
            return mDialog;
        }
        return null;
    }

    protected void updateDate() {
        int localMonth = (mMonth + 1);
        String monthString = localMonth < 10 ? "0" + localMonth : Integer
                .toString(localMonth);
        String localYear = Integer.toString(mYear);
        rowExpirationDate.setValueInfo(monthString+"/"+localYear);
    }

    private DatePickerDialog customDatePicker() {
        DatePickerDialog dpd = new DatePickerDialog(this, mDateSetListner,
                mYear, mMonth, mDay);
        try {
            Field[] datePickerDialogFields = dpd.getClass().getDeclaredFields();
            for (Field datePickerDialogField : datePickerDialogFields) {
                if (datePickerDialogField.getName().equals("mDatePicker")) {
                    datePickerDialogField.setAccessible(true);
                    DatePicker datePicker = (DatePicker) datePickerDialogField
                            .get(dpd);
                    datePicker.setCalendarViewShown(false);
                    datePicker.init(mYear, mMonth, mDay, new DatePicker.OnDateChangedListener() {
						@Override
						public void onDateChanged(DatePicker view, int year, int monthOfYear,
								int dayOfMonth) {
							mDialog.setTitle(CalendarEvent.getFormatDate(4, (monthOfYear+1)+"/"+year, "MM/yyyy"));							
						}
					});
                    Calendar cal = Calendar.getInstance();
            		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
            		datePicker.setMinDate(cal.getTimeInMillis());
                    Field datePickerFields[] = datePickerDialogField.getType()
                            .getDeclaredFields();
                    for (Field datePickerField : datePickerFields) {
                        if ("mDayPicker".equals(datePickerField.getName())
                                || "mDaySpinner".equals(datePickerField
                                        .getName())) {
                            datePickerField.setAccessible(true);
                            Object dayPicker = new Object();
                            dayPicker = datePickerField.get(datePicker);
                            ((View) dayPicker).setVisibility(View.GONE);
                        }
                    }
                }
            }
        } catch (Exception ex) {
        }
        return dpd;
    }
    
    public ArrayList<String> getArrayStates(){
    	ArrayList<String> list=new ArrayList<String>();
    	list.add("AL");
    	list.add("AK");
    	list.add("AZ");
    	list.add("AR");
    	list.add("CA");
    	list.add("CO");
    	list.add("CT");
    	list.add("DE");
    	list.add("DC");
    	list.add("FL");
    	list.add("GA");
    	list.add("HI");
    	list.add("ID");
    	list.add("IL");
    	list.add("IN");
    	list.add("IA");
    	list.add("KS");
    	list.add("KY");
    	list.add("LA");
    	list.add("ME");
    	list.add("MD");
    	list.add("MA");
    	list.add("MI");
    	list.add("MN");
    	list.add("MS");
    	list.add("MO");
    	list.add("MT");
    	list.add("NE");
    	list.add("NV");
    	list.add("NH");
    	list.add("NJ");
    	list.add("NM");
    	list.add("NY");
    	list.add("NC");
    	list.add("ND");
    	list.add("OH");
    	list.add("OK");
    	list.add("OR");
    	list.add("PA");
    	list.add("RI");
    	list.add("SC");
    	list.add("SD");
    	list.add("TN");
    	list.add("TX");
    	list.add("UT");
    	list.add("VT");
    	list.add("VA");
    	list.add("WA");
    	list.add("WV");
    	list.add("WI");
    	list.add("WY");
  return list;	
    }
    
    private void showDialogSuccesffully(){
    	final DialogInformationOF of=new DialogInformationOF(this);
    	of.setMessageDialog("You've successfully added a payment method");
    	of.setButtonListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				of.dismissDialog();
				finish();
				finishActivity(OmegaFiActivity.ACTIVITY_PAY_NOW);
				Intent payNow=new Intent(AddNewPaymentActivity.this,PayNowActivity.class);
				payNow.putExtra("id", idAccount);
				payNow.putExtra("home", false);
				if(methodTemp!=null)
					payNow.putExtra("temp", methodTemp);
				startActivityForResult(payNow, OmegaFiActivity.ACTIVITY_PAY_NOW);
			}
		});
    	of.showDialog();
    }
    
    private void showDialogSuccesffullyUpdated(){
    	final DialogInformationOF of=new DialogInformationOF(this);
    	of.setMessageDialog("Your changes have been saved.");
    	of.setButtonListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				of.dismissDialog();
				finish();
			}
		});
    	of.showDialog();
    }
    
    private void showDialogErrorsBefore(String message){
    	OmegaFiActivity.showAlertMessage(message, this);
    }
    
    
    private String validateCCInformation(){
    	String valide=null;
    	if(rowTextNameOnCard.getValueInfo1().isEmpty()){
    		valide="The card name you have entered is not valid.\nPlease correct and try again.";
    	}
    	else if(indexCardType==-1){
    		valide="Select a type of credit card.";
    	}
    	else if(rowTextNumberCard.getValueInfo1().isEmpty()||!rowTextNumberCard.getValueInfo1().matches("[0-9]*")){
    		valide="The number card you have entered is not valid.\nPlease correct and try again.";
    	}
    	else if(rowTextZipCode.getValueInfo1().isEmpty()||!rowTextZipCode.getValueInfo1().matches("[0-9]*")){
    		valide="The zip code you have entered is not valid.\nPlease correct and try again.";
    	}
    	else if(!contact.isValidEmail()){
    		valide="The email address you have entered is not valid.\nPlease correct and try again.";
    	}
    	else if(!contact.isValidPhone()){
    		valide="The phone you have entered is not valid.\nPlease correct and try again.";
    	}
    	return valide;
    }
	
    private String validateECheckInformation(){
    	String valide=null;
    	if(rowTextNameOnAccount.getValueInfo1().isEmpty()){
    		valide="The name account you have entered is not valid.\nPlease correct and try again.";
    	}
    	else if(rowTextRoutingNumber.getValueInfo1().isEmpty()||!rowTextRoutingNumber.getValueInfo1().matches("[0-9]*")){
    		valide="The rounting number you have entered is not valid.\nPlease correct and try again.";;
    	}
    	else if(rowTextAccountNumber.getValueInfo1().isEmpty()||!rowTextAccountNumber.getValueInfo1().matches("[0-9]*")){
    		valide="The account number you have entered is not valid.\nPlease correct and try again.";;
    	}
    	else if(rowEditAddres1.getValueInfo().isEmpty()){
    		valide="The address you have entered is not valid.\nPlease correct and try again.";
    	}
    	else if(rowCity.getValueInfo().isEmpty()){
    		valide="The city you have entered is not valid.\nPlease correct and try again.";
    	}
    	else if(rowZIP.getValueInfo().isEmpty()||!rowZIP.getValueInfo().matches("[0-9]*")){
    		valide="The zip code you have entered is not valid.\nPlease correct and try again.";
    	}
    	else if(!contact.isValidEmail()){
    		valide="The email address you have entered is not valid.\nPlease correct and try again.";
    	}
    	else if(!contact.isValidPhone()){
    		valide="The phone you have entered is not valid.\nPlease correct and try again.";
    	}
    	return valide;
    }
    
    private void addCreditCard(){
    	String validate=validateCCInformation();
    	if(validate==null){
    		addCreditCardAsync();
    	}
    	else{
    		showDialogErrorsBefore(validate);
    	}
    }
    
    private void addECheck(){
    	String validate=validateECheckInformation();
    	if(validate==null){
    		addECheckAsync();
    	}
    	else{
    		showDialogErrorsBefore(validate);
    	}
    }
    
    private void addCreditCardAsync(){
    	AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>(){

    		int status=0;
    		private JSONObject response=null;
    		
    		@Override
    		protected void onPreExecute() {
    			startProgressDialog("Adding Credit Card...",getResources().getString(R.string.please_wait));
    		}
    		
			@Override
			protected Boolean doInBackground(Void... params) {
				int[] monthYear=rowExpirationDate.getMonthYear();
				Object[] statusJson=Server.getServer().getHome().getAccounts().createPaymentCC
						(idAccount, rowTextNameOnCard.getValueInfo1(),rowTextNumberCard.getValueInfo1(),getNameServiceCC(listCreditCards.get(indexCardType)), 
								monthYear[0], monthYear[1], contact.getEmail(), Integer.parseInt(rowTextZipCode.getValueInfo1()), contact.getPhone(),
								rowSaveForFutureUse.isActivatedOn());
				status=(Integer)statusJson[0];
				response=(JSONObject)statusJson[1];
				Server.getServer().getHome().getProfile().updateProfileIfNecessary();
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				stopProgressDialog();
				if(Server.isStatusOk(status)){
					if(!rowSaveForFutureUse.isActivatedOn()){
						methodTemp=new PaymentMethodTemp(response, false);
					}
					else{
						methodTemp=null;
					}
					showDialogSuccesffully();
				}
				else if(status==422){
					OmegaFiActivity.showAlertMessage(getErrorJson(response), AddNewPaymentActivity.this);
				}
				else{
					OmegaFiActivity.showErrorConection(AddNewPaymentActivity.this, status, getResources().getString(R.string.object_not_found),false);
				}
			}	
    	};
    	task.execute();
    }
    
    private void updateCreditCardAsync(){
    	AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>(){

    		int status=0;
    		private JSONObject response=null;
    		
    		@Override
    		protected void onPreExecute() {
    			startProgressDialog("Updating Credit Card...",getResources().getString(R.string.please_wait));
    		}
    		
			@Override
			protected Boolean doInBackground(Void... params) {
				int[] monthYear=rowExpirationDate.getMonthYear();
				final PaymentMethod method=methodsPayment.get(typeNewPayment.getSelectedItemPosition());
				Object[] statusJson=Server.getServer().getHome().getAccounts().updatePaymentCC
						(idAccount, method.getId(),rowTextNameOnCard.getValueInfo1(),rowTextNumberCard.getValueInfo1(),getNameServiceCC(listCreditCards.get(indexCardType)), 
								monthYear[0], monthYear[1], contact.getEmail(), Integer.parseInt(rowTextZipCode.getValueInfo1()), contact.getPhone(),
								rowSaveForFutureUse.isActivatedOn());
				status=(Integer)statusJson[0];
				response=(JSONObject)statusJson[1];
				Server.getServer().getHome().getProfile().updateProfileIfNecessary();
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				stopProgressDialog();
				if(Server.isStatusOk(status)){
					showDialogSuccesffullyUpdated();
				}
				else if(status==422){
					OmegaFiActivity.showAlertMessage(getErrorJson(response), AddNewPaymentActivity.this);
				}
				else{
					OmegaFiActivity.showErrorConection(AddNewPaymentActivity.this, status, getResources().getString(R.string.object_not_found),false);
				}
			}	
    	};
    	task.execute();
    }
    
    private String getNameServiceCC(String ccLayout){
    	ccLayout=ccLayout.equalsIgnoreCase(PaymentMethod.AMERICAN_EXPRESS_NAME) ? PaymentMethod.AMERICAN_EXPRESS_SERVICE:ccLayout;
    	return ccLayout;
    }
    
    private String getErrorJson(JSONObject errorJson){
    	String error=null;
    	if(errorJson!=null){
	    	try {
	    		JSONObject jsonErrors=errorJson.getJSONObject("errors");
	    		error=Server.getFirstError(jsonErrors);
			} catch (JSONException e) {
				e.printStackTrace();
			}
    	}
    	return error;
    }
    
    private void addECheckAsync(){
    	AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>(){

    		int status=0;
    		private JSONObject response=null;
    		
    		@Override
    		protected void onPreExecute() {
    			startProgressDialog("Adding E-Check...",getResources().getString(R.string.please_wait));
    		}
    		
			@Override
			protected Boolean doInBackground(Void... params) {
				Object[] statusJson=Server.getServer().getHome().getAccounts().createPaymentECheck
						(idAccount,rowTextNameOnAccount.getValueInfo1(), rowTextRoutingNumber.getValueInfo1(),
								rowTextAccountNumber.getValueInfo1(), contact.getEmail(), contact.getPhone(),
								rowEditAddres1.getValueInfo(), rowEditAddres2.getValueInfo(), rowCity.getValueInfo(), rowSpinner.getSelectedItem(),
								Integer.parseInt(rowZIP.getValueInfo()),rowSaveForFutureUse2.isActivatedOn());
				status=(Integer)statusJson[0];
				response=(JSONObject)statusJson[1];
				Server.getServer().getHome().getProfile().updateProfileIfNecessary();
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				stopProgressDialog();
				if(Server.isStatusOk(status)){
					if(!rowSaveForFutureUse2.isActivatedOn()){
						methodTemp=new PaymentMethodTemp(response, true);
					}
					else{
						methodTemp=null;
					}
					showDialogSuccesffully();
				}
				else if(status==422){
					OmegaFiActivity.showAlertMessage(getErrorJsonECheck(response), AddNewPaymentActivity.this);
				}
				else{
					OmegaFiActivity.showErrorConection(AddNewPaymentActivity.this, status, getResources().getString(R.string.object_not_found),false);
				}
			}	
    	};
    	task.execute();
    }
    
    private void updateECheckAsync(){
    	AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>(){

    		int status=0;
    		private JSONObject response=null;
    		
    		@Override
    		protected void onPreExecute() {
    			startProgressDialog("Adding E-Check...",getResources().getString(R.string.please_wait));
    		}
    		
			@Override
			protected Boolean doInBackground(Void... params) {
				final PaymentMethod method=methodsPayment.get(typeNewPayment.getSelectedItemPosition());
				Object[] statusJson=Server.getServer().getHome().getAccounts().updatePaymentECheck
						(idAccount,method.getId(),rowTextNameOnAccount.getValueInfo1(), rowTextRoutingNumber.getValueInfo1(),
								rowTextAccountNumber.getValueInfo1(), contact.getEmail(), contact.getPhone(),
								rowEditAddres1.getValueInfo(), rowEditAddres2.getValueInfo(), rowCity.getValueInfo(), rowSpinner.getSelectedItem(),
								Integer.parseInt(rowZIP.getValueInfo()),rowSaveForFutureUse2.isActivatedOn());
				status=(Integer)statusJson[0];
				response=(JSONObject)statusJson[1];
				Server.getServer().getHome().getProfile().updateProfileIfNecessary();
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				stopProgressDialog();
				if(Server.isStatusOk(status)){
					showDialogSuccesffullyUpdated();
				}
				else if(status==422){
					OmegaFiActivity.showAlertMessage(getErrorJsonECheck(response), AddNewPaymentActivity.this);
				}
				else{
					OmegaFiActivity.showErrorConection(AddNewPaymentActivity.this, status, getResources().getString(R.string.object_not_found),false);
				}
			}	
    	};
    	task.execute();
    }
    
    private String getErrorJsonECheck(JSONObject errorJson){
    	String error=null;
    	if(errorJson!=null){
	    	try {
	    		JSONObject jsonErrors=errorJson.getJSONObject("errors");
	    		error=Server.getFirstError(jsonErrors);
	    		} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}//jhgkjgh
    	return error;
    }
    
    private void activateButtonsCreate(boolean create){
    	if(create){
    		buttonAddContinue.setVisibility(View.VISIBLE);
    		linearSaveDelete.setVisibility(View.GONE);
    	}
    	else{
    		buttonAddContinue.setVisibility(View.GONE);
    		linearSaveDelete.setVisibility(View.VISIBLE);
    	}
    }
    
    public void savePaymentMethod(View button){
    	final PaymentMethod method=methodsPayment.get(typeNewPayment.getSelectedItemPosition());
    	if(method.isEChecked()){
    		updateECheckAsync();
    	}
    	else{
    		updateCreditCardAsync();
    	}
    }
    
    private void showDialogDeletePaymentMethod(){
    	final DialogTwoOptionsOF of=new DialogTwoOptionsOF(this);
    	of.setMessageDialog("Are you sure you want to delete this payment method");
    	of.setOption1("YES");
    	of.setOption2("NO");
    	of.setListenerOption1(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				of.dismissDialog();
				deletePaymentMethodAsync();
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
    
    private void deletePaymentMethodAsync(){
    	final PaymentMethod method=methodsPayment.get(typeNewPayment.getSelectedItemPosition());
    	AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>() {
			private int status=0;
    		
    		@Override
    		protected void onPreExecute() {
    			startProgressDialog("Deleting payment method...", getResources().getString(R.string.please_wait));
    		}
    		
			@Override
			protected Boolean doInBackground(Void... params) {
				status=Server.getServer().getHome().getAccounts().deletePaymentMethod(idAccount, method.getId());
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				stopProgressDialog();
				if(Server.isStatusOk(status)){
					showDeletedSucessFull();
				}
				else{
					OmegaFiActivity.showErrorConection(AddNewPaymentActivity.this, status, getResources().getString(R.string.object_not_found), false);
				}
			}
			
		};
		task.execute();
    }
    
    public void deletePaymentMethod(View button){
    	showDialogDeletePaymentMethod();
    }
    
    private void showDeletedSucessFull(){
    	final DialogInformationOF of=new DialogInformationOF(AddNewPaymentActivity.this);
    	of.setMessageDialog("Your payment method has been deleted.");
    	of.setButtonListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				of.dismissDialog();
				finish();
			}
		});
    	of.showDialog();
    }
    
}