package com.viettel.bss.viettelpos.v4.sale.dal;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DBOpenHelper;
import com.viettel.bss.viettelpos.v4.commons.SaleCommons;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.sale.object.ProgramSaleBean;
import com.viettel.bss.viettelpos.v4.sale.object.Shop;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;
import com.viettel.bss.viettelpos.v4.sale.object.StockModel;
import com.viettel.bss.viettelpos.v4.sale.object.StockTransDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author huypq15
 * 
 */
public class LookupStockDAL {
	private SQLiteDatabase database;
	private final DBOpenHelper dal;
	private final String logTag = LookupStockDAL.class.getName();

	public LookupStockDAL(Context context) {
		dal = new DBOpenHelper(context);
	}

	public void open() throws SQLException {
		database = dal.getWritableDatabase();
	}

	public void close() {
		dal.close();
		if (database != null) {
			database.close();
		}
	}

	/**
	 * Xem kho
	 * 
	 * @param context
	 * @param collId
	 *            : Ma diem ban, CTV mua hang, neu ban le thi truyen null
	 * @param priceType
	 *            : loai gia 1 - ban dut, 5 - ban dat coc
	 * @param transType
	 *            : loai ban hang: 2 - ban dut, 1 - dat coc
	 * @return
	 * @throws Exception
	 */

	public ArrayList<StockModel> lookupSotckForSale(Context context,
			String staffOwnerCode, Long collId, Long priceType, Long transType,
			Long telecomServiceId, Boolean isBHLD, String program_id)
			throws Exception {
		ArrayList<StockModel> result = new ArrayList<>();
        Staff ownerStaff = null;
        StaffDAL staffDAL = new StaffDAL(database);
        ownerStaff = staffDAL.getStaffByStaffCode(staffOwnerCode);
        if (ownerStaff == null) {
            return result;
        }
        Long channelTypeId = null;
        Staff staff = null;
        Shop shop = null;
        ShopDAL shopDAL = new ShopDAL(database);
        shop = shopDAL.getShopBrand(ownerStaff.getShopId());
        if (shop == null) {
            return result;
        }
        if (collId != null) {
            staff = staffDAL.getStaffByStaffId(collId);
            if (staff == null) {
                Log.d(logTag, "coll is null: ");
                return result;
            }
            if (Long.valueOf(10L).compareTo(staff.getChannelTypeId()) == 0) {
                if (Long.valueOf(1L).compareTo(staff.getPointOfSale()) == 0) {
                    channelTypeId = Constant.CHANNEL_TYPE_POS;
                } else if (Long.valueOf(2L).compareTo(
                        staff.getPointOfSale()) == 0) {
                    channelTypeId = Constant.CHANNEL_TYPE_COLLABORATOR;
                } else {
                    channelTypeId = staff.getChannelTypeId();
                }
            } else {
                channelTypeId = staff.getChannelTypeId();
            }
        }
        String pricePolicy = shop.getPricePolicy();
        result = findLstStockModel(transType, channelTypeId,
                ownerStaff.getStaffId(), telecomServiceId, priceType,
                pricePolicy, shop.getShopId(), program_id);
        if (result == null || result.isEmpty()) {
            return result;
        }

        // }
		return result;
	}

