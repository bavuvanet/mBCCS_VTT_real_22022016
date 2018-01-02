package com.viettel.bss.viettelpos.v4.omichanel.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.SubPromotionPrepaidDTO;
import com.viettel.bss.smartphonev2.commons.OnPostExecute;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;

import android.view.View.OnClickListener;

import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.SearchActivity;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PaymentPrePaidPromotionBeans;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.omichanel.adapter.ReponseChangePrepaidPromotionAdapter;
import com.viettel.bss.viettelpos.v4.omichanel.adapter.SubPreChangeAdapter;
import com.viettel.bss.viettelpos.v4.omichanel.asynctask.ChangePrepaidPromotionAsyncTask;
import com.viettel.bss.viettelpos.v4.omichanel.asynctask.GetAllListPaymentPrePaidAsyn;
import com.viettel.bss.viettelpos.v4.omichanel.asynctask.GetOrderPrepaidInfoAsyncTask;
import com.viettel.bss.viettelpos.v4.omichanel.dao.AccountPrepaidInfo;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ChangePrepaidDTO;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ConnectionOrder;
import com.viettel.bss.viettelpos.v4.omichanel.dao.OmniAccountPrepaidInfo;
import com.viettel.bss.viettelpos.v4.omichanel.dao.OmniOrder;
import com.viettel.bss.viettelpos.v4.omichanel.dao.Order;
import com.viettel.bss.viettelpos.v4.utils.Log;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thuandq on 9/26/2017.
 */

