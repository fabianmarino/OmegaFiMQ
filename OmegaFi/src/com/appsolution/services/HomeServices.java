package com.appsolution.services;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.appsolution.logic.HistoryItem;
import com.appsolution.logic.PaymentMethod;
import com.appsolution.logic.ScheduledOfCharges;
import com.appsolution.logic.SimpleAnnouncement;
import com.appsolution.logic.Statement;

public class HomeServices extends ServerContext{

	private ProfileService profile;
	private AccountsService accounts;
	private ChaptersService chapters;
	private OfficersService officers;
	private CalendarService calendar;
	private NewsFeedService feeds;
	private SimpleAnnouncement announcementSelected;
	
	public HomeServices(Server server){
		super(server);
		profile=new ProfileService(server);
		accounts=new AccountsService(server);
		chapters=new ChaptersService(server);
		officers=new OfficersService(server);
		calendar=new CalendarService(server);
		feeds=new NewsFeedService(server);
	}
	
	public ProfileService getProfile() {
		return profile;
	}

	public AccountsService getAccounts() {
		return accounts;
	}

	public ChaptersService getChapters() {
		return chapters;
	}

	public OfficersService getOfficers() {
		return officers;
	}

	public CalendarService getCalendar() {
		return calendar;
	}

	public NewsFeedService getFeeds() {
		return feeds;
	}
	
	public void clearHomeServices(){
		officers.stopChargeOfficers();
		profile=new ProfileService(server);
		accounts=new AccountsService(server);
		chapters=new ChaptersService(server);
		officers=new OfficersService(server);
		calendar=new CalendarService(server);
		feeds=new NewsFeedService(server);
	}
	
	/**
	 * Return int status, ArrayList<HistoryItem>
	 * @param idAccount
	 * @return
	 */
	public Object[] getListHistory(int idAccount){
		Object[] jsonStatus=server.makeRequestGet(Server.getUrlHistory(idAccount));
		ArrayList<HistoryItem> historyItems=new ArrayList<HistoryItem>();
		JSONObject objectJson=(JSONObject)jsonStatus[1];
		try {
			JSONArray arrayTransactions=objectJson.getJSONObject("member").getJSONArray("posted_transactions");
			for (int i = 0; i < arrayTransactions.length(); i++) {
				historyItems.add(new HistoryItem(arrayTransactions.getJSONObject(i).getJSONObject("posted_transaction")));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object[] statusArray=new Object[2];
		statusArray[0]=jsonStatus[0];
		statusArray[1]=historyItems;
		return statusArray;
	}
	
	/**
	 * Return int status, ArrayList<Statements>
	 * @param idAccount
	 * @return
	 */
	public Object[] getListStatemets(int idAccount){
		Object[] jsonStatus=server.makeRequestGet(Server.getUrlStatements(idAccount));
		ArrayList<Statement> statements=new ArrayList<Statement>();
		JSONObject objectJson=(JSONObject)jsonStatus[1];
		try {
			JSONArray arrayTransactions=objectJson.getJSONArray("statements");
			for (int i = 0; i < arrayTransactions.length(); i++) {
				statements.add(new Statement(arrayTransactions.getJSONObject(i).getJSONObject("statement")));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Object[] statusArray=new Object[2];
		statusArray[0]=jsonStatus[0];
		statusArray[1]=statements;
		return statusArray;
	}
	/**
	 * 
	 * @param idAccount: Account of User
	 * @return: ScheduledOfCharges object, with information
	 */
	public Object[] getScheduledOfCharges(int idAccount){
		Object[] jsonStatus=server.makeRequestGet(Server.getUrlScheduledCharges(idAccount));
		ScheduledOfCharges scheduledCharges=null;
		if(jsonStatus[1]!=null){
			JSONObject statusSchedule=(JSONObject)jsonStatus[1];
			if(statusSchedule!=null){
				try {
					scheduledCharges=new ScheduledOfCharges(statusSchedule.getJSONObject("schedule_of_charges"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		Object[] statusScheduled=new Object[2];
		statusScheduled[0]=jsonStatus[0];
		statusScheduled[1]=scheduledCharges;
		return statusScheduled;
	}
	
	public Object[] getPaymentMethods(int idAccount){
		Object[] statusJson=server.makeRequestGet(Server.getUrlPaymentMethods(idAccount));
		ArrayList<PaymentMethod> methods = null;
		JSONArray arrayMethods=null;
		try {
			if(statusJson[1]!=null){
				methods=new ArrayList<PaymentMethod>();
				JSONObject jsonAux=(JSONObject)statusJson[1];
				arrayMethods = jsonAux.getJSONArray("payment_profiles");
				for (int i = 0; i < arrayMethods.length(); i++) {
					methods.add(new PaymentMethod(arrayMethods.getJSONObject(i).getJSONObject("payment_profile")));
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Object[] statusMethods=new Object[2];
		statusMethods[0]=statusJson[0];
		statusMethods[1]=methods;
		return statusMethods;
	}
	
	public Object[] getStatusArrayAnnouncements(){
		Object[] statusArray=server.makeRequestGetJSONArray(Server.ANNOUNCEMENTS_SERVICE);
		ArrayList<SimpleAnnouncement> announcements=new ArrayList<SimpleAnnouncement>();
		JSONArray jsonAnnouncements=(JSONArray)statusArray[1];
		try {
			if(jsonAnnouncements!=null){
				for (int i = 0; i < jsonAnnouncements.length(); i++) {		
						JSONObject objectAnnouncement=jsonAnnouncements.getJSONObject(i).getJSONObject("announcement");
						announcements.add(new SimpleAnnouncement(objectAnnouncement));
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object[] statusAnnouncements=new Object[2];
		statusAnnouncements[0]=statusArray[0];
		statusAnnouncements[1]=announcements;
		return statusAnnouncements;
	}

	public SimpleAnnouncement getAnnouncementSelected() {
		return announcementSelected;
	}

	public void setAnnouncementSelected(SimpleAnnouncement announcementSelected) {
		this.announcementSelected = announcementSelected;
	}
	
	public int updateAnnouncementsView(){
		Object[] statusJson=server.makeRequestGet(Server.ANNOUNCEMENTS_VIEW_UPDATE);
		int updated=(Integer)statusJson[0];
		Log.d("actualizando update view", updated+"");
		return updated;
	}
}
