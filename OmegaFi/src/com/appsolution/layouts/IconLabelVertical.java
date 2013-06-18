package com.appsolution.layouts;

import com.appsolution.omegafi.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class IconLabelVertical extends LinearLayout {

	public IconLabelVertical(Context context) {
		super(context);
		this.initialize();
	}

	public IconLabelVertical(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initialize();
	}

	public IconLabelVertical(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.initialize();
	}
	
	private void initialize() {
		LayoutInflater inflate= (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflate.inflate(R.layout.icon_label_vertical, this, true);
	}

}
