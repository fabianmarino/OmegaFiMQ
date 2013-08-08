package com.appsolution.logic;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.appsolution.omegafi.OmegaFiActivity;
import com.appsolution.services.Server;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.util.Log;
import android.widget.ImageView;

public class Officer {

	private int id;
	private String firstName;
	private String lastName;
	private String officeType;
	private String telephone;
	private String email;
	private String hostPhoto;
	private String urlPhoto;
	
	AsyncTask<Void, Integer, Boolean> task;
	
	
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
					email=emails.getJSONObject(0).getJSONObject("email").getString("address");
				}
			}
			if(individual.has("phone_numbers")){
				JSONArray phones=individual.getJSONArray("phone_numbers");
				if(phones!=null){
					telephone=phones.getJSONObject(0).getJSONObject("phone_number").getString("number");
				}
			}
			hostPhoto=null;
			if(!individual.isNull("profile_picture")){
				hostPhoto=individual.getJSONObject("profile_picture").getString("source");
				if(hostPhoto.equals("OmegaFi")){
					urlPhoto=Server.HOST+individual.getJSONObject("profile_picture").getString("url");
				}
				else{
					urlPhoto=individual.getJSONObject("profile_picture").getString("url");
				}
			}
		}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void stopChargePhoto(){
		if(task!=null){
			if(task.getStatus()==Status.RUNNING){
				task.cancel(true);
			}
			task=null;
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

	public String getHostPhoto() {
		return hostPhoto;
	}
	
	
	
}
