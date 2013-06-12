package com.appsolution.omegafi;

import com.appsolution.layouts.DialogInformationOF;
import com.appsolution.layouts.DialogSelectableOF;
import com.appsolution.layouts.RowEditInformation;
import com.appsolution.layouts.RowEditNameTopInfo;
import com.appsolution.layouts.RowEditTextOmegaFi;
import com.appsolution.layouts.RowInformation;
import com.appsolution.layouts.RowToogleOmegaFi;
import com.appsolution.layouts.SectionOmegaFi;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

public class AddNewPaymentActivity extends OmegaFiActivity {

	private LinearLayout linearCreditDebit;
	private RowEditTextOmegaFi rowTextNameOnCard;
	private RowInformation rowSelectCardType;
	private RowEditTextOmegaFi rowTextNumberCard;
	private RowInformation rowEditExpirationDate;
	private RowEditTextOmegaFi rowTextZipCode;
	private RowToogleOmegaFi rowSaveForFutureUse;
	
	private LinearLayout linearChekingAccount;
	
	private SectionOmegaFi sectionAddress;
	private RowEditNameTopInfo rowEditAddres1;
	private RowEditNameTopInfo rowEditAddres2;
	
	private Button buttonAddContinue;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_payment);
		linearCreditDebit=(LinearLayout)findViewById(R.id.linearCreditDebitCard);
		linearChekingAccount=(LinearLayout)findViewById(R.id.linearCheckingAccount);
		rowTextNameOnCard=(RowEditTextOmegaFi)findViewById(R.id.textNameOnCard);
		rowSelectCardType=(RowInformation)findViewById(R.id.selectCardType);
		rowEditExpirationDate=(RowInformation)findViewById(R.id.editExpirationDate);
		rowSaveForFutureUse=(RowToogleOmegaFi)findViewById(R.id.rowSaveForFutureUse);
		
		sectionAddress=(SectionOmegaFi)findViewById(R.id.sectionAddresAddPayment);
		
		buttonAddContinue=(Button)findViewById(R.id.buttonAddContinue);
		
		this.completeFields();
	}
	
	private void completeFields(){
		rowSaveForFutureUse.setOnChangeListenerToogle(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					buttonAddContinue.setText("Add");
				}
				else{
					buttonAddContinue.setText("Continue");
				}
			}
		});
		
		rowEditAddres1=new RowEditNameTopInfo(this);
		rowEditAddres1.setWidthFillParent(true);
		rowEditAddres1.setNameInfoTop("Address Line 1");
		
		rowEditAddres2=new RowEditNameTopInfo(this);
		rowEditAddres2.setWidthFillParent(true);
		rowEditAddres2.setNameInfoTop("Address Line 1");
		
		sectionAddress.addView(rowEditAddres1);
		sectionAddress.addView(rowEditAddres2);
		
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setTitle("Add New Payment");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
	}
	
	public void changeTypePayment(View view){
		if(linearCreditDebit.getVisibility()==LinearLayout.VISIBLE){
			linearCreditDebit.setVisibility(LinearLayout.GONE);
			linearChekingAccount.setVisibility(LinearLayout.VISIBLE);
		}
		else{
			linearChekingAccount.setVisibility(LinearLayout.GONE);
			linearCreditDebit.setVisibility(LinearLayout.VISIBLE);
		}

	}
	
	public void selectCardType(View view){
		DialogSelectableOF selectable=new DialogSelectableOF(this);
		selectable.setTitleDialog("Select Card Type");
		selectable.setTextButton(null);
		selectable.showDialog();
	}
	
	public void addContinueNewPayment(View view){
		final DialogInformationOF dialog=new DialogInformationOF(this);
		dialog.setMessageDialog("You've successfuly added a payment method");
		dialog.setButtonListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismissDialog();
			}
		});
		dialog.showDialog();
	}
	
	
	
	public void showPopupMenu(View v){
/*		   PopupWindow popupMenu = new PopupMenu(this, v);
		      popupMenu.getMenuInflater().inflate(R.menu.popup_menu_select_payment_method, popupMenu.getMenu());
		    
		      popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
				   @Override
				   public boolean onMenuItemClick(MenuItem item) {
					   	Toast.makeText(getApplicationContext(),
						item.toString(),
					   Toast.LENGTH_LONG).show();
				    return true;
				   }
				  });
				    
		      popupMenu.show();
		      */		
		  }
}