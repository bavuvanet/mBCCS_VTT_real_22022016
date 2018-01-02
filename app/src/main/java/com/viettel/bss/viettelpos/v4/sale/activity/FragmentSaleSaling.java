package com.viettel.bss.viettelpos.v4.sale.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.channel.activity.FragmentCusCareByDay;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.SaleCommons;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.handler.VSAMenuHandler;
import com.viettel.bss.viettelpos.v4.sale.adapter.StockModelAdapter;
import com.viettel.bss.viettelpos.v4.sale.adapter.StockModelAdapter.OnChangeQuantity;
import com.viettel.bss.viettelpos.v4.sale.adapter.StockModelAdapter.OncancelStockModel;
import com.viettel.bss.viettelpos.v4.sale.business.BhldBusiness;
import com.viettel.bss.viettelpos.v4.sale.business.LookUpStockBusiness;
import com.viettel.bss.viettelpos.v4.sale.business.TelecomServiceBusiness;
import com.viettel.bss.viettelpos.v4.sale.dal.AppParamsDAL;
import com.viettel.bss.viettelpos.v4.sale.dal.BhldDAL;
import com.viettel.bss.viettelpos.v4.sale.dal.LookupStockDAL;
import com.viettel.bss.viettelpos.v4.sale.handler.BankplusOrderDetailHandle;
import com.viettel.bss.viettelpos.v4.sale.handler.ReasonCancelOrderHandler;
import com.viettel.bss.viettelpos.v4.sale.object.BankplusOrderBO;
import com.viettel.bss.viettelpos.v4.sale.object.BhldObject;
import com.viettel.bss.viettelpos.v4.sale.object.ProgramSaleBean;
import com.viettel.bss.viettelpos.v4.sale.object.ReasonCancleOrder;
import com.viettel.bss.viettelpos.v4.sale.object.SaleOutput;
import com.viettel.bss.viettelpos.v4.sale.object.Serial;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;
import com.viettel.bss.viettelpos.v4.sale.object.StockModel;
import com.viettel.bss.viettelpos.v4.sale.object.TelecomServiceObject;
import com.viettel.bss.viettelpos.v4.synchronizationdata.GetMaxOraRowScnDal;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FragmentSaleSaling extends Fragment implements OncancelStockModel, OnClickListener, OnChangeQuantity {

    // Chuc nang ban dut
    public static final int FUNCTION_SALING = 1;
    // Ban le
    public static final int FUNCTION_SALE_RETAIL = 2;
    // Ban dat coc
    public static final int FUNCTION_SALE_DEPOSIT = 3;
    // Ban cho kenh BHLD
    public static final int FUNCTION_SALE_BHLD = 4;
    public static final int FUNCTION_SALE_BY_ORDER = 5;
    public static final int FUNCTION_SALE_PROMOTION = 6;
    public static final int REQUEST_CHOOSE_SERIAL = 1;
    public static final int REQUEST_CHOOSE_CHANNEL = 2;
    private Spinner spiService;
    private final String LOG_TAG = FragmentSaleSaling.class.getName();
    private Long selectedTelecom = -1L;
    // Diem ban, CTV da chon, neu ban le thi collId = null
    private Long collId = null;
    private String collCode = "";
    private String collName = "";
    private String errorMessage = "";
    private ArrayList<StockModel> lstStockModel = new ArrayList<StockModel>();
    private ListView lvStockModel;
    private StockModelAdapter adapter;
    private Button btnSale;
    private int functionType;
    private View btnSaleOndialog;
    private View btnCancelOndialog;
    private EditText edtCusName;
    private EditText edtCusCompany;
    private EditText edtCusTin;
    private Dialog cusDialog;
    private Spinner spiHTTT;
    private TextView tvChooseChannel;
    private View view;
    private SearchStockAsync searchStock;
    private TextView tvTotalMoney;
    private TextView tvDiscount;
    private TextView tvRealMoney;
    private CalMoneyForSaleAsy calMoneyForSaleAsy;
    private Button btnViewStockModel;
    private Boolean isViewStockModel = false;
    private TextView tvOrder;
    private TextView tvQuantitySaling;
    private BankplusOrderBO orderBO;
    private GetOrderDetailAsync orderDetailAsync;
    private List<ReasonCancleOrder> lstCancelOrder;
    private Dialog dialogCancelOrder;
    private Button btnCancelOrder;
    private Button btnCancelCancelDialog;
    private String cancelOrderReasonId;
    private Button btnHome;
    private TextView txtNameActionBar;
    private ProgressDialog dialogSendSMS;
    private Boolean isTimeout = false;
    private Long recordWorkId = null;

    // spn_ctbh
    private Spinner spn_ctbh;
    private ArrayList<ProgramSaleBean> arrProgramSaleBeans = null;
    private ProgramSaleBean programSaleBeanMain = new ProgramSaleBean();
    private Boolean isFirst = true;
    // private boolean check = false;
    private LinearLayout lnCTBH;

    private LinearLayout lnCusPhone;
    private LinearLayout lnVAS;
    private EditText edtVasPhone;
    private Spinner spiVas;
    public final int PAY_METHOD_MONEY = 1;
    public final int PAY_METHOD_TRANSFER = 2;
    public static final int PAY_METHOD_BANKPLUS = 16;

    private int payMethod = PAY_METHOD_MONEY;// tien mat
    private EditText edtBankplusMobileDialog;
    private String bankCode;
    private Spinner spnBank;
    private CheckBox cbxReceiveInvoice;
    private EditText edtEmail;
    private Spinner spnBHLD;
    private View lnBHLD, lnInput;
    private View tvHint;
    private String permission = "12334";

    public static FragmentSaleSaling create(String key, int functionType) {
        com.viettel.bss.viettelpos.v4.login.object.Staff mStaff = FragmentCusCareByDay.staff;
        Bundle bundle = new Bundle();
        bundle.putString(Define.ARG_KEY, key);
        bundle.putInt("functionType", functionType);
        bundle.putLong("staffId", mStaff.getStaffId());
        bundle.putString("staffCode", mStaff.getStaffCode());
        bundle.putString("staffName", mStaff.getNameStaff());
        FragmentSaleSaling fragment = new FragmentSaleSaling();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            try {
                view = inflater.inflate(R.layout.layout_sale_saling, container, false);
                hideMoney();

                tvTotalMoney = (TextView) view.findViewById(R.id.tvTotalMoney);
                tvHint = view.findViewById(R.id.tvHint);
                tvHint.setOnClickListener(this);
                tvDiscount = (TextView) view.findViewById(R.id.tvDiscount);
                tvRealMoney = (TextView) view.findViewById(R.id.tvRealMoney);
                tvTotalMoney.setText("");
                tvDiscount.setText("");
                tvRealMoney.setText("");
                lvStockModel = (ListView) view.findViewById(R.id.lvStockModel);
                tvChooseChannel = (TextView) view.findViewById(R.id.tvChooseChannel);
                tvChooseChannel.setOnClickListener(this);
                spiService = (Spinner) view.findViewById(R.id.spn_service);

                spn_ctbh = (Spinner) view.findViewById(R.id.spn_ctbh);

                btnSale = (Button) view.findViewById(R.id.btnOk);
                btnSale.setOnClickListener(this);

                tvQuantitySaling = (TextView) view.findViewById(R.id.tvQuantitySaling);
                view.findViewById(R.id.lnStockModel).setVisibility(View.INVISIBLE);
                btnViewStockModel = (Button) view.findViewById(R.id.btnViewStockModel);
                btnViewStockModel.setOnClickListener(this);
                lnCTBH = (LinearLayout) view.findViewById(R.id.lnCTBH);
                lnCusPhone = (LinearLayout) view.findViewById(R.id.lnPhone);
                lnVAS = (LinearLayout) view.findViewById(R.id.lnVas);
                edtVasPhone = (EditText) view.findViewById(R.id.edtCusPhone);
                spiVas = (Spinner) view.findViewById(R.id.spnVas);
                spiVas.setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        if (arg2 == 0) {
                            lnCusPhone.setVisibility(View.GONE);
                        } else {
                            lnCusPhone.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {

                    }
                });
                Bundle bunder = getArguments();
                tvOrder = (TextView) view.findViewById(R.id.tvOrderId);
                if (bunder != null) {
                    collId = bunder.getLong("staffId");
                    if (collId.compareTo(0L) == 0) {
                        collId = null;
                    }
                    collCode = bunder.getString("staffCode");
                    functionType = bunder.getInt("functionType");
                    collName = bunder.getString("staffName");

                }
                spnBHLD = (Spinner) view.findViewById(R.id.spnBHLD);
                lnBHLD = view.findViewById(R.id.lnBHLD);
                lnInput = view.findViewById(R.id.lnInput);
                lnBHLD.setVisibility(View.GONE);
//                switch (functionType) {
//                    case FUNCTION_SALE_BHLD:
//                        permission = ";sale.program;";
//                        break;
//                    case FUNCTION_SALE_BY_ORDER:
//                        permission = ";sale.by.order;";
//                        break;
//                    case FUNCTION_SALE_DEPOSIT:
//                        permission = ";sale.deposit;";
//                        break;
//                    case FUNCTION_SALE_PROMOTION:
//                        permission = ";sale_promotion;";
//                        break;
//                    case FUNCTION_SALE_RETAIL:
//                        permission = ";sale.retail;";

                switch (functionType) {


                    case FUNCTION_SALE_RETAIL:
                        permission = ";sale.retail;";
                        lnBHLD.setVisibility(View.VISIBLE);
                        lnVAS.setVisibility(View.VISIBLE);
                        initVASSpinner();
                        ArrayList<BhldObject> lstBHLD = BhldBusiness
                                .getListProgram(getActivity());
                        BhldObject first = new BhldObject();
                        first.setSaleProgramName(getString(R.string.select_bhld_program));
                        lstBHLD.add(0, first);
                        ArrayAdapter<BhldObject> adapter = new ArrayAdapter<BhldObject>(
                                getActivity(), R.layout.spinner_item, lstBHLD);
                        spnBHLD.setAdapter(adapter);
                        if (lstBHLD.size() == 1) {
                            lnBHLD.setVisibility(View.GONE);
                        }
                        break;
                    case FUNCTION_SALING:
                        permission = ";sale.saling;";
                        lnVAS.setVisibility(View.VISIBLE);
                        initVASSpinner();
                        break;
                    case FUNCTION_SALE_PROMOTION:
                        lnVAS.setVisibility(View.GONE);
                        permission = ";sale_promotion;";
                        break;
                    case FUNCTION_SALE_BY_ORDER:
                        lnVAS.setVisibility(View.GONE);
                        permission = ";sale.by.order;";
                        break;
                    case FUNCTION_SALE_DEPOSIT:
                        lnVAS.setVisibility(View.GONE);
                        permission = ";sale.deposit;";
                        break;
                    default:
                        lnVAS.setVisibility(View.GONE);
                        break;
                }

                if (functionType == FUNCTION_SALE_BY_ORDER) {

                    if (!CommonActivity.isNetworkConnected(getActivity())) {
                        CommonActivity.createAlertDialog(getActivity(), R.string.errorNetwork, R.string.app_name)
                                .show();
                        getActivity().onBackPressed();
                    }
                    btnSale.setText(getResources().getString(R.string.create_sale_trans));
                    btnViewStockModel.setText(getResources().getString(R.string.cancel_order));
                    orderBO = (BankplusOrderBO) bunder.getSerializable("orderBO");
                    view.findViewById(R.id.lnService).setVisibility(View.GONE);
                    tvQuantitySaling.setText(R.string.quantity_order);
                    tvChooseChannel.setText(orderBO.getStaffCode() + " - " + orderBO.getStaffName());
                    tvChooseChannel.setEnabled(false);
                    tvOrder.setText(orderBO.getOrderCode());
                    if (orderDetailAsync != null) {
                        orderDetailAsync.cancel(true);
                    }

                    orderDetailAsync = new GetOrderDetailAsync(getActivity());
                    orderDetailAsync.execute();

                } else {
                    tvOrder.setVisibility(View.GONE);
                    view.findViewById(R.id.lnService).setVisibility(View.VISIBLE);
                }

                if (functionType == FUNCTION_SALING || functionType == FUNCTION_SALE_BHLD
                        || functionType == FUNCTION_SALE_DEPOSIT) {
                    lnCTBH.setVisibility(View.VISIBLE);
                    if (functionType == FUNCTION_SALE_DEPOSIT) {
                        lnCTBH.setVisibility(View.GONE);
                    }

                    recordWorkId = bunder.getLong("recordWorkId", -1L);

                    if (collId != null && collId > 0) {
                        tvChooseChannel.setText(collName + " - " + collCode);
                        tvChooseChannel.setClickable(false);
                    } else {
                        tvChooseChannel.setClickable(true);
                    }
                } else if (functionType == FUNCTION_SALE_RETAIL || functionType == FUNCTION_SALE_PROMOTION) {
                    lnCTBH.setVisibility(View.VISIBLE);
                    tvChooseChannel.setVisibility(View.GONE);
                    collId = null;
                    collCode = null;
                }
                initSpinner();
                if (arrProgramSaleBeans != null && !arrProgramSaleBeans.isEmpty()) {
                    arrProgramSaleBeans = new ArrayList<ProgramSaleBean>();
                }
                if (programSaleBeanMain != null) {
                    programSaleBeanMain = new ProgramSaleBean();
                }
                initSpinnerCTBH();
                spn_ctbh.setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        // TODO cho chuong trinh ban hang
                        if (isFirst) {
                            isFirst = false;
                            return;
                        }
                        programSaleBeanMain = arrProgramSaleBeans.get(arg2);

                        if (lstStockModel != null && !lstStockModel.isEmpty()) {
                            lstStockModel.clear();
                        }
                        if (searchStock != null) {
                            searchStock.cancel(true);
                        }
                        if (arg2 != 0) {
                            spnBHLD.setSelection(0);
                        }
                        SearchStockAsync search = new SearchStockAsync(getActivity());
                        search.execute();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {

                    }
                });
                spnBHLD.setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        if (arg2 != 0) {
                            spn_ctbh.setSelection(0);
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });

                lvStockModel.setOnScrollListener(new AbsListView.OnScrollListener() {
                    int mLastFirstVisibleItem;

                    @Override
                    public void onScrollStateChanged(AbsListView view,
                                                     int scrollState) {
                        final int currentFirstVisibleItem = view.getFirstVisiblePosition();

                        if (currentFirstVisibleItem > mLastFirstVisibleItem) {
//                            lnInput.animate()
//                                    .translationY(0).alpha(0.0f)
//                                    .setListener(new AnimatorListenerAdapter() {
//                                        @Override
//                                        public void onAnimationEnd(Animator animation) {
//                                            super.onAnimationEnd(animation);
//                                            lnInput.setVisibility(View.GONE);
//                                        }
//                                    });
                            lnInput.setVisibility(View.GONE);

                        } else if (currentFirstVisibleItem == 0) {
                            lnInput.setVisibility(View.VISIBLE);
                        }

                        mLastFirstVisibleItem = currentFirstVisibleItem;

                    }

                    @Override
                    public void onScroll(AbsListView view,
                                         int firstVisibleItem, int visibleItemCount,
                                         int totalItemCount) {

//                        if (mLastFirstVisibleItem < firstVisibleItem) {
//                            // Scrolling down
//                            lnInput.setVisibility(View.GONE);
//
//                        }
//                        // if (mLastFirstVisibleItem > firstVisibleItem) {
//                        // // Scrolling up
//                        //
//                        // }
//                        mLastFirstVisibleItem = firstVisibleItem;
//
//                        if (firstVisibleItem == 0) {
//                            lnInput.setVisibility(View.VISIBLE);
//                        }
                    }
                });
            } catch (Exception e) {
                Log.e(LOG_TAG, "Exception", e);
            }
        } else {
        }
        return view;
    }

    @Override
    public void onResume() {
//        getActivity().registerReceiver(receiver, new IntentFilter(Constant.REGISTER_RECEIVER));
        if (getActivity() instanceof FragmentCusCareByDay) {
            Log.d(this.getClass().getSimpleName(), "getActivity() is instanceof FragmentCusCareByDay");
        } else {
            setTitleActionBar();
        }
        super.onResume();
    }

    private void setTitleActionBar() {
        // TODO Auto-generated method stub
        if (functionType == FUNCTION_SALING) {
            MainActivity.getInstance().setTitleActionBar(getResources().getString(R.string.sale_saling));
        } else if (functionType == FUNCTION_SALE_BHLD) {
            MainActivity.getInstance().setTitleActionBar(getResources().getString(R.string.sale_bhld));
        } else if (functionType == FUNCTION_SALE_RETAIL) {
            MainActivity.getInstance().setTitleActionBar(getResources().getString(R.string.sale_retail));
        } else if (functionType == FUNCTION_SALE_DEPOSIT) {
            MainActivity.getInstance().setTitleActionBar(getResources().getString(R.string.sale_deposit));
        } else if (functionType == FUNCTION_SALE_BY_ORDER) {
            MainActivity.getInstance().setTitleActionBar(getResources().getString(R.string.sale_by_order));
        } else if (functionType == FUNCTION_SALE_PROMOTION) {
            MainActivity.getInstance().setTitleActionBar(getResources().getString(R.string.sale_promotion));
        }
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
//        getActivity().unregisterReceiver(receiver);
        super.onStop();
    }

    /**
     * Tim kiem kho hang dua vao telecomServiceId
     *
     * @return
     */
    private String searchStock() {
        try {
            Long transType = null;
            Long priceType = null;
            if (functionType == FUNCTION_SALE_DEPOSIT) {
                transType = Constant.TRANS_TYPE_SALE_DEPOSIT;
                priceType = Constant.PRICE_SALE_DEPOSIT;
            } else if (functionType == FUNCTION_SALE_PROMOTION) {
                transType = Constant.TRANS_TYPE_SALE;
                priceType = 3L;
            } else {
                transType = Constant.TRANS_TYPE_SALE;
                if (Session.loginUser.isStaff()) {
                    priceType = Constant.PRICE_SALE;
                } else {
                    priceType = 19L;
                }
            }

            lstStockModel = LookUpStockBusiness.lookupStockForSale(getActivity(), collId, priceType, transType,
                    selectedTelecom, functionType == FUNCTION_SALE_BHLD, programSaleBeanMain.getProgram_id());

            if (lstStockModel == null || lstStockModel.isEmpty()) {
                errorMessage = getResources().getString(R.string.stock_no_value);
                return Constant.ERROR_CODE;
            }
            return Constant.SUCCESS_CODE;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception", e);
            errorMessage = getResources().getString(R.string.exception) + e.toString();
            return Constant.ERROR_CODE;
        }
    }

    /**
     * Lay chi tiet don hang cua CTV de xu ly
     *
     * @return
     */
    private String searchOrderDetail() {
        try {
            StringBuilder rawData = new StringBuilder();
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_getListBankPlusOrderDetails");

            rawData.append("<ws:getListBankPlusOrderDetails>");
            rawData.append("<bankPlusInput>");
            rawData.append("<token>").append(Session.getToken()).append("</token>");
            rawData.append("<orderID>").append(orderBO.getOrderCode()).append("</orderID>");

            rawData.append("</bankPlusInput>");
            rawData.append("</ws:getListBankPlusOrderDetails>");

            String envelope = input.buildInputGatewayWithRawData(rawData.toString());
            Log.d(LOG_TAG, envelope);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
                    "mbccs_getListBankPlusOrderDetails");
            Log.d(LOG_TAG, response);
            CommonOutput output = input.parseGWResponse(response);
            if (output == null) {
                errorMessage = getResources().getString(R.string.exception);
                return Constant.ERROR_CODE;
            }
            if (!output.getError().equals("0")) {
                errorMessage = output.getDescription();
                return Constant.ERROR_CODE;
            }

            String original = output.getOriginal();
            BankplusOrderDetailHandle handler = (BankplusOrderDetailHandle) CommonActivity
                    .parseXMLHandler(new BankplusOrderDetailHandle(), original);
            output = handler.getItem();

            if (output == null) {
                errorMessage = getResources().getString(R.string.exception);
                return Constant.ERROR_CODE;
            }
            if (output.getToken() != null && !output.getToken().isEmpty()) {
                Session.setToken(output.getToken());
            }
            if (output.getErrorCode() != null && !output.getErrorCode().equals("0")) {
                if (output.getErrorCode().equalsIgnoreCase(Constant.INVALID_TOKEN)) {
                    isTimeout = true;
                }
                errorMessage = output.getDescription();
                return Constant.ERROR_CODE;
            }

            lstStockModel = handler.getLstStockModel();
            if (lstStockModel == null || lstStockModel.isEmpty()) {
                errorMessage = getResources().getString(R.string.no_data);
                return Constant.ERROR_CODE;
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception", e);
            errorMessage = getResources().getString(R.string.exception) + " " + e.toString();
            return Constant.ERROR_CODE;
        }
        return Constant.SUCCESS_CODE;
    }

    /**
     * Lay danh sach cancelOrder
     *
     * @return
     */
    private String getReasonCancelOrder() {
        try {
            StringBuilder rawData = new StringBuilder();
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_getListCancelBankPlusOrder");

            rawData.append("<ws:getListCancelBankPlusOrder>");
            rawData.append("<bankPlusInput>");
            rawData.append("<token>").append(Session.getToken()).append("</token>");

            rawData.append("</bankPlusInput>");
            rawData.append("</ws:getListCancelBankPlusOrder>");

            String envelope = input.buildInputGatewayWithRawData(rawData.toString());
            Log.d(LOG_TAG, "envelope : " + envelope);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
                    "mbccs_getListCancelBankPlusOrder");
            Log.d(LOG_TAG, "response : " + response);
            CommonOutput output = input.parseGWResponse(response);
            if (!output.getError().equals("0")) {
                errorMessage = output.getDescription();
                return Constant.ERROR_CODE;
            }

            String original = output.getOriginal();
            Log.d(LOG_TAG, "original : " + original);
            ReasonCancelOrderHandler handler = (ReasonCancelOrderHandler) CommonActivity
                    .parseXMLHandler(new ReasonCancelOrderHandler(), original);
            output = handler.getItem();
            // if (output.getToken() != null && !output.getToken().isEmpty()) {
            // Session.setToken(output.getToken());
            // }
            // if (!output.getErrorCode().equals("0")) {
            // errorMessage = output.getDescription();
            // return Constant.ERROR_CODE;
            // }
            if (output == null) {
                errorMessage = getResources().getString(R.string.exception);
                return Constant.ERROR_CODE;
            }
            if (output.getToken() != null && !output.getToken().isEmpty()) {
                Session.setToken(output.getToken());
            }
            if (output.getErrorCode() != null && !output.getErrorCode().equals("0")) {
                if (output.getErrorCode().equalsIgnoreCase(Constant.INVALID_TOKEN)) {
                    isTimeout = true;
                }

                errorMessage = output.getDescription();
                return Constant.ERROR_CODE;
            }

            lstCancelOrder = handler.getResult();
            if (lstStockModel == null || lstStockModel.isEmpty()) {
                errorMessage = getResources().getString(R.string.no_data);
                return Constant.ERROR_CODE;
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception", e);
            errorMessage = getResources().getString(R.string.exception) + " " + e.toString();
            return Constant.ERROR_CODE;
        }
        return Constant.SUCCESS_CODE;
    }

    private String cancelOrder() {
        try {
            StringBuilder rawData = new StringBuilder();
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_cancelBankPlusOrder");

            rawData.append("<ws:cancelBankPlusOrder>");
            rawData.append("<bankPlusInput>");
            rawData.append("<token>").append(Session.getToken()).append("</token>");
            rawData.append("<orderID>").append(orderBO.getOrderCode()).append("</orderID>");
            rawData.append("<reasonID>").append(cancelOrderReasonId).append("</reasonID>");

            rawData.append("</bankPlusInput>");
            rawData.append("</ws:cancelBankPlusOrder>");

            String envelope = input.buildInputGatewayWithRawData(rawData.toString());
            Log.d(LOG_TAG, envelope);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
                    "mbccs_cancelBankPlusOrder");
            Log.d(LOG_TAG, response);
            CommonOutput output = input.parseGWResponse(response);
            if (!output.getError().equals("0")) {
                errorMessage = output.getDescription();
                return Constant.ERROR_CODE;
            }

            String original = output.getOriginal();
            ReasonCancelOrderHandler handler = (ReasonCancelOrderHandler) CommonActivity
                    .parseXMLHandler(new ReasonCancelOrderHandler(), original);
            output = handler.getItem();

            if (output == null) {
                errorMessage = getResources().getString(R.string.exception);
                return Constant.ERROR_CODE;
            }
            if (output.getToken() != null && !output.getToken().isEmpty()) {
                Session.setToken(output.getToken());
            }
            if (output.getErrorCode() != null && !output.getErrorCode().equals("0")) {
                if (output.getErrorCode().equalsIgnoreCase(Constant.INVALID_TOKEN)) {
                    isTimeout = true;
                }
                errorMessage = output.getDescription();
                return Constant.ERROR_CODE;
            }

            lstCancelOrder = handler.getResult();
            if (lstStockModel == null || lstStockModel.isEmpty()) {
                errorMessage = getResources().getString(R.string.no_data);
                return Constant.ERROR_CODE;
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception", e);
            errorMessage = getResources().getString(R.string.exception) + " " + e.toString();
            return Constant.ERROR_CODE;
        }
        return Constant.SUCCESS_CODE;
    }

    /**
     * @return
     */
    private String saleForChannel() {
        try {
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            if (functionType == FUNCTION_SALE_DEPOSIT) {
                // rawData.append("<ws:exportStockCollaborator>");
                input.addValidateGateway("wscode", "mbccs_exportStockCollaboratorBccs2");
            } else {
                // input.addValidateGateway("wscode",
                // "mbccs_saleForChannel_test");
                input.addValidateGateway("wscode", "mbccs_saleForMobileBccs2");
            }

            String envelope = input.buildInputGatewayWithRawData(buildRawData(false));
            Log.d(LOG_TAG, envelope);
            // String response = input.sendRequest(envelope,
            // Constant.BCCS_GW_URL,
            // getActivity(), "mbccs_saleForChannel_test");
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(), "mbccs_saleForMobileBccs2");
            Log.d(LOG_TAG, response);
            CommonOutput output = input.parseGWResponse(response);
            if (!output.getError().equals("0")) {
                errorMessage = output.getDescription();
                return Constant.ERROR_CODE;
            }

            String original = output.getOriginal();
            VSAMenuHandler handler = (VSAMenuHandler) CommonActivity.parseXMLHandler(new VSAMenuHandler(), original);
            output = handler.getItem();

            if (output == null) {
                errorMessage = getResources().getString(R.string.exception);
                return Constant.ERROR_CODE;
            }
            if (output.getToken() != null && !output.getToken().isEmpty()) {
                Session.setToken(output.getToken());
            }
            if (!"0".equals(output.getErrorCode())) {
                if (output.getErrorCode().equalsIgnoreCase(Constant.INVALID_TOKEN)) {
                    isTimeout = true;
                }
                errorMessage = output.getDescription();
                if (errorMessage == null || errorMessage.trim().isEmpty()) {
                    errorMessage = output.getErrorCode();
                }
                return Constant.ERROR_CODE;
            }

            return Constant.SUCCESS_CODE;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception", e);
            errorMessage = getResources().getString(R.string.exception) + " " + e.toString();
            return Constant.ERROR_CODE;
        }
    }

    /**
     * Ham build danh sach hang hoa gui len
     *
     * @param isSMS : neu trong truong hop SMS, khong truyen Token
     * @return
     * @throws Exception
     */
    private String buildRawData(Boolean isSMS) {
        StringBuilder rawData = new StringBuilder();
        if (functionType == FUNCTION_SALE_DEPOSIT) {
            rawData.append("<ws:exportStockCollaborator>");
        } else {
            rawData.append("<ws:saleForMobile>");
        }

        rawData.append("<input>");
        if (functionType == FUNCTION_SALE_RETAIL) {
            BhldObject obj = (BhldObject) spnBHLD.getSelectedItem();
            recordWorkId = obj.getRecordWorkId();
        }

        if (programSaleBeanMain != null && programSaleBeanMain.getProgram_code() != null
                && !programSaleBeanMain.getProgram_code().isEmpty()) {
            rawData.append("<saleProgramId>" + programSaleBeanMain.getProgram_id());
            rawData.append("</saleProgramId>");
        }
//        if (lnVAS.getVisibility() == View.VISIBLE) {
//            Spin vas = (Spin) spiVas.getSelectedItem();
//            if (!vas.getId().equals("-1")) {
//                rawData.append("<vasCode>" + vas.getId());
//                rawData.append("</vasCode>");
//                rawData.append("<vasPhone>" + CommonActivity.getStardardIsdn(edtVasPhone.getText().toString()));
//                rawData.append("</vasPhone>");
//            }
//        }

        HashMap<String, Object> param = new HashMap<String, Object>();
        if (collCode != null && !collCode.isEmpty()) {
            param.put("collStaffId", collId);
            param.put("channelCode", collCode);
        }
        // Neu la BHLD thi truyen false
        Long staffChannelType = 14L;
        if (functionType == FUNCTION_SALE_RETAIL) {
            if (!Session.loginUser.isStaff()) {
                param.put("sellerType", "COLL_RETAIL");
            } else {
                param.put("sellerType", "RETAIL");
            }

        } else if (functionType == FUNCTION_SALE_BHLD || functionType == FUNCTION_SALING) {
            param.put("sellerType", "COLL");
        } else if (functionType == FragmentSaleSaling.FUNCTION_SALE_PROMOTION) {
            param.put("sellerType", "PROMOTION");
        }
        if (!isSMS) {
            param.put("token", Session.getToken());
        } else {
            param.put("token", "");
        }

        StringBuilder strSaleTransInfoMin = new StringBuilder();

        if (functionType == FUNCTION_SALE_RETAIL || functionType == FragmentSaleSaling.FUNCTION_SALE_PROMOTION) {
            strSaleTransInfoMin.append("<company>");
            strSaleTransInfoMin.append(edtCusCompany.getText().toString());
            strSaleTransInfoMin.append("</company>");

            strSaleTransInfoMin.append("<custName>");
            strSaleTransInfoMin.append(edtCusName.getText().toString());
            strSaleTransInfoMin.append("</custName>");

            strSaleTransInfoMin.append("<email>");
            strSaleTransInfoMin.append(edtEmail.getText().toString());
            strSaleTransInfoMin.append("</email>");

            strSaleTransInfoMin.append("<tin>");
            strSaleTransInfoMin.append(edtCusTin.getText().toString());
            strSaleTransInfoMin.append("</tin>");

            strSaleTransInfoMin.append("<telNumber>");
            strSaleTransInfoMin.append(edtBankplusMobileDialog.getText().toString());
            strSaleTransInfoMin.append("</telNumber>");

        }
        if (payMethod == PAY_METHOD_BANKPLUS) {
            strSaleTransInfoMin.append("<payBankAccount>");

            strSaleTransInfoMin.append("<bankCode>");
            strSaleTransInfoMin.append(bankCode);
            strSaleTransInfoMin.append("</bankCode>");
            if (edtBankplusMobileDialog != null) {
                strSaleTransInfoMin.append("<isdn>");
                strSaleTransInfoMin.append(edtBankplusMobileDialog.getText().toString());
                strSaleTransInfoMin.append("</isdn>");
            }

            strSaleTransInfoMin.append("</payBankAccount>");
        }
        Boolean receiveInvoice = true;
        if (cbxReceiveInvoice != null) {
            receiveInvoice = cbxReceiveInvoice.isChecked();
        }

        strSaleTransInfoMin.append("<receiveInvoice>");
        strSaleTransInfoMin.append(receiveInvoice);
        strSaleTransInfoMin.append("</receiveInvoice>");
        if (functionType == FUNCTION_SALE_BHLD) {

            strSaleTransInfoMin.append("<recordWorkId>");
            strSaleTransInfoMin.append(recordWorkId);
            strSaleTransInfoMin.append("</recordWorkId>");
        }


        // bo sung them vascode truong hop ban dut 21/092016
        if (lnVAS.getVisibility() == View.VISIBLE) {
            Spin vas = (Spin) spiVas.getSelectedItem();
            if (!vas.getId().equals("-1")) {
                strSaleTransInfoMin.append("<dataPackCode>" + vas.getId());
                strSaleTransInfoMin.append("</dataPackCode>");
                strSaleTransInfoMin.append("<telNumber>" + CommonActivity.getStardardIsdn(edtVasPhone.getText().toString()));
                strSaleTransInfoMin.append("</telNumber>");
            }

        }


        param.put("saleTransInfoMin", strSaleTransInfoMin);
        param.put("payMethod", payMethod);
        ArrayList<String> xmlStockModel = new ArrayList<String>();
        String stockTag = "";
        String serialXMLTag = "";
        if (functionType == FUNCTION_SALE_DEPOSIT) {
            stockTag = "lstProductOfferingSM";
            serialXMLTag = "listStockTransSerialDTOs";
        } else {
            stockTag = "stockLst";
            serialXMLTag = "serialPairLst";
        }
        for (StockModel item : lstStockModel) {
            if (item.getQuantitySaling() > 0) {
                HashMap<String, Object> stockModelParam = new HashMap<String, Object>();
                if (item.getCheckSerial() != null && item.getCheckSerial().compareTo(1L) == 0) {
                    // Neu la mat hang co serial
                    stockModelParam.put("bySerial", "true");
                    Boolean isStockHandset = false;
                    if (item.isStockHandset()) {
                        isStockHandset = true;
                    }
                    ArrayList<Serial> lstSerial = SaleCommons.getRangeSerial(
                            SaleCommons.sortSerial(item.getSelectedSerial(), isStockHandset), isStockHandset);
                    ArrayList<String> xmlSerial = new ArrayList<String>();
                    for (Serial serial : lstSerial) {
                        StringBuilder serialStr = new StringBuilder();
                        serialStr.append("<fromSerial>");
                        serialStr.append(serial.getFromSerial());
                        serialStr.append("</fromSerial>");
                        serialStr.append("<toSerial>");
                        serialStr.append(serial.getToSerial());
                        serialStr.append("</toSerial>");
                        xmlSerial.add(serialStr.toString());
                    }
                    for (Serial serial : lstSerial) {
                        stockModelParam.put("toSerial", serial.getToSerial());
                    }

                    stockModelParam.put(serialXMLTag, xmlSerial);

                } else {
                    stockModelParam.put("bySerial", "false");

                }

                stockModelParam.put("quantity", item.getQuantitySaling() + "");
                stockModelParam.put("stateId", "1");
                stockModelParam.put("productOfferingCode", item.getStockModelCode());
                stockModelParam.put("code", item.getStockModelCode());
                stockModelParam.put("productOfferingId", item.getStockModelId() + "");

                param.put(stockTag, BCCSGateway.buildXMLFromHashmap(stockModelParam));
                xmlStockModel.add(BCCSGateway.buildXMLFromHashmap(stockModelParam));
            }
        }
        param.put(stockTag, xmlStockModel);
        rawData.append(BCCSGateway.buildXMLFromHashmap(param));
        rawData.append("</input>");
        if (functionType == FUNCTION_SALE_DEPOSIT) {
            rawData.append("</ws:exportStockCollaborator>");
        } else {
            rawData.append("</ws:saleForMobile>");
        }
        return rawData.toString();
    }

    /**
     * Ban hang cho CTV/DB dua tren don dat hang va thanh toan qua Bankplus
     */
    private String saleByOrder() {
        try {
            StringBuilder rawData = new StringBuilder();
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_createBankPlusSaleTrans");
            rawData.append("<ws:createBankPlusSaleTrans>");
            rawData.append("<bankPlusInput>");
            rawData.append("<token>").append(Session.getToken()).append("</token>");
            rawData.append("<orderID>").append(orderBO.getOrderCode()).append("</orderID>");
            for (StockModel item : lstStockModel) {
                rawData.append("<lstStockModel>");
                rawData.append("<stockModelId>").append(item.getStockModelId()).append("</stockModelId>");
                if (item.isCheckSerial()) {
                    rawData.append("<checkSerial>").append("1").append("</checkSerial>");
                } else {
                    rawData.append("<checkSerial>").append("2").append("</checkSerial>");
                }
                rawData.append("<transOrderId>").append(item.getTransOrderDetailId()).append("</transOrderId>");
                ArrayList<Serial> lstSerial = SaleCommons.getRangeSerial(
                        SaleCommons.sortSerial(item.getSelectedSerial(), item.isStockHandset()), item.isStockHandset());
                for (Serial serial : lstSerial) {
                    rawData.append("<listSerialPair>");
                    rawData.append("<fromSerial>").append(serial.getFromSerial()).append("</fromSerial>");
                    rawData.append("<toSerial>").append(serial.getToSerial()).append("</toSerial>");
                    rawData.append("</listSerialPair>");
                }
                rawData.append("</lstStockModel>");
            }
            rawData.append("</bankPlusInput>");
            rawData.append("</ws:createBankPlusSaleTrans>");
            String envelope = input.buildInputGatewayWithRawData(rawData.toString());
            Log.d(LOG_TAG, envelope);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
                    "mbccs_createBankPlusSaleTrans");
            Log.d(LOG_TAG, response);
            CommonOutput output = input.parseGWResponse(response);
            if (!output.getError().equals("0")) {
                errorMessage = output.getDescription();
                return Constant.ERROR_CODE;
            }
            String original = output.getOriginal();
            VSAMenuHandler handler = (VSAMenuHandler) CommonActivity.parseXMLHandler(new VSAMenuHandler(), original);
            output = handler.getItem();
            // if (output.getToken() != null && !output.getToken().isEmpty()) {
            // Session.setToken(output.getToken());
            // }
            // if (!output.getErrorCode().equals("0")) {
            // errorMessage = output.getErrorCode();
            // return Constant.ERROR_CODE;
            // }
            if (output == null) {
                errorMessage = getResources().getString(R.string.exception);
                return Constant.ERROR_CODE;
            }
            if (output.getToken() != null && !output.getToken().isEmpty()) {
                Session.setToken(output.getToken());
            }
            if (output.getErrorCode() != null && !output.getErrorCode().equals("0")) {
                if (output.getErrorCode().equalsIgnoreCase(Constant.INVALID_TOKEN)) {
                    isTimeout = true;
                }
                errorMessage = output.getDescription();
                if (errorMessage == null || errorMessage.trim().isEmpty()) {
                    errorMessage = output.getErrorCode();
                }
                return Constant.ERROR_CODE;
            }
            return Constant.SUCCESS_CODE;

        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception", e);
            errorMessage = getActivity().getResources().getString(R.string.exception) + " " + e.toString();
            return Constant.ERROR_CODE;
        }
    }

    /**
     * Tinh tien
     */
    private SaleOutput calMoneyForSale() {
        try {

            StringBuilder rawData = new StringBuilder();
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_addGoodForMobileBccs2");
            rawData.append("<ws:addGoodForMobile>");
            rawData.append("<input>");
            rawData.append("<saleTransInfoMin>");

            rawData.append("<note>");
            rawData.append("mbccs tinh tien");
            rawData.append("</note>");
            rawData.append("</saleTransInfoMin>");

            if (programSaleBeanMain != null && programSaleBeanMain.getProgram_id() != null && !programSaleBeanMain.getProgram_id().isEmpty()) {
                rawData.append("<saleProgramId>" + programSaleBeanMain.getProgram_id());
                rawData.append("</saleProgramId>");

            }

            HashMap<String, Object> param = new HashMap<String, Object>();
            if (collCode != null && !collCode.isEmpty()) {
                param.put("collStaffId", collId);
            }
            param.put("payMethod", 1);
            // Neu la BHLD thi truyen false
            if (functionType == FUNCTION_SALE_BHLD) {
                param.put("isCheckOwner", "false");
            } else {
                param.put("isCheckOwner", "true");
            }

            // Ban dut 2, ban dat coc 1
            if (functionType == FUNCTION_SALE_RETAIL) {
                param.put("sellerType", "RETAIL");
            } else {
                param.put("sellerType", "COLL");
            }
            param.put("token", Session.getToken());
            // if (functionType == FUNCTION_SALE_RETAIL) {
            // // Neu la ban le, truyen them cac them so khach hang
            // param.put("cusCompany", edtCusCompany.getText().toString());
            // param.put("cusTin", edtCusTin.getText().toString());
            // param.put("custName", edtCusName.getText().toString());
            // }
            ArrayList<String> xmlStockModel = new ArrayList<String>();
            for (StockModel item : lstStockModel) {
                if (item.getQuantitySaling() > 0) {
                    HashMap<String, Object> stockModelParam = new HashMap<String, Object>();
                    if (item.getCheckSerial() != null && item.getCheckSerial().compareTo(1L) == 0) {
                        // Neu la mat hang co serial
                        stockModelParam.put("bySerial", "true");
                        Boolean isStockHandset = false;
                        if (item.getStockModelCode() != null
                                && item.getStockModelCode().equalsIgnoreCase("stock_handset")) {
                            isStockHandset = true;
                        }
                        ArrayList<Serial> lstSerial = SaleCommons.getRangeSerial(
                                SaleCommons.sortSerial(item.getSelectedSerial(), isStockHandset), isStockHandset);
                        ArrayList<String> xmlSerial = new ArrayList<String>();
                        for (Serial serial : lstSerial) {
                            StringBuilder serialStr = new StringBuilder();
                            serialStr.append("<fromSerial>");
                            serialStr.append(serial.getFromSerial());
                            serialStr.append("</fromSerial>");
                            serialStr.append("<toSerial>");
                            serialStr.append(serial.getToSerial());
                            serialStr.append("</toSerial>");
                            xmlSerial.add(serialStr.toString());
                        }
                        for (Serial serial : lstSerial) {
                            stockModelParam.put("toSerial", serial.getToSerial());
                        }
                        stockModelParam.put("serialPairLst", xmlSerial);

                    } else {
                        stockModelParam.put("bySerial", "false");

                    }

                    stockModelParam.put("quantity", item.getQuantitySaling() + "");
                    // stockModelParam.put("stockModelCode",
                    // item.getStockModelCode());
                    stockModelParam.put("productOfferingId", item.getStockModelId() + "");
                    // stockModelParam.put("telecomServiceId", selectedTelecom
                    // + "");
                    param.put("stockLst", input.buildXMLFromHashmap(stockModelParam));
                    xmlStockModel.add(input.buildXMLFromHashmap(stockModelParam));
                }
            }
            param.put("stockLst", xmlStockModel);
            rawData.append(input.buildXMLFromHashmap(param));
            rawData.append("</input>");
            rawData.append("</ws:addGoodForMobile>");

            String envelope = input.buildInputGatewayWithRawData(rawData.toString());
            Log.e(LOG_TAG, envelope);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(), "mbccs_addGoodForMobileBccs2");
            Log.e(LOG_TAG, response);
            CommonOutput output = input.parseGWResponse(response);
            if (!output.getError().equals("0")) {
                errorMessage = output.getDescription();

                return null;
            }
            String original = output.getOriginal();
            Serializer serializer = new Persister();
            SaleOutput saleOutput = serializer.read(SaleOutput.class, original);
            if (saleOutput == null) {
                saleOutput = new SaleOutput();
                saleOutput.setDescription(getActivity().getString(R.string.no_return_from_system));
                saleOutput.setErrorCode(Constant.ERROR_CODE);
                return saleOutput;
            } else {
                return saleOutput;
            }

        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception", e);
            // errorMessage = getResources().getString(R.string.exception)
            // + e.toString();
            SaleOutput saleOutput = new SaleOutput();
            saleOutput.setErrorCode(Constant.ERROR_CODE);
            saleOutput.setDescription(MainActivity.getInstance().getString(R.string.exception));
            return saleOutput;
        }

    }

    private void initSpinnerCTBH() {
        LookupStockDAL dal = null;
        try {
            arrProgramSaleBeans = new ArrayList<ProgramSaleBean>();
            dal = new LookupStockDAL(getActivity());
            dal.open();
            arrProgramSaleBeans = dal.getProgramSale();
            dal.close();

            arrProgramSaleBeans.add(0, new ProgramSaleBean("", "", getString(R.string.chonctbh), "", "", ""));

            if (arrProgramSaleBeans != null && !arrProgramSaleBeans.isEmpty()) {

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                        R.layout.spinner_item);
                for (ProgramSaleBean programSaleBean : arrProgramSaleBeans) {
                    adapter.add(programSaleBean.getProgram_name());
                }
                spn_ctbh.setAdapter(adapter);
            }

        } catch (Exception e) {
            Log.e("initSpinnerCTBH", "Exception", e);
            if (dal != null) {
                try {
                    dal.close();
                } catch (Exception e2) {
                }
            }
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

    /**
     *
     */
    private void initSpinner() {
        try {
            final ArrayList<TelecomServiceObject> lstService = TelecomServiceBusiness
                    .getAllTelecomService(getActivity());
            if (lstService != null && !lstService.isEmpty()) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                        R.layout.spinner_item);

                for (TelecomServiceObject telecomServiceObject : lstService) {
                    adapter.add(telecomServiceObject.getName());
                }
                spiService.setAdapter(adapter);
                spiService.setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                        if (functionType == FUNCTION_SALE_BY_ORDER) {
                            return;
                        }
                        selectedTelecom = lstService.get(position).getTelecomServiceId();
                        if (functionType == FUNCTION_SALING || functionType == FUNCTION_SALE_DEPOSIT) {
                            if (collId == null) {
                                if (functionType == FUNCTION_SALING || functionType == FUNCTION_SALE_DEPOSIT) {
                                    if (collId == null) {
                                        FragmentChooseChannel fragment = new FragmentChooseChannel();
                                        fragment.setTargetFragment(FragmentSaleSaling.this, REQUEST_CHOOSE_CHANNEL);
                                        ReplaceFragment.replaceFragment(getActivity(), fragment, false);
                                    }
                                }
                                return;
                            }
                        }
                        SearchStockAsync search = new SearchStockAsync(getActivity());
                        search.execute();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "initSpinner", e);
        }
    }

    /**
     * @author huypq15
     */
    private class SearchStockAsync extends AsyncTask<String, String, String> {
        private ProgressDialog progress;
        private Activity activity;

        public SearchStockAsync(Activity activity) {
            try {
                hideMoney();
                if (adapter != null) {
                    lstStockModel.clear();
                    adapter.notifyDataSetChanged();
                }
                isViewStockModel = false;
                btnViewStockModel.setText(getResources().getString(R.string.choosed));
                this.activity = activity;


            } catch (Exception e) {
                Log.e(LOG_TAG, "initSpinner", e);
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.progress = new ProgressDialog(getActivity());

            this.progress.setMessage(getActivity().getString(R.string.searching_stock_model));
            this.progress.setCancelable(false);

            this.progress.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return searchStock();
        }

        @Override
        protected void onPostExecute(String result) {

            progress.dismiss();


            try {

                if (Constant.SUCCESS_CODE.equals(result)) {
                    // Xem kho thanh cong, hien thi danh sach hang hoa
                    view.findViewById(R.id.lnStockModel).setVisibility(View.VISIBLE);
                    adapter = new StockModelAdapter(getActivity(), lstStockModel, FragmentSaleSaling.this,
                            FragmentSaleSaling.this, FragmentSaleSaling.this, functionType == FUNCTION_SALE_BY_ORDER);
                    lvStockModel.setAdapter(adapter);
                } else if (Constant.ERROR_CODE.equals(result)) {

                    view.findViewById(R.id.lnStockModel).setVisibility(View.INVISIBLE);
                    String message = errorMessage;
                    String title = getString(R.string.app_name);
                    Dialog dialog = CommonActivity.createAlertDialog(activity, message, title);
                    dialog.show();
                }
            } catch (Exception e) {
                Log.e(LOG_TAG, "initSpinner", e);
            }

        }
    }

    private class GetOrderDetailAsync extends AsyncTask<String, String, String> {
        ProgressDialog progress;
        private Activity activity;

        public GetOrderDetailAsync(Activity activity) {
            try {
                hideMoney();
                this.activity = activity;
                this.progress = new ProgressDialog(this.activity);
                this.progress.setMessage(getResources().getString(R.string.searching_order_detail));
                progress.setCancelable(false);
                if (!this.progress.isShowing()) {
                    this.progress.show();
                }
            } catch (Exception e) {
                Log.e(LOG_TAG, "initSpinner", e);
            }

        }

        @Override
        protected String doInBackground(String... params) {
            return searchOrderDetail();
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                this.progress.dismiss();
                if (Constant.SUCCESS_CODE.equals(result)) {
                    // Xem kho thanh cong, hien thi danh sach hang hoa
                    view.findViewById(R.id.lnStockModel).setVisibility(View.VISIBLE);
                    ArrayList<StockModel> lstTmp = LookUpStockBusiness.lookUpStockForBankplus(activity, lstStockModel);
                    for (StockModel tmp : lstTmp) {
                        for (StockModel item : lstStockModel) {
                            if (tmp.getStockModelId().compareTo(item.getStockModelId()) == 0) {
                                item.setQuantityIssue(tmp.getQuantityIssue());
                                item.setCheckSerial(tmp.getCheckSerial());
                            }
                        }
                    }
                    adapter = new StockModelAdapter(getActivity(), lstStockModel, FragmentSaleSaling.this,
                            FragmentSaleSaling.this, FragmentSaleSaling.this, functionType == FUNCTION_SALE_BY_ORDER);
                    lvStockModel.setAdapter(adapter);
                } else if (Constant.ERROR_CODE.equals(result)) {
                    OnClickListener onclick = null;
                    if (isTimeout) {
                        LoginDialog dialog = new LoginDialog(getActivity(),
                                permission);
                        dialog.show();
                        isTimeout = false;
                        return;
                    }

                    btnSale.setVisibility(View.GONE);
                    btnViewStockModel.setVisibility(View.GONE);
                    view.findViewById(R.id.lnStockModel).setVisibility(View.INVISIBLE);
                    String message = errorMessage;
                    String title = getString(R.string.app_name);
                    Dialog dialog = CommonActivity.createAlertDialog(activity, message, title, onclick);
                    dialog.show();
                }
            } catch (Exception e) {
                Log.e(LOG_TAG, "initSpinner", e);
            }

        }
    }

    private class CancelOrderAsy extends AsyncTask<String, String, String> {
        ProgressDialog progress;
        private Activity activity;

        public CancelOrderAsy(Activity activity) {
            try {
                this.activity = activity;
                this.progress = new ProgressDialog(this.activity);
                this.progress.setMessage(getResources().getString(R.string.processing));
                progress.setCancelable(false);
                if (!this.progress.isShowing()) {
                    this.progress.show();
                }
            } catch (Exception e) {
                Log.e(LOG_TAG, "initSpinner", e);
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return cancelOrder();
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                this.progress.dismiss();
                if (Constant.SUCCESS_CODE.equals(result)) {
                    // Huy don hang thanh cong, tro ve man hinh danh sach don
                    // hang
                    dialogCancelOrder.dismiss();
                    Toast.makeText(activity, activity.getResources().getString(R.string.cancel_order_success),
                            Toast.LENGTH_SHORT).show();
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, null);
                    getActivity().onBackPressed();
                } else if (Constant.ERROR_CODE.equals(result)) {
                    OnClickListener onclick = null;
                    if (isTimeout) {
                        LoginDialog dialog = new LoginDialog(getActivity(),
                                permission);
                        dialog.show();
                        isTimeout = false;
                        return;
                    }
                    String message = errorMessage;
                    String title = getString(R.string.app_name);
                    Dialog dialog = CommonActivity.createAlertDialog(activity, message, title, onclick);
                    dialog.show();
                }
            } catch (Exception e) {
                Log.e(LOG_TAG, "initSpinner", e);
            }

        }
    }

    private class GetCancelOrderAsy extends AsyncTask<String, String, String> {
        ProgressDialog progress;
        private Activity activity;

        public GetCancelOrderAsy(Activity activity) {
            try {
                this.activity = activity;
                this.progress = new ProgressDialog(this.activity);
                this.progress.setMessage(getResources().getString(R.string.searching_order_detail));
                progress.setCancelable(false);
                if (!this.progress.isShowing()) {
                    this.progress.show();
                }
            } catch (Exception e) {
                Log.e(LOG_TAG, "initSpinner", e);
            }

        }

        @Override
        protected String doInBackground(String... params) {
            return getReasonCancelOrder();
        }

        @Override
        protected void onPostExecute(String result) {

            Log.e(LOG_TAG, "result : " + result);
            try {
                this.progress.dismiss();
                if (Constant.SUCCESS_CODE.equals(result)) {
                    // Lay danh sach ly do huy don hang thanh cong
                    dialogCancelOrder = new Dialog(activity);
                    if (cusDialog != null) {
                        CommonActivity.hideSoftKeyboard(MainActivity.getInstance());
                        cusDialog.dismiss();
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    View dialogView = inflater.inflate(R.layout.sale_cancel_order_dialog, null);
                    builder.setView(dialogView);

                    dialogCancelOrder = builder.create();
                    btnCancelOrder = (Button) dialogView.findViewById(R.id.btnOk);
                    btnCancelOrder.setOnClickListener(FragmentSaleSaling.this);
                    btnCancelCancelDialog = (Button) dialogView.findViewById(R.id.btnCancel);
                    btnCancelCancelDialog.setOnClickListener(FragmentSaleSaling.this);
                    Spinner spiReason = (Spinner) dialogView.findViewById(R.id.spnReason);
                    ArrayAdapter<String> cancelAdapter = new ArrayAdapter<String>(activity,
                            R.layout.simple_list_item_single_row, R.id.text1);
                    for (ReasonCancleOrder item : lstCancelOrder) {
                        cancelAdapter.add(item.getReasonName());
                    }
                    spiReason.setAdapter(cancelAdapter);
                    spiReason.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                            cancelOrderReasonId = lstCancelOrder.get(arg2).getReasonId();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }
                    });
                    dialogCancelOrder.show();
                } else if (Constant.ERROR_CODE.equals(result)) {
                    OnClickListener onclick = null;
                    if (isTimeout) {
                        LoginDialog dialog = new LoginDialog(getActivity(),
                                permission);
                        dialog.show();
                        isTimeout = false;
                        return;
                    }
                    btnSale.setVisibility(View.GONE);
                    btnViewStockModel.setVisibility(View.GONE);
                    view.findViewById(R.id.lnStockModel).setVisibility(View.INVISIBLE);
                    String message = errorMessage;
                    String title = getString(R.string.app_name);
                    Dialog dialog = CommonActivity.createAlertDialog(activity, message, title, onclick);
                    dialog.show();
                }
            } catch (Exception e) {
                Log.e(LOG_TAG, "initSpinner", e);
            }

        }
    }

    private class SaleByOrderAsy extends AsyncTask<String, String, String> {
        ProgressDialog progress;
        private Activity activity;

        public SaleByOrderAsy(Activity activity) {
            try {
                this.activity = activity;
                this.progress = new ProgressDialog(this.activity);
                this.progress.setMessage(getResources().getString(R.string.creating_sale_trans));
                progress.setCancelable(false);
                if (!this.progress.isShowing()) {
                    this.progress.show();
                }
            } catch (Exception e) {
                Log.e(LOG_TAG, "initSpinner", e);
            }

        }

        @Override
        protected String doInBackground(String... params) {
            return saleByOrder();
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                this.progress.dismiss();
                if (Constant.SUCCESS_CODE.equals(result)) {
                    // Ban hang thanh cong
                    hideMoney();
                    // GetMaxOraRowScnDal requestSync = new GetMaxOraRowScnDal(
                    // activity);
                    // requestSync.open();
                    // ArrayList<String> lstTabelName = new ArrayList<String>();
                    // lstTabelName.add("stock_model");
                    // lstTabelName.add("stock_card");
                    // lstTabelName.add("stock_kit");
                    // lstTabelName.add("stock_handset");
                    // lstTabelName.add("stock_total");
                    // lstTabelName.add("stock_sim");
                    // requestSync.updateSyncStatus(lstTabelName);
                    // requestSync.close();
                    // Cap nhat trang thai hang hoa
                    LookUpStockBusiness.updateStockTotal(getActivity(), lstStockModel);
                    Toast.makeText(activity, getResources().getString(R.string.create_sale_trans_success),
                            Toast.LENGTH_SHORT).show();
                    Intent i = new Intent();

                    i.putExtra("orderId", orderBO.getOrderCode());
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
                    getActivity().onBackPressed();

                } else if (Constant.ERROR_CODE.equals(result)) {
                    OnClickListener onclick = null;
                    if (isTimeout) {
                        LoginDialog dialog = new LoginDialog(getActivity(),
                                permission);
                        dialog.show();
                        isTimeout = false;
                        return;
                    }

                    String message = errorMessage;
                    String title = getString(R.string.app_name);
                    Dialog dialog = CommonActivity.createAlertDialog(activity, message, title, onclick);
                    dialog.show();
                }
            } catch (Exception e) {
                Log.e(LOG_TAG, "initSpinner", e);
            }

        }
    }

    /**
     * @author huypq15
     */
    private class SaleAsync extends AsyncTask<String, String, String> {
        ProgressDialog progress;
        private Activity activity;

        public SaleAsync(Activity activity) {
            try {
                this.activity = activity;
                this.progress = new ProgressDialog(this.activity);
                this.progress.setMessage(getResources().getString(R.string.processing));
                progress.setCancelable(false);
                if (!this.progress.isShowing()) {
                    this.progress.show();
                }
            } catch (Exception e) {
                Log.e(LOG_TAG, "initSpinner", e);
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return saleForChannel();
        }

        @Override
        protected void onPostExecute(String result) {
            this.progress.dismiss();
            if (Constant.SUCCESS_CODE.equals(result)) {
                // Ban hang thanh cong, hien thi dialog thong bao va reset hang
                // hoa
                try {
                    if (getActivity() instanceof FragmentCusCareByDay) {
                        if (FragmentCusCareByDay.careCus != Constant.BOC2.STATUS_CARE_AND_SALE) {
                            FragmentCusCareByDay.careCus = Constant.BOC2.STATUS_CARE_AND_SALE;
                        }
                    }
                    hideMoney();
                    if (functionType == FUNCTION_SALING) {
                        spiVas.setSelection(0);
                    }
                    GetMaxOraRowScnDal requestSync = new GetMaxOraRowScnDal(activity);
                    requestSync.open();
                    ArrayList<String> lstTabelName = new ArrayList<String>();
                    lstTabelName.add("stock_model");
                    lstTabelName.add("stock_card");
                    lstTabelName.add("stock_kit");
                    lstTabelName.add("stock_handset");
                    lstTabelName.add("stock_total");
                    lstTabelName.add("stock_sim");
                    requestSync.updateSyncStatus(lstTabelName);
                    requestSync.close();

                    if (cusDialog != null && cusDialog.isShowing()) {
                        CommonActivity.hideSoftKeyboard(MainActivity.getInstance());
                        cusDialog.dismiss();
                    }
                    // Cap nhat trang thai hang hoa
                    LookUpStockBusiness.updateStockTotal(getActivity(), lstStockModel);

                    String msg = "";
                    if (payMethod == PAY_METHOD_BANKPLUS) {
                        msg = getString(R.string.saleBankplusSuccess);
                    } else {
                        msg = getString(R.string.sale_success);
                    }
                    payMethod = PAY_METHOD_MONEY;
                    Dialog alert = CommonActivity.createAlertDialog(activity, msg, getString(R.string.app_name));
                    alert.show();
                    SearchStockAsync search = new SearchStockAsync(activity);
                    search.execute();
                } catch (Exception e) {
                    Log.e(LOG_TAG, "Exception", e);
                }

            } else if (Constant.ERROR_CODE.equals(result)) {
                OnClickListener onclick = null;
                if (isTimeout) {
                    onclick = new OnClickListener() {

                        @Override
                        public void onClick(View v) {
//                            Intent intent = new Intent(getActivity(), LoginActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
//                            startActivity(intent);
//                            getActivity().finish();
                            LoginDialog dialog = new LoginDialog(getActivity(),
                                    permission);
                            dialog.show();
                            isTimeout = false;
                            return;
                        }
                    };
                }
                String message = errorMessage;
                String title = getString(R.string.app_name);
                Dialog dialog = CommonActivity.createAlertDialog(activity, message, title, onclick);
                dialog.show();
            }
        }
    }

    /**
     * @author huypq15
     */
    private class CalMoneyForSaleAsy extends AsyncTask<Void, Void, SaleOutput> {
        private ProgressDialog progress;

        public CalMoneyForSaleAsy() {
            isCalculatingMoney();

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.progress = new ProgressDialog(getActivity());
            this.progress.setMessage(getResources().getString(R.string.processing));
            this.progress.setCancelable(false);
            this.progress.show();
        }

        @Override
        protected SaleOutput doInBackground(Void... params) {
            return calMoneyForSale();

        }

        @Override
        protected void onPostExecute(SaleOutput result) {
            if (this.progress != null && this.progress.isShowing())
                this.progress.dismiss();
            showMoney(result);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == REQUEST_CHOOSE_SERIAL) {
                if (resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    if (bundle == null) {
                        return;
                    }
                    StockModel stockModel = (StockModel) bundle.getSerializable("stockModel");

                    if (stockModel == null) {
                        return;
                    }
                    for (StockModel item : lstStockModel) {
                        if (item.getStockModelId().compareTo(stockModel.getStockModelId()) == 0) {
                            item.setSelectedSerial(stockModel.getSelectedSerial());
                            item.setLstSerial(stockModel.getLstSerial());
                            adapter.notifyDataSetChanged();
                            break;
                        }
                    }
                }
            } else if (requestCode == REQUEST_CHOOSE_CHANNEL) {
                if (resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    if (bundle == null) {
                        return;
                    }
                    Staff staff = (Staff) data.getExtras().getSerializable("staff");
                    if (staff != null) {

                        if (isCollaborator(staff)) {
                            if (staff.getX() == null || staff.getY() == null || "0".equals(staff.getX())
                                    || "0".equals(staff.getY())) {
                                CommonActivity.createAlertDialog(getActivity(),
                                        getActivity().getResources().getString(R.string.staff_have_not_locatation),
                                        getActivity().getResources().getString(R.string.app_name)).show();
                                return;
                            }
                        }

                        tvChooseChannel.setText(staff.getName() + " - " + staff.getStaffCode());
                        collId = staff.getStaffId();
                        collName = staff.getName();
                        collCode = staff.getStaffCode();
                        lstStockModel.clear();
//                        if (searchStock != null) {
//                            searchStock.cancel(true);
//                        }
                        SearchStockAsync as = new SearchStockAsync(getActivity());
                        as.execute();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCancelStockModelListener(StockModel stockModel) {
        for (StockModel item : lstStockModel) {
            if (item.getStockModelId().equals(stockModel.getStockModelId())) {
                item.getLstSerial().addAll(item.getSelectedSerial());
                item.setSelectedSerial(new ArrayList<String>());
                item.setQuantitySaling(0L);
                if (isViewStockModel) {
                    btnViewStockModel.setText(getResources().getString(R.string.all));
                } else {
                    btnViewStockModel.setText(getResources().getString(R.string.choosed));
                }
                adapter.filter(isViewStockModel, lstStockModel);
                break;
            }
        }
        Long count = countMoney();
        if (count > 0) {
            if (functionType == FUNCTION_SALING || functionType == FUNCTION_SALE_BHLD) {
                if (calMoneyForSaleAsy != null) {
                    calMoneyForSaleAsy.cancel(true);
                }
                if (CommonActivity.isNetworkConnected(getActivity())) {
                    calMoneyForSaleAsy = new CalMoneyForSaleAsy();
                    calMoneyForSaleAsy.execute();
                } else {
                    view.findViewById(R.id.prbMoney).setVisibility(View.GONE);
                    view.findViewById(R.id.lnMoney).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.lnRealMoney).setVisibility(View.VISIBLE);
                    tvRealMoney.setText(getResources().getString(R.string.money_total) + ": " + count);
                    view.findViewById(R.id.lnDiscount).setVisibility(View.GONE);
                }
            } else {
                view.findViewById(R.id.prbMoney).setVisibility(View.GONE);
                view.findViewById(R.id.lnMoney).setVisibility(View.VISIBLE);
                view.findViewById(R.id.lnRealMoney).setVisibility(View.VISIBLE);
                tvRealMoney.setText(getResources().getString(R.string.money_total) + ": " + count);
                view.findViewById(R.id.lnDiscount).setVisibility(View.GONE);
            }
        } else {
            hideMoney();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == btnSale) {

            if (functionType == FUNCTION_SALE_BY_ORDER) {
                for (StockModel item : lstStockModel) {
                    if (!item.isFullySerial()) {
                        Toast.makeText(getActivity(), getResources().getString(R.string.mustInputAllSerial),
                                Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                if (!CommonActivity.isNetworkConnected(getActivity())) {
                    CommonActivity.createAlertDialog(getActivity(), R.string.errorNetwork, R.string.app_name);
                    return;
                }

                OnClickListener saleClick = new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        SaleByOrderAsy saleAsync = new SaleByOrderAsy(getActivity());
                        saleAsync.execute();
                    }
                };
                Dialog dialog = CommonActivity.createDialog(getActivity(),
                        getResources().getString(R.string.sale_by_order_confirm),
                        getResources().getString(R.string.app_name), getResources().getString(R.string.ok),
                        getResources().getString(R.string.cancel), saleClick, null);
                dialog.show();

                return;
            }
            boolean isChoose = false;
            for (StockModel item : lstStockModel) {
                if (item.getQuantitySaling() > 0) {
                    isChoose = true;
                    break;
                }
            }
            if (!isChoose) {
                Toast.makeText(getActivity(), getResources().getString(R.string.chooseAleastItem), Toast.LENGTH_SHORT)
                        .show();
                return;
            }

            for (StockModel item : lstStockModel) {
                if (!item.isFullySerial()) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.mustInputAllSerial),
                            Toast.LENGTH_LONG).show();
                    return;
                }
            }


            if (functionType == FUNCTION_SALE_RETAIL || functionType == FUNCTION_SALE_PROMOTION) {
                if (lnVAS.getVisibility() == View.VISIBLE) {
                    Spin vas = (Spin) spiVas.getSelectedItem();
                    if (vas != null && !vas.getId().equals("-1")) {
                        String vasMobile = edtVasPhone.getText().toString().trim();
                        if (!StringUtils.isViettelMobile(vasMobile)) {
                            CommonActivity.createAlertDialog(getActivity(), R.string.sdtVasEmpty, R.string.app_name_title).show();
                            return;
                        }
                    }
                }


                // Neu la ban le, show Dialog nhap thong tin khach hang

                cusDialog = new Dialog(getActivity());
                cusDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                cusDialog.setContentView(R.layout.sale_customer_layout);
                cusDialog.setTitle(getResources().getString(R.string.app_name));
                btnSaleOndialog = cusDialog.findViewById(R.id.btnOK);
                btnSaleOndialog.setOnClickListener(this);
                btnCancelOndialog = cusDialog.findViewById(R.id.btnViewSaleTrans);
                btnCancelOndialog.setOnClickListener(this);
                edtCusName = (EditText) cusDialog.findViewById(R.id.edtCusName);
                spiHTTT = (Spinner) cusDialog.findViewById(R.id.spiHTTT);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                        R.layout.spinner_item);
                String[] arr = getResources().getStringArray(R.array.arrPaymethod);
                adapter.add(arr[0]);
                if (functionType == FUNCTION_SALE_RETAIL) {
                    adapter.add(arr[1]);
                    adapter.add(arr[2]);
                }

                spiHTTT.setAdapter(adapter);
                spnBank = (Spinner) cusDialog.findViewById(R.id.spnBank);
                spiHTTT.setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        if (arg2 == 0 || arg2 == 2) {
                            if (arg2 == 0) {
                                payMethod = PAY_METHOD_MONEY;
                            } else {
                                payMethod = PAY_METHOD_TRANSFER;
                            }
                            if (!cbxReceiveInvoice.isChecked()) {
                                cusDialog.findViewById(R.id.lnBankplusMobile).setVisibility(View.GONE);
                            }

                            cusDialog.findViewById(R.id.lnBank).setVisibility(View.GONE);
                        } else if (arg2 == 1) {
                            initBank();
                            payMethod = PAY_METHOD_BANKPLUS;
                            cusDialog.findViewById(R.id.lnBankplusMobile).setVisibility(View.VISIBLE);
                            cusDialog.findViewById(R.id.lnBank).setVisibility(View.VISIBLE);

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });
                edtCusName.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                    }

                    @Override
                    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                    }

                    @Override
                    public void afterTextChanged(Editable text) {
                        if (text.toString().trim().isEmpty()) {
                            btnSaleOndialog.setVisibility(View.GONE);
                        } else {
                            btnSaleOndialog.setVisibility(View.VISIBLE);
                        }

                    }
                });
                edtCusCompany = (EditText) cusDialog.findViewById(R.id.edtCusCopany);
                edtCusTin = (EditText) cusDialog.findViewById(R.id.edtCusTin);
                edtCusName.requestFocus();
                cusDialog.getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                edtBankplusMobileDialog = (EditText) cusDialog.findViewById(R.id.edtBankplusMobile);
                if (CommonActivity.isNullOrEmpty(edtVasPhone.getText().toString().trim())) {
                    edtBankplusMobileDialog.setText(edtVasPhone.getText().toString().trim());
                }
                edtEmail = (EditText) cusDialog.findViewById(R.id.edtEmail);
                spnBank.setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        bankCode = ((Spin) spnBank.getItemAtPosition(arg2)).getId();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });
                cbxReceiveInvoice = (CheckBox) cusDialog.findViewById(R.id.cbInvoice);
                cbxReceiveInvoice.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        if (payMethod == PAY_METHOD_MONEY) {
                            if (!cbxReceiveInvoice.isChecked()) {
                                cusDialog.findViewById(R.id.lnBankplusMobile).setVisibility(View.GONE);
                                cusDialog.findViewById(R.id.lnEmail).setVisibility(View.GONE);
                            } else {
                                cusDialog.findViewById(R.id.lnBankplusMobile).setVisibility(View.VISIBLE);
                                cusDialog.findViewById(R.id.lnEmail).setVisibility(View.VISIBLE);
                            }
                        }

                    }
                });
                cusDialog.show();
            } else if (functionType == FUNCTION_SALE_DEPOSIT) {

                // TODO Auto-generated method stub
                final Boolean isNetWorkConnected = CommonActivity.isNetworkConnected(getActivity());
                String messageConfirm = getResources().getString(R.string.sale_confirm);
                String titleConfirm = getResources().getString(R.string.app_name);
                if (!isNetWorkConnected) {
                    messageConfirm = getResources().getString(R.string.no_network_message);
                    titleConfirm = getResources().getString(R.string.no_network_title);
                }

                OnClickListener saleClick = new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (isNetWorkConnected) {
                            // truong hop co mang
                            SaleAsync saleAsync = new SaleAsync(getActivity());
                            saleAsync.execute();
                        } else {
                            // Truong hop khong co mang
                            dialogSendSMS = new ProgressDialog(getActivity());
                            dialogSendSMS.setMessage(getResources().getString(R.string.processing));
                            dialogSendSMS.setCancelable(false);
                            if (!dialogSendSMS.isShowing()) {
                                dialogSendSMS.show();
                            }

                            String synTask = "";
                            if (functionType == FUNCTION_SALE_DEPOSIT) {
                                synTask = "BDC";
                            } else {
                                synTask = "0BD";
                            }
                            CommonActivity.sendSMS(Constant.EXCHANGE_ADDRESS, buildRawData(true), getActivity(),
                                    dialogSendSMS, synTask);

                            new CountDownTimer(Constant.TIMEOUT_SMS, 1000) {
                                public void onTick(long millisUntilFinished) {
                                }

                                public void onFinish() {
                                    if (dialogSendSMS.isShowing()) {
                                        dialogSendSMS.dismiss();
                                        Toast.makeText(getActivity(), getResources().getString(R.string.time_out_sms),
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            }.start();
                        }
                    }
                };
                Dialog confirmDialog = CommonActivity.createDialog(getActivity(), messageConfirm, titleConfirm,
                        getResources().getString(R.string.ok), getResources().getString(R.string.cancel), saleClick,
                        null);
                confirmDialog.show();
            } else {

                final Dialog dialog;
                dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.choose_httt_dialog);

                edtBankplusMobileDialog = (EditText) dialog.findViewById(R.id.edtBankplusStaff);
                spiHTTT = (Spinner) dialog.findViewById(R.id.spiHTTT);
                spnBank = (Spinner) dialog.findViewById(R.id.spnBank);
                spnBank.setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        bankCode = ((Spin) spnBank.getItemAtPosition(arg2)).getId();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item,
                        getResources().getStringArray(R.array.arrPaymethod));

                spiHTTT.setAdapter(adapter);
                spiHTTT.setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        if (arg2 == 0 || arg2 == 2) {
                            if (arg2 == 0) {
                                payMethod = PAY_METHOD_MONEY;
                            } else {
                                payMethod = PAY_METHOD_TRANSFER;
                            }
                            dialog.findViewById(R.id.lnBank).setVisibility(View.GONE);
                            dialog.findViewById(R.id.lnBankplusStaff).setVisibility(View.GONE);
                        } else {
                            initBank();
                            payMethod = PAY_METHOD_BANKPLUS;
                            dialog.findViewById(R.id.lnBank).setVisibility(View.VISIBLE);
                            dialog.findViewById(R.id.lnBankplusStaff).setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });
                Button btnOK = (Button) dialog.findViewById(R.id.btnOK);
                Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
                btnCancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        dialog.dismiss();
                    }
                });
                OnClickListener btnSaleClick = new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
