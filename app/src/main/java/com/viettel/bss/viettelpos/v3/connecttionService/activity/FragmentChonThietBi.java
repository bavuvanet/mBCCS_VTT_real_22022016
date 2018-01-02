package com.viettel.bss.viettelpos.v3.connecttionService.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferTypeDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferingDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.GPSTracker;
import com.viettel.bss.viettelpos.v4.commons.GPSTrackerFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.TabThueBaoHopDongManager;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.ui.image.utils.AsyncTask;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;
import com.viettel.bss.viettelpos.v3.connecttionService.adapter.StockTypeAdapter;
import com.viettel.bss.viettelpos.v3.connecttionService.model.ApStockSupplyInfo;
import com.viettel.bss.viettelpos.v3.connecttionService.model.MapSupplyMethodSupplyProgram;
import com.viettel.bss.viettelpos.v3.connecttionService.model.Price;
import com.viettel.bss.viettelpos.v3.connecttionService.model.StockModelConnectSub;
import com.viettel.bss.viettelpos.v3.connecttionService.model.SupplyProgram;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentChonThietBi extends GPSTracker implements
		OnClickListener {

	private ListView lvItem;
	private ArrayList<ProductOfferTypeDTO> lstData = new ArrayList<ProductOfferTypeDTO>();

	private String regType;
	private ListView lvStockModel;
	private Map<String, List<SupplyProgram>> mapProgram = new HashMap<String, List<SupplyProgram>>();
	private Spinner spnProgram;
	private TextView tvMonth;
	private Spinner spnPayType;
	private TextView tvMoney;
	private Spinner spnHTCC;
	private Button btnChonThietBi;
	private Button btnCacleChonThietBi;
	private Dialog dialog;
	private ProductOfferingDTO stockModel;
	private Dialog stockTypeDialog;
	ProductOfferTypeDTO productType;
	private boolean getStockTypeResult = false;
	@BindView(R.id.toolbar)
	Toolbar toolbar;
	private String moneyKey = "";
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.layout_single_listview);
		ButterKnife.bind(this);
		setSupportActionBar(toolbar);
		getSupportActionBar().setTitle(getString(R.string.chongoicuoc));
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		Bundle b = getIntent().getExtras();
		if (b != null) {
			lstData = (ArrayList<ProductOfferTypeDTO>) b
					.getSerializable("lstProductTypeKey");
			regType = b.getString("regTypekey");
		}
        MainActivity.getInstance().setTitleActionBar(R.string.chonthietbi);
		unit();
		// adapter = new GetHTHMAdapter(arrHthmBeans, this);
		getStockTypeResult = true;
		StockTypeAdapter adapter = new StockTypeAdapter(lstData, this);

		lvItem.setAdapter(adapter);
		super.onCreate(savedInstanceState);
	}

	public void unit() {
		lvItem = (ListView) findViewById(R.id.lvItem);
		lvItem.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				productType = lstData.get(arg2);
				showDialogChooseStockModel();
			}
		});
		findViewById(R.id.btnCancel).setVisibility(View.GONE);
		findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Check tat ca cac loai mat hang da duoc chon hay chua
				if (!getStockTypeResult) {
					CommonActivity.createErrorDialog(FragmentChonThietBi.this,
							getString(R.string.chon_het_thiet_bi), "1").show();
					return;
				}

				if (!CommonActivity.isNullOrEmpty(lstData)) {
					for (ProductOfferTypeDTO item : lstData) {
						if (item.getStockModel() == null) {
							CommonActivity.createErrorDialog(
									FragmentChonThietBi.this,
									getString(R.string.chon_het_thiet_bi), "1")
									.show();
							return;
						}
					}

				}
				Intent data = new Intent();
				Bundle mBundle = new Bundle();
				mBundle.putSerializable("lstProductTypeKey", lstData);
				if(!CommonActivity.isNullOrEmpty(moneyKey)){
					mBundle.putString("priceKey" , moneyKey.replace(".",""));
				}

				data.putExtras(mBundle);
				setResult(Activity.RESULT_OK, data);
				finish();
			}
		});
	}

	// lay ds hang hoa
	// ===== ws danh sach hang hoa tra sau=============

	private class GetListSupplyInfoAsyn extends
			AsyncTask<Void, Void, List<ApStockSupplyInfo>> {

		ProgressDialog progress;
		private Context context = null;
		String errorCode = "";
		String description = "";
		private String saleServiceCode;

		public GetListSupplyInfoAsyn(Context context, String saleServiceCode) {
			this.context = context;
			this.progress = new ProgressDialog(context);
			this.saleServiceCode = saleServiceCode;
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected List<ApStockSupplyInfo> doInBackground(Void... arg0) {
			return getListSupplyInfo();
		}

		@Override
		protected void onPostExecute(List<ApStockSupplyInfo> result) {
			this.progress.dismiss();

			if (errorCode.equals("0")) {
				getStockTypeResult = true;
				if (result != null && result.size() > 0) {
					// if (dialog != null) {
					// dialog.dismiss();
					// }
					stockModel.setLstAppApStockSupplyInfo(result);
					showDialogChooseProgram(stockModel);

				}
			} else {
				getStockTypeResult = false;
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					CommonActivity.BackFromLogin((Activity) context,
							description);
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(context,
							description,
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		private List<ApStockSupplyInfo> getListSupplyInfo() {
			List<ApStockSupplyInfo> result = null;
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getSupplyInfoSP");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getSupplyInfoSP>");
				rawData.append("<input>");
				HashMap<String, String> paramToken = new HashMap<String, String>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));

				rawData.append("<saleServiceCode>" + saleServiceCode);
				rawData.append("</saleServiceCode>");

				rawData.append("<regType>" + regType);
				rawData.append("</regType>");

//				if(!CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit) && !CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit.getResultGetVendorByConnectorForm())){
//					if(!CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit.getResultGetVendorByConnectorForm().getVendorCode())){
//
//						rawData.append("<vendor>" + TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit.getResultGetVendorByConnectorForm().getVendorCode());
//						rawData.append("</vendor>");
//
//					}
//				}


				rawData.append("<stockModelId>"
						+ stockModel.getProductOfferingId());
				rawData.append("</stockModelId>");
				rawData.append("</input>");
				rawData.append("</ws:getSupplyInfoSP>");

				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, context, "mbccs_getSupplyInfoSP");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				Log.i("original 69696", "original :" + original);

				// ============parse xml in android=========

				Serializer serializer = new Persister();
				ParseOuput parseOuput = serializer.read(ParseOuput.class,
						original);
				if (parseOuput != null) {
					errorCode = parseOuput.getErrorCode();
					description = parseOuput.getDescription();
					result = parseOuput.getLstApStockSupplyInfos();
				}
			} catch (Exception e) {
				Log.d("getListProduct", e.toString());
			}

			return result;
		}
	}

	private void showDialogChooseStockModel() {
		stockTypeDialog = new Dialog(this);
		stockTypeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		stockTypeDialog.setContentView(R.layout.layout_single_listview);
		stockTypeDialog.setTitle(productType.getName());
		Toolbar toolbarDialog = (Toolbar)  stockTypeDialog.findViewById(R.id.toolbar);
		toolbarDialog.setVisibility(View.GONE);
		lvStockModel = (ListView) stockTypeDialog.findViewById(R.id.lvItem);
		ArrayAdapter<String> stockAdapter = new ArrayAdapter<String>(this,
				R.layout.spinner_item, R.id.spinner_value);
		if (!CommonActivity.isNullOrEmpty(productType.getProductOfferings())) {
			for (ProductOfferingDTO item : productType.getProductOfferings()) {
				String msg = item.toString();
				if (item.getRequestQuantity() != null) {
					msg = msg
							+ "\n"
							+ getString(R.string.quantity_request,
									item.getRequestQuantity());
				}

				if (item.getAvailableQuantity() != null) {
					msg = msg
							+ "\n"
							+ getString(R.string.quantity_in_stock,
									item.getAvailableQuantity());
				}

				if (item.getReceiveQuantity() != null) {
					msg = msg
							+ "\n"
							+ getString(R.string.quantity_useful,
									item.getReceiveQuantity());
				}
				stockAdapter.add(msg);
			}
		}
		stockTypeDialog.findViewById(R.id.lnButton).setVisibility(View.GONE);
		TextView tv = (TextView) stockTypeDialog.findViewById(R.id.tv1);
		tv.setText(R.string.danh_sach_mat_hang);
		lvStockModel.setAdapter(stockAdapter);
		lvStockModel.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				stockModel = productType.getProductOfferings().get(arg2);
				if (CommonActivity.isNullOrEmpty(stockModel
						.getLstAppApStockSupplyInfo())) {
					GetListSupplyInfoAsyn asy = new GetListSupplyInfoAsyn(
							FragmentChonThietBi.this, productType
									.getSaleServiceCode());
					asy.execute();
				} else {
					showDialogChooseProgram(stockModel);
				}
			}
		});
		stockTypeDialog.show();
	}

	private void showDialogChooseProgram(ProductOfferingDTO stockModel) {
		List<Spin> lstHTCC = new ArrayList<Spin>();

		Spin first = new Spin("", getString(R.string.select_one_value));
		lstHTCC.add(first);
		dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_choose_programe_co_dinh);
		TextView tvTitle = (TextView) dialog.findViewById(R.id.tvDialogTitle);
		tvTitle.setText(stockModel.getName());
		spnHTCC = (Spinner) dialog.findViewById(R.id.spnHTCC);
		spnProgram = (Spinner) dialog.findViewById(R.id.spnProgram);
		tvMonth = (TextView) dialog.findViewById(R.id.tvMonth);
		spnPayType = (Spinner) dialog.findViewById(R.id.spnPayType);
		tvMoney = (TextView) dialog.findViewById(R.id.tvMoney);
		btnChonThietBi = (Button) dialog.findViewById(R.id.btnChonThietBi);
		btnCacleChonThietBi = (Button) dialog.findViewById(R.id.btnCancel);
		btnChonThietBi.setOnClickListener(this);
		btnCacleChonThietBi.setOnClickListener(this);
		List<MapSupplyMethodSupplyProgram> lstProgram = stockModel
				.getLstAppApStockSupplyInfo().get(0)
				.getLstMapSupplyMethodSupplyPrograms();
		for (MapSupplyMethodSupplyProgram item : lstProgram) {
			if (!CommonActivity.isNullOrEmpty(item.getLstSupplyPrograms())) {
				Spin tmp = new Spin(item.getSupplyMethod(),
						getLabelHTCC(item.getSupplyMethod()));
				lstHTCC.add(tmp);
				mapProgram.put(item.getSupplyMethod(),
						item.getLstSupplyPrograms());
			}
		}
		if (stockModel.getLstAppApStockSupplyInfo().get(0).getPrice() != null) {
			Spin tmp = new Spin("BD", getLabelHTCC("BD"));
			lstHTCC.add(tmp);
		}

		initHTCC(lstHTCC, stockModel.getLstAppApStockSupplyInfo().get(0)
				.getPrice(), stockModel);

		dialog.show();
	}

	private String getLabelHTCC(String htcc) {
		if ("CT".equalsIgnoreCase(htcc)) {
			return getString(R.string.CT);
		}
		if ("DC".equalsIgnoreCase(htcc)) {
			return getString(R.string.DC);
		}
		if ("BD".equalsIgnoreCase(htcc)) {
			return getString(R.string.BD);
		}
		return "";
	}

	private void initHTCC(List<Spin> lst, final Long money,
			ProductOfferingDTO stockModel) {
		ArrayAdapter<Spin> httc = new ArrayAdapter<Spin>(this,
				R.layout.spinner_item, R.id.spinner_value, lst);
		spnHTCC.setAdapter(httc);
		if (productType.getStockModel() != null
				&& productType.getStockModel().getStockModelCode()
						.equals(stockModel.getCode())) {
			for (int i = 0; i < lst.size(); i++) {

				if (productType.getStockModel().getSupplyMethod()
						.equals(lst.get(i).getId())) {
					spnHTCC.setSelection(i);
				}
			}
		}
		spnHTCC.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Spin htcc = (Spin) arg0.getSelectedItem();
				if ("BD".equals(htcc.getId())) {
					dialog.findViewById(R.id.lnMonth).setVisibility(View.GONE);
					dialog.findViewById(R.id.lnProgram)
							.setVisibility(View.GONE);
					dialog.findViewById(R.id.lnPayMethod).setVisibility(
							View.GONE);
					tvMoney.setText(StringUtils.formatMoney(money + ""));
					moneyKey = tvMoney.getText().toString();
				} else {
					tvMoney.setText("");
					moneyKey = "";
					dialog.findViewById(R.id.lnMonth).setVisibility(
							View.VISIBLE);
					dialog.findViewById(R.id.lnProgram).setVisibility(
							View.VISIBLE);
					dialog.findViewById(R.id.lnPayMethod).setVisibility(
							View.VISIBLE);
					List<SupplyProgram> lstProgram = mapProgram.get(htcc
							.getId());
					if (CommonActivity.isNullOrEmpty(lstProgram)) {
						lstProgram = new ArrayList<SupplyProgram>();
					}
					lstProgram.add(0, new SupplyProgram(
							getString(R.string.select_one_value)));
					initProgram(lstProgram);

					initPayMethod();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}

	private void initPayMethod() {
		ArrayList<Spin> lst = new ArrayList<Spin>();
		lst.add(new Spin("1", getResources().getString(R.string.trangay)));
		lst.add(new Spin("0", getResources().getString(R.string.tratt)));
		ArrayAdapter<Spin> adapter = new ArrayAdapter<Spin>(this,
				R.layout.spinner_item, R.id.spinner_value, lst);
		spnPayType.setAdapter(adapter);

		if (productType.getStockModel() != null) {
			for (int i = 0; i < lst.size(); i++) {
				if (!CommonActivity.isNullOrEmpty(productType.getStockModel()
						.getHttt())
						&& productType.getStockModel().getHttt()
								.equals(lst.get(i).getId())) {
					spnPayType.setSelection(i);
					break;
				}
			}

		}
	}

	private void initProgram(List<SupplyProgram> programs) {
		ArrayAdapter<SupplyProgram> adapter = new ArrayAdapter<SupplyProgram>(
				this, R.layout.spinner_item, R.id.spinner_value, programs);
		spnProgram.setAdapter(adapter);
		if (productType.getStockModel() != null) {
			for (int i = 0; i < programs.size(); i++) {
				if (!CommonActivity.isNullOrEmpty(productType.getStockModel()
						.getProgramMonth())
						&& productType.getStockModel().getProgramMonth()
								.equals(programs.get(i).getCode())) {
					spnProgram.setSelection(i);
					break;

				}
			}

		}

		spnProgram.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				SupplyProgram supply = (SupplyProgram) arg0.getSelectedItem();
				Price tmp = null;
				if (!CommonActivity.isNullOrEmpty(supply.getSupplyMonthLst())) {
					tmp = supply.getSupplyMonthLst().get(0);
				}
				if (tmp != null) {
					tvMonth.setText(tmp.getProgramMonth() + "");
					tvMoney.setText(StringUtils.formatMoney(tmp.getPrice() + ""));
					moneyKey = tmp.getPrice() + "";
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {

		case R.id.btnChonThietBi:
			StockModelConnectSub stock = new StockModelConnectSub();

			// Kiem tra hinh thuc cung cap
			if (spnHTCC.getSelectedItemPosition() == 0) {
				CommonActivity.createErrorDialog(FragmentChonThietBi.this,
						getString(R.string.checkhtcc), "1").show();
				return;
			}
			Spin htcc = (Spin) spnHTCC.getSelectedItem();
			stock.setSupplyMethod(htcc.getId());
			if (!"BD".equals(htcc.getId())) {
				// Kiem tra chuong trinh
				SupplyProgram program = (SupplyProgram) spnProgram
						.getSelectedItem();
				if (CommonActivity.isNullOrEmpty(program.getCode())) {
					CommonActivity.createErrorDialog(FragmentChonThietBi.this,
							getString(R.string.checkct), "1").show();
					return;
				}
				stock.setProgramMonth(program.getCode());
				if(!CommonActivity.isNullOrEmpty(tvMonth.getText().toString())){
					stock.setNumberMonth(tvMonth.getText().toString().trim() + "");
				}
				stock.setPrice(stockModel.getLstAppApStockSupplyInfo().get(0)
						.getPrice());
				Spin httt = (Spin) spnPayType.getSelectedItem();
				stock.setHttt(httt.getId());

			}else{
				if(!CommonActivity.isNullOrEmpty(moneyKey)){
					String money = moneyKey.replaceAll("\\.","");
					if(!CommonActivity.isNullOrEmpty(money)){
						stock.setPrice(Long.parseLong(money));
					}else{
						stock.setPrice(0L);
					}
				}else{
					stock.setPrice(0L);
				}

			}

			stock.setStockModelId(stockModel.getProductOfferingId() + "");
			stock.setStockModelCode(stockModel.getCode());
			stock.setStockModelName(stockModel.getName());
			productType.setStockModel(stock);
			dialog.dismiss();
			StockTypeAdapter adapter = new StockTypeAdapter(lstData, this);
			lvItem.setAdapter(adapter);
			stockTypeDialog.dismiss();
			break;
			case R.id.btnCancel:
				dialog.dismiss();
				break;
			case R.id.relaBackHome:
				onBackPressed();
				break;
		}
	}

}
