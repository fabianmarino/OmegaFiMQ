package com.appsolution.logic;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.appsolution.omegafi.OmegaFiActivity;

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
	private Bitmap bitMap;
	private ImageView imagePhoto;
	AsyncTask<Void, Integer, Boolean> task;
	
	
	public Officer(JSONObject officerJSON) {
		try {
		if(officerJSON!=null){
			JSONObject individual = officerJSON.getJSONObject("individual");
			firstName=individual.getString("first_name");
			lastName=individual.getString("last_name");
			JSONObject type= officerJSON.getJSONObject("office_type");
			officeType=type.getString("office_name");
			JSONArray emails=individual.getJSONArray("emails");
			if(emails!=null){
				email=emails.getJSONObject(0).getJSONObject("email").getString("address");
			}
			JSONArray phones=individual.getJSONArray("phone_numbers");
			if(phones!=null){
				telephone=phones.getJSONObject(0).getJSONObject("phone_number").getString("number");
			}
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
	
	public void chargePhotoFromUrl(){
		if(task==null){
				task=new AsyncTask<Void, Integer, Boolean>(){
					Bitmap bitAux=null;
					@Override
					protected Boolean doInBackground(Void... params) {
						try {
							if(hostPhoto!=null){
								if(hostPhoto.equals("OmegaFi")){
									bitAux=OmegaFiActivity.servicesOmegaFi.downloadBitmap(urlPhoto);
								}
								else{
									bitAux=OmegaFiActivity.loadImageFromURL(urlPhoto);
								}
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return true;
					}
					
					@Override
					protected void onPostExecute(Boolean result) {
						bitMap=bitAux;
						Log.d("Bitmap", getCompleteName());
						if(bitMap!=null&&imagePhoto!=null){
							Log.d("En photo", getCompleteName());
							imagePhoto.setImageBitmap(bitMap);
							imagePhoto.refreshDrawableState();
						}
					}
					
				};
				task.execute();
			}
		}
	
	public void chargePhoto(){
		if(bitMap!=null&&imagePhoto!=null){
			imagePhoto.setImageBitmap(bitMap);
		}
		else{
			this.chargePhotoFromUrl();
		}
	}
	
	public void chargePhotoFromBitmap(){
		if(bitMap!=null&&imagePhoto!=null){
			imagePhoto.setImageBitmap(bitMap);
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

	public Bitmap getBitMap() {
		return bitMap;
	}

	public String getOfficeType() {
		return officeType;
	}

	public String getUrl() {
		return urlPhoto;
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

	public void setImagePhoto(ImageView imagePhoto) {
		this.imagePhoto = imagePhoto;
		if(bitMap!=null){
			this.imagePhoto.setImageBitmap(bitMap);
		}
	}
	
	public String getPhoneNormal(){
		return telephone.replace("-", "");
	}
	
}
