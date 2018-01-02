package com.viettel.gem.screen.picturedetail;

import android.os.Bundle;

import com.viettel.gem.base.viper.Presenter;
import com.viettel.gem.base.viper.interfaces.ContainerView;


/**
 */
public class PictureDetailPresenter extends Presenter<PictureDetailContract.View, PictureDetailContract.Interactor>
        implements PictureDetailContract.Presenter {

    private String url;

    public PictureDetailPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public PictureDetailContract.View onCreateView(Bundle data) {
        return PictureDetailFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
        mView.showPicture(getUrl());
    }

    public String getUrl() {
        return url;
    }

    public PictureDetailPresenter setUrl(String url) {
        this.url = url;
        return this;
    }

    @Override
    public PictureDetailContract.Interactor onCreateInteractor() {
        return new PictureDetailInteractor(this);
    }
}
