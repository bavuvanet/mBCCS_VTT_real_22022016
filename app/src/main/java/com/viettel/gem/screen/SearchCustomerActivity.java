package com.viettel.gem.screen;

import com.viettel.gem.base.ContainerActivity;
import com.viettel.gem.base.viper.ViewFragment;
import com.viettel.gem.screen.searchcustomer.SearchCustomerPresenter;

/**
 * Created by root on 21/11/2017.
 */

public class SearchCustomerActivity extends ContainerActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {
        return (ViewFragment) new SearchCustomerPresenter(this).getFragment();
    }
}
