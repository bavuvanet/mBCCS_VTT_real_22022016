package com.viettel.bss.viettelpos.v4.sale.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.sale.adapter.BankplusOrderAdapter;
import com.viettel.bss.viettelpos.v4.sale.handler.BankplusOrderBOHandler;
import com.viettel.bss.viettelpos.v4.sale.object.BankplusOrderBO;
import com.viettel.bss.viettelpos.v4.sale.object.ChannelTypeObject;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FragmentSaleByOrder extends Fragment implements OnClickListener,
		OnItemClickListener {
	public static final String STATUS_PENDING = "1";
	public static final String STATUS_HAVE_HANDLED = "2";
	private static final int REQUEST_CHANGE_CHANNEL = 1;
	private static final int REQUEST_HANDLE_ORDER = 2;
	private Staff selectedStaff;
	private ChannelTypeObject channelType;

	private View view;

	private EditText tvFromDate;
	private EditText tvToDate;
	private TextView tvChooseChannel;
	private View imgDeleteChannel;

	private EditText edtBankPlusMobile;
	private int fromYear;
	private int fromMonth;
	private int fromDay;
	private int toYear;
	private int toMonth;
	private int toDay;
	private Date dateFrom = null;
	private Date dateTo = null;
	private String dateFromString = "";
	private String dateToString = "";
	private View btnSearch;
	private ListView lvOrder;
	private View prbSearching;
	private String errorMessage = "";
	private Spinner spiStatus;
	private final String LOG_TAG = FragmentSaleByOrder.class.getName();
	private String status = "";
	private final List<BankplusOrderBO> lstOrder = new ArrayList<>();
	private SearchOrderAsy searchOrderAsy;
	private BankplusOrderAdapter adapter;
	private BankplusOrderBO orderBO;
	private Button btnHome;
	private TextView txtNameActionBar;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
        MainActivity.getInstance().setTitleActionBar(R.string.list_order);
		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.sale_export_by_order, container,
					false);
			tvChooseChannel = (TextView) view
					.findViewById(R.id.tvChooseChannel);
			tvChooseChannel.setOnClickListener(this);

			imgDeleteChannel = view.findViewById(R.id.imgDeleteChannel);
			imgDeleteChannel.setVisibility(View.GONE);
			imgDeleteChannel.setOnClickListener(this);

			tvFromDate = (EditText) view.findViewById(R.id.edtFromDate);
			tvFromDate.setOnClickListener(this);
			tvToDate = (EditText) view.findViewById(R.id.edtToDate);
			tvToDate.setOnClickListener(this);
			edtBankPlusMobile = (EditText) view
					.findViewById(R.id.edtBankplusMobile);
			btnSearch = view.findViewById(R.id.btnSearchOrder);
			btnSearch.setOnClickListener(this);
			lvOrder = (ListView) view.findViewById(R.id.lvOrder);
			lvOrder.setOnItemClickListener(this);
			prbSearching = view.findViewById(R.id.prbSearching);
			prbSearching.setVisibility(View.GONE);
			spiStatus = (Spinner) view.findViewById(R.id.spiStatus);
			initValue();
		}
		return view;
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
		case R.id.imgDeleteChannel:
			selectedStaff = null;
			channelType = null;
			tvChooseChannel.setHint(getResources().getString(
					R.string.chooseChannelOrStaff));
			imgDeleteChannel.setVisibility(View.GONE);
			break;
		case R.id.edtFromDate:
			DatePickerDialog fromDateDialog = new FixedHoloDatePickerDialog(
					getActivity(), AlertDialog.THEME_HOLO_LIGHT, fromDatePickerListener, fromYear, fromMonth,
					fromDay);
			fromDateDialog.show();
			break;
		case R.id.edtToDate:
			DatePickerDialog toDateDialog = new FixedHoloDatePickerDialog(getActivity(),
					AlertDialog.THEME_HOLO_LIGHT,toDatePickerListener, toYear, toMonth, toDay);
			toDateDialog.show();
			break;
		case R.id.btnSearchOrder:

			if (searchOrderAsy != null) {
				searchOrderAsy.cancel(false);
			}
			if (!CommonActivity.isNetworkConnected(getActivity())) {
				CommonActivity.createAlertDialog(getActivity(),
						R.string.errorNetwork, R.string.app_name).show();
				return;
			}
			if (dateFrom != null && dateTo != null) {
				if (dateFrom.before(dateTo) || dateFrom.compareTo(dateTo) == 0) {
					searchOrderAsy = new SearchOrderAsy(getActivity());
					searchOrderAsy.execute();

				} else {
					Toast.makeText(
							getActivity(),
							getResources().getString(
									R.string.checktimeupdatejob),
							Toast.LENGTH_SHORT).show();
				}
			}

			break;
		case R.id.tvChooseChannel:
			FragmentChooseChannel fragment = new FragmentChooseChannel();
			fragment.setTargetFragment(this, REQUEST_CHANGE_CHANNEL);
			ReplaceFragment.replaceFragment(getActivity(), fragment, false);
			break;
		default:
			break;
		}
	}

	private final DatePickerDialog.OnDateSetListener fromDatePickerListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
            fromMonth = selectedMonth;
			fromDay = selectedDay;
			StringBuilder strDate = new StringBuilder();
			if (fromDay < 10) {
				strDate.append("0");
			}
			strDate.append(fromDay).append("/");
			if (fromMonth < 9) {
				strDate.append("0");
			}
			strDate.append(fromMonth+1).append("/");
			strDate.append(selectedYear);

			dateFromString = String.valueOf(selectedYear) + "-" +
                    (fromMonth + 1) + "-" + fromDay;
			Log.d("dateFromString", dateFromString);
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				dateFrom = sdf.parse(dateFromString);
			} catch (Exception e) {
				e.printStackTrace();
			}
			tvFromDate.setText(strDate);
		}
	};
	private final DatePickerDialog.OnDateSetListener toDatePickerListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			toYear = selectedYear;
			toMonth = selectedMonth;
			toDay = selectedDay;
			StringBuilder strDate = new StringBuilder();
			if (toDay < 10) {
				strDate.append("0");
			}
			strDate.append(toDay).append("/");
			if (toMonth < 9) {
				strDate.append("0");
			}
			strDate.append(toMonth +1).append("/");
			strDate.append(toYear);
			dateToString = String.valueOf(toYear) + "-" +
                    (toMonth + 1) + "-" + toDay;
			Log.d("dateToString", dateToString);
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				dateTo = sdf.parse(dateToString);
			} catch (Exception e) {
				e.printStackTrace();
			}
			tvToDate.setText(strDate);
		}
	};

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case REQUEST_CHANGE_CHANNEL:
				selectedStaff = (Staff) data.getExtras().getSerializable(
						"staff");
				channelType = (ChannelTypeObject) data.getExtras()
						.getSerializable("channelType");

				if (selectedStaff != null) {
					tvChooseChannel.setHint(selectedStaff.getName() + " - "
							+ selectedStaff.getStaffCode());
					imgDeleteChannel.setVisibility(View.VISIBLE);
				} else if (channelType != null
						&& channelType.getChannelTypeId().intValue() != -1) {
					tvChooseChannel.setHint(channelType.getName());
					imgDeleteChannel.setVisibility(View.VISIBLE);
				} else {
					tvChooseChannel.setHint(getResources().getString(
							R.string.chooseChannelOrStaff));
					imgDeleteChannel.setVisibility(View.GONE);
				}
				break;
			case REQUEST_HANDLE_ORDER:
				for (int i = 0; i < lstOrder.size(); i++) {
					if (lstOrder.get(i).getOrderCode()
							.equals(orderBO.getOrderCode())) {
						lstOrder.remove(i);
						break;
					}
				}
				adapter.notifyDataSetChanged();
				break;
			default:
				break;
			}

		} else {
			channelType = null;
			selectedStaff = null;
			imgDeleteChannel.setVisibility(View.GONE);
		}
	}

	private String searchOrder() {
		try {

			StringBuilder rawData = new StringBuilder();
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_findBankPlusOrderList");
			rawData.append("<ws:findBankPlusOrderList>");
			rawData.append("<bankPlusInput>");
			rawData.append("<token>").append(Session.getToken()).append("</token>");
			if (!edtBankPlusMobile.getText().toString().trim().isEmpty()) {
				rawData.append("<blankPlusMobile>");
				rawData.append(edtBankPlusMobile.getText().toString().trim());
				rawData.append("</blankPlusMobile>");
			}

			rawData.append("<fromDate>");
			rawData.append(tvFromDate.getText().toString().trim()).append(" 00:00:00");
			rawData.append("</fromDate>");
			rawData.append("<toDate>");
			rawData.append(tvToDate.getText().toString().trim()).append(" 00:00:00");
			rawData.append("</toDate>");

			if (selectedStaff != null) {
				rawData.append("<staffCode>");
				rawData.append(selectedStaff.getStaffCode());
				rawData.append("</staffCode>");
			}

			rawData.append("<status>");
			rawData.append(status);
			rawData.append("</status>");
			rawData.append("</bankPlusInput>");
			rawData.append("</ws:findBankPlusOrderList>");

			String envelope = input.buildInputGatewayWithRawData(rawData
					.toString());
			Log.e(LOG_TAG, envelope);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,getActivity(),"mbccs_findBankPlusOrderList");
			Log.e(LOG_TAG, response);
			CommonOutput output = input.parseGWResponse(response);
			if (!output.getError().equals("0")) {
				errorMessage = output.getDescription();
				return Constant.ERROR_CODE;
			}

			String original = output.getOriginal();
			BankplusOrderBOHandler handler = (BankplusOrderBOHandler) CommonActivity
					.parseXMLHandler(new BankplusOrderBOHandler(), original);
			output = handler.getItem();
			if (output.getToken() != null && !output.equals("")) {
				Session.setToken(output.getToken());
			}
			// end lay token
			if (!output.getErrorCode().equals("0")) {
				errorMessage = output.getDescription();
				return Constant.ERROR_CODE;
			}

			if (handler.getResult() != null) {
				lstOrder.addAll(handler.getResult());
			}

			if (lstOrder == null || lstOrder.isEmpty()) {
				errorMessage = getResources().getString(R.string.no_data);
				return Constant.ERROR_CODE;
			}
			return Constant.SUCCESS_CODE;

		} catch (Exception e) {
			Log.e(LOG_TAG, "Exception", e);
			errorMessage = getResources().getString(R.string.exception)
					+ e.toString();
			return Constant.ERROR_CODE;
		}
	}

	private class SearchOrderAsy extends AsyncTask<Void, Void, String> {
		private final Activity activity;

		private SearchOrderAsy(Activity activity) {
			if (lstOrder != null) {
				lstOrder.clear();
			}
			this.activity = activity;
			lvOrder.setVisibility(View.GONE);
			prbSearching.setVisibility(View.VISIBLE);
		}

		@Override
		protected String doInBackground(Void... arg0) {
			return searchOrder();
		}

		@Override
		protected void onPostExecute(String result) {
			btnSearch.setEnabled(true);
			prbSearching.setVisibility(View.GONE);
			if (result.equals(Constant.SUCCESS_CODE)) {
				lvOrder.setVisibility(View.VISIBLE);
				if (adapter != null) {
					adapter.setStatus(status);
					adapter.notifyDataSetChanged();
				} else {
					adapter = new BankplusOrderAdapter(activity, lstOrder,
							status);
					lvOrder.setAdapter(adapter);
				}

			} else {
				// That bai, hien thi thong bao ket qua
				String message = errorMessage;
				String title = getString(R.string.app_name);
				Dialog dialog = CommonActivity.createAlertDialog(activity,
						message, title);
				dialog.show();
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		for (BankplusOrderBO item : lstOrder) {
			item.setChecked(false);
		}
		lstOrder.get(position).setChecked(true);
		adapter.notifyDataSetChanged();
		Bundle bundle = new Bundle();
		orderBO = lstOrder.get(position);
		if (status == STATUS_HAVE_HANDLED) {
			return;
		}

		if (!CommonActivity.isNetworkConnected(getActivity())) {
			CommonActivity.createAlertDialog(getActivity(),
					R.string.errorNetwork, R.string.app_name).show();
			return;
		}
		bundle.putSerializable("orderBO", lstOrder.get(position));
		bundle.putInt("functionType", FragmentSaleSaling.FUNCTION_SALE_BY_ORDER);
		FragmentSaleSaling fragment = new FragmentSaleSaling();
		fragment.setTargetFragment(this, REQUEST_HANDLE_ORDER);
		fragment.setArguments(bundle);
		ReplaceFragment.replaceFragment(getActivity(), fragment, false);
	}

	private void initValue() {
		Calendar cal = Calendar.getInstance();
		fromYear = cal.get(Calendar.YEAR);
		fromMonth = cal.get(Calendar.MONTH);
		fromDay = cal.get(Calendar.DAY_OF_MONTH);
		tvFromDate.setText(DateTimeUtils.convertDateTimeToString(cal.getTime(),
				"dd/MM/yyyy"));
		dateFromString = String.valueOf(fromYear) + "-" +
                fromMonth + "-" + fromDay;
		Log.d("dateFromString", dateFromString);
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			dateFrom = sdf.parse(dateFromString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		toYear = cal.get(Calendar.YEAR);
		toMonth = cal.get(Calendar.MONTH);
		toDay = cal.get(Calendar.DAY_OF_MONTH);
		dateToString = String.valueOf(toYear) + "-" +
                toMonth + "-" + toDay;
		Log.d("dateToString", dateToString);
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			dateTo = sdf.parse(dateToString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tvToDate.setText(DateTimeUtils.convertDateTimeToString(cal.getTime(),
				"dd/MM/yyyy"));
		ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                R.layout.simple_list_item_single_row, R.id.text1);
		adapter.add(getResources().getString(R.string.pending));
		adapter.add(getResources().getString(R.string.have_handled));
		spiStatus.setAdapter(adapter);
		spiStatus.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if (position == 0) {
					status = STATUS_PENDING;
				} else {
					status = STATUS_HAVE_HANDLED;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
	}
}
