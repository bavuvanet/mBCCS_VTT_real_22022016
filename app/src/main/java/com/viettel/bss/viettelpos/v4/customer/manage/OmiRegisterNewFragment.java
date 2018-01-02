package com.viettel.bss.viettelpos.v4.customer.manage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.AsyncGetOptionSetValue;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.DoUHaveMoreThan3SubAsyn;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustomerDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ReasonDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.CreateAddressCommon;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.Define.CHANNEL_UPDATE_IMAGE;
import com.viettel.bss.viettelpos.v4.commons.FileUtils;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.CheckSubAdapter;
import com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.CheckSubAdapter.OnChangeCheckedSub;
import com.viettel.bss.viettelpos.v4.connecttionMobile.handler.SubscriberDTOHanlder;
import com.viettel.bss.viettelpos.v4.connecttionService.listener.ExpandableHeightListView;
import com.viettel.bss.viettelpos.v4.customer.adapter.GetSobaodanhAdapter;
import com.viettel.bss.viettelpos.v4.customer.asynctask.CheckCusQuotaAsync;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.customer.object.StudentBean;
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
import com.viettel.bss.viettelpos.v4.dialog.ChooseFileDialogFragment;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.hsdt.fragments.ProfileInfoDialogFragment;
import com.viettel.bss.viettelpos.v4.infrastrucure.dal.InfrastrucureDB;
import com.viettel.bss.viettelpos.v4.listener.OnFinishDSFListener;
import com.viettel.bss.viettelpos.v4.object.ProfileBO;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ConnectionOrder;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ProfileRecord;
import com.viettel.bss.viettelpos.v4.sale.activity.ActivityChooseChannel;
import com.viettel.bss.viettelpos.v4.sale.dal.BhldDAL;
import com.viettel.bss.viettelpos.v4.sale.object.SaleOutput;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.task.AsynTaskGetListRecordProfile;
import com.viettel.bss.viettelpos.v4.task.AsynTaskZipFile;
import com.viettel.bss.viettelpos.v4.ui.image.picker.Config;
import com.viettel.bss.viettelpos.v4.ui.image.picker.ImagePickerActivity;
import com.viettel.bss.viettelpos.v4.ui.image.picker.ImagePreviewActivity;
import com.viettel.bss.viettelpos.v4.utils.ImageUtils;
import com.viettel.bss.viettelpos.v4.utils.Utils;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

//import com.viettel.bss.viettelpos.v4.charge.Business.AreaBusiness;

