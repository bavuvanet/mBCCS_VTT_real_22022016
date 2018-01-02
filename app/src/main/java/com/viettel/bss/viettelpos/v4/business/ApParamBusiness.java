package com.viettel.bss.viettelpos.v4.business;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.bankplus.object.TelecomSuppliers;
import com.viettel.bss.viettelpos.v4.bo.ApParamBO;
import com.viettel.bss.viettelpos.v4.dal.ApParamDAL;

public class ApParamBusiness {

	private static final String LOG_TAG = ApParamBusiness.class.getName();

	public static String getListMenu(Context context, String[] keyMenu) {
		try {
			ApParamDAL dal = new ApParamDAL(context);
			dal.open();
			String result = dal.getListMenu(keyMenu);
			dal.close();
			return result;
		} catch (Exception e) {
			Log.e(LOG_TAG, e.toString(), e);
		}
		return "";
	}

	public static String getValue(Context context, String par_name,
			String par_type) {
		try {
			ApParamDAL dal = new ApParamDAL(context);
			return dal.getValue(par_name, par_type);
		} catch (Exception e) {
			Log.e("Exception", "Exception", e);
		}
		return "";
	}

	public static ArrayList<TelecomSuppliers> getLstTelecomSuppliers(
			Context context, String parType) {
		try {
			ApParamDAL dal = new ApParamDAL(context);
			return dal.getLstTelecomSuppliers(parType);
		} catch (Exception e) {
		}
		return null;
	}

	public static ArrayList<ApParamBO> getLstApParamByName(Context context,
			String parName) {
		try {
			ApParamDAL dal = new ApParamDAL(context);
			return dal.getLstApParamByName(parName);
		} catch (Exception e) {

		}
		return null;
	}
}
