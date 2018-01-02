package com.viettel.gem.screen.listinforcustomer;

import android.os.Bundle;
import android.view.View;

import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.gem.base.viper.Presenter;
import com.viettel.gem.base.viper.interfaces.ContainerView;
import com.viettel.gem.model.SubscriberDTO;
import com.viettel.gem.screen.collectinfo.CollectCustomerInfoPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * The ListInforCustomer Presenter
 */
public class ListInforCustomerPresenter extends Presenter<ListInforCustomerContract.View, ListInforCustomerContract.Interactor>
        implements ListInforCustomerContract.Presenter, ListInforAdapter.InforClickListener, CollectCustomerInfoPresenter.CollectListener {

    private List<SubscriberDTO> subscriberDTOList = new ArrayList<>();
    private ListInforAdapter listInforAdapter;

    public ListInforCustomerPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {
        // Start getting data here
        listInforAdapter = new ListInforAdapter(getViewContext(), subscriberDTOList, this);
        mView.setAdapter(listInforAdapter);
    }

    @Override
    public ListInforCustomerContract.Interactor onCreateInteractor() {
        return new ListInforCustomerInteractor(this);
    }

    @Override
    public ListInforCustomerContract.View onCreateView(Bundle data) {
        return ListInforCustomerFragment.getInstance();
    }

    public ListInforCustomerPresenter setSubscriberDTOList(List<SubscriberDTO> subscriberDTOList) {
        this.subscriberDTOList = subscriberDTOList;
        return this;
    }

    @Override
    public void collectCustomer(String isdn, String idno, String address, String subId) {
        new CollectCustomerInfoPresenter(mContainerView)
                .setIsdn(isdn)
                .setIdno(idno)
                .setAddress(address)
                .setSubId(subId)
                .setCollectListener(this)
                .pushView();
    }

    @Override
    public void onClick(View view, int position) {
        SubscriberDTO subscriberDTO = subscriberDTOList.get(position);
        if (null != subscriberDTO) {
            String isdn = subscriberDTO.getIsdn();
            String idno = "";
            String address = null == subscriberDTO.getLstSubInfrastructureDTO() || subscriberDTO.getLstSubInfrastructureDTO().isEmpty()
                    ? "" : subscriberDTO.getLstSubInfrastructureDTO().get(0).getAddress();
            try {
                idno = subscriberDTO.getCustomerDTO().getListCustIdentity().get(0).getIdNo();
            } catch (Exception e) {
                e.printStackTrace();
            }
            collectCustomer(isdn, idno, address, String.valueOf(subscriberDTO.getSubId()));
        }
    }

    @Override
    public void onCollectSuccess(String subId) {
        if (!CommonActivity.isNullOrEmpty(subscriberDTOList)) {
            for (SubscriberDTO subscriberDTO : subscriberDTOList) {
                try {
                    if (subId.equals(String.valueOf(subscriberDTO.getSubId()))) {
                        subscriberDTO.setCusExisted(true);
                        getViewContext().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listInforAdapter.notifyDataSetChanged();
                            }
                        });
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
