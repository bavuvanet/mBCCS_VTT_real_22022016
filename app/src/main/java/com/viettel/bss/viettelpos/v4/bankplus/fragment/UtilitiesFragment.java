package com.viettel.bss.viettelpos.v4.bankplus.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.bankplus.adapter.MenuAdapter;
import com.viettel.bss.viettelpos.v4.bankplus.asynctask.CreateBankPlusAsyncTask;
import com.viettel.bss.viettelpos.v4.bankplus.object.BankPlusOutput;
import com.viettel.bss.viettelpos.v4.commons.BPConstant;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.login.object.Manager;
import com.viettel.bss.viettelpos.v4.sale.business.StaffBusiness;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by hantt47 on 7/25/2017.
 */

public class UtilitiesFragment extends FragmentCommon {

    @BindView(R.id.listView)
    ListView listView;

    private ArrayList<Manager> arrayListManager;
    private MenuAdapter mAdapterManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.idLayout = R.layout.layout_listview;
    }

    @Override
    public void onResume() {
        MainActivity.getInstance().setTitleActionBar(R.string.bankplus_menu_utilities);
        super.onResume();
    }

    @Override
    protected void unit(View v) {
        arrayListManager = new ArrayList<Manager>();
        addManagerList();
    }

    @Override
    protected void setPermission() {

    }

    private void addManagerList() {
        arrayListManager.clear();

        SharedPreferences preferences = getActivity().getSharedPreferences(
                Define.PRE_NAME, Activity.MODE_PRIVATE);

        String name = preferences.getString(Define.KEY_MENU_NAME, "");
//        if (name.contains(";m.transfer.money;")) {
            arrayListManager.add(new Manager(R.drawable.ic_tracuu_giaodich_bankplus,
                    getResources().getString(R.string.bankplus_reset_pin), 0,
                    Constant.BANKPLUS_FUNCTION.BANKPLUS_RESET_PIN));
        //}
        //        if (name.contains(";m.transfer.money;")) {
            arrayListManager.add(new Manager(R.drawable.ic_tracuu_giaodich_bankplus,
                    getResources().getString(R.string.bankplus_change_pin), 0,
                    Constant.BANKPLUS_FUNCTION.BANKPLUS_CHANGE_PIN));
        //}
//        if (name.contains(";m.deliver.money;")) {
            arrayListManager.add(new Manager(R.drawable.ic_tracuu_giaodich_bankplus,
                    getResources().getString(
                            R.string.bankplus_resend_id_card), 0,
                    Constant.BANKPLUS_FUNCTION.BANKPLUS_RESEND_ID_CARD));
//        }

        arrayListManager.add(new Manager(R.drawable.ic_tracuu_giaodich_bankplus,
                getResources().getString(R.string.view_tran_bank_history), 0,
                Constant.BANKPLUS_FUNCTION.BANKPLUS_VIEW_HIS_TRANS));

        mAdapterManager = new MenuAdapter(arrayListManager, getActivity());
        listView.setAdapter(mAdapterManager);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

        String menuName = arrayListManager.get(arg2).getKeyMenuName();
        MainActivity.mLevelMenu = 1;

        if (menuName.equals(Constant.BANKPLUS_FUNCTION.BANKPLUS_RESET_PIN)) {

            String staffMngtCode = Session.userName;
            ArrayList<Staff> staffs = StaffBusiness.getLstStaffByStaffMngt(getContext(), staffMngtCode);

            if (staffs.size() > 1) {
                ReplaceFragment.replaceFragment(getActivity(),
                        new ResetPinCodeFragment(staffs), false);
            } else {
                doResetPinCode(staffMngtCode);
            }
            return;
        }

        if (menuName.equals(Constant.BANKPLUS_FUNCTION.BANKPLUS_CHANGE_PIN)) {
            ReplaceFragment.replaceFragment(getActivity(),
                    new ChangePinCodeBankplusFragment(), false);
            return;
        }

        if (menuName.equals(Constant.BANKPLUS_FUNCTION.BANKPLUS_RESEND_ID_CARD)) {
            ReplaceFragment.replaceFragment(getActivity(),
                    new ResendCodeCardFragment(), false);
            return;
        }

        if (menuName.equals(Constant.BANKPLUS_FUNCTION.BANKPLUS_VIEW_HIS_TRANS)) {
            ReplaceFragment.replaceFragment(getActivity(),
                    new ViewHisTransFragment(), false);
            return;
        }
    }

    private void doResetPinCode(final String staffCode) {

        final View.OnClickListener resetPinListener = new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                doRequestResetPinCode(staffCode);
            }
        };

        CommonActivity.createDialog(getActivity(),
                getString(R.string.bankplus_msg_reset_pin_confirm),
                getString(R.string.app_name),
                getString(R.string.cancel),
                getString(R.string.ok),
                null, resetPinListener).show();
    }

    private void doRequestResetPinCode(String staffCode) {
        String token = Session.getToken();
        StringBuilder data = new StringBuilder();
        data.append(token).append(Constant.STANDARD_CONNECT_CHAR);
        data.append(BPConstant.COMMAND_RESET_PIN_CODE).append(Constant.STANDARD_CONNECT_CHAR);
        data.append(staffCode).append(Constant.STANDARD_CONNECT_CHAR);
        data.append("123456").append(Constant.STANDARD_CONNECT_CHAR);

        new CreateBankPlusAsyncTask(data.toString(), getActivity(),
                new OnPostExecuteListener<BankPlusOutput>() {
                    @Override
                    public void onPostExecute(BankPlusOutput result, String errorCode, String description) {
                        // TODO Auto-generated method stub
                        if ("0".equals(errorCode)) {
                            CommonActivity.createAlertDialog(getActivity(),
                                    getString(R.string.bankplus_msg_reset_pin_success),
                                    getString(R.string.app_name)).show();
                        } else {
                            Toast.makeText(getActivity(), description, Toast.LENGTH_LONG).show();
                        }
                    }
                }, moveLogInAct).execute();
    }
}