package com.appsolution.logic;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class PaymentMethodTemp implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6811632931289899167L;
	private int id;
	private String nameMethod;
	private String typeMethod;
	private boolean eCheck;
	private String numberMethod;
	
	public PaymentMethodTemp(JSONObject jsonMethod, boolean isCheck){
		if(jsonMethod!=null){
			if(isCheck){
				try {
					jsonMethod=jsonMethod.getJSONObject("member_electronic_check");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				completeECheckMethod(jsonMethod);
			}
			else{
				try {
					jsonMethod=jsonMethod.getJSONObject("member_credit_card");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				completeCreditCardMethod(jsonMethod);
			}
		}
	}
	
	private void completeCreditCardMethod(JSONObject json){
		try {
			id=json.getInt("saved_creditcard_id");
			nameMethod=json.getString("name_on_card");
			typeMethod=json.getString("card_type").equalsIgnoreCase("Amex")?"American Express":json.getString("card_type");
			eCheck=false;
			numberMethod=json.getString("card_number").replace("*", "");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void completeECheckMethod(JSONObject json){
		try {
			id=json.getInt("saved_check_id");
			nameMethod=json.getString("name_on_account");
			typeMethod="Checking";
			eCheck=true;
			numberMethod=json.getString("account_number");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getId() {
		return id;
	}

	public String getNameMethod() {
		return nameMethod;
	}

	public String getTypeMethod() {
		return typeMethod;
	}

	public boolean iseCheck() {
		return eCheck;
	}

	public String getNumberMethod() {
		return numberMethod;
	}
	
	public String getNameTypeNumber(){
		return nameMethod+" - "+typeMethod+"("+getNumberMethod()+")";
	}
	
}