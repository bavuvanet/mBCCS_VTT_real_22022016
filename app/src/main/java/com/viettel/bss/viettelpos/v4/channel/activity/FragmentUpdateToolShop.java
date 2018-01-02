package com.viettel.bss.viettelpos.v4.channel.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.channel.adapter.UpdateToolShopAdapter;
import com.viettel.bss.viettelpos.v4.channel.adapter.UpdateToolShopAdapter.onEdittextListenner;
import com.viettel.bss.viettelpos.v4.channel.dal.OjectCatDAL;
import com.viettel.bss.viettelpos.v4.channel.object.ObjectCatOJ;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.handler.VSAMenuHandler;
import com.viettel.bss.viettelpos.v4.login.object.Staff;

public class FragmentUpdateToolShop extends Fragment implements
		OnClickListener, OnItemClickListener, Define, OnScrollListener,
		onEdittextListenner {

    private ArrayList<ObjectCatOJ> arrList;
	private UpdateToolShopAdapter mAdapter;
	private View mView = null;
    private String errorMessage = "ERROR";

	private int position;

	private final OnClickListener updateToolShopClick = new OnClickListener() {
		String sms = "";
		final ArrayList<ObjectCatOJ> arrToolShop = new ArrayList<>();

		public int checkForSubmit() {
			int i = 0;
			if (arrToolShop != null && arrToolShop.size() > 0) {
				arrToolShop.clear();
			}
			Log.e("cap nhat", "cap nhat vat pham 1 = " + arrToolShop.size());
			for (ObjectCatOJ item : arrList) {
				if (item.getNote() != null && !item.getNote().trim().equals("")) {
					Log.e("ABC", item.getNote() + " ======= " + item.getNote());
					if (item.getQyt() == 0) {
						sms = getString(R.string.confirmInserValueToolShop)
								+ " '" + item.getValue() + "'";
						return i;
					} else {
						arrToolShop.add(item);
					}
				} else if (item.getQyt() > 0) {
					if (item.getNote() != null
							&& !item.getNote().trim().equals("")) {
						arrToolShop.add(item);
					} else {
						sms = getString(R.string.confirmInserContentToolShop)
								+ " '" + item.getValue() + "'";
						return i;
					}
				}
			}
			return -1;
		}

		@Override
		public void onClick(View v) {

			/*
			 * for (ObjectCatOJ object : arrList) { if (object.getQyt() > 0 ||
			 * object.get) { arrToolShop.add(object); } }
			 */

			if (checkForSubmit() == -1) {
				if (arrToolShop.size() > 0) {
					Bundle mBundle = getArguments();
					Staff mStaff = (Staff) mBundle
							.getSerializable(Define.KEY_STAFF);

					com.viettel.bss.viettelpos.v4.object.Location myLocation = CommonActivity
							.findMyLocation(getActivity());
					Location location = new Location("");
					location.setLatitude(Double.parseDouble(myLocation.getX()));
					location.setLongitude(Double.parseDouble(myLocation.getY()));
					if (CommonActivity.isNetworkConnected(getActivity())) {
						new UpdateToolShopAsyncTask(getActivity(), mStaff,
								arrToolShop, location).execute();
					} else {
						String title = getString(R.string.app_name);
						Dialog dialog11 = CommonActivity
								.createAlertDialog(
										getActivity(),
										getResources().getString(
												R.string.errorNetwork), title);
						dialog11.show();
					}
				} else {
					String title = getString(R.string.app_name);
					sms = getString(R.string.confirmInserValue);
					Dialog dialog1 = CommonActivity.createAlertDialog(
							getActivity(), sms, title);
					dialog1.show();
				}

			} else {
				String title = getString(R.string.app_name);
				Dialog dialog1 = CommonActivity.createAlertDialog(
						getActivity(), sms, title);
				dialog1.show();
			}
		}

	};

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub

		super.onResume();
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
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		if (mView == null) {
			mView = inflater.inflate(R.layout.layout_tool_shop_update,
					container, false);
			Unit(mView, savedInstanceState);
		}

		return mView;
	}

	private void Unit(View v, Bundle mBunder) {

        ListView lvListToolShop = (ListView) v
                .findViewById(R.id.lv_list_tool_shop_update);
        Button btnSaveToolShop = (Button) v.findViewById(R.id.btnSaveToolShop);
		arrList = new ArrayList<>();
        OjectCatDAL mDal = new OjectCatDAL(getActivity());
		arrList = mDal.getListObjectCat();
		mAdapter = new UpdateToolShopAdapter(arrList, getActivity());
		lvListToolShop.setAdapter(mAdapter);
		lvListToolShop.setOnItemClickListener(this);
		btnSaveToolShop.setOnClickListener(this);
		try {
			mDal.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

	}

	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		position = arg2;
		DialogUpdateChannel dialogUpdateChannel = new DialogUpdateChannel(
				getActivity());
		dialogUpdateChannel.show();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnSaveToolShop:
			Log.e("cap nhat", "cap nhat vat pham");
			Dialog dialog = CommonActivity.createDialog(getActivity(),

			getResources()

			.getString(R.string.updateConfirm),

			getResources().getString(R.string.app_name),

			getResources().getString(R.string.ok), getResources()

			.getString(R.string.cancel), updateToolShopClick, null);

			dialog.show();
			break;

		default:
			break;
		}
	}

	@Override
	public void onChangeEdittext(int positionTask, String edittext,
			String edtContent) {
		long number = 0;
		try {
			number = Long.parseLong(edittext);
		} catch (NumberFormatException ignored) {
		}
		arrList.get(positionTask).setQyt(number);
		arrList.get(positionTask).setNote(edtContent);
		Log.d("TAG", arrList.get(positionTask).getQyt() + "");
		Log.d("TAG", arrList.get(positionTask).getNote());
	}

	private class UpdateToolShopAsyncTask extends
			AsyncTask<String, String, String> {
		final ProgressDialog progress;
		private final Context context;
		private final Staff staff;
		private final ArrayList<ObjectCatOJ> arrToolShop;
		private final Location myLocation;

		public UpdateToolShopAsyncTask(Context context, Staff staff,
				ArrayList<ObjectCatOJ> arrToolShop, Location myLocation) {
			this.context = context;
			this.staff = staff;
			this.arrToolShop = arrToolShop;
			this.myLocation = myLocation;
			this.progress = new ProgressDialog(this.context);
			this.progress.setMessage(context.getResources().getString(
					R.string.waitting));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		public String addToolShop(Staff staff,
				ArrayList<ObjectCatOJ> arrObject, Location myLocation) {

			try {

				Long channelTypeId = staff.getChannelTypeId();
				Double x = 0.0;
				Double y = 0.0;
				if (myLocation != null) {
					x = myLocation.getLatitude();
					y = myLocation.getLongitude();
				}

				BCCSGateway input = new BCCSGateway();

				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_addToolShop");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:addToolShop>");
				rawData.append("<toolShopArr>");
				HashMap<String, String> param = new HashMap<>();
				HashMap<String, String> paramToken = new HashMap<>();

				if (arrObject.size() > 0) {
					for (ObjectCatOJ item : arrObject) {
						HashMap<String, String> rawDataItem = new HashMap<>();

						rawDataItem.put("assetCode", item.getCode());
						rawDataItem.put("channelTypeId",
								"" + channelTypeId.toString());
						rawDataItem.put("note",
								StringUtils.xmlEscapeText(item.getNote()));
						rawDataItem.put("assetId", item.getId().toString());
						rawDataItem.put("channelCode", staff.getStaffCode());
						rawDataItem.put("status", "1");
						rawDataItem.put("staffShopId", staff.getStaffId()
								.toString());
						if (myLocation != null) {
							rawDataItem.put("x", Double.toString(x));
							rawDataItem.put("y", Double.toString(y));
						}
						rawDataItem.put("qty", Long.toString(item.getQyt()));
						rawDataItem.put("userInput", Session.userName);

						param.put("toolShopInput", input.buildXML(rawDataItem));
						rawData.append(input.buildXML(param));

					}
				}

				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("</toolShopArr>");
				rawData.append("</ws:addToolShop>");

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				String response = input
						.sendRequest(envelope, Constant.BCCS_GW_URL,
								getActivity(), "mbccs_addToolShop");
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

			} catch (Exception e) {
				e.printStackTrace();
				errorMessage = getResources().getString(R.string.exception)
						+ e.toString();
				return Constant.ERROR_CODE;
			}
			return Constant.SUCCESS_CODE;

		}

		@Override
		protected String doInBackground(String... params) {
			return addToolShop(staff, arrToolShop, myLocation);

		}

		@Override
		protected void onPostExecute(String result) {
			this.progress.dismiss();
			String title = "";

			// syn data

			if (Constant.SUCCESS_CODE.equals(result)) {
				String message = context.getString(R.string.updatesucess);
				title = context.getString(R.string.app_name);

				Dialog dialog11 = CommonActivity.createAlertDialog(
						getActivity(), message, title);
				dialog11.show();
				getActivity().onBackPressed();

			} else if (result.equals(Constant.INVALID_TOKEN2)) {
				CommonActivity.BackFromLogin(getActivity(), errorMessage, ";channel.management;");

			} else {
				title = getString(R.string.app_name);
				Dialog dialog12 = CommonActivity.createAlertDialog(
						getActivity(), errorMessage, title);
				dialog12.show();
			}
		}

	}

	private class DialogUpdateChannel extends Dialog implements
			android.view.View.OnClickListener {

		private EditText edtNumberdialog, edtContentdialog;
		private Button btnUpdateDilogChannel;

		public DialogUpdateChannel(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
			setContentView(R.layout.dialog_channel_update);
			edtNumberdialog = (EditText) findViewById(R.id.edt_updateNumberDialog);
			edtContentdialog = (EditText) findViewById(R.id.edt_updateContentDialog);
			btnUpdateDilogChannel = (Button) findViewById(R.id.btnUpdate_Dialog_Channel);
			btnUpdateDilogChannel.setOnClickListener(this);
			if (arrList.get(position).getQyt() > 0) {
				edtNumberdialog.setText(arrList.get(position).getQyt() + "");
			} else {
				edtNumberdialog.setHint(getResources().getString(
						R.string.enterNumberToolshop));
			}
			if (arrList.get(position).getNote() == null
					|| arrList.get(position).getNote().equals("")) {
				edtContentdialog.setHint(getResources().getString(
						R.string.enterDesc));
			} else {
				edtContentdialog.setText(arrList.get(position).getNote());
			}
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnUpdate_Dialog_Channel:
				String num = edtNumberdialog.getText().toString().trim();

				String content = edtContentdialog.getText().toString().trim();
				if (content != null && !content.trim().equals("")) {
					arrList.get(position).setNote(content);
				}
				try {

					if (num != null && !num.trim().equals("")) {
						if (Long.parseLong(num) > 0) {
							arrList.get(position).setQyt(Long.parseLong(num));
						}

					}

				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
				mAdapter.notifyDataSetChanged();
				dismiss();
				break;

			default:
				break;
			}

		}

	}

}
