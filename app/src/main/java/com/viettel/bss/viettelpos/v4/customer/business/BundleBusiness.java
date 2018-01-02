package com.viettel.bss.viettelpos.v4.customer.business;

import java.util.List;

import com.viettel.bss.viettelpos.v4.customer.object.ProductBundleGroupsConfigDetail;

public class BundleBusiness {
	public static boolean checkDel(
			List<ProductBundleGroupsConfigDetail> lstConfigDetail,
			ProductBundleGroupsConfigDetail item) {
		int fromValue = 0;
		try {
			fromValue = Integer.parseInt(item.getFromValue());
		} catch (Exception ignored) {
		}
		if (fromValue == 0) {
			return true;
		}
		int count = 0;
		for (ProductBundleGroupsConfigDetail tmp : lstConfigDetail) {
			if (tmp.getPkId().equals(item.getPkId())) {
				count++;
			}

		}
        return count != fromValue;
    }

	public static boolean checkAdd(
			List<ProductBundleGroupsConfigDetail> lstConfigDetail,
			ProductBundleGroupsConfigDetail item) {

		int toValue = 0;
		try {
			toValue = Integer.parseInt(item.getToValue());
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (toValue == 1 || toValue == 0) {
			return false;
		}
		int count = 0;
		for (ProductBundleGroupsConfigDetail tmp : lstConfigDetail) {
			if (tmp.getPkId().equals(item.getPkId())) {
				count++;
			}
		}
        return count < toValue;
    }

	public static int countSub(
			List<ProductBundleGroupsConfigDetail> lstConfigDetail,
			ProductBundleGroupsConfigDetail item) {
		int count = 0;
		for (ProductBundleGroupsConfigDetail tmp : lstConfigDetail) {
			if (tmp.getPkId().equals(item.getPkId())) {
				count++;
			}

		}
		return count;
	}

	public static ProductBundleGroupsConfigDetail cloneProductBundleGroupsConfigDetail(
			ProductBundleGroupsConfigDetail item) {
		ProductBundleGroupsConfigDetail tmp = new ProductBundleGroupsConfigDetail();
		tmp.setCheckPOP(item.getCheckPOP());
		tmp.setFromValue(item.getFromValue());
		tmp.setIsdnAccount(item.getIsdnAccount());
		tmp.setPkId(item.getPkId());
		tmp.setPkType(item.getPkType());
		tmp.setShortNumber(item.getShortNumber());
		tmp.setSubType(item.getSubType());
		tmp.setTelecomeServiceId(item.getTelecomeServiceId());
		tmp.setToValue(item.getToValue());

		return tmp;
	}
}