	public ArrayList<StockModel> lookUpStockForBankplus(String staffOwnerCode,
			ArrayList<StockModel> lstStockModel) {
		ArrayList<StockModel> result = new ArrayList<>();
		Cursor c = null;
		try {
			Staff ownerStaff = null;
			StaffDAL staffDAL = new StaffDAL(database);
			ownerStaff = staffDAL.getStaffByStaffCode(staffOwnerCode);
			if (ownerStaff == null) {
				return result;
			}

			StringBuilder sql = new StringBuilder();
			sql.append(" select a.stock_model_id, a.quantity_issue, b.check_serial, c.table_name,");
			sql.append(" b.telecom_service_id, b.check_serial");
			sql.append(" from stock_total a, stock_model b, stock_type c ");
			sql.append(" where 1 =1");
			sql.append(" and a.status  = 1");
			sql.append(" and a.state_id  = 1");
			sql.append(" and b.status  = 1");
			sql.append(" and c.status  = 1");
			sql.append(" and a.stock_model_id  = b.stock_model_id ");
			sql.append(" and b.stock_type_id  = c.stock_type_id ");
			sql.append(" and a.owner_type  = 2");
			sql.append(" and a.owner_id  = ? ");
			sql.append(" and a.stock_model_id   in (?  ");
			for (int i = 1; i < lstStockModel.size(); i++) {
				sql.append(",? ");
			}
			sql.append(")");
			String[] param = new String[lstStockModel.size() + 1];
			param[0] = ownerStaff.getStaffId().toString();
			for (int i = 0; i < lstStockModel.size(); i++) {
				param[i + 1] = lstStockModel.get(i).getStockModelId()
						.toString();
			}

			c = database.rawQuery(sql.toString(), param);
			if (c.moveToFirst()) {
				do {
					StockModel item = new StockModel();
					item.setStockModelId(c.getLong(0));
					item.setQuantityIssue(c.getLong(1));

					item.setCheckSerial(c.getLong(2));
					if (!c.isNull(3)) {
						item.setTableName(c.getString(3));
					} else {
						item.setTableName("");
					}
					item.setTelecomServiceId(c.getLong(4));
					item.setCheckSerial(c.getLong(5));
					result.add(item);
				} while (c.moveToNext());
			}
		} finally {
			if (c != null) {
				c.close();
			}
		}

		return result;
	}

	/*
	 * @param context
	 * 
	 * @param collId : Ma diem ban, CTV mua hang, neu ban le thi truyen null
	 * 
	 * @param priceType : loai gia 1 - ban dut, 5 - ban dat coc
	 * 
	 * @param transType : loai ban hang: 2 - ban dut, 1 - dat coc
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	private ArrayList<StockModel> findLstStockModel(Long transType,
			Long channelTypeId, Long ownerId, Long telecomServiceId,
			Long priceType, String pricePolicy, Long shopId, String program_id) {
		ArrayList<StockModel> result = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		ArrayList<String> param = new ArrayList<>();

		sql.append(" select ");

		sql.append(" stock.stock_model_id,");
		sql.append(" stock.quantity, ");
		sql.append(" stock.quantity_issue,");
		sql.append(" stock.stock_model_code,");
		sql.append(" stock.name, ");
		sql.append(" stock.stock_type_id,");
		sql.append(" stock.check_serial, ");
//		sql.append(" stock.table_name,");
		sql.append(" price.price         ");
		sql.append(" from");
		sql.append(" (");
		sql.append(" select ");
		sql.append(" a.stock_model_id, ");
		sql.append(" a.quantity, ");
		sql.append(" a.quantity_issue, ");
		sql.append(" b.stock_model_code, ");
		sql.append(" b.name, ");
		sql.append(" b.stock_type_id, ");
		sql.append(" b.check_serial ");
//		sql.append(" d.table_name ");
		sql.append(" from stock_total a, stock_model b, stock_type d ");
//		if (channelTypeId != null) {
//			sql.append(" , stock_deposit c");
//		}
		sql.append(" where a.stock_model_id = b.stock_model_id   ");
		if(channelTypeId == null){
			sql.append(" and b.stock_model_code != 'TCDT'   ");
		}
		sql.append(" and a.owner_type  = 2 ");
		sql.append(" and a.owner_id  = ? ");
		sql.append(" and a.status  = 1 ");
		param.add(ownerId + "");
		sql.append(" and b.stock_type_id  = d.stock_type_id  ");
		sql.append(" and b.telecom_service_id = ? ");
		sql.append(" and b.status = 1  ");
		param.add(telecomServiceId + "");
		sql.append(" and a.quantity_issue   > 0");
		sql.append(" and a.state_id    = 1");
		sql.append(" order by d.stock_type_id, b.stock_model_code");
		sql.append(" ) stock");
		/* === modify 26012015 - start === */
		sql.append(" inner join");
		sql.append(" (");
		sql.append(" SELECT   pri.stock_model_id,pri.price");
		sql.append(" FROM   price_shop_map psm, price pri");
		sql.append(" WHERE       1 = 1");
//		if (channelTypeId != null) {
//			sql.append (" AND (psm.max_stock is not null or psm.max_stock != '')");
//		}


