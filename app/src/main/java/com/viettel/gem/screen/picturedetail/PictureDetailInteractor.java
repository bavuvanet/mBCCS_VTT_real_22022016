package com.viettel.gem.screen.picturedetail;


import com.viettel.gem.base.viper.Interactor;

/**
 */
class PictureDetailInteractor extends Interactor<PictureDetailContract.Presenter>
    implements PictureDetailContract.Interactor {

  PictureDetailInteractor(PictureDetailContract.Presenter presenter) {
    super(presenter);
  }
}
