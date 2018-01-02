package com.viettel.bss.viettelpos.v4.channel.activity;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.channel.dal.ChannelDAL;
import com.viettel.bss.viettelpos.v4.charge.dal.CacheDataCharge;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.commons.ViewConfig;
import com.viettel.bss.viettelpos.v4.customer.object.BOCOutput;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.handler.ListChannelHandler;
import com.viettel.bss.viettelpos.v4.handler.VSAMenuHandler;
import com.viettel.bss.viettelpos.v4.login.object.Staff;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentChanelInfo extends Fragment implements
        android.view.View.OnClickListener {
    private static final String TAG = FragmentChanelInfo.class.getSimpleName();
    private TextView txtName;
    private TextView txtMoneyBuy;
    private TextView tvMoneyRemain;
    private TextView txtTel;
    private TextView txtLastBuy;
    private TextView textvisitnumber;
    private String errorMessage = "";
    public static Staff mStaff;
    // private static final String TAG = "ChannelInfo";
    String objectId = null;
    private ProgressBar prbVisit;
    private ProgressBar prbMoneyBuy;
    private ProgressBar prbLastDateBuy;
    private ProgressBar prbMoneyRemain;
    private boolean isUpdate = false;
    private String channelName = "";
    private String channelPhone = "";
    private String timeCreateIdNo, birthday, idno;

    public static FragmentChanelInfo create(String key) {
        Bundle args = new Bundle();
        args.putString(Define.ARG_KEY, key);
        args.putSerializable(Define.KEY_STAFF, FragmentCusCareByDay.staff);
        // args.putSerializable(Define.KEY_STAFF, FragmentCusCareDayNew.staff);

        FragmentChanelInfo fragment = new FragmentChanelInfo();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView ++++++++++++++++++++");
        View mView = inflater.inflate(R.layout.info_chanel, container, false);
        try {
            Unit(mView);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!channelName.equals("") && isUpdate) {
            Log.d("LOG", "222222");
            txtName.setText(txtName.getText().toString() + ": " + channelName);
        }
        if (!channelPhone.equals("") && isUpdate) {
            Log.d("LOG", "3333");
            txtTel.setText(channelPhone);
        }
        return mView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d("TAG", "onActivityCreated FragmentLoginNotData");
        super.onActivityCreated(savedInstanceState);
    }

    private void Unit(View v) {
        TextView notifice = (TextView) v.findViewById(R.id.notifice);
        notifice.setVisibility(View.GONE);
        textvisitnumber = (TextView) v.findViewById(R.id.txtVisit);

        txtName = (TextView) v.findViewById(R.id.txtUserName);
        TextView txtIdUserNo = (TextView) v.findViewById(R.id.txtIDNo);
        TextView txtIdUserDate = (TextView) v.findViewById(R.id.txtIDNoDate);
        TextView txtBirthDay = (TextView) v.findViewById(R.id.txtBirthDay);
        txtMoneyBuy = (TextView) v.findViewById(R.id.txt_money_buy);
        txtLastBuy = (TextView) v.findViewById(R.id.last_date_buy);
        Button btnButtonCare = (Button) v.findViewById(R.id.btnChanelCare);
        ImageView imViewDetail = (ImageView) v.findViewById(R.id.imv_details_channel);
        TextView txtSmartSim = (TextView) v.findViewById(R.id.txtSmartSim);
        txtTel = (TextView) v.findViewById(R.id.txtTel);
        Button btnViewHistory = (Button) v.findViewById(R.id.btn_viewHistoryCare);
        Button btViewDetailAccountPaymont = (Button) v
                .findViewById(R.id.btViewDetailAccountPaymont);
        Button btnVewHistorySale = (Button) v
                .findViewById(R.id.btn_view_history_sale_trans);
        tvMoneyRemain = (TextView) v.findViewById(R.id.tvMoneyRemain);
        Button btnAddToolShop = (Button) v.findViewById(R.id.btnAddTS);
        Button btnUpdateLocation = (Button) v.findViewById(R.id.btnUpdateLocation);
        Button btnDiemBanClose = (Button) v.findViewById(R.id.btnDiemBanClose);
        LinearLayout llButtonCusCareDay = (LinearLayout) v
                .findViewById(R.id.llButtonCusCareDay);
        // btnTest = (Button) v.findViewById(R.id.btnTest);
        btnUpdateLocation.setOnClickListener(this);
        btnViewHistory.setOnClickListener(this);
        btnButtonCare.setOnClickListener(this);
        btnDiemBanClose.setOnClickListener(this);
        // imViewDetail = (ImageView) v.findViewById(R.id.imv_details_channel);
        // imViewDetail.setOnClickListener(this);
        btnVewHistorySale.setOnClickListener(this);
        btnAddToolShop.setOnClickListener(this);
        btViewDetailAccountPaymont.setOnClickListener(this);
        Button btncapnhathinhanhmoi = (Button) v
                .findViewById(R.id.btncapnhathinhanhmoi);
        btncapnhathinhanhmoi.setOnClickListener(this);
        Button btncapnhathinhanhhangthang = (Button) v
                .findViewById(R.id.btncapnhathinhanhhangthang);
        btncapnhathinhanhhangthang.setOnClickListener(this);
        Button btnthemmoiccdc = (Button) v.findViewById(R.id.btnthemmoiccdc);
        btnthemmoiccdc.setOnClickListener(this);
        LinearLayout llButton = (LinearLayout) v.findViewById(R.id.llButton);
        // Thuytv3_update info channel_start
        ImageButton btnUpdate = (ImageButton) v.findViewById(R.id.ibtnUpdateInfo);
        btnUpdate.setOnClickListener(this);

        if (getActivity() instanceof FragmentCusCareByDay) {
            Log.d(TAG, "getActivity() instanceof FragmentCusCareByDay");
            llButton.setVisibility(View.GONE);
            // btnUpdate.setVisibility(View.GONE);
            btnViewHistory.setVisibility(View.GONE);
            btnVewHistorySale.setVisibility(View.GONE);
            btViewDetailAccountPaymont.setVisibility(View.GONE);
            llButtonCusCareDay.setVisibility(View.VISIBLE);
        } else {
            Log.d(TAG, "getActivity() is not instanceof FragmentCusCareByDay");
        }
        // txtMoneyBuy.setText(MainActivity.getInstance().getResources().getString(R.string.money_buy)
        // +
        // ": "
        // + 0);
        // textvisitnumber.setText(MainActivity.getInstance().getResources().getString(
        // R.string.frequency_care)
        // + ": " + 0);
        // btnTest.setOnClickListener(this);
        // txtLastBuy.setText(MainActivity.getInstance().getResources().getString(R.string.last_date_buy)
        // + ": " +
        // MainActivity.getInstance().getResources().getString(R.string.never_sale));
        //
        // txtMoneyBuy.setText(MainActivity.getInstance().getResources().getString(R.string.money_buy)
        // +
        // ": "
        // + 0);
        // textvisitnumber.setText(MainActivity.getInstance().getResources().getString(
        // R.string.frequency_care)
        // + ": " + 0);
        // txtLastBuy.setText(MainActivity.getInstance().getResources().getString(R.string.last_date_buy)
        // + ": " +
        // MainActivity.getInstance().getResources().getString(R.string.never_sale));
        prbVisit = (ProgressBar) v.findViewById(R.id.prb_visit);
        prbLastDateBuy = (ProgressBar) v.findViewById(R.id.prb_last_date_buy);
        prbMoneyBuy = (ProgressBar) v.findViewById(R.id.prb_money_buy);
        prbMoneyRemain = (ProgressBar) v.findViewById(R.id.prb_money_remain);
        Bundle mBundle = getArguments();
        if (mBundle != null) {

            ChannelDAL mChannelDAL = new ChannelDAL(MainActivity.getInstance());
            mStaff = (Staff) mBundle.getSerializable(Define.KEY_STAFF);
            try {
                mStaff = mChannelDAL.getStaffByStaffCode(
                        Define.TABLE_NAME_STAFF, mStaff.getStaffCode(), null);
                FragmentCusCareByDay.staff = mStaff;
                mChannelDAL.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            Log.e("tag", "x = " + mStaff.getX());
            if (mStaff.getX() == 0 && mStaff.getY() == 0) {
                notifice.setVisibility(View.VISIBLE);
            }
            // SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            // ImageLoader mImageLoader = new
            // ImageLoader(MainActivity.getInstance());
            // boolean b = mImageLoader.displayImage(mStaff.getLink_photo(),
            // imViewDetail, 0);
            boolean b = false;
            if (!b) {
                // imViewDetail.setImageResource(R.drawable.logo_vt);
                ViewConfig.setStaffBitmap(mStaff, imViewDetail);

            }
            if (!mStaff.getIsdnAgent().trim().equals("")) {
                txtSmartSim.setText(MainActivity.getInstance().getResources()
                        .getString(R.string.txtSmartSim)
                        + ": " + mStaff.getIsdnAgent());
            } else {
                txtSmartSim.setText(MainActivity.getInstance().getResources()
                        .getString(R.string.txtSmartSim)
                        + ": "
                        + MainActivity.getInstance().getResources()
                        .getString(R.string.notUpdate));
            }
            timeCreateIdNo = "";
            if (mStaff.getIdIssueDate() != null
                    && !mStaff.getIdIssueDate().trim().equals("")) {
                timeCreateIdNo = DateTimeUtils.convertDate(mStaff
                        .getIdIssueDate());
//				timeCreateIdNo = mStaff.getIdIssueDate();
            } else {
                timeCreateIdNo = MainActivity.getInstance().getResources()
                        .getString(R.string.notUpdate);
            }
            // <a href='tel:416-555-1234'>our number</a>
            if (mStaff.getTel() != null && !mStaff.getTel().trim().equals("")
                    && (!isUpdate)) {
                txtTel.setText(" " + mStaff.getTel());
                txtTel.setOnClickListener(this);
            } else {
                if (!isUpdate) {
                    txtTel.setText(" "
                            + MainActivity.getInstance().getResources()
                            .getString(R.string.notUpdate));
                }
            }

            if (mStaff.getStaffCode() != null) {
                try {
                    if (CommonActivity.isNetworkConnected(MainActivity
                            .getInstance())) {
                        if (mStaff.getStaffCode() != null) {
                            new GetMoneyRemainAsynctask(
                                    MainActivity.getInstance()).execute(
                                    mStaff.getStaffCode(), "2");
                            new SortBySaleAsynctask(MainActivity.getInstance())
                                    .execute(Session.userName,
                                            mStaff.getStaffCode(), "0", "0");
                        }

                        if(mStaff.getCareNumber() != null){
                            textvisitnumber.setText(getActivity()
                                    .getResources()
                                    .getString(R.string.frequency_care)
                                    + ": " + mStaff.getCareNumber());
                        } else {
                            if (mStaff.getStaffId() != null) {
                                new GetCare(MainActivity.getInstance())
                                        .execute(mStaff.getStaffId().toString());
                            }
                        }
                    } else {
                        String title = getString(R.string.app_name);
                        Dialog dialog = CommonActivity.createAlertDialog(
                                MainActivity.getInstance(), MainActivity
                                        .getInstance().getResources()
                                        .getString(R.string.errorNetwork),
                                title);
                        dialog.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            if (mStaff.getBirthDay() != null
                    && !mStaff.getBirthDay().equals("")) {
                birthday = DateTimeUtils.convertDate(mStaff.getBirthDay());
            } else {
                birthday = MainActivity.getInstance().getResources()
                        .getString(R.string.notUpdate);
            }

            if (mStaff.getIdUser_no() != null
                    && !mStaff.getIdUser_no().trim().equals("")) {
                idno = mStaff.getIdUser_no();
            } else {
                idno = MainActivity.getInstance().getResources()
                        .getString(R.string.notUpdate);
            }
            if (!isUpdate) {
                Log.d("LOG", "1111111111111");
                txtName.setText(txtName.getText().toString() + ": "
                        + mStaff.getNameStaff());
            }
            txtIdUserNo.setText(txtIdUserNo.getText() + ": " + idno);
            txtIdUserDate.setText(txtIdUserDate.getText() + ": "
                    + timeCreateIdNo);
            txtBirthDay.setText(txtBirthDay.getText() + ": " + birthday);

            SharedPreferences preferences = MainActivity.getInstance()
                    .getSharedPreferences(Define.PRE_NAME,
                            Activity.MODE_PRIVATE);

            String name = preferences.getString(Define.KEY_MENU_NAME, "");
            if (name.contains(";update_channel;")) {
                if (mStaff.getChannelTypeId() == 10
                        || mStaff.getChannelTypeId() == 34) {
                    btnUpdate.setVisibility(View.VISIBLE);
                } else {
                    btnUpdate.setVisibility(View.GONE);
                }
            }
            // Thuytv3_update_info_channel_end
        } else {
            // Khong co du lieu
            Log.d(TAG, "mBundle is null");
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle mBundle = getArguments();
        if (mBundle != null) {
            Boolean flagReload = (Boolean) mBundle.getSerializable("FLAG");

            if (flagReload != null && flagReload) {
                Staff mStaff = (Staff) mBundle
                        .getSerializable(Define.KEY_STAFF);
                if (mStaff != null) {
                    if (CommonActivity.isNetworkConnected(MainActivity
                            .getInstance())) {
                        new GetCare(MainActivity.getInstance()).execute(mStaff
                                .getStaffId().toString());
                    }
                }
            }
        }

        if (mStaff != null) {
            MainActivity.getInstance().setTitleActionBar(mStaff.getNameStaff());
            MainActivity.getInstance().setSubTitleActionBar(mStaff.getAddressStaff());
        }

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        Bundle mBundle = getArguments();
        switch (v.getId()) {
            case R.id.btnUpdateLocation:
                // Cap nhat toa do
                if (CommonActivity.isNetworkConnected(getActivity())) {

                    com.viettel.bss.viettelpos.v4.object.Location myLocation = CommonActivity
                            .findMyLocationGPS(MainActivity.getInstance(),
                                    "updateLocation");
                    if ("0".equals(myLocation.getX())
                            || "0".equals(myLocation.getY())) {
                        CommonActivity.DoNotLocation(getActivity());
                        return;
                    }
                    android.support.v4.app.DialogFragment newFragment = new DialogUpdateLocation();
                    mBundle.putSerializable(Define.KEY_STAFF, mStaff);
                    newFragment.setArguments(mBundle);
                    newFragment.show(getFragmentManager(), "dialog");
                } else {
                    String title = getString(R.string.app_name);
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
                            getResources().getString(R.string.errorNetwork), title);
                    dialog.show();
                }
                break;
            case R.id.btnDiemBanClose:
                // Cap nhat toa do
                if (CommonActivity.isNetworkConnected(getActivity())) {
                    Dialog dialog = CommonActivity.createDialog(getActivity(),
                            getResources().getString(R.string.txt_end_care_cus),
                            getResources().getString(R.string.app_name),
                             getResources()
                                    .getString(R.string.cancel), getResources().getString(R.string.ok), null, endCareCusClick);
                    dialog.show();
                } else {
                    String title = getString(R.string.app_name);
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
                            getResources().getString(R.string.errorNetwork), title);
                    dialog.show();
                }
                break;

            case R.id.btnHome:
                CommonActivity.callphone(MainActivity.getInstance(),
                        Constant.phoneNumber);
                break;

            case R.id.relaBackHome:
                try {
                    MainActivity.getInstance().onBackPressed();
                    CacheDataCharge.getInstance().setLisArrayListRe(null);
                    CacheDataCharge.getInstance().setLisArrayList(null);
                } catch (Exception e) {
                    // TODO: handle exception
                }

                break;
            case R.id.btnChanelCare:
                Log.i("TAG", "Btnchanel duoc click");
                FragmentChannelCare fchannelcare = new FragmentChannelCare();
                mBundle.putSerializable(Define.KEY_STAFF, mStaff);
                fchannelcare.setArguments(mBundle);

                ReplaceFragment.replaceFragment(MainActivity.getInstance(),
                        fchannelcare, false);

                break;
            // case R.id.imv_details_channel:
            // FragmentChannelUpdateImage fupdateImage = new
            // FragmentChannelUpdateImage();
            // fupdateImage.setArguments(mBundle);
            //
            // ReplaceFragment.replaceFragment(MainActivity.getInstance(),
            // fupdateImage, false);
            //
            // break;
            case R.id.btn_viewHistoryCare:
                Log.i("TAG", "Click view history");

                FragmentViewHistory mviewHistory = new FragmentViewHistory();
                mviewHistory.setArguments(mBundle);
                ReplaceFragment.replaceFragment(MainActivity.getInstance(),
                        mviewHistory, false);
                break;
            case R.id.btn_view_history_sale_trans:
                Log.i("TAG", "Click view history trans");
                mBundle = getArguments();
                FragmentViewHistoryTrans mviewfrsl = new FragmentViewHistoryTrans();
                mviewfrsl.setArguments(mBundle);
                ReplaceFragment.replaceFragment(MainActivity.getInstance(),
                        mviewfrsl, false);
                break;
            case R.id.btnAddTS:
                // Log.i("TAG", "Cap nhat vat pham trung bay");
                //
                // FragmentChannelToolShop toolShopFragment = new
                // FragmentChannelToolShop();
                // toolShopFragment.setArguments(mBundle);
                // mBundle.putSerializable("FLAG", false);
                // ReplaceFragment.replaceFragment(MainActivity.getInstance(),
                // toolShopFragment,
                // false);

                FragmentUpdateImageTools fragmentUpdateImageTools = new FragmentUpdateImageTools();
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("staffIdKey", mStaff);
                fragmentUpdateImageTools.setArguments(bundle2);
                ReplaceFragment.replaceFragment(MainActivity.getInstance(),
                        fragmentUpdateImageTools, true);

                break;

            case R.id.btViewDetailAccountPaymont:
                FragmentDetailPaymentAccount mViewDetailPayment = new FragmentDetailPaymentAccount();
                mViewDetailPayment.setArguments(mBundle);
                ReplaceFragment.replaceFragment(MainActivity.getInstance(),
                        mViewDetailPayment, false);
                break;
            case R.id.txtTel:
                Dialog dialog = CommonActivity.createDialog(
                        MainActivity.getInstance(),

                        MainActivity.getInstance().getResources()

                                .getString(R.string.callConfirm) + " "
                                + mStaff.getNameStaff() + " "
                                + MainActivity.getInstance().getResources()

                                .getString(R.string.moreConfirm),

                        MainActivity.getInstance().getResources()
                                .getString(R.string.app_name),

                        MainActivity.getInstance().getResources()
                                .getString(R.string.ok), MainActivity.getInstance()
                                .getResources()

                                .getString(R.string.cancel), clicAction, null);

                dialog.show();
                break;
            case R.id.btncapnhathinhanhmoi:
                FragmentUpdateImageNew fragmentUpdateImageNew = new FragmentUpdateImageNew();
                Bundle bundle = new Bundle();
                bundle.putSerializable("staffIdKey", mStaff);
                fragmentUpdateImageNew.setArguments(bundle);
                ReplaceFragment.replaceFragment(MainActivity.getInstance(),
                        fragmentUpdateImageNew, true);
                break;
            case R.id.btncapnhathinhanhhangthang:
                FragmentUpdateImageMonth fragmentUpdateImageMonth = new FragmentUpdateImageMonth();
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("staffIdKey", mStaff);
                fragmentUpdateImageMonth.setArguments(bundle1);
                ReplaceFragment.replaceFragment(MainActivity.getInstance(),
                        fragmentUpdateImageMonth, true);
                break;
            case R.id.btnthemmoiccdc:
                FragmentInsertImageTools fragmentInsertImageTools = new FragmentInsertImageTools();
                Bundle bundleInsert = new Bundle();
                bundleInsert.putSerializable("staffIdKey", mStaff);
                fragmentInsertImageTools.setArguments(bundleInsert);
                ReplaceFragment.replaceFragment(MainActivity.getInstance(),
                        fragmentInsertImageTools, true);
                break;
            case R.id.ibtnUpdateInfo:
                FragmentUpdateInfoChannel fragmentUpdateInfo = new FragmentUpdateInfoChannel();
                Bundle bundleUpdate = new Bundle();
                bundleUpdate.putSerializable("STAFF", mStaff);
                bundleUpdate.putString("timeCreateIdNo", timeCreateIdNo);
                bundleUpdate.putString("birthday", birthday);
                bundleUpdate.putString("idno", idno);
                fragmentUpdateInfo.setArguments(bundleUpdate);
                fragmentUpdateInfo.setTargetFragment(FragmentChanelInfo.this, 100);
                // ReplaceFragment.replaceFragment(MainActivity.getInstance(),
                // fragmentUpdateInfo, false);
                ReplaceFragment.replaceFragment(getActivity(), fragmentUpdateInfo,
                        false);
                break;
            default:
                break;
        }

    }

    private final OnClickListener clicAction = new OnClickListener() {

        @Override
        public void onClick(View v) {
            MyPhoneListener phoneListener = new MyPhoneListener();
            TelephonyManager telephonyManager = (TelephonyManager) MainActivity
                    .getInstance().getSystemService(Context.TELEPHONY_SERVICE);
            telephonyManager.listen(phoneListener,
                    PhoneStateListener.LISTEN_CALL_STATE);
            String uri = "tel:" + txtTel.getText().toString();
            Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));

            startActivity(callIntent);

        }
    };

    private class MyPhoneListener extends PhoneStateListener {

        private boolean onCall = false;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    break;

                case TelephonyManager.CALL_STATE_OFFHOOK:
                    Toast.makeText(MainActivity.getInstance(), "on call...",
                            Toast.LENGTH_LONG).show();
                    onCall = true;
                    break;

                case TelephonyManager.CALL_STATE_IDLE:

                    if (onCall) {

                        onCall = false;
                    }
                    break;
                default:
                    break;
            }

        }
    }

    private String getSaleFromWs(String staffCodeOwner, String staffCode,
                                 String orderBy, String maxResult) {
        String xml = createXMLGetListStaffIM(staffCodeOwner, staffCode,
                orderBy, maxResult);
        BCCSGateway input = new BCCSGateway();
        String response = null;
        CommonOutput output = null;
        try {
            String envelope = input.buildInputGatewayWithRawData2(
                    xml.toString(), "mbccs_getStaffInfor");
            response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
                    MainActivity.getInstance(), "mbccs_getStaffInfor");

            output = input.parseGWResponse(response);
            if (!output.getError().equals("0")) {
                errorMessage = output.getDescription();
                return Constant.ERROR_CODE;
            }

            String original = output.getOriginal();
            Log.d(FragmentChanelInfo.class.getName(), original + "");
            VSAMenuHandler handler = (VSAMenuHandler) CommonActivity
                    .parseXMLHandler(new VSAMenuHandler(), original);
            output = handler.getItem();

            if (!output.getErrorCode().equals("0")) {
                errorMessage = output.getDescription();
                return Constant.ERROR_CODE;
            }

            if (output.getToken() != null && !output.getToken().isEmpty()) {
                Session.setToken(output.getToken());
            }
            return original;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                errorMessage = MainActivity.getInstance().getResources()
                        .getString(R.string.exception)
                        + e.toString();
            } catch (Exception e2) {
                // TODO: handle exception
            }

            return Constant.ERROR_CODE;
        }

    }

    private String createXMLGetListStaffIM(String staffCodeOwner,
                                           String staffCode, String orderBy, String maxResult) {
        StringBuilder stringBuilder = new StringBuilder("<ws:getStaffInfor>");

        stringBuilder.append("<getStaffSortInput>");
        stringBuilder.append("<token>").append(Session.getToken()).append("</token>");
        stringBuilder.append("<staffCodeOwner>").append(staffCodeOwner).append("</staffCodeOwner>");
        stringBuilder.append("<staffCode>").append(staffCode).append("</staffCode>");
        stringBuilder.append("<orderBy>").append(orderBy).append("</orderBy>");
        stringBuilder.append("<maxResult>").append(maxResult).append("</maxResult>");
        stringBuilder.append("</getStaffSortInput>");
        stringBuilder.append("</ws:getStaffInfor>");
        Log.d("createfilexmlSyn", stringBuilder.toString());
        return stringBuilder.toString();
    }

    private class SortBySaleAsynctask extends AsyncTask<String, Void, String> {
        final XmlDomParse parse = new XmlDomParse();
        private Activity activity;

        private SortBySaleAsynctask(Activity _activity) {
            activity = _activity;
            prbMoneyBuy.setVisibility(View.VISIBLE);
            prbLastDateBuy.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... arg0) {
            return getSaleFromWs(arg0[0], arg0[1], arg0[2], arg0[3]);
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                prbMoneyBuy.setVisibility(View.GONE);
                prbLastDateBuy.setVisibility(View.GONE);
                if (activity.hasWindowFocus()) {
                    if (result != null) {
                        if (result.equals(Constant.ERROR_CODE)) {
                            String title = getString(R.string.app_name);
                            Dialog dialog = CommonActivity
                                    .createAlertDialog(MainActivity.getInstance(),
                                            errorMessage, title);
                            dialog.show();

                        } else {

                            // ArrayList<Staff> arrayStaffWs = new
                            // ArrayList<Staff>();
                            Long totalRevenue = 0L;

                            // if (arrayStaffWs.size() > 0) {
                            // arrayStaffWs.removeAll(arrayStaffWs);
                            // }

                            ListChannelHandler handler = (ListChannelHandler) CommonActivity
                                    .parseXMLHandler(new ListChannelHandler(
                                            getActivity(), null), result);
                            handler.closeDatabase();
                            // arrayStaffWs = handler.getArrayStaff();
                            // for (int i = 0; i < arrayStaffWs.size(); i++) {

                            String saleTransDate = handler.getSaleTransDate();
                            saleTransDate = DateTimeUtils
                                    .convertDate(saleTransDate);
                            if (saleTransDate != null && !saleTransDate.isEmpty()) {
                                txtLastBuy.setText(getActivity().getResources()
                                        .getString(R.string.last_date_buy)
                                        + ": "
                                        + saleTransDate.substring(0, 10));
                            }

                            // }
                            totalRevenue = handler.getTotalRevenue();
                            txtMoneyBuy.setText(getActivity().getResources()
                                    .getString(R.string.money_buy)
                                    + ": "
                                    + StringUtils.formatMoney(totalRevenue
                                    .toString()));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // progress.dismiss();
        }
    }

    private class GetCare extends AsyncTask<String, Void, String> {
        final BCCSGateway input = new BCCSGateway();
        final XmlDomParse parse = new XmlDomParse();
        private Activity activity;
        // ProgressDialog progress;
        String sms = "";

        private GetCare(Activity _activity) {
            // this.progress = new ProgressDialog(MainActivity.getInstance());
            // this.progress.setMessage(MainActivity.getInstance().MainActivity.getInstance().getResources().getString(
            // R.string.waitting));
            // if (!this.progress.isShowing()) {
            // this.progress.show();
            // }
            this.activity = _activity;
            prbVisit.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... arg0) {
            String xml = createXMLSynOjectid(arg0[0]);
            CommonOutput output = null;
            try {
                String envelope = input.buildInputGatewayWithRawData2(
                        xml, "mbccs_countVisitedNumber");
                String response = input.sendRequest(envelope,
                        Constant.BCCS_GW_URL, MainActivity.getInstance(),
                        "mbccs_countVisitedNumber");

                output = input.parseGWResponse(response);

                if (!output.getError().equals("0")) {
                    sms = output.getDescription();

                    return Constant.ERROR_CODE;
                }
                String original = output.getOriginal();
                VSAMenuHandler handler = (VSAMenuHandler) CommonActivity
                        .parseXMLHandler(new VSAMenuHandler(), original);
                output = handler.getItem();
                if (!output.getErrorCode().equals("0")) {
                    sms = output.getDescription();
                    return output.getErrorCode();
                }

                return original;
            } catch (Exception e) {
                e.printStackTrace();
                sms = MainActivity.getInstance().getResources()
                        .getString(R.string.exception)
                        + e.toString();
                return Constant.ERROR_CODE;
            } // get data
        }

        @Override
        protected void onPostExecute(String result) {
            prbVisit.setVisibility(View.GONE);
            if (result != null && activity.hasWindowFocus()) {
                try {
                    if (result.equals(Constant.INVALID_TOKEN2)) {
                        CommonActivity.BackFromLogin(getActivity(),
                                sms, ";channel.management;");
                    }
                    if (result.equals(Constant.ERROR_CODE)) {

                        String title = getActivity().getResources()
                                .getString(R.string.app_name);
                        Dialog dialog = CommonActivity.createAlertDialog(
                                getActivity(), sms, title);
                        dialog.show();

                        // CommonActivity.BackFromLogin(MainActivity.getInstance(),
                        // sms);
                    } else {
                        String numVisit = "";
                        try {
                            ListChannelHandler handler = (ListChannelHandler) CommonActivity
                                    .parseXMLHandler(new ListChannelHandler(
                                                    getActivity(), null),
                                            result);
                            handler.closeDatabase();
                            numVisit = handler.getNumVisit();
                            textvisitnumber.setText(getActivity()
                                    .getResources()
                                    .getString(R.string.frequency_care)
                                    + ": " + numVisit);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // try {
                        // Document doc = parse.getDomElement(result);
                        // NodeList nl = doc.getElementsByTagName("return");
                        // Element getlistData = (Element) nl.item(0);
                        // String numVisit = parse.getValue(getlistData,
                        // "numVisit");
                        // numVisit = (numVisit != null && numVisit != "") ?
                        // numVisit
                        // : "0";
                        // textvisitnumber.setText(MainActivity.getInstance().getResources().getString(
                        // R.string.frequency_care)
                        // + ": " + numVisit);
                        // } catch (Exception e) {
                        //
                        // e.printStackTrace();
                        // }
                    }
                } catch (Exception e) {

                }

            }
        }
    }

    public String createXMLGetHistorySale(String staffCode) {
        StringBuilder stringBuilder = new StringBuilder(
                "<ws:viewStaffSaleHistory>");

        stringBuilder.append("<saleHistoryInput>");
        stringBuilder.append("<token>").append(Session.getToken()).append("</token>");
        stringBuilder.append("<staffCode>").append(staffCode).append("</staffCode>");
        stringBuilder.append("<manageStaffCode></manageStaffCode>");
        stringBuilder.append("</saleHistoryInput>");
        stringBuilder.append("</ws:viewStaffSaleHistory>");
        Log.d("createfilexmlSyn", stringBuilder.toString());
        return stringBuilder.toString();
    }

    /**
     * @return
     */
    private class GetMoneyRemainAsynctask extends
            AsyncTask<String, Void, String> {
        final BCCSGateway input = new BCCSGateway();
        String original = null;
        String sms = "";
        private Activity activity;
        final XmlDomParse parse = new XmlDomParse();

        private GetMoneyRemainAsynctask(Activity _activity) {
            activity = _activity;
            prbMoneyRemain.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... arg0) {
            String xml = createXMLgetMoneyRemain(arg0[0], arg0[1]);

            Log.d("xml", xml);
            CommonOutput output = null;
            try {
                String envelope = input.buildInputGatewayWithRawData2(
                        xml.toString(), "mbccs_getMonneyRemain");
                String response = input.sendRequest(envelope,
                        Constant.BCCS_GW_URL, MainActivity.getInstance(),
                        "mbccs_getMonneyRemain");

                output = input.parseGWResponse(response);
                original = output.getOriginal();

                if (!output.getError().equals("0")) {
                    sms = output.getDescription();
                    return Constant.ERROR_CODE;
                }

            } catch (Exception e) {
                e.printStackTrace();
                try {
                    sms = MainActivity.getInstance().getResources()
                            .getString(R.string.exception)
                            + e.toString();
                } catch (Exception e2) {
                    // TODO: handle exception
                }

                return Constant.ERROR_CODE;
            }

            return original;
        }

        @Override
        protected void onPostExecute(String result) {
            prbMoneyRemain.setVisibility(View.GONE);
            if (result != null && activity.hasWindowFocus()) {
                if (result.equals(Constant.ERROR_CODE)) {
                    String title = MainActivity.getInstance().getString(R.string.app_name);
                    Dialog dialog = CommonActivity.createAlertDialog(
                            getActivity(), sms, title);
                    dialog.show();
                } else {
                    String total = "";
                    try {
                        ListChannelHandler handler = (ListChannelHandler) CommonActivity
                                .parseXMLHandler(new ListChannelHandler(
                                                getActivity(), null),
                                        result);
                        handler.closeDatabase();
                        total = handler.getTotal();
                        tvMoneyRemain.setText(tvMoneyRemain.getText() + ": "
                                + StringUtils.formatMoney(total));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // try {
                    // Document doc = parse.getDomElement(result);
                    // NodeList nl = doc.getElementsByTagName("return");
                    // Element getlistData = (Element) nl.item(0);
                    // String total = parse.getValue(getlistData, "total");
                    // tvMoneyRemain.setText(tvMoneyRemain.getText() + ": "
                    // + StringUtils.formatMoney(total));
                    //
                    // } catch (Exception e) {
                    // e.printStackTrace();
                    // }
                }
            }
        }
    }

    //
    // create file xml for Synchronization
    private String createXMLSynOjectid(String ojectid) {
        StringBuilder stringBuilder = new StringBuilder(
                "<ws:countVisitedNumber>");
        stringBuilder.append("<viewVisitHistoryOutput>");
        stringBuilder.append("<token>").append(Session.getToken()).append("</token>");
        stringBuilder.append("<objId>").append(ojectid).append("</objId>");
        stringBuilder.append("</viewVisitHistoryOutput>");
        stringBuilder.append("</ws:countVisitedNumber>");
        Log.d("createfilexmlSyn", stringBuilder.toString());
        return stringBuilder.toString();
    }

    /**
     * buil request
     *
     * @param staffCode
     * @param staffTypeId
     * @return
     */
    /*
     * <ws:viewPayment> <!--Optional:--> <getChannelInfoInput> <!--Optional:-->
	 * <staffCode>?</staffCode> <!--Optional:--> <staffId>?</staffId>
	 * <!--Optional:--> <token>?</token> </getChannelInfoInput>
	 * </ws:viewPayment>
	 */
    private String createXMLgetMoneyRemain(String staffCode, String staffTypeId) {
        StringBuilder stringBuilder = new StringBuilder("<ws:getMoneyRemain>");

        stringBuilder.append("<getChannelInfoInput>");
        stringBuilder.append("<token>").append(Session.getToken()).append("</token>");
        stringBuilder.append("<staffCode>").append(staffCode).append("</staffCode>");
        stringBuilder.append("<staffTypeId>").append(staffTypeId).append("</staffTypeId>");
        stringBuilder.append("</getChannelInfoInput>");
        stringBuilder.append("</ws:getMoneyRemain>");
        Log.d("createfilexmlSyn", stringBuilder.toString());
        return stringBuilder.toString();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 100) {
                isUpdate = true;

                channelName = data.getExtras().getString("CHANNEL_NAME");
                channelPhone = data.getExtras().getString("CHANNEL_PHONE");
                Log.d("LOG", "CHANNEL_NAME: " + channelName
                        + " --CHANNEL_PHONE: " + channelPhone);

            }
        }
    }

    private class AsyUpdateSalePointsCare extends
            AsyncTask<String, Void, BOCOutput> {

        private final Activity mActivity;
        final ProgressDialog progress;

        public AsyUpdateSalePointsCare(Activity mActivity) {

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
            return updateSalePointsCare();
        }

        @Override
        protected void onPostExecute(BOCOutput result) {
            super.onPostExecute(result);
            progress.dismiss();

            try {
                if ("0".equals(result.getErrorCode())) {
                    Dialog dialog = CommonActivity.createAlertDialog(
                            getActivity(),
                            mActivity.getResources().getString(
                                    R.string.txt_end_care_cus_success), mActivity
                                    .getResources().getString(R.string.app_name),
                            backPressClick);
                    dialog.show();
                } else {
                    if (Constant.INVALID_TOKEN2.equals(result.getErrorCode())) {
                        Dialog dialog = CommonActivity
                                .createAlertDialog(getActivity(), result
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
            }catch (Exception e){

            }
        }

        private BOCOutput updateSalePointsCare() {
            BOCOutput bocOutput = new BOCOutput();
            String original = "";
            try {
                String methodName = "updateSalePointsCare";
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_" + methodName);

                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:").append(methodName).append(">");
                rawData.append("<input>");
                rawData.append("<token>").append(Session.getToken()).append("</token>");
                rawData.append("<spchId>").append(FragmentCusCareByDay.mark2SellingContactHisId).append("</spchId>");
                rawData.append("<statusCare>").append(Constant.BOC2.STATUS_POS_DOOR_CLOSE).append("</statusCare>");
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
                Log.e(Constant.TAG, "updateSalePointsCare", e);
                bocOutput = new BOCOutput();
                bocOutput.setDescription(e.getMessage());
                bocOutput.setErrorCode(Constant.ERROR_CODE);
            }
            return bocOutput;
        }

    }

    private final OnClickListener backPressClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().onBackPressed();
        }
    };

    private final OnClickListener moveLogInAct = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // Intent intent = new Intent(getActivity(), LoginActivity.class);
            // startActivity(intent);
            //
            // getActivity().finish();
            LoginDialog dialog = new LoginDialog(getActivity(),
                    ";channel.management;");
            dialog.show();
        }
    };

    private final OnClickListener endCareCusClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            new AsyUpdateSalePointsCare(getActivity()).execute();
        }
    };

    // public void onSaveInstanceState(Bundle outState) {
    // outState.putString("WORKAROUND_FOR_BUG_19917_KEY",
    // "WORKAROUND_FOR_BUG_19917_VALUE");
    // super.onSaveInstanceState(outState);
    // };

}
