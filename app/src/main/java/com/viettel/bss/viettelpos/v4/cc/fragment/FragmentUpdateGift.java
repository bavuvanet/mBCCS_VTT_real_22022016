package com.viettel.bss.viettelpos.v4.cc.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bss.smartphonev2.commons.OnPostExecute;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.cc.adapter.SubscriberCareBeanAdapter;
import com.viettel.bss.viettelpos.v4.cc.asynctask.AsyncGetCustCareDetail;
import com.viettel.bss.viettelpos.v4.cc.asynctask.AsyncGetCustCareSummary;
import com.viettel.bss.viettelpos.v4.cc.dialog.DialogUpdateGift;
import com.viettel.bss.viettelpos.v4.cc.object.CCOutput;
import com.viettel.bss.viettelpos.v4.cc.object.SubscriberCareBean;
import com.viettel.bss.viettelpos.v4.cc.object.SummaryBo;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.object.FormBean;

public class FragmentUpdateGift extends FragmentCommon {

	private EditText edtIsdn;
	private ListView lvGift;
	private TextView tvNotify;
	private View prbNotify;
	private View btnSearch;
	private ArrayList<SubscriberCareBean> lstSubscriberCareBeans = new ArrayList<SubscriberCareBean>();
	private SubscriberCareBeanAdapter adapter;
    private DialogUpdateGift dialogUpdate;
	private Spinner spnProgram;
	private Spinner spnStatus;

	private boolean loadmore = true;
	private TextView tvFromDate;
	private TextView tvToDate;

