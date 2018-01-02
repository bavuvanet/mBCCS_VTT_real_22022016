package com.viettel.bss.viettelpos.v3.connecttionService.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter.GetListCustomerBccsAdapter;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.AccountBankDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.AccountDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustIdentityDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustTypeDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustomerDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.adapter.CustomerObjectAdapter;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.charge.object.ContractFormMngtBean;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.CreateAddressCommon;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.FragmentSearchCusTypeMobile;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.SexBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.TabThueBaoHopDongManager;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.dal.CacheDatabaseManager;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.sale.dal.BhldDAL;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.LoadMoreListView;
import com.viettel.bss.viettelpos.v4.ui.LoadMoreListView.OnLoadMoreListener;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;
import com.viettel.bss.viettelpos.v3.connecttionService.adapter.GetContractAdapterBCCS;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * thinhhq1
 * 
 */
public class TabThongTinHopDong extends Fragment implements OnClickListener {
	private Activity activity;
	private View mView;

	/**
	 * Thong tin ngan hang
	 */
	private LinearLayout lnBankAccount;
	private EditText edit_ngaynhothu;
	private EditText edthdnhothu;
	private EditText edittkoanhd;
	private EditText edttentkoan;
	private TextView txtnganhang;

	private ProgressBar prbhttthd, prbchukicuoc, prbinchitiet, prbhttbc;
	private Button btnhttthd, btnchukicuoc, btninchitiet, btnhttbc;

	private EditText edtemailtbc, edtdtcdtbc, edtdidongtbc, txtdctbc, edtdchdcuoc;

	// thong tin dai dien hop dong
	private LinearLayout lnttdaidienhd, lngiaytoxacminh;
	private EditText edtloaikhDD, edit_sogiaytoDD, edit_ngaycapDD, edtnoicap, txtdcgtxmDD, edit_tenKHDD,
			edit_ngaysinhdDD, edit_quoctich;
	private ProgressBar prbCustTypeDD;
	private Button btnRefreshCustTypeDD, btnkiemtra, btnedit;
	private Spinner spinner_type_giay_to_parent, spinner_gioitinhDD;
	private LinearLayout lnsoHD;

	/**
	 * Thong tin chi tiet hop dong
	 */
	private EditText edt_contractNo; // so hop dong
	private EditText edt_signDate; // ngay ki
	private Spinner spn_payMethodCode; // hinh thuc thanh toan
	private Spinner spn_billCycleFromCharging; // chu ky cuoc
	private Spinner spn_printMethodCode, spn_noticeCharge; // in chi tiet

	private String dateNowString = null;
	private Date dateNow = null;

	private CustIdentityDTO custIdentityDTO;
	private ArrayList<CustTypeDTO> arrCusType = new ArrayList<CustTypeDTO>();

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private ArrayList<AccountDTO> arrTractBeans = new ArrayList<AccountDTO>();
	private String contractNo = "";
	private String contractId = "";

	private ProgressBar prb_contract;

	private AccountDTO accountDTO = new AccountDTO();

	private ArrayList<SexBeans> arrSexBeans = new ArrayList<SexBeans>();
	private ArrayList<CustIdentityDTO> arrTypePaper = new ArrayList<CustIdentityDTO>();

	private ArrayList<CustTypeDTO> arrCusTypeDTO = new ArrayList<CustTypeDTO>();

	private ArrayList<Spin> arrHTTTHD = null;
	private ArrayList<Spin> arrCKC = null;
	private ArrayList<Spin> arrINCT = null;
	private ArrayList<Spin> arrHTTBC = null;

	public static final int TYPE_HTTHHD = 1;
	public static final int TYPE_CK_CUOC_HD = 2;
	public static final int TYPE_INCT_HD = 3;
	public static final int TYPE_HTTBC_HD = 4;

	private Boolean isRefreshTYPE_HTTTHD = false;
	private Boolean isRefreshTYPE_CK_CUOC_HD = false;
	private Boolean isRefreshTYPE_INCT_HD = false;
	private Boolean isRefreshTYPE_HTTBC_HD = false;

	// Contract

	private AreaObj areaProvicialContract;
	private AreaObj areaDistristContract;
	private AreaObj areaPrecintContract;
	private AreaObj areaGroupContract;
	private String areaFlowContract;
	private String areaHomeNumberContract;
	private StringBuilder addressContract;
	private String provinceContract;
	private String districtContract;
	private String precintContract;
	private String streetBlockContract;
	private String streetNameContract;
	private String homeContract;

	// dai dien hop dong
	private AreaObj areaProvicialContractPR;
	private AreaObj areaDistristContractPR;
	private AreaObj areaPrecintContractPR;
	private AreaObj areaGroupContractPR;
	private String areaFlowContractPR;
	private String areaHomeNumberContractPR;
	private StringBuilder addressContractPR;
	private String provinceContractPR;
	private String districtContractPR;
	private String precintContractPR;
	private String streetBlockContractPR;
	private String streetNameContractPR;
	private String homeContractPR;

	private CustTypeDTO custTypeDTOContractPR = null;
	private String typePaperDialogPR;

	// chon thong tin khach hang cu cho nguoi dai dien khach hang doanh nghiep
	private Dialog dialogCus;

	// thong tin khach hang dai dien
	private ArrayList<CustIdentityDTO> arrCustIdentityDTODialog = new ArrayList<CustIdentityDTO>();
	private GetListCustomerBccsAdapter adaGetListCustomerBccsAdapterDialog;
	private CustomerDTO customerDTODialog = null;
	private CustIdentityDTO repreCustomer;

	// load more danh sach hop dong
	private Dialog dialogLoadMore;
	private LoadMoreListView loadMoreListView;
	private GetContractAdapterBCCS getContractAdapterBCCS;
	private int pageIndex = 0;

	private TextView edtsubscriber;
	public String accountGline;

	// doi tuong hop dong truyen doi voi dau noi mobile tra sau
	public static  AccountDTO accountDTOMain;

	private Button btncontract;

	private Button btnclearTextCustType;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// lay thong doi tuong customer cu truyen vao de lay ra thong tin hop
		// dong

		if (TabThueBaoHopDongManager.instance != null) {
			if (TabThueBaoHopDongManager.instance.custIdentityDTO != null) {
				custIdentityDTO = TabThueBaoHopDongManager.instance.custIdentityDTO;
				arrCusTypeDTO = TabThueBaoHopDongManager.instance.arrCustTypeDTOs;
				accountGline = TabThueBaoHopDongManager.instance.accountGline;
			}
		}

		Calendar calendar = Calendar.getInstance();
		int fromYear = calendar.get(Calendar.YEAR);
		int fromMonth = calendar.get(Calendar.MONTH) + 1;
		int fromDay = calendar.get(Calendar.DAY_OF_MONTH);
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
		try {
			dateNow = sdf.parse(dateNowString);
		} catch (Exception e) {
			Log.e("Exception", e.toString(), e);
		}

		if (mView == null) {
			mView = inflater.inflate(R.layout.connection_contract, container, false);
			unit(mView);
		} else {
//			((ViewGroup) mView.getParent()).removeAllViews();

//			if(!CommonActivity.isNullOrEmpty(arrTractBeans)){
//				pageIndex = 0;
//				if (custIdentityDTO != null) {
//					if (CommonActivity.isNetworkConnected(activity)) {
//						GetConTractAsyn getConTractAsyn = new GetConTractAsyn(activity);
//						getConTractAsyn.execute(custIdentityDTO.getCustomer().getCustId() + "",
//								custIdentityDTO.getIdNo(), custIdentityDTO.getIdNo());
//					} else {
//						CommonActivity.createAlertDialog(activity, getString(R.string.errorNetwork),
//								getString(R.string.app_name)).show();
//					}
//				}
//			}

		}
		return mView;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case 11112:
				areaProvicialContract = (AreaObj) data.getExtras().getSerializable("areaProvicial");
				areaDistristContract = (AreaObj) data.getExtras().getSerializable("areaDistrist");

				areaPrecintContract = (AreaObj) data.getExtras().getSerializable("areaPrecint");
				areaGroupContract = (AreaObj) data.getExtras().getSerializable("areaGroup");

				areaFlowContract = data.getExtras().getString("areaFlow");
				areaHomeNumberContract = data.getExtras().getString("areaHomeNumber");

				addressContract = new StringBuilder();

				if (areaHomeNumberContract != null && areaHomeNumberContract.length() > 0) {
					addressContract.append(areaHomeNumberContract + " ");
				}
				if (areaFlowContract != null && areaFlowContract.length() > 0) {
					addressContract.append(areaFlowContract + " ");
				}
				if (areaGroupContract != null && areaGroupContract.getName() != null
						&& areaGroupContract.getName().length() > 0) {
					addressContract.append(areaGroupContract.getName() + " ");
					streetBlockContract = areaGroupContract.getAreaCode();
				} else {
					streetBlockContract = "";
				}
				if (areaPrecintContract != null && areaPrecintContract.getName() != null
						&& areaPrecintContract.getName().length() > 0) {

					addressContract.append(areaPrecintContract.getName() + " ");
					precintContract = areaPrecintContract.getPrecinct();
				} else {
					precintContract = "";
				}
				if (areaDistristContract != null && areaDistristContract.getName() != null
						&& areaDistristContract.getName().length() > 0) {

					addressContract.append(areaDistristContract.getName() + " ");
					districtContract = areaDistristContract.getDistrict();
				} else {
					districtContract = "";
				}
				if (areaProvicialContract != null && areaProvicialContract.getName() != null
						&& areaProvicialContract.getName().length() > 0) {

					addressContract.append(areaProvicialContract.getName());
					provinceContract = areaProvicialContract.getProvince();
				} else {
					provinceContract = "";
				}
				txtdctbc.setText(addressContract);
				edtdchdcuoc.setText(addressContract);
				break;
			case 11113:
				areaProvicialContractPR = (AreaObj) data.getExtras().getSerializable("areaProvicial");
				areaDistristContractPR = (AreaObj) data.getExtras().getSerializable("areaDistrist");

				areaPrecintContractPR = (AreaObj) data.getExtras().getSerializable("areaPrecint");
				areaGroupContractPR = (AreaObj) data.getExtras().getSerializable("areaGroup");

				areaFlowContractPR = data.getExtras().getString("areaFlow");
				areaHomeNumberContractPR = data.getExtras().getString("areaHomeNumber");

				addressContractPR = new StringBuilder();

