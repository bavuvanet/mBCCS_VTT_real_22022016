package com.viettel.bss.viettelpos.v4.sale.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.captcha.Captcha;
import com.viettel.bss.viettelpos.v4.captcha.MathCaptcha;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionService.listener.ExpandableHeightListView;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.infrastrucure.adapter.AdapterProvinceSpinner;
import com.viettel.bss.viettelpos.v4.sale.adapter.AdapterIsdnOwnerObject;
import com.viettel.bss.viettelpos.v4.sale.adapter.AdapterIsdnOwnerObject.OnclickLockIsdn;
import com.viettel.bss.viettelpos.v4.sale.adapter.AdapterIsdnOwnerObjectIsdn;
import com.viettel.bss.viettelpos.v4.sale.adapter.EndlessRecyclerViewScrollListener;
import com.viettel.bss.viettelpos.v4.sale.object.IsdnOwnerObject;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.LoadMoreListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class FragmentSearchISDN extends Fragment implements OnClickListener, AdapterIsdnOwnerObjectIsdn.OnclickLockIsdn  {

	private View mView;
	private Activity activity;
	private EditText edt_from_isdn;
	private EditText edt_to_isdn;
	private EditText edt_type_isdn;
	private EditText edt_capcha;
	private Spinner spiner_service;
	private Spinner spiner_container;
	private Spinner spiner_state;
	private Button btn_search;
	private ImageView imgCapcha;;
	private RecyclerView listViewIsdn;

	private Button btnHome;

	private final ArrayList<AreaObj> mListService = new ArrayList<>();
	private final ArrayList<AreaObj> mListState = new ArrayList<>();
	private ArrayList<AreaObj> mListContainer = new ArrayList<>();
	private Captcha cap = new MathCaptcha(100, 100,
			MathCaptcha.MathOptions.PLUS_MINUS_MULTIPLY);
	private EditText edtcontainer;

	private AreaObj areaObjStock;
	private AdapterIsdnOwnerObjectIsdn adapterContainer;
	private ArrayList<IsdnOwnerObject> arrIsdnOwner = new ArrayList<>();
	private int pageIndex = 0;

	EndlessRecyclerViewScrollListener onViewScrollListener = null;

    @Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		activity = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mView == null) {
			mView = inflater.inflate(R.layout.layout_search_isdn, container,
					false);
			edt_from_isdn = (EditText) mView.findViewById(R.id.edt_from_isdn);
			edt_to_isdn = (EditText) mView.findViewById(R.id.edt_to_isdn);
			edt_type_isdn = (EditText) mView.findViewById(R.id.edt_type_isdn);
			edt_capcha = (EditText) mView.findViewById(R.id.edt_capcha);
			spiner_service = (Spinner) mView.findViewById(R.id.spiner_service);
//			spiner_container = (Spinner) mView
//					.findViewById(R.id.spiner_container);
			edtcontainer = (EditText) mView.findViewById(R.id.edtcontainer);
			edtcontainer.setOnClickListener(this);
			spiner_state = (Spinner) mView.findViewById(R.id.spiner_state);
			btn_search = (Button) mView.findViewById(R.id.btn_search);
			imgCapcha = (ImageView) mView.findViewById(R.id.imgCapcha);
			imgCapcha.setOnClickListener(this);
			listViewIsdn = (RecyclerView) mView.findViewById(R.id.listViewIsdn);
			LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
			listViewIsdn.setLayoutManager(layoutManager);
//			listViewIsdn.setHasFixedSize(true);
//			listViewIsdn.setNestedScrollingEnabled(false);

//			adapterContainer = new AdapterIsdnOwnerObjectIsdn(getActivity(), arrIsdnOwner,
//					FragmentSearchISDN.this);
//			listViewIsdn.setAdapter(adapterContainer);
			onViewScrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
				@Override
				public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//					arrIsdnOwner.add(null);
//					adapterContainer.notifyItemInserted(arrIsdnOwner.size() - 1);

					loadNextDataFromApi(page);
				}
			};
			// Adds the scroll listener to RecyclerView
			listViewIsdn.addOnScrollListener(onViewScrollListener);
//			listViewIsdn.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
//				@Override
//				public void onLoadMore() {

