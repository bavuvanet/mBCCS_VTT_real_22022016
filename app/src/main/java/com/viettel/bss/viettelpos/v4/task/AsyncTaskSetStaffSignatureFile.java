package com.viettel.bss.viettelpos.v4.task;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.cc.object.CCOutput;
import com.viettel.bss.viettelpos.v4.channel.activity.SplashActivity;
import com.viettel.bss.viettelpos.v4.channel.activity.StaffInfoActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.ByteArrayOutputStream;

/**
 * Created by thinhhq1 on 10/21/2017.
 */
public class AsyncTaskSetStaffSignatureFile extends AsyncTaskCommon<Bitmap, Void, String> {

    public AsyncTaskSetStaffSignatureFile(Activity context,
                                          OnPostExecuteListener<String> listener,
                                          View.OnClickListener moveLogInAct) {
        super(context, listener, moveLogInAct);
    }

    @Override
    protected String doInBackground(Bitmap... args) {
        return uploadInfo(args[0]);
    }

    private String uploadInfo(Bitmap image) {
        String original = "";
        String imageSignature;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        imageSignature = Base64.encodeToString(byteArray, Base64.DEFAULT);
        try {
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_setStaffSignatureFile");
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:setStaffSignatureFile>");

            rawData.append("<input>");
            rawData.append("<token>").append(Session.getToken()).append("</token>");
            if (!CommonActivity.isNullOrEmpty(imageSignature))
                rawData.append("<staffSignature>").append(imageSignature).append("</staffSignature>");
            rawData.append("</input>");

            rawData.append("</ws:setStaffSignatureFile>");

            Log.i("LOG", "raw data" + rawData.toString());
            String envelope = input.buildInputGatewayWithRawData(rawData
                    .toString());
            Log.d("LOG", "Send evelop" + envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input
                    .sendRequest(envelope, Constant.BCCS_GW_URL,
                            mActivity, "mbccs_setStaffSignatureFile");
            Log.i("LOG", "Respone:  " + response);

            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();
            Serializer serializer = new Persister();
            CCOutput parseOuput = serializer.read(CCOutput.class, original);
            if(parseOuput != null){
                errorCode = parseOuput.getErrorCode();
                description = parseOuput.getDescription();
                parseOuput.setStaffSignatureImage(imageSignature);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageSignature;
    }
}
