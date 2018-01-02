package com.viettel.bss.viettelpos.v4.commons;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author Chinh Nguyen Quang Database manager
 */
public class DBOpenHelper extends SQLiteOpenHelper implements Define {

    private SQLiteDatabase database;
    private final Context context;
    private Cursor mCursor = null;

    public DBOpenHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
        openDataBase();
    }

    private void createDataBase() {
        boolean dbExist = checkDataBase();
        if (!dbExist) {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                Log.e(this.getClass().toString(), "Copying error");
                throw new Error("Error copying database!");
            }
        } else {
            Log.i(this.getClass().toString(), "Database already exists");
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDb = null;
        Log.i("TAG", "Check DataBase");
        try {
            String path = PATH_DATABASE + DB_NAME;
            checkDb = SQLiteDatabase.openDatabase(path, null,
                    SQLiteDatabase.OPEN_READONLY);
        } catch (SQLException e) {
            Log.e(this.getClass().toString(), "Error while checking db");
        }
        if (checkDb != null) {
            checkDb.close();
        }
        return checkDb != null;
    }

    private void copyDataBase() throws IOException {
        Log.i("TAG", "-------------Copy DataBase---------------------");
        InputStream externalDbStream = context.getAssets().open(DB_NAME);
        String outFileName = PATH_DATABASE + DB_NAME;
        OutputStream localDbStream = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = externalDbStream.read(buffer)) > 0) {
            localDbStream.write(buffer, 0, bytesRead);
        }
        localDbStream.close();
        externalDbStream.close();
    }

    private void openDataBase() throws SQLException {
        String path = PATH_DATABASE + DB_NAME;
        if (database == null) {
            // createDataBase();
            if (checkDataBase()) {
                database = SQLiteDatabase.openDatabase(path, null,
                        SQLiteDatabase.OPEN_READWRITE);
            } else {
                createDataBase();
            }
        }
    }

    @Override
    public synchronized void close() {
        if (database != null) {
            database.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
