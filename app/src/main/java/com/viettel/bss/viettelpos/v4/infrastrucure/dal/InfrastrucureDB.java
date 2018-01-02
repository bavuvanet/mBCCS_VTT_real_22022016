package com.viettel.bss.viettelpos.v4.infrastrucure.dal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.charge.object.TelecomServiceObj;
import com.viettel.bss.viettelpos.v4.commons.DBOpenHelper;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customview.obj.LoaiGiayToObj;
import com.viettel.bss.viettelpos.v4.infrastrucure.object.ApParamObj;

import java.util.ArrayList;

@SuppressLint("DefaultLocale")
public class InfrastrucureDB {
    private SQLiteDatabase database;
    private final DBOpenHelper mDAL;
    private final String TABLE_CURR_BILL_CYCLE = "curr_bill_cycle";// curr_bill_cycle
    private final String TABLE_AREA = "area";// area
    private final String TABLE_AP_PARAM = "ap_param";// area

    // table telecom service
    public static final String KEY_ID = "telecom_service_id";
    public static final String KEY_TEL_NAME = "tel_service_name";
    public static final String KEY_DATE = "create_date";

    // table area
    public static final String KEY_AREA_CODE = "area_code";
    public static final String KEY_PARENT_CODE = "parent_code";
    public static final String KEY_CEN_CODE = "cen_code";
    public static final String KEY_PROVINCE_CODE = "province";
    public static final String KEY_DISTRICT_CODE = "district";
    public static final String KEY_PERCINCT_CODE = "precinct";
    public static final String KEY_AREA_NAME = "name";
    public static final String KEY_AREA_FULL_NAME = "full_name";

    public InfrastrucureDB(Context context) {
        mDAL = new DBOpenHelper(context);
    }

    public void open() throws SQLException {
        database = mDAL.getWritableDatabase();
    }

