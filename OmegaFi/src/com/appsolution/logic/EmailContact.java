package com.appsolution.logic;

import org.json.JSONException;
import org.json.JSONObject;

public class EmailContact extends ContactForm{
	
	private String email;
	
	
	public EmailContact(JSONObject email, int type) {
		if(type==ContactForm.TYPE_PROFILE){
			completeEmailProfile(email);
		}
		else{
			completeEmailMember(email);
		}
	}
	
	private void completeEmailProfile(JSONObject email){
		try {
			id=email.getInt("email_address_id");
			primary=email.getBoolean("primary");
			type=email.getString("email_address_type");
			this.email=email.getString("email_address");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void completeEmailMember(JSONObject email){
		try {
			primary=email.getBoolean("primary");
			type=email.getString("type");
			this.email=email.getString("address");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public String getEmail() {
		return email;
	}
	
	

}
