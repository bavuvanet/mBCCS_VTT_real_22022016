package com.viettel.gem.screen.demo;


import com.viettel.gem.base.viper.ViewFragment;

/**
 */
public class DemoFragment extends ViewFragment<DemoContract.Presenter> implements DemoContract.View {


  public static DemoFragment getInstance() {
    return new DemoFragment();
  }

  @Override
  protected int getLayoutId() {
    return 0;
  }

  @Override
  public void initLayout() {
    super.initLayout();
  }
}
