package com.viettel.bss.viettelpos.v4.work.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.dialog.DatePickerFragment;
import com.viettel.bss.viettelpos.v4.handler.VSAMenuHandler;
import com.viettel.bss.viettelpos.v4.login.object.Staff;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.work.adapter.AdapterSalePoint;
import com.viettel.bss.viettelpos.v4.work.adapter.AdapterSalePoint.onMyCheckBoxChangeListenner;
import com.viettel.bss.viettelpos.v4.work.adapter.AdapterSalePoint.onSelectListenner;
import com.viettel.bss.viettelpos.v4.work.dal.JobDal;
import com.viettel.bss.viettelpos.v4.work.object.SalePoint;
import com.viettel.bss.viettelpos.v4.work.object.TaskObject;
import com.viettel.maps.MapView;
import com.viettel.maps.base.LatLng;
import com.viettel.maps.layers.MapLayer;
import com.viettel.maps.objects.InfoWindow;
import com.viettel.maps.objects.InfoWindowOptions;
import com.viettel.maps.objects.MapObject;
import com.viettel.maps.objects.Marker;
import com.viettel.maps.objects.MarkerOptions;
import com.viettel.maps.objects.PolylineOptions;
import com.viettel.maps.util.MapConfig;
import com.viettel.maps.util.MapConfig.SRSType;

