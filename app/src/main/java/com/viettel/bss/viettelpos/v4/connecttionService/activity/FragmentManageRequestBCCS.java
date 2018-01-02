package com.viettel.bss.viettelpos.v4.connecttionService.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.DeleteListAsyn;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.GetLstcustomerOrderDetailMbccsDTOAsyn;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.GetReasonFullPMAsyn;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ReasonDTO;
import com.viettel.bss.viettelpos.v3.connecttionService.activity.FragmentInfoCustomerBCCS;
import com.viettel.bss.viettelpos.v3.connecttionService.model.CustomerOrderDetailDTO;
import com.viettel.bss.viettelpos.v3.connecttionService.model.ProductCatalogDTO;
import com.viettel.bss.viettelpos.v3.connecttionService.model.SubInfrastructureDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetListRequestAdaperBCCS;
import com.viettel.bss.viettelpos.v4.connecttionService.asyn.GetListCustomerOrderDetailNokByStaffCodeAsyn;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.AreaBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.CustomerOrderDetailClone;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.CustomerOrderDetailMbccsDTO;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.StatusBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.TelecomServiceBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetServiceDal;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.sale.business.StaffBusiness;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;


@SuppressLint("SimpleDateFormat")
public class FragmentManageRequestBCCS extends FragmentCommon implements OnClickListener {
    private Date dateFrom = null;
    private Date dateTo = null;
    private EditText tvFromDate;
    private EditText tvToDate;
    private LinearLayout ll_createUser;
    private final String tag = FragmentManageRequestBCCS.class.getName();
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat sdfV = new SimpleDateFormat("dd/MM/yyy");
    GetListRequestAdaperBCCS.OnClickRequestBCCS onClickRequestBCCS;
    private Button btn_search;
    private EditText edit_service, edit_chooseRequest;
    private Spinner spinner_status;
    private EditText edit_isdnacount;
    private EditText edit_mayeucau, edit_createUser;
    // init input for webservice
    private String isdnOrAccount;
    private String serviceType;
    private String subRequestId, actionCode;
    private boolean showInfoSearch = true;
    private ArrayList<TelecomServiceBeans> arrTelecomServiceBeans = new ArrayList<>();
    private HashMap<String, String> mapTelecomService = new HashMap<>();
    public ArrayList<CustomerOrderDetailMbccsDTO> arrRequestBeans = new ArrayList<>();
    public GetListRequestAdaperBCCS adaRequestAdapter;
    public CustomerOrderDetailMbccsDTO requestBeansItem = new CustomerOrderDetailMbccsDTO();
    public RecyclerView lvRequest;
    private final ArrayList<StatusBeans> arrStatusBeans = new ArrayList<>();

    private String telecomServiceId = "";
    private String typeStatus = "";

    String account = "";
    public static FragmentManageRequestBCCS fragInstance = null;
    private Dialog dialogSelectReason;
    private List<ReasonDTO> arrReasonDTO = new ArrayList<ReasonDTO>();
    private List<Staff> listStaff = new ArrayList<>();
    private List<ReasonDTO> arrReasonSearch = new ArrayList<ReasonDTO>();
    private ReasonDTO reasonDTO;

