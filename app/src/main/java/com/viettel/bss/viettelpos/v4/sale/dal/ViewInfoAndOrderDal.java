package com.viettel.bss.viettelpos.v4.sale.dal;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.commons.DBOpenHelper;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.sale.object.ObjectCheckStockTrans;
import com.viettel.bss.viettelpos.v4.sale.object.Shop;
import com.viettel.bss.viettelpos.v4.sale.object.ViewInfoOject;
import com.viettel.bss.viettelpos.v4.sale.object.ViewInfoOjectMerge;

public class ViewInfoAndOrderDal {
    public SQLiteDatabase database;
    public DBOpenHelper dal;
    public Context context;

    public ViewInfoAndOrderDal(Context context) {
        this.context = context;
        dal = new DBOpenHelper(context);
    }

    public void open() throws SQLException {
        database = dal.getWritableDatabase();
    }

    public void close() throws Exception {
        dal.close();
        if (database != null) {
            database.close();
        }
    }

    // function check date khuyenmai
    public boolean checkdatekm() throws Exception {
        boolean ischeckdate = false;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT   count(1)");
        sql.append(" FROM   debit_day_type a");
        sql.append(" WHERE 1 = 1");
        sql.append(" AND a.status = 1");
        sql.append(" AND strftime('%d-%m-%Y', a.sta_date) <= strftime('%d-%m-%Y', date('now'))");
        sql.append(" AND (a.end_date is NULL OR a.end_date >= date('now') or a.end_date = '')");
        Cursor cursor = database.rawQuery(sql.toString(), null);
        if (cursor == null) {
            return false;
        }
        if (cursor.moveToFirst()) {
            do {
                int count = cursor.getInt(0);
                if (count == 0) {
                    ischeckdate = true;
                    break;
                } else {
                    ischeckdate = false;
                    break;
                }

            } while (cursor.moveToNext());
        }
        return ischeckdate;
    }

    // check neu la ngay khuyen mai == lay ra max_debit
    public String getMaxDebit() throws Exception {

        StaffDAL staffDAL = new StaffDAL(database);
        com.viettel.bss.viettelpos.v4.sale.object.Staff staff = staffDAL
                .getStaffByStaffCode(Session.userName);

        Log.d("shopid", "" + staff.getShopId());

        Log.d("staffif", "" + staff.getStaffId());
        String maxDebit = "";
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT dte.max_debit");
        sql.append(" FROM debit_type dte, stock_debit sdt");
        sql.append(" WHERE dte.debit_day_type = sdt.debit_day_type");
        sql.append(" and cast(sdt.debit_type as integer) = cast(dte.debit_type as integer)");
        sql.append(" and cast(sdt.owner_type as integer)= 2");
        sql.append(" AND sdt.owner_id = ?");
        sql.append(" AND dte.debit_day_type = 2;");

        Cursor cursor = database.rawQuery(sql.toString(),
                new String[]{staff.getStaffId() + ""});
        if (cursor == null) {
            // maxDebit = "";
        }
        if (cursor.moveToFirst()) {
            do {
                maxDebit = String.valueOf(cursor.getInt(0));
                Log.d("maxDebit", maxDebit);
            } while (cursor.moveToNext());
        }
        return maxDebit;
    }

    // neu khong ban ghi nao thi lay debit_type trong trong bang app_param
    // /===========fuction get code========
    public String getCode() throws Exception {
        String code = "";
        StringBuilder sql = new StringBuilder();
//        sql.append("SELECT code");
//        sql.append(" FROM app_params");
//        sql.append(" WHERE TYPE = 'DEFAULT_DAY_TYPE_FOR_MAX_DEBIT';");


        sql.append(" SELECT   VALUE");
        sql.append(" FROM   option_set_value_im");
        sql.append(" WHERE   option_set_id = (SELECT   id");
        sql.append(" FROM   option_set_im");
        sql.append(" WHERE   code = 'DEFAULT_DAY_TYPE_FOR_MAX_DEBIT')");


        Cursor cursor = database.rawQuery(sql.toString(), null);
        if (cursor == null) {
            code = "";
        }
        if (cursor.moveToFirst()) {
            do {
                code = cursor.getString(0);
                Log.d("code", code);
            } while (cursor.moveToNext());
        }
        return code;
    }

    // get max debit
    public String getmaxdebit(String code) throws Exception {
        String max_debit = "";
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT dte.max_debit");
        sql.append(" FROM debit_type dte");
        sql.append(" WHERE 1 = 1");
        sql.append(" And dte.debit_type = ?");
        sql.append(" AND dte.debit_day_type = 2;");

        Cursor cursor = database.rawQuery(sql.toString(), new String[]{code
                + ""});
        if (cursor == null) {
            max_debit = "";
        }
        if (cursor.moveToFirst()) {
            do {
                max_debit = String.valueOf(cursor.getLong(0));
                Log.d("max_debit", max_debit);
            } while (cursor.moveToNext());
        }
        return max_debit;
    }

