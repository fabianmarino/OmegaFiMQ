package com.appsolution.omegafi;

import com.appsolution.layouts.CycleCharge;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class ScheduleChargesActivity extends OmegaFiActivity {

	private LinearLayout linearCycles;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule_charges);
		linearCycles=(LinearLayout)findViewById(R.id.linearCyclesCharges);
		this.completeCyclesCharges();
	}

	@Override
	protected void optionsActionBar() {
		actionBar.setTitle("Schedule of Charges");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
	}
	
	private void completeCyclesCharges(){
		for (int i = 0; i < 17; i++) {
			CycleCharge cycle=new CycleCharge(this);
			LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			params.setMargins(0, 0, 0, 10);
			cycle.setLayoutParams(params);
			cycle.setNamesInfo("Cycle "+i, "Billed: 01/04/2013","Due: 02/05/2013");
			cycle.setValueInfo("$110.00");
			linearCycles.addView(cycle);
		}
	}

}
