package com.viettel.bss.viettelpos.v4.omichanel.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.omichanel.adapter.OrderOmniAdapter;
import com.viettel.bss.viettelpos.v4.omichanel.asynctask.FindDetailTaskAsyncTask;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ConnectionOrder;
import com.viettel.bss.viettelpos.v4.omichanel.dao.OrderState;
import com.viettel.bss.viettelpos.v4.omichanel.dialog.CreateOrderDialog;
import com.viettel.bss.viettelpos.v4.omichanel.dialog.ListStaffDialog;
import com.viettel.bss.viettelpos.v4.sale.business.StaffBusiness;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by hantt47 on 9/26/2017.
 */

public class SearchOrderToClaimFragment extends FragmentCommon {

    private Activity activity;
    private Button btnSearch;
    private EditText editOrder, editId, editSim,
            editIsdnContact;
    private RecyclerView recyclerView;
    private Spinner spnStatus, spnOrderType;
    private TextView tvNoData, tvStaff;
    private LinearLayout  llStaffList;
    private String status;
    private String orderType;
    private String funcSearchName;
    private  int orderActionState ;
    private ListStaffDialog listStaffDialog;
    private ArrayList<Staff> arrStaff;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        idLayout = R.layout.search_claim_omni_fragment;
        activity = getActivity();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleActionBar(activity.getString(R.string.hand_over));
    }


    @Override
    protected void unit(View v) {
        funcSearchName = "findDetailTaskAssignByListUserId";
        orderActionState =  OrderState.ORD_RE_CLAIM_ACT;
        btnSearch = (Button) v.findViewById(R.id.btnSearch);


        editOrder = (EditText) v.findViewById(R.id.editOrder);
        editIsdnContact = (EditText) v.findViewById(R.id.editIsdnContact);
        llStaffList = (LinearLayout) v.findViewById(R.id.llStaffList);
        editId = (EditText) v.findViewById(R.id.editId);
        editSim = (EditText) v.findViewById(R.id.editSim);
        llStaffList.setVisibility(View.GONE);

        editOrder.addTextChangedListener(new AddListenerOnTextChange(
                editOrder, R.drawable.ic_assignment_18px, R.drawable.ic_clear));
        editOrder.setOnTouchListener(new SetOnTouchListener(
                editOrder,R.drawable.ic_assignment_18px ));
        editIsdnContact.addTextChangedListener(new AddListenerOnTextChange(
                editIsdnContact, R.drawable.ic_phone_18px, R.drawable.ic_clear));
        editIsdnContact.setOnTouchListener(new SetOnTouchListener(
                editIsdnContact,R.drawable.ic_phone_18px ));
        editId.addTextChangedListener(new AddListenerOnTextChange(
                editId, R.drawable.ic_featured_18px, R.drawable.ic_clear));
        editId.setOnTouchListener(new SetOnTouchListener(
                editId,R.drawable.ic_featured_18px ));
        editSim.addTextChangedListener(new AddListenerOnTextChange(
                editSim, R.drawable.ic_sim_card_18px, R.drawable.ic_clear));
        editSim.setOnTouchListener(new SetOnTouchListener(
                editSim,R.drawable.ic_sim_card_18px ));

        recyclerView = (RecyclerView) v.findViewById(R.id.listview);
        spnStatus = (Spinner) v.findViewById(R.id.spnStatus);
        spnOrderType = (Spinner) v.findViewById(R.id.spnOrderType);
        tvNoData = (TextView) v.findViewById(R.id.tvNoData);
        tvStaff = (TextView) v.findViewById(R.id.tvStaff);


        //init list staff
        String staffMngtCode = Session.userName;
        arrStaff = StaffBusiness.getLstStaffByStaffMngt(getContext(), staffMngtCode);
        listStaffDialog = new ListStaffDialog(activity, arrStaff, tvStaff);
        tvStaff.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                listStaffDialog.show();
            }
        });

        tvNoData.setVisibility(View.GONE);

        // init status
        ArrayList<Spin> lstSpinMyOrder = new ArrayList<>();
        lstSpinMyOrder.add(new Spin("1", getString(R.string.order_of_me)));
        lstSpinMyOrder.add(new Spin("2", getString(R.string.order_reciver_able)));

        // init status
        ArrayList<Spin> lstSpinOrderType = new ArrayList<>();
        lstSpinOrderType.add(new Spin("", getString(R.string.all)));
        lstSpinOrderType.add(new Spin(Constant.ORD_TYPE_CONNECT_PREPAID,
                getString(R.string.order_type_connect_prepaid)));
        lstSpinOrderType.add(new Spin(Constant.ORD_TYPE_REGISTER_PREPAID,
                getString(R.string.order_type_register_prepaid)));
        lstSpinOrderType.add(new Spin(Constant.ORD_TYPE_CONNECT_POSPAID,
                getString(R.string.order_type_connect_pospaid)));
        lstSpinOrderType.add(new Spin(Constant.ORD_TYPE_CHANGE_TO_POSPAID,
                getString(R.string.order_type_change_to_pospaid)));
        lstSpinOrderType.add(new Spin(Constant.ORD_TYPE_CHANGE_PREPAID_FEE,
                getString(R.string.change_pre_charge_tittle)));

        Utils.setDataSpinner(getActivity(), lstSpinOrderType, spnOrderType);
        spnOrderType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Spin spin = (Spin) arg0.getItemAtPosition(arg2);
                orderType = spin.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        // init status
        ArrayList<Spin> lstSpinStatus = new ArrayList<>();
        lstSpinStatus.add(new Spin("", getString(R.string.all)));
        lstSpinStatus.add(new Spin("1", getString(R.string.request_new)));
        lstSpinStatus.add(new Spin("2", getString(R.string.request_processing)));
        lstSpinStatus.add(new Spin("3", getString(R.string.request_procesed)));
        Utils.setDataSpinner(getActivity(), lstSpinStatus, spnStatus);
        spnStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Spin spinObject = (Spin) arg0.getItemAtPosition(arg2);
                status = spinObject.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        // init asyncTask findDetailTask
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findDetailTask();
            }
        });

    }

    @Override
    protected void setPermission() {

    }

    class AddListenerOnTextChange implements TextWatcher {
        private int iconFunc;
        private int iconClear;
        private EditText mEdittext;

        public AddListenerOnTextChange(EditText mEdittext, int iconFunc, int iconClear) {
            super();
            this.mEdittext = mEdittext;
            this.iconFunc = iconFunc;
            this.iconClear = iconClear;
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mEdittext.setCompoundDrawablesWithIntrinsicBounds(iconFunc, 0, iconClear, 0);
            if(CommonActivity.isNullOrEmpty(mEdittext.getText().toString().trim())){
                mEdittext.setCompoundDrawablesWithIntrinsicBounds(iconFunc, 0, 0, 0);
            }
        }
    }

    class SetOnTouchListener implements View.OnTouchListener {
        private EditText mEdittext;
        private int iconFunc;

        public SetOnTouchListener(EditText mEdittext, int iconFunc) {
            super();
            this.mEdittext = mEdittext;
            this.iconFunc = iconFunc;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final int DRAWABLE_RIGHT = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if(!CommonActivity.isNullOrEmpty(mEdittext.getCompoundDrawables()[DRAWABLE_RIGHT])){
                    if (event.getRawX() >= (mEdittext.getRight() - mEdittext.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        mEdittext.setText("");
                        mEdittext.setCompoundDrawablesWithIntrinsicBounds(iconFunc, 0, 0, 0);
                        return true;
                    }
                }
            }
            return false;
        }
    }

    private void findDetailTask() {

        String strStaff = tvStaff.getText().toString();
        if(CommonActivity.isNullOrEmpty(strStaff)
                || activity.getResources().getString(R.string.choose_staff).equals(strStaff)){
            CommonActivity.createAlertDialog(activity
                    ,activity.getResources().getString(R.string.confirm_choose_staff)
                    , activity.getResources().getString(R.string.app_name)).show();
            return;
        }

        FindDetailTaskAsyncTask findDetailTaskAsyncTask = new FindDetailTaskAsyncTask(
                funcSearchName,
                getActivity(),
                new OnPostExecuteListener<List<ConnectionOrder>>() {

                    @Override
                    public void onPostExecute(List<ConnectionOrder> result, String errorCode, String description) {
                        if (result.size() > 0) {
                            result = filterOrderCancelAndHSDT(result);
                        }
                        if (result.size() == 0) {
                            tvNoData.setVisibility(View.VISIBLE);
                        } else {
                            tvNoData.setVisibility(View.GONE);
                        }

                        Collections.sort(result, new Comparator<ConnectionOrder>() {
                            @Override
                            public int compare(ConnectionOrder o1, ConnectionOrder o2) {
                                return o1.getOrderCode().compareTo(o2.getOrderCode());
                            }
                        });

                        OrderOmniAdapter orderOmniAdapter = new OrderOmniAdapter(
                                getActivity(), orderActionState, result, SearchOrderToClaimFragment.this, arrStaff);
                        recyclerView.setAdapter(orderOmniAdapter);
                        orderOmniAdapter.notifyDataSetChanged();
                    }
                }, moveLogInAct, arrStaff);

        String shopId = "";
        String isdn = editSim.getText() == null ? ""
                : StringUtils.formatIsdn(editSim.getText().toString().trim());
        String idNo = editId.getText() == null ? ""
                : editId.getText().toString().trim();
        String orderCode = editOrder.getText() == null ? ""
                : editOrder.getText().toString().toUpperCase().trim();
        String isdnContact = editIsdnContact.getText() == null ? ""
                : editIsdnContact.getText().toString().trim();
        findDetailTaskAsyncTask.execute(StringUtils.formatMsisdn(isdnContact), "", idNo,
                isdn, orderCode, status, shopId, orderType, "", "");
    }

    private List<ConnectionOrder> filterOrderCancelAndHSDT(List<ConnectionOrder> result) {
        ConnectionOrder connectionOrderTemp;
        for (int index = result.size() - 1; index >= 0; index--) {
            connectionOrderTemp = result.get(index);
            if (("HSDT".equals(connectionOrderTemp.getTarget()))
                    || (connectionOrderTemp.getStatus() == 4)) {
                result.remove(connectionOrderTemp);
            }
        }
        return result;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        tvNoData.setVisibility(View.GONE);
        if (resultCode == getActivity().RESULT_OK && requestCode == 1011) {
            OrderOmniAdapter orderOmniAdapter =
                    new OrderOmniAdapter(getActivity(), orderActionState,
                            new ArrayList<ConnectionOrder>(), SearchOrderToClaimFragment.this);
            recyclerView.setAdapter(orderOmniAdapter);
            orderOmniAdapter.notifyDataSetChanged();
        }
    }
}