@TargetApi(19)
public class OmiRegisterNewFragment extends AppCompatActivity implements OnClickListener,
        OnChangeCheckedSub {

    public static final int REQUEST_CHOOSE_ADDRESS = 1;
    private ProgressDialog progressDangKy;
    private InfrastrucureDB mInfrastrucureDB;
    private BCCSGateway mBccsGateway = new BCCSGateway();

    private EditText mEdtISDN;
    private EditText mTvGoiCuoc;
    private EditText mEdtSerial;
    private EditText mEdtTenKH;
    private EditText mEdtNgaySinh;
    private EditText mEdtMaGiayTo;
    private EditText mEdtNgayCap;
    private EditText mEdtNoiCap;
    private EditText mEdtToDuongSoNha;
    private TextView mLnUpImage;

    private Spinner mSpGioiTinh;
    private EditText mSpLoaiGiayTo;
    private AreaObj area;

    private Button mBtnRegister;
    private ArrayList<LoaiGiayToObj> gioiTinh = new ArrayList<LoaiGiayToObj>();

    private String mIdGioiTinh = "";// loai gioi tinh
    String busType = "";
    String custId = "";
    String idIssuePlace = "";
    String idNo = "";
    String idType = "";
    String name = "";
    String sex = "";
    String status = "";
    String updatedUser = "";
    private String mISDN = "";
    String mIdNo = "";
    private LinearLayout mLnGoiDacBiet;
    private EditText mEdtQuocTich;
    private EditText mTvDonVi;
    private EditText mEdtMaGiayToDacBiet;
    private EditText mEdtNgayBD;
    private EditText mEdtNgayKT;

    private Spinner mSpDoiTuong;
    private Dialog dialogDonVi = null;
    ArrayList<SpecObject> mArrayListSpec = new ArrayList<SpecObject>();
    ArrayList<DoiTuongObj> mAratListDonVi = new ArrayList<DoiTuongObj>();
    private ListView lvListDonVi;

    // bien cho doi tuong dac biet

    private String mCodeDoiTuong = "";
    private String mCodeDonVi = "";
    private String mMaGiayToDacBiet = "";
    private View rlchondonvi, rlquoctich;


    private int mKHVersion;

    // Ma CTV dang ky thay
    private TextView tvCTV;
    private View imgDeleteChannel;
    private final int REQUEST_CHOOSE_CHANNEL = 10;
    private Date timeStart = null;
    private Date timeEnd = null;
    private ArrayList<FileObj> arrFileBackUp;

    private CustomerDTO oldCus = null;
    private SubscriberDTO sub = null;
    private Spinner spnReason;
    private View prbReason;
    private Button btnRefreshReason;
    private Long reasonId = Long.MIN_VALUE;
    //    private Long reasonIdOld = Long.MIN_VALUE;
    private View prbRecord;
    private View btnRefreshRecord;
    private ArrayList<SubscriberDTO> arrSubscriberDTO = new ArrayList<SubscriberDTO>();
    private View lnthuebaochinhchu;
    private View lnHocSinh;
    private EditText edtnote;
    private CheckSubAdapter checkSubAdapter;
    private ExpandableHeightListView lvsubparent;
    private EditText edtHidden;
    private TextView tvAddress, tvBundlDesc;
    private EditText edtAgeHS, edtNameHS;

    @BindView(R.id.lnSelectProfile)
    LinearLayout lnSelectProfile;
    ChooseFileDialogFragment fragment;
    @BindView(R.id.thumbnails)
    LinearLayout thumbnails;
    @BindView(R.id.horizontalScrollView)
    HorizontalScrollView horizontalScrollView;

    LayoutInflater inflater;
    ProfileBO profileBO = new ProfileBO();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    // Hold a reference to the current animator,
    // so that it can be canceled mid-way.
    private Animator mCurrentAnimator;

    // The system "short" animation time duration, in milliseconds. This
    // duration is ideal for subtle animations or animations that occur
    // very frequently.
    private int mShortAnimationDuration;

    // cau hinh thong tin an theo so giay to
    public static ArrayList<Spin> arrMapusage = new ArrayList<>();
    private LinearLayout lnngayhethanNew;
    private EditText edit_ngayhethan;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private Date dateNow;
    private String dateNowString;
    private EditText edit_ngayky;
    public boolean isMoreThan = false;
    private String descriptionNotice = "";

    private ProfileInfoDialogFragment profileInfoDialogFragment;

    private String getTextFromEdt(EditText edt) {
        String text = "";
        if (edt != null) {
            text = edt.getText().toString();
        }
        return text;
    }

    private ConnectionOrder connectionOrder = null;
    private String CHECK_REGISTER_INFO_OMNICHANNEL = "CHECK_REGISTER_INFO_OMNICHANNEL";


    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    protected void registerNew() throws Exception {
        if (!validateSubCusInfo()) {
            return;
        }

        try {
            Date dateSign = sdf.parse(edit_ngayky.getText().toString());
            if (dateSign.after(dateNow)) {
                CommonActivity.createAlertDialog(OmiRegisterNewFragment.this,
                        this.getString(R.string.signDateEmptyThan), this.getString(R.string.app_name)).show();
                return;
            }
        } catch (Exception e) {
            Log.d("Exception", e.toString());
        }

        //truong hop lay ho so bi loi
        if (CommonActivity.isNullOrEmpty(profileBO.getMapListRecordPrepaid())
                && profileBO.isRequiredUploadImage()) {
            CommonActivity.createAlertDialog(OmiRegisterNewFragment.this,
                    getString(R.string.error_get_profile),
                    getString(R.string.app_name)).show();
            return;
        }

        if (!CommonActivity.isNullOrEmpty(profileBO.getMapListRecordPrepaid())
                && (profileInfoDialogFragment == null || !profileInfoDialogFragment.isFullFile())) {
            CommonActivity.createAlertDialog(OmiRegisterNewFragment.this,
                    getString(R.string.chua_chon_het_anh),
                    getString(R.string.app_name)).show();
            return;
        }
        if (isMoreThan) {
            if (!validateProfileContract()) {
                CommonActivity.createAlertDialog(OmiRegisterNewFragment.this, descriptionNotice,
                        this.getString(R.string.app_name)).show();
                return;
            }
        }
        confirmDK();
//        mBtnRegister.setEnabled(false);
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().setTitle(getResources().getString(R.string.manage_register_info));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mInfrastrucureDB = new InfrastrucureDB(getApplicationContext());

        Calendar calendar = Calendar.getInstance();

        int fromYear = calendar.get(Calendar.YEAR);
        int fromMonth = calendar.get(Calendar.MONTH) + 1;
        int fromDay = calendar.get(Calendar.DAY_OF_MONTH);

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

        setContentView(R.layout.register_new_customer_fragment_omni);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(Build.VERSION.SDK_INT < 11 ) {
            getSupportActionBar().hide();
        }


        inflater = LayoutInflater.from(this);

        View relBack = findViewById(R.id.relBack);
        relBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
        lnngayhethanNew = (LinearLayout) findViewById(R.id.lnngayhethan);
        edit_ngayhethan = (EditText) findViewById(R.id.edit_ngayhethan);
        edit_ngayhethan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonActivity.showDatePickerDialog(OmiRegisterNewFragment.this, edit_ngayhethan);
            }
        });
        rlchondonvi = findViewById(R.id.rlchondonvi);
        rlquoctich = findViewById(R.id.rlquoctich);
        mLnGoiDacBiet = (LinearLayout) findViewById(R.id.lnTTGoiCuocDacBiet);
        mEdtQuocTich = (EditText) findViewById(R.id.edtQuocTich);
        mTvDonVi = (EditText) findViewById(R.id.tvDonVi);
        mEdtMaGiayToDacBiet = (EditText) findViewById(R.id.edtMaGiayToDacBiet);
        mEdtNgayBD = (EditText) findViewById(R.id.edtNgayBD);
        mEdtNgayKT = (EditText) findViewById(R.id.edtNgayKT);
        edtnote = (EditText) findViewById(R.id.edtnote);
        edtnote.setEnabled(true);
        edtAgeHS = (EditText) findViewById(R.id.edtAgeHS);
        tvBundlDesc = (TextView) findViewById(R.id.tvBundlDesc);
        tvBundlDesc.setVisibility(View.VISIBLE);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvAddress.setOnClickListener(this);
        edit_ngayky = (EditText) findViewById(R.id.edit_ngayky);
        edit_ngayky.setText(dateNowString);
        edit_ngayky.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonActivity.showDatePickerDialog(OmiRegisterNewFragment.this, edit_ngayky);
            }
        });
        edtAgeHS.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                CommonActivity.showDatePickerDialog(OmiRegisterNewFragment.this,
                        edtAgeHS);
            }
        });
        edtNameHS = (EditText) findViewById(R.id.edtNameHS);
        lnHocSinh = findViewById(R.id.lnHocSinh);
        mSpDoiTuong = (Spinner) findViewById(R.id.spDoiTuong);
        spnReason = (Spinner) findViewById(R.id.spnReason);
        spnReason.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                profileBO = new ProfileBO();
                profileBO.clearData();
                if (profileInfoDialogFragment != null) {
                    profileInfoDialogFragment.setFullFile(false);
                }
                ReasonDTO reasonDTO = (ReasonDTO) spnReason
                        .getSelectedItem();

                if (reasonDTO == null || reasonDTO.getReasonId() == null || "".equals(reasonDTO.getReasonId())) {
                    return;
                }

                reasonId = Long.parseLong(reasonDTO.getReasonId());
                if (reasonId.compareTo(-1L) != 0) {
                    new AsynTaskGetListRecordProfile(OmiRegisterNewFragment.this, new OnPostExecuteListener<Map<String, ArrayList<RecordPrepaid>>>() {
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

                                if(connectionOrder != null && !CommonActivity.isNullOrEmpty(connectionOrder.getProfileRecords())){
                                    for (ProfileRecord item : connectionOrder.getProfileRecords()){
                                        if(Constant.PROFILE.CMTNDMT.equals(item.getCode()) || Constant.PROFILE.CMTNDMS.equals(item.getCode())){
                                            profileBO.removeItemFromMapByCode(item.getCode());
                                        }
                                    }
                                }

                            }
                        }
                    }, null, initProfileBO()).execute();
                } else {
                    profileBO.getHashmapFileObj().clear();
                    reasonId = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        prbReason = findViewById(R.id.prbReason);
        btnRefreshReason = (Button) findViewById(R.id.btnRefreshReason);
        btnRefreshReason.setOnClickListener(this);
        prbRecord = findViewById(R.id.prbRecord);
        btnRefreshRecord = findViewById(R.id.btnRefreshRecord);
        edtHidden = (EditText) findViewById(R.id.edtHidden);
        prbRecord.setVisibility(View.GONE);
        btnRefreshRecord.setVisibility(View.GONE);
        btnRefreshRecord.setOnClickListener(this);
        mEdtNgayBD.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                CommonActivity.showDatePickerDialog(OmiRegisterNewFragment.this, mEdtNgayBD);
            }
        });
        mEdtNgayKT.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                CommonActivity.showDatePickerDialog(OmiRegisterNewFragment.this, mEdtNgayKT);
            }
        });
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
                            OmiRegisterNewFragment.this);
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

        mTvDonVi.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                timKiemDonVi();
            }
        });

//        mLnUploadImage = (LinearLayout) findViewById(R.id.lnUploadImage);

        mEdtISDN = (EditText) findViewById(R.id.edtISDN);
        mTvGoiCuoc = (EditText) findViewById(R.id.tvGoiCuoc);
        mEdtSerial = (EditText) findViewById(R.id.edtSerial);

        // mEdtGioiTinh = (EditText) mView.findViewById(R.id.edtGioiTinh);
        // mEdtLoaiGiayTo = (EditText)
        // mView.findViewById(R.id.edtLoaiGiayTo);
        mEdtMaGiayTo = (EditText) findViewById(R.id.edtMaGiayTo);

        mLnUpImage = (TextView) findViewById(R.id.tv20);

        mEdtNgaySinh = (EditText) findViewById(R.id.edtNgaySinh);
        mEdtNgaySinh.setEnabled(true);
        mEdtNgayCap = (EditText) findViewById(R.id.edtNgayCap);

        mEdtNgaySinh.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
