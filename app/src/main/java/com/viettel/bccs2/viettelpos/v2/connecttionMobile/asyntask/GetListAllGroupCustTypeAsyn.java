package com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.dal.CacheDatabaseManager;
import com.viettel.bss.viettelpos.v4.utils.Log;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by thinhhq1 on 3/31/2017.
 */

public class GetListAllGroupCustTypeAsyn extends AsyncTaskCommonSupper<String,Void,ArrayList<Spin>> {
    private Context mContext;
    private String TAG = GetListAllGroupCustTypeAsyn.class.getSimpleName();
    public GetListAllGroupCustTypeAsyn(Activity context, OnPostExecuteListener<ArrayList<Spin>> listener, View.OnClickListener moveLogInAct) {
        super(context, listener, moveLogInAct);
        this.mContext = context;
    }

    @Override
    protected ArrayList<Spin> doInBackground(String... params) {
        return getListAllGroupCustType();
    }

    private ArrayList<Spin> getListAllGroupCustType(){
        String original = "";
        ArrayList<Spin> lstGroup = new ArrayList<Spin>();
        try{

//            lstGroup = new CacheDatabaseManager(mContext).getListCustGroupInCacheBCCS();
//            if(!CommonActivity.isNullOrEmptyArray(lstGroup)){
//                errorCode = "0";
//                return  lstGroup;
//            }

            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_getListAllGroupCustType");
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:getListAllGroupCustType>");
            rawData.append("<input>");
            HashMap<String, String> paramToken = new HashMap<String, String>();
            paramToken.put("token", Session.getToken());
            rawData.append(input.buildXML(paramToken));
            rawData.append("</input>");
            rawData.append("</ws:getListAllGroupCustType>");
            android.util.Log.i("RowData", rawData.toString());

            String envelope = input.buildInputGatewayWithRawData(rawData.toString());
            android.util.Log.d("Send evelop", envelope);
            android.util.Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, mActivity, "mbccs_getListAllGroupCustType");
            android.util.Log.i("Responseeeeeeeeee", response);
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();

            Serializer serializer = new Persister();
            ParseOuput parseOuput = serializer.read(ParseOuput.class,
                    original);
            if(parseOuput != null){
                errorCode = parseOuput.getErrorCode();
                description = parseOuput.getDescription();
                lstGroup = parseOuput.getLstAllGroupCustType();
//                if(!CommonActivity.isNullOrEmptyArray(lstGroup)){
//                    new CacheDatabaseManager(mContext).insertCusGroupBCCS(lstGroup);
//                }
            }

        }catch (Exception e){
           Log.e(TAG + "-" + e.toString());
        }
        return lstGroup;
    }

}
