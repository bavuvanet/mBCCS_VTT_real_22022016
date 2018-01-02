package com.viettel.gem.screen.collectinfo;


import com.viettel.gem.base.viper.Interactor;

/**
 */
class CollectCustomerInfoInteractor extends Interactor<CollectCustomerInfoContract.Presenter>
    implements CollectCustomerInfoContract.Interactor {

  CollectCustomerInfoInteractor(CollectCustomerInfoContract.Presenter presenter) {
    super(presenter);
  }
}
