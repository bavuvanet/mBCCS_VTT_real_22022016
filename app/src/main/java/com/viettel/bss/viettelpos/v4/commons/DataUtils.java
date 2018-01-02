package com.viettel.bss.viettelpos.v4.commons;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.AreaBean;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetAreaDal;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.dal.ApParamDAL;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DataUtils extends Activity {
	private static final List<Spin> lstStatusCSKH = new ArrayList<>();

	/**
	 * getLstStatusCSKH
	 * 
	 * @param context
	 * @return
	 */
	private static List<Spin> getLstStatusCSKH(Context context) {
		if (CommonActivity.isNullOrEmptyArray(lstStatusCSKH)) {
			List<Spin> lstTmp = initLstStatusCSKH(context,
					Constant.PAR_NAME.STATUS_CSKHLN);
			if (!CommonActivity.isNullOrEmptyArray(lstTmp)) {
				lstStatusCSKH.addAll(lstTmp);
			}
		}
		return lstStatusCSKH;
	}

	/**
	 * 
	 * @param context
	 * @param parName
	 * @return
	 */
	private static List<Spin> initLstStatusCSKH(Context context, String parName) {
		ApParamDAL apDal = null;
		try {
			apDal = new ApParamDAL(context);
			apDal.open();

            return apDal.getAppParam(parName, true);
		} catch (Exception ex) {
			Log.d("Error: ", "Error when initSpinner: " + ex);
			return null;
		} finally {
			if (apDal != null) {
				try {
					apDal.close();
				} catch (Exception ignored) {

				}
			}
		}
	}

	/**
	 * getStatusNameCSKH
	 * 
	 * @param context
	 * @param statusId
	 * @return
	 */
	public static String getStatusNameCSKH(Context context, String statusId) {
		getLstStatusCSKH(context);

		for (Spin spin : lstStatusCSKH) {
			if (spin.getId().equals(statusId)) {
				return spin.getValue();
			}
		}
		return context.getResources().getString(R.string.tvOtherToolShop, "");
	}

    public static ArrayList<AreaBean> getProvince(Activity activity){
        GetAreaDal dal = null;
        try {
            dal = new GetAreaDal(activity);
            dal.open();
            return dal.getLstProvince();
        } catch (Exception ex) {
            Log.e("initProvince", ex.toString());
            return null;
        } finally {
            if(dal != null){
                dal.close();
            }
        }
    }


	public static ArrayList<AreaBean> initDistrict(Activity activity,
			String province) {
		GetAreaDal dal = null;
		try {
			dal = new GetAreaDal(activity);
			dal.open();
			return dal.getLstDistrict(province);
		} catch (Exception ex) {
			Log.e("initDistrict", ex.toString());
			return null;
		} finally {
			if (dal != null) {
				dal.close();
			}
		}
	}

	public static ArrayList<AreaBean> initPrecinct(Activity activity,
			String province, String district) {
		GetAreaDal dal = null;
		try {
			dal = new GetAreaDal(activity);
			dal.open();
			return dal.getLstPrecinct(province, district);
		} catch (Exception ex) {
			Log.e("initPrecinct", ex.toString());
			return null;
		} finally {
			if (dal != null) {
				dal.close();
			}
		}
	}

	public static Long safeToLong(String value){
		if(value == null){
			return 0L;
		}

		try{
			if(value.contains(".")){
				value = value.replaceAll("\\.", "");
			}

			if(value.contains(",")){
				value = value.replaceAll(",", "");
			}
			return Long.valueOf(value.trim());
		} catch (Exception ex){
			return 0L;
		}
	}

	public static Long safeToLong(Long value){
		if(value == null){
			return 0L;
		}

		return value;
	}

	public static String safeToString(String value){
		if(CommonActivity.isNullOrEmpty(value)){
			return "";
		}
		return value.trim();
	}

	public static int safeToInteger(String value){
		if(CommonActivity.isNullOrEmpty(value)){
			return 0;
		}

		try{
			return Integer.valueOf(value);
		}catch (Exception ex){
			return 0;
		}
	}

}
