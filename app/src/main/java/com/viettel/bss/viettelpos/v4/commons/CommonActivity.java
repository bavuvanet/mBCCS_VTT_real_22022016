package com.viettel.bss.viettelpos.v4.commons;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.conn.ConnectTimeoutException;
//import org.apache.http.conn.HttpHostConnectException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.FragmentConnectionMobileNew;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity.FragmentChangeSim;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.customer.activity.FragmentInsertOrUpdateCoporation;
import com.viettel.bss.viettelpos.v4.customer.fragment.CustomerManagerFragment;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.customview.obj.RecordPrepaid;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.object.GridMenu;
import com.viettel.bss.viettelpos.v4.sale.activity.DateListtennerInterface;
import com.viettel.bss.viettelpos.v4.sale.activity.SaleManagerFragment;
import com.viettel.bss.viettelpos.v4.sale.business.StaffBusiness;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;
import com.viettel.maps.util.AppInfo;
import com.viettel.savelog.SaveLog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.viettel.bss.viettelpos.v4.object.MenuActionMain;

@SuppressLint("NewApi")
public class CommonActivity extends Activity {

    private static final String TAG = "CommonActivity";

	public static void showDatePickerDialog(final Activity activity,
			final DateListtennerInterface dateCallback) {

		OnDateSetListener callback = new OnDateSetListener() {
			boolean receiverCallBack = false;

			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// Date
				if (!receiverCallBack) {
					String mDateRange = (dayOfMonth) + "/" + (monthOfYear + 1)
							+ "/" + year;
					dateCallback.onlistennerDate(mDateRange, dayOfMonth,
							monthOfYear, year);
				}
				receiverCallBack = true;
			}
		};