public class ChangePreChargeFragment extends FragmentCommon implements OnClickListener {
    @BindView(R.id.tvCustName)
    TextView tvCustName;
    @BindView(R.id.tvCustId)
    TextView tvCustId;
    @BindView(R.id.tvCustBirthday)
    TextView tvCustBirthday;
    @BindView(R.id.tvIssuePlace)
    TextView tvIssuePlace;
    @BindView(R.id.tvAccount)
    TextView tvAccount;
    @BindView(R.id.rgStartDate)
    RadioGroup rgStartDate;
    @BindView(R.id.rbThisDay)
    RadioButton rbThisDay;
    @BindView(R.id.rbOtherDay)
    RadioButton rbOtherDay;
    @BindView(R.id.edtEffectDate)
    EditText edtEffectDate;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.btnAction)
    Button btnAction;
    Activity activity;
    OmniOrder omniOrder;
    Date effectDate;
    Date today = Calendar.getInstance().getTime();
    Boolean isToday;
    ConnectionOrder connectionOrder;
    List<PaymentPrePaidPromotionBeans> paymentPrePaidPromotionBeanses;
    List<String> listPrepaid;
    SubPreChangeAdapter subPreChangeAdapter;
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    Long feeTranfer = 0l;
    List<OmniAccountPrepaidInfo> listAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        idLayout = R.layout.layout_change_pre_charge;
        ButterKnife.bind(getActivity());
        activity = getActivity();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleActionBar(activity.getString(R.string.change_pre_charge_tittle));
    }

    @Override
    protected void unit(View v) {
        Log.out("hardware", "hardware acc = " + v.isHardwareAccelerated());
        Bundle bundle = getArguments();
        connectionOrder = (ConnectionOrder) bundle.getSerializable("connectionOrder");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);
        edtEffectDate.setOnClickListener(this);
        edtEffectDate.setVisibility(View.GONE);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        effectDate = calendar.getTime();
        edtEffectDate.setText(DateTimeUtils.convertDateTimeToString(effectDate));
        if (!CommonActivity.isNullOrEmpty(connectionOrder.getFeeRecords())
                && !CommonActivity.isNullOrEmpty(connectionOrder.getFeeByCode(Order.TRANSFER_FEE))) {
            feeTranfer = connectionOrder.getFeeByCode(Order.TRANSFER_FEE);
        }
        if (!CommonActivity.isNullOrEmptyArray(connectionOrder, connectionOrder.getProcessInstanceId())) {
            getOrderPrepaidInfo();
        } else {
            CommonActivity.showConfirmValidate(getActivity(), R.string.order_validate_cdt_action);
            btnAction.setVisibility(View.GONE);
        }
        btnAction.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonActivity.isNullOrEmpty(isToday)) {
                    CommonActivity.showConfirmValidate(getActivity(), R.string.validate_select_effect_date_promotion);
                    return;
                } else if (isToday.equals(false)) {
                    if (DateTimeUtils.daysBetween(effectDate, today) >= 0) {
                        CommonActivity.showConfirmValidate(getActivity(), R.string.dateEffectvlid);
                        return;
                    } else if (!df.format(effectDate).startsWith("01")) {
                        CommonActivity.showConfirmValidate(getActivity(), R.string.validate_select_effect_date_start_month_promotion);
                        return;
                    }
                }
                CommonActivity.createDialog(getActivity(),
                        getActivity().getResources().getString(R.string.confirm_change_pre_charge),
                        getActivity().getResources().getString(R.string.app_name),
                        getActivity().getResources().getString(R.string.cancel),
                        getActivity().getResources().getString(R.string.buttonOk),
                        null, onClickListener).show();
            }
        });
        rgStartDate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rbThisDay:
                        isToday = true;
                        edtEffectDate.setVisibility(View.GONE);
                        break;
                    case R.id.rbOtherDay:
                        isToday = false;
                        edtEffectDate.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        Calendar cal = Calendar.getInstance();
        switch (v.getId()) {
            case R.id.edtEffectDate:
                if (CommonActivity.isNullOrEmpty(effectDate)) {
                    String dt = edtEffectDate.getText().toString();
                    try {
                        effectDate = df.parse(dt);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                cal.setTime(effectDate);
                DatePickerDialog fromDateDialog = new FixedHoloDatePickerDialog(
                        getActivity(), AlertDialog.THEME_HOLO_LIGHT, effectDatePickerListener, cal.get(Calendar.YEAR)
                        , cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                fromDateDialog.show();
                break;
        }
    }

    private final DatePickerDialog.OnDateSetListener effectDatePickerListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            Calendar cal = Calendar.getInstance();
            cal.set(selectedYear, selectedMonth, selectedDay);
            effectDate = cal.getTime();
            edtEffectDate.setText(DateTimeUtils.convertDateTimeToString(effectDate));
        }
    };
    OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            changePrepaidPromotion();
        }
    };

    private void getOrderPrepaidInfo() {
        GetOrderPrepaidInfoAsyncTask getOrderPrepaidInfoAsyncTask = new GetOrderPrepaidInfoAsyncTask(getActivity(), new OnPostExecuteListener<OmniOrder>() {
            @Override
            public void onPostExecute(OmniOrder result, String errorCode, String description) {
                if ("0".equals(errorCode) && !CommonActivity.isNullOrEmptyArray(result)
                        && !CommonActivity.isNullOrEmpty(result.getAccountPrepaidRecords())) {
                    omniOrder = result;
                    Boolean check = false;
                    listAdapter = new ArrayList<>();
                    for (OmniAccountPrepaidInfo accountPrepaidInfo : result.getAccountPrepaidRecords()) {
                        if (!CommonActivity.isNullOrEmpty(accountPrepaidInfo.getProductInfo()) &&
                                !CommonActivity.isNullOrEmptyArray(accountPrepaidInfo.getProductInfo().getPrepaidCode(),
                                        accountPrepaidInfo.getProductInfo().getOldPrepaidCode())) {
                            listAdapter.add(accountPrepaidInfo);
                            check = true;
                        }
                    }

                    if (!CommonActivity.isNullOrEmpty(omniOrder.getCustomer())) {
                        tvCustName.setText(omniOrder.getCustomer().getName());
                        tvCustId.setText(omniOrder.getCustomer().getIdNo());
                        tvCustBirthday.setText(omniOrder.getCustomer().getBirthDate());
                    }
                    if (!CommonActivity.isNullOrEmpty(omniOrder.getCustomer()) &&
                            !CommonActivity.isNullOrEmpty(omniOrder.getCustomer().getAddress())) {
                        tvIssuePlace.setText(omniOrder.getCustomer().getAddress().getAddress());
                    }

                    tvAccount.setText(omniOrder.getContractNo());
                    if (check) {
                        if (!CommonActivity.isNullOrEmpty(omniOrder.getAccountPrepaidRecords())) {
                            subPreChangeAdapter = new SubPreChangeAdapter(getActivity(), feeTranfer, moveLogInAct, listAdapter, new OnPostExecute<OmniAccountPrepaidInfo>() {
                                @Override
                                public void onPostExecute(OmniAccountPrepaidInfo click) {
                                    getListPaymentPrepaidPro(click);
                                }
                            });
                            recyclerView.setAdapter(subPreChangeAdapter);
                        }
                    } else {
                        CommonActivity.showConfirmValidate(getActivity(), R.string.order_validate_list_prepaid);
                        btnAction.setVisibility(View.GONE);
                    }
                } else {
                    if ("0".equals(errorCode) && CommonActivity.isNullOrEmpty(description)) {
                        CommonActivity.showConfirmValidate(getActivity(), R.string.order_validate_cdt_action);
                    } else {
                        CommonActivity.showConfirmValidate(getActivity(), description);
                    }
                    btnAction.setVisibility(View.GONE);
                }
            }
        }, moveLogInAct);
        getOrderPrepaidInfoAsyncTask.execute(connectionOrder.getProcessInstanceId(), "1");
    }

    private void getListPaymentPrepaidPro(final OmniAccountPrepaidInfo omniAccountPrepaidInfo) {
        // lay ve danh sach cuoc dong truoc

        if (CommonActivity.isNullOrEmpty(omniOrder.getAddress()) ||
                CommonActivity.isNullOrEmpty(omniOrder.getAddress().getProvince())) {
            CommonActivity.showConfirmValidate(getActivity(), R.string.validate_not_province);
            return;
        }
        if (CommonActivity.isNullOrEmpty(omniAccountPrepaidInfo.getProductInfo()) ||
                CommonActivity.isNullOrEmpty(omniAccountPrepaidInfo.getProductInfo().getProductCode())) {
            CommonActivity.showConfirmValidate(getActivity(), R.string.notgoicuoc);
            return;
        }
        if (CommonActivity.isNullOrEmpty(omniAccountPrepaidInfo.getProductInfo().getPromotionCode())) {
            CommonActivity.showConfirmValidate(getActivity(), R.string.notkhuyenmai);
            return;
        }
        String province = omniOrder.getAddress().getProvince();
        String productCode = omniAccountPrepaidInfo.getProductInfo().getProductCode();
        String promotionCode = omniAccountPrepaidInfo.getProductInfo().getPromotionCode();
        GetAllListPaymentPrePaidAsyn paymentPrePaidAsyn = new GetAllListPaymentPrePaidAsyn(activity,
                new OnPostExecuteListener<List<PaymentPrePaidPromotionBeans>>() {
                    @Override
                    public void onPostExecute(List<PaymentPrePaidPromotionBeans> result, String errorCode, String description) {
                        //
                        paymentPrePaidPromotionBeanses = new ArrayList<>();
                        if ("0".equals(errorCode) && !CommonActivity.isNullOrEmpty(result)) {
//                            if (!CommonActivity.isNullOrEmpty(omniAccountPrepaidInfo.getLstPaymentPrePaidDetailOld())) {
//                                SubPromotionPrepaidDTO prepaidDTO = omniAccountPrepaidInfo.getLstPaymentPrePaidDetailOld().get(0);
//                                String prepaidCode = CommonActivity.isNullOrEmpty(prepaidDTO) ? "" : prepaidDTO.getPrepaidCode();
//                                for (PaymentPrePaidPromotionBeans promotionBeans : result) {
//                                    if (!prepaidCode.equals(promotionBeans.getPrePaidCode())) {
//                                        paymentPrePaidPromotionBeanses.add(promotionBeans);
//                                    }
//                                }
//                            } else {
                            paymentPrePaidPromotionBeanses = result;
//                            }

                            listPrepaid = new ArrayList<>();
                            for (PaymentPrePaidPromotionBeans promotionBeans : paymentPrePaidPromotionBeanses) {
                                String content = promotionBeans.getName();
                                content += "\n" + promotionBeans.getPrePaidCode();
                                content += "\n" + StringUtils.formatMoney(promotionBeans.getTotalMoney() + "") + " VNĐ";
                                listPrepaid.add(content);
                            }
                            if (!CommonActivity.isNullOrEmpty(listPrepaid)) {
                                Intent intent = new Intent(getActivity(),
                                        SearchActivity.class);
                                intent.putExtra("list", (Serializable) listPrepaid);
                                intent.putExtra("hint", (Serializable) "Nhập tên hoặc mã để tìm kiếm");
                                intent.putExtra("tittle", (Serializable) "Chọn cước đóng trước mới");
                                startActivityForResult(intent, 9999);
                            } else {
                                CommonActivity.showConfirmValidate(getActivity(), R.string.order_validate_cdt_not_data);
                            }
                        } else {
                            if (CommonActivity.isNullOrEmpty(description)) {
                                CommonActivity.showConfirmValidate(getActivity(), R.string.order_validate_cdt_not_data);
                            } else {
                                CommonActivity.showConfirmValidate(getActivity(), description);
                            }
                        }
                    }
                }, moveLogInAct);
        paymentPrePaidAsyn.execute(province, productCode, promotionCode);
    }

    private void changePrepaidPromotion() {
        String efectDate = "";
        if (!isToday) efectDate = DateTimeUtils.convertDateSendOverSoap(effectDate);
        ChangePrepaidPromotionAsyncTask changePrepaidPromotionAsyncTask = new ChangePrepaidPromotionAsyncTask(getActivity()
                , subPreChangeAdapter.getAccoutPrepaidRecords(), omniOrder,
                efectDate, connectionOrder.getTaskId()
                , new OnPostExecuteListener<List<ChangePrepaidDTO>>() {
            @Override
            public void onPostExecute(List<ChangePrepaidDTO> result, String errorCode, String description) {
                if ("0".equals(errorCode)) {
                    if (!CommonActivity.isNullOrEmpty(result)) {
                        Boolean check = false;
                        for (ChangePrepaidDTO changePrepaidDTO : result) {
                            if ("0".equals(changePrepaidDTO.getCode())) {
                                check = true;
                                break;
                            }
                        }
                        if (check) {
                            btnAction.setVisibility(View.GONE);
                        }
                        final BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
                        dialog.setContentView(R.layout.layout_reponse_change_promotion);
                        dialog.setCancelable(false);
                        dialog.setCanceledOnTouchOutside(true);

                        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.lvReponse);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        ReponseChangePrepaidPromotionAdapter promotionAdapter = new ReponseChangePrepaidPromotionAdapter(result, getActivity());
                        recyclerView.setAdapter(promotionAdapter);
                        dialog.show();
                    } else {
                        CommonActivity.showConfirmValidate(getActivity(), R.string.no_return_from_system);
                    }
                }
            }
        }, moveLogInAct);
        changePrepaidPromotionAsyncTask.execute();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 9999:
                    Integer position = data.getExtras().getInt("position");
                    subPreChangeAdapter.setNewPrepaid(paymentPrePaidPromotionBeanses.get(position));
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void setPermission() {

    }
}
