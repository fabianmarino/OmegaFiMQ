package com.appsolution.services;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.appsolution.logic.Officer;
import com.appsolution.omegafi.OmegaFiActivity;

public class OfficersService extends ServerContext {

	private ArrayList<Officer> listOfficers;
	
	public OfficersService(Server server) {
		super(server);
		listOfficers=new ArrayList<Officer>();
	}
	
	public Object chargeOfficers(int idChapter){
		Object[] response=null;
		if(idChapter!=-1){
			response=server.makeRequestGetJSONArray(server.getUrlOfficers(idChapter));
			JSONArray object=(JSONArray)response[1];
			if(object!=null){
				if(object.length()!=0){
					try {
						for (int i = 0; i < object.length(); i++) {
							JSONObject officer=object.getJSONObject(i).getJSONObject("office_member");
							listOfficers.add(new Officer(officer));
						}
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return response;
	}
	
	public void chargeOfficers(Context context){
			JSONArray object=null;
			try {
				object = new JSONArray(OmegaFiActivity.getStringFile(context, "txt/officers.json"));
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(object!=null){
				if(object.length()!=0){
					try {
						for (int i = 0; i < object.length(); i++) {
							JSONObject officer=object.getJSONObject(i).getJSONObject("office_member");
							listOfficers.add(new Officer(officer));
						}
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
	}

	public ArrayList<Officer> getListOfficers() {
		return listOfficers;
	}
	
	public boolean isEmpty(){
		return listOfficers.isEmpty();
	}
	
	public void stopChargeOfficers(){
		for (Officer officer:listOfficers) {
			officer.stopChargePhoto();
		}
	}
	
	
	

}
