package com.appsolution.layouts;

import java.util.ArrayList;
import java.util.List;

import com.appsolution.omegafi.OmegaFiActivity;
import com.appsolution.omegafi.R;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class SpinnerNameTopInfo extends LinearLayout {

	private TextView nameTopInfo;
	private LinearLayout contentAll;
	private LinearLayout contentView;
	private Spinner spinner;
	private Activity parent;
	
	public SpinnerNameTopInfo(Activity context){
		super(context);
		parent=context;
		this.initialize();
		
	}
	
	public SpinnerNameTopInfo(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initialize();
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ViewNameTopInfo);
		
		String nameInfo=a.getString(R.styleable.ViewNameTopInfo_name_top_info);
		setNameInfoTop(nameInfo);
		
		boolean fill=a.getBoolean(R.styleable.ViewNameTopInfo_width_fill_parent, false);
		setWidthFillParent(fill);
		
		a.recycle();
	}
	
	private void initialize(){
		LayoutInflater inflate= (LayoutInflater)parent.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflate.inflate(R.layout.spinner_name_top_info, this, true);
		contentAll=(LinearLayout)findViewById(R.id.linearViewNameTopInfoSpinner);
		nameTopInfo=(TextView)findViewById(R.id.name_info_top_spinner);
		nameTopInfo.setTypeface(OmegaFiActivity.getFont(getContext(), 0));
		contentView=(LinearLayout)findViewById(R.id.linearContentAddViewSpinner);
		spinner=(Spinner)findViewById(R.id.spinner_top_info);
	}
	
	public void setNameInfoTop(String name){
		if(name!=null){
			nameTopInfo.setVisibility(View.VISIBLE);
			nameTopInfo.setText(name);
		}
		else{
			nameTopInfo.setVisibility(View.GONE);
		}
	}
	
	@Override
	public void addView(View child) {
		contentView.addView(child);
	}
	
	public void setWidthFillParent(boolean fill){
		if(fill){
			contentAll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		}
		else{
			contentAll.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		}
	}

	public void setListSpinner(ArrayList<String> listArray) {
		List<String> list = listArray;
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_omegafi_small, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);
	}
	

}