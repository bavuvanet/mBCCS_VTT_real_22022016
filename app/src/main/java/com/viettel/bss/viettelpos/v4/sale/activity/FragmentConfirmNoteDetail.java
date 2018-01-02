package com.viettel.bss.viettelpos.v4.sale.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.SaleCommons;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.sale.adapter.AvailableSerialAdapter;
import com.viettel.bss.viettelpos.v4.sale.adapter.StockTransDetailAdapter;
import com.viettel.bss.viettelpos.v4.sale.business.ConfirmNoteBusiness;
import com.viettel.bss.viettelpos.v4.sale.business.LookUpStockBusiness;
import com.viettel.bss.viettelpos.v4.sale.object.Serial;
import com.viettel.bss.viettelpos.v4.sale.object.StockTransAction;
import com.viettel.bss.viettelpos.v4.sale.object.StockTransDetail;
import com.viettel.bss.viettelpos.v4.syn.handler.SynchronizeHandler;
import com.viettel.bss.viettelpos.v4.syn.object.SynchronizeDataObject;
import com.viettel.bss.viettelpos.v4.synchronizationdata.GetMaxOraRowScnDal;
import com.viettel.bss.viettelpos.v4.synchronizationdata.OjectSyn;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentConfirmNoteDetail extends FragmentCommon implements
        OnClickListener {
    private TextView tvImpCode;
    private ListView lvItem;
    private Button btnOK;
    private StockTransAction stockTransAction;
    private ArrayList<StockTransDetail> lstItem = new ArrayList<StockTransDetail>();
    private StockTransDetailAdapter adapter;
    private String errorMessage = "";
    private String LOG_TAG = FragmentConfirmNoteDetail.class.getName();
    private Button btnHome;
    private TextView txtNameActionBar;
    private ProgressDialog dialogSendSMS;
    private Boolean isTimeout = false;
    private StockTransDetail stockTransDetail;
    private Map<Long, List<Serial>> mapStockModelAndSerial = new HashMap<Long, List<Serial>>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idLayout = R.layout.sale_confirm_note_detail_layout;
    }

    @Override
    protected void unit(View view) {
        lvItem = (ListView) view.findViewById(R.id.lvStockTransDetail);
        btnOK = (Button) view.findViewById(R.id.btnOk);
        btnOK.setOnClickListener(this);
        tvImpCode = (TextView) view.findViewById(R.id.tvImpCode);

        Bundle bundle = getArguments();
        if (bundle != null) {
            stockTransAction = (StockTransAction) bundle
                    .getSerializable("stockTransAction");
            int position = bundle.getInt("position");
            String impCode = "";
            if (position < 10) {
                impCode = "PNKSM_0"
                        + position
                        + "_"
                        + DateTimeUtils.convertDateTimeToString(new Date(),
                        "ddMMyyyy");
            } else {
                impCode = "PNKSM_"
                        + position
                        + "_"
                        + DateTimeUtils.convertDateTimeToString(new Date(),
                        "ddMMyyyy");
            }
            tvImpCode.setText(impCode);

            lstItem = (ArrayList<StockTransDetail>) ((ParseOuput) bundle
                    .getSerializable("parseOuput"))
                    .getLstStockTransDetails();
        }

        adapter = new StockTransDetailAdapter(getActivity(), lstItem);

        lvItem.setAdapter(adapter);
        lvItem.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                stockTransDetail = lstItem.get(position);
                if (stockTransDetail.getCheckSerial() != null
                        && stockTransDetail.getCheckSerial().equals(1L)) {
                    if (mapStockModelAndSerial.containsKey(stockTransDetail
                            .getStockModelId())) {
                        showDialogSerial(mapStockModelAndSerial
                                .get(stockTransDetail.getStockModelId()));
                    } else {
                        new GetLstStockTransSerial(getActivity()).execute();
                    }
                }
            }
        });
    }

    @Override
    protected void setPermission() {

    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleActionBar(R.string.confirmNote);
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
//        getActivity().unregisterReceiver(receiver);
        super.onStop();
    }

    private String buildRawData(Boolean isSMS) {
        StringBuilder rawData = new StringBuilder();
        rawData.append("<ws:confirmNoteIM2>");
        rawData.append("<input>");
        HashMap<String, String> param = new HashMap<String, String>();
        if (!isSMS) {
            param.put("token", Session.getToken());
        } else {
            param.put("token", "");
        }
        param.put(
                "createExpDate",
                DateTimeUtils.convertDateTimeToString(
                        DateTimeUtils.convertStringToTime(stockTransAction.getCreateDatetime().substring(0, 10), "yyyy-MM-dd"), "dd/MM/yyyy"));
        param.put("expNoteCode", stockTransAction.getExpCode());
        param.put("impCode", tvImpCode.getText().toString());
        param.put("stockTransId", stockTransAction.getStockTransId() + "");
        BCCSGateway input = new BCCSGateway();
        rawData.append(input.buildXML(param));
        rawData.append("</input>");
        rawData.append("</ws:confirmNoteIM2>");
        return rawData.toString();
    }

    @Override
    public void onClick(View arg0) {
        if (arg0 == btnOK) {
            final Boolean isNetWorkConnected = CommonActivity
                    .isNetworkConnected(getActivity());
            String messageConfirm = getResources().getString(
                    R.string.confirm_note_confirm);
            String titleConfirm = getResources().getString(R.string.app_name);
            if (!isNetWorkConnected) {
                messageConfirm = getResources().getString(
                        R.string.no_network_message);
                titleConfirm = getResources().getString(
                        R.string.no_network_title);
            }

            OnClickListener confirmNote = new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (isNetWorkConnected) {
                        // truong hop co mang
                        ConfirmNoteIM2Asyn asy = new ConfirmNoteIM2Asyn(
                                getActivity());
                        asy.execute();
                    } else {
                        // Truong hop khong co mang
                        dialogSendSMS = new ProgressDialog(getActivity());
                        dialogSendSMS.setMessage(getResources().getString(
                                R.string.waitting));
                        dialogSendSMS.setCancelable(false);
                        if (!dialogSendSMS.isShowing()) {
                            dialogSendSMS.show();
                        }
                        // sendSMS(Constant.EXCHANGE_ADDRESS);
                        CommonActivity.sendSMS(Constant.EXCHANGE_ADDRESS,
                                buildRawData(true), getActivity(),
                                dialogSendSMS, "CFN");
                        new CountDownTimer(Constant.TIMEOUT_SMS, 1000) {
                            public void onTick(long millisUntilFinished) {
                            }

                            public void onFinish() {
                                if (dialogSendSMS.isShowing()) {
                                    dialogSendSMS.dismiss();
                                    Toast.makeText(
                                            getActivity(),
                                            getResources().getString(
                                                    R.string.time_out_sms),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        }.start();
                    }
                }
            };

            Dialog dialog = CommonActivity.createDialog(getActivity(),
                    messageConfirm, titleConfirm,
                    getResources().getString(R.string.ok), getResources()
                            .getString(R.string.cancel), confirmNote, null);
            dialog.show();
        }
    }

    // Ham lay chi tiet serial mat hang
    private class GetLstStockTransSerial extends
            AsyncTask<String, Void, ParseOuput> {

        ProgressDialog progress;
        private Context context = null;
        String errorCode = "";
        String description = "";

        public GetLstStockTransSerial(Context context) {
            this.context = context;
            this.progress = new ProgressDialog(this.context);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(
                    R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected ParseOuput doInBackground(String... arg0) {
            return getLstStockTransSerial();
        }

        @Override
        protected void onPostExecute(final ParseOuput result) {

            progress.dismiss();

            if ("0".equals(errorCode)) {
                if (result != null) {
                    if (result.getLstStockTransSerialDTOs() != null) {
                        if (!mapStockModelAndSerial
                                .containsKey(stockTransDetail.getStockModelId())) {
                            mapStockModelAndSerial.put(
                                    stockTransDetail.getStockModelId(),
                                    result.getLstStockTransSerialDTOs());
                        }

                        showDialogSerial(result.getLstStockTransSerialDTOs());
                    }
                } else {
                    if (description != null && !description.isEmpty()) {
                        Dialog dialog = CommonActivity.createAlertDialog(
                                getActivity(), description, getResources()
                                        .getString(R.string.app_name));
                        dialog.show();
                    } else {
                        Dialog dialog = CommonActivity.createAlertDialog(
                                getActivity(),
                                getResources().getString(R.string.no_data),
                                getResources().getString(R.string.app_name));
                        dialog.show();
                    }

                }
            } else {
                if (Constant.INVALID_TOKEN2.equals(errorCode)
                        && description != null && !description.isEmpty()) {

                    Dialog dialog = CommonActivity
                            .createAlertDialog(
                                    (Activity) context,
                                    description,
                                    context.getResources().getString(
                                            R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = context.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(
                            getActivity(), description, getResources()
                                    .getString(R.string.app_name));
                    dialog.show();

                }
            }
        }

        private ParseOuput getLstStockTransSerial() {
            String original = null;
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode",
                        "mbccs_getLstStockTransSerial");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getLstStockTransSerial>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<String, String>();
                paramToken.put("token", Session.getToken());
                // paramToken.put("toOwnerCode", Session.userName);
                paramToken.put("stockTransId",
                        String.valueOf(stockTransDetail.getStockTransId()));
                paramToken.put("stockModelId",
                        String.valueOf(stockTransDetail.getStockModelId()));

                rawData.append(input.buildXML(paramToken));

                rawData.append("</input>");
                rawData.append("</ws:getLstStockTransSerial>");

                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope,
                        Constant.BCCS_GW_URL, getActivity(),
                        "mbccs_getLstStockTransSerial");

                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("Responseeeeeeeeee", original);

                Serializer serializer = new Persister();
                ParseOuput parseOuput = serializer.read(ParseOuput.class,
                        original);
                if (parseOuput != null) {
                    errorCode = parseOuput.getErrorCode();
                    description = parseOuput.getDescription();
                }

                return parseOuput;
            } catch (Exception e) {
                Log.i("getLstStockTransSerial", e.toString());
            }
            return null;
        }

    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @SuppressLint("DefaultLocale")
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String msg = intent.getStringExtra("msg_body");
                String[] result = msg.split(";", 5);
                if (result.length > 3) {
                    Session.setToken(result[0]);
                    String errorCode = result[3];

                    String syntax = result[1];
                    Log.d("syntax", syntax);

                    String message = getResources().getString(
                            R.string.sale_fail);
                    if (syntax.equalsIgnoreCase("CFN")) {
                        if (errorCode.equals("0")) {
                            // Thuc hien ban hang thanh cong
                            Toast.makeText(
                                    getActivity(),
                                    getResources().getString(
                                            R.string.confirmNoteOk),
                                    Toast.LENGTH_SHORT).show();

                            ConfirmNoteBusiness.updateStockTrans(getActivity(),
                                    stockTransAction.getStockTransId());
                            GetMaxOraRowScnDal requestSync = new GetMaxOraRowScnDal(
                                    getActivity());
                            requestSync.open();
                            ArrayList<String> lstTabelName = new ArrayList<String>();
                            lstTabelName.add("stock_model");
                            lstTabelName.add("stock_card");
                            lstTabelName.add("stock_kit");
                            lstTabelName.add("stock_handset");
                            lstTabelName.add("stock_total");
                            lstTabelName.add("stock_sim");
                            lstTabelName.add("stock_trans");
                            lstTabelName.add("stock_trans_action");
                            lstTabelName.add("stock_trans_detail");
                            lstTabelName.add("stock_trans_serial");
                            requestSync.updateSyncStatus(lstTabelName);
                            requestSync.close();
                            Intent i = new Intent();

                            i.putExtra("stockTransId",
                                    stockTransAction.getStockTransId());
                            getTargetFragment().onActivityResult(
                                    getTargetRequestCode(), Activity.RESULT_OK,
                                    i);
                            getActivity().onBackPressed();
                        } else {
                            OnClickListener onclick = null;
                            if (errorCode
                                    .equalsIgnoreCase(Constant.INVALID_TOKEN)) {
                                onclick = new OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(
                                                getActivity(),
                                                LoginActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                                | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                                };
                            }
                            if (result[4] != null && !result[4].isEmpty()) {
                                message = result[4];
                            }
                            CommonActivity
                                    .createAlertDialog(
                                            getActivity(),
                                            message,
                                            getResources().getString(
                                                    R.string.app_name), onclick)
                                    .show();
                        }
                    }
                    if (dialogSendSMS != null && dialogSendSMS.isShowing()) {
                        dialogSendSMS.dismiss();
                    }
                }
            } catch (Exception e) {
                if (dialogSendSMS != null && dialogSendSMS.isShowing()) {
                    dialogSendSMS.dismiss();
                }
            }

        }
    };

    class SynchronizationForService extends
            AsyncTask<ArrayList<OjectSyn>, Void, String> {
        ProgressDialog progress;
        String Tag = SynchronizationForService.class.getName();
        final BCCSGateway input = new BCCSGateway();
        String response = null;
        String original = null;
        String errorCode = "";
        String description = "";
        // final XmlDomParse parse = new XmlDomParse();

        private Context context = null;
        ArrayList<String> listgetdata = null;
        ArrayList<OjectSyn> lisSynchoronizationObjects = null;
        private GetMaxOraRowScnDal dal = null;
        Long timeStart = System.currentTimeMillis();

        public SynchronizationForService(Context context,
                                         ArrayList<OjectSyn> arrObjects) {
            this.context = context;
            this.progress = new ProgressDialog(this.context);
            this.progress.setMessage(getResources()
                    .getString(R.string.waitting));
            progress.setCancelable(false);
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
            this.lisSynchoronizationObjects = arrObjects;
            timeStart = System.currentTimeMillis();
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(ArrayList<OjectSyn>... arg0) {
            if (Session.isSync && !Session.isSyncRunning) {
                Session.isSyncRunning = true;
                return sendListRequest(lisSynchoronizationObjects);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            this.progress.dismiss();
            // parse reponse
            if (result != null) {
                InputStream in = null;
                try {

                    Log.d(Tag, result);
                    File file = new File(result);
                    in = new FileInputStream(file);
                    SynchronizeHandler handler = (SynchronizeHandler) CommonActivity
                            .parseXMLHandler(new SynchronizeHandler(),
                                    new InputSource(in));
                    file.delete();
                    if (handler != null) {
                        if (handler.getItem() != null
                                && Constant.INVALID_TOKEN.equals(handler
                                .getItem().getErrorCode())) {
                            Session.isSync = false;
                            return;
                        }
                        ArrayList<SynchronizeDataObject> lstObject = handler
                                .getData();
                        Long timeStart = System.currentTimeMillis();
                        if (lstObject != null && !lstObject.isEmpty()) {
                            dal = new GetMaxOraRowScnDal(context);
                            dal.syncChange(lstObject);
                        }
                        Long duration = System.currentTimeMillis() - timeStart;
                        Log.e(Tag, "time insert old: " + duration);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (dal != null) {
                        dal.close();
                    }
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                }
            }
            Long duration = System.currentTimeMillis() - timeStart;
            Log.e("SYNC_DATA_AUTO", "total time: " + duration);
            Session.isSyncRunning = false;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        // method add send list request to server
        public String sendListRequest(ArrayList<OjectSyn> araObjects) {
            try {
                // BCCSGateway input = new BCCSGateway();
                // input.addValidateGateway("username", Constant.BCCSGW_USER);
                // input.addValidateGateway("password", Constant.BCCSGW_PASS);
                // input.addValidateGateway("wscode",
                // "mbccs_syncChangeRecordDatabase");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.smartphonev2.bss.viettel.com/\">");

                rawData.append("<soapenv:Header/>");
                rawData.append("<soapenv:Body>");
                rawData.append("<ws:syncChangeRecordDatabaseNewWay>");

                rawData.append("<syncInput>");
                HashMap<String, String> param = new HashMap<String, String>();
                HashMap<String, String> paramToken = new HashMap<String, String>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));
                // syncInput

                if (araObjects.size() > 0) {
                    for (OjectSyn item : araObjects) {
                        HashMap<String, String> rawDataItem = new HashMap<String, String>();
                        rawDataItem.put("oraRowscn", item.getMax_ora_rowscn());
                        rawDataItem.put("table", item.getTable_name());
                        param.put("lstSyncBase", input.buildXML(rawDataItem));
                        rawData.append(input.buildXML(param));
                    }
                }
                rawData.append("</syncInput>");
                rawData.append("</ws:syncChangeRecordDatabaseNewWay>");
                rawData.append("</soapenv:Body>");
                rawData.append("</soapenv:Envelope>");

                // String envelope = input.buildInputGatewayWithRawData(rawData
                // .toString());
                String envelope = rawData.toString();
                Log.e("Send evelop", envelope);
                String fileName = input.sendRequestWriteToFile(envelope,
                        Constant.WS_SYNCHRONIZE_DATA,
                        Constant.SYNCHRONIZE_AUTO_FILE_NAME);
                // Log.e("Responseeeeeeeeee", response);
                // CommonOutput output = input.parseGWResponse(response);
                // if (!output.getError().equals("0")) {
                // errorMessage = output.getDescription();
                // return Constant.ERROR_CODE;
                // }
                // original = output.getOriginal();
                return fileName;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    // Ham lay confirmNote
    private class ConfirmNoteIM2Asyn extends AsyncTask<String, Void, String> {

        ProgressDialog progress;
        private Context context = null;
        String errorCode = "";
        String description = "";

        public ConfirmNoteIM2Asyn(Context context) {
            this.context = context;
            this.progress = new ProgressDialog(this.context);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(
                    R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected String doInBackground(String... arg0) {
            return confirmNoteIM2();
        }

        @Override
        protected void onPostExecute(String result) {

            progress.dismiss();

            if ("0".equals(errorCode)) {
                if (description != null && !description.isEmpty()) {
                    Dialog dialog = CommonActivity.createAlertDialog(
                            getActivity(), description, getResources()
                                    .getString(R.string.app_name));
                    dialog.show();
                } else {
                    Dialog dialog = CommonActivity.createAlertDialog(
                            getActivity(),
                            getResources().getString(R.string.confirmNoteOk),
                            getResources().getString(R.string.app_name));
                    dialog.show();
                }

                LookUpStockBusiness.updatePlusStockTotal(getActivity(), lstItem);

                Intent i = new Intent();
                i.putExtra("stockTransId", stockTransAction.getStockTransId());

                getTargetFragment().onActivityResult(getTargetRequestCode(),
                        Activity.RESULT_OK, i);
                getActivity().onBackPressed();
            } else {
                if (Constant.INVALID_TOKEN2.equals(errorCode)
                        && description != null && !description.isEmpty()) {

                    Dialog dialog = CommonActivity
                            .createAlertDialog(
                                    (Activity) context,
                                    description,
                                    context.getResources().getString(
                                            R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = context.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(
                            getActivity(), description, getResources()
                                    .getString(R.string.app_name));
                    dialog.show();

                }
            }
        }

        private String confirmNoteIM2() {
            String original = null;
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode",
                        "mbccs_confirmNoteIM2");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:confirmNoteIM2>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<String, String>();
                paramToken.put("token", Session.getToken());

//				Log.d("debug", stockTransAction.getExportDate());
//				Log.d("debug getCreateDatetime", stockTransAction.getCreateDatetime());
                paramToken.put(
                        "createExpDate",
                        DateTimeUtils.convertDateTimeToString(
                                DateTimeUtils.convertStringToTime(stockTransAction.getCreateDatetime().substring(0, 10), "yyyy-MM-dd"), "dd/MM/yyyy"));
                paramToken.put("expNoteCode", stockTransAction.getExpCode());
                paramToken.put("impCode", tvImpCode.getText().toString());
                paramToken.put("stockTransId", stockTransAction.getStockTransId() + "");

                rawData.append(input.buildXML(paramToken));

                rawData.append("</input>");
                rawData.append("</ws:confirmNoteIM2>");

                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope,
                        Constant.BCCS_GW_URL, getActivity(),
                        "mbccs_confirmNoteIM2");

                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("Responseeeeeeeeee", original);

                Serializer serializer = new Persister();
                ParseOuput parseOuput = serializer.read(ParseOuput.class,
                        original);
                if (parseOuput != null) {
                    errorCode = parseOuput.getErrorCode();
                    description = parseOuput.getDescription();
                }

            } catch (Exception e) {
                Log.i("getLstStockTransSerial", e.toString());
            }
            return errorCode;
        }

    }

    OnClickListener moveLogInAct = new OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
            MainActivity.getInstance().finish();

        }
    };

    private Dialog selectSerialDialog;
    private AvailableSerialAdapter avaiLableSerialAdapter;
    private Boolean isStockHandset = true;

    private void showDialogSerial(List<Serial> lstSerials) {
        selectSerialDialog = new Dialog(getActivity());
        selectSerialDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        selectSerialDialog.setContentView(R.layout.view_serial_layout);

        TextView tvDialogTitle = (TextView) selectSerialDialog.findViewById(R.id.tvDialogTitle);
        tvDialogTitle.setText(getResources().getString(R.string.danhsach_serial));


        Boolean isStockCard;
        if (Constant.PRODUCT_OFFER_TYPE.CARD.equals(stockTransDetail
                .getProdOfferTypeId())) {
            isStockCard = true;
        } else {
            isStockCard = false;
        }

        if (Constant.PRODUCT_OFFER_TYPE.HANDSET.equals(stockTransDetail
                .getProdOfferTypeId())) {
            isStockHandset = true;
        } else {
            isStockHandset = false;
        }

        ArrayList<String> lstSerial = new ArrayList<String>();
        for (Serial serial : lstSerials) {
            if (serial.getFromSerial().equals(serial.getToSerial())) {
                lstSerial.add(serial.getFromSerial());
            } else {
                lstSerial.add(serial.getFromSerial() + " - "
                        + serial.getToSerial());
            }
        }

        avaiLableSerialAdapter = new AvailableSerialAdapter(getActivity(),
                SaleCommons.getRangeSerial(lstSerial, false), isStockCard,
                isStockHandset);
        ListView lvAvailable = (ListView) selectSerialDialog
                .findViewById(R.id.lvAvailableSerial);
        lvAvailable.setAdapter(avaiLableSerialAdapter);
        selectSerialDialog.show();
    }

}
