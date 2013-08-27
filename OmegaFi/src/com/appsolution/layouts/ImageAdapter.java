package com.appsolution.layouts;

import java.util.ArrayList;
import com.appsolution.logic.Officer;
import com.appsolution.omegafi.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ImageAdapter extends BaseAdapter {

	private Activity context;
	private ArrayList<Officer> listOfficers;
	
	private int[] listImages ={ R.drawable.photo_2, R.drawable.photo_3,
		       R.drawable.photo_member, 
		       R.drawable.photo_2, R.drawable.photo_3,
		       R.drawable.photo_member};
	
	public ImageAdapter(Activity context){
		this.context=context;
	}
	
	public void setListOfficers(ArrayList<Officer> officers){
		this.listOfficers=officers;
	}
	
	@Override
	public int getCount() {
		return listOfficers.size();
//		return listImages.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			LayoutInflater inflate = context.getLayoutInflater();  
	         convertView= (View) inflate.inflate(R.layout.layout_image_rooster_name, null);
			ImageRoosterName rooster=(ImageRoosterName)convertView.findViewById(R.id.userContactOfficer);
			rooster.setNameRooster(listOfficers.get(position).getShortName());
			rooster.setTypeRooster(listOfficers.get(position).getOfficeType());
	//		rooster.setImageResource(listImages[position]);
			if(listOfficers.get(position).sourcePhoto()!=null){
				rooster.chargePhotoOfficer(listOfficers.get(position).sourcePhoto(),listOfficers.get(position).getUrlPhoto());
			}
		}
		return convertView;
	}
	
	public Officer getOfficer(int position){
		return listOfficers.get(position);
	}
	
	public void changeOrderImages(){
		if(listImages[0]==R.drawable.photo_2){
			listImages[0]=R.drawable.photo_member;
			listImages[2]=R.drawable.photo_1;
			listImages[1]=R.drawable.photo_3;
		}
		else{
			listImages[0]=R.drawable.photo_2;
			listImages[3]=R.drawable.photo_1;
			listImages[5]=R.drawable.photo_member;
			listImages[1]=R.drawable.photo_3;
		}
	}

}
