package com.viettel.bss.viettelpos.v4.channel.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.channel.adapter.DetailPaymentAccountAdapter;
import com.viettel.bss.viettelpos.v4.channel.object.DetailAccountPaymentOJ;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CheckConnectionInfo;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.handler.ListObjectHandler;
import com.viettel.bss.viettelpos.v4.handler.VSAMenuHandler;
import com.viettel.bss.viettelpos.v4.login.object.Staff;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import java.util.ArrayList;

public class FragmentDetailPaymentAccount extends Fragment {
	private TextView tvRemain;
	private TextView tvPaymentDC;
	private TextView tvPaymentSubmit;
	private ListView lvListProduct;
    private View mView;
	private ArrayList<DetailAccountPaymentOJ> mListObject;

	@Override
	public View getView() {
		// TODO Auto-generated method stub
		return super.getView();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (mView == null) {
			mView = inflater.inflate(
					R.layout.layout_view_detail_account_payment, container,
					false);
			Unit(mView, savedInstanceState);
			// getViewToolShopAsyncTask = new GetViewToolShopAsyncTask();
			// getViewToolShopAsyncTask.execute();
		}
		return mView;

	}

	private void Unit(View mview, Bundle bundle) {

		mListObject = new ArrayList<>();

		lvListProduct = (ListView) mview.findViewById(R.id.lvListProduct);

		Bundle mBundle;
		mBundle = getArguments();
		Staff mStaff = (Staff) mBundle.getSerializable(Define.KEY_STAFF);
		Long staffTypeId = mStaff.getChannelTypeId();
		String staffCode = mStaff.getStaffCode();
		tvPaymentDC = (TextView) mview.findViewById(R.id.tvPaymentDC);
		tvRemain = (TextView) mview.findViewById(R.id.tvRemain);
		tvPaymentSubmit = (TextView) mview.findViewById(R.id.tvPaymentSubmit);
		CheckConnectionInfo mInfo = new CheckConnectionInfo(getActivity());
		if (CommonActivity.isNetworkConnected(getActivity())) {
			new GetMoneyRemainAsynctask(getActivity()).execute(
					mStaff.getStaffCode(), "2");
		} else {
			String title = getString(R.string.app_name);
			Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
					getResources().getString(R.string.errorNetwork), title);
			dialog.show();
		}

	}

	private class GetMoneyRemainAsynctask extends
			AsyncTask<String, Void, String> {
		final BCCSGateway input = new BCCSGateway();
		String response = null;
		String original = null;
		String sms = "";
		final ProgressDialog progress;
		final XmlDomParse parse = new XmlDomParse();
		final Activity activity;

		private GetMoneyRemainAsynctask(Activity _activity) {
			this.activity = _activity;
			this.progress = new ProgressDialog(getActivity());
			this.progress.setMessage(getActivity().getResources().getString(
					R.string.waitting));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected String doInBackground(String... arg0) {
			String xml = createXMLgetMoneyRemain(arg0[0], arg0[1]);

			Log.d("xml", xml);
			CommonOutput output = null;
			try {
				String envelope = input.buildInputGatewayWithRawData2(
						xml.toString(), "mbccs_viewPaymentAccount");
				Log.e("TAG", "envelope" + envelope);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_viewPaymentAccount");

				output = input.parseGWResponse(response);
				original = output.getOriginal();

				if (!output.getError().equals("0")) {
					sms = output.getDescription();
					return Constant.ERROR_CODE;
				}

				String original = output.getOriginal();
				VSAMenuHandler handler = (VSAMenuHandler) CommonActivity
						.parseXMLHandler(new VSAMenuHandler(), original);
				output = handler.getItem();

				if (!output.getErrorCode().equals("0")) {
					sms = output.getDescription();
					return output.getErrorCode();
				}

			} catch (Exception e) {
				e.printStackTrace();
				sms = getResources().getString(R.string.exception)
						+ e.toString();
				return Constant.ERROR_CODE;
			}// get data

			return original;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {

                switch (result) {
                    case Constant.INVALID_TOKEN2:
                        CommonActivity.BackFromLogin(getActivity(), sms,
                                ";channel.management;");
                        break;
                    case Constant.ERROR_CODE:
                        String title = getString(R.string.app_name);
                        Dialog dialog = CommonActivity.createAlertDialog(
                                getActivity(), sms, title);
                        dialog.show();
                        break;
                    default:
                        try {
                            try {
                                ListObjectHandler handler = (ListObjectHandler) CommonActivity
                                        .parseXMLHandler(new ListObjectHandler(),
                                                result);

                                mListObject = handler.getmListObject();

                                String paymentAccountTotal = handler
                                        .getPaymentAccountTotal();
                                String totalDeposit = handler.getTotalDeposit();
                                String totalMoneySubmit = handler
                                        .getTotalMoneySubmit();

                                tvRemain.setText(StringUtils
                                        .formatMoney(paymentAccountTotal));
                                tvPaymentDC.setText(StringUtils
                                        .formatMoney(totalDeposit));
                                tvPaymentSubmit.setText(StringUtils
                                        .formatMoney(totalMoneySubmit));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            // Document doc = parse.getDomElement(result);
                            // NodeList nl = doc.getElementsByTagName("return");
                            // Element getlistData = (Element) nl.item(0);
                            //
                            // String paymentAccountTotal = parse.getValue(
                            // getlistData, "paymentAccountTotal");
                            // tvRemain.setText(StringUtils.formatMoney(paymentAccountTotal));
                            //
                            // String totalDeposit = parse.getValue(getlistData,
                            // "totalDeposit");
                            // tvPaymentDC.setText(StringUtils.formatMoney(totalDeposit));
                            //
                            // String totalMoneySubmit = parse.getValue(getlistData,
                            // "totalMoneySubmit");
                            // tvPaymentSubmit.setText(StringUtils.formatMoney(totalMoneySubmit));
                            // NodeList nodelstchild = null;
                            //
                            // for (int i = 0; i < nl.getLength(); i++) {
                            // nodelstchild = doc
                            // .getElementsByTagName("arrObject");
                            //
                            // for (int j = 0; j < nodelstchild.getLength(); j++) {
                            // DetailAccountPaymentOJ detailAccountPaymentOJ = new
                            // DetailAccountPaymentOJ();
                            // Element e1 = (Element) nodelstchild.item(j);
                            // String stockName = parse.getValue(e1, "name");
                            // String stockCode = parse.getValue(e1,
                            // "stockModelCode");
                            // String toalDeposit = parse.getValue(e1,
                            // "toalDeposit");
                            //
                            // detailAccountPaymentOJ.setName(stockName);
                            // detailAccountPaymentOJ.setStockCode(stockCode);
                            // detailAccountPaymentOJ.setToalDeposit(Long
                            // .parseLong(toalDeposit));
                            // mListObject.add(detailAccountPaymentOJ);
                            // }
                            //
                            // }

                            if (mListObject != null && mListObject.size() > 0) {

                                DetailPaymentAccountAdapter detailPaymentAccountAdapter = new DetailPaymentAccountAdapter(
                                        mListObject, getActivity());
                                lvListProduct
                                        .setAdapter(detailPaymentAccountAdapter);
                            } else {
                                Log.d("Chua co adapter", "chua co adapter");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
				this.progress.dismiss();
			}
		}
	}

	private String createXMLgetMoneyRemain(String staffCode, String staffTypeId) {
		StringBuilder stringBuilder = new StringBuilder("<ws:viewPayment>");

		stringBuilder.append("<getChannelInfoInput>");
		stringBuilder.append("<token>").append(Session.getToken()).append("</token>");
		stringBuilder.append("<staffCode>").append(staffCode).append("</staffCode>");
		stringBuilder.append("<staffTypeId>").append(staffTypeId).append("</staffTypeId>");
		stringBuilder.append("</getChannelInfoInput>");
		stringBuilder.append("</ws:viewPayment>");
		Log.d("createfilexmlSyn", stringBuilder.toString());
		return stringBuilder.toString();
	}
}
