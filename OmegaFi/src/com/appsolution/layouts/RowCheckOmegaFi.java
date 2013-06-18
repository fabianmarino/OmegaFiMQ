package com.appsolution.layouts;

import com.appsolution.omegafi.R;

import android.content.Context;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

public class RowCheckOmegaFi  extends RowEditInformation{

	private RadioButton radioOption;
	
	public RowCheckOmegaFi(Context context,RowCheckGroup group) {
		super(context);
		setTextSizeInformation(context.getResources().getDimensionPixelSize(R.dimen.text_10sp));
		radioOption=new RadioButton(context);
		radioOption.setEnabled(false);
		int size=context.getResources().getDimensionPixelSize(R.dimen.width_arrow_down);
		RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(size, size);
		params.setMargins(params.leftMargin, params.topMargin, 10, params.bottomMargin);
		radioOption.setLayoutParams(params);
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
