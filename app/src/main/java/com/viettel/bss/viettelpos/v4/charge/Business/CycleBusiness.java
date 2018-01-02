package com.viettel.bss.viettelpos.v4.charge.Business;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.charge.dal.CycleDal;
import com.viettel.bss.viettelpos.v4.charge.object.CycleObj;

import android.content.Context;
import android.util.Log;



class CycleBusiness {
	public static ArrayList<CycleObj> getAllCycle(Context context){
		ArrayList<CycleObj> lisCycleObjs = new ArrayList<>();
		try{
			CycleDal dal = new CycleDal(context);
			lisCycleObjs = dal.getAllCycle();
			return lisCycleObjs;
		}catch(Exception e){
			Log.e("CycleBusiness", e.toString());
		}
		return lisCycleObjs;
	}
}
