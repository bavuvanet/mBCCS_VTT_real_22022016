package com.viettel.bss.viettelpos.v4.charge.asynctask;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.object.DownloadFile;
import com.viettel.bss.viettelpos.v4.charge.object.PaymentOutput;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostSuccessExecute;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.synchronizationdata.UpdateVersionAsyn;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AsyncDownloadFile extends AsyncTask<String, String, DownloadFile> {

    private Context context;
    private String contractNo;
    private String billCycle;
    private String isdn;
    private String filename;
    private ProgressDialog progress;

    public AsyncDownloadFile(Context context, String bankContract, String isdn, String cycle) {
        super();
        this.isdn = isdn;
        this.context = context;
        this.contractNo = bankContract;
        this.billCycle = cycle;
        this.progress = new ProgressDialog(context);
        this.progress.setCancelable(false);
        this.progress.setMessage(context.getResources().getString(
                R.string.getting_charge_notify));
        if (!this.progress.isShowing()) {
            this.progress.show();
        }

        this.context = context;
        this.isdn = isdn;

    }

    private final View.OnClickListener onclickOpenFile = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (!CommonActivity.isNullOrEmpty(filename))
                showFilepdf();
        }
    };

    @Override
    protected DownloadFile doInBackground(String... params) {
        return getChargeNotify();
    }

    @Override
    protected void onPostExecute(DownloadFile result) {
        super.onPostExecute(result);
        progress.dismiss();
        String des = result.getDescription();
        try {
            if (result.getErrorCode().equals("0")) {
                filename = (CommonActivity.isNullOrEmpty(contractNo) ? "" : (contractNo + "_"))
                        + (CommonActivity.isNullOrEmpty(isdn) ? "" : (isdn + "_")) + billCycle + ".pdf";
                filename = filename.replaceAll("\\/", "");
                saveFile(result.getFileContent(), Constant.DOWNLOAD_DIRECTORY_STRING, filename);
                des = context.getResources().getString(R.string.isOpenFile)
                        + "\n" + Constant.DOWNLOAD_DIRECTORY_STRING + "/" + filename;
                Dialog dialog = CommonActivity.createDialog(
                        (Activity) context,
                        des
                        , context.getString(R.string.saveNotifySuccess),
                        context.getResources().getString(R.string.txt_cancel),
                        context.getResources().getString(R.string.ok),
                        null, onclickOpenFile);
                dialog.show();
            } else {
                if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
                    des = result.getDescription();
                    LoginDialog dialog = new LoginDialog((Activity) context, "print.charge.notify");
                    dialog.show();
                } else {
                    if (CommonActivity.isNullOrEmpty(des)) {
                        des = context.getString(R.string.no_data);
                    }
                }
                Dialog dialog = CommonActivity.createAlertDialog(context, des,
                        context.getString(R.string.app_name));
                dialog.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showFilepdf() {
        File file = new File(Constant.DOWNLOAD_DIRECTORY_STRING + "/" + filename);
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file), "application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        Intent intent = Intent.createChooser(target, "Open File");
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Instruct the user to install a PDF reader here, or something
        }
    }

    private DownloadFile getChargeNotify() {
        String original;
        DownloadFile out = new DownloadFile();
        try {
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode",
                    "mbccs_getNotice");
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:getNotice>");
            rawData.append("<input>");
            if (!CommonActivity.isNullOrEmpty(isdn)) {
                rawData.append("<isdn>").append(isdn).append("</isdn>");
            }
            if (!CommonActivity.isNullOrEmpty(contractNo)) {
                rawData.append("<contractNo>").append(contractNo).append("</contractNo>");
            }

            rawData.append("<billCycle>").append("01/" + billCycle).append("</billCycle>");
            rawData.append("<token>").append(Session.token).append("</token>");
            rawData.append("</input>");
            rawData.append("</ws:getNotice>");
            Log.i("RowData", rawData.toString());
            String envelope = input.buildInputGatewayWithRawData(rawData
                    .toString());

            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope,
                    Constant.BCCS_GW_URL, context,
                    "mbccs_getNotice");
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();

            Serializer serializer = new Persister();
            out = serializer.read(DownloadFile.class, original);
            if (out == null) {
                out = new DownloadFile();
                out.setDescription(context
                        .getString(R.string.no_return_from_system));
                out.setErrorCode(Constant.ERROR_CODE);
                return out;
            }

            Log.i("Responseeeeeeeeee", response);

        } catch (Exception e) {
            e.printStackTrace();
            out.setDescription(context.getResources().getString(R.string.exception));
        }

        return out;
    }


    public static void saveFile(String content, String directory, String fileName) {
        FileOutputStream f = null;
        InputStream in = null;
        try {


            f = new FileOutputStream(directory + File.separator + "/" + fileName);

            byte[] fileByte = Base64.decode(content, Base64.DEFAULT);
            in = new ByteArrayInputStream(fileByte);

            byte[] buffer = new byte[1024];
            int len1;
            while ((len1 = in.read(buffer)) > 0) {
                f.write(buffer, 0, len1);
            }
            f.close();
            in.close();
            Log.i("download File", directory + File.separator + "/" + fileName);
        } catch (Exception e) {
            Log.e("download file", e.getMessage());
        } finally {
            if (f != null) {
                try {
                    f.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
