package com.appsolution.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.xml.sax.SAXException;

import com.appsolution.logic.CalendarEvent;

import android.util.Log;

import nl.matshofman.saxrssreader.RssFeed;
import nl.matshofman.saxrssreader.RssItem;
import nl.matshofman.saxrssreader.RssReader;

public class NewsFeedService extends ServerContext {
	
	private ArrayList<CalendarEvent> news;
	
	public NewsFeedService(Server server) {
		super(server);
		news=new ArrayList<CalendarEvent>();
	}
	
	public void chargeNewsFeed(String urlstr){
		try {
			Log.d("start feeds...", urlstr);
			URL url=new URL(urlstr);
			RssFeed  feed=RssReader.read(url);
			ArrayList<RssItem> rssItems=feed.getRssItems();
			Log.d("cargando feeds...", rssItems.size()+"");
			news.clear();
			for (int i = 0; i < rssItems.size()&&i<6; i++) {
				RssItem item=rssItems.get(i);
				Log.d("item feed", item.getTitle());
				news.add(new CalendarEvent(item));
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<CalendarEvent> getNewsFeed(String urlstr){
		ArrayList<CalendarEvent> auxNews=new ArrayList<CalendarEvent>();
		try {
			URL url=new URL(urlstr);
			RssFeed  feed=RssReader.read(url);
			ArrayList<RssItem> rssItems=feed.getRssItems();
			for (int i = 0; i < rssItems.size(); i++) {
				RssItem item=rssItems.get(i);
				auxNews.add(new CalendarEvent(item));
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return auxNews;
	}

	public ArrayList<CalendarEvent> getNews() {
		return news;
	}
	
	public boolean isEmpty(){
		return news.isEmpty();
	}

}