//                        if (payMethod == PAY_METHOD_BANKPLUS) {
//                            if (CommonActivity.isNullOrEmpty(edtBankplusMobileDialog.getText().toString())
//                                    || !CommonActivity.validateIsdn(edtBankplusMobileDialog.getText().toString())) {
//                                CommonActivity.createAlertDialog(getActivity(),
//                                        getString(R.string.bankplusMobileInvalid), getString(R.string.app_name)).show();
//                                edtBankplusMobileDialog.requestFocus();
//                                return;
//
//                            }
//                        }
                        if (dialog != null) {
                            dialog.dismiss();
                        }

                        // TODO Auto-generated method stub
                        final Boolean isNetWorkConnected = CommonActivity.isNetworkConnected(getActivity());
                        String messageConfirm = getResources().getString(R.string.sale_confirm);
                        String titleConfirm = getResources().getString(R.string.app_name);
                        if (!isNetWorkConnected) {
                            messageConfirm = getResources().getString(R.string.no_network_message);
                            titleConfirm = getResources().getString(R.string.no_network_title);
                        }
                        OnClickListener saleClick = new OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (isNetWorkConnected) {
                                    // truong hop co mang
                                    SaleAsync saleAsync = new SaleAsync(getActivity());
                                    saleAsync.execute();
                                } else {
                                    // Truong hop khong co mang
                                    dialogSendSMS = new ProgressDialog(getActivity());
                                    dialogSendSMS.setMessage(getResources().getString(R.string.processing));
                                    dialogSendSMS.setCancelable(false);
                                    if (!dialogSendSMS.isShowing()) {
                                        dialogSendSMS.show();
                                    }

                                    String synTask = "";
                                    if (functionType == FUNCTION_SALE_DEPOSIT) {
                                        synTask = "BDC";
                                    } else {
                                        synTask = "0BD";
                                    }
                                    CommonActivity.sendSMS(Constant.EXCHANGE_ADDRESS, buildRawData(true), getActivity(),
                                            dialogSendSMS, synTask);

                                    new CountDownTimer(Constant.TIMEOUT_SMS, 1000) {
                                        public void onTick(long millisUntilFinished) {
                                        }

                                        public void onFinish() {
                                            if (dialogSendSMS.isShowing()) {
                                                dialogSendSMS.dismiss();
                                                Toast.makeText(getActivity(),
                                                        getResources().getString(R.string.time_out_sms),
                                                        Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }.start();
                                }
                            }
                        };
                        Dialog confirmDialog = CommonActivity.createDialog(getActivity(), messageConfirm, titleConfirm,
                                getResources().getString(R.string.ok), getResources().getString(R.string.cancel),
                                saleClick, null);
                        confirmDialog.show();
                    }
                };
                btnOK.setOnClickListener(btnSaleClick);

                dialog.show();
            }

        } else if (view == btnSaleOndialog) {
            String tenKH = edtCusName.getText().toString();

            if (tenKH.isEmpty() || tenKH == null) {
                Toast.makeText(getActivity(), getResources().getString(R.string.input_cust_hint), Toast.LENGTH_SHORT)
                        .show();
                edtCusName.requestFocus();
                return;
            }
            if (payMethod == PAY_METHOD_BANKPLUS) {
                if (CommonActivity.isNullOrEmpty(edtBankplusMobileDialog

                        .getText().toString())) {

                    Toast.makeText(getActivity(), getResources().getString(R.string.inputBankPlusMobile),
                            Toast.LENGTH_SHORT).show();
                    edtBankplusMobileDialog.requestFocus();
                    return;
                } else if (CommonActivity.getStardardIsdn(edtBankplusMobileDialog.getText().toString()).length() < 11
                        || CommonActivity.getStardardIsdn(edtBankplusMobileDialog.getText().toString()).length() > 12) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.bankplusMobileInvalid),
                            Toast.LENGTH_SHORT).show();
                    edtBankplusMobileDialog.requestFocus();
                    return;
                }

            }

            if (cbxReceiveInvoice.isChecked()) {
                String bplusMobile = edtBankplusMobileDialog.getText()
                        .toString();
                String email = edtEmail.getText().toString();
                if (CommonActivity.isNullOrEmpty(bplusMobile)
                        && CommonActivity.isNullOrEmpty(email)) {
                    Toast.makeText(
                            getActivity(),
                            getResources().getString(
                                    R.string.must_input_phone_or_email),
                            Toast.LENGTH_SHORT).show();
                    edtBankplusMobileDialog.requestFocus();
                    return;
                }

                if (!CommonActivity.isNullOrEmpty(bplusMobile)
                        && !CommonActivity.validateIsdn(bplusMobile)) {
                    Toast.makeText(
                            getActivity(),
                            getResources().getString(
                                    R.string.phone_number_invalid_format),
                            Toast.LENGTH_SHORT).show();
                    edtBankplusMobileDialog.requestFocus();
                    return;
                }
                if (!CommonActivity.isNullOrEmpty(email)
                        && !StringUtils.validateEmail(email)) {
                    Toast.makeText(
                            getActivity(),
                            getResources().getString(
                                    R.string.email_invalid_format),
                            Toast.LENGTH_SHORT).show();
                    edtEmail.requestFocus();
                    return;
                }

            }
            // param.put("cusCompany", edtCusCompany.getText().toString());
            // param.put("cusTin", edtCusTin.getText().toString());
            // param.put("custName", edtCusName.getText().toString());

            final Boolean isNetWorkConnected = CommonActivity.isNetworkConnected(getActivity());
            String messageConfirm = getResources().getString(R.string.sale_confirm);
            String titleConfirm = getResources().getString(R.string.app_name);
            if (!isNetWorkConnected) {
                messageConfirm = getResources().getString(R.string.no_network_message);
                titleConfirm = getResources().getString(R.string.no_network_title);
            }
            if (StringUtils.CheckCharSpecical(edtCusName.getText().toString())) {
                Toast.makeText(getActivity(), getResources().getString(R.string.cusname_special_character),
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (StringUtils.CheckCharSpecical(edtCusCompany.getText().toString())) {
                Toast.makeText(getActivity(), getResources().getString(R.string.cuscompany_special_character),
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (StringUtils.CheckCharSpecical(edtCusTin.getText().toString())) {
                Toast.makeText(getActivity(), getResources().getString(R.string.custin_special_character),
                        Toast.LENGTH_SHORT).show();
                return;
            }

            OnClickListener saleClick = new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (isNetWorkConnected) {
                        // truong hop co mang
                        SaleAsync saleAsync = new SaleAsync(getActivity());
                        saleAsync.execute();
                    } else {
                        // Truong hop khong co mang
                        dialogSendSMS = new ProgressDialog(getActivity());
                        dialogSendSMS.setMessage(getResources().getString(R.string.processing));
                        dialogSendSMS.setCancelable(false);
                        if (!dialogSendSMS.isShowing()) {
                            dialogSendSMS.show();
                        }
                        CommonActivity.sendSMS(Constant.EXCHANGE_ADDRESS, buildRawData(true), getActivity(),
                                dialogSendSMS, "0BD");

                        new CountDownTimer(Constant.TIMEOUT_SMS, 1000) {
                            public void onTick(long millisUntilFinished) {
                            }

                            public void onFinish() {
                                if (dialogSendSMS.isShowing()) {
                                    dialogSendSMS.dismiss();
                                    Toast.makeText(getActivity(), getResources().getString(R.string.time_out_sms),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        }.start();
                    }
                }
            };

            Dialog dialog = CommonActivity.createDialog(getActivity(), messageConfirm, titleConfirm,
                    getResources().getString(R.string.ok), getResources().getString(R.string.cancel), saleClick, null);
            dialog.show();

        } else if (view == tvHint) {
            if (tvHint != null) {
                tvHint.setVisibility(View.GONE);
            }
        } else if (view == btnCancelOndialog) {
            CommonActivity.hideSoftKeyboard(MainActivity.getInstance());
            cusDialog.dismiss();
        } else if (view == tvChooseChannel) {
            if (functionType == FUNCTION_SALE_BHLD) {
                return;
            }
            FragmentChooseChannel fragment = new FragmentChooseChannel();
            fragment.setTargetFragment(this, REQUEST_CHOOSE_CHANNEL);
            ReplaceFragment.replaceFragment(getActivity(), fragment, false);
        } else if (view == btnViewStockModel) {
            if (functionType == FUNCTION_SALE_BY_ORDER) {
                if (dialogCancelOrder == null) {
                    if (!CommonActivity.isNetworkConnected(getActivity())) {
                        CommonActivity.createAlertDialog(getActivity(), R.string.errorNetwork, R.string.app_name)
                                .show();
                        return;
                    }
                    GetCancelOrderAsy asy = new GetCancelOrderAsy(getActivity());
                    asy.execute();
                    return;
                } else {
                    dialogCancelOrder.show();
                }
                return;
            }

            if (lstStockModel == null || lstStockModel.isEmpty()) {
                Toast.makeText(getActivity(), getResources().getString(R.string.no_stock_model), Toast.LENGTH_LONG)
                        .show();
                return;
            }
            if (!isViewStockModel && countMoney().compareTo(0L) == 0) {
                Toast.makeText(getActivity(), getResources().getString(R.string.no_choose_stock_model),
                        Toast.LENGTH_LONG).show();
                return;
            }

            isViewStockModel = !isViewStockModel;
            if (isViewStockModel) {
                btnViewStockModel.setText(getResources().getString(R.string.all));
                lnInput.setVisibility(View.VISIBLE);
            } else {
                btnViewStockModel.setText(getResources().getString(R.string.choosed));
            }
            adapter.filter(isViewStockModel, lstStockModel);
        } else if (view == btnCancelOrder) {

            OnClickListener saleClick = new OnClickListener() {

                @Override
                public void onClick(View v) {
                    CancelOrderAsy cancelAsync = new CancelOrderAsy(getActivity());
                    cancelAsync.execute();
                }
            };
            Dialog dialog = CommonActivity.createDialog(getActivity(),
                    getResources().getString(R.string.cancel_order_confirm),
                    getResources().getString(R.string.app_name), getResources().getString(R.string.ok),
                    getResources().getString(R.string.cancel), saleClick, null);
            dialog.show();

        } else if (view == btnCancelCancelDialog) {
            dialogCancelOrder.dismiss();
        } else {
            switch (view.getId()) {
                case R.id.btnHome:
                    CommonActivity.callphone(getActivity(), Constant.phoneNumber);
                    break;
                case R.id.relaBackHome:
                    getActivity().onBackPressed();
                    break;
                default:
                    break;
            }
        }
    }

    private Boolean isFormatted = false;

    @Override
    public void onChangeQuantity(StockModel stockModel) {
        final EditText edtQuantity;
        try {
            for (final StockModel item : lstStockModel) {

                if (item.getStockModelId().compareTo(stockModel.getStockModelId()) == 0) {
                    final Dialog dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.sale_input_quantity_dialog);
                    // dialog.setTitle(getResources().getString(
                    // R.string.salingQuantity));
                    edtQuantity = (EditText) dialog.findViewById(R.id.edtQuantity);

                    edtQuantity.addTextChangedListener(new TextWatcher() {

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if (!isFormatted) {
                                isFormatted = true;
                                edtQuantity.setText(StringUtils.formatMoney(s.toString().replaceAll("\\.", "")));
                                edtQuantity.setSelection(edtQuantity.getText().toString().length());
                                isFormatted = false;
                            }
                        }
                    });
                    if (item.getQuantitySaling() > 0) {
                        edtQuantity.setText(item.getQuantitySaling() + "");
                    }

                    dialog.findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            String quantity = edtQuantity.getText().toString().replaceAll("\\.", "");
                            if (CommonActivity.isNullOrEmpty(quantity)) {
                                quantity = "0";
                            }
                            if (quantity == null || quantity.isEmpty()) {
                                if (item.getQuantitySaling().compareTo(0L) == 0) {
                                    CommonActivity.hideSoftKeyboard(MainActivity.getInstance());
                                    dialog.dismiss();
                                    return;
                                }
                                // Neu xoa het du lieu, chuyen item ve
                                // trang thai chua duoc chon
                                item.setQuantityIssue(item.getQuantityIssue());
                                if (item.isCheckSerial()) {
                                    item.getLstSerial().addAll(item.getSelectedSerial());
                                    item.setSelectedSerial(new ArrayList<String>());
                                }
                                Long count = countMoney();
                                if (count > 0) {
                                    if (functionType == FUNCTION_SALING || functionType == FUNCTION_SALE_BHLD) {
                                        if (calMoneyForSaleAsy != null) {
                                            calMoneyForSaleAsy.cancel(true);
                                        }
                                        if (CommonActivity.isNetworkConnected(getActivity())) {
                                            calMoneyForSaleAsy = new CalMoneyForSaleAsy();
                                            calMoneyForSaleAsy.execute();
                                        } else {
                                            view.findViewById(R.id.prbMoney).setVisibility(View.GONE);
                                            view.findViewById(R.id.lnMoney).setVisibility(View.VISIBLE);
                                            view.findViewById(R.id.lnRealMoney).setVisibility(View.VISIBLE);
                                            tvRealMoney.setText(
                                                    getResources().getString(R.string.money_total) + ": " + count);
                                            view.findViewById(R.id.lnDiscount).setVisibility(View.GONE);
                                        }
                                        // calMoneyForSaleAsy = new
                                        // CalMoneyForSaleAsy();
                                        // calMoneyForSaleAsy.execute();
                                    } else {
                                        view.findViewById(R.id.prbMoney).setVisibility(View.GONE);
                                        view.findViewById(R.id.lnRealMoney).setVisibility(View.VISIBLE);
                                        view.findViewById(R.id.lnMoney).setVisibility(View.VISIBLE);
                                        tvRealMoney
                                                .setText(getResources().getString(R.string.money_total) + ": " + count);
                                        view.findViewById(R.id.lnDiscount).setVisibility(View.GONE);
                                    }
                                } else {
                                    hideMoney();
                                }
                            } else if (item.getQuantitySaling().compareTo(Long.parseLong(quantity)) == 0) {
                                CommonActivity.hideSoftKeyboard(MainActivity.getInstance());
                                dialog.dismiss();
                                return;
                            } else if (item.getQuantityIssue() < Long.parseLong(quantity)) {
                                // So luong da nhap lon hon so luong
                                // trong kho, khong cho thuc hien
                                Toast.makeText(getActivity(), getResources().getString(R.string.valueGreateThanStock),
                                        Toast.LENGTH_LONG).show();
                                return;

                            }
                            // Cap nhat lai so luong, tinh lai chiet
                            // khau
                            item.setQuantitySaling(Long.parseLong(quantity));
                            item.getLstSerial().addAll(item.getSelectedSerial());
                            item.setSelectedSerial(new ArrayList<String>());
                            if (isViewStockModel) {
                                btnViewStockModel.setText(getResources().getString(R.string.all));
                            } else {
                                btnViewStockModel.setText(getResources().getString(R.string.choosed));
                            }
                            adapter.filter(isViewStockModel, lstStockModel);
                            Long count = countMoney();
                            if (count > 0) {
                                if (functionType == FUNCTION_SALING || functionType == FUNCTION_SALE_BHLD) {
                                    if (CommonActivity.isNetworkConnected(getActivity())) {
                                        calMoneyForSaleAsy = new CalMoneyForSaleAsy();
                                        calMoneyForSaleAsy.execute();
                                    } else {
                                        view.findViewById(R.id.prbMoney).setVisibility(View.GONE);
                                        view.findViewById(R.id.lnMoney).setVisibility(View.VISIBLE);
                                        view.findViewById(R.id.lnRealMoney).setVisibility(View.VISIBLE);
                                        tvRealMoney
                                                .setText(getResources().getString(R.string.money_total) + ": " + count);
                                        view.findViewById(R.id.lnDiscount).setVisibility(View.GONE);
                                    }
                                } else {
                                    view.findViewById(R.id.prbMoney).setVisibility(View.GONE);
                                    view.findViewById(R.id.lnDiscount).setVisibility(View.GONE);
                                    view.findViewById(R.id.lnMoney).setVisibility(View.VISIBLE);
                                    view.findViewById(R.id.lnRealMoney).setVisibility(View.VISIBLE);
                                    tvRealMoney.setText(getResources().getString(R.string.money_total) + ": " + count);
                                }
                            } else {
                                hideMoney();
                            }
                            CommonActivity.hideSoftKeyboard(MainActivity.getInstance());
                            dialog.dismiss();
                        }
                    });
                    dialog.findViewById(R.id.btnViewSaleTrans).setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            CommonActivity.hideSoftKeyboard(MainActivity.getInstance());
                            dialog.dismiss();
                        }
                    });
                    dialog.getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                    dialog.show();
                    // edtQuantity.requestFocus();
                    // InputMethodManager imm = (InputMethodManager)
                    // getActivity()
                    // .getSystemService(Context.INPUT_METHOD_SERVICE);
                    // imm.showSoftInput(edtQuantity,
                    // InputMethodManager.SHOW_IMPLICIT);
                    break;
                }
            }

        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception", e);
        }
    }

    private void isCalculatingMoney() {
        view.findViewById(R.id.lnMoney).setVisibility(View.VISIBLE);
        view.findViewById(R.id.lnRealMoney).setVisibility(View.GONE);
        view.findViewById(R.id.prbMoney).setVisibility(View.VISIBLE);
    }

    private void showMoney(SaleOutput saleOutput) {

        view.findViewById(R.id.lnRealMoney).setVisibility(View.VISIBLE);
        view.findViewById(R.id.prbMoney).setVisibility(View.GONE);
        if ("0".equals(saleOutput.getErrorCode())) {

            Long amountTotal = 0L;
            String discount = saleOutput.getDiscount() == null ? "0"
                    : "" + saleOutput.getDiscount();
            // String
            try {
                amountTotal = saleOutput.getAmountTax() + Long.parseLong(discount);
            } catch (Exception e) {
            }

            view.findViewById(R.id.lnDiscount).setVisibility(View.VISIBLE);
            tvDiscount.setText(getActivity().getString(R.string.discount) + ": "
                    + StringUtils.formatMoney(discount));

            tvRealMoney.setText(getActivity().getString(R.string.moneyTotal) + ": "
                    + StringUtils.formatMoney("" + saleOutput.getAmountTax()));
            tvTotalMoney.setText(
                    getActivity().getString(R.string.tv_amount) + ": " + StringUtils.formatMoney(amountTotal + ""));
        } else {
            tvRealMoney.setText(saleOutput.getDescription());
            view.findViewById(R.id.lnDiscount).setVisibility(View.GONE);
        }

    }

    private void hideMoney() {
        view.findViewById(R.id.lnMoney).setVisibility(View.GONE);
    }

    private Long countMoney() {
        try {
            Long result = 0L;
            for (StockModel item : lstStockModel) {
                if (item.getQuantitySaling() > 0) {
                    result = result + item.getQuantitySaling() * item.getPrice();
                }
            }
            return result;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception", e);
            return 0L;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (orderDetailAsync != null) {
            orderDetailAsync.cancel(true);
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @SuppressLint("DefaultLocale")
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String msg = intent.getStringExtra("msg_body");
                String[] result = msg.split(";", 5);
                if (result.length > 3) {
                    Session.setToken(result[0]);
                    String errorCode = result[3];

                    String syntax = result[1];
                    Log.d("syntax", syntax);

                    String message = getActivity().getResources().getString(R.string.sale_fail);
                    if (syntax.equalsIgnoreCase("0BD") || syntax.equalsIgnoreCase("BDC")) {
                        if (errorCode.equals("0")) {
                            // Thuc hien ban hang thanh cong
                            message = getResources().getString(R.string.sale_success);
                            hideMoney();
                            GetMaxOraRowScnDal requestSync = new GetMaxOraRowScnDal(getActivity());
                            requestSync.open();
                            ArrayList<String> lstTabelName = new ArrayList<String>();
                            lstTabelName.add("stock_model");
                            lstTabelName.add("stock_card");
                            lstTabelName.add("stock_kit");
                            lstTabelName.add("stock_handset");
                            lstTabelName.add("stock_total");
                            lstTabelName.add("stock_sim");
                            requestSync.updateSyncStatus(lstTabelName);
                            requestSync.close();
                            // Cap nhat trang thai hang hoa
                            LookUpStockBusiness.updateStockTotal(getActivity(), lstStockModel);
                            Toast.makeText(getActivity(), getResources().getString(R.string.sale_success),
                                    Toast.LENGTH_LONG).show();
                            SearchStockAsync search = new SearchStockAsync(getActivity());
                            search.execute();
                        } else {
                            OnClickListener onclick = null;
                            if (errorCode.equalsIgnoreCase(Constant.INVALID_TOKEN)) {
                                onclick = new OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                                        intent.addFlags(
                                                Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                                };
                            }
                            if (result[4] != null && !result[4].isEmpty()) {
                                message = result[4];
                            }
                            CommonActivity.createAlertDialog(getActivity(), message,
                                    getResources().getString(R.string.app_name), onclick).show();
                        }
                    }
                    if (dialogSendSMS != null && dialogSendSMS.isShowing()) {
                        dialogSendSMS.dismiss();
                    }
                }
            } catch (Exception e) {
                Log.e(LOG_TAG, e.toString(), e);
                if (dialogSendSMS != null && dialogSendSMS.isShowing()) {
                    dialogSendSMS.dismiss();
                }
            }

        }
    };

    private Boolean isCollaborator(Staff staff) throws Exception {
        if (Constant.CHANNEL_TYPE_POS.equals(staff.getChannelTypeId())) {
            return true;
        }
        if (Constant.CHANNEL_TYPE_COLLABORATOR.equals(staff.getChannelTypeId())) {
            if (Constant.POINT_OF_SALE_TYPE.equals(staff.getPointOfSale())) {
                return true;
            }
        }
        return false;
    }

    private void initVASSpinner() {
        AppParamsDAL dal = new AppParamsDAL(getActivity());
        try {
            dal.open();
            List<Spin> lstVas = dal.getListParamSale("LIST_PRODUCT_CODE_DATA");
            Spin first = new Spin("-1", getString(R.string.chooseVasProduct));
            lstVas.add(0, first);
            ArrayAdapter<Spin> adapter = new ArrayAdapter<Spin>(getActivity(),
                    R.layout.spinner_item, lstVas);
            spiVas.setAdapter(adapter);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception", e);
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

    private void initBank() {
        try {

            BhldDAL dal = new BhldDAL(getActivity());
            dal.open();
            ArrayList<Spin> arrBank = dal.getListBank("LIST_BANK");
            dal.close();
            Utils.setDataSpinner(getActivity(), arrBank, spnBank);
        } catch (Exception e) {
            Log.e("initBank", e.toString());
        }

    }

}

