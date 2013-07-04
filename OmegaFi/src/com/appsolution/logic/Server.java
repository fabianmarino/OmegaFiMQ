package com.appsolution.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Server {

	public static final String HOST="https://qa-services.omegafi.com";
	public static final String LOGIN_SERVICE=HOST+"/myomegafi/api/v1/login/mobile_login_post.php";
	public static final String ACCOUNTS_SERVICE=HOST+"/myomegafi/api/v1/accounts";
	public static final String CHAPTERS_SERVICE=HOST+"/myomegafi/api/v1/chapters";
	public static final String PROFILE_SERVICE=HOST+"/myomegafi/api/v1/user/profile";
	public static final String TERMS_SERVICE=HOST+"/myomegafi/api/v1/terms";
	public static final String PRIVACY_SERVICE=HOST+"/myomegafi/api/v1/privacy";
	public static final String FORGOT_USERNAME=HOST+"/myomegafi/api/v1/forgottenusernames";
	public static final String FORGOT_PASSWORD=HOST+"/myomegafi/api/v1/forgottenpasswords/validateusername";
	public static final String FORGOT_VALIDATE_QUESTIONS=HOST+"/myomegafi/api/v1/forgottenpasswords/validatesecurityquestions";
	public static final String CALENDAR_SERVICE=HOST+"/myomegafi/api/v1/calendar";
	public static final String CHANGE_PASSWORD_SERVICE=HOST+"/myomegafi/api/v1/forgottenpasswords/changepassword";
	public static int TIME_OUT=20000;
	private HttpClient clientRequest;
	private HttpContext contextHttp;
	private CookieStore cookieStore;
	public String evaluador=null;
	private HomeServices home;
	private ForgotLoginService forgotLogin;
	
	
	public Server(){
		clientRequest=new DefaultHttpClient();
		contextHttp=new BasicHttpContext();
		cookieStore=new BasicCookieStore();
		contextHttp.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		home=new HomeServices(this);
		forgotLogin=new ForgotLoginService(this);
	}
	
	public Object[]  makeRequestPost(String url,List<NameValuePair> data){
		Object[] statusContent=new Object[2];
		statusContent[0]=0;
		JSONObject jsonResponse = null;
		HttpPost post=new HttpPost(url);
		HttpConnectionParams.setConnectionTimeout(post.getParams(), TIME_OUT);
		HttpConnectionParams.setSoTimeout(post.getParams(), TIME_OUT);
		try {
			post.setEntity(new UrlEncodedFormEntity(data));
			HttpResponse response=clientRequest.execute(post,contextHttp);
			statusContent[0]=response.getStatusLine().getStatusCode();
			Log.d("Status", statusContent[0]+" respuesta");
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
		statusContent[1]=jsonResponse;
		return statusContent;
	}
	
	public Object[] makeRequestGet(String url){
		Object[] responseObject=new Object[2];
		responseObject[0]=0;
		JSONObject jsonResponse=null;
		HttpGet get=new HttpGet(url);
		get.addHeader("Content-Type", "text/plain");
		get.addHeader("Accept", "*/*");
		try {
			HttpResponse response=clientRequest.execute(get,contextHttp);
			responseObject[0]=response.getStatusLine().getStatusCode();
			jsonResponse=this.fromResponseToJSON(response);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		responseObject[1]=jsonResponse;
		return responseObject;
	}
	
	public Object[] makeRequestGetJSONArray(String url){
		Object[] responseObject=new Object[2];
		responseObject[0]=0;
		JSONArray jsonResponse=null;
		HttpGet get=new HttpGet(url);
		get.addHeader("Content-Type", "text/plain");
		get.addHeader("Accept", "*/*");
		try {
			HttpResponse response=clientRequest.execute(get,contextHttp);
			responseObject[0]=response.getStatusLine().getStatusCode();
			jsonResponse=this.fromResponseToJSONArray(response);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		responseObject[1]=jsonResponse;
		return responseObject;
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
	        rd.close();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			jsonResponse=null;
			e.printStackTrace();
		}
		return jsonResponse;
	}
	
	private JSONArray fromResponseToJSONArray(HttpResponse response){
		BufferedReader rd;
		StringBuilder todo;
		JSONArray jsonResponse = null;
		try {
			rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			todo=new StringBuilder();
	        String line = "";
	        while ((line = rd.readLine()) != null) {
	        	todo.append(line);
	        }
	        jsonResponse=new JSONArray(todo.toString()); 
	        rd.close();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			jsonResponse=null;
			e.printStackTrace();
		}
		return jsonResponse;
	}
	
	private String fromResponseToString(HttpResponse response){
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
	
	public ForgotLoginService getForgotLogin() {
		return forgotLogin;
	}
	
	public void clearForgotLoginService(){
		forgotLogin=null;
	}

	public HomeServices getHome() {
		return home;
	}
	
	public static String getUrlOfficers(int id){
		return "https://qa-services.omegafi.com/myomegafi/api/v1/chapters/"+id+"/officers";
	}
	
	public Bitmap downloadBitmap(String url) throws IOException {
		Bitmap bitmap=null;
		if(url!=null){
			Log.d("download bitmap", url);
	        HttpUriRequest request = new HttpGet(url.toString());
	        HttpClient httpClient = new DefaultHttpClient();
	        HttpResponse response = httpClient.execute(request,contextHttp);
	        StatusLine statusLine = response.getStatusLine();
	        int statusCode = statusLine.getStatusCode();
	        if (statusCode == 200) {
	            HttpEntity entity = response.getEntity();
	            byte[] bytes = EntityUtils.toByteArray(entity);
	            BitmapFactory.Options opt = new BitmapFactory.Options();
	            opt.inPurgeable = true;
	            bitmap = BitmapFactory.decodeByteArray(bytes, 0,
	                    bytes.length,opt);
	        } else {
	            throw new IOException("Download failed, HTTP response code "
	                    + statusCode + " - " + statusLine.getReasonPhrase());
	        }
        }
		return bitmap;
    }
	
}