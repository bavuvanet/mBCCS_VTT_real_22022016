package com.viettel.bss.viettelpos.v4.helper;

import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.auto.AutoConst;
import com.viettel.bss.viettelpos.v4.commons.auto.dto.SuggestionObj;
import com.viettel.bss.viettelpos.v4.object.MenuAction;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author thientv7
 */
public class DatabaseHelper extends SQLiteOpenHelper implements AutoConst {

    /**
     * version database current : 1
     */
    static int VERSION = 1;

    private Context mContext;

    private String TABLE_NAME = "suggest_menu_action";
    private String ID = "id";
    private String USER_ID = "user_id";
    private String URL_MENU = "url_menu";
    private String ID_MENU = "id_menu";
    private String NAME_MENU = "name_menu";
    private String USED_COUNT = "used_count";
    private String TIME = "time";

    private String ID_ICON = "id_icon";
    // Template table
    private String TABLE_TEMPLATE = "tbl_template";
    private String TMP_ID = "tmp_id";
    private String TMP_LOGIN_USER = "tmp_login_user";
    private String TMP_KEY = "tmp_key"; //  function
    private String TMP_DATETIME = "tmp_datetime";
    private String TMP_EVERYDAY = "everyday";// 1 or 0 ; everyday is 0
    private String TMP_VALUE = "tmp_value";
    // Suggestion table
    private String TABLE_SUGGESTION = "tbl_suggestion";
    private String SGG_ID = "sgg_id";
    private String SGG_KEY = "sgg_key"; //  function
    private String SGG_LOGIN_USER = "sgg_login_user";
    private String SGG_SELECTED_COUNT = "sgg_selected_count"; // selected count
    private String SGG_VALUE = "sgg_value"; // text
    private String SGG_DATETIME = "sgg_datetime";

    public DatabaseHelper(Context context) {
        super(context, Constant.DB_NAME, null, VERSION);
        mContext = context;
    }

    public DatabaseHelper(Context context, String name, CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SG_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"

                + USER_ID + " TEXT,"
                + URL_MENU + " TEXT,"
                + ID_MENU + " TEXT,"

                + NAME_MENU + " TEXT,"
                + USED_COUNT + " TEXT,"
                + ID_ICON + " INTEGER,"
                + TIME + " TEXT)";

        //added by diepdc
        String CREATE_TABLE_TEMPLATE = "CREATE TABLE " + TABLE_TEMPLATE + "("
                + TMP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TMP_LOGIN_USER + " TEXT,"
                + TMP_KEY + " TEXT,"

                + TMP_VALUE + " TEXT,"
                + TMP_EVERYDAY + " INTEGER," // 1 or 0
                + TMP_DATETIME + " TEXT)";

        String CREATE_TABLE_SUGGESTION = "CREATE TABLE " + TABLE_SUGGESTION + "("
                + SGG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + SGG_LOGIN_USER + " TEXT,"
                + SGG_KEY + " TEXT,"

                + SGG_VALUE + " TEXT,"
                + SGG_SELECTED_COUNT + " INTEGER," // selected count
                + SGG_DATETIME + " TEXT)";

        db.execSQL(CREATE_SG_TABLE);
        db.execSQL(CREATE_TABLE_TEMPLATE);
        db.execSQL(CREATE_TABLE_SUGGESTION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEMPLATE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUGGESTION);
        onCreate(db);
    }

