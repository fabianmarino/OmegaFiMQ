package com.appsolution.layouts;

import com.appsolution.omegafi.R;

import android.content.Context;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

public class RowCheckOmegaFi  extends RowEditInformation{

	private RadioButton radioOption;
	
	public RowCheckOmegaFi(Context context,RowCheckGroup group) {
		super(context);
		radioOption=new RadioButton(context);
		radioOption.setLayoutParams(new RelativeLayout.LayoutParams(50, 
				50));
		radioOption.setButtonDrawable(R.drawable.radio_button);
		this.addViewRight(radioOption);
		group.addRowCheck(this);
	}
	
	public void setChecked(boolean checked){
		radioOption.setChecked(checked);
	}
	
	public boolean isChecked(){
		return radioOption.isChecked();
	}

}
