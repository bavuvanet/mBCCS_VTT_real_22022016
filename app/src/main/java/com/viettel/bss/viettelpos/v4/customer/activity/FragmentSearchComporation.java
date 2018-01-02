package com.viettel.bss.viettelpos.v4.customer.activity;

import java.util.ArrayList;
import java.util.HashMap;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentSearchLocation;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.AreaBean;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetAreaDal;
import com.viettel.bss.viettelpos.v4.connecttionService.listener.ExpandableHeightListView;
import com.viettel.bss.viettelpos.v4.customer.adapter.GetCorporationBOAdapter;
import com.viettel.bss.viettelpos.v4.customer.adapter.GetCorporationBOAdapter.OnAceptCorporationBO;
import com.viettel.bss.viettelpos.v4.customer.adapter.GetCorporationBOAdapter.OnDeleteCorporationBO;
import com.viettel.bss.viettelpos.v4.customer.adapter.GetCorporationBOAdapter.OnEditCorporationBO;
import com.viettel.bss.viettelpos.v4.customer.object.CorporationBO;
import com.viettel.bss.viettelpos.v4.customer.object.CorporationCategoryBO;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.tooltip.ToolTip;
import com.viettel.bss.viettelpos.v4.tooltip.ToolTipRelativeLayout;
import com.viettel.bss.viettelpos.v4.tooltip.ToolTipView;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class FragmentSearchComporation extends FragmentCommon implements OnEditCorporationBO, OnDeleteCorporationBO,
		OnAceptCorporationBO, ToolTipView.OnToolTipViewClickedListener {
	private View mView = null;

	private EditText edit_sogpkd, edit_tin, edit_isdn;

	private Button btntimkiem;
	private LinearLayout btnAddCorp;
	private ExpandableHeightListView lvCorp;

	private GetCorporationBOAdapter getCorporationBOAdapter = null;
	private ArrayList<CorporationBO> arrCorporation = new ArrayList<CorporationBO>();

	private ToolTipRelativeLayout mToolTipFrameLayout;
	private ToolTipView mOrangeToolTipView;

	private Bundle mBundle = null;
	private CorporationBO corporationBO;

	private ArrayList<Spin> arrStatus = new ArrayList<Spin>();

	private Spinner spinStatus;

	private LinearLayout lnDiaban;
	private EditText edtprovince, edtdistrict;

	private ArrayList<AreaBean> arrProvince = new ArrayList<AreaBean>();
	private String province = "";
	// arrlist district
	private ArrayList<AreaBean> arrDistrict = new ArrayList<AreaBean>();
	private String district = "";
	private String functionType;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mBundle = this.getArguments();
		if (mView == null) {
			mView = inflater.inflate(R.layout.layout_search_corporation_info, container, false);
			unit(mView);
			getBundle();
		} else {
			mBundle = this.getArguments();
			getBundle();
		}
		return mView;
	}

	@Override
	public void onResume() {
		super.onResume();
		setTitleActionBar(R.string.thuthapttinkhdn);
		if (FragmentInsertOrUpdateCoporation.hashMap != null) {
			FragmentInsertOrUpdateCoporation.hashMap = new HashMap<String, CorporationCategoryBO>();
		}
		if (FragmentInsertOrUpdateCoporation.hashMapService != null) {
			FragmentInsertOrUpdateCoporation.hashMapService = new HashMap<String, CorporationCategoryBO>();
		}
	}

	private void getBundle() {

		if (mBundle != null) {
			corporationBO = (CorporationBO) mBundle.getSerializable("mCorporationBOKey");
			if (!CommonActivity.isNullOrEmpty(corporationBO)) {
				if (!CommonActivity.isNullOrEmptyArray(arrCorporation)) {
					boolean ischeck = false;
					for (CorporationBO item : arrCorporation) {
						if (corporationBO.equals(item.getCorporationId())) {
							item = corporationBO;
							ischeck = true;
							break;
						}
					}
					if (!ischeck) {
						arrCorporation.add(corporationBO);
					}
					getCorporationBOAdapter = new GetCorporationBOAdapter(arrCorporation, getActivity(),
							FragmentSearchComporation.this, FragmentSearchComporation.this,
							FragmentSearchComporation.this);
					if (lvCorp != null) {
						lvCorp.setAdapter(getCorporationBOAdapter);
					}
					getCorporationBOAdapter.notifyDataSetChanged();

				} else {
					arrCorporation = new ArrayList<CorporationBO>();
					arrCorporation.add(corporationBO);
					getCorporationBOAdapter = new GetCorporationBOAdapter(arrCorporation, getActivity(),
							FragmentSearchComporation.this, FragmentSearchComporation.this,
							FragmentSearchComporation.this);
					if (lvCorp != null) {
						lvCorp.setAdapter(getCorporationBOAdapter);
					}
					getCorporationBOAdapter.notifyDataSetChanged();
				}
			}

		}
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btntimkiem:
			if (!CommonActivity.isNullOrEmpty(functionType) && "VT".equals(functionType)) {
				if (CommonActivity.isNullOrEmpty(province)) {
					CommonActivity.createAlertDialog(getActivity(),
							getActivity().getString(R.string.message_pleass_input_province),
							getActivity().getString(R.string.app_name)).show();
				} else {
					GetListCorporationInfoAsyn getListCorporationInfoAsyn = new GetListCorporationInfoAsyn(
							getActivity());
					getListCorporationInfoAsyn.execute();
				}
			} else {
				GetListCorporationInfoAsyn getListCorporationInfoAsyn = new GetListCorporationInfoAsyn(getActivity());
				getListCorporationInfoAsyn.execute();
			}

			break;
		case R.id.btnAddCorp:
			if (CommonActivity.isNetworkConnected(getActivity())) {
				// GetListCorporationCategoryInfoAsyn
				// getListCorporationCategoryInfoAsyn = new
				// GetListCorporationCategoryInfoAsyn(getActivity(),
				// INSERT_CORP, null);
				// getListCorporationCategoryInfoAsyn.execute("");
				Bundle mBundle = new Bundle();
				mBundle.putString("checkType", Constant.INSERT_CORP);
				mBundle.putString("functionType", functionType);
				// if (!CommonActivity.isNullOrEmpty(corporationBO)) {
				// mBundle.putSerializable("mCorporationBOKey", corporationBO);
				// }
				FragmentInsertOrUpdateCoporation mListMenuManager = new FragmentInsertOrUpdateCoporation();
				mListMenuManager.setArguments(mBundle);
				mListMenuManager.setTargetFragment(FragmentSearchComporation.this, 100);
				ReplaceFragment.replaceFragment(getActivity(), mListMenuManager, false);
			}
			break;
		default:
			break;
		}

	}

	@Override
	public void unit(View v) {

		mToolTipFrameLayout = (ToolTipRelativeLayout) v.findViewById(R.id.activity_main_tooltipframelayout);

		edit_sogpkd = (EditText) v.findViewById(R.id.edit_sogpkd);
		edit_tin = (EditText) v.findViewById(R.id.edit_tin);
		btntimkiem = (Button) v.findViewById(R.id.btntimkiem);
		btntimkiem.setOnClickListener(this);
		btnAddCorp = (LinearLayout) v.findViewById(R.id.btnAddCorp);
		btnAddCorp.setOnClickListener(this);
		lvCorp = (ExpandableHeightListView) v.findViewById(R.id.lvCorp);
		lvCorp.setExpanded(true);
		edit_isdn = (EditText) v.findViewById(R.id.edit_isdn);
		spinStatus = (Spinner) v.findViewById(R.id.spinStatus);
		initStatus();

		lnDiaban = (LinearLayout) v.findViewById(R.id.lnDiaban);
		lnDiaban.setVisibility(View.GONE);
		edtprovince = (EditText) v.findViewById(R.id.edtprovince);
		edtdistrict = (EditText) v.findViewById(R.id.edtdistrict);

		if (!CommonActivity.isNullOrEmpty(arrProvince)) {
			arrProvince = new ArrayList<AreaBean>();
		}
		initProvince();

		AsyngetPositionOfShop asyngetPositionOfShop = new AsyngetPositionOfShop(getActivity());
		asyngetPositionOfShop.execute();

		edtprovince.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(), FragmentSearchLocation.class);
				intent.putExtra("arrProvincesKey", arrProvince);
				Bundle mBundle = new Bundle();
				mBundle.putString("checkKey", "1");
				intent.putExtras(mBundle);
				startActivityForResult(intent, 106);

			}
		});

		// if (!CommonActivity.isNullOrEmpty(Session.province)
		// && !CommonActivity.isNullOrEmpty(Session.district)) {
		// initPrecinct(Session.province, Session.district);
		// try {
		// GetAreaDal dal = new GetAreaDal(getActivity());
		// dal.open();
		// edtdistrict.setText(dal.getNameDistrict(Session.province,
		// Session.district));
		// district = Session.district;
		// dal.close();
		// } catch (Exception e) {
		// }
		// }
		edtdistrict.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(), FragmentSearchLocation.class);
				intent.putExtra("arrDistrictKey", arrDistrict);
				Bundle mBundle = new Bundle();
				mBundle.putString("checkKey", "2");
				intent.putExtras(mBundle);
				startActivityForResult(intent, 107);

			}
		});

		// new Handler().postDelayed(new Runnable() {
		// @Override
		// public void run() {
		// addOrangeToolTipView();
		// }
		// }, 1100);
		//
	}

	@Override
	public void setPermission() {
	}

	private void checkpermission(String fuction) {
		functionType = fuction;
		if (!CommonActivity.isNullOrEmpty(functionType)) {
			lnDiaban.setVisibility(View.VISIBLE);
			if ("VT".equals(functionType)) {
				edtprovince.setEnabled(true);
				edtdistrict.setEnabled(true);
			} else if ("Chi Nhanh".equals(functionType)) {
				edtprovince.setEnabled(false);
				edtdistrict.setEnabled(true);
				if (!CommonActivity.isNullOrEmpty(Session.province)) {
					initDistrict(Session.province);
					try {
						GetAreaDal dal = new GetAreaDal(getActivity());
						dal.open();
						edtprovince.setText(dal.getNameProvince(Session.province));
						province = Session.province;
						dal.close();
					} catch (Exception e) {
					}
				}
				if (!CommonActivity.isNullOrEmpty(Session.province)
						&& !CommonActivity.isNullOrEmpty(Session.district)) {
					try {
						GetAreaDal dal = new GetAreaDal(getActivity());
						dal.open();
						edtdistrict.setText(dal.getNameDistrict(Session.province, Session.district));
						district = Session.district;
						dal.close();
					} catch (Exception e) {
					}
				}

			} else if ("Huyen".equals(functionType)) {
				edtprovince.setEnabled(false);
				edtdistrict.setEnabled(false);
				if (!CommonActivity.isNullOrEmpty(Session.province)) {
					initDistrict(Session.province);
					try {
						GetAreaDal dal = new GetAreaDal(getActivity());
						dal.open();
						edtprovince.setText(dal.getNameProvince(Session.province));
						province = Session.province;
						dal.close();
					} catch (Exception e) {
					}
				}
				if (!CommonActivity.isNullOrEmpty(Session.province)
						&& !CommonActivity.isNullOrEmpty(Session.district)) {
					try {
						GetAreaDal dal = new GetAreaDal(getActivity());
						dal.open();
						edtdistrict.setText(dal.getNameDistrict(Session.province, Session.district));
						district = Session.district;
						dal.close();
					} catch (Exception e) {
					}

				}
			}

		} else {
			lnDiaban.setVisibility(View.GONE);
			province = "";
			district = "";
		}

	}

	@Override
	public void onDeleteCorporationBO(final CorporationBO corporationBO) {

		OnClickListener confirmCancel = new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				AsyncexecuteCorporationInfo asyncSaveFamilyInfor = new AsyncexecuteCorporationInfo(getActivity(),
						corporationBO);
				asyncSaveFamilyInfor.execute();
			}
		};
		CommonActivity.createDialog(getActivity(),
				getActivity().getString(R.string.confirmcanceldn) + " " + corporationBO.getCorporationName(),
				getActivity().getString(R.string.app_name), getActivity().getString(R.string.OK),
				getActivity().getString(R.string.boquaReq), confirmCancel, null).show();

	}

	@Override
	public void onEditCorporationBO(CorporationBO corporationBO) {

		if (CommonActivity.isNetworkConnected(getActivity())) {
			// GetListCorporationCategoryInfoAsyn
			// getListCorporationCategoryInfoAsyn = new
			// GetListCorporationCategoryInfoAsyn(getActivity(), UPDATE_CORP,
			// corporationBO);
			// getListCorporationCategoryInfoAsyn.execute(corporationBO.getCorporationId());
			Bundle mBundle = new Bundle();
			mBundle.putString("checkType", Constant.UPDATE_CORP);
			if (!CommonActivity.isNullOrEmpty(corporationBO)) {
				mBundle.putSerializable("mCorporationBOKey", corporationBO);
			}
			mBundle.putString("functionType", functionType);
			FragmentInsertOrUpdateCoporation mListMenuManager = new FragmentInsertOrUpdateCoporation();
			mListMenuManager.setArguments(mBundle);
			mListMenuManager.setTargetFragment(FragmentSearchComporation.this, 100);
			ReplaceFragment.replaceFragment(getActivity(), mListMenuManager, false);

		}

	}

	private class GetListCorporationInfoAsyn extends AsyncTask<String, Void, ArrayList<CorporationBO>> {

		private Context mContext;
		private String errorCode;
		private String description;
		private ProgressDialog progress;

		public GetListCorporationInfoAsyn(Context context) {
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
		protected ArrayList<CorporationBO> doInBackground(String... arg0) {
			return getListCorporationBO();
		}

		@Override
		protected void onPostExecute(ArrayList<CorporationBO> result) {
			super.onPostExecute(result);
			this.progress.dismiss();
			arrCorporation = new ArrayList<CorporationBO>();
			if ("0".equals(errorCode)) {
				if (!CommonActivity.isNullOrEmptyArray(result)) {
					arrCorporation = result;
					getCorporationBOAdapter = new GetCorporationBOAdapter(arrCorporation, getActivity(),
							FragmentSearchComporation.this, FragmentSearchComporation.this,
							FragmentSearchComporation.this);
					lvCorp.setAdapter(getCorporationBOAdapter);
				} else {
					getCorporationBOAdapter = new GetCorporationBOAdapter(arrCorporation, getActivity(),
							FragmentSearchComporation.this, FragmentSearchComporation.this,
							FragmentSearchComporation.this);
					lvCorp.setAdapter(getCorporationBOAdapter);
					getCorporationBOAdapter.notifyDataSetChanged();
					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.no_data),
							getActivity().getString(R.string.app_name));
				}
			} else {
				getCorporationBOAdapter = new GetCorporationBOAdapter(arrCorporation, getActivity(),
						FragmentSearchComporation.this, FragmentSearchComporation.this, FragmentSearchComporation.this);
				lvCorp.setAdapter(getCorporationBOAdapter);
				getCorporationBOAdapter.notifyDataSetChanged();
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

		private ArrayList<CorporationBO> getListCorporationBO() {
			String original = null;
			ArrayList<CorporationBO> lstCorporation = new ArrayList<CorporationBO>();
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListCorporationInfo");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListCorporationInfo>");
				rawData.append("<input>");
				rawData.append("<token>" + Session.getToken() + "</token>");

				Spin spin = (Spin) spinStatus.getSelectedItem();
				if (!CommonActivity.isNullOrEmpty(spin)) {
					rawData.append("<status>" + spin.getId() + "</status>");
				}

				// corpTaxNumber, corpBusinessLicense

				if (!CommonActivity.isNullOrEmpty(edit_sogpkd.getText().toString().trim())) {
					rawData.append("<corpBusinessLicense>" + edit_sogpkd.getText().toString().trim());
					rawData.append("</corpBusinessLicense>");
				}
				if (!CommonActivity.isNullOrEmpty(edit_tin.getText().toString().trim())) {
					rawData.append("<corpTaxNumber>" + edit_tin.getText().toString().trim());
					rawData.append("</corpTaxNumber>");
				}

				if (!CommonActivity.isNullOrEmpty(edit_isdn.getText().toString())) {
					rawData.append(
							"<isdn>" + CommonActivity.getStardardIsdnBCCS(edit_isdn.getText().toString().trim()));
					rawData.append("</isdn>");
				}
				if (!CommonActivity.isNullOrEmpty(province)) {
					rawData.append("<province>" + province);
					rawData.append("</province>");
				}
				if (!CommonActivity.isNullOrEmpty(district)) {
					rawData.append("<district>" + district);
					rawData.append("</district>");
				}

				rawData.append("</input>");
				rawData.append("</ws:getListCorporationInfo>");
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListCorporationInfo");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				Log.d("originalllllllll", original);

				Serializer serializer = new Persister();
				ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
				if (parseOuput != null) {
					errorCode = parseOuput.getErrorCode();
					description = parseOuput.getDescription();
					lstCorporation = parseOuput.getLstCorporationBO();
				}

			} catch (Exception e) {
				Log.d("getListCorporationCategoryInfo : ex", e.toString());
			}

			return lstCorporation;
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case 106:
				if (data != null) {
					AreaBean areaBean = (AreaBean) data.getExtras().getSerializable("provinceKey");
					province = areaBean.getProvince();
					initDistrict(province);
					edtprovince.setText(areaBean.getNameProvince());
					edtdistrict.setText("");
				}
				break;
			case 107:
				if (data != null) {
					AreaBean areaBean = (AreaBean) data.getExtras().getSerializable("districtKey");
					district = areaBean.getDistrict();
					edtdistrict.setText(areaBean.getNameDistrict());
				}
				break;
			default:
				break;
			}
		}

	}

	private class AsyncexecuteCorporationInfo extends AsyncTask<String, String, Void> {
		private ProgressDialog progress;
		private String errorCode;
		private String description;
		private CorporationBO corporationBO;

		public AsyncexecuteCorporationInfo(Context context, CorporationBO mCorporationBO) {
			this.corporationBO = mCorporationBO;
			progress = new ProgressDialog(context);
			progress.setMessage(getResources().getString(R.string.waitting));
			if (!progress.isShowing()) {
				progress.show();
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... params) {
			return executeCorporationInfo();
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			progress.dismiss();
			if ("0".equals(errorCode)) {
				if (!CommonActivity.isNullOrEmptyArray(arrCorporation)) {
					for (CorporationBO item : arrCorporation) {
						if (corporationBO.getCorporationId().equals(item.getCorporationId())) {
							arrCorporation.remove(item);
							break;
						}
					}
				}
				getCorporationBOAdapter = new GetCorporationBOAdapter(arrCorporation, getActivity(),
						FragmentSearchComporation.this, FragmentSearchComporation.this, FragmentSearchComporation.this);
				lvCorp.setAdapter(getCorporationBOAdapter);
				if (getCorporationBOAdapter != null) {
					getCorporationBOAdapter.notifyDataSetChanged();
				}
				if (description == null || description.isEmpty()) {
					description = getString(R.string.updatesucess);
				}
				CommonActivity.createAlertDialog(getActivity(), description, getString(R.string.app_name)).show();
			} else {
				if (Constant.INVALID_TOKEN2.equals(errorCode)) {
					Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
							getActivity().getResources().getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = getActivity().getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		private Void executeCorporationInfo() {
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_executeCorporationInfo");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:executeCorporationInfo>");
				rawData.append("<input>");
				HashMap<String, String> paramToken = new HashMap<String, String>();
				paramToken.put("token", Session.token);
				rawData.append(input.buildXML(paramToken));

				rawData.append("<executeType>" + "3");
				rawData.append("</executeType>");
				rawData.append("<corporationBO>");

				if (corporationBO != null && !CommonActivity.isNullOrEmpty(corporationBO.getCorporationId())) {
					rawData.append("<corporationId>");
					rawData.append(corporationBO.getCorporationId());
					rawData.append("</corporationId>");

				}
				rawData.append("</corporationBO>");

				rawData.append("</input>");
				rawData.append("</ws:executeCorporationInfo>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_executeCorporationInfo");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				String original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", response);

				// parser
				XmlDomParse parse = new XmlDomParse();
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode + " description " + description);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}
	}

	@Override
	public void onAceptCorporationBO(CorporationBO corporationBO) {
		if (CommonActivity.isNetworkConnected(getActivity())) {
			// GetListCorporationCategoryInfoAsyn
			// getListCorporationCategoryInfoAsyn = new
			// GetListCorporationCategoryInfoAsyn(getActivity(), UPDATE_CORP,
			// corporationBO);
			// getListCorporationCategoryInfoAsyn.execute(corporationBO.getCorporationId());
			Bundle mBundle = new Bundle();
			mBundle.putString("checkType", Constant.ACCEPT_CORP);
			if (!CommonActivity.isNullOrEmpty(corporationBO)) {
				mBundle.putSerializable("mCorporationBOKey", corporationBO);
			}
			mBundle.putString("functionType", functionType);
			FragmentInsertOrUpdateCoporation mListMenuManager = new FragmentInsertOrUpdateCoporation();
			mListMenuManager.setArguments(mBundle);
			mListMenuManager.setTargetFragment(FragmentSearchComporation.this, 100);
			ReplaceFragment.replaceFragment(getActivity(), mListMenuManager, false);

		}
	}

	private void addOrangeToolTipView() {
		ToolTip toolTip = new ToolTip().withText("Nhấn vào đây để thêm mới")
				.withColor(getActivity().getResources().getColor(R.color.holo_blue));
		// .withAnimationType(ToolTip.AnimationType.FROM_TOP);

		mOrangeToolTipView = mToolTipFrameLayout.showToolTipForView(toolTip, btnAddCorp);
		mOrangeToolTipView.setOnToolTipViewClickedListener(this);
	}
	// private void addOrangeToolTipView() {
	// ToolTip toolTip = new ToolTip()
	// .withText("Thêm mới!")
	// .withColor(getResources().getColor(R.color.red));
	//
	// mOrangeToolTipView = mToolTipFrameLayout.showToolTipForView(toolTip,
	// btnAddCorp);
	// mOrangeToolTipView.setOnToolTipViewClickedListener(this);
	// }

	@Override
	public void onToolTipViewClicked(ToolTipView toolTipView) {
		if (mOrangeToolTipView == toolTipView) {
			mOrangeToolTipView = null;
		}

	}

	private void initStatus() {
		arrStatus = new ArrayList<Spin>();
		arrStatus.add(new Spin("", getActivity().getString(R.string.txt_select)));
		arrStatus.add(new Spin("1", getActivity().getString(R.string.notapprove)));
		arrStatus.add(new Spin("2", getActivity().getString(R.string.approveDN)));
		arrStatus.add(new Spin("3", getActivity().getString(R.string.refuseapprove)));
		Utils.setDataSpinner(getActivity(), arrStatus, spinStatus);
	}

	// lay ma tinh/thanhpho
	private void initProvince() {
		try {
			GetAreaDal dal = new GetAreaDal(getActivity());
			dal.open();
			arrProvince = dal.getLstProvince();
			dal.close();
		} catch (Exception ex) {
			Log.e("initProvince", ex.toString());
		}
	}

	// lay huyen/quan theo tinh
	private void initDistrict(String province) {
		try {
			GetAreaDal dal = new GetAreaDal(getActivity());
			dal.open();
			arrDistrict = dal.getLstDistrict(province);
			dal.close();
		} catch (Exception ex) {
			Log.e("initDistrict", ex.toString());
		}
	}

	private class AsyngetPositionOfShop extends AsyncTask<String, String, Void> {
		private ProgressDialog progress;
		private String errorCode;
		private String description;
		private String isPpsitionhos;

		public AsyngetPositionOfShop(Context context) {
			progress = new ProgressDialog(context);
			progress.setMessage(getResources().getString(R.string.waitting));
			if (!progress.isShowing()) {
				progress.show();
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... params) {
			return executeCorporationInfo();
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			progress.dismiss();
			if ("0".equals(errorCode)) {
				checkpermission(isPpsitionhos);
			} else {
				if (Constant.INVALID_TOKEN2.equals(errorCode)) {
					Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
							getActivity().getResources().getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = getActivity().getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		private Void executeCorporationInfo() {
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getPositionOfShop");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getPositionOfShop>");
				rawData.append("<input>");
				HashMap<String, String> paramToken = new HashMap<String, String>();
				paramToken.put("token", Session.token);
				rawData.append(input.buildXML(paramToken));

				rawData.append("</input>");
				rawData.append("</ws:getPositionOfShop>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getPositionOfShop");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				String original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", response);

				// parser
				XmlDomParse parse = new XmlDomParse();
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					isPpsitionhos = parse.getValue(e2, "isPpsitionhos");
					Log.d("errorCode", errorCode + " description " + description);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}
	}

}
