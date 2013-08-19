package com.appsolution.logic;

import org.json.JSONException;
import org.json.JSONObject;

public class OfficerRooster extends MemberRooster{

	private String officeType;
	
	public OfficerRooster(JSONObject jsonMember) {
		super(jsonMember);
		try {
			if(jsonMember!=null){
				JSONObject officeType=jsonMember.getJSONObject("office_type");
				this.officeType=officeType.getString("office_name");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getOfficeType() {
		return officeType;
	}
	
}