				if (areaHomeNumberContractPR != null && areaHomeNumberContractPR.length() > 0) {
					addressContractPR.append(areaHomeNumberContractPR + " ");
				}
				if (areaFlowContractPR != null && areaFlowContractPR.length() > 0) {
					addressContractPR.append(areaFlowContractPR + " ");
				}
				if (areaGroupContractPR != null && areaGroupContractPR.getName() != null
						&& areaGroupContractPR.getName().length() > 0) {
					addressContractPR.append(areaGroupContractPR.getName() + " ");
					streetBlockContractPR = areaGroupContractPR.getAreaCode();
				} else {
					streetBlockContractPR = "";
				}
				if (areaPrecintContractPR != null && areaPrecintContractPR.getName() != null
						&& areaPrecintContractPR.getName().length() > 0) {

					addressContractPR.append(areaPrecintContractPR.getName() + " ");
					precintContractPR = areaPrecintContractPR.getPrecinct();
				} else {
					precintContractPR = "";
				}
				if (areaDistristContractPR != null && areaDistristContractPR.getName() != null
						&& areaDistristContractPR.getName().length() > 0) {

					addressContractPR.append(areaDistristContractPR.getName() + " ");
					districtContractPR = areaDistristContractPR.getDistrict();
				} else {
					districtContractPR = "";
				}
				if (areaProvicialContractPR != null && areaProvicialContractPR.getName() != null
						&& areaProvicialContractPR.getName().length() > 0) {

					addressContractPR.append(areaProvicialContractPR.getName());
					provinceContractPR = areaProvicialContractPR.getProvince();
				} else {
					provinceContractPR = "";
				}
				txtdcgtxmDD.setText(addressContractPR);

				break;
			case 1444:
				if (data != null) {
					custTypeDTOContractPR = (CustTypeDTO) data.getExtras().getSerializable("cusTypeDTOsKey");
					if (custTypeDTOContractPR != null
							&& !CommonActivity.isNullOrEmpty(custTypeDTOContractPR.getCustType())) {
						edtloaikhDD.setText(custTypeDTOContractPR.getName());
						// lay danh sach loại giay to theo loai khach hang
						GetMappingCustIdentityUsageAsyn getMappingCustIdentityUsageAsyn = new GetMappingCustIdentityUsageAsyn(
								getActivity(), null, null, spinner_type_giay_to_parent);
						getMappingCustIdentityUsageAsyn.execute(custTypeDTOContractPR.getCustType());
					} else {
						edtloaikhDD.setText("");
					}
				}

				break;
			default:
				break;
			}

		}

	}

	public void unit(View v) {
		btnclearTextCustType = (Button) v.findViewById(R.id.btnclearTextCustType);
		btnclearTextCustType.setVisibility(View.GONE);
		btncontract = (Button) v.findViewById(R.id.btncontract);
		prb_contract = (ProgressBar) v.findViewById(R.id.prb_contract);
		btncontract.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pageIndex = 0;
				if (custIdentityDTO != null) {
					if (CommonActivity.isNetworkConnected(activity)) {
						GetConTractAsyn getConTractAsyn = new GetConTractAsyn(activity);
						getConTractAsyn.execute(custIdentityDTO.getCustomer().getCustId() + "",
								custIdentityDTO.getIdNo(), custIdentityDTO.getIdNo());
					} else {
						CommonActivity.createAlertDialog(activity, getString(R.string.errorNetwork),
								getString(R.string.app_name)).show();
					}
				}
			}
		});
		btnedit = (Button) v.findViewById(R.id.btnedit);
		btnedit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				disenableRepresentCus();
			}
		});

		spinner_gioitinhDD = (Spinner) v.findViewById(R.id.spinner_gioitinh);

		if (arrSexBeans != null && arrSexBeans.size() > 0) {
			arrSexBeans = new ArrayList<SexBeans>();
		}
		initSex();

		spinner_type_giay_to_parent = (Spinner) v.findViewById(R.id.spinner_type_giay_to_parent);
		spinner_type_giay_to_parent.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (arrTypePaper != null && !arrTypePaper.isEmpty()) {
					typePaperDialogPR = arrTypePaper.get(arg2).getIdType();
				} else {
					typePaperDialogPR = "";
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		edtsubscriber = (TextView) v.findViewById(R.id.edtsubscriber);
		edtsubscriber.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (arrTractBeans != null && !arrTractBeans.isEmpty()) {
					dialogLoadMore = null;
					showDiaLogLoadMoreContract(arrTractBeans);
				}

			}
		});
		prbhttthd = (ProgressBar) v.findViewById(R.id.prbhttthd);
		prbchukicuoc = (ProgressBar) v.findViewById(R.id.prbchukicuoc);
		prbinchitiet = (ProgressBar) v.findViewById(R.id.prbinchitiet);
		prbhttbc = (ProgressBar) v.findViewById(R.id.prbhttbc);
		btnhttthd = (Button) v.findViewById(R.id.btnhttthd);
		btnhttthd.setOnClickListener(this);
		btnchukicuoc = (Button) v.findViewById(R.id.btnchukicuoc);
		btnchukicuoc.setOnClickListener(this);
		btninchitiet = (Button) v.findViewById(R.id.btninchitiet);
		btninchitiet.setOnClickListener(this);
		btnhttbc = (Button) v.findViewById(R.id.btnhttbc);
		btnhttbc.setOnClickListener(this);
		lnsoHD = (LinearLayout) v.findViewById(R.id.lnsoHD);
		// so hop dong
		edt_contractNo = (EditText) v.findViewById(R.id.edtsohd);
		// ngay ky hop dong
		edt_signDate = (EditText) v.findViewById(R.id.edit_ngayky);
		edt_signDate.setText(dateNowString);
		edt_signDate.setOnClickListener(editTextListener);
		// chu ky cuoc
		spn_billCycleFromCharging = (Spinner) v.findViewById(R.id.spinner_chukicuoc);
		// hinh thuc thanh toan
		spn_payMethodCode = (Spinner) v.findViewById(R.id.spinner_httthd);
		// hinh thuc thong bao cuoc spinner_httbc
		spn_payMethodCode.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Spin item = (Spin) spn_payMethodCode.getItemAtPosition(arg2);

				if ("02".equalsIgnoreCase(item.getId()) || "03".equalsIgnoreCase(item.getId())) {
					lnBankAccount.setVisibility(View.VISIBLE);
				} else {
					lnBankAccount.setVisibility(View.GONE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		// hinh thuc thong bao cuoc
		spn_noticeCharge = (Spinner) v.findViewById(R.id.spinner_httbc);

		/**
		 * Thong tin ngan hang
		 */
		lnBankAccount = (LinearLayout) v.findViewById(R.id.lnhinhthuctthd);
		lnBankAccount.setVisibility(View.GONE);
		edit_ngaynhothu = (EditText) v.findViewById(R.id.edit_ngaynhothu); // ngay
		edit_ngaynhothu.setText(dateNowString);
		edit_ngaynhothu.setOnClickListener(editTextListener);
		edthdnhothu = (EditText) v.findViewById(R.id.edthdnhothu); // hop
		edittkoanhd = (EditText) v.findViewById(R.id.edittkoanhd); // tai
		edttentkoan = (EditText) v.findViewById(R.id.edttentkoan); // ten
		txtnganhang = (TextView) v.findViewById(R.id.txtnganhang); // chon
		txtnganhang.setText(getActivity().getString(R.string.nganhang));
		txtnganhang.setOnClickListener(this);
		spn_printMethodCode = (Spinner) v.findViewById(R.id.spinner_inchitiet); // in
		// chi
		// tiet

		edtemailtbc = (EditText) v.findViewById(R.id.edtemailtbc);
		edtdtcdtbc = (EditText) v.findViewById(R.id.edtdtcdtbc);
		edtdidongtbc = (EditText) v.findViewById(R.id.txtdtdidongtbc);
		txtdctbc = (EditText) v.findViewById(R.id.txtdctbc);
		edtdchdcuoc = (EditText) v.findViewById(R.id.edtdchdcuoc);
		if(!CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance) && !CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.custIdentityDTO)){
			txtdctbc.setText(TabThueBaoHopDongManager.instance.custIdentityDTO.getCustomer().getAddress());
			edtdchdcuoc.setText(TabThueBaoHopDongManager.instance.custIdentityDTO.getCustomer().getAddress());
			provinceContract = TabThueBaoHopDongManager.instance.custIdentityDTO.getCustomer().getProvince();
			districtContract = TabThueBaoHopDongManager.instance.custIdentityDTO.getCustomer().getDistrict();
			precintContract  = TabThueBaoHopDongManager.instance.custIdentityDTO.getCustomer().getPrecinct();
			if(!CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.custIdentityDTO.getCustomer().getStreetBlock())){
				streetBlockContract = TabThueBaoHopDongManager.instance.custIdentityDTO.getCustomer().getStreetBlock();
			}else{
				streetBlockContract = "";
			}
			if(!CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.custIdentityDTO.getCustomer().getStreet())){
				streetNameContract = TabThueBaoHopDongManager.instance.custIdentityDTO.getCustomer().getStreet();
			}
			if(!CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.custIdentityDTO.getCustomer().getHome())){
				homeContract = TabThueBaoHopDongManager.instance.custIdentityDTO.getCustomer().getHome();
			}
//			areaStreet = mBundle.getString("areaFlow");
//			areaHomeNumber = mBundle.getString("areaHomeNumber");

		}

		txtdctbc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String strProvince1 = provinceContract;
				String strDistris1 = districtContract;
				String strPrecint1 = precintContract;
				String strStreetBlock1 = streetBlockContract;
				Bundle mBundle1 = new Bundle();
				mBundle1.putString("strProvince", strProvince1);
				mBundle1.putString("strDistris", strDistris1);
				mBundle1.putString("strPrecint", strPrecint1);
				mBundle1.putString("strStreetBlock", strStreetBlock1);
				mBundle1.putString("areaFlow",streetNameContract);
				mBundle1.putString("areaHomeNumber",homeContract);
				mBundle1.putBoolean("isCheckStreetBlock", true);
				mBundle1.putString("KEYPR", "1111");
				Intent i1 = new Intent(getActivity(), CreateAddressCommon.class);
				i1.putExtras(mBundle1);
				startActivityForResult(i1, 11112);
			}
		});
		


		// thong tin dai dien hop dong
		lnttdaidienhd = (LinearLayout) v.findViewById(R.id.lnttdaidienhd);
		// khach hang doanh nghiep
		if ("2".equals(custIdentityDTO.getGroupType())) {
			lnttdaidienhd.setVisibility(View.VISIBLE);
		} else {
			lnttdaidienhd.setVisibility(View.GONE);
		}

		lngiaytoxacminh = (LinearLayout) v.findViewById(R.id.lngiaytoxacminh);

		edtloaikhDD = (EditText) v.findViewById(R.id.edtloaikh);
		edtloaikhDD.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(activity, FragmentSearchCusTypeMobile.class);
