package com.viettel.bss.viettelpos.v4.commons;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


public class GPSTracker extends AppCompatActivity {
    private final String TAG = "GPSTracker";
    // flag for GPS status
    private boolean isGPSEnabled = false;

    // flag for network status
    private boolean isNetworkEnabled = false;

    // flag for GPS status

    private static final Location location = null; // location

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1; // 30 seconds
    // Declaring a Location Manager
    private LocationManager locationManager;
    private Boolean isUpdateAGPS = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!(Thread.getDefaultUncaughtExceptionHandler() instanceof CustomExceptionHandler)) {
            Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(
                    this));
        }
        turnGPSOn();
        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);

        // getting GPS status
        isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnabled) {

            OnClickListener rightClick = new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(
                            Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);

                }
            };
            System.out.println("12345 aaaaaaaaaaaaaaa 2");
            Dialog dialog = CommonActivity
                    .createDialog(
                            this,
                            getResources()
                                    .getString(
                                            com.viettel.bss.viettelpos.v4.R.string.GPS_AGPS_not_turn_on),
                            getResources()
                                    .getString(
                                            com.viettel.bss.viettelpos.v4.R.string.app_name),
                            getResources()
                                    .getString(
                                            com.viettel.bss.viettelpos.v4.R.string.cancel),
                            getResources().getString(
                                    com.viettel.bss.viettelpos.v4.R.string.ok),
                            null, rightClick);
            if (dialog != null) {
                dialog.show();
            }
        }

        if(CommonActivity.askPermission()) {
            int havePermission = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
            if (havePermission == PackageManager.PERMISSION_GRANTED) {
                if(isGPSEnabled){
                    requestLocationUpdates();
                }

                if (isNetworkEnabled) {
                    Log.d(TAG, "requestLocationUpdatesAGPS");
                    requestLocationUpdatesAGPS();
                }
            }
        } else {
            if(isGPSEnabled){
                requestLocationUpdates();
            }

            if (isNetworkEnabled) {
                Log.d(TAG, "requestLocationUpdatesAGPS");
                requestLocationUpdatesAGPS();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);

        // getting GPS status
        isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if(CommonActivity.askPermission()) {
            int havePermission = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
            if (havePermission == PackageManager.PERMISSION_GRANTED) {
                if(isGPSEnabled){
                    requestLocationUpdates();
                }

                if (isNetworkEnabled) {
                    Log.d(TAG, "requestLocationUpdatesAGPS");
                    requestLocationUpdatesAGPS();
                }
            }
        } else {
            if(isGPSEnabled){
                requestLocationUpdates();
            }

            if (isNetworkEnabled) {
                Log.d(TAG, "requestLocationUpdatesAGPS");
                requestLocationUpdatesAGPS();
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void requestLocationUpdates() {
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES, GPSLL);
        if (locationManager != null && location == null) {
            Location location = locationManager
                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                SharedPreferences sharedPreferences = getSharedPreferences(
                        Define.PRE_NAME, Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Define.X_KEY, location.getLatitude() + "-"
                        + location.getTime());
                editor.putString(Define.Y_KEY, "" + location.getLongitude());
                editor.commit();
            }
        }
    }

    private void requestLocationUpdatesAGPS() {
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES, aGPSLL);
        // Toast.makeText(getApplicationContext(), "AGPS Enable",
        // Toast.LENGTH_SHORT).show();
        if (locationManager != null && location == null) {
            Location location = locationManager
                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                SharedPreferences sharedPreferences = getSharedPreferences(
                        Define.PRE_NAME, Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Define.X_KEY_AGPS, location.getLatitude()
                        + "-" + location.getTime());
                editor.putString(Define.Y_KEY_AGPS,
                        "" + location.getLongitude());
                editor.commit();
            }
        }
    }

    /**
     * Stop using GPS listener Calling this function will stop using GPS in your
     * app
     */
    private void stopUsingGPS() {
        try {
            if (locationManager != null) {
                locationManager.removeUpdates(GPSLL);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    private void stopUsingAGPS() {
        try {
            if (locationManager != null && isUpdateAGPS) {
                locationManager.removeUpdates(aGPSLL);
                isUpdateAGPS = false;
            }

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        stopUsingAGPS();
        stopUsingGPS();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        stopUsingAGPS();
        stopUsingGPS();
    }

    private final LocationListener GPSLL = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {

            // Toast.makeText(
            // getApplicationContext(),
            // "----GPS: " + location.getLatitude() + " - "
            // + location.getLongitude(), Toast.LENGTH_SHORT)
            // .show();
            SharedPreferences sharedPreferences = getSharedPreferences(
                    Define.PRE_NAME, Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Define.X_KEY, location.getLatitude() + "-"
                    + System.currentTimeMillis());
            editor.putString(Define.Y_KEY, "" + location.getLongitude());
            editor.commit();
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };
    private final LocationListener aGPSLL = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onLocationChanged(Location location) {
            SharedPreferences sharedPreferences = getSharedPreferences(
                    Define.PRE_NAME, Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            // Toast.makeText(
            // getApplicationContext(),
            // "AGPS: " + location.getLatitude() + " - "
            // + location.getLongitude(), Toast.LENGTH_SHORT)
            // .show();
            editor.putString(Define.X_KEY_AGPS, location.getLatitude() + "-"
                    + System.currentTimeMillis());
            editor.putString(Define.Y_KEY_AGPS, "" + location.getLongitude());
            editor.commit();

        }
    };

    private void turnGPSOn() {
        try {
            String provider = Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            if (!provider.contains("gps")) {
                // if gps is disabled
                final Intent intent = new Intent();
                intent.setClassName("com.android.settings",
                        "com.android.settings.widget.SettingsAppWidgetProvider");
                intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
                intent.setData(Uri.parse("3"));
                sendBroadcast(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
		
		
		try {
		View view = getCurrentFocus();
		boolean ret = super.dispatchTouchEvent(event);

		if (view instanceof EditText) {
			View w = getCurrentFocus();
			int scrcoords[] = new int[2];
			w.getLocationOnScreen(scrcoords);
			float x = event.getRawX() + w.getLeft() - scrcoords[0];
			float y = event.getRawY() + w.getTop() - scrcoords[1];

			if (event.getAction() == MotionEvent.ACTION_UP
					&& (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w
							.getBottom())) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(getWindow().getCurrentFocus()
						.getWindowToken(), 0);
			}
		}
		return ret;
		} catch (Exception e) {
			return super.dispatchTouchEvent(event);
		}
		
    }



    private static final int REQUEST_CODE_ASK_PERMISSIONS = 12371425;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (String permission : permissions) {
                    if (permission.equals(Manifest.permission.ACCESS_COARSE_LOCATION)
                            || permission.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        requestLocationUpdates();
                    }
                }
                break;
            default:
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}