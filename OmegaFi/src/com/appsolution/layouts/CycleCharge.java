package com.appsolution.layouts;

import java.util.ArrayList;

import com.appsolution.logic.Charge;
import com.appsolution.omegafi.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;


public class CycleCharge extends LinearLayout implements View.OnClickListener{

	private RowTripleInformation rowTriple;
	private LinearLayout linearCharges;
	private ArrayList<Charge> listCharges;
	
	public CycleCharge(Context context){
		super(context);
		this.initialize();
	}
	
	public CycleCharge(Context context, AttributeSet attrs) {
		super(context, attrs);	
		this.initialize();
		
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CycleCharge);
		
		String first=a.getString(R.styleable.CycleCharge_title_line);
		setTitleLine(first);
		
		String second=a.getString(R.styleable.CycleCharge_second_line);
		setSecondLine(second);
		
		String third=a.getString(R.styleable.CycleCharge_third_line);
		setThirdLine(third);
		
		String valueInfo=a.getString(R.styleable.CycleCharge_value_info_cycle);
		setValueInfo(valueInfo);
		
		a.recycle();
	}

	private void initialize(){
		LayoutInflater inflate= (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflate.inflate(R.layout.cycle_charge, this, true);
		rowTriple=(RowTripleInformation)findViewById(R.id.titleCycleCharge);
		rowTriple.setImageIcon(R.drawable.right_down);
		rowTriple.setOnClickListener(this);
		linearCharges=(LinearLayout)findViewById(R.id.contentScheduleCycle);
	}
	
	private void completeCycleCharges(){
		linearCharges.removeAllViews();
		if(listCharges!=null){
			for (Charge chargeAux:listCharges) {
				RowInformation row=new RowInformation(super.getContext());
				row.setNameInfo(chargeAux.getDescription());
				row.setValueInfo("$"+chargeAux.getAmount());
				this.addRowCharge(row);
			}
		}
	}
	
	public void setTitleLine(String line){
		rowTriple.setNameInfo(line);
	}
	
	public void setSecondLine(String second){
		rowTriple.setNameSubInfo(second);
	}
	
	public void setThirdLine(String third){
		rowTriple.setThirdLine(third);
	}
	
	public void setNamesInfo(String first, String second, String third){
		setTitleLine(first);
		setSecondLine(second);
		setThirdLine(third);
	}
	
	public void setValueInfo(String value){
		rowTriple.setValueInfo(value);
	}
	
	public void addRowCharge(RowInformation row){
		linearCharges.addView(row);
	}
	
	public void showHideChargesLines(){
		if(linearCharges.getVisibility()==LinearLayout.GONE){
			this.completeCycleCharges();
			linearCharges.setVisibility(VISIBLE);
			rowTriple.setImageIcon(R.drawable.right_up);
		}
		else{
			linearCharges.setVisibility(GONE);
			rowTriple.setImageIcon(R.drawable.right_down);
		}
	}
	
	

	public ArrayList<Charge> getListCharges() {
		return listCharges;
	}

	public void setListCharges(ArrayList<Charge> listCharges) {
		this.listCharges = listCharges;
	}

	@Override
	public void onClick(View v) {
		showHideChargesLines();
	}
		
}