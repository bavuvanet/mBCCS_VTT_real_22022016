package com.viettel.bss.viettelpos.v3.connecttionService.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter.GetFeeBCCSAdapter;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustIdentityDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustomerDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferPriceDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferTypeDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferingDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductPackageFeeDTO;
import com.viettel.bss.smartphonev2.commons.OnPostExecute;
import com.viettel.bss.viettelpos.v3.connecttionService.adapter.ProfileAdapter.OnChangeImage;
import com.viettel.bss.viettelpos.v3.connecttionService.adapter.SubAddtionalAdapter;
import com.viettel.bss.viettelpos.v3.connecttionService.asytask.AsynGenerateGroupCode;
import com.viettel.bss.viettelpos.v3.connecttionService.asytask.AsynGetQuotaMapByTelecomService;
import com.viettel.bss.viettelpos.v3.connecttionService.asytask.AsyncGenerateAccounts;
import com.viettel.bss.viettelpos.v3.connecttionService.asytask.AsynctaskGetListCustType;
import com.viettel.bss.viettelpos.v3.connecttionService.asytask.AsynctaskGetPNIsdn;
import com.viettel.bss.viettelpos.v3.connecttionService.asytask.ConnectSubMultiAsyn;
import com.viettel.bss.viettelpos.v3.connecttionService.asytask.GetAllListPaymentPrePaidAsyn;
import com.viettel.bss.viettelpos.v3.connecttionService.asytask.GetDepositAsyncTask;
import com.viettel.bss.viettelpos.v3.connecttionService.asytask.GetIpAddressAsyncTask;
import com.viettel.bss.viettelpos.v3.connecttionService.asytask.GetListStockTypeAsynTask;
import com.viettel.bss.viettelpos.v3.connecttionService.model.MoreTelecomService;
import com.viettel.bss.viettelpos.v3.connecttionService.model.OptionSetValueDTO;
import com.viettel.bss.viettelpos.v3.connecttionService.model.ProductCatalogDTO;
import com.viettel.bss.viettelpos.v3.connecttionService.model.ResultSurveyOnlineForBccs2Form;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommonV4;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.Business.AreaBusiness;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.CreateAddressCommon;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.DialogDetailAtribute;
import com.viettel.bss.viettelpos.v4.commons.GetInfoPackageAsyn;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.commons.auto.AutoCompleteUtil;
import com.viettel.bss.viettelpos.v4.commons.auto.AutoConst;
import com.viettel.bss.viettelpos.v4.commons.auto.AutoDauNoi;
import com.viettel.bss.viettelpos.v4.commons.auto.template.CallbackObj;
import com.viettel.bss.viettelpos.v4.commons.event.DauNoiCallback;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.ProductOfferCharacterClone;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.TabThueBaoHopDongManager;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetListPaymentDetailChargeAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.CustomerOrderDetailClone;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.HTHMBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PaymentPrePaidDetailBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PaymentPrePaidPromotionBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PromotionTypeBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ReasonPledgeDTO;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.SubTypeBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetAreaDal;
import com.viettel.bss.viettelpos.v4.customer.object.FormBean;
import com.viettel.bss.viettelpos.v4.customer.object.ProfileUpload;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.customview.obj.FileObj;
import com.viettel.bss.viettelpos.v4.customview.obj.RecordPrepaid;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.hsdt.fragments.ProfileInfoDialogFragment;
import com.viettel.bss.viettelpos.v4.listener.OnFinishDSFListener;
import com.viettel.bss.viettelpos.v4.object.ProfileBO;
import com.viettel.bss.viettelpos.v4.omichanel.asynctask.PlaceOrderAsyncTask;
import com.viettel.bss.viettelpos.v4.task.AsynTaskGetListRecordProfile;
import com.viettel.bss.viettelpos.v4.ui.image.picker.ImagePreviewActivity;
import com.viettel.bss.viettelpos.v4.utils.ImageUtils;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HuYPQ15. Dau noi thue bao
 */
public class TabThongTinThueBao extends FragmentCommonV4 implements DauNoiCallback {
    public static String CHECK_CHANGE_TECH = "";
    public static String oldSubId = "";
    private final int REQUEST_CHOOSE_PACKAGE = 1;
    private final int REQUEST_CHOOSE_HTHM = 2;
    private final int REQUEST_CHOOSE_SUBTYPE = 3;
    private final int REQUEST_CHOOSE_PROMOTION = 4;
    private final int REQUEST_CHON_THIET_BI = 5;
    private final int REQUEST_CHOOSE_ADDRESS = 6;
    public CustIdentityDTO custIdentityDTO = null;
    public HTHMBeans hthm = null;
    public String reasonId = null;
    Map<String, String> mMapTemplate = new HashMap<String, String>();
    OnChangeImage onChangeImage = new OnChangeImage() {
        @Override
        public void onChangeImage(ProfileUpload item) {
            ImagePreviewActivity.pickImage(getActivity(), item.getLstFile(),
                    item.getId());
        }
    };
    @BindView(R.id.lnSelectProfile)
    LinearLayout lnSelectProfile;
    @BindView(R.id.thumbnails)
    LinearLayout thumbnails;
    @BindView(R.id.horizontalScrollView)
    HorizontalScrollView horizontalScrollView;
    @BindView(R.id.expandedImageView)
    ImageView expandedImageView;
    @BindView(R.id.frlMain)
    FrameLayout frlMain;
    private ResultSurveyOnlineForBccs2Form surveyOnline;
    private Map<Long, DialogSubInfo> mapDialogSubInfo = new HashMap<Long, DialogSubInfo>();
    private EditText edtNgayHen, edtDiaChiLapDat, edtService, edtTechnology,
            edtKetCuoi, edtDuongDay, edtIsdn, edtHTHM, edtSubType,
            edtPromotion, edtAccPPPOE, edtPassPPPOE;
    private TextView tvcamketso;
    private Spinner spnCuocDongTruoc, spnStaticIp, spnCustType, spnMoHinh,
            spnQuota;
    private View prbCustType, prbIsdn, lnThietBi, prbStaticIp, prbPromotion,
            prbThietBi, lnIpAddress, lnMoHinh;
    // private View lnPPPOE, lnPassPPPOE;
    private View btnRefreshCustType;
    private EditText edtGoiCuoc;
    private TextView tvThietBi;
    private Map<Long, ArrayList<ProductOfferingDTO>> mapGoiCuoc = new HashMap<Long, ArrayList<ProductOfferingDTO>>();
    private Map<Long, ArrayList<HTHMBeans>> mapHTHM = new HashMap<Long, ArrayList<HTHMBeans>>();
    private ArrayList<SubscriberDTO> lstSub = new ArrayList<SubscriberDTO>();
    private SubscriberDTO currentSub;
    private SubscriberDTO mainSub;
    private ArrayList<SubTypeBeans> lstSubType;
    private AreaObj area;
    private Dialog dialogCuocdongtruoc = null;
    // Cac doi tuong trong Dialog
    private Dialog dialogListSub = null;
    private View prbCuocDongTruoc, btnConnectSub;
    private Button btnInfoPromotion, btnInfoProduct, btnPaymentInfo;
    private Map<String, ArrayList<PaymentPrePaidPromotionBeans>> mapPrepaid = new HashMap<String, ArrayList<PaymentPrePaidPromotionBeans>>();
    private Spinner spnDeposit;
    private View prbPPPOE, prbPassPPPOE, btnRefreshDeposit,
            prbDeposit, prbDuongDay, lnQuota, prbQuota, btnRefreshQuota;
    // private Map<String, ArrayList<RecordPrepaid>> mapListRecordPrepaid = new
    // HashMap<String, ArrayList<RecordPrepaid>>();
    // private HashMap<String, ArrayList<FileObj>> hashmapFileObj = new
    // HashMap<String, ArrayList<FileObj>>();
    private View prbProfile;
    private SubscriberDTO tmpSub;
    private ListView lvUploadImage;
    private Map<String, List<String>> mapIpAddress = new HashMap<String, List<String>>();
    // Cac doi tuong tren dialog
    // private Map<Long, View> lnPPPOED = new HashMap<Long, View>();
    // private Map<Long, View> lnPassPPPOED = new HashMap<Long, View>();
    private Map<Long, View> prbStaticIpD = new HashMap<Long, View>();
    private Map<Long, View> btnRefreshQuotaD = new HashMap<Long, View>();
    private Map<Long, View> prbPromotionD = new HashMap<Long, View>();
    private Map<Long, View> prbThietBiD = new HashMap<Long, View>();
    private Map<Long, View> prbCustTypeD = new HashMap<Long, View>();
    private Map<Long, View> prbIsdnD = new HashMap<Long, View>();
    private Map<Long, View> lnThietBiD = new HashMap<Long, View>();
    private Map<Long, View> lnQuotaD = new HashMap<Long, View>();
    private Map<Long, View> prbCuocDongTruocD = new HashMap<Long, View>();
    private Map<Long, View> btnRefreshCustTypeD = new HashMap<Long, View>();
    private Map<Long, View> btnSaveSubD = new HashMap<Long, View>();
    private Map<Long, View> btnCloseD = new HashMap<Long, View>();
    private Map<Long, View> prbProfileD = new HashMap<Long, View>();
    // private Map<Long, View> prbPPPOED = new HashMap<Long, View>();
    // private Map<Long, View> prbPassPPPOED = new HashMap<Long, View>();
    private Map<Long, View> btnRefreshDepositD = new HashMap<Long, View>();
    private Map<Long, View> prbDepositD = new HashMap<Long, View>();
    private Map<Long, View> prbDuongDayD = new HashMap<Long, View>();
    private Map<Long, Spinner> spnCuocDongTruocD = new HashMap<Long, Spinner>();
    private Map<Long, Button> btnPaymentInfoD = new HashMap<Long, Button>();
    private Map<Long, Spinner> spnStaticIpD = new HashMap<Long, Spinner>();
    private Map<Long, Spinner> spnCustTypeD = new HashMap<Long, Spinner>();
    private Map<Long, Spinner> spnDepositD = new HashMap<Long, Spinner>();
    private Map<Long, EditText> edtGoiCuocD = new HashMap<Long, EditText>();
    private Map<Long, EditText> edtNgayHenD = new HashMap<Long, EditText>();
    private Map<Long, EditText> edtDiaChiLapDatD = new HashMap<Long, EditText>();
    private Map<Long, EditText> edtServiceD = new HashMap<Long, EditText>();
    private Map<Long, EditText> edtTechnologyD = new HashMap<Long, EditText>();
    private Map<Long, EditText> edtKetCuoiD = new HashMap<Long, EditText>();
    private Map<Long, EditText> edtDuongDayD = new HashMap<Long, EditText>();
    private Map<Long, EditText> edtIsdnD = new HashMap<Long, EditText>();
    private Map<Long, TextView> tvcamketsoD = new HashMap<Long, TextView>();
    private Map<Long, EditText> edtHTHMD = new HashMap<Long, EditText>();
    private Map<Long, EditText> edtSubTypeD = new HashMap<Long, EditText>();
    private Map<Long, EditText> edtPromotionD = new HashMap<Long, EditText>();
    // private Map<Long, EditText> edtAccPPPOED = new HashMap<Long, EditText>();
    // private Map<Long, EditText> edtPassPPPOED = new HashMap<Long,
    // EditText>();
    private Map<Long, TextView> tvThietBiD = new HashMap<Long, TextView>();
    private Map<Long, ListView> lvUploadImageD = new HashMap<Long, ListView>();
    private Map<Long, View> lnIpAddressD = new HashMap<Long, View>();
    private Map<Long, View> lnMoHinhD = new HashMap<Long, View>();
    private Map<Long, Spinner> spnMoHinhD = new HashMap<Long, Spinner>();
    private Map<Long, Spinner> spnQuotaD = new HashMap<Long, Spinner>();
    private Map<Long, View> prbQuotaD = new HashMap<Long, View>();

    private String actionCode = "549";
    private String productCode = "";
    //update profile ho so dien tu
    private HashMap<String, ArrayList<FileObj>> hashmapFileObj = new HashMap<String, ArrayList<FileObj>>();
    private ProfileBO profileBO = new ProfileBO();
    private ImageView expandedImageViewSubOther;
    private FrameLayout frlMainSubOther;
    private Animator mCurrentAnimator;
    OnFinishDSFListener onFinishDSFListener = new OnFinishDSFListener() {
        @Override
        public void onFinish(ProfileBO output) {
            profileBO = output;
            if ((profileBO != null
                    && profileBO.getHashmapFileObj() != null
                    && !profileBO.getHashmapFileObj().isEmpty())
                    || (profileBO != null && profileBO.getProfileRecords() != null && profileBO.getProfileRecords().size() > 0)) {


                thumbnails.removeAllViews();
                horizontalScrollView.setVisibility(View.VISIBLE);


                ArrayList<String> lstData = new ArrayList<>();
                for (Map.Entry<String, ArrayList<FileObj>> entry : profileBO.getHashmapFileObj().entrySet()) {
                    for (FileObj fileObj : entry.getValue()) {
                        Log.d("onFinishDSFListener", fileObj.getFullPath());
                        lstData.add(fileObj.getFullPath());
                    }
                }
                if (profileBO.getSigImageFullPath() != null) {
                    lstData.add(profileBO.getSigImageFullPath());
                }
                ImageUtils.loadImageHorizontal(getActivity(), LayoutInflater.from(getActivity()), thumbnails, lstData, new com.viettel.bss.viettelpos.v4.listener.OnClickListener() {
                    @Override
                    public void onClick(Object... obj) {
                        View thumView = (View) obj[0];
                        String path = String.valueOf(obj[1]);
                        zoomImageFromThumb(thumView, path, frlMain, expandedImageView);
                    }
                });
            } else {
                horizontalScrollView.setVisibility(View.GONE);
            }
        }
    };
    private Map<Long, LinearLayout> thumbnailsD = new HashMap<Long, LinearLayout>();
    private Map<Long, HorizontalScrollView> horizontalScrollViewD = new HashMap<Long, HorizontalScrollView>();
    private Map<Long, HTHMBeans> hthmD = new HashMap<Long, HTHMBeans>();
    private Map<Long, String> reasonIdD = new HashMap<Long, String>();
    private Map<Long, ProfileBO> profileBOD = new HashMap<Long, ProfileBO>();
    private Map<Long, ProfileInfoDialogFragment> profileInfoDialogFragmentD = new HashMap<Long, ProfileInfoDialogFragment>();
    private String groupType = null; //dang fix groupType; neu dau >1 thue bao hoac khach hang doanh nghiep thi groupType =1 de khong du sung chu ky dien tu
    private LinearLayout lnSelectProfileSubOther;
    private ProfileInfoDialogFragment profileInfoDialogFragment;
    private Map<String, ArrayList<RecordPrepaid>> mapListRecordPrepaid;


