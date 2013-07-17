package com.appsolution.layouts;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.appsolution.logic.Account;
import com.appsolution.logic.CalendarEvent;
import com.appsolution.omegafi.OmegaFiActivity;
import com.appsolution.omegafi.R;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AccountLayout extends LinearLayout{

	private TextView textName;
	private TextView chapter;
	private TextView textDueOn;
	private Button viewAccount;
	private Button payNow;
	private LabelInfoVertical account;
	private LabelInfoVertical balanceDue;
	private LabelInfoVertical current;
	private LinearLayout linearNotification;
	
	
	private int idAccount;
	
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
		account=(LabelInfoVertical)findViewById(R.id.labelInfoAccount);
		balanceDue=(LabelInfoVertical)findViewById(R.id.labelInfoBalanceDue);
		current=(LabelInfoVertical)findViewById(R.id.labelInfoCurrent);
		 linearNotification=(LinearLayout)findViewById(R.id.linearNotifications);
	}
	
	public void setListenerViewAccount(OnClickListener listener){
		viewAccount.setOnClickListener(listener);
	}
	
	public void setListenerPayNow(OnClickListener listener){
		payNow.setOnClickListener(listener);
	}
	
	public void setAccount(Account account){
			idAccount=account.getId();
			textName.setText(account.getCompleteName());
			chapter.setText(account.getNameOrgDesignationOrg());
			this.account.setValueLabel(""+idAccount);
			this.balanceDue.setValueLabel(account.getAdjustedBalance());
			this.current.setValueLabel(account.getCurrentBalance());
				textDueOn.setText("Due on: "+account.getDueOn());
				
			for (String notification:account.getListNotifications()) {
				AccountNotification noti=new AccountNotification(getContext());
				noti.setTextNotification(notification);
				linearNotification.addView(noti);
			}
		}

	public int getIdAccount() {
		return idAccount;
	}
	
	
	


}
