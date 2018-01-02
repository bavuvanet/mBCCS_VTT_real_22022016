package com.viettel.bss.viettelpos.v4.sale.activity;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.sale.adapter.GetListOrderAdapter;
import com.viettel.bss.viettelpos.v4.sale.business.CacheData;
import com.viettel.bss.viettelpos.v4.sale.object.GetOrderObject;
import com.viettel.bss.viettelpos.v4.sale.object.GetOrderObjectDetail;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentListOrder extends Fragment implements OnClickListener,
		OnItemClickListener {
	private Button btnHome;
	private ListView lvtorder;
	public GetListOrderAdapter adapListOrderAdapter;
	public ArrayList<GetOrderObject> arraGetOrderObjects = new ArrayList<>();
	private int positionlv = -1;
	public static FragmentListOrder instance = null;
	TextView txtNameActionBar;
	private final String permission = Constant.VSAMenu.MENU_CHANNEL_APPROVEORDER;

	@Override
	public void onResume() {
        MainActivity.getInstance().setTitleActionBar(R.string.listorder);
		super.onResume();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View mView = inflater.inflate(R.layout.listorder_layout, container,
				false);
		unit(mView);

		return mView;
	}

	private void unit(View view) {
		instance = this;
		lvtorder = (ListView) view.findViewById(R.id.listorde);
		// =======run asyn get list order =============
		if (arraGetOrderObjects != null && arraGetOrderObjects.size() > 0) {
			arraGetOrderObjects.clear();
		}
		if (CommonActivity.isNetworkConnected(getActivity())) {
			GetlistOrderAsyn getlistOrderAsyn = new GetlistOrderAsyn(
					getActivity());
			getlistOrderAsyn.execute();
		} else {
			Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
					getResources().getString(R.string.errorNetwork),
					getResources().getString(R.string.app_name));
			dialog.show();
		}
		// =======click item listview ============
		lvtorder.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int positon,
			long arg3) {

		if (arraGetOrderObjects.get(positon).getWarning() != null
				&& !arraGetOrderObjects.get(positon).getWarning().isEmpty()) {
			Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
					getResources().getString(R.string.tick_nop_tien),
					getResources().getString(R.string.app_name));
			dialog.show();
			return;
		}
		positionlv = positon;
		CacheData.getInstanse()
				.setOrderObject(arraGetOrderObjects.get(positon));
		// =============Run asyn getdetail object===================
		if (CommonActivity.isNetworkConnected(getActivity())) {
			GetDetailOrderAsyn getDetailOrderAsyn = new GetDetailOrderAsyn(
					getActivity());
			getDetailOrderAsyn.execute();
		} else {
			Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
					getResources().getString(R.string.errorNetwork),
					getResources().getString(R.string.app_name));
			dialog.show();
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnHome:
			CommonActivity.callphone(getActivity(), Constant.phoneNumber);
			break;
		case R.id.relaBackHome:
			getActivity().onBackPressed();
			break;
		default:
			break;
		}
	}

	// move login
	OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
			getActivity().finish();

		}
	};

	// ===============webservice get detail order==============
	public class GetDetailOrderAsyn extends AsyncTask<Void, Void, String> {
		final ProgressDialog progress;
		private Activity context = null;
		String errorCode = "";
		String description = "";

		public GetDetailOrderAsyn(Activity context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setMessage(context.getResources().getString(
					R.string.waitting));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected String doInBackground(Void... params) {
			return getDetailOrder(positionlv);
		}

		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();
			if (result != null) {
				if (result.equalsIgnoreCase("0")
						&& CacheData.getInstanse()
								.getLisGetOrderObjectDetails().size() > 0) {
					// ============co du lieu --- chuyen sang fragment detail
					FragmentResolveOrder fragmentResolveOrder = new FragmentResolveOrder();
					ReplaceFragment.replaceFragment(getActivity(),
							fragmentResolveOrder, false);
				} else {
					if (errorCode.equals(Constant.INVALID_TOKEN2)
							&& description != null && !description.isEmpty()) {
						// Dialog dialog = CommonActivity.createAlertDialog(
						// getActivity(), description, getResources()
						// .getString(R.string.app_name),
						// moveLogInAct);
						// dialog.show();
						LoginDialog dialog = new LoginDialog(context,
								permission);
						dialog.show();
					} else {
						Dialog dialog = CommonActivity.createAlertDialog(
								getActivity(), description, getResources()
										.getString(R.string.duyetdonhang));
						dialog.show();
					}
				}
			}
		}

		private String getDetailOrder(int positon) {
			String original = "";
			XmlDomParse parse = new XmlDomParse();
			ArrayList<GetOrderObjectDetail> lisGetOrderObjectDetails = new ArrayList<>();
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getListStockOrderDetailBccs2");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListStockOrderDetail>");
				rawData.append("<input>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("<stockOrderId>").append(arraGetOrderObjects.get(positon).getStockOrderId());
				rawData.append("</stockOrderId>");
				rawData.append("</input>");
				rawData.append("</ws:getListStockOrderDetail>");

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,getActivity(),"mbccs_getListStockOrderDetailBccs2");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.d("originalllllllll", original);
				// ===========parser xml ===================
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("erorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					if (errorCode.equalsIgnoreCase("0")) {
						original = errorCode;
					} else {
						original = description;
					}
					nodechild = doc.getElementsByTagName("lstStockOrderDetailDTO");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						GetOrderObjectDetail getOrderObjectDetail = new GetOrderObjectDetail();
						String quantityOrder = parse.getValue(e1,
								"quantityOrder");
						Log.d("quantityOrder", quantityOrder);
						getOrderObjectDetail.setQuantityOrder(quantityOrder);
						String stockModelId = parse
								.getValue(e1, "prodOfferId");
						Log.d("stockModelId", stockModelId);
						getOrderObjectDetail.setStockModelId(stockModelId);
						String stockOrderDetailId = parse.getValue(e1,
								"stockOrderDetailId");
						Log.d("stockOrderDetailId", stockOrderDetailId);
						getOrderObjectDetail
								.setStockOrderDetailId(stockOrderDetailId);
						String stockOrderId = parse
								.getValue(e1, "stockOrderId");
						Log.d("stockOrderId", stockOrderId);
						getOrderObjectDetail.setStockOrderId(stockOrderId);
						lisGetOrderObjectDetails.add(getOrderObjectDetail);
						CacheData.getInstanse().setLisGetOrderObjectDetails(
								lisGetOrderObjectDetails);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return original;
		}
	}

	// =================webservice get list order
	public class GetlistOrderAsyn extends
			AsyncTask<Void, Void, ArrayList<GetOrderObject>> {
		private final Activity context;
		private String original;
		final XmlDomParse parse = new XmlDomParse();
		String description = "";
		String errorCode = "";
		final ProgressDialog progress;

		public GetlistOrderAsyn(Activity context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			// check font

			this.progress.setMessage(context.getResources().getString(
					R.string.waitting));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<GetOrderObject> doInBackground(Void... params) {
			return getlistorder();
		}

		@Override
		protected void onPostExecute(ArrayList<GetOrderObject> result) {
			progress.dismiss();
			if (result != null && result.size() > 0) {
				arraGetOrderObjects = result;
				// =====co du lieu =========set adapter for listview

				adapListOrderAdapter = new GetListOrderAdapter(
						arraGetOrderObjects, context);
				lvtorder.setAdapter(adapListOrderAdapter);
				adapListOrderAdapter.notifyDataSetChanged();
			} else {
				// ========ko co du lieu ===========show popup thong bao ko co
				// du lieu
				if (errorCode.equals(Constant.INVALID_TOKEN2)
						&& description != null && !description.isEmpty()) {
					// Dialog dialog = CommonActivity.createAlertDialog(
					// getActivity(), description, getResources()
					// .getString(R.string.searchorder),
					// moveLogInAct);
					// dialog.show();
					LoginDialog dialog = new LoginDialog(context, permission);
					dialog.show();
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.notsearchorder),
							getResources().getString(R.string.searchorder));
					dialog.show();
				}

			}
		}

		// ===========method get list order object=================
		public ArrayList<GetOrderObject> getlistorder() {

			ArrayList<GetOrderObject> lisGetOrderObjects = new ArrayList<>();
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListStockOrderBccs2");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListStockOrder>");
				rawData.append("<input>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("</input>");
				rawData.append("</ws:getListStockOrder>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,getActivity(),"mbccs_getListStockOrderBccs2");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();

				// ========parse xml ==================

				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					nodechild = doc.getElementsByTagName("lstStockOrderDTO");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						GetOrderObject getOrderObject = new GetOrderObject();
						String approveStaffId = parse.getValue(e1,
								"approveStaffId");
						Log.d("approveStaffId", approveStaffId);
						getOrderObject.setApproveStaffId(approveStaffId);

						String createDate = parse.getValue(e1, "createDate");
						Log.d("createDate", createDate);
						getOrderObject.setCreateDate(createDate);

						String refuseStaffId = parse.getValue(e1,
								"refuseStaffId");
						Log.d("refuseStaffId", refuseStaffId);
						getOrderObject.setRefuseStaffId(refuseStaffId);

						String shopId = parse.getValue(e1, "shopId");
						Log.d("shopId", shopId);
						getOrderObject.setShopId(shopId);

						String staffId = parse.getValue(e1, "staffId");
						Log.d("staffId", staffId);
						getOrderObject.setStaffId(staffId);

						String status = parse.getValue(e1, "status");
						Log.d("status", status);
						getOrderObject.setStatus(status);

						String stockOrderCode = parse.getValue(e1,
								"stockOrderCode");
						Log.d("stockOrderCode", stockOrderCode);
						getOrderObject.setStockOrderCode(stockOrderCode);

						String stockOrderId = parse
								.getValue(e1, "stockOrderId");
						Log.d("stockOrderId", stockOrderId);
						getOrderObject.setStockOrderId(stockOrderId);

						String staffName = parse.getValue(e1, "staffName");
						Log.d("staffName", staffName);
						getOrderObject.setStaffName(staffName);

						String warning = parse.getValue(e1, "warning");
						getOrderObject.setWarning(warning);
						lisGetOrderObjects.add(getOrderObject);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return lisGetOrderObjects;
		}
	}
}
