package com.appsolution.logic;

import org.json.JSONException;
import org.json.JSONObject;

public class AddressContact extends ContactForm {

	private String city;
	private String line1;
	private String line2;
	private String state;
	
	public AddressContact(JSONObject address) {
		try {
			id=address.getInt("address_id");
			primary=address.getBoolean("primary");
			type=address.getString("type");
			city=address.getString("city");
			if(!address.isNull("line_1"))
				line1=address.getString("line_1");
			if(!address.isNull("line_2"))
				line2=address.getString("line_2");
			if(!address.isNull("state"))
				state=address.getString("state");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getCity() {
		return city;
	}

	public String getLine1() {
		return line1;
	}

	public String getLine2() {
		return line2;
	}

	public String getState() {
		return state;
	}
	
	

}
