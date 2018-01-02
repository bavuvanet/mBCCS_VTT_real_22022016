package com.viettel.bss.viettelpos.v4.sale.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.sale.adapter.StockModelByStaffAdapter;
import com.viettel.bss.viettelpos.v4.sale.object.IMOutput;
import com.viettel.bss.viettelpos.v4.sale.object.PriceBO;
import com.viettel.bss.viettelpos.v4.sale.object.StockModelByStaff;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentChannelOrder extends FragmentCommon implements
		StockModelByStaffAdapter.OncancelStockModel,
		StockModelByStaffAdapter.OnChangeQuantity {
	private static final int REQUEST_ADD_GOODS = 1;
	private Activity activity;
    private Button btnOrder;
    private ListView lvStockModel;
	private ArrayList<StockModelByStaff> lstStockModel;
	private ArrayList<StockModelByStaff> lstStockModelChoose;
	private StockModelByStaffAdapter adapter;
	private View tvHint;
	private String orderCode;
	private Long orderId;
	private TextView tvOrderId;
	private PriceBO price;
	private View btnDelete;

	@Override
	public void onResume() {
		super.onResume();
        setTitleActionBar(R.string.channel_order);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		idLayout = R.layout.layout_channel_order;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();

	}

	@Override
	public void unit(View v) {
		v.findViewById(R.id.edtSearch).setVisibility(View.GONE);
        Button btnAdd = (Button) v.findViewById(R.id.btnAdd);
		tvOrderId = (TextView) v.findViewById(R.id.tvOrderId);
		btnDelete = v.findViewById(R.id.imgDeleteChannel);
        View btnCalculateMoney = v.findViewById(R.id.btnCalculateMoney);
		btnDelete.setOnClickListener(this);
		btnOrder = (Button) v.findViewById(R.id.btnOk);
		tvHint = v.findViewById(R.id.tvHint);
		lvStockModel = (ListView) v.findViewById(R.id.lvStockModel);

		btnCalculateMoney.setOnClickListener(this);
		btnDelete.setOnClickListener(this);
		btnAdd.setOnClickListener(this);
		btnOrder.setOnClickListener(this);
		tvOrderId.setVisibility(View.GONE);
		btnDelete.setVisibility(View.GONE);
		GetListStockModel as = new GetListStockModel(activity);
		as.execute();
		// tuantd7();
	}

	private class GetListStockModel extends AsyncTask<Void, Void, Void> {
		ProgressDialog progress;
		String description = "";
		String errorCode = "";

		public GetListStockModel(Context context) {
			try {
				this.progress = new ProgressDialog(context);
				this.progress.setMessage(getResources().getString(
						R.string.getting_stock_model_to_order));
				progress.setCancelable(false);
				if (!this.progress.isShowing()) {
					this.progress.show();
				}
			} catch (Exception ignored) {
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			getLstStockModelByStaff();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			progress.dismiss();

			if (errorCode != null && "0".equals(errorCode)) {
				if (lstStockModel == null || lstStockModel.isEmpty()) {
					CommonActivity.createAlertDialog(activity,
							getString(R.string.no_data_return),
							getString(R.string.app_name)).show();
					return;
				}
				lstStockModelChoose = new ArrayList<>();
				for (StockModelByStaff stockModel : lstStockModel) {
					if (stockModel.getQuantity() != null
							&& stockModel.getQuantity().compareTo(0L) > 0) {
						lstStockModelChoose.add(stockModel);
					}
				}
				if (lstStockModelChoose.isEmpty()) {
					tvHint.setVisibility(View.VISIBLE);
				} else {
					tvHint.setVisibility(View.GONE);
				}
				if (orderId != null) {
					tvOrderId.setVisibility(View.VISIBLE);
					tvOrderId.setText(getString(R.string.order_id_old,
							orderCode));
					btnOrder.setText(getString(R.string.update_order));
					btnDelete.setVisibility(View.VISIBLE);
				} else {
					tvOrderId.setVisibility(View.GONE);
					btnDelete.setVisibility(View.GONE);
				}
				adapter = new StockModelByStaffAdapter(activity,
						lstStockModelChoose,
						StockModelByStaffAdapter.SHOW_TYPE_ORDER,
						FragmentChannelOrder.this, FragmentChannelOrder.this);
				lvStockModel.setAdapter(adapter);
			} else {
				OnClickListener onclick = null;
				if (errorCode != null
						&& errorCode.equalsIgnoreCase(Constant.INVALID_TOKEN)) {
					onclick = new OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(getActivity(),
									LoginActivity.class);
							intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
									| Intent.FLAG_ACTIVITY_NO_HISTORY);
							startActivity(intent);
							getActivity().finish();
						}
					};
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
		public void getLstStockModelByStaff() {
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getLstStockModelByStaff");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getLstStockModelByStaff>");
				rawData.append("<input>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("</input>");
				rawData.append("</ws:getLstStockModelByStaff>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, MainActivity.getInstance(),
						"mbccs_getLstStockModelByStaff");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				if (original != null && !original.isEmpty()) {
					Serializer serializer = new Persister();
					IMOutput imOutput = serializer.read(IMOutput.class,
							original);
					if (imOutput != null) {
						errorCode = imOutput.getErrorCode();
						description = imOutput.getDescription();
						lstStockModel = imOutput.getLstStockModelByStaff();
						orderCode = imOutput.getOrderCode();
						orderId = imOutput.getOrderId();
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

	private class OrderStockModel extends AsyncTask<Void, Void, Void> {
		ProgressDialog progress;
		String description = "";
		String errorCode = "";

		public OrderStockModel(Context context) {
			try {
				this.progress = new ProgressDialog(context);
				this.progress.setMessage(getResources().getString(
						R.string.orderring));
				progress.setCancelable(false);
				if (!this.progress.isShowing()) {
					this.progress.show();
				}
			} catch (Exception ignored) {
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			orderStockModel();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			progress.dismiss();

			if (errorCode != null && "0".equals(errorCode)) {
				OnClickListener onclick = new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						getActivity().onBackPressed();

					}
				};
				CommonActivity
						.createAlertDialog(
								MainActivity.getInstance(),
								getString(R.string.order_ok),
								MainActivity.getInstance().getString(
										R.string.app_name), onclick).show();
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
		public void orderStockModel() {
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_requestOrderChannel");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:requestOrderChannel>");
				rawData.append("<input>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				if (!CommonActivity.isNullOrEmpty(orderCode)) {
					rawData.append("<stockOrderCode>");
					rawData.append(orderCode);
					rawData.append("</stockOrderCode>");
				}
				rawData.append(input.buildXML(paramToken));
				for (StockModelByStaff item : lstStockModelChoose) {
					rawData.append("<lstOrderChannel>");
					rawData.append("<stockModelId>");
					rawData.append(item.getStockModelId());
					rawData.append("</stockModelId>");

					rawData.append("<quantityOrder>");
					rawData.append(item.getQuantity());
					rawData.append("</quantityOrder>");

					rawData.append("</lstOrderChannel>");
				}
				rawData.append("</input>");
				rawData.append("</ws:requestOrderChannel>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, MainActivity.getInstance(),
						"mbccs_requestOrderChannel");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				if (original != null && !original.isEmpty()) {
					Serializer serializer = new Persister();
					IMOutput imOutput = serializer.read(IMOutput.class,
							original);
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

	private class CalMoneyForSale extends AsyncTask<Void, Void, Void> {
		ProgressDialog progress;
		String description = "";
		String errorCode = "";

		public CalMoneyForSale(Context context) {
			try {
				this.progress = new ProgressDialog(context);
				this.progress.setMessage(getResources().getString(
						R.string.caculating_money));
				progress.setCancelable(false);
				if (!this.progress.isShowing()) {
					this.progress.show();
				}
			} catch (Exception ignored) {
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			calculateMoney();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			progress.dismiss();

			if (errorCode != null && "0".equals(errorCode)) {
				if (price == null) {
					description = getString(R.string.no_data_return);
					Dialog dialog = CommonActivity.createAlertDialog(
							MainActivity.getInstance(),
							description,
							MainActivity.getInstance().getString(
									R.string.app_name), null);
					dialog.show();
					return;
				}
				Long amount = 0L;
				for (StockModelByStaff item : lstStockModelChoose) {
					amount = amount + (item.getQuantity() * item.getPrice());
				}
				Long discount = amount - price.getAmountTax();
				String msg = getString(R.string.tong_tien,
						StringUtils.formatMoney(amount + ""))
						+ "\n"
						+ getString(R.string.discount_amount,
								StringUtils.formatMoney(discount + ""))
						+ "\n"
						+ getString(
								R.string.amount_tax,
								StringUtils.formatMoney(price.getAmountTax()
										+ ""));
				Dialog dialog = CommonActivity.createAlertDialog(MainActivity
						.getInstance(), msg, MainActivity.getInstance()
						.getString(R.string.order_total), null);
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
		public void calculateMoney() {
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_calMoneyForSale");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:calMoneyForSale>");
				rawData.append("<saleInput>");

				rawData.append("<token>");
				rawData.append(Session.token);
				rawData.append("</token>");
				rawData.append("<isCheckOwner>");
				rawData.append(true);
				rawData.append("</isCheckOwner>");
				rawData.append("<isChannel>");
				rawData.append(true);
				rawData.append("</isChannel>");
				rawData.append("<sellerType>");
				rawData.append("2");
				rawData.append("</sellerType>");
				for (StockModelByStaff item : lstStockModelChoose) {
					rawData.append("<lstStockModel>");
					rawData.append("<stockModelId>");
					rawData.append(item.getStockModelId());
					rawData.append("</stockModelId>");

					rawData.append("<haveSerial>");
					rawData.append(item.getCheckSerial() != null
							&& item.getCheckSerial().compareTo(1L) == 0);
					rawData.append("</haveSerial>");

					rawData.append("<amountTotal>");
					rawData.append(item.getQuantity());
					rawData.append("</amountTotal>");

					rawData.append("<stockModelCode>");
					rawData.append(item.getStockModelCode());
					rawData.append("</stockModelCode>");

					rawData.append("</lstStockModel>");
				}
				rawData.append("</saleInput>");
				rawData.append("</ws:calMoneyForSale>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, MainActivity.getInstance(),
						"mbccs_calMoneyForSale");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				if (original != null && !original.isEmpty()) {
					Serializer serializer = new Persister();
					IMOutput imOutput = serializer.read(IMOutput.class,
							original);
					price = imOutput.getPriceBO();
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

	/**
	 * AsygnTask huy don hang
	 * 
	 * @author huypq15
	 * 
	 */
	private class CancelOrderChannelAsy extends AsyncTask<Void, Void, Void> {
		ProgressDialog progress;
		String description = "";
		String errorCode = "";

		public CancelOrderChannelAsy(Context context) {
			try {
				this.progress = new ProgressDialog(context);
				this.progress.setMessage(getResources().getString(
						R.string.cancelingItem));
				progress.setCancelable(false);
				if (!this.progress.isShowing()) {
					this.progress.show();
				}
			} catch (Exception ignored) {
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			cancelOrderChannel();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			progress.dismiss();

			if (errorCode != null && "0".equals(errorCode)) {
				CommonActivity.createAlertDialog(
						MainActivity.getInstance(),

						MainActivity.getInstance().getString(
								R.string.cancel_order_success),
						getString(R.string.app_name), onClickListenerBack)
						.show();
				// tvOrderId.setVisibility(View.GONE);
				// btnDelete.setVisibility(View.GONE);
				// orderId = null;
				// lstStockModelChoose.clear();
				// for (StockModelByStaff item : lstStockModel) {
				// item.setQuantity(0L);
				// }
				// adapter = new StockModelByStaffAdapter(activity,
				// lstStockModelChoose,
				// StockModelByStaffAdapter.SHOW_TYPE_ORDER,
				// FragmentChannelOrder.this, null);
				// lvStockModel.setAdapter(adapter);
				// if (lstStockModelChoose.isEmpty()) {
				// tvHint.setVisibility(View.VISIBLE);
				// } else {
				// tvHint.setVisibility(View.GONE);
				// }

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
		public void cancelOrderChannel() {
			String original = "";
			try {
				String methodName = "cancelOrderChannel";

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
						errorCode = imOutput.getErrorCode();
						description = imOutput.getDescription();
					} else {
						errorCode = Constant.ERROR_CODE;
						description = getString(R.string.no_data_return);
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
		case R.id.btnAdd:
			FragmentChooseStockMode fragment = new FragmentChooseStockMode();
			Bundle bundle = new Bundle();
			bundle.putSerializable("lstStockModel", lstStockModel);
			fragment.setArguments(bundle);
			fragment.setTargetFragment(this, REQUEST_ADD_GOODS);
			ReplaceFragment.replaceFragment(getActivity(), fragment, false);
			break;
		case R.id.btnOk:
			if (lstStockModelChoose == null || lstStockModelChoose.isEmpty()) {
				CommonActivity.createAlertDialog(activity,
						getString(R.string.choose_goods_hint),
						getString(R.string.app_name)).show();
				return;
			}

			if (!CommonActivity.isNetworkConnected(getActivity())) {
				CommonActivity.createAlertNetworkDialog(activity).show();
				return;
			}
			OnClickListener saleClick = new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					OrderStockModel order = new OrderStockModel(activity);
					order.execute();
				}
			};
			String confirmMsg = getString(R.string.confirm_order);
			if (!CommonActivity.isNullOrEmpty(orderCode)) {
				confirmMsg = getString(R.string.confirm_update_order, orderCode);
			}
			Dialog confirmDialog = CommonActivity.createDialog(getActivity(),
					confirmMsg, getString(R.string.app_name),
					getString(R.string.ok),
					getResources().getString(R.string.cancel), saleClick, null);
			confirmDialog.show();
			break;
		case R.id.btnCalculateMoney:
			if (lstStockModelChoose == null || lstStockModelChoose.isEmpty()) {
				CommonActivity.createAlertDialog(activity,
						getString(R.string.choose_goods_hint),
						getString(R.string.app_name)).show();
				return;
			}
			CalMoneyForSale cal = new CalMoneyForSale(activity);
			cal.execute();
			break;
		case R.id.imgDeleteChannel:

			if (!CommonActivity.isNetworkConnected(getActivity())) {
				CommonActivity.createAlertNetworkDialog(activity).show();
				return;
			}

			OnClickListener cancelClick = new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					CancelOrderChannelAsy order = new CancelOrderChannelAsy(
							activity);
					order.execute();
				}
			};

			CommonActivity.createDialog(getActivity(),
					getString(R.string.cancel_order_confirm),
					getString(R.string.app_name), getString(R.string.ok),
					getResources().getString(R.string.cancel), cancelClick,
					null).show();

			break;
		default:
			break;
		}
	}

	@Override
	public void onCancelStockModelListener(StockModelByStaff stockModel) {
		Log.i("channel order", "---start OnactivityResult");
		for (StockModelByStaff item : lstStockModel) {
			if (item.getStockModelId().compareTo(stockModel.getStockModelId()) == 0) {
				item.setQuantity(0L);
				break;
			}
		}
		for (int i = 0; i < lstStockModelChoose.size(); i++) {
			StockModelByStaff item = lstStockModelChoose.get(i);
			if (item.getStockModelId().compareTo(stockModel.getStockModelId()) == 0) {
				lstStockModelChoose.remove(i);
				adapter = new StockModelByStaffAdapter(activity,
						lstStockModelChoose,
						StockModelByStaffAdapter.SHOW_TYPE_ORDER,
						FragmentChannelOrder.this, FragmentChannelOrder.this);
				lvStockModel.setAdapter(adapter);
				if (lstStockModelChoose.isEmpty()) {
					tvHint.setVisibility(View.VISIBLE);
				} else {
					tvHint.setVisibility(View.GONE);
				}
				break;
			}
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_ADD_GOODS) {
			if (resultCode == Activity.RESULT_OK) {
				Bundle bundle = data.getExtras();
				if (bundle == null) {
					return;
				}
				StockModelByStaff stockModel = (StockModelByStaff) bundle
						.getSerializable("stockModel");
				for (StockModelByStaff item : lstStockModel) {
					if (item.getStockModelId().compareTo(
							stockModel.getStockModelId()) == 0) {
						item.setQuantity(stockModel.getQuantity());
						break;
					}
				}
				boolean isChoice = false;
				for (StockModelByStaff item : lstStockModelChoose) {
					if (item.getStockModelId().compareTo(
							stockModel.getStockModelId()) == 0) {
						item.setQuantity(stockModel.getQuantity());
						isChoice = true;
						break;
					}
				}
				if (!isChoice) {
					lstStockModelChoose.add(stockModel);
				}

				adapter = new StockModelByStaffAdapter(activity,
						lstStockModelChoose,
						StockModelByStaffAdapter.SHOW_TYPE_ORDER,
						FragmentChannelOrder.this, FragmentChannelOrder.this);
				lvStockModel.setAdapter(adapter);
				if (lstStockModelChoose.isEmpty()) {
					tvHint.setVisibility(View.VISIBLE);
				} else {
					tvHint.setVisibility(View.GONE);
				}
			}
		}
	}

	private Boolean isFormatted = false;
	private int chooseIndex = 0;
	private int lstIndex = 0;

	@Override
	public void onChangeQuantity(StockModelByStaff stockModel) {

		boolean isChoose = false;
		for (int i = 0; i < lstStockModelChoose.size(); i++) {
			if (lstStockModelChoose.get(i).getStockModelId()
					.compareTo(stockModel.getStockModelId()) == 0) {
				chooseIndex = i;
				isChoose = true;
				break;
			}
		}
		for (int i = 0; i < lstStockModel.size(); i++) {
			if (lstStockModel.get(i).getStockModelId()
					.compareTo(stockModel.getStockModelId()) == 0) {
				lstIndex = i;
				break;
			}
		}

		final Dialog dialog = new Dialog(getActivity());
		final EditText edtQuantity;
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.sale_input_quantity_dialog);
		TextView tvDialogTitle = (TextView) dialog
				.findViewById(R.id.tvDialogTitle);
		tvDialogTitle.setText(stockModel.getStockModelCode() + " - "
				+ stockModel.getName());
		edtQuantity = (EditText) dialog.findViewById(R.id.edtQuantity);
		if (stockModel.getQuantity() != null && stockModel.getQuantity() > 0) {
			edtQuantity.setText(stockModel.getQuantity() + "");
		}

		edtQuantity.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (!isFormatted) {
					isFormatted = true;
					edtQuantity.setText(StringUtils.formatMoney(s.toString()
							.replaceAll("\\.", "")));
					edtQuantity.setSelection(edtQuantity.getText().toString()
							.length());
					isFormatted = false;
				}
			}
		});
		dialog.findViewById(R.id.btnOk).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {

						String strQuantity = edtQuantity.getText().toString();
						Long quantity = 0L;

						try {
							quantity = Long.parseLong(strQuantity.replace(".",
									""));
						} catch (Exception e) {
							Toast.makeText(
									getActivity(),
									getResources().getString(
											R.string.quantity_not_valid),
									Toast.LENGTH_LONG).show();
							return;
						}

						if (quantity.intValue() == 0) {
							Toast.makeText(
									getActivity(),
									getResources()
											.getString(
													R.string.quantity_must_greate_than_0),
									Toast.LENGTH_LONG).show();
							return;
						}
						lstStockModel.get(lstIndex).setQuantity(quantity);
						lstStockModelChoose.get(chooseIndex).setQuantity(
								quantity);
						adapter = new StockModelByStaffAdapter(activity,
								lstStockModelChoose,
								StockModelByStaffAdapter.SHOW_TYPE_ORDER,
								FragmentChannelOrder.this,
								FragmentChannelOrder.this);
						lvStockModel.setAdapter(adapter);
						dialog.dismiss();

					}
				});
		dialog.findViewById(R.id.btnViewSaleTrans).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						CommonActivity.hideSoftKeyboard(MainActivity
								.getInstance());
						dialog.dismiss();
					}
				});
		dialog.getWindow().setSoftInputMode(
				LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		dialog.show();

	}

	@Override
	public void setPermission() {
		// TODO Auto-generated method stub
		permission = ";channel.order;";
	}
}