public class FragmentListSalePoint extends Fragment implements Define,
		OnScrollListener, OnItemClickListener, onMyCheckBoxChangeListenner,
		OnClickListener, onSelectListenner {

	private ArrayList<SalePoint> arrayStaff;
	private ListView lvListStaff;
	private AdapterSalePoint mAdapterSalePoint;
	private JobDal mJobDal;
	private int pos = 0;
	private LoadListSalePoint mLoadmoreStaff;
	private View footer;

    private Staff mStaff;
    private View mView;
	private ArrayList<TaskObject> arrayListTask;
	private Button btnJobAssignment, btnShowRoute;
	private LoadTask mLoadTask;
	private EditText searchChannel1;
	private String inputSearch = null;
	private boolean loadmore = false, loadOk = false;
	private final Long staffTypeSearch = 0L;
	private MapView mapView;
	private ViewFlipper mViewFlipper;
	private Boolean showMap = false;
    private TextView sms11;
	private CreateTaskAsyn assignment;

	private final ArrayList<SalePoint> arrayStaffCache = new ArrayList<>();
	private final ArrayList<SalePoint> arrayStaffFromWS = new ArrayList<>();
	private DialogAssignment dialogAssignment = null;
	private TaskObject taskObjectSelect = null;

	@Override
	public void onAttach(Activity activity) {
		Log.d("TAG", "onAttach FragmentListSalePoint");

		super.onAttach(activity);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d("TAG", "onActivityCreated FragmentListSalePoint");

		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d("TAG", "onCreateView FragmentListSalePoint");
		if (mView == null) {
			mJobDal = new JobDal(getActivity());
            Bundle mBundle = getArguments();
			if (mBundle != null) {
				mStaff = (Staff) mBundle.getSerializable(KEY_STAFF);
			}
			// mView = inflater.inflate(R.layout.list_sale_point_job_maneger,
			// container, false);
			mView = inflater.inflate(R.layout.layout_task_manager, container,
					false);
			Unit(mView);
		}
		return mView;
	}

	@Override
	public void onStart() {
		Log.d("TAG", "onStart FragmentListSalePoint");
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onResume() {
		Log.d("TAG", "onResume FragmentListSalePoint");
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onPause() {
		Log.d("TAG", "onPause FragmentListSalePoint");
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onStop() {
		Log.d("TAG", "onStop FragmentListSalePoint");
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d("TAG", "onCreate FragmentListSalePoint");
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		Log.d("TAG", "onDestroy FragmentListSalePoint");
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		Log.d("TAG", "onDetach FragmentListSalePoint");
		try {
			mJobDal.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		super.onDetach();
	}

	@Override
	public void onDestroyView() {
		Log.d("TAG", "onDestroyView FragmentListSalePoint");
		if (mLoadmoreStaff != null)
			mLoadmoreStaff.cancel(true);
		if (mLoadTask != null)
			mLoadTask.cancel(true);
		super.onDestroyView();
	}

	@SuppressLint("InflateParams")
    private void Unit(View v) {
		lvListStaff = (ListView) v.findViewById(R.id.lvSalePoint);
		sms11 = (TextView) v.findViewById(R.id.sms11);
		sms11.setVisibility(View.GONE);
		lvListStaff.setOnScrollListener(this);
		Button selectDate_ = (Button) v.findViewById(R.id.chooseDateJob);
		selectDate_.setVisibility(View.GONE);
		mapView = (MapView) v.findViewById(R.id.mapTaskRoadMap);
		mapView.setMarkerClickListener(new MapLayer.OnClickListener() {

			@Override
			public boolean onClick(Point point, LatLng pos, MapObject obj) {
				Marker marker = (Marker) obj;
				InfoWindowOptions opts = new InfoWindowOptions(marker
						.getTitle(), marker.getPosition());
				opts.snippet(marker.getDescription());
				opts.anchor(new Point(0, -marker.getIcon().getHeight()));
				InfoWindow info = mapView.showInfoWindow(opts);
				// String id = info.getId();
				taskObjectSelect = (TaskObject) marker.getUserData();

				return true;
			}

		});

		mapView.setInfoWindowClickListener(new MapLayer.OnClickListener() {

			@Override
			public boolean onClick(Point point, LatLng pos, MapObject obj) {
				Log.i("TAG",
						" taskObjectSelect:: " + taskObjectSelect.getNameTask());
				String message = taskObjectSelect.getNameTask();
				String title = getString(R.string.app_name);
				Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
						message, title);
				dialog.show();
				return true;
			}

		});

		mViewFlipper = (ViewFlipper) v.findViewById(R.id.vfTaskManager);
		MapConfig.changeSRS(SRSType.SRS_4326);

        TextView txtNameListSalePoint = (TextView) v
                .findViewById(R.id.txtNameListSalePoint);
		btnJobAssignment = (Button) v.findViewById(R.id.btnAssignment);
		btnShowRoute = (Button) v.findViewById(R.id.btnShowRoute);
		btnJobAssignment.setOnClickListener(this);
		btnShowRoute.setOnClickListener(this);

		if (mStaff != null)
			txtNameListSalePoint.setText(getResources().getString(
					R.string.text_list_sale_point)
					+ " " + mStaff.getNameStaff());
		arrayStaff = new ArrayList<>();
		arrayListTask = new ArrayList<>();
		mAdapterSalePoint = new AdapterSalePoint(getActivity(), arrayStaff);
		mAdapterSalePoint.setOnMyCheckChange(this);
		mAdapterSalePoint.setOnMySelectListenner(this);

		LayoutInflater inflater = getActivity().getLayoutInflater();
		footer = inflater.inflate(R.layout.footer_layout, null, false);
		// lvListStaff.addFooterView(footer, null, false);
		lvListStaff.setAdapter(mAdapterSalePoint);

		lvListStaff.setOnItemClickListener(this);
		/*
		 * mLoadmoreStaff = new LoadListSalePoint(); mLoadmoreStaff.execute();
		 */
		searchChannel1 = (EditText) v.findViewById(R.id.searchStaff);
		searchChannel1.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				showKeyboard();
			}
		});
		searchChannel1.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				Log.e("SEARC",
						count
								+ "========================== SEARCH ==================== "
								+ before);
				if (count + before > 0) {
					inputSearch = searchChannel1.getText().toString();
					// loadmore = true;
					// loadOk = true;
					arrayStaff.clear();
					pos = 0;
					mLoadmoreStaff = new LoadListSalePoint();
					mLoadmoreStaff.execute();
				}
			}

		});
		mLoadmoreStaff = new LoadListSalePoint();
		mLoadmoreStaff.execute();
	}

	private void hideKeyboard() {
		// Check if no view has focus:

		if (searchChannel1 != null) {
			InputMethodManager inputManager = (InputMethodManager) getActivity()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(
					searchChannel1.getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	private void showKeyboard() {
		// Check if no view has focus:

		if (searchChannel1 != null) {
			InputMethodManager inputManager = (InputMethodManager) getActivity()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(
					searchChannel1.getWindowToken(),
					InputMethodManager.SHOW_FORCED);
		}
	}

	/**
	 * Show thong tin chi tiet
	 */
	private class ShowTaskDetail extends Dialog implements
			android.view.View.OnClickListener {
		private TextView viewDetail;
		private Button btnOk;

		public ShowTaskDetail(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.dialog_assignment);

		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}

	}

	private class SearchAsyn extends
			AsyncTask<Void, Void, ArrayList<SalePoint>> {

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
		}

		@Override
		protected ArrayList<SalePoint> doInBackground(Void... params) {

			/*
			 * return mJobDal.getListAllStaffOwner(Constant.TABLE_STAFF,
			 * staffTypeSearch, mStaff.getStaffId(), inputSearch);
			 */
			if (mStaff != null) {

				return mJobDal.getListStaffOwner(Constant.TABLE_STAFF,
						staffTypeSearch, mStaff.getStaffId(), inputSearch, pos,
						pos + COUNTLOAD);

			} else {
				return null;
			}

		}

		@Override
		protected void onPostExecute(final ArrayList<SalePoint> result) {
			try {
				if (result != null) {
					ArrayList<SalePoint> arrSalePoints = new ArrayList<>();
					if (arrayStaff.size() > 0)
						arrayStaff.clear();

					for (int i = 0; i < result.size(); i++) {

						SalePoint salePoint = result.get(i);
						Log.i("TAG", "::" + salePoint.getSalePointName());
						arrayStaff.add(salePoint);
					}
					Log.e("TAG", "kich thuoc arrayStaff " + arrayStaff.size());
					// for (SalePoint item : arrayStaffCache) {
					// Log.e("TAG", "ten diem ban " + item.getSalePointName());
					// for (SalePoint item1 : arrSalePoints) {
					// if (item.equals(item1)) {
					// arrayStaff.add(item);
					// break;
					// }
					// }
					// }
					if (result.size() < COUNTLOAD) {
						lvListStaff.removeFooterView(footer);
						loadOk = true;
					}
					if (arrayStaff.size() == 0) {
						sms11.setVisibility(View.VISIBLE);
					} else {
						sms11.setVisibility(View.GONE);
					}

					pos = arrayStaff.size();
					// loadOk = true;
					// loadmore = true;
					mAdapterSalePoint.notifyDataSetChanged();
					// new GetMoneyBuyWithListAsynctask(getActivity(), result)
					// .execute(mStaff.getStaffCode(), "0", "100");

				} else {
					Log.i("TAG", "No result");
				}

				super.onPostExecute(result);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private class GetMoneyBuyAsynctask extends AsyncTask<String, Void, String> {
		final BCCSGateway input = new BCCSGateway();
		String response = null;
		String original = null;
		final XmlDomParse parse = new XmlDomParse();
		final Activity activity;
		String errorCode = "";

		// ProgressDialog progress;

		private GetMoneyBuyAsynctask(Activity _activity) {
			Log.e("TAG", "SAP SEP THEO DOANH THU");
			this.activity = _activity;
			// this.progress = new ProgressDialog(getActivity());
			// this.progress.setMessage(getActivity().getResources().getString(
			// R.string.waitting));
			// if (!this.progress.isShowing()) {
			// this.progress.show();
			// }
		}

		public String createXMLGetListStaffIM(String staffCode, String orderBy,
				String maxResult) {
			StringBuilder stringBuilder = new StringBuilder(
					"<ws:getChannelForAssignTask>");

			stringBuilder.append("<channelInput>");
			stringBuilder.append("<token>").append(Session.getToken()).append("</token>");
			stringBuilder.append("<manageStaffCode>").append(staffCode).append("</manageStaffCode>");

			stringBuilder.append("</channelInput>");
			stringBuilder.append("</ws:getChannelForAssignTask>");
			Log.d("createfilexmlSyn", stringBuilder.toString());
			return stringBuilder.toString();
		}

		@Override
		protected String doInBackground(String... arg0) {
			String xml = createXMLGetListStaffIM(arg0[0], arg0[1], arg0[2]);

			CommonOutput output = null;
			try {
				String envelope = input.buildInputGatewayWithRawData2(
						xml.toString(), "mbccs_getChannelForAssignTask");
				Log.e("TAG", "envelope" + envelope);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getChannelForAssignTask");

				output = input.parseGWResponse(response);
				original = output.getOriginal();
				if (!output.getError().equals("0")) {
					errorCode = output.getDescription();
					return Constant.ERROR_CODE;
				}

				if (!output.getErrorCode().equals("0")) {
					errorCode = output.getDescription();
					return output.getErrorCode();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}// get data

			return original;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				Log.d("result sort by sale", result);
                switch (result) {
                    case Constant.INVALID_TOKEN2:
                        CommonActivity.BackFromLogin(getActivity(), errorCode, "");
                        break;
                    case Constant.ERROR_CODE:
                        String title = getString(R.string.app_name);
                        Dialog dialog = CommonActivity.createAlertDialog(
                                getActivity(), errorCode, title);
                        dialog.show();
                        break;
                    default:
                        try {
                            Document doc = parse.getDomElement(result);
                            NodeList nl = doc.getElementsByTagName("return");

                            NodeList nodelstchild = null;

                            for (int i = 0; i < nl.getLength(); i++) {
                                nodelstchild = doc
                                        .getElementsByTagName("lstStaffBean");
                                Log.e("TAG",
                                        "nodelstchild " + nodelstchild.getLength());

                                for (int j = 0; j < nodelstchild.getLength(); j++) {
                                    Staff mStaffObject = new Staff();
                                    Element e1 = (Element) nodelstchild.item(j);
                                    String name = parse.getValue(e1, "name");
                                    mStaffObject.setNameStaff(name);
                                    String staffCode = parse.getValue(e1,
                                            "staffCode");
                                    Long totalRevenue;
                                    if (parse.getValue(e1, "totalRevenue") != null
                                            && parse.getValue(e1, "totalRevenue") != "") {
                                        totalRevenue = Long.parseLong(parse
                                                .getValue(e1, "totalRevenue"));
                                    } else {
                                        totalRevenue = 0L;
                                    }

                                    String visitNum = parse
                                            .getValue(e1, "visitNum");
                                    SalePoint itemI = new SalePoint(
                                            Long.parseLong(visitNum), staffCode,
                                            totalRevenue);

                                    arrayStaffFromWS.add(itemI);

                                    for (SalePoint item : arrayStaff) {
                                        if (item.getStaffCode().toUpperCase()
                                                .equals(staffCode.toUpperCase())) {
                                            Log.e("staff code ",
                                                    item.getStaffCode()
                                                            + " staff code "
                                                            + staffCode);
                                            item.setMoneyBuyOfMonth(totalRevenue);
                                            item.setVisitCount(Long
                                                    .parseLong(visitNum));
                                        }
                                    }

                                }

                            }

                            for (SalePoint item : arrayStaff) {
                                arrayStaffCache.add(item);
                            }

                            mAdapterSalePoint.notifyDataSetChanged();
                            // progress.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
			}
		}
	}

	private class GetMoneyBuyWithListAsynctask extends
			AsyncTask<String, Void, String> {
		final BCCSGateway input = new BCCSGateway();
		String response = null;
		String original = null;
		final XmlDomParse parse = new XmlDomParse();
		final Activity activity;
		private final ArrayList<SalePoint> arrObject;
		final ProgressDialog progress;
		private String errorMessage = "";

		private GetMoneyBuyWithListAsynctask(Activity _activity,
				ArrayList<SalePoint> arrObject) {
			Log.e("TAG", "SAP SEP THEO DOANH THU");
			this.activity = _activity;
			this.arrObject = arrObject;
			this.progress = new ProgressDialog(getActivity());
			this.progress.setMessage(getActivity().getResources().getString(
					R.string.waitting));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		public String createXMLGetListStaffIM(String staffCode, String orderBy,
				String maxResult) {
			StringBuilder stringBuilder = new StringBuilder(
					"<ws:getChannelForAssignTask>");

			stringBuilder.append("<channelInput>");
			stringBuilder.append("<token>").append(Session.getToken()).append("</token>");
			for (SalePoint item : arrObject) {
				stringBuilder.append("<lstStaffCode>").append(item.getStaffCode()).append("</lstStaffCode>");
			}
			stringBuilder.append("<manageStaffCode>").append(staffCode).append("</manageStaffCode>");

			stringBuilder.append("</channelInput>");
			stringBuilder.append("</ws:getChannelForAssignTask>");

			Log.d("createfilexmlSyn", stringBuilder.toString());
			return stringBuilder.toString();
		}

		@Override
		protected String doInBackground(String... arg0) {
			String xml = createXMLGetListStaffIM(arg0[0], arg0[1], arg0[2]);

			CommonOutput output = null;
			try {
				String envelope = input.buildInputGatewayWithRawData2(
						xml.toString(), "mbccs_getChannelForAssignTask");
				Log.e("TAG", "envelope" + envelope);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getChannelForAssignTask");

				output = input.parseGWResponse(response);
				original = output.getOriginal();

				Log.e("TAG", "OUT" + original);
				if (!output.getError().equals("0")) {

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

			} catch (Exception e) {
				e.printStackTrace();
			}// get data

			return original;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				Log.d("result sort by sale", result);
                switch (result) {
                    case Constant.INVALID_TOKEN2:
                        CommonActivity.BackFromLogin(getActivity(), errorMessage,
                                "");
                        break;
                    case Constant.ERROR_CODE:

                        String title = getString(R.string.app_name);
                        Dialog dialog = CommonActivity.createAlertDialog(
                                getActivity(), errorMessage, title);
                        dialog.show();
                        break;
                    default:
                        try {
                            Document doc = parse.getDomElement(result);
                            NodeList nl = doc.getElementsByTagName("return");

                            NodeList nodelstchild = null;

                            for (int i = 0; i < nl.getLength(); i++) {
                                nodelstchild = doc
                                        .getElementsByTagName("lstStaffBean");
                                Log.e("TAG",
                                        "nodelstchild " + nodelstchild.getLength());

                                for (int j = 0; j < nodelstchild.getLength(); j++) {
                                    Staff mStaffObject = new Staff();
                                    Element e1 = (Element) nodelstchild.item(j);
                                    String name = parse.getValue(e1, "name");
                                    mStaffObject.setNameStaff(name);
                                    String staffCode = parse.getValue(e1,
                                            "staffCode");
                                    Long totalRevenue;
                                    if (parse.getValue(e1, "totalRevenue") != null
                                            && parse.getValue(e1, "totalRevenue") != "") {
                                        totalRevenue = Long.parseLong(parse
                                                .getValue(e1, "totalRevenue"));
                                    } else {
                                        totalRevenue = 0L;
                                    }

                                    String visitNum = parse
                                            .getValue(e1, "visitNum");
                                    SalePoint itemI = new SalePoint(
                                            Long.parseLong(visitNum), staffCode,
                                            totalRevenue);

                                    arrayStaffFromWS.add(itemI);

                                    for (SalePoint item : arrObject) {
                                        if (item.getStaffCode().toUpperCase()
                                                .equals(staffCode.toUpperCase())) {
                                            Log.e("staff code ",
                                                    item.getStaffCode()
                                                            + " staff code "
                                                            + staffCode);
                                            item.setMoneyBuyOfMonth(totalRevenue);
                                            item.setVisitCount(Long
                                                    .parseLong(visitNum));
                                        }
                                    }

                                }

                            }

                            for (SalePoint item : arrayStaff) {
                                arrayStaffCache.add(item);
                            }
                            getActivity().runOnUiThread(runnableUdapteAdapter);
                            mAdapterSalePoint.notifyDataSetChanged();
                            progress.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
			}
		}
	}

	private ArrayList<SalePoint> assignValue(ArrayList<SalePoint> arrInput) {
		ArrayList<SalePoint> arrReturn = new ArrayList<>();
		for (SalePoint item : arrInput) {
			for (SalePoint item1 : arrayStaffFromWS) {
				if (item.equals(item1)) {
					arrReturn.add(item);
					break;
				}
			}

		}
		return arrReturn;
	}

	private class LoadListSalePoint extends
			AsyncTask<Void, Void, ArrayList<SalePoint>> {

		@Override
		protected ArrayList<SalePoint> doInBackground(Void... params) {
			if (mStaff != null) {

				return mJobDal.getListStaffOwner(Constant.TABLE_STAFF,
						staffTypeSearch, mStaff.getStaffId(), inputSearch, pos,
						pos + COUNTLOAD);

			} else {
				return null;
			}
		}

		// private Boolean checkExitElement(SalePoint item, ArrayList<SalePoint>
		// arrObject){
		// for(){
		//
		// }
		// }
		@Override
		protected void onPostExecute(final ArrayList<SalePoint> result) {
			if (result != null) {

				for (int i = 0; i < result.size(); i++) {
					Log.e("TAG", "result.get(i) = "
							+ result.get(i).getSalePointName());
					arrayStaff.add(result.get(i));
				}
				Log.e("TAG", "result.size() = " + result.size()
						+ " COUNTLOAD = " + COUNTLOAD);

				if (result.size() < COUNTLOAD) {
					lvListStaff.removeFooterView(footer);
					loadOk = true;
				} else {
					loadOk = false;
					loadmore = false;
				}
				if (arrayStaff.size() == 0) {
					sms11.setVisibility(View.VISIBLE);
				} else {
					sms11.setVisibility(View.GONE);
				}
				pos = arrayStaff.size();
				if (CommonActivity.isNetworkConnected(getActivity())) {
					if (arrayStaff.size() > 0) {
						new GetMoneyBuyWithListAsynctask(getActivity(), result)
								.execute(mStaff.getStaffCode(), "0", "100");
					}

				} else {
					getActivity().runOnUiThread(runnableUdapteAdapter);
					Toast.makeText(getActivity(),
							getResources().getString(R.string.errorNetwork),
							2000).show();
				}

			}

			super.onPostExecute(result);
		}
	}

	private final Runnable runnableUdapteAdapter = new Runnable() {
		@Override
		public void run() {
			mAdapterSalePoint.notifyDataSetChanged();
            loadmore = loadOk;
		}
	};

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		Log.e("SEARC",
				"========================== SCROLL ==================== ");
		int lastitemScreen = firstVisibleItem + visibleItemCount;

		if ((lastitemScreen == totalItemCount) && (!loadmore)
				&& arrayStaff != null && (arrayStaff.size() >= COUNTLOAD)
				&& (totalItemCount > 0)) {
			/*
			 * Log.e("LOG", "SCROLL => visibleItemCount = " + visibleItemCount +
			 * "firstVisibleItem = " + firstVisibleItem + " totalItemCount = " +
			 * totalItemCount + " loadmore = " + loadmore);
			 */

			Log.e("LOG", "SCROLL => arrayStaff.size() = " + arrayStaff.size());
			/*
			 * Log.e("LOG", "visibleItemCount = " + visibleItemCount +
			 * "firstVisibleItem = " + firstVisibleItem + " totalItemCount = " +
			 * totalItemCount);
			 */
			mLoadmoreStaff = new LoadListSalePoint();
			mLoadmoreStaff.execute();
			loadmore = true;

		}

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// Bundle mBundle = new Bundle();
		// mBundle.putParcelable(KEY_STAFF, arrayStaff.get(arg2));
		// FragmentManager manager = getActivity().getFragmentManager();
		// FragmentTransaction mTransaction = manager.beginTransaction();
		// FragmentChannelManager mListMenuManager = new
		// FragmentChannelManager();
		// mListMenuManager.setArguments(mBundle);
		// mTransaction.replace(R.id.fragment_main, mListMenuManager);
		// mTransaction.addToBackStack(null);
		// mTransaction.commit();
		// Toast.makeText(getActivity(), "alo", 0).show();
	}

	@Override
	public void onCheckChange(int position, boolean ischecked) {
		arrayStaff.get(position).setCheck(ischecked);
		hideKeyboard();
		if (ischecked) {
			// arrayStaffSelect.add(mSalePoint);
			mLoadTask = new LoadTask();
			mLoadTask.execute(position);
		} else {
			mAdapterSalePoint.notifyDataSetChanged();
		}
		for (SalePoint item : arrayStaff) {
			for (SalePoint item1 : arrayStaffCache) {
				if (item.equals(item1)) {
					item1.setCheck(item.getCheck());
				}
			}
		}

	}

	private class LoadTask extends
			AsyncTask<Integer, Void, ArrayList<TaskObject>> {
		private int pos;

		@Override
		protected ArrayList<TaskObject> doInBackground(Integer... params) {
			pos = params[0];
			return mJobDal.getTaskJobManager(Define.TABLE_NAME_TASK);
		}

		@Override
		protected void onPostExecute(ArrayList<TaskObject> result) {
			if (result != null) {
				TaskObject tasknull = new TaskObject("", "", getResources()
						.getString(R.string.txtChooseTask), "", "", "", "", "");
				result.add(tasknull);
				arrayStaff.get(pos).setArrayTaskObjects(result);
				mAdapterSalePoint.notifyDataSetChanged();
				// new FillToMap().execute();
			}
			// routing draw
			super.onPostExecute(result);
		}

	}

	private class GetTaskRoadAssigned extends AsyncTask<String, String, String> {
		final BCCSGateway input = new BCCSGateway();
		String response = null;
		String original = null;
		final XmlDomParse parse = new XmlDomParse();
		final Activity activity;
		private String errorMessage = "";
		final ProgressDialog progress;

		private GetTaskRoadAssigned(Activity _activity) {
			Log.e("TAG", "SAP SEP THEO DOANH THU");
			this.activity = _activity;
			this.progress = new ProgressDialog(getActivity());
			this.progress.setMessage(getActivity().getResources().getString(
					R.string.waitting));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		public String createXML(String staffCode, String manageStaffId,
				String staffId) {
			StringBuilder stringBuilder = new StringBuilder(
					"<ws:getTaskRoadAssigned>");

			stringBuilder.append("<channelInput>");
			stringBuilder.append("<token>").append(Session.getToken()).append("</token>");
			stringBuilder.append("<manageStaffCode>").append(staffCode).append("</manageStaffCode>");
			stringBuilder.append("<manageStaffId>").append(manageStaffId).append("</manageStaffId>");
			stringBuilder.append("<staffId>").append(staffId).append("</staffId>");

			stringBuilder.append("</channelInput>");
			stringBuilder.append("</ws:getTaskRoadAssigned>");
			Log.d("createfilexmlSyn", stringBuilder.toString());
			return stringBuilder.toString();
		}

		@Override
		protected String doInBackground(String... arg0) {
			String xml = createXML(arg0[0], arg0[1], arg0[2]);

			CommonOutput output = null;
			try {
				String envelope = input.buildInputGatewayWithRawData2(
						xml.toString(), "mbccs_getTaskRoadAssigned");
				Log.e("TAG", "envelope" + envelope);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getTaskRoadAssigned");

				output = input.parseGWResponse(response);
				original = output.getOriginal();

				Log.e("TAG", "OUT" + original);
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
			} catch (Exception e) {
				errorMessage = output.getDescription();
				e.printStackTrace();
				return Constant.ERROR_CODE;
			}// get data

			return original;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				Log.d("result sort by sale", result);
                switch (result) {
                    case Constant.INVALID_TOKEN2:
                        CommonActivity.BackFromLogin(getActivity(), errorMessage,
                                "");
                        break;
                    case Constant.ERROR_CODE:

                        String title = getString(R.string.app_name);
                        Dialog dialog = CommonActivity.createAlertDialog(
                                getActivity(), errorMessage, title);
                        dialog.show();
                        break;
                    default:
                        try {
                            Document doc = parse.getDomElement(result);
                            NodeList nl = doc.getElementsByTagName("return");

                            NodeList nodelstchild = null;
                            ArrayList<LatLng> arrLatLngs1 = new ArrayList<>();
                            ArrayList<LatLng> arrLatLngs2 = new ArrayList<>();
                            MarkerOptions markerOptions = null;

                            PolylineOptions polyline1 = new PolylineOptions(arrLatLngs1);
                            PolylineOptions polyline2 = new PolylineOptions(arrLatLngs2);
                            for (int i = 0; i < nl.getLength(); i++) {
                                nodelstchild = doc
                                        .getElementsByTagName("lstTaskRoad");
                                Log.e("TAG",
                                        "nodelstchild " + nodelstchild.getLength());

                                for (int j = 0; j < nodelstchild.getLength(); j++) {
                                    Staff mStaffObject = new Staff();
                                    Element e1 = (Element) nodelstchild.item(j);
                                    String objectName = parse.getValue(e1,
                                            "objectName");
                                    String objectId = parse
                                            .getValue(e1, "objectId");
                                    String x = parse.getValue(e1, "x");
                                    String y = parse.getValue(e1, "y");
                                    String progress = parse
                                            .getValue(e1, "progress");
                                    String taskId = parse.getValue(e1, "taskId");
                                    String endDate = parse.getValue(e1, "endDate");
                                    String startDate = parse.getValue(e1,
                                            "createdDate");
                                    startDate = DateTimeUtils
                                            .convertDate(startDate);
                                    String scheduleDateFrom = parse.getValue(e1,
                                            "scheduleDateFrom");
                                    TaskObject taskObject_ = mJobDal
                                            .getTaskJobById(Define.TABLE_NAME_TASK,
                                                    taskId);
                                    Log.e("tag", "NOI DUNG CONG VIEC =  "
                                            + taskObject_.getNameTask());
                                    taskObject_.setFinishDate(scheduleDateFrom);
                                    mStaffObject.setNameStaff(objectName);
                                    mStaffObject.setStaffId(Long
                                            .parseLong(objectId));
                                    mStaffObject.setX(Double.parseDouble(x));
                                    mStaffObject.setY(Double.parseDouble(y));

                                    Log.e("TAG", "TOA DO DIEM BAN X = " + x
                                            + "y = " + y);

                                    if (progress.equals("2")) {
                                        // cong viec da hoan thanh
                                        LatLng latLng = new LatLng(
                                                Double.parseDouble(x),
                                                Double.parseDouble(y));
                                        arrLatLngs1.add(latLng);

                                        markerOptions = new MarkerOptions()
                                                .icon(BitmapFactory.decodeResource(
                                                        getResources(),
                                                        R.drawable.bad))
                                                .position(latLng)
                                                .title(objectName)
                                                .description(
                                                        getResources()
                                                                .getString(
                                                                        R.string.jobContent)
                                                                + ": "
                                                                + taskObject_
                                                                .getContentTask()
                                                                + "\n "
                                                                + getResources()
                                                                .getString(
                                                                        R.string.timeAsign)
                                                                + ": "
                                                                + startDate
                                                                + "\n "
                                                                + getResources()
                                                                .getString(
                                                                        R.string.timeComplate)
                                                                + ": " + endDate);
                                    } else {
                                        LatLng latLng = new LatLng(
                                                Double.parseDouble(x),
                                                Double.parseDouble(y));
                                        arrLatLngs2.add(latLng);

                                        markerOptions = new MarkerOptions()
                                                .icon(BitmapFactory.decodeResource(
                                                        getResources(),
                                                        R.drawable.icon_location))
                                                .position(latLng)
                                                .title(objectName)
                                                .description(
                                                        getResources()
                                                                .getString(
                                                                        R.string.jobContent)
                                                                + ": "
                                                                + taskObject_
                                                                .getContentTask()
                                                                + "\n "
                                                                + getResources()
                                                                .getString(
                                                                        R.string.timeAsign)
                                                                + ": " + startDate);
                                    }

                                    Marker marker = mapView
                                            .addMarker(markerOptions);
                                    marker.setUserData(taskObject_);

                                }

                            }
                            polyline1 = new PolylineOptions(arrLatLngs1);
                            polyline2 = new PolylineOptions(arrLatLngs2);
                            polyline1.strokeColor(Color.RED);
                            polyline1.strokeWidth(2);
                            polyline2.strokeColor(Color.BLUE);
                            polyline2.strokeWidth(2);
                            mapView.addPolyline(polyline1);
                            mapView.addPolyline(polyline2);
                            progress.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                        break;
                }
			}
		}
	}

	private Boolean checkForSubmit() {
		for (SalePoint item : arrayStaff) {
			if (item.getCheck()) {
				return true;
			}
		}
		return false;
	}

	private class CreateTaskAsyn extends AsyncTask<String, String, String> {
		private final String startDate;
		private final String endDate;
		final ProgressDialog progress;
		String errorMessage = "";
		private final String title = getString(R.string.app_name);
		private String msg = "";

		public CreateTaskAsyn(String startDate, String endDate) {
			this.progress = new ProgressDialog(getActivity());
			this.progress.setMessage(getResources()
					.getString(R.string.waitting));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
			this.startDate = startDate;
			this.endDate = endDate;
		}

		@Override
		protected void onPreExecute() {
			arrayListTask.removeAll(arrayListTask);
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {

			try {

				BCCSGateway input = new BCCSGateway();

				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_createTaskRoad");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:createTaskRoad>");
				rawData.append("<taskRoadInput>");
				HashMap<String, String> param = new HashMap<>();
				HashMap<String, String> paramToken = new HashMap<>();

				for (SalePoint item : arrayStaff) {
					boolean ischecked = item.getCheck();
					int posTask = item.getPositionTask();

					if (ischecked && posTask != -1) {
						// Tao request gui len server

						Log.d("TAG", posTask + "");
						TaskObject mObject = item.getArrayTaskObjects().get(
								posTask);
						arrayListTask.add(mObject);

						String date = new SimpleDateFormat("yyyy-MM-dd")
								.format(new Date());

						HashMap<String, String> rawDataItem = new HashMap<>();

						rawDataItem.put("address", "");
						rawDataItem.put("createdDate", "");
						rawDataItem.put("description", ""); // <district>?</district>
						rawDataItem.put("district", "");
						rawDataItem.put("manageStaffId", Session.loginUser
								.getStaffId().toString());

						rawDataItem.put("objectGroupId", "");
						rawDataItem.put("objectId", item.getStaffId()
								.toString());
						rawDataItem.put("objectName", item.getSalePointName());
						rawDataItem.put("oraRowscn", "");
						rawDataItem.put("progress", "");
						rawDataItem.put("province", "");
						rawDataItem.put("realFinishedDate", "");
						rawDataItem.put("scheduleDateFrom", "");
						rawDataItem.put("scheduleDateTo", "");
						rawDataItem.put("scheduleFrom", startDate);// yeu cau
						rawDataItem.put("scheduleTo", endDate);
						rawDataItem.put("staffId", mStaff.getStaffId()
								.toString());
						rawDataItem.put("status", "");
						rawDataItem.put("taskId", mObject.getTastId());
						rawDataItem.put("status", "true");
						rawDataItem.put("x", Float.toString(item.getX()));
						rawDataItem.put("y", Float.toString(item.getY()));

						param.put("lstTaskRoad", input.buildXML(rawDataItem));
						rawData.append(input.buildXML(param));

					}
				}

				paramToken.put("token", Session.getToken());

				rawData.append(input.buildXML(paramToken));
				rawData.append("</taskRoadInput>");
				rawData.append("</ws:createTaskRoad>");

				Log.i("LOG_TAG", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG_TAG", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_createTaskRoad");
				Log.i("LOG", response);
				CommonOutput output = input.parseGWResponse(response);
				if (!output.getError().equals("0")) {
					errorMessage = output.getDescription();
					return Constant.ERROR_CODE;
				}

				String original = output.getOriginal();
				Log.i("LOG", "original = " + original);
				VSAMenuHandler handler = (VSAMenuHandler) CommonActivity
						.parseXMLHandler(new VSAMenuHandler(), original);
				output = handler.getItem();
				if (!output.getErrorCode().equals("0")) {
					errorMessage = output.getDescription();
					return output.getErrorCode();
				}
				return Constant.SUCCESS_CODE;

			} catch (Exception e) {
				e.printStackTrace();
				errorMessage = getResources().getString(R.string.exception)
						+ e.toString();
				return Constant.ERROR_CODE;
			}

		}

		public void resetArrayList() {
			for (SalePoint salePoint : arrayStaff) {
				if (salePoint.getCheck()) {
					salePoint.setCheck(false);
				}

			}
			mAdapterSalePoint.notifyDataSetChanged();
		}

		@Override
		protected void onPostExecute(String result) {
			Log.e("result", "result = " + result);
			Dialog dialog = null;
            switch (result) {
                case Constant.INVALID_TOKEN2:
                    CommonActivity.BackFromLogin(getActivity(), errorMessage, "");
                    break;
                case Constant.SUCCESS_CODE:

                    msg = getResources().getString(R.string.createTaskSuccess);
                    dialog = CommonActivity.createAlertDialog(getActivity(), msg,
                            title);
                    dialog.show();
                    dialogAssignment.dismiss();
                    resetArrayList();
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

	// private class FillToMap extends AsyncTask<Void, Void, Void> {
	//
	// @Override
	// protected Void doInBackground(Void... params) {
	// MarkerOptions markerOptions = null;
	// ArrayList<LatLng> arrLatLngs = new ArrayList<LatLng>();
	// polyline = new PolylineOptions(arrLatLngs);
	// for (SalePoint item : arrayStaff) {
	// boolean ischecked = item.getCheck();
	//
	// if (ischecked) {
	// LatLng latLng = new LatLng(item.getX(), item.getY());
	// arrLatLngs.add(latLng);
	//
	// markerOptions = new MarkerOptions()
	// .icon(BitmapFactory.decodeResource(getResources(),
	// R.drawable.goodstaff)).position(latLng)
	// .title(item.getSalePointName())
	// .description(item.getAddress());
	//
	// mapView.addMarker(markerOptions);
	// }
	// }
	// polyline = new PolylineOptions(arrLatLngs);
	// mapView.addPolyline(polyline);
	// return null;
	// }
	//
	// }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnShowRoute:
			if (showMap) {
				showMap = false;

				mViewFlipper.showNext();
				btnJobAssignment.setVisibility(View.VISIBLE);
				btnShowRoute.setText(getResources().getString(
						R.string.show_route));
				new GetTaskRoadAssigned(getActivity()).execute(
						Session.userName, Session.loginUser.getStaffId()
								.toString(), mStaff.getStaffId().toString());
				// new FillToMap().execute();
			} else {

				// Cap nhat toa do
				if (CommonActivity.isNetworkConnected(getActivity())) {
					showMap = true;
					mViewFlipper.showNext();
					new GetTaskRoadAssigned(getActivity())
							.execute(Session.userName, Session.loginUser
									.getStaffId().toString(), mStaff
									.getStaffId().toString());
					btnJobAssignment.setVisibility(View.GONE);
					btnShowRoute.setText(getResources().getString(
							R.string.viewByList));
				} else {
					String title = getString(R.string.app_name);
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.errorNetwork),
							title);
					dialog.show();
				}
				// new FillToMap().execute();
			}

			break;
		case R.id.btnAssignment:
			// new LoadAssignment().execute();
			if (checkForSubmit()) {
				dialogAssignment = new DialogAssignment(getActivity());
				dialogAssignment.show();
			} else {
				String message = getResources().getString(
						R.string.requireSelectPointSale);
				String title = getString(R.string.app_name);
				Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
						message, title);
				dialog.show();
			}

			break;
		default:
			break;
		}

	}

	@Override
	public void onSelectBox(int positionTask, int posSalePoint) {
		Log.e("TAG", "CHON CONG VIEC ");
		arrayStaff.get(posSalePoint).setPositionTask(positionTask);

	}

	/**
	 * Tao dialog giao viec
	 */
	private class DialogAssignment extends Dialog implements
			android.view.View.OnClickListener {
		private TextView txtDateStart, txtDateEnd;
		private Button btnOk, btnCancel;
		private String toDayS;

		public DialogAssignment(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.dialog_assignment);
			btnOk = (Button) findViewById(R.id.btnDialogAssginmentOk);
			btnCancel = (Button) findViewById(R.id.btnDialogAssginmentCancel);
			btnOk.setOnClickListener(this);
			btnCancel.setOnClickListener(this);
			txtDateStart = (TextView) findViewById(R.id.txtDialogDate);
			txtDateEnd = (TextView) findViewById(R.id.txtDialogDateEnd);
			Calendar calendar = Calendar.getInstance();
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			int month = calendar.get(Calendar.MONTH) + 1;
			int year = calendar.get(Calendar.YEAR);
			toDayS = day + "-" + month + "-" + year;
			txtDateStart.setText(day + "-" + month + "-" + year);
			txtDateEnd.setText(day + "-" + month + "-" + year);
			txtDateStart.setOnClickListener(this);
			txtDateEnd.setOnClickListener(this);

			getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);

		}

		@Override
		public void onDetachedFromWindow() {
			if (assignment != null) {
				assignment.cancel(true);
			}
			super.onDetachedFromWindow();
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnDialogAssginmentOk:
				// Cap nhat toa do
				if (CommonActivity.isNetworkConnected(getActivity())) {
					try {
						SimpleDateFormat sdf = new SimpleDateFormat(
								"dd-MM-yyyy");

						Date selectDateStart = sdf.parse(txtDateStart.getText()
								.toString());
						Date selectDateEnd = sdf.parse(txtDateEnd.getText()
								.toString());
						Date toDay = sdf.parse(toDayS);

						if (toDay.after(selectDateStart)) {

							String title = getString(R.string.app_name);
							Dialog dialog = CommonActivity.createAlertDialog(
									getActivity(),
									getResources().getString(
											R.string.notValidDate), title);
							dialog.show();
						} else {
							if (selectDateEnd.before(selectDateStart)) {
								String title = getString(R.string.app_name);
								Dialog dialog = CommonActivity
										.createAlertDialog(
												getActivity(),
												getResources().getString(
														R.string.notValidDate1),
												title);
								dialog.show();
							} else {
								assignment = new CreateTaskAsyn(txtDateStart
										.getText().toString(), txtDateEnd
										.getText().toString());
								assignment.execute();

							}

						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {
					String title = getString(R.string.app_name);
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.errorNetwork),
							title);
					dialog.show();
				}

				break;
			case R.id.btnDialogAssginmentCancel:
				dismiss();
				break;
			case R.id.txtDialogDate:
				DialogFragment datePickerFragment = new DatePickerFragment(
						txtDateStart);
				datePickerFragment.show(getActivity().getFragmentManager(),
						"datepicker");
				break;
			case R.id.txtDialogDateEnd:
				DialogFragment datePickerFragment1 = new DatePickerFragment(
						txtDateEnd);
				datePickerFragment1.show(getActivity().getFragmentManager(),
						"datepicker");
				break;
			default:
				break;
			}

		}
	}

}
