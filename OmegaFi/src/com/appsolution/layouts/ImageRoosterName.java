package com.appsolution.layouts;

import java.io.IOException;

import com.appsolution.omegafi.OmegaFiActivity;
import com.appsolution.omegafi.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ImageRoosterName extends LinearLayout {

	private ImageView photoRooster;
	private TextView nameRooster;
	private TextView typeRooster;
	private AsyncTask<Void, Integer, Boolean> taskChargePhoto;
	private boolean isHostOmegaFi;
	
	public ImageRoosterName(Context context) {
		super(context);
		this.initialize();
	}

	public ImageRoosterName(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initialize();
	}
	
	private void initialize() {
		LayoutInflater inflate= (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflate.inflate(R.layout.image_rooster_name, this, true);
		photoRooster=(ImageView)findViewById(R.id.imageRoosterGalery);
		nameRooster=(TextView)findViewById(R.id.nameRoosterGalery);
		nameRooster.setTypeface(OmegaFiActivity.getFont(getContext(), 0));
		typeRooster=(TextView)findViewById(R.id.typeRoosterGalery);
		typeRooster.setTypeface(OmegaFiActivity.getFont(getContext(), 0));
		isHostOmegaFi=false;
	}
	
	public void setNameRooster(String name){
		nameRooster.setText(name);
	}
	
	public void setTypeRooster(String type){
		typeRooster.setText(type);
	}

	public void setImageBitmap(Bitmap bm) {
		photoRooster.setImageBitmap(bm);
	}

	public void setImageDrawable(Drawable drawable) {
		photoRooster.setImageDrawable(drawable);
	}

	public void setImageLevel(int level) {
		photoRooster.setImageLevel(level);
	}

	public void setImageMatrix(Matrix matrix) {
		photoRooster.setImageMatrix(matrix);
	}

	public void setImageResource(int resId) {
		photoRooster.setImageResource(resId);
	}

	public void setImageState(int[] state, boolean merge) {
		photoRooster.setImageState(state, merge);
	}

	public void setImageURI(Uri uri) {
		photoRooster.setImageURI(uri);
	}
	
	public void setSelectedImageRooster(boolean selected){
		if(selected){
			photoRooster.setBackgroundColor(this.getResources().getColor(R.color.blue_marine));
			nameRooster.setVisibility(View.INVISIBLE);
			typeRooster.setVisibility(View.INVISIBLE);
		}
		else{
			photoRooster.setBackgroundColor(Color.WHITE);
			nameRooster.setVisibility(View.VISIBLE);
			typeRooster.setVisibility(View.VISIBLE);
		}
	}
	
	public ImageView getPhotoRooster() {
		return photoRooster;
	}
	
	public void chargePhotoOfficer(String url){
		if(taskChargePhoto==null){
			this.startChargePhoto(url);
		}
		else{
			if(taskChargePhoto.getStatus()==Status.FINISHED){
				Log.d("Finalizado", "siiiiii");
				this.startChargePhoto(url);
			}
		}
	}

	@Override
	public void setSelected(boolean selected) {
		super.setSelected(selected);
		if(!selected){
			this.setSelectedImageRooster(false);
		}
	}
	
	private void startChargePhoto(final String url){
		taskChargePhoto=new AsyncTask<Void, Integer, Boolean>(){

			Bitmap bitPhoto=null;
			
			@Override
			protected Boolean doInBackground(Void... params) {
					try {
						if(isHostOmegaFi){
							bitPhoto = OmegaFiActivity.servicesOmegaFi.downloadBitmap(url);
							Log.d("charging",nameRooster.getText().toString());
						}
						else{
							bitPhoto = OmegaFiActivity.loadImageFromURL(url);
							Log.d("charging",nameRooster.getText().toString());
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				if(bitPhoto!=null){
					Log.d("charged",nameRooster.getText().toString());
					photoRooster.setImageBitmap(bitPhoto);
					photoRooster.refreshDrawableState();
				}
				bitPhoto=null;
			}
			
		};
		taskChargePhoto.execute();
	}

	public boolean isHostOmegaFi() {
		return isHostOmegaFi;
	}

	public void setHostOmegaFi(boolean isHostOmegaFi) {
		this.isHostOmegaFi = isHostOmegaFi;
	}
	
	
	
}