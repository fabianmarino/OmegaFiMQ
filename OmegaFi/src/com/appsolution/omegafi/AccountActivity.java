package com.appsolution.omegafi;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.appsolution.layouts.DialogContactAccount;
import com.appsolution.layouts.DialogSelectableOF;
import com.appsolution.layouts.LabelInfoVertical;
import com.appsolution.layouts.RowCheckOmegaFi;
import com.appsolution.layouts.RowInformation;
import com.appsolution.layouts.RowToogleOmegaFi;
import com.appsolution.layouts.SectionAccountUser;
import com.appsolution.layouts.SectionOmegaFi;
import com.appsolution.layouts.UserContactLayout;
import com.appsolution.logic.Account;
import com.appsolution.logic.PaymentMethod;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;

public class AccountActivity extends OmegaFiActivity implements OnClickListener{

	
	private UserContactLayout userContact;
	private SectionOmegaFi accountContacts;
	private SectionOmegaFi accountDetails;
	private Activity activity;
	private RowToogleOmegaFi toogleAutoPay;
	private Bundle accountId;
	private Account actualAccount;
	private LabelInfoVertical infoNumberAccount;
	private LabelInfoVertical infoBalanceDue;
	
	private RowInformation rowDueOn;
	private RowInformation rowBalanceAsOf;
	private RowInformation rowPayments;
	private RowInformation rowCredits;
	private RowInformation rowDebits;
	private RowInformation rowCurrentBalance;
	private ArrayList<PaymentMethod> methods;
	private RowInformation section4;
	
