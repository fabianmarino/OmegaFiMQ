package com.appsolution.layouts;

import com.appsolution.omegafi.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

public class SectionAccountUser extends TableLayout {

	private TextView userNameAccount;
	private TextView descriptionAccount;
	
	public SectionAccountUser(Context context){
		super(context);
		this.inflateLayout();
	}
	
	public SectionAccountUser(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.inflateLayout();
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SectionAccountUser);
	    String textName = a.getString(R.styleable.SectionAccountUser_name_user_account);
	    userNameAccount.setText(textName);
	    
	    String textDescription = a.getString(R.styleable.SectionAccountUser_description_account);
	    descriptionAccount.setText(textDescription);
	    
	    int colorUserName = a.getColor(R.styleable.SectionAccountUser_color_name_user, Color.BLACK);
	    setColorNameUser(colorUserName);
	    
	    a.recycle();
	}
	
	private void  inflateLayout(){
		LayoutInflater inflate= (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflate.inflate(R.layout.section_account_user, this, true);
		userNameAccount = (TextView)findViewById(R.id.textNameUser);
		descriptionAccount=(TextView)findViewById(R.id.textNumberUser);
	}
	
	public void viewNoti(View imagView){
		System.out.println("Aqui iria la nueva notificacion");
	}
	
	public void setTextUserName(String username){
		userNameAccount.setText(username);
	}
	
	public void setDescriptionAccount(String descripction){
		descriptionAccount.setText(descripction);
	}
	
	public void setColorNameUser(int color){
		userNameAccount.setTextColor(color);
	}

}