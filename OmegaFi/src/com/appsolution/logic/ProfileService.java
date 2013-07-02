package com.appsolution.logic;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ProfileService extends ServerContext{
	
	private JSONObject profile;
	
	public ProfileService(Server server){
		super(server);
		profile=null;
	}
	
	public Object[] chargeProfileData(){
		Object[] response=server.makeRequestGet(Server.PROFILE_SERVICE);
		profile=(JSONObject)response[1];
		return response;
	}
	
	public String getUrlPhotoProfile(){
		String url=null;
		try {
			Log.d("profile", profile+"");
			if(!profile.getJSONObject("individual").isNull("profile_picture")){
				url=profile.getJSONObject("individual").getJSONObject("profile_picture").getString("url");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}
	
	public String getCompleteName(){
		String name="First Last";
		try {
			name=profile.getJSONObject("individual").getString("first_name")+" "+
					profile.getJSONObject("individual").getString("last_name");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name;
	}
	
	public int getAnnouncementsCount(){
		int number=0;
		try {
			number= profile.getJSONObject("individual").getInt("announcement_count");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return number;
	}
	
	

}
