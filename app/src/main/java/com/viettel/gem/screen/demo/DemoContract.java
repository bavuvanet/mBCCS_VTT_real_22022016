package com.viettel.gem.screen.demo;


import com.viettel.gem.base.viper.interfaces.IInteractor;
import com.viettel.gem.base.viper.interfaces.IPresenter;
import com.viettel.gem.base.viper.interfaces.PresentView;

/**
 */
interface DemoContract {

  interface Interactor extends IInteractor<Presenter> {
  }

  interface View extends PresentView<Presenter> {
  }

  interface Presenter extends IPresenter<View, Interactor> {
  }
}