    // get max debit NOT KM
    public String getmaxdebitNOTkm(String code) throws Exception {
        String max_debit = "";
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT dte.max_debit");
        sql.append(" FROM debit_type dte");
        sql.append(" WHERE 1 = 1");
        sql.append(" And dte.debit_type = ?");
        sql.append(" AND dte.debit_day_type = 1;");

        Cursor cursor = database.rawQuery(sql.toString(), new String[]{code
                + ""});
        if (cursor == null) {
            max_debit = "";
        }
        if (cursor.moveToFirst()) {
            do {
                max_debit = String.valueOf(cursor.getLong(0));
                Log.d("max_debit", max_debit);
            } while (cursor.moveToNext());
        }
        return max_debit;
    }

    // === neu ko co khuyen mai

    public String getMaxDebitNotKM() throws Exception {
        StaffDAL staffDAL = new StaffDAL(database);
        com.viettel.bss.viettelpos.v4.sale.object.Staff staff = staffDAL
                .getStaffByStaffCode(Session.userName);
        Log.d("shopid", "" + staff.getShopId());
        String max_debit = "";
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT dte.max_debit");
        sql.append(" FROM debit_type dte, stock_debit sdt");
        sql.append(" WHERE dte.debit_day_type = sdt.debit_day_type");
        sql.append(" AND cast(sdt.debit_type as integer) = cast(dte.debit_type as integer)");
        sql.append(" AND cast(sdt.owner_type as integer)= 2");
        sql.append(" AND sdt.owner_id = ?");
        sql.append(" AND dte.debit_day_type = 1;");
        Cursor cursor = database.rawQuery(sql.toString(),
                new String[]{staff.getStaffId() + ""});
        if (cursor == null) {
            max_debit = "";
        }
        if (cursor.moveToFirst()) {
            do {
                max_debit = String.valueOf(cursor.getLong(0));
                Log.d("maxDebit", max_debit);
            } while (cursor.moveToNext());
        }
        return max_debit;
    }

    // ///=================get list a.stock_model_id,b.name, a.quantity_issue
    public ArrayList<ViewInfoOject> getListStockModelNV1(
            String telecom_service_id) throws Exception {
        ArrayList<ViewInfoOject> lisresult = new ArrayList<ViewInfoOject>();
        StaffDAL staffDAL = new StaffDAL(database);
        Log.d("Session.userName", "" + Session.userName);
        com.viettel.bss.viettelpos.v4.sale.object.Staff staff = staffDAL
                .getStaffByStaffCode(Session.userName);
        Log.d("staffid", "" + staff.getStaffId());
        StringBuilder sql = new StringBuilder();
        sql.append("select a.name, a.stock_model_id,");
        sql.append(" c.price, b.quantity_issue, c.end_date,c.price_policy");
        sql.append(" from stock_model a, stock_total b,price c");
        sql.append(" where a.stock_model_id = b.stock_model_id");
        sql.append(" and b.stock_model_id = c.stock_model_id");
        // sql.append(" and c.price_policy = d.price_policy");
        sql.append(" and b.state_id = 1 ");
        sql.append(" and b.status = 1");
        sql.append(" and a.status = 1");
        sql.append(" and c.status = 1");
        sql.append(" and b.owner_type = 2");

        sql.append(" and a.telecom_service_id = ?");
        sql.append(" and b.state_id = 1");
        sql.append(" and c.type = 23");

        sql.append(" and d.staff_id = ?");
        sql.append(" and b.owner_id = d.staff_id");
        sql.append(" and c.sta_date <= date('now')");
        sql.append(" and (c.end_date is null or c.end_date >= date('now') or c.end_date = '')");
        Cursor cursor = database.rawQuery(sql.toString(), new String[]{
                telecom_service_id + "", staff.getStaffId() + ""});
        Log.d("sqllll", sql.toString());
        if (cursor == null) {
            Log.d("khong co du lieu", "khong co du lieu");
        }
        if (cursor.moveToFirst()) {
            do {
                ViewInfoOject infoOject = new ViewInfoOject();
                String _name = cursor.getString(0);
                Log.d("name", _name);
                infoOject.set_name(_name);
                long stockModelId = cursor.getLong(1);
                Log.d("stockModelId", "" + stockModelId);
                infoOject.set_stock_model_id("" + stockModelId);
                long price = cursor.getLong(2);
                infoOject.set_price(price);
                Log.d("price", "" + price);
                int quantity = cursor.getInt(3);
                infoOject.set_quantity_issue("" + quantity);
                long sum = (long) price * quantity;
                infoOject.set_sumremane("" + sum);
                // Log.d("summmmmmmmmmmmm",""+ sum);
                lisresult.add(infoOject);
            } while (cursor.moveToNext());
        }

        return lisresult;
    }

