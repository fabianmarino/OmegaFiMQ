package com.appsolution.logic;

import org.json.JSONException;
import org.json.JSONObject;

public class ContactAccount {

	public static final int TYPE_OMEGAFI=0;
	public static final int TYPE_ORGANIZATION=1;
	
	
	private boolean isRegularContact;
	private String nameContact;
	private String titleDialog;
	private String titleContact;
	private String phoneContact;
	private String emailContact;
	private int organizationId;
	
	
	
	public ContactAccount(JSONObject jsonContact, boolean isRegular, int type) {
		this.isRegularContact=isRegular;
		if(type==TYPE_OMEGAFI){
			completeContactRegular(jsonContact);
		}
		else{
			completeContactNotRegular(jsonContact);
		}
	}
	
	public ContactAccount(String name, String title, String phone, String phoneBilling){
		isRegularContact=false;
		nameContact=name;
		titleContact=title;
		phoneContact=phone;
		emailContact="";
		organizationId=-1;
	}
	
	private void completeContactRegular(JSONObject jsonContact){
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
	
	private void completeContactNotRegular(JSONObject jsonContact){
		try {
			if(!jsonContact.isNull("contact_on_statement_name"))
				nameContact=jsonContact.getString("contact_on_statement_name");
			if(!jsonContact.isNull("contact_on_statement_title"))
				titleContact=jsonContact.getString("contact_on_statement_title");
			if(!jsonContact.isNull("contact_on_statement_email"))
				emailContact=jsonContact.getString("contact_on_statement_email");
			if(!jsonContact.isNull("contact_on_statement_phone_number"))
				phoneContact=jsonContact.getString("contact_on_statement_phone_number");
			if(!jsonContact.isNull("organization_id"))
				organizationId=jsonContact.getInt("organization_id");
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
		phoneContact.replace(".", "-");
		return phoneContact;
	}

	public String getEmailContact() {
		return emailContact;
	}
	
	public String getPhoneToCall(){
		return phoneContact.replace("-", "").replace(".", "");
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public String getTitleDialog() {
		return titleDialog;
	}

	public void setTitleDialog(String titleDialog) {
		this.titleDialog = titleDialog;
	}

	public void setPhoneContact(String phoneContact) {
		this.phoneContact = phoneContact;
	}
	
	

}
