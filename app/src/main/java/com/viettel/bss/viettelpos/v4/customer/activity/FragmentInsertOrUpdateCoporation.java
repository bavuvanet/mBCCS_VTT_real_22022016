package com.viettel.bss.viettelpos.v4.customer.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.google.gson.Gson;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.ParseOuputJson;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.AreaBean;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetAreaDal;
import com.viettel.bss.viettelpos.v4.connecttionService.listener.ExpandableHeightListView;
import com.viettel.bss.viettelpos.v4.customer.adapter.ComBoBoxAdapter;
import com.viettel.bss.viettelpos.v4.customer.adapter.MultiCheckAdapter;
import com.viettel.bss.viettelpos.v4.customer.adapter.MultiCheckAdapter.OnChangeCheckedStateMulti;
import com.viettel.bss.viettelpos.v4.customer.object.CorporationBO;
import com.viettel.bss.viettelpos.v4.customer.object.CorporationCategoryBO;
import com.viettel.bss.viettelpos.v4.customview.adapter.GetCatagoryBeanCoporationAdapter;
import com.viettel.bss.viettelpos.v4.customview.adapter.GetCatagoryBeanCoporationAdapter.OnAddCategotyCoporationBeans;
import com.viettel.bss.viettelpos.v4.customview.adapter.GetCatagoryBeanCoporationAdapter.OnDeleteCoporationBeans;
import com.viettel.bss.viettelpos.v4.dal.CacheDatabaseManager;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.work.object.DataComboboxBeanJson;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentInsertOrUpdateCoporation extends FragmentCommon implements OnClickListener, OnItemClickListener,
		OnChangeCheckedStateMulti, OnAddCategotyCoporationBeans, OnDeleteCoporationBeans {

	private LinearLayout lnttindn;
	private ExpandableHeightListView lvcategory;

	private Button btnnhapmoi, btncancel;

	private View mView = null;
	public String checkType = "";
	public static CorporationBO corporationBO = null;
	private ArrayList<CorporationCategoryBO> lstCorporationCategoryBO = new ArrayList<CorporationCategoryBO>();
	private CorporationCategoryBO catagoryInforBeans;

	private GetCatagoryBeanCoporationAdapter getCatagoryBeanCoporationAdapter = null;

	public static Map<String, CorporationCategoryBO> hashMap = new HashMap<String, CorporationCategoryBO>();

	public static Map<String, CorporationCategoryBO> hashMapService = new HashMap<String, CorporationCategoryBO>();

	private String title = "";

	private ArrayList<AreaBean> arrProvince = new ArrayList<AreaBean>();
	// arrlist district
	private ArrayList<AreaBean> arrDistrict = new ArrayList<AreaBean>();
	// arrlist precinct
	private ArrayList<AreaBean> arrPrecinct = new ArrayList<AreaBean>();

	private Dialog dialogMultiSelect = null;
	private MultiCheckAdapter nultiCheckAdapter = null;

	private List<AreaObj> arrStreetBlock = new ArrayList<AreaObj>();

	private ArrayList<DataComboboxBeanJson> lstDataComboboxBeanJson;

	private String provinceUpdate = "";
	private String districtUpdate = "";
	private String precintUpdate = "";

	private Dialog dialogCombobox = null;
	private ComBoBoxAdapter comBoBoxAdapter;

	private Dialog dialogNote;
	private EditText edtNote;

	public static Map<String, ArrayList<CorporationCategoryBO>> hashMapCorporationCategoryBO = new HashMap<String, ArrayList<CorporationCategoryBO>>();
	public static Long familyDetailId = System.currentTimeMillis();
	
	private String functionType;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		Bundle mBundle = this.getArguments();

		if (mBundle != null) {
			checkType = mBundle.getString("checkType", "");
			functionType = mBundle.getString("functionType", "");
			corporationBO = (CorporationBO) mBundle.getSerializable("mCorporationBOKey");
			catagoryInforBeans = (CorporationCategoryBO) mBundle.getSerializable("catagoryInforBeans");
		}

		if (mView == null) {
			mView = inflater.inflate(R.layout.layout_insert_update_coporation, container, false);
			unit(mView);
		}

		if (!CommonActivity.isNullOrEmptyArray(arrProvince)) {
			arrProvince = new ArrayList<AreaBean>();
		}
		initProvince();

		return mView;
	}

	@Override
	public void onResume() {
		super.onResume();
		if (title == null || title.isEmpty()) {
			setTitleActionBar(getActivity().getString(R.string.thuthapttinkhdn));
		} else {
			setTitleActionBar(title);
		}
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btnnhapmoi:

			if (catagoryInforBeans != null
					&& !CommonActivity.isNullOrEmptyArray(catagoryInforBeans.getLstCatagoryInfors())) {

				if (validate2(catagoryInforBeans.getLstCatagoryInfors())) {
					getActivity().onBackPressed();
				} else {
					CommonActivity.toastShort(getActivity(), R.string.input_blank_all);
				}
			} else {

				if (Constant.ACCEPT_CORP.equals(checkType)) {
					OnClickListener onclicConfirm = new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							AsyncexecuteCorporationInfo asyncexecuteCorporationInfo = new AsyncexecuteCorporationInfo(
									getActivity());
							asyncexecuteCorporationInfo.execute();
						}
					};
					String des = "";
					if (Constant.ACCEPT_CORP.equals(checkType)) {
						des = getActivity().getString(R.string.confirmacept);
					} else {
						des = getActivity().getString(R.string.confirmUpdate);
					}

					CommonActivity.createDialog(getActivity(), des, getActivity().getString(R.string.app_name),
							getActivity().getString(R.string.ok), getActivity().getString(R.string.cancel),
							onclicConfirm, null).show();
				} else {
					if (hashMap != null && hashMap.size() == 0) {
						CommonActivity.toastShort(getActivity(), R.string.chonhodanitnhat);
					} else {
						if (validate2(lstCorporationCategoryBO)) {

							OnClickListener onclicConfirm = new OnClickListener() {

								@Override
								public void onClick(View arg0) {
									AsyncexecuteCorporationInfo asyncexecuteCorporationInfo = new AsyncexecuteCorporationInfo(
											getActivity());
									asyncexecuteCorporationInfo.execute();
								}
							};
							String des = "";
							if (Constant.ACCEPT_CORP.equals(checkType)) {
								des = getActivity().getString(R.string.confirmacept);
							} else {
								des = getActivity().getString(R.string.confirmUpdate);
							}

							CommonActivity.createDialog(getActivity(), des, getActivity().getString(R.string.app_name),
									getActivity().getString(R.string.ok), getActivity().getString(R.string.cancel),
									onclicConfirm, null).show();

						} else {
							CommonActivity.toastShort(getActivity(), R.string.input_blank_all);
						}

					}
				}

			}
			break;
		case R.id.btncancel:
			// tu choi
			checkType = Constant.REFUSE_CORP;
			if (!CommonActivity.isNullOrEmpty(corporationBO)) {
				showDialogNote(corporationBO);
			}
			// getActivity().finish();
			break;
		default:
			break;
		}

	}

	@Override
	public void unit(View v) {
		lnttindn = (LinearLayout) v.findViewById(R.id.lnttindn);
		lnttindn.setVisibility(View.GONE);
		lvcategory = (ExpandableHeightListView) v.findViewById(R.id.lvcategory);
		lvcategory.setExpanded(true);

		lvcategory.setOnItemClickListener(this);

		btnnhapmoi = (Button) v.findViewById(R.id.btnnhapmoi);
		btnnhapmoi.setOnClickListener(this);
		btncancel = (Button) v.findViewById(R.id.btncancel);

		if (!CommonActivity.isNullOrEmpty(checkType) && "ACCEPT_CORP".equals(checkType)) {
			btnnhapmoi.setText(getActivity().getString(R.string.btn_approve));
			btncancel.setVisibility(View.VISIBLE);
		} else {
			btnnhapmoi.setText(getActivity().getString(R.string.capnhat));
			btncancel.setVisibility(View.GONE);
		}

		btncancel.setOnClickListener(this);

		if (catagoryInforBeans == null) {
			if (!CommonActivity.isNullOrEmpty(checkType) && "ACCEPT_CORP".equals(checkType)) {
				btnnhapmoi.setText(getActivity().getString(R.string.btn_approve));
				btncancel.setVisibility(View.VISIBLE);
			} else {
				btnnhapmoi.setText(getActivity().getString(R.string.capnhat));
				btncancel.setVisibility(View.GONE);
			}
			GetListCorporationCategoryInfoAsyn getListCorporationCategoryInfoAsyn = new GetListCorporationCategoryInfoAsyn(
					getActivity());
			if (!CommonActivity.isNullOrEmpty(corporationBO)) {
				getListCorporationCategoryInfoAsyn.execute(corporationBO.getCorporationId());
			} else {
				getListCorporationCategoryInfoAsyn.execute("");
			}
		} else if (catagoryInforBeans != null
				&& !CommonActivity.isNullOrEmptyArray(catagoryInforBeans.getLstCatagoryInfors())) {
			btnnhapmoi.setText(getActivity().getString(R.string.luuthongtin));
			// lstCorporationCategoryBO = new
			// ArrayList<CorporationCategoryBO>();

			if (!CommonActivity.isNullOrEmptyArray(catagoryInforBeans.getLstCatagoryInfors())) {
				lstCorporationCategoryBO = catagoryInforBeans.getLstCatagoryInfors();
			}
			// else {
			// lstCorporationCategoryBO.add(catagoryInforBeans);
			// }

			initProvince();

			for (CorporationCategoryBO corporationCategoryBO : lstCorporationCategoryBO) {
				
				if(Constant.ACCEPT_CORP.equals(checkType)){
					corporationCategoryBO.setApprove(true);
				}
				
				if ("TINH".equals(corporationCategoryBO.getInforCode())) {
					provinceUpdate = corporationCategoryBO.getInforValue();
					initDistrict(provinceUpdate);
					ArrayList<DataComboboxBeanJson> lstDataCombobean = new ArrayList<DataComboboxBeanJson>();
					if (!CommonActivity.isNullOrEmptyArray(arrProvince)) {
						for (AreaBean areaBean : arrProvince) {
							DataComboboxBeanJson DataComboboxBeanJson = new DataComboboxBeanJson();
							DataComboboxBeanJson.setCode(areaBean.getProvince());
							DataComboboxBeanJson.setName(areaBean.getNameProvince());
							lstDataCombobean.add(DataComboboxBeanJson);
						}
					}
					corporationCategoryBO.setLstDataCombo(new ArrayList<DataComboboxBeanJson>());
					corporationCategoryBO.getLstDataCombo().addAll(lstDataCombobean);
					break;
				}
			}

			for (CorporationCategoryBO corporationCategoryBO : lstCorporationCategoryBO) {
				if ("HUYEN".equals(corporationCategoryBO.getInforCode())) {
					districtUpdate = corporationCategoryBO.getInforValue();

					initPrecinct(provinceUpdate, districtUpdate);

					ArrayList<DataComboboxBeanJson> lstDataCombobean = new ArrayList<DataComboboxBeanJson>();
					if (!CommonActivity.isNullOrEmptyArray(arrDistrict)) {
						for (AreaBean areaBean : arrDistrict) {
							DataComboboxBeanJson DataComboboxBeanJson = new DataComboboxBeanJson();
							DataComboboxBeanJson.setCode(areaBean.getDistrict());
							DataComboboxBeanJson.setName(areaBean.getNameDistrict());
							lstDataCombobean.add(DataComboboxBeanJson);
						}
					}
					corporationCategoryBO.setLstDataCombo(new ArrayList<DataComboboxBeanJson>());
					corporationCategoryBO.getLstDataCombo().addAll(lstDataCombobean);

					break;
				}
			}

			for (CorporationCategoryBO corporationCategoryBO : lstCorporationCategoryBO) {

				if ("XA".equals(corporationCategoryBO.getInforCode())) {

					precintUpdate = corporationCategoryBO.getInforValue();

					ArrayList<DataComboboxBeanJson> lstDataCombobean = new ArrayList<DataComboboxBeanJson>();
					if (!CommonActivity.isNullOrEmptyArray(arrPrecinct)) {
						for (AreaBean areaBean : arrPrecinct) {
							DataComboboxBeanJson DataComboboxBeanJson = new DataComboboxBeanJson();
							DataComboboxBeanJson.setCode(areaBean.getPrecinct());
							DataComboboxBeanJson.setName(areaBean.getNamePrecint());
							lstDataCombobean.add(DataComboboxBeanJson);
						}
					}
					corporationCategoryBO.setLstDataCombo(new ArrayList<DataComboboxBeanJson>());
					corporationCategoryBO.getLstDataCombo().addAll(lstDataCombobean);

					if (!CommonActivity.isNullOrEmpty(precintUpdate)) {
						GetListGroupAdressAsyncTask getListGroupAdressAsyncTask = new GetListGroupAdressAsyncTask(
								getActivity());
						getListGroupAdressAsyncTask.execute(precintUpdate);
					}
					break;
				}
			}

			// if ("DUONG".equals(corporationCategoryBO.getInforCode())) {
			// if
			// (CommonActivity.isNullOrEmptyArray(corporationCategoryBO.getLstDataCombo()))
			// {
			// ArrayList<DataComboboxBeanJson> lstDataCombobean = new
			// ArrayList<DataComboboxBeanJson>();
			// if (!CommonActivity.isNullOrEmptyArray(arrStreetBlock)) {
			// for (AreaObj areaBean : arrStreetBlock) {
			// DataComboboxBeanJson DataComboboxBeanJson = new DataComboboxBeanJson();
			// DataComboboxBeanJson.setCode(areaBean.getAreaCode());
			// DataComboboxBeanJson.setName(areaBean.getName());
			// lstDataCombobean.add(DataComboboxBeanJson);
			// }
			// }
			// corporationCategoryBO.setLstDataCombo(new
			// ArrayList<DataComboboxBeanJson>());
			// corporationCategoryBO.getLstDataCombo().addAll(lstDataCombobean);
			// }
			//
			// }

			getCatagoryBeanCoporationAdapter = new GetCatagoryBeanCoporationAdapter(lstCorporationCategoryBO,
					getActivity(), FragmentInsertOrUpdateCoporation.this, FragmentInsertOrUpdateCoporation.this);
			lvcategory.setAdapter(getCatagoryBeanCoporationAdapter);
			title = catagoryInforBeans.getInforName();
		}

	}

	@Override
	public void setPermission() {
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		super.onItemClick(arg0, arg1, arg2, arg3);
		CorporationCategoryBO item = (CorporationCategoryBO) arg0.getAdapter().getItem(arg2);
		
		if (item != null) {
			if (!CommonActivity.isNullOrEmptyArray(item.getLstCatagoryInfors())) {
					if (item.getCorpInforDetailId() == null) {
						item.setCorpInforDetailId(familyDetailId++);
						item.setIdInsert(item.getCorporationCategoryId() + "");
						for (CorporationCategoryBO itm : item
								.getLstCatagoryInfors()) {
							if (itm.getParentId() == null) {
								itm.setParentId(item
										.getCorpInforDetailId());
							}
						}
				}
			}
		}
		
		
		if (!CommonActivity.isNullOrEmpty(arrProvince)) {
			initProvince();
		}
		if (!CommonActivity.isNullOrEmptyArray(item.getLstCatagoryInfors())) {
			Bundle bundle = new Bundle();
			bundle.putSerializable("catagoryInforBeans", item);

			bundle.putString("checkType", checkType);
			bundle.putString("functionType", functionType);
			if (!CommonActivity.isNullOrEmpty(corporationBO)) {
				bundle.putSerializable("mCorporationBOKey", corporationBO);
			}

			FragmentInsertOrUpdateCoporation fragment = new FragmentInsertOrUpdateCoporation();
			fragment.setArguments(bundle);
			fragment.setTargetFragment(FragmentInsertOrUpdateCoporation.this, 100);
			ReplaceFragment.replaceFragment(getActivity(), fragment, true);
		} else {
			if (item.getDataType().equals("EMAIL") || item.getDataType().equals("WEBSITE")
					|| item.getDataType().equals("DATE")
					|| (item.getDataType().equals("STRING") || item.getDataType().equals("NUMBER")
							|| item.getDataType().equals("COMBOBOX") || item.getDataType().equals("MULTISELECT"))) {
				if (item.getDataType().equals("COMBOBOX")) {
					if (Constant.ACCEPT_CORP.equals(checkType) || Constant.REFUSE_CORP.equals(checkType)) {
					} else {
						if ("HUYEN".equals(item.getInforCode())) {
							String province = "";
							if (!CommonActivity.isNullOrEmptyArray(lstCorporationCategoryBO)) {
								for (CorporationCategoryBO itemCorpo : lstCorporationCategoryBO) {
									if ("TINH".equals(itemCorpo.getInforCode())) {
										province = itemCorpo.getInforValue();
										break;
									}
								}
							}
							if (CommonActivity.isNullOrEmpty(province)) {
								CommonActivity.createAlertDialog(getActivity(),
										getActivity().getString(R.string.message_pleass_input_province),
										getActivity().getString(R.string.app_name)).show();
							} else {
								showDialogComboBox(item);
							}
						} else if ("XA".equals(item.getInforCode())) {
							String district = "";
							if (!CommonActivity.isNullOrEmptyArray(lstCorporationCategoryBO)) {
								for (CorporationCategoryBO itemCorpo : lstCorporationCategoryBO) {
									if ("HUYEN".equals(itemCorpo.getInforCode())) {
										district = itemCorpo.getInforValue();
										break;
									}
								}
							}
							if (CommonActivity.isNullOrEmpty(district)) {
								CommonActivity.createAlertDialog(getActivity(),
										getActivity().getString(R.string.message_pleass_input_distrist),
										getActivity().getString(R.string.app_name)).show();
							} else {
								showDialogComboBox(item);
							}
						} else if ("DUONG".equals(item.getInforCode())) {
							String streetBlock = "";
							if (!CommonActivity.isNullOrEmptyArray(lstCorporationCategoryBO)) {
								for (CorporationCategoryBO itemCorpo : lstCorporationCategoryBO) {
									if ("XA".equals(itemCorpo.getInforCode())) {
										streetBlock = itemCorpo.getInforValue();
										break;
									}
								}
							}
							if (CommonActivity.isNullOrEmpty(streetBlock)) {
								CommonActivity.createAlertDialog(getActivity(),
										getActivity().getString(R.string.message_pleass_input_precint),
										getActivity().getString(R.string.app_name)).show();
							} else {
								showDialogComboBox(item);
							}
						} else {
							showDialogComboBox(item);
						}

					}
				} else {
					if ("MULTISELECT".equals(item.getDataType())) {
						showDialogMultiSelect(item);
					} else {
						DialogUpdate dialogUpdate = new DialogUpdate(getActivity(), arg2, item.getDataType(), item);
						dialogUpdate.show();
					}
				}

			}
		}
	}

	private class DialogUpdate extends Dialog implements android.view.View.OnClickListener {

		private EditText edtValue;
		private Button btnUpdateDilog;
		private int position;
		private String inputType;
		private TextView txtTitleOnlineDialog;
		private Spinner mSpinner;
		private CorporationCategoryBO catagoryInforBeansItem = null;

		public DialogUpdate(Context context, int position, String inputType,
				CorporationCategoryBO mCatagoryInforBeans) {
			super(context);
			this.position = position;
			this.inputType = inputType;
			this.catagoryInforBeansItem = mCatagoryInforBeans;
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.dialog_family_update_detail);

			edtValue = (EditText) findViewById(R.id.edtValue);

			if (Constant.ACCEPT_CORP.equals(checkType) || Constant.REFUSE_CORP.equals(checkType)) {
				edtValue.setEnabled(false);
			} else {
				edtValue.setEnabled(true);
			}

			txtTitleOnlineDialog = (TextView) findViewById(R.id.txtTitleOnlineDialog);
			btnUpdateDilog = (Button) findViewById(R.id.btnUpdateDialog);
			btnUpdateDilog.setOnClickListener(this);
			Button btncancel = (Button) findViewById(R.id.btncancel);
			btncancel.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					cancel();
				}
			});

			txtTitleOnlineDialog.setText(lstCorporationCategoryBO.get(position).getInforName());

			mSpinner = (Spinner) findViewById(R.id.spShowCriterial);
			edtValue.setText("");
			if (inputType.equals("STRING")) {
				getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
				mSpinner.setVisibility(View.GONE);
				edtValue.setVisibility(View.VISIBLE);
				edtValue.setInputType(InputType.TYPE_CLASS_TEXT);
				int maxLength = 100;
				InputFilter[] FilterArray = new InputFilter[1];
				FilterArray[0] = new InputFilter.LengthFilter(maxLength);
				edtValue.setFilters(FilterArray);
			} else if (inputType.equals("EMAIL")) {
				getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
				mSpinner.setVisibility(View.GONE);
				edtValue.setVisibility(View.VISIBLE);
				edtValue.setInputType(InputType.TYPE_CLASS_TEXT);
				int maxLength = 100;
				InputFilter[] FilterArray = new InputFilter[1];
				FilterArray[0] = new InputFilter.LengthFilter(maxLength);
				edtValue.setFilters(FilterArray);
			} else if (inputType.equals("WEBSITE")) {
				getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
				mSpinner.setVisibility(View.GONE);
				edtValue.setVisibility(View.VISIBLE);
				edtValue.setInputType(InputType.TYPE_CLASS_TEXT);
				int maxLength = 100;
				InputFilter[] FilterArray = new InputFilter[1];
				FilterArray[0] = new InputFilter.LengthFilter(maxLength);
				edtValue.setFilters(FilterArray);
			} else if (inputType.equals("DATE")) {
				mSpinner.setVisibility(View.GONE);
				edtValue.setVisibility(View.VISIBLE);
				edtValue.setInputType(InputType.TYPE_NULL);
				edtValue.setFocusable(false);
				edtValue.setFocusableInTouchMode(false);
				edtValue.setOnClickListener(editTextListener);
			} else if (inputType.equals("NUMBER")) {
				getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
				mSpinner.setVisibility(View.GONE);
				edtValue.setVisibility(View.VISIBLE);
				edtValue.setInputType(InputType.TYPE_CLASS_NUMBER);
				int maxLength = 15;
				InputFilter[] FilterArray = new InputFilter[1];
				FilterArray[0] = new InputFilter.LengthFilter(maxLength);
				edtValue.setFilters(FilterArray);
			}
			if (catagoryInforBeansItem != null) {
				if (!CommonActivity.isNullOrEmpty(catagoryInforBeansItem.getInforValue())) {
					edtValue.setText(catagoryInforBeansItem.getInforValue());
				} else {
					edtValue.setText("");
				}

			} else {
				edtValue.setText("");
				edtValue.setHint(getResources().getString(R.string.enterDesc));
			}

		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnUpdateDialog:
				String content = edtValue.getText().toString().trim();
				if ("1".equals(catagoryInforBeansItem.getIsRequire()) && CommonActivity.isNullOrEmpty(content)) {
					Toast.makeText(getActivity(), getResources().getString(R.string.contentisempty), Toast.LENGTH_LONG)
							.show();
				} else if (StringUtils.CheckCharSpecical_1(content)) {
					Toast.makeText(getActivity(), getResources().getString(R.string.checkcharspecical_),
							Toast.LENGTH_LONG).show();
				} else if (inputType.equals("WEBSITE") && !Patterns.WEB_URL.matcher(content).matches()) {
					Toast.makeText(getActivity(), getResources().getString(R.string.checkwebstite), Toast.LENGTH_LONG)
							.show();
				} else if (inputType.equals("EMAIL") && !CommonActivity.isEmailValid(content)) {
					Toast.makeText(getActivity(), getResources().getString(R.string.email_invalid_format),
							Toast.LENGTH_LONG).show();
				} else {
					catagoryInforBeansItem.setInforValue(content);
					String key = catagoryInforBeansItem.getInforCode();

					if (!CommonActivity.isNullOrEmpty(catagoryInforBeansItem.getCorpInforDetailId())) {
						key = key + "_" + catagoryInforBeansItem.getCorpInforDetailId();
					}
					if (!CommonActivity.isNullOrEmpty(catagoryInforBeansItem.getParentCode())) {
						key = key + "_" + catagoryInforBeansItem.getParentCode();
					}
					if (!CommonActivity.isNullOrEmpty(catagoryInforBeansItem.getParentId())) {
						key = key + "_" + catagoryInforBeansItem.getParentId();
					}
					Log.d("key = ", key);

					if (!CommonActivity.isNullOrEmpty(catagoryInforBeansItem.getPath())
							&& catagoryInforBeansItem.getPath().contains(Constant.PATH_BUSSNESS)) {
						hashMapService.put(key, catagoryInforBeansItem);
					} else {
						hashMap.put(key, catagoryInforBeansItem);
					}

					getCatagoryBeanCoporationAdapter.notifyDataSetChanged();
					dismiss();
				}
				break;

			default:
				break;
			}
		}

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

			DatePickerDialog pic = new FixedHoloDatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, datePickerListener, cal.get(Calendar.YEAR),
					cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
			pic.getDatePicker().setTag(view);
			pic.show();
		}
	};

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
			Object obj = view.getTag();
			if (obj != null && obj instanceof EditText) {
				EditText editText = (EditText) obj;
				editText.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
			}
		}
	};

	private class GetListCorporationCategoryInfoAsyn extends AsyncTask<String, Void, ArrayList<CorporationCategoryBO>> {

		private Context mContext;
		private String errorCode;
		private String description;
		private ProgressDialog progress;

		public GetListCorporationCategoryInfoAsyn(Context context) {
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
		protected ArrayList<CorporationCategoryBO> doInBackground(String... arg0) {
			return getListCorporationCategoryInfo(arg0[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<CorporationCategoryBO> result) {
			super.onPostExecute(result);
			this.progress.dismiss();
			if ("0".equals(errorCode)) {
				if (!CommonActivity.isNullOrEmptyArray(result)) {
					lstCorporationCategoryBO = result;
					
					if (!CommonActivity.isNullOrEmpty(checkType) && Constant.INSERT_CORP.equals(checkType)) {
						hashMapCorporationCategoryBO = new HashMap<String, ArrayList<CorporationCategoryBO>>();
						ArrayList<CorporationCategoryBO> arrCorporationCategoryBO = new ArrayList<CorporationCategoryBO>();
						for (CorporationCategoryBO corporationCategoryBO : lstCorporationCategoryBO) {
							arrCorporationCategoryBO.add(resetValueCatagoryInforBeansClone(corporationCategoryBO));
						}
						
						hashMapCorporationCategoryBO.put(checkType, arrCorporationCategoryBO);
					}

					if (!CommonActivity.isNullOrEmptyArray(lstCorporationCategoryBO)) {
						getCatagoryBeanCoporationAdapter = new GetCatagoryBeanCoporationAdapter(
								lstCorporationCategoryBO, getActivity(), FragmentInsertOrUpdateCoporation.this,
								FragmentInsertOrUpdateCoporation.this);
						lvcategory.setAdapter(getCatagoryBeanCoporationAdapter);
					}

					// if(!CommonActivity.isNullOrEmpty(corporationBO) &&
					// !CommonActivity.isNullOrEmptyArray(lstCorporationCategoryBO)){
					initProvince();

					for (CorporationCategoryBO corporationCategoryBO : lstCorporationCategoryBO) {
						if(Constant.ACCEPT_CORP.equals(checkType)){
							corporationCategoryBO.setApprove(true);
						}
						if ("TINH".equals(corporationCategoryBO.getInforCode())) {
							provinceUpdate = corporationCategoryBO.getInforValue();
							initDistrict(provinceUpdate);
							ArrayList<DataComboboxBeanJson> lstDataCombobean = new ArrayList<DataComboboxBeanJson>();
							if (!CommonActivity.isNullOrEmptyArray(arrProvince)) {
								for (AreaBean areaBean : arrProvince) {
									DataComboboxBeanJson DataComboboxBeanJson = new DataComboboxBeanJson();
									DataComboboxBeanJson.setCode(areaBean.getProvince());
									DataComboboxBeanJson.setName(areaBean.getNameProvince());
									lstDataCombobean.add(DataComboboxBeanJson);
								}
							}
							corporationCategoryBO.setLstDataCombo(new ArrayList<DataComboboxBeanJson>());
							corporationCategoryBO.getLstDataCombo().addAll(lstDataCombobean);
							break;
						}
					}

					for (CorporationCategoryBO corporationCategoryBO : lstCorporationCategoryBO) {
						if ("HUYEN".equals(corporationCategoryBO.getInforCode())) {
							districtUpdate = corporationCategoryBO.getInforValue();

							initPrecinct(provinceUpdate, districtUpdate);

							ArrayList<DataComboboxBeanJson> lstDataCombobean = new ArrayList<DataComboboxBeanJson>();
							if (!CommonActivity.isNullOrEmptyArray(arrDistrict)) {
								for (AreaBean areaBean : arrDistrict) {
									DataComboboxBeanJson DataComboboxBeanJson = new DataComboboxBeanJson();
									DataComboboxBeanJson.setCode(areaBean.getDistrict());
									DataComboboxBeanJson.setName(areaBean.getNameDistrict());
									lstDataCombobean.add(DataComboboxBeanJson);
								}
							}
							corporationCategoryBO.setLstDataCombo(new ArrayList<DataComboboxBeanJson>());
							corporationCategoryBO.getLstDataCombo().addAll(lstDataCombobean);

							break;
						}
					}

					for (CorporationCategoryBO corporationCategoryBO : lstCorporationCategoryBO) {

						if ("XA".equals(corporationCategoryBO.getInforCode())) {

							precintUpdate = corporationCategoryBO.getInforValue();

							ArrayList<DataComboboxBeanJson> lstDataCombobean = new ArrayList<DataComboboxBeanJson>();
							if (!CommonActivity.isNullOrEmptyArray(arrPrecinct)) {
								for (AreaBean areaBean : arrPrecinct) {
									DataComboboxBeanJson DataComboboxBeanJson = new DataComboboxBeanJson();
									DataComboboxBeanJson.setCode(areaBean.getPrecinct());
									DataComboboxBeanJson.setName(areaBean.getNamePrecint());
									lstDataCombobean.add(DataComboboxBeanJson);
								}
							}
							corporationCategoryBO.setLstDataCombo(new ArrayList<DataComboboxBeanJson>());
							corporationCategoryBO.getLstDataCombo().addAll(lstDataCombobean);

							if (!CommonActivity.isNullOrEmpty(precintUpdate)) {
								GetListGroupAdressAsyncTask getListGroupAdressAsyncTask = new GetListGroupAdressAsyncTask(
										getActivity());
								getListGroupAdressAsyncTask.execute(precintUpdate);
							}
							break;
						}
					}

					// }

				} else {
					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.no_data),
							getActivity().getString(R.string.app_name));
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

		private ArrayList<CorporationCategoryBO> getListCorporationCategoryInfo(String corpId) {
			String original = null;
			ArrayList<CorporationCategoryBO> lstCorporation = new ArrayList<CorporationCategoryBO>();
			try {

				if (!CommonActivity.isNullOrEmpty(checkType) && Constant.INSERT_CORP.equals(checkType)) {
					if (hashMapCorporationCategoryBO != null && hashMapCorporationCategoryBO.size() > 0) {
						lstCorporation.addAll(hashMapCorporationCategoryBO.get(checkType));
					}
					if (!CommonActivity.isNullOrEmptyArray(lstCorporation)) {
						errorCode = "0";
						return lstCorporation;
					}

				}

				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListCorporationCategoryInfo");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListCorporationCategoryInfo>");
				rawData.append("<input>");
				rawData.append("<token>" + Session.getToken() + "</token>");
				if (!CommonActivity.isNullOrEmpty(corpId)) {
					rawData.append("<corpId>" + corpId);
					rawData.append("</corpId>");
				} else {
					rawData.append("<corpId>" + "");
					rawData.append("</corpId>");
				}
				rawData.append("</input>");
				rawData.append("</ws:getListCorporationCategoryInfo>");
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				original = input.sendRequestJson(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListCorporationCategoryInfo");
				
				Log.i("Responseeeeeeeeee", original);
//				CommonOutput output = input.parseGWResponse(response);
//				original = output.getOriginal();
//				
//				original = original.replaceAll("<return>", "").replace("</return>", "").replace("&quot;", "\"");
//				Log.d("originalllllllll", original);
//				Serializer serializer = new Persister();
//				ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
//				if (parseOuput != null) {
//					errorCode = parseOuput.getErrorCode();
//					description = parseOuput.getDescription();
//					lstCorporation = parseOuput.getLstCorporationCategoryBO();
//				}
				
			     Gson gson = new Gson();
			     ParseOuputJson parseOuput = gson.fromJson(original, ParseOuputJson.class);
			     
					if (parseOuput != null ) {
					errorCode = parseOuput.getErrorCode();
					description = parseOuput.getDescription();
					lstCorporation = parseOuput.getLstCorporationCategoryBO();
				}
			     
//			        Reader reader = new InputStreamReader(source);
//			        SearchResponse response = gson.fromJson(reader, SearchResponse.class);
				
				
				
				

			} catch (Exception e) {
				Log.d("getListCorporationCategoryInfo : ex", e.toString());
			}

			return lstCorporation;
		}

	}

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
			arrDistrict = dal.getLstDistrictAreaCode(province);
			dal.close();
		} catch (Exception ex) {
			Log.e("initDistrict", ex.toString());
		}
	}

	// lay phuong/xa theo tinh,qhuyen
	private void initPrecinct(String province, String district) {
		try {
			GetAreaDal dal = new GetAreaDal(getActivity());
			dal.open();
			arrPrecinct = dal.getLstPrecinctAreaCode(province, district);
			dal.close();
		} catch (Exception ex) {
			Log.e("initPrecinct", ex.toString());
		}
	}

	private void showDialogMultiSelect(final CorporationCategoryBO corporationCategoryBO) {
		dialogMultiSelect = new Dialog(getActivity());
		dialogMultiSelect.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogMultiSelect.setTitle(corporationCategoryBO.getInforName());
		dialogMultiSelect.setContentView(R.layout.create_multiselect_dialog);

		LinearLayout lnMultiSelect = (LinearLayout) dialogMultiSelect.findViewById(R.id.lnMultiSelect);
		if (Constant.ACCEPT_CORP.equals(checkType) || Constant.REFUSE_CORP.equals(checkType)) {
			for (int i = 0; i < lnMultiSelect.getChildCount(); i++) {
				View child = lnMultiSelect.getChildAt(i);
				child.setEnabled(false);
			}
		}
		lstDataComboboxBeanJson = new ArrayList<DataComboboxBeanJson>();
		lstDataComboboxBeanJson = corporationCategoryBO.getLstDataCombo();
		
		for (DataComboboxBeanJson itemData : lstDataComboboxBeanJson) {
			if (!CommonActivity.isNullOrEmpty(checkType) && Constant.ACCEPT_CORP.equals(checkType)) {
				itemData.setCheckApprove(true);
			}
		}
		TextView tvDialogTitle = (TextView) dialogMultiSelect.findViewById(R.id.tvDialogTitle);
		tvDialogTitle.setText(corporationCategoryBO.getInforName());
		if (!CommonActivity.isNullOrEmpty(corporationCategoryBO.getInforValue()) && !"1".equals(checkType)) {
			String[] lstValue = corporationCategoryBO.getInforValue().trim().split(";");
			if (lstValue != null && lstValue.length > 0) {
				for (String string : lstValue) {
					for (DataComboboxBeanJson itemData : lstDataComboboxBeanJson) {
						if (string.equals(itemData.getCode())) {
							itemData.setChecked(true);
								
						}
					}
				}
			}
		}else{
			for (DataComboboxBeanJson itemData : lstDataComboboxBeanJson) {
					itemData.setChecked(false);
			}
		}

		EditText edtSearch = (EditText) dialogMultiSelect.findViewById(R.id.edtSearch);
		edtSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				nultiCheckAdapter.filter(arg0.toString());

			}
		});
		ListView lvMultiSelect = (ListView) dialogMultiSelect.findViewById(R.id.lvMultiSelect);

		nultiCheckAdapter = new MultiCheckAdapter(getActivity(), corporationCategoryBO.getLstDataCombo(),
				FragmentInsertOrUpdateCoporation.this);
		lvMultiSelect.setAdapter(nultiCheckAdapter);
		nultiCheckAdapter.notifyDataSetChanged();
		Button btnupdate = (Button) dialogMultiSelect.findViewById(R.id.btnupdate);
		btnupdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (CommonActivity.isNullOrEmpty(lstDataComboboxBeanJson)) {
					lstDataComboboxBeanJson = corporationCategoryBO.getLstDataCombo();
				}
				if (!validateSelect(lstDataComboboxBeanJson)) {
					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.isSelectMulti),
							getActivity().getString(R.string.app_name)).show();
				} else {
					String value = "";
					String join = "";
					if (!CommonActivity.isNullOrEmptyArray(lstDataComboboxBeanJson)) {
						for (DataComboboxBeanJson item : lstDataComboboxBeanJson) {
							if (item.isChecked()) {
								value = value + join + item.getCode();
								join = ";";
							}
						}
					}

					if (!CommonActivity.isNullOrEmpty(value)) {
						String[] lsttm = value.split(";");
						if (lsttm.length < 2) {
							value = value.replace(";", "");
						}
					}

					corporationCategoryBO.setInforValue(value);
					String key = corporationCategoryBO.getInforCode();
					Log.d("key = ", key);

					if (!CommonActivity.isNullOrEmpty(corporationCategoryBO.getCorpInforDetailId())) {
						key = key + "_" + corporationCategoryBO.getCorpInforDetailId();
					}
					if (!CommonActivity.isNullOrEmpty(corporationCategoryBO.getParentCode())) {
						key = key + "_" + corporationCategoryBO.getParentCode();
					}
					if (!CommonActivity.isNullOrEmpty(corporationCategoryBO.getParentId())) {
						key = key + "_" + corporationCategoryBO.getParentId();
					}
					if (!CommonActivity.isNullOrEmpty(corporationCategoryBO.getPath())
							&& corporationCategoryBO.getPath().contains(Constant.PATH_BUSSNESS)) {
						hashMapService.put(key, corporationCategoryBO);
					} else {
						hashMap.put(key, corporationCategoryBO);
					}

					getCatagoryBeanCoporationAdapter.notifyDataSetChanged();
					dialogMultiSelect.cancel();
				}

			}
		});

		Button btnCancel = (Button) dialogMultiSelect.findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialogMultiSelect.cancel();
			}
		});

		dialogMultiSelect.show();
	}

	private boolean validateSelect(ArrayList<DataComboboxBeanJson> lstData) {
		boolean isCheck = false;

		for (DataComboboxBeanJson DataComboboxBeanJson : lstData) {
			if (DataComboboxBeanJson.isChecked()) {
				isCheck = true;
			}
		}
		return isCheck;
	}

	@Override
	public void onChangeCheckedMulti(DataComboboxBeanJson saleTrans, ArrayList<DataComboboxBeanJson> lstData) {
		for (DataComboboxBeanJson item : lstDataComboboxBeanJson) {
			if (item.getCode().equals(saleTrans.getCode())) {
				item.setChecked(!item.isChecked());
				break;
			}
		}
	}

	private class GetListGroupAdressAsyncTask extends AsyncTask<String, Void, ArrayList<AreaObj>> {
		private Context context = null;
		private XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private ProgressDialog progress;

		public GetListGroupAdressAsyncTask(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			this.progress = new ProgressDialog(this.context);
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(R.string.getdataing));
			this.progress.show();

		}

		@Override
		protected ArrayList<AreaObj> doInBackground(String... params) {
			return getListGroupAddress(params[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<AreaObj> result) {
			super.onPostExecute(result);
			if (progress != null && progress.isShowing()) {
				progress.dismiss();
			}
			if (errorCode.equals("0")) {
				if (result != null && result.size() > 0) {
					arrStreetBlock = result;
					if (!CommonActivity.isNullOrEmpty(catagoryInforBeans)) {
						for (CorporationCategoryBO corporationCategoryBO : lstCorporationCategoryBO) {
							if ("DUONG".equals(corporationCategoryBO.getInforCode())) {
								ArrayList<DataComboboxBeanJson> lstDataCombobean = new ArrayList<DataComboboxBeanJson>();
								if (!CommonActivity.isNullOrEmptyArray(arrStreetBlock)) {
									for (AreaObj areaBean : arrStreetBlock) {
										DataComboboxBeanJson DataComboboxBeanJson = new DataComboboxBeanJson();
										DataComboboxBeanJson.setCode(areaBean.getAreaCode());
										DataComboboxBeanJson.setName(areaBean.getName());
										lstDataCombobean.add(DataComboboxBeanJson);
									}
								}
								corporationCategoryBO.setLstDataCombo(new ArrayList<DataComboboxBeanJson>());
								corporationCategoryBO.getLstDataCombo().addAll(lstDataCombobean);
								break;
							}
						}
						if (getCatagoryBeanCoporationAdapter != null) {
							getCatagoryBeanCoporationAdapter.notifyDataSetChanged();
						}
					}
				} else {
					arrStreetBlock = new ArrayList<AreaObj>();
				}
			} else {
				arrStreetBlock = new ArrayList<AreaObj>();
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
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

		private ArrayList<AreaObj> getListGroupAddress(String parentCode) {
			if (parentCode == null || parentCode.isEmpty()) {
				return new ArrayList<AreaObj>();
			}
			ArrayList<AreaObj> listGroupAdress = null;
			listGroupAdress = new CacheDatabaseManager(MainActivity.getInstance()).getListCacheStreetBlock(parentCode);

			if (listGroupAdress != null && !listGroupAdress.isEmpty()) {
				errorCode = "0";
				return listGroupAdress;
			}
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListArea");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListArea>");
				rawData.append("<input>");
				rawData.append("<token>" + Session.getToken() + "</token>");
				rawData.append("<parentCode>" + parentCode + "</parentCode>");
				rawData.append("</input>");
				rawData.append("</ws:getListArea>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(), "mbccs_getListArea");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original group", response);

				// ==== parse xml list ip
				listGroupAdress = parserListGroup(original);
				if (listGroupAdress != null && !listGroupAdress.isEmpty()) {
					new CacheDatabaseManager(MainActivity.getInstance()).insertCacheStreetBlock(listGroupAdress,
							parentCode);
				}
			} catch (Exception e) {
				Log.d("getListGroupAddress", e.toString());
			}
			return listGroupAdress;
		}

		public ArrayList<AreaObj> parserListGroup(String original) {
			ArrayList<AreaObj> listGroupAdress = new ArrayList<AreaObj>();
			Document doc = parse.getDomElement(original);
			NodeList nl = doc.getElementsByTagName("return");
			NodeList nodechild = null;
			for (int i = 0; i < nl.getLength(); i++) {
				Element e2 = (Element) nl.item(i);
				errorCode = parse.getValue(e2, "errorCode");
				description = parse.getValue(e2, "description");
				Log.d("errorCode", errorCode);
				nodechild = doc.getElementsByTagName("lstArea");
				for (int j = 0; j < nodechild.getLength(); j++) {
					Element e1 = (Element) nodechild.item(j);
					AreaObj areaObject = new AreaObj();
					areaObject.setName(parse.getValue(e1, "name"));
					Log.d("name area: ", areaObject.getName());
					areaObject.setAreaCode(parse.getValue(e1, "streetBlock"));
					listGroupAdress.add(areaObject);
				}
			}
			return listGroupAdress;
		}
	}

	private boolean validate2(List<CorporationCategoryBO> lstCatagoryInforBeansBk) {
		boolean result = true;

		if (lstCatagoryInforBeansBk == null) {
			CommonActivity.toastShort(getActivity(), R.string.chonhodanitnhat);
			return false;
		}
		if (lstCatagoryInforBeansBk.size() == 0 || lstCatagoryInforBeansBk.size() == 1) {
			CommonActivity.toastShort(getActivity(), R.string.chonhodanitnhat);
			return false;
		}

		for (CorporationCategoryBO item : lstCatagoryInforBeansBk) {

			if (!CommonActivity.isNullOrEmptyArray(item.getLstCatagoryInfors())) {
				for (CorporationCategoryBO itemChild : item.getLstCatagoryInfors()) {
					if ("1".equals(itemChild.getIsRequire())
							&& CommonActivity.isNullOrEmpty(itemChild.getInforValue())) {
						result = false;
					}
				}
			} else {
				if ("1".equals(item.getIsRequire()) && CommonActivity.isNullOrEmpty(item.getInforValue())) {
					result = false;
				}
			}

		}
		return result;
	}

	private class AsyncexecuteCorporationInfo extends AsyncTask<String, String, ParseOuput> {
		private ProgressDialog progress;
		private String errorCode;
		private String description;

		public AsyncexecuteCorporationInfo(Context context) {
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
		protected ParseOuput doInBackground(String... params) {
			return executeCorporationInfo();
		}

		@Override
		protected void onPostExecute(final ParseOuput result) {
			super.onPostExecute(result);
			progress.dismiss();
			if ("0".equals(errorCode)) {
				if (dialogNote != null) {
					dialogNote.cancel();
				}
				if (description == null || description.isEmpty()) {

					if (Constant.ACCEPT_CORP.equals(checkType)) {
						description = getActivity().getString(R.string.acceptsuccess);
					} else if (Constant.REFUSE_CORP.equals(checkType)) {
						description = getActivity().getString(R.string.refusesuccess);
					} else {
						description = getString(R.string.updatesucess);
					}
				}
				
				OnClickListener onclickBack = new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						
						Bundle mBundle = new Bundle();
						if (!CommonActivity.isNullOrEmpty(result.getCorporationBO())) {
							mBundle.putSerializable("mCorporationBOKey", result.getCorporationBO());
						}
						
						FragmentSearchComporation fragUpdateFamily = new FragmentSearchComporation();
						fragUpdateFamily.setArguments(mBundle);
						ReplaceFragment.replaceFragment(getActivity(), fragUpdateFamily, false);
						if (corporationBO != null) {
							corporationBO = null;
						}

						if (hashMap != null) {
							hashMap = new HashMap<String, CorporationCategoryBO>();
						}

						if (hashMapService != null) {
							hashMapService = new HashMap<String, CorporationCategoryBO>();
						}

					}
				};
				
				CommonActivity.createAlertDialog(getActivity(), description, getString(R.string.app_name), onclickBack)
						.show();
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

		private ParseOuput executeCorporationInfo() {
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

				List<CorporationCategoryBO> lstCatagoryInfor = new ArrayList<CorporationCategoryBO>(hashMap.values());

				Log.d(this.getClass().getSimpleName(),
						" executeCorporationInfo title " + title + " lstCatagoryInfor " + lstCatagoryInfor.size());

				String excuteType = "";

				if (Constant.UPDATE_CORP.equals(checkType)) {
					excuteType = "2";
				} else if (Constant.ACCEPT_CORP.equals(checkType)) {
					excuteType = "4";
				} else if (Constant.REFUSE_CORP.equals(checkType)) {
					excuteType = "5";
				} else {
					excuteType = "1";
				}
				rawData.append("<executeType>" + excuteType);
				rawData.append("</executeType>");
				rawData.append("<corporationBO>");

				if (!CommonActivity.isNullOrEmpty(edtNote)
						&& !CommonActivity.isNullOrEmpty(edtNote.getText().toString())) {
					rawData.append("<note>"
							+ StringUtils.escapeHtml(CommonActivity.getNormalText(edtNote.getText().toString())));
					rawData.append("</note>");
				}

				if (corporationBO != null && !CommonActivity.isNullOrEmpty(corporationBO.getCorporationId())) {
					rawData.append("<corporationId>");
					rawData.append(corporationBO.getCorporationId());
					rawData.append("</corporationId>");

				}

				if ("4".equals(excuteType) || "5".equals(excuteType)) {

				} else {
					for (CorporationCategoryBO item : lstCatagoryInfor) {
						rawData.append(item.toXML());
					}
				}

				rawData.append("</corporationBO>");

				if ("2".equals(excuteType) || "1".equals(excuteType)) {
					List<CorporationCategoryBO> lstCatagoryInforService = new ArrayList<CorporationCategoryBO>(
							hashMapService.values());

					for (CorporationCategoryBO item : lstCatagoryInforService) {
						rawData.append(item.toXML());
					}

				}

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
				Serializer serializer = new Persister();
				ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
				if (parseOuput != null) {
					errorCode = parseOuput.getErrorCode();
					description = parseOuput.getDescription();
					return parseOuput;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}
	}



	private void showDialogComboBox(final CorporationCategoryBO corporationCategoryBO) {
		dialogCombobox = new Dialog(getActivity());
		dialogCombobox.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogCombobox.setTitle(corporationCategoryBO.getInforName());
		dialogCombobox.setContentView(R.layout.create_multiselect_dialog);

		TextView tvDialogTitle = (TextView) dialogCombobox.findViewById(R.id.tvDialogTitle);
		tvDialogTitle.setText(corporationCategoryBO.getInforName());
		EditText edtSearch = (EditText) dialogCombobox.findViewById(R.id.edtSearch);

		if ("TINH".equals(corporationCategoryBO.getInforCode())) {
			if (!CommonActivity.isNullOrEmptyArray(arrProvince)) {
				initProvince();
			}
			ArrayList<DataComboboxBeanJson> lstDataCombobean = new ArrayList<DataComboboxBeanJson>();
			if (!CommonActivity.isNullOrEmptyArray(arrProvince)) {
				for (AreaBean areaBean : arrProvince) {
					if(!CommonActivity.isNullOrEmpty(functionType) && "VT".equals(functionType)){
						DataComboboxBeanJson DataComboboxBeanJson = new DataComboboxBeanJson();
						DataComboboxBeanJson.setCode(areaBean.getProvince());
						DataComboboxBeanJson.setName(areaBean.getNameProvince());
						lstDataCombobean.add(DataComboboxBeanJson);
						edtSearch.setEnabled(true);
					}else{
						if(areaBean.getProvince().equals(Session.province)){
							DataComboboxBeanJson DataComboboxBeanJson = new DataComboboxBeanJson();
							DataComboboxBeanJson.setCode(areaBean.getProvince());
							DataComboboxBeanJson.setName(areaBean.getNameProvince());
							lstDataCombobean.add(DataComboboxBeanJson);
							edtSearch.setEnabled(false);
							break;
						}
					}
					
					
				}
			}
			corporationCategoryBO.setLstDataCombo(new ArrayList<DataComboboxBeanJson>());
			corporationCategoryBO.getLstDataCombo().addAll(lstDataCombobean);

		}
		if ("HUYEN".equals(corporationCategoryBO.getInforCode())) {
			ArrayList<DataComboboxBeanJson> lstDataCombobean = new ArrayList<DataComboboxBeanJson>();
			if (!CommonActivity.isNullOrEmptyArray(arrDistrict)) {
				for (AreaBean areaBean : arrDistrict) {
					if(!CommonActivity.isNullOrEmpty(functionType) && "Huyen".equals(functionType)){
						if(!CommonActivity.isNullOrEmpty(Session.district) && areaBean.getDistrict().equals(Session.province+Session.district)){
							lstDataCombobean = new ArrayList<DataComboboxBeanJson>();
							DataComboboxBeanJson DataComboboxBeanJson = new DataComboboxBeanJson();
							DataComboboxBeanJson.setCode(areaBean.getDistrict());
							DataComboboxBeanJson.setName(areaBean.getNameDistrict());
							lstDataCombobean.add(DataComboboxBeanJson);
							edtSearch.setEnabled(false);
							break;
						}
					}else{
						DataComboboxBeanJson DataComboboxBeanJson = new DataComboboxBeanJson();
						DataComboboxBeanJson.setCode(areaBean.getDistrict());
						DataComboboxBeanJson.setName(areaBean.getNameDistrict());
						lstDataCombobean.add(DataComboboxBeanJson);
						edtSearch.setEnabled(true);
					}
				}
			
			}
			corporationCategoryBO.setLstDataCombo(new ArrayList<DataComboboxBeanJson>());
			corporationCategoryBO.getLstDataCombo().addAll(lstDataCombobean);

		}
		if ("XA".equals(corporationCategoryBO.getInforCode())) {
			ArrayList<DataComboboxBeanJson> lstDataCombobean = new ArrayList<DataComboboxBeanJson>();
			if (!CommonActivity.isNullOrEmptyArray(arrPrecinct)) {
				for (AreaBean areaBean : arrPrecinct) {
					DataComboboxBeanJson DataComboboxBeanJson = new DataComboboxBeanJson();
					DataComboboxBeanJson.setCode(areaBean.getPrecinct());
					DataComboboxBeanJson.setName(areaBean.getNamePrecint());
					lstDataCombobean.add(DataComboboxBeanJson);
				}
			}
			corporationCategoryBO.setLstDataCombo(new ArrayList<DataComboboxBeanJson>());
			corporationCategoryBO.getLstDataCombo().addAll(lstDataCombobean);

		}

		if ("DUONG".equals(corporationCategoryBO.getInforCode())) {
			ArrayList<DataComboboxBeanJson> lstDataCombobean = new ArrayList<DataComboboxBeanJson>();
			if (!CommonActivity.isNullOrEmptyArray(arrStreetBlock)) {
				for (AreaObj areaBean : arrStreetBlock) {
					DataComboboxBeanJson DataComboboxBeanJson = new DataComboboxBeanJson();
					DataComboboxBeanJson.setCode(areaBean.getAreaCode());
					DataComboboxBeanJson.setName(areaBean.getName());
					lstDataCombobean.add(DataComboboxBeanJson);
				}
			}
			corporationCategoryBO.setLstDataCombo(new ArrayList<DataComboboxBeanJson>());
			corporationCategoryBO.getLstDataCombo().addAll(lstDataCombobean);
		}
		
		if(!CommonActivity.isNullOrEmpty(corporationCategoryBO.getInforValue())){
			if(!CommonActivity.isNullOrEmptyArray(corporationCategoryBO.getLstDataCombo())){
				for (DataComboboxBeanJson item : corporationCategoryBO.getLstDataCombo()) {
					if(corporationCategoryBO.getInforValue().equals(item.getCode())){
						edtSearch.setText(item.getName());
						break;
					}
				}
			}
		}
		
//		else{
//			if("TINH".equals(corporationCategoryBO.getInforCode())){
//				if(!CommonActivity.isNullOrEmptyArray(corporationCategoryBO.getLstDataCombo())){
//					for (DataComboboxBeanJson item : corporationCategoryBO.getLstDataCombo()) {
//						if(Session.province.equals(item.getCode())){
//							edtSearch.setText(item.getName());
//							break;
//						}
//					}
//				}
//			}
//		}
		
		edtSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				comBoBoxAdapter.filter(arg0.toString());

			}
		});
		if (!CommonActivity.isNullOrEmpty(corporationCategoryBO)
				&& !CommonActivity.isNullOrEmptyArray(corporationCategoryBO.getLstDataCombo())) {
			if (!CommonActivity.isNullOrEmpty(corporationCategoryBO.getInforValue())) {
				for (DataComboboxBeanJson itemdataCombo : corporationCategoryBO.getLstDataCombo()) {
					if (corporationCategoryBO.getInforValue().equals(itemdataCombo)) {
						edtSearch.setText(itemdataCombo.getName());
						break;
					}
				}
			}
		}
		ListView lvMultiSelect = (ListView) dialogCombobox.findViewById(R.id.lvMultiSelect);

		comBoBoxAdapter = new ComBoBoxAdapter(getActivity(), corporationCategoryBO.getLstDataCombo());
		lvMultiSelect.setAdapter(comBoBoxAdapter);

		lvMultiSelect.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				DataComboboxBeanJson item = (DataComboboxBeanJson) arg0.getAdapter().getItem(arg2);

				if (item != null) {
					corporationCategoryBO.setNameValue(item.getName());
					corporationCategoryBO.setInforValue(item.getCode());

					if ("TINH".equals(corporationCategoryBO.getInforCode())) {
						if (!CommonActivity.isNullOrEmptyArray(lstCorporationCategoryBO)) {
							for (CorporationCategoryBO itemCorpo : lstCorporationCategoryBO) {
								if ("HUYEN".equals(itemCorpo.getInforCode())) {
									itemCorpo.setInforValue("");
								} else if ("XA".equals(itemCorpo.getInforCode())) {
									itemCorpo.setInforValue("");
								} else if ("DUONG".equals(itemCorpo.getInforCode())) {
									itemCorpo.setInforValue("");
								}
							}
						}
						initDistrict(corporationCategoryBO.getInforValue());
					}
					if ("HUYEN".equals(corporationCategoryBO.getInforCode())) {
						if (!CommonActivity.isNullOrEmptyArray(lstCorporationCategoryBO)) {
							for (CorporationCategoryBO itemCorpo : lstCorporationCategoryBO) {
								if ("XA".equals(itemCorpo.getInforCode())) {
									itemCorpo.setInforValue("");
								} else if ("DUONG".equals(itemCorpo.getInforCode())) {
									itemCorpo.setInforValue("");
								}
							}
						}

						String province = "";
						if (!CommonActivity.isNullOrEmptyArray(lstCorporationCategoryBO)) {
							for (CorporationCategoryBO itemCorpo : lstCorporationCategoryBO) {
								if ("TINH".equals(itemCorpo.getInforCode())) {
									province = itemCorpo.getInforValue();
									break;
								}
							}
						}
						initPrecinct(province, corporationCategoryBO.getInforValue());
					}

					if ("XA".equals(corporationCategoryBO.getInforCode())) {
						if (!CommonActivity.isNullOrEmptyArray(lstCorporationCategoryBO)) {
							for (CorporationCategoryBO itemCorpo : lstCorporationCategoryBO) {
								if ("DUONG".equals(itemCorpo.getInforCode())) {
									itemCorpo.setInforValue("");
									break;
								}
							}
						}

//						String province = "";
//						String district = "";
						String precint = "";
						if (!CommonActivity.isNullOrEmptyArray(lstCorporationCategoryBO)) {
//							for (CorporationCategoryBO itemCorpo : lstCorporationCategoryBO) {
//								if ("TINH".equals(itemCorpo.getInforCode())) {
//									province = itemCorpo.getInforValue();
//									break;
//								}
//							}
//							for (CorporationCategoryBO itemCorpo : lstCorporationCategoryBO) {
//								if ("HUYEN".equals(itemCorpo.getInforCode())) {
//									district = itemCorpo.getInforValue();
//									break;
//								}
//							}
							for (CorporationCategoryBO itemCorpo : lstCorporationCategoryBO) {
								if ("XA".equals(itemCorpo.getInforCode())) {
									precint = itemCorpo.getInforValue();
									break;
								}
							}

							for (CorporationCategoryBO itemCorpo : lstCorporationCategoryBO) {
								if ("DC".equals(itemCorpo.getInforCode())) {

									try {
										GetAreaDal dal = new GetAreaDal(getActivity());
										dal.open();
										String fulladdress = dal.getfulladddress(precint);
										itemCorpo.setInforValue(fulladdress);
										dal.close();
										
										String key = itemCorpo.getInforCode();

										Log.d("key = ", key);

										if (!CommonActivity.isNullOrEmpty(itemCorpo.getCorpInforDetailId())) {
											key = key + "_" + itemCorpo.getCorpInforDetailId();
										}
										if (!CommonActivity.isNullOrEmpty(itemCorpo.getParentCode())) {
											key = key + "_" + itemCorpo.getParentCode();
										}
										if (!CommonActivity.isNullOrEmpty(itemCorpo.getParentId())) {
											key = key + "_" + itemCorpo.getParentId();
										}
										if (!CommonActivity.isNullOrEmpty(itemCorpo.getPath())
												&& itemCorpo.getPath().contains(Constant.PATH_BUSSNESS)) {
											hashMapService.put(key, itemCorpo);
										} else {
											hashMap.put(key, itemCorpo);
										}
									} catch (Exception e) {
										e.printStackTrace();
									}
									break;
								}
							}

						}
						GetListGroupAdressAsyncTask getListGroupAdressAsyncTask = new GetListGroupAdressAsyncTask(
								getActivity());
						getListGroupAdressAsyncTask.execute(precint);
					}

					if ("DUONG".equals(corporationCategoryBO.getInforCode())) {
						for (CorporationCategoryBO itemCorpo : lstCorporationCategoryBO) {
							if ("DC".equals(itemCorpo.getInforCode())) {
								String fulladdress = itemCorpo.getInforValue();
								itemCorpo.setInforValue(item.getName() + " " + fulladdress);
								
								String key = itemCorpo.getInforCode();

								Log.d("key = ", key);

								if (!CommonActivity.isNullOrEmpty(itemCorpo.getCorpInforDetailId())) {
									key = key + "_" + itemCorpo.getCorpInforDetailId();
								}
								if (!CommonActivity.isNullOrEmpty(itemCorpo.getParentCode())) {
									key = key + "_" + itemCorpo.getParentCode();
								}
								if (!CommonActivity.isNullOrEmpty(itemCorpo.getParentId())) {
									key = key + "_" + itemCorpo.getParentId();
								}
								if (!CommonActivity.isNullOrEmpty(itemCorpo.getPath())
										&& itemCorpo.getPath().contains(Constant.PATH_BUSSNESS)) {
									hashMapService.put(key, itemCorpo);
								} else {
									hashMap.put(key, itemCorpo);
								}
								
								break;
							}
						}
					}

					String key = corporationCategoryBO.getInforCode();

					Log.d("key = ", key);

					if (!CommonActivity.isNullOrEmpty(corporationCategoryBO.getCorpInforDetailId())) {
						key = key + "_" + corporationCategoryBO.getCorpInforDetailId();
					}
					if (!CommonActivity.isNullOrEmpty(corporationCategoryBO.getParentCode())) {
						key = key + "_" + corporationCategoryBO.getParentCode();
					}
					if (!CommonActivity.isNullOrEmpty(corporationCategoryBO.getParentId())) {
						key = key + "_" + corporationCategoryBO.getParentId();
					}
					if (!CommonActivity.isNullOrEmpty(corporationCategoryBO.getPath())
							&& Constant.PATH_BUSSNESS.contains(corporationCategoryBO.getPath())) {
						hashMapService.put(key, corporationCategoryBO);
					} else {
						hashMap.put(key, corporationCategoryBO);
					}

					getCatagoryBeanCoporationAdapter.notifyDataSetChanged();
					dialogCombobox.cancel();
				}
			}
		});

		Button btnupdate = (Button) dialogCombobox.findViewById(R.id.btnupdate);
		btnupdate.setVisibility(View.GONE);

		Button btnCancel = (Button) dialogCombobox.findViewById(R.id.btnCancel);

		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialogCombobox.cancel();
			}
		});

		dialogCombobox.show();
	}

	private void showDialogNote(final CorporationBO corporationBO) {
		dialogNote = new Dialog(getActivity());
		dialogNote.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogNote.setContentView(R.layout.dialog_note);

		edtNote = (EditText) dialogNote.findViewById(R.id.etreason);

		Button btn_reason_accept = (Button) dialogNote.findViewById(R.id.btn_reason_accept);
		btn_reason_accept.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (CommonActivity.isNullOrEmpty(edtNote.getText().toString())) {
					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.lydotc),
							getActivity().getString(R.string.app_name)).show();
				} else {
					OnClickListener onclicConfirm = new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							AsyncexecuteCorporationInfo asyncexecuteCorporationInfo = new AsyncexecuteCorporationInfo(
									getActivity());
							asyncexecuteCorporationInfo.execute();
						}
					};

					CommonActivity.createDialog(getActivity(), getActivity().getString(R.string.confirmrefuse),
							getActivity().getString(R.string.app_name), getActivity().getString(R.string.ok),
							getActivity().getString(R.string.cancel), onclicConfirm, null).show();
				}

			}
		});

		dialogNote.findViewById(R.id.btn_reason_cancel).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialogNote.cancel();
			}
		});

		dialogNote.show();

	}

	
	private CorporationCategoryBO resetValueCatagoryInforBeansClone(CorporationCategoryBO catagoryInforBeans) {
		catagoryInforBeans.setInforValue("");
		if (!CommonActivity.isNullOrEmptyArray(catagoryInforBeans.getLstCatagoryInfors())) {
			for (CorporationCategoryBO item : catagoryInforBeans.getLstCatagoryInfors()) {
				resetValueCatagoryInforBeans(item);
			}
		}
		return catagoryInforBeans;
	}
	
	private void resetValueCatagoryInforBeans(CorporationCategoryBO catagoryInforBeans) {
		catagoryInforBeans.setInforValue("");
		
//		if(CommonActivity.isNullOrEmpty(catagoryInforBeans.getCorpInforDetailId())){
			Long index = familyDetailId++;
			catagoryInforBeans.setCorpInforDetailId(index);
			catagoryInforBeans.setIdInsert(index + "");
			int display = Integer.parseInt(catagoryInforBeans.getDisplayOrder()) + 1;
			catagoryInforBeans.setDisplayOrder(display + "");
//		}
		if (!CommonActivity.isNullOrEmptyArray(catagoryInforBeans.getLstCatagoryInfors())) {
			for (CorporationCategoryBO item : catagoryInforBeans.getLstCatagoryInfors()) {
				item.setParentId(catagoryInforBeans.getCorpInforDetailId());
				resetValueCatagoryInforBeans(item);
			}
		}
	}

	private void removeCatagoryInforBeans(CorporationCategoryBO catagoryInforBeans) {

		if (!CommonActivity.isNullOrEmptyArray(catagoryInforBeans.getLstCatagoryInfors())) {
			for (CorporationCategoryBO item : catagoryInforBeans.getLstCatagoryInfors()) {
				removeCatagoryInforBeans(item);
			}
		}

		String key = catagoryInforBeans.getInforCode();

		if (!CommonActivity.isNullOrEmpty(catagoryInforBeans.getCorpInforDetailId())) {
			key = key + "_" + catagoryInforBeans.getCorpInforDetailId();
		}
		if (!CommonActivity.isNullOrEmpty(catagoryInforBeans.getParentCode())) {
			key = key + "_" + catagoryInforBeans.getParentCode();
		}
		
		if (!CommonActivity.isNullOrEmpty(catagoryInforBeans.getParentId())) {
			key = key + "_" + catagoryInforBeans.getParentId();
		}
		
		Log.d("key = ", key);

		if (!CommonActivity.isNullOrEmpty(catagoryInforBeans.getPath())
				&& Constant.PATH_BUSSNESS.contains(catagoryInforBeans.getPath())) {
			if (hashMapService.containsKey(key)) {
				Log.d("remove key", key);
				hashMapService.remove(key);
			}
		} else {
			if (hashMap.containsKey(key)) {
				Log.d("remove key", key);
				hashMap.remove(key);
			}
		}

	}

	@Override
	public void onDeleteCoporationBeans(final CorporationCategoryBO item) {

		Log.d("onDeleteeeeeeeeeeeeeeeeeeeeeeeeeeee   ", item.toString());

		OnClickListener confirmCancel = new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// item.setStatus("0");
				DeleteCorporationCategoryInfo deleteCorporationCategoryInfo = new DeleteCorporationCategoryInfo(
						getActivity(), item);
				deleteCorporationCategoryInfo.execute();
			}
		};

		if (!CommonActivity.isNullOrEmpty(item.getIdInsert())
				&& !CommonActivity.isNullOrEmpty(item.getCorpInforDetailId())) {

			Log.d("before remove hashMap", "size = " + hashMap.size());

			removeCatagoryInforBeans(item);

			Log.d("after remove hashMap", "size = " + hashMap.size());

			if (lstCorporationCategoryBO != null && lstCorporationCategoryBO.size() > 0) {
				for (int i = 0; i < lstCorporationCategoryBO.size(); i++) {
					if ((item.getCorpInforDetailId() == lstCorporationCategoryBO.get(i).getCorpInforDetailId())
							&& (item.getInforCode().equals(lstCorporationCategoryBO.get(i).getInforCode())
									&& (item.getParentCode() == lstCorporationCategoryBO.get(i).getParentCode()))) {
						lstCorporationCategoryBO.remove(i);
						break;
					}
				}
			}
			getCatagoryBeanCoporationAdapter.notifyDataSetChanged();

		} else {
			CommonActivity.createDialog(getActivity(),
					getActivity().getString(R.string.confirmcancelitem) + " " + item.getInforName(),
					getActivity().getString(R.string.app_name), getActivity().getString(R.string.OK),
					getActivity().getString(R.string.boquaReq), confirmCancel, null).show();
		}

	}

	@Override
	public void onAddCatagoryCoporationBeans(CorporationCategoryBO caBeans) {
		CorporationCategoryBO catagoryInforBeans = (CorporationCategoryBO) CommonActivity.cloneObject(caBeans);
		Log.d("adddddddddddddddddddd   ", catagoryInforBeans.toString());

		resetValueCatagoryInforBeans(catagoryInforBeans);

		String key = catagoryInforBeans.getInforCode();

		if (!CommonActivity.isNullOrEmpty(catagoryInforBeans.getCorpInforDetailId())) {
			key = key + "_" + catagoryInforBeans.getCorpInforDetailId();
		}
		if (!CommonActivity.isNullOrEmpty(catagoryInforBeans.getParentCode())) {
			key = key + "_" + catagoryInforBeans.getParentCode();
		}
		if (!CommonActivity.isNullOrEmpty(catagoryInforBeans.getParentId())) {
			key = key + "_" + catagoryInforBeans.getParentId();
		}
		Log.d("key = ", key);
		if (!CommonActivity.isNullOrEmpty(catagoryInforBeans.getPath())
				&& catagoryInforBeans.getPath().contains(Constant.PATH_BUSSNESS)) {
			Log.d("before ADD hashMapService", "size = " + hashMapService.size());
			hashMapService.put(key, catagoryInforBeans);
			Log.d("AFTER remove hashMapService", "size = " + hashMapService.size());
		} else {
			Log.d("before ADD hashMap", "size = " + hashMap.size());
			hashMap.put(key, catagoryInforBeans);
			Log.d("AFTER remove hashMap", "size = " + hashMap.size());
		}

		
		for (int i = 0; i < lstCorporationCategoryBO.size(); i++) {
			if(caBeans.getInforCode().equals(lstCorporationCategoryBO.get(i).getInforCode()) && caBeans.getDisplayOrder().equals(lstCorporationCategoryBO.get(i).getDisplayOrder())){
				lstCorporationCategoryBO.add(i + 1,catagoryInforBeans);
				break;
			}
		}
		
		
		
		
		getCatagoryBeanCoporationAdapter.notifyDataSetChanged();
	}

	private class DeleteCorporationCategoryInfo extends AsyncTask<String, String, Void> {
		private ProgressDialog progress;
		private String errorCode;
		private String description;
		private CorporationCategoryBO caInforBeans;

		public DeleteCorporationCategoryInfo(Context context, CorporationCategoryBO catagoryInforBeans) {
			progress = new ProgressDialog(context);
			progress.setMessage(getResources().getString(R.string.waitting));
			if (!progress.isShowing()) {
				progress.show();
			}
			this.caInforBeans = catagoryInforBeans;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... params) {
			return deleInfor();
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			progress.dismiss();
			if ("0".equals(errorCode)) {

				if (caInforBeans != null) {
					if (lstCorporationCategoryBO != null && lstCorporationCategoryBO.size() > 0) {
						for (int i = 0; i < lstCorporationCategoryBO.size(); i++) {
							if (caInforBeans.getCorpInforDetailId() == lstCorporationCategoryBO.get(i)
									.getCorpInforDetailId()) {
								lstCorporationCategoryBO.remove(i);
								getCatagoryBeanCoporationAdapter.notifyDataSetChanged();
								break;
							}
						}
					}
				}

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

		private Void deleInfor() {
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_deleteCorporationCategoryInfo");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:deleteCorporationCategoryInfo>");
				rawData.append("<input>");
				HashMap<String, String> paramToken = new HashMap<String, String>();
				paramToken.put("token", Session.token);
				rawData.append(input.buildXML(paramToken));

				rawData.append("<corpInforDetailId>" + caInforBeans.getCorpInforDetailId());
				rawData.append("</corpInforDetailId>");

				rawData.append("</input>");
				rawData.append("</ws:deleteCorporationCategoryInfo>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_deleteCorporationCategoryInfo");
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
	
}