		Calendar calendar = Calendar.getInstance();
		int mYear = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		DatePickerDialog pic = new FixedHoloDatePickerDialog(activity,AlertDialog.THEME_HOLO_LIGHT, callback, mYear,
				month, day);
		pic.setTitle(activity.getString(R.string.chon_ngay));
		pic.show();
	}
	

	public static void showDatePickerDialog(final Context context,
			Date lastDate, OnDateSetListener callback) {
		if (context == null || callback == null) {
			return;
		}
		final Calendar cal = Calendar.getInstance();
		if (lastDate == null) {
			lastDate = new Date();
		}
		cal.setTime(lastDate);
		DatePickerDialog pic = new FixedHoloDatePickerDialog(context, AlertDialog.THEME_HOLO_LIGHT,callback,
				cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH));
		pic.setTitle(context.getString(R.string.chon_ngay));
		pic.show();
	}

	public static String getDeviceId(Context context) {

		String deviceId = "";
		try {
			TelephonyManager telephony = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);

			if (telephony.getDeviceId() != null) {
				deviceId = telephony.getDeviceId();
			} else {
				deviceId = Secure.getString(context.getContentResolver(),
						Secure.ANDROID_ID);
			}
		} catch (Exception e) {
			Log.d(Constant.TAG, "error", e);
		}

		return deviceId;
	}

	public static Dialog createAlertDialog(Activity act, String message, String title) {
		try {
            return new MaterialDialog.Builder(act)
                    .title(title)
                    .content(message)
                    .positiveText(act.getString(R.string.ok))
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick( MaterialDialog dialog,  DialogAction which) {
                            if(dialog != null && dialog.isShowing()){
                                dialog.dismiss();
                            }
                        }
                    }).build();
		} catch (Exception e) {
            Log.d(TAG, "Error", e);
			return null;
		}
	}

	public static Dialog createAlertNetworkDialog(Activity act) {
		return createAlertDialog(act, act.getString(R.string.errorNetwork),
				act.getString(R.string.app_name));
	}

	public static Dialog createExceptionDialog(Activity act, Exception ex) {
		try {
			return createAlertDialog(act, act.getString(R.string.exception)
					+ " - " + ex.toString(), act.getString(R.string.app_name));
		} catch (Exception e) {
			Log.d(Constant.TAG, "error", e);
			return null;
		}
	}

	public static Dialog createErrorDialog(final Activity act, String msg,
			String errorCode) {

		try {
			OnClickListener onclick = null;
			if (Constant.INVALID_TOKEN.equals(errorCode)) {
				onclick = new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(act, LoginActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
								| Intent.FLAG_ACTIVITY_NO_HISTORY);
						act.startActivity(intent);
						act.finish();

					}
				};
			}
			return createAlertDialog(act, msg,
					act.getString(R.string.app_name), onclick);
		} catch (Exception e) {
			Log.d(Constant.TAG, "error", e);
			return null;
		}
	}public static Dialog createErrorDialog(final Activity act, String msg,
											String errorCode, final String permission) {

		try {
			OnClickListener onclick = null;
			if (Constant.INVALID_TOKEN.equals(errorCode)) {
				onclick = new OnClickListener() {

					@Override
					public void onClick(View v) {
						LoginDialog dialog = new LoginDialog(act,permission);
						dialog.show();

					}
				};
			}
			return createAlertDialog(act, msg,
					act.getString(R.string.app_name), onclick);
		} catch (Exception e) {
			Log.d(Constant.TAG, "Error", e);
			return null;
		}
	}

	public static Dialog createAlertDialog(Context act, String message,
			String title) {
		try {

            return new MaterialDialog.Builder(act)
                    .title(title)
                    .content(message)
                    .positiveText(act.getString(R.string.ok))
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick( MaterialDialog dialog,  DialogAction which) {
                            if(dialog != null && dialog.isShowing()){
                                dialog.dismiss();
                            }
                        }
                    }).build();
		} catch (Exception e) {
            Log.d(TAG, "Error", e);
			return null;
		}
	}

	public static Dialog createAlertDialog(final Activity act, String message,
			String title, final OnClickListener onClick) {
		try {

            return new MaterialDialog.Builder(act)
                    .title(title)
                    .content(message)
                    .positiveText(act.getString(R.string.ok))
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
						@Override
						public void onClick(MaterialDialog dialog, DialogAction which) {
							if (onClick != null) {
								onClick.onClick(act.getCurrentFocus());
							}

							if (dialog != null && dialog.isShowing()) {
								dialog.dismiss();
							}
						}
					}).build();
		} catch (Exception e) {
            Log.d(TAG, "Error", e);
			return null;
		}
	}

	public static Dialog createDialog(final Activity act, int messageId, int titleId,
			int leftButtonTextId, int rightButtonTextId,
			final OnClickListener leftClick, final OnClickListener rightClick) {
		try {
            return new MaterialDialog.Builder(act)
                    .title(act.getString(titleId))
                    .content(act.getString(messageId))
                    .positiveText(act.getString(rightButtonTextId))
                    .negativeText(act.getString(leftButtonTextId))
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
						@Override
						public void onClick(MaterialDialog dialog, DialogAction which) {
							if (rightClick != null) {
								rightClick.onClick(act.getCurrentFocus());
							}

							if (dialog != null && dialog.isShowing()) {
								dialog.dismiss();
							}
						}
					})
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
						@Override
						public void onClick(MaterialDialog dialog, DialogAction which) {
							if (leftClick != null) {
								leftClick.onClick(act.getCurrentFocus());
							}

							if (dialog != null && dialog.isShowing()) {
								dialog.dismiss();
							}
						}
					}).build();
		} catch (Exception e) {
            Log.d(TAG, "Error", e);
			return null;
		}
	}

	/**
	 * 
	 * @param handler
	 * @param original
	 * @return
	 * @throws Exception
	 */
	public static DefaultHandler parseXMLHandler(DefaultHandler handler,
			String original) throws Exception {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();
		xr.setContentHandler(handler);
		InputSource inStream = new InputSource();
		inStream.setCharacterStream(new StringReader(original));
		xr.parse(inStream);
		return handler;
	}

	/**
	 * 
	 * @param handler
	 * @return
	 * @throws Exception
	 */
	public static DefaultHandler parseXMLHandler(DefaultHandler handler,
			InputSource inSource) throws Exception {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();
		xr.setContentHandler(handler);
		// InputSource inStream = new InputSource();
		// inStream.setCharacterStream(new StringReader(original));
		xr.parse(inSource);
		return handler;
	}

	public static boolean isInternetReachable() {
		boolean isReachable = false;
		try {
			SocketAddress sockaddr = new InetSocketAddress(Constant.IP,
					Constant.PORT);
			Socket sock = new Socket();
			sock.connect(sockaddr, Constant.TIME_OUT_PING);
			sock.close();
			isReachable = true;
		} catch (Exception e) {
			Log.d(Constant.TAG, "error", e);
		}
		Log.d("TAG", "isInternetReachable: " + isReachable);
		return isReachable;
	}

	/**
	 * Kiem tra ket noi mang
	 * 
	 * @param activity
	 * @return
	 */
	public static boolean isNetworkConnected(Context activity) {
		boolean val = false;
		try {
			ConnectivityManager connectivityManager = (ConnectivityManager) activity
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivityManager == null) {
				return false;
			}
			NetworkInfo mobile = connectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (mobile != null && mobile.isAvailable() && mobile.isConnected()) {
				val = true;

				int networkType = mobile.getSubtype();
				Log.d("TAG", "networkType: " + networkType);
				switch (networkType) {
				case TelephonyManager.NETWORK_TYPE_GPRS:
				case TelephonyManager.NETWORK_TYPE_EDGE:
				case TelephonyManager.NETWORK_TYPE_CDMA:
				case TelephonyManager.NETWORK_TYPE_1xRTT:
				case TelephonyManager.NETWORK_TYPE_IDEN: // api<8 : replace by
															// 11
					// 2G
					// 2G
					Toast.makeText(activity,
							activity.getString(R.string.require_2g),
							Toast.LENGTH_LONG).show();
					// createAlertDialog(activity,
					// activity.getString(R.string.require_2g),
					// activity.getString(R.string.app_name)).show();
					break;
				case TelephonyManager.NETWORK_TYPE_UMTS:
				case TelephonyManager.NETWORK_TYPE_EVDO_0:
				case TelephonyManager.NETWORK_TYPE_EVDO_A:
				case TelephonyManager.NETWORK_TYPE_HSDPA:
				case TelephonyManager.NETWORK_TYPE_HSUPA:
				case TelephonyManager.NETWORK_TYPE_HSPA:
				case TelephonyManager.NETWORK_TYPE_EVDO_B: // api<9 : replace by
															// 14
				case TelephonyManager.NETWORK_TYPE_EHRPD: // api<11 : replace by
															// 12
				case TelephonyManager.NETWORK_TYPE_HSPAP: // api<13 : replace by
															// 15
					// 3G
					break;
				case TelephonyManager.NETWORK_TYPE_LTE: // api<11 : replace by
														// 13
					// 4G
					break;
				default:
					Toast.makeText(
							activity,
							activity.getString(R.string.require_netwotk_unknown),
							Toast.LENGTH_LONG).show();
					break;
				}
			} else {
				NetworkInfo wifi = connectivityManager
						.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
				if (wifi != null && wifi.isAvailable() && wifi.isConnected()) {
					Toast.makeText(activity,
							activity.getString(R.string.require_wifi),
							Toast.LENGTH_SHORT).show();
					val = true;
				}
			}
		} catch (Exception e) {
			Log.e("Exception", e.toString(), e);
		}
		Log.d("TAG", "isNetworkConnected: " + val);
		return val;
	}

	/**
	 * 
	 * @param phoneNumber
	 * @param msg
	 * @param context
	 * @param dialogSendSMS
	 */
	public static void sendSMS(String phoneNumber, String msg, Context context,
			ProgressDialog dialogSendSMS, String synTask) {
		try {
			String message = Hex.encode(StringUtils.compress(msg));
			SmsManager sms = SmsManager.getDefault();
			ArrayList<String> parts = new ArrayList<>();
			while (message.length() > Constant.NUM_OF_CHARACTER_SMS) {
				parts.add(message.substring(0, Constant.NUM_OF_CHARACTER_SMS));
				message = message.substring(Constant.NUM_OF_CHARACTER_SMS);
			}
			parts.add(message);
			int messageCount = parts.size();

			SharedPreferences preferences = context.getSharedPreferences(
					Define.PRE_NAME, Activity.MODE_PRIVATE);
			Integer numOfMessage = Integer.parseInt(preferences.getString(
					Constant.KEY_SMS_COUNT, "0"));
			numOfMessage++;
			SharedPreferences.Editor editor = preferences.edit();
			editor.putString(Constant.KEY_SMS_COUNT, numOfMessage + "");
			editor.commit();
			if (sms == null) {
				Toast.makeText(context,
						context.getResources().getString(R.string.sms_null),
						Toast.LENGTH_LONG).show();
				return;
			}
			String token = Session.getToken();
			if (token == null) {
				token = "";
			}
			while (token.length() < 32) {
				token = token + "0";
			}
			for (int j = 0; j < messageCount; j++) {
				PendingIntent sentPI = PendingIntent.getBroadcast(context, 0,
						new Intent(Constant.REGISTER_SENT), 0);
				PendingIntent deliveredPI = PendingIntent.getBroadcast(context,
						0, new Intent(Constant.REGISTER_DELIVERED), 0);
				String msgPart = parts.get(j);
				StringBuilder header = new StringBuilder("SMART");
				header.append(token);
				header.append(synTask);
				header.append(StringUtils.standardizeNumber("" + numOfMessage,
						Constant.SMS_TRANSACTION_LENGTH));
				header.append(StringUtils.standardizeNumber("" + messageCount,
						Constant.SMS_NUM_OF_SMS_LENGTH));
				header.append(StringUtils.standardizeNumber("" + j,
						Constant.SMS_NUM_OF_SMS_LENGTH));
				sms.sendTextMessage(phoneNumber, null, header.append(msgPart)
						.toString(), sentPI, deliveredPI);
			}

		} catch (Exception e) {
			Log.e(CommonActivity.class.getName(), e.toString(), e);
			Toast.makeText(context, "" + e.toString(), Toast.LENGTH_LONG)
					.show();
			if (dialogSendSMS != null && dialogSendSMS.isShowing()) {
				dialogSendSMS.dismiss();
			}
		}
	}

	public static Dialog createDialogWithImg(Activity act, int message,
			int title, Bitmap bitmap, int leftButtonText, int rightButtonText,
			final OnClickListener leftClick, final OnClickListener rightClick) {
		try {
			final Dialog dialog = new Dialog(act);

			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.common_dialog_img);
			TextView tvTitle = (TextView) dialog
					.findViewById(R.id.tvDialogTitle);
			dialog.setCancelable(false);
			tvTitle.setText(title);
			TextView tvMessage = (TextView) dialog
					.findViewById(R.id.tvDialogContent);
			tvMessage.setText(message);
			ImageView imgView = (ImageView) dialog.findViewById(R.id.img);
			imgView.setImageBitmap(bitmap);
			// dialog.setPositiveButton(act.getString(R.string.ok), null);
			Button btnLeft = (Button) dialog.findViewById(R.id.btnLeft);
			Button btnRight = (Button) dialog.findViewById(R.id.btnRight);
			btnLeft.setText(leftButtonText);
			btnRight.setText(rightButtonText);

			OnClickListener leftClickListener = new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (leftClick != null) {
						leftClick.onClick(arg0);
					}
					dialog.dismiss();
				}
			};
			OnClickListener rightClickListener = new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (rightClick != null) {
						rightClick.onClick(arg0);
					}
					dialog.dismiss();
				}
			};
			btnLeft.setOnClickListener(leftClickListener);
			btnRight.setOnClickListener(rightClickListener);
			return dialog;
		} catch (Exception e) {
			Log.d(Constant.TAG, "error", e);
			return null;
		}
	}

	public static Dialog createAlertDialog(Activity act, int message, int title) {
		try {

            return new MaterialDialog.Builder(act)
                    .title(act.getString(title))
                    .content(act.getString(message))
                    .positiveText(act.getString(R.string.ok))
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick( MaterialDialog dialog,  DialogAction which) {
                            if(dialog != null && dialog.isShowing()){
                                dialog.dismiss();
                            }
                        }
                    }).build();
		} catch (Exception e) {
            Log.d(TAG, "Error", e);
			return null;
		}
	}

	public static Dialog createAlertDialog(final Activity act, int message,
			int title, final OnClickListener onClick) {
		try {

            return new MaterialDialog.Builder(act)
                    .title(act.getString(title))
                    .content(act.getString(message))
                    .positiveText(act.getString(R.string.ok))
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick( MaterialDialog dialog,  DialogAction which) {
                            if(onClick != null){
                                onClick.onClick(act.getCurrentFocus());
                            }

                            if(dialog != null && dialog.isShowing()){
                                dialog.dismiss();
                            }
                        }
                    }).build();
		} catch (Exception e) {
            Log.d(TAG, "Error", e);
			return null;
		}
	}

	// SHOW KEYBOARD
	public static void showKeyBoard(View view, Context context) {
		try {
			view.requestFocus();
			InputMethodManager imm = (InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
		} catch (Exception e) {
			Log.d(Constant.TAG, "error", e);
			// TODO: handle exception
		}

	}

	public static void hideKeyboard(View view, Context context) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm != null && view != null) {
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}

	}

	public static void BackFromLogin(final Activity activity,
			String description, final String permission) {
		createAlertDialog(activity, description,
				activity.getString(R.string.app_name),
				new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// Intent intent = new Intent(activity,
						// LoginActivity.class);
						// activity.startActivity(intent);
						// activity.finish();
						LoginDialog dialog = new LoginDialog(activity,
								permission);
						dialog.show();
					}
				}).show();
	}

    public static void BackFromLogin(final Activity activity,
                                     String description) {
        createAlertDialog(activity, description,
                activity.getString(R.string.app_name),
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(activity,
                                LoginActivity.class);
                        activity.startActivity(intent);
                        activity.finish();
                    }
                }).show();
    }

	public static boolean checkGps(Context context) {

		boolean check = false;
		try {
			LocationManager locationManager = (LocationManager) context
					.getSystemService(Context.LOCATION_SERVICE);
            check = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
		} catch (Exception e) {
			Log.d(Constant.TAG, "error", e);
		}

		return check;
	}

	public static void DoNotLocation(Activity context) {
		try {
			if (checkGps(context)) {
				createAlertDialog(context,
						context.getResources().getString(R.string.checkgps),
						context.getResources().getString(R.string.app_name))
						.show();
			} else {
				createAlertDialog(
						context,
						context.getResources().getString(
								R.string.cannot_get_location),
						context.getResources().getString(R.string.app_name))
						.show();

			}
		} catch (Exception e) {
			Log.d(Constant.TAG, "error", e);
		}

	}

	public static String encodeStringUrl(Activity context, String text) {
		String textEncode = "";
		try {
			textEncode = Uri.encode(text, Constant.ALLOWED_URI_CHARS);
		} catch (Exception e) {
			// TODO: handle exception
			Log.d(Constant.TAG, "error", e);
		}

		return textEncode;
	}

	public static void ChangLanguage(Activity activity) {
		try {
			SharedPreferences prefs = activity.getSharedPreferences(
					"MBCCS_SETTING_LANGUAGE", MODE_PRIVATE);
			String codeLanguage = prefs.getString("VT_LANGUAGE", "vi");
			chang(activity, codeLanguage);
		} catch (Exception e) {
			Log.d(Constant.TAG, "error", e);
			// TODO: handle exception
		}

	}

	private static void chang(Activity activity, String languageToLoad) {
		try {
			Configuration config = new Configuration();
			if (languageToLoad == null || "".equals(languageToLoad)) {
				config.locale = Locale.getDefault();
			} else {
				Locale locale = new Locale(languageToLoad);
				Locale.setDefault(locale);
				config.locale = locale;
			}

			activity.getResources().updateConfiguration(config,
					activity.getResources().getDisplayMetrics());
		} catch (Exception e) {
			Log.d(Constant.TAG, "error", e);
			// TODO: handle exception
		}

	}

	// find location follow GPS

	public static com.viettel.bss.viettelpos.v4.object.Location findMyLocationGPS(
			Context context, String methodName) {
		// if (true) {
		// com.viettel.bss.viettelpos.v4.object.Location myLocation = new
		// com.viettel.bss.viettelpos.v4.object.Location();
		// myLocation.setX("10");
		// myLocation.setY("120");
		// return myLocation;
		// }

		SharedPreferences preferences = context.getSharedPreferences(
				Define.PRE_NAME, Activity.MODE_PRIVATE);

		String name = preferences.getString(Define.KEY_MENU_NAME, "");
		boolean hasPermission = false;
		if (name.contains(Constant.VSAMenu.GPS_ONLY)) {
			hasPermission = true;
		}
		if (!hasPermission) {
			com.viettel.bss.viettelpos.v4.object.Location location = findMyLocation(context);
			try {
				SaveLog saveLog = new SaveLog(context, Constant.SYSTEM_NAME,
						Session.userName, methodName, location.getX(),
						location.getY());

				saveLog.saveLogRequest(
						location.getType(),
						new Date(),
						new Date(),
						Session.userName + "_"
								+ CommonActivity.getDeviceId(context) + "_"
								+ System.currentTimeMillis());
			} catch (Exception e) {
				// TODO: handle exception
				Log.d(Constant.TAG, "error", e);
			}
			return location;
		}
		try {
			Long currentTime = System.currentTimeMillis();
			// Log.e("currentTime",(new Date(currentTime)) + "");
			// Date date = new Date
			SharedPreferences sharedPreferences = context.getSharedPreferences(
					Define.PRE_NAME, Activity.MODE_PRIVATE);
			// String x = "0";
			// String y = "0";
			String xGPS = sharedPreferences.getString(Define.X_KEY, "0");
			com.viettel.bss.viettelpos.v4.object.Location location = new com.viettel.bss.viettelpos.v4.object.Location();
			if (!"0".equals(xGPS)) {

				// Thoi gian lay duoc toa do tu GPS la 1 phut truoc --> su
				// dung toa do nay

				Long timeGPS = Long.parseLong(xGPS.split("\\-")[1]);
				// Log.e("timeGPS",(new Date(timeGPS)) + "");
				String yGPS = sharedPreferences.getString(Define.Y_KEY, "0");
				if (currentTime - timeGPS < Constant.TIME_VALID_LOCATION_GPS) {
					location.setX(xGPS.split("\\-")[0]);
					location.setY(yGPS);
					return location;
				} else {
					// Toast.makeText(
					// context,
					// "Co toa do nhung thoi gian lau: "
					// + (currentTime - timeGPS),
					// Toast.LENGTH_LONG).show();
					if (Session.KPI_REQUEST && methodName != null
							&& !methodName.isEmpty()) {
						// Gọi các hàm đo kiểm KPI Request
						SaveLog saveLog = new SaveLog(context,
								Constant.SYSTEM_NAME, Session.userName,
								methodName, CommonActivity.findMyLocation(
										context).getX(), CommonActivity
										.findMyLocation(context).getY());

						saveLog.saveLogRequest(
								"timeInvalid " + new Date(currentTime) + "-"
										+ new Date(timeGPS),
								new Date(),
								new Date(),
								Session.userName + "_"
										+ CommonActivity.getDeviceId(context)
										+ "_" + System.currentTimeMillis());

					}
					location.setX("0");
					location.setY("0");
					return location;
				}
				// if (!x.equals("0") && !y.equals("0")) {
			}
			// Toast.makeText(
			// context,
			// "Ko hieu sao ko lay duoc toa do",
			// Toast.LENGTH_LONG).show();
			if (Session.KPI_REQUEST && methodName != null
					&& !methodName.isEmpty()) {
				// Gọi các hàm đo kiểm KPI Request
				SaveLog saveLog = new SaveLog(context, Constant.SYSTEM_NAME,
						Session.userName, methodName, CommonActivity
								.findMyLocation(context).getX(), CommonActivity
								.findMyLocation(context).getY());
				saveLog.saveLogRequest(
						"not Location",
						new Date(),
						new Date(),
						Session.userName + "_"
								+ CommonActivity.getDeviceId(context) + "_"
								+ System.currentTimeMillis());
			}
			location.setX("0");
			location.setY("0");
			return location;
		} catch (Exception e) {
			Log.d(Constant.TAG, "error", e);
			com.viettel.bss.viettelpos.v4.object.Location location = new com.viettel.bss.viettelpos.v4.object.Location();
			location.setX("0");
			location.setY("0");
			return location;
		}
	}

	public static com.viettel.bss.viettelpos.v4.object.Location findMyLocation(
			Context context) {
		String xGPS = "0";
		String yGPS = "0";
		String xAGPS = "0";
		String yAGPS = "0";
		try {
			Long currentTime = System.currentTimeMillis();
			// Log.e("currentTime",(new Date(currentTime)) + "");
			// Date date = new Date
			SharedPreferences sharedPreferences = context.getSharedPreferences(
					Define.PRE_NAME, Activity.MODE_PRIVATE);
			// String x = "0";
			// String y = "0";
			xGPS = sharedPreferences.getString(Define.X_KEY, "0");
			yGPS = sharedPreferences.getString(Define.Y_KEY, "0");

			// Kiem tra AGPS
			xAGPS = sharedPreferences.getString(Define.X_KEY_AGPS, "0");
			yAGPS = sharedPreferences.getString(Define.Y_KEY_AGPS, "0");

			com.viettel.bss.viettelpos.v4.object.Location location = new com.viettel.bss.viettelpos.v4.object.Location();
			Boolean isKPI = false;
			Long timeAGPS;
			if (!"0".equals(xGPS)) {
				// Thoi gian lay duoc toa do tu GPS la la 1 phut truoc --> su
				// dung toa do nay
				Long timeGPS = Long.parseLong(xGPS.split("\\-")[1]);
				// Log.e("timeGPS",(new Date(timeGPS)) + "");
				// String yGPS = sharedPreferences.getString(Define.Y_KEY, "0");
				if (currentTime - timeGPS > 7200000) {
					// Sau 2 tieng ma ko co update, ghi log KPI
					isKPI = true;
				}
				if ((currentTime - timeGPS) < Constant.TIME_VALID_LOCATION_GPS) {
					location.setX(xGPS.split("\\-")[0]);
					location.setY(yGPS);
					location.setType(Constant.LOCATION_TYPE_GPS
							+ " "
							+ DateTimeUtils.convertDateTimeToString(new Date(
									timeGPS), "dd/MM/yyyy HH:mm:ss")
							+ " | xAGPS =" + xAGPS + ", yAGPS = " + yAGPS);
					return location;
				}

				// Kiem tra AGPS
				// String xAGPS = sharedPreferences.getString(Define.X_KEY_AGPS,
				// "0");
				if (!"0".equals(xAGPS)) {
					timeAGPS = Long.parseLong(xAGPS.split("\\-")[1]);
					// Log.e("timeAGPS",(new Date(timeAGPS)) + "");
					if (currentTime - timeAGPS > 7200000) {
						// Sau 2 tieng ko co update, ghi log KPI

						isKPI = true;
					}
					if (timeAGPS > timeGPS) {
						// Neu thoi gian lay duoc toa do nho hon thoi gian cau
						// hinh thi moi lay,
						// nguoc lai thi return null
						if (currentTime - timeAGPS < Constant.TIME_VALID_LOCATION) {
							// String yAGPS = sharedPreferences.getString(
							// Define.Y_KEY_AGPS, "0");
							location.setX(xAGPS.split("\\-")[0]);
							location.setY(yAGPS);
							location.setType(Constant.LOCATION_TYPE_AGPS
									+ " "
									+ DateTimeUtils.convertDateTimeToString(
											new Date(timeAGPS),
											"dd/MM/yyyy HH:mm:ss")
									+ " | xAGPS =" + xAGPS + ", yAGPS = "
									+ yAGPS);
							return location;
						} else {
							if (isKPI) {
								try {
									SaveLog saveLog = new SaveLog(context,
											Constant.SYSTEM_NAME,
											Session.userName,
											"update_location_error", "0", "0");

									saveLog.saveLogRequest(
											"not update after 2h. GPS: "
													+ new Date(timeGPS)
													+ " - AGPS: "
													+ new Date(timeAGPS),
											new Date(),
											new Date(),
											Session.userName
													+ "_"
													+ CommonActivity
															.getDeviceId(context)
													+ "_"
													+ System.currentTimeMillis());
								} catch (Exception e) {
									// TODO: handle exception
									Log.d(Constant.TAG, "error", e);
								}

							}
							location.setX("0");
							location.setY("0");
							location.setType(Constant.LOCATION_TYPE_GPS
									+ " "
									+ DateTimeUtils.convertDateTimeToString(
											new Date(timeGPS),
											"dd/MM/yyyy HH:mm:ss")
									+ " | xAGPS =" + xAGPS + ", yAGPS = "
									+ yAGPS);
							return location;
						}

					}
				}

				if (currentTime - timeGPS < Constant.TIME_VALID_LOCATION) {
					location.setX(xGPS.split("\\-")[0]);
					location.setY(yGPS);
					location.setType(Constant.LOCATION_TYPE_GPS
							+ " "
							+ DateTimeUtils.convertDateTimeToString(new Date(
									timeGPS), "dd/MM/yyyy HH:mm:ss")
							+ " | xAGPS =" + xAGPS + ", yAGPS = " + yAGPS);
					return location;
				} else {
					location.setX("0");
					location.setY("0");
					location.setType(Constant.LOCATION_TYPE_GPS
							+ " "
							+ DateTimeUtils.convertDateTimeToString(new Date(
									timeGPS), "dd/MM/yyyy HH:mm:ss")
							+ " | xAGPS =" + xAGPS + ", yAGPS = " + yAGPS);
					return location;
				}
				// if (!x.equals("0") && !y.equals("0")) {
			}
			// String xAGPS = sharedPreferences.getString(Define.X_KEY_AGPS,
			// "0");
			if (!"0".equals(xAGPS)) {
				timeAGPS = Long.parseLong(xAGPS.split("\\-")[1]);
				// Neu thoi gian lay duoc toa do <10 phut thi moi lay,
				// nguoc lai thi return 0,0
				if (currentTime - timeAGPS < Constant.TIME_VALID_LOCATION) {

					location.setX(xAGPS.split("\\-")[0]);
					location.setY(yAGPS);
					location.setType(Constant.LOCATION_TYPE_AGPS
							+ " "
							+ DateTimeUtils.convertDateTimeToString(new Date(
									timeAGPS), "dd/MM/yyyy HH:mm:ss")
							+ " | xAGPS =" + xAGPS + ", yAGPS = " + yAGPS);
					return location;
				}

			}
			location.setX("0");
			location.setY("0");
			location.setType(Constant.LOCATION_TYPE_GPS + " | xAGPS =" + xAGPS
					+ ", yAGPS = " + yAGPS);
			return location;
		} catch (Exception e) {
			Log.d(Constant.TAG, "error", e);
			com.viettel.bss.viettelpos.v4.object.Location location = new com.viettel.bss.viettelpos.v4.object.Location();
			location.setX("0");
			location.setY("0");
			location.setType(Constant.LOCATION_TYPE_GPS + " | xAGPS =" + xAGPS
					+ ", yAGPS = " + yAGPS);
			return location;
		}
	}

	// genera account
	public static String genPasswordAuto(String customerName) {
		try {
			if (customerName == null || "".equals(customerName.trim())) {
				// CustomerName Is Alphabet
				StringBuilder strBuffer = new StringBuilder();
				for (int j = 65; j <= (65 + 24); j++) {
					strBuffer.append((char) j);
				}
				customerName = strBuffer.toString();
			}
			StringBuilder buffer = new StringBuilder();
			customerName = convertUnicode2Nosign(customerName);
			customerName = customerName.replaceAll("[^a-zA-Z0-9]", "");
			customerName = refineStringForPassword(customerName);
			Random random = new Random();
			customerName = customerName.concat("0123456789");
			char[] chars = customerName.toCharArray();
			for (int i = 0; i < 6; i++) {
				buffer.append(chars[random.nextInt(chars.length)]);
			}
			return buffer.toString();
		} catch (Exception e) {
			Log.d(Constant.TAG, "error", e);
			return "";
		}

	}

	public static String convertUnicode2Nosign(String org) {
		if (org == null || org.trim().isEmpty()) {
			return "";
		}
		// convert to VNese no sign. @haidh 2008
		char arrChar[] = org.toCharArray();
		char result[] = new char[arrChar.length];
		for (int i = 0; i < arrChar.length; i++) {
			switch (arrChar[i]) {
			case '\u00E1':
			case '\u00E0':
			case '\u1EA3':
			case '\u00E3':
			case '\u1EA1':
			case '\u0103':
			case '\u1EAF':
			case '\u1EB1':
			case '\u1EB3':
			case '\u1EB5':
			case '\u1EB7':
			case '\u00E2':
			case '\u1EA5':
			case '\u1EA7':
			case '\u1EA9':
			case '\u1EAB':
			case '\u1EAD':
			case '\u0203':
			case '\u01CE': {
				result[i] = 'a';
				break;
			}
			case '\u00E9':
			case '\u00E8':
			case '\u1EBB':
			case '\u1EBD':
			case '\u1EB9':
			case '\u00EA':
			case '\u1EBF':
			case '\u1EC1':
			case '\u1EC3':
			case '\u1EC5':
			case '\u1EC7':
			case '\u0207': {
				result[i] = 'e';
				break;
			}
			case '\u00ED':
			case '\u00EC':
			case '\u1EC9':
			case '\u0129':
			case '\u1ECB': {
				result[i] = 'i';
				break;
			}
			case '\u00F3':
			case '\u00F2':
			case '\u1ECF':
			case '\u00F5':
			case '\u1ECD':
			case '\u00F4':
			case '\u1ED1':
			case '\u1ED3':
			case '\u1ED5':
			case '\u1ED7':
			case '\u1ED9':
			case '\u01A1':
			case '\u1EDB':
			case '\u1EDD':
			case '\u1EDF':
			case '\u1EE1':
			case '\u1EE3':
			case '\u020F': {
				result[i] = 'o';
				break;
			}
			case '\u00FA':
			case '\u00F9':
			case '\u1EE7':
			case '\u0169':
			case '\u1EE5':
			case '\u01B0':
			case '\u1EE9':
			case '\u1EEB':
			case '\u1EED':
			case '\u1EEF':
			case '\u1EF1': {
				result[i] = 'u';
				break;
			}
			case '\u00FD':
			case '\u1EF3':
			case '\u1EF7':
			case '\u1EF9':
			case '\u1EF5': {
				result[i] = 'y';
				break;
			}
			case '\u0111': {
				result[i] = 'd';
				break;
			}
			case '\u00C1':
			case '\u00C0':
			case '\u1EA2':
			case '\u00C3':
			case '\u1EA0':
			case '\u0102':
			case '\u1EAE':
			case '\u1EB0':
			case '\u1EB2':
			case '\u1EB4':
			case '\u1EB6':
			case '\u00C2':
			case '\u1EA4':
			case '\u1EA6':
			case '\u1EA8':
			case '\u1EAA':
			case '\u1EAC':
			case '\u0202':
			case '\u01CD': {
				result[i] = 'A';
				break;
			}
			case '\u00C9':
			case '\u00C8':
			case '\u1EBA':
			case '\u1EBC':
			case '\u1EB8':
			case '\u00CA':
			case '\u1EBE':
			case '\u1EC0':
			case '\u1EC2':
			case '\u1EC4':
			case '\u1EC6':
			case '\u0206': {
				result[i] = 'E';
				break;
			}
			case '\u00CD':
			case '\u00CC':
			case '\u1EC8':
			case '\u0128':
			case '\u1ECA': {
				result[i] = 'I';
				break;
			}
			case '\u00D3':
			case '\u00D2':
			case '\u1ECE':
			case '\u00D5':
			case '\u1ECC':
			case '\u00D4':
			case '\u1ED0':
			case '\u1ED2':
			case '\u1ED4':
			case '\u1ED6':
			case '\u1ED8':
			case '\u01A0':
			case '\u1EDA':
			case '\u1EDC':
			case '\u1EDE':
			case '\u1EE0':
			case '\u1EE2':
			case '\u020E': {
				result[i] = 'O';
				break;
			}
			case '\u00DA':
			case '\u00D9':
			case '\u1EE6':
			case '\u0168':
			case '\u1EE4':
			case '\u01AF':
			case '\u1EE8':
			case '\u1EEA':
			case '\u1EEC':
			case '\u1EEE':
			case '\u1EF0': {
				result[i] = 'U';
				break;
			}

			case '\u00DD':
			case '\u1EF2':
			case '\u1EF6':
			case '\u1EF8':
			case '\u1EF4': {
				result[i] = 'Y';
				break;
			}
			case '\u0110':
			case '\u00D0':
			case '\u0089': {
				result[i] = 'D';
				break;
			}
			default:
				result[i] = arrChar[i];
			}
		}
		return new String(result);
	}

	private static String refineStringForPassword(String input) {
		if (input == null) {
			return null;
		}
		return input.replaceAll("[^a-zA-Z0-9]", "");
	}

	public static void hideSoftKeyboard(Activity activity) {
		try {
			InputMethodManager im = (InputMethodManager) activity
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			im.hideSoftInputFromWindow(activity.getWindow().getDecorView()
					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		} catch (Exception e) {
			Log.d(Constant.TAG, "error", e);
		}

	}

	public static void hideKeyboard(Activity activity) {
		try {
			InputMethodManager inputMethodManager = (InputMethodManager) activity
					.getSystemService(Activity.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(activity
					.getCurrentFocus().getWindowToken(), 0);
		} catch (Exception e) {
			Log.d(Constant.TAG, "error", e);
		}

	}

	public static Dialog createDialogLargeContent(Activity act, String message,
			String titleId, int buttonTextId) {
		try {
			final Dialog dialog = new Dialog(act,
					android.R.style.Theme_Light_NoTitleBar_Fullscreen);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.common_dialog_large_content);
			dialog.setCancelable(false);
			TextView tvTitle = (TextView) dialog
					.findViewById(R.id.tvDialogTitle);
			tvTitle.setText(titleId);
			TextView tvMessage = (TextView) dialog
					.findViewById(R.id.tvDialogContent);
			tvMessage.setText(Html.fromHtml(message));

			Button btnLeft = (Button) dialog.findViewById(R.id.btnOk);
			btnLeft.setText(buttonTextId);
			OnClickListener leftClickListener = new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					dialog.dismiss();
				}
			};
			btnLeft.setOnClickListener(leftClickListener);
			return dialog;
		} catch (Exception e) {
			Log.d(Constant.TAG, "error", e);
			return null;
		}
	}

	public static String getStardardIsdn(String phoneNumber) {
		String strReturn = "";
		if (phoneNumber == null) {
			return strReturn;
		}
		strReturn = phoneNumber;
		if (strReturn.startsWith("+")) {
			strReturn = strReturn.substring(1);
		}
		if (strReturn.startsWith("0")) {
			strReturn = strReturn.substring(1);
		}
		if (!strReturn.startsWith("84")) {
			strReturn = "84" + strReturn;
		}
		return strReturn;
	}

	/**
	 * Kiem tra object null or empty
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isNullOrEmpty(Object input) {
        if (input == null) {
            return true;
        }
        if (input instanceof String) {
            return input.toString().trim().isEmpty();
        }
        if (input instanceof EditText) {
			return ((EditText) input).getText().toString().trim().isEmpty();
        }
        if (input instanceof List) {
            return ((List) input).isEmpty();
        }

		if(input instanceof HashMap){
			return ((HashMap)input).isEmpty();
		}

        return input instanceof ArrayList && ((ArrayList) input).isEmpty();
    }

	public static boolean isNullOrEmptyArray(Object... input) {
		if (isNullOrEmpty(input)) {
			return true;
		}
        for (Object anInput : input) {
            if (isNullOrEmpty(anInput)) {
                return true;
            }
        }
		return false;
	}

	public static void unZipFileToDirectory(String zipFile, String outputFolder) {

		byte[] buffer = new byte[1024];
		try {

			// create output directory is not exists
			File folder = new File(outputFolder);
			if (!folder.exists()) {
				folder.mkdir();
			}

			// get the zip file content
			ZipInputStream zis = new ZipInputStream(
					new FileInputStream(zipFile));

			// get the zipped file list entry
			ZipEntry ze = zis.getNextEntry();

			while (ze != null) {

				String fileName = ze.getName();
				File newFile = new File(outputFolder + File.separator
						+ fileName);

				Log.d("file unzip : ", "" + newFile.getAbsoluteFile());

				// create all non exists folders
				// else you will hit FileNotFoundException for compressed folder
				new File(newFile.getParent()).mkdirs();

				FileOutputStream fos = new FileOutputStream(newFile);

				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}

				fos.close();
				ze = zis.getNextEntry();
			}

			zis.closeEntry();
			zis.close();

			System.out.println("Done");

		} catch (IOException ex) {
			Log.d(Constant.TAG, "error", ex);
		}
	}

	// thinhhq1 =====================> call phone
	public static void callphone(final Activity context, final String phone) {

		OnClickListener onclickCall = new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String uri = "tel:" + phone;
				Intent intent = new Intent(Intent.ACTION_CALL);
				intent.setData(Uri.parse(uri));
				try {
					context.startActivity(intent);
				} catch (SecurityException ex) {
					Log.e("SecurityException call phone", ex.getMessage());
				}
			}
		};

		CommonActivity.createDialog(context,
				context.getString(R.string.confirmspport,phone),
				context.getString(R.string.app_name),
				context.getString(R.string.cancel),
                context.getString(R.string.ok), null,onclickCall).show();
		
	}

	public static String checkStardardIsdn(String phoneNumber) {
		String strReturn = "";
		if (phoneNumber == null) {
			return strReturn;
		}
		strReturn = phoneNumber.trim();
		if (strReturn.startsWith("+")) {
			strReturn = strReturn.substring(1);
		}

		if(StringUtils.isDigit(phoneNumber)) {
			while (!strReturn.isEmpty() && strReturn.startsWith("0")) {
				strReturn = strReturn.substring(1);
			}
		}

		if (strReturn.startsWith("84")) {
			strReturn = strReturn.substring(2, strReturn.length());
		}
		return strReturn;
	}

	public static Boolean validateIsdn(String phoneNumber) {
		String strReturn;
		if (phoneNumber == null) {
			return false;
		}
		strReturn = phoneNumber;
		if (strReturn.startsWith("+")) {
			strReturn = strReturn.substring(1);
		}

		if (strReturn.startsWith("84")) {
			strReturn = strReturn.substring(2, strReturn.length());
			Log.d("validateIsdn", "leng phone number: " + strReturn.length());
		}

		while (!strReturn.isEmpty() && strReturn.startsWith("0")) {
			strReturn = strReturn.substring(1);
		}

		if (isNullOrEmpty(strReturn)) {
			return false;
		}
		if (!StringUtils.isDigit(strReturn)) {
			return false;
		}
		if (strReturn.length() >= 9 && strReturn.length() <= 13) {
			return true;
		}
		return false;
	}
	public static Boolean validateHomephone(String phoneNumber) {
		String strReturn = "";
		if (phoneNumber == null) {
			return false;
		}
		strReturn = phoneNumber;
		if (strReturn.startsWith("+")) {
			strReturn = strReturn.substring(1);
		}
		while (!strReturn.isEmpty() && strReturn.startsWith("0")) {
			strReturn = strReturn.substring(1);
		}

		if (strReturn.startsWith("84")) {
			strReturn = strReturn.substring(2, strReturn.length());
			Log.d("validateIsdn", "leng phone number: " + strReturn.length());
		}
		if (isNullOrEmpty(strReturn)) {
			return false;
		}
		if (!StringUtils.isDigit(strReturn)) {
			return false;
		}

			return strReturn.length() == 9 || strReturn.length() == 10;

	}
	public static String convertUnicode2NosignString(String org) {
		// convert to VNese no sign. @haidh 2008
		char arrChar[] = org.toCharArray();
		char result[] = new char[arrChar.length];
		for (int i = 0; i < arrChar.length; i++) {
			switch (arrChar[i]) {
			case '\u00E1':
			case '\u00E0':
			case '\u1EA3':
			case '\u00E3':
			case '\u1EA1':
			case '\u0103':
			case '\u1EAF':
			case '\u1EB1':
			case '\u1EB3':
			case '\u1EB5':
			case '\u1EB7':
			case '\u00E2':
			case '\u1EA5':
			case '\u1EA7':
			case '\u1EA9':
			case '\u1EAB':
			case '\u1EAD':
			case '\u0203':
			case '\u01CE': {
				result[i] = 'a';
				break;
			}
			case '\u00E9':
			case '\u00E8':
			case '\u1EBB':
			case '\u1EBD':
			case '\u1EB9':
			case '\u00EA':
			case '\u1EBF':
			case '\u1EC1':
			case '\u1EC3':
			case '\u1EC5':
			case '\u1EC7':
			case '\u0207': {
				result[i] = 'e';
				break;
			}
			case '\u00ED':
			case '\u00EC':
			case '\u1EC9':
			case '\u0129':
			case '\u1ECB': {
				result[i] = 'i';
				break;
			}
			case '\u00F3':
			case '\u00F2':
			case '\u1ECF':
			case '\u00F5':
			case '\u1ECD':
			case '\u00F4':
			case '\u1ED1':
			case '\u1ED3':
			case '\u1ED5':
			case '\u1ED7':
			case '\u1ED9':
			case '\u01A1':
			case '\u1EDB':
			case '\u1EDD':
			case '\u1EDF':
			case '\u1EE1':
			case '\u1EE3':
			case '\u020F': {
				result[i] = 'o';
				break;
			}
			case '\u00FA':
			case '\u00F9':
			case '\u1EE7':
			case '\u0169':
			case '\u1EE5':
			case '\u01B0':
			case '\u1EE9':
			case '\u1EEB':
			case '\u1EED':
			case '\u1EEF':
			case '\u1EF1': {
				result[i] = 'u';
				break;
			}
			case '\u00FD':
			case '\u1EF3':
			case '\u1EF7':
			case '\u1EF9':
			case '\u1EF5': {
				result[i] = 'y';
				break;
			}
			case '\u0111': {
				result[i] = 'd';
				break;
			}
			case '\u00C1':
			case '\u00C0':
			case '\u1EA2':
			case '\u00C3':
			case '\u1EA0':
			case '\u0102':
			case '\u1EAE':
			case '\u1EB0':
			case '\u1EB2':
			case '\u1EB4':
			case '\u1EB6':
			case '\u00C2':
			case '\u1EA4':
			case '\u1EA6':
			case '\u1EA8':
			case '\u1EAA':
			case '\u1EAC':
			case '\u0202':
			case '\u01CD': {
				result[i] = 'A';
				break;
			}
			case '\u00C9':
			case '\u00C8':
			case '\u1EBA':
			case '\u1EBC':
			case '\u1EB8':
			case '\u00CA':
			case '\u1EBE':
			case '\u1EC0':
			case '\u1EC2':
			case '\u1EC4':
			case '\u1EC6':
			case '\u0206': {
				result[i] = 'E';
				break;
			}
			case '\u00CD':
			case '\u00CC':
			case '\u1EC8':
			case '\u0128':
			case '\u1ECA': {
				result[i] = 'I';
				break;
			}
			case '\u00D3':
			case '\u00D2':
			case '\u1ECE':
			case '\u00D5':
			case '\u1ECC':
			case '\u00D4':
			case '\u1ED0':
			case '\u1ED2':
			case '\u1ED4':
			case '\u1ED6':
			case '\u1ED8':
			case '\u01A0':
			case '\u1EDA':
			case '\u1EDC':
			case '\u1EDE':
			case '\u1EE0':
			case '\u1EE2':
			case '\u020E': {
				result[i] = 'O';
				break;
			}
			case '\u00DA':
			case '\u00D9':
			case '\u1EE6':
			case '\u0168':
			case '\u1EE4':
			case '\u01AF':
			case '\u1EE8':
			case '\u1EEA':
			case '\u1EEC':
			case '\u1EEE':
			case '\u1EF0': {
				result[i] = 'U';
				break;
			}

			case '\u00DD':
			case '\u1EF2':
			case '\u1EF6':
			case '\u1EF8':
			case '\u1EF4': {
				result[i] = 'Y';
				break;
			}
			case '\u0110':
			case '\u00D0':
			case '\u0089': {
				result[i] = 'D';
				break;
			}
			default:
				result[i] = arrChar[i];
			}
		}
		return new String(result);
	}

	public static Bitmap getResizedBitmap(Bitmap bm, int newMaxLength) {
		int width = bm.getWidth();
		int height = bm.getHeight();

		long newArea = (long) newMaxLength * newMaxLength;
		long oldArea = (long) width * height;
		if (newArea < oldArea) {
			float scale = (float) Math.sqrt((double) newArea / oldArea);
			// float scaleWidth = ((float) newWidth) / width;
			// float scaleHeight = ((float) newHeight) / height;

			Matrix matrix = new Matrix();
			// RESIZE THE BIT MAP
			matrix.postScale(scale, scale);
			// RECREATE THE NEW BITMAP
            return Bitmap.createBitmap(bm, 0, 0, width, height,
                    matrix, false);
		} else {
			return bm;
		}
	}

    public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);
        // RECREATE THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
                matrix, false);
        return resizedBitmap;
    }



	private static class MemoryAsyncTask extends
			AsyncTask<String, String, Long> {
		private final Activity activity;

		public MemoryAsyncTask(Activity activity) {
			this.activity = activity;
		}

		@Override
		protected Long doInBackground(String... params) {
			ActivityManager activityManager = (ActivityManager) activity
					.getSystemService(ACTIVITY_SERVICE);
			MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
			activityManager.getMemoryInfo(memoryInfo);

			long memory = (memoryInfo.availMem + memoryInfo.threshold) / 1048576;
			if (memoryInfo.lowMemory)
				memory = -1L;
			return memory;
		}

		@Override
		protected void onPostExecute(Long memory) {
			if (memory < Constant.lowMemory) {
				String message = String.format(
						activity.getString(R.string.lowMemory), "" + memory, ""
								+ Constant.lowMemory);
				createAlertDialog(activity, message, "Low Memory").show();
			} else {
				String message = String.format(
						activity.getString(R.string.okMemory), "" + memory);
				Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
			}

		}
	}

	public static JSONObject getMemory(Activity activity) {
		JSONObject deviceInfo = null;

		MemoryAsyncTask asyn = new MemoryAsyncTask(activity);
		asyn.execute();

		// ActivityManager activityManager = (ActivityManager)
		// activity.getSystemService(ACTIVITY_SERVICE);
		// MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
		// activityManager.getMemoryInfo(memoryInfo);
		//
		// Log.i(Constant.TAG, " memoryInfo.availMem " + memoryInfo.availMem +
		// "\n");
		// Log.i(Constant.TAG, " memoryInfo.lowMemory " + memoryInfo.lowMemory +
		// "\n");
		// Log.i(Constant.TAG, " memoryInfo.threshold " + memoryInfo.threshold +
		// "\n");
		// Log.i(Constant.TAG, " memoryInfo.totalMem " + memoryInfo.totalMem +
		// "\n");
		//
		// deviceInfo = new JSONObject();
		// deviceInfo.put("availMem", memoryInfo.availMem);
		// deviceInfo.put("lowMemory", memoryInfo.lowMemory);
		// deviceInfo.put("threshold", memoryInfo.threshold);
		// deviceInfo.put("totalMem", memoryInfo.totalMem);
		//
		// long memory = (memoryInfo.availMem + memoryInfo.threshold) / 1048576;
		//
		// if(memoryInfo.lowMemory || memory < Constant.lowMemory) {
		// String message =
		// String.format(activity.getString(R.string.lowMemory), "" + memory, ""
		// + Constant.lowMemory);
		// createAlertDialog(activity, message, "Low Memory").show();
		// } else {
		// String message = String.format(activity.getString(R.string.okMemory),
		// "" + memory);
		// Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
		// }
		// Log.i(Constant.TAG, "Build.BRAND " + Build.BRAND);
		// Log.i(Constant.TAG, "Build.CPU_ABI " + Build.CPU_ABI);
		// Log.i(Constant.TAG, "Build.CPU_ABI2 " + Build.CPU_ABI2);
		// Log.i(Constant.TAG, "Build.DEVICE " + Build.DEVICE);
		// Log.i(Constant.TAG, "Build.MANUFACTURER " + Build.MANUFACTURER);
		// Log.i(Constant.TAG, "Build.MODEL " + Build.MODEL);
		// Log.i(Constant.TAG, "Build.PRODUCT " + Build.PRODUCT);
		// Log.i(Constant.TAG, "Build.TIME " + Build.TIME);
		// Log.i(Constant.TAG, "Build.VERSION.RELEASE " +
		// Build.VERSION.RELEASE);
		// Log.i(Constant.TAG, "Build.VERSION.SDK_INT " +
		// Build.VERSION.SDK_INT);
		//
		// deviceInfo.put("BRAND", Build.BRAND);
		// deviceInfo.put("CPU_ABI", Build.CPU_ABI);
		// deviceInfo.put("CPU_ABI2", Build.CPU_ABI2);
		// deviceInfo.put("DEVICE", Build.DEVICE);
		// deviceInfo.put("MANUFACTURER", Build.MANUFACTURER);
		// deviceInfo.put("MODEL", Build.MODEL);
		// deviceInfo.put("PRODUCT", Build.PRODUCT);
		// deviceInfo.put("TIME", Build.TIME);
		// deviceInfo.put("RELEASE", Build.VERSION.RELEASE);
		// deviceInfo.put("SDK_INT", Build.VERSION.SDK_INT);

		// List<RunningAppProcessInfo> runningAppProcesses =
		// activityManager.getRunningAppProcesses();
		// Map<Integer, String> pidMap = new TreeMap<Integer, String>();
		// for (RunningAppProcessInfo runningAppProcessInfo :
		// runningAppProcesses) {
		// pidMap.put(runningAppProcessInfo.pid,
		// runningAppProcessInfo.processName);
		// }
		// Collection<Integer> keys = pidMap.keySet();
		//
		// for (int key : keys) {
		// int pids[] = new int[1];
		// pids[0] = key;
		// Debug.MemoryInfo[] memoryInfoArray =
		// activityManager.getProcessMemoryInfo(pids);
		// for (android.os.Debug.MemoryInfo pidMemoryInfo : memoryInfoArray) {
		// Log.i(Constant.TAG, String.format("** MEMINFO in pid %d [%s] **\n",
		// pids[0], pidMap.get(pids[0])));
		// Log.i(Constant.TAG, " pidMemoryInfo.getTotalPrivateDirty(): " +
		// pidMemoryInfo.getTotalPrivateDirty() + "\n");
		// Log.i(Constant.TAG, " pidMemoryInfo.getTotalPss(): " +
		// pidMemoryInfo.getTotalPss() + "\n");
		// Log.i(Constant.TAG, " pidMemoryInfo.getTotalSharedDirty(): " +
		// pidMemoryInfo.getTotalSharedDirty() + "\n");
		// }
		// }
		return deviceInfo;
	}

	public static void toast(Activity activity, String mesage) {
		Toast.makeText(activity, mesage, Toast.LENGTH_LONG).show();
	}

	public static void toast(Activity activity, int stringId) {
		Toast.makeText(activity, activity.getString(stringId),
				Toast.LENGTH_LONG).show();
	}

	public static void toastShort(Activity activity, int stringId) {
		Toast.makeText(activity, activity.getString(stringId),
				Toast.LENGTH_SHORT).show();
	}

	public static String checkVTmapCode(String temp) {
		String[] result = temp.split("_");
		if (result.length == 4) {
			return result[3];
		}
		if (result.length == 3) {
			return result[2];
		}
		if (result.length == 2) {
			return result[1];
		}
		return null;

	}

	public static Object cloneObject(Object objSource) {
		Object objDest = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(objSource);
			oos.flush();
			oos.close();
			bos.close();
			byte[] byteData = bos.toByteArray();
			ByteArrayInputStream bais = new ByteArrayInputStream(byteData);
			try {
				objDest = new ObjectInputStream(bais).readObject();
			} catch (ClassNotFoundException e) {
				Log.d(Constant.TAG, "error", e);
			}
		} catch (IOException e) {
			Log.d(Constant.TAG, "error", e);
		}
		return objDest;
	}

	private static boolean isInternetReachableMap(String ip, int port) {
		boolean isReachable = false;
		try {
			SocketAddress sockaddr = new InetSocketAddress(ip, port);
			Socket sock = new Socket();
			sock.connect(sockaddr, 60000);
			sock.close();
			isReachable = true;
		} catch (Exception e) {
			Log.d(Constant.TAG, "error", e);
		}
		Log.d("TAG", "isInternetReachable: " + isReachable);
		return isReachable;
	}

	public static void checkConnectionMap(Activity activity) {

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		if (isInternetReachableMap(Constant.IP_MAP_NEW, Constant.PORT_MAP)) {
			AppInfo.setServerAddress("http", Constant.IP_MAP_NEW,
					Constant.PORT_MAP);
		} else {
			if (isInternetReachableMap(Constant.IP_MAP_OLD, Constant.PORT_MAP)) {
				AppInfo.setServerAddress("http", Constant.IP_MAP_OLD,
						Constant.PORT_MAP);
			} else {
				CommonActivity.createAlertDialog(activity,
						activity.getString(R.string.notconnectmap),
						activity.getString(R.string.app_name)).show();

			}
		}

		// if (isConnectedToServer("http://viettelmaps.vn",30000)) {
		// AppInfo.setServerAddress("http", Constant.IP_MAP_NEW,
		// Constant.PORT_MAP);
		// } else {
		// if (isConnectedToServer("http://viettelmap.vn", 30000)) {
		// AppInfo.setServerAddress("http", Constant.IP_MAP_OLD,
		// Constant.PORT_MAP);
		// } else {
		// CommonActivity.createAlertDialog(activity,
		// activity.getString(R.string.notconnectmap),
		// activity.getString(R.string.app_name)).show();
		//
		// }
		// }

	}

	public static boolean isConnectedToServer(String url, int timeout) {
		try {

			// InetAddress.getByName(Constant.IP_MAP_NEW).isReachable(Constant.TIME_OUT_PING);

			URL myUrl = new URL(url);
			URLConnection connection = myUrl.openConnection();
			connection.setConnectTimeout(timeout);
			connection.connect();
			return true;
		} catch (Exception e) {
			// Handle your exceptions
			Log.d(Constant.TAG, "error", e);
			return false;
		}
	}


	

	public static String convertCharacter1(String input) {
		String inpNormal = Normalizer.normalize(input, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		String reStr = pattern.matcher(inpNormal).replaceAll("");
		reStr = reStr.replace("đ", "d");
		reStr = reStr.replace("Đ", "D");
		return reStr;
	}

	// format string them
	public static String formatIsdn1(String t) {
		String s = t.substring(t.length() - 3, t.length());
		int length = t.length() - 3;
		for (int i = 0; i < length; i++) {
			s = "*" + s;
		}
		return s;
	}
	// format string them
	public static String formatIsdn(String t) {
		String s = t.substring(0,t.length() - 3);

		for (int i = t.length() - 3; i < t.length(); i++) {
			s = s + "*";
		}
		return s;
	}
	/**
	 * Ham lay mo ta loi tu Exception
	 * 
	 * @param context
	 * @param ex
	 * @return
	 */
	public static String getDescription(Context context, Exception ex) {
		if (ex instanceof UnknownHostException) {
			return context.getString(R.string.unknown_host_exception);
		} else if (ex instanceof ConnectTimeoutException) {
			// return context.getString(R.string.connect_timeout_exception);
			return context.getString(R.string.txt_transaction_timeout);
		} else if (ex instanceof SocketTimeoutException) {
			// return context.getString(R.string.socket_timeout_exception);
			return context.getString(R.string.txt_transaction_timeout);
//			return context.getString(R.string.http_host_connect_exception);
		} else if (ex instanceof SocketException) {
			// return context.getString(R.string.socket_close_exception);
			return context.getString(R.string.txt_transaction_timeout);
		} else if (ex instanceof IllegalStateException) {
			return context.getString(R.string.illegal_state_exception);
		} else if (ex instanceof IOException) {
			// return context.getString(R.string.socket_io_exception);
			return context.getString(R.string.txt_transaction_timeout);
		} else {
			return context.getString(R.string.exception);
		}
	}
	public static String convertTechnogoly(String infraType) {
		if (isNullOrEmpty(infraType)) {
			return "";
		}
		if (Constant.IN_FRATYPE_CCN.equals(infraType)) { //
			return Constant.IN_FRATYPE_CCN_CM;
		}
		if (Constant.IN_FRATYPE_CATV.equals(infraType)) {
			return Constant.IN_FRATYPE_CATV_CM;
		}
		if (Constant.IN_FRATYPE_FCN.equals(infraType)) {
			return Constant.IN_FRATYPE_FCN_CM;
		}
		if (Constant.IN_FRATYPE_GPON.equals(infraType)) {
			return Constant.IN_FRATYPE_GPON_CM;
		}
		return "";
	}

	public static void showDatePickerDialog(Context ctx, final EditText edt) {
		final Calendar cal = Calendar.getInstance();
		OnDateSetListener callback = new OnDateSetListener() {
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				cal.set(year, monthOfYear, dayOfMonth);
				edt.setText(DateTimeUtils.convertDateTimeToString(
						cal.getTime(), "dd/MM/yyyy"));
			}
		};

		Date date = DateTimeUtils.convertStringToTime(edt.getText().toString(),
				"dd/MM/yyyy");
		if(date != null){
			cal.setTime(date);
		}

		DatePickerDialog pic = new FixedHoloDatePickerDialog(ctx, AlertDialog.THEME_HOLO_LIGHT,callback,
				cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH));
		pic.setTitle(ctx.getString(R.string.chon_ngay));
		pic.show();
	}

	public static void showDatePickerDialog(Context ctx, final TextView edt) {
		final Calendar cal = Calendar.getInstance();
		OnDateSetListener callback = new OnDateSetListener() {
			public void onDateSet(DatePicker view, int year, int monthOfYear,
								  int dayOfMonth) {
				cal.set(year, monthOfYear, dayOfMonth);
				edt.setText(DateTimeUtils.convertDateTimeToString(
						cal.getTime(), "dd/MM/yyyy"));
			}
		};

		if(CommonActivity.isNullOrEmpty(edt.getText().toString())){
			edt.setText(DateTimeUtils.convertDateTimeToString(new Date(),
					"dd/MM/yyyy"));
		}

		cal.setTime(DateTimeUtils.convertStringToTime(edt.getText().toString(),
				"dd/MM/yyyy"));
		DatePickerDialog pic = new FixedHoloDatePickerDialog(ctx, AlertDialog.THEME_HOLO_LIGHT,callback,
				cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH));
		pic.setTitle(ctx.getString(R.string.chon_ngay));
		pic.show();
	}

	//	public static final void focusOnView(final ScrollView scrollView,
