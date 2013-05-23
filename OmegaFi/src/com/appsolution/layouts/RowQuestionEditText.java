package com.appsolution.layouts;

import com.appsolution.omegafi.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RowQuestionEditText extends LinearLayout {

	private TextView questionRow;
	
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
		
		a.recycle();
	}
	
	private void initialize() {
		LayoutInflater inflate= (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflate.inflate(R.layout.row_question_edit_text, this, true);
		questionRow=(TextView)findViewById(R.id.textQuestionRow);
	}
	
	public void setQuestionRow(String question){
		questionRow.setText(question);
	}

}
