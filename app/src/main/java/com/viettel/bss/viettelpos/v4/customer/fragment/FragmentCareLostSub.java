package com.viettel.bss.viettelpos.v4.customer.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionService.handler.AssignIsdnHandler;
import com.viettel.bss.viettelpos.v4.connecttionService.listener.ExpandableHeightListView;
import com.viettel.bss.viettelpos.v4.customer.adapter.ListIsdnAssignAdapter;
import com.viettel.bss.viettelpos.v4.customer.adapter.ObjectTypeAdapter;
import com.viettel.bss.viettelpos.v4.customer.adapter.ReasonCareSubAdapter.OnCheckReason;
import com.viettel.bss.viettelpos.v4.customer.adapter.ReasonCareSubAdapter.OnNextReason;
import com.viettel.bss.viettelpos.v4.customer.adapter.ReasonCareSubExpandAdapter;
import com.viettel.bss.viettelpos.v4.customer.adapter.ReasonPropertyAdapter;
import com.viettel.bss.viettelpos.v4.customer.adapter.ViewCareHistoryAdapter;
import com.viettel.bss.viettelpos.v4.customer.asynctask.AsyncGetProgramBOC;
import com.viettel.bss.viettelpos.v4.customer.object.AssignIsdnStaffBean;
import com.viettel.bss.viettelpos.v4.customer.object.BOCOutput;
import com.viettel.bss.viettelpos.v4.customer.object.DataReasonMapping;
import com.viettel.bss.viettelpos.v4.customer.object.ObjectProperty;
import com.viettel.bss.viettelpos.v4.customer.object.ReasonCusCare;
import com.viettel.bss.viettelpos.v4.customer.object.ReasonCusCareProperty;
import com.viettel.bss.viettelpos.v4.customer.object.ReasonCusCareResult;
import com.viettel.bss.viettelpos.v4.customer.object.ReportCusCareBean;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.customer.object.ViewCareHistoryBO;
import com.viettel.bss.viettelpos.v4.dal.ApParamDAL;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.dialog.SubInfoDetailDialogFragment;
import com.viettel.bss.viettelpos.v4.object.CSKHBO;
import com.viettel.bss.viettelpos.v4.object.ProgramBO;
import com.viettel.bss.viettelpos.v4.object.ProgramPropertyBO;
import com.viettel.bss.viettelpos.v4.object.ProgramStatusBO;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentManageSubscriber;
import com.viettel.bss.viettelpos.v4.sale.strategy.BlockOpenSubManagerFragment;
import com.viettel.bss.viettelpos.v4.sale.adapter.SubscriberInfoAdapter;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskSearchSubscriber;
import com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.sale.strategy.ChangeProductFragmentStrategy;
import com.viettel.bss.viettelpos.v4.sale.strategy.ChangePromotionFragmentStrategy;
import com.viettel.bss.viettelpos.v4.sale.strategy.ManageSubscriberFragmentStrategy;
import com.viettel.bss.viettelpos.v4.sale.strategy.TickAppointFragmentStrategy;
import com.viettel.bss.viettelpos.v4.ui.DateTime;
import com.viettel.bss.viettelpos.v4.ui.DateTimePicker;
import com.viettel.bss.viettelpos.v4.ui.SimpleDateTimePicker;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class FragmentCareLostSub extends FragmentCommon implements
        OnNextReason, OnCheckReason {

    private EditText edtStaff;
    private EditText edtIsdn;
    private Spinner spnStatus;
    private LinearLayout lnCustomerType;
    private Spinner spnCustomerType;
    private Spinner spnCare;
    private String careId = "";
    private ListView lvItem;
    private ArrayList<AssignIsdnStaffBean> lstAssignIsdnStaffBean = new ArrayList<>();
    private ListIsdnAssignAdapter adapter;
    private Dialog dialogUpdate;
    private AssignIsdnStaffBean selectedIsdn;
    private EditText edtNote;
    private final Map<String, ReasonCusCareResult> mapReason = new HashMap<>();
    private final Map<Integer, ArrayList<ObjectProperty>> mapObjectProperty = new HashMap<>();
    // private ListView lvReason;
    private ExpandableListView lvReason;
    // private ReasonCareSubAdapter reasonAdapter;
    private ReasonCareSubExpandAdapter reasonAdapter;
    private TextView tvReasonGroup;
    private TextView tvLastReason;

    private final Map<Long, ReasonCusCareResult> mapReasonSelected = new LinkedHashMap<>();
    private boolean isMultiChoice = false;
    private final ArrayList<Long> lastReasonLevel = new ArrayList<>();
    private final ArrayList<Long> lstReasonIdSelect = new ArrayList<>();
    private Button btnNextReasonLevel;
    private CheckBox cbSelectAll;
    private Button btnBook;
    private Button btnCusCare;
    // private LinearLayout lnSelectdate;
    private LinearLayout lnTieuDe;
    private TextView tvTitle;
    private EditText edtFromDate;
    private EditText edtToDate;
    // private CheckBox cbNoiMang;
    private Button btnReport;
    // private int type = 0;
    private String statusId = "";
    private boolean book = true;

    // private RadioButton rbNoiMang;
    // private RadioButton rbNgoaiMang;
    private ReasonCusCare tmp;
    private ArrayList<ReasonCusCare> lstCurrentReasonCusCares;
//	private List<ProgramBO> lstProgramBOs;

    private ProgramBO programBO;
    private static final int FORWARD_UPDATE_CDT = 5;
    private static final int FORWARD_CHANGE_PACKAGE = 4;
    private static final int LIQUIDATION_RECOVERY = 6;
    private static final int TICK_APPOINT = 8; //man hinh gia han tam ngung

	private ProgramStatusBO programStatusBO;
    private Map<String, List<SubscriberDTO>> mapSubscriberDTO = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        idLayout = R.layout.layout_search_sub_assign;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleActionBar(R.string.cham_soc_thue_bao_roi_mang);
    }

    @Override
    public void unit(View v) {
        btnCusCare = (Button) v.findViewById(R.id.btnCusCare);
        btnCusCare.setOnClickListener(this);

        edtStaff = (EditText) v.findViewById(R.id.edtStaff);
        edtStaff.setText(Session.userName);
        edtStaff.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                if (adapter != null) {
                    lstAssignIsdnStaffBean = new ArrayList<>();
                    adapter = new ListIsdnAssignAdapter(lstAssignIsdnStaffBean,
                            getActivity(), imageViewHistoryListener,
                            checkBoxSelectListener);
                    adapter.setStatusId(statusId);
                    adapter.setCareId(careId);

                    lvItem.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

                lnTieuDe.setVisibility(View.GONE);
            }
        });

        edtIsdn = (EditText) v.findViewById(R.id.edtIsdn);
        spnStatus = (Spinner) v.findViewById(R.id.spnStatus);
        spnCare = (Spinner) v.findViewById(R.id.spnCare);
        lnCustomerType = (LinearLayout) v.findViewById(R.id.lnCustomerType);
        spnCustomerType = (Spinner) v.findViewById(R.id.spnCustomerType);

        new AsyncGetProgramBOC(getActivity(), new OnPostGetProgramBOC(), moveLogInAct).execute();
//		initSpinner(spnCare, Constant.PAR_NAME.TASK_CSKHLN);

        spnCare.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                programBO = ((ProgramBO) spnCare.getSelectedItem());
                careId = programBO.getProgramId().toString();
                // TODO Auto-generated method stub
                btnReport.setVisibility(View.GONE);

