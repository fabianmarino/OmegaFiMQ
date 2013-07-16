package com.appsolution.logic;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ChaptersService extends ServerContext {

	private ArrayList<Chapter> chapters;
	
	public ChaptersService(Server server) {
		super(server);
		chapters=new ArrayList<Chapter>();
	}
	
	public Object[] chargeChapters(){
		chapters.clear();
		Object[] json=server.makeRequestGetJSONArray(Server.CHAPTERS_SERVICE);
		if(json[1]!=null){
		JSONArray jsonChapters=(JSONArray)json[1];
		for (int i = 0; i < jsonChapters.length(); i++) {
			try {
				JSONObject chapterJson=jsonChapters.getJSONObject(i).getJSONObject("chapter");
				chapters.add(new Chapter(chapterJson));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
		return json;
	}
	
	public ArrayList<String> getChapterNames(){
		ArrayList< String> chapters=new ArrayList<String>();
			for (Chapter chapter:this.chapters) {
				chapters.add(chapter.getDesignUniversity());
			}
		return chapters;
	}

	public int getIdChapter(int index){
		int id=-1;
		if(!chapters.isEmpty()&&index<chapters.size()){
			id=chapters.get(index).getId();
		}
		return id;
	}
	
	public boolean isEmpty(){
		return chapters.isEmpty();
	}
	
	public Object[] getListSimpleMembers(int idChapter){
		Object[] statusJson=server.makeRequestGetJSONArray(Server.getUrlMemberRooster(idChapter));
		ArrayList<SimpleMember> members=new ArrayList<SimpleMember>();
		JSONArray jsonMembers=(JSONArray)statusJson[1];
		try {
			for (int i = 0; i < jsonMembers.length(); i++) {
					JSONObject jsonMember=jsonMembers.getJSONObject(i).getJSONObject("member");
					members.add(new SimpleMember(jsonMember));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Object[] statusSimple=new Object[2];
		statusSimple[0]=statusJson[0];
		statusSimple[1]=members;
		return statusSimple;
	}
	
	public static List<String> getNamesMembers(ArrayList<SimpleMember> members){
		List<String> listNames=new ArrayList<String>();
		for (SimpleMember auxMember:members) {
			listNames.add(auxMember.getLastFirstName()+"¿"+auxMember.getStatusName()+"¿"+auxMember.getSourcePhoto()+"¿"
					+auxMember.getUrlPhoto()+"¿"+auxMember.getId());
		}
		return listNames;
	}
	
	public Object[] getStatusMemberRooster(int idChapter, int idMember){
		Object[] statusJson=server.makeRequestGet(Server.getUrlMemberDetails(idChapter, idMember));
		MemberRooster rooster=null;
		try {
			if(statusJson[1]!=null){
				JSONObject jsonMember;
				jsonMember = ((JSONObject)statusJson[1]).getJSONObject("member");	
				rooster=new MemberRooster(jsonMember);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object[] statusMember=new Object[2];
		statusMember[0]=statusJson[0];
		statusMember[1]=rooster;
		return statusMember;
	}
	
}
