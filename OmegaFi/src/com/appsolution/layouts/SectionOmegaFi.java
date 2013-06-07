package com.appsolution.layouts;

import com.appsolution.omegafi.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SectionOmegaFi extends LinearLayout{
	
	private LinearLayout contentAll;
	private RelativeLayout contentTitle;
	private TextView textTitle;
	private TextView textSubTitle;
	private ImageView imageArrow;
	private LinearLayout linearContent;
	
	public SectionOmegaFi(Context context) {
		super(context);
		this.inicializar();
	}

	private void inicializar(){
		LayoutInflater li =(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(R.layout.section_omega_fi, this, true);
		contentAll=(LinearLayout)findViewById(R.id.linearContentSectionOmegaFi);
		linearContent=(LinearLayout)findViewById(R.id.contentSectionOmegaFi);
		textTitle = (TextView)findViewById(R.id.titleSection);
		textSubTitle=(TextView)findViewById(R.id.subtitleSection);
		imageArrow = (ImageView)findViewById(R.id.arrowGray);
		contentTitle=(RelativeLayout)findViewById(R.id.contentTitleSectionOmegaFi);
	}
	
	public void setTitleSection(String title){
		textTitle.setText(title);
	}
	
	public void setSubtitleSection(String subtitle){
		textSubTitle.setText(subtitle);
	}
	
	public void setShowArrow(boolean visible){
		if(visible){
	    	imageArrow.setVisibility(ImageView.VISIBLE);
	    }
	    else{
	    	imageArrow.setVisibility(ImageView.INVISIBLE);
	    }
	}
	
	public void setSizeTitle(float size){
		textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,size);
	}
	
	public void setSizeSubTitle(float size){
		textSubTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,size);
	}
	
	public void setColorTitle(int color){
		textTitle.setTextColor(color);
	}
	
	public void setColorSubTitle(int color){
		textSubTitle.setTextColor(color);
	}
	
	public void setPutBorderBottom(boolean border){
		if(!border){
			int padding=(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getContext().getResources().getDisplayMetrics());
			contentTitle.setBackgroundResource(R.drawable.abs__ab_bottom_transparent_dark_holo);
			contentTitle.setBackgroundColor(Color.TRANSPARENT);
			contentTitle.setPadding(padding,padding,padding,padding);
		}
	}
	
	public SectionOmegaFi(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.inicializar();
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SectionOmegaFi);
		    String textoTitulo = a.getString(R.styleable.SectionOmegaFi_title_section);
		    setTitleSection(textoTitulo);
		    
		    String textSubtitle=a.getString(R.styleable.SectionOmegaFi_subtitle_section);
		    setSubtitleSection(textSubtitle);
		    
		    boolean showArrow = a.getBoolean(R.styleable.SectionOmegaFi_show_arrow,true);
		    setShowArrow(showArrow);
		    
		    float sizeTile = a.getDimensionPixelSize(R.styleable.SectionOmegaFi_sizeTitle, 17);
		    this.setSizeTitle(sizeTile);
		    
		    float sizeSubTitle= a.getDimensionPixelSize(R.styleable.SectionOmegaFi_sizeSubtitle, 15);
		    this.setSizeSubTitle(sizeSubTitle);
		    
		    int color = a.getColor(R.styleable.SectionOmegaFi_colorTitle, Color.BLACK);
		    this.setColorTitle(color);
		    
		    int subcolor= a.getColor(R.styleable.SectionOmegaFi_colorSubtitle, Color.GRAY);
		    this.setColorSubTitle(subcolor);
		    
		    boolean borderBottom=a.getBoolean(R.styleable.SectionOmegaFi_put_border_title, true);
		    this.setPutBorderBottom(borderBottom);
		    
		    boolean visibleSubTitle=a.getBoolean(R.styleable.SectionOmegaFi_visible_subtitle, false);
		    this.setVisibleSubTitle(visibleSubTitle);
		    
		    int colorback = a.getColor(R.styleable.SectionOmegaFi_background_color_section, Color.WHITE);
		    this.setBackgroundColorLinear(colorback);
		    
		    int leftTitle=a.getDimensionPixelSize(R.styleable.SectionOmegaFi_padding_title_left, contentTitle.getPaddingLeft());
		    this.setPaddingLeft(leftTitle);
		    
		    a.recycle();
	}
	
	public void setVisibleSubTitle(boolean visible){
		if(visible){
			int padding=(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getContext().getResources().getDisplayMetrics());
			contentTitle.setPadding(contentTitle.getPaddingLeft(), padding, contentTitle.getPaddingRight(), padding);
			textSubTitle.setVisibility(VISIBLE);
		}
		else{
			textSubTitle.setVisibility(GONE);
		}
	}
	
	@Override
	public void addView(View child) {
		linearContent.addView(child);
	}
	
	public LinearLayout getLinearContent(){
		return linearContent;
	}
	
	public void setBackgroundColorLinear(int color){
		contentAll.setBackgroundColor(color);
	}
	
	public void setPaddingLeft(int left){
		contentTitle.setPadding(left, contentTitle.getPaddingTop(), contentTitle.getPaddingRight(), contentTitle.getPaddingBottom());
	}
	
	public void setOnClickTitleListener(View.OnClickListener evento){
		contentTitle.setOnClickListener(evento);
	}
	
	@Override
	public void setPadding(int left, int top, int right, int bottom) {
		contentAll.setPadding(left, top, right, bottom);
	}

}
