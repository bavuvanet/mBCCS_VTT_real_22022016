package com.viettel.gem.screen.collectinfo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.object.ProductSpecificationDTO;
import com.viettel.bss.viettelpos.v4.omichanel.dao.OrderState;
import com.viettel.gem.asynctask.CustomerInfoCollectAsyncTask;
import com.viettel.gem.asynctask.GetCustomerInfoCollectAsyncTask;
import com.viettel.gem.base.viper.Presenter;
import com.viettel.gem.base.viper.interfaces.ContainerView;
import com.viettel.gem.model.ProductSpecificationModel;
import com.viettel.gem.screen.collectinfo.custom.GroupBoxView;
import com.viettel.gem.utils.FileUtils;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
public class CollectCustomerInfoPresenter extends Presenter<CollectCustomerInfoContract.View, CollectCustomerInfoContract.Interactor>
        implements CollectCustomerInfoContract.Presenter, CollectCustomerInfoAdapter.Callback {

    private static final int REQUEST_PERMISSIONS_STORAGE = 1000;
    List<GroupBoxView> mGroupViewManager = new ArrayList<>();

    ProductSpecificationModel productSpecificationModel;
    private String mIsdn;
    private String mIdNo;
    private String mAddress;
    private String mSubId;
    private boolean createNew = false;
    private CollectListener collectListener;

    public static int mTotalMember = 0;
    public static int mTotalJob = 0;
    public static boolean selectedOtherCustCDT = false;

    public CollectCustomerInfoPresenter(ContainerView containerView) {
        super(containerView);
        mTotalMember = 0;
        mTotalJob = 0;
        selectedOtherCustCDT = false;
    }

    @Override
    public CollectCustomerInfoContract.View onCreateView(Bundle data) {
        return CollectCustomerInfoFragment.getInstance();
    }

    protected final View.OnClickListener moveLogInAct = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LoginDialog dialog = new LoginDialog(getViewContext(), "");
            dialog.show();
        }
    };

    @Override
    public void start() {

        // Start getting data here

        mView.setTitle(isCreateNew() ? getViewContext().getString(R.string.them_moi_thong_tin_khach_hang)
                : getViewContext().getString(R.string.cap_nhat_thong_tin_khach_hang));

//        fake();
        getCustomerInfoCollect();
    }

    private void fake() {
        //FAKE
        String response = FileUtils.loadContentFromFile(getViewContext(), "xmls/product_v1.xml");
        Serializer serializer = new Persister();
        ProductSpecificationModel result = null;
        try {
            result = serializer.read(ProductSpecificationModel.class, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != result) {
            productSpecificationModel = result;
            initData(result.getLstProductSpecificationDTOs());
            Log.e("@@@@", result.toString());
        } else {
            Log.e("@@@@", "nulllllll");
        }
    }

    private void initData(List<ProductSpecificationDTO> productSpecificationDTOList) {
        mView.addView(productSpecificationDTOList);
    }

    private void getCustomerInfoCollect() {
        new GetCustomerInfoCollectAsyncTask(getViewContext(), new OnPostExecuteListener<ProductSpecificationModel>() {
            @Override
            public void onPostExecute(ProductSpecificationModel result, String errorCode, String description) {
                if ("0".equals(errorCode)) {
                    productSpecificationModel = result;
                    if (null != result) {
                        initData(result.getLstProductSpecificationDTOs());
                    }
                } else {
                    if (CommonActivity.isNullOrEmpty(description)) {
                        CommonActivity.createAlertDialog(getViewContext(),
                                getViewContext().getString(R.string.checkdes),
                                getViewContext().getString(R.string.app_name)).show();
                    } else {
                        String[] arrayString = description.split(" ");
                        if (arrayString.length > 0
                                && StringUtils.isDigit(arrayString[arrayString.length - 1])) {
                        } else {
                            CommonActivity.createAlertDialog(getViewContext(),
                                    description,
                                    getViewContext().getString(R.string.app_name)).show();
                        }
                    }
                }
            }
        }, moveLogInAct).execute(mIsdn, mIdNo);
    }

    @Override
    public CollectCustomerInfoContract.Interactor onCreateInteractor() {
        return new CollectCustomerInfoInteractor(this);
    }

    @Override
    public void onGroupSelected(ProductSpecificationDTO productSpecificationDTO) {

    }

    @Override
    public void collect() {

        boolean isValid = true;
        for (GroupBoxView groupBoxView : mGroupViewManager) {
            if (null != groupBoxView) {
                if (!groupBoxView.validateView()) {
                    if (isValid) isValid = false;
                }
            }
        }

        if (!isValid) {
            Toast.makeText(getViewContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (null != productSpecificationModel) {

            requestPermission();

        }
    }

    private void doCollect() {
        new CustomerInfoCollectAsyncTask(getViewContext(), new OnPostExecuteListener<String>() {
            @Override
            public void onPostExecute(String result, String errorCode, String description) {
                if ("0".equals(errorCode)) {
                    Toast.makeText(getViewContext(), "Cập nhật thông tin thành công.", Toast.LENGTH_SHORT).show();

                    if (null != collectListener) {
                        collectListener.onCollectSuccess(mSubId);
                    }

                    back();
                } else {
                    if (CommonActivity.isNullOrEmpty(description)) {
                        CommonActivity.createAlertDialog(getViewContext(),
                                getViewContext().getString(R.string.checkdes),
                                getViewContext().getString(R.string.app_name)).show();
                    } else {
                        String[] arrayString = description.split(" ");
                        if (arrayString.length > 0
                                && StringUtils.isDigit(arrayString[arrayString.length - 1])) {
                        } else {
                            CommonActivity.createAlertDialog(getViewContext(),
                                    description,
                                    getViewContext().getString(R.string.app_name)).show();
                        }
                    }
                }
            }
        }, moveLogInAct).execute(productSpecificationModel);
    }

    public void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getViewContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (!getViewContext().shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    ActivityCompat.requestPermissions(getViewContext(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS_STORAGE);
                } else {
                    Snackbar.make(getViewContext().findViewById(android.R.id.content), getViewContext().getResources().getString(R.string.permisson_explain_storage), Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getViewContext().getPackageName(), null);
                            intent.setData(uri);
                            getViewContext().startActivity(intent);
                            intent.addCategory(Intent.CATEGORY_DEFAULT);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        }
                    }).show();
                }
            } else {
                doCollect();

            }
        } else {
            doCollect();
        }
    }

    @Override
    public void put(GroupBoxView groupBoxView) {
        mGroupViewManager.add(groupBoxView);
    }

    @Override
    public CollectCustomerInfoPresenter setIsdn(String mIsdn) {
        this.mIsdn = mIsdn;
        return this;
    }

    @Override
    public CollectCustomerInfoPresenter setIdno(String mIdNo) {
        this.mIdNo = mIdNo;
        return this;
    }

    public CollectCustomerInfoPresenter setSubId(String mSubId) {
        this.mSubId = mSubId;
        return this;
    }

    public CollectCustomerInfoPresenter setCollectListener(CollectListener collectListener) {
        this.collectListener = collectListener;
        return this;
    }

    @Override
    public boolean isCreateNew() {
        return createNew;
    }

    public CollectCustomerInfoPresenter setCreateNew(boolean createNew) {
        this.createNew = createNew;
        return this;
    }

    @Override
    public void resultPicture(String filePath) {
        for (GroupBoxView groupBoxView : mGroupViewManager) {
            groupBoxView.resultPicture(filePath);
        }
    }

    @Override
    public String getIsdn() {
        return mIsdn;
    }

    @Override
    public String getIdno() {
        return mIdNo;
    }

    @Override
    public String getAddress() {
        return mAddress;
    }

    public CollectCustomerInfoPresenter setAddress(String mAddress) {
        this.mAddress = mAddress;
        return this;
    }

    public interface CollectListener {
        void onCollectSuccess(String subId);
    }
}
