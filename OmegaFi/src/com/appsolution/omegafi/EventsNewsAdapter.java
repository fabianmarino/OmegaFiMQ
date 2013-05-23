package com.appsolution.omegafi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.appsolution.layouts.EventNewsContent;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EventsNewsAdapter extends PagerAdapter {

	private ArrayList<String[]> listaEventsOrNews;  
    private Context context;  
      
      
    public EventsNewsAdapter(Context context) {  
        super();    
        this.context = context;
        listaEventsOrNews=new ArrayList<String[]>();
        this.initializeNewsEventsTest();
    }  
    
    /**
     * Método de prueba para llenar noticias o eventos con contenido falso 
     */
    private void initializeNewsEventsTest(){
    	for (int i = 0; i <6; i++) {	
    		if(i%2==0){
    			String title="New "+(i+1);
    			String[] new1={title,null,"Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod" +
    					" tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud " +
    					"exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."};
    			listaEventsOrNews.add(new1);
    		}
    		else{
    			String title="Event "+(i+1);
    			String[] new1={title,Calendar.getInstance().getTime().toLocaleString(),"Lorem ipsum dolor sit amet, consectetur " +
    					"adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
    					"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea" +
    					" commodo consequat."};
    			listaEventsOrNews.add(new1);
    		}
		}
    }
  
    @Override  
    public void destroyItem(View collection, int position, Object view) {  
        ((ViewPager) collection).removeView((LinearLayout) view);  
    }  
  
    @Override  
    public void finishUpdate(View arg0) {  
        // TODO Auto-generated method stub  
          
    }  
  
    @Override  
    public int getCount() {  
        return listaEventsOrNews.size();  
    }  
  
    @Override  
    public Object instantiateItem(View collection, int position) {        
        /*LinearLayout linearLayout = new LinearLayout(context);  
        linearLayout.setOrientation(LinearLayout.VERTICAL);  
          
        final TextView textView = new TextView(context);  
        textView.setText("Position: " + position);  
          
        final TextView  image = new TextView(context);  
          image.setText(urls[position]);
          
          
        linearLayout.addView(textView);  
        linearLayout.addView(image);  
          */
    	EventNewsContent event=new EventNewsContent(context);
    	event.setTitleNewEvent(listaEventsOrNews.get(position)[0]);
    	event.setDateEventNew("DD / MM / YYYY");
    	event.setDescriptionNewEvent(listaEventsOrNews.get(position)[2]);
    	
        ((ViewPager) collection).addView(event,0);  
        return event;  
    }  
  
    @Override  
    public boolean isViewFromObject(View view, Object object) {  
         return view==((LinearLayout)object);  
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
