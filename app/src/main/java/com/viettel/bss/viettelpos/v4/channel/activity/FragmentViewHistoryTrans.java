package com.viettel.bss.viettelpos.v4.channel.activity;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.channel.adapter.ChannelSaleTransHistoryAdapter;
import com.viettel.bss.viettelpos.v4.channel.object.ArraySaleChannelOJ;
import com.viettel.bss.viettelpos.v4.channel.object.SaleTransDetailOJ;
import com.viettel.bss.viettelpos.v4.channel.object.SaleTransOJ;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.handler.VSAMenuHandler;
import com.viettel.bss.viettelpos.v4.login.object.Staff;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

public class FragmentViewHistoryTrans extends Fragment implements
		OnItemClickListener {
	// private final String KEY_SALE_TRANS_DETAIL =
	// "key_staff_sale_trans_detail";
	// private ListView lvListHistory;

	private ArrayList<SaleTransOJ> arrObject = new ArrayList<>();
	// private ChannelSaleTransHistoryAdapter mAdapter;
	// private ListView lvListHistory_d;

	// private ArrayList<SaleTransDetailOJ> arrObject_d;
	private ExpandableListView expandableList;
	private final ArrayList<ArraySaleChannelOJ> childItems = new ArrayList<>();
	private final ArrayList<SaleTransOJ> parentItems = new ArrayList<>();
	private TextView sms11;
	SaleTransOJ object, objectSaved;
	View itemTmp;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onStart() {
		super.onStart();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View mview = inflater.inflate(R.layout.layout_view_history_sale_trans,
				container, false);
		Unit(mview);
		return mview;
	}

	private void Unit(View v) {
		Bundle mBundle;

		expandableList = (ExpandableListView) v
				.findViewById(R.id.lv_view_history_sale_trans); // you can use
																// (ExpandableListView)
																// findViewById(R.id.list)
		sms11 = (TextView) v.findViewById(R.id.sms11);
		expandableList.setDividerHeight(2);
		expandableList.setGroupIndicator(null);
		expandableList.setClickable(true);
		arrObject = new ArrayList<>();

		mBundle = getArguments();
		if (mBundle != null) {
			Staff mStaff = (Staff) mBundle.getSerializable(Define.KEY_STAFF);
			if (mStaff != null) {
				// CheckConnectionInfo mInfo = new CheckConnectionInfo(
				// getActivity());
				if (CommonActivity.isNetworkConnected(getActivity())) {
					new GetSaleTransHistorytAsynctask(getActivity())
							.execute(mStaff.getStaffCode());
				} else {
					String title = getString(R.string.app_name);
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.errorNetwork),
							title);
					dialog.show();
				}

			}

		}
	}

	private String createXMLGetHistorySale(String staffCode) {
		StringBuilder stringBuilder = new StringBuilder(
				"<ws:viewStaffSaleHistory>");

		stringBuilder.append("<saleHistoryInput>");
		stringBuilder.append("<token>").append(Session.getToken()).append("</token>");
		stringBuilder.append("<staffCode>").append(staffCode).append("</staffCode>");
		stringBuilder.append("<manageStaffCode></manageStaffCode>");
		stringBuilder.append("</saleHistoryInput>");
		stringBuilder.append("</ws:viewStaffSaleHistory>");
		Log.d("createfilexmlSyn", stringBuilder.toString());
		return stringBuilder.toString();
	}

	private class GetSaleTransHistorytAsynctask extends
			AsyncTask<String, Void, String> {
		final BCCSGateway input = new BCCSGateway();
		// String response = null;
		String original = null;
		final XmlDomParse parse = new XmlDomParse();
		// Activity activity;
		String sms = "";
		final ProgressDialog progress;

		private GetSaleTransHistorytAsynctask(Activity _activity) {
			// this.activity = _activity;
			this.progress = new ProgressDialog(getActivity());
			this.progress.setMessage(getActivity().getResources().getString(
					R.string.waitting));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected String doInBackground(String... arg0) {
			Log.e("TAG", "ARG" + arg0[0]);
			String xml = createXMLGetHistorySale(arg0[0]);

			CommonOutput output = null;
			try {
				String envelope = input.buildInputGatewayWithRawData2(
						xml.toString(), "mbccs_viewStaffSaleHistory");

				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_viewStaffSaleHistory");

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

				if (output.getToken() != null && !output.getToken().isEmpty()) {
					Session.setToken(output.getToken());
				}
			} catch (Exception e) {
				e.printStackTrace();
				sms = getResources().getString(R.string.exception)
						+ e.toString();
				return Constant.ERROR_CODE;
			}

			return original;
		}

		public void setData(ArrayList<SaleTransOJ> arrList) {
			for (SaleTransOJ saleOJ : arrList) {
				if (saleOJ != null) {
					parentItems.add(saleOJ);
					ArraySaleChannelOJ child = new ArraySaleChannelOJ();
					child.setSaleList(saleOJ.getArrSaleTranDetail());
					childItems.add(child);
				}
			}
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
                            Document doc = parse.getDomElement(result);
                            NodeList nl = doc.getElementsByTagName("return");
                            // Element getlistData = (Element) nl.item(0);
                            // ArrayList<Staff> arrayStaffWs = new
                            // ArrayList<Staff>();
                            NodeList nodelstchild = null;

                            // ArrayList<Staff> resultArr = new ArrayList<Staff>();
                            for (int i = 0; i < nl.getLength(); i++) {

                                nodelstchild = doc
                                        .getElementsByTagName("lstSaleTranse");

                                // NodeList nodelstchild1 = null;
                                for (int j = 0; j < nodelstchild.getLength(); j++) {
                                    SaleTransOJ saleTransOJ = new SaleTransOJ();

                                    Element e1 = (Element) nodelstchild.item(j);

                                    // String channelName = parse.getValue(e1,
                                    // "channelName");
                                    // String staffName = parse.getValue(e1,
                                    // "staffName");
                                    String saleTransDate = DateTimeUtils
                                            .convertDate(parse.getValue(e1,
                                                    "saleTransDate"));

                                    Long totalRevenue = Long.parseLong(parse
                                            .getValue(e1, "totalRevenue"));
                                    saleTransOJ = new SaleTransOJ(saleTransDate,
                                            totalRevenue);
                                    SaleTransDetailOJ saleDetailTitle = new SaleTransDetailOJ(
                                            getResources().getString(
                                                    R.string.txtProductName),
                                            getResources().getString(
                                                    R.string.txtProductNum),
                                            getResources().getString(
                                                    R.string.tv_amount));
                                    ArrayList<SaleTransDetailOJ> arrSaleTransDetail = new ArrayList<>();
                                    arrSaleTransDetail.add(saleDetailTitle);
                                    NodeList nodelstchild2 = null;
                                    nodelstchild2 = e1
                                            .getElementsByTagName("lstSaleTransDetail");
                                    for (int c = 0; c < nodelstchild2.getLength(); c++) {
                                        SaleTransDetailOJ saleTransDetailOJ = null;
                                        Element e2 = (Element) nodelstchild2
                                                .item(c);
                                        String amount = parse
                                                .getValue(e2, "amount");
                                        String quantity = parse.getValue(e2,
                                                "quantity");
                                        String stockModelName = parse.getValue(e2,
                                                "stockModelName");
                                        saleTransDetailOJ = new SaleTransDetailOJ(
                                                stockModelName, quantity, amount);
                                        arrSaleTransDetail.add(saleTransDetailOJ);
                                    }
                                    saleTransOJ
                                            .setArrSaleTranDetail(arrSaleTransDetail);
                                    arrObject.add(saleTransOJ);
                                }

                            }
                            if (arrObject.size() > 0) {
                                setData(arrObject);
                                ChannelSaleTransHistoryAdapter adapter = new ChannelSaleTransHistoryAdapter(
                                        parentItems, childItems);

                                adapter.setInflater(
                                        (LayoutInflater) getActivity()
                                                .getSystemService(
                                                        Context.LAYOUT_INFLATER_SERVICE),
                                        getActivity());
                                expandableList.setAdapter(adapter);
                            } else {
                                sms11.setVisibility(View.VISIBLE);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
				progress.dismiss();
			}
		}

	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View item, int arg2, long arg3) {

	}

}
