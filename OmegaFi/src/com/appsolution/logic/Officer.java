package com.appsolution.logic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Officer {

	private int id;
	private String firstName;
	private String lastName;
	private String officeType;
	private String telephone;
	private String email;
	private String sourcePhoto;
	private String urlPhoto;
	
	public Officer(JSONObject officerJSON) {
		try {
		if(officerJSON!=null){
			JSONObject individual = officerJSON.getJSONObject("individual");
			id=individual.getInt("party_id");
			firstName=individual.getString("first_name");
			lastName=individual.getString("last_name");
			JSONObject type= officerJSON.getJSONObject("office_type");
			officeType=type.getString("office_name");
			if(individual.has("emails")){
				JSONArray emails=individual.getJSONArray("emails");
				if(emails!=null){
					if(emails.length()>0)
						email=emails.getJSONObject(0).getJSONObject("email").getString("address");
				}
			}
			if(individual.has("phone_numbers")){
				JSONArray phones=individual.getJSONArray("phone_numbers");
				if(phones!=null){
					if(phones.length()>0)
						telephone=phones.getJSONObject(0).getJSONObject("phone_number").getString("number");
				}
			}
			sourcePhoto=null;
			if(!individual.isNull("profile_picture")){
				sourcePhoto=individual.getJSONObject("profile_picture").getString("source");
				urlPhoto=individual.getJSONObject("profile_picture").getString("url");
			}
		}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public int getId() {
		return id;
	}


	public String getTelephone() {
		return telephone;
	}

	public String getEmail() {
		return email;
	}

	public String getOfficeType() {
		return officeType;
	}

	public String getFirstname() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getUrlPhoto() {
		return urlPhoto;
	}
	
	public String getCompleteName(){
		return firstName+" "+lastName;
	}
	
	public String getShortName(){
		String shortName="";
		if(lastName!=null){
			if(lastName.length()>0)
				shortName= firstName+" "+lastName.charAt(0)+".";
		}
		return shortName;
	}
	
	public String getPhoneNormal(){
		return telephone.replace("-", "");
	}

	public String sourcePhoto() {
		return sourcePhoto;
	}
	
	
	
}
