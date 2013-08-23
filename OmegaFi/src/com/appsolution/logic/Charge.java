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
		if(amount!=null){
			int index=amount.indexOf(".");
			if(index!=1){
				int rest=amount.length()-index;
				if(rest<3){
					amount+="0";
				}
			}
			}
		String amoun=amount;
		if(amount!=null){
			amoun=amoun.contains("-") ?amoun.replace("-", "(")+")":amoun;
		}
			return amoun;
	}
	
	

}
