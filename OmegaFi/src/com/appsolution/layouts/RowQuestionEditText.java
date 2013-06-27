package com.appsolution.layouts;

import com.appsolution.omegafi.OmegaFiActivity;
import com.appsolution.omegafi.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RowQuestionEditText extends LinearLayout {

	private TextView questionRow;
	private EditText textResult;
	
	public RowQuestionEditText(Context context){
		super(context);
		this.initialize();
	}
	
	public RowQuestionEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initialize();
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.RowQuestionEditText);
		
		String question=a.getString(R.styleable.RowQuestionEditText_text_question_row);
		setQuestionRow(question);
		
		int input=a.getInteger(R.styleable.RowQuestionEditText_input_type_question, 1);
		RowEditTextOmegaFi.setTypeInputTextFromAttrs(input, textResult);
		
		Log.d("Type Question Edit", textResult.getInputType()+" type");
		
		a.recycle();
	}
	
	private void initialize() {
		LayoutInflater inflate= (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflate.inflate(R.layout.row_question_edit_text, this, true);
		questionRow=(TextView)findViewById(R.id.textQuestionRow);
		questionRow.setTypeface(OmegaFiActivity.getFont(getContext(), 3));
		textResult=(EditText)findViewById(R.id.editTextAnswer);
		textResult.setTypeface(OmegaFiActivity.getFont(getContext(), 3));
	}
	
	public void setQuestionRow(String question){
		questionRow.setText(question);
	}
	
	public void setInputType(int type){
		textResult.setInputType(type);
	}
	
	public String getTextQuestionEdit(){
		return textResult.getText().toString();
	}

	public void setError(CharSequence error) {
		textResult.setError(error);
	}
	
	

}
