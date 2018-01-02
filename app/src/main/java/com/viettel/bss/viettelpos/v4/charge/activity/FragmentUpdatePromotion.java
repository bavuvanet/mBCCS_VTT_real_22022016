package com.viettel.bss.viettelpos.v4.charge.activity;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.adapter.ContractPromotionAdapter;
import com.viettel.bss.viettelpos.v4.charge.handler.SubscriberHandle;
import com.viettel.bss.viettelpos.v4.charge.object.ContractPromotionObj;
import com.viettel.bss.viettelpos.v4.charge.object.GroupBeans;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.TelecomServiceBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetServiceDal;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FragmentUpdatePromotion extends Fragment implements
		OnClickListener, OnItemClickListener {
	private int MAX_RECORD_PER_PAGE = 50;
	private Activity activity;
	private View mView;
	private Spinner spinner_branch;
	private Spinner spinner_plane_charge;
	private Spinner spinner_group_charge;
	private Spinner spinner_manager_ctv;
	private Spinner spinner_ctv;
	private Spinner spinner_service;
	// private EditText edt_number_date_promotion;
	private Spinner spiMonth;
    private ContractPromotionObj curentContractPromotion;

	private Button btnHome;
	private TextView txtNameActionBar;

    private final int REQUEST_TYPE_BRANCH = 0;
	private final int REQUEST_CHARGE_BOARD = 1;
	private final int REQUEST_GROUP_CHARGE = 2;

    private final ArrayList<Spin> arrBranch = new ArrayList<>();
	private ArrayList<Spin> arrChargeBoard = new ArrayList<>();
	private ArrayList<Spin> arrChargeGroup = new ArrayList<>();
	private ArrayList<Spin> arrManagerCTV = new ArrayList<>();
	private ArrayList<Spin> arrCTV = new ArrayList<>();
	private final ArrayList<Spin> arrListReasoon = new ArrayList<>();

	private ArrayList<TelecomServiceBeans> arrTelecomServiceBeans = new ArrayList<>();

	private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private Date toDate = null;
	private String ctvCode = Session.userName;
	private Dialog dialogShowListContact;
	private ContractPromotionAdapter contractPromotionAdapter;
	private ArrayList<ContractPromotionObj> arrContractPromotionObj = new ArrayList<ContractPromotionObj>();
	private boolean loadmore = false;
	private EditText edtIsdnAccount;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		activity = getActivity();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (mView == null) {
			mView = inflater.inflate(R.layout.layout_update_promotion,
					container, false);
			spiMonth = (Spinner) mView.findViewById(R.id.spiMonth);
			edtIsdnAccount = (EditText) mView.findViewById(R.id.edtIsdnAccount);
			initMonth();
            LinearLayout linearBranch = (LinearLayout) mView.findViewById(R.id.linearBranch);
            LinearLayout linearChargeBoard = (LinearLayout) mView
                    .findViewById(R.id.linearChargeBoard);
            LinearLayout linearChargeGroup = (LinearLayout) mView
                    .findViewById(R.id.linearChargeGroup);
            LinearLayout linearManagerCTV = (LinearLayout) mView
                    .findViewById(R.id.linearManagerCTV);
            LinearLayout linearCTV = (LinearLayout) mView.findViewById(R.id.linearCTV);

			spinner_branch = (Spinner) mView.findViewById(R.id.spinner_branch);
			spinner_plane_charge = (Spinner) mView
					.findViewById(R.id.spinner_plane_charge);
			spinner_group_charge = (Spinner) mView
					.findViewById(R.id.spinner_group_charge);
			spinner_manager_ctv = (Spinner) mView
					.findViewById(R.id.spinner_manager_ctv);
			spinner_ctv = (Spinner) mView.findViewById(R.id.spinner_ctv);
			spinner_service = (Spinner) mView
					.findViewById(R.id.spinner_service);
            Button btn_search = (Button) mView.findViewById(R.id.btn_search);
			btn_search.setOnClickListener(this);
			SharedPreferences preferences = getActivity().getSharedPreferences(
					Define.PRE_NAME, Activity.MODE_PRIVATE);
			String name = preferences.getString(Define.KEY_MENU_NAME, "");
			// quyen chon chi nhanh
			if (name.contains(";menu_select_branch;")) {
				linearBranch.setVisibility(View.VISIBLE);
				linearChargeBoard.setVisibility(View.VISIBLE);
				linearChargeGroup.setVisibility(View.VISIBLE);
				linearManagerCTV.setVisibility(View.VISIBLE);
				linearCTV.setVisibility(View.VISIBLE);
				// =========== lay danh sach don vi ================
				if (CommonActivity.isNetworkConnected(getActivity())) {
					GetCollectionGroupByParentAsyn getCollectionGroupByParentAsyn = new GetCollectionGroupByParentAsyn(
							getActivity(), REQUEST_TYPE_BRANCH);
					getCollectionGroupByParentAsyn.execute("" + 0);
				} else {
					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.errorNetwork),
							getString(R.string.app_name)).show();
				}

			} else {
				linearBranch.setVisibility(View.GONE);
				// quyen chon ban cuoc
				if (name.contains(";menu_select_charge_board;")) {

					// TODO XU LY LAY THONG TIN DON VI VA BAN CUOC
					GetGroupByStaffAsyn getGroupByStaffAsyn = new GetGroupByStaffAsyn(
							getActivity());
					getGroupByStaffAsyn.execute();

					linearChargeBoard.setVisibility(View.VISIBLE);
					linearChargeGroup.setVisibility(View.VISIBLE);
					linearManagerCTV.setVisibility(View.VISIBLE);
					linearCTV.setVisibility(View.VISIBLE);
				} else {
					linearChargeBoard.setVisibility(View.GONE);
					// quyen chon to thu

					if (name.contains(";menu_select_collectionunit;")) {

						// TODO XU LY LAY THONG TIN DON VI VA TO THU
						GetGroupByStaffAsyn getGroupByStaffAsyn = new GetGroupByStaffAsyn(
								getActivity());
						getGroupByStaffAsyn.execute();

						linearChargeGroup.setVisibility(View.VISIBLE);
						linearManagerCTV.setVisibility(View.VISIBLE);
						linearCTV.setVisibility(View.VISIBLE);
					} else {
						linearChargeGroup.setVisibility(View.GONE);

						// quyen chon quan ly ctv
						if (name.contains(";menu_select_manage_collaborators;")) {
							GetGroupByStaffAsyn getGroupByStaffAsyn1 = new GetGroupByStaffAsyn(
									getActivity());
							getGroupByStaffAsyn1.execute();
							linearManagerCTV.setVisibility(View.VISIBLE);
							linearCTV.setVisibility(View.VISIBLE);
						} else {
							linearManagerCTV.setVisibility(View.GONE);

							// quyen chon ctv
							if (name.contains(";menu_select_collaborators;")) {
								GetStaffByMngtAsyn getStaffByMngtAsyn = new GetStaffByMngtAsyn(
										getActivity());
								getStaffByMngtAsyn.execute(Session.userName);
								linearCTV.setVisibility(View.VISIBLE);
							} else {
								linearCTV.setVisibility(View.GONE);
							}
						}
					}
				}
			}
		}

		// ================ lay danh sach dich vu
		if (arrTelecomServiceBeans != null && !arrTelecomServiceBeans.isEmpty()) {
			arrTelecomServiceBeans = new ArrayList<>();
		}
		// initTelecomService();
		// ================chon chi nhanh=======================
		spinner_branch.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
					int arg2, long arg3) {
				Spin item = (Spin) parent.getItemAtPosition(arg2);

				if (item.getId().equals("-1")) {
					reloadSelectBranch();
				} else {
					// reloadSelectBranch();
					// TODO LAY DANH SACH BAN CUOC
					if (CommonActivity.isNetworkConnected(getActivity())) {
						GetCollectionGroupByParentAsyn getCollectionGroupByParentAsyn = new GetCollectionGroupByParentAsyn(
								getActivity(), REQUEST_CHARGE_BOARD);
						getCollectionGroupByParentAsyn.execute(""
								+ item.getId());
					} else {
						CommonActivity.createAlertDialog(getActivity(),
								getString(R.string.errorNetwork),
								getString(R.string.app_name)).show();
					}
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		// ==================== chon ban cuoc====================
		spinner_plane_charge
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent,
							View arg1, int arg2, long arg3) {
						Spin item = (Spin) parent.getItemAtPosition(arg2);

						if (item.getId().equalsIgnoreCase("-1")) {
							reloadSelectGroupCharge();
						} else {
							// reloadSelectGroupCharge();
							// TODO LAY DANH SACH TO THU
							if (CommonActivity
									.isNetworkConnected(getActivity())) {
								GetCollectionGroupByParentAsyn getCollectionGroupByParentAsyn = new GetCollectionGroupByParentAsyn(
										getActivity(), REQUEST_GROUP_CHARGE);
								getCollectionGroupByParentAsyn.execute(""
										+ item.getId());
							} else {
								CommonActivity.createAlertDialog(getActivity(),
										getString(R.string.errorNetwork),
										getString(R.string.app_name)).show();
							}

						}

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});
		// ================== chon to thu==========================
		spinner_group_charge
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent,
							View arg1, int arg2, long arg3) {
						Spin item = (Spin) parent.getItemAtPosition(arg2);

						if (item.getId().equals("-1")) {
							reloadSelectMngCTV();
						} else {
							// reloadSelectMngCTV();
							// lay danh sach quan ly ctv
							if (CommonActivity
									.isNetworkConnected(getActivity())) {

								GetMngtInGroupAsyn getMngtInGroupAsyn = new GetMngtInGroupAsyn(
										getActivity());
								getMngtInGroupAsyn.execute(item.getId());
							} else {
								CommonActivity.createAlertDialog(getActivity(),
										getString(R.string.errorNetwork),
										getString(R.string.app_name)).show();
							}
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});
		// ================chon quan ly cong tac vien===============
		spinner_manager_ctv
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent,
							View arg1, int arg2, long arg3) {
						Spin item = (Spin) parent.getItemAtPosition(arg2);

						if (item.getId().equals("-1")) {
							reloadSelectCTV();
						} else {
							// reloadSelectCTV();
							if (CommonActivity
									.isNetworkConnected(getActivity())) {
								GetStaffByMngtAsyn getStaffByMngtAsyn = new GetStaffByMngtAsyn(
										getActivity());
								getStaffByMngtAsyn.execute(item.getId());
							} else {
								CommonActivity.createAlertDialog(getActivity(),
										getString(R.string.errorNetwork),
										getString(R.string.app_name)).show();
							}
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});
		// ========================chon cong tac vien==========================
		spinner_ctv.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
					int arg2, long arg3) {
				Spin item = (Spin) parent.getItemAtPosition(arg2);
				// TODO XU LY LAY MA CONG TAC VIEN DE TIM DANH SACH THONG TIN
				// THUE BAO
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		return mView;
	}

	// reload khi chon chi nhanh
	private void reloadSelectBranch() {
		// reload ban cuoc
		arrChargeBoard = new ArrayList<>();
		arrChargeBoard.add(0, new Spin("-1", getString(R.string.chonbancuoc)));
		Utils.setDataSpinner(activity, arrChargeBoard, spinner_plane_charge);
		// reload to thu
		arrChargeGroup = new ArrayList<>();
		arrChargeGroup.add(0, new Spin("-1", getString(R.string.chontothu)));
		Utils.setDataSpinner(activity, arrChargeGroup, spinner_group_charge);
		// reload quan ly ctv
		arrManagerCTV = new ArrayList<>();
		arrManagerCTV.add(0, new Spin("-1", getString(R.string.chonqlctv)));
		Utils.setDataSpinner(activity, arrManagerCTV, spinner_manager_ctv);
		// reaload ctv
		arrCTV = new ArrayList<>();
		arrCTV.add(0, new Spin("-1", getString(R.string.chonctv)));
		Utils.setDataSpinner(activity, arrCTV, spinner_ctv);
	}

	// reload khi chon ban cuoc
	private void reloadSelectGroupCharge() {
		// reload to thu
		arrChargeGroup = new ArrayList<>();
		arrChargeGroup.add(0, new Spin("-1", getString(R.string.chontothu)));
		Utils.setDataSpinner(activity, arrChargeGroup, spinner_group_charge);
		// reload quan ly ctv
		arrManagerCTV = new ArrayList<>();
		arrManagerCTV.add(0, new Spin("-1", getString(R.string.chonqlctv)));
		Utils.setDataSpinner(activity, arrManagerCTV, spinner_manager_ctv);
		// reaload ctv
		arrCTV = new ArrayList<>();
		arrCTV.add(0, new Spin("-1", getString(R.string.chonctv)));
		Utils.setDataSpinner(activity, arrCTV, spinner_ctv);
	}

	// reload khi chon to thu
	private void reloadSelectMngCTV() {
		// reload quan ly ctv
		arrManagerCTV = new ArrayList<>();
		arrManagerCTV.add(0, new Spin("-1", getString(R.string.chonqlctv)));
		Utils.setDataSpinner(activity, arrManagerCTV, spinner_manager_ctv);
		// reaload ctv
		arrCTV = new ArrayList<>();
		arrCTV.add(0, new Spin("-1", getString(R.string.chonctv)));
		Utils.setDataSpinner(activity, arrCTV, spinner_ctv);
	}

	// reload khi chon quan ly CTV
	private void reloadSelectCTV() {

		// reaload ctv
		arrCTV = new ArrayList<>();
		arrCTV.add(0, new Spin("-1", getString(R.string.chonctv)));
		Utils.setDataSpinner(activity, arrCTV, spinner_ctv);
	}

	// lay dich vu
	private void initTelecomService() {
		GetServiceDal dal = new GetServiceDal(getActivity());
		try {
			dal.open();
			arrTelecomServiceBeans = dal.getlisTelecomServiceBeans();
			if (arrTelecomServiceBeans != null
					&& !arrTelecomServiceBeans.isEmpty()) {
				ArrayList<Spin> lstService = new ArrayList<>();
				for (TelecomServiceBeans telecomServiceBeans : arrTelecomServiceBeans) {
					Spin item = new Spin();
					if ("M".equalsIgnoreCase(telecomServiceBeans
							.getServiceAlias())) {

					} else {
						item.setValue(telecomServiceBeans
								.getTele_service_name());
						item.setId(telecomServiceBeans.getServiceAlias());
						lstService.add(item);
					}
				}
				Utils.setDataSpinner(activity, lstService, spinner_service);
			}
		} catch (Exception e) {
			Log.e("initTelecomService", e.toString());
		} finally {
			dal.close();
		}
	}

	private boolean validateSearch() {
		Spin subItem = (Spin) spinner_ctv.getSelectedItem();
		if (subItem.getId().equals("-1")) {
			Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
					getResources().getString(R.string.checkctv), getResources()
							.getString(R.string.app_name));
			dialog.show();
			return false;
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_search:
			arrContractPromotionObj.clear();
			SharedPreferences preferences = getActivity().getSharedPreferences(
					Define.PRE_NAME, Activity.MODE_PRIVATE);
			String name = preferences.getString(Define.KEY_MENU_NAME, "");

			if (!name.contains(";menu_select_branch;")
					&& !name.contains(";menu_select_charge_board;")
					&& !name.contains(";menu_select_collectionunit;")
					&& !name.contains(";menu_select_manage_collaborators;")
					&& !name.contains(";menu_select_collaborators;")) {
				ctvCode = Session.userName.toUpperCase();

				// if (CommonActivity.isNullOrEmpty(edt_number_date_promotion
				// .getText().toString())) {
				// Dialog dialog = CommonActivity
				// .createAlertDialog(getActivity(), getResources()
				// .getString(R.string.checkngaykmempty),
				// getResources().getString(R.string.app_name));
				// dialog.show();
				// } else {
				GetListSubPromotionAsyn getListSubPromotionAsyn = new GetListSubPromotionAsyn(
						getActivity());
				getListSubPromotionAsyn.execute(ctvCode);
				// }
			} else {
				if (validateSearch()) {
					Spin subItem = (Spin) spinner_ctv.getSelectedItem();
					GetListSubPromotionAsyn getListSubPromotionAsyn = new GetListSubPromotionAsyn(
							getActivity());
					ctvCode = subItem.getId();
					getListSubPromotionAsyn.execute(ctvCode);
				}
			}
			// showDialogListContract();
			break;
		case R.id.btnHome:
			CommonActivity.callphone(activity, Constant.phoneNumber);
			break;
		case R.id.relaBackHome:
			activity.onBackPressed();
			break;

		default:
			break;
		}
	}

	// move login
    private final OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
			getActivity().finish();
			MainActivity.getInstance().finish();

		}
	};

	@Override
	public void onResume() {
		super.onResume();
        MainActivity.getInstance().setTitleActionBar(R.string.title_menu_update_promotion);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		curentContractPromotion = (ContractPromotionObj) parent.getAdapter()
				.getItem(position);
		showDialogContractPromotionDetail();
	}

	private void showDialogListContract() {
		//
		if (dialogShowListContact != null) {
			dialogShowListContact.show();
			contractPromotionAdapter.notifyDataSetChanged();
			return;
		}
		dialogShowListContact = new Dialog(activity);
		dialogShowListContact.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogShowListContact
				.setContentView(R.layout.dialog_list_contract_promotion);

		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(dialogShowListContact.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.MATCH_PARENT;
		dialogShowListContact.getWindow().setAttributes(lp);

		dialogShowListContact.setCancelable(false);
        ListView listViewContract = (ListView) dialogShowListContact
                .findViewById(R.id.listViewContract);
		contractPromotionAdapter = new ContractPromotionAdapter(
				arrContractPromotionObj, activity);
		listViewContract.setAdapter(contractPromotionAdapter);
		listViewContract.setOnItemClickListener(this);
		listViewContract.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				int lastitemScreen = firstVisibleItem + visibleItemCount;
				if ((lastitemScreen == totalItemCount) && (loadmore)
						&& totalItemCount > 0) {
					loadmore = false;
					GetListSubPromotionAsyn getListSubPromotionAsyn = new GetListSubPromotionAsyn(
							getActivity());
					getListSubPromotionAsyn.execute(ctvCode);
				}
			}
		});
		Button btnClose = (Button) dialogShowListContact
				.findViewById(R.id.btnClose);
		// Log.d("Log", "Count contract promotion: " + listContract.size());

		btnClose.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialogShowListContact.dismiss();
			}
		});
		dialogShowListContact.show();
	}

	private Dialog dialogContractDetail;
	private Spinner spinnerReasoon;

	private void showDialogContractPromotionDetail() {
		//
		dialogContractDetail = new Dialog(activity);
		dialogContractDetail.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogContractDetail
				.setContentView(R.layout.layout_dialog_contract_promotion_detail);

		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(dialogContractDetail.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.MATCH_PARENT;
		dialogContractDetail.getWindow().setAttributes(lp);
		dialogContractDetail.setCancelable(false);

		TextView tvCustomerName = (TextView) dialogContractDetail
				.findViewById(R.id.tvCustomerName);
		TextView tvContractNo = (TextView) dialogContractDetail
				.findViewById(R.id.tvContractNo);
		TextView tvAddressTBC = (TextView) dialogContractDetail
				.findViewById(R.id.tvAddressTBC);
		TextView tvPromotionName = (TextView) dialogContractDetail
				.findViewById(R.id.tvPromotionName);
		TextView tvStartDate = (TextView) dialogContractDetail
				.findViewById(R.id.tvStartDate);
		TextView tvEndDate = (TextView) dialogContractDetail
				.findViewById(R.id.tvEndDate);
		// TextView tvNumberDatePromotion = (TextView) dialogContractDetail
		// .findViewById(R.id.tvNumberDatePromotion);

		TextView tvPromotionIsdn = (TextView) dialogContractDetail
				.findViewById(R.id.tvPromotionIsdn);
		TextView tvPromotionCode = (TextView) dialogContractDetail
				.findViewById(R.id.tvPromotionCode);

		spinnerReasoon = (Spinner) dialogContractDetail
				.findViewById(R.id.spinnerReasoon);
		Button btnUpdate = (Button) dialogContractDetail
				.findViewById(R.id.btn_update);
		Button btnCancel = (Button) dialogContractDetail
				.findViewById(R.id.btn_cancel);

		tvCustomerName.setText(curentContractPromotion.getCustomerName());
		tvContractNo.setText(curentContractPromotion.getContractCode());
		tvPromotionName.setText(curentContractPromotion.getPromotionName());
		tvStartDate.setText(curentContractPromotion.getStartTimePromotions());
		tvEndDate.setText(curentContractPromotion.getEndTimePromotions());
		tvPromotionCode.setText(curentContractPromotion.getPromotionCode());
		tvAddressTBC.setText(curentContractPromotion.getAddressTBC());
		tvPromotionIsdn.setText(curentContractPromotion.getIsdn());

		AsyntaskGetReasonPos asyncTaskGetReasoonPost = new AsyntaskGetReasonPos(
				activity);
		asyncTaskGetReasoonPost.execute();

		Date datenow = null;
		try {

			Calendar cal = Calendar.getInstance();

			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			datenow = cal.getTime();

			toDate = sdf.parse(curentContractPromotion.getEndTimePromotions());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Long number = 0L;
		//
		// if (toDate.after(new Date())) {
		// number = getDateDiff(datenow.getTime(), toDate.getTime()) + 1;
		// } else {
		// number = getDateDiff(toDate.getTime(), datenow.getTime()) + 1;
		// }
		//
		// tvNumberDatePromotion.setText(number + "");

		btnCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialogContractDetail.dismiss();
			}
		});

		btnUpdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Spin serviceItem = (Spin) spinnerReasoon.getSelectedItem();

				if (serviceItem != null) {
					if (serviceItem.getId().equals("-1")) {
						CommonActivity.createAlertDialog(getActivity(),
								getString(R.string.checkreasonCN),
								getString(R.string.app_name)).show();
					} else {

						if (CommonActivity.isNetworkConnected(getActivity())) {

						} else {
							CommonActivity.createAlertDialog(getActivity(),
									getString(R.string.errorNetwork),
									getString(R.string.app_name)).show();
						}

						CommonActivity.createDialog(getActivity(),
								getString(R.string.confirmUpdate),
								getString(R.string.app_name),
								getString(R.string.alert_dialog_no),
								getString(R.string.alert_dialog_ok ),
								null,updateSubAsyn ).show();
					}
				} else {
					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.checkreasonCN),
							getString(R.string.app_name)).show();
				}

			}
		});

		dialogContractDetail.show();

	}

	private final OnClickListener updateSubAsyn = new OnClickListener() {

		@Override
		public void onClick(View arg0) {

			Spin serviceItem = (Spin) spinnerReasoon.getSelectedItem();
			UpdatePromotionAsyn updatePromotionAsyn = new UpdatePromotionAsyn(
					getActivity());
			updatePromotionAsyn.execute(serviceItem.getId());
		}
	};

	// lay thong tin don vi tu staff_code
	private class GetGroupByStaffAsyn extends
			AsyncTask<String, Void, GroupBeans> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetGroupByStaffAsyn(Context context) {
			this.context = context;
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
		protected GroupBeans doInBackground(String... arg0) {
			return getListGroupByStaff();
		}

		@Override
		protected void onPostExecute(GroupBeans result) {
			progress.dismiss();
			if (errorCode.equalsIgnoreCase("0")) {

                // TODO xu ly goi lay thong ban cuoc , hoac to thu
				SharedPreferences preferences = getActivity()
						.getSharedPreferences(Define.PRE_NAME,
								Activity.MODE_PRIVATE);
				String name = preferences.getString(Define.KEY_MENU_NAME, "");
				if (name.contains(";menu_select_charge_board;")) {
					if (result != null
							&& !CommonActivity.isNullOrEmpty(result
									.getBranchId())) {
						GetCollectionGroupByParentAsyn getCollectionGroupByParentAsyn = new GetCollectionGroupByParentAsyn(
								getActivity(), REQUEST_CHARGE_BOARD);
						getCollectionGroupByParentAsyn.execute(result
								.getBranchId());
					} else {
						CommonActivity.createAlertDialog(getActivity(),
								getString(R.string.checkbrach),
								getString(R.string.app_name)).show();
					}

				}

				if (name.contains(";menu_select_collectionunit;")) {

					if (result != null
							&& !CommonActivity.isNullOrEmpty(result
									.getSubBranchId())) {
						GetCollectionGroupByParentAsyn getCollectionGroupByParentAsyn = new GetCollectionGroupByParentAsyn(
								getActivity(), REQUEST_GROUP_CHARGE);
						getCollectionGroupByParentAsyn.execute(result
								.getSubBranchId());
					} else {
						CommonActivity.createAlertDialog(getActivity(),
								getString(R.string.checkSubBranch),
								getString(R.string.app_name)).show();
					}

				}
				if (name.contains(";menu_select_manage_collaborators;")) {
					if (result != null
							&& !CommonActivity.isNullOrEmpty(result
									.getGroupId())) {
						GetMngtInGroupAsyn getMngtInGroupAsyn = new GetMngtInGroupAsyn(
								getActivity());
						getMngtInGroupAsyn.execute(result.getGroupId());
					} else {
						CommonActivity.createAlertDialog(getActivity(),
								getString(R.string.checkGroupCharge),
								getString(R.string.app_name)).show();
					}

				}

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

		private GroupBeans getListGroupByStaff() {

			GroupBeans groupBeans = new GroupBeans();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getGroupByStaff");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getGroupByStaff>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("</input>");
				rawData.append("</ws:getGroupByStaff>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getGroupByStaff");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original group", response);

				// ==== lay thong tin don vi tu staffCode
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					nodechild = doc.getElementsByTagName("staffBean");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						groupBeans.setBranchId(parse.getValue(e1, "branchId"));
						groupBeans.setCollaborator(parse.getValue(e1,
								"collaborator"));
						groupBeans.setGroupId(parse.getValue(e1, "groupId"));
						groupBeans.setName(parse.getValue(e1, "name"));
						groupBeans
								.setStaffCode(parse.getValue(e1, "staffCode"));
						groupBeans.setSubBranchId(parse.getValue(e1,
								"subBranchId"));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return groupBeans;
		}
	}

	// ===== lay sanh sach don vi theo don vi cha parentId . lay ban cuoc, to
	// thu theo groupId
	private class GetCollectionGroupByParentAsyn extends
			AsyncTask<String, Void, ArrayList<Spin>> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		int type = 0;

		public GetCollectionGroupByParentAsyn(Context context, int type) {
			this.context = context;
			this.type = type;
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
		protected ArrayList<Spin> doInBackground(String... arg0) {
			return getListBranch(arg0[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<Spin> result) {
			progress.dismiss();
			if (errorCode.equalsIgnoreCase("0")) {
				// lay chi nhanh
				if (type == REQUEST_TYPE_BRANCH) {
					result.add(0, new Spin("-1",
							getString(R.string.chonchinhanh)));
					arrBranch.addAll(result);
					Utils.setDataSpinner(activity, arrBranch, spinner_branch);
				}

				// lay ban cuoc
				if (type == REQUEST_CHARGE_BOARD) {
					result.add(0, new Spin("-1",
							getString(R.string.chonbancuoc)));
					arrChargeBoard = new ArrayList<>();
					arrChargeBoard.addAll(result);
					Utils.setDataSpinner(activity, arrChargeBoard,
							spinner_plane_charge);
				}

				// lay to thu
				if (type == REQUEST_GROUP_CHARGE) {
					result.add(0, new Spin("-1", getString(R.string.chontothu)));
					arrChargeGroup = new ArrayList<>();
					arrChargeGroup.addAll(result);
					Utils.setDataSpinner(activity, arrChargeGroup,
							spinner_group_charge);
				}

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

					// lay chi nhanh
					if (type == REQUEST_TYPE_BRANCH) {
						result.add(0, new Spin("-1",
								getString(R.string.chonchinhanh)));
						arrBranch.addAll(result);
						Utils.setDataSpinner(activity, arrBranch,
								spinner_branch);
					}

					// lay ban cuoc
					if (type == REQUEST_CHARGE_BOARD) {
						result.add(0, new Spin("-1",
								getString(R.string.chonbancuoc)));
						arrChargeBoard = new ArrayList<>();
						arrChargeBoard.addAll(result);
						Utils.setDataSpinner(activity, arrChargeBoard,
								spinner_plane_charge);
					}

					// lay to thu
					if (type == REQUEST_GROUP_CHARGE) {
						result.add(0, new Spin("-1",
								getString(R.string.chontothu)));
						arrChargeGroup = new ArrayList<>();
						arrChargeGroup.addAll(result);
						Utils.setDataSpinner(activity, arrChargeGroup,
								spinner_group_charge);
					}

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

		private ArrayList<Spin> getListBranch(String parentId) {

			ArrayList<Spin> listBranch = new ArrayList<>();
			String original = "";
			try {

				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getCollectionGroupByParent");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getCollectionGroupByParent>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<parentId>").append(parentId).append("</parentId>");
				rawData.append("</input>");
				rawData.append("</ws:getCollectionGroupByParent>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getCollectionGroupByParent");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original group", response);

				// ==== parse xml list ip
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					nodechild = doc.getElementsByTagName("lstGroupBean");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						Spin spin = new Spin();
						spin.setValue(parse.getValue(e1, "name"));
						Log.d("LOG", "value: " + spin.getValue());
						spin.setId(parse.getValue(e1, "groupId"));
						Log.d("LOG", "Idddd: " + spin.getId());
						listBranch.add(spin);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return listBranch;
		}
	}

	// ws lay danh sach quan ly nhan vien

	private class GetMngtInGroupAsyn extends
			AsyncTask<String, Void, ArrayList<Spin>> {
		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetMngtInGroupAsyn(Context context) {
			this.context = context;
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
		protected ArrayList<Spin> doInBackground(String... arg0) {
			return getMngtInGroup(arg0[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<Spin> result) {
			progress.dismiss();
			if (errorCode.equalsIgnoreCase("0")) {
				result.add(0, new Spin("-1", getString(R.string.chonqlctv)));
				arrManagerCTV = new ArrayList<>();
				arrManagerCTV.addAll(result);
				Utils.setDataSpinner(activity, arrManagerCTV,
						spinner_manager_ctv);

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

					result.add(0, new Spin("-1", getString(R.string.chonqlctv)));
					arrManagerCTV = new ArrayList<>();
					arrManagerCTV.addAll(result);
					Utils.setDataSpinner(activity, arrManagerCTV,
							spinner_manager_ctv);

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

		private ArrayList<Spin> getMngtInGroup(String parentId) {

			ArrayList<Spin> listBranch = new ArrayList<>();
			String original = "";
			try {

				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getMngtInGroup");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getMngtInGroup>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<parentId>").append(parentId).append("</parentId>");
				rawData.append("</input>");
				rawData.append("</ws:getMngtInGroup>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getMngtInGroup");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original group", response);

				// ==== parse xml list ip
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					nodechild = doc.getElementsByTagName("lstStaffBean");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						Spin spin = new Spin();

						spin.setId(parse.getValue(e1, "staffCode"));
						Log.d("LOG", "Idddd: " + spin.getId());
						spin.setValue(parse.getValue(e1, "staffCode") + "-"
								+ parse.getValue(e1, "name"));
						Log.d("LOG", "value: " + spin.getValue());
						listBranch.add(spin);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return listBranch;
		}

	}

	// ======ws lay danh sach nhan vien tu quan ly============

	private class GetStaffByMngtAsyn extends
			AsyncTask<String, Void, ArrayList<Spin>> {
		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetStaffByMngtAsyn(Context context) {
			this.context = context;
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
		protected ArrayList<Spin> doInBackground(String... arg0) {
			return getMngtInGroup(arg0[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<Spin> result) {
			progress.dismiss();
			if (errorCode.equalsIgnoreCase("0")) {
				result.add(0, new Spin("-1", getString(R.string.chonctv)));
				arrCTV = new ArrayList<>();
				arrCTV.addAll(result);
				Utils.setDataSpinner(activity, arrCTV, spinner_ctv);

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

					result.add(0, new Spin("-1", getString(R.string.chonctv)));
					arrCTV = new ArrayList<>();
					arrCTV.addAll(result);
					Utils.setDataSpinner(activity, arrCTV, spinner_ctv);

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

		private ArrayList<Spin> getMngtInGroup(String staffId) {

			ArrayList<Spin> listBranch = new ArrayList<>();
			String original = "";
			try {

				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getStaffByMngt");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getStaffByMngt>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<staffCode>").append(staffId).append("</staffCode>");
				rawData.append("</input>");
				rawData.append("</ws:getStaffByMngt>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getStaffByMngt");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original group", response);

				// ==== parse xml list ip
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					nodechild = doc.getElementsByTagName("lstStaffBeanMgnt");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						Spin spin = new Spin();

						spin.setId(parse.getValue(e1, "staffCode"));
						Log.d("LOG", "Idddd: " + spin.getId());
						spin.setValue(parse.getValue(e1, "staffCode") + "-"
								+ parse.getValue(e1, "name"));
						listBranch.add(spin);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return listBranch;
		}
	}

	// ===== get distance day < 30 day
	private static long getDateDiff(long date1, long date2) {
		long diffInMillies = date2 - date1;
		return diffInMillies / (24 * 60 * 60 * 1000);
	}

	// ===== Lay sanh sach thue bao con khuyen mai =======================
	private class GetListSubPromotionAsyn extends
			AsyncTask<String, Void, ArrayList<ContractPromotionObj>> {

		final ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetListSubPromotionAsyn(Context context) {
			this.context = context;
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
		protected ArrayList<ContractPromotionObj> doInBackground(String... arg0) {
			return getListSubPromotion(arg0[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<ContractPromotionObj> result) {
			super.onPostExecute(result);
			progress.dismiss();
			// TODO LAY DANH SACH THUE BAO CHO NAY
			if (errorCode.equalsIgnoreCase("0")) {

				if (result != null && !result.isEmpty()) {
					// arrContractPromotionObj = new
					// ArrayList<ContractPromotionObj>();
					arrContractPromotionObj.addAll(result);
					showDialogListContract();
					if (result.size() < MAX_RECORD_PER_PAGE) {
						loadmore = false;
					} else {
						loadmore = true;
					}

				} else {
					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.no_data),
							getString(R.string.app_name)).show();
					loadmore = false;
				}
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

		private ArrayList<ContractPromotionObj> getListSubPromotion(
				String staffCode) {
			ArrayList<ContractPromotionObj> arrContractPromotionObjs = new ArrayList<>();
			String original = "";
			// Spin subItem = (Spin) spinner_service.getSelectedItem();
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getSubPromotionByStaff");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getSubPromotionByStaff>");
				rawData.append("<input>");
				rawData.append("<token>" + Session.getToken() + "</token>");
				// rawData.append("<serviceType>" + subItem.getId()
				// + "</serviceType>");
				rawData.append("<staffCode>" + staffCode + "</staffCode>");
				rawData.append("<values>").append(
						spiMonth.getSelectedItemPosition());
				rawData.append("</values>");
				rawData.append("<pageIndex>").append(
						arrContractPromotionObjs.size() / MAX_RECORD_PER_PAGE);
				rawData.append("</pageIndex>");
				rawData.append("<pageSize>").append(MAX_RECORD_PER_PAGE);
				rawData.append("</pageSize>");

				String isdn = CommonActivity.getNormalText(StringUtils
						.escapeHtml(edtIsdnAccount.getText().toString()));
				rawData.append("<isdn>").append(isdn);

				rawData.append("</isdn>");
				rawData.append("</input>");
				rawData.append("</ws:getSubPromotionByStaff>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getSubPromotionByStaff");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original group", original);

				// === parse xml ==========

				SubscriberHandle handler = (SubscriberHandle) CommonActivity
						.parseXMLHandler(new SubscriberHandle(), original);
				output = handler.getItem();
				if (output.getToken() != null && !output.getToken().isEmpty()) {
					Session.setToken(output.getToken());
				}

				if (output.getErrorCode() != null) {
					errorCode = output.getErrorCode();
				}
				if (output.getDescription() != null) {
					description = output.getDescription();
				}
				arrContractPromotionObjs = handler.getLstData();

			} catch (Exception e) {
				e.printStackTrace();
			}

			return arrContractPromotionObjs;
		}
	}

	private final OnClickListener onClickListenerUpdate = new OnClickListener() {

		@Override
		public void onClick(View arg0) {

			if (arrContractPromotionObj != null
					&& !arrContractPromotionObj.isEmpty()) {
				if (curentContractPromotion != null) {
					for (int i = 0; i < arrContractPromotionObj.size(); i++) {
						if (curentContractPromotion.getSubId().equals(
								arrContractPromotionObj.get(i).getSubId())) {
							arrContractPromotionObj
									.remove(arrContractPromotionObj.get(i));
							break;
						}
					}
				}
				if (contractPromotionAdapter != null) {
					contractPromotionAdapter.notifyDataSetChanged();
				}
			}

		}
	};

	// =================== update ctkm ===========
	private class UpdatePromotionAsyn extends AsyncTask<String, Void, Void> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public UpdatePromotionAsyn(Context context) {
			this.context = context;
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
		protected Void doInBackground(String... params) {
			updatePromotion(params[0]);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			progress.dismiss();
			if (errorCode.equalsIgnoreCase("0")) {
				if (dialogContractDetail != null) {
					dialogContractDetail.dismiss();
				}
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.updatesuccess),
						getString(R.string.app_name), onClickListenerUpdate)
						.show();
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

		private String updatePromotion(String reasonId) {
			String original = "";
			Spin CTVItem = (Spin) spinner_ctv.getSelectedItem();
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_updateSubscriber");

				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:updateSubscriber>");
				rawData.append("<cmMobileInput>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				if(CTVItem != null){
					if(CTVItem.getId() != null && !CTVItem.equals("") && !CTVItem.getId().equals("-1")){
						rawData.append("<staffCode>").append(CTVItem.getId()).append("</staffCode>");
					}
				}

				rawData.append("<subId>" + curentContractPromotion.getSubId()
						+ "</subId>");
				//Fix service type la F sang CM ko dung gi truong nay
				rawData.append("<serviceType>" + "F" + "</serviceType>");
				rawData.append("<reasonId>").append(reasonId);
				rawData.append("</reasonId>");
				rawData.append("</cmMobileInput>");
				rawData.append("</ws:updateSubscriber>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_updateSubscriber");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original group", original);

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
			return null;
		}

	}

	// get reason start
	@SuppressWarnings("unused")
	private class AsyntaskGetReasonPos extends
			AsyncTask<String, Void, ArrayList<Spin>> {
		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public AsyntaskGetReasonPos(Context context) {
			this.context = context;
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
		protected ArrayList<Spin> doInBackground(String... params) {
			return getReasonInfoPos();
		}

		@Override
		protected void onPostExecute(ArrayList<Spin> result) {
			progress.dismiss();
			if (errorCode.equalsIgnoreCase("0")) {
				result.add(0, new Spin("-1",
						getString(R.string.txt_select_reason)));
				// arrListReasoon.addAll(result);
				Utils.setDataSpinner(activity, result, spinnerReasoon);

			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					// arrListReasoon.add(new Spin("-1",
					// getString(R.string.txt_select_reason)));
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
					// arrListReasoon.add(new Spin("-1",
					// getString(R.string.txt_select_reason)));
					Utils.setDataSpinner(activity, arrListReasoon,
							spinnerReasoon);
					Dialog dialog = CommonActivity.createAlertDialog(activity,
							description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		private ArrayList<Spin> getReasonInfoPos() {

			// Spin serviceItem = (Spin) spinner_service.getSelectedItem();
			ArrayList<Spin> lstReason = null;
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getListReasonByTelServicePos");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListReasonByTelServicePos>");
				rawData.append("<cmMobileInput>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<actionCode>" + 646 + "</actionCode>");
				// rawData.append("<serviceType>" + serviceItem.getId()
				// + "</serviceType>");
				rawData.append("<serviceType>" + "F" + "</serviceType>");
				rawData.append("</cmMobileInput>");
				rawData.append("</ws:getListReasonByTelServicePos>");

				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListReasonByTelServicePos");
				Log.i("LOG", "Respone:  " + response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("LOG", "Responseeeeeeeeee Original  " + response);

				// parser

				lstReason = parserListGroupPos(original);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return lstReason;
		}

		public ArrayList<Spin> parserListGroupPos(String original) {
			ArrayList<Spin> lstReason = new ArrayList<>();
			Document doc = parse.getDomElement(original);
			NodeList nl = doc.getElementsByTagName("return");
			NodeList nodechild = null;
			for (int i = 0; i < nl.getLength(); i++) {
				Element e2 = (Element) nl.item(i);
				errorCode = parse.getValue(e2, "errorCode");
				description = parse.getValue(e2, "description");
				Log.d("errorCode", errorCode);
				nodechild = doc.getElementsByTagName("lstReasonPos");
				for (int j = 0; j < nodechild.getLength(); j++) {
					Element e1 = (Element) nodechild.item(j);
					Spin spin = new Spin();
					spin.setValue(parse.getValue(e1, "codeName"));
					Log.d("LOG", "value: " + spin.getValue());
					spin.setId(parse.getValue(e1, "reasonId"));
					Log.d("LOG", "Idddd: " + spin.getId());
					lstReason.add(spin);
				}
			}

			return lstReason;
		}

	}

	private void initMonth() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
				R.layout.spinner_item);

		Calendar cal = Calendar.getInstance();
		adapter.add(DateTimeUtils.convertDateTimeToString(cal.getTime(),
				"MM/yyyy"));
		for (int i = 1; i <= 11; i++) {
			cal.add(Calendar.MONTH, 1);
			adapter.add(DateTimeUtils.convertDateTimeToString(cal.getTime(),
					"MM/yyyy"));

		}
		spiMonth.setAdapter(adapter);
	}

}
