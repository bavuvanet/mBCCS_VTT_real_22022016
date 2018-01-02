package com.viettel.bss.viettelpos.v4.charge.dal;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.DBOpenHelper;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.sale.dal.ShopDAL;
import com.viettel.bss.viettelpos.v4.sale.dal.StaffDAL;
import com.viettel.bss.viettelpos.v4.sale.object.Shop;

import java.util.ArrayList;

public class AreaDal {
	private SQLiteDatabase database;
	private final DBOpenHelper dal;

	public AreaDal(Context context) {
		dal = new DBOpenHelper(context);
	}

	public void open() throws SQLException {
		database = dal.getWritableDatabase();
	}

	public void close() {
		try {
			dal.close();
			if (database != null) {
				database.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public ArrayList<AreaObj> getAllArea1(String inputSearch) throws Exception {
		ArrayList<AreaObj> lisAreaObjs = new ArrayList<>();
		StaffDAL staffDAL = new StaffDAL(database);
		ShopDAL shopDAL = new ShopDAL(database);
		Shop shop = shopDAL.getShopByShopId(Session.loginUser.getShopId());
		StringBuilder sql = new StringBuilder();
		Cursor cursor = null;
		if (inputSearch != null && inputSearch != "") {
			sql.append("Select name, area_code from area");
			sql.append(" where status = 1");
			sql.append(" and upper(parent_code) = ? and ( upper(name) like ?  or upper(area_code) like ? )");
			sql.append(" order by name");
			cursor = database.rawQuery(sql.toString(),
					new String[] { shop.getProvince().toUpperCase() + shop.getDistrict().toUpperCase(),
							"%" + inputSearch.toUpperCase() + "%", "%" + inputSearch.toUpperCase() + "%" });
		} else {
			sql.append("Select name, area_code from area");
			sql.append(" where status = 1");
			sql.append(" and parent_code = ?");
			sql.append(" order by name");
			cursor = database.rawQuery(sql.toString(), new String[] { shop.getProvince() + shop.getDistrict() });
		}

		if (cursor.moveToFirst()) {
			do {
				AreaObj obAreaObj = new AreaObj();
				String name = cursor.getString(0);
				obAreaObj.setName(name);
				String areaCode = cursor.getString(1);
				obAreaObj.setAreaCode(areaCode);

				lisAreaObjs.add(obAreaObj);
			} while (cursor.moveToNext());
		}
		return lisAreaObjs;
	}

	public ArrayList<AreaObj> getAllArea() throws Exception {
		ArrayList<AreaObj> lisAreaObjs = new ArrayList<>();
		ShopDAL shopDAL = new ShopDAL(database);
		Shop shop = shopDAL.getShopByShopId(Session.loginUser.getShopId());

        String sql = "Select name, area_code from area" +
                " where status = 1" +
                " and parent_code = ?" +
                " order by name";

        Cursor cursor = database.rawQuery(sql, new String[] { shop.getProvince() + shop.getDistrict() });

		if (cursor.moveToFirst()) {
			do {
				AreaObj obAreaObj = new AreaObj();
				String name = cursor.getString(0);
				Log.d("name", name);
				obAreaObj.setName(name);
				String areaCode = cursor.getString(1);
				Log.d("areaCode", areaCode);
				obAreaObj.setAreaCode(areaCode);

				lisAreaObjs.add(obAreaObj);
			} while (cursor.moveToNext());
		}
		return lisAreaObjs;
	}
	
	public String getNameFromAreaCode(String areaCode) {
		String sql = "select name, area_code from area where status = 1 and area_code = ?";
		Cursor c = database.rawQuery(sql, new String[]{areaCode});
		if(c!= null && c.moveToFirst()){
			return c.getString(0);
		}
		return "";
	}

	public AreaObj getArea(String areaCode) {
		AreaObj obAreaObj = null;
        String sql = "Select name, area_code,province,district,precinct from area" +
                " where status = 1" +
                " and area_code = ?" +
                " order by name";

        Cursor cursor = database.rawQuery(sql, new String[] { areaCode });

		if (cursor.moveToFirst()) {
			obAreaObj = new AreaObj();
			obAreaObj.setName(cursor.getString(0));
			obAreaObj.setAreaCode(cursor.getString(1));
			obAreaObj.setProvince(cursor.getString(2));
			obAreaObj.setDistrict(cursor.getString(3));
			obAreaObj.setPrecinct(cursor.getString(4));
		}
		return obAreaObj;
	}

    /**
     * Lay thong tin dia ban dua vao ma dia ban
     *
     * @param areaCode
     * @return
     * @throws Exception
     */
    public AreaObj getAreaFromAreaCode(String areaCode) throws Exception {
        AreaObj area = new AreaObj();
        String sql = "select name, area_code,province,district,precinct, full_name from area where status = 1 and area_code = ?";
        Cursor c = database.rawQuery(sql, new String[] { areaCode });
        if (c != null && c.moveToFirst()) {
            area.setName(c.getString(0));
            area.setAreaCode(c.getString(1));
            area.setProvince(c.getString(2));
            area.setDistrict(c.getString(3));
            area.setPrecinct(c.getString(4));
            area.setFullNamel(c.getString(5));

            return area;
        }
        return null;
    }
}
