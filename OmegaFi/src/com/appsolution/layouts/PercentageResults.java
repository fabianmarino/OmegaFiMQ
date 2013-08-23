package com.appsolution.layouts;

import com.appsolution.omegafi.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
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
	private static final float REST_DISPLAY=0.27f; 
	
	public PercentageResults(Context context) {
		super(context);
		this.initialize();
	}
	
	public PercentageResults(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initialize();
		
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.PercentageResults);
		   
		String title=a.getString(R.styleable.PercentageResults_title_question_result);
		setTextQuestionResult(title); 
		
		a.recycle();
	}

	public void initialize(){
		LayoutInflater inflate= (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflate.inflate(R.layout.percentage_result, this, true);
		
		WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		display = wm.getDefaultDisplay();
		
		contenPercentageBar=(LinearLayout)findViewById(R.id.contentPercentageBar);
		int widthMax=display.getWidth()-(int)(display.getWidth()*REST_DISPLAY);
		contenPercentageBar.getLayoutParams().width=widthMax;
		textQuestionResult=(TextView)findViewById(R.id.textQuestionResult);
		textQuestionResult.setTextColor(Color.BLACK);
		percentageAnswer=(TextView)findViewById(R.id.numberPercentageResult);
		barPercentage=(View)findViewById(R.id.percentageBarResult);
	}
	
	public void setTextQuestionResult(String text){
		textQuestionResult.setText(text);
	}
	
	public void setPercentageAnswer(double percentage){
		float floatPercent=(float)percentage/(float)100;
		percentageAnswer.setText(percentage+"%");
		int widthMax=display.getWidth()-(int)(display.getWidth()*REST_DISPLAY);
		barPercentage.getLayoutParams().width=(int)(widthMax*floatPercent);
	}
	
	public void setBackgroundBar(int color){
		barPercentage.setBackgroundColor(color);
	}

}
