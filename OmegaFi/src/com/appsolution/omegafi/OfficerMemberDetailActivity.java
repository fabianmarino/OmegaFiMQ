package com.appsolution.omegafi;

import com.appsolution.layouts.ImageMemberTemplate;
import com.appsolution.layouts.RowInformation;
import com.appsolution.layouts.SectionOmegaFi;

import android.os.Bundle;
import android.app.Activity;
import android.util.TypedValue;
import android.view.Menu;
import android.widget.TextView;

public class OfficerMemberDetailActivity extends OmegaFiActivity {

	private ImageMemberTemplate headerMember;
	private TextView textInitiate;
	
	private SectionOmegaFi sectionContact;
	private SectionOmegaFi sectionEmail;
	private SectionOmegaFi sectionAddress;
	private int padding;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_officer_member_detail);
		padding=(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
		headerMember=(ImageMemberTemplate)findViewById(R.id.imageMemberHeader);
		sectionContact=(SectionOmegaFi)findViewById(R.id.sectionOMContact);
		sectionEmail=(SectionOmegaFi)findViewById(R.id.sectionOMEmail);
		sectionAddress=(SectionOmegaFi)findViewById(R.id.sectionOMAddress);
		this.completeImageHeaderMember();
		this.completeContact();
		this.completeEmail();
		this.completeAddress();
	}
	
	@Override
	protected void optionsActionBar() {
		actionBar.setTitle("Last, FirstName");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
	}
	
	private void completeContact(){
		RowInformation row1=new RowInformation(this);
		row1.setNameInfo("Main");
		row1.setValueInfo("(757)899-7657");
		row1.setBorderBottom(true);
		row1.setPaddingRow(padding, padding, padding, padding);
		RowInformation row2=new RowInformation(this);
		row2.setNameInfo("Other");
		row2.setValueInfo("(757)899-7657");
		row2.setBorderBottom(true);
		row2.setPaddingRow(padding, padding, padding, padding);
		sectionContact.addView(row1);
		sectionContact.addView(row2);
	}
	
	private void completeEmail(){
		RowInformation row1=new RowInformation(this);
		row1.setNameInfo("Main");
		row1.setValueInfo("gfjhf@example.com");
		row1.setBorderBottom(true);
		row1.setPaddingRow(padding, padding, padding, padding);
		RowInformation row2=new RowInformation(this);
		row2.setNameInfo("Other");
		row2.setValueInfo("gfjhf@example.com");
		row2.setBorderBottom(true);
		row2.setPaddingRow(padding, padding, padding, padding);
		sectionEmail.addView(row1);
		sectionEmail.addView(row2);
	}

	private void completeAddress(){
		RowInformation row1=new RowInformation(this);
		row1.setNameInfo("Home");
		row1.setValueInfo("2213 NY 54634-0989");
		row1.setValueInfo2("Troy, 2213 NY 54634-0989");
		row1.setBorderBottom(true);
		row1.setPaddingRow(padding, padding, padding, padding);
		RowInformation row2=new RowInformation(this);
		row2.setNameInfo("School");
		row2.setValueInfo("123 Campbell Rd");
		row2.setValueInfo2("West Sunbury, PA 65464-1701");
		row2.setBorderBottom(true);
		row2.setPaddingRow(padding, padding, padding, padding);
		sectionAddress.addView(row1);
		sectionAddress.addView(row2);
	}
	
	private void completeImageHeaderMember(){
		textInitiate=new TextView(this);
		textInitiate.setText("Initiate  - MM/DD/YYYY");
		headerMember.addViewToBasic(textInitiate);
		
		RowInformation rowFace=new RowInformation(this);
		rowFace.setImageIconInfo(R.drawable.face_char);
		rowFace.setNameInfo("facebook - handle");
		rowFace.setBorderBottom(true);
		
		RowInformation rowTwitter=new RowInformation(this);
		rowTwitter.setImageIconInfo(R.drawable.twiiter_char);
		rowTwitter.setNameInfo("twitter - handle");
		rowTwitter.setBorderBottom(true);

		RowInformation rowLinked=new RowInformation(this);
		rowLinked.setImageIconInfo(R.drawable.linkedin_char);
		rowLinked.setNameInfo("linkedin - handle");
		rowLinked.setBorderBottom(true);
		
		headerMember.addViewToAditional(rowFace);
		headerMember.addViewToAditional(rowTwitter);
		headerMember.addViewToAditional(rowLinked);
	}

}
