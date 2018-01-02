package com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity;

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
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter.GetFeeBCCSAdapter;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustIdentityDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustTypeDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustomerDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferingDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductPackageFeeDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductSpecCharDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ReasonDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.captcha.Captcha;
import com.viettel.bss.viettelpos.v4.captcha.MathCaptcha;
import com.viettel.bss.viettelpos.v4.captcha.MathCaptcha.MathOptions;
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
import com.viettel.bss.viettelpos.v4.commons.RequestCodeFragment;
import com.viettel.bss.viettelpos.v4.commons.SdCardHelper;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.commons.auto.AutoCompleteUtil;
import com.viettel.bss.viettelpos.v4.commons.auto.AutoConst;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.FragmentSearchCusTypeMobile;
import com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.CheckSubAdapter;
import com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.CheckSubAdapter.OnChangeCheckedSub;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.SexBeans;
import com.viettel.bss.viettelpos.v4.connecttionMobile.handler.SubscriberDTOHanlder;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.HotLineReponseDetail;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ReceiveRequestBean;
import com.viettel.bss.viettelpos.v4.connecttionService.listener.ExpandableHeightListView;
import com.viettel.bss.viettelpos.v4.customer.manage.AsyncTaskUpdateImageOfline;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.customview.adapter.DoiTuongObj;
import com.viettel.bss.viettelpos.v4.customview.adapter.DonViAdapter;
import com.viettel.bss.viettelpos.v4.customview.adapter.ObjDoiTuongAdapter;
import com.viettel.bss.viettelpos.v4.customview.obj.FileObj;
import com.viettel.bss.viettelpos.v4.customview.obj.RecordPrepaid;
import com.viettel.bss.viettelpos.v4.customview.obj.SpecObject;
import com.viettel.bss.viettelpos.v4.dal.CacheDatabaseManager;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.hsdt.fragments.ProfileInfoDialogFragment;
import com.viettel.bss.viettelpos.v4.listener.OnFinishDSFListener;
import com.viettel.bss.viettelpos.v4.object.ProfileBO;
import com.viettel.bss.viettelpos.v4.omichanel.asynctask.PlaceOrderAsyncTask;
import com.viettel.bss.viettelpos.v4.sale.dal.BhldDAL;
import com.viettel.bss.viettelpos.v4.sale.object.SaleOutput;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.task.AsynTaskGetListRecordProfile;
import com.viettel.bss.viettelpos.v4.ui.image.picker.ImagePreviewActivity;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;
import com.viettel.bss.viettelpos.v4.utils.ImageUtils;
import com.viettel.bss.viettelpos.v4.work.object.Action;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;
import com.viettel.savelog.SaveLog;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.github.mikephil.charting.data.LineRadarDataSet;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.AsyncGetOptionSetValueV2;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.customer.manage.RegisterNewFragment;
import com.viettel.bss.viettelpos.v4.customer.object.SpinV2;
public class FragmentChangeSim extends FragmentCommon implements AutoConst {

    private static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    public static ArrayList<SpecObject> arrSpecObjects = new ArrayList<SpecObject>();
    public ReceiveRequestBean receiveRequestBean = new ReceiveRequestBean();
    public HotLineReponseDetail hotLineReponseDetail = new HotLineReponseDetail();

    OnClickListener onclickBackScreen = new OnClickListener() {
        @Override
        public void onClick(View v) {
            btnChangeSim.setVisibility(View.GONE);
        }
    };
    LinearLayout lnSelectProfile;

    HorizontalScrollView horizontalScrollView;
    LayoutInflater inflater;
    ImageView expandedImageView;
    FrameLayout frlMain;
    ProfileInfoDialogFragment profileInfoDialogFragment;
    private View mView = null;
    // define view init
    private EditText edtloaikh, edit_sogiayto, edit_tax, edit_tenKH, edit_ngaysinh, edit_ngaycap, edit_noicap,
            edit_note, edtdiachilapdat, edit_serialsim, editotp, edt_capcha;
    private ProgressBar prbCustType, prbotp, prbreasonds, prbreasonkh, prbGiayto;
    private Button btnRefreshCustType, btnkiemtra, btnedit, btnkiemtraserial, btnSendOTP, btnChangeSim, btnRefreshGiayto;
    private LinearLayout lnsogiayto, lntax, ln_sex, lnngaycap, lnnoicap, ln_lydosuakh, lnthongtindaidien, lnhoso, lncus,
            lnchangesim, ln_lydodoisim, lnquoctich, lnttinphikh, lnttinphids, lnotpds;
    private Spinner spinner_type_giay_to, spinner_gioitinh, spinner_reasonkh, spnReasonChangeSim, spinner_quoctich;
    private TextView txtsogiayto;
    private ImageView imgCapcha;

    // khai bao list danh sach loai khach hang
    private ArrayList<CustTypeDTO> arrCusTypeDTO = new ArrayList<CustTypeDTO>();
    private ArrayList<CustIdentityDTO> arrTypePaper = new ArrayList<CustIdentityDTO>();

    // chon thong tin khach hang cu
    private Dialog dialogCus;
    private GetListCustomerBccsAdapter adaGetListCustomerBccsAdapterDialog;
    private String subType = null;
    private Bundle mbundle = null;
    private CustTypeDTO custTypeDTO = null;
    private AreaObj areaProvicialPR;
    private AreaObj areaDistristPR;
    private AreaObj areaPrecintPR;
    private AreaObj areaGroupPR;
    private String areaFlowPR;
    private String areaHomeNumberPR;
    private StringBuilder addressPR;
    private String province = "";

    // arrlist district
    private String district = "";
    // arrlist precinct
    private String precinct = "";

    private String to = "";
    private Date dateNow;
    private String dateNowString;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private BCCSGateway mBccsGateway = new BCCSGateway();
    private String serviceType = "";
    private SubscriberDTO subscriberDTO = null;
    private ArrayList<CustIdentityDTO> arrCustIdentityDTO = new ArrayList<CustIdentityDTO>();
    private CustomerDTO customerDTO = null;
    private LinearLayout thumbnails;
    private ProfileBO profileBO = new ProfileBO();

