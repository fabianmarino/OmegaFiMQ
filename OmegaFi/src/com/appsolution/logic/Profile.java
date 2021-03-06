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
	private PhoneContact[] phones=new PhoneContact[2];
	private EmailContact[] emails=new EmailContact[2];
	private AddressContact[] addresses=new AddressContact[2];
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
	private int announcementsCount=0;
	private boolean changesPending=false;
	private String statusName;
	private int idMemberStatus;
	private String nationalStatusName;
	private Organization organizationMember=null;
	
	public static final String NATIONAL_STATUS_NAME_NONE="None";
	
	public Profile(JSONObject objectprofile) {
		JSONObject individual;
		if(objectprofile!=null){
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
				if(!individual.isNull("announcement_count"))
					announcementsCount=individual.getInt("announcement_count");
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
				
				changesPending=individual.getBoolean("pending_change");
				publishProfile=individual.getBoolean("publish_profile");
				
				if(!individual.isNull("member_status")){
					JSONObject memberStatus=individual.getJSONObject("member_status");
					statusName=memberStatus.getString("status_name");
					idMemberStatus=memberStatus.getInt("member_status_id");
					nationalStatusName=memberStatus.getString("national_status_name");
				}
				if(!individual.isNull("organization")){
					JSONObject organization=individual.optJSONObject("organization");
					if(organization!=null){
						organizationMember=new Organization(organization);
					}
				}
				
				this.completePhones(individual.getJSONArray("phone_numbers"));
				this.completeEmails(individual.getJSONArray("emails"));
				this.completeAdresses(individual.getJSONArray("addresses"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void completePhones(JSONArray phones){
		for (int i = 0; i < phones.length(); i++) {
			try {
				JSONObject jsonPhone=phones.getJSONObject(i).getJSONObject("phone_number");
				PhoneContact phone=new PhoneContact(jsonPhone,ContactForm.TYPE_PROFILE);
				if(phone.isPrimary())
					this.phones[0]=phone;
				else if(this.phones[1]==null)
					this.phones[1]=phone;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void completeEmails(JSONArray emails){
		for (int i = 0; i < emails.length(); i++) {
			try {
				JSONObject jsonEmail=emails.getJSONObject(i).getJSONObject("email");
				EmailContact email=new EmailContact(jsonEmail,ContactForm.TYPE_PROFILE);
				if(email.isPrimary())
					this.emails[0]=email;
				else if(this.emails[1]==null)
					this.emails[1]=email;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void completeAdresses(JSONArray addresses){
		for (int i = 0; i < addresses.length(); i++) {
			try {
				JSONObject jsonAddress=addresses.getJSONObject(i).getJSONObject("address");
				AddressContact address=new AddressContact(jsonAddress);
				
				if(address.isPrimary())
					this.addresses[0]=address;
				else if(this.addresses[1]==null)
					this.addresses[1]=address;
				
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

	public PhoneContact[] getPhones() {
		return phones;
	}

	public EmailContact[] getEmails() {
		return emails;
	}

	public AddressContact[] getAddresses() {
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

	public List<String> getPrefixes() {
		return prefixes;
	}

	public int getAnnouncementsCount() {
		return announcementsCount;
	}

	public boolean isChangesPending() {
		return changesPending;
	}

	public String getStatusName() {
		return statusName;
	}

	public String getNationalStatusName() {
		return nationalStatusName;
	}

	public int getIdMemberStatus() {
		return idMemberStatus;
	}

	public Organization getOrganizationMember() {
		return organizationMember;
	}
	
	
	
	
	
	
	
}
