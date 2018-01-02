package com.viettel.bss.viettelpos.v4.hsdt.asynctask;

import android.app.Activity;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * Created by Toancx on 10/20/2017.
 */

public class PrintRequestOrderForSaleAsyncTask extends AsyncTaskCommon<String, Void, File> {

    private Activity context;
    private String idProfileType;
    private String orderId;
    private String currentTime;
    private String filename;
    private String fileExtension;

    public PrintRequestOrderForSaleAsyncTask(Activity context,
                                 String idProfileType,
                                 String orderId,
                                 OnPostExecuteListener<File> listener,
                                 View.OnClickListener moveLogInAct) {

        super(context, listener, moveLogInAct);

        this.context = context;
        this.idProfileType = idProfileType;
        this.orderId = orderId;

        this.currentTime = System.currentTimeMillis() + "";
        this.fileExtension = "jpg";
        this.filename = orderId + idProfileType + "_" + currentTime + "." + fileExtension;
    }

    @Override
    protected File doInBackground(String... params) {
        return printRequestOrderForSale();
    }

    private File printRequestOrderForSale() {

        File fileResult = CommonActivity.getFileInDir(Constant.DIR_SAVE_PROFILE_PATH, orderId + idProfileType);
        if (!CommonActivity.isNullOrEmpty(fileResult)) {
            errorCode = "0";
            return fileResult;
        }

        String original = "";
        ParseOuput out = null;
        String func = "printRequestOrderForSale";
        try {
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_" + func);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<ws:" + func + ">");
            stringBuilder.append("<input>");
            stringBuilder.append("<token>" + Session.getToken() + "</token>");

            if (!CommonActivity.isNullOrEmpty(idProfileType)) {
                stringBuilder.append("<idProfileType>");
                stringBuilder.append(idProfileType);
                stringBuilder.append("</idProfileType>");
            }

            if (!CommonActivity.isNullOrEmpty(orderId)) {
                stringBuilder.append("<orderId>");
                stringBuilder.append(orderId);
                stringBuilder.append("</orderId>");
            }

            stringBuilder.append("</input>");
            stringBuilder.append("</ws:" + func + ">");
            Log.i("RowData", stringBuilder.toString());
            String envelope = input.buildInputGatewayWithRawData(stringBuilder.toString());
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
            description = mActivity.getString(R.string.no_return_from_system);
            errorCode = Constant.ERROR_CODE;
            return null;
        } else {
            description = out.getDescription();
            errorCode = out.getErrorCode();
        }

        if (!CommonActivity.isNullOrEmpty(out.getFileExtension())) {
            this.fileExtension = out.getFileExtension();
            this.filename = idProfileType + "_" + currentTime + "." + fileExtension;
            this.filename = filename.replaceAll("\\/", "");
        }

        if (CommonActivity.isNullOrEmpty(out.getFileContent())) {
            return null;
        } else {
            CommonActivity.saveFileBase64(out.getFileContent(), Constant.DIR_SAVE_PROFILE_PATH, filename);
            fileResult = new File(Constant.DIR_SAVE_PROFILE_PATH +  File.separator + filename);
            return fileResult;
        }
    }
}