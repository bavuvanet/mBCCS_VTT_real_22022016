package com.viettel.bss.viettelpos.v4.sale.activity;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.channel.adapter.AddapterFilterInvoidPaymentOfStaff;
import com.viettel.bss.viettelpos.v4.channel.adapter.AddapterFilterInvoidPaymentOfStaff.ChexboxOnlistenerInterface;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DBOpenHelper;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.infrastrucure.adapter.AdapterProvinceSpinner;
import com.viettel.bss.viettelpos.v4.report.object.InfoSearchInvoicePaymentStaff;
import com.viettel.bss.viettelpos.v4.report.object.SaleTransFull;
import com.viettel.bss.viettelpos.v4.sale.adapter.AdapterTransDetail;
import com.viettel.bss.viettelpos.v4.sale.adapter.SerialTransAdapter;
import com.viettel.bss.viettelpos.v4.sale.business.TelecomServiceBusiness;
import com.viettel.bss.viettelpos.v4.sale.dal.StaffDAL;
import com.viettel.bss.viettelpos.v4.sale.object.Serial;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;
import com.viettel.bss.viettelpos.v4.sale.object.TelecomServiceObject;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

public class FragmentInfoSearch extends Fragment implements OnClickListener, ChexboxOnlistenerInterface {

	private View mView;
	private Activity activity;

	private TextView txtNameActionBar;
	private Button btnHome;
	private Button btnSearch;

	private Spinner spStaff;
	private Spinner spService;
	private Spinner spTransfer;
	private Spinner spStateReview;

	private EditText edtFromDate;
	private EditText edtToDate;
    private Date dateNow = null;
	private CheckBox cbCheckAll;
	private TextView tvTotalAmount;
	private int totalAmount;
	private Dialog dialogListSaleTranfull;

	private final ArrayList<AreaObj> mListStaff = new ArrayList<>();
	private final ArrayList<AreaObj> mListService = new ArrayList<>();
	private final ArrayList<AreaObj> mListTypeTranfer = new ArrayList<>();
	private final ArrayList<AreaObj> mListStateReview = new ArrayList<>();

	private ArrayList<SaleTransFull> mListSaleTransFull = new ArrayList<>();
	private AddapterFilterInvoidPaymentOfStaff adapterSaleTransFull;

	private ArrayList<SaleTransFull> mListSaleTransFullDetail = new ArrayList<>();
    private SaleTransFull saleTransFullObject;
	private boolean isCheckApprovalOnItem;
	private ArrayList<Serial> mListSerial = new ArrayList<>();

