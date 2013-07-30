package com.appsolution.logic;

import org.json.JSONException;
import org.json.JSONObject;

import com.appsolution.layouts.EventNewsContent;

public class SimpleScheduledPayment {

	private int id;
	public static final int STATE_PENDING=0;
	public static final int STATE_PROCESSING=1;
	private String paymentDate;
	private int stateScheduled=SimpleScheduledPayment.STATE_PENDING;
	private String paymentAmount;
	private int idProfilepayment;
	
	/**
	 * Variables using when the idProfilepayment is absent
	 */
	private String nameMethodPayment;
	private String typeMethodPayment;
	private String accountNumber;
	
	public SimpleScheduledPayment(JSONObject jsonPayment, int typeScheduled) {
		if(typeScheduled==SimpleScheduledPayment.STATE_PENDING){
			completeScheduledTypePending(jsonPayment);
		}
		else{
			completeScheduledTypeProcesing(jsonPayment);
		}
	}
	
	private void completeScheduledTypePending(JSONObject jsonPayment){
		try {
			if(jsonPayment!=null){
				JSONObject jsonScheduled=jsonPayment.getJSONObject("scheduled_payment");
				id=jsonScheduled.getInt("memberscheduledpaymentid");
				stateScheduled=SimpleScheduledPayment.STATE_PENDING;
				paymentDate=jsonScheduled.getString("paymentdate");
				paymentAmount=jsonScheduled.getString("paymentamount");
				if(!jsonScheduled.isNull("memberpaymentcreditcardid")){
					idProfilepayment=jsonScheduled.getInt("memberpaymentcreditcardid");
				}
				else if(!jsonScheduled.isNull("memberpaymentecheckid")){
					idProfilepayment=jsonScheduled.getInt("memberpaymentecheckid");
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void completeScheduledTypeProcesing(JSONObject jsonPayment){
		try {
			if(jsonPayment!=null){
				id=jsonPayment.getInt("id");
				stateScheduled=SimpleScheduledPayment.STATE_PROCESSING;
				paymentDate=jsonPayment.getString("payment_date");
				paymentAmount=jsonPayment.getString("payment_amount");
				nameMethodPayment=jsonPayment.getString("name");
				typeMethodPayment=jsonPayment.getString("account_type");
				accountNumber=jsonPayment.getString("account");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getId() {
		return id;
	}

	public String getPaymentDate() {
		String date=null;
		if(paymentDate!=null){
			if(paymentDate.length()>=10){
				date=CalendarEvent.getFormatDate(3, paymentDate.substring(0, 10), "yyyy/MM/dd");
			}
		}
		return date;
	}

	public String getPaymentAmount() {
		return paymentAmount;
	}

	public int getIdProfilepayment() {
		return idProfilepayment;
	}

	public int getStateScheduled() {
		return stateScheduled;
	}
	
	public String getStateScheduledWord(){
		if(stateScheduled==SimpleScheduledPayment.STATE_PENDING){
			return "Pending";
		}
		else{
			return "Processing";
		}
	}

	public static int getStatePending() {
		return STATE_PENDING;
	}

	public static int getStateProcessing() {
		return STATE_PROCESSING;
	}

	public String getNameMethodPayment() {
		return nameMethodPayment;
	}

	public String getAccountType() {
		return typeMethodPayment;
	}

	public String getNameTypeMethodPayment(){
		return nameMethodPayment+" - "+typeMethodPayment+"("+getAccountNumber2()+")";
	}

	public String getTypeMethodPayment() {
		return typeMethodPayment;
	}

	public String getAccountNumber() {
		return accountNumber;
	}
	
	public String getAccountNumber2() {
		return accountNumber.replace("*", "");
	}
	
	
	
}