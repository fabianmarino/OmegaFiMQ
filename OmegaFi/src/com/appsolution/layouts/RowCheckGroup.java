package com.appsolution.layouts;

import java.util.ArrayList;

import com.appsolution.interfaces.OnRowCheckListener;
import com.appsolution.omegafi.R;

import android.graphics.Color;
import android.util.Log;
import android.view.View;

public class RowCheckGroup {
	
	private ArrayList< RowCheckOmegaFi> listRowsChecked=new ArrayList<RowCheckOmegaFi>();
	private DialogSelectableOF contentSelectable;
	private OnRowCheckListener listener;
	private int indexSelected=0;
	private String itemSelected="";
	private RowCheckOmegaFi rowSelected;
	private boolean isSingleCheckUnCheck=false;
	
	public RowCheckGroup(){
		listener=new OnRowCheckListener() {
			
			@Override
			public void actionBeforeChecked() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void actionAfterChecked() {
				// TODO Auto-generated method stub
				
			}
		};
	}
	
	public void addRowCheck(final RowCheckOmegaFi row){
		if(rowSelected==null){
			rowSelected=row;
			indexSelected=0;
			itemSelected=row.getItemRow();
		}
		View.OnClickListener listener=new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
					RowCheckGroup.this.listener.actionBeforeChecked();
					
					if(!isSingleCheckUnCheck){
						unCheckedAll();
						row.setChecked(true);
					}
					else{
						row.setChecked(!rowSelected.isChecked());
					}
					
					indexSelected=listRowsChecked.indexOf(row);
					itemSelected=row.getItemRow();
					rowSelected=row;
					RowCheckGroup.this.listener.actionAfterChecked();
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
	
	public void setOnCheckedListener(OnRowCheckListener listener){
		this.listener=listener;
	}

	public int getIndexSelected() {
		return indexSelected;
	}

	public void setIndexSelected(int indexSelected) {
		this.indexSelected = indexSelected;
	}

	public String getItemSelected() {
		return itemSelected;
	}
	
	public String getItemSelectedSubInfo(){
		Log.d("RowSelected", rowSelected.getNameSubInfo());
		return rowSelected.getNameSubInfo();
	}

	public RowCheckOmegaFi getRowSelected() {
		return rowSelected;
	}
	
	public void setSelectedIndex(int index){
		indexSelected=index;
		rowSelected=listRowsChecked.get(index);
		itemSelected=rowSelected.getItemRow();
		unCheckedAll();
		rowSelected.setChecked(true);
	}
	
	public void setSelectedItem(String item){
		for (RowCheckOmegaFi check:listRowsChecked) {
			String checkString=check.getItemRow();
			if(equalsStrings(checkString, item)){
				setSelectedIndex(listRowsChecked.indexOf(check));
			}
		}
	}
	
	public void doChecksWithOutFade(){
		for (RowCheckOmegaFi check:listRowsChecked) {
			check.setButtonDrawable(R.drawable.radio_button_1);
			check.setTextSizeInformation(check.getTextNameInfo().getTextSize());
			check.getTextSubNameInfo().setTypeface(check.getTextNameInfo().getTypeface());
			check.setTextColor(Color.BLACK);
		}
	}
	
	public static boolean equalsStrings(String cad1, String cad2){
		String aux1=cad1.replace("\n", "");
		aux1=aux1.replace(" ", "");
		String aux2=cad2.replace("\n", "");
		aux2=aux2.replace(" ", "");
		return aux1.equals(aux2);
	}

	public boolean isSingleCheckUnCheck() {
		return isSingleCheckUnCheck;
	}

	public void setSingleCheckUnCheck(boolean isSingleCheckUnCheck) {
		this.isSingleCheckUnCheck = isSingleCheckUnCheck;
	}
	
	
	
}
