package com.viettel.bss.viettelpos.v4.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustIdentityDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustTypeDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferingDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ReasonDTO;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.BusTypeBean;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.BusTypePreBean;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentConnectionInfoSetting;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.CustomerGroupBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.CustomerTypeByCustGroupBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.NameProductBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ServiceBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.SubGroupBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.SubTypeBeans;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.report.object.BonusVasObject;
import com.viettel.bss.viettelpos.v3.connecttionService.model.ProductCatalogDTO;

import java.util.ArrayList;
import java.util.List;

public class CacheDatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mbccs.cache";
    private static final int DATABASE_VERSION = 2;

    // private SQLiteDatabase database;
    public CacheDatabaseManager(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        // openDataBase();
    }

    // private void openDataBase() throws SQLException {
    // String path = Define.PATH_DATABASE + DATABASE_NAME;
    // if (database == null) {
    // // createDataBase();
    // database = SQLiteDatabase.openDatabase(path, null,
    // SQLiteDatabase.OPEN_READWRITE);
    // }
    // }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_app_param = "create table app_param_local (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " par_name text," +
                " par_type text," +
                " par_value text," +
                " description text" +
                " )";

        String create_cache_local = "create table cache_local (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " item_key text," +
                " value_1 text," +
                " value_2 text," +
                " value_3 text," +
                " value_4 text," +
                " value_5 text," +
                " value_6 text," +
                " value_7 text," +
                " value_8 text," +
                " value_9 text," +
                " value_10 text" +
                " )";

        db.execSQL(create_app_param);
        db.execSQL(create_cache_local);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        db.execSQL("DROP TABLE IF EXISTS event");
        onCreate(db);

    }

    public void insertCusGroup(List<CustomerGroupBeans> lstData) {
        String parType = "custGroup";
        SQLiteDatabase database = null;
        try {
            database = this.getWritableDatabase();

            database.execSQL(" delete from app_param_local where par_type = ? ", new String[]{parType});
            if (lstData != null && lstData.size() > 0) {
                ContentValues values = new ContentValues();
                for (CustomerGroupBeans spin : lstData) {
                    values.put("par_Type", parType);
                    values.put("par_name", spin.getIdCusGroup());
                    values.put("par_value", spin.getCustTypeId());
                    values.put("description", spin.getNameCusGroup());
                    Long x = database.insert("app_param_local", null, values);
                    Log.e("insert OK  ", "" + x + " : " + spin.getNameCusGroup());
                }

            }
        } catch (Exception e) {
            Log.e("error insertCusGroup", "DATABASE_MANAGER", e);
        } finally {
            if (database != null) {
                database.close();

            }

        }
    }

    public void insertBusType(List<BusTypeBean> lstData) {
        String parType = "busType";
        SQLiteDatabase database = null;
        try {
            database = this.getWritableDatabase();

            database.execSQL(" delete from app_param_local where par_type = ? ", new String[]{parType});
            if (lstData != null && lstData.size() > 0) {
                ContentValues values = new ContentValues();
                for (BusTypeBean spin : lstData) {
                    values.put("par_Type", parType);
                    values.put("par_name", spin.getBusType());
                    values.put("par_value", spin.getCusType());
                    values.put("description", spin.getName());
                    Long x = database.insert("app_param_local", null, values);
                    Log.e("insert OK  ", "" + x + " : " + spin.getName());
                }

            }
        } catch (Exception e) {
            Log.e("error busType", "DATABASE_MANAGER", e);
        } finally {
            if (database != null) {
                database.close();

            }

        }
    }

    public void insertBusTypePre(List<BusTypePreBean> lstData) {
        String parType = "busTypePre";
        SQLiteDatabase database = null;
        try {
            database = this.getWritableDatabase();

            database.execSQL(" delete from app_param_local where par_type = ? ", new String[]{parType});
            if (lstData != null && lstData.size() > 0) {
                ContentValues values = new ContentValues();
                for (BusTypePreBean spin : lstData) {
                    values.put("par_Type", parType);
                    values.put("par_name", spin.getBusType());
                    values.put("par_value", spin.getCusType());
                    values.put("description", spin.getName());
                    Long x = database.insert("app_param_local", null, values);
                    Log.e("insert OK  ", "" + x + " : " + spin.getName());
                }

            }
        } catch (Exception e) {
            Log.e("error busTypePre", "DATABASE_MANAGER", e);
        } finally {
            if (database != null) {
                database.close();

            }

        }
    }

    public void insertNameProduct(List<NameProductBeans> lstData) {
        String parType = "nameProduct";
        SQLiteDatabase database = null;
        try {
            database = this.getWritableDatabase();

            database.execSQL(" delete from app_param_local where par_type = ? ", new String[]{parType});
            if (lstData != null && lstData.size() > 0) {
                ContentValues values = new ContentValues();
                for (NameProductBeans spin : lstData) {
                    values.put("par_Type", parType);
                    values.put("par_name", spin.getGroupName());
                    values.put("par_value", spin.getGroupProductId());
                    Long x = database.insert("app_param_local", null, values);
                    Log.e("insert OK  ", "" + x + " : " + spin.getGroupName());
                }
            }
        } catch (Exception e) {
            Log.e("error nameProduct", "DATABASE_MANAGER", e);
        } finally {
            if (database != null) {
                database.close();

            }

        }
    }

    public void insertService(List<ServiceBeans> lstData) {
        String parType = "service";
        SQLiteDatabase database = null;
        try {
            database = this.getWritableDatabase();

            database.execSQL(" delete from app_param_local where par_type = ? ", new String[]{parType});
            if (lstData != null && lstData.size() > 0) {
                ContentValues values = new ContentValues();
                for (ServiceBeans spin : lstData) {
                    values.put("par_Type", parType);
                    values.put("par_name", spin.getServiceType());
                    values.put("par_value", spin.getTelecomServiceId());
                    values.put("description", spin.getGroupName());
                    Long x = database.insert("app_param_local", null, values);
                    Log.e("insert OK  ", "" + x + " : " + spin.getGroupName());
                }
            }
        } catch (Exception e) {
            Log.e("error nameProduct", "DATABASE_MANAGER", e);
        } finally {
            if (database != null) {
                database.close();

            }

        }
    }

    public ArrayList<ServiceBeans> getListService() {
        SQLiteDatabase db = null;
        Cursor c = null;
        ArrayList<ServiceBeans> result = new ArrayList<>();
        try {
            db = this.getReadableDatabase();
            String parType = "service";

            String sql = "select par_name, par_value,description from app_param_local where par_type = ?";
            c = db.rawQuery(sql, new String[]{parType});

            if (c.moveToFirst()) {
                do {
                    ServiceBeans item = new ServiceBeans();
                    item.setServiceType(c.getString(0));
                    item.setTelecomServiceId(c.getString(1));
                    item.setGroupName(c.getString(2));
                    result.add(item);
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            Log.e("error getListBusTypeCache", "DATABASE_MANAGER", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return result;
    }

    public ArrayList<NameProductBeans> getListNameProduct() {
        SQLiteDatabase db = null;
        Cursor c = null;
        ArrayList<NameProductBeans> result = new ArrayList<>();
        try {
            db = this.getReadableDatabase();
            String parType = "nameProduct";

            String sql = "select par_name, par_value from app_param_local where par_type = ?";
            c = db.rawQuery(sql, new String[]{parType});

            if (c.moveToFirst()) {
                do {
                    NameProductBeans item = new NameProductBeans();
                    item.setGroupName(c.getString(0));
                    item.setGroupProductId(c.getString(1));
                    result.add(item);
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            Log.e("error getListNameProduct", "DATABASE_MANAGER", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return result;
    }

    public ArrayList<BusTypeBean> getListBusTypeCache() {
        SQLiteDatabase db = null;
        Cursor c = null;
        ArrayList<BusTypeBean> result = new ArrayList<>();
        try {
            db = this.getReadableDatabase();
            String parType = "busType";

            String sql = "select par_name, par_value,description from app_param_local where par_type = ?";
            c = db.rawQuery(sql, new String[]{parType});

            if (c.moveToFirst()) {
                do {
                    BusTypeBean item = new BusTypeBean();
                    item.setBusType(c.getString(0));
                    item.setCusType(c.getString(1));
                    item.setName(c.getString(2));
                    result.add(item);
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            Log.e("error getListBusTypeCache", "DATABASE_MANAGER", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return result;
    }

    public ArrayList<BusTypePreBean> getListBusTypePreCache() {
        SQLiteDatabase db = null;
        Cursor c = null;
        ArrayList<BusTypePreBean> result = new ArrayList<>();
        try {
            db = this.getReadableDatabase();
            String parType = "busTypePre";

            String sql = "select par_name, par_value,description from app_param_local where par_type = ?";
            c = db.rawQuery(sql, new String[]{parType});

            if (c.moveToFirst()) {
                do {
                    BusTypePreBean item = new BusTypePreBean();
                    item.setBusType(c.getString(0));
                    item.setCusType(c.getString(1));
                    item.setName(c.getString(2));
                    result.add(item);
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            Log.e("error getListBusTypePreCache", "DATABASE_MANAGER", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return result;
    }

    public ArrayList<CustomerGroupBeans> getListCustGroupInCache() {
        SQLiteDatabase db = null;
        Cursor c = null;
        ArrayList<CustomerGroupBeans> result = new ArrayList<>();
        try {
            db = this.getReadableDatabase();
            String parType = "custGroup";

            String sql = "select par_name, par_value,description from app_param_local where par_type = ?";
            c = db.rawQuery(sql, new String[]{parType});

            if (c.moveToFirst()) {
                do {
                    CustomerGroupBeans item = new CustomerGroupBeans();
                    item.setIdCusGroup(c.getString(0));
                    item.setCustTypeId(c.getString(1));
                    item.setNameCusGroup(c.getString(2));
                    result.add(item);
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            Log.e("error getListCustGroupInCache", "DATABASE_MANAGER", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return result;
    }

    public void insertCusTypeCache(String custGroupId, List<CustomerTypeByCustGroupBeans> lstData) {
        SQLiteDatabase db = null;
        String parType = "custType." + custGroupId;

        try {
            db = this.getWritableDatabase();
            db.execSQL("delete from app_param_local where par_type = ?", new String[]{parType});

            if (lstData != null && lstData.size() > 0) {
                for (CustomerTypeByCustGroupBeans spin : lstData) {
                    ContentValues values = new ContentValues();
                    values.put("par_Type", parType);
                    values.put("par_name", spin.getCode());
                    values.put("par_value", spin.getName());
                    values.put("description", spin.getName());
                    Long x = db.insert("app_param_local", null, values);
                    Log.e("insert OK  ", "" + x + " : " + spin.getName());
                }
            }

        } catch (Exception e) {
            android.util.Log.e("error insertCusGroup", "DATABASE_MANAGER", e);
        } finally {
            if (db != null) {
                db.close();
            }

        }
    }

    public ArrayList<CustomerTypeByCustGroupBeans> getListCustTypeInCache(String custGroupId) {
        SQLiteDatabase db = null;
        Cursor c = null;
        ArrayList<CustomerTypeByCustGroupBeans> result = new ArrayList<>();
        try {
            db = this.getReadableDatabase();
            String parType = "custType." + custGroupId;

            String sql = "select par_name, par_value, description from app_param_local where par_type = ?";
            c = db.rawQuery(sql, new String[]{parType});

            if (c.moveToFirst()) {
                do {
                    CustomerTypeByCustGroupBeans item = new CustomerTypeByCustGroupBeans();
                    item.setCode(c.getString(0));
                    item.setName(c.getString(1));

                    result.add(item);
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            android.util.Log.e("error getListCustGroupInCache", "DATABASE_MANAGER", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return result;
    }

    public void insertSubGroup(List<SubGroupBeans> lstData) {
        String parType = "subGroup";
        SQLiteDatabase database = null;
        try {
            database = this.getWritableDatabase();

            database.execSQL(" delete from app_param_local where par_type = ? ", new String[]{parType});
            if (lstData != null && lstData.size() > 0) {
                ContentValues values = new ContentValues();
                for (SubGroupBeans spin : lstData) {
                    values.put("par_Type", parType);
                    values.put("par_name", spin.getIdSubGroup());
                    values.put("par_value", spin.getName());
                    values.put("description", spin.getName());
                    Long x = database.insert("app_param_local", null, values);
                    Log.e("insert OK  ", "" + x + " : " + spin.getName());
                }

            }
        } catch (Exception e) {
            Log.e("error insertSubGroup", "DATABASE_MANAGER", e);
        } finally {
            if (database != null) {
                database.close();

            }

        }
    }

    public ArrayList<SubGroupBeans> getListSubGroupInCache() {
        SQLiteDatabase db = null;
        Cursor c = null;
        ArrayList<SubGroupBeans> result = new ArrayList<>();
        try {
            db = this.getReadableDatabase();
            String parType = "subGroup";

            String sql = "select par_name, par_value,description from app_param_local where par_type = ?";
            c = db.rawQuery(sql, new String[]{parType});

            if (c.moveToFirst()) {
                do {
                    SubGroupBeans item = new SubGroupBeans();
                    item.setIdSubGroup(c.getString(0));
                    item.setName(c.getString(1));
                    // item.setNameCusGroup(c.getString(2));
                    result.add(item);
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            Log.e("error getListCustGroupInCache", "DATABASE_MANAGER", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return result;
    }

    public void insertSubTypeCache(String telecomServiceId, String subGroupId, List<SubTypeBeans> lstData) {
        SQLiteDatabase db = null;
        String parType = "subType." + telecomServiceId + "." + subGroupId;

        try {
            db = this.getWritableDatabase();
            db.execSQL("delete from app_param_local where par_type = ?", new String[]{parType});

            if (lstData != null && lstData.size() > 0) {
                for (SubTypeBeans spin : lstData) {
                    ContentValues values = new ContentValues();
                    values.put("par_Type", parType);
                    values.put("par_name", spin.getSubType());
                    values.put("par_value", spin.getName());
                    values.put("description", spin.getName());
                    Long x = db.insert("app_param_local", null, values);
                    Log.e("insert OK  ", "" + x + " : " + spin.getName());
                }
            }

        } catch (Exception e) {
            android.util.Log.e("error insertSubTypeCache ", "DATABASE_MANAGER", e);
        } finally {
            if (db != null) {
                db.close();
            }

        }
    }

    public ArrayList<SubTypeBeans> getListSubTypeInCache(String telecomServiceId, String subGroupId) {
        SQLiteDatabase db = null;
        Cursor c = null;
        ArrayList<SubTypeBeans> result = new ArrayList<>();
        try {
            db = this.getReadableDatabase();
            String parType = "subType." + telecomServiceId + "." + subGroupId;

            String sql = "select par_name, par_value, description from app_param_local where par_type = ?";
            c = db.rawQuery(sql, new String[]{parType});

            if (c.moveToFirst()) {
                do {
                    SubTypeBeans item = new SubTypeBeans();
                    item.setSubType(c.getString(0));
                    item.setName(c.getString(1));

                    result.add(item);
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            android.util.Log.e("error getListSubTypeInCache", "DATABASE_MANAGER", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return result;
    }

    public void insertCacheList(int type, List<Spin> lstData) {
        SQLiteDatabase database = null;
        String parType = "";
        switch (type) {
            case FragmentConnectionInfoSetting.TYPE_HTTTHD:
                parType = "payMethod";
                break;
            case FragmentConnectionInfoSetting.TYPE_LOAI_HD:
                parType = "conTractType";
                break;
            case FragmentConnectionInfoSetting.TYPE_HTTBC_HD:
                parType = "contractNotice";
                break;
            case FragmentConnectionInfoSetting.TYPE_INCT_HD:
                parType = "contractPrint";
                break;
            case FragmentConnectionInfoSetting.TYPE_TTBS_HD:
                parType = "contractExtrasInfor";
                break;
            default:
                break;
        }
        try {
            database = this.getWritableDatabase();
            database.execSQL(" delete from app_param_local where par_type = ? ", new String[]{parType});

            if (lstData != null && lstData.size() > 0) {
                ContentValues values = new ContentValues();
                for (Spin spin : lstData) {
                    values.put("par_Type", parType);
                    values.put("par_name", spin.getId());
                    values.put("par_value", spin.getValue());
                    values.put("description", spin.getValue());
                    Long x = database.insert("app_param_local", null, values);
                    Log.e("insert OK  ", "" + x + " : " + spin.getValue());
                }
            }
        } catch (Exception e) {
            android.util.Log.e("error insert cache to database", "DATABASE_MANAGER", e);
        } finally {
            if (database != null) {
                database.close();
            }

        }
    }

    public ArrayList<Spin> getListInCache(int type) {
        SQLiteDatabase db = null;
        Cursor c = null;
        ArrayList<Spin> result = new ArrayList<>();
        try {
            db = this.getReadableDatabase();
            String parType = "N/A";
            switch (type) {
                case FragmentConnectionInfoSetting.TYPE_HTTTHD:
                    parType = "payMethod";
                    break;
                case FragmentConnectionInfoSetting.TYPE_LOAI_HD:
                    parType = "conTractType";
                    break;
                case FragmentConnectionInfoSetting.TYPE_HTTBC_HD:
                    parType = "contractNotice";
                    break;
                case FragmentConnectionInfoSetting.TYPE_INCT_HD:
                    parType = "contractPrint";
                    break;
                case FragmentConnectionInfoSetting.TYPE_TTBS_HD:
                    parType = "contractExtrasInfor";
                    break;
                default:
                    break;
            }
            String sql = "select par_name, par_value,description from app_param_local where par_type = ?";
            c = db.rawQuery(sql, new String[]{parType});

            if (c.moveToFirst()) {
                do {
                    Spin item = new Spin();
                    item.setId(c.getString(0));
                    item.setValue(c.getString(1));
                    result.add(item);
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            android.util.Log.e("error getListInCache", "DATABASE_MANAGER", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return result;
    }

    public void insertCacheBundleGroup(List<Spin> lstData, String parentId) {
        SQLiteDatabase database = null;
        String parType = "bundleGroup" + parentId;
        try {
            database = this.getWritableDatabase();
            database.execSQL(" delete from app_param_local where par_type = ? ", new String[]{parType});

            if (lstData != null && lstData.size() > 0) {
                ContentValues values = new ContentValues();
                for (Spin spin : lstData) {
                    values.put("par_Type", parType);
                    values.put("par_name", spin.getId());
                    values.put("par_value", spin.getValue());
                    values.put("description", spin.getCode());
                    Long x = database.insert("app_param_local", null, values);
                    Log.e("insert OK  ", "" + x + " : " + spin.getValue());
                }
            }
        } catch (Exception e) {
            android.util.Log.e("error insert cache to database", "DATABASE_MANAGER", e);
        } finally {
            if (database != null) {
                database.close();
            }

        }
    }

    public ArrayList<Spin> getListBundleGroupCache(String parentId) {
        SQLiteDatabase db = null;
        Cursor c = null;
        String parType = "bundleGroup" + parentId;
        ArrayList<Spin> result = new ArrayList<>();
        try {
            db = this.getReadableDatabase();

            String sql = "select par_name, par_value,description from app_param_local where par_type = ?";
            c = db.rawQuery(sql, new String[]{parType});

            if (c.moveToFirst()) {
                do {
                    Spin item = new Spin();
                    item.setId(c.getString(0));
                    item.setValue(c.getString(1));
                    item.setCode(c.getString(2));
                    result.add(item);
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            android.util.Log.e("error getListInCache", "DATABASE_MANAGER", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return result;
    }

    public void insertCacheStreetBlock(List<AreaObj> lstData, String parentId) {
        SQLiteDatabase database = null;
        String parType = "streetBlock_" + parentId;
        try {
            database = this.getWritableDatabase();
            database.execSQL(" delete from app_param_local where par_type = ? ", new String[]{parType});

            if (lstData != null && lstData.size() > 0) {
                ContentValues values = new ContentValues();
                for (AreaObj item : lstData) {
                    values.put("par_Type", parType);
                    values.put("par_name", item.getName());
                    values.put("par_value", item.getAreaCode());

                    database.insert("app_param_local", null, values);

                }
            }
        } catch (Exception e) {
            android.util.Log.e("error insert cache to database", "DATABASE_MANAGER", e);
        } finally {
            if (database != null) {
                database.close();
            }

        }
    }

    public ArrayList<AreaObj> getListCacheStreetBlock(String parentId) {
        SQLiteDatabase db = null;
        Cursor c = null;
        String parType = "streetBlock_" + parentId;
        ArrayList<AreaObj> result = new ArrayList<>();
        try {
            db = this.getReadableDatabase();

            String sql = "select par_name, par_value from app_param_local where par_type = ?";
            c = db.rawQuery(sql, new String[]{parType});

            if (c.moveToFirst()) {
                do {
                    AreaObj item = new AreaObj();
                    item.setName(c.getString(0));
                    item.setAreaCode(c.getString(1));
                    result.add(item);
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            android.util.Log.e("error getListInCache", "DATABASE_MANAGER", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return result;
    }

    public void insertCusTypePaymentCache(String cusTypeKey, List<Spin> lstData) {
        SQLiteDatabase db = null;
        String parType = "custType." + cusTypeKey;

        try {
            db = this.getWritableDatabase();
            db.execSQL("delete from app_param_local where par_type = ?", new String[]{parType});

            if (lstData != null && lstData.size() > 0) {
                for (Spin spin : lstData) {
                    ContentValues values = new ContentValues();
                    values.put("par_Type", parType);
                    values.put("par_name", spin.getCode());
                    values.put("par_value", spin.getName());
                    Long x = db.insert("app_param_local", null, values);
                    Log.e("insert OK  ", "" + x + " : " + spin.getName());
                }
            }

        } catch (Exception e) {
            android.util.Log.e("error insertCusTypePayment", "DATABASE_MANAGER", e);
        } finally {
            if (db != null) {
                db.close();
            }

        }
    }

    public ArrayList<Spin> getListCustTypePaymentInCache(String cusTypeKey) {
        SQLiteDatabase db = null;
        Cursor c = null;
        ArrayList<Spin> result = new ArrayList<>();
        try {
            db = this.getReadableDatabase();
            String parType = "custType." + cusTypeKey;

            String sql = "select par_name, par_value, description from app_param_local where par_type = ?";
            c = db.rawQuery(sql, new String[]{parType});

            if (c.moveToFirst()) {
                do {
                    Spin item = new Spin();
                    item.setCode(c.getString(0));
                    item.setName(c.getString(1));

                    result.add(item);
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            android.util.Log.e("error getListCustTypePaymentInCache", "DATABASE_MANAGER", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return result;
    }


    public ArrayList<Spin> getListInCacheBCCS(int type) {
        SQLiteDatabase db = null;
        Cursor c = null;
        ArrayList<Spin> result = new ArrayList<>();
        try {
            db = this.getReadableDatabase();
            String parType = "N/A";
            switch (type) {
                case 1:
                    parType = "payMethodBCCS";
                    break;
                case 4:
                    parType = "contractNoticeBCCS";
                    break;
                case 3:
                    parType = "contractPrintBCCS";
                    break;
                case 2:
                    parType = "billCycleBCCS";
                    break;
                default:
                    break;
            }
            String sql = "select par_name, par_value,description from app_param_local where par_type = ?";
            c = db.rawQuery(sql, new String[]{parType});

            if (c.moveToFirst()) {
                do {
                    Spin item = new Spin();
                    item.setId(c.getString(0));
                    item.setValue(c.getString(1));
                    result.add(item);
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            android.util.Log.e("error getListInCache", "DATABASE_MANAGER", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return result;
    }

    public void insertCacheListBCCS(int type, List<Spin> lstData) {
        SQLiteDatabase database = null;
        String parType = "";
        switch (type) {
            case 1:
                parType = "payMethodBCCS";
                break;
            case 2:
                parType = "billCycleBCCS";
                break;
            case 3:
                parType = "contractPrintBCCS";
                break;
            case 4:
                parType = "contractNoticeBCCS";
                break;

            default:
                break;
        }
        try {
            database = this.getWritableDatabase();
            database.execSQL(" delete from app_param_local where par_type = ? ", new String[]{parType});

            if (lstData != null && lstData.size() > 0) {
                ContentValues values = new ContentValues();
                for (Spin spin : lstData) {
                    values.put("par_Type", parType);
                    values.put("par_name", spin.getId());
                    values.put("par_value", spin.getValue());
                    values.put("description", spin.getValue());
                    Long x = database.insert("app_param_local", null, values);
                    Log.e("insert OK  ", "" + x + " : " + spin.getValue());
                }
            }
        } catch (Exception e) {
            android.util.Log.e("error insert cache to database", "DATABASE_MANAGER", e);
        } finally {
            if (database != null) {
                database.close();
            }

        }
    }

    public void insertQoutaCache(String telecomServiceId, String offerId, List<Spin> lstData) {
        SQLiteDatabase db = null;
        String parType = "qouta." + telecomServiceId + "." + offerId;

        try {
            db = this.getWritableDatabase();
            db.execSQL("delete from app_param_local where par_type = ?", new String[]{parType});

            if (lstData != null && lstData.size() > 0) {
                for (Spin spin : lstData) {
                    ContentValues values = new ContentValues();
                    values.put("par_Type", parType);
                    values.put("par_name", spin.getValue());
                    values.put("par_value", spin.getId());
                    values.put("description", spin.getValue());
                    Long x = db.insert("app_param_local", null, values);
                    Log.e("insert OK  ", "" + x + " : " + spin.getName());
                }
            }

        } catch (Exception e) {
            android.util.Log.e("error insertQoutaCache ", "DATABASE_MANAGER", e);
        } finally {
            if (db != null) {
                db.close();
            }

        }
    }

    public ArrayList<Spin> getListQoutaInCache(String telecomServiceId, String offerId) {
        SQLiteDatabase db = null;
        Cursor c = null;
        ArrayList<Spin> result = new ArrayList<>();
        try {
            db = this.getReadableDatabase();
            String parType = "qouta." + telecomServiceId + "." + offerId;

            String sql = "select par_name, par_value, description from app_param_local where par_type = ?";
            c = db.rawQuery(sql, new String[]{parType});

            if (c.moveToFirst()) {
                do {
                    Spin item = new Spin();
                    item.setValue(c.getString(0));
                    item.setId(c.getString(1));

                    result.add(item);
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            android.util.Log.e("error getListQoutaInCache", "DATABASE_MANAGER", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return result;
    }

    // cache thong tin dat coc

    public void insertDepositCache(List<Spin> lstData) {
        SQLiteDatabase db = null;
        String parType = "deposit";

        try {
            db = this.getWritableDatabase();
            db.execSQL("delete from app_param_local where par_type = ?", new String[]{parType});

            if (lstData != null && lstData.size() > 0) {
                for (Spin spin : lstData) {
                    ContentValues values = new ContentValues();
                    values.put("par_Type", parType);
                    values.put("par_name", spin.getValue());
                    values.put("par_value", spin.getId());
                    Long x = db.insert("app_param_local", null, values);
                    Log.e("insert OK  ", "" + x + " : " + spin.getName());
                }
            }

        } catch (Exception e) {
            android.util.Log.e("error insertQoutaCache ", "DATABASE_MANAGER", e);
        } finally {
            if (db != null) {
                db.close();
            }

        }
    }

    public ArrayList<Spin> getListDepositInCache() {
        SQLiteDatabase db = null;
        Cursor c = null;
        ArrayList<Spin> result = new ArrayList<>();
        try {
            db = this.getReadableDatabase();
            String parType = "deposit";

            String sql = "select par_name, par_value, description from app_param_local where par_type = ?";
            c = db.rawQuery(sql, new String[]{parType});

            if (c.moveToFirst()) {
                do {
                    Spin item = new Spin();
                    item.setValue(c.getString(0));
                    item.setId(c.getString(1));

                    result.add(item);
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            android.util.Log.e("error getListDepositInCache", "DATABASE_MANAGER", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return result;
    }

    public void insertCacheWithSpinObject(List<Spin> lstData, String parType) {
        SQLiteDatabase database = null;

        try {
            database = this.getWritableDatabase();
            database.execSQL(" delete from app_param_local where par_type = ? ", new String[]{parType});

            if (lstData != null && lstData.size() > 0) {
                ContentValues values = new ContentValues();
                for (Spin item : lstData) {
                    values.put("par_Type", parType);
                    values.put("par_name", item.getName());
                    values.put("par_value", item.getId());
                    values.put("description", item.getCode());

                    database.insert("app_param_local", null, values);

                }
            }
        } catch (Exception e) {
            android.util.Log.e("error insert cache to database", "DATABASE_MANAGER", e);
        } finally {
            if (database != null) {
                database.close();
            }

        }
    }

    public ArrayList<Spin> getListCacheWithSpinObject(String parType) {
        SQLiteDatabase db = null;
        Cursor c = null;
        ArrayList<Spin> result = new ArrayList<>();
        try {
            db = this.getReadableDatabase();

            String sql = "select par_name, par_value, description from app_param_local where par_type = ?";
            c = db.rawQuery(sql, new String[]{parType});

            if (c.moveToFirst()) {
                do {
                    Spin item = new Spin();
                    item.setValue(c.getString(0));
                    item.setId(c.getString(1));
                    item.setCode(c.getString(2));
                    result.add(item);
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            android.util.Log.e("error getListInCache", "DATABASE_MANAGER", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return result;
    }

    public void insertCacheReasonSale(List<ReasonDTO> lstData) {
        String parType = "reasonSale";
        SQLiteDatabase database = null;

        try {
            database = this.getWritableDatabase();
            database.execSQL(" delete from app_param_local where par_type = ? ", new String[]{parType});

            if (lstData != null && lstData.size() > 0) {
                ContentValues values = new ContentValues();
                for (ReasonDTO item : lstData) {
                    values.put("par_Type", parType);
                    values.put("par_name", item.getName());
                    values.put("par_value", item.getReasonId());
                    values.put("description", item.getCode());

                    database.insert("app_param_local", null, values);

                }
            }
        } catch (Exception e) {
            android.util.Log.e("error insert cache to database", "DATABASE_MANAGER", e);
        } finally {
            if (database != null) {
                database.close();
            }

        }
    }

    public ArrayList<ReasonDTO> getListCacheReasonSalet() {
        String parType = "reasonSale";
        SQLiteDatabase db = null;
        Cursor c = null;
        ArrayList<ReasonDTO> result = new ArrayList<>();
        try {
            db = this.getReadableDatabase();

            String sql = "select par_name, par_value, description from app_param_local where par_type = ?";
            c = db.rawQuery(sql, new String[]{parType});

            if (c.moveToFirst()) {
                do {
                    ReasonDTO item = new ReasonDTO();
                    item.setName(c.getString(0));
                    item.setReasonId(String.valueOf(c.getLong(1)));
                    item.setCode(c.getString(2));
                    result.add(item);
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            android.util.Log.e("error getListInCache", "DATABASE_MANAGER", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return result;
    }


    public void insertProductOfferringCache(String key,
                                            List<ProductOfferingDTO> lstData) {
        SQLiteDatabase db = null;
        String itemKey = "productOferringDTO." + key;

        try {
            db = this.getWritableDatabase();
            db.execSQL("delete from cache_local where item_key = ?",
                    new String[]{itemKey});

            if (lstData != null && lstData.size() > 0) {
                for (ProductOfferingDTO item : lstData) {
                    ContentValues values = new ContentValues();
                    values.put("item_key", itemKey);
                    values.put("value_1", item.getCode());
                    values.put("value_2", item.getProductOfferingId());
                    values.put("value_3", item.getName());
                    values.put("value_4", item.getDescription() + "");
                    Long x = db.insert("cache_local", null, values);
                    Log.e("insert OK  ",
                            "" + x + " : " + item.getProdPackTypeId());
                }
            }

        } catch (Exception e) {
            android.util.Log.e("error insertReasonCareCache",
                    "DATABASE_MANAGER", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public void insertBonusVasCache(List<BonusVasObject> lstData) {
        SQLiteDatabase db = null;
        String itemKey = "BonusVasObject";

        try {
            db = this.getWritableDatabase();
            db.execSQL("delete from cache_local where item_key = ?", new String[]{itemKey});

            if (lstData != null && lstData.size() > 0) {
                for (BonusVasObject item : lstData) {
                    ContentValues values = new ContentValues();
                    values.put("item_key", itemKey);
                    values.put("value_1", item.getBonus());
                    values.put("value_2", item.getBonusRate());
                    values.put("value_3", item.getCycleDays());
                    values.put("value_4", item.getCycleName());
                    values.put("value_5", item.getServiceCode());
                    values.put("value_6", item.getVasCode());
                    values.put("value_7", item.getVasPrice());
                    values.put("value_8", item.getVasType());
                    Long x = db.insert("cache_local", null, values);
                    Log.e("insert OK  ", "" + x + " : " + item.getVasCode());
                }
            }

        } catch (Exception e) {
            android.util.Log.e("error insertBonusVasCache", "DATABASE_MANAGER", e);
        } finally {
            if (db != null) {
                db.close();
            }

        }
    }

    public ArrayList<BonusVasObject> getListBonusVasInCache() {
        SQLiteDatabase db = null;
        Cursor c = null;
        ArrayList<BonusVasObject> result = new ArrayList<>();
        String itemKey = "BonusVasObject";
        try {
            db = this.getReadableDatabase();
            String sql = "select value_1, value_2,value_3,value_4,value_5,value_6,value_7,value_8 from cache_local where item_key = ?";
            c = db.rawQuery(sql, new String[]{itemKey});

            if (c.moveToFirst()) {
                do {
                    BonusVasObject item = new BonusVasObject();
                    int i = 0;
                    item.setBonus(c.getString(i));
                    i++;
                    item.setBonusRate(c.getString(i));
                    i++;
                    item.setCycleDays(c.getString(i));
                    i++;
                    item.setCycleName(c.getString(i));
                    i++;
                    item.setServiceCode(c.getString(i));
                    i++;
                    item.setVasCode(c.getString(i));
                    i++;
                    item.setVasPrice(c.getString(i));
                    i++;
                    item.setVasType(c.getString(i));
                    result.add(item);
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            android.util.Log.e("error getListReasonCareInCache", "DATABASE_MANAGER", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return result;
    }

    // cache loai khach hang cua bccs 2
    public void insertCusType(String parType, List<CustTypeDTO> lstData) {
        SQLiteDatabase database = null;
        try {
            database = this.getWritableDatabase();

            database.execSQL(" delete from app_param_local where par_type = ? ", new String[]{parType});
            if (lstData != null && lstData.size() > 0) {
                ContentValues values = new ContentValues();
                for (CustTypeDTO spin : lstData) {
                    values.put("par_Type", parType);
                    values.put("par_name", spin.getName());
                    values.put("par_value", spin.getCustType());
                    values.put("description", spin.getGroupType());
                    Long x = database.insert("app_param_local", null, values);
                    Log.e("insert OK  ", "" + x + " : " + spin.getName());
                }

            }
        } catch (Exception e) {
            Log.e("error custy", "DATABASE_MANAGER", e);
        } finally {
            if (database != null) {
                database.close();
            }
        }
    }

    public ArrayList<CustTypeDTO> getListCusTypeDTO(String parType) {
        SQLiteDatabase db = null;
        Cursor c = null;
        ArrayList<CustTypeDTO> result = new ArrayList<>();
        try {
            db = this.getReadableDatabase();

            String sql = "select par_name, par_value,description from app_param_local where par_type = ?";
            c = db.rawQuery(sql, new String[]{parType});

            if (c.moveToFirst()) {
                do {
                    CustTypeDTO item = new CustTypeDTO();
                    item.setName(c.getString(0));
                    item.setCustType(c.getString(1));
                    item.setGroupType(c.getString(2));
                    result.add(item);
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            Log.e("error getListCusTypeDTO", "DATABASE_MANAGER", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return result;
    }

    public ArrayList<CustIdentityDTO> getListTypePaperFromMap(String currType) {
        SQLiteDatabase db = null;
        Cursor c = null;
        ArrayList<CustIdentityDTO> result = new ArrayList<>();
        try {
            db = this.getReadableDatabase();
            String parType = "typePaper." + currType;

            String sql = "select par_name, par_value, description from app_param_local where par_type = ?";
            c = db.rawQuery(sql, new String[]{parType});

            if (c.moveToFirst()) {
                do {
                    CustIdentityDTO item = new CustIdentityDTO();
                    item.setIdType(c.getString(0));
                    item.setIdTypeName(c.getString(1));

                    result.add(item);
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            android.util.Log.e("error getListTypePaperFromMap", "DATABASE_MANAGER", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return result;
    }

    public void insertTypePaper(String currCusType, List<CustIdentityDTO> lstData) {
        SQLiteDatabase db = null;
        String parType = "custType." + currCusType;

        try {
            db = this.getWritableDatabase();
            db.execSQL("delete from app_param_local where par_type = ?", new String[]{parType});

            if (lstData != null && lstData.size() > 0) {
                for (CustIdentityDTO spin : lstData) {
                    ContentValues values = new ContentValues();
                    values.put("par_Type", parType);
                    values.put("par_name", spin.getIdType());
                    values.put("par_value", spin.getIdTypeName());
                    Long x = db.insert("app_param_local", null, values);
                    Log.e("insert OK  ", "" + x + " : " + spin.getIdTypeName());
                }
            }

        } catch (Exception e) {
            android.util.Log.e("error insertTypePaper", "DATABASE_MANAGER", e);
        } finally {
            if (db != null) {
                db.close();
            }

        }
    }

    // them danh sach dich vu
    public void insertServiceType(ArrayList<ProductCatalogDTO> lstData) {
        String parType = "serviceTypeBCCS";
        SQLiteDatabase database = null;
        try {
            database = this.getWritableDatabase();

            database.execSQL(" delete from app_param_local where par_type = ? ", new String[]{parType});
            if (lstData != null && lstData.size() > 0) {
                ContentValues values = new ContentValues();
                for (ProductCatalogDTO spin : lstData) {
                    values.put("par_Type", parType);
                    values.put("par_name", spin.getTelServiceAlias());
                    values.put("par_value", spin.getTelecomServiceId() + "");
                    values.put("description", spin.getName());
                    Long x = database.insert("app_param_local", null, values);
                    Log.e("insert OK  ", "" + x + " : " + spin.getDescription());
                }

            }
        } catch (Exception e) {
            Log.e("error insertServiceType", "DATABASE_MANAGER", e);
        } finally {
            if (database != null) {
                database.close();

            }

        }
    }

    // lay thong tin dich vu dau noi co dinh
    public ArrayList<ProductCatalogDTO> getListServiceTypeBCCS() {
        SQLiteDatabase db = null;
        Cursor c = null;
        ArrayList<ProductCatalogDTO> result = new ArrayList<ProductCatalogDTO>();
        try {
            db = this.getReadableDatabase();
            String parType = "serviceTypeBCCS";

            String sql = "select par_name, par_value,description from app_param_local where par_type = ?";
            c = db.rawQuery(sql, new String[]{parType});

            if (c.moveToFirst()) {
                do {
                    ProductCatalogDTO item = new ProductCatalogDTO();
                    item.setTelServiceAlias(c.getString(0));
                    item.setTelecomServiceId(Integer.parseInt(c.getString(1)));
                    item.setDescription(c.getString(2));
                    result.add(item);
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            Log.e("error getListServiceTypeBCCS", "DATABASE_MANAGER", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return result;
    }
    // them nhom loai khach hang BCCS2 trong cache
    public void insertCusGroupBCCS(ArrayList<Spin> lstData) {
        String parType = "custGroup";
        SQLiteDatabase database = null;
        try {
            database = this.getWritableDatabase();

            database.execSQL(" delete from app_param_local where par_type = ? ", new String[]{parType});
            if (lstData != null && lstData.size() > 0) {
                ContentValues values = new ContentValues();
                for (Spin spin : lstData) {
                    values.put("par_name", spin.getName());
                    values.put("par_value", spin.getValue());
                    Long x = database.insert("app_param_local", null, values);
                    Log.e("insert OK  ", "" + x + " : " + spin.getName());
                }

            }
        } catch (Exception e) {
            Log.e("error insertCusGroupBCCS", "DATABASE_MANAGER", e);
        } finally {
            if (database != null) {
                database.close();

            }

        }
    }
    // ham lay danh sach nhom khach hang
    public ArrayList<Spin> getListCustGroupInCacheBCCS() {
        SQLiteDatabase db = null;
        Cursor c = null;
        ArrayList<Spin> result = new ArrayList<>();
        try {
            db = this.getReadableDatabase();
            String parType = "custGroup";

            String sql = "select par_name, par_value from app_param_local where par_type = ?";
            c = db.rawQuery(sql, new String[]{parType});

            if (c.moveToFirst()) {
                do {
                    Spin item = new Spin();
                    item.setName(c.getString(0));
                    item.setCode(c.getString(1));
                    result.add(item);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            Log.e("error getListCustGroupInCacheBCCS", "DATABASE_MANAGER", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return result;
    }

}
