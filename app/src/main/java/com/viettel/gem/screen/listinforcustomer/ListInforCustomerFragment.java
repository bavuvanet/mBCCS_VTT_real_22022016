package com.viettel.gem.screen.listinforcustomer;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.gem.base.viper.ViewFragment;
import com.viettel.gem.utils.RecyclerUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The ListInforCustomer Fragment
 */
public class ListInforCustomerFragment extends ViewFragment<ListInforCustomerContract.Presenter>
        implements ListInforCustomerContract.View {

    @BindView(R.id.listInforCustomer)
    RecyclerView listInforCustomer;

    public static ListInforCustomerFragment getInstance() {
        return new ListInforCustomerFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list_infor_customer;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        RecyclerUtils.setupVerticalRecyclerView(getContext(), listInforCustomer);
    }

    @OnClick(R.id.back_iv)
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                mPresenter.back();
                break;
            default:
                break;
        }
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        listInforCustomer.setAdapter(adapter);
    }
}