    //xong ho so dien tu
    private ProfileBO input;
    // omni
    private String omniProcessId = "";
    private String omniTaskId = "";
    private AutoDauNoi mAutoDauNoi;
    private OnPostExecute<ParseOuput> onPostGenereAccout = new OnPostExecute<ParseOuput>() {

        @Override
        public void onPostExecute(ParseOuput result) {

            OnClickListener onCickRetry = new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    String infraType = TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit
                            .getInfraType();
                    AsyncGenerateAccounts as = new AsyncGenerateAccounts(
                            act,
                            infraType,
                            TabThueBaoHopDongManager.instance.lstProductCatalog,
                            area.getProvince(),
                            TabThueBaoHopDongManager.instance.custIdentityDTO,
                            onPostGenereAccout);
                    as.execute();
                }
            };
            OnClickListener onCickCancel = new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    getActivity().onBackPressed();
                }
            };
            if ("0".equals(result.getErrorCode())) {
                if (CommonActivity.isNullOrEmpty(result
                        .getLstMoreTelecomService())) {

                    CommonActivity.createAlertDialog(act,
                            getString(R.string.generate_account_fail),
                            getString(R.string.app_name), onCickRetry);
                    return;
                }
                List<MoreTelecomService> lstMore = result
                        .getLstMoreTelecomService();

                if (!mainSub.isPNService()) {
                    for (int i = 0; i < lstMore.size(); i++) {
                        if (lstMore.get(i).getTelecomServiceId()
                                .equals(mainSub.getTelecomServiceId())) {
                            mainSub.setAccount(lstMore.get(i).getAccount());
                            mainSub.setIsdn(lstMore.get(i).getAccount());
                            edtIsdn.setText(mainSub.getIsdn());
                            lstMore.remove(i);
                            break;
                        }
                    }
                }

                if (!CommonActivity.isNullOrEmpty(lstSub)
                        && !CommonActivity.isNullOrEmptyArray(lstMore)) {
                    for (SubscriberDTO sub : lstSub) {
                        for (int i = 0; i < lstMore.size(); i++) {
                            MoreTelecomService more = lstMore.get(i);
                            if (sub.getTelecomServiceId().equals(
                                    more.getTelecomServiceId())) {
                                sub.setAccount(more.getAccount());
                                sub.setIsdn(more.getAccount());
                                lstMore.remove(i);
                                break;
                            }
                        }
                    }
                }

            } else {
                if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
                    CommonActivity.BackFromLogin(act, result.getDescription(), ";cm.connect_sub_bccs2;");
                } else {
                    if (result.getDescription() == null
                            || result.getDescription().isEmpty()) {
                        result.setDescription(act.getString(R.string.checkdes));
                    }
                    Dialog dialog = CommonActivity.createDialog(act,
                            result.getDescription(),
                            act.getString(R.string.app_name),
                            getString(R.string.cancel),
                            getString(R.string.retry), onCickCancel,
                            onCickRetry);
                    dialog.show();
                }
            }

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        act = getActivity();
        if (mView == null) {
            // dfdf
            idLayout = R.layout.layout_thong_tin_thue_bao;
            mView = inflater.inflate(idLayout, container, false);
            ButterKnife.bind(this, mView);
            unit(mView);
        } else {
//			((ViewGroup) mView.getParent()).removeAllViews();
        }
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.getInstance().setTitleActionBar(R.string.infoconnect);
    }

    public void unit(View v) {

        //update ho so dien tu


        Bundle bundle = getArguments();
        if (bundle != null) {
            this.omniTaskId = (String) bundle.getSerializable("omniTaskId");
            this.omniProcessId = (String) bundle.getSerializable("omniProcessId");
        }

        TabThongTinHopDong.accountDTOMain = null;
        custIdentityDTO = TabThueBaoHopDongManager.instance.custIdentityDTO;
        lnSelectProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                // Kiem tra thong tin hop dong truoc khi chon ho so
//                if (CommonActivity.isNullOrEmpty(TabThongTinHopDong.accountDTOMain)) {
//                    CommonActivity.createErrorDialog(act,
//                            getString(R.string.chua_chon_hop_dong), "1").show();
//
//                    return ;
//                }
//
//                String reason = edtHTHM.getText().toString().trim();
//                if (CommonActivity.isNullOrEmpty(reasonId)
//                        || act.getString(R.string.chonlydo).equals(reason)) {
//                    CommonActivity.toast(getActivity(), R.string.chua_chon_ly_do_dang_ky);
//                    return;
//                }


                if(!validateProfile(mainSub)){
                    return ;
                }

                if (!CommonActivity.isNullOrEmpty(profileBO)
                        && !CommonActivity.isNullOrEmpty(profileBO.getMapListRecordPrepaid())) {
                    profileInfoDialogFragment = new ProfileInfoDialogFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("profileBO", profileBO);
                    String ngayHen = edtNgayHen.getText().toString();

                    bundle.putString("xmlSub", getXmlSub(mainSub, ngayHen));
                    bundle.putString("xmlCus", getXmlCus(custIdentityDTO));
                    bundle.putString("groupType", groupType);
                    profileInfoDialogFragment.setOnFinishDSFListener(onFinishDSFListener);
                    profileInfoDialogFragment.setArguments(bundle);
                    profileInfoDialogFragment.show(getFragmentManager(), "show profile main");
                }
            }
        });

        //xong ho so dien tu
        btnPaymentInfo = (Button) mView.findViewById(R.id.btnInfoPayment);
        btnPaymentInfo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                SubscriberDTO sub = getSub();
                if (!CommonActivity.isNullOrEmpty(sub.getCuocDongTruoc())) {
                    showSelectCuocDongTruoc(sub.getCuocDongTruoc());
                }
            }
        });
        btnInfoPromotion = (Button) mView.findViewById(R.id.btnInfoPromotion);
        btnInfoProduct = (Button) mView.findViewById(R.id.btnInfoProduct);
        prbPromotion = mView.findViewById(R.id.prbPromotion);
        lnQuota = mView.findViewById(R.id.lnQuota);
        prbQuota = mView.findViewById(R.id.prbQuota);
        spnQuota = (Spinner) mView.findViewById(R.id.spnQuota);

        lnIpAddress = mView.findViewById(R.id.lnIpAddress);
        btnRefreshQuota = mView.findViewById(R.id.btnRefreshQuota);
        btnRefreshQuota.setOnClickListener(this);
        lnMoHinh = mView.findViewById(R.id.lnMoHinh);
        spnMoHinh = (Spinner) mView.findViewById(R.id.spnMoHinh);
        prbDuongDay = mView.findViewById(R.id.prbDuongDay);
        prbThietBi = mView.findViewById(R.id.prbThietBi);
        spnStaticIp = (Spinner) mView.findViewById(R.id.spnStaticIp);
        prbDeposit = mView.findViewById(R.id.prbDeposit);
        prbStaticIp = mView.findViewById(R.id.prbStaticIp);
        spnDeposit = (Spinner) mView.findViewById(R.id.spnDeposit);
        btnRefreshDeposit = mView.findViewById(R.id.btnRefreshDeposit);
        btnRefreshDeposit.setVisibility(View.GONE);
        btnRefreshDeposit.setOnClickListener(this);
        surveyOnline = TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit;
        area = TabThueBaoHopDongManager.instance.area;
        spnCustType = (Spinner) mView.findViewById(R.id.spnCustType);

        edtNgayHen = (EditText) mView.findViewById(R.id.edtNgayHen);
        edtService = (EditText) mView.findViewById(R.id.edtService);
        edtTechnology = (EditText) mView.findViewById(R.id.edtTechnology);
        prbCuocDongTruoc = mView.findViewById(R.id.prbCuocdongtruoc);
        edtKetCuoi = (EditText) mView.findViewById(R.id.edtKetCuoi);
        edtDuongDay = (EditText) mView.findViewById(R.id.edtDuongDay);
        edtSubType = (EditText) mView.findViewById(R.id.edtSubType);
        edtSubType.setOnClickListener(this);

        edtNgayHen.setOnClickListener(this);
        edtDiaChiLapDat = (EditText) mView.findViewById(R.id.edtDiaChiLapDat);
        edtDiaChiLapDat.setOnClickListener(this);
        edtIsdn = (EditText) mView.findViewById(R.id.edtIsdn);
        edtIsdn.setOnClickListener(this);
        tvcamketso = (TextView) mView.findViewById(R.id.tvcamketso);
        tvcamketso.setOnClickListener(this);
        edtGoiCuoc = (EditText) mView.findViewById(R.id.edtGoiCuoc);
        edtGoiCuoc.setOnClickListener(this);
        edtPromotion = (EditText) mView.findViewById(R.id.edtPromotion);
        edtPromotion.setOnClickListener(this);
        spnCuocDongTruoc = (Spinner) mView.findViewById(R.id.spnCuocDongTruoc);
        prbCustType = mView.findViewById(R.id.prbCustType);
        btnRefreshCustType = mView.findViewById(R.id.btnRefreshCustType);
        prbIsdn = mView.findViewById(R.id.prbIsdn);
        prbIsdn.setVisibility(View.GONE);
        edtHTHM = (EditText) mView.findViewById(R.id.edtHTHM);
        edtHTHM.setOnClickListener(this);
        lnThietBi = mView.findViewById(R.id.lnThietBi);
        lnThietBi.setOnClickListener(this);
        tvThietBi = (TextView) mView.findViewById(R.id.tvThietBi);
        lvUploadImage = (ListView) mView.findViewById(R.id.lvUploadImage);
        prbProfile = mView.findViewById(R.id.prbProfile);
        btnRefreshCustType.setOnClickListener(this);
        edtNgayHen.setText(DateTimeUtils.convertDateTimeToString(new Date(),
                "dd/MM/yyyy"));
        edtKetCuoi.setHint(surveyOnline.getConnectorCode());

        List<ProductCatalogDTO> lst = TabThueBaoHopDongManager.instance.lstProductCatalog;

        boolean isGenAccount = false;
//		for (ProductCatalogDTO item : lst) {
//			for (int i = 0; i < item.getQuantity(); i++) {
//				SubscriberDTO sub = new SubscriberDTO();
//				for (CustomerOrderDetailClone customerOrderDetailClone : TabThueBaoHopDongManager.instance.lstCustomerOrderDetailId){
//					if(item.getTelecomServiceId() == customerOrderDetailClone.getTelecomServiceId()){
//						sub.setServiceType(item.getTelServiceAlias());
//						sub.setTelecomServiceId((long) item.getTelecomServiceId());
//						sub.setServiceTypeName(item.getDescription());
//						break;
//					}
//				}
//				sub.setSubId(TabThueBaoHopDongManager.instance.lstCustomerOrderDetailId.get(i).getSubId());
//				sub.setCustomerOrderDetailId(TabThueBaoHopDongManager.instance.lstCustomerOrderDetailId.get(i).getCustOrderDetailId() + "");
//				lstSub.add(sub);
//				if (!item.isPNService()) {
//					isGenAccount = true;
//				}
//			}
//		}

        for (CustomerOrderDetailClone customerOrderDetailClone : TabThueBaoHopDongManager.instance.lstCustomerOrderDetailId) {
            SubscriberDTO sub = new SubscriberDTO();

            sub.setSubId(customerOrderDetailClone.getSubId());
            sub.setCustomerOrderDetailId(customerOrderDetailClone.getCustOrderDetailId() + "");
            for (ProductCatalogDTO item : lst) {
                if (item.getTelecomServiceId() == customerOrderDetailClone.getTelecomServiceId()) {
                    sub.setServiceType(item.getTelServiceAlias());
                    sub.setTelecomServiceId((long) item.getTelecomServiceId());
                    sub.setServiceTypeName(item.getDescription());
                    break;
                }
            }
            lstSub.add(sub);


//			if ("N".equals(sub.getServiceType()) || "P".equals(sub.getServiceType())) {
//				isGenAccount = false;
//			}else{
//				isGenAccount = true;
//			}
        }


        if (lst.size() > 1) {
            groupType = "2"; //fix groupType = de khong su dung chu ky dien tu
        }

        for (int i = 0; i < lstSub.size(); i++) {
            if ("F".equals(lstSub.get(i).getServiceType())) {
                mainSub = lstSub.get(i);
                // lnPPPOE = mView.findViewById(R.id.lnPPPOE).
                // ;
                // lnPassPPPOE = mView.findViewById(R.id.lnPassPPPOE);
                lstSub.remove(i);
                break;
            }
        }
        if (CommonActivity.isNullOrEmpty(mainSub) || CommonActivity.isNullOrEmpty(mainSub.getServiceType())) {
            mainSub = lstSub.get(0);
            lstSub.remove(0);
        }

        if ("N".equals(mainSub.getServiceType()) || "P".equals(mainSub.getServiceType())) {
            if (CommonActivity.isNullOrEmptyArray(lstSub)) {

            } else {
                String infraType = TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit
                        .getInfraType();
                AsyncGenerateAccounts as = new AsyncGenerateAccounts(act,
                        infraType,
                        TabThueBaoHopDongManager.instance.lstProductCatalog,
                        area.getProvince(),
                        TabThueBaoHopDongManager.instance.custIdentityDTO,
                        onPostGenereAccout);
                as.execute();
            }
        } else {

            String infraType = TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit
                    .getInfraType();
            AsyncGenerateAccounts as = new AsyncGenerateAccounts(act,
                    infraType,
                    TabThueBaoHopDongManager.instance.lstProductCatalog,
                    area.getProvince(),
                    TabThueBaoHopDongManager.instance.custIdentityDTO,
                    onPostGenereAccout);
            as.execute();
        }
        if (CommonActivity.isNullOrEmpty(lstSub)) {
            mView.findViewById(R.id.lnSubAddtional).setVisibility(View.GONE);
        }

        edtService.setHint(mainSub.getServiceTypeName());

        edtTechnology.setHint(TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit
                .getInfratypeName(act));
        spnCuocDongTruoc
                .setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {
                        // if (arg2 == 0) {
                        // mainSub.setCuocDongTruoc(null);
                        // return;
                        // }

                        SubscriberDTO sub = getSub();
                        if (!CommonActivity.isNullOrEmpty(mainSub.getCuocDongTruocKey())) {
                            sub.setCuocDongTruoc(mapPrepaid.get(
                                    mainSub.getCuocDongTruocKey()).get(arg2));
                            ArrayList<ProductPackageFeeDTO> arrProductFee = new ArrayList<ProductPackageFeeDTO>();
                            ProductPackageFeeDTO productPackageFeeDTO = new ProductPackageFeeDTO();
                            productPackageFeeDTO.setName(mapPrepaid.get(
                                    mainSub.getCuocDongTruocKey()).get(arg2).getName());
                            productPackageFeeDTO.setPrice(mapPrepaid.get(
                                    mainSub.getCuocDongTruocKey()).get(arg2).getTotalMoney() + "");
                            arrProductFee.add(productPackageFeeDTO);

                            sub.getHashmapProductPakage().put(sub.getSubId() + "-" + "CDT", arrProductFee);
                            showSelectCuocDongTruoc(mapPrepaid.get(
                                    sub.getCuocDongTruocKey()).get(arg2));
                        } else {
                            sub.getHashmapProductPakage().put(sub.getSubId() + "-" + "CDT", new ArrayList<ProductPackageFeeDTO>());
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });
        spnCustType.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                SubscriberDTO sub = getSub();

                // Lay thong tin han muc
                if ("N".equals(sub.getServiceType())
                        || "P".equals(sub.getServiceType())) {

                    Spinner spinnerQ = spnQuota;
                    View prbQ = prbQuota;
                    View btnRefresh = btnRefreshQuota;
                    if (isShowDialog()) {
                        spinnerQ = spnQuotaD.get(sub.getSubId());
                        prbQ = prbQuotaD.get(sub.getSubId());
                        btnRefresh = btnRefreshQuotaD.get(sub.getSubId());
                    }
                    resetQuota();

                    String custType = "";
                    if (spnCustType.getSelectedItem() != null) {
                        custType = ((OptionSetValueDTO) spnCustType
                                .getSelectedItem()).getValue();
                    }

//                    if (!CommonActivity.isNullOrEmpty(custType)
//                            && !CommonActivity.isNullOrEmpty(sub
//                            .getProductCode())) {
//                        AsynGetQuotaMapByTelecomService quo = new AsynGetQuotaMapByTelecomService(
//                                getActivity(), sub, custType, prbQ, btnRefresh,
//                                spinnerQ);
//                        quo.execute();
//                    }
                }