		if (program_id != null && !program_id.isEmpty()) {
			sql.append(" AND pri.price_id in (SELECT   price_id  ");
			sql.append(" FROM   price_sales_program_map where program_id = ? ");
			param.add("" + program_id);
			sql.append(" AND   status  = 1)");
			
		} else {
			sql.append(" AND pri.price_id  NOT IN (SELECT   price_id  ");
			sql.append(" FROM   price_sales_program_map");
			sql.append(" WHERE   status  = 1)");
		}
		sql.append(" AND pri.sta_date <= datetime ('now','localtime')");
		sql.append(" AND (pri.end_date IS NULL OR pri.end_date >= datetime ('now','localtime') or pri.end_date = '')");
		sql.append(" AND pri.TYPE  = ? ");
		param.add(priceType + "");
		sql.append(" AND pri.price_policy  = ? ");
		param.add(pricePolicy);
		sql.append(" AND pri.price_id  = psm.price_id ");
		sql.append(" AND psm.shop_id  = ? ");
		param.add(shopId + "");
		sql.append(" AND pri.status  = 1");
		sql.append(" AND psm.status  = 1");
		sql.append(" AND pri.price_id   IN (SELECT   price_id ");
		sql.append(" FROM   price_channel_map ");
		sql.append(" WHERE   channel_type_id  = ? ");
		sql.append (" AND status = 1 ");
		if (channelTypeId != null) {
//			sql.append (" AND (max_stock is not null or max_stock != '') ");
			sql.append (" AND max_stock> 0 ");
		}
		sql.append(" )) price");
		sql.append(" on price.stock_model_id = stock.stock_model_id; ");

		if (channelTypeId != null) {
			param.add(channelTypeId + "");
		} else {
			Long channelType = Session.loginUser.getChannelTypeId();
			if(Session.loginUser.isPOS()){
				channelType = 80043L;
			}
			param.add( channelType +"");
		}

		/* === modify 26012015 - finish === */

		String[] strParam = param.toArray(new String[param.size()]);