//				intent.putExtra("arrCustTypeDTOsKey", arrCusType);
				Bundle mBundle = new Bundle();
				mBundle.putString("GROUPKEY","GROUPKEY");
				intent.putExtras(mBundle);
				startActivityForResult(intent, 1444);

			}
		});

		edit_sogiaytoDD = (EditText) v.findViewById(R.id.edit_sogiaytoDD);
		edit_ngaycapDD = (EditText) v.findViewById(R.id.edit_ngaycap);
		edit_ngaycapDD.setText(dateNowString);
		edit_ngaycapDD.setOnClickListener(editTextListener);
		edtnoicap = (EditText) v.findViewById(R.id.edtnoicap);
		txtdcgtxmDD = (EditText) v.findViewById(R.id.txtdcgtxm);
		txtdcgtxmDD.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String strProvince1 = Session.province;
				String strDistris1 = Session.district;

				Bundle mBundle1 = new Bundle();
				mBundle1.putString("strProvince", strProvince1);
				mBundle1.putString("strDistris", strDistris1);
				mBundle1.putString("KEYPR", "1111");
				Intent i1 = new Intent(getActivity(), CreateAddressCommon.class);
				i1.putExtras(mBundle1);
				startActivityForResult(i1, 11113);
			}
		});
		edit_tenKHDD = (EditText) v.findViewById(R.id.edit_tenKHDD);
		edit_ngaysinhdDD = (EditText) v.findViewById(R.id.edit_ngaysinhd);
		edit_ngaysinhdDD.setText(dateNowString);
		edit_ngaysinhdDD.setOnClickListener(editTextListener);
		edit_quoctich = (EditText) v.findViewById(R.id.edit_quoctich);
		prbCustTypeDD = (ProgressBar) v.findViewById(R.id.prbCustTypeDD);
		btnRefreshCustTypeDD = (Button) v.findViewById(R.id.btnRefreshCustType);
		btnkiemtra = (Button) v.findViewById(R.id.btnkiemtra);
		btnkiemtra.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (CommonActivity.isNullOrEmpty(edit_sogiaytoDD.getText().toString().trim())) {

					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.chua_nhap_giay_to),
							getActivity().getString(R.string.app_name)).show();
					return;

				}
				SearchCustIdentityAsyn searchCustIdentityAsyn = new SearchCustIdentityAsyn(getActivity(), "1");
//				if (custTypeDTOContractPR != null) {
//					searchCustIdentityAsyn.execute(edit_sogiaytoDD.getText().toString().trim(),
//							custTypeDTOContractPR.getCustType(), typePaperDialogPR);
//				} else {
					searchCustIdentityAsyn.execute(edit_sogiaytoDD.getText().toString().trim(), "", "");
//				}

			}
		});
		// lay thong tin danh sach hop dong
		if (custIdentityDTO != null) {
			if (CommonActivity.isNetworkConnected(activity)) {
				pageIndex = 0;
				GetConTractAsyn getConTractAsyn = new GetConTractAsyn(activity);
				getConTractAsyn.execute(custIdentityDTO.getCustomer().getCustId() + "", custIdentityDTO.getIdNo(),
						custIdentityDTO.getIdNo());
			} else {
				CommonActivity
						.createAlertDialog(activity, getString(R.string.errorNetwork), getString(R.string.app_name))
						.show();
			}
		}
