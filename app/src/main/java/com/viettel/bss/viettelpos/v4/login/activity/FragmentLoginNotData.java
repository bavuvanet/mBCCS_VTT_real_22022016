package com.viettel.bss.viettelpos.v4.login.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.adapter.SurveyAnwserAdapter;
import com.viettel.bss.viettelpos.v4.channel.activity.FragmentListChannel_fromNoti;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.FragmentUtils;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.SurveySubmit;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.NotifyBean;
import com.viettel.bss.viettelpos.v4.customer.manage.FragmentSearchCV;
import com.viettel.bss.viettelpos.v4.customer.object.BOCOutput;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.fragment.WarningDetailFragment;
import com.viettel.bss.viettelpos.v4.login.object.Manager;
import com.viettel.bss.viettelpos.v4.object.MenuAction;
import com.viettel.bss.viettelpos.v4.object.MenuActionMain;
import com.viettel.bss.viettelpos.v4.object.Survey;
import com.viettel.bss.viettelpos.v4.object.SurveyAnswer;
import com.viettel.bss.viettelpos.v4.object.SurveyQuestion;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentListOrder;
import com.viettel.bss.viettelpos.v4.sale.object.GetOrderObject;
import com.viettel.bss.viettelpos.v4.service.DatabaseService;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.task.AsynTaskIsGiftChooseValid;
import com.viettel.bss.viettelpos.v4.video.FragmentManageVideo;
import com.viettel.bss.viettelpos.v4.work.activity.FragmentDetailWorkManager;
import com.viettel.bss.viettelpos.v4.work.activity.FragmentUpdateWork;
import com.viettel.bss.viettelpos.v4.work.adapter.adapterManagerWork;
import com.viettel.bss.viettelpos.v4.work.dal.DalPolicy;
import com.viettel.bss.viettelpos.v4.work.dal.JobDal;
import com.viettel.bss.viettelpos.v4.work.object.InfoJobNew;
import com.viettel.bss.viettelpos.v4.work.object.InfoNewManager;
import com.viettel.bss.viettelpos.v4.work.object.ObjectCount;

import org.json.JSONArray;
import org.json.JSONObject;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tourguide.tourguide.Overlay;
import tourguide.tourguide.Pointer;
import tourguide.tourguide.ToolTip;
import tourguide.tourguide.TourGuide;


@SuppressLint("NewApi")
public class FragmentLoginNotData extends Fragment implements
        OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    // private Button btnHome;

    private static final String TAG = FragmentLoginNotData.class.getSimpleName();
    public static final String TYPE_RADIO_BUTTON = "1";
    public static final String TYPE_CHECKBOX = "2";
    private static final String TYPE_TEXT_INPUT = "3";
    public static final String TYPE_COMBOBOX = "4";
    private static final String TYPE_TEXT_AREA = "5";

    private ListView lvNewFeed;
    private ArrayList<Manager> arrayListManager;
    private adapterManagerWork mAdapterManager;
    private ProgressBar prbSearching;
    private Button btnChannelAction;
    private int countOrderItem = 0;
    public int countJob = 0;
    private int countPolicy = 0;
    private InfoJobNew inJobNew = new InfoJobNew();
    private InfoNewManager infoNewManager = new InfoNewManager();
    private static final String VSA_VIEW_NOTIFY = "view.notify";
    // canh bao ho so

    private LinearLayout lnthongtinhoso;
    private TextView txthoso;
    private ProgressBar prbProfile;
    private final String logTag = FragmentLoginNotData.class.getName();
    public static View mView;
    private final Object lock = new Object();
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isRefreshing = false;
    private ArrayList<Manager> tmp;

    @BindView(R.id.fabActionMenu)
    FloatingActionMenu fabActionMenu;