//                initSpinner(spnStatus, Constant.PAR_NAME.STATUS_CSKHLN + "_"
//                        + careId);

				Utils.setDataSpinner(getActivity(), programBO.getLstProgramStatusBO(), spnStatus);

                if (Constant.CUS_CARE.NOI_MANG_VANG_LAI.equals(careId)
                        || Constant.CUS_CARE.NGOAI_MANG_VANG_LAI.equals(careId)
                        || !"1".equals(programBO.getIsAssign())) {
                    btnCusCare.setVisibility(View.VISIBLE);
                } else {
                    btnCusCare.setVisibility(View.GONE);
                }

                if(CommonActivity.isNullOrEmptyArray(programBO.getLstProgramPropertyBOs())){
                    lnCustomerType.setVisibility(View.GONE);
                } else {
                    lnCustomerType.setVisibility(View.VISIBLE);
                    Utils.setDataSpinner(getActivity(), programBO.getLstProgramPropertyBOs(), spnCustomerType);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        lvItem = (ListView) v.findViewById(R.id.lvSub);
        tvTitle = (TextView) v.findViewById(R.id.textView2);

        // String[] arStatus =
        // act.getResources().getStringArray(R.array.arr_status_sub_assign);
        // Utils.setDataSpinnerWithListObject(act, arStatus, spnStatus);

        spnStatus
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int position, long arg3) {
						programStatusBO = (ProgramStatusBO) spnStatus.getSelectedItem();
						statusId = programStatusBO.getValue().toString();
//                        statusId = ((Spin) spnStatus.getSelectedItem()).getId();

                        // TODO Auto-generated method stub
                        if (Constant.CUS_CARE.NOI_MANG.equals(careId)) {
                            if (position == 3) {
                                btnReport.setVisibility(View.GONE);
                            } else {
                                btnReport.setVisibility(View.VISIBLE);
                            }
                        }

                        btnBook.setVisibility(View.GONE);
                        lnTieuDe.setVisibility(View.GONE);

                        if (lstAssignIsdnStaffBean != null) {
                            lstAssignIsdnStaffBean.clear();
                        }

                        if (adapter != null) {
                            adapter.setStatusId(statusId);
                            adapter.setCareId(careId);
                            adapter.notifyDataSetChanged();
                        }

                        cbSelectAll.setChecked(false);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });

        // String[] arrCare =
        // act.getResources().getStringArray(R.array.arr_care_type);
        // Utils.setDataSpinnerWithListObject(act, arrCare, spnCare);

        v.findViewById(R.id.btn_search_subscriber).setOnClickListener(this);

        btnBook = (Button) v.findViewById(R.id.btnBook);
        btnBook.setOnClickListener(this);

        btnReport = (Button) v.findViewById(R.id.btn_report);
        btnReport.setOnClickListener(this);

        cbSelectAll = (CheckBox) v.findViewById(R.id.cbSelectAll);
        cbSelectAll.setOnClickListener(this);

        // lnSelectdate = (LinearLayout) v.findViewById(R.id.lnSelectdate);
        lnTieuDe = (LinearLayout) v.findViewById(R.id.lnTieuDe);

        lvItem.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                dialogUpdate = null;
                selectedIsdn = lstAssignIsdnStaffBean.get(arg2);
                if (!validateGetSelectReason()) {
                    return;
                }
                AsyGetListReason asy = new AsyGetListReason(act, 1L);
                asy.execute();
            }
        });

        edtFromDate = (EditText) v.findViewById(R.id.edtFromDate);
        edtFromDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View arg0, boolean isFocus) {
                // TODO Auto-generated method stub
                if (!isFocus) {
                    if (!CommonActivity.isNullOrEmpty(edtFromDate.getText()
                            .toString().trim())) {
                        if (edtFromDate.getText().toString().trim().length() != 10
                                || CommonActivity.isNullOrEmpty(DateTimeUtils
                                .convertStringToTime(edtFromDate
                                                .getText().toString().trim(),
                                        "dd/MM/yyyy"))) {
                            CommonActivity
                                    .createAlertDialog(
                                            getActivity(),
                                            getString(R.string.txt_format_date_invalid),
                                            getString(R.string.app_name))
                                    .show();
                        }
                    }
                }
            }
        });

        LinearLayout lnFromDate = (LinearLayout) v.findViewById(R.id.lnFromDate);
        lnFromDate.setOnClickListener(this);

        edtToDate = (EditText) v.findViewById(R.id.edtToDate);
        edtToDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View arg0, boolean isFocus) {
                // TODO Auto-generated method stub
                if (!isFocus) {
                    if (!CommonActivity.isNullOrEmpty(edtToDate.getText()
                            .toString().trim())) {
                        if (edtToDate.getText().toString().trim().length() != 10
                                || CommonActivity.isNullOrEmpty(DateTimeUtils
                                .convertStringToTime(edtToDate
                                                .getText().toString().trim(),
                                        "dd/MM/yyyy"))) {
                            CommonActivity
                                    .createAlertDialog(
                                            getActivity(),
                                            getString(R.string.txt_format_date_invalid),
                                            getString(R.string.app_name))
                                    .show();
                        }
                    }
                }
            }
        });

        LinearLayout lnToDate = (LinearLayout) v.findViewById(R.id.lnToDate);
        lnToDate.setOnClickListener(this);

        if (CommonActivity.isNullOrEmpty(menu_vsa)) {
            SharedPreferences preferences = getActivity().getSharedPreferences(
                    Define.PRE_NAME, Activity.MODE_PRIVATE);

            menu_vsa = preferences.getString(Define.KEY_MENU_NAME, "");
        }
        //
        // rbNoiMang = (RadioButton) v.findViewById(R.id.rbNoiMang);
        // rbNoiMang.setOnClickListener(new OnClickListener() {
        //
        // @Override
        // public void onClick(View v) {
        // // TODO Auto-generated method stub
        // if (((RadioButton) v).isChecked()) {
        // String[] arStatus = act.getResources().getStringArray(
        // R.array.arr_status_sub_assign);
        // Utils.setDataSpinnerWithListObject(act, arStatus, spnStatus);
        // } else {
        // String[] arStatus = act.getResources().getStringArray(
        // R.array.arr_status_sub_assign_ngoai_mang);
        // Utils.setDataSpinnerWithListObject(act, arStatus, spnStatus);
        // }
        //
        // }
        // });

        // rbNgoaiMang = (RadioButton) v.findViewById(R.id.rbNgoaiMang);
        // rbNgoaiMang.setOnClickListener(new OnClickListener() {
        //
        // @Override
        // public void onClick(View v) {
        // // TODO Auto-generated method stub
        // if (((RadioButton) v).isChecked()) {
        // String[] arStatus = act.getResources().getStringArray(
        // R.array.arr_status_sub_assign_ngoai_mang);
        // Utils.setDataSpinnerWithListObject(act, arStatus, spnStatus);
        // } else {
        // String[] arStatus = act.getResources().getStringArray(
        // R.array.arr_status_sub_assign);
        // Utils.setDataSpinnerWithListObject(act, arStatus, spnStatus);
        // }
        //
        // }
        // });

    }

    private class OnPostGetProgramBOC implements OnPostExecuteListener<List<ProgramBO>> {

        @Override
        public void onPostExecute(List<ProgramBO> result, String errorCode,
                                  String description) {
            // TODO Auto-generated method stub
            if ("0".equals(errorCode) && !CommonActivity.isNullOrEmptyArray(result)) {
//				lstProgramBOs = result;
                Utils.setDataSpinner(getActivity(), result, spnCare);
//                initSpinner(spnStatus, Constant.PAR_NAME.STATUS_CSKHLN + "_"
//                        + ((ProgramBO) spnCare.getSelectedItem()).getProgramId());

				Utils.setDataSpinner(getActivity(), result.get(spnCare.getSelectedItemPosition()).getLstProgramStatusBO(), spnStatus);
            } else {
                if (!CommonActivity.isNullOrEmpty(description)) {
                    CommonActivity.createAlertDialog(getActivity(), description, getResources().getString(R.string.app_name))
                            .show();
                }

            }
        }
    }

    private class AsySearchListIsdn extends AsyncTask<String, Void, BOCOutput> {

        private final Activity mActivity;
        final ProgressDialog progress;
        private String errorCode = "";
        private String description = "";

        public AsySearchListIsdn(Activity mActivity) {

            this.mActivity = mActivity;

            this.progress = new ProgressDialog(mActivity);
            this.progress.setCancelable(false);
            this.progress.setMessage(mActivity.getResources().getString(
                    R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }

        }

        @Override
        protected BOCOutput doInBackground(String... params) {
            if (!statusId.equals(Constant.STATUS_HISTORY_SCHEDULE)) {
                return searchListIsdn();
                // return searchListIsdnTest();
            } else {
                return viewScheduleHistory();
            }
        }

        @Override
        protected void onPostExecute(BOCOutput result) {
            super.onPostExecute(result);
            progress.dismiss();

            lnTieuDe.setVisibility(View.GONE);
            if (!statusId.equals("1")) {
                cbSelectAll.setVisibility(View.GONE);
            } else {
                if (Constant.CUS_CARE.NOI_MANG.equals(careId)
                        || Constant.CUS_CARE.DOI_4G.equals(careId)) {
                    cbSelectAll.setVisibility(View.VISIBLE);
                } else {
                    cbSelectAll.setVisibility(View.GONE);
                }
            }

            if (statusId.equals(Constant.STATUS_HISTORY_SCHEDULE)) {
                tvTitle.setText(getActivity().getResources().getString(
                        R.string.txt_danh_sach_lich_su_dat_hen));
            } else {
                tvTitle.setText(getActivity().getResources().getString(
                        R.string.subs_is_assigned));
            }

            if ("0".equals(result.getErrorCode())) {
                if (Constant.STATUS_HISTORY_SCHEDULE.equals(statusId)) {
                    lstAssignIsdnStaffBean = result.getLstSheduleHistory();
                } else {
                    lstAssignIsdnStaffBean = result.getLstAssignIsdnStaffBean();
                }

                if (CommonActivity.isNullOrEmpty(lstAssignIsdnStaffBean)) {
                    Dialog dialog = CommonActivity.createAlertDialog(mActivity,
                            getString(R.string.no_data), mActivity
                                    .getResources()
                                    .getString(R.string.app_name));
                    dialog.show();
                    lstAssignIsdnStaffBean = new ArrayList<>();
                    lvItem.setVisibility(View.INVISIBLE);
                } else {
                    lvItem.setVisibility(View.VISIBLE);
                    lnTieuDe.setVisibility(View.VISIBLE);

                    adapter = new ListIsdnAssignAdapter(lstAssignIsdnStaffBean,
                            mActivity, imageViewHistoryListener,
                            checkBoxSelectListener);
                    adapter.setStatusId(statusId);
                    adapter.setCareId(careId);

                    adapter.setBook(book);

                    lvItem.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    if (!book && "1".equals(statusId)) {
                        cbSelectAll.setVisibility(View.GONE);
                    }
                }

            } else {
                if (Constant.INVALID_TOKEN2.equals(result.getErrorCode())) {
                    Dialog dialog = CommonActivity
                            .createAlertDialog(mActivity, result
                                    .getDescription(), mActivity.getResources()
                                    .getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    Dialog dialog = CommonActivity.createAlertDialog(
                            getActivity(), result.getDescription(),
                            getResources().getString(R.string.app_name));
                    dialog.show();

                }
            }

        }

        private BOCOutput searchListIsdn() {
            BOCOutput bocOutput = new BOCOutput();
            String original = "";
            FileInputStream in = null;
            try {
                String methodName = "getListIsdnAssign";
                BCCSGateway input = new BCCSGateway();
                // input.addValidateGateway("username", Constant.BCCSGW_USER);
                // input.addValidateGateway("password", Constant.BCCSGW_PASS);
                // input.addValidateGateway("wscode", "mbccs_" + methodName);
                StringBuilder rawData = new StringBuilder();
                rawData.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.smartphonev2.bss.viettel.com/\">");

                rawData.append("<soapenv:Header/>");
                rawData.append("<soapenv:Body>");
                rawData.append("<ws:").append(methodName).append(">");
                rawData.append("<input>");
                rawData.append("<token>").append(Session.getToken()).append("</token>");
                String isdn = CommonActivity.checkStardardIsdn(edtIsdn
                        .getText().toString().trim());
                rawData.append("<isdn>").append(isdn).append("</isdn>");
                // if (spnCare.getSelectedItemPosition() ==
                // Constant.CUS_CARE.NOI_MANG
                // || spnCare.getSelectedItemPosition() ==
                // Constant.CUS_CARE.NGOAI_MANG) {
                // rawData.append("<status>" +
                // (spnStatus.getSelectedItemPosition() + 1) + "</status>");
                // } else {
                // rawData.append("<status>" +
                // (spnStatus.getSelectedItemPosition() + 2) + "</status>");
                // }
                rawData.append("<status>").append(statusId).append("</status>");
                rawData.append("<type>").append(careId).append("</type>");
                rawData.append("<maxResult>" + 50 + "</maxResult>");

                if(!CommonActivity.isNullOrEmptyArray(programBO.getLstProgramPropertyBOs())){
                    ProgramPropertyBO programPropertyBO = (ProgramPropertyBO) spnCustomerType.getSelectedItem();
                    rawData.append("<cusType>").append(programPropertyBO.getValue()).append("</cusType>");
                }

                rawData.append("<staffCode>").append(StringUtils.escapeHtml(edtStaff.getText().toString()
                        .trim().toUpperCase())).append("</staffCode>");
                if (!CommonActivity.isNullOrEmpty(edtFromDate.getText()
                        .toString())) {
                    rawData.append("<fromDate>").append(DateTimeUtils.convertDateTimeToString(
                            DateTimeUtils.convertStringToTime(
                                    edtFromDate.getText().toString(),
                                    "dd/MM/yyyy"), "yyyyMMdd")).append("000000</fromDate>");
                }

                if (!CommonActivity.isNullOrEmpty(edtToDate.getText()
                        .toString())) {
                    rawData.append("<toDate>").append(DateTimeUtils.convertDateTimeToString(
                            DateTimeUtils
                                    .convertStringToTime(edtToDate
                                                    .getText().toString(),
                                            "dd/MM/yyyy"), "yyyyMMdd")).append("235959</toDate>");
                }
                rawData.append("</input>");
                rawData.append("</ws:").append(methodName).append(">");
                rawData.append("</soapenv:Body>");
                rawData.append("</soapenv:Envelope>");
                Log.i("RowData", rawData.toString());

                String envelope = rawData.toString();
                Log.e("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);

                String fileName = input.sendRequestWriteToFile(envelope,
                        Constant.WS_ASIGN_DATA, Constant.ASIGN_FILE_NAME);
                if (fileName != null) {
                    File file = new File(fileName);
                    try {
                        if (!file.mkdirs()) {
                            file.createNewFile();
                        }
                        in = new FileInputStream(file);
                        AssignIsdnHandler handler = (AssignIsdnHandler) CommonActivity
                                .parseXMLHandler(new AssignIsdnHandler(),
                                        new InputSource(in));

                        if (handler != null) {
                            if (handler.getItem().getToken() != null
                                    && !handler.getItem().getToken().isEmpty()) {
                                Session.setToken(handler.getItem().getToken());
                            }

                            if (handler.getItem().getErrorCode() != null) {
                                errorCode = handler.getItem().getErrorCode();
                            }
                            if (handler.getItem().getDescription() != null) {
                                description = handler.getItem()
                                        .getDescription();
                            }
                            bocOutput.setErrorCode(errorCode);
                            bocOutput.setDescription(description);
                            bocOutput.setLstAssignIsdnStaffBean(handler
                                    .getLstData());
                        } else {
                            bocOutput.setErrorCode(Constant.ERROR_CODE);
                            bocOutput.setDescription(getActivity().getString(
                                    R.string.no_return_from_system));
                        }

                    } catch (Exception e) {
                        Log.e("Exception", e.toString(), e);
                    } finally {
                        if (in != null) {
                            try {
                                in.close();
                            } catch (IOException e) {
                                Log.e("Exception", e.toString(), e);
                            }
                        }

                        try {
                            file.delete();
                        } catch (Exception ignored) {

                        }
                    }
                }

                // String envelope = input.buildInputGatewayWithRawData(rawData
                // .toString());
                // Log.d("Send evelop", envelope);
                // Log.i("LOG", Constant.BCCS_GW_URL);
                // String response = input.sendRequest(envelope,
                // Constant.BCCS_GW_URL, getActivity(), "mbccs_"
                // + methodName);
                // Log.i("Responseeeeeeeeee", response);
                // CommonOutput output = input.parseGWResponse(response);
                // original = output.getOriginal();
                // Log.i("Responseeeeeeeeee Original", response);
                //
                // // parser
                // Serializer serializer = new Persister();
                // bocOutput = serializer.read(BOCOutput.class, original);
                // if (bocOutput == null) {
                // bocOutput = new BOCOutput();
                // bocOutput
                // .setDescription(getString(R.string.no_return_from_system));
                // bocOutput.setErrorCode(Constant.ERROR_CODE);
                // return bocOutput;
                // } else {
                // return bocOutput;
                // }
            } catch (Exception e) {
                Log.e(Constant.TAG, "searchListIsdn", e);
                bocOutput = new BOCOutput();
                bocOutput.setDescription(e.getMessage());
                bocOutput.setErrorCode(Constant.ERROR_CODE);
            }
            return bocOutput;
        }

        private BOCOutput viewScheduleHistory() {
            BOCOutput bocOutput = new BOCOutput();
            String original = "";
            try {
                String methodName = "viewScheduleHistory";
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_" + methodName);
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:").append(methodName).append(">");
                rawData.append("<input>");
                rawData.append("<token>").append(Session.getToken()).append("</token>");
                if (!CommonActivity
                        .isNullOrEmpty(edtStaff.getText().toString())) {
                    rawData.append("<staffCode>").append(edtStaff.getText().toString()).append("</staffCode>");
                }

                if (!CommonActivity.isNullOrEmpty(edtIsdn.getText().toString())) {
                    rawData.append("<isdn>").append(edtIsdn.getText().toString()).append("</isdn>");
                }

                rawData.append("<type>").append(careId).append("</type>");

                if (!CommonActivity.isNullOrEmpty(edtFromDate.getText()
                        .toString())) {
                    rawData.append("<fromDate>").append(DateTimeUtils.convertDateTimeToString(
                            DateTimeUtils.convertStringToTime(
                                    edtFromDate.getText().toString(),
                                    "dd/MM/yyyy"), "yyyyMMddHHmmss")).append("</fromDate>");
                }

                if (!CommonActivity.isNullOrEmpty(edtToDate.getText()
                        .toString())) {
                    rawData.append("<toDate>").append(DateTimeUtils.convertDateTimeToString(
                            DateTimeUtils
                                    .convertStringToTime(edtToDate
                                                    .getText().toString(),
                                            "dd/MM/yyyy"),
                            "yyyyMMddHHmmss")).append("</toDate>");
                }
                rawData.append("</input>");
                rawData.append("</ws:").append(methodName).append(">");
                Log.i("RowData", rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope,
                        Constant.BCCS_GW_URL, getActivity(), "mbccs_"
                                + methodName);
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("Responseeeeeeeeee Original", response);

                // parser
                Serializer serializer = new Persister();
                bocOutput = serializer.read(BOCOutput.class, original);
                if (bocOutput == null) {
                    bocOutput = new BOCOutput();
                    bocOutput
                            .setDescription(getString(R.string.no_return_from_system));
                    bocOutput.setErrorCode(Constant.ERROR_CODE);
                    return bocOutput;
                } else {
                    return bocOutput;
                }
            } catch (Exception e) {
                Log.e(Constant.TAG, "searchListIsdn", e);
                bocOutput = new BOCOutput();
                bocOutput.setDescription(e.getMessage());
                bocOutput.setErrorCode(Constant.ERROR_CODE);
            }
            return bocOutput;
        }

    }

    private class AsyGetListReason extends AsyncTask<String, Void, BOCOutput> {

        private final Activity mActivity;
        final ProgressDialog progress;
        private final Long level;

        public AsyGetListReason(Activity mActivity, Long level) {

            this.mActivity = mActivity;
            this.level = level;
            this.progress = new ProgressDialog(mActivity);
            this.progress.setCancelable(false);
            this.progress.setMessage(mActivity.getResources().getString(
                    R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }

        }

        @Override
        protected BOCOutput doInBackground(String... params) {

            // if (rbNoiMang.isChecked()) {
            return getListReasoncare();
            // } else {
            // return getListReasoncareNgoaiMang();
            // }
        }

        @Override
        protected void onPostExecute(BOCOutput result) {
            super.onPostExecute(result);
            progress.dismiss();
            if (result.getErrorCode().equals("0")) {
                if (Constant.CUS_CARE.NOI_MANG.equals(careId)
                        || Constant.CUS_CARE.NGOAI_MANG.equals(careId)) {
                    if (CommonActivity.isNullOrEmpty(result
                            .getReasonCusCareResult())
                            || CommonActivity.isNullOrEmptyArray(result
                            .getReasonCusCareResult()
                            .getLstReasonCusCare())) {
                        Dialog dialog = CommonActivity.createAlertDialog(
                                getActivity(), getString(R.string.no_data),
                                getString(R.string.app_name));
                        dialog.show();
                        return;
                    }
                }

                if ((Constant.CUS_CARE.NOI_MANG_VANG_LAI.equals(careId) || Constant.CUS_CARE.NGOAI_MANG_VANG_LAI
                        .equals(careId) || Integer.valueOf(careId).intValue() >= 8) && level.equals(1L)) { // lay thong tin
                    // thuoc tinh
                    // can
                    // nhap vang lai
                    new AsyGetObjectType(mActivity).execute();
                }

                if (dialogUpdate == null) {
                    lastReasonLevel.clear();
                    lstReasonIdSelect.clear();
                    showDialogUpdate(result.getReasonCusCareResult()
                            .getLstReasonCusCare());
                } else {
                    nextReason(level);
                }
            } else {
                if (!CommonActivity.isNullOrEmptyArray(lastReasonLevel)) {
                    lastReasonLevel.remove(lastReasonLevel.size() - 1);
                }
                if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
                    Dialog dialog = CommonActivity
                            .createAlertDialog(mActivity, result
                                    .getDescription(), mActivity.getResources()
                                    .getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    Dialog dialog = CommonActivity.createAlertDialog(
                            getActivity(), result.getDescription(),
                            getResources().getString(R.string.app_name));
                    dialog.show();

                }
            }

        }

        private BOCOutput getListReasoncare() {

            BOCOutput bocOutput = new BOCOutput();

            if (!CommonActivity.isNullOrEmpty(mapReason
                    .get(getKeyReason(level)))) {
                bocOutput.setErrorCode("0");
                bocOutput.setReasonCusCareResult(mapReason
                        .get(getKeyReason(level)));
                return bocOutput;
            }

            String original = "";
            try {
                String methodName = "getListReasonBOC";
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_" + methodName);
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:").append(methodName).append(">");
                rawData.append("<input>");
                rawData.append("<token>").append(Session.getToken()).append("</token>");
                rawData.append("<type>").append(careId).append("</type>");

                rawData.append("<reasonLevel>").append(level).append("</reasonLevel>");
                rawData.append("</input>");
                rawData.append("</ws:").append(methodName).append(">");
                Log.i("RowData", rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope,
                        Constant.BCCS_GW_URL, getActivity(), "mbccs_"
                                + methodName);
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("Responseeeeeeeeee Original", response);

                // parser
                Serializer serializer = new Persister();
                bocOutput = serializer.read(BOCOutput.class, original);
                if (bocOutput == null) {
                    bocOutput = new BOCOutput();
                    bocOutput
                            .setDescription(getString(R.string.no_return_from_system));
                    bocOutput.setErrorCode(Constant.ERROR_CODE);
                    return bocOutput;
                } else {
                    if (!CommonActivity.isNullOrEmpty(bocOutput
                            .getReasonCusCareResult())) {
                        mapReason.put(getKeyReason(level),
                                bocOutput.getReasonCusCareResult());
                    }
                    return bocOutput;
                }
            } catch (Exception e) {
                Log.e(Constant.TAG, "getListReasoncare", e);
                bocOutput = new BOCOutput();
                bocOutput.setDescription(e.getMessage());
                bocOutput.setErrorCode(Constant.ERROR_CODE);
            }

            return bocOutput;

        }
    }

    private class AsyGetObjectType extends AsyncTask<String, Void, BOCOutput> {

        private final Activity mActivity;
        final ProgressDialog progress;

        public AsyGetObjectType(Activity mActivity) {

            this.mActivity = mActivity;
            this.progress = new ProgressDialog(mActivity);
            this.progress.setCancelable(false);
            this.progress.setMessage(mActivity.getResources().getString(
                    R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected BOCOutput doInBackground(String... params) {
            return getObjectType();
        }

        @Override
        protected void onPostExecute(BOCOutput result) {
            super.onPostExecute(result);
            progress.dismiss();
            if (result.getErrorCode().equals("0")) {
                if (!CommonActivity.isNullOrEmpty(result
                        .getLstObjectPropertys())) {
                    if (selectedIsdn != null && !CommonActivity.isNullOrEmpty(selectedIsdn.getIsdn())) {
                        for (ObjectProperty objectProperty : result.getLstObjectPropertys()) {
                            if ("ISDN".equals(objectProperty.getPropertyCode())) {
                                objectProperty.setValue(selectedIsdn.getIsdn());
                                objectProperty.setEnable(false);
                                break;
                            }
                        }
                    }

                    obAdapter = new ObjectTypeAdapter(mActivity,
                            result.getLstObjectPropertys());
                    lvInfoAdd.setAdapter(obAdapter);
                }
            } else {
                if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
                    Dialog dialog = CommonActivity
                            .createAlertDialog(mActivity, result
                                    .getDescription(), mActivity.getResources()
                                    .getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    Dialog dialog = CommonActivity.createAlertDialog(
                            getActivity(), result.getDescription(),
                            getResources().getString(R.string.app_name));
                    dialog.show();

                }
            }

        }

        private BOCOutput getObjectType() {
            BOCOutput bocOutput = new BOCOutput();
            if (!CommonActivity.isNullOrEmptyArray(mapObjectProperty
                    .get(careId))) {
                bocOutput.setErrorCode("0");
                bocOutput.setLstObjectPropertys(mapObjectProperty.get(careId));
                return bocOutput;
            }

            String original = "";
            try {
                String methodName = "getObjectType";
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_" + methodName);
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:").append(methodName).append(">");
                rawData.append("<input>");
                rawData.append("<token>").append(Session.getToken()).append("</token>");
                rawData.append("<type>").append(careId).append("</type>");
                rawData.append("</input>");
                rawData.append("</ws:").append(methodName).append(">");
                Log.i("RowData", rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope,
                        Constant.BCCS_GW_URL, getActivity(), "mbccs_"
                                + methodName);
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("Responseeeeeeeeee Original", response);

                // parser
                Serializer serializer = new Persister();
                bocOutput = serializer.read(BOCOutput.class, original);
                if (bocOutput == null) {
                    bocOutput = new BOCOutput();
                    bocOutput
                            .setDescription(getString(R.string.no_return_from_system));
                    bocOutput.setErrorCode(Constant.ERROR_CODE);
                    return bocOutput;
                } else {
                    if (!CommonActivity.isNullOrEmpty(bocOutput
                            .getLstObjectPropertys())) {
                        mapObjectProperty.put(Integer.valueOf(careId),
                                bocOutput.getLstObjectPropertys());
                    }
                    return bocOutput;
                }
            } catch (Exception e) {
                Log.e(Constant.TAG, "getObjectType", e);
                bocOutput = new BOCOutput();
                bocOutput.setDescription(e.getMessage());
                bocOutput.setErrorCode(Constant.ERROR_CODE);
            }

            return bocOutput;

        }
    }

    /**
     * @author huypq15
     */
    private class AsyUpdateStatusContact extends
            AsyncTask<String, Void, BOCOutput> {

        private final Activity mActivity;
        final ProgressDialog progress;

        public AsyUpdateStatusContact(Activity mActivity) {

            this.mActivity = mActivity;

            this.progress = new ProgressDialog(mActivity);
            this.progress.setCancelable(false);
            this.progress.setMessage(mActivity.getResources().getString(
                    R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }

        }

        @Override
        protected BOCOutput doInBackground(String... params) {
            return updateStatusContact();
        }

        @Override
        protected void onPostExecute(BOCOutput result) {
            super.onPostExecute(result);
            progress.dismiss();
            if (result.getErrorCode().equals("0")) {
                dialogUpdate.dismiss();

                if (!CommonActivity.isNullOrEmpty(lstAssignIsdnStaffBean)) {
                    lstAssignIsdnStaffBean.remove(selectedIsdn);
                }

                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }

                if (Constant.CUS_CARE.NOI_MANG.equals(careId)
                        || Constant.CUS_CARE.NGOAI_MANG.equals(careId)) {
                    CommonActivity.createAlertDialog(
                            mActivity,
                            getString(R.string.update_care_sub_success,
                                    selectedIsdn.getIsdn()),
                            getString(R.string.app_name)).show();


                } else {
                    CommonActivity
                            .createAlertDialog(
                                    mActivity,
                                    getString(R.string.update_care_sub_success_vang_lai),
                                    getString(R.string.app_name)).show();
                }

            } else {
                if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
                    Dialog dialog = CommonActivity
                            .createAlertDialog(mActivity, result
                                    .getDescription(), mActivity.getResources()
                                    .getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    Dialog dialog = CommonActivity.createAlertDialog(
                            getActivity(), result.getDescription(),
                            getResources().getString(R.string.app_name));
                    dialog.show();
                }
            }

        }

        private BOCOutput updateStatusContact() {
            BOCOutput bocOutput = new BOCOutput();
            String original = "";
            try {
                String methodName = "updateCusCareResult";
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_" + methodName);
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:").append(methodName).append(">");
                rawData.append("<input>");
                rawData.append("<token>").append(Session.getToken()).append("</token>");

                if (Constant.CUS_CARE.NOI_MANG.equals(careId)
                        || Constant.CUS_CARE.NGOAI_MANG.equals(careId)
                        || Constant.CUS_CARE.DOI_4G.equals(careId)) {
                    rawData.append("<isdn>").append(selectedIsdn.getIsdn()).append("</isdn>");
                }

                rawData.append("<type>").append(careId).append("</type>");
                rawData.append("<status>").append(statusId).append("</status>");

                for (Map.Entry<Long, ReasonCusCareResult> entrySet : mapReasonSelected
                        .entrySet()) {
                    ReasonCusCareResult bo = entrySet.getValue();
                    for (ReasonCusCare reasonCusCare : bo.getLstReasonCusCare()) {
                        rawData.append("<lstReasonCusCares>");

                        if (!CommonActivity.isNullOrEmpty(reasonCusCare
                                .getCode())) {
                            rawData.append("<code>").append(reasonCusCare.getCode()).append("</code>");
                        }

                        if (!CommonActivity.isNullOrEmpty(reasonCusCare
                                .getLevelReason())) {
                            rawData.append("<levelReason>").append(reasonCusCare.getLevelReason()).append("</levelReason>");
                        }

                        if (!CommonActivity.isNullOrEmpty(reasonCusCare
                                .getNextLevel())) {
                            rawData.append("<nextLevel>").append(reasonCusCare.getNextLevel()).append("</nextLevel>");
                        }

                        if (!CommonActivity.isNullOrEmpty(reasonCusCare
                                .getReasonId())) {
                            rawData.append("<reasonId>").append(reasonCusCare.getReasonId()).append("</reasonId>");
                        }

                        if (!CommonActivity.isNullOrEmpty(reasonCusCare
                                .getIsUpdate())) {
                            rawData.append("<isUpdate>").append(reasonCusCare.getIsUpdate()).append("</isUpdate>");
                        }

                        if (!CommonActivity.isNullOrEmpty(reasonCusCare
                                .getPriority())) {
                            rawData.append("<priority>").append(reasonCusCare.getPriority()).append("</priority>");
                        }

                        if (!CommonActivity.isNullOrEmpty(reasonCusCare
                                .getReasonValue())) {
                            rawData.append("<reasonValue>").append(reasonCusCare.getReasonValue()).append("</reasonValue>");
                        }

                        if (!CommonActivity.isNullOrEmptyArray(reasonCusCare
                                .getLstReasonCusCareProperty())) {
                            for (ReasonCusCareProperty reProperty : reasonCusCare
                                    .getLstReasonCusCareProperty()) {
                                rawData.append("<lstReasonCusCareProperty>");

                                rawData.append("<checkIsdnViettel>").append(reProperty.getCheckIsdnViettel()).append("</checkIsdnViettel>");
                                rawData.append("<code>").append(reProperty.getCode()).append("</code>");
                                rawData.append("<name>").append(StringUtils.escapeHtml(reProperty
                                        .getName())).append("</name>");
                                rawData.append("<value>").append(reProperty.getValue()).append("</value>");
                                rawData.append("<dataType>").append(reProperty.getDataType()).append("</dataType>");
                                rawData.append("<optionType>").append(reProperty.getOptionType()).append("</optionType>");

                                rawData.append("</lstReasonCusCareProperty>");
                            }
                        }

                        rawData.append("</lstReasonCusCares>");
                    }
                }

                if (Constant.CUS_CARE.NOI_MANG_VANG_LAI.equals(careId)
                        || Constant.CUS_CARE.NGOAI_MANG_VANG_LAI.equals(careId)
                        || programBO.getProgramId() >= 8) {
                    if (obAdapter != null) {
                        ArrayList<ObjectProperty> lstObjectProperties = obAdapter
                                .getLstData();
                        if (!CommonActivity.isNullOrEmptyArray(lstObjectProperties)) {
                            for (ObjectProperty objectProperty : lstObjectProperties) {
                                if (!CommonActivity.isNullOrEmpty(objectProperty
                                        .getValue())) {
                                    rawData.append("<lstObjectProperty>");

                                    rawData.append("<name>"
                                            + objectProperty.getName() + "</name>");
                                    rawData.append("<value>"
                                            + objectProperty.getValue().trim()
                                            + "</value>");
                                    rawData.append("<propertyCode>"
                                            + objectProperty.getPropertyCode()
                                            + "</propertyCode>");

                                    rawData.append("</lstObjectProperty>");
                                }
                            }
                        }
                    }
                }

                rawData.append("<note>").append(StringUtils.escapeHtml(edtNote.getText().toString()
                        .trim())).append("</note>");

                if (Constant.CUS_CARE.NOI_MANG.equals(careId)
                        || Constant.CUS_CARE.NGOAI_MANG.equals(careId)) {
                    rawData.append("<staffCode>").append(StringUtils.escapeHtml(edtStaff.getText()
                            .toString().toUpperCase())).append("</staffCode>");
                }

                rawData.append("</input>");
                rawData.append("</ws:").append(methodName).append(">");
                Log.i("RowData", rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());

                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope,
                        Constant.BCCS_GW_URL, getActivity(), "mbccs_"
                                + methodName);
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("Responseeeeeeeeee Original", original);

                // parser
                Serializer serializer = new Persister();
                bocOutput = serializer.read(BOCOutput.class, original);
                if (bocOutput == null) {
                    bocOutput = new BOCOutput();
                    bocOutput
                            .setDescription(getString(R.string.no_return_from_system));
                    bocOutput.setErrorCode(Constant.ERROR_CODE);
                    return bocOutput;
                } else {
                    return bocOutput;
                }
            } catch (Exception e) {
                Log.e(Constant.TAG, "updateStatusContact", e);
                bocOutput = new BOCOutput();
                bocOutput.setDescription(e.getMessage());
                bocOutput.setErrorCode(Constant.ERROR_CODE);
            }

            return bocOutput;
        }
    }

    private LinearLayout lnReason;
    private ExpandableHeightListView lvInfoAdd;
    private ObjectTypeAdapter obAdapter;
    LinearLayout lnNote;

    private void showDialogUpdate(ArrayList<ReasonCusCare> lstReasonCare) {
        mapReasonSelected.clear();
        dialogUpdate = new Dialog(getActivity(),R.style.AppCompatAlertDialogStyle);
        dialogUpdate.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialogUpdate.setContentView(R.layout.dialog_update_care_lost_sub);
        dialogUpdate.setCancelable(false);

        TextView tvTitle = (TextView) dialogUpdate.findViewById(R.id.tvTitle);
        if (Constant.CUS_CARE.NOI_MANG.equals(careId)
                || Constant.CUS_CARE.DOI_4G.equals(careId)
                || (Integer.valueOf(careId) >= 8 && "1".equals(programBO.getIsAssign()))) {
            tvTitle.setText(getString(R.string.care_sub, selectedIsdn.getIsdn()));
        } else if (Constant.CUS_CARE.NOI_MANG_VANG_LAI.equals(careId)) {
            tvTitle.setText(getString(R.string.txt_tiepxuc_tb_noi_mang_vang_lai));
        } else if (Constant.CUS_CARE.NGOAI_MANG.equals(careId)) {
            tvTitle.setText(getString(R.string.txt_stb_ngoai_mang,
                    selectedIsdn.getIsdn()));
        } else if (Constant.CUS_CARE.NGOAI_MANG_VANG_LAI.equals(careId)) {
            tvTitle.setText(getString(R.string.txt_tiepxuc_tb_ngoai_mang_vang_lai));
        } else {
            tvTitle.setText(getString(R.string.update_care_sub));
        }

        Button btnUpdate = (Button) dialogUpdate.findViewById(R.id.btnOK);
        Button btnCancel = (Button) dialogUpdate.findViewById(R.id.btnCancel);
        ImageView imgInfo = (ImageView) dialogUpdate.findViewById(R.id.imgInfo);
        edtNote = (EditText) dialogUpdate.findViewById(R.id.edtNote);
        lnNote = (LinearLayout) dialogUpdate.findViewById(R.id.lnNote);

        if (programBO != null && programBO.getProgramId() >= 8) {
            lnNote.setVisibility(View.GONE);
        } else {
            lnNote.setVisibility(View.VISIBLE);
        }

        btnUpdate.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        lvReason = (ExpandableListView) dialogUpdate
                .findViewById(R.id.lvReason);
        lvReason.setGroupIndicator(null);

        tvReasonGroup = (TextView) dialogUpdate
                .findViewById(R.id.tvReasonGroup);
        tvLastReason = (TextView) dialogUpdate.findViewById(R.id.tvLastReason);
        // lnTBNoiMang = (LinearLayout) dialogUpdate
        // .findViewById(R.id.lnTBNoiMang);
        // edtTBNoiMang = (EditText)
        // dialogUpdate.findViewById(R.id.edtTBNoiMang);

        lnReason = (LinearLayout) dialogUpdate.findViewById(R.id.lnReason);
        lvInfoAdd = (ExpandableHeightListView) dialogUpdate
                .findViewById(R.id.lvInfoAdd1);
        lvInfoAdd.setExpanded(true);

        // if (rbNoiMang.isChecked()) {
        // lnTBNoiMang.setVisibility(View.GONE);
        // } else {
        // lnTBNoiMang.setVisibility(View.VISIBLE);
        // }

        // if (spnCare.getSelectedItemPosition() == 2) {
        // lnTBNoiMang.setVisibility(View.VISIBLE);
        // } else {
        // lnTBNoiMang.setVisibility(View.GONE);
        // }

        btnNextReasonLevel = (Button) dialogUpdate
                .findViewById(R.id.btnNextReasonLevel);
        btnNextReasonLevel.setOnClickListener(this);
        // if (!rbNoiMang.isChecked()) {
        // btnNextReasonLevel.setVisibility(View.GONE);
        // }
        // btnRefreshReason = (Button)
        // dialogUpdate.findViewById(R.id.btnRefreshReason);
        // btnRefreshReason.setOnClickListener(this);
        // if (rbNoiMang.isChecked()) {
        ReasonCusCareResult root = mapReason.get(getKeyReason(1L));
        for (Map.Entry<String, ReasonCusCareResult> entrySet : mapReason
                .entrySet()) {

            ReasonCusCareResult bo = entrySet.getValue();
            // Reason bo = entrySet.getValue();
            // for (ReasonDetailBO item : bo.getLstReasonDetailBO()) {
            // item.setChecked(false);
            // }
            for (ReasonCusCare item : bo.getLstReasonCusCare()) {
                item.setChecked(false);
                if (!CommonActivity.isNullOrEmptyArray(item
                        .getLstReasonCusCareProperty())) {
                    for (ReasonCusCareProperty reProperty : item
                            .getLstReasonCusCareProperty()) {
                        reProperty.setValue("");
                    }
                }
            }
        }

        if (root.isMultiChoice()
                && root.getLstReasonCusCare().get(0).isNextLevel()) {
            btnNextReasonLevel.setVisibility(View.VISIBLE);
        } else {
            btnNextReasonLevel.setVisibility(View.INVISIBLE);
        }

        // reasonAdapter = new ReasonCareSubAdapter(
        // root.getLstReasonCusCare(), root.isMultiChoice(), act,
        // null, null);
        lstCurrentReasonCusCares = root.getLstReasonCusCare();
        reasonAdapter = new ReasonCareSubExpandAdapter(getActivity(),
                root.getLstReasonCusCare(), root.isMultiChoice());
        isMultiChoice = root.isMultiChoice();

        lvReason.setAdapter(reasonAdapter);
        dialogUpdate.findViewById(R.id.lnBack).setVisibility(View.INVISIBLE);
        tvReasonGroup.setText(root.getLevelName());
        // } else {
        // for (ReasonDetailBO reasonDetailBO : lstReasonCare) {
        // reasonDetailBO.setLevelReason(Long.MAX_VALUE);
        // }
        //
        // reasonAdapter = new ReasonCareSubAdapter(lstReasonCare, false, act,
        // null, null);
        // lvReason.setAdapter(reasonAdapter);
        // dialogUpdate.findViewById(R.id.lnBack)
        // .setVisibility(View.INVISIBLE);
        // }
        lvReason.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView arg0, View arg1,
                                        int arg2, long arg3) {
                tmp = (ReasonCusCare) reasonAdapter.getGroup(arg2);
                if (!CommonActivity.isNullOrEmptyArray(tmp
                        .getLstReasonCusCareProperty())) {
                    if (FORWARD_CHANGE_PACKAGE != tmp.getLstReasonCusCareProperty().get(0).getDataType()
                            && FORWARD_UPDATE_CDT != tmp.getLstReasonCusCareProperty().get(0).getDataType() && LIQUIDATION_RECOVERY != tmp.getLstReasonCusCareProperty().get(0).getDataType() && TICK_APPOINT != tmp.getLstReasonCusCareProperty().get(0).getDataType() ) {
                        if (!tmp.isChecked()) {
                            showDialogProperty(tmp.getLstReasonCusCareProperty());
                            // new AsyShowDialogProperty(getActivity(),
                            // tmp.getLstReasonCusCareProperty()).execute();
                            return true;
                        }
                    }

                }

                selectReason(tmp);
                return true;
            }
        });

        imgInfo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                List<SubscriberDTO> lstSubscriberDTOs = mapSubscriberDTO.get(selectedIsdn.getIsdn());
                if(CommonActivity.isNullOrEmpty(lstSubscriberDTOs)) {
                    AsyncTaskSearchSubscriber task = new AsyncTaskSearchSubscriber(
                            getActivity(), new OnPostSearchSubscriber(), moveLogInAct);
                    task.execute(selectedIsdn.getIsdn(), "");
                } else {
                    showDialogSubInfo(lstSubscriberDTOs);
                }
            }
        });

        // lvReason.setOnItemClickListener(new OnItemClickListener() {
        // @Override
        // public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
        // long arg3) {
        // tmp = (ReasonCusCare) reasonAdapter.getGroup(arg2);
        // if(!CommonActivity.isNullOrEmptyArray(tmp.getLstReasonCusCareProperty())){
        // if(!tmp.isChecked()){
        // showDialogProperty(tmp.getLstReasonCusCareProperty());
        // return;
        // }
        // }
        //
        // selectReason(tmp);
        // }
        // });

        dialogUpdate.findViewById(R.id.lnBack).setOnClickListener(
                FragmentCareLostSub.this);
        dialogUpdate.show();
    }

    private void showDialogSubInfo(List<SubscriberDTO> lsSubscriberDTOs){
        SubInfoDetailDialogFragment fragment = SubInfoDetailDialogFragment.newInstance(lsSubscriberDTOs);
        fragment.show(getFragmentManager(), "subInfo");
    }

    private class OnPostSearchSubscriber implements
            OnPostExecuteListener<List<SubscriberDTO>> {

        @Override
        public void onPostExecute(List<SubscriberDTO> result, String errorCode,
                                  String description) {
            // TODO Auto-generated method stub
            if("0".equals(errorCode)){
                if (CommonActivity.isNullOrEmptyArray(result)) {
                    CommonActivity.toast(getActivity(), getResources().getString(R.string.checkinfosub));
                } else {
                    mapSubscriberDTO.put(selectedIsdn.getIsdn(), result);
                    showDialogSubInfo(result);
                }
            }else{
                String des = getResources().getString(R.string.checkinfosub);
                if(!CommonActivity.isNullOrEmpty(description)){
                    des = description;
                }
                CommonActivity.toast(getActivity(), des);
            }
        }
    }

    private String dateBook = null;
    private String menu_vsa = "";

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Long currLevel = 0L;
        switch (v.getId()) {
            case R.id.btnCusCare:
                dialogUpdate = null;
                selectedIsdn = null;
                // reset lai du lieu
                ArrayList<ObjectProperty> lstObjectProperties = mapObjectProperty
                        .get(careId);
                if (!CommonActivity.isNullOrEmptyArray(lstObjectProperties)) {
                    for (ObjectProperty obProperty : lstObjectProperties) {
                        obProperty.setValue("");
                    }
                }

                new AsyGetListReason(act, 1L).execute();
                break;
            case R.id.lnFromDate:
                showDialogFromDate();
                break;
            case R.id.lnToDate:
                showDialogToDate();
                break;
            case R.id.btnBook:

                SimpleDateTimePicker.make(getString(R.string.txt_dat_lich_hen),
                        new Date(), new DateTimePicker.OnDateTimeSetListener() {

                            @Override
                            public void DateTimeSet(Date date) {
                                // TODO Auto-generated method stub
                                DateTime mDateTime = new DateTime(date);
                                dateBook = mDateTime
                                        .getDateString("yyyyMMddHHmmss");

                                String msg = getString(
                                        R.string.txt_dat_lich_hen_confirm,
                                        mDateTime.getDateString("HH:mm:ss"),
                                        mDateTime.getDateString("dd/MM/yyyy"));
                                if (!CommonActivity.isNullOrEmpty(edtStaff
                                        .getText().toString())) {
                                    if (menu_vsa
                                            .contains(Constant.VSAMenu.VSA_BOOK_SMS)) {
                                        if (!edtStaff.getText().toString().trim()
                                                .equalsIgnoreCase(Session.userName)) {
                                            msg = getString(
                                                    R.string.txt_dat_lich_hen_thay_confirm,
                                                    edtStaff.getText().toString(),
                                                    mDateTime
                                                            .getDateString("HH:mm:ss"),
                                                    mDateTime
                                                            .getDateString("dd/MM/yyyy"));
                                        }
                                    }
                                }

                                CommonActivity.createDialog(act, msg,
                                        getString(R.string.app_name),
                                        getString(R.string.ok),
                                        getString(R.string.cancel), bookListener,
                                        null).show();
                            }
                        }, getFragmentManager()).show();
                break;
            case R.id.cbSelectAll:
                if (cbSelectAll.isChecked()) {
                    for (AssignIsdnStaffBean assignIsdnStaffBean : lstAssignIsdnStaffBean) {
                        if (assignIsdnStaffBean.getIsSim4G() == null
                                || !assignIsdnStaffBean.getIsSim4G().equals(1)) {
                            assignIsdnStaffBean.setSelect(true);
                        }
                    }
                    btnBook.setVisibility(View.VISIBLE);
                } else {
                    for (AssignIsdnStaffBean assignIsdnStaffBean : lstAssignIsdnStaffBean) {
                        assignIsdnStaffBean.setSelect(false);
                    }
                    btnBook.setVisibility(View.GONE);
                }

                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.btn_search_subscriber:
                if (!Constant.STATUS_HISTORY_SCHEDULE.equals(statusId)) {
                    if (CommonActivity.isNullOrEmpty(edtStaff) && CommonActivity.isNullOrEmpty(edtIsdn)) {
                        CommonActivity.createAlertDialog(act,
                                getString(R.string.staff_code_or_isdn_cannot_empty),
                                getString(R.string.app_name)).show();
                        edtStaff.requestFocus();
                        return;
                    }
                } else {
                    if (!validateViewScheduleHistory()) {
                        return;
                    }
                }

                cbSelectAll.setChecked(false);
                if (!CommonActivity.isNullOrEmpty(edtStaff)) {
                    book = edtStaff.getText().toString().trim().equalsIgnoreCase(Session.userName) || menu_vsa.contains(Constant.VSAMenu.VSA_BOOK_SMS);
                } else {
                    book = true;
                }

                if (!validateDate()) {
                    return;
                }

                AsySearchListIsdn asy = new AsySearchListIsdn(act);
                asy.execute();
                break;
            case R.id.btn_report:
                if (CommonActivity.isNullOrEmpty(edtStaff)) {
                    CommonActivity.createAlertDialog(act,
                            getString(R.string.staff_code_cannot_empty),
                            getString(R.string.app_name)).show();
                    edtStaff.requestFocus();
                    return;
                }

                AsyReportCusCare asyReportCusCare = new AsyReportCusCare(act);
                asyReportCusCare.execute();
                break;
            case R.id.btnOK:
                Boolean isCheck = false;
                currLevel = reasonAdapter.getLstData().get(0).getLevelReason();
                if (CommonActivity.isNullOrEmpty(reasonAdapter)
                        && CommonActivity.isNullOrEmptyArray(reasonAdapter
                        .getLstData())) {
                    CommonActivity.createAlertDialog(act,
                            R.string.must_select_reason_to_care_sub,
                            R.string.app_name).show();
                    return;
                }

                if (mapReasonSelected != null
                        && mapReasonSelected.get(currLevel) != null
                        && !CommonActivity.isNullOrEmpty(mapReasonSelected.get(
                        currLevel).getLstReasonCusCare())
                        && !mapReasonSelected.get(currLevel).getLstReasonCusCare()
                        .get(0).isNextLevel()) {
                    isCheck = true;
                }

                if (!isCheck) {
                    CommonActivity.createAlertDialog(act,
                            R.string.must_select_reason_to_care_sub,
                            R.string.app_name).show();
                    return;
                }

                if (isMultiChoice) {
                    // check cac thuoc tinh bat buoc nhap
                    if (!CommonActivity
                            .isNullOrEmptyArray(lstCurrentReasonCusCares)) {
                        for (ReasonCusCare reasonCusCare : lstCurrentReasonCusCares) {
                            if (reasonCusCare.getOptionType() != null
                                    && reasonCusCare.getOptionType().equals(1)) {
                                boolean existed = false;
                                for (ReasonCusCare reCare : mapReasonSelected.get(
                                        currLevel).getLstReasonCusCare()) {
                                    if (reCare.getCode().equals(
                                            reasonCusCare.getCode())) {
                                        existed = true;
                                        break;
                                    }
                                }

                                if (!existed) {
                                    CommonActivity
                                            .createAlertDialog(
                                                    act,
                                                    R.string.must_select_reason_to_care_sub,
                                                    R.string.app_name).show();
                                    return;
                                }
                            }
                        }
                    }
                }

                if (StringUtils.CheckCharSpecical(edtNote.getText().toString()
                        .trim())) {
                    CommonActivity.createAlertDialog(act,
                            R.string.note_not_include_special_chars,
                            R.string.app_name).show();
                    edtNote.requestFocus();
                    return;
                }

                if (Constant.CUS_CARE.NOI_MANG_VANG_LAI.equals(careId)
                        || Constant.CUS_CARE.NGOAI_MANG_VANG_LAI.equals(careId)) {
                    if (obAdapter != null && !validateVangLai(obAdapter.getLstData())) {
                        return;
                    }
                }

                OnClickListener updateClick = new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        AsyUpdateStatusContact asyUpdate = new AsyUpdateStatusContact(
                                act);
                        asyUpdate.execute();

                    }
                };
                String msg = "";
                if (Constant.CUS_CARE.NOI_MANG_VANG_LAI.equals(careId)) {
                    msg = getString(R.string.update_care_sub_noimang_vanglai_confirm);
                } else if (Constant.CUS_CARE.NGOAI_MANG_VANG_LAI.equals(careId)) {
                    msg = getString(R.string.update_care_sub_ngoaimang_vanglai_confirm);
                } else if(!"1".equals(programBO.getIsAssign())) {
                    msg = getString(R.string.update_care_sub_ngoaimang_vanglai_confirm);
                } else {
                    msg = getString(R.string.update_care_sub_confirm,
                            selectedIsdn.getIsdn());
                }

                CommonActivity.createDialog(act, msg, getString(R.string.app_name),
                        getString(R.string.ok), getString(R.string.cancel),
                        updateClick, null).show();
                break;
            case R.id.btnCancel:
                dialogUpdate.dismiss();
                break;

            case R.id.lnBack:

                Long lastLevel = lastReasonLevel.get(lastReasonLevel.size() - 1);
                Long currentLevel = reasonAdapter.getLstData().get(0)
                        .getLevelReason();
                mapReasonSelected.remove(currentLevel);
                lastReasonLevel.remove(lastReasonLevel.size() - 1);

                if (CommonActivity.isNullOrEmpty(lastReasonLevel)) {
                    dialogUpdate.findViewById(R.id.lnBack).setVisibility(
                            View.INVISIBLE);
                } else {
                    Long backLevel = lastReasonLevel
                            .get(lastReasonLevel.size() - 1);
                    dialogUpdate.findViewById(R.id.lnBack).setVisibility(
                            View.VISIBLE);
                    tvLastReason.setText(mapReason.get(getKeyReason(backLevel))
                            .getLevelName());
                }
                ReasonCusCareResult tmp = mapReason.get(getKeyReason(lastLevel));
                isMultiChoice = tmp.isMultiChoice();

                if (tmp.isMultiChoice()
                        && tmp.getLstReasonCusCare().get(0).isNextLevel()) {
                    btnNextReasonLevel.setVisibility(View.VISIBLE);
                } else {
                    btnNextReasonLevel.setVisibility(View.INVISIBLE);
                }
                reasonAdapter = new ReasonCareSubExpandAdapter(getActivity(),
                        tmp.getLstReasonCusCare(), isMultiChoice);
                tvReasonGroup.setText(tmp.getLevelName());
                lvReason.setAdapter(reasonAdapter);
                break;
            case R.id.btnNextReasonLevel:
                boolean checked = false;

                currLevel = reasonAdapter.getLstData().get(0).getLevelReason();

                if (mapReasonSelected != null
                        && mapReasonSelected.get(currLevel) != null
                        && !CommonActivity.isNullOrEmpty(mapReasonSelected.get(
                        currLevel).getLstReasonCusCare())) {
                    checked = true;
                }

                if (!checked) {
                    CommonActivity.createAlertDialog(act,
                            R.string.must_select_reason_to_care_sub,
                            R.string.app_name).show();
                    return;
                }

                if (isMultiChoice) {
                    // check cac thuoc tinh bat buoc nhap
                    if (!CommonActivity
                            .isNullOrEmptyArray(lstCurrentReasonCusCares)) {
                        for (ReasonCusCare reasonCusCare : lstCurrentReasonCusCares) {
                            if (reasonCusCare.getOptionType() != null
                                    && reasonCusCare.getOptionType().equals(1)) {
                                boolean existed = false;
                                for (ReasonCusCare reCare : mapReasonSelected.get(
                                        currLevel).getLstReasonCusCare()) {
                                    if (reCare.getReasonId().equals(
                                            reasonCusCare.getReasonId())) {
                                        existed = true;
                                        break;
                                    }
                                }

                                if (!existed) {
                                    CommonActivity
                                            .createAlertDialog(
                                                    act,
                                                    R.string.must_select_reason_to_care_sub,
                                                    R.string.app_name).show();
                                    return;
                                }
                            }
                        }
                    }
                }

                lastReasonLevel.add(currLevel);

                List<ReasonCusCare> lstReasonCusCares = mapReasonSelected.get(
                        currLevel).getLstReasonCusCare();

                List<ReasonCusCare> lstReasonCusCaresPrioity = new ArrayList<>();
                for (ReasonCusCare reasonCusCare : lstReasonCusCares) {
                    if (reasonCusCare.getPriority() != null) {
                        lstReasonCusCaresPrioity.add(reasonCusCare);
                    }
                }

                Long nextLevel = 0L;
                if (!CommonActivity.isNullOrEmptyArray(lstReasonCusCaresPrioity)) {
                    Collections.sort(lstReasonCusCaresPrioity,
                            new Comparator<ReasonCusCare>() {

                                @Override
                                public int compare(ReasonCusCare arg0,
                                                   ReasonCusCare arg1) {
                                    // TODO Auto-generated method stub
                                    return arg0.getPriority().compareTo(
                                            arg1.getPriority());
                                }
                            });

                    nextLevel = lstReasonCusCaresPrioity.get(0).getNextLevel()
                            .longValue();
                }

                Collections.sort(lstReasonCusCares,
                        new Comparator<ReasonCusCare>() {

                            @Override
                            public int compare(ReasonCusCare arg0,
                                               ReasonCusCare arg1) {
                                // TODO Auto-generated method stub
                                return arg0.getReasonId().compareTo(
                                        arg1.getReasonId());
                            }
                        });

                if (nextLevel.equals(0L)) {
                    nextLevel = mapReasonSelected.get(currLevel)
                            .getLstReasonCusCare().get(0).getNextLevel()
                            .longValue();
                }
                boolean isNextLevel = false;
                for (ReasonCusCare reasonCusCare : lstReasonCusCares) {
                    if (!CommonActivity.isNullOrEmptyArray(reasonCusCare
                            .getLstDataReasonMapping())) {
                        for (DataReasonMapping dataReasonMapping : reasonCusCare
                                .getLstDataReasonMapping()) {
                            if (lstReasonIdSelect.contains(dataReasonMapping
                                    .getLastReasonId())) {
                                nextLevel = dataReasonMapping.getNextLevel()
                                        .longValue();
                                isNextLevel = true;
                                break;
                            }
                        }
                    }

                    if (isNextLevel) {
                        break;
                    }
                }

                Log.d(getClass().getSimpleName(), "nextLevel = " + nextLevel);
                AsyGetListReason asyn = new AsyGetListReason(act, nextLevel);
                asyn.execute();

                break;
            case R.id.btnRefreshReason:
                // if (selectedReasonCare == null) {
                // key = "rootTree";
                // } else {
                // key = selectedReasonCare.getReasonCode();
                // }
                // isRefreshReason = true;
                // new CacheDatabaseManager(act).insertReasonCareCache(key, null);
                // mapReason.remove(key);
                // AsyGetListReason asyGetReason = new AsyGetListReason(act);
                // asyGetReason.execute();
                break;
            default:
                break;
        }
    }

    private void nextReason(Long level) {

        ReasonCusCareResult tmp = mapReason.get(getKeyReason(level));
        if (!CommonActivity.isNullOrEmpty(tmp)) {
            for (ReasonCusCare reasonCare : tmp.getLstReasonCusCare()) {
                reasonCare.setChecked(false);
            }
            isMultiChoice = tmp.isMultiChoice();
            lstCurrentReasonCusCares = tmp.getLstReasonCusCare();
            reasonAdapter = new ReasonCareSubExpandAdapter(getActivity(),
                    tmp.getLstReasonCusCare(), tmp.isMultiChoice());
            lvReason.setAdapter(reasonAdapter);

            tvReasonGroup.setText(tmp.getLevelName());
            tvLastReason.setText(mapReason
                    .get(getKeyReason(lastReasonLevel.get(lastReasonLevel
                            .size() - 1))).getLevelName());

            dialogUpdate.findViewById(R.id.lnBack).setVisibility(View.VISIBLE);
            if (tmp.isMultiChoice()
                    && tmp.getLstReasonCusCare().get(0).isNextLevel()) {
                btnNextReasonLevel.setVisibility(View.VISIBLE);
            } else {
                btnNextReasonLevel.setVisibility(View.INVISIBLE);
            }

        }

    }

    // private void refreshReason() {
    // String key;
    // if (selectedReasonCare == null) {
    // key = "rootTree";
    // } else {
    // key = selectedReasonCare.getReasonCode();
    // }
    // ArrayList<ReasonLevelBO> tmp = mapReason.get(key);
    // for (ReasonLevelBO reasonCare : tmp) {
    // reasonCare.setIsChecked(false);
    // }
    // reasonAdapter = new ReasonCareSubAdapter(tmp, act, null, null);
    // lvReason.setAdapter(reasonAdapter);
    // isRefreshReason = false;
    // }

    private class AsyReportCusCare extends AsyncTask<String, Void, BOCOutput> {

        private final Activity mActivity;
        final ProgressDialog progress;

        public AsyReportCusCare(Activity mActivity) {

            this.mActivity = mActivity;

            this.progress = new ProgressDialog(mActivity);
            this.progress.setCancelable(false);
            this.progress.setMessage(mActivity.getResources().getString(
                    R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }

        }

        @Override
        protected BOCOutput doInBackground(String... params) {

            return reportCusCare();
        }

        @Override
        protected void onPostExecute(BOCOutput result) {
            super.onPostExecute(result);
            progress.dismiss();

            if (result.getErrorCode().equals("0")) {
                showReportCusCare(result);
            } else {
                if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
                    Dialog dialog = CommonActivity
                            .createAlertDialog(mActivity, result
                                    .getDescription(), mActivity.getResources()
                                    .getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    Dialog dialog = CommonActivity.createAlertDialog(
                            getActivity(), result.getDescription(),
                            getResources().getString(R.string.app_name));
                    dialog.show();

                }
            }

        }

        private BOCOutput reportCusCare() {
            BOCOutput bocOutput = new BOCOutput();
            String original = "";
            try {
                String methodName = "getListReportCusCare";
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_" + methodName);

                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:").append(methodName).append(">");
                rawData.append("<input>");
                rawData.append("<token>").append(Session.getToken()).append("</token>");
                rawData.append("<staffCode>").append(StringUtils.escapeHtml(edtStaff.getText().toString()
                        .trim().toUpperCase())).append("</staffCode>");
                rawData.append("</input>");
                rawData.append("</ws:").append(methodName).append(">");

                Log.i("RowData", rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope,
                        Constant.BCCS_GW_URL, getActivity(), "mbccs_"
                                + methodName);
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("Responseeeeeeeeee Original", response);

                // parser
                Serializer serializer = new Persister();
                bocOutput = serializer.read(BOCOutput.class, original);
                if (bocOutput == null) {
                    bocOutput = new BOCOutput();
                    bocOutput
                            .setDescription(getString(R.string.no_return_from_system));
                    bocOutput.setErrorCode(Constant.ERROR_CODE);
                    return bocOutput;
                } else {
                    return bocOutput;
                }
            } catch (Exception e) {
                Log.e(Constant.TAG, "searchListIsdn", e);
                bocOutput = new BOCOutput();
                bocOutput.setDescription(CommonActivity.getDescription(
                        getActivity(), e));
                bocOutput.setErrorCode(Constant.ERROR_CODE);
            }
            return bocOutput;
        }
    }

    private void showReportCusCare(BOCOutput bocOutput) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.item_report_cus_care);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        ReportCusCareBean reportCusCareBean = bocOutput
                .getLstReportCusCareBeans().get(0);

        ((TextView) dialog.findViewById(R.id.txtThueBaoDuocGiao)).setText(""
                + reportCusCareBean.getTotalSub());
        ((TextView) dialog.findViewById(R.id.txtTiepXucNgay)).setText(""
                + reportCusCareBean.getTotalDay());
        ((TextView) dialog.findViewById(R.id.txtTiepXucLuyKe)).setText(""
                + reportCusCareBean.getTotalMonth());
        ((TextView) dialog.findViewById(R.id.txtThueBaoConLai)).setText(""
                + reportCusCareBean.getRemainSub());
        ((TextView) dialog.findViewById(R.id.txtThueBaoKHHen)).setText(""
                + reportCusCareBean.getCusApp());

        ((TextView) dialog.findViewById(R.id.txtThueBaoDuocGiaoNM)).setText(""
                + reportCusCareBean.getTotalSubOtherViettelSim());
        ((TextView) dialog.findViewById(R.id.txtTiepXucNgayNM)).setText(""
                + reportCusCareBean.getTotalDayOtherViettelSim());
        ((TextView) dialog.findViewById(R.id.txtTiepXucLuyKeNM)).setText(""
                + reportCusCareBean.getTotalMonthOtherViettelSim());
        ((TextView) dialog.findViewById(R.id.txtThueBaoConLaiNM)).setText(""
                + reportCusCareBean.getRemainSubOtherViettelSim());

        ((TextView) dialog.findViewById(R.id.txtTBNoiMangVLTX)).setText(""
                + reportCusCareBean.getTotalSubViettelVL());

        ((TextView) dialog.findViewById(R.id.txtTBNgoaiMangVLTX)).setText(""
                + reportCusCareBean.getTotalSubOtherViettelVL());

        Button btnClose = (Button) dialog.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private final View.OnClickListener imageViewHistoryListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            AssignIsdnStaffBean assignIsdnStaffBean = (AssignIsdnStaffBean) v
                    .getTag();
            AsyGetListViewCareHistory asyn = new AsyGetListViewCareHistory(
                    getActivity());
            asyn.execute(assignIsdnStaffBean.getIsdn());
        }
    };

    private final View.OnClickListener checkBoxSelectListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            AssignIsdnStaffBean assignIsdnStaffBean = (AssignIsdnStaffBean) v
                    .getTag();
            CheckBox cb = (CheckBox) v;

            if (cb.isChecked()) {
                assignIsdnStaffBean.setSelect(true);
            } else {
                assignIsdnStaffBean.setSelect(false);
            }

            displayBtnBook();

            selectCheckBoxAll();
        }
    };

    private void displayBtnBook() {
        for (AssignIsdnStaffBean assignIsdnStaffBean : lstAssignIsdnStaffBean) {
            if (assignIsdnStaffBean.isSelect()) {
                btnBook.setVisibility(View.VISIBLE);
                return;
            }
        }
        btnBook.setVisibility(View.GONE);
    }

    private void selectCheckBoxAll() {
        for (AssignIsdnStaffBean assignIsdnStaffBean : lstAssignIsdnStaffBean) {
            if (!assignIsdnStaffBean.isSelect()) {
                cbSelectAll.setChecked(false);
                return;
            }
        }
        cbSelectAll.setChecked(true);
    }

    private class AsyGetListViewCareHistory extends
            AsyncTask<String, Void, BOCOutput> {

        private final Activity mActivity;
        final ProgressDialog progress;

        public AsyGetListViewCareHistory(Activity mActivity) {

            this.mActivity = mActivity;

            this.progress = new ProgressDialog(mActivity);
            this.progress.setCancelable(false);
            this.progress.setMessage(mActivity.getResources().getString(
                    R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }

        }

        @Override
        protected BOCOutput doInBackground(String... params) {
            return getListViewCareHistory(params[0]);
        }

        @Override
        protected void onPostExecute(BOCOutput result) {
            super.onPostExecute(result);
            progress.dismiss();

            if (result.getErrorCode().equals("0")
                    && result.getLstViewCareHistoryBOs() != null
                    && !result.getLstViewCareHistoryBOs().isEmpty()) {
                showDetailViewCareHistory(result);
            } else {
                if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
                    Dialog dialog = CommonActivity
                            .createAlertDialog(mActivity, result
                                    .getDescription(), mActivity.getResources()
                                    .getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    Dialog dialog = CommonActivity.createAlertDialog(
                            getActivity(), result.getDescription(),
                            getResources().getString(R.string.app_name));
                    dialog.show();

                }
            }

        }

        private BOCOutput getListViewCareHistory(String isdn) {
            BOCOutput bocOutput = new BOCOutput();
            String original = "";
            try {
                String methodName = "getListViewCareHistory";
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_" + methodName);

                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:").append(methodName).append(">");
                rawData.append("<input>");

                rawData.append("<token>").append(Session.getToken()).append("</token>");
                rawData.append("<isdn>").append(isdn).append("</isdn>");
                rawData.append("<type>").append(careId).append("</type>");

                rawData.append("</input>");
                rawData.append("</ws:").append(methodName).append(">");

                Log.i("RowData", rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope,
                        Constant.BCCS_GW_URL, getActivity(), "mbccs_"
                                + methodName);
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("Responseeeeeeeeee Original", response);

                // parser
                Serializer serializer = new Persister();
                bocOutput = serializer.read(BOCOutput.class, original);
                if (bocOutput == null) {
                    bocOutput = new BOCOutput();
                    bocOutput
                            .setDescription(getString(R.string.no_return_from_system));
                    bocOutput.setErrorCode(Constant.ERROR_CODE);
                    return bocOutput;
                } else {
                    return bocOutput;
                }
            } catch (Exception e) {
                Log.e(Constant.TAG, "getListViewCareHistory", e);
                bocOutput = new BOCOutput();
                bocOutput.setDescription(CommonActivity.getDescription(
                        getActivity(), e));
                bocOutput.setErrorCode(Constant.ERROR_CODE);
            }
            return bocOutput;
        }

    }

    private void showDetailViewCareHistory(BOCOutput bocOutput) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.item_view_care_history);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);

        if (!CommonActivity.isNullOrEmpty(bocOutput.getNote())) {
            LinearLayout llNote = (LinearLayout) dialog
                    .findViewById(R.id.llNote);
            llNote.setVisibility(View.VISIBLE);

            TextView tvNote = (TextView) dialog.findViewById(R.id.tvNote);
            tvNote.setText(bocOutput.getNote());
        }

        ExpandableListView exListView = (ExpandableListView) dialog
                .findViewById(R.id.expanListView);
        // exListView.setDividerHeight(3);
        exListView.setGroupIndicator(null);
        exListView.setClickable(true);

        ViewCareHistoryAdapter adapter = new ViewCareHistoryAdapter(
                getActivity(),
                initMapData(bocOutput.getLstViewCareHistoryBOs()));
        exListView.setAdapter(adapter);

        // exListView.setOnChildClickListener(new
        // ExpandableListView.OnChildClickListener() {
        // @Override
        // public boolean onChildClick(ExpandableListView arg0, View arg1, int
        // arg2, int arg3, long arg4) {
        // // TODO Auto-generated method stub
        // return false;
        // }
        // });

        ImageView btnClose = (ImageView) dialog.findViewById(R.id.btn_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private <T> Map<Long, ArrayList<ViewCareHistoryBO>> initMapData(
            ArrayList<ViewCareHistoryBO> lstViewCareHistoryBOs) {
        Map<Long, ArrayList<ViewCareHistoryBO>> mapData = new HashMap<>();

        Long index = 0L;
        Long key = lstViewCareHistoryBOs.get(0).getLevelValue();
        for (ViewCareHistoryBO viewCareHistoryBO : lstViewCareHistoryBOs) {
            if (key.equals(viewCareHistoryBO.getLevelValue())) {
                if (mapData.containsKey(index)) {
                    mapData.get(index).add(viewCareHistoryBO);

                    Log.d(getClass().getSimpleName(),
                            "mapData.get(index).add(viewCareHistoryBO) size = "
                                    + mapData.get(index).size());
                } else {
                    ArrayList<ViewCareHistoryBO> lstCareHistoryBOs = new ArrayList<>();
                    lstCareHistoryBOs.add(viewCareHistoryBO);

                    Log.d("mapData", "mapData put key = " + index);
                    mapData.put(index, lstCareHistoryBOs);
                }
            } else {
                Collections.sort(mapData.get(index),
                        new Comparator<ViewCareHistoryBO>() {

                            @Override
                            public int compare(ViewCareHistoryBO arg0,
                                               ViewCareHistoryBO arg1) {
                                // TODO Auto-generated method stub
                                return arg0.getReasonName().compareTo(
                                        arg1.getReasonName());
                            }
                        });

                ++index;
                key = viewCareHistoryBO.getLevelValue();

                ArrayList<ViewCareHistoryBO> lstCareHistoryBOs = new ArrayList<>();
                lstCareHistoryBOs.add(viewCareHistoryBO);

                Log.d("mapData", "mapData put key = " + index);
                mapData.put(index, lstCareHistoryBOs);
            }
        }

        if (mapData.containsKey(index)) {
            Collections.sort(mapData.get(index),
                    new Comparator<ViewCareHistoryBO>() {

                        @Override
                        public int compare(ViewCareHistoryBO arg0,
                                           ViewCareHistoryBO arg1) {
                            // TODO Auto-generated method stub
                            return arg0.getReasonName().compareTo(
                                    arg1.getReasonName());
                        }
                    });

        }

        Log.d("AAAAA", "lstViewCareHistoryBOs.size() = "
                + lstViewCareHistoryBOs.size());
        Log.d("BBBBB", "mapData size = " + mapData.size());
        return mapData;
    }

    private final OnClickListener bookListener = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            new AsyUpdateSchedule(getActivity()).execute();

        }
    };

    private class AsyUpdateSchedule extends AsyncTask<String, Void, BOCOutput> {

        private final Activity mActivity;
        final ProgressDialog progress;

        public AsyUpdateSchedule(Activity mActivity) {

            this.mActivity = mActivity;

            this.progress = new ProgressDialog(mActivity);
            this.progress.setCancelable(false);
            this.progress.setMessage(mActivity.getResources().getString(
                    R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }

        }

        @Override
        protected BOCOutput doInBackground(String... params) {

            return updateSchedule();
        }

        @Override
        protected void onPostExecute(BOCOutput result) {
            super.onPostExecute(result);
            progress.dismiss();
            if (result.getErrorCode().equals("0")) {
                CommonActivity.createAlertDialog(mActivity,
                        getString(R.string.txt_dat_lich_hen_success),
                        getString(R.string.app_name)).show();
            } else {
                if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
                    Dialog dialog = CommonActivity
                            .createAlertDialog(mActivity, result
                                    .getDescription(), mActivity.getResources()
                                    .getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (result.getDescription() == null
                            || result.getDescription().isEmpty()) {
                        result.setDescription(mActivity.getResources()
                                .getString(R.string.checkdes));
                    }

                    Dialog dialog = CommonActivity.createAlertDialog(
                            getActivity(), result.getDescription(),
                            getResources().getString(R.string.app_name));
                    dialog.show();
                }
            }

        }

        private BOCOutput updateSchedule() {
            BOCOutput bocOutput = new BOCOutput();
            String original = "";
            try {
                String methodName = "saveSchedule";
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_" + methodName);
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:").append(methodName).append(">");
                rawData.append("<input>");
                rawData.append("<token>").append(Session.getToken()).append("</token>");

                if (!CommonActivity
                        .isNullOrEmpty(edtStaff.getText().toString())
                        && menu_vsa.contains(Constant.VSAMenu.VSA_BOOK_SMS)) {
                    rawData.append("<staffCode>").append(StringUtils.escapeHtml(edtStaff.getText()
                            .toString().trim().toUpperCase())).append("</staffCode>");
                }

                rawData.append("<token>").append(Session.getToken()).append("</token>");

                for (AssignIsdnStaffBean assignIsdnStaffBean : lstAssignIsdnStaffBean) {
                    if (assignIsdnStaffBean.isSelect()) {
                        rawData.append("<lstSheduleSendMessage>");
                        rawData.append("<isdn>").append(assignIsdnStaffBean.getIsdn()).append("</isdn>");
                        rawData.append("<timeSendMessage>").append(dateBook).append("</timeSendMessage>");
                        rawData.append("</lstSheduleSendMessage>");
                    }
                }

                rawData.append("<type>").append(careId).append("</type>");

                rawData.append("</input>");
                rawData.append("</ws:").append(methodName).append(">");
                Log.i("RowData", rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());

                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope,
                        Constant.BCCS_GW_URL, getActivity(), "mbccs_"
                                + methodName);
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("Responseeeeeeeeee Original", original);

                // parser
                Serializer serializer = new Persister();
                bocOutput = serializer.read(BOCOutput.class, original);
                if (bocOutput == null) {
                    bocOutput = new BOCOutput();
                    bocOutput
                            .setDescription(getString(R.string.no_return_from_system));
                    bocOutput.setErrorCode(Constant.ERROR_CODE);
                    return bocOutput;
                } else {
                    return bocOutput;
                }
            } catch (Exception e) {
                Log.e(Constant.TAG, "updateSchedule", e);
                bocOutput = new BOCOutput();
                bocOutput.setDescription(CommonActivity.getDescription(
                        getActivity(), e));
                bocOutput.setErrorCode(Constant.ERROR_CODE);
            }

            return bocOutput;
        }

    }

    private int day;
    private int month;
    private int year;

    private void showDialogFromDate() {
        String date = edtFromDate.getText().toString();
        if (CommonActivity.isNullOrEmpty(date)) {
            Calendar cal = Calendar.getInstance();
            day = cal.get(Calendar.DAY_OF_MONTH);
            month = cal.get(Calendar.MONTH);
            year = cal.get(Calendar.YEAR);
        } else {
            String[] params = date.split("/");
            if(params.length == 3){
                day = Integer.valueOf(params[0]);
                month = Integer.valueOf(params[1]) - 1;
                year = Integer.valueOf(params[2]);
            }

        }

        DatePickerDialog fromDateDialog = new FixedHoloDatePickerDialog(getActivity(),
                AlertDialog.THEME_HOLO_LIGHT,fromDatePickerListener, year, month, day);
        fromDateDialog.show();
    }

    private void showDialogToDate() {
        String date = edtToDate.getText().toString();
        if (CommonActivity.isNullOrEmpty(date)) {
            Calendar cal = Calendar.getInstance();
            day = cal.get(Calendar.DAY_OF_MONTH);
            month = cal.get(Calendar.MONTH);
            year = cal.get(Calendar.YEAR);
        } else {
            String[] params = date.split("/");
            day = Integer.valueOf(params[0]);
            month = Integer.valueOf(params[1]) - 1;
            year = Integer.valueOf(params[2]);
        }

        DatePickerDialog fromDateDialog = new FixedHoloDatePickerDialog(getActivity(),
                AlertDialog.THEME_HOLO_LIGHT, toDatePickerListener, year, month, day);
        fromDateDialog.show();
    }

    private final DatePickerDialog.OnDateSetListener fromDatePickerListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            StringBuilder strDate = new StringBuilder();
            if (selectedDay < 10) {
                strDate.append("0");
            }
            strDate.append(selectedDay).append("/");
            if (selectedMonth < 9) {
                strDate.append("0");
            }
            strDate.append(selectedMonth + 1).append("/");
            strDate.append(selectedYear);

            edtFromDate.setText(strDate);
        }
    };

    private final DatePickerDialog.OnDateSetListener toDatePickerListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            StringBuilder strDate = new StringBuilder();
            if (selectedDay < 10) {
                strDate.append("0");
            }
            strDate.append(selectedDay).append("/");
            if (selectedMonth < 9) {
                strDate.append("0");
            }
            strDate.append(selectedMonth + 1).append("/");
            strDate.append(selectedYear);

            edtToDate.setText(strDate);
        }
    };

    private boolean validateViewScheduleHistory() {
        if (CommonActivity.isNullOrEmpty(edtFromDate.getText().toString())) {
            CommonActivity.createAlertDialog(getActivity(),
                    getString(R.string.notstartdate),
                    getString(R.string.app_name)).show();
            return false;
        }

        if (CommonActivity.isNullOrEmpty(edtToDate.getText().toString())) {
            CommonActivity
                    .createAlertDialog(getActivity(),
                            getString(R.string.notendate),
                            getString(R.string.app_name)).show();
            return false;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date from = sdf.parse(edtFromDate.getText().toString());
            Date to = sdf.parse(edtToDate.getText().toString());
            if (from.after(to)) {
                CommonActivity.createAlertDialog(getActivity(),
                        getString(R.string.checktimeupdatejob),
                        getString(R.string.app_name)).show();
                return false;
            }

            long diff = to.getTime() - from.getTime();
            long dayDiff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            if (dayDiff > 30) {
                CommonActivity.createAlertDialog(getActivity(),
                        getString(R.string.modify_profile_search_err01),
                        getString(R.string.app_name)).show();
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean validateDate() {
        if (CommonActivity.isNullOrEmpty(edtFromDate.getText())
                && CommonActivity.isNullOrEmpty(edtToDate.getText())) {
            return true;
        }

        if (CommonActivity.isNullOrEmpty(edtFromDate.getText().toString())
                && CommonActivity.isNullOrEmpty(edtToDate.getText().toString())) {
            return true;
        }

        if (!CommonActivity.isNullOrEmpty(edtFromDate.getText().toString())
                && !CommonActivity
                .isNullOrEmpty(edtToDate.getText().toString())) {
            Calendar calFromDate = Calendar.getInstance();
            calFromDate.setTime(DateTimeUtils.convertStringToTime(edtFromDate
                    .getText().toString(), "dd/MM/yyyy"));

            Calendar calToDate = Calendar.getInstance();
            calToDate.setTime(DateTimeUtils.convertStringToTime(edtToDate
                    .getText().toString(), "dd/MM/yyyy"));

            if (calFromDate.getTime().after(calToDate.getTime())) {
                CommonActivity.createAlertDialog(getActivity(),
                        getString(R.string.checktimeupdatejob),
                        getString(R.string.app_name)).show();
                return false;
            }

            if (calFromDate.get(Calendar.MONTH) != calToDate
                    .get(Calendar.MONTH)
                    || calFromDate.get(Calendar.YEAR) != calToDate
                    .get(Calendar.YEAR)) {
                CommonActivity.createAlertDialog(getActivity(),
                        getString(R.string.txt_only_search_in_month),
                        getString(R.string.app_name)).show();
                return false;
            }
            return true;
        } else {
            CommonActivity.createAlertDialog(getActivity(),
                    getString(R.string.txt_search_to_date),
                    getString(R.string.app_name)).show();
            return false;
        }

    }

    private String getKeyReason(Long level) {
        return level + ";" + careId;
    }

    private void showDialogProperty(
            ArrayList<ReasonCusCareProperty> lstReasonCusCareProperties) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.boc_layout_dialog_property);
        dialog.setCancelable(true);

        ListView lvProperty = (ListView) dialog.findViewById(R.id.lvProperty);
        lvProperty.setItemsCanFocus(true);

        final ReasonPropertyAdapter adapter = new ReasonPropertyAdapter(
                getActivity(), lstReasonCusCareProperties);
        lvProperty.setAdapter(adapter);

        Button btnDongY = (Button) dialog.findViewById(R.id.btnOK);
        btnDongY.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (!validateSelectReason(adapter.getLstData())) {

                    return;
                }

                tmp.setLstReasonCusCareProperty(adapter.getLstData());
                selectReason(tmp);

                dialog.dismiss();
            }
        });

        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        dialog.getWindow().setSoftInputMode(
                LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show();

    }

    // private class AsyShowDialogProperty extends AsyncTask<Void, Void, Void> {
    //
    // private Activity mActivity;
    // ProgressDialog progress;
    // private ArrayList<ReasonCusCareProperty> lstReasonCusCareProperties;
    //
    // public AsyShowDialogProperty(Activity mActivity,
    // ArrayList<ReasonCusCareProperty> lstReasonCusCareProperties) {
    //
    // this.mActivity = mActivity;
    // this.progress = new ProgressDialog(mActivity);
    // this.progress.setCancelable(false);
    // this.progress.setMessage(mActivity.getResources().getString(R.string.getdataing));
    // this.lstReasonCusCareProperties = lstReasonCusCareProperties;
    // if (!this.progress.isShowing()) {
    // this.progress.show();
    // }
    // }
    //
    // @Override
    // protected Void doInBackground(Void... arg0) {
    // // TODO Auto-generated method stub
    // showDialogProperty(this.lstReasonCusCareProperties);
    // return null;
    // }
    //
    // @Override
    // protected void onPostExecute(Void result) {
    // // TODO Auto-generated method stub
    //
    // this.progress.dismiss();
    // }
    // }

    private boolean validateSelectReason(
            ArrayList<ReasonCusCareProperty> lstData) {
        for (ReasonCusCareProperty property : lstData) {
            if (property.getOptionType() != null
                    && property.getOptionType().equals(1)) {
                if (CommonActivity.isNullOrEmpty(property.getValue())) {
                    Dialog dialog = CommonActivity.createAlertDialog(
                            getActivity(), getString(R.string.checkinfo),
                            getString(R.string.app_name));
                    dialog.show();
                    return false;
                }
            }

            if (!CommonActivity.isNullOrEmpty(property.getCheckIsdnViettel())) {
                if (property.getCheckIsdnViettel().equals(1)
                        || property.getCheckIsdnViettel().equals(2)) {
                    if (!StringUtils.isViettelMobile(property.getValue())) {
                        CommonActivity.createAlertDialog(act,
                                R.string.isdn_not_viettel_mobile,
                                R.string.app_name).show();
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean validateVangLai(
            ArrayList<ObjectProperty> lstObjectProperties) {
        if (CommonActivity.isNullOrEmptyArray(lstObjectProperties)) {
            return true;
        }

        for (ObjectProperty objectProperty : lstObjectProperties) {
            if (objectProperty.getOptionType().equals(1)) {
                if (CommonActivity.isNullOrEmpty(objectProperty.getValue())) {
                    CommonActivity.createAlertDialog(act, R.string.checkinfo,
                            R.string.app_name).show();
                    return false;
                }

                if (Constant.CUS_CARE.NOI_MANG_VANG_LAI.equals(careId)) {
                    if (!StringUtils.isViettelMobile(objectProperty.getValue()
                            .trim())) {
                        CommonActivity.createAlertDialog(act,
                                R.string.isdn_not_viettel_mobile,
                                R.string.app_name).show();
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void selectReason(ReasonCusCare tmp) {
        if(!CommonActivity.isNullOrEmpty(tmp.getLstReasonCusCareProperty())) {
            if (FORWARD_UPDATE_CDT == tmp.getLstReasonCusCareProperty().get(0).getDataType()) {
                if (!"1".equals(programBO.getIsAssign())) {
                    for (ObjectProperty obj : obAdapter.getLstData()) {
                        if ("ISDN".equals(obj.getPropertyCode())) {
                            if (CommonActivity.isNullOrEmpty(obj.getValue())) {
                                CommonActivity.createAlertDialog(act, R.string.checkaccount,
                                        R.string.app_name).show();
                                return;
                            }
                        }
                    }
                }
            }
        }

        Long currLevel = tmp.getLevelReason();
        ReasonCusCareResult reasonLevel = mapReasonSelected.get(currLevel);
        if (reasonLevel == null) {
            reasonLevel = new ReasonCusCareResult();
            reasonLevel.setLevelValue(currLevel);
            mapReasonSelected.put(currLevel, reasonLevel);
        }

        if (isMultiChoice) {
            if (tmp.isChecked()) {
                lstReasonIdSelect.remove(tmp.getReasonId());
            } else {
                lstReasonIdSelect.add(tmp.getReasonId());
            }
        } else {
            if (tmp.isChecked()) {
                lstReasonIdSelect.remove(tmp.getReasonId());
            } else {
                lstReasonIdSelect.add(tmp.getReasonId());
            }
        }

        Log.d("lstReasonIdSelect", lstReasonIdSelect.toString());

        tmp.setMultiChoice(isMultiChoice);
        if (isMultiChoice) {
            tmp.setChecked(!tmp.isChecked());
            for (ReasonCusCare careItem : reasonAdapter.getLstData()) {
                if (careItem.getReasonId().equals(tmp.getReasonId())) {
                    careItem.setChecked(tmp.isChecked());
                    if (careItem.isChecked()) {
                        mapReasonSelected.get(currLevel).getLstReasonCusCare()
                                .add(careItem);
                    } else {
                        for (int i = 0; i < mapReasonSelected.get(currLevel)
                                .getLstReasonCusCare().size(); i++) {
                            if (mapReasonSelected.get(currLevel)
                                    .getLstReasonCusCare().get(i).getReasonId()
                                    .compareTo(tmp.getReasonId()) == 0) {
                                mapReasonSelected.get(currLevel)
                                        .getLstReasonCusCare().remove(i);
                                break;
                            }
                        }
                    }
                    break;
                }
            }
            reasonAdapter.notifyDataSetChanged();
        } else {
            mapReasonSelected.get(currLevel).getLstReasonCusCare().clear();
            if (!tmp.isNextLevel()) {
                tmp.setChecked(!tmp.isChecked());
                for (ReasonCusCare careItem : reasonAdapter.getLstData()) {
                    if (careItem.getReasonId().equals(tmp.getReasonId())) {
                        careItem.setChecked(tmp.isChecked());
                        if (careItem.isChecked()) {
                            mapReasonSelected.get(currLevel)
                                    .getLstReasonCusCare().add(careItem);
                        }
                    } else {
                        careItem.setChecked(false);
                    }
                }

                reasonAdapter.notifyDataSetChanged();
            } else {
                lastReasonLevel.add(currLevel);

                mapReasonSelected
                        .get(reasonAdapter.getLstData().get(0).getLevelReason())
                        .getLstReasonCusCare().add(tmp);

                for (ReasonCusCare careItem : reasonAdapter.getLstData()) {
                    if (careItem.getReasonId().equals(tmp.getReasonId())) {
                        careItem.setChecked(tmp.isChecked());
                    } else {
                        careItem.setChecked(false);
                    }
                }

                Long level = tmp.getNextLevel().longValue();
                if (!CommonActivity.isNullOrEmptyArray(tmp
                        .getLstDataReasonMapping())) {
                    for (DataReasonMapping data : tmp.getLstDataReasonMapping()) {
                        if (lstReasonIdSelect.contains(data.getLastReasonId())) {
                            level = data.getNextLevel().longValue();
                            break;
                        }
                    }
                }

                reasonAdapter.notifyDataSetChanged();

                AsyGetListReason asy = new AsyGetListReason(act, level);
                asy.execute();
            }
        }

        if (!CommonActivity.isNullOrEmptyArray(tmp.getLstReasonCusCareProperty())) {
            if (FORWARD_CHANGE_PACKAGE == tmp.getLstReasonCusCareProperty().get(0).getDataType().intValue()) {
                if (tmp.isChecked()) {
                    changePackage(tmp.getReasonCode());
                    dialogUpdate.dismiss();
                }
            } else if (FORWARD_UPDATE_CDT == tmp.getLstReasonCusCareProperty().get(0).getDataType().intValue()){
                if (tmp.isChecked()) {
                    updateCDT(tmp.getReasonCode());
                    dialogUpdate.dismiss();
                }
            }
            //// TODO: 6/30/2017  type = 6 : thanh ly thu hoi day gia tri cua doi tuong CSKHBO sang man hinh searchsub
            // TODO Trong doi tuong CSKHBO them truong reasonCode
            // TODO Khi ma thuc hien thanh ly va thu hoi thi day them ban tin xml = truong CSKHBO.getRawData
            // rawData.append("<bocInput>");
            // rawData.append(cskhBO.getRawData());
            // rawData.append("</bocInput>");
            else if(LIQUIDATION_RECOVERY== tmp.getLstReasonCusCareProperty().get(0).getDataType().intValue()){
//                if (tmp.isChecked()) {
                    changeLiquiddation(tmp.getReasonCode());
                    dialogUpdate.dismiss();
//                }
            } else if (TICK_APPOINT == tmp.getLstReasonCusCareProperty().get(0).getDataType().intValue()){
//                if(tmp.isChecked()){
                    tickAppoint(tmp.getReasonCode());
                    dialogUpdate.dismiss();
//                }
            }

        }
    }

    private void changeLiquiddation(String changePackage) {
        Bundle bundle = new Bundle();

        CSKHBO cskhbo = new CSKHBO();
        cskhbo.setIsdn(selectedIsdn.getIsdn());
        cskhbo.setPackageChange(changePackage);
        cskhbo.setRawData(getRawData());
        bundle.putSerializable("cskhbo", cskhbo);

        ManageSubscriberFragmentStrategy blockOpenSubHomess = new BlockOpenSubManagerFragment();
        bundle.putSerializable("fragmentStrategy",
                blockOpenSubHomess);
        bundle.putString("funtionTypeKey", Constant.LIQUIDATION_RECOVERY);
        FragmentManageSubscriber fragmentManage = new FragmentManageSubscriber();
        fragmentManage.setArguments(bundle);
        fragmentManage.setTargetFragment(FragmentCareLostSub.this, 100);
        ReplaceFragment.replaceFragment(getActivity(),
                fragmentManage, false);


    }

    private void changePackage(String changePackage) {
        ManageSubscriberFragmentStrategy manageSubscriberFragmentStrategy = new ChangeProductFragmentStrategy();
        Bundle bundle = new Bundle();
        bundle.putSerializable("fragmentStrategy",
                manageSubscriberFragmentStrategy);

        CSKHBO cskhbo = new CSKHBO();
        cskhbo.setIsdn(selectedIsdn.getIsdn());
        cskhbo.setPackageChange(changePackage);
        cskhbo.setRawData(getRawData());
        bundle.putSerializable("cskhbo", cskhbo);


        FragmentManageSubscriber fragmentManageSubscriber = new FragmentManageSubscriber();
        fragmentManageSubscriber.setArguments(bundle);
        fragmentManageSubscriber.setTargetFragment(this, 100);

        ReplaceFragment.replaceFragment(getActivity(),
                fragmentManageSubscriber, false);


    }

    private void updateCDT(String cdtCode) {
        ManageSubscriberFragmentStrategy manageSubscriberFragmentStrategy1 = new ChangePromotionFragmentStrategy();
        Bundle bundle = new Bundle();
        bundle.putSerializable("fragmentStrategy",
                manageSubscriberFragmentStrategy1);

        CSKHBO cskhbo = new CSKHBO();
        if(!"1".equals(programBO.getIsAssign())){
            for(ObjectProperty obj : obAdapter.getLstData()){
                if("ISDN".equals(obj.getPropertyCode())){
                    cskhbo.setIsdn(obj.getValue());
                }
            }
        } else {
            cskhbo.setIsdn(selectedIsdn.getIsdn());
        }

        cskhbo.setCdtCode(cdtCode);
        cskhbo.setRawData(getRawData());
        bundle.putSerializable("cskhbo", cskhbo);

        FragmentManageSubscriber fragmentManageSubscriber = new FragmentManageSubscriber();
        fragmentManageSubscriber.setArguments(bundle);
        fragmentManageSubscriber.setTargetFragment(this, 100);
        ReplaceFragment.replaceFragment(getActivity(),
                fragmentManageSubscriber, false);
    }

    private void tickAppoint(String cdtCode) {
        ManageSubscriberFragmentStrategy manageSubscriberFragmentStrategy1 = new TickAppointFragmentStrategy();
        Bundle bundle = new Bundle();
        bundle.putSerializable("fragmentStrategy",
                manageSubscriberFragmentStrategy1);

        CSKHBO cskhbo = new CSKHBO();
        if(!"1".equals(programBO.getIsAssign())){
            for(ObjectProperty obj : obAdapter.getLstData()){
                if("ISDN".equals(obj.getPropertyCode())){
                    cskhbo.setIsdn(obj.getValue());
                }
            }
        } else {
            cskhbo.setIsdn(selectedIsdn.getIsdn());
        }

        cskhbo.setCdtCode(cdtCode);
        cskhbo.setRawData(getRawData());
        bundle.putSerializable("cskhbo", cskhbo);

        FragmentManageSubscriber fragmentManageSubscriber = new FragmentManageSubscriber();
        fragmentManageSubscriber.setArguments(bundle);
        fragmentManageSubscriber.setTargetFragment(this, 100);
        ReplaceFragment.replaceFragment(getActivity(),
                fragmentManageSubscriber, false);
    }

    private String getRawData() {
        StringBuilder rawData = new StringBuilder();
        if (Constant.CUS_CARE.NOI_MANG.equals(careId)
                || Constant.CUS_CARE.NGOAI_MANG.equals(careId)
                || Constant.CUS_CARE.DOI_4G.equals(careId)) {
            rawData.append("<isdn>" + selectedIsdn.getIsdn()
                    + "</isdn>");
        }

        rawData.append("<type>" + careId + "</type>");
        rawData.append("<status>" + statusId + "</status>");

        for (Map.Entry<Long, ReasonCusCareResult> entrySet : mapReasonSelected
                .entrySet()) {
            ReasonCusCareResult bo = entrySet.getValue();
            for (ReasonCusCare reasonCusCare : bo.getLstReasonCusCare()) {
                rawData.append("<lstReasonCusCares>");

                if (!CommonActivity.isNullOrEmpty(reasonCusCare
                        .getCode())) {
                    rawData.append("<code>" + reasonCusCare.getCode()
                            + "</code>");
                }

                if (!CommonActivity.isNullOrEmpty(reasonCusCare
                        .getLevelReason())) {
                    rawData.append("<levelReason>"
                            + reasonCusCare.getLevelReason()
                            + "</levelReason>");
                }

                if (!CommonActivity.isNullOrEmpty(reasonCusCare
                        .getNextLevel())) {
                    rawData.append("<nextLevel>"
                            + reasonCusCare.getNextLevel()
                            + "</nextLevel>");
                }

                if (!CommonActivity.isNullOrEmpty(reasonCusCare
                        .getReasonId())) {
                    rawData.append("<reasonId>"
                            + reasonCusCare.getReasonId()
                            + "</reasonId>");
                }

                if (!CommonActivity.isNullOrEmpty(reasonCusCare
                        .getIsUpdate())) {
                    rawData.append("<isUpdate>"
                            + reasonCusCare.getIsUpdate()
                            + "</isUpdate>");
                }

                if (!CommonActivity.isNullOrEmpty(reasonCusCare
                        .getPriority())) {
                    rawData.append("<priority>"
                            + reasonCusCare.getPriority()
                            + "</priority>");
                }

                if (!CommonActivity.isNullOrEmpty(reasonCusCare
                        .getReasonValue())) {
                    rawData.append("<reasonValue>"
                            + reasonCusCare.getReasonValue()
                            + "</reasonValue>");
                }

                if (!CommonActivity.isNullOrEmptyArray(reasonCusCare
                        .getLstReasonCusCareProperty())) {
                    if (FORWARD_CHANGE_PACKAGE != reasonCusCare.getLstReasonCusCareProperty().get(0).getDataType()) {
                        for (ReasonCusCareProperty reProperty : reasonCusCare
                                .getLstReasonCusCareProperty()) {
                            rawData.append("<lstReasonCusCareProperty>");

                            rawData.append("<checkIsdnViettel>"
                                    + reProperty.getCheckIsdnViettel()
                                    + "</checkIsdnViettel>");
                            rawData.append("<code>" + reProperty.getCode()
                                    + "</code>");
                            rawData.append("<name>"
                                    + StringUtils.escapeHtml(reProperty
                                    .getName()) + "</name>");
                            rawData.append("<value>"
                                    + reProperty.getValue() + "</value>");
                            rawData.append("<dataType>"
                                    + reProperty.getDataType()
                                    + "</dataType>");
                            rawData.append("<optionType>"
                                    + reProperty.getOptionType()
                                    + "</optionType>");

                            rawData.append("</lstReasonCusCareProperty>");
                        }
                    }
                }

                rawData.append("</lstReasonCusCares>");
            }
        }

        if (Constant.CUS_CARE.NOI_MANG_VANG_LAI.equals(careId)
                || Constant.CUS_CARE.NGOAI_MANG_VANG_LAI.equals(careId)
                || programBO.getProgramId() >= 8) {
            if (obAdapter != null) {
                ArrayList<ObjectProperty> lstObjectProperties = obAdapter
                        .getLstData();
                if (!CommonActivity.isNullOrEmptyArray(lstObjectProperties)) {
                    for (ObjectProperty objectProperty : lstObjectProperties) {
                        if (!CommonActivity.isNullOrEmpty(objectProperty
                                .getValue())) {
                            rawData.append("<lstObjectProperty>");

                            rawData.append("<name>"
                                    + objectProperty.getName() + "</name>");
                            rawData.append("<value>"
                                    + objectProperty.getValue().trim()
                                    + "</value>");
                            rawData.append("<propertyCode>"
                                    + objectProperty.getPropertyCode()
                                    + "</propertyCode>");

                            rawData.append("</lstObjectProperty>");
                        }
                    }
                }
            }
        }

        rawData.append("<note>"
                + StringUtils.escapeHtml(edtNote.getText().toString()
                .trim()) + "</note>");

        if (Constant.CUS_CARE.NOI_MANG.equals(careId)
                || Constant.CUS_CARE.NGOAI_MANG.equals(careId)) {
            rawData.append("<staffCode>"
                    + StringUtils.escapeHtml(edtStaff.getText()
                    .toString().toUpperCase()) + "</staffCode>");
        }
        return rawData.toString();
    }

    private boolean validateGetSelectReason() {
//		if (Constant.CUS_CARE.NOI_MANG_VANG_LAI.equals(careId)
//				|| Constant.CUS_CARE.NGOAI_MANG_VANG_LAI.equals(careId)) {
//			return false;
//		}
//
//		if (Constant.CUS_CARE.NOI_MANG.equals(careId)) {
//			if ((selectedIsdn.getIsDisable() != null && selectedIsdn
//					.getIsDisable() == 1)
//					|| Constant.STATUS_HISTORY_SCHEDULE.equals(statusId)) {
//				return false;
//			} else {
//				return true;
//			}
//		}
//
//		if (Constant.CUS_CARE.NGOAI_MANG.equals(careId)
//				|| Constant.CUS_CARE.DOI_4G.equals(careId)) {
//			if (selectedIsdn.getIsDisable() != null
//					&& selectedIsdn.getIsDisable() == 1) {
//				return false;
//			}
//		}
        if (selectedIsdn.getIsDisable() != null
                && selectedIsdn.getIsDisable() == 1) {
            return false;
        }
        return true;
    }

    /**
     * Ham khoi tao spinner
     *
     * @param spinner
     */
    private void initSpinner(Spinner spinner, String parName) {
        try {
            ApParamDAL apDal = new ApParamDAL(getActivity());
            apDal.open();

            List<Spin> lstSpins = apDal.getAppParam(parName, true);
            Utils.setDataSpinner(getActivity(), lstSpins, spinner);

            apDal.close();
        } catch (Exception ex) {
            Log.d(getClass().getSimpleName(), "Error when initSpinner: " + ex);
        }

    }

    @Override
    public void setPermission() {
        permission = ";care_lost_sub;";

    }
}
