package com.appsolution.logic;

import org.json.JSONException;
import org.json.JSONObject;

public class AnswerPoll {
	
	private int id;
	private String answer;
	private int votes=0;
	
	public AnswerPoll(JSONObject jsonAnswer){
		try {
			if(jsonAnswer!=null){	
					id=jsonAnswer.getInt("poll_answer_id");
					if(!jsonAnswer.isNull("poll_answer"))
						answer=jsonAnswer.getString("poll_answer");
					votes=jsonAnswer.getInt("votes");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getId() {
		return id;
	}

	public String getAnswer() {
		return answer;
	}

	public int getVotes() {
		return votes;
	}
	
	public void addVote(){
		votes++;
	}
	
	

}