//                showDatePickerDialog(mEdtNgaySinh);
                CommonActivity.showDatePickerDialog(OmiRegisterNewFragment.this, mEdtNgaySinh);
            }
        });
        mEdtNgayCap.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                CommonActivity.showDatePickerDialog(OmiRegisterNewFragment.this, mEdtNgayCap);
//                showDatePickerDialog(mEdtNgayCap);
            }
        });

        mEdtNoiCap = (EditText) findViewById(R.id.edtNoiCap);
        mEdtToDuongSoNha = (EditText) findViewById(R.id.edtToDuongSoNha);
        mEdtTenKH = (EditText) findViewById(R.id.edtTenKH);

        mSpGioiTinh = (Spinner) findViewById(R.id.spGioiTinh);
        mSpLoaiGiayTo = (EditText) findViewById(R.id.spLoaiGiayTo);

        mBtnRegister = (Button) findViewById(R.id.btnRegister);
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

        RegisterInfoAdapter gioiTinhAdapter = new RegisterInfoAdapter(gioiTinh,
                this);
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

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            connectionOrder = (ConnectionOrder) bundle.getSerializable("connectionOrder");
            mIdNo = bundle.getString("mIdNo", mIdNo);
            // idNo = bundle.getString("idNo", idNo);
            idType = bundle.getString("idType", idType);
            // bundle.getString("status", status);
            DoUHaveMoreThan3SubAsyn doUHaveMoreThan3SubAsyn = new DoUHaveMoreThan3SubAsyn(OmiRegisterNewFragment.this, new OnPostDoUHaveMorethan(), moveLogInAct);
            doUHaveMoreThan3SubAsyn.execute(mIdNo + "", idType + "");
//            if(CommonActivity.isNullOrEmptyArray(arrMapusage)){
            AsyncGetOptionSetValue asyncGetOptionSetValue = new AsyncGetOptionSetValue(OmiRegisterNewFragment.this, new OnPostGetOptionSetValue(), moveLogInAct);
            asyncGetOptionSetValue.execute("IDTYPE_FIELD_USAGE");
