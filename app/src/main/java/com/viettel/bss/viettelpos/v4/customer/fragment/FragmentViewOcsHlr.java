package com.viettel.bss.viettelpos.v4.customer.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.sale.object.SaleOutput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class FragmentViewOcsHlr extends FragmentCommon {
	private Activity activity;
	private EditText edtIsdn;
	private TextView tvOcs, tvHlr;

	@Override
	public void onResume() {
		super.onResume();
        setTitleActionBar(R.string.view_ocs_hlr);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		idLayout = R.layout.layout_view_ocs_hlr;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
	}

	@Override
	public void unit(View v) {
		edtIsdn = (EditText) v.findViewById(R.id.edtIsdn);
		// edtIsdn.setEnabled(true);

		tvOcs = (TextView) v.findViewById(R.id.edtOcs);
		tvOcs.setMovementMethod(new ScrollingMovementMethod());
		tvHlr = (TextView) v.findViewById(R.id.edtHlr);
		tvHlr.setMovementMethod(new ScrollingMovementMethod());
		v.findViewById(R.id.btnViewOH).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						int msg = validateSearch();
						if (msg != 0) {
							CommonActivity.createAlertDialog(activity, msg,
									R.string.app_name).show();
							return;
						}

						ViewHlrOcsAsy asy = new ViewHlrOcsAsy(activity);
						asy.execute();
					}
				});
		Bundle bundle = getArguments();
		if (bundle != null) {
			if (bundle.containsKey("isdnKey")) {
				// edtIsdn.setEnabled(false);
				edtIsdn.setText(bundle.getString("isdnKey"));
				ViewHlrOcsAsy asy = new ViewHlrOcsAsy(activity);
				asy.execute();
			}
		}
	}

	private class ViewHlrOcsAsy extends AsyncTask<String, Void, SaleOutput> {

		private final Context context;
		final ProgressDialog progress;

		public ViewHlrOcsAsy(Context context) {

			this.context = context;
			this.progress = new ProgressDialog(context);
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}

		}

		@Override
		protected SaleOutput doInBackground(String... params) {

			// if (rbNoiMang.isChecked()) {
			return viewHlrOcs();
			// } else {
			// return getListReasoncareNgoaiMang();
			// }
		}

		@Override
		protected void onPostExecute(SaleOutput result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (result.getErrorCode().equals("0")) {
				tvOcs.setText(result.getOcs());
				tvHlr.setText(result.getHlr());
			} else {
				tvOcs.setText("");
				tvHlr.setText("");
				if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
					CommonActivity.BackFromLogin((Activity) context,
							result.getDescription(), ";view_ocs_hlr;");
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(context,
							result.getDescription(),
							context.getString(R.string.app_name));
					dialog.show();

				}
			}

		}

		private SaleOutput viewHlrOcs() {

			String original = "";
			SaleOutput saleOutput;
			String methodName = "viewHlrOcs";
			try {

				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_" + methodName);
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:").append(methodName).append(">");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");

				rawData.append("<isdn>").append(CommonActivity.checkStardardIsdn(edtIsdn.getText()
                        .toString().trim())).append("</isdn>");

				rawData.append("<user>").append(Session.userName).append("</user>");
				rawData.append("</input>");
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
				saleOutput = serializer.read(SaleOutput.class, original);
				if (saleOutput == null) {
					saleOutput = new SaleOutput();
					saleOutput
							.setDescription(getString(R.string.no_return_from_system));
					saleOutput.setErrorCode(Constant.ERROR_CODE);
					return saleOutput;
				} else {
					return saleOutput;
				}
			} catch (Exception e) {
				Log.e(Constant.TAG, methodName, e);
				saleOutput = new SaleOutput();
				saleOutput.setDescription(e.getMessage());
				saleOutput.setErrorCode(Constant.ERROR_CODE);
			}

			return saleOutput;
		}
	}

	private int validateSearch() {
		if (CommonActivity.isNullOrEmpty(edtIsdn.getText().toString().trim())) {
			return R.string.must_input_isdn;
		}
		if (!CommonActivity.validateIsdn(edtIsdn.getText().toString().trim())) {
			return R.string.phone_number_invalid_format;
		}
		return 0;
	}

	@Override
	public void setPermission() {
		// TODO Auto-generated method stub

	}
}
