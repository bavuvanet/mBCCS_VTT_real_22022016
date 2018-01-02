package com.viettel.bss.viettelpos.v4.charge.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.GetReasonFullPMAsyn;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.AccountDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductPackageFeeDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ReasonDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.charge.adapter.AdapterSubscriberListView;
import com.viettel.bss.viettelpos.v4.charge.adapter.FeeAdapter;
import com.viettel.bss.viettelpos.v4.charge.adapter.HintAdapter;
import com.viettel.bss.viettelpos.v4.charge.adapter.SubGoodDTOAdapter;
import com.viettel.bss.viettelpos.v4.charge.asynctask.AsynBlockSubForTerminate;
import com.viettel.bss.viettelpos.v4.charge.asynctask.AsynGetAccount;
import com.viettel.bss.viettelpos.v4.charge.asynctask.AsynGetFeeRevoke;
import com.viettel.bss.viettelpos.v4.charge.asynctask.AsynGetSubGoodDTO;
import com.viettel.bss.viettelpos.v4.charge.asynctask.AsynGetSubscribersByAccountId;
import com.viettel.bss.viettelpos.v4.charge.asynctask.AsynRevokeSubGoodsNotBlockSub;
import com.viettel.bss.viettelpos.v4.charge.object.SubGoodsDTO;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.OnPostSuccessExecute;
import com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;


/**
 * Created by thuandq on 6/19/2017.
 */

