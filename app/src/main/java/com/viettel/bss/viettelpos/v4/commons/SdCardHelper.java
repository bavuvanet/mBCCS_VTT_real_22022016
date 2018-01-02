package com.viettel.bss.viettelpos.v4.commons;

import java.io.File;

import android.os.Environment;
import android.os.StatFs;

public class SdCardHelper {
	public static String ERROR = "Error";
	private static boolean externalMemoryAvailable() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    public static double getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
       
        return formatSize(availableBlocks * blockSize);
    }

    public static double getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
       
       return formatSize(totalBlocks * blockSize);
    }

    public static double getAvailableExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return formatSize(availableBlocks * blockSize);
        } else {
            return 0;
        }
    }

    public static double getTotalExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return formatSize(totalBlocks * blockSize);
        } else {
            return 0;
        }
    }

    private static double formatSize(long size) {
       
        if (size >= 1024) {
         
            size /= 1024;
            if (size >= 1024) {
               
                size /= 1024;
            }
        }
        return size;
    } 
}
