package com.viettel.bss.viettelpos.v4.business;

import android.content.Context;

import com.viettel.bss.viettelpos.v4.dal.LocalConfigDAL;
import com.viettel.bss.viettelpos.v4.object.LocalConfig;

class LocalConfigBusiness {

	public int insertLocalConfig(Context context, LocalConfig config)
			throws Exception {
		LocalConfigDAL dal = new LocalConfigDAL(context);
		dal.open();
		dal.deleteByName(config.getParamName());
		int result = dal.insertLocalConfig(config);
		dal.close();
		return result;
	}
}
