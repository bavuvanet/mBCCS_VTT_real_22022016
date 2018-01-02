package com.viettel.bss.viettelpos.v4.sale.strategy;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity.FragmentChooseReason;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter.GetFeeBCCSAdapter;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.GetReasonFullPMAsyn;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.AccountDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustomerDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductPackageFeeDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ReasonDTO;
import com.viettel.bss.smartphonev2.commons.OnPostExecute;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentChargeLiquidationRecovery;
import com.viettel.bss.viettelpos.v4.charge.adapter.SubGoodDTOAdapter;
import com.viettel.bss.viettelpos.v4.charge.asynctask.AsyncGetDebitInfo;
import com.viettel.bss.viettelpos.v4.charge.fragment.FragmentPayment;
import com.viettel.bss.viettelpos.v4.charge.object.VirtualInvoice;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.customer.asynctask.AsyncBlockOpenSub;
import com.viettel.bss.viettelpos.v4.customer.asynctask.AsyncCollectHotCharge;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.dal.ApParamDAL;
import com.viettel.bss.viettelpos.v4.message.BaseMsg;
import com.viettel.bss.viettelpos.v4.message.BlockOpenMsg;
import com.viettel.bss.viettelpos.v4.object.FormBean;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsynFindFeeByReasonTeleId;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskGetListReason;
import com.viettel.bss.viettelpos.v4.sale.object.SaleOutput;
import com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Toancx on 4/25/2017.
 */

public class BlockOpenSubHomeFragment extends FragmentCommon {
    private EditText edtSelectReason;
    private Spinner spiAction;
    private EditText edtIsdn;
    private SubscriberDTO sub = null;
    private Button btnExecute;
    private LinearLayout lnFeeType;
    private LinearLayout lnActionType;

    private RadioGroup rgActionType;
    private RadioButton rbChargeBlockOneWay;
    private RadioButton rbChargeBlockTwoWay;
    private RadioButton rbBlockOneWay;
    private RadioButton rbOpenOneWay;
    private RadioButton rbOpenTwoWay;

    private ListView listView;
    private String funtionTypeKey = Constant.BLOCK_OPEN_SUB_HOMEPHONE;
    private String function;
    private List<FormBean> lstAction = new ArrayList<FormBean>();
    private final String HOME_SERVICE = "HOME_SERVICE";
    private final String BLOCK_ONE_WAY = "BLOCK_ONE_WAY";
    private final String BLOCK_TWO_WAY = "BLOCK_TWO_WAY";
    private final String BLOCK_ONE_TWO_WAY = "BLOCK_ONE_TWO_WAY";
    private String blockType = "1"; //1: chi chan 1 chieu, 2: chi chan 2 chieu, 0: chan 1 chieu , 2 chieu
    private String actionCode = Constant.ACTION_CODE_BLOCK_OPEN_SUB.BLOCK_ONE_WAY;
    private VirtualInvoice virtualInvoice;
    private ArrayList<ReasonDTO> lstReasonDTOs = new ArrayList<ReasonDTO>();
    private ReasonDTO reasonDTO = null;
    private boolean forwardToPayment = false;
    private LinearLayout lnReason;
    private int requestCode;
    private int resultCode;
    private Intent data;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        idLayout = R.layout.fragment_block_open_sub_home;
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
        String titile = getString(R.string.change_pos_to_pre);
        if (Constant.BLOCK_OPEN_SUB_MOBILE.equals(funtionTypeKey)) {
            titile = getString(R.string.blockOpenSubMobile);
        } else if (Constant.LIQUIDATION_RECOVERY.equals(funtionTypeKey)) {
            titile = getString(R.string.liquidation_and_recovery);
        } else {
            titile = getString(R.string.blockOpenSubHomephone);
        }

