package com.appsolution.logic;

import org.json.JSONException;
import org.json.JSONObject;

public class SimpleAnnouncement {

	private int id;
	private String subject;
	private String dateCreate;
	private String announcement;
	private String source;
	private String previewAnnoncement;
	
	public SimpleAnnouncement(JSONObject jsonAnnouncement) {
		try {
			if(jsonAnnouncement!=null){	
					id=jsonAnnouncement.getInt("announcement_id");
					if(!jsonAnnouncement.isNull("subject"))
						subject=jsonAnnouncement.getString("subject");
					if(!jsonAnnouncement.isNull("announcement"))
						announcement=jsonAnnouncement.getString("announcement");
					if(!jsonAnnouncement.isNull("created_on"))
						dateCreate=jsonAnnouncement.getString("created_on");
					if(!jsonAnnouncement.isNull("source"))
						source=jsonAnnouncement.getString("source");
					if(!jsonAnnouncement.isNull("announcement_preview"))
						previewAnnoncement=jsonAnnouncement.getString("announcement_preview");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getId() {
		return id;
	}

	public String getSubject() {
		return subject;
	}

	public String getDateCreate() {
		String date=null;
		if(dateCreate!=null){
			if(dateCreate.length()>=10){
				date=CalendarEvent.getFormatDate(3, dateCreate.substring(0, 10), "yyyy/MM/dd");
			}
		}
		return date;
	}

	public String getAnnouncement() {
		return announcement;
	}

	public String getSource() {
		return source;
	}

	public String getPreviewAnnoncement() {
		return previewAnnoncement;
	}
	
	
	

}
