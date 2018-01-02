package com.viettel.bss.viettelpos.v4.channel.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.channel.adapter.AdapterFilterChannelBlack;
import com.viettel.bss.viettelpos.v4.channel.adapter.AdapterStaff;
import com.viettel.bss.viettelpos.v4.channel.adapter.ChannelTypeAdapter;
import com.viettel.bss.viettelpos.v4.channel.dal.ChannelDAL;
import com.viettel.bss.viettelpos.v4.channel.dal.ChannelTypeDAL;
import com.viettel.bss.viettelpos.v4.channel.object.ChannelTypeOJ;
import com.viettel.bss.viettelpos.v4.channel.object.SalePointsRouteDTO;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customer.object.BOCOutput;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.handler.ListChannelHandler;
import com.viettel.bss.viettelpos.v4.handler.VSAMenuHandler;
import com.viettel.bss.viettelpos.v4.login.object.Manager;
import com.viettel.bss.viettelpos.v4.login.object.Staff;
import com.viettel.bss.viettelpos.v4.object.Location;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.maps.MapView;
import com.viettel.maps.base.LatLng;
import com.viettel.maps.layers.MapLayer;
import com.viettel.maps.objects.InfoWindowOptions;
import com.viettel.maps.objects.MapObject;
import com.viettel.maps.objects.Marker;
import com.viettel.maps.objects.MarkerOptions;
import com.viettel.maps.util.MapConfig;
import com.viettel.maps.util.MapConfig.SRSType;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FragmentListChannel extends Fragment implements Define,
		OnItemClickListener, TextWatcher, OnClickListener {
	private ArrayList<Staff> arrayStaff;
	private static ArrayList<Staff> arrayStaffCusCareByDay;
	private ArrayList<Staff> arrayStaffCacheClient;
	private ArrayList<Staff> arrayStaffCacheServer;
	private String selectType = "";
	private ArrayList<ChannelTypeOJ> arrChannelType;
	private ListView lvListStaff;
	private Spinner spChannelType;

	private AdapterStaff mAdapterStaff;
    // private boolean loadmore = true; //tham so cho phep load more
	// private boolean loadOk = false;

	private String inputSearchName = null;
	private LoadStaffByChannelType mLoadStaffByChannelType;
//	private View footer;
	private EditText searchChannel;
	private View mView;
	private Long staffType = 0L;
	// private int pos = 0;
	private Location myLocation;
	private ViewFlipper mViewFlipper;
	private MapView mapView;
	private LoadmoreStaff loadmoreStaff;
	private AsyGetListChannelInDay asyGetListChannelInDay;
    // public static String userName;
	private boolean ktMap = false;
	private Spinner spSortBy;
	// private String TABLE_NAME = "staff";
	private static final String TAG = "Map test";
	private Staff staffSelect = null;
	private Boolean mager = true;
    private Boolean selectByLocation = false;
	private Context context;
	private Long pointOfSale = null;
	private String THREAD_RESULT_KEY = "result_thread";
	private boolean selectCusCareByDay = false;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.e("TAG", "onActivityCreated  FragmentListChannel");

		super.onActivityCreated(savedInstanceState);
	}

	private void initOnResume() {
//		mapView = (MapView) getActivity().findViewById(R.id.idMapViewStaff);
		MapConfig.changeSRS(SRSType.SRS_900913);
		mapView.setMarkerClickListener(new MapLayer.OnClickListener() {

			@Override
			public boolean onClick(Point point, LatLng pos, MapObject obj) {
				Marker marker = (Marker) obj;
				InfoWindowOptions opts = new InfoWindowOptions(marker
						.getTitle(), marker.getPosition());
				opts.snippet(marker.getDescription());
				opts.anchor(new Point(0, -marker.getIcon().getHeight()));
				// InfoWindow info = mapView.showInfoWindow(opts);
				// String id = info.getId();
				staffSelect = (Staff) marker.getUserData();
				return true;
			}
		});
		mapView.setInfoWindowClickListener(new MapLayer.OnClickListener() {
			@Override
			public boolean onClick(Point point, LatLng pos, MapObject obj) {
				Bundle mBundle = new Bundle();
				mBundle.putSerializable(KEY_STAFF, staffSelect);
				FragmentChanelFunction mListMenuManager = new FragmentChanelFunction();
				mListMenuManager.setArguments(mBundle);
				mListMenuManager.setTargetFragment(this, 100);
				ReplaceFragment.replaceFragment(getActivity(),
						mListMenuManager, false);
				return true;
			}
		});
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
        enableMenuChannel(menu, true);
	}

    private MenuItem menuItemMap;
    private void enableMenuChannel(Menu menu, boolean enable){
        MenuItem menuItemSortByCareAsc = menu.findItem(R.id.btnSortByCareAsc);
        menuItemSortByCareAsc.setVisible(enable);

        MenuItem menuItemSortByCareDesc = menu.findItem(R.id.btnSortByCareDesc);
        menuItemSortByCareDesc.setVisible(enable);

        menuItemMap = menu.findItem(R.id.btnMap);
        menuItemMap.setVisible(enable);
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
            case R.id.btnSortByCareAsc:
                Log.e("TAG", "Session.userName1 = " + Session.userName);
                if (CommonActivity.isNetworkConnected(getActivity())) {
                    selectType = Constant.BY_VISIT;
                    new SortAsynctask(getActivity()).execute(Session.userName, "1", "10");
                    mager = false;
                } else {
                    String title = getString(R.string.app_name);
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
                            getResources().getString(R.string.errorNetwork), title);
                    dialog.show();
                }
                break;
            case R.id.btnSortByCareDesc:
                if (CommonActivity.isNetworkConnected(getActivity())) {
                    Log.e("TAG", "Session.userName2 = " + Session.userName);
                    selectType = Constant.BY_VISIT;
                    new SortAsynctask(getActivity()).execute(Session.userName, "0", "10");
                    mager = false;
                } else {
                    String title = getString(R.string.app_name);
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
                            getResources().getString(R.string.errorNetwork), title);
                    dialog.show();
                }
                break;
            case R.id.btnMap:
                if (CommonActivity.isNetworkConnected(getActivity())) {
                    showTapMap();
                } else {
                    String title = getString(R.string.app_name);
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
                            getResources().getString(R.string.errorNetwork), title);
                    dialog.show();
                }
                break;
            default:
                break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView");
		if (mView == null) {
			mView = inflater.inflate(R.layout.layout_list_staff, container,
					false);

			arrayStaff = new ArrayList<>();
			arrayStaffCacheClient = new ArrayList<>();
			arrayStaffCacheServer = new ArrayList<>();
			arrayStaffCusCareByDay = new ArrayList<>();

			Unit(mView);
		} else {
		}

		return mView;
	}

	@Override
	public void onStart() {
		Log.d("TAG", "onStart FragmentListChannel");
		super.onStart();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == 100) {
				// loadmore = true;
				// loadOk = true;
                Boolean backPress = true;
				if (loadmoreStaff != null) {
					loadmoreStaff.cancel(true);
				}
			}
		}
	}

	@Override
	public void onResume() {
        MainActivity.getInstance().setTitleActionBar(R.string.channel, true);
        initOnResume();

		if (mapView != null) {
			mapView.refresh();
		}

		if (mAdapterStaff != null) {
			mAdapterStaff.notifyDataSetChanged();
		}

		super.onResume();
	}

	@Override
	public void onPause() {
		Log.d("TAG", "onPause FragmentListChannel");
		super.onPause();
	}

	@Override
	public void onStop() {
		Log.d("TAG", "onStop FragmentListChannel");
		super.onStop();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d("TAG", "onCreate FragmentListChannel");
		super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

	}

	@Override
	public void onDestroy() {
		Log.d("TAG", "onDestroy FragmentListChannel");
		dismissProgressDialog();
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		Log.d("TAG", "onDetach FragmentListChannel");

		super.onDetach();
	}

	@Override
	public void onDestroyView() {
		Log.d("TAG", "onDestroyView FragmentListChannel");
		if (loadmoreStaff != null) {
			loadmoreStaff.cancel(true);
			// loadOk = true;
			// loadmore = true;
		}
		if (mLoadStaffByChannelType != null)
			mLoadStaffByChannelType.cancel(true);
		super.onDestroyView();
	}

	String tag = "list channel";

	@SuppressLint("InflateParams")
    private void Unit(View v) {
		mapView = (MapView) v.findViewById(R.id.idMapViewStaff);
		try {
			this.context = getActivity();
			mViewFlipper = (ViewFlipper) v.findViewById(R.id.vfListAndMap);
			// GPSTracker gps = new GPSTracker(getActivity());
			// myLocation = gps.getLocation();
			myLocation = CommonActivity.findMyLocation(getActivity());
			// TextView textTitle = (TextView) v.findViewById(R.id.textTitle);

			lvListStaff = (ListView) v.findViewById(R.id.lv_staff);
			// arrayStaff = new ArrayList<Staff>();
			// smsListStaff = (TextView) v.findViewById(R.id.smsListStaff);

			mAdapterStaff = new AdapterStaff(arrayStaff, getActivity(),
					AdapterStaff.TYPE_CUSCARE_BY_DAY);
			LayoutInflater inflater = getActivity().getLayoutInflater();
//			footer = inflater.inflate(R.layout.footer_layout, null, false);
			lvListStaff.setAdapter(mAdapterStaff);
			// lvListStaff.setOnScrollListener(this);
			lvListStaff.setOnItemClickListener(this);
			selectByLocation = false;

			/*
			 * Khoi tao spin loc kenh theo loai kenh
			 */
            ChannelTypeDAL channelTypeDAL = new ChannelTypeDAL(getActivity());
			channelTypeDAL.open();
			spChannelType = (Spinner) v.findViewById(R.id.sp_list_channel_type);
			arrChannelType = new ArrayList<>();
			arrChannelType = channelTypeDAL.getTasksToArray(false);
			channelTypeDAL.close();
			ChannelTypeAdapter adChannelType = new ChannelTypeAdapter(
					getActivity(), arrChannelType);
			spChannelType.setAdapter(adChannelType);
			spChannelType.setOnItemSelectedListener(new OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> adapterView,
								View view, int position, long id) {
							try {
								// if (mAdapterStaff != null) {
								// if (selectCusCareByDay) {
								// mAdapterStaff
								// .setAdapterType(AdapterStaff.TYPE_CUSCARE_BY_DAY);
								// } else {
								// mAdapterStaff
								// .setAdapterType(AdapterStaff.TYPE_LOCATION);
								// }
								// }

								arrayStaffCacheServer = new ArrayList<>();
								selectByLocation = false;
								// loadmore = false;
								// loadOk = false;
								ChannelTypeOJ channelTypeOJ = arrChannelType
										.get(position);
								staffType = channelTypeOJ.getType_channel_id();
								if (staffType == 0L) {
									arrayStaffCacheServer.clear();
								}
								pointOfSale = null;
								if (staffType != null
										&& Constant.CHANNEL_TYPE_POS
												.compareTo(staffType) == 0) {
									staffType = Constant.CHANNEL_TYPE_COLLABORATOR;
									pointOfSale = Constant.POINT_OF_SALE_TYPE;
								} else if (staffType != null
										&& Constant.CHANNEL_TYPE_COLLABORATOR
												.compareTo(staffType) == 0) {
									// channel_type_id =10: kenh NVDB fix
									// channel_type_id =10,
									// point_of_sale = 2
									pointOfSale = Constant.POINT_OF_SALE_COLLABORATOR;
								}

//								if (position != 0) {
									 if (asyGetListChannelInDay == null || !asyGetListChannelInDay.getRunning()) {
										arrayStaff.clear();
										loadmoreStaff = new LoadmoreStaff();
										spSortBy.setSelection(0);
										loadmoreStaff.execute();
									 }
//								}
							} catch (Exception e) {
								e.printStackTrace();
							}

						}

						@Override
						public void onNothingSelected(AdapterView<?> adapter) {

						}
					});
			/**
			 * Search
			 */
			searchChannel = (EditText) v.findViewById(R.id.autoSearchStaffName);
			searchChannel.addTextChangedListener(new TextWatcher() {
				@Override
				public void afterTextChanged(Editable s) {

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {

				}

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
//					Log.i("i", "before = " + before + " count= " + count);
//					if (backPress == false) {
//						if ((count + before) > 0) {
//							inputSearchName = searchChannel.getText()
//									.toString();
//							arrayStaff.clear();
//							arrayStaffCacheClient.clear();
//							arrayStaffCacheServer.clear();
//							// pos = 0;
//							// loadmore = true;
//							// loadOk = true;
//							// mLoadStaffByChannelType = new
//							// LoadStaffByChannelType();
//							// mLoadStaffByChannelType.execute();
//							loadmoreStaff = new LoadmoreStaff();
//							loadmoreStaff.execute();
//
//							// lvListStaff.addFooterView(footer, null, false);
//						}
//
//					} else {
//						mAdapterStaff.notifyDataSetChanged();
//
//						backPress = false;
//					}
					
					spSortBy.setSelection(0);
					
					inputSearchName = searchChannel.getText()
							.toString();
					arrayStaff.clear();
					arrayStaffCacheClient.clear();
					arrayStaffCacheServer.clear();
					// pos = 0;
					// loadmore = true;
					// loadOk = true;
					// mLoadStaffByChannelType = new
					// LoadStaffByChannelType();
					// mLoadStaffByChannelType.execute();
					loadmoreStaff = new LoadmoreStaff();
					loadmoreStaff.execute();

				}

			});
			/**
			 * Khoi tao spin sap xep kenh
			 */
			spSortBy = (Spinner) v.findViewById(R.id.sp_fillter);
			final ArrayList<Manager> arrayListManagerSpin = new ArrayList<>();
			arrayListManagerSpin.add(new Manager(R.drawable.ic_icon_macdinh,
					getResources().getString(R.string.defaultFillter), 0,
					Constant.Fillter.defaultFillter));
			arrayListManagerSpin.add(new Manager(R.drawable.ic_icon_macdinh,
					getResources().getString(R.string.cusCareByDay), 0,
					Constant.Fillter.cusCareByDay));
			// arrayListManagerSpin.add(new Manager(R.drawable.customers,
			// getResources().getString(R.string.spinner_show), 0,
			// Constant.Fillter.spinner_show));
			arrayListManagerSpin.add(new Manager(R.drawable.ic_icon_macdinh,
					getResources().getString(R.string.orderByVisitDesc), 0,
					Constant.Fillter.orderByVisitDesc));
			arrayListManagerSpin.add(new Manager(R.drawable.ic_icon_macdinh,
					getResources().getString(R.string.orderByVisitAsc), 0,
					Constant.Fillter.orderByVisitAsc));
			arrayListManagerSpin.add(new Manager(R.drawable.ic_icon_macdinh,
					getResources().getString(R.string.orderBySaleDesc), 0,
					Constant.Fillter.orderBySaleDesc));
			arrayListManagerSpin.add(new Manager(R.drawable.ic_icon_macdinh,
					getResources().getString(R.string.orderBySaleAsc), 0,
					Constant.Fillter.orderBySaleAsc));
			arrayListManagerSpin.add(new Manager(R.drawable.ic_icon_macdinh,
					getResources().getString(R.string.orderByLocation), 0,
					Constant.Fillter.orderByLocation));
			arrayListManagerSpin.add(new Manager(R.drawable.ic_icon_macdinh,
					getResources().getString(R.string.notUpdateLocation), 0,
					Constant.Fillter.notUpdateLocation));
			AdapterFilterChannelBlack adSort = new AdapterFilterChannelBlack(
					getActivity(), arrayListManagerSpin);
			spSortBy.setAdapter(adSort);
			spSortBy.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> adapterView,
						View view, int position, long id) {
					Log.d(TAG, "setOnItemSelectedListener position = "
							+ position);
					String menuNameSelect = arrayListManagerSpin.get(position)
							.getKeyMenuName();
					selectByLocation = false;
					selectCusCareByDay = false;

					spChannelType.setVisibility(View.VISIBLE);
					searchChannel.setVisibility(View.VISIBLE);

					// loadmore = true;
					// loadOk = true;
					mager = false;

					if (arrayStaff != null) {
						arrayStaff.clear();
					}

					if (mAdapterStaff != null) {
						mAdapterStaff.setAdapterType(AdapterStaff.TYPE_LOCATION);
					}

                    switch (menuNameSelect) {
                        case Constant.Fillter.spinner_show:
                            break;
                        case Constant.Fillter.orderByVisitDesc:
                            // loadmore = true;
                            // loadOk = true;
                            mager = true;
                            // Log.e("", "");
                            selectType = Constant.BY_VISIT;
                            if (CommonActivity.isNetworkConnected(getActivity())) {
                                new SortAsynctask(getActivity()).execute(
                                        Session.userName, "0", "10");
                            } else {
                                Toast.makeText(
                                        getActivity(),
                                        getResources().getString(
                                                R.string.errorNetwork), Toast.LENGTH_SHORT)
                                        .show();
                            }
                            break;
                        case Constant.Fillter.orderByVisitAsc:
                            // Cham soc it
                            // mager = true;
                            // loadmore = true;
                            // loadOk = true;
                            selectType = Constant.BY_VISIT;
                            if (CommonActivity.isNetworkConnected(getActivity())) {
                                new SortAsynctask(getActivity()).execute(
                                        Session.userName, "1", "10");
                            } else {
                                Toast.makeText(
                                        getActivity(),
                                        getResources().getString(
                                                R.string.errorNetwork), Toast.LENGTH_SHORT)
                                        .show();
                            }

                            break;
                        case Constant.Fillter.orderBySaleDesc:
                            // Ban hang tot nhat
                            // mager = true;
                            // loadmore = true;
                            // loadOk = true;
                            selectType = Constant.BY_SALE;
                            if (CommonActivity.isNetworkConnected(getActivity())) {
                                new SortBySaleAsynctask(getActivity()).execute(
                                        Session.userName, "0", "10");
                            } else {
                                Toast.makeText(
                                        getActivity(),
                                        getResources().getString(
                                                R.string.errorNetwork), Toast.LENGTH_SHORT)
                                        .show();
                            }
                            break;
                        case Constant.Fillter.orderBySaleAsc:
                            // ban hang it nhat
                            // mager = true;
                            // loadmore = true;
                            // loadOk = true;
                            selectType = Constant.BY_SALE;

                            if (CommonActivity.isNetworkConnected(getActivity())) {
                                new SortBySaleAsynctask(getActivity()).execute(
                                        Session.userName, "1", "10");
                            } else {
                                Toast.makeText(
                                        getActivity(),
                                        getResources().getString(
                                                R.string.errorNetwork), Toast.LENGTH_SHORT)
                                        .show();
                            }
                            break;
                        case Constant.Fillter.orderByLocation:
                            // loadmore = true;
                            // loadOk = false;
                            myLocation = CommonActivity
                                    .findMyLocation(getActivity());
                            if (myLocation == null) {
                                Context mContext = getActivity();
                                if (CommonActivity.checkGps(mContext)) {
                                    Toast.makeText(
                                            getActivity(),
                                            getResources().getString(
                                                    R.string.checkgps), Toast.LENGTH_SHORT)
                                            .show();

                                } else {
                                    Toast.makeText(
                                            getActivity(),
                                            getResources().getString(
                                                    R.string.cannot_get_location),
											Toast.LENGTH_SHORT).show();
                                }
                            } else {

                                // loadmore = false;
                                // loadOk = false;
                                // arrayStaff.clear();
                                // pos = 0;
                                // loadOk = true;
                                loadmoreStaff = new LoadmoreStaff();
                                loadmoreStaff.execute();
                            }

                            break;
                        case Constant.Fillter.notUpdateLocation:
                            // loadmore = true;
                            // loadOk = false;
                            // arrayStaff.clear();
                            // pos = 0;
                            mAdapterStaff.setAdapterType(-2);
                            selectByLocation = true;
                            loadmoreStaff = new LoadmoreStaff();
                            loadmoreStaff.execute();
                            // new LoadStaffNotLocationAsync().execute();

                            break;
                    }

					if (menuNameSelect.equals(Constant.Fillter.cusCareByDay)) {
						selectCusCareByDay = true;
						myLocation = CommonActivity
								.findMyLocation(getActivity());
						// loadmore = false;

						mAdapterStaff
								.setAdapterType(AdapterStaff.TYPE_CUSCARE_BY_DAY);
						// loadOk = false;

						// spChannelType.setVisibility(View.GONE);
						// searchChannel.setVisibility(View.GONE);

						if (myLocation == null) {
							Context mContext = getActivity();
							if (CommonActivity.checkGps(mContext)) {
								Toast.makeText(
										getActivity(),
										getResources().getString(
												R.string.checkgps), Toast.LENGTH_SHORT)
										.show();

							} else {
								Toast.makeText(
										getActivity(),
										getResources().getString(
												R.string.cannot_get_location),
										Toast.LENGTH_SHORT).show();
							}
						} else {

							// pos = 0;
							// loadOk = true;
							if (asyGetListChannelInDay == null
									|| !asyGetListChannelInDay.getRunning()) {
								asyGetListChannelInDay = new AsyGetListChannelInDay(
										getActivity());
								asyGetListChannelInDay.execute();
							}
						}
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
		// pos = 0;
		// loadOk = true;
		// loadmoreStaff = new LoadmoreStaff();
		// if(spSortBy != null){
		// spSortBy.setSelection(0);
		// }
		//
		// loadmoreStaff.execute();
		if (spSortBy != null) {
			spSortBy.setSelection(1);
		}

		if (myLocation.getX().equals("0") || myLocation.getY().equals("0")) {
			findingLocation(true);

			// AsynGetLocation asynGetLocation = new AsynGetLocation();
			// asynGetLocation.execute();
			// FindLocationThread thread = new FindLocationThread(handler);
			// thread.run();

			new Thread(new Runnable() {

				@Override
				public void run() {
					Message msg = Message.obtain(handler);
					try {
						while (true) {
							Thread.sleep(1000);
							if (CommonActivity.findMyLocation(context) != null
									&& !CommonActivity.findMyLocation(context)
											.getX().equals("0")) {
								break;
							}
						}
						msg.obj = 0;

					} catch (Exception e) {
						Log.e("exception when handle result of finding location",
								"xxx", e);
						msg.obj = 1;
					}
					handler.sendMessage(msg);
				}
			}).start();
		} else {
			findingLocation(false);
		}
	}

	private class LoadmoreStaff extends AsyncTask<Void, Void, ArrayList<Staff>> {
		public LoadmoreStaff() {
//			Log.d(TAG, "++++++ Run LoadmoreStaff");
//			lvListStaff.addFooterView(footer, null, false);
		}

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
		}

		@Override
		protected ArrayList<Staff> doInBackground(Void... params) {
			ChannelDAL channelDAL = new ChannelDAL(getActivity());
			android.location.Location anrLocation = new android.location.Location(
					"");
			anrLocation.setLatitude(Double.parseDouble(myLocation.getX()));
			anrLocation.setLongitude(Double.parseDouble(myLocation.getY()));
			ArrayList<Staff> rs = channelDAL.getListStaff(TABLE_NAME_STAFF,
					staffType, inputSearchName, anrLocation, Session.userName,
					arrayStaff.size(), Integer.MAX_VALUE, selectByLocation,
					pointOfSale);
			try {
				channelDAL.close();
			} catch (Exception ignored) {
			}
			return rs;

		}

		@Override
		protected void onPostExecute(final ArrayList<Staff> result) {
			try {
				if (result != null && !result.isEmpty()) {
					Log.d("=>>>>>> LoadmoreStaff",
							"result.size() :: " + result.size());
					for (int i = 0; i < result.size(); i++) {

						Staff staff = result.get(i);
						arrayStaff.add(staff);
					}
					// arrayStaff.addAll(result);

					// if (result.size() < COUNTLOAD) {
					// loadOk = true;
					// loadmore = true;
					// } else {
					// loadOk = false;
					// loadmore = false;
					// }

					if (arrayStaffCacheServer.size() > 0 && mager) {

						// arrayStaff = intersect(arrayStaffCacheServer,
						// arrayStaff);

						ArrayList<Staff> arrStaff = intersect(
								arrayStaffCacheServer, arrayStaff);
						arrayStaff = new ArrayList<>();
						arrayStaff.addAll(arrStaff);
						if (arrStaff != null && !arrStaff.isEmpty()) {
							arrStaff.clear();
						}
					}

					// pos = arrayStaff.size();

				} else {
					Log.d("=>>>>>> LoadmoreStaff", "result is null");
					arrayStaff = new ArrayList<>();
					// loadmore = false;
					// loadOk = false;
				}

				// if (mAdapterStaff == null) {
				Log.d("=>>>>>> LoadmoreStaff", "notifyDataSetChanged");
				mAdapterStaff = new AdapterStaff(arrayStaff, getActivity(),
						AdapterStaff.TYPE_LOCATION);
				lvListStaff.setAdapter(mAdapterStaff);
				mAdapterStaff.notifyDataSetChanged();

				// if (selectByLocation == true) {
				// mAdapterStaff = new AdapterStaff(arrayStaff,
				// getActivity());
				// lvListStaff.setAdapter(mAdapterStaff);
				// // selectByLocation = false;
				// }
				// if (loadOk) {
				// loadmore = true;
				// } else {
				// loadmore = false;
				// }
				// } else {
				// mAdapterStaff.notifyDataSetChanged();
				// }
				super.onPostExecute(result);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
//				try {
//					lvListStaff.removeFooterView(footer);
//				} catch (Exception ignored) {
//				}
			}
		}
	}

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
				ChannelDAL channelDAL = new ChannelDAL(getActivity());
				android.location.Location anrLocation = new android.location.Location(
						"");
				anrLocation.setLatitude(Double.parseDouble(myLocation.getX()));
				anrLocation.setLongitude(Double.parseDouble(myLocation.getY()));
				ArrayList<Staff> rs = channelDAL.getAllStaff(TABLE_NAME_STAFF,
						staffType, inputSearchName, anrLocation,
						Session.userName);
				channelDAL.close();
				return rs;
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
					Log.e("NUMVISIT ", "selectType = " + selectType);
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
									" getTotalRevenue = "
											+ item.getTotalRevenue());
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
						if (mStaff.getX() > 0 && mStaff.getY() > 0) {

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
                                                        R.drawable.goodstaff))
                                                .position(
                                                        new LatLng(mStaff.getX(),
                                                                mStaff.getY()))
                                                .title(mStaff.getNameStaff())
                                                .description(
                                                        mStaff.getAddressStaff());
                                    } else {
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
                                                        R.drawable.goodstaff))
                                                .position(
                                                        new LatLng(mStaff.getX(),
                                                                mStaff.getY()))
                                                .title(mStaff.getNameStaff())
                                                .description(
                                                        mStaff.getAddressStaff());
                                        Log.e("TAG",
                                                "BAN HANG trung binh"
                                                        + mStaff.getNameStaff());
                                    } else {
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

					if (mAdapterStaff != null) {
						mAdapterStaff.notifyDataSetChanged();
						// if (selectByLocation == true) {
						// mAdapterStaff = new AdapterStaff(arrayStaff,
						// getActivity());
						// lvListStaff.setAdapter(mAdapterStaff);
						// // selectByLocation = false;
						// }
						// if (loadOk) {
						// loadmore = true;
						// } else {
						// loadmore = false;
						// }
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			super.onPostExecute(result);
		}
	}

	/**
	 * tim kiem theo loai kenh
	 */
	private class LoadStaffByChannelType extends
			AsyncTask<Void, Void, ArrayList<Staff>> {

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
		}

		@Override
		protected ArrayList<Staff> doInBackground(Void... params) {
			/*
			 * return channelDAL.getTasksToArray(TABLE_NAME_STAFF, staffType,
			 * inputSearchName, myLocation, userName);
			 */
			ChannelDAL channelDAL = new ChannelDAL(context);
			try {
				android.location.Location anrLocation = new android.location.Location(
						"");
				anrLocation.setLatitude(Double.parseDouble(myLocation.getX()));
				anrLocation.setLongitude(Double.parseDouble(myLocation.getY()));

                return channelDAL.getListStaff(
                        TABLE_NAME_STAFF, staffType, inputSearchName,
                        anrLocation, Session.userName, arrayStaff.size(),
                        Integer.MAX_VALUE, selectByLocation, pointOfSale);
			} catch (Exception ignored) {
			} finally {
				channelDAL.close();
			}
			return new ArrayList<>();
			/*
			 * return channelDAL .getAllStaff(TABLE_NAME_STAFF, staffType,
			 * inputSearchName, myLocation, userName);
			 */
		}

		@Override
		protected void onPostExecute(final ArrayList<Staff> result) {
			super.onPostExecute(result);
			try {
				if (result != null && !result.isEmpty()) {
					Log.e("e", "Co phan tu tra ve");
					if (arrayStaff.size() > 0) {
						arrayStaff.clear();
					}
					for (Staff item : result) {

						Log.e("info", "getStaffCode = " + item.getStaffCode());
						arrayStaff.add(item);
					}

					if (arrayStaffCacheServer.size() > 0 && mager) {
						arrayStaff = intersect(arrayStaffCacheServer,
								arrayStaff);
					}

					// if (result.size() < COUNTLOAD) {
					// lvListStaff.removeFooterView(footer);
					// loadOk = true;
					// } else {
					// loadOk = false;
					// loadmore = false;
					// }
					// pos = arrayStaff.size();
					// mAdapterStaff.notifyDataSetChanged();
					mAdapterStaff = new AdapterStaff(arrayStaff, getActivity(),
							AdapterStaff.TYPE_LOCATION);
					lvListStaff.setAdapter(mAdapterStaff);
					if (mAdapterStaff != null) {
						mAdapterStaff.notifyDataSetChanged();
						// if (selectByLocation == true) {
						// mAdapterStaff = new AdapterStaff(arrayStaff,
						// getActivity());
						// lvListStaff.setAdapter(mAdapterStaff);
						// // selectByLocation = false;
						// }
						// if (loadOk) {
						// loadmore = true;
						// } else {
						// loadmore = false;
						// }
					}
					Log.e("TAG", "kiem tra " + arrayStaff.size());
				} else {
					arrayStaff = new ArrayList<>();
					if (mAdapterStaff != null) {
						mAdapterStaff.notifyDataSetChanged();
						// if (selectByLocation == true) {
						// mAdapterStaff = new AdapterStaff(arrayStaff,
						// getActivity());
						// lvListStaff.setAdapter(mAdapterStaff);
						// // selectByLocation = false;
						// }
						// if (loadOk) {
						// loadmore = true;
						// } else {
						// loadmore = false;
						// }
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	// @Override
	// public void onScroll(AbsListView view, int firstVisibleItem,
	// int visibleItemCount, int totalItemCount) {
	// int lastitemScreen = firstVisibleItem + visibleItemCount;
	// if ((lastitemScreen == totalItemCount) && (!loadmore) && (!backPress)
	// && totalItemCount > 0) {
	// // loadmore = true;
	// if (AdapterStaff.TYPE_CUSCARE_BY_DAY != mAdapterStaff
	// .getAdapterType()) {
	// loadmoreStaff = new LoadmoreStaff();
	// loadmoreStaff.execute();
	// }
	// }
	//
	// }

	// @Override
	// public void onScrollStateChanged(AbsListView view, int scrollState) {
	//
	// }

	private Staff mStaff;
	private int posistion;

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		posistion = arg2;
		CommonActivity.hideSoftKeyboard(getActivity());
		mStaff = arrayStaff.get(posistion);		
		if (AdapterStaff.TYPE_CUSCARE_BY_DAY != mAdapterStaff.getAdapterType()
				&& !isStaffByDay(mStaff)) {
			Bundle mBundle = new Bundle();
			mBundle.putSerializable(KEY_STAFF, mStaff);
			mBundle.putSerializable("FLAG", false);

			FragmentChanelFunction mListMenuManager = new FragmentChanelFunction();
			mListMenuManager.setArguments(mBundle);
			mListMenuManager.setTargetFragment(this, 100);
			ReplaceFragment.replaceFragment(getActivity(), mListMenuManager,
					false);
		} else {
			new AsyMark2SellingContactHis(getActivity()).execute();
		}
	}

	@Override
	public void afterTextChanged(Editable arg0) {

	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {

	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnHome:
			CommonActivity.callphone(getActivity(), Constant.phoneNumber);
			// FragmentMenuHome fragmentMenuHome = new FragmentMenuHome();
			// ReplaceFragment.replaceFragment(getActivity(), fragmentMenuHome,
			// true);
			break;


		}
		CommonActivity.hideKeyboard(searchChannel, getActivity());
	}

	/**
	 * 
	 * @author DuongTT Sap xep theo so lan cham soc 1. Lay danh sach kenh theo
	 *         tieu chi tim kiem hien tai 2. Gui danh sach kenh len server 3.
	 *         Server sap xep va tra ve danh sahc tuong ung 4. Client parse du
	 *         lieu va hien hti
	 */
	private class SortAsynctask extends AsyncTask<String, Void, String> {
		final BCCSGateway input = new BCCSGateway();
		String original = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorMessage = "";
		final ProgressDialog progress;

		private SortAsynctask(Activity _activity) {
			Log.e("TAG", "SAP SEP THEO CHAM SOC");
			this.progress = new ProgressDialog(_activity);
			this.progress.setMessage(_activity.getResources().getString(
					R.string.waitting));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
			// lvListStaff.addFooterView(footer, null, false);
		}

		public String createXMLGetListStaff(String staffCode, String orderBy,
				String maxResult, ArrayList<Staff> arrStaff_) {
			StringBuilder stringBuilder = new StringBuilder(
					"<ws:viewFrequencyVisited>");

			stringBuilder.append("<viewVisitHistoryInput>");
			stringBuilder.append("<token>").append(Session.getToken()).append("</token>");
			for (Staff iteam : arrStaff_) {
				stringBuilder.append("<lstStaffId>").append(iteam.getStaffId()).append("</lstStaffId>");
			}
			stringBuilder.append("<staffCode>").append(staffCode).append("</staffCode>");
			stringBuilder.append("<orderBy>").append(orderBy).append("</orderBy>");
			stringBuilder.append("<maxResult>0</maxResult>");
			stringBuilder.append("</viewVisitHistoryInput>");
			stringBuilder.append("</ws:viewFrequencyVisited>");
			return stringBuilder.toString();
		}

		@Override
		protected String doInBackground(String... arg0) {
			ArrayList<Staff> arrStaff_ = new ArrayList<>();
			ChannelDAL channelDAL = new ChannelDAL(context);
			try {
				android.location.Location anrLocation = new android.location.Location(
						"");
				anrLocation.setLatitude(Double.parseDouble(myLocation.getX()));
				anrLocation.setLongitude(Double.parseDouble(myLocation.getY()));
				arrStaff_ = channelDAL.getAllStaff(TABLE_NAME_STAFF, staffType,
						inputSearchName, anrLocation, Session.userName);
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				if (channelDAL != null) {
					channelDAL.close();
				}
			}

			String xml = "";
			if (arrStaff_.size() > 0) {
				xml = createXMLGetListStaff(arg0[0], arg0[1], arg0[2],
						arrStaff_);
			} else {
				progress.dismiss();
				return Constant.SUCCESS_CODE;
			}

			CommonOutput output = null;
			try {
				String envelope = input.buildInputGatewayWithRawData2(
						xml.toString(), "mbccs_viewFrequencyVisited");

				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_viewFrequencyVisited");

				output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.e("SMS:original", "" + original.toString());
				// Log.e("SMS", "" + output.getError());
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
				return original;
			} catch (Exception e) {
				e.printStackTrace();
				errorMessage = getResources().getString(R.string.exception)
						+ e.toString();
				return Constant.ERROR_CODE;
			}

		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
                switch (result) {
                    case Constant.INVALID_TOKEN2:
                        CommonActivity.BackFromLogin(getActivity(), errorMessage,
                                ";channel.management;");
                        break;
                    case Constant.ERROR_CODE:

                        String title = getString(R.string.app_name);
                        Dialog dialog = CommonActivity.createAlertDialog(
                                getActivity(), errorMessage, title);
                        dialog.show();
                        break;
                    default:
                        try {

                            if (arrayStaff.size() > 0) {
                                arrayStaff.clear();
                                mAdapterStaff.notifyDataSetChanged();
                            }
                            if (arrayStaffCacheServer.size() > 0) {
                                arrayStaffCacheServer.clear();
                            }

                            parseResult(result);

                            // loadOk = true;
                            // loadmore = true;
                            // getActivity().runOnUiThread(runnableUdapteAdapter);
                            // mAdapterStaff.notifyDataSetChanged();
                            mAdapterStaff = new AdapterStaff(arrayStaff,
                                    getActivity(), AdapterStaff.TYPE_VISIT);
                            lvListStaff.setAdapter(mAdapterStaff);
                            for (Staff item : arrayStaff) {
                                Log.e("TAG84", "item name = " + item.getNameStaff());
                            }
                            // lvListStaff.removeFooterView(footer);
                            progress.dismiss();
                            if (ktMap) {
                                Log.e("Log", "hien thi tren ban do");
                                if (myLocation != null) {
                                    mapView.clear();
                                    new FillToMap().execute();
                                }

                            } else {
                                Log.e("Log", "Khong hien thi tren ban do");
                            }

                            // }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
			}
		}
	}

	private void parseResult(String result) {
		try {
			android.location.Location anrLocation = new android.location.Location(
					"");
			anrLocation.setLatitude(Double.parseDouble(myLocation.getX()));
			anrLocation.setLongitude(Double.parseDouble(myLocation.getY()));
			ListChannelHandler handler = (ListChannelHandler) CommonActivity
					.parseXMLHandler(new ListChannelHandler(getActivity(),
							anrLocation), result);
			handler.closeDatabase();
			arrayStaff = handler.getArrayStaff();
			arrayStaffCacheServer = handler.getArrayStaff();

		} catch (Exception e) {
			e.printStackTrace();
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

	/**
	 * 
	 * @author DuongTT Sap xep theo so tien mua hang trong thang 1. Lay danh
	 *         sach kenh theo tieu chi tim kiem hien tai 2. Gui danh sach kenh
	 *         len server 3. Server sap xep va tra ve danh sahc tuong ung 4.
	 *         Client parse du lieu va hien hti
	 */
	private class SortBySaleAsynctask extends AsyncTask<String, Void, String> {
		final BCCSGateway input = new BCCSGateway();
		String errorMessage = "";
		final XmlDomParse parse = new XmlDomParse();
		final ProgressDialog progress;

		private SortBySaleAsynctask(Activity _activity) {
			Log.e("TAG", "SAP SEP THEO DOANH THU");
			this.progress = new ProgressDialog(getActivity());
			this.progress.setMessage(getActivity().getResources().getString(
					R.string.waitting));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
			// lvListStaff.addFooterView(footer, null, false);
		}

		public String createXMLGetListStaffIM(String staffCode, String orderBy,
				String maxResult, ArrayList<Staff> arrStaff_) {
			StringBuilder stringBuilder = new StringBuilder(
					"<ws:getListStaffOrder>");
			stringBuilder.append("<getStaffSortInput>");
			stringBuilder.append("<token>").append(Session.getToken()).append("</token>");
			for (Staff iteam : arrStaff_) {
				stringBuilder.append("<lstStaffCode>").append(iteam.getStaffCode()).append("</lstStaffCode>");
			}
			stringBuilder.append("<staffCodeOwner>").append(staffCode).append("</staffCodeOwner>");
			stringBuilder.append("<orderBy>").append(orderBy).append("</orderBy>");
			stringBuilder.append("<maxResult>0</maxResult>");
			stringBuilder.append("</getStaffSortInput>");
			stringBuilder.append("</ws:getListStaffOrder>");
			Log.d("createfilexmlSyn", stringBuilder.toString());
			return stringBuilder.toString();
		}

		@Override
		protected String doInBackground(String... arg0) {
			ArrayList<Staff> arrStaff_ = new ArrayList<>();
			ChannelDAL channelDAL = new ChannelDAL(context);
			android.location.Location anrLocation = new android.location.Location(
					"");
			anrLocation.setLatitude(Double.parseDouble(myLocation.getX()));
			anrLocation.setLongitude(Double.parseDouble(myLocation.getY()));
			arrStaff_ = channelDAL.getAllStaff(TABLE_NAME_STAFF, staffType,
					inputSearchName, anrLocation, Session.userName);
			channelDAL.close();
			String xml = "";
			if (arrStaff_.size() > 0) {
				xml = createXMLGetListStaffIM(arg0[0], arg0[1], arg0[2],
						arrStaff_);
			} else {
				progress.dismiss();
				return Constant.SUCCESS_CODE;
			}
			CommonOutput output = null;

			try {
				String envelope = input.buildInputGatewayWithRawData2(
						xml.toString(), "mbccs_getListStaffOrder");
				Log.e("TAG", "envelope" + envelope);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListStaffOrder");

				output = input.parseGWResponse(response);

				if (!output.getError().equals("0")) {
					errorMessage = output.getDescription();
					return Constant.ERROR_CODE;
				}
				String original = output.getOriginal();

				VSAMenuHandler handler = (VSAMenuHandler) CommonActivity
						.parseXMLHandler(new VSAMenuHandler(), original);

				output = handler.getItem();
				Log.e("eeee", "original" + original);
				if (!output.getErrorCode().equals("0")) {
					errorMessage = output.getDescription();
					return output.getErrorCode();
				}

				return original;
			} catch (Exception e) {
				e.printStackTrace();
				errorMessage = getResources().getString(R.string.exception)
						+ e.toString();
				return Constant.ERROR_CODE;
			}

		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
                switch (result) {
                    case Constant.INVALID_TOKEN2:
                        CommonActivity.BackFromLogin(getActivity(), errorMessage,
                                ";channel.management;");
                        break;
                    case Constant.ERROR_CODE:
                        String title = getString(R.string.app_name);
                        Dialog dialog = CommonActivity.createAlertDialog(
                                getActivity(), errorMessage, title);
                        dialog.show();
                        break;
                    default:
                        try {

                            if (arrayStaff.size() > 0) {
                                arrayStaff.clear();
                                mAdapterStaff.notifyDataSetChanged();
                            }
                            if (arrayStaffCacheServer.size() > 0) {
                                arrayStaffCacheServer.clear();
                            }

                            parseResult(result);

                            // loadOk = true;
                            // loadmore = true;

                            // getActivity().runOnUiThread(runnableUdapteAdapter);
                            mAdapterStaff = new AdapterStaff(arrayStaff,
                                    getActivity(), AdapterStaff.TYPE_SALE);
                            lvListStaff.setAdapter(mAdapterStaff);

                            if (ktMap) {
                                Log.e("Log", "hien thi tren ban do");
                                if (myLocation != null) {
                                    mapView.clear();
                                    new FillToMap().execute();
                                }

                            } else {
                                Log.e("Log", "Khong hien thi tren ban do");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            progress.dismiss();
                            // lvListStaff.removeFooterView(footer);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        break;
                }
			}
		}
	}

	private void showTapMap() {
		mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(getActivity(),
				R.anim.right_in));
		mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(
				getActivity(), R.anim.left_out));

		if (!ktMap) {
			menuItemMap.setIcon(R.drawable.button_list_background);
			ktMap = true;
			MapConfig.changeSRS(SRSType.SRS_4326);
            FillToMap fillToMap = new FillToMap();
			if (myLocation != null) {

				fillToMap.execute();
			}

		} else {
			selectType = "";
            menuItemMap.setIcon(R.drawable.background_button_map);
			ktMap = false;
		}

		mViewFlipper.showNext();
	}

	private class AsynGetLocation extends AsyncTask<Void, Void, Integer> {

		@Override
		protected Integer doInBackground(Void... arg0) {
			try {
				while (true) {
					Thread.sleep(1000);
					if (CommonActivity.findMyLocation(context) != null
							&& !CommonActivity.findMyLocation(context).getX()
									.equals("0")) {
						break;
					}
				}
				return 0;
			} catch (Exception e) {
				e.printStackTrace();
			}

			return 1;
		}

		@Override
		protected void onPostExecute(Integer result) {
			myLocation = CommonActivity.findMyLocation(context);
			if (result != null && result == 0) {
				findingLocation(false);
			} else {
				mView.findViewById(R.id.textTitle).setVisibility(View.VISIBLE);
				TextView tvTitle = (TextView) mView
						.findViewById(R.id.textTitle);
				tvTitle.setText(R.string.get_location_false);
				mView.findViewById(R.id.lnSearchingLocation).setVisibility(
						View.GONE);
			}

		}
	}

	private void findingLocation(boolean isFinding) {
		try {
			if (isFinding) {
				mView.findViewById(R.id.textTitle).setVisibility(View.GONE);
				mView.findViewById(R.id.lnSearchingLocation).setVisibility(
						View.VISIBLE);
			} else {
				mView.findViewById(R.id.textTitle).setVisibility(View.VISIBLE);
				mView.findViewById(R.id.lnSearchingLocation).setVisibility(
						View.GONE);
				if (mAdapterStaff != null
						&& mAdapterStaff.getAdapterType() == AdapterStaff.TYPE_CUSCARE_BY_DAY) {
					arrayStaff.clear();
					if (asyGetListChannelInDay == null
							|| !asyGetListChannelInDay.getRunning()) {
						asyGetListChannelInDay = new AsyGetListChannelInDay(
								getActivity());
						asyGetListChannelInDay.execute();
					}
				}
			}
		} catch (Exception e) {
			Log.e("exception when handle result of finding location", "xxx", e);
		}
	}

	private final UIHandler handler = new UIHandler();

	class UIHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			try {
				int result = Integer.parseInt(msg.obj.toString());
				myLocation = CommonActivity.findMyLocation(context);
				if (result == 0) {
					findingLocation(false);
				} else {
					mView.findViewById(R.id.textTitle).setVisibility(
							View.VISIBLE);
					TextView tvTitle = (TextView) mView
							.findViewById(R.id.textTitle);
					tvTitle.setText(R.string.get_location_false);
					mView.findViewById(R.id.lnSearchingLocation).setVisibility(
							View.GONE);
				}
			} catch (Exception e) {
				Log.e("exception when handle result of finding location",
						"xxx", e);
			}
			super.handleMessage(msg);
		}
	}

	private void dismissProgressDialog() {
		if (progress != null && progress.isShowing()) {
			progress.dismiss();
		}
	}

	private void showProgressDialog(Activity mActivity) {
		if (progress == null) {
			progress = new ProgressDialog(mActivity);
			progress.setCancelable(false);
			progress.setMessage(mActivity.getResources().getString(
					R.string.getdataing));
		}

		if (!progress.isShowing()) {
			progress.show();
		}
	}

	ProgressDialog progress;
	private class
	AsyGetListChannelInDay extends
			AsyncTask<String, Void, BOCOutput> {

		private final Activity mActivity;

		private Boolean running = false;

		public AsyGetListChannelInDay(Activity mActivity) {
			this.mActivity = mActivity;
			showProgressDialog(mActivity);
			this.running = true;
		}

		@Override
		protected BOCOutput doInBackground(String... params) {
			return getListChannelInDay();
		}

		@Override
		protected void onPostExecute(BOCOutput result) {
			super.onPostExecute(result);
			dismissProgressDialog();

			this.running = false;

			if (result.getErrorCode().equals("0")
					&& result.getLstSalePointsRouteDTOs() != null
					&& !result.getLstSalePointsRouteDTOs().isEmpty()) {
				mappingStaff(result.getLstSalePointsRouteDTOs());
				arrayStaffCusCareByDay.clear();
				arrayStaffCusCareByDay.addAll(arrayStaff);

				mAdapterStaff = new AdapterStaff(arrayStaff, getActivity(),
						AdapterStaff.TYPE_CUSCARE_BY_DAY);
				lvListStaff.setAdapter(mAdapterStaff);
				mAdapterStaff.notifyDataSetChanged();
			} else {
				arrayStaff = new ArrayList<>();
				if (mAdapterStaff != null) {
					mAdapterStaff.notifyDataSetChanged();
				}

				if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity
							.createAlertDialog(mActivity, result
									.getDescription(), mActivity.getResources()
									.getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {

					String description = result.getDescription();
					if (description != null && description.contains("java.lang.String.length()")) {
						description = mActivity.getString(R.string.server_time_out);
					}

					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), CommonActivity.isNullOrEmpty(description) ? getResources().getString(R.string.no_data) : description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}
		}


		private BOCOutput getListChannelInDay() {
			BOCOutput bocOutput = new BOCOutput();
			String original = "";
			try {
				String methodName = "getSalePointsRouteDay";
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_" + methodName);

//				ArrayList<Staff> arrStaff_ = new ArrayList<>();
//				ChannelDAL channelDAL = new ChannelDAL(context);
//				try {
//					android.location.Location anrLocation = new android.location.Location(
//							"");
//					anrLocation.setLatitude(Double.parseDouble(myLocation
//							.getX()));
//					anrLocation.setLongitude(Double.parseDouble(myLocation
//							.getY()));
//					arrStaff_ = channelDAL.getAllStaff(TABLE_NAME_STAFF,
//							staffType, inputSearchName, anrLocation,
//							Session.userName);
//				} catch (Exception e) {
//					// TODO: handle exception
//				} finally {
//					if (channelDAL != null) {
//						channelDAL.close();
//					}
//				}
//
//				if (arrStaff_.size() == 0) {
//					progress.dismiss();
//					bocOutput = new BOCOutput();
//					bocOutput.setErrorCode(Constant.SUCCESS_CODE);
//					return bocOutput;
//				}

				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:").append(methodName).append(">");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");

//				for (Staff iteam : arrStaff_) {
//					rawData.append("<lstStaffId>").append(iteam.getStaffId()).append("</lstStaffId>");
//				}

				rawData.append("</input>");
				rawData.append("</ws:").append(methodName).append(">");

				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(), "mbccs_"
								+ methodName);
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", response);

				// parser
				Serializer serializer = new Persister();
				bocOutput = serializer.read(BOCOutput.class, original);
				if (bocOutput == null) {
					bocOutput = new BOCOutput();
					bocOutput
							.setDescription(getString(R.string.no_return_from_system));
					bocOutput.setErrorCode(Constant.ERROR_CODE);
					return bocOutput;
				} else {
					return bocOutput;
				}
			} catch (Exception e) {
				Log.e(Constant.TAG, "getListAgentChannelInDay", e);
				bocOutput = new BOCOutput();
				bocOutput.setDescription(e.getMessage());
				bocOutput.setErrorCode(Constant.ERROR_CODE);
			}
			return bocOutput;
		}

		public Boolean getRunning() {
			return running;
		}
	}



//	private void mappingStaff(ArrayList<Staff> lstStaff) {
//		android.location.Location currentLocation = new android.location.Location(
//				"");
//		currentLocation.setLatitude(Double.parseDouble(myLocation.getX()));
//		currentLocation.setLongitude(Double.parseDouble(myLocation.getY()));
//
//		arrayStaff.clear();
//		ChannelDAL channelDAL = new ChannelDAL(context);
//		for (Staff staff : lstStaff) {
//			Staff tmp = channelDAL.getStaffByStaffCode("staff",
//					staff.getStaffCode(), currentLocation);
//			arrayStaff.add(tmp);
//		}
//		channelDAL.close();
//
//		Collections.sort(arrayStaff, new Comparator<Staff>() {
//			@Override
//			public int compare(Staff st1, Staff st2) {
//				// TODO Auto-generated method stub
//				if (st1.getDistance() == null && st2.getDistance() == null) {
//					return st1.getStaffCode().compareTo(st2.getStaffCode());
//				}
//
//				if (st1.getDistance() == null) {
//					return -1;
//				}
//
//				if (st2.getDistance() == null) {
//					return 1;
//				}
//
//				return st1.getDistance().compareTo(st2.getDistance());
//			}
//		});
//
//	}

	private void mappingStaff(List<SalePointsRouteDTO> lstStaff) {
		android.location.Location currentLocation = new android.location.Location(
				"");
		currentLocation.setLatitude(Double.parseDouble(myLocation.getX()));
		currentLocation.setLongitude(Double.parseDouble(myLocation.getY()));

		arrayStaff.clear();
		ChannelDAL channelDAL = new ChannelDAL(context);
		for (SalePointsRouteDTO staff : lstStaff) {
			Staff tmp = channelDAL.getStaffByStaffCode("staff",
					staff.getSalePointCode(), currentLocation);
			if (tmp != null) {
				tmp.setCareNumber(staff.getCareNumber());
				tmp.setSprId(staff.getSprId());
				arrayStaff.add(tmp);
			}

		}
		channelDAL.close();

		Collections.sort(arrayStaff, new Comparator<Staff>() {
			@Override
			public int compare(Staff st1, Staff st2) {
				// TODO Auto-generated method stub
				if (st1.getDistance() == null && st2.getDistance() == null) {
					return st1.getStaffCode().compareTo(st2.getStaffCode());
				}

				if (st1.getDistance() == null) {
					return -1;
				}

				if (st2.getDistance() == null) {
					return 1;
				}

				return st1.getDistance().compareTo(st2.getDistance());
			}
		});

	}

	private final OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			LoginDialog dialog = new LoginDialog(getActivity(),
					";channel.management;");
			dialog.show();

		}
	};

	private boolean isStaffByDay(Staff staff) {
		Log.d(TAG, "isStaffByDay with staffCode = " + staff.getStaffCode());

		if (arrayStaffCusCareByDay != null) {
			for (Staff sf : arrayStaffCusCareByDay) {
				if (staff.getStaffCode().equalsIgnoreCase(sf.getStaffCode())) {
					staff.setSprId(sf.getSprId());
					staff.setCareNumber(sf.getCareNumber());
					return true;
				}
			}
		} else {
			Log.d(TAG, "arrayStaffCusCareByDay is null.");
		}
		return false;
	}

	private class AsyMark2SellingContactHis extends
			AsyncTask<String, Void, BOCOutput> {

		private final Activity mActivity;
		final ProgressDialog progress;

		public AsyMark2SellingContactHis(Activity mActivity) {

			this.mActivity = mActivity;

			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}

		}

		@Override
		protected BOCOutput doInBackground(String... params) {
			return insertSalePointsCare();
		}

		@Override
		protected void onPostExecute(BOCOutput result) {
			super.onPostExecute(result);
			progress.dismiss();

			if ("0".equals(result.getErrorCode())) {
				Intent intent = new Intent(getActivity(),
						FragmentCusCareByDay.class);
				intent.putExtra(Define.MARK_SELLING_CONTACT_HIS_ID,
						result.getSpchId());
				intent.putExtra(Define.KEY_STAFF, mStaff);

				startActivity(intent);

			} else {
				if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity
							.createAlertDialog(mActivity, result
									.getDescription(), mActivity.getResources()
									.getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					String description = result.getDescription();
					if (description != null && description.contains("java.lang.String.length()")) {
						description = mActivity.getString(R.string.server_time_out);
					}
					if (!CommonActivity.isNullOrEmpty(result.getDescription())) {
						Dialog dialog = CommonActivity.createAlertDialog(
								getActivity(), description,
								getResources().getString(R.string.app_name));
						dialog.show();
					} else {
						Dialog dialog = CommonActivity.createAlertDialog(
								getActivity(), mActivity.getResources()
										.getString(R.string.app_name),
								getResources().getString(R.string.app_name));
						dialog.show();
					}
				}
			}
		}

		private BOCOutput insertSalePointsCare() {
			BOCOutput bocOutput = new BOCOutput();
			String original = "";
			try {
				String methodName = "insertSalePointsCare";
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_" + methodName);

				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:").append(methodName).append(">");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<salePointCode>").append(mStaff.getStaffCode()).append("</salePointCode>");
				rawData.append("<statusCare>").append(Constant.BOC2.STATUS_CARING).append("</statusCare>");
				rawData.append("<sprId>").append(mStaff.getSprId()).append("</sprId>");
				rawData.append("</input>");
				rawData.append("</ws:").append(methodName).append(">");

				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(), "mbccs_"
								+ methodName);
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", response);

				// parser
				Serializer serializer = new Persister();
				bocOutput = serializer.read(BOCOutput.class, original);
				if (bocOutput == null) {
					bocOutput = new BOCOutput();
					bocOutput
							.setDescription(getString(R.string.no_return_from_system));
					bocOutput.setErrorCode(Constant.ERROR_CODE);
					return bocOutput;
				} else {
					return bocOutput;
				}
			} catch (Exception ex) {
				Log.e("Exception insertSalePointsCare", ex.getMessage(), ex);
				bocOutput.setDescription(CommonActivity.getDescription(
						getActivity(), ex));
				bocOutput.setErrorCode(Constant.ERROR_CODE);
			}
			return bocOutput;
		}

	}

}
