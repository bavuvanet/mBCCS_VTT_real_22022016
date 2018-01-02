package com.viettel.bss.viettelpos.v4.contract.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter.GetListCustomerBccsAdapter;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.AccountBankDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.AccountDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustIdentityDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustTypeDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustomerDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferingDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductPackageFeeDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductSpecCharDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ReasonDTO;
import com.viettel.bss.smartphonev2.commons.OnPostExecute;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.captcha.Captcha;
import com.viettel.bss.viettelpos.v4.captcha.MathCaptcha;
import com.viettel.bss.viettelpos.v4.captcha.MathCaptcha.MathOptions;
import com.viettel.bss.viettelpos.v4.charge.adapter.CustomerObjectAdapter;
import com.viettel.bss.viettelpos.v4.charge.asynctask.AsyncGetDebitInfo;
import com.viettel.bss.viettelpos.v4.charge.asynctask.AsyncPaymentDebt;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.charge.object.ContractFormMngtBean;
import com.viettel.bss.viettelpos.v4.charge.object.VirtualInvoice;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.CreateAddressCommon;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.SdCardHelper;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.ActivityCreateNewRequestMobileNew;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.FragmentConnectionMobileNew;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.FragmentSearchCusTypeMobile;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.SearchCodeHthmActivity;
import com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.GetSubscriberChangeSimAdapter.OnChangeCustomer;
import com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.RecordPrepaidAdapter;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.FeeDTO;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.SexBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.HTHMBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PromotionTypeBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.SubTypeBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.listener.ExpandableHeightListView;
import com.viettel.bss.viettelpos.v4.contract.adapter.ListContractAdapter;
import com.viettel.bss.viettelpos.v4.customer.asynctask.AsyncBlockOpenSub;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.customview.obj.FileObj;
import com.viettel.bss.viettelpos.v4.customview.obj.RecordPrepaid;
import com.viettel.bss.viettelpos.v4.customview.obj.SpecObject;
import com.viettel.bss.viettelpos.v4.dal.CacheDatabaseManager;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.object.AddInfo;
import com.viettel.bss.viettelpos.v4.sale.dal.BhldDAL;
import com.viettel.bss.viettelpos.v4.sale.object.SaleOutput;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.CustomEditText;
import com.viettel.bss.viettelpos.v4.ui.LoadMoreListView;
import com.viettel.bss.viettelpos.v4.ui.image.picker.ImagePickerActivity;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;
import com.viettel.bss.viettelpos.v4.work.object.ProductSpecCharUseDTO;
import com.viettel.savelog.SaveLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.Text;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static android.R.attr.isDefault;
import static android.R.attr.manageSpaceActivity;

public class FragmentSearchContract extends FragmentCommon implements OnChangeCustomer {

	private Context mContext;
	private View mView = null;
	// thong tin cha
	private EditText editsogiayto, editisdnacount;

	private Button btnTimKiem;
	private ProgressBar prbreasonBtn;
	private ExpandableHeightListView lvAccount;
	private Spinner spinner_loaibaomat;

	// sua doi thong tin khach hang

	// khai bao loai bao mat
	private ArrayList<Spin> arrTypeSecurity = new ArrayList<Spin>();

	private SubscriberDTO subscriberDTO = new SubscriberDTO();

	private String functionName = "";

	private Captcha cap = new MathCaptcha(100, 100,
			MathOptions.PLUS_MINUS_MULTIPLY);

	private GetListCustomerBccsAdapter adaGetListCustomerBccsAdapter;
	private List<CustIdentityDTO> arrCustIdentityDTOs = new ArrayList<>();
	// list contract
	private ArrayList<AccountDTO> arrTractBeans = new ArrayList<AccountDTO>();
	private AccountDTO accountDTO = new AccountDTO();

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private Date dateNow = null;

//	private Gson g = new Gson();

	private List<CustIdentityDTO> arrCustIdentityDDDTOs = new ArrayList<>();


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


		mContext = getActivity();
		Bundle mBundle = getArguments();

		if (mBundle != null) {
			functionName = mBundle.getString("functionKey", "");
		}

		if (mView == null) {
			mView = inflater.inflate(R.layout.layout_search_sub_contract, container, false);
			unit(mView);
		}
		return mView;
	}

	@Override
	public void onResume() {
		super.onResume();

		String title = getString(R.string.title_contract_search);

		setTitleActionBar(title);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
			case R.id.btnTimKiem:
				if (CommonActivity.isNetworkConnected(getActivity())) {
					if (!CommonActivity.isNullOrEmptyArray(arrCustIdentityDTOs)) {
						arrCustIdentityDTOs = new ArrayList<CustIdentityDTO>();
					}
					// subscriberDTO = new SubscriberDTO();
					adaGetListCustomerBccsAdapter = new GetListCustomerBccsAdapter(getActivity(), arrCustIdentityDTOs,
							null);
					lvAccount.setAdapter(adaGetListCustomerBccsAdapter);
					if (adaGetListCustomerBccsAdapter != null) {
						adaGetListCustomerBccsAdapter.notifyDataSetChanged();
					}

					// neu truyen them isdn
					if (!CommonActivity.isNullOrEmpty(editisdnacount.getText().toString())){

						if (StringUtils.CheckCharSpecical_1(editsogiayto.getText().toString().trim())||
								StringUtils.CheckCharSpecical_1(editisdnacount.getText().toString().trim())){
							CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validate_special_char),
									getActivity().getString(R.string.app_name)).show();
						} else {
							GetConTractAsyn getConTractAsyn = new GetConTractAsyn(mContext);
							getConTractAsyn.execute("", editsogiayto.getText().toString(), editisdnacount.getText().toString());
						}

					} else {
						if (validateSearch()) {
							getCustomerIdentity();
						}
					}


				} else {
					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.errorNetwork),
							getActivity().getString(R.string.app_name)).show();
				}
				break;

			default:
				break;
		}
	}

	private boolean validateSearch() {
		if (CommonActivity.isNullOrEmpty(editsogiayto.getText().toString().trim())) {
			CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validate_type_paper),
					getActivity().getString(R.string.app_name)).show();
			return false;
		}


		if (StringUtils.CheckCharSpecical_1(editsogiayto.getText().toString().trim())||
				StringUtils.CheckCharSpecical_1(editisdnacount.getText().toString().trim())){
			CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validate_special_char),
					getActivity().getString(R.string.app_name)).show();
			return false;
		}
//		if (CommonActivity.isNullOrEmpty(editisdnacount.getText().toString().trim())) {
//			CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.isdnnotempty),
//					getActivity().getString(R.string.app_name)).show();
//			return false;
//		}

		return true;
	}

	@Override
	public void unit(View v) {
		editsogiayto = (EditText) v.findViewById(R.id.editsogiayto);
		editisdnacount = (EditText) v.findViewById(R.id.editisdnacount);

//		editsogiayto.setText("888111000");
//		editisdnacount.setText("980000684");

		btnTimKiem = (Button) v.findViewById(R.id.btnTimKiem);
		btnTimKiem.setOnClickListener(this);
		prbreasonBtn = (ProgressBar) v.findViewById(R.id.prbreasonBtn);
		lvAccount = (ExpandableHeightListView) v.findViewById(R.id.lvAccount);
		lvAccount.setExpanded(true);
		lvAccount.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Gson g = new Gson();
				custIdentityDTO = arrCustIdentityDTOs.get(position);
				getListContract(arrCustIdentityDTOs.get(position));
				Log.i("DATA","arrCustIdentityDTOs: "+arrCustIdentityDTOs.get(position).getCustomer().getCustId());
				getCustomerByCustId(""+arrCustIdentityDTOs.get(position).getCustomer().getCustId());
			}
		});



	}


	@Override
	public void onChangeCustomerListener(SubscriberDTO subscriberDTO) {
		// TODO thay doi thong tin khach hang doi voi truong hop chinh chu
	}

	// lay thong tin otp


	// tim kiem thong tin thue bao


	@Override
	protected void setPermission() {

	}


	// show popup hopdong
	private Dialog dialogContractList;

	View dialogViewList;

	private void showPopupListContract(final List<AccountDTO> arrTractBeans) {


		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		dialogViewList = inflater.inflate(R.layout.popup_list_contract, null, false);

		dialogContractList = new Dialog(mContext);
		dialogContractList.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogContractList.setContentView(dialogViewList);
		dialogContractList.setCancelable(false);

		TextView txt_tenKH = (TextView) dialogContractList.findViewById(R.id.txt_tenKH);
		TextView txt_sogiayto = (TextView) dialogContractList.findViewById(R.id.txt_sogiayto);
		ListView lst_contract = (ListView) dialogContractList.findViewById(R.id.lst_contract);

		ListContractAdapter adapter = new ListContractAdapter((Activity) mContext, arrTractBeans);
		lst_contract.setAdapter(adapter);

		lst_contract.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				dialogContractList.dismiss();
				areaHomeNumberContract = "";
				areaFlowContract = "";
				accountDTOMain = new AccountDTO();
				GetAccountInforAsyn getAccountInforAsyn = new GetAccountInforAsyn(getActivity());
				getAccountInforAsyn.execute(arrTractBeans.get(position).getAccountId() + "");
			}
		});


