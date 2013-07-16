package com.appsolution.logic;

import org.json.JSONException;
import org.json.JSONObject;

import com.appsolution.layouts.EventNewsContent;

public class SimpleScheduledPayment {

	private int id;
	private String paymentDate;
	private String stateScheduled;
	private String paymentAmount;
	private int idProfilepayment;
	
	public SimpleScheduledPayment(JSONObject jsonPayment) {
		try {
			if(jsonPayment!=null){
				JSONObject jsonScheduled=jsonPayment.getJSONObject("scheduled_payment");
				id=jsonScheduled.getInt("memberscheduledpaymentid");
				paymentDate=jsonScheduled.getString("paymentdate");
				paymentAmount=jsonScheduled.getString("paymentamount");
				stateScheduled="Processing";//Cambiar cuando se obtenga la respuesta del cliente.
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

	public String getStateScheduled() {
		return stateScheduled;
	}

	public String getPaymentAmount() {
		return paymentAmount;
	}

	public int getIdProfilepayment() {
		return idProfilepayment;
	}
	
	
	
	
}