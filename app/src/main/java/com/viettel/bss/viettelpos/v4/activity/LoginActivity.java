package com.viettel.bss.viettelpos.v4.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.advisory.screen.WarningSubInfoDialog;
import com.viettel.bss.viettelpos.v4.business.ApParamBusiness;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DatabaseUtils;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.EncryptKeystore;
import com.viettel.bss.viettelpos.v4.commons.FingerManager;
import com.viettel.bss.viettelpos.v4.commons.FingerManager.OnAuthenticationError;
import com.viettel.bss.viettelpos.v4.commons.FingerManager.OnAuthenticationHelp;
import com.viettel.bss.viettelpos.v4.commons.FingerManager.OnAuthenticationSucceed;
import com.viettel.bss.viettelpos.v4.commons.GPSTracker;
import com.viettel.bss.viettelpos.v4.commons.PreferenceUtils;
import com.viettel.bss.viettelpos.v4.commons.SdCardHelper;
import com.viettel.bss.viettelpos.v4.commons.SecurityUtil;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.commons.ZipUtils;
import com.viettel.bss.viettelpos.v4.handler.VSAMenuHandler;
import com.viettel.bss.viettelpos.v4.infrastrucure.dal.InfrastrucureDB;
import com.viettel.bss.viettelpos.v4.login.activity.FragmentLoginNotData;
import com.viettel.bss.viettelpos.v4.object.KeyPairs;
import com.viettel.bss.viettelpos.v4.object.VSA;
import com.viettel.bss.viettelpos.v4.sale.business.CacheData;
import com.viettel.bss.viettelpos.v4.sale.business.StaffBusiness;
import com.viettel.bss.viettelpos.v4.synchronizationdata.UpdateVersionAsyn;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.savelog.SaveLog;

import org.apache.http.conn.ConnectTimeoutException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.fabric.sdk.android.Fabric;

import static com.viettel.bss.viettelpos.v4.commons.Session.userName;

public class LoginActivity extends GPSTracker implements OnClickListener {

    private final int REQUEST_CODE_ASK_PERMISSIONS = 12354;
    private final static int PERM_REQUEST_CODE_DRAW_OVERLAYS = 11111;
    private final static int APP_SETTING_RESULT_CODE = 333;

    private static final String LOG_TAG = LoginActivity.class.getName();
    private static final String ERROR_USER_MISSING = "User_missing";
    private static final String ERROR_PASSWORD_MISSING = "Password_missing";
    private static final String ERROR_PING_SERVER = "Error_Ping_Server";

    // userName: bss_quynhln6
    // pass: 123
    // 10 61 11 36
    private TextInputEditText edtUserName;
    private EditText editUserName;
    private EditText edtPassword, edtNewPassword, edtOldPassword,
            edtConfirmPassword;
    private Button btnLogin;
    private String errorMessage;
    private String serialSim = "";
    private static Activity act;
    private ProgressDialog dialogSendSMS;
    private String description = "";
    private String forceUpgrade = "";
    private String version = "";
    private String checkSyn = "";
    private TelephonyManager tele;
//    private boolean genClientKey = false;
    private final FingerManager fingerManager = new FingerManager();
    private Animation shake;
    private TextView tvFinger;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
//    private GoogleApiClient client;
    public static Activity getInstance() {
        return act;
    }

    private CountDownTimer countDownTimer;
    private Dialog dialogChangePass;
    private TextView txtchangePass;

    // dialog show reset pass
    private EditText edtUserNameReset, edtNewPasswordReset,
            edtConfirmPasswordReset, edtmabimat;
    private Dialog dialogResetpass;
    private TextView txtresetpass;
    private String daysBetweenExpried;

    private GuideSettingPermissionDialog dialogGuideSetting;

    private static final String[] arrPermission = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private final List<String> lstPermissionDenied = new ArrayList<>();

    // thientv7 bo sung addInfo
    String addInfo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        edtUserName = (TextInputEditText) findViewById(R.id.edtUserName);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtPassword.setTransformationMethod(new PasswordTransformationMethod());
        edtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE) {
                    {
                        FragmentLoginNotData.mView = null;
                        edtUserName.setText(edtUserName.getText().toString().trim());
                        edtPassword.setText(edtPassword.getText().toString().trim());
                        if (edtUserName.getText().toString().isEmpty()) {
                            String message = getString(R.string.userNameRequired);
                            String title = getString(R.string.app_name);
                            Dialog dialog = CommonActivity.createAlertDialog(LoginActivity.this,
                                    message, title);
                            dialog.show();
                            edtUserName.requestFocus();
                            edtUserName.startAnimation(shake);
                        } else if (edtPassword.getText().toString().isEmpty()) {
                            String message = getString(R.string.passwordRequired);
                            String title = getString(R.string.app_name);
                            Dialog dialog = CommonActivity.createAlertDialog(LoginActivity.this,
                                    message, title);
                            dialog.show();
                            edtPassword.requestFocus();
                        } /*
                 * else if (serialSim == null || serialSim.isEmpty()) { String
				 * message = getString(R.string.serialSimNotFound); String title
				 * = getString(R.string.app_name); Dialog dialog =
				 * CommonActivity.createAlertDialog(this, message, title);
				 * dialog.show(); }
				 */ else {

                            if (CommonActivity.isNetworkConnected(LoginActivity.this)) {
                                new LoginAsyncTask(LoginActivity.this).execute();
                            } else {
                                OnClickListener loginClick = new OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        dialogSendSMS = new ProgressDialog(
                                                LoginActivity.this);
                                        dialogSendSMS.setMessage(getResources()
                                                .getString(R.string.processing));
                                        dialogSendSMS.setCancelable(false);
                                        if (!dialogSendSMS.isShowing()) {
                                            dialogSendSMS.show();
                                        }
                                        String synTask = "0SI";

                                        try {
                                            CommonActivity.sendSMS(
                                                    Constant.EXCHANGE_ADDRESS,
                                                    buildRawData(true, edtUserName
                                                            .getText().toString()
                                                            .trim(), edtPassword
                                                            .getText().toString()
                                                            .trim(), addInfo),
                                                    LoginActivity.this, dialogSendSMS,
                                                    synTask);
                                        } catch (Exception e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }

                                        new CountDownTimer(Constant.TIMEOUT_SMS, 1000) {
                                            @Override
                                            public void onTick(long millisUntilFinished) {
                                            }

                                            @Override
                                            public void onFinish() {
                                                if (dialogSendSMS.isShowing()) {
                                                    dialogSendSMS.dismiss();
                                                    Toast.makeText(
                                                            LoginActivity.this,
                                                            getResources()
                                                                    .getString(
                                                                            R.string.time_out_sms),
                                                            Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        }.start();

                                    }
                                };

                                CommonActivity.createDialog(
                                        LoginActivity.this,
                                        getResources().getString(
                                                R.string.no_network_message),
                                        getResources().getString(
                                                R.string.no_network_title),
                                        getResources().getString(R.string.cancel),
                                        getResources().getString(R.string.ok),
                                        null, loginClick).show();

                            }

                        }
                    }
                    return true;
                }
                return false;
            }
        });
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);


        tvFinger = (TextView) findViewById(R.id.tvFinger);
        TextView txtversion = (TextView) findViewById(R.id.txtversion);
        version = getversionclient();
        if (version != null && !version.equals("")) {
            txtversion
                    .setText(getString(R.string.text_version) + " " + version);
        } else {
            txtversion.setText("");
        }
        txtchangePass = (TextView) findViewById(R.id.txtchangePass);
        txtchangePass.setVisibility(View.VISIBLE);
        txtchangePass.setOnClickListener(this);
        txtchangePass.setOnClickListener(this);
        txtresetpass = (TextView) findViewById(R.id.txtresetpass);
        // txtresetpass.setVisibility(View.GONE);
        txtresetpass.setOnClickListener(this);
        // serialSim = "78123467831273";
        act = this;

        // checkDualSim();
        SharedPreferences preferences = getSharedPreferences(Define.PRE_NAME,
                Activity.MODE_PRIVATE);
        String lastLogin = "";
        if (preferences != null) {
            lastLogin = preferences.getString(Define.KEY_LOGIN_NAME, "");
        }
        Session.KPI_REQUEST = true;
        edtUserName.setText(lastLogin);

        serialSim = System.currentTimeMillis() + "";
        // Session.connectService(getInstance());
        // vyvy();
//		 testServiceThat();

        // huypq();
        // tt_tester();
        // tuantm();
