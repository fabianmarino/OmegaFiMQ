package com.appsolution.logic;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ChaptersService extends ServerContext {

	private JSONArray jsonChapters;
	
	public ChaptersService(Server server) {
		super(server);
	}
	
	public Object[] chargeChapters(){
		Object[] json=server.makeRequestGetJSONArray(Server.CHAPTERS_SERVICE);
		jsonChapters=(JSONArray)json[1];
		return json;
	}
	
	public ArrayList<String> getChapterNames(){
		ArrayList< String> chapters=new ArrayList<String>();
		try {
			for (int i = 0; i < jsonChapters.length(); i++) {
				JSONObject objectJSON=jsonChapters.getJSONObject(i).getJSONObject("chapter");
				chapters.add(objectJSON.getString("designation")+","+objectJSON.getString("university_name"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return chapters;
	}
	

	public int getIdChapter(int index){
		int id=-1;
		if(jsonChapters!=null){
			try {
				JSONObject account=jsonChapters.getJSONObject(index).getJSONObject("chapter");
				id=account.getInt("chapter_id");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return id;
	}
	
	public boolean isEmpty(){
		return jsonChapters.length()==0;
	}


}
