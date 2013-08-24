package com.appsolution.omegafi;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.cookie.Cookie;

import com.appsolution.layouts.DialogInformationOF;
import com.appsolution.layouts.RowInformation;
import com.appsolution.logic.Statement;
import com.appsolution.services.Server;

import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Browser;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

public class StatementsActivity extends OmegaFiActivity {

	private LinearLayout linearStatements;
	private ListView listStatements;
	private StatementArrayAdapter statementArray;
	private int idAccount;
	private long enqueue;
    private DownloadManager dm;
    public static final String MIME_TYPE_PDF = "application/pdf";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statements);
		linearStatements=(LinearLayout)findViewById(R.id.linearStatements);
		listStatements=(ListView)findViewById(R.id.listViewStatements);
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
	
	

	/**
	 * Check if the supplied context can render PDF files via some installed application that reacts to a intent
	 * with the pdf mime type and viewing action.
	 *
	 * @param context
	 * @return
	 */
	public static boolean canDisplayPdf(Context context) {
	    PackageManager packageManager = context.getPackageManager();
	    Intent testIntent = new Intent(Intent.ACTION_VIEW);
	    testIntent.setType(MIME_TYPE_PDF);
	    if (packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0) {
	        return true;
	    } else {
	        return false;
	    }
	}
	

	@Override
	protected void optionsActionBar() {
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBarCustom.setTitle("STATEMENTS");
		actionBar.setCustomView(actionBarCustom);
	}
	
	private List<String> getListAdapter(ArrayList<Statement> statements){
		List<String> list=new ArrayList<String>();
		for(Statement statement:statements){
			list.add(statement.getId()+"�"+statement.getDateClose()+"�"+statement.getTypeStatement());
		}
		return list;
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
				startProgressDialog("Loading Statements...", getResources().getString(R.string.please_wait));
			}
			
			@Override
			protected Boolean doInBackground(Void... params) {
				Object[] listItems=Server.getServer().getHome().getListStatemets(idAccount);
				status=(Integer)listItems[0];
				ArrayList<Statement> arrayList = (ArrayList<Statement>)listItems[1];
				list=arrayList;
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				if(Server.isStatusOk(status)){
					if(!list.isEmpty()){
						statementArray=new StatementArrayAdapter(getApplicationContext(),  getListAdapter(list));
						listStatements.setAdapter(statementArray);
					}
					else{
						showMessageNotStatements();
					}
				}
				stopProgressDialog();
				refreshActivity();
			}
		};
		
		task.execute();
	}
    
    private void showMessageNotStatements(){
		final DialogInformationOF of=new DialogInformationOF(this);
		of.setMessageDialog("There are no statements on file at this time.");
		of.setButtonListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				of.dismissDialog();
				finish();
			}
		});
		of.showDialog();
	}
    
    private void showDownload() {
        Intent i = new Intent();
        i.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
        startActivity(i);
    }
    
    private class StatementArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StatementArrayAdapter(Context context, List<String> objects) {
          super(context, android.R.layout.simple_list_item_1, objects);
          for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i), i);
          }
        }

        @Override
        public long getItemId(int position) {
          String item = getItem(position);
          return mIdMap.get(item);
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
        	final String[] itemStatement=getItem(position).split("�");
        	RowInformation rowpdf=null;
        	if(convertView==null){
        		convertView=new RowInformation(getApplicationContext()); 
        		rowpdf=(RowInformation)convertView;
        	}
        	else{
        		rowpdf=(RowInformation)convertView;
        	}
        		rowpdf.setImageIconInfo(R.drawable.pdf_icon);
    			rowpdf.setNameInfo(itemStatement[1]);
    			rowpdf.setVisibleArrow(true);

    			int padding=getResources().getDimensionPixelSize(R.dimen.padding_6dp);
    			rowpdf.setPaddingRow(padding, padding, padding, padding);
    			rowpdf.setOnClickListener(new View.OnClickListener() {
    				
    				@Override
    				public void onClick(View arg0) {
    					if(StatementsActivity.canDisplayPdf(StatementsActivity.this)){
    					try {
    						ProgressDialog progressDiag=new ProgressDialog(StatementsActivity.this);
    						progressDiag.setTitle("Download Statement");
    						progressDiag.setMessage(getResources().getString(R.string.please_wait));
    						progressDiag.setCancelable(false);
    						progressDiag.setIndeterminate(false);
    						progressDiag.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
							Server.getServer().downloadFileAsync(Server.getUrlStatementsView(idAccount, Integer.parseInt(itemStatement[0]),itemStatement[2]),
									"statement.pdf", progressDiag, StatementsActivity.this);
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
    					}
    					else{
    						OmegaFiActivity.showAlertMessage("Please install a pdf reader", StatementsActivity.this);
    					}
    				}
    			});
    			return convertView; 
        }

        @Override
        public boolean hasStableIds() {
          return true;
        }

      }
    
}
