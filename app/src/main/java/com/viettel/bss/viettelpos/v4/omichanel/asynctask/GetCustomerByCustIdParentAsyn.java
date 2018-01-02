package com.viettel.bss.viettelpos.v4.omichanel.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustIdentityDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustomerDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.HashMap;

/**
 * Created by hantt47 on 9/27/2017.
 */

public class GetCustomerByCustIdParentAsyn extends AsyncTask<String, Void, ParseOuput> {

    private final Context context;
    private final ProgressDialog progress;
    private String errorCode;
    private String description;
    private boolean ischeck = false;

    private CustIdentityDTO mCustIdentityDTO;

    private OnPostExecuteListener<ParseOuput> listener;

    public GetCustomerByCustIdParentAsyn(Context mContext, boolean mischeck, CustIdentityDTO custIdentityDTOEdit, OnPostExecuteListener<ParseOuput> listener) {
        this.context = mContext;
        this.ischeck = mischeck;
        this.progress = new ProgressDialog(this.context);
        // check font
        this.progress.setCancelable(false);
        this.progress.setMessage(context.getResources().getString(R.string.getdataing));
        if (!this.progress.isShowing()) {
            this.progress.show();
        }
        this.mCustIdentityDTO = custIdentityDTOEdit;
        this.listener = listener;
    }

    @Override
    protected ParseOuput doInBackground(String... params) {
        return getCustomerByCustId(params[0]);
    }

//    @Override
//    protected void onPostExecute(CustomerDTO result) {
//        progress.dismiss();
//        super.onPostExecute(result);
//        if ("0".equals(errorCode)) {
//            // thong tin hop dong cu
//            if (result != null && result.getCustId() != null) {
//
//                // truong hop sua thong tin khach hang
//                if (mCustIdentityDTO != null) {
//                    boolean isCheckCusType = false;
//                    if (!CommonActivity.isNullOrEmptyArray(arrCustTypeDTOs)) {
//                        for (CustTypeDTO custTypeDTO : arrCustTypeDTOs) {
//                            if (result.getCustType().equals(custTypeDTO.getCustType()) && "2".equals(custTypeDTO.getGroupType())) {
//                                isCheckCusType = true;
//                                break;
//                            }
//                        }
//                    }
//                    if (isCheckCusType) {
//                        CommonActivity.createAlertDialog(context, context.getString(R.string.edit_customer_busPermitNo), getActivity().getString(R.string.app_name)).show();
//                    } else {
//                        // sua thong tin khach hang
//                        mCustIdentityDTO.setCustomer(result);
//                        showEditCustomer(mCustIdentityDTO, isPSenTdO);
//                    }
//                } else {
//                    // xu ly thong tin khach hang dai dien cu cho nay
//                    // truong hop dai dien
//                    if (ischeck) {
//                        custIdentityDTO.setCustomer(result);
//                        showPopupInsertParent(custIdentityDTO);
//                    } else {
//                        // truogn hop ko phai dai dien
//                        custIdentityDTO.setCustomer(result);
//                        // truyen thong tin qua tab thong tin hop dong va thue
//                        // bao
//                        if (!CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer()) && !CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getListCustIdentity()))
//                            if ("BUS".equals(custIdentityDTO.getCustomer().getListCustIdentity().get(0).getIdType()) || "TIN".equals(custIdentityDTO.getCustomer().getListCustIdentity().get(0).getIdType())) {
//                                custIdentityDTO.setGroupType("2");
//                            }
//                        if (adaGetListCustomerBccsAdapter != null) {
//                            adaGetListCustomerBccsAdapter.notifyDataSetChanged();
//                        }
//                        CreateConnectMobileOmiActivity.instance.tHost.setCurrentTab(1);
//                    }
//                }
//            } else {
//                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notDetailCus),
//                        getActivity().getString(R.string.app_name)).show();
//            }
//
//        } else {
//            if (Constant.INVALID_TOKEN2.equals(errorCode)) {
//
//                Dialog dialog = CommonActivity.createAlertDialog(context, description,
//                        context.getResources().getString(R.string.app_name), moveLogInAct);
//                dialog.show();
//            } else {
//                if (description == null || description.isEmpty()) {
//                    description = context.getString(R.string.checkdes);
//                }
//                Dialog dialog = CommonActivity.createAlertDialog(activity, description,
//                        getResources().getString(R.string.app_name));
//                dialog.show();
//
//            }
//        }
//
//    }

    private ParseOuput getCustomerByCustId(String custId) {

        CustomerDTO customerDTO = new CustomerDTO();
        String original = "";
        ParseOuput parseOuput= null;
        try {

            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_getCustomerByCustId");
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:getCustomerByCustId>");
            rawData.append("<input>");
            HashMap<String, String> paramToken = new HashMap<>();
            paramToken.put("token", Session.getToken());
            rawData.append(input.buildXML(paramToken));

            rawData.append("<custId>").append(custId);
            rawData.append("</custId>");

//                KHCH_TT
            if (!CommonActivity.isNullOrEmpty(mCustIdentityDTO)) {
                rawData.append("<type>" + "KHCH_TT");
                rawData.append("</type>");
            } else {
                rawData.append("<type>" + "");
                rawData.append("</type>");
            }

            rawData.append("</input>");
            rawData.append("</ws:getCustomerByCustId>");
            Log.i("RowData", rawData.toString());

            String envelope = input.buildInputGatewayWithRawData(rawData.toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, context,
                    "mbccs_getCustomerByCustId");
            Log.i("Responseeeeeeeeee", response);
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();
            Log.d("original", original);
            // ====== parse xml ===================

            Serializer serializer = new Persister();
             parseOuput = serializer.read(ParseOuput.class, original);
            if (parseOuput != null) {
                errorCode = parseOuput.getErrorCode();
                description = parseOuput.getDescription();
//                isPSenTdO = parseOuput.getIsPSenTdO();
//                customerDTO = parseOuput.getCustomerDTO();
            }

            return parseOuput;

        } catch (Exception e) {
            Log.e("getCustomerByCustId + exception", e.toString(), e);
        }
        progress.dismiss();
        return parseOuput;

    }
}
