package com.appsolution.omegafi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.actionbarsherlock.widget.SearchView;
import com.appsolution.layouts.AlphabeticAdapter;
import com.appsolution.logic.SimpleMember;
import com.appsolution.services.ChaptersService;
import com.appsolution.services.Server;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

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
	    			startProgressDialog("Loading Members...", getResources().getString(R.string.please_wait));
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
					if(Server.isStatusOk(status)){
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