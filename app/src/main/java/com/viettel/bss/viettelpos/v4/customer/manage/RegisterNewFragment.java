package com.viettel.bss.viettelpos.v4.customer.manage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity.FragmentSearchSubChangeSim;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.AsyncGetOptionSetValue;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.AsyncGetOptionSetValueV2;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.DoUHaveMoreThan3SubAsyn;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustomerDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ReasonDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.CreateAddressCommon;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.FileUtils;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.commons.SdCardHelper;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.SearchReasonActivity;
import com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.CheckSubAdapter;
import com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.CheckSubAdapter.OnChangeCheckedSub;
import com.viettel.bss.viettelpos.v4.connecttionMobile.handler.SubscriberDTOHanlder;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.Reason;
import com.viettel.bss.viettelpos.v4.connecttionService.listener.ExpandableHeightListView;
import com.viettel.bss.viettelpos.v4.customer.adapter.GetSobaodanhAdapter;
import com.viettel.bss.viettelpos.v4.customer.asynctask.CheckCusQuotaAsync;
import com.viettel.bss.viettelpos.v4.customer.asynctask.SearchInvalidInfoSubcriberAsyncTask;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.customer.object.ReasonBundleObj;
import com.viettel.bss.viettelpos.v4.customer.object.SpinV2;
import com.viettel.bss.viettelpos.v4.customer.object.StudentBean;
import com.viettel.bss.viettelpos.v4.customer.object.SubInvalidDTO;
import com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.customview.adapter.DoiTuongObj;
import com.viettel.bss.viettelpos.v4.customview.adapter.DonViAdapter;
import com.viettel.bss.viettelpos.v4.customview.adapter.ObjDoiTuongAdapter;
import com.viettel.bss.viettelpos.v4.customview.adapter.RegisterInfoAdapter;
import com.viettel.bss.viettelpos.v4.customview.obj.FileObj;
import com.viettel.bss.viettelpos.v4.customview.obj.LoaiGiayToObj;
import com.viettel.bss.viettelpos.v4.customview.obj.RecordPrepaid;
import com.viettel.bss.viettelpos.v4.customview.obj.SpecObject;
import com.viettel.bss.viettelpos.v4.dal.CacheDatabaseManager;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.hsdt.fragments.ProfileInfoDialogFragment;
import com.viettel.bss.viettelpos.v4.infrastrucure.dal.InfrastrucureDB;
import com.viettel.bss.viettelpos.v4.listener.OnFinishDSFListener;
import com.viettel.bss.viettelpos.v4.object.ProfileBO;
import com.viettel.bss.viettelpos.v4.omichanel.asynctask.PlaceOrderAsyncTask;
import com.viettel.bss.viettelpos.v4.sale.activity.ActivityChooseChannel;
import com.viettel.bss.viettelpos.v4.sale.dal.BhldDAL;
import com.viettel.bss.viettelpos.v4.sale.object.SaleOutput;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.task.AsynTaskGetListRecordProfile;
import com.viettel.bss.viettelpos.v4.task.AsynTaskZipFile;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;
import com.viettel.bss.viettelpos.v4.utils.ImageUtils;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;
import com.viettel.savelog.SaveLog;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;

import static com.viettel.bss.viettelpos.v4.commons.Constant.ACTION_SCAN;

public class RegisterNewFragment extends FragmentCommon implements OnChangeCheckedSub {

    public static final int REQUEST_CHOOSE_ADDRESS = 1;
	public static final int REQUEST_CHOOSE_REASON = 2212;

    @BindView(R.id.lnChinhChu)
    LinearLayout lnChinhChu;
    @BindView(R.id.tvThueBaoChinhChu)
    TextView tvThueBaoChinhChu;
    @BindView(R.id.edtISDN)
    EditText mEdtISDN;
    @BindView(R.id.tvGoiCuoc)
    EditText mTvGoiCuoc;
    @BindView(R.id.edtSerial)
    EditText mEdtSerial;
    @BindView(R.id.btnBar)
    Button btnBar;

    @BindView(R.id.edtTenKH)
    EditText mEdtTenKH;
    @BindView(R.id.edtNgaySinh)
    EditText mEdtNgaySinh;
    @BindView(R.id.edtMaGiayTo)
    EditText mEdtMaGiayTo;
    @BindView(R.id.edtNgayCap)
    EditText mEdtNgayCap;
    @BindView(R.id.edtNoiCap)
    EditText mEdtNoiCap;
    @BindView(R.id.spGioiTinh)
    Spinner mSpGioiTinh;
    @BindView(R.id.spLoaiGiayTo)
    EditText mSpLoaiGiayTo;
    @BindView(R.id.lnTTGoiCuocDacBiet)
    LinearLayout mLnGoiDacBiet;
    @BindView(R.id.edtQuocTich)
    EditText mEdtQuocTich;
    @BindView(R.id.tvDonVi)
    EditText mTvDonVi;
    @BindView(R.id.edtMaGiayToDacBiet)
    EditText mEdtMaGiayToDacBiet;
    @BindView(R.id.edtNgayBD)
    EditText mEdtNgayBD;
    @BindView(R.id.edtNgayKT)
    EditText mEdtNgayKT;
    @BindView(R.id.spDoiTuong)
    Spinner mSpDoiTuong;

    @BindView(R.id.rlchondonvi)
    LinearLayout rlchondonvi;
    @BindView(R.id.rlquoctich)
    LinearLayout rlquoctich;

