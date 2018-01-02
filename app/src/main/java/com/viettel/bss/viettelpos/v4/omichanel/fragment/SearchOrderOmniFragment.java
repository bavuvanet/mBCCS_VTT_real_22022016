package com.viettel.bss.viettelpos.v4.omichanel.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.channel.activity.StaffInfoActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.hsdt.fragments.ProfileInfoOmniFragment;
import com.viettel.bss.viettelpos.v4.omichanel.adapter.OrderOmniAdapter;
import com.viettel.bss.viettelpos.v4.omichanel.asynctask.FindDetailTaskAsyncTask;
import com.viettel.bss.viettelpos.v4.omichanel.asynctask.FindMappingAreaForStaffAsyncTask;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ConnectionOrder;
import com.viettel.bss.viettelpos.v4.omichanel.dao.MapStaffArea;
import com.viettel.bss.viettelpos.v4.omichanel.dao.OrderState;
import com.viettel.bss.viettelpos.v4.omichanel.dialog.CreateOrderDialog;
import com.viettel.bss.viettelpos.v4.sale.business.StaffBusiness;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;
import com.viettel.bss.viettelpos.v4.task.AsynTaskGetStaffSignatureFile;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.view.View.GONE;

/**
 * Created by hantt47 on 9/1/2017.
 */

public class SearchOrderOmniFragment extends FragmentCommon {

    private Activity activity;
    private Button btnSearch, btnCreateNew;
    private EditText editOrder, editId, editSim, editIsdnContact;
    private RecyclerView recyclerView;
    private Spinner spnStatus, spnMyOrder;
    private TextView tvNoData, tvOrderNew, tvCountOrder;
    private LinearLayout linMyOrder;

    private Staff staff;
    private String status;
    private String funcSearchName;
    private String createUser;
    private String createDate;
    private int orderActionState;
    private CreateOrderDialog createOrderDialog;

