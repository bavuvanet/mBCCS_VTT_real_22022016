package com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask;

import android.app.Activity;
import android.view.View.OnClickListener;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.MBCCSVasResultBO;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;

import java.util.ArrayList;

public class RegisterOrRemoveVASAsyn extends AsyncTaskCommonSupper<String, Void, ArrayList<MBCCSVasResultBO>>{

	
	
	public RegisterOrRemoveVASAsyn(Activity context, OnPostExecuteListener<ArrayList<MBCCSVasResultBO>> listener,
			OnClickListener moveLogInAct) {
		super(context, listener, moveLogInAct);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ArrayList<MBCCSVasResultBO> doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
