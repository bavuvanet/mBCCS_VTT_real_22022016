package com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustomerDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.captcha.Captcha;
import com.viettel.bss.viettelpos.v4.captcha.MathCaptcha;
import com.viettel.bss.viettelpos.v4.captcha.MathCaptcha.MathOptions;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.auto.AutoCompleteUtil;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.ActivityCreateNewRequestMobileNew;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.CreateConnectMobileOmiActivity;
import com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.GetSubscriberChangeSimAdapter;
import com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.GetSubscriberChangeSimAdapter.OnChangeCustomer;
import com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.IsdnAutoAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.HotLineReponseDetail;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ReceiveRequestBean;
import com.viettel.bss.viettelpos.v4.connecttionService.listener.ExpandableHeightListView;
import com.viettel.bss.viettelpos.v4.customer.asynctask.SearchInvalidInfoSubcriberAsyncTask;
import com.viettel.bss.viettelpos.v4.customer.fragment.FragmentBlockOpenSub;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.customer.object.SubInvalidDTO;
import com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.customview.InstantAutoComplete;
import com.viettel.bss.viettelpos.v4.message.BaseMsg;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ConnectionOrder;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.viettel.bss.viettelpos.v4.commons.auto.AutoConst.AUTO_ISDN;
import static com.viettel.bss.viettelpos.v4.commons.auto.AutoConst.AUTO_NUMBER_PAPER;

public class FragmentSearchSubChangeSim extends FragmentCommon implements OnChangeCustomer {

    public ReceiveRequestBean receiveRequestBean = new ReceiveRequestBean();
    public HotLineReponseDetail hotLineReponseDetail = new HotLineReponseDetail();
    public ConnectionOrder connectionOrder;
    private View mView = null;
    // thong tin cha
    private InstantAutoComplete editsogiayto;
    private InstantAutoComplete editisdnacount;
    private EditText editmasocanhan, editotp, editsotxll1, editsotxll2, editsotxll3;
    private LinearLayout lnloaibaomat, lnmasocanhan, lnotp, lntxll, lntxll1, lntxll2, lntxll3;
    private Button btnSendOTP, btnTimKiem;

    // sua doi thong tin khach hang
    private ProgressBar prbreasonBtn;
    private ExpandableHeightListView lvAccount;
    private Spinner spinner_loaibaomat;
    // khai bao loai bao mat
    private ArrayList<Spin> arrTypeSecurity = new ArrayList<Spin>();
    private GetSubscriberChangeSimAdapter adapter = null;
    private List<SubscriberDTO> arrSubscriberDTO = new ArrayList<SubscriberDTO>();
    private SubscriberDTO subscriberDTO = new SubscriberDTO();
    private String functionName = "";
    private boolean ignoreOTP = false;
    private Captcha cap = new MathCaptcha(100, 100,
            MathOptions.PLUS_MINUS_MULTIPLY);
    private IsdnAutoAdapter isdnAutoAdapter;
    private String omniProcessId = "";
    private String omniTaskId = "";
    private List<SubInvalidDTO> arrSubInvalidDTO = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle mBundle = getArguments();

        if (mBundle != null) {
            functionName = mBundle.getString("functionKey", "");
            hotLineReponseDetail = (HotLineReponseDetail) mBundle.getSerializable("hotLineReponseDetail");
            receiveRequestBean = (ReceiveRequestBean) mBundle.getSerializable("receiveRequestBean");
            connectionOrder = (ConnectionOrder) mBundle.getSerializable("connectionOrder");
            this.omniTaskId = mBundle.getString("omniTaskId", omniTaskId);
            this.omniProcessId = mBundle.getString("omniProcessId", omniProcessId);
        }

        if (mView == null) {
            mView = inflater.inflate(R.layout.layout_search_sub_changesim, container, false);
            unit(mView);
        }
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();