        setTitleActionBar(titile);
    }

    @Override
    public void unit(View v) {

        edtSelectReason = (EditText) v.findViewById(R.id.edtSelectReason);
        edtSelectReason.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (!CommonActivity.isNullOrEmptyArray(lstReasonDTOs)) {
                    Intent intent = new Intent(getActivity(),
                            FragmentChooseReason.class);
                    intent.putExtra("arrReasonDTO", lstReasonDTOs);
                    startActivityForResult(intent, 1235);
                } else {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notdatareason),
                            getActivity().getString(R.string.app_name)).show();
                }
            }
        });

        spiAction = (Spinner) v.findViewById(R.id.spiAction);
        edtIsdn = (EditText) v.findViewById(R.id.edtIsdn);
        Bundle bundle = getArguments();
        sub = (SubscriberDTO) bundle.getSerializable("subscriberDTO");
        if (bundle.getString("funtionTypeKey") != null) {
            funtionTypeKey = bundle.getString("funtionTypeKey");
            function = bundle.getString("function");
        }
        edtIsdn.setText(sub.getAccount());
        edtIsdn.setEnabled(false);
        lnFeeType = (LinearLayout) v.findViewById(R.id.lnFeeType);
        lnActionType = (LinearLayout) v.findViewById(R.id.lnActionType);

        rgActionType = (RadioGroup) v.findViewById(R.id.rgActionType);
        rbChargeBlockOneWay = (RadioButton) v.findViewById(R.id.rbChargeBlockOneWay);
        rbChargeBlockTwoWay = (RadioButton) v.findViewById(R.id.rbChargeBlockTwoWay);
        rbBlockOneWay = (RadioButton) v.findViewById(R.id.rbBlockOneWay);
        rbOpenTwoWay = (RadioButton) v.findViewById(R.id.rbOpenTwoWay);

        rbOpenOneWay = (RadioButton) v.findViewById(R.id.rbOpenOneWay);
        btnExecute = (Button) v.findViewById(R.id.btnExecute);
        listView = (ListView) v.findViewById(R.id.listView);
        lnReason = (LinearLayout) v.findViewById(R.id.lnReason);


