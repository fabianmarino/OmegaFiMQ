package com.appsolution.layouts;

import com.appsolution.omegafi.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.EditText;

public class RowEditTextOmegaFi extends RowEditInformation{

	private EditText textEdit;
	
	public RowEditTextOmegaFi(Context context){
		super(context);
		this.initializeComponets();
	}
	
	public RowEditTextOmegaFi(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initializeComponets();
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.RowEditTextOmegaFi);
		   boolean isEditable = a.getBoolean(R.styleable.RowEditTextOmegaFi_text_editable,true);
		   setEditable(isEditable);
		   
		   String text=a.getString(R.styleable.RowEditTextOmegaFi_text_edit);
		   setTextEdit(text);
		    
		    a.recycle();
	}
	
	public void initializeComponets(){
		textEdit=new EditText(super.getContext());
		textEdit.setGravity(Gravity.RIGHT);
		textEdit.setLayoutParams(new LayoutParams(200,LayoutParams.WRAP_CONTENT));
		this.addViewRight(textEdit);
	}
	
	public void setEditable(boolean editable){
		textEdit.setEnabled(editable);
	}
	
	public void setTextEdit(String text){
		textEdit.setText(text);
	}
	
	
}