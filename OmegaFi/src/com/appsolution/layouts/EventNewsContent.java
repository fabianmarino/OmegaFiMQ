package com.appsolution.layouts;

import com.appsolution.omegafi.OmegaFiActivity;
import com.appsolution.omegafi.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Html;
import android.text.Layout;
import android.text.Spannable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EventNewsContent extends LinearLayout {

	private LinearLayout contentAll;
	private TextView titleNewEvent;
	private TextView dateNewEvent;
	private TextView descriptionNewEvent;
	private int linesTitle=0;
	private int descriptionLines=0;
	
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
		titleNewEvent.setTypeface(OmegaFiActivity.getFont(getContext(), 1));
		titleNewEvent.setClickable(true);
		dateNewEvent=(TextView)findViewById(R.id.dateEventNewOmegaFi);
		dateNewEvent.setTypeface(OmegaFiActivity.getFont(getContext(), 3));
		dateNewEvent.setClickable(true);
		dateNewEvent.setVisibility(View.GONE);
		descriptionNewEvent=(TextView)findViewById(R.id.descriptionNewOrEvent);
		descriptionNewEvent.setClickable(true);
		descriptionNewEvent.setTypeface(OmegaFiActivity.getFont(getContext(), 3));
		contentAll=(LinearLayout)findViewById(R.id.linearContentNewsEvents);
	}
	
	public void setTitleNewEvent(String title){
		titleNewEvent.setText(title);
	}
	
	public void setDateEventNew(String details){
		if(details!=null){
			if(!details.isEmpty()){
				dateNewEvent.setText(details);
				dateNewEvent.setVisibility(View.VISIBLE);
				}
		}
	}
	
	public void setDescriptionNewEvent(String description){
		Log.d("description", description);
		descriptionNewEvent.setText(description);
		this.truncateNewOrEvent();
	}
	
	public void setDescriptionHtmlComplete(String description){
		descriptionNewEvent.setText(Html.fromHtml(description));
	}
	
	public void setDescriptionHtmlNewEvent(String description){
		descriptionNewEvent.setText(Html.fromHtml(description));
		this.truncateNewOrEvent();
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
	
	@Override
	public void setOnClickListener(OnClickListener l) {
		descriptionNewEvent.setOnClickListener(l);
		titleNewEvent.setOnClickListener(l);
		dateNewEvent.setOnClickListener(l);
		super.setOnClickListener(l);
	}
	
	private void truncateNewOrEvent(){
		Log.d("longitud", descriptionNewEvent.getText().toString());
		if(descriptionNewEvent.getText().toString().length()>10){
			ViewTreeObserver vto = this.titleNewEvent.getViewTreeObserver();
	        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
	
	            public void onGlobalLayout() {
	                ViewTreeObserver obs = titleNewEvent.getViewTreeObserver();
	                obs.removeGlobalOnLayoutListener(this);
	                linesTitle=titleNewEvent.getLineCount();
	                ViewTreeObserver vto2 = descriptionNewEvent.getViewTreeObserver();
	                vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
	
	                    public void onGlobalLayout() {
	                        ViewTreeObserver obs = descriptionNewEvent.getViewTreeObserver();
	                        obs.removeGlobalOnLayoutListener(this);
	                        int linesDescription=4;//-(linesTitle-1);
//	                        if(linesTitle==2&&dateNewEvent.getVisibility()==View.VISIBLE){
//	                        	linesDescription--;
//	                        }
//	                		if(dateNewEvent.getVisibility()==View.GONE&&linesTitle==1){
//	                			linesDescription++;
//	                		}
	                        descriptionLines=descriptionNewEvent.getLineCount();
	                        Layout layout = descriptionNewEvent.getLayout();
	                        String text = descriptionNewEvent.getText().toString();
	                        int start=0;
	                        int end;
	                        StringBuilder textDescription=new StringBuilder();
	                        for (int i=0; i<descriptionNewEvent.getLineCount()&&i<linesDescription; i++) {
	                            end = layout.getLineEnd(i);
	                            textDescription.append(text.substring(start,end));
	                            start = end;
	                        }
	                    	if(textDescription.charAt(textDescription.length()-1)!='.'){
	                    		textDescription.append("...");
	                        }
                        
	                        descriptionNewEvent.setText(Html.fromHtml(textDescription.toString()));
	                    }
	                });
	
	            }
	        });
		}
	}

}
