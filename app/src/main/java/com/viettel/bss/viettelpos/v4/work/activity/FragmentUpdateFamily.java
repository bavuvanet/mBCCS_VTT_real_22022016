package com.viettel.bss.viettelpos.v4.work.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.object.Location;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;
import com.viettel.bss.viettelpos.v4.work.adapter.GetFamilyInfoAdapter;
import com.viettel.bss.viettelpos.v4.work.adapter.GetFamilyInfoAdapter.OnDeleteFamily;
import com.viettel.bss.viettelpos.v4.work.adapter.GetFamilyInfoAdapter.OnEditFamily;
import com.viettel.bss.viettelpos.v4.work.object.AreaInforStaff;
import com.viettel.bss.viettelpos.v4.work.object.FamilyInforBean;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class FragmentUpdateFamily extends Fragment implements OnClickListener,
		OnDeleteFamily, OnEditFamily {

	private View mView = null;

	private ListView lvFamily;

    private GetFamilyInfoAdapter adapter = null;

	private AreaInforStaff areaInforStaff = null;

	private List<FamilyInforBean> lstFamilyInforBeans = new ArrayList<>();

	private boolean check = false;

	private Date dateNow = null;
	private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private EditText edtsearch;
	private String lat = "0";
	private String lon = "0";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Calendar cal = Calendar.getInstance();
		dateNow = cal.getTime();

		Bundle mBundle = getArguments();
		if (mBundle != null) {
			areaInforStaff = (AreaInforStaff) mBundle
					.getSerializable("areaInforStaff");
		}
		if (mView == null) {
			mView = inflater.inflate(R.layout.layout_update_family, container,
					false);
			check = true;
			unitView(mView);
		}

		return mView;
	}

	@Override
	public void onResume() {
		super.onResume();
		MainActivity.getInstance().setTitleActionBar(R.string.updateHD);

		if (FragmentUpdateFamilyDetail.familyInforBean != null) {
			FragmentUpdateFamilyDetail.familyInforBean = null;
		}

		if (FragmentUpdateFamilyDetail.hashMap != null) {
			FragmentUpdateFamilyDetail.hashMap = new HashMap<>();
		}

	}

	// khoi tao view
	private void unitView(View mView) {

		edtsearch = (EditText) mView.findViewById(R.id.edtsearch);
		edtsearch.setVisibility(View.VISIBLE);
		edtsearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				String input = edtsearch.getText().toString()
						.toLowerCase(Locale.getDefault());

				if (adapter != null) {
					lstFamilyInforBeans = adapter.SearchInput(input);
					lvFamily.setAdapter(adapter);
					adapter.notifyDataSetChanged();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {

			}
		});

		lvFamily = (ListView) mView.findViewById(R.id.lvFamily);
		lvFamily.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				FamilyInforBean familyInforBean = lstFamilyInforBeans.get(arg2);
				Bundle mBundle = new Bundle();
				FragmentUpdateFamilyDetail fragmentUpdateFamilyDetail = new FragmentUpdateFamilyDetail();
				mBundle.putSerializable("familyInforBean", familyInforBean);
				fragmentUpdateFamilyDetail.setArguments(mBundle);
				ReplaceFragment.replaceFragment(getActivity(),
						fragmentUpdateFamilyDetail, false);

			}
		});
        Button btncapnhat = (Button) mView.findViewById(R.id.btncapnhat);
		btncapnhat.setOnClickListener(this);
		btncapnhat.setVisibility(View.GONE);

        Button btnthemmoi = (Button) mView.findViewById(R.id.btnthemmoi);
		btnthemmoi.setOnClickListener(this);

		if (check && areaInforStaff != null
				&& !CommonActivity.isNullOrEmpty(areaInforStaff.getAreaCode())) {
			GetLstFamilyAsyn getLstFamilyAsyn = new GetLstFamilyAsyn(
					getActivity());
			getLstFamilyAsyn.execute(areaInforStaff.getAreaCode(), "");
		}
	}

	// show thong tin them moi ho dan
	private Dialog dialog;
	private String chucvu = "";

	// private EditText edtdiachi, edtmahodan, edttenhodan, edtphone_number,
	// edtbirth_day, edittoadox, edittoadoy;

	private void showDialogInsertFamily(final FamilyInforBean item, int type) {

		dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.layout_insert_family);

		LinearLayout lnmahodan = (LinearLayout) dialog
				.findViewById(R.id.lnmahodan);
		if (item == null) {
			lnmahodan.setVisibility(View.GONE);
		} else {
			lnmahodan.setVisibility(View.VISIBLE);

		}

		final EditText edtdiachi = (EditText) dialog
				.findViewById(R.id.edtdiachi);
		if (item != null) {
			edtdiachi.setText(item.getAddress());
		}

		final EditText edtmahodan = (EditText) dialog
				.findViewById(R.id.edtmahodan);
		if (item != null) {
			edtmahodan.setText(item.getFamilyInforCode());
			edtmahodan.setEnabled(false);
		}
		final EditText edttenhodan = (EditText) dialog
				.findViewById(R.id.edttenhodan);
		if (item != null) {
			edttenhodan.setText(item.getFamilyName());
		}
		final EditText edtphone_number = (EditText) dialog
				.findViewById(R.id.edtphone_number);
		if (item != null) {
			edtphone_number.setText(item.getPhone());
		}

		final EditText edtbirth_day = (EditText) dialog
				.findViewById(R.id.edtbirth_day);
		if (item != null) {
			edtbirth_day.setText(item.getBirthDay());
		}

		Location location = CommonActivity.findMyLocation(getActivity());
		if (location != null) {

			lat = location.getX();
			lon = location.getY();
		} else {

			CommonActivity.createAlertDialog(getActivity(),
					getResources().getString(R.string.checkgps),
					getResources().getString(R.string.app_name)).show();

			lat = "";
			lon = "";
		}
		edtbirth_day.setOnClickListener(editTextListener);
		final EditText edittoadox = (EditText) dialog
				.findViewById(R.id.edittoadox);
		if (item != null) {
			edittoadox.setText(item.getX());
		} else {
			edittoadox.setText(lat);
		}
		final EditText edittoadoy = (EditText) dialog
				.findViewById(R.id.edittoadoy);
		if (item != null) {
			edittoadoy.setText(item.getY());
		} else {
			edittoadoy.setText(lon);
		}
		final EditText edtnghenghiep = (EditText) dialog
				.findViewById(R.id.edtnghenghiep);
		if (item != null) {
			edtnghenghiep.setText(item.getJob());
		}

		final Spinner spinner_chucvu = (Spinner) dialog
				.findViewById(R.id.spinner_chucvu);
		List<Spin> arrSpins = new ArrayList<>();
		arrSpins.add(new Spin("", getActivity().getString(R.string.txt_select)));
		arrSpins.add(new Spin("TTHON", getActivity().getString(
				R.string.truongthon)));
		Utils.setDataSpinner(getActivity(), arrSpins, spinner_chucvu);

		if (item != null && !CommonActivity.isNullOrEmpty(item.getPositionCode())) {
			for (Spin spin : arrSpins) {
				if (item.getPositionCode().equals(spin.getId())) {
					spinner_chucvu.setSelection(arrSpins.indexOf(spin));
					break;
				}
			}
		}

		spinner_chucvu.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Spin item = (Spin) arg0.getItemAtPosition(arg2);
				if (item != null) {
					chucvu = item.getId();
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		Button btnthemmoi = (Button) dialog.findViewById(R.id.btnthemmoi);
		btnthemmoi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (CommonActivity
						.isNullOrEmpty(edtdiachi.getText().toString())) {

					Toast.makeText(getActivity(),
							getActivity().getString(R.string.addressempty),
							Toast.LENGTH_SHORT).show();
					return;
				}

				if (StringUtils.CheckCharSpecical(edtdiachi.getText()
						.toString())) {

					Toast.makeText(
							getActivity(),
							getActivity().getString(
									R.string.checkcontentspecial),
							Toast.LENGTH_SHORT).show();
					return;
				}

				// if (CommonActivity.isNullOrEmpty(edtmahodan.getText()
				// .toString())) {
				//
				// Toast.makeText(getActivity(),
				// getActivity().getString(R.string.mahdempty),
				// Toast.LENGTH_SHORT).show();
				// return;
				// }
				if (CommonActivity.isNullOrEmpty(edttenhodan.getText()
						.toString())) {

					Toast.makeText(getActivity(),
							getActivity().getString(R.string.tenhdempty),
							Toast.LENGTH_SHORT).show();
					return;
				}

				if (StringUtils.CheckCharSpecical(edttenhodan.getText()
						.toString())) {

					Toast.makeText(
							getActivity(),
							getActivity().getString(
									R.string.checkcontentspecial),
							Toast.LENGTH_SHORT).show();
					return;
				}

				if (CommonActivity.isNullOrEmpty(edtphone_number.getText()
						.toString())) {
					Toast.makeText(getActivity(),
							getActivity().getString(R.string.sdtempty),
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (CommonActivity.isNullOrEmpty(edtbirth_day.getText()
						.toString())) {
					Toast.makeText(getActivity(),
							getActivity().getString(R.string.ngaysinhempty),
							Toast.LENGTH_SHORT).show();
					return;
				}

				try {
					Date dateBirth = sdf.parse(edtbirth_day.getText()
							.toString());

					if (dateBirth.after(dateNow) || dateBirth.equals(dateNow)) {
						Toast.makeText(
								getActivity(),
								getActivity().getString(
										R.string.ngaysinhnhohonngayhientai),
								Toast.LENGTH_SHORT).show();
						return;
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

				if (!CommonActivity.isNullOrEmpty(edttenhodan.getText()
						.toString())) {
					if (StringUtils.CheckCharSpecical(edttenhodan.getText()
							.toString())) {

						Toast.makeText(
								getActivity(),
								getActivity().getString(
										R.string.checkcontentspecial),
								Toast.LENGTH_SHORT).show();
						return;
					}
				}

				if (item != null) {
					item.setAddress(edtdiachi.getText().toString().trim());
					item.setFamilyInforCode(edtmahodan.getText().toString()
							.trim());
					item.setFamilyName(edttenhodan.getText().toString().trim());
					item.setPhone(edtphone_number.getText().toString().trim());
					item.setAreaCode(areaInforStaff.getAreaCode());
					item.setBirthDay(edtbirth_day.getText().toString());
					item.setX(edittoadox.getText().toString().trim());
					item.setY(edittoadoy.getText().toString().trim());
					item.setJob(edtnghenghiep.getText().toString().trim());
					item.setPositionCode(chucvu);
					AsyncSaveFamilyInfor asyncSaveFamilyInfor = new AsyncSaveFamilyInfor(
							getActivity(), item);
					asyncSaveFamilyInfor.execute();
				} else {
					FamilyInforBean inforBean = new FamilyInforBean();
					inforBean.setAddress(edtdiachi.getText().toString().trim());
					inforBean.setBirthDay(edtbirth_day.getText().toString());
					inforBean.setFamilyInforCode(edtmahodan.getText()
							.toString().trim());
					inforBean.setFamilyName(edttenhodan.getText().toString()
							.trim());
					inforBean.setPhone(edtphone_number.getText().toString()
							.trim());
					inforBean.setAreaCode(areaInforStaff.getAreaCode());

					inforBean.setX(edittoadox.getText().toString().trim());
					inforBean.setY(edittoadoy.getText().toString().trim());
					inforBean.setJob(edtnghenghiep.getText().toString().trim());
					inforBean.setPositionCode(chucvu);
					AsyncSaveFamilyInfor asyncSaveFamilyInfor = new AsyncSaveFamilyInfor(
							getActivity(), inforBean);
					asyncSaveFamilyInfor.execute();
				}

				dialog.cancel();

			}
		});
		Button btncacel = (Button) dialog.findViewById(R.id.btncacel);
		btncacel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.cancel();
			}
		});
		dialog.show();
	}

	// Ham lay thong tin danh sach ho gia dinh
	private class GetLstFamilyAsyn extends AsyncTask<String, Void, ParseOuput> {

		final ProgressDialog progress;
		private Context context = null;
		String errorCode = "";
		String description = "";

		public GetLstFamilyAsyn(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ParseOuput doInBackground(String... arg0) {
			return getLstFamily(arg0[0], arg0[1]);
		}

		@Override
		protected void onPostExecute(final ParseOuput result) {

			progress.dismiss();

			if ("0".equals(errorCode)) {
				if (result != null) {
					if (result.getLstFamilyInfor() != null
							&& result.getLstFamilyInfor().size() > 0) {
						lstFamilyInforBeans = result.getLstFamilyInfor();
					} else {
						lstFamilyInforBeans = new ArrayList<>();
					}
					adapter = new GetFamilyInfoAdapter(lstFamilyInforBeans,
							getActivity(), FragmentUpdateFamily.this,
							FragmentUpdateFamily.this);
					lvFamily.setAdapter(adapter);
				} else {
					if (description != null && !description.isEmpty()) {
						Dialog dialog = CommonActivity.createAlertDialog(
								getActivity(), description, getResources()
										.getString(R.string.app_name));
						dialog.show();
					} else {
						Dialog dialog = CommonActivity.createAlertDialog(
								getActivity(),
								getResources().getString(R.string.no_data),
								getResources().getString(R.string.app_name));
						dialog.show();
					}

				}
			} else {
				if (Constant.INVALID_TOKEN2.equals(errorCode)
						&& description != null && !description.isEmpty()) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
									(Activity) context,
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

		private ParseOuput getLstFamily(String areaCode, String version) {
			String original = null;
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getLstFamily");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getLstFamily>");
				rawData.append("<input>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));

				rawData.append("<areaCode>").append(areaCode);
				rawData.append("</areaCode>");

				rawData.append("<version>").append(version);
				rawData.append("</version>");

				rawData.append("</input>");
				rawData.append("</ws:getLstFamily>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getLstFamily");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee", original);

				Serializer serializer = new Persister();
				ParseOuput parseOuput = serializer.read(ParseOuput.class,
						original);
				if (parseOuput != null) {
					errorCode = parseOuput.getErrorCode();
					description = parseOuput.getDescription();
				}

				return parseOuput;
			} catch (Exception e) {
				Log.i("getLstFamily", e.toString());
			}
			return null;
		}

	}

	private final OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
			getActivity().finish();
			MainActivity.getInstance().finish();

		}
	};

	private final View.OnClickListener editTextListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			EditText edt = (EditText) view;
			Calendar cal = Calendar.getInstance();
			if (!CommonActivity.isNullOrEmpty(edt.getText().toString())) {
				Date date = DateTimeUtils.convertStringToTime(edt.getText()
						.toString(), "dd/MM/yyyy");
				cal.setTime(date);
			}

			DatePickerDialog pic = new FixedHoloDatePickerDialog(getActivity(),
					AlertDialog.THEME_HOLO_LIGHT,datePickerListener, cal.get(Calendar.YEAR),
					cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
			pic.getDatePicker().setTag(view);
			pic.show();
		}
	};

	private final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			Object obj = view.getTag();
			if (obj != null && obj instanceof EditText) {
				EditText editText = (EditText) obj;
				editText.setText(selectedDay + "/" + (selectedMonth + 1) + "/"
						+ selectedYear);
			}
		}
	};

	private final OnClickListener onclick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			GetLstFamilyAsyn getLstFamilyAsyn = new GetLstFamilyAsyn(
					getActivity());
			getLstFamilyAsyn.execute(areaInforStaff.getAreaCode(), "");
		}
	};

	private class AsyncSaveFamilyInfor extends AsyncTask<String, String, Void> {
		private final ProgressDialog progress;
		private String errorCode;
		private String description;
		private final FamilyInforBean familyInfo;

		public AsyncSaveFamilyInfor(Context context,
				FamilyInforBean mFamilyInforBean) {
			progress = new ProgressDialog(context);
			progress.setMessage(getResources().getString(R.string.waitting));
			if (!progress.isShowing()) {
				progress.show();
			}
			this.familyInfo = mFamilyInforBean;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... params) {
			return saveAreaInfor();
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			progress.dismiss();
			if ("0".equals(errorCode)) {
				if (dialog != null) {
					dialog.cancel();
				}
				if (description == null || description.isEmpty()) {
					description = getString(R.string.updatesucess);
				}
				CommonActivity.createAlertDialog(getActivity(), description,
						getString(R.string.app_name), onclick).show();

			} else {
				if (Constant.INVALID_TOKEN2.equals(errorCode)) {
					Dialog dialog = CommonActivity
							.createAlertDialog(
									getActivity(),
									description,
									getActivity().getResources().getString(
											R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = getActivity()
								.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		private Void saveAreaInfor() {
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_saveFamilyInfor");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:saveFamilyInfor>");
				rawData.append("<input>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.token);
				rawData.append(input.buildXML(paramToken));

				if (familyInfo != null) {
					rawData.append(familyInfo.toXML());
				}

				rawData.append("</input>");
				rawData.append("</ws:saveFamilyInfor>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_saveFamilyInfor");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				String original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", response);

				// parser
				XmlDomParse parse = new XmlDomParse();
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode + " description "
							+ description);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.relaBackHome:
			getActivity().onBackPressed();
			break;
		case R.id.btnthemmoi:
			showDialogInsertFamily(null, 0);
			break;
		default:
			break;
		}
	}

	@Override
	public void onEditFamily(FamilyInforBean familyInforBean) {
		showDialogInsertFamily(familyInforBean, 0);
	}

	@Override
	public void onDeleteFamily(final FamilyInforBean inforBean) {
		// confirmcancelfm
		OnClickListener confirmCancel = new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				inforBean.setStatus("0");
				AsyncSaveFamilyInfor asyncSaveFamilyInfor = new AsyncSaveFamilyInfor(
						getActivity(), inforBean);
				asyncSaveFamilyInfor.execute();
			}
		};
		CommonActivity
				.createDialog(
						getActivity(),
						getActivity().getString(R.string.confirmcancelfm) + " "
								+ inforBean.getFamilyName(),
						getActivity().getString(R.string.app_name),
						getActivity().getString(R.string.OK),
						getActivity().getString(R.string.boquaReq),
						confirmCancel, null).show();
	}

}
