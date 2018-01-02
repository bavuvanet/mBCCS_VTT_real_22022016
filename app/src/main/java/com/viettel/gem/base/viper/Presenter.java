package com.viettel.gem.base.viper;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.viettel.gem.base.viper.interfaces.ContainerView;
import com.viettel.gem.base.viper.interfaces.IInteractor;
import com.viettel.gem.base.viper.interfaces.IPresenter;
import com.viettel.gem.base.viper.interfaces.IView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Base implements for presenters
 * Created by neo on 14/03/2016.
 */
public abstract class Presenter<V extends IView, I extends IInteractor>
    implements IPresenter<V, I> {
  protected ContainerView mContainerView;
  protected V mView;
  protected I mInteractor;

  @SuppressWarnings("unchecked")
  public Presenter(ContainerView containerView) {
    init(containerView, null);
  }

  private void init(ContainerView containerView, Bundle data) {
    mContainerView = containerView;
    mInteractor = onCreateInteractor();
    mView = onCreateView(data);
    mView.setPresenter(this);
  }

  public Presenter(ContainerView containerView, Bundle data) {
    init(containerView, data);
  }

  public Activity getViewContext() {
    return mView.getViewContext();
  }

  @Override
  public V getView() {
    return mView;
  }

  @Override
  public I getInteractor() {
    return mInteractor;
  }

  @Override
  public Fragment getFragment() {
    return getView() instanceof Fragment ? (Fragment) getView() : null;
  }

  @Override
  public void presentView() {
    mContainerView.presentView(mView);
  }

  @Override
  public void pushView() {
    mContainerView.pushView(mView);
  }

  @Override
  public void pushChildView(int frameId, FragmentManager childFragmentManager) {
    if (getFragment() != null) {
      mContainerView.pushChildView(mView, frameId, childFragmentManager);
    }
  }

  @Override
  public void loadChildView(int frameId, FragmentManager childFragmentManager) {
    if (getFragment() != null) {
      mContainerView.loadChildView(mView, frameId, childFragmentManager);
    }
  }

  public void addView() {
    mContainerView.addView(mView);
  }

  // Event bus

  @Override
  public void registerEventBus() {
  }

  @Override
  public void unregisterEventBus() {
  }

  @Subscribe(threadMode = ThreadMode.BACKGROUND)
  public void onMessageEvent(NoneEvent event) {
  }

  public static class NoneEvent {
  }

  @Override
  public void back() {
    mView.getBaseActivity().hideKeyboard();
    mContainerView.back();
  }

  @Override
  public void onFragmentDisplay() {
  }

  @Override
  public boolean isViewShowing() {
    return ((ViewFragment) mView).isShowing();
  }
}