//		txt_tenKH.setText();


		Button btn_close = (Button) dialogContractList.findViewById(R.id.btn_close);
		btn_close.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialogContractList.dismiss();
			}
		});


		dialogContractList.show();
	}


	// search custom similar paper
	private void getCustomerIdentity() {

		SearchCustIdentityAsyn searchCustIdentityAsyn = new SearchCustIdentityAsyn(mContext, "");

		searchCustIdentityAsyn.execute(editsogiayto.getText().toString().trim(), "");


	}

	@Override
	public void onDetach() {
		if (progress != null && progress.isShowing()) {
			progress.dismiss();
		}

		super.onDetach();
	}

	private ProgressDialog progress;

	private class SearchCustIdentityAsyn extends AsyncTask<String, Void, ParseOuput> {


		private Context context;
		private String errorCode = "";
		private String description = "";
		private String input = "";


		public SearchCustIdentityAsyn(Context context, String input) {
			this.context = context;
			this.input = input;

		}

		@Override
		protected void onPreExecute() {

			new Thread(new Runnable() {
				@Override
				public void run() {
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Log.i("DATA", "context: " + context);
							progress = ProgressDialog.show(context, "", context.getResources().getString(R.string.getdataing), false);
						}
					});
				}
			}).start();

		}

		@Override
		protected ParseOuput doInBackground(String... arg0) {
			return searchCus(arg0[0], arg0[1]);
		}

		@Override
		protected void onPostExecute(final ParseOuput result) {
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (progress != null && progress.isShowing()) {
						progress.dismiss();
						progress = null;
					}
				}
			});


			arrCustIdentityDTOs = new ArrayList<>();

			if ("0".equals(errorCode)) {
				if (result != null) {

					if (result.getLstCustIdentityDTOs() != null && result.getLstCustIdentityDTOs().size() > 0) {


						arrCustIdentityDTOs = result.getLstCustIdentityDTOs();

						for (CustIdentityDTO itemCus : arrCustIdentityDTOs) {
							if ("BUS".equals(itemCus.getIdType()) || "TIN".equals(itemCus.getIdType())) {
								itemCus.setGroupType("2");
							} else {
								itemCus.setGroupType(itemCus.getCustomer().getCustTypeDTO().getGroupType());
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

					adaGetListCustomerBccsAdapter = new GetListCustomerBccsAdapter(getActivity(), arrCustIdentityDTOs,
							null);
					lvAccount.setAdapter(adaGetListCustomerBccsAdapter);
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

		private ParseOuput searchCus(String idNo, String custType) {
			String original = null;
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_searchCustIdentity");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:searchCustIdentity>");
				rawData.append("<input>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("<custIdentitySearchDTO>");
				rawData.append("<idNo>").append(idNo);
				rawData.append("</idNo>");
				if (!CommonActivity.isNullOrEmpty(custType)) {
					rawData.append("<custType>").append(custType);
					rawData.append("</custType>");
				}

				// 1 idType

//				if (!CommonActivity.isNullOrEmpty(typePaper)) {
//					rawData.append("<idType>").append(typePaper);
//					rawData.append("</idType>");
//				}

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
				Log.i("parseOuput", "parseOuput: " + parseOuput);
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

	// get thong tin khach hang dai dien khi thay so giay // TODO: 7/20/17
	private ArrayList<CustIdentityDTO> arrCustIdentityDTODialog = new ArrayList<CustIdentityDTO>();
	private GetListCustomerBccsAdapter adaGetListCustomerBccsAdapterDialog;
	private class SearchCustIdentityDDAsyn extends AsyncTask<String, Void, ParseOuput> {

		private ProgressDialog progress;
		private Context context = null;
		private String errorCode = "";
		private String description = "";

		private String check = "";

		public SearchCustIdentityDDAsyn(Context context, String mCheck) {
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
	// chon thong tin khach hang cu cho nguoi dai dien khach hang doanh nghiep
	private Dialog dialogCus;
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
				AsynGetCustomerByCustIdDD asynGetCustomerByCustId = new AsynGetCustomerByCustIdDD(getActivity(),
						repreCustomer);
				asynGetCustomerByCustId.execute(repreCustomer.getCustomer().getCustId() + "");

			}
		});

		Button btnCancel = (Button) dialogCus.findViewById(R.id.btncancel);
		btnCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialogCus.cancel();
			}
		});

		dialogCus.show();
	}

	// getlist contract
	private void getListContract(CustIdentityDTO custIdentityDTO) {
		GetConTractAsyn getConTractAsyn = new GetConTractAsyn(mContext);
		getConTractAsyn.execute(custIdentityDTO.getCustomer().getCustId() + "", custIdentityDTO.getIdNo(), "");
	}

	private class GetConTractAsyn extends AsyncTask<String, Void, ArrayList<AccountDTO>> {

		private Context context = null;
		String errorCode = "";
		String description = "";

		public GetConTractAsyn(Context context) {
			this.context = context;
//			prb_contract.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onPreExecute() {

			new Thread(new Runnable() {
				@Override
				public void run() {
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Log.i("DATA", "context: " + context);
							progress = ProgressDialog.show(context, "", context.getResources().getString(R.string.getdataing), false);
						}
					});
				}
			}).start();

		}

		@Override
		protected ArrayList<AccountDTO> doInBackground(String... params) {
			return sendRequestGetLisContract(params[0], params[1], params[2]);
		}

		@Override
		protected void onPostExecute(ArrayList<AccountDTO> result) {
//			prb_contract.setVisibility(View.GONE);
			// progress.dismiss();

			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (progress != null && progress.isShowing()) {
						progress.dismiss();
						progress = null;
					}
				}
			});

			if (errorCode.equals("0")) {


				if (result != null && result.size() > 0) {
					arrTractBeans = result;
//					initContract();
					showPopupListContract(arrTractBeans);
				} else {
					if (arrTractBeans != null && arrTractBeans.size() > 0) {
						arrTractBeans.clear();
					}
//					initContract();

					Dialog dialog = CommonActivity.createAlertDialog((Activity) mContext, context.getResources().getString(R.string.txt_no_data),
							context.getResources().getString(R.string.app_name));
					dialog.show();

				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {

					Dialog dialog = CommonActivity.createAlertDialog((Activity) mContext, description,
							context.getResources().getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog((Activity) mContext, description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		private ArrayList<AccountDTO> sendRequestGetLisContract(String cusId, String idNo, String isdn) {
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

				rawData.append("<inputForSearchContract>");
				rawData.append("<custId>" + cusId);
				rawData.append("</custId>");

				rawData.append("<idNo>" + idNo);
				rawData.append("</idNo>");
				rawData.append("<isdn>" + (StringUtils.isDigit(isdn) ? CommonActivity.getStardardIsdnBCCS(isdn) : isdn));
				rawData.append("</isdn>");
				rawData.append("</inputForSearchContract>");
				rawData.append("</input>");
				rawData.append("</ws:searchAccount>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, (Activity) mContext, "mbccs_searchAccount");
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

	// get thong tin hop dong cu
	private class GetAccountInforAsyn extends AsyncTask<String, Void, AccountDTO> {
		private ProgressDialog progress;
		private Context context = null;
		String errorCode = "";
		String description = "";

		public GetAccountInforAsyn(Context context) {
			this.context = context;

			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setMessage(context.getResources().getString(R.string.getdataing));
			this.progress.setCancelable(false);
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected AccountDTO doInBackground(String... params) {
			return getAccountInfo(params[0]);
		}

		@Override
		protected void onPostExecute(AccountDTO result) {
			progress.dismiss();
			accountDTO = new AccountDTO();
			if ("0".equals(errorCode)) {
				// thong tin hop dong cu
				if (result != null) {
					accountDTO = result;
					Gson g = new Gson();
					Log.i("DATA","accountDTO: "+g.toJson(accountDTO));

					addInfoStr = accountDTO.getAddInfo();
					accountDTOMain = new AccountDTO();
					showPopupInsertInfoContractOffer(accountDTO, false);
				} else {
					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notcontract),
							getActivity().getString(R.string.app_name)).show();
				}

			} else {
				if (Constant.INVALID_TOKEN2.equals(errorCode)) {

					Dialog dialog = CommonActivity.createAlertDialog((Activity) mContext, description,
							context.getResources().getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(mContext, description,
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
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, mContext, "mbccs_getAccountInfor");
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
					Gson g = new Gson();
					Log.i("DATA", "parseOuput: " + g.toJson(parseOuput));
				}

			} catch (Exception e) {
				Log.e("Exception", e.toString(), e);
			}

			return accountDTO;
		}
	}

	/**
	 * Thong tin ngan hang
	 */
	private LinearLayout lnBankAccount;
	private EditText edit_ngaynhothu;
	private EditText edthdnhothu;
	private EditText edittkoanhd;
	private EditText edttentkoan;
	private TextView txtnganhang;

	/**
	 * Thong tin chi tiet hop dong
	 */
	protected EditText edt_contractNo; // so hop dong
	protected EditText edt_contractNoInHoadon; // so hop dong in hoa don
	protected EditText edt_payer; // nguoi thanh toan
	protected Spinner spn_contractTypeCode; // loai hop dong
	protected EditText edt_signDate; // ngay ki
	protected EditText edt_effectDate; // ngay hieu luc
	protected EditText edt_endEffectDate; // ngay het han hop dong
	protected Spinner spn_payMethodCode; // hinh thuc thanh toan
	protected Spinner spn_billCycleFromCharging; // chu ky cuoc
	protected Spinner spn_printMethodCode, spn_noticeCharge; // in chi tiet


	/**
	 * Dialog dai dien hop dong cu
	 *
	 * @author Aloha
	 */

	private ProgressBar prbhttthd, prbchukicuoc, prbinchitiet, prbhttbc;
	private Button btnhttthd, btnchukicuoc, btninchitiet, btnhttbc;

	private EditText edtemailtbc, edtdtcdtbc, edtdidongtbc, txtdctbc, edtdchdcuoc;

	// thong tin dai dien hop dong
	private ViewGroup lnViewddhd;
	private LinearLayout lnttdaidienhd, lngiaytoxacminh;
	private EditText edtloaikhDD, edit_sogiaytoDD, edit_ngaycapDD, edtnoicap, txtdcgtxmDD, edit_tenKHDD,
			edit_ngaysinhdDD;
	private ProgressBar prbCustTypeDD;
	private Button btnRefreshCustTypeDD, btnkiemtra, btnedit;
	private Spinner spinner_type_giay_to_parent, spinner_gioitinhDD;
	private LinearLayout lnsoHD, lnsoHDInHoaDon;

	private Dialog dialogContract;

	View dialogView;
	ViewGroup main;

	private AccountDTO accountDTOMain;
	private ArrayList<CustTypeDTO> arrCusTypeDTO = new ArrayList<CustTypeDTO>();
	private String dateNowString = null;

	private String addInfoStr = "";

	private AccountDTO accountDTOMainNew;

	private CustIdentityDTO custIdentityDTO = new CustIdentityDTO();

	private Spinner spinner_quoctichpr;
	private ArrayList<Spin> spinNation = new ArrayList<Spin>();

	private CustTypeDTO custTypeDTOContractPR = null;
	private String typePaperDialogPR;

	public static String subType = "2";

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
	private StringBuilder areaCodeContractPR;

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

	private ArrayList<SexBeans> arrSexBeans = new ArrayList<SexBeans>();
	private ArrayList<CustIdentityDTO> arrTypePaper = new ArrayList<CustIdentityDTO>();

	private ArrayList<Spin> arrHTTTHD = null;
	private ArrayList<Spin> arrCKC = null;
	private ArrayList<Spin> arrINCT = null;
	private ArrayList<Spin> arrHTTBC = null;

	public static final int TYPE_HTTHHD = 1;
	public static final int TYPE_CK_CUOC_HD = 2;
	public static final int TYPE_INCT_HD = 3;
	public static final int TYPE_HTTBC_HD = 4;

	private String serviceType = "";


//	private Spinner spinner_quota, spinner_deposit;
//	private ContractFormMngtBean bankBean = null;
	private ArrayList<Spin> arrQouta = new ArrayList<Spin>();

	private Boolean isRefreshTYPE_HTTTHD = false;
	private Boolean isRefreshTYPE_CK_CUOC_HD = false;
	private Boolean isRefreshTYPE_INCT_HD = false;
	private Boolean isRefreshTYPE_HTTBC_HD = false;

	private Spinner spiner_lydo;

//	private LinearLayout lndeposit, lnqouta;



	private void createDateString() {
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
//			dateBD = sdf.parse(dateNowString);
//			dateEnd = sdf.parse(dateNowString);
//			birthDateHs = sdf.parse(dateNowString);
		} catch (Exception e) {
			Log.e("Exception", e.toString(), e);
		}
	}

	// truyen ngan hang len hoac khon
	private boolean isBank = false;

	private void showPopupInsertInfoContractOffer(final AccountDTO accountDTO,boolean isNew) {

		createDateString();
		accountDTOMain = new AccountDTO();


		final ArrayList<CustTypeDTO> arrCusType = new ArrayList<CustTypeDTO>();
		if (arrCusTypeDTO != null && !arrCusTypeDTO.isEmpty()) {
			for (CustTypeDTO item : arrCusTypeDTO) {
				if ("2".equals(item.getGroupType())) {

				} else {
					arrCusType.add(item);
				}
			}

		}

		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		dialogView = inflater.inflate(R.layout.connectionmobile_contract_update, null, false);

		dialogContract = new Dialog(mContext);
		dialogContract.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogContract.setContentView(dialogView);
		dialogContract.setCancelable(false);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String day = sdf.format(new Date());

		main = (ViewGroup) dialogContract.findViewById(R.id.main);
		prbhttthd = (ProgressBar) dialogContract.findViewById(R.id.prbhttthd);
		prbchukicuoc = (ProgressBar) dialogContract.findViewById(R.id.prbchukicuoc);
		prbinchitiet = (ProgressBar) dialogContract.findViewById(R.id.prbinchitiet);
		prbhttbc = (ProgressBar) dialogContract.findViewById(R.id.prbhttbc);
		btnhttthd = (Button) dialogContract.findViewById(R.id.btnhttthd);
		btnchukicuoc = (Button) dialogContract.findViewById(R.id.btnchukicuoc);
		btninchitiet = (Button) dialogContract.findViewById(R.id.btninchitiet);
		btnhttbc = (Button) dialogContract.findViewById(R.id.btnhttbc);

		lnsoHD = (LinearLayout) dialogContract.findViewById(R.id.lnsoHD);
		lnsoHDInHoaDon = (LinearLayout) dialogContract.findViewById(R.id.lnsoHDInhoadon);

		spiner_lydo = (Spinner) dialogContract.findViewById(R.id.spinner_lydo);

		getReason();


		if (isNew) {
			lnsoHD.setVisibility(View.GONE);
		} else {
			lnsoHD.setVisibility(View.VISIBLE);
		}
		// so hop dong
		edt_contractNo = (EditText) dialogContract.findViewById(R.id.edtsohd);
		edt_contractNoInHoadon = (EditText) dialogContract.findViewById(R.id.edtsohdInHoaDon);
		// ngay ky hop dong
		edt_signDate = (EditText) dialogContract.findViewById(R.id.edit_ngayky);
		edt_signDate.setText(dateNowString);
		edt_signDate.setOnClickListener(editTextListener);
		// chu ky cuoc
		spn_billCycleFromCharging = (Spinner) dialogContract.findViewById(R.id.spinner_chukicuoc);
		// hinh thuc thanh toan
		spn_payMethodCode = (Spinner) dialogContract.findViewById(R.id.spinner_httthd);
		// hinh thuc thong bao cuoc spinner_httbc
		spn_payMethodCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Spin item = (Spin) spn_payMethodCode.getItemAtPosition(arg2);

				if ("02".equalsIgnoreCase(item.getId()) || "03".equalsIgnoreCase(item.getId())) {
					lnBankAccount.setVisibility(View.VISIBLE);
					accountDTOMain.setPayMethod(item.getId());
					accountDTOMain.setPayMethodName(item.getValue());
					isBank = true;
				} else {
					isBank = false;
					lnBankAccount.setVisibility(View.GONE);
				}


			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		// hinh thuc thong bao cuoc
		spn_noticeCharge = (Spinner) dialogContract.findViewById(R.id.spinner_httbc);
		llFee = (ViewGroup) dialogContract.findViewById(R.id.ll_fee);
		ll_table_fee = (ViewGroup) dialogContract.findViewById(R.id.ll_table_fee);

		/**
		 * Thong tin ngan hang
		 */
		lnBankAccount = (LinearLayout) dialogContract.findViewById(R.id.lnhinhthuctthd);
		lnBankAccount.setVisibility(View.GONE);
		edit_ngaynhothu = (EditText) dialogContract.findViewById(R.id.edit_ngaynhothu); // ngay
		edit_ngaynhothu.setText(dateNowString);
		edit_ngaynhothu.setOnClickListener(editTextListener);
		edthdnhothu = (EditText) dialogContract.findViewById(R.id.edthdnhothu); // hop
		edittkoanhd = (EditText) dialogContract.findViewById(R.id.edittkoanhd); // tai
		edttentkoan = (EditText) dialogContract.findViewById(R.id.edttentkoan); // ten
		txtnganhang = (TextView) dialogContract.findViewById(R.id.txtnganhang); // chon

		spn_printMethodCode = (Spinner) dialogContract.findViewById(R.id.spinner_inchitiet); // in
		// chi
		// tiet

		edtemailtbc = (EditText) dialogContract.findViewById(R.id.edtemailtbc);
		edtdtcdtbc = (EditText) dialogContract.findViewById(R.id.edtdtcdtbc);
		edtdidongtbc = (EditText) dialogContract.findViewById(R.id.txtdtdidongtbc);
		txtdctbc = (EditText) dialogContract.findViewById(R.id.txtdctbc);
		Button btncancel = (Button) dialogContract.findViewById(R.id.btn_cancel);
		btncancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialogContract.dismiss();
			}
		});
		txtdctbc.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String strProvince1 = Session.province;
				String strDistris1 = Session.district;

				Bundle mBundle1 = new Bundle();
				mBundle1.putString("strProvince", strProvince1);
				mBundle1.putString("strDistris", strDistris1);
				mBundle1.putString("KEYPR", "1111");
				mBundle1.putBoolean("isCheckStreetBlock", true);
				Intent i1 = new Intent(getActivity(), CreateAddressCommon.class);
				i1.putExtras(mBundle1);
				startActivityForResult(i1, 11112);
			}
		});
		edtdchdcuoc = (EditText) dialogContract.findViewById(R.id.edtdchdcuoc);

		// thong tin dai dien hop dong
		lnViewddhd = (ViewGroup) dialogContract.findViewById(R.id.lnViewddhd);
		lnttdaidienhd = (LinearLayout) dialogContract.findViewById(R.id.lnttdaidienhd);
		// khach hang doanh nghiep
//		Gson g = new Gson();
//		Log.i("DATA","custIdentityDTO: "+g.toJson(custIdentityDTO));
		if ("2".equals(custIdentityDTO.getGroupType())) {
			lnViewddhd.setVisibility(View.VISIBLE);
			lnttdaidienhd.setVisibility(View.VISIBLE);
		} else {
			lnViewddhd.setVisibility(View.GONE);
			lnttdaidienhd.setVisibility(View.GONE);
		}

		lngiaytoxacminh = (LinearLayout) dialogContract.findViewById(R.id.lngiaytoxacminh);

		edtloaikhDD = (EditText) dialogContract.findViewById(R.id.edtloaikh);


		edtloaikhDD.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(mContext, FragmentSearchCusTypeMobile.class);
//                intent.putExtra("arrCustTypeDTOsKey", arrCusType);
				Bundle mBundle = new Bundle();
				mBundle.putString("GROUPKEY", "GROUPKEY");
				intent.putExtras(mBundle);
				startActivityForResult(intent, 1444);

			}
		});

		edit_sogiaytoDD = (EditText) dialogContract.findViewById(R.id.edit_sogiaytoDD);
		edit_ngaycapDD = (EditText) dialogContract.findViewById(R.id.edit_ngaycap);
		edit_ngaycapDD.setText(dateNowString);
		edit_ngaycapDD.setOnClickListener(editTextListener);
		edtnoicap = (EditText) dialogContract.findViewById(R.id.edtnoicap);
		txtdcgtxmDD = (EditText) dialogContract.findViewById(R.id.txtdcgtxm);
		txtdcgtxmDD.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String strProvince1 = Session.province;
				String strDistris1 = Session.district;

				Log.i("DATA","strProvince1: "+strProvince1+" strDistris1: "+strDistris1);

				Bundle mBundle1 = new Bundle();
				mBundle1.putString("strProvince", strProvince1);
				mBundle1.putString("strDistris", strDistris1);
				mBundle1.putString("KEYPR", "1111");
				Intent i1 = new Intent(getActivity(), CreateAddressCommon.class);
				i1.putExtras(mBundle1);
				startActivityForResult(i1, 11113);
			}
		});
		edit_tenKHDD = (EditText) dialogContract.findViewById(R.id.edit_tenKHDD);
		edit_ngaysinhdDD = (EditText) dialogContract.findViewById(R.id.edit_ngaysinhd);
		edit_ngaysinhdDD.setText(dateNowString);
		edit_ngaysinhdDD.setOnClickListener(editTextListener);
		spinner_quoctichpr = (Spinner) dialogContract.findViewById(R.id.spinner_quoctichpr);
		initNationly(spinner_quoctichpr);
		prbCustTypeDD = (ProgressBar) dialogContract.findViewById(R.id.prbCustTypeDD);
		btnRefreshCustTypeDD = (Button) dialogContract.findViewById(R.id.btnRefreshCustType);
		btnkiemtra = (Button) dialogContract.findViewById(R.id.btnkiemtra);

		btnkiemtra.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (CommonActivity.isNullOrEmpty(edit_sogiaytoDD.getText().toString().trim())) {

					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.chua_nhap_giay_to),
							getActivity().getString(R.string.app_name)).show();
					return;

				}
				SearchCustIdentityDDAsyn searchCustIdentityAsyn = new SearchCustIdentityDDAsyn(getActivity(), "1");
				if (custTypeDTOContractPR != null) {
					searchCustIdentityAsyn.execute(edit_sogiaytoDD.getText().toString().trim(),
							custTypeDTOContractPR.getCustType(), typePaperDialogPR);
				} else {
					searchCustIdentityAsyn.execute(edit_sogiaytoDD.getText().toString().trim(), "", typePaperDialogPR);
				}

			}
		});

		btnedit = (Button) dialogContract.findViewById(R.id.btnedit);

		btnedit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				disenableRepresentCus();

			}
		});
		if (!CommonActivity.isNullOrEmpty(accountDTO) && !CommonActivity.isNullOrEmpty(accountDTO.getRefCustomer())
				&& !CommonActivity.isNullOrEmpty(accountDTO.getRefCustomer().getCustId())) {
			btnkiemtra.setVisibility(View.GONE);
			btnedit.setVisibility(View.GONE);
		} else {
			btnkiemtra.setVisibility(View.VISIBLE);
			btnedit.setVisibility(View.GONE);
		}

		spinner_type_giay_to_parent = (Spinner) dialogContract.findViewById(R.id.spinner_type_giay_to_parent);
		spinner_type_giay_to_parent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