//            }


            mISDN = bundle.getString("mISDN", mISDN);
            oldCus = (CustomerDTO) bundle.getSerializable("customerOld");
            mKHVersion = bundle.getInt("KHVersion");


            if (oldCus != null) {
                CheckCusQuotaAsync asy = new CheckCusQuotaAsync(this, mIdNo);
                asy.execute();
                custId = oldCus.getCustId() + "";

                name = oldCus.getName();
                sex = oldCus.getSex();
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

                //lay doi tuong ConnectionOrder tu man hinh request de fill vao
            } else {
                custId = "";
                name = connectionOrder.getCustomer().getName();
                sex = "M"; //ben omni chua co truong gioi tinh
                area = new AreaObj();
                area.setProvince(connectionOrder.getAddress().getProvince());
                area.setDistrict(connectionOrder.getAddress().getDistrict());
                area.setPrecinct(connectionOrder.getAddress().getPrecinct());
                area.setHome(connectionOrder.getAddress().getAddress());
                if(!CommonActivity.isNullOrEmpty(connectionOrder.getAddress().getFullAddress())) {
                    tvAddress.setText(connectionOrder.getAddress().getFullAddress());
                }
            }

            mPositionLoaiGiayTo = bundle.getInt("mPositionLoaiGiayTo");
            sub = (SubscriberDTO) bundle.getSerializable("subscriber");
            if (mKHVersion == 1) {
                // KH cu
                mEdtTenKH.setText(name);

//                mEdtNgayCap.setText(idIssueDate);
                mEdtNoiCap.setText(idIssuePlace);
                mEdtNgayCap.setVisibility(View.GONE);
                findViewById(R.id.lnNgayCap).setVisibility(View.GONE);
                findViewById(R.id.lnNoiCap).setVisibility(View.GONE);
                mEdtNoiCap.setVisibility(View.GONE);

                String strDate = DateTimeUtils.convertDate(oldCus.getBirthDate());
                mEdtNgaySinh.setText(strDate);

//          mEdtToDuongSoNha.setText(home);
                if ("M".equals(sex)) {
                    mSpGioiTinh.setSelection(0);
                } else {
                    mSpGioiTinh.setSelection(1);
                }
//          setSpinnerDiaChiKH();
                setEnabledView(false);
                edtnote.setText(oldCus.getDescription());
                edtnote.setEnabled(false);
            } else {
                mEdtNoiCap.setText(connectionOrder.getCustomer().getIdIssuePlace());
                mEdtNgayCap.setText(Utils.revertDate(connectionOrder.getCustomer().getIdIssueDate()));
                String strDate = DateTimeUtils.convertDate(connectionOrder.getCustomer().getBirthDate());
                mEdtNgaySinh.setText(strDate);
                edtnote.setText(connectionOrder.getStatusDesc());
//                setEnabledView(false);
                findViewById(R.id.lnNgayCap).setVisibility(View.VISIBLE);
                findViewById(R.id.lnNoiCap).setVisibility(View.VISIBLE);
            }
            mEdtTenKH.setText(name);
            if ("M".equals(sex)) {
                mSpGioiTinh.setSelection(0);
            } else {
                mSpGioiTinh.setSelection(1);
            }
            mEdtMaGiayTo.setText(mIdNo);
            mEdtISDN.setText(mISDN);
            mEdtISDN.setEnabled(false);
            mEdtMaGiayTo.setEnabled(false);
            mTvGoiCuoc.setText(sub.getProductCode());
            tvBundlDesc.setText(connectionOrder.getProductInfo().getBundleDesc().replaceAll("\\n", ","));

        }

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
        // Get list record prepaid

        // RegisterInfoAdapter registerInfoAdapter = new RegisterInfoAdapter(
        // RegisterInfoFragment.mLoaiGiayTo, this);
        ;

        if (RegisterInfoFragment.mLoaiGiayTo.size() > 0) {
            mSpLoaiGiayTo.setText(RegisterInfoFragment.mLoaiGiayTo.get(
                    mPositionLoaiGiayTo).getParValue());
        }

        // requestProduccode();
        imgDeleteChannel = findViewById(R.id.imgDeleteChannel);
        imgDeleteChannel.setVisibility(View.GONE);
        tvCTV = (TextView) findViewById(R.id.tvChooseChannel);
        tvCTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(OmiRegisterNewFragment.this,
                        ActivityChooseChannel.class);
                startActivityForResult(i, REQUEST_CHOOSE_CHANNEL);
            }
        });
        imgDeleteChannel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                tvCTV.setText(getResources().getString(R.string.chooseCTV));
                imgDeleteChannel.setVisibility(View.GONE);
            }
        });

        AsynctaskGetListReason as = new AsynctaskGetListReason(this);
        as.execute();

        lnthuebaochinhchu = findViewById(R.id.lnChinhChu);
        if (oldCus != null) {
            lnthuebaochinhchu.setVisibility(View.VISIBLE);
        } else {
            lnthuebaochinhchu.setVisibility(View.GONE);
        }
        lnthuebaochinhchu.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (!CommonActivity.isNullOrEmpty(arrSubscriberDTO)) {
                    showSelectIsdnOwner();
                } else {
                    FindVerifiedOwnerByListIdNoAsyn findVerifiedOwnerByListIdNoAsyn = new FindVerifiedOwnerByListIdNoAsyn(
                            OmiRegisterNewFragment.this);
                    findVerifiedOwnerByListIdNoAsyn.execute();
                }

            }
        });

        lnSelectProfile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (reasonId == null) {
//                    CommonActivity.toast(OmiRegisterNewFragment.this, R.string.chua_chon_ly_do_dang_ky);
//                    return;
//                }
//
//                if (!CommonActivity.isNullOrEmpty(profileBO)
//                        && !CommonActivity.isNullOrEmpty(profileBO.getMapListRecordPrepaid())) {
//
//                    fragment = ChooseFileDialogFragment.newInstance(profileBO, CHECK_REGISTER_INFO_OMNICHANNEL, isMoreThan, connectionOrder);
//                    fragment.setOnFinishDSFListener(onFinishDSFListener);
//                    fragment.show(getSupportFragmentManager(), "Choose file");
//                }
                if (reasonId == null) {
                    CommonActivity.toast(OmiRegisterNewFragment.this, R.string.chua_chon_ly_do_dang_ky);
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
                    bundle.putString("isOmni", "1");
                    profileInfoDialogFragment.setOnFinishDSFListener(onFinishDSFListener);
                    profileInfoDialogFragment.setArguments(bundle);
                    profileInfoDialogFragment.show(getSupportFragmentManager(), "show profile");
                }
            }
        });
    }

    private ProfileBO initProfileBO() {
        horizontalScrollView.setVisibility(View.GONE);
        profileBO.getLstActionCode().add(Constant.ACTION_CODE.REGISTER_INFORMATION);
        profileBO.getLstReasonId().add(reasonId);
        profileBO.setParValue(Constant.POSTPAID.equals(sub.getPayType()) ? sub.getSubType() : sub.getProductCode());
        profileBO.setPayType(sub.getPayType());
        String custType = sub.getCustType()!=null ? sub.getCustType(): ((oldCus!=null &&oldCus.getCustType()!=null)?oldCus.getCustType():
                (connectionOrder.getCustomer().getCustType()!=null?connectionOrder.getCustomer().getCustType():"VIE"));
        profileBO.setCustType(custType);
        profileBO.setServiceType("M");
        profileBO.setIdNo(mIdNo);
        return profileBO;
    }

    private void setEnabledView(boolean b) {
        mEdtTenKH.setEnabled(b);
        // mEdtISDN.setEnabled(b);
        // mEdtMaGiayTo.setEnabled(b);
        mEdtNgayCap.setEnabled(b);
        mEdtNoiCap.setEnabled(b);
        mEdtNgaySinh.setEnabled(b);
        mEdtToDuongSoNha.setEnabled(b);
        mSpGioiTinh.setEnabled(b);
        edit_ngayhethan.setEnabled(b);
//    mSpQuan.setEnabled(b);
//    mSpThanhPho.setEnabled(b);
//    mSpXa.setEnabled(b);
    }

    private void confirmDK() {

        CommonActivity.createDialog(OmiRegisterNewFragment.this,
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
            CommonActivity.createAlertDialog(OmiRegisterNewFragment.this,
                    R.string.chua_nhap_serial,
                    R.string.app_name).show();
            mEdtSerial.requestFocus();
            return false;
        }
        if (!sub.getSerial().endsWith(serial)) {
            Log.d("SERIAL", sub.getSerial());
            CommonActivity.createAlertDialog(OmiRegisterNewFragment.this, R.string.serial_not_match,
                    R.string.app_name).show();
            mEdtSerial.requestFocus();
            return false;
        }

        if (reasonId == null) {
            CommonActivity.createAlertDialog(OmiRegisterNewFragment.this,
                    R.string.chua_chon_ly_do_dang_ky, R.string.app_name).show();
            return false;
        }

        if (CommonActivity.isNullOrEmpty(name)) {
            CommonActivity.createAlertDialog(OmiRegisterNewFragment.this, R.string.chua_nhap_ten_kh,
                    R.string.app_name).show();
            mEdtTenKH.requestFocus();
            return false;
        }

        if (StringUtils.CheckCharSpecical(name)) {
            CommonActivity.createAlertDialog(OmiRegisterNewFragment.this, getResources().getString(R.string.checkcharspecical),
                    getResources().getString(R.string.app_name)).show();
            mEdtTenKH.requestFocus();
            return false;
        }

        if (mEdtTenKH.getText().toString().length() < 4) {
            CommonActivity.createAlertDialog(OmiRegisterNewFragment.this, getResources().getString(R.string.namecust),
                    getResources().getString(R.string.app_name)).show();
            return false;
        }

        if (CommonActivity.isNullOrEmpty(mEdtNgaySinh.getText().toString())) {
            CommonActivity.createAlertDialog(OmiRegisterNewFragment.this, R.string.cus_birthday_empty,
                    R.string.app_name).show();
            mEdtNgaySinh.requestFocus();
            return false;
        }

        Date birthDate = sdf.parse(mEdtNgaySinh.getText().toString().trim());
        if (birthDate.after(dateNow)) {
            CommonActivity.createAlertDialog(OmiRegisterNewFragment.this,
                    getResources().getString(R.string.nsnhohonhtai),
                    getResources().getString(R.string.app_name)).show();
            return false;
        }

        if (oldCus == null && CommonActivity.isNullOrEmpty(mEdtNgayCap.getText().toString())) {
            CommonActivity.createAlertDialog(OmiRegisterNewFragment.this,
                    R.string.cus_id_issue_date_empty, R.string.app_name).show();
            mEdtNgayCap.requestFocus();
            return false;
        }

        if (CommonActivity.isNullOrEmpty(idIssuePlace) && oldCus == null) {
            CommonActivity.createAlertDialog(OmiRegisterNewFragment.this, R.string.chua_nhap_noi_cap,
                    R.string.app_name).show();
            mEdtNoiCap.requestFocus();
            return false;
        }
        if (oldCus == null) {
            Date datengaycap = sdf.parse(mEdtNgayCap.getText().toString().trim());
            if (datengaycap.after(dateNow)) {
                CommonActivity.createAlertDialog(OmiRegisterNewFragment.this,
                        getString(R.string.ngaycapnhohonngayhientai),
                        getString(R.string.app_name))
                        .show();
                return false;
            }

            if (datengaycap.before(birthDate)) {
                CommonActivity.createAlertDialog(OmiRegisterNewFragment.this, getString(R.string.checkcmtngaycap),
                        getString(R.string.app_name)).show();
                return false;
            }

            if ("ID".equals(idType)) {
                if (CommonActivity.getDiffYearsMain(birthDate, dateNow) < 14) {
                    CommonActivity.createAlertDialog(OmiRegisterNewFragment.this, this.getString(R.string.khdd14),
                            this.getString(R.string.app_name)).show();
                    return false;
                }

                if (CommonActivity.getDiffYearsMain(birthDate, datengaycap) < 14) {
                    CommonActivity.createAlertDialog(OmiRegisterNewFragment.this, getString(R.string.checkcmtngaycap14),
                            getString(R.string.app_name)).show();
                    return false;
                }
                if (CommonActivity.getDiffYears(datengaycap, dateNow) > 15) {
                    CommonActivity.createAlertDialog(OmiRegisterNewFragment.this,
                            getString(R.string.checkcmtngaycap15),
                            getString(R.string.app_name)).show();
                    return false;
                }
            }
            if ("MID".equals(idType)) {
                if (idNo.length() < 6 || idNo.length() > 12) {
                    CommonActivity.createAlertDialog(OmiRegisterNewFragment.this,
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
                            CommonActivity.createAlertDialog(OmiRegisterNewFragment.this,
                                    getString(R.string.validateExpiredate),
                                    getString(R.string.app_name)).show();
                            return false;
                        }
                        Date datenExpired = sdf.parse(edit_ngayhethan.getText().toString().trim());
                        if (datenExpired.before(datengaycap)) {
                            CommonActivity.createAlertDialog(OmiRegisterNewFragment.this,
                                    getString(R.string.checkhethanngaycap),
                                    getString(R.string.app_name)).show();
                            return false;
                        }
                        if (datenExpired.before(dateNow)) {
                            CommonActivity.createAlertDialog(OmiRegisterNewFragment.this,
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
            CommonActivity.createAlertDialog(OmiRegisterNewFragment.this,
                    R.string.chua_nhap_dia_chi,
                    R.string.app_name).show();
            mEdtNoiCap.requestFocus();
            return false;
        }

        if (typeCode1 == 2) {
            if (CommonActivity.isNullOrEmpty(edtNameHS.getText().toString())) {

                CommonActivity.createAlertDialog(OmiRegisterNewFragment.this,
                        R.string.nhap_ten_hoc_sinh, R.string.app_name).show();
                edtNameHS.requestFocus();
                return false;
            }
            if (CommonActivity.isNullOrEmpty(edtAgeHS.getText().toString())) {
                CommonActivity.createAlertDialog(OmiRegisterNewFragment.this,
                        R.string.pupil_birthday_not_null, R.string.app_name).show();
                edtNameHS.requestFocus();
                return false;
            }
        }

        if (typeCode1 == 1) {
            if (CommonActivity.isNullOrEmpty(mEdtMaGiayToDacBiet.getText()
                    .toString())) {
                CommonActivity.createAlertDialog(OmiRegisterNewFragment.this,
                        R.string.nhap_ma_giay_to_dac_biet, R.string.app_name)
                        .show();
                mEdtMaGiayToDacBiet.requestFocus();
                return false;
            }
            if (CommonActivity.isNullOrEmpty(mCodeDoiTuong)) {
                CommonActivity.createAlertDialog(OmiRegisterNewFragment.this,
                        R.string.nhap_ten_doi_tuong, R.string.app_name).show();

                return false;
            }
            if (CommonActivity.isNullOrEmpty(mCodeDonVi)) {
                CommonActivity.createAlertDialog(OmiRegisterNewFragment.this,
                        R.string.nhap_ten_don_vi, R.string.app_name).show();
                return false;
            }

            if (CommonActivity.isNullOrEmpty(mEdtNgayBD.getText().toString())) {
                CommonActivity.createAlertDialog(OmiRegisterNewFragment.this,
                        R.string.nhap_ngay_bat_dau, R.string.app_name).show();
                mEdtNgayBD.requestFocus();
                return false;
            }
            if (CommonActivity.isNullOrEmpty(mEdtNgayKT.getText().toString())) {
                CommonActivity.createAlertDialog(OmiRegisterNewFragment.this,
                        R.string.nhap_ngay_ket_thuc, R.string.app_name).show();
                mEdtNgayKT.requestFocus();
                return false;
            }
            if (CommonActivity.isNullOrEmpty(mEdtQuocTich.getText().toString())) {
                CommonActivity.createAlertDialog(OmiRegisterNewFragment.this,
                        R.string.nhap_quoc_tich,
                        R.string.app_name).show();
                mEdtQuocTich.requestFocus();
                return false;
            }
        }

        return true;
    }
    private void uploadImage() {

        timeStart = new Date();
//        new AsyncTaskDK(OmiRegisterNewFragment.this).execute();
        if ((connectionOrder != null && !CommonActivity.isNullOrEmpty(connectionOrder.getProcessInstanceId()))
                || CommonActivity.isNullOrEmpty(profileBO.getProfileRecords())) {
            new AsyncTaskDK(OmiRegisterNewFragment.this).execute();
            return;
        }
    }


    protected void timKiemDonVi() {
        dialogLockBoxInfo();
    }

    private void dialogLockBoxInfo() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // LocationService locationService = arrayListLocation.get(position);

        LayoutInflater inflater = LayoutInflater.from(OmiRegisterNewFragment.this);
        View dialogView = inflater.inflate(R.layout.dialog_don_vi, null);

        builder.setView(dialogView);
        dialogDonVi = builder.create();

        final EditText edtMaDonVi = (EditText) dialogView
                .findViewById(R.id.edtMaDonVi);
        final EditText edtTenDonVi = (EditText) dialogView
                .findViewById(R.id.edtTenDonVi);
        Button btnTimKiem = (Button) dialogView.findViewById(R.id.btnTimKiem);

        lvListDonVi = (ListView) dialogView.findViewById(R.id.lvLockBoxInfo);
        // LockBoxInfoAdapter adapterBoxInfo = new LockBoxInfoAdapter(
        // getApplicationContext(), listLockBoxInfo);
        // lvListLockBox.setAdapter(adapterBoxInfo);

        btnTimKiem.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String maDonVi = edtMaDonVi.getText().toString().trim();
                String maTenVi = edtTenDonVi.getText().toString().trim();

                hideKeyBoard();
                if (CommonActivity.isNullOrEmpty(maDonVi) && CommonActivity.isNullOrEmpty(maTenVi)) {
                    Toast.makeText(getApplicationContext(),
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
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
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
            dialog = new ProgressDialog(OmiRegisterNewFragment.this);
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage(getResources().getString(R.string.waitting));
            this.dialog.show();
            super.onPreExecute();
        }

        @Override
        protected ArrayList<DoiTuongObj> doInBackground(Void... params) {
            BhldDAL dal = null;
            try {
                dal = new BhldDAL(OmiRegisterNewFragment.this);
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
                            OmiRegisterNewFragment.this,
                            getResources().getString(
                                    R.string.ko_tim_thay_don_vi_nao),
                            getResources().getString(R.string.app_name)).show();
                }
                // parseResultError(result);
                // dfdff

                // dfdf

                DonViAdapter adapter = new DonViAdapter(
                        OmiRegisterNewFragment.this, mAratListDonVi);
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

    public String requestSeviceDonVi(String maDonVi, String tenDonVi,
                                     String codeDoiTuong) {
        String reponse = null;
        String original = null;

        String xml = mBccsGateway.getXmlCustomer(
                createXMLDonVi(maDonVi, tenDonVi, codeDoiTuong),
                "mbccs_getListDepByObject");
        Log.e("TAG89", "xml Don Vi" + xml);
        try {
            reponse = mBccsGateway.sendRequest(xml, Constant.BCCS_GW_URL,
                    OmiRegisterNewFragment.this, "mbccs_getListDepByObject");
            Log.e("TAG8", "reponse Don Vi" + reponse);
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

    public class GetLisObjectByProductCode extends
            AsyncTask<Void, Void, String> {

        private final String productCode;
        private final ProgressDialog dialog;

        public GetLisObjectByProductCode(String productCode) {
            this.productCode = productCode;
            dialog = new ProgressDialog(OmiRegisterNewFragment.this);
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage(getResources().getString(R.string.waitting));
            // this.dialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            String original = requestSeviceObjectByProductCode(productCode);
            return original;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                if (CommonActivity.isNullOrEmpty(result)) {
                    CommonActivity.createAlertDialog(OmiRegisterNewFragment.this,
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
                                    mArrayListSpec, OmiRegisterNewFragment.this);
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
                                OmiRegisterNewFragment.this, description,
                                saleOutput.getErrorCode()).show();

                    }

                }
            } catch (Exception e) {
                Log.e("exceptionnnnn", "aaaaaaaaaa", e);
            }
            // if (dialog.isShowing()) {
            // dialog.dismiss();
            // }

            super.onPostExecute(result);
        }
    }


//    public class GetLisRecordPrepaidTask extends AsyncTask<Void, Void, String> {
//
//        private String productCode;
//
//        public GetLisRecordPrepaidTask(String productCode, Context context) {
//            this.productCode = productCode;
//            prbRecord.setVisibility(View.VISIBLE);
//            btnRefreshRecord.setVisibility(View.GONE);
//            // this.dialog.show();
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected String doInBackground(Void... params) {
//            String original = requestSevice(productCode);
////            } else {
////            }
//            return original;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            prbRecord.setVisibility(View.GONE);
//            btnRefreshRecord.setVisibility(View.GONE);
//            if (CommonActivity.isNullOrEmpty(result)) {
//                CommonActivity.createAlertDialog(RegisterNewFragment.this,
//                        getResources().getString(R.string.errorNetwork),
//                        getResources().getString(R.string.app_name)).show();
//            } else {
//                // chi goi o day thoi moi dung
//                profileBO.getHashmapFileObj().clear();
//                // parseResultError(result);
//                if (mLnUploadImage.getChildCount() > 0) {
//                    mLnUploadImage.removeAllViews();
//                }
//                mListIvO.clear();
//                parseResultListRecordPrepaid(result);
//
//            }
//            Log.e("TAG6", "result List productCode : " + result);
//
//            super.onPostExecute(result);
//        }
//    }

    public String requestSevice(String productCode) {

        String reponse = null;
        String original = null;

        String xml = mBccsGateway.getXmlCustomer(createXML(productCode),
                "mbccs_getListRecordPrepaid");
        Log.e("TAG8", "xml getListRecordPrepaid" + xml);
        try {
            reponse = mBccsGateway.sendRequest(xml, Constant.BCCS_GW_URL,
                    OmiRegisterNewFragment.this, "mbccs_getListRecordPrepaid");
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
                            OmiRegisterNewFragment.this,
                            "mbccs_getListObjectByProductCodeBccs2");
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

    private String createXMLDonVi(String maDonVi, String tenDonVi,
                                  String codeDoiTuong) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<ws:getListDepByObject>");
        stringBuilder.append("<input>");
        stringBuilder.append("<token>" + Session.getToken() + "</token>");
        stringBuilder.append("<deptCode>" + maDonVi + "</deptCode>");
        stringBuilder.append("<deptName>" + tenDonVi + "</deptName>");
        stringBuilder.append("<objectCode>" + codeDoiTuong + "</objectCode>");
        stringBuilder.append("</input>");
        stringBuilder.append("</ws:getListDepByObject>");
        return stringBuilder.toString();
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

//    private void parseResultListRecordPrepaid(String result) {
//
//        arrayOfArrayList = new ArrayList<ArrayList<RecordPrepaid>>();
//        for (int i = 0; i < 50; i++) {
//            ArrayList<RecordPrepaid> arrayList = new ArrayList<RecordPrepaid>();
//            arrayOfArrayList.add(arrayList);
//        }
//
//        if (result != null) {
//            try {
//                Log.e("TAG69", result);
//                XmlDomParse domParse = new XmlDomParse();
//                Document document = domParse.getDomElement(result);
//
//                NodeList nodeListErrorCode = document
//                        .getElementsByTagName("lstProfileRecord");
//
//                for (int i = 0; i < nodeListErrorCode.getLength(); i++) {
//                    Node mNode = nodeListErrorCode.item(i);
//                    Element element = (Element) mNode;
//                    String id = domParse.getValue(element, "id");
//                    String code = domParse.getValue(element, "code");
//                    String name = domParse.getValue(element, "name");
//                    RecordPrepaid recordPrepaid = new RecordPrepaid(id, code,
//                            name);
//                    ArrayList<RecordPrepaid> arrayList = new ArrayList<RecordPrepaid>();
//                    arrayList.add(recordPrepaid);
//                    int ID = Integer.parseInt(id);
//
//                    arrayOfArrayList.get(ID).add(recordPrepaid);
//                }
//
//                Log.e("TAG69", "arrayOfArrayList : " + arrayOfArrayList);
//
//            } catch (Exception e) {
//                Log.e("TAG8", "Exception", e);
//            }
//        }
//
//        // Tao spinner upload anh
//        createSpinnerUploadImage(arrayOfArrayList);
//    }

//    private void createSpinnerUploadImage(
//            ArrayList<ArrayList<RecordPrepaid>> arrayOfArrayList) {
//        int h = (int) getResources().getDimension(R.dimen.height_imv);
//        int w = (int) getResources().getDimension(R.dimen.width_imv);
//
//        listSpinner = new ArrayList<Spinner>();
//
//        for (int i = 0; i < arrayOfArrayList.size(); i++) {
//            ArrayList<RecordPrepaid> arrayListRecord = arrayOfArrayList.get(i);
//            if (arrayListRecord.size() > 0) {
//                mLnUpImage.setVisibility(View.VISIBLE);
//                isUpImage = true;
//
//                // tao spinner
//                LinearLayout lnUpload = new LinearLayout(
//                        getApplicationContext());
//                lnUpload.setOrientation(LinearLayout.HORIZONTAL);
//
//                Spinner spinner = new Spinner(this);
//
//                Log.d(Constant.TAG, "imageId : " + i);
//                spinner.setId(i);
//                UploadImageAdapter adapter = null;
//                if(isMoreThan && checkRecord(arrayListRecord)){
//                    ArrayList<RecordPrepaid> arrayListR = new ArrayList<>();
//                    for (RecordPrepaid item: arrayListRecord) {
//                        if("HDMBTT".equals(item.getCode())){
//                            arrayListR.add(item);
//                            break;
//                        }
//                    }
//                    adapter = new UploadImageAdapter(
//                            arrayListR, this);
//                }else{
//                    adapter = new UploadImageAdapter(
//                            arrayListRecord, this);
//                }
//                spinner.setAdapter(adapter);
//                final ImageView image = new ImageView(getApplicationContext());
//                LinearLayout.LayoutParams layoutParamsIv = new LinearLayout.LayoutParams(
//                        w, h);
//                image.setId(i);
//                image.setBackgroundResource(R.drawable.logo_vt);
//
//                ImageObj imageObj = new ImageObj(false, image);
//                mListIvO.add(imageObj);
//
//                LinearLayout.LayoutParams layoutSpinner = new LinearLayout.LayoutParams(
//                        0, LinearLayout.LayoutParams.MATCH_PARENT, 70);
//                lnUpload.addView(spinner, layoutSpinner);
//                lnUpload.addView(image, layoutParamsIv);
//
//                LinearLayout.LayoutParams layoutLnUpload = new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.MATCH_PARENT, h);
//                layoutLnUpload.setMargins(20, 10, 10, 20);
//                mLnUploadImage.addView(lnUpload, layoutLnUpload);
//
//                image.setOnClickListener(new OnClickListener() {
//
//                    @Override
//                    public void onClick(View view) {
//                        Log.d(Constant.TAG, "view.getId() : " + view.getId());
//                        pickImage(view.getId());
//                    }
//                });
//                listSpinner.add(spinner);
//            } else {
//                // ko co truong upload anh
//                isUpImage = true;
//            }
//        }
//        if (!isUpImage) {
//            // ko can fai up anh
//            mLnUpImage.setVisibility(View.GONE);
//        }
//    }

    private void pickImage(final int imageId) {
        Log.d(Constant.TAG, "pickImage() : " + imageId);

        if (profileBO.getHashmapFileObj().containsKey(String.valueOf(imageId))) {
            ArrayList<FileObj> fileObjs = profileBO.getHashmapFileObj().get(String
                    .valueOf(imageId));

            Intent intent = new Intent(getApplicationContext(),
                    ImagePreviewActivity.class);
            intent.putExtra("imageId", imageId);
            intent.putExtra("fileObjs", fileObjs);

            startActivityForResult(intent, CHANNEL_UPDATE_IMAGE.PICK_IMAGE);
        } else {
            Config config = new Config.Builder().setSelectionLimit(4).build();

            Intent intent = new Intent(getApplicationContext(),
                    ImagePickerActivity.class);
            intent.putExtra("imageId", imageId);

            ImagePickerActivity.setConfig(config);
            startActivityForResult(intent, CHANNEL_UPDATE_IMAGE.PICK_IMAGE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG 9", "requestCode : " + requestCode + " resultCode : "
                + resultCode);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CHOOSE_CHANNEL:
                    Staff selectedStaff = (Staff) data.getExtras().getSerializable(
                            "staff");

                    if (selectedStaff != null) {
                        tvCTV.setText(getString(R.string.registerFor) + " "
                                + selectedStaff.getName() + " - "
                                + selectedStaff.getStaffCode());
                        imgDeleteChannel.setVisibility(View.VISIBLE);
                    } else {
                        tvCTV.setText(getResources().getString(R.string.chooseCTV));
                        imgDeleteChannel.setVisibility(View.GONE);
                    }
                    break;
                case CHANNEL_UPDATE_IMAGE.PICK_IMAGE:
                    fragment.onActivityResult(requestCode,
                            resultCode, data);
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
                    if (streetBlock != null) {
                        area.setStreetBlock(streetBlock.getAreaCode());
//                        area.setStreet(streetBlock.getName());
                    }
                    area.setHome(home);
                    area.setStreet(streetName);
                    area.setPrecinct(precinct.getPrecinct());
                    area.setPrecinctName(precinct.getName());
//                    String addressPlus = precinct.getName() + " " + district.getName() + " " + province.getName();
//                    if(!CommonActivity.isNullOrEmpty(streetBlock)){
//                        addressPlus =   streetBlock.getName() + " " + addressPlus;
//                    }
//                    if(!CommonActivity.isNullOrEmpty(home)){
//                        addressPlus = home + " " + addressPlus;
//                    }
//                    if(!CommonActivity.isNullOrEmpty(streetName)){
//                        addressPlus = streetName + " " + addressPlus;
//                    }
                    area.setName(address);

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
//            if (isUpImage) {
            if (profileBO.getHashmapFileObj() != null
                    && !profileBO.getHashmapFileObj().isEmpty()) {
                lstFilePath = FileUtils.createFilePaths(profileBO.getHashmapFileObj());
            }
//            }

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
            // rawData.append("<ws:exportStockCollaborator>");
            input.addValidateGateway("wscode", "mbccs_doUpdateCustomer");


            try {
                String reponse = input.sendRequest(xml, Constant.BCCS_GW_URL,
                        OmiRegisterNewFragment.this, "mbccs_doUpdateCustomer");
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
            mBtnRegister.setVisibility(View.VISIBLE);
            try {
                Log.i("TAG 99 DK", " result : " + result);
                if (progressDangKy != null && progressDangKy.isShowing()) {
                    progressDangKy.dismiss();
                }
                if (CommonActivity.isNullOrEmpty(result)) {
                    CommonActivity.createAlertDialog(OmiRegisterNewFragment.this,
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
                        mBtnRegister.setVisibility(View.GONE);
                        actionAuditId = parseOuput.getDescription();
                        if(!CommonActivity.isNullOrEmpty(profileBO.getHashmapFileObj()) && !CommonActivity.isNullOrEmpty(lstFilePath)){
                            new AsynTaskZipFile(OmiRegisterNewFragment.this,
                                    onPostExecuteListener,
                                    moveLogInAct,
                                    profileBO.getHashmapFileObj(),
                                    lstFilePath).execute();
                        }else{
                            CommonActivity.createAlertDialog(OmiRegisterNewFragment.this,
                                    getString(R.string.register_roll_up_success),
                                    getString(R.string.app_name)).show();
                        }

                    } else {
                        if (arrFileBackUp != null && arrFileBackUp.size() > 0) {
                            for (FileObj fileObj : arrFileBackUp) {
                                File tmp = new File(fileObj.getPath());
                                tmp.delete();
                            }
                        }
                        CommonActivity.createAlertDialog(OmiRegisterNewFragment.this,
                                parseOuput.getDescription(),
                                getString(R.string.app_name)).show();
                    }

                }
            } catch (Exception e) {
                Log.d(Constant.TAG, "error", e);
                CommonActivity.createAlertDialog(OmiRegisterNewFragment.this,
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

                    String des = getString(R.string.register_success);
                    new AsyncTaskUpdateImageOfline(OmiRegisterNewFragment.this, result,
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
        stringBuilder.append(getXmlSub());
        stringBuilder.append("</subDTO>");
        stringBuilder.append("</cmInput>");

        stringBuilder.append("</ws:doUpdateCustomer>");

        Log.d("TAG RegisterNewFragment", "stringBuilder.toString()"
                + stringBuilder.toString());
        String xmlRp = stringBuilder.toString().replace(">null<","><");
        String xml = mBccsGateway.getXmlCustomer(xmlRp,
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

                    final Dialog dialog = new Dialog(OmiRegisterNewFragment.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.connection_layout_lst_sobaodanh);

                    ListView lvsobaodanh = (ListView) dialog
                            .findViewById(R.id.lvsobaodanh);

                    Button btnhuy = (Button) dialog.findViewById(R.id.btnhuy);

                    GetSobaodanhAdapter getSobaodanhAdapter = new GetSobaodanhAdapter(
                            result, OmiRegisterNewFragment.this);
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
                            OmiRegisterNewFragment.this,
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
                            OmiRegisterNewFragment.this, description,
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
                        Constant.BCCS_GW_URL, OmiRegisterNewFragment.this,
                        "mbccs_getThongTinThiSinh");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();

                // parse xml
                Document doc = parse.getDomElement(original);
                NodeList nl = doc.getElementsByTagName("return");
                NodeList nodechild = null;
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
            LoginDialog dialog = new LoginDialog(OmiRegisterNewFragment.this, ";menu.register.mobile2;");
            dialog.show();
        }
    };

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.btnHome:
                break;
            case R.id.relaBackHome:

                break;
            case R.id.btnRefreshReason:
                new CacheDatabaseManager(this).insertCacheReasonSale(null);
                AsynctaskGetListReason as = new AsynctaskGetListReason(this);
                as.execute();
                break;
            case R.id.tvAddress:
                SharedPreferences preferences = getSharedPreferences(Define.PRE_NAME, Activity.MODE_PRIVATE);
                String name = preferences.getString(Define.KEY_MENU_NAME, "");

                if (oldCus == null || name.contains(";menu.tdttkh;")) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isCheckStreetBlock", false);
                    if (area != null) {
                        bundle.putString("strProvince", area.getProvince());
                        bundle.putString("strDistris", area.getDistrict());
          
                        bundle.putString("areaHomeNumber", area.getHome());
                        bundle.putString("strStreetBlock", area.getStreetBlock());
                        bundle.putString("areaFlow", area.getStreet());
                    }
                    Intent intent = new Intent(this, CreateAddressCommon.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, REQUEST_CHOOSE_ADDRESS);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return false;
    }

    @SuppressWarnings("unused")
    private class AsynctaskGetListReason extends
            AsyncTask<Void, Void, ParseOuput> {
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
            boolean isCheckNull = false;
            if ("0".equals(result.getErrorCode())) {
                List<ReasonDTO> lstAllReason = result.getLstReasonDTO();
                List<ReasonDTO> lstReason = new ArrayList<>();

                if (!CommonActivity.isNullOrEmpty(connectionOrder.getProductInfo().getRegReasonId())) {
                    String regReasonId = connectionOrder.getProductInfo().getRegReasonId() + "";

                    for (ReasonDTO reasonDTO : lstAllReason) {
                        if (regReasonId.equalsIgnoreCase(reasonDTO.getReasonId())) {
                            lstReason.add(reasonDTO);
                        }
                    }
                } else {
                    isCheckNull = true;
                    CommonActivity.createAlertDialog(OmiRegisterNewFragment.this, R.string.reason_null, R.string.app_name).show();
                }
                ArrayAdapter<ReasonDTO> adapter = new ArrayAdapter<>(
                        mActivity, R.layout.spinner_item, lstReason);
                adapter.setDropDownViewResource(R.layout.spinner_item);
                spnReason.setAdapter(adapter);
                if (lstReason.size() > 0) {
                    spnReason.setSelection(0);
                } else {
                    if (!isCheckNull)
                        CommonActivity.createAlertDialog(OmiRegisterNewFragment.this, R.string.reason_false, R.string.app_name).show();
                }


            } else {

                CommonActivity.createAlertDialog(OmiRegisterNewFragment.this, R.string.lst_reason_null, R.string.app_name).show();
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
        }

        private ParseOuput getListReason() {
            ParseOuput result;
            String original = "";

//            ArrayList<ReasonDTO> lstReason = new CacheDatabaseManager(mActivity)
//                    .getListCacheReasonSalet();
//            if (lstReason != null && !lstReason.isEmpty()) {
//                result = new SaleOutput();
//                result.setErrorCode("0");
//                result.setLstReason(lstReason);
//                return result;
//            }
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
//                if (result.getLstReason() != null
//                        && !result.getLstReason().isEmpty()) {
//                    new CacheDatabaseManager(mActivity)
//                            .insertCacheReasonSale(result.getLstReason());
//                }
            } catch (Exception e) {
                Log.e("getListIP", e.toString() + "description error", e);
                result = new ParseOuput();
                result.setDescription(getString(R.string.exception) + " - " + e);
                result.setErrorCode(Constant.ERROR_CODE);
            }
            return result;

        }
    }

    private class FindVerifiedOwnerByListIdNoAsyn extends
            AsyncTask<String, Void, ArrayList<SubscriberDTO>> {
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
                    lnthuebaochinhchu.setVisibility(View.VISIBLE);
                    TextView tvThueBaoChinhChu = (TextView) findViewById(R.id.tvThueBaoChinhChu);
                    tvThueBaoChinhChu.setText(R.string.dsthuebaodskh);

                    arrSubscriberDTO.addAll(result);
                    showSelectIsdnOwner();
                } else {
                    TextView tvThueBaoChinhChu = (TextView) findViewById(R.id.tvThueBaoChinhChu);
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
                            .createAlertDialog(OmiRegisterNewFragment.this,
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
//                        if (!file.mkdirs()) {
//                            file.createNewFile();
//                        }
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

        final Dialog dialogIsdn = new Dialog(this);
        dialogIsdn.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogIsdn.setContentView(R.layout.dialog_search_isdn);
        lvsubparent = (ExpandableHeightListView) dialogIsdn
                .findViewById(R.id.lvsubparent);
        lvsubparent.setExpanded(true);
        checkSubAdapter = new CheckSubAdapter(this, arrSubscriberDTO, this);
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
                checkSubAdapter = new CheckSubAdapter(OmiRegisterNewFragment.this,
                        arrSubscriberDTO, OmiRegisterNewFragment.this);
                checkSubAdapter.notifyDataSetChanged();
                checkSubAdapter.SearchInput(input);
                lvsubparent.setAdapter(checkSubAdapter);
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
            if (profileBO != null
                    && profileBO.getHashmapFileObj() != null
                    && !profileBO.getHashmapFileObj().isEmpty()) {
                thumbnails.removeAllViews();
                horizontalScrollView.setVisibility(View.VISIBLE);

                ArrayList<String> lstData = new ArrayList<>();
                for (Map.Entry<String, ArrayList<FileObj>> entry : profileBO.getHashmapFileObj().entrySet()) {
                    for (FileObj fileObj : entry.getValue()) {
                        Log.d("onFinishDSFListener", fileObj.getFullPath());
                        lstData.add(fileObj.getFullPath());
                    }
                }

//                ImageUtils.loadImageHorizontal(getApplicationContext(), inflater, thumbnails, lstData);

                ImageUtils.loadImageHorizontal(getApplicationContext(), inflater, thumbnails, lstData, new com.viettel.bss.viettelpos.v4.listener.OnClickListener() {
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

//    private boolean validateProfileContract(){
//        for (java.util.Map.Entry<String, ArrayList<FileObj>> entry : hashmapFileObj.entrySet()) {
//            ArrayList<FileObj> listFileObjs = entry.getValue();
//            if(!CommonActivity.isNullOrEmpty(listFileObjs)){
//                if("HDMBTT".equals(listFileObjs.get(0).getCodeSpinner())){
//                    return  true;
//                }
//            }
//        }
//        return false;
//    }

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

    private boolean checkRecord(ArrayList<RecordPrepaid> arrayList) {
        for (RecordPrepaid item : arrayList) {
            if ("HDMBTT".equals(item.getCode())) {
                return true;
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
//                if(isMoreThan){
//                    CommonActivity.createAlertDialog(OmiRegisterNewFragment.this,description,OmiRegisterNewFragment.this.getString(R.string.app_name)).show();
//                }
            }
        }
    }


    public void zoomImageFromThumb(final View thumbView, String path) {
        final int mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        Bitmap myBitmap = BitmapFactory.decodeFile(path);

        // Load the high-resolution "zoomed-in" image.
        final ImageView expandedImageView = (ImageView) this.findViewById(
                R.id.expanded_image);
        expandedImageView.setImageBitmap(myBitmap);

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        this.findViewById(R.id.scrMain)
                .getGlobalVisibleRect(finalBounds, globalOffset);
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

        //bong sung luong ho so voi luong omni
        if(profileBO !=null && profileBO.getProfileRecords() != null && profileBO.getProfileRecords().size() > 0){
            stringBuilder.append("<profileDocumentDTO>");
            for (ProfileRecord item: profileBO.getProfileRecords()) {
                stringBuilder.append("<lstFile>" + item.toXmlOmni());
                stringBuilder.append("</lstFile>");
            }
            stringBuilder.append("</profileDocumentDTO>");
        }

        return stringBuilder.toString();
    }

    private String getXmlSub() {
        Date date = null;
        StringBuilder stringBuilder = new StringBuilder("");
        // bo sung omichanel
        if(!CommonActivity.isNullOrEmpty(connectionOrder)){
            stringBuilder.append("<omniProcessId>" + connectionOrder.getProcessInstanceId());
            stringBuilder.append("</omniProcessId>");
            stringBuilder.append("<omniTaskId>" + connectionOrder.getTaskId());
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
}
