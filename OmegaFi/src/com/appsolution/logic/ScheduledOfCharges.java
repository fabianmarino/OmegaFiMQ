package com.appsolution.logic;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ScheduledOfCharges {

	private String interval;
	private String grandTotal;
	private ArrayList<BillingCycle> billingCycles;
	
	public ScheduledOfCharges(JSONObject jsonScheduledCharges) {
		try {
			if(jsonScheduledCharges!=null){
					interval=jsonScheduledCharges.getString("interval");
					grandTotal=jsonScheduledCharges.getString("grand_total");
					billingCycles=new ArrayList<BillingCycle>();
					
					JSONArray arrayCycles=jsonScheduledCharges.getJSONArray("billing_cycles");
					for (int i = 0; i < arrayCycles.length(); i++) {
						JSONObject cycle=arrayCycles.getJSONObject(i).getJSONObject("billing_cycle");
						billingCycles.add(new BillingCycle(cycle));
					}
					
					
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public String getInterval() {
		return interval;
	}

	public String getGrandTotal() {
		return grandTotal;
	}

	public ArrayList<BillingCycle> getBillingCycles() {
		return billingCycles;
	}
	
	

}
