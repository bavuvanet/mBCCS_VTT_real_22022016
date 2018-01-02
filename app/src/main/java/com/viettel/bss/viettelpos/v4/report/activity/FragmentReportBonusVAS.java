package com.viettel.bss.viettelpos.v4.report.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customer.object.BOCOutput;
import com.viettel.bss.viettelpos.v4.dal.CacheDatabaseManager;
import com.viettel.bss.viettelpos.v4.report.adapter.AdapterReportBonusVas;
import com.viettel.bss.viettelpos.v4.report.object.BonusVasObject;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;

public class FragmentReportBonusVAS extends FragmentCommon {

	private Activity activity;
	private EditText edtSearch;
    private ListView lvItem;
	private AdapterReportBonusVas adapter;
	private ArrayList<BonusVasObject> lstData;

	@Override
	public void onAttach(Activity _activity) {
		super.onAttach(_activity);
		activity = _activity;
	}

    @Override
	public void onResume() {
		super.onResume();
        setTitleActionBar(R.string.report_bonus_vas);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		idLayout = R.layout.layout_report_bonus_vas;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void unit(View v) {
		edtSearch = (EditText) v.findViewById(R.id.edtSearch);
        View btnRefreshTable = v.findViewById(R.id.btnRefreshTable);
		lvItem = (ListView) v.findViewById(R.id.lvItem);
		GetServiceCodeList asy = new GetServiceCodeList(act);
		asy.execute();
		edtSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				if (adapter != null) {
					if (!CommonActivity.isNullOrEmpty(lstData)) {
						String text = arg0.toString().toLowerCase();
						ArrayList<BonusVasObject> lstTmp = new ArrayList<>();
						for (BonusVasObject vas : lstData) {
							if (vas.getVasCode().toLowerCase().contains(text)) {
								lstTmp.add(vas);
							}
						}
						adapter.setLstData(lstTmp);
						adapter.notifyDataSetChanged();
					}
				}

			}
		});

		btnRefreshTable.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new CacheDatabaseManager(activity).insertBonusVasCache(null);
				GetServiceCodeList asyn = new GetServiceCodeList(act);
				asyn.execute();
			}
		});

	}

	private class GetServiceCodeList extends AsyncTask<String, Void, BOCOutput> {
		final ProgressDialog progress;
		private final Activity mActivity;

		public GetServiceCodeList(Activity mActivity) {
			this.mActivity = mActivity;
			this.progress = new ProgressDialog(mActivity);

			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected BOCOutput doInBackground(String... params) {
			return getServiceCodeList();
		}

		@Override
		protected void onPostExecute(BOCOutput result) {
			super.onPostExecute(result);
			progress.dismiss();
			edtSearch.setText("");
			if ("0".equals(result.getErrorCode())) {
				lvItem.setVisibility(View.VISIBLE);
				lstData = result.getLstBonusVasObject();
				adapter = new AdapterReportBonusVas(mActivity, lstData);
				lvItem.setAdapter(adapter);
			} else {
				lvItem.setVisibility(View.INVISIBLE);
				if (Constant.INVALID_TOKEN2.equals(result.getErrorCode())) {
					Dialog dialog = CommonActivity
							.createAlertDialog(mActivity, result
									.getDescription(), mActivity.getResources()
									.getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), result.getDescription(),
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		private BOCOutput getServiceCodeList() {

			BOCOutput bocOutput = new BOCOutput();

			ArrayList<BonusVasObject> lst = new CacheDatabaseManager(mActivity)
					.getListBonusVasInCache();
			if (!CommonActivity.isNullOrEmpty(lst)) {
				bocOutput.setErrorCode("0");
				bocOutput.setLstBonusVasObject(lst);
				return bocOutput;
			}

			String original = "";
			try {
				String methodName = "getServiceCodeList";
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_" + methodName);
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:").append(methodName).append(">");
				rawData.append("<reportInput>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("</reportInput>");
				rawData.append("</ws:").append(methodName).append(">");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(), "mbccs_"
								+ methodName);
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", response);

				// parser
				Serializer serializer = new Persister();
				bocOutput = serializer.read(BOCOutput.class, original);
				if (bocOutput == null
						|| CommonActivity.isNullOrEmpty(bocOutput
								.getLstBonusVasObject())) {
					bocOutput = new BOCOutput();
					bocOutput
							.setDescription(getString(R.string.no_return_from_system));
					bocOutput.setErrorCode(Constant.ERROR_CODE);
					return bocOutput;
				} else {
					if (!CommonActivity.isNullOrEmpty(bocOutput
							.getLstBonusVasObject())) {
						new CacheDatabaseManager(mActivity)
								.insertBonusVasCache(bocOutput
										.getLstBonusVasObject());
					}
					return bocOutput;
				}
			} catch (Exception e) {
				Log.e(Constant.TAG, "getListReasoncare", e);
				bocOutput = new BOCOutput();
				bocOutput.setDescription(e.getMessage());
				bocOutput.setErrorCode(Constant.ERROR_CODE);
			}

			return bocOutput;

		}
	}

	@Override
	public void setPermission() {
		// TODO Auto-generated method stub
		
	}
}
