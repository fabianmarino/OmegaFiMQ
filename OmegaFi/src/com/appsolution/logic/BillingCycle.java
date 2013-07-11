package com.appsolution.logic;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BillingCycle {

	private int cycleNumber;
	private String totalAmount;
	private String dateBillOn;
	private String dateDueOn;
	private ArrayList<Charge> listCharges;
	
	public BillingCycle(JSONObject billing) {
		try {
			if(billing!=null){
					cycleNumber=billing.getInt("cycle_number");
					dateBillOn=billing.getString("bill_on");
					dateDueOn=billing.getString("due_on");
					totalAmount=billing.getString("total");
					listCharges=new ArrayList<Charge>();
					JSONArray jsonCharges=billing.getJSONArray("charges");
					for (int i = 0; i < jsonCharges.length(); i++) {
						JSONObject jsonCharge=jsonCharges.getJSONObject(i).getJSONObject("charge");
						Charge newCharge=new Charge(jsonCharge);
						listCharges.add(newCharge);
					}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getCycleNumber() {
		return cycleNumber;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public String getDateBillOn() {
		if(dateBillOn.length()>=10){
			return CalendarEvent.getFormatDate(3, dateBillOn.substring(0, 10), "yyyy/MM/dd");
		}
		else{
			return null;
		}
	}

	public String getDateDueOn() {
		if(dateDueOn.length()>=10){
			return CalendarEvent.getFormatDate(3, dateDueOn.substring(0, 10), "yyyy/MM/dd");
		}
		else{
			return null;
		}
	}

	public ArrayList<Charge> getListCharges() {
		return listCharges;
	}
	
	
	

}
