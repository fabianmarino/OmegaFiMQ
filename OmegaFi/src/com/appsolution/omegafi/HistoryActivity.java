package com.appsolution.omegafi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.appsolution.layouts.DialogInformationOF;
import com.appsolution.layouts.RowInformation;
import com.appsolution.logic.HistoryItem;
import com.appsolution.services.Server;

import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HistoryActivity extends OmegaFiActivity {

	
	private HistoryArrayAdapter adapterHistory;
	private ListView listHistory;
	private int id;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
		listHistory=(ListView)findViewById(R.id.listViewHistory);
		id=getIntent().getExtras().getInt("id");
		chargeHistory();
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBarCustom.setTitle("HISTORY");
		actionBar.setCustomView(actionBarCustom);
	}
	
	private List<String> getListHistory(ArrayList<HistoryItem> list){
		List<String> listHistory=new ArrayList<String>();
		for (HistoryItem item:list) {
			listHistory.add(item.getDescription()+"�"+item.getDateTransaction()+"�"+item.getTransactionAmount());
		}
		return listHistory;
	}
	
	private void chargeHistory(){
		AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>(){

			private int status=0;
			ArrayList<HistoryItem> list;
			
			@Override
			protected void onPreExecute() {
				startProgressDialog("Loading history", getResources().getString(R.string.please_wait));
			}
			
			@Override
			protected Boolean doInBackground(Void... params) {
				Object[] listItems=Server.getServer().getHome().getListHistory(id);
				status=(Integer)listItems[0];
				ArrayList<HistoryItem> arrayList = (ArrayList<HistoryItem>)listItems[1];
				list=arrayList;
				Server.getServer().getHome().getProfile().updateProfileIfNecessary();
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				if(Server.isStatusOk(status)){
					if(!list.isEmpty()){
						adapterHistory=new HistoryArrayAdapter(getApplicationContext(), getListHistory(list));
						listHistory.setAdapter(adapterHistory);
					}
					else{
						showMessageNotHistory();
					}
					}
				else{
					OmegaFiActivity.showErrorConection(HistoryActivity.this, status, getResources().getString(R.string.object_not_found), false);
				}
				stopProgressDialog();
				refreshActivity();
			}
		};
		
		task.execute();
	}
	
	private void showMessageNotHistory(){
		final DialogInformationOF of=new DialogInformationOF(this);
		of.setMessageDialog("There are no transaction history on file at this time.");
		of.setButtonListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				of.dismissDialog();
				finish();
			}
		});
		of.showDialog();
	}
	
    private class HistoryArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public HistoryArrayAdapter(Context context, List<String> objects) {
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
        	RowInformation rowHistory=null;
        	if(convertView==null){
        		convertView=new RowInformation(getApplicationContext()); 
        		rowHistory=(RowInformation)convertView;
        		rowHistory.getNameTextView().getLayoutParams().width=OmegaFiActivity.getWidthPercentageDisplay(getApplicationContext(), 0.8f);
        	}
        	else{
        		rowHistory=(RowInformation)convertView;
        	}
        	
        	rowHistory.setNameInfo(itemStatement[0]);
        	rowHistory.setNameSubInfo(itemStatement[1]);
        	rowHistory.setValueInfo(itemStatement[2]);
    			return convertView; 
        }

        @Override
        public boolean hasStableIds() {
          return true;
        }

      }	
}