package com.appsolution.layouts;

import android.content.Context;
import android.text.InputType;
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
		rowEditEmail.setTypeInputEditText(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
		
		rowEditPhone=new RowEditTextOmegaFi(getContext());
		rowEditPhone.setNameInfo("Phone");
		rowEditPhone.setTypeInputEditText(InputType.TYPE_CLASS_PHONE);
		
		this.addView(rowEditEmail);
		this.addView(rowEditPhone);
	}

}