    public void close() {
        try {
            mDAL.close();
            if (database != null) {
                database.close();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public void truncateVSATable() {
        database.execSQL("delete from VSA");
    }

    public ArrayList<AreaObj> getListArea() {
        ArrayList<AreaObj> mArrayList = new ArrayList<>();
        Cursor mCursor = null;
        SQLiteDatabase db = mDAL.getReadableDatabase();

        mCursor = db.query(TABLE_AREA, new String[]{}, null, null, null, null,
                null);
        if (mCursor.moveToFirst()) {
            while (!mCursor.isAfterLast()) {
                String areaCode = mCursor.getString(1);
                String parentCode = mCursor.getString(2);

                String province = mCursor.getString(4);
                String distict = mCursor.getString(5);
                String precinct = mCursor.getString(6);
                String name = mCursor.getString(9);
                String fullName = mCursor.getString(10);
                // String createDate = mCursor.getString(2).split(" ")[0];
                AreaObj areaObj = new AreaObj();
                areaObj.setAreaCode(areaCode);
                areaObj.setParentCode(parentCode);
                areaObj.setProvince(province);
                areaObj.setDistrict(distict);
                areaObj.setPrecinct(precinct);
                areaObj.setName(name);
                areaObj.setFullNamel(fullName);
                mArrayList.add(areaObj);
                mCursor.moveToNext();
            }
        }
        mCursor.close();
        db.close();
        Log.i("TAG2", "mArrayList size = " + mArrayList.size());
        return mArrayList;

    }

    public ArrayList<LoaiGiayToObj> getListLoaiGiayTo() {
        ArrayList<LoaiGiayToObj> mArrayList = new ArrayList<>();
        Cursor mCursor = null;
        SQLiteDatabase db = mDAL.getReadableDatabase();

        // select * from ap_param where par_name = 'TECHNOLOGY' ;
        // SELECT * FROM ap_param WHERE par_name = '301';
        String mQuery = "select" + " par_type,par_value from " + TABLE_AP_PARAM
                + " where par_name = '301_BCCS2' and status = '1' ";

        try {
            mCursor = db.rawQuery(mQuery, null);
            if (mCursor.moveToFirst()) {
                while (!mCursor.isAfterLast()) {
                    String parName = mCursor.getString(0);
                    String parValue = mCursor.getString(1);
                    LoaiGiayToObj apParam = new LoaiGiayToObj(parName, parValue);
                    mArrayList.add(apParam);
                    mCursor.moveToNext();
                }
            }
            mCursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i("TAG2", "mArrayList size = " + mArrayList.size());
        return mArrayList;

    }

    public ArrayList<LoaiGiayToObj> getListLoaiGiayToBCCS2() {
        ArrayList<LoaiGiayToObj> mArrayList = new ArrayList<LoaiGiayToObj>();
        Cursor mCursor = null;
        SQLiteDatabase db = mDAL.getReadableDatabase();

        // select * from ap_param where par_name = 'TECHNOLOGY' ;
        // SELECT * FROM ap_param WHERE par_name = '301';
        String mQuery = "select" + " par_type,par_value from " + TABLE_AP_PARAM
                + " where par_name = '301_BCCS2' and status = '1' order by id";

        try {
            mCursor = db.rawQuery(mQuery, null);
            if (mCursor.moveToFirst()) {
                while (!mCursor.isAfterLast()) {
                    String parName = mCursor.getString(0);
                    String parValue = mCursor.getString(1);
                    LoaiGiayToObj apParam = new LoaiGiayToObj(parName, parValue);
                    mArrayList.add(apParam);
                    mCursor.moveToNext();
                }
            }
            mCursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i("TAG2", "mArrayList size = " + mArrayList.size());
        return mArrayList;

    }


    public ArrayList<ApParamObj> getListParam() {
        ArrayList<ApParamObj> mArrayList = new ArrayList<>();
        Cursor mCursor = null;


        SQLiteDatabase db = mDAL.getReadableDatabase();

        // select * from ap_param where par_name = 'TECHNOLOGY' ;
        String mQuery = "select" + " * from " + TABLE_AP_PARAM
                + " where par_name = 'TECHNOLOGY'";

        mCursor = db.rawQuery(mQuery, null);

        if (mCursor.moveToFirst()) {
            while (!mCursor.isAfterLast()) {
                int id = mCursor.getInt(1);
                String parName = mCursor.getString(1);
                String parType = mCursor.getString(2);
                String parValue = mCursor.getString(3);
                String parDescription = mCursor.getString(4);
                ApParamObj apParam = new ApParamObj();
                apParam.setId(id);
                apParam.setDescription(parDescription);
                apParam.setParName(parName);
                apParam.setParType(parType);
                apParam.setParValue(parValue);
                mArrayList.add(apParam);
                mCursor.moveToNext();
            }
        }
        mCursor.close();
        db.close();
        Log.i("TAG2", "mArrayList size = " + mArrayList.size());
        return mArrayList;

    }

    public ArrayList<TelecomServiceObj> getListTelecomService() {
        ArrayList<TelecomServiceObj> mArrayList = new ArrayList<>();
        Cursor mCursor = null;
        SQLiteDatabase db = mDAL.getReadableDatabase();

        // select * from telecom_service where code in ('A','P','I','F') and
        // status = 1;
        String TABLE_TELECOM_SERVICE = "telecom_service";
        String mQuery = "select" + " telecom_service_id,name,service_alias from " + TABLE_TELECOM_SERVICE
                + " where service_alias in ('A','P','I','F') and status = 1";

        mCursor = db.rawQuery(mQuery, null);

        if (mCursor.moveToFirst()) {
            while (!mCursor.isAfterLast()) {
                int content_id = mCursor.getInt(0);
                String telName = mCursor.getString(1);
                String code = mCursor.getString(2);
                // String createDate = mCursor.getString(2).split(" ")[0];
                TelecomServiceObj telecomSv = new TelecomServiceObj();
                telecomSv.setTelecomServiceId(content_id);
                telecomSv.setTelServiceName(telName);
                telecomSv.setCode(code);
                mArrayList.add(telecomSv);
                mCursor.moveToNext();
            }
        }
        mCursor.close();
        db.close();
        Log.i("TAG2", "mArrayList size = " + mArrayList.size());
        return mArrayList;

    }

    public String[] getProvince() {
        String shop[] = new String[2];
        String province = "";
        Cursor mCursor = null;
        SQLiteDatabase db = null;
        try {
            db = mDAL.getReadableDatabase();

            String TABLE_SHOP = "shop";
            String mQuery = "select province, district "
                    + " from "
                    + TABLE_SHOP
                    + " where shop_id in (select shop_id from staff where upper(staff_code) = "
                    + " ?)";
            Log.i("TAG2", "Query shop : " + mQuery);

            mCursor = db.rawQuery(mQuery, new String[]{Session.userName.toUpperCase()});

            if (mCursor.moveToFirst()) {
                while (!mCursor.isAfterLast()) {
                    province = mCursor.getString(0);
                    String dis = mCursor.getString(1);
                    Log.i("TAG2", "province shop : " + province);
                    Log.i("TAG2", "dis shop : " + dis);
                    shop[0] = province;
                    shop[1] = dis;
                    mCursor.moveToNext();
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (mCursor != null) {
                mCursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        Log.i("TAG2", "shop size = " + shop.length);
        return shop;

    }

    public ArrayList<AreaObj> getListDis(String provinceCode) {
        ArrayList<AreaObj> mArrayList = new ArrayList<>();
        Cursor mCursor = null;
        SQLiteDatabase db = null;

        try {

            db = mDAL.getReadableDatabase();
            String mQuery = "select" + " * from " + TABLE_AREA
                    + " where area_code = ?"
                    + " and status = 1";
            Log.d("TAG DB", "Query getListDis : " + mQuery);
            mCursor = db.rawQuery(mQuery, new String[]{provinceCode});
            if (mCursor.moveToFirst()) {
                while (!mCursor.isAfterLast()) {
                    String areaCode = mCursor.getString(1);
                    String parentCode = mCursor.getString(2);

                    String province = mCursor.getString(4);
                    String distict = mCursor.getString(5);
                    String precinct = mCursor.getString(6);
                    String name = mCursor.getString(9);
                    String fullName = mCursor.getString(10);
                    // String createDate = mCursor.getString(2).split(" ")[0];
                    AreaObj areaObj = new AreaObj();
                    areaObj.setAreaCode(areaCode);
                    areaObj.setParentCode(parentCode);
                    areaObj.setProvince(province);
                    areaObj.setDistrict(distict);
                    areaObj.setPrecinct(precinct);
                    areaObj.setName(name);
                    areaObj.setFullNamel(fullName);
                    mArrayList.add(areaObj);
                    mCursor.moveToNext();
                }
            }

        } catch (Exception e) {
            Log.e(InfrastrucureDB.class.getName(), "Exception", e);
        } finally {
            if (mCursor != null) {
                mCursor.close();
            }
            if (db != null) {
                db.close();
            }
            Log.i("TAG2", "area_code size = " + mArrayList.size());
        }
        Log.i("TAG2", "area_code size = " + mArrayList.size());
        return mArrayList;
    }

    public ArrayList<AreaObj> getLisProvince() {
        ArrayList<AreaObj> mArrayList = new ArrayList<>();
        Cursor mCursor = null;
        SQLiteDatabase db = null;
        long begin = System.currentTimeMillis();
        try {

            Log.d(InfrastrucureDB.class.getName(), "Begin getLisProvince");
            db = mDAL.getReadableDatabase();
            String mQuery = "select distinct province, name"
                    + "  from "
                    + TABLE_AREA
                    + " where (district IS NULL OR district = '') AND (province IS NOT NULL and province !='') "
                    + " and status = 1 order by name";

            mCursor = db.rawQuery(mQuery, null);

            if (mCursor.moveToFirst()) {
                while (!mCursor.isAfterLast()) {
                    String province = mCursor.getString(0);
                    String name = mCursor.getString(1);

                    AreaObj areaObj = new AreaObj();
                    areaObj.setProvince(province);
                    areaObj.setName(name);
                    mArrayList.add(areaObj);
                    mCursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.e(InfrastrucureDB.class.getName(), "Exception", e);
        } finally {
            if (mCursor != null) {
                mCursor.close();
            }
            if (db != null) {
                db.close();
            }
            Log.i("TAG2", "area_code size = " + mArrayList.size());
        }
        Log.d(InfrastrucureDB.class.getName(), "End getLisProvince in " + (System.currentTimeMillis() - begin));
        Log.i("TAG2", "area_code size = " + mArrayList.size());
        return mArrayList;
    }

    public ArrayList<AreaObj> getLisDistrict(String parent_code) {
        ArrayList<AreaObj> mArrayList = new ArrayList<>();
        Cursor mCursor = null;
        SQLiteDatabase db = null;
        try {
            db = mDAL.getReadableDatabase();
            String mQuery = "select distinct area_code,district,name" + "  from "
                    + TABLE_AREA + " where parent_code = ? "
                    + " and status = 1 order by name";

            mCursor = db.rawQuery(mQuery, new String[]{parent_code});

            if (mCursor.moveToFirst()) {
                while (!mCursor.isAfterLast()) {
                    String area_code = mCursor.getString(0);
                    String district = mCursor.getString(1);
                    String name = mCursor.getString(2);

                    AreaObj areaObj = new AreaObj();
                    areaObj.setAreaCode(area_code);
                    areaObj.setDistrict(district);
                    areaObj.setName(name);
                    mArrayList.add(areaObj);
                    mCursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.e(InfrastrucureDB.class.getName(), "Exception", e);
        } finally {
            if (mCursor != null) {
                mCursor.close();
            }
            if (db != null) {
                db.close();
            }
            Log.i("TAG2", "area_code size = " + mArrayList.size());
        }


        return mArrayList;
    }

    public ArrayList<AreaObj> getListPrecinct(String parent_code) {
        ArrayList<AreaObj> mArrayList = new ArrayList<>();
        Cursor mCursor = null;
        SQLiteDatabase db = null;
        try {
            db = mDAL.getReadableDatabase();
            String mQuery = "select distinct precinct, name, area_code" + "  from "
                    + TABLE_AREA + " where parent_code = ? "
                    + " and status = 1 order by name";
            Log.d("Tag SQL", "mQuery : " + mQuery);
            mCursor = db.rawQuery(mQuery, new String[]{parent_code});

            if (mCursor.moveToFirst()) {
                while (!mCursor.isAfterLast()) {
                    String precinct = mCursor.getString(0);
                    String name = mCursor.getString(1);
                    String areaCode = mCursor.getString(2);

                    AreaObj areaObj = new AreaObj();
                    areaObj.setPrecinct(precinct);
                    areaObj.setName(name);
                    areaObj.setAreaCode(areaCode);
                    mArrayList.add(areaObj);
                    mCursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.e(InfrastrucureDB.class.getName(), "Exception", e);
        } finally {
            if (mCursor != null) {
                mCursor.close();
            }
            if (db != null) {
                db.close();
            }
            Log.i("TAG2", "area_code size = " + mArrayList.size());
        }


        Log.i("TAG2", "area_code size = " + mArrayList.size());
        return mArrayList;
    }

    public ArrayList<AreaObj> getListBTS(String provinceCode, String dis) {
        ArrayList<AreaObj> mArrayList = new ArrayList<>();
        Cursor mCursor = null;
        SQLiteDatabase db = mDAL.getReadableDatabase();
        String query = "";
        if (Session.loginUser != null) {
            String TABLE_BTS = "object_map";
            query = "select" + " id,children from " + TABLE_BTS
                    + " where province_code = " + "'" + provinceCode + "'"
                    + " AND " + "district_code = " + "'" + dis + "'" + " AND "
                    + "status =1 and staff_owner_id = " + "'"
                    + Session.loginUser.getStaffId() + "'"
                    + " and group_obj_id = 1  ";
            Log.i("TAG2", "query = " + query);
        }
        mCursor = db.rawQuery(query, null);

        if (mCursor.moveToFirst()) {
            while (!mCursor.isAfterLast()) {
                int id = mCursor.getInt(0);
                String name = mCursor.getString(1);
                AreaObj areaObj = new AreaObj();
                areaObj.setId(id);
                areaObj.setName(name);
                mArrayList.add(areaObj);
                mCursor.moveToNext();
            }
        }
        mCursor.close();
        db.close();
        Log.i("TAG2", "area_code size = " + mArrayList.size());
        return mArrayList;
    }

    public ArrayList<AreaObj> getListCell(String btsName) {
        ArrayList<AreaObj> mArrayList = new ArrayList<>();
        Cursor mCursor = null;
        SQLiteDatabase db = mDAL.getReadableDatabase();

        String TABLE_CELL_DETAIL = "cell_detail";
        String mQuery = "select" + " cell_id,cell_name from "
                + TABLE_CELL_DETAIL + " where bts_name = " + "'" + btsName
                + "'";

        mCursor = db.rawQuery(mQuery, null);

        if (mCursor.moveToFirst()) {
            while (!mCursor.isAfterLast()) {
                int id = mCursor.getInt(0);
                String name = mCursor.getString(1);
                AreaObj areaObj = new AreaObj();
                areaObj.setId(id);
                areaObj.setName(name);
                mArrayList.add(areaObj);
                mCursor.moveToNext();
            }
        }
        mCursor.close();
        db.close();
        Log.i("TAG2", "area_code size = " + mArrayList.size());
        return mArrayList;

    }

    public ArrayList<AreaObj> getListStreet(String parent_code) {
        ArrayList<AreaObj> mArrayList = new ArrayList<>();
        Cursor mCursor = null;
        SQLiteDatabase db = mDAL.getReadableDatabase();

        String mQuery = "select" + " * from " + TABLE_AREA
                + " where parent_code = " + "'" + parent_code + "'"
                + " and status = 1";

        mCursor = db.rawQuery(mQuery, null);

        if (mCursor.moveToFirst()) {
            while (!mCursor.isAfterLast()) {
                String areaCode = mCursor.getString(1);
                String parentCode = mCursor.getString(2);

                String province = mCursor.getString(4);
                String distict = mCursor.getString(5);
                String precinct = mCursor.getString(6);
                String name = mCursor.getString(9);
//                String fullName = mCursor.getString(10);
                // String createDate = mCursor.getString(2).split(" ")[0];
                AreaObj areaObj = new AreaObj();
                areaObj.setAreaCode(areaCode);
                areaObj.setParentCode(parentCode);
                areaObj.setProvince(province);
                areaObj.setDistrict(distict);
                areaObj.setPrecinct(precinct);
                areaObj.setName(name);
//                areaObj.setFullNamel(fullName);
                mArrayList.add(areaObj);
                mCursor.moveToNext();
            }
        }
        mCursor.close();
        db.close();
        Log.i("TAG2", "parent_code size = " + mArrayList.size());
        return mArrayList;

    }

    public String getProvince(String provinceCode) {
        String province = "";
        Cursor mCursor = null;
        SQLiteDatabase db = mDAL.getReadableDatabase();

        String mQuery = "select" + " * from " + TABLE_AREA
                + " where area_code = " + "'" + provinceCode + "'"
                + " and status = 1";

        mCursor = db.rawQuery(mQuery, null);

        if (mCursor.moveToFirst()) {
            while (!mCursor.isAfterLast()) {

                province = mCursor.getString(10);
                mCursor.moveToNext();
            }
        }
        mCursor.close();
        db.close();
        return province;

    }

    public ArrayList<AreaObj> getListTypeOfSale(String typeOfSale) {
        ArrayList<AreaObj> mArrayList = new ArrayList<>();
        Cursor mCursor = null;
        SQLiteDatabase db = null;
        try {
            db = mDAL.getReadableDatabase();
            // sua lay loai thong tin loai diem ban
//            String mQuery = "select value, name from option_set_value_product where type = ? and status = 1 and code not in (5,6,7)";
            StringBuilder query = new StringBuilder();
            query.append(" SELECT   value, name");
            query.append(" FROM   option_set_value_product");
            query.append(" WHERE   status = 1 and value not in('5','6','7')");
            query.append(" AND option_set_id =");
            query.append(" (SELECT   id");
            query.append(" FROM   option_set_product");
            query.append(" WHERE   status = 1 AND code = ?)");
            mCursor = db.rawQuery(query.toString(), new String[]{typeOfSale});
            Log.d("Tag SQL", "mQuery : " + query.toString());

            if (mCursor.moveToFirst()) {
                while (!mCursor.isAfterLast()) {
                    String codeSale = mCursor.getString(0);
                    String nameSale = mCursor.getString(1);
                    AreaObj areaObj = new AreaObj();
                    areaObj.setAreaCode(codeSale);
                    areaObj.setName(nameSale);
                    mArrayList.add(areaObj);
                    mCursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.e(InfrastrucureDB.class.getName(), "Exception", e);
        } finally {
            if (mCursor != null) {
                mCursor.close();
            }
            if (db != null) {
                db.close();
            }
            Log.i("TAG2", "area_code size = " + mArrayList.size());
        }
        return mArrayList;

    }


    public ArrayList<AreaObj> getListTypeOfSaleCreateChanel(String typeOfSale) {
        ArrayList<AreaObj> mArrayList = new ArrayList<>();
        Cursor mCursor = null;
        SQLiteDatabase db = null;
        try {
            db = mDAL.getReadableDatabase();
            String mQuery = "select PAR_VALUE, PAR_NAME from ap_param where PAR_TYPE = ? and status = 1";
            mCursor = db.rawQuery(mQuery, new String[]{typeOfSale});
            Log.d("Tag SQL", "mQuery : " + mQuery);

            if (mCursor.moveToFirst()) {
                while (!mCursor.isAfterLast()) {
                    String codeSale = mCursor.getString(0);
                    String nameSale = mCursor.getString(1);

                    AreaObj areaObj = new AreaObj();
                    areaObj.setAreaCode(codeSale);
                    areaObj.setName(nameSale);
                    mArrayList.add(areaObj);
                    mCursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.e(InfrastrucureDB.class.getName(), "Exception", e);
        } finally {
            if (mCursor != null) {
                mCursor.close();
            }
            if (db != null) {
                db.close();
            }
            Log.i("TAG2", "area_code size = " + mArrayList.size());
        }
        return mArrayList;

    }

}
