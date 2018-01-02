package com.viettel.bss.viettelpos.v4.customer.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.customer.adapter.DeviceWarrantyAdapter;
import com.viettel.bss.viettelpos.v4.customer.adapter.DeviceWarrantyAdapter.OnSelectWarrantyDevice;
import com.viettel.bss.viettelpos.v4.customer.asynctask.AsyncCancelWarrantymBCCS;
import com.viettel.bss.viettelpos.v4.customer.object.BOCOutput;
import com.viettel.bss.viettelpos.v4.customer.object.DeviceWarrantyBO;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentChooseChannel;
import com.viettel.bss.viettelpos.v4.sale.fragment.ChooseShopFragment;
import com.viettel.bss.viettelpos.v4.sale.object.Shop;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FragmentWarranty extends FragmentCommon {
    public static final int REQUEST_CHOOSE_STAFF = 1;
    public static final int REQUEST_CHOOSE_SHOP = 2;
    private final String TAG = "FragmentWarranty";
    private ViewHolder holder;
    private DeviceWarrantyBO deviceWarrantyBO;
    private DeviceWarrantyAdapter warrantyAdapter;
    private String lastTextChange = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        idLayout = R.layout.fragment_warranty;

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        MainActivity.getInstance().setTitleActionBar(R.string.txt_warranty);
    }

    @Override
    public void unit(View view) {
        // TODO Auto-generated method stub
        holder = new ViewHolder();
        holder.btnAccept = (Button) view.findViewById(R.id.btnAccept);
        holder.edtFromDate = (EditText) view.findViewById(R.id.edtFromDate);
        holder.edtSerial = (EditText) view.findViewById(R.id.edtSerial);
        holder.edtToDate = (EditText) view.findViewById(R.id.edtToDate);
        holder.imgSearch = (ImageButton) view.findViewById(R.id.imgSearch);
        holder.tvRecordTotal = (TextView) view.findViewById(R.id.tvRecordTotal);
        holder.edtNote = (EditText) view.findViewById(R.id.edtNote);

        // holder.lnDeviceWarranty = (LinearLayout)
        // view.findViewById(R.id.lnDeviceWarranty);
        holder.lnDate = (LinearLayout) view.findViewById(R.id.lnDate);
        holder.rbLookupReturnWarranty = (RadioButton) view
                .findViewById(R.id.rbLookupReturnWarranty);
        holder.rbWarrantyRepair = (RadioButton) view
                .findViewById(R.id.rbWarrantyRepair);
        holder.edtSerial.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                String text = arg0.toString().trim();
                if (!text.equals(lastTextChange)) {
                    lastTextChange = text;
                    if (holder.lnAccept != null) {
                        holder.lnAccept.setVisibility(View.GONE);

                    }
                }


            }
        });

        holder.lvtransacsion = (ListView) view.findViewById(R.id.lvtransacsion);

        // holder.lnTransStatus = (LinearLayout) view
        // .findViewById(R.id.lnTransStatus);
        holder.lnAccept = (LinearLayout) view.findViewById(R.id.lnAccept);

        holder.spnMethod = (Spinner) view.findViewById(R.id.spnMethod);

        holder.spnObjectType = (Spinner) view.findViewById(R.id.spnObjectType);

        holder.spnObjectType
                .setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {
                        // TODO Auto-generated method stub

                        holder.lnAccept.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });

        holder.spnMethod
                .setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {
                        // TODO Auto-generated method stub
                        if (holder.spnMethod.getSelectedItemPosition() == 1) {
                            holder.spnObjectType.setVisibility(View.GONE);
                        } else if (holder.spnMethod.getSelectedItemPosition() == 2) {
                            holder.spnObjectType.setVisibility(View.VISIBLE);
                        }
                        holder.lnAccept.setVisibility(View.GONE);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });

        holder.imgSearch.setOnClickListener(this);
        holder.btnAccept.setOnClickListener(this);

        holder.rbLookupReturnWarranty.setOnClickListener(this);
        holder.rbWarrantyRepair.setOnClickListener(this);
        holder.lvtransacsion
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,
                                            int position, long arg3) {
                        // TODO Auto-generated method stub
                        DeviceWarrantyBO tmp = warrantyAdapter.getLstData().get(
                                position);
                        if (deviceWarrantyBO != null && !CommonActivity.isNullOrEmpty(tmp.getImei())
                                && !CommonActivity.isNullOrEmpty(deviceWarrantyBO.getImei()) && !deviceWarrantyBO.getImei().equals(tmp.getImei())) {
                            dialogReceiveWarranty = null;
                        }
                        deviceWarrantyBO = warrantyAdapter.getLstData().get(
                                position);

                        if (holder.rbWarrantyRepair.isChecked()) {
                            if (Constant.WARRANTY_SATUS.WARRANTY
                                    .equals(deviceWarrantyBO.getReceiceBHVT())
                                    || Constant.WARRANTY_SATUS.REPAIR
                                    .equals(deviceWarrantyBO
                                            .getReceiceSCDV())) {
                                deviceWarrantyBO.setSelect(true);

                            }
                            rbSelectResetData(deviceWarrantyBO);
                        } else {
                            deviceWarrantyBO.setSelect(true);

                            warrantyAdapter.notifyDataSetChanged();

                        }
                    }
                });

        holder.edtFromDate.setText(DateTimeUtils.getFirstDateOfMonth());

        Calendar cal = Calendar.getInstance();
        holder.edtToDate.setText(DateTimeUtils.convertDateTimeToString(
                cal.getTime(), "dd/MM/yyyy"));

        holder.edtFromDate.setOnClickListener(this);
        holder.edtToDate.setOnClickListener(this);

        holder.spnMethod.setSelection(1);
        new AsyGetWarrantymBccs(getActivity()).execute();

    }

    private void rbSelectResetData(DeviceWarrantyBO item) {
        for (DeviceWarrantyBO deviceWarrantyBO : warrantyAdapter.getLstData()) {
            if (!CommonActivity.isNullOrEmpty(deviceWarrantyBO.getImei())
                    && !CommonActivity.isNullOrEmpty(deviceWarrantyBO
                    .getStockModelName())) {
                if (deviceWarrantyBO.getImei().equals(item.getImei())
                        && deviceWarrantyBO.getStockModelCode().equals(
                        item.getStockModelCode())) {
                    continue;
                }
            }

            deviceWarrantyBO.setSelect(false);
        }
        warrantyAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub
        switch (view.getId()) {
            case R.id.rbLookupReturnWarranty:
                if (holder.rbLookupReturnWarranty.isChecked()) {
                    holder.lnDate.setVisibility(View.VISIBLE);
                } else {
                    holder.lnDate.setVisibility(View.GONE);
                }
                holder.lnAccept.setVisibility(View.GONE);
                holder.btnAccept.setText(getResources().getString(
                        R.string.txt_return_device));
                holder.edtSerial.setText("");
                break;
            case R.id.rbWarrantyRepair:
                if (holder.rbWarrantyRepair.isChecked()) {
                    holder.lnDate.setVisibility(View.GONE);
                } else {
                    holder.lnDate.setVisibility(View.VISIBLE);
                }
                holder.lnAccept.setVisibility(View.GONE);
                holder.btnAccept.setText(getResources()
                        .getString(R.string.tiepnhan));
                holder.edtSerial.setText("");
                break;
            case R.id.imgSearch:
                // holder.lnDeviceWarranty.setVisibility(View.GONE);
                holder.edtNote.setVisibility(View.GONE);
                if (holder.rbWarrantyRepair.isChecked()) {
                    if (CommonActivity.isNullOrEmpty(holder.edtSerial.getText()
                            .toString())) {
                        CommonActivity.createAlertDialog(
                                getActivity(),
                                getResources().getString(
                                        R.string.txt_serial_must_enter),
                                getResources().getString(R.string.app_name)).show();
                        return;
                    }

                    if (StringUtils.CheckCharSpecical(holder.edtSerial.getText()
                            .toString())) {
                        CommonActivity.createAlertDialog(
                                getActivity(),
                                getResources().getString(
                                        R.string.checkspecialSerial),
                                getResources().getString(R.string.app_name)).show();
                        return;
                    }

                    new AsyGetSearchImeiReceive(getActivity()).execute();
                } else {
                    if (holder.spnMethod.getSelectedItemPosition() == 0) {
                        CommonActivity
                                .createAlertDialog(
                                        getActivity(),
                                        getResources()
                                                .getString(
                                                        R.string.txt_dont_select_warranty_method),
                                        getResources().getString(R.string.app_name))
                                .show();
                        return;
                    }

                    //tra cuu tra bao hanh
                    if (holder.spnMethod.getSelectedItemPosition() == 1) {
                        if (!validateDate()) {
                            return;
                        }

                        new AsyGetWarrantymBccs(getActivity()).execute();
                    } else if (holder.spnMethod.getSelectedItemPosition() == 2) {
                        //truong hop tra cuu
                        if (holder.spnObjectType.getSelectedItemPosition() == 0) {
                            CommonActivity.createAlertDialog(
                                    getActivity(),
                                    getResources().getString(
                                            R.string.txt_dont_select_warranty_object_type),
                                    getResources().getString(R.string.app_name)).show();
                            return;
                        }

                        if (holder.spnObjectType.getSelectedItemPosition() == 1) {
                            if (!validateDate()) {
                                return;
                            }

                            new AsyGetWarrantymBccs(getActivity()).execute();
                        } else {
                            if (!validateGetWarrantyActionMBCCS()) {
                                return;
                            }

                            new AsyGetWarrantyActionmBccs(getActivity()).execute();
                        }
                    }
                }
                break;

            case R.id.btnAccept:
                if (!validateReceiveWarranty()) {
                    return;
                }
                if (holder.rbWarrantyRepair.isChecked()) {
                    showDialogReceiveWarranty();
                } else {
                    CommonActivity
                            .createDialog(
                                    getActivity(),
                                    getResources().getString(
                                            R.string.txt_warranty_return_confirm),
                                    getString(R.string.app_name),

                                    getString(R.string.cancel),
                                    getString(R.string.ok),
                                    null, OnClickWarrantyFinish).show();
                }
                break;
            case R.id.edtFromDate:
                CommonActivity.showDatePickerDialog(getActivity(),
                        holder.edtFromDate);
                break;
            case R.id.edtToDate:
                CommonActivity
                        .showDatePickerDialog(getActivity(), holder.edtToDate);
                break;
            default:
                super.onClick(view);
                break;
        }
    }

    private boolean validateGetWarrantyActionMBCCS() {

        if (CommonActivity.isNullOrEmpty(holder.edtSerial.getText().toString())) {
            CommonActivity.createAlertDialog(getActivity(),
                    getResources().getString(R.string.txt_serial_must_enter),
                    getResources().getString(R.string.app_name)).show();
            return false;
        }

        if (StringUtils
                .CheckCharSpecical(holder.edtSerial.getText().toString())) {
            CommonActivity.createAlertDialog(getActivity(),
                    getResources().getString(R.string.checkspecialSerial),
                    getResources().getString(R.string.app_name)).show();
            return false;
        }

        return validateDate();
    }

    private boolean validateReceiveWarranty() {
        for (DeviceWarrantyBO item : warrantyAdapter.getLstData()) {
            if (item.isSelect()) {
                return true;
            }
        }

        CommonActivity.createAlertDialog(getActivity(),
                getResources().getString(R.string.txt_warranty_not_select),
                getResources().getString(R.string.app_name)).show();
        return false;
    }

    private boolean validateDate() {
        Calendar calFromDate = Calendar.getInstance();
        calFromDate.setTime(DateTimeUtils.convertStringToTime(
                holder.edtFromDate.getText().toString(), "dd/MM/yyyy"));

        Calendar calToDate = Calendar.getInstance();
        calToDate.setTime(DateTimeUtils.convertStringToTime(holder.edtToDate
                .getText().toString(), "dd/MM/yyyy"));

        if (calFromDate.getTime().after(calToDate.getTime())) {
            CommonActivity.createAlertDialog(getActivity(),
                    getString(R.string.checktimeupdatejob),
                    getString(R.string.app_name)).show();
            return false;
        }

        // if (calFromDate.get(Calendar.MONTH) != calToDate.get(Calendar.MONTH)
        // || calFromDate.get(Calendar.YEAR) != calToDate
        // .get(Calendar.YEAR)) {
        // CommonActivity.createAlertDialog(getActivity(),
        // getString(R.string.txt_only_search_in_month),
        // getString(R.string.app_name)).show();
        // return false;
        // }

        int DURATION_MAX = 30;
        if (DateTimeUtils.calculateDays2Date(calFromDate.getTime(),
                calToDate.getTime()) > DURATION_MAX) {
            CommonActivity.createAlertDialog(getActivity(),
                    getString(R.string.duration_over_load, DURATION_MAX),
                    getString(R.string.app_name)).show();
            return false;
        }

        return true;
    }

    @Override
    public void setPermission() {
        // TODO Auto-generated method stub

    }

    private class AsyGetSearchImeiReceive extends
            AsyncTask<String, Void, BOCOutput> {

        private final Activity mActivity;
        final ProgressDialog progress;

        public AsyGetSearchImeiReceive(Activity mActivity) {

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
            return getSearchImeiReceive();
        }

        @Override
        protected void onPostExecute(BOCOutput result) {
            super.onPostExecute(result);
            progress.dismiss();

            if (result.getErrorCode().equals("0")) {
                if (CommonActivity.isNullOrEmpty(result.getLstImeiReceive())) {
                    Dialog dialog = CommonActivity.createAlertDialog(
                            getActivity(),
                            mActivity.getResources().getString(
                                    R.string.txt_search_invalid_info),
                            getResources().getString(R.string.app_name));
                    dialog.show();

                    holder.lnAccept.setVisibility(View.GONE);
                    return;
                }

                Log.d(TAG, "result.getLstImeiReceive() size = "
                        + result.getLstImeiReceive().size());
                holder.tvRecordTotal.setText(getString(
                        R.string.txt_record_total, result.getLstImeiReceive()
                                .size()));
                warrantyAdapter = new DeviceWarrantyAdapter(getActivity(),
                        result.getLstImeiReceive(), onSelectWarrantyDevice,
                        Constant.WARRANTY_TYPE.WARRANTY_RECEIVE_OR_REPAIR, 2);
                holder.lvtransacsion.setAdapter(warrantyAdapter);

                holder.lnAccept.setVisibility(View.VISIBLE);
                // holder.btnAccept.setVisibility(View.VISIBLE);
                enableBtnAccept();
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

        private BOCOutput getSearchImeiReceive() {
            BOCOutput bocOutput = new BOCOutput();
            String original = "";
            try {
                String methodName = "getSearchImeiReceive";
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);

                input.addValidateGateway("wscode", "mbccs_" + methodName);
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:").append(methodName).append(">");
                rawData.append("<input>");
                rawData.append("<token>").append(Session.getToken()).append("</token>");

                rawData.append("<imei>").append(holder.edtSerial.getText().toString()).append("</imei>");

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
                Log.e(Constant.TAG, "getSearchImeiReceive", e);
                bocOutput = new BOCOutput();
                bocOutput.setDescription(e.getMessage());
                bocOutput.setErrorCode(Constant.ERROR_CODE);
            }

            return bocOutput;
        }

    }

    private void enableBtnAccept() {
        boolean enable = false;
        for (DeviceWarrantyBO item : warrantyAdapter.getLstData()) {
            if (Constant.WARRANTY_SATUS.REPAIR.equals(item.getReceiceSCDV())
                    || Constant.WARRANTY_SATUS.WARRANTY.equals(item
                    .getReceiceBHVT())) {
                enable = true;
                break;
            }
        }

        if (enable) {
            holder.btnAccept.setVisibility(View.VISIBLE);
        } else {
            holder.btnAccept.setVisibility(View.GONE);
        }
    }

    private EditText edtCustomerName;
    private EditText edtCustomerIsdn;
    private EditText edtCustomerAddress;
    EditText edtError;
    private Spinner spnWarrantyType;
    private Dialog dialogReceiveWarranty;
    private Spinner spnChannelType;
    private TextView edtChannel;
    private String channelName;
    private String channelCode;

    private void showDialogReceiveWarranty() {
        if (dialogReceiveWarranty != null) {
            dialogReceiveWarranty.show();
            return;
        }
        dialogReceiveWarranty = new Dialog(getActivity());
        dialogReceiveWarranty.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogReceiveWarranty
                .setContentView(R.layout.fragment_dialog_receive_warranty);
        dialogReceiveWarranty.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogReceiveWarranty.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogReceiveWarranty.getWindow().setAttributes(lp);
        edtCustomerName = (EditText) dialogReceiveWarranty
                .findViewById(R.id.edtCustomerName);
        edtCustomerIsdn = (EditText) dialogReceiveWarranty
                .findViewById(R.id.edtIsdn);
        edtCustomerAddress = (EditText) dialogReceiveWarranty
                .findViewById(R.id.edtAddress);
        spnWarrantyType = (Spinner) dialogReceiveWarranty
                .findViewById(R.id.spnWarrantyType);
        spnChannelType = (Spinner) dialogReceiveWarranty
                .findViewById(R.id.spnChannelType);
        edtError = (EditText) dialogReceiveWarranty
                .findViewById(R.id.edtError);
        edtChannel = (TextView) dialogReceiveWarranty
                .findViewById(R.id.edtChannel);

        Utils.setDataSpinner(getActivity(), initSpinner(), spnWarrantyType);
        Utils.setDataSpinner(getActivity(), initChannelType(), spnChannelType);

        Button btnOk = (Button) dialogReceiveWarranty.findViewById(R.id.btnOK);
        Button btnCancel = (Button) dialogReceiveWarranty
                .findViewById(R.id.btnCancel);

        btnOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                warrantyReceivemBCCS();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                dialogReceiveWarranty.dismiss();
            }
        });

        spnChannelType.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                edtChannel.setText("");
                channelName = "";
                channelCode = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edtChannel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseChannel();
            }
        });
        dialogReceiveWarranty.show();
    }

    private List<Spin> initSpinner() {
        List<Spin> lstSpin = new ArrayList<>();
        Spin spin = new Spin();
        spin.setName(getString(R.string.txt_select_warranty_type));
        spin.setId("-1");
        lstSpin.add(spin);

        if (Constant.WARRANTY_SATUS.WARRANTY.equals(deviceWarrantyBO
                .getReceiceBHVT())) {
            spin = new Spin();
            spin.setName(getString(R.string.txt_spn_select_warranty));
            spin.setId(deviceWarrantyBO.getReceiceBHVT());
            lstSpin.add(spin);
        }

        if (Constant.WARRANTY_SATUS.REPAIR.equals(deviceWarrantyBO
                .getReceiceSCDV())) {
            spin = new Spin();
            spin.setName(getString(R.string.txt_spn_select_repair));
            spin.setId(deviceWarrantyBO.getReceiceSCDV());
            lstSpin.add(spin);
        }
        return lstSpin;
    }

    private List<Spin> initChannelType() {
        List<Spin> lstSpin = new ArrayList<>();
        Spin staff = new Spin();
        staff.setName(getString(R.string.staff_type));
        staff.setId("1");
        lstSpin.add(staff);
        Spin shop = new Spin();
        shop.setName(getString(R.string.shop_type));
        shop.setId("2");
        lstSpin.add(shop);
        return lstSpin;
    }

    private void warrantyReceivemBCCS() {
        if (CommonActivity.isNullOrEmpty(edtCustomerName.getText().toString())
                || CommonActivity.isNullOrEmpty(edtCustomerIsdn.getText()
                .toString())
                || CommonActivity.isNullOrEmpty(edtCustomerAddress.getText()
                .toString())) {
            CommonActivity.createAlertDialog(getActivity(),
                    R.string.txt_customer_info_not_enough, R.string.app_name)
                    .show();
            return;
        }

        if (CommonActivity.isNullOrEmpty(edtError.getText().toString())) {
            CommonActivity.createAlertDialog(getActivity(),
                    R.string.txt_warranty_error_required, R.string.app_name)
                    .show();
            return;
        }

        if (StringUtils.CheckCharSpecical(edtError.getText().toString())) {
            CommonActivity.createAlertDialog(getActivity(),
                    R.string.txt_warranty_error_contain_special, R.string.app_name)
                    .show();
            return;
        }

        if (spnWarrantyType.getSelectedItemPosition() == 0) {
            CommonActivity.createAlertDialog(getActivity(),
                    R.string.txt_dont_select_warranty_type, R.string.app_name)
                    .show();
            return;
        }
        if (CommonActivity.isNullOrEmpty(channelCode)) {
            CommonActivity.createAlertDialog(getActivity(),
                    R.string.channel_do_not_empty, R.string.app_name)
                    .show();
            return;
        }

       	CommonActivity.createDialog(getActivity(),
				getResources().getString(R.string.txt_warranty_confirm),
				getString(R.string.app_name), getString(R.string.cancel),
				getString(R.string.ok ),null ,OnClickReceiveWarranty )
				.show();

    }

    private final OnClickListener OnClickReceiveWarranty = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            new AsyReceiveWarranty(getActivity()).execute();
        }
    };

    private final OnClickListener OnClickWarrantyFinish = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            if (StringUtils.CheckCharSpecical(holder.edtNote.getText().toString())) {
                CommonActivity.createAlertDialog(getActivity(),
                        getResources().getString(R.string.txt_warranty_note_error_contain_special),
                        getResources().getString(R.string.app_name)).show();
                return;
            }
            new AsyWarrantyFinishedmBccs(getActivity()).execute();
        }
    };

    private class AsyReceiveWarranty extends AsyncTask<String, Void, BOCOutput> {

        private final Activity mActivity;
        final ProgressDialog progress;

        public AsyReceiveWarranty(Activity mActivity) {

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
            return warrantyReceivemBCCS();
        }

        @Override
        protected void onPostExecute(BOCOutput result) {
            super.onPostExecute(result);
            progress.dismiss();

            if (result.getErrorCode().equals("0")) {
                refreshAdapter();
                if (CommonActivity.isNullOrEmpty(result.getDescription())) {
                    Dialog dialog = CommonActivity.createAlertDialog(
                            getActivity(), R.string.txt_warranty_success,
                            R.string.app_name);
                    dialog.show();
                } else {
                    Dialog dialog = CommonActivity.createAlertDialog(
                            getActivity(), result.getDescription(),
                            getString(R.string.app_name));
                    dialog.show();
                }

                dialogReceiveWarranty.dismiss();
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

        private BOCOutput warrantyReceivemBCCS() {
            BOCOutput bocOutput = new BOCOutput();
            String original = "";
            try {
                String methodName = "warrantyReceivemBCCS";
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);

                input.addValidateGateway("wscode", "mbccs_" + methodName);
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:" + methodName + ">");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");

                rawData.append("<customerName>"
                        + StringUtils.escapeHtml(edtCustomerName.getText()
                        .toString().trim()) + "</customerName>");
                rawData.append("<customerIsdn>"
                        + edtCustomerIsdn.getText().toString().trim()
                        + "</customerIsdn>");
                rawData.append("<customerAddress>"
                        + StringUtils.escapeHtml(edtCustomerAddress.getText()
                        .toString().trim()) + "</customerAddress>");
                rawData.append("<ttnsCode>" + Session.getTtnsCode()
                        + "</ttnsCode>");
                rawData.append("<imei>" + deviceWarrantyBO.getImei()
                        + "</imei>");
                rawData.append("<stockModelCode>"
                        + deviceWarrantyBO.getStockModelCode()
                        + "</stockModelCode>");

                rawData.append("<receiptType>"
                        + ((Spin) spnWarrantyType.getSelectedItem()).getId()
                        + "</receiptType>");

                rawData.append("<errorDesc>").append(edtError.getText().toString().trim()).append("</errorDesc>");
                rawData.append("<codeAgency>").append(channelCode).append("</codeAgency>");

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
                Log.e(Constant.TAG, "warrantyReceivemBCCS", e);
                bocOutput = new BOCOutput();
                bocOutput.setDescription(e.getMessage());
                bocOutput.setErrorCode(Constant.ERROR_CODE);
            }

            return bocOutput;
        }

    }

    private final OnSelectWarrantyDevice onSelectWarrantyDevice = new OnSelectWarrantyDevice() {

        @Override
        public void onSelectWarrantyDevice(DeviceWarrantyBO item) {
            // TODO Auto-generated method stub
            deviceWarrantyBO = item;
            warrantyAdapter.notifyDataSetChanged();
        }

        @Override
        public void onSelectWarrantyDeviceReturn(DeviceWarrantyBO item) {


        }

        @Override
        public void onCancelWarrantyDevice(DeviceWarrantyBO item) {
            cancelRequest(item);
        }
    };

    private class AsyGetWarrantyActionmBccs extends
            AsyncTask<String, Void, BOCOutput> {

        private final Activity mActivity;
        final ProgressDialog progress;

        public AsyGetWarrantyActionmBccs(Activity mActivity) {

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
            return getWarrantyActionmBccs();
        }

        @Override
        protected void onPostExecute(BOCOutput result) {
            super.onPostExecute(result);
            progress.dismiss();
            holder.btnAccept.setVisibility(View.GONE);

            if (result.getErrorCode().equals("0")) {
                if (CommonActivity.isNullOrEmpty(result.getLstWarrantyAction())) {
                    Dialog dialog = CommonActivity.createAlertDialog(
                            getActivity(),
                            mActivity.getResources().getString(
                                    R.string.txt_search_invalid_info),
                            getResources().getString(R.string.app_name));
                    dialog.show();

                    holder.lnAccept.setVisibility(View.GONE);
                    return;
                }

                Log.d(TAG, "result.getLstWarrantyAction() size = "
                        + result.getLstWarrantyAction().size());
                holder.tvRecordTotal.setText(getString(
                        R.string.txt_record_total, result
                                .getLstWarrantyAction().size()));
                int type = 0;
                if (holder.spnObjectType != null) {
                    type = holder.spnObjectType.getSelectedItemPosition();
                }
                warrantyAdapter = new DeviceWarrantyAdapter(getActivity(),
                        result.getLstWarrantyAction(), onSelectWarrantyDevice,
                        Constant.WARRANTY_TYPE.WARRANTY_LOOKUP, type);
                holder.lvtransacsion.setAdapter(warrantyAdapter);

                holder.lnAccept.setVisibility(View.VISIBLE);
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

        private BOCOutput getWarrantyActionmBccs() {
            BOCOutput bocOutput = new BOCOutput();
            String original = "";
            try {
                String methodName = "getWarrantyActionmBccs";
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);

                input.addValidateGateway("wscode", "mbccs_" + methodName);
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:").append(methodName).append(">");
                rawData.append("<input>");
                rawData.append("<token>").append(Session.getToken()).append("</token>");

                if (!CommonActivity.isNullOrEmpty(holder.edtSerial.getText()
                        .toString())) {
                    rawData.append("<imei>").append(holder.edtSerial.getText().toString()).append("</imei>");
                }

                rawData.append("<fromDate>").append(holder.edtFromDate.getText().toString()).append("</fromDate>");
                rawData.append("<toDate>").append(holder.edtToDate.getText().toString()).append("</toDate>");

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
                Log.e(Constant.TAG, "getWarrantyActionmBccs", e);
                bocOutput = new BOCOutput();
                bocOutput.setDescription(e.getMessage());
                bocOutput.setErrorCode(Constant.ERROR_CODE);
            }

            return bocOutput;
        }

    }

    private class AsyGetWarrantymBccs extends
            AsyncTask<String, Void, BOCOutput> {

        private final Activity mActivity;
        final ProgressDialog progress;

        public AsyGetWarrantymBccs(Activity mActivity) {

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
            return getWarrantymBccs();
        }

        @Override
        protected void onPostExecute(BOCOutput result) {
            super.onPostExecute(result);
            progress.dismiss();

            if (result.getErrorCode().equals("0")) {
                if (CommonActivity.isNullOrEmpty(result.getLstWarrantyReturn())) {
                    Dialog dialog = CommonActivity.createAlertDialog(
                            getActivity(),
                            mActivity.getResources().getString(
                                    R.string.txt_search_invalid_info),
                            getResources().getString(R.string.app_name));
                    dialog.show();

                    holder.lnAccept.setVisibility(View.GONE);
                    return;
                }


                Log.d(TAG, "result.getLstWarrantyReturn() size = "
                        + result.getLstWarrantyReturn().size());

                holder.tvRecordTotal.setText(getString(
                        R.string.txt_record_total, result
                                .getLstWarrantyReturn().size()));
                int type = 0;
                if (holder.spnObjectType != null) {
                    type = holder.spnObjectType.getSelectedItemPosition();
                }
                warrantyAdapter = new DeviceWarrantyAdapter(
                        getActivity(),
                        result.getLstWarrantyReturn(),
                        onSelectWarrantyDevice,
                        holder.spnMethod.getSelectedItemPosition() == 2 ? Constant.WARRANTY_TYPE.WARRANTY_LOOKUP
                                : Constant.WARRANTY_TYPE.WARRANTY_RETURN, type);
                holder.lvtransacsion.setAdapter(warrantyAdapter);

                holder.lnAccept.setVisibility(View.VISIBLE);
                if (holder.spnMethod.getSelectedItemPosition() == 1) {
                    holder.btnAccept.setVisibility(View.VISIBLE);
                    holder.edtNote.setVisibility(View.VISIBLE);
                } else {
                    holder.btnAccept.setVisibility(View.GONE);
                    holder.edtNote.setVisibility(View.GONE);
                }
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

        private BOCOutput getWarrantymBccs() {
            BOCOutput bocOutput = new BOCOutput();
            String original = "";
            try {
                String methodName = "getWarrantymBccs";
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);

                input.addValidateGateway("wscode", "mbccs_" + methodName);
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:").append(methodName).append(">");
                rawData.append("<input>");
                rawData.append("<token>").append(Session.getToken()).append("</token>");

                if (!CommonActivity.isNullOrEmpty(holder.edtSerial.getText()
                        .toString())) {
                    rawData.append("<imei>").append(holder.edtSerial.getText().toString()).append("</imei>");
                }

                rawData.append("<fromDate>").append(holder.edtFromDate.getText().toString()).append("</fromDate>");
                rawData.append("<toDate>").append(holder.edtToDate.getText().toString()).append("</toDate>");
                if (holder.spnMethod.getSelectedItemPosition() == 1) {
                    rawData.append("<actionType>6</actionType>");
                }
                // else {
                // rawData.append("<actionType>1000</actionType>");
                // }

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
                Log.e(Constant.TAG, "getWarrantymBccs", e);
                bocOutput = new BOCOutput();
                bocOutput.setDescription(e.getMessage());
                bocOutput.setErrorCode(Constant.ERROR_CODE);
            }

            return bocOutput;
        }

    }

    private class AsyWarrantyFinishedmBccs extends
            AsyncTask<String, Void, BOCOutput> {

        private final Activity mActivity;
        final ProgressDialog progress;

        public AsyWarrantyFinishedmBccs(Activity mActivity) {

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
            return warrantyFinishedmBccs();
        }

        @Override
        protected void onPostExecute(BOCOutput result) {
            super.onPostExecute(result);
            progress.dismiss();

            if (result.getErrorCode().equals("0")) {
                Dialog dialog = CommonActivity
                        .createAlertDialog(getActivity(),
                                R.string.txt_warranty_return_success,
                                R.string.app_name);
                dialog.show();

                refreshAdapter();
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

        private BOCOutput warrantyFinishedmBccs() {
            BOCOutput bocOutput = new BOCOutput();
            String original = "";
            try {
                String methodName = "warrantyFinishedmBccs";
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);

                input.addValidateGateway("wscode", "mbccs_" + methodName);
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:").append(methodName).append(">");
                rawData.append("<input>");
                rawData.append("<token>").append(Session.getToken()).append("</token>");

                for (DeviceWarrantyBO item : warrantyAdapter.getLstData()) {
                    if (item.isSelect()) {
                        rawData.append("<lstWarrantyReturn>");

                        rawData.append("<imei>").append(StringUtils.escapeHtml(item.getImei())).append("</imei>");

                        rawData.append("<stockModelCode>").append(StringUtils.escapeHtml(item
                                .getStockModelCode())).append("</stockModelCode>");

                        rawData.append("<stockModelName>").append(StringUtils.escapeHtml(item
                                .getStockModelName())).append("</stockModelName>");

                        rawData.append("<warrantyRequestDetailId>").append(item.getWarrantyRequestDetailId()).append("</warrantyRequestDetailId>");

                        rawData.append("<id>").append(item.getId()).append("</id>");
                        // rawData.append("<customerName>"
                        // + StringUtils.escapeHtml(edtCustomerName.getText()
                        // .toString()) + "</customerName>");

                        rawData.append("</lstWarrantyReturn>");
                    }
                }

                if (!CommonActivity.isNullOrEmpty(holder.edtNote.getText().toString())) {
                    rawData.append("<finishDesc>"
                            + holder.edtNote.getText().toString().trim()
                            + "</finishDesc>");
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
                Log.e(Constant.TAG, "warrantyReceivemBCCS", e);
                bocOutput = new BOCOutput();
                bocOutput.setDescription(e.getMessage());
                bocOutput.setErrorCode(Constant.ERROR_CODE);
            }

            return bocOutput;
        }

    }

    private void refreshAdapter() {
        try {
            int index = warrantyAdapter.getLstData().size();
            for (int i = (index - 1); i >= 0; i--) {
                if (warrantyAdapter.getLstData().get(i).isSelect()) {
                    warrantyAdapter.getLstData().remove(i);
                }
            }
            warrantyAdapter.notifyDataSetChanged();
        } catch (Exception ex) {
            Log.d(TAG, "refreshAdapter", ex);
        }
    }

    private void chooseChannel() {
        Spin spn = (Spin) spnChannelType.getSelectedItem();
        if ("1".equals(spn.getId())) {
            FragmentChooseChannel fragment = new FragmentChooseChannel();
            fragment.setTargetFragment(FragmentWarranty.this, REQUEST_CHOOSE_STAFF);
            ReplaceFragment.replaceFragment(getActivity(), fragment, false);
            dialogReceiveWarranty.dismiss();
        } else if ("2".equals(spn.getId())) {
            ChooseShopFragment fragment = new ChooseShopFragment();
            fragment.setTargetFragment(FragmentWarranty.this, REQUEST_CHOOSE_SHOP);
            ReplaceFragment.replaceFragment(getActivity(), fragment, false);
            dialogReceiveWarranty.dismiss();
        }
    }


    class ViewHolder {
        EditText edtSerial;
        ImageButton imgSearch;
        EditText edtFromDate;
        EditText edtToDate;
        RadioButton rbWarrantyRepair;
        RadioButton rbLookupReturnWarranty;
        TextView tvRecordTotal;
        EditText edtNote;
        // LinearLayout lnDeviceWarranty;
        LinearLayout lnDate;
        // LinearLayout lnTransStatus;
        LinearLayout lnAccept;

        ListView lvtransacsion;

        Button btnAccept;
        Spinner spnMethod;
        Spinner spnObjectType;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CHOOSE_SHOP:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    if (bundle == null) {
                        return;
                    }
                    Shop shop = (Shop) data.getExtras().getSerializable("shop");
                    channelName = shop.getName();
                    channelCode = shop.getShopCode();
                    edtChannel.setText(channelCode + " - " + channelName);
                }
                dialogReceiveWarranty.show();
                break;
            case REQUEST_CHOOSE_STAFF:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    if (bundle == null) {
                        return;
                    }
                    Staff staff = (Staff) data.getExtras().getSerializable("staff");
                    channelName = staff.getName();
                    channelCode = staff.getStaffCode();
                    edtChannel.setText(channelCode + " - " + channelName);
                }
                dialogReceiveWarranty.show();
                break;
        }
    }

    OnClickListener cancelRequest = new OnClickListener() {
        @Override
        public void onClick(View v) {
            AsyncCancelWarrantymBCCS asy = new AsyncCancelWarrantymBCCS(getActivity(), cancelWarrantyListener, moveLogInAct, deviceWarrantyBO);
            asy.execute();
        }
    };
    OnPostExecuteListener<Void> cancelWarrantyListener = new OnPostExecuteListener<Void>() {
        OnClickListener searchClick = new OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentWarranty.this.onClick(holder.imgSearch);
            }
        };

        @Override
        public void onPostExecute(Void result, String errorCode, String description) {

            CommonActivity.createAlertDialog(getActivity(), R.string.cancel_warranty_success, R.string.app_name, searchClick).show();
        }
    };

    private void cancelRequest(DeviceWarrantyBO item) {
        deviceWarrantyBO = item;
        String title = getString(R.string.app_name);
        String message = getString(R.string.cancel_warranty_confirm, item.getReceiptNomBccs(),
                item.getStockModelName(),
                item.getImei(), item.getcName());
        String leftButton = getString(R.string.cancel_all_cap);
        String rightButton = getString(R.string.agree_all_cap);

        //Thuc hien huy yeu cau
        CommonActivity.createDialog(getActivity(), message, title,
                leftButton, rightButton, null, cancelRequest).show();
    }
}
