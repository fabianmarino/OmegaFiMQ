package com.appsolution.logic;

import org.json.JSONException;
import org.json.JSONObject;

public class ContactAccount {

	private boolean isRegularContact;
	private String nameContact;
	private String titleContact;
	private String phoneContact;
	private String emailContact;
	
	
	public ContactAccount(JSONObject jsonContact, boolean isRegular) {
		this.isRegularContact=isRegular;
		if(isRegularContact){
			completeContactRegular(jsonContact);
		}
		else{
			completeContactNotRegular(jsonContact);
		}
	}
	
	private void completeContactRegular(JSONObject jsonContact){
		try {
			if(!jsonContact.isNull("contact_on_statement_name"))
				nameContact=jsonContact.getString("contact_on_statement_name");
			if(!jsonContact.isNull("contact_on_statement_title"))
				titleContact=jsonContact.getString("contact_on_statement_title");
			if(!jsonContact.isNull("contact_on_statement_email"))
				emailContact=jsonContact.getString("contact_on_statement_email");
			if(!jsonContact.isNull("contact_on_statement_phone_number"))
				phoneContact=jsonContact.getString("contact_on_statement_phone_number");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void completeContactNotRegular(JSONObject jsonContact){
		try {
			if(!jsonContact.isNull("contact_name"))
				nameContact=jsonContact.getString("contact_name");
			if(!jsonContact.isNull("contact_title"))
				titleContact=jsonContact.getString("contact_title");
			if(!jsonContact.isNull("contact_email"))
				emailContact=jsonContact.getString("contact_email");
			if(!jsonContact.isNull("contact_phone"))
				phoneContact=jsonContact.getInt("contact_phone")+"";
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isRegularContact() {
		return isRegularContact;
	}

	public String getNameContact() {
		return nameContact;
	}

	public String getTitleContact() {
		return titleContact;
	}

	public String getPhoneContact() {
		return phoneContact;
	}

	public String getEmailContact() {
		return emailContact;
	}
	
	public String getPhoneToCall(){
		return phoneContact.replace("-", "");
	}
	
	

}
