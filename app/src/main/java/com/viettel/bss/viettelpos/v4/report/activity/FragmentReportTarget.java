package com.viettel.bss.viettelpos.v4.report.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.report.object.TargetObject;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FragmentReportTarget extends FragmentCommon {

	private EditText edtReportDate;

    private ArrayAdapter<String> adapter;

	private Date fromDate;

	private TableLayout table;
	private TableLayout header;


    @Override
	public void onResume() {
		super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.report_target));
        ((MainActivity)getActivity()).enableViews(true);
	}

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		idLayout = R.layout.layout_report_target;
		getActivity().setRequestedOrientation(
				ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if(isVisibleToUser) {
			Activity a = getActivity();
			if(a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void unit(View v) {
		edtReportDate = (EditText) v.findViewById(R.id.edt_report_date);
        Button btnExportReport = (Button) v.findViewById(R.id.btnExportReport);
		table = (TableLayout) v.findViewById(R.id.table);
		header = (TableLayout) v.findViewById(R.id.header);
		edtReportDate.setOnClickListener(this);
		btnExportReport.setOnClickListener(this);
        LinearLayout lnTable = (LinearLayout) v.findViewById(R.id.lnTable);
		Calendar calendar = Calendar.getInstance();
		String reportDate = calendar.get(Calendar.DATE) + "/"
				+ (calendar.get(Calendar.MONTH) + 1) + "/"
				+ calendar.get(Calendar.YEAR);
		edtReportDate.setText(reportDate);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		fromDate = calendar.getTime();
	}

	@Override
	public void onClick(View view) {
		super.onClick(view);
		switch (view.getId()) {

		case R.id.edt_report_date:

			OnDateSetListener onDateSetListener = new OnDateSetListener() {
				public void onDateSet(DatePicker view, int year, int month,
						int day) {
					String reportDate = day + "/" + (month + 1) + "/" + year;
					edtReportDate.setText(reportDate);
					Calendar calendar = Calendar.getInstance();
					calendar.set(year, month, day, 0, 0, 0);
					fromDate = calendar.getTime();
				}
			};
			DatePickerDialog pic = null;
			Calendar cal = Calendar.getInstance();
			if (!CommonActivity.isNullOrEmpty(edtReportDate.getText()
					.toString())) {
				Date date = DateTimeUtils.convertStringToTime(edtReportDate
						.getText().toString(), "dd/MM/yyyy");

				cal.setTime(date);
			}
			pic = new FixedHoloDatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT,onDateSetListener,
					cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
					cal.get(Calendar.DAY_OF_MONTH));
			pic.show();
			break;

		case R.id.btnExportReport:
			if (CommonActivity.isNetworkConnected(getActivity())) {
				if (fromDate == null) {
					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.edt_report_date_select),
							getString(R.string.app_name)).show();
					break;
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				AsyncContractReport asynctaskSearchContract = new AsyncContractReport(
						sdf.format(fromDate));
				asynctaskSearchContract.execute();
			} else {
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.errorNetwork),
						getString(R.string.app_name)).show();
			}
			break;
		default:
			break;
		}
	}

	private class AsyncContractReport extends
			AsyncTask<String, Void, TargetObject> {

		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private final ProgressDialog progress;

		private final String fromDate;

		public AsyncContractReport(String _fromDate) {
			fromDate = _fromDate;

			this.progress = new ProgressDialog(getActivity());
			this.progress.setCancelable(false);
			this.progress.setMessage(getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

        @Override
		protected TargetObject doInBackground(String... params) {
			return getTargetData();
		}

		@Override
		protected void onPostExecute(TargetObject result) {

			super.onPostExecute(result);

			progress.dismiss();

			if (errorCode.equals("0")) {

				if (result != null && result.getLstHeader() != null
						&& result.getLstHeader().length > 0) {

					TableRow tableRow;
					TextView textView;
					table.removeAllViews();
					header.removeAllViews();
					int numOfCol = result.getLstHeader().length;

					tableRow = new TableRow(getActivity());
					int textLength = 0;
					int columnIndex = 0;
					for (int i = 0; i < numOfCol; i++) {
						if (result.getLstHeader()[i].length() > textLength) {
							textLength = result.getLstHeader()[i].length();
							columnIndex = i;
						}
					}

					textView = new TextView(getActivity());
					textView.setText(result.getLstHeader()[columnIndex]);
					textView.setPadding(15, 15, 15, 15);
					textView.setWidth(200);
					Display display = getActivity().getWindowManager()
							.getDefaultDisplay();
					int width = display.getWidth();
					int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(
							width, View.MeasureSpec.AT_MOST);
					int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0,
							View.MeasureSpec.UNSPECIFIED);
					textView.measure(widthMeasureSpec, heightMeasureSpec);
					int maxHeight = textView.getMeasuredHeight();

					for (int i = 0; i < numOfCol; i++) {
						TextView tv = new TextView(getActivity());
						tv.setText(result.getLstHeader()[i]);
						tv.setPadding(15, 15, 15, 15);

						tv.setBackgroundResource(R.drawable.cell_shape_background);
						// LinearLayout.LayoutParams params = new
						// LinearLayout.LayoutParams(
						// LayoutParams.WRAP_CONTENT,
						// LayoutParams.WRAP_CONTENT);
						// params.gravity = Gravity.CENTER;
						// tv.setLayoutParams(params);
						if (i == 0) {
							tv.setWidth(80);
						} else {
							tv.setWidth(200);
						}

						tv.setHeight(maxHeight);
						tableRow.addView(tv);
					}
					header.addView(tableRow);
					for (int i = 0; i < result.getLstValue().size(); i++) {
						tableRow = new TableRow(getActivity());
						textView = new TextView(getActivity());
						textView.setText(result.getLstValue().get(i)[1]);
						textView.setPadding(15, 15, 15, 15);
						textView.setWidth(200);

						textView.measure(widthMeasureSpec, heightMeasureSpec);
						int maxHeightValue = textView.getMeasuredHeight();

						for (int j = 0; j < numOfCol; j++) {
							TextView tv = new TextView(getActivity());
							tv.setText(result.getLstValue().get(i)[j]);
							tv.setPadding(15, 15, 15, 15);
							tv.setHeight(maxHeightValue);
							// tv.setGravity(Gravity.CENTER);
							if (j == 0) {
								tv.setWidth(80);
							} else {
								tv.setWidth(200);
							}
							if (i % 2 == 0) {
								tv.setBackgroundResource(R.drawable.cell_shape);
							} else {
								tv.setBackgroundResource(R.drawable.cell_shape_odd);
							}

							tableRow.addView(tv);
						}
						table.addView(tableRow);
					}
				} else {

					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.no_data),
							getString(R.string.app_name)).show();
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity
							.createAlertDialog(getActivity(), description, getResources()
									.getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();
				}
			}

		}

		private TargetObject getTargetData() {
			String original = "";
			TargetObject obj = new TargetObject();
			// int numColl = 10;
			// String[] header = new String[numColl];
			// for (int i = 0; i < numColl; i++) {
			// if (i == 3) {
			// header[i] = "Phung Quoc Huy ABC 1234 hic hic ";
			// } else if (i == 5) {
			// header[i] = "Phung Quoc Huy ";
			// }
			//
			// else {
			// header[i] = "header: " + i;
			// }
			//
			// }
			// obj.setLstHeader(header);
			// List<String[]> value = new ArrayList<String[]>();
			// for (int i = 0; i < 20; i++) {
			// String[] tmp = new String[numColl];
			// for (int j = 0; j < numColl; j++) {
			// if (j == 0) {
			// tmp[j] = i + "";
			// } else if (j == 1 && i == 2) {
			// tmp[j] = "Tỉ lệ card bán ra cho điểm bán";
			// } else if (j == 1 && i == 6) {
			// tmp[j] = "Tỉ lệ thuê bao KIT bán ra cho điểm bán";
			// } else {
			// tmp[j] = "value";
			// }
			// }
			// value.add(tmp);
			// }
			// obj.setLstValue(value);
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getViewDataDaily");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getViewDataDaily>");
				rawData.append("<reportInput>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<fromDate>").append(edtReportDate.getText().toString()).append("</fromDate>");
				rawData.append("</reportInput>");
				rawData.append("</ws:getViewDataDaily>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getViewDataDaily");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original group", original);

				// ==== parse xml list ip

				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;

				List<String> lstHeader = new ArrayList<>();
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					nodechild = doc.getElementsByTagName("lstHeader");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						String headerItem = XmlDomParse
								.getCharacterDataFromElement(e1);
						lstHeader.add(headerItem);
					}
					NodeList nodeValue = doc.getElementsByTagName("lstValue");
					obj.setLstHeader(lstHeader.toArray(new String[lstHeader
							.size()]));
					List<String[]> lstValue = new ArrayList<>();
					for (int j = 0; j < nodeValue.getLength(); j++) {
						Element e3 = (Element) nodeValue.item(j);
						List<String> tmp = new ArrayList<>();
						NodeList nodeItem = e3.getElementsByTagName("item");
						for (int k = 0; k < nodeItem.getLength(); k++) {
							Element e4 = (Element) nodeItem.item(k);
							String item = XmlDomParse
									.getCharacterDataFromElement(e4);
							tmp.add(item);
						}
						lstValue.add(tmp.toArray(new String[tmp.size()]));

					}
					obj.setLstValue(lstValue);
				}
			} catch (Exception e) {
				Log.e(Constant.TAG, e.toString(), e);
			}
			errorCode = "0";
			return obj;
		}
	}

	@Override
	public void setPermission() {
		// TODO Auto-generated method stub
		
	}
}