		Cursor c = null;
		try {
			c = database.rawQuery(sql.toString(), strParam);
			Log.d("sql hang hoa", ""+sql.toString());
			String x = "";
			for (int i = 0; i < strParam.length; i++) {
				x = x + " - "+ strParam[i];
			}
			Log.d("tham so", x);
			if (c.moveToFirst()) {
				do {
					StockModel item = new StockModel();
					item.setStockModelId(c.getLong(0));
					item.setQuantity(c.getLong(1));
					item.setQuantityIssue(c.getLong(2));
					item.setStockModelCode(c.getString(3));
					item.setStockModelName(c.getString(4));
					item.setStockTypeId(c.getLong(5));
					item.setCheckSerial(c.getLong(6));
//					if (!c.isNull(7)) {
//						item.setTableName(c.getString(7));
//					} else {
//						item.setTableName(null);
//					}
					item.setPrice(c.getLong(7));
					result.add(item);
				} while (c.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (c != null) {
				c.close();
			}
		}

		if(!CommonActivity.isNullOrEmpty(result)){
			StockModel last = result.get(result.size()-1);
			if("TCDT".equals(last.getStockModelCode().toUpperCase())){
				result.remove(result.get(result.size()-1));
				result.add(0,last);
			}
		}
		return result;
	}

	/**
	 * 
	 * @param tableName
	 * @param stockModelId
	 * @return
	 * @throws Exception
	 */
	public ArrayList<String> getSerial(String tableName, Long stockModelId,
			String ownerStaffCode) {
		ArrayList<String> result = new ArrayList<>();
		Staff ownerStaff = null;
		Cursor c = null;
		try {
			StaffDAL staffDAL = new StaffDAL(database);
			ownerStaff = staffDAL.getStaffByStaffCode(ownerStaffCode);
			String[] param = new String[2];
			String SQL_SELECT = " select serial from  " + tableName
					+ " where stock_model_id  =    ? ";

			SQL_SELECT += " and owner_id  = ? ";
			SQL_SELECT += " and owner_type   = 2";

			SQL_SELECT += " and  status  = 1 and "
					+ "state_id  = 1 order by cast(serial as INTEGER) ; ";

			if ("STOCK_HANDSET".equalsIgnoreCase(tableName)) {
				SQL_SELECT = " SELECT   serial as  fromSerial,"
						+ " serial as toSerial," + " 1 as quantity " + " FROM "
						+ tableName + " where owner_type  = 2  "
						+ " and stock_model_id  = ?  " + " and owner_id  = ?  "
						+ " and state_id  = 1 " + " and status  = 1 "
						+ "ORDER BY serial";
			}
			param[0] = "" + stockModelId;
			param[1] = "" + ownerStaff.getStaffId();
			c = database.rawQuery(SQL_SELECT, param);
			if (c == null) {
				return result;
			}
			if (c.moveToFirst()) {
				do {
					String tmp = c.getString(0);
					if (tableName.equalsIgnoreCase("Stock_card")) {
						tmp = SaleCommons.normalSerial(tmp);
					}
					result.add(tmp);

				} while (c.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (c != null) {
				c.close();
			}
		}

		return result;
	}

	/**
	 * 
	 * @param stockModel
	 * @throws Exception
	 */
	public void updateStockTotal(StockModel stockModel) {
		String sqlStockTotal = " update stock_total set quantity = ?, "
				+ " quantity_issue = ? where stock_model_id  = ? ";
		Long quantity = stockModel.getQuantityIssue()
				- stockModel.getQuantitySaling();
		database.execSQL(sqlStockTotal, new String[] { quantity + "",
				quantity + "", stockModel.getStockModelId().toString() });
		if (stockModel.getTableName() != null
				&& !stockModel.getTableName().isEmpty()) {
			String sql = "";
			if (stockModel.isStockHandset()) {
				sql = " update " + stockModel.getTableName()
						+ " set status = 0 where serial in (? ";
				for (int i = 1; i < stockModel.getSelectedSerial().size(); i++) {
					sql += ",?";
				}
				sql += ")";
			} else {
				sql = " update "
						+ stockModel.getTableName()
						+ " set status = 0 where cast(serial as integer) in (cast (? as integer) ";
				for (int i = 1; i < stockModel.getSelectedSerial().size(); i++) {
					sql += ",cast(? as integer)";
				}
				sql += ")";
			}

			database.execSQL(
					sql,
					stockModel.getSelectedSerial().toArray(
							new String[stockModel.getSelectedSerial().size()]));

		}
	}

	public void updateStockTotal(List<StockModel> lstStockModel) {
		if (lstStockModel == null) {
			return;
		}
		SQLiteStatement stockTotalStmt = null;
		SQLiteStatement stockXStmt = null;
		SQLiteDatabase db = null;

		String sqlStockTotal = " update stock_total set quantity = quantity - ?, "
				+ " quantity_issue = quantity_issue - ? where stock_model_id  = ? ";
		try {
			db = dal.getWritableDatabase();
			db.beginTransactionNonExclusive();
			for (StockModel stockModel : lstStockModel) {
				if (stockModel.getQuantitySaling() > 0) {

					// quantity = stockModel.getQuantityIssue()
					// - stockModel.getQuantitySaling();
					stockTotalStmt = db.compileStatement(sqlStockTotal);
					stockTotalStmt.bindAllArgsAsStrings(new String[] {
							stockModel.getQuantitySaling() + "",
							stockModel.getQuantitySaling() + "",
							stockModel.getStockModelId() + "" });
					int numOfRecord = stockTotalStmt.executeUpdateDelete();
					Log.e(logTag, "update stockTotal, stock_model_id: "
							+ stockModel.getStockModelId() + "---result: "
							+ numOfRecord);
					stockTotalStmt.clearBindings();

					if (stockModel.getSelectedSerial() != null
							&& !stockModel.getSelectedSerial().isEmpty()) {
						int countSerial = 0;
						String sql = "";
						if (stockModel.isStockCard()) {
							sql = " update "
									+ stockModel.getTableName()
									+ " set status = 0 where cast(serial as integer) = cast(? as integer) ";
						} else {
							sql = " update " + stockModel.getTableName()
									+ " set status = 0 where serial = ? ";
						}

						stockXStmt = db.compileStatement(sql);
						for (String serial : stockModel.getSelectedSerial()) {
							stockXStmt.bindString(1, serial);
							countSerial = countSerial
									+ stockXStmt.executeUpdateDelete();
							stockXStmt.clearBindings();
						}
						Log.e(logTag,
								"update table " + stockModel.getTableName()
										+ "---num of record: " + countSerial);
					}
				}
			}
			db.setTransactionSuccessful();
			db.endTransaction();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				try {
					if (db.inTransaction()) {
						db.setTransactionSuccessful();
						db.endTransaction();
					}
					db.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if (stockTotalStmt != null) {

				stockTotalStmt.close();
			}
			if (stockXStmt != null) {
				stockXStmt.close();
			}
		}

	}

	/**
	 *
	 * @param lstStockTransDetails
	 */
	public void updatePlusStockTotal(List<StockTransDetail> lstStockTransDetails) {
		if (lstStockTransDetails == null) {
			return;
		}
		SQLiteStatement stockTotalStmt = null;
		SQLiteStatement stockXStmt = null;
		SQLiteDatabase db = null;

		String sqlStockTotal = " update stock_total set quantity = quantity - ?, "
				+ " quantity_issue = quantity_issue + ? where stock_model_id  = ? ";
		try {
			db = dal.getWritableDatabase();
			db.beginTransactionNonExclusive();
			for (StockTransDetail stockModel : lstStockTransDetails) {
				if (stockModel.getQuantity() > 0) {
					stockTotalStmt = db.compileStatement(sqlStockTotal);
					stockTotalStmt.bindAllArgsAsStrings(new String[] {
							stockModel.getQuantity() + "",
							stockModel.getQuantity() + "",
							stockModel.getStockModelId() + "" });
					int numOfRecord = stockTotalStmt.executeUpdateDelete();
					Log.e(logTag, "update stockTotal, stock_model_id: "
							+ stockModel.getStockModelId() + "---result: "
							+ numOfRecord);
					stockTotalStmt.clearBindings();
				}
			}
			db.setTransactionSuccessful();
			db.endTransaction();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				try {
					if (db.inTransaction()) {
						db.setTransactionSuccessful();
						db.endTransaction();
					}
					db.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if (stockTotalStmt != null) {

				stockTotalStmt.close();
			}
			if (stockXStmt != null) {
				stockXStmt.close();
			}
		}
	}

	/**
	 *
	 * @param lstStockTransDetails
	 */
	public void updateMinusStockTotal(List<StockTransDetail> lstStockTransDetails) {
		if (lstStockTransDetails == null) {
			return;
		}
		SQLiteStatement stockTotalStmt = null;
		SQLiteStatement stockXStmt = null;
		SQLiteDatabase db = null;

		String sqlStockTotal = " update stock_total set quantity = quantity - ?, "
				+ " quantity_issue = quantity_issue - ? where stock_model_id  = ? ";
		try {
			db = dal.getWritableDatabase();
			db.beginTransactionNonExclusive();
			for (StockTransDetail stockModel : lstStockTransDetails) {
				if (stockModel.getQuantity() > 0) {
					stockTotalStmt = db.compileStatement(sqlStockTotal);
					stockTotalStmt.bindAllArgsAsStrings(new String[] {
							stockModel.getQuantity() + "",
							stockModel.getQuantity() + "",
							stockModel.getStockModelId() + "" });
					int numOfRecord = stockTotalStmt.executeUpdateDelete();
					Log.e(logTag, "update stockTotal, stock_model_id: "
							+ stockModel.getStockModelId() + "---result: "
							+ numOfRecord);
					stockTotalStmt.clearBindings();
				}
			}
			db.setTransactionSuccessful();
			db.endTransaction();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				try {
					if (db.inTransaction()) {
						db.setTransactionSuccessful();
						db.endTransaction();
					}
					db.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if (stockTotalStmt != null) {

				stockTotalStmt.close();
			}
			if (stockXStmt != null) {
				stockXStmt.close();
			}
		}
	}

	/**
	 * lay chuong trinh ban hang
	 * 
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ProgramSaleBean> getProgramSale() {
		ArrayList<ProgramSaleBean> result = new ArrayList<>();
		Cursor c = null;
		try {
			String SQL_SELECT = " select program_id,program_code,program_name,"
					+ " status from sales_program order by program_name; ";
			c = database.rawQuery(SQL_SELECT, null);
			if (c == null) {
				return result;
			}
			if (c.moveToFirst()) {
				do {
					ProgramSaleBean programSaleBean = new ProgramSaleBean();
					String program_id = c.getString(0);
					programSaleBean.setProgram_id(program_id);
					String program_code = c.getString(1);
					programSaleBean.setProgram_code(program_code);
					String program_name = c.getString(2);
					programSaleBean.setProgram_name(program_name);
					String status = c.getString(3);
					programSaleBean.setStatus(status);
					result.add(programSaleBean);
				} while (c.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (c != null) {
				c.close();
			}
		}

		return result;
	}

}
