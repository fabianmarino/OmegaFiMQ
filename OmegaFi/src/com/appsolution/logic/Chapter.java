package com.appsolution.logic;

import org.json.JSONException;
import org.json.JSONObject;

public class Chapter {

	private int id;
	private String designation;
	private String university;
	private boolean canSeeMembers=false;
	
	
	public Chapter(JSONObject jsonChapter) {
		try {
			if(jsonChapter!=null){
				id=jsonChapter.getInt("chapter_id");	
				designation=jsonChapter.getString("designation");
				university=jsonChapter.getString("university_name");
				canSeeMembers=jsonChapter.getBoolean("can_see_chapter_membership");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getId() {
		return id;
	}

	public String getDesignation() {
		return designation;
	}

	public String getUniversity() {
		return university;
	}
	
	public String getDesignUniversity(){
		return designation+","+university;
	}

	public boolean isCanSeeMembers() {
		return canSeeMembers;
	}
	
}