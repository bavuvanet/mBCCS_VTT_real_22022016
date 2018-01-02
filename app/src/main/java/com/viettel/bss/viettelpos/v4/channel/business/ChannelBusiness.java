package com.viettel.bss.viettelpos.v4.channel.business;

import android.content.Context;

import com.viettel.bss.viettelpos.v4.channel.dal.SaleTransDAL;
import com.viettel.bss.viettelpos.v4.commons.GPSTracker;

class ChannelBusiness {
	private String errorMessage;
	private String serialSim = "bss_duongtt8";
	private static final String TABLE_NAME_STAFF = "sale_trans";
	public static String TABLE_NAME_VISIT = "visit";
	public static String LOG_TAG = "LOG_TAG";
	private SaleTransDAL mSaleTransDAL;
	private final Context context;

	GPSTracker gps;
	private final String TAG = ChannelBusiness.class.getName();

	private ChannelBusiness(Context context) {
		this.context = context;
	}

	

	

	public long getTotalBuyofCurentMonth(Long staffId, String userName) {

		mSaleTransDAL = new SaleTransDAL(context);
		long total = mSaleTransDAL.getTotalBuyofCurentMonth(TABLE_NAME_STAFF,
				"09", "2013", staffId);
		try {
			mSaleTransDAL.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return total;
	}

	
	

}
