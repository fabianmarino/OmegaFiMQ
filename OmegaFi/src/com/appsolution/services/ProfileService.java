package com.appsolution.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.appsolution.logic.Profile;
import com.appsolution.logic.TypeFormContact;

import android.util.Log;

public class ProfileService extends ServerContext{
	
	private Profile profile;
	private ArrayList<String> notifications=new ArrayList<String>();
	
	public ProfileService(Server server){
		super(server);
		profile=null;
	}
	
	public Object[] chargeProfileData(){
		Object[] response=server.makeRequestGet(Server.PROFILE_SERVICE);
		profile=(Profile)this.getStatusProfile()[1];
		chargeNotifications();
		return response;
	}
	
	public Object[] updateProfileIfNecessary(){
		if(profile==null){
			return chargeProfileData();
		}
		else{
			return null;
		}
	}
	
	public Object[] getStatusProfile(){
		Object[] statusJsonProfile=server.makeRequestGet(Server.PROFILE_SERVICE);
		Profile myProfile=new Profile((JSONObject)statusJsonProfile[1]);
		if(myProfile!=null)
			profile=myProfile;
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

	public Profile getProfile() {
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
	
	public int deletePhoneNumber(int idPhone){
		int delete=server.makeRequestDelete(Server.PROFILE_PHONES+"/"+idPhone);
		Log.d("delete phone", delete+" status");
		return delete;
	}
	
	public int deleteEmail(int idEmail){
		int delete= server.makeRequestDelete(Server.PROFILE_EMAILS+"/"+idEmail);
		Log.d("delete email", delete+" status");
		return delete;
	}
	
	public int deleteAddress(int idAddress){
		int delete= server.makeRequestDelete(Server.PROFILE_ADDRESSES+"/"+idAddress);
		Log.d("delete address", delete+" status");
		return delete;
	}
	
	public Object[] updatePhoneNumber(int idPhone, String phone, boolean isPrimary){
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("phone_number[phone_number]", phone));
		int primary= isPrimary ? 1 : 0;
		nameValuePairs.add(new BasicNameValuePair("profile[last_name]", primary+""));
		Object[] statusJson=server.makeRequestPut(Server.PROFILE_PHONES+"/"+idPhone, nameValuePairs);
		Log.d("update phone", (Integer)statusJson[0]+" status");
		return statusJson;
	}
	
	public Object[] updateEmailAddress(int idEmail, String email, boolean isPrimary){
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("email[email_address]",email));
		int primary= isPrimary ? 1 : 0;
		nameValuePairs.add(new BasicNameValuePair("email[primary]", primary+""));
		Object[] statusJson=server.makeRequestPut(Server.PROFILE_EMAILS+"/"+idEmail, nameValuePairs);
		Log.d("update email", (Integer)statusJson[0]+" status");
		return statusJson;
	}
	
