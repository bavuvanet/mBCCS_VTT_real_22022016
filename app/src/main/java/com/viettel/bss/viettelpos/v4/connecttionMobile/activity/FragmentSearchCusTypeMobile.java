package com.viettel.bss.viettelpos.v4.connecttionMobile.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity.FragmentChooseCusGroupBCCS;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter.GetCusGroupDTOAdapter;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter.GetCusTypeBccsAdapter;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.GetListAllGroupCustTypeAsyn;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustTypeDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.GPSTracker;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentSearchCusTypeMobile extends GPSTracker implements
        OnClickListener, OnItemClickListener {

    // == unitview
    private EditText edtsearch;
    private ListView lvpakage;
    private GetCusTypeBccsAdapter getCusTypeAdapter;
    private ArrayList<CustTypeDTO> arrCustTypeDTOs = new ArrayList<>();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
//    private EditText edtnhomkh;
    private Spinner spnnhomkh;
    private ProgressBar prbnhomkh;
    private Button btnnhomkh;
    private Spin cusGroup;
    private String groupKey;
    private String groupTypeKey;
    private String cusTypeKey;
    private ArrayList<Spin> arrCusGroup = new ArrayList<Spin>();
    private GetCusGroupDTOAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connectionmobile_layout_lst_custypegr);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle mBundle = this.getIntent().getExtras();
        if (mBundle != null) {
            arrCustTypeDTOs = (ArrayList<CustTypeDTO>) mBundle
                    .getSerializable("arrCustTypeDTOsKey");
            groupKey = mBundle.getString("GROUPKEY", "");
            groupTypeKey = mBundle.getString("GROUPTYPEKEY", "");
            cusTypeKey = mBundle.getString("CUSTYPEKEY","");
        }
        UnitView();


            if (arrCustTypeDTOs != null && arrCustTypeDTOs.size() > 0) {
                getCusTypeAdapter = new GetCusTypeBccsAdapter(arrCustTypeDTOs,
                        FragmentSearchCusTypeMobile.this);
                lvpakage.setAdapter(getCusTypeAdapter);
            } else {
                arrCustTypeDTOs = new ArrayList<>();
            }

    }

    private void UnitView() {

//        edtnhomkh = (EditText) findViewById(R.id.edtnhomkh);
//        edtnhomkh.setOnClickListener(this);
        spnnhomkh = (Spinner) findViewById(R.id.spnnhomkh);
        spnnhomkh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spin spinGroup = (Spin) spnnhomkh.getItemAtPosition(position);
                GetMappingChannelCustTypeAsyn getMappingChannelCustTypeAsyn = new GetMappingChannelCustTypeAsyn(
                        FragmentSearchCusTypeMobile.this);
                getMappingChannelCustTypeAsyn.execute(spinGroup.getValue() + "");


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        prbnhomkh = (ProgressBar) findViewById(R.id.prbnhomkh);
        btnnhomkh = (Button) findViewById(R.id.btnnhomkh);
        btnnhomkh.setOnClickListener(this);

        Button btnRefreshStreetBlock = (Button) findViewById(R.id.btnRefreshStreetBlock);
        btnRefreshStreetBlock.setVisibility(View.GONE);
        edtsearch = (EditText) findViewById(R.id.edtsearch);
        lvpakage = (ListView) findViewById(R.id.lstpakage);
        lvpakage.setOnItemClickListener(this);
        lvpakage.setTextFilterEnabled(true);
        TextView txtinfo = (TextView) findViewById(R.id.txtinfo);
        txtinfo.setText(getString(R.string.dscustype));
        edtsearch.setHint(R.string.checkcustype);
        edtsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                String input = edtsearch.getText().toString()
                        .toLowerCase(Locale.getDefault());

                if (getCusTypeAdapter != null) {
                    arrCustTypeDTOs = getCusTypeAdapter.SearchInput(input);
                    lvpakage.setAdapter(getCusTypeAdapter);
                    getCusTypeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if(CommonActivity.isNullOrEmpty(arrCusGroup)){
            GetListAllGroupCustTypeAsyn getListAllGroupCustTypeAsyn = new GetListAllGroupCustTypeAsyn(FragmentSearchCusTypeMobile.this,new OnPostGetAllGroupCustype(),moveLogInAct);
            getListAllGroupCustTypeAsyn.execute();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        getSupportActionBar().setTitle(getString(R.string.search));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == Activity.RESULT_OK) {
//            switch (requestCode) {
//
//                case 144:
//                    if (data != null) {
//                        cusGroup = (Spin) data.getExtras().getSerializable("cusgroup");
//                        if (!CommonActivity.isNullOrEmpty(cusGroup) && !CommonActivity.isNullOrEmpty(cusGroup.getName())) {
//                            edtnhomkh.setText(cusGroup.getName());
//                            GetMappingChannelCustTypeAsyn getMappingChannelCustTypeAsyn = new GetMappingChannelCustTypeAsyn(
//                                    FragmentSearchCusTypeMobile.this);
//                            getMappingChannelCustTypeAsyn.execute(cusGroup.getValue());
//                        } else {
//                            edtnhomkh.setText("");
//                            edtnhomkh.setHint(getString(R.string.chonnhomKH));
//                        }
//                    }
//                    break;
//
//                default:
//                    break;
//            }
//        }
//
//    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

        Intent data = new Intent();
        Bundle mBundle = new Bundle();
        Spin spin = (Spin) spnnhomkh.getSelectedItem();
        CustTypeDTO custTypeDTO = arrCustTypeDTOs.get(arg2);
        custTypeDTO.setCusGroup(spin.getValue());
        mBundle.putSerializable("cusTypeDTOsKey",custTypeDTO);
        data.putExtras(mBundle);
        setResult(RESULT_OK, data);
        finish();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relaBackHome:
                onBackPressed();
                break;
//            case R.id.edtnhomkh:
//                Intent intent1 = new Intent(FragmentSearchCusTypeMobile.this, FragmentChooseCusGroupBCCS.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("GROUPKEY", groupKey);
//                startActivityForResult(intent1, 144);
//                break;
            default:
                break;
        }
    }

    private class GetMappingChannelCustTypeAsyn extends AsyncTask<String, Void, ArrayList<CustTypeDTO>> {
        private Context context = null;
        String errorCode = "";
        String description = "";
        private ProgressDialog progress;

        public GetMappingChannelCustTypeAsyn(Context context) {
            this.context = context;
            this.progress = new ProgressDialog(this.context);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected ArrayList<CustTypeDTO> doInBackground(String... params) {
            return getMappingChannelCustType(params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<CustTypeDTO> result) {
            progress.dismiss();
            arrCustTypeDTOs = new ArrayList<>();
            if ("0".equals(errorCode)) {
                if (!CommonActivity.isNullOrEmptyArray(result)) {
                    if(!CommonActivity.isNullOrEmpty(cusTypeKey)){
                        for (CustTypeDTO item: result) {
                            if(!cusTypeKey.equals(item.getCustType())){
                                arrCustTypeDTOs.add(item);
                            }
                        }
                    }else{
                        arrCustTypeDTOs = result;
                    }

                    getCusTypeAdapter = new GetCusTypeBccsAdapter(arrCustTypeDTOs,
                            FragmentSearchCusTypeMobile.this);
                    lvpakage.setAdapter(getCusTypeAdapter);
                    getCusTypeAdapter.notifyDataSetChanged();
                    if(CommonActivity.isNullOrEmptyArray(arrCustTypeDTOs)){
                        Dialog dialog = CommonActivity.createAlertDialog(FragmentSearchCusTypeMobile.this,
                                getResources().getString(R.string.checkTypeCus),
                                getResources().getString(R.string.app_name));
                        dialog.show();
                    }

                } else {
                    arrCustTypeDTOs = new ArrayList<>();
                    getCusTypeAdapter = new GetCusTypeBccsAdapter(arrCustTypeDTOs,
                            FragmentSearchCusTypeMobile.this);
                    lvpakage.setAdapter(getCusTypeAdapter);
                    getCusTypeAdapter.notifyDataSetChanged();
                    Dialog dialog = CommonActivity.createAlertDialog(FragmentSearchCusTypeMobile.this,
                            getResources().getString(R.string.checkTypeCus),
                            getResources().getString(R.string.app_name));
                    dialog.show();
                }

            } else {
                arrCustTypeDTOs = new ArrayList<>();
                getCusTypeAdapter = new GetCusTypeBccsAdapter(arrCustTypeDTOs,
                        FragmentSearchCusTypeMobile.this);
                lvpakage.setAdapter(getCusTypeAdapter);
                getCusTypeAdapter.notifyDataSetChanged();
                if (Constant.INVALID_TOKEN2.equals(errorCode)) {

                    CommonActivity.BackFromLogin(FragmentSearchCusTypeMobile.this, description, "");
                } else {
                    if (description == null || description.equals("")) {
                        description = context.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(FragmentSearchCusTypeMobile.this, description,
                            getResources().getString(R.string.app_name));
                    dialog.show();

                }
            }

        }

        private ArrayList<CustTypeDTO> getMappingChannelCustType(String payType) {
            ArrayList<CustTypeDTO> lstCustTypeDTOs = new ArrayList<>();
            String original = null;
            try {
                // tra truoc
//				if ("2".equals(payType)) {
//					lstCustTypeDTOs = new CacheDatabaseManager(context).getListCusTypeDTO("cusTypeDTOPre");
//				} else {
                // tra sau
//                lstCustTypeDTOs = new CacheDatabaseManager(context).getListCusTypeDTO("cusTypeDTO" + "-" + payType);
//				}

                if (lstCustTypeDTOs != null && !lstCustTypeDTOs.isEmpty()) {
                    errorCode = "0";
                    return lstCustTypeDTOs;
                }

                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getMappingChannelCustType");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getMappingChannelCustType>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));
                rawData.append("<payType>").append(payType);
                rawData.append("</payType>");
                rawData.append("</input>");
                rawData.append("</ws:getMappingChannelCustType>");
                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, FragmentSearchCusTypeMobile.this,
                        "mbccs_getMappingChannelCustType");
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
                    lstCustTypeDTOs = parseOuput.getLstCustTypeDTO();
                }

            } catch (Exception e) {
                Log.d("exception", e.toString());
            }
//			if ("2".equals(payType)) {
//				new CacheDatabaseManager(context).insertCusType("cusTypeDTOPre", lstCustTypeDTOs);
//			} else {
//            new CacheDatabaseManager(context).insertCusType("cusTypeDTO" + "-" + payType, lstCustTypeDTOs);
//			}

            return lstCustTypeDTOs;
        }
    }
    private class OnPostGetAllGroupCustype implements OnPostExecuteListener<ArrayList<Spin>> {
        @Override
        public void onPostExecute(ArrayList<Spin> result, String errorCode, String description) {
            arrCusGroup = new ArrayList<>();
            if ("0".equals(errorCode)) {
                if (!CommonActivity.isNullOrEmptyArray(result)) {
                    if(!CommonActivity.isNullOrEmpty(groupKey)){
                        for (Spin item : result){
                            if("1".equals(item.getValue()) || "3".equals(item.getValue())){
                                arrCusGroup.add(item);
                            }
                        }
                    }else{
                        arrCusGroup = result;
                    }




                    itemsAdapter = new GetCusGroupDTOAdapter(arrCusGroup, FragmentSearchCusTypeMobile.this);
                    spnnhomkh.setAdapter(itemsAdapter);
//                    if(!CommonActivity.isNullOrEmpty(groupTypeKey)){
//                        for (Spin item: result) {
//                            if("GROUPTYPEKEY".equals(item.getValue())){
////                                arrCusGroup.add(item);
//                                edtnhomkh.setText(item.getName());
//                                edtnhomkh.setEnabled(false);
//                                GetMappingChannelCustTypeAsyn getMappingChannelCustTypeAsyn = new GetMappingChannelCustTypeAsyn(
//                                        FragmentSearchCusTypeMobile.this);
//                                getMappingChannelCustTypeAsyn.execute(groupTypeKey);
//                                break;
//                            }
//                        }
//                    }
                    if(!CommonActivity.isNullOrEmpty(groupKey)){
                        for (Spin item: result) {
                            if("1".equals(item.getValue())){
                                spnnhomkh.setSelection(arrCusGroup.indexOf(item));
//                                spnnhomkh.setEnabled(false);
//                                arrCusGroup.add(item);
//                                edtnhomkh.setText(item.getName());
//                                edtnhomkh.setEnabled(false);
//                                GetMappingChannelCustTypeAsyn getMappingChannelCustTypeAsyn = new GetMappingChannelCustTypeAsyn(
//                                        FragmentSearchCusTypeMobile.this);
//                                getMappingChannelCustTypeAsyn.execute("1");
                                break;
                            }
                        }
                    }
                } else {
                    spnnhomkh.setEnabled(true);
                    arrCusGroup = new ArrayList<>();
                    itemsAdapter = new GetCusGroupDTOAdapter(arrCusGroup, FragmentSearchCusTypeMobile.this);
                    spnnhomkh.setAdapter(itemsAdapter);
//                    edtnhomkh.setText("");
//                    edtnhomkh.setHint(getString(R.string.chonnhomKH));
                }
            } else {
                if (Constant.INVALID_TOKEN2.equals(errorCode)) {
                    Dialog dialog = CommonActivity.createAlertDialog(FragmentSearchCusTypeMobile.this, description,
                            FragmentSearchCusTypeMobile.this.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = FragmentSearchCusTypeMobile.this.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(FragmentSearchCusTypeMobile.this, description,
                            FragmentSearchCusTypeMobile.this.getResources().getString(R.string.app_name));
                    dialog.show();
                }
            }
        }
    }
    private OnClickListener moveLogInAct = new OnClickListener() {

        @Override
        public void onClick(View v) {
            LoginDialog dialog = new LoginDialog(FragmentSearchCusTypeMobile.this,
                    "");

            dialog.show();
        }
    };
}
