package com.appsolution.omegafi;

import java.util.Calendar;

import com.appsolution.layouts.RowEditInformation;
import com.appsolution.layouts.SectionOmegaFi;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class EditCardInformation extends OmegaFiActivity {

	private RowEditInformation rowName;
	private RowEditInformation rowNumber;
	private RowEditInformation rowDate;
	private SectionOmegaFi sectionAddress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_card_information);
		rowName=(RowEditInformation)findViewById(R.id.editNameCard);
		rowNumber=(RowEditInformation)findViewById(R.id.editNumberCard);
		rowDate=(RowEditInformation)findViewById(R.id.editDateCard);
		sectionAddress=(SectionOmegaFi)findViewById(R.id.sectionEditAddress);
		this.completeEditCardInformation();
		this.completeSetionEditAddress();
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setTitle("Edit Card Details");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
	}
	
	private void completeEditCardInformation(){
		EditText editTexto=new EditText(this);
		editTexto.setLayoutParams(new RelativeLayout.LayoutParams(
				(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics()),
				android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT));
		rowName.addViewRight(editTexto);
		
		EditText passwordText=new EditText(this);
		passwordText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
		passwordText.setLayoutParams(new RelativeLayout.LayoutParams(
				(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics()),
				android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT));
		rowNumber.addViewRight(passwordText);
		
		TextView textFecha=new TextView(this);
		textFecha.setTextSize(15f);
		textFecha.setText(Calendar.getInstance().getTime().toGMTString());
		textFecha.setLayoutParams(new RelativeLayout.LayoutParams(
				android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT,
				android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT));
		textFecha.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final Calendar c = Calendar.getInstance();
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH);
				int day = c.get(Calendar.DAY_OF_MONTH);
				DatePickerDialog.OnDateSetListener dateset=new DatePickerDialog.OnDateSetListener() {
					
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear,
							int dayOfMonth) {
						// TODO Auto-generated method stub
						
					}
				};
				 Dialog dateDialog= new  DatePickerDialog(getApplicationContext(), dateset, year, month, day);
				 dateDialog.show();
				
			}
		});
		rowDate.addViewRight(textFecha);
	}
	
	private void completeSetionEditAddress(){
		LinearLayout linear=(LinearLayout)sectionAddress.findViewById(R.id.contentSectionOmegaFi);
		EditText edit=new EditText(this);
		edit.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		EditText edit2=new EditText(this);
		edit2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		linear.addView(edit);
		linear.addView(edit2);
	}
	
	public static class DatePickerFragment extends DialogFragment
    implements DatePickerDialog.OnDateSetListener {

			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				// Use the current date as the default date in the picker
				final Calendar c = Calendar.getInstance();
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH);
				int day = c.get(Calendar.DAY_OF_MONTH);
				// Create a new instance of DatePickerDialog and return it
				return new DatePickerDialog(getActivity(), this, year, month, day);
			}
			
			public void onDateSet(DatePicker view, int year, int month, int day) {
			// Do something with the date chosen by the user
			}
	}
	
}
