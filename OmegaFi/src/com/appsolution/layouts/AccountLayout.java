package com.appsolution.layouts;
import com.appsolution.omegafi.OmegaFiActivity;
import com.appsolution.omegafi.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AccountLayout extends LinearLayout{

	private TextView textName;
	private TextView chapter;
	private TextView textDueOn;
	private Button viewAccount;
	private Button payNow;
	
	public AccountLayout(Context context){
		super(context);
		this.initialize();
	}
	
	public AccountLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initialize();
	}
	
	public void initialize() {
		LayoutInflater inflate= (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflate.inflate(R.layout.account_layout, this, true);
		textName=(TextView)findViewById(R.id.nameUserAccount);
		textName.setTypeface(OmegaFiActivity.getFont(getContext(), 2));
		chapter=(TextView)findViewById(R.id.nameChaptersFraternity);
		chapter.setTypeface(OmegaFiActivity.getFont(getContext(), 0));
		textDueOn=(TextView)findViewById(R.id.dueOnAccountUser);
		textDueOn.setTypeface(OmegaFiActivity.getFont(getContext(), 1));
		viewAccount=(Button)findViewById(R.id.buttonViewAccount);
		payNow=(Button)findViewById(R.id.buttonPayNowAccount);
	}
	
	public void setListenerViewAccount(OnClickListener listener){
		viewAccount.setOnClickListener(listener);
	}
	
	public void setListenerPayNow(OnClickListener listener){
		payNow.setOnClickListener(listener);
	}
	
	


}
