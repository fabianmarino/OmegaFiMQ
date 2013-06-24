package com.appsolution.omegafi;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.appsolution.layouts.AccountLayout;
import com.appsolution.layouts.DetailsOfficer;
import com.appsolution.layouts.DialogSelectableOF;
import com.appsolution.layouts.EventsNewsAdapter;
import com.appsolution.layouts.ImageAdapter;
import com.appsolution.layouts.PollAdapter;
import com.appsolution.layouts.PollOmegaFiContent;
import com.appsolution.layouts.RowAnnouncement;
import com.appsolution.layouts.RowInformation;
import com.appsolution.layouts.SectionOmegaFi;
import com.appsolution.logic.Server;
import com.viewpagerindicator.CirclePageIndicator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class HomeActivity extends OmegaFiActivity {
	
	private SectionOmegaFi sectionAccountUser;
	
	private SectionOmegaFi sectionChapterDirectory;
	private DetailsOfficer detailsOffice;
	
	private Gallery listPhotos;
	private SectionOmegaFi sectionEvents;
	private ViewPager paginator;
	private PagerAdapter adapterPager;
	
	private SectionOmegaFi sectionPoll;
	
	private PollOmegaFiContent contentPoll;
	private SectionOmegaFi sectionNews;
	
	private Bundle bundleHome;
	
	private JSONObject jsonAccounts;
	private JSONObject jsonChapters;
	
	private LinearLayout linearAccounts;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		linearAccounts=(LinearLayout)findViewById(R.id.linearChargeAccounts);
		sectionChapterDirectory=(SectionOmegaFi)findViewById(R.id.sectionChapterDirectory);
		sectionChapterDirectory.setOnClickTitleListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent memberRosters=new Intent(getApplicationContext(), ListMembersActivity.class);
				startActivity(memberRosters);
				
			}
		});
		detailsOffice=new DetailsOfficer(this);
		detailsOffice.setVisibility(LinearLayout.GONE);
		
		this.chargeAccounts();
		this.completeChapterDirectory();
		
		sectionEvents=(SectionOmegaFi)findViewById(R.id.sectionEvents);
		sectionEvents.setOnClickTitleListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
					Intent calendarActivity=new Intent(getApplication(), CalendarActivity.class);
					startActivity(calendarActivity);
			}
		});
		this.completeEvents();
		
		sectionPoll=(SectionOmegaFi)findViewById(R.id.sectionPoll);
		this.completePollSection();
		
		sectionNews=(SectionOmegaFi)findViewById(R.id.sectionNews);
		sectionNews.setOnClickTitleListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
					Intent newsActivity=new Intent(getApplication(), NewsOmegaFiActivity.class);
					startActivity(newsActivity);
			}
		});
		
		this.completeNewsSection();
		this.getJSONsServicesHome();
	}
	
	private void chargeAccounts(){
		for (int i = 0; i < 2; i++) {
			AccountLayout account=new AccountLayout(getApplicationContext());
			android.widget.LinearLayout.LayoutParams params=new android.widget.LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
					android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
			params.setMargins(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.padding_5dp_1));
			account.setLayoutParams(params);
			account.setListenerViewAccount(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent viewAccount=new Intent(getApplicationContext(), AccountActivity.class);
					startActivity(viewAccount);
				}
			});
			account.setListenerPayNow(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent payNow=new Intent(getApplicationContext(), PayNowActivity.class);
					startActivity(payNow);		
				}
			});
			linearAccounts.addView(account);
		}
	}
	
	private void getJSONsServicesHome(){
		/*bundleHome=getIntent().getExtras();
		String profile=bundleHome.getString("profile");
		String accounts=bundleHome.getString("accounts");
		String chapters="";
		try {
			jsonAccounts=new JSONObject(accounts);
			if(chapters!=""){
				jsonChapters=new JSONObject(chapters);
			}
			JSONArray jsonArray=jsonAccounts.getJSONArray("accounts");
			Log.d("Recibido el json array", jsonArray.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	
	private void completeChapterDirectory(){
		SectionOmegaFi sectionOfficers=new SectionOmegaFi(this);
		sectionOfficers.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		sectionOfficers.setTitleSection("Officers");
		sectionOfficers.setSizeTitle(getResources().getDimensionPixelSize(R.dimen.text_14sp));
		sectionOfficers.setShowArrow(false);
		sectionOfficers.setPutBorderBottom(true);
		listPhotos=new Gallery(this);
		listPhotos.setPadding(10,10, 10, 10);
		listPhotos.setLayoutParams(new LayoutParams(android.widget.Gallery.LayoutParams.MATCH_PARENT,
				android.widget.Gallery.LayoutParams.WRAP_CONTENT));
		listPhotos.setAdapter(new ImageAdapter(this));
		listPhotos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				detailsOffice.setVisibility(View.VISIBLE);
				detailsOffice.setNameRooster("Bryan Farnswortheimer "+(position+1));
				detailsOffice.setPositionRooster("Team Leader "+(position));
				detailsOffice.setPhoneRooster("654654"+(position+4));
				detailsOffice.setEmailRooster("bryan_3"+position+"@example.com");
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				detailsOffice.setVisibility(View.GONE);
			}
		});
		
		LinearLayout linearSection=(LinearLayout)sectionOfficers.findViewById(R.id.contentSectionOmegaFi);
		linearSection.setPadding(12, 0, 0, 10);
		linearSection.addView(listPhotos);
		
		RowInformation rowChapter=new RowInformation(this);
		rowChapter.setNameInfo("Alpha Delta Pi - Alpha Delta");
		rowChapter.setNameSubInfo("Miami University");
		rowChapter.setColorFontRowInformation(Color.BLACK);
		rowChapter.setBorderBottom(true);
		final Activity home=this;
		rowChapter.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DialogSelectableOF selectables=new DialogSelectableOF(home);
				selectables.setTitleDialog(null);
				selectables.setTextButton(null);
				selectables.setCloseOnSelectedItem(true);
				selectables.showDialog();
			}
		});
		int padding=this.getResources().getDimensionPixelSize(R.dimen.padding_5dp);
		rowChapter.setPaddingRow(padding,5,rowChapter.getPaddingRight(), 5);
		
		sectionChapterDirectory.addView(rowChapter);
		sectionChapterDirectory.addView(sectionOfficers);
		sectionChapterDirectory.addView(detailsOffice);
	}
	
	private void completeEvents(){
		sectionEvents.setPaddingAll(0, 0, 0, this.getResources().getDimensionPixelSize(R.dimen.padding_5dp));
		paginator=new ViewPager(getApplicationContext());
		paginator.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 
				this.getResources().getDimensionPixelSize(R.dimen.height_new_event_content)));
		adapterPager=new EventsNewsAdapter(this);
		paginator.setAdapter(adapterPager);
		
		CirclePageIndicator titlesIndicator=new CirclePageIndicator(this);
		titlesIndicator.setFillColor(this.getResources().getColor(R.color.red_wine));
		titlesIndicator.setStrokeColor(this.getResources().getColor(R.color.gray_background));
		int circleSize=this.getResources().getDimensionPixelSize(R.dimen.size_circle_newevents);
		titlesIndicator.setRadius(circleSize);
		titlesIndicator.setLayoutParams(new LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 
				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
		titlesIndicator.setViewPager(paginator);
		sectionEvents.addView(paginator);
		sectionEvents.addView(titlesIndicator);
	}
	
	private void completePollSection(){
		
		LinearLayout content=(LinearLayout)sectionPoll.findViewById(R.id.contentSectionOmegaFi);
		paginator=new ViewPager(getApplicationContext());
		paginator.setAdapter(new PollAdapter(getApplicationContext()));
		paginator.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 
				getResources().getDimensionPixelSize(R.dimen.height_poll_content)));
		
		LinearLayout linTitles=new LinearLayout(getApplicationContext());
		linTitles.setGravity(Gravity.CENTER_VERTICAL);
		linTitles.setLayoutParams(new LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 
				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
		linTitles.setOrientation(LinearLayout.VERTICAL);
		
		CirclePageIndicator titlesIndicator=new CirclePageIndicator(this);
		titlesIndicator.setLayoutParams(new LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 
				40));
		titlesIndicator.setPadding(0, 8, 0, 0);
		titlesIndicator.setBackgroundColor(Color.WHITE);
		titlesIndicator.setFillColor(this.getResources().getColor(R.color.red_wine));
		titlesIndicator.setStrokeColor(this.getResources().getColor(R.color.gray_background));
		int circleSize=this.getResources().getDimensionPixelSize(R.dimen.size_circle_newevents);
		titlesIndicator.setRadius(circleSize);
		titlesIndicator.setViewPager(paginator);
		
		View jagged=new View(getApplicationContext());
		jagged.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.padding_10dp)));
		jagged.setBackgroundResource(R.drawable.jagged_inv);
		linTitles.addView(jagged);
		linTitles.addView(titlesIndicator);
	
		content.setBackgroundColor(getResources().getColor(R.color.gray_background));
		content.addView(paginator);
		content.addView(linTitles);
	}
	
	private void completeNewsSection(){
		sectionNews.setPaddingAll(0, 0, 0, this.getResources().getDimensionPixelSize(R.dimen.padding_5dp));
		paginator=new ViewPager(getApplicationContext());
		paginator.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, this.getResources().getDimensionPixelSize
				(R.dimen.height_new_event_content)));
		adapterPager=new EventsNewsAdapter(this);
		paginator.setAdapter(adapterPager);
		
		CirclePageIndicator titlesIndicator=new CirclePageIndicator(this);
		titlesIndicator.setFillColor(this.getResources().getColor(R.color.red_wine));
		titlesIndicator.setStrokeColor(this.getResources().getColor(R.color.gray_background));
		int circleSize=this.getResources().getDimensionPixelSize(R.dimen.size_circle_newevents);
		titlesIndicator.setRadius(circleSize);
		titlesIndicator.setLayoutParams(new LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 
				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
		titlesIndicator.setViewPager(paginator);
		sectionNews.addView(paginator);
		sectionNews.addView(titlesIndicator);
	}
	
	@Override
	public void onBackPressed() {
		this.showConfirmateExit();
	}
	
	public void activityTermsUse(View button){
		Intent activityTerms=new Intent(this, TermsActivity.class);
		startActivity(activityTerms);
	}
	
	public void activityPrivatePolicy(View button){
		Intent activityPrivacy=new Intent(this, PrivacyActivity.class);
		startActivity(activityPrivacy);
	}
	
	public void seeMoreMemberRooster(View button){
		Intent roosterDetail=new Intent(this, OfficerMemberDetailActivity.class);
		startActivity(roosterDetail);
	}
	
	public void callToMember(View button){
		Intent intentCall=new Intent(Intent.ACTION_CALL);
		intentCall.setData(Uri.parse("tel:*123"));
		startActivity(intentCall);
	}

	
}
