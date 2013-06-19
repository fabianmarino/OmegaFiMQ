package com.appsolution.layouts;

import com.appsolution.omegafi.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;

public class RowEditTextOmegaFi extends RowEditInformation{

	private EditText textEdit;
	private EditText textEdit2;
	private Drawable textOriginal;
	
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
		   
		   String text2=a.getString(R.styleable.RowEditTextOmegaFi_text_edit_2);
		   setTextEdit2(text2);
		   
		   int resource=a.getResourceId(R.styleable.RowEditTextOmegaFi_background_input_text, R.drawable.white_input);
		   setBackgroundInputs(resource);
		    
		    a.recycle();
	}
	
	public void initializeComponets(){
		LinearLayout linear=new LinearLayout(super.getContext());
		linear.setLayoutParams(new LayoutParams(android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, 
				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
		linear.setOrientation(LinearLayout.VERTICAL);
		linear.setGravity(Gravity.LEFT);
		textEdit=new EditText(super.getContext());
		textOriginal=textEdit.getBackground();
		textEdit.setBackgroundResource(R.drawable.white_input);
		textEdit.setLayoutParams(new LayoutParams(super.getResources().getDimensionPixelSize(R.dimen.width_row_edit_text),LayoutParams.WRAP_CONTENT));
		textEdit2=new EditText(super.getContext());
		textEdit2.setBackgroundResource(R.drawable.white_input);
		textEdit2.setLayoutParams(new LayoutParams(super.getResources().getDimensionPixelSize(R.dimen.width_row_edit_text),LayoutParams.WRAP_CONTENT));
		textEdit2.setVisibility(LinearLayout.GONE);
		linear.addView(textEdit);
		linear.addView(textEdit2);
		this.addViewRight(linear);
		int padding=super.getResources().getDimensionPixelSize(R.dimen.padding_6dp);
		this.setPaddingRow(padding,padding,padding,padding);
	}
	
	public void setEditable(boolean editable){
		textEdit.setEnabled(editable);
	}
	
	public void setTextEdit(String text){
		textEdit.setText(text);
	}
	
	public void setTextEdit2(String text){
		if(text!=null){
			textEdit2.setText(text);
			textEdit2.setVisibility(LinearLayout.VISIBLE);
		}
	}
	
	public void setBackgroundInputs(int resource){
		if(resource!=R.drawable.white_input){
			textEdit.setBackgroundDrawable(textOriginal);
			textEdit2.setBackgroundDrawable(textOriginal);
		}
	}
	
}