    OnClickListener onclickChangeCustype = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), FragmentSearchCusTypeMobile.class);
            Bundle mBundle = new Bundle();
            if (subscriberDTO.isMarkNotOwner()) {
                mBundle.putString("GROUPTYPEKEY", customerDTO.getCustTypeDTO().getGroupType());
                mBundle.putString("CUSTYPEKEY", customerDTO.getCustTypeDTO().getCustType());
            } else {
                mBundle.putString("GROUPTYPEKEY", subscriberDTO.getCustomerDTOInput().getCustTypeDTO().getGroupType());
                mBundle.putString("CUSTYPEKEY", subscriberDTO.getCustomerDTOInput().getCustTypeDTO().getCustType());
            }

            intent.putExtras(mBundle);
            startActivityForResult(intent, 1513);
        }
    };

    private ArrayList<Spin> spinNation = new ArrayList<Spin>();
    private ArrayList<SexBeans> arrSexBeans = new ArrayList<SexBeans>();
    private List<ReasonDTO> arrReasonChangeCus = new ArrayList<ReasonDTO>();
    private List<ReasonDTO> arrReasonChangeSim = new ArrayList<ReasonDTO>();
    private Date timeStart = null;
    private Date timeEnd = null;
    // khai bao thong tin dac biet
    // bo sung thong tin dau noi goi hoc sinh
    private LinearLayout lnGoiCuocDacBietHs;
    private EditText edttenhs;
    private EditText edtngaysinhhs;
    private Date birthDateHs = null;
    // khai bao thong tin goi dac biet
    // define view form specical paper
    private LinearLayout lnTTGoiCuocDacBiet;
	private LinearLayout lnDoituong;
    private View viewSpec, viewSpec1;
    private Spinner spDoiTuong;
	private Spinner spnDoituong;
    private EditText edtQuocTich, tvDonVi, edtMaGiayToDacBiet, edtNgayBD, edtNgayKT;
    private ObjDoiTuongAdapter doiTuongAdapter;
    private String mCodeDoiTuong = "";
    // define time ngay bat dau va ngay het thuc
    private Calendar cal;
    private int day;
    private int month;
    private int year;
    private String mNgayBatDau = "";
    private String mNgayKetThuc = "";
    private String mCodeDonVi = "";
    private RelativeLayout rlchondonvi;
    private ArrayList<DoiTuongObj> mAratListDonVi = new ArrayList<DoiTuongObj>();
    private ListView lvListDonVi;
    private Dialog dialogDonVi = null;
    private DonViAdapter donviadapter;
    private View imgDeleteDV;
    private Date dateBD = null;
    private Date dateEnd = null;
    // khai bao thong tin dai dien khach hang doanh nghiep
    private EditText edtloaikhPR, edit_ngaycapPR, edtdiachilapdatPR;
    private ProgressBar prbCustTypePR;
    private Button btnRefreshCustTypeBR, btnkiemtraPR, btneditPR, btnthemmoiPR, btncancelPR;
    private Spinner spinner_type_giay_toPR;
    private Dialog dialogCusPR;
    private LinearLayout lnotp;
    private String checkIsSpec = "";
    private Button btnbar;

    // omni
    private String omniProcessId = "";
    private String omniTaskId = "";

    private Captcha cap = new MathCaptcha(100, 100, MathOptions.PLUS_MINUS_MULTIPLY);

    OnClickListener onclickChangeSim = new OnClickListener() {
        @Override
        public void onClick(View arg0) {
            initCap();
            timeStart = new Date();

            if (!CommonActivity.isNullOrEmpty(omniProcessId)
                    || CommonActivity.isNullOrEmpty(profileBO.getProfileRecords())) {
                ChangeSimAsyn changeSimAsyn = new ChangeSimAsyn(getActivity());
                changeSimAsyn.execute();
                return;
            }

            PlaceOrderAsyncTask placeOrderAsyncTask = new PlaceOrderAsyncTask(getActivity(),
                    new OnPostExecuteListener<String>() {
                @Override
                public void onPostExecute(String result, String errorCode, String description) {
                    if ("0".equals(errorCode)) {
                        omniProcessId = result;
                        ChangeSimAsyn changeSimAsyn = new ChangeSimAsyn(getActivity());
                        changeSimAsyn.execute();
                    } else {
                        if (description == null || description.isEmpty()) {
                            description = getActivity().getString(R.string.checkdes);
                        }
                        Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
                                description,
                                getActivity().getResources().getString(R.string.app_name));
                        dialog.show();
                    }
                }
            }, moveLogInAct, Constant.HSDT, profileBO.getProfileRecords());

            String cusName = "";
            if (!CommonActivity.isNullOrEmpty(customerDTO)) {
                cusName = customerDTO.getName() + "";
            }

            placeOrderAsyncTask.execute(cusName, Constant.CHANGE_SIM);
        }
    };
    // Tich khong chinh chu
    private ArrayList<SubscriberDTO> arrSubscriberDTO = new ArrayList<SubscriberDTO>();
    private View lnChinhChu;
    private ExpandableHeightListView lvsubparent;
    private CheckSubAdapter checkSubAdapter;
    private String custypeKey;
    private EditText edit_ngaykySub;
    private LinearLayout lnsigndate;
    private OnDateSetListener datePickerListener = new OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            Object obj = view.getTag();
            if (obj != null && obj instanceof EditText) {
                EditText editText = (EditText) obj;
                String month = "";
                String day = "";
                if (selectedMonth + 1 < 10) {
                    month = "0" + (selectedMonth + 1);
                } else {
                    month = "" + (selectedMonth + 1);
                }
                if (selectedDay < 10) {
                    day = "0" + selectedDay;
                } else {
                    day = "" + selectedDay;
                }

                editText.setText(day + "/" + month + "/" + selectedYear);

                int id = editText.getId();

                Calendar cal = Calendar.getInstance();
                cal.set(selectedYear, selectedMonth, selectedDay);
                Date date = cal.getTime();

            }
        }
    };
    private OnClickListener editTextListener = new OnClickListener() {

        @Override
        public void onClick(View view) {
            EditText edt = (EditText) view;
            Calendar cal = Calendar.getInstance();
            if (!CommonActivity.isNullOrEmpty(edt.getText().toString())) {
                Date date = DateTimeUtils.convertStringToTime(edt.getText().toString(), "dd/MM/yyyy");
                cal.setTime(date);

            }

            DatePickerDialog datePicker = new FixedHoloDatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, datePickerListener,
                    cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

            datePicker.getDatePicker().setTag(view);
            datePicker.show();
        }
    };

    private OnChangeCheckedSub onChangeCheckSub = new OnChangeCheckedSub() {
        @Override
        public void onChangeChecked(SubscriberDTO subscriberDTO) {
            for (SubscriberDTO item : arrSubscriberDTO) {
                if (subscriberDTO.getSubId().equals(item.getSubId())) {
                    item.setHasVerifiedOwner(!item.isHasVerifiedOwner());
                    break;
                }
            }
        }
    };

    private Animator mCurrentAnimator;
    OnFinishDSFListener onFinishDSFListener = new OnFinishDSFListener() {
        @Override
        public void onFinish(ProfileBO output) {
            profileBO = output;
            if ((profileBO != null
                    && profileBO.getHashmapFileObj() != null
                    && !profileBO.getHashmapFileObj().isEmpty())
                    || (profileBO.getProfileRecords() != null
                    && profileBO.getProfileRecords().size() > 0)) {

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflater = LayoutInflater.from(getActivity());
    }
	
	private  ArrayList<SpinV2> arrDoituong = new ArrayList<>();
	private  String subObject = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();

        int fromYear = calendar.get(Calendar.YEAR);
        int fromMonth = calendar.get(Calendar.MONTH) + 1;
        int fromDay = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.add(Calendar.DATE, 1);
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
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);

        dateNowString = fromDayStr + "/" + fromMothStr + "/" + fromYear + "";
        try {
            dateBD = sdf.parse(dateNowString);
            dateEnd = sdf.parse(dateNowString);
            birthDateHs = sdf.parse(dateNowString);
        } catch (Exception e) {
            Log.e("Exception", e.toString(), e);
        }

        mbundle = this.getArguments();


        if (mBundle != null) {
            hotLineReponseDetail = (HotLineReponseDetail) mBundle.getSerializable("hotLineReponseDetail");
            receiveRequestBean = (ReceiveRequestBean) mBundle.getSerializable("receiveRequestBean");
        }

        subscriberDTO = (SubscriberDTO) mbundle.getSerializable("subscriberDTO");
        if (subscriberDTO != null) {
            subType = subscriberDTO.getPayType();
        }

        if (mView == null) {
            mView = inflater.inflate(R.layout.layout_cus_changesim, container, false);
            unit(mView);
        }

        return mView;
    }

    @Override
    public void unit(View v) {
        edit_ngaykySub = (EditText) v.findViewById(R.id.edit_ngayky);
        lnsigndate = (LinearLayout) v.findViewById(R.id.lnsigndate);
        btnbar = (Button) v.findViewById(R.id.btnbar);
        lnotp = (LinearLayout) v.findViewById(R.id.lnotp);
        lnotpds = (LinearLayout) v.findViewById(R.id.lnotpds);

        // khai bao thong tin goi cuoc dac biet
        lnTTGoiCuocDacBiet = (LinearLayout) v.findViewById(R.id.lnGoiCuocDacBiet);
        viewSpec = (View) v.findViewById(R.id.viewSpec);
        viewSpec1 = (View) v.findViewById(R.id.viewSpec1);
        spDoiTuong = (Spinner) v.findViewById(R.id.spDoiTuong);
        edtQuocTich = (EditText) v.findViewById(R.id.edtQuocTich);
        tvDonVi = (EditText) v.findViewById(R.id.tvDonVi);
        edtMaGiayToDacBiet = (EditText) v.findViewById(R.id.edtMaGiayToDacBiet);
        edtNgayBD = (EditText) v.findViewById(R.id.edtNgayBD);
        edtNgayKT = (EditText) v.findViewById(R.id.edtNgayKT);
        rlchondonvi = (RelativeLayout) v.findViewById(R.id.rlchondonvi);
        imgDeleteDV = (LinearLayout) v.findViewById(R.id.imgDeleteDonvi);

        // khai bao thong tin dau noi goi dac biet hoc sinh
        lnGoiCuocDacBietHs = (LinearLayout) v.findViewById(R.id.lnGoiCuocDacBietHs);
        edttenhs = (EditText) v.findViewById(R.id.edttenhs);
        edtngaysinhhs = (EditText) v.findViewById(R.id.edtngaysinhhs);

        lnttinphikh = (LinearLayout) v.findViewById(R.id.lnttinphikh);
        lnttinphids = (LinearLayout) v.findViewById(R.id.lnttinphids);
        edtloaikh = (EditText) v.findViewById(R.id.edtloaikh);
        edit_sogiayto = (EditText) v.findViewById(R.id.edit_sogiayto);
        edit_tax = (EditText) v.findViewById(R.id.edit_tax);
        edit_tenKH = (EditText) v.findViewById(R.id.edit_tenKH);
        edit_ngaysinh = (EditText) v.findViewById(R.id.edit_ngaysinh);
        edit_ngaycap = (EditText) v.findViewById(R.id.edit_ngaycap);
        edit_noicap = (EditText) v.findViewById(R.id.edit_noicap);

        edit_note = (EditText) v.findViewById(R.id.edit_note);
        edtdiachilapdat = (EditText) v.findViewById(R.id.edtdiachilapdat);
        edit_serialsim = (EditText) v.findViewById(R.id.edit_serialsim);
        editotp = (EditText) v.findViewById(R.id.editotp);
        edt_capcha = (EditText) v.findViewById(R.id.edt_capcha);

        prbCustType = (ProgressBar) v.findViewById(R.id.prbCustType);
        prbotp = (ProgressBar) v.findViewById(R.id.prbotp);
        prbreasonds = (ProgressBar) v.findViewById(R.id.prbreasonds);
        prbreasonkh = (ProgressBar) v.findViewById(R.id.prbreasonkh);
        prbGiayto = (ProgressBar) v.findViewById(R.id.prbGiayto);
        btnRefreshCustType = (Button) v.findViewById(R.id.btnRefreshCustType);
        btnRefreshGiayto = (Button) v.findViewById(R.id.btnRefreshGiayto);
        btnkiemtra = (Button) v.findViewById(R.id.btnkiemtra);
        btnedit = (Button) v.findViewById(R.id.btnedit);
        btnkiemtraserial = (Button) v.findViewById(R.id.btnkiemtraserial);
        btnSendOTP = (Button) v.findViewById(R.id.btnSendOTP);
        btnChangeSim = (Button) v.findViewById(R.id.btnChangeSim);
        lnsogiayto = (LinearLayout) v.findViewById(R.id.lnsogiayto);
        lntax = (LinearLayout) v.findViewById(R.id.lntax);
        ln_sex = (LinearLayout) v.findViewById(R.id.ln_sex);
        lnngaycap = (LinearLayout) v.findViewById(R.id.lnngaycap);
        lnnoicap = (LinearLayout) v.findViewById(R.id.lnnoicap);
        ln_lydosuakh = (LinearLayout) v.findViewById(R.id.ln_lydosuakh);
        lnthongtindaidien = (LinearLayout) v.findViewById(R.id.lnthongtindaidien);
        lnhoso = (LinearLayout) v.findViewById(R.id.lnhoso);
        lncus = (LinearLayout) v.findViewById(R.id.lncus);
        lnchangesim = (LinearLayout) v.findViewById(R.id.lnchangesim);
        ln_lydodoisim = (LinearLayout) v.findViewById(R.id.ln_lydodoisim);
        lnquoctich = (LinearLayout) v.findViewById(R.id.lnquoctich);
        lnSelectProfile = (LinearLayout) v.findViewById(R.id.lnSelectProfile);
        spinner_type_giay_to = (Spinner) v.findViewById(R.id.spinner_type_giay_to);
        spinner_gioitinh = (Spinner) v.findViewById(R.id.spinner_gioitinh);
        spinner_reasonkh = (Spinner) v.findViewById(R.id.spinner_reasonkh);
        spnReasonChangeSim = (Spinner) v.findViewById(R.id.spinner_lydodoisim);
        spinner_quoctich = (Spinner) v.findViewById(R.id.spinner_quoctich);
        txtsogiayto = (TextView) v.findViewById(R.id.txtsogiayto);
        imgCapcha = (ImageView) v.findViewById(R.id.imgCapcha);
        horizontalScrollView = (HorizontalScrollView) v.findViewById(R.id.horizontalScrollView);
        expandedImageView = (ImageView) v.findViewById(R.id.expandedImageView);
        frlMain = (FrameLayout) v.findViewById(R.id.frlMain);
        thumbnails = (LinearLayout) v.findViewById(R.id.thumbnails);
        lnChinhChu = v.findViewById(R.id.lnChinhChu);

        lnotp.setVisibility(View.GONE);
        lnSelectProfile.setVisibility(View.GONE);
        lnTTGoiCuocDacBiet.setVisibility(View.GONE);
        imgDeleteDV.setVisibility(View.GONE);
        lnGoiCuocDacBietHs.setVisibility(View.GONE);
        lnthongtindaidien.setVisibility(View.GONE);
        lntax.setVisibility(View.GONE);
        btnedit.setVisibility(View.GONE);
        prbCustType.setVisibility(View.GONE);
        prbGiayto.setVisibility(View.GONE);
        prbreasonkh.setVisibility(View.GONE);
        prbreasonds.setVisibility(View.GONE);
        prbotp.setVisibility(View.GONE);

        lnttinphids.setOnClickListener(this);
        lnttinphikh.setOnClickListener(this);
        edtloaikh.setOnClickListener(this);
        edtdiachilapdat.setOnClickListener(this);
        btnSendOTP.setOnClickListener(this);
        btnkiemtraserial.setOnClickListener(this);
        btnkiemtra.setOnClickListener(this);
        btnRefreshGiayto.setOnClickListener(this);
        btnRefreshCustType.setOnClickListener(this);
        btnChangeSim.setOnClickListener(this);
        btnedit.setOnClickListener(this);
        edtNgayBD.setOnClickListener(this);
        edtNgayKT.setOnClickListener(this);

        edtQuocTich.setText(getString(R.string.viet_nam));
        edtQuocTich.setEnabled(false);
        edtNgayBD.setText(dateNowString);
        edtNgayKT.setText(dateNowString);
        edit_ngaykySub.setText(dateNowString);
        edit_ngaykySub.setOnClickListener(editTextListener);
        edit_ngaysinh.setText(dateNowString);
        edit_ngaysinh.setOnClickListener(editTextListener);
        edit_ngaycap.setText(dateNowString);
        edit_ngaycap.setOnClickListener(editTextListener);

        if (!CommonActivity.isNullOrEmpty(hotLineReponseDetail)
                && !CommonActivity.isNullOrEmpty(hotLineReponseDetail.getStrDateOfBirth())) {
            edtngaysinhhs.setText(hotLineReponseDetail.getStrDateOfBirth());
        }
        edtngaysinhhs.setOnClickListener(this);
        edtngaysinhhs.setText(dateNowString);

        serviceType = subscriberDTO.getTelecomServiceAlias();
        if (CommonActivity.isNullOrEmpty(serviceType)) {
            subscriberDTO.getServiceType();
        }
        if ("M".equals(serviceType)) {
            lnsigndate.setVisibility(View.VISIBLE);
        } else {
            lnsigndate.setVisibility(View.GONE);
        }

        btnbar.setOnClickListener(new OnClickListener() {

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
                            getActivity().getString(R.string.confirmdlapk),
                            getActivity().getString(R.string.app_name),
                            getActivity().getString(R.string.cancel),
                            getActivity().getString(R.string.ok), null,
                            click).show();
                }
            }
        });

        SharedPreferences preferences = getActivity().getSharedPreferences(
                Define.PRE_NAME, Activity.MODE_PRIVATE);
        String name = preferences.getString(Define.KEY_MENU_NAME, "");
        if (name.contains(";ds_no_otp;")) {
            lnotpds.setVisibility(View.GONE);
        } else {
            lnotpds.setVisibility(View.VISIBLE);
        }

		spnDoituong = (Spinner) v.findViewById(R.id.spnDoituong);
		lnDoituong = (LinearLayout) v.findViewById(R.id.lnDoituong);
        imgDeleteDV.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                tvDonVi.setText("");
                mCodeDonVi = "";
                imgDeleteDV.setVisibility(View.GONE);
            }
        });

        spDoiTuong.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                mCodeDoiTuong = arrSpecObjects.get(arg2).getCode();
                tvDonVi.setText("");
                mCodeDonVi = "";
                imgDeleteDV.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        tvDonVi.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                timKiemDonVi();
            }
        });

        imgCapcha.setOnClickListener(this);
        if (!CommonActivity.isNullOrEmptyArray(spinNation)) {
            spinNation = new ArrayList<Spin>();
        }
        if (!CommonActivity.isNullOrEmptyArray(arrSexBeans)) {
            arrSexBeans = new ArrayList<SexBeans>();
        }
        initSex();
        // lay danh sach loai khach hang , truogn hop ko chinh chu
        if (subscriberDTO != null && subscriberDTO.isMarkNotOwner()) {
            lnsigndate.setVisibility(View.VISIBLE);
            initNationly();
            GetMappingChannelCustTypeAsyn getMappingChannelCustTypeAsyn = new GetMappingChannelCustTypeAsyn(
                    getActivity(), prbCustType, btnRefreshCustType);
            getMappingChannelCustTypeAsyn.execute(subType);
            lncus.setVisibility(View.VISIBLE);

            FindProductOfferingByListCodeAsyn findProductOfferingByListCodeAsyn = new FindProductOfferingByListCodeAsyn(
                    getActivity());
            findProductOfferingByListCodeAsyn.execute(subscriberDTO
                    .getProductCode());
            lnChinhChu.setVisibility(View.GONE);
			lnDoituong.setVisibility(View.VISIBLE);
        } else {
            lnsigndate.setVisibility(View.GONE);
            lncus.setVisibility(View.GONE);
            lnChinhChu.setVisibility(View.GONE);
            lnChinhChu.setOnClickListener(this);
            // truong hop chinh chu
            GetReasonFullPM getReasonFullPMChangeSim = new GetReasonFullPM(getActivity(), prbreasonkh, "11");
            getReasonFullPMChangeSim.execute();
        }
        initCap();

        lnSelectProfile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!validateChangeSim(true)) {
                        return;
                    }
                } catch (Exception e) {
                    Log.e("validateChangeSim" + "" + "-+", e.getMessage());
                }

                if (!CommonActivity.isNullOrEmpty(profileBO) && !profileBO.getMapListRecordPrepaid().isEmpty()) {

                    subscriberDTO.setRequestType("CHANGESIM");
                    subscriberDTO.setNewSerialChangeSim(edit_serialsim.getText().toString().trim());

                    profileInfoDialogFragment = new ProfileInfoDialogFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("profileBO", profileBO);
                    bundle.putString("xmlSub", getXmlSubSubmit());
                    bundle.putString("xmlCus", getXmlCusSubmit());

                    // neu khong chinh chu hoac KHDN thi thuc hien theo luong cu
//                    if (subscriberDTO.isMarkNotOwner() ||
//                            "2".equals(subscriberDTO.getCustomerDTOInput().getCustTypeDTO().getGroupType())) {

                    if (!CommonActivity.isNullOrEmpty(subscriberDTO)
                            && !CommonActivity.isNullOrEmpty(subscriberDTO.getCustomerDTOInput())
                            && !CommonActivity.isNullOrEmpty(subscriberDTO.getCustomerDTOInput().getCustTypeDTO())
                            && !CommonActivity.isNullOrEmpty(subscriberDTO.getCustomerDTOInput().getCustTypeDTO().getGroupType())
                            && "2".equals(subscriberDTO.getCustomerDTOInput().getCustTypeDTO().getGroupType())) {
                        bundle.putString("groupType", "2");
                    }

                    profileInfoDialogFragment.setOnFinishDSFListener(onFinishDSFListener);
                    profileInfoDialogFragment.setArguments(bundle);
                    profileInfoDialogFragment.setTargetFragment(FragmentChangeSim.this,
                            RequestCodeFragment.FRAG_MENT_CHANGE_SIM);
                    profileInfoDialogFragment.show(getFragmentManager(), "change sim");
                }
            }
        });
        
        spnReasonChangeSim.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                if (position == 0) {
                    return;
                }
                List<Object> lstReasonId = new ArrayList<>();
                List<Object> lstActionCode = new ArrayList<>();

                if (subscriberDTO.isMarkNotOwner()) {
                    // Truong hop khong chinh chu
                    ReasonDTO reasonKh = (ReasonDTO) spinner_reasonkh.getSelectedItem();
                    if (reasonKh != null && !CommonActivity.isNullOrEmpty(reasonKh.getReasonId())) {
                        lstReasonId.add(reasonKh.getReasonId());
                        lstActionCode.add("1015");
                    } else {
                        CommonActivity.createAlertDialog(
                                        getActivity(),
                                        getActivity()
                                                .getString(
                                                        R.string.validate_reason_changecus),
                                        getActivity().getString(
                                                R.string.app_name)).show();
                        return;
                    }
                }
                ReasonDTO reasonDS = (ReasonDTO) spnReasonChangeSim.getSelectedItem();
                lstReasonId.add(reasonDS.getReasonId());
                lstActionCode.add("11");

                String parValue = "";
                if ("1".equals(subscriberDTO.getPayType())) {
                    if (CommonActivity.isNullOrEmpty(subscriberDTO.getSubType())) {
                        parValue = "1";
                    } else {
                        parValue = subscriberDTO.getSubType();
                    }
                } else if ("2".equals(subscriberDTO.getPayType())) {
                    parValue = subscriberDTO.getProductCode();
                }

                String cusType = "";
                String idNo = "";
                if (subscriberDTO.isMarkNotOwner()) {
                    idNo = edit_sogiayto.getText().toString().trim();
                    if (!CommonActivity.isNullOrEmpty(customerDTO)
                            && !CommonActivity.isNullOrEmpty(customerDTO.getCustId())) {
                        cusType = customerDTO.getCustType();
                    } else {
                        cusType = custTypeDTO.getCustType();
                    }
                } else {
                    cusType = subscriberDTO.getCustomerDTOInput().getCustType();
                    idNo = subscriberDTO.getCustomerDTOInput().getIdentityNo();
                }

                horizontalScrollView.setVisibility(View.GONE);
                profileBO = new ProfileBO();
                profileBO.clearData();
                profileBO.setSigImageFullPath(null);
                profileBO.setLstActionCode(lstActionCode);
                profileBO.setLstReasonId(lstReasonId);
                profileBO.setParValue(parValue);
                profileBO.setPayType(subType);
                profileBO.setCustType(cusType);
                profileBO.setServiceType("M");
                profileBO.setIdNo(idNo);

                new AsynTaskGetListRecordProfile(getActivity(), new OnPostExecuteListener<Map<String, ArrayList<RecordPrepaid>>>() {
                    @Override
                    public void onPostExecute(Map<String, ArrayList<RecordPrepaid>> result, String errorCode, String description) {
                        if (CommonActivity.isNullOrEmpty(result)) {
                            profileBO.setRequiredUploadImage(false);
                            lnSelectProfile.setVisibility(View.GONE);
                        } else {
                            lnSelectProfile.setVisibility(View.VISIBLE);
                            profileBO.setMapListRecordPrepaid(result);
                        }
                    }
                }, null, profileBO).execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        spinner_reasonkh.setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                        ReasonDTO reasonDS = (ReasonDTO) spnReasonChangeSim.getSelectedItem();
                        if (reasonDS != null && !CommonActivity.isNullOrEmpty(reasonDS.getReasonId())) {
                            ReasonDTO reasonKh = (ReasonDTO) spinner_reasonkh.getSelectedItem();
                            List<Object> lstReasonId = new ArrayList<>();
                            List<Object> lstActionCode = new ArrayList<>();
                            lstReasonId.add(reasonKh.getReasonId());
                            lstActionCode.add("1015");

                            String parValue = "";
                            if ("1".equals(subscriberDTO.getPayType())) {
                                if (CommonActivity.isNullOrEmpty(subscriberDTO.getSubType())) {
                                    parValue = "1";
                                } else {
                                    parValue = subscriberDTO.getSubType();
                                }
                            } else if ("2".equals(subscriberDTO.getPayType())) {
                                parValue = subscriberDTO.getProductCode();
                            }

                            String cusType = "";
                            String idNo = "";
                            if (subscriberDTO.isMarkNotOwner()) {
                                idNo = edit_sogiayto.getText().toString().trim();
                                if (!CommonActivity.isNullOrEmpty(customerDTO)
                                        && !CommonActivity.isNullOrEmpty(customerDTO.getCustId())) {
                                    cusType = customerDTO.getCustType();
                                } else {
                                    cusType = custTypeDTO.getCustType();
                                }
                            } else {
                                cusType = subscriberDTO.getCustomerDTOInput().getCustType();
                                idNo = subscriberDTO.getCustomerDTOInput().getIdentityNo();
                            }

                            horizontalScrollView.setVisibility(View.GONE);
                            profileBO = new ProfileBO();
                            profileBO.clearData();
                            profileBO.setSigImageFullPath(null);
                            profileBO.setLstActionCode(lstActionCode);
                            profileBO.setLstReasonId(lstReasonId);
                            profileBO.setParValue(parValue);
                            profileBO.setPayType(subType);
                            profileBO.setCustType(cusType);
                            profileBO.setServiceType("M");
                            profileBO.setIdNo(idNo);

                            new AsynTaskGetListRecordProfile(getActivity(), new OnPostExecuteListener<Map<String, ArrayList<RecordPrepaid>>>() {
                                @Override
                                public void onPostExecute(Map<String, ArrayList<RecordPrepaid>> result, String errorCode, String description) {
                                    if (CommonActivity.isNullOrEmpty(result)) {
                                        profileBO.setRequiredUploadImage(false);
                                        lnSelectProfile.setVisibility(View.GONE);
                                    } else {
                                        lnSelectProfile.setVisibility(View.VISIBLE);
                                        profileBO.setMapListRecordPrepaid(result);
                                    }
                                }
                            }, null, profileBO).execute();
                        } else {
                            return;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {

                    }
                });


		spnDoituong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				SpinV2 itemAtPosition = (SpinV2) parent.getItemAtPosition(position);
				if(!itemAtPosition.getValue().equals(act.getString(R.string.chondoituong))) {
					subObject = itemAtPosition.getId();
				}else{
					subObject = null;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
    }

    private void updateSuggestion(AdapterView<?> arg0, int position) {
        ReasonDTO reasonDTO = (ReasonDTO) arg0.getItemAtPosition(position);

        AutoCompleteUtil.getInstance(getActivity()).addToSuggestionList(AUTO_CHANGE_SIM, reasonDTO.getReasonCode() + reasonDTO.getName());
        System.out.println("12345 reasonDTO.getName() " + reasonDTO.getName());

        AutoCompleteUtil.getInstance(getActivity()).sortReasonChangeSimBySelectedCount(AUTO_CHANGE_SIM, arrReasonChangeSim);

    }

    private void initNationly() {
        try {
            BhldDAL dal = new BhldDAL(getActivity());
            dal.open();
            spinNation = dal.getNationaly();
            dal.close();
            Utils.setDataSpinner(getActivity(), spinNation, spinner_quoctich);

            if (!CommonActivity.isNullOrEmptyArray(spinNation)) {

                for (Spin spin : spinNation) {
                    if ("VN".equals(spin.getId())) {
                        spinner_quoctich.setSelection(spinNation.indexOf(spin));
                        break;
                    }
                }
            }

        } catch (Exception e) {
            Log.d("initNationly", e.toString());
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleActionBar(R.string.txt_change_sim);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {

                case 104:
                    if (data != null) {
                        custTypeDTO = (CustTypeDTO) data.getExtras().getSerializable("cusTypeDTOsKey");

                        if (custTypeDTO != null && !CommonActivity.isNullOrEmpty(custTypeDTO.getCustType())) {
                            if ("2".equals(custTypeDTO.getGroupType())) {
                                lnsogiayto.setVisibility(View.GONE);
                                txtsogiayto.setText(getActivity().getString(R.string.soGPKD));
                                lntax.setVisibility(View.VISIBLE);
                                ln_sex.setVisibility(View.GONE);
                                lnquoctich.setVisibility(View.GONE);
                                lnngaycap.setVisibility(View.VISIBLE);
                                lnnoicap.setVisibility(View.VISIBLE);
                                lnsogiayto.setVisibility(View.GONE);
                            } else {
                                lnsogiayto.setVisibility(View.VISIBLE);
                                txtsogiayto.setText(Html.fromHtml(getActivity().getString(R.string.sogiaytoCD)));
                                lntax.setVisibility(View.GONE);
                                ln_sex.setVisibility(View.VISIBLE);
                                lnquoctich.setVisibility(View.VISIBLE);
                                lnngaycap.setVisibility(View.VISIBLE);
                                lnnoicap.setVisibility(View.VISIBLE);
                            }

                            edtloaikh.setText(custTypeDTO.getName());
                            // lay danh sach loại giay to theo loai khach hang
                            GetMappingCustIdentityUsageAsyn getMappingCustIdentityUsageAsyn = new GetMappingCustIdentityUsageAsyn(
                                    getActivity(), prbGiayto, btnRefreshGiayto, spinner_type_giay_to);
                            getMappingCustIdentityUsageAsyn.execute(custTypeDTO.getCustType());

                            GetReasonFullPM getReasonFullPM = new GetReasonFullPM(getActivity(), prbreasonkh, "1015");
                            getReasonFullPM.execute();

                            GetReasonFullPM getReasonFullPMChangeSim = new GetReasonFullPM(getActivity(), prbreasonkh, "11");
                            getReasonFullPMChangeSim.execute();

                            //lay doi tuong
                            if(!CommonActivity.isNullOrEmpty(custTypeDTO.getGroupType())) {
                                String groupType = custTypeDTO.getGroupType();

                                String code = "";
                                if ("1".equals(groupType) || "3".equals(groupType)) {
                                    code = "INDIVIDUAL";
                                } else if ("2".equals(groupType)) {
                                    code = "BUSINESS";
                                }
                                AsyncGetOptionSetValueV2 asyncGetOptionSetValue = new AsyncGetOptionSetValueV2(act, new OnPostGetInfoCustomer(), moveLogInAct);
                                asyncGetOptionSetValue.execute(code);
                            }
                        } else {
                            edtloaikh.setText("");
                        }
                    }
                    break;
                case 11111:
                    areaProvicialPR = (AreaObj) data.getExtras().getSerializable("areaProvicial");
                    areaDistristPR = (AreaObj) data.getExtras().getSerializable("areaDistrist");

                    areaPrecintPR = (AreaObj) data.getExtras().getSerializable("areaPrecint");
                    areaGroupPR = (AreaObj) data.getExtras().getSerializable("areaGroup");

                    areaFlowPR = data.getExtras().getString("areaFlow");
                    areaHomeNumberPR = data.getExtras().getString("areaHomeNumber");

                    addressPR = new StringBuilder();

                    if (areaHomeNumberPR != null && areaHomeNumberPR.length() > 0) {
                        addressPR.append(areaHomeNumberPR + " ");
                    }
                    if (areaFlowPR != null && areaFlowPR.length() > 0) {
                        addressPR.append(areaFlowPR + " ");
                    }
                    if (areaGroupPR != null && areaGroupPR.getName() != null && areaGroupPR.getName().length() > 0) {
                        addressPR.append(areaGroupPR.getName() + " ");
                        to = areaGroupPR.getAreaCode();
                    } else {
                        to = "";
                    }
                    if (areaPrecintPR != null && areaPrecintPR.getName() != null && areaPrecintPR.getName().length() > 0) {

                        addressPR.append(areaPrecintPR.getName() + " ");
                        precinct = areaPrecintPR.getPrecinct();
                    } else {
                        precinct = "";
                    }
                    if (areaDistristPR != null && areaDistristPR.getName() != null
                            && areaDistristPR.getName().length() > 0) {

                        addressPR.append(areaDistristPR.getName() + " ");
                        district = areaDistristPR.getDistrict();
                    } else {
                        district = "";
                    }
                    if (areaProvicialPR != null && areaProvicialPR.getName() != null
                            && areaProvicialPR.getName().length() > 0) {

                        addressPR.append(areaProvicialPR.getName());
                        province = areaProvicialPR.getProvince();
                    } else {
                        province = "";
                    }
                    edtdiachilapdat.setText(addressPR);
                    break;

                case CHANNEL_UPDATE_IMAGE.PICK_IMAGE:
                    profileInfoDialogFragment.onActivityResult(requestCode, resultCode, data);
                    break;

                case 5555:
                    String contents = data.getStringExtra("SCAN_RESULT");
                    edit_serialsim.setText(contents);
                    edit_serialsim.requestFocus();

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

                case 1513:
                    CustTypeDTO custTypeDTO = (CustTypeDTO) data.getExtras().getSerializable("cusTypeDTOsKey");
                    if (!CommonActivity.isNullOrEmpty(custTypeDTO)) {
                        custypeKey = custTypeDTO.getCustType();
                    } else {
                        custypeKey = "";
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btnkiemtra:
                if (!CommonActivity.isNullOrEmpty(edit_sogiayto.getText().toString().trim())) {
                    // check han muc
                    CheckQuotaSubAsyn checkResourceAsyn = new CheckQuotaSubAsyn(getActivity());
                    checkResourceAsyn.execute();
                } else {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.idnoemptycsim),
                            getActivity().getString(R.string.app_name)).show();
                }
                break;
            case R.id.btnedit:
                enableCus();
                break;
            case R.id.edtdiachilapdat:
                String strProvince1 = Session.province;
                String strDistris1 = Session.district;
                Bundle mBundle1 = new Bundle();
                mBundle1.putString("strProvince", strProvince1);
                mBundle1.putString("strDistris", strDistris1);
                mBundle1.putString("KEYPR", "1111");
                Intent i1 = new Intent(getActivity(), CreateAddressCommon.class);
                i1.putExtras(mBundle1);
                startActivityForResult(i1, 11111);

                break;
            case R.id.btnRefreshCustType:
                arrCusTypeDTO = new ArrayList<CustTypeDTO>();
                new CacheDatabaseManager(getActivity()).insertCusType("cusTypeDTOPre", null);
                new CacheDatabaseManager(getActivity()).insertCusType("cusTypeDTOPos", null);
                GetMappingChannelCustTypeAsyn getMappingChannelCustTypeAsyn = new GetMappingChannelCustTypeAsyn(
                        getActivity(), prbCustType, btnRefreshCustType);
                getMappingChannelCustTypeAsyn.execute(subType);
                break;
            case R.id.btnRefreshGiayto:
                if (custTypeDTO != null) {
                    GetMappingCustIdentityUsageAsyn getMappingCustIdentityUsageAsyn = new GetMappingCustIdentityUsageAsyn(
                            getActivity(), prbGiayto, btnRefreshGiayto, spinner_type_giay_to);
                    getMappingCustIdentityUsageAsyn.execute(custTypeDTO.getCustType());
                }
                break;

            case R.id.edtloaikh:
                Intent intent = new Intent(getActivity(), FragmentSearchCusTypeMobile.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("GROUPKEY", "GROUPKEY");
                intent.putExtras(mBundle);
                startActivityForResult(intent, 104);
                break;
            case R.id.btnSendOTP:
                OnClickListener confirm = new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        SendOTPUtilAsyn sendOTPUtilAsyn = new SendOTPUtilAsyn(getActivity());
                        sendOTPUtilAsyn.execute(subscriberDTO.getIsdn());
                    }
                };
                CommonActivity
                        .createDialog(getActivity(), getActivity().getString(R.string.sendotpds, subscriberDTO.getIsdn()),
                                getActivity().getString(R.string.app_name), getActivity().getString(R.string.cancel),
                                getActivity().getString(R.string.OK), null, confirm)
                        .show();
                break;
            case R.id.btnChangeSim:
                try {
                    if (CommonActivity.isNetworkConnected(getActivity())) {
                        if (validateChangeSim(false)) {
                            CommonActivity.createDialog(getActivity(),
                                    getActivity().getResources().getString(R.string.confirmchangesim),
                                    getActivity().getResources().getString(R.string.app_name),
                                    getActivity().getResources().getString(R.string.cancel),
                                    getActivity().getResources().getString(R.string.buttonOk),
                                    null, onclickChangeSim).show();
                        }
                    } else {
                        CommonActivity.createAlertDialog(getActivity(),
                                getActivity().getResources().getString(R.string.errorNetwork),
                                getActivity().getResources().getString(R.string.app_name)).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.lnttinphikh:
                ReasonDTO reasonKh = (ReasonDTO) spinner_reasonkh.getSelectedItem();
                if (reasonKh != null && !CommonActivity.isNullOrEmpty(reasonKh.getReasonId())) {
                    FindFeeByReasonTeleIdAsyn findFeeByReasonTeleIdAsyn = new FindFeeByReasonTeleIdAsyn(getActivity());
                    findFeeByReasonTeleIdAsyn.execute(subscriberDTO.getTelecomServiceId() + "", reasonKh.getReasonId(),
                            subscriberDTO.getProductCode());
                } else {
                    CommonActivity
                            .createAlertDialog(getActivity(), getActivity().getString(R.string.validate_reason_changecus),
                                    getActivity().getString(R.string.app_name))
                            .show();
                }
                break;
            case R.id.lnttinphids:
                ReasonDTO reasonDS = (ReasonDTO) spnReasonChangeSim.getSelectedItem();
                if (reasonDS != null && !CommonActivity.isNullOrEmpty(reasonDS.getReasonId())) {
                    FindFeeByReasonTeleIdAsyn findFeeByReasonTeleIdAsyn = new FindFeeByReasonTeleIdAsyn(getActivity());
                    findFeeByReasonTeleIdAsyn.execute(subscriberDTO.getTelecomServiceId() + "", reasonDS.getReasonId(),
                            subscriberDTO.getProductCode());
                } else {
                    CommonActivity
                            .createAlertDialog(getActivity(), getActivity().getString(R.string.validate_reason_changesim),
                                    getActivity().getString(R.string.app_name))
                            .show();
                }
                break;
            case R.id.edtNgayBD:
                showDatePickerDialog(edtNgayBD);
                break;
            case R.id.edtNgayKT:
                showDatePickerDialog(edtNgayKT);
                break;
            case R.id.edtngaysinhhs:
                showDatePickerDialog(edtngaysinhhs);
                break;
            case R.id.btnkiemtraserial:
                if (validateSerial()) {
                    CheckResourceAsyn checkResourceAsyn = new CheckResourceAsyn(getActivity());
                    checkResourceAsyn.execute();
                }
                break;
            case R.id.imgCapcha:
                initCap();
                break;
            case R.id.lnChinhChu:
                if (!CommonActivity.isNullOrEmpty(arrSubscriberDTO)) {
                    showSelectIsdnOwner();
                } else {
                    FindVerifiedOwnerByListIdNoAsyn findVerifiedOwnerByListIdNoAsyn = new FindVerifiedOwnerByListIdNoAsyn(
                            getActivity());
                    findVerifiedOwnerByListIdNoAsyn.execute();
                }
                break;
            default:
                break;
        }

    }

    private boolean validateSerial() {
        ReasonDTO reasonChangeSim = (ReasonDTO) spnReasonChangeSim.getSelectedItem();
        if (reasonChangeSim == null) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validate_reason_changesim),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }
        if (CommonActivity.isNullOrEmpty(reasonChangeSim.getReasonId())) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validate_reason_changesim),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }
        if (CommonActivity.isNullOrEmpty(edit_serialsim.getText().toString().trim())) {
            CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validateserial),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }
        return true;
    }

    // validate thong tin doi sim
    // isHSDT = true - validate truoc khi chon HS
    // isHDST = fale - validate sau khi chon HS
    private boolean validateChangeSim(boolean isHSDT) throws ParseException {

        SharedPreferences preferences = getActivity().getSharedPreferences(
                Define.PRE_NAME, Activity.MODE_PRIVATE);
        String name = preferences.getString(Define.KEY_MENU_NAME, "");

        ReasonDTO reasonChangeSim = (ReasonDTO) spnReasonChangeSim.getSelectedItem();
        if (reasonChangeSim == null) {
            CommonActivity.createAlertDialog(getActivity(),
                    getActivity().getString(R.string.validate_reason_changesim),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }
        if (CommonActivity.isNullOrEmpty(reasonChangeSim.getReasonId())) {
            CommonActivity.createAlertDialog(getActivity(),
                    getActivity().getString(R.string.validate_reason_changesim),
                    getActivity().getString(R.string.app_name)).show();
            return false;
        }

        if (!isHSDT) {
            if (CommonActivity.isNullOrEmpty(profileInfoDialogFragment)
                    || !profileInfoDialogFragment.isFullFile()) {
                CommonActivity.createAlertDialog(getActivity(),
                        getActivity().getString(R.string.requireProfile),
                        getActivity().getString(R.string.app_name)).show();
                return false;
            }
        }

        if (subscriberDTO.isMarkNotOwner()) {

            if (CommonActivity.isNullOrEmpty(subObject)) {
                CommonActivity.createAlertDialog(getActivity(),
                        getResources().getString(R.string.doituong_not_select), getString(R.string.app_name)).show();
                return false;
            }

            if (!CommonActivity.isNullOrEmpty(customerDTO) && !CommonActivity.isNullOrEmpty(customerDTO.getCustId())) {
                // truong hop kh cu
                // validate truong hop khong chinh chu validate phan thong tin doi sim
                if (!CommonActivity.isNullOrEmpty(customerDTO)
                        && !CommonActivity.isNullOrEmpty(customerDTO)
                        && !CommonActivity.isNullOrEmpty(customerDTO.getCustId())
                        && !CommonActivity.isNullOrEmpty(customerDTO.getCustTypeDTO().getPlan())
                        && !"1".equals(customerDTO.getCustTypeDTO().getPlan())) {
                    if (CommonActivity.isNullOrEmpty(custypeKey)) {
                        CommonActivity.createAlertDialog(getActivity(),
                                getActivity().getString(R.string.updatecustype),
                                getActivity().getString(R.string.app_name),
                                onclickChangeCustype).show();
                        return false;
                    }
                }

                ReasonDTO reasonDTO = (ReasonDTO) spinner_reasonkh.getSelectedItem();
                if (reasonDTO == null) {
                    CommonActivity.createAlertDialog(getActivity(),
                            getActivity().getString(R.string.validate_reason_changecus),
                            getActivity().getString(R.string.app_name)).show();
                    return false;
                }

                if (CommonActivity.isNullOrEmpty(reasonDTO.getReasonId())) {
                    CommonActivity.createAlertDialog(getActivity(),
                            getActivity().getString(R.string.validate_reason_changecus),
                            getActivity().getString(R.string.app_name)).show();
                    return false;
                }

                Log.d("checkIsSpeccc", checkIsSpec);
                if ("IS_SPECIAL_PRODUCT".equals(checkIsSpec)) {
                    if (CommonActivity.isNullOrEmpty(tvDonVi.getText()
                            .toString().trim())) {
                        Toast.makeText(getActivity(),
                                getString(R.string.chekdonvi),
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    if (CommonActivity.isNullOrEmpty(mCodeDonVi)) {
                        Toast.makeText(getActivity(),
                                getString(R.string.chekdonvi),
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    if (CommonActivity.isNullOrEmpty(edtMaGiayToDacBiet
                            .getText().toString().trim())) {
                        Toast.makeText(getActivity(),
                                getString(R.string.checkidnospec),
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    if (CommonActivity.isNullOrEmpty(edtNgayBD.getText()
                            .toString())) {
                        Toast.makeText(getActivity(),
                                getString(R.string.chekngayBD),
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    if (dateBD.after(dateNow)) {
                        Toast.makeText(getActivity(),
                                getString(R.string.chekngayBD1),
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    if (dateEnd.before(dateBD)) {
                        Toast.makeText(getActivity(),
                                getString(R.string.starttime_small_endtime),
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }

                if ("SPEC_HISCL".equals(checkIsSpec)) {
                    if (CommonActivity.isNullOrEmpty(edttenhs.getText()
                            .toString())) {
                        Toast.makeText(getActivity(),
                                getString(R.string.tenhsempty),
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    if (edttenhs.getText().toString().trim().length() < 4
                            || edttenhs.getText().toString().trim().length() > 120) {
                        Toast.makeText(getActivity(),
                                getString(R.string.tenhs4), Toast.LENGTH_SHORT)
                                .show();
                        return false;
                    }
                    if (birthDateHs.after(dateNow)) {
                        Toast.makeText(getActivity(),
                                getString(R.string.ngaysinhnhohonngayhientai),
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }

                if (CommonActivity.isNullOrEmpty(edit_serialsim.getText().toString().trim())) {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validateserial),
                            getActivity().getString(R.string.app_name)).show();
                    return false;
                }

                if (!isHSDT && !name.contains(";ds_no_otp;")) {
                    if (CommonActivity.isNullOrEmpty(editotp.getText().toString().trim())) {
                        CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validateotp),
                                getActivity().getString(R.string.app_name)).show();
                        return false;
                    }
                }

                String codeCapcha = edt_capcha.getText().toString().trim();
                if (!isHSDT && CommonActivity.isNullOrEmpty(codeCapcha)) {
                    CommonActivity.createAlertDialog(getActivity(),
                            getResources().getString(R.string.please_input_capcha), getString(R.string.app_name))
                            .show();
                    return false;
                }
                if (!isHSDT && !cap.checkAnswer(codeCapcha)) {
                    CommonActivity.createAlertDialog(getActivity(),
                            getResources().getString(R.string.error_input_capcha), getString(R.string.app_name)).show();
                    return false;
                }
                if ("M".equals(serviceType)) {
                    try {
                        Date dateSign = sdf.parse(edit_ngaykySub.getText().toString());
                        if (dateSign.after(dateNow)) {
                            Toast.makeText(getActivity(), getString(R.string.signDateEmptyThan), Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    } catch (Exception e) {
                        Log.d("Exception", e.toString());
                    }
                }
            } else {
                if (custTypeDTO == null) {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validatekh),
                            getActivity().getString(R.string.app_name)).show();
                    return false;
                }
                if (CommonActivity.isNullOrEmpty(custTypeDTO.getCustType())) {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validatekh),
                            getActivity().getString(R.string.app_name)).show();
                    return false;
                }
                CustIdentityDTO spinTypePayper = (CustIdentityDTO) spinner_type_giay_to.getSelectedItem();
                if (spinTypePayper == null) {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.typepaperemtpty),
                            getActivity().getString(R.string.app_name)).show();
                    return false;
                }
                if (CommonActivity.isNullOrEmpty(spinTypePayper.getIdType())) {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.typepaperemtpty),
                            getActivity().getString(R.string.app_name)).show();
                    return false;
                }

                if (CommonActivity.isNullOrEmpty(edit_tenKH.getText().toString())) {

                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checknameKH),
                            getActivity().getString(R.string.app_name)).show();
                    edit_tenKH.requestFocus();

                    return false;
                }
                if (StringUtils.CheckCharSpecical(edit_tenKH.getText().toString())) {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkcharspecical),
                            getActivity().getString(R.string.app_name)).show();
                    edit_tenKH.requestFocus();
                    return false;
                }
                if (edit_tenKH.getText().toString().length() < 4) {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.namecust),
                            getActivity().getString(R.string.app_name)).show();
                    return false;
                }
                if (CommonActivity.isNullOrEmpty(edit_ngaysinh.getText().toString())) {
                    CommonActivity.createAlertDialog(getActivity(),
                            getActivity().getString(R.string.message_pleass_input_birth_day),
                            getActivity().getString(R.string.app_name)).show();
                    return false;
                }
                Date birthDate = sdf.parse(edit_ngaysinh.getText().toString().trim());
                if (birthDate.after(dateNow)) {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.nsnhohonhtai),
                            getActivity().getString(R.string.app_name)).show();
                    return false;
                }

                // truong hop khach hang doanh nghiep
                if ("2".equals(custTypeDTO.getGroupType())) {
                    if (CommonActivity.isNullOrEmpty(edit_sogiayto.getText().toString())) {
                        CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checksogpkd),
                                getActivity().getString(R.string.app_name)).show();
                        edit_sogiayto.requestFocus();
                        return false;
                    }
                    if (CommonActivity.isNullOrEmpty(edit_tax.getText().toString())) {
                        CommonActivity.createAlertDialog(getActivity(),
                                getActivity().getString(R.string.message_pleass_input_taxcode),
                                getActivity().getString(R.string.app_name)).show();
                        edit_tax.requestFocus();
                        return false;
                    }
                } else {
                    if (CommonActivity.isNullOrEmpty(edit_sogiayto.getText().toString())) {
                        CommonActivity
                                .createAlertDialog(getActivity(), getActivity().getString(R.string.chua_nhap_giay_to),
                                        getActivity().getString(R.string.app_name))
                                .show();
                        return false;
                    }
                    CustIdentityDTO spinTypePaper = (CustIdentityDTO) spinner_type_giay_to.getSelectedItem();
                    if ("ID".equals(spinTypePaper.getIdType())) {
                        if (edit_sogiayto.getText().toString().length() != 9
                                && edit_sogiayto.getText().toString().length() != 12) {
                            CommonActivity
                                    .createAlertDialog(getActivity(), getActivity().getString(R.string.checksoidno),
                                            getActivity().getString(R.string.app_name))
                                    .show();
                            edit_sogiayto.requestFocus();
                            return false;
                        }
                        if (!DateTimeUtils.compareAge(birthDate, 14)) {
                            CommonActivity
                                    .createAlertDialog(getActivity(), getActivity().getString(R.string.validatebirh),
                                            getActivity().getString(R.string.app_name))
                                    .show();
                            edit_sogiayto.requestFocus();
                            return false;

                        }
                    }
                    if (CommonActivity.isNullOrEmpty(edit_ngaycap.getText().toString())) {
                        CommonActivity
                                .createAlertDialog(getActivity(), getActivity().getString(R.string.checkissueempty),
                                        getActivity().getString(R.string.app_name))
                                .show();
                        return false;
                    }

                    Date datengaycap = sdf.parse(edit_ngaycap.getText().toString().trim());

                    if (datengaycap.after(dateNow)) {
                        CommonActivity.createAlertDialog(getActivity(),
                                getActivity().getString(R.string.ngaycapnhohonngayhientai),
                                getActivity().getString(R.string.app_name)).show();
                        return false;
                    }

                    if (datengaycap.before(birthDate)) {
                        CommonActivity
                                .createAlertDialog(getActivity(), getActivity().getString(R.string.checkcmtngaycap),
                                        getActivity().getString(R.string.app_name))
                                .show();
                        return false;
                    }

                    if ("ID".equals(spinTypePaper.getIdType())) {
                        if (CommonActivity.getYear(birthDate, datengaycap) <= 14) {
                            CommonActivity.createAlertDialog(getActivity(),
                                    getActivity().getString(R.string.checkcmtngaycap14),
                                    getActivity().getString(R.string.app_name)).show();
                            return false;
                        }
                    }

                    if ("MID".equals(spinTypePaper.getIdType())) {
                        if (edit_sogiayto.getText().toString().trim().length() < 6
                                || edit_sogiayto.getText().toString().trim().length() > 12) {
                            CommonActivity
                                    .createAlertDialog(getActivity(), getActivity().getString(R.string.checkcmtca),
                                            getActivity().getString(R.string.app_name))
                                    .show();
                            return false;
                        }
                    }

                    if (CommonActivity.isNullOrEmpty(edit_noicap.getText().toString().trim())) {
                        CommonActivity
                                .createAlertDialog(getActivity(), getActivity().getString(R.string.chua_nhap_noi_cap),
                                        getActivity().getString(R.string.app_name))
                                .show();
                        edit_noicap.requestFocus();
                        return false;
                    }
                }

                if (CommonActivity.isNullOrEmpty(province) && CommonActivity.isNullOrEmpty(district)
                        && CommonActivity.isNullOrEmpty(precinct) && CommonActivity.isNullOrEmpty(to)) {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.addressempry),
                            getActivity().getString(R.string.app_name)).show();
                    return false;
                }

                ReasonDTO reasonDTO = (ReasonDTO) spinner_reasonkh.getSelectedItem();
                if (reasonDTO == null) {
                    CommonActivity.createAlertDialog(getActivity(),
                            getActivity().getString(R.string.validate_reason_changecus),
                            getActivity().getString(R.string.app_name)).show();
                    return false;
                }
                if (CommonActivity.isNullOrEmpty(reasonDTO.getReasonId())) {
                    CommonActivity.createAlertDialog(getActivity(),
                            getActivity().getString(R.string.validate_reason_changecus),
                            getActivity().getString(R.string.app_name)).show();
                    return false;
                }

                Log.d("checkIsSpeccc", checkIsSpec);
                if ("IS_SPECIAL_PRODUCT".equals(checkIsSpec)) {
                    if (CommonActivity.isNullOrEmpty(tvDonVi.getText()
                            .toString().trim())) {
                        Toast.makeText(getActivity(),
                                getString(R.string.chekdonvi),
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    if (CommonActivity.isNullOrEmpty(mCodeDonVi)) {
                        Toast.makeText(getActivity(),
                                getString(R.string.chekdonvi),
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    if (CommonActivity.isNullOrEmpty(edtMaGiayToDacBiet
                            .getText().toString().trim())) {
                        Toast.makeText(getActivity(),
                                getString(R.string.checkidnospec),
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    if (CommonActivity.isNullOrEmpty(edtNgayBD.getText()
                            .toString())) {
                        Toast.makeText(getActivity(),
                                getString(R.string.chekngayBD),
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    if (dateBD.after(dateNow)) {
                        Toast.makeText(getActivity(),
                                getString(R.string.chekngayBD1),
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    if (dateEnd.before(dateBD)) {
                        Toast.makeText(getActivity(),
                                getString(R.string.starttime_small_endtime),
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }

                if ("SPEC_HISCL".equals(checkIsSpec)) {
                    if (CommonActivity.isNullOrEmpty(edttenhs.getText()
                            .toString())) {
                        Toast.makeText(getActivity(),
                                getString(R.string.tenhsempty),
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    if (edttenhs.getText().toString().trim().length() < 4
                            || edttenhs.getText().toString().trim().length() > 120) {
                        Toast.makeText(getActivity(),
                                getString(R.string.tenhs4), Toast.LENGTH_SHORT)
                                .show();
                        return false;
                    }
                    if (birthDateHs.after(dateNow)) {
                        Toast.makeText(getActivity(),
                                getString(R.string.ngaysinhnhohonngayhientai),
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }

                // validate truong hop chinh chu validate phan thong tin doi sim
                if (CommonActivity.isNullOrEmpty(edit_serialsim.getText().toString().trim())) {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validateserial),
                            getActivity().getString(R.string.app_name)).show();
                    return false;
                }
                if (!isHSDT && !name.contains(";ds_no_otp;")) {
                    if (CommonActivity.isNullOrEmpty(editotp.getText().toString().trim())) {
                        CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validateotp),
                                getActivity().getString(R.string.app_name)).show();
                        return false;
                    }
                }
                String codeCapcha = edt_capcha.getText().toString().trim();
                if (!isHSDT && CommonActivity.isNullOrEmpty(codeCapcha)) {
                    CommonActivity.createAlertDialog(getActivity(),
                            getResources().getString(R.string.please_input_capcha), getString(R.string.app_name))
                            .show();
                    return false;
                }
                if (!isHSDT && !cap.checkAnswer(codeCapcha)) {
                    CommonActivity.createAlertDialog(getActivity(),
                            getResources().getString(R.string.error_input_capcha), getString(R.string.app_name)).show();
                    return false;
                }
                if ("M".equals(serviceType)) {
                    try {
                        Date dateSign = sdf.parse(edit_ngaykySub.getText().toString());
                        if (dateSign.after(dateNow)) {
                            Toast.makeText(getActivity(), getString(R.string.signDateEmptyThan), Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    } catch (Exception e) {
                        Log.d("Exception", e.toString());
                    }
                }
            }
        } else {
            if (!CommonActivity.isNullOrEmpty(subscriberDTO.getCustomerDTOInput())
                    && !CommonActivity.isNullOrEmpty(subscriberDTO.getCustomerDTOInput().getCustId())) {
                // truong hop kh cu
                // validate truong hop khong chinh chu validate phan thong tin doi sim
                if (!CommonActivity.isNullOrEmpty(subscriberDTO.getCustomerDTOInput())
                        && !CommonActivity.isNullOrEmpty(subscriberDTO.getCustomerDTOInput())
                        && !CommonActivity.isNullOrEmpty(subscriberDTO.getCustomerDTOInput().getCustId())
                        && !CommonActivity.isNullOrEmpty(subscriberDTO.getCustomerDTOInput().getCustTypeDTO().getPlan())
                        && !"1".equals(subscriberDTO.getCustomerDTOInput().getCustTypeDTO().getPlan())) {
                    if (CommonActivity.isNullOrEmpty(custypeKey)) {
                        CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.updatecustype), getActivity().getString(R.string.app_name), onclickChangeCustype).show();
                        return false;
                    }
                }
            }

            if (CommonActivity.isNullOrEmpty(edit_serialsim.getText().toString().trim())) {
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validateserial),
                        getActivity().getString(R.string.app_name)).show();
                return false;
            }
            if (!isHSDT && !name.contains(";ds_no_otp;")) {
                if (CommonActivity.isNullOrEmpty(editotp.getText().toString().trim())) {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validateotp),
                            getActivity().getString(R.string.app_name)).show();
                    return false;
                }
            }

            String codeCapcha = edt_capcha.getText().toString().trim();
            if (!isHSDT && CommonActivity.isNullOrEmpty(codeCapcha)) {
                CommonActivity.createAlertDialog(getActivity(), getResources().getString(R.string.please_input_capcha),
                        getString(R.string.app_name)).show();
                return false;
            }
            if (!isHSDT && !cap.checkAnswer(codeCapcha)) {
                CommonActivity.createAlertDialog(getActivity(), getResources().getString(R.string.error_input_capcha),
                        getString(R.string.app_name)).show();
                return false;
            }
        }

        return true;
    }

    private void initTypePaper(ArrayList<CustIdentityDTO> lstTypePaper, Spinner spinerPaper) {
        if (lstTypePaper == null) {
            lstTypePaper = new ArrayList<CustIdentityDTO>();
        }
        ArrayAdapter<CustIdentityDTO> adapter = null;
        // if (lstTypePaper != null && !lstTypePaper.isEmpty()) {
        adapter = new ArrayAdapter<CustIdentityDTO>(getActivity(), android.R.layout.simple_dropdown_item_1line,
                android.R.id.text1);
        adapter.addAll(lstTypePaper);
        // for (CustIdentityDTO custIdentityDTO : lstTypePaper) {
        // adapter.add(custIdentityDTO.getIdTypeName());
        // }
        spinerPaper.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        // }
    }

    private void showSelectCus(final ArrayList<CustIdentityDTO> lstCusIdentity) {
        dialogCus = new Dialog(getActivity());
        dialogCus.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogCus.setContentView(R.layout.connection_layout_select_customer);
        dialogCus.setCancelable(false);
        ListView lvcustomerDiaLog = (ListView) dialogCus.findViewById(R.id.listcustomer);
        adaGetListCustomerBccsAdapterDialog = new GetListCustomerBccsAdapter(getActivity(), lstCusIdentity, null);
        lvcustomerDiaLog.setAdapter(adaGetListCustomerBccsAdapterDialog);

        lvcustomerDiaLog.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                CustIdentityDTO repreCustomer = lstCusIdentity.get(arg2);
                AsynGetCustomerByCustIdParent asynGetCustomerByCustId = new AsynGetCustomerByCustIdParent(getActivity(),
                        true);
                asynGetCustomerByCustId.execute(repreCustomer.getCustomer().getCustId() + "");

            }
        });
        Button btnCancel = (Button) dialogCus.findViewById(R.id.btncancel);
        btnCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialogCus.cancel();
            }
        });
        dialogCus.show();
    }

    private void enableCus() {
        customerDTO = new CustomerDTO();
        custTypeDTO = null;
        txtsogiayto.setText(Html.fromHtml(getActivity().getString(R.string.sogiaytoCD)));
        lnsogiayto.setVisibility(View.VISIBLE);
        lntax.setVisibility(View.GONE);
        ln_sex.setVisibility(View.VISIBLE);
        lnquoctich.setVisibility(View.VISIBLE);
        lnngaycap.setVisibility(View.VISIBLE);
        lnnoicap.setVisibility(View.VISIBLE);
        edtloaikh.setEnabled(true);
        edtloaikh.setText("");
        edtloaikh.setHint(getActivity().getString(R.string.chonloaiKH));
        spinner_type_giay_to.setEnabled(true);
        edit_sogiayto.setEnabled(true);
        edit_sogiayto.setText("");
        btnkiemtra.setVisibility(View.VISIBLE);
        btnedit.setVisibility(View.GONE);
        edit_tax.setEnabled(true);
        edit_tax.setText("");
        edit_tenKH.setEnabled(true);
        edit_tenKH.setText("");
        edit_ngaysinh.setEnabled(true);
        edit_ngaysinh.setText(dateNowString);
        edit_ngaycap.setEnabled(true);
        edit_ngaycap.setText(dateNowString);
        edit_noicap.setEnabled(true);
        edit_noicap.setText("");
        spinner_quoctich.setEnabled(true);
        edit_note.setEnabled(true);
        edit_note.setText("");
        if (!CommonActivity.isNullOrEmptyArray(spinNation)) {
            for (Spin item : spinNation) {
                if ("VN".equals(item.getId())) {
                    spinner_quoctich.setSelection(spinNation.indexOf(item));
                    break;
                }
            }
        }

        spinner_gioitinh.setEnabled(true);
        edtdiachilapdat.setEnabled(true);
        edtdiachilapdat.setText("");
    }

    private void disableCus(CustomerDTO customerDTO) {
        edtloaikh.setEnabled(false);
        if (!CommonActivity.isNullOrEmpty(customerDTO.getCustTypeDTO()) && !CommonActivity.isNullOrEmpty(customerDTO.getCustTypeDTO().getName())) {
            edtloaikh.setText(customerDTO.getCustTypeDTO().getName());
            if (CommonActivity.isNullOrEmptyArray(arrTypePaper)) {
                GetMappingCustIdentityUsageAsyn getMappingCustIdentityUsageAsyn = new GetMappingCustIdentityUsageAsyn(
                        getActivity(), prbGiayto, btnRefreshCustType, spinner_type_giay_to);
                getMappingCustIdentityUsageAsyn.execute(customerDTO.getCustType());
            }
        }
        spinner_type_giay_to.setEnabled(false);
        if (!CommonActivity.isNullOrEmptyArray(customerDTO.getListCustIdentity())) {
            if (!CommonActivity.isNullOrEmptyArray(arrTypePaper)) {
                for (CustIdentityDTO custIdentityDTO : arrTypePaper) {
                    if (customerDTO.getListCustIdentity().get(0).getIdNo().equals(custIdentityDTO.getIdNo())) {
                        spinner_type_giay_to.setSelection(arrTypePaper.indexOf(custIdentityDTO));
                        break;
                    }
                }
            }
            // truong hop ca nhan
            for (CustIdentityDTO cusIndentityDTO : customerDTO.getListCustIdentity()) {
                if ("BUS".equals(cusIndentityDTO.getIdType()) || "TIN".equals(cusIndentityDTO.getIdType())) {
                    if ("BUS".equals(cusIndentityDTO.getIdType())) {
                        txtsogiayto.setText(getActivity().getString(R.string.soGPKD));
                        edit_sogiayto.setText(cusIndentityDTO.getIdNo());
                    }
                    if ("TIN".equals(cusIndentityDTO.getIdType())) {
                        edit_tax.setText(cusIndentityDTO.getIdNo());
                    }
                    lnsogiayto.setVisibility(View.GONE);
                    lntax.setVisibility(View.VISIBLE);
                    ln_sex.setVisibility(View.GONE);
                    lnquoctich.setVisibility(View.GONE);
                    lnngaycap.setVisibility(View.VISIBLE);
                    lnnoicap.setVisibility(View.VISIBLE);
                    lnsogiayto.setVisibility(View.GONE);

                } else {
                    lnsogiayto.setVisibility(View.VISIBLE);
                    txtsogiayto.setText(Html.fromHtml(getActivity().getString(R.string.sogiaytoCD)));
                    lntax.setVisibility(View.GONE);
                    ln_sex.setVisibility(View.VISIBLE);
                    lnquoctich.setVisibility(View.VISIBLE);
                    lnngaycap.setVisibility(View.VISIBLE);
                    lnnoicap.setVisibility(View.VISIBLE);

                    edit_sogiayto.setText(cusIndentityDTO.getIdNo());
                    edit_ngaycap.setText(StringUtils.convertDate(cusIndentityDTO.getIdIssueDate()));
                    edit_noicap.setText(cusIndentityDTO.getIdIssuePlace());
                }
            }
        }

        edit_sogiayto.setEnabled(false);
        btnkiemtra.setVisibility(View.GONE);
        btnedit.setVisibility(View.VISIBLE);
        edit_tax.setEnabled(false);
        edit_tenKH.setEnabled(false);
        edit_tenKH.setText(customerDTO.getName());
        edit_ngaysinh.setEnabled(false);
        edit_ngaysinh.setText(StringUtils.convertDate(customerDTO.getBirthDate()));
        edit_ngaycap.setEnabled(false);
        edit_noicap.setEnabled(false);
        spinner_quoctich.setEnabled(false);
        edit_note.setEnabled(false);
        edit_note.setText(customerDTO.getDescription());
        if (!CommonActivity.isNullOrEmpty(customerDTO.getNationality())
                && !CommonActivity.isNullOrEmptyArray(spinNation)) {
            for (Spin spin : spinNation) {
                if (customerDTO.getNationality().equals(spin.getId())) {
                    spinner_quoctich.setSelection(spinNation.indexOf(spin));
                }
            }
        }
        edtdiachilapdat.setEnabled(false);
        edtdiachilapdat.setText(customerDTO.getAddress());
        spinner_gioitinh.setEnabled(false);
        if (arrSexBeans != null && arrSexBeans.size() > 0 && !CommonActivity.isNullOrEmpty(customerDTO.getSex())) {
            for (SexBeans item : arrSexBeans) {
                if (customerDTO.getSex().equals(item.getValues())) {
                    spinner_gioitinh.setSelection(arrSexBeans.indexOf(item));
                    break;
                }
            }
        }
        spinner_gioitinh.setEnabled(false);
    }

    private void parseResultError(String result) {
        if (result != null) {
            try {
                XmlDomParse domParse = new XmlDomParse();
                Document document = domParse.getDomElement(result);

                NodeList nodeListErrorCode = document.getElementsByTagName("return");

                for (int i = 0; i < nodeListErrorCode.getLength(); i++) {
                    Node mNode = nodeListErrorCode.item(i);
                    Element element = (Element) mNode;
                    String errorCode = domParse.getValue(element, "errorCode");
                    String description = domParse.getValue(element, "description");
                    String token = domParse.getValue(element, "token");
                    if (token == null || token.equals("")) {

                    } else {
                        Session.setToken(token);
                    }
                    if (errorCode.equalsIgnoreCase(Constant.INVALID_TOKEN)) {
                        CommonActivity.BackFromLogin(getActivity(), description);
                    } else if (errorCode.equals("0")) {
                    }
                }
            } catch (Exception e) {
                Log.e("Exception", e.toString(), e);
            }
        }
    }

    // init gioi tinh
    private void initSex() {
        arrSexBeans.add(new SexBeans(getString(R.string.male), "M"));
        arrSexBeans.add(new SexBeans(getString(R.string.female), "F"));
        if (arrSexBeans != null && arrSexBeans.size() > 0) {
            ArrayAdapter<SexBeans> adapter = new ArrayAdapter<SexBeans>(getActivity(),
                    android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
            adapter.addAll(arrSexBeans);
            // for (SexBeans sexBeans : arrSexBeans) {
            // adapter.add(sexBeans.getName());
            // }
            spinner_gioitinh.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    private void showDialogGetFee(ArrayList<ProductPackageFeeDTO> arrProductPakage) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_changesim_viewfee);
        dialog.setCancelable(false);
        ListView listView = (ListView) dialog.findViewById(R.id.listView);
        GetFeeBCCSAdapter getFeeBCCSAdapter = new GetFeeBCCSAdapter(arrProductPakage, getActivity());
        listView.setAdapter(getFeeBCCSAdapter);

        dialog.findViewById(R.id.btncancel).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.cancel();
            }
        });
        dialog.show();

    }

    protected void timKiemDonVi() {
        dialogLockBoxInfo();
    }

    private void dialogLockBoxInfo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // LocationService locationService = arrayListLocation.get(position);

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View dialogView = inflater.inflate(R.layout.dialog_don_vi, null);

        builder.setView(dialogView);
        dialogDonVi = builder.create();

        final EditText edtMaDonVi = (EditText) dialogView.findViewById(R.id.edtMaDonVi);
        final EditText edtTenDonVi = (EditText) dialogView.findViewById(R.id.edtTenDonVi);
        Button btnTimKiem = (Button) dialogView.findViewById(R.id.btnTimKiem);

        lvListDonVi = (ListView) dialogView.findViewById(R.id.lvLockBoxInfo);

        btnTimKiem.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String maDonVi = edtMaDonVi.getText().toString();
                String tenDonVi = edtTenDonVi.getText().toString();

                hideKeyBoard();
                if (maDonVi.equals("") && tenDonVi.equals("")) {
                    Toast.makeText(getActivity(), getString(R.string.chua_nhap_ma_dvi), Toast.LENGTH_LONG).show();
                } else {

                    mAratListDonVi = new ArrayList<DoiTuongObj>();
                    if (donviadapter != null) {
                        donviadapter.notifyDataSetChanged();
                    }

                    getDonVi(maDonVi, tenDonVi, mCodeDoiTuong);
                }
            }
        });
        lvListDonVi.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (mAratListDonVi != null && mAratListDonVi.size() > 0) {
                    mCodeDonVi = mAratListDonVi.get(position).getCode();
                    tvDonVi.setText(mAratListDonVi.get(position).getCodeName());
                    imgDeleteDV.setVisibility(View.VISIBLE);
                    dialogDonVi.dismiss();
                }

            }
        });

        dialogDonVi.show();

    }

    protected void getDonVi(String maDonVi, String tenDonVi, String codeObject) {
        new GetLisDonVi(maDonVi, tenDonVi, codeObject).execute();

    }

    protected void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public void showDatePickerDialog(final EditText tvNgay) {
        OnDateSetListener callback = new OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Date
                switch (tvNgay.getId()) {
                    case R.id.edtNgayBD:

                        String fromMothStr = "";
                        if (monthOfYear + 1 < 10) {
                            fromMothStr = "0" + (monthOfYear + 1);
                        } else {
                            fromMothStr = "" + (monthOfYear + 1);
                        }

                        String fromDayStr = "";
                        if (dayOfMonth < 10) {
                            fromDayStr = "0" + dayOfMonth;
                        } else {
                            fromDayStr = "" + dayOfMonth;
                        }

                        mNgayBatDau = (fromDayStr) + "/" + (fromMothStr) + "/" + year;
                        edtNgayBD.setText(mNgayBatDau);
                        try {
                            dateBD = sdf.parse(mNgayBatDau);
                        } catch (Exception e) {
                            Log.e("Exception", e.toString(), e);
                        }

                        // TODO modify ngay 08/07/2015 === thinhhq1
                        if (mCodeDoiTuong.equalsIgnoreCase("NEW_STU_15")) {
                            // neu la doi tuong tan sinh vien + 5 nÃƒâ€žÃ†â€™m ngay
                            // bat
                            // dau
                            // va enable
                            String[] ngayketthuc = edtNgayBD.getText().toString().split("/");
                            if (ngayketthuc.length == 3) {
                                int yearKT = Integer.parseInt(ngayketthuc[2]) + 5;
                                mNgayKetThuc = ngayketthuc[0] + "/" + ngayketthuc[1] + "/" + yearKT;
                                edtNgayKT.setText(mNgayKetThuc);
                                edtNgayKT.setEnabled(true);
                            }

                        } else {
                            edtNgayKT.setEnabled(true);
                        }

                        break;
                    case R.id.edtNgayKT:

                        String fromMothStr1 = "";
                        if (monthOfYear + 1 < 10) {
                            fromMothStr1 = "0" + (monthOfYear + 1);
                        } else {
                            fromMothStr1 = "" + (monthOfYear + 1);
                        }

                        String fromDayStr1 = "";
                        if (dayOfMonth < 10) {
                            fromDayStr1 = "0" + dayOfMonth;
                        } else {
                            fromDayStr1 = "" + dayOfMonth;
                        }

                        mNgayKetThuc = (fromDayStr1) + "/" + (fromMothStr1) + "/" + year;
                        edtNgayKT.setText(mNgayKetThuc);
                        try {
                            dateEnd = sdf.parse(mNgayKetThuc);
                        } catch (Exception e) {
                            Log.e("Exception", e.toString(), e);
                        }
                        break;
                    case R.id.edtngaysinhhs:

                        String fromMothStr2 = "";
                        if (monthOfYear + 1 < 10) {
                            fromMothStr2 = "0" + (monthOfYear + 1);
                        } else {
                            fromMothStr2 = "" + (monthOfYear + 1);
                        }

                        String fromDayStr2 = "";
                        if (dayOfMonth < 10) {
                            fromDayStr2 = "0" + dayOfMonth;
                        } else {
                            fromDayStr2 = "" + dayOfMonth;
                        }

                        String bithdatehs = (fromDayStr2) + "/" + (fromMothStr2) + "/" + year;
                        edtngaysinhhs.setText(bithdatehs);
                        try {
                            birthDateHs = sdf.parse(bithdatehs);
                        } catch (Exception e) {
                            Log.e("Exception", e.toString(), e);
                        }
                        break;

                    default:
                        break;
                }
                cal.set(year, monthOfYear, dayOfMonth);
            }
        };
        DatePickerDialog pic = null;
        if (!CommonActivity.isNullOrEmpty(tvNgay.getText().toString())) {
            Date date = DateTimeUtils.convertStringToTime(tvNgay.getText().toString(), "dd/MM/yyyy");
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            pic = new FixedHoloDatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, callback, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH));
        } else {
            pic = new FixedHoloDatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, callback, year, month, day);
        }
        pic.setTitle(getString(R.string.chon_ngay));
        pic.show();
    }

    // show thong tin dai dien khach hang doanh nghiep
    private void showDialogParentCustomer(CustomerDTO customerDTO) {
        dialogCusPR = new Dialog(getActivity());
        dialogCusPR.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogCusPR.setContentView(R.layout.layout_cusparent_changesim);
        dialogCusPR.setCancelable(false);
    }

    @Override
    protected void setPermission() {

    }

    public void scanQR(View v) {
        try {
            // start the scanning activity from the
            // com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {

        }
    }

    private void initCap() {
        edt_capcha.setText("");
        cap = new MathCaptcha(300, 100, MathOptions.PLUS_MINUS);
        imgCapcha.setImageBitmap(cap.getImage());
    }

    private void showSelectIsdnOwner() {

        final Dialog dialogIsdn = new Dialog(getActivity());
        dialogIsdn.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogIsdn.setContentView(R.layout.dialog_search_isdn);
        lvsubparent = (ExpandableHeightListView) dialogIsdn
                .findViewById(R.id.lvsubparent);
        lvsubparent.setExpanded(true);
        checkSubAdapter = new CheckSubAdapter(act, arrSubscriberDTO,
                onChangeCheckSub);
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
                checkSubAdapter = new CheckSubAdapter(act, arrSubscriberDTO,
                        onChangeCheckSub);
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
        expandedImageView.setOnClickListener(new OnClickListener() {
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

    // lay danh sach loai khach hang
    private class GetMappingChannelCustTypeAsyn extends AsyncTask<String, Void, ArrayList<CustTypeDTO>> {
        String errorCode = "";
        String description = "";
        ProgressBar progressBarRefresh;
        Button btnRefreshInit;
        private Context context = null;

        public GetMappingChannelCustTypeAsyn(Context context, ProgressBar prBar, Button btnRefresh) {
            this.context = context;
            this.progressBarRefresh = prBar;
            this.btnRefreshInit = btnRefresh;
            progressBarRefresh.setVisibility(View.VISIBLE);
            btnRefreshInit.setVisibility(View.GONE);
        }

        @Override
        protected ArrayList<CustTypeDTO> doInBackground(String... params) {
            return getMappingChannelCustType(params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<CustTypeDTO> result) {

            progressBarRefresh.setVisibility(View.GONE);
            btnRefreshInit.setVisibility(View.VISIBLE);
            if ("0".equals(errorCode)) {
                if (result != null && !result.isEmpty()) {

                    for (CustTypeDTO custTypeDTO : result) {
                        if ("2".equals(custTypeDTO.getGroupType())) {

                        } else {
                            // ko phai khach hang doanh nghiep thi add vao
                            arrCusTypeDTO.add(custTypeDTO);
                        }
                    }
                } else {
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
                            getResources().getString(R.string.checkTypeCus),
                            getResources().getString(R.string.app_name));
                    dialog.show();
                }

            } else {
                if (Constant.INVALID_TOKEN2.equals(errorCode)) {

                    Dialog dialog = CommonActivity.createAlertDialog((Activity) context, description,
                            context.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.equals("")) {
                        description = context.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                            getResources().getString(R.string.app_name));
                    dialog.show();

                }
            }

        }

        private ArrayList<CustTypeDTO> getMappingChannelCustType(String payType) {
            ArrayList<CustTypeDTO> lstCustTypeDTOs = new ArrayList<CustTypeDTO>();
            String original = null;
            try {
                // tra truoc
                if ("2".equals(payType)) {
                    lstCustTypeDTOs = new CacheDatabaseManager(context).getListCusTypeDTO("cusTypeDTOPre");
                } else {
                    // tra sau
                    lstCustTypeDTOs = new CacheDatabaseManager(context).getListCusTypeDTO("cusTypeDTOPos");
                }

                if (lstCustTypeDTOs != null && !lstCustTypeDTOs.isEmpty()) {
                    errorCode = "0";
                    return lstCustTypeDTOs;
                }

                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getMappingChannelCustType");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getMappingChannelCustType>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<String, String>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));
                rawData.append("<payType>" + "1");
                rawData.append("</payType>");
                rawData.append("</input>");
                rawData.append("</ws:getMappingChannelCustType>");
                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
                        "mbccs_getMappingChannelCustType");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.d("original", original);
                // ========parser xml get employ from server
                Serializer serializer = new Persister();
                ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
                if (parseOuput != null) {
                    errorCode = parseOuput.getErrorCode();
                    description = parseOuput.getDescription();
                    lstCustTypeDTOs = parseOuput.getLstCustTypeDTO();
                }

            } catch (Exception e) {
                Log.d("exception", e.toString());
            }
            if ("2".equals(payType)) {
                new CacheDatabaseManager(context).insertCusType("cusTypeDTOPre", lstCustTypeDTOs);
            } else {
                new CacheDatabaseManager(context).insertCusType("cusTypeDTOPos", lstCustTypeDTOs);
            }

            return lstCustTypeDTOs;
        }
    }

    private class GetMappingCustIdentityUsageAsyn extends AsyncTask<String, Void, ArrayList<CustIdentityDTO>> {
        String errorCode = "";
        String description = "";
        ProgressBar prbarCus;
        Button btnRefresh;
        Spinner spinerPaper;
        private Context context = null;

        public GetMappingCustIdentityUsageAsyn(Context context, ProgressBar prb, Button btnres, Spinner spin) {
            this.context = context;
            this.prbarCus = prb;
            this.btnRefresh = btnres;
            this.spinerPaper = spin;
            if (prbarCus != null) {
                prbarCus.setVisibility(View.VISIBLE);
            }
            if (btnRefresh != null) {
                btnRefresh.setVisibility(View.GONE);
            }

        }

        @Override
        protected ArrayList<CustIdentityDTO> doInBackground(String... params) {
            return getMappingCustIdentityUsage(params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<CustIdentityDTO> result) {
            if (prbarCus != null) {
                prbarCus.setVisibility(View.GONE);
            }
            if (btnRefresh != null) {
                btnRefresh.setVisibility(View.VISIBLE);
            }
            arrTypePaper = new ArrayList<CustIdentityDTO>();
            if ("0".equals(errorCode)) {
                // lay danh sach loai giay to
                arrTypePaper = result;
                initTypePaper(arrTypePaper, spinerPaper);
                if (result != null && !result.isEmpty()) {
                } else {
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
                            getResources().getString(R.string.notpapaer), getResources().getString(R.string.app_name));
                    dialog.show();
                }

            } else {
                if (Constant.INVALID_TOKEN2.equals(errorCode)) {

                    Dialog dialog = CommonActivity.createAlertDialog((Activity) context, description,
                            context.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.equals("")) {
                        description = context.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                            getResources().getString(R.string.app_name));
                    dialog.show();

                }
            }

        }

        private ArrayList<CustIdentityDTO> getMappingCustIdentityUsage(String currCusType) {
            ArrayList<CustIdentityDTO> lstTypePaper = new ArrayList<CustIdentityDTO>();
            String original = null;
            try {
                lstTypePaper = new CacheDatabaseManager(context).getListTypePaperFromMap(currCusType);
                if (lstTypePaper != null && !lstTypePaper.isEmpty()) {
                    errorCode = "0";
                    return lstTypePaper;
                }

                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_mappingCustIdentityUsage");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:mappingCustIdentityUsage>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<String, String>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));
                rawData.append("<currCustType>" + currCusType);
                rawData.append("</currCustType>");
                rawData.append("</input>");
                rawData.append("</ws:mappingCustIdentityUsage>");
                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
                        "mbccs_mappingCustIdentityUsage");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.d("original", original);
                // ========parser xml get employ from server
                Serializer serializer = new Persister();
                ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
                if (parseOuput != null) {
                    errorCode = parseOuput.getErrorCode();
                    description = parseOuput.getDescription();
                    lstTypePaper = parseOuput.getLstCustIdentityDTOs();
                }

            } catch (Exception e) {
                Log.d("exception", e.toString());
            }
            new CacheDatabaseManager(context).insertTypePaper(currCusType, lstTypePaper);

            return lstTypePaper;
        }
    }

    private class AsynGetCustomerByCustIdParent extends AsyncTask<String, Void, CustomerDTO> {

        private String errorCode;
        private String description;
        private Context context;
        private ProgressDialog progress;
        private boolean ischeck = false;

        public AsynGetCustomerByCustIdParent(Context mContext, boolean mischeck) {
            this.context = mContext;
            this.ischeck = mischeck;
            this.progress = new ProgressDialog(this.context);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected CustomerDTO doInBackground(String... params) {
            return getCustomerByCustId(params[0]);
        }

        @Override
        protected void onPostExecute(CustomerDTO result) {
            progress.dismiss();
            super.onPostExecute(result);
            if ("0".equals(errorCode)) {
                // thong tin hop dong cu
                if (result != null && result.getCustId() != null) {
                    // xu ly thong tin khach hang dai dien cu cho nay
                    // truong hop dai dien

                    if ("2".equals(result.getCustTypeDTO().getGroupType())) {

                        if (dialogCus != null) {
                            dialogCus.dismiss();
                        }
                        CommonActivity.createAlertDialog(getActivity(),
                                getActivity().getString(R.string.notkhdn),
                                getActivity().getString(R.string.app_name)).show();
                    } else {
                        if (ischeck) {
                            customerDTO = result;
                            disableCus(customerDTO);

                            if (dialogCus != null) {
                                dialogCus.dismiss();
                            }
                            GetReasonFullPM getReasonFullPM = new GetReasonFullPM(getActivity(), prbreasonkh, "1015");
                            getReasonFullPM.execute();

                            GetReasonFullPM getReasonFullPMChangeSim = new GetReasonFullPM(getActivity(), prbreasonkh, "11");
                            getReasonFullPMChangeSim.execute();

                            //lay doi tuong
                            custTypeDTO = customerDTO.getCustTypeDTO();
                            if(!CommonActivity.isNullOrEmpty(custTypeDTO.getGroupType())) {
                                String groupType = custTypeDTO.getGroupType();

                                String code = "";
                                if ("1".equals(groupType) || "3".equals(groupType)) {
                                    code = "INDIVIDUAL";
                                } else if ("2".equals(groupType)) {
                                    code = "BUSINESS";
                                }
                                AsyncGetOptionSetValueV2 asyncGetOptionSetValue = new AsyncGetOptionSetValueV2(act, new OnPostGetInfoCustomer(), moveLogInAct);
                                asyncGetOptionSetValue.execute(code);
                            }

                        } else {
                            // truogn hop ko phai dai dien
                            // custIdentityDTO.setCustomer(result);
                            // truyen thong tin qua tab thong tin hop dong va thue
                            // bao
                        }
                    }
                } else {
                    CommonActivity.createAlertDialog(getActivity(),
                            getActivity().getString(R.string.notDetailCus),
                            getActivity().getString(R.string.app_name)).show();
                }
            } else {
                if (Constant.INVALID_TOKEN2.equals(errorCode)) {

                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                            context.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = context.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                            getResources().getString(R.string.app_name));
                    dialog.show();

                }
            }

        }

        private CustomerDTO getCustomerByCustId(String custId) {
            CustomerDTO customerDTO = new CustomerDTO();
            String original = "";
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getCustomerByCustId");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getCustomerByCustId>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<String, String>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));

                rawData.append("<custId>" + custId);
                rawData.append("</custId>");

                rawData.append("</input>");
                rawData.append("</ws:getCustomerByCustId>");
                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
                        "mbccs_getCustomerByCustId");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.d("original", original);
                // ====== parse xml ===================

                Serializer serializer = new Persister();
                ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
                if (parseOuput != null) {
                    errorCode = parseOuput.getErrorCode();
                    description = parseOuput.getDescription();
                    customerDTO = parseOuput.getCustomerDTO();
                }

                return customerDTO;

            } catch (Exception e) {
                Log.e("getCustomerByCustIdEx", e.toString(), e);
            }
            return customerDTO;

        }
    }

    private class SearchCustIdentityAsyn extends AsyncTask<String, Void, ParseOuput> {

        private ProgressDialog progress;
        private Context context = null;
        private String errorCode = "";
        private String description = "";

        private String check = "";

        public SearchCustIdentityAsyn(Context context, String mCheck) {
            this.context = context;
            this.check = mCheck;
            this.progress = new ProgressDialog(this.context);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected ParseOuput doInBackground(String... arg0) {
            return searchCus(arg0[0], arg0[1], arg0[2]);
        }

        @Override
        protected void onPostExecute(final ParseOuput result) {
            progress.dismiss();
            arrCustIdentityDTO = new ArrayList<CustIdentityDTO>();
            if ("0".equals(errorCode)) {
                if (result != null) {

                    if (result.getLstCustIdentityDTOs() != null && result.getLstCustIdentityDTOs().size() > 0) {
                        arrCustIdentityDTO = result.getLstCustIdentityDTOs();

                        if (!CommonActivity.isNullOrEmpty(check)) {
                            showSelectCus(arrCustIdentityDTO);
                        }

                    } else {
                        if (description != null && !description.isEmpty()) {
                            Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                                    getResources().getString(R.string.app_name));
                            dialog.show();
                        } else {
                            Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
                                    getResources().getString(R.string.notkh),
                                    getResources().getString(R.string.app_name));
                            dialog.show();
                        }
                    }

                } else {
                    if (description != null && !description.isEmpty()) {
                        Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                                getResources().getString(R.string.app_name));
                        dialog.show();
                    } else {
                        Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
                                getResources().getString(R.string.no_data),
                                getResources().getString(R.string.app_name));
                        dialog.show();
                    }

                }
            } else {
                if (Constant.INVALID_TOKEN2.equals(errorCode) && description != null && !description.isEmpty()) {
                    Dialog dialog = CommonActivity.createAlertDialog((Activity) context, description,
                            context.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = context.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                            getResources().getString(R.string.app_name));
                    dialog.show();

                }
            }
        }

        private ParseOuput searchCus(String idNo, String custType, String idType) {
            String original = null;
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_searchCustIdentity");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:searchCustIdentity>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<String, String>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));
                rawData.append("<custIdentitySearchDTO>");
                rawData.append("<idNo>" + idNo);
                rawData.append("</idNo>");
                if (!CommonActivity.isNullOrEmpty(custType)) {
                    rawData.append("<custType>" + custType);
                    rawData.append("</custType>");
                }

                rawData.append("</custIdentitySearchDTO>");
                rawData.append("</input>");
                rawData.append("</ws:searchCustIdentity>");
                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
                        "mbccs_searchCustIdentity");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("Responseeeeeeeeee", original);

                Serializer serializer = new Persister();
                ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
                if (parseOuput != null) {
                    errorCode = parseOuput.getErrorCode();
                    description = parseOuput.getDescription();
                }

                return parseOuput;
            } catch (Exception e) {
                Log.i("SearchCustIdentity", e.toString());
            }
            return null;
        }
    }

    private class GetReasonFullPM extends AsyncTask<String, Void, List<ReasonDTO>> {

        private Context mContext;
        private String errorCode;
        private String description;
        private ProgressBar progress;
        private String actionCode = "";

        public GetReasonFullPM(Context context, ProgressBar progressBar, String actioncode) {
            this.actionCode = actioncode;
            this.mContext = context;
            this.progress = progressBar;
            this.progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<ReasonDTO> doInBackground(String... arg0) {
            return getListReasonDTO();
        }

        @Override
        protected void onPostExecute(List<ReasonDTO> result) {
            super.onPostExecute(result);
            progress.setVisibility(View.GONE);
            try {
                // doi sim
                if ("11".equals(actionCode)) {
                    arrReasonChangeSim = new ArrayList<ReasonDTO>();
                }
                if ("1015".equals(actionCode)) {
                    arrReasonChangeCus = new ArrayList<ReasonDTO>();
                }
                if ("0".equals(errorCode)) {
                    if (!CommonActivity.isNullOrEmpty(result)) {

                        if ("11".equals(actionCode)) {
                            arrReasonChangeSim = result;
                            ReasonDTO item = new ReasonDTO();
                            item.setReasonId("");
                            item.setName(getActivity().getString(R.string.txt_select));
                            arrReasonChangeSim.add(0, item);

                            AutoCompleteUtil.getInstance(getActivity()).sortReasonChangeSimBySelectedCount(AUTO_CHANGE_SIM, arrReasonChangeSim);
                            Utils.setDataReasonDTO(getActivity(), arrReasonChangeSim, spnReasonChangeSim);
                        }

                        if ("1015".equals(actionCode)) {
                            arrReasonChangeCus = result;
                            ReasonDTO item = new ReasonDTO();
                            item.setReasonId("");
                            item.setName(getActivity().getString(R.string.txt_select));
                            arrReasonChangeSim.add(0, item);
                            Utils.setDataReasonDTO(getActivity(), arrReasonChangeCus, spinner_reasonkh);
                        }

                    } else {
                        if ("11".equals(actionCode)) {
                            arrReasonChangeSim = new ArrayList<ReasonDTO>();
                            Utils.setDataReasonDTO(getActivity(), arrReasonChangeSim, spnReasonChangeSim);
                        }
                        if ("1015".equals(actionCode)) {
                            arrReasonChangeCus = new ArrayList<ReasonDTO>();
                            Utils.setDataReasonDTO(getActivity(), arrReasonChangeCus, spinner_reasonkh);
                        }
                        CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.no_data),
                                getActivity().getString(R.string.app_name))
                                .show();
                    }
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
            } catch (Exception e) {
                Log.d("Exception", e.toString());
            }

        }

        private List<ReasonDTO> getListReasonDTO() {
            String original = null;
            List<ReasonDTO> lstReasonDTO = new ArrayList<ReasonDTO>();
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getReasonFullPM");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getReasonFullPM>");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");

                rawData.append("<payType>" + subscriberDTO.getPayType() + "</payType>");
                rawData.append("<offerId>" + subscriberDTO.getOfferId() + "</offerId>");
                // 11 doi sim 1015
                rawData.append("<actionCode>" + actionCode + "</actionCode>");
                rawData.append("<serviceType>" + "M" + "</serviceType>");

                // thay doi thong tin khach hang
                String cusType = "";
                if (subscriberDTO.isMarkNotOwner()) {
                    if (!CommonActivity.isNullOrEmpty(customerDTO)
                            && !CommonActivity.isNullOrEmpty(customerDTO.getCustId())) {
                        cusType = customerDTO.getCustType();
                    } else {
                        cusType = custTypeDTO.getCustType();
                    }
                } else {
                    cusType = subscriberDTO.getCustomerDTOInput().getCustType();
                }

                rawData.append("<cusType>" + cusType + "</cusType>");

                rawData.append("<subType>" + subscriberDTO.getSubType() + "</subType>");

                rawData.append("</input>");
                rawData.append("</ws:getReasonFullPM>");
                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
                        "mbccs_getReasonFullPM");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);

                original = output.getOriginal();
                Log.d("originalllllllll", original);

                Serializer serializer = new Persister();
                ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
                if (parseOuput != null) {
                    errorCode = parseOuput.getErrorCode();
                    description = parseOuput.getDescription();
                    lstReasonDTO = parseOuput.getLstReasonDTO();
                }

                return lstReasonDTO;
            } catch (Exception e) {
                Log.d("getListSubscriber", e.toString());
            }

            return null;
        }
    }

    private class SendOTPUtilAsyn extends AsyncTask<String, Void, String> {
        private Context mContext;
        private String errorCode;
        private String description;
        private ProgressDialog progress;

        public SendOTPUtilAsyn(Context context) {
            this.mContext = context;
            this.progress = new ProgressDialog(this.mContext);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdatareport));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected String doInBackground(String... arg0) {
            return sendOtpUntil(arg0[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progress.dismiss();
            lnotp.setVisibility(View.VISIBLE);
            if ("0".equals(errorCode)) {
                CommonActivity.createAlertDialog(getActivity(),
                        getActivity().getString(R.string.confirmotpds, subscriberDTO.getIsdn()),
                        getActivity().getString(R.string.app_name)).show();
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

        private String sendOtpUntil(String isdn) {
            String original = null;
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_sendOTPUtil");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:sendOTPUtil>");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");
                rawData.append("<isdn>" + CommonActivity.getStardardIsdnBCCS(isdn) + "</isdn>");
                rawData.append("</input>");
                rawData.append("</ws:sendOTPUtil>");
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
                } else {
                    errorCode = Constant.ERROR_CODE;
                    description = getActivity().getString(R.string.no_data_return);
                }
            } catch (Exception e) {
                Log.d("sendOtpUntil", e.toString());
                errorCode = Constant.ERROR_CODE;
                description = getActivity().getString(R.string.no_data_return);
            }
            return errorCode;
        }
    }

    private class ChangeSimAsyn extends AsyncTask<Void, String, String> {

        private Context mContext;
        private String errorCode;
        private String description;
        private ProgressDialog progress;
        private ArrayList<String> lstFilePath = new ArrayList<String>();
        private List<Action> lstAction;

        public ChangeSimAsyn(Context context) {
            this.mContext = context;
            this.progress = new ProgressDialog(this.mContext);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdatareport));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected String doInBackground(Void... arg0) {
            // TODO: 10/21/2017
            try {
                if (profileBO.getHashmapFileObj() != null && profileBO.getHashmapFileObj().size() > 0) {
                    File folder = new File(Constant.MBCCS_TEMP_FOLDER_IMG_PROFILE);
                    if (!folder.exists()) {
                        folder.mkdir();
                    }
                    Log.d("Log", "Folder zip file create: " + folder.getAbsolutePath());
                    for (Map.Entry<String, ArrayList<FileObj>> entry : profileBO.getHashmapFileObj().entrySet()) {
                        ArrayList<FileObj> listFileObjs = entry.getValue();
                        String zipFilePath = "";
                        if (listFileObjs.size() > 1) {
                            String spinnerCode = "";
                            for (FileObj fileObj : listFileObjs) {
                                spinnerCode = fileObj.getCodeSpinner();
                            }
                            zipFilePath = folder.getPath() + File.separator + System.currentTimeMillis() + "_"
                                    + spinnerCode + ".zip";
                            lstFilePath.add(zipFilePath);
                        } else if (listFileObjs.size() == 1) {
                            zipFilePath = folder.getPath() + File.separator + System.currentTimeMillis() + "_"
                                    + listFileObjs.get(0).getCodeSpinner() + ".jpg";
                            lstFilePath.add(zipFilePath);
                        }
                    }
                    return changeSim(lstFilePath);
                } else {
                    return changeSim(null);
                }
            } catch (Exception e) {
                errorCode = "1";
                description = "Error when zip file: " + e.toString();
                return "1";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            progress.dismiss();
            try {
                SaveLog saveLog = new SaveLog(mContext, Constant.SYSTEM_NAME, Session.userName, "mbccs_changeSim",
                        CommonActivity.findMyLocation(mContext).getX(), CommonActivity.findMyLocation(mContext).getY());
                timeEnd = new Date();

                saveLog.saveLogRequest(errorCode, timeStart, timeEnd, Session.userName + "_"
                        + CommonActivity.getDeviceId(mContext) + "_" + System.currentTimeMillis());
            } catch (Exception e) {
                Log.e("Exception", e.toString(), e);
            }
            if ("0".equals(errorCode)) {
                btnChangeSim.setVisibility(View.GONE);
                if (!CommonActivity.isNullOrEmptyArray(lstFilePath)
                        && profileBO.getHashmapFileObj() != null && profileBO.getHashmapFileObj().size() > 0) {
                    AsynZipFile asynZipFile = new AsynZipFile(getActivity(),
                            profileBO.getHashmapFileObj(), lstFilePath, lstAction);
                    asynZipFile.execute();
                } else {
                    CommonActivity.createAlertDialog(getActivity(),
                            getActivity().getString(R.string.changesimsucsess),
                            getActivity().getString(R.string.app_name)).show();
                }
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

        private String changeSim(ArrayList<String> mlstFilePath) {
            String original = null;
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_changeSim");
                StringBuilder rawData = new StringBuilder();

                rawData.append("<ws:changeSim>");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");
                if (!CommonActivity.isNullOrEmpty(custypeKey)) {
                    rawData.append("<custType>" + custypeKey + "</custType>");
                }

                //omni
                if (!CommonActivity.isNullOrEmpty(hotLineReponseDetail)
                        && !CommonActivity.isNullOrEmpty(hotLineReponseDetail.getReciveRequestId())) {
                    rawData.append("<hotlineInput>");
                    rawData.append("<receiveRequestId>" + hotLineReponseDetail.getReciveRequestId() + "</receiveRequestId>");
                    rawData.append("</hotlineInput>");
                }

                if (subscriberDTO.isMarkNotOwner()) {
                    ReasonDTO reasonChangeCus = (ReasonDTO) spinner_reasonkh.getSelectedItem();
                    if (reasonChangeCus != null) {
                        rawData.append("<reasonIdUpdateCustomer>" + reasonChangeCus.getReasonId()
                                + "</reasonIdUpdateCustomer>");
                    }
                    if (customerDTO != null && !CommonActivity.isNullOrEmpty(customerDTO.getCustId())) {
                        rawData.append("<customerDTO>");
                        rawData.append(getXmlCusSubmit());
                        rawData.append("</customerDTO>");
                    } else {
                        rawData.append("<customerDTO>");
                        rawData.append(getXmlCusSubmit());
                        rawData.append("</customerDTO>");
                    }
                    if ("IS_SPECIAL_PRODUCT".equals(checkIsSpec)) {
                        rawData.append("<productSpecInfoDTO>");
                        rawData.append("<productCode>"
                                + subscriberDTO.getProductCode()
                                + "</productCode>");
                        rawData.append("<showProdSpecInfo>" + true
                                + "</showProdSpecInfo>");
                        rawData.append("<status>" + 1 + "</status>");
                        rawData.append("<idNo>"
                                + edit_sogiayto.getText().toString()
                                + "</idNo>");
                        rawData.append("<departmentCode>" + mCodeDonVi
                                + "</departmentCode>");
                        rawData.append("<endDatetime>"
                                + StringUtils.convertDateToString(edtNgayKT
                                .getText().toString().trim())
                                + "T00:00:00Z" + "</endDatetime>");
                        rawData.append("<nationCode>" + "VIE" + "</nationCode>");
                        rawData.append("<objectCode>" + mCodeDoiTuong
                                + "</objectCode>");
                        rawData.append("<orderNumber>"
                                + edtMaGiayToDacBiet.getText().toString()
                                + "</orderNumber>");
                        rawData.append("<startDatetime>"
                                + StringUtils.convertDateToString(edtNgayBD
                                .getText().toString().trim())
                                + "T00:00:00Z" + "</startDatetime>");
                        rawData.append("</productSpecInfoDTO>");
                    }

                    // dau noi goi hoc sinh
                    if ("SPEC_HISCL".equals(checkIsSpec)) {
                        rawData.append("<productSpecInfoDTO>");
                        rawData.append("<productCode>"
                                + subscriberDTO.getProductCode()
                                + "</productCode>");
                        rawData.append("<showProdSpecInfo>" + true
                                + "</showProdSpecInfo>");
                        rawData.append("<status>" + 1 + "</status>");
                        rawData.append("<idNo>"
                                + edit_sogiayto.getText().toString()
                                + "</idNo>");
                        rawData.append("<subDob>"
                                + StringUtils.convertDateToString(edtngaysinhhs
                                .getText().toString().trim())
                                + "T00:00:00Z" + "</subDob>");
                        rawData.append("<subName>"
                                + edttenhs.getText().toString().trim()
                                + "</subName>");
                        rawData.append("</productSpecInfoDTO>");
                    }

                    if ("SPEC_ELDER".equals(checkIsSpec)) {
                        rawData.append("<productSpecInfoDTO>");
                        rawData.append("<productCode>"
                                + subscriberDTO.getProductCode()
                                + "</productCode>");
                        rawData.append("<showProdSpecInfo>" + true
                                + "</showProdSpecInfo>");
                        rawData.append("<status>" + 1 + "</status>");
                        rawData.append("<idNo>"
                                + edit_sogiayto.getText().toString()
                                + "</idNo>");
                        rawData.append("</productSpecInfoDTO>");
                    }
                } else {
                    // truong hop chinh chu
                    rawData.append("<customerDTO>");
                    rawData.append(getXmlCusSubmit());
                    rawData.append("</customerDTO>");
                }

                if (!CommonActivity.isNullOrEmptyArray(mlstFilePath)) {
                    for (String fileObj : mlstFilePath) {
                        rawData.append("<lstFilePath>");
                        rawData.append(fileObj);
                        rawData.append("</lstFilePath>");
                    }

                    if (profileBO.getHashmapFileObj() != null && profileBO.getHashmapFileObj().size() > 0) {
                        for (Map.Entry<String, ArrayList<FileObj>> entry : profileBO.getHashmapFileObj().entrySet()) {
                            ArrayList<FileObj> listFileObjs = entry.getValue();
                            rawData.append("<lstRecordName>");
                            rawData.append(listFileObjs.get(0).getRecodeName());
                            rawData.append("</lstRecordName>");
                            rawData.append("<lstRecordCode>");
                            rawData.append(listFileObjs.get(0).getCodeSpinner());
                            rawData.append("</lstRecordCode>");
                        }
                    }

                    rawData.append("<fileContent>");
                    rawData.append("");
                    rawData.append("</fileContent>");

                    rawData.append("<noSendFile>" + "NO_SEND_FILE");
                    rawData.append("</noSendFile>");
                }

                rawData.append("<payType>" + subscriberDTO.getPayType() + "</payType>");
                rawData.append("<isdn>" + subscriberDTO.getIsdn() + "</isdn>");
                rawData.append("<newSerial>" + edit_serialsim.getText().toString().trim() + "</newSerial>");
                rawData.append("<value>" + editotp.getText().toString().trim() + "</value>");
                rawData.append("<markNotOwner>" + subscriberDTO.isMarkNotOwner() + "</markNotOwner>");
                rawData.append("<subId>" + subscriberDTO.getSubId() + "</subId>");
                ReasonDTO reasonChangeSim = (ReasonDTO) spnReasonChangeSim.getSelectedItem();
                if (reasonChangeSim != null && !CommonActivity.isNullOrEmpty(reasonChangeSim.getReasonId())) {
                    rawData.append("<reasonIdChangeSim>" + reasonChangeSim.getReasonId() + "</reasonIdChangeSim>");
                }

				if(subscriberDTO.isMarkNotOwner()){
					rawData.append("<subObject>" + subObject + "</subObject>");
				}

                rawData.append("</input>");
                rawData.append("</ws:changeSim>");
                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(), "mbccs_sendOTPUtil");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);

                original = output.getOriginal();
                Log.d("originalllllllll", original);

                Serializer serializer = new Persister();
                com.viettel.bss.viettelpos.v4.customer.object.SaleOutput parseOuput = serializer
                        .read(com.viettel.bss.viettelpos.v4.customer.object.SaleOutput.class,
                                original);

                if (parseOuput != null) {
                    errorCode = parseOuput.getErrorCode();
                    description = parseOuput.getDescription();
                    lstAction = parseOuput.getLstAction();
                } else {
                    errorCode = Constant.ERROR_CODE;
                    description = getActivity().getString(R.string.no_data_return);
                }

            } catch (Exception e) {
                Log.d("changeSim", e.toString());
                errorCode = Constant.ERROR_CODE;
                description = getActivity().getString(R.string.no_data_return);
            }

            return null;
        }
    }

    private class AsynZipFile extends AsyncTask<String, Void, ArrayList<FileObj>> {

        private HashMap<String, ArrayList<FileObj>> mHashMapFileObj;
        private Context mContext;
        private ProgressDialog progress;
        private String errorCode = "";
        private String description = "";
        private ArrayList<String> lstFilePath = new ArrayList<String>();
        private List<Action> lstAction;

        public AsynZipFile(Context context, HashMap<String, ArrayList<FileObj>> hasMapFile,
                           ArrayList<String> mlstFilePath, List<Action> lstAction) {
            this.mContext = context;
            this.mHashMapFileObj = hasMapFile;
            this.lstFilePath = mlstFilePath;
            this.lstAction = lstAction;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.progress = new ProgressDialog(mContext);
            // check font
            this.progress.setMessage(mContext.getResources().getString(R.string.progress_zip));
            this.progress.setCancelable(false);
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected ArrayList<FileObj> doInBackground(String... arg0) {
            ArrayList<FileObj> arrFileBackUp1 = null;
            try {
                arrFileBackUp1 = FileUtils.getArrFileBackUp2(mContext, mHashMapFileObj, lstFilePath);
                errorCode = "0";
                return arrFileBackUp1;
            } catch (Exception e) {
                errorCode = "1";
                description = "Error when zip file: " + e.toString();
                return arrFileBackUp1;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<FileObj> result) {
            progress.dismiss();
            if ("0".equals(errorCode)) {
                if (result != null && !result.isEmpty()) {
                    ArrayList<FileObj> lstTmp = new ArrayList<FileObj>();
                    ReasonDTO reasonKH = (ReasonDTO) spinner_reasonkh
                            .getSelectedItem();
                    ReasonDTO reasonDS = (ReasonDTO) spnReasonChangeSim
                            .getSelectedItem();
                    // lstReasonId.add(reasonDS.getReasonId());
                    // lstActionCode.add("11");
                    for (Action action : lstAction) {
                        for (FileObj fileObj : result) {
                            FileObj tmp = fileObj.clone();
                            tmp.setActionCode(action.getActionCode());
                            if ("11".equals(action.getActionCode())) {
                                tmp.setReasonId(reasonDS.getReasonId());
                            } else if ("1015".equals(action.getActionCode())) {
                                tmp.setReasonId(reasonKH.getReasonId());
                            }
                            tmp.setIsdn(subscriberDTO.getIsdn());
                            tmp.setActionAudit(action.getActionAuditId());
                            tmp.setPageSize(0 + "");
                            tmp.setStatus(0);
                            lstTmp.add(tmp);
                        }
                    }

                    AsyncTaskUpdateImageOfline uploadImageAsy = new AsyncTaskUpdateImageOfline(
                            mContext, lstTmp, onclickBackScreen,
                            getString(R.string.changesimsucsess) + "\n");
                    uploadImageAsy.execute();

                }
            } else {
                if (result != null && result.size() > 0) {
                    for (FileObj fileObj : result) {
                        File tmp = new File(fileObj.getPath());
                        tmp.delete();
                    }
                }
                CommonActivity.createAlertDialog(getActivity(),
                        description, getActivity().getString(R.string.app_name)).show();
            }
        }
    }

    private class FindFeeByReasonTeleIdAsyn extends AsyncTask<String, Void, ArrayList<ProductPackageFeeDTO>> {
        private Context mContext;
        private String errorCode;
        private String description;
        private ProgressDialog progress;

        public FindFeeByReasonTeleIdAsyn(Context context) {
            this.mContext = context;
            this.progress = new ProgressDialog(this.mContext);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdatareport));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected ArrayList<ProductPackageFeeDTO> doInBackground(String... arg0) {
            return getProductSpec(arg0[0], arg0[1], arg0[2]);
        }

        @Override
        protected void onPostExecute(ArrayList<ProductPackageFeeDTO> result) {
            super.onPostExecute(result);
            progress.dismiss();
            if ("0".equals(errorCode)) {
                if (!CommonActivity.isNullOrEmptyArray(result)) {
                    showDialogGetFee(result);
                } else {
                    CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notthogntinvi),
                            getActivity().getString(R.string.app_name)).show();
                }
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

        private ArrayList<ProductPackageFeeDTO> getProductSpec(
                String telecomserviceId, String reasonId, String productCode) {

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

    // Ham lay thong tin doi duong tu offerid va idNo
    private class GetProductSpecInfoPreAsyn extends AsyncTask<String, Void, ArrayList<SpecObject>> {
        ProgressDialog progress;
        XmlDomParse parse = new XmlDomParse();
        String errorCode = "";
        String description = "";
        private Context context = null;

        public GetProductSpecInfoPreAsyn(Context context) {
            this.context = context;
            this.progress = new ProgressDialog(this.context);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected ArrayList<SpecObject> doInBackground(String... arg0) {
            return getLstObjectSpec(arg0[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<SpecObject> result) {
            progress.dismiss();

            // truong hop co giay to dac biet == danh sach cac doi duong
            if (errorCode.equalsIgnoreCase("0")) {
                if (result != null && !result.isEmpty()) {
                    arrSpecObjects = result;
                    for (SpecObject item : arrSpecObjects) {
                        if ("NEW_STU_15".equals(item.getCode())) {
                            arrSpecObjects.remove(item);
                            break;
                        }
                    }
                    // hien thi form thong tin dac biet len
                    lnTTGoiCuocDacBiet.setVisibility(View.VISIBLE);
                    // do du lieu vao spin doi tuong
                    // mCodeDoiTuong = arrSpecObjects.get(0).getCode();
                    doiTuongAdapter = new ObjDoiTuongAdapter(arrSpecObjects, getActivity());
                    spDoiTuong.setAdapter(doiTuongAdapter);

                    for (SpecObject specObject : arrSpecObjects) {
                        // if ("IS_SPECIAL_PRODUCT".equals(checkIsSpec)){
                        if ("SV_VN".equals(specObject.getCode())) {
                            spDoiTuong.setSelection(arrSpecObjects.indexOf(specObject));
                            spDoiTuong.setEnabled(true);

                            break;
                        }
                    }
                    doiTuongAdapter.notifyDataSetChanged();

                } else {
                    // hide form thong tin dac biet di va reload thong tin dac
                    // biet

                    CommonActivity.createAlertDialog(getActivity(),
                            getActivity().getString(R.string.notobject),
                            getActivity().getString(R.string.app_name)).show();

                    spDoiTuong.setEnabled(true);
                    mCodeDoiTuong = "";
                    mCodeDonVi = "";
                    lnTTGoiCuocDacBiet.setVisibility(View.GONE);
                    if (arrSpecObjects != null && !arrSpecObjects.isEmpty()) {
                        arrSpecObjects.clear();
                    }
                    if (doiTuongAdapter != null) {
                        doiTuongAdapter.notifyDataSetChanged();
                    }
                }
            } else {

                spDoiTuong.setEnabled(true);
                mCodeDoiTuong = "";
                mCodeDonVi = "";
                lnTTGoiCuocDacBiet.setVisibility(View.GONE);
                if (arrSpecObjects != null && !arrSpecObjects.isEmpty()) {
                    arrSpecObjects.clear();
                }
                if (doiTuongAdapter != null) {
                    doiTuongAdapter.notifyDataSetChanged();
                }

                if (errorCode.equals(Constant.INVALID_TOKEN2)) {

                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                            context.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else if (errorCode.equalsIgnoreCase("1")) {

                    if (description == null || description.isEmpty()) {
                        description = context.getString(R.string.checkdes1);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                            getResources().getString(R.string.app_name));
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = context.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                            getResources().getString(R.string.app_name));
                    dialog.show();
                }
            }
        }

        private ArrayList<SpecObject> getLstObjectSpec(String productCode) {
            ArrayList<SpecObject> arrayList = new ArrayList<SpecObject>();
            String original = null;
            try {

                if (!CommonActivity.isNullOrEmptyArray(arrSpecObjects)) {
                    arrayList = arrSpecObjects;
                    errorCode = "0";
                    return arrayList;
                }

                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode",
                        "mbccs_getListObjectByProductCodeBccs2");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getListObjectByProductCodeBccs2>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<String, String>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));

                if (!CommonActivity.isNullOrEmpty(subType) && "1".equals(subType)) {
                    rawData.append("<subType>" + "1");
                    rawData.append("</subType>");
                } else {
                    rawData.append("<subType>" + "");
                    rawData.append("</subType>");
                }

                rawData.append("<productCode>" + productCode);
                rawData.append("</productCode>");

                rawData.append("</input>");
                rawData.append("</ws:getListObjectByProductCodeBccs2>");

                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
                        "mbccs_getListObjectByProductCodeBccs2");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);

                original = output.getOriginal();
                Log.i("original 69696", "original :" + original);

                // === parrse xml =====
                Serializer serializer = new Persister();
                SaleOutput saleOutput = serializer.read(SaleOutput.class, original);

                if (saleOutput != null) {
                    errorCode = saleOutput.getErrorCode();
                    description = saleOutput.getDescription();

                    arrayList = saleOutput.getLstObject();

                }

            } catch (Exception e) {
                Log.e("Exception", e.toString(), e);
            }
            return arrayList;
        }
    }

    public class GetLisDonVi extends AsyncTask<Void, Void, ArrayList<DoiTuongObj>> {

        private final String maDonVi;
        private final String tenDonVi;
        private final ProgressDialog dialog;
        XmlDomParse parse = new XmlDomParse();
        private String codeDoiTuong;

        public GetLisDonVi(String maDonVi, String tenDonVi, String codeDoiTuong) {
            this.maDonVi = maDonVi;
            this.tenDonVi = tenDonVi;
            this.codeDoiTuong = codeDoiTuong;
            dialog = new ProgressDialog(getActivity());
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage(getResources().getString(R.string.waitting));
            this.dialog.show();
            super.onPreExecute();
        }

        @Override
        protected ArrayList<DoiTuongObj> doInBackground(Void... params) {

            return getDSDonvi(maDonVi, tenDonVi, codeDoiTuong);
        }

        @Override
        protected void onPostExecute(ArrayList<DoiTuongObj> result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            super.onPostExecute(result);
            try {
                if (result == null || result.equals("")) {
                    mAratListDonVi = new ArrayList<DoiTuongObj>();
                    donviadapter = new DonViAdapter(getActivity(), mAratListDonVi);
                    lvListDonVi.setAdapter(donviadapter);
                    if (donviadapter != null) {
                        donviadapter.notifyDataSetChanged();
                    }

                    CommonActivity
                            .createAlertDialog(getActivity(), getResources().getString(R.string.ko_tim_thay_don_vi_nao),
                                    getResources().getString(R.string.app_name))
                            .show();
                } else {
                    mAratListDonVi = new ArrayList<DoiTuongObj>();
                    mAratListDonVi.addAll(result);
                    donviadapter = new DonViAdapter(getActivity(), mAratListDonVi);
                    lvListDonVi.setAdapter(donviadapter);

                }
            } catch (Exception e) {
                // TODO: handle exception
                mAratListDonVi = new ArrayList<DoiTuongObj>();
                donviadapter = new DonViAdapter(getActivity(), mAratListDonVi);
                lvListDonVi.setAdapter(donviadapter);
                if (donviadapter != null) {
                    donviadapter.notifyDataSetChanged();
                }

                CommonActivity
                        .createAlertDialog(getActivity(), getResources().getString(R.string.ko_tim_thay_don_vi_nao),
                                getResources().getString(R.string.app_name))
                        .show();
            }

        }

        private ArrayList<DoiTuongObj> getDSDonvi(
                String depcode, String deptName, String codeDoiTuong) {

            ArrayList<DoiTuongObj> arrDoituong = new ArrayList<DoiTuongObj>();
            String original = "";
            try {

//          BCCSGateway input = new BCCSGateway();
//             input.addValidateGateway("username", Constant.BCCSGW_USER);
//             input.addValidateGateway("password", Constant.BCCSGW_PASS);
//             input.addValidateGateway("wscode", "mbccs_getListDepByObject");
//             StringBuilder rawData = new StringBuilder();
//             rawData.append("<ws:getListDepByObject>");
//             rawData.append("<cmInput>");
//             rawData.append("<token>" + Session.getToken() + "</token>");
//             rawData.append("<deptCode>" + depcode + "</deptCode>");
//             rawData.append("<deptName>" + deptName + "</deptName>");
//             rawData.append("<objectCode>" + codeDoiTuong + "</objectCode>");
//             rawData.append("</cmInput>");
//             rawData.append("</ws:getListDepByObject>");
//             String envelope = input.buildInputGatewayWithRawData(rawData
//                   .toString());
//             Log.d("Send evelop", envelope);
//             Log.i("LOG", Constant.BCCS_GW_URL);
//             String response = input.sendRequest(envelope,
//                   Constant.BCCS_GW_URL, getActivity(),
//                   "mbccs_getListDepByObject");
//             Log.i("Responseeeeeeeeee", response);
//             CommonOutput output = input.parseGWResponse(response);
//
//             original = output.getOriginal();
//             Log.d("originalllllllll", original);
//             Document doc = parse.getDomElement(original);
//             NodeList nl = doc.getElementsByTagName("return");
//             NodeList nodechild = null;
//             for (int i = 0; i < nl.getLength(); i++) {
//                Element e2 = (Element) nl.item(i);
//                String errorCode = parse.getValue(e2, "errorCode");
//                Log.d("errorCode", errorCode);
//                String description = parse.getValue(e2, "description");
//                Log.d("description", description);
//                nodechild = doc.getElementsByTagName("lstDepartment");
//                for (int j = 0; j < nodechild.getLength(); j++) {
//                   Node mNode = nodechild.item(j);
//                   Element element = (Element) mNode;
//                   String id = parse.getValue(element, "id");
//                   String code = parse.getValue(element, "code");
//                   String codeName = parse.getValue(element, "codeName");
//                   int ID = Integer.parseInt(id);
//
//                   DoiTuongObj doiTuongObj = new DoiTuongObj(ID, codeName,
//                         code);
//                   arrDoituong.add(doiTuongObj);
//                }
//
                BhldDAL dal = new BhldDAL(getActivity());
                dal.open();
                arrDoituong = dal.getListDeptObjectBCCSFromCode(mCodeDoiTuong, depcode, deptName);
                dal.close();

                return arrDoituong;
//          }
            } catch (Exception e) {
                Log.d("Exception", e.toString());
            }
            return arrDoituong;
        }
    }

    // check serial hop le hay khong
    private class CheckResourceAsyn extends AsyncTask<String, Void, String> {
        private Context mContext;
        private String errorCode;
        private String description;
        private ProgressDialog progress;

        public CheckResourceAsyn(Context context) {
            this.mContext = context;
            this.progress = new ProgressDialog(this.mContext);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdatareport));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected String doInBackground(String... arg0) {
            return checkResource();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progress.dismiss();
            if ("0".equals(errorCode)) {
                CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.Serialhople),
                        getActivity().getString(R.string.app_name)).show();
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

        private String checkResource() {
            String original = "";
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_checkResource");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:checkResource>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<String, String>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));

                rawData.append("<productCode>" + subscriberDTO.getProductCode());
                rawData.append("</productCode>");
                rawData.append("<telecomServiceId>" + subscriberDTO.getTelecomServiceId());
                rawData.append("</telecomServiceId>");
                rawData.append("<newSerial>" + edit_serialsim.getText().toString().trim());
                rawData.append("</newSerial>");

                ReasonDTO reasonChangeSim = (ReasonDTO) spnReasonChangeSim.getSelectedItem();
                if (!CommonActivity.isNullOrEmpty(reasonChangeSim)
                        && !CommonActivity.isNullOrEmpty(reasonChangeSim.getReasonId())) {
                    rawData.append("<reasonIdChangeSim>" + reasonChangeSim.getReasonId());
                    rawData.append("</reasonIdChangeSim>");
                }

                rawData.append("</input>");
                rawData.append("</ws:checkResource>");

                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
                        "mbccs_checkResource");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);

                original = output.getOriginal();
                Log.i("original 69696", "original :" + original);

                // === parrse xml =====
                Serializer serializer = new Persister();
                SaleOutput saleOutput = serializer.read(SaleOutput.class, original);

                if (saleOutput != null) {
                    errorCode = saleOutput.getErrorCode();
                    description = saleOutput.getDescription();
                } else {
                    errorCode = Constant.ERROR_CODE;
                    description = getActivity().getString(R.string.no_data_return);
                }
            } catch (Exception e) {
                Log.d("checkResource", e.toString());
                errorCode = Constant.ERROR_CODE;
                description = getActivity().getString(R.string.no_data_return);
            }
            return null;
        }
    }

    private class CheckQuotaSubAsyn extends AsyncTask<String, Void, String> {
        private Context mContext;
        private String errorCode;
        private String description;
        private ProgressDialog progress;

        public CheckQuotaSubAsyn(Context context) {
            this.mContext = context;
            this.progress = new ProgressDialog(this.mContext);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdatareport));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected String doInBackground(String... arg0) {
            return checkQuotaSub();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progress.dismiss();
            if ("0".equals(errorCode)) {
                SearchCustIdentityAsyn searchCustIdentityAsyn = new SearchCustIdentityAsyn(getActivity(), "1");
                if (custTypeDTO == null) {
                    searchCustIdentityAsyn.execute(edit_sogiayto.getText().toString().trim(), "", "");
                } else {
                    searchCustIdentityAsyn.execute(edit_sogiayto.getText().toString().trim(), custTypeDTO.getCustType(), "");
                }
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

        private String checkQuotaSub() {
            String original = "";
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_checkQuotaSub");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:checkQuotaSub>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<String, String>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));

                rawData.append("<idNo>" + edit_sogiayto.getText().toString());
                rawData.append("</idNo>");
                rawData.append("<telecomServiceId>" + subscriberDTO.getTelecomServiceId());
                rawData.append("</telecomServiceId>");
                rawData.append("<payType>" + subscriberDTO.getPayType());
                rawData.append("</payType>");

                rawData.append("</input>");
                rawData.append("</ws:checkQuotaSub>");

                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
                        "mbccs_checkQuotaSub");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);

                original = output.getOriginal();
                Log.i("original 69696", "original :" + original);

                // === parrse xml =====
                Serializer serializer = new Persister();
                SaleOutput saleOutput = serializer.read(SaleOutput.class, original);

                if (saleOutput != null) {
                    errorCode = saleOutput.getErrorCode();
                    description = saleOutput.getDescription();
                } else {
                    errorCode = Constant.ERROR_CODE;
                    description = getActivity().getString(R.string.no_data_return);
                }
            } catch (Exception e) {
                Log.d("checkResource", e.toString());
                errorCode = Constant.ERROR_CODE;
                description = getActivity().getString(R.string.no_data_return);
            }
            return null;
        }
    }

    private class FindProductOfferingByListCodeAsyn extends
            AsyncTask<String, Void, ArrayList<ProductOfferingDTO>> {
        private Context mContext;
        private String errorCode;
        private String description;
        private ProgressDialog progress;

        public FindProductOfferingByListCodeAsyn(Context context) {
            this.mContext = context;
            this.progress = new ProgressDialog(this.mContext);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(
                    R.string.getdatareport));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected ArrayList<ProductOfferingDTO> doInBackground(String... arg0) {
            return findProductOfferingByListCode(arg0[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<ProductOfferingDTO> result) {
            super.onPostExecute(result);
            this.progress.dismiss();
            if ("0".equals(errorCode)) {
                if (!CommonActivity.isNullOrEmptyArray(result)) {
                    ProductOfferingDTO productOfferingDTO = result.get(0);
                    if (!CommonActivity.isNullOrEmptyArray(productOfferingDTO
                            .getLstProductSpecCharDTOs())) {
                        for (ProductSpecCharDTO item : productOfferingDTO
                                .getLstProductSpecCharDTOs()) {
                            if ("IS_SPECIAL_PRODUCT".equals(item.getCode())) {
                                spDoiTuong.setEnabled(true);
                                checkIsSpec = "IS_SPECIAL_PRODUCT";
                                GetProductSpecInfoPreAsyn getInfoPreAsyn = new GetProductSpecInfoPreAsyn(
                                        getActivity());
                                getInfoPreAsyn.execute(subscriberDTO
                                        .getProductCode());
                                break;
                            } else {
                                if ("SPEC_HISCL".equals(item.getCode())) {
                                    checkIsSpec = "SPEC_HISCL";
                                    lnGoiCuocDacBietHs
                                            .setVisibility(View.VISIBLE);
                                    break;
                                }
                                if ("SPEC_ELDER".equals(item.getCode())) {
                                    checkIsSpec = "SPEC_ELDER";
                                    break;
                                }
                                checkIsSpec = "";
                                spDoiTuong.setEnabled(true);
                                mCodeDoiTuong = "";
                                mCodeDonVi = "";
                                lnTTGoiCuocDacBiet.setVisibility(View.GONE);
                                if (arrSpecObjects != null
                                        && !arrSpecObjects.isEmpty()) {
                                    arrSpecObjects.clear();
                                }
                                if (doiTuongAdapter != null) {
                                    doiTuongAdapter.notifyDataSetChanged();
                                }
                            }

                        }

                    } else {
                        checkIsSpec = "";
                        spDoiTuong.setEnabled(true);
                        mCodeDoiTuong = "";
                        mCodeDonVi = "";
                        lnTTGoiCuocDacBiet.setVisibility(View.GONE);
                        if (arrSpecObjects != null && !arrSpecObjects.isEmpty()) {
                            arrSpecObjects.clear();
                        }
                        if (doiTuongAdapter != null) {
                            doiTuongAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    checkIsSpec = "";
                    spDoiTuong.setEnabled(true);
                    mCodeDoiTuong = "";
                    mCodeDonVi = "";
                    lnTTGoiCuocDacBiet.setVisibility(View.GONE);
                    if (arrSpecObjects != null && !arrSpecObjects.isEmpty()) {
                        arrSpecObjects.clear();
                    }
                    if (doiTuongAdapter != null) {
                        doiTuongAdapter.notifyDataSetChanged();
                    }
                }
            } else {
                if (Constant.INVALID_TOKEN2.equals(errorCode)
                        && description != null && !description.isEmpty()) {

                    Dialog dialog = CommonActivity
                            .createAlertDialog((Activity) mContext,
                                    description, mContext.getResources()
                                            .getString(R.string.app_name),
                                    moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = mContext.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(
                            getActivity(), description, getResources()
                                    .getString(R.string.app_name));
                    dialog.show();

                }
            }
        }

        private ArrayList<ProductOfferingDTO> findProductOfferingByListCode(
                String productCode) {

            String original = null;
            ArrayList<ProductOfferingDTO> arrPro = new ArrayList<ProductOfferingDTO>();
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode",
                        "mbccs_findProductOfferingByListCode");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:findProductOfferingByListCode>");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");
                rawData.append("<strValues>" + productCode + "</strValues>");
                rawData.append("</input>");
                rawData.append("</ws:findProductOfferingByListCode>");
                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope,
                        Constant.BCCS_GW_URL, getActivity(),
                        "mbccs_findProductOfferingByListCode");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);

                original = output.getOriginal();
                Log.d("originalllllllll", original);

                Serializer serializer = new Persister();
                ParseOuput parseOuput = serializer.read(ParseOuput.class,
                        original);
                if (parseOuput != null) {
                    errorCode = parseOuput.getErrorCode();
                    description = parseOuput.getDescription();
                    arrPro = parseOuput.getLstProductOfferingDTO();
                    return arrPro;
                } else {
                    errorCode = Constant.ERROR_CODE;
                    description = getActivity().getString(
                            R.string.no_data_return);
                }

            } catch (Exception e) {
                Log.d("sendOtpUntil", e.toString());
                errorCode = Constant.ERROR_CODE;
                description = getActivity().getString(R.string.no_data_return);
            }

            return null;
        }
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
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(new File(urlinstall)),
                            "application/vnd.android.package-archive");
