package com.appsolution.logic;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.List;

import org.apache.http.Header;
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
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.appsolution.omegafi.MainActivity;
import com.appsolution.omegafi.OmegaFiActivity;
import com.appsolution.omegafi.StatementsActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

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
	public static final String ANNOUNCEMENTS_SERVICE=HOST+"/myomegafi/api/v1/announcements";
	
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
		logCookies();
	}
	
	public Object[]  makeRequestPost(String url,List<NameValuePair> data){
		Object[] statusContent=new Object[2];
		statusContent[0]=0;
		JSONObject jsonResponse = null;
		HttpPost post=new HttpPost(url);
//		post.addHeader("Content-Type", "text/plain");
		post.addHeader("Accept", "*/*");
		HttpConnectionParams.setConnectionTimeout(post.getParams(), TIME_OUT);
		HttpConnectionParams.setSoTimeout(post.getParams(), TIME_OUT);
		try {
			post.setEntity(new UrlEncodedFormEntity(data));
			HttpResponse response=clientRequest.execute(post,contextHttp);
			statusContent[0]=response.getStatusLine().getStatusCode();
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
	
	public Object[] getPrivacyOmegaFi(){
		return this.makeRequestGetHtml(Server.PRIVACY_SERVICE);
	}
	
	public Object[] getTermsOmegaFi(){
		return this.makeRequestGetHtml(Server.TERMS_SERVICE);
	}
	
	public Object[] makeRequestGetHtml(String url){
		Object[] statusHtml=new Object[2];
		HttpGet get=new HttpGet(url);
		String builder="";
		get.addHeader("Content-Type", "text/plain");
		get.addHeader("Accept", "*/*");
		try {
			HttpResponse response=clientRequest.execute(get,contextHttp);
			statusHtml[0]=response.getStatusLine().getStatusCode();
			builder=this.fromResponseToString(response);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		statusHtml[1]=builder;
		return statusHtml;
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
	        Log.d("response", todo.toString());
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
		Log.d("Cookies", cookies.size()+"");
        for (int i = 0; i < cookies.size(); i++) {
            Log.d("Cookies", "Local cookie: " + cookies.get(i));
        }
	}
	
	public List<Cookie> getListCookies(){
		return cookieStore.getCookies();
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
		return Server.CHAPTERS_SERVICE+"/"+id+"/officers";
	}
	
	public static String getUrlHistory(int idAccount){
		return Server.ACCOUNTS_SERVICE+"/"+idAccount+"/history";
	}
	
	public static String getUrlStatements(int idAccount){
		return Server.ACCOUNTS_SERVICE+"/"+idAccount+"/statements";
	}
	
	public static String getUrlStatementsView(int idAccount,int idStatement){
		return Server.ACCOUNTS_SERVICE+"/"+idAccount+"/statements/"+idStatement+"?statement_type=Regular";
	}
	
	public static String getUrlScheduledCharges(int idAccount){
		return Server.ACCOUNTS_SERVICE+"/"+idAccount+"/charges";
	}
	
	public static String getUrlPaymentMethods(int idAccount){
		return Server.ACCOUNTS_SERVICE+"/"+idAccount+"/payment_profiles";
	}
	
	public static String getUrlScheduledPaymentsCreate(int idAccount){
		return Server.ACCOUNTS_SERVICE+"/"+idAccount+"/scheduledpayments";
	}
	
	public static String getUrlScheduledPayments(int idAccount){
		return Server.ACCOUNTS_SERVICE+"/"+idAccount+"/scheduledpayments";
	}
	
	public static String getUrlMemberRooster(int idChapter){
		return Server.CHAPTERS_SERVICE+"/"+idChapter+"/members";
	}
	
	public static String getUrlMemberDetails(int idChapter, int idMember){
		return Server.CHAPTERS_SERVICE+"/"+idChapter+"/members/"+idMember;
	}
	
	public static String getUrlOfficerDetails(int idChapter, int idOfficer){
		return Server.CHAPTERS_SERVICE+"/"+idChapter+"/officers/"+idOfficer;
	}
	
	public Bitmap downloadBitmap(String url) throws IOException {
		Bitmap bitmap=null;
		if(url!=null){
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
	
	public void downloadFileAsync(final String url, final String nameFile, final ProgressDialog barCreated,final Activity parent) throws IOException {
		
		AsyncTask<Void, Integer, Boolean> taskAsync=new AsyncTask<Void, Integer, Boolean>() {
			private String pathFile=null;
			
			@Override
			protected void onPreExecute() {
				barCreated.show();
			}
			
			@Override
			protected void onProgressUpdate(Integer... progress) {
				barCreated.setProgress(progress[0]);
			}
			
			@Override
			protected Boolean doInBackground(Void... params) {
				if(url!=null){
					pathFile=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/"+nameFile;
					HttpGet httpget = new HttpGet(url);
			        HttpResponse response;
					try {
						response = clientRequest.execute(httpget,contextHttp);
						HttpEntity entity = response.getEntity();
				        int totalSize = 0; 
				        int downloadedSize = 0;
				        if (entity != null) {
				        	BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);
				        	totalSize=(int)bufHttpEntity.getContentLength();
				        	barCreated.setMax(totalSize/1000);
				            InputStream stream = bufHttpEntity.getContent();
				            byte buf[] = new byte[1024];
				            int numBytesRead;
				            BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(pathFile));
				            do {
				                numBytesRead = stream.read(buf);
				                if (numBytesRead > 0) {
				                    fos.write(buf, 0, numBytesRead);
				                    downloadedSize += numBytesRead;
				                    publishProgress(downloadedSize/1000);
				                }
				            } while (numBytesRead > 0);
				            fos.flush();
				            fos.close();
				            stream.close();
				            }
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				if(StatementsActivity.canDisplayPdf(parent)){
					Intent intent = new Intent(Intent.ACTION_VIEW);
			        intent.setDataAndType(Uri.parse("file://"+pathFile),
			                "application/pdf");
			        barCreated.getContext().startActivity(intent);
		        }
				else{
					OmegaFiActivity.showAlertMessage("Please install a pdf reader", parent);
				}
		        barCreated.dismiss();
			}
		};
		taskAsync.execute();
    }
	
	public String downloadFile(String url, String nameFile) throws IOException {
		String pathFile=null;
		if(url!=null){
			Log.d("download bitmap", url);
	        HttpUriRequest request = new HttpGet(url.toString());
	        HttpClient httpClient = new DefaultHttpClient();
	        HttpResponse response = httpClient.execute(request,contextHttp);
	        StatusLine statusLine = response.getStatusLine();
	        int statusCode = statusLine.getStatusCode();
	        if (statusCode == 200) {
	        	pathFile="file://sdcard/"+nameFile;
	        	OutputStream output = new FileOutputStream(pathFile);
	            HttpEntity entity = response.getEntity();
	            byte[] bytes = EntityUtils.toByteArray(entity);
	            output.write(bytes);
	        } else {
	            throw new IOException("Download failed, HTTP response code "
	                    + statusCode + " - " + statusLine.getReasonPhrase());
	        }
        }
		return pathFile;
    }
	
	public static void chargeBitmapInImageView(final String source,final String url, final ImageView image){
		AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>() {
			Bitmap imagePhoto=null;
			@Override
			protected Boolean doInBackground(Void... params) {
				if(source!=null){
					if(source.equalsIgnoreCase("omegafi")){
						try {
							imagePhoto=MainActivity.servicesOmegaFi.downloadBitmap(Server.HOST+url);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					else{
						imagePhoto=OmegaFiActivity.loadImageFromURL(url);
					}
				}
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				if(imagePhoto!=null){
					image.setImageBitmap(imagePhoto);
				}
			}
		};
		task.execute();
	}
	
	
	
}