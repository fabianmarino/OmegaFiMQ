package com.appsolution.layouts;

import java.util.ArrayList;

import android.view.View;

public class RowCheckGroup {
	
	private ArrayList< RowCheckOmegaFi> listRowsChecked=new ArrayList<RowCheckOmegaFi>();
	private DialogSelectableOF contentSelectable;
	
	public void addRowCheck(final RowCheckOmegaFi row){
		View.OnClickListener listener=new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				unCheckedAll();
				row.setChecked(true);
				if(contentSelectable!=null){
					contentSelectable.dismissDialog();
				}
			}
		};
		row.setOnClickListener(listener);
		row.getTextNameInfo().setOnClickListener(listener);
		row.getTextSubNameInfo().setOnClickListener(listener);
		listRowsChecked.add(row);
	}
	
	private void unCheckedAll(){
		for (RowCheckOmegaFi chek:listRowsChecked) {
			chek.setChecked(false);
		}
	}

	public DialogSelectableOF getContentSelectable() {
		return contentSelectable;
	}

	public void setContentSelectable(DialogSelectableOF contentSelectable) {
		this.contentSelectable = contentSelectable;
	}
	
	
	
}
