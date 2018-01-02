package com.viettel.gem.screen.demo;

import android.os.Bundle;

import com.viettel.gem.base.viper.Presenter;
import com.viettel.gem.base.viper.interfaces.ContainerView;

/**
 */
public class DemoPresenter extends Presenter<DemoContract.View, DemoContract.Interactor>
    implements DemoContract.Presenter {

  public DemoPresenter(ContainerView containerView) {
    super(containerView);
  }

  @Override
  public DemoContract.View onCreateView(Bundle data) {
    return DemoFragment.getInstance();
  }

  @Override
  public void start() {
    // Start getting data here
  }

  @Override
  public DemoContract.Interactor onCreateInteractor() {
    return new DemoInteractor(this);
  }
}
