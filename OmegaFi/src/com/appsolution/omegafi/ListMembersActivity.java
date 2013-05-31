package com.appsolution.omegafi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

public class ListMembersActivity extends OmegaFiActivity {

	private ListView listMembers;
	private AlphabeticAdapter members;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_members);
		listMembers=(ListView)findViewById(R.id.listViewMembers);
		members=new AlphabeticAdapter(this,android.R.layout.simple_list_item_1, getArrayTest());
		listMembers.setAdapter(members);
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setTitle("Members Roster");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
	}
	
	private List<String> getArrayTest(){
		ArrayList<String> stringList=new ArrayList<String>();
		stringList.add("aback");  
        stringList.add("abash");  
        stringList.add("abbey");  
        stringList.add("abhor");  
        stringList.add("abide");  
        stringList.add("abuse");  
        stringList.add("candidate");  
        stringList.add("capture");  
        stringList.add("careful");  
        stringList.add("catch");  
        stringList.add("cause");  
        stringList.add("celebrate");  
        stringList.add("forever");  
        stringList.add("fable");  
        stringList.add("fidelity");  
        stringList.add("fox");  
        stringList.add("funny");  
        stringList.add("fail");  
        stringList.add("jail");  
        stringList.add("jade");  
        stringList.add("jailor");  
        stringList.add("january");  
        stringList.add("jasmine");  
        stringList.add("jazz");  
        stringList.add("zero");  
        stringList.add("zoo");  
        stringList.add("zeus");  
        stringList.add("zebra");  
        stringList.add("zest");  
        stringList.add("zing"); 
        stringList.add("celebrate");  
        stringList.add("forever");  
        stringList.add("fable");  
        stringList.add("fidelity");
        String[] aux=Arrays.copyOf(stringList.toArray(), stringList.size(), String[].class);
        Arrays.sort(aux);
		return (List<String>)Arrays.asList(aux);
	}

}