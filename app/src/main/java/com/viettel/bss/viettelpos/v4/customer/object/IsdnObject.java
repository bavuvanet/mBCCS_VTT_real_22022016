package com.viettel.bss.viettelpos.v4.customer.object;

import java.io.Serializable;
import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;

import android.app.Activity;

@SuppressWarnings("serial")
public class IsdnObject implements Serializable {

	private String account;
	private String createDate;
	private String actionName;
	private ArrayList<ProfileUploadObj> listProfileUploadObj;

	public ArrayList<ProfileUploadObj> getListProfileUploadObj() {
		return listProfileUploadObj;
	}

	public void setListProfileUploadObj(ArrayList<ProfileUploadObj> listProfileUploadObj) {
		this.listProfileUploadObj = listProfileUploadObj;
	} 

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setCreateName(String actionCode, Activity activity) {
		if (actionCode.equalsIgnoreCase("00")) { // dau noi co dinh
			this.actionName = activity.getString(R.string.connect_permanent);
		} else if (actionCode.equalsIgnoreCase("01")) { // dau noi mobile tra
														// truoc
			this.actionName = activity.getString(R.string.connect_moble_prepay);
		} else if (actionCode.equalsIgnoreCase("02")) { // dau noi mobile tra
														// sau
			this.actionName = activity.getString(R.string.connect_moble_postpaid);
		} else if (actionCode.equalsIgnoreCase("03")) { // dang ky thong tin
			this.actionName = activity.getString(R.string.customer_new);
		}
	} 
	
	
	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	
	
	
}
