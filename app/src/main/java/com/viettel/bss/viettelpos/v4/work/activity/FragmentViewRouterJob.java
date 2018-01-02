package com.viettel.bss.viettelpos.v4.work.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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

public class FragmentViewRouterJob extends Fragment implements Define,
		OnScrollListener, OnItemClickListener, OnClickListener {

	private ArrayList<SalePoint> arrayStaff;
	private ListView lvListStaff;
	private AdapterSalePoint mAdapterSalePoint;
	private JobDal mJobDal;

    private Staff mStaff;

	private View mView;
	private ArrayList<TaskObject> arrayListTask;

	private boolean loadmore = false;
    private MapView mapView;
	private ViewFlipper mViewFlipper;
	private Boolean showMap = false;
    private DialogAssignment dialogAssignment = null;
    private ArrayList<SalePoint> arrayStaffCache = new ArrayList<>();
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
			mView = inflater.inflate(R.layout.layout_task_road_map, container,
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

		super.onDestroyView();
	}

	private void Unit(View v) {

        Button chooseDateJob = (Button) v.findViewById(R.id.chooseDateJob);
		chooseDateJob.setOnClickListener(this);
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
		MapConfig.changeSRS(SRSType.SRS_4326);
		new GetTaskRoadAssigned(getActivity()).execute(Session.userName,
				Session.loginUser.getStaffId().toString(), mStaff.getStaffId()
						.toString(), "", "");

	}

	private Runnable runnableUdapteAdapter = new Runnable() {
		@Override
		public void run() {
			mAdapterSalePoint.notifyDataSetChanged();
            loadmore = false;
		}
	};

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		int lastitemScreen = firstVisibleItem + visibleItemCount;
		if ((lastitemScreen == totalItemCount) && (!loadmore)) {
			loadmore = true;
			// mLoadmoreStaff = new LoadListSalePoint();
			// mLoadmoreStaff.execute();

		}

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

	}

	private class GetTaskRoadAssigned extends AsyncTask<String, Void, String> {
		final BCCSGateway input = new BCCSGateway();
		String response = null;
		String original = null;
		final XmlDomParse parse = new XmlDomParse();
		final Activity activity;
		final ProgressDialog progress;
		private String errorMessage = "";
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
				String staffId, String startDate, String endDate) {
			StringBuilder stringBuilder = new StringBuilder(
					"<ws:getTaskRoadAssigned>");

			stringBuilder.append("<channelInput>");
			stringBuilder.append("<token>").append(Session.getToken()).append("</token>");
			stringBuilder.append("<manageStaffCode>").append(staffCode).append("</manageStaffCode>");
			stringBuilder.append("<manageStaffId>").append(manageStaffId).append("</manageStaffId>");
			stringBuilder.append("<staffId>").append(staffId).append("</staffId>");
			stringBuilder.append("<endDate>").append(endDate).append("</endDate>");
			stringBuilder.append("<startDate>").append(startDate).append("</startDate>");
			stringBuilder.append("<type>0</type>");
			stringBuilder.append("</channelInput>");
			stringBuilder.append("</ws:getTaskRoadAssigned>");
			Log.d("createfilexmlSyn", stringBuilder.toString());
			return stringBuilder.toString();
		}

		@Override
		protected String doInBackground(String... arg0) {
			String xml = createXML(arg0[0], arg0[1], arg0[2], arg0[3], arg0[4]);

			CommonOutput output = null;
			
			try {
				String envelope = input.buildInputGatewayWithRawData2(
						xml.toString(), "mbccs_getTaskRoadAssigned");
				Log.e("TAG", "envelope" + envelope);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,getActivity(),"mbccs_getTaskRoadAssigned");

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
			}// get data
			return original;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				Log.d("result sort by sale", result);
                switch (result) {
                    case Constant.INVALID_TOKEN2:
                        CommonActivity.BackFromLogin(getActivity(), errorMessage, ";work_management;");
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
                                    String scheduleDateFrom = parse.getValue(e1,
                                            "scheduleDateFrom");
                                    String endDate = parse.getValue(e1, "endDate");
                                    String startDate = parse.getValue(e1,
                                            "createdDate");
                                    startDate = DateTimeUtils
                                            .convertDate(startDate);
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
                                                        R.drawable.goodstaff))
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
                            if (dialogAssignment != null) {
                                dialogAssignment.dismiss();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnShowRoute:

			break;
		case R.id.chooseDateJob:
			dialogAssignment = new DialogAssignment(getActivity());
			dialogAssignment.show();
			break;
		default:
			break;
		}

	}

	/**
	 * Tao dialog giao viec
	 */
	private class DialogAssignment extends Dialog implements
			android.view.View.OnClickListener {
		private TextView txtDateStart, txtDateEnd;
		private Button btnOk, btnCancel;
		private String toDayS;
		private final Context context;

		public DialogAssignment(Context context) {
			super(context);
			this.context = context;
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.dialog_assignment);
			TextView txtTitleOnlineDialog = (TextView) findViewById(R.id.txtTitleOnlineDialog);
			txtTitleOnlineDialog.setText(context.getResources().getString(
					R.string.selectIn7day));

			btnOk = (Button) findViewById(R.id.btnDialogAssginmentOk);
			btnCancel = (Button) findViewById(R.id.btnDialogAssginmentCancel);
			btnOk.setOnClickListener(this);
			btnCancel.setOnClickListener(this);
			txtDateStart = (TextView) findViewById(R.id.txtDialogDate);
			txtDateEnd = (TextView) findViewById(R.id.txtDialogDateEnd);
			Calendar calendar = Calendar.getInstance();
			Calendar calendarBefor = Calendar.getInstance();
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			int month = calendar.get(Calendar.MONTH) + 1;
			int year = calendar.get(Calendar.YEAR);

			// int dayBefor = calendar.get(Calendar.DAY_OF_MONTH) - 7;
			// calendarBefor.set(Calendar.DAY_OF_MONTH, dayBefor);
			// int monthBefor = calendarBefor.get(Calendar.MONTH);
			// int currentDay=cal.get(Calendar.DAY_OF_MONTH);
			// Set the date to 2 days ago
			// cal.set(Calendar.DAY_OF_MONTH, currentDay-2);
			calendarBefor.add(Calendar.DAY_OF_YEAR, -7);
			toDayS = day + "-" + month + "-" + year;
			txtDateStart.setText(calendarBefor.get(Calendar.DAY_OF_MONTH) + "-"
					+ (calendarBefor.get(Calendar.MONTH) + 1) + "-"
					+ calendarBefor.get(Calendar.YEAR));
			txtDateEnd.setText(day + "-" + month + "-" + year);
			txtDateStart.setOnClickListener(this);
			txtDateEnd.setOnClickListener(this);
		}

        public long calculateDays(Date dateEarly, Date dateLater) {
			return (dateLater.getTime() - dateEarly.getTime())
					/ (24 * 60 * 60 * 1000);
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnDialogAssginmentOk:
				
				// Cap nhat toa do
				if (CommonActivity.isNetworkConnected(getActivity())) {
					try {
						SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

						Date selectDateStart = sdf.parse(txtDateStart.getText()
								.toString());
						Date selectDateEnd = sdf.parse(txtDateEnd.getText()
								.toString());
						Date toDay = sdf.parse(toDayS);
						Log.e("e",
								"Khong cach hai ngay = "
										+ calculateDays(selectDateStart,
												selectDateEnd));
						if (selectDateEnd.before(selectDateStart)) {
							String title = getString(R.string.app_name);
							Dialog dialog = CommonActivity.createAlertDialog(
									getActivity(),
									getResources()
											.getString(R.string.notValidDate1),
									title);
							dialog.show();
							dismiss();
						} else {
							if (calculateDays(selectDateStart, selectDateEnd) > 7) {
								String title = getString(R.string.app_name);
								Dialog dialog = CommonActivity.createAlertDialog(
										getActivity(),
										getResources().getString(
												R.string.notValidDate2), title);
								dismiss();
								dialog.show();
							} else {
							
								new GetTaskRoadAssigned(getActivity()).execute(
										Session.userName, Session.loginUser
												.getStaffId().toString(), mStaff
												.getStaffId().toString(),
										txtDateStart.getText().toString(),
										txtDateEnd.getText().toString());
							}

						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					String title = getString(R.string.app_name);
					Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
							getResources().getString(R.string.errorNetwork), title);
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
