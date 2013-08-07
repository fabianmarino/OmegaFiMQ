package com.appsolution.logic;

import org.json.JSONException;
import org.json.JSONObject;

public class PhoneContact extends ContactForm{
	
	private String number;

	public PhoneContact(JSONObject phone) {
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

	public String getNumber() {
		return number;
	}

	
	

}
