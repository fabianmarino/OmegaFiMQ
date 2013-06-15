package com.appsolution.layouts;

import com.appsolution.omegafi.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EventNewsContent extends LinearLayout {

	private LinearLayout contentAll;
	private TextView titleNewEvent;
	private TextView dateNewEvent;
	private TextView descriptionNewEvent;
	
	public EventNewsContent(Context context) {
		super(context);
		this.initialize();
	}
	
	public EventNewsContent(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initialize();
		
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.EventNewsContent);
		   boolean isEvent = a.getBoolean(R.styleable.EventNewsContent_is_event, false);	   
		   
		   String titleNewEvent=a.getString(R.styleable.EventNewsContent_title_new_event);
		   setTitleNewEvent(titleNewEvent);
		   
		   String date=a.getString(R.styleable.EventNewsContent_date_new_event);
		   setDateEventNew(date);
		   
		   String description=a.getString(R.styleable.EventNewsContent_description_new_event);
		   setDescriptionNewEvent(description);
		   
		   int padding=a.getDimensionPixelSize(R.styleable.EventNewsContent_padding_new_event, contentAll.getPaddingLeft());
		   setPadding(padding);
		   
		   boolean borderBottom=a.getBoolean(R.styleable.EventNewsContent_put_border_new_event, false);
		   setBorderBottom(borderBottom);
		    a.recycle();
	}
	
	private void initialize(){
		LayoutInflater inflate= (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflate.inflate(R.layout.events_news_content, this, true);
		titleNewEvent=(TextView)findViewById(R.id.titleEventNewOmegaFi);
		dateNewEvent=(TextView)findViewById(R.id.dateEventNewOmegaFi);
		descriptionNewEvent=(TextView)findViewById(R.id.descriptionNewOrEvent);
		contentAll=(LinearLayout)findViewById(R.id.linearContentNewsEvents);
	}
	
	public void setTitleNewEvent(String title){
		titleNewEvent.setText(title);
	}
	
	public void setDateEventNew(String details){
		dateNewEvent.setText(details);
	}
	
	public void setDescriptionNewEvent(String description){
		descriptionNewEvent.setText(description);
	}
	
	public void setBorderBottom(boolean put){
		if(put){
			contentAll.setBackgroundResource(R.drawable.border_bottom);
		}
		else{
			contentAll.setBackgroundColor(Color.WHITE);
		}
	}
	
	public void setPadding(int padding){
		  contentAll.setPadding(padding, padding, padding, padding);
	}

}
