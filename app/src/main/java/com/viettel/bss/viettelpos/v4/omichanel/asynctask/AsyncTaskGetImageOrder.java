package com.viettel.bss.viettelpos.v4.omichanel.asynctask;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.hsdt.object.Decompress;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ProfileRecord;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thuandq on 11/09/2017.
 */

public class AsyncTaskGetImageOrder extends AsyncTaskCommon<Void, Void, List<ProfileRecord>> {

    private List<ProfileRecord> profileRecordList;
    private Activity activity;

    public AsyncTaskGetImageOrder(List<ProfileRecord> profileRecordList,
                                  Activity activity,
                                  OnPostExecuteListener<List<ProfileRecord>> listener,
                                  View.OnClickListener moveLogInAct) {

        super(activity, listener, moveLogInAct);

        this.profileRecordList = profileRecordList;
        this.activity = activity;
    }

    @Override
    protected List<ProfileRecord> doInBackground(Void... params) {
        List<ProfileRecord> profileRecords = getImageOrder();
        for (ProfileRecord profileRecord : profileRecords) {
            try {
                if (!CommonActivity.isNullOrEmpty(profileRecord.getSymbolicLink())) {
                    if (profileRecord.getSymbolicLink().toLowerCase().endsWith(".jpg")
                            || profileRecord.getSymbolicLink().toLowerCase().endsWith(".png")) {
                        Bitmap bitmap = getBitmapFromURL(profileRecord.getSymbolicLink());
                        profileRecord.setBitmap(bitmap);
                        if (!CommonActivity.isNullOrEmpty(bitmap)) {
                            // save file
                            String fileName = profileRecord.getSymbolicLink().substring(
                                    profileRecord.getSymbolicLink().lastIndexOf('/') + 1);
                            String filePath = saveImage(bitmap, fileName);
                            profileRecord.setPath(filePath);
                        }
                    } else if (profileRecord.getSymbolicLink().toLowerCase().endsWith(".zip")) {
                        String fileName = profileRecord.getSymbolicLink().substring(
                                profileRecord.getSymbolicLink().lastIndexOf('/') + 1);
                        Decompress decompress = new Decompress(fileName,
                                Constant.DIR_SAVE_PROFILE_SALE_PATH, profileRecord.getSymbolicLink());
                        List<String> list = decompress.unzip();
                        profileRecord.setUnZipPath(list);
                    } else { // pdf, etc...
                        // do nothing...
                    }
                }
            } catch (Exception e) {
                Log.e("getImageOrder", e.getMessage());
            }
        }
        return profileRecords;
    }

    private List<ProfileRecord> getImageOrder() {

        String original = "";
        ParseOuput out = null;
        String func = "getImageOrder";
        try {
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_" + func);
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:" + func + ">");
            rawData.append("<input>");
            rawData.append("<token>" + Session.getToken() + "</token>");

            if (!CommonActivity.isNullOrEmpty(profileRecordList)) {
                for (ProfileRecord profileRecord : profileRecordList) {
                    rawData.append("<lstProfileRecords>");
                    if (!CommonActivity.isNullOrEmpty(profileRecord.getCode()))
                        rawData.append("<code>").append(profileRecord.getCode()).append("</code>");
                    if (!CommonActivity.isNullOrEmpty(profileRecord.getUrl()))
                        rawData.append("<url>").append(profileRecord.getUrl()).append("</url>");
                    if (!CommonActivity.isNullOrEmpty(profileRecord.getServer()))
                        rawData.append("<server>").append(profileRecord.getServer()).append("</server>");
                    if (!CommonActivity.isNullOrEmpty(profileRecord.getSymbolicLink()))
                        rawData.append("<symbolicLink>").append(profileRecord.getSymbolicLink()).append("</symbolicLink>");
                    if (!CommonActivity.isNullOrEmpty(profileRecord.isDownload()))
                        rawData.append("<isDownload>").append(profileRecord.isDownload()).append("</isDownload>");
                    if (!CommonActivity.isNullOrEmpty(profileRecord.getFileExtension()))
                        rawData.append("<fileExtension>").append(profileRecord.getFileExtension()).append("</fileExtension>");
                    if (!CommonActivity.isNullOrEmpty(profileRecord.getType()))
                        rawData.append("<type>").append(profileRecord.getCode()).append("</type>");
                    rawData.append("</lstProfileRecords>");
                }
            }
            rawData.append("</input>");
            rawData.append("</ws:" + func + ">");
            Log.i("RowData", rawData.toString());
            String envelope = input.buildInputGatewayWithRawData(rawData
                    .toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
                    mActivity, "mbccs_" + func);
            Log.i("Responseeeeeeeeee", response);
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();
            Log.i(" Original", response);
            // parser
            Serializer serializer = new Persister();
            out = serializer.read(ParseOuput.class, original);
        } catch (Exception e) {
            Log.e(Constant.TAG, "blockSubForTerminate", e);
            description = e.getMessage();
            errorCode = Constant.ERROR_CODE;
        }
        if (CommonActivity.isNullOrEmpty(out)) {
            description = mActivity
                    .getString(R.string.no_return_from_system);
            errorCode = Constant.ERROR_CODE;
            return new ArrayList<>();
        } else {
            description = out.getDescription();
            errorCode = out.getErrorCode();
        }
        if (CommonActivity.isNullOrEmpty(out.getLstProfileRecords())) {
            return new ArrayList<>();
        }
        return out.getLstProfileRecords();
    }

    private String saveImage(Bitmap bitmap, String fileName) {
        File path = new File(Constant.DIR_SAVE_PROFILE_SALE_PATH);
        File file = new File(path, fileName);
        try {
            path.mkdirs();
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("saveImage", "File not found: " + e.getMessage());
        } catch (Exception e) {
            Log.e("saveImage", e.getMessage());
        }
        return file.getPath();
    }

    private Bitmap getBitmapFromURL(String urlString) {
        URL url = null;
        Bitmap resultBitmap = null;
        InputStream inputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            url = new URL(urlString.replace(" ", "%20"));
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            bufferedInputStream = new BufferedInputStream(inputStream, 8192);

            resultBitmap = BitmapFactory.decodeStream(bufferedInputStream);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultBitmap;
    }

    public String downloadFile(URL urlFile, String fileName) {
        File dir = new File(Constant.DIR_SAVE_PROFILE_SALE_PATH);
        File file = new File(dir, fileName);
        try {
            dir.mkdirs();
            InputStream is = urlFile.openStream();
            DataInputStream dis = new DataInputStream(is);
            byte[] buffer = new byte[1024];
            int length;
            FileOutputStream fos = new FileOutputStream(file);
            while ((length = dis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
        } catch (MalformedURLException mue) {
            com.viettel.bss.viettelpos.v4.utils.Log.e("SYNC getUpdate" + "malformed url error" + mue);
        } catch (IOException ioe) {
            com.viettel.bss.viettelpos.v4.utils.Log.e("SYNC getUpdate" + " io error " + ioe);
        } catch (SecurityException se) {
            com.viettel.bss.viettelpos.v4.utils.Log.e("SYNC getUpdate security error " + se);
        }
        return file.getPath();
    }
}
