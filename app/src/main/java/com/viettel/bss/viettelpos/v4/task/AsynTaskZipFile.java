package com.viettel.bss.viettelpos.v4.task;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.FileUtils;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.customview.obj.FileObj;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by toancx on 5/23/2017.
 */
public class AsynTaskZipFile extends AsyncTaskCommon<String, Void, ArrayList<FileObj>> {
    HashMap<String, ArrayList<FileObj>> hasMapFile;
    ArrayList<String> mlstFilePath;

    public AsynTaskZipFile(Activity context,
                           OnPostExecuteListener<ArrayList<FileObj>> listener,
                           View.OnClickListener moveLogInAct,
                           HashMap<String, ArrayList<FileObj>> hasMapFile,
                           ArrayList<String> mlstFilePath) {
        super(context, listener, moveLogInAct);
        this.hasMapFile = hasMapFile;
        this.mlstFilePath = mlstFilePath;

        this.progress.setMessage(context.getResources().getString(R.string.zipping_file));
    }

    @Override
    protected ArrayList<FileObj> doInBackground(String... params) {
        ArrayList<FileObj> arrFileObjs = new ArrayList<>();
        try {
            arrFileObjs = FileUtils.getArrFileBackUp2(mActivity, hasMapFile, mlstFilePath);
            errorCode = Constant.RESPONSE_CODE.SUCCESS;
        } catch (Exception ex) {
            errorCode = Constant.RESPONSE_CODE.ERROR;
            Log.d(Constant.TAG, "AsynTaskZipFile", ex);
        }
        return arrFileObjs;
    }

    @Override
    protected void onPostExecute(ArrayList<FileObj> result) {
        if (Constant.RESPONSE_CODE.ERROR.equals(errorCode)) { //xoa file truoc hop zip file that bai
            if (!CommonActivity.isNullOrEmpty(result)) {
                for (FileObj fileObj : result) {
                    File tmp = new File(fileObj.getPath());
                    tmp.delete();
                }
            }
        }

        super.onPostExecute(result);
    }
}
