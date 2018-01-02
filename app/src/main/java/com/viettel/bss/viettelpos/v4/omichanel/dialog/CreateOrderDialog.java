package com.viettel.bss.viettelpos.v4.omichanel.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity.FragmentSearchSubChangeSim;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customer.fragment.CustomerManagerFragment;
import com.viettel.bss.viettelpos.v4.customer.manage.RegisterInfoFragment;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.omichanel.asynctask.PlaceOrderAsyncTask;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ConnectionOrder;
import com.viettel.bss.viettelpos.v4.omichanel.dao.OrderState;
import com.viettel.bss.viettelpos.v4.omichanel.fragment.DetailOrderOmniFragment;
import com.viettel.bss.viettelpos.v4.omichanel.fragment.SearchOrderOmniFragment;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentManageSubscriber;
import com.viettel.bss.viettelpos.v4.sale.business.StaffBusiness;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;
import com.viettel.bss.viettelpos.v4.sale.strategy.ChangePromotionFragmentStrategy;
import com.viettel.bss.viettelpos.v4.sale.strategy.ManageSubscriberFragmentStrategy;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

import java.util.ArrayList;

/**
 * Created by toancx on 9/26/2017.
 */

public class CreateOrderDialog extends Dialog {

    private Spinner spnOrderTypeNewRequest;
    private EditText edtNameCustomerNewRequest;
    private Button btnCreateNewRequest;
    private Button btnCancelNewRequest;

    private Activity context;
    private SearchOrderOmniFragment searchOrderOmniFragment;
    private String nameCusCreateNew;
    private String orderTypeCreateNew;

    public CreateOrderDialog(Activity context, SearchOrderOmniFragment searchOrderOmniFragment) {
        super(context);

        this.context = context;
        this.searchOrderOmniFragment = searchOrderOmniFragment;
        nameCusCreateNew = "";
        orderTypeCreateNew = "";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setCancelable(true);
        setContentView(R.layout.omni_layout_create_new);

        spnOrderTypeNewRequest = (Spinner) findViewById(R.id.spnOrderTypeNewRequest);
        edtNameCustomerNewRequest = (EditText) findViewById(R.id.edtNameCustomerNewRequest);
        btnCreateNewRequest = (Button) findViewById(R.id.btnCreateNewRequest);
        btnCancelNewRequest = (Button) findViewById(R.id.btnCancelNewRequest);

        // init spinner
        ArrayList<Spin> lstSpinOrderType = new ArrayList<>();
        lstSpinOrderType.add(new Spin("",
                context.getString(R.string.order_choose_order_type)));
        lstSpinOrderType.add(new Spin(Constant.ORD_TYPE_CONNECT_PREPAID,
                context.getString(R.string.order_type_connect_prepaid)));
        lstSpinOrderType.add(new Spin(Constant.ORD_TYPE_CONNECT_POSPAID,
                context.getString(R.string.order_type_connect_pospaid)));
        lstSpinOrderType.add(new Spin(Constant.ORD_TYPE_REGISTER_PREPAID,
                context.getString(R.string.order_type_register_prepaid)));
        lstSpinOrderType.add(new Spin(Constant.ORD_TYPE_CHANGE_TO_POSPAID,
                context.getString(R.string.order_type_change_to_pospaid)));
        lstSpinOrderType.add(new Spin(Constant.ORD_TYPE_CHANGE_PREPAID_FEE,
                context.getString(R.string.change_pre_charge_tittle)));

        Utils.setDataSpinner(context, lstSpinOrderType, spnOrderTypeNewRequest);

        spnOrderTypeNewRequest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Spin spin = (Spin) arg0.getItemAtPosition(arg2);
                orderTypeCreateNew = spin.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        btnCancelNewRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnCreateNewRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CommonActivity.isNullOrEmpty(orderTypeCreateNew)){
                    CommonActivity.createAlertDialog(context,
                            context.getString(R.string.type_request_null),
                            context.getString(R.string.app_name)).show();
                    return;
                }

