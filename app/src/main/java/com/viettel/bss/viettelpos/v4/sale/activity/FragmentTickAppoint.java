package com.viettel.bss.viettelpos.v4.sale.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.smartphonev2.commons.OnPostExecute;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncdoUpdateDraw;
import com.viettel.bss.viettelpos.v4.sale.object.SaleOutput;
import com.viettel.bss.viettelpos.v4.sale.object.SubDepositDTO;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.object.FormBean;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncAddQuotaPSTN;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncLstTimeMoney;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskGetListReason;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTickAppointBlock;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncViewHisTick;
import com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v3.connecttionService.model.OptionSetValueDTO;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

public class FragmentTickAppoint extends FragmentCommon {

    private final String GIA_HAN_TAM_NGUNG = "GIA_HAN_TAM_NGUNG";
    private final String GIA_HAN_HAN_MUC = "GIA_HAN_HAN_MUC";
    private SubscriberDTO sub;
    private Spinner spnAction, spnDay, spnMoney, spnReason;
    private TextView tvAccount;
    private Button btnTickPoint, btnViewTickHis;
    private View lnTickPoint;
    private String action;
    private List<OptionSetValueDTO> lstTimeMoney = new ArrayList<OptionSetValueDTO>();
    private List<Spin> lstReason = new ArrayList<Spin>();
    Dialog dialog;

