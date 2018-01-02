package com.viettel.bss.viettelpos.v4.customer.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity.FragmentChooseReason;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter.GetFeeBCCSAdapter;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.GetReasonFullPMAsyn;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductPackageFeeDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ReasonDTO;
import com.viettel.bss.smartphonev2.commons.OnPostExecute;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.charge.asynctask.AsyncGetDebitInfo;
import com.viettel.bss.viettelpos.v4.charge.fragment.FragmentPayment;
import com.viettel.bss.viettelpos.v4.charge.object.VirtualInvoice;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.ReasonCommon;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.HotLineReponseDetail;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ReceiveRequestBean;
import com.viettel.bss.viettelpos.v4.customer.asynctask.AsyncBlockOpenSub;
import com.viettel.bss.viettelpos.v4.customer.asynctask.AsyncCollectHotCharge;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.message.BaseMsg;
import com.viettel.bss.viettelpos.v4.message.BlockOpenMsg;
import com.viettel.bss.viettelpos.v4.object.FormBean;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsynFindFeeByReasonTeleId;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskGetListReason;
import com.viettel.bss.viettelpos.v4.sale.object.SaleOutput;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class FragmentBlockOpenSub extends FragmentCommon {
    private Spinner spiReason;
    private Spinner spiAction;
    private Button btnBlockOpenSub;
    private EditText edtIsdn;
    private SubscriberDTO sub = null;
    private ProgressBar prbReason;
    private Button btnRefreshReason;
    private Button btnExecute;
    private LinearLayout lnFeeType;
    private TextView tvDialogTitle;
    private LinearLayout lnTitle;
    private LinearLayout lnActionType;
    private RadioGroup rgActionType;
    private RadioButton rbBlockOneWay;
    private RadioButton rbBlockTwoWay;
    private RadioButton rbOpenOneWay;
    private RadioButton rbOpenTwoWay;
    private RadioButton rbSyntheticChargeHot;
    private ListView listView;
    private String funtionTypeKey;
    private List<FormBean> lstAction = new ArrayList<FormBean>();
    private VirtualInvoice virtualInvoice;
    private String actionCode = Constant.ACTION_CODE_BLOCK_OPEN_SUB.BLOCK_TWO_WAY;
    private String blockMode = Constant.BLOCK_MODE_BOSUB.BLOCK_ONE_WAY;
    private boolean isBlockTwoWay = false;
    private EditText edtSelectReason;
    private ArrayList<ReasonDTO> lstReasonDTOs = new ArrayList<ReasonDTO>();
    private ReasonDTO reasonDTO = null;
    private boolean forwardToPayment = false;
    private LinearLayout lnReason;

    public ReceiveRequestBean receiveRequestBean = new ReceiveRequestBean();
    public HotLineReponseDetail hotLineReponseDetail = new HotLineReponseDetail();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        idLayout = R.layout.layout_block_open_sub;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        String titile = getString(R.string.change_pos_to_pre);
        if (Constant.BLOCK_OPEN_SUB_MOBILE.equals(funtionTypeKey)) {
            titile = getString(R.string.blockOpenSubMobile);
        } else if (Constant.BLOCK_OPEN_SUB_HOMEPHONE.equals(funtionTypeKey)) {
            titile = getString(R.string.blockOpenSubHomephone);
        }
        setTitleActionBar(titile);
    }

    @Override
    public void unit(View v) {

        spiReason = (Spinner) v.findViewById(R.id.spiReason);
        spiAction = (Spinner) v.findViewById(R.id.spiAction);
        prbReason = (ProgressBar) v.findViewById(R.id.prbReason);
        btnRefreshReason = (Button) v.findViewById(R.id.btnRefreshReason);
        btnBlockOpenSub = (Button) v.findViewById(R.id.btnBlockOpenSub);
        edtIsdn = (EditText) v.findViewById(R.id.edtIsdn);
        Bundle bundle = getArguments();
        sub = (SubscriberDTO) bundle.getSerializable("subscriberDTOKey");
        funtionTypeKey = bundle.getString("funtionTypeKey");
        if (!"000".equals(sub.getActStatus())) {
            btnBlockOpenSub.setText(R.string.collect_hot_charge);
        }
        edtIsdn.setText(sub.getIsdn());
        edtIsdn.setEnabled(false);
        lnFeeType = (LinearLayout) v.findViewById(R.id.lnFeeType);
        tvDialogTitle = (TextView) v.findViewById(R.id.tvDialogTitle);
        lnTitle = (LinearLayout) v.findViewById(R.id.lnTitle);
        lnActionType = (LinearLayout) v.findViewById(R.id.lnActionType);
        lnReason = (LinearLayout) v.findViewById(R.id.lnReason);

        rgActionType = (RadioGroup) v.findViewById(R.id.rgActionType);
        rbBlockOneWay = (RadioButton) v.findViewById(R.id.rbBlockOneWay);
        rbBlockTwoWay = (RadioButton) v.findViewById(R.id.rbBlockTwoWay);
        rbOpenOneWay = (RadioButton) v.findViewById(R.id.rbOpenOneWay);
        rbOpenTwoWay = (RadioButton) v.findViewById(R.id.rbOpenTwoWay);
        rbSyntheticChargeHot = (RadioButton) v.findViewById(R.id.rbSyntheticChargeHot);
        btnExecute = (Button) v.findViewById(R.id.btnExecute);
        listView = (ListView) v.findViewById(R.id.listView);
        edtSelectReason = (EditText) v.findViewById(R.id.edtSelectReason);
        edtSelectReason.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (!CommonActivity.isNullOrEmptyArray(lstReasonDTOs)) {
                    Intent intent = new Intent(getActivity(),
                            FragmentChooseReason.class);
                    intent.putExtra("arrReasonDTO", lstReasonDTOs);
                    startActivityForResult(intent, 1235);
                } else {
                    if (selectActionType()) {
                        CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notdatareason),
                                getActivity().getString(R.string.app_name)).show();
                    } else {
                        CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.block_open_sub_action_type_required),
                                getActivity().getString(R.string.app_name)).show();
                    }
                }
            }
        });

        if (Constant.BLOCK_OPEN_SUB_HOMEPHONE.equals(funtionTypeKey)
                || Constant.BLOCK_OPEN_SUB_MOBILE.equals(funtionTypeKey)) {
            lnTitle.setVisibility(View.GONE);
            lnActionType.setVisibility(View.VISIBLE);
            btnBlockOpenSub.setVisibility(View.GONE);
            btnExecute.setVisibility(View.VISIBLE);
            edtSelectReason.setVisibility(View.VISIBLE);
            spiReason.setVisibility(View.GONE);
            if (!CommonActivity.isNullOrEmpty(sub.getActStatus())) {
                if (sub.getActStatus().startsWith("0")) { //trang thai binh thuong
                    rbBlockOneWay.setVisibility(View.VISIBLE);
                    if (!"1".equals(sub.getPayType())) { // neu la tra truoc
                        rbBlockTwoWay.setVisibility(View.VISIBLE);
                    }
                } else if (sub.getActStatus().startsWith("1")) { //trang thai chan 1 chieu
                    rbOpenOneWay.setVisibility(View.VISIBLE);
                    if ("1".equals(sub.getPayType())) {
                        rbSyntheticChargeHot.setVisibility(View.VISIBLE); //tong hop cuoc nong truong hop thue bao tra sau
                    } else {
                        rbBlockTwoWay.setVisibility(View.VISIBLE);
                    }
                } else if (sub.getActStatus().startsWith("2")) {
                    rbOpenTwoWay.setVisibility(View.VISIBLE);
                }
            }
        } else {
            edtSelectReason.setVisibility(View.GONE);
            spiReason.setVisibility(View.VISIBLE);
        }

        rgActionType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                forwardToPayment = false;
                switch (checkedId) {
                    case R.id.rbBlockOneWay:
                        actionCode = Constant.ACTION_CODE_BLOCK_OPEN_SUB.BLOCK_ONE_WAY;
                        break;
                    case R.id.rbBlockTwoWay:
                        actionCode = Constant.ACTION_CODE_BLOCK_OPEN_SUB.BLOCK_TWO_WAY;
                        break;
                    case R.id.rbOpenOneWay:
                        actionCode = Constant.ACTION_CODE_BLOCK_OPEN_SUB.OPEN_ONE_WAY;
                        break;
                    case R.id.rbOpenTwoWay:
                        actionCode = Constant.ACTION_CODE_BLOCK_OPEN_SUB.OPEN_TWO_WAY;
                        break;
                    case R.id.rbSyntheticChargeHot:
                        actionCode = Constant.ACTION_CODE_BLOCK_OPEN_SUB.BLOCK_TWO_WAY;

                        AsyncCollectHotCharge asy = new AsyncCollectHotCharge(
                                sub.getAccountDTOForInput().getAccountId(),
                                sub.getSubId(),
                                getString(R.string.dang_tong_hop_cuoc_nong),
                                collectHotchargePost, getActivity());
                        asy.execute();
                        return;
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

        btnBlockOpenSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (forwardToPayment) {
                    AsyncCollectHotCharge asy = new AsyncCollectHotCharge(
                            sub.getAccountDTOForInput().getAccountId(),
                            sub.getSubId(),
                            getString(R.string.dang_tong_hop_cuoc_nong),
                            collectHotchargePost, getActivity());
                    asy.execute();
                    return;
                }

                if (edtIsdn.getText().toString().isEmpty()) {
                    CommonActivity.createAlertDialog(getActivity(),
                            getString(R.string.chua_nhap_thue_bao),
                            getString(R.string.app_name)).show();
                    edtIsdn.requestFocus();
                    return;
                }

                if (!CommonActivity.validateHomephone(edtIsdn.getText().toString()
                        .trim())) {
                    CommonActivity.createAlertDialog(getActivity(),
                            getString(R.string.sdt_chan_mo),
                            getString(R.string.app_name)).show();
                    edtIsdn.requestFocus();
                    return;
                }

                OnClickListener onClick = new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        if ("000".equals(sub.getActStatus())) {
                            // Neu thue bao hoat dong 2 chieu, chan 1 chieu roi
                            // tong hop cuoc nong
                            AsyncBlockOpenSub block = new AsyncBlockOpenSub(
                                    getActivity(), "1", sub,
                                    blockOpenSubPost);
                            block.execute();
                        } else {
                            // Neu thue bao da bi chan 1 chieu or 2 chi can tong
                            // hop cuoc nong
                            AsyncCollectHotCharge asy = new AsyncCollectHotCharge(
                                    sub.getAccountDTOForInput().getAccountId(),
                                    sub.getSubId(),
                                    getString(R.string.dang_tong_hop_cuoc_nong),
                                    collectHotchargePost, getActivity());
                            asy.execute();
                        }
                    }
                };
                String confirm = getString(R.string.confirm_chan_mo,
                        sub.getIsdn());
                if (!"000".equals(sub.getActStatus())) {
                    confirm = getString(R.string.confirm_collect_hot_charge,
                            sub.getIsdn());

                }
                CommonActivity.createDialog(getActivity(), confirm,
                        getString(R.string.app_name), getString(R.string.cancel),
                        getString(R.string.ok), null, onClick).show();
            }
        });

        btnExecute.setOnClickListener(new OnClickListener() {
            private String THCN = "THCN";

            @Override
            public void onClick(View v) {
                if (rbBlockOneWay.isChecked()) {
                    blockMode = Constant.BLOCK_MODE_BOSUB.BLOCK_ONE_WAY;
                } else if (rbBlockTwoWay.isChecked()) {
                    blockMode = Constant.BLOCK_MODE_BOSUB.BLOCK_TWO_WAY;
                } else if (rbOpenOneWay.isChecked() || rbOpenTwoWay.isChecked()) {
                    blockMode = Constant.BLOCK_MODE_BOSUB.OPEN;
                } else if (rbSyntheticChargeHot.isChecked() && !isBlockTwoWay) {
                    blockMode = THCN;
                } else if (rbSyntheticChargeHot.isChecked() && isBlockTwoWay) {
                    blockMode = Constant.BLOCK_MODE_BOSUB.BLOCK_TWO_WAY;
                } else {
                    CommonActivity.toast(getActivity(), getString(R.string.block_open_sub_action_type_required));
                    return;
                }

                if (CommonActivity.isNullOrEmpty(reasonDTO) || CommonActivity.isNullOrEmpty(reasonDTO.getReasonId())) {
                    CommonActivity.toast(getActivity(), getString(R.string.block_open_sub_reason_required));
                    return;
                }

                OnClickListener onClick = new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        if (THCN.equals(blockMode)) {
                            AsyncCollectHotCharge asy = new AsyncCollectHotCharge(
                                    sub.getAccountDTOForInput().getAccountId(),
                                    sub.getSubId(),
                                    getString(R.string.dang_tong_hop_cuoc_nong),
                                    collectHotchargePost, getActivity());
                            asy.execute();
                        } else {
                            AsyncBlockOpenSub block = new AsyncBlockOpenSub(
                                    getActivity(), blockMode, sub,
                                    blockOpenSubMobile, reasonDTO.getReasonId());
                            block.execute();
                        }
                    }
                };


                String confirm = getString(R.string.block_open_confirm_chan_mot_chieu,
                        sub.getIsdn());
                if (Constant.BLOCK_MODE_BOSUB.BLOCK_ONE_WAY.equals(blockMode)) {
                    confirm = getString(R.string.block_open_confirm_chan_mot_chieu,
                            sub.getIsdn());
                } else if (Constant.BLOCK_MODE_BOSUB.BLOCK_TWO_WAY.equals(blockMode)) {
                    confirm = getString(R.string.block_open_confirm_chan_hai_chieu,
                            sub.getIsdn());
                } else if (Constant.BLOCK_MODE_BOSUB.OPEN.equals(blockMode)) {
                    confirm = getString(R.string.block_open_confirm_mo_chan,
                            sub.getIsdn());
                } else {
                    confirm = getString(R.string.block_open_tonghop_cuocnong,
                            sub.getIsdn());
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
        spiAction.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });

        btnRefreshReason.setVisibility(View.GONE);
        prbReason.setVisibility(View.GONE);

        List<FormBean> lstReason = new ArrayList<FormBean>();
        FormBean bean = new FormBean();
        bean.setCode(ReasonCommon.CODE_REASON_BLOCK_KHYC);
        bean.setId(ReasonCommon.ID_REASON_BLOCK_1C_KHYC);
        bean.setName(getString(R.string.chan_khyc));
        lstReason.add(bean);
        Utils.setDataSpinnerForm(getActivity(), lstReason, spiReason);
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
                default:
                    break;
            }
        }
    }

    private OnPostExecute<SaleOutput> collectHotchargePost = new OnPostExecute<SaleOutput>() {

        @Override
        public void onPostExecute(SaleOutput result) {
            if ("0".equals(result.getErrorCode())) {
                if(Constant.BLOCK_OPEN_SUB_MOBILE.equals(funtionTypeKey)
                        || Constant.BLOCK_OPEN_SUB_HOMEPHONE.equals(funtionTypeKey)) {
                    AsyncGetDebitInfo asy = new AsyncGetDebitInfo(getActivity(),
                            sub.getAccountDTOForInput().getAccountId() + "", onPostGetDebit);
                    asy.execute();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("subscriberDTOKey", sub);
                    FragmentPayment payment = new FragmentPayment();
                    payment.setArguments(bundle);
                    ReplaceFragment.replaceFragment(getActivity(), payment, false);
                }
            } else {
                btnBlockOpenSub.setText(R.string.collect_hot_charge);
                if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
                    CommonActivity.BackFromLogin(getActivity(),
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


    private OnPostExecute<SaleOutput> blockOpenSubPost = new OnPostExecute<SaleOutput>() {
        @Override
        public void onPostExecute(SaleOutput result) {

            if ("0".equals(result.getErrorCode())) {
                btnBlockOpenSub.setText(R.string.collect_hot_charge);
                sub.setActStatus("100");
                AsyncCollectHotCharge asy = new AsyncCollectHotCharge(sub
                        .getAccountDTOForInput().getAccountId(),
                        sub.getSubId(),
                        getString(R.string.chan_thanh_cong_tong_hop_cuoc_nong),
                        collectHotchargePost, getActivity());
                asy.execute();
            } else {

                if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
                    CommonActivity.BackFromLogin(getActivity(),
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

    private OnPostExecute<SaleOutput> blockOpenSubMobile = new OnPostExecute<SaleOutput>() {
        @Override
        public void onPostExecute(SaleOutput result) {

            if ("0".equals(result.getErrorCode())) {
                String msg = getString(R.string.block_open_chan_mot_chieu_success, sub.getIsdn());
                if (rbBlockTwoWay.isChecked()) {
                    msg = getString(R.string.block_open_chan_hai_chieu_success, sub.getIsdn());
                } else if (rbOpenOneWay.isChecked() || rbOpenTwoWay.isChecked()) {
                    msg = getString(R.string.block_open_mo_chan_success, sub.getIsdn());
                } else if (rbSyntheticChargeHot.isChecked() && !isBlockTwoWay) {
                    msg = getString(R.string.block_open_tonghop_cuocnong_success, sub.getIsdn());
                } else if (rbSyntheticChargeHot.isChecked() && isBlockTwoWay) {
                    msg = getString(R.string.block_open_chan_hai_chieu_success, sub.getIsdn());
                }

                Dialog dialog = CommonActivity.createAlertDialog(
                        getActivity(), msg,
                        getString(R.string.app_name), new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                EventBus.getDefault().postSticky(new BaseMsg());
                                getActivity().onBackPressed();
                            }
                        });
                dialog.show();
            } else {

                if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
                    CommonActivity.BackFromLogin(getActivity(),
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
            if ("0".equals(result.getErrorCode())) {
                virtualInvoice = result.getVirtualInvoice();


                if (virtualInvoice.getNeedAmount() <= 0) {
                    btnExecute.setText(getString(R.string.chan_2c));
                    isBlockTwoWay = true;

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
                } else { //chuyen sang man hinh thanh toan
                    forwardToPayment = true;
                    lnReason.setVisibility(View.GONE);

                    Bundle bundle = new Bundle();
                    sub.setActionCode(actionCode);
                    bundle.putSerializable("subscriberDTOKey", sub);
                    if (rbSyntheticChargeHot.isChecked()) {
                        bundle.putString("blockOpenSub", "true");
                        bundle.putSerializable("virtualInvoice", virtualInvoice);
                    }
                    // check code de them thong bao
                    bundle.putString("funtionTypeKey", funtionTypeKey);
                    FragmentPayment payment = new FragmentPayment();
                    payment.setArguments(bundle);
                    ReplaceFragment.replaceFragment(getActivity(), payment, false);
                }

            } else {
                if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
                    CommonActivity.BackFromLogin(getActivity(),
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

    private boolean selectActionType() {
        return rbOpenTwoWay.isChecked() || rbBlockOneWay.isChecked() || rbBlockTwoWay.isChecked() || rbOpenOneWay.isChecked() || rbSyntheticChargeHot.isChecked();
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
        EventBus.getDefault().post(new BaseMsg());
    }
}