//				if (!CommonActivity.isNullOrEmpty(sub.getHthm())) {
//					View prbPro = prbPromotion;
//					EditText edtPro = edtPromotion;
//					if (isShowDialog()) {
//						edtPro = edtPromotionD.get(sub.getSubId());
//						prbPro = prbPromotionD.get(sub.getSubId());
//					}
//					GetPromotionByMapAsytask pro = new GetPromotionByMapAsytask(
//							act, sub, ((OptionSetValueDTO) spnCustType
//									.getSelectedItem()).getValue(), area
//									.getProvince(), edtPro, prbPro,
//							TabThongTinThueBao.this);
//					pro.execute();
//				}
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        btnConnectSub = mView.findViewById(R.id.btnConnectSub);
        if (!CommonActivity.isNullOrEmpty(CHECK_CHANGE_TECH)) {
            ((Button) btnConnectSub).setText(getResources().getString(R.string.button_change_tech));
        }
        btnConnectSub.setOnClickListener(this);
        mView.findViewById(R.id.lnSubAddtional).setOnClickListener(this);
        AsynctaskGetListCustType asy = new AsynctaskGetListCustType(act,
                prbCustType, btnRefreshCustType, spnCustType);
        asy.execute();
        edtDiaChiLapDat.setText(area.getFullNamel());
        // if (!"N".equals(mainSub.getServiceType())
        // && !"P".equals(mainSub.getServiceType())) {
        // AsynGenerateGroupCode genAccount = new AsynGenerateGroupCode(act,
        // prbIsdn, mainSub, edtIsdn, area.getProvince());
        // genAccount.execute();
        // }

        if (CommonActivity
                .isNullOrEmpty(TabThueBaoHopDongManager.instance.accountGline)) {
            AsynGenerateGroupCode genAccount = new AsynGenerateGroupCode(act,
                    prbDuongDay, mainSub, edtDuongDay, area.getProvince());
            genAccount.execute(TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit
                    .getInfraType());
        } else {
            edtDuongDay.setHint(TabThueBaoHopDongManager.instance.accountGline);
        }

        // Neu la khach hang cu va ko chon duong day cu
        if (CommonActivity
                .isNullOrEmpty(TabThueBaoHopDongManager.instance.accountGline)
                && TabThueBaoHopDongManager.instance.custIdentityDTO
                .getCustomer().getCustId() != null) {

        }
        // Neu la thue bao GPON, hien thi mo hinh voi tat ca dich vu ko phai A,
        // THS1C
        if ("4".equals(TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit
                .getInfraType())) {
            if (!"A".equals(mainSub.getServiceType())
                    || !"U".equals(mainSub.getServiceType())) {
                lnMoHinh.setVisibility(View.VISIBLE);
            } else {
                lnMoHinh.setVisibility(View.GONE);
            }
        } else {
            lnMoHinh.setVisibility(View.GONE);
        }

        List<Spin> arrMohinh = new ArrayList<Spin>();
        arrMohinh.add(new Spin("ONT", getString(R.string.ont)));
        arrMohinh.add(new Spin("ONT_BRIDGE", getString(R.string.ont_bridge)));
        arrMohinh.add(new Spin("SFU", getString(R.string.sfu)));
        ArrayAdapter<Spin> adapter = new ArrayAdapter<Spin>(act,
                R.layout.spinner_item, R.id.spinner_value, arrMohinh);
        spnMoHinh.setAdapter(adapter);
        spnMoHinh.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                Spin item = (Spin) arg0.getSelectedItem();
                mainSub.setDeployModel(item.getId());

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        // Neu thue bao khong la PSTN hoac NGN hien thi han muc, nguoc lai thi
        // an di
        if ("N".equals(mainSub.getServiceType())
                || "P".equals(mainSub.getServiceType())) {
            lnQuota.setVisibility(View.VISIBLE);
            tvcamketso.setVisibility(View.VISIBLE);
        } else {
            lnQuota.setVisibility(View.GONE);
            tvcamketso.setVisibility(View.GONE);
        }

        spnQuota.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                SubscriberDTO sub = getSub();
                if (arg2 == 0) {
                    mainSub.setQuota("");
//                    sub.getHashmapProductPakage().put(sub.getSubId() + "-" + "HM", new ArrayList<ProductPackageFeeDTO>());
                } else {
                    mainSub.setQuota(mainSub.getLstQuota().get(arg2).getValue());
//                    ArrayList<ProductPackageFeeDTO> arrProductFee = new ArrayList<ProductPackageFeeDTO>();
//                    ProductPackageFeeDTO productPackageFeeDTO = new ProductPackageFeeDTO();
//                    productPackageFeeDTO.setName(getActivity().getString(R.string.qouataname));
//                    productPackageFeeDTO.setPrice(mainSub.getLstQuota().get(arg2).getValue() + "");
//                    arrProductFee.add(productPackageFeeDTO);
//
//                    sub.getHashmapProductPakage().put(sub.getSubId() + "-" + "HM", arrProductFee);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        spnDeposit.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                SubscriberDTO sub = getSub();
                ReasonPledgeDTO reasonPledgeDTO = (ReasonPledgeDTO) spnDeposit
                        .getItemAtPosition(arg2);
                if (reasonPledgeDTO != null && reasonPledgeDTO.getNumMoney() != null) {
                    mainSub.setDeposit((ReasonPledgeDTO) spnDeposit
                            .getItemAtPosition(arg2));
                    ArrayList<ProductPackageFeeDTO> arrProductFee = new ArrayList<ProductPackageFeeDTO>();
                    ProductPackageFeeDTO productPackageFeeDTO = new ProductPackageFeeDTO();
                    productPackageFeeDTO.setName(getActivity().getString(R.string.depositcds));

                    productPackageFeeDTO.setPrice(reasonPledgeDTO.getNumMoney() + "");
                    arrProductFee.add(productPackageFeeDTO);
                    sub.getHashmapProductPakage().put(sub.getSubId() + "-" + "DC", arrProductFee);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    /**
     * Hien thi thong tin chi tiet cuoc dong truoc
     *
     * @param paymentPrePaidPromotionBeans
     */
    private void showSelectCuocDongTruoc(
            PaymentPrePaidPromotionBeans paymentPrePaidPromotionBeans) {

        dialogCuocdongtruoc = new Dialog(act);
        dialogCuocdongtruoc.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogCuocdongtruoc
                .setContentView(R.layout.connection_layout_detail_precharge);

        ListView listdetail = (ListView) dialogCuocdongtruoc
                .findViewById(R.id.listdetail);

        EditText txttencuocdongtruoc = (EditText) dialogCuocdongtruoc
                .findViewById(R.id.txttencuocdongtruoc);
        txttencuocdongtruoc.setText(paymentPrePaidPromotionBeans.getName());

        EditText txtmacuocdongtruoc = (EditText) dialogCuocdongtruoc
                .findViewById(R.id.txtmacuocdongtruoc);
        txtmacuocdongtruoc.setText(paymentPrePaidPromotionBeans
                .getPrePaidCode());
        GetListPaymentDetailChargeAdapter getListPaymentDetailChargeAdapter = new GetListPaymentDetailChargeAdapter(
                paymentPrePaidPromotionBeans.getLstDetailBeans(), act);
        listdetail.setAdapter(getListPaymentDetailChargeAdapter);
        Button btnchon = (Button) dialogCuocdongtruoc
                .findViewById(R.id.btnchon);
        btnchon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialogCuocdongtruoc.dismiss();
            }
        });

        dialogCuocdongtruoc.show();

    }

    private void showDialogListSub() {
        dialogListSub = new Dialog(act);
        dialogListSub.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogListSub.setContentView(R.layout.dialog_list_addtion_sub);

        // dialogListSub.setTitle(R.string.thue_bao_dau_kem);
        ListView lvSub = (ListView) dialogListSub.findViewById(R.id.lvItem);
        SubAddtionalAdapter adapter = new SubAddtionalAdapter(lstSub, act);
        lvSub.setAdapter(adapter);
        lvSub.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                currentSub = lstSub.get(arg2);
                tmpSub = currentSub.cloneSub();

                DialogSubInfo dialogSubInfo = mapDialogSubInfo.get(tmpSub.getSubId());
                {
                    if (dialogSubInfo == null) {
                        dialogSubInfo = new DialogSubInfo(act);
                        mapDialogSubInfo.put(tmpSub.getSubId(), dialogSubInfo);
                    } else {
                        dialogSubInfo.reloadDeployMoldel(mainSub.getDeployModel());
                    }
                }
                dialogSubInfo.show();
            }
        });
        Button btn = (Button) dialogListSub.findViewById(R.id.btnOk);
        btn.setText(getString(R.string.btn_close));
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialogListSub.dismiss();
            }
        });
        dialogListSub.show();
    }

    @Override
    public void onClick(View v) {
        boolean isShowingDialog = false;
        for (SubscriberDTO s : lstSub) {
            if (mapDialogSubInfo.get(s.getSubId()) != null
                    && mapDialogSubInfo.get(s.getSubId()).isShowing()) {
                isShowingDialog = true;
                break;
            }
        }
        SubscriberDTO sub = mainSub;
        if (isShowingDialog) {
            sub = tmpSub;
        }
        if (v == btnSaveSubD.get(sub.getSubId())) {
            if (!validateSub(sub)) {
                return;
            }
            String ip = spnStaticIpD.get(sub.getSubId()).getSelectedItem() + "";
            if (!ip.equals(getString(R.string.not_support_static_ip))) {
                tmpSub.setIp(ip);
            }
            currentSub.copyFormAnotherSub(tmpSub);
            mapDialogSubInfo.get(tmpSub.getSubId()).dismiss();

        } else if (v == btnCloseD.get(sub.getSubId())) {
            mapDialogSubInfo.get(tmpSub.getSubId()).dismiss();
        } else if (v == edtNgayHen) {
            showDatePickerDialog(edtNgayHen);
        } else if (v == edtNgayHenD.get(sub.getSubId())) {
            showDatePickerDialog(edtNgayHenD.get(sub.getSubId()));
        } else if (v == edtGoiCuoc || v == edtGoiCuocD.get(sub.getSubId())) {
            Intent intent = new Intent(getActivity(), FragmentChonGoiCuoc.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("arrGoiCuocKey",
                    mapGoiCuoc.get(sub.getTelecomServiceId()));
            bundle.putLong("telecomServiceIdKey", sub.getTelecomServiceId());
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUEST_CHOOSE_PACKAGE);
        } else if (v == edtHTHM || v == edtHTHMD.get(sub.getSubId())) {
            if (CommonActivity.isNullOrEmpty(sub.getOfferId())
                    || CommonActivity.isNullOrEmpty(sub.getSubType())) {
                CommonActivity.createErrorDialog(act,
                        getString(R.string.chon_goi_cuoc_truoc_chon_HTHM), "1")
                        .show();
                return;
            }
            Intent intent = new Intent(getActivity(), FragmentChonHTHM.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("arrHTHMKey", mapHTHM.get(sub.getOfferId()));
            bundle.putString("offerIdKey", sub.getOfferId() + "");
            bundle.putString("serviceTypeKey", sub.getServiceType());
            bundle.putString("subTypeKey", sub.getSubType());
            bundle.putString("custTypeKey",
                    TabThueBaoHopDongManager.instance.custIdentityDTO
                            .getCustomer().getCustType());
            bundle.putString("payTypeKey", "1");
            bundle.putSerializable("areaObjKey", area);
            if (TabThueBaoHopDongManager.instance.lstProductCatalog.size() == 1) {
                if (TabThueBaoHopDongManager.instance.lstProductCatalog.get(0).getQuantity() == 1) {
                    bundle.putBoolean("isCombo", false);
                } else {
                    bundle.putBoolean("isCombo", true);
                }
            } else {
                bundle.putBoolean("isCombo", true);
            }

            bundle.putString("technology", TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit.getInfraType());
            bundle.putString("CHECK_CHANGE_TECH", CHECK_CHANGE_TECH);
            bundle.putString("subIdKey", sub.getSubId() + "");
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUEST_CHOOSE_HTHM);
        } else if (v == edtSubType || v == edtSubTypeD.get(sub.getSubId())) {
            if (CommonActivity.isNullOrEmpty(sub.getOfferId())) {
                CommonActivity
                        .createErrorDialog(
                                act,
                                getString(R.string.chon_goi_cuoc_truoc_chon_loaiThueBao),
                                "1").show();
                return;
            }
            Intent intent = new Intent(getActivity(),
                    FragmentChooseSubType.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("lstSubTypeKey", lstSubType);
            bundle.putString("productCodeKey", sub.getProductCode());
            bundle.putString("serviceTypeKey", sub.getServiceType());
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUEST_CHOOSE_SUBTYPE);
        } else if (v == edtPromotion || v == edtPromotionD.get(sub.getSubId())) {
//			if (sub.getLstPromotion() == null
//					|| sub.getLstPromotion().size() <= 1) {
//				return;
//			}

            Intent intent = new Intent(getActivity(),
                    FragmentChoosePromotion.class);
            Bundle bundle = new Bundle();
            String custType = "";

            Spinner spinerC = spnCustType;
            if (isShowDialog()) {
                spinerC = spnCustTypeD.get(sub.getSubId());
            }
            if (spinerC.getSelectedItem() != null) {
                custType = ((OptionSetValueDTO) spinerC
                        .getSelectedItem()).getValue();
            }
//			rawData.append("<regType>" + sub.getHthm().getCode() + "</regType>");
//			rawData.append("<offerId>" + sub.getOfferId() + "</offerId>");
//			rawData.append("<serviceType>" + sub.getServiceType()
//					+ "</serviceType>");
//			rawData.append("<custType>" + custType + "</custType>");
//			rawData.append("<payType>" + 1 + "</payType>");
//			rawData.append("<subType>" + sub.getSubType() + "</subType>");
//			rawData.append("<province>" + province + "</province>");
//			bundle.putSerializable("lstPromotionBeansKey",
//					sub.getLstPromotion());
//			bundle.putSerializable("subscriberKey",sub);
            bundle.putString("custType", custType + "");
            bundle.putString("province", area.getProvince() + "");
            if (sub.getHthm() != null) {
                bundle.putString("regType", sub.getHthm().getReasonId());
            }
            bundle.putString("offerId", sub.getOfferId() + "");
            bundle.putString("serviceType", sub.getServiceType());
            bundle.putString("subType", sub.getSubType());
            bundle.putString("productCode", productCode);
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUEST_CHOOSE_PROMOTION);
        } else if (v == lnThietBi || v == lnThietBiD.get(sub.getSubId())) {
            if (CommonActivity.isNullOrEmptyArray(sub.getLstProductType())) {
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notitem), getActivity().getString(R.string.app_name)).show();
                return;
            }
            Intent intent = new Intent(getActivity(), FragmentChonThietBi.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("lstProductTypeKey", sub.getLstProductType());
            bundle.putString("regTypekey", sub.getHthm().getCode());
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUEST_CHON_THIET_BI);
        } else if (v.getId() == R.id.lnSubAddtional) {


            if (!validateSub(mainSub)) {
                return;
            }

            showDialogListSub();
        } else if (v == btnConnectSub) {
            connectSub();
        } else if (v == edtDiaChiLapDat
                || v == edtDiaChiLapDatD.get(sub.getSubId())) {
            String strProvince = "";
            String strDistris = "";

            AreaObj precinct = null;
            if (area != null) {
                strProvince = area.getProvince();
                strDistris = area.getDistrict();
                precinct = AreaBusiness.getAreaFromAreaCode(
                        act,
                        area.getProvince() + area.getDistrict()
                                + area.getPrecinct());
            }
            Bundle mBundle = new Bundle();
            mBundle.putString("strProvince", strProvince);
            mBundle.putString("strDistris", strDistris);
            mBundle.putSerializable("areaPrecint", precinct);
            mBundle.putString("strPrecint", area.getPrecinct());
//			mBundle.putString("strStreetBlock", strStreetBlock1);
            mBundle.putBoolean("isCheckDes", true);
            Intent i = new Intent(act, CreateAddressCommon.class);
            i.putExtras(mBundle);
            startActivityForResult(i, REQUEST_CHOOSE_ADDRESS);
        } else if (v == edtIsdn || v == edtIsdnD.get(sub.getSubId())) {

            if (!CommonActivity.isNullOrEmpty(sub) && !CommonActivity.isNullOrEmpty(sub.getHthm())) {
                if ("N".equals(sub.getServiceType())
                        || "P".equals(sub.getServiceType())) {
                    if (!CommonActivity.isNullOrEmpty(sub)) {
                        EditText edt = edtIsdn;

                        if (isShowingDialog) {
                            edt = edtIsdnD.get(sub.getSubId());
                        }
                        edt.setText("");
                        sub.setIsdn("");
                        AsynctaskGetPNIsdn asy = new AsynctaskGetPNIsdn(act,
                                edt, sub, surveyOnline.getStationId() + "",
                                area.getProvince());
                        asy.execute();
                    }
                }
            } else {
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkregtype), getActivity().getString(R.string.app_name)).show();
            }

        } else if (v == tvcamketso) {

            if ("N".equals(sub.getServiceType())
                    || "P".equals(sub.getServiceType())) {
                if (!CommonActivity.isNullOrEmpty(sub) && CommonActivity.isNullOrEmpty(sub.getProductCode())) {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkofferid), getActivity().getString(R.string.app_name)).show();
                    return;
                }
                if (!CommonActivity.isNullOrEmpty(sub) && CommonActivity.isNullOrEmpty(sub.getHthm())) {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkregtype), getActivity().getString(R.string.app_name)).show();
                    return;
                }
                EditText edt = edtIsdn;

                if (isShowingDialog) {
                    edt = edtIsdnD.get(sub.getSubId());
                }
                if (!CommonActivity.isNullOrEmpty(edt) && CommonActivity.isNullOrEmpty(edt.getText().toString())) {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.isdnemptyNP), getActivity().getString(R.string.app_name)).show();
                    return;
                }

