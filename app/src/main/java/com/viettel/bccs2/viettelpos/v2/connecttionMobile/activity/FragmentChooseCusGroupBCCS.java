package com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter.GetCusGroupDTOAdapter;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.GetListAllGroupCustTypeAsyn;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.GPSTracker;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.dal.CacheDatabaseManager;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentChooseCusGroupBCCS extends GPSTracker implements OnClickListener {

    private EditText edt_search;
    private ListView lvReason;
    private ArrayList<Spin> arrCusGroup = new ArrayList<Spin>();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtinfo)
    TextView txtinfo;
    @BindView(R.id.btnRefreshStreetBlock)
    Button btnRefresh;
    private String groupKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connectionmobile_layout_lst_pakage);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Bundle mBundle = this.getIntent().getExtras();
        if (mBundle != null) {
            groupKey= mBundle.getString("GROUPKEY","");
        }
        unit();


    }

    public void unit() {
        txtinfo.setText(this.getString(R.string.dscusgroup));
        btnRefresh.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new CacheDatabaseManager(FragmentChooseCusGroupBCCS.this).insertCusGroupBCCS(null);
                GetListAllGroupCustTypeAsyn getListAllGroupCustTypeAsyn = new GetListAllGroupCustTypeAsyn(FragmentChooseCusGroupBCCS.this,new OnPostGetAllGroupCustype(),moveLogInAct);
                getListAllGroupCustTypeAsyn.execute();
            }
        });
        edt_search = (EditText) findViewById(R.id.edtsearch);
        edt_search.setHint(getApplicationContext().getString(R.string.checknhhomkh));
        lvReason = (ListView) findViewById(R.id.lstpakage);

        edt_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                onSearchBeans();
            }
        });
        // lay danh sach nhom khach hang
        if(CommonActivity.isNullOrEmptyArray(arrCusGroup)){
            GetListAllGroupCustTypeAsyn getListAllGroupCustTypeAsyn = new GetListAllGroupCustTypeAsyn(FragmentChooseCusGroupBCCS.this,new OnPostGetAllGroupCustype(),moveLogInAct);
            getListAllGroupCustTypeAsyn.execute();
        }



        lvReason.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Spin reasonDTO = (Spin) arg0.getAdapter().getItem(arg2);
                Intent data = new Intent();
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("cusgroup", reasonDTO);
                data.putExtras(mBundle);
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    private void onSearchBeans() {
        String keySearch = edt_search.getText().toString().trim();
        if (keySearch.length() == 0) {
            GetCusGroupDTOAdapter itemsAdapter = new GetCusGroupDTOAdapter(arrCusGroup, FragmentChooseCusGroupBCCS.this);
            lvReason.setAdapter(itemsAdapter);
            itemsAdapter.notifyDataSetChanged();

        } else {
            ArrayList<Spin> items = new ArrayList<Spin>();
            for (Spin promotionTypeBeans : arrCusGroup) {
                if ((promotionTypeBeans.getName() != null && promotionTypeBeans.getName().toLowerCase().contains(keySearch.toLowerCase()))) {
                    items.add(promotionTypeBeans);
                }
            }
            GetCusGroupDTOAdapter itemsAdapter = new GetCusGroupDTOAdapter(items, FragmentChooseCusGroupBCCS.this);
            lvReason.setAdapter(itemsAdapter);
            itemsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setSubtitle(getString(R.string.dscusgroup));
    }

    private class OnPostGetAllGroupCustype implements OnPostExecuteListener<ArrayList<Spin>> {
        @Override
        public void onPostExecute(ArrayList<Spin> result, String errorCode, String description) {
            arrCusGroup = new ArrayList<>();
            if ("0".equals(errorCode)) {
                if (!CommonActivity.isNullOrEmptyArray(result)) {
                    if(!CommonActivity.isNullOrEmpty(groupKey)){
                        for (Spin item: result) {
                            if("1".equals(item.getValue())){
                                arrCusGroup.add(item);
                            }
                        }
                    }else{
                        arrCusGroup.addAll(result);
                    }


                } else {
                    arrCusGroup = new ArrayList<>();
                }
                onSearchBeans();
            } else {
                if (Constant.INVALID_TOKEN2.equals(errorCode)) {
                    Dialog dialog = CommonActivity.createAlertDialog(FragmentChooseCusGroupBCCS.this, description,
                            FragmentChooseCusGroupBCCS.this.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = FragmentChooseCusGroupBCCS.this.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(FragmentChooseCusGroupBCCS.this, description,
                            FragmentChooseCusGroupBCCS.this.getResources().getString(R.string.app_name));
                    dialog.show();
                }
            }
        }
    }

    private OnClickListener moveLogInAct = new OnClickListener() {

        @Override
        public void onClick(View v) {
            LoginDialog dialog = new LoginDialog(FragmentChooseCusGroupBCCS.this,
                    "");

            dialog.show();
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relaBackHome:
                onBackPressed();
                break;
            default:
                break;
        }
    }
}
