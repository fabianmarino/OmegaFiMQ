package com.appsolution.logic;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.appsolution.services.ForgotLoginService;

public class Account {

	private int id;
	private String firstName;
	private String lastName;
	private String memberStatus;
	private boolean autoPay=false;
	private String nameOrg;
	private String designationOrg;
	private String university;
	private String currentBalance;
	private String dueOn;
	private String adjustedBalance;
	
	private String creditsLast;
	private String paymentsLast;
	private String activityLast;
	private ArrayList<String> listNotifications=new ArrayList<String>();
	
	private String sourcePhoto;
	private String urlPhotoAccount;
	
	private String dateBalanceAsOf;
	private String moneyBalanceAsOf;
	
	private ArrayList<ContactAccount> contacts=new ArrayList<ContactAccount>();
	
	public Account(JSONObject jsonAccount) {
		try {
			id=jsonAccount.getInt("member_id");
			firstName=jsonAccount.getString("first_name");
			lastName=jsonAccount.getString("last_name");
			memberStatus=jsonAccount.getString("member_status");
			String autoPayAux=jsonAccount.getString("auto_pay");
			if(!autoPayAux.equalsIgnoreCase("inactive")){
				autoPay=true;
			}
			JSONObject objectOrg=jsonAccount.getJSONObject("organization");
			nameOrg=objectOrg.getString("name");
			designationOrg=objectOrg.getString("designation");
			university=objectOrg.getString("university");
			currentBalance=jsonAccount.getString("current_balance");
			adjustedBalance=jsonAccount.getString("adjusted_balance");
			creditsLast=jsonAccount.getString("credits_since_last_statement");
			paymentsLast=jsonAccount.getString("payments_since_last_statement");
			activityLast=jsonAccount.getString("activity_since_last_statement");
			
			contacts.clear();
			if(!jsonAccount.isNull("organization")){
				JSONObject regularContact=jsonAccount.getJSONObject("organization");
				contacts.add(new ContactAccount(regularContact, true,ContactAccount.TYPE_ORGANIZATION));
			}
			
			
			if(!jsonAccount.isNull("omegafi_contact")){
				JSONObject omegafiContact=jsonAccount.getJSONObject("omegafi_contact");
				contacts.add(new ContactAccount(omegafiContact, true,ContactAccount.TYPE_OMEGAFI));
			}
			
			JSONObject  lastestStatement= !jsonAccount.isNull("latest_statement") ? jsonAccount.getJSONObject("latest_statement") : null;
			if(lastestStatement!=null){
				dueOn=lastestStatement.getString("due_on");
				dateBalanceAsOf=lastestStatement.getString("cycle_on");
				moneyBalanceAsOf=lastestStatement.getString("current_balance");
			}
			JSONArray arrayNotifications=jsonAccount.getJSONArray("account_notifications");
			for (int i = 0; i < arrayNotifications.length(); i++) {
				JSONObject objectNotification=arrayNotifications.getJSONObject(i).getJSONObject("account_notification");
				listNotifications.add(objectNotification.getString("notification"));
			}
			
			if(!jsonAccount.isNull("profile_picture")){
				JSONObject profilePhoto=jsonAccount.getJSONObject("profile_picture");
				sourcePhoto=profilePhoto.getString("source");
				urlPhotoAccount=profilePhoto.getString("url");
			}	
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getMemberStatus() {
		return memberStatus;
	}


	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}


	public boolean isAutoPay() {
		return autoPay;
	}


	public void setAutoPay(boolean autoPay) {
		this.autoPay = autoPay;
	}


	public String getNameOrg() {
		return nameOrg;
	}


	public void setNameOrg(String nameOrg) {
		this.nameOrg = nameOrg;
	}


	public String getDesignationOrg() {
		return designationOrg;
	}


	public void setDesignationOrg(String designationOrg) {
		this.designationOrg = designationOrg;
	}


	public String getUniversity() {
		return university;
	}


	public void setUniversity(String university) {
		this.university = university;
	}


	public String getCurrentBalance() {
		String currentBal="$"+currentBalance;
		if(currentBalance!=null){
			currentBal=currentBal.contains("-") ? currentBal.replace("-", "(")+")": currentBal;
		}
		return currentBal;
	}


	public void setCurrentBalance(String currentBalance) {
		this.currentBalance = currentBalance;
	}


	public String getDueOn() {
		if(dueOn!=null){
			if(dueOn.length()>=10){
				return CalendarEvent.getFormatDate(3, dueOn.substring(0, 10), "yyyy-MM-dd");
			}
			else{
				return null;
			}
		}
		else{
			return null;
		}
	}


	public void setDueOn(String dueOn) {
		this.dueOn = dueOn;
	}


	public String getAdjustedBalance() {
		String adjusted="$"+adjustedBalance;
		if(adjustedBalance!=null){
			adjusted=adjusted.contains("-") ? adjusted.replace("-", "(")+")": adjusted;
		}
		return adjusted;
	}


	public void setAdjustedBalance(String adjustedBalance) {
		this.adjustedBalance = adjustedBalance;
	}


	public String getCreditsLast() {
		String credits ="$"+ creditsLast;
		if(creditsLast!=null){
			credits=credits.contains("-") ? credits.replace("-", "(") +")": credits;
		}
		return credits;
	}


	public void setCreditsLast(String creditsLast) {
		this.creditsLast = creditsLast;
	}


	public String getPaymentsLast() {
		String payment="$"+paymentsLast;
		if(paymentsLast!=null){
			payment=payment.contains("-") ? payment.replace("-", "(")+")":payment;
		}
		return payment;
	}


	public void setPaymentsLast(String paymentsLast) {
		this.paymentsLast = paymentsLast;
	}


	public String getActivityLast() {
		String activLast="$"+activityLast;
		if(activityLast!=null){
			activLast=activLast.contains("-") ? activLast.replace("-", "(")+")":activLast;
		}
		return activLast;
	}


	public void setActivityLast(String activityLast) {
		this.activityLast = activityLast;
	}


	public ArrayList<String> getListNotifications() {
		return listNotifications;
	}


	public String getSourcePhoto() {
		return sourcePhoto;
	}


	public String getUrlPhotoAccount() {
		return urlPhotoAccount;
	}
	
	public String getCompleteName(){
		return firstName+" "+lastName;
	}
	
	public String getNameOrgDesignationOrg(){
		return nameOrg+" - "+designationOrg;
	}


	public String getDateBalanceAsOf() {
		String date=null;
		if(dateBalanceAsOf!=null){
			if(dateBalanceAsOf.length()>=10){
				date=CalendarEvent.getFormatDate(3, dateBalanceAsOf.substring(0, 10), "yyyy-MM-dd");
			}
		}
		return date;
	}


	public String getMoneyBalanceAsOf() {
		String money="$"+moneyBalanceAsOf;
		if(moneyBalanceAsOf!=null){
			money=money.contains("-") ? money.replace("-", "(")+")":money;
		}
		return money;
	}


	public ArrayList<ContactAccount> getContacts() {
		return contacts;
	}
	
}