	private DBOpenHelper dbOpenHelper = null;
	private SQLiteDatabase database = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		activity = getActivity();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		try {
			dateNow = calendar.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (mView == null) {
			mView = inflater.inflate(R.layout.layout_info_search, container, false);
			btnSearch = (Button) mView.findViewById(R.id.btnSearch);
			spStaff = (Spinner) mView.findViewById(R.id.spStaff);
			spService = (Spinner) mView.findViewById(R.id.spService);
			spTransfer = (Spinner) mView.findViewById(R.id.spTransfer);
			spStateReview = (Spinner) mView.findViewById(R.id.spStateReview);
			edtFromDate = (EditText) mView.findViewById(R.id.edtFromDate);
			edtToDate = (EditText) mView.findViewById(R.id.edtToDate);
		}

		btnSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				searchListVoiceInfoPayment();
			}
		});

		addItemToSpiner();
		setupdate();

		return mView;
	}

	private void searchListVoiceInfoPayment() {
		InfoSearchInvoicePaymentStaff infoSearchInvoicePaymentStaff = new InfoSearchInvoicePaymentStaff();
		AreaObj areaStaff = null;
		AreaObj areaService = null;
		AreaObj areaTypeTranfer = null;
		AreaObj areaStateReview = null;

		String strFromDate = edtFromDate.getText().toString();
		String strTodate = edtToDate.getText().toString();

		infoSearchInvoicePaymentStaff.setFromDate(strFromDate);
		infoSearchInvoicePaymentStaff.setToDate(strTodate);
		Dialog dialog = null;

		if (mListStaff != null && mListStaff.size() > 0) {
			int indexStaff = spStaff.getSelectedItemPosition();
			areaStaff = mListStaff.get(indexStaff);
			infoSearchInvoicePaymentStaff.setStaffCode(areaStaff.getAreaCode());
		} else {
			dialog = CommonActivity.createAlertDialog(activity, getString(R.string.staff_not_data),
					getString(R.string.app_name));
		}

		if (mListService != null && mListService.size() > 0) {
			int indexService = spService.getSelectedItemPosition();
			if (indexService != 0) {
				areaService = mListService.get(indexService);
				infoSearchInvoicePaymentStaff.setTelecomeServiceId(areaService.getAreaCode());
			}
		} else {
			dialog = CommonActivity.createAlertDialog(activity, getString(R.string.service_not_data),
					getString(R.string.app_name));
		}

		if (mListTypeTranfer != null && mListTypeTranfer.size() > 0) {
			int indexTypeTranfer = spTransfer.getSelectedItemPosition();
			if (indexTypeTranfer != 0) {
				areaTypeTranfer = mListTypeTranfer.get(indexTypeTranfer);
				infoSearchInvoicePaymentStaff.setSaleTransType(areaTypeTranfer.getAreaCode());
			}
		}

		if (mListStateReview != null && mListStateReview.size() > 0) {
			int indexStateReview = spStateReview.getSelectedItemPosition();
			areaStateReview = mListStateReview.get(indexStateReview);
			infoSearchInvoicePaymentStaff.setInspectStatus(areaStateReview.getAreaCode());
		}

		if (dialog != null) {
			dialog.show();
			return;
		}

		if (!CommonActivity.isNetworkConnected(activity)) {
			CommonActivity.createAlertDialog(activity, getString(R.string.errorNetwork), getString(R.string.app_name))
					.show();
		} else {
			AsyncTaskSearchDataPayment asyncTaskSearchDataPayment = new AsyncTaskSearchDataPayment(activity,
					infoSearchInvoicePaymentStaff);
			asyncTaskSearchDataPayment.execute();
		}
	}

	private void showListStaffInfo() {

		totalAmount = 0;
		dialogListSaleTranfull = new Dialog(activity);
		dialogListSaleTranfull.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogListSaleTranfull.setContentView(R.layout.layout_list_info_payment_of_staff);

		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(dialogListSaleTranfull.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.MATCH_PARENT;
		dialogListSaleTranfull.getWindow().setAttributes(lp);

		Button btnApproval = (Button) dialogListSaleTranfull.findViewById(R.id.btnApproval);
		tvTotalAmount = (TextView) dialogListSaleTranfull.findViewById(R.id.tvTotalAmount);
		tvTotalAmount.setText(getString(R.string.tv_amount) + ": " + totalAmount);
		cbCheckAll = (CheckBox) dialogListSaleTranfull.findViewById(R.id.cbCheckAll);
		TextView tvDialogTitle = (TextView) dialogListSaleTranfull.findViewById(R.id.tvDialogTitle);

		int index = spStateReview.getSelectedItemPosition();
		AreaObj areaReview = mListStateReview.get(index);
		if (areaReview.getAreaCode().equals("0")) { // chua duyet
			btnApproval.setText(getString(R.string.btn_approval));
			tvDialogTitle.setText(getString(R.string.title_un_approval));
		} else { // da duyet
			btnApproval.setText(getString(R.string.btn_un_approval));
			tvDialogTitle.setText(getString(R.string.title_approval));
		}

		btnApproval.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				boolean isCheckApproval = false;
				for (SaleTransFull saleTransFull : mListSaleTransFull) {
					if (saleTransFull.isCheckApproval()) {
						isCheckApproval = true;
					}
				}
				
				if (mListSaleTransFull.size() == 0) {
					dialogListSaleTranfull.dismiss();
					return;
				}

				if (!isCheckApproval) {
					CommonActivity.createAlertDialog(activity, getString(R.string.no_select_checkbox_approval),
							getString(R.string.app_name)).show();
					return;
				}

				String messageConfirm = "";
				int index = spStateReview.getSelectedItemPosition();
				AreaObj areaReview = mListStateReview.get(index);

				if (areaReview.getAreaCode().equals("0")) { // chua duyet
					messageConfirm = getString(R.string.message_confirm_approval);
				} else {
					messageConfirm = getString(R.string.message_un_confirm_approval);
				}

				CommonActivity.createDialog(activity, messageConfirm, getString(R.string.app_name),
						getString(R.string.say_ko), getString(R.string.say_co), null, onclickConfirmApprovalCalback)
						.show();

			}
		});

		cbCheckAll.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

				if (!isCheckApprovalOnItem) {
					totalAmount = 0;
					boolean isCheckAll = cbCheckAll.isChecked();
					for (SaleTransFull saleTransFull : mListSaleTransFull) {
						saleTransFull.setCheckApproval(isCheckAll);
						if (saleTransFull.isCheckApproval()) {
							totalAmount += Integer.parseInt(saleTransFull.getAmountTax());
						}
					}
					tvTotalAmount.setText(getString(R.string.tv_amount) + ": "
							+ StringUtils.formatMoney(String.valueOf(totalAmount)));
				}

				if (adapterSaleTransFull != null) {
					adapterSaleTransFull.notifyDataSetChanged();
				}
				isCheckApprovalOnItem = false;
			}
		});

        ListView mLvInfoPayment = (ListView) dialogListSaleTranfull.findViewById(R.id.lvInfoPayment);
		Button btnClose = (Button) dialogListSaleTranfull.findViewById(R.id.btnClose);
		btnClose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialogListSaleTranfull.dismiss();
			}
		});

		adapterSaleTransFull = new AddapterFilterInvoidPaymentOfStaff(mListSaleTransFull, activity,
				FragmentInfoSearch.this);
		mLvInfoPayment.setAdapter(adapterSaleTransFull);
		mLvInfoPayment.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				if (!CommonActivity.isNetworkConnected(activity)) {
					CommonActivity
							.createAlertDialog(activity, getString(R.string.errorNetwork), getString(R.string.app_name))
							.show();
				} else {
					saleTransFullObject = mListSaleTransFull.get(position);
					AsyncTaskGetDetailStaffSalePayMoney asyncTaskGetDetailPaymentStaff = new AsyncTaskGetDetailStaffSalePayMoney(
							activity, saleTransFullObject);
					asyncTaskGetDetailPaymentStaff.execute();

				}
			}
		});

		dialogListSaleTranfull.show();
	}

	@SuppressWarnings("unused")
	private void onClickApprovalOrUnApproval(int typeRequest) {
		if (!CommonActivity.isNetworkConnected(activity)) {
			CommonActivity.createAlertDialog(activity, getString(R.string.errorNetwork), getString(R.string.app_name))
					.show();
		} else {
			ArrayList<SaleTransFull> listSaleTranfull = new ArrayList<>();
			for (SaleTransFull saleTransFull : mListSaleTransFull) {
				if (saleTransFull.isCheckApproval()) {
					listSaleTranfull.add(saleTransFull);
				}
			}

			if (listSaleTranfull.size() > 0) {
				AsyncTaskApprovalOrUnApproval asyncTaskApproval = new AsyncTaskApprovalOrUnApproval(activity,
						listSaleTranfull, typeRequest);
				asyncTaskApproval.execute();
			} else {
				CommonActivity.createAlertDialog(activity, getString(R.string.not_data_select_sale_tranfull),
						getString(R.string.app_name)).show();
			}
		}
	}

	private void showDetailInfoStaff() {
		//
		final Dialog dialog = new Dialog(activity);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.transaction_layout_detail);

		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(dialog.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.MATCH_PARENT;
		dialog.getWindow().setAttributes(lp);

		dialog.setCancelable(false);
		ListView lvTrandetail = (ListView) dialog.findViewById(R.id.lvdetailTran);
		TextView txttitle = (TextView) dialog.findViewById(R.id.tvDialogTitle);

		txttitle.setText(saleTransFullObject.getSaleTransCode());
		TextView tvUserNameTranfer = (TextView) dialog.findViewById(R.id.tvUserNameTranfer);
		TextView tvDateTranfer = (TextView) dialog.findViewById(R.id.tvDateTranfer);
		TextView tvMountTranfer = (TextView) dialog.findViewById(R.id.tvMountTranfer);
		TextView tvTypeTranfer = (TextView) dialog.findViewById(R.id.tvTypeTranfer);
		TextView tvCusName = (TextView) dialog.findViewById(R.id.tvCusName);

		tvTypeTranfer.setText(saleTransFullObject.getSaleTransTypeName());
		tvCusName.setText(saleTransFullObject.getCustName());
		tvUserNameTranfer.setText(saleTransFullObject.getStaffCode() + " - " + saleTransFullObject.getStaffName());
		tvDateTranfer.setText(saleTransFullObject.getSaleTransDate());
		tvMountTranfer.setText(StringUtils.formatMoney(saleTransFullObject.getAmountTax()));

        AdapterTransDetail adapterTransDetail = new AdapterTransDetail(activity, mListSaleTransFullDetail);
		adapterTransDetail = new AdapterTransDetail(getActivity(), mListSaleTransFullDetail);
		lvTrandetail.setAdapter(adapterTransDetail);
		lvTrandetail.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				SaleTransFull saleTranfull = mListSaleTransFullDetail.get(arg2);
				if (CommonActivity.isNetworkConnected(activity)) {
					AsynTaskGetListSaleSerial asyntaskGetlistSerial = new AsynTaskGetListSaleSerial(activity,
							saleTranfull);
					asyntaskGetlistSerial.execute();
				} else {
					CommonActivity
							.createAlertDialog(activity, getString(R.string.errorNetwork), getString(R.string.app_name))
							.show();
				}
			}
		});

		Button btnclose = (Button) dialog.findViewById(R.id.btnclose);
		btnclose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	private void showListSerial() {
		final Dialog dialog = new Dialog(activity);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.layout_list_serial);

		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(dialog.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.MATCH_PARENT;
		dialog.getWindow().setAttributes(lp);
		dialog.setCancelable(false);

		ListView lvSerial = (ListView) dialog.findViewById(R.id.lvSerial);
		Button btnClose = (Button) dialog.findViewById(R.id.btnClose);
		btnClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();

			}
		});

		SerialTransAdapter serialTransAdapter = new SerialTransAdapter(activity, mListSerial);
		lvSerial.setAdapter(serialTransAdapter);

		dialog.show();
	}

	private void addItemToSpiner() {

		if (mListService != null && mListService.size() > 0) {
			mListService.clear();
		}

		if (mListStaff != null && mListStaff.size() > 0) {
			mListStaff.clear();
		}

		if (mListTypeTranfer != null && mListTypeTranfer.size() > 0) {
			mListTypeTranfer.clear();
		}

		// mListStateReview
		if (mListStateReview != null && mListStateReview.size() > 0) {
			mListStateReview.clear();
		}

		// dich vu
		AreaObj areaServiceObject = new AreaObj();
		areaServiceObject.setName(getString(R.string.title_spinner_id_num));
		mListService.add(areaServiceObject);
		TelecomServiceBusiness telecomService = new TelecomServiceBusiness();
		ArrayList<TelecomServiceObject> mListTelecomService;
		try {
			mListTelecomService = TelecomServiceBusiness.getAllTelecomService(activity);
//			if (mListTelecomService.size() > 0) {
//			    Collections.sort(mListTelecomService, new Comparator<TelecomServiceObject>() {
//			        @Override
//			        public int compare(final TelecomServiceObject object1, final TelecomServiceObject object2) {
//			            return object1.getName().compareTo(object2.getName());
//			        }
//			       } );
//			   } 
			
			for (TelecomServiceObject telecomServiceObject : mListTelecomService) {
				AreaObj areaObject = new AreaObj();
				areaObject.setAreaCode(telecomServiceObject.getTelecomServiceId() + "");
				areaObject.setName(telecomServiceObject.getName());
				mListService.add(areaObject);
			}

			AdapterProvinceSpinner adapterSerVice = new AdapterProvinceSpinner(mListService, activity);
			spService.setAdapter(adapterSerVice);

		} catch (Exception e) {
			e.printStackTrace();
		}

		ArrayList<Staff> listStaff = null;
		// nhan vien
		try {
			dbOpenHelper = new DBOpenHelper(activity);
			database = dbOpenHelper.getReadableDatabase();
			StaffDAL staffDal = new StaffDAL(database);
			listStaff = staffDal.getLisStaffByShopId(Session.loginUser.getShopId(), 14L);
			
//			if (mListTelecomService.size() > 0) {
//			    Collections.sort(mListTelecomService, new Comparator<TelecomServiceObject>() {
//			        @Override
//			        public int compare(final TelecomServiceObject object1, final TelecomServiceObject object2) {
//			            return object1.getName().compareTo(object2.getName());
//			        }
//			       } );
//			   }
			
			
			database.close();
			dbOpenHelper.close();
		} catch (Exception ignored) {
		} finally {
			if (dbOpenHelper != null) {
				dbOpenHelper.close();
			}

			if (database != null) {
				database.close();
			}
		}

		if (listStaff != null && listStaff.size() > 0) {
			for (Staff staff : listStaff) {
				AreaObj areaObject = new AreaObj();
				areaObject.setName(staff.getStaffCode());
				areaObject.setAreaCode(staff.getStaffCode());
				mListStaff.add(areaObject);
			}

			AdapterProvinceSpinner adapterStaff = new AdapterProvinceSpinner(mListStaff, activity);
			spStaff.setAdapter(adapterStaff);
		}

		// loai giao dich

		AreaObj areaObject1 = new AreaObj();
		areaObject1.setName(getString(R.string.sellect_type_tranfer));
		mListTypeTranfer.add(areaObject1);

		AreaObj areaObject = new AreaObj();
		areaObject.setName(getString(R.string.type_tranfer_one));
		areaObject.setAreaCode("1");
		mListTypeTranfer.add(areaObject);

		AreaObj areaObject2 = new AreaObj();
		areaObject2.setName(getString(R.string.type_tranfer_ctv));
		areaObject2.setAreaCode("3");
		mListTypeTranfer.add(areaObject2);

		AdapterProvinceSpinner adapterTranfer = new AdapterProvinceSpinner(mListTypeTranfer, activity);
		spTransfer.setAdapter(adapterTranfer);

		// duyet spinner

		AreaObj areaStateNotReview = new AreaObj();
		areaStateNotReview.setName(getString(R.string.not_reviewed));
		areaStateNotReview.setAreaCode("0");
		mListStateReview.add(areaStateNotReview);

		AreaObj areaStateReview = new AreaObj();
		areaStateReview.setName(getString(R.string.reviewed));
		areaStateReview.setAreaCode("1");
		mListStateReview.add(areaStateReview);

		AdapterProvinceSpinner adapterStateReview = new AdapterProvinceSpinner(mListStateReview, activity);
		spStateReview.setAdapter(adapterStateReview);

	}

	private void setupdate() {
		Calendar calendar = Calendar.getInstance();
		int curentYear = calendar.get(Calendar.YEAR);
		int curentMonth = calendar.get(Calendar.MONTH);
		int curentDay = calendar.get(Calendar.DAY_OF_MONTH);

		Calendar calendarBefor3day = Calendar.getInstance();
		calendarBefor3day.add(Calendar.DATE, -2);
		int befor3dayYear = calendarBefor3day.get(Calendar.YEAR);
		int befor3daycurentMonth = calendarBefor3day.get(Calendar.MONTH);
		int befor3daycurentDay = calendarBefor3day.get(Calendar.DAY_OF_MONTH);

		String strFromDate = (befor3daycurentDay) + "/" + (befor3daycurentMonth + 1) + "/" + befor3dayYear;
		edtFromDate.setText(strFromDate);

		String strToDate = (curentDay) + "/" + (curentMonth + 1) + "/" + curentYear;
		edtToDate.setText(strToDate);

		edtFromDate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				CommonActivity.showDatePickerDialog(activity, new DateListtennerInterface() {
					@Override
					public void onlistennerDate(String date, int day, int month, int year) {

						Calendar calendar = Calendar.getInstance();

						int curentYear = calendar.get(Calendar.YEAR);
						int curentMonth = calendar.get(Calendar.MONTH);
						int curentDay = calendar.get(Calendar.DAY_OF_MONTH);

						Calendar calendarBefor10day = Calendar.getInstance();
						calendarBefor10day.add(Calendar.DATE, -10);

						int befor10dayYear = calendarBefor10day.get(Calendar.YEAR);
						int befor10daycurentMonth = calendarBefor10day.get(Calendar.MONTH);
						int befor10daycurentDay = calendarBefor10day.get(Calendar.DAY_OF_MONTH);

						String strDayBefor10day = (befor10daycurentDay) + "/" + (befor10daycurentMonth + 1) + "/"
								+ befor10dayYear;

						Date toDate = null;
						Date fromDate = null;
						Date befor10day = null;
						String strTodate = edtToDate.getText().toString().trim();

						try {

							if (strTodate != null && strTodate.length() > 0) {
								toDate = DateTimeUtils.convertStringToTime(strTodate, "dd/MM/yyyy");
							}
							fromDate = DateTimeUtils.convertStringToTime(date, "dd/MM/yyyy");
							befor10day = DateTimeUtils.convertStringToTime(strDayBefor10day, "dd/MM/yyyy");

							if (fromDate.after(dateNow)) {
								CommonActivity.createAlertDialog(activity,
										getString(R.string.date_select_smaller_date_now), getString(R.string.app_name))
										.show();
							} else if (befor10day.after(fromDate)) {
								CommonActivity.createAlertDialog(activity, getString(R.string.starttime_10_curent_time),
										getString(R.string.app_name)).show();
							} else if (toDate != null && fromDate.after(toDate)) {
								CommonActivity.createAlertDialog(activity, getString(R.string.fromDate_smaller_Todate),
										getString(R.string.app_name)).show();
							} else {
								edtFromDate.setText(date);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				});
			}
		});

		edtToDate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				CommonActivity.showDatePickerDialog(activity, new DateListtennerInterface() {

					@Override
					public void onlistennerDate(String date, int day, int month, int year) {

						Calendar calendar = Calendar.getInstance();
						int curentYear = calendar.get(Calendar.YEAR);
						int curentMonth = calendar.get(Calendar.MONTH);
						int curentDay = calendar.get(Calendar.DAY_OF_MONTH);

						Calendar calendarBefor10day = Calendar.getInstance();
						calendarBefor10day.add(Calendar.DATE, -10);

						int befor10dayYear = calendarBefor10day.get(Calendar.YEAR);
						int befor10daycurentMonth = calendarBefor10day.get(Calendar.MONTH);
						int befor10daycurentDay = calendarBefor10day.get(Calendar.DAY_OF_MONTH);

						String strDayBefor10day = (befor10daycurentDay) + "/" + (befor10daycurentMonth + 1) + "/"
								+ befor10dayYear;
						String strFromDate = edtFromDate.getText().toString().trim();

						Date toDate = null;
						Date fromDate = null;
						Date befor10day = null;

						try {
							toDate = DateTimeUtils.convertStringToTime(date, "dd/MM/yyyy");
							befor10day = DateTimeUtils.convertStringToTime(strDayBefor10day, "dd/MM/yyyy");

							if (strFromDate != null && strFromDate.length() > 0) {
								fromDate = DateTimeUtils.convertStringToTime(strFromDate, "dd/MM/yyyy");
							}

							if (toDate.after(dateNow)) {
								CommonActivity.createAlertDialog(activity,
										getString(R.string.date_select_smaller_date_now), getString(R.string.app_name))
										.show();
							} else if (fromDate.before(befor10day)) {
								CommonActivity.createAlertDialog(activity, getString(R.string.starttime_10_curent_time),
										getString(R.string.app_name)).show();
							} else if (fromDate != null && fromDate.after(toDate)) {
								CommonActivity.createAlertDialog(activity, getString(R.string.Todate_bigger_Fromdate),
										getString(R.string.app_name)).show();
							} else {
								edtToDate.setText(date);
							}

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
	}

	private final OnClickListener onclickConfirmApprovalCalback = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int index = spStateReview.getSelectedItemPosition();
			AreaObj areaReview = mListStateReview.get(index);
			if (areaReview.getAreaCode().equals("0")) { // chua duyet
				onClickApprovalOrUnApproval(1); // thuc hien duyet
			} else {
				onClickApprovalOrUnApproval(2); // thuc hien huy duyet
			}

		}
	};

	@Override
	public void onResume() {
		MainActivity.getInstance().setTitleActionBar(R.string.info_search_payment_staff);
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
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

	public class AsyncTaskSearchDataPayment extends AsyncTask<String, Void, ArrayList<SaleTransFull>> {

		private Activity mActivity = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final ProgressDialog progress;
		private final InfoSearchInvoicePaymentStaff infoPaymentSearch;
		// private int typeRequest;

		public AsyncTaskSearchDataPayment(Activity mActivity, InfoSearchInvoicePaymentStaff infoSearchPayment) {
			this.mActivity = mActivity;
			this.infoPaymentSearch = infoSearchPayment;
			// this.typeRequest = typeRequest;
			this.progress = new ProgressDialog(this.mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(this.mActivity.getResources().getString(R.string.searching));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<SaleTransFull> doInBackground(String... params) {

			return getListInvoicePaymentStaff(infoPaymentSearch);
		}

		@Override
		protected void onPostExecute(ArrayList<SaleTransFull> result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result != null && result.size() > 0) {
					mListSaleTransFull = result;
					showListStaffInfo();
				} else {
					CommonActivity.createAlertDialog(mActivity, getString(R.string.not_result_search),
							getString(R.string.app_name)).show();
				}
			} else {

				Log.d("Log", "description error update" + description);
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity.createAlertDialog(mActivity, description,
							mActivity.getResources().getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = mActivity.getString(R.string.checkdes);
					}
					CommonActivity.createAlertDialog(mActivity, description, getString(R.string.app_name)).show();
				}
			}
		}

		@SuppressWarnings("unused")
		private ArrayList<SaleTransFull> getListInvoicePaymentStaff(InfoSearchInvoicePaymentStaff infoSearchject) {

			ArrayList<SaleTransFull> listSaleTransFull = new ArrayList<>();

			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getLstInspectStaffSalePayMoney");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getLstInspectStaffSalePayMoney>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");

				rawData.append("<staffCode>").append(infoSearchject.getStaffCode()).append("</staffCode>");
				rawData.append("<fromDate>").append(infoSearchject.getFromDate()).append("</fromDate>");

				rawData.append("<toDate>").append(infoSearchject.getToDate()).append("</toDate>");

				if (infoSearchject.getTelecomeServiceId() != null
						&& infoSearchject.getTelecomeServiceId().length() > 0) {
					rawData.append("<telecomeServiceId>").append(infoSearchject.getTelecomeServiceId()).append("</telecomeServiceId>");
				}

				if (infoSearchject.getSaleTransType() != null && infoSearchject.getSaleTransType().length() > 0) {
					rawData.append("<saleTransType>").append(infoSearchject.getSaleTransType()).append("</saleTransType>");
				}

				rawData.append("<inspectStatus>").append(infoSearchject.getInspectStatus()).append("</inspectStatus>");

				rawData.append("</input>");
				rawData.append("</ws:getLstInspectStaffSalePayMoney>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getLstInspectStaffSalePayMoney");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", response);

				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					nodechild = doc.getElementsByTagName("lstSaleTransFull");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);

						SaleTransFull saleTransFull = new SaleTransFull();
						saleTransFull.setSaleTransId(parse.getValue(e1, "saleTransId"));
						saleTransFull.setSaleTransCode(parse.getValue(e1, "saleTransCode"));
						saleTransFull.setStaffCode(parse.getValue(e1, "staffCode"));
						saleTransFull.setStaffName(parse.getValue(e1, "staffName"));
						saleTransFull.setSaleTransDate(DateTimeUtils.convertDate(parse.getValue(e1, "saleTransDate")));
						saleTransFull.setAmountTax(parse.getValue(e1, "amountTax"));
						saleTransFull.setCustName(parse.getValue(e1, "custName"));
						saleTransFull.setSaleTransTypeName(parse.getValue(e1, "saleTransTypeName"));
						saleTransFull.setInSpectStatus(parse.getValue(e1, "inSpectStatus"));
						listSaleTransFull.add(saleTransFull);
					}
				}
			} catch (Exception e) {
				Log.d("getListIP", e.toString());
			}

			return listSaleTransFull;
		}

	}

	@SuppressWarnings("unused")
	private class AsyncTaskGetDetailStaffSalePayMoney extends AsyncTask<String, Void, ArrayList<SaleTransFull>> {

		private Activity mActivity = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final ProgressDialog progress;
		private final SaleTransFull mSaleTransfull;

		public AsyncTaskGetDetailStaffSalePayMoney(Activity mActivity, SaleTransFull mSaleTransfull) {
			this.mActivity = mActivity;
			this.mSaleTransfull = mSaleTransfull;
			this.progress = new ProgressDialog(this.mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(this.mActivity.getResources().getString(R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<SaleTransFull> doInBackground(String... params) {

			return getInspectDetailStaffSalePayMoney(mSaleTransfull);
		}

		@Override
		protected void onPostExecute(ArrayList<SaleTransFull> result) {

			super.onPostExecute(result);

			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result != null && result.size() > 0) {
					mListSaleTransFullDetail = result;
					showDetailInfoStaff();
				} else {
					CommonActivity
							.createAlertDialog(mActivity, getString(R.string.ko_co_dl), getString(R.string.app_name))
							.show();
				}
			} else {
				Log.d("Log", "description error update" + description);
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity.createAlertDialog(mActivity, description,
							mActivity.getResources().getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = mActivity.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			}

		}

		private ArrayList<SaleTransFull> getInspectDetailStaffSalePayMoney(SaleTransFull saleTransFull) {
			ArrayList<SaleTransFull> listSaleTransFull = new ArrayList<>();

			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getInspectDetailStaffSalePayMoney");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getInspectDetailStaffSalePayMoney>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<saleTransId>").append(saleTransFull.getSaleTransId()).append("</saleTransId>");
				rawData.append("</input>");
				rawData.append("</ws:getInspectDetailStaffSalePayMoney>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getInspectDetailStaffSalePayMoney");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", response);

				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					nodechild = doc.getElementsByTagName("lstSaleTransDetailFull");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);

						SaleTransFull saleTransFullDetail = new SaleTransFull();
						saleTransFullDetail.setSaleTransDetailId(parse.getValue(e1, "saleTransDetailId"));
						saleTransFullDetail.setStockModelId(parse.getValue(e1, "stockModelId"));
						saleTransFullDetail.setStockModelCode(parse.getValue(e1, "stockModelCode"));
						saleTransFullDetail.setName(parse.getValue(e1, "name"));
						saleTransFullDetail.setQuantity(parse.getValue(e1, "quantity"));

						// saleTransFullDetail.setSaleTransDate(saleTransFull.getSaleTransDate());
						// saleTransFullDetail.setAmountTax(saleTransFull.getAmountTax());

						listSaleTransFull.add(saleTransFullDetail);
					}
				}
			} catch (Exception e) {
				Log.d("getListIP", e.toString());
			}

			return listSaleTransFull;

		}
	}

	@Override
	public void onclickCheckboxApproval(int position, boolean isChecked) {
		totalAmount = 0;
		isCheckApprovalOnItem = true;
		boolean isCheckAll = true;
		for (SaleTransFull saleTransFull2 : mListSaleTransFull) {
			if (!saleTransFull2.isCheckApproval()) {
				isCheckAll = false;
				Log.d("Log", "check box check all");
			} else {
				totalAmount += Integer.parseInt(saleTransFull2.getAmountTax());

			}
		}

		tvTotalAmount
				.setText(getString(R.string.tv_amount) + ": " + StringUtils.formatMoney(String.valueOf(totalAmount)));
		cbCheckAll.setChecked(isCheckAll);

		if (adapterSaleTransFull != null) {
			adapterSaleTransFull.notifyDataSetChanged();
		}
		isCheckApprovalOnItem = false;
	}

	@SuppressWarnings("unused")
	private class AsyncTaskApprovalOrUnApproval extends AsyncTask<Void, Void, Void> {

		private final Activity mActivity;
		private final ArrayList<SaleTransFull> listSaleTransfull;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final ProgressDialog progress;
		private final int typeRequest;

		public AsyncTaskApprovalOrUnApproval(Activity mActivity, ArrayList<SaleTransFull> mlistTranfull,
				int typeRequest) {
			this.mActivity = mActivity;
			this.listSaleTransfull = mlistTranfull;
			this.typeRequest = typeRequest;

			this.progress = new ProgressDialog(this.mActivity);
			this.progress.setCancelable(false);
			if (typeRequest == 1) {
				this.progress.setMessage(this.mActivity.getResources().getString(R.string.loadding_approval));
			} else {
				this.progress.setMessage(this.mActivity.getResources().getString(R.string.loadding_un_approval));
			}

			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			doApprovalSaleTransfull(listSaleTransfull);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			super.onPostExecute(result);
			progress.dismiss();

			Dialog dialog = null;
			if (errorCode.equals("0")) {
				if (typeRequest == 1) { // duyet
					CommonActivity.createAlertDialog(activity, getString(R.string.user_approval_transffer_success),
							getString(R.string.app_name)).show();
				} else { // 2 huy duyet
					CommonActivity.createAlertDialog(activity, getString(R.string.user_un_approval_transffer_success),
							getString(R.string.app_name)).show();
				}
				
				
				for (Iterator<SaleTransFull> iterator = mListSaleTransFull.iterator(); iterator.hasNext();) {
				    SaleTransFull saleTransFull = iterator.next();
				    if (saleTransFull.isCheckApproval()) {
				        // Remove the current element from the iterator and the list.
				        iterator.remove();
				    }
				}  
				totalAmount = 0;
				cbCheckAll.setChecked(false);
				tvTotalAmount.setText(getString(R.string.tv_amount) + ": " + totalAmount); 
				adapterSaleTransFull.notifyDataSetChanged();
			     
				
				if (mListSaleTransFull.size() == 0) {
					dialogListSaleTranfull.dismiss();
				}
				
//				refreshData();

			} else {
				Log.d("Log", "description error update" + description);
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					CommonActivity.createAlertDialog(mActivity, description,
							mActivity.getResources().getString(R.string.app_name), moveLogInAct).show();
				} else {
					if (description == null || description.isEmpty()) {
						description = mActivity.getString(R.string.checkdes);
					}
					CommonActivity
							.createAlertDialog(getActivity(), description, getResources().getString(R.string.app_name))
							.show();

				}
			}
		}

		private void refreshData() {
			addItemToSpiner();
			setupdate();
		}

		private void doApprovalSaleTransfull(ArrayList<SaleTransFull> mListSaleTranfull) {

			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_inspectSaleTrans");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:inspectSaleTrans>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");

				for (SaleTransFull saleTransFull : mListSaleTranfull) {
					rawData.append("<lstSaleTransId>").append(saleTransFull.getSaleTransId()).append("</lstSaleTransId>");

				}

				if (typeRequest == 1) { // duyet
					rawData.append("<status>" + 1 + "</status>");
				} else { // huy duyet
					rawData.append("<status>" + 0 + "</status>");
				}

				rawData.append("</input>");
				rawData.append("</ws:inspectSaleTrans>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_inspectSaleTrans");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", response);

				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
				}
			} catch (Exception e) {
				Log.d("getListIP", e.toString());
			}
		}

	}

	@SuppressWarnings("unused")
	private class AsynTaskGetListSaleSerial extends AsyncTask<Void, Void, ArrayList<Serial>> {

		private Activity mActivity = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final ProgressDialog progress;
		private final SaleTransFull mSaleTransfull;

		public AsynTaskGetListSaleSerial(Activity mActivity, SaleTransFull mSaleTranfull) {
			this.mActivity = mActivity;
			this.mSaleTransfull = mSaleTranfull;

			this.progress = new ProgressDialog(this.mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(this.mActivity.getResources().getString(R.string.getdataing));

			if (!this.progress.isShowing()) {
				this.progress.show();
			}

		}

		@Override
		protected ArrayList<Serial> doInBackground(Void... params) {

			return getListSerialObject(mSaleTransfull);
		}

		@Override
		protected void onPostExecute(ArrayList<Serial> result) {
			super.onPostExecute(result);

			progress.dismiss();
			if (errorCode.equals("0")) {

				if (result != null && result.size() > 0) {
					mListSerial = result;
					showListSerial();
				} else {
					CommonActivity.createAlertDialog(activity, getString(R.string.no_data_serial),
							getString(R.string.app_name)).show();
				}
			} else {
				Log.d("Log", "description error update" + description);
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity.createAlertDialog(mActivity, description,
							mActivity.getResources().getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = mActivity.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			}

		}

		private ArrayList<Serial> getListSerialObject(SaleTransFull mSaleTransfull) {

			ArrayList<Serial> mListSerial = new ArrayList<>();

			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getSaleTransSerial");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getSaleTransSerial>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<saleTransDetailId>").append(mSaleTransfull.getSaleTransDetailId()).append("</saleTransDetailId>");
				rawData.append("</input>");
				rawData.append("</ws:getSaleTransSerial>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getSaleTransSerial");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", response);

				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					nodechild = doc.getElementsByTagName("lstSaleTransSerial");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						Serial serialObject = new Serial();
						serialObject.setFromSerial(parse.getValue(e1, "fromSerial"));
						serialObject.setToSerial(parse.getValue(e1, "toSerial"));
						mListSerial.add(serialObject);
					}
				}
			} catch (Exception e) {
				Log.d("getListIP", e.toString());
			}
			return mListSerial;
		}

	}

}
