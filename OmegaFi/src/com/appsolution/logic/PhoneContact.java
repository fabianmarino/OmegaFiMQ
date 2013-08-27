package com.appsolution.logic;

import org.json.JSONException;
import org.json.JSONObject;

public class PhoneContact extends ContactForm{
	private String number;

	public PhoneContact(JSONObject phone,int type) {
		if(type==ContactForm.TYPE_PROFILE){
			completePhoneContactProfile(phone);
		}
		else{
			completePhoneContactMember(phone);
		}
	}
	
	private void completePhoneContactProfile(JSONObject phone){
		try {
			id=phone.getInt("phone_number_id");
			primary=phone.getBoolean("primary");
			number=phone.getString("phone_number");
			type=phone.getString("phone_number_type");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void completePhoneContactMember(JSONObject phone){
		try {
			primary=phone.getBoolean("primary");
			number=phone.getString("number");
			type=phone.getString("type");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getNumber() {
		return number;
	}

	
	

}
