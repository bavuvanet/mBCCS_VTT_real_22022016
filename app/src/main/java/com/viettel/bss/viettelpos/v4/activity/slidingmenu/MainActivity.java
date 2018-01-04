package com.viettel.bss.viettelpos.v4.activity.slidingmenu;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity.FragmentChangeSim;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity.FragmentEditCustomerBCCS;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity.FragmentTransferCustomer;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.bankplus.fragment.FragmentBankplusManager;
import com.viettel.bss.viettelpos.v4.cc.fragment.FragmentCCManager;
import com.viettel.bss.viettelpos.v4.channel.activity.FragmentListChannel;
import com.viettel.bss.viettelpos.v4.channel.activity.StaffInfoActivity;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentContractVerifyUpdateDetail;
import com.viettel.bss.viettelpos.v4.charge.fragment.ChargeManagerFragment;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.GPSTracker;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.FragmentConnectionMobileNew;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.FragmentConnectionMobileSmartSimConnect;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentManageConnect;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentModifyProfile;
import com.viettel.bss.viettelpos.v4.customer.fragment.ComplainFragment;
import com.viettel.bss.viettelpos.v4.customer.fragment.CustomerManagerFragment;
import com.viettel.bss.viettelpos.v4.customer.manage.AlarmUploadImageReceiver;
import com.viettel.bss.viettelpos.v4.customer.manage.FragmentCustomerUpdateCV;
import com.viettel.bss.viettelpos.v4.customer.manage.RegisterNewFragment;
import com.viettel.bss.viettelpos.v4.dialog.ChooseFileDialogFragment;
import com.viettel.bss.viettelpos.v4.fragment.ModifyProfileFragment;
import com.viettel.bss.viettelpos.v4.guide.fragment.GuideManagerFragment;
import com.viettel.bss.viettelpos.v4.hsdt.fragments.ProfileInfoDialogFragment;
import com.viettel.bss.viettelpos.v4.hsdt.fragments.ProfileInfoOmniFragment;
import com.viettel.bss.viettelpos.v4.infrastrucure.fragment.InfrastructureManagerFragment;
import com.viettel.bss.viettelpos.v4.login.activity.FragmentLoginNotData;
import com.viettel.bss.viettelpos.v4.message.BaseMsg;
import com.viettel.bss.viettelpos.v4.omichanel.fragment.OmniFragment;
import com.viettel.bss.viettelpos.v4.omichanel.fragment.OmniManagerFragment;
import com.viettel.bss.viettelpos.v4.omichanel.fragment.SearchOrderOmniFragment;
import com.viettel.bss.viettelpos.v4.report.fragment.ReportManagerFragment;
import com.viettel.bss.viettelpos.v4.sale.activity.SaleManagerFragment;
import com.viettel.bss.viettelpos.v4.synchronizationdata.FragmentSynthrozation;
import com.viettel.bss.viettelpos.v4.synchronizationdata.RunSynSerice;
import com.viettel.bss.viettelpos.v4.work.fragment.JobManagerFragment;
import com.viettel.savelog.SaveLog;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import tourguide.tourguide.Overlay;
import tourguide.tourguide.Pointer;
import tourguide.tourguide.ToolTip;
import tourguide.tourguide.TourGuide;

