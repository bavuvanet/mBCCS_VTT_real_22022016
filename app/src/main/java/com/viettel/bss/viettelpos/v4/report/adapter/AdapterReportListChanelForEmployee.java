package com.viettel.bss.viettelpos.v4.report.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.report.object.ReportChanelCareDetail;
import com.viettel.bss.viettelpos.v4.report.object.ReportObjectChanelCareEmploy;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterReportListChanelForEmployee extends BaseAdapter {

	private final Context mContext;
	private final ArrayList<ReportObjectChanelCareEmploy> lisHeaders;
	private String fromDate = "";
	private String toDate = "";
	private AdapterReportListChanelDetail adListChanelDetail;

	public AdapterReportListChanelForEmployee(Context mContext,
			ArrayList<ReportObjectChanelCareEmploy> lisHeaders,
			String fromDate, String toDate) {
		super();
		this.mContext = mContext;
		this.lisHeaders = lisHeaders;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	@Override
	public int getCount() {
		return lisHeaders.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	class ViewHolder {
		TextView tvusercode;
		TextView tvsoluongcs1lan;
		TextView tvsoluongcs2lan;
		TextView tvsoluongcs3lan;
		TextView tvsoluongcs4lan;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		View mView = convertView;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.report_item_employee, parent,
					false);
			holder = new ViewHolder();
			holder.tvusercode = (TextView) mView.findViewById(R.id.tvnamenv);
			holder.tvsoluongcs1lan = (TextView) mView
					.findViewById(R.id.tvslancs1lan);
			holder.tvsoluongcs2lan = (TextView) mView
					.findViewById(R.id.tvslancs2lan);
			holder.tvsoluongcs3lan = (TextView) mView
					.findViewById(R.id.tvslancs3lan);
			holder.tvsoluongcs4lan = (TextView) mView
					.findViewById(R.id.tvslancs4lan);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		final ReportObjectChanelCareEmploy reportObjectChanelCareEmploy = this.lisHeaders
				.get(position);
		if (reportObjectChanelCareEmploy != null) {
			holder.tvusercode.setText(reportObjectChanelCareEmploy
					.getUserCode());

			holder.tvsoluongcs1lan.setText(""
					+ reportObjectChanelCareEmploy.getVisit1());
			holder.tvsoluongcs1lan
					.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View arg0) {

							if (reportObjectChanelCareEmploy.getVisit1() > 0) {
								if (lisHeaders.get(position)
										.getLisChanelCareDetails().size() > 0) {
									final Dialog dialog = new Dialog(mContext);
									dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
									dialog.setContentView(R.layout.report_chanelcare_popup);
									final ListView lvdetail = (ListView) dialog
											.findViewById(R.id.lvdetail);
									adListChanelDetail = new AdapterReportListChanelDetail(
											mContext, lisHeaders.get(position)
													.getLisChanelCareDetails());
									lvdetail.setAdapter(adListChanelDetail);
									Button btncancel = (Button) dialog
											.findViewById(R.id.btncancel);

									btncancel
											.setOnClickListener(new OnClickListener() {

												@Override
												public void onClick(View v) {
													dialog.cancel();
												}
											});

									dialog.show();
								} else {
									getListChild("1",
											reportObjectChanelCareEmploy
													.getUserCode());
								}

							} else {
								CommonActivity.createAlertDialog(
										(Activity) mContext,
										mContext.getResources().getString(
												R.string.checkcs1),
										mContext.getResources().getString(
												R.string.REPORT_CHANEL_CARE))
										.show();
							}
						}
					});
			holder.tvsoluongcs2lan.setText(""
					+ reportObjectChanelCareEmploy.getVisit2());
			holder.tvsoluongcs2lan
					.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							if (reportObjectChanelCareEmploy.getVisit2() > 0) {

								getListChild("2", 
										reportObjectChanelCareEmploy
												.getUserCode());

							} else {
								CommonActivity.createAlertDialog(
										(Activity) mContext,
										mContext.getResources().getString(
												R.string.checkcs2),
										mContext.getResources().getString(
												R.string.REPORT_CHANEL_CARE))
										.show();
							}

						}
					});
			holder.tvsoluongcs3lan.setText(""
					+ reportObjectChanelCareEmploy.getVisit3());
			holder.tvsoluongcs3lan
					.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							if (reportObjectChanelCareEmploy.getVisit3() > 0) {

								getListChild("3", 
										reportObjectChanelCareEmploy
												.getUserCode());

							} else {
								CommonActivity.createAlertDialog(
										(Activity) mContext,
										mContext.getResources().getString(
												R.string.checkcs3),
										mContext.getResources().getString(
												R.string.REPORT_CHANEL_CARE))
										.show();
							}

						}
					});
			holder.tvsoluongcs4lan.setText(""
					+ reportObjectChanelCareEmploy.getVisit4());
			holder.tvsoluongcs4lan
					.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							if (reportObjectChanelCareEmploy.getVisit4() > 0) {
								getListChild("4",
										reportObjectChanelCareEmploy
												.getUserCode());

							} else {
								CommonActivity.createAlertDialog(
										(Activity) mContext,
										mContext.getResources().getString(
												R.string.checkcs4),
										mContext.getResources().getString(
												R.string.REPORT_CHANEL_CARE))
										.show();
							}

						}
					});
		}
		return mView;
	}

	private void getListChild(String vitnumber, String userCode) {
		if (CommonActivity.isNetworkConnected(mContext)) {
			new GetDetailChanelCare(mContext).execute(vitnumber, userCode);
		} else {
			CommonActivity.createAlertDialog((Activity) mContext,
					mContext.getResources().getString(R.string.errorNetwork),
					mContext.getResources().getString(R.string.app_name))
					.show();
		}

	}

	// Asyn get detail list object
	// move login
    private final OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(mContext, LoginActivity.class);
			mContext.startActivity(intent);
			((Activity) mContext).finish();
		}
	};

	// ws get detail chanel care
	public class GetDetailChanelCare extends
			AsyncTask<String, Void, ArrayList<ReportChanelCareDetail>> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetDetailChanelCare(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.getdatareport));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<ReportChanelCareDetail> doInBackground(
				String... params) {
			// truyen visited num
			return getListDetailChanelCare(params[0], params[1]);
		}

		@Override
		protected void onPostExecute(ArrayList<ReportChanelCareDetail> result) {

			// check errorcode
			progress.dismiss();

			if (errorCode.equals("0")) {

				if (result.size() > 0) {
					final Dialog dialog = new Dialog(context);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.report_chanelcare_popup);
					final ListView lvdetail = (ListView) dialog
							.findViewById(R.id.lvdetail);
					adListChanelDetail = new AdapterReportListChanelDetail(
							context, result);
					lvdetail.setAdapter(adListChanelDetail);
					Button btncancel = (Button) dialog
							.findViewById(R.id.btncancel);

					btncancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							dialog.cancel();
						}
					});

					dialog.show();

				} else {
					CommonActivity.createAlertDialog(
							(Activity) context,
							context.getResources().getString(R.string.no_data),
							context.getResources().getString(
									R.string.REPORT_CHANEL_CARE)).show();
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
									(Activity) context,
									description,
									context.getResources().getString(
											R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							(Activity) context,
							description,
							context.getResources().getString(
									R.string.REPORT_CHANEL_CARE));
					dialog.show();

				}
			}

		}

		public ArrayList<ReportChanelCareDetail> getListDetailChanelCare(
				String visitNum, String usercode) {
			ArrayList<ReportChanelCareDetail> lisChanelCareDetails = new ArrayList<>();
			String original = "";

			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_reportChannelCareDetailBusiness");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:reportChannelCareDetailBusiness>");
				rawData.append("<reportInput>");
				HashMap<String, String> param = new HashMap<>();
				param.put("token", Session.getToken());
				param.put("fromDate", fromDate);
				param.put("toDate", toDate);
				param.put("userCode", usercode);
				param.put("visitedNum", "" + visitNum);
				rawData.append(input.buildXML(param));
				rawData.append("</reportInput>");
				rawData.append("</ws:reportChannelCareDetailBusiness>");
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,mContext,"mbccs_reportChannelCareDetailBusiness");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				Log.d("originalllllllll", original);

				// ============== parse xml =================
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					nodechild = doc.getElementsByTagName("lstStaff");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						ReportChanelCareDetail reportChanelCareDetail = new ReportChanelCareDetail();

						String address = parse.getValue(e1, "address");
						Log.d("address", address);
						reportChanelCareDetail.setAddress(address);

						String channelType = parse.getValue(e1, "channelType");
						Log.d("channelType", channelType);
						reportChanelCareDetail.setChanelType(channelType);

						String name = parse.getValue(e1, "name");
						Log.d("name", name);
						reportChanelCareDetail.setNameChanel(name);

						String arrivalTime = parse.getValue(e1, "arrivalTime");
						Log.d("arrivalTime", arrivalTime);
						reportChanelCareDetail.setCsgannhat(arrivalTime);
						// add list detail
						lisChanelCareDetails.add(reportChanelCareDetail);

					}
				
				}

			} catch (Exception e) {
				Log.d("GetDetailChanelCare", e.toString());
			}

			return lisChanelCareDetails;
		}

	}

}