                if(CommonActivity.isNullOrEmpty(edtNameCustomerNewRequest.getText().toString())){
                    CommonActivity.createAlertDialog(context,
                            context.getString(R.string.name_cus_null),
                            context.getString(R.string.app_name)).show();
                    return;
                }

                nameCusCreateNew = CommonActivity.getNormalText(edtNameCustomerNewRequest.getText().toString());

                PlaceOrderAsyncTask placeOrderAsyncTask =
                        new PlaceOrderAsyncTask(context, new OnPostExecuteListener<String>() {
                    @Override
                    public void onPostExecute(String result, String errorCode, String description) {
                        if("0".equals(errorCode)){
                            dismiss();
                            confirmContinueProcessOrder(result);
                        }else{
                            CommonActivity.createAlertDialog(context, description,
                                    context.getString(R.string.app_name)).show();
                        }
                    }
                }, null);
                placeOrderAsyncTask.execute(nameCusCreateNew, orderTypeCreateNew);
            }
        });
    }

    private void confirmContinueProcessOrder(final String processId) {

        final View.OnClickListener okListener = new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                doContinueProcessOrder(processId);
            }
        };

        final View.OnClickListener cancelListener = new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // do nothing
            }
        };

        CommonActivity.createDialog(context,
                context.getString(R.string.confirm_new_order_process_continue, processId),
                context.getString(R.string.app_name),
                context.getString(R.string.cancel),
                context.getString(R.string.ok),
                cancelListener, okListener).show();
    }

    private void doContinueProcessOrder(String processId) {
        Bundle bundle = new Bundle();
        switch (orderTypeCreateNew) {
            case Constant.ORD_TYPE_CONNECT_PREPAID:
            case Constant.ORD_TYPE_CONNECT_POSPAID:
                Staff staff = StaffBusiness.getStaffByStaffCode(context, Session.userName);
                DetailOrderOmniFragment fragment = new DetailOrderOmniFragment();
                ConnectionOrder connectionOrder = new ConnectionOrder(
                        orderTypeCreateNew, nameCusCreateNew, processId, staff.getShopId());
                bundle.putInt("orderActionState", OrderState.ORD_CLAIM_RECEIP_ACT);
                bundle.putSerializable("connectionOrder", connectionOrder);
                fragment.setArguments(bundle);
                ReplaceFragment.replaceFragment(context, fragment, false);
                break;
            case Constant.ORD_TYPE_REGISTER_PREPAID:
                RegisterInfoFragment registerInfoFragment = new RegisterInfoFragment();
                bundle.putString("omniProcessId", processId);
                registerInfoFragment.setArguments(bundle);
                ReplaceFragment.replaceFragment(context, registerInfoFragment, false);
                break;
            case Constant.ORD_TYPE_CHANGE_TO_POSPAID:
                FragmentSearchSubChangeSim fragmentSearchSubPos = new FragmentSearchSubChangeSim();
                bundle.putString("functionKey", Constant.CHANGE_PRE_TO_POS);
                bundle.putString("omniProcessId", processId);
                fragmentSearchSubPos.setArguments(bundle);
                ReplaceFragment.replaceFragment(context, fragmentSearchSubPos, true);
                break;
            case Constant.ORD_TYPE_CHANGE_PREPAID_FEE:
                ManageSubscriberFragmentStrategy manageSubscriberFragmentStrategy =
                        new ChangePromotionFragmentStrategy();
                bundle.putSerializable("fragmentStrategy", manageSubscriberFragmentStrategy);
                bundle.putString("omniProcessId", processId);
                FragmentManageSubscriber fragmentManageSubscriber = new FragmentManageSubscriber();
                fragmentManageSubscriber.setArguments(bundle);
                fragmentManageSubscriber.setTargetFragment(searchOrderOmniFragment, 100);
                ReplaceFragment.replaceFragment(context, fragmentManageSubscriber, false);
                break;
        }
    }
}
