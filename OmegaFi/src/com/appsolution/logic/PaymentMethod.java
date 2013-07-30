package com.appsolution.logic;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentMethod {

	private String profileType;
	private int id;
	private String cardName;
	private String cardAccountNumber;
	
	public PaymentMethod(JSONObject jsonAccount) {
		try {
			if(jsonAccount!=null){	
				profileType=jsonAccount.getString("profile_type");
				id=jsonAccount.getInt("profile_id");
				cardName=jsonAccount.getString("card_account_name");
				cardAccountNumber=jsonAccount.getString("card_account_number");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getProfileType() {
		return profileType;
	}

	public int getId() {
		return id;
	}

	public String getCardName() {
		return cardName;
	}

	public String getCardAccountNumber() {
		return cardAccountNumber;
	}
	
	public boolean isEChecked(){
		boolean isChecked=false;
		if(profileType!=null){
			if(profileType.equalsIgnoreCase("checking")){	
				isChecked=true;
			}
		}
		return isChecked;
	}
	
	public String getNameTypeNumber(){
		return cardName+" - "+profileType+"("+getCardAccountNumber2()+")";
	}
	
	public String getProfileTypeNumber(){
		return profileType+"("+getCardAccountNumber2()+")";
	}
	
	public String getCardAccountNumber2(){
		return cardAccountNumber.replace("*", "");
	}
	

}