//		 hantt();
//		quitt();
        shake = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.shake);
        try {
            CommonActivity.getMemory(LoginActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        CommonActivity.clearCache();
        Fabric.with(this, new Crashlytics());

        dialogGuideSetting = new GuideSettingPermissionDialog(
                this, onClickAcceptGuideSetting);

        if (!checkDonePermission()) {
            //checkDrawOverlayPermission();
            //dialogGuideSetting.show();
            showDialogViewSetting();
        }

        if (CommonActivity.askPermission()) {
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                tele = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                serialSim = tele.getSimSerialNumber();
            }
        }
        if (CommonActivity.isNullOrEmpty(serialSim)) {
            serialSim = System.currentTimeMillis() + "";
        }
    }

    private void initLogUser() {
        Crashlytics.setUserName(edtUserName.getText().toString());
        Crashlytics.setUserEmail(edtUserName.getText().toString());
        Crashlytics.setUserIdentifier(Session.token);
    }

    private void quitt() {
        serialSim = System.currentTimeMillis() + "";
        edtUserName.setText("quitt");
        edtPassword.setText("123456");
    }

    private void nva1() {
        serialSim = "4234893128947124";
        edtUserName.setText("nva1");
        edtPassword.setText("123456a@");
    }

    private void hantt() {
        serialSim = "4234893128947124";
        serialSim = System.currentTimeMillis() + "";
        edtUserName.setText("hantt");
        edtPassword.setText("123456a@");
    }


    private void pmvthuypq15() {
        serialSim = "4234893128947124";
        serialSim = System.currentTimeMillis() + "";
        edtUserName.setText("pmvt_huypq15");
        edtPassword.setText("Mbccs@2016");
    }

    private void tt_tester() {
        serialSim = "4234893128947124";
        edtUserName.setText("tt_tester");
        edtPassword.setText("payment@123");
    }

    private void hadt15() {
        serialSim = "Quang33";
        edtUserName.setText("hadt15");
        edtPassword.setText("123");
    }

    private void giang_ht() {
        serialSim = System.currentTimeMillis() + "";
        edtUserName.setText("vt_test_dgd_gianght1");
        edtPassword.setText("Gianght@241182");
    }

    private void testServiceThat() {
        serialSim = "testWS1";
        edtUserName.setText("tanlm_hcm");
        edtPassword.setText("tan@1987");
        edtUserName.setText("CUONGNV_HPG");
        edtPassword.setText("(*)khanhphuong$400");
        edtUserName.setText("thovp_hue");
        edtPassword.setText("tho123456*");

        // edtUserName.setText("diepdh_hpg");
        // edtPassword.setText("hoanganh@07");
        edtUserName.setText("vt_cdbr_quantn");
        edtPassword.setText("Ngocquan2211@b");
        edtUserName.setText("vt_test_dgd_gianght1");
        edtPassword.setText("trungduc@07112011");
        edtUserName.setText("chintk_cht");
        edtPassword.setText("Viettel!2345678");
        // serialSim = "test";
        // edtUserName.setText("hungnt_hni2");
        // edtPassword.setText("hung..14789*");
        // edtUserName.setText("phongvv_hni1");
        // edtPassword.setText("12phong@phong");
        // edtUserName.setText("1000112100_00019_NVDBCD");
        // edtPassword.setText("phuong@8489");

        // edtUserName.setText("vt_cntt_binhbm");
        // edtPassword.setText("Binh00!!234");
        // edtUserName.setText("1000129100_00045_nvdbcd");123
        // edtPassword.setText("an@123456");
        // edtUserName.setText("anhbh_vt");
        // edtPassword.setText("anhbh@2212");
        // edtUserName.setText("1000146100_00045_nvdbcd");
        // edtPassword.setText("phuc@321");
        edtUserName.setText("vt_cntt_phuoctv");
        edtPassword.setText("HMH@82best");
        edtUserName.setText("TUANTV_HNI_DDV");
        edtPassword.setText("Tuan@187");
        edtUserName.setText("HIEUTT1_HCM");
        edtPassword.setText("Hangdtm@2008");

    }

    private void tuantm() {
        serialSim = "tuantm0";
        edtUserName.setText("tuantm");
        edtPassword.setText("123");
    }

    private void duongtt() {
        serialSim = "bss_duongtt81";
        edtUserName.setText("bss_duongtt8");
        edtPassword.setText("123");
    }

    private void vyvy() {
        // TODO Auto-generated method stub
        // serialSim = "vyvy0";
        // serialSim = "tuantm0";
        edtPassword.setText("123456a@");
        edtUserName.setText("vyvy");
    }

    private void quynh() {
        // TODO Auto-generated method stub
        serialSim = "89840485011972638601";
        // serialSim = "tuantm0";
        edtPassword.setText("123");
        edtUserName.setText("bss_quynhln6");
    }

    private void huypq() {
        // TODO Auto-generated method stub
        serialSim = "89840485011972638570";
        serialSim = "bss_huypq159";
        // serialSim = "tuantm0";
        edtPassword.setText("555555a@");
        edtUserName.setText("bss_huypq15");
    }

    private void checkDualSim() {
        // TelephonyInfo telephonyInfo = TelephonyInfo.getInstance(this);
        //
        // boolean isSIM1Ready = telephonyInfo.isSIM1Ready();
        // boolean isSIM2Ready = telephonyInfo.isSIM2Ready();
        // if (isSIM1Ready) {
        // serialSim = tele.getSimSerialNumber();
        // } else if (isSIM2Ready) {
        // serialSim = tele.getSimSerialNumber();
        // }
        //
        // telephonyInfo
        // .printTelephonyManagerMethodNamesForThisDevice(LoginActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(Constant.REGISTER_RECEIVER));
        edtPassword = (EditText) findViewById(R.id.edtPassword);

        final SharedPreferences preferences = getSharedPreferences(
                Define.PRE_NAME, MODE_PRIVATE);
        String useFinger = preferences.getString(Constant.KEY_FINGER_USER, "");

        // Neu cau hinh co dung van tay

        if (!FingerManager.checkFingerSupported(this)
                || !FingerManager.checkFingerEnable(this)) {
            // lnFingerfin
            findViewById(R.id.lnFinger).setVisibility(View.GONE);
        } else {
            findViewById(R.id.lnFinger).setVisibility(View.VISIBLE);
        }
        try {
            CommonActivity.getMemory(LoginActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if ("1".equals(useFinger) && FingerManager.checkFingerSupported(this)) {

            if (countDownTimer == null) {
                fingerManager.fingerListener(this, onSuccess, onError, onHelp, onFail);
            }


        }
    }

    @Override
    protected void onStop() {
        super.onStop();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
//        AppIndex.AppIndexApi.end(client, getIndexApiAction());
//        unregisterReceiver(receiver);
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client.disconnect();
    }

    @Override
    protected void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
    }

    /**
     * HuyPQ15: Dang nhap he thong
     *
     * @param user
     * @param pass
     * @return
     */


    private BCCSGateway loginGateway = null;

    private String login(String user, String pass, String addInfo) {

        boolean isException = false;
        Exception exception = null;
        try {
            loginGateway = new BCCSGateway();
            loginGateway.addValidateGateway("wscode", "mbccs_loginBccs2");
            loginGateway.addValidateGateway("username", Constant.BCCSGW_USER);
            loginGateway.addValidateGateway("password", Constant.BCCSGW_PASS);

            // kiem tra client da luu keystore chua
//            if (!PreferenceUtils.isKeyPairExist(getApplicationContext(),
//                    serialSim)) {
//                genClientKey = true; //mac dinh dang nhap la sinh lai key
//            }

            // KpiLog
            String envelope = loginGateway.buildInputGatewayWithRawData(
                    buildRawData(false, user, pass, addInfo), 0, true);
            Log.e("envlop Login", envelope);

            String response = loginGateway.sendRequest(envelope,
                    Constant.BCCS_GW_URL, LoginActivity.this, "mbccs_loginBccs2",
                    Constant.LOGIN_TIME_OUT_CONECT,
                    Constant.LOGIN_TIME_OUT_RESPONE);
            Log.e("response Login", response);

            CommonOutput output = loginGateway.parseGWResponse(response);
            if (output == null) {
                errorMessage = getResources().getString(R.string.exception);
                return Constant.ERROR_CODE;
            }
            String original = output.getOriginal();
            if (original != null) {
                Log.d("original", original);
            }

            VSAMenuHandler handler = (VSAMenuHandler) CommonActivity
                    .parseXMLHandler(new VSAMenuHandler(), original);
            output = handler.getItem();

            if (output == null) {
                errorMessage = getResources().getString(R.string.exception);
                return Constant.ERROR_CODE;
            }
            if (!output.getErrorCode().equals("0")) {
                errorMessage = output.getDescription();
                return Constant.ERROR_CODE;
            }
            description = handler.getUpgradeDescription();
            forceUpgrade = handler.getForceUpgrade();
            version = handler.getVersion();
            checkSyn = handler.getCheckSyn();
            Session.setFixErrorVersion(handler.getDebugMsg());
            CacheData.getInstanse().setVersion(version);
            List<VSA> lstMenu = handler.getLstVSAMenu();
            daysBetweenExpried = handler.getDaysBetweenExpried();
            StringBuilder menuList = new StringBuilder(";");
            if (lstMenu != null) {
                for (int i = 0; i < lstMenu.size(); i++) {
                    menuList.append(lstMenu.get(i).getObjectName()).append(";");
                }
            }
            SharedPreferences preferences = getSharedPreferences(
                    Define.PRE_NAME, MODE_PRIVATE);
            String lastLogin = preferences.getString(Define.KEY_LOGIN_NAME, "");
            SharedPreferences.Editor editor = preferences.edit();
            if (!user.equalsIgnoreCase(lastLogin)) {
                deleteDatabase(Define.DB_NAME);
                editor.putString(Constant.KEY_FINGER_USER, "0");
                editor.putString(Constant.KEY_PASS, "");
                editor.putString(Constant.KEY_IV, "");
            }
            editor.putString(Define.KEY_MENU_NAME, menuList.toString());
            editor.putString(Define.KEY_LOGIN_NAME, user.toUpperCase());

            editor.putString(Define.KEY_TRACKING, handler.getIsTracking());

//            if (genClientKey) {
            KeyPairs keyPairs = new KeyPairs();
            keyPairs.setClientPrivateKey(handler.getPrivateKey());
            keyPairs.setViettelPublicKey(handler.getPublicKey());

            Session.setPublicKey(handler.getPublicKey());
            Session.setPrivateKey(handler.getPrivateKey());

//                PreferenceUtils.saveKeyPairInfo(getApplicationContext(),
//                        keyPairs, serialSim);
//            } else {
//                KeyPairs keyPairs = PreferenceUtils.getKeyPairInfo(
//                        getApplicationContext(), serialSim);
//                Session.setPublicKey(keyPairs.getViettelPublicKey());
//                Session.setPrivateKey(keyPairs.getClientPrivateKey());
//            }

            // Mac dinh
            // editor.putString(Define.KEY_TRACKING, "2");

            String token = StringUtils.encryptIt(output.getToken(),
                    getApplicationContext());
            editor.putString(Define.KEY_TEMP, token);
            editor.putString(Define.KEY_INVALID_TOKEN, "0");

            Session.setToken(output.getToken());
            if (!CommonActivity.isNullOrEmpty(addInfo)) {
                userName = addInfo.toUpperCase();
            } else {
                userName = user.toUpperCase();
            }

            Session.passWord = pass;
            String useFinger = preferences.getString(Constant.KEY_FINGER_USER,
                    "");
            if ("1".equals(useFinger)) {
                String[] x = EncryptKeystore.encrypt(pass);
                if (x != null) {
                    editor.putString(Constant.KEY_PASS, x[0]);
                    editor.putString(Constant.KEY_IV, x[1]);
                }

            }

            editor.commit();
            Session.setToken(output.getToken());

            return Constant.SUCCESS_CODE;
        } catch (UnknownHostException ex) {
            isException = true;
            exception = ex;
            Log.e("UnknownHostException", ex.toString(), ex);

            errorMessage = getResources().getString(
                    R.string.unknown_host_exception);
            return Constant.ERROR_CODE;
        } catch (ConnectTimeoutException ex) {
            Log.e("ConnectTimeoutException", ex.getMessage(), ex);
            isException = true;
            exception = ex;
            errorMessage = getResources().getString(
                    R.string.connect_timeout_exception);
            return Constant.ERROR_CODE;
        } catch (SocketTimeoutException ex) {
            Log.e("SocketTimeoutException", ex.getMessage(), ex);
            isException = true;
            exception = ex;
            errorMessage = getResources().getString(
                    R.string.socket_timeout_exception);
            return Constant.ERROR_CODE;
        } catch (SocketException ex) {
            Log.e("SocketException", ex.getMessage(), ex);
            isException = true;
            exception = ex;
            errorMessage = getResources().getString(
                    R.string.socket_close_exception);
            return Constant.ERROR_CODE;

        } catch (IllegalStateException ex) {
            Log.e("IllegalStateException", ex.getMessage(), ex);
            isException = true;
            exception = ex;
            errorMessage = getResources().getString(
                    R.string.illegal_state_exception);
            return Constant.ERROR_CODE;
        } catch (IllegalArgumentException ex) {
            isException = true;
            exception = ex;
            Log.e("IllegalArgumentException", ex.getMessage(), ex);

            errorMessage = getResources().getString(
                    R.string.unknown_host_exception)
                    + ex.toString();
            return Constant.ERROR_CODE;
        } catch (IOException ex) {
            Log.e("IOException", ex.getMessage(), ex);
            isException = true;
            exception = ex;
            errorMessage = getResources().getString(
                    R.string.socket_io_exception);
            return Constant.ERROR_CODE;
        } catch (Exception ex) {
            Log.e("Exception", ex.getMessage(), ex);
            isException = true;
            exception = ex;
            errorMessage = getResources().getString(R.string.exception);
            return Constant.ERROR_CODE;
        } finally {
            try {
                if (isException) {
                    SaveLog saveLog = new SaveLog(LoginActivity.this,
                            Constant.SYSTEM_NAME, userName,
                            "loginBccs2_exception", CommonActivity
                            .findMyLocation(LoginActivity.this).getX(),
                            CommonActivity.findMyLocation(LoginActivity.this)
                                    .getY());
                    saveLog.saveLogException(exception, new Date(), new Date(), userName + "_"
                            + CommonActivity.getDeviceId(this) + "_"
                            + System.currentTimeMillis());
                }
            } catch (Exception e) {

            }
        }
    }

    private String buildRawData(Boolean isSMS, String user, String pass, String addInfo)
            throws Exception {
        BCCSGateway input = new BCCSGateway();
        StringBuilder result = new StringBuilder();
        result.append("<ws:login>");
        result.append("<userName>");
        result.append(new SecurityUtil().encrypt(user));
        result.append("</userName>");
        result.append("<passWord>");
        result.append(new SecurityUtil().encrypt(pass));
        result.append("</passWord>");

        result.append("<addInfo>");
        result.append(new SecurityUtil().encrypt(addInfo));
        result.append("</addInfo>");

        result.append("<clientTime>");
        result.append(System.currentTimeMillis());
        result.append("</clientTime>");
        result.append("<version>");
        result.append(getversionclient());
        result.append("</version>");
        result.append("<serialSim>");
        if (serialSim == null || serialSim.trim().isEmpty()) {
            serialSim = user.toUpperCase();
        }
        result.append(serialSim);
        result.append("</serialSim>");
        result.append("</ws:login>");

        // input.addParam("userName", user);
        // input.addParam("passWord", pass);
        // input.addParam("serialSim", serialSim);

        return result.toString();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
//    private Action getIndexApiAction() {
//        Thing object = new Thing.Builder()
//                .setName("Login Page") // TODO: Define a title for the content shown.
//                // TODO: Make sure this auto-generated URL is correct.
//                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
//                .build();
//        return new Action.Builder(Action.TYPE_VIEW)
//                .setObject(object)
//                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
//                .build();
//    }
    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client.connect();
//        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    //
    // private String syncDatabase() {
    // try {
    //
    // FirstSyncBusiness syncBusiness = new FirstSyncBusiness(this);
    // syncBusiness.sync(Session.token, Session.userName);
    // return Constant.SUCCESS_CODE;
    // } catch (Exception e) {
    // Log.e(LoginActivity.class.getName(), "Exception", e);
    // errorMessage = getResources().getString(R.string.exception)
    // + e.toString();
    // return Constant.ERROR_CODE;
    // }
    // // return Constant.SUCCESS_CODE;
    // }

    /**
     * @author huypq15
     */
    private class LoginAsyncTask extends AsyncTask<String, String, String> {
        private final ProgressDialog progress;
        private final Activity activity;
        private CountDownTimer mCountDownTimer;
        private float count = 0;
        private int maxProgess;

        public LoginAsyncTask(Activity activity) {
            this.activity = activity;
            progress = new ProgressDialog(activity);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            if (progress != null) {
                String message = getResources().getString(R.string.logining);
                if (values != null && values.length > 0) {
                    message += " " + values[0] + " s";
                }
                progress.setMessage(message);
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progress != null && !progress.isShowing()) {
                progress.setMessage(getResources().getString(R.string.logining));
                progress.setCancelable(false);
                progress.setIndeterminate(false);
                progress.setProgress(0);
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.show();

                maxProgess = Constant.LOGIN_TIME_OUT_RESPONE / 100;
                mCountDownTimer = new CountDownTimer(
                        Constant.LOGIN_TIME_OUT_RESPONE, 100) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        // Log.v(this.getClass().getSimpleName(), "onTick " +
                        // count + " " + millisUntilFinished);
                        count++;
                        if (progress != null && progress.isShowing()) {
                            publishProgress("" + count / 10);
                            progress.setProgress(100 - (int) (count * 100 / maxProgess));
                        }
                    }

                    @Override
                    public void onFinish() {
                        Log.v(this.getClass().getSimpleName(), "onFinish "
                                + count);
                        if (progress != null && progress.isShowing()) {
                            publishProgress(""
                                    + Constant.LOGIN_TIME_OUT_RESPONE / 1000);
                            progress.setProgress(100);
                        } else {
                            Log.v(this.getClass().getSimpleName(),
                                    "onFinish progress NULL or Not Showing");
                        }
//						if (loginGateway != null) {
//							try {
//								// check request HTTP and shutdown
//								loginGateway.httpClient.getConnectionManager()
//										.shutdown();
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
//						}
                    }
                };
                mCountDownTimer.start();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            if (edtUserName.getText().toString().trim().isEmpty()) {
                return ERROR_USER_MISSING;
            }
            if (edtPassword.getText().toString().trim().isEmpty()) {
                return ERROR_PASSWORD_MISSING;
            }

            // thientv7 them phan bo sung thong tin user
            // KpiLog
            String userNameContain = edtUserName.getText().toString();

            if ((Constant.addInfo)) {
                if (userNameContain.contains(";,;")) {
                    String[] parts = userNameContain.split(";,;");

                    String part1 = parts[0];
                    String part2 = parts[1];

                    Session.userName = part2;
                    userNameContain = part1;
                    addInfo = part2;
                } else {
                    Session.userName = userNameContain;
                }

            } else {
                userName = userNameContain;
            }

            if (!CommonActivity.isInternetReachable()) {
                return ERROR_PING_SERVER;
            }
            return login(userNameContain, edtPassword
                    .getText().toString(), addInfo);
        }

        @Override
        protected void onPostExecute(String result) {
            if (!isFinishing() && progress != null && progress.isShowing()) {
                progress.dismiss();
            }
            if (mCountDownTimer != null) {
                mCountDownTimer.cancel();
            }
            if (Constant.SUCCESS_CODE.equals(result)) {
                initLogUser();

                OnClickListener changePass = new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {

                        showPopupChangePass();
                    }
                };
                OnClickListener later = new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        successLogin();
                    }
                };
                if (daysBetweenExpried != null && !daysBetweenExpried.isEmpty()) {
                    String msg = activity.getString(
                            R.string.password_will_expire, daysBetweenExpried);
                    if ("0".equals(daysBetweenExpried)) {
                        msg = activity.getString(
                                R.string.password_expire_today,
                                daysBetweenExpried);
                    }
                    CommonActivity.createDialog(LoginActivity.this, msg,
                            activity.getString(R.string.app_name),
                            activity.getString(R.string.later),
                            activity.getString(R.string.change_pass_now),
                            later, changePass).show();
                } else {
                    successLogin();
                }
            } else {
                fingerManager.fingerListener(LoginActivity.this, onSuccess,
                        onError, onHelp, onFail);

                if (ERROR_USER_MISSING.equals(result)) {
                    String message = getString(R.string.userNameRequired);
                    String title = getString(R.string.app_name);
                    Dialog dialog = CommonActivity.createAlertDialog(activity,
                            message, title);
                    dialog.show();
                    edtUserName.requestFocus();
                } else if (ERROR_PASSWORD_MISSING.equals(result)) {
                    String message = getString(R.string.passwordRequired);
                    String title = getString(R.string.app_name);
                    Dialog dialog = CommonActivity.createAlertDialog(activity,
                            message, title);
                    dialog.show();
                    edtPassword.requestFocus();
                } else if (Constant.ERROR_CODE.equals(result)) {
                    String message = getString(R.string.login_error);
                    if (!CommonActivity.isNullOrEmpty(errorMessage)) {
                        message = errorMessage;
                    }
                    String title = getString(R.string.app_name);

                    Dialog dialog = CommonActivity.createAlertDialog(activity,
                            message, title);
                    dialog.show();
                } else if (ERROR_PING_SERVER.equals(result)) {
                    String message = getString(R.string.login_error);
                    String title = getString(R.string.app_name);
                    Dialog dialog = CommonActivity.createDialog(activity, message,
                            title, getString(R.string.ok),
                            getString(R.string.check_apn), null, showAPNSetting);
                    dialog.show();
                }
            }
        }

        private void successLogin() {

//             ===========run check version==============
            if (forceUpgrade.equalsIgnoreCase("0")
                    && !version.equals(getversionclient())) {
                // ========== khong bat buoc cap nhat version===== show
                // aletdialog
                Dialog dialog = CommonActivity.createDialog(
                        LoginActivity.this,
                        description
                                + "\n"
                                + getApplicationContext().getResources()
                                .getString(R.string.isversion),
                        getApplicationContext().getResources().getString(
                                R.string.updateversion),
                        getApplicationContext()
                                .getResources().getString(R.string.cancel), getApplicationContext().getResources().getString(
                                R.string.ok),
                        cancelUpdateVer, onclickCheckUpdate);
                dialog.show();

            } else if (forceUpgrade.equalsIgnoreCase("1")
                    && !version.equals(getversionclient())) {
                // ========== bat buoc cap nhat ======================
                Log.d("versionnnnnserrver", version);
                UpdateVersionAsyn updateVersionAsyn = new UpdateVersionAsyn(
                        LoginActivity.this, Constant.PATH_UPDATE_VERSION
                        + Session.token);
                updateVersionAsyn.execute();

            } else {
                // ==========chay dong bo du lieu ==========================
                boolean isDatabaseExists = DatabaseUtils.doesDatabaseExist(
                        LoginActivity.this, Define.DB_NAME);
                Log.e("DatabaseExists", "" + isDatabaseExists);
                // Dang nhap thanh cong, kiem tra ton tai database
                if (isDatabaseExists) {
                    if (!CommonActivity.isNullOrEmpty(checkSyn)
                            && "1".equals(checkSyn)) {
                        //HuyPQ15 fix tam luon luon dong bo du lieu neu la ban moi, neu trien khai dien rong thi xoa di, mo comment doan tren ra
//                    if (true) {
                        SharedPreferences preferences = getSharedPreferences(
                                Define.PRE_NAME, MODE_PRIVATE);
                        String lastVersion = preferences.getString(
                                Define.KEY_LAST_VERSION, "");
                        PackageInfo packageInfo;

                        try {
                            packageInfo = getPackageManager().getPackageInfo(
                                    getPackageName(), 0);
                            String currentVersion = packageInfo.versionName;
                            if (lastVersion == null
                                    || !lastVersion.equals(currentVersion)) {
                                deleteDatabase(Define.DB_NAME);
                                SyncAsyncTask sync = new SyncAsyncTask(
                                        LoginActivity.this);
                                sync.execute(Session.getToken(),
                                        userName);
                            } else {
                                try {
                                    InfrastrucureDB mInfrastrucureDB = new InfrastrucureDB(
                                            getApplicationContext());
                                    String shop[] = mInfrastrucureDB
                                            .getProvince();
                                    Session.province = shop[0];
                                    Session.district = shop[1];
                                    mInfrastrucureDB.close();
                                    Session.loginUser = StaffBusiness
                                            .getStaffByStaffCode(
                                                    LoginActivity.this,
                                                    userName);
                                } catch (Exception ignored) {
                                }
                                // Neu database da ton tai, login vao man
                                // hinh chinh
                                Intent i = new Intent(LoginActivity.this,
                                        MainActivity.class);
                                clearOmniStaff();
                                startActivity(i);
                            }
                        } catch (NameNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            InfrastrucureDB mInfrastrucureDB = new InfrastrucureDB(
                                    getApplicationContext());
                            String shop[] = mInfrastrucureDB.getProvince();
                            Session.province = shop[0];
                            Session.district = shop[1];
                            mInfrastrucureDB.close();
                            Session.loginUser = StaffBusiness
                                    .getStaffByStaffCode(LoginActivity.this,
                                            userName);
                        } catch (Exception ignored) {
                        }
                        // Neu database da ton tai, login vao man hinh chinh
                        Intent i = new Intent(LoginActivity.this,
                                MainActivity.class);
                        clearOmniStaff();
                        startActivity(i);
                    }
                    // finish();
                } else {
                    SyncAsyncTask sync = new SyncAsyncTask(LoginActivity.this);
                    sync.execute(Session.getToken(), userName);
                }
            }
        }
    }

    private OnClickListener showAPNSetting = new OnClickListener() {
        @Override
        public void onClick(View arg0) {
            startActivityForResult(new Intent(Settings.ACTION_APN_SETTINGS), 0);
//            Intent intent = new Intent();
//            intent.setAction(Intent.ACTION_VIEW);
//            intent.setClassName("com.android.settings",
//                    "com.android.settings.ApnSettings");
//            startActivity(intent);
        }
    };


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
            progress.setCancelable(false);
        }
    }

    private ProgressDialog progress;

    /**
     * @author huypq15
     */
    private class SyncAsyncTask extends AsyncTask<String, String, String> {

        String shop[] = new String[2];
        private final Activity activity;

        public SyncAsyncTask(Activity activity) {
            this.activity = activity;
            showProgressDialog(activity);
        }

        @Override
        protected String doInBackground(String... params) {
            return sync(params[0], params[1]);

        }

        @Override
        protected void onPostExecute(String result) {
            dismissProgressDialog();

            if (Constant.SUCCESS_CODE.equals(result)) {
                // Dong bo du lieu thanh cong, luu bien staff vao session, vao
                // man hinh chinh
                // chinh
                InfrastrucureDB mInfrastrucureDB = new InfrastrucureDB(activity);
                shop = mInfrastrucureDB.getProvince();
                mInfrastrucureDB.close();
                Session.loginUser = StaffBusiness.getStaffByStaffCode(
                        LoginActivity.this, userName);
                Session.province = shop[0];
                Session.district = shop[1];
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                clearOmniStaff();
                startActivity(i);
                if (progress != null && progress.isShowing()) {
                    progress.dismiss();
                }
                finish();
            } else if (Constant.ERROR_CODE.equals(result)) {
                String message = errorMessage;
                String title = getString(R.string.app_name);
                Dialog dialog = CommonActivity.createAlertDialog(activity,
                        message, title);
                dialog.show();
            }


        }

        public String sync(String token, String userName) {
            Log.e("downloadAndSyn", "Start Download >>>>>>>>>>>>>>>> ");
            String result = "";
            String url = Constant.PATH_DOWNLOAD;
            HttpURLConnection conn = null;
            try {
//                Long start = System.currentTimeMillis();
//                Log.d(LoginActivity.class.getSimpleName(),"START DOWN :=========> " + start);
                URL url1 = new URL(url);

                Log.i("Bo nho con trong", Double.toString(SdCardHelper
                        .getAvailableInternalMemorySize()));

                double availAbleMemory = SdCardHelper
                        .getAvailableInternalMemorySize();

                if (availAbleMemory > Constant.checkmemoryPhone) {

//                    InputStream input = new BufferedInputStream(
//                            url1.openStream());
                    conn = (HttpURLConnection) url1.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("token", token);
                    conn.setConnectTimeout(Constant.TIMEOUT_SYNC_DATA_CONNECT);
                    conn.setReadTimeout(Constant.TIMEOUT_SYNC_DATA_CONNECT);
                    if (conn.getResponseCode() != 200) {
                        errorMessage = getString(R.string.syndatafails);
                        return Constant.ERROR_CODE;

                    }
                    File databaseFolder = new File(Define.PATH_DATABASE);
                    if (!databaseFolder.exists()) {
                        databaseFolder.mkdirs();
                    }
                    String databaseFilePath = Define.PATH_DATABASE + userName
                            + ".zip";
                    OutputStream output = new FileOutputStream(databaseFilePath);
                    byte data[] = new byte[1024];
                    int count = 0;
                    BufferedInputStream input = new BufferedInputStream(conn.getInputStream());
                    while ((count = input.read(data)) != -1) {
                        output.write(data, 0, count);
                    }

                    output.flush();
                    output.close();
                    input.close();
//                    Long endtime = System.currentTimeMillis()-start;
//                    Log.d(LoginActivity.class.getSimpleName(),"END DOWN :=========> " + endtime);
                    File zipFile = new File(databaseFilePath);
                    File desFolder = new File(Define.PATH_DATABASE);
                    ZipUtils.unzip(zipFile, desFolder);

                    String pathData = Define.PATH_DATABASE
                            + userName.toUpperCase();
                    File file2 = new File(pathData);
                    File newFileName = new File(Define.PATH_DATABASE
                            + "smartphone");
                    file2.renameTo(newFileName);
                    zipFile.delete();
                    Log.e("FilePath", Define.PATH_DATABASE);
//                    Log.d(LoginActivity.class.getSimpleName(), "End Unzip >>>>>>>>>>>>>>>> " + (System.currentTimeMillis() - endtime));
                    result = Constant.SUCCESS_CODE;

                } else {

                    result = Constant.ERROR_CODE;
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }

            }
            return result;
        }


    }


    private String getversionclient() {
        String versionclient = "";
        try {
            PackageInfo packageInfo = getApplicationContext()
                    .getPackageManager().getPackageInfo(getPackageName(), 0);
            versionclient = packageInfo.versionName;
            Log.d("versionclienttttttttttttttttttt", versionclient);
            CacheData.getInstanse().setVersionclient(versionclient);
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return versionclient;
    }

    // ==============Asyn check version ===============
    public class checkVersionAsyn extends AsyncTask<Void, Void, String> {
        private Context context = null;
        private final XmlDomParse parse = new XmlDomParse();
        String errorMessage = null;
        String forceUpgrade = "";
        String version = "";
        String description = "";

        private checkVersionAsyn(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(Void... params) {
            return CheckVersionAsyn();
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                if (forceUpgrade.equalsIgnoreCase("0")
                        && version.compareTo(getversionclient()) > 0) {

                    // ========== khong bat buoc cap nhat version===== show
                    // aletdialog
                    Dialog dialog = CommonActivity.createDialog(
                            LoginActivity.this,
                            description
                                    + "\n"
                                    + context.getResources().getString(
                                    R.string.isversion),
                            context.getResources().getString(
                                    R.string.updateversion),
                            context.getResources().getString(R.string.cancel), context
                                    .getResources().getString(R.string.ok),
                            cancelUpdateVer, onclickCheckUpdate);
                    dialog.show();

                } else if (forceUpgrade.equalsIgnoreCase("1")) {
                    // ========== bat buoc cap nhat ======================

                    UpdateVersionAsyn updateVersionAsyn = new UpdateVersionAsyn(
                            context, Constant.PATH_UPDATE_VERSION
                            + Session.token);
                    updateVersionAsyn.execute();

                } else {
                    // ==========chay dong bo du lieu ==========================
                    boolean isDatabaseExists = DatabaseUtils.doesDatabaseExist(
                            (Activity) context, Define.DB_NAME);
                    Log.e("DatabaseExists", "" + isDatabaseExists);
                    // Dang nhap thanh cong, kiem tra ton tai database
                    if (isDatabaseExists) {
                        InfrastrucureDB mInfrastrucureDB = new InfrastrucureDB(
                                context);
                        String shop[] = mInfrastrucureDB.getProvince();
                        mInfrastrucureDB.close();
                        Session.province = shop[0];
                        Session.district = shop[1];
                        Session.loginUser = StaffBusiness.getStaffByStaffCode(
                                LoginActivity.this, userName);
                        // Neu database da ton tai, login vao man hinh chinh
                        Intent i = new Intent(LoginActivity.this,
                                MainActivity.class);
                        clearOmniStaff();
                        startActivity(i);
                        // finish();
                    } else {
                        SyncAsyncTask sync = new SyncAsyncTask(
                                (Activity) context);
                        sync.execute(Session.getToken(), userName);
                    }
                }

            }
        }

        public String CheckVersionAsyn() {
            String original = null;

            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_checkUpgradeVersion");
                String rawData = "<ws:checkUpgradeVersion>" +
                        "<versionInput>" +
                        "<token>" +
                        Session.getToken() +
                        "</token>" +
                        "</versionInput>" +
                        "</ws:checkUpgradeVersion>";

                String envelope = input.buildInputGatewayWithRawData(rawData);
                Log.d("Send evelop", envelope);
                String response = input.sendRequest(envelope,
                        Constant.BCCS_GW_URL, LoginActivity.this,
                        "mbccs_checkUpgradeVersion");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);

                original = output.getOriginal();
                Log.d("originalllllllll", original);

                // ============pasrese xml ====================
                Document doc = parse.getDomElement(original);
                NodeList nl = doc.getElementsByTagName("return");
                for (int i = 0; i < nl.getLength(); i++) {
                    Element e1 = (Element) nl.item(i);
                    String errorCode = parse.getValue(e1, "errorCode");
                    Log.d("errorcode", errorCode);
                    original = errorCode;
                    description = parse.getValue(e1, "description");
                    Log.d("description", description);
                    forceUpgrade = parse.getValue(e1, "forceUpgrade");
                    Log.d("forceUpgrade", forceUpgrade);
                    version = parse.getValue(e1, "version");
                    Log.d("versionnnnnnnnnnnnnnnnnnnn", version);
                    CacheData.getInstanse().setVersion(version);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return original;
        }
    }

    // check updateDateVersion
    private final OnClickListener onclickCheckUpdate = new OnClickListener() {

        @Override
        public void onClick(View v) {
            UpdateVersionAsyn updateVersionAsyn = new UpdateVersionAsyn(
                    LoginActivity.this, Constant.PATH_UPDATE_VERSION
                    + Session.token);
            updateVersionAsyn.execute();
        }
    };
    // cacel updateVersion
    private final OnClickListener cancelUpdateVer = new OnClickListener() {

        @Override
        public void onClick(View v) {
            boolean isDatabaseExists = DatabaseUtils.doesDatabaseExist(
                    LoginActivity.this, Define.DB_NAME);
            Log.e("DatabaseExists", "" + isDatabaseExists);
            // Dang nhap thanh cong, kiem tra
            // ton tai database
            if (isDatabaseExists) {
                if (!CommonActivity.isNullOrEmpty(checkSyn)
                        && "1".equals(checkSyn)) {
                    SharedPreferences preferences = getSharedPreferences(
                            Define.PRE_NAME, MODE_PRIVATE);
                    String lastVersion = preferences.getString(
                            Define.KEY_LAST_VERSION, "");
                    PackageInfo packageInfo;

                    try {
                        packageInfo = getPackageManager().getPackageInfo(
                                getPackageName(), 0);
                        String currentVersion = packageInfo.versionName;
                        if (lastVersion == null
                                || !lastVersion.equals(currentVersion)) {
                            deleteDatabase(Define.DB_NAME);
                            SyncAsyncTask sync = new SyncAsyncTask(
                                    LoginActivity.this);
                            sync.execute(Session.getToken(), userName);
                        } else {
                            try {
                                InfrastrucureDB mInfrastrucureDB = new InfrastrucureDB(
                                        getApplicationContext());
                                String shop[] = mInfrastrucureDB.getProvince();
                                Session.province = shop[0];
                                Session.district = shop[1];
                                mInfrastrucureDB.close();
                                Session.loginUser = StaffBusiness
                                        .getStaffByStaffCode(
                                                LoginActivity.this,
                                                userName);
                            } catch (Exception ignored) {
                            }
                            // Neu database da ton tai, login vao man hinh chinh
                            Intent i = new Intent(LoginActivity.this,
                                    MainActivity.class);
                            clearOmniStaff();
                            startActivity(i);
                        }
                    } catch (NameNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    try {
                        InfrastrucureDB mInfrastrucureDB = new InfrastrucureDB(
                                getApplicationContext());
                        String shop[] = mInfrastrucureDB.getProvince();
                        Session.province = shop[0];
                        Session.district = shop[1];
                        mInfrastrucureDB.close();
                        Session.loginUser = StaffBusiness.getStaffByStaffCode(
                                LoginActivity.this, userName);
                    } catch (Exception ignored) {
                    }
                    // Neu database da ton tai, login vao man hinh chinh
                    Intent i = new Intent(LoginActivity.this,
                            MainActivity.class);
                    clearOmniStaff();
                    startActivity(i);
                }
            } else {
                SyncAsyncTask sync = new SyncAsyncTask(LoginActivity.this);
                sync.execute(Session.getToken(), userName);
            }
        }
    };

    @Override
    public void onClick(View v) {
        try {
            if (v.equals(txtchangePass)) {

                showPopupChangePass();

            }
            if (v.equals(txtresetpass)) {
                showPopupResetPass();
            }
            if (v.equals(btnLogin)) {
                FragmentLoginNotData.mView = null;
                edtUserName.setText(edtUserName.getText().toString().trim());
                edtPassword.setText(edtPassword.getText().toString().trim());
                if (edtUserName.getText().toString().isEmpty()) {
                    String message = getString(R.string.userNameRequired);
                    String title = getString(R.string.app_name);
                    Dialog dialog = CommonActivity.createAlertDialog(this,
                            message, title);
                    dialog.show();
                    edtUserName.requestFocus();
                    edtUserName.startAnimation(shake);
                } else if (edtPassword.getText().toString().isEmpty()) {
                    String message = getString(R.string.passwordRequired);
                    String title = getString(R.string.app_name);
                    Dialog dialog = CommonActivity.createAlertDialog(this,
                            message, title);
                    dialog.show();
                    edtPassword.requestFocus();
                } /*
                 * else if (serialSim == null || serialSim.isEmpty()) { String
				 * message = getString(R.string.serialSimNotFound); String title
				 * = getString(R.string.app_name); Dialog dialog =
				 * CommonActivity.createAlertDialog(this, message, title);
				 * dialog.show(); }
				 */ else {

                    if (CommonActivity.isNetworkConnected(this)) {
                        new LoginAsyncTask(this).execute();
                    } else {
                        OnClickListener loginClick = new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                dialogSendSMS = new ProgressDialog(
                                        LoginActivity.this);
                                dialogSendSMS.setMessage(getResources()
                                        .getString(R.string.processing));
                                dialogSendSMS.setCancelable(false);
                                if (!dialogSendSMS.isShowing()) {
                                    dialogSendSMS.show();
                                }
                                String synTask = "0SI";

                                try {
                                    CommonActivity.sendSMS(
                                            Constant.EXCHANGE_ADDRESS,
                                            buildRawData(true, edtUserName
                                                    .getText().toString()
                                                    .trim(), edtPassword
                                                    .getText().toString()
                                                    .trim(), addInfo),
                                            LoginActivity.this, dialogSendSMS,
                                            synTask);
                                } catch (Exception e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }

                                new CountDownTimer(Constant.TIMEOUT_SMS, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                    }

                                    @Override
                                    public void onFinish() {
                                        if (dialogSendSMS.isShowing()) {
                                            dialogSendSMS.dismiss();
                                            Toast.makeText(
                                                    LoginActivity.this,
                                                    getResources()
                                                            .getString(
                                                                    R.string.time_out_sms),
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }.start();

                            }
                        };

                        CommonActivity.createDialog(
                                LoginActivity.this,
                                getResources().getString(
                                        R.string.no_network_message),
                                getResources().getString(
                                        R.string.no_network_title),
                                getResources().getString(R.string.cancel),
                                getResources().getString(R.string.ok),
                                null, loginClick).show();

                    }

                }
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.toString(), e);
            CommonActivity.createAlertDialog(
                    this,
                    getResources().getString(R.string.exception) + " "
                            + e.toString(),
                    getResources().getString(R.string.app_name));
        }

    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {

        @SuppressLint("DefaultLocale")
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String msg = intent.getStringExtra("msg_body");
                Log.d("msg", "msg :" + msg);
                String[] result = msg.split(";", 7);

                if (result.length > 3) {
                    Session.setToken(result[0]);
                    String syntax = result[1];
                    Log.d("syntax", syntax);
                    String errorCode = result[3];
                    String message = getResources().getString(
                            R.string.login_fail);
                    if (syntax.equalsIgnoreCase(Constant.LOGIN_SYNTAX)) {
                        if (errorCode.equals("0")) {
                            // Dang nhap thanh cong,
                            // Kiem tra version
                            PackageInfo packageInfo = getApplicationContext()
                                    .getPackageManager().getPackageInfo(
                                            getPackageName(), 0);
                            String currentVersion = packageInfo.versionName;
                            String newVersion = result[5];
                            if (!currentVersion.equals(newVersion)
                                    && "1".equalsIgnoreCase(result[6])) {
                                // neu khac phien ban va bat buoc cap nhat,
                                // khong cho
                                // dang nhap
                                CommonActivity
                                        .createAlertDialog(
                                                LoginActivity.this,
                                                getResources()
                                                        .getString(
                                                                R.string.update_version_required),
                                                getResources().getString(
                                                        R.string.app_name),
                                                dismisDialog).show();
                                return;
                            }
                            // Kiem tra database
                            boolean isDatabaseExists = DatabaseUtils
                                    .doesDatabaseExist((Activity) context,
                                            Define.DB_NAME);
                            if (!isDatabaseExists) {
                                CommonActivity
                                        .createAlertDialog(
                                                LoginActivity.this,
                                                getResources()
                                                        .getString(
                                                                R.string.login_database_required),
                                                getResources().getString(
                                                        R.string.app_name),
                                                dismisDialog).show();
                                return;
                            }

                            SharedPreferences preferences = getSharedPreferences(
                                    Define.PRE_NAME, MODE_PRIVATE);
                            String lastLogin = preferences.getString(
                                    Define.KEY_LOGIN_NAME, "");
                            if (!edtUserName.getText().toString().trim()
                                    .equalsIgnoreCase(lastLogin)) {
                                // deleteDatabase(Define.DB_NAME);
                                CommonActivity
                                        .createAlertDialog(
                                                LoginActivity.this,
                                                getResources()
                                                        .getString(
                                                                R.string.login_database_required),
                                                getResources().getString(
                                                        R.string.app_name),
                                                dismisDialog).show();

                                return;
                            }

                            // insert thong tin role quyen
                            String roles[] = result[4].split(",");
                            String role = ApParamBusiness.getListMenu(
                                    LoginActivity.this, roles);
                            SharedPreferences.Editor editor = preferences
                                    .edit();
                            editor.putString(Define.KEY_MENU_NAME, role);
                            editor.putString(Define.KEY_LOGIN_NAME, edtUserName
                                    .getText().toString().trim().toUpperCase());
                            editor.commit();
                            userName = edtUserName.getText().toString()
                                    .trim().toUpperCase();
                            InfrastrucureDB mInfrastrucureDB = new InfrastrucureDB(
                                    LoginActivity.this);
                            String shop[] = mInfrastrucureDB.getProvince();
                            Session.loginUser = StaffBusiness
                                    .getStaffByStaffCode(LoginActivity.this,
                                            userName);
                            Session.province = shop[0];
                            Session.district = shop[1];
                            Intent i = new Intent(LoginActivity.this,
                                    MainActivity.class);
                            clearOmniStaff();
                            startActivity(i);

                        } else {
                            if (result[2] != null && !result[2].isEmpty()) {
                                message = result[4];
                            }
                            CommonActivity
                                    .createAlertDialog(
                                            LoginActivity.this,
                                            message,
                                            getResources().getString(
                                                    R.string.app_name)).show();
                        }

                    }

                    if (dialogSendSMS != null && dialogSendSMS.isShowing()) {
                        dialogSendSMS.dismiss();
                    }
                }
            } catch (Exception e) {
                Log.e(LOG_TAG, e.toString(), e);
                CommonActivity.createAlertDialog(
                        LoginActivity.this,
                        getResources().getString(R.string.exception) + " "
                                + e.toString(),
                        getResources().getString(R.string.app_name)).show();
                if (dialogSendSMS != null && dialogSendSMS.isShowing()) {
                    dialogSendSMS.dismiss();
                }
            }

        }
    };

    private final OnClickListener dismisDialog = new OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (dialogSendSMS != null && dialogSendSMS.isShowing()) {
                dialogSendSMS.dismiss();
            }
        }
    };

    // change password popup
    private void showPopupChangePass() {
        dialogChangePass = new Dialog(LoginActivity.this);
        dialogChangePass.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogChangePass.setContentView(R.layout.layout_change_pass);
        editUserName = (EditText) dialogChangePass
                .findViewById(R.id.edtUserName);
        edtOldPassword = (EditText) dialogChangePass
                .findViewById(R.id.edtOldPassword);
        edtNewPassword = (EditText) dialogChangePass
                .findViewById(R.id.edtNewPassword);
        edtConfirmPassword = (EditText) dialogChangePass
                .findViewById(R.id.edtConfirmPassword);
        if (edtUserName.getText() != null
                && !edtUserName.getText().toString().isEmpty()) {
            editUserName.setText(edtUserName.getText().toString());
        }
        editUserName.setText(editUserName.getText().toString().trim());
        edtOldPassword.setText(edtOldPassword.getText().toString().trim());
        edtNewPassword.setText(edtNewPassword.getText().toString().trim());
        edtConfirmPassword.setText(edtConfirmPassword.getText().toString()
                .trim());

        dialogChangePass.findViewById(R.id.btnchangepass).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        if (!editUserName.getText().toString().isEmpty()) {
                            if (!edtOldPassword.getText().toString().isEmpty()) {
                                if (!edtNewPassword.getText().toString()
                                        .isEmpty()) {
                                    if (!edtConfirmPassword.getText()
                                            .toString().isEmpty()) {
                                        // check newpass # oldpass
                                        if (!edtOldPassword
                                                .getText()
                                                .toString()
                                                .equalsIgnoreCase(
                                                        edtNewPassword
                                                                .getText()
                                                                .toString())) {
                                            // check new password same repeat
                                            // pass
                                            if (edtNewPassword
                                                    .getText()
                                                    .toString()
                                                    .equalsIgnoreCase(
                                                            edtConfirmPassword
                                                                    .getText()
                                                                    .toString())) {
                                                if (CommonActivity
                                                        .isNetworkConnected(LoginActivity.this)) {

                                                    CommonActivity
                                                            .createDialog(
                                                                    LoginActivity.this,
                                                                    getString(R.string.confirmchangepassaction),
                                                                    getString(R.string.app_name),
                                                                    getString(R.string.buttonOk),
                                                                    getString(R.string.buttonCancel),
                                                                    onclickConfirmChangePass,
                                                                    null)
                                                            .show();

                                                } else {
                                                    CommonActivity
                                                            .createAlertDialog(
                                                                    LoginActivity.this,
                                                                    getString(R.string.errorNetwork),
                                                                    getString(R.string.app_name))
                                                            .show();
                                                }

                                            } else {
                                                String message = getString(R.string.password_not_same_repass);
                                                String title = getString(R.string.app_name);
                                                Dialog dialog = CommonActivity
                                                        .createAlertDialog(
                                                                LoginActivity.this,
                                                                message, title);
                                                dialog.show();

                                            }

                                        } else {
                                            String message = getString(R.string.password_not_same);
                                            String title = getString(R.string.app_name);
                                            Dialog dialog = CommonActivity
                                                    .createAlertDialog(
                                                            LoginActivity.this,
                                                            message, title);
                                            dialog.show();
                                        }
                                    } else {
                                        String message = getString(R.string.passwordConfirmRequired);
                                        String title = getString(R.string.app_name);
                                        Dialog dialog = CommonActivity
                                                .createAlertDialog(
                                                        LoginActivity.this,
                                                        message, title);
                                        dialog.show();
                                        edtConfirmPassword.requestFocus();
                                    }
                                } else {
                                    String message = getString(R.string.passwordNewRequired);
                                    String title = getString(R.string.app_name);
                                    Dialog dialog = CommonActivity
                                            .createAlertDialog(
                                                    LoginActivity.this,
                                                    message, title);
                                    dialog.show();
                                    edtNewPassword.requestFocus();
                                }
                            } else {
                                String message = getString(R.string.passwordOldRequired);
                                String title = getString(R.string.app_name);
                                Dialog dialog = CommonActivity
                                        .createAlertDialog(LoginActivity.this,
                                                message, title);
                                dialog.show();
                                edtOldPassword.requestFocus();
                            }
                        } else {
                            String message = getString(R.string.userNameRequired);
                            String title = getString(R.string.app_name);
                            Dialog dialog = CommonActivity.createAlertDialog(
                                    LoginActivity.this, message, title);
                            dialog.show();
                            editUserName.requestFocus();
                        }

                    }
                });
        dialogChangePass.findViewById(R.id.btncanel).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        dialogChangePass.cancel();
                    }
                });

        dialogChangePass.show();
    }

    private void showPopupResetPass() {
        dialogResetpass = new Dialog(LoginActivity.this);
        dialogResetpass.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogResetpass.setContentView(R.layout.layout_reset_pass);
        edtUserNameReset = (EditText) dialogResetpass
                .findViewById(R.id.edtUserNameReset);
        edtNewPasswordReset = (EditText) dialogResetpass
                .findViewById(R.id.edtNewPasswordReset);
        edtConfirmPasswordReset = (EditText) dialogResetpass
                .findViewById(R.id.edtConfirmPasswordReset);
        edtmabimat = (EditText) dialogResetpass.findViewById(R.id.edtmabimat);

        if (edtUserName.getText() != null
                && !edtUserName.getText().toString().isEmpty()) {
            edtUserNameReset.setText(edtUserName.getText().toString().trim());
        }

        Button btnsinhma = (Button) dialogResetpass
                .findViewById(R.id.btnsinhmabimat);

        btnsinhma.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (edtUserNameReset.getText() != null
                        && !edtUserNameReset.getText().toString().isEmpty()) {

                    if (CommonActivity.isNetworkConnected(LoginActivity.this)) {

                        GetSecretKeyAsyn getSecretKeyAsyn = new GetSecretKeyAsyn(
                                LoginActivity.this);
                        getSecretKeyAsyn.execute(edtUserNameReset.getText()
                                .toString().trim());

                    } else {
                        CommonActivity.createAlertDialog(LoginActivity.this,
                                getString(R.string.errorNetwork),
                                getString(R.string.app_name)).show();
                    }
                } else {
                    String message = getString(R.string.userNameRequired);
                    String title = getString(R.string.app_name);
                    Dialog dialog = CommonActivity.createAlertDialog(
                            LoginActivity.this, message, title);
                    dialog.show();
                    edtUserNameReset.requestFocus();
                }
            }
        });

        Button btnresetpass = (Button) dialogResetpass
                .findViewById(R.id.btncresetpass);

        btnresetpass.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (edtUserNameReset.getText() != null
                        && !edtUserNameReset.getText().toString().isEmpty()) {
                    if (edtNewPasswordReset.getText() != null
                            && !edtNewPasswordReset.getText().toString()
                            .isEmpty()) {
                        if (edtConfirmPasswordReset.getText() != null
                                && !edtConfirmPasswordReset.getText()
                                .toString().isEmpty()) {
                            if (edtmabimat.getText() != null
                                    && !edtmabimat.getText().toString()
                                    .isEmpty()) {

                                if (edtNewPasswordReset
                                        .getText()
                                        .toString()
                                        .equalsIgnoreCase(
                                                edtConfirmPasswordReset
                                                        .getText().toString())) {
                                    if (CommonActivity
                                            .isNetworkConnected(LoginActivity.this)) {

                                        CommonActivity
                                                .createDialog(
                                                        LoginActivity.this,
                                                        getString(R.string.confirmresetpassaction),
                                                        getString(R.string.app_name),
                                                        getString(R.string.buttonOk),
                                                        getString(R.string.buttonCancel),
                                                        onclickconfireset, null)
                                                .show();
                                    } else {
                                        CommonActivity
                                                .createAlertDialog(
                                                        LoginActivity.this,
                                                        getString(R.string.errorNetwork),
                                                        getString(R.string.app_name))
                                                .show();
                                    }
                                } else {
                                    String message = getString(R.string.password_not_same_repass);
                                    String title = getString(R.string.app_name);
                                    Dialog dialog = CommonActivity
                                            .createAlertDialog(
                                                    LoginActivity.this,
                                                    message, title);
                                    dialog.show();
                                }
                            } else {
                                String message = getString(R.string.checkmabimat);
                                String title = getString(R.string.app_name);
                                Dialog dialog = CommonActivity
                                        .createAlertDialog(LoginActivity.this,
                                                message, title);
                                dialog.show();
                                edtmabimat.requestFocus();
                            }
                        } else {
                            String message = getString(R.string.passwordConfirmRequired);
                            String title = getString(R.string.app_name);
                            Dialog dialog = CommonActivity.createAlertDialog(
                                    LoginActivity.this, message, title);
                            dialog.show();
                            edtConfirmPasswordReset.requestFocus();
                        }
                    } else {
                        String message = getString(R.string.passwordNewRequired);
                        String title = getString(R.string.app_name);
                        Dialog dialog = CommonActivity.createAlertDialog(
                                LoginActivity.this, message, title);
                        dialog.show();
                        edtNewPasswordReset.requestFocus();
                    }
                } else {
                    String message = getString(R.string.userNameRequired);
                    String title = getString(R.string.app_name);
                    Dialog dialog = CommonActivity.createAlertDialog(
                            LoginActivity.this, message, title);
                    dialog.show();
                    edtUserNameReset.requestFocus();
                }

            }
        });

        Button btncancel = (Button) dialogResetpass.findViewById(R.id.btncanel);
        btncancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialogResetpass.cancel();

            }
        });

        dialogResetpass.show();
    }

    private final OnClickListener onclickconfireset = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            ResetPassAsyn resetPassAsyn = new ResetPassAsyn(LoginActivity.this);
            resetPassAsyn.execute(edtUserNameReset.getText().toString().trim(),
                    edtNewPasswordReset.getText().toString().trim(),
                    edtConfirmPasswordReset.getText().toString().trim(),
                    edtmabimat.getText().toString().trim());

        }
    };

    // asyntask mbccs_getSecretKey
    private class GetSecretKeyAsyn extends AsyncTask<String, Void, String> {

        final ProgressDialog progress;
        private Context context = null;
        final XmlDomParse parse = new XmlDomParse();
        String errorCode = "";
        String description = "";

        public GetSecretKeyAsyn(Context context) {
            this.context = context;
            this.progress = new ProgressDialog(LoginActivity.this);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(
                    R.string.waitting));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected String doInBackground(String... arg0) {
            if (!CommonActivity.isInternetReachable()) {
                return ERROR_PING_SERVER;
            }
            return getSecretKey(arg0[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            if (!isFinishing() && progress != null && progress.isShowing()) {
                progress.dismiss();
            }
            if ("0".equalsIgnoreCase(errorCode)) {
                CommonActivity.createAlertDialog(LoginActivity.this,
                        getString(R.string.sinhmathanhcong),
                        getString(R.string.app_name)).show();
            } else if (ERROR_PING_SERVER.equals(result)) {
                String message = getString(R.string.login_error);
                String title = getString(R.string.app_name);
                Dialog dialog = CommonActivity.createDialog(LoginActivity.this,
                        message, title, getString(R.string.ok),
                        getString(R.string.check_apn), null, showAPNSetting);
                dialog.show();
            } else {
                if (description == null || description.isEmpty()) {
                    description = getString(R.string.sinhmathatbai);
                }
                CommonActivity.createAlertDialog(LoginActivity.this,
                        description, getString(R.string.app_name)).show();

            }

        }

        private String getSecretKey(String userName) {
            String original = "";
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getSecretKey");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getSecretKey>");
                rawData.append("<input>");
                rawData.append("<userName>").append(userName);
                rawData.append("</userName>");
                rawData.append("</input>");
                rawData.append("</ws:getSecretKey>");
                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope,
                        Constant.BCCS_GW_URL, LoginActivity.this,
                        "mbccs_getSecretKey");
                Log.i("Responseeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("original123", original);

                // ==== parse xml list ip
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

    // Asyntask reset password
    private class ResetPassAsyn extends AsyncTask<String, Void, String> {

        final ProgressDialog progress;
        private Context context = null;
        final XmlDomParse parse = new XmlDomParse();
        String errorCode = "";
        String description = "";

        public ResetPassAsyn(Context context) {
            this.context = context;
            this.progress = new ProgressDialog(this.context);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(
                    R.string.waitting));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected String doInBackground(String... arg0) {
            if (!CommonActivity.isInternetReachable()) {
                return ERROR_PING_SERVER;
            }
            return resetPass(arg0[0], arg0[1], arg0[2], arg0[3]);
        }

        @Override
        protected void onPostExecute(String result) {
            if (!isFinishing() && progress != null && progress.isShowing()) {
                progress.dismiss();
            }
            if ("0".equalsIgnoreCase(errorCode)) {
                CommonActivity.createAlertDialog(LoginActivity.this,
                        getString(R.string.resetpasssucess),
                        getString(R.string.app_name), onclickresetPassSucess)
                        .show();
            } else if (ERROR_PING_SERVER.equals(result)) {
                String message = getString(R.string.login_error);
                String title = getString(R.string.app_name);
                Dialog dialog = CommonActivity.createDialog(LoginActivity.this,
                        message, title, getString(R.string.ok),
                        getString(R.string.check_apn), null, showAPNSetting);
                dialog.show();
            } else {
                if (description == null || description.isEmpty()) {
                    description = getString(R.string.resetpassfail);
                }
                CommonActivity.createAlertDialog(LoginActivity.this,
                        description, getString(R.string.app_name)).show();

            }

        }

        private String resetPass(String userName, String newPass,
                                 String reNewPass, String secretKey) {
            String original = "";
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_resetPass");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:resetPass>");
                rawData.append("<input>");
                rawData.append("<userName>").append(userName);
                rawData.append("</userName>");
                rawData.append("<newPass>").append(newPass);
                rawData.append("</newPass>");
                rawData.append("<secretPassword>").append(secretKey);
                rawData.append("</secretPassword>");
                rawData.append("<reNewPass>").append(reNewPass);
                rawData.append("</reNewPass>");
                rawData.append("</input>");
                rawData.append("</ws:resetPass>");

                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope,
                        Constant.BCCS_GW_URL, LoginActivity.this,
                        "mbccs_resetPass");
                Log.i("Responseeeeeeeeee0f0a", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("original123", original);

                // ==== parse xml list ip
                Document doc = parse.getDomElement(original);
                NodeList nl = doc.getElementsByTagName("return");
                for (int i = 0; i < nl.getLength(); i++) {
                    Element e2 = (Element) nl.item(i);
                    errorCode = parse.getValue(e2, "errorCode");
                    Log.d("errorCode", errorCode);
                    description = parse.getValue(e2, "description");
                    Log.d("description", description);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return errorCode;
        }

    }

    // Asyntask change password
    private class ChangePassAsyn extends AsyncTask<String, Void, String> {

        final ProgressDialog progress;
        private Context context = null;
        final XmlDomParse parse = new XmlDomParse();
        String errorCode = "";
        String description = "";

        public ChangePassAsyn(Context context) {
            this.context = context;
            this.progress = new ProgressDialog(this.context);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(
                    R.string.waitting));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected String doInBackground(String... arg0) {
            if (!CommonActivity.isInternetReachable()) {
                return ERROR_PING_SERVER;
            }
            return changePass(arg0[0], arg0[1], arg0[2], arg0[3]);
        }

        @Override
        protected void onPostExecute(String result) {
            if (!isFinishing() && progress != null && progress.isShowing()) {
                progress.dismiss();
            }
            if ("0".equalsIgnoreCase(errorCode)) {
                CommonActivity.createAlertDialog(LoginActivity.this,
                        getString(R.string.cp_success),
                        getString(R.string.app_name), onclickChangePassSucess)
                        .show();
            } else if (ERROR_PING_SERVER.equals(result)) {
                String message = getString(R.string.login_error);
                String title = getString(R.string.app_name);
                Dialog dialog = CommonActivity.createDialog(LoginActivity.this,
                        message, title, getString(R.string.ok),
                        getString(R.string.check_apn), null, showAPNSetting);
                dialog.show();
            } else {
                if (description == null || description.isEmpty()) {
                    description = getString(R.string.cp_fail);
                }
                CommonActivity.createAlertDialog(LoginActivity.this,
                        description, getString(R.string.app_name)).show();
            }

        }

        private String changePass(String userName, String oldPass,
                                  String newPass, String reNewPass) {

            String original = "";
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_changePass");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:changePass>");
                rawData.append("<input>");
                rawData.append("<userName>").append(userName);
                rawData.append("</userName>");
                rawData.append("<newPass>").append(newPass);
                rawData.append("</newPass>");
                rawData.append("<oldPass>").append(oldPass);
                rawData.append("</oldPass>");
                rawData.append("<reNewPass>").append(reNewPass);
                rawData.append("</reNewPass>");
                rawData.append("</input>");
                rawData.append("</ws:changePass>");

                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope,
                        Constant.BCCS_GW_URL, LoginActivity.this,
                        "mbccs_changePass");
                Log.i("Responseeeeeeeeee0f0a", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("original123", original);

                // ==== parse xml list ip
                Document doc = parse.getDomElement(original);
                NodeList nl = doc.getElementsByTagName("return");
                for (int i = 0; i < nl.getLength(); i++) {
                    Element e2 = (Element) nl.item(i);
                    errorCode = parse.getValue(e2, "errorCode");
                    Log.d("errorCode", errorCode);
                    description = parse.getValue(e2, "description");
                    Log.d("description", description);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return errorCode;
        }
    }

    // ONCLICK CONFIRM CHANGE PASS
    private final OnClickListener onclickConfirmChangePass = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            ChangePassAsyn changePassAsyn = new ChangePassAsyn(
                    LoginActivity.this);
            changePassAsyn.execute(editUserName.getText().toString(),
                    edtOldPassword.getText().toString(), edtNewPassword
                            .getText().toString(), edtConfirmPassword.getText()
                            .toString());
        }
    };

    private final OnClickListener onclickresetPassSucess = new OnClickListener() {

        @Override
        public void onClick(View arg0) {


            if (CommonActivity.isNetworkConnected(LoginActivity.this)) {
                if (dialogChangePass != null) {
                    dialogChangePass.cancel();
                }

                edtUserName.setText(edtUserNameReset.getText().toString()
                        .trim());
                edtPassword.setText(edtNewPasswordReset.getText().toString()
                        .trim());
                LoginAsyncTask loginAsyncTask = new LoginAsyncTask(
                        LoginActivity.this);
                loginAsyncTask.execute();
            } else {
                CommonActivity.createAlertDialog(LoginActivity.this,
                        getString(R.string.errorNetwork),
                        getString(R.string.app_name)).show();
            }

        }
    };
    private final OnClickListener onclickChangePassSucess = new OnClickListener() {
        @Override
        public void onClick(View arg0) {
            if (CommonActivity.isNetworkConnected(LoginActivity.this)) {
                if (dialogChangePass != null) {
                    dialogChangePass.cancel();
                }

                edtUserName.setText(editUserName.getText().toString().trim());
                edtPassword.setText(edtNewPassword.getText().toString().trim());
                LoginAsyncTask loginAsyncTask = new LoginAsyncTask(
                        LoginActivity.this);
                loginAsyncTask.execute();
            } else {
                CommonActivity.createAlertDialog(LoginActivity.this,
                        getString(R.string.errorNetwork),
                        getString(R.string.app_name)).show();
            }
        }
    };

    private void initWSDL() {
        Constant.BCCS_GW_URL = Constant.URL_SERVICE
                + "BCCSGatewayWS/BCCSGatewayWS?wsdl";
        Constant.LINK_WS_UPLOAD_IMAGE = Constant.URL_SERVICE_DOWNLOAD
                + "SmartphoneV2WS/webresources/syncDatabase/upload/";
        Constant.LINK_WS_SYNC = Constant.URL_SERVICE_DOWNLOAD
                + "SmartphoneV2WS/webresources/syncDatabase/";
        Constant.LINK_WS_COM = Constant.URL_SERVICE_DOWNLOAD
                + "SmartphoneV2WS/webresources/syncDatabase/communication/";
        Constant.PATH_DOWNLOAD = Constant.URL_SERVICE_DOWNLOAD
                + "SmartphoneV2WS/webresources/syncDatabase/firstSync/";
        Constant.PATH_SYNC_DATA = Constant.URL_SERVICE_DOWNLOAD
                + "SmartphoneV2WS/webresources/syncDatabase/syncData/";

        Constant.PATH_UPDATE_VERSION = Constant.URL_SERVICE_DOWNLOAD
                + "SmartphoneV2WS/webresources/syncDatabase/upgrade/";
        Constant.WS_SYNCHRONIZE_DATA = Constant.URL_SERVICE_DOWNLOAD
                + "SmartphoneV2WS/SmartphoneWS?wsdl";

        Constant.WS_PAKAGE_DATA = Constant.URL_SERVICE_DOWNLOAD
                + "SmartphoneV2WS/CMWS?wsdl";
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (String permission : permissions) {
                    if (permission.equals(Manifest.permission.READ_PHONE_STATE)) {
                        if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                            //TODO
                            tele = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                            serialSim = tele.getSimSerialNumber();
                        }
                    }
                }
                break;
            default:
                break;
        }

    }

    private final OnAuthenticationSucceed onSuccess = new OnAuthenticationSucceed() {

        @Override
        public void onAuthenticationSucceed() {
            String pass = "";
            try {
                final SharedPreferences preferences = getSharedPreferences(
                        Define.PRE_NAME, MODE_PRIVATE);
                pass = EncryptKeystore.decrypt(
                        preferences.getString(Constant.KEY_PASS, ""),
                        preferences.getString(Constant.KEY_IV, ""));
                edtPassword.setText(pass);
                String lastLogin = "";
                lastLogin = preferences.getString(Define.KEY_LOGIN_NAME, "");
                edtUserName.setText(lastLogin);
                new LoginAsyncTask(LoginActivity.this).execute();
            } catch (Exception e) {
                Log.e("Exception", "ex", e);
            }
            if (CommonActivity.isNullOrEmpty(pass)) {

            }

        }
    };
    private final OnAuthenticationHelp onHelp = new OnAuthenticationHelp() {

        @Override
        public void onAuthenticationHelp(String msg) {

            showErrorFinger(msg);

        }
    };
    private final OnAuthenticationError onError = new OnAuthenticationError() {
        @Override
        public void onAuthenticationError(String msg) {
            fingerManager.cancelFinger();
            countDownTimer = new CountDownTimer(30000, 1000) {
                @Override
                public void onTick(long l) {
                    tvFinger.setText(getString(R.string.finger_error, l / 1000));

                }

                @Override
                public void onFinish() {
                    tvFinger.setText(getString(R.string.finger_hint));
                    fingerManager.fingerListener(LoginActivity.this, onSuccess, onError,
                            onHelp, onFail);
                    countDownTimer = null;
                }
            };
            countDownTimer.start();
        }
    };
    private final FingerManager.OnAuthenticationFail onFail = new FingerManager.OnAuthenticationFail() {
        @Override
        public void onAuthenticationFail() {
            showErrorFinger(getString(R.string.finger_not_match));
        }
    };


    private void showErrorFinger(CharSequence text) {
        tvFinger.setText(text);
        tvFinger.removeCallbacks(mResetErrorTextRunnable);
        tvFinger.setTextColor(getColor(R.color.red_normal));
        tvFinger.postDelayed(mResetErrorTextRunnable, 2000);
    }

    private final Runnable mResetErrorTextRunnable = new Runnable() {
        @Override
        public void run() {
            tvFinger.setText(
                    getString(R.string.finger_hint));
            tvFinger.setTextColor(getColor(R.color.white));
        }
    };

    private void requestPermission() {
        if (CommonActivity.askPermission()) {
            List<String> lstPermissionNotGrant = new ArrayList<>();
            lstPermissionDenied.clear();

            for (String permission : arrPermission) {
                int havePermission = checkSelfPermission(permission);
                if (havePermission != PackageManager.PERMISSION_GRANTED) {
                    if (!shouldShowRequestPermissionRationale(permission)) {
                        lstPermissionDenied.add(permission);
                        Log.d("requestPermission lstPermissionDenied", permission);
                    } else {
                        lstPermissionNotGrant.add(permission);
                        Log.d("requestPermission lstPermissionNotGrant", permission);
                    }
                }
            }

            if (!lstPermissionDenied.isEmpty()) {
                onClickPermission.onClick(getCurrentFocus());
                return;
            }

            if (!lstPermissionNotGrant.isEmpty()) {
                requestPermissions(convertListToArray(lstPermissionNotGrant),
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
        }
    }

    private final OnClickListener onClickPermission = new OnClickListener() {
        @Override
        public void onClick(View view) {
            requestPermissions(convertListToArray(lstPermissionDenied),
                    REQUEST_CODE_ASK_PERMISSIONS);
        }
    };

    private String[] convertListToArray(List<String> lstData) {
        String[] arrData = new String[lstData.size()];
        for (int i = 0; i < lstData.size(); i++) {
            arrData[i] = lstData.get(i);
        }
        return arrData;
    }

    public void checkDrawOverlayPermission() {
        if (CommonActivity.askPermission()) {   //Android M Or Over
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, PERM_REQUEST_CODE_DRAW_OVERLAYS);
            } else {
                requestPermission();
            }
        }
    }

    @Override
    @TargetApi(Build.VERSION_CODES.M)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PERM_REQUEST_CODE_DRAW_OVERLAYS) {
            if (Settings.canDrawOverlays(this)) {
                requestPermission();
            } else {
                checkDrawOverlayPermission();
            }
        } else if (requestCode == APP_SETTING_RESULT_CODE) {
            if (!checkDonePermission()) {
                //dialogGuideSetting.show();
                //startSettingApp();
                showDialogViewSetting();
            }
        }
    }

    private boolean checkDonePermission() {
        if (!CommonActivity.askPermission()) {
            return true;
        }

        for (String permission : arrPermission) {
            int havePermission = checkSelfPermission(permission);
            if (havePermission != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private final OnClickListener onClickAcceptGuideSetting = new OnClickListener() {
        @Override
        public void onClick(View arg0) {
            dialogGuideSetting.dismiss();
            startSettingApp();
        }
    };

    private void startSettingApp() {
        try {
            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, APP_SETTING_RESULT_CODE);
        } catch (ActivityNotFoundException e) {
            Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
            startActivityForResult(intent, APP_SETTING_RESULT_CODE);
        }
    }

    private final OnClickListener cancelViewSettingListener = new OnClickListener() {
        @Override
        public void onClick(View arg0) {
            startSettingApp();
        }
    };

    private final OnClickListener okViewSettingListener = new OnClickListener() {
        @Override
        public void onClick(View arg0) {
            dialogGuideSetting.show();
        }
    };

    private void showDialogViewSetting() {
        Dialog dialog = CommonActivity.createDialog(this, "Bạn chưa cấp đủ quyền cho ứng dụng, bạn có muốn xem hướng dẫn cấp quyền không?",
                getString(R.string.app_name),
                getString(R.string.cancel),
                getString(R.string.ok),
                cancelViewSettingListener, okViewSettingListener);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void clearOmniStaff() {
        SharedPreferences sharedPreferences = getSharedPreferences(
                Constant.SHARE_PREFERENCES_FILE, MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();

        edit.putString(Constant.OMNI_STAFF_NAME_SAVE, Constant.OMNI_STAFF_INVALID);
        edit.putString(Constant.SIGNATURE_STAFF_SAVE, "");
        edit.putString(Constant.SIGNATURE_STAFF_EXISTS, "");

        edit.commit();
    }
}