//				Log.i("DATA", "arrTypePaper: "+arrTypePaper.size());

				if (arrTypePaper != null && !arrTypePaper.isEmpty()) {
					typePaperDialogPR = arrTypePaper.get(arg2).getIdType();
				} else {
					typePaperDialogPR = "";
				}

//				Log.i("DATA", "typePaperDialogPR: "+typePaperDialogPR);


			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		spinner_gioitinhDD = (Spinner) dialogContract.findViewById(R.id.spinner_gioitinh);

		if (!CommonActivity.isNullOrEmptyArray(arrSexBeans)) {
			arrSexBeans = new ArrayList<SexBeans>();
		}
		initSex();
		enableContract();

//		Log.i("DATA","accountDTO.getRefCustomer(): "+accountDTO.getRefCustomer().getCustType());
//		Log.i("DATA","accountDTO.getRefCustomer()getCustTypeDTO: "+accountDTO.getRefCustomer().getCustTypeDTO().getCustType());

		if (!CommonActivity.isNullOrEmpty(accountDTO) && !CommonActivity.isNullOrEmpty(accountDTO.getRefCustomer())) {
			Log.i("DATA","accountDTO.getRefCustomer()-------: "+accountDTO.getRefCustomer().getCustType());
			edtloaikhDD.setHint(accountDTO.getRefCustomer().getCustType());

			if ("2".equals(custIdentityDTO.getGroupType())) {
				// lay danh sach loáº¡i giay to theo loai khach hang
				GetMappingCustIdentityUsageAsyn getMappingCustIdentityUsageAsyn = new GetMappingCustIdentityUsageAsyn(
						getActivity(), null, null, spinner_type_giay_to_parent);
				getMappingCustIdentityUsageAsyn.execute(accountDTO.getRefCustomer().getCustType());
			}

		}



//		if ("2".equalsIgnoreCase(subType) && isNew && !CommonActivity.isNullOrEmptyArray(arrHTTTHD)) {
			AsyntaskGetListAllCommon asyntaskGetListAllCommon = new AsyntaskGetListAllCommon(mContext, TYPE_HTTHHD);
			asyntaskGetListAllCommon.execute();