//				TextView txtView = tvcamketso;
//				if (isShowingDialog) {
//					txtView = tvcamketsoD.get(sub.getSubId());
//				}
//				String telecomServiceId, String reasonId, String productCode, String isdn
                GetPriceInServicesMultiplePledgeAsyn getPriceInServicesMultiplePledgeAsyn = new GetPriceInServicesMultiplePledgeAsyn(getActivity(), edt);
                getPriceInServicesMultiplePledgeAsyn.execute(sub.getTelecomServiceId() + "", sub.getHthm().getReasonId() + "", sub.getProductCode(), edt.getText().toString().trim());

            }
        }
        super.onClick(v);
    }

    public void showDatePickerDialog(final EditText edt) {
        final Calendar cal = Calendar.getInstance();
        OnDateSetListener callback = new OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                cal.set(year, monthOfYear, dayOfMonth);
                edt.setText(DateTimeUtils.convertDateTimeToString(
                        cal.getTime(), "dd/MM/yyyy"));
            }
        };

        cal.setTime(DateTimeUtils.convertStringToTime(edt.getText().toString(),
                "dd/MM/yyyy"));
        DatePickerDialog pic = new FixedHoloDatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, callback,
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));
        pic.setTitle(getString(R.string.chon_ngay));
        pic.show();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        boolean isShowingDialog = false;
        for (SubscriberDTO s : lstSub) {
            if (mapDialogSubInfo.get(s.getSubId()) != null
                    && mapDialogSubInfo.get(s.getSubId()).isShowing()) {
                isShowingDialog = true;
                break;
            }
        }
        SubscriberDTO sub = mainSub;
        if (isShowingDialog) {
            sub = tmpSub;
        }
        if (requestCode == REQUEST_CHOOSE_PACKAGE) {
            // Chon goi cuoc
            if (resultCode == Activity.RESULT_OK) {
                mapGoiCuoc.put(sub.getTelecomServiceId(),
                        (ArrayList<ProductOfferingDTO>) data
                                .getSerializableExtra("lstGoiCuocKey"));
                ProductOfferingDTO tmp = (ProductOfferingDTO) data
                        .getSerializableExtra("selectedGoiCuocKey");

                final ProductOfferingDTO objProduct = tmp;
                if (!CommonActivity.isNullOrEmpty(objProduct)) {
                    productCode = objProduct.getProductOfferingId() + "";
                }
                choosePackage(tmp);


            }
        } else if (requestCode == REQUEST_CHOOSE_HTHM) {
            // Chon hinh thuc hoa mang
            if (resultCode == Activity.RESULT_OK) {
//				mapHTHM.put(sub.getOfferId(), (ArrayList<HTHMBeans>) data
//						.getSerializableExtra("arrHthmBeansKey"));
                HTHMBeans hthmTmp = (HTHMBeans) data
                        .getSerializableExtra("selectedHTHMKey");


                if (isShowingDialog) {
                    hthmD.put(tmpSub.getSubId(), hthmTmp);
                    reasonIdD.put(tmpSub.getSubId(), hthmTmp.getReasonId());
                } else {
                    hthm = hthmTmp;
                    reasonId = hthmTmp.getReasonId();
                }

                setHthmInfo(hthmTmp);

            }

        } else if (requestCode == REQUEST_CHOOSE_SUBTYPE) {
            if (resultCode == Activity.RESULT_OK) {
                lstSubType = (ArrayList<SubTypeBeans>) data
                        .getSerializableExtra("lstSubTypeKey");
                SubTypeBeans tmp = (SubTypeBeans) data
                        .getSerializableExtra("selectedSubTypeKey");

                setSubType(tmp);
//                if (isShowingDialog) {
//                    edtSubTypeD.get(sub.getSubId()).setText(tmp.getName());
//                    edtSubType.setText(tmp.getName());
//                } else {
//                    edtSubType.setText(tmp.getName());
//
//                }
//                sub.setSubType(tmp.getSubType());
//                sub.setSubTypeName(tmp.getName());
//                resetRegType();
            }

        } else if (requestCode == REQUEST_CHOOSE_PROMOTION) {
            if (resultCode == Activity.RESULT_OK) {
                PromotionTypeBeans objPromotion = (PromotionTypeBeans) data
                        .getSerializableExtra("selectedPromotionKey");


                final PromotionTypeBeans tmp = objPromotion;
                setPromotion(tmp);
                btnInfoPromotion.setVisibility(View.VISIBLE);
                btnInfoPromotion.setOnClickListener(new View.OnClickListener() {
                    /**
                     * Called when a view has been clicked.
                     *
                     * @param v The view that was clicked.
                     */
                    @Override
                    public void onClick(View v) {
                        DialogDetailAtribute.showDialogDetailPromotion(getActivity(), tmp, productCode);
                    }
                });

            }

        } else if (requestCode == REQUEST_CHON_THIET_BI) {
            if (resultCode == Activity.RESULT_OK) {
                sub.setCheckStock(true);
                sub.setLstProductType((ArrayList<ProductOfferTypeDTO>) data
                        .getSerializableExtra("lstProductTypeKey"));
                ArrayList<ProductPackageFeeDTO> arrProductPackageFeeDTOs = new ArrayList<>();
                String price = data.getStringExtra("priceKey");
                if (CommonActivity.isNullOrEmpty(price)) {
                    price = "0";
                }
                if (!CommonActivity.isNullOrEmpty(sub.getLstProductType())) {
                    for (ProductOfferTypeDTO item : sub.getLstProductType()) {
                        ProductPackageFeeDTO productPackageFeeDTO = new ProductPackageFeeDTO();
                        productPackageFeeDTO.setName(item.getName());
                        productPackageFeeDTO.setPrice(price);
                        arrProductPackageFeeDTOs.add(productPackageFeeDTO);
                    }
                }
                if (!CommonActivity.isNullOrEmptyArray(arrProductPackageFeeDTOs)) {
                    sub.getHashmapProductPakage().put(sub.getSubId() + "-" + "ITEM", arrProductPackageFeeDTOs);
                }
                int msg = R.string.da_chon_thiet_bi;
                if (CommonActivity.isNullOrEmpty(sub.getLstProductType())) {
                    msg = R.string.khong_thiet_bi_dau_kem;
                }
                if (isShowingDialog) {
                    tvThietBiD.get(sub.getSubId()).setText(msg);

                } else {
                    tvThietBi.setText(msg);

                }

            }
        } else if (requestCode == REQUEST_CHOOSE_ADDRESS) {
            if (resultCode == Activity.RESULT_OK) {

//                areaProvicialContract = (AreaObj) data.getExtras().getSerializable("areaProvicial");
//                areaDistristContract = (AreaObj) data.getExtras().getSerializable("areaDistrist");
//
//                areaPrecintContract = (AreaObj) data.getExtras().getSerializable("areaPrecint");
//                areaGroupContract = (AreaObj) data.getExtras().getSerializable("areaGroup");
//
//                areaFlowContract = data.getExtras().getString("areaFlow");
//                areaHomeNumberContract = data.getExtras().getString("areaHomeNumber");


                AreaObj areaprovince = (AreaObj) data.getExtras().getSerializable("areaProvicial");
                AreaObj areaDistrist = (AreaObj) data.getExtras().getSerializable("areaDistrist");
                AreaObj areaPrecint = (AreaObj) data.getExtras().getSerializable(
                        "areaPrecint");

                if (!area.getProvince().equals(areaprovince.getProvince())) {
                    if ("N".equals(sub.getServiceType())
                            || "P".equals(sub.getServiceType())) {
                        EditText edt = edtIsdn;
                        if (isShowingDialog) {
                            edt = edtIsdnD.get(sub.getSubId());
                        }
                        edt.setText("");
                        sub.setIsdn("");
                        if (sub.getHthm() != null) {
                            AsynctaskGetPNIsdn asy = new AsynctaskGetPNIsdn(
                                    act, edt, sub, surveyOnline.getStationId()
                                    + "", area.getProvince());
                            asy.execute();
                        }

                    }
                }

                AreaObj streetBlock = (AreaObj) data.getExtras()
                        .getSerializable("areaGroup");

                String street = data.getExtras().getString("areaFlow");
                String home = data.getExtras().getString("areaHomeNumber");

                String description = data.getExtras().getString("description", "");
                if (!CommonActivity.isNullOrEmpty(description)) {
                    area.setNote(description);
                }
                area.setProvince(areaprovince.getProvince());
                area.setDistrict(areaDistrist.getDistrict());
                area.setPrecinct(areaPrecint.getPrecinct());

                StringBuilder address = new StringBuilder();

                if (!CommonActivity.isNullOrEmpty(home)) {
                    address.append(home).append(" ");
                }
                if (!CommonActivity.isNullOrEmpty(street)) {
                    address.append(street + " ");
                }
                if (streetBlock != null
                        && !CommonActivity.isNullOrEmpty(streetBlock.getName())) {
                    address.append(streetBlock.getName() + " ");
                    area.setStreetBlock(streetBlock.getAreaCode());
                }
                if (!CommonActivity.isNullOrEmpty(areaPrecint)) {
                    address.append(areaPrecint.getName());
                }
                if (!CommonActivity.isNullOrEmpty(areaDistrist)) {
                    address.append(areaDistrist.getName() + " ");
                }
                if (!CommonActivity.isNullOrEmpty(areaprovince)) {
                    address.append(areaprovince.getName() + " ");
                }

                if (isShowingDialog) {
                    edtDiaChiLapDatD.get(sub.getSubId()).setText(address);

                }
                area.setFullNamel(address.toString());
                edtDiaChiLapDat.setText(address);

            }
        } else if (requestCode == CHANNEL_UPDATE_IMAGE.PICK_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                // Utils.updateListPictureProfile(act, data, lvUploadImage,
                // mapProfile, mapFile, sub);

//                Parcelable[] parcelableUris = data
//                        .getParcelableArrayExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);
//
//                if (parcelableUris == null) {
//                    return;
//                }
//                Uri[] uris = new Uri[parcelableUris.length];
//                System.arraycopy(parcelableUris, 0, uris, 0,
//                        parcelableUris.length);
//                ListView lv = lvUploadImage;
//                if (isShowDialog()) {
//                    lv = lvUploadImageD.get(sub.getSubId());
//                }
//                int groupId = data.getExtras().getInt("imageId", -1);
//                if (uris != null && uris.length > 0) {
//                    List<File> lstFile = new ArrayList<File>();
//                    for (Uri uri : uris) {
//                        File file = new File(uri.toString());
//                        if (!file.exists()) {
//                            File mediaStorageDir = new File(
//                                    Environment
//                                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
//                                    "MBBCSCameraApp");
//                            file = new File(mediaStorageDir.getPath()
//                                    + File.separator + file.getName());
//                        }
//                        lstFile.add(file);
//                    }
//                    for (ProfileUpload profile : sub.getLstProfile()) {
//                        if (Integer.parseInt(profile.getId()) == groupId) {
//                            profile.setLstFile(lstFile);
//                            break;
//                        }
//                    }
//                    ProfileAdapter adapter = new ProfileAdapter(getActivity(),
//                            sub.getLstProfile(), onChangeImage);
//                    lv.setAdapter(adapter);
//                    UI.setListViewHeightBasedOnChildren(lvUploadImage);
//                }

                if (isShowingDialog) {
                    profileInfoDialogFragmentD.get(tmpSub.getSubId()).onActivityResult(requestCode, resultCode, data);
                } else
                    profileInfoDialogFragment.onActivityResult(requestCode, resultCode, data);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    // callback function
    @Subscribe
    public void onTemplateSelected(CallbackObj s) {
        if (s != null) {
            loadTemplate(s.map);
        }
    }

    public String getServiceName() {
        return mainSub.getServiceTypeName();
    }

    public void loadTemplate(Map<String, String> map) {
        mMapTemplate = map;
        if (mMapTemplate == null) {
            mMapTemplate = new HashMap<>();
        }
        if (mMapTemplate.size() > 0) {
            mAutoDauNoi = new AutoDauNoi(getActivity(), this, mMapTemplate);
            boolean isShowingDialog = false;
            for (SubscriberDTO s : lstSub) {
                if (mapDialogSubInfo.get(s.getSubId()) != null
                        && mapDialogSubInfo.get(s.getSubId()).isShowing()) {
                    isShowingDialog = true;
                    break;
                }
            }
            SubscriberDTO sub = mainSub;
            if (isShowingDialog) {
                sub = tmpSub;
            }
            mAutoDauNoi.getPackage(mapGoiCuoc.get(sub.getTelecomServiceId()), sub.getTelecomServiceId());
        }
    }

    @Override
    public void packageCallback(ProductOfferingDTO tmp, ArrayList<ProductOfferingDTO> lstGoiCuoc) {

        boolean isShowingDialog = false;
        for (SubscriberDTO s : lstSub) {
            if (mapDialogSubInfo.get(s.getSubId()) != null
                    && mapDialogSubInfo.get(s.getSubId()).isShowing()) {
                isShowingDialog = true;
                break;
            }
        }
        SubscriberDTO sub = mainSub;
        if (isShowingDialog) {
            sub = tmpSub;
        }

        mapGoiCuoc.put(sub.getTelecomServiceId(), lstGoiCuoc);

        choosePackage(tmp);

        // load sub type
        if (mMapTemplate.get(AutoConst.LOAITB) != null) {
            mAutoDauNoi.getSubtype(lstSubType, sub.getProductCode(), sub.getServiceType());
        }
    }

    @Override
    public void subTypeCallback(SubTypeBeans subTypeBeans, ArrayList<SubTypeBeans> lstData) {
        lstSubType = lstData;
        setSubType(subTypeBeans);
        // load hthm
        if (mMapTemplate.get(AutoConst.HTHM) != null) {
            boolean isShowingDialog = false;
            for (SubscriberDTO s : lstSub) {
                if (mapDialogSubInfo.get(s.getSubId()) != null
                        && mapDialogSubInfo.get(s.getSubId()).isShowing()) {
                    isShowingDialog = true;
                    break;
                }
            }
            SubscriberDTO sub = mainSub;
            if (isShowingDialog) {
                sub = tmpSub;
            }

            mAutoDauNoi.getHTHM(mapHTHM.get(sub.getOfferId()),
                    sub.getOfferId() + "",
                    sub.getServiceType(),
                    "1",
                    sub.getSubType(),
                    TabThueBaoHopDongManager.instance.custIdentityDTO
                            .getCustomer().getCustType(),
                    area,
                    TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit.getInfraType());
        }
    }

    @Override
    public void hthmCallback(HTHMBeans hthm) {
        setHthmInfo(hthm);

        // load km
        if (mMapTemplate.get(AutoConst.KM) != null) {
            boolean isShowingDialog = false;
            for (SubscriberDTO s : lstSub) {
                if (mapDialogSubInfo.get(s.getSubId()) != null
                        && mapDialogSubInfo.get(s.getSubId()).isShowing()) {
                    isShowingDialog = true;
                    break;
                }
            }
            SubscriberDTO sub = mainSub;
            if (isShowingDialog) {
                sub = tmpSub;
            }

            String custType = "";

            Spinner spinerC = spnCustType;
            if (isShowDialog()) {
                spinerC = spnCustTypeD.get(sub.getSubId());
            }
            if (spinerC.getSelectedItem() != null) {
                custType = ((OptionSetValueDTO) spinerC
                        .getSelectedItem()).getValue();
            }
            mAutoDauNoi.getPromotion(custType + "",
                    area.getProvince() + "",
                    sub.getHthm().getReasonId(), sub.getOfferId() + "", sub.getServiceType(), sub.getSubType());
        }
    }

    @Override
    public void promotionCallback(PromotionTypeBeans tmp) {
        setPromotion(tmp);
        if (mMapTemplate.get(AutoConst.CDT) != null) {
            AutoCompleteUtil.getInstance(getContext()).setSpinnerSelection(spnCuocDongTruoc, AutoConst.CDT, true);
        }
    }

    private void connectSub() {
        final List<SubscriberDTO> lstTmp = new ArrayList<SubscriberDTO>();
        String ip = spnStaticIp.getSelectedItem() + "";
        if (!ip.equals(getString(R.string.not_support_static_ip))) {
            mainSub.setIp(ip);
        }
        lstTmp.add(0, mainSub);

        lstTmp.addAll(lstSub);

        for (SubscriberDTO sub : lstTmp) {
            if (!validateSub(sub)) {
                return;
            }
        }
        ArrayList<ProductPackageFeeDTO> arrProductPackageFeeDTOs = new ArrayList<>();

        for (SubscriberDTO sub : lstTmp) {
            if (!CommonActivity.isNullOrEmpty(sub) && !CommonActivity.isNullOrEmpty(sub.getHashmapProductPakage())) {
//				sub.getHashmapProductPakage().values().forEach(arrProductPackageFeeDTOs::addAll);
                for (ArrayList<ProductPackageFeeDTO> productPackageFeeDTOs : sub.getHashmapProductPakage().values()) {
                    arrProductPackageFeeDTOs.addAll(productPackageFeeDTOs);
                }
            }
        }

        //hsdt : add fileObj vao profileUpload
        for (SubscriberDTO sub : lstTmp) {
            if (!CommonActivity.isNullOrEmpty(profileBOD.get(sub))) {
                sub.setLstProfile(convertToProfileUpload(profileBOD.get(sub).getHashmapFileObj()));
            } else {
                sub.setLstProfile(convertToProfileUpload(profileBO.getHashmapFileObj()));
            }
        }
        //xong hsdt

        if (!CommonActivity.isNullOrEmptyArray(arrProductPackageFeeDTOs)) {
            showDialogGetFee(arrProductPackageFeeDTOs, lstTmp);
        } else {
            if (CommonActivity.isNullOrEmpty(CHECK_CHANGE_TECH)) {
                OnClickListener onClick = new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        if(CommonActivity.isNullOrEmpty(omniProcessId)) {
                            //lay processId
                            PlaceOrderAsyncTask placeOrderAsyncTask =
                                    new PlaceOrderAsyncTask(getActivity(), new OnPostExecuteListener<String>() {
                                        @Override
                                        public void onPostExecute(String result, String errorCode, String description) {
                                            if ("0".equals(errorCode)) {
                                                // neu thanh cong thi goi ham dk
                                                if (!CommonActivity.isNullOrEmpty(result)) {
                                                    omniProcessId = result;

                                                    // goi ham dang ky o day
                                                    saveTemplate();
                                                    ConnectSubMultiAsyn connect = new ConnectSubMultiAsyn(act, lstTmp,
                                                            surveyOnline, area, btnConnectSub, omniProcessId);
                                                    connect.execute();

                                                } else {
                                                    CommonActivity.createAlertDialog(getActivity(),
                                                            getActivity().getString(R.string.notdataomniprocessid),
                                                            getActivity().getString(R.string.app_name)).show();
                                                }
                                            } else {
                                                CommonActivity.createAlertDialog(getActivity(), description,
                                                        getActivity().getString(R.string.app_name)).show();
                                            }
                                        }
                                    }, null, "HSDT", profileBO.getProfileRecords());


                            placeOrderAsyncTask.execute(custIdentityDTO.getCustomer().getName(), Constant.CONNECT_FIX_LINE);

                            //xong lay processId
                        }else{
                            // da co omniProcessId roi thi khong PlaceOrder nua
                            saveTemplate();
                            ConnectSubMultiAsyn connect = new ConnectSubMultiAsyn(act, lstTmp,
                                    surveyOnline, area, btnConnectSub, omniProcessId);
                            connect.execute();
                        }

                    }
                };
                Dialog dialog = CommonActivity.createDialog(getActivity(),
                        getString(R.string.confirm_connect_sub),
                        getString(R.string.app_name),
                        getResources().getString(R.string.cancel),
                        getString(R.string.ok), null, onClick);
                dialog.show();
            } else {
                if (CommonActivity.isNullOrEmpty(oldSubId)) {
                    CommonActivity
                            .createAlertDialog(getActivity(), getActivity().getString(R.string.not_old_subId),
                                    getActivity().getString(R.string.app_name))
                            .show();
                    return;
                } else {
                    OnClickListener onClick = new OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            saveTemplate();
                            ConnectSubMultiAsyn connect = new ConnectSubMultiAsyn(act, lstTmp,
                                    surveyOnline, area, btnConnectSub, oldSubId, actionCode);
                            connect.execute();
                        }
                    };

                    Dialog dialog = CommonActivity.createDialog(getActivity(),
                            getString(R.string.confirm_connect_sub),
                            getString(R.string.app_name),
                            getResources().getString(R.string.cancel),
                            getString(R.string.ok), null, onClick);
                    dialog.show();
                }
            }
        }
    }

    public void saveTemplate() {
        //added by diepdc

        mMapTemplate.put(AutoConst.SERVICE, mainSub.getServiceTypeName());
        mMapTemplate.put(AutoConst.PACKAGE, edtGoiCuoc.getText().toString());
        mMapTemplate.put(AutoConst.LOAITB, edtSubType.getText().toString());
        mMapTemplate.put(AutoConst.HTHM, edtHTHM.getText().toString());
        mMapTemplate.put(AutoConst.KM, edtPromotion.getText().toString());

        if (spnCuocDongTruoc != null && spnCuocDongTruoc.getSelectedItem() != null) {
            mMapTemplate.put(AutoConst.CDT, spnCuocDongTruoc.getSelectedItem().toString());
        }

        AutoCompleteUtil.getInstance(getActivity())
                .saveTemplate(AutoConst.PREF_DAU_NOI_CO_DINH_MOI_SCREEN, mMapTemplate);
    }

    private boolean validateSub(SubscriberDTO sub) {
        // if (sub.getSubId() != null) {
        // return true;
        // }
        // Kiem tra goi cuoc
        if (sub.getOfferId() == null) {
            CommonActivity.createErrorDialog(
                    act,
                    getString(R.string.chua_chon_goi_cuoc,
                            sub.getServiceTypeName()), "1").show();
            return false;
        }

        // Kiem tra Account
        if (CommonActivity.isNullOrEmpty(sub.getIsdn())) {
            CommonActivity.createErrorDialog(
                    act,
                    getString(R.string.sinh_account_that_bai,
                            sub.getServiceTypeName()), "1").show();
            return false;
        }
        // Kiem tra han muc
        if ("N".equals(sub.getServiceType())
                || "P".equals(sub.getServiceType())) {
            if (!CommonActivity.isNullOrEmpty(sub.getLstQuota())
                    && sub.getLstQuota().size() > 1
                    && CommonActivity.isNullOrEmpty(sub.getQuota())) {

                CommonActivity.createErrorDialog(
                        act,
                        getString(R.string.chon_han_muc,
                                sub.getServiceTypeName()), "1").show();
                return false;
            }

        }

        // Kiem tra loai thue bao
        if (CommonActivity.isNullOrEmpty(sub.getSubType())) {
            CommonActivity.createErrorDialog(
                    act,
                    getString(R.string.chua_chon_sub_type,
                            sub.getServiceTypeName()), "1").show();
            return false;
        }
        // Kiem tra hinh thuc hoa mang
        if (CommonActivity.isNullOrEmpty(sub.getHthm())) {

            CommonActivity
                    .createErrorDialog(
                            act,
                            getString(R.string.chua_chon_hthm,
                                    sub.getServiceTypeName()), "1").show();
            return false;
        }
        // Kiem tra khuyen mai
        if (CommonActivity.isNullOrEmpty(sub.getPromotion())) {

            CommonActivity.createErrorDialog(
                    act,
                    getString(R.string.chua_chon_khuyen_mai,
                            sub.getServiceTypeName()), "1").show();
            return false;
        }
        // Kiem tra cuoc dong truoc
        if (!sub.isGetCDT()
                || (!CommonActivity.isNullOrEmpty(mapPrepaid.get(sub
                .getCuocDongTruocKey()))
                && mapPrepaid.get(sub.getCuocDongTruocKey()).size() > 1 && sub
                .getCuocDongTruoc() == null)) {

            CommonActivity
                    .createErrorDialog(
                            act,
                            getString(R.string.chua_chon_cdt,
                                    sub.getServiceTypeName()), "1").show();
            return false;
        }
        // Kiem tra thiet bi kem theo

        if (!CommonActivity.isNullOrEmpty(sub.getLstProductType())) {

            for (ProductOfferTypeDTO item : sub.getLstProductType()) {
                if (item.getStockModel() == null) {
                    CommonActivity.createErrorDialog(
                            act,
                            getString(R.string.chon_het_thiet_bi,
                                    sub.getServiceTypeName()), "1").show();
                    return false;
                }
            }
        }

        //kiem tra ho so o subMain
        if (CommonActivity.isNullOrEmpty(profileInfoDialogFragment)
                || !profileInfoDialogFragment.isFullFile()) {
            CommonActivity.createErrorDialog(act,
                    getString(R.string.chua_chon_ho_so, sub.getAccount()), "1").show();
            return false;
        }

        //kiem tra ho so o tmpMain
        if (!CommonActivity.isNullOrEmpty(profileInfoDialogFragmentD.get(sub.getSubId()))) {
            if (!profileInfoDialogFragmentD.get(sub.getSubId()).isFullFile()) {
                CommonActivity.createErrorDialog(act,
                        getString(R.string.chua_chon_ho_so, sub.getAccount()), "1").show();
                return false;
            }
        }


        // Kiem tra thong tin hop dong
        if (CommonActivity.isNullOrEmpty(TabThongTinHopDong.accountDTOMain)) {
            CommonActivity.createErrorDialog(act,
                    getString(R.string.chua_chon_hop_dong), "1").show();

            return false;
        }

        return true;

    }

    private boolean validateProfile(SubscriberDTO sub) {

        // Kiem tra goi cuoc
        if (sub.getOfferId() == null) {
            CommonActivity.createErrorDialog(
                    act,
                    getString(R.string.chua_chon_goi_cuoc,
                            sub.getServiceTypeName()), "1").show();
            return false;
        }

        // Kiem tra Account
        if (CommonActivity.isNullOrEmpty(sub.getIsdn())) {
            CommonActivity.createErrorDialog(
                    act,
                    getString(R.string.sinh_account_that_bai,
                            sub.getServiceTypeName()), "1").show();
            return false;
        }
        // Kiem tra han muc
        if ("N".equals(sub.getServiceType())
                || "P".equals(sub.getServiceType())) {
            if (!CommonActivity.isNullOrEmpty(sub.getLstQuota())
                    && sub.getLstQuota().size() > 1
                    && CommonActivity.isNullOrEmpty(sub.getQuota())) {

                CommonActivity.createErrorDialog(
                        act,
                        getString(R.string.chon_han_muc,
                                sub.getServiceTypeName()), "1").show();
                return false;
            }

        }

        // Kiem tra loai thue bao
        if (CommonActivity.isNullOrEmpty(sub.getSubType())) {
            CommonActivity.createErrorDialog(
                    act,
                    getString(R.string.chua_chon_sub_type,
                            sub.getServiceTypeName()), "1").show();
            return false;
        }
        // Kiem tra hinh thuc hoa mang
        if (CommonActivity.isNullOrEmpty(sub.getHthm())) {

            CommonActivity
                    .createErrorDialog(
                            act,
                            getString(R.string.chua_chon_hthm,
                                    sub.getServiceTypeName()), "1").show();
            return false;
        }
        // Kiem tra khuyen mai
        if (CommonActivity.isNullOrEmpty(sub.getPromotion())) {

            CommonActivity.createErrorDialog(
                    act,
                    getString(R.string.chua_chon_khuyen_mai,
                            sub.getServiceTypeName()), "1").show();
            return false;
        }
        // Kiem tra cuoc dong truoc
        if (!sub.isGetCDT()
                || (!CommonActivity.isNullOrEmpty(mapPrepaid.get(sub
                .getCuocDongTruocKey()))
                && mapPrepaid.get(sub.getCuocDongTruocKey()).size() > 1 && sub
                .getCuocDongTruoc() == null)) {

            CommonActivity
                    .createErrorDialog(
                            act,
                            getString(R.string.chua_chon_cdt,
                                    sub.getServiceTypeName()), "1").show();
            return false;
        }
        // Kiem tra thiet bi kem theo

        if (!CommonActivity.isNullOrEmpty(sub.getLstProductType())) {

            for (ProductOfferTypeDTO item : sub.getLstProductType()) {
                if (item.getStockModel() == null) {
                    CommonActivity.createErrorDialog(
                            act,
                            getString(R.string.chon_het_thiet_bi,
                                    sub.getServiceTypeName()), "1").show();
                    return false;
                }
            }
        }


        // Kiem tra thong tin hop dong
        if (CommonActivity.isNullOrEmpty(TabThongTinHopDong.accountDTOMain)) {
            CommonActivity.createErrorDialog(act,
                    getString(R.string.chua_chon_hop_dong), "1").show();

            return false;
        }

        return true;

    }

    private SubscriberDTO getSub() {
        // boolean isShowingDialog = dialogSubInfo != null
        // && dialogSubInfo.isShowing();
        SubscriberDTO sub = mainSub;
        if (isShowDialog()) {
            sub = tmpSub;
        }
        return sub;
    }

    private boolean isShowDialog() {
        boolean isShowingDialog = false;
        for (SubscriberDTO s : lstSub) {
            if (mapDialogSubInfo.get(s.getSubId()) != null
                    && mapDialogSubInfo.get(s.getSubId()).isShowing()) {
                isShowingDialog = true;
                break;
            }
        }
        return isShowingDialog;
    }

    private void resetRegType() {
        SubscriberDTO sub = getSub();
        sub.setHthm(null);
        if (isShowDialog()) {
            edtHTHMD.get(sub.getSubId()).setText(R.string.chonhthm);
        } else {
            edtHTHM.setText(R.string.chonhthm);
        }
        // mapProfile.get(sub.getSubId()).clear();
        // mapFile.get(sub.getSubId()).clear();
        ListView lvImage = lvUploadImage;
        if (isShowDialog()) {
            lvImage = lvUploadImageD.get(sub.getSubId());

        }
        lvImage.setVisibility(View.GONE);
    }

    private void resetPromotion() {
        SubscriberDTO sub = getSub();
        sub.setPromotion(null);
        sub.setLstPromotion(null);
        if (isShowDialog()) {
            edtPromotionD.get(sub.getSubId()).setText(R.string.chonctkm1);
        } else {
            edtPromotion.setText(R.string.chonctkm1);
        }
    }

    private void resetPrepaid() {
        SubscriberDTO sub = getSub();
        sub.setCuocDongTruoc(null);
        sub.setGetCDT(false);
        PaymentPrePaidPromotionBeans paymentPrePaidPromotionBeans = new PaymentPrePaidPromotionBeans();
        paymentPrePaidPromotionBeans.setName(getString(R.string.txt_select));
        paymentPrePaidPromotionBeans.setPrePaidCode("");
        // List<String> lst = new
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(act,
                R.layout.spinner_item, R.id.spinner_value);
        if (isShowDialog()) {
            spnCuocDongTruocD.get(sub.getSubId()).setAdapter(adapter);
        } else {
            spnCuocDongTruoc.setAdapter(adapter);
        }
    }

    private void resetDevice() {
        SubscriberDTO sub = getSub();
        sub.setLstProductType(null);
        if (isShowDialog()) {
            tvThietBiD.get(sub.getSubId()).setText(R.string.danh_sach_thiet_bi);
        } else {
            tvThietBi.setText(R.string.danh_sach_thiet_bi);
        }
    }

    private void resetQuota() {
        SubscriberDTO sub = getSub();
        Spinner spinnerQ = spnQuota;
        if (isShowDialog()) {
            spinnerQ = spnQuotaD.get(sub.getSubId());
        }
        spinnerQ.setAdapter(new ArrayAdapter<OptionSetValueDTO>(getActivity(),
                R.layout.spinner_item, R.id.spinner_value, sub.getLstQuota()));

    }

    public void getCDT() {
        Spinner spn;
        View prb;
        SubscriberDTO sub = getSub();
        if (isShowDialog()) {
            spn = spnCuocDongTruocD.get(sub.getSubId());
            prb = prbCuocDongTruocD.get(sub.getSubId());
        } else {
            spn = spnCuocDongTruoc;
            prb = prbCuocDongTruoc;
        }
        String areaCode = area.getProvince();
        if (!CommonActivity.isNullOrEmpty(area.getDistrict())) {
            areaCode = areaCode + area.getDistrict();
        }
        if (!CommonActivity.isNullOrEmpty(area.getPrecinct())) {
            areaCode = areaCode + area.getPrecinct();
        }

        GetAllListPaymentPrePaidAsyn asy = new GetAllListPaymentPrePaidAsyn(
                act, sub, spn, prb, areaCode, mapPrepaid);
        asy.execute();
    }

    // show thong tin cam ket
    private void showDialogViewDataStock(ProductOfferPriceDTO productOfferPriceDTO, String isdn) {

        final Dialog dialogViewCommitment = new Dialog(getActivity());
        dialogViewCommitment.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogViewCommitment.setContentView(R.layout.dialog_layout_commitment_bccs);

        TextView tvDialogTitle = (TextView) dialogViewCommitment.findViewById(R.id.tvDialogTitle);
        tvDialogTitle.setText(getActivity().getString(R.string.ttincamketso));
        TextView tvIsdn = (TextView) dialogViewCommitment.findViewById(R.id.tvIsdn);
        tvIsdn.setText(isdn);
        TextView tvmoney = (TextView) dialogViewCommitment.findViewById(R.id.tvmoney);
        tvmoney.setText(StringUtils.formatMoney(productOfferPriceDTO.getPrice()));

        TextView tvmoneycamket = (TextView) dialogViewCommitment.findViewById(R.id.tvmoneycamket);
        tvmoneycamket.setText(StringUtils.formatMoney(productOfferPriceDTO.getPricePledgeAmount()));

        // TextView tvmoneycamketungtruoc = (TextView)
        // dialogViewCommitment.findViewById(R.id.tvmoneycamketungtruoc);
        // tvmoneycamketungtruoc.setText(StringUtils.formatMoney(stockNumberDTO.getPricePriorPay()));

        TextView tvcamketthang = (TextView) dialogViewCommitment.findViewById(R.id.tvcamketthang);
        tvcamketthang.setText(productOfferPriceDTO.getPledgeTime());

        TextView tvpricevat = (TextView) dialogViewCommitment.findViewById(R.id.tvpricevat);
        tvpricevat.setText(productOfferPriceDTO.getPriceVat() + "%");
        dialogViewCommitment.findViewById(R.id.btnclose).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialogViewCommitment.dismiss();

            }
        });

        dialogViewCommitment.show();

    }

    private void showDialogGetFee(ArrayList<ProductPackageFeeDTO> arrProductPakage, final List<SubscriberDTO> lstTmp) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_view_fee_cd);
        dialog.setCancelable(false);
        ListView listView = (ListView) dialog.findViewById(R.id.listView);
        GetFeeBCCSAdapter getFeeBCCSAdapter = new GetFeeBCCSAdapter(arrProductPakage, getActivity());
        listView.setAdapter(getFeeBCCSAdapter);

        TextView tvSum = (TextView) dialog.findViewById(R.id.tvSummoney);
        Long sum = 0L;
        if (!CommonActivity.isNullOrEmptyArray(arrProductPakage)) {
            for (ProductPackageFeeDTO item : arrProductPakage) {
                if (!CommonActivity.isNullOrEmpty(item.getPrice()) && !"null".equals(item.getPrice())) {
                    sum = sum + Long.parseLong(item.getPrice());
                }
            }
        }
        tvSum.setText(getActivity().getString(R.string.summoney, StringUtils.formatMoney(sum.toString())));


        dialog.findViewById(R.id.btnok)
                .setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                            if (CommonActivity.isNullOrEmpty(CHECK_CHANGE_TECH)) {
                                                if(CommonActivity.isNullOrEmpty(omniProcessId)) {
                                                    //lay processId
                                                    PlaceOrderAsyncTask placeOrderAsyncTask =
                                                            new PlaceOrderAsyncTask(getActivity(), new OnPostExecuteListener<String>() {
                                                                @Override
                                                                public void onPostExecute(String result, String errorCode, String description) {
                                                                    if ("0".equals(errorCode)) {
                                                                        // neu thanh cong thi goi ham dk
                                                                        if (!CommonActivity.isNullOrEmpty(result)) {
                                                                            omniProcessId = result;

                                                                            //goi ham dau noi
                                                                            saveTemplate();
                                                                            ConnectSubMultiAsyn connect = new ConnectSubMultiAsyn(act, lstTmp,
                                                                                    surveyOnline, area, btnConnectSub, omniProcessId);
                                                                            connect.execute();
                                                                        } else {
                                                                            CommonActivity.createAlertDialog(getActivity(),
                                                                                    getActivity().getString(R.string.notdataomniprocessid),
                                                                                    getActivity().getString(R.string.app_name)).show();
                                                                        }
                                                                    } else {
                                                                        CommonActivity.createAlertDialog(getActivity(), description,
                                                                                getActivity().getString(R.string.app_name)).show();
                                                                    }
                                                                }
                                                            }, null, "HSDT", profileBO.getProfileRecords());


                                                    placeOrderAsyncTask.execute(custIdentityDTO.getCustomer().getName(), Constant.ORD_TYPE_REGISTER_PREPAID);

                                                    //xong lay processId
                                                }else{
                                                    //da co omniProcessId roi
                                                    saveTemplate();
                                                    ConnectSubMultiAsyn connect = new ConnectSubMultiAsyn(act, lstTmp,
                                                            surveyOnline, area, btnConnectSub, omniProcessId);
                                                    connect.execute();

                                                }
                                            } else {
                                                if (CommonActivity.isNullOrEmpty(oldSubId)) {
                                                    dialog.cancel();
                                                    CommonActivity
                                                            .createAlertDialog(getActivity(), getActivity().getString(R.string.not_old_subId),
                                                                    getActivity().getString(R.string.app_name))
                                                            .show();
                                                    return;
                                                } else {
                                                    ConnectSubMultiAsyn connect = new ConnectSubMultiAsyn(act, lstTmp,
                                                            surveyOnline, area, btnConnectSub, oldSubId, actionCode);
                                                    connect.execute();
                                                }
                                            }
                                        }
                                    }
                );

        dialog.findViewById(R.id.btncancel).

                setOnClickListener(new OnClickListener() {

                                       @Override
                                       public void onClick(View arg0) {
                                           dialog.cancel();
                                       }
                                   }

                );
        dialog.show();
    }

    private void choosePackage(ProductOfferingDTO tmp) {
        boolean isShowingDialog = false;
        for (SubscriberDTO s : lstSub) {
            if (mapDialogSubInfo.get(s.getSubId()) != null
                    && mapDialogSubInfo.get(s.getSubId()).isShowing()) {
                isShowingDialog = true;
                break;
            }
        }
        SubscriberDTO sub = mainSub;
        if (isShowingDialog) {
            sub = tmpSub;
        }
        if (isShowingDialog) {
            edtGoiCuocD.get(sub.getSubId()).setText(
                    tmp.getCode() + " - " + tmp.getName());
            if ("N".equals(sub.getServiceType())
                    || "P".equals(sub.getServiceType())) {
                edtIsdnD.get(sub.getSubId()).setText("");
            }
        } else {
            edtGoiCuoc.setText(tmp.getCode() + " - " + tmp.getName());
        }
        if (sub.getOfferId() == null
                || sub.getOfferId().compareTo(
                tmp.getProductOfferingId()) != 0) {
            sub.setIp("");
            sub.setQuota("");
            sub.setLstQuota(new ArrayList<OptionSetValueDTO>());
            sub.setHashmapProductPakage(new HashMap<String, ArrayList<ProductPackageFeeDTO>>());
            sub.setLstIsdn(null);
            sub.setOfferId(tmp.getProductOfferingId());
            sub.setProductCode(tmp.getCode());
            resetRegType();
            resetPromotion();
            resetPrepaid();
            resetDevice();
            Spinner spinner = spnStaticIp;
            View prb = prbStaticIp;
            View ln = lnIpAddress;
            if (isShowingDialog) {
                spinner = spnStaticIpD.get(sub.getSubId());
                prb = prbStaticIpD.get(sub.getSubId());
                ln = lnIpAddressD.get(sub.getSubId());
            }
            ln.setVisibility(View.GONE);
            GetIpAddressAsyncTask getIp = new GetIpAddressAsyncTask(
                    act, spinner, sub, mapIpAddress, area, prb, ln);
            getIp.execute();

            // Lay thong tin han muc
            if ("N".equals(sub.getServiceType())
                    || "P".equals(sub.getServiceType())) {

                Spinner spinnerQ = spnQuota;
                View prbQ = prbQuota;
                View btnRefresh = btnRefreshQuota;
                if (isShowingDialog) {
                    spinnerQ = spnQuotaD.get(sub.getSubId());
                    prbQ = prbQuotaD.get(sub.getSubId());
                    btnRefresh = btnRefreshQuotaD.get(sub.getSubId());
                }
                resetQuota();

                String custType = "";
                if (spnCustType.getSelectedItem() != null) {
                    custType = ((OptionSetValueDTO) spnCustType
                            .getSelectedItem()).getValue();
                }

                if (!CommonActivity.isNullOrEmpty(custType)) {
                    AsynGetQuotaMapByTelecomService quo = new AsynGetQuotaMapByTelecomService(
                            getActivity(), sub, custType, prbQ,
                            btnRefresh, spinnerQ);
                    quo.execute();
                }
            }
        }
    }

    private void setSubType(SubTypeBeans tmp) {
        boolean isShowingDialog = false;
        for (SubscriberDTO s : lstSub) {
            if (mapDialogSubInfo.get(s.getSubId()) != null
                    && mapDialogSubInfo.get(s.getSubId()).isShowing()) {
                isShowingDialog = true;
                break;
            }
        }
        SubscriberDTO sub = mainSub;
        if (isShowingDialog) {
            sub = tmpSub;
        }
        if (isShowingDialog) {
            edtSubTypeD.get(sub.getSubId()).setText(tmp.getName());
            edtSubType.setText(tmp.getName());
        } else {
            edtSubType.setText(tmp.getName());

        }
        sub.setSubType(tmp.getSubType());
        sub.setSubTypeName(tmp.getName());
        resetRegType();
    }

    private void setHthmInfo(HTHMBeans hthm) {
        boolean isShowingDialog = false;
        for (SubscriberDTO s : lstSub) {
            if (mapDialogSubInfo.get(s.getSubId()) != null
                    && mapDialogSubInfo.get(s.getSubId()).isShowing()) {
                isShowingDialog = true;
                break;
            }
        }
        SubscriberDTO sub = mainSub;
        if (isShowingDialog) {
            sub = tmpSub;
        }

        Spinner spinner = spnStaticIp;
        View prbIp = prbStaticIp;
        View ln = lnIpAddress;
        if (isShowingDialog) {
            spinner = spnStaticIpD.get(sub.getSubId());
            prbIp = prbStaticIpD.get(sub.getSubId());
            ln = lnIpAddressD.get(sub.getSubId());
        }
        GetIpAddressAsyncTask getIp = new GetIpAddressAsyncTask(
                act, spinner, sub, mapIpAddress, area, prbIp, ln);
        getIp.execute();

        // Lay thong tin han muc
        if ("N".equals(sub.getServiceType())
                || "P".equals(sub.getServiceType())) {

            Spinner spinnerQ = spnQuota;
            View prbQ = prbQuota;
            View btnRefresh = btnRefreshQuota;
            if (isShowingDialog) {
                spinnerQ = spnQuotaD.get(sub.getSubId());
                prbQ = prbQuotaD.get(sub.getSubId());
                btnRefresh = btnRefreshQuotaD.get(sub.getSubId());
            }
            resetQuota();

            String custType = "";
            if (spnCustType.getSelectedItem() != null) {
                custType = ((OptionSetValueDTO) spnCustType
                        .getSelectedItem()).getValue();
            }

            if (!CommonActivity.isNullOrEmpty(custType)) {
                AsynGetQuotaMapByTelecomService quo = new AsynGetQuotaMapByTelecomService(
                        getActivity(), sub, custType, prbQ,
                        btnRefresh, spinnerQ);
                quo.execute();
            }
        }
//				GetReasonCharacterAsyn getReasonCharacterAsyn = new GetReasonCharacterAsyn(getActivity(),hthm ,sub);
//				getReasonCharacterAsyn.execute(hthm.getReasonId());

        if (isShowingDialog) {
            edtHTHMD.get(sub.getSubId()).setText(
                    hthm.getCode() + " - " + hthm.getName());
        } else {
            edtHTHM.setText(hthm.getCode() + " - " + hthm.getName());
        }

        FindFeeByReasonTeleIdAsyn findFeeByReasonTeleIdAsyn = new FindFeeByReasonTeleIdAsyn(getActivity(), sub);
        findFeeByReasonTeleIdAsyn.execute(sub.getTelecomServiceId() + "", hthm.getReasonId(), sub.getProductCode());

        if (sub.getHthm() == null
                || !sub.getHthm().getReasonId()
                .equals(hthm.getReasonId())) {
            if ("N".equals(sub.getServiceType())
                    || "P".equals(sub.getServiceType())) {
                EditText edt = edtIsdn;

                if (isShowingDialog) {
                    edt = edtIsdnD.get(sub.getSubId());
                }
                edt.setText("");
                sub.setIsdn("");
                AsynctaskGetPNIsdn asy = new AsynctaskGetPNIsdn(act,
                        edt, sub, surveyOnline.getStationId() + "",
                        area.getProvince());
                asy.execute();

            }


            sub.setHthm(hthm);
            SharedPreferences preferences = getActivity().getSharedPreferences(Define.PRE_NAME, Activity.MODE_PRIVATE);
            String name = preferences.getString(Define.KEY_MENU_NAME, "");


            if (name.contains(";menu.deposit.cd;")) {
                Spinner spn = spnDeposit;
                View prbDP = prbDeposit;
                LinearLayout lnDatcoc = (LinearLayout) mView.findViewById(R.id.lnDatCoc);
                // Neu HTHM cau hinh co dat coc
                if (isShowingDialog) {
                    lnDatcoc = (LinearLayout) mapDialogSubInfo.get(sub.getSubId())
                            .findViewById(R.id.lnDatCoc);
//							mapDialogSubInfo.get(sub.getSubId())
//									.findViewById(R.id.lnDatCoc)
//									.setVisibility(View.VISIBLE);M
                    spn = spnDepositD.get(sub.getSubId());
                    prbDP = prbDepositD.get(sub.getSubId());
                } else {
//							mView.findViewById(R.id.lnDatCoc).setVisibility(
//									View.VISIBLE);

                }
                GetDepositAsyncTask deposit = new GetDepositAsyncTask(
                        act, spn, sub, prbDP, lnDatcoc);
                deposit.execute();
            } else {
                if (isShowingDialog) {
                    mapDialogSubInfo.get(sub.getSubId())
                            .findViewById(R.id.lnDatCoc)
                            .setVisibility(View.GONE);
                } else {
                    mView.findViewById(R.id.lnDatCoc).setVisibility(
                            View.GONE);
                }
                sub.setReasonPledgeDTO(null);
            }
            ListView lvImage = lvUploadImage;
            View prb = prbProfile;
            View prbPro = prbPromotion;
            EditText edtPro = edtPromotion;
            TextView tvDevice = tvThietBi;
            View prbDevice = prbThietBi;
            if (isShowingDialog) {
                lvImage = lvUploadImageD.get(sub.getSubId());
                prb = prbProfileD.get(sub.getSubId());
                edtPro = edtPromotionD.get(sub.getSubId());
                prbPro = prbPromotionD.get(sub.getSubId());
                tvDevice = tvThietBiD.get(sub.getSubId());
                prbDevice = prbThietBiD.get(sub.getSubId());

            }

            //lay hthm theo hsdt
            SubscriberDTO subscriberDTO = null;

            if (isShowingDialog) {
                subscriberDTO = tmpSub;
                if (!CommonActivity.isNullOrEmpty(reasonIdD.get(tmpSub.getSubId()))) {
//                    profileBOD.get(tmpSub.getSubId()).clearData();
                    new AsynTaskGetListRecordProfile(getActivity(), new OnPostExecuteListener<Map<String, ArrayList<RecordPrepaid>>>() {
                        @Override
                        public void onPostExecute(Map<String, ArrayList<RecordPrepaid>> result, String errorCode, String description) {

                            if (CommonActivity.isNullOrEmpty(result)) {
                                lnSelectProfileSubOther.setVisibility(View.GONE);
                                profileBOD.get(tmpSub.getSubId()).setRequiredUploadImage(false);
                            } else {
                                lnSelectProfileSubOther.setVisibility(View.VISIBLE);
                                profileBOD.get(tmpSub.getSubId()).setMapListRecordPrepaid(result);
                            }

                        }

                    }, null, initProfileBOSubOther(subscriberDTO)).execute();
                } else {
                    profileBOD.get(tmpSub.getSubId()).getHashmapFileObj().clear();
                }

            } else {
                subscriberDTO = mainSub;
                if (!CommonActivity.isNullOrEmpty(reasonId)) {
                    if(profileBO != null){
                        profileBO.clearData();
                    }
                    new AsynTaskGetListRecordProfile(getActivity(), new OnPostExecuteListener<Map<String, ArrayList<RecordPrepaid>>>() {
                        @Override
                        public void onPostExecute(Map<String, ArrayList<RecordPrepaid>> result, String errorCode, String description) {

                            if (CommonActivity.isNullOrEmpty(result)) {
                                lnSelectProfile.setVisibility(View.GONE);
                                profileBO.setRequiredUploadImage(false);
                            } else {
                                lnSelectProfile.setVisibility(View.VISIBLE);
                                profileBO.setMapListRecordPrepaid(result);
                            }
                        }

                    }, null, initProfileBO(subscriberDTO)).execute();
                } else {
                    profileBO.getHashmapFileObj().clear();
                }


            }


            resetPromotion();

//					String custType = "";
//					if (spnCustType.getSelectedItem() != null) {
//						custType = ((OptionSetValueDTO) spnCustType
//								.getSelectedItem()).getValue();
//					}
//					if (!CommonActivity.isNullOrEmpty(custType)) {
//						GetPromotionByMapAsytask pro = new GetPromotionByMapAsytask(
//								act, sub, custType, area.getProvince(), edtPro,
//								prbPro, TabThongTinThueBao.this);
//						pro.execute();
//					}
            // Lay thong tin
            resetPrepaid();
            resetDevice();

            GetListStockTypeAsynTask stockType = new GetListStockTypeAsynTask(
                    act, sub, tvDevice, prbDevice);
            stockType.execute();
        }
    }

    private void setPromotion(PromotionTypeBeans tmp) {
        boolean isShowingDialog = false;
        for (SubscriberDTO s : lstSub) {
            if (mapDialogSubInfo.get(s.getSubId()) != null
                    && mapDialogSubInfo.get(s.getSubId()).isShowing()) {
                isShowingDialog = true;
                break;
            }
        }
        SubscriberDTO sub = mainSub;
        if (isShowingDialog) {
            sub = tmpSub;
        }

        if (isShowingDialog) {
            edtPromotionD.get(sub.getSubId())
                    .setText(tmp.getCodeName());
        } else {
            edtPromotion.setText(tmp.getCodeName());

        }
        sub.setPromotion(tmp);

        getCDT();
    }

    public void zoomImageFromThumb(final View thumbView, String path
            , FrameLayout frlMain, final ImageView expandedImageView) {
        final int mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        Bitmap myBitmap = BitmapFactory.decodeFile(path);

        // Load the high-resolution "zoomed-in" image.
        expandedImageView.setImageBitmap(myBitmap);
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        frlMain.getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f))
                .with(ObjectAnimator.ofFloat(expandedImageView,
                        View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.Y, startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }

    private String getXmlCus(CustIdentityDTO custIdentityDTO) {

        StringBuilder stringBuilder = new StringBuilder("");
        String address = area.getName();
        CustomerDTO oldCus = custIdentityDTO.getCustomer();
        boolean isCustomerOld = false;
        if(!CommonActivity.isNullOrEmpty(oldCus.getCustId())){
            isCustomerOld = true;
        }

        if (oldCus != null) {
            address = oldCus.getAddress();
            stringBuilder.append("<createUser>" + oldCus.getCreateUser()
                    + "</createUser>");
            stringBuilder.append("<createDatetime>"
                    + oldCus.getCreateDatetime() + "</createDatetime>");
            stringBuilder.append("<updateDatetime>"
                    + oldCus.getUpdateDatetime() + "</updateDatetime>");
            if (oldCus.getBirthDate().length() > 0) {
                Date date = DateTimeUtils.convertStringToTime(oldCus.getBirthDate(), "dd/MM/yyyy");
                stringBuilder.append("<birthDate>"
                        + DateTimeUtils.convertDateSendOverSoap(date)
                        + "</birthDate>");
            } else {
                stringBuilder.append("<birthDate>" + oldCus.getBirthDate() + "</birthDate>");
            }
            stringBuilder.append("<custId>" + oldCus.getCustId());
            stringBuilder.append("</custId>");

            stringBuilder.append("<updateCustIdentity>" + false);
            stringBuilder.append("</updateCustIdentity>");

            stringBuilder.append("<isNewCustomer>" + false);
            stringBuilder.append("</isNewCustomer>");
        } else {
            Date date = DateTimeUtils.convertStringToTime(oldCus.getBirthDate(),
                    "dd/MM/yyyy");
            stringBuilder.append("<birthDate>"
                    + DateTimeUtils.convertDateSendOverSoap(date)
                    + "</birthDate>");
            stringBuilder.append("<custId>" + null);
            stringBuilder.append("</custId>");

            stringBuilder.append("<updateCustIdentity>" + true);
            stringBuilder.append("</updateCustIdentity>");

            stringBuilder.append("<isNewCustomer>" + true);
            stringBuilder.append("</isNewCustomer>");

        }
        stringBuilder.append("<address>" + address + "</address>");
        stringBuilder.append("<district>" + area.getDistrict() + "</district>");// Quan
        // huyen
        stringBuilder.append("<precinct>" + area.getPrecinct() + "</precinct>");// phuong
        stringBuilder.append("<province>" + area.getProvince() + "</province>");// tinh

        if (!CommonActivity.isNullOrEmpty(area.getStreetBlock())) {
            stringBuilder.append("<streetBlock>" + area.getStreetBlock() + "</streetBlock>");
            stringBuilder.append("<areaCode>" + area.getProvince() + area.getDistrict() + area.getPrecinct() + area.getStreetBlock()
                    + "</areaCode>");
        } else {
            stringBuilder.append("<areaCode>" + area.getProvince() + area.getDistrict() + area.getPrecinct()
                    + "</areaCode>");
        }
        // tinh
        if (!CommonActivity.isNullOrEmpty(area.getStreet())) {
            stringBuilder.append("<streetName>"
                    + StringUtils.xmlEscapeText(area.getStreet()) + "</streetName>");
        }
        stringBuilder.append("<home>"
                + StringUtils.xmlEscapeText(area.getHome()) + "</home>");
        stringBuilder.append("<description>"
                + StringUtils.xmlEscapeText(oldCus.getDescription())
                + "</description>");

        stringBuilder.append("<custId>" + oldCus.getCustId() + "</custId>");
        stringBuilder.append("<custType>VIE</custType>");


    if(isCustomerOld) {
        //khach hang cu
    stringBuilder.append("<custIdentityDTO>");
    stringBuilder.append("<custId>" + oldCus.getCustId() + "</custId>");

    Date date = DateTimeUtils
            .convertStringToTime(oldCus.getListCustIdentity().get(0).getIdIssueDate(), "dd/MM/yyyy");
    stringBuilder.append("<idIssueDate>"
            + DateTimeUtils.convertDateSendOverSoap(date)
            + "</idIssueDate>");
    if (!TextUtils.isEmpty(oldCus.getListCustIdentity().get(0).getIdExpireDate())) {
        Date dateExpire = DateTimeUtils
                .convertStringToTime(oldCus.getListCustIdentity().get(0).getIdExpireDate(), "dd/MM/yyyy");
        stringBuilder.append("<idExpireDate>"
                + DateTimeUtils.convertDateSendOverSoap(dateExpire)
                + "</idExpireDate>");
    }

    stringBuilder.append("<idIssuePlace>"
            + StringUtils.xmlEscapeText(oldCus.getListCustIdentity().get(0).getIdIssuePlace()) + "</idIssuePlace>");
    stringBuilder.append("<idNo>" + oldCus.getListCustIdentity().get(0).getIdNo() + "</idNo>");
    stringBuilder.append("<idType>" + oldCus.getListCustIdentity().get(0).getIdType() + "</idType>");
    stringBuilder.append("</custIdentityDTO>");

    stringBuilder.append("<listCustIdentity>");
    stringBuilder.append("<custId>" + oldCus.getCustId() + "</custId>");
    date = DateTimeUtils.convertStringToTime(oldCus.getListCustIdentity().get(0).getIdIssueDate(), "dd/MM/yyyy");
    stringBuilder.append("<idIssueDate>"
            + DateTimeUtils.convertDateSendOverSoap(date)
            + "</idIssueDate>");
    stringBuilder.append("<idIssuePlace>"
            + StringUtils.xmlEscapeText(oldCus.getListCustIdentity().get(0).getIdIssueDate()) + "</idIssuePlace>");
    stringBuilder.append("<idNo>" + oldCus.getListCustIdentity().get(0).getIdNo() + "</idNo>");
    stringBuilder.append("<idType>" + oldCus.getListCustIdentity().get(0).getIdType() + "</idType>");
    stringBuilder.append("</listCustIdentity>");

    }else{
        //la khach hang moi
        stringBuilder.append("<custIdentityDTO>");

        Date date = DateTimeUtils
                .convertStringToTime(custIdentityDTO.getIdIssueDate(), "dd/MM/yyyy");
        stringBuilder.append("<idIssueDate>"
                + DateTimeUtils.convertDateSendOverSoap(date)
                + "</idIssueDate>");
        if (!TextUtils.isEmpty(custIdentityDTO.getIdExpireDate())) {
            Date dateExpire = DateTimeUtils
                    .convertStringToTime(custIdentityDTO.getIdExpireDate(), "dd/MM/yyyy");
            stringBuilder.append("<idExpireDate>"
                    + DateTimeUtils.convertDateSendOverSoap(dateExpire)
                    + "</idExpireDate>");
        }

        stringBuilder.append("<idIssuePlace>"
                + StringUtils.xmlEscapeText(custIdentityDTO.getIdIssuePlace()) + "</idIssuePlace>");
        stringBuilder.append("<idNo>" + custIdentityDTO.getIdNo() + "</idNo>");
        stringBuilder.append("<idType>" + custIdentityDTO.getIdType() + "</idType>");
        stringBuilder.append("</custIdentityDTO>");

        stringBuilder.append("<listCustIdentity>");

        date = DateTimeUtils.convertStringToTime(custIdentityDTO.getIdIssueDate(), "dd/MM/yyyy");
        stringBuilder.append("<idIssueDate>"
                + DateTimeUtils.convertDateSendOverSoap(date)
                + "</idIssueDate>");
        stringBuilder.append("<idIssuePlace>"
                + StringUtils.xmlEscapeText(custIdentityDTO.getIdIssueDate()) + "</idIssuePlace>");
        stringBuilder.append("<idNo>" + custIdentityDTO.getIdNo() + "</idNo>");
        stringBuilder.append("<idType>" +custIdentityDTO.getIdType() + "</idType>");
        stringBuilder.append("</listCustIdentity>");

    }


        stringBuilder.append("<name>"
                + StringUtils.xmlEscapeText(oldCus.getName())
                + "</name>");
        stringBuilder.append("<nationality>" + getString(R.string.viet_nam)
                + "</nationality>");
        stringBuilder.append("<sex>" + oldCus.getSex() + "</sex>");
        stringBuilder.append("<custAdd>");
        stringBuilder.append("<areaCode>");
        stringBuilder.append(area.getProvince() + area.getDistrict() + area.getPrecinct());
        stringBuilder.append("</areaCode>");

        stringBuilder.append("<province>");
        stringBuilder.append("<code>" + area.getProvince() + "</code>");
        stringBuilder.append("<name>" + area.getProvinceName() + "</name>");
        stringBuilder.append("</province>");

        stringBuilder.append("<district>");
        stringBuilder.append("<code>" + area.getDistrict() + "</code>");
        stringBuilder.append("<name>" + area.getDistrictName() + "</name>");
        stringBuilder.append("</district>");

        stringBuilder.append("<precinct>");
        stringBuilder.append("<code>" + area.getPrecinct() + "</code>");
        stringBuilder.append("<name>" + area.getPrecinctName() + "</name>");
        stringBuilder.append("</precinct>");

        if (!CommonActivity.isNullOrEmpty(area.getStreetBlock())) {
            stringBuilder.append("<streetBlock>");
            stringBuilder.append("<code>" + area.getStreetBlock() + "</code>");
            stringBuilder.append("</streetBlock>");
        }
        if (!CommonActivity.isNullOrEmpty(area.getStreet())) {
            stringBuilder.append("<street>"
                    + StringUtils.xmlEscapeText(area.getStreet()) + "</street>");
        }
        if (!CommonActivity.isNullOrEmpty(area.getStreet())) {
            stringBuilder.append("<streetName>"
                    + StringUtils.xmlEscapeText(area.getStreet()) + "</streetName>");
        }
        stringBuilder.append("<home>"
                + StringUtils.xmlEscapeText(area.getHome()) + "</home>");
        stringBuilder.append("</custAdd>");
        return stringBuilder.toString();
    }

    private String getXmlSub(SubscriberDTO sub, String ngayhen) {
        Date date = null;
        GetAreaDal getAreaDal = new GetAreaDal(act);
        getAreaDal.open();
       final  StringBuilder stringBuilder = new StringBuilder("");
        // bo sung omichanel
        if (!CommonActivity.isNullOrEmpty(omniProcessId)) {
            stringBuilder.append("<omniProcessId>" + omniProcessId);
            stringBuilder.append("</omniProcessId>");
        }
        if (!CommonActivity.isNullOrEmpty(omniTaskId)) {
            stringBuilder.append("<omniTaskId>" + omniTaskId);
            stringBuilder.append("</omniTaskId>");
        }

        stringBuilder.append("<serial>" + sub.getSerial() + "</serial>");

        stringBuilder.append("<subId>" + sub.getSubId() + "</subId>");
        stringBuilder.append("<payType>" + sub.getPayType() + "</payType>");

        stringBuilder.append("<actStatus>" + sub.getActStatus()
                + "</actStatus>");
        stringBuilder.append("<activeDatetime>" + sub.getActiveDatetime()
                + "</activeDatetime>");
        stringBuilder.append("<createDatetime>" + sub.getCreateDatetime()
                + "</createDatetime>");
        stringBuilder.append("<createUser>" + sub.getCreateUser()
                + "</createUser>");
        stringBuilder.append("<imsi>" + sub.getImsi() + "</imsi>");
        stringBuilder.append("<isInfoComplete>" + sub.getIsInfoComplete()
                + "</isInfoComplete>");

        stringBuilder.append("<orgProductCode>" + sub.getOrgProductCode()
                + "</orgProductCode>");
        stringBuilder.append("<regTypeId>" + sub.getRegTypeId()
                + "</regTypeId>");
        stringBuilder.append("<shopCode>" + sub.getShopCode() + "</shopCode>");
        stringBuilder.append("<startMoney>" + sub.getStartMoney()
                + "</startMoney>");
        stringBuilder.append("<status>" + sub.getStatus() + "</status>");
        stringBuilder.append("<createUser>" + sub.getCreateUser()
                + "</createUser>");


        stringBuilder.append(
                "<signDate>" + StringUtils.convertDateToString(ngayhen)
                        + "T00:00:00+07:00");
        stringBuilder.append("</signDate>");

        //thong tin hop dong
        stringBuilder.append("<accountDTOForInput>");
        stringBuilder.append(TabThongTinHopDong.accountDTOMain.toXml());
        if ("02".equals(TabThongTinHopDong.accountDTOMain.getPayMethod())
                || "03".equals(TabThongTinHopDong.accountDTOMain.getPayMethod())) {
            stringBuilder.append(TabThongTinHopDong.accountDTOMain.getAccountBank().toXmlAcc());
         }
        stringBuilder.append("</accountDTOForInput>");

        stringBuilder.append("<subInfrastructureDTO>");
        stringBuilder.append("<userUsing>");
        stringBuilder.append(TabThueBaoHopDongManager.instance.contactName);
        stringBuilder.append("</userUsing>");

        stringBuilder.append("<telMobile>");
        stringBuilder.append(TabThueBaoHopDongManager.instance.telMobile);
        stringBuilder.append("</telMobile>");

        stringBuilder.append("<dataAddress>");
        stringBuilder.append("<province>");
        stringBuilder.append("<name>");
        try {
            stringBuilder.append(getAreaDal.getNameProvince(TabThueBaoHopDongManager.instance.area.getProvince()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        stringBuilder.append("</name>");
        stringBuilder.append("<code>");
        stringBuilder.append(TabThueBaoHopDongManager.instance.area.getProvince());
        stringBuilder.append("</code>");
        stringBuilder.append("</province>");

        stringBuilder.append("<district>");
        stringBuilder.append("<name>");
        try {
            stringBuilder.append(getAreaDal.getNameDistrict(TabThueBaoHopDongManager.instance.area.getProvince()
                    , TabThueBaoHopDongManager.instance.area.getDistrict()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        stringBuilder.append("</name>");
        stringBuilder.append("<code>");
        stringBuilder.append(TabThueBaoHopDongManager.instance.area.getDistrict());
        stringBuilder.append("</code>");
        stringBuilder.append("</district>");

        stringBuilder.append("<precinct>");
        stringBuilder.append("<name>");
        stringBuilder.append(getAreaDal.getNamePrecint(TabThueBaoHopDongManager.instance.area.getProvince()
                , TabThueBaoHopDongManager.instance.area.getDistrict()
                , TabThueBaoHopDongManager.instance.area.getPrecinct()));
        stringBuilder.append("</name>");
        stringBuilder.append("<code>");
        stringBuilder.append(TabThueBaoHopDongManager.instance.area.getPrecinct());
        stringBuilder.append("</code>");
        stringBuilder.append("</precinct>");
        getAreaDal.close();

        stringBuilder.append("<fullAddress>");
        stringBuilder.append(TabThueBaoHopDongManager.instance.area.getFullNamel());
        stringBuilder.append("</fullAddress>");

        stringBuilder.append("</dataAddress>");
        stringBuilder.append("</subInfrastructureDTO>");

        stringBuilder.append("<productOfferCodeWsDTO>");
        stringBuilder.append("<productOffer>");
        stringBuilder.append("<offerId>");
        stringBuilder.append(sub.getOfferId());
        stringBuilder.append("</offerId>");
        stringBuilder.append("</productOffer>");
        stringBuilder.append("</productOfferCodeWsDTO>");


            stringBuilder.append("<promotionCode>");
            stringBuilder.append(sub.getPromotion().getPromProgramCode());
            stringBuilder.append("</promotionCode>");


        stringBuilder.append("<telecomServiceId>");
        stringBuilder.append(sub.getTelecomServiceId());
        stringBuilder.append("</telecomServiceId>");

        stringBuilder.append("<reasonId>");
        stringBuilder.append(reasonId);
        stringBuilder.append("</reasonId>");

        stringBuilder.append("<productCode>");
        stringBuilder.append(sub.getProductCode());
        stringBuilder.append("</productCode>");

        stringBuilder.append("<isdn>");
        stringBuilder.append(sub.getIsdn());
        stringBuilder.append("</isdn>");

        //han muc
        stringBuilder.append("<subExtDTO>");
        stringBuilder.append("<extValue>");
        stringBuilder.append(sub.getQuota());
        stringBuilder.append("</extValue>");
        stringBuilder.append("</subExtDTO>");

        //chi phi so
        stringBuilder.append("<lstStockIsdnBeanDTO>");
        for(ProductOfferTypeDTO productOfferTypeDTO : sub.getLstProductType()) {
            stringBuilder.append("<price>");
            stringBuilder.append(productOfferTypeDTO.getStockModel().getPrice());
            stringBuilder.append("</price>");
        }
        stringBuilder.append("</lstStockIsdnBeanDTO>");

        //cuoc dong truoc
        stringBuilder.append("<listPaymentPrepaidDetail>");
        for(PaymentPrePaidDetailBeans paymentPrePaidDetailBeans: sub.getCuocDongTruoc().getLstDetailBeans()) {

            stringBuilder.append("<totalMoney>");
            stringBuilder.append(paymentPrePaidDetailBeans.getTotalMoney());
            stringBuilder.append("</totalMoney>");

                stringBuilder.append("<monthPrepaid>");
                stringBuilder.append(paymentPrePaidDetailBeans.getSubMonth());
                stringBuilder.append("</monthPrepaid>");

        }
        stringBuilder.append("</listPaymentPrepaidDetail>");


        //toc do down up
        if(!CommonActivity.isNullOrEmpty(sub.getOfferId())) {
            GetInfoPackageAsyn getInfoPackageAsyn = new GetInfoPackageAsyn(act, new OnPostExecuteListener<ProductOfferCharacterClone>() {
                @Override
                public void onPostExecute(ProductOfferCharacterClone result, String errorCode, String description) {
                }
            }, null);

            try {
                ProductOfferCharacterClone result = getInfoPackageAsyn.execute(String.valueOf(sub.getOfferId())).get()  ;
                if(!CommonActivity.isNullOrEmpty(result)) {
                    String speed = result.getDownLoadSpeed() + "/" + result.getUpLoadSpeed();
                    stringBuilder.append("<downUpSpeed>");
                    stringBuilder.append(speed);
                    stringBuilder.append("</downUpSpeed>");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }



        return stringBuilder.toString();
    }

    private ProfileBO initProfileBO(SubscriberDTO subscriberDTO) {
        horizontalScrollView.setVisibility(View.GONE);
        String actionCode = "00";

        profileBO.setSigImageFullPath(null);
        if (!CommonActivity.isNullOrEmpty(profileBO.getLstActionCode())) {
            profileBO.getLstActionCode().clear();
        }
        profileBO.getLstActionCode().add(actionCode);
        if (!CommonActivity.isNullOrEmpty(profileBO.getLstReasonId())) {
            profileBO.getLstReasonId().clear();
        }
        profileBO.getLstReasonId().add(reasonId);
        String payType = "1"; //dau noi co dinh , luon la 1 tra sau
        String parValue = payType;

        profileBO.setPayType(payType);
        profileBO.setParValue(parValue);

        if (custIdentityDTO != null && !CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer())) {
            if (!CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getCustId()) && !CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getCustTypeDTO())) {
                profileBO.setCustType(custIdentityDTO.getCustomer().getCustTypeDTO().getCustType());
            } else {
                profileBO.setCustType(custIdentityDTO.getCustomer().getCustType());
            }
        }
        profileBO.setServiceType(subscriberDTO.getServiceType());
        profileBO.setIdNo(custIdentityDTO.getIdNo());
        return profileBO;
    }


    private ProfileBO initProfileBOSubOther(SubscriberDTO subscriberDTO) {
        Long subIdTmp = subscriberDTO.getSubId();
        horizontalScrollViewD.get(subIdTmp).setVisibility(View.GONE);
        String actionCode = "00";

        profileBOD.put(subIdTmp, new ProfileBO());
        profileBOD.get(subIdTmp).setSigImageFullPath(null);
        if (!CommonActivity.isNullOrEmpty(profileBOD.get(subIdTmp).getLstActionCode())) {
            profileBOD.get(subIdTmp).getLstActionCode().clear();
        }
        profileBOD.get(subIdTmp).getLstActionCode().add(actionCode);
        if (!CommonActivity.isNullOrEmpty(profileBOD.get(subIdTmp).getLstReasonId())) {
            profileBOD.get(subIdTmp).getLstReasonId().clear();
        }
        profileBOD.get(subIdTmp).getLstReasonId().add(reasonIdD.get(subIdTmp));
        String payType = "1"; //dau noi co dinh , luon la 1 tra sau
        String parValue = payType;

        profileBOD.get(subIdTmp).setPayType(payType);
        profileBOD.get(subIdTmp).setParValue(parValue);

        if (custIdentityDTO != null && !CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer())) {
            if (!CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getCustId()) && !CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getCustTypeDTO())) {
                profileBOD.get(subIdTmp).setCustType(custIdentityDTO.getCustomer().getCustTypeDTO().getCustType());
            } else {
                profileBOD.get(subIdTmp).setCustType(custIdentityDTO.getCustomer().getCustType());
            }
        }
        profileBOD.get(subIdTmp).setServiceType(subscriberDTO.getServiceType());
        profileBOD.get(subIdTmp).setIdNo(custIdentityDTO.getIdNo());
        return profileBOD.get(subIdTmp);
    }

    private ArrayList<FormBean> cloneToFormBean(ArrayList<FileObj> fileObjs) {
        ArrayList<FormBean> formBeans = new ArrayList<>();
        for (FileObj fileObj : fileObjs) {
            FormBean formBean = new FormBean();
            formBean.setId(String.valueOf(fileObj.getId()));
            formBean.setCode(fileObj.getCodeSpinner());
            formBean.setName(fileObj.getName());
            formBeans.add(formBean);
        }
        return formBeans;
    }

    private List<ProfileUpload> convertToProfileUpload(HashMap<String, ArrayList<FileObj>> hashmapFileObj) {
        List<ProfileUpload> lstProfileUploads = new ArrayList<>();
        //
        if (!CommonActivity.isNullOrEmpty(hashmapFileObj)) {
            for (ArrayList<FileObj> item : hashmapFileObj.values()) {
                ProfileUpload profileUpload = new ProfileUpload();
                List<FormBean> lstProfile = new ArrayList<>();
                List<File> lstFile = new ArrayList<File>();
                for (FileObj fileObj : item) {
                    lstProfile.add(convertFormBean(fileObj));

                    //create file image
                    File file = new File(fileObj.getPath());
                    if (!file.exists()) {
                        File mediaStorageDir = new File(
                                Environment
                                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                                "MBBCSCameraApp");
                        file = new File(mediaStorageDir.getPath()
                                + File.separator + file.getName());
                    }
                    lstFile.add(file);
                }

                profileUpload.setLstProfile(lstProfile);
                lstProfileUploads.add(profileUpload);

                //add lstFile
                profileUpload.setLstFile(lstFile);
            }
        }

        return lstProfileUploads;
    }

    private FormBean convertFormBean(FileObj fileObj) {
        FormBean formBean = new FormBean();
        formBean.setName(fileObj.getName());
        formBean.setCode(fileObj.getCodeSpinner());
        formBean.setId(fileObj.getId() + "");

        return formBean;
    }

    private class DialogSubInfo extends Dialog {

        private Long subIdTmp;
        OnFinishDSFListener onFinishDSFListenerSubOther = new OnFinishDSFListener() {
            @Override
            public void onFinish(ProfileBO output) {
                profileBOD.put(subIdTmp, output);
                if ((profileBOD.get(subIdTmp) != null
                        && profileBOD.get(subIdTmp).getHashmapFileObj() != null
                        && !profileBOD.get(subIdTmp).getHashmapFileObj().isEmpty())
                        || (profileBOD.get(subIdTmp) != null && profileBOD.get(subIdTmp).getProfileRecords() != null && profileBOD.get(subIdTmp).getProfileRecords().size() > 0)) {

                    thumbnailsD.get(subIdTmp).removeAllViews();
                    horizontalScrollViewD.get(subIdTmp).setVisibility(View.VISIBLE);

                    ArrayList<String> lstData = new ArrayList<>();
                    for (Map.Entry<String, ArrayList<FileObj>> entry : profileBOD.get(subIdTmp).getHashmapFileObj().entrySet()) {
                        for (FileObj fileObj : entry.getValue()) {
                            Log.d("onFinishDSFListener", fileObj.getFullPath());
                            lstData.add(fileObj.getFullPath());
                        }
                    }
                    if (profileBOD.get(subIdTmp).getSigImageFullPath() != null) {
                        lstData.add(profileBOD.get(subIdTmp).getSigImageFullPath());
                    }
                    ImageUtils.loadImageHorizontal(getActivity(), LayoutInflater.from(getActivity()), thumbnailsD.get(subIdTmp), lstData, new com.viettel.bss.viettelpos.v4.listener.OnClickListener() {
                        @Override
                        public void onClick(Object... obj) {
                            View thumView = (View) obj[0];
                            String path = String.valueOf(obj[1]);

                            zoomImageFromThumb(thumView, path, frlMainSubOther, expandedImageViewSubOther);
                        }
                    });
                } else {
                    horizontalScrollViewD.get(subIdTmp).setVisibility(View.GONE);
                }
            }
        };


        public DialogSubInfo(Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.layout_thong_tin_thue_bao_addtion);
            setTitle(R.string.thue_bao_dau_kem);
            expandedImageViewSubOther = (ImageView) findViewById(R.id.expandedImageView);
            lnSelectProfileSubOther = (LinearLayout) findViewById(R.id.lnSelectProfile);
            frlMainSubOther = (FrameLayout) findViewById(R.id.frlMain);

            subIdTmp = tmpSub.getSubId();
            thumbnailsD.put(subIdTmp, (LinearLayout) findViewById(R.id.thumbnails));
            horizontalScrollViewD.put(subIdTmp, (HorizontalScrollView) findViewById(R.id.horizontalScrollView));
            lnMoHinhD.put(subIdTmp, findViewById(R.id.lnMoHinh));
            btnRefreshQuotaD.put(subIdTmp,
                    mView.findViewById(R.id.btnRefreshQuota));
            spnMoHinhD.put(subIdTmp, (Spinner) findViewById(R.id.spnMoHinh));
            lnQuotaD.put(subIdTmp, findViewById(R.id.lnQuota));
            prbQuotaD.put(subIdTmp, findViewById(R.id.prbQuota));
            spnQuotaD.put(subIdTmp, (Spinner) findViewById(R.id.spnQuota));

            lnIpAddressD.put(subIdTmp, findViewById(R.id.lnIpAddress));
            prbDepositD.put(subIdTmp, findViewById(R.id.prbDeposit));
            prbDuongDayD.put(subIdTmp, findViewById(R.id.prbDuongDay));
            prbStaticIpD.put(subIdTmp, findViewById(R.id.prbStaticIp));
            spnDepositD.put(subIdTmp, (Spinner) findViewById(R.id.spnDeposit));
            btnRefreshDepositD.put(subIdTmp,
                    mView.findViewById(R.id.btnRefreshDeposit));
            btnRefreshDepositD.get(subIdTmp).setVisibility(View.GONE);
            btnRefreshDepositD.get(subIdTmp).setOnClickListener(
                    TabThongTinThueBao.this);

            btnSaveSubD.put(subIdTmp, findViewById(R.id.btnSaveSubD));
            btnCloseD.put(subIdTmp, findViewById(R.id.btnCloseD));
            btnSaveSubD.get(subIdTmp).setOnClickListener(TabThongTinThueBao.this);
            btnCloseD.get(subIdTmp).setOnClickListener(TabThongTinThueBao.this);

            spnCustTypeD.put(subIdTmp, (Spinner) findViewById(R.id.spnCustType));
            edtNgayHenD.put(subIdTmp, (EditText) findViewById(R.id.edtNgayHen));
            edtServiceD.put(subIdTmp, (EditText) findViewById(R.id.edtService));
            edtTechnologyD.put(subIdTmp,
                    (EditText) findViewById(R.id.edtTechnology));
            prbCuocDongTruocD.put(subIdTmp, findViewById(R.id.prbCuocdongtruoc));
            edtKetCuoiD.put(subIdTmp, (EditText) findViewById(R.id.edtKetCuoi));
            edtDuongDayD.put(subIdTmp, (EditText) findViewById(R.id.edtDuongDay));
            edtSubTypeD.put(subIdTmp, (EditText) findViewById(R.id.edtSubType));
            edtSubTypeD.get(subIdTmp).setOnClickListener(TabThongTinThueBao.this);
            edtNgayHenD.get(subIdTmp).setOnClickListener(TabThongTinThueBao.this);
            edtDiaChiLapDatD.put(subIdTmp,
                    (EditText) findViewById(R.id.edtDiaChiLapDat));
            edtDiaChiLapDatD.get(subIdTmp).setOnClickListener(
                    TabThongTinThueBao.this);
            edtIsdnD.put(subIdTmp, (EditText) findViewById(R.id.edtIsdn));
            tvcamketsoD.put(subIdTmp, (TextView) findViewById(R.id.tvcamketso));
            edtGoiCuocD.put(subIdTmp, (EditText) findViewById(R.id.edtGoiCuoc));
            edtGoiCuocD.get(subIdTmp).setOnClickListener(TabThongTinThueBao.this);
            spnStaticIpD.put(subIdTmp, (Spinner) findViewById(R.id.spnStaticIp));
            prbPromotionD.put(subIdTmp, findViewById(R.id.prbPromotion));
            prbThietBiD.put(subIdTmp, findViewById(R.id.prbThietBi));
            if (!CommonActivity.isNullOrEmpty(tmpSub.getProductCode())) {
                edtGoiCuocD.get(subIdTmp).setText(
                        tmpSub.getProductCode() + " - "
                                + tmpSub.getProductName());
            }
            edtPromotionD
                    .put(subIdTmp, (EditText) findViewById(R.id.edtPromotion));
            if (tmpSub.getPromotion() != null) {
                edtPromotionD.get(subIdTmp).setText(
                        tmpSub.getPromotion().getCodeName());
            }
            edtPromotionD.get(subIdTmp)
                    .setOnClickListener(TabThongTinThueBao.this);
            spnCuocDongTruocD.put(subIdTmp,
                    (Spinner) findViewById(R.id.spnCuocDongTruoc));
            btnPaymentInfoD.put(subIdTmp, (Button) findViewById(R.id.btnInfoPayment));
            btnPaymentInfoD.get(subIdTmp).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SubscriberDTO sub = getSub();
                    if (!CommonActivity.isNullOrEmpty(sub.getCuocDongTruoc())) {
                        showSelectCuocDongTruoc(sub.getCuocDongTruoc());
                    }
                }
            });
            prbCustTypeD.put(subIdTmp, findViewById(R.id.prbCustType));
            btnRefreshCustTypeD.put(subIdTmp,
                    findViewById(R.id.btnRefreshCustType));
            lvUploadImageD.put(subIdTmp,
                    (ListView) findViewById(R.id.lvUploadImage));
            prbProfileD.put(subIdTmp, findViewById(R.id.prbProfile));
            prbIsdnD.put(subIdTmp, findViewById(R.id.prbIsdn));
            prbIsdnD.get(subIdTmp).setVisibility(View.GONE);
            edtHTHMD.put(subIdTmp, (EditText) findViewById(R.id.edtHTHM));

            edtHTHMD.get(subIdTmp).setOnClickListener(TabThongTinThueBao.this);
            btnRefreshQuotaD.get(subIdTmp).setOnClickListener(
                    TabThongTinThueBao.this);
            lnThietBiD.put(subIdTmp, findViewById(R.id.lnThietBi));
            lnThietBiD.get(subIdTmp).setOnClickListener(TabThongTinThueBao.this);
            tvThietBiD.put(subIdTmp, (TextView) findViewById(R.id.tvThietBi));

            btnRefreshCustTypeD.get(subIdTmp).setOnClickListener(
                    TabThongTinThueBao.this);
            edtNgayHenD.get(subIdTmp).setText(
                    DateTimeUtils.convertDateTimeToString(new Date(),
                            "dd/MM/yyyy"));
            edtKetCuoiD.get(subIdTmp).setHint(surveyOnline.getConnectorCode());
            edtServiceD.get(subIdTmp).setHint(tmpSub.getServiceTypeName());
            edtDuongDayD.get(subIdTmp).setHint(edtDuongDay.getHint().toString());
            if (!CommonActivity.isNullOrEmpty(area) && !CommonActivity.isNullOrEmpty(area.getFullNamel())) {
                edtDiaChiLapDatD
                        .get(subIdTmp)
                        .setText(
                                area.getFullNamel());
            }
            edtTechnologyD
                    .get(subIdTmp)
                    .setHint(
                            TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit
                                    .getInfratypeName(act));
            spnCuocDongTruocD.get(subIdTmp).setOnItemSelectedListener(
                    new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> arg0,
                                                   View arg1, int arg2, long arg3) {
                            // if (arg2 == 0) {
                            // tmpSub.setCuocDongTruoc(null);
                            // return;
                            // }

                            PaymentPrePaidPromotionBeans bean = mapPrepaid.get(
                                    tmpSub.getCuocDongTruocKey()).get(arg2);
                            tmpSub.setCuocDongTruoc(bean);
                            ArrayList<ProductPackageFeeDTO> arrProductFee = new ArrayList<ProductPackageFeeDTO>();
                            ProductPackageFeeDTO productPackageFeeDTO = new ProductPackageFeeDTO();
                            productPackageFeeDTO.setName(bean.getName());
                            productPackageFeeDTO.setPrice(bean.getTotalMoney() + "");
                            arrProductFee.add(productPackageFeeDTO);

                            if ("-1".equals(bean.getPrePaidCode())) {
                                tmpSub.getHashmapProductPakage().put(tmpSub.getSubId() + "-" + "CDT", new ArrayList<ProductPackageFeeDTO>());
                                return;
                            } else {
                                tmpSub.getHashmapProductPakage().put(tmpSub.getSubId() + "-" + "CDT", arrProductFee);
                            }
                            showSelectCuocDongTruoc(bean);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                        }
                    });

            AsynctaskGetListCustType asy = new AsynctaskGetListCustType(act,
                    prbCustTypeD.get(subIdTmp), btnRefreshCustTypeD.get(subIdTmp),
                    spnCustTypeD.get(subIdTmp));
            asy.execute();
            if (!tmpSub.isPNService()) {
                edtIsdnD.get(subIdTmp).setText(tmpSub.getIsdn());
            }
            if ("4".equals(TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit
                    .getInfraType())) {
                if (!"A".equals(tmpSub.getServiceType())
                        || !"U".equals(tmpSub.getServiceType())) {
                    lnMoHinhD.get(subIdTmp).setVisibility(View.VISIBLE);
                } else {
                    lnMoHinhD.get(subIdTmp).setVisibility(View.GONE);
                }
            } else {
                lnMoHinhD.get(subIdTmp).setVisibility(View.GONE);
            }

            List<Spin> arrMohinh = new ArrayList<Spin>();
            arrMohinh.add(new Spin("ONT", getString(R.string.ont)));
            arrMohinh
                    .add(new Spin("ONT_BRIDGE", getString(R.string.ont_bridge)));
            arrMohinh.add(new Spin("SFU", getString(R.string.sfu)));
            ArrayAdapter<Spin> adapter = new ArrayAdapter<Spin>(act,
                    R.layout.spinner_item, R.id.spinner_value, arrMohinh);
            spnMoHinhD.get(subIdTmp).setAdapter(adapter);

            if (!CommonActivity.isNullOrEmpty(mainSub) && !CommonActivity.isNullOrEmpty(mainSub.getDeployModel())) {
                for (Spin spin : arrMohinh) {
                    if (spin.getId().equals(mainSub.getDeployModel())) {
                        spnMoHinhD.get(subIdTmp).setSelection(arrMohinh.indexOf(spin));
                        spnMoHinhD.get(subIdTmp).setEnabled(false);
                        break;
                    }
                }
            }

            spnMoHinhD.get(subIdTmp).setOnItemSelectedListener(
                    new OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> arg0,
                                                   View arg1, int arg2, long arg3) {
                            Spin item = (Spin) arg0.getSelectedItem();
                            tmpSub.setDeployModel(item.getId());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                        }
                    });

            if ("N".equals(tmpSub.getServiceType())
                    || "P".equals(tmpSub.getServiceType())) {
                lnQuotaD.get(subIdTmp).setVisibility(View.VISIBLE);
                tvcamketsoD.get(subIdTmp).setVisibility(View.VISIBLE);
            } else {
                lnQuotaD.get(subIdTmp).setVisibility(View.GONE);
                tvcamketsoD.get(subIdTmp).setVisibility(View.GONE);
            }

            spnQuotaD.get(subIdTmp).setOnItemSelectedListener(
                    new OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> arg0,
                                                   View arg1, int arg2, long arg3) {

                            if (arg2 == 0) {
                                tmpSub.setQuota("");

                            } else {
                                tmpSub.setQuota(tmpSub.getLstQuota().get(arg2).getValue());

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub

                        }
                    });

            spnDepositD.get(subIdTmp).setOnItemSelectedListener(
                    new OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> arg0,
                                                   View arg1, int arg2, long arg3) {
                            SubscriberDTO sub = getSub();
                            ReasonPledgeDTO reasonPledgeDTO = (ReasonPledgeDTO) spnDepositD
                                    .get(subIdTmp).getItemAtPosition(arg2);
                            tmpSub.setDeposit((ReasonPledgeDTO) spnDepositD
                                    .get(subIdTmp).getItemAtPosition(arg2));
                            if (reasonPledgeDTO != null && reasonPledgeDTO.getNumMoney() != null) {
                                ArrayList<ProductPackageFeeDTO> arrProductFee = new ArrayList<ProductPackageFeeDTO>();
                                ProductPackageFeeDTO productPackageFeeDTO = new ProductPackageFeeDTO();
                                productPackageFeeDTO.setName(getActivity().getString(R.string.depositcds));
                                productPackageFeeDTO.setPrice(reasonPledgeDTO.getNumMoney() + "");
                                arrProductFee.add(productPackageFeeDTO);
                                sub.getHashmapProductPakage().put(sub.getSubId() + "-" + "DC", arrProductFee);
                            }


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub

                        }
                    });

            lnSelectProfileSubOther.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    String reason = edtHTHMD.get(subIdTmp).getText().toString().trim();
