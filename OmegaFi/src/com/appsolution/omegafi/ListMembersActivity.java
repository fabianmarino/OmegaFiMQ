package com.appsolution.omegafi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.actionbarsherlock.widget.SearchView;
import com.appsolution.layouts.AlphabeticAdapter;
import com.appsolution.logic.SimpleMember;
import com.appsolution.services.ChaptersService;
import com.appsolution.services.Server;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ListMembersActivity extends OmegaFiActivity implements SearchView.OnQueryTextListener{

	private EditText search;
	private ListView listMembers;
	private AlphabeticAdapter membersAlpha;
	private SearchView mSearchView;
	private int idChapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_members);
		listMembers=(ListView)findViewById(R.id.listViewMembers);
		idChapter=getIntent().getExtras().getInt("id");
		chargeListMembers();
	}
	
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
		 
        com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.search_view_in_menu, menu);
        com.actionbarsherlock.view.MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) searchItem.getActionView();
        setupSearchView(searchItem);
	    return true;
	 }
	
	 private void setupSearchView(com.actionbarsherlock.view.MenuItem searchItem) {
		 
	        if (isAlwaysExpanded()) {
	            mSearchView.setIconifiedByDefault(false);
	        } else {
	            searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);
	        }
	        
	        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	        if (searchManager != null) {
	            List<SearchableInfo> searchables = searchManager.getSearchablesInGlobalSearch();
	            
	            SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
//	            for (SearchableInfo inf : searchables) {
//	                if (inf.getSuggestAuthority() != null
//	                        && inf.getSuggestAuthority().startsWith("applications")) {
//	                    info = inf;
//	                }
//	            }
	            mSearchView.setSearchableInfo(info);
	        }
	 
	        mSearchView.setOnQueryTextListener(this);
	    }
	
	@Override
	protected void optionsActionBar() {
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBarCustom.setTitle("MEMBER ROSTER");
		actionBar.setCustomView(actionBarCustom);
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
	
	 public boolean onQueryTextChange(String newText) {
	        membersAlpha.getFilter().filter(newText);
	        return false;
	    }
	 
	    public boolean onQueryTextSubmit(String query) {
	        return false;
	    }
	 
	    public boolean onClose() {
	        return false;
	    }
	 
	    protected boolean isAlwaysExpanded() {
	        return false;
	    }
	    
	    private void chargeListMembers(){
	    	AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>() {
				
	    		private int status=0;
	    		private ArrayList<SimpleMember> members; 
	    		
	    		@Override
	    		protected void onPreExecute() {
	    			startProgressDialog("Charging members", getResources().getString(R.string.please_wait));
	    		}
	    		
				@Override
				protected Boolean doInBackground(Void... params) {
					Object[] statusMembers=Server.getServer().getHome().getChapters().getListSimpleMembers(idChapter);
					status=(Integer)statusMembers[0];
					members=(ArrayList<SimpleMember>)statusMembers[1];
					Server.getServer().getHome().getProfile().updateProfileIfNecessary();
					return true;
				}
				
				@Override
				protected void onPostExecute(Boolean result) {
					stopProgressDialog();
					if(status==200){
						membersAlpha=new AlphabeticAdapter(ListMembersActivity.this,android.R.layout.simple_list_item_1, 
								ChaptersService.getNamesMembers(members),idChapter);
						listMembers.setAdapter(membersAlpha);
						refreshActivity();
					}
				}
			};
			task.execute();
	    }

}