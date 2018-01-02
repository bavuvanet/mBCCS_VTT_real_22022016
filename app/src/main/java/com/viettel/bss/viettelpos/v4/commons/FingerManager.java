package com.viettel.bss.viettelpos.v4.commons;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.os.CancellationSignal;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;

/**
 * Created by Toancx on 12/29/2016.
 */

public class FingerManager {
    private CancellationSignal cancellationSignal;

    @SuppressLint("NewApi")
    public void fingerListener(Context context,
                               final OnAuthenticationSucceed onSuccess,
                               final OnAuthenticationError onError,
                               final OnAuthenticationHelp onHelp,
                               final OnAuthenticationFail onFail) {
        try {
            if (!checkOSCompatible() || !checkFingerSupported(context)) {
                return;
            }
            final SharedPreferences preferences = context.getSharedPreferences(
                    Define.PRE_NAME, Activity.MODE_PRIVATE);
            String useFinger = preferences.getString(Constant.KEY_FINGER_USER,
                    "");
            if ("1".equals(useFinger)) {

                final FingerprintManager.AuthenticationCallback mAuthenticationCallback = new FingerprintManager.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationSucceeded(
                            FingerprintManager.AuthenticationResult result) {
                        onSuccess.onAuthenticationSucceed();
                    }

                    @Override
                    public void onAuthenticationError(int errorCode,
                                                      CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                        onError.onAuthenticationError(errString.toString());
                    }

                    @Override
                    public void onAuthenticationHelp(int helpCode,
                                                     CharSequence helpString) {
                        // TODO Auto-generated method stub
                        super.onAuthenticationHelp(helpCode, helpString);
                        onHelp.onAuthenticationHelp(helpString.toString());
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        onFail.onAuthenticationFail();
                    }
                };

                final FingerprintManager fingerprintManager = (FingerprintManager) context
                        .getSystemService(Context.FINGERPRINT_SERVICE);
                cancellationSignal = new CancellationSignal();
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                fingerprintManager.authenticate(null, cancellationSignal,
                        0, mAuthenticationCallback, null);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public void cancelFinger() {
        if (cancellationSignal != null) {
            cancellationSignal.cancel();
            cancellationSignal = null;
        }
    }

    public static boolean checkOSCompatible() {
        try {
            return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M;
        } catch (Exception e) {
            Log.e("Exception", "Exception", e);
        }
        return false;
    }

    @SuppressLint("NewApi")
    public static boolean checkFingerSupported(Context context) {
        try {
            if (!checkOSCompatible()) {
                return false;
            }
            final FingerprintManager fingerprintManager = (FingerprintManager) context
                    .getSystemService(Context.FINGERPRINT_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return false;
            }
            return fingerprintManager.isHardwareDetected() && fingerprintManager.hasEnrolledFingerprints();
        } catch (Exception e) {
            Log.e("Exception", "Exception", e);
        }

        return false;
    }

    public static boolean checkFingerEnable(Context context) {
        try {
            SharedPreferences preferences = context.getSharedPreferences(
                    Define.PRE_NAME, Activity.MODE_PRIVATE);

            String useFinger = preferences.getString(Constant.KEY_FINGER_USER,
                    "");

            if ("1".equals(useFinger)) {
                return true;
            }
        } catch (Exception e) {
            Log.e("Exception", "Exception", e);
        }

        return false;
    }

    public interface OnAuthenticationSucceed {
        void onAuthenticationSucceed();
    }

    public interface OnAuthenticationError {
        void onAuthenticationError(String msg);
    }

    public interface OnAuthenticationHelp {
        void onAuthenticationHelp(String msg);
    }

    public interface OnAuthenticationFail {
        void onAuthenticationFail();
    }

    public static void enableFinger(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                Define.PRE_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constant.KEY_FINGER_USER, "1");
        String[] x = EncryptKeystore.encrypt(Session.passWord);
        if (x != null) {
            editor.putString(Constant.KEY_PASS, x[0]);
            editor.putString(Constant.KEY_IV, x[1]);
        }
        Toast.makeText(context,
                context.getString(R.string.finger_active_ok),
                Toast.LENGTH_LONG).show();
        editor.commit();
    }
    public static void disableFinger(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                Define.PRE_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
         editor.putString(Constant.KEY_FINGER_USER, "0");
        editor.putString(Constant.KEY_PASS, "");
        editor.putString(Constant.KEY_IV, "");

        Toast.makeText(context,
                context.getString(R.string.finger_inactive_ok),
                Toast.LENGTH_LONG).show();
        editor.commit();
    }
}