	@Override
	public void onResume() {
		super.onResume();
		setTitleActionBar(getActivity().getString(R.string.update_gift));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		idLayout = R.layout.layout_update_gift;

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void unit(View v) {
		edtIsdn = (EditText) v.findViewById(R.id.edtIsdn);
		tvFromDate = (TextView) v.findViewById(R.id.tvFromDate);
		tvToDate = (TextView) v.findViewById(R.id.tvToDate);

		tvToDate.setText(DateTimeUtils.convertDateTimeToString(new Date(),
				"dd/MM/yyyy"));
		Calendar calFrom = Calendar.getInstance();
		calFrom.set(Calendar.DAY_OF_MONTH, 1);
		tvFromDate.setText(DateTimeUtils.convertDateTimeToString(
				calFrom.getTime(), "dd/MM/yyyy"));

		tvFromDate.setOnClickListener(onClick);
		tvToDate.setOnClickListener(onClick);

		lvGift = (ListView) v.findViewById(R.id.lvGift);
		tvNotify = (TextView) v.findViewById(R.id.tvNotify);
		prbNotify = v.findViewById(R.id.prbNotify);
		tvNotify.setVisibility(View.GONE);
		prbNotify.setVisibility(View.VISIBLE);
		btnSearch = v.findViewById(R.id.btnSearch);
		btnSearch.setOnClickListener(onClick);
		adapter = new SubscriberCareBeanAdapter(act, lstSubscriberCareBeans);
		lvGift.setAdapter(adapter);
		spnProgram = (Spinner) v.findViewById(R.id.spnProgram);
		spnStatus = (Spinner) v.findViewById(R.id.spnStatus);

		AsyncGetCustCareSummary asy = new AsyncGetCustCareSummary(
				onPostGetSumarry, getActivity());
		asy.execute();
		lvGift.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				SubscriberCareBean bean = lstSubscriberCareBeans.get(arg2);
				if ("1".equals(bean.getGiftStatus())) {
					Toast.makeText(getActivity(),
							getString(R.string.cannot_update_gift_delivered),
							Toast.LENGTH_SHORT).show();
					return;
				}
				dialogUpdate = new DialogUpdateGift(getActivity(), bean,
						onPostExecuteUpdate);
				dialogUpdate.show();
			}
		});
		lvGift.setOnScrollListener(new OnScrollListener() {

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
					FormBean prog = (FormBean) spnProgram.getSelectedItem();
					FormBean status = (FormBean) spnStatus.getSelectedItem();

					AsyncGetCustCareDetail asy = new AsyncGetCustCareDetail(
							edtIsdn.getText().toString().trim(), prog.getId(),
							tvFromDate.getText().toString(), tvToDate.getText()
									.toString(), status.getId(),
							lstSubscriberCareBeans.size(),
							onPostGetCustCareDetail, getActivity());
					asy.execute();
				}

			}
		});

		final List<FormBean> lstStatus = new ArrayList<FormBean>();

		FormBean all = new FormBean();
		all.setId("");
		all.setName(getString(R.string.all));
		lstStatus.add(all);

		FormBean success = new FormBean();
		success.setId("1");
		success.setName(getString(R.string.gift_birthday));
		lstStatus.add(success);

		FormBean fail = new FormBean();
		fail.setId("2");
		fail.setName(getString(R.string.gift_local));
		lstStatus.add(fail);
		ArrayAdapter<FormBean> statusAdapter = new ArrayAdapter<FormBean>(
				getActivity(), R.layout.spinner_item, lstStatus);
		spnProgram.setAdapter(statusAdapter);
		initStatus();
	}

	OnPostExecute<CCOutput> onPostGetSumarry = new OnPostExecute<CCOutput>() {

		@Override
		public void onPostExecute(CCOutput result) {
			prbNotify.setVisibility(View.GONE);
			tvNotify.setVisibility(View.VISIBLE);
			if (result.getErrorCode().equals("0")) {
				SummaryBo bo = result.getSummaryBo();
				if (bo != null) {
					String month = DateTimeUtils.convertDateTimeToString(
							new Date(), "MM/yyyy");
					String day = DateTimeUtils.convertDateTimeToString(
							new Date(), "dd/MM/yyyy");
					String totalMonth = StringUtils.formatMoney(bo.getTotal());
					String remainMonth = StringUtils
							.formatMoney(bo.getRemain());
					String totalToday = StringUtils.formatMoney(bo
							.getTotalToday());
					String remainToday = StringUtils.formatMoney(bo
							.getRemainToday());
					tvNotify.setText(getString(R.string.summery_cus_care,
							month, remainMonth + "/" + totalMonth, day,
							remainToday + "/" + totalToday));

				} else {
					tvNotify.setText("");
				}
			} else {
				if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
					CommonActivity.BackFromLogin(getActivity(),
							result.getDescription(), permission);
				} else {
					tvNotify.setText(result.getDescription());

				}
			}
		}
	};
	OnPostExecute<CCOutput> onPostGetCustCareDetail = new OnPostExecute<CCOutput>() {

		@Override
		public void onPostExecute(CCOutput result) {
			prbNotify.setVisibility(View.GONE);
			if (result.getErrorCode().equals("0")) {
				loadmore = false;
				if (!CommonActivity.isNullOrEmpty(result
						.getListSubscriberCareBean())) {
					List<SubscriberCareBean> tmp = result
							.getListSubscriberCareBean();
					// for (SubscriberCareBean item : tmp) {
					// if (CommonActivity.isNullOrEmpty(item.getGiftStatus())) {
					// item.setStatus("0");
					// }
					// }
					lstSubscriberCareBeans.addAll(tmp);
					adapter.notifyDataSetChanged();
					if (result.getListSubscriberCareBean().size() == AsyncGetCustCareDetail.MAX_RESULT) {
						loadmore = true;
					}
				}
			} else {
				if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
					CommonActivity.BackFromLogin(getActivity(),
							result.getDescription(), permission);
				} else {
					String description = result.getDescription();
					if (CommonActivity.isNullOrEmpty(description)) {
						description = getActivity().getString(R.string.no_data_return);
					}
					CommonActivity.createAlertDialog(
							MainActivity.getInstance(), description,
							getActivity().getString(R.string.app_name)).show();
				}
			}
		}
	};

	OnClickListener onClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnSearch:
				Date fromDate = DateTimeUtils.convertStringToTime(tvFromDate
						.getText().toString(), "dd/MM/yyyy");
				Date toDate = DateTimeUtils.convertStringToTime(tvToDate
						.getText().toString(), "dd/MM/yyyy");

				if (fromDate.after(toDate) ) {
					CommonActivity.createAlertDialog(getActivity(),
							R.string.report_warn_todate_fromdate,
							R.string.app_name).show();
					return;
				}

				if (DateTimeUtils.calculateDays2Date(toDate, fromDate) > 30) {
					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.duration_over_load, 30),
							getString(R.string.app_name)).show();
					return;
				}

				lstSubscriberCareBeans.clear();
				adapter.notifyDataSetChanged();
				FormBean prog = (FormBean) spnProgram.getSelectedItem();
				FormBean status = (FormBean) spnStatus.getSelectedItem();
				AsyncGetCustCareDetail asy = new AsyncGetCustCareDetail(edtIsdn
						.getText().toString().trim(), prog.getId(), tvFromDate
						.getText().toString(), tvToDate.getText().toString(),
						status.getId(), lstSubscriberCareBeans.size(),
						onPostGetCustCareDetail, getActivity());
				asy.execute();
				break;
			case R.id.tvFromDate:
				CommonActivity.showDatePickerDialog(getActivity(),
						DateTimeUtils.convertStringToTime(tvFromDate.getText()
								.toString(), "dd/MM/yyyy"), fromDateCallBack);
				break;
			case R.id.tvToDate:
				CommonActivity.showDatePickerDialog(getActivity(),
						DateTimeUtils.convertStringToTime(tvToDate.getText()
								.toString(), "dd/MM/yyyy"), toDateCallBack);
				break;
			default:
				break;
			}
		}
	};

	OnPostExecute<CCOutput> onPostExecuteUpdate = new OnPostExecute<CCOutput>() {
		@Override
		public void onPostExecute(final CCOutput result) {
			if (result.getErrorCode().equals("0")) {
				OnClickListener searchClick = new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// for (int i = 0; i < lstSubscriberCareBeans.size();
						// i++) {
						// if (lstSubscriberCareBeans
						// .get(i)
						// .getSubCareDetailId()
						// .equals(result.getSubscriberCareBean()
						// .getSubCareDetailId())) {
						// lstSubscriberCareBeans.remove(i);
						// break;
						// }
						// }
						lstSubscriberCareBeans.clear();
						adapter.notifyDataSetChanged();
						FormBean prog = (FormBean) spnProgram.getSelectedItem();
						FormBean status = (FormBean) spnStatus
								.getSelectedItem();
						AsyncGetCustCareDetail asy = new AsyncGetCustCareDetail(
								edtIsdn.getText().toString().trim(),
								prog.getId(), tvFromDate.getText().toString(),
								tvToDate.getText().toString(), status.getId(),
								lstSubscriberCareBeans.size(),
								onPostGetCustCareDetail, getActivity());
						asy.execute();

					}
				};
				CommonActivity.createAlertDialog(MainActivity.getInstance(),
						getString(R.string.gift_update_success),
						getString(R.string.app_name), searchClick).show();
				dialogUpdate.dismiss();

				// adapter = new SubscriberCareBeanAdapter(act,
				// lstSubscriberCareBeans);
				// lvGift.setAdapter(adapter);

			} else {
				if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
					CommonActivity.BackFromLogin(MainActivity.getInstance(),
							result.getDescription(), ";update.gift;");
				} else {
					String description = result.getDescription();
					if (CommonActivity.isNullOrEmpty(description)) {
						description = getString(R.string.updatefail);
					}
					CommonActivity.createAlertDialog(
							MainActivity.getInstance(), description,
							getString(R.string.app_name)).show();

				}
			}
		}
	};

	@Override
	public void setPermission() {
		permission = ";update.gift;";
	}

	private OnDateSetListener fromDateCallBack = new OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			if (!view.isShown()) {
				return;
			}
			Calendar cal = Calendar.getInstance();
			cal.set(year, monthOfYear, dayOfMonth);
			// Date fromDate = cal.getTime();
			// Date toDate =
			// DateTimeUtils.convertStringToTime(tvToDate.getText()
			// .toString(), "dd/MM/yyyy");
			// if (DateTimeUtils.calculateDays2Date(fromDate, toDate) > 0) {
			// CommonActivity
			// .createAlertDialog(getActivity(),
			// R.string.report_warn_todate_fromdate,
			// R.string.app_name).show();
			// return;
			// // cal.setTime(toDate);
			// }

			tvFromDate.setText(DateTimeUtils.convertDateTimeToString(
					cal.getTime(), "dd/MM/yyyy"));
			clearOldSearch();
		}
	};

	private OnDateSetListener toDateCallBack = new OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			if (!view.isShown()) {
				return;
			}
			Calendar cal = Calendar.getInstance();
			cal.set(year, monthOfYear, dayOfMonth);
