package com.viettel.bss.viettelpos.v4.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Process;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.helper.DatabaseHelper;
import com.viettel.bss.viettelpos.v4.object.MenuAction;

/**
 * Created by Toancx on 2/13/2017.
 */

public class DatabaseService  extends IntentService {
    private DatabaseHelper dbHelper;

    private MenuAction menuAction;

    public DatabaseService() {
        super("com.viettel.bss.viettelpos.v4.service.DatabaseService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            if(intent != null) {
                menuAction = (MenuAction) intent.getSerializableExtra("menuAction");

                Log.d("DatabaseHelper", "DatabaseService onHandleIntent process id: " + Process.myPid() + " thread id: " + Process.myTid());
                dbHelper = new DatabaseHelper(getApplicationContext());
                dbHelper.insertActionToDB(menuAction);
            } else {
                Log.d("DatabaseHelper", "DatabaseService onHandleIntent intent NULL process id: " + Process.myPid() + " thread id: " + Process.myTid());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
