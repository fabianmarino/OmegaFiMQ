package com.appsolution.layouts;

import android.content.Context;
import android.util.AttributeSet;

public class ContactInformation extends SectionOmegaFi{

	private RowEditTextOmegaFi rowEditEmail;
	private RowEditTextOmegaFi rowEditPhone;
	
	public ContactInformation(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setTitleSection("Contact Information");
		this.initializeComponents();
	}
	
	public ContactInformation(Context context) {
		super(context);
		this.setTitleSection("Contact Information");
		this.initializeComponents();
	}
	
	private void initializeComponents(){
		rowEditEmail=new RowEditTextOmegaFi(getContext());
		rowEditEmail.setNameInfo("Email");
		
		rowEditPhone=new RowEditTextOmegaFi(getContext());
		rowEditPhone.setNameInfo("Phone");
		
		this.addView(rowEditEmail);
		this.addView(rowEditPhone);
	}

}
