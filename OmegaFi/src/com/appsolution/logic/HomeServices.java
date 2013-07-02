package com.appsolution.logic;

public class HomeServices extends ServerContext{

	private ProfileService profile;
	private AccountsService accounts;
	private ChaptersService chapters;
	private OfficersService officers;
	private CalendarService calendar;
	private NewsFeedService feeds;
	
	public HomeServices(Server server){
		super(server);
		profile=new ProfileService(server);
		accounts=new AccountsService(server);
		chapters=new ChaptersService(server);
		officers=new OfficersService(server);
		calendar=new CalendarService(server);
		feeds=new NewsFeedService(server);
	}
	
	public ProfileService getProfile() {
		return profile;
	}

	public AccountsService getAccounts() {
		return accounts;
	}

	public ChaptersService getChapters() {
		return chapters;
	}

	public OfficersService getOfficers() {
		return officers;
	}

	public CalendarService getCalendar() {
		return calendar;
	}

	public NewsFeedService getFeeds() {
		return feeds;
	}
	
	public void clearHomeServices(){
		officers.stopChargeOfficers();
		profile=new ProfileService(server);
		accounts=new AccountsService(server);
		chapters=new ChaptersService(server);
		officers=new OfficersService(server);
		calendar=new CalendarService(server);
		feeds=new NewsFeedService(server);
	}
	
	
	
}
