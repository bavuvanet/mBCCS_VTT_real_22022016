package com.viettel.bss.viettelpos.v4.task;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.viettel.bss.viettelpos.v4.bo.StationBO;
import com.viettel.bss.viettelpos.v4.cc.object.CCOutput;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Toancx on 2/17/2017.
 */

public class AsynTaskLookupRevenueBTS extends AsyncTaskCommon<String, Void, List<StationBO>>{
    private String lookupDate;
    private String criteria;
    private Integer page = 1;
    private Integer size = 20;
    private String station;

    public AsynTaskLookupRevenueBTS(Activity context,
                                    OnPostExecuteListener<List<StationBO>> listener,
                                    View.OnClickListener moveLogInAct,
                                    String lookupDate, String criteria, String station,
                                    Integer page, Integer size, boolean loadMore) {
        super(context, listener, moveLogInAct);
        this.lookupDate = lookupDate;
        this.criteria = criteria;
        this.page = page;
        this.size = size;
        this.station = station;
        if(loadMore) {
            this.progress.dismiss();
        }
    }

    @Override
    protected List<StationBO> doInBackground(String... args) {
        return lookupRevenueBTS();
    }

    private List<StationBO> lookupRevenueBTS(){
        List<StationBO> lstData = new ArrayList<>();
        String original = "";
        try{
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_lookupRevenueBTS");

            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:lookupRevenueBTS>");
            rawData.append("<input>");

            HashMap<String, String> paramToken = new HashMap<String, String>();
            paramToken.put("token", Session.getToken());
            paramToken.put("currentTimeMillis", lookupDate);
            paramToken.put("criteria", criteria);
            paramToken.put("stationCode", station);
            paramToken.put("page", page.toString());
            paramToken.put("size", size.toString());
            rawData.append(input.buildXML(paramToken));

            rawData.append("</input>");
            rawData.append("</ws:lookupRevenueBTS>");
            Log.i("RowData", rawData.toString());

            String envelope = input.buildInputGatewayWithRawData(rawData.toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, mActivity, "mbccs_lookupRevenueBTS");
            Log.i("Responseeeeeeeeee", response);
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();

            Serializer serializer = new Persister();
            CCOutput parseOuput = serializer.read(CCOutput.class,
                    original);
            if(parseOuput != null){
                errorCode = parseOuput.getErrorCode();
                description = parseOuput.getDescription();

                lstData = parseOuput.getLstRevenueStation();
            }
        }catch (Exception e) {
            Log.d("lookupRevenueBTS", e.toString());
        }
        return lstData;
    }
}
