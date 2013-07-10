package com.appsolution.logic;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class HomeServices extends ServerContext{

	private ProfileService profile;
	private AccountsService accounts;
	private ChaptersService chapters;
	private OfficersService officers;
	private CalendarService calendar;
	private NewsFeedService feeds;
	
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
	
	
	
	
	
	
}
