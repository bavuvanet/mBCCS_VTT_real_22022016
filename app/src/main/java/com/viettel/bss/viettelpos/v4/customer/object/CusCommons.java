package com.viettel.bss.viettelpos.v4.customer.object;

import android.content.Context;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.connecttionService.beans.AreaBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.Contract;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetAreaDal;

import java.util.ArrayList;

/**
 * Created by toancx on 8/26/2017.
 */

public class CusCommons {

    // lay ma tinh/thanhpho
    public static ArrayList<AreaBean> getProvince(Context context) {
        ArrayList<AreaBean> result = new ArrayList<>();
        try {
            GetAreaDal dal = new GetAreaDal(context);
            dal.open();
            result = dal.getLstProvince();
            dal.close();
        } catch (Exception ex) {
            Log.e("initProvince", ex.toString());
        }
        return result;
    }

    // lay huyen/quan theo tinh
    public static ArrayList<AreaBean> getDistrict(String province, Context context) {
        ArrayList<AreaBean> result = new ArrayList<>();
        try {
            GetAreaDal dal = new GetAreaDal(context);
            dal.open();
            result = dal.getLstDistrict(province);
            dal.close();
        } catch (Exception ex) {
            Log.e("initDistrict", ex.toString());
        }
        return result;
    }

    // lay phuong/xa theo tinh,qhuyen
    public static ArrayList<AreaBean> getPrecinct(String province, String district, Context context) {
        ArrayList<AreaBean> result = new ArrayList<>();
        try {
            GetAreaDal dal = new GetAreaDal(context);
            dal.open();
            result = dal.getLstPrecinct(province, district);
            dal.close();
        } catch (Exception ex) {
            Log.e("initPrecinct", ex.toString());
        }
        return result;
    }

    public static String getProvinceFromId(String id, ArrayList<AreaBean> arrProvince) {
        for (AreaBean areaBean : arrProvince) {
            if (areaBean.getProvince().equals(id)) {
                return areaBean.getNameProvince();
            }
        }
        return null;
    }

    public static String getDistrictFromId(String id, ArrayList<AreaBean> arrDistrict) {
        for (AreaBean areaBean : arrDistrict) {
            if (areaBean.getDistrict().equals(id)) {
                return areaBean.getNameDistrict();
            }
        }
        return null;
    }

    public static String getPrecinctFromId(String id, ArrayList<AreaBean> arrPrecinct) {
        for (AreaBean areaBean : arrPrecinct) {
            if (areaBean.getPrecinct().equals(id)) {
                return areaBean.getNamePrecint();
            }
        }
        return null;
    }
}
