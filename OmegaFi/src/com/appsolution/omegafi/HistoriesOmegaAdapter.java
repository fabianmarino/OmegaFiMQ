package com.appsolution.omegafi;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HistoriesOmegaAdapter extends BaseAdapter {

	private ArrayList<String> titlesHistory=new ArrayList<String>();
	private Context context;
	
	public HistoriesOmegaAdapter(Context context){
		this.context=context;
		this.completeTestTitlesHistory();
	}
	
	private void completeTestTitlesHistory(){
		for (int i = 2000; i < 2014; i++) {
			titlesHistory.add(i+"");
		}
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return titlesHistory.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView texto=new TextView(context);
		texto.setPadding(20, 20, 20, 20);
		texto.setTextSize(25);
		texto.setText(titlesHistory.get(position));
		return texto;
	}

}
