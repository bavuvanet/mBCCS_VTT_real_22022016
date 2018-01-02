package com.viettel.bss.viettelpos.v4.commons;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.customview.obj.FileObj;
import com.viettel.bss.viettelpos.v4.dal.DatabaseManager;
import com.viettel.bss.viettelpos.v4.utils.ImageUtils;
import com.viettel.savelog.SaveLog;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileUtils {
    public static void writeStringAsFile(final String fileContents, File fileDest) {
        FileWriter out = null;
        try {
            out = new FileWriter(fileDest);
            out.write(fileContents);
        } catch (Exception e) {
            Log.e(FileUtils.class.getName(), "exception when writting file", e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    Log.d(Constant.TAG, "error", e);
                }
            }
        }
    }

    /**
     * inserDatabase
     */

    public static void insertFileBackUpToDataBase(
            ArrayList<FileObj> listFileObject, Context context) {
        try {
            DatabaseManager databaseManager = new DatabaseManager(context);
            for (FileObj fileObj2 : listFileObject) {
                databaseManager.insertImageBackUpToDatabase(fileObj2,
                        fileObj2.getPath());
            }
        } catch (Exception e) {
            Log.e("Log", "loi insert database file image backup ", e);
        }
    }

    public static void insertFileBackUpToDataBase(FileObj fileObj2,
                                                  Context context) {
        try {
            DatabaseManager databaseManager = new DatabaseManager(context);
            databaseManager.insertImageBackUpToDatabase(fileObj2,
                    fileObj2.getPath());
        } catch (Exception e) {
            Log.e("Log", "loi insert database file image backup ", e);
        }
    }

    /**
     * Luu file khi dau noi de sau nay upload
     *
     * @param hashmapFileObj
     * @param context
     */
    public static void backUpImageFile(
            HashMap<String, ArrayList<FileObj>> hashmapFileObj, Context context) {
        File folder = new File(Constant.MBCCS_TEMP_FOLDER_IMG_PROFILE);
        if (!folder.exists()) {
            folder.mkdir();
        }

        DatabaseManager databaseManager = null;
        int count = 0;
        for (java.util.Map.Entry<String, ArrayList<FileObj>> entry : hashmapFileObj
                .entrySet()) {
            Log.d("Log", "backUpImageFile count in hashmapFileObj: "
                    + hashmapFileObj.size());
            ArrayList<FileObj> listFileObjs = entry.getValue();
            Log.d("Log", "backUpImageFile count file in entry listFileObjs : "
                    + listFileObjs.size());
            // listFileObjs object la cua thang nao dua vao spinner code
            if (databaseManager == null) {
                databaseManager = new DatabaseManager(context);
            }
            for (FileObj fileObj : listFileObjs) {
                File fsrc = new File(fileObj.getPath());
                File fdst = new File(folder.getPath() + File.separator
                        + new Date().getTime() + "_" + fileObj.getName());
                Log.d("Log", "backUpImageFile path fdst : " + fdst.getPath());
                boolean isCoppy = copy(fsrc, fdst);
                if (isCoppy) {
                    // save database info file with contract
                    databaseManager.insertImageBackUpToDatabase(fileObj,
                            fdst.getPath());
                    count++;

                }
            }
            Log.d("Log", "backUpImageFile count insert database " + count);
        }
    }

    public static boolean copy(File src, File dst) {
        InputStream in;
        OutputStream out;
        boolean result = false;
        try {
            in = new FileInputStream(src);
            out = new FileOutputStream(dst);

            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            result = true;
        } catch (Exception e) {
            Log.d(Constant.TAG, "error", e);
        }
        return result;
    }

    private static Bitmap decodeFile(String path) {
        try {
            File f = new File(path);
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE = Constant.COUNT_SALE_TRANS_PER_ONCE;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;

            while (o.outWidth / scale / 2 >= REQUIRED_SIZE
                    && o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
            Log.d(Constant.TAG, "error", e);
        }
        return null;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeBitmapFromFile(String filePath, int reqWidth,
                                              int reqHeight) {

        return decodeFile(filePath);

        // // First decode with inJustDecodeBounds=true to check dimensions
        // final BitmapFactory.Options options = new BitmapFactory.Options();
        // options.inJustDecodeBounds = true;
        // BitmapFactory.decodeFile(filePath, options);
        //
        // // Calculate inSampleSize
        // options.inSampleSize = calculateInSampleSize(options, reqWidth,
        // reqHeight);
        //
        // // Decode bitmap with inSampleSize set
        // options.inJustDecodeBounds = false;
        // return BitmapFactory.decodeFile(filePath, options);
    }

    private static Bitmap decodeBitmapFromByte(byte[] data, int reqWidth,
                                               int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(data, 0, data.length, options);
    }

    public static byte[] fileToBytes(File file) {
        byte[] bytes = null;

        InputStream is = null;
        try {
            is = new FileInputStream(file);

            long length = file.length();
            if (length > Integer.MAX_VALUE) {
                Log.d(Constant.TAG, "File is too large " + length);
            } else {
                Log.d(Constant.TAG, "File length: " + length);
            }

            bytes = new byte[(int) length];

            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length
                    && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }

            if (offset < bytes.length) {
                bytes = null;
                Log.d(Constant.TAG,
                        "Could not completely read file " + file.getName());
            }
        } catch (Exception e) {
            Log.d(Constant.TAG, "error", e);
            bytes = null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    Log.d(Constant.TAG, "error", e);
                }
            }
        }
        return bytes;
    }

    public static File zip(List<File> files, String zipFilePath) {
        File zipfile = new File(zipFilePath);
        FileOutputStream fos = null;
        ZipOutputStream zout = null;
        try {
            fos = new FileOutputStream(zipfile);
            zout = new ZipOutputStream(fos);
            byte data[] = new byte[1024];
            List<String> lstNameAdded = new ArrayList<>();

            int i = 0;
            for (File file : files) {
                String name = file.getName();
                if (lstNameAdded.contains(name)) {
                    name = i + "_" + name;
                }
                Log.v("Compress", "Zipping: " + file.getPath());
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedInputStream origin = new BufferedInputStream(
                        fileInputStream, 1024);

                ZipEntry entry = new ZipEntry(name);
                zout.putNextEntry(entry);

                int count;
                while ((count = origin.read(data, 0, 1024)) != -1) {
                    zout.write(data, 0, count);
                }
                origin.close();
                i++;
                lstNameAdded.add(file.getName());
            }


            return zipfile;

        } catch (Exception e) {
            Log.d(Constant.TAG, "error", e);
        } finally {
            try {
                if(zout != null) {
                    zout.close();
                }
                if(fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                Log.d(Constant.TAG, "error", e);
            }
        }
        return null;
    }

    private static File zipFileImage(List<FileObj> _files, String zipFilePath) {
        File zipfile = new File(zipFilePath);
        FileOutputStream fos = null;
        ZipOutputStream zout = null;
        try {
            fos = new FileOutputStream(zipfile);
            zout = new ZipOutputStream(fos);
            for (FileObj obj : _files) {
                ZipEntry entry = new ZipEntry(obj.getName());
                zout.putNextEntry(entry);

                long length = obj.getFile().length();
                int percent = length < Constant.MAX_SIZE_IMG ? 100
                        : (int) (Constant.MAX_SIZE_IMG * 100 / length);
                // Bitmap bitmap =
                // BitmapFactory.decodeFile(obj.getFile().getPath());
                Bitmap bitmap;
                if (obj.getFile().length() < Constant.MAX_SIZE_IMG) {
                    bitmap = BitmapFactory.decodeFile(obj.getFile().getPath());
                } else {
                    bitmap = FileUtils.decodeBitmapFromFile(obj.getFile()
                            .getPath(), 1000, 1000);
                }
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, percent, baos);
                byte[] buffer = baos.toByteArray();
                Log.v("Compress", "Adding length: " + length + " percent: "
                        + percent + " buffer: " + buffer.length + " "
                        + obj.getFile().getPath());
                zout.write(buffer);
                baos.close();
            }

            return zipfile;

        } catch (Exception e) {
            Log.d(Constant.TAG, "error", e);
        } finally {
            try {
                if(zout != null) {
                    zout.close();
                }
                if(fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                Log.d(Constant.TAG, "error", e);
            }
        }
        return null;
    }

    public static String zipFileToBase64(Activity activity,
                                         HashMap<String, ArrayList<FileObj>> hashmapFileObj,
                                         String fileZipPath) {
        Date zipTime = new Date();
        File folder = new File(Constant.MBCCS_TEMP_FOLDER);
        if (!folder.exists()) {
            folder.mkdir();
        }

        List<File> listZip = new ArrayList<>();

        for (Entry<String, ArrayList<FileObj>> entry : hashmapFileObj
                .entrySet()) {
            ArrayList<FileObj> fileObjs = entry.getValue();

            if (fileObjs.size() > 1) {
                String spinnerCode = fileObjs.get(0).getCodeSpinner();
                String mPathFileZ = Constant.MBCCS_TEMP_FOLDER + File.separator
                        + spinnerCode + ".zip";
                File fileZip = zipFileImage(fileObjs, mPathFileZ);
                listZip.add(fileZip);
            } else if (fileObjs.size() == 1) {
                String spinnerCode = fileObjs.get(0).getCodeSpinner();
                String mPathFileZ = Constant.MBCCS_TEMP_FOLDER + File.separator
                        + spinnerCode + ".jpg";

                File fileZip = new File(mPathFileZ);

                FileUtils.copy(fileObjs.get(0).getFile(), fileZip);

                listZip.add(fileZip);
            }
        }

        File isdnZip = zip(listZip, fileZipPath);
        try {
            if (Session.KPI_REQUEST) {

                SaveLog saveLog = new SaveLog(activity, Constant.SYSTEM_NAME,
                        Session.userName, Constant.METHOD_NAME_ZIP_FILE,
                        CommonActivity.findMyLocation(activity).getX(),
                        CommonActivity.findMyLocation(activity).getY());

                Date timeEnd = new Date();

                saveLog.saveLogRequest(
                        "SUCCESS",
                        zipTime,
                        timeEnd,
                        Session.userName + "_"
                                + CommonActivity.getDeviceId(activity) + "_"
                                + System.currentTimeMillis());
            }
        } catch (Exception e) {
            Log.d(Constant.TAG, "error", e);
        }
        Date encodeTime = new Date();
        byte[] bytes = FileUtils.fileToBytes(isdnZip);
        String base64 = Base64.encodeToString(bytes, Activity.TRIM_MEMORY_BACKGROUND);

        try {
            if (Session.KPI_REQUEST) {
                SaveLog saveLog = new SaveLog(activity, Constant.SYSTEM_NAME,
                        Session.userName, Constant.METHOD_NAME_ENCODE_FILE,
                        CommonActivity.findMyLocation(activity).getX(),
                        CommonActivity.findMyLocation(activity).getY());

                saveLog.saveLogRequest(
                        "SUCCESS",
                        encodeTime,
                        new Date(),
                        Session.userName + "_"
                                + CommonActivity.getDeviceId(activity) + "_"
                                + System.currentTimeMillis());
            }
        } catch (Exception e) {
            Log.d(Constant.TAG, "error", e);
        }

        try {
            if (Session.KPI_REQUEST) {
                SaveLog saveLog = new SaveLog(activity, Constant.SYSTEM_NAME,
                        Session.userName, Constant.METHOD_NAME_ENCODE_FILE,
                        CommonActivity.findMyLocation(activity).getX(),
                        CommonActivity.findMyLocation(activity).getY());

                saveLog.saveLogRequest(
                        "SUCCESS",
                        encodeTime,
                        new Date(),
                        Session.userName + "_"
                                + CommonActivity.getDeviceId(activity) + "_"
                                + System.currentTimeMillis());
            }
        } catch (Exception e) {
            Log.d(Constant.TAG, "error", e);
        }

        return base64;
    }

    public static void copyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception e) {
            Log.d(Constant.TAG, "error", e);
        }
    }

    public static ArrayList<FileObj> getArrFileBackUp(Context context,
                                                      HashMap<String, ArrayList<FileObj>> hashmapFileObj)
            throws Exception {
        ArrayList<FileObj> arrFileBackUp = new ArrayList<>();
        FileObj fileObjBackup = null;
        try {
            File folder = new File(Constant.MBCCS_TEMP_FOLDER_IMG_PROFILE);
            if (!folder.exists()) {
                folder.mkdir();
            }
            Log.d("Log", "Folder zip file create: " + folder.getAbsolutePath());
            for (java.util.Map.Entry<String, ArrayList<FileObj>> entry : hashmapFileObj
                    .entrySet()) {
                ArrayList<FileObj> listFileObjs = entry.getValue();

                if (listFileObjs.size() > 1) {
                    String spinnerCode = "";
                    long fileLengthOrigin = 0l;
                    List<FileObj> listFileDest = new ArrayList<>();

                    for (FileObj fileObj : listFileObjs) {

                        try {
                            File fileSrc = new File(fileObj.getPath());
                            Log.d(Constant.TAG,
                                    "FileUtil getArrFileBackUp fileSrc: "
                                            + fileSrc.getPath()
                                            + " fileSrc length "
                                            + fileSrc.length()
                                            + " fileSrc.exists() "
                                            + fileSrc.exists());
                            if (fileSrc.length() > 0) {
                                // Bitmap bitmapImage =
                                // Picasso.with(context).load(fileSrc)
                                // .resize(Constant.SIZE_IMAGE_SCALE,
                                // Constant.SIZE_IMAGE_SCALE).get();

                                Bitmap bitmap = BitmapFactory
                                        .decodeFile(fileSrc.getPath());

                                Bitmap bitmapImage = CommonActivity
                                        .getResizedBitmap(bitmap,
                                                Constant.SIZE_IMAGE_SCALE);
                                File fileDes = new File(folder.getPath()
                                        + File.separator + "_"
                                        + System.currentTimeMillis()
                                        + fileObj.getCodeSpinner() + ".jpg");
                                FileOutputStream out = new FileOutputStream(fileDes);
                                bitmapImage.compress(
                                        Bitmap.CompressFormat.JPEG,
                                        Constant.DEFAULT_JPEG_QUALITY, out);
                                out.close();
                                FileObj fileObjDest = new FileObj();
                                fileObjDest.setCodeSpinner(fileObj
                                        .getCodeSpinner());
                                fileObjDest.setPath(fileDes.getPath());
                                fileObjDest.setName(fileObj.getName());
                                fileObjDest.setActionCode(fileObj
                                        .getActionCode());
                                fileObjDest.setReasonId(fileObj.getReasonId());
                                fileObjDest.setIsdn(fileObj.getIsdn());
                                fileObjDest.setActionAudit(fileObj
                                        .getActionAudit());
                                fileObjDest.setFile(fileDes);
                                fileObjDest.setRecodeName(fileObj
                                        .getRecodeName());
                                fileLengthOrigin += fileSrc.length();
                                spinnerCode = fileObj.getCodeSpinner();
                                listFileDest.add(fileObjDest);
                            } else {
                                // CommonActivity.createAlertDialog(context,
                                // fileSrc.getPath(), fileSrc.length() +
                                // "").show();
                                Log.e(Constant.TAG,
                                        "FileUtil getArrFileBackUp notfound: "
                                                + fileSrc.getPath());
                                return null;
                            }
                        } catch (Exception e) {
                            SaveLog saveLog = new SaveLog(context,
                                    Constant.SYSTEM_NAME, Session.userName,
                                    "registerMobilePre_Zipfile", CommonActivity
                                    .findMyLocation(context).getX(),
                                    CommonActivity.findMyLocation(context)
                                            .getY());
                            saveLog.saveLogRequest(fileObj.getIsdn() + " - "
                                            + fileObj.getActionCode() + " Exception "
                                            + getException(e),

                                    new Date(), new Date(), Session.userName + "_"
                                            + CommonActivity.getDeviceId(context) + "_"
                                            + System.currentTimeMillis());
                            throw e;
                        }

                    }
                    String zipFilePath = folder.getPath() + File.separator
                            + System.currentTimeMillis() + "_" + spinnerCode
                            + ".zip";
                    Log.d("Log", "zipFilePath: " + zipFilePath);

                    String recodeName = "";
                    ArrayList<File> listfileDesZip = new ArrayList<>();
                    for (FileObj fileObj : listFileDest) {
                        recodeName = fileObj.getRecodeName();
                        listfileDesZip.add(fileObj.getFile());
                    }

                    File isdnZip = FileUtils.zip(listfileDesZip, zipFilePath);
                    for (File file : listfileDesZip) {
                        file.delete();
                    }

                    fileObjBackup = new FileObj();
                    fileObjBackup.setPath(isdnZip.getPath());
                    fileObjBackup.setFile(isdnZip);
                    fileObjBackup.setCodeSpinner(spinnerCode);
                    fileObjBackup.setPageIndex(1 + "");
                    fileObjBackup.setFileLength(isdnZip.length() + "");
                    fileObjBackup.setFileLengthOrigin(fileLengthOrigin + "");
                    fileObjBackup.setRecodeName(recodeName);
                    fileObjBackup.setName(System.currentTimeMillis() + ".zip");
                    arrFileBackUp.add(fileObjBackup);
                } else if (listFileObjs.size() == 1) {
                    FileObj fileObject = listFileObjs.get(0);
                    File fileSrc = new File(fileObject.getPath());
                    if (fileSrc.exists()) {
                        Bitmap bitmap = BitmapFactory.decodeFile(fileSrc
                                .getPath());
                        Bitmap bitmapImage = CommonActivity.getResizedBitmap(
                                bitmap, Constant.SIZE_IMAGE_SCALE);

                        File fileDes = new File(folder.getPath()
                                + File.separator + File.separator
                                + System.currentTimeMillis() + "_"
                                + fileObject.getCodeSpinner() + ".jpg");
                        FileOutputStream out = new FileOutputStream(fileDes);
                        bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100,
                                out);
                        out.close();
                        fileObjBackup = new FileObj();
                        fileObjBackup.setCodeSpinner(fileObject
                                .getCodeSpinner());
                        fileObjBackup.setPath(fileDes.getPath());
                        fileObjBackup.setFile(fileDes);
                        fileObjBackup.setPageIndex(2 + "");
                        fileObjBackup.setFileLength(fileDes.length() + "");
                        fileObjBackup
                                .setFileLengthOrigin(fileSrc.length() + "");
                        fileObjBackup.setRecodeName(fileObject.getRecodeName());
                        fileObjBackup.setName(System.currentTimeMillis() + ".jpg");
                        arrFileBackUp.add(fileObjBackup);
                    } else {
                        Log.d("Log", "fileSrc: " + fileSrc.getPath()
                                + " NOT FOUND");
                    }
                }
            }
        } catch (Exception e) {
            SaveLog saveLog = new SaveLog(context, Constant.SYSTEM_NAME,
                    Session.userName, "registerMobilePre_Zipfile", CommonActivity
                    .findMyLocation(context).getX(), CommonActivity
                    .findMyLocation(context).getY());
            saveLog.saveLogRequest(
                    " Exception when zip file " + getException(e),

                    new Date(), new Date(), Session.userName + "_"
                            + CommonActivity.getDeviceId(context) + "_"
                            + System.currentTimeMillis());
            throw e;
        }
        return arrFileBackUp;
    }

    public static ArrayList<FileObj> getArrFileBackUp2(Context context,
                                                       HashMap<String, ArrayList<FileObj>> hashmapFileObj,
                                                       ArrayList<String> lstPathFileZip) throws Exception {

        ArrayList<FileObj> arrFileBackUp = new ArrayList<>();
        FileObj fileObjBackup = null;
        try {
            File folder = new File(Constant.MBCCS_TEMP_FOLDER_IMG_PROFILE);
            if (!folder.exists()) {
                folder.mkdir();
            }
            Log.d("Log", "Folder zip file create: " + folder.getAbsolutePath());
            for (java.util.Map.Entry<String, ArrayList<FileObj>> entry : hashmapFileObj.entrySet()) {
                ArrayList<FileObj> listFileObjs = entry.getValue();
                if (listFileObjs.size() > 1) {
                    String spinnerCode = "";
                    long fileLengthOrigin = 0L;
                    List<FileObj> listFileDest = new ArrayList<>();
                    FileObj fileObjFirst = listFileObjs.get(0);
                    for (FileObj fileObj : listFileObjs) {

                        try {
                            File fileSrc = new File(fileObj.getPath());
                            Log.d(Constant.TAG,
                                    "FileUtil getArrFileBackUp fileSrc: "
                                            + fileSrc.getPath()
                                            + " fileSrc length "
                                            + fileSrc.length()
                                            + " fileSrc.exists() "
                                            + fileSrc.exists());
                            if (fileSrc.length() > 0) {

                                Bitmap bitmap = BitmapFactory
                                        .decodeFile(fileSrc.getPath());

                                Bitmap bitmapImage = CommonActivity
                                        .getResizedBitmap(bitmap,
                                                Constant.SIZE_IMAGE_SCALE);

                                File fileDes = new File(folder.getPath()
                                        + File.separator + "_"
                                        + System.currentTimeMillis()
                                        + fileObj.getCodeSpinner() + ".jpg");
                                FileOutputStream out = new FileOutputStream(fileDes);
                                bitmapImage.compress(
                                        Bitmap.CompressFormat.JPEG,
                                        Constant.DEFAULT_JPEG_QUALITY, out);
                                out.close();
                                FileObj fileObjDest = new FileObj();
                                fileObjDest.setCodeSpinner(fileObj
                                        .getCodeSpinner());
                                fileObjDest.setPath(fileDes.getPath());
                                fileObjDest.setName(fileObj.getName());
                                fileObjDest.setActionCode(fileObj
                                        .getActionCode());
                                fileObjDest.setReasonId(fileObj.getReasonId());
                                fileObjDest.setIsdn(fileObj.getIsdn());
                                fileObjDest.setActionAudit(fileObj
                                        .getActionAudit());
                                fileObjDest.setFile(fileDes);
                                fileObjDest.setRecodeName(fileObj
                                        .getRecodeName());
                                fileLengthOrigin += fileSrc.length();
                                spinnerCode = fileObj.getCodeSpinner();
                                listFileDest.add(fileObjDest);
                            } else {
                                Log.e(Constant.TAG,
                                        "FileUtil getArrFileBackUp notfound: "
                                                + fileSrc.getPath());
                                return null;
                            }
                        } catch (Exception e) {
                            SaveLog saveLog = new SaveLog(context,
                                    Constant.SYSTEM_NAME, Session.userName,
                                    "connectSub_Zipfile", CommonActivity
                                    .findMyLocation(context).getX(),
                                    CommonActivity.findMyLocation(context)
                                            .getY());
                            saveLog.saveLogRequest(fileObj.getIsdn() + " - "
                                            + fileObj.getActionCode() + " Exception "
                                            + getException(e),

                                    new Date(), new Date(), Session.userName + "_"
                                            + CommonActivity.getDeviceId(context) + "_"
                                            + System.currentTimeMillis());
                            throw e;
                        }

                    }
                    // String zipFilePath = folder.getPath() + File.separator
                    // + System.currentTimeMillis() + "_" + spinnerCode
                    // + ".zip";
                    String zipFilePath = "";
                    for (int j = 0; j < lstPathFileZip.size(); j++) {
                        String zipFilePath1 = lstPathFileZip.get(j);
                        // String recordCode =
                        // zipFilePath1.split("_",2)[1].split("\\.")[0];
                        String[] tmp = zipFilePath1.split("\\/");
                        if (tmp[tmp.length - 1].contains("_" + spinnerCode
                                + ".")) {
                            zipFilePath = zipFilePath1;
                            break;
                        }
                    }
                    Log.d("Log", "zipFilePath: " + zipFilePath);

                    String recodeName = "";
                    ArrayList<File> listfileDesZip = new ArrayList<>();
                    for (FileObj fileObj : listFileDest) {
                        recodeName = fileObj.getRecodeName();
                        listfileDesZip.add(fileObj.getFile());
                    }

                    File isdnZip = FileUtils.zip(listfileDesZip, zipFilePath);
//                    for (File file : listfileDesZip) {
//                        file.delete();
//                    }

                    fileObjBackup = new FileObj();
                    fileObjBackup.setPath(isdnZip.getPath());
                    fileObjBackup.setFile(isdnZip);
                    fileObjBackup.setCodeSpinner(spinnerCode);
                    fileObjBackup.setPageIndex(1 + "");
                    fileObjBackup.setFileLength(isdnZip.length() + "");
                    fileObjBackup.setFileLengthOrigin(fileLengthOrigin + "");
                    fileObjBackup.setRecodeName(recodeName);
                    fileObjBackup.setFileName(isdnZip.getName());
                    fileObjBackup.setRecordId(listFileObjs.get(0).getRecordId());
                    if(entry.getKey().split("_").length > 1) {
                        fileObjBackup.setActions(entry.getKey().split("_")[1]);
                    }
                    //hsdt: nhan biet file zip
                    fileObjBackup.setZip(true);
                    //add listOrder vao fileObj
                    fileObjBackup.getArrOrderInfo().addAll(fileObjFirst.getArrOrderInfo());
                    arrFileBackUp.add(fileObjBackup);
                } else if (listFileObjs.size() == 1) {
                    FileObj fileObject = listFileObjs.get(0);
                    if(fileObject.isUseOldProfile()) {
                        String zipFilePath = "";
                        for (int j = 0; j < lstPathFileZip.size(); j++) {
                            String zipFilePath1 = lstPathFileZip.get(j);
                            String[] tmp = zipFilePath1.split("\\/");
                            if (tmp[tmp.length - 1].contains("_" + fileObject.getCodeSpinner())) {
                                zipFilePath = zipFilePath1;
                                break;
                            }
                        }
                        fileObjBackup = new FileObj();
                        fileObjBackup.setCodeSpinner(fileObject
                                .getCodeSpinner());
                        fileObjBackup.setPath(zipFilePath);
                        fileObjBackup.setFile(null);
                        fileObjBackup.setPageIndex(2 + "");
                        fileObjBackup.setFileLength("");
                        fileObjBackup
                                .setFileLengthOrigin("");
                        fileObjBackup.setRecodeName(fileObject.getRecodeName());
                        fileObjBackup.setRecordId(listFileObjs.get(0)
                                .getRecordId());
                        fileObjBackup.setIsUseOldProfile(true);
                        if (entry.getKey().split("_").length > 1) {
                            fileObjBackup.setActions(entry.getKey().split("_")[1]);
                        }
                        //add order vao fileObjBackup
                        fileObjBackup.getArrOrderInfo().addAll(fileObject.getArrOrderInfo());
                        arrFileBackUp.add(fileObjBackup);
                    } else {
                        File fileSrc = new File(fileObject.getPath());
                        Log.d(Constant.TAG,
                                "FileUtil getArrFileBackUp fileSrc: "
                                        + fileSrc.getPath()
                                        + " fileSrc length "
                                        + fileSrc.length()
                                        + " fileSrc.exists() "
                                        + fileSrc.exists());
                        if (fileSrc.exists()) {
                            Bitmap bitmap = BitmapFactory.decodeFile(fileSrc
                                    .getPath());
                            Bitmap bitmapImage = CommonActivity.getResizedBitmap(
                                    bitmap, Constant.SIZE_IMAGE_SCALE);

                            String zipFilePath = "";
                            for (int j = 0; j < lstPathFileZip.size(); j++) {
                                String zipFilePath1 = lstPathFileZip.get(j);
                                String[] tmp = zipFilePath1.split("\\/");
                                if (tmp[tmp.length - 1].contains("_" + fileObject.getCodeSpinner())) {
                                    zipFilePath = zipFilePath1;
                                    break;
                                }
                            }

                            File fileDes = new File(zipFilePath);
                            FileOutputStream out = new FileOutputStream(fileDes);
                            bitmapImage.compress(Bitmap.CompressFormat.JPEG,
                                    Constant.DEFAULT_JPEG_QUALITY, out);
                            out.close();

                            fileObjBackup = new FileObj();
                            fileObjBackup.setCodeSpinner(fileObject
                                    .getCodeSpinner());
                            fileObjBackup.setPath(fileDes.getPath());
                            fileObjBackup.setFile(fileDes);
                            fileObjBackup.setPageIndex(2 + "");
                            fileObjBackup.setFileLength(fileDes.length() + "");
                            fileObjBackup
                                    .setFileLengthOrigin(fileSrc.length() + "");
                            fileObjBackup.setRecodeName(fileObject.getRecodeName());
                            fileObjBackup.setRecordId(listFileObjs.get(0)
                                    .getRecordId());
                            fileObjBackup.setFileName(fileDes.getName());
                            if (entry.getKey().split("_").length > 1) {
                                fileObjBackup.setActions(entry.getKey().split("_")[1]);
                            }
                            //add order vao fileObjBackup
                            fileObjBackup.getArrOrderInfo().addAll(fileObject.getArrOrderInfo());
                            arrFileBackUp.add(fileObjBackup);
                        } else {
                            Log.d("Log", "fileSrc: " + fileSrc.getPath()
                                    + " NOT FOUND");
                        }
                    }
                }
            }
        } catch (Exception e) {
            SaveLog saveLog = new SaveLog(context, Constant.SYSTEM_NAME,
                    Session.userName, "connectSub_Zipfile", CommonActivity
                    .findMyLocation(context).getX(), CommonActivity
                    .findMyLocation(context).getY());
            saveLog.saveLogRequest(
                    " Exception when zip file " + getException(e),
                    new Date(), new Date(), Session.userName + "_"
                            + CommonActivity.getDeviceId(context) + "_"
                            + System.currentTimeMillis());
            throw e;
        }
        return arrFileBackUp;
    }

    public static ArrayList<FileObj> getArrFileImageUploadFail(Context context,
                                                               HashMap<String, ArrayList<FileObj>> hashmapFileObj) {
        ArrayList<FileObj> arrFileBackUp = new ArrayList<>();
        FileObj fileObjBackup = null;
        try {

            File folder = new File(Constant.MBCCS_TEMP_FOLDER_IMG_PROFILE);
            if (!folder.exists()) {
                folder.mkdir();
            }
            Log.d("Log", "Folder zip file create: " + folder.getAbsolutePath());
            for (java.util.Map.Entry<String, ArrayList<FileObj>> entry : hashmapFileObj
                    .entrySet()) {
                ArrayList<FileObj> listFileObjs = entry.getValue();

                if (listFileObjs.size() > 1) {
                    String spinnerCode = "";
                    long fileLengthOrigin = 0L;
                    List<FileObj> listFileDest = new ArrayList<>();

                    for (FileObj fileObj : listFileObjs) {
                        File fileSrc = new File(fileObj.getPath());
                        Log.d(Constant.TAG,
                                "FileUtil getArrFileBackUp fileSrc: "
                                        + fileSrc.getPath()
                                        + " fileSrc length " + fileSrc.length()
                                        + " fileSrc.exists() "
                                        + fileSrc.exists());
                        if (fileSrc.length() > 0) {

                            Bitmap bitmap = BitmapFactory.decodeFile(fileSrc
                                    .getPath());
                            Bitmap bitmapImage = CommonActivity
                                    .getResizedBitmap(bitmap,
                                            Constant.SIZE_IMAGE_SCALE);

                            File fileDes = new File(folder.getPath()
                                    + File.separator
                                    + System.currentTimeMillis() + "_"
                                    + fileObj.getCodeSpinner() + ".jpg");
                            FileOutputStream out = new FileOutputStream(fileDes);
                            bitmapImage.compress(Bitmap.CompressFormat.JPEG,
                                    Constant.DEFAULT_JPEG_QUALITY, out);
                            out.close();
                            FileObj fileObjDest = new FileObj();
                            fileObjDest
                                    .setCodeSpinner(fileObj.getCodeSpinner());
                            fileObjDest.setPath(fileDes.getPath());
                            fileObjDest.setName(fileObj.getName());
                            fileObjDest.setActionCode(fileObj.getActionCode());
                            fileObjDest.setReasonId(fileObj.getReasonId());
                            fileObjDest.setIsdn(fileObj.getIsdn());
                            fileObjDest
                                    .setActionAudit(fileObj.getActionAudit());
                            fileObjDest.setFile(fileDes);
                            fileLengthOrigin += fileSrc.length();
                            fileObjDest.setRecodeName(fileObj.getRecodeName());
                            spinnerCode = fileObj.getCodeSpinner();
                            listFileDest.add(fileObjDest);
                        } else {
                            Log.e(Constant.TAG,
                                    "FileUtil getArrFileBackUp notfound: "
                                            + fileSrc.getPath());
                            return null;
                        }
                    }
                    String zipFilePath = folder.getPath() + File.separator
                            + spinnerCode + ".zip";
                    Log.d("Log", "zipFilePath: " + zipFilePath);

                    String recodeName = "";
                    ArrayList<File> listfileDesZip = new ArrayList<>();
                    for (FileObj fileObj : listFileDest) {
                        listfileDesZip.add(fileObj.getFile());
                        recodeName = fileObj.getRecodeName();
                    }

                    File isdnZip = FileUtils.zip(listfileDesZip, zipFilePath);
                    for (File file : listfileDesZip) {
                        file.delete();
                    }

                    fileObjBackup = new FileObj();
                    fileObjBackup.setPath(isdnZip.getPath());
                    fileObjBackup.setFile(isdnZip);
                    fileObjBackup.setCodeSpinner(spinnerCode);
                    fileObjBackup.setPageIndex(1 + "");
                    fileObjBackup.setFileLength(isdnZip.length() + "");
                    fileObjBackup.setFileLengthOrigin(fileLengthOrigin + "");
                    fileObjBackup.setRecodeName(recodeName);
                    arrFileBackUp.add(fileObjBackup);
                } else if (listFileObjs.size() == 1) {
                    FileObj fileObject = listFileObjs.get(0);
                    File fileSrc = new File(fileObject.getPath());
                    if (fileSrc.exists()) {

                        Bitmap bitmap = BitmapFactory.decodeFile(fileSrc
                                .getPath());
                        Bitmap bitmapImage = CommonActivity.getResizedBitmap(
                                bitmap, Constant.SIZE_IMAGE_SCALE);

                        File fileDes = new File(folder.getPath()
                                + File.separator + fileObject.getCodeSpinner()
                                + ".jpg");
                        FileOutputStream out = new FileOutputStream(fileDes);
                        bitmapImage.compress(Bitmap.CompressFormat.JPEG,
                                Constant.DEFAULT_JPEG_QUALITY, out);
                        out.close();

						Log.d(Constant.TAG, "=>>>>>>>zip file path = " + fileDes.getPath());

                        // FileUtils.copy(fileSrc, fileDes);
                        fileObjBackup = new FileObj();
                        fileObjBackup.setCodeSpinner(fileObject
                                .getCodeSpinner());
                        fileObjBackup.setPath(fileDes.getPath());
                        fileObjBackup.setFile(fileDes);
                        fileObjBackup.setPageIndex(2 + "");
                        fileObjBackup.setFileLength(fileDes.length() + "");
                        fileObjBackup
                                .setFileLengthOrigin(fileSrc.length() + "");
                        fileObjBackup.setRecodeName(fileObject.getRecodeName());
                        arrFileBackUp.add(fileObjBackup);
                    } else {
                        Log.d("Log", "fileSrc: " + fileSrc.getPath()
                                + " NOT FOUND");
                    }
                }
            }
        } catch (Exception e) {
            Log.e("Log", "loi su ly file ", e);
            fileObjBackup = new FileObj();
            fileObjBackup.setPath(e.toString() + " /NULL/NULL.NULL");
            fileObjBackup.setStatus(-2);
            arrFileBackUp.add(fileObjBackup);
        }
        return arrFileBackUp;
    }

    public static ArrayList<File> backUplistFileUnzip(ArrayList<File> listFile) {
        ArrayList<File> listFileCoppy = new ArrayList<>();
        File folder = new File(Constant.MBCCS_TEMP_FOLDER_IMG_PROFILE);
        if (!folder.exists()) {
            folder.mkdir();
        }
        for (File fileSrc : listFile) {
            String pathDest = folder.getPath() + "/" + fileSrc.getName();
            File fileDest = new File(pathDest);
            boolean isCoppy = copy(fileSrc, fileDest);
            if (isCoppy) {
                listFileCoppy.add(fileDest);
                fileSrc.delete();
            }
        }
        return listFileCoppy;
    }

    public static ArrayList<File> listFilesForFolder(final File folder) {
        ArrayList<File> listFile = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                Log.d(Constant.TAG,
                        "FileUtils listFilesForFolder fileEntry.getName() "
                                + fileEntry.getName());
                listFile.add(fileEntry);
            }
        }
        return listFile;
    }

    public static File zipListFile(Context context,
                                   HashMap<String, ArrayList<FileObj>> hashmapFileObj) {
        File isdnZip = null;
        try {
            File folder = new File(Constant.MBCCS_TEMP_FOLDER_IMG_PROFILE);
            if (!folder.exists()) {
                folder.mkdir();
            }
            Log.d("Log",
                    "zipListFile Folder zip file create: "
                            + folder.getAbsolutePath());
            for (Entry<String, ArrayList<FileObj>> entry : hashmapFileObj
                    .entrySet()) {
                ArrayList<FileObj> listFileObjs = entry.getValue();

                Log.d(Constant.TAG,
                        "FileUtil zipListFile listFileObjs.size(): "
                                + listFileObjs.size());
                if (listFileObjs.size() > 1) {
                    String spinnerCode = "";
                    List<FileObj> listFileDest = new ArrayList<>();

                    for (FileObj fileObj : listFileObjs) {
                        File fileSrc = new File(fileObj.getPath());
                        if (fileSrc.length() > 0) {
                            Bitmap bitmapImage = Picasso
                                    .with(context)
                                    .load(fileSrc)
                                    .resize(Constant.SIZE_IMAGE_SCALE,
                                            Constant.SIZE_IMAGE_SCALE).get();
                            File fileDes = new File(folder.getPath()
                                    + File.separator + "_"
                                    + System.currentTimeMillis()
                                    + fileObj.getCodeSpinner() + ".jpg");
                            FileOutputStream out = new FileOutputStream(fileDes);
                            bitmapImage.compress(Bitmap.CompressFormat.JPEG,
                                    Constant.DEFAULT_JPEG_QUALITY, out);
                            out.close();

                            Log.d(Constant.TAG,
                                    "FileUtil zipListFile fileSrc.getPath() "
                                            + fileSrc.getPath()
                                            + " fileSrc length "
                                            + fileSrc.length()
                                            + " fileSrc.exists() "
                                            + fileSrc.exists());
                            Log.d(Constant.TAG,
                                    "FileUtil zipListFile bitmapImage.getByteCount() "
                                            + bitmapImage.getByteCount());
                            Log.d(Constant.TAG,
                                    "FileUtil zipListFile fileDes.getPath() "
                                            + fileDes.getPath()
                                            + " fileDes length "
                                            + fileDes.length()
                                            + " fileDes.exists() "
                                            + fileDes.exists());

                            FileObj fileObjDest = new FileObj();
                            fileObjDest
                                    .setCodeSpinner(fileObj.getCodeSpinner());
                            fileObjDest.setPath(fileDes.getPath());
                            fileObjDest.setName(fileObj.getName());
                            fileObjDest.setActionCode(fileObj.getActionCode());
                            fileObjDest.setReasonId(fileObj.getReasonId());
                            fileObjDest.setIsdn(fileObj.getIsdn());
                            fileObjDest
                                    .setActionAudit(fileObj.getActionAudit());
                            fileObjDest.setFile(fileDes);
                            spinnerCode = fileObj.getCodeSpinner();
                            listFileDest.add(fileObjDest);
                        } else {
                            Log.e(Constant.TAG,
                                    "FileUtil zipListFile NOTFOUND fileSrc.getPath() "
                                            + fileSrc.getPath()
                                            + " fileSrc length "
                                            + fileSrc.length()
                                            + " fileSrc.exists() "
                                            + fileSrc.exists());
                            return null;
                        }
                    }
                    String zipFilePath = folder.getPath() + File.separator
                            + spinnerCode + ".zip";
                    Log.d("Log", "zipListFile zipFilePath: " + zipFilePath);

                    ArrayList<File> listfileDesZip = new ArrayList<>();
                    for (FileObj fileObj : listFileDest) {
                        listfileDesZip.add(fileObj.getFile());
                    }

                    isdnZip = FileUtils.zip(listfileDesZip, zipFilePath);
                    break;
                }
            }
        } catch (Exception e) {
            Log.e(Constant.TAG, "zipListFile loi su ly file ", e);
        }
        return isdnZip;
    }

    /**
     * Ham reduce file anh
     *
     * @param file
     * @return
     */
    public static File saveBitmapToFile(File file) {
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE = 75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            return file;
        } catch (Exception e) {
            Log.d(Constant.TAG, "error", e);
            return null;
        }
    }

    public static String getException(Exception e) {
        StringBuilder sb = new StringBuilder();
        if (e != null) {
            sb.append(e.toString()); // 7
            StackTraceElement[] st = e.getStackTrace();
//            StackTraceElement stackTrace = null;
            // tim kiem stack danh dau co Log cua com.viettel.nqt
            for (int i = 0; i < st.length; i++) {
                StackTraceElement stackTrace = st[i];
                if (stackTrace != null) {
                    sb.append(" ");
                    sb.append(stackTrace.getClassName());
                    sb.append(":");
                    sb.append(stackTrace.getMethodName());
                    sb.append(":");
                    sb.append(stackTrace.getLineNumber());
                }
            }
        }
        return sb.toString();
    }

	public static void openFile(Context mContext, String fileName) {
		File file = new File(fileName);
		Log.d("LOG", "Link file: " + fileName);
		Intent intent = new Intent(Intent.ACTION_VIEW);

		String extension = android.webkit.MimeTypeMap
				.getFileExtensionFromUrl(Uri.fromFile(file).toString());
		String mimetype = android.webkit.MimeTypeMap.getSingleton()
				.getMimeTypeFromExtension(extension);
		if (extension.equalsIgnoreCase("") || mimetype == null) {
		} else {
			intent.setDataAndType(Uri.fromFile(file), mimetype);
		}

		intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		Intent intent2 = Intent.createChooser(intent, "Open File");
		try {
			mContext.startActivity(intent2);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}


//	public static String getRealPathFromURI(Context mContext, Uri contentURI){
//		Cursor cursor = mContext.getContentResolver().query(contentURI, null, null, null, null);
//		if(cursor == null){
//			return contentURI.getPath();
//		} else {
//			cursor.moveToFirst();
//			int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//			return cursor.getString(index);
//		}
//	}

    /**
     * Ham tao file gui len server
     *
     * @param hashmapFileObj
     * @return
     * @throws Exception
     */
    public static ArrayList<String> createFilePaths(HashMap<String, ArrayList<FileObj>> hashmapFileObj) {
        ArrayList<String> lstFilePath = new ArrayList<>();
        try {
            File folder = new File(Constant.MBCCS_TEMP_FOLDER_IMG_PROFILE);
            if (!folder.exists()) {
                folder.mkdir();
            }

            Log.d("Log", "Folder zip file create: " + folder.getAbsolutePath());
            for (java.util.Map.Entry<String, ArrayList<FileObj>> entry : hashmapFileObj.entrySet()) {
                ArrayList<FileObj> listFileObjs = entry.getValue();
                String zipFilePath = "";
                if (listFileObjs.size() > 1) {
                    String spinnerCode = "";
                    for (FileObj fileObj : listFileObjs) {
                        spinnerCode = fileObj.getCodeSpinner();
                    }
                    zipFilePath = folder.getPath() + File.separator + System.currentTimeMillis() + "_"
                            + spinnerCode + ".zip";
                    lstFilePath.add(zipFilePath);
                } else if (listFileObjs.size() == 1) {
                    FileObj fileObj = listFileObjs.get(0);
                    if(fileObj != null){
                        if(fileObj.getRecordId() != null
                                && fileObj.getRecordId() > 0){
                            zipFilePath = folder.getPath() + File.separator + System.currentTimeMillis() + "_"
                                    + fileObj.getCodeSpinner() + "_" +  fileObj.getRecordId() + ".jpg"; // luu them thong tin recordId
                        } else {
                            zipFilePath = folder.getPath() + File.separator + System.currentTimeMillis() + "_"
                                    + fileObj.getCodeSpinner()  + ".jpg";
                        }
                    }
                    lstFilePath.add(zipFilePath);
                }
            }
        } catch (Exception ex) {
            Log.d(Constant.TAG, "createFilePaths", ex);
        }
        return lstFilePath;
    }

    public static void createFilePaths(HashMap<String, ArrayList<FileObj>> hashmapFileObj, ArrayList<String> lstFilePath, ArrayList<String> lstRecordName, ArrayList<String> lstString) {
        try {
            File folder = new File(Constant.MBCCS_TEMP_FOLDER_IMG_PROFILE);
            if (!folder.exists()) {
                folder.mkdir();
            }

            Log.d("Log", "Folder zip file create: " + folder.getAbsolutePath());
            for (java.util.Map.Entry<String, ArrayList<FileObj>> entry : hashmapFileObj.entrySet()) {
                ArrayList<FileObj> listFileObjs = entry.getValue();
                String zipFilePath = "";
                if (listFileObjs.size() > 1) {
                    String spinnerCode = "";
                    for (FileObj fileObj : listFileObjs) {
                        spinnerCode = fileObj.getCodeSpinner();
                    }
                    zipFilePath = folder.getPath() + File.separator + System.currentTimeMillis() + "_"
                            + spinnerCode + ".zip";
                    lstFilePath.add(zipFilePath);
                } else if (listFileObjs.size() == 1) {
                    FileObj fileObj = listFileObjs.get(0);
                    if(fileObj != null){
                        if(fileObj.getRecordId() != null
                                && fileObj.getRecordId() > 0){
                            zipFilePath = folder.getPath() + File.separator + System.currentTimeMillis() + "_"
                                    + fileObj.getCodeSpinner() + "_" +  fileObj.getRecordId() + ".jpg"; // luu them thong tin recordId
                        } else {
                            zipFilePath = folder.getPath() + File.separator + System.currentTimeMillis() + "_"
                                    + fileObj.getCodeSpinner()  + ".jpg";
                        }
                    }
                    lstFilePath.add(zipFilePath);
                }
                lstRecordName.add(listFileObjs.get(0).getRecodeName());
                if(entry.getKey().contains("_")) {
                    lstString.add(entry.getKey().split("_")[1]);
                }
            }
            Log.d(Constant.TAG, "lstFilePath size = " + lstFilePath.size());
        } catch (Exception ex) {
            Log.d(Constant.TAG, "createFilePaths", ex);
        }
    }

    public static void createFilePaths(HashMap<String, ArrayList<FileObj>> hashmapFileObj, ArrayList<String> lstFilePath, ArrayList<String> lstRecordName, ArrayList<String> lstString, ArrayList<String> lstRecordCode) {
        try {
            File folder = new File(Constant.MBCCS_TEMP_FOLDER_IMG_PROFILE);
            if (!folder.exists()) {
                folder.mkdir();
            }

            Log.d("Log", "Folder zip file create: " + folder.getAbsolutePath());
            for (java.util.Map.Entry<String, ArrayList<FileObj>> entry : hashmapFileObj.entrySet()) {
                ArrayList<FileObj> listFileObjs = entry.getValue();
                String zipFilePath = "";
                if (listFileObjs.size() > 1) {
                    String spinnerCode = "";
                    for (FileObj fileObj : listFileObjs) {
                        spinnerCode = fileObj.getCodeSpinner();
                    }
                    zipFilePath = folder.getPath() + File.separator + System.currentTimeMillis() + "_"
                            + spinnerCode + ".zip";
                    lstFilePath.add(zipFilePath);
                } else if (listFileObjs.size() == 1) {
                    FileObj fileObj = listFileObjs.get(0);
                    if(fileObj != null){
                        if(fileObj.getRecordId() != null
                                && fileObj.getRecordId() > 0){
                            zipFilePath = folder.getPath() + File.separator + System.currentTimeMillis() + "_"
                                    + fileObj.getCodeSpinner() + "_" +  fileObj.getRecordId() + ".jpg"; // luu them thong tin recordId
                        } else {
                            zipFilePath = folder.getPath() + File.separator + System.currentTimeMillis() + "_"
                                    + fileObj.getCodeSpinner()  + ".jpg";
                        }
                    }
                    lstFilePath.add(zipFilePath);
                }
                lstRecordName.add(listFileObjs.get(0).getRecodeName());
                lstRecordCode.add(listFileObjs.get(0).getCodeSpinner());
                if(entry.getKey().contains("_")) {
                    lstString.add(entry.getKey().split("_")[1]);
                }
            }
            Log.d(Constant.TAG, "lstFilePath size = " + lstFilePath.size());
        } catch (Exception ex) {
            Log.d(Constant.TAG, "createFilePaths", ex);
        }
    }

    public static void unzip(File zipFile, File targetDirectory) throws IOException {
        ZipInputStream zis = new ZipInputStream(
                new BufferedInputStream(new FileInputStream(zipFile)));
        try {
            ZipEntry ze;
            int count;
            byte[] buffer = new byte[1024];
            while ((ze = zis.getNextEntry()) != null) {
                File file = new File(targetDirectory, ze.getName());
                File dir = ze.isDirectory() ? file : file.getParentFile();
                if (!dir.isDirectory() && !dir.mkdirs())
                    throw new FileNotFoundException("Failed to ensure directory: " +
                            dir.getAbsolutePath());
                if (ze.isDirectory())
                    continue;
                FileOutputStream fout = new FileOutputStream(file);
                try {
                    while ((count = zis.read(buffer)) != -1)
                        fout.write(buffer, 0, count);
                } finally {
                    fout.close();
                }
            /* if time should be restored as well
            long time = ze.getTime();
            if (time > 0)
                file.setLastModified(time);
            */
            }
        } finally {
            zis.close();
        }
    }

    public static List<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                inFiles.addAll(getListFiles(file));
            } else {
                inFiles.add(file);
            }
        }
        return inFiles;
    }

    public static byte[] getByte(File file) {
        byte[] getBytes = {};
        try {
            getBytes = new byte[(int) file.length()];
            InputStream is = new FileInputStream(file);
            is.read(getBytes);
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getBytes;
    }

    public static boolean deleteDir(File dir) {
        deleteDirectory(dir);
//        if (dir.isDirectory()) {
//            String[] children = dir.list();
//            for (int i = 0; i < children.length; i++) {
//                boolean success = deleteDir(new File(dir, children[i]));
//                if (!success) {
//                    return false;
//                }
//            }
//        }

        return true;
    }

    public static void deleteDirectory(File dir) {

        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                File child = new File(dir, children[i]);
                if (child.isDirectory()) {
                    deleteDirectory(child);
                    child.delete();
                } else {
                    child.delete();

                }
            }
//            dir.delete();
        }
    }

    public static boolean zip2(List<File> fileList, File zipFile) {
        ZipOutputStream zipOutputStream = null;
        try {
            zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));
            int bufferSize = 1024;
            byte[] buf = new byte[bufferSize];
            ZipEntry zipEntry;
            for (int i = 0; i < fileList.size(); i++) {
                File file = fileList.get(i);
                String name = file.getName();
                zipEntry = new ZipEntry(name);
                zipOutputStream.putNextEntry(zipEntry);
                if (!file.isDirectory()) {
                    InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
                    int readLength;
                    while ((readLength = inputStream.read(buf, 0, bufferSize)) != -1) {
                        zipOutputStream.write(buf, 0, readLength);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                zipOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static Bitmap decodeBitmapFromFile2(String filePath, int reqWidth,
                                               int reqHeight) {

//        return decodeFile2(filePath);
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    private static Bitmap decodeFile2(String path) {
        try {
            File f = new File(path);
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE = 500;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;

            while (o.outWidth / scale / 2 >= REQUIRED_SIZE
                    && o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
            Log.d(Constant.TAG, "error", e);
        }
        return null;
    }

    public static File zipFileImage2(List<File> _files, String zipFilePath) {
        File zipfile = new File(zipFilePath);
        FileOutputStream fos = null;
        ZipOutputStream zout = null;
        try {
            fos = new FileOutputStream(zipfile);
            zout = new ZipOutputStream(fos);
            for (File obj : _files) {
                ZipEntry entry = new ZipEntry(obj.getName());
                zout.putNextEntry(entry);

                long length = obj.length();
                int percent = length < Constant.MAX_SIZE_IMG ? 100
                        : (int) (Constant.MAX_SIZE_IMG * 100 / length);
                // Bitmap bitmap =
                // BitmapFactory.decodeFile(obj.getFile().getPath());
                Bitmap bitmap;
                if (obj.length() < Constant.MAX_SIZE_IMG) {
                    bitmap = BitmapFactory.decodeFile(obj.getPath());
                } else {
                    bitmap = FileUtils.decodeBitmapFromFile(obj
                            .getPath(), 1000, 1000);
                }
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, percent, baos);
                byte[] buffer = baos.toByteArray();
                Log.v("Compress", "Adding length: " + length + " percent: "
                        + percent + " buffer: " + buffer.length + " "
                        + obj.getPath());
                zout.write(buffer);
                baos.close();
            }

            return zipfile;

        } catch (Exception e) {
            Log.d(Constant.TAG, "error", e);
        } finally {
            try {
                if (zout != null) {
                    zout.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                Log.d(Constant.TAG, "error", e);
            }
        }
        return null;
    }

}
