package com.appsolution.layouts;

import com.appsolution.omegafi.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class RowToogleOmegaFi extends RowEditInformation {

	private ToggleButton toogleButton;
	
	public RowToogleOmegaFi(Context context){
		super(context);
		this.initialize();
	}
	
	public RowToogleOmegaFi(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initialize();
		
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.RowToogleOmegaFi);
		   boolean isActivate = a.getBoolean(R.styleable.RowToogleOmegaFi_toogle_on,false);
		   setActivateOn(isActivate);
		   
		   String textToogle=a.getString(R.styleable.RowToogleOmegaFi_text_toogle);
		   setNameInfo(textToogle);
		   
		   a.recycle();
	}
	
	private void initialize(){
		this.setPaddingRow(10, 5, 5, 5);
		toogleButton=new ToggleButton(super.getContext());
		toogleButton.setTextOn("");
		toogleButton.setTextOff("");
		int width=getContext().getResources().getDimensionPixelSize(R.dimen.width_95dp);
		toogleButton.setLayoutParams(new LayoutParams(width, width/3));
		toogleButton.setBackgroundResource(R.drawable.toogle_omegafi);
		this.addViewRight(toogleButton);
	}
	
	public void setActivateOn(boolean on){
		toogleButton.setChecked(on);
	}
	
	public boolean isActivatedOn(){
		return toogleButton.isChecked();
	}
	
	public void setOnChangeListenerToogle(OnCheckedChangeListener listener){
		toogleButton.setOnCheckedChangeListener(listener);
	}

}
