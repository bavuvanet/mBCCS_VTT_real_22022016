package com.viettel.gem.base.viper;

import com.viettel.gem.base.viper.interfaces.IInteractor;
import com.viettel.gem.base.viper.interfaces.IPresenter;

/**
 * Base Interactor
 * Created by neo on 8/29/2016.
 */
public abstract class Interactor<P extends IPresenter> implements IInteractor<P> {
  protected P mPresenter;

  public Interactor(P presenter) {
    mPresenter = presenter;
  }
}
