package com.appsolution.logic;

import org.json.JSONException;
import org.json.JSONObject;

public class HistoryItem {

	private String description;
	private String dateTransaction;
	private String transactionAmount;
	
	
	public HistoryItem(JSONObject jsonHistory) {
		try {
			description=jsonHistory.getString("transaction_description");
			dateTransaction=jsonHistory.getString("posting_date");
			transactionAmount=jsonHistory.getString("transaction_amount");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public String getDescription() {
		return description;
	}


	public String getDateTransaction() {
		if(dateTransaction.length()>10){
			return CalendarEvent.getFormatDate(3, dateTransaction.substring(0, 10), "yyyy/MM/dd");
		}
		else{
			return null;
		}
	}


	public String getTransactionAmount() {
		return transactionAmount;
	}
	
	

}