	private int paymentSelected=-1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account);
		activity=this;
		userContact=(UserContactLayout)findViewById(R.id.userContactAccount);
		accountContacts=(SectionOmegaFi)findViewById(R.id.sectionAccountContacts);
		accountDetails=(SectionOmegaFi)findViewById(R.id.sectionAccountDetails);
		toogleAutoPay=(RowToogleOmegaFi)findViewById(R.id.toogleAutoPay);
		toogleAutoPay.backgroundActiveForm();
		infoNumberAccount=(LabelInfoVertical)findViewById(R.id.accountNumberInfo);
		infoBalanceDue=(LabelInfoVertical)findViewById(R.id.balanceDueInfo);
		rowDueOn=(RowInformation)findViewById(R.id.rowBalanceDueOn);
		rowBalanceAsOf=(RowInformation)findViewById(R.id.rowBalanceAsOf);
		rowPayments=(RowInformation)findViewById(R.id.rowPaymentsAccount);
		rowCredits=(RowInformation)findViewById(R.id.rowCreditsAccount);
		rowDebits=(RowInformation)findViewById(R.id.rowDebitsAccount);
		rowCurrentBalance=(RowInformation)findViewById(R.id.rowCurrentBalance);
		this.completeAccountContacts();
		this.completeAccountDetails();
		accountId=getIntent().getExtras();
		this.chargeAccountSelected(accountId.getInt("id"));
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBarCustom.setTitle("ACCOUNT INFORMATION");
		actionBar.setCustomView(actionBarCustom);
	}
	
	public void completeAccountContacts(){
		for (int i = 0; i < 5; i++) {
			RowInformation row=new RowInformation(activity);
			row.setNameInfo("Contact #"+i);
			row.setBorderBottom(true);
			row.setNameSubInfo("Info description");
			row.setColorFontRowInformation(Color.BLACK);
			row.setTypeFaceNameSubInfo(OmegaFiActivity.getFont(activity, 0));
			row.setColorNameSubInfo(Color.GRAY);
			row.setVisibleArrow(true);
			row.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					DialogContactAccount diag=new DialogContactAccount(activity,false);
					diag.showDialog();
					
				}
			});
			int padding=super.getResources().getDimensionPixelSize(R.dimen.padding_6dp);
			row.setPaddingRow(padding, padding,padding,padding);
			accountContacts.addView(row);
		}
	}
	
	public void completeAccountDetails(){
			RowInformation section3=new RowInformation(getApplicationContext());
			section3.setNameInfo(getResources().getString(R.string.scheduled_payments));
			section3.setColorFontRowInformation(Color.GRAY);
			section3.setVisibleArrow(true);
			
			section3.setId(104);
			section3.setOnClickListener(this);
			accountDetails.addView(section3);
			RowInformation section=new RowInformation(getApplicationContext());
			section.setNameInfo("History");
			section.setColorFontRowInformation(Color.GRAY);
			section.setVisibleArrow(true);
			
			
			section.setId(101);
			section.setOnClickListener(this);
			accountDetails.addView(section);
			RowInformation section1=new RowInformation(getApplicationContext());
			section1.setNameInfo(getResources().getString(R.string.scheduled_of_charges));
			section1.setColorFontRowInformation(Color.GRAY);
			section1.setVisibleArrow(true);
			
			section1.setOnClickListener(this);
			section1.setId(106);
			accountDetails.addView(section1);
			RowInformation section2=new RowInformation(getApplicationContext());
			section2.setNameInfo("Statements");
			section2.setVisibleArrow(true);
			section2.setColorFontRowInformation(Color.GRAY);
			
			section2.setId(107);
			section2.setOnClickListener(this);
			accountDetails.addView(section2);
			section4=new RowInformation(getApplicationContext());
			section4.setNameInfo("Payment Methods");
			section4.setVisibleArrow(true);
			section4.setColorFontRowInformation(Color.GRAY);
			section4.setId(105);
			section4.setOnClickListener(this);
			
			accountDetails.addView(section4);
	}
	
	public void selectPayMethod(View view){
		if(!methods.isEmpty()){
			final DialogSelectableOF selectable=new DialogSelectableOF(this);
			selectable.setOptionsSelectables(getPaymentMethodsList(methods));
			selectable.setTitleDialog("Select Payment Method");
			selectable.setTextButton("Save");
			selectable.setCloseOnSelectedItem(false);
			if(paymentSelected!=-1){
				selectable.setSelectedIndex(paymentSelected);
			}
			selectable.setButtonListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					paymentSelected=selectable.getIndexSelected();
					section4.setValueInfo(methods.get(paymentSelected).getProfileTypeNumber());
					selectable.dismissDialog();
				}
			});
			selectable.showDialog();
		}
		else{
			OmegaFiActivity.showAlertMessage("No payment methods", AccountActivity.this);
		}
	}
	
	public void activityPayNow(View button){
		Intent viewPayNow=new Intent(getApplicationContext(), PayNowActivity.class);
		viewPayNow.putExtra("id", actualAccount.getId());
		startActivityForResult(viewPayNow, OmegaFiActivity.ACTIVITY_PAY_NOW);
	}
	
	public void goToMyProfile(View user){
		Intent activityProfile=new Intent(this, MyProfileActivity.class);
		startActivity(activityProfile);
	}

	@Override
	public void onClick(View v) {
		Log.e("Account -> ", v.getId()+"");
		switch (v.getId()) {
		case 100:
			Intent viewMake=new Intent(this, MakePaymentActivity.class);
			startActivity(viewMake);
			break;
		case 101:
			Intent viewHistory=new Intent(this, HistoryActivity.class);
			viewHistory.putExtra("id", actualAccount.getId());
			startActivity(viewHistory);
			break;
		case 104:
			Intent viewScheduledPayments=new Intent(this, ScheduledPaymentsActivity.class);
			viewScheduledPayments.putExtra("id", actualAccount.getId());
			startActivityForResult(viewScheduledPayments,OmegaFiActivity.ACTIVITY_SCHEDULED_PAYMENTS);
			break;
		case 105:
			this.selectPayMethod(null);
			break;
		case 106:
			Intent viewScheduleCharges=new Intent(this, ScheduleChargesActivity.class);
			viewScheduleCharges.putExtra("id", actualAccount.getId());
			startActivity(viewScheduleCharges);
			break;
		case 107:
			Intent viewStatements=new Intent(this, StatementsActivity.class);
			viewStatements.putExtra("id", actualAccount.getId());
			startActivity(viewStatements);
			break;
		default:
			break;
		}
		
	}
	
	private void chargeAccountSelected(final int id){
		AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>() {
			int status=0;
			private Account account=null;
			
			@Override
			protected void onPreExecute() {
				startProgressDialog("Charging Account", getResources().getString(R.string.please_wait));
			}
			
			@Override
			protected Boolean doInBackground(Void... params) {
				Object[] objectAux=MainActivity.servicesOmegaFi.getHome().getAccounts().getStatusAccount(id);
				status=(Integer)objectAux[0];
				account=(Account)objectAux[1];
				actualAccount=account;
				if(actualAccount!=null){
					Object[] statusMethods=MainActivity.servicesOmegaFi.getHome().getPaymentMethods(actualAccount.getId());
					methods=(ArrayList<PaymentMethod>)statusMethods[1];
				}
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				if(actualAccount!=null){
					userContact.chargeImageFromUrlAsync(actualAccount.getSourcePhoto(), actualAccount.getUrlPhotoAccount());
					userContact.setNameUserProfile(actualAccount.getCompleteName());
					userContact.setSubTitleProfile(actualAccount.getNameOrgDesignationOrg());
					userContact.setThirdLine(actualAccount.getUniversity());
					infoNumberAccount.setValueLabel(actualAccount.getId()+"");
					infoBalanceDue.setValueLabel("$ "+actualAccount.getAdjustedBalance());
					rowBalanceAsOf.setNameSubInfo(actualAccount.getDateBalanceAsOf());
					rowBalanceAsOf.setValueInfo("$"+actualAccount.getMoneyBalanceAsOf());
					rowDueOn.setValueInfo(actualAccount.getDueOn());
					rowPayments.setValueInfo("$ "+actualAccount.getPaymentsLast());
					rowCredits.setValueInfo("$ "+actualAccount.getCreditsLast());
					rowDebits.setValueInfo("$ "+actualAccount.getActivityLast());
					rowCurrentBalance.setValueInfo("$ "+actualAccount.getCurrentBalance());
					toogleAutoPay.setActivateOn(actualAccount.isAutoPay());
					if(!methods.isEmpty()){
						paymentSelected=0;
						section4.setValueInfo(methods.get(paymentSelected).getProfileTypeNumber());
					}
				}
				stopProgressDialog();
			}
		};		
		task.execute();
	}
	
	public static ArrayList<String> getPaymentMethodsList(ArrayList<PaymentMethod> methods){
		ArrayList<String> list=new ArrayList<String>();
		if(methods!=null){
			for (PaymentMethod method:methods) {
				list.add(method.getCardName()+","+method.getProfileType()+" ("+method.getId()+")");
			}
		}
		return list;
	}
}