    // ===get soluong treo
    public ArrayList<ObjectCheckStockTrans> getStockTrans() throws Exception {
        ArrayList<ObjectCheckStockTrans> lisCheckStockTrans = new ArrayList<ObjectCheckStockTrans>();

        return lisCheckStockTrans;
    }

    // ///=================get list a.stock_model_id,b.name, a.quantity_issue
    public ArrayList<ViewInfoOject> getListStockModelNV(
            String telecom_service_id) throws Exception {
        ArrayList<ViewInfoOject> lisresult = new ArrayList<ViewInfoOject>();
        StaffDAL staffDAL = new StaffDAL(database);

        Log.d("Session.userName", "" + Session.userName);

        com.viettel.bss.viettelpos.v4.sale.object.Staff staff = staffDAL
                .getStaffByStaffCode(Session.userName);
        ShopDAL shopDAL = new ShopDAL(database);
        Shop shop = shopDAL.getShopBrand(staff.getShopId());
        Log.d("shop_policy", "" + shop.getPricePolicy());
        Log.d("staffid", "" + staff.getStaffId());
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT  a.stock_model_id,b.name,c.price,");
        sql.append(" a.quantity");
        sql.append(" from stock_total a,");
        sql.append(" stock_model b,");
        sql.append(" price c");
        sql.append(" where a.status = 1");
        sql.append(" and a.owner_type = 2");
        sql.append(" and c.price_policy = ?");
        sql.append(" and c.status = 1");
        sql.append(" and c.type = 23");
        sql.append(" and a.state_id = 1 and a.owner_id = ?");
        sql.append(" and b.stock_model_id = c.stock_model_id");
        sql.append(" and c.sta_date <= date('now')");
        sql.append(" and (c.end_date IS NULL OR end_date >= date('now') or c.end_date = '')");
        sql.append(" and a.stock_model_id = b.stock_model_id and b.status = 1 and b.telecom_service_id = ?");
        sql.append(" order by b.name");
        Cursor cursor = database.rawQuery(sql.toString(),
                new String[]{shop.getPricePolicy(), staff.getStaffId() + "",
                        telecom_service_id});

        Log.d("sqllll", sql.toString());
        if (cursor == null) {
            Log.d("khong co du lieu", "khong co du lieu");
        }
        if (cursor.moveToFirst()) {
            do {
                ViewInfoOject infoOject = new ViewInfoOject();
                long stockModelId = cursor.getLong(0);
                Log.d("stockModelIdNV", "" + stockModelId);
                infoOject.set_stock_model_id("" + stockModelId);
                String _name = cursor.getString(1);
                long price = cursor.getLong(2);
                Log.d("price", "" + price);
                infoOject.set_price(price);
                int quantity = cursor.getInt(3);
                Log.d("quantityNV", "" + quantity);
                infoOject.set_quantity_issue("" + quantity);
                infoOject.set_sumremane("" + price * quantity);
                Log.d("nameNV", _name);
                infoOject.set_name(_name);
                lisresult.add(infoOject);
            } while (cursor.moveToNext());
        }

        return lisresult;
    }

    // ===========tinh tong hang treo trong kho ============
    public ArrayList<ObjectCheckStockTrans> tinhhangtreo() throws Exception {
        ArrayList<ObjectCheckStockTrans> lisCheckStockTrans = new ArrayList<ObjectCheckStockTrans>();
        StaffDAL staffDAL = new StaffDAL(database);

        Log.d("Session.userName", "" + Session.userName);
        com.viettel.bss.viettelpos.v4.sale.object.Staff staff = staffDAL
                .getStaffByStaffCode(Session.userName);
        ShopDAL shopDAL = new ShopDAL(database);
        Shop shop = shopDAL.getShopBrand(staff.getShopId());

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT st.stock_model_id,");
        sql.append(" st.quantity_res, sn.stock_trans_id,");
        sql.append(" pe.price, pe.sta_date, pe.end_date");
        sql.append(" FROM stock_trans_detail st, stock_trans sn, price pe");
        sql.append(" where 1=1");
        sql.append(" AND sn.stock_trans_id = st.stock_trans_id");
        sql.append(" AND sn.stock_trans_type = 2");
        sql.append(" and st.stock_trans_id = sn.stock_trans_id");
        sql.append(" and stock_trans_status = 3");
        sql.append(" and pe.status =1 ");
        sql.append(" and pe.stock_model_id = st.stock_model_id");
        sql.append(" and pe.sta_date <= date('now')");
        sql.append(" and (pe.end_date >= date('now') or pe.end_date is null or pe.end_date = '')");
        sql.append(" and pe.price_policy = ?");
        Cursor cursor = database.rawQuery(sql.toString(),
                new String[]{shop.getPricePolicy()});
        if (cursor == null) {
            Log.d("not data", "not data");
        }
        if (cursor.moveToFirst()) {
            do {
                ObjectCheckStockTrans oCheckStockTrans = new ObjectCheckStockTrans();
                long quantity_res = cursor.getLong(1);
                oCheckStockTrans.setQuanTityRes(quantity_res);
                long price = cursor.getLong(3);
                long sumres = quantity_res * price;
                Log.d("sumres", "" + sumres);
                oCheckStockTrans.setSumitemRes(sumres);
                lisCheckStockTrans.add(oCheckStockTrans);
            } while (cursor.moveToNext());
        }
        return lisCheckStockTrans;
    }