//		}

		btncancel.setVisibility(View.VISIBLE);
		// conTractBean
		if (accountDTO != null) {
			btncancel.setVisibility(View.VISIBLE);
			AccountDTO contract = accountDTO;
//			Log.i("DATA","contract.getAccountId(): "+contract.getAccountId()+" contract.getAccountNo():"+contract.getAccountNo());
			if (contract != null && contract.getAccountId() != null
					&& !CommonActivity.isNullOrEmpty(contract.getAccountNo())) {

				provinceContract = contract.getProvince();


				disableContract();
				// init giao dien
				edt_contractNo.setText(contract.getAccountNo()); // so hop dong
				edt_contractNoInHoadon.setText(contract.getPrintContractNo()); // so hop dong in hoa don
			} else {
				enableContract();
				// init giao dien
				edt_contractNo.setText(""); // so hop dong
			}


			if (!CommonActivity.isNullOrEmpty(accountDTO) && !CommonActivity.isNullOrEmpty(accountDTO.getRefCustomer())) {
				Log.i("DATA","accountDTO.getRefCustomer(): "+accountDTO.getRefCustomer().getCustType());
				if (!CommonActivity.isNullOrEmpty(accountDTO.getRefCustomer().getCustTypeDTO())) {
					Log.i("DATA","accountDTO.getRefCustomer(): "+accountDTO.getRefCustomer().getCustType());
					edtloaikhDD.setText(accountDTO.getRefCustomer().getCustTypeDTO().getCustType());
				} else {
					edtloaikhDD.setText(accountDTO.getRefCustomer().getCustType());
				}

			}


			edt_signDate.setText(StringUtils.convertDate(contract.getSignDate())); // ngay

			if (!CommonActivity.isNullOrEmpty(contract.getPayMethod()) && arrHTTTHD != null) {
				Utils.setDataSpinner(mContext, arrHTTTHD, spn_payMethodCode);
				for (int i = 0; i < arrHTTTHD.size(); i++) {
					Spin spin = arrHTTTHD.get(i);
					if (spin.getId().equalsIgnoreCase(contract.getPayMethod())) {
						spn_payMethodCode.setSelection(arrHTTTHD.indexOf(spin));
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
				Utils.setDataSpinner(mContext, arrCKC, spn_billCycleFromCharging);
				for (int i = 0; i < arrCKC.size(); i++) {
					Spin spin = arrCKC.get(i);
					if (spin.getId().equalsIgnoreCase(String.valueOf(contract.getBillCycleId()))) {
						spn_billCycleFromCharging.setSelection(arrCKC.indexOf(spin));
						break;
					}
				}
			}
			// in chi tiet
			if (contract.getPrintMethod() != null && arrINCT != null) {
				Utils.setDataSpinner(mContext, arrINCT, spn_printMethodCode);
				for (int i = 0; i < arrINCT.size(); i++) {
					Spin spin = arrINCT.get(i);
					if (spin.getId().equalsIgnoreCase(contract.getPrintMethod())) {
						spn_printMethodCode.setSelection(arrINCT.indexOf(spin));
						break;
					}
				}
			}



			edtdchdcuoc.setText(contract.getBillAddress());

			edtdtcdtbc.setText(contract.getPhoneContact()); // dien thoai co
			// dinh
			edtdidongtbc.setText(contract.getTelMobile()); // dien thoai di
			// dong
			edtemailtbc.setText(contract.geteMail()); // email

			// hinh thuc thong bao cuoc
			if (contract.getNoticeCharge() != null && arrHTTBC != null) {
				Utils.setDataSpinner(mContext, arrHTTBC, spn_noticeCharge);
				for (int i = 0; i < arrHTTBC.size(); i++) {
					Spin spin = arrHTTBC.get(i);
					if (spin.getId().equalsIgnoreCase(contract.getNoticeCharge())) {
						spn_noticeCharge.setSelection(arrHTTBC.indexOf(spin));
						break;
					}
				}
			}

			txtdctbc.setText(contract.getAddress()); // dia chi thong

			AccountBankDTO contractBank = contract.getAccountBank();
//			Log.i("DATA","contractBank:"+g.toJson(contractBank));
			if (contractBank != null) {
				edittkoanhd.setText(contractBank.getAccount()); // bank tai
				edttentkoan.setText(contractBank.getAccountName()); // bank
				if (!CommonActivity.isNullOrEmpty(contractBank.getBankName())){
					txtnganhang.setText(contractBank.getBankName()+"-"+contractBank.getBankCode()); // bank ma
				} else {
					txtnganhang.setText(mContext.getString(R.string.nganhang));
				}

				edthdnhothu.setText(contractBank.getBankAccountNo());
				edit_ngaynhothu.setText(StringUtils.convertDate(contractBank.getBankAccountDate())); // bank
			} else {
				txtnganhang.setText(mContext.getString(R.string.nganhang));
			}

			txtnganhang.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					showDialogListBank();
				}
			});


			Gson g = new Gson();
			Log.i("DATA","contract.getRefCustomer():"+g.toJson(contract.getRefCustomer()));
			// xy ly thong tin dai dien hop dong cu
			if (contract.getRefCustomer() != null && "2".equals(custIdentityDTO.getGroupType())) {
				repreCustomer = new CustIdentityDTO();
				repreCustomer.setCustomer(contract.getRefCustomer());

				lnttdaidienhd.setVisibility(View.VISIBLE);
				lngiaytoxacminh.setVisibility(View.VISIBLE);

				edtloaikhDD.setText(contract.getRefCustomer().getCustTypeDTO().getCustType());
				if (contract.getRefCustomer().getListCustIdentity() != null
						&& contract.getRefCustomer().getListCustIdentity().size() > 0) {
					edit_sogiaytoDD.setText(contract.getRefCustomer().getListCustIdentity().get(0).getIdNo());
//					Log.i("DATA","getIdIssueDateate contract2: "+contract.getRefCustomer().getListCustIdentity().get(0).getIdIssueDate());
					edit_ngaycapDD.setText(contract.getRefCustomer().getListCustIdentity().get(0).getIdIssueDate());
					edtnoicap.setText(contract.getRefCustomer().getListCustIdentity().get(0).getIdIssuePlace());
				}

				txtdcgtxmDD.setText(contract.getRefCustomer().getAddress());
				edit_tenKHDD.setText(contract.getRefCustomer().getName());
				edit_ngaysinhdDD.setText(StringUtils.convertDate(contract.getRefCustomer().getBirthDate()));

				if (!CommonActivity.isNullOrEmptyArray(spinNation)
						&& !CommonActivity.isNullOrEmpty(contract.getRefCustomer().getNationality())) {
					for (Spin spin : spinNation) {
						if (spin.getId().equals(contract.getRefCustomer().getNationality())) {
							spinner_quoctichpr.setSelection(spinNation.indexOf(spin));
							break;
						} else {
							if ("VN".equals(spin.getId())) {
								spinner_quoctichpr.setSelection(spinNation.indexOf(spin));
								break;
							}
						}

					}
				}

				spinner_type_giay_to_parent = (Spinner) dialogContract.findViewById(R.id.spinner_type_giay_to_parent);
				// spinner_gioitinhDD = (Spinner)
				// dialog.findViewById(R.id.spinner_gioitinh);
				if (!CommonActivity.isNullOrEmpty(contract.getRefCustomer().getSex())) {
					for (SexBeans spin : arrSexBeans) {
						if (contract.getRefCustomer().getSex().equals(spin.getValues())) {
							spinner_gioitinhDD.setSelection(arrSexBeans.indexOf(spin));
							spinner_gioitinhDD.setEnabled(false);
							break;
						}
					}

				}



				spinner_type_giay_to_parent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//						Log.i("DATA", "arrTypePaper: "+arrTypePaper.size());

						if (arrTypePaper != null && !arrTypePaper.isEmpty()) {
							typePaperDialogPR = arrTypePaper.get(position).getIdType();
						} else {
							typePaperDialogPR = "";
						}

//						Log.i("DATA", "typePaperDialogPR: "+typePaperDialogPR);

					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {

					}
				});
			}

		}

		if ("2".equals(custIdentityDTO.getGroupType())) {
			if (accountDTO != null && accountDTO.getRefCustomer() != null && accountDTO.getRefCustomer().getCustId() != null) {
				enableRepresentCus(accountDTO.getRefCustomer());
			}
		}


		Button btn_insert_contract = (Button) dialogContract.findViewById(R.id.btn_insert_contract_offer);
		btn_insert_contract.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// xu ly them moi hop dong o day
				try{
					if(validateContract(accountDTO)) {
						CommonActivity.createDialog((Activity) mContext, mContext.getResources().getString(R.string.confirm_update), mContext.getResources().getString(R.string.app_name),
								mContext.getResources().getString(R.string.cancel),
								mContext.getResources().getString(R.string.buttonOk), null, updateOnClick).show();
					}
				}catch (Exception ex){
					Log.e("FragmentSearchContract", "Error validateContract: ", ex);
				}

			}
		});



		// show them thong tin so dien thoai thu 2


		if (!isNew) {
//            Log.i("DATA", "getAccountId: "+accountDTO.getAccountId());
//            Log.i("DATA", "getAddInfo: "+accountDTO.getAddInfo());
			addViewDataWithOldContract(addInfoStr);
		} else {
//			FragmentConnectionMobileNew.GetInfoPlus getInfoPlus = new FragmentConnectionMobileNew.GetInfoPlus(getActivity(), accountDTOMain.getAccountNo());
//			getInfoPlus.execute();
		}


		dialogContract.show();
	}

	View.OnClickListener updateOnClick = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			if (CommonActivity.isNetworkConnected(mContext)) {
				try {
					if (accountDTO != null && accountDTO.getAccountId() != null
							&& !CommonActivity.isNullOrEmpty(accountDTO.getAccountNo())) {
						accountDTOMain = accountDTO;
					}

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
						if (bankBean != null && !CommonActivity.isNullOrEmpty(bankBean.getCode())) {
							accountBank.setBankCode(bankBean.getCode());
							accountBank.setBankName(bankBean.getName());
						}
						if (!CommonActivity.isNullOrEmpty(edit_ngaynhothu.getText().toString())) {
							accountBank.setBankAccountDate(
									StringUtils.convertDateToString(edit_ngaynhothu.getText().toString()));
						}
						if (!CommonActivity.isNullOrEmpty(edthdnhothu.getText().toString())) {
							accountBank.setBankAccountNo(edthdnhothu.getText().toString());
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
//					accountDTOMainNew = accountDTOMain;
	//						}
					// thong tin khach hang dai dien cu voi hop dong cu co
					if ("2".equals(custIdentityDTO.getGroupType())) {
						if (accountDTO != null && accountDTO.getRefCustomer() != null
								&& accountDTO.getRefCustomer().getCustId() != null) {
							accountDTOMain.setRefCustomer(accountDTO.getRefCustomer());
//							if (accountDTOMainNew != null) {
//								accountDTOMainNew.setRefCustomer(accountDTO.getRefCustomer());
//							}
						} else {
							// khi chon khach hang cu
							if (accountDTOMain != null && accountDTOMain.getRefCustomer() != null
									&& accountDTOMain.getRefCustomer().getCustId() != null) {
								dialogContract.dismiss();
								return;
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

								Spin spin = (Spin) spinner_quoctichpr.getSelectedItem();
								if (!CommonActivity.isNullOrEmpty(spin)
										&& !CommonActivity.isNullOrEmpty(spin.getId())) {
									cusDTO.setNationality(spin.getId());
								}

								accountDTOMain.setRefCustomer(cusDTO);
//								if (accountDTOMainNew != null) {
//									accountDTOMainNew.setRefCustomer(cusDTO);
//								}
							}

						}
					}
					// thientv7 luu thong tin moi can cap nhat
					getDataFromViewGenerate(false);
					updateAccountMBCCS();
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				CommonActivity
						.createAlertDialog(mContext, getString(R.string.errorNetwork), getString(R.string.app_name))
						.show();
			}
		}
	};

	private View.OnClickListener editTextListener = new View.OnClickListener() {

		@Override
		public void onClick(View view) {
			EditText edt = (EditText) view;
			Calendar cal = Calendar.getInstance();
			if (!CommonActivity.isNullOrEmpty(edt.getText().toString())) {
				Date date = DateTimeUtils.convertStringToTime(edt.getText().toString(), "dd/MM/yyyy");
				cal.setTime(date);

			}

			DatePickerDialog datePicker = new FixedHoloDatePickerDialog(mContext, AlertDialog.THEME_HOLO_LIGHT, datePickerListener, cal.get(Calendar.YEAR),
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

	private CustIdentityDTO repreCustomer;
	private List<CustomEditText> lstEditTexts = new ArrayList<>();
	private List<AddInfo> lstAddInfoOlds = new ArrayList<>();
	private String addInfo = "";
	private List<AddInfo> lstAddInfos = new ArrayList<>();
	List<ProductSpecCharUseDTO> lstProductSpecCharUseDTOs;
	private void addViewDataWithOldContract(String addInfos){

//        List<AddInfo> lstAddInfos = new ArrayList<>();
		if (!CommonActivity.isNullOrEmpty(addInfos)){
			try {
				JSONArray ja = new JSONArray(addInfos);
				lstAddInfoOlds.clear();
				for (int i = 0; i < ja.length(); i++) {
					JSONObject jo = ja.getJSONObject(i);
					AddInfo addInfo = new AddInfo();

					String contactType = jo.has("contactType")?jo.getString("contactType"):"";
					String contactName = jo.has("contactName")?jo.getString("contactName"):"";
					String value = jo.has("value")?jo.getString("value"):"";

					int minLength = jo.has("minLength")?jo.getInt("minLength"):0;
					int maxLength = jo.has("maxLength")?jo.getInt("maxLength"):0;
					String valueType = jo.has("valueType")?jo.getString("valueType"):"";
					boolean required = jo.has("required")?jo.getBoolean("required"):false;

					addInfo.setContactType(contactType);
					addInfo.setContactName(contactName);
					addInfo.setValue(value);



					//add view
					LinearLayout layout = new LinearLayout(getActivity());
					layout.setOrientation(LinearLayout.HORIZONTAL);
					layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

					TextView titleView = new TextView(getActivity());
					LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 2.1f);
					titleView.setLayoutParams(lparams);

					Log.d("DATA", "NAME: "+contactName);

					if (required){
						titleView.setText(Html.fromHtml(""+contactName+"<font color=\"red\">*</font>"));
					} else {
						titleView.setText(""+contactName);
					}

					layout.addView(titleView);


					CustomEditText editValue = new CustomEditText(getActivity());
					LinearLayout.LayoutParams lparams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.9f);
					editValue.setHint("");
					editValue.setLayoutParams(lparams1);
					editValue.setId(i);
					editValue.setTag(i);

					editValue.setTitleName(contactName);

					// set type editext
					if ("1".equals(valueType)){
						editValue.setInputType(InputType.TYPE_CLASS_TEXT);
					} else if ("2".equals(valueType)){
						editValue.setInputType(InputType.TYPE_CLASS_NUMBER);
					}
					// set min/max length
//                editValue.setMaxLength(maxLength);

					// set required
					editValue.setRequired(false);
					editValue.setText(""+value);

					lstEditTexts.add(editValue);

					layout.addView(editValue);


					main.addView(layout);
					lstAddInfoOlds.add(addInfo);

				}
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
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


	private void showDialogListBank() {
		final Dialog dialog = new Dialog(mContext);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_layout_object_bank);
		lvListObjectCustomer = (LoadMoreListView) dialog.findViewById(R.id.lvListObjectCustomer);
		btnSearchCustomerObject = (Button) dialog.findViewById(R.id.btnSearch);
		btnDeleteCustomerObject = (Button) dialog.findViewById(R.id.btnDelete);
		edtCusCode = (EditText) dialog.findViewById(R.id.edtCodeCustomer);
		edtCusName = (EditText) dialog.findViewById(R.id.edtNameCustomer);

		lvListObjectCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				bankBean = lstContractFormMngtBean.get(position);
				txtnganhang.setText(bankBean.getName()+"-"+bankBean.getCode());
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
				CommonActivity.hideKeyboard(v, mContext);
				String customerName = edtCusName.getText().toString().trim();
				String customerCode = edtCusCode.getText().toString().trim();

				if (!CommonActivity.isNullOrEmpty(customerName) || !CommonActivity.isNullOrEmpty(customerCode)) {
					if (CommonActivity.isNetworkConnected(mContext)) {
						searchBean = new ContractFormMngtBean();
						searchBean.setCode(edtCusCode.getText().toString().trim());
						searchBean.setName(edtCusName.getText().toString().trim());

						lstContractFormMngtBean.clear();
						AsyncBank asynctaskContractFotmMngt = new AsyncBank(searchBean, lstContractFormMngtBean.size(),
								lstContractFormMngtBean.size() + Constant.PAGE_SIZE);
						asynctaskContractFotmMngt.execute();
					} else {
						CommonActivity.createAlertDialog(mContext, getString(R.string.errorNetwork),
								getString(R.string.app_name)).show();
					}
				} else {
					CommonActivity.createAlertDialog(mContext,
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

			this.progress = new ProgressDialog(mContext);
			this.progress.setCancelable(false);

			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		;

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
					customerObjectAdapter = new CustomerObjectAdapter((Activity) mContext, lstContractFormMngtBean);
					lvListObjectCustomer.setAdapter(customerObjectAdapter);
					customerObjectAdapter.notifyDataSetChanged();
				} else {
					if (lstContractFormMngtBean.isEmpty()) {
						lvListObjectCustomer.setAdapter(null);
						CommonActivity.createAlertDialog(mContext, getString(R.string.notnganhang),
								getString(R.string.app_name)).show();
					}
				}
				Log.d(Constant.TAG, "onPostExecute result.size(): " + result.size());
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity.createAlertDialog((Activity) mContext, description,
							mContext.getResources().getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = mContext.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(mContext, description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}

		}

		private List<ContractFormMngtBean> getBankByCode() {
			List<ContractFormMngtBean> lstBank = new ArrayList<ContractFormMngtBean>();
			String original = "";
			try {

				BhldDAL dal = new BhldDAL(getActivity());
				dal.open();
				lstBank = dal.getListBankFromBCCS(searchBean);
				errorCode = "0";
				dal.close();
				// BCCSGateway input = new BCCSGateway();
				// input.addValidateGateway("username", Constant.BCCSGW_USER);
				// input.addValidateGateway("password", Constant.BCCSGW_PASS);
				// input.addValidateGateway("wscode",
				// "mbccs_findActiveBankByCodeName");
				// StringBuilder rawData = new StringBuilder();
				// rawData.append("<ws:findActiveBankByCodeName>");
				// rawData.append("<input>");
				// rawData.append("<token>" + Session.getToken() + "</token>");
				//
				// rawData.append("<bankCodeOrName>" + searchBean.getCode() +
				// "</bankCodeOrName>");
				//
				// // rawData.append("<pageIndex>" + pageIndex +
				// "</pageIndex>");
				// // rawData.append("<pageSize>" + pageSize + "</pageSize>");
				//
				// rawData.append("</input>");
				// rawData.append("</ws:findActiveBankByCodeName>");
				// Log.i("RowData", rawData.toString());
				// String envelope =
				// input.buildInputGatewayWithRawData(rawData.toString());
				// Log.d("Send evelop", envelope);
				// Log.i("LOG", Constant.BCCS_GW_URL);
				// String response = input.sendRequest(envelope,
				// Constant.BCCS_GW_URL, activity,
				// "mbccs_findActiveBankByCodeName");
				// Log.i("Responseeeeeeeeee", response);
				// CommonOutput output = input.parseGWResponse(response);
				// original = output.getOriginal();
				// Log.i("Responseeeeeeeeee Original group", response);
				//
				// JSONObject jsonObject = null;
				// try {
				// jsonObject = XML.toJSONObject(original);
				// Log.i(Constant.TAG, jsonObject.toString());
				//
				// if (jsonObject.has("lstBank")) {
				// Log.i(Constant.TAG, "lstBank Key Found");
				// JSONArray jsonArray = jsonObject.getJSONArray("lstBank");
				// for (int i = 0; i < jsonArray.length(); i++) {
				// JSONObject obj = jsonArray.getJSONObject(i);
				// }
				// } else {
				// Log.i(Constant.TAG, "lstBank Key Not Found");
				// }
				// } catch (Exception e) {
				// Log.e(Constant.TAG, e.getMessage(), e);
				// }
				//
				// // ==== parse xml list ip
				// Document doc = parse.getDomElement(original);
				// NodeList nl = doc.getElementsByTagName("return");
				// NodeList nodechild = null;
				// for (int i = 0; i < nl.getLength(); i++) {
				// Element e2 = (Element) nl.item(i);
				// errorCode = parse.getValue(e2, "errorCode");
				// description = parse.getValue(e2, "description");
				// Log.d(Constant.TAG, "getBankByCode errorCode: " + errorCode +
				// " description: " + description);
				// nodechild = doc.getElementsByTagName("lstBankDTO");
				//
				// if (nodechild != null && nodechild.getLength() > 0) {
				// for (int j = 0; j < nodechild.getLength(); j++) {
				// Element e1 = (Element) nodechild.item(j);
				// ContractFormMngtBean obj = new ContractFormMngtBean();
				// obj.setCode(parse.getValue(e1, "bankCode"));
				// obj.setName(parse.getValue(e1, "name"));
				//
				// lstBank.add(obj);
				// }
				// } else {
				// Log.d(Constant.TAG, "getBankByCode nodechild lstBank NULL or
				// EMPTY");
				// }
				// }

			} catch (Exception e) {
				Log.e("getBankByCode", e.toString(), e);
			}
			return lstBank;
		}
	}

	private void getDataFromViewGenerate(boolean isNew){
		lstAddInfos.clear();


		if (isNew){
			for (int i = 0; i < lstProductSpecCharUseDTOs.size(); i++) {
				AddInfo addInfo = new AddInfo();

				ProductSpecCharDTO ps = lstProductSpecCharUseDTOs.get(i).getListProductSpecCharDTOs();


//            String value = lstEditTexts.get(i).getText().toString();

				CustomEditText customEditText = (CustomEditText) dialogContract.findViewById(i);
				String value = customEditText.getText().toString();

				addInfo.setContactType(ps.getCode());
				addInfo.setValue(value);
				addInfo.setContactName(ps.getName());

				lstAddInfos.add(addInfo);

			}
			Gson g = new Gson();
			addInfo = g.toJson(lstAddInfos);

			Log.i("DATA", "addInfo: "+addInfo);
		} else {
			Gson g = new Gson();
			addInfo = g.toJson(lstAddInfoOlds);

			Log.i("DATA", "lstAddInfoOlds: "+addInfo);
		}

	}

	private boolean validateValueFromView(){
		for (int i = 0; i < lstProductSpecCharUseDTOs.size(); i++) {
			ProductSpecCharDTO ps = lstProductSpecCharUseDTOs.get(i).getListProductSpecCharDTOs();


//            String isDefault = ps.getProductSpecCharValueDTOList().getIsDefault();
			CustomEditText customEditText1 = lstEditTexts.get(i);
			CustomEditText customEditText = (CustomEditText) dialogContract.findViewById(i);
			String value = customEditText.getText().toString();
			int maxLength = customEditText1.getMaxCardinality();
			int minLength = customEditText1.getMinCardinality();
			Log.d("DATA", "maxLength: "+maxLength);
			Log.d("DATA", "minLength: "+minLength);
			Log.d("DATA", "isDefault: "+isDefault);
			Log.d("DATA", "value.length(): "+value.length());
			Log.d("DATA", "value: "+value);
			boolean isDefault = customEditText1.isRequired();
			Log.d("DATA", "isDefault: "+isDefault);
			if (isDefault) { // bat buoc

				if (CommonActivity.isNullOrEmpty(value)) {
					Toast.makeText(mContext, ps.getName()+" "+getString(R.string.checkValueInfo), Toast.LENGTH_SHORT).show();
					return false;
				}
				if (value.length()<minLength||value.length()>maxLength){
					String msg = ps.getName()+" "+getString(R.string.checkMinMaxlength, minLength,maxLength);
					Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
					return false;
				}
			} else {
				if (value.length() != 0){
					if (minLength == 0 && maxLength == 0){

					} else {
						if (value.length()<minLength||value.length()>maxLength){
							String msg = ps.getName()+getString(R.string.checkMinMaxlength, minLength,maxLength);
							Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
							return false;
						}
					}
				}

			}
		}
		return true;
	}

	private void initNationly(Spinner spinquoctich) {
		try {
			BhldDAL dal = new BhldDAL(getActivity());
			dal.open();
			spinNation = dal.getNationaly();
			dal.close();
			Utils.setDataSpinner(getActivity(), spinNation, spinquoctich);

			if (!CommonActivity.isNullOrEmptyArray(spinNation)) {

				for (Spin spin : spinNation) {
					if ("VN".equals(spin.getId())) {
						spinquoctich.setSelection(spinNation.indexOf(spin));
						break;
					}
				}
			}

		} catch (Exception e) {
			Log.d("initNationly", e.toString());
		}

	}

	private void disenableRepresentCus() {

		btnkiemtra.setVisibility(View.VISIBLE);
		btnedit.setVisibility(View.GONE);
		if (accountDTOMain.getRefCustomer() != null) {
			accountDTOMain.setRefCustomer(null);
		}
		edit_sogiaytoDD.setText("");
		edit_sogiaytoDD.setEnabled(true);
		edit_ngaycapDD.setText(dateNowString);
		edit_ngaycapDD.setEnabled(true);
		edtnoicap.setText("");
		edtnoicap.setEnabled(true);
		txtdcgtxmDD.setText(getActivity().getString(R.string.sellect_address));
		txtdcgtxmDD.setEnabled(true);

		edit_tenKHDD.setText("");
		edit_tenKHDD.setEnabled(true);

		edit_ngaysinhdDD.setText(dateNowString);
		edit_ngaysinhdDD.setEnabled(true);

		spinner_gioitinhDD.setEnabled(true);

		// edit_quoctich.setText("");

		spinner_quoctichpr.setEnabled(true);

		edtloaikhDD.setHint(getActivity().getString(R.string.chonloaiKH));

		typePaperDialogPR = "";
		arrTypePaper.clear();
		Utils.setDataSpinner(getActivity(), arrTypePaper, spinner_type_giay_to_parent);

		repreCustomer = new CustIdentityDTO();

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
					Utils.setDataSpinner(mContext, result, spn_payMethodCode);


					// set mac dinh
					if (!CommonActivity.isNullOrEmpty(accountDTO) && !CommonActivity.isNullOrEmpty(accountDTO.getPayMethod())){
						if (!CommonActivity.isNullOrEmptyArray(arrHTTTHD) && spn_payMethodCode != null) {
							for (int i = 0; i < arrHTTTHD.size(); i++) {
								Spin spin = arrHTTTHD.get(i);
								if (spin.getId().equalsIgnoreCase(accountDTO.getPayMethod())) {
									spn_payMethodCode.setSelection(arrHTTTHD.indexOf(spin));
									break;
								}
							}
						}
					} else {
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



					if (!isRefreshTYPE_CK_CUOC_HD) {
						AsyntaskGetListAllCommon asyntaskGetListAllCommon4 = new AsyntaskGetListAllCommon(mContext,
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
					Utils.setDataSpinner(mContext, result, spn_billCycleFromCharging);

					// set mac dinh
					// chu ky cuoc
					if (accountDTO.getBillCycleId() != null && arrCKC != null) {
						Utils.setDataSpinner(mContext, arrCKC, spn_billCycleFromCharging);
						for (int i = 0; i < arrCKC.size(); i++) {
							Spin spin = arrCKC.get(i);
							if (spin.getId().equalsIgnoreCase(String.valueOf(accountDTO.getBillCycleId()))) {
								spn_billCycleFromCharging.setSelection(arrCKC.indexOf(spin));
								break;
							}
						}
					}

					if (!isRefreshTYPE_INCT_HD) {
						AsyntaskGetListAllCommon asyntaskGetListAllCommon5 = new AsyntaskGetListAllCommon(mContext,
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
					Utils.setDataSpinner(mContext, result, spn_printMethodCode);
					// if(CommonActivity.isNullOrEmpty(accountDTO)){

					if (!CommonActivity.isNullOrEmptyArray(arrINCT) && spn_printMethodCode != null && !CommonActivity.isNullOrEmpty(accountDTO.getPrintMethod())) {
						for (int i = 0; i < arrINCT.size(); i++) {
							Spin spin = arrINCT.get(i);
							if (spin.getId().equalsIgnoreCase(accountDTO.getPrintMethod())) {
								spn_printMethodCode.setSelection(arrINCT.indexOf(spin));
								break;
							}
						}
					}
					// }

					// TODO THINHHQ1 tbc
					if (!isRefreshTYPE_HTTBC_HD) {
						AsyntaskGetListAllCommon asyntaskGetListAllCommon6 = new AsyntaskGetListAllCommon(mContext,
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
					Utils.setDataSpinner(mContext, result, spn_noticeCharge);

					if (!CommonActivity.isNullOrEmptyArray(arrHTTBC) && spn_noticeCharge != null && !CommonActivity.isNullOrEmpty(accountDTO.getNoticeCharge())) {
						for (int i = 0; i < arrHTTBC.size(); i++) {
							Spin spin = arrHTTBC.get(i);
							if (spin.getId().equalsIgnoreCase(accountDTO.getNoticeCharge())) {
								spn_noticeCharge.setSelection(arrHTTBC.indexOf(spin));
								break;
							}
						}
					}

				}

			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {

					Dialog dialog = CommonActivity.createAlertDialog((Activity) mContext, description,
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
						Utils.setDataSpinner(mContext, result, spn_payMethodCode);
					}

					if (type == TYPE_CK_CUOC_HD) {
						arrCKC = new ArrayList<Spin>();
						arrCKC.addAll(result);
						Utils.setDataSpinner(mContext, result, spn_billCycleFromCharging);
					}
					if (type == TYPE_INCT_HD) {
						arrINCT = new ArrayList<Spin>();
						arrINCT.addAll(result);
						Utils.setDataSpinner(mContext, result, spn_printMethodCode);
					}
					if (type == TYPE_HTTBC_HD) {
						arrHTTBC = new ArrayList<Spin>();
						arrHTTBC.addAll(result);
						Utils.setDataSpinner(mContext, result, spn_noticeCharge);
					}

					Dialog dialog = CommonActivity.createAlertDialog(mContext, description,
							getResources().getString(R.string.app_name));
					dialog.show();

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
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, mContext,
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
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, mContext,
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
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, mContext,
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
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, mContext,
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

	// init gioi tinh
	private void initSex() {
		arrSexBeans.add(new SexBeans(getString(R.string.male), "M"));
		arrSexBeans.add(new SexBeans(getString(R.string.female), "F"));
		if (arrSexBeans != null && arrSexBeans.size() > 0) {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
					android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
			for (SexBeans sexBeans : arrSexBeans) {
				adapter.add(sexBeans.getName());
			}
			spinner_gioitinhDD.setAdapter(adapter);

			spinner_gioitinhDD.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
					sexDD = spinner_gioitinhDD.getSelectedItemId()+"";
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {

				}
			});
		}
	}


	private void enableContract() {
		edt_contractNo.setEnabled(true);
		edt_contractNoInHoadon.setEnabled(true);
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
		edthdnhothu.setText("");
		edthdnhothu.setEnabled(true);
		edittkoanhd.setText("");
		edittkoanhd.setEnabled(true); // tai
		edttentkoan.setText("");
		edttentkoan.setEnabled(true); // ten
		txtnganhang.setEnabled(true); // chon
		spn_printMethodCode.setEnabled(true); // in

		if (!CommonActivity.isNullOrEmptyArray(arrINCT)) {
			for (int i = 0; i < arrINCT.size(); i++) {
				Spin spin = arrINCT.get(i);
				if ("2".equals(spin.getId())) {
					spn_printMethodCode.setSelection(arrINCT.indexOf(spin));
					break;
				}
			}
		}

		edtemailtbc.setEnabled(true);
		edtdtcdtbc.setEnabled(true);
		edtdidongtbc.setEnabled(true);
		txtdctbc.setEnabled(true);
		edtdchdcuoc.setEnabled(true);
		// thong tin dai dien hop dong
		edtloaikhDD.setText("");
		edtloaikhDD.setHint(getActivity().getString(R.string.chonloaiKH));
		edtloaikhDD.setEnabled(true);



		edit_sogiaytoDD.setText("");
		edit_sogiaytoDD.setEnabled(true);
		edit_ngaycapDD.setEnabled(true);
		edit_ngaycapDD.setText(dateNowString);
		edtnoicap.setText("");
		edtnoicap.setEnabled(true);
		txtdcgtxmDD.setText(getActivity().getString(R.string.sellect_address));
		txtdcgtxmDD.setEnabled(true);
		edit_tenKHDD.setText("");
		edit_tenKHDD.setEnabled(true);
		edit_ngaysinhdDD.setEnabled(true);
		edit_ngaysinhdDD.setText(dateNowString);
		spinner_quoctichpr.setEnabled(true);
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
		edt_contractNoInHoadon.setEnabled(true);
		// ngay ky hop dong
		edt_signDate.setEnabled(false);
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
		spn_printMethodCode.setEnabled(true); // in

		edtemailtbc.setEnabled(true);
		edtdtcdtbc.setEnabled(true);
		edtdidongtbc.setEnabled(true);
		txtdctbc.setEnabled(true);
		edtdchdcuoc.setEnabled(true);
		// thong tin dai dien hop dong

		edtloaikhDD.setEnabled(true);
		edit_sogiaytoDD.setEnabled(true);
		edit_ngaycapDD.setEnabled(true);
		edtnoicap.setEnabled(true);
		txtdcgtxmDD.setEnabled(true);
		edit_tenKHDD.setEnabled(true);
		edit_ngaysinhdDD.setEnabled(true);
		edit_ngaysinhdDD.setText(dateNowString);
		spinner_quoctichpr.setEnabled(true);
		spinner_type_giay_to_parent.setEnabled(true);
		spinner_gioitinhDD.setEnabled(true);
	}

	private void enableRepresentCus(CustomerDTO customerDTO) {
		btnkiemtra.setVisibility(View.GONE);
		btnedit.setVisibility(View.VISIBLE);
//		Log.i("DATA","getIdIssueDate 1: "+customerDTO.getListCustIdentity().get(0).getIdIssueDate());
		if(!CommonActivity.isNullOrEmptyArray(customerDTO.getListCustIdentity())){
			edit_sogiaytoDD.setText(customerDTO.getListCustIdentity().get(0).getIdNo());
			edit_ngaycapDD.setText(StringUtils.convertDate(customerDTO.getListCustIdentity().get(0).getIdIssueDate()));
			edtnoicap.setText(customerDTO.getListCustIdentity().get(0).getIdIssuePlace());

			provinceContractPR = customerDTO.getProvince();
			districtContractPR = customerDTO.getDistrict();

			precintContractPR = customerDTO.getPrecinct();

			streetBlockContractPR = customerDTO.getStreetBlock();

			areaCodeContractPR = new StringBuilder();
			areaCodeContractPR = areaCodeContractPR.append(customerDTO.getAreaCode());


		}

		edit_sogiaytoDD.setEnabled(false);


		edit_ngaycapDD.setEnabled(false);


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

		if (!CommonActivity.isNullOrEmptyArray(spinNation)
				&& !CommonActivity.isNullOrEmpty(customerDTO.getNationality())) {
			for (Spin spin : spinNation) {
				if (spin.getId().equals(customerDTO.getNationality())) {
					spinner_quoctichpr.setSelection(spinNation.indexOf(spin));
					break;
				}
			}
		}
		spinner_quoctichpr.setEnabled(false);


	}

	private boolean validateContract(final AccountDTO item) throws Exception {
//		Log.i("DATA","item.getAccountId(): "+item.getAccountId() + "item.getAccountNo(): "+item.getAccountNo());
		if (item != null && item.getAccountId() != null && !CommonActivity.isNullOrEmpty(item.getAccountNo())) {
//			if (!CommonActivity.isNullOrEmpty(item.getRefCustomer())
//					&& !CommonActivity.isNullOrEmpty(item.getRefCustomer().getCustId())) {
//				repreCustomer = new CustIdentityDTO();
//				repreCustomer.setCustomer(item.getRefCustomer());
//			}

			if ("2".equals(custIdentityDTO.getGroupType())) {
				// validate kh cu
				if (repreCustomer != null
						&& repreCustomer.getCustomer() != null
						&& repreCustomer.getCustomer().getCustId() != null) {

					if (CommonActivity.isNullOrEmpty(provinceContractPR)
							&& CommonActivity.isNullOrEmpty(districtContractPR)
							&& CommonActivity.isNullOrEmpty(precintContractPR)
							&& CommonActivity.isNullOrEmpty(streetBlockContractPR)) {

						CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.addKHempty),
								getActivity().getString(R.string.app_name)).show();
						return false;
					}
					// validate 18 age
					if (com.viettel.bss.viettelpos.v4.utils.Utils.validateAgeAbove18(edit_ngaysinhdDD.getText().toString().trim())) {
						CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validate_above_age),
								getActivity().getString(R.string.app_name)).show();
						return false;
					}
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
						edit_tenKHDD.requestFocus();
						return false;
					}
					if (StringUtils.CheckCharSpecical(edit_tenKHDD.getText().toString())) {
						CommonActivity
								.createAlertDialog(getActivity(), getActivity().getString(R.string.checkcharspecical),
										getActivity().getString(R.string.app_name))
								.show();
						edit_tenKHDD.requestFocus();
						return false;
					}
					if (CommonActivity.isNullOrEmpty(edit_ngaysinhdDD.getText().toString())) {
						CommonActivity.createAlertDialog(getActivity(),
								getActivity().getString(R.string.message_pleass_input_birth_day),
								getActivity().getString(R.string.app_name)).show();
						edit_ngaysinhdDD.requestFocus();
						return false;
					}

					Date birthDate = sdf.parse(edit_ngaysinhdDD.getText().toString().trim());

					if (birthDate.after(dateNow)) {
						CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.nsnhohonhtai),
								getActivity().getString(R.string.app_name)).show();
						return false;
					}



					if (CommonActivity.isNullOrEmpty(edit_sogiaytoDD.getText().toString())) {
						CommonActivity
								.createAlertDialog(getActivity(), getActivity().getString(R.string.chua_nhap_giay_to),
										getActivity().getString(R.string.app_name))
								.show();
						edit_sogiaytoDD.requestFocus();
						return false;
					}
					if ("ID".equals(typePaperDialogPR)) {

						if (edit_sogiaytoDD.getText().toString().length() != 9
								&& edit_sogiaytoDD.getText().toString().length() != 12) {
							CommonActivity
									.createAlertDialog(getActivity(), getActivity().getString(R.string.checksoidno),
											getActivity().getString(R.string.app_name))
									.show();
							edit_sogiaytoDD.requestFocus();
							return false;
						}
					}

					if (CommonActivity.isNullOrEmpty(edit_ngaycapDD.getText().toString())) {
						CommonActivity
								.createAlertDialog(getActivity(), getActivity().getString(R.string.checkissueempty),
										getActivity().getString(R.string.app_name))
								.show();
						edit_ngaycapDD.requestFocus();
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
						CommonActivity
								.createAlertDialog(getActivity(), getActivity().getString(R.string.checkcmtngaycap),
										getActivity().getString(R.string.app_name))
								.show();
						return false;
					}

					if (CommonActivity.isNullOrEmpty(edtnoicap.getText().toString().trim())) {
						CommonActivity
								.createAlertDialog(getActivity(), getActivity().getString(R.string.chua_nhap_noi_cap),
										getActivity().getString(R.string.app_name))
								.show();
						edtnoicap.requestFocus();
						return false;
					}

					if (CommonActivity.isNullOrEmpty(provinceContractPR)
							&& CommonActivity.isNullOrEmpty(districtContractPR)
							&& CommonActivity.isNullOrEmpty(precintContractPR)
							&& CommonActivity.isNullOrEmpty(streetBlockContractPR)) {

						CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.addKHempty),
								getActivity().getString(R.string.app_name)).show();
						return false;
					}
				}
			}

		}

		if (CommonActivity.isNullOrEmpty(edt_signDate.getText().toString().trim())) {
			CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.signDateEmpty),
					getActivity().getString(R.string.app_name)).show();
			edt_signDate.requestFocus();
			return false;
		}

		Spin spinCharge = (Spin) spn_billCycleFromCharging.getSelectedItem();
		if (spinCharge == null) {
			CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.ckcnotempty),
					getActivity().getString(R.string.app_name)).show();
			spn_billCycleFromCharging.requestFocus();
			return false;
		}
		Spin spinPay = (Spin) spn_payMethodCode.getSelectedItem();
		if (spinPay == null) {
			CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.paymethodEmpty),
					getActivity().getString(R.string.app_name)).show();
			spn_payMethodCode.requestFocus();
			return false;
		}
		Spin spinMethod = (Spin) spn_noticeCharge.getSelectedItem();
		if (spinMethod == null) {
			CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.noticeEmpty),
					getActivity().getString(R.string.app_name)).show();
			spn_noticeCharge.requestFocus();
			return false;
		}
		// 1 va 5
		if ("1".equals(spinMethod.getId()) || "5".equals(spinMethod.getId())) {

			if (CommonActivity.isNullOrEmpty(edtemailtbc.getText().toString().trim())) {
				CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.emailempty),
						getActivity().getString(R.string.app_name)).show();
				edtemailtbc.requestFocus();
				return false;
			}

		}

		if ("02".equalsIgnoreCase(spinPay.getId()) || "03".equalsIgnoreCase(spinPay.getId())) {
			if (CommonActivity.isNullOrEmpty(edittkoanhd.getText().toString().trim())) {
				CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.accountnotempty),
						getActivity().getString(R.string.app_name)).show();
				edittkoanhd.requestFocus();
				return false;
			}
			if (CommonActivity.isNullOrEmpty(edttentkoan.getText().toString().trim())) {
				CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.tentknotempty),
						getActivity().getString(R.string.app_name)).show();
				edttentkoan.requestFocus();
				return false;
			}

			if (item != null && !CommonActivity.isNullOrEmpty(item.getAccountBank())) {

			} else {
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


		}

		Spin spinPrinMethod = (Spin) spn_printMethodCode.getSelectedItem();
		if (spinPrinMethod == null) {
			CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.inchitietEmpty),
					getActivity().getString(R.string.app_name)).show();
			spn_printMethodCode.requestFocus();
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
			edtdidongtbc.requestFocus();
			return false;
		}
		if (CommonActivity.isNullOrEmpty(provinceContract) && CommonActivity.isNullOrEmpty(districtContract)
				&& CommonActivity.isNullOrEmpty(precintContract)) {
			CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.billAdressNotEmpty),
					getActivity().getString(R.string.app_name)).show();
			return false;
		}
		if (CommonActivity.isNullOrEmpty(edtdchdcuoc.getText().toString().trim())) {
			edtdchdcuoc.requestFocus();
			CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.addresschargenotempty),
					getActivity().getString(R.string.app_name)).show();
			return false;
		}

		// validate thong tin khach hang dai dien doanh nghiep
