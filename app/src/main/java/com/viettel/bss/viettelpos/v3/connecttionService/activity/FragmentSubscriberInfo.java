package com.viettel.bss.viettelpos.v3.connecttionService.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bss.smartphonev2.commons.OnPostExecute;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.CreateAddressCommon;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.AreaBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.CustomerOrderDetailClone;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ReceiveRequestBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.SysUsersBO;
import com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.dal.CacheDatabaseManager;
import com.viettel.bss.viettelpos.v4.sale.dal.BhldDAL;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.LoadMoreListView;
import com.viettel.bss.viettelpos.v4.ui.LoadMoreListView.OnLoadMoreListener;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;
import com.viettel.bss.viettelpos.v3.connecttionService.adapter.GetServiceTypeAdapter;
import com.viettel.bss.viettelpos.v3.connecttionService.adapter.GetServiceTypeAdapter.OnChangeCheckQuantityService;
import com.viettel.bss.viettelpos.v3.connecttionService.adapter.GetServiceTypeAdapter.OnChangeSoLuongService;
import com.viettel.bss.viettelpos.v3.connecttionService.adapter.GetSubscriberAdapter;
import com.viettel.bss.viettelpos.v3.connecttionService.model.GroupsDTO;
import com.viettel.bss.viettelpos.v3.connecttionService.model.ProductCatalogDTO;
import com.viettel.bss.viettelpos.v3.connecttionService.model.ResultGetVendorByConnectorForm;
import com.viettel.bss.viettelpos.v3.connecttionService.model.ResultSurveyOnlineForBccs2Form;
import com.viettel.bss.viettelpos.v3.connecttionService.model.SubInfrastructureDTO;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author thinhhq1 core
 * @version 2.0
 *
 */
public class FragmentSubscriberInfo extends Fragment implements OnChangeSoLuongService, OnChangeCheckQuantityService {
	private View mView;
	private ExpandableHeightGridView grid;
	private Button btnQuanLyYeuCau;
	private Button btnFinish;
	private Button btnClear;
	private Button btnSearch;
	private Button btnKhaoSat;
	private TextView txtLocation;

	private TextView edtsubscriber;

	private LinearLayout lnsubsamaddress, lnacount;

	private TextView txtacount;

	private GetServiceTypeAdapter adapter = null;
	private ArrayList<ProductCatalogDTO> arrProductCatalog = new ArrayList<ProductCatalogDTO>();

	private EditText editsogiayto, editisdnacount, editNguoiLienHe, editDTLienHe;

	private TextView txtKetQuaKS, txtxamchitiet;

	// thong tin ha tang tu duong day
	private ResultSurveyOnlineForBccs2Form resultSurveyOnlineForBccs2FormInit;
	private SubInfrastructureDTO subInfrastructureDTO;
	private ResultGetVendorByConnectorForm resultGetVendorByConnectorForm;

	private String serviceType = "";

	private EditText edit_maketcuoi;

	private String checkConnectorCode = "";
	private String connectorCode = "";
	private String mText = "";
	private String locationCode = "";

	private Button btncheckConectorCode;

	private ArrayList<ProductCatalogDTO> lstProductCatalog;

	private TextView txtnamequanly, txtdtlienhe;

	// danh sach thue bao cung dia chi
	private ArrayList<SubscriberDTO> arrSubscriberDTO = new ArrayList<SubscriberDTO>();
	private SubscriberDTO subscriberDTOSelect;

	private LinearLayout btndelete;
	private LinearLayout btndelete2;

	private AreaBean areaBeanMain = new AreaBean();
	private AreaObj areaProvicial;
	private AreaObj areaDistrist;
	private AreaObj areaPrecint;
	private AreaObj areaGroup;
	private String areaFlow;
	private String areaHomeNumber;
	private StringBuilder address;

	private GroupsDTO groupsDTO;
//	public  static  FragmentSubscriberInfo instance = null;
	private String requestExtId;
	private ReceiveRequestBean receiveRequestBean;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Bundle bundle = this.getArguments();
		if(bundle != null){
			receiveRequestBean = (ReceiveRequestBean) bundle.getSerializable("ReceiveRequestBeanKey");
			if(receiveRequestBean != null){
				requestExtId = receiveRequestBean.getReciveRequestId();
			}
		}
		if (mView == null) {
			mView = inflater.inflate(R.layout.connection_layout_subscriber_info, container, false);
			unit(mView);
		}
//		instance = FragmentSubscriberInfo.this;
		return mView;
	}

	// unit view
	private void unit(View v) {

		edtsubscriber = (TextView) v.findViewById(R.id.edtsubscriber);
		edtsubscriber.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// show len man hinh chon so subscriber load more
				if (arrSubscriberDTO != null && arrSubscriberDTO.size() > 0) {
					showDiaLogLoadMoreSub(arrSubscriberDTO);
				}

			}
		});

		editsogiayto = (EditText) v.findViewById(R.id.editsogiayto);
		editisdnacount = (EditText) v.findViewById(R.id.editisdnacount);
		edit_maketcuoi = (EditText) v.findViewById(R.id.editMaKetCuoi);
		btncheckConectorCode = (Button) v.findViewById(R.id.btnkiemtra);
		btncheckConectorCode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				serviceType = "";
				ArrayList<ProductCatalogDTO> lst = new ArrayList<ProductCatalogDTO>();
				if (arrProductCatalog != null && arrProductCatalog.size() > 0) {
					for (ProductCatalogDTO item : arrProductCatalog) {
						if (item.isCheck()) {
							lst.add(item);
						}
					}

					if (lst != null && lst.size() > 0) {
						if (lst.size() == 1) {
							serviceType = lst.get(0).getTelServiceAlias() + "";
						} else {
							for (ProductCatalogDTO productCatalogDTO2 : lst) {
								if (CommonActivity.isNullOrEmpty(serviceType)) {
									serviceType = productCatalogDTO2.getTelServiceAlias();
								} else {
									serviceType = serviceType + "," + productCatalogDTO2.getTelServiceAlias();
								}

							}
						}
					}

				}

				if (CommonActivity.isNullOrEmpty(serviceType)) {
					CommonActivity.createAlertDialog(getActivity(),
							getActivity().getString(R.string.checkserviceTypeInfoExamine),
							getActivity().getString(R.string.app_name)).show();
					return;
				}

				if (CommonActivity.isNullOrEmpty(edit_maketcuoi.getText().toString())) {
					CommonActivity
							.createAlertDialog(getActivity(), getActivity().getString(R.string.checkconnectorCode),
									getActivity().getString(R.string.app_name))
							.show();
					return;
				}
				if (CommonActivity.isNetworkConnected(getActivity())) {
					GetSurveyOnlineByConnectorCodeAsyn getSurveyOnlineByConnectorCodeAsyn = new GetSurveyOnlineByConnectorCodeAsyn(
							getActivity());
					getSurveyOnlineByConnectorCodeAsyn.execute(serviceType,
							edit_maketcuoi.getText().toString().trim().toUpperCase(), "");
				}

			}
		});

		edit_maketcuoi.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

//				if(!"1000".equals(checkConnectorCode)){
//					if (mText != null && !mText.toString().isEmpty()) {
//						if (s != null && !s.equals(mText)
//								&& ((s.length() < mText.length()) || (s.length() >= mText.length()))) {
//							Log.d("TAG", "Xoa xoa");
//							checkConnectorCode = "1002";
//							resultSurveyOnlineForBccs2FormInit = new ResultSurveyOnlineForBccs2Form();
//							txtKetQuaKS.setText("");
//							btncheckConectorCode.setVisibility(View.VISIBLE);
//						} else {
//							btncheckConectorCode.setVisibility(View.GONE);
//						}
//					}
//				}


			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				Log.d("TAG", "Xoa xoa");
			}

			@Override
			public void afterTextChanged(Editable arg0) {
				Log.d("TAG", "Xoa xoa");
			}
		});

		lnsubsamaddress = (LinearLayout) v.findViewById(R.id.lnsubsamaddress);
		lnacount = (LinearLayout) v.findViewById(R.id.lnacount);
		lnsubsamaddress.setVisibility(View.GONE);
		lnacount.setVisibility(View.GONE);

		txtacount = (TextView) v.findViewById(R.id.txtacount);

		grid = (ExpandableHeightGridView) v.findViewById(R.id.gridviewDichVu);
		grid.setExpanded(true);

		btnQuanLyYeuCau = (Button) v.findViewById(R.id.btnQuanLyYeuCau);
		btnQuanLyYeuCau.setVisibility(View.GONE);
		btnQuanLyYeuCau.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
//				FragmentSubscriberInfo.this.getActivity().onBackPressed();
//				FragmentManageRequest fragManageRequest = new FragmentManageRequest();
//				ReplaceFragment.replaceFragment(getActivity(), fragManageRequest, true);
			}
		});

		btnFinish = (Button) v.findViewById(R.id.btnFinish);
		btnFinish.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				if (CommonActivity.isNullOrEmpty(editNguoiLienHe.getText().toString())) {
					CommonActivity
							.createAlertDialog(getActivity(), getActivity().getString(R.string.nguoilienheisempty),
									getActivity().getString(R.string.app_name))
							.show();
					return;
				}
				if (CommonActivity.isNullOrEmpty(editDTLienHe.getText().toString())) {
					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.sdtlienhe),
							getActivity().getString(R.string.app_name)).show();
					return;
				}

				if (CommonActivity.isNullOrEmpty(serviceType)) {
					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkserviceType),
							getActivity().getString(R.string.app_name)).show();
					return;
				}

				if (resultSurveyOnlineForBccs2FormInit == null) {
					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.hatangempty),
							getActivity().getString(R.string.app_name)).show();
					return;
				}

				if (CommonActivity.isNullOrEmpty(txtKetQuaKS.getText().toString())) {
					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.surveyempty),
							getActivity().getString(R.string.app_name)).show();
					return;
				}

				if ("NOK".equals(txtKetQuaKS.getText().toString().trim())) {
					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.hatangnok),
							getActivity().getString(R.string.app_name)).show();
					return;

				}

				if (areaBeanMain == null) {
					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.addressnull),
							getActivity().getString(R.string.app_name)).show();
					return;
				}
				if (CommonActivity.isNullOrEmpty(areaBeanMain.getProvince())
						&& CommonActivity.isNullOrEmpty(areaBeanMain.getPrecinct())
						&& CommonActivity.isNullOrEmpty(areaBeanMain.getDistrict())
//						&& CommonActivity.isNullOrEmpty(areaBeanMain.getStreetBlock())
						) {
					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.addressnull),
							getActivity().getString(R.string.app_name)).show();
					return;
				}
				resultSurveyOnlineForBccs2FormInit.setResult("OK");
				// chuyen qua man hinh thong tin khach hang
				DoFinishSurveyOnline doFinishSurveyOnline = new DoFinishSurveyOnline(getActivity(), areaBeanMain);
				doFinishSurveyOnline.execute();

			}
		});

		btndelete = (LinearLayout) v.findViewById(R.id.btndelete);
		btndelete2 = (LinearLayout) v.findViewById(R.id.btndelete2);
		btndelete2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				resetView();

			}
		});
		btndelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				resetView();

			}
		});


		btnClear = (Button) v.findViewById(R.id.btnClear);
		btnClear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				resetView();

			}
		});

		btnSearch = (Button) v.findViewById(R.id.btnTimKiem);
		btnSearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				pageIndex = 0;
				dialogLoadMore = null;
				arrSubscriberDTO = new ArrayList<SubscriberDTO>();
				if (arrProductCatalog != null && arrProductCatalog.size() > 0) {
					for (ProductCatalogDTO productCatalogDTO : arrProductCatalog) {
						productCatalogDTO.setCheck(false);
						productCatalogDTO.setCheckEnable(false);
					}
					if (adapter != null) {
						adapter.notifyDataSetChanged();
					}
				}
				if (CommonActivity.isNullOrEmpty(editsogiayto.getText().toString())
						&& CommonActivity.isNullOrEmpty(editisdnacount.getText().toString().trim())) {
					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.idnoEmpty),
							getActivity().getString(R.string.app_name)).show();
					return;
				}

				if (CommonActivity.isNetworkConnected(getActivity())) {
					FindWhichHasSubdeploymentDataAsyn findWhichHasSubdeploymentDataAsyn = new FindWhichHasSubdeploymentDataAsyn(
							getActivity());
					findWhichHasSubdeploymentDataAsyn.execute(editsogiayto.getText().toString().trim(),
							editisdnacount.getText().toString().trim());
				} else {
					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.errorNetwork),
							getActivity().getString(R.string.app_name)).show();
				}

			}
		});

		btnKhaoSat = (Button) v.findViewById(R.id.btnKhaoSat);
		btnKhaoSat.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				serviceType = "";
				ArrayList<ProductCatalogDTO> lst = new ArrayList<ProductCatalogDTO>();
				if (arrProductCatalog != null && arrProductCatalog.size() > 0) {
					for (ProductCatalogDTO item : arrProductCatalog) {
						if (item.isCheck()) {
							lst.add(item);
							if (lst.size() == 1) {
								serviceType = item.getTelServiceAlias() + "";
							} else {
								serviceType = serviceType + "," + item.getTelServiceAlias() + "";
							}
						}
					}
				}

				if (CommonActivity.isNullOrEmpty(serviceType)) {
					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkserviceType),
							getActivity().getString(R.string.app_name)).show();
					return;
				}