    // =========sql dat hang cap tren ===========================

	/*
     * SELECT a.stock_model_id,b.name, IFNULL(c.price, 0) price,
	 * a.quantity_issue
	 * 
	 * from stock_total a, stock_model b left outer join price c on
	 * b.stock_model_id = c.stock_model_id
	 * 
	 * and c.status = 1 and c.type = 23 and c.price_policy = 1
	 * 
	 * and c.sta_date <= date('now')
	 * 
	 * and (c.end_date IS NULL OR end_date >= date('now') or c.end_date = '')
	 * 
	 * where a.status = 1 and a.owner_type = 1
	 * 
	 * and a.state_id = 1 and a.owner_id = 24926
	 * 
	 * and a.stock_model_id = b.stock_model_id
	 * 
	 * and b.status = 1 and b.telecom_service_id = '1' order by b.name;
	 */

    // ///=================get list a.stock_model_id,b.name, a.quantity_issue
    public ArrayList<ViewInfoOject> getListStockModelTH(
            String telecom_service_id) throws Exception {
        ArrayList<ViewInfoOject> lisresult = new ArrayList<ViewInfoOject>();
        StaffDAL staffDAL = new StaffDAL(database);

        Log.d("Session.userName", "" + Session.userName);
        com.viettel.bss.viettelpos.v4.sale.object.Staff staff = staffDAL
                .getStaffByStaffCode(Session.userName);
        ShopDAL shopDAL = new ShopDAL(database);
        Shop shop = shopDAL.getShopBrand(staff.getShopId());
        Log.d("staffid", "" + staff.getStaffId());
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT  a.stock_model_id,b.name,IFNULL(c.price, 0) price,");
        sql.append(" a.quantity_issue");
        sql.append(" from stock_total a, stock_model b left outer join price c on b.stock_model_id = c.stock_model_id");
        sql.append(" and c.status = 1 and c.type = 23 and c.price_policy = ?");
        sql.append(" and c.sta_date <= date('now')");
        sql.append(" and (c.end_date IS NULL OR end_date >= date('now') or c.end_date = '')");
        sql.append(" where a.status = 1 and a.owner_type = 1");
        sql.append(" and a.state_id = 1 and a.owner_id = ?");
        sql.append(" and a.stock_model_id = b.stock_model_id");
        sql.append(" and b.status = 1 and b.telecom_service_id = ? order by b.name");
        // sql.append(" from stock_total a,");
        // sql.append(" stock_model b,");
        // sql.append(" price c");
        // sql.append(" where a.status = 1");
        // sql.append(" and a.owner_type = 1");
        // sql.append(" and c.status = 1");
        // sql.append(" and c.type = 23");
        // sql.append(" and c.price_policy = ?");
        // sql.append(" and a.state_id = 1 and a.owner_id = ?");
        // sql.append(" and b.stock_model_id = c.stock_model_id");
        // sql.append(" and c.sta_date <= date('now')");
        // sql.append(" and (c.end_date IS NULL OR end_date >= date('now') or c.end_date = '')");
        // sql.append(" and a.stock_model_id = b.stock_model_id and b.status = 1 and b.telecom_service_id = ?");
        // sql.append(" order by b.name");
        Cursor cursor = database.rawQuery(sql.toString(),
                new String[]{shop.getPricePolicy() + "",
                        staff.getShopId() + "", telecom_service_id});
        Log.d("sqllll", sql.toString());
        if (cursor == null) {
            Log.d("khong co du lieu", "khong co du lieu");
        }
        if (cursor.moveToFirst()) {
            do {
                ViewInfoOject infoOject = new ViewInfoOject();
                long stockModelId = cursor.getLong(0);
                Log.d("stockModelIdTH", "" + stockModelId);
                infoOject.set_stock_model_id("" + stockModelId);
                String _name = cursor.getString(1);
                long price = cursor.getLong(2);
                Log.d("priceTH", "" + price);
                infoOject.set_price(price);
                int quantity = cursor.getInt(3);
                Log.d("quantityTH", "" + quantity);
                infoOject.set_quantity_issue("" + quantity);
                infoOject.set_sumremane("" + price * quantity);
                Log.d("nameNV", _name);
                infoOject.set_name(_name);
                if (quantity == 0) {
                    infoOject = null;
                } else {
                    lisresult.add(infoOject);
                }

            } while (cursor.moveToNext());
        }

        return lisresult;
    }