        String title = getString(R.string.txt_change_sim);
        if (Constant.CHANGE_POS_TO_PRE.equals(functionName)) {
            title = getString(R.string.change_pos_to_pre);
        } else if (Constant.CHANGE_PRE_TO_POS.equals(functionName)) {
            title = getString(R.string.change_pre_to_pos);
        } else if (Constant.BLOCK_OPEN_SUB_MOBILE.equals(functionName)) {
            title = getString(R.string.blockOpenSubMobile);
        } else if (Constant.BLOCK_OPEN_SUB_HOMEPHONE.equals(functionName)) {
            title = getString(R.string.blockOpenSubHomephone);
        } else if (Constant.MENU_FUNCTIONS.CUSTOMER_MANAGER.equals(functionName)) {
            title = getString(R.string.cus_management);
        } else if ("TRANSFER_CUS".equals(functionName)) {
            title = getActivity().getString(R.string.transfercus);
        } else if (connectionOrder != null && Constant.ORD_TYPE_CHANGE_TO_POSPAID.equals(connectionOrder.getOrderType())) {
            title = getString(R.string.change_pre_to_pos);
        }
        setTitleActionBar(title);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 100) {
                CustomerDTO customerDTO = (CustomerDTO) data.getExtras().getSerializable("customerDTO");
                if (!CommonActivity.isNullOrEmpty(customerDTO)) {
                    subscriberDTO.setCustomerDTOInput(customerDTO);
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btnTimKiem:
                if (CommonActivity.isNetworkConnected(getActivity())) {
                    AutoCompleteUtil.getInstance(getActivity()).addToSuggestionList(AUTO_NUMBER_PAPER, editsogiayto.getText().toString());
                    AutoCompleteUtil.getInstance(getActivity()).autoComplete(AUTO_NUMBER_PAPER, editsogiayto);

                    AutoCompleteUtil.getInstance(getActivity()).addToSuggestionList(AUTO_ISDN, editisdnacount.getText().toString());
                    AutoCompleteUtil.getInstance(getActivity()).autoComplete(AUTO_ISDN, editisdnacount);
                    lvAccount.setVisibility(View.GONE);

                    if (!CommonActivity.isNullOrEmptyArray(arrSubscriberDTO)) {
                        arrSubscriberDTO = new ArrayList<SubscriberDTO>();
                    }
                    // subscriberDTO = new SubscriberDTO();
                    adapter = new GetSubscriberChangeSimAdapter(arrSubscriberDTO, getActivity(),
                            FragmentSearchSubChangeSim.this, moveLogInAct);
                    lvAccount.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    if (validateSearch()) {

                        SearchSubscriberAsyn searchSubscriberAsyn = new SearchSubscriberAsyn(getActivity());
                        searchSubscriberAsyn.execute(editsogiayto.getText().toString().trim(),
                                editisdnacount.getText().toString().trim());

                    }
                } else {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.errorNetwork),
                            getActivity().getString(R.string.app_name)).show();
                }
                break;
            case R.id.btnSendOTP:

                OnClickListener confirm = new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        SendOTPUtilAsyn sendOTPUtilAsyn = new SendOTPUtilAsyn(getActivity());
                        sendOTPUtilAsyn.execute(editisdnacount.getText().toString().trim());
                    }
                };
                CommonActivity.createDialog(getActivity(),
                        getActivity().getString(R.string.sendotpds, editisdnacount.getText().toString().trim()),
                        getActivity().getString(R.string.app_name), getActivity().getString(R.string.cancel),
                        getActivity().getString(R.string.OK), null, confirm).show();

                break;
            default:
                break;
        }
    }

    private boolean validateSearch() {
        if (CommonActivity.isNullOrEmpty(editsogiayto.getText().toString().trim())) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.idnoemptycsim),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }
        if (CommonActivity.isNullOrEmpty(editisdnacount.getText().toString().trim())) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.isdnnotempty),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }

        if (!CommonActivity.isNullOrEmpty(subscriberDTO) && subscriberDTO.isMarkNotOwner()) {

            Spin item = (Spin) spinner_loaibaomat.getSelectedItem();
            lnloaibaomat.setVisibility(View.VISIBLE);
            if (item == null) {
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validatetextsc),
                        getActivity().getString(R.string.app_name)).show();
                return false;
            }
            if (CommonActivity.isNullOrEmpty(item.getId())) {
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validatetextsc),
                        getActivity().getString(R.string.app_name)).show();
                return false;
            }
            // bat buoc nhap so otp
            if ("1".equals(item.getId())) {
                if (CommonActivity.isNullOrEmpty(editotp.getText().toString())) {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validateotp),
                            getActivity().getString(R.string.app_name)).show();
                    lnotp.setVisibility(View.VISIBLE);
                    return false;
                }
            }
            if ("2".equals(item.getId())) {
                if (CommonActivity.isNullOrEmpty(editmasocanhan.getText().toString().trim())) {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.masocnempty),
                            getActivity().getString(R.string.app_name)).show();
                    lnmasocanhan.setVisibility(View.VISIBLE);
                    return false;
                }
            }
            // so thuong xuyen lien lac
            if ("3".equals(item.getId())) {
                lntxll.setVisibility(View.VISIBLE);
                if (subscriberDTO.isNormalIsdn()) {
                    if (CommonActivity.isNullOrEmpty(editsotxll1.getText().toString().trim())) {
                        CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validateisdn),
                                getActivity().getString(R.string.app_name)).show();
                        return false;
                    }
                } else {
                    if (CommonActivity.isNullOrEmpty(editsotxll1.getText().toString().trim())) {
                        CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validate3isdn),
                                getActivity().getString(R.string.app_name)).show();
                        return false;
                    }
                    if (CommonActivity.isNullOrEmpty(editsotxll2.getText().toString().trim())) {
                        CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validate3isdn),
                                getActivity().getString(R.string.app_name)).show();
                        return false;
                    }
                    if (CommonActivity.isNullOrEmpty(editsotxll3.getText().toString().trim())) {
                        CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validate3isdn),
                                getActivity().getString(R.string.app_name)).show();
                        return false;
                    }
                }

            }

        }

        return true;
    }

    @Override
    public void unit(View v) {
        editsogiayto = (InstantAutoComplete) v.findViewById(R.id.editsogiayto);
        if (!CommonActivity.isNullOrEmpty(connectionOrder) && !CommonActivity.isNullOrEmpty(connectionOrder.getCustomer().getIdNo())) {
            editsogiayto.setText(connectionOrder.getCustomer().getIdNo());
            editsogiayto.setEnabled(false);
        }
        editisdnacount = (InstantAutoComplete) v.findViewById(R.id.editisdnacount);
        if (!CommonActivity.isNullOrEmpty(connectionOrder) && !CommonActivity.isNullOrEmpty(connectionOrder.getIsdn())) {
            editisdnacount.setText(connectionOrder.getIsdn());
            editisdnacount.setEnabled(false);
        }
        AutoCompleteUtil.getInstance(getActivity()).autoComplete(AUTO_NUMBER_PAPER, editsogiayto);
        AutoCompleteUtil.getInstance(getActivity()).autoComplete(AUTO_ISDN, editisdnacount);

        editmasocanhan = (EditText) v.findViewById(R.id.editmasocanhan);
        editotp = (EditText) v.findViewById(R.id.editotp);

//        lvIsdn = (ExpandableHeightListView) v.findViewById(R.id.lvIsdn);
//        lvIsdn.setExpanded(true);
        editsotxll1 = (EditText) v.findViewById(R.id.editsotxll1);
        editsotxll2 = (EditText) v.findViewById(R.id.editsotxll2);
        editsotxll3 = (EditText) v.findViewById(R.id.editsotxll3);

        lnloaibaomat = (LinearLayout) v.findViewById(R.id.lnloaibaomat);
        lnloaibaomat.setVisibility(View.GONE);
        lnmasocanhan = (LinearLayout) v.findViewById(R.id.lnmasocanhan);
        lnmasocanhan.setVisibility(View.GONE);
        lnotp = (LinearLayout) v.findViewById(R.id.lnotp);
        lnotp.setVisibility(View.GONE);
        lntxll = (LinearLayout) v.findViewById(R.id.lntxll);
        lntxll.setVisibility(View.GONE);
        lntxll1 = (LinearLayout) v.findViewById(R.id.lntxll1);
        lntxll2 = (LinearLayout) v.findViewById(R.id.lntxll2);
        lntxll3 = (LinearLayout) v.findViewById(R.id.lntxll3);

        btnSendOTP = (Button) v.findViewById(R.id.btnSendOTP);
        btnSendOTP.setOnClickListener(this);
        btnTimKiem = (Button) v.findViewById(R.id.btnTimKiem);
        btnTimKiem.setOnClickListener(this);
        prbreasonBtn = (ProgressBar) v.findViewById(R.id.prbreasonBtn);

        lvAccount = (ExpandableHeightListView) v.findViewById(R.id.lvAccount);
        lvAccount.setExpanded(true);

        lvAccount.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                SubscriberDTO subscriberDTO = arrSubscriberDTO.get(arg2);
                subscriberDTO = arrSubscriberDTO.get(arg2);
                //truong hop cap nhat thong tin khac hang
                if (!CommonActivity.isNullOrEmpty(functionName) && Constant.MENU_FUNCTIONS.CUSTOMER_MANAGER.equals(functionName)) {
                    if (!CommonActivity.isNullOrEmpty(subscriberDTO) && !CommonActivity.isNullOrEmpty(subscriberDTO.getCustomerDTOInput()) && !subscriberDTO.isMarkNotOwner()) {
                        AsynGetCustomerByCustIdParent asynGetCustomerByCustIdParent = new AsynGetCustomerByCustIdParent(getActivity(), subscriberDTO.getCustomerDTOInput());
                        asynGetCustomerByCustIdParent.execute(subscriberDTO.getCustomerDTOInput().getCustId() + "");
                    }
                } else if (!CommonActivity.isNullOrEmpty(functionName) && Constant.CHANGE_PRE_TO_POS.equals(functionName)) {
                    if (!CommonActivity.isNullOrEmpty(subscriberDTO) && "2".equals(subscriberDTO.getPayType())) {
                        ValidateCommitmentAsyn validateCommitmentAsyn = new ValidateCommitmentAsyn(getActivity(), subscriberDTO);
                        validateCommitmentAsyn.execute(subscriberDTO.getSubId() + "", "220", subscriberDTO.getIsdn());
                    } else {
                        CommonActivity.createAlertDialog(getActivity(),
                                getActivity().getString(R.string.ttintbtt, subscriberDTO.getIsdn()),
                                getActivity().getString(R.string.app_name)).show();

                    }
                } else if (!CommonActivity.isNullOrEmpty(functionName)
                        && Constant.CHANGE_POS_TO_PRE.equals(functionName)) {
                    if (!CommonActivity.isNullOrEmpty(subscriberDTO) && "1".equals(subscriberDTO.getPayType())) {
                        ValidateCommitmentAsyn validateCommitmentAsyn =
                                new ValidateCommitmentAsyn(getActivity(), subscriberDTO);
                        validateCommitmentAsyn.execute(subscriberDTO.getSubId() + "", "221", subscriberDTO.getIsdn());
                    } else {
                        CommonActivity.createAlertDialog(getActivity(),
                                getActivity().getString(R.string.ttintbtt, subscriberDTO.getIsdn()),
                                getActivity().getString(R.string.app_name)).show();
                    }
                } else if (Constant.BLOCK_OPEN_SUB_MOBILE.equals(functionName)
                        || Constant.BLOCK_OPEN_SUB_HOMEPHONE.equals(functionName)) {
                    if (!CommonActivity.isNullOrEmpty(subscriberDTO)) {
                        Bundle mBundle = new Bundle();
                        mBundle.putSerializable("subscriberDTOKey", subscriberDTO);
                        mBundle.putString("funtionTypeKey", functionName);
                        FragmentBlockOpenSub mListMenuManager = new FragmentBlockOpenSub();
                        mListMenuManager.setArguments(mBundle);
                        ReplaceFragment.replaceFragment(getActivity(), mListMenuManager, false);
                    } else {
                        CommonActivity.createAlertDialog(getActivity(),
                                getActivity().getString(R.string.ttintbtt, subscriberDTO.getIsdn()),
                                getActivity().getString(R.string.app_name)).show();
                    }
                } else if ("TRANSFER_CUS".equals(functionName)) {
                    Bundle mBundle = new Bundle();
                    arrSubscriberDTO.get(arg2).setIgnoreOTPCTV(ignoreOTP);
                    mBundle.putSerializable("subscriberDTO", arrSubscriberDTO.get(arg2));
                    mBundle.putString("idNo", editsogiayto.getText().toString().trim());
                    FragmentTransferCustomer mListMenuManager = new FragmentTransferCustomer();
                    mListMenuManager.setArguments(mBundle);
                    ReplaceFragment.replaceFragment(getActivity(), mListMenuManager, false);
                } else if (!CommonActivity.isNullOrEmptyArray(arrSubscriberDTO)) {
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("subscriberDTO", arrSubscriberDTO.get(arg2));
                    mBundle.putSerializable("receiveRequestBean", receiveRequestBean);
                    mBundle.putSerializable("hotLineReponseDetail", hotLineReponseDetail);
                    mBundle.putString("idNo", editsogiayto.getText().toString().trim());
                    FragmentChangeSim mListMenuManager = new FragmentChangeSim();
                    mListMenuManager.setArguments(mBundle);
                    ReplaceFragment.replaceFragment(getActivity(), mListMenuManager, false);
                }
            }
        });
        spinner_loaibaomat = (Spinner) v.findViewById(R.id.spinner_loaibaomat);
        if (arrTypeSecurity != null && arrTypeSecurity.size() > 0) {
            arrTypeSecurity = new ArrayList<Spin>();
        }
        initTypeSecurity();
        spinner_loaibaomat.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                editmasocanhan.setText("");
                editotp.setText("");
                editsotxll1.setText("");
                editsotxll2.setText("");
                editsotxll3.setText("");
                if (isdnAutoAdapter != null) {
                    isdnAutoAdapter.notifyDataSetChanged();
                }
                Spin item = (Spin) arg0.getItemAtPosition(arg2);
                if (!CommonActivity.isNullOrEmptyArray(arrSubscriberDTO)) {
                    arrSubscriberDTO = new ArrayList<SubscriberDTO>();
                }
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
                adapter = new GetSubscriberChangeSimAdapter(arrSubscriberDTO, getActivity(),
                        FragmentSearchSubChangeSim.this, moveLogInAct);
                lvAccount.setAdapter(adapter);