//    @BindView(R.id.fab1)
//    FloatingActionMenu fab1;
//    @BindView(R.id.fab2)
//    FloatingActionMenu fab2;
//    @BindView(R.id.fab3)
//    FloatingActionMenu fab3;

    private MenuActionMain menuActionMain = null;
    private int valueMenu = 3;
    private ArrayList<MenuAction> lstMenuAction = new ArrayList<>();

    @BindView(R.id.lnChannelManager)
    LinearLayout lnChannelManager;
    DatabaseService databaseService;

    //[BaVV] Add Tooltip Start
    public TourGuide mTutorialHandler;
    public TourGuide mTutorialHandler2;
    @BindView(R.id.imvAddTemp)
    ImageView imvAddTemp;
    @BindView(R.id.imvSkipGuide)
    ImageView imvSkipGuide;
    //[BaVV] Add Tooltip End

    @Override
    public void onAttach(Activity activity) {
        Log.d("TAG", "onAttach FragmentLoginNotData");

        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("TAG", "onCreateView FragmentLoginNotData");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (mView == null) {
            mView = inflater.inflate(R.layout.layout_channels_manager,
                    container, false);

            ButterKnife.bind(this, mView);
            databaseService = new DatabaseService();
            Unit(mView);


        }

        return mView;
    }

    //[BaVV] Add Tooltip Start
    public void cleanTourGuide() {
        if(null != mTutorialHandler) {
            mTutorialHandler.cleanUp();
        }
    }
    //[BaVV] Add Tooltip End

    private void initMenuActionMain() {

        try {
            menuActionMain = new MenuActionMain(getContext(), Session.userName);
            lstMenuAction = menuActionMain.getFiveValueMenusWithUserId(Session.userName, valueMenu);

//        if(CommonActivity.isNullOrEmptyArray(lstMenuAction)) {
//            initMenuTest();
//        }

            if (!CommonActivity.isNullOrEmptyArray(lstMenuAction)) {
                if (fabActionMenu == null) {
                    fabActionMenu = (FloatingActionMenu) mView.findViewById(R.id.fabActionMenu);
                }

                fabActionMenu.setVisibility(View.VISIBLE);
                fabActionMenu.removeAllMenuButtons();

                //[BaVV] Add Tooltip Start

                ToolTip toolTip = new ToolTip()
                        .setTitle("")
                        .setDescription("Bấm chọn để hiển thị các chức năng mới sử dụng")
                        .setGravity(Gravity.TOP);

                mTutorialHandler = TourGuide.init(getActivity()).with(TourGuide.Technique.Click)
                        .motionType(TourGuide.MotionType.AllowAll)
//                        .setPointer(new Pointer())
                        .setToolTip(toolTip)
                        .setOverlay(new Overlay().disableClick(false))
                        .playOn(imvAddTemp);

                fabActionMenu.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
                    @Override
                    public void onMenuToggle(boolean opened) {
                        if(null != mTutorialHandler) {
                            mTutorialHandler.cleanUp();
                        }
                        ((MainActivity) getActivity()).cleanTourGuide();
                    }
                });

                //[BaVV] Add Tooltip End

                for (final MenuAction menuAction : lstMenuAction) {

                    try {
                        FloatingActionButton floatingActionButton = new FloatingActionButton(getContext());
                        floatingActionButton.setLabelText(menuAction.getNameMenu());
                        floatingActionButton.setColorNormal(getResources().getColor(R.color.background));
                        floatingActionButton.setColorPressed(getResources().getColor(R.color.colorPrimary));
                        floatingActionButton.setButtonSize(1);
                        fabActionMenu.addMenuButton(floatingActionButton);

                        if (menuAction.getIdIcon() != 0) {
//                        Drawable drawable = VectorDrawableCompat.create(getResources(), menuAction.getIdIcon(), getActivity().getTheme());
//                        floatingActionButton.setImageDrawable(drawable);
                            floatingActionButton.setImageResource(menuAction.getIdIcon());
                        }

                        floatingActionButton.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                FragmentUtils fragmentUtils = new FragmentUtils(getActivity());
                                fragmentUtils.replaceFragment(menuAction.getIdMenu());
                                fabActionMenu.toggle(false);
                            }
                        });
                    } catch (Exception e) {
                        Log.d("exception", e.toString());
                    }


                }
            } else {
                if (fabActionMenu != null) {
                    fabActionMenu.setVisibility(View.GONE);
                }

            }
        } catch (Exception ex){
            Log.d(TAG, "Error", ex);
        }
    }

    private void initMenuTest() {
        lstMenuAction.clear();

        MenuAction menuAction = new MenuAction();
        menuAction.setNameMenu(getResources().getString(R.string.sale_search_serial_card));
        menuAction.setUrlMenu("SALE_SEARCH_SERIAL_CARD");
        menuAction.setIdMenu("SALE_SEARCH_SERIAL_CARD");
        menuAction.setIdIcon(R.drawable.ic_2g_3g);


        lstMenuAction.add(menuAction);
        menuAction = new MenuAction();
        menuAction.setNameMenu(getResources().getString(R.string.tichduyetnoptien));
        menuAction.setUrlMenu("APPROVE_MONEY");
        menuAction.setIdMenu("APPROVE_MONEY");
        menuAction.setIdIcon(R.drawable.ic_icon_macdinh);
        lstMenuAction.add(menuAction);
    }

    private void Unit(View v) {

		new AsynTaskIsGiftChooseValid(getActivity(), new OnPostExecuteListener<String>() {
			@Override
			public void onPostExecute(String result, String errorCode, String description) {
				if(!CommonActivity.isNullOrEmpty(result)) {
					SharedPreferences preferences = getActivity().getSharedPreferences(Define.PRE_NAME, Activity.MODE_PRIVATE);
					String name = preferences.getString(Define.KEY_MENU_NAME, "");
                    String[] arrPermission = result.split(";");
					for(String permission : arrPermission){
						name.replace(";" + permission + ";", ";;");
					}

					SharedPreferences.Editor editor = preferences.edit();
					editor.putString(Define.KEY_MENU_NAME, name);
                    editor.commit();

					synchronized (lock) {
						if(arrayListManager == null){
							arrayListManager = new ArrayList<Manager>();
						}

						arrayListManager.add(new Manager(
								R.drawable.ic_launcher, MainActivity
								.getInstance()
								.getResources()
								.getString(
										R.string.txt_warning_gift),
								Integer.parseInt(infoNewManager.getAllTask()),
								Integer.parseInt(infoNewManager.getFailTask()),
								Integer.parseInt(infoNewManager
										.getWarningTask()),
								Define.MENU_SALE_JOB_INFO_MANAGER));

						Manager manager = new Manager();
						manager.setResIcon(R.drawable.ic_launcher);
						manager.setHeader(MainActivity.getInstance().getResources()
								.getString(R.string.txt_warning_gift));
						manager.setContent(description);
						manager.setKeyMenuName(Define.NOTIFY);

						mAdapterManager.notifyDataSetChanged();
					}
				}
			}
		}, moveLogInAct).execute();

        LinearLayout lnsupport = (LinearLayout) v.findViewById(R.id.lnsupport);
        lnsupport.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try {
                    OnClickListener onclickCall = new OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            String uri = "tel:" + Constant.phoneNumber;
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            intent.setData(Uri.parse(uri));
                            startActivity(intent);

                        }
                    };
                    // Toast.makeText(context, context.getString(R.string.nhanline2),
                    // Toast.LENGTH_LONG).show();