    // ================== get all stock item NV all telecomservice
    // =============================
    public ArrayList<ViewInfoOject> getListAllStockModelNV() throws Exception {
        ArrayList<ViewInfoOject> lisresult = new ArrayList<ViewInfoOject>();
        StaffDAL staffDAL = new StaffDAL(database);

        Log.d("Session.userName", "" + Session.userName);

        com.viettel.bss.viettelpos.v4.sale.object.Staff staff = staffDAL
                .getStaffByStaffCode(Session.userName);
        ShopDAL shopDAL = new ShopDAL(database);
        Shop shop = shopDAL.getShopBrand(staff.getShopId());
        Log.d("shop_policy", "" + shop.getPricePolicy());
        Log.d("staffid", "" + staff.getStaffId());
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT  a.stock_model_id,b.name,c.price,");
        sql.append(" a.quantity_issue");
        sql.append(" from stock_total a,");
        sql.append(" stock_model b,");
        sql.append(" price c");
        sql.append(" where a.status = 1");
        sql.append(" and a.owner_type = 2");
        sql.append(" and c.price_policy = ?");
        sql.append(" and c.status = 1");
        sql.append(" and c.type = 23");
        sql.append(" and a.state_id = 1 and a.owner_id = ?");
        sql.append(" and b.stock_model_id = c.stock_model_id");
        sql.append(" and c.sta_date <= date('now')");
        sql.append(" and (c.end_date IS NULL OR end_date >= date('now') or c.end_date = '')");
        sql.append(" and a.stock_model_id = b.stock_model_id and b.status = 1");
        sql.append(" order by b.name");
        Cursor cursor = database
                .rawQuery(sql.toString(), new String[]{shop.getPricePolicy(),
                        staff.getStaffId() + ""});

        Log.d("sqllll", sql.toString());
        if (cursor == null) {
            Log.d("khong co du lieu", "khong co du lieu");
        }
        if (cursor.moveToFirst()) {
            do {
                ViewInfoOject infoOject = new ViewInfoOject();
                long stockModelId = cursor.getLong(0);
                Log.d("stockModelIdAllNV", "" + stockModelId);
                infoOject.set_stock_model_id("" + stockModelId);
                String _name = cursor.getString(1);
                long price = cursor.getLong(2);
                Log.d("priceAllNV", "" + price);
                infoOject.set_price(price);
                int quantity = cursor.getInt(3);
                Log.d("quantityAllNV", "" + quantity);
                infoOject.set_quantity_issue("" + quantity);
                infoOject.set_sumremane("" + price * quantity);
                Log.d("nameAllNV", _name);
                infoOject.set_name(_name);

                lisresult.add(infoOject);

            } while (cursor.moveToNext());
        }

        return lisresult;
    }