//                lntxll.setVisibility(View.GONE);
                if (item != null && !CommonActivity.isNullOrEmpty(item.getId())) {

                    switch (Integer.parseInt(item.getId())) {
                        case 2:
                            lnmasocanhan.setVisibility(View.VISIBLE);
                            lnotp.setVisibility(View.GONE);
                            lntxll.setVisibility(View.GONE);
                            break;
                        case 3:
                            lnmasocanhan.setVisibility(View.GONE);
                            lnotp.setVisibility(View.GONE);
                            lntxll.setVisibility(View.VISIBLE);

                            if (!CommonActivity.isNullOrEmpty(subscriberDTO) && subscriberDTO.isNormalIsdn()) {
                                lntxll1.setVisibility(View.VISIBLE);
                                lntxll2.setVisibility(View.GONE);
                                lntxll3.setVisibility(View.GONE);
                            } else {
                                lntxll1.setVisibility(View.VISIBLE);
                                lntxll2.setVisibility(View.VISIBLE);
                                lntxll3.setVisibility(View.VISIBLE);
                            }

                            break;
                        case 1:
                            lnmasocanhan.setVisibility(View.GONE);
                            lnotp.setVisibility(View.VISIBLE);
                            lntxll.setVisibility(View.GONE);
                            break;
                        default:
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        // edt so giay to, edit isdn
        if (!CommonActivity.isNullOrEmpty(connectionOrder)
                && !CommonActivity.isNullOrEmpty(connectionOrder.getCustomer()) && !CommonActivity.isNullOrEmpty(connectionOrder.getCustomer().getIdNo()) && !CommonActivity.isNullOrEmpty(connectionOrder.getIsdn())) {
            SearchSubscriberAsyn searchSubscriberAsyn = new SearchSubscriberAsyn(getActivity());
            searchSubscriberAsyn.execute(editsogiayto.getText().toString().trim(),
                    editisdnacount.getText().toString().trim());
        }

        editsogiayto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                subscriberDTO = new SubscriberDTO();
                resetForm();
            }
        });

        editisdnacount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                subscriberDTO = new SubscriberDTO();
                resetForm();
            }
        });
    }

    private void initTypeSecurity() {
        arrTypeSecurity.add(new Spin("", getActivity().getString(R.string.txt_select)));
        arrTypeSecurity.add(new Spin("2", getActivity().getString(R.string.masocn)));
        arrTypeSecurity.add(new Spin("3", getActivity().getString(R.string.sotxllds)));
        arrTypeSecurity.add(new Spin("1", getActivity().getString(R.string.maotp)));
        Utils.setDataSpinner(getActivity(), arrTypeSecurity, spinner_loaibaomat);
    }

    @Override
    public void onChangeCustomerListener(SubscriberDTO subscriberDTO) {

        if (!CommonActivity.isNullOrEmpty(subscriberDTO) && !CommonActivity.isNullOrEmpty(subscriberDTO.getCustomerDTOInput()) && !subscriberDTO.isMarkNotOwner()) {
            AsynGetCustomerByCustIdParent asynGetCustomerByCustIdParent = new AsynGetCustomerByCustIdParent(getActivity(), subscriberDTO.getCustomerDTOInput());
            asynGetCustomerByCustIdParent.execute(subscriberDTO.getCustomerDTOInput().getCustId() + "");
        }


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
    public void onMessage(BaseMsg msg) {
        EventBus.getDefault().removeStickyEvent(BaseMsg.class);

        if (CommonActivity.isNetworkConnected(getActivity())) {
            lvAccount.setVisibility(View.GONE);

            if (!CommonActivity.isNullOrEmptyArray(arrSubscriberDTO)) {
                arrSubscriberDTO = new ArrayList<SubscriberDTO>();
            }
            // subscriberDTO = new SubscriberDTO();
            adapter = new GetSubscriberChangeSimAdapter(arrSubscriberDTO, getActivity(),
                    FragmentSearchSubChangeSim.this, moveLogInAct);
            lvAccount.setAdapter(adapter);
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
            if (validateSearch()) {
                SearchSubscriberAsyn searchSubscriberAsyn = new SearchSubscriberAsyn(getActivity());
                searchSubscriberAsyn.execute(editsogiayto.getText().toString().trim(),
                        editisdnacount.getText().toString().trim());
            }
        }
    }

    // lay thong tin otp
    private class SendOTPUtilAsyn extends AsyncTask<String, Void, String> {
        private Context mContext;
        private String errorCode;
        private String description;
        private ProgressDialog progress;

        public SendOTPUtilAsyn(Context context) {
            this.mContext = context;
            this.progress = new ProgressDialog(this.mContext);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdatareport));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected String doInBackground(String... arg0) {
            return sendOtpUntil(arg0[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progress.dismiss();
            if ("0".equals(errorCode)) {

                CommonActivity.createAlertDialog(getActivity(),
                        getActivity().getString(R.string.confirmotpds, editisdnacount.getText().toString().trim()),
                        getActivity().getString(R.string.app_name)).show();

            } else {
                if (Constant.INVALID_TOKEN2.equals(errorCode) && description != null && !description.isEmpty()) {

                    Dialog dialog = CommonActivity.createAlertDialog((Activity) mContext, description,
                            mContext.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = mContext.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                            getResources().getString(R.string.app_name));
                    dialog.show();

                }
            }

        }

        private String sendOtpUntil(String isdn) {
            String original = null;
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_sendOTPUtil");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:sendOTPUtil>");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");
                rawData.append("<isdn>" + CommonActivity.getStardardIsdnBCCS(isdn) + "</isdn>");
                rawData.append("</input>");
                rawData.append("</ws:sendOTPUtil>");
                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(), "mbccs_sendOTPUtil");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);

                original = output.getOriginal();
                Log.d("originalllllllll", original);

                Serializer serializer = new Persister();
                ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
                if (parseOuput != null) {
                    errorCode = parseOuput.getErrorCode();
                    description = parseOuput.getDescription();
                } else {
                    errorCode = Constant.ERROR_CODE;
                    description = getActivity().getString(R.string.no_data_return);
                }

            } catch (Exception e) {
                Log.d("sendOtpUntil", e.toString());
                errorCode = Constant.ERROR_CODE;
                description = getActivity().getString(R.string.no_data_return);
            }

            return errorCode;
        }

    }

    private void resetForm(){
        lnloaibaomat.setVisibility(View.GONE);
        lnmasocanhan.setVisibility(View.GONE);
        lnotp.setVisibility(View.GONE);
        lntxll.setVisibility(View.GONE);
        editmasocanhan.setText("");
        editotp.setText("");
        editsotxll1.setText("");
        editsotxll2.setText("");
        editsotxll3.setText("");
    }

    // tim kiem thong tin thue bao
    private class SearchSubscriberAsyn extends AsyncTask<String, Void, List<SubscriberDTO>> {

        private Context mContext;
        private String errorCode;
        private String description;
        private ProgressDialog progress;

        public SearchSubscriberAsyn(Context context) {
            this.mContext = context;
            this.progress = new ProgressDialog(this.mContext);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdatareport));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected List<SubscriberDTO> doInBackground(String... arg0) {
            return getListSubscriber(arg0[0], arg0[1]);
        }

        @Override
        protected void onPostExecute(List<SubscriberDTO> result) {
            super.onPostExecute(result);
            progress.dismiss();
            arrSubscriberDTO = new ArrayList<SubscriberDTO>();
            subscriberDTO = new SubscriberDTO();


            if ("0".equals(errorCode)) {
                if (!CommonActivity.isNullOrEmpty(result)) {
                    subscriberDTO = result.get(0);
                    lvAccount.setVisibility(View.VISIBLE);
                    //hien thi thong bao sai ho so

                    for (SubInvalidDTO subInvalidDTO : arrSubInvalidDTO) {
                        if ("1".equals(subInvalidDTO.getStatus())) {
                            CommonActivity.createAlertDialog(getActivity(),
                                    getActivity().getResources().getString(R.string.thong_bao_thue_bao_sai_thong_tin,
                                            subInvalidDTO.getDescription()),
                                    getActivity().getResources().getString(R.string.app_name)).show();
                             subscriberDTO.setCustomerInfoInvalid(true);
                        }
                    }

                    if (subscriberDTO.isMarkNotOwner()) {
                        // truogn hop ma khong chinh chu
                        // if(subscriberDTO.isHasVerifiedOwner()){
                        lnloaibaomat.setVisibility(View.VISIBLE);

                        if (!CommonActivity.isNullOrEmpty(subscriberDTO.getSubId())
                                    && (!CommonActivity.isNullOrEmpty(editmasocanhan.getText().toString())
                                || !CommonActivity.isNullOrEmpty(editotp.getText().toString())
                                    || !CommonActivity.isNullOrEmpty(editsotxll1.getText().toString()))) {
                                resetForm();

                            arrSubscriberDTO.add(result.get(0));
                            adapter = new GetSubscriberChangeSimAdapter(arrSubscriberDTO, getActivity(),
                                    FragmentSearchSubChangeSim.this, moveLogInAct);
                            lvAccount.setAdapter(adapter);
                        } else {
                            if (!CommonActivity.isNullOrEmptyArray(arrTypeSecurity)) {
                                for (Spin item : arrTypeSecurity) {
                                    if (CommonActivity.isNullOrEmpty(item.getId())) {
                                        spinner_loaibaomat.setSelection(arrTypeSecurity.indexOf(item));
                                    }
                                }
                            }
                            // subscriberDTO = new SubscriberDTO();
                            arrSubscriberDTO = new ArrayList<SubscriberDTO>();
                            if (adapter != null) {
                                adapter.notifyDataSetChanged();
                            }
                        }


                    } else {
                        resetForm();

                        // truong hop chinh chu
                        arrSubscriberDTO.add(result.get(0));
                        adapter = new GetSubscriberChangeSimAdapter(arrSubscriberDTO, getActivity(),
                                FragmentSearchSubChangeSim.this, moveLogInAct);
                        lvAccount.setAdapter(adapter);
                    }
                } else {
                    lnloaibaomat.setVisibility(View.GONE);
                    lnmasocanhan.setVisibility(View.GONE);
                    lnotp.setVisibility(View.GONE);
                    lntxll.setVisibility(View.GONE);
                    editmasocanhan.setText("");
                    editotp.setText("");
                    editsotxll1.setText("");
                    editsotxll2.setText("");
                    editsotxll3.setText("");
                    arrSubscriberDTO = new ArrayList<SubscriberDTO>();
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.no_data),
                            getActivity().getString(R.string.app_name)).show();
                }
            } else {
                // subscriberDTO = new SubscriberDTO();
                arrSubscriberDTO = new ArrayList<SubscriberDTO>();
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }


                if (Constant.INVALID_TOKEN2.equals(errorCode) && description != null && !description.isEmpty()) {

                    Dialog dialog = CommonActivity.createAlertDialog((Activity) mContext, description,
                            mContext.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = mContext.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                            getResources().getString(R.string.app_name));
                    dialog.show();

                }
            }
        }

        private List<SubscriberDTO> getListSubscriber(String idNo, String isdnAccount) {
            String original = null;
            List<SubscriberDTO> lstSubscriber = new ArrayList<SubscriberDTO>();
            SubscriberDTO subscriberDTO = new SubscriberDTO();
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_searchSubscriberCSBccs2");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:searchSubscriberCS>");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");

                // chi doi sim moi check quyen so dep
                if (CommonActivity.isNullOrEmpty(functionName)) {
                    rawData.append("<type>" + "SCUHBOT" + "</type>");
                } else {
                    rawData.append("<type>" + "SCUHB" + "</type>");
                }

                Spin spin = (Spin) spinner_loaibaomat.getSelectedItem();
                rawData.append("<isdn>" + CommonActivity.getStardardIsdnBCCS(isdnAccount));
                rawData.append("</isdn>");
                rawData.append("<documentNo>" + idNo);
                rawData.append("</documentNo>");

                if (!CommonActivity.isNullOrEmpty(functionName)) {
                    rawData.append("<documentNo>" + idNo);
                    rawData.append("</documentNo>");
                }


                if ((!CommonActivity.isNullOrEmpty(editmasocanhan.getText().toString())
                        || !CommonActivity.isNullOrEmpty(editotp.getText().toString())
                        || !CommonActivity.isNullOrEmpty(editsotxll1.getText().toString()))
                        && !CommonActivity.isNullOrEmpty(spin) && !CommonActivity.isNullOrEmpty(spin.getId())) {
                    if ("1".equals(spin.getId())) {
                        rawData.append("<value>" + editotp.getText().toString().trim());
                        rawData.append("</value>");
                    } else {
                        rawData.append("<securityForm>");
                        rawData.append("<type>" + spin.getId());
                        rawData.append("</type>");
                        if ("2".equals(spin.getId())) {
                            rawData.append("<mscn>" + editmasocanhan.getText().toString().trim());
                            rawData.append("</mscn>");
                        }
                        if ("3".equals(spin.getId())) {
                            if (subscriberDTO.isNormalIsdn()) {
                                rawData.append("<lstIsdn>"
                                        + CommonActivity.getStardardIsdnBCCS(editsotxll1.getText().toString().trim()));
                                rawData.append("</lstIsdn>");
                            } else {
                                rawData.append("<lstIsdn>"
                                        + CommonActivity.getStardardIsdnBCCS(editsotxll1.getText().toString().trim()));
                                rawData.append("</lstIsdn>");
                                rawData.append("<lstIsdn>"
                                        + CommonActivity.getStardardIsdnBCCS(editsotxll2.getText().toString().trim()));
                                rawData.append("</lstIsdn>");
                                rawData.append("<lstIsdn>"
                                        + CommonActivity.getStardardIsdnBCCS(editsotxll3.getText().toString().trim()));
                                rawData.append("</lstIsdn>");
                            }

                        }
                        rawData.append("</securityForm>");
                    }
                }
                rawData.append("</input>");
                rawData.append("</ws:searchSubscriberCS>");
                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
                        "mbccs_searchSubscriberCSBccs2");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);

                original = output.getOriginal();
                Log.d("originalllllllll", original);

                Serializer serializer = new Persister();
                ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
                if (parseOuput != null) {
                    errorCode = parseOuput.getErrorCode();
                    description = parseOuput.getDescription();
                    subscriberDTO = parseOuput.getSubscriberDTOExt();
                    subscriberDTO.setCustomerDTOInput(parseOuput.getSubscriberDTOExt().getCustomerDTOInput());
                    subscriberDTO.setAccountDTOForInput(parseOuput.getAccountDTO());
                    subscriberDTO.setLstSubPromotionDTO(parseOuput.getLstSubPromotionDTO());
                    subscriberDTO.setLstSubPromotionPrepaidDTO(parseOuput.getLstSubPromotionPrepaidDTO());
                    lstSubscriber.add(subscriberDTO);

                    //lay danh sach thue bao sai ho so
                    arrSubInvalidDTO = parseOuput.getLstSubInvalidDTO();
                    if(CommonActivity.isNullOrEmpty(arrSubInvalidDTO)){
                        arrSubInvalidDTO = new ArrayList<>();
                    }
                }

                return lstSubscriber;
            } catch (Exception e) {
                Log.d("getListSubscriber", e.toString());
            }

            return null;
        }
    }

    private class ValidateCommitmentAsyn extends AsyncTask<String, Void, String> {
        SubscriberDTO mSubscriberDTO;
        private Context mContext;
        private String errorCode;
        private String description;
        private ProgressDialog progress;

        public ValidateCommitmentAsyn(Context context, SubscriberDTO subscriberDTO) {
            this.mContext = context;
            this.progress = new ProgressDialog(this.mContext);
            // check font
            this.mSubscriberDTO = subscriberDTO;
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(
                    R.string.getdatareport));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected String doInBackground(String... arg0) {
            return validateCommitment(arg0[0], arg0[1], arg0[2]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progress.dismiss();
            if ("0".equals(errorCode)) {
                if (functionName != null
                        && (functionName.equals(Constant.CHANGE_POS_TO_PRE)
                        || Constant.BLOCK_OPEN_SUB_MOBILE.equals(functionName)
                        || Constant.BLOCK_OPEN_SUB_HOMEPHONE.equals(functionName))) {
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("subscriberDTOKey", mSubscriberDTO);
                    mBundle.putString("funtionTypeKey", functionName);
                    mBundle.putSerializable("receiveRequestBean", receiveRequestBean);
                    mBundle.putSerializable("hotLineReponseDetail", hotLineReponseDetail);
                    FragmentBlockOpenSub mListMenuManager = new FragmentBlockOpenSub();
                    mListMenuManager.setArguments(mBundle);
                    ReplaceFragment.replaceFragment(getActivity(), mListMenuManager, false);
                } else {
                    if (connectionOrder != null) {
                        Intent intent = new Intent(getActivity(), CreateConnectMobileOmiActivity.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putSerializable("subscriberDTOKey", mSubscriberDTO);
                        mBundle.putSerializable("connectionOrder", connectionOrder);
                        intent.putExtras(mBundle);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getActivity(), ActivityCreateNewRequestMobileNew.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putSerializable("subscriberDTOKey", mSubscriberDTO);
                        mBundle.putString("funtionTypeKey", functionName);
                        mBundle.putSerializable("receiveRequestBean", receiveRequestBean);
                        mBundle.putSerializable("hotLineReponseDetail", hotLineReponseDetail);
                        //omni
                        mBundle.putString("omniProcessId", omniProcessId);
                        mBundle.putString("omniTaskId", omniTaskId);
                        intent.putExtras(mBundle);
                        startActivity(intent);
                    }
                }
            } else {
                if (Constant.INVALID_TOKEN2.equals(errorCode)
                        && description != null && !description.isEmpty()) {

                    Dialog dialog = CommonActivity
                            .createAlertDialog((Activity) mContext,
                                    description, mContext.getResources()
                                            .getString(R.string.app_name),
                                    moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = mContext.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(
                            getActivity(), description, getResources()
                                    .getString(R.string.app_name));
                    dialog.show();
                }
            }
        }

        private String validateCommitment(String subId, String actionCode, String isdn) {
            String original = null;
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_validateCommitment");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:validateCommitment>");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");
                rawData.append("<subId>"
                        + subId + "</subId>");
                rawData.append("<isdn>"
                        + isdn.trim() + "</isdn>");
                rawData.append("<actionCode>"
                        + actionCode + "</actionCode>");

                rawData.append("</input>");
                rawData.append("</ws:validateCommitment>");
                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input
                        .sendRequest(envelope, Constant.BCCS_GW_URL,
                                getActivity(), "mbccs_validateCommitment");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);

                original = output.getOriginal();
                Log.d("originalllllllll", original);

                Serializer serializer = new Persister();
                ParseOuput parseOuput = serializer.read(ParseOuput.class,
                        original);
                if (parseOuput != null) {
                    errorCode = parseOuput.getErrorCode();
                    description = parseOuput.getDescription();
                } else {
                    errorCode = Constant.ERROR_CODE;
                    description = getActivity().getString(
                            R.string.no_data_return);
                }

            } catch (Exception e) {
                Log.d("sendOtpUntil", e.toString());
                errorCode = Constant.ERROR_CODE;
                description = getActivity().getString(R.string.no_data_return);
            }

            return errorCode;
        }

    }

    private class AsynGetCustomerByCustIdParent extends AsyncTask<String, Void, CustomerDTO> {

        private final Context context;
        private final ProgressDialog progress;
        private String errorCode;
        private String description;
        private CustomerDTO mCustomerDTO;
        private String isPSenTdO;

        public AsynGetCustomerByCustIdParent(Context mContext, CustomerDTO custIdentityDTOEdit) {
            this.context = mContext;
            this.progress = new ProgressDialog(this.context);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
            this.mCustomerDTO = custIdentityDTOEdit;
        }

        @Override
        protected CustomerDTO doInBackground(String... params) {
            return getCustomerByCustId(params[0]);
        }

        @Override
        protected void onPostExecute(CustomerDTO result) {
            progress.dismiss();
            super.onPostExecute(result);
            if ("0".equals(errorCode)) {
                // thong tin hop dong cu
                if (result != null && result.getCustId() != null) {
                    // truong hop sua thong tin khach hang
                    if (mCustomerDTO != null) {

                        Bundle mBundle = new Bundle();
                        mBundle.putSerializable("customerDTO", result);
                        mBundle.putString("subType", subscriberDTO.getPayType());
                        mBundle.putString("isPSenTdO", isPSenTdO);
                        mBundle.putString("functionKey", functionName);

                        subscriberDTO.setIgnoreOTPCTV(ignoreOTP);
                        mBundle.putSerializable("subscriberDTO", subscriberDTO);

                        FragmentEditCustomerBCCS mListMenuManager = new FragmentEditCustomerBCCS();
                        mListMenuManager.setArguments(mBundle);
                        mListMenuManager.setTargetFragment(FragmentSearchSubChangeSim.this, 100);
                        ReplaceFragment.replaceFragment(getActivity(), mListMenuManager,
                                false);

                    }

                } else {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notDetailCus),
                            getActivity().getString(R.string.app_name)).show();
                }

            } else {
                if (Constant.INVALID_TOKEN2.equals(errorCode)) {

                    CommonActivity.BackFromLogin(getActivity(), description, "");
                } else {
                    if (description == null || description.isEmpty()) {
                        description = context.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                            getResources().getString(R.string.app_name));
                    dialog.show();

                }
            }

        }

        private CustomerDTO getCustomerByCustId(String custId) {

            CustomerDTO customerDTO = new CustomerDTO();
            String original = "";
            try {

                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getCustomerByCustId");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getCustomerByCustId>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));

                rawData.append("<custId>").append(custId);
                rawData.append("</custId>");

//                KHCH_TT
                if (!CommonActivity.isNullOrEmpty(mCustomerDTO)) {
                    rawData.append("<type>" + "KHCH_TT");
                    rawData.append("</type>");
                } else {
                    rawData.append("<type>" + "");
                    rawData.append("</type>");
                }

                rawData.append("</input>");
                rawData.append("</ws:getCustomerByCustId>");
                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
                        "mbccs_getCustomerByCustId");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.d("original", original);
                // ====== parse xml ===================

                Serializer serializer = new Persister();
                ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
                if (parseOuput != null) {
                    errorCode = parseOuput.getErrorCode();
                    description = parseOuput.getDescription();
                    isPSenTdO = parseOuput.getIsPSenTdO();
                    customerDTO = parseOuput.getCustomerDTO();
                }

                return customerDTO;

            } catch (Exception e) {
                Log.e("getCustomerByCustId + exception", e.toString(), e);
            }
            return customerDTO;

        }
    }