    private LinearLayout lnRuttien;
    private Spinner spnDrawtick;
    private List<Spin> arrDrawtick = new ArrayList<>();
    private SubDepositDTO itemClick;
    ;
    private String payMethodNew = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        idLayout = R.layout.layout_tick_app_point;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act = getActivity();
        mBundle = getArguments();
        sub = (SubscriberDTO) mBundle.getSerializable("subscriberDTO");
    }

    @Override
    public void onResume() {
        setTitleActionBar(getString(R.string.tick_appoint));
        super.onResume();
    }

    @Override
    public void unit(View v) {
        lnRuttien = (LinearLayout) v.findViewById(R.id.lnRuttien);
        lnRuttien.setVisibility(View.GONE);
        spnDrawtick = (Spinner) v.findViewById(R.id.spnDrawtick);
        initTakeoutDeposit();
        spnAction = (Spinner) v.findViewById(R.id.spnAction);
        spnDay = (Spinner) v.findViewById(R.id.spnDay);
        spnMoney = (Spinner) v.findViewById(R.id.spnMoney);
        spnReason = (Spinner) v.findViewById(R.id.spnReason);
        tvAccount = (TextView) v.findViewById(R.id.tvAccount);
        btnTickPoint = (Button) v.findViewById(R.id.btnTickPoint);
        btnViewTickHis = (Button) v.findViewById(R.id.btnViewTickHis);
        lnTickPoint = v.findViewById(R.id.lnTickPoint);
        tvAccount.setText(sub.getAccount());
        initAction();
        btnTickPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (GIA_HAN_TAM_NGUNG.equals(action)) {
                    final OptionSetValueDTO day = (OptionSetValueDTO) spnDay
                            .getSelectedItem();
                    int position = spnDay.getSelectedItemPosition();
                    if (day == null || position == 0) {
                        CommonActivity.createAlertDialog(act,
                                R.string.chua_chon_so_ngay, R.string.app_name)
                                .show();
                        return;
                    }
                    final String money = (String) spnMoney.getSelectedItem();
                    position = spnMoney.getSelectedItemPosition();
                    if (CommonActivity.isNullOrEmpty(money) || position == 0) {
                        CommonActivity.createAlertDialog(act,
                                R.string.chua_chon_so_tien, R.string.app_name)
                                .show();
                        return;
                    }

                    final Spin reason = (Spin) spnReason.getSelectedItem();
                    if (reason == null
                            || CommonActivity.isNullOrEmpty(reason.getId())) {
                        CommonActivity.createAlertDialog(act,
                                R.string.choose_reason_not, R.string.app_name)
                                .show();
                        return;
                    }

                    OnClickListener onClick = new OnClickListener() {

                        @Override
                        public void onClick(View arg0) {

                            Spin spinItemTick = (Spin) spnDrawtick.getSelectedItem();
                            if (spinItemTick != null && !CommonActivity.isNullOrEmpty(spinItemTick.getId())) {
                                AsyncTickAppointBlock asy = new AsyncTickAppointBlock(
                                        onPostTick, act, sub, money, day.getName(),
                                        reason.getId(), spinItemTick.getId());
                                asy.execute();
                            }

                        }
                    };
                    CommonActivity.createDialog(act,
                            getString(R.string.tick_confirm, sub.getAccount()),
                            getString(R.string.app_name),
                            getString(R.string.ok), getString(R.string.cancel),
                            onClick, null).show();

                } else if (GIA_HAN_HAN_MUC.equals(action)) {
                    OnClickListener onClick = new OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            AsyncAddQuotaPSTN asy = new AsyncAddQuotaPSTN(
                                    onPostAddQuota, act, sub);
                            asy.execute();
                        }
                    };
                    CommonActivity.createDialog(
                            act,
                            getString(R.string.add_quota_confirm,
                                    sub.getAccount()),
                            getString(R.string.app_name),
                            getString(R.string.ok), getString(R.string.cancel),
                            onClick, null).show();
                }
            }
        });
        btnViewTickHis.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (dialog != null) {
                    dialog.show();
                } else {
                    AsyncViewHisTick asy = new AsyncViewHisTick(
                            onPostViewHisTick, act, sub.getSubId());
                    asy.execute();
                }

            }
        });
        spnDay.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
                // if (position == 0) {
                // spnMoney.setVisibility(View.GONE);
                // return;
                // }
                // spnMoney.setVisibility(View.VISIBLE);
                OptionSetValueDTO item = lstTimeMoney.get(position);
                List<String> money = new ArrayList<String>();
                money.add(getString(R.string.txt_select));
                if (!CommonActivity.isNullOrEmpty(item.getValue())) {
                    try {
                        String[] arrMoney = item.getValue().split(";");
                        money.addAll(Arrays.asList(arrMoney));
                    } catch (Exception e) {
                        Log.e("exception", "Exception", e);
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        getActivity(), R.layout.spinner_item, money);
                spnMoney.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    @Override
    public void setPermission() {

    }

    private void initAction() {
        final List<FormBean> lstStatus = new ArrayList<FormBean>();

        FormBean all = new FormBean();
        all.setId("");
        all.setName(getString(R.string.chon_loai_gia_han));
        lstStatus.add(all);
        FormBean tickPoint = new FormBean();
        tickPoint.setId(GIA_HAN_TAM_NGUNG);
        tickPoint.setName(getString(R.string.gia_han_tam_ngung));

        FormBean addQuota = new FormBean();
        addQuota.setId(GIA_HAN_HAN_MUC);
        addQuota.setName(getString(R.string.gia_han_han_muc));

        SharedPreferences preferences = getActivity().getSharedPreferences(
                Define.PRE_NAME, Activity.MODE_PRIVATE);

        String name = preferences.getString(Define.KEY_MENU_NAME, "");
        if (name.contains(";m.tick.appoint;")) {
            lstStatus.add(tickPoint);
        }
        if (name.contains(";m.add.quota;")) {
            lstStatus.add(addQuota);
        }

        ArrayAdapter<FormBean> statusAdapter = new ArrayAdapter<FormBean>(
                getActivity(), R.layout.spinner_item, lstStatus);
        spnAction.setAdapter(statusAdapter);
        spnAction.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                FormBean item = (FormBean) spnAction.getSelectedItem();
                btnTickPoint.setVisibility(View.GONE);
                lnTickPoint.setVisibility(View.GONE);
                btnViewTickHis.setVisibility(View.GONE);

                action = item.getId();
                if (GIA_HAN_TAM_NGUNG.equals(action)) {
                    lnRuttien.setVisibility(View.VISIBLE);
                    lnTickPoint.setVisibility(View.VISIBLE);
                    btnTickPoint.setVisibility(View.VISIBLE);
                    btnViewTickHis.setVisibility(View.VISIBLE);
                    if (CommonActivity.isNullOrEmpty(lstTimeMoney)) {
                        AsyncLstTimeMoney asy = new AsyncLstTimeMoney(
                                onPostLstTimeMoney, act);
                        asy.execute();
                    }
                } else if (GIA_HAN_HAN_MUC.equals(action)) {

                    if (!"3".equals(sub.getTelecomServiceId())
                            && !"23".equals(sub.getTelecomServiceId())) {
                        // chi gia han han muc doi voi PSTN va IPPhone
                        CommonActivity.createAlertDialog(act,
                                R.string.ipPhone_pstn_only, R.string.app_name)
                                .show();
                        return;
                    }
                    lnRuttien.setVisibility(View.GONE);
                    lnTickPoint.setVisibility(View.GONE);
                    btnViewTickHis.setVisibility(View.GONE);
                    lnRuttien.setVisibility(View.GONE);
                    btnTickPoint.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    OnPostExecute<SaleOutput> onPostLstTimeMoney = new OnPostExecute<SaleOutput>() {

        @Override
        public void onPostExecute(SaleOutput result) {
            if (!CommonActivity.isNullOrEmpty(result.getLstOptionSet())) {
                lstTimeMoney = result.getLstOptionSet();
                if (lstTimeMoney == null) {
                    lstTimeMoney = new ArrayList<OptionSetValueDTO>();
                }

                OptionSetValueDTO first = new OptionSetValueDTO();
                first.setName(getString(R.string.txt_select));
                lstTimeMoney.add(0, first);
                ArrayAdapter<OptionSetValueDTO> adapter = new ArrayAdapter<OptionSetValueDTO>(
                        getActivity(), R.layout.spinner_item, lstTimeMoney);
                spnDay.setAdapter(adapter);
                if (CommonActivity.isNullOrEmpty(lstReason)) {
                    AsyncTaskGetListReason asy = new AsyncTaskGetListReason(
                            act, onPostGetReason, moveLogInAct, "566");
                    asy.execute(sub);
                }
            }

        }
    };
    OnPostExecuteListener<ArrayList<Spin>> onPostGetReason = new OnPostExecuteListener<ArrayList<Spin>>() {

        @Override
        public void onPostExecute(ArrayList<Spin> result, String errorCode,
                                  String description) {
            lstReason = result;
            if (CommonActivity.isNullOrEmpty(lstReason)) {
                lstReason = new ArrayList<Spin>();
            }
            Spin first = new Spin();
            first.setValue(getString(R.string.txt_select_reason));
            lstReason.add(0, first);
            ArrayAdapter<Spin> adapter = new ArrayAdapter<Spin>(getActivity(),
                    R.layout.spinner_item, lstReason);
            spnReason.setAdapter(adapter);
        }
    };
    OnPostExecute<SaleOutput> onPostTick = new OnPostExecute<SaleOutput>() {

        @Override
        public void onPostExecute(SaleOutput result) {
            CommonActivity.createAlertDialog(
                    act,
                    getString(R.string.ticking_appoint_success,
                            sub.getAccount()), getString(R.string.app_name))
                    .show();
            btnTickPoint.setVisibility(View.GONE);
        }

    };
    OnPostExecute<SaleOutput> onPostAddQuota = new OnPostExecute<SaleOutput>() {

        @Override
        public void onPostExecute(SaleOutput result) {
            CommonActivity.createAlertDialog(act, result.getDescription(),
                    getString(R.string.app_name)).show();
            if (Constant.SUCCESS_CODE.equals(result.getErrorCode())) {
                btnTickPoint.setVisibility(View.GONE);
            }
        }

    };

    OnPostExecute<SaleOutput> onPostViewHisTick = new OnPostExecute<SaleOutput>() {

        @Override
        public void onPostExecute(SaleOutput result) {
            final List<SubDepositDTO> lstData = result.getLstSubDeposit();
            if (CommonActivity.isNullOrEmpty(lstData)) {
                CommonActivity.createAlertDialog(act, R.string.no_data,
                        R.string.app_name).show();
                return;
            }
            itemClick = new SubDepositDTO();
            dialog = new Dialog(act,
                    android.R.style.Theme_Light_NoTitleBar_Fullscreen);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_single_list_view);
            TextView tvConfirm = (TextView) dialog.findViewById(R.id.tvConfirm);
            tvConfirm.setVisibility(View.VISIBLE);
            Button btnClose = (Button) dialog.findViewById(R.id.btnClose);
            TextView tvTitle = (TextView) dialog
                    .findViewById(R.id.tvDialogTitle);
            tvTitle.setText(getString(R.string.his_tick, sub.getAccount()));
            ListView lvItem = (ListView) dialog.findViewById(R.id.lvTransHis);

            for (SubDepositDTO item : lstData) {
                item.setContext(act);
            }
            ArrayAdapter<SubDepositDTO> adapterViewHis = new ArrayAdapter<SubDepositDTO>(
                    getActivity(), R.layout.spinner_item, lstData);
            lvItem.setAdapter(adapterViewHis);
            final OnClickListener onclickConfirm = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    AsyncdoUpdateDraw asyncdoUpdateDraw = new AsyncdoUpdateDraw(onPostDoUpdateDraw, getActivity());
                    asyncdoUpdateDraw.execute(sub.getSubId() + "", payMethodNew, itemClick.getSubDepositId() + "");
                }
            };
            lvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    itemClick = lstData.get(position);
                    if(itemClick.isChangeDrawMethod()){
                        String msg = "";
                        if ("1".equals(itemClick.getPayMethod())) {
                            payMethodNew = "2";
                            msg = getActivity().getString(R.string.updatedrawtotakeout);
                        } else {
                            payMethodNew = "1";
                            msg = getActivity().getString(R.string.updatedrawtotrancharge);
                        }
                        CommonActivity.createDialog(getActivity(), msg, getActivity().getString(R.string.app_name), getActivity().getString(R.string.ok), getActivity().getString(R.string.cancel), onclickConfirm, null).show();
                    }else{
                        CommonActivity.createAlertDialog(getActivity(),getActivity().getString(R.string.notpermissiondeposit),getActivity().getString(R.string.app_name)).show();
                    }

                }
            });


            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }

    };

    private void initTakeoutDeposit() {
        if (!CommonActivity.isNullOrEmptyArray(arrDrawtick)) {
            arrDrawtick = new ArrayList<>();
        }
        arrDrawtick.add(new Spin("1", getActivity().getString(R.string.ruttienmat)));
        arrDrawtick.add(new Spin("2", getActivity().getString(R.string.chuyentiencuoc)));
        Utils.setDataSpinner(getActivity(), arrDrawtick, spnDrawtick);
    }

    OnClickListener onclickRetryViewHis = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (dialog != null) {
                dialog.cancel();
            }
            AsyncViewHisTick asy = new AsyncViewHisTick(
                    onPostViewHisTick, act, sub.getSubId());
            asy.execute();
        }
    };

    OnPostExecute<SaleOutput> onPostDoUpdateDraw = new OnPostExecute<SaleOutput>() {
        @Override
        public void onPostExecute(SaleOutput result) {
            if ("0".equals(result.getErrorCode())) {
                String des = getActivity().getString(R.string.doupdatedrawsuccess);
                if (!CommonActivity.isNullOrEmpty(result.getDescription())) {
                    des = result.getDescription();
                }
                CommonActivity.createAlertDialog(getActivity(), des, getActivity().getString(R.string.app_name), onclickRetryViewHis).show();
            }

        }
    };
}
