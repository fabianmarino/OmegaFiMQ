package com.appsolution.logic;

import org.json.JSONException;
import org.json.JSONObject;

public class Charge {

	private String description;
	private String amount;
	
	public Charge(JSONObject jsonCharge) {
		try {
			if(jsonCharge!=null){
				description=jsonCharge.getString("description");
				amount=jsonCharge.getString("amount");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getDescription() {
		return description;
	}

	public String getAmount() {
		return amount;
	}
	
	

}
