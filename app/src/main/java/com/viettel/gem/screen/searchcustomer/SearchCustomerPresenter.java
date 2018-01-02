package com.viettel.gem.screen.searchcustomer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.gem.asynctask.SearchCustomerAsyncTask;
import com.viettel.gem.base.viper.Presenter;
import com.viettel.gem.base.viper.interfaces.ContainerView;
import com.viettel.gem.model.SubscriberDTO;
import com.viettel.gem.model.SubscriberModel;
import com.viettel.gem.screen.collectinfo.CollectCustomerInfoPresenter;
import com.viettel.gem.screen.listinforcustomer.ListInforCustomerPresenter;
import com.viettel.gem.utils.FileUtils;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 21/11/2017.
 */

public class SearchCustomerPresenter extends Presenter<SearchCustomerContract.View, SearchCustomerContract.Interactor>
        implements SearchCustomerContract.Presenter {
    public SearchCustomerPresenter(ContainerView containerView) {
        super(containerView);
    }

    private List<SubscriberDTO> subscriberDTOList = new ArrayList<>();

    protected final View.OnClickListener moveLogInAct = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LoginDialog dialog = new LoginDialog(getViewContext(), "");
            dialog.show();
        }
    };

    @Override
    public void start() {

    }

    @Override
    public SearchCustomerContract.Interactor onCreateInteractor() {
        return new SearchCustomerInteractor(this);
    }

    @Override
    public SearchCustomerContract.View onCreateView(Bundle data) {
        return SearchCustomerFragment.getInstance();
    }

    @Override
    public void doSearch(String isdn, String idno) {
//        fakeSearch();//fake
        getAllCustomer(isdn, idno);
    }

    @Override
    public void showDialogNoData(final String isdn, final String idno) {
        final ConfirmDialog confirmDialog = new ConfirmDialog(getViewContext(), new ConfirmDialog.ButtonClickDialog() {
            @Override
            public void onAddNew() {
                //add new infor customer
                new CollectCustomerInfoPresenter(mContainerView).setIsdn(isdn).setIdno(idno).setCreateNew(true).pushView();
            }

            @Override
            public void onCancel() {
            }
        });
        confirmDialog.show();
    }

    @Override
    public void fakeSearch() {
        //FAKE
        String response = FileUtils.loadContentFromFile(getViewContext(), "xmls/search.xml");
        Serializer serializer = new Persister();
        SubscriberModel result = null;
        try {
            result = serializer.read(SubscriberModel.class, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != result
                && null != result.getLstSubscriberDTOExt()
                && !result.getLstSubscriberDTOExt().isEmpty()) {
            if (null == subscriberDTOList) {
                subscriberDTOList = new ArrayList<>();
            } else {
                subscriberDTOList.clear();
            }

            subscriberDTOList.addAll(result.getLstSubscriberDTOExt());

            new ListInforCustomerPresenter(mContainerView).setSubscriberDTOList(subscriberDTOList).pushView();
            Log.e("@@@@", result.toString());
        } else {
            showDialogNoData("42342323525435", "2323123dsasad");
            Log.e("@@@@", "nulllllll");
        }
    }

    private void getAllCustomer(final String isdn, final String idno) {
        new SearchCustomerAsyncTask(getViewContext(), new OnPostExecuteListener<SubscriberModel>() {
            @Override
            public void onPostExecute(SubscriberModel result, String errorCode, String description) {
                if ("0".equals(errorCode)) {
                    if (null != result
                            && null != result.getLstSubscriberDTOExt()
                            && !result.getLstSubscriberDTOExt().isEmpty()) {
                        if (null == subscriberDTOList) {
                            subscriberDTOList = new ArrayList<>();
                        } else {
                            subscriberDTOList.clear();
                        }

                        subscriberDTOList.addAll(result.getLstSubscriberDTOExt());

                        new ListInforCustomerPresenter(mContainerView).setSubscriberDTOList(subscriberDTOList).pushView();
                        Log.e("@@@@", result.toString());
                    } else {
                        showDialogNoData(isdn, idno);
                    }
                } else {
                    showDialogNoData(isdn, idno);
                }
            }
        }, moveLogInAct).execute(isdn, idno);
    }
}
