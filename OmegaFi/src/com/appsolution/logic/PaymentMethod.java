package com.appsolution.logic;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentMethod {

	public static final String AMERICAN_EXPRESS_NAME="American Express";
	public static final String AMERICAN_EXPRESS_SERVICE="Amex";
	
	private String profileType;
	private String profileKind;
	private String routingNumber;
	private boolean profile;
	private String emailAddres;
	private String phoneNumber;
	
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String zipCode;
	
	private String monthYear;
	private boolean expired;
	
	private int id;
	private String cardName;
	private String cardAccountNumber;
	
	public PaymentMethod(JSONObject jsonAccount) {
		try {
			if(jsonAccount!=null){	
				profileType=jsonAccount.getString("profile_type");
				if(profileType.equalsIgnoreCase(PaymentMethod.AMERICAN_EXPRESS_SERVICE)){
					profileType=PaymentMethod.AMERICAN_EXPRESS_NAME;
				}
				id=jsonAccount.getInt("profile_id");
				cardName=jsonAccount.getString("card_account_name");
				cardAccountNumber=jsonAccount.getString("card_account_number");
				if(!jsonAccount.isNull("profile_kind"))
					profileKind=jsonAccount.getString("profile_kind");
				if(!jsonAccount.isNull("routing_number"))
					routingNumber=jsonAccount.getString("routing_number");
				profile=jsonAccount.getBoolean("profile");
				if(!jsonAccount.isNull("emailaddress"))
					emailAddres=jsonAccount.getString("emailaddress");
				if(!jsonAccount.isNull("phone_number"))
					phoneNumber=jsonAccount.getString("phone_number");
				if(!jsonAccount.isNull("address1"))
					addressLine1=jsonAccount.getString("address1");
				if(!jsonAccount.isNull("address2"))
					addressLine2=jsonAccount.getString("address2");
				if(!jsonAccount.isNull("city"))
					city=jsonAccount.getString("city");
				if(!jsonAccount.isNull("state"))
					state=jsonAccount.getString("state");
				if(!jsonAccount.isNull("zip_code"))
					zipCode=jsonAccount.getString("zip_code");
				if(!jsonAccount.isNull("exp_month"))
					monthYear=jsonAccount.getString("exp_month")+"/"+jsonAccount.getString("exp_year");
//				expired=jsonAccount.getBoolean("expired");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getProfileType() {
		if(profileType.equalsIgnoreCase(PaymentMethod.AMERICAN_EXPRESS_SERVICE)){
			profileType=PaymentMethod.AMERICAN_EXPRESS_NAME;
		}
		return profileType;
	}

	public int getId() {
		return id;
	}

	public String getCardName() {
		return cardName;
	}

	public String getCardAccountNumber() {
		return cardAccountNumber;
	}
	
	public boolean isEChecked(){
		boolean isChecked=false;
		if(profileType!=null){
			if(profileType.equalsIgnoreCase("checking")){	
				isChecked=true;
			}
			else if(profileType.equalsIgnoreCase(PaymentMethod.AMERICAN_EXPRESS_SERVICE)){
				profileType=PaymentMethod.AMERICAN_EXPRESS_NAME;
			}
		}
		return isChecked;
	}
	
	public String getNameTypeNumber(){
		return cardName+" - "+profileType+"("+getCardAccountNumber2()+")";
	}
	
	public String getProfileTypeNumber(){
		return profileType+"("+getCardAccountNumber2()+")";
	}
	
	public String getCardAccountNumber2(){
		return cardAccountNumber.replace("*", "");
	}

	public String getProfileKind() {
		return profileKind;
	}

	public String getRoutingNumber() {
		return routingNumber;
	}

	public boolean isProfile() {
		return profile;
	}

	public String getEmailAddres() {
		return emailAddres;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public String getMonthYear() {
		return monthYear;
	}

	public boolean isExpired() {
		return expired;
	}
	
}