//		Log.i("DATA","custIdentityDTO.getGroupType()2222: "+custIdentityDTO.getGroupType());
		if ("2".equals(custIdentityDTO.getGroupType())) {

			// validate kh cu
			if (repreCustomer != null
					&& repreCustomer.getCustomer() != null
					&& repreCustomer.getCustomer().getCustId() != null) {
//				// validate 18 age
//				if (com.viettel.bss.viettelpos.v4.utils.Utils.validateAgeAbove18(edit_ngaysinhdDD.getText().toString().trim())) {
//					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validate_above_age),
//							getActivity().getString(R.string.app_name)).show();
//					return false;
//				}
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

				if (CommonActivity.isNullOrEmpty(provinceContractPR)
						&& CommonActivity.isNullOrEmpty(districtContractPR)
						&& CommonActivity.isNullOrEmpty(precintContractPR)
						&& CommonActivity.isNullOrEmpty(streetBlockContractPR)) {

					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.addKHempty),
							getActivity().getString(R.string.app_name)).show();
					return false;
				}


				// validate 18 age
				if (com.viettel.bss.viettelpos.v4.utils.Utils.validateAgeAbove18(edit_ngaysinhdDD.getText().toString().trim())) {
					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.validate_above_age),
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
						&& CommonActivity.isNullOrEmpty(precintContractPR)
						&& CommonActivity.isNullOrEmpty(streetBlockContractPR)) {

					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.addKHempty),
							getActivity().getString(R.string.app_name)).show();
					return false;
				}
			}
		}
		return true;


	}

	private List<ReasonDTO> listReasonDTOs;

	private void getReason(){
		GetReasonFullPM getReason = new GetReasonFullPM(getActivity());
		getReason.execute();
	}

	private class GetReasonFullPM extends AsyncTask<String, Void, List<ReasonDTO>> {

		private Context mContext;
		private String errorCode;
		private String description;
		private ProgressDialog progress;

		public GetReasonFullPM(Context context) {
			this.mContext = context;
			this.progress = new ProgressDialog(this.mContext);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected List<ReasonDTO> doInBackground(String... arg0) {
			return getListReasonDTO();
		}

		@Override
		protected void onPostExecute(List<ReasonDTO> result) {
			super.onPostExecute(result);
			progress.dismiss();

			if ("0".equals(errorCode)) {

				if (result != null && result.size() > 0) {
					listReasonDTOs = result;

					ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
							android.R.layout.simple_dropdown_item_1line, android.R.id.text1);

					for (ReasonDTO reasonDTO : listReasonDTOs) {
						adapter.add(reasonDTO.getName());
					}
					spiner_lydo.setAdapter(adapter);

					spiner_lydo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
							reasonId = listReasonDTOs.get(position).getReasonId();
							findFeeByReason(reasonId);
						}

						@Override
						public void onNothingSelected(AdapterView<?> parent) {

						}
					});


				} else {
					Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
							getResources().getString(R.string.noththm), getResources().getString(R.string.app_name));
					dialog.show();
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

				rawData.append("<payType>" + "1" + "</payType>");
				rawData.append("<offerId>" + "" + "</offerId>");

				// 220 Chuyen doi tra truoc sang tra sau
				// 221 Chuyen doi TS sang TT

				rawData.append("<actionCode>" + "91" + "</actionCode>");
				rawData.append("<serviceType>" + serviceType + "</serviceType>");


