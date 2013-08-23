package com.appsolution.logic;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Poll {

	private int id;
	private int partyId;
	private String pollName;
	private String pollQuestion;
	private boolean voted;
	private ArrayList<AnswerPoll> answers=new ArrayList<AnswerPoll>();
	
	public Poll(JSONObject jsonPoll){
		try {
			if(jsonPoll!=null){	
					id=jsonPoll.getInt("poll_id");
					partyId=jsonPoll.getInt("owner_party_id");
					if(!jsonPoll.isNull("poll_name"))
						pollName=jsonPoll.getString("poll_name");
					if(!jsonPoll.isNull("poll_question"))
						pollQuestion=jsonPoll.getString("poll_question");
					voted=jsonPoll.getBoolean("voted");
					if(!jsonPoll.isNull("answers")){
						JSONArray arrayAnswers=jsonPoll.getJSONArray("answers");
						answers.clear();
						for (int i = 0; i < arrayAnswers.length(); i++) {
							JSONObject answerJson=arrayAnswers.getJSONObject(i).getJSONObject("answer");
							answers.add(new AnswerPoll(answerJson));
						}
					}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getId() {
		return id;
	}

	public int getPartyId() {
		return partyId;
	}

	public String getPollName() {
		return pollName;
	}

	public String getPollQuestion() {
		return pollQuestion;
	}

	public boolean isVoted() {
		return voted;
	}

	public ArrayList<AnswerPoll> getAnswers() {
		return answers;
	}
	
	public int votesTotal(){
		int sum=0;
		for (AnswerPoll answer:answers) {
			sum+=answer.getVotes();
		}
		return sum;
	}
	
	public double getPercentajeAnswer(int index){
		int votesTotal=votesTotal();
		double perc=votesTotal>0 ? (answers.get(index).getVotes()*100.0)/(float)votesTotal:0;
		return perc;
	}
	
	public ArrayList<String> getStringAnswers(){
		ArrayList<String> array=new ArrayList<String>();
		for (AnswerPoll answer:answers) {
			array.add(answer.getAnswer());
		}
		return array;
	}
	
	public void votingAnswer(int index){
		voted=true;
		answers.get(index).addVote();
		
	}
		
}