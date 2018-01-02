package com.viettel.bss.viettelpos.v4.sale.dal;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.viettel.bss.viettelpos.v4.charge.object.ContractFormMngtBean;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.DBOpenHelper;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.customview.adapter.DoiTuongObj;
import com.viettel.bss.viettelpos.v4.sale.object.BhldObject;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;

import java.util.ArrayList;

public class BhldDAL {
    private SQLiteDatabase database;
    private final DBOpenHelper dal;
    private final String logTag = BhldDAL.class.getName();

    public BhldDAL(Context context) {
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

    final String tag = "bhld dal";

    /**
     * Lay danh sach chuong trinh BHLD do nhan vien tao ra
     * staffId
	 * @param
     * @return
     * @throws Exception
     */
    public ArrayList<BhldObject> getListProgram() {
		ArrayList<BhldObject> result = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
		ArrayList<String> param = new ArrayList<>();
        sql.append(" SELECT rw.record_work_id ,");
        sql.append(" sp.code ,");
        sql.append(" sp.name ,");

        sql.append(" rw.full_address");
        sql.append(" ,rw.from_date, rw.TO_DATE ");
        sql.append(" FROM   record_work rw, sale_program sp");
        sql.append(" WHERE   rw.status = 3 AND rw.sale_program_id = sp.program_id");
        sql.append(" AND EXISTS");
        sql.append(" (SELECT 1");
        sql.append(" FROM   register_record rr");
        sql.append(" WHERE   rr.record_work_id = rw.record_work_id ");
        sql.append(" AND rr.status  = 1");
        sql.append(" AND rr.staff_id  = ? )");
        Cursor c = null;
        try {
            param.add(Session.loginUser.getStaffId() + "");
            sql.append(" AND rw.from_date <= date('now')");
            sql.append(" AND (rw.to_date ='' or rw.to_date is null or rw.TO_DATE >= date('now')) ");
            String[] strParam = param.toArray(new String[param.size()]);
            c = database.rawQuery(sql.toString(), strParam);
            if (c == null) {
                return result;
            }

            if (c.moveToFirst()) {
                do {
                    BhldObject item = new BhldObject();
                    item.setRecordWorkId(c.getLong(0));
                    item.setSaleProgramCode(c.getString(1));
                    item.setSaleProgramName(c.getString(2));
                    item.setAddress(c.getString(3));
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

        return result;
    }

    /**
     * @param recordWorkId
     * @return
     * @throws Exception
     */
    // TODO modify 6/5/2015
    public ArrayList<Staff> getListObjectByProgram(Long recordWorkId) {
		ArrayList<Staff> result = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT   a.record_work_id recordworkid,");
        sql.append(" c.staff_code staffcode,");
        sql.append(" c.staff_name staffname");
        sql.append(" FROM   record_work a, register_record c");
        sql.append(" WHERE       a.record_work_id  = c.record_work_id ");
        sql.append(" AND a.status = 3 ");
        sql.append(" AND c.status   IN (1, 2)");
        sql.append(" AND c.staff_type  IN (2, 3, 4)");
        sql.append(" AND  a.record_Work_Id  = ? ");
        sql.append(" AND  upper(c.staff_code)  != ? ");
        Cursor c = null;
        try {
            c = database.rawQuery(sql.toString(), new String[]{recordWorkId + "", Session.userName.toUpperCase()});
            if (c == null) {
                return result;
            }
            if (c.moveToFirst()) {
                do {

                    Staff item = new Staff();
                    item.setStaffCode(c.getString(1));
                    item.setName(c.getString(2));
                    result.add(item);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (c != null) {
                c.close();
            }
        }

        return result;
    }

    // select * from app_params where type = 'PAYMENT_DEBIT_BANK' and status = 1
    public ArrayList<Spin> getListBank(String type) {

		ArrayList<Spin> result = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
//        sql.append(" select value,name  from option_set_value where status = 1 ");


        sql.append("SELECT   VALUE, name");
        sql.append(" FROM   option_set_value_im");
        sql.append(" WHERE   status = 1");
        sql.append(" AND option_set_id = (SELECT   id");
        sql.append(" FROM   option_set_im");
        sql.append(" WHERE   code = ?)");

        Cursor c = null;
        try {
            c = database.rawQuery(sql.toString(), new String[]{type + ""});
            if (c == null) {
                return result;
            }
            if (c.moveToFirst()) {
                do {
                    Spin item = new Spin();
                    item.setId(c.getString(0));
                    item.setValue(c.getString(1));
                    result.add(item);
                } while (c.moveToNext());
            }
		} catch (Exception ignored) {
        } finally {
            if (c != null) {
                c.close();
            }
        }

        return result;
    }

    @SuppressWarnings("resource")
    public ArrayList<ContractFormMngtBean> getListBankFromBCCS(ContractFormMngtBean searchBean) {

		ArrayList<ContractFormMngtBean> result = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append(" select bank_code , name from bank ");
        if (searchBean != null) {
            if (!CommonActivity.isNullOrEmpty(searchBean.getCode())
                    && !CommonActivity.isNullOrEmpty(searchBean.getName())) {
                sql.append(" where ");
                sql.append(" lower(bank_code) like " + "?");
                sql.append(" and lower(name) like " + "?");
            } else {

                if (!CommonActivity.isNullOrEmpty(searchBean.getCode())) {
                    sql.append(" where ");
                    sql.append(" lower(bank_code) like " + "?");
                }
                if (!CommonActivity.isNullOrEmpty(searchBean.getName())) {
                    sql.append(" where ");
                    sql.append(" lower(name) like " + "?");
                }

            }
        }
        Cursor c = null;
        try {
            if (searchBean == null) {
                c = database.rawQuery(sql.toString(), new String[]{});
            }
            if (!CommonActivity.isNullOrEmpty(searchBean.getCode())
                    && !CommonActivity.isNullOrEmpty(searchBean.getName())) {
                c = database.rawQuery(sql.toString(), new String[]{"%" + searchBean.getCode().toLowerCase() + "%",
                        "%" + searchBean.getName() + "%"});
            } else {
                if (!CommonActivity.isNullOrEmpty(searchBean.getCode())) {
                    c = database.rawQuery(sql.toString(),
                            new String[]{"%" + searchBean.getCode().toLowerCase() + "%"});
                }
                if (!CommonActivity.isNullOrEmpty(searchBean.getName())) {
                    c = database.rawQuery(sql.toString(),
                            new String[]{"%" + searchBean.getName().toLowerCase() + "%"});
                }
            }

            if (c == null) {
                return result;
            }
            if (c.moveToFirst()) {
                do {
                    ContractFormMngtBean item = new ContractFormMngtBean();
                    item.setCode(c.getString(0));
                    item.setName(c.getString(1));
                    result.add(item);
                } while (c.moveToNext());
            }
		} catch (Exception ignored) {
        } finally {
            if (c != null) {
                c.close();
            }
        }

        return result;
    }

    public ArrayList<Spin> getNationaly() {

		ArrayList<Spin> result = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append(" select par_type ,par_value from ap_param where par_name = 'NATIONALY_FIX'");

        Cursor c = null;
        try {
            c = database.rawQuery(sql.toString(), new String[]{});
            if (c == null) {
                return result;
            }
            if (c.moveToFirst()) {
                do {
                    Spin item = new Spin();
                    // ma
                    item.setId(c.getString(1));
                    // ten
                    item.setValue(c.getString(0));
                    result.add(item);
                } while (c.moveToNext());
            }
		} catch (Exception ignored) {
        } finally {
            if (c != null) {
                c.close();
            }
        }

        return result;
    }

    public ArrayList<DoiTuongObj> getListDeptObjectBCCS(String deptCode, String deptName) {

		ArrayList<DoiTuongObj> result = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append(" select id,code,name from department ");

        if (!CommonActivity.isNullOrEmpty(deptCode) && !CommonActivity.isNullOrEmpty(deptName)) {
            sql.append(" where ");
            sql.append(" lower(code) like " + "?");
            sql.append(" and lower(name) like " + "?");
        } else {

            if (!CommonActivity.isNullOrEmpty(deptCode)) {
                sql.append(" where ");
                sql.append(" lower(code) like " + "?");
            }
            if (!CommonActivity.isNullOrEmpty(deptName)) {
                sql.append(" where ");
                sql.append(" lower(name) like " + "?");
            }

        }

        Cursor c = null;
        try {
            if (!CommonActivity.isNullOrEmpty(deptCode) && !CommonActivity.isNullOrEmpty(deptName)) {
                c = database.rawQuery(sql.toString(),
                        new String[]{"%" + deptCode.toLowerCase() + "%", "%" + deptName + "%"});
            } else {
                if (!CommonActivity.isNullOrEmpty(deptCode)) {
                    c = database.rawQuery(sql.toString(), new String[]{"%" + deptCode.toLowerCase() + "%"});
                }
                if (!CommonActivity.isNullOrEmpty(deptName)) {
                    c = database.rawQuery(sql.toString(), new String[]{"%" + deptName.toLowerCase() + "%"});
                }
            }

            if (c == null) {
                return result;
            }
            if (c.moveToFirst()) {
                do {
                    DoiTuongObj item = new DoiTuongObj();
                    item.setId(c.getInt(0));
                    item.setCode(c.getString(1));
                    item.setName(c.getString(2));
					item.setCodeName(item.getCode() + "-" +item.getName());
                    result.add(item);
                } while (c.moveToNext());
            }
		} catch (Exception ignored) {
        } finally {
            if (c != null) {
                c.close();
            }
        }

        return result;
    }

    public ArrayList<DoiTuongObj> getListDeptObjectBCCSFromCode(String objectCode,String deptCode, String deptName) {

        ArrayList<DoiTuongObj> result = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT   d.id, d.code,d.name ");
        sql.append("  FROM   object_spec a,\n" +
                "         object_group b,\n" +
                "         department_group c,\n" +
                "         department d ");
        if (!CommonActivity.isNullOrEmpty(deptCode) && !CommonActivity.isNullOrEmpty(deptName)) {
            sql.append(" where ");
            sql.append(" lower(d.code) like " + "?");
            sql.append(" and lower(d.name) like " + "?");
        } else {
            if (!CommonActivity.isNullOrEmpty(deptCode)) {
                sql.append(" where ");
                sql.append(" lower(d.code) like " + "?");
            }
            if (!CommonActivity.isNullOrEmpty(deptName)) {
                sql.append(" where ");
                sql.append(" lower(d.name) like " + "?");
            }
        }
        sql.append("     AND a.GROUP_ID = b.id\n" +
                "        AND b.dep_group_id = c.id\n" +
                "        AND d.group_dep_id = c.id\n" +
                "        AND a.status = 1\n" +
                "        AND b.status = 1\n" +
                "        AND c.status = 1\n" +
                "        AND d.status = 1");
        sql.append(" AND a.code = " + "?");

        Cursor c = null;
        try {
            if (!CommonActivity.isNullOrEmpty(deptCode) && !CommonActivity.isNullOrEmpty(deptName)) {
                c = database.rawQuery(sql.toString(),
                        new String[]{"%" + deptCode.toLowerCase() + "%", "%" + deptName + "%" , objectCode});
            } else {
                if (!CommonActivity.isNullOrEmpty(deptCode)) {
                    c = database.rawQuery(sql.toString(), new String[]{"%" + deptCode.toLowerCase() + "%",objectCode});
                }
                if (!CommonActivity.isNullOrEmpty(deptName)) {
                    c = database.rawQuery(sql.toString(), new String[]{"%" + deptName.toLowerCase() + "%",objectCode});
                }
            }

            if (c == null) {
                return result;
            }
            if (c.moveToFirst()) {
                do {
                    DoiTuongObj item = new DoiTuongObj();
                    item.setId(c.getInt(0));
                    item.setCode(c.getString(1));
                    item.setName(c.getString(2));
                    item.setCodeName(item.getCode() + "-" +item.getName());
                    result.add(item);
                } while (c.moveToNext());
            }
        } catch (Exception ignored) {
        } finally {
            if (c != null) {
                c.close();
            }
        }

        return result;
    }

    // ham lay thong tin max so luong dich vu
    public int getMaxQuantityService(int telecomserviceId) {
        int result = 0;
        Cursor c = null;
        StringBuilder sql = new StringBuilder();

        sql.append("select  par_value from ap_param where par_name = 'MAX_SUB_SURVEY_SERVICE_FIX' and par_type = ? and status = 1");
        try {

            c = database.rawQuery(sql.toString(), new String[] {telecomserviceId + ""});
            if(c == null){
                return 0;
            }
            if (c.moveToFirst()) {
                do {
                    result = Integer.parseInt(c.getString(0));
                } while (c.moveToNext());
            }
        }catch (Exception e) {
        }finally {
            if (c != null) {
                c.close();
            }
        }

        return result;
    }

    public String getLstDisableService(int telecomserviceId){
        String  result = "";
        Cursor c = null;
        StringBuilder sql = new StringBuilder();

        sql.append("select  par_value from ap_param where par_name = 'DISABLE_GROUP_SERVICE_FIX' and par_type = ? and status = 1");
        try {

            c = database.rawQuery(sql.toString(), new String[] {telecomserviceId + ""});
            if(c == null){
                return "";
            }
            if (c.moveToFirst()) {
                do {
                    result = c.getString(0);
                } while (c.moveToNext());
            }
        }catch (Exception e) {
        }finally {
            if (c != null) {
                c.close();
            }
        }

        return result;
    }

}
