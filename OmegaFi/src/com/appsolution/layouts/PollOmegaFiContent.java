package com.appsolution.layouts;

import java.util.ArrayList;

import com.appsolution.omegafi.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class PollOmegaFiContent extends ViewSwitcher{

	private TextView titleQuestion;
	private RadioGroup groupAnswers;
	private Button buttonSubmit;
	private Button buttonVote;
	private ViewSwitcher viewSwitcher;
	private LinearLayout linearResultsPercentage;
	
	public PollOmegaFiContent(Context context) {
		super(context);
		this.initialize();
	}
	
	public PollOmegaFiContent(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initialize();
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.PollOmegaFiContent);
		   
		String title=a.getString(R.styleable.PollOmegaFiContent_title_question);
		setTitleQuestion(title);
		
		a.recycle();
	}
	
	private void initialize(){
		LayoutInflater inflate= (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflate.inflate(R.layout.poll_omega_fi_content, this, true);
		viewSwitcher=(ViewSwitcher)findViewById(R.id.pollSwitcher);
		titleQuestion=(TextView)findViewById(R.id.titleQuestionPoll);
		groupAnswers=(RadioGroup)findViewById(R.id.contentAnswersPoll);
		
		linearResultsPercentage=(LinearLayout)findViewById(R.id.contentPollResults);
		this.addAnswersResultToPoll();
		buttonSubmit=(Button)findViewById(R.id.buttonSubmitPoll);
		buttonSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				viewSwitcher.showNext();		
			}
		});
		
		buttonVote=(Button)findViewById(R.id.buttonVote);
		buttonVote.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				viewSwitcher.showNext();
			}
		});
		
	}
	
	public void addAnswersToPoll(ArrayList<String> answers){
		for(String answer:answers){
			RadioButton newAnswer=new RadioButton(getContext());
			newAnswer.setTextColor(Color.GRAY);
			newAnswer.setText(answer);
			newAnswer.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
			groupAnswers.addView(newAnswer);
		}
	}
	
	public void setTitleQuestion(String title){
		titleQuestion.setText(title);
	}
	
	public void addAnswersResultToPoll(){
		for (int i = 0; i < 4; i++) {
			PercentageResults percentaje=new PercentageResults(getContext());
			percentaje.setPercentageAnswer((int) (Math.random () * (100) + 1));
			linearResultsPercentage.addView(percentaje);
		}
	}
	
}