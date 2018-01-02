package com.viettel.bss.viettelpos.v4.channel.activity;

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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.channel.adapter.AdapterFilterChannel;
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
import com.viettel.bss.viettelpos.v4.login.object.Manager;
import com.viettel.bss.viettelpos.v4.login.object.Staff;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.work.activity.FragmentManagePolicy;
import com.viettel.maps.MapView;
import com.viettel.maps.base.LatLng;
import com.viettel.maps.layers.MapLayer;
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

public class FragmentListChannel_fromNoti extends Fragment

        implements Define, OnScrollListener, OnItemClickListener, TextWatcher,
        OnClickListener {

    private ArrayList<Staff> arrayStaff = new ArrayList<>();
    private ArrayList<Staff> arrayStaffCacheClient = new ArrayList<>();
    private ArrayList<Staff> arrayStaffCacheServer = new ArrayList<>();
    private final String selectType = "";
    private ArrayList<ChannelTypeOJ> arrChannelType;
    private ListView lvListStaff;
    private Spinner spChannelType;

    private AdapterStaff mAdapterStaff;
    private ChannelTypeDAL channelTypeDAL;
    private boolean loadmore = false;
    private boolean loadOk = false;

    private String inputSearchName = null;
    private LoadStaffByChannelType mLoadStaffByChannelType;
    // public View footer;
    private EditText edtSearchChannel;
    // public Boolean flagSearch = false;
    private View mView;
    private Long staffType = 0L;

    private Location myLocation;

    private ViewFlipper mViewFlipper;
    private MapView mapView;
    private LoadmoreStaff loadmoreStaff;
    private FillToMap fillToMap;
    // public static String userName;
    private boolean ktMap = false;
    public Spinner spSortBy;
    private final String TABLE_NAME = "staff";
    public TextView smsListStaff;
    public String TAG = "Map test";
    private Staff staffSelect = null;
    public String staffCodeMarker = null;
    private int pos = 0;
    Boolean mager = true;
    private Context context;

    public FragmentListChannel_fromNoti(boolean finished) {
        super();
        this.finished = finished;
    }

    private void initOnResume() {
//		btnHome.setVisibility(View.GONE);
        mapView = (MapView) getActivity().findViewById(R.id.idMapViewStaff);

        mapView.setMarkerClickListener(new MapLayer.OnClickListener() {

            @Override
            public boolean onClick(Point point, LatLng pos, MapObject obj) {
                Marker marker = (Marker) obj;
                InfoWindowOptions opts = new InfoWindowOptions(marker
                        .getTitle(), marker.getPosition());
                opts.snippet(marker.getDescription());
                opts.anchor(new Point(0, -marker.getIcon().getHeight()));
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
                ReplaceFragment.replaceFragment(getActivity(),
                        mListMenuManager, false);
                return true;
            }

        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity.getInstance().setTitleActionBar(R.string.job_manager_5);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
            case R.id.btnMap:
                showTapMap();
                CommonActivity.hideKeyboard(edtSearchChannel, getActivity());
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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

    private static int myPos = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("TAG", "onCreateView FragmentListChannel");

        // if (mView == null) {
        // mView = inflater.inflate(R.layout.layout_list_staff, container,
        // false);
        // Unit(mView);
        if (arrayStaff.size() == 0) {
            Log.e("TAG", " KHOI TAO LAI GIAO DIEN " + arrayStaff.size());
            mView = inflater.inflate(R.layout.layout_list_staff, container,
                    false);
            Unit(mView);
            // myLocation = getMyLocation();
            // loadmoreStaff = new LoadmoreStaff();
            fillToMap = new FillToMap();
            if (arrayStaff != null) {
                arrayStaff.clear();
            }

        }
        // }
        // if (arrayStaff.size() == 0) {
        // ChannelTypeOJ channelTypeOJ = arrChannelType.get(myPos);
        //
        // staffType = channelTypeOJ.getType_channel_id();
        if (staffType == -1) {
            staffType = 0L;

        }
        pos = 0;
        loadmore = true;
        loadOk = true;
        // mLoadStaffByChannelType = new LoadStaffByChannelType();
        // mLoadStaffByChannelType.execute();
        // lvListStaff.removeFooterView(footer);
        // lvListStaff.addFooterView(footer, null, false);
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
        initOnResume();

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

    private Boolean backPress = false;

    private void Unit(View v) {

        try {
            mViewFlipper = (ViewFlipper) v.findViewById(R.id.vfListAndMap);
            this.context = getActivity();
            if (myLocation != null) {
                Log.e("TAG", "TOA DO X" + myLocation.getLatitude());
                Log.e("TAG", "TOA DO Y" + myLocation.getLongitude());
            }

            lvListStaff = (ListView) v.findViewById(R.id.lv_staff);
            Log.e(tag, (lvListStaff == null)
                    + "----------list staff null ----unit");
            arrayStaff = new ArrayList<>();
            // smsListStaff = (TextView) v.findViewById(R.id.smsListStaff);
            if (arrayStaff.size() > 0) {
                mAdapterStaff = new AdapterStaff(arrayStaff, getActivity(),
                        AdapterStaff.TYPE_LOCATION);
            }

            LayoutInflater inflater = getActivity().getLayoutInflater();
            // footer = inflater.inflate(R.layout.footer_layout, null, false);
            // lvListStaff.addFooterView(footer, null, false);
            lvListStaff.setAdapter(mAdapterStaff);
            lvListStaff.setOnScrollListener(this);
            lvListStaff.setOnItemClickListener(this);

			/*
             * Khoi tao spin loc kenh theo loai kenh
			 */
            channelTypeDAL = new ChannelTypeDAL(getActivity());

            spChannelType = (Spinner) v.findViewById(R.id.sp_list_channel_type);
            arrChannelType = new ArrayList<>();

            arrChannelType = channelTypeDAL.getTasksToArray();
            channelTypeDAL.close();
            Log.e(tag, arrChannelType.size() + ".....channel type size");
            arrChannelType.remove(0);

            ChannelTypeAdapter adChannelType = new ChannelTypeAdapter(
                    getActivity(), arrChannelType);
            spChannelType.setAdapter(adChannelType);

            spChannelType
                    .setOnItemSelectedListener(new OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> adapterView,
                                                   View view, int position, long id) {
                            myPos = position;
                            ChannelTypeOJ channelTypeOJ = arrChannelType
                                    .get(position);
                            staffType = channelTypeOJ.getType_channel_id();
                            if (staffType == 0L) {
                                arrayStaffCacheClient.clear();
                                arrayStaffCacheServer.clear();
                            }
                            if (channelTypeOJ.getType_channel_id() != -1L) {
                                Log.e("TAG", "TIM THEO LOAI KENH");
                                if (arrayStaff != null) {
                                    arrayStaff.clear();
                                }

                                pos = 0;
                                loadmore = true;
                                loadOk = true;
                                // if (count > 0) {
                                // mLoadStaffByChannelType = new
                                // LoadStaffByChannelType();
                                // mLoadStaffByChannelType.execute();
                                // }

                                // lvListStaff.removeFooterView(footer);
                                // lvListStaff.addFooterView(footer, null,
                                // false);
                                mLoadStaffByChannelType = new LoadStaffByChannelType();
                                mLoadStaffByChannelType.execute();
                            }

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

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    if (!backPress) {

                        // lvListStaff.removeFooterView(footer);
                        // lvListStaff.addFooterView(footer, null, false);
                    } else {
                        backPress = false;
                    }
                    inputSearchName = edtSearchChannel.getText().toString();
                    Log.i("Inputtex", "Input texx :: " + inputSearchName);
                    Log.e("Loc kenh ", " +++++++++++ Tim kiem kenh ++++++++++");
                    arrayStaff.clear();
                    arrayStaffCacheClient.clear();
                    arrayStaffCacheServer.clear();
                    pos = 0;
                    loadmore = true;
                    loadOk = true;
                    mLoadStaffByChannelType = new LoadStaffByChannelType();
                    mLoadStaffByChannelType.execute();

                }

            });

            LinearLayout layout = (LinearLayout) v.findViewById(R.id.linsortBy);
            layout.setVisibility(View.GONE);
            final ArrayList<Manager> arrayListManagerSpin = new ArrayList<>();
            arrayListManagerSpin.add(new Manager(R.drawable.ic_icon_macdinh,
                    getResources().getString(R.string.defaultFillter), 0,
                    Constant.Fillter.defaultFillter));
            arrayListManagerSpin.add(new Manager(R.drawable.ic_icon_macdinh,
                    getResources().getString(R.string.spinner_show), 0,
                    Constant.Fillter.spinner_show));
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
            AdapterFilterChannel adSort = new AdapterFilterChannel(
                    getActivity(), arrayListManagerSpin);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final String tag = "list channel noti";

    public class LoadmoreStaff extends AsyncTask<Void, Void, ArrayList<Staff>> {
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
                    // lvListStaff.removeFooterView(footer);
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

    public class FillToMap extends AsyncTask<Void, Void, ArrayList<Staff>> {
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
                            inputSearchName, myLocation, Session.userName,
                            taskTypeId, finished);
                } catch (Exception e) {
                    return new ArrayList<>();
                } finally {
                    if (channelDAL != null) {
                        channelDAL.close();
                    }
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
                    // lvListStaff.removeFooterView(footer);
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

    private boolean finished = false;
    private final int taskTypeId = Constant.POLICY_TASK_TYPE;

    /**
     * Fill channel type
     */
    public class LoadStaffByChannelType extends
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
                result = channelDAL.getTasksToArray_(staffType,
                        inputSearchName, myLocation, Session.userName,
                        taskTypeId, finished);
            } catch (Exception ignored) {
            } finally {
                if (channelDAL != null) {
                    channelDAL.close();
                }
            }
            return result;

        }

        @Override
        protected void onPostExecute(final ArrayList<Staff> result) {
            try {
                if (result != null) {

                    Log.e(tag, "result ok_..............." + result.size());
                    if (arrayStaff.size() > 0) {
                        arrayStaff.removeAll(arrayStaff);
                    } else {

                    }
                    if (result.size() == 0) {
                        Log.e(tag, count++ + "..............----count");
                        mAdapterStaff = new AdapterStaff(arrayStaff,
                                getActivity(), AdapterStaff.TYPE_LOCATION);
                        Log.e(tag, (lvListStaff == null)
                                + "----------list staff null");
                        lvListStaff.setAdapter(mAdapterStaff);
                        CommonActivity.createAlertDialog(getActivity(),
                                R.string.no_data, R.string.app_name).show();
                        edtSearchChannel.setEnabled(false);
                        spChannelType.setEnabled(false);
                        return;
                    }
                    edtSearchChannel.setEnabled(true);
                    spChannelType.setEnabled(true);
                    // arrayStaff = result;
                    // Collections.addAll(arrayStaff, result);

                    for (int i = 0; i < result.size(); i++) {

                        Staff staff = result.get(i);
                        Log.e("TAG", "::" + staff.getNameStaff());
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
                        Log.e(tag, arrayStaff.size()
                                + ".................arr staff size 0");
                    }
                    Log.e(tag, arrayStaff.size()
                            + ".................arr staff size 1");
                    // mAdapterStaff.notifyDataSetChanged();
                    mAdapterStaff = new AdapterStaff(arrayStaff, getActivity(),
                            AdapterStaff.TYPE_LOCATION);
                    Log.e(tag, (lvListStaff == null)
                            + "----------list staff null");
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
                loadmore = loadOk;
            }
        }
    };

    // public Runnable runnableUdapteAdapterLoadByType = new Runnable() {
    // @Override
    // public void run() {
    // mAdapterStaff.notifyDataSetChanged();
    //
    // }
    // };
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        int lastitemScreen = firstVisibleItem + visibleItemCount;
        Log.e("Scroll",
                " --------------- Thuc hien scrollllllllllllllll ------------");

        // if ((lastitemScreen == totalItemCount) && (loadmore == false)
        // && (backPress == false)) {
        // Log.i("firstVisibleItem", "firstVisibleItem = " + firstVisibleItem);
        // Log.i("visibleItemCount", "visibleItemCount = " + visibleItemCount);
        // Log.i("totalItemCount", "totalItemCount = " + totalItemCount);
        // Log.i("lastitemScreen", "lastitemScreen = " + lastitemScreen);
        // Log.i("loadmore", "loadmore = " + loadmore);
        //
        // loadmoreStaff = new LoadmoreStaff();
        // loadmoreStaff.execute();
        // loadmore = true;
        // }

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        Activity act = getActivity();
        if (!CommonActivity.isNetworkConnected(act)) {
            CommonActivity.createAlertDialog(act, R.string.errorNetwork,
                    R.string.app_name).show();
            return;
        }
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(KEY_STAFF, arrayStaff.get(arg2));
        mBundle.putSerializable("FLAG", false);
        FragmentManagePolicy mListMenuManager = new FragmentManagePolicy();
        // ReplaceFragment.replaceFragmentSlidingMenu(getActivity(),
        // new FragmentListChannel_work(), true);
        mListMenuManager.setArguments(mBundle);
        ReplaceFragment.replaceFragment(getActivity(), mListMenuManager, false);

        CommonActivity.hideKeyboard(edtSearchChannel, getActivity());
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
//			FragmentMenuHome fragmentMenuHome = new FragmentMenuHome();
//			ReplaceFragment.replaceFragment(getActivity(), fragmentMenuHome,
//					true);
            default:
                break;
        }
    }

    /**
     * Fill channel type
     */
    public class SortAsynctask extends AsyncTask<String, Void, String> {
        final BCCSGateway input = new BCCSGateway();
        String response = null;
        String original = null;
        final XmlDomParse parse = new XmlDomParse();
        final Activity activity;
        String sms = "";
        String errorMessage = "";
        final ProgressDialog progress;

        public SortAsynctask(Activity _activity) {
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
                        Constant.BCCS_GW_URL, getActivity(),
                        "mbccs_viewFrequencyVisited");

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
                    return output.getErrorCode();
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
                                    if (!staffCode.equals(Session.userName)) {
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
                                        if (myLocation != null) {
                                            distance = myLocation
                                                    .distanceTo(locationStaff);
                                        }

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
                        break;
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

    public class SortBySaleAsynctask extends AsyncTask<String, Void, String> {
        final BCCSGateway input = new BCCSGateway();
        final String original = null;
        String errorMessage = "";
        final XmlDomParse parse = new XmlDomParse();
        final Activity activity;
        final ProgressDialog progress;

        public SortBySaleAsynctask(Activity _activity) {
            Log.e("TAG", "SAP SEP THEO DOANH THU");
            this.activity = _activity;
            this.progress = new ProgressDialog(getActivity());
            this.progress.setMessage(getActivity().getResources().getString(
                    R.string.waitting));
            this.progress.setCancelable(false);
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
                        Constant.BCCS_GW_URL, getActivity(),
                        "mbccs_getListStaffOrder");

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
                    return output.getErrorCode();
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
                        ChannelDAL channelDAL = new ChannelDAL(context);
                        try {
                            Document doc = parse.getDomElement(result);
                            NodeList nl = doc.getElementsByTagName("return");

                            NodeList nodelstchild = null;

                            com.viettel.bss.viettelpos.v4.object.Location myLocation = CommonActivity
                                    .findMyLocation(getActivity());
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

                                    if (!staffCode.equals(Session.userName)) {
                                        Location location = new Location("");
                                        location.setLatitude(Double
                                                .parseDouble(myLocation.getX()));
                                        location.setLongitude(Double
                                                .parseDouble(myLocation.getY()));
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
                        } finally {
                            channelDAL.close();
                        }
                        break;
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
            menuItemMap
                    .setIcon(R.drawable.button_list_background);
            ktMap = true;
            MapConfig.changeSRS(SRSType.SRS_4326);
            fillToMap = new FillToMap();
            if (myLocation != null) {

                fillToMap.execute();
            }

        } else {

            menuItemMap
                    .setIcon(R.drawable.background_button_map);
            ktMap = false;
        }

        mViewFlipper.showNext();
    }
    private int count;
}
