package com.viettel.bss.viettelpos.v4.advisory.screen;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.advisory.adapter.ExListViewProductAdapter;
import com.viettel.bss.viettelpos.v4.advisory.data.ProductBean;
import com.viettel.bss.viettelpos.v4.advisory.data.ProductGroupBean;
import com.viettel.bss.viettelpos.v4.cc.object.CCOutput;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.task.AsynTaskGetHistoryConsultTVBH;
import com.viettel.bss.viettelpos.v4.task.AsynTaskGetListOfferConsultTVBH;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hantt47 on 7/18/2017.
 */

public class AdvisoryPacketFragment extends FragmentCommon {

    @BindView(R.id.contentLayout)
    LinearLayout contentLayout;
    @BindView(R.id.expandListView)
    ExpandableListView expandListView;
    @BindView(R.id.tvNoData)
    TextView tvNoData;



    private ExListViewProductAdapter listAdapter;
    private List<String> headers;
    private HashMap<String, List<ProductBean>> listHashMapFull;
    private HashMap<String, List<ProductBean>> listHashMapThree;

    private CCOutput ccOutput;
    private String isdn;
    private String prepaid;
    private boolean isRequested;

    public AdvisoryPacketFragment(String isdn, String prepaid) {
        super();

        this.isdn = isdn;
        this.prepaid = prepaid;
        this.isRequested = false;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.idLayout = R.layout.advisory_packet_fragment;
    }

    @Override
    protected void unit(View v) {

    }

    public void requestData() {
        if (isRequested) {
            return;
        }
        new AsynTaskGetListOfferConsultTVBH(getActivity(), new OnPostExecuteListener<CCOutput>() {
            @Override
            public void onPostExecute(CCOutput result, String errorCode, String description) {
                if(!CommonActivity.isNullOrEmpty(result)){
                    ccOutput = result;
                    refreshUI();
                    isRequested = true;
                } else {
                    CommonActivity.toast(getActivity(), R.string.no_data);

                    tvNoData.setVisibility(View.GONE);
                    contentLayout.setVisibility(View.VISIBLE);
                    prepareListData();
                    isRequested = true;
                }
            }
        }, moveLogInAct).execute(isdn, prepaid);
    }

    private void refreshUI() {
        if (ccOutput.getLstProductGroupBeans() != null) {

            tvNoData.setVisibility(View.GONE);
            contentLayout.setVisibility(View.VISIBLE);

            headers = new ArrayList<String>();
            listHashMapFull = new HashMap<String, List<ProductBean>>();
            listHashMapThree = new HashMap<String, List<ProductBean>>();

            ArrayList<ProductGroupBean> productGroupBeens =
                    new ArrayList<>(ccOutput.getLstProductGroupBeans());
            ProductGroupBean productGroupBean;

            for (int index = 0; index < productGroupBeens.size(); index++) {
                productGroupBean = productGroupBeens.get(index);
                headers.add("" + productGroupBean.getType());

                listHashMapFull.put("" + productGroupBean.getType(),
                        productGroupBean.getLstProductBeans());
                listHashMapThree.put("" + productGroupBean.getType(),
                        getThreeList(productGroupBean.getLstProductBeans()));
            }

            listAdapter = new ExListViewProductAdapter(getContext(),
                    getActivity(), headers, listHashMapThree, listHashMapFull, isdn, prepaid);
            expandListView.setAdapter(listAdapter);

            for (int index = 0; index < headers.size(); index++) {
                expandListView.expandGroup(index);
            }
        } else {
            tvNoData.setVisibility(View.VISIBLE);
            contentLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void setPermission() {

    }

    private List<ProductBean> getThreeList(List<ProductBean> productBeanList) {
        if (productBeanList.size() < 4) {
            return productBeanList;
        }
        List<ProductBean> productBeanListTemp = new ArrayList<>();
        for (int index = 0; index < productBeanList.size(); index++) {
            if (index == 3) {
                break;
            }
            productBeanListTemp.add(productBeanList.get(index));
        }
        return productBeanListTemp;
    }

    private void prepareListData() {
        headers = new ArrayList<String>();
        listHashMapFull = new HashMap<String, List<ProductBean>>();
        listHashMapThree = new HashMap<String, List<ProductBean>>();

        // Adding child data
        headers.add("VTFree");
        headers.add("S70");

        // Adding child data
        List<ProductBean> nowShowing1 = new ArrayList<ProductBean>();
        nowShowing1.add(new ProductBean("MIMAX1", "VTFree", "Mo ta", "50000", "7000"));
        nowShowing1.add(new ProductBean("MIMAX2", "VTFree", "Mo ta", "50000", "7000"));
        nowShowing1.add(new ProductBean("MIMAX3", "VTFree", "Mo ta", "50000", "7000"));
        nowShowing1.add(new ProductBean("MIMAX4", "VTFree", "Mo ta", "50000", "7000"));
        nowShowing1.add(new ProductBean("MIMAX5", "VTFree", "Mo ta", "50000", "7000"));

        List<ProductBean> nowShowing2 = new ArrayList<ProductBean>();
        nowShowing2.add(new ProductBean("XXX1", "S70", "Mo ta", "50000", "7000"));
        nowShowing2.add(new ProductBean("XXX2", "S70", "Mo ta", "50000", "7000"));
        nowShowing2.add(new ProductBean("XXX3", "S70", "Mo ta", "50000", "7000"));
        nowShowing2.add(new ProductBean("XXX4", "S70", "Mo ta", "50000", "7000"));
        nowShowing2.add(new ProductBean("XXX5", "S70", "Mo ta", "50000", "7000"));


        listHashMapFull.put(headers.get(0), nowShowing1); // Header, Child data
        listHashMapFull.put(headers.get(1), nowShowing2);

        listHashMapThree.put(headers.get(0), getThreeList(nowShowing1)); // Header, Child data
        listHashMapThree.put(headers.get(1), getThreeList(nowShowing2));

        listAdapter = new ExListViewProductAdapter(getContext(),
                getActivity(), headers, listHashMapThree, listHashMapFull, isdn, prepaid);
        expandListView.setAdapter(listAdapter);

        for (int index = 0; index < headers.size(); index++) {
            expandListView.expandGroup(index);
        }
    }
}
