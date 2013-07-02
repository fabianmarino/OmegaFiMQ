package com.appsolution.layouts;

import java.util.ArrayList;

import com.appsolution.logic.Officer;
import com.appsolution.omegafi.R;
import android.content.Context;
import android.os.AsyncTask;
import android.sax.StartElementListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ImageAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Officer> listOfficers;
	
	private int[] listImages ={ R.drawable.photo_2, R.drawable.photo_3,
		       R.drawable.photo_member, 
		       R.drawable.photo_2, R.drawable.photo_3,
		       R.drawable.photo_member};
	
	public ImageAdapter(Context context){
		this.context=context;
	}
	
	public void setListOfficers(ArrayList<Officer> officers){
		this.listOfficers=officers;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listOfficers.size();
	}
	
	

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void chargeImagesRooster(){
		for (int i = 0; i < getCount(); i++) {
			listOfficers.get(i).chargePhoto();
		}
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ImageRoosterName rooster=new ImageRoosterName(context);
		listOfficers.get(position).setImagePhoto(rooster.getPhotoRooster());
		rooster.setNameRooster(listOfficers.get(position).getShortName());
		rooster.setTypeRooster(listOfficers.get(position).getOfficeType());
		return rooster;
	}
	
	public Officer getOfficer(int position){
		return listOfficers.get(position);
	}

}
