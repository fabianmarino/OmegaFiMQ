package com.appsolution.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class Server {

	public static final String LOGIN_SERVICE="https://qa-services.omegafi.com/myomegafi/api/v1/login/mobile_login_post.php";
	public static final String ACCOUNTS_SERVICE="https://qa-services.omegafi.com/myomegafi/api/v1/accounts";
	public static final String CHAPTERS_SERVICE="https://qa-services.omegafi.com/myomegafi/api/v1/chapters";
	public static final String PROFILE_SERVICE="https://qa-services.omegafi.com/myomegafi/api/v1/user/profile";
	private HttpClient clientRequest;
	
	public Server(){
		clientRequest=new DefaultHttpClient();
	}
	
	public JSONObject makeRequestPost(String url,List<NameValuePair> data){
		JSONObject jsonResponse = null;
		HttpPost post=new HttpPost(url);
		try {
			post.setEntity(new UrlEncodedFormEntity(data));
			HttpResponse response=clientRequest.execute(post);
			jsonResponse=this.fromResponseToJSON(response);
		} catch (UnsupportedEncodingException e) {
			System.err.print("Error at parsing input json ");
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			System.err.print("Error at execute post ");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return jsonResponse;
	}
	
	public JSONObject makeRequestGet(String url){
		JSONObject jsonResponse=null;
		clientRequest=new DefaultHttpClient();
		HttpGet get=new HttpGet(url);
		try {
			HttpResponse response=clientRequest.execute(get);
			jsonResponse=this.fromResponseToJSON(response);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonResponse;
	}
	
	private JSONObject fromResponseToJSON(HttpResponse response){
		BufferedReader rd;
		StringBuilder todo;
		JSONObject jsonResponse = null;
		try {
			rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			todo=new StringBuilder();
	        String line = "";
	        while ((line = rd.readLine()) != null) {
	        	todo.append(line);
	        }
	        jsonResponse=new JSONObject(todo.toString());
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonResponse;
	}
	
}