    // added by diepdc
    public void insertSuggestion(SuggestionObj suggestionObj) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        db.beginTransaction();
        try {
            values.put(SGG_LOGIN_USER, suggestionObj.sgg_login_user);
            values.put(SGG_KEY, suggestionObj.sgg_key);
            values.put(SGG_DATETIME, suggestionObj.sgg_datetime);
            values.put(SGG_SELECTED_COUNT, suggestionObj.sgg_selected_count);
            values.put(SGG_VALUE, suggestionObj.sgg_value);

            db.insert(TABLE_SUGGESTION, null, values);

            db.setTransactionSuccessful();
        } catch (Exception e) {
            //Error in between database transaction
            e.printStackTrace();
        }
        db.endTransaction();
        db.close();
    }

    public void deleteAllSuggestion() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("DELETE FROM " + TABLE_SUGGESTION);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteSuggestion(String sggId) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("DELETE FROM " + TABLE_SUGGESTION + " WHERE " + SGG_ID + "='" + sggId + "'");
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long updateSuggestion(String sggId, int selectedCount, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(SGG_SELECTED_COUNT, selectedCount);
            values.put(SGG_DATETIME, time);
            int update = db.update(TABLE_SUGGESTION, values, SGG_ID + "=" + sggId, null);
            return update;
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
        return -1;
    }

    public ArrayList<SuggestionObj> loadAllSuggestionByKey(String loginUser, String key) {
        ArrayList<SuggestionObj> arr = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String selectQuery = "SELECT  * FROM " + TABLE_SUGGESTION + " WHERE " + SGG_KEY + " = '" + key + "' AND " + SGG_LOGIN_USER + " = '" + loginUser + "'";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() == false) {
                    SuggestionObj suggestionObj = new SuggestionObj();
                    String sgg_id = cursor.getString(cursor.getColumnIndex(SGG_ID));
                    String sgg_loginUser = cursor.getString(cursor.getColumnIndex(SGG_LOGIN_USER));
                    String sgg_key = cursor.getString(cursor.getColumnIndex(SGG_KEY));
                    String sgg_value = cursor.getString(cursor.getColumnIndex(SGG_VALUE));
                    int sgg_count = cursor.getInt(cursor.getColumnIndex(SGG_SELECTED_COUNT));
                    String sgg_time = cursor.getString(cursor.getColumnIndex(SGG_DATETIME));

                    suggestionObj.sgg_id = sgg_id;
                    suggestionObj.sgg_key = sgg_key;
                    suggestionObj.sgg_login_user = sgg_loginUser;
                    suggestionObj.sgg_datetime = sgg_time;
                    suggestionObj.sgg_value = sgg_value;
                    suggestionObj.sgg_selected_count = sgg_count;

                    arr.add(suggestionObj);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
        return arr;
    }

    // template
    public ArrayList<Map<String, String>> loadAllTemplateByKey(String loginUser, String key, int type) {
        ArrayList<Map<String, String>> arr = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String selectQuery = "SELECT  * FROM " + TABLE_TEMPLATE + " WHERE " + TMP_KEY + " = '" + key + "' AND " + TMP_EVERYDAY + " = '" + type + "' AND " + TMP_LOGIN_USER + " = '" + loginUser + "' order by " + TMP_ID + " desc";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() == false) {
                    Map<String, String> map = new HashMap<>();
                    String tmp_id = cursor.getString(cursor.getColumnIndex(TMP_ID));
                    String tmp_loginUser = cursor.getString(cursor.getColumnIndex(TMP_LOGIN_USER));
                    String tmp_key = cursor.getString(cursor.getColumnIndex(TMP_KEY));
                    String tmp_value = cursor.getString(cursor.getColumnIndex(TMP_VALUE));
                    int tmp_everyday = cursor.getInt(cursor.getColumnIndex(TMP_EVERYDAY));
                    String tmp_time = cursor.getString(cursor.getColumnIndex(TMP_DATETIME));

                    map.put(TEMPLATE_ID, tmp_id);
                    map.put(LOGIN_USER, tmp_loginUser);
                    map.put(MAP_KEY, tmp_key);
                    map.put(TYPE, String.valueOf(tmp_everyday));
                    map.put(TIME_STAMP, tmp_time);

                    String[] arrValue = tmp_value.split(AUTO_SPLIT_KEY);

                    for (int i = 0, n = arrValue.length; i < n; i++) {
                        String[] arrItem = arrValue[i].split(AUTO_CONTENT_KEY);
                        if (arrItem.length > 0) {
                            map.put(arrItem[0], arrItem[1]); //ex: service | mobile
                        }
                    }
                    arr.add(map);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
        return arr;
    }

    public void deleteTemplate(String templateId) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("DELETE FROM " + TABLE_TEMPLATE + " WHERE " + TMP_ID + "='" + templateId + "'");
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int insertTemplate(Map<String, String> mapTemplate) {
        if (mapTemplate == null) return -1;
        String key = mapTemplate.get(MAP_KEY);
        if (!TextUtils.isEmpty(key)) {
            if (key.contains(PREF_MOBILE_NEW_SCREEN)) { // trả trước, trả sau
                insertTemplateMobileNewScreen(mapTemplate);
            } else if (key.contains(PREF_DAU_NOI_CO_DINH_MOI_SCREEN)) {
                insertTemplateMobileNewScreen(mapTemplate);
            }
        }
        return -1;
    }

    private void insertTemplateMobileNewScreen(Map<String, String> mapTemplate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        db.beginTransaction();
        try {
            values.put(TMP_LOGIN_USER, Session.userName);
            values.put(TMP_EVERYDAY, NORMAL); // 0 is everyday; 1 is normal
            values.put(TMP_KEY, mapTemplate.get(MAP_KEY));
            values.put(TMP_DATETIME, mapTemplate.get(TIME_STAMP));

            StringBuilder sb = new StringBuilder();
            if (!TextUtils.isEmpty(mapTemplate.get(SERVICE))) {
                sb.append(SERVICE + AUTO_CONTENT_KEY + mapTemplate.get(SERVICE)).append(AUTO_SPLIT_KEY);
            }
            if (!TextUtils.isEmpty(mapTemplate.get(PACKAGE))) {
                sb.append(PACKAGE + AUTO_CONTENT_KEY + mapTemplate.get(PACKAGE)).append(AUTO_SPLIT_KEY);
            }
            if (!TextUtils.isEmpty(mapTemplate.get(LOAITB))) {
                sb.append(LOAITB + AUTO_CONTENT_KEY + mapTemplate.get(LOAITB)).append(AUTO_SPLIT_KEY);
            }
            if (!TextUtils.isEmpty(mapTemplate.get(HTHM))) {
                sb.append(HTHM + AUTO_CONTENT_KEY + mapTemplate.get(HTHM)).append(AUTO_SPLIT_KEY);
            }
            if (!TextUtils.isEmpty(mapTemplate.get(KM))) {
                sb.append(KM + AUTO_CONTENT_KEY + mapTemplate.get(KM)).append(AUTO_SPLIT_KEY);
            }
            if (!TextUtils.isEmpty(mapTemplate.get(CDT))) {
                sb.append(CDT + AUTO_CONTENT_KEY + mapTemplate.get(CDT)).append(AUTO_SPLIT_KEY);
            }
            values.put(TMP_VALUE, sb.toString());
            db.insert(TABLE_TEMPLATE, null, values);

            db.setTransactionSuccessful();
        } catch (Exception e) {
            //Error in between database transaction
            e.printStackTrace();
        }
        db.endTransaction();
        db.close();
    }

    public long updateTemplate(String user, String key, String templateId, int everyday) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(TMP_EVERYDAY, everyday);
            int update = db.update(TABLE_TEMPLATE, values, TMP_ID + "=" + templateId, null);
            return update;
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
        return -1;
    }

    // ended by diepdc

    public void insertActionToDB(MenuAction menuAction) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_ID, menuAction.getUserId());
        values.put(URL_MENU, menuAction.getUrlMenu());
        values.put(ID_MENU, menuAction.getIdMenu());

        values.put(NAME_MENU, menuAction.getNameMenu());
