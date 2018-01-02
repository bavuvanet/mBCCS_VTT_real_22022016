package com.viettel.bss.viettelpos.v4.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.SecurityUtil;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.handler.VSAMenuHandler;
import com.viettel.bss.viettelpos.v4.infrastrucure.dal.InfrastrucureDB;
import com.viettel.bss.viettelpos.v4.object.VSA;
import com.viettel.bss.viettelpos.v4.sale.business.CacheData;
import com.viettel.bss.viettelpos.v4.sale.business.StaffBusiness;
import com.viettel.bss.viettelpos.v4.synchronizationdata.UpdateVersionAsyn;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

public class LoginDialog extends Dialog implements android.view.View.OnClickListener {

	private static final String ERROR_PING_SERVER = "Error_Ping_Server";
	private EditText edtUserName;
	private EditText edtPassword;
	private final Activity context;
	private String errorMessage;
	private String description = "";
	private String forceUpgrade = "";
	private String version = "";
	private String serialSim;
	private final String permission;
	private final String NOT_PERMISSION = "NOT_PERMISSION";

	public LoginDialog(Activity context, String permission) {
		super(context);
		this.context = context;
		this.permission = permission;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_layout_dialog);
		edtUserName = (EditText) findViewById(R.id.edtUser);
		edtUserName.setEnabled(false);
		edtUserName.setText(Session.userName);
		edtPassword = (EditText) findViewById(R.id.edtPassword);
		findViewById(R.id.btnLogin).setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btnLogin:
			edtUserName.setText(edtUserName.getText().toString().trim());
			edtPassword.setText(edtPassword.getText().toString().trim());
			if (edtUserName.getText().toString().isEmpty()) {
				String message = context.getString(R.string.userNameRequired);
				String title = context.getString(R.string.app_name);
				Dialog dialog = CommonActivity.createAlertDialog(context,
						message, title);
				dialog.show();
				edtUserName.requestFocus();
				return;

			}
			if (edtPassword.getText().toString().isEmpty()) {
				String message = context.getString(R.string.passwordRequired);
				String title = context.getString(R.string.app_name);
				Dialog dialog = CommonActivity.createAlertDialog(context,
						message, title);
				dialog.show();
				edtPassword.requestFocus();
				return;
			}
			String username = edtUserName.getText().toString();
			String pass = edtPassword.getText().toString();
			LoginAsyncTask login = new LoginAsyncTask(context);
			login.execute(username, pass);
			break;

