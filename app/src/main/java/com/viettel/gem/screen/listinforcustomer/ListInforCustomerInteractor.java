package com.viettel.gem.screen.listinforcustomer;

import com.viettel.gem.base.viper.Interactor;

/**
 * The ListInforCustomer interactor
 */
class ListInforCustomerInteractor extends Interactor<ListInforCustomerContract.Presenter>
        implements ListInforCustomerContract.Interactor {

    ListInforCustomerInteractor(ListInforCustomerContract.Presenter presenter) {
        super(presenter);
    }
}
