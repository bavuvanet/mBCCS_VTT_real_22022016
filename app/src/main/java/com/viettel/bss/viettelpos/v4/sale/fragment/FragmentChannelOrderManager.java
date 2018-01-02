package com.viettel.bss.viettelpos.v4.sale.fragment;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentChooseChannel;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentSaleSaling;
import com.viettel.bss.viettelpos.v4.sale.adapter.ChannelOrderAdapter;
import com.viettel.bss.viettelpos.v4.sale.adapter.SaleOrderChannelDetailAdapter;
import com.viettel.bss.viettelpos.v4.sale.business.StaffBusiness;
import com.viettel.bss.viettelpos.v4.sale.object.IMOutput;
import com.viettel.bss.viettelpos.v4.sale.object.SaleOrderChannel;
import com.viettel.bss.viettelpos.v4.sale.object.SaleOrderChannelDetail;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentChannelOrderManager extends FragmentCommon implements
		OnClickListener {
	private static final int REQUEST_HANDEL_ORDER = 1;
	private static final int REQUEST_CHOOSE_CHANNEL = 2;
	private Activity activity;
	private ListView lvOrder;
	private List<SaleOrderChannel> listChannelOrder;
	private ChannelOrderAdapter adapter;
	private final Map<Long, ArrayList<SaleOrderChannelDetail>> mapOrderDetail = new HashMap<>();
    private Dialog dialog;
	private SaleOrderChannel order;
	private TextView tvChooseChannel;
	private Long staffId;

	@Override
	public void onResume() {
		super.onResume();
		setTitleActionBar(R.string.xy_ly_don_hang_diem_ban);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		idLayout = R.layout.layout_channel_order_manager;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();

	}

	@Override
	public void unit(View v) {
		v.findViewById(R.id.lnButton).setVisibility(View.GONE);
		v.findViewById(R.id.lnHeader).setVisibility(View.GONE);
		tvChooseChannel = (TextView) v.findViewById(R.id.tvChooseChannel);
		tvChooseChannel.setOnClickListener(this);
		v.findViewById(R.id.imgSearch).setOnClickListener(this);
		lvOrder = (ListView) v.findViewById(R.id.lvOrder);
		lvOrder.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				order = listChannelOrder.get(arg2);
				GetChannelOrderDetailAsy asy = new GetChannelOrderDetailAsy(
						activity);
				asy.execute();
			}
		});
		// GetListSaleOrderAsy as = new GetListSaleOrderAsy(activity);
		// as.execute();
	}

	private class GetListSaleOrderAsy extends AsyncTask<Void, Void, Void> {
		ProgressDialog progress;
		String description = "";
		String errorCode = "";

		public GetListSaleOrderAsy(Context context) {
			try {
				this.progress = new ProgressDialog(context);
				this.progress.setMessage(getResources().getString(
						R.string.getting_list_order));
				progress.setCancelable(false);
				if (!this.progress.isShowing()) {
					this.progress.show();
				}
			} catch (Exception ignored) {
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			getListSaleOrder();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			progress.dismiss();

			if (errorCode != null && "0".equals(errorCode)) {

				if (listChannelOrder == null || listChannelOrder.isEmpty()) {
					CommonActivity.createAlertDialog(
							MainActivity.getInstance(),
							MainActivity.getInstance().getString(
									R.string.no_data_return),
							getString(R.string.app_name), null).show();					
				}

				adapter = new ChannelOrderAdapter(activity, listChannelOrder);
				lvOrder.setAdapter(adapter);

			} else {
				OnClickListener onclick = null;
				if (errorCode != null
						&& errorCode.equalsIgnoreCase(Constant.INVALID_TOKEN)) {
					onclick = moveLogInAct;

				}

				if (description == null || description.isEmpty()) {
					description = getString(R.string.no_data_return);
				}
				CommonActivity
						.createAlertDialog(
								MainActivity.getInstance(),
								description,
								MainActivity.getInstance().getString(
										R.string.app_name), onclick).show();
			}
		}

		// ===========method get list order object=================
		public void getListSaleOrder() {
			String original = "";
			try {
				String methodName = "getListSaleOrder";
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_" + methodName);
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:").append(methodName).append(">");
				rawData.append("<input>");
				rawData.append("<token>");
				rawData.append(Session.getToken());
				rawData.append("</token>");
				if (staffId != null) {
					rawData.append("<ownerId>");
					rawData.append(staffId);
					rawData.append("</ownerId>");
				}

				rawData.append("</input>");
				rawData.append("</ws:").append(methodName).append(">");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, MainActivity.getInstance(),
						"mbccs_" + methodName);
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				if (original != null && !original.isEmpty()) {
					Serializer serializer = new Persister();
					IMOutput imOutput = serializer.read(IMOutput.class,
							original);

					if (imOutput != null) {
						listChannelOrder = imOutput.getListChannelOrder();
						errorCode = imOutput.getErrorCode();
						description = imOutput.getDescription();
					}
				} else {
					errorCode = Constant.ERROR_CODE;
					description = getString(R.string.no_data_return);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private class GetChannelOrderDetailAsy extends AsyncTask<Void, Void, Void> {
		ProgressDialog progress;
		String description = "";
		String errorCode = "";

		public GetChannelOrderDetailAsy(Context context) {
			try {
				this.progress = new ProgressDialog(context);
				this.progress.setMessage(getResources().getString(
						R.string.getting_list_order));
				progress.setCancelable(false);
				if (!this.progress.isShowing()) {
					this.progress.show();
				}
			} catch (Exception ignored) {
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			getListSaleOrderDetail();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			progress.dismiss();

			if (errorCode != null && "0".equals(errorCode)) {

				List<SaleOrderChannelDetail> lst = mapOrderDetail.get(order
						.getSaleOrderId());
				if (lst == null || lst.isEmpty()) {
					CommonActivity.createAlertDialog(
							MainActivity.getInstance(),
							description,
							MainActivity.getInstance().getString(
									R.string.no_data_return), null).show();
					return;
				}
				dialog = new Dialog(activity,
						android.R.style.Theme_Light_NoTitleBar_Fullscreen);
				dialog.setContentView(R.layout.layout_channel_order_manager);

				dialog.findViewById(R.id.lnChooseChannel).setVisibility(
						View.GONE);

                ListView lvOrderDetail = (ListView) dialog.findViewById(R.id.lvOrder);
                TextView tvLabel = (TextView) dialog.findViewById(R.id.tvLabel);
				tvLabel.setText(getString(R.string.label_order_code,
						order.getSaleOrderCode()));
				SaleOrderChannelDetailAdapter detailAdapter = new SaleOrderChannelDetailAdapter(
						activity, lst);
				lvOrderDetail.setAdapter(detailAdapter);

                Button btnHandleOrder = (Button) dialog
                        .findViewById(R.id.btnHandleOrder);
				btnHandleOrder
						.setOnClickListener(FragmentChannelOrderManager.this);
                Button btnClose = (Button) dialog.findViewById(R.id.btnClose);
				btnClose.setOnClickListener(FragmentChannelOrderManager.this);
                Button btnRefuseOrder = (Button) dialog
                        .findViewById(R.id.btnRefuseOrder);
				btnRefuseOrder
						.setOnClickListener(FragmentChannelOrderManager.this);

				dialog.show();
			} else {
				OnClickListener onclick = null;
				if (errorCode != null
						&& errorCode.equalsIgnoreCase(Constant.INVALID_TOKEN)) {
					onclick = moveLogInAct;

				}

				if (description == null || description.isEmpty()) {
					description = getString(R.string.no_data_return);
				}
				Dialog dialog = CommonActivity.createAlertDialog(MainActivity
						.getInstance(), description, MainActivity.getInstance()
						.getString(R.string.app_name), onclick);
				dialog.show();
			}
		}

		// ===========method get list order object=================
		public void getListSaleOrderDetail() {
			String original = "";
			try {
				if (mapOrderDetail.get(order.getSaleOrderId()) != null
						&& !mapOrderDetail.get(order.getSaleOrderId())
								.isEmpty()) {
					errorCode = "0";
					return;
				}
				String methodName = "getListSaleOrderDetail";
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_" + methodName);
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:").append(methodName).append(">");
				rawData.append("<input>");
				rawData.append("<token>");
				rawData.append(Session.getToken());
				rawData.append("</token>");
				rawData.append("<saleChannelOrderId>");
				rawData.append(order.getSaleOrderId());
				rawData.append("</saleChannelOrderId>");
				rawData.append("</input>");
				rawData.append("</ws:").append(methodName).append(">");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, MainActivity.getInstance(),
						"mbccs_" + methodName);
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				if (original != null && !original.isEmpty()) {
					Serializer serializer = new Persister();
					IMOutput imOutput = serializer.read(IMOutput.class,
							original);
					mapOrderDetail.put(order.getSaleOrderId(),
							imOutput.getLstSaleOrderChannelDetail());
					if (imOutput != null) {
						errorCode = imOutput.getErrorCode();
						description = imOutput.getDescription();
					}
				} else {
					errorCode = Constant.ERROR_CODE;
					description = getString(R.string.no_data_return);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private class RefuseOrderChannelAsy extends AsyncTask<Void, Void, Void> {
		ProgressDialog progress;
		String description = "";
		String errorCode = "";

		public RefuseOrderChannelAsy(Context context) {
			try {
				this.progress = new ProgressDialog(context);
				this.progress.setMessage(getResources().getString(
						R.string.refusing_order, order.getSaleOrderCode()));
				progress.setCancelable(false);
				if (!this.progress.isShowing()) {
					this.progress.show();
				}
			} catch (Exception ignored) {
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			refuseOrderChannel();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			progress.dismiss();
			if (errorCode != null && "0".equals(errorCode)) {
				CommonActivity.createAlertDialog(
						MainActivity.getInstance(),

						MainActivity.getInstance().getString(
								R.string.refusing_order_channel_success,
								getString(R.string.app_name),
								order.getSaleOrderCode()), null).show();
				dialog.dismiss();
				mapOrderDetail.remove(order.getSaleOrderId());
				for (int i = 0; i < listChannelOrder.size(); i++) {
					SaleOrderChannel item = listChannelOrder.get(i);
					if (item.getSaleOrderId().compareTo(order.getSaleOrderId()) == 0) {
						listChannelOrder.remove(i);
						adapter.notifyDataSetChanged();
						break;
					}
				}

            } else {
				OnClickListener onclick = null;
				if (errorCode != null
						&& errorCode.equalsIgnoreCase(Constant.INVALID_TOKEN)) {
					onclick = moveLogInAct;

				}

				if (description == null || description.isEmpty()) {
					description = getString(R.string.no_data_return);
				}
				Dialog dialog = CommonActivity.createAlertDialog(MainActivity
						.getInstance(), description, MainActivity.getInstance()
						.getString(R.string.app_name), onclick);
				dialog.show();
			}
		}

		// ===========method get list order object=================
		public void refuseOrderChannel() {
			String original = "";
			try {
				String methodName = "refuseOrderChannel";

				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_" + methodName);
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:").append(methodName).append(">");
				rawData.append("<input>");
				rawData.append("<token>");
				rawData.append(Session.getToken());
				rawData.append("</token>");
				rawData.append("<stockOrderCode>");
				rawData.append(order.getSaleOrderCode());
				rawData.append("</stockOrderCode>");

				rawData.append("</input>");
				rawData.append("</ws:").append(methodName).append(">");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, MainActivity.getInstance(),
						"mbccs_" + methodName);
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				if (original != null && !original.isEmpty()) {
					Serializer serializer = new Persister();
					IMOutput imOutput = serializer.read(IMOutput.class,
							original);
					mapOrderDetail.put(order.getSaleOrderId(),
							imOutput.getLstSaleOrderChannelDetail());
					if (imOutput != null) {
						errorCode = imOutput.getErrorCode();
						description = imOutput.getDescription();
					}
				} else {
					errorCode = Constant.ERROR_CODE;
					description = getString(R.string.no_data_return);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		super.onClick(arg0);
		switch (arg0.getId()) {
		case R.id.btnRefuseOrder:
			if (!CommonActivity.isNetworkConnected(getActivity())) {
				CommonActivity.createAlertNetworkDialog(activity).show();
				return;
			}

			OnClickListener refuseOrderClick = new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					RefuseOrderChannelAsy order = new RefuseOrderChannelAsy(
							activity);
					order.execute();
				}
			};
			String confirmMsg = getString(R.string.confirm_refuse_order,
					order.getSaleOrderCode(), order.getStaffCode());

			CommonActivity.createDialog(getActivity(), confirmMsg,
					getString(R.string.app_name), getString(R.string.ok),
					getResources().getString(R.string.cancel),
					refuseOrderClick, null).show();

			break;
		case R.id.btnClose:
			if (dialog != null) {
				dialog.dismiss();
			}
			break;
		case R.id.btnHandleOrder:

			Staff staff = StaffBusiness.getStaffByStaffCode(activity,
					order.getStaffCode());
			if (staff == null) {
				CommonActivity.createAlertDialog(
						activity,
						getString(R.string.staff_code_not_found,
								order.getStaffCode()),
						getString(R.string.app_name)).show();
				return;
			}

			if (isCollaborator(staff)) {
				if (staff.getX() == null || staff.getY() == null
						|| "0".equals(staff.getX()) || "0".equals(staff.getY())) {
					CommonActivity.createAlertDialog(
							getActivity(),
							getActivity().getResources().getString(
									R.string.staff_have_not_locatation),
							getActivity().getResources().getString(
									R.string.app_name)).show();
					return;
				}
			}

			FragmentSaleSaling fragment = new FragmentSaleSaling();
			Bundle bundle = new Bundle();
			bundle.putSerializable("lstSaleOrderChannelDetail",
					mapOrderDetail.get(order.getSaleOrderId()));
			bundle.putInt("functionType", FragmentSaleSaling.FUNCTION_SALING);
			bundle.putSerializable("saleOrderChannel", order);
			bundle.putBoolean("isHandleOrder", true);
			fragment.setArguments(bundle);
			fragment.setTargetFragment(this, REQUEST_HANDEL_ORDER);
			ReplaceFragment.replaceFragment(getActivity(), fragment, false);
			if (dialog != null) {
				dialog.dismiss();
			}
			break;
		case R.id.tvChooseChannel:
			FragmentChooseChannel f = new FragmentChooseChannel();
			f.setTargetFragment(this, REQUEST_CHOOSE_CHANNEL);
			ReplaceFragment.replaceFragment(getActivity(), f, false);
			break;
		case R.id.imgSearch:
			GetListSaleOrderAsy as = new GetListSaleOrderAsy(activity);
			as.execute();
			break;
		default:
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_CHOOSE_CHANNEL) {
			if (resultCode == Activity.RESULT_OK) {
				Bundle bundle = data.getExtras();
				if (bundle == null) {
					return;
				}
				Staff staff = (Staff) data.getExtras().getSerializable("staff");
				if (staff != null) {

					tvChooseChannel.setText(staff.getName() + " - "
							+ staff.getStaffCode());
					staffId = staff.getStaffId();
				} else {
					tvChooseChannel.setHint(getString(R.string.chooseChannel));
					staffId = null;
				}
				GetListSaleOrderAsy as = new GetListSaleOrderAsy(activity);
				as.execute();
			}
		} else if (requestCode == REQUEST_HANDEL_ORDER) {
			if (resultCode == Activity.RESULT_OK) {
				Bundle bundle = data.getExtras();
				if (bundle == null) {
					return;
				}
				Long saleOrderId = data.getExtras().getLong("saleOrderId");
				if (saleOrderId == null) {
					return;
				}
				for (int i = 0; i < listChannelOrder.size(); i++) {
					if (listChannelOrder.get(i).getSaleOrderId()
							.compareTo(saleOrderId) == 0) {
						listChannelOrder.remove(i);
						adapter = new ChannelOrderAdapter(activity,
								listChannelOrder);
						lvOrder.setAdapter(adapter);
						break;
					}
				}

			}
		}

	}

	private Boolean isCollaborator(Staff staff) {
		if (staff == null) {
			return false;
		}
		if (Constant.CHANNEL_TYPE_POS.equals(staff.getChannelTypeId())) {
			return true;
		}
		if (Constant.CHANNEL_TYPE_COLLABORATOR.equals(staff.getChannelTypeId())) {
			if (Constant.POINT_OF_SALE_TYPE.equals(staff.getPointOfSale())) {
				return true;
			}
		}
		return false;
	}
	@Override
	public void setPermission() {
		// TODO Auto-generated method stub
		permission = ";staff_manage_order;";
	}
}
