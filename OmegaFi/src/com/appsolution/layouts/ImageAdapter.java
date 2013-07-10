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
		ImageRoosterName rooster=new ImageRoosterName(context);
		rooster.setNameRooster(listOfficers.get(position).getShortName());
		rooster.setTypeRooster(listOfficers.get(position).getOfficeType());
//		rooster.setImageResource(listImages[position]);
		if(listOfficers.get(position).getHostPhoto()!=null){
			if(listOfficers.get(position).getHostPhoto().equals("OmegaFi")){
				rooster.setHostOmegaFi(true);
			}
			else{
				rooster.setHostOmegaFi(false);
			}
			rooster.chargePhotoOfficer(listOfficers.get(position).getUrlPhoto());
		}
		return rooster;
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