//				if(CommonActivity.isNullOrEmpty(areaBeanMain)){
//					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.addressnull) , getActivity().getString(R.string.app_name)).show();
//					return;
//				}
//				if(CommonActivity.isNullOrEmpty(areaBeanMain.getProvince()) || CommonActivity.isNullOrEmpty(areaBeanMain.getDistrict()) || CommonActivity.isNullOrEmpty(areaBeanMain.getPrecinct())){
//					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.addressnull) , getActivity().getString(R.string.app_name)).show();
//					return;
//				}
				// check neu la truong hop moi thi moi cho sang ban do
				Intent intent1 = new Intent(getActivity(), FragmentLoadMapBCCS.class);
				Bundle mBundle = new Bundle();
				mBundle.putSerializable("lstProductCatalog", lst);
				mBundle.putSerializable("areaBeanMainKey",areaBeanMain);
				mBundle.putString("serviceType", serviceType);
				Log.d("serviceType", serviceType);
				intent1.putExtras(mBundle);
				startActivityForResult(intent1, 10001);

			}
		});

		txtLocation = (TextView) v.findViewById(R.id.txtDiaChiKS);
		txtLocation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {


//				String strProvince = Session.province;
//				String strDistris = Session.district;
//
//				Bundle mBundle = new Bundle();
//				mBundle.putString("strProvince", strProvince);
//				mBundle.putString("strDistris", strDistris);
//				mBundle.putString("areaFlow", areaFlow);
//				mBundle.putString("areaHomeNumber", areaHomeNumber);
//				mBundle.putSerializable("areaPrecint", areaPrecint);
//				mBundle.putSerializable("areaGroup", areaGroup);

				String strProvince1 = Session.province;
				String strDistris1 = Session.district;
				String strPrecint1 = "";
				if(!CommonActivity.isNullOrEmpty(areaBeanMain)){
					strProvince1 = areaBeanMain.getProvince();
					strDistris1 = areaBeanMain.getDistrict();
					strPrecint1 = areaBeanMain.getPrecinct();
				}

				Bundle mBundle1 = new Bundle();
				mBundle1.putString("strProvince", strProvince1);
				mBundle1.putString("strDistris", strDistris1);
				mBundle1.putString("strPrecint", strPrecint1);
				mBundle1.putBoolean("isCheckStreetBlock", false);
				mBundle1.putString("areaFlow", areaFlow);
				mBundle1.putString("areaHomeNumber", areaHomeNumber);
				Intent i = new Intent(getActivity(), CreateAddressCommon.class);
				i.putExtras(mBundle1);
				startActivityForResult(i, 100);

			}
		});
		txtKetQuaKS = (TextView) v.findViewById(R.id.txtKetQuaKS);
		txtxamchitiet = (TextView) v.findViewById(R.id.txtxamchitiet);
		txtxamchitiet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (resultSurveyOnlineForBccs2FormInit != null
						&& !CommonActivity.isNullOrEmpty(resultSurveyOnlineForBccs2FormInit.getConnectorCode())) {
					showDialogInfratructer(resultSurveyOnlineForBccs2FormInit);
				}

			}
		});

		editNguoiLienHe = (EditText) v.findViewById(R.id.editNguoiLienHe);
		editDTLienHe = (EditText) v.findViewById(R.id.editDTLienHe);

		if (CommonActivity.isNetworkConnected(getActivity())) {
			FindFixedTelecomServiceAsyn fixedTelecomServiceAsyn = new FindFixedTelecomServiceAsyn(getActivity());
			fixedTelecomServiceAsyn.execute();
		}
		if(!CommonActivity.isNullOrEmpty(receiveRequestBean) && !CommonActivity.isNullOrEmpty(receiveRequestBean.getReciveRequestId())){
			initHotline();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 10001 && resultCode == Activity.RESULT_OK) {
			Log.d("TAG RESULT_OK", "RESULT_OK");

			if (data != null) {
				 resultSurveyOnlineForBccs2FormInit =(ResultSurveyOnlineForBccs2Form) data
						.getSerializableExtra("ServeyKey");
				checkConnectorCode = data.getStringExtra("checkConnectorCode");
				if (!CommonActivity.isNullOrEmpty(checkConnectorCode)) {
					Log.d("checkConnectorCode", checkConnectorCode);
				}

			}

			if (resultSurveyOnlineForBccs2FormInit != null) {
				if (resultSurveyOnlineForBccs2FormInit.getConnectorCode() != null) {
					edit_maketcuoi.setText(resultSurveyOnlineForBccs2FormInit.getConnectorCode());
					connectorCode = edit_maketcuoi.getText().toString();
					mText = edit_maketcuoi.getText().toString();
					Log.d("connectorCode", connectorCode);
					if (resultSurveyOnlineForBccs2FormInit.getLocationCode() != null) {
						locationCode = resultSurveyOnlineForBccs2FormInit.getLocationCode();
						Log.d("locationCode", locationCode);
						txtKetQuaKS.setText("OK");
						edit_maketcuoi.setEnabled(false);
					}

				} else {
					edit_maketcuoi.setEnabled(true);
					txtKetQuaKS.setText("");
					edit_maketcuoi.setText("");
				}
			} else {
				edit_maketcuoi.setEnabled(true);
				txtKetQuaKS.setText("");
				edit_maketcuoi.setText("");
			}
		}
		if (requestCode == 100 && resultCode == Activity.RESULT_OK) {

			areaBeanMain = new AreaBean();
			areaProvicial = (AreaObj) data.getExtras().getSerializable("areaProvicial");
			areaDistrist = (AreaObj) data.getExtras().getSerializable("areaDistrist");
			areaPrecint = (AreaObj) data.getExtras().getSerializable("areaPrecint");
			areaGroup = (AreaObj) data.getExtras().getSerializable("areaGroup");

			areaFlow = data.getExtras().getString("areaFlow");
			areaHomeNumber = data.getExtras().getString("areaHomeNumber");

			address = new StringBuilder();

			if (areaHomeNumber != null && areaHomeNumber.length() > 0) {
				address.append(areaHomeNumber + " ");
				areaBeanMain.setHomeNumber(areaHomeNumber);
			}
			if (areaFlow != null && areaFlow.length() > 0) {
				address.append(areaFlow + " ");
				areaBeanMain.setStreet(areaFlow);
			}
			if (areaGroup != null && areaGroup.getName() != null && areaGroup.getName().length() > 0) {
				address.append(areaGroup.getName() + " ");
				areaBeanMain.setStreetBlock(areaGroup.getAreaCode());
			}
			if (areaPrecint != null && areaPrecint.getName() != null && areaPrecint.getName().length() > 0) {
				areaBeanMain.setPrecinct(areaPrecint.getPrecinct());
				address.append(areaPrecint.getName() + " ");
			}
			if (areaDistrist != null && areaDistrist.getName() != null && areaDistrist.getName().length() > 0) {
				areaBeanMain.setDistrict(areaDistrist.getDistrict());
				address.append(areaDistrist.getName() + " ");
			}
			if (areaProvicial != null && areaProvicial.getName() != null && areaProvicial.getName().length() > 0) {
				areaBeanMain.setProvince(areaProvicial.getProvince());
				address.append(areaProvicial.getName());
			}

			areaBeanMain.setFullAddress(address.toString());
			// Log.d("Log", "Check edit address null: " + txtdctbc + "adess :"
			// + address);
			txtLocation.setText(address);
		}

		if (requestCode == 1100 && resultCode == Activity.RESULT_OK) {
			getActivity().onBackPressed();
		}

	}

	@Override
	public void onResume() {
//		addActionBar();
		super.onResume();
		MainActivity.getInstance().setTitleActionBar(R.string.manager_customer_connecttion);
	}

	private void addActionBar() {
		ActionBar mActionBar = getActivity().getActionBar();
		assert mActionBar != null;
		mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		mActionBar.setCustomView(R.layout.layout_actionbar_channel);
		LinearLayout relaBackHome = (LinearLayout) mActionBar.getCustomView().findViewById(R.id.relaBackHome);
		relaBackHome.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				FragmentSubscriberInfo.this.getActivity().onBackPressed();
			}
		});
		Button btnHome = (Button) mActionBar.getCustomView().findViewById(R.id.btnHome);
		btnHome.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				CommonActivity.callphone(FragmentSubscriberInfo.this.getActivity(), Constant.phoneNumber);
			}
		});
		TextView txtNameActionBar = (TextView) mActionBar.getCustomView().findViewById(R.id.txtNameActionbar);
		TextView txtAddressActionBar = (TextView) mActionBar.getCustomView().findViewById(R.id.txtAddressActionbar);
		txtAddressActionBar.setVisibility(View.GONE);
		txtNameActionBar.setText(getResources().getString(R.string.manager_customer_connecttion));
	}

	/**
	 */
	private void clearData(View parent) {
		ViewGroup vg = (ViewGroup) parent;
		View vChild;
		for (int i = 0; i < vg.getChildCount(); i++) {
			vChild = vg.getChildAt(i);
			if (vChild instanceof EditText) {
				((EditText) vChild).setText("");
			} else if (vChild instanceof CheckBox) {
				((CheckBox) vChild).setChecked(false);
			} else if (vChild instanceof TextView
					&& getResources().getString(R.string.siTagQuanLyKetCuoi).equals(vChild.getTag())) {
				((TextView) vChild).setText("");
			}
			if (vChild instanceof ViewGroup) {
				clearData(vChild);
			}
		}
	}

	// @Override
	// public void passLocation(AreaBean area) {
	// areaBeanMain = area;
	// txtLocation.setText(area.getFullAddress());
	// txtLocation.setTag(area);
	// }

	// ham lay danh sach dich vu dau noi co dinh
	private class FindFixedTelecomServiceAsyn extends AsyncTask<Void, Void, ArrayList<ProductCatalogDTO>> {

		private Context context;
		private ProgressDialog progress;
		private String errorCode = "";
		private String description = "";

		public FindFixedTelecomServiceAsyn(Context mContext) {
			this.context = mContext;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<ProductCatalogDTO> doInBackground(Void... params) {
			return findFixedTelecomService();
		}

		@Override
		protected void onPostExecute(ArrayList<ProductCatalogDTO> result) {
			super.onPostExecute(result);
			progress.dismiss();
			if ("0".equals(errorCode)) {
				arrProductCatalog = new ArrayList<ProductCatalogDTO>();
				if (result != null) {
					if (result != null && result.size() > 0) {
						arrProductCatalog = result;
						adapter = new GetServiceTypeAdapter(arrProductCatalog, getActivity(),
								FragmentSubscriberInfo.this, FragmentSubscriberInfo.this);
						grid.setAdapter(adapter);

						// day lai dich vu phan hotline
						if(receiveRequestBean != null && !CommonActivity.isNullOrEmpty(receiveRequestBean.getTelecomServiceId())){
							disableEnableServiceHotline(Integer.parseInt(receiveRequestBean.getTelecomServiceId()));
						}

					} else {
						adapter.notifyDataSetChanged();
						if (description != null && !description.isEmpty()) {
							Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
									getResources().getString(R.string.app_name));
							dialog.show();
						} else {
							Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
									getResources().getString(R.string.notsubscriber),
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

		private ArrayList<ProductCatalogDTO> findFixedTelecomService() {
			String original = null;

			ArrayList<ProductCatalogDTO> arrayList = new ArrayList<ProductCatalogDTO>();

			ArrayList<ProductCatalogDTO> arrResult = new ArrayList<ProductCatalogDTO>();

			try {

//				arrResult = new CacheDatabaseManager(context).getListServiceTypeBCCS();
//				if (arrResult != null && !arrResult.isEmpty()) {
//					errorCode = "0";
//					return arrResult;
//				}

				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_findFixedTelecomService");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:findFixedTelecomService>");
				rawData.append("<input>");
				HashMap<String, String> paramToken = new HashMap<String, String>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));

				rawData.append("</input>");
				rawData.append("</ws:findFixedTelecomService>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_findFixedTelecomService");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee", original);

				Serializer serializer = new Persister();
				ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
				if (parseOuput != null) {
					errorCode = parseOuput.getErrorCode();
					description = parseOuput.getDescription();
					arrayList = parseOuput.getLstProductCatalogDTOs();

					if (arrayList != null && arrayList.size() > 0) {
//						public static final String LST_SERVICE_TYPE = "A;P;N;E;I;F;U;T;2";
						for (ProductCatalogDTO productCatalogDTO : arrayList) {
								if ("F".equals(productCatalogDTO.getTelServiceAlias())) {
									arrResult.add(productCatalogDTO);
									break;
								}
						}
						for (ProductCatalogDTO productCatalogDTO : arrayList) {
							if ("2".equals(productCatalogDTO.getTelServiceAlias())) {
								productCatalogDTO.setName("Multi 2C");
								arrResult.add(productCatalogDTO);
								break;
							}
						}
						for (ProductCatalogDTO productCatalogDTO : arrayList) {
							if ("E".equals(productCatalogDTO.getTelServiceAlias())) {
								productCatalogDTO.setName("EoC");
								arrResult.add(productCatalogDTO);
								break;
							}
						}
						for (ProductCatalogDTO productCatalogDTO : arrayList) {
							if ("U".equals(productCatalogDTO.getTelServiceAlias())) {
								productCatalogDTO.setName("Multi 1C");
								arrResult.add(productCatalogDTO);
								break;
							}
						}
						for (ProductCatalogDTO productCatalogDTO : arrayList) {
							if ("N".equals(productCatalogDTO.getTelServiceAlias())) {
								arrResult.add(productCatalogDTO);
								break;
							}
						}
						for (ProductCatalogDTO productCatalogDTO : arrayList) {
							if ("I".equals(productCatalogDTO.getTelServiceAlias())) {
								arrResult.add(productCatalogDTO);
								break;
							}
						}
						for (ProductCatalogDTO productCatalogDTO : arrayList) {
							if ("A".equals(productCatalogDTO.getTelServiceAlias())) {
								arrResult.add(productCatalogDTO);
								break;
							}
						}
						for (ProductCatalogDTO productCatalogDTO : arrayList) {
							if ("T".equals(productCatalogDTO.getTelServiceAlias())) {
								productCatalogDTO.setName("THC");
								arrResult.add(productCatalogDTO);
								break;
							}
						}
						for (ProductCatalogDTO productCatalogDTO : arrayList) {
							if ("P".equals(productCatalogDTO.getTelServiceAlias())) {
								arrResult.add(productCatalogDTO);
								break;
							}
						}

					}
				}
//				new CacheDatabaseManager(context).insertServiceType(arrResult);

				return arrResult;

			} catch (Exception e) {
				Log.d("findFixedTelecomService", e.toString());
			}
			return null;
		}

	}

	// ham lay thong tin duong day
	private class FindBySubIdAndTypeAsyn extends AsyncTask<String, Void, GroupsDTO> {
		private Context context;
		private ProgressDialog progress;
		private String errorCode = "";
		private String description = "";

		public FindBySubIdAndTypeAsyn(Context mContext) {
			this.context = mContext;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected GroupsDTO doInBackground(String... params) {
			return findBySubIdAndType(params[0]);
		}

		@Override
		protected void onPostExecute(GroupsDTO result) {
			super.onPostExecute(result);

			progress.dismiss();
			if ("0".equals(errorCode)) {

				if (result != null) {
					groupsDTO = result;
					lnacount.setVisibility(View.VISIBLE);
					if (!CommonActivity.isNullOrEmpty(result.getCode())) {
						txtacount.setText(result.getCode());
						if (subscriberDTOSelect != null) {
							if (subscriberDTOSelect.getSubId() != null) {
								FindBySubIdAsyn findBySubIdAsyn = new FindBySubIdAsyn(getActivity());
								findBySubIdAsyn.execute(subscriberDTOSelect.getSubId() + "");
							}
						}
					} else {
						txtacount.setText("");
					}

				} else {
					editNguoiLienHe.setText("");
					editDTLienHe.setText("");
					edit_maketcuoi.setText("");
					txtacount.setText("");
					lnacount.setVisibility(View.GONE);
					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notubinfra),
							getActivity().getString(R.string.app_name)).show();

				}
			} else {
				editNguoiLienHe.setText("");
				editDTLienHe.setText("");
				txtacount.setText("");
				lnacount.setVisibility(View.GONE);
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

		private GroupsDTO findBySubIdAndType(String subId) {
			String original = null;

			GroupsDTO arrayList = new GroupsDTO();

			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_findBySubIdAndType");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:findBySubIdAndType>");
				rawData.append("<input>");
				HashMap<String, String> paramToken = new HashMap<String, String>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("<subId>" + subId);
				rawData.append("</subId>");
				rawData.append("</input>");
				rawData.append("</ws:findBySubIdAndType>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_findBySubIdAndType");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee", original);

				Serializer serializer = new Persister();
				ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
				if (parseOuput != null) {
					errorCode = parseOuput.getErrorCode();
					description = parseOuput.getDescription();
					arrayList = parseOuput.getGroupsDTO();
				}
				return arrayList;

			} catch (Exception e) {
				Log.d("findBySubIdAndType", e.toString());
			}
			return null;
		}

	}

	// ham lay thong tin ha tang tu tu thue bao

	private class FindBySubIdAsyn extends AsyncTask<String, Void, SubInfrastructureDTO> {
		private Context context;
		private ProgressDialog progress;
		private String errorCode = "";
		private String description = "";

		public FindBySubIdAsyn(Context mContext) {
			this.context = mContext;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected SubInfrastructureDTO doInBackground(String... params) {
			return findBySubId(params[0]);
		}

		@Override
		protected void onPostExecute(SubInfrastructureDTO result) {
			super.onPostExecute(result);

			progress.dismiss();
			if ("0".equals(errorCode)) {
				areaBeanMain = new AreaBean();
				if (result != null) {

					subInfrastructureDTO = result;
					edit_maketcuoi.setText(subInfrastructureDTO.getCableBoxCode());
					btncheckConectorCode.setVisibility(View.GONE);
					btnKhaoSat.setVisibility(View.GONE);

					edit_maketcuoi.setEnabled(false);

					areaBeanMain.setProvince(subInfrastructureDTO.getProvince());
					areaBeanMain.setDistrict(subInfrastructureDTO.getDistrict());
					areaBeanMain.setPrecinct(subInfrastructureDTO.getPrecinct());
					if (!CommonActivity.isNullOrEmpty(subInfrastructureDTO.getStreetBlock())) {
						areaBeanMain.setStreetBlock(subInfrastructureDTO.getStreetBlock());
					}


					areaBeanMain.setFullAddress(subInfrastructureDTO.getAddress());

					txtLocation.setText(subInfrastructureDTO.getAddress());
					txtLocation.setEnabled(false);

					if (subscriberDTOSelect.getSubId() != null && subInfrastructureDTO != null) {
						edit_maketcuoi.setText(subInfrastructureDTO.getCableBoxCode());
						GetSurveyOnlineByConnectorCodeAsyn getSurveyOnlineByConnectorCodeAsyn = new GetSurveyOnlineByConnectorCodeAsyn(
								getActivity());
						if(!CommonActivity.isNullOrEmpty(txtacount.getText().toString())){
							getSurveyOnlineByConnectorCodeAsyn.execute(subscriberDTOSelect.getServiceType(),
									edit_maketcuoi.getText().toString().trim().toUpperCase(),txtacount.getText().toString().trim());
						}else{
							getSurveyOnlineByConnectorCodeAsyn.execute(subscriberDTOSelect.getServiceType(),
									edit_maketcuoi.getText().toString().trim().toUpperCase(),"");
						}

					}

				} else {

					areaBeanMain = new AreaBean();
					txtLocation.setText(getActivity().getString(R.string.selectaddresscd));
					txtLocation.setEnabled(true);
					btncheckConectorCode.setVisibility(View.VISIBLE);
					btnKhaoSat.setVisibility(View.VISIBLE);
					edit_maketcuoi.setText("");
					edit_maketcuoi.setEnabled(false);

					subInfrastructureDTO = null;
					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notubinfra),
							getActivity().getString(R.string.app_name)).show();

				}
			} else {
				areaBeanMain = new AreaBean();
				txtLocation.setText(getActivity().getString(R.string.selectaddresscd));
				txtLocation.setEnabled(true);
				btncheckConectorCode.setVisibility(View.VISIBLE);
				btnKhaoSat.setVisibility(View.VISIBLE);
				edit_maketcuoi.setText("");
				subInfrastructureDTO = null;
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

		private SubInfrastructureDTO findBySubId(String subId) {
			String original = null;

			SubInfrastructureDTO arrayList = new SubInfrastructureDTO();

			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_findBySubId");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:findBySubId>");
				rawData.append("<input>");
				HashMap<String, String> paramToken = new HashMap<String, String>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("<subId>" + subId);
				rawData.append("</subId>");
				rawData.append("</input>");
				rawData.append("</ws:findBySubId>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(), "mbccs_findBySubId");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee", original);

				Serializer serializer = new Persister();
				ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
				if (parseOuput != null) {
					errorCode = parseOuput.getErrorCode();
					description = parseOuput.getDescription();
					ArrayList<SubInfrastructureDTO> infrastructureDTOs = parseOuput.getLstInfrastructureDTOs();
					if (infrastructureDTOs != null && infrastructureDTOs.size() > 0) {
						arrayList = infrastructureDTOs.get(0);
					}

				}
				return arrayList;

			} catch (Exception e) {
				Log.d("findBySubId", e.toString());
			}
			return null;
		}

	}

	// tim kiem danh sach thue bao tu idNo, isdn, address

	private class FindWhichHasSubdeploymentDataAsyn extends AsyncTask<String, Void, ArrayList<SubscriberDTO>> {

		private Context context;
		private ProgressDialog progress;
		private String errorCode = "";
		private String description = "";

		public FindWhichHasSubdeploymentDataAsyn(Context mContext) {
			this.context = mContext;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<SubscriberDTO> doInBackground(String... params) {
			return findWhichHasSubdeploymentData(params[0], params[1]);
		}

		@Override
		protected void onPostExecute(ArrayList<SubscriberDTO> result) {
			super.onPostExecute(result);
			progress.dismiss();
			if ("0".equals(errorCode)) {
				if (result != null && result.size() > 0) {
					lnsubsamaddress.setVisibility(View.VISIBLE);
					if(!CommonActivity.isNullOrEmpty(result)){
						for (SubscriberDTO item : result){
							if(item.getSubId() != 0){
								arrSubscriberDTO.add(item);
							}
						}

					}

					if (dialogLoadMore == null ) {
						if(!CommonActivity.isNullOrEmpty(arrSubscriberDTO)){
							showDiaLogLoadMoreSub(arrSubscriberDTO);
						}else{
							Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
									getResources().getString(R.string.notsubscriber),
									getResources().getString(R.string.app_name));
							dialog.show();
						}
					}
				} else {
					lnsubsamaddress.setVisibility(View.GONE);
					edtsubscriber.setText("");
					if (description != null && !description.isEmpty()) {
						Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
								getResources().getString(R.string.app_name));
						dialog.show();
					} else {
						Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
								getResources().getString(R.string.notsubscriber),
								getResources().getString(R.string.app_name));
						dialog.show();
					}
				}
			} else {
				lnsubsamaddress.setVisibility(View.GONE);
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

		private ArrayList<SubscriberDTO> findWhichHasSubdeploymentData(String idNo, String isdn) {
			String original = null;

			ArrayList<SubscriberDTO> arrayList = new ArrayList<SubscriberDTO>();

			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_findWhichHasSubdeploymentData");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:findWhichHasSubdeploymentData>");
				rawData.append("<input>");
				HashMap<String, String> paramToken = new HashMap<String, String>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("<internetSubscriberDTO>");
				rawData.append("<idNo>" + idNo);
				rawData.append("</idNo>");
				rawData.append("<isdn>" + isdn);
				rawData.append("</isdn>");

				rawData.append("</internetSubscriberDTO>");

				rawData.append("<pageIndex>" + pageIndex + "</pageIndex>");
				rawData.append("<pageSize>" + 10 + "</pageSize>");

				rawData.append("</input>");
				rawData.append("</ws:findWhichHasSubdeploymentData>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_findWhichHasSubdeploymentData");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee", original);

				Serializer serializer = new Persister();
				ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
				if (parseOuput != null) {
					errorCode = parseOuput.getErrorCode();
					description = parseOuput.getDescription();
					arrayList = parseOuput.getLstSubscriberDTO();
				}
				return arrayList;

			} catch (Exception e) {
				Log.d("findWhichHasSubdeploymentData", e.toString());
			}
			return null;
		}

	}

	OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
			getActivity().finish();
			MainActivity.getInstance().finish();

		}
	};

	@Override
	public void onChangeSoluongListener(final ProductCatalogDTO productCatalogDTO) {
		final Dialog dialog = new Dialog(getActivity());
		dialog.setContentView(R.layout.sale_input_quantity_dialog_order2);
		dialog.setTitle(getResources().getString(R.string.salingQuantityOder));

		final EditText edtsoluong = (EditText) dialog.findViewById(R.id.edtQuantity);
		edtsoluong.setText(productCatalogDTO.getQuantity() + "");
		dialog.findViewById(R.id.btnOk).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (CommonActivity.isNullOrEmpty(edtsoluong.getText().toString().trim())) {
					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.checkslcap),
							getActivity().getString(R.string.app_name)).show();
					return;
				}
				if (Long.parseLong(edtsoluong.getText().toString().trim()) < 1) {
					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.quantitythan1),
							getActivity().getString(R.string.app_name)).show();
					return;
				}
				int max = 0;
				try {
					BhldDAL dal = new BhldDAL(getActivity());
					dal.open();
					max = dal.getMaxQuantityService(productCatalogDTO.getTelecomServiceId());
					dal.close();
				} catch (Exception e) {
					Log.e("getMaxQuantityService", e.toString(), e);
				}
				if (Long.parseLong(edtsoluong.getText().toString().trim()) > max) {
					CommonActivity
							.createAlertDialog(getActivity(), getActivity().getString(R.string.maxservice) + " " + max,
									getActivity().getString(R.string.app_name))
							.show();
					return;
				}

				productCatalogDTO.setQuantity(Long.parseLong(edtsoluong.getText().toString().trim()));

				if (adapter != null) {
					adapter.notifyDataSetChanged();
				}

				dialog.dismiss();

			}
		});

		dialog.findViewById(R.id.btnViewSaleTrans).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// CLICK CANCEL
				edtsoluong.setText("1");
				productCatalogDTO.setQuantity(1);
				if (adapter != null) {
					adapter.notifyDataSetChanged();
				}
				dialog.dismiss();
			}
		});

		edtsoluong.requestFocus();
		dialog.getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		dialog.show();

	}

	// show chi tiet thong tin ha tang
	private void showDialogInfratructer(final ResultSurveyOnlineForBccs2Form resultSurveyOnlineForBccs2Form) {

		resultSurveyOnlineForBccs2FormInit = resultSurveyOnlineForBccs2Form;

		final Dialog dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.connection_examine_info_infrastructure_dialog);
		TextView tvconnectorCode = (TextView) dialog.findViewById(R.id.tvconnectorCode);
		tvconnectorCode.setText(resultSurveyOnlineForBccs2Form.getConnectorCode());
		TextView txttnketcuoi = (TextView) dialog.findViewById(R.id.txttnketcuoi);
		TextView txtTaiNguyenThietBi = (TextView) dialog.findViewById(R.id.txtTaiNguyenThietBi);
		TextView txtMaTram = (TextView) dialog.findViewById(R.id.txtMaTram);
		TextView txtDonViQuanLyKetCuoi = (TextView) dialog.findViewById(R.id.txtDonViQuanLyKetCuoi);

		TextView txtsoluongportrong = (TextView) dialog.findViewById(R.id.txtsoluongportrong);
		TextView txtsluongportdangtkmoi = (TextView) dialog.findViewById(R.id.txtsluongportdangtkmoi);

		TextView txtPortRF = (TextView) dialog.findViewById(R.id.txtPortRF);
		txtPortRF.setText(resultSurveyOnlineForBccs2Form.getPortCode());
		TextView txtcheck = (TextView) dialog.findViewById(R.id.txtcheck);

		// thong tin quan ly nha tram
		txtnamequanly = (TextView) dialog.findViewById(R.id.txtnamequanly);
		txtdtlienhe = (TextView) dialog.findViewById(R.id.txtdtlienhe);

		// thong tin vendor
		LinearLayout lnVendor = (LinearLayout) dialog.findViewById(R.id.lnVendor);
		TextView txtloaivendor = (TextView) dialog.findViewById(R.id.txtloaivendor);
		if (resultSurveyOnlineForBccs2Form != null
				&& resultSurveyOnlineForBccs2Form.getResultGetVendorByConnectorForm() != null) {
			lnVendor.setVisibility(View.VISIBLE);
			txtloaivendor.setText(resultSurveyOnlineForBccs2Form.getResultGetVendorByConnectorForm().getVendorCode());
		} else {
			lnVendor.setVisibility(View.GONE);
			txtloaivendor.setText("");
		}

		TextView txtCongNghe = (TextView) dialog.findViewById(R.id.txtCongNghe);
		switch (Integer.parseInt(resultSurveyOnlineForBccs2Form.getInfraType())) {
		case 1:
			// cap dong
			txtCongNghe.setText(getActivity().getString(R.string.capdong));
			break;
		case 2:
			txtCongNghe.setText(getActivity().getString(R.string.capdongtruc));
			break;
		case 3:
			txtCongNghe.setText(getActivity().getString(R.string.capquang));
			break;
		case 4:
			txtCongNghe.setText(getActivity().getString(R.string.quanggpon));
			break;
		default:
			break;
		}

		TextView txtDiaChiKetCuoi = (TextView) dialog.findViewById(R.id.txtDiaChiKetCuoi);
		if (resultSurveyOnlineForBccs2Form != null
				&& !CommonActivity.isNullOrEmpty(resultSurveyOnlineForBccs2Form.getAddress())) {
			txtDiaChiKetCuoi.setText(resultSurveyOnlineForBccs2Form.getAddress());
		}

		LinearLayout lncapquang = (LinearLayout) dialog.findViewById(R.id.lnQuang);
		LinearLayout lnquangGPON = (LinearLayout) dialog.findViewById(R.id.lnGPON);
		if ("4".equals(resultSurveyOnlineForBccs2Form.getInfraType())) {
			lnquangGPON.setVisibility(View.VISIBLE);
			lncapquang.setVisibility(View.GONE);
			txtsoluongportrong.setText(resultSurveyOnlineForBccs2Form.getGponConnectorFreePort() + "");
			txtsluongportdangtkmoi.setText(resultSurveyOnlineForBccs2Form.getGponConnectorLockPort() + "");

		} else {
			lnquangGPON.setVisibility(View.GONE);
			lncapquang.setVisibility(View.VISIBLE);
			txttnketcuoi.setText(resultSurveyOnlineForBccs2Form.getResourceConnector());
			txtTaiNguyenThietBi.setText(resultSurveyOnlineForBccs2Form.getResourceDevice());


		}
		txtcheck.setText(resultSurveyOnlineForBccs2Form.getResult());
		txtMaTram.setText(resultSurveyOnlineForBccs2Form.getStationCode());
		txtDonViQuanLyKetCuoi.setText(resultSurveyOnlineForBccs2Form.getDeptName());

		Button btnkiemtra = (Button) dialog.findViewById(R.id.btnkiemtra);
		btnkiemtra.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// lay thong tin nguoi quan ly o day

				GetListStaffInfraConnector getListStaffInfraConnector = new GetListStaffInfraConnector(getActivity());
				getListStaffInfraConnector.execute(resultSurveyOnlineForBccs2Form.getConnectorCode());
			}
		});

		Button btncapnhat = (Button) dialog.findViewById(R.id.btncapnhat);
		btncapnhat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				btncheckConectorCode.setVisibility(View.VISIBLE);
				resultSurveyOnlineForBccs2FormInit = resultSurveyOnlineForBccs2Form;
				dialog.dismiss();
			}
		});

		Button btnchonlai = (Button) dialog.findViewById(R.id.btnchonlai);
		btnchonlai.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		if ("OK".equals(resultSurveyOnlineForBccs2Form.getResult())) {
			btncapnhat.setVisibility(View.VISIBLE);
			btnchonlai.setVisibility(View.GONE);
		} else {
			btnchonlai.setVisibility(View.VISIBLE);
			btncapnhat.setVisibility(View.GONE);
		}

		ImageView btncall = (ImageView) dialog.findViewById(R.id.btnphone);
		btncall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (txtdtlienhe.getText().toString() != null && !txtdtlienhe.getText().toString().isEmpty()) {
					Intent intent = new Intent(Intent.ACTION_CALL);

					intent.setData(Uri.parse("tel:" + txtdtlienhe.getText().toString()));
					startActivity(intent);
				} else {
					Toast.makeText(getActivity(), getResources().getString(R.string.checkphone), Toast.LENGTH_SHORT)
							.show();
				}

			}
		});

		dialog.show();
	}

	// lay thong ha tang tu ma ket cuoi
	private class GetSurveyOnlineByConnectorCodeAsyn extends AsyncTask<String, Void, ResultSurveyOnlineForBccs2Form> {

		private String errorCode;
		private String description;
		private Context context;
		private ProgressDialog progress;

		public GetSurveyOnlineByConnectorCodeAsyn(Context mContext) {
			this.context = mContext;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(R.string.getdatareport));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ResultSurveyOnlineForBccs2Form doInBackground(String... params) {
			return getSurveyOnlineByConetorCode(params[0], params[1], params[2]);
		}

		@Override
		protected void onPostExecute(ResultSurveyOnlineForBccs2Form result) {
			super.onPostExecute(result);
			progress.dismiss();
			if ("0".equals(errorCode)) {
				if (result != null) {
					resultSurveyOnlineForBccs2FormInit = result;
					if(!CommonActivity.isNullOrEmpty(subscriberDTOSelect) && CommonActivity.isNullOrEmpty(serviceType)){
						txtKetQuaKS.setText("");
					}else{
						txtKetQuaKS.setText(resultSurveyOnlineForBccs2FormInit.getResult());
					}

					// lay them thong tin vendor o day
					if (serviceType.contains("F") || serviceType.contains("N") || serviceType.contains("I")
							|| serviceType.contains("2")) {
						GetVendorByConnectorAsyn getVendorByConnectorAsyn = new GetVendorByConnectorAsyn(getActivity());
						getVendorByConnectorAsyn.execute(resultSurveyOnlineForBccs2FormInit.getConnectorId() + "",
								resultSurveyOnlineForBccs2FormInit.getConnectorCode(), "");
					}
				} else {
					resultSurveyOnlineForBccs2FormInit = null;
					txtKetQuaKS.setText("");
					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notinfra),
							getActivity().getString(R.string.app_name)).show();

				}
			} else {
				resultSurveyOnlineForBccs2FormInit = null;
				txtKetQuaKS.setText("");
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

		private ResultSurveyOnlineForBccs2Form getSurveyOnlineByConetorCode(String serviceType, String connectorCode,
				String accountGline) {
			String original = null;
			ResultSurveyOnlineForBccs2Form resultSurveyOnlineForBccs2Form = null;
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getSurveyOnlineByConnectorCodeForBccs2");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getSurveyOnlineByConnectorCodeForBccs2>");
				rawData.append("<input>");
				HashMap<String, String> paramToken = new HashMap<String, String>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("<surveyOnlineByConnectorCodeForBccs2Form>");
				
				rawData.append("<connectorCode>" + connectorCode);
				rawData.append("</connectorCode>");
				rawData.append("<serviceType>" + serviceType);
				rawData.append("</serviceType>");
				rawData.append("<accountGline>" + accountGline);
				rawData.append("</accountGline>");
				rawData.append("</surveyOnlineByConnectorCodeForBccs2Form>");
				rawData.append("</input>");
				rawData.append("</ws:getSurveyOnlineByConnectorCodeForBccs2>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getSurveyOnlineByConnectorCodeForBccs2");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee", original);

				Serializer serializer = new Persister();
				ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
				if (parseOuput != null) {
					errorCode = parseOuput.getErrorCode();
					description = parseOuput.getDescription();
					resultSurveyOnlineForBccs2Form = parseOuput.getResultSurveyOnlineForBccs2Form();
				}

				return resultSurveyOnlineForBccs2Form;

			} catch (Exception e) {
				Log.d("getSurveyOnlineByConetorCode", e.toString());
			}
			return resultSurveyOnlineForBccs2Form;
		}
	}
	// ham lay thong tin vendor
	private class GetVendorByConnectorAsyn extends AsyncTask<String, Void, ResultGetVendorByConnectorForm> {

		private String errorCode;
		private String description;
		private Context context;
		private ProgressDialog progress;

		public GetVendorByConnectorAsyn(Context mContext) {
			this.context = mContext;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(R.string.getdatareport));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ResultGetVendorByConnectorForm doInBackground(String... params) {
			return getVendorByConnector(params[0], params[1], params[2]);
		}

		@Override
		protected void onPostExecute(ResultGetVendorByConnectorForm result) {
			super.onPostExecute(result);
			progress.dismiss();
			if ("0".equals(errorCode)) {
				if (result != null) {
					resultGetVendorByConnectorForm = result;
					if(!CommonActivity.isNullOrEmpty(resultSurveyOnlineForBccs2FormInit)){
						resultSurveyOnlineForBccs2FormInit.setResultGetVendorByConnectorForm(result);
					}
				} else {
					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notubinfra),
							getActivity().getString(R.string.app_name)).show();
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

		private ResultGetVendorByConnectorForm getVendorByConnector(String connectorId, String connectorCode,
				String accountGline) {
			String original = null;
			ResultGetVendorByConnectorForm resultGetVendorByConnectorForm = null;
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getVendorByConnector");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getVendorByConnector>");
				rawData.append("<input>");
				HashMap<String, String> paramToken = new HashMap<String, String>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("<connectorId>" + connectorId);
				rawData.append("</connectorId>");
				rawData.append("<connectorCode>" + connectorCode);
				rawData.append("</connectorCode>");
				rawData.append("<glineAccount>" + accountGline);
				rawData.append("</glineAccount>");
				rawData.append("</input>");
				rawData.append("</ws:getVendorByConnector>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getVendorByConnector");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee", original);

				Serializer serializer = new Persister();
				ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
				if (parseOuput != null) {
					errorCode = parseOuput.getErrorCode();
					description = parseOuput.getDescription();
					resultGetVendorByConnectorForm = parseOuput.getResultGetVendorByConnectorForm();
				}

				return resultGetVendorByConnectorForm;

			} catch (Exception e) {
				Log.d("getVendorByConnector", e.toString());
			}
			return resultGetVendorByConnectorForm;
		}
	}

	@Override
	public void onChangeCheckQuantityService(ProductCatalogDTO productCatalogDTO, TextView view) {
		productCatalogDTO.setCheck(!productCatalogDTO.isCheck());

		// reset du lieu
		txtKetQuaKS.setText("");
		resultSurveyOnlineForBccs2FormInit = null;
//		edit_maketcuoi.setText("");


		if (Constant.LST_SERVICE_TYPE_QUANTITY.contains(productCatalogDTO.getTelServiceAlias())) {
			view.setVisibility(View.GONE);
			productCatalogDTO.setQuantity(1);
		} else {
			if (productCatalogDTO.isCheck()) {
				view.setVisibility(View.VISIBLE);
			} else {
				view.setVisibility(View.GONE);
				productCatalogDTO.setQuantity(1);

			}
		}
		adapter.notifyDataSetChanged();

		serviceType = "";

		lstProductCatalog = new ArrayList<ProductCatalogDTO>();
		if (arrProductCatalog != null && arrProductCatalog.size() > 0) {
			for (ProductCatalogDTO item : arrProductCatalog) {
				if (item.isCheck()) {
					lstProductCatalog.add(item);
				}
			}

			if (lstProductCatalog != null && lstProductCatalog.size() > 0) {
				if (lstProductCatalog.size() == 1) {
					serviceType = lstProductCatalog.get(0).getTelServiceAlias() + "";
				} else {
					for (ProductCatalogDTO productCatalogDTO2 : lstProductCatalog) {
						if (CommonActivity.isNullOrEmpty(serviceType)) {
							serviceType = productCatalogDTO2.getTelServiceAlias();
						} else {
							serviceType = serviceType + "," + productCatalogDTO2.getTelServiceAlias();
						}
					}
				}

				if (CommonActivity.isNetworkConnected(getActivity())) {
					// SubscriberDTO itemSub = (SubscriberDTO)
					// spinner_subscriber.getSelectedItem();
					if (subscriberDTOSelect != null) {
						if (subscriberDTOSelect.getSubId() != null && subInfrastructureDTO != null) {
							edit_maketcuoi.setText(subInfrastructureDTO.getCableBoxCode());
							GetSurveyOnlineByConnectorCodeAsyn getSurveyOnlineByConnectorCodeAsyn = new GetSurveyOnlineByConnectorCodeAsyn(
									getActivity());
							if(!CommonActivity.isNullOrEmpty(txtacount.getText().toString())){
								getSurveyOnlineByConnectorCodeAsyn.execute(serviceType,
										edit_maketcuoi.getText().toString().trim().toUpperCase(),txtacount.getText().toString().trim());
							}else{
								getSurveyOnlineByConnectorCodeAsyn.execute(serviceType,
										edit_maketcuoi.getText().toString().trim().toUpperCase(),"");
							}
							
						}
					}
				}
			} else {
				txtKetQuaKS.setText("");
				resultSurveyOnlineForBccs2FormInit = null;
			}

			// hien thi va khoi tao service lan dau tao moi
			disableEnableService(productCatalogDTO);

			// off dich vu co dinh trong truong hop cu
			if(!CommonActivity.isNullOrEmpty(subscriberDTOSelect) && !CommonActivity.isNullOrEmpty(subscriberDTOSelect.getSubId()) && !CommonActivity.isNullOrEmpty(subscriberDTOSelect.getTelecomServiceId())){
				disableEnableServiceOld(Integer.parseInt(subscriberDTOSelect.getTelecomServiceId() + ""));
			}

			adapter.notifyDataSetChanged();

		}

	}

	// check dich vu
	private boolean checkService(ArrayList<ProductCatalogDTO> lsProductCatalogDTOs) {

		boolean check = false;
		if (lsProductCatalogDTOs == null) {
			check = false;
		}

		if (lsProductCatalogDTOs.size() == 0) {
			check = false;
		}

		for (ProductCatalogDTO productCatalogDTO : lsProductCatalogDTOs) {
			if ("2".equals(productCatalogDTO.getTelServiceAlias())) {
				check = true;
				break;
			} else {
				check = false;
			}
		}

		return check;
	}

	// sort list service
	private ArrayList<ProductCatalogDTO> sortService(ArrayList<ProductCatalogDTO> arrayList) {
		ArrayList<ProductCatalogDTO> lstProduct = new ArrayList<ProductCatalogDTO>();
		if (arrayList == null) {
			return new ArrayList<ProductCatalogDTO>();
		}

		// truong hop co truyen hinh so 2 chieu
		for (ProductCatalogDTO item : arrayList) {
			if ("2".equals(item.getTelServiceAlias())) {
				lstProduct.add(0, item);
			} else {
				lstProduct.add(item);
			}
		}
		for (int i = 0; i < lstProduct.size(); i++) {
			if ("F".equals(lstProduct.get(i).getTelServiceAlias())) {
				ProductCatalogDTO productCatalogDTO = lstProduct.get(i);
				lstProduct.remove(i);
				lstProduct.add(0, productCatalogDTO);
				break;
			}
		}

		return lstProduct;

	}

	// chuyen qua man hinh thong tin map
	private void moveFragmentCus() {

	}

	// lay thong tin quan ly tu ma ket cuoi
	public class GetListStaffInfraConnector extends AsyncTask<String, Void, SysUsersBO> {

		ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetListStaffInfraConnector(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(R.string.getdatareport));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected SysUsersBO doInBackground(String... arg0) {
			return getListStaffInfraConnector(arg0[0]);
		}

		@Override
		protected void onPostExecute(SysUsersBO result) {
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result.getFullname() != null && !result.getFullname().isEmpty()) {
					txtnamequanly.setText(result.getFullname());
					if (result.getPhone() != null) {
						txtdtlienhe.setText(result.getPhone());
					}
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {

					CommonActivity.BackFromLogin(getActivity(), description,";cm.connect_sub_bccs2;");
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
							getResources().getString(R.string.ksbando));
					dialog.show();

				}
			}
		}

		// ======= method get User from CableBox

		private SysUsersBO getListStaffInfraConnector(String cableCode) {
			SysUsersBO sysUsersBO = new SysUsersBO();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListStaffInfraConnector");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListStaffInfraConnector>");
				rawData.append("<input>");
				rawData.append("<token>" + Session.getToken() + "</token>");
				rawData.append("<connectorCode>" + cableCode);
				rawData.append("</connectorCode>");
				// rawData.append("<infraType>" + techchologyID);
				// rawData.append("</infraType>");
				rawData.append("</input>");
				rawData.append("</ws:getListStaffInfraConnector>");
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListStaffInfraConnector");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				Log.d("originalllllllll", original);

				// ====== parse xml ============
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					nodechild = doc.getElementsByTagName("lstSysUsersBO");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						String fullname = parse.getValue(e1, "fullname");
						Log.d("fullname", fullname);
						sysUsersBO.setFullname(fullname);
						String phone = parse.getValue(e1, "phone");
						Log.d("phone", phone);
						sysUsersBO.setPhone(phone);
					}
				}

			} catch (Exception e) {
				Log.d("GetUserFromCableBox", e.toString());
			}

			return sysUsersBO;
		}
	}

	// show list dich vu an theo thue bao cu
	private void disableEnableServiceOld(int serviceId) {
		try {
			BhldDAL dal = new BhldDAL(getActivity());
			dal.open();
			// lay ra danh sach dich vu disable
			String lstDisableService = dal.getLstDisableService(serviceId);
			dal.close();

//			if (arrProductCatalog != null && arrProductCatalog.size() > 0) {
//				for (ProductCatalogDTO item : arrProductCatalog) {
//					if(item.getTelecomServiceId() == serviceId){
//						item.setCheck(true);
//						item.setCheckEnable(false);
//					}
//				}
//			}

			// truong hop check
			if (!CommonActivity.isNullOrEmpty(lstDisableService) && !"-1".equals(lstDisableService)) {
				String[] arrService = lstDisableService.split(";");

				if (arrProductCatalog != null && arrProductCatalog.size() > 0) {
					for (ProductCatalogDTO item : arrProductCatalog) {
						if (lstDisableService.contains(item.getTelecomServiceId() + "")) {
							for (String strService : arrService) {
								if (item.getTelecomServiceId() == Integer.parseInt(strService)) {
									item.setCheckEnable(true);
								}
							}
						}
					}
				}
			}
			if (adapter != null) {
				adapter.notifyDataSetChanged();
			}
		} catch (Exception e) {
			Log.d("disableEnableServiceOld", e.toString());
		}
	}

	// show list dich vu
	private void disableEnableService(ProductCatalogDTO prCatalogDTO) {
		int serviceId = prCatalogDTO.getTelecomServiceId();
		try {
			BhldDAL dal = new BhldDAL(getActivity());
			dal.open();
			// lay ra danh sach dich vu disable
			String lstDisableService = dal.getLstDisableService(serviceId);
			dal.close();
			// truong hop check
			if (prCatalogDTO.isCheck()) {
				if (!CommonActivity.isNullOrEmpty(lstDisableService) && !"-1".equals(lstDisableService)) {
					String[] arrService = lstDisableService.split(";");

					if (arrProductCatalog != null && arrProductCatalog.size() > 0) {
						for (ProductCatalogDTO item : arrProductCatalog) {
							for (String strService : arrService) {
								if (item.getTelecomServiceId() == Integer.parseInt(strService)) {
									item.setCheckEnable(true);
								}
							}
						}
					}
				}
			} else {
				// truong hop uncheck
				if (!CommonActivity.isNullOrEmpty(lstDisableService) && !"-1".equals(lstDisableService)) {
					String[] arrService = lstDisableService.split(";");
					if (arrProductCatalog != null && arrProductCatalog.size() > 0) {
						for (ProductCatalogDTO item : arrProductCatalog) {
							for (String strService : arrService) {
								if (item.getTelecomServiceId() == Integer.parseInt(strService)) {
									item.setCheckEnable(false);
									lstProductCatalog.remove(item);
								}
							}
						}
					}

					// quay lai list da chon va disable a
					if (!CommonActivity.isNullOrEmpty(lstProductCatalog)) {
						for (ProductCatalogDTO item2 : lstProductCatalog) {
							dal.open();
							String lstDisableService2 = dal.getLstDisableService(item2.getTelecomServiceId());
							dal.close();
							String[] arrService2 = lstDisableService2.split(";");
							if (arrProductCatalog != null && arrProductCatalog.size() > 0) {
								for (ProductCatalogDTO item : arrProductCatalog) {
									for (String strService : arrService2) {
										if (item.getTelecomServiceId() == Integer.parseInt(strService)) {
											item.setCheckEnable(true);
										}
									}
								}
							}

						}

					}

				}
			}
			if (adapter != null) {
				adapter.notifyDataSetChanged();
			}
		} catch (Exception e) {
			Log.d("showListService", e.toString());
		}
	}

	// load more dia chi cung duong day
	private Dialog dialogLoadMore;
	private LoadMoreListView loadMoreListView;
	private GetSubscriberAdapter getSubscriberAdapter;
	private int pageIndex = 0;

	private void showDiaLogLoadMoreSub(ArrayList<SubscriberDTO> lsSubscriberDTOs) {

		dialogLoadMore = new Dialog(getActivity());
		dialogLoadMore.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogLoadMore.setContentView(R.layout.connection_layout_lst_sub);
		dialogLoadMore.setCancelable(false);

		loadMoreListView = (LoadMoreListView) dialogLoadMore.findViewById(R.id.loadMoreListView);
		getSubscriberAdapter = new GetSubscriberAdapter(lsSubscriberDTOs, getActivity());
		loadMoreListView.setAdapter(getSubscriberAdapter);
		getSubscriberAdapter.notifyDataSetChanged();
		loadMoreListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (arrSubscriberDTO != null && arrSubscriberDTO.size() > 0) {

					if (arrProductCatalog != null && arrProductCatalog.size() > 0) {
						for (ProductCatalogDTO productCatalogDTO : arrProductCatalog) {
							productCatalogDTO.setCheck(false);
							productCatalogDTO.setCheckEnable(false);
						}
						if (adapter != null) {
							adapter.notifyDataSetChanged();
						}
					}

					subscriberDTOSelect = arrSubscriberDTO.get(arg2);


					if(!CommonActivity.isNullOrEmpty(arrProductCatalog)){
						for (ProductCatalogDTO item: arrProductCatalog) {
							if(subscriberDTOSelect.getTelecomServiceId().equals(Long.parseLong(item.getTelecomServiceId() + ""))){
								subscriberDTOSelect.setServiceType(item.getTelServiceAlias());
								break;
							}
						}
					}

					if (subscriberDTOSelect != null) {
						if (subscriberDTOSelect.getSubId() != null) {
							disableEnableServiceOld(Integer.parseInt(subscriberDTOSelect.getTelecomServiceId() + ""));
							edtsubscriber.setText(subscriberDTOSelect.toString());

							FindBySubIdAndTypeAsyn findBySubIdAndTypeAsyn = new FindBySubIdAndTypeAsyn(getActivity());
							findBySubIdAndTypeAsyn.execute(subscriberDTOSelect.getSubId() + "");

							editNguoiLienHe.setText(subscriberDTOSelect.getCustomerDTOInput().getName());
							editDTLienHe.setText(subscriberDTOSelect.getCustomerDTOInput().getContactNumber());
							dialogLoadMore.cancel();
						}
					}
				}

			}
		});

		loadMoreListView.setOnLoadMoreListener(new OnLoadMoreListener() {

			@Override
			public void onLoadMore() {
				// TODO load more list danh sach ban ghi
				pageIndex = pageIndex + 1;
				if (CommonActivity.isNetworkConnected(getActivity())) {
					FindWhichHasSubdeploymentDataAsyn findWhichHasSubdeploymentDataAsyn = new FindWhichHasSubdeploymentDataAsyn(
							getActivity());
					findWhichHasSubdeploymentDataAsyn.execute(editsogiayto.getText().toString().trim(),
							editisdnacount.getText().toString().trim());
				} else {
					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.errorNetwork),
							getActivity().getString(R.string.app_name)).show();
				}

			}

		});

		Button btnCancel = (Button) dialogLoadMore.findViewById(R.id.btnhuy);
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialogLoadMore.cancel();

			}
		});

		dialogLoadMore.show();

	}

	private void resetView() {
		FragmentSubscriberInfo.this.clearData(mView);
		// reload lại dữ liệu như ban đầu
		serviceType = "";
		resultSurveyOnlineForBccs2FormInit = null;
		ArrayList<SubscriberDTO> arrayList = new ArrayList<SubscriberDTO>();

		arrSubscriberDTO = new ArrayList<SubscriberDTO>();
		subscriberDTOSelect = new SubscriberDTO();
		edtsubscriber.setText("");

		lnsubsamaddress.setVisibility(View.GONE);
		lnacount.setVisibility(View.GONE);

		lnsubsamaddress.setVisibility(View.GONE);
		txtacount.setText("");
		lnacount.setVisibility(View.GONE);
		editNguoiLienHe.setText("");
		editDTLienHe.setText("");
		edit_maketcuoi.setText("");
		edit_maketcuoi.setEnabled(true);
		txtKetQuaKS.setText("");
		edit_maketcuoi.setEnabled(true);
		txtLocation.setEnabled(true);
		pageIndex = 0;
		dialogLoadMore = null;
		btncheckConectorCode.setVisibility(View.VISIBLE);
		btnKhaoSat.setVisibility(View.VISIBLE);

		areaBeanMain = new AreaBean();
		txtLocation.setText(getActivity().getString(R.string.selectaddresscd));

		if (arrProductCatalog != null && arrProductCatalog.size() > 0) {
			for (ProductCatalogDTO productCatalogDTO : arrProductCatalog) {
				productCatalogDTO.setCheck(false);
				productCatalogDTO.setCheckEnable(false);
			}

			adapter = new GetServiceTypeAdapter(arrProductCatalog, getActivity(), FragmentSubscriberInfo.this,
					FragmentSubscriberInfo.this);
			grid.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		}
	}

	private class DoFinishSurveyOnline extends AsyncTask<String, Void, ArrayList<CustomerOrderDetailClone>> {

		private String errorCode;
		private String description;
		private Context context;

		private ProgressDialog progress;
		private AreaBean areaBean;
		private XmlDomParse parse = new XmlDomParse();

		public DoFinishSurveyOnline(Context mContext, AreaBean mAreaBean) {
			this.context = mContext;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(R.string.getdatareport));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
			this.areaBean = mAreaBean;

		}

		@Override
		protected ArrayList<CustomerOrderDetailClone> doInBackground(String... arg0) {
			// errorCode = "0";
			// return "123";
			return doFinishSurvey();
		}

		@Override
		protected void onPostExecute(ArrayList<CustomerOrderDetailClone> result) {
			super.onPostExecute(result);
			progress.dismiss();
			if ("0".equals(errorCode)) {
				if (!CommonActivity.isNullOrEmpty(result)) {
					Bundle bundle = new Bundle();
					bundle.putString("serviceTypeKey", serviceType);
					bundle.putSerializable("resultSurveyOnlineForBccs2Form", resultSurveyOnlineForBccs2FormInit);
					bundle.putSerializable("areaBeanKey", areaBeanMain);
					bundle.putSerializable("lstProductCatalog", sortService(lstProductCatalog));
					if (!CommonActivity.isNullOrEmpty(txtacount.getText().toString().trim())) {
						bundle.putString("accountGline", txtacount.getText().toString().trim());
					}
					bundle.putSerializable("groupsDTO", groupsDTO);
					bundle.putString("contactName",editNguoiLienHe.getText().toString().trim());
					bundle.putString("telMobile",editDTLienHe.getText().toString().trim());
					bundle.putSerializable("customerOrderDetailId", result);
					if(!CommonActivity.isNullOrEmpty(subscriberDTOSelect) && !CommonActivity.isNullOrEmpty(subscriberDTOSelect.getCustomerDTOInput())){
						bundle.putSerializable("subscriberDTOSelect",subscriberDTOSelect);
					}
					if(!CommonActivity.isNullOrEmpty(requestExtId)){
						bundle.putSerializable("requestExtId",requestExtId);
					}
					FragmentInfoCustomerBCCS fragmentInfoCustomerMobileBCCS = new FragmentInfoCustomerBCCS();
					fragmentInfoCustomerMobileBCCS.setArguments(bundle);
					fragmentInfoCustomerMobileBCCS.setTargetFragment(fragmentInfoCustomerMobileBCCS,1100);
					ReplaceFragment.replaceFragment(getActivity(), fragmentInfoCustomerMobileBCCS, false);
				} else {
					CommonActivity.createAlertDialog(getActivity(), context.getString(R.string.surveynotok),
							context.getString(R.string.app_name)).show();
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

		private ArrayList<CustomerOrderDetailClone> doFinishSurvey() {
			String original = "";
//			String customerOrderDetailId = "";
			 ArrayList<CustomerOrderDetailClone> arrayList = new ArrayList<>();
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_doFinishOnlineSurvey");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:doFinishOnlineSurvey>");
				rawData.append("<input>");
				rawData.append("<token>" + Session.getToken() + "</token>");
				rawData.append("<customerOrderDetailDTO>");
				if(!CommonActivity.isNullOrEmpty(requestExtId)){
					rawData.append("<requestExtId>" + "" + requestExtId);
					rawData.append("</requestExtId>");
					rawData.append("<sourceType>" + "HL");
					rawData.append("</sourceType>");

				}

				if(!CommonActivity.isNullOrEmpty(subscriberDTOSelect) && !CommonActivity.isNullOrEmpty(subscriberDTOSelect.getSubId())){

					rawData.append("<oldSubId>" + "" + subscriberDTOSelect.getSubId());
					rawData.append("</oldSubId>");
				}

				// bo sung lock ha tang
				if (!CommonActivity.isNullOrEmpty(txtacount.getText().toString().trim())) {
					rawData.append("<gponGroupAccount>" + "" + txtacount.getText().toString().trim());
					rawData.append("</gponGroupAccount>");
				}

				rawData.append("<contactName>" + StringUtils.escapeHtml(editNguoiLienHe.getText().toString().trim()) + "</contactName>");
				rawData.append("<telMobile>" + editDTLienHe.getText().toString().trim()
						+ "</telMobile>");
				if(!CommonActivity.isNullOrEmptyArray(arrProductCatalog) && CommonActivity.isNullOrEmptyArray(lstProductCatalog)){
					lstProductCatalog= new ArrayList<>();
					for (ProductCatalogDTO item:
						 arrProductCatalog) {
						if(item.isCheck()){
							lstProductCatalog.add(item);
						}
					}
				}
				ArrayList<ProductCatalogDTO> lstPro = sortService(lstProductCatalog);

				if (lstPro != null && lstPro.size() > 0) {
					// NEU CHON NHIEU THUA BAO TREN 1 DICH VU
					if (lstPro.get(0).getQuantity() > 1) {
						rawData.append("<serviceCode>" + "" + lstPro.get(0).getTelServiceAlias());
						rawData.append("</serviceCode>");
						rawData.append("<telecomServiceId>" + "" + lstPro.get(0).getTelecomServiceId());
						rawData.append("</telecomServiceId>");

						lstPro.get(0).setQuantity(lstPro.get(0).getQuantity() - 1);
					} else {
						rawData.append("<serviceCode>" + "" + lstPro.get(0).getTelServiceAlias());
						rawData.append("</serviceCode>");
						rawData.append("<telecomServiceId>" + "" + lstPro.get(0).getTelecomServiceId());
						rawData.append("</telecomServiceId>");
						lstPro.remove(0);
					}

				}

				// moreServices
				if (lstPro != null && lstPro.size() > 0) {
					for (ProductCatalogDTO item : lstPro) {
						if (item.getQuantity() > 1) {
							for (int i = 1; i <= item.getQuantity(); i++) {
								rawData.append("<moreServices>");
								rawData.append("<serviceCode>" + "" + item.getTelServiceAlias());
								rawData.append("</serviceCode>");
								rawData.append("<telecomServiceId>" + "" + item.getTelecomServiceId());
								rawData.append("</telecomServiceId>");
								rawData.append("</moreServices>");
							}
						} else {
							rawData.append("<moreServices>");
							rawData.append("<serviceCode>" + "" + item.getTelServiceAlias());
							rawData.append("</serviceCode>");
							rawData.append("<telecomServiceId>" + "" + item.getTelecomServiceId());
							rawData.append("</telecomServiceId>");
							rawData.append("</moreServices>");
						}
					}
				}
				if (lstProductCatalog != null && lstProductCatalog.size() > 0) {
					for (ProductCatalogDTO item : lstProductCatalog) {
						rawData.append("<moreServicesAlias>" + item.getTelServiceAlias());
						rawData.append("</moreServicesAlias>");
					}
				}
				rawData.append("<subInfrastructureDTO>");
				if (!CommonActivity.isNullOrEmpty(txtacount.getText().toString().trim())) {
					rawData.append("<accountGline>" + "" + txtacount.getText().toString().trim());
					rawData.append("</accountGline>");
				}

				if (resultSurveyOnlineForBccs2FormInit != null) {
					// "cabLen",
					// "cableBoxCode",
					// "cableBoxId",
					// "cableBoxType",
					rawData.append("<cabLen>" + "" + resultSurveyOnlineForBccs2FormInit.getCableLength());
					rawData.append("</cabLen>");
					rawData.append("<cableBoxCode>" + "" + resultSurveyOnlineForBccs2FormInit.getConnectorCode());
					rawData.append("</cableBoxCode>");
					rawData.append("<cableBoxId>" + "" + resultSurveyOnlineForBccs2FormInit.getConnectorId());
					rawData.append("</cableBoxId>");
					rawData.append("<cableBoxType>" + "" + resultSurveyOnlineForBccs2FormInit.getConnectorType());
					rawData.append("</cableBoxType>");
					rawData.append("<portNo>" + "" + resultSurveyOnlineForBccs2FormInit.getPortCode());
					rawData.append("</portNo>");
					rawData.append("<radiusCust>" + "" + resultSurveyOnlineForBccs2FormInit.getSurveyRadius());
					rawData.append("</radiusCust>");
					rawData.append("<stationCode>" + "" + resultSurveyOnlineForBccs2FormInit.getStationCode());
					rawData.append("</stationCode>");
					rawData.append("<technology>" + "" + resultSurveyOnlineForBccs2FormInit.getInfraType());
					rawData.append("</technology>");

					rawData.append("<result>" + "" + resultSurveyOnlineForBccs2FormInit.getResult());
					rawData.append("</result>");

					if (resultSurveyOnlineForBccs2FormInit.getResultGetVendorByConnectorForm() != null) {
						rawData.append("<vender>" + "" + resultSurveyOnlineForBccs2FormInit
								.getResultGetVendorByConnectorForm().getVendorCode());
						rawData.append("</vender>");
					}
					rawData.append("<teamCode>" + "" + resultSurveyOnlineForBccs2FormInit.getDeptCode());
					rawData.append("</teamCode>");
				}

				// thong tin dia chi khach hang lap dat

				rawData.append("<province>" + "" + areaBean.getProvince());
				rawData.append("</province>");
				rawData.append("<district>" + "" + areaBean.getDistrict());
				rawData.append("</district>");
				rawData.append("<precinct>" + "" + areaBean.getPrecinct());
				rawData.append("</precinct>");
				if (!CommonActivity.isNullOrEmpty(areaBean.getStreetBlock())) {
					rawData.append("<streetBlock>" + "" + areaBean.getStreetBlock());
					rawData.append("</streetBlock>");
				}
				if(!CommonActivity.isNullOrEmpty(areaBean.getHomeNumber())){
					rawData.append("<home>" + "" + StringUtils.escapeHtml(areaBean.getHomeNumber()));
					rawData.append("</home>");
				}else{
					rawData.append("<home>" + "");
					rawData.append("</home>");
				}

				if(!CommonActivity.isNullOrEmpty( areaBean.getStreet())){
					rawData.append("<streetName>" + "" + areaBean.getStreet());
					rawData.append("</streetName>");
				}else{
					rawData.append("<streetName>" + "");
					rawData.append("</streetName>");
				}

				if (!CommonActivity.isNullOrEmpty(areaBean.getStreetBlock())) {
					rawData.append("<areaCode>" + "" + areaBean.getProvince() + areaBean.getDistrict()
							+ areaBean.getPrecinct() + areaBean.getStreetBlock());
				} else {
					rawData.append("<areaCode>" + "" + areaBean.getProvince() + areaBean.getDistrict()
							+ areaBean.getPrecinct());
				}

				rawData.append("</areaCode>");
				rawData.append("<address>" + "" + StringUtils.escapeHtml(areaBean.getFullAddress()));
				rawData.append("</address>");
				rawData.append("</subInfrastructureDTO>");

				rawData.append("<resultSurveyOnlineFormDTO>");
				if (!CommonActivity.isNullOrEmpty(txtacount.getText().toString().trim())) {
					rawData.append("<account>" + "" + txtacount.getText().toString().trim());
					rawData.append("</account>");
				}

				if (resultSurveyOnlineForBccs2FormInit != null) {
					// "cabLen",
					// "cableBoxCode",
					// "cableBoxId",
					// "cableBoxType",
					rawData.append("<cabLen>" + "" + resultSurveyOnlineForBccs2FormInit.getCableLength());
					rawData.append("</cabLen>");
					if(!CommonActivity.isNullOrEmpty(subscriberDTOSelect) && !CommonActivity.isNullOrEmpty(subscriberDTOSelect.getSubId())){
						rawData.append("<connectorCode>" + "" + resultSurveyOnlineForBccs2FormInit.getConnectorCode());
						rawData.append("</connectorCode>");
						rawData.append("<connectorId>" + "" + resultSurveyOnlineForBccs2FormInit.getConnectorId());
						rawData.append("</connectorId>");
						if(!CommonActivity.isNullOrEmpty(resultSurveyOnlineForBccs2FormInit.getConnectorType())){
							rawData.append("<connectorType>" + "" + resultSurveyOnlineForBccs2FormInit.getConnectorType());
							rawData.append("</connectorType>");
						}
						rawData.append("<technogoly>" + "" + resultSurveyOnlineForBccs2FormInit.getInfraType());
						rawData.append("</technogoly>");
					}


					rawData.append("<portNo>" + "" + resultSurveyOnlineForBccs2FormInit.getPortCode());
					rawData.append("</portNo>");
					rawData.append("<radiusCust>" + "" + resultSurveyOnlineForBccs2FormInit.getSurveyRadius());
					rawData.append("</radiusCust>");
					rawData.append("<stationCode>" + "" + resultSurveyOnlineForBccs2FormInit.getStationCode());
					rawData.append("</stationCode>");

					rawData.append("<locationCode>" + "" + resultSurveyOnlineForBccs2FormInit.getLocationCode());
					rawData.append("</locationCode>");

					rawData.append("<result>" + "" + resultSurveyOnlineForBccs2FormInit.getResult());
					rawData.append("</result>");

					if (resultSurveyOnlineForBccs2FormInit.getResultGetVendorByConnectorForm() != null) {
						rawData.append("<vender>" + "" + resultSurveyOnlineForBccs2FormInit
								.getResultGetVendorByConnectorForm().getVendorCode());
						rawData.append("</vender>");
					}
					rawData.append("<teamCode>" + "" + resultSurveyOnlineForBccs2FormInit.getDeptCode());
					rawData.append("</teamCode>");
				}

				rawData.append("</resultSurveyOnlineFormDTO>");
				if (!CommonActivity.isNullOrEmpty(areaBean.getStreetBlock())) {
					rawData.append("<deployAreaCode>" + "" + areaBean.getProvince() + areaBean.getDistrict()
							+ areaBean.getPrecinct() + areaBean.getStreetBlock());
				}else{
					rawData.append("<deployAreaCode>" + "" + areaBean.getProvince() + areaBean.getDistrict()
							+ areaBean.getPrecinct());

				}
				rawData.append("</deployAreaCode>");

				rawData.append("<deployAddress>" + "" + StringUtils.escapeHtml(areaBean.getFullAddress()));
				rawData.append("</deployAddress>");

				rawData.append("<fullDeploymentAddress>");

				rawData.append("<province>");

				rawData.append("<code>" + "" + areaBean.getProvince());
				rawData.append("</code>");
				rawData.append("</province>");

				rawData.append("<district>");
				rawData.append("<code>" + "" + areaBean.getDistrict());
				rawData.append("</code>");
				rawData.append("</district>");

				rawData.append("<precinct>");
				rawData.append("<code>" + "" + areaBean.getPrecinct());
				rawData.append("</code>");
				rawData.append("</precinct>");
				if(!CommonActivity.isNullOrEmpty(areaBean.getStreetBlock())){
					rawData.append("<streetBlock>");
					rawData.append("<code>" + "" + areaBean.getStreetBlock());
					rawData.append("</code>");
					rawData.append("</streetBlock>");
				}


				rawData.append("<number>" + "" + StringUtils.escapeHtml(areaBean.getHomeNumber()));
				rawData.append("</number>");
				rawData.append("<street>" + "" + areaBean.getStreet());
				rawData.append("</street>");
				if(!CommonActivity.isNullOrEmpty(areaBean.getStreetBlock())){
					rawData.append("<areaCode>" + "" + areaBean.getProvince() + areaBean.getDistrict()
							+ areaBean.getPrecinct() + areaBean.getStreetBlock());
				}else{
					rawData.append("<areaCode>" + "" + areaBean.getProvince() + areaBean.getDistrict()
							+ areaBean.getPrecinct() );
				}

				rawData.append("</areaCode>");
				rawData.append("<fullAddress>" + "" + StringUtils.escapeHtml(areaBean.getFullAddress()));
				rawData.append("</fullAddress>");
				rawData.append("</fullDeploymentAddress>");

				rawData.append("</customerOrderDetailDTO>");

				rawData.append("</input>");
				rawData.append("</ws:doFinishOnlineSurvey>");
				String rawDataRe = rawData.toString().replace(">null<","><");
				String envelope = input.buildInputGatewayWithRawData(rawDataRe);
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_doFinishOnlineSurvey");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				Log.d("originalllllllll", original);
				// ==== parse xml list ip
//				Document doc = parse.getDomElement(original);
//				NodeList nl = doc.getElementsByTagName("return");
//				for (int i = 0; i < nl.getLength(); i++) {
//					Element e2 = (Element) nl.item(i);
//					errorCode = parse.getValue(e2, "errorCode");
//					Log.d("errorCode", errorCode);
//					description = parse.getValue(e2, "description");
//					Log.d("description", description);
//					customerOrderDetailId = parse.getValue(e2, "customerOrderDetailId");
//					Log.d("customerOrderDetailId", customerOrderDetailId);
//				}

				Serializer serializer = new Persister();
				ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
				if (parseOuput != null) {
					errorCode = parseOuput.getErrorCode();
					description = parseOuput.getDescription();
					arrayList = parseOuput.getLstCustomerDTOClone();


				}


				return arrayList;

			} catch (Exception e) {
				Log.d("doFinishSurvey", e.toString());
			}

			return null;
		}
	}
	// hien thi thong tin hotline
	private void disableEnableServiceHotline(int serviceId) {
		try {
			BhldDAL dal = new BhldDAL(getActivity());
			dal.open();
			// lay ra danh sach dich vu disable
			String lstDisableService = dal.getLstDisableService(serviceId);
			dal.close();

			if (arrProductCatalog != null && arrProductCatalog.size() > 0) {
				for (ProductCatalogDTO item : arrProductCatalog) {
					if(item.getTelecomServiceId() == serviceId){
						item.setCheck(true);
						item.setCheckEnable(false);
					}
				}
			}

			// truong hop check
			if (!CommonActivity.isNullOrEmpty(lstDisableService) && !"-1".equals(lstDisableService)) {
				String[] arrService = lstDisableService.split(";");

				if (arrProductCatalog != null && arrProductCatalog.size() > 0) {
					for (ProductCatalogDTO item : arrProductCatalog) {
						if (lstDisableService.contains(item.getTelecomServiceId() + "")) {
							for (String strService : arrService) {
								if (item.getTelecomServiceId() == Integer.parseInt(strService)) {
									item.setCheckEnable(true);
								}
							}
						}
					}
				}
			}
			if (adapter != null) {
				adapter.notifyDataSetChanged();
			}
		} catch (Exception e) {
			Log.d("disableEnableServiceOld", e.toString());
		}
	}

	private void initHotline(){
		if(receiveRequestBean != null){
			if(!CommonActivity.isNullOrEmpty(receiveRequestBean.getCustomerName())){
				editNguoiLienHe.setText(receiveRequestBean.getCustomerName());
			}
			if (!CommonActivity.isNullOrEmpty(receiveRequestBean.getTel())) {
				editDTLienHe.setText(receiveRequestBean.getTel());
			}
			if(!CommonActivity.isNullOrEmpty(receiveRequestBean.getDslam())){
				edit_maketcuoi.setText(receiveRequestBean.getDslam());
			}
			if(!CommonActivity.isNullOrEmpty(receiveRequestBean.getProvinceCode()) && !CommonActivity.isNullOrEmpty(receiveRequestBean.getDistrinctCode()) && !CommonActivity.isNullOrEmpty(receiveRequestBean.getPrecinctCode()) && !CommonActivity.isNullOrEmpty(receiveRequestBean.getAddress())){
				areaBeanMain = new AreaBean();
				areaBeanMain.setProvince(receiveRequestBean.getProvinceCode());
				areaBeanMain.setDistrict(receiveRequestBean.getDistrinctCode());
				areaBeanMain.setPrecinct(receiveRequestBean.getDistrinctCode());
				areaBeanMain.setFullAddress(receiveRequestBean.getAddress());
				txtLocation.setText(receiveRequestBean.getAddress());
			}
		}

	}

    private OnPostExecute<ParseOuput> onPostGetStaffNot = new OnPostExecute<ParseOuput>() {

        @Override
        public void onPostExecute(ParseOuput result) {
            if("0".equals(result.getErrorCode())){

            }else{

            }


        }
    };

}
