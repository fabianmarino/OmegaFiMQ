package com.appsolution.omegafi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.appsolution.layouts.DetailsOfficer;
import com.appsolution.layouts.UserContactLayout;
import com.appsolution.logic.SimpleMember;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class AlphabeticAdapter extends ArrayAdapter<String> implements SectionIndexer {
	  	private HashMap<String, Integer> alphaIndexer;
	    private String[] sections;
	    private Activity activity;
	    private int idChapter=-1;

	    public AlphabeticAdapter(Activity c, int resource, List<String> data, int idChapter)
	    {
	        super(c, resource, data);
	        this.idChapter=idChapter;
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
	        for (int i = 0; i < sectionList.size(); i++){
	            sections[i] = sectionList.get(i);   
	        }
	    }

	    public int getPositionForSection(int section)
	    {   
	        return alphaIndexer.get(sections[section]);
	    }

	    public int getSectionForPosition(int position)
	    {
	        return position;
	    }

	    public Object[] getSections()
	    {
	        return sections;
	    }   
	    
	    private void setSection(LinearLayout header, String label) {
	    	header.removeAllViews();
	        TextView text = new TextView(activity);    
	        text.setTextColor(Color.BLACK);  
	        text.setText(label.substring(0, 1).toUpperCase());  
	        text.setTextSize(20);
	        text.setTypeface(OmegaFiActivity.getFont(activity, 2));
	        text.setPadding(0, 10, 0, 0);  
	        text.setGravity(Gravity.CENTER_VERTICAL);  
	        header.addView(text);  
	    }
	    
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	    	if(convertView==null){
	    		Log.d("position", position+"");
		    	LayoutInflater inflate = activity.getLayoutInflater();  
		        convertView = (View) inflate.inflate(R.layout.list_view_member, null);  
	    		UserContactLayout userMember = (UserContactLayout) convertView.findViewById(R.id.userMemberLayout);
		        userMember.chargeImageTest();
		        userMember.setBackgroundColor(Color.WHITE);
		        userMember.setFontColor(Color.BLACK);
		        userMember.setBlackArrow();
				final String[] memberString=getItem(position).split("¿");
				Log.d("itemc", getItem(position));
				Log.d("item", memberString[0]);
				LinearLayout header = (LinearLayout) convertView.findViewById(R.id.section);  
		        String label=memberString[0];
		        char firstChar = label.toUpperCase().charAt(0);  
		        if (position == 0) {  
		        	header.setVisibility(View.VISIBLE);
		            setSection(header, label);  
		        } else {
		        	String[] memberStrAnt=getItem(position-1).split("¿");
		            String preLabel = memberStrAnt[0];
		            char preFirstChar = preLabel.toUpperCase().charAt(0);
		            Log.d("char prechar", firstChar+", "+preFirstChar);
		            if (firstChar != preFirstChar) {
		            	header.setVisibility(View.VISIBLE);
		                setSection(header, label);  
		            } else {  
		                header.setVisibility(View.GONE);  
		            }  
		        }  
				userMember.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View member) {
						Intent memberDetail=new Intent(activity, OfficerMemberDetailActivity.class);
						memberDetail.putExtra("idc", idChapter);
						memberDetail.putExtra("idm", Integer.parseInt(memberString[4]));
						activity.startActivity(memberDetail);
					}
				});
		       userMember.setNameUserProfile(memberString[0]);
		       userMember.setSubTitleProfile(memberString[1]);
		       userMember.chargeImageFromUrlAsync(memberString[2], memberString[3]);
		        
	    	}
	    	else{
	    		UserContactLayout userMember = (UserContactLayout) convertView.findViewById(R.id.userMemberLayout);
		        userMember.chargeImageTest();
		        userMember.setBackgroundColor(Color.WHITE);
		        userMember.setFontColor(Color.BLACK);
		        userMember.setBlackArrow();
		        final String[] memberString=getItem(position).split("¿");
		        Log.d("itemc", getItem(position));
		        Log.d("item", memberString[0]);
				LinearLayout header = (LinearLayout) convertView.findViewById(R.id.section);  
		        String label = memberString[0];
		        char firstChar = label.toUpperCase().charAt(0);  
		        if (position == 0) {  
		        	header.setVisibility(View.VISIBLE);
		            setSection(header, label);  
		        } else {  
		        	String[] memberStrAnt=getItem(position-1).split("¿");
		            String preLabel = memberStrAnt[0];
		            char preFirstChar = preLabel.toUpperCase().charAt(0);
		            Log.d("char prechar", firstChar+", "+preFirstChar);
		            if (firstChar != preFirstChar) {
		            	header.setVisibility(View.VISIBLE);
		                setSection(header, label);  
		            } else {  
		                header.setVisibility(View.GONE);  
		            }  
		        }
				userMember.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View member) {
						Intent memberDetail=new Intent(activity, OfficerMemberDetailActivity.class);
						memberDetail.putExtra("idc", idChapter);
						memberDetail.putExtra("idm", Integer.parseInt(memberString[4]));
						activity.startActivity(memberDetail);
					}
				});
					userMember.setNameUserProfile(memberString[0]);
			       userMember.setSubTitleProfile(memberString[1]);
			       userMember.chargeImageFromUrlAsync(memberString[2], memberString[3]);
	    	}
	        return convertView;
	    }
}