//                    if (act.getString(R.string.chonlydo).equals(reason)
//                            || CommonActivity.isNullOrEmpty(reasonIdD.get(tmpSub.getSubId()))) {
//                        CommonActivity.toast(getActivity(), R.string.chua_chon_ly_do_dang_ky);
//                        return;
//                    }

                    if(!validateProfile(tmpSub)){
                        return ;
                    }


                    if (!CommonActivity.isNullOrEmpty(profileBOD.get(tmpSub.getSubId()))
                            && !CommonActivity.isNullOrEmpty(profileBOD.get(tmpSub.getSubId()).getMapListRecordPrepaid())) {
                        profileInfoDialogFragmentD.put(tmpSub.getSubId(), new ProfileInfoDialogFragment());
                        String ngayHen = edtNgayHenD.get(subIdTmp).getText().toString();

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("profileBO", profileBOD.get(tmpSub.getSubId()));

                        bundle.putString("xmlSub", getXmlSub(tmpSub, ngayHen));
                        bundle.putString("xmlCus", getXmlCus(custIdentityDTO));
                        bundle.putString("groupType", groupType);
                        profileInfoDialogFragmentD.get(tmpSub.getSubId()).setOnFinishDSFListener(onFinishDSFListenerSubOther);
                        profileInfoDialogFragmentD.get(tmpSub.getSubId()).setArguments(bundle);
                        profileInfoDialogFragmentD.get(tmpSub.getSubId()).show(getFragmentManager(), "show profile");
                    }


                }
            });
        }

        public void reloadDeployMoldel(String deployModel) {
            Long subId = tmpSub.getSubId();
            List<Spin> arrMohinh = new ArrayList<Spin>();
            arrMohinh.add(new Spin("ONT", getString(R.string.ont)));
            arrMohinh
                    .add(new Spin("ONT_BRIDGE", getString(R.string.ont_bridge)));
            arrMohinh.add(new Spin("SFU", getString(R.string.sfu)));
            ArrayAdapter<Spin> adapter = new ArrayAdapter<Spin>(act,
                    R.layout.spinner_item, R.id.spinner_value, arrMohinh);
            spnMoHinhD.get(subId).setAdapter(adapter);

            if (!CommonActivity.isNullOrEmpty(mainSub) && !CommonActivity.isNullOrEmpty(deployModel)) {
                for (Spin spin : arrMohinh) {
                    if (spin.getId().equals(deployModel)) {
                        spnMoHinhD.get(subId).setSelection(arrMohinh.indexOf(spin));
                        spnMoHinhD.get(subId).setEnabled(false);
                        break;
                    }
                }
            }
        }


    }

    // ham lay thong tin cam ket so dep cho dich vu P hoac N
    private class GetPriceInServicesMultiplePledgeAsyn extends AsyncTask<String, Void, ArrayList<ProductOfferPriceDTO>> {

        private Context mContext;
        private String errorCode;
        private String description;
        private ProgressDialog progress;
        private EditText txtisdn;

        public GetPriceInServicesMultiplePledgeAsyn(Context context, EditText edtIsdn) {
            this.mContext = context;
            this.txtisdn = edtIsdn;
            this.progress = new ProgressDialog(mContext);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected ArrayList<ProductOfferPriceDTO> doInBackground(String... arg0) {
            return getDataStock(arg0[0], arg0[1], arg0[2], arg0[3]);
        }

        @Override
        protected void onPostExecute(ArrayList<ProductOfferPriceDTO> result) {
            super.onPostExecute(result);
            progress.dismiss();

            if ("0".equals(errorCode)) {
                if (!CommonActivity.isNullOrEmpty(result)) {

                    if (!CommonActivity.isNullOrEmpty(result.get(0).getPrice())
                            && !CommonActivity.isNullOrEmpty(result.get(0).getPledgeTime())) {
                        showDialogViewDataStock(result.get(0), txtisdn.getText().toString().trim());
                    } else {
                        CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notviewcamket),
                                getActivity().getString(R.string.app_name)).show();
                    }
                } else {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notviewcamket),
                            getActivity().getString(R.string.app_name)).show();
                }

            } else {

                if (Constant.INVALID_TOKEN2.equals(errorCode)) {

                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                            mContext.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = getActivity().getString(R.string.checkdes);
                    }

                }
                Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                        getActivity().getResources().getString(R.string.app_name));
                dialog.show();

            }

        }

        private ArrayList<ProductOfferPriceDTO> getDataStock(String telecomServiceId, String reasonId, String productCode, String isdn) {
            String original = "";
            ArrayList<ProductOfferPriceDTO> stockNumberDTO = new ArrayList<>();
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getPriceInServicesMultiplePledge");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getPriceInServicesMultiplePledge>");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");

                rawData.append("<telecomServiceId>" + telecomServiceId + "</telecomServiceId>");
                rawData.append("<isdn>" + CommonActivity.getStardardIsdnBCCS(isdn) + "</isdn>");
                rawData.append("<reasonId>" + reasonId + "</reasonId>");
                rawData.append("<productCode>" + productCode + "</productCode>");
                rawData.append("</input>");
                rawData.append("</ws:getPriceInServicesMultiplePledge>");
                Log.i("LOG", "raw data" + rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("LOG", "Send evelop" + envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
                        "mbccs_getPriceInServicesMultiplePledge");
                Log.i("LOG", "Respone:  " + response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("LOG", "Responseeeeeeeeee Original  " + original);

                Serializer serializer = new Persister();
                ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
                if (parseOuput != null) {
                    errorCode = parseOuput.getErrorCode();
                    description = parseOuput.getDescription();
                    stockNumberDTO = parseOuput.getLstProductOfferPriceDTOs();
                }

                return stockNumberDTO;

            } catch (Exception e) {
                Log.d("getDataStock", e.toString());
            }

            return stockNumberDTO;
        }

    }

    private class FindFeeByReasonTeleIdAsyn extends AsyncTask<String, Void, ArrayList<ProductPackageFeeDTO>> {
        private Context mContext;
        private String errorCode;
        private String description;
        private ProgressDialog progress;
        private SubscriberDTO subscriberDTO;

        public FindFeeByReasonTeleIdAsyn(Context context, SubscriberDTO sub) {
            this.mContext = context;
            this.progress = new ProgressDialog(this.mContext);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdatareport));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
            this.subscriberDTO = sub;
        }

        @Override
        protected ArrayList<ProductPackageFeeDTO> doInBackground(String... arg0) {
            return getProductSpec(arg0[0], arg0[1], arg0[2]);
        }

        @Override
        protected void onPostExecute(ArrayList<ProductPackageFeeDTO> result) {
            super.onPostExecute(result);
            progress.dismiss();
            subscriberDTO.getHashmapProductPakage().put(subscriberDTO.getSubId() + "-" + "PHM", result);
            if ("0".equals(errorCode)) {
//				subscriberDTO.getHashmapProductPakage().put(subscriberDTO.getSubId() + "-" + "PHM",result);
//				if (!CommonActivity.isNullOrEmptyArray(result)) {
//					showDialogGetFee(result);
//				} else {
//					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notthogntinvi),
//							getActivity().getString(R.string.app_name)).show();
//				}
            } else {
                if (Constant.INVALID_TOKEN2.equals(errorCode) && description != null && !description.isEmpty()) {
                    Dialog dialog = CommonActivity.createAlertDialog((Activity) mContext, description,
                            mContext.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = mContext.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                            getResources().getString(R.string.app_name));
                    dialog.show();

                }
            }
        }

        private ArrayList<ProductPackageFeeDTO> getProductSpec(String telecomserviceId, String reasonId,
                                                               String productCode) {
            String original = "";
            ArrayList<ProductPackageFeeDTO> arrayList = new ArrayList<ProductPackageFeeDTO>();
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_findFeeByReasonTeleId");
                StringBuilder rawData = new StringBuilder();

                rawData.append("<ws:findFeeByReasonTeleId>");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");

                rawData.append("<telecomServiceId>" + telecomserviceId + "</telecomServiceId>");
                rawData.append("<reasonId>" + reasonId + "</reasonId>");
                rawData.append("<productCode>" + productCode + "</productCode>");

                rawData.append("</input>");
                rawData.append("</ws:findFeeByReasonTeleId>");
                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(), "mbccs_sendOTPUtil");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);

                original = output.getOriginal();
                Log.d("originalllllllll", original);

                Serializer serializer = new Persister();
                ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
                if (parseOuput != null) {
                    errorCode = parseOuput.getErrorCode();
                    description = parseOuput.getDescription();
                    arrayList = parseOuput.getLstProductPackageFeeDTO();
                } else {
                    errorCode = Constant.ERROR_CODE;
                    description = getActivity().getString(R.string.no_data_return);
                }
            } catch (Exception e) {
                Log.d("getProductSpec", e.toString());
                errorCode = Constant.ERROR_CODE;
                description = getActivity().getString(R.string.no_data_return);
            }

            return arrayList;
        }
    }

    private class GetReasonCharacterAsyn extends AsyncTask<String, Void, ArrayList<String>> {

        HTHMBeans hthmBeans;
        SubscriberDTO sub;
        private Context mContext;
        private String errorCode;
        private String description;
        private ProgressDialog progress;

        public GetReasonCharacterAsyn(Context context, HTHMBeans hthmBeans, SubscriberDTO subscriberDTO) {
            this.mContext = context;
            this.progress = new ProgressDialog(mContext);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
            this.hthmBeans = hthmBeans;
            this.sub = subscriberDTO;
        }

        @Override
        protected ArrayList<String> doInBackground(String... arg0) {
            return getReasonCharater(arg0[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            super.onPostExecute(result);
            progress.dismiss();
            if ("0".equals(errorCode)) {
                if (!CommonActivity.isNullOrEmpty(result)) {
                    for (String item : result) {
                        if ("DAT_COC".equals(item)) {
                            hthmBeans.setDeposit(true);
                            SharedPreferences preferences = getActivity().getSharedPreferences(Define.PRE_NAME, Activity.MODE_PRIVATE);
                            String name = preferences.getString(Define.KEY_MENU_NAME, "");
                            if (hthmBeans.isDeposit() && name.contains(";menu.deposit.cd;")) {
                                Spinner spn = spnDeposit;
                                View prbDP = prbDeposit;
                                // Neu HTHM cau hinh co dat coc
                                if (isShowDialog()) {
                                    mapDialogSubInfo.get(sub.getSubId())
                                            .findViewById(R.id.lnDatCoc)
                                            .setVisibility(View.VISIBLE);
                                    spn = spnDepositD.get(sub.getSubId());
                                    prbDP = prbDepositD.get(sub.getSubId());
                                } else {
                                    mView.findViewById(R.id.lnDatCoc).setVisibility(
                                            View.VISIBLE);

                                }
                                GetDepositAsyncTask deposit = new GetDepositAsyncTask(
                                        act, spn, sub, prbDP);
                                deposit.execute();
                            } else {
                                if (isShowDialog()) {
                                    mapDialogSubInfo.get(sub.getSubId())
                                            .findViewById(R.id.lnDatCoc)
                                            .setVisibility(View.GONE);
                                } else {
                                    mView.findViewById(R.id.lnDatCoc).setVisibility(
                                            View.GONE);
                                }
                                sub.setReasonPledgeDTO(null);
                            }

                            break;
                        }
                    }
                } else {
                    if (isShowDialog()) {
                        mapDialogSubInfo.get(sub.getSubId())
                                .findViewById(R.id.lnDatCoc)
                                .setVisibility(View.GONE);
                    } else {
                        mView.findViewById(R.id.lnDatCoc).setVisibility(
                                View.GONE);
                    }
                    sub.setReasonPledgeDTO(null);
                }
            } else {
                if (isShowDialog()) {
                    mapDialogSubInfo.get(sub.getSubId())
                            .findViewById(R.id.lnDatCoc)
                            .setVisibility(View.GONE);
                } else {
                    mView.findViewById(R.id.lnDatCoc).setVisibility(
                            View.GONE);
                }
                sub.setReasonPledgeDTO(null);
                if (Constant.INVALID_TOKEN2.equals(errorCode)) {

                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                            mContext.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = getActivity().getString(R.string.checkdes);
                    }

                }
                Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                        getActivity().getResources().getString(R.string.app_name));
                dialog.show();

            }

        }

        private ArrayList<String> getReasonCharater(String reasonId) {
            String original = "";
            ArrayList<String> lstChar = new ArrayList<String>();
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getReasonCharacter");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getReasonCharacter>");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");
                rawData.append("<reasonId>" + reasonId + "</reasonId>");
                rawData.append("</input>");
                rawData.append("</ws:getReasonCharacter>");
                Log.i("LOG", "raw data" + rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("LOG", "Send evelop" + envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
                        "mbccs_getReasonCharacter");
                Log.i("LOG", "Respone:  " + response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("LOG", "Responseeeeeeeeee Original  " + original);

                Serializer serializer = new Persister();
                ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
                if (parseOuput != null) {
                    errorCode = parseOuput.getErrorCode();
                    description = parseOuput.getDescription();
                    lstChar = parseOuput.getLstchar();
                }

                return lstChar;

            } catch (Exception e) {
                Log.d("getDataStock", e.toString());
            }

            return lstChar;
        }
    }
}
