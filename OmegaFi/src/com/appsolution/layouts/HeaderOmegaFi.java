package com.appsolution.layouts;

import com.appsolution.omegafi.OmegaFiActivity;
import com.appsolution.omegafi.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HeaderOmegaFi extends LinearLayout {
	
	private TextView titleMessageHeader;
	private TextView messageForForm;
	
	public HeaderOmegaFi(Context context){
		super(context);
		this.initialize();
	}
	
	public HeaderOmegaFi(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initialize();
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.HeaderOmegaFi);
		
		String textTitle= a.getString(R.styleable.HeaderOmegaFi_text_title_header);
		setTextMessageHeader(textTitle);
		
		String textMessage = a.getString(R.styleable.HeaderOmegaFi_text_message_form);
		setMessageForForm(textMessage);
		
		a.recycle();
	}
	
	
	private void initialize() {
		LayoutInflater inflate= (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflate.inflate(R.layout.header_omage_fi, this, true);
		titleMessageHeader=(TextView)findViewById(R.id.titleMessageHeader);
		titleMessageHeader.setTypeface(OmegaFiActivity.getFont(getContext(), 3));
		messageForForm=(TextView)findViewById(R.id.messageForForm);
		messageForForm.setTypeface(OmegaFiActivity.getFont(getContext(), 3));
	}
	
	public void setTextMessageHeader(String msg){
		if(msg!=null){
			titleMessageHeader.setText(msg);
			titleMessageHeader.setVisibility(VISIBLE);
		}
	}
	
	public void setMessageForForm(String msg){
		if(msg!=null){
			messageForForm.setText(msg);
			messageForForm.setVisibility(VISIBLE);
		}
	}

}
