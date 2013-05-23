package com.appsolution.layouts;

import com.appsolution.omegafi.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class RowEditTextSubmit extends RelativeLayout {
	
	private EditText textEdit;
	private Button buttonSubmit;

	public RowEditTextSubmit(Context context){
		super(context);
		this.initialize();
	}
	
	public RowEditTextSubmit(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initialize();
		
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.RowEditTextSubmit);
		
		String placeholder=a.getString(R.styleable.RowEditTextSubmit_text_placehoder);
		setPlaceHolderEdit(placeholder);
		
		String textButton= a.getString(R.styleable.RowEditTextSubmit_text_submit_button);
		setTextButton(textButton);
		
		a.recycle();
		
	}
	
	private void initialize() {
		LayoutInflater inflate= (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflate.inflate(R.layout.row_text_edit_submit, this, true);
		textEdit=(EditText)findViewById(R.id.editText_submit);
		buttonSubmit=(Button)findViewById(R.id.buttonSubmitEdit);
	}
	
	
	public void setPlaceHolderEdit(String text){
		textEdit.setHint(text);
	}
	
	public void setTextButton(String text){
		buttonSubmit.setText(text);
	}
	
	public void onSubmit(OnClickListener listener){
		buttonSubmit.setOnClickListener(listener);
	}

}
