package com.viettel.bss.viettelpos.v4.sale.business;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.sale.dal.LookupStockDAL;
import com.viettel.bss.viettelpos.v4.sale.object.StockModel;
import com.viettel.bss.viettelpos.v4.sale.object.StockTransDetail;

public class LookUpStockBusiness {
	private static final String logTag = LookUpStockBusiness.class.getName();

	/**
	 * 
	 * @param context
	 * @param collId
	 * @param priceType
	 *            1 - ban dut, 5: dat coc
	 * @param transType
	 *            2 - ban dut, 1 - dat coc
	 * @param telecomServiceId
	 * @return
	 */
	public static ArrayList<StockModel> lookupStockForSale(Context context,
			Long collId, Long priceType, Long transType, Long telecomServiceId,
			Boolean isBHLD,String program_id) {
		LookupStockDAL dal = null;
		try {
			ArrayList<StockModel> result = new ArrayList<>();
			dal = new LookupStockDAL(context);
			dal.open();
			result = dal.lookupSotckForSale(context, Session.userName, collId,
					priceType, transType, telecomServiceId, isBHLD,program_id);
			dal.close();
			return result;
		} catch (Exception e) {
			Log.e(logTag, "Exception", e);
			if (dal != null) {
				try {
					dal.close();
				} catch (Exception ignored) {
				}
			}

			return null;
		} finally {
			if (dal != null) {
				try {
					dal.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static ArrayList<StockModel> lookUpStockForBankplus(Context context,
			ArrayList<StockModel> lstStockModel) {
		LookupStockDAL dal = null;
		try {
			ArrayList<StockModel> result = new ArrayList<>();
			dal = new LookupStockDAL(context);
			dal.open();
			result = dal
					.lookUpStockForBankplus(Session.userName, lstStockModel);
			dal.close();
			return result;
		} catch (Exception e) {
			Log.e(logTag, "Exception", e);
			if (dal != null) {
				try {
					dal.close();
				} catch (Exception ignored) {
				}
			}

			return null;
		} finally {
			if (dal != null) {
				try {
					dal.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void updateStockTotal(Context context,
			List<StockModel> lstStockModel) {
		LookupStockDAL dal = null;
		try {
			dal = new LookupStockDAL(context);
			dal.open();

					dal.updateStockTotal(lstStockModel);
			dal.close();
		} catch (Exception e) {
			Log.e(logTag, "Exception", e);
		} finally {
			if (dal != null) {
				try {
					dal.close();
				} catch (Exception ignored) {
				}
			}
		}
	}

	public static void updatePlusStockTotal(Context context, List<StockTransDetail> lstStockTransDetails) {
		LookupStockDAL dal = null;
		try {
			dal = new LookupStockDAL(context);
			dal.open();

			dal.updatePlusStockTotal(lstStockTransDetails);
			dal.close();
		} catch (Exception e) {
			Log.e(logTag, "Exception", e);
		} finally {
			if (dal != null) {
				try {
					dal.close();
				} catch (Exception ignored) {
				}
			}
		}
	}

	public static ArrayList<String> getListSerial(Context context,
			StockModel stockModel) {
		LookupStockDAL dal = null;
		try {
			dal = new LookupStockDAL(context);
			dal.open();
			ArrayList<String> result = dal.getSerial(stockModel.getTableName(),
					stockModel.getStockModelId(), Session.userName);
			dal.close();
			return result;
		} catch (Exception e) {
			Log.e(logTag, "Exception", e);
		} finally {
			if (dal != null) {
				try {
					dal.close();
				} catch (Exception ignored) {
				}
			}
		}
		return new ArrayList<>();
	}
}
