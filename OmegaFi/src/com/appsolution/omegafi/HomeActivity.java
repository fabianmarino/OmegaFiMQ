package com.appsolution.omegafi;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.appsolution.interfaces.OnRowCheckListener;
import com.appsolution.layouts.AccountLayout;
import com.appsolution.layouts.DetailsOfficer;
import com.appsolution.layouts.DialogSelectableOF;
import com.appsolution.layouts.EventsNewsAdapter;
import com.appsolution.layouts.ImageAdapter;
import com.appsolution.layouts.ImageRoosterName;
import com.appsolution.layouts.NotificationLayout;
import com.appsolution.layouts.PollAdapter;
import com.appsolution.layouts.PollOmegaFiContent;
import com.appsolution.layouts.RowInformation;
import com.appsolution.layouts.SectionOmegaFi;
import com.appsolution.logic.Account;
import com.appsolution.logic.CalendarEvent;
import com.appsolution.logic.Officer;
import com.appsolution.logic.Poll;
import com.appsolution.services.Server;
import com.viewpagerindicator.CirclePageIndicator;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeActivity extends OmegaFiActivity {
	
	private LinearLayout linearNotifications;
	private SectionOmegaFi sectionChapterDirectory;
	private ImageAdapter listGallery;
	private DetailsOfficer detailsOffice;
	
	private boolean isAnySelected=false;
	private Gallery listPhotos;
	private SectionOmegaFi sectionEvents;
	private ViewPager paginator1;
	private ViewPager paginator2;
	private ViewPager paginator3;
	private PagerAdapter adapterPager1;
	private PagerAdapter adapterPager2;
	private PagerAdapter adapterPager3;
	
	private SectionOmegaFi sectionPoll;
	
	private PollOmegaFiContent contentPoll;
	private SectionOmegaFi sectionNews;
	
	private LinearLayout linearAccounts; 
	
	private TextView textTerms;
	private TextView textPrivacy;
	private int indexChapter=0;
	private Officer officerActual=null;
	
	private int servicesCharged=0;
	private static final int NUM_SERVICES_TO_CHARGING=6;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		linearNotifications=(LinearLayout)findViewById(R.id.linearNotificationsHome);
		linearAccounts=(LinearLayout)findViewById(R.id.linearChargeAccounts);
//		Intent intent=new Intent(this, AccountActivity.class);
//		startActivity(intent);
		sectionChapterDirectory=(SectionOmegaFi)findViewById(R.id.sectionChapterDirectory);
		sectionChapterDirectory.setPaddingLeft(getResources().getDimensionPixelSize(R.dimen.padding_10dp));
		sectionChapterDirectory.setOnClickTitleListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent memberRosters=new Intent(getApplicationContext(), ListMembersActivity.class);
				memberRosters.putExtra("id",Server.getServer().getHome().getChapters().getIdChapter( indexChapter));
				startActivityForResult(memberRosters,OmegaFiActivity.ACTIVITY_LIST_MEMBERS);
			}
		});
		detailsOffice=new DetailsOfficer(this);
		detailsOffice.setVisibility(LinearLayout.GONE);
		this.chargeNotifications();
		this.chargeAccounts();
		this.completeChapterDirectory();
		
		sectionEvents=(SectionOmegaFi)findViewById(R.id.sectionEvents);
		sectionEvents.setOnClickTitleListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
					Intent calendarActivity=new Intent(getApplication(), CalendarActivity.class);
					startActivityForResult(calendarActivity,OmegaFiActivity.ACTIVITY_CALENDAR);
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
					startActivityForResult(newsActivity,OmegaFiActivity.ACTIVITY_NEWS);
			}
		});
		
		textTerms=(TextView)findViewById(R.id.textTermsUse);
		textTerms.setTypeface(OmegaFiActivity.getFont(getApplicationContext(), 0));
		textPrivacy=(TextView)findViewById(R.id.textPrivatePolicy);
		textPrivacy.setTypeface(OmegaFiActivity.getFont(getApplicationContext(), 0));
		this.completeNewsSection();
		chargeHomeAsync();
	}
	
	private void chargeNotifications(){
		ArrayList<String> notifications=Server.getServer().getHome().getProfile().getNotifications();
		for (String noti:notifications) {
			NotificationLayout notifiLayout=new NotificationLayout(this);
			notifiLayout.setNotification(noti);
			linearNotifications.addView(notifiLayout);
		}
	}
	
	private void chargeAccounts(){
		ArrayList<Account> accounts=null;
		accounts=Server.getServer().getHome().getAccounts().getAccounts();
		if(accounts!=null){
			for (Account accountAux:accounts) {
				final AccountLayout account=new AccountLayout(getApplicationContext());
				android.widget.LinearLayout.LayoutParams params=new android.widget.LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
						android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
				params.setMargins(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.padding_8dp));
				account.setAccount(accountAux);
				account.setLayoutParams(params);
				account.setListenerViewAccount(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						finish();
						Intent viewAccount=new Intent(HomeActivity.this, AccountActivity.class);
						viewAccount.putExtra("id", account.getIdAccount());
						startActivityForResult(viewAccount,OmegaFiActivity.ACTIVITY_VIEW_ACCOUNT);
					}
				});
				account.setListenerPayNow(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						Intent payNow=new Intent(HomeActivity.this, PayNowActivity.class);
						payNow.putExtra("id", account.getIdAccount());
						payNow.putExtra("home", true);
						startActivityForResult(payNow, OmegaFiActivity.ACTIVITY_PAY_NOW);		
					}
				});
				linearAccounts.addView(account);
			}
		}
	}
	
	
	private void completeChapterDirectory(){
		if(!Server.getServer().getHome().getChapters().isEmpty()){
//		if(true){
			SectionOmegaFi sectionOfficers=new SectionOmegaFi(this);
			sectionOfficers.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			sectionOfficers.setTitleSection("Officers");
			sectionOfficers.setSizeTitle(getResources().getDimensionPixelSize(R.dimen.text_14sp));
			sectionOfficers.setShowArrow(false);
			sectionOfficers.setPutBorderBottom(false);
			sectionOfficers.setBackgroundColorLinear(Color.TRANSPARENT);
			
			listPhotos=new Gallery(this);
			listPhotos.setPadding(15,10, 10, 10);
			listPhotos.setSpacing(10);
			listPhotos.setLayoutParams(new LayoutParams(android.widget.Gallery.LayoutParams.MATCH_PARENT,
					android.widget.Gallery.LayoutParams.WRAP_CONTENT));
			listGallery=new ImageAdapter(this);
//			Server.getServer().getHome().getOfficers().chargeOfficers(getApplicationContext());
			listGallery.setListOfficers(Server.getServer().getHome().getOfficers().getListOfficers());
			listPhotos.setAdapter(listGallery);
			if(listGallery.getCount()>0)
				listPhotos.setSelection(1);
			listPhotos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	
				@Override
				public void onItemSelected(AdapterView<?> arg0, View actual,
						int position, long arg3) {
					ImageRoosterName rooster=(ImageRoosterName)actual;
					rooster.setSelectedImageRooster(false);
					if(!isAnySelected){
						detailsOffice.setVisibility(View.GONE);
						changeStateIsAnythingSelected();
					}
					else{
						rooster.setSelectedImageRooster(true);
						detailsOffice.setVisibility(View.VISIBLE);
						officerActual=listGallery.getOfficer(position);
						detailsOffice.setNameRooster(officerActual.getCompleteName());
						detailsOffice.setPositionRooster(officerActual.getOfficeType());
						detailsOffice.setPhoneRooster(officerActual.getTelephone());
						detailsOffice.setEmailRooster(officerActual.getEmail());
					}	
				}
	
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					
				}
			});
			listPhotos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	
				@Override
				public void onItemClick(AdapterView<?> arg0, View actual, int position,
						long arg3) {
					ImageRoosterName rooster=(ImageRoosterName)actual;
					if(listPhotos.getSelectedItemPosition()==position){
						if(detailsOffice.getVisibility()==View.GONE){
							detailsOffice.setVisibility(View.VISIBLE);
							rooster.setSelectedImageRooster(true);
							officerActual=listGallery.getOfficer(position);
							detailsOffice.setNameRooster(officerActual.getCompleteName());
							detailsOffice.setPositionRooster(officerActual.getOfficeType());
							detailsOffice.setPhoneRooster(officerActual.getTelephone());
							detailsOffice.setEmailRooster(officerActual.getEmail());
						}
						else{
							detailsOffice.setVisibility(View.GONE);
							rooster.setSelectedImageRooster(false);
						}
					}
				}
			});
			
			LinearLayout linearSection=(LinearLayout)sectionOfficers.findViewById(R.id.contentSectionOmegaFi);
			linearSection.setPadding(12, 0, 0, 10);
			linearSection.addView(listPhotos);