//				}
//			});
//			listViewIsdn.setExpanded(true);
			loadItemAndShowToSpinner();
			initCap();
		}

		btn_search.setOnClickListener(this);

		return mView;
	}

	@Override
	public void onResume() {
		MainActivity.getInstance().setTitleActionBar(R.string.sale_for_search_isdn);
		super.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == Activity.RESULT_OK){
			switch (requestCode){
				case  100:
					areaObjStock = 	(AreaObj) data.getExtras().getSerializable("areaObj");
					edtcontainer.setText(areaObjStock.getName());
					break;
				default:
					break;
			}
		}
	}

	@SuppressWarnings("unused")
	private void loadItemAndShowToSpinner() {
		AreaObj areaServiceMobile = new AreaObj();
		areaServiceMobile.setName(getString(R.string.service_mobile));
		areaServiceMobile.setAreaCode("1");
		mListService.add(areaServiceMobile);

		AreaObj areaServiceHomePhone = new AreaObj();
		areaServiceHomePhone.setName(getString(R.string.service_home_phone));
		areaServiceHomePhone.setAreaCode("2");
		mListService.add(areaServiceHomePhone);

		AreaObj areaServicePSTN = new AreaObj();
		areaServicePSTN.setName(getString(R.string.service_pstn));
		areaServicePSTN.setAreaCode("3");
		mListService.add(areaServicePSTN);

		AdapterProvinceSpinner adapterSerVice = new AdapterProvinceSpinner(
				mListService, activity);
		spiner_service.setAdapter(adapterSerVice);

		// trang thai
		// 20/07/2017 MTYCTD_GP_QT05_Smartphone_Nang cap android.doc : 1.3.1.1.1	Sửa mặc định trường Trạng thái
		/*AreaObj areaStateObject = new AreaObj();
		areaStateObject.setName(getString(R.string.selectstatus));
		areaStateObject.setAreaCode("-1");
		mListState.add(areaStateObject);*/

		AreaObj isdn_new = new AreaObj();
		isdn_new.setName(getResources().getString(R.string.isdn_new));
		isdn_new.setAreaCode("1");
		mListState.add(isdn_new);

		AreaObj isdn_using = new AreaObj();
		isdn_using.setName(getResources().getString(R.string.isdn_using));
		isdn_using.setAreaCode("2");
		mListState.add(isdn_using);

		AreaObj isdn_end_use = new AreaObj();
		isdn_end_use.setName(getResources().getString(R.string.isdn_end_use));
		isdn_end_use.setAreaCode("3");
		mListState.add(isdn_end_use);

		AreaObj isdn_start_kit = new AreaObj();
		isdn_start_kit.setName(getResources()
				.getString(R.string.isdn_start_kit));
		isdn_start_kit.setAreaCode("4");
		mListState.add(isdn_start_kit);

		AreaObj isdn_lock = new AreaObj();
		isdn_lock.setName(getResources().getString(R.string.isdn_lock));
		isdn_lock.setAreaCode("5");
		mListState.add(isdn_lock);

		AdapterProvinceSpinner adapterState = new AdapterProvinceSpinner(
				mListState, activity);
		spiner_state.setAdapter(adapterState);

		if (CommonActivity.isNetworkConnected(activity)) {
			AsyntaskGetListStockByStaffCode asyntaskGetListStockByStaffCode = new AsyntaskGetListStockByStaffCode(
					activity);
			asyntaskGetListStockByStaffCode.execute();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_search:
				if(validateSearchIsdn()) {
					onSearchIsdn();
				}
				break;
			case R.id.imgCapcha:
				initCap();
				break;
			case R.id.relaBackHome:
				getActivity().onBackPressed();
				break;
			case R.id.edtcontainer:
				if(!CommonActivity.isNullOrEmptyArray(mListContainer)){
					Intent intent = new Intent(getActivity(),SearchStockActivity.class);
					intent.putExtra("arrAreaObj",mListContainer);
					startActivityForResult(intent,100);
				}else{
					CommonActivity.createAlertDialog(getActivity(),getActivity().getString(R.string.notdatastock),getActivity().getString(R.string.app_name)).show();
				}
				break;
			default:
				break;
		}
	}

	private void initBeforeSearch(){
		pageIndex = 0;

		if(arrIsdnOwner != null) {
			arrIsdnOwner.clear();
		}

		if(adapterContainer != null) {
			adapterContainer.notifyDataSetChanged();
		}

		if(onViewScrollListener != null) {
			onViewScrollListener.resetState();
		}
	}

	// move login
    private final OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