    // actioncode 542
    private CustomerOrderDetailMbccsDTO custDTO;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragInstance = this;
        View mView = inflater.inflate(
                R.layout.connection_layout_manager_request_bccs, container, false);
        unitView(mView);
        return mView;
    }

    @Override
    protected void unit(View v) {

    }

    @Override
    protected void setPermission() {

    }

    @Override
    public void onResume() {
        Log.e(tag, "onResume");
        MainActivity.getInstance().setTitleActionBar(R.string.manager_request);
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        Calendar cal = Calendar.getInstance();
        switch (v.getId()) {
            case R.id.edtFromDate:
                cal.setTime(dateFrom);
                DatePickerDialog fromDateDialog = new FixedHoloDatePickerDialog(
                        getActivity(), AlertDialog.THEME_HOLO_LIGHT, fromDatePickerListener, cal.get(Calendar.YEAR)
                        , cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                fromDateDialog.show();
                break;
            case R.id.edtToDate:
                cal.setTime(dateTo);
                DatePickerDialog toDateDialog = new FixedHoloDatePickerDialog(getActivity(),
                        AlertDialog.THEME_HOLO_LIGHT, toDatePickerListener, cal.get(Calendar.YEAR)
                        , cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                toDateDialog.show();
                break;
            case R.id.relaBackHome:
                getActivity().onBackPressed();
                break;
            case R.id.btn_search:

                // servicetype
                if (validateSearch())
                    searchRequestBeans();

                break;
            default:
                break;
        }

    }

    private void searchRequestBeans() {
        String requestId = edit_mayeucau.getText().toString();
        String createUser = edit_createUser.getText().toString();
        String dateFromString = null;
        String dateToString = null;
        try {
            dateFromString = sdf.format(sdfV.parse(tvFromDate.getText().toString()));
            dateToString = sdf.format(sdfV.parse(tvToDate.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dateFrom != null && dateTo != null) {
            if (dateFrom.after(dateTo)) {
                Dialog dialog = CommonActivity.createAlertDialog(getActivity(), getResources().getString(R.string.checktimeupdatejob), getResources().getString(R.string.app_name));
                dialog.show();
            } else {

                if (CommonActivity.isNetworkConnected(getActivity())) {

                    long day = getDateDiff(dateFrom, dateTo, TimeUnit.MINUTES);
                    Log.d("dayyyyyyyyyyyy", "" + day);
                    if (day <= 30) {
                        if (arrRequestBeans.size() > 0
                                && adaRequestAdapter != null) {
                            arrRequestBeans.clear();
                            adaRequestAdapter.notifyDataSetChanged();
                        }
                        GetLstcustomerOrderDetailMbccsDTOAsyn getLstCustOrderDetailAsyn = new GetLstcustomerOrderDetailMbccsDTOAsyn(getActivity(),
                                new OnPosGetLstCustOrderDetail(), moveLogInAct);
                        getLstCustOrderDetailAsyn.execute(requestId, telecomServiceId
                                , isdnOrAccount, dateFromString, dateToString, typeStatus, createUser, actionCode);

                    } else {
                        if (arrRequestBeans.size() > 0
                                && adaRequestAdapter != null) {
                            arrRequestBeans.clear();
                            adaRequestAdapter.notifyDataSetChanged();
                        }
                        Dialog dialog = CommonActivity.createAlertDialog(getActivity(), getActivity().getResources().getString(
                                R.string.checkrequest30), getResources().getString(R.string.app_name));
                        dialog.show();
                    }

                } else {
                    Dialog dialog = CommonActivity.createAlertDialog(
                            getActivity(),
                            getResources().getString(R.string.errorNetwork),
                            getResources().getString(R.string.app_name));
                    dialog.show();
                }
            }
        }
    }

    private boolean validateSearch() {
        String requestId = edit_mayeucau.getText().toString();
        isdnOrAccount = edit_isdnacount.getText().toString();
        if (!CommonActivity.isNullOrEmpty(isdnOrAccount) && CommonActivity.isNullOrEmpty(telecomServiceId)) {
            Dialog dialog = CommonActivity.createAlertDialog(getActivity(), getResources().getString(R.string.please_input_service),
                    getResources().getString(R.string.app_name));
            dialog.show();
            return false;
        }

        if (CommonActivity.isNullOrEmpty(actionCode)) {
            Dialog dialog = CommonActivity.createAlertDialog(getActivity(), getResources().getString(R.string.please_input_request),
                    getResources().getString(R.string.app_name));
            dialog.show();
            return false;
        }
        return true;
    }

    public class OnPosGetLstCustOrderDetail implements OnPostExecuteListener<List<CustomerOrderDetailMbccsDTO>> {
        @Override
        public void onPostExecute(List<CustomerOrderDetailMbccsDTO> result, String errorCode, String description) {
            arrRequestBeans = new ArrayList<>();
            List<String> liststatus = new ArrayList(Arrays.asList("02", "03", "06", "12", "33", "36", "48"));
            String ids = ",4,3,9,19,23,25,28,35,45,";
            if (!CommonActivity.isNullOrEmpty(result)) {
                for (CustomerOrderDetailMbccsDTO cus : result)
                    if (liststatus.contains(cus.getCustomerOrderDetail().getStatus())
                            && ids.contains("," + cus.getCustomerOrderDetail().getTelecomServiceId() + ",")) {
                        arrRequestBeans.add(cus);
                    }
            }
            Comparator<CustomerOrderDetailMbccsDTO> s = new Comparator<CustomerOrderDetailMbccsDTO>() {
                @Override
                public int compare(CustomerOrderDetailMbccsDTO o1, CustomerOrderDetailMbccsDTO o2) {
                    CustomerOrderDetailDTO c1 = o1.getCustomerOrderDetail();
                    CustomerOrderDetailDTO c2 = o2.getCustomerOrderDetail();
                    return (c1.getEffectDate() + c1.getCustOrderDetailId()).compareTo((c2.getEffectDate() + c2.getCustOrderDetailId()));
                }
            };
            Collections.sort(arrRequestBeans, s);
            if ("0".equals(errorCode)) {
                if (!CommonActivity.isNullOrEmpty(arrRequestBeans)) {
                    SharedPreferences preferences = getActivity().getSharedPreferences(Define.PRE_NAME, Activity.MODE_PRIVATE);
                    String name = preferences.getString(Define.KEY_MENU_NAME, "");
                    adaRequestAdapter = new GetListRequestAdaperBCCS(getActivity(), arrRequestBeans, onClickRequestBCCS, mapTelecomService, name);
                    lvRequest.setAdapter(adaRequestAdapter);
                    adaRequestAdapter.notifyDataSetChanged();
                    if (CommonActivity.isNullOrEmptyArray(arrRequestBeans)) {

                        if (showInfoSearch)
                            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notrequestDetal),
                                    getActivity().getString(R.string.app_name)).show();
                        lvRequest.setVisibility(View.GONE);
                    } else {
                        lvRequest.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (showInfoSearch)
                        CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notrequestDetal), getActivity().getString(R.string.app_name))
                                .show();
                }
            } else {
                arrRequestBeans = new ArrayList<CustomerOrderDetailMbccsDTO>();
                if (adaRequestAdapter != null) {
                    adaRequestAdapter.setLstData(arrRequestBeans);
                    adaRequestAdapter.notifyDataSetChanged();
                }
                lvRequest.setVisibility(View.GONE);
                if (!CommonActivity.isNullOrEmpty(description)) {
                    CommonActivity
                            .createAlertDialog(getActivity(), description, getActivity().getString(R.string.app_name))
                            .show();
                }
            }
        }
    }


    private void unitView(View view) {
        tvFromDate = (EditText) view.findViewById(R.id.edtFromDate);
        tvFromDate.setOnClickListener(this);
        tvToDate = (EditText) view.findViewById(R.id.edtToDate);
        tvToDate.setOnClickListener(this);
        btn_search = (Button) view.findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);
        edit_service = (EditText) view.findViewById(R.id.edit_service);
        edit_chooseRequest = (EditText) view.findViewById(R.id.edit_chooseRequest);
        edit_isdnacount = (EditText) view.findViewById(R.id.edit_isdnacount);
        edit_mayeucau = (EditText) view.findViewById(R.id.edit_mayeucau);
        edit_createUser = (EditText) view.findViewById(R.id.edit_createUser);
        spinner_status = (Spinner) view.findViewById(R.id.spinner_status);
        lvRequest = (RecyclerView) view.findViewById(R.id.lisrequest);
        ll_createUser = (LinearLayout) view.findViewById(R.id.ll_createUser);

        edit_chooseRequest.setText("Đấu nối");
        actionCode = "00";
        listStaff = getListStaff();
        edit_createUser.setText(Session.userName);
        if (CommonActivity.isNullOrEmpty(listStaff) || listStaff.size() == 1) {
            edit_createUser.setClickable(false);
            ll_createUser.setVisibility(View.GONE);
        } else {
            edit_createUser.setClickable(true);
            edit_createUser.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<String> list = new ArrayList<String>();
                    if (!CommonActivity.isNullOrEmpty(listStaff)) {
                        for (Staff e : listStaff)
                            list.add(e.getStaffCode() + "\n" + e.getName());
                    }
                    Intent intent = new Intent(getActivity(),
                            SearchActivity.class);
                    intent.putExtra("list", (Serializable) list);
                    intent.putExtra("hint", (Serializable) "Nhập tên hoặc mã để tìm kiếm");
                    intent.putExtra("tittle", (Serializable) getActivity().getResources().getString(R.string.manager_request));
                    startActivityForResult(intent, 9999);
                }
            });
        }
        edit_chooseRequest.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> list = new ArrayList(Arrays.asList("Đấu nối", "Chuyển đổi CN"));
                Intent intent = new Intent(getActivity(),
                        SearchActivity.class);
                intent.putExtra("list", (Serializable) list);
                intent.putExtra("hint", (Serializable) "Nhập tên để tìm kiếm");
                intent.putExtra("tittle", (Serializable) getActivity().getResources().getString(R.string.manager_request));
                startActivityForResult(intent, 6868);
            }
        });
        lvRequest.setHasFixedSize(true);
        lvRequest.setLayoutManager(new LinearLayoutManager(getActivity()));

        onClickRequestBCCS = new GetListRequestAdaperBCCS.OnClickRequestBCCS() {
            @Override
            public void onClickRequest(CustomerOrderDetailMbccsDTO customerOrderDetailDTO, int id) {
                if (id == R.id.btnDelete) {
                    requestBeansItem = customerOrderDetailDTO;
                    GetReasonFullPMAsyn getReasonFullPMAsyn = new GetReasonFullPMAsyn(getActivity(),
                            new OnPostGetReasonFull(), moveLogInAct);
                    getReasonFullPMAsyn.execute("", "542", "", "", "", "", "");
                } else {
                    custDTO = customerOrderDetailDTO;
                    if ("12".equals(custDTO.getCustomerOrderDetail().getStatus())) {
                        GetListCustomerOrderDetailNokByStaffCodeAsyn getListCustomerOrderDetailNokByStaffCodeAsyn =
                                new GetListCustomerOrderDetailNokByStaffCodeAsyn(getActivity(),onPostGetStaffNot,moveLogInAct);
                        getListCustomerOrderDetailNokByStaffCodeAsyn.execute();
                    }
                }
            }
        };

        isdnOrAccount = edit_isdnacount.getText().toString();
        Log.d("isdnOrAccount", isdnOrAccount);
        subRequestId = edit_mayeucau.getText().toString();
        Log.d("subRequestId", subRequestId);
        initValue();
        initTelecomService();
        if (arrStatusBeans != null && arrRequestBeans.size() > 0) {
            arrStatusBeans.clear();
        }

        initSpinerStatus();

        spinner_status.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                typeStatus = arrStatusBeans.get(arg2).getTypeStatus();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

    }

    // ==== add list status =====
    private void addStatus() {
        arrStatusBeans.add(new StatusBeans("12", getActivity().getResources()
                .getString(R.string.statusRq12)));
        arrStatusBeans.add(new StatusBeans("33", getActivity().getResources()
                .getString(R.string.statusRq33)));
        arrStatusBeans.add(new StatusBeans("36", getActivity().getResources()
                .getString(R.string.statusRq36)));
        arrStatusBeans.add(new StatusBeans("48", getActivity().getResources()
                .getString(R.string.statusRq48)));
        arrStatusBeans.add(new StatusBeans("02", getActivity().getResources()
                .getString(R.string.statusRq02)));
        arrStatusBeans.add(new StatusBeans("03", getActivity().getResources()
                .getString(R.string.statusRq03)));
        arrStatusBeans.add(new StatusBeans("06", getActivity().getResources()
                .getString(R.string.statusRq06)));
    }

    // init spiner status
    private void initSpinerStatus() {
        addStatus();
        if (arrStatusBeans != null && arrStatusBeans.size() > 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getActivity(), android.R.layout.simple_dropdown_item_1line,
                    android.R.id.text1);
            for (StatusBeans statusBeans : arrStatusBeans) {
                adapter.add(statusBeans.getNameStatus());
            }
            spinner_status.setAdapter(adapter);
        }

    }

    // lay thong tin user
    public List<Staff> getListStaff() {
        List<Staff> result = StaffBusiness.getLstStaffByStaffMngt(getContext(), Session.userName);
        return result;
    }

    // init typepaper
    private void initTelecomService() {
        try {

            GetServiceDal dal = new GetServiceDal(getActivity());
            dal.open();
            ArrayList<TelecomServiceBeans> tmp = dal.getlisTelecomServiceBeans();
            dal.close();
            String ids = ",4,3,9,19,23,25,28,35,45,";
            arrTelecomServiceBeans = new ArrayList<>();

            for (TelecomServiceBeans e : tmp)
                if (ids.contains("," + e.getTelecomServiceId() + ",")) {
                    if (e.getTele_service_name().equals("Multiscreen"))
                        e.setTele_service_name("Multiscreen 1 chiều");
                    arrTelecomServiceBeans.add(e);
                    mapTelecomService.put(e.getTelecomServiceId(), e.getTele_service_name());
                }
            Comparator<TelecomServiceBeans> s = new Comparator<TelecomServiceBeans>() {
                @Override
                public int compare(TelecomServiceBeans o1, TelecomServiceBeans o2) {
                    return o1.getTele_service_name().compareTo(o2.getTele_service_name());
                }
            };
            Collections.sort(arrTelecomServiceBeans, s);
            edit_service.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    ArrayList<String> list = new ArrayList<String>();
                    list.add("Tất cả");
                    if (!CommonActivity.isNullOrEmpty(arrTelecomServiceBeans)) {
                        for (TelecomServiceBeans e : arrTelecomServiceBeans)
                            list.add(e.getTele_service_name());
                    }
                    Intent intent = new Intent(getActivity(),
                            SearchActivity.class);
                    intent.putExtra("list", (Serializable) list);
                    intent.putExtra("hint", (Serializable) "Nhập tên dịch vụ để tìm kiếm");
                    intent.putExtra("tittle", (Serializable) getActivity().getResources().getString(R.string.manager_request));
                    startActivityForResult(intent, 1235);
                }
            });
        } catch (Exception e) {
            Log.e("initTypePaper", e.toString());
        }
    }

    private void initValue() {
        Calendar cal = Calendar.getInstance();
        tvToDate.setText(sdfV.format(cal.getTime()));
        try {
            dateTo = sdf.parse(sdf.format(cal.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        cal.set(Calendar.DAY_OF_MONTH, 1);
        tvFromDate.setText(sdfV.format(cal.getTime()));
        try {
            dateFrom = sdf.parse(sdf.format(cal.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final DatePickerDialog.OnDateSetListener fromDatePickerListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            Calendar cal = Calendar.getInstance();
            cal.set(selectedYear, selectedMonth, selectedDay);
            try {
                dateFrom = sdf.parse(sdf.format(cal.getTime()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            tvFromDate.setText(sdfV.format(dateFrom));
        }
    };
    private final DatePickerDialog.OnDateSetListener toDatePickerListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            Calendar cal = Calendar.getInstance();
            cal.set(selectedYear, selectedMonth, selectedDay);
            try {
                dateTo = sdf.parse(sdf.format(cal.getTime()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            tvToDate.setText(sdfV.format(dateTo));
        }
    };

    // ===== get distance day < 30 day
    private static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return diffInMillies / (24 * 60 * 60 * 1000);
    }

    // move login
    private final OnClickListener moveLogInAct = new OnClickListener() {

        @Override
        public void onClick(View v) {
            LoginDialog dialog = new LoginDialog(getActivity(),
                    ";cm.req.management;");

            dialog.show();

        }
    };


    private class OnPostGetReasonFull implements OnPostExecuteListener<List<ReasonDTO>> {
        @Override
        public void onPostExecute(List<ReasonDTO> result, String errorCode, String description) {
            arrReasonDTO = new ArrayList<ReasonDTO>();
            if (!CommonActivity.isNullOrEmptyArray(result)) {
                arrReasonDTO.addAll(result);
            } else {
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notdatareason),
                        getActivity().getString(R.string.app_name)).show();
            }
            showDiaLogSelectReason(arrReasonDTO);
        }
    }

    private void showDiaLogSelectReason(final List<ReasonDTO> result) {

        dialogSelectReason = new Dialog(getActivity());
        dialogSelectReason.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSelectReason
                .setContentView(R.layout.connection_layout_select_reason);
        final EditText editSearch = (EditText) dialogSelectReason.findViewById(R.id.editSearch);
        final ListView lvReason = (ListView) dialogSelectReason.findViewById(R.id.lstreason);


        List<String> reseanString = new ArrayList<>();
        arrReasonSearch = onSearchBeans(result, editSearch.getText().toString());
        for (ReasonDTO reasonDTO : arrReasonSearch)
            reseanString.add(reasonDTO.toString());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1, reseanString);



        lvReason.setAdapter(adapter);


        final View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRequestBean();
            }
        };

        lvReason.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                reasonDTO = arrReasonSearch.get(position);
                CommonActivity.createDialog(getActivity()
                        , getActivity().getResources().getString(
                                R.string.delete_request_confirm, requestBeansItem.getCustomerOrderDetail().getCustOrderDetailId() + ""),
                        getActivity().getResources().getString(
                                R.string.app_name),
                        getActivity().getResources().getString(R.string.say_ko),
                        getActivity().getResources().getString(R.string.say_co),
                        null, onClickListener).show();
            }
        });
        Button btncancel = (Button) dialogSelectReason
                .findViewById(R.id.btnhuy);
        btncancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialogSelectReason.dismiss();
            }
        });


        editSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                List<String> strings = new ArrayList<String>();
                arrReasonSearch = onSearchBeans(result, editSearch.getText().toString());
                for (ReasonDTO reasonDTO : arrReasonSearch)
                    strings.add(reasonDTO.toString());
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        getActivity(),
                        android.R.layout.simple_dropdown_item_1line,
                        android.R.id.text1, strings);
                lvReason.setAdapter(adapter);
            }
        });

        dialogSelectReason.show();
    }

    private List<ReasonDTO> onSearchBeans(List<ReasonDTO> reasonDTOs, String keySearch) {

        if (keySearch.length() == 0) {
            return reasonDTOs;
        } else {
            List<ReasonDTO> result = new ArrayList<>();
            for (ReasonDTO re : reasonDTOs) {
                if (re.toString().toLowerCase().contains(keySearch.toLowerCase()))
                    result.add(re);
            }
            return result;
        }

    }

    private void deleteRequestBean() {
        ArrayList<CustomerOrderDetailDTO> list = new ArrayList<>();
        list.add(requestBeansItem.getCustomerOrderDetail());
        DeleteListAsyn deleteListAsyn = new DeleteListAsyn(getActivity(), new OnPostExecuteListener<ParseOuput>() {
            @Override
            public void onPostExecute(ParseOuput result, String errorCode, String description) {
                if ("0".equals(errorCode)) {
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.cancel_register_roll_up_success),
                            getActivity().getString(R.string.app_name));
                    requestBeansItem = null;
                    showInfoSearch = false;
                    searchRequestBeans();
                    showInfoSearch = true;
                    dialogSelectReason.dismiss();
                    dialog.show();
                } else {
                    if (CommonActivity.isNullOrEmpty(description)) {
                        description = getActivity().getString(R.string.fails_not_description, "xóa yêu cầu");
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                            getActivity().getString(R.string.app_name));
                    dialogSelectReason.dismiss();
                    dialog.show();
                }
            }
        }, moveLogInAct, list);
        if (!CommonActivity.isNullOrEmpty(reasonDTO) && !CommonActivity.isNullOrEmpty(reasonDTO.getReasonId()))
            deleteListAsyn.execute(reasonDTO.getReasonId(), requestBeansItem.getCustomerOrderDetail().getActionCode());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 1235:
                    String select = data.getExtras().getString("select");
                    if (!select.equals("Tất cả")) {
                        for (TelecomServiceBeans te : arrTelecomServiceBeans) {
                            if (te.getTele_service_name().equals(select)) {
                                serviceType = te
                                        .getServiceAlias();
                                edit_service.setText(te.getTele_service_name());
                                Log.d("serviceType", serviceType);
                                telecomServiceId = te
                                        .getTelecomServiceId();
                                Log.d("telecomServiceId", telecomServiceId);
                            }
                        }

                    } else {
                        serviceType = "";
                        telecomServiceId = "";
                        edit_service.setText("");
                        edit_service.setHint("Tất cả");
                    }
                    if (CommonActivity.isNullOrEmpty(arrRequestBeans)) {
                        SharedPreferences preferences = getActivity().getSharedPreferences(Define.PRE_NAME, Activity.MODE_PRIVATE);
                        String name = preferences.getString(Define.KEY_MENU_NAME, "");
                        adaRequestAdapter = new GetListRequestAdaperBCCS(getActivity(), arrRequestBeans, onClickRequestBCCS, mapTelecomService, name);
                        lvRequest.setAdapter(adaRequestAdapter);
                        adaRequestAdapter.notifyDataSetChanged();
                    }
                    break;
                case 9999:
                    String staffCode = data.getExtras().getString("select");
                    if (CommonActivity.isNullOrEmpty(staffCode)) {
                        edit_createUser.setText(Session.userName);
                    } else {
                        edit_createUser.setText(staffCode.split("\n")[0]);
                    }
                    break;
                case 6868:
                    String request = data.getExtras().getString("select");
                    if (!CommonActivity.isNullOrEmpty(request)) {
                        edit_chooseRequest.setText(request);
                        if (request.equals("Đấu nối")) {
                            actionCode = "00";
                        } else {
                            actionCode = "549";
                        }
                    }
                    break;

                default:
                    break;
            }
        }
    }

    private AreaBean getAreaBeanBySubInfrastructureDTO(SubInfrastructureDTO subInfrastructureDTO) {
        AreaBean areaBean = new AreaBean();
        areaBean.setProvince(subInfrastructureDTO.getProvince());
        areaBean.setDistrict(subInfrastructureDTO.getDistrict());
        areaBean.setPrecinct(subInfrastructureDTO.getPrecinct());
        if (!CommonActivity.isNullOrEmpty(subInfrastructureDTO.getStreetBlock())) {
            areaBean.setStreetBlock(subInfrastructureDTO.getStreetBlock());
        }
        areaBean.setFullAddress(subInfrastructureDTO.getAddress());

        return areaBean;
    }
    OnPostExecuteListener<ParseOuput> onPostGetStaffNot = new  OnPostExecuteListener<ParseOuput>(){

        @Override
        public void onPostExecute(ParseOuput result, String errorCode, String description) {
//            if("0".equals(result.getErrorCode())){
//                if(result.getLimitPobas() > 0){
//                    CommonActivity.createAlertDialog(getActivity(),getActivity().getString(R.string.qoutarequets,result.getLimitPobas()),getActivity().getString(R.string.app_name)).show();
//                }else{
                    Bundle bundle = new Bundle();
                    bundle.putString("serviceTypeKey", custDTO.getTelecomServiceAlias());
                    bundle.putSerializable("resultSurveyOnlineForBccs2Form", custDTO.getResultSurveyOnlineForBccs2Form());

                    AreaBean areaBean = CommonActivity.isNullOrEmpty(custDTO.getLstSubInfrastructureS()) ?
                            new AreaBean() : getAreaBeanBySubInfrastructureDTO(custDTO.getLstSubInfrastructureS().get(0));
                    bundle.putSerializable("areaBeanKey", areaBean);

                    List<ProductCatalogDTO> lstProductCatalog = new ArrayList<ProductCatalogDTO>();
                    ProductCatalogDTO productCatalogDTO = new ProductCatalogDTO();
                    productCatalogDTO.setTelecomServiceId(Integer.valueOf(custDTO.getCustomerOrderDetail().getTelecomServiceId() + ""));
                    productCatalogDTO.setTelServiceAlias(custDTO.getTelecomServiceAlias());
                    productCatalogDTO.setDescription(custDTO.getTelecomServiceName());
                    productCatalogDTO.setName(custDTO.getTelecomServiceName());
                    productCatalogDTO.setQuantity(1);
                    lstProductCatalog.add(productCatalogDTO);
                    bundle.putSerializable("lstProductCatalog", (Serializable) lstProductCatalog);
                    String accountGline = CommonActivity.isNullOrEmpty(custDTO.getGroupsDTO()) ?
                            "" : custDTO.getGroupsDTO().getCode();
                    if (!CommonActivity.isNullOrEmpty(accountGline)) {
                        bundle.putString("accountGline", accountGline);
                    }
                    bundle.putSerializable("groupsDTO", custDTO.getGroupsDTO());

                    bundle.putString("contactName", custDTO.getCustomerOrderDetail().getContactName());
                    if (!CommonActivity.isNullOrEmpty(custDTO.getCustomerOrderDetail())) {
                        ArrayList<CustomerOrderDetailClone> list = new ArrayList<>();
                        CustomerOrderDetailClone customerOrderDetailId = new CustomerOrderDetailClone();
                        customerOrderDetailId.setCustOrderDetailId(custDTO.getCustomerOrderDetail().getCustOrderDetailId());
                        customerOrderDetailId.setSubId(custDTO.getCustomerOrderDetail().getSubId());
                        customerOrderDetailId.setTelecomServiceId(custDTO.getCustomerOrderDetail().getTelecomServiceId());
                        customerOrderDetailId.setTelecomServiceId(custDTO.getCustomerOrderDetail().getTelecomServiceId());
                        list.add(customerOrderDetailId);
                        bundle.putSerializable("customerOrderDetailId", list);
                    }
                    if (!CommonActivity.isNullOrEmpty(custDTO.getCustomer())) {
                        bundle.putSerializable("subscriberDTOSelect", custDTO.getCustomer());
                    }

                    // them tham so truyen sang hotline
                    if(!CommonActivity.isNullOrEmpty(custDTO) && !CommonActivity.isNullOrEmpty(custDTO.getCustomerOrderDetail()) && !CommonActivity.isNullOrEmpty(custDTO.getCustomerOrderDetail().getRequestExtId()) && custDTO.getCustomerOrderDetail().getRequestExtId() != 0){
                        bundle.putString("requestExtId", custDTO.getCustomerOrderDetail().getRequestExtId() + "");
                    }

                    FragmentInfoCustomerBCCS fragmentInfoCustomerMobileBCCS = new FragmentInfoCustomerBCCS();
                    fragmentInfoCustomerMobileBCCS.setArguments(bundle);
                    fragmentInfoCustomerMobileBCCS.setTargetFragment(fragmentInfoCustomerMobileBCCS, 1100);
                    ReplaceFragment.replaceFragment(getActivity(), fragmentInfoCustomerMobileBCCS, false);
//                }
//            }else{
//                String des = result.getDescription();
//                if(CommonActivity.isNullOrEmpty(des)){
//                    des = getActivity().getString(R.string.no_data);
//                }
//                CommonActivity.createAlertDialog(getActivity(),des,getActivity().getString(R.string.app_name)).show();
//            }
        }
    };

}