//        if (!allowBlockOpenSub(sub.getServiceType())) {
//            btnExecute.setEnabled(false);
//            if (!Constant.LIQUIDATION_RECOVERY.equals(funtionTypeKey))
//                CommonActivity.toast(getActivity(), getString(R.string.block_open_sub_service_not_allow, sub.getServiceType()));
//        }

        if (!CommonActivity.isNullOrEmpty(sub.getActStatus())) {

            if (Constant.LIQUIDATION_RECOVERY.equals(funtionTypeKey)) {

                rbChargeBlockOneWay.setVisibility(View.VISIBLE);
                rbChargeBlockOneWay.setText(getActivity().getString(R.string.collect_hot_charge));
                lnReason.setVisibility(View.GONE);
                btnExecute.setVisibility(View.GONE);

            } else {
                if (sub.getActStatus().startsWith("0")) { //trang thai binh thuong
                    if ("1".equals(blockType)) { //truong hop thue bao chi chan 1 chieu
                        rbChargeBlockOneWay.setVisibility(View.VISIBLE);
                    } else if ("2".equals(blockType)) { //truong hop thue bao chi chan 2 chieu
                        rbChargeBlockTwoWay.setVisibility(View.VISIBLE);
                    } else if ("0".equals(blockType)) { //truong hop thue bao chan 1 chieu hoac 2 chieu
                        rbBlockOneWay.setVisibility(View.VISIBLE);
                    }
                } else if (sub.getActStatus().startsWith("1")) { //truong hop chan 1 chieu
                    if ("1".equals(blockType)) { //truong hop thue bao chi chan 1 chieu
                        rbOpenOneWay.setVisibility(View.VISIBLE);
                    } else if ("0".equals(blockType)) { //truong hop thue bao chan 1 chieu hoac 2 chieu
                        rbOpenOneWay.setVisibility(View.VISIBLE);
                        rbChargeBlockTwoWay.setVisibility(View.VISIBLE);
                    }
                } else if (sub.getActStatus().startsWith("2")) {
                    rbOpenTwoWay.setVisibility(View.VISIBLE);
                }
            }


        }

        rgActionType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                forwardToPayment = false;
                switch (checkedId) {
                    case R.id.rbBlockOneWay:
                        actionCode = Constant.ACTION_CODE_BLOCK_OPEN_SUB.BLOCK_ONE_WAY;
                        break;
                    case R.id.rbChargeBlockOneWay:
                        actionCode = Constant.ACTION_CODE_BLOCK_OPEN_SUB.BLOCK_ONE_WAY;
                        AsyncCollectHotCharge asy1 = new AsyncCollectHotCharge(
                                sub.getAccountId(),
                                Long.valueOf(sub.getSubId()),
                                getString(R.string.dang_tong_hop_cuoc_nong),
                                collectHotchargePost, getActivity());
                        asy1.execute();
                        return;
                    case R.id.rbChargeBlockTwoWay:
                        actionCode = Constant.ACTION_CODE_BLOCK_OPEN_SUB.BLOCK_TWO_WAY;

                        AsyncCollectHotCharge asy2 = new AsyncCollectHotCharge(
                                sub.getAccountId(),
                                Long.valueOf(sub.getSubId()),
                                getString(R.string.dang_tong_hop_cuoc_nong),
                                collectHotchargePost, getActivity());
                        asy2.execute();
                        return;
                    case R.id.rbOpenOneWay:
                        actionCode = Constant.ACTION_CODE_BLOCK_OPEN_SUB.OPEN_ONE_WAY;
                        break;
                    case R.id.rbOpenTwoWay:
                        actionCode = Constant.ACTION_CODE_BLOCK_OPEN_SUB.OPEN_TWO_WAY;
                        break;
                    default:
                        break;
                }

                if (CommonActivity.isNetworkConnected(getActivity())) {
                    GetReasonFullPMAsyn asyncGetListReason = new GetReasonFullPMAsyn(getActivity(),
                            new OnPostGetReasonFullChangeProduct(), moveLogInAct);
                    asyncGetListReason.setPayType(sub.getPayType());

                    asyncGetListReason.execute(sub.getOfferId() + "", actionCode,
                            sub.getTelecomServiceAlias(), sub.getCustType(),
                            sub.getSubType(), "", "");
                } else {
                    CommonActivity.createAlertDialog(getActivity(),
                            getString(R.string.errorNetwork),
                            getString(R.string.app_name)).show();
                }
            }
        });

        btnExecute.setOnClickListener(new View.OnClickListener() {
            private String blockMode = "0";

            @Override
            public void onClick(View v) {

                blockMode = Constant.BLOCK_MODE_BOSUB.BLOCK_ONE_WAY;


//
                if (rbOpenOneWay.isChecked() || rbOpenTwoWay.isChecked()) {
                    blockMode = Constant.BLOCK_MODE_BOSUB.OPEN;
                } else if (rbBlockOneWay.isChecked() || rbChargeBlockOneWay.isChecked()) {
                    blockMode = Constant.BLOCK_MODE_BOSUB.BLOCK_ONE_WAY;
                } else if (rbChargeBlockTwoWay.isChecked()) {
                    blockMode = Constant.BLOCK_MODE_BOSUB.BLOCK_TWO_WAY;
                } else {
                    CommonActivity.toast(getActivity(), getString(R.string.block_open_sub_action_type_required));
                    return;
                }

                if (forwardToPayment) {
                    AsyncCollectHotCharge asy2 = new AsyncCollectHotCharge(
                            sub.getAccountId(),
                            Long.valueOf(sub.getSubId()),
                            getString(R.string.dang_tong_hop_cuoc_nong),
                            collectHotchargePost, getActivity());
                    asy2.execute();
                    return;
                }

                if (CommonActivity.isNullOrEmpty(reasonDTO) || CommonActivity.isNullOrEmpty(reasonDTO.getReasonId())) {
                    CommonActivity.toast(getActivity(), getString(R.string.block_open_sub_reason_required));
                    return;
                }

                View.OnClickListener onClick = new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO subscriberDTO = new com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO();
                        subscriberDTO.setSubId(Long.valueOf(sub.getSubId()));
                        subscriberDTO.setActionCode(actionCode);
                        subscriberDTO.setIsdn(sub.getAccount());

                        AsyncBlockOpenSub block = new AsyncBlockOpenSub(
                                getActivity(), blockMode, subscriberDTO,
                                blockOpenSubPost, reasonDTO.getReasonId());
                        block.execute();
                    }
                };

                String confirm = getString(R.string.block_open_confirm_chan_mot_chieu,
                        sub.getAccount());
                if (Constant.BLOCK_MODE_BOSUB.BLOCK_ONE_WAY.equals(blockMode)) {
                    confirm = getString(R.string.block_open_confirm_chan_mot_chieu,
                            sub.getAccount());
                } else if (Constant.BLOCK_MODE_BOSUB.BLOCK_TWO_WAY.equals(blockMode)) {
                    confirm = getString(R.string.block_open_confirm_chan_hai_chieu,
                            sub.getAccount());
                } else if (Constant.BLOCK_MODE_BOSUB.OPEN.equals(blockMode)) {
                    confirm = getString(R.string.block_open_confirm_mo_chan,
                            sub.getAccount());
                }

                CommonActivity.createDialog(getActivity(), confirm,
                        getString(R.string.app_name), getString(R.string.cancel),
                        getString(R.string.ok), null, onClick).show();
            }
        });

        ArrayAdapter<FormBean> actionAdapter = new ArrayAdapter<FormBean>(
                getActivity(), R.layout.simple_list_item_single_row,
                R.id.text1, lstAction);
        spiAction.setAdapter(actionAdapter);
        spiAction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });
    }

    // ly do tac dong
    private class OnPostGetReasonFullChangeProduct implements OnPostExecuteListener<List<ReasonDTO>> {
        @Override
        public void onPostExecute(List<ReasonDTO> result, String errorCode, String description) {
            lnReason.setVisibility(View.VISIBLE);
            if (!CommonActivity.isNullOrEmptyArray(result)) {
                lstReasonDTOs.addAll(result);
            } else {
                lstReasonDTOs = new ArrayList<ReasonDTO>();
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notdatareason),
                        getActivity().getString(R.string.app_name)).show();
            }
        }
    }

    private OnPostExecute<SaleOutput> collectHotchargePost = new OnPostExecute<SaleOutput>() {

        @Override
        public void onPostExecute(SaleOutput result) {
            if ("0".equals(result.getErrorCode())) {
                AsyncGetDebitInfo asy = new AsyncGetDebitInfo(getActivity(),
                        sub.getAccountId() + "", onPostGetDebit);
                asy.execute();
            } else {
                if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
                    CommonActivity.BackFromLogin((Activity) getActivity(),
                            result.getDescription());
                } else {
                    Dialog dialog = CommonActivity.createAlertDialog(
                            getActivity(), result.getDescription(),
                            getString(R.string.app_name));
                    dialog.show();
                }
            }
        }
    };

    OnPostExecute<SaleOutput> onPostGetDebit = new OnPostExecute<SaleOutput>() {
        @Override
        public void onPostExecute(SaleOutput result) {
            forwardToPayment = false;
            if ("0".equals(result.getErrorCode())) {
                virtualInvoice = result.getVirtualInvoice();

                if (virtualInvoice.getNeedAmount() <= 0) {
                    //TODO  check Type thanh ly thi chuyen sang man hinh thanh ly

                    if (Constant.LIQUIDATION_RECOVERY.equals(funtionTypeKey)) {
                        View.OnClickListener listener = new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("subscriberDTO", sub);
                                bundle.putString("funtionTypeKey", funtionTypeKey);
                                bundle.putString("function", function);
                                FragmentChargeLiquidationRecovery fragmentTarget = new FragmentChargeLiquidationRecovery();
                                fragmentTarget.setArguments(bundle);
                                ((FragmentCommon) fragmentTarget).setSkipBackStack(true);
                                ReplaceFragment.replaceFragment(getActivity(), fragmentTarget, false);
                            }
                        };
                        CommonActivity.createDialog(
                                getActivity(),
                                getResources().getString(
                                        R.string.revoke_confirm_) + " " + function.toLowerCase() + " ?",
                                getResources().getString(
                                        R.string.app_name),
                                getResources().getString(R.string.say_ko),
                                getResources().getString(R.string.say_co),
                                null, listener).show();
                    } else {
                        if (rbChargeBlockOneWay.isChecked()) {
                            btnExecute.setText(getString(R.string.chan_1c));
                        } else if (rbChargeBlockTwoWay.isChecked()) {
                            btnExecute.setText(getString(R.string.chan_2c));
                        }
                        //lay thong tin ly do chan mo 2 chieu
                        if (CommonActivity.isNetworkConnected(getActivity())) {
                            GetReasonFullPMAsyn asyncGetListReason = new GetReasonFullPMAsyn(getActivity(),
                                    new OnPostGetReasonFullChangeProduct(), moveLogInAct);
                            asyncGetListReason.setPayType(sub.getPayType());

                            asyncGetListReason.execute(sub.getOfferId() + "", actionCode,
                                    sub.getTelecomServiceAlias(), sub.getCustType(),
                                    sub.getSubType(), "", "");
                        } else {
                            CommonActivity.createAlertDialog(getActivity(),
                                    getString(R.string.errorNetwork),
                                    getString(R.string.app_name)).show();
                        }
                    }
                } else { //chuyen sang man hinh thanh toan
                    forwardToPayment = true;
                    lnReason.setVisibility(View.GONE);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("subscriberDTOKey", convertSubscriberDTO(sub));
                    bundle.putString("blockOpenSub", "true");
                    bundle.putSerializable("virtualInvoice", virtualInvoice);
                    bundle.putString("funtionTypeKey", funtionTypeKey);
                    FragmentPayment payment = new FragmentPayment();
                    if (Constant.LIQUIDATION_RECOVERY.equals(funtionTypeKey)) {
                        bundle.putString("function", function);
                        ((FragmentCommon) payment).setSkipBackStack(true);
                    }

                    payment.setArguments(bundle);

                    ReplaceFragment.replaceFragment(getActivity(), payment, false);
                }

            } else {
                if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
                    CommonActivity.BackFromLogin((Activity) getActivity(),
                            result.getDescription());
                } else {
                    Dialog dialog = CommonActivity.createAlertDialog(
                            getActivity(), result.getDescription(),
                            getString(R.string.app_name));
                    dialog.show();

                }
            }
        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 1235:
                    reasonDTO = (ReasonDTO) data.getExtras().getSerializable("reasonDTO");
                    if (reasonDTO != null) {
                        edtSelectReason.setText(reasonDTO.toString());
                        AsynFindFeeByReasonTeleId asynFindFeeByReasonTeleId = new AsynFindFeeByReasonTeleId(getActivity(), new OnPostExecuteListener<ArrayList<ProductPackageFeeDTO>>() {
                            @Override
                            public void onPostExecute(ArrayList<ProductPackageFeeDTO> result, String errorCode, String description) {
                                if (CommonActivity.isNullOrEmpty(result)) {
                                    lnFeeType.setVisibility(View.GONE);
                                } else {
                                    lnFeeType.setVisibility(View.VISIBLE);
                                    GetFeeBCCSAdapter getFeeBCCSAdapter = new GetFeeBCCSAdapter(result, getActivity());
                                    listView.setAdapter(getFeeBCCSAdapter);
                                }
                            }
                        }, moveLogInAct);

                        asynFindFeeByReasonTeleId.execute(String.valueOf(sub.getTelecomServiceId()), reasonDTO.getReasonId(), sub.getProductCode());

                    } else {
                        edtSelectReason.setText("");
                        edtSelectReason.setHint(getActivity().getString(R.string.block_open_select_reason));
                    }
                    break;
                case 1234:
                    getActivity().onBackPressed();
                    break;
                default:
                    break;
            }
        } else if (Constant.LIQUIDATION_RECOVERY.equals(funtionTypeKey)) {

            rbChargeBlockOneWay.setChecked(false);
            rbChargeBlockOneWay.setVisibility(View.VISIBLE);
            rbChargeBlockOneWay.setText(getActivity().getString(R.string.collect_hot_charge));
            lnReason.setVisibility(View.GONE);
            btnExecute.setVisibility(View.GONE);
        }
    }


    private com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO convertSubscriberDTO(SubscriberDTO sub) {
        com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO subscriberDTO = new com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO();

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountId(sub.getAccountId());
        accountDTO.setAccountNo(sub.getAccountNo());
//        accountDTO.setAddress(sub.geta);
        subscriberDTO.setAccountDTOForInput(accountDTO);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName(sub.getCustomerName());
        subscriberDTO.setCustomerDTOInput(customerDTO);

        subscriberDTO.setActStatus(sub.getActStatus());
        subscriberDTO.setIsdn(sub.getAccount());
        subscriberDTO.setTelecomServiceId(Long.valueOf(sub.getTelecomServiceId()));
        subscriberDTO.setTelecomServiceAlias(sub.getServiceType());
        subscriberDTO.setProductCode(sub.getProductCode());
        subscriberDTO.setActionCode(actionCode);
        subscriberDTO.setSubId(Long.valueOf(sub.getSubId()));
        subscriberDTO.setRawData(sub.getRawData());
        subscriberDTO.setCdtCode(sub.getCdtCode());
        return subscriberDTO;
    }

    private OnPostExecute<SaleOutput> blockOpenSubPost = new OnPostExecute<SaleOutput>() {
        @Override
        public void onPostExecute(SaleOutput result) {

            if ("0".equals(result.getErrorCode())) {
                String msg = getString(R.string.block_open_chan_mot_chieu_success, sub.getAccount());
                if (rbOpenOneWay.isChecked() || rbOpenTwoWay.isChecked()) {
                    msg = getString(R.string.block_open_mo_chan_success, sub.getAccount());
                } else if (rbChargeBlockOneWay.isChecked()) {
                    msg = getString(R.string.block_open_chan_mot_chieu_success, sub.getAccount());
                } else if (rbChargeBlockTwoWay.isChecked()) {
                    msg = getString(R.string.block_open_chan_hai_chieu_success, sub.getAccount());
                } else if (rbBlockOneWay.isChecked()) {
                    msg = getString(R.string.block_open_chan_mot_chieu_success, sub.getAccount());
                }
                if (Constant.LIQUIDATION_RECOVERY.equals(funtionTypeKey)) {
                    // replace fragment
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("subscriberDTO", sub);
                    bundle.putString("function", function);
                    FragmentChargeLiquidationRecovery fragmentChargeLiquidationRecovery = new FragmentChargeLiquidationRecovery();
                    fragmentChargeLiquidationRecovery.setSkipBackStack(true);
                    ReplaceFragment.replaceFragment(getActivity(), fragmentChargeLiquidationRecovery, false);
                }
                Dialog dialog = CommonActivity.createAlertDialog(
                        getActivity(), msg,
                        getString(R.string.app_name), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                EventBus.getDefault().postSticky(new BaseMsg());
                                getActivity().onBackPressed();
                            }
                        });
                dialog.show();

            } else {

                if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
                    CommonActivity.BackFromLogin((Activity) getActivity(),
                            result.getDescription());
                } else {
                    Dialog dialog = CommonActivity.createAlertDialog(
                            getActivity(), result.getDescription(),
                            getString(R.string.app_name));
                    dialog.show();

                }
            }
        }
    };

    private boolean allowBlockOpenSub(String serviceType) {
        ApParamDAL apParamDAL = new ApParamDAL(getActivity());
        String parValue = "";
        Log.d("allowBlockOpenSub", "allowBlockOpenSub with serviceType = " + serviceType);
        try {
            apParamDAL.open();

            //Lay danh sach chi chan 1 chieu
            parValue = apParamDAL.getValue(HOME_SERVICE, BLOCK_ONE_WAY);
            if (!CommonActivity.isNullOrEmpty(parValue)) {
                if (parValue.contains("," + serviceType.toUpperCase() + ",")) {
                    blockType = "1"; //truong hop chi chan 1 chieu
                    return true;
                }
            }

            //lay danh sach chi chan 2 chieu
            parValue = apParamDAL.getValue(HOME_SERVICE, BLOCK_TWO_WAY);
            if (!CommonActivity.isNullOrEmpty(parValue)) {
                if (parValue.contains("," + serviceType.toUpperCase() + ",")) {
                    blockType = "2"; //truong hop chi chan 1 chieu
                    return true;
                }
            }

            //lay danh sach chan 1 chieu 2 chieu
            parValue = apParamDAL.getValue(HOME_SERVICE, BLOCK_ONE_TWO_WAY);
            if (!CommonActivity.isNullOrEmpty(parValue)) {
                if (parValue.contains("," + serviceType.toUpperCase() + ",")) {
                    blockType = "0"; //truong hop chi chan 1 chieu
                    return true;
                }
            }

        } catch (Exception ex) {
            Log.d("BlockOpenSubHome", "allowBlockOpenSub", ex);
        } finally {
            apParamDAL.close();
        }
        return false;
    }

    @Override
    protected void setPermission() {

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessage(BlockOpenMsg msg) {
        EventBus.getDefault().removeStickyEvent(BlockOpenMsg.class);

        getActivity().onBackPressed();
        EventBus.getDefault().postSticky(new BaseMsg());
    }

}

