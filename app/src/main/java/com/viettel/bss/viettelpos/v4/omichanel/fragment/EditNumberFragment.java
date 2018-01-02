package com.viettel.bss.viettelpos.v4.omichanel.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.omichanel.adapter.VStockNumberOmniDTOAdapter;
import com.viettel.bss.viettelpos.v4.omichanel.asynctask.SearchIsdnOmniAsyncTask;
import com.viettel.bss.viettelpos.v4.omichanel.dao.VStockNumberOmniDTO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hantt47 on 9/5/2017.
 */

public class EditNumberFragment extends FragmentCommon {

    @BindView(R.id.listNumber)
    RecyclerView listNumber;
    @BindView(R.id.editTypeSearch)
    EditText editTypeSearch;
    @BindView(R.id.btnSearch)
    Button btnSearch;

    private String isdn = "";
    private String telecomServiceId = "1";
    private String areaCode = "";
    private String startRow = "0";
    private String endRow = "20";

    private boolean isPreNumber;
    private VStockNumberOmniDTOAdapter vStockNumberOmniDTOAdapter;

    public EditNumberFragment(boolean isPreNumber) {
        super();

        this.isPreNumber = isPreNumber;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        idLayout = R.layout.layout_omnichannel_search_number;
        ButterKnife.bind(getActivity());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleActionBar(getString(R.string.search_number));
    }

    @Override
    protected void unit(View v) {

        listNumber.setHasFixedSize(true);
        listNumber.setLayoutManager(new LinearLayoutManager(getActivity()));
        search("*9*");

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search(editTypeSearch.getText().toString());
            }
        });
    }

    private void search(String isdnMask) {
        if (isdnMask.startsWith("0")) {
            isdnMask = isdnMask.substring(1);
        }
        isdn = isdnMask;
        SearchIsdnOmniAsyncTask searchIsdnOmniAsyncTask = new SearchIsdnOmniAsyncTask(getActivity(),
                new OnPostExecuteListener<List<VStockNumberOmniDTO>>() {
                    @Override
                    public void onPostExecute(List<VStockNumberOmniDTO> result,
                                              String errorCode, String description) {
                        if ("0".equals(errorCode) && result != null) {
                            updateUI(result);
                        } else {
                            CommonActivity.createAlertDialog(getActivity(),
                                    description,
                                    getString(R.string.app_name)).show();
                        }
                    }
                }, moveLogInAct);
        searchIsdnOmniAsyncTask.execute(isdn, telecomServiceId, areaCode, startRow, endRow);
    }

    private void updateUI(List<VStockNumberOmniDTO> vStockNumberOmniDTOs) {
        vStockNumberOmniDTOAdapter = new VStockNumberOmniDTOAdapter(
                isPreNumber, getActivity(), this, vStockNumberOmniDTOs);
        listNumber.setAdapter(vStockNumberOmniDTOAdapter);
    }

    @Override
    protected void setPermission() {

    }


    public void doSaveNumber(VStockNumberOmniDTO vStockNumberOmniDTO) {
        Intent intent = new Intent(getContext(), EditNumberFragment.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("vStockNumberOmniDTO", vStockNumberOmniDTO);
        intent.putExtras(bundle);
        getTargetFragment().onActivityResult(getTargetRequestCode(),
                DetailOrderOmniFragment.RESULT_OK, intent);
        getFragmentManager().popBackStack();
    }
}
