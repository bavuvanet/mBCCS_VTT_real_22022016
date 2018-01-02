package com.viettel.bss.viettelpos.v4.sale.business;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.sale.dal.BhldDAL;
import com.viettel.bss.viettelpos.v4.sale.object.BhldObject;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;

public class BhldBusiness {

	/**
	 * Lay cac kenh duoc gan vao 1 chuong trinh BHLD
	 * 
	 * @param context
	 * @return
	 */
	public static ArrayList<BhldObject> getListProgram(Context context) {
		try {
			ArrayList<BhldObject> result = new ArrayList<>();

			BhldDAL dal = new BhldDAL(context);
			dal.open();

			result = dal.getListProgram();
			dal.close();
			return result;
		} catch (Exception e) {
			Log.e("Exception", "Exception", e);
		}

		return null;
	}

	/**
	 * Lay cac kenh duoc gan vao 1 chuong trinh BHLD
	 * 
	 * @param context
	 * @param recordWorkId
	 * @return
	 */
	public static ArrayList<Staff> getListObjectByProgram(Context context,
			Long recordWorkId) {
		try {
			ArrayList<Staff> result = new ArrayList<>();
			BhldDAL dal = new BhldDAL(context);
			dal.open();

			result = dal.getListObjectByProgram(recordWorkId);
			dal.close();
			return result;
		} catch (Exception e) {
			Log.e("Exception", "Exception", e);
		}

		return null;
	}

	/**
	 * 
	 * @param context
	 * @param staffCode
	 * @return
	 */
//	public static Staff getStaffByStaffCode(Context context, String staffCode) {
//		StaffDAL dal = null;
//		try {
//dal = new 
//			
//			dal.open();
//			Staff result = dal.getStaffByStaffCode(staffCode);
//			dal.close();
//			return result;
//		} catch (Exception e) {
//			Log.e("Exception", "Exception", e);
//		}finally{
//			
//		}
//		return null;
//	}

	/**
	 * 
	 * @param context
	 * @param staffId
	 * @return
	 */
//	public static Staff getStaffByStaffId(Context context, Long staffId) {
//		try {
//
//			StaffSaleDAL dal = new StaffSaleDAL(context);
//			dal.open();
//			Staff result = dal.getStaffByStaffId(staffId);
//			dal.close();
//			return result;
//		} catch (Exception e) {
//			Log.e("Exception", "Exception", e);
//		}
//		return null;
//	}

}
