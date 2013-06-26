package com.appsolution.layouts;

import com.appsolution.omegafi.OmegaFiActivity;
import com.appsolution.omegafi.R;
import com.viewpagerindicator.TitlePageIndicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContentAnnouncement extends LinearLayout {
	
	private LinearLayout contentAll;
	private RowInformation rowTitleDate;
	private TextView descriptionAnnouncement;
	private TextView textSource;
	
	public ContentAnnouncement(Context context) {
		super(context);
		this.initialize();
	}

	public ContentAnnouncement(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initialize();
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ContentAnnouncement);
		
		String title=a.getString(R.styleable.ContentAnnouncement_title_announcement);
		setTitleAnnouncement(title);
		
		String date=a.getString(R.styleable.ContentAnnouncement_date_announcement);
		setDateAnnouncement(date);
		
		String description=a.getString(R.styleable.ContentAnnouncement_description_announcement);
		setDescriptionAnnouncement(description);
		
		boolean visibleSource=a.getBoolean(R.styleable.ContentAnnouncement_source_announcement_visible, true);
		setVisibleSourceAnnouncement(visibleSource);
		
		a.recycle();
		 
		 this.setPadding(10);
	}
	
	private void initialize() {
		LayoutInflater inflate= (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflate.inflate(R.layout.content_announcement, this, true);
		contentAll=(LinearLayout)findViewById(R.id.linearContentAnnouncements);
		rowTitleDate=(RowInformation)findViewById(R.id.titleNameDateAnnouncement);
		descriptionAnnouncement=(TextView)findViewById(R.id.descriptionAnnouncement);
		descriptionAnnouncement.setClickable(true);
		descriptionAnnouncement.setTypeface(OmegaFiActivity.getFont(getContext(), 3));
		textSource=(TextView)findViewById(R.id.textSourceAnnouncement);
		textSource.setTypeface(OmegaFiActivity.getFont(getContext(), 2));
	}
	
	public void setTitleAnnouncement(String title){
		rowTitleDate.setNameInfo(title);
	}
	
	public void setDateAnnouncement(String date){
		rowTitleDate.setValueInfo(date);
	}
	
	public void setDescriptionAnnouncement(String description){
		descriptionAnnouncement.setText(description);
	}
	
	public void setBackgroundNewAnnoncement(){
		contentAll.setBackgroundResource(R.drawable.background_white_gray);
		setPadding(10);
	}
	
	@Override
	public void setBackgroundResource(int resid) {
		contentAll.setBackgroundResource(resid);
		setPadding(10);
	}
	
	@Override
	public void setOnClickListener(OnClickListener l) {
		descriptionAnnouncement.setOnClickListener(l);
		rowTitleDate.setOnClickListener(l);
		super.setOnClickListener(l);
	}
	
	public void setVisibleSourceAnnouncement(boolean visible){
		if(visible){
			textSource.setVisibility(View.VISIBLE);
		}
		else{
			textSource.setVisibility(View.GONE);
		}
	}
	
	private void setPadding(int padding){
		  contentAll.setPadding(padding, padding, padding, padding);
	}
	
}