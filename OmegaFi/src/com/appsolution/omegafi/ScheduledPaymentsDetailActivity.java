package com.appsolution.omegafi;

import com.appsolution.layouts.DialogInformationOF;
import com.appsolution.layouts.DialogTwoOptionsOF;
import com.appsolution.layouts.RowEditTextOmegaFi;
import com.appsolution.layouts.RowInformation;
import com.appsolution.layouts.SectionOmegaFi;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;

public class ScheduledPaymentsDetailActivity extends OmegaFiActivity {

	private SectionOmegaFi sectionDetails;
	private SectionOmegaFi sectionMethod;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scheduled_payments_detail);
		sectionDetails=(SectionOmegaFi)findViewById(R.id.sectionScheduledPaymentDetails);
		sectionMethod=(SectionOmegaFi)findViewById(R.id.sectionScheduledPaymentMethod);
		
		this.completePaymentDetails();
		this.completePaymentMethod();
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setTitle("Scheduled Payments");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
	}
	
	private void completePaymentDetails(){
		RowEditTextOmegaFi rowEdit1=new RowEditTextOmegaFi(this);
		rowEdit1.setNameInfo("Amount");
		rowEdit1.setEditable(false);
		rowEdit1.setTextEdit("$335.00");
		rowEdit1.setBorderBottom(true);
		 
	
		RowEditTextOmegaFi rowEdit2=new RowEditTextOmegaFi(this);
		rowEdit2.setNameInfo("Payment Date");
		rowEdit2.setTextEdit("04/15/2013");
		rowEdit2.setEditable(false);
		rowEdit2.setBorderBottom(true);
		sectionDetails.addView(rowEdit1);
		sectionDetails.addView(rowEdit2);
	}
	
	private void completePaymentMethod(){
		int padding=(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
		
		RowInformation row=new RowInformation(this);
		row.setNameInfo("Winston Smith - Mastercard (4751)");
		row.setBorderBottom(true);
		row.setVisibleArrow(true);
		row.setPaddingRow(padding, padding, padding, padding);
		sectionMethod.addView(row);
	}
	
	public void saveSchedulePayment(View button){
		final DialogInformationOF saved=new DialogInformationOF(this);
		saved.setMessageDialog("Your paymenth has been saved");
		saved.setButtonListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				saved.dismissDialog();
			}
		});
		saved.showDialog();
	}
	
	public void deleteSchedulePayment(View button){
		final OmegaFiActivity omega=this;
		final DialogTwoOptionsOF yesNo=new DialogTwoOptionsOF(this);
		yesNo.setMessageDialog("Are you sure you want to delete this payment?");
		yesNo.setOption1("Yes");
		yesNo.setOption2("No");
		yesNo.setListenerOption1(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				yesNo.dismissDialog();
				final DialogInformationOF confirm=new DialogInformationOF(omega);
				confirm.setMessageDialog("Your payment has been deleted");
				confirm.setButtonListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						confirm.dismissDialog();
					}
				});
				confirm.showDialog();
			}
		});
		yesNo.showDialog();
	}


}
