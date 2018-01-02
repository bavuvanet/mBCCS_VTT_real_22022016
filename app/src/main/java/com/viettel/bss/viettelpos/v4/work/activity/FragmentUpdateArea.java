package com.viettel.bss.viettelpos.v4.work.activity;

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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.work.adapter.AdapterAreaInforStaff;
import com.viettel.bss.viettelpos.v4.work.object.AreaInforStaff;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FragmentUpdateArea extends Fragment implements OnClickListener, OnItemClickListener {

	private View mView = null;

	private ListView lvArea;

    private Activity activity;

	
	private String updatefamily = "";
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		Bundle mBundle = getArguments();
		if(mBundle != null){
			updatefamily = mBundle.getString("updatefamily", "0");
		}
		
		
		if (mView == null) {
			mView = inflater.inflate(R.layout.layout_update_area, container, false);
		}
		unitView(mView);
		return mView;
	}

	@Override
	public void onResume() {
		super.onResume();
        if("1".equals(updatefamily)){
            MainActivity.getInstance().setTitleActionBar(getResources().getString(R.string.updateHD));
        } else{
            MainActivity.getInstance().setTitleActionBar(getResources().getString(R.string.task_update_area));
        }
	}

	// khoi tao view
	private void unitView(View view) {
		lvArea = (ListView) view.findViewById(R.id.lvArea);

		lvArea.setOnItemClickListener(this);

		if(lvArea.getAdapter() == null || lvArea.getAdapter().isEmpty()) {
			AsyncGetAreaStaff async = new AsyncGetAreaStaff(activity);
			async.execute();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relaBackHome:
			getActivity().onBackPressed();
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if("1".equals(updatefamily)){
			AreaInforStaff areaInforStaff = (AreaInforStaff) parent.getAdapter().getItem(position);

			Log.d(Constant.TAG, "FragmentUpdateArea onItemClick areaInforStaff " + areaInforStaff.toString());

			Bundle bundle = new Bundle();
			bundle.putSerializable("areaInforStaff", areaInforStaff);

			FragmentUpdateFamily fragment = new FragmentUpdateFamily();
			fragment.setArguments(bundle);
			fragment.setTargetFragment(FragmentUpdateArea.this, 100);
			ReplaceFragment.replaceFragment(activity, fragment, true);
		} else {
			AreaInforStaff areaInforStaff = (AreaInforStaff) parent.getAdapter().getItem(position);
			
			Log.d(Constant.TAG, "FragmentUpdateArea onClick areaInforStaff " + areaInforStaff.toString());

			Bundle bundle = new Bundle();
			bundle.putSerializable("areaInforStaff", areaInforStaff);

			FragmentUpdateAreaDetail fragment = new FragmentUpdateAreaDetail();
			fragment.setArguments(bundle);
			fragment.setTargetFragment(FragmentUpdateArea.this, 100);
			ReplaceFragment.replaceFragment(activity, fragment, true);
		}
		
	}

	// move login
	private final OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
			getActivity().finish();
			MainActivity.getInstance().finish();
		}
	};
	
	private final View.OnClickListener editBtnListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Object obj = v.getTag();

		
				if(obj != null && obj instanceof AreaInforStaff) {
					AreaInforStaff areaInforStaff = (AreaInforStaff) obj;
					
					Log.d(Constant.TAG, "FragmentUpdateArea onClick areaInforStaff " + areaInforStaff.toString());

					Bundle bundle = new Bundle();
					bundle.putSerializable("areaInforStaff", areaInforStaff);

					FragmentUpdateAreaDetail fragment = new FragmentUpdateAreaDetail();
					fragment.setArguments(bundle);
					fragment.setTargetFragment(FragmentUpdateArea.this, 100);
					ReplaceFragment.replaceFragment(activity, fragment, true);
				}
			}
			
		
	};

	private class AsyncGetAreaStaff extends AsyncTask<String, String, List<AreaInforStaff>> {
		private final Context context;
		private final ProgressDialog progress;
		private String errorCode;
		private String description;

		public AsyncGetAreaStaff(Context context) {
			this.context = context;
			progress = new ProgressDialog(context);
			progress.setMessage(getResources().getString(R.string.waitting));
			if (!progress.isShowing()) {
				progress.show();
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected List<AreaInforStaff> doInBackground(String... params) {
			return getAreaStaff();
		}

		@Override
		protected void onPostExecute(List<AreaInforStaff> lstAreaStaff) {
			super.onPostExecute(lstAreaStaff);
			progress.dismiss();
			if (errorCode.equals("0")) {

				if (lstAreaStaff != null && !lstAreaStaff.isEmpty()) {
                    AdapterAreaInforStaff adapter = new AdapterAreaInforStaff(context, lstAreaStaff, editBtnListener, updatefamily);
					lvArea.setAdapter(adapter);
				} else {
					CommonActivity.createAlertDialog(context, getString(R.string.not_result_work), getString(R.string.app_name)).show();
				}

			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity.createAlertDialog(activity, description, context.getResources().getString(R.string.app_name),
							moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description, context.getResources().getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		private List<AreaInforStaff> getAreaStaff() {
			List<AreaInforStaff> lstAreaStaff = new ArrayList<>();
			try {

				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getAreaStaff");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getAreaStaff>");
				rawData.append("<input>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.token);
				rawData.append(input.buildXML(paramToken));
				rawData.append("</input>");
				rawData.append("</ws:getAreaStaff>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(), "mbccs_lookUpTaskAssign");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				String original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", response);

				// parser
				XmlDomParse parse = new XmlDomParse();
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode + " description " + description);

					nodechild = doc.getElementsByTagName("lstAreaInforStaffs");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						AreaInforStaff obj = new AreaInforStaff();
						obj.setAreaCode(parse.getValue(e1, "areaCode"));
						obj.setFullName(parse.getValue(e1, "fullName"));
						obj.setProvinceCode(parse.getValue(e1, "provinceCode"));
						obj.setDistrictCode(parse.getValue(e1, "districtCode"));
						obj.setPrecinctCode(parse.getValue(e1, "precinctCode"));
						obj.setStreetBlockCode(parse.getValue(e1, "streetBlockCode"));

						lstAreaStaff.add(obj);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return lstAreaStaff;
		}
	}

}
