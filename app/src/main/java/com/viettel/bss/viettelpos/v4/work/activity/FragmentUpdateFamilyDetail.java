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
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import com.viettel.bss.viettelpos.v4.work.adapter.GetCatagoryBeanFamilyAdapter;
import com.viettel.bss.viettelpos.v4.work.adapter.GetCatagoryBeanFamilyAdapter.OnAddCategotyBeans;
import com.viettel.bss.viettelpos.v4.work.adapter.GetCatagoryBeanFamilyAdapter.OnDeleteCatagoryBeans;
import com.viettel.bss.viettelpos.v4.work.object.DataComboboxBean;
import com.viettel.bss.viettelpos.v4.work.object.FamilyInforBean;
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

public class FragmentUpdateFamilyDetail extends Fragment implements
		OnClickListener, OnAddCategotyBeans, OnDeleteCatagoryBeans {

	private View mView = null;

	private ListView lvCategory;

    private GetCatagoryBeanFamilyAdapter adapter = null;

	public static FamilyInforBean familyInforBean = null;

	// list root
	private List<CatagoryInforBeans> lstCatagoryInforBeans = new ArrayList<>();
	private CatagoryInforBeans catagoryInforBeans;

	private String title = "";
	public static Map<String, CatagoryInforBeans> hashMap = new HashMap<>();

    private List<CatagoryInforBeans> lstCatagoryInforBeansRequire;

	private static Long familyDetailId = 1L;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Bundle mBundle = getArguments();
		if (mBundle != null) {
			if (familyInforBean == null) {
				familyInforBean = (FamilyInforBean) mBundle
						.getSerializable("familyInforBean");
			}

			catagoryInforBeans = (CatagoryInforBeans) mBundle
					.getSerializable("catagoryInforBeans");

			
		}
		if (mView == null) {
			mView = inflater.inflate(R.layout.layout_update_family, container,
					false);
			unitView(mView);
		}

		return mView;
	}

	@Override
	public void onResume() {
		super.onResume();
        if (CommonActivity.isNullOrEmpty(title)) {
            MainActivity.getInstance().setTitleActionBar(getResources()
                    .getString(R.string.updateHD));
        } else {
            MainActivity.getInstance().setTitleActionBar(title);
        }
	}

	// khoi tao view
	private void unitView(View mView) {
		lvCategory = (ListView) mView.findViewById(R.id.lvFamily);
		lvCategory.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1,
					int position, long arg3) {
				CatagoryInforBeans item = (CatagoryInforBeans) parent
						.getAdapter().getItem(position);
				
				if (item != null) {
					if (item.getLstCatagoryInforBeans() != null
							&& item.getLstCatagoryInforBeans().size() > 0) {
						if (item.getFamilyInforDetailId() == null) {
							if (item.getFamilyInforDetailId() == null) {
								item
										.setFamilyInforDetailId(familyDetailId++);
								for (CatagoryInforBeans itm : item
										.getLstCatagoryInforBeans()) {
									if (itm.getParentId() == null) {
										itm.setParentId(item
												.getFamilyInforDetailId());
									}
								}
							}
						}
					}
					if(item.getLstCatagoryInforBeans() != null ){
						for (CatagoryInforBeans itm : item
								.getLstCatagoryInforBeans()) {
							if (CommonActivity.isNullOrEmpty(itm.getIsNew())) {
								if (CommonActivity.isNullOrEmpty(itm.getValue())
										&& (itm.getLstCatagoryInforBeans() == null
												|| itm.getLstCatagoryInforBeans()
														.size() == 0 || CommonActivity
													.isNullOrEmpty(itm
															.getLstCatagoryInforBeans()
															.get(0).getValue()))) {
									itm.setIsNew("1");

								} else {
									itm.setIsNew("0");
								}
							}
						}
					}
					

				}
				Log.d("HASMMAP ONCREATEEEEEEEEEEEEEEE", hashMap.size() + "");
				
				if (item != null
						&& !CommonActivity.isNullOrEmpty(item.getType())) {

					String key = item.getInforCode();
					if (item.getFamilyInforDetailId() != null) {
						key += "_" + item.getFamilyInforDetailId();
					}

					if (item.getParentId() != null) {
						key += "_" + item.getParentId();
					}

					Log.d("key = ", key);
					hashMap.put(key, item);

					// truong hop don tri va la nhanh
					if ("1".equals(item.getType())) {
						if (item.getLstCatagoryInforBeans() != null
								&& !item.getLstCatagoryInforBeans().isEmpty()) {
							Bundle bundle = new Bundle();
							bundle.putSerializable("catagoryInforBeans", item);

							FragmentUpdateFamilyDetail fragment = new FragmentUpdateFamilyDetail();
							fragment.setArguments(bundle);
							fragment.setTargetFragment(
									FragmentUpdateFamilyDetail.this, 100);
							ReplaceFragment.replaceFragment(getActivity(),
									fragment, true);
						} else {
							if (item.getDataType().equals("DATE")
									|| (item.getDataType().equals("STRING")
											|| item.getDataType().equals(
													"NUMBER") || item
											.getDataType().equals("COMBOBOX"))) {
								DialogUpdate dialogUpdate = new DialogUpdate(
										getActivity(), position, item
												.getDataType(), item);
								dialogUpdate.show();
							}
						}
					} else {
						// truong hop da tri
						if (item.getLstCatagoryInforBeans() != null
								&& !item.getLstCatagoryInforBeans().isEmpty()) {
							Bundle bundle = new Bundle();
							bundle.putSerializable("catagoryInforBeans", item);

							FragmentUpdateFamilyDetail fragment = new FragmentUpdateFamilyDetail();
							fragment.setArguments(bundle);
							fragment.setTargetFragment(
									FragmentUpdateFamilyDetail.this, 100);
							ReplaceFragment.replaceFragment(getActivity(),
									fragment, true);
						} else {
							if (item.getDataType().equals("DATE")
									|| (item.getDataType().equals("STRING")
											|| item.getDataType().equals(
													"NUMBER") || item
											.getDataType().equals("COMBOBOX"))) {
								DialogUpdate dialogUpdate = new DialogUpdate(
										getActivity(), position, item
												.getDataType(), item);
								dialogUpdate.show();
							}
						}
					}
				}
			}
		});
        Button btncapnhat = (Button) mView.findViewById(R.id.btncapnhat);
		btncapnhat.setOnClickListener(this);
        Button btnthemmoi = (Button) mView.findViewById(R.id.btnthemmoi);
		btnthemmoi.setOnClickListener(this);
		btnthemmoi.setVisibility(View.GONE);

		if (catagoryInforBeans == null) {
			GetCatagoryInforAsyn getCatagoryInforAsyn = new GetCatagoryInforAsyn(
					getActivity());
			getCatagoryInforAsyn.execute(familyInforBean.getFamilyInforCode(),
					"");
		} else if (catagoryInforBeans != null) {

			lstCatagoryInforBeans = new ArrayList<>();

			if (catagoryInforBeans.getLstCatagoryInforBeans() != null
					&& !catagoryInforBeans.getLstCatagoryInforBeans().isEmpty()) {
				lstCatagoryInforBeans = catagoryInforBeans
						.getLstCatagoryInforBeans();
			} else {
				lstCatagoryInforBeans.add(catagoryInforBeans);
			}

			adapter = new GetCatagoryBeanFamilyAdapter(lstCatagoryInforBeans,
					getActivity(), FragmentUpdateFamilyDetail.this,
					FragmentUpdateFamilyDetail.this);
			lvCategory.setAdapter(adapter);
			title = catagoryInforBeans.getInforName();
		}

	}

	// Ham lay thong tin cap nhat ho dan
	private class GetCatagoryInforAsyn extends
			AsyncTask<String, Void, ParseOuput> {

		final ProgressDialog progress;
		private Context context = null;
		String errorCode = "";
		String description = "";

		public GetCatagoryInforAsyn(Context context) {
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
			return getCategory(arg0[0], arg0[1]);
		}

		@Override
		protected void onPostExecute(final ParseOuput result) {
			progress.dismiss();
			
			
			
			if ("0".equals(errorCode)) {
				if (result != null
						&& result.getLstCatagoryInforBeans().size() > 0) {
					lstCatagoryInforBeans = result.getLstCatagoryInforBeans();


                    List<CatagoryInforBeans> lstCatagoryInforBeansBackup = result
                            .getLstCatagoryInforBeans();
					for (CatagoryInforBeans item : lstCatagoryInforBeans) {
						if (CommonActivity.isNullOrEmpty(item.getIsNew())) {
							if (CommonActivity.isNullOrEmpty(item.getValue())
									&& (item.getLstCatagoryInforBeans() == null
											|| item.getLstCatagoryInforBeans()
													.size() == 0 || CommonActivity
												.isNullOrEmpty(item
														.getLstCatagoryInforBeans()
														.get(0).getValue()))) {
								item.setIsNew("1");

							} else {
								item.setIsNew("0");
							}
						}
					}
					adapter = new GetCatagoryBeanFamilyAdapter(
							result.getLstCatagoryInforBeans(), getActivity(),
							FragmentUpdateFamilyDetail.this,
							FragmentUpdateFamilyDetail.this);
					lvCategory.setAdapter(adapter);
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

				rawData.append("<type>" + 2);
				rawData.append("</type>");

				rawData.append("</input>");
				rawData.append("</ws:getCatagoryInfor>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getCatagoryInfor");
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

					if (parseOuput.getLstCatagoryInforBeans() == null) {
						parseOuput
								.setLstCatagoryInforBeans(new ArrayList<CatagoryInforBeans>());
					}

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

	private class DialogUpdate extends Dialog implements
			android.view.View.OnClickListener {

		private EditText edtValue;
		private Button btnUpdateDilog;
		private final int position;
		private final String inputType;
		private TextView txtTitleOnlineDialog;
		private Spinner mSpinner;
		private CatagoryInforBeans catagoryInforBeansItem = null;

		public DialogUpdate(Context context, int position, String inputType,
				CatagoryInforBeans mCatagoryInforBeans) {
			super(context);
			this.position = position;
			this.inputType = inputType;
			this.catagoryInforBeansItem = mCatagoryInforBeans;
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.dialog_family_update_detail);

			edtValue = (EditText) findViewById(R.id.edtValue);
			txtTitleOnlineDialog = (TextView) findViewById(R.id.txtTitleOnlineDialog);
			btnUpdateDilog = (Button) findViewById(R.id.btnUpdateDialog);
			btnUpdateDilog.setOnClickListener(this);

			Button btncancel = (Button) findViewById(R.id.btncancel);
			btncancel.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					cancel();
				}
			});

			txtTitleOnlineDialog.setText(lstCatagoryInforBeans.get(position)
					.getInforName());

			mSpinner = (Spinner) findViewById(R.id.spShowCriterial);
			edtValue.setText("");
            switch (inputType) {
                case "STRING": {
                    getWindow().setSoftInputMode(
                            LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                    mSpinner.setVisibility(View.GONE);
                    edtValue.setVisibility(View.VISIBLE);
                    edtValue.setInputType(InputType.TYPE_CLASS_TEXT);
                    int maxLength = 100;
                    InputFilter[] FilterArray = new InputFilter[1];
                    FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                    edtValue.setFilters(FilterArray);
                    break;
                }
                case "DATE":
                    mSpinner.setVisibility(View.GONE);
                    edtValue.setVisibility(View.VISIBLE);
                    edtValue.setInputType(InputType.TYPE_NULL);
                    edtValue.setFocusable(false);
                    edtValue.setFocusableInTouchMode(false);
                    edtValue.setOnClickListener(editTextListener);
                    break;
                case "NUMBER": {
                    getWindow().setSoftInputMode(
                            LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                    mSpinner.setVisibility(View.GONE);
                    edtValue.setVisibility(View.VISIBLE);
                    edtValue.setInputType(InputType.TYPE_CLASS_NUMBER);
                    int maxLength = 15;
                    InputFilter[] FilterArray = new InputFilter[1];
                    FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                    edtValue.setFilters(FilterArray);
                    break;
                }
                case "COMBOBOX":
                    mSpinner.setVisibility(View.VISIBLE);
                    edtValue.setVisibility(View.GONE);
                    edtValue.setFocusable(false);
                    edtValue.setFocusableInTouchMode(false);
                    if (catagoryInforBeansItem != null
                            && catagoryInforBeansItem.getLstDataCombo() != null) {
                        Utils.setDataComboSpinner(getActivity(),
                                catagoryInforBeansItem.getLstDataCombo(), mSpinner,
                                catagoryInforBeansItem);
                    }
                    break;
            }
			if (catagoryInforBeansItem != null) {
				if (!CommonActivity.isNullOrEmpty(catagoryInforBeansItem
						.getValue())) {
					edtValue.setText(catagoryInforBeansItem.getValue());
				} else {
					edtValue.setText("");
				}

			} else {
				edtValue.setText("");
				edtValue.setHint(getResources().getString(R.string.enterDesc));
			}

		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnUpdateDialog:
				String content = edtValue.getText().toString().trim();
				DataComboboxBean item = (DataComboboxBean) mSpinner
						.getSelectedItem();

				if ("COMBOBOX".equals(inputType)) {
					if (item != null) {
						catagoryInforBeansItem.setNameValue(item.getName());
						catagoryInforBeansItem.setValue(item.getCode());
					
						if (catagoryInforBeansItem.getFamilyInforDetailId() == null
								&& catagoryInforBeansItem.getParentId() == null) {
							Log.d("key = ", catagoryInforBeansItem.getInforCode());
							hashMap.put(catagoryInforBeansItem.getInforCode(),
									catagoryInforBeansItem);
						} else {
							String key = catagoryInforBeansItem.getInforCode();
							if (catagoryInforBeansItem.getFamilyInforDetailId() != null) {
								key += "_"
										+ catagoryInforBeansItem
												.getFamilyInforDetailId();
							}

							if (catagoryInforBeansItem.getParentId() != null) {
								key += "_"
										+ catagoryInforBeansItem.getParentId();
							}

							Log.d("key = ", key);
							hashMap.put(key, catagoryInforBeansItem);
						}

						adapter.notifyDataSetChanged();
						dismiss();
					}
				} else {
					if (CommonActivity.isNullOrEmpty(content)) {
						Toast.makeText(
								getActivity(),
								getResources().getString(
										R.string.contentisempty),
								Toast.LENGTH_LONG).show();
					} else if (StringUtils.CheckCharSpecical_1(content)) {
						Toast.makeText(
								getActivity(),
								getResources().getString(
										R.string.checkcharspecical_),
								Toast.LENGTH_LONG).show();
					} else {
						catagoryInforBeansItem.setValue(content);

						if (catagoryInforBeansItem.getFamilyInforDetailId() == null
								&& catagoryInforBeansItem.getParentId() == null) {
							Log.d("key = ", catagoryInforBeansItem.getInforCode());
							hashMap.put(catagoryInforBeansItem.getInforCode(),
									catagoryInforBeansItem);
						} else {
							String key = catagoryInforBeansItem.getInforCode();
							if (catagoryInforBeansItem.getFamilyInforDetailId() != null) {
								key += "_"
										+ catagoryInforBeansItem
												.getFamilyInforDetailId();
							}

							if (catagoryInforBeansItem.getParentId() != null) {
								key += "_"
										+ catagoryInforBeansItem.getParentId();
							}

							Log.d("key = ", key);
							hashMap.put(key, catagoryInforBeansItem);
						}

						adapter.notifyDataSetChanged();
						dismiss();

					}
				}
				break;

			default:
				break;
			}

		}

	}

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

	private class AsyncDeleteCategoryInfor extends
			AsyncTask<String, String, Void> {
		private final ProgressDialog progress;
		private String errorCode;
		private String description;
		private final CatagoryInforBeans caInforBeans;

		public AsyncDeleteCategoryInfor(Context context,
				CatagoryInforBeans catagoryInforBeans) {
			progress = new ProgressDialog(context);
			progress.setMessage(getResources().getString(R.string.waitting));
			if (!progress.isShowing()) {
				progress.show();
			}
			this.caInforBeans = catagoryInforBeans;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... params) {
			return deleInfor();
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			progress.dismiss();
			if ("0".equals(errorCode)) {

				if (caInforBeans != null) {
					if (lstCatagoryInforBeans != null
							&& lstCatagoryInforBeans.size() > 0) {
						for (int i = 0; i < lstCatagoryInforBeans.size(); i++) {
							if (caInforBeans.getFamilyInforDetailId()
									== lstCatagoryInforBeans.get(i)
											.getFamilyInforDetailId()) {
								lstCatagoryInforBeans.remove(i);
								adapter.notifyDataSetChanged();
								break;
							}
						}
					}
				}

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

		private Void deleInfor() {
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

				rawData.append("<familyInforForm>");

				if (familyInforBean != null) {
					rawData.append("<areaCode>");
					rawData.append(familyInforBean.getAreaCode());
					rawData.append("</areaCode>");

					rawData.append("<familyInforCode>");
					rawData.append(familyInforBean.getFamilyInforCode());
					rawData.append("</familyInforCode>");

				}
				if (caInforBeans != null) {
					rawData.append(caInforBeans.toXML());
				}

				rawData.append("</familyInforForm>");

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

	private final OnClickListener onclickBack = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			FragmentUpdateFamily fragUpdateFamily = new FragmentUpdateFamily();
			ReplaceFragment.replaceFragment(getActivity(), fragUpdateFamily,
					false);
			if (familyInforBean != null) {
				familyInforBean = null;
			}

			if (hashMap != null) {
				hashMap = new HashMap<>();
			}

			// getActivity().finish();
		}
	};

	private class AsyncSaveFamilyInfor extends AsyncTask<String, String, Void> {
		private final ProgressDialog progress;
		private String errorCode;
		private String description;

		public AsyncSaveFamilyInfor(Context context) {
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
			if ("0".equals(errorCode)) {
				if (description == null || description.isEmpty()) {
					description = getString(R.string.updatesucess);
				}
				CommonActivity.createAlertDialog(getActivity(), description,
						getString(R.string.app_name), onclickBack).show();
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

				List<CatagoryInforBeans> lstCatagoryInfor = new ArrayList<>(
                        hashMap.values());

				Log.d(this.getClass().getSimpleName(),
						" saveFamilyInfor title " + title
								+ " lstCatagoryInfor "
								+ lstCatagoryInfor.size());

				rawData.append("<familyInforForm>");

				if (familyInforBean != null) {
					rawData.append("<areaCode>");
					rawData.append(familyInforBean.getAreaCode());
					rawData.append("</areaCode>");
					rawData.append("<familyInforCode>");
					rawData.append(familyInforBean.getFamilyInforCode());
					rawData.append("</familyInforCode>");

				}

				for (CatagoryInforBeans item : lstCatagoryInfor) {
					if("1".equals(item.getIsLeaves()) && !"1".equals(item.getIsRequire()) && CommonActivity.isNullOrEmpty(item.getValue())){
						
					}else{
						rawData.append(item.toXML());
					}
					
				}

				rawData.append("</familyInforForm>");

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

	private boolean validate2(List<CatagoryInforBeans> lstCatagoryInforBeansBk) {
		boolean result = true;

		if(lstCatagoryInforBeansBk == null){
			CommonActivity.toastShort(getActivity(),
					R.string.chonhodanitnhat);
			return false;
		}
		if(lstCatagoryInforBeansBk.size() == 0 || lstCatagoryInforBeansBk.size() == 1){
			CommonActivity.toastShort(getActivity(),
					R.string.chonhodanitnhat);
			return false;
		}
		
		for (CatagoryInforBeans item : lstCatagoryInforBeansBk) {
            result = !("1".equals(item.getIsRequire()) && CommonActivity.isNullOrEmpty(item.getValue()));
		}
		return result;
	}
	
	private boolean validate(List<CatagoryInforBeans> lstCatagoryInforBeansBk) {
		boolean result = true;

		if(lstCatagoryInforBeansBk == null){
			CommonActivity.toastShort(getActivity(),
					R.string.chonhodanitnhat);
			return false;
		}
		if(lstCatagoryInforBeansBk.size() == 0 || lstCatagoryInforBeansBk.size() == 1){
			CommonActivity.toastShort(getActivity(),
					R.string.chonhodanitnhat);
			return false;
		}
		
		
		
		
		Queue<CatagoryInforBeans> qe = new LinkedList<>();

		for (CatagoryInforBeans catagoryInforBeans : lstCatagoryInforBeansBk) {
			qe.add(catagoryInforBeans);
		}

		if (lstCatagoryInforBeansRequire == null
				|| lstCatagoryInforBeansRequire.isEmpty()) {
			lstCatagoryInforBeansRequire = new ArrayList<>();

			while (!qe.isEmpty()) {
				CatagoryInforBeans catagoryInforBeans = qe.poll();
				if (catagoryInforBeans != null
						&& "1".equalsIgnoreCase(catagoryInforBeans
								.getIsRequire())
						&& (catagoryInforBeans.getLstCatagoryInforBeans() == null || catagoryInforBeans
								.getLstCatagoryInforBeans().isEmpty())
						&& (catagoryInforBeans.getValue() == null || ""
								.equalsIgnoreCase(catagoryInforBeans.getValue()))) {
					lstCatagoryInforBeansRequire.add(catagoryInforBeans);
				}

				if (catagoryInforBeans.getLstCatagoryInforBeans() != null
						&& !catagoryInforBeans.getLstCatagoryInforBeans()
								.isEmpty()) {
					for (CatagoryInforBeans obj : catagoryInforBeans
							.getLstCatagoryInforBeans()) {
						qe.add(obj);
					}
				}
			}
		}

		Log.d(this.getClass().getSimpleName(), " validate title " + title
				+ " hashMap.size() " + +hashMap.size()
				+ " lstCatagoryInforBeansRequire "
				+ lstCatagoryInforBeansRequire.size());

		if (hashMap.size() < lstCatagoryInforBeansRequire.size()) {
			result = false;
		} else {
			for (CatagoryInforBeans catagoryInforBeansRequire : lstCatagoryInforBeansRequire) {
				if (!hashMap.containsKey(catagoryInforBeansRequire
						.getInforCode())) {
					result = false;
					break;
				}
			}
		}

		return result;
	}

	private final OnClickListener onclick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			AsyncSaveFamilyInfor async = new AsyncSaveFamilyInfor(getActivity());
			async.execute();
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relaBackHome:
			getActivity().onBackPressed();

			break;
		case R.id.btncapnhat:
			if (CommonActivity.isNetworkConnected(getActivity())) {

				
				if(catagoryInforBeans != null && catagoryInforBeans.getLstCatagoryInforBeans() != null ){
					if (validate2(catagoryInforBeans.getLstCatagoryInforBeans())) {
						CommonActivity.createDialog(getActivity(),
								getActivity().getString(R.string.confirmUpdate),
								getActivity().getString(R.string.app_name),
								getActivity().getString(R.string.ok),
								getActivity().getString(R.string.cancel), onclick,
								null).show();
					} else {
						CommonActivity.toastShort(getActivity(),
								R.string.input_blank_all);
					}
				}else{
					
					if(hashMap != null && hashMap.size() == 0){
						CommonActivity.toastShort(getActivity(),
								R.string.chonhodanitnhat);
					}else{
						CommonActivity.createDialog(getActivity(),
								getActivity().getString(R.string.confirmUpdate),
								getActivity().getString(R.string.app_name),
								getActivity().getString(R.string.ok),
								getActivity().getString(R.string.cancel), onclick,
								null).show();
					}
					
				
				}
			

			}
			break;
		case R.id.btnthemmoi:

			break;
		default:
			break;
		}
	}

	@Override
	public void onDeleteCatagoryBeans(final CatagoryInforBeans item) {

		Log.d("onDeleteeeeeeeeeeeeeeeeeeeeeeeeeeee   ", item.toString());

		OnClickListener confirmCancel = new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				item.setStatus("0");
				AsyncDeleteCategoryInfor asyncSaveFamilyInfor = new AsyncDeleteCategoryInfor(
						getActivity(), item);
				asyncSaveFamilyInfor.execute();
			}
		};

		if ("1".equals(item.getIsNew())) {

			Log.d("before remove hashMap", "size = " + hashMap.size());

			removeCatagoryInforBeans(item);

			Log.d("after remove hashMap", "size = " + hashMap.size());

			if (lstCatagoryInforBeans != null
					&& lstCatagoryInforBeans.size() > 0) {
				for (int i = 0; i < lstCatagoryInforBeans.size(); i++) {
					if ((item.getFamilyInforDetailId() == lstCatagoryInforBeans
							.get(i).getFamilyInforDetailId())
							&& (item.getInforCode()
									.equals(lstCatagoryInforBeans.get(i)
											.getInforCode()) && (item
									.getParentId() == lstCatagoryInforBeans
									.get(i).getParentId()))) {
						lstCatagoryInforBeans.remove(i);
						break;
					}
				}
			}
			adapter.notifyDataSetChanged();

		} else {
			CommonActivity.createDialog(
					getActivity(),
					getActivity().getString(R.string.confirmcancelitem) + " "
							+ item.getInforName(),
					getActivity().getString(R.string.app_name),
					getActivity().getString(R.string.OK),
					getActivity().getString(R.string.boquaReq), confirmCancel,
					null).show();
		}

	}

	@Override
	public void onAddCatagoryBeans(CatagoryInforBeans caBeans) {

		CatagoryInforBeans catagoryInforBeans = (CatagoryInforBeans) CommonActivity
				.cloneObject(caBeans);
		Log.d("adddddddddddddddddddd   ", catagoryInforBeans.toString());

		resetValueCatagoryInforBeans(catagoryInforBeans);
//		if (catagoryInforBeans.getLstCatagoryInforBeans() != null
//				&& catagoryInforBeans.getLstCatagoryInforBeans().size() > 0) {

			String key = catagoryInforBeans.getInforCode();
			if (catagoryInforBeans.getFamilyInforDetailId() != null) {
				key += "_" + catagoryInforBeans.getFamilyInforDetailId();
			}

			if (catagoryInforBeans.getParentId() != null) {
				key += "_" + catagoryInforBeans.getParentId();
			}

			Log.d("key = ", key);
			Log.d("before ADD hashMap", "size = " + hashMap.size());
			hashMap.put(key, catagoryInforBeans);
			Log.d("AFTER remove hashMap", "size = " + hashMap.size());

//		}
		// if (catagoryInforBeans.getLstCatagoryInforBeans() != null
		// && catagoryInforBeans.getLstCatagoryInforBeans().size() > 0
		// && "1".equals(catagoryInforBeans.getType())) {
		// catagoryInforBeans.setFamilyInforDetailId(familyDetailId++);
		// for (CatagoryInforBeans item : catagoryInforBeans
		// .getLstCatagoryInforBeans()) {
		// item.setValue("");
		// item.setParentId(catagoryInforBeans.getFamilyInforDetailId());
		// }
		// catagoryInforBeans.setIsNew("1");
		// } else {
		// catagoryInforBeans.setFamilyInforDetailId(familyDetailId++);
		// catagoryInforBeans.setParentId(caBeans.getParentId());
		// catagoryInforBeans.setValue("");
		// catagoryInforBeans.setIsNew("1");
		// }

		lstCatagoryInforBeans.add(catagoryInforBeans);
		adapter.notifyDataSetChanged();
	}

	private void resetValueCatagoryInforBeans(
			CatagoryInforBeans catagoryInforBeans) {
		catagoryInforBeans.setValue("");
		catagoryInforBeans.setIsNew("1");
		Long index = familyDetailId++;

		if (catagoryInforBeans.getLstCatagoryInforBeans() != null
				&& !catagoryInforBeans.getLstCatagoryInforBeans().isEmpty()) {
			catagoryInforBeans.setFamilyInforDetailId(index);
			for (CatagoryInforBeans item : catagoryInforBeans
					.getLstCatagoryInforBeans()) {
				item.setParentId(catagoryInforBeans.getFamilyInforDetailId());

				resetValueCatagoryInforBeans(item);
			}
		}else{
			catagoryInforBeans.setFamilyInforDetailId(index);
		}
	}

	private void removeCatagoryInforBeans(CatagoryInforBeans catagoryInforBeans) {

		if (catagoryInforBeans.getLstCatagoryInforBeans() != null
				&& !catagoryInforBeans.getLstCatagoryInforBeans().isEmpty()) {
			for (CatagoryInforBeans item : catagoryInforBeans
					.getLstCatagoryInforBeans()) {
				removeCatagoryInforBeans(item);
			}
		}

		String key = catagoryInforBeans.getInforCode();
		if (catagoryInforBeans.getFamilyInforDetailId() != null) {
			key += "_" + catagoryInforBeans.getFamilyInforDetailId();
		}

		if (catagoryInforBeans.getParentId() != null) {
			key += "_" + catagoryInforBeans.getParentId();
		}

		if (hashMap.containsKey(key)) {
			Log.d("remove key", key);
			hashMap.remove(key);
		}
	}

}
