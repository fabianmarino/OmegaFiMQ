package com.appsolution.layouts;

import com.appsolution.omegafi.R;
import com.appsolution.omegafi.R.drawable;

import android.R.dimen;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {

	private Context context;
	private int[] listImages ={ R.drawable.photo_2, R.drawable.photo_3,
		       R.drawable.photo_member, 
		       R.drawable.photo_2, R.drawable.photo_3,
		       R.drawable.photo_member};
	
	
	public ImageAdapter(Context context){
		this.context=context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listImages.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return listImages[position];
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
					LinearLayout linear=new LinearLayout(context);
					linear.setGravity(Gravity.CENTER_HORIZONTAL);
					linear.setOrientation(LinearLayout.VERTICAL);
					ImageView iv = new ImageView(this.context);
					iv.setImageResource(this.listImages[position]);
					iv.setAdjustViewBounds(true);
					iv.setLayoutParams(new Gallery.LayoutParams(context.getResources().getDimensionPixelSize(R.dimen.width_image_chapter), 
							context.getResources().getDimensionPixelSize(R.dimen.height_image_chapter)));
					
					TextView textView=new TextView(context);
					android.widget.LinearLayout.LayoutParams params=new android.widget.LinearLayout.LayoutParams
							(android.widget.LinearLayout.LayoutParams.MATCH_PARENT,android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
					params.gravity=Gravity.CENTER_HORIZONTAL;
					textView.setGravity(Gravity.CENTER_HORIZONTAL);
					textView.setText("Title name");
					
					linear.addView(iv);
					linear.addView(textView);
					return linear;
	}

}
