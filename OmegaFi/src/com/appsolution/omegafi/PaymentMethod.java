package com.appsolution.omegafi;

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
	
	

}
