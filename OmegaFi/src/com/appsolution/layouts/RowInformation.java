package com.appsolution.layouts;

import com.appsolution.omegafi.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RowInformation extends RelativeLayout{

	private RelativeLayout content;
	
	private LinearLayout linearNamesInformation;
	private TextView textNameInfo;
	private TextView textNameSubInfo;
	private TextView textValueInfo;
	private TextView textValueInfo2;
	
	private ImageView arrowImage;
	private String stringSubInformation="";
	private ImageView imageInfo;
	
	public RowInformation(Context context){
		super(context);
		this.initialize();
	}
	
	public RowInformation(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initialize();
		
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.RowInformation);
	    boolean isBorderBottom = a.getBoolean(R.styleable.RowInformation_put_border_bottom,false);
		 setBorderBottom(isBorderBottom);
	
	   String nameInfo = a.getString(R.styleable.RowInformation_name_information);
	   setNameInfo(nameInfo);
	   
	   String nameSubInfo=a.getString(R.styleable.RowInformation_name_subinformation);
	   setNameSubInfo(nameSubInfo);
	   
	   String valueInfo = a.getString(R.styleable.RowInformation_value_information);
	   setValueInfo(valueInfo);
	   
	   float textSize = a.getDimensionPixelSize(R.styleable.RowInformation_row_text_size, 15);
	   setTextSizeInformation(textSize);
	   
	   int colorFont = a.getColor(R.styleable.RowInformation_color_font_row_information, Color.BLACK);
	   setColorFontRowInformation(colorFont);
	   
	   int padding=(int)a.getDimension(R.styleable.RowInformation_padding_row_info, content.getPaddingLeft());
	   setPaddingRow(padding, padding, padding, padding);
	   int paddingLeft=(int)a.getDimension(R.styleable.RowInformation_padding_left_row_info, content.getPaddingLeft());
	   int paddingTop=(int)a.getDimension(R.styleable.RowInformation_padding_top_row_info, content.getPaddingTop());
	   int paddingRight=(int)a.getDimension(R.styleable.RowInformation_padding_right_row_info, content.getPaddingRight());
	   int paddingBottom=(int)a.getDimension(R.styleable.RowInformation_padding_bottom_row_info, content.getPaddingBottom());
	   setPaddingRow(paddingLeft, paddingTop, paddingRight, paddingBottom);
	   
	   boolean showArrow=a.getBoolean(R.styleable.RowInformation_show_arrow_info, false);
	   setVisibleArrow(showArrow);
	   
	   int image=a.getResourceId(R.styleable.RowInformation_icon_drawable, R.drawable.right_arrow_1);
	   setImageIcon(image);
	   
	   int imageInfo = a.getResourceId(R.styleable.RowInformation_icon_drawable_info, -1);
	   setImageIconInfo(imageInfo);
	   
	   String textValue2=a.getString(R.styleable.RowInformation_value_information_2);
	   setValueInfo2(textValue2);
	   
	    a.recycle();
	   
	}
	
	
	private void initialize(){
		LayoutInflater inflate= (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflate.inflate(R.layout.row_information, this, true);
		content=(RelativeLayout)findViewById(R.id.rowInformationContent);
		linearNamesInformation=(LinearLayout)findViewById(R.id.contentNamesInformation);
		textNameInfo=(TextView)findViewById(R.id.nameInfo);
		textNameSubInfo=(TextView)findViewById(R.id.nameSubInfo);
		textValueInfo=(TextView)findViewById(R.id.valueInfo);
		textValueInfo2=(TextView)findViewById(R.id.valueInfo2);
		arrowImage=(ImageView)findViewById(R.id.arrowGrayInfo);
		imageInfo=(ImageView)findViewById(R.id.icon_image_information);
	}
	
	public void setImageIcon(int drawable){
		arrowImage.setVisibility(VISIBLE);
		arrowImage.setImageResource(drawable);
	}
	
	public void setNameInfo(String name) {
		textNameInfo.setText(stringSubInformation+name);
	}
	
	public void setNameSubInfo(String name) {
		if(name != null){
			textNameSubInfo.setVisibility(VISIBLE);
			textNameSubInfo.setText(name);
		}
	}
	
	public void setValueInfo(String value){
		textValueInfo.setText(value);
	}
	
	public void setVisibleArrow(boolean visible){
		if(visible){
			arrowImage.setVisibility(VISIBLE);
		}
		else{
			arrowImage.setVisibility(GONE);
		}
	}
	
	public void setTextSizeInformation(float size){
		textNameInfo.setTextSize(size);
		textValueInfo.setTextSize(size);
	}
	
	public void setSubInformation(boolean isSub){
		if(isSub){
			ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) textNameInfo.getLayoutParams();
			mlp.setMargins(20, 0, 0, 0);
			textNameInfo.setLayoutParams(mlp);
			stringSubInformation="- ";
		}
		else{
			ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) textNameInfo.getLayoutParams();
			mlp.setMargins(0, 0, 0, 0);
			textNameInfo.setLayoutParams(mlp);
			stringSubInformation="";
		}
	}

	
	public void setPaddingRow(int left, int top, int right, int bottom){
		content.setPadding(left, top, right, bottom);
	}
	
	public void setBorderBottom(boolean border){
		if(border){
			content.setBackgroundColor(Color.TRANSPARENT);
			content.setBackgroundResource(R.drawable.border_bottom);
		}
		else{
			 	content.setBackgroundColor(Color.TRANSPARENT);
		}
	}
	
	public void setColorFontRowInformation(int color){
		textNameInfo.setTextColor(color);
		textValueInfo.setTextColor(color);
	}
	
	public void setImageIconInfo(int drawable){
		if(drawable != -1){
			imageInfo.setVisibility(VISIBLE);
			imageInfo.setImageResource(drawable);
		}
	}

	public LinearLayout getLinearNamesInformation() {
		return linearNamesInformation;
	}

	public TextView getTextNameSubInfo() {
		return textNameSubInfo;
	}
	
	public void setValueInfo2(String text){
		if(text!=null){
			textValueInfo2.setVisibility(VISIBLE);
			textValueInfo2.setText(text);
		}
	}
}