//		CommonActivity.createDialog(context,
//				context.getString(R.string.nhanline2),
//				context.getString(R.string.app_name),
//				context.getString(R.string.ok),
//				context.getString(R.string.cancel), onclickCall, null).show();
                    CommonActivity.createDialog(MainActivity.getInstance(),
                            MainActivity.getInstance().getResources().getString(R.string.confirmspport2),
                            MainActivity.getInstance().getString(R.string.app_name),
                            MainActivity.getInstance().getString(R.string.cancel),
                            MainActivity.getInstance().getString(R.string.ok), null, onclickCall).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // TODO Auto-generated method stub

            }
        });

        //[BaVV] Add Tooltip Start

        ToolTip toolTip2 = new ToolTip()
                .setTitle("")
                .setDescription("KHÔNG HIỆN LẠI LẦN SAU")
                .setGravity(Gravity.TOP)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mTutorialHandler2.cleanUp();
                        CommonActivity.createDialog(getActivity(),
                                R.string.confirm_repeat_tooltip, R.string.title_tooltip,
                                R.string.cancel, R.string.ok, null, null).show();
                    }
                });

        mTutorialHandler2 = TourGuide.init(getActivity()).with(TourGuide.Technique.Click)
                .motionType(TourGuide.MotionType.AllowAll)
                .setToolTip(toolTip2)
                .playOn(imvSkipGuide);

        //[BaVV] Add Tooltip End

        lvNewFeed = (ListView) v.findViewById(R.id.lvNewFeed);
        btnChannelAction = (Button) v.findViewById(R.id.btnGoHome);
        arrayListManager = new ArrayList<>();

        mAdapterManager = new adapterManagerWork(arrayListManager,
                MainActivity.getInstance());
        lvNewFeed.setAdapter(mAdapterManager);
        lvNewFeed.setOnItemClickListener(this);

        prbSearching = (ProgressBar) v.findViewById(R.id.prb_searching);
        prbSearching.setVisibility(View.GONE);

        swipeRefreshLayout = (SwipeRefreshLayout) v
                .findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.YELLOW,
        // Color.BLUE, Color.YELLOW);

        swipeRefreshLayout.setOnRefreshListener(this);

        lnthongtinhoso = (LinearLayout) v.findViewById(R.id.lnthongtinhoso);
        txthoso = (TextView) v.findViewById(R.id.txthoso);
        prbProfile = (ProgressBar) v.findViewById(R.id.prbProfile);
        lnthongtinhoso.setVisibility(View.GONE);
        lnthongtinhoso.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Bundle bundle = new Bundle();
                FragmentSearchCV fragmentUpdateWork = new FragmentSearchCV();
                bundle.putInt(Define.KEY_JOB, 2);
                fragmentUpdateWork.setArguments(bundle);
                ReplaceFragment.replaceFragment(MainActivity.getInstance(),
                        fragmentUpdateWork, true);
            }
        });

        lnChannelManager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (fabActionMenu.isOpened()) {
                    fabActionMenu.toggle(false);
                }
                return true;
            }
        });

        SharedPreferences preferences = MainActivity.getInstance()
                .getSharedPreferences(Define.PRE_NAME, Activity.MODE_PRIVATE);

        String name = preferences.getString(Define.KEY_MENU_NAME, "");
        lnthongtinhoso.setVisibility(View.GONE);

        if (name.contains(";upload_profile;")) {
            CountRemainRecordAsyn countRemainRecordAsyn = new CountRemainRecordAsyn(
                    getActivity());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                countRemainRecordAsyn.executeOnExecutor(
                        AsyncTask.THREAD_POOL_EXECUTOR, null);
            } else {
                countRemainRecordAsyn.execute();
            }
        }
        //

        if (name.contains(";sale_handle_order;")) {
            if (CommonActivity.isNetworkConnected(MainActivity.getInstance())) {
                GetlistOrderAsynManager getAsynManager = new GetlistOrderAsynManager(
                        MainActivity.getInstance());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    getAsynManager.executeOnExecutor(
                            AsyncTask.THREAD_POOL_EXECUTOR, null);
                } else {
                    getAsynManager.execute();
                }
            } else {
                countOrderItem = 0;
                addManagerList();
                Dialog dialog = CommonActivity.createAlertDialog(
                        MainActivity.getInstance(),
                        MainActivity.getInstance().getResources()
                                .getString(R.string.errorNetwork),
                        MainActivity.getInstance().getResources()
                                .getString(R.string.app_name));
                dialog.show();
            }
        } else {
            GetListTitleNotify getListNotify = new GetListTitleNotify();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                getListNotify.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                        null);
            } else {
                getListNotify.execute();
            }
            addManagerList();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new GetListNotifyAsyn().executeOnExecutor(
                    AsyncTask.THREAD_POOL_EXECUTOR, null);
        } else {
            new GetListNotifyAsyn().execute();
        }


        // Kiem tra VSA de gan survey
        if (name.contains(";mbccs.survey;")) {

            // Kiem tra thoi gian va trang thai lay survey
            String nowDate = DateTimeUtils.convertDateTimeToString(new Date(),
                    "dd/MM/yyyy");
            String surveyDate = preferences.getString(Define.KEY_SURVEY_DATE,
                    "");
            Boolean getSurvey = false;
            if (nowDate.equals(surveyDate)) {
                String required = preferences.getString(
                        Define.KEY_SURVEY_REQUIRED, "");
                if ("1".equals(required)) {
                    getSurvey = true;
                }
            } else {
                getSurvey = true;
            }

            if (getSurvey) {
                GetSurvey getSurveyAsy = new GetSurvey(getActivity());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    getSurveyAsy.executeOnExecutor(
                            AsyncTask.THREAD_POOL_EXECUTOR, null);
                } else {
                    getSurveyAsy.execute();
                }
            }

        }

