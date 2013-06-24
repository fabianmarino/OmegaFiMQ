package com.appsolution.layouts;

import com.appsolution.omegafi.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Spinner;

public class RowSpinnerNameTopInfo extends ViewNameTopInfo {

	private Spinner spinner;
	
	public RowSpinnerNameTopInfo(Context context) {
		super(context);
		this.initialize();
	}

	public RowSpinnerNameTopInfo(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initialize();
	}
	
	private void initialize() {
		spinner=new Spinner(getContext());
		spinner.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		spinner.setBackgroundResource(R.drawable.white_input_spinner);
		this.addView(spinner);

	}

}
