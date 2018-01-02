package com.viettel.gem.screen.picturedetail;

import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.FileUtils;
import com.viettel.gem.base.viper.ViewFragment;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 */
public class PictureDetailFragment extends ViewFragment<PictureDetailContract.Presenter> implements PictureDetailContract.View {

    @BindView(R.id.imvPictureDetail)
    ImageView imvPictureDetail;

    public static PictureDetailFragment getInstance() {
        return new PictureDetailFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_picture_view;
    }

    @Override
    public void initLayout() {
        super.initLayout();
    }

    @OnClick(R.id.back_iv)
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                mPresenter.back();
                break;
            default:
                break;
        }
    }

    @Override
    public void showPicture(String url) {
        File file = new File(url);
//        byte[] byteImage = FileUtils.getByte(file);
//        Glide.with(getBaseActivity()).load(byteImage).fitCenter().into(imvPictureDetail);
        Picasso.with(getBaseActivity()).load(file).into(imvPictureDetail);
    }
}
