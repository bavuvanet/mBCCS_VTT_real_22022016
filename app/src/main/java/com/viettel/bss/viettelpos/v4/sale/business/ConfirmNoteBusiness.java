package com.viettel.bss.viettelpos.v4.sale.business;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.sale.dal.ConfirmNoteDAL;
import com.viettel.bss.viettelpos.v4.sale.object.Serial;
import com.viettel.bss.viettelpos.v4.sale.object.StockTransAction;
import com.viettel.bss.viettelpos.v4.sale.object.StockTransDetail;

public class ConfirmNoteBusiness {
	private final static String TAG = ConfirmNoteBusiness.class.getName();

	/**
	 * Lay danh sach phieu xuat
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<StockTransAction> getLstStockTrans(Context context) {
		ArrayList<StockTransAction> result = new ArrayList<>();
		ConfirmNoteDAL dal =null;
		try {
			// LocalConfig toOwnerCode = LocalConfigBusiness.getConfigByName(
			// context, Constant.PARAM_USER_LOGIN);
			 dal = new ConfirmNoteDAL(context);

			dal.open();
			result = dal.getLstStockTrans(Session.userName);
			dal.close();
			return result;
		} catch (Exception e) {
			Log.e(TAG, "Exception", e);
			return new ArrayList<>();
		}
	}

	/**
	 * 
	 * @param context
	 * @param stockTransId
	 * @return
	 */
	public static ArrayList<StockTransDetail> getLstStockTransDetail(
			Context context, Long stockTransId) {
		ArrayList<StockTransDetail> result = new ArrayList<>();
		try {
			ConfirmNoteDAL dal = new ConfirmNoteDAL(context);
			dal.open();
			result = dal.getLstStockTransDetail(stockTransId);
			dal.close();
			return result;
		} catch (Exception e) {
			Log.e(TAG, "Exception", e);
			return new ArrayList<>();
		}
	}

	/**
	 * 
	 * @param stockTransId
	 * @param stockModelId
	 * @return
	 */
	public static ArrayList<Serial> getLstSerial(Context context,
			Long stockTransId, Long stockModelId) {
		ArrayList<Serial> result = new ArrayList<>();
		try {
			ConfirmNoteDAL dal = new ConfirmNoteDAL(context);
			dal.open();
			result = dal.getLstSerial(stockTransId, stockModelId);
			
			dal.close();
			return result;
		} catch (Exception e) {
			Log.e(TAG, "Exception", e);
			return new ArrayList<>();
		}
	}

	public static void updateStockTrans(Context context, Long stockTransId) {
		try {
			ConfirmNoteDAL dal = new ConfirmNoteDAL(context);
			dal.open();
			dal.updateStockTrans(stockTransId);
			dal.close();
		} catch (Exception e) {
			Log.e(TAG, "Exception", e);
		}
	}
}