//			Date toDate = cal.getTime();
//			Date fromDate = DateTimeUtils.convertStringToTime(tvFromDate
//					.getText().toString(), "dd/MM/yyyy");
//			if (DateTimeUtils.calculateDays2Date(fromDate, toDate) > 0) {
//				CommonActivity
//						.createAlertDialog(getActivity(),
//								R.string.report_warn_todate_fromdate,
//								R.string.app_name).show();
//				return;
//			}
			// if (toDate.after(new Date())) {
			// cal = Calendar.getInstance();
			//
			// }
			tvToDate.setText(DateTimeUtils.convertDateTimeToString(
					cal.getTime(), "dd/MM/yyyy"));
			getActivity();
			clearOldSearch();
		}
	};

	private void clearOldSearch() {
		lstSubscriberCareBeans.clear();
		adapter.notifyDataSetChanged();
	}

	private void initStatus() {
		final List<FormBean> lstStatus = new ArrayList<FormBean>();

		FormBean all = new FormBean();
		all.setId("");
		all.setName(getString(R.string.all));
		lstStatus.add(all);
		FormBean fail = new FormBean();
		fail.setId("0");
		fail.setName(getString(R.string.update_gift_fail));
		lstStatus.add(fail);
		FormBean success = new FormBean();
		success.setId("1");
		success.setName(getString(R.string.updated_gift));
		lstStatus.add(success);

		ArrayAdapter<FormBean> statusAdapter = new ArrayAdapter<FormBean>(
				getActivity(), R.layout.spinner_item, lstStatus);
		spnStatus.setAdapter(statusAdapter);

	}
}