		default:
			break;
		}
	}

	private BCCSGateway loginGateway = null;

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
				String message = context.getString(R.string.logining);
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
				progress.setMessage(context.getString(R.string.logining));
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
					}
				};
				mCountDownTimer.start();
			}
		}

		@Override
		protected String doInBackground(String... params) {

			// KpiLog
			Session.userName = params[0];
			if (!CommonActivity.isInternetReachable()) {
				return ERROR_PING_SERVER;
			}
			return login(params[0], params[1]);
		}

		@Override
		protected void onPostExecute(String result) {
			if (progress != null) {
				progress.dismiss();
			}
			if (mCountDownTimer != null) {
				mCountDownTimer.cancel();
			}
			if (Constant.SUCCESS_CODE.equals(result)) {
				successLogin();
			} else if (Constant.ERROR_CODE.equals(result)) {
				String message = context.getString(R.string.login_error);
				if (!CommonActivity.isNullOrEmpty(errorMessage)) {
					message = errorMessage;
				}
				String title = context.getString(R.string.app_name);

				Dialog dialog = CommonActivity.createAlertDialog(activity,
						message, title);
				dialog.show();
			} else if (ERROR_PING_SERVER.equals(result)) {
				String message = context.getString(R.string.login_error);
				String title = context.getString(R.string.app_name);
				Dialog dialog = CommonActivity.createDialog(activity, message,
						title, context.getString(R.string.ok),
						context.getString(R.string.check_apn), null,
						showAPNSetting);
				dialog.show();
			} else if (NOT_PERMISSION.equals(result)) {

				android.view.View.OnClickListener backClick = new android.view.View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// LoginDialog.this.dismiss();
						// context.onBackPressed();
						try {
							InfrastrucureDB mInfrastrucureDB = new InfrastrucureDB(
									context);
							String shop[] = mInfrastrucureDB.getProvince();
							Session.province = shop[0];
							Session.district = shop[1];
							mInfrastrucureDB.close();
							Session.loginUser = StaffBusiness
									.getStaffByStaffCode(context,
											Session.userName);
						} catch (Exception ignored) {
						}
						// Neu database da ton tai, login vao man hinh chinh
						Intent i = new Intent(context, MainActivity.class);
						context.startActivity(i);

					}
				};
				String title = context.getString(R.string.app_name);

				Dialog dialog = CommonActivity.createAlertDialog(activity,
						errorMessage, title, backClick);
				dialog.show();

			}
		}

		private final android.view.View.OnClickListener showAPNSetting = new android.view.View.OnClickListener() {
			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				intent.setClassName("com.android.settings",
						"com.android.settings.ApnSettings");
				context.startActivity(intent);
			}
		};

		private void successLogin() {

			// ===========run check version==============
			if (forceUpgrade.equalsIgnoreCase("0")
					&& !version.equals(getversionclient())) {
				// ========== khong bat buoc cap nhat version===== show
				// aletdialog
				Dialog dialog = CommonActivity.createDialog(
						activity,
						description + "\n"
								+ context.getString(R.string.isversion),
						context.getString(R.string.updateversion),
						context.getString(R.string.ok),
						context.getString(R.string.cancel), onclickCheckUpdate,
						null);
				dialog.show();

			} else if (forceUpgrade.equalsIgnoreCase("1")
					&& !version.equals(getversionclient())) {
				// ========== bat buoc cap nhat ======================
				Log.d("versionnnnnserrver", version);
				UpdateVersionAsyn updateVersionAsyn = new UpdateVersionAsyn(
						context, Constant.PATH_UPDATE_VERSION + Session.token);
				updateVersionAsyn.execute();

			}
			dismiss();
		}
	}

	private String login(String user, String pass) {
		try {
			loginGateway = new BCCSGateway();
			loginGateway.addValidateGateway("wscode", "mbccs_loginBccs2");
			loginGateway.addValidateGateway("username", Constant.BCCSGW_USER);
			loginGateway.addValidateGateway("password", Constant.BCCSGW_PASS);
			// KpiLog
			String envelope = loginGateway.buildInputGatewayWithRawData(
					buildRawData(false, user, pass), 0, true);
			Log.e("envlop Login", envelope);
			String response = loginGateway.sendRequest(envelope,
					Constant.BCCS_GW_URL, context, "mbccs_loginBccs2",
					Constant.LOGIN_TIME_OUT_CONECT,
					Constant.LOGIN_TIME_OUT_RESPONE);
			Log.e("response Login", response);
			CommonOutput output = loginGateway.parseGWResponse(response);
			if (output == null) {
				errorMessage = context.getString(R.string.exception);
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
				errorMessage = context.getString(R.string.exception);
				return Constant.ERROR_CODE;
			}
			if (!output.getErrorCode().equals("0")) {
				errorMessage = output.getDescription();
				return Constant.ERROR_CODE;
			}
			description = handler.getUpgradeDescription();
			forceUpgrade = handler.getForceUpgrade();
			version = handler.getVersion();
			CacheData.getInstanse().setVersion(version);
			List<VSA> lstMenu = handler.getLstVSAMenu();

			StringBuilder menuList = new StringBuilder(";");
			if (lstMenu != null) {
				for (int i = 0; i < lstMenu.size(); i++) {
					menuList.append(lstMenu.get(i).getObjectName()).append(";");
				}
			}

			SharedPreferences preferences = context.getSharedPreferences(
					Define.PRE_NAME, Activity.MODE_PRIVATE);
			SharedPreferences.Editor editor = preferences.edit();
			editor.putString(Define.KEY_MENU_NAME, menuList.toString());
			editor.putString(Define.KEY_LOGIN_NAME, user.toUpperCase());

			editor.putString(Define.KEY_TRACKING, handler.getIsTracking());

			// Mac dinh
			// editor.putString(Define.KEY_TRACKING, "2");

			String token = StringUtils.encryptIt(output.getToken(), context);
			editor.putString(Define.KEY_TEMP, token);
			editor.putString(Define.KEY_INVALID_TOKEN, "0");
			editor.commit();
			Session.setToken(output.getToken());
			Session.userName = user;
			Session.setPublicKey(handler.getPublicKey());
			Session.setPrivateKey(handler.getPrivateKey());

			if (!menuList.toString().contains(permission)) {
				errorMessage = context.getString(R.string.not_permission);
				return NOT_PERMISSION;
			}
			return Constant.SUCCESS_CODE;
		} catch (UnknownHostException ex) {
			Log.e("UnknownHostException", ex.toString(), ex);

			errorMessage = context.getString(R.string.unknown_host_exception);
			return Constant.ERROR_CODE;
		} catch (ConnectTimeoutException ex) {
			Log.e("ConnectTimeoutException", ex.getMessage(), ex);

			errorMessage = context
					.getString(R.string.connect_timeout_exception);
			return Constant.ERROR_CODE;
		} catch (SocketTimeoutException ex) {
			Log.e("SocketTimeoutException", ex.getMessage(), ex);

			errorMessage = context.getString(R.string.socket_timeout_exception);
			return Constant.ERROR_CODE;
		} catch (SocketException ex) {
			Log.e("SocketException", ex.getMessage(), ex);

			errorMessage = context.getString(R.string.socket_close_exception);
			return Constant.ERROR_CODE;
		} catch (IllegalStateException ex) {
			Log.e("IllegalStateException", ex.getMessage(), ex);

			errorMessage = context.getString(R.string.illegal_state_exception);
			return Constant.ERROR_CODE;
		} catch (IOException ex) {
			Log.e("IOException", ex.getMessage(), ex);

			errorMessage = context.getString(R.string.socket_io_exception);
			return Constant.ERROR_CODE;
		} catch (Exception ex) {
			Log.e("Exception", ex.getMessage(), ex);
			errorMessage = context.getString(R.string.exception);
			return Constant.ERROR_CODE;
		}
	}

	private String getversionclient() {
		String versionclient = "";
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			versionclient = packageInfo.versionName;
			Log.d("versionclienttttttttttttttttttt", versionclient);
			CacheData.getInstanse().setVersionclient(versionclient);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return versionclient;
	}

	private final android.view.View.OnClickListener onclickCheckUpdate = new android.view.View.OnClickListener() {

		@Override
		public void onClick(View v) {
			UpdateVersionAsyn updateVersionAsyn = new UpdateVersionAsyn(
					context, Constant.PATH_UPDATE_VERSION + Session.token);
			updateVersionAsyn.execute();
		}
	};

	private String buildRawData(Boolean isSMS, String user, String pass)
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
		result.append("<clientTime>");
		result.append(System.currentTimeMillis());
		result.append("</clientTime>");
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
}
