package com.viettel.bss.viettelpos.v4.charge.Business;

import android.content.Context;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.charge.dal.AreaDal;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;

import java.util.ArrayList;

public class AreaBusiness {
	public static ArrayList<AreaObj> getAllArea(Context context){
		ArrayList<AreaObj> lisAreaObjs = new ArrayList<>();
		try{
			AreaDal dal = new AreaDal(context);
			lisAreaObjs = dal.getAllArea();
			return lisAreaObjs;
		}catch(Exception e){
			Log.e("AreaBusiness", e.toString());
		}
		return null;
	}

    public static AreaObj getAreaFromAreaCode(Context context, String areaCode) {
        AreaDal dal = null;
        try {
            dal = new AreaDal(context);
            dal.open();
            return dal.getAreaFromAreaCode(areaCode);
        } catch (Exception e) {
            Log.e("AreaBusiness", e.toString());
        } finally {
            if (dal != null) {
                try {
                    dal.close();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