    @BindView(R.id.spnReason)
    EditText spnReason;
    @BindView(R.id.prbReason)
    View prbReason;
    @BindView(R.id.btnRefreshReason)
    Button btnRefreshReason;
    @BindView(R.id.btnRegister)
    Button mBtnRegister;
    @BindView(R.id.lnHocSinh)
    LinearLayout lnHocSinh;
    @BindView(R.id.edtnote)
    EditText edtnote;

    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.edtAgeHS)
    EditText edtAgeHS;
    @BindView(R.id.edtNameHS)
    EditText edtNameHS;

    @BindView(R.id.lnSelectProfile)
    LinearLayout lnSelectProfile;
    @BindView(R.id.thumbnails)
    LinearLayout thumbnails;
    @BindView(R.id.horizontalScrollView)
    HorizontalScrollView horizontalScrollView;
    @BindView(R.id.lnngayhethan)
    LinearLayout lnngayhethanNew;
    @BindView(R.id.edit_ngayhethan)
    EditText edit_ngayhethan;
    @BindView(R.id.edit_ngayky)
    EditText edit_ngayky;
    @BindView(R.id.expandedImageView)
    ImageView expandedImageView;
    @BindView(R.id.frlMain)
    FrameLayout frlMain;

    ListView lvListDonVi;
    LayoutInflater inflater;

    private ProfileInfoDialogFragment profileInfoDialogFragment;
    private ProfileBO profileBO = new ProfileBO();
    private AreaObj area;
    private ArrayList<LoaiGiayToObj> gioiTinh = new ArrayList<LoaiGiayToObj>();
    private String mIdGioiTinh = "";// loai gioi tinh
    private String custId = "";
    private String idIssueDate = "";
    private String idIssuePlace = "";
    private String idNo = "";
    private String idType = "";
    private String name = "";
    private String sex = "";
    private String mISDN = "";
    private String mIdNo = "";
    private Dialog dialogDonVi = null;
    private ArrayList<SpecObject> mArrayListSpec = new ArrayList<SpecObject>();
    private ArrayList<DoiTuongObj> mAratListDonVi = new ArrayList<DoiTuongObj>();

    // bien cho doi tuong dac biet
    private String mCodeDoiTuong = "";
    private String mCodeDonVi = "";
    private int mKHVersion;

    // Ma CTV dang ky thay
    private Date timeStart = null;
    private ArrayList<FileObj> arrFileBackUp;

    private CustomerDTO oldCus = null;
    private SubscriberDTO sub = null;
    private Long reasonId = Long.MIN_VALUE;
    private ArrayList<SubscriberDTO> arrSubscriberDTO = new ArrayList<SubscriberDTO>();
    private CheckSubAdapter checkSubAdapter;
    private ExpandableHeightListView lvsubparent;
    @BindView(R.id.spnDoituong)
    Spinner spnDoituong;

    private Animator mCurrentAnimator;
    private int mShortAnimationDuration;
    private ProgressDialog progressDangKy;
    private InfrastrucureDB mInfrastrucureDB;
    private BCCSGateway mBccsGateway = new BCCSGateway();

    // cau hinh thong tin an theo so giay to
    public static ArrayList<Spin> arrMapusage = new ArrayList<>();
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private Date dateNow;
    private String dateNowString;
    public boolean isMoreThan = false;
    private String descriptionNotice = "";
    // omni
    private String omniProcessId = "";
    private String omniTaskId = "";
    private   ArrayList<SpinV2> arrDoituong = new ArrayList<>();
    private  String subObject = null;
    private  String failCustomerInfo = null;







    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.idLayout = R.layout.register_new_customer_fragment;
        this.inflater = LayoutInflater.from(getActivity());
    }

    @Override
    protected void unit(View v) {

        this.lnSelectProfile.setVisibility(View.GONE);
        this.lnngayhethanNew.setVisibility(View.GONE);

        mInfrastrucureDB = new InfrastrucureDB(getContext());
        Calendar calendar = Calendar.getInstance();
        int fromYear = calendar.get(Calendar.YEAR);
        int fromMonth = calendar.get(Calendar.MONTH) + 1;
        final int fromDay = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.add(Calendar.DATE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        try {
            dateNow = calendar.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String fromMothStr = "";
        if (fromMonth < 10) {
            fromMothStr = "0" + fromMonth;
        } else {
            fromMothStr = "" + fromMonth;
        }

        String fromDayStr = "";
        if (fromDay < 10) {
            fromDayStr = "0" + fromDay;
        } else {
            fromDayStr = "" + fromDay;
        }

        dateNowString = fromDayStr + "/" + fromMothStr + "/" + fromYear + "";
        int mPositionLoaiGiayTo = 0;
        getSerialSim();

        edit_ngayhethan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonActivity.showDatePickerDialog(getActivity(), edit_ngayhethan);
            }
        });

        tvAddress.setOnClickListener(this);

        edit_ngayky.setText(dateNowString);
        edit_ngayky.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonActivity.showDatePickerDialog(getContext(), edit_ngayky);
            }
        });
        edtAgeHS.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                CommonActivity.showDatePickerDialog(getContext(), edtAgeHS);
            }
        });
        spnReason.setOnClickListener(this);

        btnRefreshReason.setOnClickListener(this);
        mEdtNgayBD.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                CommonActivity.showDatePickerDialog(getContext(), mEdtNgayBD);
            }
        });
        mEdtNgayKT.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                CommonActivity.showDatePickerDialog(getContext(), mEdtNgayKT);
            }
        });

                 

        mTvDonVi.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                timKiemDonVi();
            }
        });
        mEdtNgaySinh.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                CommonActivity.showDatePickerDialog(getActivity(), mEdtNgaySinh);
            }
        });

        mEdtNgayCap.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                CommonActivity.showDatePickerDialog(getActivity(), mEdtNgayCap);
            }
        });

        mBtnRegister.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try {
                    registerNew();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        String gt[] = {getString(R.string.gt_nam), getString(R.string.gt_nu)};
        String gtId[] = {"M", "F"};
        for (int i = 0; i < gt.length; i++) {
            LoaiGiayToObj gioiTinhStr = new LoaiGiayToObj(gtId[i], gt[i]);
            gioiTinh.add(gioiTinhStr);
        }

        RegisterInfoAdapter gioiTinhAdapter = new RegisterInfoAdapter(gioiTinh, getActivity());
        mSpGioiTinh.setAdapter(gioiTinhAdapter);
        mSpGioiTinh.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
                mIdGioiTinh = gioiTinh.get(position).getParType();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            mIdNo = (String) bundle.getSerializable("mIdNo");
            failCustomerInfo = bundle.getString("failCustomerInfo", failCustomerInfo);
            idType = (String) bundle.getSerializable("idType");
            DoUHaveMoreThan3SubAsyn doUHaveMoreThan3SubAsyn = new DoUHaveMoreThan3SubAsyn(getActivity(),
                    new OnPostDoUHaveMorethan(), moveLogInAct);
            doUHaveMoreThan3SubAsyn.execute(mIdNo + "", idType + "");
            AsyncGetOptionSetValue asyncGetOptionSetValue = new AsyncGetOptionSetValue(getActivity(),
                    new OnPostGetOptionSetValue(), moveLogInAct);
            asyncGetOptionSetValue.execute("IDTYPE_FIELD_USAGE");
            mISDN = (String) bundle.getSerializable("mISDN");
            oldCus = (CustomerDTO) bundle.getSerializable("customerOld");
            mKHVersion = bundle.getInt("KHVersion");

            // omni
            this.omniTaskId = (String) bundle.getSerializable("omniTaskId");
            this.omniProcessId = (String) bundle.getSerializable("omniProcessId");
            if (oldCus != null) {
                CheckCusQuotaAsync asy = new CheckCusQuotaAsync(getContext(), mIdNo);
                asy.execute();
                custId = oldCus.getCustId() + "";

                name = oldCus.getName();
                sex = oldCus.getSex();

                if (oldCus.getListCustIdentity() != null && oldCus.getListCustIdentity().size() > 0) {
                    idIssueDate = StringUtils.convertDate(oldCus.getListCustIdentity().get(0).getIdIssueDate());
                    idIssuePlace = oldCus.getListCustIdentity().get(0).getIdIssuePlace();
                }

                area = new AreaObj();
                area.setProvince(oldCus.getProvince());
                area.setDistrict(oldCus.getDistrict());
                area.setPrecinct(oldCus.getPrecinct());
                area.setStreetBlock(oldCus.getStreetBlock());
                if(oldCus.getCustAdd() != null){
					area.setStreet(oldCus.getCustAdd().getStreet());
					area.setStreetName(oldCus.getCustAdd().getStreet());
                    area.setHome(oldCus.getCustAdd().getNumber());
                    tvAddress.setText(oldCus.getCustAdd().getFullAddress());
                }
            }

            mPositionLoaiGiayTo = bundle.getInt("mPositionLoaiGiayTo");
            sub = (SubscriberDTO) bundle.getSerializable("subscriber");
            if (mKHVersion == 1) {
                mEdtTenKH.setText(name);
                String strDate = DateTimeUtils.convertDate(oldCus.getBirthDate());
                mEdtNgaySinh.setText(strDate);
                if ("M".equals(sex)) {
                    mSpGioiTinh.setSelection(0);
                } else {
                    mSpGioiTinh.setSelection(1);
                }
                //added by diepdc
                if (!CommonActivity.isNullOrEmpty(oldCus.getCustId())) {
                    //menu.tdttkh
                    SharedPreferences preferences = getActivity().getSharedPreferences(
                            Define.PRE_NAME, Activity.MODE_PRIVATE);
                    String name = preferences.getString(Define.KEY_MENU_NAME, "");
                    if (name.contains(";menu.tdttkh;")) {
                        setEnabledView(true);
                    } else {
                        setEnabledView(false);
                    }
                } else {
                    setEnabledView(false);
                }
                //end
                edtnote.setText(oldCus.getDescription());
            } else {
                setEnabledView(true);
            }
            mEdtNgayCap.setText(idIssueDate);
            mEdtNoiCap.setText(idIssuePlace);

            mEdtMaGiayTo.setText(mIdNo);
            mEdtISDN.setText(mISDN);
            mEdtISDN.setEnabled(false);
            mEdtMaGiayTo.setEnabled(false);
            mTvGoiCuoc.setText(sub.getProductCode());
        }
        if (RegisterInfoFragment.mLoaiGiayTo.size() > 0) {
            mSpLoaiGiayTo.setText(RegisterInfoFragment.mLoaiGiayTo.get(
                    mPositionLoaiGiayTo).getParValue());
        }

        AsynctaskGetListReason as = new AsynctaskGetListReason(getActivity());
        as.execute();

        if (oldCus != null) {
            lnChinhChu.setVisibility(View.VISIBLE);
        } else {
            lnChinhChu.setVisibility(View.GONE);
        }

        lnChinhChu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (!CommonActivity.isNullOrEmpty(arrSubscriberDTO)) {
                    showSelectIsdnOwner();
                } else {
                    FindVerifiedOwnerByListIdNoAsyn findVerifiedOwnerByListIdNoAsyn =
                            new FindVerifiedOwnerByListIdNoAsyn(getContext());
                    findVerifiedOwnerByListIdNoAsyn.execute();
                }

            }
        });

        try {

            int typeCode = sub.getType().intValue();

            // test truong hop dac biet
            // typeCode = 1;
            switch (typeCode) {
                case 0:
                    // goi cuoc thuong
                    mLnGoiDacBiet.setVisibility(View.GONE);
                    lnHocSinh.setVisibility(View.GONE);
                    break;
                case 1:
                    // goi cuoc dac biet
                    mLnGoiDacBiet.setVisibility(View.VISIBLE);
                    lnHocSinh.setVisibility(View.GONE);
                    // Test mProductCode = SVN
                    // mProductCode = "SVN";
                    getListObjectByProductCode(sub.getProductCode());
                    break;
                case 2:
                    // goi cuoc hoc sinh
                    mLnGoiDacBiet.setVisibility(View.GONE);
                    lnHocSinh.setVisibility(View.VISIBLE);
                    break;

                default:
                    break;
            }
        } catch (Exception e) {
            Log.e("TAG8", "Exception", e);
        }
        mSpDoiTuong.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {

                mCodeDoiTuong = mArrayListSpec.get(position).getCode();
                mBtnRegister.setVisibility(View.VISIBLE);
                rlquoctich.setVisibility(View.VISIBLE);
                mEdtQuocTich.setText(getString(R.string.viet_nam));
                mEdtQuocTich.setEnabled(false);

                // check neu code = NEW_STU_2015 thi goi ws lay so bao danh va
                // an chon dv di
                if ("NEW_STU_15".equals(mCodeDoiTuong)) {
                    CheckInfoCusSpecialAsyn checkInfoCusSpecialAsyn = new CheckInfoCusSpecialAsyn(
                            getContext());
                    checkInfoCusSpecialAsyn.execute(mIdNo);
                    // TODO XU LY

                } else {
                    // an linear donvi di
                    rlchondonvi.setVisibility(View.VISIBLE);
                    mEdtMaGiayToDacBiet.setEnabled(true);
                    mEdtMaGiayToDacBiet.setText("");
                    mTvDonVi.setText("");
                    mCodeDonVi = "";
//                    mTvDonVi.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });


        lnSelectProfile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reasonId == null) {
                    CommonActivity.toast(getActivity(), R.string.chua_chon_ly_do_dang_ky);
                    return;
                }

                try {
                    if (!validateSubCusInfo()) {
                        return;
                    }
                } catch (Exception e) {
                    Log.e("lnSelectProfile", e.getMessage());
                }

                if (!CommonActivity.isNullOrEmpty(profileBO)
                        && !CommonActivity.isNullOrEmpty(profileBO.getMapListRecordPrepaid())) {
                    profileInfoDialogFragment = new ProfileInfoDialogFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("profileBO", profileBO);
                    bundle.putString("xmlSub", getXmlSub());
                    bundle.putString("xmlCus", getXmlCus());
                    bundle.putString("orderType", Constant.ORD_TYPE_REGISTER_PREPAID);

                    if (!CommonActivity.isNullOrEmpty(oldCus)) {
                        bundle.putString("groupType", oldCus.getGroupType());
                    }

                    profileInfoDialogFragment.setOnFinishDSFListener(onFinishDSFListener);
                    profileInfoDialogFragment.setArguments(bundle);
                    profileInfoDialogFragment.show(getFragmentManager(), "show profile");
                }
            }
        });


        //chon loai doi tuong
        String groupType = "1";

        String code = "";
        if ("1".equals(groupType) || "3".equals(groupType)){
            code = "INDIVIDUAL";
        }
        else if ("2".equals(groupType)){
            code = "BUSINESS";
        }
        AsyncGetOptionSetValueV2 asyncGetOptionSetValue = new AsyncGetOptionSetValueV2(getActivity(), new OnPostGetInfoCustomer(), moveLogInAct);
        asyncGetOptionSetValue.execute(code);



        spnDoituong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinV2 itemAtPosition = (SpinV2) parent.getItemAtPosition(position);
                if(!itemAtPosition.getValue().equals(RegisterNewFragment.this.getString(R.string.chondoituong))) {
                    subObject = itemAtPosition.getId();
                }else{
                    subObject = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayList<String> lstIsdn = new ArrayList<>();
        lstIsdn.add(mISDN);
        SearchInvalidInfoSubcriberAsyncTask searchInvalidInfoSubcriberAsyncTask
                = new SearchInvalidInfoSubcriberAsyncTask(getActivity(), lstIsdn, new OnPostExecuteListener<List<SubInvalidDTO>>() {
            @Override
            public void onPostExecute(List<SubInvalidDTO> result, String errorCode, String description) {
                if(!CommonActivity.isNullOrEmpty(result)){
                    for (SubInvalidDTO subInvalidDTO : result) {
                        if ("1".equals(subInvalidDTO.getStatus())) {
                            CommonActivity.createAlertDialog(getActivity(),
                                    RegisterNewFragment.this.getResources().getString(R.string.thong_bao_thue_bao_sai_thong_tin),
                                    RegisterNewFragment.this.getResources().getString(R.string.app_name)).show();
                                     failCustomerInfo= "true";
                        }
                    }


                }else{
                    if(CommonActivity.isNullOrEmpty(description)){
                        description = RegisterNewFragment.this.getString(R.string.not_result_search);
                    }
                    CommonActivity.createAlertDialog(getActivity(),
                            description,
                            RegisterNewFragment.this.getResources().getString(R.string.app_name)).show();
                }
            }
        }, moveLogInAct);
        searchInvalidInfoSubcriberAsyncTask.execute();

    }

    @Override
    protected void setPermission() {

    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleActionBar(getResources().getString(R.string.manage_register_info));
    }

    private String getTextFromEdt(EditText edt) {
        String text = "";
        if (edt != null) {
            text = edt.getText().toString();
        }
        return text;
    }

    protected void registerNew() throws Exception {

        if (!validateSubCusInfo()) {
            return;
        }

        try {
            Date dateSign = sdf.parse(edit_ngayky.getText().toString());
            if (dateSign.after(dateNow)) {
                CommonActivity.createAlertDialog(getActivity(),
                        this.getString(R.string.signDateEmptyThan), this.getString(R.string.app_name)).show();
                return;
            }
        } catch (Exception e) {
            Log.d("Exception", e.toString());
        }

        //truong hop lay ho so bi loi
        if (CommonActivity.isNullOrEmpty(profileBO.getMapListRecordPrepaid())
                && profileBO.isRequiredUploadImage()) {
            CommonActivity.createAlertDialog(getActivity(),
                    getString(R.string.error_get_profile),
                    getString(R.string.app_name)).show();
            return;
        }

        if (!CommonActivity.isNullOrEmpty(profileBO.getMapListRecordPrepaid())
                && (profileInfoDialogFragment == null || !profileInfoDialogFragment.isFullFile())) {
            CommonActivity.createAlertDialog(getActivity(),
                    getString(R.string.chua_chon_het_anh),
                    getString(R.string.app_name)).show();
            return;
        }
        if (isMoreThan) {
            if (!validateProfileContract()) {
                CommonActivity.createAlertDialog(getActivity(), descriptionNotice,
                        this.getString(R.string.app_name)).show();
                return;
            }
        }
        confrimDK();
    }

    private void confrimDK() {
        CommonActivity.createDialog(getActivity(),
                getString(R.string.confirm_dk), getString(R.string.app_name),
                getString(R.string.say_ko), getString(R.string.say_co),
                null, new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        uploadImage();
                    }
                }).show();
    }

    private boolean validateSubCusInfo() throws Exception {
        String serial = getTextFromEdt(mEdtSerial);
        mISDN = getTextFromEdt(mEdtISDN);

        name = getTextFromEdt(mEdtTenKH);
        mIdNo = getTextFromEdt(mEdtMaGiayTo);
        idIssuePlace = getTextFromEdt(mEdtNoiCap);
        int typeCode1 = sub.getType().intValue();

        // test truong hop dac biet
        // typeCode1 = 1;
        if ("".equals(serial) || serial.length() != 6) {
            CommonActivity.createAlertDialog(getActivity(),
                    R.string.chua_nhap_serial,
                    R.string.app_name).show();
            mEdtSerial.requestFocus();
            return false;
        }
        if (!sub.getSerial().endsWith(serial)) {
            Log.d("SERIAL", sub.getSerial());
            CommonActivity.createAlertDialog(getActivity(), R.string.serial_not_match,
                    R.string.app_name).show();
            mEdtSerial.requestFocus();
            return false;
        }

        if (reasonId == null) {
            CommonActivity.createAlertDialog(getActivity(),
                    R.string.chua_chon_ly_do_dang_ky, R.string.app_name).show();
            return false;
        }

        if (CommonActivity.isNullOrEmpty(name)) {
            CommonActivity.createAlertDialog(getActivity(), R.string.chua_nhap_ten_kh,
                    R.string.app_name).show();
            mEdtTenKH.requestFocus();
            return false;
        }

        if (StringUtils.CheckCharSpecical(name)) {
            CommonActivity.createAlertDialog(getActivity(), getResources().getString(R.string.checkcharspecical),
                    getResources().getString(R.string.app_name)).show();
            mEdtTenKH.requestFocus();
            return false;
        }

        if (mEdtTenKH.getText().toString().length() < 4) {
            CommonActivity.createAlertDialog(getActivity(), getResources().getString(R.string.namecust),
                    getResources().getString(R.string.app_name)).show();
            return false;
        }

        if (CommonActivity.isNullOrEmpty(mEdtNgaySinh.getText().toString())) {
            CommonActivity.createAlertDialog(getActivity(), R.string.cus_birthday_empty,
                    R.string.app_name).show();
            mEdtNgaySinh.requestFocus();
            return false;
        }

        Date birthDate = sdf.parse(mEdtNgaySinh.getText().toString().trim());
        if (birthDate.after(dateNow)) {
            CommonActivity.createAlertDialog(getActivity(),
                    getResources().getString(R.string.nsnhohonhtai),
                    getResources().getString(R.string.app_name)).show();
            return false;
        }

        if (oldCus == null && CommonActivity.isNullOrEmpty(mEdtNgayCap.getText().toString())) {
            CommonActivity.createAlertDialog(getActivity(),
                    R.string.cus_id_issue_date_empty, R.string.app_name).show();
            mEdtNgayCap.requestFocus();
            return false;
        }

        if (CommonActivity.isNullOrEmpty(idIssuePlace) && oldCus == null) {
            CommonActivity.createAlertDialog(getActivity(), R.string.chua_nhap_noi_cap,
                    R.string.app_name).show();
            mEdtNoiCap.requestFocus();
            return false;
        }
        if (oldCus == null) {
            Date datengaycap = sdf.parse(mEdtNgayCap.getText().toString().trim());
            if (datengaycap.after(dateNow)) {
                CommonActivity.createAlertDialog(getActivity(),
                        getString(R.string.ngaycapnhohonngayhientai),
                        getString(R.string.app_name))
                        .show();
                return false;
            }

            if (datengaycap.before(birthDate)) {
                CommonActivity.createAlertDialog(getActivity(), getString(R.string.checkcmtngaycap),
                        getString(R.string.app_name)).show();
                return false;
            }

            if ("ID".equals(idType)) {
                if (CommonActivity.getDiffYearsMain(birthDate, dateNow) < 14) {
                    CommonActivity.createAlertDialog(getActivity(), this.getString(R.string.khdd14),
                            this.getString(R.string.app_name)).show();
                    return false;
                }

                if (CommonActivity.getDiffYearsMain(birthDate, datengaycap) < 14) {
                    CommonActivity.createAlertDialog(getActivity(), getString(R.string.checkcmtngaycap14),
                            getString(R.string.app_name)).show();
                    return false;
                }
                if (CommonActivity.getDiffYears(datengaycap, dateNow) > 15) {
                    CommonActivity.createAlertDialog(getActivity(),
                            getString(R.string.checkcmtngaycap15),
                            getString(R.string.app_name)).show();
                    return false;
                }
            }
            if ("MID".equals(idType)) {
                if (idNo.length() < 6 || idNo.length() > 12) {
                    CommonActivity.createAlertDialog(getActivity(),
                            getString(R.string.checkcmtca),
                            getString(R.string.app_name)).show();
                    return false;
                }
            }
            // validate ngay het han
            if (!CommonActivity.isNullOrEmpty(arrMapusage)) {
                for (Spin spin : arrMapusage) {
                    if (CommonActivity.checkMapUsage(idType, spin)) {
                        // truong hop ma bat buoc nhap nhat het han thi phai validate
                        if (CommonActivity.isNullOrEmpty(edit_ngayhethan.getText().toString())) {
                            CommonActivity.createAlertDialog(getActivity(),
                                    getString(R.string.validateExpiredate),
                                    getString(R.string.app_name)).show();
                            return false;
                        }
                        Date datenExpired = sdf.parse(edit_ngayhethan.getText().toString().trim());
                        if (datenExpired.before(datengaycap)) {
                            CommonActivity.createAlertDialog(getActivity(),
                                    getString(R.string.checkhethanngaycap),
                                    getString(R.string.app_name)).show();
                            return false;
                        }
                        if (datenExpired.before(dateNow)) {
                            CommonActivity.createAlertDialog(getActivity(),
                                    this.getString(R.string.checkhethanhientai),
                                    this.getString(R.string.app_name)).show();
                            return false;
                        }
                        break;
                    }
                }
            }
        }

        if (area == null) {
            CommonActivity.createAlertDialog(getActivity(),
                    R.string.chua_nhap_dia_chi,
                    R.string.app_name).show();
            mEdtNoiCap.requestFocus();
            return false;
        }

        if (typeCode1 == 2) {
            if (CommonActivity.isNullOrEmpty(edtNameHS.getText().toString())) {

                CommonActivity.createAlertDialog(getActivity(),
                        R.string.nhap_ten_hoc_sinh, R.string.app_name).show();
                edtNameHS.requestFocus();
                return false;
            }
            if (CommonActivity.isNullOrEmpty(edtAgeHS.getText().toString())) {
                CommonActivity.createAlertDialog(getActivity(),
                        R.string.pupil_birthday_not_null, R.string.app_name).show();
                edtNameHS.requestFocus();
                return false;
            }
        }

        if (typeCode1 == 1) {
            if (CommonActivity.isNullOrEmpty(mEdtMaGiayToDacBiet.getText()
                    .toString())) {
                CommonActivity.createAlertDialog(getActivity(),
                        R.string.nhap_ma_giay_to_dac_biet, R.string.app_name)
                        .show();
                mEdtMaGiayToDacBiet.requestFocus();
                return false;
            }
            if (CommonActivity.isNullOrEmpty(mCodeDoiTuong)) {
                CommonActivity.createAlertDialog(getActivity(),
                        R.string.nhap_ten_doi_tuong, R.string.app_name).show();

                return false;
            }
            if (CommonActivity.isNullOrEmpty(mCodeDonVi)) {
                CommonActivity.createAlertDialog(getActivity(),
                        R.string.nhap_ten_don_vi, R.string.app_name).show();
                return false;
            }

            if (CommonActivity.isNullOrEmpty(mEdtNgayBD.getText().toString())) {
                CommonActivity.createAlertDialog(getActivity(),
                        R.string.nhap_ngay_bat_dau, R.string.app_name).show();
                mEdtNgayBD.requestFocus();
                return false;
            }
            if (CommonActivity.isNullOrEmpty(mEdtNgayKT.getText().toString())) {
                CommonActivity.createAlertDialog(getActivity(),
                        R.string.nhap_ngay_ket_thuc, R.string.app_name).show();
                mEdtNgayKT.requestFocus();
                return false;
            }
            if (CommonActivity.isNullOrEmpty(mEdtQuocTich.getText().toString())) {
                CommonActivity.createAlertDialog(getActivity(),
                        R.string.nhap_quoc_tich,
                        R.string.app_name).show();
                mEdtQuocTich.requestFocus();
                return false;
            }
        }

        if (CommonActivity.isNullOrEmpty(subObject)) {
            CommonActivity.createAlertDialog(getActivity(),
                    getResources().getString(R.string.doituong_not_select),
                    getResources().getString(R.string.app_name)).show();
            return false;
        }

        return true;
    }

    private void uploadImage() {
        timeStart = new Date();

        if (!CommonActivity.isNullOrEmpty(omniProcessId)
                || CommonActivity.isNullOrEmpty(profileBO.getProfileRecords())) {
            new AsyncTaskDK(getActivity()).execute();
            return;
        }

        PlaceOrderAsyncTask placeOrderAsyncTask =
                new PlaceOrderAsyncTask(getActivity(), new OnPostExecuteListener<String>() {
            @Override
            public void onPostExecute(String result, String errorCode, String description) {
                if("0".equals(errorCode)){
                    // neu thanh cong thi goi ham dk
                    if(!CommonActivity.isNullOrEmpty(result)){
                        omniProcessId = result;
                        // goi ham dang ky o day
                        new AsyncTaskDK(getActivity()).execute();
                    } else {
                        CommonActivity.createAlertDialog(getActivity(),
                                getActivity().getString(R.string.notdataomniprocessid),
                                getActivity().getString(R.string.app_name)).show();
                    }
                }else{
                    CommonActivity.createAlertDialog(getActivity(), description,
                            getActivity().getString(R.string.app_name)).show();
                }
            }
        }, null, "HSDT", profileBO.getProfileRecords());

        String cusName = StringUtils.xmlEscapeText(getTextFromEdt(mEdtTenKH));
        placeOrderAsyncTask.execute(cusName, Constant.ORD_TYPE_REGISTER_PREPAID);
    }

    private ProfileBO initProfileBO() {
        horizontalScrollView.setVisibility(View.GONE);
        profileBO = new ProfileBO();
        profileBO.clearData();
        profileBO.getLstActionCode().add(Constant.ACTION_CODE.REGISTER_INFORMATION);
        profileBO.getLstReasonId().add(reasonId);
        profileBO.setParValue(Constant.POSTPAID.equals(sub.getPayType())
                ? sub.getSubType() : sub.getProductCode());
        profileBO.setPayType(sub.getPayType());
        profileBO.setCustType(sub.getCustType());
        profileBO.setServiceType("M");
        profileBO.setIdNo(mIdNo);
        return profileBO;
    }

    private void setEnabledView(boolean b) {
        mEdtTenKH.setEnabled(b);
        mEdtNgayCap.setEnabled(b);
        mEdtNoiCap.setEnabled(b);
        mEdtNgaySinh.setEnabled(b);
        mSpGioiTinh.setEnabled(b);
        edit_ngayhethan.setEnabled(b);
        edtnote.setEnabled(b);
        tvAddress.setEnabled(b);
    }

    protected void timKiemDonVi() {
        dialogLockBoxInfo();
    }

    private void dialogLockBoxInfo() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View dialogView = inflater.inflate(R.layout.dialog_don_vi, null);

        builder.setView(dialogView);
        dialogDonVi = builder.create();

        final EditText edtMaDonVi = (EditText) dialogView
                .findViewById(R.id.edtMaDonVi);
        final EditText edtTenDonVi = (EditText) dialogView
                .findViewById(R.id.edtTenDonVi);
        Button btnTimKiem = (Button) dialogView.findViewById(R.id.btnTimKiem);
        lvListDonVi = (ListView) dialogView.findViewById(R.id.lvLockBoxInfo);

        btnTimKiem.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String maDonVi = edtMaDonVi.getText().toString().trim();
                String maTenVi = edtTenDonVi.getText().toString().trim();

                hideKeyBoard();
                if (CommonActivity.isNullOrEmpty(maDonVi) && CommonActivity.isNullOrEmpty(maTenVi)) {
                    Toast.makeText(getActivity(),
                            getString(R.string.chua_nhap_ma_dvi),
                            Toast.LENGTH_LONG).show();
                } else {
                    getDonVi(maDonVi, maTenVi);
                }
                // dialogDonVi.dismiss();
            }
        });
        lvListDonVi.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                mCodeDonVi = mAratListDonVi.get(position).getCode();
                mTvDonVi.setText(mAratListDonVi.get(position).getCodeName());

                dialogDonVi.dismiss();
            }
        });

        dialogDonVi.show();

    }

    protected void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager)
                getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    protected void getDonVi(String maDonVi, String tenDonVi) {
        new GetLisDonVi(maDonVi, tenDonVi).execute();
    }

    public class GetLisDonVi extends
            AsyncTask<Void, Void, ArrayList<DoiTuongObj>> {
        private final String maDonVi;
        private final String tenDonVi;
        private final ProgressDialog dialog;

        public GetLisDonVi(String maDonVi, String tenDonVi) {
            this.maDonVi = maDonVi;
            this.tenDonVi = tenDonVi;
            dialog = new ProgressDialog(getActivity());
            dialog.setCancelable(false);
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage(getResources().getString(R.string.waitting));
            if (!this.dialog.isShowing())
                this.dialog.show();
            super.onPreExecute();
        }

        @Override
        protected ArrayList<DoiTuongObj> doInBackground(Void... params) {
            BhldDAL dal = null;
            try {
                dal = new BhldDAL(getActivity());
                dal.open();
                return dal.getListDeptObjectBCCSFromCode(mCodeDoiTuong, maDonVi, tenDonVi);
            } catch (Exception e) {
                Log.e("TAG8", "Exception", e);
                return null;
                // TODO: handle exception
            } finally {
                if (dal != null) {
                    dal.close();
                }
            }
        }

        @Override
        protected void onPostExecute(ArrayList<DoiTuongObj> result) {
            try {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                mAratListDonVi = result;
                if (CommonActivity.isNullOrEmpty(mAratListDonVi)) {
                    CommonActivity.createAlertDialog(
                            getActivity(),
                            getResources().getString(
                                    R.string.ko_tim_thay_don_vi_nao),
                            getResources().getString(R.string.app_name)).show();
                }
                DonViAdapter adapter = new DonViAdapter(
                        getActivity(), mAratListDonVi);
                lvListDonVi.setAdapter(adapter);

            } catch (Exception e) {
                Log.e("TAG8", "Exception", e);
            }

            super.onPostExecute(result);
        }
    }

    private void getListObjectByProductCode(String productCode) {
        new GetLisObjectByProductCode(productCode).execute();
    }

    public class GetLisObjectByProductCode extends
            AsyncTask<Void, Void, String> {

        private final String productCode;
        private final ProgressDialog dialog;

        public GetLisObjectByProductCode(String productCode) {
            this.productCode = productCode;
            dialog = new ProgressDialog(getActivity());
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage(getResources().getString(R.string.waitting));
            this.dialog.setCancelable(false);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            String original = requestSeviceObjectByProductCode(productCode);
            return original;
        }

        @Override
        protected void onPostExecute(String result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            try {
                if (CommonActivity.isNullOrEmpty(result)) {
                    CommonActivity.createAlertDialog(getActivity(),
                            getResources().getString(R.string.errorNetwork),
                            getResources().getString(R.string.app_name)).show();
                } else {

                    Serializer serializer = new Persister();
                    SaleOutput saleOutput = serializer.read(SaleOutput.class,
                            result);

                    if ("0".equals(saleOutput.getErrorCode())) {
                        mArrayListSpec = saleOutput.getLstObject();


                        Log.e("TAG6", "result List productCode : " + result);

                        if (mArrayListSpec != null && mArrayListSpec.size() > 0) {
                            for (SpecObject item : mArrayListSpec) {
                                if ("NEW_STU_15".equals(item.getCode())) {
                                    mArrayListSpec.remove(item);
                                    break;
                                }
                            }

                            mCodeDoiTuong = mArrayListSpec.get(0).getCode();
                            ObjDoiTuongAdapter doiTuongAdapter = new ObjDoiTuongAdapter(
                                    mArrayListSpec, getActivity());
                            mSpDoiTuong.setAdapter(doiTuongAdapter);

                            for (SpecObject specObject : mArrayListSpec) {
                                // if ("IS_SPECIAL_PRODUCT".equals(checkIsSpec)){
                                if ("SV_VN".equals(specObject.getCode())) {
                                    mSpDoiTuong.setSelection(mArrayListSpec.indexOf(specObject));
                                    mSpDoiTuong.setEnabled(true);
                                    mCodeDoiTuong = specObject.getCode();
                                    break;
                                }
                            }
                            doiTuongAdapter.notifyDataSetChanged();
                        }
                    } else {
                        String description = saleOutput.getDescription();
                        if (CommonActivity.isNullOrEmpty(description)) {
                            description = getString(
                                    R.string.fails_not_description,
                                    getString(R.string.getListObjectByProductCode));
                        }
                        CommonActivity.createErrorDialog(
                                getActivity(), description,
                                saleOutput.getErrorCode()).show();
                    }
                }
            } catch (Exception e) {
                Log.e("exceptionnnnn", "aaaaaaaaaa", e);
            }
            super.onPostExecute(result);
        }
    }

    public String requestSevice(String productCode) {
        String reponse = null;
        String original = null;
        String xml = mBccsGateway.getXmlCustomer(createXML(productCode),
                "mbccs_getListRecordPrepaid");
        Log.e("TAG8", "xml getListRecordPrepaid" + xml);
        try {
            reponse = mBccsGateway.sendRequest(xml, Constant.BCCS_GW_URL,
                    getActivity(), "mbccs_getListRecordPrepaid");
            Log.e("TAG8", "reponse getListRecordPrepaid" + reponse);
        } catch (Exception e) {

            Log.e("TAG8", "Exception", e);
        }
        if (reponse != null) {
            CommonOutput commonOutput;
            try {
                commonOutput = mBccsGateway.parseGWResponse(reponse);
                original = commonOutput.getOriginal();
            } catch (Exception e) {
                Log.e("TAG8", "Exception", e);
            }

        }
        return original;
    }

    private String requestSeviceObjectByProductCode(String productCode) {

        String reponse = null;
        String original = null;

        String xml = mBccsGateway.getXmlCustomer(
                createXMLObjectByProductCode(productCode),
                "mbccs_getListObjectByProductCodeBccs2");
        Log.e("TAG89", "xml getListObjectByProductCode" + xml);
        try {
            reponse = mBccsGateway
                    .sendRequest(xml, Constant.BCCS_GW_URL,
                            getActivity(), "mbccs_getListObjectByProductCodeBccs2");
            Log.e("TAG8", "reponse getListRecordPrepaid" + reponse);
        } catch (Exception e) {

            Log.e("TAG8", "reponse getListRecordPrepaid", e);
        }
        if (reponse != null) {
            CommonOutput commonOutput;
            try {
                commonOutput = mBccsGateway.parseGWResponse(reponse);
                original = commonOutput.getOriginal();
            } catch (Exception e) {
                Log.e("TAG8", "Exception", e);
            }

        }
        return original;
    }

    private String createXMLObjectByProductCode(String productCode) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<ws:getListObjectByProductCodeBccs2>");
        stringBuilder.append("<input>");
        stringBuilder.append("<token>" + Session.getToken() + "</token>");
        stringBuilder.append("<productCode>" + productCode + "</productCode>");
        stringBuilder.append("<subType>" + "");
        stringBuilder.append("</subType>");
        stringBuilder.append("</input>");
        stringBuilder.append("</ws:getListObjectByProductCodeBccs2>");
        return stringBuilder.toString();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG 9", "requestCode : " + requestCode + " resultCode : " + resultCode);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CHOOSE_REASON:
                    profileBO = new ProfileBO();
                    profileBO.clearData();
                    if (profileInfoDialogFragment != null) {
                        profileInfoDialogFragment.setFullFile(false);
                    }
                    ReasonDTO reasonDTO = (ReasonDTO) data.getExtras().getSerializable("mReasonObj");
                    if (reasonDTO == null || reasonDTO.getReasonId() == null || "".equals(reasonDTO.getReasonId())) {
                        return;
                    }
                    if (!TextUtils.isEmpty(reasonDTO.getReasonCode())) {
                        spnReason.setText(reasonDTO.getReasonCode() + " - " + reasonDTO.getName());
                    } else {
                        spnReason.setText("" + reasonDTO.getName());
                    }
                    reasonId = Long.parseLong(reasonDTO.getReasonId());
                    if (reasonId.compareTo(-1L) != 0) {
                        new AsynTaskGetListRecordProfile(getActivity(), new OnPostExecuteListener<Map<String, ArrayList<RecordPrepaid>>>() {
                        @Override
                        public void onPostExecute(Map<String, ArrayList<RecordPrepaid>> result, String errorCode, String description) {
                            if (CommonActivity.isNullOrEmpty(result)) {
                                profileBO.setRequiredUploadImage(false);
                                lnSelectProfile.setVisibility(View.GONE);
                            } else {
                                lnSelectProfile.setVisibility(View.VISIBLE);
                                profileBO.setMapListRecordPrepaid(result);
                                if (isMoreThan) {
                                    profileBO.removeRecordByCode("PYCTT");
                                }
                            }
                        }
                    }, null, initProfileBO()).execute();
                    } else {
                        profileBO.getHashmapFileObj().clear();
                        reasonId = null;
                    }
                    break;
                case CHANNEL_UPDATE_IMAGE.PICK_IMAGE:
                    profileInfoDialogFragment.onActivityResult(requestCode, resultCode, data);
                    break;
                case REQUEST_CHOOSE_ADDRESS:
                    AreaObj province = (AreaObj) data.getExtras().getSerializable("areaProvicial");
                    AreaObj district = (AreaObj) data.getExtras().getSerializable("areaDistrist");
                    AreaObj precinct = (AreaObj) data.getExtras().getSerializable("areaPrecint");
                    AreaObj streetBlock = (AreaObj) data.getExtras().getSerializable("areaGroup");
                    String home = data.getExtras().getString("areaHomeNumber");
                    String streetName = data.getExtras().getString("areaFlow");
                    String address = precinct.getName() + " " + district.getName() + " " + province.getName();
                    if (streetBlock != null && !CommonActivity.isNullOrEmpty(streetBlock.getName())) {
                        address = streetBlock.getName() + " " + address;
                    }
                    if (!CommonActivity.isNullOrEmpty(streetName)) {
                        address = streetName + " " + address;
                    }
                    if (!CommonActivity.isNullOrEmpty(home)) {
                        address = home + " " + address;
                    }

                    tvAddress.setText(address);
                    area = new AreaObj();
                    area.setProvince(province.getProvince());
                    area.setProvinceName(province.getName());
                    area.setDistrict(district.getDistrict());
                    area.setDistrictName(district.getName());
                    area.setStreetName(streetName);
                    area.setStreet(streetName);
                    area.setHome(home);
                    area.setPrecinct(precinct.getPrecinct());
                    area.setPrecinctName(precinct.getName());
                    area.setName(address);
                    if (streetBlock != null) {
                        area.setStreetBlock(streetBlock.getAreaCode());
                    }

                    break;
                case 5555:
                    String contents = data.getStringExtra("SCAN_RESULT");
                    if (!TextUtils.isEmpty(contents) && contents.length() > 6) {
                        contents = contents.substring(contents.length() - 6, contents.length());
                    }
                    mEdtSerial.setText(contents);
                    mEdtSerial.requestFocus();
                    try {
                        SaveLog saveLog = new SaveLog(getActivity(),
                                Constant.SYSTEM_NAME, Session.userName,
                                "changeSim_barcode", CommonActivity.findMyLocation(
                                getActivity()).getX(), CommonActivity
                                .findMyLocation(getActivity()).getY());
                        saveLog.saveLogRequest(
                                " barcode scan serial : " + contents,

                                new Date(), new Date(), Session.userName + "_"
                                        + CommonActivity.getDeviceId(getActivity())
                                        + "_" + System.currentTimeMillis());
                    } catch (Exception e) {
                        Log.d("saveLog", e.toString());
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }

    private String actionAuditId = "";

    private class AsyncTaskDK extends AsyncTask<String, String, String> {

        private Context context;
        private ArrayList<String> lstFilePath = new ArrayList<String>();

        public AsyncTaskDK(Context context) {
            this.context = context;
            progressDangKy = new ProgressDialog(this.context);
            progressDangKy.setMessage(context.getResources().getString(
                    R.string.registering));
            progressDangKy.setCancelable(false);
            if (!progressDangKy.isShowing()) {
                progressDangKy.show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            progressDangKy.setMessage(values[0]);
            Log.i("makemachine",
                    "onProgressUpdate(): " + String.valueOf(values[0]));
        }

        @Override
        protected String doInBackground(String... params) {
            if (profileBO.getHashmapFileObj() != null
                    && !profileBO.getHashmapFileObj().isEmpty()) {
                lstFilePath = FileUtils.createFilePaths(profileBO.getHashmapFileObj());
            }

            String xml = "";
            try {
                xml = createXMLDangKy(lstFilePath);
            } catch (Exception e) {
                Log.e("TAG8", "Exception", e);
            }

            String original = null;

            publishProgress(context.getResources().getString(
                    R.string.progress_upload));
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_doUpdateCustomer");

            try {
                String reponse = input.sendRequest(xml, Constant.BCCS_GW_URL,
                        getActivity(), "mbccs_doUpdateCustomer");
                Log.e("TAG868686", "reponse SeviceDangKy" + reponse);

                CommonOutput output = input.parseGWResponse(reponse);
                original = output.getOriginal();
                return original;
            } catch (Exception e) {
                Log.e("Exception", "Exception", e);
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                Log.i("TAG 99 DK", " result : " + result);
                if (progressDangKy != null && progressDangKy.isShowing()) {
                    progressDangKy.dismiss();
                }
                if (CommonActivity.isNullOrEmpty(result)) {
                    CommonActivity.createAlertDialog(getActivity(),
                            getString(R.string.errorNetwork),
                            getString(R.string.app_name)).show();
                } else {
                    Serializer serializer = new Persister();
                    ParseOuput parseOuput = serializer.read(ParseOuput.class, result);
                    if (parseOuput == null
                            || CommonActivity.isNullOrEmpty(parseOuput.getErrorCode())) {
                        parseOuput = new ParseOuput();
                        parseOuput.setDescription(
                                getString(R.string.no_return_from_system));
                        parseOuput.setErrorCode(Constant.ERROR_CODE);
                        return;
                    }
                    if ("0".equals(parseOuput.getErrorCode())) {
                        actionAuditId = parseOuput.getDescription();
                        new AsynTaskZipFile(getActivity(),
                                onPostExecuteListener,
                                moveLogInAct,
                                profileBO.getHashmapFileObj(),
                                lstFilePath).execute();

                    } else {
                        if (arrFileBackUp != null && arrFileBackUp.size() > 0) {
                            for (FileObj fileObj : arrFileBackUp) {
                                File tmp = new File(fileObj.getPath());
                                tmp.delete();
                            }
                        }
                        CommonActivity.createAlertDialog(getActivity(),
                                parseOuput.getDescription(),
                                getString(R.string.app_name)).show();
                    }

                }
            } catch (Exception e) {
                Log.d(Constant.TAG, "error", e);
                CommonActivity.createAlertDialog(getActivity(),
                        getString(R.string.exception) + " " + e.toString(),
                        getString(R.string.app_name)).show();
            }
        }
    }

    OnPostExecuteListener<ArrayList<FileObj>> onPostExecuteListener = new OnPostExecuteListener<ArrayList<FileObj>>() {
        @Override
        public void onPostExecute(ArrayList<FileObj> result, String errorCode, String description) {
            if (Constant.RESPONSE_CODE.SUCCESS.equals(errorCode)) {
                if (!CommonActivity.isNullOrEmpty(result)) {
                    for (FileObj fileObj : result) {
                        fileObj.setActionCode(Constant.ACTION_CODE.REGISTER_INFORMATION); //fileObj.setActionCode("03");
                        fileObj.setReasonId(reasonId + "");
                        fileObj.setIsdn(mISDN);
                        fileObj.setActionAudit(actionAuditId);
                        fileObj.setPageSize(0 + "");
                        fileObj.setStatus(0);
                    }

                    mBtnRegister.setVisibility(View.GONE);
                    String des = getString(R.string.register_success);
                    new AsyncTaskUpdateImageOfline(getActivity(), result,
                            null, des + "\n").execute();
                }
            }
        }
    };


    private String createXML(String productCode) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<ws:getListRecordPrepaid>");
        stringBuilder.append("<cmInput>");
        stringBuilder.append("<token>" + Session.getToken() + "</token>");
        stringBuilder.append("<productCode>" + productCode + "</productCode>");
        stringBuilder.append("<reasonId>" + reasonId + "</reasonId>");
        stringBuilder.append("</cmInput>");
        stringBuilder.append("</ws:getListRecordPrepaid>");
        return stringBuilder.toString();
    }


    private String createXMLDangKy(ArrayList<String> mlstFilePath) throws Exception {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<ws:doUpdateCustomer>");
        stringBuilder.append("<cmInput>");
        if (oldCus != null && !CommonActivity.isNullOrEmpty(arrSubscriberDTO)) {
            for (SubscriberDTO item : arrSubscriberDTO) {
                if (!item.isHasVerifiedOwner()) {
                    stringBuilder.append("<lstSub>");
                    stringBuilder.append("<subId>");
                    stringBuilder.append(item.getSubId());
                    stringBuilder.append("</subId>");
                    stringBuilder.append("</lstSub>");
                }
            }
        }
        stringBuilder.append("<token>" + Session.getToken() + "</token>");
        stringBuilder.append("<reasonId>" + reasonId + "</reasonId>");

        for (String fileObj : mlstFilePath) {
            stringBuilder.append("<lstFilePath>");
            stringBuilder.append(fileObj);
            stringBuilder.append("</lstFilePath>");
        }
        for (java.util.Map.Entry<String, ArrayList<FileObj>> entry : profileBO.getHashmapFileObj().entrySet()) {
            ArrayList<FileObj> listFileObjs = entry.getValue();
            if (!CommonActivity.isNullOrEmpty(listFileObjs)) {
                stringBuilder.append("<lstRecordName>");
                stringBuilder.append(listFileObjs.get(0).getRecodeName());
                stringBuilder.append("</lstRecordName>");
                stringBuilder.append("<lstRecordCode>");
                stringBuilder.append(listFileObjs.get(0).getCodeSpinner());
                stringBuilder.append("</lstRecordCode>");
            }
        }

        stringBuilder.append("<fileContent>");
        stringBuilder.append("");
        stringBuilder.append("</fileContent>");

        stringBuilder.append("<cusDTO>");
        stringBuilder.append(getXmlCus());
        stringBuilder.append("</cusDTO>");

        stringBuilder.append("<subDTO>");
        //them laoi doi tuong
        stringBuilder.append("<subObject>");
        stringBuilder.append(subObject);
        stringBuilder.append("</subObject>");
        stringBuilder.append(getXmlSub());
        stringBuilder.append("</subDTO>");

        stringBuilder.append("</cmInput>");
        stringBuilder.append("</ws:doUpdateCustomer>");
        Log.d("TAG RegisterNewFragment", "stringBuilder.toString()"
                + stringBuilder.toString());
        String xml = mBccsGateway.getXmlCustomer(stringBuilder.toString(),
                "mbccs_doUpdateCustomer");
        return xml;
    }

    private class CheckInfoCusSpecialAsyn extends
            AsyncTask<String, Void, ArrayList<StudentBean>> {

        ProgressDialog progress;
        private Context context = null;
        XmlDomParse parse = new XmlDomParse();
        String errorCode = "";
        String description = "";

        public CheckInfoCusSpecialAsyn(Context context) {
            this.context = context;
            this.progress = new ProgressDialog(this.context);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(
                    R.string.waitting));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected ArrayList<StudentBean> doInBackground(String... arg0) {
            return sendReqCheckInfoCus(arg0[0]);
        }

        @Override
        protected void onPostExecute(final ArrayList<StudentBean> result) {
            progress.dismiss();
            if ("0".equalsIgnoreCase(errorCode)) {
                if (result != null && !result.isEmpty()) {

                    final Dialog dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.connection_layout_lst_sobaodanh);

                    ListView lvsobaodanh = (ListView) dialog
                            .findViewById(R.id.lvsobaodanh);

                    Button btnhuy = (Button) dialog.findViewById(R.id.btnhuy);

                    GetSobaodanhAdapter getSobaodanhAdapter = new GetSobaodanhAdapter(
                            result, getActivity());
                    lvsobaodanh.setAdapter(getSobaodanhAdapter);

                    lvsobaodanh
                            .setOnItemClickListener(new OnItemClickListener() {

                                @Override
                                public void onItemClick(AdapterView<?> arg0,
                                                        View arg1, int arg2, long arg3) {
                                    StudentBean studentBean = result.get(arg2);
                                    if (!CommonActivity
                                            .isNullOrEmpty(studentBean
                                                    .getSoBD())) {
                                        mEdtMaGiayToDacBiet.setText(studentBean
                                                .getSoBD());
                                        mEdtMaGiayToDacBiet.setEnabled(false);
                                    }

                                    if (!CommonActivity
                                            .isNullOrEmpty(studentBean
                                                    .getMaTruongDKTT())) {
                                        mCodeDonVi = studentBean
                                                .getMaTruongDKTT();
                                        mTvDonVi.setText(mCodeDonVi);
                                    }

                                    dialog.dismiss();
                                }
                            });

                    btnhuy.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();

                } else {
                    mBtnRegister.setVisibility(View.GONE);
                    Dialog dialog = CommonActivity.createAlertDialog(
                            getActivity(),
                            getString(R.string.checkinfocusnewst),
                            getResources().getString(R.string.app_name));
                    dialog.show();
                }
            } else {
                if (errorCode.equals(Constant.INVALID_TOKEN2)
                        && description != null && !description.isEmpty()) {

                    Dialog dialog = CommonActivity
                            .createAlertDialog(
                                    (Activity) context,
                                    description,
                                    context.getResources().getString(
                                            R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = context.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(
                            getActivity(), description,
                            getResources().getString(R.string.app_name));
                    dialog.show();
                }
            }
        }

        private ArrayList<StudentBean> sendReqCheckInfoCus(String soCMT) {
            ArrayList<StudentBean> lstSoBD = new ArrayList<StudentBean>();
            String original = null;
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getThongTinThiSinh");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getThongTinThiSinh>");
                rawData.append("<cmMobileInput>");
                rawData.append("<token>" + Session.getToken());
                rawData.append("</token>");
                rawData.append("<soBD>" + "");
                rawData.append("</soBD>");
                rawData.append("<soCMT>" + soCMT);
                rawData.append("</soCMT>");
                rawData.append("</cmMobileInput>");
                rawData.append("</ws:getThongTinThiSinh>");

                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope,
                        Constant.BCCS_GW_URL, getActivity(),
                        "mbccs_getThongTinThiSinh");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();

                // parse xml
                Document doc = parse.getDomElement(original);
                NodeList nl = doc.getElementsByTagName("return");
                NodeList nodechild;
                for (int i = 0; i < nl.getLength(); i++) {
                    Element e2 = (Element) nl.item(i);
                    errorCode = parse.getValue(e2, "errorCode");
                    Log.d("errorCode", errorCode);
                    description = parse.getValue(e2, "description");
                    Log.d("description", description);
                    nodechild = doc.getElementsByTagName("ns3:ThiSinhModel");
                    for (int j = 0; j < nodechild.getLength(); j++) {
                        Element e1 = (Element) nodechild.item(j);
                        StudentBean studentBean = new StudentBean();
                        String sobaodanh = parse.getValue(e1, "ns3:SoBaoDanh");
                        if (sobaodanh != null && !sobaodanh.isEmpty()) {
                            studentBean.setSoBD(sobaodanh);
                            Log.d("sobaodanh", sobaodanh);
                        }
                        String matruongDKDT = parse.getValue(e1,
                                "ns3:MaTruongDKDT");
                        studentBean.setMaTruongDKTT(matruongDKDT);

                        String hoten = parse.getValue(e1, "ns3:Hoten");
                        studentBean.setHoten(hoten);

                        String ngaysinh = parse.getValue(e1, "ns3:NgaySinh");
                        studentBean.setNgaysinh(ngaysinh);

                        String gioitinh = parse.getValue(e1, "ns3:GioiTinh");
                        studentBean.setGioiTinh(gioitinh);

                        String soCMND = parse.getValue(e1, "ns3:SoCMND");
                        studentBean.setSoCMND(soCMND);

                        lstSoBD.add(studentBean);
                    }
                }
            } catch (Exception e) {
                Log.e("TAG8", "Exception", e);
            }
            return lstSoBD;
        }

    }

    OnClickListener moveLogInAct = new OnClickListener() {

        @Override
        public void onClick(View v) {
            LoginDialog dialog = new LoginDialog(getActivity(), ";menu.register.mobile2;");
            dialog.show();
        }
    };

    private void gotoSearchReasonScreen() {
        Bundle bundle = new Bundle();
        if (mReasonDTOList != null) {
            ReasonBundleObj reasonBundleObj = new ReasonBundleObj();
            reasonBundleObj.arrReason = mReasonDTOList;
            bundle.putSerializable("mReasonDTOList", reasonBundleObj);
        }
        Intent intent = new Intent(getActivity(), SearchReasonActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_CHOOSE_REASON);
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.spnReason:
                if (mReasonDTOList != null && mReasonDTOList.size() > 0) {
                    gotoSearchReasonScreen();
                } else {
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
                            getString(R.string.not_reason),
                            getResources().getString(R.string.app_name));
                    dialog.show();
                }
                break;
            case R.id.btnHome:
                break;
            case R.id.relaBackHome:

                break;
            case R.id.btnRefreshReason:
                new CacheDatabaseManager(getActivity()).insertCacheReasonSale(null);
                AsynctaskGetListReason as = new AsynctaskGetListReason(getActivity());
                as.execute();
                break;
            case R.id.tvAddress:
                SharedPreferences preferences = getActivity().getSharedPreferences(
                        Define.PRE_NAME, Activity.MODE_PRIVATE);
                String name = preferences.getString(Define.KEY_MENU_NAME, "");

                if ((oldCus == null) || name.contains(";menu.tdttkh;")) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isCheckStreetBlock", false);
                    if (area != null) {
                        bundle.putString("strProvince", area.getProvince());
                        bundle.putString("strDistris", area.getDistrict());
                        bundle.putString("strPrecint", area.getPrecinct());
                        bundle.putString("strStreetBlock", area.getStreetBlock());
                        bundle.putString("areaFlow", area.getStreetName());
                        bundle.putString("areaHomeNumber", area.getHome());
                    }
                    System.out.println("12345  CreateAddressCommon : " + oldCus);
                    Intent intent = new Intent(getActivity(), CreateAddressCommon.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, REQUEST_CHOOSE_ADDRESS);
                }
                break;
            default:
                break;
        }
    }

    private ArrayList<ReasonDTO> mReasonDTOList;

    @SuppressWarnings("unused")
    private class AsynctaskGetListReason extends AsyncTask<Void, Void, ParseOuput> {
        private Activity mActivity = null;

        public AsynctaskGetListReason(Activity mActivity) {
            this.mActivity = mActivity;
            prbReason.setVisibility(View.VISIBLE);
            btnRefreshReason.setVisibility(View.GONE);
        }

        @Override
        protected ParseOuput doInBackground(Void... params) {
            return getListReason();
        }

        @Override
        protected void onPostExecute(ParseOuput result) {
            super.onPostExecute(result);
            prbReason.setVisibility(View.GONE);
            btnRefreshReason.setVisibility(View.VISIBLE);
            try {
                if (result.getErrorCode().equals("0")) {
                    mReasonDTOList = new ArrayList<>(result.getLstReasonDTO().size());
                    mReasonDTOList.addAll(result.getLstReasonDTO());

                    if (mReasonDTOList == null || mReasonDTOList.isEmpty()) {
                        mReasonDTOList = new ArrayList<ReasonDTO>();
                    }
                    //remove reason
//                    if("true".equals(failCustomerInfo)){
//                        ArrayList<ReasonDTO> mReasonDTOListTmp = new ArrayList<ReasonDTO>();
//
//                        for(ReasonDTO reasonDTO: mReasonDTOList) {
//                            {
//                                if ("REPAIR_SUB_INFO".equals(reasonDTO.getReasonCode())) {
//                                    mReasonDTOListTmp.add(reasonDTO);
//                                    break;
//                                }
//                            }
//                        }
//                        mReasonDTOList.clear();
//                        mReasonDTOList.addAll(mReasonDTOListTmp);
//
//                    }else{
//                        for(ReasonDTO reasonDTO: mReasonDTOList){
//                            if("REPAIR_SUB_INFO".equals(reasonDTO.getReasonCode())){
//                                mReasonDTOList.remove(reasonDTO);
//                                break;
//                            }
//                        }
//                    }

                    // sau khi lay danh sach ly do xong
                } else {
                    if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
                        Dialog dialog = CommonActivity
                                .createAlertDialog(mActivity, result
                                        .getDescription(), mActivity.getResources()
                                        .getString(R.string.app_name), moveLogInAct);
                        dialog.show();
                    } else {
                        if (result.getDescription() == null
                                || result.getDescription().isEmpty()) {
                            result.setDescription(mActivity
                                    .getString(R.string.checkdes));
                        }
                        Dialog dialog = CommonActivity.createAlertDialog(mActivity,
                                result.getDescription(),
                                getResources().getString(R.string.app_name));
                        dialog.show();
                    }
                }
            } catch(Exception e){

            }
        }

        private ParseOuput getListReason() {
            ParseOuput result;
            String original = "";

            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getReasonFullPM");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getReasonFullPM>");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");
                rawData.append("<actionCode>" + "04" + "</actionCode>");
                rawData.append("<payType>2</payType>");
                rawData.append("<offerId>").append(sub.getOfferId())
                        .append("</offerId>");
                rawData.append("<serviceType>").append("M")
                        .append("</serviceType>");
                rawData.append("<custType>").append(sub.getCustType())
                        .append("</custType>");
                rawData.append("<subType>").append(sub.getSubType())
                        .append("</subType>");
                rawData.append("</input>");
                rawData.append("</ws:getReasonFullPM>");
                Log.i("RowData", rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input
                        .sendRequest(envelope, Constant.BCCS_GW_URL, mActivity,
                                "mbccs_getReasonFullPM");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("Respiginal", original);
                // parser
                Serializer serializer = new Persister();
                result = serializer.read(ParseOuput.class, original);
                if (result == null) {
                    result = new ParseOuput();
                    result.setDescription(getString(R.string.no_return_from_system));
                    result.setErrorCode(Constant.ERROR_CODE);
                }
            } catch (Exception e) {
                Log.e("getListIP", e.toString() + "description error", e);
                result = new ParseOuput();
                result.setDescription(getString(R.string.exception) + " - " + e);
                result.setErrorCode(Constant.ERROR_CODE);
            }
            return result;
        }
    }

    private class FindVerifiedOwnerByListIdNoAsyn extends AsyncTask<String, Void, ArrayList<SubscriberDTO>> {
        private Context mContext;
        private String errorCode;
        private String description;
        private ProgressDialog progress;

        public FindVerifiedOwnerByListIdNoAsyn(Context context) {
            this.mContext = context;
            this.progress = new ProgressDialog(mContext);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(
                    R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected ArrayList<SubscriberDTO> doInBackground(String... arg0) {
            return findVerifiedOwnerByListIdNo();
        }

        @Override
        protected void onPostExecute(ArrayList<SubscriberDTO> result) {
            arrSubscriberDTO = new ArrayList<SubscriberDTO>();
            progress.dismiss();
            if ("0".equals(errorCode)) {
                if (!CommonActivity.isNullOrEmpty(result)) {
                    lnChinhChu.setVisibility(View.VISIBLE);
                    tvThueBaoChinhChu.setText(R.string.dsthuebaodskh);
                    arrSubscriberDTO.addAll(result);
                    showSelectIsdnOwner();
                } else {
                    tvThueBaoChinhChu.setText(R.string.cus_not_mobile_before);
                    if (checkSubAdapter != null) {
                        checkSubAdapter.notifyDataSetChanged();
                    }
                }
            } else {
                if (checkSubAdapter != null) {
                    checkSubAdapter.notifyDataSetChanged();
                }
                if (Constant.INVALID_TOKEN2.equals(errorCode)) {

                    Dialog dialog = CommonActivity
                            .createAlertDialog(getActivity(),
                                    description, mContext.getResources()
                                            .getString(R.string.app_name),
                                    moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = getString(R.string.checkdes);
                    }

                }
                Dialog dialog = CommonActivity.createAlertDialog(mContext,
                        description, getString(R.string.app_name));
                dialog.show();
            }
        }

        private ArrayList<SubscriberDTO> findVerifiedOwnerByListIdNo() {
            ArrayList<SubscriberDTO> arrSub = new ArrayList<SubscriberDTO>();
            FileInputStream in = null;
            try {
                BCCSGateway input = new BCCSGateway();
                StringBuilder rawData = new StringBuilder();
                rawData.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.smartphonev2.bss.viettel.com/\">");

                rawData.append("<soapenv:Header/>");
                rawData.append("<soapenv:Body>");
                rawData.append("<ws:findVerifiedOwnerByListIdNo>");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");
                rawData.append("<custId>" + oldCus.getCustId() + "</custId>");
                rawData.append("</input>");
                rawData.append("</ws:findVerifiedOwnerByListIdNo>");
                rawData.append("</soapenv:Body>");
                rawData.append("</soapenv:Envelope>");

                String envelope = rawData.toString();
                Log.e("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);

                String fileName = input.sendRequestWriteToFile(envelope,
                        Constant.WS_SUB_DATA, Constant.SUB_FILE_NAME);
                if (fileName != null) {

                    try {

                        File file = new File(fileName);
                        in = new FileInputStream(file);
                        SubscriberDTOHanlder handler = (SubscriberDTOHanlder) CommonActivity
                                .parseXMLHandler(new SubscriberDTOHanlder(),
                                        new InputSource(in));
                        file.delete();
                        if (handler != null) {
                            if (handler.getItem().getToken() != null
                                    && !handler.getItem().getToken().isEmpty()) {
                                Session.setToken(handler.getItem().getToken());
                            }
                            if (handler.getItem().getErrorCode() != null) {
                                errorCode = handler.getItem().getErrorCode();
                            }
                            if (handler.getItem().getDescription() != null) {
                                description = handler.getItem()
                                        .getDescription();
                            }
                            arrSub = handler.getLstData();
                        }

                    } catch (Exception e) {
                        Log.e("Exception", e.toString(), e);
                    } finally {
                        if (in != null) {
                            try {
                                in.close();
                            } catch (IOException e) {
                                Log.e("Exception", e.toString(), e);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("TAG8", "Exception", e);
            }
            return arrSub;
        }
    }

    private void showSelectIsdnOwner() {
        final Dialog dialogIsdn = new Dialog(getActivity());
        dialogIsdn.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogIsdn.setContentView(R.layout.dialog_search_isdn);
        lvsubparent = (ExpandableHeightListView) dialogIsdn
                .findViewById(R.id.lvsubparent);
        lvsubparent.setExpanded(true);
        checkSubAdapter = new CheckSubAdapter(getActivity(), arrSubscriberDTO, this);
        lvsubparent.setAdapter(checkSubAdapter);
        checkSubAdapter.notifyDataSetChanged();
        final EditText edtseach = (EditText) dialogIsdn
                .findViewById(R.id.edtseach);
        edtseach.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {

                String input = edtseach.getText().toString()
                        .toLowerCase(Locale.getDefault());
                checkSubAdapter = new CheckSubAdapter(getActivity(),
                        arrSubscriberDTO, RegisterNewFragment.this);
                checkSubAdapter.notifyDataSetChanged();
                if (checkSubAdapter != null) {
                    checkSubAdapter.SearchInput(input);
                    lvsubparent.setAdapter(checkSubAdapter);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }
        });

        Button btnok = (Button) dialogIsdn.findViewById(R.id.btnok);
        btnok.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialogIsdn.cancel();
            }
        });

        dialogIsdn.show();
    }


    @Override
    public void onChangeChecked(SubscriberDTO subscriberDTO) {
        for (SubscriberDTO item : arrSubscriberDTO) {
            if (subscriberDTO.getSubId().equals(item.getSubId())) {
                item.setHasVerifiedOwner(!item.isHasVerifiedOwner());
                break;
            }
        }
    }

    OnFinishDSFListener onFinishDSFListener = new OnFinishDSFListener() {
        @Override
        public void onFinish(ProfileBO output) {
            profileBO = output;
            if ((profileBO != null
                    && profileBO.getHashmapFileObj() != null
                    && !profileBO.getHashmapFileObj().isEmpty())
                    || (profileBO.getProfileRecords() != null && profileBO.getProfileRecords().size() > 0)) {
                thumbnails.removeAllViews();
                horizontalScrollView.setVisibility(View.VISIBLE);

                ArrayList<String> lstData = new ArrayList<>();
                for (Map.Entry<String, ArrayList<FileObj>> entry : profileBO.getHashmapFileObj().entrySet()) {
                    for (FileObj fileObj : entry.getValue()) {
                        Log.d("onFinishDSFListener", fileObj.getFullPath());
                        lstData.add(fileObj.getFullPath());
                    }
                }

                if (!CommonActivity.isNullOrEmpty(profileBO.getSigImageFullPath())) {
                    lstData.add(profileBO.getSigImageFullPath());
                }

                ImageUtils.loadImageHorizontal(getActivity(), inflater, thumbnails, lstData,
                        new com.viettel.bss.viettelpos.v4.listener.OnClickListener() {
                    @Override
                    public void onClick(Object... obj) {
                        View thumView = (View) obj[0];
                        String path = String.valueOf(obj[1]);
                        zoomImageFromThumb(thumView, path);
                    }
                });
            } else {
                horizontalScrollView.setVisibility(View.GONE);
            }
        }
    };

    // lay ra danh sach cau hinh so giay to
    private class OnPostGetOptionSetValue implements OnPostExecuteListener<ArrayList<Spin>> {

        @Override
        public void onPostExecute(ArrayList<Spin> result, String errorCode, String description) {
            if (lnngayhethanNew != null) {
                lnngayhethanNew.setVisibility(View.GONE);
            }
            if (!CommonActivity.isNullOrEmptyArray(result)) {
                arrMapusage = result;
                for (Spin spin : arrMapusage) {
                    if (CommonActivity.checkMapUsage(idType, spin)) {
                        if (lnngayhethanNew != null) {
                            lnngayhethanNew.setVisibility(View.VISIBLE);
                        }
                        break;
                    }
                }
            } else {
                arrMapusage = new ArrayList<>();
            }
        }
    }

    private boolean validateProfileContract() {
        if (profileBO != null && profileBO.getMapRecordSelected() != null) {
            for (RecordPrepaid recordPrepaid : profileBO.getMapRecordSelected().values()) {
                if ("HDMBTT".equals(recordPrepaid.getCode())) {
                    return true;
                }
            }
        }
        return false;
    }

    private class OnPostDoUHaveMorethan implements OnPostExecuteListener<ParseOuput> {
        @Override
        public void onPostExecute(ParseOuput result, String errorCode, String description) {
            isMoreThan = false;
            descriptionNotice = "";
            if (result != null) {
                isMoreThan = result.isMoreThan();
                descriptionNotice = description;
            }
        }
    }


    public void zoomImageFromThumb(final View thumbView, String path) {
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

    private void getSerialSim() {

        btnBar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    Intent intent = new Intent(ACTION_SCAN);
                    intent.putExtra("SCAN_FORMATS",
                            "CODE_39,CODE_93,CODE_128,DATA_MATRIX,ITF,CODABAR,EAN_13,EAN_8,UPC_A,QR_CODE");
                    startActivityForResult(intent, 5555);

                } catch (ActivityNotFoundException anfe) {

                    OnClickListener click = new OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            UpdateApkAsyn updateApkAsyn = new UpdateApkAsyn(
                                    getActivity());
                            updateApkAsyn.execute();

                        }
                    };
                    CommonActivity.createDialog(getActivity(),
                            getString(R.string.confirmdlapk),
                            getString(R.string.app_name),
                            getString(R.string.cancel),
                            getString(R.string.ok), null,
                            click).show();
                }

            }
        });
    }

    public class UpdateApkAsyn extends AsyncTask<String, Void, String> {
        String urlinstall = "";
        ProgressDialog progress;
        private Context context = null;
        public UpdateApkAsyn(Context context) {
            this.context = context;
            this.progress = new ProgressDialog(this.context);
            // check font
            this.progress.setMessage(context.getResources().getString(
                    R.string.processingdl));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return UpdateVersion(Session.getToken());
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equalsIgnoreCase(Constant.SUCCESS_CODE)) {
                progress.dismiss();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(urlinstall)),
                        "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            } else {
                progress.dismiss();
                Dialog dialog = CommonActivity
                        .createAlertDialog((Activity) context, context
                                .getResources()
                                .getString(R.string.downloadfail), context
                                .getResources().getString(R.string.app_name));
                dialog.show();
            }
        }

        public String UpdateVersion(String token) {
            String result = "";
            String url = Constant.PATH_BARCODE + token;
            try {
                URL urlcontrol = new URL(url);
                Log.e("URL getVersion:", url);
                Log.i("Bo nho con trong", Double.toString(SdCardHelper
                        .getAvailableInternalMemorySize()));
                double availAbleMemory = SdCardHelper
                        .getAvailableInternalMemorySize();
                if (availAbleMemory > 50) {

                    InputStream input = new BufferedInputStream(
                            urlcontrol.openStream());
                    OutputStream output;
                    File file = new File(Constant.MBCCS_TEMP_FOLDER);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    output = new FileOutputStream(Constant.MBCCS_TEMP_FOLDER
                            + "barcode.apk");
                    urlinstall = Constant.MBCCS_TEMP_FOLDER + "barcode.apk";
                    Log.d("urlinstall", urlinstall);
                    byte data[] = new byte[1024];
                    int count = 0;
                    while ((count = input.read(data)) != -1) {
                        output.write(data, 0, count);
                    }
                    output.flush();
                    output.close();
                    input.close();
                    Log.e("FilePath", urlinstall);
                    Log.e("UPDATE VERSION", "End Download >>>>>>>>>>>>>>>> ");
                    result = Constant.SUCCESS_CODE;
                } else {
                    result = Constant.ERROR_CODE;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    private String getXmlCus() {
        StringBuilder stringBuilder = new StringBuilder("");
        Date date;
        //String address = area.getName();
        if (oldCus != null) {
            //address = oldCus.getAddress();
            stringBuilder.append("<createUser>" + oldCus.getCreateUser()
                    + "</createUser>");
            stringBuilder.append("<createDatetime>"
                    + oldCus.getCreateDatetime() + "</createDatetime>");
            stringBuilder.append("<updateDatetime>"
                    + oldCus.getUpdateDatetime() + "</updateDatetime>");
            if (mEdtNgaySinh.getText().toString().length() > 0) {
                date = DateTimeUtils.convertStringToTime(mEdtNgaySinh.getText().toString(), "dd/MM/yyyy");
                stringBuilder.append("<birthDate>"
                        + DateTimeUtils.convertDateSendOverSoap(date)
                        + "</birthDate>");
            } else {
                stringBuilder.append("<birthDate>" + oldCus.getBirthDate() + "</birthDate>");
            }
            stringBuilder.append("<custId>" + custId);
            stringBuilder.append("</custId>");
            stringBuilder.append("<updateCustIdentity>" + false);
            stringBuilder.append("</updateCustIdentity>");
            stringBuilder.append("<isNewCustomer>" + false);
            stringBuilder.append("</isNewCustomer>");
        } else {
            date = DateTimeUtils.convertStringToTime(mEdtNgaySinh.getText().toString(),
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
        stringBuilder.append("<address>" + tvAddress.getText().toString().trim() + "</address>");
        stringBuilder.append("<district>" + area.getDistrict() + "</district>");// Quan
        stringBuilder.append("<precinct>" + area.getPrecinct() + "</precinct>");// phuong
        stringBuilder.append("<province>" + area.getProvince() + "</province>");// tinh
        if (!CommonActivity.isNullOrEmpty(area.getStreetBlock())) {
            stringBuilder.append("<streetBlock>" + area.getStreetBlock() + "</streetBlock>");
            stringBuilder.append("<areaCode>" + area.getProvince() + area.getDistrict()
                    + area.getPrecinct() + area.getStreetBlock() + "</areaCode>");
        } else {
            stringBuilder.append("<areaCode>" + area.getProvince() + area.getDistrict()
                    + area.getPrecinct() + "</areaCode>");
        }

        // tinh
        if (!CommonActivity.isNullOrEmpty(area.getStreet())) {
            stringBuilder.append("<streetName>"
                    + StringUtils.xmlEscapeText(area.getStreet()) + "</streetName>");
        }
        stringBuilder.append("<home>"
                + StringUtils.xmlEscapeText(area.getHome()) + "</home>");
        stringBuilder.append("<description>"
                + StringUtils.xmlEscapeText(getTextFromEdt(edtnote))
                + "</description>");
        stringBuilder.append("<custId>" + custId + "</custId>");
        stringBuilder.append("<custType>VIE</custType>");

        // custIdentityDTO
        stringBuilder.append("<custIdentityDTO>");
        stringBuilder.append("<custId>" + custId + "</custId>");
        if (!TextUtils.isEmpty(edit_ngayhethan.getText().toString())) {
            Date dateExpire = DateTimeUtils
                    .convertStringToTime(edit_ngayhethan.getText().toString(), "dd/MM/yyyy");
            stringBuilder.append("<idExpireDate>"
                    + DateTimeUtils.convertDateSendOverSoap(dateExpire)
                    + "</idExpireDate>");
        }
        date = DateTimeUtils.convertStringToTime(
                mEdtNgayCap.getText().toString(), "dd/MM/yyyy");
        stringBuilder.append("<idIssueDate>"
                + DateTimeUtils.convertDateSendOverSoap(date) + "</idIssueDate>");
        stringBuilder.append("<idIssuePlace>" + StringUtils.xmlEscapeText(
                mEdtNoiCap.getText().toString().trim()) + "</idIssuePlace>");
        stringBuilder.append("<idNo>" + mIdNo + "</idNo>");
        stringBuilder.append("<idType>" + idType + "</idType>");
        stringBuilder.append("</custIdentityDTO>");

        // listCustIdentity
        stringBuilder.append("<listCustIdentity>");
        stringBuilder.append("<custId>" + custId + "</custId>");
        date = DateTimeUtils.convertStringToTime(
                mEdtNgayCap.getText().toString(), "dd/MM/yyyy");
        stringBuilder.append("<idIssueDate>"
                + DateTimeUtils.convertDateSendOverSoap(date) + "</idIssueDate>");
        stringBuilder.append("<idIssuePlace>" + StringUtils.xmlEscapeText(
                mEdtNoiCap.getText().toString().trim()) + "</idIssuePlace>");
        stringBuilder.append("<idNo>" + mIdNo + "</idNo>");
        stringBuilder.append("<idType>" + idType + "</idType>");
        stringBuilder.append("</listCustIdentity>");
        stringBuilder.append("<name>"
                + StringUtils.xmlEscapeText(getTextFromEdt(mEdtTenKH))
                + "</name>");
        stringBuilder.append("<nationality>" + getString(R.string.viet_nam)
                + "</nationality>");
        stringBuilder.append("<sex>" + mIdGioiTinh + "</sex>");
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

        stringBuilder.append("<fullAddress>" + tvAddress.getText().toString().trim() + "</fullAddress>");

        stringBuilder.append("</custAdd>");
        return stringBuilder.toString();
    }

    private String getXmlSub() {
        Date date = null;
        StringBuilder stringBuilder = new StringBuilder("");
        // bo sung omichanel
        if(!CommonActivity.isNullOrEmpty(omniProcessId)){
            stringBuilder.append("<omniProcessId>" + omniProcessId);
            stringBuilder.append("</omniProcessId>");
        }
        if(!CommonActivity.isNullOrEmpty(omniTaskId)){
            stringBuilder.append("<omniTaskId>" + omniTaskId);
            stringBuilder.append("</omniTaskId>");
        }
        stringBuilder.append("<isdn>" + mISDN + "</isdn>");
        stringBuilder.append("<serial>" + sub.getSerial() + "</serial>");
        stringBuilder.append("<productCode>" + sub.getProductCode()
                + "</productCode>");
        stringBuilder.append("<subId>" + sub.getSubId() + "</subId>");
        stringBuilder.append("<payType>" + sub.getPayType() + "</payType>");
        stringBuilder.append("<telecomServiceId>" + sub.getTelecomServiceId()
                + "</telecomServiceId>");
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
        stringBuilder.append("<offerId>" + sub.getOfferId() + "</offerId>");
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

        // goi cuoc dac biet dac biet
        int typeCodeXml = sub.getType().intValue();
        // test truong hop dac biet
        // typeCode = 1;
        if (typeCodeXml == 1) {
            stringBuilder.append("<productSpecInfoDTO>");
            stringBuilder.append("<departmentCode>" + mCodeDonVi
                    + "</departmentCode>");
            stringBuilder.append("<showProdSpecInfo>true</showProdSpecInfo>");

            date = DateTimeUtils
                    .convertStringToTime(mEdtNgayKT.getText().toString(), "dd/MM/yyyy");
            stringBuilder.append("<endDatetime>"
                    + DateTimeUtils.convertDateSendOverSoap(date)
                    + "</endDatetime>");

            // stringBuilder.append("<endDatetime>" + mNgayKetThuc
            // + "</endDatetime>");
            stringBuilder.append("<nationCode>"
                    + mEdtQuocTich.getText().toString() + "</nationCode>");
            stringBuilder.append("<objectCode>" + mCodeDoiTuong
                    + "</objectCode>");
            stringBuilder.append("<orderNumber>"
                    + mEdtMaGiayToDacBiet.getText().toString()
                    + "</orderNumber>");

            date = DateTimeUtils.convertStringToTime(mEdtNgayBD.getText().toString(), "dd/MM/yyyy");
            stringBuilder.append("<startDatetime>"
                    + DateTimeUtils.convertDateSendOverSoap(date)
                    + "</startDatetime>");
            stringBuilder.append("<status>" + 1 + "</status>");
            stringBuilder.append("<serviceType>" + "M" + "</serviceType>");
            stringBuilder.append("<productCode>" + sub.getProductCode()
                    + "</productCode>");
            stringBuilder.append("</productSpecInfoDTO>");
        } else if (typeCodeXml == 2) {
            stringBuilder.append("<productSpecInfoDTO>");
            stringBuilder.append("<subName>");
            stringBuilder.append(StringUtils.escapeHtml(edtNameHS.getText()
                    .toString()));
            stringBuilder.append("</subName>");

            stringBuilder.append("<subDob>");
            date = DateTimeUtils.convertStringToTime(edtAgeHS.getText()
                    .toString(), "dd/MM/yyyy");
            stringBuilder.append(DateTimeUtils.convertDateSendOverSoap(date));
            stringBuilder.append("</subDob>");
            stringBuilder.append("<productCode>" + sub.getProductCode()
                    + "</productCode>");
            stringBuilder.append("</productSpecInfoDTO>");

        }
        stringBuilder.append(
                "<signDate>" + StringUtils.convertDateToString(edit_ngayky.getText().toString().trim())
                        + "T00:00:00+07:00");
        stringBuilder.append("</signDate>");
        return stringBuilder.toString();
    }
	
    private class OnPostGetInfoCustomer implements OnPostExecuteListener<ArrayList<SpinV2>> {

        @Override
        public void onPostExecute(ArrayList<SpinV2> result, String errorCode, String description) {

            if (!CommonActivity.isNullOrEmptyArray(result)) {
                arrDoituong = result;
                SpinV2 spin = new SpinV2();
                spin.setValue(RegisterNewFragment.this.getString(R.string.chondoituong));
                arrDoituong.add(0, spin);
                Utils.setDataSpinner(getActivity(),arrDoituong,spnDoituong);

            } else {
                CommonActivity.createAlertDialog(getActivity(),
                        getString(R.string.doituong_null),
                        getString(R.string.app_name));
                return;
            }
        }
    }

}