//		values.put(USED_COUNT, menuAction.getUsedCount());
        values.put(TIME, menuAction.getTime());

        values.put(ID_ICON, menuAction.getIdIcon());


        if (isMenuIdExist(TABLE_NAME, menuAction.getIdMenu())) {

            long count = getValueCountMenu(TABLE_NAME, menuAction.getIdMenu());

            Log.i("DatabaseHelper", "count: " + count);


            values.put(USED_COUNT, String.valueOf(count + 1));

            long update = db.update(TABLE_NAME, values, ID_MENU + " =  ? ",
                    new String[]{menuAction.getIdMenu()});
            Log.i("DatabaseHelper", ID_MENU + " = " + menuAction.getIdMenu());
            db.close();
            Log.i("DatabaseHelper", "insertActionToDB update Action with Row: " + update);

        } else {
            values.put(USED_COUNT, "1");
            long insert = db.insert(TABLE_NAME, null, values);
            db.close();
            Log.i("DatabaseHelper", "insertActionToDB inserted Action with Row: " + insert);

        }

        ////////////////
        /*long insert = db.insert(TABLE_NAME, null, values);
        db.close();
		Log.e("insertActionToDB inserted Action with Row: " + insert);// + " " + values.toString());
*/
    }


    /**
     * @param table_name
     * @param id_menu
     * @return
     */
    public boolean isMenuIdExist(String table_name, String id_menu) {
        SQLiteDatabase db = this.getReadableDatabase();
        long line = DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM " + table_name + " WHERE id_menu = ?",
                new String[]{id_menu});
        Log.i("DatabaseHelper", "SELECT COUNT(*) FROM " + table_name + " WHERE id_menu = " + id_menu);
        Log.i("DatabaseHelper", "isMenuIdExist: " + line);
        return line > 0;
    }


    public long getValueCountMenu(String table_name, String id_menu) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  used_count FROM " + table_name + " WHERE "
                + ID_MENU + " = '" + id_menu + "'";

        Log.i("DatabaseHelper", selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null)
            c.moveToFirst();

        return Long.valueOf(c.getString(c.getColumnIndex(USED_COUNT)));
    }

    public ArrayList<MenuAction> getAllMenuActions() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MenuAction> menuActions = new ArrayList<MenuAction>();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                MenuAction menuAction = new MenuAction();

                String userId = cursor.getString(cursor.getColumnIndex(USER_ID));
                String urlMenu = cursor.getString(cursor.getColumnIndex(URL_MENU));
                String idMenu = cursor.getString(cursor.getColumnIndex(ID_MENU));
                String nameMenu = cursor.getString(cursor.getColumnIndex(NAME_MENU));
                String usedCount = cursor.getString(cursor.getColumnIndex(USED_COUNT));
                String time = cursor.getString(cursor.getColumnIndex(TIME));
                int idIcon = cursor.getInt(cursor.getColumnIndex(ID_ICON));

                menuAction.setUserId(userId);
                menuAction.setUrlMenu(urlMenu);
                menuAction.setIdMenu(idMenu);
                menuAction.setNameMenu(nameMenu);
                menuAction.setUsedCount(usedCount);
                menuAction.setTime(time);

                menuAction.setIdIcon(idIcon);

                menuActions.add(menuAction);
                cursor.moveToNext();
            }
        }

        return menuActions;
    }

    public ArrayList<MenuAction> getValuesFrequencyUsed(int count) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MenuAction> menuActions = new ArrayList<MenuAction>();
        try {
            Cursor cursor = db.query(TABLE_NAME, new String[]{USER_ID, URL_MENU, ID_MENU, NAME_MENU, USED_COUNT, TIME, ID_ICON}, null, null, null, null, USED_COUNT + " DESC", "" + count);

            try {
                if (cursor.moveToFirst()) {
                    while (cursor.isAfterLast() == false) {
                        MenuAction menuAction = new MenuAction();

                        String userId = cursor.getString(cursor.getColumnIndex(USER_ID));
                        String urlMenu = cursor.getString(cursor.getColumnIndex(URL_MENU));
                        String idMenu = cursor.getString(cursor.getColumnIndex(ID_MENU));
                        String nameMenu = cursor.getString(cursor.getColumnIndex(NAME_MENU));
                        String usedCount = cursor.getString(cursor.getColumnIndex(USED_COUNT));
                        String time = cursor.getString(cursor.getColumnIndex(TIME));

                        int idIcon = cursor.getInt(cursor.getColumnIndex(ID_ICON));

                        menuAction.setUserId(userId);
                        menuAction.setUrlMenu(urlMenu);
                        menuAction.setIdMenu(idMenu);
                        menuAction.setNameMenu(nameMenu);
                        menuAction.setUsedCount(usedCount);
                        menuAction.setTime(time);

                        menuAction.setIdIcon(idIcon);

                        menuActions.add(menuAction);
                        cursor.moveToNext();
                    }
                }
            } finally {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
            }
        } finally {
            db.close();
        }


        return menuActions;
    }


    public ArrayList<MenuAction> getValuesFrequencyUsedWithUserId(String userId, int count) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MenuAction> menuActions = new ArrayList<MenuAction>();
        try {
            Cursor cursor = db.query(TABLE_NAME, new String[]{USER_ID, URL_MENU, ID_MENU, NAME_MENU, USED_COUNT, TIME, ID_ICON}, USER_ID + "=?", new String[]{userId}, null, null, USED_COUNT + " DESC", "" + count);

            try {
                if (cursor.moveToFirst()) {
                    while (cursor.isAfterLast() == false) {
                        MenuAction menuAction = new MenuAction();

                        String userIdDb = cursor.getString(cursor.getColumnIndex(USER_ID));
                        String urlMenu = cursor.getString(cursor.getColumnIndex(URL_MENU));
                        String idMenu = cursor.getString(cursor.getColumnIndex(ID_MENU));
                        String nameMenu = cursor.getString(cursor.getColumnIndex(NAME_MENU));
                        String usedCount = cursor.getString(cursor.getColumnIndex(USED_COUNT));
                        String time = cursor.getString(cursor.getColumnIndex(TIME));

                        int idIcon = cursor.getInt(cursor.getColumnIndex(ID_ICON));

                        menuAction.setUserId(userIdDb);
                        menuAction.setUrlMenu(urlMenu);
                        menuAction.setIdMenu(idMenu);
                        menuAction.setNameMenu(nameMenu);
                        menuAction.setUsedCount(usedCount);
                        menuAction.setTime(time);

                        menuAction.setIdIcon(idIcon);

                        menuActions.add(menuAction);
                        cursor.moveToNext();
                    }
                }
            } finally {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
            }
        } finally {
            db.close();
        }


        return menuActions;
    }
}