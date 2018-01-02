package com.viettel.gem.screen.listinforcustomer;

import android.support.v7.widget.RecyclerView;

import com.viettel.gem.base.viper.interfaces.IInteractor;
import com.viettel.gem.base.viper.interfaces.IPresenter;
import com.viettel.gem.base.viper.interfaces.PresentView;

/**
 * The ListInforCustomer Contract
 */
interface ListInforCustomerContract {

    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {
        void setAdapter(RecyclerView.Adapter adapter);
    }

    interface Presenter extends IPresenter<View, Interactor> {
      void collectCustomer(String isdn, String idno, String address, String subId);
    }
}



