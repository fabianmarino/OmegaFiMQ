package com.appsolution.layouts;

import com.appsolution.omegafi.R;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

public class RowCheckBoxOmegaFi  extends RowEditInformation{

	private CheckBox checkOption;
	
	public RowCheckBoxOmegaFi(Context context) {
		super(context);
		setTextSizeInformation(context.getResources().getDimensionPixelSize(R.dimen.text_12_notification));
		checkOption= new CheckBox(context);
		checkOption.setEnabled(true);
		RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		params.setMargins(params.leftMargin, params.topMargin, 10, params.bottomMargin);
		checkOption.setLayoutParams(params);
		checkOption.setButtonDrawable(R.drawable.radio_button);
		checkOption.setSoundEffectsEnabled(true);
		this.addViewRight(checkOption);
		setTextColor(Color.GRAY);
		setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("onclick listener row",checkOption.isChecked()+"");
				checkOption.setChecked(!checkOption.isChecked());
			}
		});
	}
	
	public void setChecked(boolean checked){
		checkOption.setChecked(checked);
		Log.d("se ha puesto en: ", checked+"");
	}
	
	public boolean isChecked(){
		return checkOption.isChecked();
	}
	
	public void setMarginRightCheckBox(int marginRight){
		LayoutParams params=(LayoutParams) checkOption.getLayoutParams();
		params.rightMargin=marginRight;
		checkOption.setLayoutParams(params);
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
		checkOption.setButtonDrawable(resource);
	}
	
	@Override
	public void setOnClickListener(OnClickListener l) {
		checkOption.setOnClickListener(null);
		super.setOnClickListener(l);
	}
	
	public void setOnCheckedChangeListener(OnCheckedChangeListener listener){
		checkOption.setOnCheckedChangeListener(listener);
	}

	public CheckBox getRadioOption() {
		return checkOption;
	}
	
	
	
}
