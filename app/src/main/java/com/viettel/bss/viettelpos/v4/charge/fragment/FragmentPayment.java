package com.viettel.bss.viettelpos.v4.charge.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity.FragmentChooseReason;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter.GetFeeBCCSAdapter;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.GetReasonFullPMAsyn;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.AccountDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductPackageFeeDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ReasonDTO;
import com.viettel.bss.smartphonev2.commons.OnPostExecute;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentChargeLiquidationRecovery;
import com.viettel.bss.viettelpos.v4.charge.asynctask.AsyncGetDebitInfo;
import com.viettel.bss.viettelpos.v4.charge.asynctask.AsyncPaymentDebt;
import com.viettel.bss.viettelpos.v4.charge.object.VirtualInvoice;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.ActivityCreateNewRequestMobileNew;
import com.viettel.bss.viettelpos.v4.customer.asynctask.AsyncBlockOpenSub;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.message.BlockOpenMsg;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsynFindFeeByReasonTeleId;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskGetListReason;
import com.viettel.bss.viettelpos.v4.sale.object.SaleOutput;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FragmentPayment extends FragmentCommon {
    private SubscriberDTO sub;
    private String funtionTypeKey;
    private String function;
    private TextView tvCuocNong;
    private TextView tvNoDauKy;
    private TextView tvNoTruoc;
    private TextView tvNoPhatSinh;
    private TextView tvDieuChinhDuong;
    private TextView tvDieuChinhAm;
    private TextView tvKhuyenMai;
    private TextView tvDaThanhToan;
    private TextView tvTienThua;
    private TextView tvPhaiThanhToan;
    private TextView tvChietKhau;
    private EditText edtMoney;
    private Boolean isFormatted = false;
    private Button btnPayment;
    private VirtualInvoice virtualInvoice;
    private boolean isBlockOpenSub = false; //tham so kiem tra thuc hien chuc nang chan mo thue bao

    @BindView(R.id.lnReasonBlock)
    LinearLayout lnReasonBlock;
    @BindView(R.id.lnFeeType)
    LinearLayout lnFeeType;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.edtSelectReason)
    EditText edtSelectReason;
    @BindView(R.id.btnBlock)
    Button btnBlock;
    @BindView(R.id.lnWarning)
    LinearLayout lnWarning;

    @BindView(R.id.txtWarning)
    TextView txtWarning;

    private ArrayList<ReasonDTO> lstReasonDTOs = new ArrayList<ReasonDTO>();
    private ReasonDTO reasonDTO = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        idLayout = R.layout.layout_payment;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Constant.LIQUIDATION_RECOVERY.equals(funtionTypeKey)) {
            setTitleActionBar(getActivity().getString(R.string.liquidation_and_recovery));
            btnPayment.setText("Thanh toán");
            lnWarning.setVisibility(View.GONE);
            btnBlock.setVisibility(View.GONE);
            edtSelectReason.setVisibility(View.GONE);
            lnReasonBlock.setVisibility(View.GONE);
        } else
            setTitleActionBar(R.string.thanh_toan);
    }

    @Override
    public void unit(View v) {
        Bundle bundle = getArguments();
        sub = (SubscriberDTO) bundle.getSerializable("subscriberDTOKey");
        if (bundle.getString("funtionTypeKey") != null) {
            funtionTypeKey = bundle.getString("funtionTypeKey");
            function = bundle.getString("function");
        }
        if ("true".equals(bundle.getString("blockOpenSub", ""))) {
            isBlockOpenSub = true;
            virtualInvoice = (VirtualInvoice) bundle.getSerializable("virtualInvoice");
        }

        AccountDTO acc = sub.getAccountDTOForInput();
        TextView tvSoHopDong = (TextView) mView.findViewById(R.id.tvSoHopDong);
        TextView tvCusDaiDien = (TextView) mView
                .findViewById(R.id.tvCusDaiDien);
        TextView tvSubDaiDien = (TextView) mView
                .findViewById(R.id.tvSubDaiDien);
        TextView tvContractAddress = (TextView) mView
                .findViewById(R.id.tvContractAddress);
        tvSoHopDong
                .setText(getString(R.string.so_hop_dong, acc.getAccountNo()));
        tvCusDaiDien.setText(getString(R.string.khach_hang, sub
                .getCustomerDTOInput().getName()));
        tvSubDaiDien.setText(getString(R.string.thue_bao_dai_dien,
                acc.getIsdn()));
        tvContractAddress
                .setText(getString(R.string.dia_chi, acc.getAddress()));
        tvCuocNong = (TextView) mView.findViewById(R.id.tvCuocNong);
        tvNoDauKy = (TextView) mView.findViewById(R.id.tvNoDauKy);
        tvNoTruoc = (TextView) mView.findViewById(R.id.tvNoTruoc);
        tvNoPhatSinh = (TextView) mView.findViewById(R.id.tvNoPhatSinh);
        tvDieuChinhDuong = (TextView) mView.findViewById(R.id.tvDieuChinhDuong);
        tvDieuChinhAm = (TextView) mView.findViewById(R.id.tvDieuChinhAm);
        tvKhuyenMai = (TextView) mView.findViewById(R.id.tvKhuyenMai);
        tvDaThanhToan = (TextView) mView.findViewById(R.id.tvDaThanhToan);
        tvTienThua = (TextView) mView.findViewById(R.id.tvTienThua);
        tvPhaiThanhToan = (TextView) mView.findViewById(R.id.tvPhaiThanhToan);
        tvChietKhau = (TextView) mView.findViewById(R.id.tvChietKhau);
        edtMoney = (EditText) mView.findViewById(R.id.edtMoney);
        btnPayment = (Button) mView.findViewById(R.id.btnPayment);
        btnPayment.setEnabled(false);
        TextView tvIsdn = (TextView) mView.findViewById(R.id.isdnChange);
        tvIsdn.setText(getString(R.string.isdn_change, sub.getIsdn()));
        mView.findViewById(R.id.lnDebit).setVisibility(View.GONE);
        edtMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isFormatted) {
                    isFormatted = true;
                    edtMoney.setText(StringUtils.formatMoney(s.toString()
                            .replaceAll("\\.", "")));
                    edtMoney.setSelection(edtMoney.getText().toString()
                            .length());
                    isFormatted = false;
                }
            }
        });

        if (isBlockOpenSub) {
            mView.findViewById(R.id.lnDebit).setVisibility(View.VISIBLE);
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

            if (!CommonActivity.isNullOrEmpty(sub.getActionCode())) {
                if (Constant.ACTION_CODE_BLOCK_OPEN_SUB.BLOCK_ONE_WAY.equals(sub.getActionCode())) {
                    btnPayment.setText(getString(R.string.thanh_toan_chan_1c));
                    btnBlock.setText(getString(R.string.chan_1c));
                } else if (Constant.ACTION_CODE_BLOCK_OPEN_SUB.BLOCK_TWO_WAY.equals(sub.getActionCode())) {
                    btnPayment.setText(getString(R.string.thanh_toan_chan_2c));
                    btnBlock.setText(getString(R.string.chan_2c));
                }
            }

            updateDebit();
            btnPayment.setEnabled(true);

            lnReasonBlock.setVisibility(View.VISIBLE);

            if (CommonActivity.isNetworkConnected(getActivity())) {
                GetReasonFullPMAsyn asyncGetListReason = new GetReasonFullPMAsyn(getActivity(),
                        new OnPostGetReasonFullChangeProduct(), moveLogInAct);
                asyncGetListReason.setPayType(sub.getPayType());

                asyncGetListReason.execute(sub.getOfferId() + "", Constant.ACTION_CODE_BLOCK_OPEN_SUB.BLOCK_TWO_WAY,
                        sub.getTelecomServiceAlias(), sub.getCustType(),
                        sub.getSubType(), "", "");
            } else {
                CommonActivity.createAlertDialog(getActivity(),
                        getString(R.string.errorNetwork),
                        getString(R.string.app_name)).show();
            }

            SharedPreferences preferences = MainActivity.getInstance()
                    .getSharedPreferences(Define.PRE_NAME, Activity.MODE_PRIVATE);
            String name = preferences.getString(Define.KEY_MENU_NAME, "");
            if (name.contains(";sale_no_chk_debt;") || Constant.LIQUIDATION_RECOVERY.equals(funtionTypeKey)) {
                if (virtualInvoice.getNeedAmount() > 0) {
                    btnBlock.setVisibility(View.VISIBLE);
                }
            }
        } else {
            AsyncGetDebitInfo asy = new AsyncGetDebitInfo(getActivity(),
                    acc.getAccountId() + "", onPostGetDebit);
            asy.execute();
        }

        btnPayment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Double money = 0.0;
                final String strMoney = edtMoney.getText().toString()
                        .replaceAll("\\.", "").trim();
                try {
                    money = Double.parseDouble(strMoney);
                } catch (Exception e) {
                }

                if (sub.getActStatus().startsWith("2")
                        && virtualInvoice.getNeedAmount() <= 0) {
                    Intent intent = new Intent(getActivity(),
                            ActivityCreateNewRequestMobileNew.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("subscriberDTOKey", sub);
                    mBundle.putString("funtionTypeKey",
                            Constant.CHANGE_POS_TO_PRE);
                    intent.putExtras(mBundle);
                    startActivity(intent);
                    return;
                }

                if (money < virtualInvoice.getNeedAmount()) {
                    String msg = getString(
                            R.string.tien_thanh_toan_bang_tien_no, StringUtils
                                    .formatMoneyFromObject(virtualInvoice
                                            .getNeedAmount()));
                    CommonActivity.createErrorDialog(act, msg, "1").show();
                    return;
                }

                if (isBlockOpenSub && !Constant.LIQUIDATION_RECOVERY.equals(funtionTypeKey)) {
                    if (CommonActivity.isNullOrEmpty(reasonDTO)) {
                        CommonActivity.toast(getActivity(), getString(R.string.block_open_sub_reason_required));
                        return;
                    }
                }

                String strPayAmount = StringUtils.formatMoney(strMoney);
                String accountNo = sub.getAccountDTOForInput().getAccountNo();
                String isdn = sub.getIsdn();
                String confirm = getString(R.string.confirm_thanh_toan_chan_2c,
                        strPayAmount, accountNo, isdn);
                if (sub.getActStatus().startsWith("2")) {
                    confirm = getString(R.string.confirm_thanh_toan,
                            strPayAmount, accountNo);
                } else if (virtualInvoice.getNeedAmount() <= 0) {
                    confirm = getString(R.string.confirm_chan_2c, sub.getIsdn());
                }

                if (isBlockOpenSub) {
                    if (!CommonActivity.isNullOrEmpty(sub.getActionCode())) {
                        if (Constant.ACTION_CODE_BLOCK_OPEN_SUB.BLOCK_TWO_WAY.equals(sub.getActionCode())) {
                            if (money > 0) {
                                confirm = getString(R.string.confirm_thanh_toan_chan_2c,
                                        strPayAmount, accountNo, isdn);
                            } else {
                                confirm = getString(R.string.block_open_confirm_chan_hai_chieu, isdn);
                            }
                        } else if (Constant.ACTION_CODE_BLOCK_OPEN_SUB.BLOCK_ONE_WAY.equals(sub.getActionCode())) {
                            if (money > 0) {
                                confirm = getString(R.string.confirm_thanh_toan_chan_1c,
                                        strPayAmount, accountNo, isdn);
                            } else {
                                confirm = getString(R.string.block_open_confirm_chan_mot_chieu, isdn);
                            }
                        }
                    }
                }

                View.OnClickListener onClick = new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {

                        if (virtualInvoice.getNeedAmount() <= 0 && !Constant.LIQUIDATION_RECOVERY.equals(funtionTypeKey)) {
                            // Neu no cuoc <= 0, chan 2 chieu
                            if (isBlockOpenSub) {
                                AsyncBlockOpenSub block = new AsyncBlockOpenSub(
                                        getActivity(), getBlockMode(sub.getActionCode()), sub, block2WayPost, reasonDTO.getReasonId());
                                block.execute();
                            } else {
                                AsyncBlockOpenSub block = new AsyncBlockOpenSub(
                                        getActivity(), "2", sub, block2WayPost);
                                block.execute();
                            }
                        } else {
                            // Thuc hien thanh toan
                            AsyncPaymentDebt payment = new AsyncPaymentDebt(
                                    getActivity(), sub.getAccountDTOForInput()
                                    .getAccountId(), sub.getIsdn(),
                                    strMoney, onPostPaymentExecute);
                            payment.execute();
                        }

                    }
                };
                if (Constant.LIQUIDATION_RECOVERY.equals(funtionTypeKey)) {
                    confirm = getString(R.string.confirm_thanh_toan,
                            strPayAmount, accountNo);
                }
                CommonActivity.createDialog(getActivity(), confirm,
                        getString(R.string.app_name), getString(R.string.ok),
                        getString(R.string.cancel), onClick, null).show();

            }
        });


        btnBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBlockOpenSub) {
                    if (CommonActivity.isNullOrEmpty(reasonDTO)) {
                        CommonActivity.toast(getActivity(), getString(R.string.block_open_sub_reason_required));
                        return;
                    }
                }

                String confirm = "";
                if (!CommonActivity.isNullOrEmpty(sub.getActionCode())) {
                    if (Constant.ACTION_CODE_BLOCK_OPEN_SUB.BLOCK_TWO_WAY.equals(sub.getActionCode())) {
                        confirm = getString(R.string.block_open_confirm_chan_hai_chieu, sub.getIsdn());
                    } else if (Constant.ACTION_CODE_BLOCK_OPEN_SUB.BLOCK_ONE_WAY.equals(sub.getActionCode())) {
                        confirm = getString(R.string.block_open_confirm_chan_mot_chieu, sub.getIsdn());
                    }
                }

                View.OnClickListener onClick = new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        AsyncBlockOpenSub block = new AsyncBlockOpenSub(
                                getActivity(), getBlockMode(sub.getActionCode()), sub, onlyBlockPost, reasonDTO.getReasonId());
                        block.execute();
                    }
                };

                CommonActivity.createDialog(getActivity(), confirm,
                        getString(R.string.app_name), getString(R.string.cancel),
                        getString(R.string.ok), null, onClick).show();
            }
        });
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

    @Override
    protected void setPermission() {

    }

    // ly do tac dong
    private class OnPostGetReasonFullChangeProduct implements OnPostExecuteListener<List<ReasonDTO>> {
        @Override
        public void onPostExecute(List<ReasonDTO> result, String errorCode, String description) {
            if (!CommonActivity.isNullOrEmptyArray(result)) {
                lstReasonDTOs.addAll(result);
            } else {
                lstReasonDTOs = new ArrayList<ReasonDTO>();
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notdatareason),
                        getActivity().getString(R.string.app_name)).show();
            }
        }
    }

    OnPostExecute<SaleOutput> onPostGetDebit = new OnPostExecute<SaleOutput>() {
        @Override
        public void onPostExecute(SaleOutput result) {
            if ("0".equals(result.getErrorCode())) {
                // neu thue bao da duoc chan 2 chieu va khong no cuoc, sang man
                // hinh chuyen doi
                mView.findViewById(R.id.lnDebit).setVisibility(View.VISIBLE);
                virtualInvoice = result.getVirtualInvoice();

                if (virtualInvoice.getNeedAmount() <= 0) {
                    btnPayment.setText(getString(R.string.chan_2c));
                }

                if (sub.getActStatus().startsWith("2")) {
                    btnPayment.setText(getString(R.string.thanh_toan));
                }

                if (sub.getActStatus().startsWith("2")
                        && virtualInvoice.getNeedAmount() <= 0) {
                    btnPayment.setText(getString(R.string.chuyen_sang_tra_truoc));
                }

                updateDebit();

                //
                btnPayment.setEnabled(true);
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
    private OnPostExecute<SaleOutput> onPostPaymentExecute = new OnPostExecute<SaleOutput>() {
        @Override
        public void onPostExecute(SaleOutput result) {
            if ("0".equals(result.getErrorCode())) {
                btnPayment.setText(R.string.chan_2c);


                resetDebit();
                updateDebit();

                // TODO neu Typq = thanh ly thi chuyen qua man hinh thanh ly

                if (Constant.LIQUIDATION_RECOVERY.equals(funtionTypeKey)) {
                    View.OnClickListener listener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle = new Bundle();
                            com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO subscriberDTO = new com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO();
                            if(!CommonActivity.isNullOrEmpty(sub.getAccount()))
                                subscriberDTO.setAccount(sub.getAccount());
                            else if(!CommonActivity.isNullOrEmpty(sub.getIsdn()))
                                subscriberDTO.setAccount(sub.getIsdn());
                            subscriberDTO.setSubId(sub.getSubId() + "");
                            subscriberDTO.setOfferId(sub.getOfferId() + "");
                            subscriberDTO.setTelecomServiceAlias(sub.getTelecomServiceAlias() + "");
                            subscriberDTO.setSubType(sub.getSubType() + "");
                            subscriberDTO.setCustType(sub.getCustType() + "");
                            subscriberDTO.setTelecomServiceId(sub.getTelecomServiceId() + "");
                            subscriberDTO.setProductCode(sub.getProductCode() + "");
                            subscriberDTO.setRawData(sub.getRawData());
                            subscriberDTO.setCdtCode(sub.getCdtCode());

                            bundle.putSerializable("subscriberDTO", subscriberDTO);
                            bundle.putString("function", function);
                            FragmentChargeLiquidationRecovery fragmentTarget = new FragmentChargeLiquidationRecovery();
                            fragmentTarget.setArguments(bundle);
                            ReplaceFragment.replaceFragment(getActivity(), fragmentTarget, false);
                        }
                    };
                    CommonActivity.createDialog(
                            getActivity(),
                            getResources().getString(
                                    R.string.revoke_confirm_)+" "+function.toLowerCase()+" ?",
                            getResources().getString(
                                    R.string.app_name),
                            getResources().getString(R.string.say_ko),
                            getResources().getString(R.string.say_co),
                            null, listener).show();
                } else {

                    if (sub.getActStatus().startsWith("2")) {
                        edtMoney.setText("0");
                        // Truong hop da chan 2 chieu, chuyen luon sang man hinh
                        // tich hop
                        Intent intent = new Intent(getActivity(),
                                ActivityCreateNewRequestMobileNew.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putSerializable("subscriberDTOKey", sub);
                        mBundle.putString("funtionTypeKey",
                                Constant.CHANGE_POS_TO_PRE);
                        intent.putExtras(mBundle);
                        startActivity(intent);

                    } else {
                        // Goi ham chan 2 chieu
                        if (isBlockOpenSub) {
                            AsyncBlockOpenSub block = new AsyncBlockOpenSub(
                                    getActivity(), getBlockMode(sub.getActionCode()), sub, block2WayPost, reasonDTO.getReasonId());
                            block.execute();
                        } else {
                            AsyncBlockOpenSub block = new AsyncBlockOpenSub(
                                    getActivity(), "2", sub, block2WayPost);
                            block.execute();
                        }
                    }
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

    private OnPostExecute<SaleOutput> block2WayPost = new OnPostExecute<SaleOutput>() {
        @Override
        public void onPostExecute(SaleOutput result) {

            if ("0".equals(result.getErrorCode())) {
                if (isBlockOpenSub) {
                    Dialog dialog = CommonActivity.createAlertDialog(
                            getActivity(), getString(R.string.block_open_tonghop_cuocnong_success, sub.getIsdn()),
                            getString(R.string.app_name), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getActivity().onBackPressed();
                                    EventBus.getDefault().postSticky(new BlockOpenMsg());
                                }
                            });
                    dialog.show();
                } else {
                    sub.setActStatus("200");
                    Intent intent = new Intent(getActivity(),
                            ActivityCreateNewRequestMobileNew.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("subscriberDTOKey", sub);
                    mBundle.putString("funtionTypeKey", Constant.CHANGE_POS_TO_PRE);
                    intent.putExtras(mBundle);
                    startActivity(intent);
                }
                return;
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

    private OnPostExecute<SaleOutput> onlyBlockPost = new OnPostExecute<SaleOutput>() {
        @Override
        public void onPostExecute(SaleOutput result) {

            if ("0".equals(result.getErrorCode())) {
                String msg = getString(R.string.block_open_chan_mot_chieu_success, sub.getIsdn());
                if (Constant.ACTION_CODE_BLOCK_OPEN_SUB.BLOCK_TWO_WAY.equals(sub.getActionCode())) {
                    msg = getString(R.string.block_open_chan_hai_chieu_success, sub.getIsdn());
                }
                Dialog dialog = CommonActivity.createAlertDialog(
                        getActivity(), msg,
                        getString(R.string.app_name), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getActivity().onBackPressed();
                                EventBus.getDefault().postSticky(new BlockOpenMsg());
                            }
                        });
                dialog.show();
                return;
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

    private void updateDebit() {
        tvChietKhau.setText(getString(R.string.chiet_khau, StringUtils
                .formatMoneyFromObject(virtualInvoice.getDiscount())));
        tvCuocNong.setText(getString(R.string.hotCharge, StringUtils
                .formatMoneyFromObject(virtualInvoice.getHotCharge())));
        tvDaThanhToan.setText(getString(R.string.da_thanh_toan,
                StringUtils.formatMoneyFromObject(virtualInvoice
                        .getPaymentAmount())));
        tvDieuChinhAm.setText(getString(R.string.dieu_chinh_am,
                StringUtils.formatMoneyFromObject(virtualInvoice
                        .getAdjustmentNegative())));
        tvDieuChinhDuong.setText(getString(R.string.dieu_chinh_duong,
                StringUtils.formatMoneyFromObject(virtualInvoice
                        .getAdjustmentPositive())));
        tvKhuyenMai.setText(getString(R.string.khuyen_mai, StringUtils
                .formatMoneyFromObject(virtualInvoice.getPromotion())));
        tvNoDauKy.setText(getString(R.string.no_dau_ky, StringUtils
                .formatMoneyFromObject(virtualInvoice.getAmount())));
        // tvNoPhatSinh.setText(getString(R.string.no_phat_sinh,
        // StringUtils.formatMoneyFromObject(v.getAmountDebit())));
//        if (isBlockOpenSub) {
//            virtualInvoice
//                    .setNeedAmount(virtualInvoice.getNeedAmount() - virtualInvoice.getHotCharge());
//        }

        tvPhaiThanhToan.setText(getString(R.string.phai_thanh_toan,
                StringUtils.formatMoneyFromObject(virtualInvoice
                        .getNeedAmount())));
        tvTienThua.setText(getString(R.string.tien_thua, StringUtils
                .formatMoneyFromObject(virtualInvoice
                        .getContractRemain())));
        edtMoney.setText(StringUtils
                .formatMoneyFromObject(virtualInvoice.getNeedAmount()));
    }

    private String getBlockMode(String actionCode) {
        if (Constant.ACTION_CODE_BLOCK_OPEN_SUB.BLOCK_ONE_WAY.equals(actionCode)) {
            return Constant.BLOCK_MODE_BOSUB.BLOCK_ONE_WAY;
        } else if (Constant.ACTION_CODE_BLOCK_OPEN_SUB.BLOCK_TWO_WAY.equals(actionCode)) {
            return Constant.BLOCK_MODE_BOSUB.BLOCK_TWO_WAY;
        } else {
            return Constant.BLOCK_MODE_BOSUB.OPEN;
        }
    }

    private void resetDebit() {
        virtualInvoice.setDiscount(0l);
        virtualInvoice.setHotCharge(0.0);
        virtualInvoice.setPaymentAmount(0.0);
        virtualInvoice.setAdjustmentNegative(0.0);
        virtualInvoice.setAdjustmentPositive(0.0);
        virtualInvoice.setPromotion(0l);
        virtualInvoice.setAmount(0.0);
        virtualInvoice.setNeedAmount(0.0);
        virtualInvoice.setContractRemain(0.0);
        btnBlock.setVisibility(View.VISIBLE);
        edtMoney.setText("");
    }
}

