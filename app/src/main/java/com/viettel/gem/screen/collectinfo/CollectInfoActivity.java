package com.viettel.gem.screen.collectinfo;

import com.viettel.gem.base.ContainerActivity;
import com.viettel.gem.base.viper.ViewFragment;

public class CollectInfoActivity extends ContainerActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {
        return (ViewFragment) new CollectCustomerInfoPresenter(this).getFragment();
    }
}
