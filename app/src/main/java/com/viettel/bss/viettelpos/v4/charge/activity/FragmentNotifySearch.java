package com.viettel.bss.viettelpos.v4.charge.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.asynctask.AsyncDownloadFile;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by thinhhq1 on 6/16/2017.
 */
public class FragmentNotifySearch extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private EditText txtIdAccount;
    private EditText txtbankContract;
    private Spinner spCycle;
    private Button btPrintNotify;
    //    private Activity activity;
    private View mView;
    private String cycle = getCycle().get(0);

    @Override
    public void onResume() {
        super.onResume();
        setTitleActionBar(R.string.search_charge_notify);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {

            mView = inflater.inflate(R.layout.layout_search_charge_notify, container,
                    false);
            txtIdAccount = (EditText) mView.findViewById(R.id.txt_id_Account);
            txtbankContract = (EditText) mView.findViewById(R.id.txt_bank_contract_no);
            spCycle = (Spinner) mView.findViewById(R.id.sp_bill_cycle);
            btPrintNotify = (Button) mView.findViewById(R.id.btnPrint);
            btPrintNotify.setOnClickListener(this);
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_list_item_1, getCycle());
            spCycle.setAdapter(dataAdapter);
            spCycle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int pos, long id) {
                    cycle = (String) parent.getItemAtPosition(pos);
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });
        }
        return mView;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnPrint:
                if (CommonActivity.isNetworkConnected(getActivity())) {
                    printNotify();
                } else {
                    CommonActivity.createAlertDialog(getActivity(),
                            getString(R.string.errorNetwork),
                            getString(R.string.app_name)).show();
                }
                break;
            case R.id.relaBackHome:
                getActivity().onBackPressed();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        cycle = (String) parent.getItemAtPosition(position);
    }

    private void printNotify() {
        String bankContract = txtbankContract.getText().toString().trim();
        String isdn = txtIdAccount.getText().toString().trim();
        String description = "";

        if (CommonActivity.isNullOrEmpty(bankContract) && CommonActivity.isNullOrEmpty(isdn)) {
            description = getString(R.string.input_isdn_or_contract_no);
        } else {
            if (!CommonActivity.isNullOrEmpty(isdn) && Pattern.matches("[0-9]+", isdn)) {
                isdn = Long.valueOf(isdn) + "";
            }
            AsyncDownloadFile asyncDownloadFile = new AsyncDownloadFile(getActivity(), bankContract, isdn, cycle);
            asyncDownloadFile.execute("");
        }
        if (!CommonActivity.isNullOrEmpty(description)) {
            Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                    getResources().getString(R.string.app_name));
            dialog.show();
        }
    }

    private List<String> getCycle() {
        List<String> lsCycle = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH, -1);
            lsCycle.add(DateTimeUtils.convertDateTimeToString(calendar.getTime(), "MM/yyyy"));
        }
        return lsCycle;
    }

    protected void setTitleActionBar(int title) {
        MainActivity.getInstance().setTitleActionBar(title);
    }
}
