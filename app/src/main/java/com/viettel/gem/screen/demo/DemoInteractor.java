package com.viettel.gem.screen.demo;

import com.viettel.gem.base.viper.Interactor;

/**
 */
class DemoInteractor extends Interactor<DemoContract.Presenter>
    implements DemoContract.Interactor {

  DemoInteractor(DemoContract.Presenter presenter) {
    super(presenter);
  }
}
