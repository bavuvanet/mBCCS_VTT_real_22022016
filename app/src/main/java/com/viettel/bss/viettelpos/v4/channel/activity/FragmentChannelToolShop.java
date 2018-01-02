package com.viettel.bss.viettelpos.v4.channel.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.channel.adapter.ToolShopExpandAdapter;
import com.viettel.bss.viettelpos.v4.channel.object.ArrayAssetOJ;
import com.viettel.bss.viettelpos.v4.channel.object.AssetDetailHistoryOJ;
import com.viettel.bss.viettelpos.v4.channel.object.AssetTypeOJ;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CheckConnectionInfo;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.handler.VSAMenuHandler;
import com.viettel.bss.viettelpos.v4.login.object.Staff;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class FragmentChannelToolShop extends Fragment implements
		OnClickListener, OnItemClickListener, Define, OnScrollListener {

	private View mView = null;
    private Long staffId;
    // private TextView
	private ExpandableListView expandableList;
	private final ArrayList<ArrayAssetOJ> childItems = new ArrayList<>();
	private final ArrayList<AssetTypeOJ> parentItems = new ArrayList<>();
	private GetViewToolShopAsyncTask getViewToolShopAsyncTask;
	private String errorMessage;
    private final ArrayList<AssetTypeOJ> arrListAssetObject = new ArrayList<>();

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Log.d("TAG", "onActivityCreated FragmentLoginNotData");
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
		CheckConnectionInfo mInfo = new CheckConnectionInfo(getActivity());
		if (CommonActivity.isNetworkConnected(getActivity())) {
			getViewToolShopAsyncTask = new GetViewToolShopAsyncTask();
			getViewToolShopAsyncTask.execute();
		} else {
			String title = getString(R.string.app_name);
			Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
					getResources().getString(R.string.errorNetwork), title);
			dialog.show();
		}

		// Bundle mBundle = getArguments();
		// if (mBundle != null) {
		// flag = (Boolean) mBundle.getSerializable("FLAG");
		//
		// if (flag != null && flag) {
		// CheckConnectionInfo mInfo = new CheckConnectionInfo(
		// getActivity());
		// if (mInfo.isNetworkOnline()) {
		// getViewToolShopAsyncTask = new GetViewToolShopAsyncTask();
		// getViewToolShopAsyncTask.execute();
		// } else {
		// String title = getString(R.string.app_name);
		// AlertDialog dialog = CommonActivity.createDialog(
		// getActivity(),
		// getResources().getString(R.string.errorNetwork),
		// title);
		// dialog.show();
		// }
		//
		// }
		// }
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		if (mView == null) {
			mView = inflater.inflate(R.layout.layout_tool_shop, container,
					false);
			Unit(mView, savedInstanceState);
			CheckConnectionInfo mInfo = new CheckConnectionInfo(getActivity());
			if (CommonActivity.isNetworkConnected(getActivity())) {
				getViewToolShopAsyncTask = new GetViewToolShopAsyncTask();
				getViewToolShopAsyncTask.execute();
			} else {
				String title = getString(R.string.app_name);
				Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
						getResources().getString(R.string.errorNetwork), title);
				dialog.show();
			}
		}

		return mView;
	}

	private void Unit(View v, Bundle mBunder) {

		expandableList = (ExpandableListView) v
				.findViewById(R.id.lv_list_tool_shop); // you can use
														// (ExpandableListView)
														// findViewById(R.id.list)

		expandableList.setDividerHeight(2);
		expandableList.setGroupIndicator(null);
		expandableList.setClickable(true);

		Bundle mBundle;
		mBundle = getArguments();
        Staff mStaff = (Staff) mBundle.getSerializable(Define.KEY_STAFF);
		staffId = mStaff.getStaffId();
		Log.i("TAG", "staffId" + staffId);

        Button btUpdate = (Button) v.findViewById(R.id.btnUpdateToolShop);
		btUpdate.setOnClickListener(this);
	}

	private void setData(ArrayList<AssetTypeOJ> arrList) {
		if (!parentItems.isEmpty() && parentItems.size() > 0) {
			parentItems.removeAll(parentItems);
		}

		if (!childItems.isEmpty() && childItems.size() > 0) {
			childItems.removeAll(childItems);
		}
		for (AssetTypeOJ assetTypeOJ : arrList) {
			if (assetTypeOJ != null) {
				parentItems.add(assetTypeOJ);
				ArrayAssetOJ child = new ArrayAssetOJ();
				child.setAssetList(assetTypeOJ.getLstAssetDetailOJ());
				childItems.add(child);
			}
		}
	}

    @Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);

		// TODO Auto-generated method stub

	}

	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Bundle mBundle = getArguments();
		switch (v.getId()) {
		case R.id.btnUpdateToolShop:
			Log.i("TAG", "Them vat pham trung bay");

			FragmentUpdateToolShop fragmentUpdateToolShop = new FragmentUpdateToolShop();
			fragmentUpdateToolShop.setArguments(mBundle);

			ReplaceFragment.replaceFragment(getActivity(),
					fragmentUpdateToolShop, false);

			break;
		}
	}

	private String getListObjectFromWebService(Long staffId) {

		try {

			BCCSGateway input = new BCCSGateway();

			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_viewManageAssetHistory");
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:viewManageAssetHistory>");
			rawData.append("<viewManageAssetInput>");

			HashMap<String, String> param = new HashMap<>();
			param.put("token", Session.getToken());
			param.put("staffId", staffId.toString());
			param.put("shopId", null);
			rawData.append(input.buildXML(param));
			rawData.append("</viewManageAssetInput>");
			rawData.append("</ws:viewManageAssetHistory>");

			Log.i("LOG_TAG", rawData.toString());
			String envelope = input.buildInputGatewayWithRawData(rawData
					.toString());
			Log.i("LOG_TAG", envelope);
			String response = input.sendRequest(envelope,Constant.BCCS_GW_URL,getActivity(),"mbccs_viewManageAssetHistory");

			CommonOutput output = input.parseGWResponse(response);
			if (!output.getError().equals("0")) {
				errorMessage = output.getDescription();
				return null;
			}
			String original = output.getOriginal();
			Log.i("LOG", original);
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

            ArrayList<AssetDetailHistoryOJ> arrayList = new ArrayList<>();
			if (arrayList.size() > 0)
				arrayList.clear();
			arrListAssetObject.clear();

//			parseDataSAX(original);

			XmlDomParse domParse = new XmlDomParse();
			Document document = domParse.getDomElement(original);
			NodeList nodeList = document
					.getElementsByTagName("lstManageAssetBean");
			// ArrayList<AssetTypeOJ> arrayAssetType = new
			// ArrayList<AssetTypeOJ>();

			HashMap<String, AssetTypeOJ> arrayAssetType = new HashMap<>();

			for (int i = 0; i < nodeList.getLength(); i++) {
				AssetTypeOJ assetTypeOJ = new AssetTypeOJ();

				Node mNode = nodeList.item(i);
				Element element = (Element) mNode;
				AssetDetailHistoryOJ assetDetailHistoryOJ = new AssetDetailHistoryOJ();
				String assetName = domParse.getValue(element, "name");
				assetDetailHistoryOJ.setName(assetName);
				String note = domParse.getValue(element, "note");
				note = (note != null) ? note : "";

				assetDetailHistoryOJ.setNote(note);
				String assetCode = domParse.getValue(element, "assetCode");
				assetDetailHistoryOJ.setCode(assetCode);

				String qty = domParse.getValue(element, "quantity");
				qty = (qty != null && qty != "") ? qty : "0";
				assetDetailHistoryOJ.setQty(Long.parseLong(qty));
				assetDetailHistoryOJ.setStaffId(domParse.getValue(element,
						"staffId"));
				assetDetailHistoryOJ.setUpdateUserId(domParse.getValue(element,
						"updateUserId"));

				if (!arrayAssetType.isEmpty()
						&& arrayAssetType.get(assetCode) != null) {
					// arrOjTmp = assetTypeOJ.getLstAssetDetailOJ();
					// arrOjTmp.add(assetDetailHistoryOJ);
					// assetTypeOJ.setLstAssetDetailOJ(arrOjTmp);
					AssetTypeOJ assetTypeOJ2 = arrayAssetType.get(assetCode);
					assetTypeOJ2.setName(assetName);
					assetTypeOJ2.setQty(assetTypeOJ2.getQty()
							+ assetDetailHistoryOJ.getQty());
					assetTypeOJ2.getLstAssetDetailOJ()
							.add(assetDetailHistoryOJ);
				} else {
					assetTypeOJ.getLstAssetDetailOJ().add(assetDetailHistoryOJ);
					assetTypeOJ.setQty(assetDetailHistoryOJ.getQty());
					assetTypeOJ.setName(assetName);
					arrayAssetType.put(assetCode, assetTypeOJ);

				}
				arrayList.add(assetDetailHistoryOJ);
				Log.d("LOG", assetDetailHistoryOJ.getName());
			}
			for (java.util.Map.Entry<String, AssetTypeOJ> entry : arrayAssetType
					.entrySet()) {

				AssetTypeOJ assetTypeOJ = entry.getValue();
				arrListAssetObject.add(assetTypeOJ);
			}
		} catch (Exception e) {

			e.printStackTrace();
			errorMessage = getResources().getString(R.string.exception)
					+ e.toString();
			return Constant.ERROR_CODE;
		}
		return Constant.SUCCESS_CODE;

	}

	private void parseDataSAX(String original) throws IOException {
		// TODO Auto-generated method stub
		XmlPullParserFactory fc;
		XmlPullParser parser = null;
		FileInputStream fIn = null;

		try {
			fc = XmlPullParserFactory.newInstance();
			parser = fc.newPullParser();

		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		String sdcard = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		String xmlfile = sdcard + "/parse" + System.currentTimeMillis()
				+ ".xml";
		try {
			fIn = new FileInputStream(xmlfile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		int eventType = -1;
		String nodeName;
		String datashow = "";

		try {
			parser.setInput(fIn, "UTF-8");

			while (eventType != XmlPullParser.END_DOCUMENT)
			{
				eventType = parser.next();// bắt đầu duyệt để
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					break;
				case XmlPullParser.END_DOCUMENT:
					break;
				case XmlPullParser.START_TAG:// tag mở
					nodeName = parser.getName();
                    switch (nodeName) {
                        case "name":
//						assetDetailHistoryOJ.setName(assetName);
                            datashow += parser.getAttributeValue(0) + "-";
                            datashow += parser.getAttributeValue(1) + "-";
                            break;
                        case "phone":
                            datashow += parser.nextText() + "-";
                            break;
                    }
					
					
					break;
				case XmlPullParser.END_TAG:
					nodeName = parser.getName();
//					if (nodeName.equals("employee")) {
//						datashow += "\n----------------\n";
//					}
					break;
				}
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}

	}

	private class GetViewToolShopAsyncTask extends
			AsyncTask<String, String, String> {
		final ProgressDialog progress;

		public GetViewToolShopAsyncTask() {
			progress = new ProgressDialog(getActivity());
			this.progress.setMessage(getResources()
					.getString(R.string.waitting));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected String doInBackground(String... params) {

			return getListObjectFromWebService(staffId);
		}

		@Override
		protected void onPostExecute(String result) {
            switch (result) {
                case Constant.INVALID_TOKEN2:
                    CommonActivity.BackFromLogin(getActivity(), errorMessage, "");
                    break;
                case Constant.SUCCESS_CODE:
                    if (arrListAssetObject.size() > 0) {
                        setData(arrListAssetObject);

                        ToolShopExpandAdapter adapter = new ToolShopExpandAdapter(
                                parentItems, childItems);

                        adapter.setInflater((LayoutInflater) getActivity()
                                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE),
                                getActivity());
                        expandableList.setAdapter(adapter);

                        for (int count = 0; count < arrListAssetObject.size(); count++) {
                            expandableList.expandGroup(count);
                        }
                        TextView sms__ = (TextView) getActivity().findViewById(
                                R.id.sms__);
                        sms__.setVisibility(View.GONE);
                    } else {
                        TextView sms__ = (TextView) getActivity().findViewById(
                                R.id.sms__);
                        sms__.setVisibility(View.VISIBLE);
                    }

                    break;
                default:
                    String title = getString(R.string.app_name);
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
                            errorMessage, title);
                    dialog.show();
                    break;
            }
			this.progress.dismiss();
		}

	}

}
