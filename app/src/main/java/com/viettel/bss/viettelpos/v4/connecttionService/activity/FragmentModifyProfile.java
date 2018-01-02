package com.viettel.bss.viettelpos.v4.connecttionService.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity.FragmentEditCustomerBCCS;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustomerDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeDialogWrapper;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.commons.Define.CHANNEL_UPDATE_IMAGE;
import com.viettel.bss.viettelpos.v4.commons.FileUtils;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.FragmentRegisterServiceVas;
import com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.UpdateModifyProfileAdapter;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.ActionProfileBean;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.RecordBean;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.ModifyProfileAdapter;

import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.customview.obj.FileObj;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.fragment.ModifyProfileFragment;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.task.AsynTaskGetCustomerByCustId;
import com.viettel.bss.viettelpos.v4.ui.image.picker.ImagePickerActivity;
import com.viettel.bss.viettelpos.v4.ui.image.picker.ImagePreviewActivity;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;
import com.viettel.savelog.SaveLog;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentModifyProfile extends Fragment implements OnClickListener {
    private View view;
    private ViewHolder viewHolder;
    private int fromYear;
    private int fromMonth;
    private int fromDay;
    private int toYear;
    private int toMonth;
    private int toDay;
    private Date dateFrom = null;
    private Date dateTo = null;
    private Date currentDate = null;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private List<ActionProfileBean> lstActionProfileBeans = new ArrayList<>();
    private ActionProfileBean actionProfileBeanCurrent = null;
    private List<RecordBean> lstRecordBeans = new ArrayList<>();
    private RecordBean recordBeanCurrent = null;

    private ModifyProfileAdapter adapter;
    private UpdateModifyProfileAdapter updateModifyProfileAdapter;

    private final HashMap<String, ArrayList<FileObj>> hashmapFileObj = new HashMap<>();
    private Activity activity;

    private AsyncTaskUpdateImageOfline uploadImageAsy = null;

    private Date timeStart = null;
    private String FMP_EDT_CUST = "FMP_EDT_CUST";

    @BindView(R.id.spinStatus)
    Spinner spinStatus;
    @BindView(R.id.lnStatus)
    LinearLayout lnStatus;
    @BindView(R.id.lnAction)
    LinearLayout lnAction;
    @BindView(R.id.spinAction)
    Spinner spinAction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        if (view == null) {
            view = inflater.inflate(R.layout.layout_modify_profile, container,
                    false);
            activity = getActivity();
            ButterKnife.bind(this, view);

            initView(view);
        }
        return view;
    }

    private void initView(View view) {
        viewHolder = new ViewHolder();

        viewHolder.edtIsdnAccount = (EditText) view
                .findViewById(R.id.edtIsdnAccount);
        viewHolder.edtIsdnAccount.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                viewHolder.lnResult.setVisibility(View.GONE);
            }
        });

        viewHolder.edtContractNo = (EditText) view
                .findViewById(R.id.edtContractNo);

        Calendar calDateFrom = Calendar.getInstance();
        dateFrom = calDateFrom.getTime();

        fromDay = calDateFrom.get(Calendar.DAY_OF_MONTH);
        fromMonth = calDateFrom.get(Calendar.MONTH);
        fromYear = calDateFrom.get(Calendar.YEAR);

        Calendar caltoDate = Calendar.getInstance();
        dateTo = caltoDate.getTime();
        toDay = caltoDate.get(Calendar.DAY_OF_MONTH);
        toMonth = caltoDate.get(Calendar.MONTH);
        toYear = caltoDate.get(Calendar.YEAR);

        String dateNowString = fromDay + "/" + (fromMonth + 1) + "/" + fromYear
                + "";

        viewHolder.edtFromDate = (EditText) view.findViewById(R.id.edtFromDate);
//        viewHolder.edtFromDate.setText(dateNowString);
//        viewHolder.edtFromDate.setOnClickListener(this);
        new DateTimeDialogWrapper(viewHolder.edtFromDate, getActivity());
        viewHolder.edtFromDate.setText(DateTimeUtils.getFirstDateOfMonth());

        viewHolder.edtToDate = (EditText) view.findViewById(R.id.edtToDate);
        viewHolder.edtToDate.setText(dateNowString);
//        viewHolder.edtToDate.setOnClickListener(this);
        new DateTimeDialogWrapper(viewHolder.edtToDate, getActivity());

        viewHolder.lvdetail = (ListView) view.findViewById(R.id.lvdetail);
        viewHolder.lvdetail.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                actionProfileBeanCurrent = lstActionProfileBeans.get(position);
                hashmapFileObj.clear();
                lstRecordBeans.clear();
//				if ("2".equals(actionProfileBeanCurrent.getTypeStatus())
//						|| "3".equals(actionProfileBeanCurrent.getTypeStatus())) {
                if (Constant.PROFILE_STATUS.HS_GIA_MAO.equals(actionProfileBeanCurrent.getTypeStatus())
                        || Constant.PROFILE_STATUS.HS_SCAN_SAI.equals(actionProfileBeanCurrent.getTypeStatus())
                        || Constant.PROFILE_STATUS.HS_SCAN_TIEPNHAN_CHUAKT.equals(actionProfileBeanCurrent.getTypeStatus())) {
                    if (viewHolder.rbModifyProfile.isChecked()) {
                        new GetDetailProfileAsyn(getActivity()).execute();
                    } else if (!Constant.PROFILE_STATUS.HS_SCAN_TIEPNHAN_CHUAKT.equals(actionProfileBeanCurrent.getTypeStatus())) {
                        new GetDetailProfileAsyn(getActivity()).execute();
                    }
                } else if (Constant.PROFILE_STATUS.HS_SAI_THONG_TIN.equals(actionProfileBeanCurrent.getTypeStatus())) {
                    if (viewHolder.rbModifyProfile.isChecked()) {
                        if(actionProfileBeanCurrent.getCustId() != null && actionProfileBeanCurrent.getCustId() > 0) {
                            new AsynTaskGetCustomerByCustId(getActivity(), new OnPostExecuteListener<CustomerDTO>() {
                                @Override
                                public void onPostExecute(CustomerDTO result, String errorCode, String description) {
                                    Bundle mBundle = new Bundle();
                                    mBundle.putSerializable("customerDTO", result);
                                    mBundle.putString("type", FMP_EDT_CUST);
                                    mBundle.putString("isPSenTdO", result.getIsPSenTdO());

                                    FragmentEditCustomerBCCS mListMenuManager = new FragmentEditCustomerBCCS();
                                    mListMenuManager.setArguments(mBundle);
                                    mListMenuManager.setTargetFragment(FragmentModifyProfile.this, 100);
                                    ReplaceFragment.replaceFragment(getActivity(), mListMenuManager,
                                            false);
                                }
                            }, moveLogInAct, String.valueOf(actionProfileBeanCurrent.getCustId()), "").execute();
                        } else {
                            CommonActivity.toast(getActivity(), R.string.customer_info_profile_not_found);
                        }
                    }
                } else {
                    CommonActivity.toast(getActivity(), getString(R.string.profile_not_allow_modify, actionProfileBeanCurrent.getDescTypeStatus()));
                }
