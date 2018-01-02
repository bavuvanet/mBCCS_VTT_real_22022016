package com.viettel.bss.viettelpos.v4.channel.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.channel.adapter.ChannelCareAdapter;
import com.viettel.bss.viettelpos.v4.channel.dal.ChannelCareDAL;
import com.viettel.bss.viettelpos.v4.channel.dal.ChannelDAL;
import com.viettel.bss.viettelpos.v4.channel.object.ChannelContentCareOJ;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.handler.VSAMenuHandler;
import com.viettel.bss.viettelpos.v4.login.object.Staff;
import com.viettel.bss.viettelpos.v4.work.adapter.AdapterSalePoint.onMyCheckBoxChangeListenner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class FragmentChannelCare extends Fragment implements OnClickListener,
		OnItemClickListener, Define, OnScrollListener,
		onMyCheckBoxChangeListenner {
	private ProgressDialog dialogSendSMS;
    private ArrayList<ChannelContentCareOJ> arrdaAdapter;
    private View mView = null;
    private String errorMessage = "";
	private static final String TAG = "ChannelCare";
	private final String TABLE_NAME = "staff";
	private Staff staffx;
	private Boolean isNetWorkConnected = true;

	public static FragmentChannelCare create(String key) {
		Bundle args = new Bundle();
		args.putString(Define.ARG_KEY, key);
		args.putSerializable("staffIdKey", FragmentCusCareByDay.staff);
		args.putSerializable(Define.KEY_STAFF, FragmentChanelInfo.mStaff);
		FragmentChannelCare fragment = new FragmentChannelCare();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
	}

	private final OnClickListener channelCare = new OnClickListener() {

		@Override
		public void onClick(View v) {
			ArrayList<ChannelContentCareOJ> arrOutPut = new ArrayList<>();
			if (arrOutPut.size() > 0) {
				// arrOutPut.removeAll(arrOutPut);
				arrOutPut = new ArrayList<>();
			}

			if (arrdaAdapter != null && arrdaAdapter.size() > 0) {
				for (int i = 0; i < arrdaAdapter.size(); i++) {
					ChannelContentCareOJ channelContentCareOJ = arrdaAdapter
							.get(i);
					if (channelContentCareOJ.getChecked()) {
						arrOutPut.add(channelContentCareOJ);
					}
				}
			}

            String smsDialog = "";
            if (arrOutPut.size() > 0) {
				com.viettel.bss.viettelpos.v4.object.Location myLocation = CommonActivity
						.findMyLocationGPS(getActivity(), "channelCare");
//				myLocation.setX("10.123456");
//				myLocation.setY("105.123456");
				if (!myLocation.getX().equals("0") && !myLocation.getY().equals("0")) {
					Location location = new Location("");
					location.setLatitude(Double.parseDouble(myLocation.getX()));
					location.setLongitude(Double.parseDouble(myLocation.getY()));
					Bundle mBundle = getArguments();
					Staff mStaff = (Staff) mBundle.getSerializable(Define.KEY_STAFF);
					Location locationStaff = new Location("Location Staff");
					ChannelDAL mdDal = new ChannelDAL(getActivity());

					Staff staff = null;
					try {
						staff = mdDal.getStaffByStaffCode(Define.TABLE_NAME_STAFF, mStaff.getStaffCode(), location);
					} catch (Exception e) {
						// TODO: handle exception
					} finally {
						if (mdDal != null) {
							mdDal.close();
						}
					}

					// Set location cua diem ban
					if (mStaff.getX() != 0 && mStaff.getY() != 0) {
						locationStaff.setLatitude(staff.getX());
						locationStaff.setLongitude(staff.getY());

						if (isNetWorkConnected) {
							// truong hop co
							// mang
							new VisitChannelAsyncTask(getActivity(), mStaff, arrOutPut, location).execute();
						} else {
							// Truong hop khong
							// co mang
							dialogSendSMS = new ProgressDialog(getActivity());
							dialogSendSMS.setMessage(getResources().getString(R.string.waitting));
							dialogSendSMS.setCancelable(false);
							if (!dialogSendSMS.isShowing()) {
								dialogSendSMS.show();
							}
							try {
								CommonActivity.sendSMS(Constant.EXCHANGE_ADDRESS,
										buildXML(mStaff, arrOutPut, location, true), getActivity(), dialogSendSMS,
										"0CC");
							} catch (Exception e) {
								// TODO
								// Auto-generated
								// catch block
								e.printStackTrace();
							}
							new CountDownTimer(Constant.TIMEOUT_SMS, 1000) {
								public void onTick(long millisUntilFinished) {
								}

								public void onFinish() {
									if (dialogSendSMS.isShowing()) {
										dialogSendSMS.dismiss();
										Toast.makeText(getActivity(), getResources().getString(R.string.time_out_sms),
												Toast.LENGTH_LONG).show();
									}
								}
							}.start();
						}
					} else {
						smsDialog = getResources().getString(R.string.staff_have_not_locatation);
						String title = getString(R.string.app_name);
						Dialog dialog = CommonActivity.createAlertDialog(getActivity(), smsDialog, title);
						dialog.show();
					}
				} else {

					CommonActivity.DoNotLocation(getActivity());
					// if (CommonActivity.checkGps(getActivity())) {
					// CommonActivity.createAlertDialog(
					// getActivity(),
					// getActivity().getResources().getString(
					// R.string.checkgps),
					// getActivity().getResources().getString(
					// R.string.app_name)).show();
					// } else {
					// smsDialog = getResources().getString(
					// R.string.cannot_get_location);
					// String title = getString(R.string.app_name);
					// Dialog dialog = CommonActivity.createAlertDialog(
					// getActivity(), smsDialog, title);
					// dialog.show();
					// }
				}

			} else {
				smsDialog = getString(R.string.insertcontentCare);
				String title = getString(R.string.app_name);
				Dialog dialog = CommonActivity.createAlertDialog(getActivity(), smsDialog, title);
				dialog.show();
			}

		}

	};

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (mView == null) {
			mView = inflater.inflate(R.layout.channel_care, container, false);
			Unit(mView, savedInstanceState);
		}

		return mView;
	}

	private void Unit(View v, Bundle mBundle) {
		try {
            ListView lvListContentCare = (ListView) v.findViewById(R.id.lv_list_care);
			arrdaAdapter = new ArrayList<>();
            ChannelCareDAL mchannelCareDLA = new ChannelCareDAL(getActivity());
			arrdaAdapter = mchannelCareDLA.getListCareContent(0, 100);
			TextView sms11 = (TextView) v.findViewById(R.id.sms11);
			mchannelCareDLA.close();
			if (arrdaAdapter.size() > 0) {
                ChannelCareAdapter mchannelAdapter = new ChannelCareAdapter(arrdaAdapter, getActivity());
				mchannelAdapter.setOnMyCheckChange(this);
				sms11.setVisibility(View.GONE);
				lvListContentCare.setAdapter(mchannelAdapter);
			} else {
				sms11.setVisibility(View.VISIBLE);
			}

            Button btnUpdate = (Button) v.findViewById(R.id.btnChanelCare);
			btnUpdate.setOnClickListener(this);

			if (getActivity() instanceof FragmentCusCareByDay) {
				Log.d(TAG, "getActivity() instanceof FragmentCusCareByDay");
				v.findViewById(R.id.llHeaderCC)
						.setVisibility(View.VISIBLE);
				v.findViewById(R.id.tvHistoryCare)
						.setVisibility(View.GONE);
			} else {
				Log.d(TAG, "getActivity() dont instanceof FragmentCusCareByDay");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		if (getActivity() instanceof FragmentCusCareByDay) {
			Log.d(this.getClass().getSimpleName(),
					"getActivity() is instanceof FragmentCusCareByDay");
		} else {
			getActivity().registerReceiver(receiver,
					new IntentFilter(Constant.REGISTER_RECEIVER));
		}
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnChanelCare:
			isNetWorkConnected = CommonActivity.isNetworkConnected(getActivity());
			String messageConfirm = getResources().getString(R.string.ask_channel_care_update);
			String titleConfirm = getResources().getString(R.string.app_name);
			if (!isNetWorkConnected) {
				messageConfirm = getResources().getString(R.string.no_network_message);
				titleConfirm = getResources().getString(R.string.no_network_title);
			}
			Dialog dialog = CommonActivity.createDialog(getActivity(),

					messageConfirm,

					titleConfirm,

					getResources().getString(R.string.cancel), getResources().getString(R.string.ok),
					null, channelCare);

			dialog.show();

			break;

		default:
			break;
		}

	}

	public void onCheckboxClicked(View view) {
		// Is the view now checked?
		Log.i("TAG", "CLICK CHECK ");
		int checkId = view.getId();
		Log.i("TAG", "CLICK CHECK " + checkId);
		// Check which checkbox was clicked

	}

	private String updateVisit(Staff staff, ArrayList<ChannelContentCareOJ> arrChannelContent, Location myLocation) {
		// TODO Auto-generated method stub
		ChannelDAL channelDAL = new ChannelDAL(getActivity());
		try {
			String sms = null;
			// Long channelTypeId, Long objId, String address, Double x, Double
			// y,

			BCCSGateway input = new BCCSGateway();

			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_updateCare");

			String envelope = input.buildInputGatewayWithRawData(buildXML(staff, arrChannelContent, myLocation, false));
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(), "mbccs_updateCare");
			CommonOutput output = input.parseGWResponse(response);
			if (!output.getError().equals("0")) {
				errorMessage = output.getDescription();
				return Constant.ERROR_CODE;
			}

			String original = output.getOriginal();
			VSAMenuHandler handler = (VSAMenuHandler) CommonActivity.parseXMLHandler(new VSAMenuHandler(), original);
			output = handler.getItem();
			if (!output.getErrorCode().equals("0")) {
				errorMessage = output.getDescription();
				return output.getErrorCode();
			}
			if (output.getToken() != null && !output.getToken().isEmpty()) {
				Session.setToken(output.getToken());
			}

		} catch (Exception e) {

			return Constant.ERROR_CODE;

		} finally {
			if (channelDAL != null) {
				channelDAL.close();
			}
		}

		return Constant.SUCCESS_CODE;
	}

	private String buildXML(Staff staff, ArrayList<ChannelContentCareOJ> arrChannelContent, Location myLocation,
			Boolean isSMS) {
		ChannelDAL channelDAL = new ChannelDAL(getActivity());
		try {
			staffx = channelDAL.getStaffByStaffCode(TABLE_NAME, staff.getStaffCode(), myLocation);
		} finally {
			channelDAL.close();
		}

		String address = staff.getAddressStaff();
		Long objId = staffx.getStaffId();
		Long channelTypeId = staffx.getChannelTypeId();

		// gps = new GPSTracker(getActivity());
		// check if GPS enabled
		double x = 0; // x
		double y = 0;// y
		x = myLocation.getLatitude();
		y = myLocation.getLongitude();

		StringBuilder rawData = new StringBuilder();
		rawData.append("<ws:insertVisit>");
		rawData.append("<arrObject>");
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		HashMap<String, String> param = new HashMap<>();
		HashMap<String, String> paramToken = new HashMap<>();
		BCCSGateway input = new BCCSGateway();
		if (arrChannelContent.size() > 0) {
			for (ChannelContentCareOJ item : arrChannelContent) {
				HashMap<String, String> rawDataItem = new HashMap<>();
				rawDataItem.put("objId", objId.toString());
				rawDataItem.put("channelTypeId", channelTypeId.toString());
				rawDataItem.put("address", StringUtils.escapeHtml(address));
				rawDataItem.put("arrivalTime", date);
				rawDataItem.put("content", item.getContentCare());
				rawDataItem.put("status", "true");
				rawDataItem.put("x", Double.toString(x));
				rawDataItem.put("y", Double.toString(y));
				rawDataItem.put("taskId", Long.toString(1));
				rawDataItem.put("taskType", Long.toString(1));
				param.put("channelCareInput", input.buildXML(rawDataItem));
				rawData.append(input.buildXML(param));

			}
		}
		if (isSMS) {
			paramToken.put("token", "");
		} else {
			paramToken.put("token", Session.getToken());
		}
		rawData.append(input.buildXML(paramToken));
		rawData.append("</arrObject>");
		rawData.append("</ws:insertVisit>");
		return rawData.toString();
	}

	private class VisitChannelAsyncTask extends AsyncTask<String, String, String> {
		final ProgressDialog progress;
		private final Context context;
		private final Activity activity;
		private final Staff staff;
		private final Location myLocation;
		private final ArrayList<ChannelContentCareOJ> arrChannelContent;

		public VisitChannelAsyncTask(Context context, Staff staff, ArrayList<ChannelContentCareOJ> arrChannelContent,
				Location myLocation) {
			this.context = context;
			this.staff = staff;
			this.activity = (Activity) context;
			this.arrChannelContent = arrChannelContent;
			this.myLocation = myLocation;
			this.progress = new ProgressDialog(this.context);
			this.progress.setMessage(context.getResources().getString(R.string.waitting));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected String doInBackground(String... params) {
			return updateVisit(staff, arrChannelContent, myLocation);

		}

		@Override
		protected void onPostExecute(String result) {
			try {
				this.progress.dismiss();
				String title = context.getString(R.string.app_name);
				if (Constant.SUCCESS_CODE.equals(result)) {
					String message = context.getString(R.string.updatesucess);
					FragmentCusCareByDay.supportChannel = true;

					if (!(activity instanceof FragmentCusCareByDay)) {
						Bundle mBundle = getArguments();
						mBundle.putSerializable("FLAG", true);
						mBundle.putSerializable(Define.KEY_STAFF, staffx);

						FragmentChanelInfo mChanelInfo = new FragmentChanelInfo();
						mChanelInfo.setArguments(mBundle);
						ReplaceFragment.replaceFragment(getActivity(),
								mChanelInfo, false);
					} else {
						if (getActivity() instanceof FragmentCusCareByDay) {
							if (FragmentCusCareByDay.careCus != Constant.BOC2.STATUS_CARE_AND_SALE) { // neu trang thai khac ban hang thanh cong, cap nhat lai trang thai cham soc kenh
								FragmentCusCareByDay.careCus = Constant.BOC2.STATUS_CARE_NOT_SALE;
							}
						}
					}

					Dialog dialog11 = CommonActivity.createAlertDialog(
							activity, message, title);
					dialog11.show();

				} else if (result.equals(Constant.INVALID_TOKEN2)) {
					CommonActivity.BackFromLogin(activity, errorMessage,
							";channel.management;");

				} else {
					Dialog dialog11 = CommonActivity.createAlertDialog(activity, errorMessage, title);
					dialog11.show();

				}
			} catch (Exception e) {
				// TODO: handle exception
			}

		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Bundle mBundle = new Bundle();
		ChannelContentCareOJ channelCareOJ = arrdaAdapter.get(arg2);
		if (channelCareOJ.getChecked()) {
			channelCareOJ.setChecked(false);
		} else {
			channelCareOJ.setChecked(true);
		}

	}

	@Override
	public void onCheckChange(int position, boolean ischecked) {

		// ChannelContentCareOJ channelCareOJ = arrdaAdapter.get(position - 1);
		for (ChannelContentCareOJ channelCareOJ : arrdaAdapter) {
			if (channelCareOJ.getContent_care_id() == position) {
				if (channelCareOJ.getChecked()) {
					channelCareOJ.setChecked(false);
				} else {
					channelCareOJ.setChecked(true);
				}
			}

		}
	}

	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	private final BroadcastReceiver receiver = new BroadcastReceiver() {

		@SuppressLint("DefaultLocale")
		@Override
		public void onReceive(Context context, Intent intent) {
			try {
				String msg = intent.getStringExtra("msg_body");
				String[] result = msg.split(";", 5);

				if (result.length > 3) {
					Session.setToken(result[0]);
					String errorCode = result[3];
					String syntax = result[1];
					String message = getResources().getString(R.string.sale_fail);
					if (syntax.equalsIgnoreCase("0CC")) {
						if (errorCode.equals("0")) {
							String mess = context.getString(R.string.updatesucess);
							// String message = "";
							String title = context.getString(R.string.app_name);
							Dialog dialog = CommonActivity.createAlertDialog(getActivity(), mess, title,
									onclickmoveFragmentchanl);
							dialog.show();
							// new AlertDialog.Builder(context)
							// .setTitle(title)
							// .setMessage(msg)
							// .setCancelable(false)
							// .setNegativeButton(
							// getResources().getString(
							// R.string.alert_dialog_ok),
							// null).show();
							//
							// Bundle mBundle = getArguments();
							//
							// Staff mStaff = (Staff) mBundle
							// .getSerializable(Define.KEY_STAFF);
							// mBundle.putSerializable("FLAG", true);
							// mBundle.putSerializable(Define.KEY_STAFF,
							// staffx);
							//
							// FragmentChanelInfo mChanelInfo = new
							// FragmentChanelInfo();
							// mChanelInfo.setArguments(mBundle);
							// ReplaceFragment.replaceFragment(getActivity(),
							// mChanelInfo, false);
						} else {
							OnClickListener onclick = null;
							if (errorCode.equalsIgnoreCase(Constant.INVALID_TOKEN)) {
								onclick = new OnClickListener() {

									@Override
									public void onClick(View v) {
										Intent intent = new Intent(getActivity(), LoginActivity.class);
										intent.addFlags(
												Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
										startActivity(intent);
										getActivity().finish();
									}
								};
							}
							if (result[4] != null && !result[4].isEmpty()) {
								message = result[4];
							}
							CommonActivity.createAlertDialog(getActivity(), message,
									getResources().getString(R.string.app_name), onclick).show();
						}
					}
					if (dialogSendSMS != null && dialogSendSMS.isShowing()) {
						dialogSendSMS.dismiss();
					}
				}

			} catch (Exception e) {
				if (dialogSendSMS != null && dialogSendSMS.isShowing()) {
					dialogSendSMS.dismiss();
				}
			}

		}
	};
	private final OnClickListener onclickmoveFragmentchanl = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Bundle mBundle = getArguments();

			Staff mStaff = (Staff) mBundle.getSerializable(Define.KEY_STAFF);
			mBundle.putSerializable("FLAG", true);
			mBundle.putSerializable(Define.KEY_STAFF, staffx);

			FragmentChanelInfo mChanelInfo = new FragmentChanelInfo();
			mChanelInfo.setArguments(mBundle);
			ReplaceFragment.replaceFragment(getActivity(), mChanelInfo, false);
		}
	};
}
