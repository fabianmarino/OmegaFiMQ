package com.appsolution.layouts;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

public class CustomDatePickerDialog extends DatePickerDialog {
	public CustomDatePickerDialog(Context context,
		OnDateSetListener callBack, int year, int monthOfYear,
		int dayOfMonth) {
			super(context, callBack, year, monthOfYear, dayOfMonth);
		}
		@Override
		public void onDateChanged(DatePicker view, int year, int month, int day) {
			super.onDateChanged(view, year, month, day);
			this.setTitle((month + 1) + "-" + day + "-");
	}
}