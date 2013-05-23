package com.appsolution.layouts;

import com.appsolution.omegafi.HistoriesOmegaAdapter;
import com.appsolution.omegafi.R;
import com.devsmart.android.ui.HorizontalListView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class HorizontalListOmega extends LinearLayout {

	private HorizontalListView horizontalList;
	
	public HorizontalListOmega(Context context){
		super(context);
		this.initialize();
	}
	
	public HorizontalListOmega(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initialize();
	}
	
	public void initialize(){
		LayoutInflater inflate= (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflate.inflate(R.layout.horizontal_list_omegafi, this, true);
		horizontalList=(HorizontalListView)findViewById(R.id.horizontalListOmega);
		horizontalList.setAdapter(new HistoriesOmegaAdapter(getContext()));
	}

}