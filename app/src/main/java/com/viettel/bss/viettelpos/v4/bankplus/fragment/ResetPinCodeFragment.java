package com.viettel.bss.viettelpos.v4.bankplus.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.advisory.adapter.AdvisoryHistoryAdapter;
import com.viettel.bss.viettelpos.v4.bankplus.adapter.StaffResetPinAdapter;
import com.viettel.bss.viettelpos.v4.bankplus.asynctask.CreateBankPlusAsyncTask;
import com.viettel.bss.viettelpos.v4.bankplus.object.BankPlusOutput;
import com.viettel.bss.viettelpos.v4.commons.BPConstant;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.sale.business.StaffBusiness;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;

/**
 * Created by toancx on 8/11/2017.
 */

public class ResetPinCodeFragment extends FragmentCommon
        implements SearchView.OnQueryTextListener {

    @BindView(R.id.lvKpp)
    ListView lvKpp;
    @BindView(R.id.searchView)
    SearchView searchView;

    private ArrayList<Staff> staffs;
    private StaffResetPinAdapter staffResetPinAdapter;

    public ResetPinCodeFragment(ArrayList<Staff> staffs) {
        super();
        this.idLayout = R.layout.bankplus_reset_pin_code_fragment;
        this.staffs = staffs;
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleActionBar(R.string.bankplus_reset_pin);
    }

    @Override
    protected void unit(View v) {

        this.staffResetPinAdapter = new StaffResetPinAdapter(staffs, getActivity());
        lvKpp.setAdapter(staffResetPinAdapter);

        lvKpp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Staff staff = staffs.get(position);
                doResetPinCode(staff.getStaffCode());
            }
        });

        searchView.setOnQueryTextListener(this);
    }

    private void doResetPinCode(final String staffCode) {

        final View.OnClickListener resetPinListener = new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                doRequestResetPinCode(staffCode);
            }
        };

        CommonActivity.createDialog(getActivity(),
                "Bạn có đồng ý reset mã pin cho " + staffCode + " không?",
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

    @Override
    protected void setPermission() {

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        String text = s.toLowerCase(Locale.getDefault());
        staffResetPinAdapter.filter(text);
        return false;
    }
}
