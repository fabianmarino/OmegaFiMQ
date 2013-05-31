package com.appsolution.omegafi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class AlphabeticAdapter extends ArrayAdapter<String> implements
		SectionIndexer {
	  	private HashMap<String, Integer> alphaIndexer;
	    private String[] sections;
	    private Activity activity;

	    public AlphabeticAdapter(Activity c, int resource, List<String> data)
	    {
	        super(c, resource, data);
	        this.activity=c;
	        alphaIndexer = new HashMap<String, Integer>();
	        for (int i = 0; i < data.size(); i++)
	        {
	            String s = data.get(i).substring(0, 1).toUpperCase();
	            if (!alphaIndexer.containsKey(s))
	                alphaIndexer.put(s, i);
	        }

	        Set<String> sectionLetters = alphaIndexer.keySet();
	        ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);
	        Collections.sort(sectionList);
	        sections = new String[sectionList.size()];
	        for (int i = 0; i < sectionList.size(); i++)
	            sections[i] = sectionList.get(i);   
	    }

	    public int getPositionForSection(int section)
	    {   
	        return alphaIndexer.get(sections[section]);
	    }

	    public int getSectionForPosition(int position)
	    {
	        return 1;
	    }

	    public Object[] getSections()
	    {
	        return sections;
	    }   
	    
	    private void setSection(LinearLayout header, String label) {  
	        TextView text = new TextView(activity);  
	        header.setBackgroundColor(0xffaabbcc);  
	        text.setTextColor(Color.WHITE);  
	        text.setText(label.substring(0, 1).toUpperCase());  
	        text.setTextSize(20);  
	        text.setPadding(5, 0, 0, 0);  
	        text.setGravity(Gravity.CENTER_VERTICAL);  
	        header.addView(text);  
	    }
	    
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	    	LayoutInflater inflate = activity.getLayoutInflater();  
	        View view = (View) inflate.inflate(R.layout.list_view_member, null);  
	        LinearLayout header = (LinearLayout) view.findViewById(R.id.section);  
	        String label = getItem(position);  
	        char firstChar = label.toUpperCase().charAt(0);  
	        if (position == 0) {  
	            setSection(header, label);  
	        } else {  
	            String preLabel = getItem(position - 1);  
	            char preFirstChar = preLabel.toUpperCase().charAt(0);  
	            if (firstChar != preFirstChar) {  
	                setSection(header, label);  
	            } else {  
	                header.setVisibility(View.GONE);  
	            }  
	        }  
	        TextView textView = (TextView) view.findViewById(R.id.textNameMember);
	        textView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					getFilter().filter("F");					
				}
			});
	        textView.setText(label);
	        return view;
	    }
}