package com.appsolution.omegafi;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.appsolution.layouts.CustomDatePickerDialog;
import com.appsolution.layouts.DialogInformationOF;
import com.appsolution.layouts.DialogSelectableOF;
import com.appsolution.layouts.RowEditInformation;
import com.appsolution.layouts.RowEditNameTopInfo;
import com.appsolution.layouts.RowEditTextOmegaFi;
import com.appsolution.layouts.RowInformation;
import com.appsolution.layouts.RowSpinnerNameTopInfo;
import com.appsolution.layouts.RowToogleOmegaFi;
import com.appsolution.layouts.SectionOmegaFi;
import com.appsolution.layouts.SpinnerNameTopInfo;
import com.appsolution.logic.CalendarEvent;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
	
	private LinearLayout linearChekingAccount;
	
	private SectionOmegaFi sectionAddress;
	private RowEditNameTopInfo rowEditAddres1;
	private RowEditNameTopInfo rowEditAddres2;
	
	private LinearLayout linearCityStateZIP;
	private RowEditNameTopInfo rowCity;
	private SpinnerNameTopInfo rowSpinner;
	private RowEditNameTopInfo rowZIP;
	
	
	private Button buttonAddContinue;
	private DatePickerDialog mDialog;
	
	static final int DATE_DIALOG_ID = 1;
    private int mYear = 2013;
    private int mMonth = 5;
    private int mDay = 30;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_payment);
		typeNewPayment=(Spinner)findViewById(R.id.spinnerCreditECheck);
		this.completeSpinnerNewPayment();
		linearCreditDebit=(LinearLayout)findViewById(R.id.linearCreditDebitCard);
		linearChekingAccount=(LinearLayout)findViewById(R.id.linearCheckingAccount);
		rowTextNameOnCard=(RowEditTextOmegaFi)findViewById(R.id.textNameOnCard);
		rowSelectCardType=(RowInformation)findViewById(R.id.selectCardType);
		rowSaveForFutureUse=(RowToogleOmegaFi)findViewById(R.id.rowSaveForFutureUse);
		rowExpirationDate=(RowInformation)findViewById(R.id.rowSelectExpirationDate);
		rowExpirationDate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				showExpirationDate(null);
			}
		});
		sectionAddress=(SectionOmegaFi)findViewById(R.id.sectionAddresAddPayment);
		buttonAddContinue=(Button)findViewById(R.id.buttonAddContinue);
		this.completeFields();

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
	}
	
	private void completeFields(){
		rowSaveForFutureUse.setOnChangeListenerToogle(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					buttonAddContinue.setText("Add");
				}
				else{
					buttonAddContinue.setText("Continue");
				}
			}
		});
		
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
		
		rowSpinner=new SpinnerNameTopInfo(this);
		rowSpinner.setNameInfoTop("State");
		rowSpinner.setListSpinner(getArrayStates());
		LayoutParams paramsState=new LayoutParams(0, LayoutParams.FILL_PARENT,2);
		rowSpinner.setLayoutParams(paramsState);
		
		rowZIP=new RowEditNameTopInfo(getApplicationContext());
		rowZIP.setTypeInputEditText(InputType.TYPE_CLASS_NUMBER);
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
		ArrayList<String> list=new ArrayList<String>();
		list.add("Visa");
		list.add("Mastercard");
		list.add("Discover");
		list.add("American Express");
		final DialogSelectableOF selectable=new DialogSelectableOF(this);
		selectable.setTitleDialog("Select Card Type");
		selectable.setOptionsSelectables(list);
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
	
	public void addContinueNewPayment(View view){
		final DialogInformationOF dialog=new DialogInformationOF(this);
		dialog.setMessageDialog("You've successfuly added a payment method");
		dialog.setButtonListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismissDialog();
			}
		});
		dialog.showDialog();
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
				Log.d("Cambia spinner", position+"");
				switch (position) {
				case 0:
					linearChekingAccount.setVisibility(LinearLayout.GONE);
					linearCreditDebit.setVisibility(LinearLayout.VISIBLE);
					break;
				case 1:
					linearCreditDebit.setVisibility(LinearLayout.GONE);
					linearChekingAccount.setVisibility(LinearLayout.VISIBLE);
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

    @SuppressWarnings("deprecation")
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
	
}