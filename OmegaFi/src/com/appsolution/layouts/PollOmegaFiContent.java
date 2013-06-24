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
//		for(String answer:answers){
//			RadioButton newAnswer=new RadioButton(getContext());
//			newAnswer.setTextColor(Color.GRAY);
//			newAnswer.setText(answer);
//			newAnswer.setTextSize(TypedValue.COMPLEX_UNIT_PX,
//					getContext().getResources().getDimensionPixelSize(R.dimen.text_answer_poll));
//			groupAnswers.addView(newAnswer);
//		}
		
		RowCheckGroup group=new RowCheckGroup();
		for (int i = 0; i < 4; i++) {
			RowCheckOmegaFi check=new RowCheckOmegaFi(getContext(), group);
			check.setTextSizeInformation(getResources().getDimensionPixelSize(R.dimen.text_12_notification));
			check.setNameInfo("Answer "+(i+1));
			check.setBackgroundColor(Color.TRANSPARENT);
			groupAnswers.addView(check);
		}
		
	}
	
	public void setTitleQuestion(String title){
		titleQuestion.setText(title);
	}
	
	public void addAnswersResultToPoll(){
		for (int i = 0; i < 4; i++) {
			PercentageResults percentaje=new PercentageResults(getContext());
			percentaje.setPercentageAnswer((int) (Math.random () * (100) + 1));
			percentaje.setBackgroundBar(this.getColorFromResource(i));
			linearResultsPercentage.addView(percentaje);
		}
	}
	
	private int getColorFromResource(int number){
		int resourceColor=super.getResources().getColor(R.color.gray_bar);
		switch (number) {
		case 0:
			resourceColor=super.getResources().getColor(R.color.green_bar);
			break;
		case 1:
			resourceColor=super.getResources().getColor(R.color.orange_bar);
			break;
		case 2:
			resourceColor=super.getResources().getColor(R.color.red_bar);
			break;
		case 3:
			resourceColor=super.getResources().getColor(R.color.blue_bar);
			break;

		default:
			break;
		}
		return resourceColor;
	}
	
}