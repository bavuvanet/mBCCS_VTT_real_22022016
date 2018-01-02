package com.viettel.bss.viettelpos.v4.infrastrucure.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.infrastrucure.adapter.AdapterExpandableListview;
import com.viettel.bss.viettelpos.v4.infrastrucure.adapter.AdapterProvinceSpinner;
import com.viettel.bss.viettelpos.v4.infrastrucure.dal.InfrastrucureDB;
import com.viettel.bss.viettelpos.v4.infrastrucure.object.ObjectExpandable;
import com.viettel.bss.viettelpos.v4.process.PrcDateSingle;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FragmentShowDataInfrastructure extends Fragment implements
		OnClickListener {

    private EditText edtDate;
	private Button btnHome;
	private View mView;
	private EditText edtSearchShowData;
    // private ProgressBar progressBar;

	private BCCSGateway mBccsGateway = new BCCSGateway();
	private AdapterExpandableListview adapter;

    // private final int viewIndex = 0;

	private String choosedDistrict;
	private String choosedProvince;
	private String mCellID;
	// private final String choosedCellID = "";

    private List<ObjectExpandable> objectsLvl1;
	private List<ObjectExpandable> objectsLvl2;
	private List<ObjectExpandable> objectsLvl3;

	private List<ObjectExpandable> noObjectsLevel1;
	private List<ObjectExpandable> noObjectsLevel2;
	private List<ObjectExpandable> noObjectsLevel3;

	// private String shop[];
	private InfrastrucureDB mInfrastrucureDB;

	private ArrayList<AreaObj> disCode1 = new ArrayList<>();
	private ArrayList<AreaObj> disCode2 = new ArrayList<>();
	private ArrayList<AreaObj> mListBTS = new ArrayList<>();
	private final ArrayList<AreaObj> mListBTSName = new ArrayList<>();
	private ArrayList<AreaObj> mListCell = new ArrayList<>();

	private String mBtsName = "";
	private String mCellName = "";
	private TextView txtNameActionBar;

	// private int mCellId;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onResume() {

		super.onResume();
        MainActivity.getInstance().setTitleActionBar(R.string.infrastructure_3);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// MainActivity.mActionBarMain.setDisplayHomeAsUpEnabled(true);
		// MainActivity.mActionBarMain.setDisplayShowHomeEnabled(false);
		// MainActivity.mActionBarMain.setDisplayShowTitleEnabled(true);

        ArrayList<String> districtArray = new ArrayList<>();
        ArrayList<String> provinceArray = new ArrayList<>();
        ArrayList<String> viewArray = new ArrayList<>();
		objectsLvl1 = new ArrayList<>();
		objectsLvl2 = new ArrayList<>();
		objectsLvl3 = new ArrayList<>();
		noObjectsLevel1 = new ArrayList<>();
		noObjectsLevel2 = new ArrayList<>();
		noObjectsLevel3 = new ArrayList<>();

		mInfrastrucureDB = new InfrastrucureDB(getActivity());
		// shop = new String[2];

		// new GetDataTask().execute();
		getListSpinerDialog();
		mBccsGateway = new BCCSGateway();
		if (mView == null) {
			mView = inflater
					.inflate(R.layout.showdata_layout, container, false);
			unit(mView);

		}
		return mView;
	}

	private void getDaTa() {
		// shop = mInfrastrucureDB.getProvince();
		getListSpinerDialog();
	}

	class GetDataTask extends AsyncTask<Void, Void, String> {

		private final ProgressDialog dialog;

		public GetDataTask() {
			dialog = new ProgressDialog(getActivity());
		}

		@Override
		protected String doInBackground(Void... params) {

			String original = "";
			getDaTa();
			return original;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			this.dialog.setMessage(getResources().getString(R.string.waitting));
			this.dialog.show();
			this.dialog.setCancelable(false);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			// parseResultError(result);

			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			try {
				// BTS
				if (mListBTS != null) {
					mListBTS.clear();
				}
				mListBTS = mInfrastrucureDB.getListBTS(Session.province,
						Session.district);
				if (mListBTSName != null) {
					mListBTSName.clear();
				}
				Log.e("TAG6 mListBTS 1", "mListBTS 1: " + mListBTS);
				for (int i = 0; i < mListBTS.size(); i++) {
					String str = mListBTS.get(i).getName();
					String strSplit[] = str.split(";");
					int id = mListBTS.get(i).getId();
                    for (String aStrSplit : strSplit) {
                        AreaObj areaObj = new AreaObj();
                        areaObj.setName(aStrSplit);
                        areaObj.setId(id);
                        mListBTSName.add(areaObj);
                    }
				}
				AreaObj areaObj = new AreaObj();
				areaObj.setId(0);
				areaObj.setName(getString(R.string.all));
				mListBTSName.add(0, areaObj);
				Log.e("TAG6 mListBTS 2", "mListBTS 2: " + mListBTS);
			} catch (Exception e) {
				// TODO: handle exception
				Log.e("TAG6 mListBTS 2", "e: " + e.toString());
			}

			CustomDialogShowData customDialogShowData = new CustomDialogShowData(
					getActivity());
			customDialogShowData.show();
		}
	}

	private void getListSpinerDialog() {

		if (disCode1.isEmpty()) {
			disCode1 = mInfrastrucureDB.getListDis(Session.province);

			String disCodeStr1 = Session.province + Session.district;
			disCode2 = mInfrastrucureDB.getListDis(disCodeStr1);
		}

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
        FragmentManager manager = getActivity().getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

		super.onActivityCreated(savedInstanceState);
	}

	private final boolean cancelDpd = false;

	@Override
	public void onClick(View v) {
		Date mDateFrom = prcDateSingle.mDate;
		switch (v.getId()) {
		case R.id.txt_date_from:
			DatePickerDialog dpd = new FixedHoloDatePickerDialog(getActivity(),
					AlertDialog.THEME_HOLO_LIGHT,	prcDateSingle.onDateSetListener, mDateFrom.getYear(),
					mDateFrom.getMonth(), mDateFrom.getDate());

			dpd.show();
			if (Build.VERSION.SDK_INT >= 18) {
				dpd.setCancelable(cancelDpd);
			}
			break;
		case R.id.btnHome:
			CommonActivity.callphone(getActivity(), Constant.phoneNumber);
			break;
		case R.id.edtSearchShowData:
			new GetDataTask().execute();
			break;
		case R.id.btnSubmit:

			if (mBtsName == null) {
				mBtsName = "";
			}
			if (mCellID == null) {
				mCellID = "";
			}
			if (prcDateSingle.checkDate(R.string.report_warn_date_now)) {
				if (mBtsName.isEmpty() && mCellID.isEmpty()) {
					CommonActivity.createDialog(getActivity(),
							R.string.warn_big_data, R.string.app_name,
							R.string.buttonOk, R.string.buttonCancel, lsnClick,
							null).show();
				} else {
					exe();
				}

			} else {

			}

			break;
		case R.id.relaBackHome:
			getActivity().onBackPressed();
			break;

		default:
			break;
		}
	}

	private final OnClickListener lsnClick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			exe();
		}
	};

	private void exe() {
		// TODO Auto-generated method stub
        ShowDataTask showDataTask = new ShowDataTask(mBtsName, "" + mCellID,
                Session.district, Session.province);
		showDataTask.execute();
	}

	private PrcDateSingle prcDateSingle;

	private void unit(View v) {

		edtSearchShowData = (EditText) v.findViewById(R.id.edtSearchShowData);
		edtSearchShowData.setActivated(false);
		edtSearchShowData.setOnClickListener(this);
        Button btnSearchShowData = (Button) v.findViewById(R.id.btnSubmit);
		btnSearchShowData.setOnClickListener(this);
		edtDate = (EditText) v.findViewById(R.id.txt_date_from);
		edtDate.setOnClickListener(this);
        ExpandableListView list = (ExpandableListView) v.findViewById(R.id.lvExpand);

		adapter = new AdapterExpandableListview(getActivity(), noObjectsLevel1);
		list.setGroupIndicator(null);
		list.setAlwaysDrawnWithCacheEnabled(false);
		list.setChildIndicator(null);
		list.setDivider(new ColorDrawable(android.R.color.transparent));
		// list.setBackgroundResource(R.drawable.background_show_data);
		list.setAdapter(adapter);

		list.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		prcDateSingle = new PrcDateSingle(getActivity(), edtDate, textDate,
				true, true);
	}

	private String textDate;

    private class CustomDialogShowData extends Dialog {
		private EditText provinceSpinner;
		private EditText districtSpinner;
		private Spinner spinnerBTS;
		private Spinner cellIDSpinner;
		private Button search;

		public CustomDialogShowData(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		String tag = "show infraste";

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.dialog_showdata_search_layout);

			provinceSpinner = (EditText) findViewById(R.id.spinnerProvince);
			districtSpinner = (EditText) findViewById(R.id.spinnerDistrict);

			spinnerBTS = (Spinner) findViewById(R.id.spinnertram);
			cellIDSpinner = (Spinner) findViewById(R.id.spinnerCell);

			// thanh pho
			if (disCode1.size() > 0) {
				provinceSpinner.setText(disCode1.get(0).getName());
			}

			// Quan
			if (disCode2.size() > 0) {
				districtSpinner.setText(disCode2.get(0).getName());
			}

			// BTS
			AdapterProvinceSpinner adapterSpinerBts = new AdapterProvinceSpinner(
					mListBTSName, getActivity());
			spinnerBTS.setAdapter(adapterSpinerBts);
			setSpinerCell(cellIDSpinner, mListBTSName.get(0).getName());
			search = (Button) findViewById(R.id.btnSearch_Dialog_ShowData);
			search.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dismiss();
					setTextEdt();
					// shop[1] dis

				}
			});

			spinnerBTS.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					// choosedProvince = provinceArray.get(position);

					if (mListBTSName.get(position).getId() == 0) {
						mBtsName = "";
					} else {
						mBtsName = mListBTSName.get(position).getName();
					}
					setSpinerCell(cellIDSpinner, mBtsName);
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					mBtsName = "";
				}
			});

			cellIDSpinner
					.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> parent,
								View view, int position, long id) {
							mCellName = mListCell.get(position).getName();

							if (mListCell.get(position).getId() == 0) {
								mCellID = "";
							} else {
								mCellID = "" + mListCell.get(position).getId();
							}
						}

						@Override
						public void onNothingSelected(AdapterView<?> parent) {
							// TODO Auto-generated method stub
							mCellName = "";
							mCellID = "";
						}
					});
		}
	}

	private void setSpinerCell(Spinner cellIDSpinner, String mBtsName) {
		// Cell
		if (mListCell != null) {
			mListCell.clear();
		}
		mListCell = mInfrastrucureDB.getListCell(mBtsName);

		AreaObj areaObj = new AreaObj();
		areaObj.setName(getResources().getString(R.string.all));
		mListCell.add(0, areaObj);
		AdapterProvinceSpinner adapterSpinerCell = new AdapterProvinceSpinner(
				mListCell, getActivity());
		cellIDSpinner.setAdapter(adapterSpinerCell);
	}

	public class ShowDataTask extends AsyncTask<Void, Void, String> {
		private final String btsName;
		private final String choosedCellID;
		private final String choosedDistrict;
		private final String choosedProvince;
		private final ProgressDialog dialog;

		// public ShowDataTask(String choosedCellID, String choosedDistrict,
		// String choosedProvince) {
		// this.choosedCellID = choosedCellID;
		// this.choosedDistrict = choosedDistrict;
		// this.choosedProvince = choosedProvince;
		// dialog = new ProgressDialog(getActivity());
		// }
		public ShowDataTask(String btsName, String choosedCellID,
				String choosedDistrict, String choosedProvince) {
			this.btsName = btsName;
			this.choosedCellID = choosedCellID;
			this.choosedDistrict = choosedDistrict;
			this.choosedProvince = choosedProvince;
			dialog = new ProgressDialog(getActivity());
		}

		// String id1, id2;
		// public ShowDataTask(String choosedCellID, String choosedDistrict,
		// String choosedProvince, String id1, String id2) {
		// this.choosedCellID = choosedCellID;
		// this.choosedDistrict = choosedDistrict;
		// this.choosedProvince = choosedProvince;
		// this.id1 = id1;
		// this.id2 = id2;
		// dialog = new ProgressDialog(getActivity());
		// }
		@Override
		protected void onPreExecute() {
			// progressBar.setVisibility(View.VISIBLE);
			this.dialog.setMessage(getResources().getString(R.string.waitting));
			this.dialog.show();
			this.dialog.setCancelable(false);
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(Void... params) {
			// getDistrict("area");
			String reponse = null;
			String original = null;
			String xml = mBccsGateway.buildInputGatewayWithRawData2(
					createXML(btsName, choosedCellID, choosedDistrict,
							choosedProvince,
							// getDate(System.currentTimeMillis(), "yyyy-MM-dd")
							getDate1("yyyy-MM-dd")),
					"mbccs_reportBusinessResult");
			Log.e("TAG6969", "xml reportBusinessResult" + xml);
			boolean isNetwork = CommonActivity
					.isNetworkConnected(getActivity());
			if (isNetwork) {

				try {

					reponse = mBccsGateway.sendRequest(xml,
							Constant.BCCS_GW_URL, getActivity(),
							"mbccs_reportBusinessResult");
				} catch (Exception e) {

					e.printStackTrace();
				}
				if (reponse != null) {
					CommonOutput commonOutput;
					try {
						commonOutput = mBccsGateway.parseGWResponse(reponse);
						original = commonOutput.getOriginal();
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			} else {
			}
			Log.e("TAG6969", "original reportBusinessResult" + original);
			return original;
		}

		@Override
		protected void onPostExecute(String result) {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			Log.e("TAG6969", "result reportBusinessResult" + result);
			if (result == null || result.equals("")) {
				CommonActivity.createAlertDialog(getActivity(),
						getResources().getString(R.string.errorNetwork),
						getResources().getString(R.string.app_name)).show();
			} else {
				parseResultError(result);
				parseResult(result);
				// setTextEdt();
			}

			super.onPostExecute(result);
		}
	}

	private String createXML(String btsName, String cellid, String district,
			String province, String reportDate) {
		Log.d("", "cellid,district,province, reportDate: " + cellid + ","
				+ district + "," + province + "," + reportDate);
		StringBuilder stringBuilder = new StringBuilder(
				"<ws:reportBusinessResult>");
		stringBuilder.append("<reportInput>");
		stringBuilder.append("<token>").append(Session.getToken()).append("</token>");
		if (!TextUtils.isEmpty(btsName)) {
			stringBuilder.append("<stationCode>").append(btsName).append("</stationCode>");
			if (!TextUtils.isEmpty(cellid)) {
				stringBuilder.append("<cellId>").append(cellid).append("</cellId>");
			}
		}

		if (!TextUtils.isEmpty(district)) {
			stringBuilder.append("<district>").append(district).append("</district>");
		}
		if (!TextUtils.isEmpty(province)) {
			stringBuilder.append("<province>").append(province).append("</province>");
		}
		stringBuilder.append("<reportDate>").append(reportDate).append("</reportDate>");
		stringBuilder.append("</reportInput>");
		stringBuilder.append("</ws:reportBusinessResult>");
		return stringBuilder.toString();
	}

	private void setTextEdt() {
		String edt = "";
		if (mBtsName.equals("") || mBtsName == null) {
			edt = Session.province + Session.district;
		} else {
			if (mCellName.equals("") || mCellName == null) {
				edt = Session.province + Session.district + "-" + mBtsName;
			} else
				edt = Session.province + Session.district + "-" + mBtsName
						+ "-" + mCellName;
		}

		edtSearchShowData.setText(edt);
		Log.e("TAG6", "shop[0], shop[1],mCellID :" + Session.province + ","
				+ Session.district + ", " + mCellID);
	}

	private void parseResult(String result) {

		if (result != null) {
			try {
				objectsLvl1.clear();
				objectsLvl2.clear();
				objectsLvl3.clear();
				noObjectsLevel1.clear();
				noObjectsLevel2.clear();
				noObjectsLevel3.clear();
				Log.e("TAG 69", "Data table :" + result);
				XmlDomParse domParse = new XmlDomParse();
				Document document = domParse.getDomElement(result);

				if (document == null) {
					CommonActivity.createAlertDialog(getActivity(),
							R.string.exception, R.string.app_name).show();
					return;
				}
				NodeList nodeList = document
						.getElementsByTagName("lstReportBean");
				for (int i = 0; i < nodeList.getLength(); i++) {
					Node mNode = nodeList.item(i);
					Element element = (Element) mNode;
					int groupId = Integer.valueOf(domParse.getValue(element,
							"groupId"));
					String index = URLDecoder.decode(
							domParse.getValue(element, "index"), "UTF-8");
					String indexId = domParse.getValue(element, "indexId");
					int indexLevel = Integer.valueOf(domParse.getValue(element,
							"indexLevel"));
					String unit = domParse.getValue(element, "unit");
					String total = "";

					int parentIndexId = 0;

					if (indexLevel == 2 || indexLevel == 3) {
						try {
							parentIndexId = Integer.valueOf(domParse.getValue(
									element, "parentIndexId"));

						} catch (Exception e) {
							Log.e("TAG6",
									"parentIndexId :" + "," + e.toString());
							parentIndexId = 0;
						}
					}

					int totalNumber = 0;
					try {
						total = (domParse.getValue(element, "total"));
					} catch (Exception e) {
						Log.e("TAG6",
								"totalNumber" + total + "," + e.toString());
					}

					if (indexLevel == 1) {
						ObjectExpandable object = new ObjectExpandable(index,
								groupId, indexId, indexLevel, null, unit,
								total, null);
						objectsLvl1.add(object);
					} else if (indexLevel == 2) {
						ObjectExpandable object2 = new ObjectExpandable(index,
								groupId, indexId, indexLevel, null, unit,
								total, parentIndexId, null);
						objectsLvl2.add(object2);
					} else if (indexLevel == 3) {
						ObjectExpandable object3 = new ObjectExpandable(index,
								groupId, indexId, indexLevel, null, unit,
								total, parentIndexId, null);
						objectsLvl3.add(object3);
					}
					Log.e("TAG6",
							" objectsLvl1.size() :" + "," + objectsLvl1.size()
									+ "," + objectsLvl2.size() + ","
									+ objectsLvl3.size());

				}

				if (objectsLvl1.size() > 0) {
					for (int i = 0; i < objectsLvl1.size(); i++) {
						ObjectExpandable object1 = objectsLvl1.get(i);

						List<ObjectExpandable> noObjectsLevel22 = new ArrayList<>();
						String id = objectsLvl1.get(i).getIndexId();
						int indexId1 = Integer.parseInt(id);

						for (int j = 0; j < objectsLvl2.size(); j++) {
							ObjectExpandable object2 = objectsLvl2.get(j);
							String indexId2Str = object2.getIndexId();
							int indexId2 = Integer.parseInt(indexId2Str);
							int iDParent = object2.getParentIndexId();

							List<ObjectExpandable> noObjectsLevel33 = new ArrayList<>();

							if (iDParent == indexId1) {
								for (int n = 0; n < objectsLvl3.size(); n++) {
									ObjectExpandable object3 = objectsLvl3
											.get(n);
									int iDparent3 = object3.getParentIndexId();
									if (iDparent3 == indexId2) {
										noObjectsLevel33
												.add(new ObjectExpandable(
														object3.getName(),
														object3.getGroupId(),
														object3.getIndexId(),
														object3.getIndexLevel(),
														object3.getListCellId(),
														object3.getUnit(),
														object3.getTotal(),
														null));
									}

								}
								noObjectsLevel22.add(new ObjectExpandable(
										object2.getName(),
										object2.getGroupId(), object2
												.getIndexId(), object2
												.getIndexLevel(), object2
												.getListCellId(), object2
												.getUnit(), object2.getTotal(),
										noObjectsLevel33));
							}
						}
						noObjectsLevel1.add(new ObjectExpandable(object1
								.getName(), object1.getGroupId(), object1
								.getIndexId(), object1.getIndexLevel(), object1
								.getListCellId(), object1.getUnit(), object1
								.getTotal(), noObjectsLevel22));
					}

					adapter.notifyDataSetChanged();
				} else {
					Toast.makeText(getActivity(),
							getResources().getString(R.string.ko_co_dl),
							Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public String getDate(long milliSeconds, String dateFormat) {
		// Create a DateFormatter object for displaying date in specified
		// format.
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		// Create a calendar object that will convert the date and time value in
		// milliseconds to date.
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milliSeconds);
		return formatter.format(calendar.getTime());
	}

	// VU
    private String getDate1(String dateFormat) {
		// Create a DateFormatter object for displaying date in specified
		// format.
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		// Create a calendar object that will convert the date and time value in
		// milliseconds to date.
		Date mDate = prcDateSingle.mDate;
		Calendar calendar = Calendar.getInstance();
		calendar.set(mDate.getYear(), mDate.getMonth(), mDate.getDate());
		return formatter.format(calendar.getTime());
	}

	private class LevelOne {

	}

	private void parseResultError(String result) {
		if (result != null) {
			try {
				XmlDomParse domParse = new XmlDomParse();
				Document document = domParse.getDomElement(result);

				NodeList nodeListErrorCode = document
						.getElementsByTagName("return");

				for (int i = 0; i < nodeListErrorCode.getLength(); i++) {
					Node mNode = nodeListErrorCode.item(i);
					Element element = (Element) mNode;
					String errorCode = domParse.getValue(element, "errorCode");
					String description = domParse.getValue(element,
							"description");
					String token = domParse.getValue(element, "token");
					if (token == null || token.equals("")) {

					} else {
						Session.setToken(token);
					}
					if (errorCode.equalsIgnoreCase(Constant.INVALID_TOKEN)) {
						CommonActivity.BackFromLogin(getActivity(),
								description, "");
					} else if (errorCode.equals("0")) {
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
