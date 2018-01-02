package com.viettel.bss.viettelpos.v4.sale.confirm.debt.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionService.listener.ExpandableHeightListView;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.sale.confirm.debt.adapter.StaffDebtAdapter;
import com.viettel.bss.viettelpos.v4.sale.confirm.debt.asynctask.ConfirmDebitTransAsync;
import com.viettel.bss.viettelpos.v4.sale.confirm.debt.asynctask.FindDetailByOwnerIdAndOwnerTypeAndCycleAsync;
import com.viettel.bss.viettelpos.v4.sale.object.ConfirmDebitTransDTO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by toancx on 4/25/2017.
 */

public class DebtStaffDetailFragment extends Fragment {
    final String permission = ";confirm.debit;";
    View mView;

    @BindView(R.id.lvDebt)
    ExpandableHeightListView lvDebt;
    @BindView(R.id.tvStartBalance)
    TextView tvStartBalance;
    @BindView(R.id.tvEndBalance)
    TextView tvEndBalance;
    @BindView(R.id.tvConfirm)
    TextView tvConfirm;
    @BindView(R.id.tvDifferent)
    TextView tvDifferent;
    @BindView(R.id.tvReason)
    TextView tvReason;
    @BindView(R.id.lnDebtStaff)
    View lnDebtStaff;
    @BindView(R.id.lnDebtViettel)
    View lnDebtViettel;
    @BindView(R.id.btnConfirmDebt)
    Button btnConfirmDebt;
    private String fromDate;
    private Long status;
    private ConfirmDebitTransDTO debtViettel;
    private List<ConfirmDebitTransDTO> lstDebtStaff;
private StaffDebtAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.layout_debt_staff_detail, container, false);
        ButterKnife.bind(this, mView);
        Bundle bundle = getArguments();
        if (bundle != null) {
            fromDate = bundle.getString("fromDate");
            status = bundle.getLong("status");
        }
        lnDebtStaff.setVisibility(View.GONE);
        FindDetailByOwnerIdAndOwnerTypeAndCycleAsync asy = new FindDetailByOwnerIdAndOwnerTypeAndCycleAsync(getActivity(), getDetailListener, moveLogInAct, fromDate, status);
        asy.execute();
        lvDebt.setExpanded(true);
        lvDebt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position != lstDebtStaff.size()-1){
                    showDialogStaffDebt(lstDebtStaff.get(position));
                }

            }
        });
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.getInstance().setTitleActionBar(R.string.confirm_debit, false);
    }

    OnPostExecuteListener<List<ConfirmDebitTransDTO>> getDetailListener = new OnPostExecuteListener<List<ConfirmDebitTransDTO>>() {
        @Override
        public void onPostExecute(List<ConfirmDebitTransDTO> result, String errorCode, String description) {
            if (CommonActivity.isNullOrEmpty(result)) {
                CommonActivity.createErrorDialog(getActivity(), getString(R.string.no_debt_staff_found, Session.userName, fromDate), "1").show();
                return;
            }
            lstDebtStaff = result;
            long needConfirmSum = 0l;
            long confirmSum = 0l;
            long differentSum = 0l;
            if (lstDebtStaff.get(0).getStatus().compareTo(0L) == 0) {
                btnConfirmDebt.setVisibility(View.VISIBLE);
            } else {
                btnConfirmDebt.setVisibility(View.GONE);
            }
            for (int i = 0; i < lstDebtStaff.size(); i++) {
                if (lstDebtStaff.get(i).getDebitType().compareTo(2L) == 0) {
                    debtViettel = lstDebtStaff.get(i);
                    lstDebtStaff.remove(i);
                    break;
                }
            }

            for (ConfirmDebitTransDTO item : lstDebtStaff) {
                needConfirmSum = needConfirmSum + item.getEndCycleAmount();
                Long confirmed = item.getConfirmPay() == null ? 0L : item.getConfirmPay();
                confirmSum = confirmSum + confirmed;
                long different = item.getPayDifferent();
                differentSum = differentSum + different;
            }
            if (!CommonActivity.isNullOrEmpty(lstDebtStaff)) {
                lnDebtStaff.setVisibility(View.VISIBLE);
                ConfirmDebitTransDTO sum = new ConfirmDebitTransDTO();
                sum.setDebitTypeDetailName(getString(R.string.sum_all));
                sum.setDebitTypeDetail(-1L);
                sum.setEndCycleAmount(needConfirmSum);
                sum.setConfirmPay(confirmSum);
                sum.setPayDifferent(differentSum);
                lstDebtStaff.add(sum);
                 adapter = new StaffDebtAdapter(getActivity(), lstDebtStaff);
                lvDebt.setAdapter(adapter);
            } else {
                lnDebtStaff.setVisibility(View.GONE);
            }
            showViettelDebt();
        }
    };



    @OnClick(R.id.btnConfirmDebt)
    void showDialogConfirm() {
        String confirmMsg = getString(R.string.confirm_confirm_debt);
        String title = getString(R.string.app_name);
        String left = getString(R.string.cancel);
        String right = getString(R.string.ok);
        CommonActivity.createDialog(getActivity(), confirmMsg, title, left, right, null, confirmDebt).show();
    }

    View.OnClickListener confirmDebt = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ConfirmDebitTransAsync asy = new ConfirmDebitTransAsync(getActivity(), confirmListener, moveLogInAct, lstDebtStaff, debtViettel);
            asy.execute();
        }
    };

    OnPostExecuteListener<Void> confirmListener = new OnPostExecuteListener<Void>() {
        @Override
        public void onPostExecute(Void result, String errorCode, String description) {
            CommonActivity.createAlertDialog(getActivity(), R.string.confirm_debt_success, R.string.app_name, successClick).show();
        }
    };

    View.OnClickListener successClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            getActivity().setResult(Activity.RESULT_OK);
            getActivity().onBackPressed();
        }
    };
    protected final View.OnClickListener moveLogInAct = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LoginDialog dialog = new LoginDialog(getActivity(), permission);
            dialog.show();
        }
    };
    Boolean isFormatted =false;
    private void showDialogStaffDebt(final ConfirmDebitTransDTO item) {
        try {
            isFormatted =false;
            final Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.layout_debt_staff_detail_dialog);
            dialog.setTitle(getString(R.string.debt_detail_lower));
            TextView tvDebtDetail = (TextView) dialog.findViewById(R.id.tvDebtDetail);
            TextView tvStaOfCycleAmount = (TextView) dialog.findViewById(R.id.tvStaOfCycleAmount);
            TextView tvBillCycleAmount = (TextView) dialog.findViewById(R.id.tvBillCycleAmount);
            TextView tvPayCycleAmount = (TextView) dialog.findViewById(R.id.tvPayCycleAmount);
            TextView tvEndCycleAmount = (TextView) dialog.findViewById(R.id.tvEndCycleAmount);
           final TextView tvDifferentDialog = (TextView) dialog.findViewById(R.id.tvDifferent);
            Button close = (Button) dialog.findViewById(R.id.btnCloseDialog);
            Button btnOK = (Button) dialog.findViewById(R.id.btnOK);

            final TextInputEditText edtReason = (TextInputEditText) dialog.findViewById(R.id.edtReason);
            final TextInputEditText edtConfirmPay = (TextInputEditText) dialog.findViewById(R.id.edtConfirmPay);

            tvDebtDetail.setText(item.getDebitTypeDetailName());
            tvStaOfCycleAmount.setText(StringUtils.formatMoney(item.getStaOfCycleAmount() + ""));
            tvBillCycleAmount.setText(StringUtils.formatMoney(item.getBillCycleAmount() + ""));
            tvPayCycleAmount.setText(StringUtils.formatMoney(item.getPayCycleAmount() + ""));
            tvEndCycleAmount.setText(StringUtils.formatMoney(item.getEndCycleAmount() + ""));
            tvDifferentDialog.setText(getString(R.string.different,StringUtils.formatMoney(item.getPayDifferent() + "")));
            edtReason.setText(item.getPayDifferentReason());
            edtConfirmPay.setText(StringUtils.formatMoney(item.getConfirmPay() + ""));
            edtConfirmPay.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s ) {
                    String money = s.toString().replaceAll("\\.", "");
                    money = money.replaceAll("\\+","");
                    if(CommonActivity.isNullOrEmpty(money)){
                        return;
                    }
                    if("-".equals(money)||"+".equals(money)){
                        return;
                    }
                    if (!isFormatted) {
                        isFormatted = true;

                        edtConfirmPay.setText(StringUtils.formatMoney(money));
                        edtConfirmPay.setSelection(edtConfirmPay.getText().toString().length());
                        long different = Long.parseLong(money) - item.getEndCycleAmount();
                        if (different < 0) {
                            different = 0 - different;
                        }
                        tvDifferentDialog.setText(getString(R.string.different,StringUtils.formatMoney(different +"")));
                        isFormatted = false;

                    }
                }
            });

            if (item.getStatus().compareTo(0l) == 0) {
                edtReason.setEnabled(true);
                edtConfirmPay.setEnabled(true);
                btnOK.setVisibility(View.VISIBLE);

            } else {
                edtReason.setEnabled(false);
                edtConfirmPay.setEnabled(false);
                btnOK.setVisibility(View.GONE);
                close.setText(getString(R.string.close));
            }

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            btnOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String money = edtConfirmPay.getText().toString().replaceAll("\\.", "");
                    String reason = edtReason.getText().toString().trim();
                    if (CommonActivity.isNullOrEmpty(money)) {
                        CommonActivity.createErrorDialog(getActivity(), getString(R.string.pay_confirm_not_null), "1").show();
                        edtConfirmPay.requestFocus();
                        return;
                    }

                    long different = Long.parseLong(money) - item.getEndCycleAmount();
                    if (different < 0) {
                        different = 0 - different;
                    }
                    if(different>0 && CommonActivity.isNullOrEmpty(reason)){
                        CommonActivity.createErrorDialog(getActivity(), getString(R.string.reason_different_not_null), "1").show();
                        edtReason.requestFocus();
                        return;
                    }
                    item.setConfirmPay(Long.parseLong(money));
                    item.setPayDifferent(different);
                    item.setPayDifferentReason(edtReason.getText().toString().trim());
                    item.setConfirmPay(Long.parseLong(money));
                    adapter.notifyDataSetChanged();

                    long confirmPay = 0l;
                    long needConfirm = 0l;
                    long diffrent = 0l;
                    for (int i = 0; i < lstDebtStaff.size() - 1; i++) {
                        needConfirm = lstDebtStaff.get(i).getEndCycleAmount() + needConfirm;
                        confirmPay = lstDebtStaff.get(i).getConfirmPay() + confirmPay;
                        diffrent = lstDebtStaff.get(i).getPayDifferent() + diffrent;

                    }
                    lstDebtStaff.get(lstDebtStaff.size() -1).setConfirmPay(confirmPay);
                    lstDebtStaff.get(lstDebtStaff.size() -1).setEndCycleAmount(needConfirm);
                    lstDebtStaff.get(lstDebtStaff.size() -1).setPayDifferent(different);
                    dialog.dismiss();
                }
            });



            dialog.show();
        } catch (Exception e) {
            Log.e(e.getMessage(), e.getMessage(), e);
        }
    }
    @OnClick(R.id.lnDebtViettel)
    void onClickViettelDebt(){
        showDialogViettelDebt(debtViettel);
    }
    private void showDialogViettelDebt(final ConfirmDebitTransDTO item) {
        try {
            isFormatted = false;
            final Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.layout_debt_viettel_detail_dialog);
            dialog.setTitle(getString(R.string.debt_detail_lower));
            TextView tvStaOfCycleBalance = (TextView) dialog.findViewById(R.id.tvStaOfCycleBalance);
            TextView tvAddCycleBalance = (TextView) dialog.findViewById(R.id.tvAddCycleBalance);
            TextView tvGetCycleBalance = (TextView) dialog.findViewById(R.id.tvGetCycleBalance);
            TextView tvEndCycleBalance = (TextView) dialog.findViewById(R.id.tvEndCycleBalance);
            final TextView tvDifferentDialog = (TextView) dialog.findViewById(R.id.tvDifferent);
            Button close = (Button) dialog.findViewById(R.id.btnCloseDialog);
            Button btnOK = (Button) dialog.findViewById(R.id.btnOK);
            final TextInputEditText edtReason = (TextInputEditText) dialog.findViewById(R.id.edtReason);
            final TextInputEditText edtConfirmPay = (TextInputEditText) dialog.findViewById(R.id.edtConfirmPay);
            tvStaOfCycleBalance.setText(StringUtils.formatMoney(item.getStaOfCycleBalance() + ""));
            tvAddCycleBalance.setText(StringUtils.formatMoney(item.getAddCycleBalance() + ""));
            tvGetCycleBalance.setText(StringUtils.formatMoney(item.getGetCycleBalance() + ""));
            tvEndCycleBalance.setText(StringUtils.formatMoney(item.getEndCycleBalance() + ""));
            tvDifferentDialog.setText(getString(R.string.different,StringUtils.formatMoney(item.getBalanceDifferent() + "")));
            edtReason.setText(item.getPayDifferentReason());
            edtConfirmPay.setText(StringUtils.formatMoney(item.getConfirmBalance() + ""));
            edtConfirmPay.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String money = s.toString().replaceAll("\\.", "");
                    money = money.replaceAll("\\+","");
                    if(CommonActivity.isNullOrEmpty(money)){
                        return;
                    }
                    if("-".equals(money) || "+".equals(money)){
                        return;
                    }
                    if (!isFormatted) {
                        isFormatted = true;


                        edtConfirmPay.setText(StringUtils.formatMoney(money));
                        edtConfirmPay.setSelection(edtConfirmPay.getText().toString().length());

                        long different = Long.parseLong(money) - item.getEndCycleBalance();
                        if (different < 0) {
                            different = 0 - different;
                        }
                        tvDifferentDialog.setText(getString(R.string.different,StringUtils.formatMoney(different +"")));
                        isFormatted = false;

                    }
                }
            });



            if (item.getStatus().compareTo(0l) == 0) {
                edtReason.setEnabled(true);
                edtConfirmPay.setEnabled(true);
                btnOK.setVisibility(View.VISIBLE);

            } else {
                edtReason.setEnabled(false);
                edtConfirmPay.setEnabled(false);
                btnOK.setVisibility(View.GONE);
                close.setText(getString(R.string.close));
            }

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            btnOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String money = edtConfirmPay.getText().toString().replaceAll("\\.", "");
                    String reason = edtReason.getText().toString().trim();
                    if (CommonActivity.isNullOrEmpty(money)) {
                        CommonActivity.createErrorDialog(getActivity(), getString(R.string.pay_confirm_not_null), "1").show();
                        edtConfirmPay.requestFocus();
                        return;
                    }

                    long different = Long.parseLong(money) - item.getEndCycleBalance();
                    if (different < 0) {
                        different = 0 - different;
                    }
                    if(different>0 && CommonActivity.isNullOrEmpty(reason)){
                        CommonActivity.createErrorDialog(getActivity(), getString(R.string.reason_different_not_null), "1").show();
                        edtReason.requestFocus();
                        return;
                    }
                    item.setConfirmBalance(Long.parseLong(money));
                    item.setBalanceDifferent(different);
                    item.setBalanceDifferentReason(edtReason.getText().toString().trim());
                    showViettelDebt();
                    dialog.dismiss();
                }
            });



            dialog.show();
        } catch (Exception e) {
            Log.e(e.getMessage(), e.getMessage(), e);
        }
    }
    private void showViettelDebt(){
        if (!CommonActivity.isNullOrEmpty(debtViettel)) {
            lnDebtViettel.setVisibility(View.VISIBLE);
            tvStartBalance.setText(getString(R.string.start_cycle_balance, StringUtils.formatMoney(debtViettel.getStaOfCycleBalance() + "")));
            tvEndBalance.setText(getString(R.string.end_cycle_balance, StringUtils.formatMoney(debtViettel.getEndCycleBalance() + "")));
            tvConfirm.setText(getString(R.string.confirm, StringUtils.formatMoney(debtViettel.getConfirmBalance() + "")));
            tvDifferent.setText(getString(R.string.different,StringUtils.formatMoney(debtViettel.getBalanceDifferent()+"")));
            if (debtViettel.getBalanceDifferent() > 0l) {

                tvReason.setVisibility(View.VISIBLE);
                tvReason.setText(getString(R.string.reason,debtViettel.getBalanceDifferentReason()));
            } else {

                tvReason.setVisibility(View.GONE);
            }

        } else {
            lnDebtViettel.setVisibility(View.GONE);
        }
    }
}
