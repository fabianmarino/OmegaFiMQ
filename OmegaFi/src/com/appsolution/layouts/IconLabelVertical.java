package com.appsolution.layouts;

import com.appsolution.omegafi.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IconLabelVertical extends LinearLayout {

	private ImageView imageIcon;
	private TextView textLabel;
	
	public IconLabelVertical(Context context) {
		super(context);
		this.initialize();
	}

	public IconLabelVertical(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initialize();
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.IconLabelVertical);
		
		int resource=a.getResourceId(R.styleable.IconLabelVertical_src_icon_label_vertical, R.drawable.icon_email_white);
		setIconResourceLabel(resource);
		
		String text=a.getString(R.styleable.IconLabelVertical_text_label_vertical);
		setTextLabel(text);
		
		a.recycle();
	}

	public IconLabelVertical(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.initialize();
	}
	
	private void initialize() {
		LayoutInflater inflate= (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflate.inflate(R.layout.icon_label_vertical, this, true);
		imageIcon=(ImageView)findViewById(R.id.imageIconLabel);
		textLabel=(TextView)findViewById(R.id.labelIconLabel);
	}
	
	public void setIconResourceLabel(int src){
		imageIcon.setImageResource(src);
	}
	
	public void setTextLabel(String text){
		textLabel.setText(text);
	}

}
