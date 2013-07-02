package com.appsolution.logic;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.xml.sax.SAXException;

import android.util.Log;

import com.appsolution.layouts.EventsNewsAdapter;

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

	public ArrayList<CalendarEvent> getNews() {
		return news;
	}
	
	public boolean isEmpty(){
		return news.isEmpty();
	}

}
