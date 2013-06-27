package com.appsolution.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

import com.appsolution.omegafi.OmegaFiActivity;

import android.util.Log;

public class Server {

	public static final String LOGIN_SERVICE="https://qa-services.omegafi.com/myomegafi/api/v1/login/mobile_login_post.php";
	public static final String ACCOUNTS_SERVICE="https://qa-services.omegafi.com/myomegafi/api/v1/accounts";
	public static final String CHAPTERS_SERVICE="https://qa-services.omegafi.com/myomegafi/api/v1/chapters";
	public static final String PROFILE_SERVICE="https://qa-services.omegafi.com/myomegafi/api/v1/user/profile";
	public static final String TERMS_SERVICE="https://qa-services.omegafi.com/myomegafi/api/v1/terms";
	public static final String PRIVACY_SERVICE="https://qa-services.omegafi.com/myomegafi/api/v1/privacy";
	
	public static int TIME_OUT=10000;
	private HttpClient clientRequest;
	private HttpContext contextHttp;
	private CookieStore cookieStore;
	public String evaluador=null;
	private JSONObject jsonProfile;
	
	public Server(){
		clientRequest=new DefaultHttpClient();
		contextHttp=new BasicHttpContext();
		cookieStore=new BasicCookieStore();
		contextHttp.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
	}
	
	public JSONObject getProfileUser(){
		jsonProfile=this.makeRequestGet(OmegaFiActivity.servicesOmegaFi.PROFILE_SERVICE);
		return jsonProfile;
	}
	
	public String getURLProfilePhoto(){
		String url=null;
		try {
			if(!jsonProfile.getJSONObject("individual").getJSONObject("profile_picture").isNull("url")){
				url= jsonProfile.getJSONObject("individual").getJSONObject("profile_picture").getString("url");
			}  
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}
	
	public String getCompleteName(){
		String name="First Last";
		try {
			name= jsonProfile.getJSONObject("individual").getString("first_name")+" "+jsonProfile.getJSONObject("individual").getString("last_name");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name;
	}
	
	public int getAnnouncementsCount(){
		int number=0;
		try {
			number= jsonProfile.getJSONObject("individual").getInt("announcement_count");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return number;
	}
	
	public JSONObject getAccountsUser(){
		return this.makeRequestGet(OmegaFiActivity.servicesOmegaFi.ACCOUNTS_SERVICE);
	}
	
	public JSONObject getChaptersUser(){
		return this.makeRequestGet(OmegaFiActivity.servicesOmegaFi.CHAPTERS_SERVICE);
	}
	
	
	public JSONObject makeRequestPost(String url,List<NameValuePair> data){
		JSONObject jsonResponse = null;
		HttpPost post=new HttpPost(url);
		HttpConnectionParams.setConnectionTimeout(post.getParams(), TIME_OUT);
		HttpConnectionParams.setSoTimeout(post.getParams(), TIME_OUT);
		try {
			post.setEntity(new UrlEncodedFormEntity(data));
			HttpResponse response=clientRequest.execute(post,contextHttp);
			Log.d("Cookies", "Lista a continuacion");
			logCookies();
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
		HttpGet get=new HttpGet(url);
		get.addHeader("Content-Type", "text/plain");
		get.addHeader("Accept", "*/*");
		try {
			HttpResponse response=clientRequest.execute(get,contextHttp);
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
	
	public String getPrivacyOmegaFi(){
		return this.makeRequestGetHtml(Server.PRIVACY_SERVICE);
	}
	
	public String getTermsOmegaFi(){
		return this.makeRequestGetHtml(Server.TERMS_SERVICE);
	}
	
	public String makeRequestGetHtml(String url){
		HttpGet get=new HttpGet(url);
		String builder="";
		get.addHeader("Content-Type", "text/plain");
		get.addHeader("Accept", "*/*");
		try {
			HttpResponse response=clientRequest.execute(get,contextHttp);
			builder=this.fromResponseToString(response);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return builder;
	}
	
	private JSONObject fromResponseToJSON(HttpResponse response){
		Log.d("Status", response.getStatusLine().toString());
		Log.d("Status code", response.getStatusLine().getStatusCode()+"");
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
	        Log.d("Read Buffer", todo.toString());
	        jsonResponse=new JSONObject(todo.toString());
	        rd.close();
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
	
	private String fromResponseToString(HttpResponse response){
		Log.d("Status", response.getStatusLine().toString());
		BufferedReader rd;
		StringBuilder todo=new StringBuilder();
		try {
			rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	        String line = "";
	        while ((line = rd.readLine()) != null) {
	        	todo.append(line);
	        }
	        rd.close();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return todo.toString();
	}
	
	public void setEvaluador(String eval){
		evaluador=eval;
	}
	
	public String getEvaluador(){
		return evaluador;
	}
	
	public void logCookies(){
		List<Cookie> cookies = cookieStore.getCookies();
        for (int i = 0; i < cookies.size(); i++) {
            Log.d("Cookies", "Local cookie: " + cookies.get(i));
        }
	}
	
}