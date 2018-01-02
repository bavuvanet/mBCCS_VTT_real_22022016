package com.viettel.bss.viettelpos.v4.sale.activity;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.handler.VSAMenuHandler;
import com.viettel.bss.viettelpos.v4.sale.adapter.ViewInFoAdapter;
import com.viettel.bss.viettelpos.v4.sale.adapter.ViewInFoAdapter.OnChangeSoLuong;
import com.viettel.bss.viettelpos.v4.sale.adapter.ViewInFoAdapter.OncacelObjectMerge;
import com.viettel.bss.viettelpos.v4.sale.business.CacheData;
import com.viettel.bss.viettelpos.v4.sale.business.TelecomServiceBusiness;
import com.viettel.bss.viettelpos.v4.sale.dal.ViewInfoAndOrderDal;
import com.viettel.bss.viettelpos.v4.sale.object.ObjectCheckStockTrans;
import com.viettel.bss.viettelpos.v4.sale.object.TelecomServiceObject;
import com.viettel.bss.viettelpos.v4.sale.object.ViewInfoOjectMerge;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class FragmentViewinfoAndOrder extends Fragment implements
		OnClickListener, OncacelObjectMerge, OnChangeSoLuong, OnTouchListener {
	private Button btnHome;
	private TextView txtviewtonghanmuc, textstockordercode;
	private Spinner spindichvu = null;
	private ViewInfoAndOrderDal viewInfoDal = null;
	private long selectedTelecom = 0L;
	private ListView listDataViewInfo;
	private TextView txtviewremainstock, textconlai, textgiatrihangdat;
	// define linearlayout button
	private LinearLayout linearbuttonOderitem, linearbuttoncancelOder;
	// Long sumhangton = 0l;
	// public boolean ischeckOrderItem = false;
    private Long conlai = 0L;
	private Button btnsaleitem;
    private Button btnviewreviewoderitem;
    private Button btncancelitem;
    private Button btnreoderitem;
    private Button btnviewreviewoderitem2;
	Long valueitemorder = 0L;
	private boolean isCheckReview = false;
	private ArrayList<TelecomServiceObject> lstService = null;

	private ArrayList<ViewInfoOjectMerge> lisViewInfoOjectMerges = new ArrayList<>();
	private ArrayList<ViewInfoOjectMerge> lstMergeAllService = new ArrayList<>();
	private final ArrayList<ViewInfoOjectMerge> lisMerges = new ArrayList<>();
	private ArrayList<ObjectCheckStockTrans> lisCheckStockTrans = new ArrayList<>();
	private ViewInFoAdapter viewInFoAdapter = null;
	private long sumres = 0L;
	// Long countmoney = 0l;
    private Long sumhangton = 0L;
	private long quantity_TH = 0L;
	// khai bao bien luu gia tri hang da dat
	Long valuesOrdedItem = 0L;
	private EditText edtsearch;
	private final String permission = Constant.VSAMenu.SALE_ORDER;

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
		View mView = inflater.inflate(R.layout.viewinfoandorder_layout2,
				container, false);
		unit(mView);
		initSpinnerDichVu();
		isCheckReview = false;
		ViewInfoAndOrderDal dal = new ViewInfoAndOrderDal(getActivity());
		dal.open();
		try {
			lisCheckStockTrans = dal.getStockTrans();
			for (int i = 0; i < lisCheckStockTrans.size(); i++) {
				sumres = sumres + lisCheckStockTrans.get(i).getSumitemRes();
				Log.d("sumres", "" + sumres);
			}

			for (ViewInfoOjectMerge item : lstMergeAllService) {
				if (!item.get_quantity_issui_NV().equals("0")) {
					sumhangton = sumhangton
							+ Long.parseLong(item.get_sumremane());
				}

			}
			sumhangton = sumhangton + sumres;
			Log.d("sumhangton", "" + sumhangton);
			String tonghanmuc = txtviewtonghanmuc.getText().toString()
					.replace(".", "");
			conlai = Long.parseLong(tonghanmuc) - sumhangton;
			txtviewremainstock
					.setText(StringUtils.formatMoney("" + sumhangton));
			textconlai.setText(StringUtils.formatMoney("" + conlai));
			dal.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		spindichvu.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Log.d("possition_spiner", "" + position);

				selectedTelecom = lstService.get(position)
						.getTelecomServiceId();
				Log.d("selectedTelecomidddddd", "" + selectedTelecom);
				lisViewInfoOjectMerges = getlistDataAsynItem(selectedTelecom);

				for (ViewInfoOjectMerge item : lisViewInfoOjectMerges) {
					Long stockmodelid = Long.parseLong(item
							.get_stock_model_id());
					for (ViewInfoOjectMerge itemMerge : lisMerges) {
						Long stockmodelidMerge = Long.parseLong(itemMerge
								.get_stock_model_id());
						if (stockmodelid.compareTo(stockmodelidMerge) == 0) {
							item.set_soluong(itemMerge.get_soluong());
							break;
						}
					}
				}
				// lay gia tri hang da dat
				isCheckReview = false;
				btnviewreviewoderitem.setText(getResources().getString(
						R.string.DSitemorder));
				btnviewreviewoderitem2.setText(getResources().getString(
						R.string.DSitemorder));
				viewInFoAdapter = new ViewInFoAdapter(getActivity(),
						lisViewInfoOjectMerges, FragmentViewinfoAndOrder.this,
						FragmentViewinfoAndOrder.this);
				listDataViewInfo.setAdapter(viewInFoAdapter);
				// }
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				Log.d("onNothingSelected", "onNothingSelected");
			}
		});
		// event review order item
		btnviewreviewoderitem2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				isCheckReview = !isCheckReview;
				if (isCheckReview) {
					// show item da duoc dat
					btnviewreviewoderitem2.setText(getResources().getString(
							R.string.ViewAllItem));
					viewInFoAdapter.setLisInfoOjects(lisMerges);
					viewInFoAdapter.notifyDataSetChanged();
				} else {
					btnviewreviewoderitem2.setText(getResources().getString(
							R.string.DSitemorder));
					viewInFoAdapter.setLisInfoOjects(lisViewInfoOjectMerges);
					viewInFoAdapter.notifyDataSetChanged();
				}
			}
		});

		// event review order item
		btnviewreviewoderitem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				isCheckReview = !isCheckReview;
				if (isCheckReview) {
					// show item da duoc dat
					btnviewreviewoderitem.setText(getResources().getString(
							R.string.ViewAllItem));
					viewInFoAdapter.setLisInfoOjects(lisMerges);
					viewInFoAdapter.notifyDataSetChanged();

				} else {

					btnviewreviewoderitem.setText(getResources().getString(
							R.string.DSitemorder));
					viewInFoAdapter.setLisInfoOjects(lisViewInfoOjectMerges);
					viewInFoAdapter.notifyDataSetChanged();
				}

				// viewInFoAdapter.filter(isCheckReview,
				// lisViewInfoOjectMerges);
			}
		});

		// button sale oder item
		btnsaleitem.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (lisMerges.size() == 0) {
					Toast.makeText(getActivity(),
							getResources().getString(R.string.isorderitem),
							Toast.LENGTH_LONG).show();
					return;
				}
				if (lisMerges != null && lisMerges.size() > 0) {
					if (CommonActivity.isNetworkConnected(getActivity())) {
						Dialog dialog = CommonActivity.createDialog(
								getActivity(),
								getResources().getString(R.string.saletite),
								getResources().getString(R.string.orderitem),
								getResources().getString(R.string.ok),
								getResources().getString(R.string.cancel),
								OrderItemClick, null);
						dialog.show();
					} else {
						Dialog dialog = CommonActivity
								.createAlertDialog(
										getActivity(),
										getResources().getString(
												R.string.errorNetwork),
										getResources().getString(
												R.string.app_name));
						dialog.show();
					}
				}
			}
		});
		// event cancel order item
		btncancelitem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (CommonActivity.isNetworkConnected(getActivity())) {
					Dialog dialog = CommonActivity.createDialog(getActivity(),
							getResources().getString(R.string.canceloder),
							getResources().getString(R.string.orderitem),
							getResources().getString(R.string.ok),
							getResources().getString(R.string.cancel),
							CancelOrderClick, null);
					dialog.show();
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.errorNetwork),
							getResources().getString(R.string.app_name));
					dialog.show();
				}

			}
		});

		// event lai hang
		btnreoderitem.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View arg0) {

				// TODO Auto-generated method stub dat lai hang
				// if(ischeckOrderItem == true){
				if (lisMerges.size() == 0) {
					Toast.makeText(getActivity(),
							getResources().getString(R.string.isorderitem),
							Toast.LENGTH_SHORT).show();
				}
				if (CommonActivity.isNetworkConnected(getActivity())) {
					Dialog dialog = CommonActivity.createDialog(getActivity(),
							getResources().getString(R.string.saletitere),
							getResources().getString(R.string.orderitem),
							getResources().getString(R.string.ok),
							getResources().getString(R.string.cancel),
							OrderReSynClick, null);
					dialog.show();

				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.errorNetwork),
							getResources().getString(R.string.app_name));
					dialog.show();
				}

			}
		});
		return mView;
	}

	// ====== event dat lai hang
    private final OnClickListener OrderReSynClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// call Asyntask========
			OrderReItemManager odItemManager = new OrderReItemManager(
					getActivity(), lisMerges);
			odItemManager.execute();
		}
	};

	// ======huy don hang =========

	private final OnClickListener CancelOrderClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// call Asyntask========
			CancelOrderManager cancelOrderManager = new CancelOrderManager(
					getActivity());
			cancelOrderManager.execute();
		}
	};
	// ========= su kien dat hang

	private final OnClickListener OrderItemClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// call Asyntask========
			if (lisMerges != null && lisMerges.size() > 0) {
				OrderItemManager orderItemManager = new OrderItemManager(
						getActivity(), lisMerges);
				orderItemManager.execute(lisMerges);
			}
		}
	};

	private ArrayList<ViewInfoOjectMerge> getlistDataAsynItem(
            Long selectedTelecom) {
		ArrayList<ViewInfoOjectMerge> lisInfoOject = new ArrayList<>();
		try {



			// viewInfoDal.close();
			if (lstMergeAllService != null && !lstMergeAllService.isEmpty()) {
				for (ViewInfoOjectMerge tmp : lstMergeAllService) {
					if (tmp.getTelecomServiceId().compareTo(selectedTelecom) == 0) {
						lisInfoOject.add(tmp);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lisInfoOject;
	}

	// fill data for spiner
	private void initSpinnerDichVu() {
		try {
			lstService = TelecomServiceBusiness
					.getAllTelecomService(getActivity());
			if (lstService != null && !lstService.isEmpty()) {
				ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        getActivity(),
                        android.R.layout.simple_dropdown_item_1line,
                        android.R.id.text1);
				for (TelecomServiceObject telecomServiceObject : lstService) {
					adapter.add(telecomServiceObject.getName());
				}
				spindichvu.setAdapter(adapter);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void unit(View view) {

		edtsearch = (EditText) view.findViewById(R.id.edtsearch);
		edtsearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String input = edtsearch.getText().toString()
						.toLowerCase(Locale.getDefault());

				if (isCheckReview) {
					// show item da duoc dat
					btnviewreviewoderitem2.setText(getResources().getString(
							R.string.ViewAllItem));
					viewInFoAdapter = new ViewInFoAdapter(getActivity(),
							lisMerges, FragmentViewinfoAndOrder.this,
							FragmentViewinfoAndOrder.this);
					viewInFoAdapter.notifyDataSetChanged();
					if (viewInFoAdapter != null) {
						viewInFoAdapter.SearchInput(input);
						listDataViewInfo.setAdapter(viewInFoAdapter);
					}
				} else {
					btnviewreviewoderitem2.setText(getResources().getString(
							R.string.DSitemorder));
					// viewInFoAdapter.setLisInfoOjects(lisViewInfoOjectMerges);
					viewInFoAdapter = new ViewInFoAdapter(getActivity(),
							lisViewInfoOjectMerges,
							FragmentViewinfoAndOrder.this,
							FragmentViewinfoAndOrder.this);
					viewInFoAdapter.notifyDataSetChanged();
					if (viewInFoAdapter != null) {
						viewInFoAdapter.SearchInput(input);
						listDataViewInfo.setAdapter(viewInFoAdapter);
					}
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		txtviewtonghanmuc = (TextView) view.findViewById(R.id.txtsumhamuc);
		listDataViewInfo = (ListView) view
				.findViewById(R.id.listdataviewinforder);
		listDataViewInfo.setOnTouchListener(this);
		listDataViewInfo.setTextFilterEnabled(true);
		txtviewremainstock = (TextView) view
				.findViewById(R.id.txtgiatritrongkho);
		textconlai = (TextView) view.findViewById(R.id.txtconlai);
		// linear layout button
		linearbuttonOderitem = (LinearLayout) view.findViewById(R.id.lnButton);
		linearbuttoncancelOder = (LinearLayout) view
				.findViewById(R.id.listbutonCacel);
		// define 2 button cancel item
		btncancelitem = (Button) view.findViewById(R.id.btncancelitem);
		btnreoderitem = (Button) view.findViewById(R.id.btnreorder);
		textgiatrihangdat = (TextView) view.findViewById(R.id.txtgiatrihangdat);
		// =====fill data for listview
		spindichvu = (Spinner) view.findViewById(R.id.spindichvu);

		// define btnsaleitem
		btnsaleitem = (Button) view.findViewById(R.id.btndathang);
		// review order item
		btnviewreviewoderitem = (Button) view
				.findViewById(R.id.btnxemhangdadat);
		btnviewreviewoderitem2 = (Button) view
				.findViewById(R.id.btnxemhangdadat2);
		// define text stock order code
		textstockordercode = (TextView) view
				.findViewById(R.id.texstockodercode);
		if (CacheData.getInstanse().getLisStockOrderDetails() != null) {
			ViewInfoAndOrderDal dal = new ViewInfoAndOrderDal(getActivity());
			try {
				dal.open();
				lstMergeAllService = dal.getStockStaffAndManager();
				dal.close();

				if (lstMergeAllService != null && lstMergeAllService.size() > 0) {
					if (CacheData.getInstanse().getLisStockOrderDetails() != null
							&& CacheData.getInstanse()
									.getLisStockOrderDetails().size() > 0) {
						for (int i = 0; i < CacheData.getInstanse()
								.getLisStockOrderDetails().size(); i++) {
							Log.d("stock_model_id", ""
									+ CacheData.getInstanse()
											.getLisStockOrderDetails().get(i)
											.getStockModelId());
							for (int j = 0; j < lstMergeAllService.size(); j++) {
								String stockmodelidStock = String
										.valueOf(CacheData.getInstanse()
												.getLisStockOrderDetails()
												.get(i).getStockModelId());
								String stockmodelidMerge = lstMergeAllService
										.get(j).get_stock_model_id();

								if (stockmodelidStock.equals(stockmodelidMerge)) {
									ViewInfoOjectMerge objectMerge = new ViewInfoOjectMerge();
									objectMerge.set_name(lstMergeAllService
											.get(j).get_name());
									objectMerge
											.set_quantity_issui_NV(lstMergeAllService
													.get(j)
													.get_quantity_issui_NV());
									objectMerge
											.set_quantity_issui_TH(lstMergeAllService
													.get(j)
													.get_quantity_issui_TH());
									objectMerge.set_soluong(CacheData
											.getInstanse()
											.getLisStockOrderDetails().get(i)
											.getQuantityOder());
									objectMerge
											.set_stock_model_id(stockmodelidMerge);
									objectMerge
											.set_priceMerge(lstMergeAllService
													.get(j).get_priceMerge());
									lisMerges.add(objectMerge);
									Log.d("price list merge", ""
											+ lisMerges.get(0).get_priceMerge());
								}
							}
						}
					}

				}
				Long countmoney = countMoney();
				textgiatrihangdat.setText(StringUtils.formatMoney(""
						+ countmoney));
			} catch (Exception e) {
				e.printStackTrace();
			}
			initSpinnerDichVu();
			// hugyugj
			if (CacheData.getInstanse().getStockOrderCode() != null
					&& !CacheData.getInstanse().getStockOrderCode().isEmpty()) {
				textstockordercode.setText(getResources().getString(
						R.string.idoderitem)
						+ " : " + CacheData.getInstanse().getStockOrderCode());
				linearbuttoncancelOder.setVisibility(View.VISIBLE);
			} else {
				textstockordercode.setText("");
				linearbuttonOderitem.setVisibility(View.VISIBLE);
			}
		}
		// run asyn get stock order id
		// new GetStockOrderManager(getActivity()).execute();
		// ====================tinh tong han muc===========================
		viewInfoDal = new ViewInfoAndOrderDal(getActivity());
		viewInfoDal.open();

		try {
			// ==date KM
			if (!viewInfoDal.checkdatekm()) {
				String debitMax = viewInfoDal.getMaxDebit();
				Log.d("debitMax", debitMax);
				if (debitMax == "") {
					String code = viewInfoDal.getCode();
					Log.d("code", code);
					String debitMaxCode = viewInfoDal.getmaxdebit(code);
					Log.d("debitMaxCode", debitMaxCode);
					txtviewtonghanmuc.setText(StringUtils
							.formatMoney(debitMaxCode));
					viewInfoDal.close();
				} else {
					txtviewtonghanmuc
							.setText(StringUtils.formatMoney(debitMax));
					viewInfoDal.close();
				}

			} else {
				// == date not KM
				String debitMaxNotKM = viewInfoDal.getMaxDebitNotKM();
				if (debitMaxNotKM.isEmpty()) {
					String codeNotKM = viewInfoDal.getCode();
					Log.d("codeNotKM", codeNotKM);
					String debitMaxCode = viewInfoDal
							.getmaxdebitNOTkm(codeNotKM);
					Log.d("debitMaxCode", debitMaxCode);
					txtviewtonghanmuc.setText(StringUtils
							.formatMoney(debitMaxCode));
					viewInfoDal.close();
				} else {
					txtviewtonghanmuc.setText(StringUtils
							.formatMoney(debitMaxNotKM));
					viewInfoDal.close();
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		// get stock order detail
		// GetStockOrderManager getStockOrderManager = new
		// GetStockOrderManager(getActivity());
		// getStockOrderManager.execute();

	}

	@Override
	public void onDestroy() {

		super.onDestroy();
	}

	@Override
	public void onDetach() {

		super.onDetach();
	}

	@Override
	public void onResume() {
        MainActivity.getInstance().setTitleActionBar(R.string.viewinfoandorder);
		super.onResume();
	}

	@Override
	public void onStart() {

		super.onStart();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnHome:
			CommonActivity.callphone(getActivity(), Constant.phoneNumber);
			break;
		case R.id.relaBackHome:
			CacheData.getInstanse().setLisInfoOjectMerges(null);
			CacheData.getInstanse().setStockOrderCode(null);
			getActivity().onBackPressed();
			break;
		default:
			break;
		}

	}

	@Override
	public void onChangeSoluongListener(ViewInfoOjectMerge viOjectMerge) {
		try {

			if (!isCheckReview) {
				// Neu dang o muc xem tat ca hang hoa cua dich vu,
				// duyet trong danh sach listViewInfoObjectMerges
				for (final ViewInfoOjectMerge item : lisViewInfoOjectMerges) {
					Long stock_id_item = Long.parseLong(item
							.get_stock_model_id());
					Long stock_id_object = Long.parseLong(viOjectMerge
							.get_stock_model_id());
					if (stock_id_item.compareTo(stock_id_object) == 0) {
						final Long soluongCB = Long.parseLong(item
								.get_quantity_issui_TH());
						final Dialog dialog = new Dialog(getActivity());
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
						dialog.setContentView(R.layout.sale_input_quantity_dialog_order2);
						// dialog.setTitle(getResources().getString(
						// R.string.salingQuantityOder));
						final TextView texteror = (TextView) dialog
								.findViewById(R.id.texterror);

						final EditText edtsoluong = (EditText) dialog
								.findViewById(R.id.edtQuantity);
						if (item.get_soluong() > 0) {
							edtsoluong.setText("" + item.get_soluong());
						}
						dialog.findViewById(R.id.btnOk).setOnClickListener(
								new OnClickListener() {
									@Override
									public void onClick(View arg0) {
										// WHEN CLICK OK
										String soluong = edtsoluong.getText()
												.toString().trim();
										if (soluong.equalsIgnoreCase("0")) {
											texteror.setVisibility(View.VISIBLE);
											texteror.setText(getResources()
													.getString(
															R.string.isorderthan));
											edtsoluong.setText("");
											return;
										}
										Log.d("soluong", soluong);
										if (!soluong.isEmpty()) {
											quantity_TH = Long
													.parseLong(soluong);
											Log.d("quantity_TH", quantity_TH
													+ "");
										} else {
											quantity_TH = 0L;
										}

										// check
										if (soluong.isEmpty()
												&& quantity_TH == 0L) {
											texteror.setVisibility(View.VISIBLE);
											texteror.setText(getResources()
													.getString(
															R.string.isorderthan));
											edtsoluong.setText("");
											return;
										}
										if (soluongCB < Long.parseLong(soluong)) {
											// So luong da nhap lon hon so luong
											// trong kho, khong cho thuc hien
											texteror.setVisibility(View.VISIBLE);
											texteror.setText(getResources()
													.getString(
															R.string.valueGreateThanStock));
											edtsoluong.setText("");
                                        }

										else {

											Long countmoney = countMoney()
													+ quantity_TH
													* item.get_priceMerge();
											Log.d("count money quantity order",
													"" + countmoney);
											if (countmoney > conlai) {
												texteror.setVisibility(View.VISIBLE);
												texteror.setText(getResources()
														.getString(
																R.string.valueGreateThanRemain));
												edtsoluong.setText("");
												return;
											}

											dialog.dismiss();
											item.set_soluong(quantity_TH);
											// lisMerges.add(item)
											// 1
											if (lisMerges.size() > 0) {
												Boolean isExists = false;
												for (int i = 0; i < lisMerges
														.size(); i++) {
													if (item.get_stock_model_id()
															.equals(lisMerges
																	.get(i)
																	.get_stock_model_id())) {
														lisMerges
																.get(i)
																.set_soluong(
																		item.get_soluong());
														isExists = true;

														break;
													}
												}
												if (!isExists) {
													lisMerges.add(item);
												}
											} else {
												lisMerges.add(item);
											}

											viewInFoAdapter
													.notifyDataSetChanged();

											Long countmoney2 = countMoney();
											Log.d("countmoney", ""
													+ countmoney2);
											if (countmoney2 > 0) {
												textgiatrihangdat.setText(StringUtils
														.formatMoney(""
																+ countmoney2));
											} else {
												textgiatrihangdat.setText(StringUtils
														.formatMoney(""));
											}

										}
									}
								});
						// cancel order item
						dialog.findViewById(R.id.btnViewSaleTrans)
								.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View arg0) {
										// CLICK CANCEL
										edtsoluong.setText("");
										dialog.dismiss();
									}
								});
						edtsoluong.requestFocus();
						dialog.getWindow().setSoftInputMode(
								LayoutParams.SOFT_INPUT_STATE_VISIBLE);
						dialog.show();
					}
				}
			} else {
				// Neu dang o muc xem danh sach mat hang da chon,
				// duyet trong danh sach listViewInfoObjectMerges
				for (final ViewInfoOjectMerge item : lisMerges) {
					Long stock_id_item = Long.parseLong(item
							.get_stock_model_id());
					Long stock_id_object = Long.parseLong(viOjectMerge
							.get_stock_model_id());
					if (stock_id_item.compareTo(stock_id_object) == 0) {
						final Long soluongCB = Long.parseLong(item
								.get_quantity_issui_TH());
						final Dialog dialog = new Dialog(getActivity());
						dialog.setContentView(R.layout.sale_input_quantity_dialog_order2);
						dialog.setTitle(getResources().getString(
								R.string.salingQuantityOder));
						final TextView texteror = (TextView) dialog
								.findViewById(R.id.texterror);

						final EditText edtsoluong = (EditText) dialog
								.findViewById(R.id.edtQuantity);
						if (item.get_soluong() > 0) {
							edtsoluong.setText("" + item.get_soluong());
						}
						dialog.findViewById(R.id.btnOk).setOnClickListener(
								new OnClickListener() {
									@Override
									public void onClick(View arg0) {
										// WHEN CLICK OK
										// long quantity_TH = 0L;
										// long countmone0y = 0L;
										String soluong = edtsoluong.getText()
												.toString().trim();
										if (soluong.equalsIgnoreCase("0")) {
											texteror.setVisibility(View.VISIBLE);
											texteror.setText(getResources()
													.getString(
															R.string.isorderthan));
											edtsoluong.setText("");
											return;
										}
										Log.d("soluong", soluong);
										if (!soluong.isEmpty()) {
											quantity_TH = Long
													.parseLong(soluong);
											Log.d("quantity_TH", quantity_TH
													+ "");
										} else {
											quantity_TH = 0L;
										}

										// check
										if (soluong.isEmpty()
												&& quantity_TH == 0L) {
											texteror.setVisibility(View.VISIBLE);
											texteror.setText(getResources()
													.getString(
															R.string.isorderthan));
											edtsoluong.setText("");
											return;
										}

										if (soluongCB < Long.parseLong(soluong)) {
											// So luong da nhap lon hon so luong
											// trong kho, khong cho thuc hien
											texteror.setVisibility(View.VISIBLE);
											texteror.setText(getResources()
													.getString(
															R.string.valueGreateThanStock));
											edtsoluong.setText("");
											return;
										}
										Long countmoney = countMoney()
												+ quantity_TH
												* item.get_priceMerge();
										Log.d("count money quantity order", ""
												+ countmoney);
										if (countmoney > conlai) {

											// check sum so luong dat = soluong
											// *
											// dongia
											texteror.setVisibility(View.VISIBLE);
											texteror.setText(getResources()
													.getString(
															R.string.valueGreateThanRemain));
											edtsoluong.setText("");
                                        } else {
											dialog.dismiss();
											item.set_soluong(quantity_TH);
											// lisMerges.add(item)
											// 1
											for (int i = 0; i < lisViewInfoOjectMerges
													.size(); i++) {
												if (item.get_stock_model_id()
														.equals(lisViewInfoOjectMerges
																.get(i)
																.get_stock_model_id())) {
													lisViewInfoOjectMerges.get(
															i).set_soluong(
															item.get_soluong());

													break;
												}
											}
											Long countmoney2 = countMoney();
											viewInFoAdapter
													.notifyDataSetChanged();
											Log.d("countmoney", ""
													+ countmoney2);
											if (countmoney2 > 0) {
												textgiatrihangdat.setText(StringUtils
														.formatMoney(""
																+ countmoney2));
											} else {
												textgiatrihangdat.setText(StringUtils
														.formatMoney(""));
											}

										}
									}
								});
						// cancel order item
						dialog.findViewById(R.id.btnViewSaleTrans)
								.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View arg0) {
										// CLICK CANCEL
										edtsoluong.setText("");
										dialog.dismiss();
									}
								});
						edtsoluong.requestFocus();
						dialog.getWindow().setSoftInputMode(
								LayoutParams.SOFT_INPUT_STATE_VISIBLE);
						dialog.show();
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onCancelOjectMergeListenner(ViewInfoOjectMerge objeOjectMerge) {
		for (ViewInfoOjectMerge item : lisViewInfoOjectMerges) {
			Long stock_id_item = Long.parseLong(item.get_stock_model_id());
			Long stock_id_object = Long.parseLong(objeOjectMerge
					.get_stock_model_id());
			if (stock_id_item.compareTo(stock_id_object) == 0) {
				// lisMerges.clear();
				// viewInFoAdapter.notifyDataSetChanged();
				textstockordercode.setText("");
				item.set_soluong(0L);
				break;
			}

		}
		for (int i = 0; i < lisMerges.size(); i++) {
			if (lisMerges.get(i).get_stock_model_id()
					.equals(objeOjectMerge.get_stock_model_id())) {
				lisMerges.remove(i);
			}
		}
		if (isCheckReview) {
			viewInFoAdapter.setLisInfoOjects(lisMerges);
		}
		viewInFoAdapter.notifyDataSetChanged();
		textgiatrihangdat.setText(StringUtils.formatMoney("" + countMoney()));
	}

	private Long countMoney() {
		try {
			Long result = 0L;
			for (ViewInfoOjectMerge item : lisMerges) {
				if (item.get_soluong() > 0) {
					result = result + item.get_soluong()
							* item.get_priceMerge();
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return 0L;
		}
	}

	// cancel order item
	public class CancelOrderManager extends AsyncTask<Void, Void, String> {
		// get stock order details

		final ProgressDialog progress;
		private Activity context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public CancelOrderManager(Activity context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setMessage(context.getResources().getString(
					R.string.cancelingItem));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected String doInBackground(Void... arg0) {
			return sendRequestCancelOrder();
		}

		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();
			if (result.equals("0")) {
				Dialog dialog = CommonActivity.createAlertDialog(
                        context,
						context.getResources().getString(
								R.string.cancelordersuccess),
						context.getResources().getString(
								R.string.cancelorderItem), checkcancelclick);
				dialog.show();
			} else {
				if (errorCode.equalsIgnoreCase(Constant.INVALID_TOKEN2)
						&& description != null && !description.isEmpty()) {
					// Dialog dialog = CommonActivity.createAlertDialog(
					// getActivity(), description, context.getResources()
					// .getString(R.string.cancelorderItem),
					// moveLogInAct);
					// dialog.show();
					LoginDialog dialog = new LoginDialog(context, permission);
					dialog.show();
                } else {
					Dialog dialog = CommonActivity.createAlertDialog(
                            context, result, context.getResources()
									.getString(R.string.cancelorderItem));
					dialog.show();
				}
			}
		}

		// call request getlist stock order
		public String sendRequestCancelOrder() {
			String original = null;
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_cancelOrderBccs2");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:cancelOrder>");
				rawData.append("<orderStockInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("</orderStockInput>");
				rawData.append("</ws:cancelOrder>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input
						.sendRequest(envelope, Constant.BCCS_GW_URL,
								getActivity(), "mbccs_cancelOrderBccs2");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				if (!output.getError().equals("0")) {
					return Constant.ERROR_CODE;
				}
				original = output.getOriginal();
				Log.d("originalllllllll", original);
				// ===========parser xml ===================
				// Document doc = parse.getDomElement(original);
				// NodeList nl = doc.getElementsByTagName("return");
				// for (int i = 0; i < nl.getLength(); i++) {
				// Element e2 = (Element) nl.item(i);
				// errorCode = parse.getValue(e2, "errorCode");
				// Log.d("erorCode", errorCode);
				// description = parse.getValue(e2, "description");
				// Log.d("description", description);
				// }

				VSAMenuHandler handler = (VSAMenuHandler) CommonActivity
						.parseXMLHandler(new VSAMenuHandler(), original);
				output = handler.getItem();
				errorCode = output.getErrorCode();
				description = output.getDescription();
				if (output.getToken() != null && !output.getToken().isEmpty()) {
					Session.setToken(output.getToken());
				}
				original = output.getErrorCode();
				if (!output.getErrorCode().equals("0")) {
					original = output.getDescription();
					// return Constant.ERROR_CODE;
				}
				if (output.getErrorCode().equals("0")) {
					original = output.getErrorCode();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return original;
		}

	}

	// ==== ucancel click =============
    private final OnClickListener checkcancelclick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// call Asyntask========
			for (int i = 0; i < lisViewInfoOjectMerges.size(); i++) {
				if (lisViewInfoOjectMerges.get(i).get_soluong() > 0) {
					lisViewInfoOjectMerges.get(i).set_soluong(0);
				}
			}
			lisMerges.clear();
			CacheData.getInstanse().setLisStockOrderDetails(null);
			CacheData.getInstanse().setStockOrderCode("");
			linearbuttoncancelOder.setVisibility(View.GONE);
			linearbuttonOderitem.setVisibility(View.VISIBLE);
			viewInFoAdapter.notifyDataSetChanged();
			textgiatrihangdat.setText("");
			textstockordercode.setText("");
		}
	};

	// oderitem manager

	public class OrderItemManager extends
			AsyncTask<ArrayList<ViewInfoOjectMerge>, Void, String> {

		final ProgressDialog progress;
		private Context context = null;
		private final ArrayList<ViewInfoOjectMerge> lisInfoOjectMerges;
		String errorCode = "";
		String description = "";
		final XmlDomParse parse = new XmlDomParse();

		public OrderItemManager(Context context,
				ArrayList<ViewInfoOjectMerge> arrObjects) {
			this.context = context;
			this.lisInfoOjectMerges = arrObjects;
			this.progress = new ProgressDialog(this.context);
			// check font

			this.progress.setMessage(context.getResources().getString(
					R.string.orderitem));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected String doInBackground(ArrayList<ViewInfoOjectMerge>... arg0) {
			return sendRequestOrderItem(lisInfoOjectMerges);
		}

		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();
			if (result.equals("0")) {
				Log.d("result", result);
				Dialog dialog = CommonActivity.createAlertDialog(
						(Activity) context,
						context.getResources().getString(
								R.string.saleitemsucess), context
								.getResources().getString(R.string.orderitem),
						checkhidelinearlayout);
				dialog.show();

			} else {
				if (errorCode.equalsIgnoreCase(Constant.INVALID_TOKEN2)
						&& description != null && !description.isEmpty()) {
					Dialog dialog = CommonActivity.createAlertDialog(
							(Activity) context, description,
							context.getResources()
									.getString(R.string.orderitem),
							moveLogInAct);
					dialog.show();
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							(Activity) context, result, context.getResources()
									.getString(R.string.orderitem));
					dialog.show();
				}
			}
		}

		// ==== update version click =============

		final OnClickListener checkhidelinearlayout = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// call Asyntask========
				linearbuttoncancelOder.setVisibility(View.VISIBLE);
				linearbuttonOderitem.setVisibility(View.GONE);
			}
		};

		public String sendRequestOrderItem(
				ArrayList<ViewInfoOjectMerge> lisInfoOjectMerges) {
			String original = null;
			String errorMessage = null;
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_requestOrderBccs2");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:requestOrder>");
				rawData.append("<orderStockInput>");
				HashMap<String, String> param = new HashMap<>();
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				if (lisInfoOjectMerges.size() > 0) {
					for (ViewInfoOjectMerge item : lisInfoOjectMerges) {
						HashMap<String, String> rawDataItem = new HashMap<>();
						rawDataItem.put("quantityOrder",
								"" + item.get_soluong());
						rawDataItem.put("stockModelId",
								item.get_stock_model_id());
						param.put("lstStockOrderDetail",
								input.buildXML(rawDataItem));
						rawData.append(input.buildXML(param));
					}
				}
				rawData.append("</orderStockInput>");
				rawData.append("</ws:requestOrder>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_requestOrderBccs2");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				// if (!output.getError().equals("0")) {
				// errorMessage = output.getDescription();
				// return Constant.ERROR_CODE;
				// }
				original = output.getOriginal();
				Log.d("originalllllllll", original);
				// ===========parser xml ===================
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("erorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
				}

				VSAMenuHandler handler = (VSAMenuHandler) CommonActivity
						.parseXMLHandler(new VSAMenuHandler(), original);
				output = handler.getItem();
				if (output.getToken() != null && !output.getToken().isEmpty()) {
					Session.setToken(output.getToken());
				}
				// original = output.getErrorCode();
				if (!output.getErrorCode().equals("0")) {
					original = output.getDescription();
				}
				if (output.getErrorCode().equals("0")) {
					original = output.getErrorCode();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return original;
		}

	}

	// move login
    private final OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
			getActivity().finish();

		}
	};

	// reitem manager

	public class OrderReItemManager extends
			AsyncTask<ArrayList<ViewInfoOjectMerge>, Void, String> {

		final ProgressDialog progress;
		private Context context = null;
		private final ArrayList<ViewInfoOjectMerge> lisInfoOjectMerges;
		String errorCode = "";
		String description = "";
		final XmlDomParse parse = new XmlDomParse();

		public OrderReItemManager(Context context,
				ArrayList<ViewInfoOjectMerge> arrObjects) {
			this.context = context;
			this.lisInfoOjectMerges = arrObjects;
			this.progress = new ProgressDialog(this.context);
			// check font

			this.progress.setMessage(context.getResources().getString(
					R.string.orderitem));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected String doInBackground(ArrayList<ViewInfoOjectMerge>... arg0) {
			return sendRequestOrderItem(lisInfoOjectMerges);
		}

		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();
			if (result.equals("0")) {
				Log.d("result", result);
				Dialog dialog = CommonActivity.createAlertDialog(
						(Activity) context,
						context.getResources().getString(
								R.string.saleitemsucess), context
								.getResources().getString(R.string.orderitem));
				dialog.show();
			} else {

				if (errorCode.equals(Constant.INVALID_TOKEN2)
						&& description != null && !description.isEmpty()) {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, context.getResources()
									.getString(R.string.orderitem),
							moveLogInAct);
					dialog.show();
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							(Activity) context, result, context.getResources()
									.getString(R.string.orderitem));
					dialog.show();
				}
			}
		}

		public String sendRequestOrderItem(
				ArrayList<ViewInfoOjectMerge> lisInfoOjectMerges) {
			String original = null;
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_retryRequestOrderBccs2");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:retryRequestOrder>");
				rawData.append("<orderStockInput>");
				HashMap<String, String> param = new HashMap<>();
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				if (lisInfoOjectMerges.size() > 0) {
					for (ViewInfoOjectMerge item : lisInfoOjectMerges) {
						HashMap<String, String> rawDataItem = new HashMap<>();
						rawDataItem.put("quantityOrder",
								"" + item.get_soluong());
						rawDataItem.put("stockModelId",
								item.get_stock_model_id());
						param.put("lstStockOrderDetail",
								input.buildXML(rawDataItem));
						rawData.append(input.buildXML(param));
					}
				}
				rawData.append("</orderStockInput>");
				rawData.append("</ws:retryRequestOrder>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_retryRequestOrderBccs2");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				if (!output.getError().equals("0")) {
					return Constant.ERROR_CODE;
				}
				original = output.getOriginal();
				Log.d("originalllllllll", original);
				// ===========parser xml ===================
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("erorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
				}

				VSAMenuHandler handler = (VSAMenuHandler) CommonActivity
						.parseXMLHandler(new VSAMenuHandler(), original);
				output = handler.getItem();
				if (output.getToken() != null && !output.getToken().isEmpty()) {
					Session.setToken(output.getToken());
				}
				if (!output.getErrorCode().equals("0")) {
					original = output.getDescription();
				}
				if (output.getErrorCode().equals("0")) {
					original = output.getErrorCode();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return original;
		}

	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		arg0.getParent().requestDisallowInterceptTouchEvent(true);
		return false;
	}

}
