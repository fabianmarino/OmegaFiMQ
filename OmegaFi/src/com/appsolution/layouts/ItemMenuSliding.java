package com.appsolution.layouts;

import com.appsolution.omegafi.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ItemMenuSliding extends RelativeLayout {

	private ImageView imageItem;
	private TextView textItem;
	private RelativeLayout contentNumber;
	private TextView textNumber;
	
	public ItemMenuSliding(Context context) {
		super(context);
		this.initialize();
	}

	public ItemMenuSliding(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initialize();
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ItemMenuSliding);
		
		String item=a.getString(R.styleable.ItemMenuSliding_text_item_menu);
		setTextItemMenu(item);
		int drawable=a.getResourceId(R.styleable.ItemMenuSliding_image_item_menu, R.drawable.white_circle);
		setImageItemMenu(drawable);
		int number=a.getInt(R.styleable.ItemMenuSliding_number_notifications, 0);
		setNumberNotifications(number);
		
		a.recycle();
	}

	private void initialize(){
		LayoutInflater inflate= (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflate.inflate(R.layout.item_menu_sliding, this, true);
		textItem=(TextView)findViewById(R.id.textItemMenuSliding);
		imageItem=(ImageView)findViewById(R.id.iconItemMenuSliding);
		contentNumber=(RelativeLayout)findViewById(R.id.contentNumberNotifications);
		textNumber=(TextView)findViewById(R.id.textNumberNotifySliding);
	}
	
	public void setImageItemMenu(int resource){
		imageItem.setImageResource(resource);
	}
	
	public void setTextItemMenu(String text){
		textItem.setText(text);
	}
	
	public void setNumberNotifications(int number){
		if(number<=0){
			contentNumber.setVisibility(GONE);
		}
		else{
			contentNumber.setVisibility(VISIBLE);
		}
		textNumber.setText(number+"");
	}
	
	

}
