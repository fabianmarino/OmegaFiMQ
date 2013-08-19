package com.appsolution.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.appsolution.logic.Account;
import com.appsolution.logic.AutoPayConfig;
import com.appsolution.logic.PaymentMethod;
import com.appsolution.logic.SimpleScheduledPayment;

import android.util.Log;

public class AccountsService extends ServerContext {

	private ArrayList<Account> accounts;
	private SimpleScheduledPayment selected=null;
	
	public AccountsService(Server server) {
		super(server);
		accounts=new ArrayList<Account>();
	}

	
	public Object[] chargeAccounts(){
		Object[] json=server.makeRequestGet(Server.ACCOUNTS_SERVICE);
		JSONObject jsonAccounts=(JSONObject)json[1];
		try {
			if(jsonAccounts!=null){
					JSONArray arrayAccounts=jsonAccounts.getJSONArray("accounts");
					accounts.clear();
					for (int i = 0; i < arrayAccounts.length(); i++) {
						accounts.add(new Account(arrayAccounts.getJSONObject(i).getJSONObject("account")));
					}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object[] statusAccounts=new Object[2];
		statusAccounts[0]=json[0];
		statusAccounts[1]=accounts;
		return statusAccounts;
	}
	
	public Object[] getStatusAccount(int id){
		Account actual=null;
		Object[] json=server.makeRequestGet(Server.ACCOUNTS_SERVICE+"/"+id);
		try {
			if(json[1]!=null){
				JSONObject accountJson=(JSONObject)json[1];
				actual=new Account(accountJson.getJSONObject("account"));	
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object[] statusAccount=new Object[2];
		statusAccount[0]=json[0];
		statusAccount[1]=actual;
		return statusAccount;
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
			int month, int year, String emailAddress, int zipCode, String phone, boolean saveForFuture){
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("credit_card_profile[nameoncard]", nameCC));
        nameValuePairs.add(new BasicNameValuePair("credit_card_profile[cardnumber]", creditNumber));
        nameValuePairs.add(new BasicNameValuePair("credit_card_profile[cardtype]", typeCard));
        nameValuePairs.add(new BasicNameValuePair("credit_card_profile[expmonth]", month+""));
        nameValuePairs.add(new BasicNameValuePair("credit_card_profile[expyear]", year+""));
        nameValuePairs.add(new BasicNameValuePair("credit_card_profile[emailaddress]", emailAddress));
        nameValuePairs.add(new BasicNameValuePair("credit_card_profile[zipcode]", zipCode+""));
        nameValuePairs.add(new BasicNameValuePair("credit_card_profile[transactionphone]", phone));
        int booleanValue=saveForFuture ? 1 : 0;
        nameValuePairs.add(new BasicNameValuePair("credit_card_profile[profile]", booleanValue+""));
		Object[] statusJson=server.makeRequestPost(Server.getUrlPaymentMethods(idAccount), nameValuePairs);
		return statusJson;
	}
	
	//Falta lo de las dos lineas de la direccion al servicio web solo hay que enviarle un string
	public Object[] createPaymentECheck(int idAccount, String nameAccount, String routingNumber, String accountNumber, 
			String emailAddress, String phone, String addressLine1, String addressLine2, String city, String state, int zipCode, boolean saveForFuture){
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("echeck_profile[routingnumber]", routingNumber));
        nameValuePairs.add(new BasicNameValuePair("echeck_profile[accountnumber]", accountNumber));
        nameValuePairs.add(new BasicNameValuePair("echeck_profile[nameonaccount]", nameAccount));
        nameValuePairs.add(new BasicNameValuePair("echeck_profile[emailaddress]", emailAddress));
        nameValuePairs.add(new BasicNameValuePair("echeck_profile[phonenumber]", phone));
        nameValuePairs.add(new BasicNameValuePair("echeck_profile[address1]", addressLine1));
        if(!addressLine2.isEmpty())
        	nameValuePairs.add(new BasicNameValuePair("echeck_profile[address2]", addressLine2));
        nameValuePairs.add(new BasicNameValuePair("echeck_profile[city]", city));
        nameValuePairs.add(new BasicNameValuePair("echeck_profile[state]", state));
        nameValuePairs.add(new BasicNameValuePair("echeck_profile[zipcode]", zipCode+""));
        int booleanValue=saveForFuture ? 1 : 0;
        nameValuePairs.add(new BasicNameValuePair("echeck_profile[profile]", booleanValue+""));
		Object[] statusJson=server.makeRequestPost(Server.getUrlPaymentMethods(idAccount), nameValuePairs);
		return statusJson;
	}
	
	public Object[] getScheduledPayments(int idAccount){
		ArrayList<SimpleScheduledPayment> scheduleds=new ArrayList<SimpleScheduledPayment>();
		Object[] statusJson=server.makeRequestGet(Server.getUrlScheduledPayments(idAccount));
		JSONObject scheduledJson=(JSONObject)statusJson[1];
		try {
			JSONArray arrayScheduled=scheduledJson.getJSONArray("scheduled_payments");
			for (int i = 0; i < arrayScheduled.length(); i++) {
				JSONObject jsonScheduled=arrayScheduled.getJSONObject(i);
				SimpleScheduledPayment scheduled=new SimpleScheduledPayment(jsonScheduled,SimpleScheduledPayment.STATE_PENDING);
				scheduleds.add(scheduled);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object[] statusScheduleds=new Object[2];
		statusScheduleds[0]=statusJson[0];
		statusScheduleds[1]=scheduleds;
		return statusScheduleds;
	}
	
	public Object[] getProcessingPayments(int idAccount){
		ArrayList<SimpleScheduledPayment> scheduleds=new ArrayList<SimpleScheduledPayment>();
		Object[] statusJson=server.makeRequestGet(Server.getUrlProcesingPayments(idAccount));
		JSONObject scheduledJson=(JSONObject)statusJson[1];
		try {
			JSONArray arrayScheduled=scheduledJson.getJSONArray("processing_payments");
			for (int i = 0; i < arrayScheduled.length(); i++) {
				JSONObject jsonScheduled=arrayScheduled.getJSONObject(i);
				SimpleScheduledPayment scheduled=new SimpleScheduledPayment(jsonScheduled,SimpleScheduledPayment.STATE_PROCESSING);
				scheduleds.add(scheduled);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object[] statusScheduleds=new Object[2];
		statusScheduleds[0]=statusJson[0];
		statusScheduleds[1]=scheduleds;
		return statusScheduleds;
	}

	public SimpleScheduledPayment getSelected() {
		return selected;
	}

	public void setSelected(SimpleScheduledPayment selected) {
		this.selected = selected;
	}


	public ArrayList<Account> getAccounts() {
		return accounts;
	}
	
	public Object[] getStatusAutoPay(int idAccount){
		Object[] statusJson=server.makeRequestGet(Server.getUrlAutoPay(idAccount));
		AutoPayConfig config=null;
		if(statusJson[1]!=null){
			config=new AutoPayConfig((JSONObject)statusJson[1]);	
		}
		Object[] statusAutoPay=new Object[2];
		statusAutoPay[0]=statusJson[0];
		statusAutoPay[1]=config;
		return statusAutoPay;
	}
	
	public int removeAutoPaySettings(int idAccount,int idAutoPay){
		return server.makeRequestDelete(Server.getUrlAutoPayId(idAccount, idAutoPay));
	}
	
	public int removeScheduledPayments(int idAccount,int idScheduled){
		return server.makeRequestDelete(Server.getUrlScheduledPaymentsId(idAccount, idScheduled));
	}
	
	public Object[] createAutoPay(int idAccount, AutoPayConfig config){
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		if(config.getPaymentDayOfMonth()!=-1){
			nameValuePairs.add(new BasicNameValuePair("autopayrecord[payonduedate]", ""+1));
			Log.d("autopayrecord[payonduedate]", "1");
			nameValuePairs.add(new BasicNameValuePair("autopayrecord[dayofmonth]", ""+config.getPaymentDayOfMonth()));
			Log.d("autopayrecord[dayofmonth]", config.getPaymentDayOfMonth()+"");
        }
		else{
			nameValuePairs.add(new BasicNameValuePair("autopayrecord[payonduedate]", ""+0));
			Log.d("autopayrecord[payonduedate]", 0+"");
		}
        nameValuePairs.add(new BasicNameValuePair("autopayrecord[begindate]", config.getBeginDateRequest()));
        Log.d("autopayrecord[begindate]", config.getBeginDateRequest());
        if(config.getTypePaymenthAmount()==AutoPayConfig.PAY_AMOUNT_DUE){
        	nameValuePairs.add(new BasicNameValuePair("autopayrecord[paydueamount]", 1+""));
        	nameValuePairs.add(new BasicNameValuePair("autopayrecord[capamount]", config.getAmountEnterMax()+""));
        	Log.d("autopayrecord[paydueamount]", 1+"");
        	Log.d("autopayrecord[capamount]", config.getAmountEnterMax()+"");
        }
        else{
        	nameValuePairs.add(new BasicNameValuePair("autopayrecord[paydueamount]", 0+""));
        	nameValuePairs.add(new BasicNameValuePair("autopayrecord[paymentamount]", config.getAmountEnterMax()+""));
        	Log.d("autopayrecord[paydueamount]", 0+"");
        	Log.d("autopayrecord[paymentamount]", config.getAmountEnterMax()+"");
        }
        
        if(config.getEndDate()!=null){
        	nameValuePairs.add(new BasicNameValuePair("autopayrecord[enddate]", config.getEndDateRequest()));
        	Log.d("autopayrecord[enddate]", config.getEndDateRequest());
        }
        
        if(config.iseCheck()){
        	nameValuePairs.add(new BasicNameValuePair("autopayrecord[memberpaymentecheckid]", config.getIdCreditOrECheck()+""));
        	Log.d("autopayrecord[memberpaymentecheckid]", config.getIdCreditOrECheck()+"");
        }
        else{
        	nameValuePairs.add(new BasicNameValuePair("autopayrecord[memberpaymentcreditcardid]", config.getIdCreditOrECheck()+""));
        	Log.d("autopayrecord[memberpaymentcreditcardid]", config.getIdCreditOrECheck()+"");
        }
        
		Object[] statusJson=server.makeRequestPost(Server.getUrlAutoPay(idAccount), nameValuePairs);
		
		return statusJson;
	}
	
	public Object[] updateAutoPay(int idAccount, AutoPayConfig config){
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		if(config.getPaymentDayOfMonth()!=-1){
			nameValuePairs.add(new BasicNameValuePair("autopayrecord[payonduedate]", ""+1));
			Log.d("autopayrecord[payonduedate]", "1");
			nameValuePairs.add(new BasicNameValuePair("autopayrecord[dayofmonth]", ""+config.getPaymentDayOfMonth()));
			Log.d("autopayrecord[dayofmonth]", config.getPaymentDayOfMonth()+"");
        }
		else{
			nameValuePairs.add(new BasicNameValuePair("autopayrecord[payonduedate]", ""+0));
			Log.d("autopayrecord[payonduedate]", 0+"");
		}
        nameValuePairs.add(new BasicNameValuePair("autopayrecord[begindate]", config.getBeginDateRequest()));
        Log.d("autopayrecord[begindate]", config.getBeginDateRequest());
        if(config.getTypePaymenthAmount()==AutoPayConfig.PAY_AMOUNT_DUE){
        	nameValuePairs.add(new BasicNameValuePair("autopayrecord[paydueamount]", 1+""));
        	nameValuePairs.add(new BasicNameValuePair("autopayrecord[capamount]", config.getAmountEnterMax()+""));
        	Log.d("autopayrecord[paydueamount]", 1+"");
        	Log.d("autopayrecord[capamount]", config.getAmountEnterMax()+"");
        }
        else{
        	nameValuePairs.add(new BasicNameValuePair("autopayrecord[paydueamount]", 0+""));
        	nameValuePairs.add(new BasicNameValuePair("autopayrecord[paymentamount]", config.getAmountEnterMax()+""));
        	Log.d("autopayrecord[paydueamount]", 0+"");
        	Log.d("autopayrecord[paymentamount]", config.getAmountEnterMax()+"");
        }
        
        if(config.getEndDate()!=null){
        	nameValuePairs.add(new BasicNameValuePair("autopayrecord[enddate]", config.getEndDateRequest()));
        	Log.d("autopayrecord[enddate]", config.getEndDateRequest());
        }
        
        if(config.iseCheck()){
        	nameValuePairs.add(new BasicNameValuePair("autopayrecord[memberpaymentecheckid]", config.getIdCreditOrECheck()+""));
        	Log.d("autopayrecord[memberpaymentecheckid]", config.getIdCreditOrECheck()+"");
        }
        else{
        	nameValuePairs.add(new BasicNameValuePair("autopayrecord[memberpaymentcreditcardid]", config.getIdCreditOrECheck()+""));
        	Log.d("autopayrecord[memberpaymentcreditcardid]", config.getIdCreditOrECheck()+"");
        }
        
		Object[] statusJson=server.makeRequestPut(Server.getUrlAutoPayId(idAccount,config.getId()), nameValuePairs);
		return statusJson;
	}
	
	public Object[] updateScheduledPament(int idAccount, int idScheduled, String paymentAmount, String paymentDate, PaymentMethod method){
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("scheduledpaymentrecord[paymentamount]", paymentAmount));
		nameValuePairs.add(new BasicNameValuePair("scheduledpaymentrecord[paymentdate]", paymentDate));
		if(method.isEChecked()){
			nameValuePairs.add(new BasicNameValuePair("scheduledpaymentrecord[memberpaymentecheckid]", method.getId()+""));
		}
		else{
			nameValuePairs.add(new BasicNameValuePair("scheduledpaymentrecord[memberpaymentcreditcardid]", method.getId()+""));
		}
		Object[] statusJson=server.makeRequestPut(Server.getUrlScheduledPaymentsId(idAccount,idScheduled),nameValuePairs);
		return statusJson;
	}
	
	public Object[] sendOpenRequest(int account, String phoneNumber, String numberOrganization, String logAs, String email, int contactedBefore, String request){
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("task[priority]", "1"));
		nameValuePairs.add(new BasicNameValuePair("task[phonenumber]", phoneNumber));
		nameValuePairs.add(new BasicNameValuePair("task[openedby]", logAs));
		nameValuePairs.add(new BasicNameValuePair("task[organizationnumber]", numberOrganization));
		nameValuePairs.add(new BasicNameValuePair("task[email]", email));
		nameValuePairs.add(new BasicNameValuePair("task[contacted_before]", contactedBefore+""));
		nameValuePairs.add(new BasicNameValuePair("task[request]", request));
		Object[] statusJson=server.makeRequestPost(Server.getUrlCreateTask(account),nameValuePairs);
		return statusJson;
	}
	
	
	
	

}