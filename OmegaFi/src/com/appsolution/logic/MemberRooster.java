package com.appsolution.logic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class MemberRooster extends SimpleMember{

	private PhoneContact[] phones=new PhoneContact[2];
	private EmailContact[] emails=new EmailContact[2];
	private AddressContact[] addresses=new AddressContact[2];
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
				if(individual.has("phone_numbers"))
					this.completePhones(individual.getJSONArray("phone_numbers"));
				if(individual.has("emails"))
					this.completeEmails(individual.getJSONArray("emails"));
				if(individual.has("addresses"))
					this.completeAdresses(individual.getJSONArray("addresses"));
				
				completeSocialProfiles(individual.getJSONArray("social_profiles"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void completePhones(JSONArray phones){
		for (int i = 0; i < phones.length(); i++) {
			try {
				JSONObject jsonPhone=phones.getJSONObject(i).getJSONObject("phone_number");
				PhoneContact phone=new PhoneContact(jsonPhone,ContactForm.TYPE_MEMBER);
				if(phone.isPrimary())
					this.phones[0]=phone;
				else if(this.phones[1]==null)
					this.phones[1]=phone;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void completeEmails(JSONArray emails){
		for (int i = 0; i < emails.length(); i++) {
			try {
				JSONObject jsonEmail=emails.getJSONObject(i).getJSONObject("email");
				EmailContact email=new EmailContact(jsonEmail,ContactForm.TYPE_MEMBER);
				if(email.isPrimary())
					this.emails[0]=email;
				else if(this.emails[1]==null)
					this.emails[1]=email;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void completeAdresses(JSONArray addresses){
		for (int i = 0; i < addresses.length(); i++) {
			try {
				JSONObject jsonAddress=addresses.getJSONObject(i).getJSONObject("address");
				AddressContact address=new AddressContact(jsonAddress);
				
				if(address.isPrimary())
					this.addresses[0]=address;
				else if(this.addresses[1]==null)
					this.addresses[1]=address;
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String getInitiationDate() {
		String initiation=null;
		if(initiationDate!=null){
			if(initiationDate.length()>=10){
				initiation= CalendarEvent.getFormatDate(3, initiationDate.substring(0, 10), "yyyy/MM/dd");
			}
		}
		return initiation;
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

	public PhoneContact[] getPhones() {
		return phones;
	}

	public EmailContact[] getEmails() {
		return emails;
	}

	public AddressContact[] getAddresses() {
		return addresses;
	}
	
	


}