//			final ArrayList<String> chapters=new ArrayList<String>();
			final ArrayList<String> chapters=Server.getServer().getHome().getChapters().getChapterNames();
//			chapters.add("Sigma Pi - Beta Nu,Oregon State University");
			String[] nameSubName=chapters.get(0).split(",");
			if(!Server.getServer().getHome().getChapters().getChapter(0).isCanSeeMembers()){
				sectionChapterDirectory.contentTitleVisible(false);
			}
			final RowInformation rowChapter=new RowInformation(this);
			rowChapter.setNameInfo(nameSubName[0]);
			rowChapter.setNameSubInfo(nameSubName[1]);
			rowChapter.setColorFontRowInformation(Color.BLACK);
			if(chapters.size()>1)
				rowChapter.setImageIcon(R.drawable.icon_spinner);
			rowChapter.setBorderBottom(true);
			
			final Activity home=this;
			if(chapters.size()>1){
				rowChapter.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						final DialogSelectableOF selectables=new DialogSelectableOF(home);
						selectables.setOptionsSelectables(chapters);
						selectables.doChecksWithOutFade();
						selectables.setItemSelected(rowChapter.getNameAndSubNameInfo());
						selectables.setOnCheckedListener(new OnRowCheckListener() {
							
							@Override
							public void actionBeforeChecked() {
							}
							
							@Override
							public void actionAfterChecked() {
								indexChapter=selectables.getIndexSelected();
								changeListImages();
//								changeListImages();
								rowChapter.setNameInfo(selectables.getRowSelected().getNameInfo());
								rowChapter.setNameSubInfo(selectables.getRowSelected().getNameSubInfo());
							}
						});
						selectables.setTitleDialog(null);
						selectables.setTextButton(null);
						selectables.setCloseOnSelectedItem(true);
						selectables.showDialog();
					}
				});
			}
			int padding=this.getResources().getDimensionPixelSize(R.dimen.padding_5dp);
			rowChapter.setPaddingRow(padding+8,5,padding, 5);
			rowChapter.getTextNameInfo().setPadding(5, 0, 0, 0);
			rowChapter.getTextNameSubInfo().setPadding(5, 0, 0, 0);
			sectionChapterDirectory.addView(rowChapter);
			sectionChapterDirectory.addView(sectionOfficers);
			sectionChapterDirectory.addView(detailsOffice);
		}
		else{
			sectionChapterDirectory.setVisibility(View.GONE);
		}
	}
	
	public void changeListImages(){
		sectionChapterDirectory.contentTitleVisible(Server.getServer().getHome().getChapters().getChapter(indexChapter).isCanSeeMembers());
		AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>(){

			@Override
			protected void onPreExecute() {
				startProgressDialog("Officers","Loading...");
			}
			
			@Override
			protected Boolean doInBackground(Void... arg0) {
				int idChapter=Server.getServer().getHome().getChapters().getIdChapter(indexChapter);
				Server.getServer().getHome().getOfficers().chargeOfficers(idChapter);
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				listGallery.setListOfficers(Server.getServer().getHome().getOfficers().getListOfficers());
				listPhotos.setAdapter(listGallery);
				if(listGallery.getCount()>0)
					listPhotos.setSelection(1);
				stopProgressDialog();
			}
			
		};
		task.execute();
	}
	

	private void changeStateIsAnythingSelected(){
		if(isAnySelected){
			isAnySelected=false;
		}
		else{
			isAnySelected=true;
		}
	}
	
	private void completeEvents(){
		if(Server.getServer().getHome().getCalendar().getListEvents().size()==0){
//		if(false){
			sectionEvents.setVisibility(View.GONE);
		}
		else{
			sectionEvents.setPaddingAll(0, 0, 0, 0);
			paginator1=new ViewPager(getApplicationContext());
			paginator1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 
					this.getResources().getDimensionPixelSize(R.dimen.height_new_event_content)));
			adapterPager1=new EventsNewsAdapter(this);
			((EventsNewsAdapter)adapterPager1).setListaEventsOrNews(Server.getServer().getHome().getCalendar().getListEvents());
			paginator1.setAdapter(adapterPager1);
			CirclePageIndicator titlesIndicator=new CirclePageIndicator(this);
			titlesIndicator.setFillColor(this.getResources().getColor(R.color.red_wine));
			titlesIndicator.setPageColor(this.getResources().getColor(R.color.gray_background));
			int circleSize=this.getResources().getDimensionPixelSize(R.dimen.size_circle_newevents);
			titlesIndicator.setRadius(circleSize);
			titlesIndicator.setLayoutParams(new LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 
					getResources().getDimensionPixelSize(R.dimen.height_20dp)));
			titlesIndicator.setViewPager(paginator1);
			sectionEvents.addView(paginator1);
			sectionEvents.addView(titlesIndicator);
		}
	}
	
	private void completePollSection(){
		if(Server.getServer().getHome().getPolls().isEmpty()){
//		if(false){
			sectionPoll.setVisibility(View.GONE);
		}
		else{
			sectionPoll.setBackgroundResourceTitle(R.drawable.background_trans_white_border);
			LinearLayout content=(LinearLayout)sectionPoll.findViewById(R.id.contentSectionOmegaFi);
			content.setPadding(0, content.getPaddingTop(), content.getPaddingRight(), content.getPaddingBottom());
			paginator2=new ViewPager(getApplicationContext());
			PollAdapter pollAdapter=new PollAdapter(this);
//			pollAdapter.setPolls(getPollsTest());
			pollAdapter.setPolls(Server.getServer().getHome().getPolls().getPolls());
			paginator2.setAdapter(pollAdapter);
			paginator2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 
					getResources().getDimensionPixelSize(R.dimen.height_poll_content)));
			
			LinearLayout linTitles=new LinearLayout(getApplicationContext());
			linTitles.setBackgroundResource(R.drawable.background_trans_border_1);
			linTitles.setPadding(2, 0, 2, 2);
			linTitles.setGravity(Gravity.CENTER_VERTICAL);
			linTitles.setLayoutParams(new LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 
					android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
			linTitles.setOrientation(LinearLayout.VERTICAL);
			
			CirclePageIndicator titlesIndicator=new CirclePageIndicator(this);
			titlesIndicator.setLayoutParams(new LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 
				getResources().getDimensionPixelSize(R.dimen.height_20dp)));
			titlesIndicator.setPadding(0, 8, 0, 0);
			titlesIndicator.setBackgroundColor(Color.WHITE);
			titlesIndicator.setFillColor(this.getResources().getColor(R.color.red_wine));
			titlesIndicator.setPageColor(this.getResources().getColor(R.color.gray_background));
			int circleSize=this.getResources().getDimensionPixelSize(R.dimen.size_circle_newevents);
			titlesIndicator.setRadius(circleSize);
			titlesIndicator.setViewPager(paginator2);
			
			View jagged=new View(getApplicationContext());
			jagged.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.padding_10dp)));
			jagged.setBackgroundResource(R.drawable.jagged_inv);
			linTitles.addView(jagged);
			linTitles.addView(titlesIndicator);
		
			content.setBackgroundColor(getResources().getColor(R.color.gray_background));
			content.addView(paginator2);
			content.addView(linTitles);
		}
	}
	
	private void completeNewsSection(){
//		String title="Alpha - Kappa News";
		String title=Server.getServer().getForgotLogin().getTitleFeed(HomeActivity.this);
		if(Server.getServer().getHome().getFeeds().isEmpty()){
//		if(false){
			sectionNews.setVisibility(View.GONE);
		}
		else{
			sectionNews.setTitleSection(title);
			sectionNews.setPaddingAll(0, 0, 0, 0);
			paginator3=new ViewPager(getApplicationContext());
			paginator3.requestDisallowInterceptTouchEvent(true);
			paginator3.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, this.getResources().getDimensionPixelSize
					(R.dimen.height_new_event_content_2)));
			adapterPager3=new EventsNewsAdapter(this);
			((EventsNewsAdapter)adapterPager3).setHTML(true);
			((EventsNewsAdapter)adapterPager3).setListaEventsOrNews(Server.getServer().getHome().getFeeds().getNews());
