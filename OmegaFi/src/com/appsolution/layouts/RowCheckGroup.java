package com.appsolution.layouts;

import java.util.ArrayList;

import android.view.View;

public class RowCheckGroup {
	
	private ArrayList< RowCheckOmegaFi> listRowsChecked=new ArrayList<RowCheckOmegaFi>();
	
	public void addRowCheck(final RowCheckOmegaFi row){
		
		row.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				unCheckedAll();
				row.setChecked(true);	
			}
		});
		listRowsChecked.add(row);
	}
	
	private void unCheckedAll(){
		for (RowCheckOmegaFi chek:listRowsChecked) {
			chek.setChecked(false);
		}
	}
	
}
