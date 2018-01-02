package com.viettel.bss.viettelpos.v4.sale.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.sale.adapter.SaleTransBankplusAdapter;
import com.viettel.bss.viettelpos.v4.sale.adapter.SaleTransBankplusAdapter.OnChangeCheckedStateBplus;
import com.viettel.bss.viettelpos.v4.sale.asytask.GetAccountBookBankPlusInfo;
import com.viettel.bss.viettelpos.v4.sale.dal.AppParamsDAL;
import com.viettel.bss.viettelpos.v4.sale.dal.BhldDAL;
import com.viettel.bss.viettelpos.v4.sale.object.SaleTransBankplus;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FragmentCheckoutMoney extends Fragment implements OnClickListener,
        OnChangeCheckedStateBplus {
    private View mView = null;
    private EditText edtFromDate, edtToDate;
    private Spinner spn_congno, spn_bank;
    private Button btntimkiem;
    private ListView lvtransacsion;
    private Button btntichtuyet;
    private LinearLayout lntrans;

    private Date fromDate = null;
    private Date toDate = null;

//    private Calendar calfromDate;
//    private int dayfromDate;
//    private int monthfromDate;
//    private int yearsfromDate;
//
//    private Calendar caltoDate;
//    private int daytoDate;
//    private int monthtoDate;
//    private int yearetoDate;


    private ArrayList<SaleTransBankplus> lstSaleTransBankplus = new ArrayList<SaleTransBankplus>();
    private SaleTransBankplusAdapter saleTransBankplusAdapter = null;


    private TextView tvCountTrans;

    private CheckBox checkAllBox;


    private Button btnSearchUtilities;

    private Spinner spn_status;
    private TextView tvBCCSBankAmount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (mView == null) {
            mView = inflater.inflate(R.layout.transacsion_checkout_layout,
                    container, false);
            unitView(mView);
        }
        return mView;
    }

    private void initSpinTrangThai() {

        ArrayList<Spin> arrSpins = new ArrayList<Spin>();
        arrSpins.add(new Spin("", getActivity().getString(R.string.allq)));
        arrSpins.add(new Spin("0", getActivity().getString(R.string.nopthatbai)));
        arrSpins.add(new Spin("1", getActivity().getString(R.string.chuanop)));
        arrSpins.add(new Spin("2", getActivity().getString(R.string.choduyet1)));
        arrSpins.add(new Spin("3", getActivity().getString(R.string.danop)));
        arrSpins.add(new Spin("4", getActivity().getString(R.string.giaodichtreo)));
        arrSpins.add(new Spin("9", getActivity().getString(R.string.dahuy)));

        Utils.setDataSpinner(getActivity(), arrSpins, spn_status);

    }

    private void unitView(View v) {


        tvCountTrans = (TextView) v.findViewById(R.id.tvCountTrans);

        spn_status = (Spinner) v.findViewById(R.id.spn_status);
        initSpinTrangThai();

        lntrans = (LinearLayout) v.findViewById(R.id.lntrans);
        lntrans.setVisibility(View.GONE);
        btntimkiem = (Button) v.findViewById(R.id.btntimkiem);
        btntimkiem.setOnClickListener(this);
        btnSearchUtilities = (Button) v.findViewById(R.id.btnSearchUtilities);
        btnSearchUtilities.setOnClickListener(this);
        SharedPreferences preferences = getActivity().getSharedPreferences(
                Define.PRE_NAME, Activity.MODE_PRIVATE);

        String name = preferences.getString(Define.KEY_MENU_NAME, "");
        if (name.contains(";search.trans.approval;")) {
            btnSearchUtilities.setVisibility(View.VISIBLE);
        } else {
            btnSearchUtilities.setVisibility(View.GONE);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DATE, -3);

        edtFromDate = (EditText) v.findViewById(R.id.edtFromDate);
        edtFromDate.setText(DateTimeUtils.convertDateTimeToString(
                calendar.getTime(), "dd/MM/yyyy"));
        edtFromDate.setOnClickListener(this);
        edtToDate = (EditText) v.findViewById(R.id.edtToDate);
        edtToDate.setText(DateTimeUtils.convertDateTimeToString(
                new Date(), "dd/MM/yyyy"));
        edtToDate.setOnClickListener(this);
        spn_congno = (Spinner) v.findViewById(R.id.spn_congno);
        spn_bank = (Spinner) v.findViewById(R.id.spn_bank);
        lvtransacsion = (ListView) v.findViewById(R.id.lvtransacsion);
        btntichtuyet = (Button) v.findViewById(R.id.btntichtuyet);
        btntichtuyet.setOnClickListener(this);

        checkAllBox = (CheckBox) v.findViewById(R.id.checkAllBox);
        checkAllBox.setOnClickListener(this);

        initBank();
        initType();
        tvBCCSBankAmount = (TextView) v.findViewById(R.id.tvBCCSBankAmount);
        GetAccountBookBankPlusInfo asy = new GetAccountBookBankPlusInfo(
                getActivity(), null, true, null, null, tvBCCSBankAmount);
        asy.execute();
    }

    private void initBank() {
        try {
            BhldDAL dal = new BhldDAL(getActivity());
            dal.open();
            ArrayList<Spin> arrBank = dal.getListBank("PAYMENT_DEBIT_BANK");
            dal.close();
            // Spin spin = new Spin("-1", getString(R.string.txt_select));
            // arrBank.add(0, spin);
            Spin first = new Spin();
            first.setId("");
            first.setValue(getString(R.string.chonnganhang));
            if (arrBank.isEmpty()) {
                arrBank.add(first);
            } else {
                arrBank.add(0, first);
            }
            Utils.setDataSpinner(getActivity(), arrBank, spn_bank);
        } catch (Exception e) {
            Log.e("initBank", e.toString());
        }
    }

    private void initType() {
        ArrayList<Spin> lstType = new ArrayList<Spin>();

//        lstType.add(new Spin("1", getString(R.string.sale)));
//        lstType.add(new Spin("2", getString(R.string.charge_del_ctv)));
//
//        lstType.add(new Spin("3", getString(R.string.cuocdongtruoccodinh)));

        AppParamsDAL dal = new AppParamsDAL(getActivity());
        try {
            dal.open();
            lstType = dal.getListParamSale("PAYMENT_DEBIT_TYPE");
            ArrayAdapter<Spin> adapter = new ArrayAdapter<Spin>(getActivity(),
                    R.layout.spinner_item, lstType);
            spn_congno.setAdapter(adapter);
        } catch (Exception e) {
            Log.e("exception", "Exception", e);
        } finally {
            if (dal != null) {
                try {
                    dal.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }



    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.getInstance().setTitleActionBar(R.string.tichduyetnoptien);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edtFromDate:
//                showDatePickerFromDate(edtFromDate);
                CommonActivity.showDatePickerDialog(getActivity(),edtFromDate);
                break;
            case R.id.edtToDate:
//                showDatePickerToDate(edtToDate);

            CommonActivity.showDatePickerDialog(getActivity(),edtToDate);
                break;
            case R.id.btntimkiem:
                if (validateSearch()) {
                    Spin spnCongno = (Spin) spn_congno.getSelectedItem();
                    if (CommonActivity.isNetworkConnected(getActivity())) {
                        if (spnCongno != null) {
                            checkAllBox.setChecked(false);
                            lstSaleTransBankplus = new ArrayList<SaleTransBankplus>();
                            AsynGetListApproveFinance asynGetListApproveFinance = new AsynGetListApproveFinance(
                                    getActivity());
                            asynGetListApproveFinance.execute(spnCongno.getId());
                        }
                    } else {
                        CommonActivity.createAlertDialog(getActivity(),
                                getString(R.string.errorNetwork),
                                getString(R.string.app_name)).show();
                    }
                }
                break;
            case R.id.btntichtuyet:
                if (validateUpdate()) {

                    OnClickListener onclickTichtuyet = new OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            Spin spnCongno = (Spin) spn_congno.getSelectedItem();
                            if (spnCongno != null) {

                                ApproveFinancialFromPaymentAsyn approveFinancialFromPaymentAsyn = new ApproveFinancialFromPaymentAsyn(
                                        getActivity(), lstSaleTransBankplus);
                                approveFinancialFromPaymentAsyn.execute(spnCongno
                                        .getId());
                            }
                        }
                    };

                    CommonActivity.createDialog(getActivity(),
                            getActivity().getString(R.string.confirmtichduyet),
                            getActivity().getString(R.string.app_name),
                            getActivity().getString(R.string.ok),
                            getActivity().getString(R.string.cancel),
                            onclickTichtuyet, null).show();

                }

                break;

            case R.id.relaBackHome:
                getActivity().onBackPressed();
                break;
            case R.id.checkAllBox:

                for (SaleTransBankplus item : lstSaleTransBankplus) {
                    if ("0".equals(item.getStatus()) || "1".equals(item.getStatus())) {
                        item.setChecked(checkAllBox.isChecked());
                    }
                }
                saleTransBankplusAdapter.notifyDataSetChanged();

                //Dem so luong va tinh tong tien cac giao dich duoc chon
                updateTvCountTrans();

                break;
            case R.id.btnSearchUtilities:
                initFragmentSearchAnypay();
                break;

            default:
                break;
        }

    }

    private void initFragmentSearchAnypay() {
        FragmentSearchAnypay fragmentSearchAnypay = new FragmentSearchAnypay();
        Bundle mbundle = new Bundle();
        mbundle.putString("ketSearch", "1");
        fragmentSearchAnypay.setArguments(mbundle);
        ReplaceFragment.replaceFragment(getActivity(), fragmentSearchAnypay,
                false);
    }

    // OnClickListener onclickTichtuyet = new OnClickListener() {
    //
    // @Override
    // public void onClick(View arg0) {
    // if (CommonActivity.isNetworkConnected(getActivity())) {
    // Spin spnCongno = (Spin) spn_congno.getSelectedItem();
    // if (spnCongno != null) {
    // ApproveFinancialFromPaymentAsyn approveFinancialFromPaymentAsyn = new
    // ApproveFinancialFromPaymentAsyn(
    // getActivity(), lstSaleTransBankplus);
    // approveFinancialFromPaymentAsyn.execute(spnCongno.getId());
    // }
    // }
    //
    // }
    // };

    private boolean validateUpdate() {
        for (SaleTransBankplus item : lstSaleTransBankplus) {
            if (item.isChecked()) {
                return true;
            }
        }

        CommonActivity
                .createAlertDialog(getActivity(),
                        getString(R.string.checkgiaodich),
                        getString(R.string.app_name)).show();
        return false;
    }

    private boolean validateSearch() {
        fromDate = DateTimeUtils.convertStringToTime(edtFromDate.getText().toString(),"dd/MM/yyyy");
        toDate= DateTimeUtils.convertStringToTime(edtToDate.getText().toString(),"dd/MM/yyyy");
        if (fromDate.after(new Date())) {
            CommonActivity.createAlertDialog(getActivity(),
                    getString(R.string.tungaynhohonhtai),
                    getString(R.string.app_name)).show();
            return false;
        }
        if (toDate.after(new Date())) {
            CommonActivity.createAlertDialog(getActivity(),
                    getString(R.string.denngaynhohonhtai),
                    getString(R.string.app_name)).show();
            return false;
        }

        if (fromDate.after(toDate)) {
            CommonActivity.createAlertDialog(getActivity(),
                    getString(R.string.checktimeupdatejob),
                    getString(R.string.app_name)).show();
            return false;
        }

        return true;
    }

    private class AsynGetListApproveFinance extends
            AsyncTask<String, Void, ArrayList<SaleTransBankplus>> {

        private Activity mActivity = null;
        XmlDomParse parse = new XmlDomParse();
        String errorCode = "";
        String description = "";
        ProgressDialog progress;

        public AsynGetListApproveFinance(Activity context) {
            this.progress = new ProgressDialog(context);
            // check font

            this.progress.setMessage(context.getResources().getString(
                    R.string.processing));
            this.progress.setCancelable(false);
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected ArrayList<SaleTransBankplus> doInBackground(String... arg0) {
            return getListApproveFinance(arg0[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<SaleTransBankplus> result) {
            super.onPostExecute(result);
            progress.dismiss();
            if (errorCode.equals("0")) {
                if (result != null && !result.isEmpty()) {
                    lntrans.setVisibility(View.VISIBLE);
                    lstSaleTransBankplus.addAll(result);
                    saleTransBankplusAdapter = new SaleTransBankplusAdapter(
                            getActivity(), lstSaleTransBankplus,
                            FragmentCheckoutMoney.this);
                    lvtransacsion.setAdapter(saleTransBankplusAdapter);
                    tvCountTrans.setText(Html.fromHtml(getActivity().getString(R.string.counttrans) + " <b>" + 0
                            + "</b>.<br> " + getActivity().getString(R.string.counttransMoney) + " <b><font color=\"red\">"
                            + StringUtils.formatMoney("" + 0) + "</font></b>."));
                } else {
                    lntrans.setVisibility(View.GONE);
                    lstSaleTransBankplus = new ArrayList<SaleTransBankplus>();
                    saleTransBankplusAdapter = new SaleTransBankplusAdapter(
                            getActivity(), lstSaleTransBankplus,
                            FragmentCheckoutMoney.this);
                    lvtransacsion.setAdapter(saleTransBankplusAdapter);
                    saleTransBankplusAdapter.notifyDataSetChanged();
                    CommonActivity.createAlertDialog(getActivity(),
                            getActivity().getString(R.string.khongcotran),
                            getActivity().getString(R.string.app_name)).show();
                }
            } else {
                lntrans.setVisibility(View.GONE);
                lstSaleTransBankplus = new ArrayList<SaleTransBankplus>();
                saleTransBankplusAdapter = new SaleTransBankplusAdapter(
                        getActivity(), lstSaleTransBankplus,
                        FragmentCheckoutMoney.this);
                lvtransacsion.setAdapter(saleTransBankplusAdapter);
                saleTransBankplusAdapter.notifyDataSetChanged();
                if (errorCode.equals(Constant.INVALID_TOKEN2)) {
                    Dialog dialog = CommonActivity
                            .createAlertDialog(
                                    getActivity(),
                                    description,
                                    getActivity().getResources().getString(
                                            R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = getActivity()
                                .getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(
                            getActivity(), description, getActivity()
                                    .getResources()
                                    .getString(R.string.app_name));
                    dialog.show();
                }
            }

        }

        private ArrayList<SaleTransBankplus> getListApproveFinance(String type) {
            String original = "";
            ArrayList<SaleTransBankplus> arSaleTransBankplus = new ArrayList<SaleTransBankplus>();
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getListApproveFinanceBccs2");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getListApproveFinance>");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");

                rawData.append("<fromDate>"
                        + edtFromDate.getText().toString().trim());
                rawData.append("</fromDate>");
                rawData.append("<toDate>"
                        + edtToDate.getText().toString().trim());
                rawData.append("</toDate>");
                rawData.append("<saleTransType>" + type);
                rawData.append("</saleTransType>");

                Spin spin = (Spin) spn_status.getSelectedItem();
                if (spin != null) {
                    rawData.append("<status>" + spin.getId());
                    rawData.append("</status>");
                }

                rawData.append("</input>");
                rawData.append("</ws:getListApproveFinance>");
                Log.i("RowData", rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope,
                        Constant.BCCS_GW_URL, getActivity(),
                        "mbccs_getListApproveFinance");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("Responseeeeeeeeee Original", original);

                // parser
                Document doc = parse.getDomElement(original);
                NodeList nl = doc.getElementsByTagName("return");
                NodeList nodeChild = null;
                for (int i = 0; i < nl.getLength(); i++) {
                    Element e2 = (Element) nl.item(i);
                    errorCode = parse.getValue(e2, "errorCode");
                    description = parse.getValue(e2, "description");
                    Log.d("errorCode", errorCode);

                    // lstSaleTransBankPluses
                    nodeChild = doc
                            .getElementsByTagName("lstSaleTransDTOs");
                    for (int j = 0; j < nodeChild.getLength(); j++) {
                        Element e1 = (Element) nodeChild.item(j);
                        SaleTransBankplus saleTransBankplus = new SaleTransBankplus();
                        saleTransBankplus.setSaleTransBankPlusID(parse
                                .getValue(e1, "saleTransId"));
                        saleTransBankplus.setIsdnBankPlus(parse.getValue(e1,
                                "bankplusNumber"));
                        saleTransBankplus.setBalance(parse.getValue(e1,
                                "amountTax"));
                        saleTransBankplus.setFeeAmount(parse.getValue(e1,
                                "feeAmount"));
                        saleTransBankplus.setStatusName(parse.getValue(e1,
                                "bankplusStatusName"));
                        saleTransBankplus.setCreateDate(StringUtils
                                .convertDate(parse.getValue(e1, "saleTransDate")));
                        saleTransBankplus
                                .setDateApprove(StringUtils.convertDate(parse
                                        .getValue(e1, "dateApprove")));
                        saleTransBankplus.setStatus(parse
                                .getValue(e1, "bankplusStatus"));
                        arSaleTransBankplus.add(saleTransBankplus);
                    }
                }
            } catch (Exception e) {
                Log.d("mbccs_getListApproveFinance", e.toString()
                        + "description error", e);
            }

            return arSaleTransBankplus;

        }
    }

    OnClickListener removeTransClick = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            if (lstSaleTransBankplus != null && !lstSaleTransBankplus.isEmpty()) {
                lstSaleTransBankplus = new ArrayList<SaleTransBankplus>();
            }
            if (saleTransBankplusAdapter != null) {
                saleTransBankplusAdapter.notifyDataSetChanged();
            }
            Spin spnCongno = (Spin) spn_congno.getSelectedItem();
            if (CommonActivity.isNetworkConnected(getActivity())) {
                if (spnCongno != null) {
                    AsynGetListApproveFinance asynGetListApproveFinance = new AsynGetListApproveFinance(
                            getActivity());
                    asynGetListApproveFinance.execute(spnCongno.getId());
                }
            } else {
                CommonActivity.createAlertDialog(getActivity(),
                        getActivity().getString(R.string.errorNetwork),
                        getActivity().getString(R.string.app_name)).show();
            }

        }
    };

    private class ApproveFinancialFromPaymentAsyn extends
            AsyncTask<String, Void, String> {

        private Activity mActivity = null;
        XmlDomParse parse = new XmlDomParse();
        String errorCode = "";
        String description = "";
        ProgressDialog progress;
        private ArrayList<SaleTransBankplus> arrSaleTransBankplus = new ArrayList<SaleTransBankplus>();

        public ApproveFinancialFromPaymentAsyn(Activity context,
                                               ArrayList<SaleTransBankplus> lstSaleTransBankplus) {
            this.progress = new ProgressDialog(context);
            // check font

            this.progress.setMessage(context.getResources().getString(
                    R.string.processing));
            this.progress.setCancelable(false);
            if (!this.progress.isShowing()) {
                this.progress.show();
            }

            this.arrSaleTransBankplus = lstSaleTransBankplus;

        }

        @Override
        protected String doInBackground(String... arg0) {
            return approveFinancialFromPayment(arg0[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progress.dismiss();
            if (errorCode.equals("0")) {
                CommonActivity.createAlertDialog(getActivity(),
                        getActivity().getString(R.string.tichduyetok),
                        getActivity().getString(R.string.app_name),
                        removeTransClick).show();
            } else {
                if (errorCode.equals(Constant.INVALID_TOKEN2)) {
                    Dialog dialog = CommonActivity
                            .createAlertDialog(
                                    getActivity(),
                                    description,
                                    getActivity().getResources().getString(
                                            R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = getActivity()
                                .getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(
                            getActivity(), description, getActivity()
                                    .getResources()
                                    .getString(R.string.app_name));
                    dialog.show();
                }
            }

        }

        private String approveFinancialFromPayment(String type) {
            String original = "";
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode",
                        "mbccs_approveFinancialFromPaymentBccs2");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:approveFinancialFromPayment>");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");

                Spin spinBank = (Spin) spn_bank.getSelectedItem();
                if (spinBank != null) {
                    rawData.append("<bankCode>" + spinBank.getId());
                    rawData.append("</bankCode>");
                }

                if (arrSaleTransBankplus != null
                        && !arrSaleTransBankplus.isEmpty()) {
                    for (SaleTransBankplus saleTransBankplus : arrSaleTransBankplus) {
                        if (saleTransBankplus.isChecked()) {
                            rawData.append("<lstSaleTransId>"
                                    + saleTransBankplus
                                    .getSaleTransBankPlusID());
                            rawData.append("</lstSaleTransId>");
                        }
                    }
                }
                rawData.append("<saleTransType>" + type);
                rawData.append("</saleTransType>");

                rawData.append("</input>");
                rawData.append("</ws:approveFinancialFromPayment>");
                Log.i("RowData", rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope,
                        Constant.BCCS_GW_URL, getActivity(),
                        "mbccs_approveFinancialFromPayment");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("Responseeeeeeeeee Original", original);

                // parser
                Document doc = parse.getDomElement(original);
                NodeList nl = doc.getElementsByTagName("return");
                for (int i = 0; i < nl.getLength(); i++) {
                    Element e2 = (Element) nl.item(i);
                    errorCode = parse.getValue(e2, "errorCode");
                    description = parse.getValue(e2, "description");
                    Log.d("errorCode", errorCode);

                }
            } catch (Exception e) {
                Log.d("mbccs_approveFinancialFromPayment", e.toString()
                        + "description error", e);
            }

            return errorCode;

        }
    }

    OnClickListener moveLogInAct = new OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
            MainActivity.getInstance().finish();

        }
    };

    @Override
    public void onChangeChecked(SaleTransBankplus saleTrans) {

        for (SaleTransBankplus item : lstSaleTransBankplus) {
            if (item.getSaleTransBankPlusID().equals(
                    saleTrans.getSaleTransBankPlusID())) {
                item.setChecked(!item.isChecked());

                break;
            }
        }

        boolean isCheckAll = true;
        for (SaleTransBankplus item : lstSaleTransBankplus) {
            if ("0".equals(item.getStatus()) || "1".equals(item.getStatus())) {
                if (!item.isChecked()) {
                    isCheckAll = false;
                    break;
                }
            }
        }
        checkAllBox.setChecked(isCheckAll);

        //Dem so luong va tinh tong tien cac giao dich duoc chon
        updateTvCountTrans();
    }

    private void updateTvCountTrans() {
        int dem = 0;
        long totalMoney = 0;
        for (SaleTransBankplus item : lstSaleTransBankplus) {
            if (item.isChecked()) {
                dem++;
                totalMoney += Long.parseLong(item.getBalance());
                if (!CommonActivity.isNullOrEmpty(item.getFeeAmount())) {
                    totalMoney += Long.parseLong(item.getFeeAmount());
                }
            }
        }
        tvCountTrans.setText(Html.fromHtml(getActivity().getString(R.string.counttrans) + " <b>" + dem
                + "</b>.<br> " + getActivity().getString(R.string.counttransMoney) + " <b><font color=\"red\">"
                + StringUtils.formatMoney("" + totalMoney) + "</font></b>."));

    }
}
