package com.appsolution.logic;

import org.json.JSONException;
import org.json.JSONObject;

public class TypeFormContact {

	public static final int TYPE_PHONE=0;
	public static final int TYPE_EMAIL=1;
	public static final int TYPE_ADDRESS=2;
	
	public static final String TYPE_HOME="Home";
	public static final String TYPE_FORMER_HOME="Former Home";
	public static final String TYPE_WORK="Work";
	public static final String TYPE_SCHOOL="School";
	public static final String TYPE_OTHER="Other";
	
	private int id;
	private String type;
	
	public TypeFormContact(JSONObject object, int type) {
		switch (type) {
		case TYPE_PHONE:
			completeTypePhone(object);
			break;
		case TYPE_EMAIL:
			completeTypeEmail(object);
			break;
		case TYPE_ADDRESS:
			completeTypeAddress(object);
			break;

		default:
			break;
		}
	}
	
	private void completeTypePhone(JSONObject typePhone){
		try {
			id=typePhone.getInt("phone_number_type_id");
			type=typePhone.getString("phone_number_type");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void completeTypeEmail(JSONObject typeEmail){
		try {
			id=typeEmail.getInt("email_address_type_id");
			type=typeEmail.getString("email_address_type");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void completeTypeAddress(JSONObject typeAddress){
		try {
			id=typeAddress.getInt("address_type_id");
			type=typeAddress.getString("type");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getId() {
		return id;
	}

	public String getType() {
		return type;
	}
	
	

}