//        initMenuActionMain();

    }

    private void addManagerList() {

        SharedPreferences preferences = MainActivity.getInstance()
                .getSharedPreferences(Define.PRE_NAME, Activity.MODE_PRIVATE);

        String name = preferences.getString(Define.KEY_MENU_NAME, "");
        if (name.contains(";pos_work_info;")) {
            JobDal jobDal1 = new JobDal(MainActivity.getInstance());
            try {
                jobDal1.open();
                infoNewManager = jobDal1.getListNotifyManager();
                if (Integer.parseInt(infoNewManager.getAllTask()) > 0
                        || Integer.parseInt(infoNewManager.getFailTask()) > 0
                        || Integer.parseInt(infoNewManager.getWarningTask()) > 0) {
                    synchronized (lock) {
                        arrayListManager.add(new Manager(
                                R.drawable.iconcongviecduocgiao, MainActivity
                                .getInstance()
                                .getResources()
                                .getString(
                                        R.string.text_channel_manager),
                                Integer.parseInt(infoNewManager.getAllTask()),
                                Integer.parseInt(infoNewManager.getFailTask()),
                                Integer.parseInt(infoNewManager
                                        .getWarningTask()),
                                Define.MENU_SALE_JOB_INFO_MANAGER));
                        mAdapterManager.notifyDataSetChanged();
                    }
                }

            } catch (Exception e) {

                e.printStackTrace();
            } finally {
                if (jobDal1 != null) {
                    jobDal1.close();
                }
            }

        }
        if (name.contains(";channel.management;")) {
            btnChannelAction.setVisibility(View.VISIBLE);
        } else {
            btnChannelAction.setVisibility(View.GONE);
        }

//		arrayListManager
//				.add(new Manager(
//						R.drawable.iconpheduyetdonhang,
//						MainActivity
//								.getInstance()
//								.getResources()
//								.getString(
//										R.string.viewvideo),
//						countOrderItem, 0, 0,
//						Define.PLAY_VIDEO));
        mAdapterManager.notifyDataSetChanged();

        if (name.contains(";sale_handle_order;")) {
            if (countOrderItem > 0) {
                synchronized (lock) {
                    arrayListManager
                            .add(new Manager(
                                    R.drawable.iconpheduyetdonhang,
                                    MainActivity
                                            .getInstance()
                                            .getResources()
                                            .getString(
                                                    R.string.text_2_layout_channel_manager),
                                    countOrderItem, 0, 0,
                                    Define.MENU_SALE_ORDER_INFO));
                    mAdapterManager.notifyDataSetChanged();
                }

            }
        }

        if (name.contains(";work_communication;")) {
            DalPolicy dalPolicy = new DalPolicy(MainActivity.getInstance());
            try {
                dalPolicy.open();
                // countPolicy = dalPolicy.getcountPolicy();
                // if (countPolicy > 0) {
                // arrayListManager.add(new Manager(
                // R.drawable.iconthongtinchinhsach,
                // MainActivity.getInstance().getResources().getString(
                // R.string.text_3_layout_channel_manager),
                // countPolicy, 0, 0, Define.MENU_SALE_POLICY_INFO));
                // }
                ObjectCount objectCount = dalPolicy.getcountPolicy_();
                int total = objectCount.getTotal();
                countPolicy = total;
                int sub = objectCount.getSub();
                int subToiHan = objectCount.getSubToiHan();
                if (total > 0) {
                    synchronized (lock) {
                        arrayListManager
                                .add(new Manager(
                                        R.drawable.iconthongtinchinhsach,
                                        MainActivity
                                                .getInstance()
                                                .getResources()
                                                .getString(
                                                        R.string.text_3_layout_channel_manager),
                                        total, sub, subToiHan,
                                        Define.MENU_SALE_POLICY_INFO));
                        mAdapterManager.notifyDataSetChanged();
                    }
                }

                dalPolicy.close();
            } catch (Exception e) {
                try {
                    dalPolicy.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        }
        if (name.contains(";work_update;")) {
            JobDal jobDal = new JobDal(MainActivity.getInstance());
            try {
                jobDal.open();
                inJobNew = jobDal.getListCountJob();
                if (Integer.parseInt(inJobNew.getTotal()) > 0
                        || Integer.parseInt(inJobNew.getBacklog()) > 0
                        || Integer.parseInt(inJobNew.getTerm()) > 0) {
                    synchronized (lock) {
                        arrayListManager
                                .add(new Manager(
                                        R.drawable.iconcongviecduocgiao,
                                        MainActivity
                                                .getInstance()
                                                .getResources()
                                                .getString(
                                                        R.string.text_4_layout_channel_manager),
                                        Integer.parseInt(inJobNew.getTotal()),
                                        Integer.parseInt(inJobNew.getBacklog()),
                                        Integer.parseInt(inJobNew.getTerm()),
                                        Define.MENU_SALE_JOB_INFO));
                        mAdapterManager.notifyDataSetChanged();
                    }
                }
                jobDal.close();
            } catch (Exception e) {
                try {
                    jobDal.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onStart() {
        Log.d("TAG", "onStart FragmentLoginNotData");
        // TODO Auto-generated method stub
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d("TAG", "onResume FragmentLoginNotData");
        super.onResume();
        MainActivity.getInstance().setTitleActionBar(getString(R.string.new_info));
        initMenuActionMain();
    }

    @Override
    public void onStop() {
        Log.d("TAG", "onStop FragmentLoginNotData");
        // TODO Auto-generated method stub
        super.onStop();
    }

    @Override
    public void onPause() {
        Log.d("TAG", "onPause FragmentLoginNotData");
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        Log.d("TAG", "onDestroyView FragmentLoginNotData");
        // TODO Auto-generated method stub
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d("TAG", "onDestroy FragmentLoginNotData");
        // TODO Auto-generated method stub
        if (surveyDialog != null) {
            surveyDialog.cancel();
        }
        super.onDestroy();

    }

    @Override
    public void onDetach() {
        Log.d("TAG", "onDetach FragmentLoginNotData");
        // TODO Auto-generated method stub
        super.onDetach();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Manager manager = arrayListManager.get(position);
        String menukey = arrayListManager.get(position).getKeyMenuName();

//		if (menukey.equals(Define.MENU_SALE_JOB_INFO_MANAGER)) {
//			FragmentDetailWorkManager fraDetailWorkManager = new FragmentDetailWorkManager();
//			ReplaceFragment.replaceFragment(MainActivity.getInstance(),
//					fraDetailWorkManager, true);
//		}

        if (!CommonActivity.isNullOrEmpty(manager.getLstWarningStaffBOs())) {
            WarningDetailFragment fragment = new WarningDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("lstWarningStaffBOs", manager.getLstWarningStaffBOs());
            fragment.setArguments(bundle);

            MainActivity.getInstance().enableViews(true);
            ReplaceFragment.replaceFragmentFromMain(MainActivity.getInstance(), fragment, true);
            return;
        }

        switch (menukey) {
            case Define.MENU_SALE_JOB_INFO_MANAGER:
                FragmentDetailWorkManager fraDetailWorkManager = new FragmentDetailWorkManager();
                ReplaceFragment.replaceFragment(MainActivity.getInstance(),
                        fraDetailWorkManager, true);
                break;

            case Define.MENU_SALE_ORDER_INFO:
                // ==============move FragmentListOrder ===========
                if (countOrderItem > 0) {
                    FragmentListOrder fragmentListOrder = new FragmentListOrder();
                    ReplaceFragment.replaceFragment(MainActivity.getInstance(),
                            fragmentListOrder, true);
                } else {
                    Toast.makeText(
                            MainActivity.getInstance(),
                            MainActivity.getInstance().getResources()
                                    .getString(R.string.notorderitem),
                            Toast.LENGTH_SHORT).show();
                }

                break;
            case Define.MENU_SALE_POLICY_INFO:
                // ===== check menu sale policy ============

                // FragmentPolicyDetail fragmentPolicyDetail = new
                // FragmentPolicyDetail();
                ReplaceFragment.replaceFragment(MainActivity.getInstance(),
                        new FragmentListChannel_fromNoti(false), true);

                break;
            case Define.MENU_SALE_JOB_INFO:
                // ============move fragment update job
                Bundle bundle = new Bundle();
                FragmentUpdateWork fragmentUpdateWork = new FragmentUpdateWork();
                bundle.putInt(Define.KEY_JOB, 2);
                fragmentUpdateWork.setArguments(bundle);
                ReplaceFragment.replaceFragment(MainActivity.getInstance(),
                        fragmentUpdateWork, true);

                break;
            case VSA_VIEW_NOTIFY:
                Manager item = arrayListManager.get(position);
                GetContentNotify asy = new GetContentNotify(
                        MainActivity.getInstance(), new Spin(item.getItemId(), ""));
                asy.execute();
                break;
            case Define.NOTIFY:
                try {
                    ReplaceFragment.replaceFragment(MainActivity.getInstance(),
                            (Fragment) Class.forName(manager.getClassName())
                                    .newInstance(), true);
                } catch (Exception ex) {
                    Log.d("Error replaceFragment: ", ex.getMessage());
                }
                break;
            case Define.PLAY_VIDEO:

                ReplaceFragment.replaceFragment(MainActivity.getInstance(),
                        new FragmentManageVideo(), true);

                break;
        }

        arrayListManager.remove(manager);
        mAdapterManager.notifyDataSetChanged();
    }

    public class GetlistOrderAsynManager extends AsyncTask<Void, Void, Integer> {
        // =================webservice get list order
        private String original;
        final XmlDomParse parse = new XmlDomParse();
        // ProgressDialog progress;

        String description = "";
        String errorCode = "";

        public GetlistOrderAsynManager(Context context) {
            prbSearching.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Void... params) {
            return getlistorder();
        }

        @Override
        protected void onPostExecute(Integer result) {
            lvNewFeed.setVisibility(View.VISIBLE);
            prbSearching.setVisibility(View.GONE);
            // progress.dismiss();
            if (Constant.INVALID_TOKEN2.equals(errorCode) && !MainActivity.getInstance().isFinishing() &&
                    MainActivity.getInstance().hasWindowFocus()
                    && description != null && !description.isEmpty()) {

                Dialog dialog = CommonActivity.createAlertDialog(
                        MainActivity.getInstance(),
                        description,
                        MainActivity.getInstance().getResources()
                                .getString(R.string.app_name), moveLogInAct);
                dialog.show();
            } else {
                countOrderItem = result;
                addManagerList();
                GetListTitleNotify getListNotify = new GetListTitleNotify();
                getListNotify.execute();
            }

        }

        // ===========method get list order object=================
        public Integer getlistorder() {

            ArrayList<GetOrderObject> lisGetOrderObjects = new ArrayList<>();
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getListStockOrderBccs2");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getListStockOrder>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));
                rawData.append("</input>");
                rawData.append("</ws:getListStockOrder>");
                Log.i("RowData", rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope,
                        Constant.BCCS_GW_URL, MainActivity.getInstance(),
                        "mbccs_getListStockOrder");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();

                // ========parse xml ==================

                Document doc = parse.getDomElement(original);
                NodeList nl = doc.getElementsByTagName("return");
                NodeList nodechild = null;
                for (int i = 0; i < nl.getLength(); i++) {
                    Element e2 = (Element) nl.item(i);
                    errorCode = parse.getValue(e2, "errorCode");
                    Log.d("errorCode", errorCode);
                    description = parse.getValue(e2, "description");
                    Log.d("description", description);
                    nodechild = doc.getElementsByTagName("lstStockOrderDTO");
                    return nodechild.getLength();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }

    }

    public class CountRemainRecordAsyn extends AsyncTask<Void, Void, String> {
        // =================webservice get list order
        private final Context context;
        final XmlDomParse parse = new XmlDomParse();
        String description = "";
        String errorCode = "";

        public CountRemainRecordAsyn(Context context) {

            this.context = context;
            lnthongtinhoso.setVisibility(View.GONE);
            prbProfile.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... params) {
            return countRemainRecord();
        }

        @Override
        protected void onPostExecute(String result) {

            prbProfile.setVisibility(View.GONE);
            if (errorCode.equals("0")) {
                if (!"0".equals(description)) {
                    lnthongtinhoso.setVisibility(View.VISIBLE);
                    txthoso.setText(description);
                } else {
                    lnthongtinhoso.setVisibility(View.GONE);
                }
            } else {
                lnthongtinhoso.setVisibility(View.GONE);
                txthoso.setText("");
                if (Constant.INVALID_TOKEN2.equals(errorCode)&& !MainActivity.getInstance().isFinishing()&&
                        MainActivity.getInstance().hasWindowFocus()
                        && description != null && !description.isEmpty()) {

                    Dialog dialog = CommonActivity
                            .createAlertDialog(MainActivity.getInstance(),
                                    description,
                                    MainActivity.getInstance().getResources()
                                            .getString(R.string.app_name),
                                    moveLogInAct);
                    dialog.show();
                }
            }
        }


        // ===========method get list order object=================
        public String countRemainRecord() {

            String original = "";
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_countRemainRecord");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:countRemainRecord>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));
                rawData.append("</input>");
                rawData.append("</ws:countRemainRecord>");
                Log.i("RowData", rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope,
                        Constant.BCCS_GW_URL, MainActivity.getInstance(),
                        "mbccs_countRemainRecord");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();

                // ========parse xml ==================

                Document doc = parse.getDomElement(original);
                NodeList nl = doc.getElementsByTagName("return");
                for (int i = 0; i < nl.getLength(); i++) {
                    Element e2 = (Element) nl.item(i);
                    errorCode = parse.getValue(e2, "errorCode");
                    Log.d("errorCode", errorCode);
                    description = parse.getValue(e2, "description");
                    Log.d("description", description);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return errorCode;
        }

    }

    public class GetListTitleNotify extends AsyncTask<Void, Void, List<Spin>> {
        // =================webservice get list order
        final XmlDomParse parse = new XmlDomParse();
        String description = "";
        String errorCode = "";

        public GetListTitleNotify() {
            prbSearching.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Spin> doInBackground(Void... params) {
            return getListTitleNotify();
        }

        @Override
        protected void onPostExecute(List<Spin> result) {
            prbSearching.setVisibility(View.GONE);
            if (result != null && !result.isEmpty()) {
                synchronized (lock) {
                    for (Spin item : result) {
                        Manager manage = new Manager();
                        manage.setResIcon(R.drawable.truyen_thong_chinh_sach_10);
                        manage.setNameManager(item.getCode());
                        manage.setKeyMenuName(VSA_VIEW_NOTIFY);
                        manage.setItemId(item.getId());
                        arrayListManager.add(manage);
                    }
                    mAdapterManager.notifyDataSetChanged();
                }

            }
        }

        // ===========method get list order object=================
        public List<Spin> getListTitleNotify() {
            String original = "";
            List<Spin> result = new ArrayList<>();
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getListTitleNotify");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getListTitleNotify>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));
                rawData.append("</input>");
                rawData.append("</ws:getListTitleNotify>");
                Log.i("RowData", rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope,
                        Constant.BCCS_GW_URL, MainActivity.getInstance(),
                        "mbccs_getListTitleNotify");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                // ========parse xml ==================
                Document doc = parse.getDomElement(original);
                NodeList nl = doc.getElementsByTagName("return");
                for (int i = 0; i < nl.getLength(); i++) {
                    Element e2 = (Element) nl.item(i);
                    errorCode = parse.getValue(e2, "errorCode");
                    Log.d("errorCode", errorCode);
                    description = parse.getValue(e2, "description");
                    Log.d("description", description);
                    NodeList lstNotifyNode = doc
                            .getElementsByTagName("lstNotify");
                    for (int j = 0; j < lstNotifyNode.getLength(); j++) {
                        Element notifyElement = (Element) lstNotifyNode.item(j);
                        Spin tmp = new Spin();
                        tmp.setId(parse.getValue(notifyElement, "notifyId"));
                        tmp.setCode(parse.getValue(notifyElement, "title"));
                        result.add(tmp);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

    }

    public class GetListNotifyAsyn extends
            AsyncTask<Void, Void, List<NotifyBean>> {
        // =================webservice get list order
        final XmlDomParse parse = new XmlDomParse();
        String description = "";
        String errorCode = "";

        public GetListNotifyAsyn() {
            isRefreshing = true;
        }

        @Override
        protected List<NotifyBean> doInBackground(Void... params) {
            return getListProfileStatus();
        }

        @Override
        protected void onPostExecute(List<NotifyBean> result) {
            if (result != null && !result.isEmpty()) {
                addNotifyBean(result);
            }
            swipeRefreshLayout.setRefreshing(false);
            isRefreshing = false;
        }

        // ===========method get list order object=================
        public List<NotifyBean> getListProfileStatus() {
            String original = "";
            List<NotifyBean> result = new ArrayList<>();
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getListNotify");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getListNotify>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<>();
                paramToken.put("token", Session.getToken());

//				SharedPreferences preferences = MainActivity.getInstance()
//						.getSharedPreferences(Define.PRE_NAME,
//								Activity.MODE_PRIVATE);
//				String name = preferences.getString(Define.KEY_MENU_NAME, "");
//				paramToken.put("vsa", name);

                rawData.append(input.buildXML(paramToken));

                rawData.append("</input>");
                rawData.append("</ws:getListNotify>");
                Log.i("RowData", rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope,
                        Constant.BCCS_GW_URL, MainActivity.getInstance(),
                        "mbccs_getListNotify");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                // ========parse xml ==================
//				Document doc = parse.getDomElement(original);
//				NodeList nl = doc.getElementsByTagName("return");
//				for (int i = 0; i < nl.getLength(); i++) {
//					Element e2 = (Element) nl.item(i);
//					errorCode = parse.getValue(e2, "errorCode");
//					Log.d("errorCode", errorCode);
//					description = parse.getValue(e2, "description");
//					Log.d("description", description);
//					NodeList lstNotifyNode = doc
//							.getElementsByTagName("lstNotifyBeans");
//					for (int j = 0; j < lstNotifyNode.getLength(); j++) {
//						Element notifyElement = (Element) lstNotifyNode.item(j);
//						NotifyBean tmp = new NotifyBean();
//						tmp.setHeader(parse.getValue(notifyElement, "header"));
//						tmp.setContent(parse.getValue(notifyElement, "content"));
//						tmp.setClassName(parse.getValue(notifyElement,
//								"className"));
//						result.add(tmp);
//					}
//				}

                Serializer serializer = new Persister();
                BOCOutput bocOutput = serializer.read(
                        BOCOutput.class, original);
                errorCode = bocOutput.getErrorCode();
                description = bocOutput.getDescription();
                result = bocOutput.getLstNotifyBeans();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

    }

    // move login
    private final OnClickListener moveLogInAct = new OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.getInstance(),
                    LoginActivity.class);
            startActivity(intent);
            MainActivity.getInstance().finish();
        }
    };

    // check survey

    public class GetContentNotify extends AsyncTask<Void, Void, Spin> {
        ProgressDialog progress;
        final Context context;
        final XmlDomParse parse = new XmlDomParse();
        String description = "";
        String errorCode = "";
        final Spin selectedItem;

        public GetContentNotify(Context context, Spin selectedItem) {
            this.context = context;
            this.selectedItem = selectedItem;
            try {
                this.progress = new ProgressDialog(context);
                this.progress.setMessage(getResources().getString(
                        R.string.getting_notify_content));
                progress.setCancelable(false);
                if (!this.progress.isShowing()) {
                    this.progress.show();
                }
            } catch (Exception ignored) {
            }
        }

        @Override
        protected Spin doInBackground(Void... params) {
            return getContentNotify();
        }

        @Override
        protected void onPostExecute(Spin result) {
            progress.dismiss();

            if (errorCode != null && "0".equals(errorCode) && result != null) {
                Dialog dialog = CommonActivity.createDialogLargeContent(
                        MainActivity.getInstance(), result.getValue(),
                        result.getCode(), R.string.btn_close);
                if (dialog != null) {
                    dialog.show();
                }
            } else if (!MainActivity.getInstance().isFinishing() &&
                    MainActivity.getInstance().hasWindowFocus()) {
                OnClickListener onclick = null;
                if (errorCode != null
                        && errorCode.equalsIgnoreCase(Constant.INVALID_TOKEN)) {
                    onclick = new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(),
                                    LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                    | Intent.FLAG_ACTIVITY_NO_HISTORY);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    };
                }
                Dialog dialog = CommonActivity.createAlertDialog(MainActivity
                        .getInstance(), description, MainActivity.getInstance()
                        .getString(R.string.app_name), onclick);
                dialog.show();
            }
        }

        // ===========method get list order object=================
        public Spin getContentNotify() {
            String original = "";
            Spin result = new Spin();
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getContentNotify");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getContentNotify>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<>();
                paramToken.put("token", Session.getToken());
                paramToken.put("notifyId", selectedItem.getId());
                rawData.append(input.buildXML(paramToken));
                rawData.append("</input>");
                rawData.append("</ws:getContentNotify>");
                Log.i("RowData", rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope,
                        Constant.BCCS_GW_URL, MainActivity.getInstance(),
                        "mbccs_getContentNotify");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                // ========parse xml ==================
                Document doc = parse.getDomElement(original);
                NodeList nl = doc.getElementsByTagName("return");
                for (int i = 0; i < nl.getLength(); i++) {
                    Element e2 = (Element) nl.item(i);
                    errorCode = parse.getValue(e2, "errorCode");
                    Log.d("errorCode", errorCode);
                    description = parse.getValue(e2, "description");
                    Log.d("description", description);
                    NodeList lstNotifyNode = doc
                            .getElementsByTagName("lstNotify");
                    for (int j = 0; j < lstNotifyNode.getLength(); j++) {
                        Element notifyElement = (Element) lstNotifyNode.item(j);

                        result.setId(parse.getValue(notifyElement, "notifyId"));
                        result.setCode(parse.getValue(notifyElement, "title"));
                        result.setValue(parse
                                .getValue(notifyElement, "content"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

    }

    private Dialog surveyDialog;

    public class GetSurvey extends AsyncTask<Void, Void, Survey> {
        ProgressDialog progress;
        final Context context;
        XmlDomParse parse = new XmlDomParse();
        String description = "";
        String errorCode = "";

        public GetSurvey(Context context) {
            this.context = context;
            try {
                this.progress = new ProgressDialog(context);
                this.progress.setMessage(getResources().getString(
                        R.string.getting_survey));
                progress.setCancelable(false);
                if (!this.progress.isShowing()) {
                    this.progress.show();
                }
            } catch (Exception ignored) {
            }
        }

        @Override
        protected Survey doInBackground(Void... params) {
            return getSurvey();
        }


        private Dialog questionDialog;
        private int questionIndex = 0;
        private SurveyAnwserAdapter adapter;

        @Override
        protected void onPostExecute(final Survey result) {
            progress.dismiss();

            // Dialog dialog = CommonActivity.createAlertDialog(MainActivity
            // .getInstance(), description, MainActivity.getInstance()
            // .getString(R.string.app_name), onclick);
            // dialog.show();

            if (result != null && result.getLstQuestion() != null
                    && !result.getLstQuestion().isEmpty()) {
                surveyDialog = new Dialog(getActivity(),
                        android.R.style.Theme_Light_NoTitleBar_Fullscreen);
                surveyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                surveyDialog.setContentView(R.layout.layout_survey);

                TextView tv = (TextView) surveyDialog
                        .findViewById(R.id.tvSurveyName);
                String msg = getString(R.string.survey_hint);
                tv.setText(MessageFormat.format(msg, result.getSurveyName()));
                View btnCancel = surveyDialog.findViewById(R.id.btnCancel);
                View btnOK = surveyDialog.findViewById(R.id.btnOK);
                if (!"1".equals(result.getRequired())) {
                    btnCancel.setVisibility(View.VISIBLE);
                    surveyDialog.setCancelable(false);
                } else {
                    btnCancel.setVisibility(View.GONE);
                    surveyDialog.setCancelable(true);
                }

                btnCancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        surveyDialog.dismiss();
                    }
                });
                btnOK.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        questionIndex = 0;
                        showQuestionDialog(result);

                    }
                });
                surveyDialog.show();
            } else {

            }
        }

        public void submitSurvey(Survey survey, Dialog surveyDialog) {
            SubmitSurvey sub = new SubmitSurvey(getActivity(), survey,
                    surveyDialog);
            sub.execute();
        }

        public void showQuestionDialog(final Survey survey) {
            try {
                questionDialog = new Dialog(getActivity(),
                        android.R.style.Theme_Light_NoTitleBar_Fullscreen);
                questionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                questionDialog.setContentView(R.layout.layout_survey_question);

                final SurveyQuestion q = survey.getLstQuestion().get(
                        questionIndex);

                View btnCancel = questionDialog.findViewById(R.id.btnCancel);
                Button btnOK = (Button) questionDialog.findViewById(R.id.btnOK);

                ListView lvAnwser = (ListView) questionDialog
                        .findViewById(R.id.lvAnwser);
                TextView tvTitle = (TextView) questionDialog
                        .findViewById(R.id.tvDialogTitle);
                tvTitle.setText(MessageFormat.format(
                        getString(R.string.question), questionIndex + 1, survey
                                .getLstQuestion().size()));

                TextView tvQuestion = (TextView) questionDialog
                        .findViewById(R.id.tvQuestion);
                // Html.fromHtml(Html.fromHtml(mHtmlString).toString())
                tvQuestion.setText(Html.fromHtml(q.getQuestion()));
                final EditText edtAnwser = (EditText) questionDialog
                        .findViewById(R.id.edtAnwser);

                if ("1".equals(q.getRequired())) {
                    btnCancel.setVisibility(View.GONE);
                    questionDialog.setCancelable(false);
                } else {
                    btnCancel.setVisibility(View.VISIBLE);
                }

                if (questionIndex == survey.getLstQuestion().size() - 1) {
                    btnCancel.setVisibility(View.GONE);
                    btnOK.setText(getString(R.string.submit_survey));
                }
                if (TYPE_TEXT_INPUT.equals(q.getType())
                        || TYPE_TEXT_AREA.equals(q.getType())) {
                    lvAnwser.setVisibility(View.GONE);
                    edtAnwser.setVisibility(View.VISIBLE);
                    edtAnwser.setText(q.getAnwser());
                } else if (TYPE_RADIO_BUTTON.equals(q.getType())
                        || TYPE_CHECKBOX.equals(q.getType())
                        || TYPE_COMBOBOX.equals(q.getType())) {
                    lvAnwser.setVisibility(View.VISIBLE);
                    adapter = new SurveyAnwserAdapter(context,
                            q.getLstAnswer(), q.getType());
                    lvAnwser.setAdapter(adapter);
                    lvAnwser.setOnItemClickListener(new OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1,
                                                int arg2, long arg3) {
                            q.getLstAnswer()
                                    .get(arg2)
                                    .setIsChoose(
                                            !q.getLstAnswer().get(arg2)
                                                    .getIsChoose());
                            if (TYPE_RADIO_BUTTON.equals(q.getType())
                                    || TYPE_COMBOBOX.equals(q.getType())) {
                                for (SurveyAnswer item : q.getLstAnswer()) {
                                    if (!item.getId().equals(
                                            q.getLstAnswer().get(arg2).getId())) {
                                        item.setIsChoose(false);
                                    }
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                    });
                    edtAnwser.setVisibility(View.GONE);
                }
                btnCancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        try {
                            if (questionIndex < survey.getLstQuestion().size() - 1) {
                                questionIndex++;
                                questionDialog.dismiss();
                                showQuestionDialog(survey);

                            } else {
                                SurveyQuestion question = survey
                                        .getLstQuestion().get(questionIndex);
                                question.setAnwser(edtAnwser.getText()
                                        .toString());
                                Boolean isChoosen = checkAnwsered(question);
                                if ("1".equals(question.getRequired())) {

                                    if (!isChoosen) {
                                        Dialog dialog = CommonActivity.createAlertDialog(
                                                MainActivity.getInstance(),
                                                getString(R.string.survey_question_required),
                                                MainActivity
                                                        .getInstance()
                                                        .getString(
                                                                R.string.app_name));
                                        dialog.show();
                                        return;
                                    }
                                }
                                questionDialog.dismiss();
                                submitSurvey(survey, surveyDialog);
                            }
                        } catch (Exception e) {
                            Log.e(logTag, "Exception", e);
                            questionDialog.dismiss();
                        }

                    }
                });
                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        try {

                            SurveyQuestion question = survey.getLstQuestion()
                                    .get(questionIndex);
                            question.setAnwser(edtAnwser.getText().toString());
                            Boolean isChoosen = checkAnwsered(question);
                            if ("1".equals(question.getRequired())) {

                                if (!isChoosen) {
                                    Dialog dialog = CommonActivity.createAlertDialog(
                                            MainActivity.getInstance(),
                                            getString(R.string.survey_question_required),
                                            MainActivity.getInstance()
                                                    .getString(
                                                            R.string.app_name));
                                    dialog.show();
                                    return;
                                }
                            }

                            if (questionIndex < survey.getLstQuestion().size() - 1) {
                                questionIndex++;
                                questionDialog.dismiss();
                                showQuestionDialog(survey);
                            } else {
                                questionDialog.dismiss();
                                submitSurvey(survey, surveyDialog);
                            }
                        } catch (Exception e) {
                            Log.e(logTag, "Exception", e);
                            questionDialog.dismiss();
                        }

                    }
                });

                questionDialog.show();
            } catch (Exception ignored) {

            }
        }

        // ===========method get list order object=================
        public Survey getSurvey() {
            try {

                BCCSGateway input = new BCCSGateway();
                String response = input.getDataFromURL(
                        Constant.SURVEY_GET_DATA_URL
                                + Session.userName.toLowerCase() + "/",
                        context, "getSurvey", Constant.CONNECT_TIMEOUT_SURVEY,
                        Constant.RESPONSE_TIMEOUT_SURVEY);
                // Log.i("getSurveyResponse", response);
                // String response = "";
                Survey survey = parseSurvey(response);
                SharedPreferences preferences = getActivity()
                        .getSharedPreferences(Define.PRE_NAME,
                                Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(Define.KEY_SURVEY_DATE, DateTimeUtils
                        .convertDateTimeToString(new Date(), "dd/MM/yyyy"));
                if (survey != null && survey.getLstQuestion() != null
                        && !survey.getLstQuestion().isEmpty()) {

                    // editor.putString(Define.KEY_SURVEY_NAME,
                    // survey.getSurveyName());
                    // editor.putString(Define.KEY_SURVEY_ID, survey.getId());
                    // editor.putString(Define.KEY_SURVEY_DESCRIPTION,
                    // survey.getDescription());
                    editor.putString(Define.KEY_SURVEY_REQUIRED, "1");

                    // Mac dinh
                    // editor.putString(Define.KEY_TRACKING, "2");

                } else {
                    editor.putString(Define.KEY_SURVEY_REQUIRED, "0");
                }
                editor.commit();
                return survey;
            } catch (Exception e) {
                Log.e("GetSurvey", e.getMessage(), e);
                return null;
            }
        }

        public Survey parseSurvey(String input) {
            try {
                Survey result = new Survey();

                JSONObject mainObject = new JSONObject(input)
                        .getJSONObject("survey");

                result.setId(mainObject.getString("id"));
                result.setSurveyName(mainObject.getString("surveyName"));
                result.setRequired(mainObject.getString("required"));
                result.setDescription(mainObject.getString("description"));

                JSONArray listQuestion = mainObject
                        .getJSONArray("listQuestion");
                if (listQuestion != null && listQuestion.length() > 0) {
                    List<SurveyQuestion> lstQ = new ArrayList<>();

                    for (int i = 0; i < listQuestion.length(); i++) {
                        SurveyQuestion question = new SurveyQuestion();
                        JSONObject q = listQuestion.getJSONObject(i);
                        question.setId(q.getString("id"));
                        question.setQuestion(q.getString("question"));
                        question.setRequired(q.getString("required"));
                        question.setSurveyId(q.getString("surveyId"));
                        question.setType(q.getString("type"));

                        JSONArray lstAnwser = q.getJSONArray("listAnswer");
                        if (lstAnwser != null && lstAnwser.length() > 0) {

                            List<SurveyAnswer> lstA = new ArrayList<>();
                            for (int j = 0; j < lstAnwser.length(); j++) {
                                SurveyAnswer anwser = new SurveyAnswer();
                                JSONObject aws = lstAnwser.getJSONObject(j);
                                anwser.setContent(aws
                                        .getString("answerContent"));
                                anwser.setId(aws.getString("id"));
                                anwser.setQuestionId(aws
                                        .getString("questionId"));
                                lstA.add(anwser);
                            }
                            question.setLstAnswer(lstA);
                        }

                        lstQ.add(question);
                    }
                    result.setLstQuestion(lstQ);
                }

                return result;
            } catch (Exception e) {
                SharedPreferences preferences = getActivity()
                        .getSharedPreferences(Define.PRE_NAME,
                                Activity.MODE_PRIVATE);

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(Define.KEY_SURVEY_DATE, DateTimeUtils
                        .convertDateTimeToString(new Date(), "dd/MM/yyyy"));
                editor.putString(Define.KEY_SURVEY_REQUIRED, "0");
                editor.commit();
                return null;
            }

        }
    }

    public class SubmitSurvey extends AsyncTask<Void, Void, String> {
        ProgressDialog progress;
        final Context context;
        String description = "";
        String errorCode = "";
        final Survey survey;
        final Dialog surveyDialog;

        public SubmitSurvey(Context context, Survey survey, Dialog surveyDialog) {
            this.survey = survey;
            this.context = context;
            this.surveyDialog = surveyDialog;
            try {
                this.progress = new ProgressDialog(getActivity());
                this.progress.setMessage(getResources().getString(
                        R.string.submitting_survey));
                progress.setCancelable(false);
                if (!this.progress.isShowing()) {
                    this.progress.show();
                }
            } catch (Exception ignored) {
            }
        }

        @Override
        protected String doInBackground(Void... params) {
            return submitSurvey();
        }

        @Override
        protected void onPostExecute(final String result) {
            progress.dismiss();
            String msg = getString(R.string.submit_survey_fail);
            SharedPreferences preferences = getActivity().getSharedPreferences(
                    Define.PRE_NAME, Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(Define.KEY_SURVEY_DATE, DateTimeUtils
                    .convertDateTimeToString(new Date(), "dd/MM/yyyy"));

            // editor.putString(Define.KEY_SURVEY_NAME,
            // survey.getSurveyName());
            // editor.putString(Define.KEY_SURVEY_ID, survey.getId());
            // editor.putString(Define.KEY_SURVEY_DESCRIPTION,
            // survey.getDescription());

            // Mac dinh
            // editor.putString(Define.KEY_TRACKING, "2");

            editor.putString(Define.KEY_SURVEY_REQUIRED, "1");

            if ("1".equals(result)) {
                editor.putString(Define.KEY_SURVEY_REQUIRED, "0");
                msg = getString(R.string.submit_survey_success);
                if (surveyDialog != null) {
                    surveyDialog.dismiss();
                }
                Dialog dialog = CommonActivity.createAlertDialog(MainActivity
                        .getInstance(), msg, MainActivity.getInstance()
                        .getString(R.string.app_name));
                dialog.show();
            } else {
                OnClickListener cancelClick = new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        surveyDialog.dismiss();

                    }
                };
                Dialog dialog = CommonActivity.createDialog(
                        MainActivity.getInstance(),
                        R.string.submit_survey_fail, R.string.app_name,
                        R.string.ok, R.string.cancel, null, cancelClick);

                dialog.show();
            }

            editor.commit();

        }

        // ===========method get list order object=================
        public String submitSurvey() {
            try {

                // Log.i("submitSurveyResponse", response);
                return SurveySubmit.POST(

                        Constant.SURVEY_SUBMIT_DATA_URL, survey,
                        Session.userName.toLowerCase(),
                        Constant.CONNECT_TIMEOUT_SURVEY,
                        Constant.RESPONSE_TIMEOUT_SURVEY);
            } catch (Exception e) {
                Log.e("GetSurvey", e.getMessage(), e);
                return e.getMessage();
            }
        }
    }

    private Boolean checkAnwsered(SurveyQuestion q) {
        Boolean isChoosen = false;
        String anwserType = q.getType();
        if (FragmentLoginNotData.TYPE_CHECKBOX.equals(anwserType)
                || FragmentLoginNotData.TYPE_RADIO_BUTTON.equals(anwserType)
                || FragmentLoginNotData.TYPE_COMBOBOX.equals(anwserType)) {
            for (SurveyAnswer ans : q.getLstAnswer()) {
                if (ans.getIsChoose()) {
                    isChoosen = true;
                    break;
                }
            }
        } else {
            if (q.getAnwser() != null && !q.getAnwser().trim().isEmpty()) {
                isChoosen = true;
            }
        }
        return isChoosen;
    }

    private synchronized void addNotifyBean(List<NotifyBean> lstNotifyBean) {
        for (NotifyBean notifyBean : lstNotifyBean) {
            Manager manager = new Manager();
            manager.setResIcon(R.drawable.ic_launcher);
            manager.setHeader(notifyBean.getHeader());
            manager.setContent(notifyBean.getContent());
            manager.setClassName(notifyBean.getClassName());
            manager.setKeyMenuName(Define.NOTIFY);
            manager.setLstWarningStaffBOs(notifyBean.getLstWarningStaffBOs());

            arrayListManager.add(manager);
        }

        if (tmp != null && !tmp.isEmpty()) {
            arrayListManager.addAll(tmp);
        }

        mAdapterManager.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        if (!isRefreshing) {
            swipeRefreshLayout.setRefreshing(true);

            // TODO Auto-generated method stub
            tmp = new ArrayList<>();
            synchronized (lock) {
                for (Manager manager : arrayListManager) {
                    if (!Define.NOTIFY.equals(manager.getKeyMenuName())) {
                        tmp.add(manager);
                    }
                }

                arrayListManager.clear();
                mAdapterManager.notifyDataSetChanged();
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                new GetListNotifyAsyn().executeOnExecutor(
                        AsyncTask.THREAD_POOL_EXECUTOR, null);
            } else {
                new GetListNotifyAsyn().execute();
            }
        }
    }
}
