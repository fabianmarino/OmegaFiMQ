package com.appsolution.layouts;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

public class PollAdapter extends PagerAdapter {

	private ArrayList<String[]> listaEventsOrNews;  
    private Context context;  
      
      
    public PollAdapter(Context context) {  
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
        ((ViewPager) collection).removeView((ViewSwitcher) view);  
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
    	PollOmegaFiContent poll=new PollOmegaFiContent(context);
    	poll.setTitleQuestion("Lorem ipsum dolor sit amet, consectetur adipisicing?");
		ArrayList<String> aux=new ArrayList<String>();
		for (int i = 0; i < 4; i++) {
			aux.add("Lorem ipsum dolor sit amet, consectetur adipisicing");
		}
		poll.addAnswersToPoll(aux);
        ((ViewPager) collection).addView(poll,0);  
        return poll;  
    }  
  
    @Override  
    public boolean isViewFromObject(View view, Object object) {  
         return view==((ViewSwitcher)object);  
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
