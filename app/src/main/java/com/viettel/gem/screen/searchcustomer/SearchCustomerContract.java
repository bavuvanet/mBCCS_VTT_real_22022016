package com.viettel.gem.screen.searchcustomer;


import com.viettel.gem.base.viper.interfaces.IInteractor;
import com.viettel.gem.base.viper.interfaces.IPresenter;
import com.viettel.gem.base.viper.interfaces.PresentView;

/**
 * Created by root on 21/11/2017.
 */

interface SearchCustomerContract {

    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void doSearch(String isdn, String idno);
        void showDialogNoData(String isdn, String idno);
        void fakeSearch();
    }
}
