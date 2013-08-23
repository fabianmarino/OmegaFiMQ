package com.appsolution.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.appsolution.logic.Poll;

public class PollsService extends ServerContext{

	private ArrayList<Poll> polls;
	
	public PollsService(Server server) {
		super(server);
		polls=new ArrayList<Poll>();
	}
	
	public Object[] chargePolls(){
		Object[] statusJson=server.makeRequestGetJSONArray(Server.POLLS_SERVICE);
			if(statusJson[1]!=null){
				JSONArray arrayPolls=(JSONArray)statusJson[1];
				try {
					polls.clear();
					for (int i = 0; i < arrayPolls.length(); i++) {	
							JSONObject jsonPoll=arrayPolls.getJSONObject(i).getJSONObject("poll");
							polls.add(new Poll(jsonPoll));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		return statusJson;
	}
	
	public Object[] voteInPollAnswer(int idPoll, int idAnswer){
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("poll[pollanswerid]", idAnswer+""));
		Object[] response=server.makeRequestPost(Server.getUrlVotePoll(idPoll), nameValuePairs);
		return response;
	}

	public ArrayList<Poll> getPolls() {
		return polls;
	}
	
	public boolean isEmpty(){
		return polls.isEmpty();
	}

}