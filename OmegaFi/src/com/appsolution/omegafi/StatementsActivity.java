package com.appsolution.omegafi;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import com.appsolution.layouts.RowInformation;
import com.appsolution.logic.HistoryItem;
import com.appsolution.logic.Server;
import com.appsolution.logic.Statement;

import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

public class StatementsActivity extends OmegaFiActivity {

	private LinearLayout linearStatements;
	private int idAccount;
	private long enqueue;
    private DownloadManager dm;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statements);
		linearStatements=(LinearLayout)findViewById(R.id.linearStatements);
		idAccount=getIntent().getExtras().getInt("id");
		this.chargeStatements();
		
		BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                    long downloadId = intent.getLongExtra(
                            DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                    Query query = new Query();
                    query.setFilterById(enqueue);
                    Cursor c = dm.query(query);
                    if (c.moveToFirst()) {
                        int columnIndex = c
                                .getColumnIndex(DownloadManager.COLUMN_STATUS);
                        if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)) {
                        	showDownload();
                        }
                    }
                }
            }
        };
 
        registerReceiver(receiver, new IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
		
	}
	
	private void downloadFile(int idStatement){
		dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
		Uri Download_Uri = Uri.parse(Server.getUrlStatementsView(idAccount, idStatement));
		   DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
		   request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
		   request.setAllowedOverRoaming(false);
		   request.setTitle("My Data Download");
		   request.setDescription("Android Data download using DownloadManager.");
		   request.setDestinationInExternalFilesDir(this,Environment.DIRECTORY_DOWNLOADS,"statement.pdf");
        
        enqueue = dm.enqueue(request);
	}

	@Override
	protected void optionsActionBar() {
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBarCustom.setTitle("STATEMENTS");
		actionBar.setCustomView(actionBarCustom);
	}
	
	private void completeStatements(ArrayList<Statement> statements){
		int padding=getResources().getDimensionPixelSize(R.dimen.padding_6dp);
		for (final Statement auxStatement:statements) {
			RowInformation rowpdf=new RowInformation(this);
			rowpdf.setImageIconInfo(R.drawable.pdf_icon);
			rowpdf.setNameInfo(auxStatement.getDateClose());
			rowpdf.setVisibleArrow(true);
			rowpdf.setBorderBottom(true);
			rowpdf.setPaddingRow(padding, padding, padding, padding);
			rowpdf.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					dowloadFileAsyncTask(Server.getUrlStatementsView(idAccount, auxStatement.getId()),
							"statement-"+auxStatement.getDateClose()+".pdf");
				}
			});
			rowpdf.postInvalidate();
			linearStatements.addView(rowpdf);
		}
	}
	
	private void openIntentPdf(){
		copyReadAssets();
	}
	
	private void copyReadAssets()
    {
        AssetManager assetManager = getAssets();

        InputStream in = null;
        OutputStream out = null;
        File file = new File(getFilesDir(), "lorem.pdf");
        try
        {
            in = assetManager.open("pdf/lorem.pdf");
            out = openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);

            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e)
        {
            Log.e("tag", e.getMessage());
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(
                Uri.parse("file://" + getFilesDir() + "/lorem.pdf"),
                "application/pdf");

        startActivity(intent);
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException
    {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1)
        {
            out.write(buffer, 0, read);
        }
    }
    
    private void chargeStatements(){
		AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>(){

			int status=0;
			ArrayList<Statement> list;
			
			@Override
			protected void onPreExecute() {
				startProgressDialog("Charging statements", getResources().getString(R.string.please_wait));
			}
			
			@Override
			protected Boolean doInBackground(Void... params) {
				Object[] listItems=OmegaFiActivity.servicesOmegaFi.getHome().getListStatemets(idAccount);
				status=(Integer)listItems[0];
				ArrayList<Statement> arrayList = (ArrayList<Statement>)listItems[1];
				list=arrayList;
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				stopProgressDialog();
				completeStatements(list);
				refreshActivity();
			}
		};
		
		task.execute();
	}
    
    private void showDownload() {
        Intent i = new Intent();
        i.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
        startActivity(i);
    }
    
    private void dowloadFileAsyncTask(final String url,final String nameFIle){
    	AsyncTask<Void, Integer, Boolean> downloader=new AsyncTask<Void, Integer, Boolean>() {
			
    		String path=null;
    		
    		@Override
    		protected void onPreExecute() {
    			startProgressDialog("Downloading", "Downloading pdf statement...");
    		}
    		
			@Override
			protected Boolean doInBackground(Void... params) {
				try {
					path=OmegaFiActivity.servicesOmegaFi.downloadFile(url, nameFIle);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				stopProgressDialog();
				Log.d("path", path);
				if(path!=null){
					Intent intent = new Intent(Intent.ACTION_VIEW);
			        intent.setDataAndType(
			                Uri.parse(path),
			                "application/pdf");

			        startActivity(intent);
				}
			}
		};
		downloader.execute();
    }
    
    


}