public class FragmentChargeLiquidationRecovery extends FragmentCommon implements
        View.OnClickListener {
    private ListView lv_subscriber;
    //    private AdapterExpandSubscriber expandableLVAdapter;
    private AdapterSubscriberListView adapterSubscriberListView;
    private ListView lvSubGoodsDTO;
    private SubGoodDTOAdapter subGoodsDTOAdapter;
    private ListView lvFee;
    private EditText editFunction;
    private Spinner spinReason;
    private Button btnExecute;
    private AccountDTO accountDTO;
    private LinearLayout table_contract;
    private LinearLayout showSubscriber;
    private TextView editTotalFee;
    private TextView txt_show, txtContractNo, txtDate, notifi_choose;
    private CheckBox checkAllSubsGood;
    private TextView txtIsdn;
    private HintAdapter hinAdapter;
    private SubscriberDTO subscriberDTO;
    private ArrayList<SubscriberDTO> subscriberDTOList;
    private List<SubGoodsDTO> subGoodsDTOs;
    private List<ReasonDTO> lstReasonDTO = new ArrayList<ReasonDTO>();
    private ReasonDTO reasonDTO;

    private Activity activity;
    private String function = "";
    private Boolean isShowGoods = false;
    private Boolean isShowFees = false;
    private List<ProductPackageFeeDTO> productPackageFeeDTOList;
    private LinearLayout tableGood;
    private TableLayout tableFee;
    LinearLayout layout;
    private HashMap<String, Integer> mapFunc = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        idLayout = R.layout.layout_liquidation_and_recovery;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void unit(View v) {
//        "Thanh lý thuê bao, không thu hồi hàng hóa", "Thu hồi hàng hóa", "Thanh lý thuê bao và thu hồi hàng hóa"
        mapFunc.put("Thanh lý thuê bao, không thu hồi hàng hóa", 1);
        mapFunc.put("Thu hồi hàng hóa", 2);
        mapFunc.put("Thanh lý thuê bao và thu hồi hàng hóa", 3);

        Bundle bundle = getArguments();
        subscriberDTO = (SubscriberDTO) bundle.getSerializable("subscriberDTO");
        function = bundle.getString("function");
        lv_subscriber = (ListView) v.findViewById(R.id.lv_subscriber);
        layout = (LinearLayout) v.findViewById(R.id.layout_liquidation_recovery);
        lvSubGoodsDTO = (ListView) v.findViewById(R.id.lvProduct);
        lvFee = (ListView) v.findViewById(R.id.lvFee);
        btnExecute = (Button) v.findViewById(R.id.btnRun);
        editTotalFee = (TextView) v.findViewById(R.id.txt_totalFee);
        txt_show = (TextView) v.findViewById(R.id.txt_show);
        txtDate = (TextView) v.findViewById(R.id.txtDate);
        notifi_choose = (TextView) v.findViewById(R.id.notifi_choose);
        table_contract = (LinearLayout) v.findViewById(R.id.table_contract);
        showSubscriber = (LinearLayout) v.findViewById(R.id.showSubscriber);
        txtContractNo = (TextView) v.findViewById(R.id.txtContractNo);
        txtIsdn = (TextView) v.findViewById(R.id.txtIsdn);
        tableFee = (TableLayout) v.findViewById(R.id.tableFee);
        tableGood = (LinearLayout) v.findViewById(R.id.tableGood);
        txtIsdn.setText(subscriberDTO.getAccount());
        editFunction = (EditText) v.findViewById(R.id.editSelectFunction);
        checkAllSubsGood = (CheckBox) v.findViewById(R.id.CbCheckAllSubGood);

        setRule();
        editFunction.setText(function);
        editFunction.setClickable(false);
        btnExecute.setVisibility(View.GONE);
        spinReason = (Spinner) v.findViewById(R.id.spinSelectReason);
        checkAllSubsGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subGoodsDTOAdapter.setcheckAll(checkAllSubsGood.isChecked());
            }
        });


        if (!CommonActivity.isNullOrEmpty(subscriberDTO) && !CommonActivity.isNullOrEmpty(subscriberDTO.getSubId())) {
            final AsynGetAccount asynGetAccount = new AsynGetAccount(getActivity(), new OnPostSuccessExecute<AccountDTO>() {
                @Override
                public void onPostSuccess(AccountDTO result) {
                    accountDTO = result;
                    if (!CommonActivity.isNullOrEmpty(accountDTO)) {
                        txtContractNo.setText(accountDTO.getAccountNo());
                        txtDate.setText(CommonActivity.isNullOrEmpty(accountDTO.getSignDate())
                                ? "" : formartDate(accountDTO.getSignDate().substring(0, 10)));
                        table_contract.setVisibility(View.VISIBLE);
                        btnExecute.setVisibility(View.VISIBLE);
                    } else {
                        table_contract.setVisibility(View.GONE);
                        btnExecute.setVisibility(View.VISIBLE);
                    }
                    if (!CommonActivity.isNullOrEmpty(accountDTO) && !CommonActivity.isNullOrEmpty(accountDTO.getAccountId())) {
                        if (!CommonActivity.isNullOrEmpty(subscriberDTO.getLstSubPromotionPrepaidDTO())) {
                            AsynGetSubscribersByAccountId asynGetSubscribersByAccountId = new AsynGetSubscribersByAccountId(getActivity(), new OnPostSuccessExecute<List<SubscriberDTO>>() {
                                @Override
                                public void onPostSuccess(List<SubscriberDTO> result) {
                                    //do in here
                                    if (CommonActivity.isNullOrEmpty(result)) {
                                        subscriberDTOList = new ArrayList<>();
                                        showSubscriber.setVisibility(View.GONE);
                                        notifi_choose.setVisibility(View.GONE);

                                    } else {
                                        subscriberDTOList = new ArrayList<>();
                                        for (SubscriberDTO dto : result) {
                                            if (!subscriberDTO.getAccount().equals(dto.getIsdn())
                                                    && !CommonActivity.isNullOrEmpty(dto.getIsdn())) {
                                                subscriberDTOList.add(dto);
                                            }
                                        }
                                        Comparator<SubscriberDTO> s = new Comparator<SubscriberDTO>() {
                                            @Override
                                            public int compare(SubscriberDTO o1, SubscriberDTO o2) {
                                                return o1.getIsdn().compareTo(o2.getIsdn());
                                            }
                                        };
                                        Collections.sort(subscriberDTOList, s);
                                        showSubscriber.setVisibility(View.VISIBLE);
                                        notifi_choose.setVisibility(View.VISIBLE);
                                    }

                                    adapterSubscriberListView = new AdapterSubscriberListView(getActivity(), subscriberDTOList);
                                    lv_subscriber.setAdapter(adapterSubscriberListView);
                                    setListViewHeightBasedOnChildren(lv_subscriber);
                                }
                            }, moveLogInAct);
                            asynGetSubscribersByAccountId.execute(subscriberDTO.getSubId());
                        } else {
                            notifi_choose.setVisibility(View.GONE);
                            showSubscriber.setVisibility(View.GONE);
                        }
                    }
                }
            }, moveLogInAct);
            asynGetAccount.execute(subscriberDTO.getSubId());
        }
        if (!CommonActivity.isNullOrEmpty(subscriberDTO)) {
            GetReasonFullPMAsyn getReasonFullPMAsyn = new GetReasonFullPMAsyn(getActivity(), new OnPostExecuteListener<List<ReasonDTO>>() {
                @Override
                public void onPostExecute(List<ReasonDTO> result, String errorCode, String description) {
                    if (!CommonActivity.isNullOrEmptyArray(result)) {
                        lstReasonDTO.addAll(result);
                    } else {
                        btnExecute.setVisibility(View.INVISIBLE);
                        lstReasonDTO = new ArrayList<ReasonDTO>();
                        CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notdatareason),
                                getActivity().getString(R.string.app_name)).show();
                    }
                    List<String> strReason = new ArrayList<>();
                    for (ReasonDTO reasonDTO : lstReasonDTO) {
                        strReason.add(reasonDTO.toString());
                    }
                    hinAdapter = new HintAdapter(getContext(), strReason, "--Chọn lý do--", new HintAdapter.OnClickWhenDropDown() {
                        @Override
                        public void onClick(int position) {
                            reasonDTO = lstReasonDTO.get(position);
                            initFee();
                        }
                    });
                    spinReason.setAdapter(hinAdapter);
                }
            }, moveLogInAct);
            getReasonFullPMAsyn.execute(subscriberDTO.getOfferId() + "", "03",
                    subscriberDTO.getTelecomServiceAlias(), subscriberDTO.getCustType(),
                    subscriberDTO.getSubType(), "", "");
        }
        btnExecute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View.OnClickListener onClickListenerExecute = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        execute();
                    }
                };
                if (validate(getListSubGoodsDTO())) {
                    CommonActivity.createDialog(
                            activity,
                            getResources().getString(
                                    R.string.execute_confirm),
                            getResources().getString(
                                    R.string.app_name),
                            getResources().getString(R.string.say_ko),
                            getResources().getString(R.string.say_co),
                            null, onClickListenerExecute).show();

                }
            }
        });
        notifi_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showSubscriber.getVisibility() == View.VISIBLE) {
                    showSubscriber.setVisibility(View.GONE);
                } else {
                    showSubscriber.setVisibility(View.VISIBLE);
                }

            }
        });
        initSubgood();
        initFee();
