package com.viettel.gem.screen.collectinfo;

import android.support.v7.widget.RecyclerView;
import android.widget.ScrollView;

import com.viettel.bss.viettelpos.v4.object.ProductSpecificationDTO;
import com.viettel.gem.base.viper.interfaces.IInteractor;
import com.viettel.gem.base.viper.interfaces.IPresenter;
import com.viettel.gem.base.viper.interfaces.PresentView;
import com.viettel.gem.screen.collectinfo.custom.GroupBoxView;

import java.io.File;
import java.util.List;

/**
 */
interface CollectCustomerInfoContract {

    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {
        void setTitle(String title);

        void addView(List<ProductSpecificationDTO> productSpecificationDTOList);

        ScrollView getScrollView();
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void collect();

        void put(GroupBoxView groupBoxView);

        CollectCustomerInfoPresenter setIsdn(String mIsdn);

        CollectCustomerInfoPresenter setIdno(String mIdNo);

        boolean isCreateNew();

        void resultPicture(String filePath);

        String getIsdn();

        String getIdno();

        String getAddress();
    }
}
