package com.appsolution.logic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AccountsService extends ServerContext {

	private JSONObject jsonAccounts;
	
	public AccountsService(Server server) {
		super(server);
		jsonAccounts=null;
	}
	
	public JSONObject getJsonAccounts() {
		return jsonAccounts;
	}

	public Object[] chargeAccounts(){
		Object[] json=server.makeRequestGet(Server.ACCOUNTS_SERVICE);
		jsonAccounts=(JSONObject)json[1];
		return json;
	}
	
	public JSONArray getAccountsArray(){
		JSONArray array=null;
		if(jsonAccounts!=null){
			try {
				array = jsonAccounts.getJSONArray("accounts");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return array;
	}
	
	public Object[] getAccountSelected(int id){
		Account accountSelected=null;
		Object[] json=server.makeRequestGet(Server.ACCOUNTS_SERVICE+"/"+id);
		return json;
	}
	

}