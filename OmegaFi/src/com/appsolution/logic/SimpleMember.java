package com.appsolution.logic;

import org.json.JSONException;
import org.json.JSONObject;

public class SimpleMember {

	private int id;
	private String firstName;
	private String middleName;
	private String lastName;
	private String statusName;
	private String sourcePhoto;
	private String urlPhoto;
	
	public SimpleMember(JSONObject jsonMember) {
		if(jsonMember!=null){
			try {
				JSONObject individual=jsonMember.getJSONObject("individual");
				id=individual.getInt("party_id");
				firstName=individual.getString("first_name");
				middleName=individual.getString("middle_name");
				lastName=individual.getString("last_name");
				
				JSONObject memberStatus=individual.getJSONObject("member_status");
				if(memberStatus!=null){
					statusName=memberStatus.getString("status_name");
				}
				if(!individual.isNull("profile_picture")){
					JSONObject photoJson=individual.getJSONObject("profile_picture");
					sourcePhoto=photoJson.getString("source");
					urlPhoto=photoJson.getString("url");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}



	public int getId() {
		return id;
	}



	public String getFirstName() {
		return firstName;
	}



	public String getMiddleName() {
		return middleName;
	}



	public String getLastName() {
		return lastName;
	}



	public String getStatusName() {
		return statusName;
	}
	
	public String getLastFirstName(){
		return lastName+", "+firstName;
	}



	public String getSourcePhoto() {
		return sourcePhoto;
	}



	public String getUrlPhoto() {
		return urlPhoto;
	}
	
	public String getCompleteName(){
		return firstName+" "+lastName;
	}
	
	

}
