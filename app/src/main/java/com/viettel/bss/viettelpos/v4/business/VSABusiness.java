package com.viettel.bss.viettelpos.v4.business;

import java.util.List;

import android.content.Context;

import com.viettel.bss.viettelpos.v4.dal.VSADAL;
import com.viettel.bss.viettelpos.v4.object.VSA;

class VSABusiness {

	/**
	 * Insert vao database
	 * @param context
	 * @param lstMenu
	 * @return
	 * @throws Exception
	 */
	public int insertMenu(Context context,List<VSA> lstMenu) throws Exception{
		if(lstMenu!= null && !lstMenu.isEmpty()){
			int result = 0;
			VSADAL dal = new VSADAL(context);
			dal.open();
			for (VSA vsaMenu : lstMenu) {
				result = result + dal.insertVSAItem(vsaMenu);
			}
			dal.close();
			return result;
		}
		return 0;
	}
	public void truncateTable(Context context)throws Exception{
		VSADAL dal = new VSADAL(context);
		dal.open();
		dal.truncateVSATable();
		dal.close();
	}
	/**
	 * Lay tat ca cac mail 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public List<String> getAllMenu(Context context) throws Exception{
		VSADAL dal = new VSADAL(context);
		dal.open();
		List<String>result = dal.getAllVSAName();
		dal.close();
		return result;
	}
}
