package com.appsolution.logic;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
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
		Object[] json=server.makeRequestGet(Server.ACCOUNTS_SERVICE+"/"+id);
		return json;
	}
	
	public int submitPayNow(int idAccount, String paymenthAmmount, String datePaymenth, String typePaymenth, int idPaymenth){
		int status=0;
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("scheduledpaymentrecord[paymentamount]",paymenthAmmount));
        nameValuePairs.add(new BasicNameValuePair("scheduledpaymentrecord[paymentdate]", datePaymenth));
        if(typePaymenth.equalsIgnoreCase("checking")){
        	nameValuePairs.add(new BasicNameValuePair("scheduledpaymentrecord[memberpaymentecheckid]", idPaymenth+""));
        }
        else{
        	nameValuePairs.add(new BasicNameValuePair("scheduledpaymentrecord[memberpaymentcreditcardid]", idPaymenth+""));
        }
		status =(Integer)server.makeRequestPost(Server.getUrlScheduledPaymentsCreate(idAccount), nameValuePairs)[0];
		return status;
	}
	
	public Object[] createPaymentCC(int idAccount,String nameCC, String creditNumber, String typeCard ,
			int month, int year, String emailAddress, int zipCode, String phone){
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("credit_card_profile[nameoncard]", nameCC));
        nameValuePairs.add(new BasicNameValuePair("credit_card_profile[cardnumber]", creditNumber));
        nameValuePairs.add(new BasicNameValuePair("credit_card_profile[cardtype]", typeCard));
        nameValuePairs.add(new BasicNameValuePair("credit_card_profile[expmonth]", month+""));
        nameValuePairs.add(new BasicNameValuePair("credit_card_profile[expyear]", year+""));
        nameValuePairs.add(new BasicNameValuePair("credit_card_profile[emailaddress]", emailAddress));
        nameValuePairs.add(new BasicNameValuePair("credit_card_profile[zipcode]", zipCode+""));
        nameValuePairs.add(new BasicNameValuePair("credit_card_profile[transactionphone]", phone));
		Object[] statusJson=server.makeRequestPost(Server.getUrlPaymentMethods(idAccount), nameValuePairs);
		return statusJson;
	}
	
	//Falta lo de las dos lineas de la direccion al servicio web solo hay que enviarle un string
	public Object[] createPaymentECheck(int idAccount, String nameAccount, String routingNumber, String accountNumber, 
			String emailAddress, String phone, String addressLine1, String addressLine2, String city, String state, int zipCode){
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("echeck_profile[routingnumber]", routingNumber));
        nameValuePairs.add(new BasicNameValuePair("echeck_profile[accountnumber]", accountNumber));
        nameValuePairs.add(new BasicNameValuePair("echeck_profile[nameonaccount]", nameAccount));
        nameValuePairs.add(new BasicNameValuePair("echeck_profile[emailaddress]", emailAddress));
        nameValuePairs.add(new BasicNameValuePair("echeck_profile[phonenumber]", phone));
        nameValuePairs.add(new BasicNameValuePair("echeck_profile[address1]", addressLine1));
        nameValuePairs.add(new BasicNameValuePair("echeck_profile[city]", city));
        nameValuePairs.add(new BasicNameValuePair("echeck_profile[state]", state));
        nameValuePairs.add(new BasicNameValuePair("echeck_profile[zipcode]", zipCode+""));
		Object[] statusJson=server.makeRequestPost(Server.getUrlPaymentMethods(idAccount), nameValuePairs);
		return statusJson;
	}

}