//    private class OnPostGetOptionSetValue implements OnPostExecuteListener<ArrayList<Spin>> {
//        @Override
//        public void onPostExecute(ArrayList<Spin> result, String errorCode, String description) {
//            if (lntxll != null) {
//                lntxll.setVisibility(View.GONE);
//            }
//            lstSpin = new ArrayList<>();
//            if (!CommonActivity.isNullOrEmptyArray(result)) {
//                lntxll.setVisibility(View.VISIBLE);
//                Spin spinSelect = null;
//                if (!CommonActivity.isNullOrEmpty(subscriberDTO) && subscriberDTO.isNormalIsdn()) {
//                    for (Spin spin : result) {
//                        if (spin.getValue().equals("NORMAL_ISDN_PASS")) {
//                            spinSelect = spin;
//                            break;
//                        }
//                    }
//                }
//                if (!CommonActivity.isNullOrEmpty(subscriberDTO) && !subscriberDTO.isNormalIsdn()) {
//                    for (Spin spin : result) {
//                        if (spin.getValue().equals("NICE_ISDN_PASS")) {
//                            spinSelect = spin;
//                            break;
//                        }
//                    }
//                }
//                if (!CommonActivity.isNullOrEmpty(spinSelect) && !CommonActivity.isNullOrEmpty(spinSelect.getId())) {
//                    int numberIsdn = Integer.parseInt(spinSelect.getId());
//                    for (int i = 0; i < numberIsdn; i++) {
//                        lstSpin.add(new Spin(i + ""));
//                    }
//                    isdnAutoAdapter = new IsdnAutoAdapter(getActivity(), lstSpin);
//                    lvIsdn.setAdapter(isdnAutoAdapter);
//                }
//
//
//            } else {
//                lstSpin = new ArrayList<>();
//                if (isdnAutoAdapter != null) {
//                    isdnAutoAdapter.notifyDataSetChanged();
//                }
//            }
//        }
//    }
}