//			((EventsNewsAdapter)adapterPager3).setListaEventsOrNews(getTestCalendarEvent());
			paginator3.setAdapter(adapterPager3);
			
			CirclePageIndicator titlesIndicator=new CirclePageIndicator(this);
			titlesIndicator.setFillColor(this.getResources().getColor(R.color.red_wine));
			titlesIndicator.setPageColor(this.getResources().getColor(R.color.gray_background));
			int circleSize=this.getResources().getDimensionPixelSize(R.dimen.size_circle_newevents);
			titlesIndicator.setRadius(circleSize);
			titlesIndicator.setLayoutParams(new LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 
					getResources().getDimensionPixelSize(R.dimen.height_20dp)));
			titlesIndicator.setViewPager(paginator3);
			sectionNews.addView(paginator3);
			sectionNews.addView(titlesIndicator);
		}
	}
	
	@Override
	public void onBackPressed() {
		this.showConfirmateExit();
	}
	
	public void activityTermsUse(View button){
		Intent activityTerms=new Intent(this, TermsActivity.class);
		startActivityForResult(activityTerms,OmegaFiActivity.ACTIVITY_TERMS);
	}
	
	public void activityPrivatePolicy(View button){
		Intent activityPrivacy=new Intent(this, PrivacyActivity.class);
		startActivityForResult(activityPrivacy,OmegaFiActivity.ACTIVITY_PRIVACY);
	}
	
	public void seeMoreMemberRooster(View button){
		Intent roosterDetail=new Intent(this, OfficerMemberDetailActivity.class);
		roosterDetail.putExtra(OfficerMemberDetailActivity.TYPE_ROOSTER, OfficerMemberDetailActivity.OFFICER_ROOSTER);
		int idChapter=Server.getServer().getHome().getChapters().getIdChapter(indexChapter);
		roosterDetail.putExtra("idc", idChapter);
		roosterDetail.putExtra("idm", officerActual.getId());
		startActivityForResult(roosterDetail,OmegaFiActivity.ACTIVITY_MEMBER_DETAIL);
	}
	
	public void callToMember(View button){
		String numberCall=detailsOffice.getPhoneCall().replace("-", "");
		if(!numberCall.isEmpty()){
			Intent intentCall=new Intent(Intent.ACTION_CALL);
			intentCall.setData(Uri.parse("tel:"+numberCall));
			startActivity(intentCall);
		}
	}
	
	public void sendEmailMember(View button){
		if(!detailsOffice.getEmailRooster().isEmpty()){
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("text/plain");
			intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {detailsOffice.getEmailRooster()});
			Intent mailer = Intent.createChooser(intent, null);
			startActivity(mailer);
		}
	}
	
	public ArrayList<CalendarEvent> getTestCalendarEvent(){
		ArrayList<CalendarEvent> array=new ArrayList<CalendarEvent>();
		for (int i = 0; i < 6; i++) {
			array.add(new CalendarEvent());
		}
		return array;
	}
	

	@Override
	protected void finalize() throws Throwable {
		sectionChapterDirectory=null;
		listGallery=null;
		detailsOffice=null;
		listPhotos=null;
		sectionEvents=null;
		paginator1=null;
		paginator2=null;
		paginator3=null;
		adapterPager1=null;
		adapterPager2=null;
		adapterPager3=null;
		sectionPoll=null;
		contentPoll=null;
		linearAccounts=null;
		textPrivacy=null;
		textTerms=null;
		super.finalize();
	}
	
	private void finishCharge(){
		servicesCharged++;
		if(servicesCharged>=NUM_SERVICES_TO_CHARGING){
			Server.getServer().setEmptyInformation(false);
			servicesCharged=0;
			stopProgressDialog();
			recreate();
			refreshActivity();
		}
	}
	
	private void chargeProfileInformation(){
		AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>() {
			int status=0;
			@Override
			protected Boolean doInBackground(Void... params) {
				status=(Integer)Server.getServer().getHome().getProfile().chargeProfileData()[0];
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				finishCharge();
			}
		};
		task.execute();
	}
	
	private void chargeAccountsInformation(){
		AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>() {
			
			@Override
			protected Boolean doInBackground(Void... params) {
				Server.getServer().getHome().getAccounts().chargeAccounts();
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				finishCharge();
			}
		};
		task.execute();
	}
	
	private void chargeChaptersInformation(){
		AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>() {
			
			@Override
			protected Boolean doInBackground(Void... params) {
				Server.getServer().getHome().getChapters().chargeChapters();
				Server.getServer().getHome().getOfficers().chargeOfficers(
						Server.getServer().getHome().getChapters().getIdChapter(0));
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				finishCharge();
			}
		};
		task.execute();
	}
	
	private void chargeCalendarInformation(){
		AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>() {
			
			@Override
			protected Boolean doInBackground(Void... params) {
				Server.getServer().getHome().getCalendar().chargeEventsHome();
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				finishCharge();
			}
		};
		task.execute();
	}
	
	private void chargePollsInformation(){
		AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>() {
			
			@Override
			protected Boolean doInBackground(Void... params) {
				Server.getServer().getHome().getPolls().chargePolls();
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				finishCharge();
			}
		};
		task.execute();
	}
	
	private void chargeNewsInformation(){
		AsyncTask<Void, Integer, Boolean> task=new AsyncTask<Void, Integer, Boolean>() {
			
			@Override
			protected Boolean doInBackground(Void... params) {
				Server.getServer().getHome().getFeeds().chargeNewsFeed
				(Server.getServer().getForgotLogin().getUrlFeed(HomeActivity.this));
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				finishCharge();
			}
		};
		task.execute();
	}
	
	
	private void chargeHomeAsync(){
		if(Server.getServer().isEmptyInformation()){
			startProgressDialog("Loading Home...", getResources().getString(R.string.please_wait));
			chargeProfileInformation();
			chargeAccountsInformation();
			chargeChaptersInformation();
			chargeCalendarInformation();
			chargePollsInformation();
			chargeNewsInformation();
		}
	}
	
	private ArrayList<Poll> getPollsTest(){
		ArrayList<Poll> polls=new ArrayList<Poll>();
		try {
			JSONArray jsonFile=new JSONArray(OmegaFiActivity.getStringFile(getApplicationContext(), "txt/polls.json"));
			for (int i = 0; i < jsonFile.length(); i++) {	
				JSONObject jsonPoll=jsonFile.getJSONObject(i).getJSONObject("poll");
				polls.add(new Poll(jsonPoll));
		}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return polls;
	}
	
}