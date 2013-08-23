package com.appsolution.layouts;

import java.util.ArrayList;

import com.appsolution.logic.Poll;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ViewSwitcher;

public class PollAdapter extends PagerAdapter {
  
    private Activity context;
    private ArrayList<Poll> polls;
      
      
    public PollAdapter(Activity context) {  
        super();    
        this.context = context;
    }
    
    
  
    public void setPolls(ArrayList<Poll> polls) {
		this.polls = polls;
	}



	@Override  
    public void destroyItem(View collection, int position, Object view) {  
        ((ViewPager) collection).removeView((ViewSwitcher) view);  
    }  
  
    @Override  
    public int getCount() {  
        return polls.size();  
    }  
  
    @Override  
    public Object instantiateItem(View collection, int position) {
    	Poll actualPoll=polls.get(position);
    	PollOmegaFiContent poll=new PollOmegaFiContent(context);
    	poll.completeLayoutFromPoll(actualPoll);
        ((ViewPager) collection).addView(poll);  
        return poll;  
    }  
  
    @Override  
    public boolean isViewFromObject(View view, Object object) {  
         return view==((ViewSwitcher)object);  
    }  
  

}