    // ================== get all stock item TH all telecomservice
    // =============================
    public ArrayList<ViewInfoOject> getListAllStockModelTH() throws Exception {
        ArrayList<ViewInfoOject> lisresult = new ArrayList<ViewInfoOject>();
        StaffDAL staffDAL = new StaffDAL(database);

        Log.d("Session.userName", "" + Session.userName);
        com.viettel.bss.viettelpos.v4.sale.object.Staff staff = staffDAL
                .getStaffByStaffCode(Session.userName);
        ShopDAL shopDAL = new ShopDAL(database);
        Shop shop = shopDAL.getShopBrand(staff.getShopId());
        Log.d("staffid", "" + staff.getStaffId());
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT  a.stock_model_id,b.name,c.price,");
        sql.append(" a.quantity_issue");
        sql.append(" from stock_total a,");
        sql.append(" stock_model b,");
        sql.append(" price c");
        sql.append(" where a.status = 1");
        sql.append(" and a.owner_type = 1");
        sql.append(" and c.status = 1");
        sql.append(" and c.type = 23");
        sql.append(" and c.price_policy = ?");
        sql.append(" and a.state_id = 1 and a.owner_id = ?");
        sql.append(" and b.stock_model_id = c.stock_model_id");
        sql.append(" and c.sta_date <= date('now')");
        sql.append(" and (c.end_date IS NULL OR end_date >= date('now') or c.end_date = '')");
        sql.append(" and a.stock_model_id = b.stock_model_id and b.status = 1");
        sql.append(" order by b.name");
        Cursor cursor = database.rawQuery(sql.toString(),
                new String[]{shop.getPricePolicy() + "",
                        staff.getShopId() + ""});
        Log.d("sqllll", sql.toString());
        if (cursor == null) {
            Log.d("khong co du lieu", "khong co du lieu");
        }
        if (cursor.moveToFirst()) {
            do {
                ViewInfoOject infoOject = new ViewInfoOject();
                long stockModelId = cursor.getLong(0);
                Log.d("stockModelIdTH", "" + stockModelId);
                infoOject.set_stock_model_id("" + stockModelId);
                String _name = cursor.getString(1);
                long price = cursor.getLong(2);
                Log.d("priceTH", "" + price);
                infoOject.set_price(price);
                int quantity = cursor.getInt(3);
                Log.d("quantityTH", "" + quantity);
                infoOject.set_quantity_issue("" + quantity);
                infoOject.set_sumremane("" + price * quantity);
                Log.d("nameNV", _name);
                infoOject.set_name(_name);
                if (quantity == 0) {
                    infoOject = null;
                } else {
                    lisresult.add(infoOject);
                }
            } while (cursor.moveToNext());
        }

        return lisresult;
    }

    //
    public ArrayList<ViewInfoOject> getListAllStockModelTH1() throws Exception {
        ArrayList<ViewInfoOject> lisresult = new ArrayList<ViewInfoOject>();
        StaffDAL staffDAL = new StaffDAL(database);

        Log.d("Session.userName", "" + Session.userName);
        com.viettel.bss.viettelpos.v4.sale.object.Staff staff = staffDAL
                .getStaffByStaffCode(Session.userName);

        Log.d("staffid", "" + staff.getShopId());
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT  a.stock_model_id,b.name,");
        sql.append(" a.quantity_issue");
        sql.append(" from stock_total a,");
        sql.append(" stock_model b");
        // sql.append(" price c");
        sql.append(" where a.status = 1");
        sql.append(" and a.owner_type = 1");
        sql.append(" and a.state_id = 1 and a.owner_id = ?");
        // sql.append(" and c.status = 1");
        // sql.append(" and c.type = 23");
        // sql.append(" and c.price_policy = ?");
        // sql.append(" and b.stock_model_id = c.stock_model_id");
        // sql.append(" and c.sta_date <= date('now')");
        // sql.append(" and (c.end_date IS NULL OR end_date >= date('now') or c.end_date = '')");
        sql.append(" and a.stock_model_id = b.stock_model_id and b.status = 1");
        sql.append(" order by b.name");
        Cursor cursor = database.rawQuery(sql.toString(),
                new String[]{staff.getShopId() + ""});
        Log.d("sqllll", sql.toString());
        if (cursor == null) {

            Log.d("khong co du lieu", "khong co du lieu");
            return lisresult;
        }
        if (cursor.moveToFirst()) {
            do {
                ViewInfoOject infoOject = new ViewInfoOject();
                long stockModelId = cursor.getLong(0);
                Log.d("stockModelIdTH", "" + stockModelId);
                infoOject.set_stock_model_id("" + stockModelId);
                String _name = cursor.getString(1);
                infoOject.set_name(_name);
                String quantity = cursor.getString(2);
                Log.d("quantityTH", "" + quantity);
                infoOject.set_quantity_issue("" + quantity);
                Log.d("nameNV", _name);
                if (Long.parseLong(quantity) == 0) {
                    infoOject = null;
                } else {
                    lisresult.add(infoOject);
                }
            } while (cursor.moveToNext());
        }

        return lisresult;
    }

