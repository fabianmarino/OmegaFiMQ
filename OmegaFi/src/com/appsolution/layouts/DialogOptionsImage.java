package com.appsolution.layouts;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.appsolution.omegafi.R;

public class DialogOptionsImage {
	private AlertDialog.Builder builderDialog;
	private AlertDialog alertDialog;
	private Activity activity;
	private LayoutInflater layoutInflater;
	
	private Button buttonLibrary;
	private Button buttonCamera;
	
	private Button buttonCancel;
	
	public DialogOptionsImage(Activity activity){
		this.activity=activity;
		layoutInflater=(LayoutInflater)this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = layoutInflater.inflate(R.layout.dialog_profile_image,
				(ViewGroup)this.activity.findViewById(R.id.layout_root_image));
		builderDialog = new AlertDialog.Builder(activity);
		builderDialog.setView(layout);
		
		buttonLibrary=(Button)layout.findViewById(R.id.buttonChooseFromLibrary);
		buttonCamera=(Button)layout.findViewById(R.id.buttonTakePhoto);
		
		buttonCancel=(Button)layout.findViewById(R.id.buttonCancelImage);
		buttonCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismissDialog();			
			}
		});
		
		alertDialog = builderDialog.create();
	}
	
	public void showDialog(){
		alertDialog.show();
	}
	
	public void dismissDialog(){
		alertDialog.dismiss();
	}
	
	public void setOnClickLibrary(OnClickListener listener){
		buttonLibrary.setOnClickListener(listener);
	}
	
	public void setOnClickCamera(OnClickListener listener){
		buttonCamera.setOnClickListener(listener);
	}
	
}
