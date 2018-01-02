package com.viettel.bss.viettelpos.v4.omichanel.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.omichanel.adapter.ProductBundleAdapter;
import com.viettel.bss.viettelpos.v4.omichanel.asynctask.WsProductCatalogAsyncTask;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ConnectionOrder;
import com.viettel.bss.viettelpos.v4.omichanel.dao.PoCatalogOutsideDTO;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hantt47 on 9/5/2017.
 */

public class EditBundleFragment extends FragmentCommon {

    @BindView(R.id.llFilter)
    LinearLayout llFilter;
    @BindView(R.id.spnObject)
    Spinner spnObject;
    @BindView(R.id.recProductView)
    RecyclerView recProductView;

    private String functionName;
    private String code;
    private String value;
    private String operator;

    private ConnectionOrder connectionOrder;
    private String bundleCodeOld;
    private String buttonName;

    private ArrayList<PoCatalogOutsideDTO> poCatalogOutsideDTOs;
    private ProductBundleAdapter productBundleAdapter;
    private boolean isMoreThan30Days;

    public EditBundleFragment(ConnectionOrder connectionOrder) {
        super();

        this.connectionOrder = connectionOrder;
        this.isMoreThan30Days = false;

        if (connectionOrder.getOrderType().equals(Constant.ORD_TYPE_CONNECT_PREPAID)
                || connectionOrder.getOrderType().equals(Constant.ORD_TYPE_REGISTER_PREPAID)) {
            functionName = "getLstBundle";
            code = "type";
            value = "0";
            operator = null;
        } else {
            functionName = "getLstBundleMobilePos";
            code = "type";
            operator = "5";

            if (connectionOrder.getOrderType().equals(Constant.ORD_TYPE_CONNECT_POSPAID)) {
                value = "0";
            } else if (isMoreThan30Days) {
                value = "1";
            } else {
                value = "2";
            }
        }

        poCatalogOutsideDTOs = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.idLayout = R.layout.layout_omnichannel_choose_product;
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleActionBar(getString(R.string.choose_product));
    }

    @Override
    protected void unit(View v) {

        poCatalogOutsideDTOs = new ArrayList<>();
        productBundleAdapter = new ProductBundleAdapter(
                getActivity(), this.buttonName, this, poCatalogOutsideDTOs);
        recProductView.setAdapter(productBundleAdapter);

        if (!CommonActivity.isNullOrEmpty(connectionOrder.getProductInfo())
                && !CommonActivity.isNullOrEmpty(connectionOrder.getProductInfo().getBundleCode())) {
            this.bundleCodeOld = connectionOrder.getProductInfo().getBundleCode();
            this.buttonName = getString(R.string.change_packet);
        } else {
            this.bundleCodeOld = "";
            this.buttonName = getString(R.string.choose_packet);
        }

        recProductView.setHasFixedSize(true);
        recProductView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (connectionOrder.getOrderType().equals(Constant.ORD_TYPE_CONNECT_PREPAID)
                || connectionOrder.getOrderType().equals(Constant.ORD_TYPE_REGISTER_PREPAID)) {
            ArrayList<Spin> lstSpinObject = new ArrayList<>();
            lstSpinObject.add(new Spin("0", getString(R.string.product_normal)));
            lstSpinObject.add(new Spin("1", getString(R.string.product_hssv)));
            Utils.setDataSpinner(getActivity(), lstSpinObject, spnObject);

            spnObject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    Spin spinObject = (Spin) arg0.getItemAtPosition(arg2);
                    value = spinObject.getId();
                    searchProducts();
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });
        } else {
            llFilter.setVisibility(View.GONE);
            searchProducts();
        }
    }

    public void doSaveProductBundle(PoCatalogOutsideDTO poCatalogOutsideDTO) {
        Intent intent = new Intent(getContext(), DetailOrderOmniFragment.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("poCatalogOutsideDTO", poCatalogOutsideDTO);
        intent.putExtras(bundle);
        getTargetFragment().onActivityResult(getTargetRequestCode(),
                DetailOrderOmniFragment.RESULT_OK, intent);
        getFragmentManager().popBackStack();
    }

    @Override
    protected void setPermission() {

    }

    private void searchProducts() {
        // reset list
        poCatalogOutsideDTOs = new ArrayList<>();
        productBundleAdapter = new ProductBundleAdapter(
                getActivity(), this.buttonName, this, poCatalogOutsideDTOs);
        recProductView.setAdapter(productBundleAdapter);

        WsProductCatalogAsyncTask wsProductCatalogAsyncTask = new WsProductCatalogAsyncTask(
                getActivity(),
                new OnPostExecuteListener<List<PoCatalogOutsideDTO>>() {
                    @Override
                    public void onPostExecute(List<PoCatalogOutsideDTO> result,
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
        wsProductCatalogAsyncTask.execute(functionName, code, value, operator);
    }

    private void updateUI(List<PoCatalogOutsideDTO> lstDTOs) {
        PoCatalogOutsideDTO poCatalogOutsideDTO;
        for (int index = lstDTOs.size() - 1; index >= 0; index--) {
            poCatalogOutsideDTO = lstDTOs.get(index);
            if (bundleCodeOld.equals(poCatalogOutsideDTO.getCode())) {
                poCatalogOutsideDTO.setSelected(true);
            } else {
                poCatalogOutsideDTO.setSelected(false);
            }

            // loc bo goi theo loai yeu cau
            if (Constant.ORD_TYPE_REGISTER_PREPAID.equals(connectionOrder.getOrderType())
                    && "2".equals(poCatalogOutsideDTO.getBundleType())) {
                lstDTOs.remove(poCatalogOutsideDTO);
            }

            // loc bo goi theo loai yeu cau
            if (Constant.ORD_TYPE_CONNECT_PREPAID.equals(connectionOrder.getOrderType())
                    && "1".equals(poCatalogOutsideDTO.getBundleType())) {
                lstDTOs.remove(poCatalogOutsideDTO);
            }
        }

        poCatalogOutsideDTOs = new ArrayList<>(lstDTOs);
        productBundleAdapter = new ProductBundleAdapter(
                getActivity(), this.buttonName, this, poCatalogOutsideDTOs);
        recProductView.setAdapter(productBundleAdapter);
    }
}