    // get list order of TH
    public ArrayList<ViewInfoOject> getListStockModelCB(
            String telecom_service_id) throws Exception {
        ArrayList<ViewInfoOject> lisresult = new ArrayList<ViewInfoOject>();
        StaffDAL staffDAL = new StaffDAL(database);
        Log.d("Session.userName", "" + Session.userName);
        com.viettel.bss.viettelpos.v4.sale.object.Staff staff = staffDAL
                .getStaffByStaffCode(Session.userName);
        Log.d("shopid", "" + staff.getShopId());

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT  a.stock_model_id,b.name,");
        sql.append(" a.quantity_issue");
        sql.append(" from stock_total a,");
        sql.append(" stock_model b");
        sql.append(" where a.status = 1");
        sql.append(" and a.owner_type = 1");
        sql.append(" and a.state_id = 1 and a.owner_id = ?");
        sql.append(" and a.stock_model_id = b.stock_model_id and b.status = 1 and b.telecom_service_id = ?");
        sql.append(" order by b.stock_type_id, b.name");
        Cursor cursor = database.rawQuery(sql.toString(),
                new String[]{staff.getShopId() + "", telecom_service_id});
        Log.d("sqlllllll", sql.toString());
        if (cursor == null) {
            Log.d("khong co du lieu", "khong co du lieu");
            return lisresult;
        }
        if (cursor.moveToFirst()) {
            do {
                ViewInfoOject infoOject = new ViewInfoOject();
                long stockModelId = cursor.getLong(0);
                Log.d("stockModelIdTH", "" + stockModelId);
                infoOject.set_stock_model_id("" + stockModelId);
                String _name = cursor.getString(1);
                Log.d("nameTH", _name);
                infoOject.set_name(_name);
                int quantity = cursor.getInt(2);
                Log.d("quantityTH", "" + quantity);
                infoOject.set_quantity_issue("" + quantity);
                lisresult.add(infoOject);
            } while (cursor.moveToNext());
        }

        return lisresult;
    }

    // ham get price_policy
    public String getPricePolicy() throws Exception {

        String pricePolicy = "";
        StaffDAL staffDAL = new StaffDAL(database);
        com.viettel.bss.viettelpos.v4.sale.object.Staff staff = staffDAL
                .getStaffByStaffCode(Session.userName);
        Log.d("staffcode", "" + staff.getStaffCode());
        StringBuilder sql = new StringBuilder();
        sql.append("Select price_policy from staff");
        sql.append(" where staff_code = ?");
        Cursor cursor = database.rawQuery(sql.toString(),
                new String[]{staff.getStaffCode() + ""});
        if (cursor == null) {
            Log.d("no data", "no data");
        }
        if (cursor.moveToFirst()) {
            do {
                pricePolicy = "" + cursor.getInt(0);
                Log.d("pricePolycy", "" + pricePolicy);
            } while (cursor.moveToNext());
        }
        return pricePolicy;
    }

    // ==fuction get price
    public long getPrice(String telecomid) throws Exception {
        long sumpriceinstock = 1l;
        // ArrayList<ViewInfoOjectMerge> lisInfoOjectMerges =
        // getListStockModelNV(telecomid);
        long price = 1l;

        StringBuilder sql = new StringBuilder();
        sql.append("select price from price");
        sql.append(" where stock_model_id = ?");
        sql.append(" and type = 23)");
        sql.append(" and status = 1");
        sql.append(" and sta_date <= date('now')");
        sql.append(" and (end_date IS NULL OR end_date >= date('now'))");
        sql.append(" and price_policy = ?");
        Cursor cursor = database.rawQuery(sql.toString(),
                new String[]{getPricePolicy() + ""});
        Log.d("get price", sql.toString());
        if (cursor == null) {
            Log.d("get data falis", "get data fails");
        }
        if (cursor.moveToFirst()) {
            do {
                price = cursor.getLong(0);
                Log.d("price", "" + price);
            } while (cursor.moveToNext());
        }

        return price;
    }

