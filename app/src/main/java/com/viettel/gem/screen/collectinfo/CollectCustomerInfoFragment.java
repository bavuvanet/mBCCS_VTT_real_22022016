package com.viettel.gem.screen.collectinfo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.object.ProductSpecificationDTO;
import com.viettel.bss.viettelpos.v4.utils.Log;
import com.viettel.gem.base.viper.ViewFragment;
import com.viettel.gem.screen.collectinfo.custom.GroupBoxView;
import com.viettel.gem.screen.collectinfo.custom.picture.PictureBoxView;
import com.viettel.gem.utils.PathUtils;
import com.viettel.gem.utils.RecyclerUtils;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 */
public class CollectCustomerInfoFragment extends ViewFragment<CollectCustomerInfoContract.Presenter> implements CollectCustomerInfoContract.View {

    @BindView(R.id.back_iv)
    ImageView mBackIv;

    @BindView(R.id.title_tv)
    TextView mTitleTv;

    @BindView(R.id.action_iv)
    ImageView mActioniv;

    @BindView(R.id.svRoot)
    ScrollView svRoot;

    @BindView(R.id.contentRoot)
    LinearLayout contentRoot;

    public static CollectCustomerInfoFragment getInstance() {
        return new CollectCustomerInfoFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_collect_customer_info;
    }

    @Override
    public void initLayout() {
        super.initLayout();
    }

    @Override
    public void setTitle(String title) {
        mTitleTv.setText(title);
    }

    @Override
    public void addView(List<ProductSpecificationDTO> productSpecificationDTOList) {
        if (null == productSpecificationDTOList || productSpecificationDTOList.isEmpty()) return;

        contentRoot.removeAllViews();

        for (ProductSpecificationDTO productSpecificationDTO : productSpecificationDTOList) {
            GroupBoxView groupBoxView = new GroupBoxView(getViewContext());
            groupBoxView.setIsdn(mPresenter.getIsdn());
            groupBoxView.setIdno(mPresenter.getIdno());
            groupBoxView.setAddress(mPresenter.getAddress());
            groupBoxView.setCreateNew(mPresenter.isCreateNew());
            groupBoxView.build(productSpecificationDTO, getBaseActivity());
            contentRoot.addView(groupBoxView);
            mPresenter.put(groupBoxView);
        }

        contentRoot.clearFocus();

    }

    @Override
    public ScrollView getScrollView() {
        return svRoot;
    }

    @OnClick({R.id.back_iv, R.id.title_tv, R.id.action_iv})
    void onClickView(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                mPresenter.back();
                break;
            case R.id.action_iv:
                mPresenter.collect();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureBoxView.CAPTURE_IMAGE) {
                mPresenter.resultPicture(null);
            }
            if (requestCode == PictureBoxView.PICK_FROM_FILE) {
                try {
                    Uri uri = data.getData();
                    String filePath = PathUtils.getPath(getViewContext(), uri);
                    if (null != filePath)
                        mPresenter.resultPicture(filePath);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
