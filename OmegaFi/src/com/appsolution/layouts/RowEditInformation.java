package com.appsolution.layouts;

import com.appsolution.omegafi.OmegaFiActivity;
import com.appsolution.omegafi.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RowEditInformation extends RelativeLayout {

	private RelativeLayout content;
	private TextView textNameInfo;
	private TextView textSubNameInfo;
	private TextView textThirdLine;
	
	public RowEditInformation(Context context){
		super(context);
		this.initialize();
		 setTextSizeInformation(super.getResources().getDimensionPixelSize(R.dimen.text_12_notification));
	}
	
	public RowEditInformation(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initialize();
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.RowEditInformation);
		   boolean isBorderBottom = a.getBoolean(R.styleable.RowEditInformation_put_border_bottom_e,true);
		   if(isBorderBottom){
			   setBorderBottom(isBorderBottom);
			   }
		
		   String nameInfo = a.getString(R.styleable.RowEditInformation_name_information_e);
		   setNameInfo(nameInfo);
		   
		   float textSize = a.getDimension(R.styleable.RowEditInformation_row_text_size_e,  
				   super.getResources().getDimensionPixelSize(R.dimen.text_12_notification));
		   setTextSizeInformation(textSize);
		   
		   int resource=(int)a.getResourceId(R.styleable.RowEditInformation_background_row_edit_info, -1);
		   setBackgroundResource(resource);	   
		   
		   int padding=(int)a.getDimension(R.styleable.RowEditInformation_padding_row_edit, content.getPaddingLeft());
		   setPaddingRow(padding, padding, padding, padding);
		   int paddingLeft=(int)a.getDimension(R.styleable.RowEditInformation_padding_left_row_edit, content.getPaddingLeft());
		   int paddingTop=(int)a.getDimension(R.styleable.RowEditInformation_padding_top_row_edit, content.getPaddingTop());
		   int paddingRight=(int)a.getDimension(R.styleable.RowEditInformation_padding_right_row_edit, content.getPaddingRight());
		   int paddingBottom=(int)a.getDimension(R.styleable.RowEditInformation_padding_bottom_row_edit, content.getPaddingBottom());
		   setPaddingRow(paddingLeft, paddingTop, paddingRight, paddingBottom);
		    a.recycle();
	}
	
	private void initialize(){
		LayoutInflater inflate= (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflate.inflate(R.layout.row_edit_information, this, true);
		content=(RelativeLayout)findViewById(R.id.rowEditInformation);
		content.setPadding(content.getPaddingLeft(), content.getPaddingTop(), 10, content.getPaddingBottom());
		textNameInfo=(TextView)findViewById(R.id.nameInfoEdit);
		textNameInfo.setClickable(true);
		textNameInfo.setTextColor(getResources().getColor(R.color.gray_font_row_info));
		textNameInfo.setTypeface(OmegaFiActivity.getFont(getContext(), 1));
		textSubNameInfo=(TextView)findViewById(R.id.subnameInfoEdit);
		textSubNameInfo.setTextColor(getResources().getColor(R.color.gray_font_row_info));
		textSubNameInfo.setClickable(true);
		}
	
	public void setNameInfo(String name) {
		textNameInfo.setText(name);
	}
	
	public void setSubnameInfo(String name) {
		if(name!=null){
			textSubNameInfo.setText(name);
			textSubNameInfo.setVisibility(View.VISIBLE);
		}
	}
	
	public void setTextSizeInformation(float size){
		textNameInfo.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);
		textSubNameInfo.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);
	}
	
	
	public void setPaddingRow(int left, int top, int right, int bottom){
		content.setPadding(left, top, right, bottom);
	}
	
	public void setBorderBottom(boolean border){
		if(border){
//			   content.setBackgroundResource(R.drawable.border_bottom);
		}
		else{
			content.setBackgroundColor(Color.TRANSPARENT);
		}
	}
	
	public void addViewRight(View view){
		LayoutParams params = (RelativeLayout.LayoutParams)view.getLayoutParams();
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		params.addRule(RelativeLayout.CENTER_VERTICAL);
		view.setLayoutParams(params);	
		content.addView(view);
	}
	
	public void setBackgroundResource(int resource){
		if(resource!=-1){
			content.setBackgroundResource(resource);
		}
	}
	
	public void setBackgroundColor(int color){
		content.setBackgroundResource(0);
		content.setBackgroundColor(color);
	}

	public TextView getTextNameInfo() {
		return textNameInfo;
	}

	public TextView getTextSubNameInfo() {
		return textSubNameInfo;
	}
	
	@Override
	public void setOnClickListener(OnClickListener l) {
		if(textNameInfo!=null){
			textNameInfo.setOnClickListener(l);
			textSubNameInfo.setOnClickListener(l);
		}
		super.setOnClickListener(l);
	}
	
	public void setTextColor(int color){
		textNameInfo.setTextColor(color);
		textSubNameInfo.setTextColor(color);
	}
	
	public String getNameInfo(){
		return textNameInfo.getText().toString();
	}
	
	public String getNameSubInfo(){
		if(textSubNameInfo.getText().toString().contains("\n")){
			return textSubNameInfo.getText().toString().replace("\n", "");
		}
		else{
			return textSubNameInfo.getText().toString();
		}
		
	}
	public int getPaddingRight(){
		return content.getPaddingRight();
	}

}