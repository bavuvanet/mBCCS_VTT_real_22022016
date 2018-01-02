package com.viettel.bss.viettelpos.v4.sale.dal;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

class PriceDAL {
	private final SQLiteDatabase database;

	public PriceDAL(SQLiteDatabase database) {
		this.database = database;
	}

	/**
	 * HuyPQ15: Lay gia cua mat hang
	 * 
	 * @param stockModelId
	 *            : Id mat hang
	 * @param priceType
	 *            : loai gia, 1: ban dut; 5: ban dat coc
	 * @param pricePolicy
	 * @return
	 * @throws Exception
	 */
	public Long findPriceForSale(Long stockModelId, Long priceType,
			String pricePolicy, Long shopId, Long channelTypeId) {
		Long price = 0L;
		ArrayList<String> param = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT   pri.price");
		sql.append(" FROM   price_shop_map psm, price pri");
		sql.append(" WHERE       1 = 1");
		sql.append(" AND pri.price_id  NOT IN (SELECT   price_id  ");
		sql.append("        FROM   price_sales_program_map");
		sql.append(" WHERE   status  = 1)");
		sql.append(" AND pri.sta_date <= date ('now')");
		sql.append(" AND (pri.end_date IS NULL OR pri.end_date >= date ('now') or pri.end_date = '')");
		sql.append(" AND pri.stock_model_id  = ? ");
		param.add(stockModelId + "");
		sql.append(" AND pri.TYPE  = ? ");
		param.add(priceType + "");
		sql.append(" AND pri.price_policy  = ? ");
		param.add(pricePolicy);
		sql.append(" AND pri.price_id  = psm.price_id ");
		sql.append(" AND psm.shop_id  = ? ");
		param.add(shopId + "");
		sql.append(" AND pri.status  = 1");
		sql.append(" AND pri.price_id   IN (SELECT   price_id ");
		sql.append(" FROM   price_channel_map");
		sql.append(" WHERE   channel_type_id  = ? )");
		param.add(channelTypeId + "");
		sql.append(" ORDER BY   pri.price_id ");
		String[] strParam = param.toArray(new String[param.size()]);
		Cursor c = database.rawQuery(sql.toString(), strParam);
		if (c == null) {
			return price;
		}
		if (c.moveToFirst()) {
			if (c.isNull(0)) {
				return price;
			}
			return c.getLong(0);

		}
		return price;
	}
}
