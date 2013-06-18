package com.appsolution.layouts;

import com.appsolution.omegafi.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LabelInfoVertical extends LinearLayout {
	
	private TextView textNameInfo;
	private TextView textContentInfo;

	public LabelInfoVertical(Context context) {
		super(context);
		this.initialize();
	}

	public LabelInfoVertical(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initialize();
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.LabelInfoVertical);
			int colorFont=a.getColor(R.styleable.LabelInfoVertical_color_font_vertical, Color.BLACK);
			setFontColor(colorFont);
			
			String textTitle=a.getString(R.styleable.LabelInfoVertical_text_title_label);
			setTitleLabel(textTitle);
			
			int sizeName=a.getDimensionPixelSize(R.styleable.LabelInfoVertical_size_title_label,
					super.getResources().getDimensionPixelSize(R.dimen.text_8sp));
			setSizeTitleLabel(sizeName);
			
			int sizeSubName=a.getDimensionPixelSize(R.styleable.LabelInfoVertical_size_value_label,
					super.getResources().getDimensionPixelSize(R.dimen.text_12sp_row_info));
			setSizeValueLabel(sizeSubName);
			
		a.recycle();
		
	}

	public LabelInfoVertical(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.initialize();
	}
	
	private void initialize() {
		LayoutInflater inflate= (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflate.inflate(R.layout.label_info_vertical, this, true);
		textNameInfo=(TextView)findViewById(R.id.nameInfoVertical);
		textContentInfo=(TextView)findViewById(R.id.contentInfoVertical);
	}
	
	public void setFontColor(int color){
		textContentInfo.setTextColor(color);
		textNameInfo.setTextColor(color);
	}
	
	public void setTitleLabel(String text){
		textNameInfo.setText("# Account");
		if(text!=null){
			textNameInfo.setText(text);
		}	
	}
	
	public void setSizeTitleLabel(int size){
		textNameInfo.setTextSize(TypedValue.COMPLEX_UNIT_SP,size);
	}
	
	public void setSizeValueLabel(int size){
		textContentInfo.setTextSize(TypedValue.COMPLEX_UNIT_SP,size);
	}

}
