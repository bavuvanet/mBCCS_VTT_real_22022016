package com.viettel.gem.screen.searchcustomer;


import com.viettel.gem.base.viper.Interactor;

/**
 * Created by root on 21/11/2017.
 */

public class SearchCustomerInteractor extends Interactor<SearchCustomerContract.Presenter>
        implements SearchCustomerContract.Interactor {
    public SearchCustomerInteractor(SearchCustomerContract.Presenter presenter) {
        super(presenter);
    }
}
