package com.viettel.bss.viettelpos.v3.connecttionService.activity;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetListInfoAccountAdapter;
import com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentSearchAccountChangeInfra extends Fragment implements OnClickListener {

	private View mView = null;
	private EditText editsogiayto, editisdnacount;
	private Button btnTimKiem;
	private ListView lvAccount;
	private GetListInfoAccountAdapter adapter = null;

	private List<SubscriberDTO> arrSubscriberDTO = new ArrayList<SubscriberDTO>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (mView == null) {
			mView = inflater.inflate(R.layout.connection_layout_subscriber_searchacc, container, false);
		}
		unitView(mView);
		return mView;
	}

	private void unitView(View v) {
		editsogiayto = (EditText) v.findViewById(R.id.editsogiayto);
		editisdnacount = (EditText) v.findViewById(R.id.editisdnacount);
		btnTimKiem = (Button) v.findViewById(R.id.btnTimKiem);
		btnTimKiem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (CommonActivity.isNullOrEmpty(editisdnacount.getText().toString().trim())) {
					CommonActivity
							.createAlertDialog(getActivity(), getActivity().getString(R.string.must_input_isdn_account),
									getActivity().getString(R.string.app_name))
							.show();
					return;
				}
				
				SearchSubscriberAsyn searchSubscriberAsyn = new SearchSubscriberAsyn(getActivity());
				if(!CommonActivity.isNullOrEmpty(editsogiayto.getText().toString())){
					searchSubscriberAsyn.execute(editsogiayto.getText().toString(),editisdnacount.getText().toString().trim());
				}else{
					searchSubscriberAsyn.execute("",editisdnacount.getText().toString().trim());
				}

			}
		});
		lvAccount = (ListView) v.findViewById(R.id.lvAccount);
	}

	@Override
	public void onResume() {
		super.onResume();
		addActionBar();
	}

	private void addActionBar() {
		ActionBar mActionBar = getActivity().getActionBar();
		assert mActionBar != null;
		mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		mActionBar.setCustomView(R.layout.layout_actionbar_channel);
		LinearLayout relaBackHome = (LinearLayout) mActionBar.getCustomView().findViewById(R.id.relaBackHome);
		relaBackHome.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				getActivity().onBackPressed();
			}
		});
		Button btnHome = (Button) mActionBar.getCustomView().findViewById(R.id.btnHome);
		btnHome.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				CommonActivity.callphone(getActivity(), Constant.phoneNumber);
			}
		});
		TextView txtNameActionBar = (TextView) mActionBar.getCustomView().findViewById(R.id.txtNameActionbar);
		TextView txtAddressActionBar = (TextView) mActionBar.getCustomView().findViewById(R.id.txtAddressActionbar);
		txtAddressActionBar.setVisibility(View.GONE);
		txtNameActionBar.setText(getResources().getString(R.string.manager_customer_connecttion_info));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnTimKiem:
			break;
		default:
			break;
		}
	}

	private class SearchSubscriberAsyn extends AsyncTask<String, Void, List<SubscriberDTO>> {

		private Context mContext;
		private String errorCode;
		private String description;
		private ProgressDialog progress;

		public SearchSubscriberAsyn(Context context) {
			this.mContext = context;
			this.progress = new ProgressDialog(this.mContext);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(R.string.getdatareport));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected List<SubscriberDTO> doInBackground(String... arg0) {
			return getListSubscriber(arg0[0], arg0[1]);
		}

		@Override
		protected void onPostExecute(List<SubscriberDTO> result) {
			super.onPostExecute(result);

			if ("0".equals(errorCode)) {
				if (!CommonActivity.isNullOrEmpty(result)) {

					arrSubscriberDTO = result;
					adapter = new GetListInfoAccountAdapter(arrSubscriberDTO, getActivity());
					lvAccount.setAdapter(adapter);

				} else {
					arrSubscriberDTO = new ArrayList<SubscriberDTO>();
					if (adapter != null) {
						adapter.notifyDataSetChanged();
					}
					CommonActivity.createAlertDialog(getActivity(), getActivity().getString(R.string.no_data),
							getActivity().getString(R.string.app_name)).show();
				}
			} else {
				arrSubscriberDTO = new ArrayList<SubscriberDTO>();
				if (adapter != null) {
					adapter.notifyDataSetChanged();
				}
				if (Constant.INVALID_TOKEN2.equals(errorCode) && description != null && !description.isEmpty()) {

					Dialog dialog = CommonActivity.createAlertDialog((Activity) mContext, description,
							mContext.getResources().getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = mContext.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		private List<SubscriberDTO> getListSubscriber(String idNo, String isdnAccount) {
			String original = null;
			List<SubscriberDTO> lstSubscriber = new ArrayList<SubscriberDTO>();
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_searchSubscriber");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:searchSubscriber>");
				rawData.append("<input>");
				rawData.append("<token>" + Session.getToken() + "</token>");
				rawData.append("<isdnAccount>" + isdnAccount);
				rawData.append("</isdnAccount>");

				rawData.append("<idNo>" + idNo);
				rawData.append("</idNo>");

				rawData.append("</input>");
				rawData.append("</ws:searchSubscriber>");
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_searchSubscriber");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				Log.d("originalllllllll", original);

				Serializer serializer = new Persister();
				ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
				if (parseOuput != null) {
					errorCode = parseOuput.getErrorCode();
					description = parseOuput.getDescription();
					lstSubscriber = parseOuput.getLstSubscriberDTO();
				}

				return lstSubscriber;
			} catch (Exception e) {
				Log.d("getListSubscriber", e.toString());
			}

			return null;
		}

	}

	OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
			getActivity().finish();
			MainActivity.getInstance().finish();

		}
	};

}
