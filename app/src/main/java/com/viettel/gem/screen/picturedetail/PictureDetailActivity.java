package com.viettel.gem.screen.picturedetail;

import android.os.Bundle;

import com.viettel.gem.base.ContainerActivity;
import com.viettel.gem.base.viper.ViewFragment;


/**
 * Created by root on 21/11/2017.
 */

public class PictureDetailActivity extends ContainerActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {
        Bundle b = getData();
        String url = null;
        if (null != b) {
            url = b.getString("url");
        }
        return (ViewFragment) new PictureDetailPresenter(this).setUrl(url).getFragment();
    }
}
