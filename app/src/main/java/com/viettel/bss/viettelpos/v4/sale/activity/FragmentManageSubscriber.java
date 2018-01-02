package com.viettel.bss.viettelpos.v4.sale.activity;


import com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter.GetFeeBCCSAdapter;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductPackageFeeDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ReasonDTO;
import com.viettel.bss.viettelpos.v4.charge.fragment.FragmentchoseFunction;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.auto.AutoCompleteUtil;
import com.viettel.bss.viettelpos.v4.customview.InstantAutoComplete;
import com.viettel.bss.viettelpos.v4.object.CSKHBO;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentManagerChangeTech;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.InfoSub;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ReceiveRequestBean;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter.SubscriberInfoChildAdapter;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.GetListGroupsMemberSameLineAsyn;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentChargeLiquidationRecovery;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.message.BaseMsg;
import com.viettel.bss.viettelpos.v4.sale.adapter.SubscriberInfoAdapter;
import com.viettel.bss.viettelpos.v4.sale.adapter.SubscriberInfoAdapter.OnSelectSubscriber;
import com.viettel.bss.viettelpos.v4.sale.adapter.SubscriberInfoAdapter.OnViewSubscriberChild;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsynFindFeeByReasonTeleId;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskSearchSubscriber;
import com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.sale.strategy.ManageSubscriberFragmentStrategy;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.viettel.bss.viettelpos.v4.commons.auto.AutoConst.AUTO_ISDN;
import static com.viettel.bss.viettelpos.v4.commons.auto.AutoConst.AUTO_NUMBER_PAPER;
public class FragmentManageSubscriber extends FragmentCommon implements
        OnClickListener, OnSelectSubscriber, OnViewSubscriberChild {

    private Activity mActivity;
    private ManageSubscriberFragmentStrategy fragmentStrategy;

    	private InstantAutoComplete edt_idno;
    private String funtionTypeKey;
    private String function;
   private InstantAutoComplete edt_isdnAccount;
    private ListView lv_accountList;
    private List<SubscriberDTO> lstSubscribers;
    private SubscriberDTO subscriberDTO;
    private SubscriberInfoAdapter subscriberInfoAdapter;
    private CSKHBO cskhbo;
    private ReceiveRequestBean receiveRequestBean = null;
    private Dialog dialogLiquidation;

    //    private HashMap<String,Integer> mapFunc;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle mBundle = getArguments();
        if (mBundle != null) {
            receiveRequestBean = (ReceiveRequestBean) mBundle.getSerializable("ReceiveRequestBeanKey");
        }
        idLayout = R.layout.layout_manage_subscriber;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        mBundle = getArguments();
        if (mBundle == null) {
            Log.i("tag", "mBundle null");
        } else {
            Log.i("tag", "mBundle not null");
        }
        if (mBundle != null) {
            fragmentStrategy = (ManageSubscriberFragmentStrategy) mBundle
                    .getSerializable("fragmentStrategy");
            funtionTypeKey = mBundle.getString("funtionTypeKey");
            cskhbo = (CSKHBO) mBundle.getSerializable("cskhbo");
        }
    }

    @Override
    public void onResume() {
        if (Constant.LIQUIDATION_RECOVERY.equals(funtionTypeKey)) {
            MainActivity.getInstance().setTitleActionBar(getString(R.string.liquidation_and_recovery));
        } else {
            MainActivity.getInstance().setTitleActionBar(fragmentStrategy.getLabel());
        }
        super.onResume();
    }

    @Override
    public void unit(View v) {
        // TODO Auto-generated method stub
		edt_idno = (InstantAutoComplete) v.findViewById(R.id.edt_idno);
		edt_isdnAccount = (InstantAutoComplete) v.findViewById(R.id.edt_isdnAccount);

		AutoCompleteUtil.getInstance(getActivity()).autoComplete(AUTO_NUMBER_PAPER, edt_idno);
		AutoCompleteUtil.getInstance(getActivity()).autoComplete(AUTO_ISDN, edt_isdnAccount);
        lv_accountList = (ListView) v.findViewById(R.id.lv_accountList);

        lstSubscribers = new ArrayList();
//		subscriberInfoAdapter = new SubscriberInfoAdapter(mActivity,
//				lstSubscribers, this,this);
//		lv_accountList.setAdapter(subscriberInfoAdapter);

        Button btn_searchAccount = (Button) v.findViewById(R.id.btn_searchAccount);
        btn_searchAccount.setOnClickListener(this);

		//edt_isdnAccount = (EditText) mView.findViewById(R.id.edt_isdnAccount);

        if (receiveRequestBean != null && !CommonActivity.isNullOrEmpty(receiveRequestBean.getServiceType()) && !CommonActivity.isNullOrEmpty(receiveRequestBean.getAccountChange())) {
            edt_isdnAccount.setText(receiveRequestBean.getAccountChange());
            edt_isdnAccount.setEnabled(false);
            edt_idno.setEnabled(false);
            String idNo = edt_idno.getText().toString().trim();
            btn_searchAccount.setVisibility(View.GONE);
            AsyncTaskSearchSubscriber asyncTaskSearchSubscriber = new AsyncTaskSearchSubscriber(mActivity, new OnPostSearchSubscriber(), moveLogInAct);
            asyncTaskSearchSubscriber.execute(
                    receiveRequestBean.getAccountChange(),
                    idNo);
        } else {
            edt_isdnAccount.setEnabled(true);
            btn_searchAccount.setVisibility(View.VISIBLE);
        }

        if (!CommonActivity.isNullOrEmpty(cskhbo)) {
            edt_isdnAccount.setText(cskhbo.getIsdn());
            edt_isdnAccount.setEnabled(false);

            onSearchIsdn();
        }

    }

    @Override
    public void onClick(View arg0) {
        super.onClick(arg0);
        switch (arg0.getId()) {
            case R.id.btn_searchAccount:
//			lstSubscribers = new ArrayList<SubscriberDTO>();
//			subscriberInfoAdapter.notifyDataSetChanged();
                onSearchIsdn();
                break;
            default:
                break;
        }

    }

    private void onSearchIsdn() {
        String isdn = edt_isdnAccount.getText().toString().trim();
        String idNo = edt_idno.getText().toString().trim();
        if (CommonActivity.isNullOrEmpty(isdn)
                && CommonActivity.isNullOrEmpty(idNo)) {
            CommonActivity.createAlertDialog(mActivity,
                    getResources().getString(R.string.isdn_account_idno_empty),
                    getString(R.string.app_name)).show();
            return;
        }
		AutoCompleteUtil.getInstance(getActivity()).addToSuggestionList(AUTO_NUMBER_PAPER, edt_idno.getText().toString());
		AutoCompleteUtil.getInstance(getActivity()).addToSuggestionList(AUTO_ISDN, edt_isdnAccount.getText().toString());

		AutoCompleteUtil.getInstance(getActivity()).autoComplete(AUTO_NUMBER_PAPER, edt_idno);
		AutoCompleteUtil.getInstance(getActivity()).autoComplete(AUTO_ISDN, edt_isdnAccount);

        AsyncTaskSearchSubscriber task = new AsyncTaskSearchSubscriber(
                mActivity, new OnPostSearchSubscriber(), moveLogInAct);
        task.execute(isdn, idNo);
    }

    private class OnPostSearchSubscriber implements
            OnPostExecuteListener<List<SubscriberDTO>> {

        @Override
        public void onPostExecute(List<SubscriberDTO> result, String errorCode,
                                  String description) {
            // TODO Auto-generated method stub
            if ("0".equals(errorCode)) {
                if (CommonActivity.isNullOrEmptyArray(result)) {
                    String des = getResources().getString(R.string.checkinfosub);
//					if(!CommonActivity.isNullOrEmpty(description)){
//						des = description;
//					}
                    CommonActivity.createAlertDialog(mActivity,
                            des,
                            getString(R.string.app_name)).show();
                    lstSubscribers.clear();
                    subscriberInfoAdapter = new SubscriberInfoAdapter(mActivity,
                            lstSubscribers, FragmentManageSubscriber.this, FragmentManageSubscriber.this);
                    lv_accountList.setAdapter(subscriberInfoAdapter);
                } else {
                    lstSubscribers.clear();
                    lstSubscribers.addAll(result);
//					subscriberInfoAdapter.notifyDataSetChanged();
                    subscriberInfoAdapter = new SubscriberInfoAdapter(mActivity,
                            lstSubscribers, FragmentManageSubscriber.this, FragmentManageSubscriber.this);
                    lv_accountList.setAdapter(subscriberInfoAdapter);

                }
            } else {
                String des = getResources().getString(R.string.checkinfosub);
                if (!CommonActivity.isNullOrEmpty(description)) {
                    des = description;
                }
                CommonActivity.createAlertDialog(mActivity,
                        des,
                        getString(R.string.app_name)).show();
                lstSubscribers.clear();
                subscriberInfoAdapter = new SubscriberInfoAdapter(mActivity,
                        lstSubscribers, FragmentManageSubscriber.this, FragmentManageSubscriber.this);
                lv_accountList.setAdapter(subscriberInfoAdapter);
            }

        }

    }

    @Override
    public void onSelect(final SubscriberDTO subscriberDTO, int position) {
        // TODO Auto-generated method stub
        if (Constant.LIQUIDATION_RECOVERY.equals(funtionTypeKey)) {

            if (!CommonActivity.isNullOrEmpty(subscriberDTO.getActStatus())) {
//                if (subscriberDTO.getActStatus().startsWith("0")) { //trang thai binh thuong chuyen qua man hinh chan cuoc nong
                //TODO chuyen sang man hinh chan mo Block open sub

                //TODO TH1 ==> neu ma tong hop cuoc nong ko no cuoc thi chuyen qua man hinh thanh ly luon
                //TODO TH2 ==> Neu no cuoc thi chuyen qua man hinh gach no(FragmentPayment) ==> Gach no xong ==> thanh ly

                this.subscriberDTO = subscriberDTO;
                showDialogToGoLiquidation(subscriberDTO, getContext(), "");
//                }
//                else if (subscriberDTO.getActStatus().startsWith("1") || subscriberDTO.getActStatus().startsWith("2")) { //truong hop chan 1 chieu hoac chan 2 chieu thi chuyen qua man hinh chan thanh ly
//                    //TODO chuyen sang man hinh thanh ly
//                    View.OnClickListener listener = new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Bundle bundle = new Bundle();
//                            bundle.putSerializable("subscriberDTO", subscriberDTO);
//                            bundle.putString("funtionTypeKey", funtionTypeKey);
//                            FragmentChargeLiquidationRecovery fragmentTarget = new FragmentChargeLiquidationRecovery();
//                            fragmentTarget.setArguments(bundle);
//                            ReplaceFragment.replaceFragment(getActivity(), fragmentTarget, false);
//
//                        }
//                    };
//                    CommonActivity.createDialog(
//                            getActivity(),
//                            getResources().getString(
//                                    R.string.revoke_confirm),
//                            getResources().getString(
//                                    R.string.app_name),
//                            getResources().getString(R.string.say_ko),
//                            getResources().getString(R.string.say_co),
//                            null, listener).show();
//                }
            }


        } else {
            Fragment newFragment = fragmentStrategy.getReplaceFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("subscriberDTO", subscriberDTO);
            if (!CommonActivity.isNullOrEmpty(funtionTypeKey))
                bundle.putString("funtionTypeKey", funtionTypeKey);
            newFragment.setArguments(bundle);
            ReplaceFragment.replaceFragment(getActivity(), newFragment, false);
        }

    }

    @Override
    public void setPermission() {
        // TODO Auto-generated method stub

    }

    @Override
    public void OnViewSubscriberDetail(SubscriberDTO subscriberDTO) {
        GetListGroupsMemberSameLineAsyn getListGroupsMemberSameLineAsyn = new GetListGroupsMemberSameLineAsyn(getActivity(), new OnPosGetSubChild(), moveLogInAct);
        getListGroupsMemberSameLineAsyn.execute(subscriberDTO.getSubId() + "");
    }

    private class OnPosGetSubChild implements OnPostExecuteListener<ArrayList<SubscriberDTO>> {

        @Override
        public void onPostExecute(ArrayList<SubscriberDTO> result, String errorCode, String description) {
            if ("0".equals(errorCode)) {
                if (!CommonActivity.isNullOrEmptyArray(result)) {
                    showDialogSubChild(result, getActivity());
                } else {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.no_data), getActivity().getString(R.string.app_name)).show();
                }
            } else {
                CommonActivity.createAlertDialog(getActivity(), description, getActivity().getString(R.string.app_name)).show();
            }
        }
    }

    // lay danh sach duong day
    private void showDialogSubChild(final ArrayList<SubscriberDTO> arrSubscriberDTO, Context context) {

        final Dialog dialogSubChild = new Dialog(context);
        dialogSubChild.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSubChild
                .setContentView(R.layout.dialog_layout_sub_child);
        ListView lvSubChild = (ListView) dialogSubChild.findViewById(R.id.lvSubChild);

        lvSubChild.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                edt_isdnAccount.setText(arrSubscriberDTO.get(arg2).getAccount());
                edt_idno.setText("");
                lstSubscribers = new ArrayList<SubscriberDTO>();
                if (subscriberInfoAdapter != null) {
                    subscriberInfoAdapter.notifyDataSetChanged();
                }
                dialogSubChild.cancel();
                AsyncTaskSearchSubscriber task = new AsyncTaskSearchSubscriber(
                        mActivity, new OnPostSearchSubscriber(),
                        moveLogInAct);
                task.execute(arrSubscriberDTO.get(arg2).getAccount(), "");


            }
        });

        SubscriberInfoChildAdapter subscriberInfoChildAdapter = new SubscriberInfoChildAdapter(context, arrSubscriberDTO);
        lvSubChild.setAdapter(subscriberInfoChildAdapter);

        Button btncancel = (Button) dialogSubChild.findViewById(R.id.btncancel);
        btncancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialogSubChild.cancel();
            }
        });
        dialogSubChild.show();
    }

    private void showDialogToGoLiquidation(final SubscriberDTO subscriberDTO, final Context context, final String function) {
        final HashMap<String, Integer> mapFunc = new HashMap<>();
        final List<String> listFunction=new ArrayList<>();
        mapFunc.put("Thanh lý thuê bao, không thu hồi hàng hóa", 1);
        mapFunc.put("Thu hồi hàng hóa", 2);
        mapFunc.put("Thanh lý thuê bao và thu hồi hàng hóa", 3);
        listFunction.add("Thanh lý thuê bao, không thu hồi hàng hóa");
        listFunction.add("Thu hồi hàng hóa");
        listFunction.add("Thanh lý thuê bao và thu hồi hàng hóa");
        dialogLiquidation = new Dialog(context);
        dialogLiquidation.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogLiquidation
                .setContentView(R.layout.dialog_to_fragment_liquidation);
        EditText edtSelectFunction = (EditText) dialogLiquidation.findViewById(R.id.editSelectFunction);
        LinearLayout setIgnore = (LinearLayout) dialogLiquidation.findViewById(R.id.setIgnore);
        final CheckBox cbIgnore = (CheckBox) dialogLiquidation.findViewById(R.id.cbIgnore);
        Button btnRun = (Button) dialogLiquidation.findViewById(R.id.btnRun);
        if (CommonActivity.isNullOrEmpty(function)) {
            edtSelectFunction.setText(context.getString(R.string.chooseAction));
            btnRun.setVisibility(View.GONE);
        } else {
            edtSelectFunction.setText(function);
            btnRun.setVisibility(View.VISIBLE);
        }
        final int idMap = mapFunc.get(function) == null ? 0 : mapFunc.get(function);
        if (idMap == 2) {
            setIgnore.setVisibility(View.VISIBLE);
            cbIgnore.setChecked(false);
        } else {
            setIgnore.setVisibility(View.GONE);
            cbIgnore.setChecked(false);
        }
        edtSelectFunction
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(getActivity(),
                                FragmentchoseFunction.class);
                        intent.putExtra("listFunction", (Serializable) listFunction);
                        startActivityForResult(intent, 1235);
                    }
                });

        btnRun.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (CommonActivity.isNullOrEmpty(function)) {

                            Dialog dialog = CommonActivity.createAlertDialog(getContext(), getContext().getString(R.string.checkfunction),
                                    getContext().getString(R.string.app_name));
                            dialog.show();

                        } else {
                            Fragment newFragment;
                            if (cbIgnore.isChecked() && idMap == 2) {
                                newFragment = new FragmentChargeLiquidationRecovery();
                            } else {
                                newFragment = fragmentStrategy.getReplaceFragment();
                            }
                            Bundle bundle = new Bundle();
                            if (!CommonActivity.isNullOrEmpty(cskhbo)) {
                                subscriberDTO.setNewProductCode(cskhbo.getPackageChange());
                                subscriberDTO.setRawData(cskhbo.getRawData());
                                subscriberDTO.setCdtCode(cskhbo.getCdtCode());
                            }

                            if (!CommonActivity.isNullOrEmpty(receiveRequestBean)) {
                                bundle.putString("productCodeHotline", receiveRequestBean.getProductCode());
                                bundle.putString("receiveRequestId", receiveRequestBean.getReciveRequestId());
                            }
                            bundle.putSerializable("subscriberDTO", subscriberDTO);
                            bundle.putString("function", function);
                            if (!CommonActivity.isNullOrEmpty(funtionTypeKey))
                                bundle.putString("funtionTypeKey", funtionTypeKey);
                            newFragment.setArguments(bundle);
                            dialogLiquidation.dismiss();
                            ReplaceFragment.replaceFragment(getActivity(), newFragment, false);
                        }
                    }
                }

        );
        dialogLiquidation.show();
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
    public void onMessage(BaseMsg msg) {
        EventBus.getDefault().removeStickyEvent(BaseMsg.class);

        onSearchIsdn();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 1235:
                    function = data.getExtras().getString("function");
                    if (function != null) {
                        if (!CommonActivity.isNullOrEmpty(dialogLiquidation) && !CommonActivity.isNullOrEmpty(subscriberDTO)) {
                            dialogLiquidation.dismiss();
                            showDialogToGoLiquidation(subscriberDTO, getContext(), function);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

}
