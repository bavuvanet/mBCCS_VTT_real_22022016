package com.viettel.gem.screen.picturedetail;


import com.viettel.gem.base.viper.interfaces.IInteractor;
import com.viettel.gem.base.viper.interfaces.IPresenter;
import com.viettel.gem.base.viper.interfaces.PresentView;

/**
 */
interface PictureDetailContract {

  interface Interactor extends IInteractor<Presenter> {
  }

  interface View extends PresentView<Presenter> {
    void showPicture(String url);
  }

  interface Presenter extends IPresenter<View, Interactor> {
  }
}
