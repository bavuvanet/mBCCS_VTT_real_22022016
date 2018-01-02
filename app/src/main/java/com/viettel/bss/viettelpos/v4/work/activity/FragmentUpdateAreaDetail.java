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
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;
import com.viettel.bss.viettelpos.v4.work.adapter.CatagoryInforBeans;
import com.viettel.bss.viettelpos.v4.work.adapter.GetCatagoryBeanAdapter;
import com.viettel.bss.viettelpos.v4.work.adapter.GetCatagoryBeanAdapter.ViewHolder;
import com.viettel.bss.viettelpos.v4.work.object.AreaInforStaff;
import com.viettel.bss.viettelpos.v4.work.object.DataComboboxBean;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class FragmentUpdateAreaDetail extends Fragment implements OnClickListener, OnItemClickListener {

	private View mView = null;

	private AreaInforStaff areaInforStaff;

	private ListView lvCategory;

    private GetCatagoryBeanAdapter adapter = null;

	private List<CatagoryInforBeans> lstCatagoryInforBeans;

	private CatagoryInforBeans catagoryInforBeans;

	private String title = "";

	private static List<CatagoryInforBeans> lstCatagoryInforBeansBackup;

	private List<CatagoryInforBeans> lstCatagoryInforBeansRequire;

	private static String areaCode = "";

	public static final Map<String, CatagoryInforBeans> hashMap = new HashMap<>();

	private Activity activity;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (mView == null) {
			mView = inflater.inflate(R.layout.layout_update_area_detail, container, false);
		}

		Bundle mBundle = getArguments();
		if (mBundle != null) {
			areaInforStaff = (AreaInforStaff) mBundle.getSerializable("areaInforStaff");
			catagoryInforBeans = (CatagoryInforBeans) mBundle.getSerializable("catagoryInforBeans");

			if (areaInforStaff != null) {
				areaCode = areaInforStaff.getAreaCode();
			}
		}

		unitView(mView);
		return mView;
	}

	@Override
	public void onResume() {
		super.onResume();
        if (CommonActivity.isNullOrEmpty(title)) {
            MainActivity.getInstance().setTitleActionBar(getResources().getString(R.string.task_update_area));
        } else {
            MainActivity.getInstance().setTitleActionBar(title);
        }
	}

	// khoi tao view
	private void unitView(View view) {

		lvCategory = (ListView) mView.findViewById(R.id.lvFamily);

        Button btnUpdate = (Button) mView.findViewById(R.id.btnUpdate);
		btnUpdate.setOnClickListener(this);

        Button btnSave = (Button) mView.findViewById(R.id.btnSave);
		btnSave.setOnClickListener(this);

		if (areaInforStaff != null && (lstCatagoryInforBeans == null || lstCatagoryInforBeans.isEmpty())) {
			btnSave.setVisibility(View.GONE);

			lstCatagoryInforBeansBackup = new ArrayList<>();

			GetCatagoryInforAsyn async = new GetCatagoryInforAsyn(getActivity());
			async.execute(areaInforStaff.getAreaCode(), "");
		} else if (catagoryInforBeans != null && (lstCatagoryInforBeans == null || lstCatagoryInforBeans.isEmpty())) {
			btnSave.setVisibility(View.VISIBLE);

			lstCatagoryInforBeans = new ArrayList<>();

			if (catagoryInforBeans.getLstCatagoryInforBeans() != null && !catagoryInforBeans.getLstCatagoryInforBeans().isEmpty()) {
				lstCatagoryInforBeans = catagoryInforBeans.getLstCatagoryInforBeans();
			} else {
				lstCatagoryInforBeans.add(catagoryInforBeans);
			}

			adapter = new GetCatagoryBeanAdapter(lstCatagoryInforBeans, activity);
			lvCategory.setAdapter(adapter);
			title = catagoryInforBeans.getInforName();

			lvCategory.setOnItemClickListener(this);
		}
	}

	private boolean validate() {
		boolean result = true;

		Queue<CatagoryInforBeans> qe = new LinkedList<>();

		for (CatagoryInforBeans catagoryInforBeans : lstCatagoryInforBeansBackup) {
			qe.add(catagoryInforBeans);
		}

		if (lstCatagoryInforBeansRequire == null || lstCatagoryInforBeansRequire.isEmpty()) {
			lstCatagoryInforBeansRequire = new ArrayList<>();

			while (!qe.isEmpty()) {
				CatagoryInforBeans catagoryInforBeans = qe.poll();
				if (catagoryInforBeans != null && "1".equalsIgnoreCase(catagoryInforBeans.getIsRequire())
						&& (catagoryInforBeans.getLstCatagoryInforBeans() == null || catagoryInforBeans.getLstCatagoryInforBeans().isEmpty())
						&& (catagoryInforBeans.getValue() == null || "".equalsIgnoreCase(catagoryInforBeans.getValue()))) {
					lstCatagoryInforBeansRequire.add(catagoryInforBeans);
				}

				if (catagoryInforBeans.getLstCatagoryInforBeans() != null && !catagoryInforBeans.getLstCatagoryInforBeans().isEmpty()) {
					for (CatagoryInforBeans obj : catagoryInforBeans.getLstCatagoryInforBeans()) {
						qe.add(obj);
					}
				}
			}
		}

		Log.d(this.getClass().getSimpleName(), " validate title " + title + " hashMap.size() " + +hashMap.size() + " lstCatagoryInforBeansRequire "
				+ lstCatagoryInforBeansRequire.size());

		if (hashMap.size() < lstCatagoryInforBeansRequire.size()) {
			result = false;
		} else {
			for (CatagoryInforBeans catagoryInforBeansRequire : lstCatagoryInforBeansRequire) {
				if (!hashMap.containsKey(catagoryInforBeansRequire.getInforCode())) {
					result = false;
					break;
				}
			}
		}

		return result;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relaBackHome:
			getActivity().onBackPressed();
			break;

		case R.id.btnUpdate:
			if (validate()) {
				CommonActivity.createDialog(activity, getString(R.string.updateConfirm), getString(R.string.app_name), getString(R.string.say_ko),
						getString(R.string.say_co), null, confirmChargeAct).show();
			} else {
				CommonActivity.toastShort(activity, R.string.input_blank_all);
			}
			break;
		case R.id.btnSave:
			getActivity().onBackPressed();
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

	// confirm charge
	private final OnClickListener confirmChargeAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (CommonActivity.isNetworkConnected(activity)) {
				AsyncSaveAreaInfor async = new AsyncSaveAreaInfor(activity);
				async.execute();
			} else {
				CommonActivity.createAlertDialog(activity, getString(R.string.errorNetwork), getString(R.string.app_name)).show();
			}
		}
	};

	// private View.OnClickListener deleteBtnListener = new
	// View.OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// Object obj = v.getTag();
	// int position = Integer.parseInt(obj.toString());
	// }
	// };

	private void showDialogEdit(final TextView tvValue, final CatagoryInforBeans catagoryInforBeans) {
		Log.d(this.getClass().getSimpleName(), " showDialogEdit title " + title + " " + catagoryInforBeans);

		final Dialog dialog = new Dialog(activity);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_layout_object_area);

		TextView tvDialogTitle = (TextView) dialog.findViewById(R.id.tvDialogTitle);
		final EditText edtValue = (EditText) dialog.findViewById(R.id.edtValue);
		final Spinner spnValue = (Spinner) dialog.findViewById(R.id.spnValue);

		tvDialogTitle.setText(catagoryInforBeans.getInforName());

		Button btnAdd = (Button) dialog.findViewById(R.id.btnAdd);
		Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
		Button btnSave = (Button) dialog.findViewById(R.id.btnSave);

		edtValue.setText(catagoryInforBeans.getValue());

		String inputType = catagoryInforBeans.getDataType();

        switch (inputType) {
            case "STRING": {
                spnValue.setVisibility(View.GONE);
                edtValue.setVisibility(View.VISIBLE);
                edtValue.setInputType(InputType.TYPE_CLASS_TEXT);
                int maxLength = 100;
                InputFilter[] FilterArray = new InputFilter[1];
                FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                edtValue.setFilters(FilterArray);
                break;
            }
            case "DATE":
                spnValue.setVisibility(View.GONE);
                edtValue.setVisibility(View.VISIBLE);
                edtValue.setInputType(InputType.TYPE_NULL);
                edtValue.setFocusable(false);
                edtValue.setFocusableInTouchMode(false);
                edtValue.setOnClickListener(editTextListener);
                break;
            case "NUMBER": {
                spnValue.setVisibility(View.GONE);
                edtValue.setVisibility(View.VISIBLE);
                edtValue.setInputType(InputType.TYPE_CLASS_NUMBER);
                int maxLength = 15;
                InputFilter[] FilterArray = new InputFilter[1];
                FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                edtValue.setFilters(FilterArray);
                break;
            }
            case "COMBOBOX":
                spnValue.setVisibility(View.VISIBLE);
                edtValue.setVisibility(View.GONE);
                btnAdd.setVisibility(View.GONE);
                edtValue.setText("");
                if (catagoryInforBeans != null && catagoryInforBeans.getLstDataCombo() != null) {
                    Utils.setDataComboSpinner(activity, catagoryInforBeans.getLstDataCombo(), spnValue, catagoryInforBeans);
                }
                break;
        }

		final List<String> lstValue = catagoryInforBeans.getLstValue();
		final ListView listViewValue = (ListView) dialog.findViewById(R.id.listViewValue);
		final ArrayAdapter<String> adapterValue = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, lstValue);
		listViewValue.setAdapter(adapterValue);

		if (!"1".equalsIgnoreCase(catagoryInforBeans.getType())) {
			btnAdd.setVisibility(View.GONE);
			listViewValue.setVisibility(View.GONE);
		} else {
			btnAdd.setVisibility(View.VISIBLE);
			listViewValue.setVisibility(View.VISIBLE);
			btnAdd.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					String value = edtValue.getText().toString().trim();
					edtValue.setText("");

					if ("".equalsIgnoreCase(value)) {
						Toast.makeText(getActivity(), getResources().getString(R.string.input_blank), Toast.LENGTH_LONG).show();
					} else if (StringUtils.CheckCharSpecical_1(value)) {
						Toast.makeText(getActivity(), getResources().getString(R.string.checkcharspecical_), Toast.LENGTH_LONG).show();
					} else {
						tvValue.setText(value);
						catagoryInforBeans.setValue(value);
						catagoryInforBeans.setNameValue(value);

						lstValue.add(value);
						adapterValue.notifyDataSetChanged();
					}
				}
			});
		}

		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		btnSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String value = edtValue.getText().toString().trim();
				DataComboboxBean item = (DataComboboxBean) spnValue.getSelectedItem();

				String inputType = catagoryInforBeans.getDataType();
				if ("COMBOBOX".equals(inputType)) {
					if (item != null) {
						Log.d(this.getClass().getSimpleName(),
								" showDialogEdit title " + title + " btnSave " + inputType + " code " + item.getCode() + " name " + item.getName());
						tvValue.setText(item.getName());
						catagoryInforBeans.setValue(item.getCode());
						catagoryInforBeans.setNameValue(item.getName());
					}
				} else {
					if (!"1".equalsIgnoreCase(catagoryInforBeans.getType())) {
						Log.d(this.getClass().getSimpleName(),
								" showDialogEdit title " + title + " btnSave " + inputType + " " + edtValue.getText().toString());
						if ("".equalsIgnoreCase(value)) {
							Toast.makeText(getActivity(), getResources().getString(R.string.input_blank), Toast.LENGTH_LONG).show();
						} else if (StringUtils.CheckCharSpecical_1(value)) {
							Toast.makeText(getActivity(), getResources().getString(R.string.checkcharspecical_), Toast.LENGTH_LONG).show();
						} else {
							tvValue.setText(value);
							catagoryInforBeans.setValue(value);
							catagoryInforBeans.setNameValue(value);
						}
					} else {
						Log.d(this.getClass().getSimpleName(), " showDialogEdit title " + title + " btnSave " + inputType + " MUTIPLE "
								+ edtValue.getText().toString() + " blankValue.size() " + lstValue.size());
						if ("".equalsIgnoreCase(value)) {
							Toast.makeText(getActivity(), getResources().getString(R.string.input_blank), Toast.LENGTH_LONG).show();
						} else if (StringUtils.CheckCharSpecical_1(value)) {
							Toast.makeText(getActivity(), getResources().getString(R.string.checkcharspecical_), Toast.LENGTH_LONG).show();
						} else {
							tvValue.setText(value);
							catagoryInforBeans.setValue(value);
							catagoryInforBeans.setNameValue(value);

							catagoryInforBeans.setLstValue(lstValue);
						}
					}
				}

				hashMap.put(catagoryInforBeans.getInforCode(), catagoryInforBeans);
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Log.d(this.getClass().getSimpleName(), " onItemClick  title " + title + " position " + position);

		CatagoryInforBeans catagoryInforBeans = (CatagoryInforBeans) parent.getAdapter().getItem(position);

		if (catagoryInforBeans != null && !catagoryInforBeans.isDeleted()) {
			if (catagoryInforBeans.getLstCatagoryInforBeans() != null && !catagoryInforBeans.getLstCatagoryInforBeans().isEmpty()) {
				Bundle bundle = new Bundle();
				bundle.putSerializable("catagoryInforBeans", catagoryInforBeans);

				FragmentUpdateAreaDetail fragment = new FragmentUpdateAreaDetail();
				fragment.setArguments(bundle);
				fragment.setTargetFragment(FragmentUpdateAreaDetail.this, 100);
				ReplaceFragment.replaceFragment(activity, fragment, true);
			} else {
				Log.d(this.getClass().getSimpleName(), " onItemClick  title " + title + " position " + position
						+ " catagoryInforBeans.getLstCatagoryInforBeans() NULL lvCategory.getChildCount() " + lvCategory.getChildCount());

				View rowView = lvCategory.getChildAt(position - lvCategory.getFirstVisiblePosition());
				if (rowView != null) {
					ViewHolder holder = (ViewHolder) rowView.getTag();
					if (holder != null) {
						showDialogEdit(holder.tvValue, catagoryInforBeans);
					} else {
						Log.d(this.getClass().getSimpleName(), " onItemClick  title " + title + " position " + position + " holder NULL");
					}
				} else {
					Log.d(this.getClass().getSimpleName(), " onItemClick  title " + title + " position " + position + " rowView NULL");

				}
			}
		} else {
			Log.d(this.getClass().getSimpleName(), " onItemClick  title " + title + " position " + position + " catagoryInforBeans NULL");
		}
	}

	// Ham lay thong tin cap nhat ho dan
	private class GetCatagoryInforAsyn extends AsyncTask<String, Void, ParseOuput> {

		final ProgressDialog progress;
		String errorCode = "";
		String description = "";

		public GetCatagoryInforAsyn(Context context) {
			this.progress = new ProgressDialog(context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ParseOuput doInBackground(String... arg0) {
			return getCategory(arg0[0], arg0[1]);
		}

		@Override
		protected void onPostExecute(final ParseOuput result) {
			if (this.progress != null && this.progress.isShowing()) {
				this.progress.dismiss();
			}

			if (errorCode.equals("0")) {
				if (result != null && result.getLstCatagoryInforBeans().size() > 0) {

					lstCatagoryInforBeans = result.getLstCatagoryInforBeans();

					lstCatagoryInforBeansBackup = result.getLstCatagoryInforBeans();

					adapter = new GetCatagoryBeanAdapter(result.getLstCatagoryInforBeans(), activity);
					lvCategory.setAdapter(adapter);

					lvCategory.setOnItemClickListener(FragmentUpdateAreaDetail.this);
				} else {
					if (description != null && !description.isEmpty()) {
						Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description, getResources().getString(R.string.app_name));
						dialog.show();
					} else {
						Dialog dialog = CommonActivity.createAlertDialog(getActivity(), getResources().getString(R.string.no_data),
								getResources().getString(R.string.app_name));
						dialog.show();
					}
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2) && description != null && !description.isEmpty()) {

					Dialog dialog = CommonActivity.createAlertDialog(activity, description, activity.getResources().getString(R.string.app_name),
							moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = activity.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description, getResources().getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		private ParseOuput getCategory(String areaCode, String version) {
			String original = null;
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getCatagoryInfor");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getCatagoryInfor>");
				rawData.append("<input>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));

				rawData.append("<areaCode>").append(areaCode);
				rawData.append("</areaCode>");

				rawData.append("<version>").append(version);
				rawData.append("</version>");

				rawData.append("<type>" + 1);
				rawData.append("</type>");

				rawData.append("</input>");
				rawData.append("</ws:getCatagoryInfor>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(), "mbccs_getCatagoryInfor");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee", original);

				Serializer serializer = new Persister();
				ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
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

	private class AsyncSaveAreaInfor extends AsyncTask<String, String, Void> {
		private final ProgressDialog progress;
		private String errorCode;
		private String description;

		public AsyncSaveAreaInfor(Context context) {
			progress = new ProgressDialog(context);
			progress.setMessage(getResources().getString(R.string.waitting));
			if (!progress.isShowing()) {
				progress.show();
			}
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
			if (errorCode != null && errorCode.equals("0")) {
				if (description == null || description.isEmpty()) {
					description = getString(R.string.updatesucess);
				}
				CommonActivity.createAlertDialog(activity, description, getString(R.string.app_name)).show();
			} else {
				if (errorCode == null || errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity.createAlertDialog(activity, description, activity.getResources().getString(R.string.app_name),
							moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = activity.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(activity, description, getResources().getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		private Void saveAreaInfor() {
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_saveAreaInfor");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:saveAreaInfor>");
				rawData.append("<input>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.token);
				rawData.append(input.buildXML(paramToken));

				List<CatagoryInforBeans> lstCatagoryInfor = new ArrayList<>(hashMap.values());

				Log.d(this.getClass().getSimpleName(), " saveAreaInfor title " + title + " lstCatagoryInfor " + lstCatagoryInfor.size());

				rawData.append("<areaInforForm>");

				rawData.append("<areaCode>");
				rawData.append(areaCode);
				rawData.append("</areaCode>");

				for (CatagoryInforBeans catagoryInforBeans : lstCatagoryInfor) {
					rawData.append(catagoryInforBeans.toXML());
				}

				rawData.append("</areaInforForm>");

				rawData.append("</input>");
				rawData.append("</ws:saveAreaInfor>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, activity, "mbccs_saveAreaInfor");
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
					Log.d("errorCode", errorCode + " description " + description);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}
	}

	private final View.OnClickListener editTextListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			EditText edt = (EditText) view;
			Calendar cal = Calendar.getInstance();
			if (!CommonActivity.isNullOrEmpty(edt.getText().toString())) {
				Date date = DateTimeUtils.convertStringToTime(edt.getText().toString(), "dd/MM/yyyy");
				cal.setTime(date);
			}

			DatePickerDialog pic = new FixedHoloDatePickerDialog(activity, AlertDialog.THEME_HOLO_LIGHT, datePickerListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
					cal.get(Calendar.DAY_OF_MONTH));
			pic.getDatePicker().setTag(view);
			pic.show();
		}
	};

	private final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
			Object obj = view.getTag();
			if (obj != null && obj instanceof EditText) {
				EditText editText = (EditText) obj;
				editText.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
			}
		}
	};

}
