package com.viettel.bss.viettelpos.v4.work.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
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
import android.widget.AdapterView.OnItemSelectedListener;
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
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.handler.VSAMenuHandler;
import com.viettel.bss.viettelpos.v4.synchronizationdata.GetMaxOraRowScnDal;
import com.viettel.bss.viettelpos.v4.synchronizationdata.OjectSyn;
import com.viettel.bss.viettelpos.v4.synchronizationdata.SynchronizationOneData;
import com.viettel.bss.viettelpos.v4.work.adapter.AdapterCriterial;
import com.viettel.bss.viettelpos.v4.work.adapter.AdapterCriterialSpinner;
import com.viettel.bss.viettelpos.v4.work.adapter.AdapterSalePoint.onSelectListenner;
import com.viettel.bss.viettelpos.v4.work.adapter.AdapterUpdateCriterial;
import com.viettel.bss.viettelpos.v4.work.dal.JobDal;
import com.viettel.bss.viettelpos.v4.work.object.CollectedObjectInfo;
import com.viettel.bss.viettelpos.v4.work.object.ObjectDetailGroup;
import com.viettel.bss.viettelpos.v4.work.object.OjectSpinerCriterial;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static com.viettel.bss.viettelpos.v4.R.id.relaBackHome;

public class FragmentUpdateCriteria extends Fragment implements
		OnItemClickListener, OnClickListener, onSelectListenner {
    private String errorMessage = "";
    private AdapterUpdateCriterial mAdapterUpdateCriterial;
	private Button btnHome;
	private JobDal mJobDal;
    private ObjectDetailGroup mDetailGroup = null;
    private FragmentUpdateCriteria mFragmentUpdateCriteria;
	private TextView txtNameActionBar;
	private ArrayList<ObjectDetailGroup> lstObjs = new ArrayList<>();
	private final ArrayList<OjectSpinerCriterial> mArrO = new ArrayList<>();
	private CollectedObjectInfo mCollectedObjectInfo = null;
	private String slectDate;

	private int position;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

		this.getView().setFocusableInTouchMode(true);

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.i("TAG", "onActivityResult FragmentUpdateCriteria");
	}

	@Override
	public void onAttach(Activity activity) {
		Log.i("TAG", "onAttach FragmentUpdateCriteria");
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("TAG", "onCreateView FragmentUpdateCriteria");
		View mView = inflater.inflate(R.layout.layout_update_criteria,
				container, false);
		unit(mView);

		return mView;
	}

	@Override
	public void onDestroy() {
		Log.i("TAG", "onDestroy FragmentUpdateCriteria");
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		Log.i("TAG", "onDestroyView FragmentUpdateCriteria");

		super.onDestroyView();
	}

	@Override
	public void onDetach() {
		Log.i("TAG", "onDetach FragmentUpdateCriteria");
		// TODO Auto-generated method stub
		super.onDetach();
	}

	@Override
	public void onPause() {
		Log.i("TAG", "onPause FragmentUpdateCriteria");
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onResume() {
		Log.i("TAG", "onResume FragmentUpdateCriteria");

		super.onResume();
	}

	@Override
	public void onStart() {
		Log.i("TAG", "onStart FragmentUpdateCriteria");
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onStop() {
		Log.i("TAG", "onStop FragmentUpdateCriteria");
		// TODO Auto-generated method stub
		super.onStop();
	}

	private void unit(View v) {

		try {
			Log.i("TAG", "onActivityCreated FragmentUpdateCriteria");
			Bundle mBundle = getArguments();
			Boolean getValue = true;
			if (mBundle != null) {
				if (mBundle
						.getSerializable(Define.KEY_CRITERIA_CHILD) != null) {
					mDetailGroup = (ObjectDetailGroup) mBundle
							.getSerializable(Define.KEY_CRITERIA_CHILD);
				} else {
					mDetailGroup = (ObjectDetailGroup) mBundle
							.getSerializable(Define.KEY_CRITERIA);
				}
				if (mBundle.getSerializable(Define.KEY_COLLECTED_INFO) != null) {
					mCollectedObjectInfo = (CollectedObjectInfo) mBundle
							.getSerializable(Define.KEY_COLLECTED_INFO);
				}
				if (mBundle.getSerializable(Define.GET_VALUE) != null) {
					getValue = (Boolean) mBundle
							.getSerializable(Define.GET_VALUE);
				}

			}
            ObjectDetailGroup mObjectDetailGroup = (ObjectDetailGroup) mBundle
                    .getSerializable(Define.KEY_CRITERIA);
            ObjectDetailGroup mObjectChild = (ObjectDetailGroup) mBundle
                    .getSerializable(Define.KEY_CRITERIA_CHILD);

			MainActivity.getInstance().setTitleActionBar(mObjectDetailGroup.getName());

			mFragmentUpdateCriteria = new FragmentUpdateCriteria();

			mBundle.putSerializable(Define.KEY_CRITERIA_CHILD, null);

			mJobDal = new JobDal(getActivity());
            Button btUpdateOU = (Button) v.findViewById(R.id.btUpdateOU);
			mJobDal.open();
			if (mDetailGroup != null) {
				Log.i("TIM CON", "TIM CON = " + mDetailGroup.getId());
				if (lstObjs != null) {
					lstObjs.clear();
				}

                String TABLE_NAME = "object_detail_group";
                lstObjs = mJobDal.getObjectGroupChild(TABLE_NAME,
						mDetailGroup.getId(), Session.loginUser.getStaffCode(),
						getValue);
                ListView lvUpdateCriteria = (ListView) v
                        .findViewById(R.id.lvUpdateCriteria);
				Boolean checkButton = false;
				checkButton = checkButton();
				if (lstObjs.size() > 0) {
					// Check loai danh sach tra ve

					if (checkHaveChildrend(lstObjs)) {
						// Co sanh sach con
						Log.i("TAG", "muc cha");
                        AdapterCriterial mAdapter = new AdapterCriterial(getActivity(), lstObjs);
						lvUpdateCriteria.setAdapter(mAdapter);
						lvUpdateCriteria.setOnItemClickListener(this);
						btUpdateOU.setVisibility(View.GONE);
					} else {
						Log.i("TAG", "muc con");
						// Khong co danh sach con
//						txtNameActionBar.setText(mDetailGroup.getName());
						if (mCollectedObjectInfo != null) {
							Log.i("TAG", "muc la");
							lstObjs.clear();
							lstObjs = mJobDal.getObjectGroupChildWithValue(
                                    TABLE_NAME, mDetailGroup.getId(),
									Session.loginUser.getStaffCode(),
									mCollectedObjectInfo.getId());
							mAdapterUpdateCriterial = new AdapterUpdateCriterial(
									getActivity(), lstObjs);
							lvUpdateCriteria
									.setAdapter(mAdapterUpdateCriterial);
							btUpdateOU.setOnClickListener(this);
							btUpdateOU.setText(getResources().getString(
									R.string.btnUpdate));
							btUpdateOU.setVisibility(View.VISIBLE);
							lvUpdateCriteria.setOnItemClickListener(this);

						} else {
							Log.i("TAG", "muc con muc con muc con " + getValue);

							mAdapterUpdateCriterial = new AdapterUpdateCriterial(
									getActivity(), lstObjs);
							lvUpdateCriteria
									.setAdapter(mAdapterUpdateCriterial);
							btUpdateOU.setOnClickListener(this);
							if (checkButton) {
								btUpdateOU.setText(getResources().getString(
										R.string.btnUpdate));
							} else {
								btUpdateOU.setText(getResources().getString(
										R.string.addNewItem));
							}

							btUpdateOU.setVisibility(View.VISIBLE);
							lvUpdateCriteria.setOnItemClickListener(this);

						}
					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			mJobDal.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Boolean checkButton() {
		for (ObjectDetailGroup item : lstObjs) {
			if (item.getmCollectedObjectInfo() != null) {
				return true;
			}
		}
		return false;
	}

	private Boolean checkCollectInfo(ArrayList<ObjectDetailGroup> lstObjs) {
		Boolean check = false;
		for (ObjectDetailGroup item : lstObjs) {
			Log.i("",
					item.getName() + " item.getHaveOinfo() "
							+ item.getHaveOinfo());
			if (item.getHaveOinfo().equals("1")) {
				check = true;
				break;
			}
		}
		return check;
	}

	private Boolean checkHaveChildrend(ArrayList<ObjectDetailGroup> lstObjs) {
		Boolean check = false;
		for (ObjectDetailGroup item : lstObjs) {
			Log.i("",
					item.getName() + " item.getHaveOinfo() "
							+ item.getHaveOinfo());
			if (item.getHaveChild().equals("1")) {
				check = true;
				break;
			}
		}
		return check;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		ObjectDetailGroup mDetailGroup1 = lstObjs.get(arg2);
		position = arg2;
		if (mDetailGroup1.getHaveChild().equals("1")) {

			Bundle mBundle = getArguments();
			mBundle.putSerializable(Define.KEY_CRITERIA_CHILD,
					lstObjs.get(arg2));

			mFragmentUpdateCriteria.setArguments(mBundle);
			ReplaceFragment.replaceFragment(getActivity(),
					mFragmentUpdateCriteria, false);
			// FragmentManager fragmentManager = getFragmentManager();
			// fragmentManager.beginTransaction()
			// .replace(R.id.frame_container, mFragmentUpdateCriteria)
			// .commit();

		} else {
			// Neu da la muc la thi hien thi dialog nhap lieu

			if (mDetailGroup1.getValueType().equals("DATE")) {
				DialogFragment datePickerFragment = new DatePickerCustomFragment();
				datePickerFragment.show(getActivity().getFragmentManager(),
						"datepicker");
			} else if ((mDetailGroup1.getValueType().equals("STRING")
					|| mDetailGroup1.getValueType().equals("NUMBER") || mDetailGroup1
					.getValueType().equals("LIST"))
					&& !checkHaveChildrend(lstObjs)) {
				DialogUpdate dialogUpdate = new DialogUpdate(getActivity(),
						arg2, mDetailGroup1.getValueType());
				dialogUpdate.show();
			} else if (mDetailGroup1.getValueType().equals("LIST")
					&& checkHaveChildrend(lstObjs)) {
				Bundle mBundle = getArguments();
				mBundle.putSerializable(Define.KEY_CRITERIA_CHILD,
						lstObjs.get(arg2));

				mFragmentUpdateCriteria.setArguments(mBundle);
				ReplaceFragment.replaceFragment(getActivity(),
						mFragmentUpdateCriteria, false);
				// FragmentManager fragmentManager = getFragmentManager();
				// fragmentManager.beginTransaction()
				// .replace(R.id.frame_container, mFragmentUpdateCriteria)
				// .commit();

			}
		}

	}

	private final OnClickListener clickUpdate = new OnClickListener() {

		String nameRequire = "";

		public Boolean checkValidateSubmit(ArrayList<ObjectDetailGroup> arr) {
			Boolean result = true;
			for (ObjectDetailGroup item : lstObjs) {
				if (item.getIsKey().equals("1")
						&& (item.getValue() == null || item.getValue().trim()
								.equals(""))) {

					nameRequire = item.getName();
					result = false;
					break;

				}
			}
			return result;
		}

		@Override
		public void onClick(View v) {
			if (checkValidateSubmit(lstObjs)) {

				if (CommonActivity.isNetworkConnected(getActivity())) {
					CollectMarketInfo mCollectMarketInfo = new CollectMarketInfo(
							getActivity());
					mCollectMarketInfo.execute();
				} else {
					String title = getString(R.string.app_name);
					Dialog dialog1 = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.errorNetwork),
							title);
					dialog1.show();
				}

			} else {
				String message = getResources()
						.getString(R.string.requirefilde)
						+ " '"
						+ nameRequire
						+ "' ";
				String title = getString(R.string.app_name);
				Dialog dialog1 = CommonActivity.createAlertDialog(
						getActivity(), message, title);
				dialog1.show();
			}

		}
	};

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {

		case R.id.btnHome:
			CommonActivity.callphone(getActivity(), Constant.phoneNumber);
			break;
		case relaBackHome:
			getActivity().onBackPressed();
			break;
		case R.id.btUpdateOU:
			Log.e("TAG", "CAP NHAT");

			Dialog dialog = CommonActivity.createDialog(getActivity(),

			getResources()

			.getString(R.string.confirmUpdate),

			getResources().getString(R.string.app_name),

			getResources().getString(R.string.ok), getResources()

			.getString(R.string.cancel), clickUpdate, null);

			dialog.show();

			break;
		case R.id.lvUpdateCriteria:
			break;

		}

	}

	@Override
	public void onSelectBox(int positionTask, int positonSalePoint) {
		// lstObjs.get(posSalePoint).setPositionTask(positionTask);
	}

	@SuppressLint("ValidFragment")
	public class DatePickerCustomFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		public DatePickerCustomFragment() {
			super();

		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			return new FixedHoloDatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {

			slectDate = String.valueOf(day) + "/" +
                    (month + 1) + "/" + year + " ";
			CollectedObjectInfo item = new CollectedObjectInfo();
			item.setValue(slectDate);
			lstObjs.get(position).setmCollectedObjectInfo(item);
			lstObjs.get(position).setValue(slectDate);
			mAdapterUpdateCriterial.notifyDataSetChanged();
		}

	}

	private class DialogUpdate extends Dialog implements
			android.view.View.OnClickListener {

		private EditText edtValue;
		private Button btnUpdateDilog;
		private final int position;
		private final String inputType;
		private TextView txtTitleOnlineDialog;
		private Spinner mSpinner;

		public DialogUpdate(Context context, int position, String inputType) {
			super(context);
			this.position = position;
			this.inputType = inputType;
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.dialog_criteral_update);
			getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
			edtValue = (EditText) findViewById(R.id.edtValue);
			txtTitleOnlineDialog = (TextView) findViewById(R.id.txtTitleOnlineDialog);
			btnUpdateDilog = (Button) findViewById(R.id.btnUpdateDialog);
			btnUpdateDilog.setOnClickListener(this);

			mSpinner = (Spinner) findViewById(R.id.spShowCriterial);
			mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int positionChild, long id) {
					Log.e("TAG", "vi tri " + positionChild
							+ " NOI DUNG CAP NHAT = "
							+ mArrO.get(positionChild).getValue());
					if (positionChild > 0) {
						CollectedObjectInfo item = new CollectedObjectInfo();
						item.setValue(mArrO.get(positionChild).getValue());
						lstObjs.get(position).setmCollectedObjectInfo(item);
						lstObjs.get(position).setValue(
								mArrO.get(positionChild).getValue());
						mAdapterUpdateCriterial.notifyDataSetChanged();
						dismiss();
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {

				}
			});

			txtTitleOnlineDialog.setText(lstObjs.get(position).getName());
            switch (inputType) {
                case "STRING": {
                    edtValue.setInputType(InputType.TYPE_CLASS_TEXT);
                    int maxLength = 100;
                    InputFilter[] FilterArray = new InputFilter[1];
                    FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                    edtValue.setFilters(FilterArray);
                    break;
                }
                case "NUMBER": {
                    edtValue.setInputType(InputType.TYPE_CLASS_NUMBER);
                    int maxLength = 15;
                    InputFilter[] FilterArray = new InputFilter[1];
                    FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                    edtValue.setFilters(FilterArray);
                    break;
                }
                default:
                    mSpinner.setVisibility(View.VISIBLE);
                    String[] dataSpiner = new String[]{};
                    mArrO.clear();
                    OjectSpinerCriterial defaultItem = new OjectSpinerCriterial(
                            getResources().getString(R.string.selectTitles), "0");
                    mArrO.add(defaultItem);
                    JobDal mDal = new JobDal(getActivity());
                    try {
                        mDal.open();
                        //
                        ObjectDetailGroup mItem = mDal.getObjectChildOfList(
                                Define.object_detail_group, lstObjs.get(position)
                                        .getId());
                        if (mItem != null) {
                            dataSpiner = mItem.getName().split(";");
                        } else {
                            dataSpiner = lstObjs.get(position).getName().split(";");
                            mItem = mDal.getObjectGroupDetailById(
                                    Define.object_detail_group,
                                    lstObjs.get(position).getParentID());
                            txtTitleOnlineDialog.setText(mItem.getName());
                        }

                        for (String item : dataSpiner) {
                            OjectSpinerCriterial mOjectSpinerCriterial = new OjectSpinerCriterial(
                                    item, item);
                            mArrO.add(mOjectSpinerCriterial);

                        }
                        if (mArrO.size() > 0) {
                            AdapterCriterialSpinner adapterSpinner = new AdapterCriterialSpinner(
                                    getActivity(), mArrO);
                            mSpinner.setAdapter(adapterSpinner);
                        }
                        edtValue.setVisibility(View.GONE);

                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } finally {
                        if (mDal != null) {
                            mDal.close();
                        }
                    }
                    break;
            }
			if (lstObjs.get(position).getmCollectedObjectInfo() != null
					&& !lstObjs.get(position).getmCollectedObjectInfo()
							.getValue().equals("")) {

				edtValue.setText(lstObjs.get(position)
						.getmCollectedObjectInfo().getValue());
			} else {
				edtValue.setHint(getResources().getString(R.string.enterDesc));
			}
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnUpdateDialog:
				String content = edtValue.getText().toString().trim();
				if (StringUtils.CheckCharSpecical_1(content)) {
					Toast.makeText(
							getActivity(),
							getResources().getString(
									R.string.checkcharspecical_),
							Toast.LENGTH_LONG).show();
				} else {
					CollectedObjectInfo item = new CollectedObjectInfo();
					item.setValue(content);
					lstObjs.get(position).setmCollectedObjectInfo(item);
					lstObjs.get(position).setValue(content);
					mAdapterUpdateCriterial.notifyDataSetChanged();
					dismiss();
				}

				break;

			default:
				break;
			}

		}

	}

	private class CollectMarketInfo extends AsyncTask<String, String, String> {
		final ProgressDialog progress;
		private final String title = getString(R.string.app_name);
		private String msg = "";
		private final Context mContext;
		private final Activity activity;

		public CollectMarketInfo(Context context) {
			this.mContext = context;
			this.activity = (Activity) context;
			this.progress = new ProgressDialog(getActivity());
			this.progress.setMessage(getResources()
					.getString(R.string.waitting));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {

			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_collectMarketInfo");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:collectMarketInfo>");
				rawData.append("<collectInput>");
				HashMap<String, String> param = new HashMap<>();
				HashMap<String, String> paramToken = new HashMap<>();

				for (ObjectDetailGroup item : lstObjs) {

					// Tao request gui len serve

					HashMap<String, String> rawDataItem = new HashMap<>();
					// if (area.getAreaCode() != null && area.getAreaCode() !=
					// "") {
					// rawDataItem.put("areaCode", area.getAreaCode());
					// }
					if (mCollectedObjectInfo != null) {
						rawDataItem.put("id", mCollectedObjectInfo.getId());
					}

					if (item.getId() != null && item.getId() != "") {
						rawDataItem.put("groupId", item.getId());
					}

					if (item.getIsKey() != null && item.getIsKey() != "") {
						rawDataItem.put("isKey", item.getIsKey());
					}

					if (item.getValue() != null && item.getValue() != "") {
						Log.e("TAG",
								"StringUtils.xmlEscapeText(item.getValue())) = "
										+ StringUtils.xmlEscapeText(">"));
						rawDataItem.put("value",
								StringUtils.xmlEscapeText(item.getValue()));
					}
					param.put("lstCollected", input.buildXML(rawDataItem));
					rawData.append(input.buildXML(param));

				}

				paramToken.put("token", Session.getToken());

				rawData.append(input.buildXML(paramToken));
				rawData.append("</collectInput>");
				rawData.append("</ws:collectMarketInfo>");

				Log.i("LOG_TAG", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG_TAG", envelope);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,getActivity(),"mbccs_collectMarketInfo");
				Log.i("LOG", response);
				CommonOutput output = input.parseGWResponse(response);
				if (!output.getError().equals("0")) {
					errorMessage = output.getDescription();
					return Constant.ERROR_CODE;
				}

				String original = output.getOriginal();
				VSAMenuHandler handler = (VSAMenuHandler) CommonActivity
						.parseXMLHandler(new VSAMenuHandler(), original);
				output = handler.getItem();

				if (!output.getErrorCode().equals("0")) {
					errorMessage = output.getDescription();
					return output.getErrorCode();
				}

				if (output.getToken() != null && !output.getToken().isEmpty()) {
					Session.setToken(output.getToken());
				}
				return Constant.SUCCESS_CODE;

			} catch (Exception e) {
				e.printStackTrace();
				errorMessage = getResources().getString(R.string.exception)
						+ e.toString();
				return Constant.ERROR_CODE;
			}

		}

		@Override
		protected void onPostExecute(String result) {
			Log.e("Kiem tra", "Log = " + result);
			Dialog dialog = null;
            switch (result) {
                case Constant.INVALID_TOKEN2:
                    CommonActivity.BackFromLogin(activity, errorMessage, ";work_management;");
                    break;
                case Constant.SUCCESS_CODE:
                    dialog = CommonActivity.createAlertDialog(getActivity(),
                            getResources().getString(R.string.updatesucess), title);
                    dialog.show();
                    GetMaxOraRowScnDal dal = new GetMaxOraRowScnDal(mContext);
                    try {

                        dal.open();
                        OjectSyn obSyn = new OjectSyn("collected_object_info",
                                dal.getMaxOrarow("max_ora_rowscn",
                                        "collected_object_info"));
                        SynchronizationOneData synData = new SynchronizationOneData(
                                getActivity(), obSyn, true);
                        synData.execute();
                        dal.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (dal != null) {
                            dal.close();
                        }
                    }

                    break;
                default:
                    dialog = CommonActivity.createAlertDialog(getActivity(),
                            errorMessage, title);
                    break;
            }

			super.onPostExecute(result);
			progress.dismiss();

		}

	}
}
