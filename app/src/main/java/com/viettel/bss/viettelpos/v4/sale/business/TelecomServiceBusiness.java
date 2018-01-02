package com.viettel.bss.viettelpos.v4.sale.business;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.sale.dal.TelecomServiceDAL;
import com.viettel.bss.viettelpos.v4.sale.object.TelecomServiceObject;

public class TelecomServiceBusiness {
	public static ArrayList<TelecomServiceObject> getAllTelecomService(Context context){
		
		TelecomServiceDAL dal=null;
		try {
			dal = new TelecomServiceDAL(context);
			dal.open();
			ArrayList<TelecomServiceObject> result = dal.getAllTelecomService();
			dal.close();
			return result;
		} catch (Exception e) {
			if(dal!= null){
				try {
					dal.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			Log.e(TelecomServiceBusiness.class.getName(),"Exception",e);
			return null;
		}finally{
			if(dal!= null){
				try {
					dal.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
}