public class MainActivity extends GPSTracker implements NavigationView.OnNavigationItemSelectedListener {
    private final String TAG = "MainActivity";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imvTemp)
    ImageView imvTemp;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    private ActionBarDrawerToggle toggle;
    private static MainActivity instance;
    public static int mLevelMenu = 0;

    public static MainActivity getInstance() {
        return instance;
    }

    private Object lock = new Object();

    //[BaVV] Add Tooltip Start
    public TourGuide mTutorialHandler;
    public TourGuide mTutorialHandler2;
    //[BaVV] Add Tooltip End

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bccs2);

        synchronized (lock) {
            instance = this;
        }
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //[BaVV] Edit Tooltip Start
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerStateChanged(int newState) {
                cleanTourGuide();
            }
        };
        //[BaVV] Edit Tooltip End
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        showDialogNewVertion();

        //[BaVV] Add Tooltip Start
        View button = navigationButtonView(toolbar);
        if(null != button) {
            ToolTip toolTip = new ToolTip()
                    .setTitle("")
                    .setDescription("Bấm chọn để hiển thị danh mục")
                    .setGravity(Gravity.BOTTOM);

            mTutorialHandler = TourGuide.init(this).with(TourGuide.Technique.Click)
                    .motionType(TourGuide.MotionType.AllowAll)
//                .setPointer(new Pointer())
                    .setToolTip(toolTip)
                    .setOverlay(new Overlay().disableClick(false))
                    .playOn(button);
        }

        ToolTip toolTip2 = new ToolTip()
                .setTitle("")
                .setDescription("Bấm chọn để gọi tổng đài hỗ trợ")
                .setGravity(Gravity.BOTTOM);

        mTutorialHandler2 = TourGuide.init(this).with(TourGuide.Technique.Click)
                .motionType(TourGuide.MotionType.AllowAll)
