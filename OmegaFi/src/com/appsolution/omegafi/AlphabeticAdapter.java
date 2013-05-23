package com.appsolution.omegafi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AlphabetIndexer;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class AlphabeticAdapter extends ArrayAdapter<String> implements
		SectionIndexer {
	 private static final int TYPE_HEADER = 1;
	    private static final int TYPE_NORMAL = 0;
	 
	    private static final int TYPE_COUNT = 2;
	 
	    private AlphabetIndexer indexer;
	 
	    private int[] usedSectionNumbers;
	 
	    private Map<Integer, Integer> sectionToOffset;
	    private Map<Integer, Integer> sectionToPosition;
	 
	    private Activity activity;
	    public AlphabeticAdapter(Activity context, int layout, Cursor c,
	            String[] from) {
	        super(context, layout, from);
	         this.activity=context;
	        indexer = new AlphabetIndexer(c, 0, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
	        sectionToPosition = new TreeMap<Integer, Integer>();
	        sectionToOffset = new HashMap<Integer, Integer>();
	 
	        final int count = super.getCount();
	         
	        int i;
	        for (i = count - 1 ; i >= 0; i--){
	            sectionToPosition.put(indexer.getSectionForPosition(i), i);
	        }
	 
	        i = 0;
	        usedSectionNumbers = new int[sectionToPosition.keySet().size()];
	         
	        for (Integer section : sectionToPosition.keySet()){
	            sectionToOffset.put(section, i);
	            usedSectionNumbers[i] = section;
	            i++;
	        }
	 
	        for(Integer section: sectionToPosition.keySet()){
	            sectionToPosition.put(section, sectionToPosition.get(section) + sectionToOffset.get(section));
	        }
	    }
	 
	    @Override
	    public int getCount() {
	        if (super.getCount() != 0){
	            return super.getCount() + usedSectionNumbers.length;
	        }
	         
	        return 0;
	    }
	     
//	    @Override
//	    public Object getItem(int position) {
//	        if (getItemViewType(position) == TYPE_NORMAL){//we define this function later
//	            return super.getItem(position - sectionToOffset.get(getSectionForPosition(position)) - 1);
//	        }
//	 
//	        return null;
//	    }
	 
	    @Override
	    public int getPositionForSection(int section) {
	        if (! sectionToOffset.containsKey(section)){ 
	            int i = 0;
	            int maxLength = usedSectionNumbers.length;
	             
	            while (i < maxLength && section > usedSectionNumbers[i]){
	                i++;
	            }
	            if (i == maxLength) return getCount();
	 
	            return indexer.getPositionForSection(usedSectionNumbers[i]) + sectionToOffset.get(usedSectionNumbers[i]);
	        }
	 
	        return indexer.getPositionForSection(section) + sectionToOffset.get(section);
	    }
	 
	    @Override
	    public int getSectionForPosition(int position) {
	        int i = 0;      
	        int maxLength = usedSectionNumbers.length;
	 
	        while (i < maxLength && position >= sectionToPosition.get(usedSectionNumbers[i])){
	            i++;
	        }
	        return usedSectionNumbers[i-1];
	    }
	 
	    @Override
	    public Object[] getSections() {
	        return indexer.getSections();
	    }
	 
	    //nothing much to this: headers have positions that the sectionIndexer manages.
	    @Override
	    public int getItemViewType(int position) {
	        if (position == getPositionForSection(getSectionForPosition(position))){
	            return TYPE_HEADER;
	        } return TYPE_NORMAL;
	    }
	 
	    @Override
	    public int getViewTypeCount() {
	        return TYPE_COUNT;
	    }
	 
	    //return the header view, if it's in a section header position
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        final int type = getItemViewType(position);
	        if (type == TYPE_HEADER){
	            if (convertView == null){
	                convertView =activity. getLayoutInflater().inflate(R.layout.list_view_member, parent, false); 
	            }
	            ((TextView)convertView.findViewById(R.id.textNameMember)).setText((String)getSections()[getSectionForPosition(position)]);
	            return convertView;
	        }
	        return super.getView(position - sectionToOffset.get(getSectionForPosition(position)) - 1, convertView, parent); 
	    }
	 
	 
	    //these two methods just disable the headers
	    @Override
	    public boolean areAllItemsEnabled() {
	        return false;
	    }
	 
	    @Override
	    public boolean isEnabled(int position) {
	        if (getItemViewType(position) == TYPE_HEADER){
	            return false;
	        }
	        return true;
	    }    
}	
