package com.appsolution.logic;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ForgotLoginService extends ServerContext{
	
	private JSONObject jsonQuestionResetPassword;
	private JSONObject jsonTokenChangePassword;
	private JSONObject jsonLoginService;
	private JSONObject jsonChangePassword;
	
	public ForgotLoginService(Server server){
		super(server);
		this.clearForgotLoginService();
	}

	public JSONObject getJsonQuestionResetPassword() {
		return jsonQuestionResetPassword;
	}

	public JSONObject getJsonTokenChangePassword() {
		return jsonTokenChangePassword;
	}

	public void setJsonTokenChangePassword(JSONObject jsonTokenChangePassword) {
		this.jsonTokenChangePassword = jsonTokenChangePassword;
	}
	
	public Object[] sendQuestionResetPassword(String question1, String question2, String question3){
		JSONArray array;
		List<NameValuePair> nameValuePairs=null;
		try {
			array = jsonQuestionResetPassword.getJSONArray("securityquestions");
			nameValuePairs= new ArrayList<NameValuePair>();
	        nameValuePairs.add(new BasicNameValuePair("securityanswers["+array.getJSONObject(0).getInt("securityanswerid")+"]",question1));
	        nameValuePairs.add(new BasicNameValuePair("securityanswers["+array.getJSONObject(1).getInt("securityanswerid")+"]"+"]",question2));
	        nameValuePairs.add(new BasicNameValuePair("securityanswers["+array.getJSONObject(2).getInt("securityanswerid")+"]"+"]",question3));
	        nameValuePairs.add(new BasicNameValuePair("userid", ""+jsonQuestionResetPassword.getInt("userid")));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object[] response=server.makeRequestPost(Server.FORGOT_VALIDATE_QUESTIONS, nameValuePairs);
		this.jsonTokenChangePassword=(JSONObject)response[1];
		return  response;	
	}
	
	public Object[] forgotUserName(String email){
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("emailaddress",email));
        Object[] sendEmail=server.makeRequestPost(Server.FORGOT_USERNAME, nameValuePairs);
		return  sendEmail;
	}
	
	public Object[] forgotPassword(String username){
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("username",username));
        Object[] response=server.makeRequestPost(Server.FORGOT_PASSWORD, nameValuePairs);
        this.jsonQuestionResetPassword=(JSONObject)response[1];
		return response;
	}
	
	public Object[] loginUser(String username, String password){
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("UserName",username));
        nameValuePairs.add(new BasicNameValuePair("Password", password));
        nameValuePairs.add(new BasicNameValuePair("pipe", "mobile"));
        Object[] response=server.makeRequestPost(Server.LOGIN_SERVICE, nameValuePairs);
        this.jsonLoginService=(JSONObject)response[1];
        return response;
	}
	
	public Object[] changePassword(String newPassword, String confirmPassword){
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("password",newPassword));
        nameValuePairs.add(new BasicNameValuePair("confirmpassword", confirmPassword));
        try {
        	if(jsonTokenChangePassword!=null){
        		nameValuePairs.add(new BasicNameValuePair("token", jsonTokenChangePassword.getString("token")));
        		nameValuePairs.add(new BasicNameValuePair("userid", jsonTokenChangePassword.getString("userid")));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Object[] response=server.makeRequestPost(Server.CHANGE_PASSWORD_SERVICE, nameValuePairs);
        jsonChangePassword=(JSONObject)response[1];
        return response;
	}
	
	public String getFirstName(){
		String username="Charles";
		try {
			if(jsonLoginService!=null){
				username= this.jsonLoginService.getString("FirstName");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return username;
	}
	
	public String getTitleFeed(){
		String title="";
		try {
			title= this.jsonLoginService.getString("RSSTitle");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return title;
	}
	
	public String getUrlFeed(){
		String url=null;
		try {
			url= this.jsonLoginService.getString("OmegaFiRSSFeed");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}
	
	public void clearForgotLoginService(){
		jsonQuestionResetPassword=null;
		jsonLoginService=null;
		jsonChangePassword=null;
		jsonChangePassword=null;
	}
}