package com.viettel.bss.viettelpos.v4.connecttionService.dal;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.commons.DBOpenHelper;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.AreaBean;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class GetAreaDal {
	private SQLiteDatabase database;
	private final DBOpenHelper dal;

	public GetAreaDal(Context context) {
		dal = new DBOpenHelper(context);
	}

	public void open() throws SQLException {
		database = dal.getWritableDatabase();
	}

	public void close() {
		try{
			dal.close();
			database.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	// get location test from app_param for test
	public String getX() {


        return "";
	}
	
	
	
	public AreaBean getAreaDal(String locationCode) {
		AreaBean areaBean = new AreaBean();
        Cursor cursor = null;
        cursor = database.rawQuery("SELECT province, district, precinct,full_name FROM area WHERE area_code = ?", new String[]{locationCode});
		if(cursor == null){
			return areaBean;
		}
		if(cursor.moveToFirst()){
			do {
				String province = cursor.getString(0);
				Log.d("province", province);
				areaBean.setProvince(province);
				String district = cursor.getString(1);
				Log.d("district", district);
				areaBean.setDistrict(district);
				String precinct = cursor.getString(2);
				Log.d("precinct", precinct);
				areaBean.setPrecinct(precinct);
				String full_name = cursor.getString(3);
				Log.d("full_name", full_name);
				areaBean.setFullAddress(full_name);
			} while (cursor.moveToNext());
		}
		return areaBean;
	}
	public String getfulladddress(String locationCode) {
		String address = "";
        Cursor cursor = null;
        cursor = database.rawQuery("select full_name from area where area_code = ?", new String[]{locationCode});
		if(cursor == null){
			return address;
		}
		if(cursor.moveToFirst()){
			do {
			
				address = cursor.getString(0);
				
				
			} while (cursor.moveToNext());
		}
		return address;
	}
	// get list province 
	public ArrayList<AreaBean> getLstProvince() {
		ArrayList<AreaBean> lstProvinceArea = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		Cursor cursor = null;
		sql.append("select  province,name  from area Where district ='' and province <> '' and status = 1 order by name");
		cursor = database.rawQuery(sql.toString(), null);
		Log.d("", sql.toString());
		if(cursor == null){
			return lstProvinceArea;
		}
		if(cursor.moveToFirst()){
			do {
				AreaBean areaBean = new AreaBean();
				String province = cursor.getString(0);
//				Log.d("province", province);
				areaBean.setProvince(province);
				String nameProvince = cursor.getString(1);
//				Log.d("nameProvince", nameProvince);
				areaBean.setNameProvince(nameProvince);
				lstProvinceArea.add(areaBean);
			} while (cursor.moveToNext());
		}
		return lstProvinceArea;
	}
	
	// get list district 
	public ArrayList<AreaBean> getLstDistrict(String provinceInput) {
		ArrayList<AreaBean> lstProvinceArea = new ArrayList<>();
        Cursor cursor = null;
        cursor = database.rawQuery("select district,name from area where parent_code = ?  and status = 1 order by name", new String[]{provinceInput});
		if(cursor == null){
			return lstProvinceArea;
		}
		if(cursor.moveToFirst()){
			do {
				AreaBean areaBean = new AreaBean();
				areaBean.setProvince(provinceInput);
				String district = cursor.getString(0);
//				Log.d("district", district);
				areaBean.setDistrict(district);
				String nameDistrict = cursor.getString(1);
//				Log.d("nameDistrict", nameDistrict);
				areaBean.setNameDistrict(nameDistrict);
				lstProvinceArea.add(areaBean);
			} while (cursor.moveToNext());
		}
		return lstProvinceArea;
	}
	// get list precinct 
	public ArrayList<AreaBean> getLstPrecinct(String provinceInput,String districtInput) {
		ArrayList<AreaBean> lstProvinceArea = new ArrayList<>();
        Cursor cursor = null;
		StringBuilder sql = new StringBuilder("");
		sql.append("select precinct,name, area_code  from area where parent_code = ? and status = 1 order by name");
		cursor = database.rawQuery(sql.toString(), new String[]{provinceInput+districtInput});
		if(cursor == null){
			return lstProvinceArea;
		}
		if(cursor.moveToFirst()){
			do {
				AreaBean areaBean = new AreaBean();
				areaBean.setProvince(provinceInput);
				areaBean.setDistrict(districtInput);
				String precinct = cursor.getString(0);
//				Log.d("precinct", precinct);
				areaBean.setPrecinct(precinct);
				String namePrecinct = cursor.getString(1);
//				Log.d("namePrecinct", namePrecinct);
				areaBean.setNamePrecint(namePrecinct);
				areaBean.setAreaCode(cursor.getString(2));
				lstProvinceArea.add(areaBean);
			} while (cursor.moveToNext());
		}
		return lstProvinceArea;
	}
	
	
	// lay ten tinh
	public String getNameProvince(String province) throws Exception{
		String nameProvince = "";
		StringBuilder sql = new StringBuilder();
		Cursor cursor = null;
		sql.append("select  name  from area Where district ='' and province = ?  order by name");
		cursor = database.rawQuery(sql.toString(), new String[]{province});
		Log.d("", sql.toString());
		if(cursor == null){
			return nameProvince;
		}
		if(cursor.moveToFirst()){
			do {
				nameProvince = cursor.getString(0);
			} while (cursor.moveToNext());
		}
		return nameProvince;
	}
	
	
	// lay ten huyen
		public String getNameDistrict(String province, String district) throws Exception{
			String nameProvince = "";
			StringBuilder sql = new StringBuilder();
			Cursor cursor = null;
			sql.append("select  name  from area Where   province =  ? and district  = ?  and precinct = ''");
			cursor = database.rawQuery(sql.toString(), new String[]{province,district});
			Log.d("", sql.toString());
			if(cursor == null){
				return nameProvince;
			}
			if(cursor.moveToFirst()){
				do {
					nameProvince = cursor.getString(0);
				} while (cursor.moveToNext());
			}
			return nameProvince;
		}
		//select precinct,name  from area where parent_code = ? order by name

		
		// lay thong tin map code
		public String getmAPVTMap(String area_code) {
			String vtmap_code = "";
			StringBuilder sql = new StringBuilder();
			Cursor cursor = null;
			sql.append("select area_map_vtmap from area where area_code = ?");
			cursor = database.rawQuery(sql.toString(), new String[]{area_code});
			if(cursor == null){
				return vtmap_code;
			}
			if(cursor.moveToFirst()){
				do {
					vtmap_code = cursor.getString(0);
				} while (cursor.moveToNext());
			}
			return vtmap_code;
		}
		
		// lay ten huyen
		public String getNamePrecint(String province, String district , String precint) {
			String nameProvince = "";
			StringBuilder sql = new StringBuilder();
			Cursor cursor = null;
			sql.append("select  name  from area Where   province =  ? and district  = ?  and precinct = ?");
			cursor = database.rawQuery(sql.toString(), new String[]{province,district,precint});
			Log.d("", sql.toString());
			if(cursor == null){
				return nameProvince;
			}
			if(cursor.moveToFirst()){
				do {
					nameProvince = cursor.getString(0);
				} while (cursor.moveToNext());
			}
			return nameProvince;
		}
		
		public ArrayList<AreaBean> getLstDistrictAreaCode(String provinceInput) throws Exception{
			ArrayList<AreaBean> lstProvinceArea = new ArrayList<AreaBean>();
			StringBuilder sql = new StringBuilder();
			Cursor cursor = null;
			sql.append("select area_code,name from area where parent_code = ? and status = 1  order by name");
			cursor = database.rawQuery(sql.toString(), new String[]{provinceInput});
			if(cursor == null){
				return lstProvinceArea;
			}
			if(cursor.moveToFirst()){
				do {
					AreaBean areaBean = new AreaBean();
					areaBean.setProvince(provinceInput);
					String district = cursor.getString(0);
//					Log.d("district", district);
					areaBean.setDistrict(district);
					String nameDistrict = cursor.getString(1);
//					Log.d("nameDistrict", nameDistrict);
					areaBean.setNameDistrict(nameDistrict);
					lstProvinceArea.add(areaBean);
				} while (cursor.moveToNext());
			}
			return lstProvinceArea;
		}
		// get list precinct 
		public ArrayList<AreaBean> getLstPrecinctAreaCode(String provinceInput,String districtInput) throws Exception{
			ArrayList<AreaBean> lstProvinceArea = new ArrayList<AreaBean>();
			StringBuilder sql = new StringBuilder();
			Cursor cursor = null;
			sql.append("select area_code,name  from area where parent_code = ? and status = 1 order by name");
			cursor = database.rawQuery(sql.toString(), new String[]{districtInput});
			if(cursor == null){
				return lstProvinceArea;
			}
			if(cursor.moveToFirst()){
				do {
					AreaBean areaBean = new AreaBean();
					areaBean.setProvince(provinceInput);
					areaBean.setDistrict(districtInput);
					String precinct = cursor.getString(0);
//					Log.d("precinct", precinct);
					areaBean.setPrecinct(precinct);
					String namePrecinct = cursor.getString(1);
//					Log.d("namePrecinct", namePrecinct);
					areaBean.setNamePrecint(namePrecinct);
					lstProvinceArea.add(areaBean);
				} while (cursor.moveToNext());
			}
			return lstProvinceArea;
		}
	
		
		
		// lay ten huyen
			public String getNameDistrictAreaCode(String district) throws Exception{
				String nameProvince = "";
				StringBuilder sql = new StringBuilder();
				Cursor cursor = null;
				sql.append("select  name  from area Where   area_code = ?  and precinct = ''");
				cursor = database.rawQuery(sql.toString(), new String[]{district});
				Log.d("", sql.toString());
				if(cursor == null){
					return nameProvince;
				}
				if(cursor.moveToFirst()){
					do {
						nameProvince = cursor.getString(0);
					} while (cursor.moveToNext());
				}
				return nameProvince;
			}
			public String getNamePrecintAreaCode(String precint) throws Exception{
				String nameProvince = "";
				StringBuilder sql = new StringBuilder();
				Cursor cursor = null;
				sql.append("select  name  from area Where   area_code = ?'");
				cursor = database.rawQuery(sql.toString(), new String[]{precint});
				Log.d("", sql.toString());
				if(cursor == null){
					return nameProvince;
				}
				if(cursor.moveToFirst()){
					do {
						nameProvince = cursor.getString(0);
					} while (cursor.moveToNext());
				}
				return nameProvince;
			}
}
