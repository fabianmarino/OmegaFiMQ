package com.appsolution.logic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class MemberRooster extends SimpleMember{

	private String[] phones=new String[2];
	private String[] emails=new String[2];
	private String[] adresses=new String[2];
	private String initiationDate="";
	
	public MemberRooster(JSONObject jsonMember) {
		super(jsonMember);
		try {
			if(jsonMember!=null){
				JSONObject individual=jsonMember.getJSONObject("individual");
				if(!individual.isNull("initiation_date")){
					initiationDate=individual.getString("initiation_date");
				}
				JSONArray arrayPhones=individual.getJSONArray("phone_numbers");
				chargePhones(arrayPhones);
				JSONArray arrayEmails=individual.getJSONArray("emails");
				chargeEmails(arrayEmails);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void chargePhones(JSONArray arrayPhones){
		if(arrayPhones.length()>0){
			try {
				for (int i = 0; i < arrayPhones.length(); i++) {
						JSONObject jsonPhone=arrayPhones.getJSONObject(i).getJSONObject("phone_number");
						Log.d("phone", jsonPhone.toString());
						if(jsonPhone.getBoolean("primary")){
							phones[0]=jsonPhone.getString("number");
						}
						else{
							if(phones[1]==null)
								phones[1]=jsonPhone.getString("number");
						}
					
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void chargeEmails(JSONArray arrayEmails){
		if(arrayEmails.length()>0){
			try {
				for (int i = 0; i < arrayEmails.length(); i++) {
						JSONObject jsonEmail=arrayEmails.getJSONObject(i).getJSONObject("email");
						if(jsonEmail.getBoolean("primary")){
							emails[0]=jsonEmail.getString("address");
						}
						else{
							if(emails[1]==null)
								emails[1]=jsonEmail.getString("address");
						}
					
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String[] getPhones() {
		return phones;
	}

	public String[] getEmails() {
		return emails;
	}

	public String[] getAdresses() {
		return adresses;
	}

	public String getInitiationDate() {
		if(initiationDate.length()>=10){
			return CalendarEvent.getFormatDate(3, initiationDate.substring(0, 10), "yyyy/MM/dd");
		}
		else{
			return null;
		}
	}
	
	


}
