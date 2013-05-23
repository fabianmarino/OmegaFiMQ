package com.appsolution.layouts;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class RowEditNameTopInfo extends ViewNameTopInfo {

	private EditText textInfo;
	
	public RowEditNameTopInfo(Context context){
		super(context);
		this.initialize();
	}
	
	public RowEditNameTopInfo(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initialize();
	}
	
	private void initialize(){
		textInfo=new EditText(super.getContext());
		textInfo.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		this.addView(textInfo);
	}

}
