package com.viettel.bss.viettelpos.v4.work.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity.FragmentSearchSubChangeSim;
import com.viettel.bss.viettelpos.v3.connecttionService.activity.FragmentSubscriberInfo;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.ActivityCreateNewRequestMobileNew;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.FragmentConnectionMobileNew;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.SubTypeBean;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.ActivityCreateNewRequestHotLine;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentManagerChangeTech;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.HotLineReponseDetail;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ImageBO;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ReceiveRequestBean;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetAreaDal;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentManageSubscriber;
import com.viettel.bss.viettelpos.v4.sale.strategy.ChangeProductFragmentStrategy;
import com.viettel.bss.viettelpos.v4.sale.strategy.ManageSubscriberFragmentStrategy;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.work.adapter.GetListReceiverequestHotlineAdapter;
import com.viettel.bss.viettelpos.v4.work.adapter.ItemProcessHistoryAdapter;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;
import com.viettel.bss.viettelpos.v4.work.object.ProcessRequestBO;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class FragmentRequestChangeHotLine extends Fragment implements
        OnClickListener, OnItemClickListener {

    private EditText editusernameortel, edit_isdnacount;
    private ListView lvrequest;
    private Spinner spinner_hotlinetype;
    private String hotlineType = "";

    private ArrayList<ReceiveRequestBean> arrReceiveRequestBeans = new ArrayList<>();

	// thientv7 them list history
	private ArrayList<ProcessRequestBO> arrProcessRequestBOs = new ArrayList<>();
	private ItemProcessHistoryAdapter adapterHis;

    private GetListReceiverequestHotlineAdapter adapter = null;
    private LinearLayout lnttcus, lnaccount;

    private HotLineReponseDetail hotLineReponseDetail = new HotLineReponseDetail();
    private ArrayList<ImageBO> lstImageBOs = new ArrayList<>();

    private String checkRequest = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        if(bundle != null){
            checkRequest = bundle.getString("ID_HOTLINE_NEW","");
        }

        View mView = inflater.inflate(
                R.layout.connection_layout_request_hotline, container,
                false);
        unitView(mView);
        return mView;

    }

    private void initHotlineType() {
        final ArrayList<SubTypeBean> arrSubTypeBeans = new ArrayList<>();
        arrSubTypeBeans.add(new SubTypeBean(getString(R.string.allq), "0"));
        arrSubTypeBeans.add(new SubTypeBean(getString(R.string.hotline_type_1), "1"));
        arrSubTypeBeans.add(new SubTypeBean(getString(R.string.hotline_type_2), "2"));
        arrSubTypeBeans.add(new SubTypeBean(getString(R.string.hotline_type_8), "8"));
        arrSubTypeBeans.add(new SubTypeBean(getString(R.string.changeTechnology), "7"));
        arrSubTypeBeans.add(new SubTypeBean(getString(R.string.Chuyendoigoicuoc), "12"));
        if (arrSubTypeBeans != null && arrSubTypeBeans.size() > 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getActivity(),
                    android.R.layout.simple_dropdown_item_1line,
                    android.R.id.text1);
            for (SubTypeBean subTypeBean : arrSubTypeBeans) {
                adapter.add(subTypeBean.getName());
            }
            spinner_hotlinetype.setAdapter(adapter);
        }

        spinner_hotlinetype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                hotlineType = arrSubTypeBeans.get(arg2).getValue();
                switch (hotlineType) {
                    case "0":
                        lnttcus.setVisibility(View.VISIBLE);
                        lnaccount.setVisibility(View.VISIBLE);
                        break;
                    case "1":
                        lnttcus.setVisibility(View.GONE);
                        lnaccount.setVisibility(View.VISIBLE);
                        break;

                    case "8":
                    case "2":
                        lnttcus.setVisibility(View.VISIBLE);
                        lnaccount.setVisibility(View.GONE);
                        break;
                    case "7":
                        lnttcus.setVisibility(View.GONE);
                        lnaccount.setVisibility(View.VISIBLE);
                        break;
                    case "12":
                        lnttcus.setVisibility(View.GONE);
                        lnaccount.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

    }

    private void unitView(View mView) {
        editusernameortel = (EditText) mView
                .findViewById(R.id.editusernameortel);
        edit_isdnacount = (EditText) mView.findViewById(R.id.edit_isdnacount);
        Button btn_search_acc = (Button) mView.findViewById(R.id.btn_search_acc);
        btn_search_acc.setOnClickListener(this);
        lvrequest = (ListView) mView.findViewById(R.id.lvrequest);
        lvrequest.setOnItemClickListener(this);


        lnttcus = (LinearLayout) mView.findViewById(R.id.lnttcus);
        lnaccount = (LinearLayout) mView.findViewById(R.id.lnaccount);

        spinner_hotlinetype = (Spinner) mView.findViewById(R.id.spinner_hotlinetype);
        initHotlineType();
    }


    @Override
    public void onResume() {
        super.onResume();
        MainActivity.getInstance().setTitleActionBar(R.string.searchychotline);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.relaBackHome:
                getActivity().onBackPressed();
                break;
            case R.id.btn_search_acc:
                if (CommonActivity.isNetworkConnected(getActivity())) {
                    if (arrReceiveRequestBeans != null && !arrReceiveRequestBeans.isEmpty()) {
                        arrReceiveRequestBeans = new ArrayList<>();
                    }
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
//				if(CommonActivity.isNullOrEmpty(editusernameortel.getText().toString())){
//					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkaccount), getActivity().getString(R.string.app_name)).show();
//				}else{
                    GetListRequestHotlineAsyn getListRequestHotlineAsyn = new GetListRequestHotlineAsyn(getActivity());
                    getListRequestHotlineAsyn.execute(editusernameortel.getText().toString().trim(), edit_isdnacount.getText().toString(), hotlineType.toString());
//				}
                } else {
                    CommonActivity.createAlertDialog(getActivity(), getString(R.string.errorNetwork), getString(R.string.app_name)).show();
                }
                break;
            default:
                break;
        }
    }


    // move login
    private final OnClickListener moveLogInAct = new OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
            MainActivity.getInstance().finish();
            if (ActivityCreateNewRequestHotLine.instance != null) {
                ActivityCreateNewRequestHotLine.instance.finish();
            }

        }
    };

    // ws get list request
    private class GetListRequestHotlineAsyn extends
            AsyncTask<String, Void, ArrayList<ReceiveRequestBean>> {

        final ProgressDialog progress;
        private Context context = null;
        final XmlDomParse parse = new XmlDomParse();
        String errorCode = "";
        String description = "";

        public GetListRequestHotlineAsyn(Context context) {
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
        protected ArrayList<ReceiveRequestBean> doInBackground(String... arg0) {
            return getListRequest(arg0[0], arg0[1], arg0[2]);
        }

        @Override
        protected void onPostExecute(ArrayList<ReceiveRequestBean> result) {
            CommonActivity.hideKeyboard(editusernameortel, context);
            progress.dismiss();
            if ("0".equals(errorCode)) {
                if (result != null && !result.isEmpty()) {

                    arrReceiveRequestBeans = result;
                    adapter = new GetListReceiverequestHotlineAdapter(arrReceiveRequestBeans, getActivity());
                    lvrequest.setAdapter(adapter);
                } else {
                    if (arrReceiveRequestBeans != null && !arrReceiveRequestBeans.isEmpty()) {
                        arrReceiveRequestBeans = new ArrayList<>();
                    }

                    adapter = new GetListReceiverequestHotlineAdapter(arrReceiveRequestBeans, getActivity());
                    lvrequest.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


                    CommonActivity.createAlertDialog(getActivity(), getString(R.string.no_data), getString(R.string.app_name)).show();
                }
            } else {
                if (errorCode.equals(Constant.INVALID_TOKEN2)) {

                    Dialog dialog = CommonActivity
                            .createAlertDialog(
                                    getActivity(),
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



        private ArrayList<ReceiveRequestBean> getListRequest(String telorCusname, String accountChange, String hotlineType) {
            String original = "";
            ArrayList<ReceiveRequestBean> arrReceiveRequestBeans = new ArrayList<>();
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getListReceiveRequest");
                StringBuilder rawData = new StringBuilder();

                rawData.append("<ws:getListReceiveRequest>");
                rawData.append("<hotLineInput>");

                rawData.append("<token>").append(Session.getToken());
                rawData.append("</token>");



                if (!CommonActivity.isNullOrEmpty(telorCusname)) {
                    rawData.append("<telOrCusName>").append(telorCusname);
                    rawData.append("</telOrCusName>");
                }


                if (!CommonActivity.isNullOrEmpty(accountChange)) {
                    rawData.append("<accountChange>").append(accountChange);
                    rawData.append("</accountChange>");
                }
                if (!CommonActivity.isNullOrEmpty(hotlineType) && !"0".equals(hotlineType)) {
                    rawData.append("<requestType>").append(hotlineType);
                    rawData.append("</requestType>");
                }


                rawData.append("</hotLineInput>");
                rawData.append("</ws:getListReceiveRequest>");


                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope,
                        Constant.BCCS_GW_URL, getActivity(), "mbccs_getListReceiveRequest");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);

                original = output.getOriginal();
                Log.d("originalllllllll", original);

                // parse xml
                Document doc = parse.getDomElement(original);
                NodeList nl = doc.getElementsByTagName("return");
                NodeList nodechild = null;
                for (int i = 0; i < nl.getLength(); i++) {
                    Element e2 = (Element) nl.item(i);
                    errorCode = parse.getValue(e2, "errorCode");
                    Log.d("errorCode", errorCode);
                    description = parse.getValue(e2, "description");
                    Log.d("description", description);
                    nodechild = doc.getElementsByTagName("lstReceiveRequest");
                    for (int j = 0; j < nodechild.getLength(); j++) {
                        Element e1 = (Element) nodechild.item(j);
                        ReceiveRequestBean receiveRequestBean = new ReceiveRequestBean();

                        String account = parse.getValue(e1, "accountChange");
                        receiveRequestBean.setAccountChange(account);

                        String requestType = parse.getValue(e1, "requestType");
                        receiveRequestBean.setRequestType(requestType);

                        String productCode = parse.getValue(e1, "packData");
                        receiveRequestBean.setProductCode(productCode);



                        String reciveRequestId = parse.getValue(e1, "reciveRequestId");
                        Log.d("reciveRequestId", reciveRequestId);
                        receiveRequestBean.setReciveRequestId(reciveRequestId);

                        String customerName = parse.getValue(e1, "customerName");
                        receiveRequestBean.setCustomerName(customerName);

                        String desciptionSurvey = parse.getValue(e1, "desciptionSurvey");
                        receiveRequestBean.setDesciptionSurvey(desciptionSurvey);

                        String address = parse.getValue(e1, "address");
                        String areacode = "";

                        String provinceCode = parse.getValue(e1, "provinceCode");

                        String distrinctCode = parse.getValue(e1, "distrinctCode");

                        String precinct = parse.getValue(e1, "precinctCode");

                        receiveRequestBean.setProvinceCode(provinceCode);
                        receiveRequestBean.setDistrinctCode(distrinctCode);
                        receiveRequestBean.setPrecinctCode(precinct);


                        String addressDal = "";
                        try {
                            GetAreaDal dal = new GetAreaDal(getActivity());
                            dal.open();
                            if (!CommonActivity.isNullOrEmpty(provinceCode)) {
                                areacode = provinceCode;
                            }
                            if (!CommonActivity.isNullOrEmpty(distrinctCode)) {
                                areacode = distrinctCode;
                            }
                            if (!CommonActivity.isNullOrEmpty(precinct)) {
                                areacode = precinct;
                            }
                            if (!CommonActivity.isNullOrEmpty(areacode)) {
                                addressDal = dal.getfulladddress(areacode);
                                if (!CommonActivity.isNullOrEmpty(addressDal)) {
                                    address = address + " " + addressDal;
                                }
                            }
                            dal.close();
                        } catch (Exception e) {
                            Log.d("Exx ", e.toString());
                        }
                        receiveRequestBean.setAddress(address);

                        String tel = parse.getValue(e1, "tel");
                        receiveRequestBean.setTel(tel);

                        String dslam = parse.getValue(e1, "dslam");
                        receiveRequestBean.setDslam(dslam);

                        String contentRequest = parse.getValue(e1, "contentRequest");
                        receiveRequestBean.setContentRequest(contentRequest);

                        String userUpdateSurvey = parse.getValue(e1, "userUpdateSurvey");
                        receiveRequestBean.setUserUpdateSurvey(userUpdateSurvey);

                        String telecomserviceid = parse.getValue(e1, "serviceTypeId");
                        receiveRequestBean.setTelecomServiceId(telecomserviceid);


                        String userConnection = parse.getValue(e1, "userConnection");
                        receiveRequestBean.setUserConnection(userConnection);

                        if (!CommonActivity.isNullOrEmpty(account) && "7".equals(requestType)) {
                            String telecomservicename = parse.getValue(e1, "serviceTypeNameOld");
                            receiveRequestBean.setTelecomServiceName(telecomservicename);
                            String serviceType = parse.getValue(e1, "serviceTypeCodeOld");
                            receiveRequestBean.setServiceType(serviceType);
                        } else {
                            String telecomservicename = parse.getValue(e1, "serviceTypeName");
                            receiveRequestBean.setTelecomServiceName(telecomservicename);
                            String serviceType = parse.getValue(e1, "serviceTypeCode");
                            receiveRequestBean.setServiceType(serviceType);
                        }


                        String serviceTypeId = parse.getValue(e1, "serviceTypeId");
                        receiveRequestBean.setServiceTypeId(serviceTypeId);


                        String dateRequest = parse.getValue(e1, "dateReciveDate");
                        if (dateRequest != null && dateRequest.length() >= 10) {
                            String dateconvert = dateRequest.subSequence(0, 10)
                                    .toString();
                            Log.d("dateconvert", dateconvert);
                            String[] splitDate = dateconvert.split("-", 3);
                            if (splitDate.length == 3) {
                                String convertdate = splitDate[2] + "/"
                                        + splitDate[1] + "/" + splitDate[0];
                                Log.d("convertdate", convertdate);
                                receiveRequestBean.setReciveDate(convertdate);
                            } else {
                                receiveRequestBean.setReciveDate(dateconvert);
                            }


                        }
                        arrReceiveRequestBeans.add(receiveRequestBean);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return arrReceiveRequestBeans;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

        ReceiveRequestBean receiveRequestBean = arrReceiveRequestBeans.get(arg2);

        showDialogViewDetailReceive(receiveRequestBean);


    }

    EditText edtsodtdathang;
    private void showDialogViewDetailReceive(final
                                             ReceiveRequestBean receiveRequestBean) {

        getListHistoryFunction(receiveRequestBean.getReciveRequestId());
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.connection_dialog_detail_hotline);
        TextView txttitle = (TextView) dialog.findViewById(R.id.txttitle);
        txttitle.setText(getString(R.string.chitietyeucau) + receiveRequestBean.getReciveRequestId());

        EditText edtidrequesthotline = (EditText) dialog.findViewById(R.id.edtidrequesthotline);
        if (!CommonActivity.isNullOrEmpty(receiveRequestBean.getReciveRequestId())) {
            edtidrequesthotline.setText(receiveRequestBean.getReciveRequestId());
        }
        EditText edttenkhachhang = (EditText) dialog.findViewById(R.id.edttenkhachhang);
        if (!CommonActivity.isNullOrEmpty(receiveRequestBean.getCustomerName())) {
            edttenkhachhang.setText(receiveRequestBean.getCustomerName());
        }

        EditText edtsodthotline = (EditText) dialog.findViewById(R.id.edtsodthotline);
        if (!CommonActivity.isNullOrEmpty(receiveRequestBean.getTel())) {
            edtsodthotline.setText(receiveRequestBean.getTel());
        }

//        Log.i("DATA","hotLineReponseDetail.getTelRegister(): "+hotLineReponseDetail.getTelRegister());
        edtsodtdathang = (EditText) dialog.findViewById(R.id.edtsodtdathang);


        EditText edtdiachihotline = (EditText) dialog.findViewById(R.id.edtdiachihotline);
        if (!CommonActivity.isNullOrEmpty(receiveRequestBean.getAddress())) {
            edtdiachihotline.setText(receiveRequestBean.getAddress());
        }

        EditText edtnoidunghotline = (EditText) dialog.findViewById(R.id.edtnoidunghotline);
        if (!CommonActivity.isNullOrEmpty(receiveRequestBean.getContentRequest())) {
            edtnoidunghotline.setText(receiveRequestBean.getContentRequest());
        }

        EditText edtnguoikhaosat = (EditText) dialog.findViewById(R.id.edtnguoikhaosat);
        if (!CommonActivity.isNullOrEmpty(receiveRequestBean.getUserUpdateSurvey())) {
            edtnguoikhaosat.setText(receiveRequestBean.getUserUpdateSurvey());
        }

        EditText edtndkhaosat = (EditText) dialog.findViewById(R.id.edtndkhaosat);
        if (!CommonActivity.isNullOrEmpty(receiveRequestBean.getDesciptionSurvey())) {
            edtndkhaosat.setText(receiveRequestBean.getDesciptionSurvey());
        }

        EditText edtdichvuhotline = (EditText) dialog.findViewById(R.id.edtdichvuhotline);
        if (!CommonActivity.isNullOrEmpty(receiveRequestBean.getTelecomServiceName())) {
            edtdichvuhotline.setText(receiveRequestBean.getTelecomServiceName());
        }

		// thientv7 bo sung lich su
		Button btnViewHistoryConnect = (Button) dialog.findViewById(R.id.btn_view_history);

		btnViewHistoryConnect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				showDialogViewHistory(receiveRequestBean.getReciveRequestId());
			}
		});

        Button btnok = (Button) dialog.findViewById(R.id.btnok);
        btnok.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Log.i("DATA","ReciveRequestId: "+receiveRequestBean.getReciveRequestId()
                        +" ServiceType: "+receiveRequestBean.getServiceType()
                        +" receiveRequestBean.getRequestType(): "+receiveRequestBean.getRequestType()
                        +"receiveRequestBean.getServiceTypeId(): "+receiveRequestBean.getServiceTypeId());

                if (!CommonActivity.isNullOrEmpty(receiveRequestBean.getReciveRequestId()) && !CommonActivity.isNullOrEmpty(receiveRequestBean.getServiceType())) {

                    if (!CommonActivity.isNullOrEmpty(receiveRequestBean.getAccountChange()) && "7".equals(receiveRequestBean.getRequestType())) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("ReceiveRequestBeanKey", receiveRequestBean);
                        FragmentManagerChangeTech mListMenuManager = new FragmentManagerChangeTech();
                        mListMenuManager.setArguments(bundle);
                        ReplaceFragment.replaceFragment(getActivity(),
                                mListMenuManager, false);

                        dialog.dismiss();
                    } else if ("2".equals(receiveRequestBean.getRequestType()) || "8".equals(receiveRequestBean.getRequestType())){
                        if(!CommonActivity.isNullOrEmpty(checkRequest)){
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("ReceiveRequestBeanKey", receiveRequestBean);
                            FragmentSubscriberInfo mListMenuManager = new FragmentSubscriberInfo();
                            mListMenuManager.setArguments(bundle);
                            ReplaceFragment.replaceFragment(getActivity(),
                                    mListMenuManager, false);
                        }else{
                            Bundle bundle = new Bundle();
                            Intent intent = new Intent(getActivity(), ActivityCreateNewRequestHotLine.class);
                            bundle.putSerializable("ReceiveRequestBeanKey", receiveRequestBean);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }


                        dialog.dismiss();
                    }
                    else if(!CommonActivity.isNullOrEmpty(receiveRequestBean.getAccountChange()) && "12".equals(receiveRequestBean.getRequestType())) {

                        ManageSubscriberFragmentStrategy manageSubscriberFragmentStrategy2 = new ChangeProductFragmentStrategy();
                        Bundle bundle2 = new Bundle();
                        bundle2.putSerializable("fragmentStrategy",
                                manageSubscriberFragmentStrategy2);
                        bundle2.putSerializable("ReceiveRequestBeanKey", receiveRequestBean);
                        FragmentManageSubscriber fragmentManageSubscriber2 = new FragmentManageSubscriber();
                        fragmentManageSubscriber2.setArguments(bundle2);
                        fragmentManageSubscriber2.setTargetFragment(FragmentRequestChangeHotLine.this, 100);
                        ReplaceFragment.replaceFragment(getActivity(),
                                fragmentManageSubscriber2, false);
                        dialog.dismiss();
                    } else if ("1".equals(receiveRequestBean.getRequestType())){
                        if ("240".equals(receiveRequestBean.getServiceTypeId())||"241".equals(receiveRequestBean.getServiceTypeId()) ){ // di dong tra truoc/ di dong tra sau

                            Bundle bundle2 = new Bundle();

                            bundle2.putSerializable("hotLineReponseDetail", hotLineReponseDetail);
                            bundle2.putSerializable("receiveRequestBean", receiveRequestBean);
                            bundle2.putSerializable("lstImageBOs" , lstImageBOs);
                            Intent intent = new Intent(getActivity(), ActivityCreateNewRequestMobileNew.class);
                            intent.putExtras(bundle2);
                            startActivity(intent);
                            dialog.dismiss();

                        }  else if ("445".equals(receiveRequestBean.getServiceTypeId())||"446".equals(receiveRequestBean.getServiceTypeId())){ // tra truoc sang tra sau va nguoc lai

                            Bundle bundle2 = new Bundle();
                            bundle2.putSerializable("receiveRequestBean", receiveRequestBean);
                            bundle2.putSerializable("hotLineReponseDetail", hotLineReponseDetail);
                            if("445".equals(receiveRequestBean.getServiceTypeId())){ // tra truoc sang tra sau
                                bundle2.putSerializable("functionKey", Constant.CHANGE_PRE_TO_POS);
                            } else if ("446".equals(receiveRequestBean.getServiceTypeId())){ // tra sau sang tra truoc
                                bundle2.putSerializable("functionKey", Constant.CHANGE_POS_TO_PRE);
                            }
                            FragmentSearchSubChangeSim fragmentSearchSubChangeSim = new FragmentSearchSubChangeSim();
                            fragmentSearchSubChangeSim.setArguments(bundle2);
                            fragmentSearchSubChangeSim.setTargetFragment(FragmentRequestChangeHotLine.this, 100);
                            ReplaceFragment.replaceFragment(getActivity(),
                                    fragmentSearchSubChangeSim, false);
                            dialog.dismiss();


                        }
                    }


                } else {

                    CommonActivity.createAlertDialog(getActivity(), getString(R.string.thieuthongtin), getString(R.string.app_name)).show();

                }
            }
        });


        Button btncanel = (Button) dialog.findViewById(R.id.btncanel);
        btncanel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


	// show dialog history
	ListView lstHistory;
	Dialog dialog;
	private void showDialogViewHistory(String reciveRequestId) {


		dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.connection_dialog_view_history_hotline);
		TextView txttitle = (TextView) dialog.findViewById(R.id.txttitle);
		txttitle.setText(getString(R.string.txt_view_history_connect));

		lstHistory = (ListView) dialog.findViewById(R.id.lst_history);

        adapterHis = new ItemProcessHistoryAdapter(getActivity(), arrProcessRequestBOs);
        lstHistory.setAdapter(adapterHis);


		Button btnClose = (Button) dialog.findViewById(R.id.btn_close);
		btnClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});

		dialog.show();
	}

    // get histtory function
    private void getListHistoryFunction(String reciveRequestId){
        GetListRequestHistoryHotlineAsyn getListRequestHistoryHotlineAsyn = new GetListRequestHistoryHotlineAsyn(getActivity());
        getListRequestHistoryHotlineAsyn.execute(reciveRequestId);
    }

	// ws get list history
	// ws get list request
	private class GetListRequestHistoryHotlineAsyn extends
			AsyncTask<String, Void, ArrayList<ProcessRequestBO>> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetListRequestHistoryHotlineAsyn(Context context) {
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
		protected ArrayList<ProcessRequestBO> doInBackground(String... arg0) {
			return getListHistoryRequest(arg0[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<ProcessRequestBO> result) {
			CommonActivity.hideKeyboard(editusernameortel, context);
			progress.dismiss();
			Log.i("DATA", "errorCode:" +errorCode);
			if("0".equals(errorCode)){
				Log.i("DATA", "Result:" +result.toString());
				if(result != null && !result.isEmpty()){

					arrProcessRequestBOs = result;
					/*adapterHis = new ItemProcessHistoryAdapter(getActivity(), arrProcessRequestBOs);
					lstHistory.setAdapter(adapterHis);*/
					// set gia tri so dt dat hang o day
                    if (!CommonActivity.isNullOrEmpty(hotLineReponseDetail.getTelRegister())) {
                        edtsodtdathang.setText(hotLineReponseDetail.getTelRegister());
                    }
				}else{
					if(arrProcessRequestBOs != null && !arrProcessRequestBOs.isEmpty()){
						arrProcessRequestBOs = new ArrayList<>();
					}

					/*adapterHis = new ItemProcessHistoryAdapter(getActivity(), arrProcessRequestBOs);
					lstHistory.setAdapter(adapterHis);
					adapterHis.notifyDataSetChanged();*/

					CommonActivity.createAlertDialog(getActivity(), getString(R.string.no_data), getString(R.string.app_name), new OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog.dismiss();
						}
					}).show();

//					CommonActivity.createAlertDialog(getActivity(), getString(R.string.no_data), getString(R.string.app_name)).show();
				}
			}else{
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
									getActivity(),
									description,
									context.getResources().getString(
											R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if(description == null || description.isEmpty()){
						description = context.getString(R.string.checkdes);
					}
					/*Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();*/

					CommonActivity.createAlertDialog(getActivity(), description, getString(R.string.app_name), null).show();

				}

			}
		}

		private ArrayList<ProcessRequestBO> getListHistoryRequest(String receiveRequestId){
			String original = "";
			arrProcessRequestBOs = new ArrayList<>();
			try{
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getRequestHotlineDetail");
				StringBuilder rawData = new StringBuilder();

				rawData.append("<ws:getRequestHotlineDetail>");
				rawData.append("<hotLineInput>");

				rawData.append("<receiveRequestId>").append(receiveRequestId);
				rawData.append("</receiveRequestId>");

				rawData.append("<token>").append(Session.getToken());
				rawData.append("</token>");

				rawData.append("</hotLineInput>");
				rawData.append("</ws:getRequestHotlineDetail>");


				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,getActivity(),"mbccs_getRequestHotlineDetail");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				Log.d("originalllllllll", original);

                Serializer serializer = new Persister();
                ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
                if (parseOuput != null) {
                    errorCode = parseOuput.getErrorCode();
                    description = parseOuput.getDescription();
                    arrProcessRequestBOs = parseOuput.getListProcessRequest();

                    hotLineReponseDetail = parseOuput.getHotLineReponseDetail();
                    lstImageBOs = parseOuput.getLstImageBOs();
                    Gson g = new Gson();
                    Log.d("hotLineReponseDetail_FragmentRequestChangeHotLine: ", g.toJson(hotLineReponseDetail));

                } else {
                    errorCode = Constant.ERROR_CODE;
                    description = getActivity().getString(R.string.no_data_return);
                }

			/*	// parse xml
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
//					description = parse.getValue(e2, "description");
//					Log.d("description", description);
					nodechild = doc.getElementsByTagName("listProcessRequest");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						ProcessRequestBO processRequestBO = new ProcessRequestBO();

						String processDate = parse.getValue(e1, "processDate");
						processRequestBO.setProcessDate(processDate);

						String reciveDate = parse.getValue(e1, "reciveDate");
						processRequestBO.setReciveDate(reciveDate);

						String userCreate = parse.getValue(e1, "userCreate");
						processRequestBO.setUserCreate(userCreate);

						String status = parse.getValue(e1, "status");
						processRequestBO.setStatus(status);

						String contentProcess = parse.getValue(e1, "contentProcess");
						processRequestBO.setContentProcess(contentProcess);


						arrProcessRequestBOs.add(processRequestBO);
					}


					Log.d("arrProcessRequestBOs", "arrProcessRequestBOs size: "+arrProcessRequestBOs.size());
				}*/
			}catch(Exception e){
				e.printStackTrace();
			}
			return arrProcessRequestBOs;
		}

	}



}