//       intent.setDataAndType(FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileprovider", new File(urlinstall)),
//             "application/vnd.android.package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    Log.d(Constant.TAG, "fileApk path = " + urlinstall);
                    File fileApk = new File(urlinstall);
                    Uri apkUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileprovider", fileApk);
                    Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
                    intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                    ;
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//          intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }

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
                    TextView tvThueBaoChinhChu = (TextView) mView
                            .findViewById(R.id.tvThueBaoChinhChu);
                    tvThueBaoChinhChu.setText(R.string.dsthuebaodskh);
                    arrSubscriberDTO.addAll(result);
                    showSelectIsdnOwner();
                } else {
                    TextView tvThueBaoChinhChu = (TextView) mView
                            .findViewById(R.id.tvThueBaoChinhChu);
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
                            .createAlertDialog(act, description, mContext
                                    .getResources()
                                    .getString(R.string.app_name), moveLogInAct);
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
                rawData.append("<custId>" + customerDTO.getCustId()
                        + "</custId>");
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
                        if (!file.mkdirs()) {
                            file.createNewFile();
                        }
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
                e.printStackTrace();
            }
            return arrSub;
        }
    }

    private String getXmlSubSubmit() {
        StringBuilder rawData = new StringBuilder("");
        // bo sung omichanel
        if(!CommonActivity.isNullOrEmpty(omniProcessId)){
            rawData.append("<omniProcessId>" + omniProcessId);
            rawData.append("</omniProcessId>");
        }
        if(!CommonActivity.isNullOrEmpty(omniTaskId)){
            rawData.append("<omniTaskId>" + omniTaskId);
            rawData.append("</omniTaskId>");
        }
        rawData.append("<requestType>"
                + subscriberDTO.getRequestType() + "</requestType>");
        rawData.append("<newSerialChangeSim>"
                + subscriberDTO.getNewSerialChangeSim() + "</newSerialChangeSim>");
        rawData.append("<oldSerialChangeSim>" +
                subscriberDTO.getSerial() + "</oldSerialChangeSim>");
        rawData.append("<serial>"
                + subscriberDTO.getNewSerialChangeSim() + "</serial>");
        rawData.append("<isdn>" +
                subscriberDTO.getIsdn() + "</isdn>");

        return rawData.toString();
    }

    private String getXmlCusSubmit() {
        StringBuilder rawData = new StringBuilder();

        // bo sung omichanel
        if(!CommonActivity.isNullOrEmpty(omniProcessId)){
            rawData.append("<omniProcessId>" + omniProcessId);
            rawData.append("</omniProcessId>");
        }
        if(!CommonActivity.isNullOrEmpty(omniTaskId)){
            rawData.append("<omniTaskId>" + omniTaskId);
            rawData.append("</omniTaskId>");
        }
        rawData.append("<requestType>"
                + subscriberDTO.getRequestType() + "</requestType>");
        if(subscriberDTO.isMarkNotOwner()){
            if (customerDTO != null && !CommonActivity.isNullOrEmpty(customerDTO.getCustId())) {
                rawData.append("<custId>");
                rawData.append(customerDTO.getCustId() + "");
                rawData.append("</custId>");

                rawData.append("<identityNo>");
                rawData.append(customerDTO.getIdentityNo() + "");
                rawData.append("</identityNo>");

                rawData.append("<isNewCustomer>" + false);
                rawData.append("</isNewCustomer>");
                rawData.append(
                        "<signDate>" + StringUtils.convertDateToString(edit_ngaykySub.getText().toString().trim())
                                + "T00:00:00+07:00");
                rawData.append("</signDate>");
            } else {
                rawData.append(
                        "<signDate>" + StringUtils.convertDateToString(edit_ngaykySub.getText().toString().trim())
                                + "T00:00:00+07:00");
                rawData.append("</signDate>");
                rawData.append("<updateCustIdentity>" + true);
                rawData.append("</updateCustIdentity>");

                rawData.append("<isNewCustomer>" + true);
                rawData.append("</isNewCustomer>");
                rawData.append("<name>" + "" + CommonActivity.getNormalText(edit_tenKH.getText().toString().trim()));
                rawData.append("</name>");
                Log.d("name cusss", edit_tenKH.getText().toString().trim());
                rawData.append("<custType>" + "" + custTypeDTO.getCustType());
                rawData.append("</custType>");
                rawData.append("<custTypeDTO>");
                rawData.append("<custType>" + "" + custTypeDTO.getCustType());
                rawData.append("</custType>");
                rawData.append("<groupType>" + "" + custTypeDTO.getGroupType());
                rawData.append("</groupType>");
                rawData.append("</custTypeDTO>");

                if ("2".equals(custTypeDTO.getGroupType())) {
                    // truong hop khach hang cu khong chinh chu
                } else {
                    CustIdentityDTO spinTypePayper = (CustIdentityDTO) spinner_type_giay_to.getSelectedItem();
                    rawData.append("<listCustIdentity>");

                    rawData.append("<idNo>" + "" + edit_sogiayto.getText().toString().trim());
                    rawData.append("</idNo>");
                    rawData.append("<idType>" + "" + spinTypePayper.getIdType());
                    rawData.append("</idType>");

                    rawData.append("<idIssueDate>" + ""
                            + StringUtils.convertDateToString(edit_ngaycap.getText().toString())
                            + "T00:00:00Z");
                    rawData.append("</idIssueDate>");
                    rawData.append("<idIssuePlace>" + "" + CommonActivity.getNormalText(edit_noicap.getText().toString().trim()));
                    rawData.append("</idIssuePlace>");
                    rawData.append("<required>" + true);
                    rawData.append("</required>");

                    rawData.append("</listCustIdentity>");

                    SexBeans sexbean = (SexBeans) spinner_gioitinh.getSelectedItem();
                    rawData.append("<sex>" + "" + sexbean.getValues());
                    rawData.append("</sex>");
                    rawData.append("<birthDate>" + ""
                            + StringUtils.convertDateToString(edit_ngaysinh.getText().toString())
                            + "T00:00:00Z");
                    rawData.append("</birthDate>");
                    rawData.append("<custAdd>");
                    rawData.append("<province>");
                    rawData.append("<code>" + "" + province);
                    rawData.append("</code>");
                    rawData.append("</province>");

                    rawData.append("<district>");
                    rawData.append("<code>" + "" + district);
                    rawData.append("</code>");
                    rawData.append("</district>");

                    rawData.append("<precinct>");
                    rawData.append("<code>" + "" + precinct);
                    rawData.append("</code>");
                    rawData.append("</precinct>");

                    rawData.append("<streetBlock>");
                    rawData.append("<code>" + "" + to);
                    rawData.append("</code>");
                    rawData.append("</streetBlock>");

                    rawData.append("<areaCode>" + "" + province + district + precinct + to);
                    rawData.append("</areaCode>");
                    rawData.append("<fullAddress>" + "" + addressPR.toString());
                    rawData.append("</fullAddress>");

                    rawData.append("</custAdd>");

                    rawData.append("<province>" + "" + province);
                    rawData.append("</province>");
                    rawData.append("<district>" + "" + district);
                    rawData.append("</district>");
                    rawData.append("<precinct>" + "" + precinct);
                    rawData.append("</precinct>");
                    rawData.append("<streetBlock>" + "" + to);
                    rawData.append("</streetBlock>");

                    if (!CommonActivity.isNullOrEmpty(areaHomeNumberPR)) {
                        rawData.append("<home>" + "" + CommonActivity.getNormalText(areaHomeNumberPR));
                        rawData.append("</home>");
                    }

                    if (!CommonActivity.isNullOrEmpty(areaFlowPR)) {
                        rawData.append("<streetName>" + "" + CommonActivity.getNormalText(areaFlowPR));
                        rawData.append("</streetName>");
                    }

                    Spin spination = (Spin) spinner_quoctich.getSelectedItem();
                    if (!CommonActivity.isNullOrEmpty(spination)
                            && !CommonActivity.isNullOrEmpty(spination.getId())) {
                        rawData.append("<nationality>" + "" + spination.getId());
                        rawData.append("</nationality>");
                    }

                    rawData.append("<areaCode>" + "" + province + district + precinct + to);
                    rawData.append("</areaCode>");
                    rawData.append("<address>"
                            + ""
                            + CommonActivity.getNormalText(addressPR
                            .toString()));
                    rawData.append("</address>");

                    if (!CommonActivity.isNullOrEmpty(edit_note.getText().toString())) {
                        rawData.append("<description>" + "" + CommonActivity.getNormalText(edit_note.getText().toString()));
                        rawData.append("</description>");
                    }
                    rawData.append("<identityNo>" + "" + edit_sogiayto.getText().toString());
                    rawData.append("</identityNo>");
                }
            }
        } else {
            rawData.append("<custId>");
            rawData.append(subscriberDTO.getCustomerDTOInput().getCustId() + "");
            rawData.append("</custId>");
            rawData.append("<isNewCustomer>" + false);
            rawData.append("</isNewCustomer>");
        }
        return rawData.toString();
    }
	private class OnPostGetInfoCustomer implements OnPostExecuteListener<ArrayList<SpinV2>> {

		@Override
		public void onPostExecute(ArrayList<SpinV2> result, String errorCode, String description) {

			if (!CommonActivity.isNullOrEmptyArray(result)) {
				arrDoituong = result;
				SpinV2 spin = new SpinV2();
				spin.setValue(act.getString(R.string.chondoituong));
				arrDoituong.add(0, spin);
				Utils.setDataSpinner(act, arrDoituong,spnDoituong);

			} else {
				CommonActivity.createAlertDialog(act,
						getString(R.string.doituong_null),
						getString(R.string.app_name));
				return;
			}
		}
	}
}