//			Intent intent = new Intent(getActivity(), LoginActivity.class);
//			startActivity(intent);
//			getActivity().finish();
//			MainActivity.getInstance().finish();
			
			LoginDialog dialog = new LoginDialog(getActivity(),
					";search_isdn;");
			dialog.show();

		}
	};

	private IsdnOwnerObject isdnOwnerObjectSearch;
	private boolean validateSearchIsdn(){
		initBeforeSearch();

		CommonActivity.hideKeyboard(btn_search, activity);

		String fromIsdn = edt_from_isdn.getText().toString().trim();
		String toIsdn = edt_to_isdn.getText().toString().trim();
		String typeIsdn = edt_type_isdn.getText().toString().trim();

		AreaObj areaService = mListService.get(spiner_service
				.getSelectedItemPosition());
		String codeCapcha = edt_capcha.getText().toString().trim();
		Dialog dialogError = null;
		if ((CommonActivity.isNullOrEmpty(fromIsdn) && CommonActivity.isNullOrEmpty(toIsdn)) && CommonActivity.isNullOrEmpty(typeIsdn) ) {
			dialogError = CommonActivity.createAlertDialog(activity,
					getResources().getString(R.string.validIsdnOrType),
					getString(R.string.app_name));
			dialogError.show();
			return false;
		}
		if(!CommonActivity.isNullOrEmpty(fromIsdn) || !CommonActivity.isNullOrEmpty(toIsdn)){
			if(!CommonActivity.isNullOrEmpty(fromIsdn) && CommonActivity.isNullOrEmpty(toIsdn)){
				dialogError = CommonActivity.createAlertDialog(activity,
						getResources().getString(R.string.toisdnempty),
						getString(R.string.app_name));
				dialogError.show();
				return false;
			}
			if(CommonActivity.isNullOrEmpty(fromIsdn) && !CommonActivity.isNullOrEmpty(toIsdn)){
				dialogError = CommonActivity.createAlertDialog(activity,
						getResources().getString(R.string.fromisdnepty),
						getString(R.string.app_name));
				dialogError.show();
				return false;
			}
		}

		if (!CommonActivity.isNullOrEmpty(fromIsdn) && !CommonActivity.isNullOrEmpty(toIsdn)){
			if(Long.parseLong(fromIsdn) > Long.parseLong(toIsdn)){
				dialogError = CommonActivity.createAlertDialog(activity,
						getResources().getString(R.string.fromisto),
						getString(R.string.app_name));
				dialogError.show();
				return false;
			}
		}

		if(CommonActivity.isNullOrEmpty(edtcontainer.getText().toString())){
			dialogError = CommonActivity.createAlertDialog(activity,
					getResources().getString(R.string.stockisnull),
					getString(R.string.app_name));
			dialogError.show();
			return false;
		}

		if (codeCapcha.isEmpty()) {
			dialogError = CommonActivity.createAlertDialog(activity,
					getResources().getString(R.string.please_input_capcha),
					getString(R.string.app_name));
			edt_capcha.requestFocus();
			dialogError.show();
			return false;
		}

		if (!cap.checkAnswer(codeCapcha)) {
			dialogError = CommonActivity.createAlertDialog(activity,
					getResources().getString(R.string.error_input_capcha),
					getString(R.string.app_name));
			edt_capcha.requestFocus();
			dialogError.show();
			return false;
		}



//			else if(CommonActivity.isNullOrEmpty(toIsdn)){
//				dialogError = CommonActivity.createAlertDialog(activity,
//						getResources().getString(R.string.toisdnempty),
//						getString(R.string.app_name));
//			} else if(Long.parseLong(fromIsdn) > Long.parseLong(toIsdn)){
//				dialogError = CommonActivity.createAlertDialog(activity,
//						getResources().getString(R.string.fromisto),
//						getString(R.string.app_name));
//			}
//			else if (codeCapcha.isEmpty()) {
//				dialogError = CommonActivity.createAlertDialog(activity,
//						getResources().getString(R.string.please_input_capcha),
//						getString(R.string.app_name));
//				edt_capcha.requestFocus();
//			} else if (!cap.checkAnswer(codeCapcha)) {
//				dialogError = CommonActivity.createAlertDialog(activity,
//						getResources().getString(R.string.error_input_capcha),
//						getString(R.string.app_name));
//				edt_capcha.requestFocus();
//			}

		if (dialogError != null) {
			dialogError.show();
			return false;
		}

		initCap();

		isdnOwnerObjectSearch = new IsdnOwnerObject();
		isdnOwnerObjectSearch.setFromIsdn(fromIsdn);
		isdnOwnerObjectSearch.setToIsdn(toIsdn);
		isdnOwnerObjectSearch.setTypeIsdn(typeIsdn);
		isdnOwnerObjectSearch.setStockTypeId(areaService.getAreaCode());

//			if (mListContainer.size() > 0) {
//				AreaObj areaContainer = mListContainer.get(spiner_container
//						.getSelectedItemPosition());
//				if (!areaContainer.getAreaCode().equals("-1")) {

		if(!CommonActivity.isNullOrEmpty(areaObjStock)){
			isdnOwnerObjectSearch.setOwnerId(areaObjStock.getAreaCode());
			isdnOwnerObjectSearch.setOwnerType(areaObjStock.getParentCode());
			isdnOwnerObjectSearch.setOwnerCode(areaObjStock.getName());
		}

//				}
//			}

		AreaObj areaState = mListState.get(spiner_state
				.getSelectedItemPosition());
		if (!areaState.getAreaCode().equals("-1")) {
			isdnOwnerObjectSearch.setStatus(areaState.getAreaCode());
		}


		return true;
	}

	private void onSearchIsdn() {
		try {
			AsyntaskSearchIsdnOwners asyntaskSearchIsdn = new AsyntaskSearchIsdnOwners(
					activity, isdnOwnerObjectSearch ,pageIndex);
			asyntaskSearchIsdn.execute();
		} catch (Exception e) {
			Log.d(Constant.TAG, "FragmentSearchISDN onSearchIsdn Exception ", e);
		}
	}




	private class AsyntaskSearchIsdnOwners extends
			AsyncTask<Void, Void, ArrayList<IsdnOwnerObject>> {

		private final Activity mActivity;
		private AreaObj areaBillDate;

		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final ProgressDialog progress;
		private final IsdnOwnerObject isdnOwnerObject;
		int pageIndex;
		public AsyntaskSearchIsdnOwners(Activity mActivity,
				IsdnOwnerObject isdnOwnerObject) {
			this.mActivity = mActivity;
			this.isdnOwnerObject = isdnOwnerObject;
			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}


		public AsyntaskSearchIsdnOwners(Activity mActivity,
										IsdnOwnerObject isdnOwnerObject , int pageIndex) {
			this.mActivity = mActivity;
			this.isdnOwnerObject = isdnOwnerObject;
			this.progress = new ProgressDialog(mActivity);
//			if(pageIndex == 0) {
				this.progress.setCancelable(false);
				this.progress.setMessage(mActivity.getResources().getString(
						R.string.getdataing));
				if (!this.progress.isShowing()) {
					this.progress.show();
				}
//			}
			this.pageIndex = pageIndex;
		}

		@Override
		protected ArrayList<IsdnOwnerObject> doInBackground(Void... params) {
			return doGetListIsdnOwnerObject();
		}

		@Override
		protected void onPostExecute(ArrayList<IsdnOwnerObject> result) {
			super.onPostExecute(result);
			if(progress.isShowing()) {
				progress.dismiss();
			}

			// if(imgCapcha != null){
			// imgCapcha.initCaptcha();
			// }
//			if (mView != null) {
//				imgCapcha = (ImageView) mView.findViewById(R.id.imgCapcha);
//
//			}

			if (errorCode.equals("0")) {
				edt_capcha.setText("");

				if (result.size() == 0 && pageIndex == 0) {
					CommonActivity.createAlertDialog(mActivity,
							getActivity().getString(R.string.not_result_search_isdn),
                            getActivity().getString(R.string.app_name)).show();
				}

				if(pageIndex != 0) {
//					arrIsdnOwner.remove(arrIsdnOwner.size() - 1); //remove loading
//					adapterContainer.notifyItemRemoved(arrIsdnOwner.size());

					Log.d(Constant.TAG, "onPostExecute mListIsdnOwnerObject "
							+ result.size());
					arrIsdnOwner.addAll(result);
					adapterContainer.notifyDataSetChanged();
				} else {
					Log.d(Constant.TAG, "onPostExecute mListIsdnOwnerObject "
							+ result.size());
					arrIsdnOwner.addAll(result);
					adapterContainer = new AdapterIsdnOwnerObjectIsdn(getActivity(), arrIsdnOwner,
							FragmentSearchISDN.this);
					listViewIsdn.setAdapter(adapterContainer);
				}


			} else {
				arrIsdnOwner = new ArrayList<>();
				adapterContainer = new AdapterIsdnOwnerObjectIsdn(getActivity(), arrIsdnOwner,
						FragmentSearchISDN.this);
				listViewIsdn.setAdapter(adapterContainer);
				adapterContainer.notifyDataSetChanged();
				Log.d("Log", "description error update" + description);
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity.createAlertDialog(
							mActivity,
							description,
							mActivity.getResources().getString(
									R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = mActivity.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getActivity().getResources()
									.getString(R.string.app_name));
					dialog.show();
				}
			}

		}

		private ArrayList<IsdnOwnerObject> doGetListIsdnOwnerObject() {
			ArrayList<IsdnOwnerObject> listIsdnOwnerObject = new ArrayList<>();

			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_searchIsdnBccs2");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:searchIsdn>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");

				if (isdnOwnerObject.getOwnerId() != null) {
					rawData.append("<shopCode>").append(isdnOwnerObject.getOwnerCode()).append("</shopCode>");
					rawData.append("<ownerId>").append(isdnOwnerObject.getOwnerId()).append("</ownerId>");
					rawData.append("<ownerType>").append(isdnOwnerObject.getOwnerType()).append("</ownerType>");
				}
				if (isdnOwnerObject.getStatus() != null) {
					rawData.append("<status>").append(isdnOwnerObject.getStatus()).append("</status>");
				}

				rawData.append("<stockTypeId>").append(isdnOwnerObject.getStockTypeId()).append("</stockTypeId>");
				if(!CommonActivity.isNullOrEmpty(isdnOwnerObject.getFromIsdn())){
					rawData.append("<fromIsdn>").append(isdnOwnerObject.getFromIsdn()).append("</fromIsdn>");
				}
				if(!CommonActivity.isNullOrEmpty(isdnOwnerObject.getToIsdn())){
					rawData.append("<toIsdn>").append(isdnOwnerObject.getToIsdn()).append("</toIsdn>");
				}

				String typeIsdn = "";
				if (!CommonActivity.isNullOrEmpty(isdnOwnerObject.getTypeIsdn())) {
					typeIsdn = isdnOwnerObject.getTypeIsdn().trim();
					if("0".equals(typeIsdn.split("")[1])){
						typeIsdn = typeIsdn.substring(1,typeIsdn.length());
					}
					rawData.append("<typeIsdn>").append(StringUtils.escapeHtml(typeIsdn)).append("</typeIsdn>");
				}
				rawData.append("<pageIndex>").append(pageIndex).append("</pageIndex>");
//				rawData.append("<pageSize>").append("100").append("</pageSize>");



				rawData.append("</input>");
				rawData.append("</ws:searchIsdn>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_searchIsdnBccs2");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", original);

				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				NodeList nodechildListSerial = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					nodechild = doc.getElementsByTagName("vStockNumberDTOs");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Log.d(Constant.TAG, "vStockNumberDTOs count " + j);
						Element e1 = (Element) nodechild.item(j); 
						IsdnOwnerObject misdnOwnerObject = new IsdnOwnerObject();
//						isdnOwnerObject.setStaffCode(parse.getValue(e1, "staffCode"));
						misdnOwnerObject.setOwnerCode(parse.getValue(e1, "ownerCode"));
						misdnOwnerObject.setOwnerName(parse.getValue(e1, "ownerName"));
						misdnOwnerObject.setStatus(parse.getValue(e1, "status"));
//						isdnOwnerObject.setStatusName(parse.getValue(e1, "statusName"));
//						isdnOwnerObject.setStockTypeName(parse.getValue(e1, "productOfferTypeName"));
						misdnOwnerObject.setStockTypeId(isdnOwnerObject.getStockTypeId());
//						isdnOwnerObject.setOwnerId(parse.getValue(e1, "ownerId"));
//						isdnOwnerObject.setOwnerType(parse.getValue(e1, "ownerType"));
						misdnOwnerObject.setStockModelName(parse.getValue(e1, "stockModelName"));
						misdnOwnerObject.setIsdn(parse.getValue(e1, "isdn"));
						listIsdnOwnerObject.add(misdnOwnerObject);
					}
				}
			} catch (Exception e) {
				Log.d("getListIP", e.toString() + "description error", e);
			}

			return listIsdnOwnerObject;
		}

	}

	@SuppressWarnings("unused")
	private class AsyntaskGetListStockByStaffCode extends
			AsyncTask<Void, Void, ArrayList<AreaObj>> {

		private final Activity mActivity;
		private AreaObj areaBillDate;

		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final ProgressDialog progress;

		public AsyntaskGetListStockByStaffCode(Activity mActivity) {
			this.mActivity = mActivity;
			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<AreaObj> doInBackground(Void... params) {
			return doGetListStockByStaffCode();
		}

		@Override
		protected void onPostExecute(ArrayList<AreaObj> result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {
				mListContainer = result;
//				AdapterProvinceSpinner adapterContainer = new AdapterProvinceSpinner(
//						mListContainer, activity);
//				spiner_container.setAdapter(adapterContainer);
			} else {
				Log.d("Log", "description error update" + description);
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity.createAlertDialog(
							mActivity,
							description,
							mActivity.getResources().getString(
									R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = mActivity.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();
				}
			}

		}

		private ArrayList<AreaObj> doGetListStockByStaffCode() {
			ArrayList<AreaObj> listStockObject = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getListStockByStaffCodeBccs2");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListStockByStaffCode>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("</input>");
				rawData.append("</ws:getListStockByStaffCode>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListStockByStaffCodeBccs2");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", original);

				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				NodeList nodechildListSerial = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					nodechild = doc.getElementsByTagName("lstSmartPhoneDTOs");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						AreaObj areaStock = new AreaObj();
						areaStock.setName(parse.getValue(e1, "ownerCode"));
						areaStock.setAreaCode(parse.getValue(e1, "ownerId"));
						areaStock
								.setParentCode(parse.getValue(e1, "ownerType"));
						listStockObject.add(areaStock);
					}
				}
			} catch (Exception e) {
				Log.d("getListIP", e.toString() + "description error", e);
			}

			return listStockObject;

		}

	}

	private IsdnOwnerObject isdnOwnerObject;

	@Override
	public void onclickLock(IsdnOwnerObject isdnOwnerObject) {
		this.isdnOwnerObject = isdnOwnerObject;
		if (CommonActivity.isNetworkConnected(getActivity())) {
			// CommonActivity.createAlertDialog(activity,
			// getResources().getString(R.string.lb_confirm_lock_isdn) + ":" +
			// isdnOwnerObject.getIsdn(),
			// getString(R.string.app_name),
			// clickLockListenr).show();
			if("5".equalsIgnoreCase(isdnOwnerObject.getStatus())){
				CommonActivity.createDialog(activity,
						activity.getResources().getString(R.string.lb_confirm_unlock_isdn)+ ": " + isdnOwnerObject.getIsdn() + " ?",
						activity.getString(R.string.app_name), activity.getString(R.string.cancel), activity.getString(R.string.ok),
						null,clickUnLockListenr).show();
			}else{
				CommonActivity.createDialog(activity,
							activity.getResources().getString(R.string.lb_confirm_lock_isdn)+ ": " + isdnOwnerObject.getIsdn() + " ?",
					activity.getString(R.string.app_name), activity.getString(R.string.cancel), activity.getString(R.string.ok ),
					null,clickLockListenr ).show();
			}

		}
	}

	private class LockIsdnAsyn extends AsyncTask<String, Void, String> {

		private final ProgressDialog progress;
		private String errorCode = "";
		private String description = "";
		private final Context context;
		private final XmlDomParse parse = new XmlDomParse();
		private boolean isLockOrUnlock = false;
		public LockIsdnAsyn(Context mContext , boolean isLockOrUnlock) {
			this.context = mContext;
			progress = new ProgressDialog(context);
			this.isLockOrUnlock = isLockOrUnlock;
			if(isLockOrUnlock){
				this.progress.setMessage(mContext.getResources().getString(
						R.string.lb_btn_ulock_isdn));
			}else{
				this.progress.setMessage(mContext.getResources().getString(
						R.string.lb_btn_lock_isdn));
			}

			this.progress.setTitle(mContext.getResources().getString(
					R.string.app_name));
			if (!progress.isShowing()) {
				progress.show();
			}
		}

		@Override
		protected String doInBackground(String... arg0) {
			return lockIsdn(arg0[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			progress.dismiss();

			if ("0".equals(errorCode)) {
				if(!CommonActivity.isNullOrEmpty(isdnOwnerObject) && !CommonActivity.isNullOrEmptyArray(arrIsdnOwner)){
					if(isLockOrUnlock){
						for (IsdnOwnerObject item: arrIsdnOwner) {
							if(isdnOwnerObject.getIsdn().equals(item.getIsdn())){
								item.setStatus("1");
								break;
							}
						}
						adapterContainer = new AdapterIsdnOwnerObjectIsdn(getActivity(), arrIsdnOwner,
								FragmentSearchISDN.this);
						listViewIsdn.setAdapter(adapterContainer);
					}else{
						for (IsdnOwnerObject item: arrIsdnOwner) {
							if(isdnOwnerObject.getIsdn().equals(item.getIsdn())){
								item.setStatus("5");
								break;
							}
						}
						adapterContainer = new AdapterIsdnOwnerObjectIsdn(getActivity(), arrIsdnOwner,
								FragmentSearchISDN.this);
						listViewIsdn.setAdapter(adapterContainer);
					}
				}
				CommonActivity.createAlertDialog(getActivity(), description,
						getActivity().getString(R.string.app_name)).show();

			} else {

				Log.d("Log", "description error update" + description);
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity
							.createAlertDialog(
									getActivity(),
									description,
									getActivity().getResources().getString(
											R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = getActivity()
								.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getActivity().getResources()
									.getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		private String lockIsdn(String isdn) {
			String original = "";
			try {
				String methodName = "lockIsdnByStaff";
				if(isLockOrUnlock){
					methodName = "unLockIsdnByStaff";
				}

				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				if(isLockOrUnlock){
					input.addValidateGateway("wscode", "mbccs_" + methodName);
				}else{
					input.addValidateGateway("wscode", "mbccs_" + methodName + "Bccs2");
				}

				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:"+methodName+">");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<isdn>").append(isdn).append("</isdn>");
				rawData.append("</input>");
				rawData.append("</ws:"+methodName+">");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_"+ methodName);
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", original);

				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
				}

			} catch (Exception e) {
				Log.d("lockIsdn", e.toString());
			}
			return errorCode;
		}

	}

	private final OnClickListener clickLockListenr = new View.OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			LockIsdnAsyn lockIsdnAsyn = new LockIsdnAsyn(getActivity() , false);
			lockIsdnAsyn.execute(isdnOwnerObject.getIsdn());
		}
	};

	private final OnClickListener clickUnLockListenr = new View.OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			LockIsdnAsyn lockIsdnAsyn = new LockIsdnAsyn(getActivity() , true);
			lockIsdnAsyn.execute(isdnOwnerObject.getIsdn());
		}
	};

	private void initCap() {
		edt_capcha.setText("");
		cap = new MathCaptcha(300, 100, MathCaptcha.MathOptions.PLUS_MINUS);
		imgCapcha.setImageBitmap(cap.getImage());
		// imgCapcha.setLayoutParams(new LinearLayout.LayoutParams(cap.width *
		// 2,
		// cap.height * 2));
	}

	public void loadNextDataFromApi(int offset) {
		// Send an API request to retrieve appropriate paginated data
		//  --> Send the request including an offset value (i.e `page`) as a query parameter.
		//  --> Deserialize and construct new model objects from the API response
		//  --> Append the new data objects to the existing set of items inside the array of items
		//  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
//			pageIndex = pageIndex + 1;
		pageIndex = offset;
		onSearchIsdn();

	}


}
