package com.viettel.bss.viettelpos.v4.channel.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.channel.adapter.AdapterStaff;
import com.viettel.bss.viettelpos.v4.channel.adapter.ChannelTypeAdapter;
import com.viettel.bss.viettelpos.v4.channel.dal.ChannelDAL;
import com.viettel.bss.viettelpos.v4.channel.dal.ChannelTypeDAL;
import com.viettel.bss.viettelpos.v4.channel.object.ChannelTypeOJ;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.handler.VSAMenuHandler;
import com.viettel.bss.viettelpos.v4.login.object.Staff;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.work.activity.FragmentManagePolicy;
import com.viettel.maps.MapView;
import com.viettel.maps.base.LatLng;
import com.viettel.maps.layers.MapLayer;
import com.viettel.maps.objects.InfoWindow;
import com.viettel.maps.objects.InfoWindowOptions;
import com.viettel.maps.objects.MapObject;
import com.viettel.maps.objects.Marker;
import com.viettel.maps.objects.MarkerOptions;
import com.viettel.maps.util.MapConfig;
import com.viettel.maps.util.MapConfig.SRSType;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class FragmentListChannel_work extends Fragment implements Define,
		OnScrollListener, OnItemClickListener, TextWatcher, OnClickListener {

	private ArrayList<Staff> arrayStaff = new ArrayList<>();
	private ArrayList<Staff> arrayStaffCacheClient = new ArrayList<>();
	private ArrayList<Staff> arrayStaffCacheServer = new ArrayList<>();
	private String selectType = "";
	private ArrayList<ChannelTypeOJ> arrChannelType;
	private ListView lvListStaff;

    private AdapterStaff mAdapterStaff;
    private boolean loadOk = false;

	private String inputSearchName = null;
	private LoadStaffByChannelType mLoadStaffByChannelType;
	private View footer;
	private EditText edtSearchChannel;
	// private Boolean flagSearch = false;
	private View mView;
	private Long staffType = 0L;

    private Button btnMapAndList;
    private Location myLocation;

	private ViewFlipper mViewFlipper;
	private MapView mapView;
	private LoadmoreStaff loadmoreStaff;
	private FillToMap fillToMap;
	private static String userName;
	private boolean ktMap = false;
    private TextView smsListStaff;
	private final String TAG = "Map test";
	private Staff staffSelect = null;
	private final String staffCodeMarker = null;
	private Context context;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// Log.d("TAG", "onActivityCreated ---- FragmentListChannel");
        ActionBar mActionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();

		mActionBar.setDisplayShowHomeEnabled(true);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowCustomEnabled(true);

		mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		mActionBar.setCustomView(R.layout.layout_actionbar_channel);

		mActionBar.setDisplayShowTitleEnabled(true);

        Button btnHome = (Button) mActionBar.getCustomView()
                .findViewById(R.id.btnHome);
		btnHome.setVisibility(View.VISIBLE);
		btnHome.setOnClickListener(this);
        Button btShortByCare = (Button) mActionBar.getCustomView().findViewById(
                R.id.btnSortByCareAsc);
        Button btShortByCareDesc = (Button) mActionBar.getCustomView().findViewById(
                R.id.btnSortByCareDesc);
		btShortByCareDesc.setVisibility(View.VISIBLE);
		btShortByCare.setVisibility(View.VISIBLE);
		btnMapAndList = (Button) mActionBar.getCustomView().findViewById(
				R.id.btnMap);
		btnMapAndList.setVisibility(View.VISIBLE);
		mapView = (MapView) getActivity().findViewById(R.id.idMapViewStaff);

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
				staffSelect = (Staff) marker.getUserData();

				return true;
			}

		});

		mapView.setInfoWindowClickListener(new MapLayer.OnClickListener() {

			@Override
			public boolean onClick(Point point, LatLng pos, MapObject obj) {

				InfoWindow info = (InfoWindow) obj;
				Log.i("tag", "click info window");

				Log.i("TAG", " setMarkerClickListener staffCode :: "
						+ staffSelect.getStaffCode());

				Bundle mBundle = new Bundle();
				mBundle.putSerializable(KEY_STAFF, staffSelect);

				FragmentChanelFunction mListMenuManager = new FragmentChanelFunction();
				mListMenuManager.setArguments(mBundle);
				ReplaceFragment.replaceFragment(getActivity(),
						mListMenuManager, false);
				return true;
			}

		});
		// mapView.
		btnMapAndList.setOnClickListener(this);
		btShortByCare.setOnClickListener(this);
		btShortByCareDesc.setOnClickListener(this);
		LinearLayout relaBackHome = (LinearLayout) mActionBar.getCustomView()
				.findViewById(R.id.relaBackHome);
		relaBackHome.setOnClickListener(this);
		TextView txtNameActionBar = (TextView) mActionBar.getCustomView()
				.findViewById(R.id.txtNameActionbar);
		txtNameActionBar.setText(getResources().getString(
				R.string.job_manager_5));
		TextView txtAddressActionBar = (TextView) mActionBar.getCustomView()
				.findViewById(R.id.txtAddressActionbar);
		txtAddressActionBar.setVisibility(View.GONE);

		userName = Session.userName;

		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		getActivity().getMenuInflater().inflate(R.menu.menu_list_channel, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d("TAG", "onCreateView FragmentListChannel");

		// if (mView == null) {

		if (arrayStaff.size() == 0) {
			Log.e("TAG", " KHOI TAO LAI GIAO DIEN " + arrayStaff.size());
			mView = inflater.inflate(R.layout.layout_list_staff, container,
					false);
			Unit(mView);
			// loadmoreStaff = new LoadmoreStaff();
			fillToMap = new FillToMap();
			// if (myLocation != null) {
			//
			// fillToMap.execute();
			// }
		}
		// }

		return mView;
	}

	@Override
	public void onStart() {
		Log.d("TAG", "onStart FragmentListChannel");
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onResume() {
		Log.d("TAG", "onResume FragmentListChannel");
		// TODO Auto-generated method stub
		if (mapView != null) {
			mapView.refresh();
		}

		if (arrayStaff.size() > 0) {
			arrayStaff.removeAll(arrayStaff);
		}
		super.onResume();
	}

	@Override
	public void onPause() {
		Log.d("TAG", "onPause FragmentListChannel");
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onStop() {
		Log.d("TAG", "onStop FragmentListChannel");
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d("TAG", "onCreate FragmentListChannel");
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		Log.d("TAG", "onDestroy FragmentListChannel");
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		Log.d("TAG", "onDetach FragmentListChannel");

		// TODO Auto-generated method stub
		super.onDetach();
	}

	@Override
	public void onDestroyView() {
		Log.d("TAG", "onDestroyView FragmentListChannel");
		if (loadmoreStaff != null)
			loadmoreStaff.cancel(true);
		if (mLoadStaffByChannelType != null)
			mLoadStaffByChannelType.cancel(true);
		try {
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.onDestroyView();
	}

	@SuppressLint("InflateParams")
    private void Unit(View v) {
		try {
			mViewFlipper = (ViewFlipper) v.findViewById(R.id.vfListAndMap);
			context = getActivity();
			Log.e("TAG", "TOA DO X" + myLocation.getLatitude());
			Log.e("TAG", "TOA DO Y" + myLocation.getLongitude());
			lvListStaff = (ListView) v.findViewById(R.id.lv_staff);
			arrayStaff = new ArrayList<>();
			// smsListStaff = (TextView) v.findViewById(R.id.smsListStaff);
			mAdapterStaff = new AdapterStaff(arrayStaff, getActivity(),AdapterStaff.TYPE_LOCATION);

			LayoutInflater inflater = getActivity().getLayoutInflater();
			footer = inflater.inflate(R.layout.footer_layout, null, false);
			// lvListStaff.addFooterView(footer, null, false);
			lvListStaff.setAdapter(mAdapterStaff);
			// lvListStaff.setOnScrollListener(this);
			lvListStaff.setOnItemClickListener(this);

			/*
			 * Khoi tao spin loc kenh theo loai kenh
			 */
			ChannelTypeDAL channelTypeDAL = new ChannelTypeDAL(getActivity());

            Spinner spChannelType = (Spinner) v.findViewById(R.id.sp_list_channel_type);
			arrChannelType = new ArrayList<>();
			arrChannelType = channelTypeDAL.getTasksToArray();
			channelTypeDAL.close();
			ChannelTypeOJ ctypeDefault = new ChannelTypeOJ(0L, getResources()
					.getString(R.string.spinner_show));
			arrChannelType.add(0, ctypeDefault);
			ChannelTypeAdapter adChannelType = new ChannelTypeAdapter(
					getActivity(), arrChannelType);
			spChannelType.setAdapter(adChannelType);

			spChannelType
					.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> adapterView,
								View view, int position, long id) {
							// Here you get the current item (a User object)
							// that is
							// selected by its position
							ChannelTypeOJ channelTypeOJ = arrChannelType
									.get(position);
							// Here you can do the action you want to...

							staffType = channelTypeOJ.getType_channel_id();
							Log.e(tag, staffType + "");
							mLoadStaffByChannelType = new LoadStaffByChannelType();
							mLoadStaffByChannelType.execute();
						}

						@Override
						public void onNothingSelected(AdapterView<?> adapter) {

						}
					});
			/**
			 * Search
			 */
			edtSearchChannel = (EditText) v
					.findViewById(R.id.autoSearchStaffName);
			inputSearchName = edtSearchChannel.getText().toString();
			edtSearchChannel.addTextChangedListener(new TextWatcher() {
				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					inputSearchName = edtSearchChannel.getText().toString();
					Log.i("Inputtex", "Input texx :: " + inputSearchName);
					// channelDAL.open();

					mLoadStaffByChannelType = new LoadStaffByChannelType();
					mLoadStaffByChannelType.execute();
					// try {
					// channelDAL.close();
					// } catch (Exception e) {
					// // TODO Auto-generated catch block
					// e.printStackTrace();
					// }

				}

			});

			/**
			 * Khoi tao spin sap xep kenh
			 */
            Spinner spSortBy = (Spinner) v.findViewById(R.id.sp_fillter);
			List<String> list = new ArrayList<>();

			// list.add(getResources().getString(R.string.orderByVisitDesc));
			list.add(getResources().getString(R.string.spinner_show));
			list.add(getResources().getString(R.string.orderByVisitDesc));
			list.add(getResources().getString(R.string.orderByVisitAsc));
			list.add(getResources().getString(R.string.orderBySaleDesc));
			list.add(getResources().getString(R.string.orderBySaleAsc));
			list.add(getResources().getString(R.string.orderByLocation));
			// AdapterFilterChannel adSort = new AdapterFilterChannel(
			// getActivity(), list);

			// spSortBy.setAdapter(adSort);
			spSortBy.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> adapterView,
						View view, int position, long id) {

					switch (position) {
					case 0:
						Log.e(TAG, "Tat ca");

						mLoadStaffByChannelType = new LoadStaffByChannelType();
						mLoadStaffByChannelType.execute();
						break;
					case 1:
						// cham soc nhieu
						selectType = Constant.BY_VISIT;
						new SortAsynctask(getActivity()).execute(
								Session.userName, "0", "10");
						break;
					case 2:
						// Cham soc it

						selectType = Constant.BY_VISIT;
						new SortAsynctask(getActivity()).execute(
								Session.userName, "1", "10");
						break;
					case 3:
						// Ban hang tot nhat
						Log.e(TAG, "CHON 3");
						selectType = Constant.BY_SALE;
						new SortBySaleAsynctask(getActivity()).execute(
								Session.userName, "0", "20"); // false
						break;
					case 4:
						// ban hang it nhat
						Log.e(TAG, "CHON 3");
						selectType = Constant.BY_SALE;
						new SortBySaleAsynctask(getActivity()).execute(
								Session.userName, "1", "10");// true Tham so
						break;
					case 5:
						// gan nhat
						Log.e(TAG, "CHON 4");
						mLoadStaffByChannelType = new LoadStaffByChannelType();
						mLoadStaffByChannelType.execute();
						break;
					default:
						break;
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> adapter) {
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * Do du lieu vao list view
		 */

	}

	private final String tag = "list channel work";

	private class LoadmoreStaff extends AsyncTask<Void, Void, ArrayList<Staff>> {
		@Override
		protected void onPreExecute() {

			super.onPreExecute();
		}

		@Override
		protected ArrayList<Staff> doInBackground(Void... params) {

			return arrayStaff;

		}

		@Override
		protected void onPostExecute(final ArrayList<Staff> result) {

			try {
				if (result != null && result.size() > 0) {

					Staff mStaff = null;

					for (int i = 0; i < result.size(); i++) {
						mStaff = result.get(i);
						arrayStaff.add(mStaff);
						if (mStaff.getX() > 0 && mStaff.getY() > 0) {
							Log.e("TAG",
									mStaff.getNameStaff() + "mStaffX="
											+ mStaff.getX() + " mStaffY="
											+ mStaff.getY());
							MarkerOptions markerOptions = new MarkerOptions()
									.icon(BitmapFactory.decodeResource(
											getResources(),
											R.drawable.location_pin))
									.position(
											new LatLng(mStaff.getX(), mStaff
													.getY()))
									.title(mStaff.getNameStaff())
									.description(mStaff.getAddressStaff());
							Marker marker = mapView.addMarker(markerOptions);
							marker.setUserData(mStaff);
						}
					}
					lvListStaff.removeFooterView(footer);
					loadOk = true;

					getActivity().runOnUiThread(runnableUdapteAdapter);
				} else {
					arrayStaff = null;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			super.onPostExecute(result);
		}
	}

	private final boolean finished = true;
	private final int taskTypeId = Constant.POLICY_TASK_TYPE;

	private class FillToMap extends AsyncTask<Void, Void, ArrayList<Staff>> {
		@Override
		protected void onPreExecute() {

			super.onPreExecute();
		}

		@Override
		protected ArrayList<Staff> doInBackground(Void... params) {
			if (arrayStaff.size() > 0) {
				return arrayStaff;

			} else {
				ChannelDAL channelDAL = new ChannelDAL(context);
				try {
					return channelDAL.getTasksToArray_(staffType,
							inputSearchName, myLocation, userName, taskTypeId,
							finished);
				} catch (Exception e) {
					return new ArrayList<>();
				} finally {
					channelDAL.close();
				}

			}
		}

		@Override
		protected void onPostExecute(final ArrayList<Staff> result) {

			try {
				if (result != null) {
					MarkerOptions markerOptions = null;
					Staff mStaff = null;
					Long max = 0L;
					Long min = 1000000000L;
					Long mid = 0L;
					Long offSet = 0L;
					if (selectType.equals(Constant.BY_VISIT)) {
						for (Staff item : result) {
							Log.e("TAG", " NUMVISIT = " + item.getVisitNum());
							if (item.getVisitNum() > max) {
								max = item.getVisitNum();
							}
							if (item.getVisitNum() < min) {
								min = item.getVisitNum();
							}

						}
					} else if (selectType.equals(Constant.BY_SALE)) {
						for (Staff item : result) {
							Log.e("TAG",
									" NUMVISIT = " + item.getTotalRevenue());
							if (item.getTotalRevenue() > max) {
								max = item.getTotalRevenue();
							}
							if (item.getTotalRevenue() < min) {
								min = item.getTotalRevenue();
							}

						}
					}

					mid = (min + max) / 2;
					offSet = max * 10 / 100;
					Log.e("TAG", max + " NUMVISIT = " + min + "MIDLE " + mid
							+ "offSet = " + offSet);

					for (int i = 0; i < result.size(); i++) {
						mStaff = result.get(i);
						String img = "";
						if (mStaff.getX() > 0 && mStaff.getY() > 0) {
							Log.e("TAG",
									mStaff.getNameStaff() + "mStaffX="
											+ mStaff.getX() + " mStaffY="
											+ mStaff.getY());
							// if(selectType.equals(Constant.BY_SALE)){
                            switch (selectType) {
                                case Constant.BY_VISIT:
                                    if (mStaff.getVisitNum() > (mid + offSet)
                                            && mStaff.getVisitNum() <= max) {

                                        markerOptions = new MarkerOptions()
                                                .icon(BitmapFactory.decodeResource(
                                                        getResources(),
                                                        R.drawable.goodstaff))
                                                .position(
                                                        new LatLng(mStaff.getX(),
                                                                mStaff.getY()))
                                                .title(mStaff.getNameStaff())
                                                .description(
                                                        mStaff.getAddressStaff());
                                    } else if (mStaff.getVisitNum() < (mid + offSet)
                                            && mStaff.getVisitNum() <= min) {

                                        markerOptions = new MarkerOptions()
                                                .icon(BitmapFactory.decodeResource(
                                                        getResources(),
                                                        R.drawable.bad))
                                                .position(
                                                        new LatLng(mStaff.getX(),
                                                                mStaff.getY()))
                                                .title(mStaff.getNameStaff())
                                                .description(
                                                        mStaff.getAddressStaff());
                                    } else if (mStaff.getVisitNum() < (mid + offSet)
                                            && mStaff.getVisitNum() > (mid - offSet)) {

                                        markerOptions = new MarkerOptions()
                                                .icon(BitmapFactory.decodeResource(
                                                        getResources(),
                                                        R.drawable.nomal))
                                                .position(
                                                        new LatLng(mStaff.getX(),
                                                                mStaff.getY()))
                                                .title(mStaff.getNameStaff())
                                                .description(
                                                        mStaff.getAddressStaff());
                                    }
                                    break;
                                case Constant.BY_SALE:

                                    if (mStaff.getTotalRevenue() > (mid + offSet)
                                            && mStaff.getTotalRevenue() <= max) {
                                        markerOptions = new MarkerOptions()
                                                .icon(BitmapFactory.decodeResource(
                                                        getResources(),
                                                        R.drawable.goodstaff))
                                                .position(
                                                        new LatLng(mStaff.getX(),
                                                                mStaff.getY()))
                                                .title(mStaff.getNameStaff())
                                                .description(
                                                        mStaff.getAddressStaff());
                                        Log.e("TAG",
                                                "BAN HANG TOT NHAT"
                                                        + mStaff.getNameStaff());
                                    } else if (mStaff.getTotalRevenue() < (mid + offSet)
                                            && mStaff.getTotalRevenue() <= min) {

                                        markerOptions = new MarkerOptions()
                                                .icon(BitmapFactory.decodeResource(
                                                        getResources(),
                                                        R.drawable.bad))
                                                .position(
                                                        new LatLng(mStaff.getX(),
                                                                mStaff.getY()))
                                                .title(mStaff.getNameStaff())
                                                .description(
                                                        mStaff.getAddressStaff());
                                        Log.e("TAG",
                                                "BAN HANG kem nhat"
                                                        + mStaff.getNameStaff());
                                    } else if (mStaff.getTotalRevenue() < (mid + offSet)
                                            && mStaff.getTotalRevenue() > (mid - offSet)) {

                                        markerOptions = new MarkerOptions()
                                                .icon(BitmapFactory.decodeResource(
                                                        getResources(),
                                                        R.drawable.nomal))
                                                .position(
                                                        new LatLng(mStaff.getX(),
                                                                mStaff.getY()))
                                                .title(mStaff.getNameStaff())
                                                .description(
                                                        mStaff.getAddressStaff());
                                        Log.e("TAG",
                                                "BAN HANG trung binh"
                                                        + mStaff.getNameStaff());
                                    }

                                    break;
                                default:
                                    markerOptions = new MarkerOptions()
                                            .icon(BitmapFactory.decodeResource(
                                                    getResources(),
                                                    R.drawable.location_pin))
                                            .position(
                                                    new LatLng(mStaff.getX(),
                                                            mStaff.getY()))
                                            .title(mStaff.getNameStaff())
                                            .description(mStaff.getAddressStaff());
                                    break;
                            }
							Marker marker = mapView.addMarker(markerOptions);
							marker.setUserData(mStaff);

						}
					}
					lvListStaff.removeFooterView(footer);
					loadOk = true;

					getActivity().runOnUiThread(runnableUdapteAdapter);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			super.onPostExecute(result);
		}
	}

	/**
	 * Fill channel type
	 */
	private class LoadStaffByChannelType extends
			AsyncTask<Void, Void, ArrayList<Staff>> {

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
		}

		@Override
		protected ArrayList<Staff> doInBackground(Void... params) {
			// totalRow = channelDAL.queryCount(TABLE_NAME_STAFF, staff_type,
			// inputSearchName);
			// return channelDAL.findStaffByTaffType(TABLE_NAME_STAFF, 0, 10,
			// staffType, inputSearchName);
			ChannelDAL channelDAL = new ChannelDAL(context);
			ArrayList<Staff> result = new ArrayList<>();
			try {
				result = channelDAL.getTasksToArray_(staffType, inputSearchName,
						myLocation, userName, taskTypeId, finished);
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				channelDAL.close();
			}
			return result;

		}

		@Override
		protected void onPostExecute(final ArrayList<Staff> result) {
			try {
				if (result != null) {

					if (arrayStaff.size() > 0) {
						arrayStaff.removeAll(arrayStaff);
					}

					// arrayStaff = result;
					// Collections.addAll(arrayStaff, result);
					for (int i = 0; i < result.size(); i++) {

						Staff staff = result.get(i);
						Log.i("TAG", "::" + staff.getNameStaff());
						arrayStaff.add(staff);
					}

					// getActivity().runOnUiThread(runnableUdapteAdapter);
					// mAdapterStaff.notifyDataSetChanged();
					// mAdapterStaff = new AdapterStaff(arrayStaff,
					// getActivity());
					arrayStaffCacheClient = arrayStaff;
					if (arrayStaffCacheServer.size() > 0) {
						arrayStaff = intersect(arrayStaffCacheServer,
								arrayStaffCacheClient);
					}
					// mAdapterStaff.notifyDataSetChanged();
					mAdapterStaff = new AdapterStaff(arrayStaff, getActivity(),AdapterStaff.TYPE_LOCATION);
					lvListStaff.setAdapter(mAdapterStaff);

				} else {
					Log.i("TAG", "khong tim duoc du lieu");
				}

				super.onPostExecute(result);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * 
	 */
	private final Runnable runnableUdapteAdapter = new Runnable() {
		@Override
		public void run() {
			if (mAdapterStaff != null) {
				mAdapterStaff.notifyDataSetChanged();
                boolean loadmore = false;
                loadmore = loadOk;
			}
		}
	};

	// private Runnable runnableUdapteAdapterLoadByType = new Runnable() {
	// @Override
	// public void run() {
	// mAdapterStaff.notifyDataSetChanged();
	//
	// }
	// };
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// Log.i("onScroll", "onScroll");
		// int lastitemScreen = firstVisibleItem + visibleItemCount;
		// Log.i("lastitemScreen", "lastitemScreen = " + lastitemScreen);
		// Log.i("totalItemCount", "totalItemCount = " + totalItemCount);
		// Log.i("loadmore", "loadmore = " + loadmore);
		// if ((lastitemScreen == totalItemCount) && (loadmore == false)) {
		// Log.i("onScroll", "Star load database");
		// loadmore = true;
		//
		// }

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Bundle mBundle = new Bundle();
		mBundle.putSerializable(KEY_STAFF, arrayStaff.get(arg2));
		mBundle.putSerializable("FLAG", false);
		FragmentManagePolicy mListMenuManager = new FragmentManagePolicy();
		// ReplaceFragment.replaceFragmentSlidingMenu(getActivity(),
		// new FragmentListChannel_work(), true);
		mListMenuManager.setArguments(mBundle);
		ReplaceFragment.replaceFragment(getActivity(), mListMenuManager, false);
		CommonActivity.hideKeyboard(edtSearchChannel, getActivity());
		// if (FragmentJobManager.policy) {
		// // FragmentChanelFunction mListMenuManager = new
		// // FragmentChanelFunction();
		// FragmentManagePolicy mListMenuManager = new FragmentManagePolicy();
		//
		// mListMenuManager.setArguments(mBundle);
		// ReplaceFragment.replaceFragment(getActivity(), mListMenuManager,
		// false);
		// // FragmentJobManager.policy = false;
		// } else {
		//
		// FragmentChanelFunction mListMenuManager = new
		// FragmentChanelFunction();
		// mListMenuManager.setArguments(mBundle);
		// ReplaceFragment.replaceFragment(getActivity(), mListMenuManager,
		// false);
		// }

	}

	public void onItemClick_(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		Bundle mBundle = new Bundle();
		mBundle.putSerializable(KEY_STAFF, arrayStaff.get(arg2));
		mBundle.putSerializable("FLAG", false);
		FragmentChanelFunction mListMenuManager = new FragmentChanelFunction();
		mListMenuManager.setArguments(mBundle);
		ReplaceFragment.replaceFragment(getActivity(), mListMenuManager, false);
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnHome:
			CommonActivity.callphone(getActivity(), Constant.phoneNumber);
//			FragmentMenuHome fragmentMenuHome = new FragmentMenuHome();
//			ReplaceFragment.replaceFragment(getActivity(), fragmentMenuHome,
//					true);
			break;
		case R.id.btnMap:
			showTapMap();
			break;
		case R.id.relaBackHome:
			getActivity().onBackPressed();

			break;
		case R.id.btnSortByCareAsc:
			Log.e("TAG", "Session.userName1 = " + Session.userName);

			new SortAsynctask(getActivity()).execute(Session.userName, "1",
					"10");
			break;
		case R.id.btnSortByCareDesc:
			Log.e("TAG", "Session.userName2 = " + Session.userName);
			new SortAsynctask(getActivity()).execute(Session.userName, "0",
					"10");
			break;
		default:
			break;
		}

	}

	/**
	 * Fill channel type
	 */
	private class SortAsynctask extends AsyncTask<String, Void, String> {
		final BCCSGateway input = new BCCSGateway();
		String response = null;
		String original = null;
		final XmlDomParse parse = new XmlDomParse();
		final Activity activity;
		String sms = "";
		String errorMessage = "";
		final ProgressDialog progress;

		private SortAsynctask(Activity _activity) {
			Log.e("TAG", "SAP SEP THEO CHAM SOC");
			this.activity = _activity;
			this.progress = new ProgressDialog(getActivity());
			this.progress.setMessage(getActivity().getResources().getString(
					R.string.waitting));
			this.progress.setCancelable(false);
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected String doInBackground(String... arg0) {
			String xml = createXMLGetListStaff(arg0[0], arg0[1], arg0[2]);

			CommonOutput output = null;
			try {
				String envelope = input.buildInputGatewayWithRawData2(
						xml.toString(), "mbccs_viewFrequencyVisited");

				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,getActivity(),"mbccs_viewFrequencyVisited");

				output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.e("SMS:original", "" + original.toString());
				Log.e("SMS", "" + output.getError());
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
					return Constant.ERROR_CODE;
				}
				return original;
			} catch (Exception e) {
				e.printStackTrace();
				errorMessage = getResources().getString(R.string.exception)
						+ e.toString();
				return Constant.ERROR_CODE;
			}// get data

		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				if (result.equals(Constant.ERROR_CODE)) {
					String title = getString(R.string.app_name);
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), errorMessage, title);
					dialog.show();
				} else {
					try {
						Document doc = parse.getDomElement(result);
						NodeList nl = doc.getElementsByTagName("return");

						ArrayList<Staff> arrayStaffWs = new ArrayList<>();
						NodeList nodelstchild = null;

						ArrayList<Staff> resultArr = new ArrayList<>();
						for (int i = 0; i < nl.getLength(); i++) {
							if (arrayStaffWs.size() > 0)
								arrayStaffWs.removeAll(arrayStaffWs);
							nodelstchild = doc.getElementsByTagName("lstStaff");
							// if (arrayStaff.size() > 0) {
							// arrayStaff.removeAll(arrayStaff);
							// }

							for (int j = 0; j < nodelstchild.getLength(); j++) {
								Staff mStaffObject = new Staff();
								Element e1 = (Element) nodelstchild.item(j);
								String name = parse.getValue(e1, "name");
								String staffCode = parse.getValue(e1,
										"staffCode");
								// Log.e("TAG", "arrayStaff " + name);
								if (!staffCode.equals(userName)) {
									String staffId = parse.getValue(e1,
											"staffId");
									String visitNum = parse.getValue(e1,
											"visitNum");
									String x = parse.getValue(e1, "x");
									String y = parse.getValue(e1, "y");
									String imgUrl = parse
											.getValue(e1, "imgUrl");
									String imgPath = parse.getValue(e1,
											"imgPath");
									mStaffObject.setNameStaff(name);
									mStaffObject.setVisitNum(Long
											.parseLong(visitNum));
									Log.e("TAG", "NUM VISIT" + visitNum);
									mStaffObject.setStaffId(Long
											.parseLong(staffId));
									mStaffObject.setStaffCode(staffCode);
									mStaffObject.setX(Double.parseDouble(x));
									mStaffObject.setY(Double.parseDouble(y));
									Location locationStaff = new Location(
											"location Satff");
									if (x != null) {
										locationStaff.setLatitude(Double
												.parseDouble(x));
									} else {
										locationStaff.setLatitude(0);
									}
									if (y != null) {
										locationStaff.setLongitude(Double
												.parseDouble(y));
									} else {
										locationStaff.setLongitude(0);
									}

									float distance = 0;
									distance = myLocation
											.distanceTo(locationStaff);
									mStaffObject.setDistance(distance);
									arrayStaffWs.add(mStaffObject);
									// arrayStaff.add(mStaffObject);
								}
							}
							arrayStaffCacheServer = arrayStaffWs;
							/**
							 * So sanh du lieu tra ve tu ws va client
							 */

							if (arrayStaff.size() > 0) {
								resultArr.removeAll(resultArr);
								resultArr = intersect(arrayStaffWs,
										arrayStaffCacheClient);
							}

							if (resultArr.size() > 0) {
								arrayStaff.removeAll(arrayStaff);
								for (Staff item : resultArr) {
									arrayStaff.add(item);
								}
							}
							if (arrayStaff.size() > 0) {
								for (Staff item : arrayStaff) {
									Log.e("TAG",
											"KIEM TRA " + item.getStaffCode());
								}
							}
							mAdapterStaff.notifyDataSetChanged();
							progress.dismiss();

						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private ArrayList<Staff> intersect(ArrayList<Staff> arr1,
			ArrayList<Staff> arr2) {
		ArrayList<Staff> result = new ArrayList<>();
		for (Staff staffItem : arr1) {
			for (Staff staffItem1 : arr2) {
				if (staffItem.equals(staffItem1)) {

					result.add(staffItem);
					break;
				}
			}

		}

		return result;
	}

	private class SortBySaleAsynctask extends AsyncTask<String, Void, String> {
		final BCCSGateway input = new BCCSGateway();
		final String original = null;
		String errorMessage = "";
		final XmlDomParse parse = new XmlDomParse();
		final Activity activity;
		final ProgressDialog progress;

		private SortBySaleAsynctask(Activity _activity) {
			Log.e("TAG", "SAP SEP THEO DOANH THU");
			this.activity = _activity;
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
					"<ws:getListStaffOrder>");

			stringBuilder.append("<getStaffSortInput>");
			stringBuilder.append("<token>").append(Session.getToken()).append("</token>");
			stringBuilder.append("<staffCodeOwner>").append(staffCode).append("</staffCodeOwner>");
			stringBuilder.append("<staffCode>null</staffCode>");
			stringBuilder.append("<orderBy>").append(orderBy).append("</orderBy>");
			stringBuilder.append("<maxResult>").append(maxResult).append("</maxResult>");
			stringBuilder.append("</getStaffSortInput>");
			stringBuilder.append("</ws:getListStaffOrder>");
			Log.d("createfilexmlSyn", stringBuilder.toString());
			return stringBuilder.toString();
		}

		@Override
		protected String doInBackground(String... arg0) {
			String xml = createXMLGetListStaffIM(arg0[0], arg0[1], arg0[2]);

			CommonOutput output = null;
			try {
				String envelope = input.buildInputGatewayWithRawData2(
						xml.toString(), "mbccs_getListStaffOrder");
				Log.e("TAG", "envelope" + envelope);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,getActivity(),"mbccs_getListStaffOrder");

				output = input.parseGWResponse(response);

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
					return Constant.ERROR_CODE;
				}
				return original;
			} catch (Exception e) {
				e.printStackTrace();
				errorMessage = getResources().getString(R.string.exception)
						+ e.toString();
				return Constant.ERROR_CODE;
			}// get data

		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				if (result.equals(Constant.ERROR_CODE)) {
					String title = getString(R.string.app_name);
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), errorMessage, title);
					dialog.show();
				} else {
					try {
						Document doc = parse.getDomElement(result);
						NodeList nl = doc.getElementsByTagName("return");

						NodeList nodelstchild = null;
						ChannelDAL channelDAL = new ChannelDAL(context);
						com.viettel.bss.viettelpos.v4.object.Location myLocation = CommonActivity
								.findMyLocation(context);
						Location location = new Location("");
						location.setLatitude(Double.parseDouble(myLocation.getX()));
						location.setLongitude(Double.parseDouble(myLocation.getY()));
						ArrayList<Staff> arrayStaffWs = new ArrayList<>();
						ArrayList<Staff> resultArr = new ArrayList<>();
						for (int i = 0; i < nl.getLength(); i++) {
							nodelstchild = doc
									.getElementsByTagName("listStaff");
							Log.e("TAG",
									"nodelstchild " + nodelstchild.getLength());
							if (arrayStaffWs.size() > 0)
								arrayStaffWs.removeAll(arrayStaffWs);
							for (int j = 0; j < nodelstchild.getLength(); j++) {
								Staff mStaffObject = new Staff();
								Element e1 = (Element) nodelstchild.item(j);
								String name = parse.getValue(e1, "name");
								mStaffObject.setNameStaff(name);
								String staffCode = parse.getValue(e1,
										"staffCode");
								Long totalRevenue = Long.parseLong(parse
										.getValue(e1, "totalRevenue"));
								if (!staffCode.equals(userName)) {
                                    String TABLE_NAME = "staff";
                                    Staff tmStaff = channelDAL
											.getStaffByStaffCode(TABLE_NAME,
													staffCode, location);
									mStaffObject.setImgPath(tmStaff
											.getImgPath());
									mStaffObject.setDistance(tmStaff
											.getDistance());
									mStaffObject.setStaffCode(staffCode);
									mStaffObject.setTotalRevenue(totalRevenue);
									mStaffObject.setStaffCode(staffCode);
									mStaffObject.setX(tmStaff.getX());
									mStaffObject.setY(tmStaff.getY());
									Log.e("TAG", "SAP SEP THEO DOANH THU "
											+ name);
									Location locationStaff = new Location(
											"location Satff");
									if (tmStaff.getX() != 0) {
										locationStaff.setLatitude(tmStaff
												.getX());
									} else {
										locationStaff.setLatitude(0);
									}
									if (tmStaff.getY() != 0) {
										locationStaff.setLongitude(tmStaff
												.getY());
									} else {
										locationStaff.setLongitude(0);
									}

									float distance = 0;
									distance = location
											.distanceTo(locationStaff);
									mStaffObject.setDistance(distance);
									arrayStaffWs.add(mStaffObject);

								}
							}

						}

						arrayStaffCacheServer = arrayStaffWs;
						/**
						 * So sanh du lieu tra ve tu ws va client
						 */
						Log.e("TAG",
								"kich thuoc arrayStaff = " + arrayStaff.size());
						if (arrayStaff.size() > 0) {
							resultArr.removeAll(resultArr);
							resultArr = intersect(arrayStaffWs,
									arrayStaffCacheClient);
						}
						Log.e("TAG",
								"kich thuoc arrayStaff = " + resultArr.size());
						if (resultArr.size() > 0) {
							arrayStaff.removeAll(arrayStaff);
							for (Staff item : resultArr) {
								Log.e("TAG", "KIEM TRA " + item.getStaffCode());
								arrayStaff.add(item);
							}
						}

						mAdapterStaff.notifyDataSetChanged();

						progress.dismiss();
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
			}
		}
	}

	private String createXMLGetListStaff(String staffCode, String orderBy,
                                         String maxResult) {
		StringBuilder stringBuilder = new StringBuilder(
				"<ws:viewFrequencyVisited>");

		stringBuilder.append("<viewVisitHistoryInput>");
		stringBuilder.append("<token>").append(Session.getToken()).append("</token>");
		stringBuilder.append("<staffCode>").append(staffCode).append("</staffCode>");
		stringBuilder.append("<orderBy>").append(orderBy).append("</orderBy>");
		stringBuilder.append("<maxResult>").append(maxResult).append("</maxResult>");
		stringBuilder.append("</viewVisitHistoryInput>");
		stringBuilder.append("</ws:viewFrequencyVisited>");
		Log.d("createfilexmlSyn", stringBuilder.toString());
		return stringBuilder.toString();
	}


	private void showTapMap() {
		mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(getActivity(),
				R.anim.right_in));
		mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(
				getActivity(), R.anim.left_out));

		if (!ktMap) {
			btnMapAndList
					.setBackgroundResource(R.drawable.button_list_background);
			ktMap = true;
			MapConfig.changeSRS(SRSType.SRS_4326);
			fillToMap = new FillToMap();
			if (myLocation != null) {

				fillToMap.execute();
			}

		} else {

			btnMapAndList
					.setBackgroundResource(R.drawable.background_button_map);
			ktMap = false;
		}

		mViewFlipper.showNext();
	}

	// private void showTabList() {
	// mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(getActivity(),
	// R.anim.right_in));
	// btnMapAndList.setBackgroundResource(R.drawable.background_button_map);
	// mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(
	// getActivity(), R.anim.left_out));
	// mViewFlipper.setDisplayedChild(0);
	// }
}