//				} else {
//					Dialog dialog = CommonActivity.createAlertDialog(
//							getActivity(),
//							getResources().getString(
//									R.string.error_profile_saithongtin),
//							getResources().getString(R.string.app_name));
//					dialog.show();
//				}
            }
        });

        viewHolder.btnSearch = (Button) view.findViewById(R.id.btnSearch);
        viewHolder.btnSearch.setOnClickListener(this);

        viewHolder.lnResult = (LinearLayout) view.findViewById(R.id.lnResult);
        viewHolder.rbModifyProfile = (RadioButton) view.findViewById(R.id.rbModifyProfile);
        viewHolder.rbModifyProfile.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                viewHolder.lnResult.setVisibility(View.GONE);
                lnStatus.setVisibility(View.VISIBLE);
                lnAction.setVisibility(View.VISIBLE);
            }
        });

        viewHolder.rbVerifyProfile = (RadioButton) view.findViewById(R.id.rbVerifyProfile);
        viewHolder.rbVerifyProfile.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                viewHolder.lnResult.setVisibility(View.GONE);
                lnStatus.setVisibility(View.GONE);
                lnAction.setVisibility(View.GONE);
            }
        });

        ArrayAdapter<String> spnArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.modify_profile_status));
        spnArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinStatus.setAdapter(spnArrayAdapter);

        ArrayAdapter<String> spnArrayActionAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.modify_profile_action_code));
        spnArrayActionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinAction.setAdapter(spnArrayActionAdapter);


    }

    class ViewHolder {
        EditText edtIsdnAccount;
        EditText edtContractNo;
        EditText edtFromDate;
        EditText edtToDate;

        ListView lvdetail;
        ListView lvDSChungTuSai;
        Button btnSearch;
        LinearLayout lnResult;
        RadioButton rbModifyProfile;
        RadioButton rbVerifyProfile;
    }

    private final DatePickerDialog.OnDateSetListener fromDatePickerListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            fromYear = selectedYear;
            fromMonth = selectedMonth;
            fromDay = selectedDay;
            StringBuilder strDate = new StringBuilder();
            if (fromDay < 10) {
                strDate.append("0");
            }
            strDate.append(fromDay).append("/");
            if (fromMonth < 9) {
                strDate.append("0");
            }
            strDate.append(fromMonth + 1).append("/");
            strDate.append(fromYear);
            String fromDate = strDate.toString();

            String dateFromString = String.valueOf(fromYear) + "-" +
                    (fromMonth + 1) + "-" + fromDay;
            try {
                dateFrom = sdf.parse(dateFromString);
            } catch (Exception e) {
                e.printStackTrace();
            }
            viewHolder.edtFromDate.setText(strDate);
        }
    };

    private final DatePickerDialog.OnDateSetListener toDatePickerListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            toYear = selectedYear;
            toMonth = selectedMonth;
            toDay = selectedDay;
            StringBuilder strDate = new StringBuilder();
            if (toDay < 10) {
                strDate.append("0");
            }
            strDate.append(toDay).append("/");
            if (toMonth < 9) {
                strDate.append("0");
            }
            strDate.append(toMonth + 1).append("/");
            strDate.append(toYear);
            String toDate = strDate.toString();

            String dateToString = String.valueOf(toYear) + "-" +
                    (toMonth + 1) + "-" + toDay;
            try {
                dateTo = sdf.parse(dateToString);
            } catch (Exception e) {
                e.printStackTrace();
            }
            viewHolder.edtToDate.setText(strDate);
        }
    };

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.edtFromDate:
                DatePickerDialog fromDateDialog = new FixedHoloDatePickerDialog(
                        getActivity(), AlertDialog.THEME_HOLO_LIGHT, fromDatePickerListener, fromYear, fromMonth,
                        fromDay);
                fromDateDialog.show();

                break;
            case R.id.edtToDate:
                DatePickerDialog toDateDialog = new FixedHoloDatePickerDialog(getActivity(),
                        AlertDialog.THEME_HOLO_LIGHT, toDatePickerListener, toYear, toMonth, toDay);
                toDateDialog.show();
                break;
            case R.id.btnSearch:

                if (CommonActivity.isNetworkConnected(getActivity())) {
                    if (validateGetLstProfile()) {
                        new GetLstProfileAsyn(getActivity()).execute();
                    }
                } else {
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
                            getResources().getString(R.string.errorNetwork),
                            getResources().getString(R.string.app_name));
                    dialog.show();
                }
                break;
            case R.id.btnUpdate:
                if (CommonActivity.isNetworkConnected(getActivity())) {
                    if (validateUpdateModifyProfile()) {
                        Dialog confirmDialog = CommonActivity.createDialog(
                                getActivity(),
                                viewHolder.rbModifyProfile.isChecked() ?
                                        getResources().getString(
                                                R.string.modify_profile_confirm) :
                                        getResources().getString(
                                                R.string.verify_profile_confirm),
                                getResources().getString(R.string.app_name_title),
                                getResources().getString(R.string.ok),
                                getResources().getString(R.string.cancel),
                                modifyProfileClick, null);
                        confirmDialog.show();
                    }
                } else {
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
                            getResources().getString(R.string.errorNetwork),
                            getResources().getString(R.string.app_name));
                    dialog.show();
                }
                break;
            case R.id.relaBackHome:
                getActivity().onBackPressed();
                break;
            case R.id.btnEdtCust:
                new AsynTaskGetCustomerByCustId(getActivity(), new OnPostExecuteListener<CustomerDTO>() {
                    @Override
                    public void onPostExecute(CustomerDTO result, String errorCode, String description) {
                        Bundle mBundle = new Bundle();
                        mBundle.putSerializable("customerDTO", result);
//					mBundle.putString("subType", subscriberDTO.getPayType());
//					mBundle.putString("isPSenTdO", isPSenTdO);
//					mBundle.putString("functionKey", functionName);
//					mBundle.putSerializable("subscriberDTO", subscriberDTO);

                        FragmentEditCustomerBCCS mListMenuManager = new FragmentEditCustomerBCCS();
                        mListMenuManager.setArguments(mBundle);
                        mListMenuManager.setTargetFragment(FragmentModifyProfile.this, 100);
                        ReplaceFragment.replaceFragment(getActivity(), mListMenuManager,
                                false);

                        dialog.dismiss();
                    }
                }, moveLogInAct, String.valueOf(actionProfileBeanCurrent.getCustId()), "").execute();
                break;
            default:
                break;
        }
    }

    private class GetLstProfileAsyn extends AsyncTask<String, Void, ParseOuput> {

        final ProgressDialog progress;
        private Context context = null;
        String errorCode = "";
        String description = "";

        public GetLstProfileAsyn(Context context) {
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
        protected ParseOuput doInBackground(String... arg0) {
            return getLstProfile();
        }

        @Override
        protected void onPostExecute(final ParseOuput result) {

            progress.dismiss();

            if ("0".equals(errorCode)) {
                if (result != null) {
                    if (result.getLstActionProfileBeans() != null
                            && result.getLstActionProfileBeans().size() > 0) {
                        viewHolder.lnResult.setVisibility(View.VISIBLE);

                        lstActionProfileBeans = result
                                .getLstActionProfileBeans();

                        adapter = new ModifyProfileAdapter(getActivity(),
                                lstActionProfileBeans);
                        viewHolder.lvdetail.setAdapter(adapter);
                    } else {
                        viewHolder.lnResult.setVisibility(View.GONE);

                        Dialog dialog = CommonActivity.createAlertDialog(
                                getActivity(),
                                getResources().getString(R.string.no_data),
                                getResources().getString(R.string.app_name));
                        dialog.show();
                    }

                } else {
                    viewHolder.lnResult.setVisibility(View.GONE);

                    if (description != null && !description.isEmpty()) {
                        Dialog dialog = CommonActivity.createAlertDialog(
                                getActivity(), description, getResources()
                                        .getString(R.string.app_name));
                        dialog.show();
                    } else {
                        Dialog dialog = CommonActivity.createAlertDialog(
                                getActivity(),
                                getResources().getString(R.string.no_data),
                                getResources().getString(R.string.app_name));
                        dialog.show();
                    }

                }
            } else {
                viewHolder.lnResult.setVisibility(View.GONE);

                if (Constant.INVALID_TOKEN2.equals(errorCode)
                        && description != null && !description.isEmpty()) {

                    Dialog dialog = CommonActivity
                            .createAlertDialog(
                                    (Activity) context,
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

        private ParseOuput getLstProfile() {
            String original = null;
            try {
                BCCSGateway input = new BCCSGateway();

                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode",
                        "mbccs_getIncorectProfileFormBCCS");

                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getIncorectProfileFormBCCS>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<>();

                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));

                if (StringUtils.isViettelMobile(viewHolder.edtIsdnAccount.getText().toString().trim())) {
                    rawData.append("<isdnAccount>"
                            + StringUtils.formatIsdn(viewHolder.edtIsdnAccount.getText().toString().trim()));
                    rawData.append("</isdnAccount>");
                } else {
                    rawData.append("<isdnAccount>"
                            + viewHolder.edtIsdnAccount.getText().toString().trim());
                    rawData.append("</isdnAccount>");
                }

                rawData.append("<fromDate>").append(viewHolder.edtFromDate.getText().toString().trim());
                rawData.append("</fromDate>");

                rawData.append("<toDate>").append(viewHolder.edtToDate.getText().toString().trim());
                rawData.append("</toDate>");

                rawData.append("<contractNo>").append(viewHolder.edtContractNo.getText().toString().trim());
                rawData.append("</contractNo>");

                if (viewHolder.rbModifyProfile.isChecked()) {
                    rawData.append("<status>").append(getLstStatus(spinStatus.getSelectedItemPosition()));
                    rawData.append("</status>");

                    rawData.append("<actionCode>").append(getActionCode(spinAction.getSelectedItemPosition())).append("</actionCode>");

                    rawData.append("<typeProfile>")
                            .append("ACTION_PROFILE")
                            .append("</typeProfile>");
                } else {
                    rawData.append("<typeProfile>")
                            .append("ACTION_VERIFICATION")
                            .append("</typeProfile>");
                }

                rawData.append("</input>");
                rawData.append("</ws:getIncorectProfileFormBCCS>");
                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope,
                        Constant.BCCS_GW_URL, getActivity(),
                        "mbccs_getIncorectProfileFormBCCS");
                Log.i("Responseeeeeeeeee", response);

                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("Responseeeeeeeeee", original);

                Serializer serializer = new Persister();
                ParseOuput parseOuput = serializer.read(ParseOuput.class,
                        original);
                if (parseOuput != null) {
                    errorCode = parseOuput.getErrorCode();
                    description = parseOuput.getDescription();
                }

                return parseOuput;
            } catch (Exception e) {
                Log.i("getIncorectProfileFormBCCS", e.toString());
            }
            return null;
        }

        private String getLstStatus(Integer position) {
            StringBuilder lstStatus = new StringBuilder("");
            if (position == 0) {
                lstStatus.append("1;").append("2;").append("3;").append("11;").append("7;").append("8;").append("9");
            } else if (position == 1) {
                lstStatus.append("7;").append("8;").append("9");
            } else if (position == 2) {
                lstStatus.append("1;").append("2;").append("3;").append("11");
            }
            return lstStatus.toString();
        }

        private String getActionCode(Integer position){
            if(position == 0){
                return "";
            } else if (position == 1){
                return "00";
            } else if (position == 2){
                return "04";
            } else if (position == 3){
                return "220";
            } else if (position == 4){
                return "221";
            } else {
                return "";
            }
        }

    }

    private class GetDetailProfileAsyn extends
            AsyncTask<String, Void, ParseOuput> {

        final ProgressDialog progress;
        private Context context = null;
        String errorCode = "";
        String description = "";

        public GetDetailProfileAsyn(Context context) {
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
        protected ParseOuput doInBackground(String... arg0) {
            return getDetailProfile();
        }

        @Override
        protected void onPostExecute(final ParseOuput result) {

            progress.dismiss();

            if ("0".equals(errorCode)) {
                if (result != null) {
                    viewHolder.lnResult.setVisibility(View.VISIBLE);

                    if (result.getLstRecordBeans() != null
                            && result.getLstRecordBeans().size() > 0) {
                        lstRecordBeans = result.getLstRecordBeans();

                        if (viewHolder.rbVerifyProfile.isChecked()) {
                            showDialogDetail();
                        } else {
                            Bundle bundle = new Bundle();
                            actionProfileBeanCurrent.setIsModify(viewHolder.rbModifyProfile.isChecked());
                            bundle.putSerializable("actionProfileBean", actionProfileBeanCurrent);
                            bundle.putSerializable("lstRecordBeans", (Serializable) lstRecordBeans);

                            ModifyProfileFragment fragment = new ModifyProfileFragment();
                            fragment.setArguments(bundle);
                            ReplaceFragment.replaceFragment(getActivity(), fragment, false);
                        }
                    } else {
                        Dialog dialog = CommonActivity.createAlertDialog(
                                getActivity(),
                                getResources().getString(R.string.no_data),
                                getResources().getString(R.string.app_name));
                        dialog.show();
                    }

                } else {
                    if (description != null && !description.isEmpty()) {
                        Dialog dialog = CommonActivity.createAlertDialog(
                                getActivity(), description, getResources()
                                        .getString(R.string.app_name));
                        dialog.show();
                    } else {
                        Dialog dialog = CommonActivity.createAlertDialog(
                                getActivity(),
                                getResources().getString(R.string.no_data),
                                getResources().getString(R.string.app_name));
                        dialog.show();
                    }

                }
            } else {
                if (Constant.INVALID_TOKEN2.equals(errorCode)
                        && description != null && !description.isEmpty()) {

                    Dialog dialog = CommonActivity
                            .createAlertDialog(
                                    (Activity) context,
                                    description,
                                    context.getResources().getString(
                                            R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = context.getString(R.string.no_data);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(
                            getActivity(), description, getResources()
                                    .getString(R.string.app_name));
                    dialog.show();

                }
            }
        }

        private ParseOuput getDetailProfile() {
            String original = null;
            try {
                BCCSGateway input = new BCCSGateway();

                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode",
                        "mbccs_getIncorectRecordByActionProfileId");

                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getIncorectRecordByActionProfileId>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<>();

                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));

                rawData.append("<actionProfileId>").append(actionProfileBeanCurrent.getActionProfileId());
                rawData.append("</actionProfileId>");

                rawData.append("<typeStatus>").append(actionProfileBeanCurrent.getTypeStatus());
                rawData.append("</typeStatus>");

                if (viewHolder.rbModifyProfile.isChecked()) {
                    rawData.append("<typeProfile>")
                            .append("ACTION_PROFILE")
                            .append("</typeProfile>");
                } else {
                    rawData.append("<typeProfile>")
                            .append("ACTION_VERIFICATION")
                            .append("</typeProfile>");
                }


                rawData.append("</input>");
                rawData.append("</ws:getIncorectRecordByActionProfileId>");
                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope,
                        Constant.BCCS_GW_URL, getActivity(),
                        "mbccs_getIncorectRecordByActionProfileId");
                Log.i("Responseeeeeeeeee", response);

                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("Responseeeeeeeeee", original);

                Serializer serializer = new Persister();
                ParseOuput parseOuput = serializer.read(ParseOuput.class,
                        original);
                if (parseOuput != null) {
                    errorCode = parseOuput.getErrorCode();
                    description = parseOuput.getDescription();
                }

                return parseOuput;
            } catch (Exception e) {
                Log.i("getIncorectRecordByActionProfileId", e.toString());
            }
            return null;
        }

    }

    private Dialog dialog;

    private void showDialogDetail() {
        dialog = new Dialog(getActivity());
//        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_update_modfiy_profile);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        // This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        viewHolder.lvDSChungTuSai = (ListView) dialog
                .findViewById(R.id.lvdetail);
        updateModifyProfileAdapter = new UpdateModifyProfileAdapter(
                getActivity(), lstRecordBeans);

        viewHolder.lvDSChungTuSai.setAdapter(updateModifyProfileAdapter);
        setOnClickDowload();
        setOnClickSelectImage();

        if (viewHolder.rbModifyProfile.isChecked()) {
            ((TextView) dialog.findViewById(R.id.tvTitle)).setText(getString(R.string.txt_dschungtusai));
        } else {
            ((TextView) dialog.findViewById(R.id.tvTitle)).setText(getString(R.string.txt_dssuahosophieuxacminh));
        }

        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        Button btnUpdate = (Button) dialog.findViewById(R.id.btnUpdate);
        if (viewHolder.rbModifyProfile.isChecked()) {
            btnUpdate.setText(getResources().getString(R.string.txt_suasaihs));
        } else {
            btnUpdate.setText(getResources().getString(R.string.txt_suahsxacminh));
        }
        btnUpdate.setOnClickListener(this);

//        Button btnEdtCust = (Button) dialog.findViewById(R.id.btnEdtCust);
//        if (!"8".equals(actionProfileBeanCurrent.getTypeStatus())) {
//            btnEdtCust.setVisibility(View.VISIBLE);
//            btnEdtCust.setOnClickListener(this);
//        } else {
//            btnEdtCust.setVisibility(View.GONE);
//        }

        dialog.show();
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

    private class AsyntaskDowloadFile extends AsyncTask<String, Void, String> {
        final ProgressDialog progress;
        private Context context = null;
        final XmlDomParse parse = new XmlDomParse();
        String errorCode = "";
        String description = "";
        String fileName = "";

        public AsyntaskDowloadFile(Context context) {
            this.context = context;
            this.progress = new ProgressDialog(this.context);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(
                    R.string.waitting));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return dowloadInfo();
        }

        @Override
        protected void onPostExecute(String result) {
            progress.dismiss();
            if (errorCode.equalsIgnoreCase("0")) {
                try {
                    if (!CommonActivity.isNullOrEmpty(result)) {
                        byte[] fileByte = Base64.decode(result, Base64.DEFAULT);
                        recordBeanCurrent.setFileName(fileName);

                        File file = new File(
                                Environment.getExternalStorageDirectory(),
                                recordBeanCurrent.getFileName());
                        FileOutputStream fos = new FileOutputStream(
                                file.getPath());
                        fos.write(fileByte);
                        fos.flush();
                        fos.close();
                        String filePath = file.getPath();
                        recordBeanCurrent.setFilePath(filePath);

                        openFile(filePath);
                    } else {
                        Dialog dialog = CommonActivity
                                .createAlertDialog(
                                        getActivity(),
                                        context.getString(R.string.error_download_file_from_profile),
                                        getResources().getString(
                                                R.string.app_name));
                        dialog.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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

        private String dowloadInfo() {
            String strResult = "";
            String original = "";
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getSoftLink");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getSoftLink>");
                rawData.append("<input>");
                rawData.append("<token>").append(Session.getToken()).append("</token>");
                rawData.append("<strHardLink>").append(recordBeanCurrent.getRecordPath()).append(recordBeanCurrent.getRecordNameScan()).append("</strHardLink>");
                rawData.append("<strSymbolicLink>").append(recordBeanCurrent.getRecordNameScan()).append("</strSymbolicLink>");
                rawData.append("</input>");
                rawData.append("</ws:getSoftLink>");

                Log.i("LOG", "raw data" + rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("LOG", "Send evelop" + envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input
                        .sendRequest(envelope, Constant.BCCS_GW_URL,
                                getActivity(), "mbccs_getSoftLink");
                Log.i("LOG", "Respone:  " + response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("LOG", "Responseeeeeeeeee Original  " + response);

                // parser

                strResult = parserDowloadFile(original);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return strResult;
        }

        public String parserDowloadFile(String original) {
            String strFile = "";
            Document doc = parse.getDomElement(original);
            NodeList nl = doc.getElementsByTagName("return");
            for (int i = 0; i < nl.getLength(); i++) {
                Element e2 = (Element) nl.item(i);
                errorCode = parse.getValue(e2, "errorCode");
                description = parse.getValue(e2, "description");
                Log.d("LOG", "errorCode:  " + errorCode);
                Log.d("LOG", "description: " + description);
                strFile = parse.getValue(e2, "fileContent");
                Log.d("LOG", "fileContent: " + strFile);
                fileName = parse.getValue(e2, "fileName");
            }

            return strFile;
        }

    }

    // open file_start
    private void openFile(String fileName) {
        File file = new File(fileName);
        Log.d("LOG", "Link file: " + fileName);
        Intent intent = new Intent(Intent.ACTION_VIEW);

        String extension = android.webkit.MimeTypeMap
                .getFileExtensionFromUrl(Uri.fromFile(file).toString());
        String mimetype = android.webkit.MimeTypeMap.getSingleton()
                .getMimeTypeFromExtension(extension);
        if (extension.equalsIgnoreCase("") || mimetype == null) {
        } else {
            intent.setDataAndType(Uri.fromFile(file), mimetype);
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        Intent intent2 = Intent.createChooser(intent, "Open File");
        try {
            startActivity(intent2);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setOnClickDowload() {
        if (updateModifyProfileAdapter != null) {
            updateModifyProfileAdapter
                    .setOnClickDownload(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            switch (v.getId()) {
                                case R.id.tvDownloadImage:
                                    int pos = (Integer) v.getTag();
                                    recordBeanCurrent = lstRecordBeans.get(pos);
                                    if (CommonActivity
                                            .isNullOrEmpty(recordBeanCurrent
                                                    .getFilePath())) {
                                        AsyntaskDowloadFile dowloadFile = new AsyntaskDowloadFile(
                                                getActivity());
                                        dowloadFile.execute();
                                    } else {
                                        openFile(recordBeanCurrent.getFilePath());
                                    }
                                    break;

                                default:
                                    break;
                            }

                        }
                    });
        }
    }

    private void setOnClickSelectImage() {
        if (updateModifyProfileAdapter != null) {
            updateModifyProfileAdapter.setOnClickSelectImage(imageListenner);
        }
    }

    private final OnClickListener imageListenner = new OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d(Constant.TAG, "view.getId() : " + view.getId());
            ImagePreviewActivity.pickImage(activity, hashmapFileObj,
                    view.getId());
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG 9", "FragmentModifyProfile onActivityResult requestCode : "
                + requestCode);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CHANNEL_UPDATE_IMAGE.PICK_IMAGE:
                    Log.d(Constant.TAG, "CHANNEL_UPDATE_IMAGE.PICK_IMAGE");

                    Parcelable[] parcelableUris = data
                            .getParcelableArrayExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);

                    if (parcelableUris == null) {
                        return;
                    }
                    // Java doesn't allow array casting, this is a little hack
                    Uri[] uris = new Uri[parcelableUris.length];
                    System.arraycopy(parcelableUris, 0, uris, 0,
                            parcelableUris.length);

                    int imageId = data.getExtras().getInt("imageId", -1);

                    Log.d(Constant.TAG,
                            "FragmentModifyProfile onActivityResult() imageId: "
                                    + imageId);

                    if (uris != null && uris.length > 0 && imageId >= 0) {
                        com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.UpdateModifyProfileAdapter.ViewHolder holder = null;
                        for (int i = 0; i < viewHolder.lvDSChungTuSai
                                .getChildCount(); i++) {
                            View rowView = viewHolder.lvDSChungTuSai.getChildAt(i);
                            com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.UpdateModifyProfileAdapter.ViewHolder h = (com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.UpdateModifyProfileAdapter.ViewHolder) rowView
                                    .getTag();
                            if (h != null) {
                                int id = h.ibUploadImage.getId();
                                if (imageId == id) {
                                    holder = h;
                                    break;
                                }
                            }
                        }

                        if (holder != null) {
                            Picasso.with(activity)
                                    .load(new File(uris[0].toString()))
                                    .centerCrop().resize(100, 100)
                                    .into(holder.ibUploadImage);

                            RecordBean recordBean = getRecordBean((long) imageId);

                            String recordCode = recordBean.getRecordCode();

                            Log.i(Constant.TAG, "imageId: " + imageId
                                    + " spinnerCode: " + recordCode + " uris: "
                                    + uris.length);

                            ArrayList<FileObj> fileObjs = new ArrayList<>();
                            for (int i = 0; i < uris.length; i++) {
                                File uriFile = new File(uris[i].getPath());
                                String fileNameServer = recordCode + "-" + (i + 1)
                                        + ".jpg";
                                FileObj obj = new FileObj(recordCode, uriFile,
                                        uris[i].getPath(), fileNameServer);
                                obj.setRecordId(recordBean.getRecordId());
                                fileObjs.add(obj);
                            }
                            hashmapFileObj.put(String.valueOf(imageId), fileObjs);
                        } else {
                            Log.d(Constant.TAG,
                                    "FragmentModifyProfile onActivityResult() holder NULL");
                        }
                    }
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * getRecordBean
     *
     * @param recordId
     * @return
     */
    private RecordBean getRecordBean(Long recordId) {
        for (RecordBean recordBean : lstRecordBeans) {
            if (recordBean.getRecordId().equals(recordId)) {
                return recordBean;
            }
        }
        return null;
    }

    private class ModifyProfileAsyn extends
            AsyncTask<String, Void, ArrayList<FileObj>> {

        private final HashMap<String, ArrayList<FileObj>> mHashMapFileObj;
        private final Context mContext;
        private ProgressDialog progress;
        private String errorCode = "";
        private String description = "";
        private final ArrayList<String> lstFilePath = new ArrayList<>();
        final Date timeStartZipFile = new Date();

        public ModifyProfileAsyn(Context context,
                                 HashMap<String, ArrayList<FileObj>> hasMapFile) {
            this.mContext = context;
            this.mHashMapFileObj = hasMapFile;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.progress = new ProgressDialog(mContext);
            // check font
            this.progress.setMessage(mContext.getResources().getString(
                    R.string.processModifyProfile));
            this.progress.setCancelable(false);
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected ArrayList<FileObj> doInBackground(String... arg0) {
            ArrayList<FileObj> arrFileBackUp1 = null;
            try {
                if (mHashMapFileObj != null && mHashMapFileObj.size() > 0) {
                    File folder = new File(
                            Constant.MBCCS_TEMP_FOLDER_IMG_PROFILE);
                    if (!folder.exists()) {
                        folder.mkdir();
                    }
                    Log.d("Log",
                            "Folder zip file create: "
                                    + folder.getAbsolutePath());
                    for (java.util.Map.Entry<String, ArrayList<FileObj>> entry : hashmapFileObj
                            .entrySet()) {
                        ArrayList<FileObj> listFileObjs = entry.getValue();
                        String zipFilePath = "";
                        if (listFileObjs.size() > 1) {
                            String spinnerCode = "";
                            for (FileObj fileObj : listFileObjs) {
                                spinnerCode = fileObj.getCodeSpinner();
                            }
                            zipFilePath = folder.getPath()
                                    + File.separator
                                    + "HS"
                                    + actionProfileBeanCurrent
                                    .getActionProfileId() + "_"
                                    + actionProfileBeanCurrent.getIsdnAccount()
                                    + "_" + spinnerCode + ".zip";
                            lstFilePath.add(zipFilePath);
                        } else if (listFileObjs.size() == 1) {
                            zipFilePath = folder.getPath()
                                    + File.separator
                                    + "HS"
                                    + actionProfileBeanCurrent
                                    .getActionProfileId() + "_"
                                    + actionProfileBeanCurrent.getIsdnAccount()
                                    + "_"
                                    + listFileObjs.get(0).getCodeSpinner()
                                    + ".jpg";
                            lstFilePath.add(zipFilePath);
                        }
                    }
                }

                arrFileBackUp1 = FileUtils.getArrFileBackUp2(mContext,
                        mHashMapFileObj, lstFilePath);
                errorCode = "0";
                return arrFileBackUp1;
            } catch (Exception e) {
                errorCode = "1";
                description = "Error when zip file: " + e.toString();
                return arrFileBackUp1;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<FileObj> result) {
            progress.dismiss();
            if ("0".equals(errorCode)) {
                try {
                    SaveLog saveLog = new SaveLog(mContext,
                            Constant.SYSTEM_NAME, Session.userName,
                            "mbccs_connectSub_zipFile", CommonActivity
                            .findMyLocation(mContext).getX(),
                            CommonActivity.findMyLocation(mContext).getY());

                    Date timeEnd = new Date();

                    saveLog.saveLogRequest(
                            errorCode,
                            timeStartZipFile,
                            timeEnd,
                            Session.userName + "_"
                                    + CommonActivity.getDeviceId(mContext)
                                    + "_" + System.currentTimeMillis());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (result != null && !result.isEmpty()) {
                    for (FileObj fileObj2 : result) {
                        fileObj2.setActionCode("MPHS");
                        fileObj2.setActionProfileId(String
                                .valueOf(actionProfileBeanCurrent
                                        .getActionProfileId()));
                        // fileObj2.setReasonId(infoConnectionBeans
                        // .getRegReasonId());
                        fileObj2.setIsdn(actionProfileBeanCurrent
                                .getIsdnAccount());
                        // fileObj2.setActionAudit(actionAuditId);
                        fileObj2.setPageSize(0 + "");
                    }

                    uploadImageAsy = new AsyncTaskUpdateImageOfline(mContext,
                            result, reloadClick,
                            getString(R.string.modifyProfileSuccess) + "\n",
                            null, "10");
                    uploadImageAsy.execute();
                }
            } else {
                CommonActivity.createAlertDialog(getActivity(), description,
                        getActivity().getString(R.string.app_name)).show();
            }

        }
    }

    private final OnClickListener reloadClick = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            if (dialog != null) {
                dialog.dismiss();
            }

            if (hashmapFileObj.size() != uploadImageAsy.getNumberSuccess()) {
                new GetLstProfileAsyn(getActivity()).execute();
            } else {
                lstActionProfileBeans.remove(actionProfileBeanCurrent);
                adapter.notifyDataSetChanged();
            }

            lstRecordBeans.clear();
            hashmapFileObj.clear();
        }
    };

    private final OnClickListener modifyProfileClick = new OnClickListener() {
        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            new ModifyProfileAsyn(getActivity(), hashmapFileObj).execute();
        }
    };

    private boolean validateGetLstProfile() {
        if (CommonActivity.isNullOrEmpty(viewHolder.edtFromDate.getText()
                .toString())) {
            CommonActivity.createAlertDialog(activity,
                    getString(R.string.notstartdate),
                    getString(R.string.app_name)).show();
            return false;
        }

        if (CommonActivity.isNullOrEmpty(viewHolder.edtToDate.getText()
                .toString())) {
            CommonActivity
                    .createAlertDialog(activity, getString(R.string.notendate),
                            getString(R.string.app_name)).show();
            return false;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date from = sdf.parse(viewHolder.edtFromDate.getText().toString());
            Date to = sdf.parse(viewHolder.edtToDate.getText().toString());
            if (from.after(to)) {
                CommonActivity.createAlertDialog(activity,
                        getString(R.string.checktimeupdatejob),
                        getString(R.string.app_name)).show();
                return false;
            }

            long diff = to.getTime() - from.getTime();
            long dayDiff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            if (dayDiff > 30) {
                CommonActivity.createAlertDialog(activity,
                        getString(R.string.modify_profile_search_err01),
                        getString(R.string.app_name)).show();
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean validateUpdateModifyProfile() {
        if (lstRecordBeans.size() != hashmapFileObj.size()) {
            CommonActivity.createAlertDialog(activity,
                    getString(R.string.modify_profile_thieu_chung_tu),
                    getString(R.string.app_name)).show();
            return false;
        }
        return true;
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        MainActivity.getInstance().setTitleActionBar(R.string.txt_suasaihs);
    }

    private class AsyncTaskUpdateImageOfline extends
            AsyncTask<Void, Void, Integer> {

        private final Context mActivity;
        private final ArrayList<FileObj> listFileUploadImage;
        private final OnClickListener onClick;
        final XmlDomParse parse = new XmlDomParse();
        String errorCode = "";
        String description = "";
        final ProgressDialog progress;
        final String isdn;
        final String dbType;
        Integer numberSuccess;
        final String noticeSucess;

        public AsyncTaskUpdateImageOfline(Context mActivity,
                                          ArrayList<FileObj> listFileUploadImage,
                                          OnClickListener onClick, String description, String mIsdn,
                                          String dbType) {
            this.mActivity = mActivity;
            this.listFileUploadImage = listFileUploadImage;
            this.progress = new ProgressDialog(mActivity);
            this.progress.setCancelable(false);
            if (!"10".equals(dbType)) {
                this.progress.setMessage(mActivity.getResources().getString(
                        R.string.progress_uploadImageOffline));
            } else {
                this.progress.setMessage(mActivity.getResources().getString(
                        R.string.progress_uploadChungTu));
            }
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
            this.description = description;
            this.noticeSucess = description;
            this.onClick = onClick;
            this.isdn = mIsdn;
            this.dbType = dbType;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            return onProcessDataToUpload(listFileUploadImage);
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            progress.dismiss();

            setNumberSuccess(result);

            if (result == listFileUploadImage.size()) {

                if (!CommonActivity.isNullOrEmpty(noticeSucess)) {
                    description = noticeSucess;
                }
                Log.d("descriptionnnnnnnnnnnnnnnnnnnnnn", description);

                if (listFileUploadImage != null
                        && listFileUploadImage.size() > 0) {
                    for (FileObj fileObj : listFileUploadImage) {
                        File tmp = new File(fileObj.getPath());
                        tmp.delete();
                    }
                }

                if (!CommonActivity.isNullOrEmpty(isdn) && "2".equals(dbType)) {
                    CommonActivity
                            .createDialog(
                                    (Activity) mActivity,
                                    description
                                            + mActivity
                                            .getString(R.string.upload_all_profile_success)
                                            + "\n"
                                            + mActivity
                                            .getString(R.string.messagetichvas),
                                    mActivity.getString(R.string.app_name),
                                    mActivity.getString(R.string.OK),
                                    mActivity.getString(R.string.cancel),
                                    onclickIsdn, onClick).show();
                } else if ("10".equals(dbType)) {
                    CommonActivity.createAlertDialog((Activity) mActivity,
                            viewHolder.rbModifyProfile.isChecked() ? mActivity.getString(R.string.modifyProfileSuccess)
                                    : mActivity.getString(R.string.verifyProfileSuccess),
                            mActivity.getString(R.string.app_name), onClick)
                            .show();
                } else {
                    CommonActivity
                            .createAlertDialog(
                                    (Activity) mActivity,
                                    description
                                            + mActivity
                                            .getString(R.string.upload_all_profile_success),
                                    mActivity.getString(R.string.app_name),
                                    onClick).show();
                }

            } else {

                // if (listFileUploadImage != null && listFileUploadImage.size()
                // >
                // 0) {
                // for (FileObj fileObj : listFileUploadImage) {
                // File tmp = new File(fileObj.getPath());
                // tmp.delete();
                // }
                // }
                if (!CommonActivity.isNullOrEmpty(noticeSucess)) {
                    description = noticeSucess;
                }
                if (!CommonActivity.isNullOrEmpty(isdn) && "2".equals(dbType)) {
                    CommonActivity
                            .createDialog(
                                    (Activity) mActivity,
                                    description
                                            + mActivity
                                            .getString(R.string.upload_all_profile_success)
                                            + "\n"
                                            + mActivity
                                            .getString(R.string.messagetichvas),
                                    mActivity.getString(R.string.app_name),
                                    mActivity.getString(R.string.OK),
                                    mActivity.getString(R.string.cancel),
                                    onclickIsdn, onClick).show();
                } else if ("10".equals(dbType)) {

                    CommonActivity.createAlertDialog(
                            (Activity) mActivity,
                            viewHolder.rbModifyProfile.isChecked() ?
                                    mActivity.getString(
                                            R.string.modify_profile_fail_try_again,
                                            result, listFileUploadImage.size())
                                    : mActivity.getString(
                                    R.string.verify_profile_fail_try_again,
                                    result, listFileUploadImage.size()),
                            mActivity.getString(R.string.app_name), onClick)
                            .show();
                } else {
                    CommonActivity
                            .createAlertDialog(
                                    (Activity) mActivity,
                                    description
                                            + mActivity
                                            .getString(R.string.upload_profile_fail_try_again),
                                    mActivity.getString(R.string.app_name),
                                    onClick).show();
                }

            }
        }

        private int onProcessDataToUpload(ArrayList<FileObj> listFileUploadImage) {
            String messageUpload = "";
            int numberSuccess = 0;

            for (FileObj fileObj2 : listFileUploadImage) {
                try {
                    File isdnZip = new File(fileObj2.getPath());
                    byte[] buffer = FileUtils.fileToBytes(isdnZip);
                    if (buffer != null && buffer.length > 0) {
                        Log.d(Constant.TAG,
                                "FragmentCustomerUpdateCV onProcessDataToUpload service running fileToBytes"
                                        + buffer.length);
                        String base64 = Base64.encodeToString(buffer,
                                Activity.TRIM_MEMORY_BACKGROUND);
                        Log.d(Constant.TAG,
                                "FragmentCustomerUpdateCV onProcessDataToUpload service running encodeToString"
                                        + buffer.length);
                        Log.d(Constant.TAG,
                                "FragmentCustomerUpdateCV onProcessDataToUpload Bat dau tien trinh upload co file");
                        String error = doUploadImage(fileObj2, base64);
                        if (error.equals("0")) {
                            // messageUpload += mActivity
                            // .getString(R.string.message_update_cv_success)
                            // + " " + fileObj2.getCodeSpinner() + " \n";
                            fileObj2.setUploadStatus(1);
                            isdnZip.delete();
                            numberSuccess++;
                        } else {

                            if (!"10".equals(dbType)) {
                                fileObj2.setStatus(0);
                                FileUtils.insertFileBackUpToDataBase(fileObj2,
                                        mActivity.getApplicationContext());
                            } else {
                                isdnZip.delete();
                            }
                            // messageUpload += mActivity
                            // .getString(R.string.message_update_cv_fail)
                            // + " " + fileObj2.getCodeSpinner() + " " + " \n";
                        }
                    } else {
                        Log.d(Constant.TAG,
                                "FragmentCustomerUpdateCV onProcessDataToUpload khong co file");
                    }
                } catch (Exception e) {
                    // messageUpload += mActivity
                    // .getString(R.string.message_update_cv_fail)
                    // + " "
                    // + fileObj2.getCodeSpinner() + " " + " \n";
                }
            }

            description = messageUpload;
            return numberSuccess;
        }

        private String doUploadImage(FileObj fileObject, String fileContent) {
            Log.d("Log",
                    "doUploadImage upload image to server "
                            + fileObject.getPath() + " filecontent:  "
                            + fileContent.length() + " "
                            + fileObject.toString());

            String original = "";
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_uploadImageOffline");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:uploadImageOffline>");
                rawData.append("<input>");
                rawData.append("<token>").append(Session
                        .getToken()).append("</token>");
                rawData.append("<isdn>").append(fileObject.getIsdn()).append("</isdn>");
                rawData.append("<recordCode>").append(fileObject.getCodeSpinner()).append("</recordCode>");
                rawData.append("<actionCode>").append(fileObject.getActionCode()).append("</actionCode>");
                rawData.append("<reasonId>").append(fileObject.getReasonId()).append("</reasonId>");
                rawData.append("<actionAudit>").append(fileObject.getActionAudit()).append("</actionAudit>");
                rawData.append("<pageIndex>").append(fileObject.getPageIndex()).append("</pageIndex>");
                rawData.append("<status>").append(fileObject.getStatus()).append("</status>");
                rawData.append("<filePath>").append(fileObject.getPath()).append("</filePath>");
                rawData.append("<fileLength>").append(fileObject.getFileLength()).append("</fileLength>");
                rawData.append("<fileLengthOrigin>").append(fileObject.getFileLengthOrigin()).append("</fileLengthOrigin>");
                rawData.append("<fileContent>").append(fileContent).append("</fileContent>");
                rawData.append("<recordId>").append(fileObject.getRecordId()).append("</recordId>");
                rawData.append("<fileName>").append(fileObject.getFileName()).append("</fileName>");
                rawData.append("<actionProfileId>").append(fileObject.getActionProfileId()).append("</actionProfileId>");
                if (viewHolder.rbModifyProfile.isChecked()) {
                    rawData.append("<typeProfile>")
                            .append("ACTION_PROFILE")
                            .append("</typeProfile>");
                } else {
                    rawData.append("<typeProfile>")
                            .append("ACTION_VERIFICATION")
                            .append("</typeProfile>");
                }

                rawData.append("</input>");
                rawData.append("</ws:uploadImageOffline>");
                Log.i("RowData", rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("uploadImageOffline Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope,
                        Constant.BCCS_GW_URL, mActivity,
                        "mbccs_uploadImageOffline", 10000, 30000);
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("mBCCS", "uploadImageOffline Responseeeeeeeeee Original " + response);

                // parser
                Document doc = parse.getDomElement(original);
                NodeList nl = doc.getElementsByTagName("return");
                for (int i = 0; i < nl.getLength(); i++) {
                    Element e2 = (Element) nl.item(i);
                    errorCode = parse.getValue(e2, "errorCode");
                    description = parse.getValue(e2, "description");
                    Log.d(Constant.TAG, errorCode);
                }
                return errorCode;
            } catch (Exception e) {
                Log.e("Log", "Upload fail ", e);
            }
            return "1";
        }

        final OnClickListener onclickIsdn = new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(mActivity.getApplicationContext(),
                        FragmentRegisterServiceVas.class);
                intent.putExtra("isdnKey", isdn);
                mActivity.startActivity(intent);

            }
        };

        public Integer getNumberSuccess() {
            return numberSuccess;
        }

        public void setNumberSuccess(Integer numberSuccess) {
            this.numberSuccess = numberSuccess;
        }


    }
}
