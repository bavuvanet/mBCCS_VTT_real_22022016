package com.viettel.bss.viettelpos.v4.commons;

import java.io.File;

import android.content.ContextWrapper;



public class DatabaseUtils {
	
	public static boolean doesDatabaseExist(ContextWrapper context, String dbName) {
		try {
			File dbFile = context.getDatabasePath(dbName);
		    return dbFile.exists();	
		} catch (Exception e) {
			return false;
		}
	    
	}
}
