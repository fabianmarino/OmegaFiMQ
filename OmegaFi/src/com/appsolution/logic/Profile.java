package com.appsolution.logic;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Profile {

	private String firstName;
	private String middleName;
	private String lastName;
	private String informalFirstName;
	private String parentsName;
	private String travelVisaNumber;
	private String[] phones=new String[2];
	private String[] emails=new String[2];
	private String[] addresses=new String[2];
	private String dateInitiate;
	private int inititeYear;
	private int graduationYear;
	private String source;
	private String urlPhoto;
	private String prefix;
	private String suffix;
	private String gender;
	private String dateCollegeEntry;
	private boolean publishProfile;
	private List<String> prefixes=new ArrayList<String>();
	
	
	public Profile(JSONObject objectprofile,JSONArray phones, JSONArray emails, JSONArray address) {
		JSONObject individual;
		try {
			individual = objectprofile.getJSONObject("individual");
			firstName=individual.getString("first_name");
			if(!individual.isNull("middle_name"))
				middleName=individual.getString("middle_name");
			lastName=individual.getString("last_name");
			if(!individual.isNull("informal_first_name"))
				informalFirstName=individual.getString("informal_first_name");
			if(!individual.isNull("parents_name"))
				parentsName=individual.getString("parents_name");
			if(!individual.isNull("travel_visa_number"))
				travelVisaNumber=individual.getString("travel_visa_number");
			if(!individual.isNull("initiation_date"))
				dateInitiate=individual.getString("initiation_date");
			if(!individual.isNull("initiation_year"))
				inititeYear=individual.getInt("initiation_year");
			if(!individual.isNull("graduation_year"))
				graduationYear=individual.getInt("graduation_year");
			if(!individual.isNull("profile_picture")){
				JSONObject jsonPicture=individual.getJSONObject("profile_picture");
				source=jsonPicture.getString("source");
				urlPhoto=jsonPicture.getString("url");
			}
			if(!individual.isNull("prefix"))
				prefix=individual.getString("prefix");
			if(!individual.isNull("suffix"))
				suffix=individual.getString("suffix");
			if(!individual.isNull("gender"))
				gender=individual.getString("gender");
			if(!individual.isNull("date_of_college_entry"))
				dateCollegeEntry=individual.getString("date_of_college_entry");
			
			publishProfile=individual.getBoolean("publish_profile");
			
			this.completePhones(phones);
			this.completeEmails(emails);
			this.completeAdresses(address);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void completePhones(JSONArray phones){
		for (int i = 0; i < phones.length()&&i<2; i++) {
			try {
				JSONObject jsonPhone=phones.getJSONObject(i).getJSONObject("phone_number");
				this.phones[i]=jsonPhone.getString("phone_number");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void completeEmails(JSONArray emails){
		for (int i = 0; i < emails.length()&&i<2; i++) {
			try {
				JSONObject jsonEmail=emails.getJSONObject(i).getJSONObject("email");
				this.emails[i]=jsonEmail.getString("email_address");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void completeAdresses(JSONArray addresses){
		for (int i = 0; i < addresses.length()&&i<2; i++) {
			try {
				JSONObject jsonAddress=addresses.getJSONObject(i).getJSONObject("address");
				this.addresses[i]=jsonAddress.getString("line_1")+"¿"+jsonAddress.getString("line_2");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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

	public String getInformalFirstName() {
		return informalFirstName;
	}

	public String getParentsName() {
		return parentsName;
	}

	public String getTravelVisaNumber() {
		return travelVisaNumber;
	}

	public String[] getPhones() {
		return phones;
	}

	public String[] getEmails() {
		return emails;
	}

	public String[] getAddresses() {
		return addresses;
	}

	public String getDateInitiate() {
		return dateInitiate;
	}
	
	public String getDateInitiatePretty() {
		String dateInit=null;
		if(dateInitiate!=null){
			if(dateInitiate.length()>=10){
				dateInit=CalendarEvent.getFormatDate(3, dateInitiate.substring(0, 10), "yyyy/MM/dd");
			}
		}
		return dateInit;
	}
	
	public int getInititeYear() {
		return inititeYear;
	}

	public int getGraduationYear() {
		return graduationYear;
	}

	public String getSource() {
		return source;
	}

	public String getUrlPhoto() {
		return urlPhoto;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public String getGender() {
		return gender;
	}

	public String getDateCollegeEntry() {
		return dateCollegeEntry;
	}
	
	public String getDateCollegeEntryPretty() {
		String datePretty=null;
		if(dateCollegeEntry!=null){
			if(dateCollegeEntry.length()>=10){
				datePretty=CalendarEvent.getFormatDate(3, dateCollegeEntry.substring(0, 10), "yyyy/MM/dd");
			}
		}
		return datePretty;
	}
	
	public String getFirstLastName(){
		return firstName+" "+lastName;
	}
	
	public String getFirstMiddleLastName(){
		return firstName+" "+middleName+" "+lastName;
	}

	public boolean isPublishProfile() {
		return publishProfile;
	}
	
	
	
	

}
