package com.viettel.bss.viettelpos.v4.advisory.screen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.advisory.adapter.ProductAdapter;
import com.viettel.bss.viettelpos.v4.advisory.data.ProductBean;
import com.viettel.bss.viettelpos.v4.cc.object.CCOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

/**
 * Created by hantt47 on 7/19/2017.
 */

public class FullProductFragment extends FragmentCommon
        implements SearchView.OnQueryTextListener {

    @BindView(R.id.lvProduct)
    ListView lvProduct;
    @BindView(R.id.searchView)
    SearchView searchView;

    private List<ProductBean> productBeanList;
    private ProductAdapter productAdapter;
    private ArrayList<ProductBean> productBeens;
    private String productName;
    private String isdn;
    private String prepaid;


    public FullProductFragment(String productName, String isdn,
                               String prepaid, List<ProductBean> productBeanList) {
        super();
        this.productName = productName;
        this.productBeanList = productBeanList;
        this.isdn = isdn;
        this.prepaid = prepaid;
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleActionBar(productName);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.idLayout = R.layout.advisory_full_product_fragment;
    }

    @Override
    protected void unit(View v) {

        // list view history:
        productBeens = new ArrayList<>(productBeanList);
        productAdapter = new ProductAdapter(isdn, prepaid, productBeens, getActivity(), getContext());
        this.lvProduct.setAdapter(productAdapter);

        this.lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //...
            }
        });

        // Locate the EditText in listview_main.xml
        searchView.setOnQueryTextListener(this);
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
        productAdapter.filter(text);
        return false;
    }
}
