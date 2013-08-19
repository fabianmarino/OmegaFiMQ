package com.appsolution.layouts;

import com.appsolution.omegafi.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class RowEditTextSubmit extends LinearLayout {
	
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
		
		int input=a.getInteger(R.styleable.RowEditTextSubmit_input_type_submit, 1);
		RowEditTextOmegaFi.setTypeInputTextFromAttrs(input, textEdit);
		
		a.recycle();	
	}
	
	private void initialize() {
		LayoutInflater inflate= (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflate.inflate(R.layout.row_text_edit_submit, this, true);
		textEdit=(EditText)findViewById(R.id.editText_submit);
		textEdit.setSingleLine(true);
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
	
	public void setInputTypeEditSubmit(int type){
		textEdit.setInputType(type);
	}
	
	public void setTextRowEditSubmit(String text){
		textEdit.setText(text);
	}
	
	public String getTextEditSubmit(){
		return textEdit.getText().toString();
	}
	
	public void closeKeyBoard(){
		InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(textEdit.getWindowToken(), 0);
	}
	
	public void setErrorEditText(String error){
		textEdit.setError(error);
	}
	
	public final static boolean isValidEmail(CharSequence target) {
	    if (target == null) {
	        return false;
	    } else {
	        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
	    }
	}
	
	public boolean isValidEmail(){
		return RowEditTextSubmit.isValidEmail(textEdit.getText().toString());
	}
	
	public boolean isEmpty(){
		return textEdit.getText().toString().isEmpty();
	}

}