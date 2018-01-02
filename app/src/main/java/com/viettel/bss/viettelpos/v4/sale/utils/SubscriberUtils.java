package com.viettel.bss.viettelpos.v4.sale.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO;

public class SubscriberUtils {

	public static List<Spin> toSpins(SubscriberDTO sub, Activity mActivity) {
		List<Spin> results = new ArrayList<>();

		Spin item = new Spin(mActivity.getString(R.string.isdnandaccount),
				sub.getAccount());
		results.add(item);

		item = new Spin(mActivity.getString(R.string.trangthai),
				sub.getStatusView());
		results.add(item);
		
		item = new Spin(mActivity.getString(R.string.trangthaichancat),
				sub.getActStatusText());
		results.add(item);
		
		item = new Spin(mActivity.getString(R.string.sohd),
				sub.getAccountNo());
		results.add(item);
		
		item = new Spin(mActivity.getString(R.string.tenkh),
				sub.getCustomerName());
		results.add(item);
		
		item = new Spin(mActivity.getString(R.string.sogiayto),
				sub.getIdNo());
		results.add(item);

		item = new Spin(mActivity.getString(R.string.dichvu),
				sub.getServiceTypeName());
		results.add(item);
		
		item = new Spin(mActivity.getString(R.string.goicuoc),
				sub.getProductCode());
		results.add(item);
		
		return results;
	}
}
