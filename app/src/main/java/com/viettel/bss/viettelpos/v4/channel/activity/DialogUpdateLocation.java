package com.viettel.bss.viettelpos.v4.channel.activity;

import java.util.List;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.channel.dal.ChannelDAL;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.handler.VSAMenuHandler;
import com.viettel.bss.viettelpos.v4.login.object.Staff;
import com.viettel.bss.viettelpos.v4.sale.business.StaffBusiness;
import com.viettel.bss.viettelpos.v4.synchronizationdata.GetMaxOraRowScnDal;

import java.util.ArrayList;
import java.util.HashMap;

public class DialogUpdateLocation extends android.support.v4.app.DialogFragment implements
		View.OnClickListener {
	private Button acceptButton;
	private Button cancelButton;
    public Long staffId;
	private String errorMessage = "";
	private String TAG = "Dialog confirm";
	private com.viettel.bss.viettelpos.v4.object.Location myLocation;
    private Staff mStaff = null;

	public interface Callback {
		void accept();

		void cancel();
	}

	public DialogUpdateLocation() {
		// Empty constructor required for DialogFragment
	}

	private void updateLocationExcute() {
		// check if GPS enabled

		// if (gps.canGetLocation()) {
		// Location myLocation =
		// !myLocation.getX().equals("0")!myLocation.getX().equals("0")!myLocation.getX().equals("0")!myLocation.getX().equals("0")!myLocation.getX().equals("0")!myLocation.getX().equals("0")!myLocation.getX().equals("0")gps.getLocation();
		// myLocation.setX("20.1112");
		// myLocation.setY("105.009233");

		// if (!myLocation.getX().equals("0") && !myLocation.getY().equals("0"))
		// {
		// double latitude = gps.getLatitude(); // x
		// double longitude = gps.getLongitude();// y
		android.location.Location anrLocation = new android.location.Location(
				"");
		anrLocation.setLatitude(Double.parseDouble(myLocation.getX()));
		anrLocation.setLongitude(Double.parseDouble(myLocation.getY()));
		new UpdateLocationAsyncTask(getActivity(),
				Double.parseDouble(myLocation.getX()),
				Double.parseDouble(myLocation.getY()), mStaff.getStaffCode(),
				anrLocation).execute();
		// } else {
		// CommonActivity.DoNotLocation(getActivity());
		// }

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setCancelable(false);
		View view = inflater.inflate(R.layout.common_dialog, container);
		Bundle mBundle = getArguments();
		mStaff = (Staff) mBundle.getSerializable(Define.KEY_STAFF);

		if (mStaff != null) {
			Log.i("Tag", "Thong tin kenh" + mStaff.getStaffId());
		}

		myLocation = CommonActivity.findMyLocationGPS(
				MainActivity.getInstance(), "updateLocation");

        TextView tvDialogContent = (TextView) view.findViewById(R.id.tvDialogContent);

		List<com.viettel.bss.viettelpos.v4.sale.object.Staff> lstStaff = StaffBusiness
				.getStaffByStaffLocation(getActivity(), myLocation.getX(),
						myLocation.getY());

		String msg = getString(R.string.alert_dialog_two_buttons_title);

		if (!CommonActivity.isNullOrEmpty(lstStaff)) {
			String duplicate = "";
			for (com.viettel.bss.viettelpos.v4.sale.object.Staff staff : lstStaff) {
				duplicate = duplicate + staff.getStaffCode() + ";";
			}
			duplicate = duplicate.substring(0, duplicate.length() - 1);
			msg = getString(R.string.luu_y_trung_toa_do, mStaff.getStaffCode(),
					duplicate) + "\n" + getString(R.string.continue_confirm);
			// msg = getString(R.string.luu_y_trung_toa_do);

		}
		tvDialogContent.setText(msg);
		acceptButton = (Button) view.findViewById(R.id.btnLeft);
        TextView tvDialogTitle = (TextView) view.findViewById(R.id.tvDialogTitle);
		cancelButton = (Button) view.findViewById(R.id.btnRight);
		acceptButton.setText(MainActivity.getInstance().getResources()
				.getString(R.string.ok));
		cancelButton.setText(MainActivity.getInstance().getResources()
				.getString(R.string.cancel));
		acceptButton.setOnClickListener(this);
		cancelButton.setOnClickListener(this);
		tvDialogTitle.setVisibility(View.GONE);
		getDialog().setTitle(R.string.app_name);
		return view;
	}

	private class UpdateLocationAsyncTask extends
			AsyncTask<String, String, String> {
		final ProgressDialog progress;
		private final Context context;
		private final Double x;
		private final Activity activity;
		private final Double y;
		private final String staffCode;

		public UpdateLocationAsyncTask(Context context, Double x, Double y,
				String staffCode, Location myLocation) {
			this.context = context;
			this.activity = (Activity) context;
			this.x = x;
			this.y = y;
			this.staffCode = staffCode;
			this.progress = new ProgressDialog(this.context);
			this.progress.setMessage(context.getResources().getString(
					R.string.waitting));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		public String updateLocation(double x, double y, String staffCode,
				Location myLocation) {
			try {

				BCCSGateway input = new BCCSGateway();

				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_updateLocation");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:updateLocation>");
				rawData.append("<inputTypeUpdate>");
				HashMap<String, String> param = new HashMap<>();
				param.put("token", Session.getToken());
				param.put("x", Double.toString(x));
				param.put("y", Double.toString(y));
				param.put("staffCode", staffCode);

				rawData.append(input.buildXML(param));
				rawData.append("</inputTypeUpdate>");
				rawData.append("</ws:updateLocation>");

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.e(DialogUpdateLocation.class.getName(), "evelope: "
						+ envelope);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, MainActivity.getInstance(),
						"mbccs_updateLocation");
				Log.e(DialogUpdateLocation.class.getName(), "response: "
						+ response);
				CommonOutput output = input.parseGWResponse(response);
				if (!output.getError().equals("0")) {
					errorMessage = output.getDescription();
					return Constant.ERROR_CODE;
				}
				String original = output.getOriginal();
				VSAMenuHandler handler = (VSAMenuHandler) CommonActivity
						.parseXMLHandler(new VSAMenuHandler(), original);
				output = handler.getItem();
				if (output.getToken() != null && !output.getToken().isEmpty()) {
					Session.setToken(output.getToken());
				}
				if (!output.getErrorCode().equals("0")) {
					errorMessage = output.getDescription();
					return output.getErrorCode();
				}
				mStaff.setX(x);
				mStaff.setY(y);
			} catch (Exception e) {
				try {
					errorMessage = MainActivity.getInstance().getResources()
							.getString(R.string.exception)
							+ " " + e.toString();
				} catch (Exception e2) {
					// TODO: handle exception
				}

				return Constant.ERROR_CODE;
			}

			return Constant.SUCCESS_CODE;

		}

		@Override
		protected String doInBackground(String... params) {
			android.location.Location anrLocation = new android.location.Location(
					"");
			anrLocation.setLatitude(Double.parseDouble(myLocation.getX()));
			anrLocation.setLongitude(Double.parseDouble(myLocation.getY()));
			return updateLocation(x, y, staffCode, anrLocation);
		}

		@Override
		protected void onPostExecute(String result) {
			this.progress.dismiss();
			if (Constant.SUCCESS_CODE.equals(result)) {

				String message = context
						.getString(R.string.updatelocationsuccess)
						+ "\n Lat = " + x + "\n Log = " + y;
				String title = context.getString(R.string.app_name);

				Dialog dialog11 = CommonActivity.createAlertDialog(activity,
						message, title);
				dialog11.show();

				ArrayList<String> arrTable = new ArrayList<>();
				arrTable.add("staff");
				// ==update status table
				GetMaxOraRowScnDal getMaxOraRowScnDal = new GetMaxOraRowScnDal(
						context);
				ChannelDAL mChannelDAL = null;
				try {
					getMaxOraRowScnDal.open();
					getMaxOraRowScnDal.updateSyncStatus(arrTable);
					getMaxOraRowScnDal.close();
					mChannelDAL = new ChannelDAL(context);
					mChannelDAL.open();
					mChannelDAL.updateLocation("staff", x.toString(),
							y.toString(), staffCode);

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (mChannelDAL != null) {
						try {
							mChannelDAL.close();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				dismiss();
				Bundle mBundle = getArguments();
				if (mStaff != null) {
					mBundle.putSerializable(Define.KEY_STAFF, mStaff);
				}

			} else if (result.equals(Constant.INVALID_TOKEN2)) {
				CommonActivity.BackFromLogin(activity, errorMessage,
						Constant.ChannelMenu.CHANNEL_LOCATION);
			} else {
				if (errorMessage == null || errorMessage.isEmpty()) {
					errorMessage = context.getString(R.string.updatefail);
				}
				String title = context.getString(R.string.app_name);
				Dialog dialog11 = CommonActivity.createAlertDialog(activity,
						errorMessage, title);
				dialog11.show();
			}
		}

	}

	@Override
	public void onClick(View v) {
		if (v == acceptButton) {

			updateLocationExcute();
			this.dismiss();
		} else if (v == cancelButton) {
			this.dismiss();
		}

	}
}