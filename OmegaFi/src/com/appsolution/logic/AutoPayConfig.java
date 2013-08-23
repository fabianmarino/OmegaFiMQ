package com.appsolution.logic;

import org.json.JSONException;
import org.json.JSONObject;

public class AutoPayConfig {

	private int idCreditOrECheck;
	private boolean eCheck;
	private String beginDate;
	private String endDate;
	private boolean payOnDueDate;
	private int paymentDayOfMonth;
	public static final int PAY_AMOUNT_DUE=1;
	public static final int PAY_SPECIFIC_AMOUNT=2;
	private int typePaymenthAmount;
	private int id=-1;
	
	/**
	 * If amountEnterMax < 0 is No Maximum amount
	 */
	private float amountEnterMax=0;
	
	
	public AutoPayConfig() {
		this.clearInformation();
	}
	
	public AutoPayConfig(JSONObject jsonAutoPay){
		try {
			if(jsonAutoPay!=null){
				JSONObject autoPayRecord=jsonAutoPay.getJSONObject("autopay_record");
				id=autoPayRecord.getInt("memberrecurringpaymentid");
				if(!autoPayRecord.isNull("memberpaymentcreditcardid")){
					eCheck=false;
					idCreditOrECheck=autoPayRecord.getInt("memberpaymentcreditcardid");
				}
				else{
					eCheck=true;
					idCreditOrECheck=autoPayRecord.getInt("memberpaymentecheckid");
				}
				beginDate=autoPayRecord.getString("begindate");
				if(!autoPayRecord.isNull("enddate")){
					endDate=autoPayRecord.getString("enddate");
				}
				paymentDayOfMonth=autoPayRecord.getInt("dayofmonth");
				payOnDueDate=autoPayRecord.getBoolean("payonduedate");
				if(autoPayRecord.getBoolean("paydueamount")){
					typePaymenthAmount=AutoPayConfig.PAY_AMOUNT_DUE;
					if(!autoPayRecord.isNull("capamount"))
						amountEnterMax=Float.parseFloat(autoPayRecord.getString("capamount").replace("$", ""));
					else
						amountEnterMax=-1;
				}
				else{
					typePaymenthAmount=AutoPayConfig.PAY_SPECIFIC_AMOUNT;
					if(!autoPayRecord.isNull("paymentamount"))
						amountEnterMax=Float.parseFloat(autoPayRecord.getString("paymentamount").replace("$", ""));
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Date complete (date + hour)
	 * @return
	 */
	public String getBeginDate() {
		return beginDate;
	}
	
	public void setBeginDate(String date) {
		beginDate=date;
	}
	
	/**
	 * Only the date on a specific format
	 * @return
	 */
	public String getBeginDatePretty(){
		String begin=null;
		if(beginDate!=null){
			if(beginDate.length()>=10){
				begin=CalendarEvent.getFormatDate(3, beginDate.substring(0,10), "yyyy/MM/dd");
			}
		}
		return begin;
	}
	
	public String getBeginDateRequest(){
		String begin=null;
		if(beginDate!=null){
			if(beginDate.length()>=10){
				begin=CalendarEvent.getFormatDate(5, beginDate.substring(0,10), "yyyy/MM/dd");
			}
		}
		return begin;
	}
	
	public void setAutoPayConfig(AutoPayConfig config){
		id=config.getId();
		beginDate=config.getBeginDate();
		endDate=config.getEndDate();
		paymentDayOfMonth=config.getPaymentDayOfMonth();
		typePaymenthAmount=config.getTypePaymenthAmount();
		eCheck=config.iseCheck();
		idCreditOrECheck=config.getIdCreditOrECheck();
		amountEnterMax=config.getAmountEnterMax();
		payOnDueDate=config.isPayOnDueDate();
	}

	public String getEndDate() {
		return endDate;
	}
	
	public String getEndDatePretty(){
		String end=null;
		if(endDate!=null){
			if(endDate.length()>=10){
				end=CalendarEvent.getFormatDate(3, endDate.substring(0,10), "yyyy/MM/dd");
			}
		}
		return end;
	}
	
	public String getEndDateRequest(){
		String end=null;
		if(endDate!=null){
			if(endDate.length()>=10){
				end=CalendarEvent.getFormatDate(5, endDate.substring(0,10), "yyyy/MM/dd");
			}
		}
		return end;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public int getPaymentDayOfMonth() {
		return paymentDayOfMonth;
	}


	public void setPaymentDayOfMonth(int paymentDayOfMonth) {
		this.paymentDayOfMonth = paymentDayOfMonth;
	}


	public int getTypePaymenthAmount() {
		return typePaymenthAmount;
	}


	public void setTypePaymenthAmount(int typePaymenthAmount) {
		this.typePaymenthAmount = typePaymenthAmount;
	}


	public float getAmountEnterMax() {
		return amountEnterMax;
	}


	public void setAmountEnterMax(float amountEnterMax) {
		this.amountEnterMax = amountEnterMax;
	}
	
	public void clearInformation(){
		id=-1;
		beginDate=null;
		endDate=null;
		payOnDueDate=false;
		paymentDayOfMonth=1;
		typePaymenthAmount=AutoPayConfig.PAY_AMOUNT_DUE;
		eCheck=false;
		idCreditOrECheck=0;
		amountEnterMax=0;
	}

	public int getIdCreditOrECheck() {
		return idCreditOrECheck;
	}

	public boolean iseCheck() {
		return eCheck;
	}

	public int getId() {
		return id;
	}

	public void setIdCreditOrECheck(int idCreditOrECheck) {
		this.idCreditOrECheck = idCreditOrECheck;
	}

	public void seteCheck(boolean eCheck) {
		this.eCheck = eCheck;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isPayOnDueDate() {
		return payOnDueDate;
	}

	public void setPayOnDueDate(boolean payOnDueDate) {
		this.payOnDueDate = payOnDueDate;
	}
	
}