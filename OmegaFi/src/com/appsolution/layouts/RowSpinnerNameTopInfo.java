package com.appsolution.layouts;

import java.util.ArrayList;
import java.util.List;

import com.appsolution.omegafi.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class RowSpinnerNameTopInfo extends ViewNameTopInfo {

	private Spinner spinner;
	
	public RowSpinnerNameTopInfo(Context context) {
		super(context);
		this.initialize();
	}

	public RowSpinnerNameTopInfo(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initialize();
	}
	
	private void initialize() {
		spinner=new Spinner(getContext());
		spinner.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		spinner.setBackgroundResource(R.drawable.white_input_small);
		this.addView(spinner);
	}
	
public void setListSpinner(ArrayList<String> listArray){
	List<String> list = listArray;
	ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_omegafi_small, list);
	dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	spinner.setAdapter(dataAdapter);
}

}
