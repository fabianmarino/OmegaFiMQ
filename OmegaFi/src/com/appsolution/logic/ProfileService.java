package com.appsolution.logic;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ProfileService extends ServerContext{
	
	private JSONObject profile;
	private ArrayList<String> notifications=new ArrayList<String>();
	
	public ProfileService(Server server){
		super(server);
		profile=null;
	}
	
	public Object[] chargeProfileData(){
		Object[] response=server.makeRequestGet(Server.PROFILE_SERVICE);
		profile=(JSONObject)response[1];
		chargeNotifications();
		return response;
	}
	
	public String getUrlPhotoProfile(){
		String url=null;
		try {

			if(profile!=null){
				if(!profile.getJSONObject("individual").isNull("profile_picture")){
					url=profile.getJSONObject("individual").getJSONObject("profile_picture").getString("url");
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}
	
	public String getCompleteName(){
		String name="";
		try {
			if(profile!=null){
				name=profile.getJSONObject("individual").getString("first_name")+" "+
						profile.getJSONObject("individual").getString("last_name");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name;
	}
	
	public int getAnnouncementsCount(){
		int number=0;
		try {
			if(profile!=null){
				number= profile.getJSONObject("individual").getInt("announcement_count");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return number;
	}
	
	public Object[] getStatusProfile(){
		Object[] statusJsonProfile=server.makeRequestGet(Server.PROFILE_SERVICE);
		Profile myProfile=new Profile((JSONObject)statusJsonProfile[1]);
		Object[] statusProfile=new Object[2];
		statusProfile[0]=statusJsonProfile[0];
		statusProfile[1]=myProfile;
		return statusProfile;
	}
	
	public List<String> getPrefixesGender(String gender){
		List<String> prexifex=new ArrayList<String>();
		JSONArray arrayPrefixes=null;
		if(gender==null){
			arrayPrefixes=(JSONArray)server.makeRequestGetJSONArray(Server.PREFIXES_ALL)[1];
		}
		else{
			if(gender.equalsIgnoreCase("m")){
				arrayPrefixes=(JSONArray)server.makeRequestGetJSONArray(Server.PREFIXES_MALE)[1];
			}
			else{
				arrayPrefixes=(JSONArray)server.makeRequestGetJSONArray(Server.PREFIXES_FEMALE)[1];
			}
		}
		if(arrayPrefixes!=null){
			try {
				for (int i = 0; i < arrayPrefixes.length(); i++) {
						prexifex.add(arrayPrefixes.getJSONObject(i).getString("prefix"));	
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return prexifex;
	}
	
	public void chargeNotifications(){
		notifications.clear();
		Object[] statusJson=server.makeRequestGet(Server.NOTIFICATIONS_SERVICE);
		try {
			if(((JSONObject)statusJson[1])!=null){
				JSONArray jsonArray=((JSONObject)statusJson[1]).getJSONArray("profile_notifications");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonNoti=jsonArray.getJSONObject(i);
					notifications.add(jsonNoti.getString("notification"));
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public JSONObject getProfile() {
		return profile;
	}

	public ArrayList<String> getNotifications() {
		return notifications;
	}
	
	public Object[] updateProfileBasic(String firstName, String lastName, String middleName, String prefix, String sufix, String informalFirstName,
			String parentsName, int graduationYear, String travelVisaNumber, String dateCollege){
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("profile[first_name]", firstName));
		nameValuePairs.add(new BasicNameValuePair("profile[last_name]", lastName));
		nameValuePairs.add(new BasicNameValuePair("profile[middle_name]", middleName));
		nameValuePairs.add(new BasicNameValuePair("profile[prefix]", prefix));
		nameValuePairs.add(new BasicNameValuePair("profile[suffix]", sufix));
		nameValuePairs.add(new BasicNameValuePair("profile[informal_first_name]", informalFirstName));
		nameValuePairs.add(new BasicNameValuePair("profile[parents_name]", parentsName));
		if(graduationYear!=-1)
			nameValuePairs.add(new BasicNameValuePair("profile[graduation_year]", graduationYear+""));
		nameValuePairs.add(new BasicNameValuePair("profile[travel_visa_number]", travelVisaNumber));
		if(dateCollege!=null)
			nameValuePairs.add(new BasicNameValuePair("profile[date_of_college_entry]", dateCollege));
		
		Object[] statusJson=server.makeRequestPut(Server.PROFILE_SERVICE,nameValuePairs);
		return statusJson;
	}
	
	
	

}