package com.viettel.bss.viettelpos.v4.omichanel.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.omichanel.adapter.ProductVasPlusAdapter;
import com.viettel.bss.viettelpos.v4.omichanel.asynctask.WsProductCatalogAsyncTask;
import com.viettel.bss.viettelpos.v4.omichanel.dao.PoCatalogOutsideDTO;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ProductInfo;
import com.viettel.bss.viettelpos.v4.omichanel.dao.VasInfo;
import com.viettel.bss.viettelpos.v4.omichanel.dialog.VasPlusDeatailDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hantt47 on 9/5/2017.
 */

public class EditVasPlusFragment extends FragmentCommon {

    Activity activity;
    @BindView(R.id.btnSave)
    Button btnSave;
    @BindView(R.id.recListVas)
    RecyclerView recListVas;
    @BindView(R.id.tvCount)
    TextView tvCount;

    private String functionName;
    private String code;
    private String value;

    private ArrayList<VasInfo> vasInfos;
    private ArrayList<PoCatalogOutsideDTO> lstVasFull;
    ProductVasPlusAdapter productVasPlusAdapter;

    public EditVasPlusFragment(ProductInfo productInfo) {
        super();

        functionName = "getLstVas";
        code = "showOmni";
        value = "1";

        if (!CommonActivity.isNullOrEmpty(productInfo)
                && !CommonActivity.isNullOrEmpty(productInfo.getVasInfos())) {
            vasInfos = new ArrayList<>(productInfo.getVasInfos());
        } else {
            vasInfos = new ArrayList<>();
        }

        lstVasFull = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        idLayout = R.layout.edit_vas_plus_fragment;
        ButterKnife.bind(getActivity());
        activity = getActivity();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleActionBar(activity.getString(R.string.choose_service));
    }

    @Override
    protected void unit(View v) {

        recListVas.setHasFixedSize(true);
        recListVas.setLayoutManager(new LinearLayoutManager(getActivity()));

        searchService();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSaveVasPlus();
            }
        });
    }

    @Override
    protected void setPermission() {

    }

    private void searchService() {
        WsProductCatalogAsyncTask wsProductCatalogAsyncTask = new WsProductCatalogAsyncTask(
                getActivity(),
                new OnPostExecuteListener<List<PoCatalogOutsideDTO>>() {
                    @Override
                    public void onPostExecute(List<PoCatalogOutsideDTO> result,
                                              String errorCode, String description) {
                        if ("0".equals(errorCode) && result != null) {
                            lstVasFull = new ArrayList<>(result);
                            updateUI();
                        } else {
                            CommonActivity.createAlertDialog(getActivity(),
                                    description,
                                    getString(R.string.app_name)).show();
                        }
                    }
                }, moveLogInAct);
        wsProductCatalogAsyncTask.execute(functionName, code, value, "");
    }

    private void updateUI() {
        // check vas selected
        for (PoCatalogOutsideDTO poCatalogOutsideDTO : lstVasFull) {
            poCatalogOutsideDTO.setSelected(false);
            for (VasInfo vasInfo : vasInfos) {
                if (vasInfo.getVasCode().equals(poCatalogOutsideDTO.getVasCode())) {
                    poCatalogOutsideDTO.setSelected(true);
                }
            }
        }
        productVasPlusAdapter = new ProductVasPlusAdapter(getActivity(), this, lstVasFull);
        recListVas.setAdapter(productVasPlusAdapter);

        tvCount.setText(getTextNumberVasSelected());
    }

    private void doSaveVasPlus() {
        Intent intent = new Intent(getContext(), EditVasPlusFragment.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("vasInfos", getListVasSelected());
        intent.putExtras(bundle);
        getTargetFragment().onActivityResult(getTargetRequestCode(),
                DetailOrderOmniFragment.RESULT_OK, intent);
        getFragmentManager().popBackStack();
    }

    public void showDetailDialog(PoCatalogOutsideDTO poCatalogOutsideDTO) {
        VasPlusDeatailDialog vasPlusDeatailDialog =
                new VasPlusDeatailDialog(getContext(), poCatalogOutsideDTO);
        vasPlusDeatailDialog.show();
    }

    public void updateVas(int position) {
        boolean stateNew = !lstVasFull.get(position).isSelected();
        lstVasFull.get(position).setSelected(stateNew);
        productVasPlusAdapter.notifyDataSetChanged();
        if (stateNew) {
            setDisableGroup(lstVasFull.get(position));
        }
        tvCount.setText(getTextNumberVasSelected());
    }

    private void setDisableGroup(PoCatalogOutsideDTO poCatalogOutsideDTO) {
        for (PoCatalogOutsideDTO poItem : lstVasFull) {
            if (!poCatalogOutsideDTO.getCode().equals(poItem.getCode())
                    && poCatalogOutsideDTO.getGroup().equals(poItem.getGroup())) {
                poItem.setSelected(false);
            }
        }
    }

    private String getTextNumberVasSelected() {
        int count = 0;
        StringBuilder vasString = new StringBuilder("");
        for (PoCatalogOutsideDTO poCatalogOutsideDTO : lstVasFull) {
            if (poCatalogOutsideDTO.isSelected()) {
                vasString.append(poCatalogOutsideDTO.getVasCode()).append(", ");
                count++;
            }
        }
        String resultString = "";
        if (count > 0) {
            String vasStringNew = vasString.toString();
            vasStringNew = vasStringNew.substring(0, vasStringNew.length() - 2);
            resultString = getString(R.string.order_vas_plus_count_selected,
                    "" + count) + ": " + vasStringNew;
        } else {
            resultString = getString(R.string.order_vas_plus_count_selected, "" + count);
        }
        return  resultString;
    }

    private ArrayList<VasInfo> getListVasSelected() {
        ArrayList<VasInfo> vasInfosSelected = new ArrayList<>();
        for (PoCatalogOutsideDTO poCatalogOutsideDTO : lstVasFull) {
            if (poCatalogOutsideDTO.isSelected()) {
                vasInfosSelected.add(new VasInfo(poCatalogOutsideDTO));
            }
        }
        return vasInfosSelected;
    }
}
