package com.appsolution.layouts;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appsolution.omegafi.R;

public class DialogSelectableOF {

	private AlertDialog.Builder builderDialog;
	private AlertDialog alertDialog;
	private Activity activity;
	private LayoutInflater layoutInflater;
	
	private LinearLayout linearSelectables;
	private TextView textTitle;
	private Button buttonAction;
	
	private ArrayList<String> optionsSelectables=new ArrayList<String>();
	RowCheckGroup group;
	
	public DialogSelectableOF(Activity activity){
		this.activity=activity;
		layoutInflater=(LayoutInflater)this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = layoutInflater.inflate(R.layout.dialog_selectable_omegafi,
				(ViewGroup)this.activity.findViewById(R.id.layout_root_selectable));
		builderDialog = new AlertDialog.Builder(activity);
		builderDialog.setView(layout);
		
		linearSelectables=(LinearLayout)layout.findViewById(R.id.contentRowsSelectables);
		textTitle=(TextView)layout.findViewById(R.id.titleSelectables);
		buttonAction=(Button)layout.findViewById(R.id.buttonActionDialog);
		this.completeTestOptionsSelectables();
		this.completeOptionsSelectable();
		alertDialog = builderDialog.create();
	}
	
	private void completeTestOptionsSelectables(){
		optionsSelectables.add("Winston Smith,Mastercard (4571)");
		optionsSelectables.add("Wanda Smith,Mastercard (1234)");
		optionsSelectables.add("Winston Smith,Checking (0215)");
	}
	
	private void completeOptionsSelectable(){
		group=new RowCheckGroup();
		for (String option:optionsSelectables) {
			RowCheckOmegaFi rowChecked=new RowCheckOmegaFi(activity,group);
			rowChecked.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
			int padding=(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, 
					activity.getApplicationContext().getResources().getDisplayMetrics());
			rowChecked.setPaddingRow(padding,padding,0,padding);
			String[] values=option.split(",");
			rowChecked.setNameInfo(values[0]);
			rowChecked.setSubnameInfo(values[1]);
			linearSelectables.addView(rowChecked);
		}
	}
	
	public void setTitleDialog(String message){
		if(message==null){
			textTitle.setVisibility(View.GONE);	
		}
		else{
			textTitle.setText(message);
		}
	}
	
	public void setTextButton(String text){
		if(text==null){
			buttonAction.setVisibility(View.GONE);
		}
		else{
			buttonAction.setText(text);
		}
	}
	
	public void showDialog(){
		alertDialog.show();
	}
	
	public void dismissDialog(){
		alertDialog.dismiss();
	}
	
	public void setButtonListener(View.OnClickListener event){
		buttonAction.setOnClickListener(event);
	}
	
	public void setCloseOnSelectedItem(boolean closed){
		if(closed){
			group.setContentSelectable(this);
		}
		else{
			group.setContentSelectable(null);
		}
	}
	
}
