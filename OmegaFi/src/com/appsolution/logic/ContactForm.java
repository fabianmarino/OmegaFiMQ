package com.appsolution.logic;

public class ContactForm {

	public static final int TYPE_PROFILE=0;
	public static final int TYPE_MEMBER=1;
	
	protected int id;
	protected boolean primary;
	protected String type;
	
	public int getId() {
		return id;
	}
	public boolean isPrimary() {
		return primary;
	}
	public String getType() {
		return type;
	}
	
	

}