//		AsyntaskGetListAllCommon asyntaskGetListAllCommon = new AsyntaskGetListAllCommon(activity, TYPE_HTTHHD);
//		asyntaskGetListAllCommon.execute();

		Button btn_insert_contract = (Button) v.findViewById(R.id.btn_insert_contract_offer);
		btn_insert_contract.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// xu ly them moi hop dong o day

				try {
					if (validateContract(accountDTO)) {
						// hop dong cu
						if (accountDTO != null && accountDTO.getAccountId() != null
								&& !CommonActivity.isNullOrEmpty(accountDTO.getAccountNo())) {
							accountDTOMain = accountDTO;
						} else {
							accountDTOMain = new AccountDTO();
							accountDTOMain.setSignDate(
									StringUtils.convertDateToString(edt_signDate.getText().toString().trim()));
							Spin spinCharge = (Spin) spn_billCycleFromCharging.getSelectedItem();
							accountDTOMain.setBillCycleId(Long.parseLong(spinCharge.getId()));
							Spin spinPay = (Spin) spn_payMethodCode.getSelectedItem();
							accountDTOMain.setPayMethod(spinPay.getId());
							accountDTOMain.setPayMethodName(spinPay.getValue());
							Spin spinNotice = (Spin) spn_noticeCharge.getSelectedItem();
							accountDTOMain.setNoticeCharge(spinNotice.getId());
							accountDTOMain.setNoticeChargeName(spinNotice.getValue());
							if ("02".equalsIgnoreCase(spinPay.getId()) || "03".equalsIgnoreCase(spinPay.getId())) {

								AccountBankDTO accountBank = new AccountBankDTO();

								if (!CommonActivity.isNullOrEmpty(edittkoanhd.getText().toString().trim())) {
									accountBank.setAccount(edittkoanhd.getText().toString().trim());
								}
								if (!CommonActivity.isNullOrEmpty(edttentkoan.getText().toString().trim())) {
									accountBank.setAccountName(edttentkoan.getText().toString());
								}
								if (!CommonActivity.isNullOrEmpty(bankBean.getCode())) {
									accountBank.setBankCode(bankBean.getCode());
								}
								
								if (!CommonActivity.isNullOrEmpty(bankBean.getName())) {
									accountBank.setBankName(bankBean.getName());
								}
								if (!CommonActivity.isNullOrEmpty(edit_ngaynhothu.getText().toString())) {
									accountBank.setBankAccountDate(
											StringUtils.convertDateToString(edit_ngaynhothu.getText().toString()));
								}
								
								if (!CommonActivity.isNullOrEmpty(edthdnhothu.getText().toString())) {
									accountBank.setBankAccountNo(
											StringUtils.convertDateToString(edthdnhothu.getText().toString()));
								}
								
								accountDTOMain.setAccountBank(accountBank);
							}
							Spin spininchitiet = (Spin) spn_printMethodCode.getSelectedItem();
							accountDTOMain.setPrintMethod(spininchitiet.getId());
							accountDTOMain.setPrintMethodName(spininchitiet.getValue());
							if (!CommonActivity.isNullOrEmpty(edtemailtbc.getText().toString())) {
								accountDTOMain.seteMail(edtemailtbc.getText().toString());
							}
							if (!CommonActivity.isNullOrEmpty(edtdidongtbc.getText().toString().trim())) {
								accountDTOMain.setTelMobile(edtdidongtbc.getText().toString().trim());
							}
							accountDTOMain.setProvince(provinceContract);
							accountDTOMain.setDistrict(districtContract);
							accountDTOMain.setPrecinct(precintContract);
							accountDTOMain.setStreetBlock(streetBlockContract);
							accountDTOMain.setPhoneContact(edtdtcdtbc.getText().toString().trim());
							if (!CommonActivity.isNullOrEmpty(streetNameContract)) {
								accountDTOMain.setStreetName(streetNameContract);
							}
							if (!CommonActivity.isNullOrEmpty(homeContract)) {
								accountDTOMain.setHome(homeContract);
							}
							accountDTOMain.setAreaCode(
									provinceContract + districtContract + precintContract + streetBlockContract);
							accountDTOMain.setAddress(txtdctbc.getText().toString());
							accountDTOMain.setBillAddress(txtdctbc.getText().toString());
							accountDTOMain.setAddressPrint(edtdchdcuoc.getText().toString());
						}
						// thong tin khach hang dai dien cu voi hop dong cu co
						if ("2".equals(custIdentityDTO.getGroupType())) {
							if (accountDTO.getRefCustomer() != null
									&& accountDTO.getRefCustomer().getCustId() != null) {
								accountDTOMain.setRefCustomer(accountDTO.getRefCustomer());
							} else {
								// khi chon khach hang cu
								if (repreCustomer != null && repreCustomer.getCustomer() != null
										&& repreCustomer.getCustomer().getCustId() != null) {
									accountDTOMain.setRefCustomer(repreCustomer.getCustomer());
								} else {
									// thong tin khach hang dai dien moi
									CustomerDTO cusDTO = new CustomerDTO();
									cusDTO.setCustType(custTypeDTOContractPR.getCustType());
									ArrayList<CustIdentityDTO> arrCustIndentity = new ArrayList<CustIdentityDTO>();
									CustIdentityDTO cusIdentity = new CustIdentityDTO();
									cusIdentity.setIdNo(edit_sogiaytoDD.getText().toString().trim());
									cusIdentity.setIdType(typePaperDialogPR);
									cusIdentity.setIdIssueDate(edit_ngaycapDD.getText().toString());
									cusIdentity.setIdIssuePlace(edtnoicap.getText().toString());
									arrCustIndentity.add(cusIdentity);

									cusDTO.setListCustIdentity(arrCustIndentity);
									cusDTO.setProvince(provinceContractPR);
									cusDTO.setDistrict(districtContractPR);
									cusDTO.setPrecinct(precintContractPR);
									cusDTO.setStreetBlock(streetBlockContractPR);
									cusDTO.setAreaCode(provinceContractPR + districtContractPR + precintContractPR
											+ streetBlockContractPR);
									cusDTO.setAddress(txtdcgtxmDD.getText().toString());
									cusDTO.setStreetName(streetNameContractPR);
									cusDTO.setHome(homeContractPR);

									cusDTO.setName(edit_tenKHDD.getText().toString().trim());
									cusDTO.setBirthDate(edit_ngaysinhdDD.getText().toString());

									SexBeans sexBean = (SexBeans) arrSexBeans
											.get(spinner_gioitinhDD.getSelectedItemPosition());
									cusDTO.setSex(sexBean.getValues());
									cusDTO.setNationality(edit_quoctich.getText().toString());

									accountDTOMain.setRefCustomer(cusDTO);
								}

							}
						}
						// chuyen qua tab thong tin thue bao
						TabThueBaoHopDongManager.instance.tHost.setCurrentTab(0);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

	}

	private View.OnClickListener editTextListener = new View.OnClickListener() {

		@Override
		public void onClick(View view) {
			EditText edt = (EditText) view;
			Calendar cal = Calendar.getInstance();
			if (!CommonActivity.isNullOrEmpty(edt.getText().toString())) {
				Date date = DateTimeUtils.convertStringToTime(edt.getText().toString(), "dd/MM/yyyy");
				cal.setTime(date);

			}

			DatePickerDialog datePicker = new FixedHoloDatePickerDialog(activity, AlertDialog.THEME_HOLO_LIGHT, datePickerListener, cal.get(Calendar.YEAR),
					cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

			datePicker.getDatePicker().setTag(view);
			datePicker.show();
		}
	};

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
			Object obj = view.getTag();
			if (obj != null && obj instanceof EditText) {

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

				EditText editText = (EditText) obj;
				editText.setText(day + "/" + month + "/" + selectedYear);
			}
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnhttthd:
			new CacheDatabaseManager(activity).insertCacheList(TYPE_HTTHHD, null);
			isRefreshTYPE_HTTTHD = true;
			AsyntaskGetListAllCommon asnGetHHTH = new AsyntaskGetListAllCommon(activity, TYPE_HTTHHD);
			asnGetHHTH.execute();
			break;
		case R.id.btnchukicuoc:
			new CacheDatabaseManager(activity).insertCacheList(TYPE_CK_CUOC_HD, null);
			isRefreshTYPE_CK_CUOC_HD = true;
			AsyntaskGetListAllCommon asnGetBillCycle = new AsyntaskGetListAllCommon(activity, TYPE_CK_CUOC_HD);
			asnGetBillCycle.execute();
			break;
		case R.id.btninchitiet:
			new CacheDatabaseManager(activity).insertCacheList(TYPE_HTTHHD, null);
			isRefreshTYPE_INCT_HD = true;
			AsyntaskGetListAllCommon asnInChiTiet = new AsyntaskGetListAllCommon(activity, TYPE_INCT_HD);
			asnInChiTiet.execute();
			break;
		case R.id.btnhttbc:
			new CacheDatabaseManager(activity).insertCacheList(TYPE_HTTBC_HD, null);
			isRefreshTYPE_HTTBC_HD = true;
			AsyntaskGetListAllCommon asnHTTBC = new AsyntaskGetListAllCommon(activity, TYPE_HTTBC_HD);
			asnHTTBC.execute();
			break;
		case R.id.txtnganhang:
			showDialogListBank();
			break;
		default:
			break;
		}

	}

	private class GetConTractAsyn extends AsyncTask<String, Void, ArrayList<AccountDTO>> {

		private Context context = null;
		String errorCode = "";
		String description = "";

		public GetConTractAsyn(Context context) {
			this.context = context;
			prb_contract.setVisibility(View.VISIBLE);
		}

		@Override
		protected ArrayList<AccountDTO> doInBackground(String... params) {
			return sendRequestGetLisContract(params[0], params[1]);
		}

		@Override
		protected void onPostExecute(ArrayList<AccountDTO> result) {
			prb_contract.setVisibility(View.GONE);
			// progress.dismiss();
			arrTractBeans = new ArrayList<AccountDTO>();
			if ("0".equals(errorCode)) {

				if (result != null && result.size() > 0) {
					arrTractBeans.addAll(result);
				
				}
				initContract();
				if (dialogLoadMore == null || !dialogLoadMore.isShowing()) {
					showDiaLogLoadMoreContract(arrTractBeans);
				}
				AsyntaskGetListAllCommon asyntaskGetListAllCommon = new AsyntaskGetListAllCommon(activity, TYPE_HTTHHD);
				asyntaskGetListAllCommon.execute();
				
				// else {
				// if (arrTractBeans != null && arrTractBeans.size() > 0) {
				// arrTractBeans.clear();
				// }
				// initContract();
				// }
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {

					CommonActivity.BackFromLogin(getActivity(), description,";cm.connect_sub_bccs2;");
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(activity, description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		private ArrayList<AccountDTO> sendRequestGetLisContract(String cusId, String idNo) {
			ArrayList<AccountDTO> lstTractBeans = new ArrayList<AccountDTO>();
			String original = "";
			try {

				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_searchAccount");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:searchAccount>");
				rawData.append("<input>");
				HashMap<String, String> paramToken = new HashMap<String, String>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("<pageIndex>" + pageIndex + "</pageIndex>");
				rawData.append("<pageSize>" + 10 + "</pageSize>");

				rawData.append("<inputForSearchContract>");
				rawData.append("<custId>" + cusId);
				rawData.append("</custId>");

				rawData.append("<idNo>" + idNo);
				rawData.append("</idNo>");
				rawData.append("</inputForSearchContract>");
				rawData.append("</input>");
				rawData.append("</ws:searchAccount>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity, "mbccs_searchAccount");
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
					lstTractBeans = parseOuput.getLstAccountDTO();
				}

			} catch (Exception e) {
				Log.e("Exception", e.toString(), e);
			}

			return lstTractBeans;
		}
	}

	// move login
	OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(activity, LoginActivity.class);
			startActivity(intent);
			activity.finish();
			if (MainActivity.getInstance() != null) {
				MainActivity.getInstance().finish();
			}

		}
	};

	private void initContract() {
//		if (!CommonActivity.isNullOrEmpty(accountGline)) {
//
//		} else {
			AccountDTO conTractBean1 = new AccountDTO();
			conTractBean1.setAccountNo(TabThongTinHopDong.this.getString(R.string.contractNew));
			conTractBean1.setAccountId(null);
			arrTractBeans.add(0, conTractBean1);
//		}

	}

	// lay thong tin chi tiet hop dong cu
	private class GetAccountInforAsyn extends AsyncTask<String, Void, AccountDTO> {

		private Context context = null;
		String errorCode = "";
		String description = "";

		public GetAccountInforAsyn(Context context) {
			this.context = context;
		}

		@Override
		protected AccountDTO doInBackground(String... params) {
			return getAccountInfo(params[0]);
		}

		@Override
		protected void onPostExecute(AccountDTO result) {
			// progress.dismiss();
			accountDTO = new AccountDTO();
			if ("0".equals(errorCode)) {
				// thong tin hop dong cu
				if (result != null) {
					accountDTO = result;
					// fill thong tin hop dong cu vao day
					showPopupInsertInfoContractOffer(result, false);
					if (dialogLoadMore != null) {
						if (dialogLoadMore.isShowing()) {
							dialogLoadMore.cancel();
						}
					}

				} else {
					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notcontract),
							getActivity().getString(R.string.app_name)).show();
				}

			} else {
				if (Constant.INVALID_TOKEN2.equals(errorCode)) {

					CommonActivity.BackFromLogin(getActivity(), description,";cm.connect_sub_bccs2;");
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(activity, description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		private AccountDTO getAccountInfo(String accountId) {
			AccountDTO accountDTO = new AccountDTO();
			String original = "";
			try {

				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getAccountInfor");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getAccountInfor>");
				rawData.append("<input>");
				HashMap<String, String> paramToken = new HashMap<String, String>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));

				rawData.append("<accountId>" + accountId);
				rawData.append("</accountId>");

				rawData.append("</input>");
				rawData.append("</ws:getAccountInfor>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity, "mbccs_getAccountInfor");
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
					accountDTO = parseOuput.getAccountDTO();
				}

			} catch (Exception e) {
				Log.e("Exception", e.toString(), e);
			}

			return accountDTO;
		}
	}

	// init gioi tinh
	private void initSex() {
		arrSexBeans.add(new SexBeans(getString(R.string.male), "M"));
		arrSexBeans.add(new SexBeans(getString(R.string.female), "F"));
		if (arrSexBeans != null && arrSexBeans.size() > 0) {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
					android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
			for (SexBeans sexBeans : arrSexBeans) {
				adapter.add(sexBeans.getName());
			}
			spinner_gioitinhDD.setAdapter(adapter);
		}
	}

	private void enableContract() {
		lnsoHD.setVisibility(View.GONE);
		edt_contractNo.setEnabled(true);
		edt_contractNo.setText("");
		// ngay ky hop dong
		edt_signDate.setEnabled(true);
		edt_signDate.setText(dateNowString);
		// chu ky cuoc
		spn_billCycleFromCharging.setEnabled(true);
		// hinh thuc thanh toan
		spn_payMethodCode.setEnabled(true);
		// hinh thuc thong bao cuoc
		spn_noticeCharge.setEnabled(true);
		/**
		 * Thong tin ngan hang
		 */
		lnBankAccount.setVisibility(View.GONE);
		edit_ngaynhothu.setEnabled(true);
		edit_ngaynhothu.setText(dateNowString);// ngay
		edthdnhothu.setEnabled(true);
		edittkoanhd.setEnabled(true); // tai
		edttentkoan.setEnabled(true); // ten

		txtnganhang.setEnabled(true); // chon
		txtnganhang.setText(getActivity().getString(R.string.nganhang));
		spn_printMethodCode.setEnabled(true); // in

		edtemailtbc.setEnabled(true);
		edtdtcdtbc.setEnabled(true);
		edtdidongtbc.setEnabled(true);
		txtdctbc.setEnabled(true);
		txtdctbc.setText("");
		edtdchdcuoc.setEnabled(true);
		edtdchdcuoc.setText("");
		if(!CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance) && !CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.custIdentityDTO)){
			txtdctbc.setText(TabThueBaoHopDongManager.instance.custIdentityDTO.getCustomer().getAddress());
			edtdchdcuoc.setText(TabThueBaoHopDongManager.instance.custIdentityDTO.getCustomer().getAddress());
			provinceContract = TabThueBaoHopDongManager.instance.custIdentityDTO.getCustomer().getProvince();
			districtContract = TabThueBaoHopDongManager.instance.custIdentityDTO.getCustomer().getDistrict();
			precintContract  = TabThueBaoHopDongManager.instance.custIdentityDTO.getCustomer().getPrecinct();
		}
		// thong tin dai dien hop dong

		edtloaikhDD.setEnabled(true);
		edtloaikhDD.setText("");
		edtloaikhDD.setHint(getActivity().getString(R.string.chonloaiKH));
		edit_sogiaytoDD.setEnabled(true);
		edit_sogiaytoDD.setText("");
		edit_ngaycapDD.setEnabled(true);
		edit_ngaycapDD.setText(dateNowString);
		edtnoicap.setEnabled(true);
		edtnoicap.setText("");
		txtdcgtxmDD.setEnabled(true);
		edit_tenKHDD.setEnabled(true);
		edit_tenKHDD.setText("");
		edit_ngaysinhdDD.setEnabled(true);
		edit_ngaysinhdDD.setText(dateNowString);
		edit_quoctich.setEnabled(true);
		edit_quoctich.setText("");
		spinner_type_giay_to_parent.setEnabled(true);
		spinner_gioitinhDD.setEnabled(true);
	}

	private void disableContract() {

		btnchukicuoc.setVisibility(View.GONE);
		btnhttbc.setVisibility(View.GONE);
		btnhttthd.setVisibility(View.GONE);
		btninchitiet.setVisibility(View.GONE);

		prbchukicuoc.setVisibility(View.GONE);
		prbhttbc.setVisibility(View.GONE);
		prbhttthd.setVisibility(View.GONE);
		prbinchitiet.setVisibility(View.GONE);

		edt_contractNo.setEnabled(false);
		// ngay ky hop dong
		edt_signDate.setEnabled(false);
		// chu ky cuoc
		spn_billCycleFromCharging.setEnabled(false);
		// hinh thuc thanh toan
		spn_payMethodCode.setEnabled(false);
		// hinh thuc thong bao cuoc
		spn_noticeCharge.setEnabled(false);
		/**
		 * Thong tin ngan hang
		 */
		lnBankAccount.setVisibility(View.GONE);
		edit_ngaynhothu.setEnabled(false);
		edit_ngaynhothu.setText(dateNowString);// ngay
		edthdnhothu.setEnabled(false);
		edittkoanhd.setEnabled(false); // tai
		edttentkoan.setEnabled(false); // ten
		txtnganhang.setEnabled(false); // chon
		spn_printMethodCode.setEnabled(false); // in

		edtemailtbc.setEnabled(false);
		edtdtcdtbc.setEnabled(false);
		edtdidongtbc.setEnabled(false);
		txtdctbc.setEnabled(false);
		edtdchdcuoc.setEnabled(false);
		// thong tin dai dien hop dong

		edtloaikhDD.setEnabled(false);
		edit_sogiaytoDD.setEnabled(false);
		edit_ngaycapDD.setEnabled(false);
		edtnoicap.setEnabled(false);
		txtdcgtxmDD.setEnabled(false);
		edit_tenKHDD.setEnabled(false);
		edit_ngaysinhdDD.setEnabled(false);
		edit_ngaysinhdDD.setText(dateNowString);
		edit_quoctich.setEnabled(false);
		spinner_type_giay_to_parent.setEnabled(false);
		spinner_gioitinhDD.setEnabled(false);
	}

	// enamble lai thong tin dai dien hop dong
	private void enableRepresentCus(CustomerDTO customerDTO) {
		btnkiemtra.setVisibility(View.GONE);
		btnedit.setVisibility(View.VISIBLE);

		edit_sogiaytoDD.setText(customerDTO.getListCustIdentity().get(0).getIdNo());
		edit_sogiaytoDD.setEnabled(false);

		edit_ngaycapDD.setText(customerDTO.getListCustIdentity().get(0).getIdIssueDate());
		edit_ngaycapDD.setEnabled(false);

		edtnoicap.setText(customerDTO.getListCustIdentity().get(0).getIdIssuePlace());
		edtnoicap.setEnabled(false);

		txtdcgtxmDD.setText(customerDTO.getAddress());
		txtdcgtxmDD.setEnabled(false);

		edit_tenKHDD.setText(customerDTO.getName());
		edit_tenKHDD.setEnabled(false);

		edit_ngaysinhdDD.setText(StringUtils.convertDate(customerDTO.getBirthDate()));
		edit_ngaysinhdDD.setEnabled(false);

		if (arrSexBeans != null && arrSexBeans.size() > 0) {
			for (SexBeans item : arrSexBeans) {
				if (customerDTO.getSex().equals(item.getValues())) {
					spinner_gioitinhDD.setSelection(arrSexBeans.indexOf(item));
					spinner_gioitinhDD.setEnabled(false);
					break;
				}
			}
		}

		edit_quoctich.setText(customerDTO.getNationality());
		edit_quoctich.setEnabled(false);
	}

	// disable thong tin dai dien hop dong

	private void disenableRepresentCus() {



		btnkiemtra.setVisibility(View.VISIBLE);
		btnedit.setVisibility(View.GONE);
		if(accountDTOMain != null){
			if (accountDTOMain.getRefCustomer() != null) {
				accountDTOMain.setRefCustomer(new CustomerDTO());
			}
		}


		edit_ngaycapDD.setText(dateNowString);
		edit_ngaycapDD.setEnabled(true);
		edit_sogiaytoDD.setEnabled(true);
		edit_sogiaytoDD.setText("");
		edtnoicap.setEnabled(true);

		txtdcgtxmDD.setText(getActivity().getString(R.string.sellect_address));
		txtdcgtxmDD.setEnabled(true);

		edit_tenKHDD.setText("");
		edit_tenKHDD.setEnabled(true);

		edit_ngaysinhdDD.setText(dateNowString);
		edit_ngaysinhdDD.setEnabled(true);

		spinner_gioitinhDD.setEnabled(true);

		edit_quoctich.setText("");
		edit_quoctich.setEnabled(true);
		edtloaikhDD.setText("");
		edtloaikhDD.setHint(getActivity().getString(R.string.chonloaiKH));
		spinner_type_giay_to_parent.setEnabled(true);
		initTypePaper(new ArrayList<CustIdentityDTO>(),spinner_type_giay_to_parent);

	}

	// show thong tin hop dong cu
	private void showPopupInsertInfoContractOffer(final AccountDTO accountDTO, boolean isNew) {

		final ArrayList<CustTypeDTO> arrCusType = new ArrayList<CustTypeDTO>();
		if (arrCusTypeDTO != null && !arrCusTypeDTO.isEmpty()) {
			for (CustTypeDTO item : arrCusTypeDTO) {
				if ("2".equals(item.getGroupType())) {

				} else {
					arrCusType.add(item);
				}
			}

		}

		if (isNew) {
			lnsoHD.setVisibility(View.GONE);
		} else {
			lnsoHD.setVisibility(View.VISIBLE);
		}

		// conTractBean
		if (accountDTO != null && accountDTO.getAccountId() != null
				&& !CommonActivity.isNullOrEmpty(accountDTO.getAccountNo())) {
			AccountDTO contract = accountDTO;
			if (contract != null && contract.getAccountId() != null
					&& !CommonActivity.isNullOrEmpty(contract.getAccountNo())) {

				disableContract();
				// init giao dien
				edt_contractNo.setText(contract.getAccountNo()); // so hop dong

				edt_signDate.setText(StringUtils.convertDate(contract.getSignDate())); // ngay

				if (!CommonActivity.isNullOrEmpty(contract.getPayMethod()) && arrHTTTHD != null) {
					Utils.setDataSpinner(activity, arrHTTTHD, spn_payMethodCode);
					for (int i = 0; i < arrHTTTHD.size(); i++) {
						Spin spin = arrHTTTHD.get(i);
						if (spin.getId().equalsIgnoreCase(contract.getPayMethod())) {
							spn_payMethodCode.setSelection(i);
							break;
						}
					}
				}

				if ("02".equalsIgnoreCase(contract.getPayMethod()) || "03".equalsIgnoreCase(contract.getPayMethod())) {
					lnBankAccount.setVisibility(View.VISIBLE);
				} else {
					lnBankAccount.setVisibility(View.GONE);
				}

				// chu ky cuoc
				if (contract.getBillCycleId() != null && arrCKC != null) {
					Utils.setDataSpinner(activity, arrCKC, spn_billCycleFromCharging);
					for (int i = 0; i < arrCKC.size(); i++) {
						Spin spin = arrCKC.get(i);
						if (spin.getId().equalsIgnoreCase(String.valueOf(contract.getBillCycleId()))) {
							spn_billCycleFromCharging.setSelection(i);
							break;
						}
					}
				}
				// in chi tiet
				if (contract.getPrintMethod() != null && arrINCT != null) {
					Utils.setDataSpinner(activity, arrINCT, spn_printMethodCode);
					for (int i = 0; i < arrINCT.size(); i++) {
						Spin spin = arrINCT.get(i);
						if (spin.getId().equalsIgnoreCase(contract.getPrintMethod())) {
							spn_printMethodCode.setSelection(i);
							break;
						}
					}
				}

				// edt_streetName.setText(contract.getStreet());
				// edt_home.setText(contract.get()); // so nha
				if (!CommonActivity.isNullOrEmpty(contract.getAddressPrint())) {
					edtdchdcuoc.setText(contract.getAddressPrint());
				} else {
					edtdchdcuoc.setText(contract.getAddress());
				}

				edtdtcdtbc.setText(contract.getPhoneContact()); // dien thoai co
																// dinh
				edtdidongtbc.setText(contract.getTelMobile()); // dien thoai di
																// dong
				edtemailtbc.setText(contract.geteMail()); // email

				// hinh thuc thong bao cuoc
				if (contract.getNoticeCharge() != null && arrHTTBC != null) {
					Utils.setDataSpinner(activity, arrHTTBC, spn_noticeCharge);
					for (int i = 0; i < arrHTTBC.size(); i++) {
						Spin spin = arrHTTBC.get(i);
						if (spin.getId().equalsIgnoreCase(contract.getNoticeCharge())) {
							spn_noticeCharge.setSelection(i);
							break;
						}
					}
				}

				txtdctbc.setText(contract.getAddress()); // dia chi thong

				AccountBankDTO contractBank = contract.getAccountBank();
				if (contractBank != null) {
					edittkoanhd.setText(contractBank.getAccount()); // bank tai
					edttentkoan.setText(contractBank.getAccountName()); // bank
					txtnganhang.setText(contractBank.getBankName()); // bank ma
					edthdnhothu.setText(contractBank.getBankAccountNo());
					edit_ngaynhothu.setText(StringUtils.convertDate(contractBank.getBankAccountDate())); // bank
				}

				// xy ly thong tin dai dien hop dong cu
				if (contract.getRefCustomer() != null && contract.getRefCustomer().getCustId() != null) {
					lnttdaidienhd.setVisibility(View.VISIBLE);
					lngiaytoxacminh.setVisibility(View.VISIBLE);

					edtloaikhDD.setText(contract.getRefCustomer().getCustType());


					if (contract.getRefCustomer().getListCustIdentity() != null
							&& contract.getRefCustomer().getListCustIdentity().size() > 0) {
						edit_sogiaytoDD.setText(contract.getRefCustomer().getListCustIdentity().get(0).getIdNo());
						edit_ngaycapDD.setText(contract.getRefCustomer().getListCustIdentity().get(0).getIdIssueDate());
						edtnoicap.setText(contract.getRefCustomer().getListCustIdentity().get(0).getIdIssuePlace());
					}

					txtdcgtxmDD.setText(contract.getRefCustomer().getAddress());
					edit_tenKHDD.setText(contract.getRefCustomer().getName());
					edit_ngaysinhdDD.setText(StringUtils.convertDate(contract.getRefCustomer().getBirthDate()));
					edit_quoctich.setText(contract.getRefCustomer().getNationality());
					if (!CommonActivity.isNullOrEmpty(contract.getRefCustomer().getSex())) {
						for (SexBeans spin : arrSexBeans) {
							if (contract.getRefCustomer().getSex().equals(spin.getValues())) {
								spinner_gioitinhDD.setSelection(arrSexBeans.indexOf(spin));
								spinner_gioitinhDD.setEnabled(false);
								break;
							}
						}
					}
				}

			}

		}
	}

	private class AsyntaskGetListAllCommon extends AsyncTask<Void, Void, ArrayList<Spin>> {

		private Context context = null;
		private XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private int type = 0;

		public AsyntaskGetListAllCommon(Context context, int type) {
			this.context = context;
			this.type = type;
			switch (type) {
			case TYPE_HTTHHD:
				if (prbhttthd != null) {
					prbhttthd.setVisibility(View.VISIBLE);
				}
				if (btnhttthd != null) {
					btnhttthd.setVisibility(View.GONE);
				}
				break;
			case TYPE_CK_CUOC_HD:

				if (prbchukicuoc != null) {
					prbchukicuoc.setVisibility(View.VISIBLE);
				}
				if (btnchukicuoc != null) {
					btnchukicuoc.setVisibility(View.GONE);
				}
				break;
			case TYPE_HTTBC_HD:
				if (prbhttbc != null) {
					prbhttbc.setVisibility(View.VISIBLE);
				}
				if (btnhttbc != null) {
					btnhttbc.setVisibility(View.GONE);
				}
				break;
			case TYPE_INCT_HD:
				if (prbinchitiet != null) {
					prbinchitiet.setVisibility(View.VISIBLE);
				}
				if (btninchitiet != null) {
					btninchitiet.setVisibility(View.GONE);
				}
				break;

			default:
				break;
			}

		}

		@Override
		protected ArrayList<Spin> doInBackground(Void... arg0) {
			if (type == TYPE_HTTHHD) {
				return getListPayMethode();
			} else if (type == TYPE_CK_CUOC_HD) {
				return getListCurrBillCycleAll();
			} else if (type == TYPE_INCT_HD) {
				return getListPrintMethode();
			} else if (type == TYPE_HTTBC_HD) {
				return getListNoticeMethode();
			}
			return null;
		}

		@Override
		protected void onPostExecute(ArrayList<Spin> result) {
			// progress.dismiss();
			if (errorCode.equalsIgnoreCase("0")) {
				if (type == TYPE_HTTHHD) {

					if (prbhttthd != null) {
						prbhttthd.setVisibility(View.GONE);
					}
					if (btnhttthd != null) {
						btnhttthd.setVisibility(View.VISIBLE);
					}

					arrHTTTHD = new ArrayList<Spin>();
					arrHTTTHD.addAll(result);
					Utils.setDataSpinner(activity, result, spn_payMethodCode);
					if (!isRefreshTYPE_CK_CUOC_HD) {
						AsyntaskGetListAllCommon asyntaskGetListAllCommon4 = new AsyntaskGetListAllCommon(activity,
								TYPE_CK_CUOC_HD);
						asyntaskGetListAllCommon4.execute();
					}
				}

				if (type == TYPE_CK_CUOC_HD) {

					if (prbchukicuoc != null) {
						prbchukicuoc.setVisibility(View.GONE);
					}
					if (btnchukicuoc != null) {
						btnchukicuoc.setVisibility(View.VISIBLE);
					}

					arrCKC = new ArrayList<Spin>();
					arrCKC.addAll(result);
					Utils.setDataSpinner(activity, result, spn_billCycleFromCharging);

					if (!isRefreshTYPE_INCT_HD) {
						AsyntaskGetListAllCommon asyntaskGetListAllCommon5 = new AsyntaskGetListAllCommon(activity,
								TYPE_INCT_HD);
						asyntaskGetListAllCommon5.execute();
					}

				}
				if (type == TYPE_INCT_HD) {

					if (prbinchitiet != null) {
						prbinchitiet.setVisibility(View.GONE);
					}
					if (btninchitiet != null) {
						btninchitiet.setVisibility(View.VISIBLE);
					}

					arrINCT = new ArrayList<Spin>();
					arrINCT.addAll(result);
					Utils.setDataSpinner(activity, result, spn_printMethodCode);
					// TODO THINHHQ1 tbc
					if (!isRefreshTYPE_HTTBC_HD) {
						AsyntaskGetListAllCommon asyntaskGetListAllCommon6 = new AsyntaskGetListAllCommon(activity,
								TYPE_HTTBC_HD);
						asyntaskGetListAllCommon6.execute();
					}

				}
				if (type == TYPE_HTTBC_HD) {

					if (prbhttbc != null) {
						prbhttbc.setVisibility(View.GONE);
					}
					if (btnhttbc != null) {
						btnhttbc.setVisibility(View.VISIBLE);
					}

					arrHTTBC = new ArrayList<Spin>();
					arrHTTBC.addAll(result);
					Utils.setDataSpinner(activity, result, spn_noticeCharge);

				}

			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {

					Dialog dialog = CommonActivity.createAlertDialog(activity, description,
							context.getResources().getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					result = new ArrayList<Spin>();
					if (type == TYPE_HTTHHD) {
						arrHTTTHD = new ArrayList<Spin>();
						arrHTTTHD.addAll(result);
						Utils.setDataSpinner(activity, result, spn_payMethodCode);

						if (!CommonActivity.isNullOrEmptyArray(arrHTTTHD) && spn_payMethodCode != null) {
							for (int i = 0; i < arrHTTTHD.size(); i++) {
								Spin spin = arrHTTTHD.get(i);
								if ("00".equals(spin.getId())) {
									spn_payMethodCode.setSelection(arrHTTTHD.indexOf(spin));
									break;
								}
							}
						}

					}

					if (type == TYPE_CK_CUOC_HD) {
						arrCKC = new ArrayList<Spin>();
						arrCKC.addAll(result);
						Utils.setDataSpinner(activity, result, spn_billCycleFromCharging);
					}
					if (type == TYPE_INCT_HD) {
						arrINCT = new ArrayList<Spin>();
						arrINCT.addAll(result);
						Utils.setDataSpinner(activity, result, spn_printMethodCode);

						if (!CommonActivity.isNullOrEmptyArray(arrINCT) && spn_printMethodCode != null) {
							for (int i = 0; i < arrINCT.size(); i++) {
								Spin spin = arrINCT.get(i);
								if ("2".equals(spin.getId())) {
									spn_printMethodCode.setSelection(arrINCT.indexOf(spin));
									break;
								}
							}
						}

					}
					if (type == TYPE_HTTBC_HD) {
						arrHTTBC = new ArrayList<Spin>();
						arrHTTBC.addAll(result);
						Utils.setDataSpinner(activity, result, spn_noticeCharge);
					}

					CommonActivity.BackFromLogin(getActivity(), description,";cm.connect_sub_bccs2;");

				}
			}
		}

		// lay danh sach hinh thuc thanh toan hop dong
		private ArrayList<Spin> getListPayMethode() {
			ArrayList<Spin> lstpaymethod = new ArrayList<Spin>();
			String original = "";
			try {
				lstpaymethod = new CacheDatabaseManager(context).getListInCacheBCCS(TYPE_HTTHHD);
				if (lstpaymethod != null && !lstpaymethod.isEmpty()) {
					errorCode = "0";
					return lstpaymethod;
				}
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getLsOptionSetValueByCode");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getLsOptionSetValueByCode>");
				rawData.append("<input>");
				rawData.append("<token>" + Session.getToken() + "</token>");

				rawData.append("<code>" + "MPOS_PAY_METHOD" + "</code>");

				rawData.append("</input>");
				rawData.append("</ws:getLsOptionSetValueByCode>");
				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity,
						"mbccs_getLsOptionSetValueByCode");
				Log.i("LOG", "Respone:  " + response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("LOG", "Responseeeeeeeeee Original  " + response);

				// parser
				lstpaymethod = parserListGroup(original);
				new CacheDatabaseManager(context).insertCacheListBCCS(TYPE_HTTHHD, lstpaymethod);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return lstpaymethod;
		}

		// lay danh sach in chi tiet cuoc
		private ArrayList<Spin> getListPrintMethode() {
			ArrayList<Spin> lstprintmethod = new ArrayList<Spin>();
			String original = "";
			try {
				lstprintmethod = new CacheDatabaseManager(context).getListInCacheBCCS(TYPE_INCT_HD);
				if (lstprintmethod != null && !lstprintmethod.isEmpty()) {
					errorCode = "0";
					return lstprintmethod;
				}
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getLsOptionSetValueByCode");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getLsOptionSetValueByCode>");
				rawData.append("<input>");
				rawData.append("<token>" + Session.getToken() + "</token>");
				rawData.append("<code>" + "MPOS_PRINT_METHOD" + "</code>");
				rawData.append("</input>");
				rawData.append("</ws:getLsOptionSetValueByCode>");

				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity,
						"mbccs_getLsOptionSetValueByCode");
				Log.i("LOG", "Respone:  " + response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("LOG", "Responseeeeeeeeee Original  " + response);

				// parser

				lstprintmethod = parserListGroup(original);
				new CacheDatabaseManager(context).insertCacheListBCCS(TYPE_INCT_HD, lstprintmethod);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return lstprintmethod;
		}

		// lay danh sach hinh thuc thong bao cuoc
		private ArrayList<Spin> getListNoticeMethode() {
			ArrayList<Spin> lstNoticeMethod = new ArrayList<Spin>();
			String original = "";
			try {
				lstNoticeMethod = new CacheDatabaseManager(context).getListInCacheBCCS(TYPE_HTTBC_HD);
				if (lstNoticeMethod != null && !lstNoticeMethod.isEmpty()) {
					errorCode = "0";
					return lstNoticeMethod;
				}
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getLsOptionSetValueByCode");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getLsOptionSetValueByCode>");
				rawData.append("<input>");
				rawData.append("<token>" + Session.getToken() + "</token>");
				rawData.append("<code>" + "MPOS_NOTICE_CHARGE" + "</code>");

				rawData.append("</input>");
				rawData.append("</ws:getLsOptionSetValueByCode>");

				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity,
						"mbccs_getLsOptionSetValueByCode");
				Log.i("LOG", "Respone:  " + response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("LOG", "Responseeeeeeeeee Original  " + response);

				// parser

				lstNoticeMethod = parserListGroup(original);
				new CacheDatabaseManager(context).insertCacheListBCCS(TYPE_HTTBC_HD, lstNoticeMethod);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return lstNoticeMethod;
		}

		// lay danh sach chu ky cuoc
		private ArrayList<Spin> getListCurrBillCycleAll() {
			ArrayList<Spin> lstCurrBillCycle = new ArrayList<Spin>();
			String original = "";
			try {
				lstCurrBillCycle = new CacheDatabaseManager(context).getListInCacheBCCS(TYPE_CK_CUOC_HD);
				if (lstCurrBillCycle != null && !lstCurrBillCycle.isEmpty()) {
					errorCode = "0";
					return lstCurrBillCycle;
				}
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getAllBillCycleType");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getAllBillCycleType>");
				rawData.append("<input>");
				rawData.append("<token>" + Session.getToken() + "</token>");
				rawData.append("<status>" + 1 + "</status>");
				rawData.append("</input>");
				rawData.append("</ws:getAllBillCycleType>");

				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity,
						"mbccs_getAllBillCycleType");
				Log.i("LOG", "Respone:  " + response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("LOG", "Responseeeeeeeeee Original  " + response);

				// parser

				lstCurrBillCycle = parserBillCycle(original);
				new CacheDatabaseManager(context).insertCacheListBCCS(TYPE_CK_CUOC_HD, lstCurrBillCycle);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return lstCurrBillCycle;
		}

		public ArrayList<Spin> parserBillCycle(String original) {
			ArrayList<Spin> lstReason = new ArrayList<Spin>();
			Document doc = parse.getDomElement(original);
			NodeList nl = doc.getElementsByTagName("return");
			NodeList nodechild = null;
			for (int i = 0; i < nl.getLength(); i++) {
				Element e2 = (Element) nl.item(i);
				errorCode = parse.getValue(e2, "errorCode");
				description = parse.getValue(e2, "description");
				Log.d("errorCode", errorCode);
				nodechild = doc.getElementsByTagName("lstBillCycleTypeDTO");
				for (int j = 0; j < nodechild.getLength(); j++) {
					Element e1 = (Element) nodechild.item(j);
					Spin spin = new Spin();
					spin.setValue(parse.getValue(e1, "billCycleId"));

					spin.setId(parse.getValue(e1, "billCycleId"));

					lstReason.add(spin);
				}
			}
			return lstReason;
		}

		public ArrayList<Spin> parserListGroup(String original) {
			ArrayList<Spin> lstReason = new ArrayList<Spin>();
			Document doc = parse.getDomElement(original);
			NodeList nl = doc.getElementsByTagName("return");
			NodeList nodechild = null;
			for (int i = 0; i < nl.getLength(); i++) {
				Element e2 = (Element) nl.item(i);
				errorCode = parse.getValue(e2, "errorCode");
				description = parse.getValue(e2, "description");
				Log.d("errorCode", errorCode);
				nodechild = doc.getElementsByTagName("lstOptionSetValueDTOs");
				for (int j = 0; j < nodechild.getLength(); j++) {
					Element e1 = (Element) nodechild.item(j);
					Spin spin = new Spin();
					spin.setValue(parse.getValue(e1, "name"));

					spin.setId(parse.getValue(e1, "value"));

					lstReason.add(spin);
				}
			}
			return lstReason;
		}
	}
	// lay thong tin loai giay to dai dien
	private class GetMappingCustIdentityUsageAsyn extends AsyncTask<String, Void, ArrayList<CustIdentityDTO>> {
		private Context context = null;
		String errorCode = "";
		String description = "";
		ProgressBar prbarCus;
		Button btnRefresh;
		Spinner spinerPaper;

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
					Dialog dialog = CommonActivity.createAlertDialog(activity,
							getResources().getString(R.string.checkTypeCus),
							getResources().getString(R.string.app_name));
					dialog.show();
				}

			} else {
				if (Constant.INVALID_TOKEN2.equals(errorCode)) {

					CommonActivity.BackFromLogin(getActivity(), description,";cm.connect_sub_bccs2;");
				} else {
					if (description == null || description.equals("")) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(activity, description,
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
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity,
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

	private void initTypePaper(ArrayList<CustIdentityDTO> lstTypePaper, Spinner spinerPaper) {
		if (lstTypePaper == null) {
			lstTypePaper = new ArrayList<CustIdentityDTO>();
		}

		ArrayAdapter<String> adapter = null;
		// if (lstTypePaper != null && !lstTypePaper.isEmpty()) {
		adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line,
				android.R.id.text1);
		for (CustIdentityDTO custIdentityDTO : lstTypePaper) {
			adapter.add(custIdentityDTO.getIdTypeName());
		}
		spinerPaper.setAdapter(adapter);
		// }
	}

	private class GetMappingChannelCustTypeAsyn extends AsyncTask<String, Void, ArrayList<CustTypeDTO>> {
		private Context context = null;
		String errorCode = "";
		String description = "";

		public GetMappingChannelCustTypeAsyn(Context context) {
			this.context = context;
		}

		@Override
		protected ArrayList<CustTypeDTO> doInBackground(String... params) {
			return getMappingChannelCustType(params[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<CustTypeDTO> result) {
			arrCusTypeDTO = new ArrayList<CustTypeDTO>();
			if ("0".equals(errorCode)) {
				if (result != null && !result.isEmpty()) {
					arrCusTypeDTO = result;
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(activity,
							getResources().getString(R.string.checkTypeCus),
							getResources().getString(R.string.app_name));
					dialog.show();
				}

			} else {
				if (Constant.INVALID_TOKEN2.equals(errorCode)) {

					CommonActivity.BackFromLogin(getActivity(), description,";cm.connect_sub_bccs2;");
				} else {
					if (description == null || description.equals("")) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(activity, description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}

		}

		private ArrayList<CustTypeDTO> getMappingChannelCustType(String payType) {
			ArrayList<CustTypeDTO> lstCustTypeDTOs = new ArrayList<CustTypeDTO>();
			String original = null;
			try {

				// tra sau
				lstCustTypeDTOs = new CacheDatabaseManager(context).getListCusTypeDTO("cusTypeDTOPos");

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
				rawData.append("<payType>" + payType);
				rawData.append("</payType>");
				rawData.append("</input>");
				rawData.append("</ws:getMappingChannelCustType>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity,
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

			return lstCustTypeDTOs;
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
			arrCustIdentityDTODialog = new ArrayList<CustIdentityDTO>();
			if ("0".equals(errorCode)) {
				if (result != null) {

					if (result.getLstCustIdentityDTOs() != null && result.getLstCustIdentityDTOs().size() > 0) {
						arrCustIdentityDTODialog = result.getLstCustIdentityDTOs();

						if (!CommonActivity.isNullOrEmpty(check)) {
							showSelectCus(arrCustIdentityDTODialog);
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

					CommonActivity.BackFromLogin(getActivity(), description,";cm.connect_sub_bccs2;");
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

				repreCustomer = lstCusIdentity.get(arg2);
				AsynGetCustomerByCustId asynGetCustomerByCustId = new AsynGetCustomerByCustId(getActivity(),
						repreCustomer);
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

	private class AsynGetCustomerByCustId extends AsyncTask<String, Void, CustomerDTO> {

		private String errorCode;
		private String description;
		private Context context;
		private ProgressDialog progress;
		private CustIdentityDTO reCustIdentityDTO;

		public AsynGetCustomerByCustId(Context mContext, CustIdentityDTO custIdentityDTO) {
			this.context = mContext;
			this.reCustIdentityDTO = custIdentityDTO;
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
				customerDTODialog = new CustomerDTO();
				if (result != null && result.getCustId() != null) {
					customerDTODialog = result;
					// xu ly thong tin khach hang dai dien cu cho nay
					// reCustIdentityDTO.setCustomer(customerDTODialog);
					enableRepresentCus(customerDTODialog);
					// accountDTOMain.setRefCustomer(customerDTODialog);

					// if (dialogContract != null) {
					// dialogContract.dismiss();
					// }
					if (dialogCus != null) {
						dialogCus.dismiss();
					}
				} else {
					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notDetailCus),
							getActivity().getString(R.string.app_name)).show();
				}

			} else {
				if (Constant.INVALID_TOKEN2.equals(errorCode)) {

					CommonActivity.BackFromLogin(getActivity(), description,";cm.connect_sub_bccs2;");
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(activity, description,
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
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity,
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
				Log.e("getCustomerByCustId + exception", e.toString(), e);
			}
			return customerDTO;

		}
	}

	private LoadMoreListView lvListObjectCustomer;
	private Button btnSearchCustomerObject;
	private Button btnDeleteCustomerObject;
	private EditText edtCusCode;
	private EditText edtCusName;

	private ContractFormMngtBean searchBean;

	private CustomerObjectAdapter customerObjectAdapter;
	private ContractFormMngtBean bankBean = null;

	private List<ContractFormMngtBean> lstContractFormMngtBean = new ArrayList<ContractFormMngtBean>();

	private LinearLayout lnmanganhang, lntennganhang;

	private void showDialogListBank() {
		final Dialog dialog = new Dialog(activity);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_layout_object_bank);
		lvListObjectCustomer = (LoadMoreListView) dialog.findViewById(R.id.lvListObjectCustomer);
		btnSearchCustomerObject = (Button) dialog.findViewById(R.id.btnSearch);
		btnDeleteCustomerObject = (Button) dialog.findViewById(R.id.btnDelete);
		edtCusCode = (EditText) dialog.findViewById(R.id.edtCodeCustomer);
		edtCusName = (EditText) dialog.findViewById(R.id.edtNameCustomer);

		lnmanganhang = (LinearLayout) dialog.findViewById(R.id.lnmanganhang);
		lnmanganhang.setVisibility(View.VISIBLE);
		lntennganhang = (LinearLayout) dialog.findViewById(R.id.lntennganhang);
		lntennganhang.setVisibility(View.VISIBLE);

		lvListObjectCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				bankBean = lstContractFormMngtBean.get(position);
				txtnganhang.setText(bankBean.getName());
				dialog.dismiss();
			}
		});

		lvListObjectCustomer.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {

			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				Log.i(Constant.TAG, "lvListObjectCustomer onLoadMore");

				// AsyncContractFormMngt asynctaskContractFotmMngt = new
				// AsyncContractFormMngt(
				// searchBean, lstContractFormMngtBean.size(),
				// lstContractFormMngtBean.size() + Constant.PAGE_SIZE);
				// asynctaskContractFotmMngt.execute();
			}
		});

		btnSearchCustomerObject.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CommonActivity.hideKeyboard(v, activity);
				String customerName = edtCusName.getText().toString().trim();
				String customerCode = edtCusCode.getText().toString().trim();

				if (!CommonActivity.isNullOrEmpty(customerName) || !CommonActivity.isNullOrEmpty(customerCode)) {
					if (CommonActivity.isNetworkConnected(activity)) {
						searchBean = new ContractFormMngtBean();
						searchBean.setCode(edtCusCode.getText().toString().trim());
						searchBean.setName(edtCusName.getText().toString().trim());

						lstContractFormMngtBean.clear();
						AsyncBank asynctaskContractFotmMngt = new AsyncBank(searchBean, lstContractFormMngtBean.size(),
								lstContractFormMngtBean.size() + Constant.PAGE_SIZE);
						asynctaskContractFotmMngt.execute();
					} else {
						CommonActivity.createAlertDialog(activity, getString(R.string.errorNetwork),
								getString(R.string.app_name)).show();
					}
				} else {
					CommonActivity.createAlertDialog(activity,
							getString(R.string.message_not_input_customer_name_or_name), getString(R.string.app_name))
							.show();

				}
			}
		});

		btnDeleteCustomerObject.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				bankBean = null;
				txtnganhang.setText(getActivity().getString(R.string.chonnganhang));
				dialog.dismiss();
			}
		});

		dialog.show();

	}

	private class AsyncBank extends AsyncTask<String, Void, List<ContractFormMngtBean>> {

		private XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private ProgressDialog progress;
		private ContractFormMngtBean searchBean;

		private int pageIndex;
		private int pageSize;

		public AsyncBank(ContractFormMngtBean _searchBean, int _pageIndex, int _pageSize) {
			this.searchBean = _searchBean;
			this.pageIndex = _pageIndex;
			this.pageSize = _pageSize;

			this.progress = new ProgressDialog(activity);
			this.progress.setCancelable(false);

			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		};

		@Override
		protected List<ContractFormMngtBean> doInBackground(String... params) {

			return getBankByCode();
		}

		@Override
		protected void onPostExecute(List<ContractFormMngtBean> result) {
			super.onPostExecute(result);

			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result != null && result.size() > 0) {
					// them danh sach
					lstContractFormMngtBean.addAll(result);
					customerObjectAdapter = new CustomerObjectAdapter(activity, lstContractFormMngtBean);
					lvListObjectCustomer.setAdapter(customerObjectAdapter);
					customerObjectAdapter.notifyDataSetChanged();
				} else {
					if (lstContractFormMngtBean.isEmpty()) {
						lvListObjectCustomer.setAdapter(null);
						CommonActivity.createAlertDialog(activity, getString(R.string.notnganhang),
								getString(R.string.app_name)).show();
					}
				}
				Log.d(Constant.TAG, "onPostExecute result.size(): " + result.size());
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity.createAlertDialog(activity, description,
							activity.getResources().getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = activity.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(activity, description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}

		}

		private List<ContractFormMngtBean> getBankByCode() {
			List<ContractFormMngtBean> lstBank = new ArrayList<ContractFormMngtBean>();
			try {
				BhldDAL dal = new BhldDAL(getActivity());
				dal.open();
				lstBank = dal.getListBankFromBCCS(searchBean);
				errorCode = "0";
				dal.close();
			} catch (Exception e) {
				Log.e("getBankByCode", e.toString(), e);
			}
			return lstBank;
		}
	}

	private void showDiaLogLoadMoreContract(final ArrayList<AccountDTO> lstAccountDTO) {

		dialogLoadMore = new Dialog(getActivity());
		dialogLoadMore.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogLoadMore.setContentView(R.layout.connection_layout_lst_sub);
		dialogLoadMore.setCancelable(false);

		loadMoreListView = (LoadMoreListView) dialogLoadMore.findViewById(R.id.loadMoreListView);
		getContractAdapterBCCS = new GetContractAdapterBCCS(lstAccountDTO, getActivity());
		loadMoreListView.setAdapter(getContractAdapterBCCS);

		TextView txtinfo = (TextView) dialogLoadMore.findViewById(R.id.txtinfo);
		txtinfo.setText(getActivity().getString(R.string.list_contract_promotion));

		loadMoreListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (lstAccountDTO != null && lstAccountDTO.size() > 0) {
					Long accountId = lstAccountDTO.get(arg2).getAccountId();
					if (arg2 == 0 && accountId == null) {
						accountDTO = new AccountDTO();
						contractNo = "";
						contractId = "";

						edtsubscriber.setText(getActivity().getString(R.string.contractNew));

						enableContract();
						dialogLoadMore.cancel();
					} else {
						edtsubscriber.setText(lstAccountDTO.get(arg2).getAccountNo());
						GetAccountInforAsyn getAccountInforAsyn = new GetAccountInforAsyn(getActivity());
						getAccountInforAsyn.execute(accountId + "");
					}
				}

			}
		});

		loadMoreListView.setOnLoadMoreListener(new OnLoadMoreListener() {

			@Override
			public void onLoadMore() {
				// TODO load more list danh sach ban ghi
				pageIndex = pageIndex + 1;
				if (custIdentityDTO != null) {
					if (CommonActivity.isNetworkConnected(activity)) {
						GetConTractAsyn getConTractAsyn = new GetConTractAsyn(activity);
						getConTractAsyn.execute(custIdentityDTO.getCustomer().getCustId() + "",
								custIdentityDTO.getIdNo(), custIdentityDTO.getIdNo());
					} else {
						CommonActivity.createAlertDialog(activity, getString(R.string.errorNetwork),
								getString(R.string.app_name)).show();
					}
				}

			}

		});

		Button btnCancel = (Button) dialogLoadMore.findViewById(R.id.btnhuy);
		btnCancel.setVisibility(View.GONE);
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialogLoadMore.cancel();

			}
		});

		dialogLoadMore.show();

	}

	private boolean validateContract(AccountDTO item) throws Exception {

		if (item != null && item.getAccountId() != null && !CommonActivity.isNullOrEmpty(item.getAccountNo())) {
			return true;

		}

		if (CommonActivity.isNullOrEmpty(edt_signDate.getText().toString().trim())) {
			CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.signDateEmpty),
					getActivity().getString(R.string.app_name)).show();
			return false;
		}

		Spin spinCharge = (Spin) spn_billCycleFromCharging.getSelectedItem();
		if (spinCharge == null) {
			CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.ckcnotempty),
					getActivity().getString(R.string.app_name)).show();
			return false;
		}
		Spin spinPay = (Spin) spn_payMethodCode.getSelectedItem();
		if (spinPay == null) {
			CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.paymethodEmpty),
					getActivity().getString(R.string.app_name)).show();
			return false;
		}
		Spin spinMethod = (Spin) spn_noticeCharge.getSelectedItem();
		if (spinMethod == null) {
			CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.noticeEmpty),
					getActivity().getString(R.string.app_name)).show();
			return false;
		}
		if ("02".equalsIgnoreCase(spinPay.getId()) || "03".equalsIgnoreCase(spinPay.getId())) {
			if (CommonActivity.isNullOrEmpty(edittkoanhd.getText().toString().trim())) {
				CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.accountnotempty),
						getActivity().getString(R.string.app_name)).show();
				return false;
			}
			if (CommonActivity.isNullOrEmpty(edttentkoan.getText().toString().trim())) {
				CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.tentknotempty),
						getActivity().getString(R.string.app_name)).show();
				return false;
			}
			if (bankBean == null) {
				CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.nganhangnotempty),
						getActivity().getString(R.string.app_name)).show();
				return false;
			}
			if (CommonActivity.isNullOrEmpty(bankBean.getCode())) {
				CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.nganhangnotempty),
						getActivity().getString(R.string.app_name)).show();
				return false;
			}
		}

		Spin spinPrinMethod = (Spin) spn_printMethodCode.getSelectedItem();
		if (spinPrinMethod == null) {
			CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.inchitietEmpty),
					getActivity().getString(R.string.app_name)).show();
			return false;
		}
		// if
		// (CommonActivity.isNullOrEmpty(edtemailtbc.getText().toString().trim()))
		// {
		// CommonActivity.createAlertDialog(getActivity(),
		// getActivity().getString(R.string.emailempty),
		// getActivity().getString(R.string.app_name)).show();
		// return false;
		// }
		if (CommonActivity.isNullOrEmpty(edtdidongtbc.getText().toString().trim())) {
			CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.phoneempty),
					getActivity().getString(R.string.app_name)).show();
			return false;
		}
		if (CommonActivity.isNullOrEmpty(provinceContract) && CommonActivity.isNullOrEmpty(districtContract)
				&& CommonActivity.isNullOrEmpty(precintContract)) {
			CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.addempty),
					getActivity().getString(R.string.app_name)).show();
			return false;
		}

		if(CommonActivity.isNullOrEmpty(streetBlockContract)){
			CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validatestreetblockcd),
					getActivity().getString(R.string.app_name)).show();
			return false;
		}

		if (CommonActivity.isNullOrEmpty(edtdchdcuoc.getText().toString().trim())) {
			CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.addresschargenotempty),
					getActivity().getString(R.string.app_name)).show();
			return false;
		}

		// validate thong tin khach hang dai dien doanh nghiep
		if ("2".equals(custIdentityDTO.getGroupType())) {

			// validate kh cu
			if (repreCustomer != null && repreCustomer.getCustomer().getCustId() != null) {

				return true;
			} else {
				// validate kh moi
				if (custTypeDTOContractPR == null) {
					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.confirmloaikh),
							getActivity().getString(R.string.app_name)).show();
					return false;
				}
				if (CommonActivity.isNullOrEmpty(custTypeDTOContractPR.getCustType())) {
					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.confirmloaikh),
							getActivity().getString(R.string.app_name)).show();
					return false;
				}

				if (CommonActivity.isNullOrEmpty(edit_tenKHDD.getText().toString())) {

					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checknameKH),
							getActivity().getString(R.string.app_name)).show();
					return false;
				}
				if (StringUtils.CheckCharSpecical(edit_tenKHDD.getText().toString())) {
					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkcharspecical),
							getActivity().getString(R.string.app_name)).show();
					return false;
				}
				if (CommonActivity.isNullOrEmpty(edit_ngaysinhdDD.getText().toString())) {
					CommonActivity.createAlertDialog(getActivity(),
							getActivity().getString(R.string.message_pleass_input_birth_day),
							getActivity().getString(R.string.app_name)).show();
					return false;
				}

				Date birthDate = sdf.parse(edit_ngaysinhdDD.getText().toString().trim());
				if (birthDate.after(dateNow)) {
					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.nsnhohonhtai),
							getActivity().getString(R.string.app_name)).show();
					return false;
				}
				if (CommonActivity.isNullOrEmpty(edit_sogiaytoDD.getText().toString())) {
					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.chua_nhap_giay_to),
							getActivity().getString(R.string.app_name)).show();
					return false;
				}
				if ("ID".equals(typePaperDialogPR)) {

					if (edit_sogiaytoDD.getText().toString().length() != 9
							&& edit_sogiaytoDD.getText().toString().length() != 12) {
						CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checksoidno),
								getActivity().getString(R.string.app_name)).show();
						return false;
					}
				}

				if (CommonActivity.isNullOrEmpty(edit_ngaycapDD.getText().toString())) {
					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkissueempty),
							getActivity().getString(R.string.app_name)).show();
					return false;
				}

				Date datengaycap = sdf.parse(edit_ngaycapDD.getText().toString().trim());

				if (datengaycap.after(dateNow)) {
					CommonActivity.createAlertDialog(getActivity(),
							getActivity().getString(R.string.ngaycapnhohonngayhientai),
							getActivity().getString(R.string.app_name)).show();
					return false;
				}

				if (datengaycap.before(birthDate)) {
					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkcmtngaycap),
							getActivity().getString(R.string.app_name)).show();
					return false;
				}

				if (CommonActivity.isNullOrEmpty(edtnoicap.getText().toString().trim())) {
					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.chua_nhap_noi_cap),
							getActivity().getString(R.string.app_name)).show();
					return false;
				}
				if (CommonActivity.isNullOrEmpty(provinceContractPR) && CommonActivity.isNullOrEmpty(districtContractPR)
						&& CommonActivity.isNullOrEmpty(precintContractPR)) {
					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.addKHempty),
							getActivity().getString(R.string.app_name)).show();
					return false;
				}
			}
		}
		return true;
	}

}
