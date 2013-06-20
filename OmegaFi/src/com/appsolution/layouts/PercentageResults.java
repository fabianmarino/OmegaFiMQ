package com.appsolution.layouts;

import com.appsolution.omegafi.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PercentageResults extends LinearLayout{

	private Display display;
	private LinearLayout contenPercentageBar;
	private TextView textQuestionResult;
	private TextView percentageAnswer;
	private View barPercentage;
	
	public PercentageResults(Context context) {
		super(context);
		this.initialize();
	}
	
	public PercentageResults(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initialize();
		
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.PercentageResults);
		   
		String title=a.getString(R.styleable.PercentageResults_title_question_result);
		setTitleQuestionResult(title);
		
		int percentage = a.getInt(R.styleable.PercentageResults_answer_percentage, 0); 
		
		a.recycle();
	}

	public void initialize(){
		LayoutInflater inflate= (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflate.inflate(R.layout.percentage_result, this, true);
		
		WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		display = wm.getDefaultDisplay();
		
		contenPercentageBar=(LinearLayout)findViewById(R.id.contentPercentageBar);
		int widthMax=display.getWidth()-(int)(display.getWidth()*0.25);
		contenPercentageBar.getLayoutParams().width=widthMax;
		textQuestionResult=(TextView)findViewById(R.id.textQuestionResult);
		textQuestionResult.setTextColor(Color.BLACK);
		percentageAnswer=(TextView)findViewById(R.id.numberPercentageResult);
		barPercentage=(View)findViewById(R.id.percentageBarResult);
	}
	
	public void setTitleQuestionResult(String text){
		textQuestionResult.setText(text);
	}
	
	public void setPercentageAnswer(int percentage){
		float floatPercent=(float)percentage/(float)100;
		percentageAnswer.setText(percentage+" %");
		int widthMax=display.getWidth()-(int)(display.getWidth()*0.25);
		barPercentage.getLayoutParams().width=(int)(widthMax*floatPercent);
	}
	
	public void setBackgroundBar(int color){
		barPercentage.setBackgroundColor(color);
	}

}