	public Object[] updateAddress(int idAddress, int typeId, String line1, String line2, boolean isPrimary){
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("address[address_type_id]", typeId+""));
		nameValuePairs.add(new BasicNameValuePair("address[address_line_1]", line1));
		nameValuePairs.add(new BasicNameValuePair("address[address_line_2]", line2));
		nameValuePairs.add(new BasicNameValuePair("address[city]", "city"));
		nameValuePairs.add(new BasicNameValuePair("address[state]", "state"));
		nameValuePairs.add(new BasicNameValuePair("address[zip_code]", "31111"));
		int primary= isPrimary ? 1 : 0;
		nameValuePairs.add(new BasicNameValuePair("address[default]", primary+""));
		nameValuePairs.add(new BasicNameValuePair("address[primary]", primary+""));
		Object[] statusJson=server.makeRequestPut(Server.PROFILE_ADDRESSES+"/"+idAddress, nameValuePairs);
		Log.d("update address", (Integer)statusJson[0]+" status");
		return statusJson;
	}
	
	public Object[] createPhoneNumber(int typeId, String phoneNumber, boolean isPrimary){
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("phone_number[phone_number_type_id]", typeId+""));
		nameValuePairs.add(new BasicNameValuePair("phone_number[phone_number]", phoneNumber));
		int primary= isPrimary ? 1 : 0;
		nameValuePairs.add(new BasicNameValuePair("phone_number[primary]", primary+""));
		nameValuePairs.add(new BasicNameValuePair("phone_number[country_code]", "1"));
		Object[] statusJson=server.makeRequestPost(Server.PROFILE_PHONES, nameValuePairs);
		Log.d("create phone", (Integer)statusJson[0]+" status");
		return statusJson;
	}
	
	public Object[] createEmail(int typeId, String email, boolean isPrimary){
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("email[email_address]", email));
		nameValuePairs.add(new BasicNameValuePair("email[email_address_type_id]", typeId+""));
		int primary= isPrimary ? 1 : 0;
		nameValuePairs.add(new BasicNameValuePair("email[primary]", primary+""));
		Object[] statusJson=server.makeRequestPost(Server.PROFILE_EMAILS, nameValuePairs);
		Log.d("create email", (Integer)statusJson[0]+" status");
		return statusJson;
	}
	
	public Object[] createAddress(int typeId, String line1, String line2, boolean isPrimary){
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("address[address_type_id]", typeId+""));
		nameValuePairs.add(new BasicNameValuePair("address[address_line_1]", line1));
		nameValuePairs.add(new BasicNameValuePair("address[address_line_2]", line2));
		nameValuePairs.add(new BasicNameValuePair("address[city]", "city"));
		nameValuePairs.add(new BasicNameValuePair("address[state]", "state"));
		nameValuePairs.add(new BasicNameValuePair("address[zip_code]", "31111"));
		int primary= isPrimary ? 1 : 0;
		nameValuePairs.add(new BasicNameValuePair("address[default]", primary+""));
		nameValuePairs.add(new BasicNameValuePair("address[primary]", primary+""));
		Object[] statusJson=server.makeRequestPost(Server.PROFILE_ADDRESSES, nameValuePairs);
		Log.d("create address", (Integer)statusJson[0]+" status");
		return statusJson;
	}
	
	public Object[] getTypePhones(){
		ArrayList<TypeFormContact> types=new ArrayList<TypeFormContact>();
		Object[] statusJson=server.makeRequestGetJSONArray(Server.TYPE_PHONES);
		int status=(Integer)statusJson[0];
		if(status==200){
			JSONArray phones=(JSONArray)statusJson[1];
			if(phones!=null){
				for (int i = 0; i < phones.length(); i++) {
					try {
						types.add(new TypeFormContact(phones.getJSONObject(i).getJSONObject("phone_number_type"), TypeFormContact.TYPE_PHONE));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		Object[] statusTypes=new Object[2];
		statusTypes[0]=status;
		statusTypes[1]=types;
		return statusTypes;
	}
	
	public Object[] getTypeEmails(){
		ArrayList<TypeFormContact> types=new ArrayList<TypeFormContact>();
		Object[] statusJson=server.makeRequestGetJSONArray(Server.TYPE_EMAILS);
		int status=(Integer)statusJson[0];
		if(status==200){
			JSONArray emails=(JSONArray)statusJson[1];
			if(emails!=null){
				for (int i = 0; i < emails.length(); i++) {
					try {
						types.add(new TypeFormContact(emails.getJSONObject(i).getJSONObject("email_address_type"), TypeFormContact.TYPE_EMAIL));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		Object[] statusTypes=new Object[2];
		statusTypes[0]=status;
		statusTypes[1]=types;
		return statusTypes;
	}
	
	public Object[] getTypeAddresses(){
		ArrayList<TypeFormContact> types=new ArrayList<TypeFormContact>();
		Object[] statusJson=server.makeRequestGetJSONArray(Server.TYPE_ADDRESSES);
		int status=(Integer)statusJson[0];
		if(status==200){
			JSONArray addresses=(JSONArray)statusJson[1];
			if(addresses!=null){
				for (int i = 0; i < addresses.length(); i++) {
					try {
						types.add(new TypeFormContact(addresses.getJSONObject(i).getJSONObject("address_type"), TypeFormContact.TYPE_ADDRESS));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		Object[] statusTypes=new Object[2];
		statusTypes[0]=status;
		statusTypes[1]=types;
		return statusTypes;
	}
	
	
	
	
	

}