//				if (custIdentityDTO != null && !CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer())) {
//					rawData.append("<cusType>" + custIdentityDTO.getCustomer().getCustType() + "</cusType>");
//				}

				rawData.append("<subType>" + "" + "</subType>");

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
				Log.d("getListReasonDTO", e.toString());
			}

			return null;
		}
	}

	// ham update

	View.OnClickListener updateSuccess = new View.OnClickListener() {


		@Override
		public void onClick(View v) {
//			dDebit.dismiss();
			dialogContract.dismiss();


		}
	};
	View.OnClickListener showPopupDebit = new View.OnClickListener() {


		@Override
		public void onClick(View v) {
			showPopupClearDebit();
		}
	};

	private String sexDD = "";
	private String reasonId = "";
	private void updateAccountMBCCS(){
		UpdateAccountMbccs updateAccountMbccs = new UpdateAccountMbccs(mContext);
		updateAccountMbccs.execute(reasonId);
	}

	public class UpdateAccountMbccs extends AsyncTask<String, Void, String> {

		ProgressDialog progress;
		private Context context = null;
		private String errorCode = "";
		private String description = "";

		public UpdateAccountMbccs(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			// check font

			this.progress.setMessage(context.getResources().getString(
					R.string.update_processing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected String doInBackground(String... params) {
			return updateAccountMbccs(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			Log.i("DATA","result: "+result);
			Log.i("DATA","SUCCESS_CODE: "+Constant.SUCCESS_CODE);
			progress.dismiss();
			if (!CommonActivity.isNullOrEmpty(result)){

				if (result.equalsIgnoreCase(Constant.SUCCESS_CODE)) {

					Dialog dialog = CommonActivity.createAlertDialog((Activity) mContext, mContext.getResources().getString(R.string.update_success),
							mContext.getResources().getString(R.string.app_name), updateSuccess);
					dialog.show();


				} else {
					// check debit
					if (result.contains(ERROR_SALE804) || result.contains(ERROR_SALE805)){
						if (result.contains(DES_DEBIT)){
							Dialog dialog = CommonActivity.createAlertDialog((Activity) mContext, description,
									mContext.getResources().getString(R.string.app_name), showPopupDebit);
							dialog.show();
						} else {
							Dialog dialog = CommonActivity
									.createAlertDialog((Activity) context,
											description, context
													.getResources().getString(R.string.app_name));
							dialog.show();
						}

					} else if (result.contains(DES_DEBIT)){
						Dialog dialog = CommonActivity.createAlertDialog((Activity) mContext, description,
								mContext.getResources().getString(R.string.app_name), showPopupDebit);
						dialog.show();
					} else {
						Dialog dialog = CommonActivity
								.createAlertDialog((Activity) context,
										description, context
												.getResources().getString(R.string.app_name));
						dialog.show();
					}

//					Dialog dialog = CommonActivity.createAlertDialog((Activity) mContext, description,
//							mContext.getResources().getString(R.string.app_name), showPopupDebit);
//					dialog.show();


				}
			} else {
				if (!CommonActivity.isNullOrEmpty(description)){
					Dialog dialog = CommonActivity
							.createAlertDialog((Activity) context,
									description, context
											.getResources().getString(R.string.app_name));
					dialog.show();
				} else {
					Dialog dialog = CommonActivity
							.createAlertDialog((Activity) context,
									getResources().getString(R.string.update_failure), context
											.getResources().getString(R.string.app_name));
					dialog.show();
				}

			}

		}

		public String updateAccountMbccs(String reasonId) {
			String original = null;
			String result = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_updateAccountMbccs");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:updateAccountMbccs>");
				rawData.append("<input>");
				rawData.append("<token>" + Session.getToken() + "</token>");



				rawData.append("<accountDTO>");

				rawData.append("<accountNo>"+ accountDTOMain.getAccountNo() +"</accountNo>");
				rawData.append("<printContractNo>"+ edt_contractNoInHoadon.getText().toString() +"</printContractNo>");
				rawData.append("<accountId>"+ accountDTOMain.getAccountId() +"</accountId>");
				rawData.append("<billCycleId>"+ accountDTOMain.getBillCycleId() +"</billCycleId>");
//				rawData.append("<signDate>"+ accountDTOMain.getSignDate() +"</signDate>");
				rawData.append("<payMethod>"+ accountDTOMain.getPayMethod() +"</payMethod>");
				rawData.append("<noticeCharge>"+ accountDTOMain.getNoticeCharge() +"</noticeCharge>");
				rawData.append("<printMethod>"+ accountDTOMain.getPrintMethod() +"</printMethod>");
				rawData.append("<eMail>"+ edtemailtbc.getText().toString() +"</eMail>");
				rawData.append("<telMobile>"+ edtdidongtbc.getText().toString() +"</telMobile>");
				rawData.append("<phoneContact>"+ accountDTOMain.getPhoneContact() +"</phoneContact>");
				rawData.append("<province>"+ accountDTOMain.getProvince() +"</province>");
				rawData.append("<district>"+ accountDTOMain.getDistrict() +"</district>");
				rawData.append("<precinct>"+ accountDTOMain.getPrecinct() +"</precinct>");
				rawData.append("<streetBlock>"+ accountDTOMain.getStreetBlock() +"</streetBlock>");
				if(!CommonActivity.isNullOrEmpty(areaHomeNumberContract)){
					rawData.append("<home>"+ areaHomeNumberContract +"</home>");
				}else{
					rawData.append("<home>"+ accountDTOMain.getHome() +"</home>");
				}
				if(!CommonActivity.isNullOrEmpty(areaFlowContract)){
					rawData.append("<streetName>"+ areaFlowContract +"</streetName>");
				}else{
					rawData.append("<streetName>"+ accountDTOMain.getStreetName() +"</streetName>");
				}

				rawData.append("<streetBlockName>"+ accountDTOMain.getStreetBlockName() +"</streetBlockName>");
				rawData.append("<areaCode>"+ accountDTOMain.getAreaCode() +"</areaCode>");
				rawData.append("<billAddress>"+ edtdchdcuoc.getText().toString() +"</billAddress>");
				rawData.append("<addressPrint>"+ edtdchdcuoc.getText().toString() +"</addressPrint>");

				// acount bank

				if (isBank){
					rawData.append(accountDTOMain.getAccountBank().toXml());
					rawData.append(accountDTOMain.getAccountBank().toXmlAccountBank());
				}



				// refcustomer

				// khach hang dai dien
				if ("2".equals(custIdentityDTO.getGroupType())) {



				rawData.append("<refCustomer>");

				rawData.append("<name>" + "" + edit_tenKHDD.getText().toString());
				rawData.append("</name>");

				String sexStr = "";
				if ("0".equals(sexDD)){
					sexStr = "M";
				} else {
					sexStr = "F";
				}

				rawData.append("<sex>" + "" + sexStr);
				rawData.append("</sex>");


				rawData.append("<birthDate>" + ""
						+ StringUtils.convertDateToString(edit_ngaysinhdDD.getText().toString())
						+ "T00:00:00+07:00");
				rawData.append("</birthDate>");

				// id no
				rawData.append("<custIdentityDTO>");
				rawData.append("<idNo>" + "" + edit_sogiaytoDD.getText().toString());
				rawData.append("</idNo>");


				Log.i("DATA", "typePaperDialogPR: " + typePaperDialogPR);
				rawData.append("<idType>" + "" + typePaperDialogPR);
				rawData.append("</idType>");


				rawData.append("<required>" + true);
				rawData.append("</required>");

				rawData.append(
						"<idIssueDate>" + "" +  StringUtils.convertDateToString(edit_ngaycapDD.getText().toString())
								+ "T00:00:00+07:00");;
				rawData.append("</idIssueDate>");

				rawData.append(
						"<idIssuePlace>" + "" +  edtnoicap.getText().toString());
				rawData.append("</idIssuePlace>");

				rawData.append("</custIdentityDTO>");


				rawData.append("<identityNo>" + "" + edit_sogiaytoDD.getText().toString());
				rawData.append("</identityNo>");




				rawData.append("<custAdd>");
				rawData.append("<province>");
				rawData.append("<code>" + "" + provinceContractPR);
				rawData.append("</code>");
				rawData.append("</province>");

				rawData.append("<district>");
				rawData.append("<code>" + "" + districtContractPR);
				rawData.append("</code>");
				rawData.append("</district>");

				rawData.append("<precinct>");
				rawData.append("<code>" + "" + precintContractPR);
				rawData.append("</code>");
				rawData.append("</precinct>");

				rawData.append("<streetBlock>");
				rawData.append(
						"<code>" + "" + streetBlockContractPR);
				rawData.append("</code>");
				rawData.append("</streetBlock>");

				rawData.append(
						"<areaCode>" + "" + areaCodeContractPR);
				rawData.append("</areaCode>");
				rawData.append(
						"<fullAddress>" + "" + txtdcgtxmDD.getText().toString());
				rawData.append("</fullAddress>");

				rawData.append("</custAdd>");

				rawData.append(
						"<province>" + "" + provinceContractPR);
				rawData.append("</province>");
				rawData.append(
						"<district>" + "" + districtContractPR);
				rawData.append("</district>");
				rawData.append(
						"<precinct>" + "" + precintContractPR);
				rawData.append("</precinct>");
				rawData.append("<streetBlock>" + ""
						+ streetBlockContractPR);
				rawData.append("</streetBlock>");

				rawData.append("<home>" + "" + areaHomeNumberContractPR);
				rawData.append("</home>");

				rawData.append("<streetName>" + ""+areaFlowContractPR);
				rawData.append("</streetName>");

				Spin spin = (Spin) spinner_quoctichpr.getSelectedItem();

				rawData.append("<nationality>" + ""
							+ spin.getId());
				rawData.append("</nationality>");

				rawData.append(
						"<areaCode>" + "" + areaCodeContractPR);
				rawData.append("</areaCode>");
				rawData.append(
						"<address>" + "" + txtdcgtxmDD.getText().toString());
				rawData.append("</address>");


//					Log.i("DATA","custTypeDTOContractPR.getCustType():---------- ");

//					Log.i("DATA","custTypeDTOContractPR.getCustType(): "+custTypeDTOContractPR.getCustType());

				if(!CommonActivity.isNullOrEmpty(custTypeDTOContractPR)&& !CommonActivity.isNullOrEmpty(custTypeDTOContractPR.getCustType())){

//					Log.i("DATA","custTypeDTOContractPR.getCustType():---------- ");
					rawData.append(
							"<custType>" + "" + custTypeDTOContractPR.getCustType());
					rawData.append("</custType>");
				} else {

//					Log.i("DATA","accountDTO.getRefCustomer().getCustType()");
					rawData.append(
							"<custType>" + "" + accountDTO.getRefCustomer().getCustType());
					rawData.append("</custType>");
				}

				// bo sung ngay cap, noi cap




				rawData.append("</refCustomer>");

				}


				rawData.append("</accountDTO>");




				rawData.append("<reasonId>" + reasonId + "</reasonId>");




				rawData.append("</input>");
				rawData.append("</ws:updateAccountMbccs>");
				String envelope = input.buildInputGatewayWithRawData(CommonActivity.replaceNull(rawData.toString()));
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_updateAccountMbccs");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				Log.d("originalllllllll", original);

				Serializer serializer = new Persister();
				ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
				if (parseOuput != null) {
					errorCode = parseOuput.getErrorCode();
					description = parseOuput.getDescription();

					result = description;
				}

				return result;
			} catch (Exception e) {
				Log.d("result", e.toString());
			}

			return null;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d("TAG 9", "FragmentConnectionMobileNew onActivityResult requestCode : " + requestCode);
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

					areaCodeContractPR = new StringBuilder();

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

					if (!CommonActivity.isNullOrEmpty(provinceContractPR) && !CommonActivity.isNullOrEmpty(districtContractPR) && !CommonActivity.isNullOrEmpty(precintContractPR)){
						areaCodeContractPR.append(provinceContractPR).append(districtContractPR).append(precintContractPR);

					}

					break;
				case 1444:
					if (data != null) {
						custTypeDTOContractPR = (CustTypeDTO) data.getExtras().getSerializable("cusTypeDTOsKey");
						if (custTypeDTOContractPR != null
								&& !CommonActivity.isNullOrEmpty(custTypeDTOContractPR.getCustType())) {
							edtloaikhDD.setText(custTypeDTOContractPR.getName());
							// lay danh sach loáº¡i giay to theo loai khach hang
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
					Dialog dialog = CommonActivity.createAlertDialog(mContext,
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
					Dialog dialog = CommonActivity.createAlertDialog(mContext, description,
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
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, mContext,
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




	// get chi tiet customerDTO DD
	private class AsynGetCustomerByCustIdDD extends AsyncTask<String, Void, CustomerDTO> {

		private String errorCode;
		private String description;
		private Context context;
		private ProgressDialog progress;
		private CustIdentityDTO reCustIdentityDTO;

		public AsynGetCustomerByCustIdDD(Context mContext, CustIdentityDTO custIdentityDTO) {
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
				// customerDTODialog = new CustomerDTO();
				if (result != null && result.getCustId() != null) {
					// customerDTODialog = result;
					// xu ly thong tin khach hang dai dien cu cho nay
					// reCustIdentityDTO.setCustomer(customerDTODialog);
					btnkiemtra.setVisibility(View.GONE);
					btnedit.setVisibility(View.VISIBLE);
					enableRepresentCus(result);
					accountDTOMain.setRefCustomer(result);

					Gson g = new Gson();

					Log.i("DATA", "result: "+g.toJson(result));




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

					Dialog dialog = CommonActivity.createAlertDialog((Activity) mContext, description,
							context.getResources().getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog((Activity) mContext, description,
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
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, mContext,
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


	// get chi tiet customerDTO
	private void getCustomerByCustId(String custId){
		AsynGetCustomerByCustId asynGetCustomerByCustId = new AsynGetCustomerByCustId(getActivity());
		asynGetCustomerByCustId.execute(custId);
	}
	private class AsynGetCustomerByCustId extends AsyncTask<String, Void, CustomerDTO> {

		private String errorCode;
		private String description;
		private final Context context;
		private final ProgressDialog progress;
		private final CustIdentityDTO reCustIdentityDTO;

		public AsynGetCustomerByCustId(Context mContext) {
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
			Log.i("DATA","Param[0]: "+params[0]);
			return getCustomerByCustId(params[0]);
		}

		@Override
		protected void onPostExecute(CustomerDTO result) {
			progress.dismiss();
			super.onPostExecute(result);
			if ("0".equals(errorCode)) {
				// thong tin hop dong cu
				CustomerDTO customerDTODialog = new CustomerDTO();
				if (result != null && result.getCustId() != null) {
					customerDTODialog = result;
					// xu ly thong tin khach hang dai dien cu cho nay
					reCustIdentityDTO.setCustomer(customerDTODialog);
					custIdentityDTO.setRepreCustomer(reCustIdentityDTO);

				} else {
					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notDetailCus),
							getActivity().getString(R.string.app_name)).show();
				}

			} else {
				if (Constant.INVALID_TOKEN2.equals(errorCode)) {

					Dialog dialog = CommonActivity.createAlertDialog((Activity) mContext, description,
							context.getResources().getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(mContext, description,
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
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));

				rawData.append("<custId>").append(custId);
				rawData.append("</custId>");

				rawData.append("</input>");
				rawData.append("</ws:getCustomerByCustId>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, mContext,
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

	//show fee

    List<ProductPackageFeeDTO> feeHoamangs = new ArrayList<>();
	private void findFeeByReason(String reasonId){
		feeHoamangs.clear();
		FindFeeByReasonTeleIdAsyn feeByReasonTeleIdAsyn = new FindFeeByReasonTeleIdAsyn(mContext);
		feeByReasonTeleIdAsyn.execute("null", reasonId, "null");
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
//					showDialogGetFee(result);
					ll_table_fee.setVisibility(View.VISIBLE);
					for (int i = 0; i < result.size(); i++) {
						String name = result.get(i).getDescription();
						if (CommonActivity.isNullOrEmpty(name)){
							name = result.get(i).getName();
						}
						String value = result.get(i).getPrice();

						ProductPackageFeeDTO feeDTO = new ProductPackageFeeDTO();
						feeDTO.setName(name);
						feeDTO.setPrice(value);

						feeHoamangs.add(feeDTO);
					}

					addFeeToDialog();

				} else {
					ll_table_fee.setVisibility(View.GONE);
//					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.notthogntinvi),
//							getActivity().getString(R.string.app_name)).show();
				}
			} else {
				ll_table_fee.setVisibility(View.GONE);
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

				rawData.append("<accountId>" + accountDTO.getAccountId() + "</accountId>");
				rawData.append("<telecomServiceId>" + telecomserviceId + "</telecomServiceId>");
				rawData.append("<reasonId>" + reasonId + "</reasonId>");
				rawData.append("<productCode>" + productCode + "</productCode>");

				rawData.append("</input>");
				rawData.append("</ws:findFeeByReasonTeleId>");
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(), "mbccs_findFeeByReasonTeleId");
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


	// add thong tin phi
	ViewGroup llFee;
	ViewGroup ll_table_fee;
	private void addFeeToDialog(){

		llFee.removeAllViewsInLayout();

		for (int i = 0; i < feeHoamangs.size(); i++) {

			ProductPackageFeeDTO feeDTO = feeHoamangs.get(i);

			//add view
			LinearLayout layout = new LinearLayout(getActivity());
			layout.setOrientation(LinearLayout.HORIZONTAL);
			layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

			TextView titleView = new TextView(getActivity());
			LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.5f);
			titleView.setLayoutParams(lparams);
			titleView.setLines(2);

			titleView.setText(feeDTO.getName());


			layout.addView(titleView);


			TextView txtValue = new TextView(getActivity());
			LinearLayout.LayoutParams lparams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 2.5f);
			txtValue.setLayoutParams(lparams1);
			txtValue.setText(StringUtils.formatMoney(feeDTO.getPrice()));


			layout.addView(txtValue);

			llFee.addView(layout);

		}

	}

	// show popup gach no
	private Dialog dDebit;
	View dialogViewDebit;
	private String ERROR_SALE804 = "SALE804";
	private String ERROR_SALE805 = "SALE805";
	private String DES_DEBIT = "đang nợ cước";
	TextView txtMoneyDebit;
	Button btnClearDebit;
	private VirtualInvoice virtualInvoice;
	private void showPopupClearDebit(){
		getClearDebit();
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		dialogViewDebit = inflater.inflate(R.layout.connectionmobile_contract_clear_debit, null, false);

		dDebit = new Dialog(mContext);
		dDebit.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dDebit.setContentView(dialogViewDebit);
		dDebit.setCancelable(false);


		TextView txtContractNo = (TextView) dDebit.findViewById(R.id.txtContractNo);
		txtMoneyDebit = (TextView) dDebit.findViewById(R.id.txtMoneyDebit);

		txtContractNo.setText(accountDTO.getAccountNo());

		Button btnCancel = (Button) dDebit.findViewById(R.id.btn_cancel);
		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dDebit.dismiss();
			}
		});
		btnClearDebit = (Button) dDebit.findViewById(R.id.btn_clear_debit);
		btnClearDebit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dDebit.dismiss();
				paymentContract();
			}
		});


		dDebit.show();
	}


	// get money clear debit
	private void getClearDebit(){
		AsyncGetDebitInfo asy = new AsyncGetDebitInfo(getActivity(),
				accountDTO.getAccountId() + "", onPostGetDebit);
		asy.execute();
	}

	OnPostExecute<SaleOutput> onPostGetDebit = new OnPostExecute<SaleOutput>() {
		@Override
		public void onPostExecute(SaleOutput result) {
			if ("0".equals(result.getErrorCode())) {
				// neu thue bao da duoc chan 2 chieu va khong no cuoc, sang man
				// hinh chuyen doi
				Gson g = new Gson();
				Log.i("DATA","SaleOutput: "+g.toJson(result));
				virtualInvoice = result.getVirtualInvoice();
				txtMoneyDebit.setText(""+StringUtils.formatMoneyFromObject(virtualInvoice
						.getNeedAmount()));
			} else {
				if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
					CommonActivity.BackFromLogin((Activity) getActivity(),
							result.getDescription());
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), result.getDescription(),
							getString(R.string.app_name));
					dialog.show();

				}
			}
		}
	};


	// thuc hien gach no
	private void paymentContract(){
		Long money = 0l;
		if(CommonActivity.isNullOrEmpty(virtualInvoice.getNeedAmount())){
			money = 0l;
		}else{
			money = virtualInvoice.getNeedAmount().longValue();
		}
		String strMoney = money.toString().replaceAll("\\.", "").trim();
		AsyncPaymentDebt payment = new AsyncPaymentDebt(
				getActivity(), accountDTO.getAccountId(), accountDTO.getIsdn(),
				strMoney, onPostPaymentExecute);
		payment.execute();
	}


	private OnPostExecute<SaleOutput> onPostPaymentExecute = new OnPostExecute<SaleOutput>() {
		@Override
		public void onPostExecute(SaleOutput result) {
			if ("0".equals(result.getErrorCode())) {
				View.OnClickListener onClickListener = new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if(dDebit != null){
							dDebit.dismiss();
						}
						updateAccountMBCCS();
					}
				};
				Dialog dialog = CommonActivity.createAlertDialog((Activity) mContext, mContext.getResources().getString(R.string.del_success),
						mContext.getResources().getString(R.string.app_name), onClickListener);
				dialog.show();

			} else {
				if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
					CommonActivity.BackFromLogin((Activity) getActivity(),
							result.getDescription());
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), result.getDescription(),
							getString(R.string.app_name));
					dialog.show();

				}
			}
		}
	};


}
