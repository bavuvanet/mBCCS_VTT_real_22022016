package com.viettel.bss.viettelpos.v4.omichanel.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.adapter.ListViewMenuAdapter;
import com.viettel.bss.viettelpos.v4.channel.activity.StaffInfoActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.guide.fragment.GuideConnectFragment;
import com.viettel.bss.viettelpos.v4.listener.OnItemClickListener;
import com.viettel.bss.viettelpos.v4.login.object.Manager;
import com.viettel.bss.viettelpos.v4.sale.business.StaffBusiness;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;
import com.viettel.bss.viettelpos.v4.task.AsynTaskGetStaffSignatureFile;
import com.viettel.bss.viettelpos.v4.video.FragmentPlayvideo;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Toancx on 1/12/2017.
 */

public class OmniFragment extends FragmentCommon {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private Staff staff;
    private String staffSigExists;
    private ListViewMenuAdapter menuAdapter;

    private final String ID_SEARCH_ORDER = "ID_SEARCH_ORDER";
    private final String ID_CLAIM_ORDER = "ID_CLAIM_ORDER";

    public static OmniFragment newInstance() {
        return new OmniFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idLayout = R.layout.layout_recycler_view;
    }

    @Override
    public void unit(View v) {

        this.staff = StaffBusiness.getStaffByStaffCode(getContext(), Session.userName);
        menuAdapter = new ListViewMenuAdapter(getActivity(), getManagerList(), onItemClickListener);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(menuAdapter);
    }

    private final OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(Object item) {
            Manager manager = (Manager) item;
            String keyMenuName = manager.getKeyMenuName();
            if (CommonActivity.isNullOrEmpty(keyMenuName)) {
                return;
            }

            CommonActivity.addMenuActionToDatabase(getContext(),
                    manager.getNameManager(), keyMenuName,
                    keyMenuName, manager.getResIcon());

            switch (keyMenuName) {
                case ID_SEARCH_ORDER:
                    if (!CommonActivity.isNullOrEmpty(staff)) {
                        checkSignatureStaff();
                    } else {
                        CommonActivity.createAlertDialog(getActivity(),
                                R.string.data_syn_require, R.string.app_name).show();
                    }
                    break;
                case ID_CLAIM_ORDER:
                    ReplaceFragment.replaceFragment(getActivity(),
                            new SearchOrderToClaimFragment(), true);
                    break;
                default:
                    break;
            }
        }
    };

    private void checkSignatureStaff() {
        if (!CommonActivity.isNullOrEmpty(getSignatureStaff())) {
            this.staffSigExists = "1";
            saveSignatureExists(staffSigExists);
        } else {
            this.staffSigExists = getSignatureExists();
        }

        if (CommonActivity.isNullOrEmpty(this.staffSigExists)) {
            new AsynTaskGetStaffSignatureFile(getActivity(), new OnPostExecuteListener<String>() {
                @Override
                public void onPostExecute(String result, String errorCode, String description) {
                    if("1".equals(result)){
                        staffSigExists = result;
                        saveSignatureExists(result);
                        showSearchOmniScreen();
                    } else {
                        doCreateSigStaff();
                    }
                }
            }, moveLogInAct, AsynTaskGetStaffSignatureFile.TYPE_CHECK_SIG).execute();
        } else {
            showSearchOmniScreen();
        }
    }

    private void showSearchOmniScreen() {
        if ("1".equals(this.staffSigExists)) {
            ReplaceFragment.replaceFragment(getActivity(),
                    new SearchOrderOmniFragment(), true);
        }
    }

    private void doCreateSigStaff() {
        final View.OnClickListener okListener = new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getActivity(), StaffInfoActivity.class);
                intent.putExtra("type", "order");
                startActivityForResult(intent, 191);
            }
        };

        CommonActivity.createDialog(getActivity(),
                getString(R.string.staff_sig_not_exits),
                getString(R.string.app_name), "",
                getString(R.string.ok),
                null, okListener).show();
    }

    private String getSignatureStaff() {
        SharedPreferences sharedPreferences = act.getSharedPreferences(
                Constant.SHARE_PREFERENCES_FILE, act.MODE_PRIVATE);
        return sharedPreferences.getString(Constant.SIGNATURE_STAFF_SAVE, "");
    }

    private String getSignatureExists() {
        SharedPreferences sharedPreferences = act.getSharedPreferences(
                Constant.SHARE_PREFERENCES_FILE, act.MODE_PRIVATE);
        return sharedPreferences.getString(Constant.SIGNATURE_STAFF_EXISTS, "");
    }

    private void saveSignatureExists(String existsSig) {
        SharedPreferences sharedPreferences = act.getSharedPreferences(
                Constant.SHARE_PREFERENCES_FILE, act.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(Constant.SIGNATURE_STAFF_EXISTS, existsSig);
        edit.commit();
    }

    @Override
    public void setPermission() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 191) {
            checkSignatureStaff();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("InventoryFragment", "onOptionsItemSelected");
        return super.onOptionsItemSelected(item);
    }

    private ArrayList<Manager> getManagerList() {
        SharedPreferences preferences = getActivity().getSharedPreferences(Define.PRE_NAME, Activity.MODE_PRIVATE);
        String name = preferences.getString(Define.KEY_MENU_NAME, "");

        ArrayList<Manager> arrayListManager = new ArrayList<>();

        //if (name.contains(";cm.connect_sub_CD;")) {
            arrayListManager.add(new Manager(R.drawable.ic_research_order,
                    getResources().getString(R.string.order_search), 0,
                    ID_SEARCH_ORDER));
        //}

        //if (name.contains(";cm.connect_sub_CD;")) {
            arrayListManager.add(new Manager(R.drawable.ic_reclaim_order,
                    getResources().getString(R.string.order_receiption_claim), 0,
                    ID_CLAIM_ORDER));
        //}

        return arrayListManager;
    }
}