//			final View childView) {
//		try {
//			scrollView.post(new Runnable() {
//				@Override
//				public void run() {
//					scrollView.scrollTo(0, childView.getBottom());
//				}
//			});
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
	// lay so nam
	public static int getYear(Date d1, Date d2) {
		double diff = d2.getTime() - d1.getTime();
		double d = 1000 * 60 * 60 * 24 * 365;
		return (int) Math.round(diff / d);

	}

	public static String getStardardIsdnBCCS(String phoneNumber) {
		String strReturn = "";
		if (phoneNumber == null) {
			return strReturn;
		}
		strReturn = phoneNumber;
		if (strReturn.startsWith("+")) {
			strReturn = strReturn.substring(1);
		}
		if (strReturn.startsWith("0")) {
			strReturn = strReturn.substring(1);
		}
		if (strReturn.startsWith("84")) {
			strReturn = strReturn.substring(2, strReturn.length());
		}
		return strReturn;
	}

	public static int InsertAPN(String name, String apn, Context context) {

		// Set the URIs and variables
		int id = -1;
		boolean existing = false;
		final Uri APN_TABLE_URI = Uri.parse("content://telephony/carriers");
		final Uri PREFERRED_APN_URI = Uri
				.parse("content://telephony/carriers/preferapn");

		// Check if the specified APN is already in the APN table, if so skip
		// the insertion
		Cursor parser = context.getContentResolver().query(APN_TABLE_URI, null,
				null, null, null);
		parser.moveToLast();
		while (!parser.isBeforeFirst()) {
			int index = parser.getColumnIndex("name");
			String n = parser.getString(index);
			if (n.equals(name)) {
				existing = true;
				Toast.makeText(context, "APN s-intranet already configured.",
						Toast.LENGTH_SHORT).show();
				break;
			}
			parser.moveToPrevious();
		}

		// if the entry doesn't already exist, insert it into the APN table
		if (!existing) {

			// Initialize the Content Resolver and Content Provider
			ContentResolver resolver = context.getContentResolver();
			ContentValues values = new ContentValues();

			// Capture all the existing field values excluding name
			Cursor apu = context.getContentResolver().query(PREFERRED_APN_URI,
					null, null, null, null);
			apu.moveToFirst();

			// Assign them to the ContentValue object
			values.put("name", name); // the method parameter
			values.put("apn", apn);

			// Actual insertion into table
			Cursor c = null;
			try {
				Uri newRow = resolver.insert(APN_TABLE_URI, values);

				if (newRow != null) {
					c = resolver.query(newRow, null, null, null, null);
					int idindex = c.getColumnIndex("_id");
					c.moveToFirst();
					id = c.getShort(idindex);
				}
			} catch (SQLException ex) {
				Log.d(Constant.TAG, "error", ex);
			}
			if (c != null)
				c.close();
		}

		return id;
	}

	// Takes the ID of the new record generated in InsertAPN and sets that
	// particular record the default preferred APN configuration
	public static boolean setPreferredAPN(String name, Context context) {
		final Uri APN_TABLE_URI = Uri.parse("content://telephony/carriers");
		final Uri PREFERRED_APN_URI = Uri
				.parse("content://telephony/carriers/preferapn");

		Cursor parser = context.getContentResolver().query(APN_TABLE_URI, null,
				null, null, null);
		parser.moveToLast();
		int id = -1;
		while (!parser.isBeforeFirst()) {
			int index = parser.getColumnIndex("name");
			String n = parser.getString(index);
			if (n.equals(name)) {
				int idindex = parser.getColumnIndex("_id");
				parser.moveToFirst();
				id = parser.getShort(idindex);
				break;
			}
			parser.moveToPrevious();
		}

		// If the id is -1, that means the record was found in the APN table
		// before insertion, thus, no action required
		if (id == -1) {
			return false;
		}

		Uri.parse("content://telephony/carriers");

		boolean res = false;
		ContentResolver resolver = context.getContentResolver();
		ContentValues values = new ContentValues();

		values.put("apn_id", id);
		try {
			resolver.update(PREFERRED_APN_URI, values, null, null);
			Cursor c = resolver.query(PREFERRED_APN_URI, new String[] { "name",
					"apn" }, "_id=" + id, null, null);
			if (c != null) {
				res = true;
				c.close();
			}
		} catch (SQLException e) {
			Log.d(Constant.TAG, "error", e);
		}
		return res;
	}
	public static void clearCacheStatic() {

		FragmentConnectionMobileNew.mapPakage = null;
		FragmentConnectionMobileNew.subType = "";

	}

	public static String getNormalText(String text) {

		if (CommonActivity.isNullOrEmpty(text)) {
			return "";
		}

		return Normalizer.normalize(text.trim(), Normalizer.Form.NFC);
	}

    public static boolean askPermission(){
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1;
    }

    public static String formatJsonString(String text){

        StringBuilder json = new StringBuilder();
        String indentString = "";

        for (int i = 0; i < text.length(); i++) {
            char letter = text.charAt(i);
            switch (letter) {
                case '{':
                case '[':
                    json.append("\n").append(indentString).append(letter).append("\n");
                    indentString = indentString + "\t";
                    json.append(indentString);
                    break;
                case '}':
                case ']':
                    indentString = indentString.replaceFirst("\t", "");
                    json.append("\n").append(indentString).append(letter);
                    break;
                case ',':
                    json.append(letter).append("\n").append(indentString);
                    break;

                default:
                    json.append(letter);
                    break;
            }
        }

        return json.toString();
    }

    public static Dialog createDialog(final Activity act, String message,
                                              String title, String leftButtonText, String rightButtonText,
                                              final OnClickListener leftClick, final OnClickListener rightClick) {
        try {
            return new MaterialDialog.Builder(act)
                    .title(title)
                    .content(message)
                    .positiveText(rightButtonText)
                    .negativeText(leftButtonText)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick( MaterialDialog dialog,  DialogAction which) {
                            if(rightClick != null){
                                rightClick.onClick(act.getCurrentFocus());
                            }

                            if(dialog != null && dialog.isShowing()){
                                dialog.dismiss();
                            }
                        }
                    })
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick( MaterialDialog dialog,  DialogAction which) {
                            if(leftClick != null){
                                leftClick.onClick(act.getCurrentFocus());
                            }

                            if(dialog != null && dialog.isShowing()){
                                dialog.dismiss();
                            }
                        }
                    }).build();
        } catch (Exception e) {
            Log.d(TAG, "Error", e);
            return null;
        }
    }
	public static void clearCache() {
		if (FragmentInsertOrUpdateCoporation.hashMapCorporationCategoryBO != null) {
			FragmentInsertOrUpdateCoporation.hashMapCorporationCategoryBO = null;
		}

//		if (!CommonActivity
//				.isNullOrEmptyArray(FragmentChangeSim.arrSpecObjects)) {
//			FragmentChangeSim.arrSpecObjects.clear();
//		}

//		if(!CommonActivity.isNullOrEmptyArray(SaleManagerFragment.lstDataSale)){
//			SaleManagerFragment.lstDataSale = new ArrayList<GridMenu>();
//		}
//		if(!CommonActivity.isNullOrEmptyArray(CustomerManagerFragment.lstDataCus)){
//			CustomerManagerFragment.lstDataCus = new ArrayList<GridMenu>();
//		}
//		if(FragmentConnectionMobileNew.hashmapProductOffer != null && FragmentConnectionMobileNew.hashmapProductOffer.size() > 0){
//			FragmentConnectionMobileNew.hashmapProductOffer = new HashMap<>();
//		}

	}

	public static boolean isEmailValid(String email) {
		boolean isValid = false;

		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

    public static void addMenuActionToDatabase(Context mContext, String nameActionMenu,
											   String urlActionMenu, String idActionMenu, int idIcon) {
        try {
            MenuActionMain menuActionMain = new MenuActionMain(mContext, Session.userName);
            menuActionMain.addMenuActionToDatabase(nameActionMenu, urlActionMenu, idActionMenu, idIcon);
        } catch (Exception ex){
            Log.d("addMenuActionToDatabase", "Error addMenuActionToDatabase: " + ex.getMessage());
        }
    }
	public static String getUser(String user){
		String result = "";
		try{
			result = new SecurityUtil().decrypt(user.trim());
			return result;
		}catch (Exception e) {
			Log.d("getUser", e.toString());
		}
		return null;
	}
	public static String getPass(String ps){
		String result = "";
		try{
			result = new SecurityUtil().decrypt(ps.trim());
			return result;
		}catch (Exception e) {
			Log.d("getPass", e.toString());
		}
		return null;
	}
	public static Bitmap decodeFile(File f,int WIDTH,int HIGHT){
		try {
			//Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f),null,o);

			//The new size we want to scale to
			final int REQUIRED_WIDTH=WIDTH;
			final int REQUIRED_HIGHT=HIGHT;
			//Find the correct scale value. It should be the power of 2.
			int scale=1;
			while(o.outWidth/scale/2>=REQUIRED_WIDTH && o.outHeight/scale/2>=REQUIRED_HIGHT)
				scale*=2;

			//Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize=scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {}
		return null;
	}
	public  static String replaceNull(String str){
		if(CommonActivity.isNullOrEmpty(str)){
			return "";
		}
		return str.replace("null","");
	}

	public static boolean checkMapUsage(String code , Spin spin){
		if(CommonActivity.isNullOrEmpty(code)){
			return false;
		}
		if(CommonActivity.isNullOrEmpty(spin)){
			return false;
		}
		if(code.equals(spin.getValue())   && spin.getId().contains("5")){
			return true;
		}
		return false;
	}


	public static int getDiffYears(Date first, Date last) {
		Calendar a = getCalendar(first);
		Calendar b = getCalendar(last);

//		19/07/2002
//		20/07/2017

		int diff = b.get(b.YEAR) - a.get(a.YEAR); // 15
		if (a.get(a.MONTH) > b.get(b.MONTH) ||
				(a.get(a.MONTH) == b.get(b.MONTH) && a.get(a.DATE) < b.get(b.DATE))) {
			diff ++;
		}
		return diff;
	}

	public static int getDiffYearsMain(Date first, Date last) {
		Calendar a = getCalendar(first);
		Calendar b = getCalendar(last);
		int diff = b.get(b.YEAR) - a.get(a.YEAR);

		return diff;
	}
	public static Calendar getCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
	public static void showConfirmValidate(Activity activity, int id) {
		Dialog dialog = CommonActivity.createAlertDialog(activity, activity.getResources().getString(id), activity.getResources().getString(R.string.app_name));
		dialog.show();
	}

	public static  void showConfirmValidate(Activity activity, String  message) {
		Dialog dialog = CommonActivity.createAlertDialog(activity, message, activity.getResources().getString(R.string.app_name));
		dialog.show();
	}


	public static String getActStatusString(Activity activity ,String actStatus, String payType) {
		if (CommonActivity.isNullOrEmpty(actStatus) || actStatus.length() != 3) {
			return activity.getString(R.string.common_no_infor);
		}
		if (CommonActivity.isNullOrEmpty(actStatus) || "000".equals(actStatus)) {
			return activity.getString(R.string.common_act_normal);
		}
		if (CommonActivity.isNullOrEmpty(payType)) {
			payType = "1";
		}

		char bit1 = actStatus.charAt(0);
		char bit2 = actStatus.charAt(1);
		char bit3 = actStatus.charAt(2);

		if (bit2 == '3') {
			return activity.getString(R.string.common_act_not_active);
		}

		String bit1String = activity.getString(R.string.common_act_cus_req);
		if (bit1 == '3') {
			bit1String = "";
		}
		String bit2String = activity.getString(R.string.common_act_charge);
		if ("2".equals(payType)) {
			bit2String = activity.getString(R.string.common_act_spam);
		}

		String bit3String = activity.getString(R.string.common_act_red_warn);

		String bit1Info = "".equals(getBitInfo(activity,bit1)) ? null : getBitInfo(activity,bit1) + " " + bit1String;
		String bit2Info = "".equals(getBitInfo(activity,bit2)) ? null : getBitInfo(activity,bit2) + " " + bit2String;
		String bit3Info = "".equals(getBitInfo(activity,bit3)) ? null : getBitInfo(activity,bit3) + " " + bit3String;
		String bit = "";
		if(!CommonActivity.isNullOrEmpty(bit1Info)){
			bit = bit + bit1Info;
		}
		if(!CommonActivity.isNullOrEmpty(bit2Info)){
			bit = bit + bit2Info;
		}
		if(!CommonActivity.isNullOrEmpty(bit3Info)){
			bit = bit + bit3Info;
		}
		return bit;
	}
	public static String getBitInfo(Activity activity,char bit) {
		if (bit == '0') {
			return "";
		}
		if (bit == '1') {
			return activity.getString(R.string.common_one_block_way);
		}
		if (bit == '2') {
			return activity.getString(R.string.common_two_block_way);
		}
		if (bit == '3') {
			return activity.getString(R.string.common_spam_block_way);
		}
		return "";
	}
	
	public static Dialog createAlertDialogInfo(Activity act, String message, String title,String ok) {
		try {
			return new MaterialDialog.Builder(act)
					.title(title)
					.content(message)
					.positiveText(ok)
					.onPositive(new MaterialDialog.SingleButtonCallback() {
						@Override
						public void onClick( MaterialDialog dialog,  DialogAction which) {
							if(dialog != null && dialog.isShowing()){
								dialog.dismiss();
							}
						}
					}).build();
		} catch (Exception e) {
			Log.d(TAG, "Error", e);
			return null;
		}
	}

	public static Long getCurrentStaffId(Context context) {
		String staffMngtCode = Session.userName;
		Staff staff = StaffBusiness.getStaffByStaffCode(context, staffMngtCode);
		return staff.getStaffId();
	}

	public static void openFile(File file, Activity activity) {

		Intent intent = new Intent(Intent.ACTION_VIEW);
		String extension = android.webkit.MimeTypeMap
				.getFileExtensionFromUrl(Uri.fromFile(file).toString());
		Log.d(TAG, "extension = " + extension);

		String mimetype = android.webkit.MimeTypeMap.getSingleton()
				.getMimeTypeFromExtension(extension);
		if (extension.equalsIgnoreCase("") || mimetype == null) {
			// do nothing
		} else {
			intent.setDataAndType(Uri.fromFile(file), mimetype);
		}

		intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		Intent intentChooser = Intent.createChooser(intent, "Open File");
		try {
			activity.startActivity(intentChooser);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void openFileFromLink(String link, Activity activity) {
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link.replace(" ", "%20")));
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		Intent intentChooser = Intent.createChooser(intent, "Open File");
		try {
			activity.startActivity(intentChooser);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void saveFileBase64(String content, String directory, String fileName) {
		FileOutputStream fileOutputStream = null;
		InputStream inputStream = null;
		File dirFile = new File(directory);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		try {
			fileOutputStream = new FileOutputStream(directory + File.separator + fileName);
			byte[] fileByte = Base64.decode(content, Base64.DEFAULT);
			inputStream = new ByteArrayInputStream(fileByte);
			byte[] buffer = new byte[1024];
			int leng;
			while ((leng = inputStream.read(buffer)) > 0) {
				fileOutputStream.write(buffer, 0, leng);
			}
			fileOutputStream.close();
			inputStream.close();
			Log.d("saveFileBase64", directory + File.separator + fileName);
		} catch (Exception e) {
			Log.e("saveFileBase64", e.getMessage());
		} finally {
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String getBase64String(Bitmap bitmap, String ext) {

		if (CommonActivity.isNullOrEmpty(bitmap)) {
			return null;
		}

		Bitmap bitmapImage = CommonActivity.getResizedBitmap(
				bitmap, Constant.SIZE_IMAGE_SCALE);
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			if (Constant.IMG_EXT_PNG.equals(ext)) {
				bitmapImage.compress(Bitmap.CompressFormat.PNG,
						Constant.DEFAULT_JPEG_QUALITY, baos);
			} else {
				bitmapImage.compress(Bitmap.CompressFormat.PNG,
						Constant.DEFAULT_JPEG_QUALITY, baos);
			}
			byte[] imageBytes = baos.toByteArray();
			String base64String = Base64.encodeToString(
					imageBytes, Activity.TRIM_MEMORY_BACKGROUND);
			baos.close();
			return base64String;
		} catch (IOException ex) {
			Log.e("Error", "getBase64String", ex);
			return "";
		}
	}

	public static File getFileInDir(String directoryPath, String fileName) {
		File dirFile = new File(directoryPath);
		if (!dirFile.exists()) {
			return null;
		}
		File[] filenames = dirFile.listFiles();
		String name;
		for (File tmpf : filenames){
			name = tmpf.getName().substring(0, tmpf.getName().length() - 3);
			name = name.split("_")[0];
			if (name.equals(fileName)) {
				return tmpf;
			}
		}
		return null;
	}


	public static void deleteAllFileInDir(String dirPath) {
		File dirFile = new File(dirPath);
		if (!dirFile.exists()) {
			return;
		}
		File[] filenames = dirFile.listFiles();
		for (File tmpf : filenames){
			tmpf.delete();
		}
	}
	public static String SaveImage(Bitmap finalBitmap) {
		String root = Environment.getExternalStorageDirectory().toString();
		File myDir = new File(root + "/Shopic Snaps");

		if (!myDir.exists())
			myDir.mkdirs();

		// Random generator = new Random();
		// int n = 10000;
		// n = generator.nextInt(n);
		String fname = "Image_" + System.currentTimeMillis() + ".jpg";
		File file = new File(myDir, fname);

		if (file.exists())
			file.delete();

		try {
			FileOutputStream out = new FileOutputStream(file);
			finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return root + "/App Snaps/" + fname;
	}
	public static List<File> getAllFileInDir(String directoryPath) {
		List<File> result=new ArrayList<>();
		File dirFile = new File(directoryPath);
		if (!dirFile.exists()) {
			return null;
		}
		File[] filenames = dirFile.listFiles();
		String name;
		for (File tmpf : filenames){
			result.add(tmpf);
		}
		return result;
	}

	public static String getFileExtension(String filePath) {
		try {
			return filePath.substring(filePath.lastIndexOf(".") + 1);
		} catch (Exception e) {
			return Constant.IMG_EXT_JPG;
		}
	}
	public static boolean validateInputPin(Long channelTypeId, Long type){
		if(channelTypeId == null){
			return true;
		}

		//kenh nhan vien khac da dich vu khong bat buoc nhap ma OTP
		if(channelTypeId.equals(14L) && (type == null || !type.equals(9L))){
			return false;
		}

		return true;
	}

	public static void removeRecordByCode(Map<String, ArrayList<RecordPrepaid>> mapListRecordPrepaid , String code) {
		for (Map.Entry<String, ArrayList<RecordPrepaid>> entry : mapListRecordPrepaid.entrySet()) {
			ArrayList<RecordPrepaid> recordPrepaids = entry.getValue();
			if(!CommonActivity.isNullOrEmpty(recordPrepaids)){
				for (int i= 0; i < recordPrepaids.size() ; i++ ){
					RecordPrepaid recordPrepaidTemp = recordPrepaids.get(i);
					if (recordPrepaidTemp.getCode().equals(code) && recordPrepaids.size() > 1) {
						recordPrepaids.remove(i);
						i--;
						break;
					}
				}
			}
		}
	}
	public static boolean areThereMockPermissionApps(Context context) {

		int count = 0;

		PackageManager pm = context.getPackageManager();
		List<ApplicationInfo> packages =
				pm.getInstalledApplications(PackageManager.GET_META_DATA);

		for (ApplicationInfo applicationInfo : packages) {
			try {
				PackageInfo packageInfo = pm.getPackageInfo(applicationInfo.packageName,
						PackageManager.GET_PERMISSIONS);

				// Get Permissions
				String[] requestedPermissions = packageInfo.requestedPermissions;

				if (requestedPermissions != null) {
					for (int i = 0; i < requestedPermissions.length; i++) {
						if (requestedPermissions[i]
								.equals("android.permission.ACCESS_MOCK_LOCATION")
								&& !applicationInfo.packageName.equals(context.getPackageName())) {
							count++;
						}
					}
				} else {
					return false;
				}
			} catch (PackageManager.NameNotFoundException e) {
				Log.e("E","Got exception " + e.getMessage());
				return false;
			}
		}

		if (count > 0)
			return true;
		return false;

		// mo check
//      return false;

	}

	public static boolean isMockSettingsON(Context context) {
		// returns true if mock location enabled, false if not enabled.
//      Logs.i("DATA", "ALLOW_MOCK_LOCATION : "+Settings.Secure.getString(context.getContentResolver(),
//                                    Settings.Secure.ALLOW_MOCK_LOCATION));
		if (Settings.Secure.getString(context.getContentResolver(),
				Settings.Secure.ALLOW_MOCK_LOCATION).equals("0"))
			return false;
		else
			return true;

		// mo check
//      return false;


	}

}