    public ArrayList<ViewInfoOjectMerge> getStockStaffAndManager() {

        ArrayList<ViewInfoOjectMerge> result = new ArrayList<ViewInfoOjectMerge>();
        Cursor cursor = null;
        StaffDAL staffDAL = null;
        ShopDAL shopDAL = null;
        try {
            staffDAL = new StaffDAL(database);
            com.viettel.bss.viettelpos.v4.sale.object.Staff staff = staffDAL
                    .getStaffByStaffCode(Session.userName);
            shopDAL = new ShopDAL(database);
            Shop shop = shopDAL.getShopBrand(staff.getShopId());

            StringBuilder sql = new StringBuilder();


            sql.append(" SELECT   stock.stock_model_id,");
            sql.append("          stock.name,");
            sql.append("          ifnull(pri.price,0) price,");
            sql.append("          stock.staff_quantity,");
            sql.append("          stock.th_quantity,");
            sql.append("          stock.telecom_service_id");
            sql.append("   FROM       (");
            sql.append(" SELECT   th.stock_model_id,");
            sql.append("                        th.name,");
            sql.append("                        ifnull(sf.quantity,0) staff_quantity,");
            sql.append("                        ifnull(th.quantity,0) th_quantity,");
            sql.append("                        th.telecom_service_id");
            sql.append("                 FROM       (  SELECT   a.stock_model_id,");
            sql.append("                                        b.name,");
            sql.append("                                        a.quantity,");
            sql.append("                                        b.telecom_service_id");
            sql.append("                                 FROM   stock_total a, stock_model b");
            sql.append("                                WHERE       a.status = 1");
            sql.append("                                        AND a.owner_type = 1");
            sql.append("                                        AND a.state_id = 1");
            sql.append("                                        AND a.owner_id = ?");
            sql.append("                                        AND a.stock_model_id = b.stock_model_id");
            sql.append("                                        AND b.status = 1");
            sql.append("                             ORDER BY   b.name) th");
            sql.append("                        LEFT JOIN");
            sql.append("                            (  SELECT   a.stock_model_id, a.quantity");
            sql.append("                                 FROM   stock_total a, stock_model b");
            sql.append("                                WHERE       a.status = 1");
            sql.append("                                        AND a.owner_type = 2");
            sql.append("                                        AND a.state_id = 1");
            sql.append("                                        AND a.owner_id = ?");
            sql.append("                                        AND a.stock_model_id = b.stock_model_id");
            sql.append("                                        AND b.status = 1");
            sql.append("                             ORDER BY   b.name) sf");
            sql.append("                        ON sf.stock_model_id = th.stock_model_id ");

            sql.append("                        where 1=1 and (staff_quantity !=0 or th_quantity !=0)");

            sql.append(" UNION");
            sql.append(" SELECT   sf.stock_model_id,");
            sql.append("         sf.name,");
            sql.append("         ifnull (sf.quantity, 0) staff_quantity,");
            sql.append("         ifnull (th.quantity, 0) th_quantity,");
            sql.append("         sf.telecom_service_id");
            sql.append(" FROM       (  SELECT   a.stock_model_id,");
            sql.append("         a.quantity,");
            sql.append("         b.name,");
            sql.append("         b.telecom_service_id");
            sql.append("         FROM   stock_total a, stock_model b");
            sql.append("         WHERE       a.status = 1");
            sql.append("         AND a.owner_type = 2");
            sql.append("         AND a.state_id = 1");
            sql.append("         AND a.owner_id = ?");
            sql.append("         AND a.stock_model_id = b.stock_model_id");
            sql.append(" AND b.status = 1");
            sql.append(" ORDER BY   b.name) sf");
            sql.append(" LEFT JOIN");
            sql.append(" (  SELECT   a.stock_model_id, a.quantity");
            sql.append(" FROM   stock_total a, stock_model b");
            sql.append(" WHERE       a.status = 1");
            sql.append(" AND a.owner_type = 1");
            sql.append(" AND a.state_id = 1");
            sql.append(" AND a.owner_id = ?");
            sql.append(" AND a.stock_model_id = b.stock_model_id");
            sql.append(" AND b.status = 1");
            sql.append(" ORDER BY   b.name) th");
            sql.append(" ON sf.stock_model_id = th.stock_model_id");
            sql.append(" WHERE   1 = 1 AND (staff_quantity != 0 OR th_quantity != 0)");
            sql.append(")stock");
            sql.append("          LEFT JOIN");
            sql.append("              (SELECT   price, stock_model_id");
            sql.append("                 FROM   price c");
            sql.append("                WHERE       1 = 1");
            sql.append("                        AND c.status = 1");
            sql.append("                        AND c.TYPE = 23");
            sql.append("                        AND c.price_policy = ?");
            sql.append("                        AND c.sta_date <= date ('now')");
            sql.append("                        AND (   c.end_date IS NULL");
            sql.append("                             OR end_date >= date ('now')");
            sql.append("                             OR c.end_date = '')) pri");
            sql.append("          ON stock.stock_model_id = pri.stock_model_id ");

            cursor = database.rawQuery(sql.toString(),
                    new String[]{staff.getShopId() + "",
                            staff.getStaffId() + "",staff.getStaffId() +"", staff.getShopId() +"", shop.getPricePolicy()});

            if (cursor == null) {
                Log.d("khong co du lieu", "khong co du lieu");
                return result;
            }
            if (cursor.moveToFirst()) {
                do {
                    ViewInfoOjectMerge infoOject = new ViewInfoOjectMerge();
                    long stockModelId = cursor.getLong(0);
                    infoOject.set_stock_model_id("" + stockModelId);
                    String _name = cursor.getString(1);
                    infoOject.set_name(_name);
                    infoOject.set_priceMerge(cursor.getLong(2));

                    infoOject.set_quantity_issui_NV(cursor.getString(3));
                    infoOject.set_quantity_issui_TH(cursor.getString(4));
                    infoOject.setTelecomServiceId(cursor.getLong(5));
                    result.add(infoOject);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return result;
    }
}
