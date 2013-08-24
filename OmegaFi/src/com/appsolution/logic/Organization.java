package com.appsolution.logic;

import org.json.JSONException;
import org.json.JSONObject;

public class Organization {
	
	private String national;
	private String chapterDesignation;
	private String university;
	
	public Organization(JSONObject jsonOrganization){
		if(jsonOrganization!=null){
			try {
				national=jsonOrganization.getString("national");
				chapterDesignation=jsonOrganization.getString("chapter_designation");
				university=jsonOrganization.getString("university");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	public String getNational() {
		return national;
	}

	public String getChapterDesignation() {
		return chapterDesignation;
	}

	public String getUniversity() {
		return university;
	}
	
	

}