//        removeFragment(getActivity());
    }

    private void execute() {
        List<SubGoodsDTO> listSubGood = getListSubGoodsDTO();
        int idFunc = mapFunc.get(function) == null ? 0 : mapFunc.get(function);
        if (idFunc == 2) {
            AsynRevokeSubGoodsNotBlockSub asynRevokeSubGoodsNotBlockSub = new AsynRevokeSubGoodsNotBlockSub(getActivity(), subscriberDTO.getSubId() + "",
                    listSubGood, reasonDTO.getReasonId(), subscriberDTO, new OnPostSuccessExecute<String>() {
                @Override
                public void onPostSuccess(String result) {
                    if ("0".equals(result)) {
                        btnExecute.setVisibility(View.GONE);
                    }
                }
            }, moveLogInAct);
            asynRevokeSubGoodsNotBlockSub.execute();
        } else {
            List<String> listSub = new ArrayList<String>();
            for (SubscriberDTO dto : getListSubcriberIsCheck()) listSub.add(dto.getSubId());
            AsynBlockSubForTerminate asynBlockSubForTerminate = new AsynBlockSubForTerminate(getActivity(), subscriberDTO.getSubId()
                    , reasonDTO.getReasonId(), listSub, listSubGood, subscriberDTO, new OnPostSuccessExecute<String>() {
                @Override
                public void onPostSuccess(String result) {
                    if ("0".equals(result)) {
                        btnExecute.setVisibility(View.GONE);
                    }
                }
            }, moveLogInAct);
            asynBlockSubForTerminate.execute();
        }

    }


    @Override
    protected void setPermission() {

    }

    private void initFee() {
        if (!CommonActivity.isNullOrEmpty(reasonDTO)) {
            if (isShowFees && !CommonActivity.isNullOrEmpty(reasonDTO)) {
                tableFee.setVisibility(View.VISIBLE);
                AsynGetFeeRevoke asynGetFeeRevoke = new AsynGetFeeRevoke(getContext(), new OnPostSuccessExecute<List<ProductPackageFeeDTO>>() {
                    @Override
                    public void onPostSuccess(List<ProductPackageFeeDTO> result) {
                        Long totalFee = 0l;
                        if (result == null) {
                            result = new ArrayList<>();
                        }
                        productPackageFeeDTOList = result;
                        try {
                            lvFee.setAdapter(new FeeAdapter(activity, productPackageFeeDTOList));
                            for (ProductPackageFeeDTO feeDTO : productPackageFeeDTOList)
                                totalFee += Long.valueOf(feeDTO.getPrice());
                        } catch (Exception e) {
                            Log.e("error: set fee lv", e.getMessage());
                        }
                        editTotalFee.setText(totalFee + "");
                    }
                }, moveLogInAct);
                asynGetFeeRevoke.execute(subscriberDTO.getTelecomServiceId()
                        , reasonDTO.getReasonId(), subscriberDTO.getProductCode());
            } else {
                tableFee.setVisibility(View.GONE);
            }
        } else {
            tableFee.setVisibility(View.GONE);
        }
    }

    private void initSubgood() {
        if (isShowGoods) {
            List<String> subIds = new ArrayList<>();
            subIds.add(subscriberDTO.getSubId());
            AsynGetSubGoodDTO asynGetSubGoodDTO = new AsynGetSubGoodDTO(getActivity(), new OnPostSuccessExecute<List<SubGoodsDTO>>() {
                @Override
                public void onPostSuccess(List<SubGoodsDTO> result) {

                    if (CommonActivity.isNullOrEmpty(result)) {
                        result = new ArrayList<>();
                        tableGood.setVisibility(View.GONE);
                        txt_show.setVisibility(View.GONE);
                        if (mapFunc.get(function) == 2) {
                            btnExecute.setVisibility(View.GONE);
                        }
                    } else {
                        tableGood.setVisibility(View.VISIBLE);
                        txt_show.setVisibility(View.VISIBLE);
                    }
                    subGoodsDTOs = result;
                    subGoodsDTOAdapter = new SubGoodDTOAdapter(getActivity(), result, moveLogInAct);
                    lvSubGoodsDTO.setAdapter(subGoodsDTOAdapter);
                    setListViewHeightBasedOnChildren(lvSubGoodsDTO);
                }
            }, subIds, moveLogInAct);
            asynGetSubGoodDTO.execute();
        } else {
            tableGood.setVisibility(View.GONE);
            txt_show.setVisibility(View.GONE);
            subGoodsDTOAdapter = new SubGoodDTOAdapter(getActivity(), new ArrayList<SubGoodsDTO>(), moveLogInAct);
            lvSubGoodsDTO.setAdapter(subGoodsDTOAdapter);
        }
    }

    private void setRule() {
        int idFunc = mapFunc.get(function) == null ? 0 : mapFunc.get(function);
        if (idFunc == 0) {
            isShowGoods = false;
            isShowFees = false;
        } else if (idFunc == 1) {
            isShowGoods = false;
            isShowFees = true;
        } else if (idFunc == 2) {
            isShowFees = false;
            isShowGoods = true;
        } else {
            isShowGoods = true;
            isShowFees = true;
        }

    }


    private Boolean validate(List<SubGoodsDTO> listSubGood) {

        if (CommonActivity.isNullOrEmpty(reasonDTO)) {
            Dialog dialog = CommonActivity.createAlertDialog(activity, activity.getString(R.string.choose_reason_not),
                    activity.getString(R.string.app_name));
            dialog.show();
            return false;
        }
        int idFunc = mapFunc.get(function) == null ? 0 : mapFunc.get(function);

        if (idFunc == 2) {
            if (CommonActivity.isNullOrEmpty(listSubGood)) {
                Dialog dialog = CommonActivity.createAlertDialog(activity, activity.getString(R.string.no_subgood_to_stoke),
                        activity.getString(R.string.app_name));
                dialog.show();
                return false;
            }
            Boolean check = true;
            for (SubGoodsDTO subGoods : listSubGood)
                check = check && !CommonActivity.isNullOrEmpty(subGoods.getSerial());
            if (!check) {
                Dialog dialog = CommonActivity.createAlertDialog(activity, activity.getString(R.string.showCreateSerial),
                        activity.getString(R.string.app_name));
                dialog.show();
                return false;
            }
        }
        return true;
    }

    private List<SubGoodsDTO> getListSubGoodsDTO() {
        if (CommonActivity.isNullOrEmpty(subGoodsDTOAdapter))
            return new ArrayList<>();
        else return subGoodsDTOAdapter.getSubGoodisCheck();
    }

    private List<SubscriberDTO> getListSubcriberIsCheck() {
        List<SubscriberDTO> subscribers = new ArrayList<>();
        if (CommonActivity.isNullOrEmpty(adapterSubscriberListView))
            return subscribers;
        else {
            subscribers = adapterSubscriberListView.getListSubCheck();
        }
        return subscribers;
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleActionBar(getActivity().getString(R.string.liquidation_and_recovery));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();

    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, LinearLayout.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private String formartDate(String fromDate){
        SimpleDateFormat dateFormatFrom = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormatTo = new SimpleDateFormat("dd/MM/yyyy");
        String result="";
        try {
            result=dateFormatTo.format(dateFormatFrom.parse(fromDate));
        } catch (ParseException e) {
            Log.e("Error format date",e.getMessage());
        }
        return result;
    }

}

