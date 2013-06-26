package com.appsolution.layouts;

import com.appsolution.omegafi.R;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

public class RowCheckOmegaFi  extends RowEditInformation{

	private RadioButton radioOption;
	
	public RowCheckOmegaFi(Context context,RowCheckGroup group) {
		super(context);
		setTextSizeInformation(context.getResources().getDimensionPixelSize(R.dimen.text_12_notification));
		radioOption=new RadioButton(context);
		radioOption.setEnabled(false);
		int size=context.getResources().getDimensionPixelSize(R.dimen.width_arrow_down);
		RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		params.setMargins(params.leftMargin, params.topMargin, 10, params.bottomMargin);
		radioOption.setLayoutParams(params);
		radioOption.setButtonDrawable(R.drawable.radio_button);
		this.addViewRight(radioOption);
		group.addRowCheck(this);
		setTextColor(Color.GRAY);
	}
	
	public void setChecked(boolean checked){
		radioOption.setChecked(checked);
	}
	
	public boolean isChecked(){
		return radioOption.isChecked();
	}
	
	public String getItemRow(){
		String item=this.getNameInfo();
		if(!this.getNameSubInfo().isEmpty()){
			item=item+","+this.getNameSubInfo();
		}
		return item;
	}
	
	public String[] getItemNameSubNameRow(){
		return getItemRow().split(",");
	}
	
	public void setButtonDrawable(int resource){
		radioOption.setButtonDrawable(resource);
	}
	
}
