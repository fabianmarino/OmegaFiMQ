package com.appsolution.omegafi;

import com.appsolution.layouts.RowInformation;

import android.os.Bundle;
import android.util.TypedValue;
import android.widget.LinearLayout;

public class StatementsActivity extends OmegaFiActivity {

	private LinearLayout linearStatements;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statements);
		linearStatements=(LinearLayout)findViewById(R.id.linearStatements);
		this.completeStatements();
	}

	@Override
	protected void optionsActionBar() {
		actionBar.setTitle("Statements");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
	}
	
	private void completeStatements(){
		int padding=(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
		for (int i = 0; i < 16; i++) {
			RowInformation rowpdf=new RowInformation(this);
			rowpdf.setImageIconInfo(R.drawable.pdf_icon);
			rowpdf.setNameInfo("December 1, 2012");
			rowpdf.setVisibleArrow(true);
			rowpdf.setBorderBottom(true);
			rowpdf.setPaddingRow(padding, padding, padding, padding);
			linearStatements.addView(rowpdf);
		}
	}

}
