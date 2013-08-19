package com.appsolution.layouts;

import java.util.ArrayList;
import com.appsolution.logic.CalendarEvent;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

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
    public int getItemPosition(Object object) {
    	return POSITION_NONE;
    }
  
    @Override  
    public int getCount() {  
        return listaEventsOrNews.size();  
    }  
  
    @Override  
    public Object instantiateItem(View collection, int position) {
    	final CalendarEvent newEvent=listaEventsOrNews.get(position);
    	EventNewsContent event=new EventNewsContent(context);
    	event.setTitleNewEvent(newEvent.getTitle());
    	if(newEvent.getBeginDate().length()>=10){
    		event.setDateEventNew(CalendarEvent.getFormatDate(0, newEvent.getBeginDate().substring(0, 10),"yyyy/MM/dd"));
    	}
    	else{
    		event.setDateEventNew("");
    	}
    	Log.d("description adapter", newEvent.getDescription());
    	if(newEvent.getDescription()!=null){
    		if(isHTML){
    			event.setDescriptionHtmlNewEvent(newEvent.getDescription());
    		}
    		else{
    			event.setDescriptionNewEvent(newEvent.getDescription());
    		}
    	}
    	if(newEvent.getLinkUrl()!=null){
	    	event.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent i = new Intent(Intent.ACTION_VIEW,  Uri.parse(newEvent.getLinkUrl()));
					context.startActivity(i);
				}
			});
    	}
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
