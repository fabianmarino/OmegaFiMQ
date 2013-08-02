package com.appsolution.logic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class MemberRooster extends SimpleMember{

	private String[] phones=new String[2];
	private String[] emails=new String[2];
	private String[] adresses=new String[2];
	private String initiationDate;
	private String profFacebook;
	private String profTwitter;
	private String profLinked;
	public static final String TYPE_FACEBOOK="facebook";
	public static final String TYPE_TWITTER="twitter";
	public static final String TYPE_LINKEDIN="linkedin";
	
	
	public MemberRooster(JSONObject jsonMember) {
		super(jsonMember);
		try {
			if(jsonMember!=null){
				JSONObject individual=jsonMember.getJSONObject("individual");
				if(!individual.isNull("initiation_date")){
					initiationDate=individual.getString("initiation_date");
				}
				completeSocialProfiles(individual.getJSONArray("social_profiles"));
				JSONArray arrayPhones=individual.getJSONArray("phone_numbers");
				chargePhones(arrayPhones);
				JSONArray arrayEmails=individual.getJSONArray("emails");
				chargeEmails(arrayEmails);
				if(individual.has("addresses")){
					JSONArray arrayAddresses=individual.getJSONArray("addresses");
					chargeAddresses(arrayAddresses);
				}
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
	
	private void chargeAddresses(JSONArray arrayAddresses){
		if(arrayAddresses.length()>0){
			try {
				for (int i = 0; i < arrayAddresses.length(); i++) {
						JSONObject jsonEmail=arrayAddresses.getJSONObject(i).getJSONObject("address");
						if(jsonEmail.getBoolean("primary")){
							adresses[0]=jsonEmail.getString("line_1")+"¿"+jsonEmail.getString("line_2")+"¿"+jsonEmail.getString("city");
						}
						else{
							if(adresses[1]==null)
								adresses[1]=jsonEmail.getString("line_1")+"¿"+jsonEmail.getString("line_2")+"¿"+jsonEmail.getString("city");
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
	
	private void completeSocialProfiles(JSONArray arraySocial){
		for (int i = 0; i < arraySocial.length(); i++) {
			try {
				JSONObject jsonSocial=arraySocial.getJSONObject(i).getJSONObject("social_profile");
				if(jsonSocial.getString("type").equalsIgnoreCase(TYPE_FACEBOOK)){
					profFacebook=jsonSocial.getString("social_url");
				}
				else if(jsonSocial.getString("type").equalsIgnoreCase(TYPE_LINKEDIN)){
					profLinked=jsonSocial.getString("social_url");
				}
				else{
					profTwitter=jsonSocial.getString("social_url");
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String getProfFacebook() {
		return profFacebook;
	}

	public String getProfTwitter() {
		return profTwitter;
	}

	public String getProfLinked() {
		return profLinked;
	}
	
	


}
