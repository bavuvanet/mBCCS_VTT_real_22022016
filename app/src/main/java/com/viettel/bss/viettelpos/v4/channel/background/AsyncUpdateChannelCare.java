package com.viettel.bss.viettelpos.v4.channel.background;

import android.app.Dialog;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.channel.activity.FragmentChannelCare;
import com.viettel.bss.viettelpos.v4.channel.dal.ChannelDAL;
import com.viettel.bss.viettelpos.v4.channel.object.ChannelContentCareOJ;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.handler.VSAMenuHandler;
import com.viettel.bss.viettelpos.v4.login.object.Staff;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

class AsyncUpdateChannelCare extends AsyncTask<String, String, String> {
	private final Context context;
	private final Staff staff;
	private final Location myLocation;
	private final ArrayList<ChannelContentCareOJ> arrChannelContent;

	public AsyncUpdateChannelCare(Context context, Staff staff,
			ArrayList<ChannelContentCareOJ> arrChannelContent,
			Location myLocation) {
		this.context = context;
		this.staff = staff;
		this.arrChannelContent = arrChannelContent;
		this.myLocation = myLocation;

	}

	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		return updateVisit(staff, arrChannelContent, myLocation);
	}

	private final String tag = "async update ch care";

	@Override
	protected void onPostExecute(String result) {
		// this.progress.dismiss();
		Log.e(tag, result + "----------result");
		String title = "";
		if (context != null) {
			title = context.getString(R.string.app_name);
		} else {

		}
		try {
			if (Constant.SUCCESS_CODE.equals(result)) {
				String message = context.getString(R.string.updatesucess);

				Toast.makeText(context, message, 1).show();

			} else if (result.equals(Constant.INVALID_TOKEN2)) {
				CommonActivity.BackFromLogin(MainActivity.getInstance(),
						errorMessage, ";channel.management;");

			} else {
				Dialog dialog11 = CommonActivity.createAlertDialog(
						MainActivity.getInstance(), errorMessage, title);
				if (dialog11 != null) {
					dialog11.show();
				} else {
					// Log.e("tagxxx", (activity == null) + "----act null");
					// Log.e("tagxxx", (errorMessage == null) +
					// "----msg null");
					Toast.makeText(MainActivity.getInstance(), errorMessage, 1)
							.show();
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

    }

	private boolean checkFragmentOnTop() {
		// TODO Auto-generated method stub

		FragmentManager manager = MainActivity.getInstance().getSupportFragmentManager();
		FragmentChannelCare fragmentChannelCare = (FragmentChannelCare) manager
				.findFragmentByTag("FragmentChannelCare");
		return fragmentChannelCare.isVisible();

	}

	private final String TABLE_NAME = "staff";
	private String errorMessage;

	private String buildXML(Staff staff,
			ArrayList<ChannelContentCareOJ> arrChannelContent,
			Location myLocation, Boolean isSMS) {
		ChannelDAL channelDAL = new ChannelDAL(context);
		Log.e(tag, (staff == null) + ".........staff null");
		Log.e(tag, (channelDAL == null) + ".........channel dal null");
		Log.e(tag, (myLocation == null) + ".........mloca null");
		Staff staffx = channelDAL.getStaffByStaffCode(TABLE_NAME,
				staff.getStaffCode(), myLocation);
		String address = staff.getAddressStaff();
		Long objId = staffx.getStaffId();
		Long channelTypeId = staffx.getChannelTypeId();
		channelDAL.close();
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
				rawDataItem.put("address", address);
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

	private String updateVisit(Staff staff,
                               ArrayList<ChannelContentCareOJ> arrChannelContent,
                               Location myLocation) {
		// TODO Auto-generated method stub
		ChannelDAL channelDAL = new ChannelDAL(context);
		try {
			String sms = null;
			// Long channelTypeId, Long objId, String address, Double x, Double
			// y,

			BCCSGateway input = new BCCSGateway();
			Log.e(tag, "fuck");
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_updateCare");

			String envelope = input.buildInputGatewayWithRawData(buildXML(
					staff, arrChannelContent, myLocation, false));
			Log.e(tag, envelope + "--------------envelop");
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
					context, "mbccs_updateCare");
			Log.e(tag, response + "--------------response");
			CommonOutput output = input.parseGWResponse(response);
			if (!output.getError().equals("0")) {
				errorMessage = output.getDescription();
				return Constant.ERROR_CODE;
			}

			String original = output.getOriginal();
			VSAMenuHandler handler = (VSAMenuHandler) CommonActivity
					.parseXMLHandler(new VSAMenuHandler(), original);
			output = handler.getItem();
			if (!output.getErrorCode().equals("0")) {
				errorMessage = output.getDescription();
				return output.getErrorCode();
			}
			if (output.getToken() != null && !output.getToken().isEmpty()) {
				Session.setToken(output.getToken());
			}
			channelDAL.close();
			channelDAL.close();

		} catch (Exception e) {
			e.printStackTrace();
			return Constant.ERROR_CODE;

		}

		return Constant.SUCCESS_CODE;
	}

}
