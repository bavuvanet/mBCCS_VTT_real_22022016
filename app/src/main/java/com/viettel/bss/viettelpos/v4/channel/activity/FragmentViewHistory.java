package com.viettel.bss.viettelpos.v4.channel.activity;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.channel.adapter.ChannelViewHistoryCareAdapter;
import com.viettel.bss.viettelpos.v4.channel.dal.ChannelViewHistoryDAL;
import com.viettel.bss.viettelpos.v4.channel.object.ChannelViewHistoryOJ;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CheckConnectionInfo;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.handler.VSAMenuHandler;
import com.viettel.bss.viettelpos.v4.login.object.Staff;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

public class FragmentViewHistory extends Fragment {
	private ListView lvListHistory;
	private ChannelViewHistoryDAL mchannelViewHistoryDLA;
	private ArrayList<ChannelViewHistoryOJ> arrdaAdapter;
    private ArrayList<ChannelViewHistoryOJ> lisChannelViewHistoryOJs;

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
//		Bundle mBundle;
//		lvListHistory = (ListView) getActivity().findViewById(
//				R.id.lv_view_history_care);
//
//		mBundle = getArguments();
//		Staff mStaff = (Staff) mBundle.getSerializable(Define.KEY_STAFF);
//		Long staffId = mStaff.getStaffId();
//		new Getviewhistory(getActivity()).execute("" + staffId);

	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View mview = inflater.inflate(R.layout.layout_view_history_care,
				container, false);

		Unit(mview);

		return mview;
	}

	private void Unit(View v) {
		Bundle mBundle;
		lvListHistory = (ListView) v.findViewById(R.id.lv_view_history_care);

		mBundle = getArguments();
		Staff mStaff = (Staff) mBundle.getSerializable(Define.KEY_STAFF);
		Long staffId = mStaff.getStaffId();
		CheckConnectionInfo mInfo = new CheckConnectionInfo(
				getActivity());
		if (CommonActivity.isNetworkConnected(getActivity())) {
			new Getviewhistory(getActivity()).execute("" + staffId);		
		}else{
			String title = getString(R.string.app_name);
			Dialog dialog = CommonActivity
					.createAlertDialog(getActivity(), getResources()
							.getString(R.string.errorNetwork),
							title);
			dialog.show();
		}
		
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

	private class Getviewhistory extends AsyncTask<String, Void, String> {
		final BCCSGateway input = new BCCSGateway();
		String response = null;
		String original = null;
		final ProgressDialog progress;
		String sms = "";
		final XmlDomParse parse = new XmlDomParse();
		final Activity activity;

		private Getviewhistory(Activity _activity) {
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
			lisChannelViewHistoryOJs = new ArrayList<>();
			String xml = createXMLSynOjectid(arg0[0]);
			CommonOutput output = null;
			try {
				String envelope = input.buildInputGatewayWithRawData2(
						xml.toString(), "mbccs_viewVisitHistoryDetail");
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,getActivity(),"mbccs_viewVisitHistoryDetail");
				output = input.parseGWResponse(response);
				if (!output.getError().equals("0")) {
					sms = output.getDescription();
					return Constant.ERROR_CODE;
				}
				original = output.getOriginal();
				if (!output.getError().equals("0")) {
					sms = output.getDescription();
					return Constant.ERROR_CODE;
				}

				String original = output.getOriginal();
				VSAMenuHandler handler = (VSAMenuHandler) CommonActivity
						.parseXMLHandler(new VSAMenuHandler(), original);
				output = handler.getItem();
				Log.e("dd", "original = " + original.toString());
				if (!output.getErrorCode().equals("0")) {
					sms = output.getDescription();
					return output.getErrorCode();
				}
				return original;
				
			} catch (Exception e) {
				e.printStackTrace();
				sms = getResources().getString(R.string.exception)
						+ e.toString();
				return Constant.ERROR_CODE;
			}// get data
			
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
                switch (result) {
                    case Constant.INVALID_TOKEN2:
                        CommonActivity.BackFromLogin(getActivity(), sms, ";channel.management;");
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

                            if (lisChannelViewHistoryOJs.size() > 0)
                                lisChannelViewHistoryOJs
                                        .removeAll(lisChannelViewHistoryOJs);
                            NodeList nodelstchild = null;
                            // NodeList nl = doc.getElementsByTagName("lstVisit");
                            for (int i = 0; i < nl.getLength(); i++) {
                                nodelstchild = doc.getElementsByTagName("lstVisit");
                                for (int j = 0; j < nodelstchild.getLength(); j++) {
                                    Element e1 = (Element) nodelstchild.item(j);
                                    String taskName = parse
                                            .getValue(e1, "taskName");
                                    ChannelViewHistoryOJ channelViewHistoryOJ = new ChannelViewHistoryOJ();
                                    channelViewHistoryOJ.setTaskName(taskName);
                                    channelViewHistoryOJ.setContent(parse.getValue(
                                            e1, "content"));
                                    channelViewHistoryOJ.setDate(parse.getValue(e1,
                                            "strArrivalTime"));
                                    lisChannelViewHistoryOJs
                                            .add(channelViewHistoryOJ);
                                }
                            }
                            if (lisChannelViewHistoryOJs != null
                                    && lisChannelViewHistoryOJs.size() > 0) {
                                ChannelViewHistoryCareAdapter mchannelHistoryAdapter = new ChannelViewHistoryCareAdapter(
                                        lisChannelViewHistoryOJs, getActivity());
                                lvListHistory.setAdapter(mchannelHistoryAdapter);
                            } else {
                                TextView sms = (TextView) getActivity().findViewById(R.id.sms);
                                sms.setVisibility(View.VISIBLE);
                            }

                            this.progress.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
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
