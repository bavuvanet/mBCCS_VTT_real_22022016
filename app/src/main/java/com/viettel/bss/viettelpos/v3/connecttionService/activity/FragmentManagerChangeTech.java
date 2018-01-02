package com.viettel.bss.viettelpos.v3.connecttionService.activity;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetListInfoSubAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetListInfoSubAdapter.OnCancelInfoSub;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetListInfoSubAdapter.OnCheckChangeInfoSub;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetListInfoSubChildAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetListInfoSubChildAdapter.OnCancelChangeInfoSubChild;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetListInfoSubChildAdapter.OnCheckChangeInfoSubChild;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ChangeInfraReq;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.CustommerByIdNoBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.InfoSub;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.InfoSubChild;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ReceiveRequestBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.TelecomServiceBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetServiceDal;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetTechologyDal;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.work.object.HotlineForm;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class FragmentManagerChangeTech extends Fragment implements
		OnClickListener, OnCheckChangeInfoSub, OnCheckChangeInfoSubChild,
		OnCancelInfoSub, OnCancelChangeInfoSubChild {

	private ArrayList<InfoSub> arrInfoSubsTmp = null;
	private String serviceType = "";
	private Spinner spnService;
	private ImageButton btn_search;
	// private View mView = null;
	private ListView lvrequest;
	private EditText edt_account;

	private GetListInfoSubAdapter getListInfoSubAdapter = null;
	private GetListInfoSubChildAdapter getListInfoSubChildAdapter = null;

	private ArrayList<TelecomServiceBeans> arrTelecomServiceBeans = new ArrayList<TelecomServiceBeans>();
	private InfoSub infoSubItem = null;

	private boolean check = false;

	private ReceiveRequestBean receiveRequestBean = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Bundle mBundle = getArguments();
		if (mBundle != null) {
			receiveRequestBean = (ReceiveRequestBean) mBundle.getSerializable("ReceiveRequestBeanKey");
		}
		View mView = inflater.inflate(
				R.layout.layout_manage_request_technology, container, false);
		unitView(mView);
		
	
		return mView;
	}

	private void unitView(View mView) {

		spnService = (Spinner) mView.findViewById(R.id.spnService);
		spnService.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

				TelecomServiceBeans telecomServiceBeans = arrTelecomServiceBeans
						.get(arg2);
				if (telecomServiceBeans != null) {
					serviceType = telecomServiceBeans.getServiceAlias();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		btn_search = (ImageButton) mView.findViewById(R.id.btn_search);
		btn_search.setOnClickListener(this);
		edt_account = (EditText) mView.findViewById(R.id.edt_account);
		
		if(receiveRequestBean != null && !CommonActivity.isNullOrEmpty(receiveRequestBean.getServiceType()) && !CommonActivity.isNullOrEmpty(receiveRequestBean.getAccountChange())){
			edt_account.setText(receiveRequestBean.getAccountChange());
			edt_account.setEnabled(false);
			btn_search.setVisibility(View.GONE);
			AsynGetInfoSubscriberByAccountAndServiceType asynGetInfoSubscriberByAccountAndServiceType = new AsynGetInfoSubscriberByAccountAndServiceType(
					getActivity());
			asynGetInfoSubscriberByAccountAndServiceType.execute(
					receiveRequestBean.getAccountChange(), receiveRequestBean.getServiceType(),
					"1");
		}else{
			edt_account.setEnabled(true);
			btn_search.setVisibility(View.VISIBLE);
		}
		
		
		lvrequest = (ListView) mView.findViewById(R.id.lvrequest);
		lvrequest.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				if (dialogChild != null) {
					dialogChild = null;
				}
				// check cho nay de show neu tao yeu cau thanh cong thi chuyen
				// luon qua man hinh tao yeu cau

				if (arrInfoSubsTmp != null && arrInfoSubsTmp.size() > 0) {
					infoSubItem = arrInfoSubsTmp.get(arg2);
					ArrayList<InfoSubChild> arrInfoSubChilds = infoSubItem
							.getLstInfoSubChilds();
					if (arrInfoSubChilds != null && arrInfoSubChilds.size() > 0) {

						if (lstInfoSubChilds != null) {
							lstInfoSubChilds = new ArrayList<InfoSubChild>();
						}
						showDialogShowSelectSubChild(infoSubItem,
								arrInfoSubChilds, false);
					} else {

						ArrayList<InfoSubChild> arr = new ArrayList<InfoSubChild>();

						if (infoSubItem.getChangeInfraReq() != null
								&& !CommonActivity.isNullOrEmpty(infoSubItem
										.getChangeInfraReq().getNewAccount())) {
							CommonActivity.createAlertDialog(getActivity(),
									getString(R.string.confirmChangetech),
									getString(R.string.app_name));
						} else {
							if (infoSubItem.getChangeInfraReq() != null
									&& !CommonActivity
											.isNullOrEmpty(infoSubItem
													.getChangeInfraReq()
													.getInfraType())) {

								InfoSubChild item = new InfoSubChild();
								item.setCustommerByIdNoBean(infoSubItem
										.getCustommerByIdNoBean());
								item.setAccount(infoSubItem.getAccount());
								item.setServiceType(infoSubItem
										.getServiceType());
								item.setHotlineForm(infoSubItem
										.getHotlineForm());
								Bundle bundle = new Bundle();
								Intent intent = new Intent(getActivity(),
										ActivityCreateNewRequestHotLine.class);
								bundle.putSerializable("InfoSubKey", item);
								String nameTech = "";
								try {
									GetTechologyDal dal = new GetTechologyDal(
											getActivity());
									dal.open();

									nameTech = dal
											.getNameTechologyCD(infoSubItem
													.getChangeInfraReq()
													.getInfraType());
								} catch (Exception e) {
									e.printStackTrace();
								}

								bundle.putString("technologyKey", infoSubItem
										.getChangeInfraReq().getInfraType());
								bundle.putString("technologyNameKey", nameTech);
								intent.putExtras(bundle);
								startActivity(intent);

							} else {
								InfoSub tmp = new InfoSub();
								InfoSubChild item = new InfoSubChild();
								item.setAccount(infoSubItem.getAccount());
								item.setServiceType(infoSubItem
										.getServiceType());
								item.setCustommerByIdNoBean(infoSubItem
										.getCustommerByIdNoBean());
								item.setHotlineForm(infoSubItem
										.getHotlineForm());
								arr.add(item);
								tmp.setLstInfoSubChilds(arr);

								Bundle mBundle = new Bundle();
								mBundle.putSerializable("INFOSUBKEY", tmp);
								mBundle.putString("serviceTypeKey",
										infoSubItem.getServiceType());
								mBundle.putString("accountKey",
										infoSubItem.getAccount());
								FragmentCreateRequestChangeTech mListMenuManager = new FragmentCreateRequestChangeTech();
								mListMenuManager.setArguments(mBundle);
								ReplaceFragment.replaceFragment(getActivity(),
										mListMenuManager, false);
							}
						}

					}
				}

			}
		});
		initTelecomService();
	}

	// init typepaper
	private void initTelecomService() {
		try {

			GetServiceDal dal = new GetServiceDal(getActivity());
			dal.open();
			arrTelecomServiceBeans = dal.getlisTelecomServiceBeans();
			dal.close();
			TelecomServiceBeans serviceBeans = new TelecomServiceBeans();
			serviceBeans.setTele_service_name(getActivity().getResources()
					.getString(R.string.chondichvu));
			arrTelecomServiceBeans.add(0, serviceBeans);
			if (arrTelecomServiceBeans != null
					&& !arrTelecomServiceBeans.isEmpty()) {
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						getActivity(),
						android.R.layout.simple_dropdown_item_1line,
						android.R.id.text1);
				for (TelecomServiceBeans telecomServiceBeans : arrTelecomServiceBeans) {
					adapter.add(telecomServiceBeans.getTele_service_name());
				}
				spnService.setAdapter(adapter);
			}
			
			if(receiveRequestBean != null && !CommonActivity.isNullOrEmpty(receiveRequestBean.getServiceType())){
				for (TelecomServiceBeans item : arrTelecomServiceBeans) {
					if(receiveRequestBean.getServiceType().equals(item.getServiceAlias())){
						spnService.setSelection(arrTelecomServiceBeans.indexOf(item));
						spnService.setEnabled(false);
					}
				}
			}else{
				spnService.setEnabled(true);
			}
			
		} catch (Exception e) {
			Log.e("initTypePaper", e.toString());
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		addActionBar();
	}

	private void addActionBar() {

		ActionBar mActionBar = getActivity().getActionBar();
		mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		mActionBar.setCustomView(R.layout.layout_actionbar_channel);
		LinearLayout relaBackHome = (LinearLayout) mActionBar.getCustomView()
				.findViewById(R.id.relaBackHome);
		relaBackHome.setOnClickListener(this);
		Button btnHome = (Button) mActionBar.getCustomView().findViewById(
				R.id.btnHome);
		btnHome.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				CommonActivity.callphone(getActivity(), Constant.phoneNumber);
			}
		});
		TextView txtNameActionBar = (TextView) mActionBar.getCustomView()
				.findViewById(R.id.txtNameActionbar);
		txtNameActionBar.setText(getResources().getString(R.string.updatekm));
		TextView txtAddressActionBar = (TextView) mActionBar.getCustomView()
				.findViewById(R.id.txtAddressActionbar);
		txtAddressActionBar.setVisibility(View.GONE);
		txtNameActionBar.setText(getResources().getString(
				R.string.changeTechnology));
	}

	// ham lay thong tin thue bao
	private class AsynGetInfoSubscriberByAccountAndServiceType extends
			AsyncTask<String, Void, ArrayList<InfoSub>> {

		ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public AsynGetInfoSubscriberByAccountAndServiceType(Context mContext) {
			this.context = mContext;
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
		protected ArrayList<InfoSub> doInBackground(String... arg0) {
			return getInfoSubscriberByAccountAndServiceType(arg0[0], arg0[1],
					arg0[2]);
		}

		@Override
		protected void onPostExecute(ArrayList<InfoSub> result) {
			progress.dismiss();
			if ("0".equals(errorCode)) {
				arrInfoSubsTmp = new ArrayList<InfoSub>();

				if (result != null && !result.isEmpty()) {
					arrInfoSubsTmp.addAll(result);
					getListInfoSubAdapter = new GetListInfoSubAdapter(
							arrInfoSubsTmp, getActivity(),
							FragmentManagerChangeTech.this,
							FragmentManagerChangeTech.this);
					lvrequest.setAdapter(getListInfoSubAdapter);
				} else {
					CommonActivity.createAlertDialog(getActivity(),
							getActivity().getString(R.string.checknotaccount),
							getActivity().getString(R.string.app_name)).show();
					arrInfoSubsTmp = new ArrayList<InfoSub>();
					getListInfoSubAdapter = new GetListInfoSubAdapter(
							arrInfoSubsTmp, getActivity(),
							FragmentManagerChangeTech.this,
							FragmentManagerChangeTech.this);
					lvrequest.setAdapter(getListInfoSubAdapter);
					if (getListInfoSubAdapter != null) {
						getListInfoSubAdapter.notifyDataSetChanged();
					}

				}

				// if (result != null
				// && !CommonActivity.isNullOrEmpty(result.getAccount())
				// && (!CommonActivity.isNullOrEmpty(result.getProvince())
				// || !CommonActivity.isNullOrEmpty(result
				// .getDistrict()) || !CommonActivity
				// .isNullOrEmpty(result.getPrecinct()))) {
				// infoSub = result;
				// }
			} else {
				arrInfoSubsTmp = new ArrayList<InfoSub>();

				getListInfoSubAdapter = new GetListInfoSubAdapter(
						arrInfoSubsTmp, getActivity(),
						FragmentManagerChangeTech.this,
						FragmentManagerChangeTech.this);
				lvrequest.setAdapter(getListInfoSubAdapter);
				if (getListInfoSubAdapter != null) {
					getListInfoSubAdapter.notifyDataSetChanged();
				}
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
									getActivity(),
									description,
									context.getResources().getString(
											R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}

					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		private ArrayList<InfoSub> getInfoSubscriberByAccountAndServiceType(
				String account, String serviceType, String isChangeFraRequest) {
			ArrayList<InfoSub> arrInfoSubs = new ArrayList<InfoSub>();
			String original = "";

			boolean checkInfoSub = false;
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				// mbccs_getInfoSubscriberByAccountAndServiceType
				input.addValidateGateway("wscode",
						"mbccs_getInfoSubscriberByAccountAndServiceType");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getInfoSubscriberByAccountAndServiceType>");
				rawData.append("<input>");
				rawData.append("<token>" + Session.getToken() + "</token>");
				rawData.append("<account>" + account + "</account>");
				rawData.append("<serviceType>" + serviceType + "</serviceType>");
				rawData.append("<isGetCust>" + true + "</isGetCust>");
				// check xem co yeu cau chuyen doi cong nghe hay khong
				if ("1".equals(isChangeFraRequest)) {
					rawData.append("<isChangeFraRequest>" + true
							+ "</isChangeFraRequest>");
				} else {
					rawData.append("<isChangeFraRequest>" + false
							+ "</isChangeFraRequest>");
				}

				rawData.append("</input>");
				rawData.append("</ws:getInfoSubscriberByAccountAndServiceType>");

				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getInfoSubscriberByAccountAndServiceType");
				Log.i("LOG", "Respone:  " + response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("LOG", "Responseeeeeeeeee Original  " + original);

				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				// for (int i = 0; i < nl.getLength(); i++) {
				Element e2 = (Element) nl.item(0);
				errorCode = parse.getValue(e2, "errorCode");
				description = parse.getValue(e2, "description");
				Log.d("errorCode", errorCode);
				// lay ra thong tin thue bao
				nodechild = e2.getElementsByTagName("infoSub");
				// for (int j = 0; j < nodechild.getLength(); j++) {
				Node node2 = nodechild.item(0);
				Element e4 = (Element) node2;
				InfoSub infoSub = new InfoSub();
				NodeList nodeList = node2.getChildNodes();
				for (int i = 0; i < nodeList.getLength(); i++) {
					Node tmp = nodeList.item(i);

					if (tmp.getNodeName().equals("account")) {
						infoSub.setAccount(tmp.getTextContent().trim());
					}
					if (tmp.getNodeName().equals("subId")) {
						infoSub.setSubId(tmp.getTextContent().trim());
					}
					if (tmp.getNodeName().equals("serviceType")) {
						infoSub.setServiceType(tmp.getTextContent().trim());
					}
					if (tmp.getNodeName().equals("actStautsName")) {
						infoSub.setStatus(tmp.getTextContent().trim());
					}

					if (tmp.getNodeName().equals("hotlineForm")) {
						NodeList nodeHotlineForm = e4
								.getElementsByTagName("hotlineForm");
						HotlineForm hotlineForm = new HotlineForm();
						if (nodeHotlineForm != null
								&& nodeHotlineForm.getLength() >= 0) {
							for (int k = 0; k < nodeHotlineForm.getLength(); k++) {
								Node node3 = nodeHotlineForm.item(k);
								NodeList nodeTemp = node3.getChildNodes();
								for (int h = 0; h < nodeTemp.getLength(); h++) {
									Node node4 = nodeTemp.item(h);
									if (node4.getNodeName().equals("infraCode")) {
										if(CommonActivity.isNullOrEmpty(infoSub.getHotlineForm().getInfraCode())){
											hotlineForm.setInfraCode(node4
													.getTextContent().trim());
										}
									}
									if (node4.getNodeName().equals(
											"requestHotlineId")) {
										
										if(CommonActivity.isNullOrEmpty(infoSub.getHotlineForm().getRequestHotlineId())){
											hotlineForm.setRequestHotlineId(node4
													.getTextContent().trim());
										}
									}
									if(CommonActivity.isNullOrEmpty(infoSub.getHotlineForm().getRequestHotlineId())){
										infoSub.setHotlineForm(hotlineForm);
									}
								}
							}
						}
					}
					// check thong tin xem thue bao con
					if (tmp.getNodeName().equals("changeInfraReq")) {
						NodeList nodechangeInfraReq = e4
								.getElementsByTagName("changeInfraReq");
						ChangeInfraReq changeInfraReq = new ChangeInfraReq();
						if (nodechangeInfraReq != null
								&& nodechangeInfraReq.getLength() >= 0) {
							for (int l = 0; l < nodechangeInfraReq.getLength(); l++) {
								Node node3 = nodechangeInfraReq.item(l);
								NodeList nodeTemp = node3.getChildNodes();
								for (int h = 0; h < nodeTemp.getLength(); h++) {
									Node node4 = nodeTemp.item(h);
									if (node4.getNodeName().equals("infraType")) {
										if(CommonActivity.isNullOrEmpty(infoSub.getChangeInfraReq().getInfraType())){
											changeInfraReq.setInfraType(node4
													.getTextContent().trim());
										}
									}
									if (node4.getNodeName().equals(
											"oldTechnology")) {
										if(CommonActivity.isNullOrEmpty(infoSub.getChangeInfraReq().getOldTechnology())){
											changeInfraReq.setOldTechnology(node4
													.getTextContent().trim());
										}
							
									}
									if (node4.getNodeName()
											.equals("newAccount")) {
										if(CommonActivity.isNullOrEmpty(infoSub.getChangeInfraReq().getNewAccount())){
											changeInfraReq.setNewAccount(node4
													.getTextContent().trim());
										}
									}
									if(CommonActivity.isNullOrEmpty(infoSub.getChangeInfraReq().getInfraType())){
										infoSub.setChangeInfraReq(changeInfraReq);
									}
								}
							}
						}
					}

					if (tmp.getNodeName().equals("customer")) {
						NodeList nodechildCus1 = e4
								.getElementsByTagName("customer");
						for (int k = 0; k < nodechildCus1.getLength(); k++) {
							Element e1 = (Element) nodechildCus1.item(k);
							CustommerByIdNoBean custommerByIdNoBean = new CustommerByIdNoBean();
							String address = parse.getValue(e1, "address");
							Log.d("address", address);
							custommerByIdNoBean.setAddreseCus(address);
							String custId = parse.getValue(e1, "custId");
							Log.d("custId", custId);
							custommerByIdNoBean.setCustId(custId);
							String nameCus = parse.getValue(e1, "name");
							Log.d("nameCus", nameCus);
							custommerByIdNoBean.setNameCustomer(nameCus);
							String idNoCus = parse.getValue(e1, "idNo");
							Log.d("idNo", idNoCus);
							String tin = parse.getValue(e1, "tin");
							custommerByIdNoBean.setTin(tin);
							custommerByIdNoBean.setIdNo(idNoCus);
							String busPermitNo1 = parse.getValue(e1,
									"busPermitNo");
							Log.d("busPermitNo1", busPermitNo1);
							custommerByIdNoBean.setBusPermitNo(busPermitNo1);
							// if
							// (CommonActivity.isNullOrEmpty(idNoCus)) {
							// custommerByIdNoBean.setBusPermitNo(busPermitNo1);
							// }
							String birthDate = parse.getValue(e1, "birthDate");
							Log.d("birthDate", birthDate);
							custommerByIdNoBean.setBirthdayCus(StringUtils
									.convertDate(birthDate));

							String busType = parse.getValue(e1, "busType");
							Log.d("busType", busType);
							custommerByIdNoBean.setCusType(busType);

							String custGroupId = parse.getValue(e1,
									"custGroupId");
							Log.d("custGroupId", custGroupId);

							custommerByIdNoBean.setProvince(parse.getValue(e1,
									"province"));
							custommerByIdNoBean.setPrecint(parse.getValue(e1,
									"precinct"));
							custommerByIdNoBean.setDistrict(parse.getValue(e1,
									"district"));

							custommerByIdNoBean.setIdType(parse.getValue(e1,
									"idType"));
							custommerByIdNoBean.setIdIssueDate(StringUtils
									.convertDate(parse.getValue(e1,
											"idIssueDate")));
							custommerByIdNoBean.setIdIssuePlace(parse.getValue(
									e1, "idIssuePlace"));

							custommerByIdNoBean.setCusGroupId(custGroupId);
							NodeList nodeCusAttribute = e1
									.getElementsByTagName("customerAttribute");
							for (int l = 0; l < nodeCusAttribute.getLength(); l++) {
								Element e3 = (Element) nodeCusAttribute.item(l);
								String birthDateAtt = parse.getValue(e3,
										"birthDate");
								custommerByIdNoBean
										.getCustomerAttribute()
										.setBirthDate(
												StringUtils
														.convertDate(birthDateAtt));
								custommerByIdNoBean
										.getCustomerAttribute()
										.setCustId(parse.getValue(e3, "custId"));
								custommerByIdNoBean.getCustomerAttribute()
										.setId(parse.getValue(e3, "id"));
								custommerByIdNoBean
										.getCustomerAttribute()
										.setIdType(parse.getValue(e3, "idType"));
								custommerByIdNoBean.getCustomerAttribute()
										.setIdNo(parse.getValue(e3, "idNo"));
								custommerByIdNoBean.getCustomerAttribute()
										.setIssueDate(
												StringUtils.convertDate(parse
														.getValue(e3,
																"issueDate")));
								custommerByIdNoBean.getCustomerAttribute()
										.setIssuePlace(
												parse.getValue(e3,
														"isssuePlace"));
								custommerByIdNoBean.getCustomerAttribute()
										.setName(parse.getValue(e3, "name"));
							}
							if (custommerByIdNoBean != null) {
								if(CommonActivity.isNullOrEmpty(infoSub.getCustommerByIdNoBean().getCustId())){
									infoSub.setCustommerByIdNoBean(custommerByIdNoBean);
								}
							}
						}
					}

					// lay thong tin khach hang doi voi truong hop chi co
					// thue bao cha

					// lay ra danh sach account con can dau

					if (tmp.getNodeName().equals("lstInfoSubChild")) {
						NodeList nodelstInfoSubChild = e4
								.getElementsByTagName("lstInfoSubChild");
						ArrayList<InfoSubChild> arrInfoSubChilds = new ArrayList<InfoSubChild>();
						if (nodelstInfoSubChild != null
								&& nodelstInfoSubChild.getLength() >= 0) {
							for (int n = 0; n < nodelstInfoSubChild.getLength(); n++) {

								Node nodeInfoSubChild = nodelstInfoSubChild
										.item(n);

								Element e5 = (Element) nodeInfoSubChild;

								NodeList nodeLstChild = nodeInfoSubChild
										.getChildNodes();
								InfoSubChild infoSubChild = new InfoSubChild();
								for (int m = 0; m < nodeLstChild.getLength(); m++) {
									Node tmp1 = nodeLstChild.item(m);
									if (tmp1.getNodeName().equals("account")) {
										infoSubChild.setAccount(tmp1
												.getTextContent().trim());
									}
									if (tmp1.getNodeName().equals("subId")) {
										infoSubChild.setSubId(tmp1
												.getTextContent().trim());
									}
									if (tmp1.getNodeName()
											.equals("serviceType")) {
										infoSubChild.setServiceType(tmp1
												.getTextContent().trim());
									}
									if (tmp1.getNodeName().equals(
											"actStautsName")) {
										infoSubChild.setStatus(tmp1
												.getTextContent().trim());
									}

									if (tmp1.getNodeName()
											.equals("hotlineForm")) {
										NodeList nodeHotlineForm = e5
												.getElementsByTagName("hotlineForm");
										HotlineForm hotlineForm = new HotlineForm();
										if (nodeHotlineForm != null
												&& nodeHotlineForm.getLength() >= 0) {
											for (int k = 0; k < nodeHotlineForm
													.getLength(); k++) {
												Node node3 = nodeHotlineForm
														.item(k);
												NodeList nodeTemp = node3
														.getChildNodes();
												for (int h = 0; h < nodeTemp
														.getLength(); h++) {
													Node node4 = nodeTemp
															.item(h);
													if (node4.getNodeName().equals("infraCode")) {
														if(CommonActivity.isNullOrEmpty(infoSubChild.getHotlineForm().getInfraCode())){
															hotlineForm.setInfraCode(node4
																	.getTextContent().trim());
														}
													}
													if (node4.getNodeName().equals(
															"requestHotlineId")) {
														
														if(CommonActivity.isNullOrEmpty(infoSubChild.getHotlineForm().getRequestHotlineId())){
															hotlineForm.setRequestHotlineId(node4
																	.getTextContent().trim());
														}
													}
													
													if(CommonActivity.isNullOrEmpty(infoSubChild.getHotlineForm().getRequestHotlineId())){
														infoSubChild
														.setHotlineForm(hotlineForm);
													}

												}
											}
										}
									}
									// check thong tin xem thue bao con
									if (tmp1.getNodeName().equals(
											"changeInfraReq")) {
										NodeList nodechangeInfraReq = e5
												.getElementsByTagName("changeInfraReq");
										ChangeInfraReq changeInfraReq = new ChangeInfraReq();
										if (nodechangeInfraReq != null
												&& nodechangeInfraReq
														.getLength() >= 0) {
											for (int l = 0; l < nodechangeInfraReq
													.getLength(); l++) {
												Node node3 = nodechangeInfraReq
														.item(l);
												NodeList nodeTemp = node3
														.getChildNodes();
												for (int h = 0; h < nodeTemp
														.getLength(); h++) {
													Node node4 = nodeTemp
															.item(h);
													if (node4.getNodeName().equals("infraType")) {
														if(CommonActivity.isNullOrEmpty(infoSubChild.getChangeInfraReq().getInfraType())){
															changeInfraReq.setInfraType(node4
																	.getTextContent().trim());
														}
													}
													if (node4.getNodeName().equals(
															"oldTechnology")) {
														if(CommonActivity.isNullOrEmpty(infoSubChild.getChangeInfraReq().getOldTechnology())){
															changeInfraReq.setOldTechnology(node4
																	.getTextContent().trim());
														}
											
													}
													if (node4.getNodeName()
															.equals("newAccount")) {
														if(CommonActivity.isNullOrEmpty(infoSubChild.getChangeInfraReq().getNewAccount())){
															changeInfraReq.setNewAccount(node4
																	.getTextContent().trim());
														}
													}
													
													if(CommonActivity.isNullOrEmpty(infoSubChild.getChangeInfraReq().getInfraType())){
														
														infoSubChild
																.setChangeInfraReq(changeInfraReq);
													}

												}
											}
										}
									}

									if (tmp1.getNodeName().equals("customer")) {
										NodeList nodechildCus = e5
												.getElementsByTagName("customer");

										if (nodechildCus != null
												&& nodechildCus.getLength() >= 0) {
											for (int k = 0; k < nodechildCus
													.getLength(); k++) {
												Element e1 = (Element) nodechildCus
														.item(k);
												CustommerByIdNoBean custommerByIdNoBean = new CustommerByIdNoBean();
												String address = parse
														.getValue(e1, "address");
												Log.d("address", address);
												custommerByIdNoBean
														.setAddreseCus(address);
												String custId = parse.getValue(
														e1, "custId");
												Log.d("custId", custId);
												custommerByIdNoBean
														.setCustId(custId);
												String nameCus = parse
														.getValue(e1, "name");
												Log.d("nameCus", nameCus);
												custommerByIdNoBean
														.setNameCustomer(nameCus);
												String idNoCus = parse
														.getValue(e1, "idNo");
												Log.d("idNo", idNoCus);
												String tin = parse.getValue(e1,
														"tin");
												custommerByIdNoBean.setTin(tin);
												custommerByIdNoBean
														.setIdNo(idNoCus);
												String busPermitNo1 = parse
														.getValue(e1,
																"busPermitNo");
												Log.d("busPermitNo1",
														busPermitNo1);
												custommerByIdNoBean
														.setBusPermitNo(busPermitNo1);
												String birthDate = parse
														.getValue(e1,
																"birthDate");
												Log.d("birthDate", birthDate);
												custommerByIdNoBean
														.setBirthdayCus(StringUtils
																.convertDate(birthDate));

												String busType = parse
														.getValue(e1, "busType");
												Log.d("busType", busType);
												custommerByIdNoBean
														.setCusType(busType);

												String custGroupId = parse
														.getValue(e1,
																"custGroupId");
												Log.d("custGroupId",
														custGroupId);

												custommerByIdNoBean
														.setProvince(parse
																.getValue(e1,
																		"province"));
												custommerByIdNoBean
														.setPrecint(parse
																.getValue(e1,
																		"precinct"));
												custommerByIdNoBean
														.setDistrict(parse
																.getValue(e1,
																		"district"));

												custommerByIdNoBean
														.setIdType(parse
																.getValue(e1,
																		"idType"));
												custommerByIdNoBean
														.setIdIssueDate(StringUtils
																.convertDate(parse
																		.getValue(
																				e1,
																				"idIssueDate")));
												custommerByIdNoBean
														.setIdIssuePlace(parse
																.getValue(e1,
																		"idIssuePlace"));

												custommerByIdNoBean
														.setCusGroupId(custGroupId);
												NodeList nodeCusAttribute = e1
														.getElementsByTagName("customerAttribute");
												for (int l = 0; l < nodeCusAttribute
														.getLength(); l++) {
													Element e3 = (Element) nodeCusAttribute
															.item(l);
													String birthDateAtt = parse
															.getValue(e3,
																	"birthDate");
													custommerByIdNoBean
															.getCustomerAttribute()
															.setBirthDate(
																	StringUtils
																			.convertDate(birthDateAtt));
													custommerByIdNoBean
															.getCustomerAttribute()
															.setCustId(
																	parse.getValue(
																			e3,
																			"custId"));
													custommerByIdNoBean
															.getCustomerAttribute()
															.setId(parse
																	.getValue(
																			e3,
																			"id"));
													custommerByIdNoBean
															.getCustomerAttribute()
															.setIdType(
																	parse.getValue(
																			e3,
																			"idType"));
													custommerByIdNoBean
															.getCustomerAttribute()
															.setIdNo(
																	parse.getValue(
																			e3,
																			"idNo"));
													custommerByIdNoBean
															.getCustomerAttribute()
															.setIssueDate(
																	StringUtils
																			.convertDate(parse
																					.getValue(
																							e3,
																							"issueDate")));
													custommerByIdNoBean
															.getCustomerAttribute()
															.setIssuePlace(
																	parse.getValue(
																			e3,
																			"isssuePlace"));
													custommerByIdNoBean
															.getCustomerAttribute()
															.setName(
																	parse.getValue(
																			e3,
																			"name"));
												}
												if (custommerByIdNoBean != null) {
													
													if(CommonActivity.isNullOrEmpty(infoSubChild.getCustommerByIdNoBean().getCustId())){
														infoSubChild
														.setCustommerByIdNoBean(custommerByIdNoBean);
													}
												}
											}
										}
									}
								}
								arrInfoSubChilds.add(infoSubChild);
							}
							infoSub.setLstInfoSubChilds(arrInfoSubChilds);
						}
					}
				}
				arrInfoSubs.add(infoSub);
				// }
			} catch (Exception e) {
				e.printStackTrace();
			}
			return arrInfoSubs;
		}
	}

	// move login
	OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
			getActivity().finish();
			MainActivity.getInstance().finish();

		}
	};

	private boolean validateSearch() {

		if (CommonActivity.isNullOrEmpty(serviceType)) {

			Toast.makeText(getActivity(),
					getActivity().getString(R.string.checkserviceType),
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (CommonActivity.isNullOrEmpty(edt_account.getText().toString()
				.trim())) {

			Toast.makeText(getActivity(),
					getActivity().getString(R.string.accountEmpty),
					Toast.LENGTH_SHORT).show();
			return false;
		}

		if (StringUtils.CheckCharSpecical(edt_account.getText().toString()
				.trim())) {
			Toast.makeText(getActivity(),
					getActivity().getString(R.string.checkaccountspecial),
					Toast.LENGTH_SHORT).show();
			return false;
		}

		return true;
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.relaBackHome:
			getActivity().onBackPressed();
			break;
		case R.id.btn_search:
			if (validateSearch()) {
				ischeckAll = false;

				if (arrInfoSubsTmp != null && !arrInfoSubsTmp.isEmpty()) {
					arrInfoSubsTmp = new ArrayList<InfoSub>();
				}
				if (getListInfoSubAdapter != null) {
					getListInfoSubAdapter.notifyDataSetChanged();
				}

				AsynGetInfoSubscriberByAccountAndServiceType asynGetInfoSubscriberByAccountAndServiceType = new AsynGetInfoSubscriberByAccountAndServiceType(
						getActivity());
				asynGetInfoSubscriberByAccountAndServiceType.execute(
						edt_account.getText().toString().trim(), serviceType,
						"1");
			}
			break;
		default:
			break;
		}

	}

	private boolean ischeckAll = false;

	@Override
	public void onCheckChangeInfoSub(InfoSub infoSub) {
		if (dialogChild != null) {
			dialogChild = null;
		}
		ischeckAll = true;
		if (infoSub.getLstInfoSubChilds() != null
				&& !infoSub.getLstInfoSubChilds().isEmpty()) {
			if (arrInfoSubsTmp != null && !arrInfoSubsTmp.isEmpty()) {
				for (InfoSub item : arrInfoSubsTmp) {
					if (item.getAccount().equals(infoSub.getAccount())) {
						item.setCheck(!item.isCheck());
					}
				}
			}
			if (getListInfoSubAdapter != null) {
				getListInfoSubAdapter.notifyDataSetChanged();
			}
			showDialogShowSelectSubChild(infoSub,
					infoSub.getLstInfoSubChilds(), true);
		}
	}

	private Dialog dialogChild = null;

	private ArrayList<InfoSubChild> lstInfoSubChilds = null;

	private void showDialogShowSelectSubChild(final InfoSub infoSub,
			final ArrayList<InfoSubChild> infoSubChilds, final boolean ischeck) {
		lstInfoSubChilds = new ArrayList<InfoSubChild>();
		lstInfoSubChilds.addAll(infoSubChilds);

		dialogChild = new Dialog(getActivity());

		InfoSubChild item = new InfoSubChild();
		item.setSubId(infoSub.getSubId());
		item.setAccount(infoSub.getAccount());
		item.setServiceType(infoSub.getServiceType());
		item.setCustommerByIdNoBean(infoSub.getCustommerByIdNoBean());
		item.setStatus(infoSub.getStatus());
		item.setChangeInfraReq(infoSub.getChangeInfraReq());
		item.setHotlineForm(infoSub.getHotlineForm());
		lstInfoSubChilds.add(0, item);

		if (ischeck) {
			for (InfoSubChild infoSubChild : lstInfoSubChilds) {
				infoSubChild.setCheck(ischeck);
				infoSubChild.setCheckAll(true);
			}
		}

		dialogChild.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogChild.setContentView(R.layout.create_request_changetech_dialog);

		ListView lvsubchild = (ListView) dialogChild
				.findViewById(R.id.lvsubchild);
		getListInfoSubChildAdapter = new GetListInfoSubChildAdapter(
				lstInfoSubChilds, getActivity(),
				FragmentManagerChangeTech.this, FragmentManagerChangeTech.this);
		lvsubchild.setAdapter(getListInfoSubChildAdapter);
		Button btnOk = (Button) dialogChild.findViewById(R.id.btnOk);

		if (!ischeck) {
			btnOk.setVisibility(View.GONE);
		} else {
			btnOk.setVisibility(View.VISIBLE);
		}

		lvsubchild.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				final InfoSubChild item = lstInfoSubChilds.get(arg2);

				if (item.getChangeInfraReq() != null
						&& !CommonActivity.isNullOrEmpty(item
								.getChangeInfraReq().getNewAccount())) {
					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.confirmChangetech),
							getString(R.string.app_name));
				} else {

					if (ischeck) {

					} else {
						if (item.getChangeInfraReq() != null
								&& !CommonActivity.isNullOrEmpty(item
										.getChangeInfraReq().getInfraType())) {
							Bundle bundle = new Bundle();
							Intent intent = new Intent(getActivity(),
									ActivityCreateNewRequestHotLine.class);
							bundle.putSerializable("InfoSubKey", item);
							String nameTech = "";
							try {
								GetTechologyDal dal = new GetTechologyDal(
										getActivity());
								dal.open();

								nameTech = dal.getNameTechologyCD(item
										.getChangeInfraReq().getInfraType());
							} catch (Exception e) {
								e.printStackTrace();
							}

							bundle.putString("technologyKey", item
									.getChangeInfraReq().getInfraType());
							bundle.putString("technologyNameKey", nameTech);
							intent.putExtras(bundle);
							startActivity(intent);

						} else {

							final InfoSub tmp = new InfoSub();
							ArrayList<InfoSubChild> arr = new ArrayList<InfoSubChild>();
							if (infoSub.getAccount().equals(item.getAccount())) {
								if (infoSub.getLstInfoSubChilds() != null
										&& !infoSub.getLstInfoSubChilds()
												.isEmpty()) {

									for (InfoSubChild infoSubChild : infoSubChilds) {
										if (infoSubChild.getChangeInfraReq() != null
												&& !CommonActivity
														.isNullOrEmpty(infoSubChild
																.getChangeInfraReq()
																.getInfraType())) {

										} else {
											arr.add(infoSubChild);
										}
									}
								}
							}
							arr.add(item);
							tmp.setLstInfoSubChilds(arr);

							// Bundle mBundle = new Bundle();
							// mBundle.putSerializable("INFOSUBKEY", tmp);
							// mBundle.putString("serviceTypeKey",
							// item.getServiceType());
							// mBundle.putString("accountKey",
							// item.getAccount());
							// FragmentCreateRequestChangeTech mListMenuManager
							// = new FragmentCreateRequestChangeTech();
							// mListMenuManager.setArguments(mBundle);
							// ReplaceFragment.replaceFragment(getActivity(),
							// mListMenuManager, false);

							OnClickListener onClickListener = new OnClickListener() {

								@Override
								public void onClick(View arg0) {
									Bundle mBundle = new Bundle();
									mBundle.putSerializable("INFOSUBKEY", tmp);
									mBundle.putString("serviceTypeKey",
											item.getServiceType());
									mBundle.putString("accountKey",
											item.getAccount());
									FragmentCreateRequestChangeTech mListMenuManager = new FragmentCreateRequestChangeTech();
									mListMenuManager.setArguments(mBundle);
									ReplaceFragment.replaceFragment(
											getActivity(), mListMenuManager,
											false);
								}
							};

							if (item.getAccount().equals(infoSub.getAccount())) {
								CommonActivity.createDialog(
										getActivity(),
										getActivity().getString(
												R.string.confirmtransteach),
										getActivity().getString(
												R.string.app_name),
										getActivity().getString(R.string.ok),
										getActivity()
												.getString(R.string.cancel),
										onClickListener, null).show();
							} else {
								Bundle mBundle = new Bundle();
								mBundle.putSerializable("INFOSUBKEY", tmp);
								mBundle.putString("serviceTypeKey",
										item.getServiceType());
								mBundle.putString("accountKey",
										item.getAccount());
								FragmentCreateRequestChangeTech mListMenuManager = new FragmentCreateRequestChangeTech();
								mListMenuManager.setArguments(mBundle);
								ReplaceFragment.replaceFragment(getActivity(),
										mListMenuManager, false);
							}
						}
						dialogChild.dismiss();
					}
				}

			}
		});

		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (infoSub.getChangeInfraReq() != null
						&& !CommonActivity.isNullOrEmpty(infoSub
								.getChangeInfraReq().getNewAccount())) {
					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.confirmChangetech),
							getString(R.string.app_name));
				} else {
					ArrayList<InfoSubChild> arrInfoSubChilds = new ArrayList<InfoSubChild>();
					final InfoSub tmp = new InfoSub();

					for (InfoSubChild infoSubChild : lstInfoSubChilds) {

						if (infoSubChild.getChangeInfraReq() != null
								&& !CommonActivity.isNullOrEmpty(infoSubChild
										.getChangeInfraReq().getInfraType())) {

						} else {
							if (infoSubChild.isCheck()
									&& CommonActivity
											.isNullOrEmpty(infoSubChild
													.getChangeInfraReq()
													.getAccount())) {
								arrInfoSubChilds.add(infoSubChild);
							}
						}

					}
					tmp.setLstInfoSubChilds(arrInfoSubChilds);
					// if (arrInfoSubChilds.size() == lstInfoSubChilds.size()
					// || ischeckAll) {
					// InfoSubChild item = new InfoSubChild();
					// item.setAccount(infoSub.getAccount());
					// item.setServiceType(infoSub.getServiceType());
					// item.setCustommerByIdNoBean(infoSub
					// .getCustommerByIdNoBean());
					// arrInfoSubChilds.add(item);
					// }
					OnClickListener onClickListener = new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							Bundle mBundle = new Bundle();
							mBundle.putSerializable("INFOSUBKEY", tmp);
							mBundle.putString("serviceTypeKey",
									infoSub.getServiceType());
							mBundle.putString("accountKey",
									infoSub.getAccount());
							FragmentCreateRequestChangeTech mListMenuManager = new FragmentCreateRequestChangeTech();
							mListMenuManager.setArguments(mBundle);
							// mListMenuManager.setTargetFragment(this, 100);
							ReplaceFragment.replaceFragment(getActivity(),
									mListMenuManager, false);
						}
					};

					CommonActivity
							.createDialog(
									getActivity(),
									getActivity().getString(
											R.string.confirmtransteach),
									getActivity().getString(R.string.app_name),
									getActivity().getString(R.string.ok),
									getActivity().getString(R.string.cancel),
									onClickListener, null).show();

				}

				dialogChild.dismiss();

			}
		});
		Button btnCancel = (Button) dialogChild.findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				ischeckAll = false;

				if (lstInfoSubChilds != null && !lstInfoSubChilds.isEmpty()) {
					// for (InfoSubChild infoSubChild : lstInfoSubChilds) {
					// infoSubChild.setCheck(false);
					// infoSubChild.setCheckAll(true);
					// }
					lstInfoSubChilds = new ArrayList<InfoSubChild>();
				}
				if (getListInfoSubChildAdapter != null) {
					getListInfoSubChildAdapter.notifyDataSetChanged();
				}

				if (ischeck) {
					if (arrInfoSubsTmp != null && !arrInfoSubsTmp.isEmpty()) {
						for (InfoSub item : arrInfoSubsTmp) {
							item.setCheck(false);
						}
					}
					if (getListInfoSubAdapter != null) {
						getListInfoSubAdapter.notifyDataSetChanged();
					}
				}

				dialogChild.dismiss();

			}
		});
		dialogChild.show();
	}

	@Override
	public void onCheckChangeInfoSubChild(InfoSubChild infoSub) {

		for (InfoSubChild item : lstInfoSubChilds) {
			if (infoSub.getAccount().equals(item.getAccount())) {
				item.setCheck(!item.isCheck());
				break;
			}
		}
		if (getListInfoSubChildAdapter != null) {
			getListInfoSubChildAdapter.notifyDataSetChanged();
		}

	}

	OnClickListener removeClick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			if (check) {
				if (infoSubItem != null
						&& !CommonActivity.isNullOrEmpty(infoSubItem
								.getAccount())) {
					if (arrInfoSubsTmp != null && !arrInfoSubsTmp.isEmpty()) {

						arrInfoSubsTmp = new ArrayList<InfoSub>();

						getListInfoSubAdapter = new GetListInfoSubAdapter(
								arrInfoSubsTmp, getActivity(),
								FragmentManagerChangeTech.this,
								FragmentManagerChangeTech.this);
						lvrequest.setAdapter(getListInfoSubAdapter);
						getListInfoSubAdapter.notifyDataSetChanged();

						// if (infoSubItem != null) {
						// for (InfoSub item : arrInfoSubsTmp) {
						// if (item.getAccount().equals(
						// infoSubItem.getAccount())) {
						// arrInfoSubsTmp.remove(item);
						// break;
						// }
						// }
						// if (getListInfoSubAdapter != null) {
						// getListInfoSubAdapter.notifyDataSetChanged();
						// }
						// }
					}
				}
			} else {
				if (infoSubChild != null
						&& !CommonActivity.isNullOrEmpty(infoSubChild
								.getAccount())) {
					if (lstInfoSubChilds != null && !lstInfoSubChilds.isEmpty()) {
						if (infoSubChild != null) {
							for (InfoSubChild item : lstInfoSubChilds) {
								if (item.getAccount().equals(
										infoSubChild.getAccount())) {
									item.setChangeInfraReq(null);
									break;
								}
							}
							if (getListInfoSubChildAdapter != null) {
								getListInfoSubChildAdapter
										.notifyDataSetChanged();
							}
						}
					}
				}
			}

		}
	};

	// ham chuyen doi chuong trinh khuyen mai
	private class AsynDeleteRequestChangeInfra extends
			AsyncTask<String, Void, String> {

		ProgressDialog progress;
		private Context context = null;
		private int type;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public AsynDeleteRequestChangeInfra(Context mContext) {
			this.context = mContext;
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
		protected String doInBackground(String... arg0) {
			return delRequestChangeInfra(arg0[0], arg0[1]);
		}

		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();
			if ("0".equals(errorCode)) {
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.cancelrequestsuccess),
						getString(R.string.app_name), removeClick).show();

			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
									getActivity(),
									description,
									context.getResources().getString(
											R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}

					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		private String delRequestChangeInfra(String account, String serviceType) {
			String original = "";

			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_deleteChangeInfraReq");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:deleteChangeInfraReq>");
				rawData.append("<input>");
				rawData.append("<token>" + Session.getToken() + "</token>");
				// rawData.append("<account>" + account + "</account>");
				// rawData.append("<serviceType>" + serviceType +
				// "</serviceType>");
				// rawData.append("<infraType>" + infraType + "</infraType>");
				// rawData.append("<reasonId>" + reasonId + "</reasonId>");

				// if (arrInfoSubChilds != null && !arrInfoSubChilds.isEmpty())
				// {
				// for (InfoSubChild infoSubChild : arrInfoSubChilds) {
				rawData.append("<lsChangeInfraReq>");
				rawData.append("<account>" + account + "</account>");
				rawData.append("<serviceType>" + serviceType + "</serviceType>");
				rawData.append("</lsChangeInfraReq>");
				// }
				// }
				rawData.append("</input>");
				rawData.append("</ws:deleteChangeInfraReq>");

				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_deleteChangeInfraReq");
				Log.i("LOG", "Respone:  " + response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("LOG", "Responseeeeeeeeee Original  " + original);

				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return errorCode;

		}

	}

	@Override
	public void onCancelInfoSub(final InfoSub infoSub) {

		check = true;
		OnClickListener onClickListener = new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				AsynDeleteRequestChangeInfra asynDeleteRequestChangeInfra = new AsynDeleteRequestChangeInfra(
						getActivity());
				asynDeleteRequestChangeInfra.execute(infoSub.getAccount(),
						infoSub.getServiceType());
			}
		};
		CommonActivity.createDialog(getActivity(),
				getString(R.string.checkconfirmhuy),
				getString(R.string.app_name), getString(R.string.ok),
				getString(R.string.cancel), onClickListener, null).show();

	}

	private InfoSubChild infoSubChild = null;

	@Override
	public void onCancelChangeInfoSubChild(final InfoSubChild infoSub) {

		infoSubChild = infoSub;
		OnClickListener removeRequestChild = new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				AsynDeleteRequestChangeInfra asynDeleteRequestChangeInfra = new AsynDeleteRequestChangeInfra(
						getActivity());
				asynDeleteRequestChangeInfra.execute(infoSubChild.getAccount(),
						infoSubChild.getServiceType());
			}
		};

		CommonActivity.createDialog(getActivity(),
				getString(R.string.checkconfirmhuy),
				getString(R.string.app_name), getString(R.string.ok),
				getString(R.string.cancel), removeRequestChild, null).show();

	}

}
