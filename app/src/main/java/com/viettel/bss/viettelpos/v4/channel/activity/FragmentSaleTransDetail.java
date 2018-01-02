package com.viettel.bss.viettelpos.v4.channel.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.channel.adapter.ChannelSaleTransDetailAdapter;
import com.viettel.bss.viettelpos.v4.channel.dal.SaleTransDetailsDAL;
import com.viettel.bss.viettelpos.v4.channel.object.SaleTransDetailOJ;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.handler.ListChannelHandler;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

public class FragmentSaleTransDetail extends Fragment implements
		OnItemClickListener {

    @Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);

	}

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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View mview = inflater.inflate(
				R.layout.layout_view_sale_trans_detail, container, false);
		Unit(mview);
		return mview;
	}

	private void Unit(View v) {
		Bundle mBundle;
        ListView lvListHistory = (ListView) v
                .findViewById(R.id.lv_view_history_sale_trans_details);
        ArrayList<SaleTransDetailOJ> arrObject = new ArrayList<>();
        SaleTransDetailsDAL mDLA = new SaleTransDetailsDAL(getActivity());

		mBundle = getArguments();

        ChannelSaleTransDetailAdapter mAdapter = new ChannelSaleTransDetailAdapter(arrObject, getActivity());

		lvListHistory.setAdapter(mAdapter);

		lvListHistory.setOnItemClickListener(this);
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public void startActivity(Intent intent) {
		// TODO Auto-generated method stub
		super.startActivity(intent);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		// Log.i("TAG","click thong tin chi tiet giao dich");
		// Bundle mBundle = new Bundle();
		// SaleTransDetailOJ object = arrObject.get(arg2);
		// mBundle.putSerializable(KEY_SALE_TRANS_DETAIL, object);
		//
		//
		// FragmentManager manager = getActivity().getFragmentManager();
		// FragmentTransaction mTransaction = manager.beginTransaction();
		// FragmentSaleTransDetail mTransDetail = new FragmentSaleTransDetail();
		// mTransDetail.setArguments(mBundle);
		// mTransaction.replace(R.id.fragment_main, mTransDetail);
		// mTransaction.addToBackStack(null);
		// mTransaction.commit();

	}

	// public class GetArrayOject extends AsyncTask<Params, Progress, Result>

	// fuction get details
	private class Getdetails extends AsyncTask<String, Void, String> {
		final BCCSGateway input = new BCCSGateway();
		String response = null;
		String original = null;
		final XmlDomParse parse = new XmlDomParse();
		final Activity activity;

		private Getdetails(Activity _activity) {
			this.activity = _activity;
		}

		@Override
		protected String doInBackground(String... arg0) {
			String xml = createXMLSynOjectid(arg0[0]);
			Log.d("xml", xml);
			CommonOutput output = null;
			try {
				String envelope = input.buildInputGatewayWithRawData2(
						xml.toString(), "mbccs_countVisitedNumber");
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,getActivity(),"mbccs_countVisitedNumber");
				output = input.parseGWResponse(response);
				if (!output.getError().equals("0")) {
					return Constant.ERROR_CODE;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}// get data
			original = output.getOriginal();
			Log.d("Parse origin", original);
			return original;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				try {
					// Document doc = parse.getDomElement(result);
					// NodeList nl = doc.getElementsByTagName("result");
					// Element getlistData = (Element) nl.item(0);
					// String numVisit = parse.getValue(getlistData,
					// "numVisit");

					ListChannelHandler handler = (ListChannelHandler) CommonActivity
							.parseXMLHandler(new ListChannelHandler(
									getActivity(), null), result);
					String numVisit = handler.getNumVisit();
					handler.closeDatabase();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	// create file xml for Synchronization
    private String createXMLSynOjectid(String ojectid) {
		StringBuilder stringBuilder = new StringBuilder(
				"<ws:viewVisitHistoryDetail>");
		stringBuilder.append("<viewVisitHistoryInput>");
		stringBuilder.append("<token>").append(Session.getToken()).append("</token>");
		stringBuilder.append("<objId>").append(ojectid).append("</objId>");
		stringBuilder.append("</viewVisitHistoryInput>");
		stringBuilder.append("</ws:viewVisitHistoryDetail>");
		Log.d("createfilexmlSyn", stringBuilder.toString());
		return stringBuilder.toString();
	}

}
