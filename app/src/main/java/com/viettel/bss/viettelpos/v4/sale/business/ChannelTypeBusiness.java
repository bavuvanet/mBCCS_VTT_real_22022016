package com.viettel.bss.viettelpos.v4.sale.business;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.sale.dal.ChannelTypeDAL;
import com.viettel.bss.viettelpos.v4.sale.object.ChannelTypeObject;

public class ChannelTypeBusiness {
	public static List<ChannelTypeObject> getAllChannel(Context context) {
		try {
			ChannelTypeDAL dal = new ChannelTypeDAL(context);
			dal.open();
			List<ChannelTypeObject> result = dal.getAllChannel();
			dal.close();
			return result;
		} catch (Exception e) {
			Log.e(ChannelTypeBusiness.class.getName(), "Exception", e);
			return new ArrayList<>();
		}
	}
}
