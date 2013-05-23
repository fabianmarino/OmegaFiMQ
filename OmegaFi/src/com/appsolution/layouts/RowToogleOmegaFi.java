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
		toogleButton=new ToggleButton(super.getContext());
		toogleButton.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
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
