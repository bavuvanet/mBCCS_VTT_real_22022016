package com.viettel.bss.viettelpos.v4.synchronizationdata;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.nfc.tech.MifareUltralight;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.FingerManager;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.SdCardHelper;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.ZipUtils;
import com.viettel.bss.viettelpos.v4.infrastrucure.dal.InfrastrucureDB;
import com.viettel.bss.viettelpos.v4.sale.business.CacheData;
import com.viettel.bss.viettelpos.v4.sale.business.StaffBusiness;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FragmentSynthrozation extends Fragment implements OnClickListener {
	public String TAG = "FragmentSynthrozation";
	private LinearLayout lnSynData = null;
    private LinearLayout llManageApParam = null;
    private LinearLayout lnUpdateVersion = null;

	ArrayList<OjectSyn> lisOjectSyns = null;
	GetMaxOraRowScnDal getOraRowScnDal = null;
	private Context context;

	protected Bundle mBundle;
	protected TextView txtNameActionBar;
	protected TextView txtAddressActionBar;
	private Activity act;
	private LinearLayout btnUpgradeFix;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		act = getActivity();

	}

	@Override
	public void onResume() {
		super.onResume();
        MainActivity.getInstance().setTitleActionBar(R.string.system);
	}

	@Override
	public void onAttach(Activity activity) {
		Log.d("TAG", "onAttach onCreateView");
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d("TAG", "onDetach onCreateView");
		View mView = inflater.inflate(R.layout.synchrozation_layout, container,
				false);
        final SwitchCompat switchCompat = (SwitchCompat) mView.findViewById(R.id.switchCompat);
        CardView cardView = (CardView) mView.findViewById(R.id.cardView);
        if (!FingerManager.checkOSCompatible()
                || !FingerManager.checkFingerSupported(getActivity())) {
            cardView.setVisibility(View.GONE);
        } else {
            cardView.setVisibility(View.VISIBLE);
            if (FingerManager.checkFingerEnable(getActivity())) {
                switchCompat.setChecked(true);
            } else {
                switchCompat.setChecked(false);
            }
        }

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences preferences = context.getSharedPreferences(
                        Define.PRE_NAME, Activity.MODE_PRIVATE);
                if (FingerManager.checkFingerEnable(getActivity())) {
                    FingerManager.disableFinger(getActivity());
                    Toast.makeText(getActivity(),
                            getString(R.string.finger_inactive_ok),
                            Toast.LENGTH_LONG).show();
                } else {
                    FingerManager.enableFinger(getActivity());
                    Toast.makeText(getActivity(),
                            getString(R.string.finger_active_ok),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

		context = MainActivity.getInstance();
		Unit(mView);
		lnSynData.setOnClickListener(this);

		// ==============cap nhat version ==================
		lnUpdateVersion.setOnClickListener(this);
		return mView;
	}

	private void Unit(View v) {
		lnSynData = (LinearLayout) v.findViewById(R.id.lnSynData);
		llManageApParam = (LinearLayout) v.findViewById(R.id.llManageApParam);
		lnUpdateVersion = (LinearLayout) v.findViewById(R.id.lnUpdateVersion);
		btnUpgradeFix = (LinearLayout) v.findViewById(R.id.btnUpgradeFix);
		if (CommonActivity.isNullOrEmpty(Session.getFixErrorVersion())) {
			btnUpgradeFix.setVisibility(View.GONE);
		}
		btnUpgradeFix.setOnClickListener(this);
		llManageApParam.setOnClickListener(this);

		if("PMVT_HUYPQ15".equalsIgnoreCase(Session.userName)){
			llManageApParam.setVisibility(View.VISIBLE);
		}
	}

	// ==== syndata click listenner ===============
    private final OnClickListener SynDataClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (CommonActivity.isNetworkConnected(getActivity())) {
				try {
					// getActivity().deleteDatabase(Define.DB_NAME);
					SyncAsyncTask syncAsncTask = new SyncAsyncTask(
							getActivity());
					syncAsncTask.execute(Session.getToken(), Session.userName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
						getResources().getString(R.string.errorNetwork),
						getResources().getString(R.string.app_name));
				dialog.show();
			}

		}
	};

	// ==== update version click =============
    private final OnClickListener UpdateVersionSyn = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// call Asyntask========
			if (CommonActivity.isNetworkConnected(getActivity())) {
				UpdateVersionAsyn updateVersionAsyn = new UpdateVersionAsyn(
						getActivity(), Constant.PATH_UPDATE_MBCCS_VERSION
								+ Session.token);
				updateVersionAsyn.execute();
			} else {
				Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
						getResources().getString(R.string.errorNetwork),
						getResources().getString(R.string.app_name));
				dialog.show();
			}

		}
	};

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btnHome:
			CommonActivity.callphone(getActivity(), Constant.phoneNumber);
			break;
		case R.id.relaBackHome:
			// getActivity().onBackPressed();
			ReplaceFragment.replaceFragmentToHome(getActivity(), true);
			break;
		case R.id.lnSynData:
			Log.d("OnClick", "OnClick");

			// // =========hoi co chac chan dong bo ko============
			if (CommonActivity.isNetworkConnected(getActivity())) {
				Dialog dialog = CommonActivity.createDialog(getActivity(),
						getResources().getString(R.string.issyn),
						getResources().getString(R.string.syndata),
						getResources().getString(R.string.cancel),
						getResources().getString(R.string.ok),
						null, SynDataClick);
				dialog.show();

			} else {
				Dialog dialog = CommonActivity.createAlertDialog(
						getActivity(),
						getResources().getString(R.string.errorNetwork),
						getResources().getString(R.string.app_name));
				dialog.show();
			}

			break;
			case R.id.lnUpdateVersion:
				//
				if (CommonActivity.isNetworkConnected(getActivity())) {
//					if (CacheData.getInstanse().getVersion()
//							.equals(CacheData.getInstanse().getVersionclient())) {
//						Dialog dialog = CommonActivity.createAlertDialog(
//								getActivity(),
//								getResources().getString(R.string.notversion),
//								getResources()
//										.getString(R.string.updateversion));
//						dialog.show();
//					} else {

						Dialog dialog = CommonActivity.createDialog(
								getActivity(),
								getResources().getString(R.string.isversion),
								getResources()
										.getString(R.string.updateversion),
								getResources().getString(R.string.cancel),
								getResources().getString(R.string.ok),
								null, UpdateVersionSyn);
						dialog.show();

//					}
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.errorNetwork),
							getResources().getString(R.string.app_name));
					dialog.show();
				}
				break;
			case R.id.btnUpgradeFix:
				OnClickListener updateFixClick = new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						if (CommonActivity.isNetworkConnected(getActivity())) {
							UpdateVersionAsyn updateVersionAsyn = new UpdateVersionAsyn(
									getActivity(),
									Constant.PATH_UPDATE_FIX_ERROR_VERSION
											+ Session.token);
							updateVersionAsyn.execute();
						} else {
							Dialog dialog = CommonActivity
									.createAlertDialog(
											getActivity(),
											getResources().getString(
													R.string.errorNetwork),
											getResources().getString(
													R.string.app_name));
							dialog.show();
						}

					}
				};
				String msg = getString(R.string.confirmUpgradeVersion2);
				Dialog dialog = CommonActivity.createDialog(getActivity(), msg,
						getString(R.string.app_name), getString(R.string.ok),
						getString(R.string.cancel), updateFixClick, null);
				dialog.show();
				break;
			case R.id.llManageApParam:
				ManagerApParamFragment fragment =new ManagerApParamFragment();
				ReplaceFragment.replaceFragment(getActivity(), fragment, true);
				break;
		}
	}

	private class SyncAsyncTask extends AsyncTask<String, String, String> {
		final ProgressDialog progress;
		String shop[] = new String[2];
		private final Activity activity;

		public SyncAsyncTask(Activity activity) {
			this.activity = activity;
			this.progress = new ProgressDialog(this.activity);
			this.progress.setMessage(getResources().getString(
					R.string.first_downloading));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
			progress.setCancelable(false);
		}

		@Override
		protected String doInBackground(String... params) {
			Session.isSyncRunning = true;
			return sync(params[0], params[1]);

		}

		@Override
		protected void onPreExecute() {
			Long start = System.currentTimeMillis();
			while (Session.isSyncRunning) {
				try {
					Thread.sleep(1000L);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (System.currentTimeMillis() - start > 300000L) {
					break;
				}

			}
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(String result) {
			Session.isSyncRunning = false;
			this.progress.dismiss();
			if (Constant.SUCCESS_CODE.equals(result)) {
				// Dong bo du lieu thanh cong, luu bien staff vao session, vao
				// man hinh chinh
				// chinh
				InfrastrucureDB mInfrastrucureDB = new InfrastrucureDB(activity);
				shop = mInfrastrucureDB.getProvince();
				mInfrastrucureDB.close();
				Session.loginUser = StaffBusiness.getStaffByStaffCode(
						getActivity(), Session.userName);
				Session.province = shop[0];
				Session.district = shop[1];
				Dialog dialog = CommonActivity.createAlertDialog(MainActivity
						.getInstance(),
						context.getResources()
								.getString(R.string.syndatasucess), context
								.getResources().getString(R.string.syndata));
				dialog.show();
				// Intent i = new Intent(getActivity(), Login2Activity.class);
				// startActivity(i);
			} else if (Constant.ERROR_CODE.equals(result)) {
				// String message = errorMessage;
				String title = getString(R.string.app_name);
				Dialog dialog = CommonActivity.createAlertDialog(activity,
						getActivity().getString(R.string.syndatafails), title);
				dialog.show();
			}

		}

		public String sync(String token, String userName) {

			String result = "";
			String url = Constant.PATH_SYNC_DATA;
			Log.e("downloadAndSyn", "Start Download >>>>>>>>>>>>>>>> " + url);
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
			}finally{
				if(conn!=null){
					conn.disconnect();
				}

			}
			return result;
		}

	}
}
