package com.appsolution.layouts;

import java.util.ArrayList;

import com.appsolution.interfaces.OnRowCheckListener;
import com.appsolution.logic.AnswerPoll;
import com.appsolution.logic.Poll;
import com.appsolution.omegafi.OmegaFiActivity;
import com.appsolution.omegafi.R;
import com.appsolution.services.Server;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class PollOmegaFiContent extends ViewSwitcher{

	private TextView titleQuestion;
	private TextView titleQuestionR;
	private LinearLayout groupAnswers;
	private Button buttonSubmit;
	private ViewSwitcher viewSwitcher;
	private LinearLayout linearResultsPercentage;
	private Poll poll;
	private Activity parent;
	private RowCheckGroup group;
	
	public PollOmegaFiContent(Activity context) {
		super(context);
		parent=context;
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
		titleQuestionR=(TextView)findViewById(R.id.titlePollResult);
		groupAnswers=(LinearLayout)findViewById(R.id.contentAnswersPoll);	
		linearResultsPercentage=(LinearLayout)findViewById(R.id.contentPollResults);
		buttonSubmit=(Button)findViewById(R.id.buttonSubmitPoll);
		buttonSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(group.getIndexSelected()<0)
					OmegaFiActivity.showAlertMessage("Choose one answer choice", parent);
				else
					voteAnswerPoll(poll.getId(), poll.getAnswers().get(group.getIndexSelected()).getId());		
			}
		});
		
		
	}
	
	private void voteAnswerPoll(final int idPoll, final int idAnswer){
		AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>() {
			
			private ProgressDialog progressDiag=null;
			private int status=0;
			
			@Override
			protected void onPreExecute() {
				progressDiag=new ProgressDialog(parent);
				progressDiag.setTitle("Voting...");
				progressDiag.setMessage(getResources().getString(R.string.please_wait));
				progressDiag.setCancelable(false);
				progressDiag.setIndeterminate(true);
				progressDiag.show();
			}
			
			@Override
			protected Boolean doInBackground(Void... params) {
				Object[] responseVote=Server.getServer().getHome().getPolls().voteInPollAnswer(idPoll, idAnswer);
				status=(Integer)responseVote[0];
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				if(status==200||status==201){
					votingSuccessfully(group.getIndexSelected());
				}
				else{
					group.unCheckedAll();
					OmegaFiActivity.showAlertMessage("Error to vote poll: Error "+status, parent);
				}
				progressDiag.dismiss();
				progressDiag=null;	
			}
		};
		task.execute();
	}
	
	private void votingSuccessfully(int indexAnswer){
		poll.votingAnswer(indexAnswer);
		setAnswersResultToPoll(poll);
		viewSwitcher.showNext();
	}
	
	
	public void addAnswersToPoll(ArrayList<String> answers){
		titleQuestionR.setText(titleQuestion.getText());
		group=new RowCheckGroup();
		for (int i=0;i<answers.size();i++) {
			LinearLayout lineCheck=new LinearLayout(getContext());
			lineCheck.setLayoutParams(new LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 
					android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
			lineCheck.setGravity(Gravity.CENTER_HORIZONTAL);
			lineCheck.setOrientation(LinearLayout.VERTICAL);
			int padding10=getResources().getDimensionPixelSize(R.dimen.padding_8dp);
			lineCheck.setPadding(padding10, 0, padding10, 0);
			RowCheckOmegaFi check=new RowCheckOmegaFi(getContext(), group);
			check.setTextSizeInformation(getResources().getDimensionPixelSize(R.dimen.text_12_notification));
			check.setNameInfo(answers.get(i));
			check.getTextNameInfo().setWidth(OmegaFiActivity.getWidthPercentageDisplay(parent, 0.7f));
			check.setBackgroundColor(Color.TRANSPARENT);
			int padding=getResources().getDimensionPixelSize(R.dimen.padding_6dp);
			check.setPaddingRow(0, padding, 0, padding);
			check.setMarginRightCheckBox(0);
			View line=new View(getContext());
			line.setBackgroundColor(this.getResources().getColor(R.color.gray_font_welcome));
			LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
			params.rightMargin=getResources().getDimensionPixelSize(R.dimen.margin_9dp_right_poll);
			line.setLayoutParams(params);
			lineCheck.addView(check);
			if(i<(answers.size()-1)){
				lineCheck.addView(line);
			}
			groupAnswers.addView(lineCheck);
		}
		
	}
	
	public void setTitleQuestion(String title){
		titleQuestion.setText(title);
	}
	
	public void setAnswersResultToPoll(Poll poll){
		linearResultsPercentage.removeAllViews();
		ArrayList<AnswerPoll> answers=poll.getAnswers();
		for (int i=0;i<answers.size();i++) {
			PercentageResults percentaje=new PercentageResults(getContext());
			percentaje.setPercentageAnswer(poll.getPercentajeAnswer(i));
			percentaje.setLayoutParams(new LinearLayout.LayoutParams(OmegaFiActivity.getWidthPercentageDisplay(parent, 0.85f), android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
			percentaje.setTextQuestionResult(answers.get(i).getAnswer());
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
	
	public void completeLayoutFromPoll(Poll poll){
		this.poll=poll;
		setTitleQuestion(poll.getPollQuestion());
		addAnswersToPoll(poll.getStringAnswers());
		this.setAnswersResultToPoll(poll);
		if(poll.isVoted()){
			viewSwitcher.showNext();
		}
	}
	
}