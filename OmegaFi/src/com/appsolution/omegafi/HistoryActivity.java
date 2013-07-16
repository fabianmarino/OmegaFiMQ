package com.appsolution.omegafi;

import java.util.ArrayList;

import com.appsolution.layouts.RowInformation;
import com.appsolution.logic.HistoryItem;
import com.appsolution.omegafi.R.drawable;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.drm.DrmStore.Action;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class HistoryActivity extends OmegaFiActivity {

	private LinearLayout linearContent;
	private int id;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
		linearContent=(LinearLayout)findViewById(R.id.contentLinearHistory);
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
	
	private void completeHistory(ArrayList<HistoryItem> list){
		for (HistoryItem item:list) {
			Log.d("esta entrando", "Hasta aqui");
			RowInformation row=new RowInformation(this);
			row.setNameInfo(item.getDescription());
			row.setNameSubInfo(item.getDateTransaction());
			row.setValueInfo("$ "+item.getTransactionAmount());
			row.postInvalidate();
			linearContent.addView(row);
		}
		
	}
	
	private void chargeHistory(){
		AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>(){

			int status=0;
			ArrayList<HistoryItem> list;
			
			@Override
			protected void onPreExecute() {
				startProgressDialog("Charging history", getResources().getString(R.string.please_wait));
			}
			
			@Override
			protected Boolean doInBackground(Void... params) {
				Object[] listItems=MainActivity.servicesOmegaFi.getHome().getListHistory(id);
				status=(Integer)listItems[0];
				ArrayList<HistoryItem> arrayList = (ArrayList<HistoryItem>)listItems[1];
				list=arrayList;
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				stopProgressDialog();
				completeHistory(list);
				linearContent.postInvalidate();
				refreshActivity();
			}
		};
		
		task.execute();
	}

}