//                .setPointer(new Pointer())
                .setToolTip(toolTip2)
                .setOverlay(new Overlay().disableClick(false))
                .playOn(imvTemp);
        //[BaVV] Add Tooltip End

        if (savedInstanceState == null) {
            ReplaceFragment.replaceFragmentFromMain(this, new FragmentLoginNotData(), true);
        }

        synData();
        startServiceWriteLog();
        startServiceUploadImage();
        initNavigation();

        View header = navigationView.getHeaderView(0);
        ImageButton signatureBtn = (ImageButton) header.findViewById(R.id.signatureBtn);

        signatureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StaffInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initNavigation() {
        View navView = navigationView.getHeaderView(0);
        if (!CommonActivity.isNullOrEmpty(Session.userName)) {
            ((TextView) navView.findViewById(R.id.tvStaffCode)).setText(Session.userName);
        }
        if (!CommonActivity.isNullOrEmpty(Session.loginUser)) {
            if (CommonActivity.isNullOrEmpty(Session.loginUser.getName())
                    || CommonActivity.isNullOrEmpty(Session.loginUser.getStaffId())) {
                CommonActivity.createAlertDialog(this, R.string.sync_data_error, R.string.app_name).show();
            } else {
                ((TextView) navView.findViewById(R.id.tvStaffName)).setText(Session.loginUser.getName());
                ((TextView) navView.findViewById(R.id.tvStaffId)).setText(getString(R.string.maNV, Session.loginUser.getStaffId()));
            }
        }

        Menu navMenuView = navigationView.getMenu();

        SharedPreferences preferences = getSharedPreferences(
                Define.PRE_NAME, Activity.MODE_PRIVATE);
        String name = preferences.getString(Define.KEY_MENU_NAME, "");

        if (!name.contains(";sale.management;")) {
            navMenuView.findItem(R.id.nav_sale).setVisible(false);
        }
        if (!name.contains(";cus_management;")) {
            navMenuView.findItem(R.id.nav_customer).setVisible(false);
        }

        if (!name.contains(";pm.management;")) {
            navMenuView.findItem(R.id.nav_charge).setVisible(false);
        }
        if (!name.contains(";channel.management;")) {
            navMenuView.findItem(R.id.nav_channel).setVisible(false);
        }

        if (!name.contains(";infractrue_managament;")) {
            navMenuView.findItem(R.id.nav_infras).setVisible(false);
        }
        if (!name.contains(";work_management;")) {
            navMenuView.findItem(R.id.nav_job).setVisible(false);
        }
        if (!name.contains(";report;")) {
            navMenuView.findItem(R.id.nav_report).setVisible(false);
        }

        if (!name.contains(";cc.mamagment;")) {
            navMenuView.findItem(R.id.nav_cc).setVisible(false);
        }

        if (!name.contains(";m.menu.bankplus;")) {
            navMenuView.findItem(R.id.nav_bankplus).setVisible(false);
        }
//        if (!name.contains(";menu.contract;")) {
//            navMenuView.findItem(R.id.nav_contract).setVisible(true);
//        }
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            doBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        doDestroy();
        super.onDestroy();
    }

    public static Menu menuCreateOption;

    public Menu getMenuCreateOption() {
        return menuCreateOption;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menuCreateOption = menu;

        return true;
    }

    //[BaVV] Add Tooltip Start
    public View navigationButtonView(Toolbar toolbar) {
        try {
            Field field = Toolbar.class.getDeclaredField("mNavButtonView");
            field.setAccessible(true);
            View navigationView = (View) field.get(toolbar);
            return navigationView;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //[BaVV] Add Tooltip End

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");
        int id = item.getItemId();
        switch (id) {
            case R.id.btnHome:
                //[BaVV] Add Tooltip Start
                cleanTourGuide();
                //[BaVV] Add Tooltip End
                CommonActivity.callphone(this, Constant.phoneNumber);
                return true;
            case R.id.btnListOrGridView:
                EventBus.getDefault().post(new BaseMsg());
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        try{
            int id = item.getItemId();
            Fragment fragment = null;
            switch (id) {
                case R.id.nav_sale:
                    fragment = new SaleManagerFragment();
                    break;
                case R.id.nav_omichanel:
                    fragment = new OmniManagerFragment();
                    break;
                case R.id.nav_customer:
                    fragment = new CustomerManagerFragment();
                    break;
                case R.id.nav_charge:
                    fragment = new ChargeManagerFragment();
                    break;
                case R.id.nav_channel:
                    CommonActivity.addMenuActionToDatabase(MainActivity.this, getResources().getString(R.string.channel),
                            Constant.MENU_FUNCTIONS.CHANNEL_CARE, Constant.MENU_FUNCTIONS.CHANNEL_CARE, R.drawable.ic_icon_macdinh);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("frommenu", true);
                    fragment = new FragmentListChannel();
                    fragment.setArguments(mBundle);
                    break;
                case R.id.nav_infras:
                    fragment = new InfrastructureManagerFragment();
                    break;
                case R.id.nav_job:
                    fragment = new JobManagerFragment();
                    break;
                case R.id.nav_report:
                    fragment = new ReportManagerFragment();
                    break;
                case R.id.nav_cc:
                    fragment = new FragmentCCManager();
                    break;
                case R.id.nav_bankplus:
                    fragment = new FragmentBankplusManager();
                    break;
                case R.id.nav_system:
                    fragment = new FragmentSynthrozation();
                    break;
                case R.id.nav_guide:
                    fragment = new GuideManagerFragment();
                    break;
//            case R.id.nav_contract:
//                fragment = new FragmentContractManager();
//                break;
                case R.id.nav_logout:
                    exitApp(1);
                    if (fragment != null) {
                        ReplaceFragment.replaceFragmentFromMain(this, fragment, true);
                        setTitleActionBar(getString(R.string.new_info));
                    }
                    return true;
                default:
                    break;
            }

            if (fragment != null) {
                ReplaceFragment.replaceFragmentFromMain(this, fragment, true);
                setTitleActionBar(getString(R.string.new_info));
            }

            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            enableViews(true);
        }catch (Exception e){
            e.printStackTrace();
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult requestCode : " + requestCode);
        Fragment fragment = ReplaceFragment.getActiveFragment(this);

        if (fragment instanceof FragmentManageConnect) {
            fragment.onActivityResult(requestCode,
                    resultCode, data);
        } else if (fragment instanceof FragmentConnectionMobileSmartSimConnect) {
            fragment
                    .onActivityResult(requestCode, resultCode, data);
        } else if (fragment instanceof FragmentContractVerifyUpdateDetail) {
            fragment.onActivityResult(
                    requestCode, resultCode, data);
        } else if (fragment instanceof FragmentCustomerUpdateCV) {
            fragment.onActivityResult(requestCode,
                    resultCode, data);
        } else if (fragment instanceof FragmentModifyProfile) {
            fragment.onActivityResult(requestCode,
                    resultCode, data);
        } else if (fragment instanceof FragmentChangeSim) {
            fragment.onActivityResult(requestCode,
                    resultCode, data);
        } else if (fragment instanceof ComplainFragment) {
            fragment.onActivityResult(requestCode,
                    resultCode, data);
        } else if (fragment instanceof ModifyProfileFragment){
            fragment.onActivityResult(requestCode,
                    resultCode, data);
        } else if (fragment instanceof SearchOrderOmniFragment){
            fragment.onActivityResult(requestCode,
                    resultCode, data);
        } else if (fragment instanceof FragmentEditCustomerBCCS) {
            fragment.onActivityResult(requestCode,
                    resultCode, data);
        } else if (fragment instanceof ChooseFileDialogFragment) {
            fragment.onActivityResult(requestCode,
                    resultCode, data);
        }  else if (fragment instanceof FragmentTransferCustomer) {
            fragment.onActivityResult(requestCode,
                    resultCode, data);
        } else if (fragment instanceof ProfileInfoDialogFragment) {
            fragment.onActivityResult(requestCode,
                    resultCode, data);
        } else if (fragment instanceof RegisterNewFragment) {
            fragment.onActivityResult(requestCode,
                    resultCode, data);
        } else if (fragment instanceof FragmentConnectionMobileNew) {
            fragment.onActivityResult(requestCode,
                    resultCode, data);
        } else if (fragment instanceof ProfileInfoOmniFragment) {
            fragment.onActivityResult(requestCode,
                    resultCode, data);
        } else if (fragment instanceof SearchOrderOmniFragment) {
            fragment.onActivityResult(requestCode,
                    resultCode, data);
        } else if (fragment instanceof OmniFragment) { // check chu ky
            fragment.onActivityResult(requestCode,
                    resultCode, data);
        } else {
            Log.d(TAG, "MainActivity onActivityResult Fragment : "
                    + fragment.getClass().getName());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            Fragment f = getSupportFragmentManager().findFragmentById(
                    R.id.frame_container);
            if (f instanceof FragmentLoginNotData) {
                setTitleActionBar(getString(R.string.new_info));
            }
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(
                    "FragmentLoginNotData");
            if (fragment instanceof FragmentLoginNotData) {
                setTitleActionBar(getString(R.string.new_info));
            }
//            else if(fragment instanceof ReportManagerFragment){
//                setTitleActionBar(R.string.report);
//                enableMenuListOrGridView(true);
//            }
            CommonActivity.getMemory(this);
        } catch (Exception e) {
            Log.d(Constant.TAG, "error", e);
        }
    }

    private synchronized void doBackPressed() {
        int mCountBackStack = getSupportFragmentManager().getBackStackEntryCount();
        Log.d(TAG, "mCountBackStack = " + mCountBackStack);
        try {
            if (mCountBackStack > 0) {
                if (mCountBackStack == 1) {
                    Fragment f = getSupportFragmentManager().findFragmentById(
                            R.id.frame_container);
                    if (f instanceof FragmentLoginNotData) {
                        exitApp(0);
                    } else {
                        ReplaceFragment.replaceFragmentToHome(this, true);
                    }
                    enableViews(false);

                } else if (mCountBackStack == 2) {
                    enableViews(true);
                    super.onBackPressed();
                } else {
                    enableViews(true);
                    super.onBackPressed();
                }
            } else {
                exitApp(0);
            }
            CommonActivity.hideSoftKeyboard(this);
        } catch (Exception e) {
            Log.d(TAG, "Error doBackPressed", e);
        }
    }

    private boolean mToolBarNavigationListenerIsRegistered = false;

    public void enableViews(boolean enable) {
        if (enable) {
            // Remove hamburger
            toggle.setDrawerIndicatorEnabled(false);
            // Show back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            // click events to this listener.
            if (!mToolBarNavigationListenerIsRegistered) {
                toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Doesn't have to be onBackPressed
                        onBackPressed();
                    }
                });

                mToolBarNavigationListenerIsRegistered = true;
            }
        } else {
            // Remove back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            // Show hamburger
            toggle.setDrawerIndicatorEnabled(true);
            // Remove the/any drawer toggle listener
            toggle.setToolbarNavigationClickListener(null);
            mToolBarNavigationListenerIsRegistered = false;
        }
    }

    private void exitApp(final int i) {
        try {
            String messg = getString(R.string.exit_app);
            if (i == 1) {
                messg = getString(R.string.log_out);//
            }
            CommonActivity.createDialog(this, messg,
                    getString(R.string.app_name),
                    getString(R.string.say_ko), getString(R.string.say_co), null, new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            if (i == 0) {
                                finish();
                            } else if (i == 1) {
                                Intent intent = new Intent(
                                        getApplicationContext(),
                                        LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                        | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(intent);
                                finish();
                            }
                        }

                    }).show();
        } catch (Exception e) {
            Log.d(TAG, "Error exitApp", e);
        }
    }

    private Timer timer;

    private void synData() {
        Log.d(TAG, "start sync data");
        try {
            SharedPreferences preferences = getSharedPreferences(
                    Define.PRE_NAME, MODE_PRIVATE);
            String name = preferences.getString(Define.KEY_MENU_NAME, "");
            if (name.contains(";sync.staff;")) {
                final Handler handler = new Handler();
                Session.isSync = true;
                Session.isSyncRunning = false;
                timer = new Timer();
                TimerTask doAsynchronousTask = new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            public void run() {
                                try {
                                    Intent intent = new Intent(getApplicationContext(),
                                            RunSynSerice.class);
                                    intent.putExtra("KEY_SESSION_TOKEN",
                                            Session.getToken());
                                    startService(intent);
                                } catch (Exception e) {
                                    Log.d(Constant.TAG, "error", e);
                                }
                            }
                        });
                    }
                };
                timer.schedule(doAsynchronousTask, 0,
                        Constant.TIME_SYNC_CHANGE_RECORD);
            }
        } catch (Exception e) {
            Log.d(TAG, "Error synData", e);
        }
    }

    private void showDialogNewVertion() {
        try {
            SharedPreferences preferences = getSharedPreferences(
                    Define.PRE_NAME, MODE_PRIVATE);
            String lastVersion = preferences.getString(Define.KEY_LAST_VERSION,
                    "");
            PackageInfo packageInfo = this.getPackageManager().getPackageInfo(
                    getPackageName(), 0);
            String currentVersion = packageInfo.versionName;
            if (lastVersion == null || !lastVersion.equals(currentVersion)) {
                Dialog dialog = CommonActivity.createDialogLargeContent(
                        this,
                        getString(
                                R.string.new_version_item,
                                getPackageManager().getPackageInfo(
                                        getPackageName(), 0).versionName),
                        getString(R.string.app_name), R.string.ok);
                if (dialog != null) {
                    dialog.show();
                }
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(Define.KEY_LAST_VERSION, currentVersion);
                editor.commit();
            }
        } catch (Exception e) {
            Log.d(TAG, "Error showDialogNewVertion", e);
        }
    }

    private void startServiceWriteLog() {
        try {
            if (Session.KPI_REQUEST) {
                SaveLog.sendLog(getApplicationContext(),
                        Constant.KPI_SERVER, Constant.KPI_PARTY_CODE,
                        Constant.USER_KPI, Constant.PASS_KPI, 1);
            }
        } catch (Exception e) {
            Log.d(TAG, "Error runServiceWriteLog", e);
        }
    }

    private void startServiceUploadImage() {
        try {
            AlarmUploadImageReceiver
                    .startServiceUploadImage(getApplicationContext());
        } catch (Exception e) {
            Log.d(TAG, "Error startServiceUploadImage", e);
        }
    }

    private void doDestroy() {
        try {
            Session.isSync = false;
            if (timer != null) {
                timer.cancel();
            }
            AlarmUploadImageReceiver
                    .stopServiceUploadImage(getApplicationContext());

        } catch (Exception e) {
            Log.d(TAG, "Error doDestroy", e);
        }
    }

    public void setTitleActionBar(String title) {
        getSupportActionBar().setTitle(title);
        setSubTitleActionBar("");
        if (menuCreateOption != null) {
            enableMenuChannel(menuCreateOption, false);
            enableMenuListOrGridView(false);
        }
    }

    public void setTitleActionBar(String title, boolean enable) {
        try{
            getSupportActionBar().setTitle(title);
            setSubTitleActionBar("");
            if (menuCreateOption != null) {
                enableMenuChannel(menuCreateOption, false);
                enableMenuListOrGridView(false);
            }
        }catch (Exception e){

        }

    }

    private void enableMenuChannel(Menu menu, boolean enable) {
        try{
            Log.d(TAG, "enableMenuChannel = " + enable);
            MenuItem menuItemSortByCareAsc = menu.findItem(R.id.btnSortByCareAsc);
            menuItemSortByCareAsc.setVisible(enable);

            MenuItem menuItemSortByCareDesc = menu.findItem(R.id.btnSortByCareDesc);
            menuItemSortByCareDesc.setVisible(enable);

            MenuItem menuItemMap = menu.findItem(R.id.btnMap);
            menuItemMap.setVisible(enable);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void enableMenuMap(){
        MenuItem menuItemMap = menuCreateOption.findItem(R.id.btnMap);
        menuItemMap.setVisible(true);

        MenuItem menuItemHome = menuCreateOption.findItem(R.id.btnHome);
        menuItemHome.setVisible(false);
    }

    public void setTitleActionBar(int id) {
        getSupportActionBar().setTitle(getString(id));
        setSubTitleActionBar("");
        enableMenuChannel(menuCreateOption, false);
        enableMenuListOrGridView(false);
    }

    public void setTitleActionBar(int id, boolean enable) {
        try{
            getSupportActionBar().setTitle(getString(id));
            setSubTitleActionBar("");
            enableMenuChannel(menuCreateOption, enable);
            enableMenuListOrGridView(false);
        }catch (Exception e){

        }
    }

    public void enableMenuListOrGridView(boolean enable) {
        try{
            MenuItem menuItemView = menuCreateOption.findItem(R.id.btnListOrGridView);
            menuItemView.setVisible(enable);

            MenuItem menuItemHome = menuCreateOption.findItem(R.id.btnHome);
            menuItemHome.setVisible(!enable);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void setSubTitleActionBar(String title) {
        getSupportActionBar().setSubtitle(title);
    }

    public void setSubTitleActionBar(int id) {
        getSupportActionBar().setSubtitle(getString(id));
    }

    public void setIconListOrGridView(int idIcon) {
        try {
            MenuItem menuItemView = menuCreateOption.findItem(R.id.btnListOrGridView);
            menuItemView.setIcon(idIcon);
        }catch (Exception e){
            Log.e("Exception","Exception",e);
        }

    }

    //[BaVV] Add Tooltip Start
    public void cleanTourGuide() {
        if(null != mTutorialHandler) {
            mTutorialHandler.cleanUp();
        }
        if(null != mTutorialHandler2) {
            mTutorialHandler2.cleanUp();
        }

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (fragment instanceof FragmentLoginNotData) {
            ((FragmentLoginNotData) fragment).cleanTourGuide();
        }
    }
    //[BaVV] Add Tooltip End
}