    private String staffState;
    private ArrayList<String> listIdNo;
    private FloatingActionButton imgAction;
    private List<ConnectionOrder> orderList;
    private ArrayList<ConnectionOrder> arrConnectionOrder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        idLayout = R.layout.search_request_omni_fragment;
        activity = getActivity();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleActionBar(activity.getString(R.string.search_request));
    }

    @Override
    protected void unit(View v) {
        imgAction = (FloatingActionButton) v.findViewById(R.id.imgAction);

        btnSearch = (Button) v.findViewById(R.id.btnSearch);
        btnCreateNew = (Button) v.findViewById(R.id.btnCreateNew);
        editOrder = (EditText) v.findViewById(R.id.editOrder);
        editIsdnContact = (EditText) v.findViewById(R.id.editIsdnContact);
        editId = (EditText) v.findViewById(R.id.editId);
        editSim = (EditText) v.findViewById(R.id.editSim);

        editOrder.addTextChangedListener(new AddListenerOnTextChange(
                editOrder, R.drawable.ic_assignment_18px, R.drawable.ic_clear));
        editOrder.setOnTouchListener(new SetOnTouchListener(
                editOrder, R.drawable.ic_assignment_18px));
        editIsdnContact.addTextChangedListener(new AddListenerOnTextChange(
                editIsdnContact, R.drawable.ic_phone_18px, R.drawable.ic_clear));
        editIsdnContact.setOnTouchListener(new SetOnTouchListener(
                editIsdnContact, R.drawable.ic_phone_18px));
        editId.addTextChangedListener(new AddListenerOnTextChange(
                editId, R.drawable.ic_featured_18px, R.drawable.ic_clear));
        editId.setOnTouchListener(new SetOnTouchListener(
                editId, R.drawable.ic_featured_18px));
        editSim.addTextChangedListener(new AddListenerOnTextChange(
                editSim, R.drawable.ic_sim_card_18px, R.drawable.ic_clear));
        editSim.setOnTouchListener(new SetOnTouchListener(
                editSim, R.drawable.ic_sim_card_18px));

        recyclerView = (RecyclerView) v.findViewById(R.id.listview);
        spnStatus = (Spinner) v.findViewById(R.id.spnStatus);
        spnMyOrder = (Spinner) v.findViewById(R.id.spnMyOrder);
        linMyOrder = (LinearLayout) v.findViewById(R.id.linMyOrder);
        tvNoData = (TextView) v.findViewById(R.id.tvNoData);
        tvOrderNew = (TextView) v.findViewById(R.id.tvOrderNew);
        tvCountOrder = (TextView) v.findViewById(R.id.tvCountOrder);

        tvNoData.setVisibility(GONE);
        linMyOrder.setVisibility(GONE);
        tvOrderNew.setVisibility(GONE);
        tvOrderNew.setPaintFlags(tvOrderNew.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        funcSearchName = "findDetailTaskForCheckIn";
        this.staff = StaffBusiness.getStaffByStaffCode(getContext(), Session.userName);
        this.staffState = getOmniStaffState();

        // init status
        ArrayList<Spin> lstSpinStatus = new ArrayList<>();
        lstSpinStatus.add(new Spin("", getString(R.string.all)));
        lstSpinStatus.add(new Spin("1", getString(R.string.request_new)));
        lstSpinStatus.add(new Spin("2", getString(R.string.request_processing)));
        lstSpinStatus.add(new Spin("3", getString(R.string.request_procesed)));
        Utils.setDataSpinner(getActivity(), lstSpinStatus, spnStatus);
        this.spnStatus.setSelection(1);
        this.status = "1";

        // init asyncTask findDetailTask
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

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

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDate = "";
                createUser = "";
                if ((editId.getText().length() == 0)
                        && (editIsdnContact.getText().length() == 0)
                        && (editOrder.getText().length() == 0)
                        && (editSim.getText().length() == 0)
                        && Constant.OMNI_STAFF_NV.equals(staffState)) {

                    CommonActivity.createAlertDialog(getActivity(),
                            getString(R.string.order_validate_input_search),
                            getString(R.string.app_name)).show();
                } else {
                    tvCountOrder.setVisibility(GONE);
                    findDetailTask();
                }
            }
        });

        btnCreateNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createOrderDialog = new CreateOrderDialog(activity, SearchOrderOmniFragment.this);
                createOrderDialog.show();
            }
        });

        tvOrderNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgAction.setVisibility(GONE);
                tvCountOrder.setVisibility(GONE);
                findNewOrderForUser();
            }
        });

        imgAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrConnectionOrder = new ArrayList<>();
                for (ConnectionOrder connectionOrder : orderList ) {
                    if (connectionOrder.isCheckIdNo()) {
                        arrConnectionOrder.add(connectionOrder);
                    }
                }
                OrderOmniAdapter orderOmniAdapter = new OrderOmniAdapter(act);
                orderOmniAdapter.processMultiOrderHSDT(arrConnectionOrder);

            }
        });
        showUIbyStaffState(staff, staffState);
    }

    @Override
    protected void setPermission() {

    }

    public void findDetailTask() {

        if (CommonActivity.isNullOrEmpty(staff)) {
            CommonActivity.createAlertDialog(
                    getActivity(), R.string.data_syn_require, R.string.app_name).show();
            return;
        }

        tvCountOrder.setVisibility(View.GONE);
        FindDetailTaskAsyncTask findDetailTaskAsyncTask = new FindDetailTaskAsyncTask(
                funcSearchName,
                getActivity(),
                new OnPostExecuteListener<List<ConnectionOrder>>() {

                    @Override
                    public void onPostExecute(List<ConnectionOrder> result, String errorCode, String description) {
                        if (result.size() > 0) {
                            result = filterOrderCancel(result);
                        }
                        if (result.size() == 0) {
                            tvNoData.setVisibility(View.VISIBLE);
                        } else {
                            tvNoData.setVisibility(GONE);
                        }
                        orderList = result;

                        OrderOmniAdapter orderOmniAdapter = new OrderOmniAdapter(
                                getActivity(), orderActionState, result, SearchOrderOmniFragment.this, SearchOrderOmniFragment.this);
                        recyclerView.setAdapter(orderOmniAdapter);
                        orderOmniAdapter.notifyDataSetChanged();

                    }
                }, moveLogInAct);

        String shopId = "";
        String isdn = editSim.getText() == null ? ""
                : StringUtils.formatIsdn(editSim.getText().toString().trim());
        String idNo = editId.getText() == null ? ""
                : editId.getText().toString().trim();
        String orderCode = editOrder.getText() == null ? ""
                : editOrder.getText().toString().toUpperCase().trim();
        String isdnContact = editIsdnContact.getText() == null ? ""
                : StringUtils.formatMsisdn(editIsdnContact.getText().toString().trim());
        findDetailTaskAsyncTask.execute(isdnContact, "", idNo,
                isdn, orderCode, status, shopId, "", createUser, createDate);
    }

    private void findNewOrderForUser() {
        resetViewForFindNewOrder();
        createUser = staff.getStaffId() + "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        try {
            createDate = sdf.format(calendar.getTime());

        } catch (Exception e) {
            e.printStackTrace();
        }


        findDetailTask();
    }

    private List<ConnectionOrder> filterOrderCancel(List<ConnectionOrder> result) {
        ConnectionOrder connectionOrderTemp;
        for (int index = result.size() - 1; index >= 0; index--) {
            connectionOrderTemp = result.get(index);
            if (connectionOrderTemp.getStatus() == 4) {
                result.remove(connectionOrderTemp);
            }
        }
        return result;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        tvNoData.setVisibility(GONE);

        if (resultCode == getActivity().RESULT_OK && requestCode == 1011) {
            OrderOmniAdapter orderOmniAdapter = new OrderOmniAdapter(
                    getActivity(),
                    orderActionState,
                    new ArrayList<ConnectionOrder>(),
                    SearchOrderOmniFragment.this, this);
            recyclerView.setAdapter(orderOmniAdapter);
            orderOmniAdapter.notifyDataSetChanged();
        }
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
            if (CommonActivity.isNullOrEmpty(mEdittext.getText().toString().trim())) {
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
                if (!CommonActivity.isNullOrEmpty(mEdittext.getCompoundDrawables()[DRAWABLE_RIGHT])) {
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

    private void showUIbyStaffState(Staff staff, String staffState) {
        switch (staffState) {
            case Constant.OMNI_STAFF_INVALID:
                doCheckStaffArea(staff);
                break;
            case Constant.OMNI_STAFF_NV:
                orderActionState = OrderState.ORD_CHECKIN_ACT;
                linMyOrder.setVisibility(GONE);
                tvOrderNew.setVisibility(View.VISIBLE);
                funcSearchName = "findDetailTaskForCheckIn";
                status = "1";
                findNewOrderForUser();
                break;
            case Constant.OMNI_STAFF_CTV:
                orderActionState = OrderState.ORD_CONNECT_ACT;
                linMyOrder.setVisibility(View.VISIBLE);
                tvOrderNew.setVisibility(GONE);
                funcSearchName = "findDetailTaskAssignByUserId";
                initSpnStatusOrder();
                createDate = "";
                createUser = "";
                findDetailTask();
                break;
            default:
                break;
        }
    }

    private void initSpnStatusOrder() {
        funcSearchName = "findDetailTaskAssignByUserId";
        spnMyOrder.setVisibility(View.VISIBLE);

        ArrayList<Spin> lstSpinMyOrder = new ArrayList<>();
        lstSpinMyOrder.add(new Spin("1", getString(R.string.order_of_me)));
        lstSpinMyOrder.add(new Spin("2", getString(R.string.order_reciver_able)));
        Utils.setDataSpinner(getActivity(), lstSpinMyOrder, spnMyOrder);

        spnMyOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Spin spin = (Spin) arg0.getItemAtPosition(arg2);
                if (spin.getId().equals("1")) {
                    orderActionState = OrderState.ORD_CONNECT_ACT;
                    funcSearchName = "findDetailTaskAssignByUserId";
                } else {
                    orderActionState = OrderState.ORD_CLAIM_ACT;
                    funcSearchName = "findDetailTaskCandidateByUserId";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    private void doCheckStaffArea(final Staff staff) {
        FindMappingAreaForStaffAsyncTask findMappingAreaForStaffAsyncTask =
                new FindMappingAreaForStaffAsyncTask(activity, new OnPostExecuteListener<List<MapStaffArea>>() {
                    @Override
                    public void onPostExecute(List<MapStaffArea> result, String errorCode, String description) {
                        if (!"0".equals(errorCode)) {
                            CommonActivity.createAlertDialog(getActivity(),
                                    description,
                                    getString(R.string.app_name)).show();
                            return;
                        }
                        if (CommonActivity.isNullOrEmpty(result)) {
                            staffState = Constant.OMNI_STAFF_NV;
                        } else {
                            staffState = Constant.OMNI_STAFF_CTV;
                        }
                        saveOmniStaff(staffState);
                        showUIbyStaffState(staff, staffState);
                    }
                }, moveLogInAct);
        findMappingAreaForStaffAsyncTask.execute(staff.getStaffId() + "");
    }

    private void saveOmniStaff(String staffState) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(
                Constant.SHARE_PREFERENCES_FILE, activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(Constant.OMNI_STAFF_NAME_SAVE, staffState);
        edit.commit();
    }

    private String getOmniStaffState() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(
                Constant.SHARE_PREFERENCES_FILE, activity.MODE_PRIVATE);
        return sharedPreferences.getString(Constant.OMNI_STAFF_NAME_SAVE,
                Constant.OMNI_STAFF_INVALID);
    }

    private void resetViewForFindNewOrder() {
        spnStatus.setSelection(1);
        status = "1";
        editOrder.setText("");
        editId.setText("");
        editSim.setText("");
        editIsdnContact.setText("");
    }

    public void setView(List<ConnectionOrder> listconnectionOrder) {
        for (ConnectionOrder connectionOrder : listconnectionOrder) {
            if (connectionOrder.isCheckIdNo()) {
                imgAction.setVisibility(View.VISIBLE);
                return;
            }
        }
        imgAction.setVisibility(GONE);
    }

    public void setViewLabel(List<ConnectionOrder> listconnectionOrder) {
        int countOrder = 0;
        for (ConnectionOrder connectionOrder : listconnectionOrder) {
            if (connectionOrder.isCheckIdNo()) {
                countOrder ++;
            }
        }
        tvCountOrder.setVisibility(View.VISIBLE);
        tvCountOrder.setText(act.getString(R.string.count_order, countOrder));
    }
}


