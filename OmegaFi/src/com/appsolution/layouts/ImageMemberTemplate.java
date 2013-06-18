package com.appsolution.layouts;

import com.appsolution.omegafi.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ImageMemberTemplate extends LinearLayout {

	private LinearLayout linearBasicInfo;
	private LinearLayout linearAditionalInfo;
	private ImageView imagePhoto;
	
	public ImageMemberTemplate(Context context){
		super(context);
		this.initialize();
	}
	
	public ImageMemberTemplate(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initialize();
	}
	
	private void initialize(){
		LayoutInflater inflate= (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflate.inflate(R.layout.image_member_template, this, true);
		linearBasicInfo=(LinearLayout)findViewById(R.id.linearBasicInfoMember);
		linearAditionalInfo=(LinearLayout)findViewById(R.id.linearAditionalInfoMember);
		imagePhoto=(ImageView)findViewById(R.id.image_member_omegafi);
	}
	
	public void addViewToBasic(View view){
		linearBasicInfo.addView(view);
	}
	
	public void addViewToAditional(View view){
		linearAditionalInfo.addView(view);
	}
	
	public void setImageClickListener(View.OnClickListener listener){
		imagePhoto.setOnClickListener(listener);
	}

}
