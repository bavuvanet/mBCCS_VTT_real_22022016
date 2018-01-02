package com.viettel.bss.viettelpos.v4.customer.manage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter.GetListCustomerRegisterInfoAdapter;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustIdentityDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustomerDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.channel.activity.FragmentMenuHome;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.commons.auto.AutoCompleteUtil;
import com.viettel.bss.viettelpos.v4.commons.auto.AutoConst;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ObjAPStockModelBeanBO;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ObjThongTinHH;
import com.viettel.bss.viettelpos.v4.connecttionService.listener.ExpandableHeightListView;
import com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.customview.InstantAutoComplete;
import com.viettel.bss.viettelpos.v4.customview.adapter.RegisterInfoAdapter;
import com.viettel.bss.viettelpos.v4.customview.obj.CustomerObj;
import com.viettel.bss.viettelpos.v4.customview.obj.LoaiGiayToObj;
import com.viettel.bss.viettelpos.v4.dal.CacheDatabaseManager;
import com.viettel.bss.viettelpos.v4.infrastrucure.adapter.RegisterAdapter;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ConnectionOrder;
import com.viettel.bss.viettelpos.v4.sale.object.SaleOutput;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class RegisterInfoFragment extends Fragment implements OnClickListener, AutoConst, OnItemClickListener {

    private String permission = ";menu.register.mobile2;";
    private View mView;
    private InstantAutoComplete mEdtNumberPaper;
    private InstantAutoComplete mEdtNumberSubscribers;
    private Activity mActivity;

    private String mIdNo;// so cnnd
    private String mIdType;// loai giay to
    private int mPositionLoaiGiayTo;
    private String mISDN;// so thue bao

    private final BCCSGateway mBccsGateway = new BCCSGateway();
    static ArrayList<LoaiGiayToObj> mLoaiGiayTo = new ArrayList<>();
    static ArrayList<CustomerDTO> arrayListCustomer = new ArrayList<>();

    private String mNgaySinhHS;
    private ActionBar mActionBar;
    private TextView txtNameActionBar;
    private SubscriberDTO sub;
    private String CHECK_REGISTER_INFO_OMNICHANNEL = "";
    private ConnectionOrder connectionOrder = null;

    // omni
    private String omniProcessId = "";
    private String omniTaskId = "";



    // private String address = "";
    // private String province = "";
    // private String precinct = "";
    // private String district = "";
    // private String home = "";
	private ExpandableHeightListView lvCustomer;
    Spinner mSpTypeNo;

    @Override
    public void onResume() {
        Log.d("RegisterInfoFragment", "onResume RegisterInfoFragment");
        super.onResume();
        MainActivity.getInstance().setTitleActionBar(R.string.customer_new);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("RegisterInfoFragment", "onCreateView RegisterInfoFragment");
        mActivity = getActivity();
//        InfrastrucureDB mInfrastrucureDB = new InfrastrucureDB(getActivity());
//        mLoaiGiayTo = mInfrastrucureDB.getListLoaiGiayToBCCS2();

        Bundle mBundle = getArguments();
        if (mBundle != null) {
            CHECK_REGISTER_INFO_OMNICHANNEL = mBundle.getString("CHECK_REGISTER_INFO_OMNICHANNEL", "");
            connectionOrder = (ConnectionOrder) mBundle.getSerializable("connectionOrder");
            // omni
            this.omniTaskId = mBundle.getString("omniTaskId", omniTaskId);
            this.omniProcessId = mBundle.getString("omniProcessId", omniProcessId);

        }

        if (mView == null) {
            mView = inflater.inflate(R.layout.register_info_fragment, container, false);
            Calendar cal = Calendar.getInstance();
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int month = cal.get(Calendar.MONTH);
            int year = cal.get(Calendar.YEAR);

            mEdtNumberPaper = (InstantAutoComplete) mView
                    .findViewById(R.id.edtNumberPaper);
            mEdtNumberSubscribers = (InstantAutoComplete) mView
                    .findViewById(R.id.edtNumberSubscribers);

            AutoCompleteUtil.getInstance(getActivity()).autoComplete(AUTO_NUMBER_PAPER, mEdtNumberPaper);
            AutoCompleteUtil.getInstance(getActivity()).autoComplete(AUTO_NUMBER_SUB, mEdtNumberSubscribers);

            lvCustomer = (ExpandableHeightListView) mView.findViewById(R.id.listcustomer);
            lvCustomer.setExpanded(true);
            lvCustomer.setOnItemClickListener(this);

            Button mBtnCheck = (Button) mView.findViewById(R.id.btnKiemtra);
            mSpTypeNo = (Spinner) mView.findViewById(R.id.spTypeNo);
            GetMappingCustIdentityUsageAsyn getMappingCustIdentityUsageAsyn = new GetMappingCustIdentityUsageAsyn(getActivity(), new OnPostExecuteListener<ArrayList<CustIdentityDTO>>() {
                @Override
                public void onPostExecute(ArrayList<CustIdentityDTO> result, String errorCode, String description) {
                    if (!CommonActivity.isNullOrEmptyArray(result)) {
                        for (CustIdentityDTO item : result) {
                            LoaiGiayToObj loaiGiayToObj = new LoaiGiayToObj(item.getIdType(), item.getIdTypeName());
                            mLoaiGiayTo.add(loaiGiayToObj);
                        }
                    }

                    RegisterInfoAdapter registerInfoAdapter = new RegisterInfoAdapter(
                            mLoaiGiayTo, mActivity);
                    mSpTypeNo.setAdapter(registerInfoAdapter);

                    //dang ky thong tin, chuc nang omnichannel
                    if (!CommonActivity.isNullOrEmpty(CHECK_REGISTER_INFO_OMNICHANNEL)) {
                        if (!CommonActivity.isNullOrEmpty(connectionOrder)) {
                            for (int i = 0; i < mLoaiGiayTo.size(); i++) {
                                if ("CMT".equals(mLoaiGiayTo.get(i).getParValue())) {
                                    mIdType = mLoaiGiayTo.get(i).getParType();
                                    mPositionLoaiGiayTo = i;
                                    mSpTypeNo.setSelection(i);
                                }
                            }

                            if (!CommonActivity.isNullOrEmpty(connectionOrder.getCustomer().getIdNo())) {
                                mEdtNumberPaper.setText(connectionOrder.getCustomer().getIdNo());
                                mIdNo = connectionOrder.getCustomer().getIdNo();

                            }
                            if (!CommonActivity.isNullOrEmpty(connectionOrder.getIsdn())) {
                                mEdtNumberSubscribers.setText(connectionOrder.getIsdn());
                                mISDN = connectionOrder.getIsdn();
                            }

                            mEdtNumberSubscribers.setEnabled(false);


                            if (CommonActivity.isNetworkConnected(getActivity())) {
                                new GetLisCustomerTask(mIdNo, mIdType, mISDN).execute();
                            } else {
                                CommonActivity.createAlertDialog(getActivity(),
                                        getResources().getString(R.string.errorNetwork),
                                        getResources().getString(R.string.app_name)).show();
                            }
                        } else {
                            CommonActivity.createAlertDialog(mActivity,
                                    R.string.customer_null, R.string.app_name).show();
                        }
                    }
                }
            });
            getMappingCustIdentityUsageAsyn.execute("VIE");

            mSpTypeNo.setOnItemSelectedListener(new OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1,
                                           int position, long arg3) {
                    mIdType = mLoaiGiayTo.get(position).getParType();
                    mPositionLoaiGiayTo = position;
                    Log.d("TAG mIdType", "mIdType :" + mIdType + ", ");
                    if (mIdType.equals("ID")) {
                        mEdtNumberPaper
                                .setInputType(InputType.TYPE_CLASS_NUMBER);
                    } else {
                        mEdtNumberPaper.setInputType(InputType.TYPE_CLASS_TEXT);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {

                }
            });
//            try {
//                mInfrastrucureDB.close();
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
            mBtnCheck.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    checkInfo();
                }
            });

            // Test
            // mEdtNumberPaper.setText("878787878729");
            // mEdtNumberSubscribers.setText("989507712");

        }
        return mView;
    }

    private void checkInfo() {
        mIdNo = mEdtNumberPaper.getText().toString().trim();
        mISDN = CommonActivity.checkStardardIsdn(mEdtNumberSubscribers.getText().toString().trim());

        if (mIdNo.equals("")) {

            CommonActivity.createAlertDialog(mActivity,
                    R.string.chua_nhap_giay_to, R.string.app_name).show();
            return;
        }
        if (mISDN.equals("")) {
            CommonActivity.createAlertDialog(mActivity,
                    R.string.chua_nhap_thue_bao, R.string.app_name).show();
            return;
        }

        boolean isCharSpecical = StringUtils.CheckCharSpecical(mIdNo);
        if (isCharSpecical) {
            // Toast.makeText(getActivity(),
            // getString(R.string.checkcharspecical), Toast.LENGTH_LONG)
            // .show();
            CommonActivity.createAlertDialog(mActivity,
                    R.string.checkcharspecical, R.string.app_name).show();
            return;
        }

        AutoCompleteUtil.getInstance(getActivity()).addToSuggestionList(AUTO_NUMBER_PAPER, mEdtNumberPaper.getText().toString());
        AutoCompleteUtil.getInstance(getActivity()).addToSuggestionList(AUTO_NUMBER_SUB, mEdtNumberSubscribers.getText().toString());

        AutoCompleteUtil.getInstance(getActivity()).autoComplete(AUTO_NUMBER_PAPER, mEdtNumberPaper);
        AutoCompleteUtil.getInstance(getActivity()).autoComplete(AUTO_NUMBER_SUB, mEdtNumberSubscribers);

        boolean isNetwork = CommonActivity.isNetworkConnected(getActivity());
        if (isNetwork) {
            new GetLisCustomerTask(mIdNo, mIdType, mISDN).execute();
        } else {
            CommonActivity.createAlertDialog(getActivity(),
                    getResources().getString(R.string.errorNetwork),
                    getResources().getString(R.string.app_name)).show();
        }

    }

    public class GetLisCustomerTask extends AsyncTask<Void, Void, String> {

        private final String mIdNo;
        private final String mIdType;
        private final String mISDN;
        private final ProgressDialog dialog;

        public GetLisCustomerTask(String mIdNo, String mIdType, String mISDN) {
            this.mIdNo = mIdNo;
            this.mIdType = mIdType;
            this.mISDN = mISDN;
            dialog = new ProgressDialog(getActivity());
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage(getResources().getString(R.string.waitting));
            this.dialog.setCancelable(false);
            this.dialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            String original = null;

            // boolean isNetwork = CommonActivity
            // .isNetworkConnected(getActivity());
            // if (isNetwork) {
            original = requestSevice(mIdNo, mIdType, mISDN);
            // } else {
            // }
            return original;
        }

        @Override
        protected void onPostExecute(String result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if (result == null || result.equals("")) {
                CommonActivity.createAlertDialog(getActivity(),
                        getResources().getString(R.string.errorNetwork),
                        getResources().getString(R.string.app_name)).show();
            } else {
                try {
                    Serializer serializer = new Persister();
                    SaleOutput saleOutput = serializer.read(SaleOutput.class,
                            result);

                    if ("0".equals(saleOutput.getErrorCode())) {
                        arrayListCustomer = saleOutput.getLstCustomerDTO();
                        sub = saleOutput.getSubscriberDTO();
                        if (sub.getCustId() != null) {
                            CommonActivity.createErrorDialog(getActivity(), getString(R.string.sub_registed, sub.getIsdn()), "1").show();
                            return;
                        }

                        if (arrayListCustomer != null
                                && arrayListCustomer.size() > 0) {
                            // show list khach hang
                            showListCustomer(arrayListCustomer);
                        } else {
                            // next from dang ky
                            nextFromRegisterNew(null, 0);
                        }
                    } else {
                        String description = saleOutput.getDescription();
                        if (CommonActivity.isNullOrEmpty(description)) {
                            description = getString(
                                    R.string.fails_not_description,
                                    getString(R.string.getListCustomerDKTT));
                        }

                        CommonActivity.createErrorDialog(mActivity,
                                description, saleOutput.getErrorCode(), permission).show();
                    }

                } catch (Exception e) {
                    CommonActivity.createExceptionDialog(mActivity, e).show();
                    Log.e("exception", "dddddddddd", e);
                }
            }
            Log.e("TAG6", "result List Customer" + result);
        }
    }
	private class AsynGetCustomerByCustId extends AsyncTask<String, Void, CustomerDTO> {

        private String errorCode;
        private String description;
        private final Context context;
        private final ProgressDialog progress;
        private final CustomerDTO mCustomerDTO;

        public AsynGetCustomerByCustId(Context mContext, CustomerDTO customerDTO) {
            this.context = mContext;
            this.mCustomerDTO = customerDTO;
            this.progress = new ProgressDialog(this.context);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected CustomerDTO doInBackground(String... params) {
            return getCustomerByCustId(mCustomerDTO.getCustId() + "");
        }

        @Override
        protected void onPostExecute(CustomerDTO result) {
            progress.dismiss();
            super.onPostExecute(result);
            if ("0".equals(errorCode)) {
                // thong tin hop dong cu
                if (result != null && result.getCustId() != null) {
                    nextFromRegisterNew(result, 1);
                } else {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notDetailCus),
                            getActivity().getString(R.string.app_name)).show();
                }

            } else {
                if (Constant.INVALID_TOKEN2.equals(errorCode)) {
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                            context.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = context.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                            getResources().getString(R.string.app_name));
                    dialog.show();
                }
            }
        }

        private CustomerDTO getCustomerByCustId(String custId) {

            CustomerDTO customerDTO = new CustomerDTO();
            String original = "";
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

                rawData.append("</input>");
                rawData.append("</ws:getCustomerByCustId>");
                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
                        "mbccs_getCustomerByCustId");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.d("original", "12345 : " + original);
                // ====== parse xml ===================

                Serializer serializer = new Persister();
                ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
                if (parseOuput != null) {
                    errorCode = parseOuput.getErrorCode();
                    description = parseOuput.getDescription();
                    customerDTO = parseOuput.getCustomerDTO();
                }

                Log.d("original", "12345 getIdIssuePlace : " + customerDTO.getListCustIdentity().get(0).getIdIssuePlace());
                Log.d("TAG obj", "customerDTO.getName :" + customerDTO.getName());
                return customerDTO;

            } catch (Exception e) {
                Log.e("getCustomerByCustId + exception", e.toString(), e);
            }
            return customerDTO;

        }

    }

    private final OnClickListener moveLogInAct = new OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
            MainActivity.getInstance().finish();

        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            CustomerDTO customerObj = (CustomerDTO) parent.getItemAtPosition(position);

                if(CommonActivity.isNullOrEmpty(customerObj.getProvince()) || CommonActivity.isNullOrEmpty(customerObj.getDistrict()) || CommonActivity.isNullOrEmpty(customerObj.getPrecinct())){
                CommonActivity.createAlertDialog(getActivity(), getString(R.string.checkdiabannew), getString(R.string.app_name)).show();
                }else{
                new AsynGetCustomerByCustId(getActivity(), customerObj).execute();
            }
        }

    private void showListCustomer(final ArrayList<CustomerDTO> listCustomer) {
        GetListCustomerRegisterInfoAdapter adaGetListCustomerBccsAdapter = new GetListCustomerRegisterInfoAdapter(getActivity(), listCustomer, imageListenner);
        lvCustomer.setAdapter(adaGetListCustomerBccsAdapter);

        if (adaGetListCustomerBccsAdapter != null) {
            adaGetListCustomerBccsAdapter.notifyDataSetChanged();
        }

       /* AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.dialog_list_customer, null);

        builder.setView(dialogView);
        final Dialog dialogLockBoxInfo = builder.create();

        Button btnClose = (Button) dialogView.findViewById(R.id.btn_colse);
        ListView lvListLockBox = (ListView) dialogView
                .findViewById(R.id.lvLockBoxInfo);
        RegisterAdapter registerAdapter = new RegisterAdapter(getActivity(),
                listCustomer);
        lvListLockBox.setAdapter(registerAdapter);

        // tvNumberPortService.setText(locationService.getCountNetTV());
        btnClose.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialogLockBoxInfo.dismiss();
            }
        });
        lvListLockBox.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                CustomerDTO customerObj = listCustomer.get(position);
                if (CommonActivity.isNullOrEmpty(customerObj.getProvince()) || CommonActivity.isNullOrEmpty(customerObj.getDistrict()) || CommonActivity.isNullOrEmpty(customerObj.getPrecinct())) {
                    CommonActivity.createAlertDialog(getActivity(), getString(R.string.checkdiabannew), getString(R.string.app_name)).show();
                } else {
                    Log.d("TAG obj", "obj :" + customerObj);
                    nextFromRegisterNew(customerObj, 1);
                }


                dialogLockBoxInfo.dismiss();
            }
        });

        dialogLockBoxInfo.show();*/
    }
    private View.OnClickListener imageListenner = new OnClickListener() {
        @Override
        public void onClick(View v) {
            /*Object obj = v.getTag();
            if (obj instanceof CustomerDTO) {
                CustomerDTO customerObj = (CustomerDTO) obj;
                if (customerObj != null) {
                    Log.e("TAG6", "customerDTO" + customerObj);
                    if (CommonActivity.isNullOrEmpty(customerObj.getProvince()) || CommonActivity.isNullOrEmpty(customerObj.getDistrict()) || CommonActivity.isNullOrEmpty(customerObj.getPrecinct())) {
                        CommonActivity.createAlertDialog(getActivity(), getString(R.string.checkdiabannew), getString(R.string.app_name)).show();
                    } else {
                        //  nextFromRegisterNew(customerObj, 1);
                        new AsynGetCustomerByCustId(getActivity(), customerObj).execute();
                    }
                }
            }*/
        }
    };

    public ArrayList<CustomerObj> parseResultThongTinHH(String result) {
        ArrayList<CustomerObj> arrayList = new ArrayList<>();

        ArrayList<ObjThongTinHH> arrayListThongTinHH = new ArrayList<>();
        ArrayList<ObjAPStockModelBeanBO> arrayAPStockModelBeanBO = new ArrayList<>();

        if (result != null) {
            try {
                Log.e("TAG33", "KH cu  + Constant.INVALID_TOKEN: " + result
                        + ", Token :" + Constant.INVALID_TOKEN);
                XmlDomParse domParse = new XmlDomParse();
                Document document = domParse.getDomElement(result);

                NodeList lstAPSaleModelBeanBO = document
                        .getElementsByTagName("lstAPSaleModelBeanBO");

                String saleServicesModelId = "";
                String stockTypeId = "";
                String stockTypeName = "";

                for (int i = 0; i < lstAPSaleModelBeanBO.getLength(); i++) {
                    Node mNode = lstAPSaleModelBeanBO.item(i);
                    Element element = (Element) mNode;
                    saleServicesModelId = domParse.getValue(element,
                            "saleServicesModelId");
                    stockTypeId = domParse.getValue(element, "stockTypeId");
                    stockTypeName = domParse.getValue(element, "stockTypeName");

                    NodeList lstAPStockModelBeanBO = document
                            .getElementsByTagName("lstAPStockModelBeanBO");
                    for (int j = 0; j < lstAPStockModelBeanBO.getLength(); j++) {
                        String stockTypeIdModelBeanBO = domParse.getValue(
                                element, "stockTypeId");
                        String stockModelId = domParse.getValue(element,
                                "stockModelId");
                        String stockModelType = domParse.getValue(element,
                                "stockModelType");
                        String stockModelName = domParse.getValue(element,
                                "stockModelName");
                        String quantity = domParse
                                .getValue(element, "quantity");
                        String saleTransDetailId = domParse.getValue(element,
                                "saleTransDetailId");
                        String saleServicesDetailId = domParse.getValue(
                                element, "saleServicesDetailId");
                        String saleTransId = domParse.getValue(element,
                                "saleTransId");
                        ObjAPStockModelBeanBO objAPStockModelBeanBO = new ObjAPStockModelBeanBO(
                                stockTypeIdModelBeanBO, stockModelId,
                                stockModelType, stockModelName, quantity,
                                saleTransDetailId, saleServicesDetailId,
                                saleTransId);
                        arrayAPStockModelBeanBO.add(objAPStockModelBeanBO);
                    }

                    ObjThongTinHH objThongTinHH = new ObjThongTinHH(
                            saleServicesModelId, stockTypeId, stockTypeName,
                            arrayAPStockModelBeanBO);
                    arrayListThongTinHH.add(objThongTinHH);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return arrayList;
    }

    private String requestSevice(String mIdNo, String mIdType, String mISDN) {

        String reponse = null;
        String original = null;
        try {
            String xml = mBccsGateway.getXmlCustomer(
                    createXML(mIdNo, mIdType, mISDN),
                    "mbccs_getListCustomerByIdNoAndType");
            Log.e("TAG6", "xml mbccs_getListCustomerByIdNoAndType" + xml);

            reponse = mBccsGateway.sendRequest(xml, Constant.BCCS_GW_URL,
                    getActivity(), "mbccs_getListCustomerByIdNoAndType");
            Log.e("TAG6", "mbccs_getListCustomerByIdNoAndType" + reponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (reponse != null) {
            CommonOutput commonOutput;
            try {
                commonOutput = mBccsGateway.parseGWResponse(reponse);
                original = commonOutput.getOriginal();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return original;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void nextFromRegisterNew(CustomerDTO obj, int i) {
        Intent intent = null;
        //truong hop thuc hien chuc nang omnichannel
        if (!CommonActivity.isNullOrEmpty(CHECK_REGISTER_INFO_OMNICHANNEL)) {
            intent = new Intent(getActivity(), OmiRegisterNewFragment.class);
            intent.putExtra("connectionOrder", connectionOrder);
            intent.putExtra("customerOld", obj);
            intent.putExtra("subscriber", sub);
            intent.putExtra("mISDN", mISDN);
            intent.putExtra("mIdNo", mIdNo);
            intent.putExtra("idType", mIdType);
            intent.putExtra("mPositionLoaiGiayTo", mPositionLoaiGiayTo);

            // omni
            intent.putExtra("omniProcessId", omniProcessId);
            intent.putExtra("omniTaskId", omniTaskId);

            // i = 1 KH cu, i = 0 KH moi
            intent.putExtra("KHVersion", i);

            startActivity(intent);
        } else {

            RegisterNewFragment registerNewFragment = new RegisterNewFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("customerOld", obj);
            bundle.putSerializable("subscriber", sub);
            bundle.putSerializable("mISDN", mISDN);
            bundle.putSerializable("mIdNo", mIdNo);
            bundle.putSerializable("idType", mIdType);
            bundle.putSerializable("mPositionLoaiGiayTo", mPositionLoaiGiayTo);

            // omni
            bundle.putSerializable("omniProcessId", omniProcessId);
            bundle.putSerializable("omniTaskId", omniTaskId);

            // i = 1 KH cu, i = 0 KH moi
            bundle.putInt("KHVersion", i);

            registerNewFragment.setArguments(bundle);
            ReplaceFragment.replaceFragment(getActivity(), registerNewFragment, false);
        }
    }

    private String createXML(String mIdNo2, String mIdType2, String mISDN2)
            throws Exception {
        StringBuilder stringBuilder = new StringBuilder(
                "<ws:getListCustomerByIdNoAndType>");
        stringBuilder.append("<input>");
        stringBuilder.append("<token>" + Session.getToken() + "</token>");
        stringBuilder.append("<idNo>" + StringUtils.escapeHtml(mIdNo2.trim())
                + "</idNo>");
        stringBuilder.append("<idType>" + StringUtils.xmlEscapeText(mIdType2)
                + "</idType>");
        stringBuilder.append("<isdn>"
                + CommonActivity.checkStardardIsdn(mISDN2) + "</isdn>");
        stringBuilder.append("</input>");
        stringBuilder.append("</ws:getListCustomerByIdNoAndType>");
        return stringBuilder.toString();
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.btnHome:
                FragmentMenuHome fragmentMenuHome = new FragmentMenuHome();
                ReplaceFragment.replaceFragment(getActivity(), fragmentMenuHome,
                        true);
                break;
            case R.id.relaBackHome:
                getActivity().onBackPressed();
                break;

            default:
                break;
        }

    }

    private class GetMappingCustIdentityUsageAsyn extends android.os.AsyncTask<String, Void, ArrayList<CustIdentityDTO>> {
        private Context context = null;
        String errorCode = "";
        String description = "";
        private ProgressDialog progress;
        private OnPostExecuteListener listener;


        public GetMappingCustIdentityUsageAsyn(Context context, OnPostExecuteListener<ArrayList<CustIdentityDTO>> listener) {
            this.context = context;
            this.listener = listener;
            this.progress = new ProgressDialog(this.context);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }

        }

        @Override
        protected ArrayList<CustIdentityDTO> doInBackground(String... params) {
            return getMappingCustIdentityUsage(params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<CustIdentityDTO> result) {
            progress.dismiss();
            if ("0".equals(errorCode)) {
                listener.onPostExecute(result,errorCode, description );

            } else {
                if (Constant.INVALID_TOKEN2.equals(errorCode)) {
                    CommonActivity.createErrorDialog(mActivity,
                            description, errorCode, permission).show();
                } else {
                    if (description == null || description.equals("")) {
                        description = context.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(mActivity, description,
                            getResources().getString(R.string.app_name));
                    dialog.show();

                }
            }

        }

        private ArrayList<CustIdentityDTO> getMappingCustIdentityUsage(String currCusType) {
            ArrayList<CustIdentityDTO> lstTypePaper = new ArrayList<>();
            String original = null;
            try {
                lstTypePaper = new CacheDatabaseManager(context).getListTypePaperFromMap(currCusType);
                if (lstTypePaper != null && !lstTypePaper.isEmpty()) {
                    errorCode = "0";
                    return lstTypePaper;
                }
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_mappingCustIdentityUsage");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:mappingCustIdentityUsage>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));
                rawData.append("<currCustType>").append(currCusType);
                rawData.append("</currCustType>");
                rawData.append("</input>");
                rawData.append("</ws:mappingCustIdentityUsage>");
                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, mActivity,
                        "mbccs_mappingCustIdentityUsage");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.d("original", original);
                // ========parser xml get employ from server
                Serializer serializer = new Persister();
                ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
                if (parseOuput != null) {
                    errorCode = parseOuput.getErrorCode();
                    description = parseOuput.getDescription();
                    lstTypePaper = parseOuput.getLstCustIdentityDTOs();
                }

            } catch (Exception e) {
                Log.d("exception", e.toString());
            }
            new CacheDatabaseManager(context).insertTypePaper(currCusType, lstTypePaper);

            return lstTypePaper;
        }
    }
}
