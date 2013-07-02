package com.appsolution.layouts;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.appsolution.logic.CalendarEvent;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EventsNewsAdapter extends PagerAdapter {

	private ArrayList<CalendarEvent> listaEventsOrNews;  
    private Activity context;  
    private boolean isHTML;
      
    public EventsNewsAdapter(Activity context) {  
        super();    
        this.context = context;
        isHTML=false;
    } 
    
    
    
    public boolean isHTML() {
		return isHTML;
	}



	public void setHTML(boolean isHTML) {
		this.isHTML = isHTML;
	}



	public ArrayList<CalendarEvent> getListaEventsOrNews() {
		return listaEventsOrNews;
	}
    
	public void setListaEventsOrNews(ArrayList<CalendarEvent> listaEventsOrNews) {
		this.listaEventsOrNews = listaEventsOrNews;
	}

    @Override  
    public void destroyItem(View collection, int position, Object view) {  
        ((ViewPager) collection).removeView((EventNewsContent) view);
   }
  
    @Override  
    public void finishUpdate(View arg0) {  
        // TODO Auto-generated method stub  
          
    }
    
    @Override
    public int getItemPosition(Object object) {
    	return POSITION_NONE;
    }
  
    @Override  
    public int getCount() {  
        return listaEventsOrNews.size();  
    }  
  
    @Override  
    public Object instantiateItem(View collection, int position) {        
    	EventNewsContent event=new EventNewsContent(context);
    	event.setTitleNewEvent(listaEventsOrNews.get(position).getTitle());
    	if(listaEventsOrNews.get(position).getBeginDate().length()>=10){
    		event.setDateEventNew(CalendarEvent.getFormatDate(0, listaEventsOrNews.get(position).getBeginDate().substring(0, 10),"yyyy/MM/dd"));
    	}
    	else{
    		event.setDateEventNew("");
    	}
    	if(listaEventsOrNews.get(position).getDescription()!=null){
    		if(isHTML){
    			event.setDescriptionHtmlNewEvent(listaEventsOrNews.get(position).getDescription());
    		}
    		else{
    			event.setDescriptionNewEvent(listaEventsOrNews.get(position).getDescription());
    		}
    	}
    	event.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_VIEW,  Uri.parse("http://omegafi.com"));
				context.startActivity(i);
			}
		});
        ((ViewPager) collection).addView(event);  
        return event;  
    }  
  
    @Override  
    public boolean isViewFromObject(View view, Object object) {  
         return view==((EventNewsContent)object);  
    }  
  
    @Override  
    public void restoreState(Parcelable arg0, ClassLoader arg1) {  
        // TODO Auto-generated method stub  
          
    }  
  
    @Override  
    public Parcelable saveState() {  
        // TODO Auto-generated method stub  
        return null;  
    }  
  
    @Override  
    public void startUpdate(View arg0) {  
        // TODO Auto-generated method stub  
          
    }

}
