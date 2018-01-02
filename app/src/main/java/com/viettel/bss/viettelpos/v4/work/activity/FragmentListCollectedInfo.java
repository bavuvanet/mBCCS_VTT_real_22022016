package com.viettel.bss.viettelpos.v4.work.activity;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.handler.VSAMenuHandler;
import com.viettel.bss.viettelpos.v4.synchronizationdata.GetMaxOraRowScnDal;
import com.viettel.bss.viettelpos.v4.synchronizationdata.OjectSyn;
import com.viettel.bss.viettelpos.v4.synchronizationdata.SynchronizationOneData;
import com.viettel.bss.viettelpos.v4.work.adapter.AdapterCollectedInfo;
import com.viettel.bss.viettelpos.v4.work.adapter.AdapterCollectedInfo.onClickButtonListener;
import com.viettel.bss.viettelpos.v4.work.adapter.AdapterCollectedInfo.onClickViewDetailListener;
import com.viettel.bss.viettelpos.v4.work.dal.JobDal;
import com.viettel.bss.viettelpos.v4.work.object.CollectedObjectInfo;
import com.viettel.bss.viettelpos.v4.work.object.ObjectDetailGroup;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentListCollectedInfo extends Fragment implements
		OnItemClickListener, OnClickListener, onClickButtonListener,
		onClickViewDetailListener, OnTaskCompleted {

	private AdapterCollectedInfo mAdapter;
	private Button btnHome;
	private JobDal mJobDal;
	private ListView lvCriteria = null;
	private final String TABLE_NAME = "collected_object_info";
    private Bundle mBundle;
	private int postisionDelete;
    private FragmentListCollectedInfo inStance;
	private ObjectDetailGroup mObjectDetailGroup;
	private ArrayList<CollectedObjectInfo> lstObjs = new ArrayList<>();

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.d("TAG", "onActivityCreated FragmentCriteria");
		mBundle = getArguments();
		mObjectDetailGroup = (ObjectDetailGroup) mBundle
				.getSerializable(Define.KEY_CRITERIA);

        MainActivity.getInstance().setTitleActionBar(mObjectDetailGroup.getName());
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		Log.d("TAG", "onAttach FragmentCriteria");
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d("TAG", "onCreateView FragmentCriteria");

        View mView = inflater.inflate(R.layout.layout_criteria_detail_list,
                container, false);
		unit(mView);

		return mView;
	}

	@Override
	public void onDestroy() {
		Log.d("TAG", "onDestroy FragmentCriteria");
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		Log.d("TAG", "onDestroyView FragmentCriteria");

		super.onDestroyView();
	}

	@Override
	public void onDetach() {
		Log.d("TAG", "onDetach FragmentCriteria");
		// TODO Auto-generated method stub
		super.onDetach();
	}

	@Override
	public void onPause() {
		Log.d("TAG", "onPause FragmentCriteria");
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onResume() {
		Log.d("TAG", "onResume FragmentCriteria");
		super.onResume();

		try {
			mJobDal.open();
			if (Session.loginUser != null
					&& Session.loginUser.getStaffCode() != null) {
				lstObjs = mJobDal.getListCollectedInfo(TABLE_NAME,
						mObjectDetailGroup.getId(),
						Session.loginUser.getStaffCode());
			}
			mJobDal.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onStart() {
		Log.d("TAG", "onStart FragmentCriteria");
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onStop() {
		Log.d("TAG", "onStop FragmentCriteria");
		// TODO Auto-generated method stub
		super.onStop();
	}

	private void unit(View v) {
		lvCriteria = (ListView) v.findViewById(R.id.lvCriteria);
		// lvCriteria.setOnItemClickListener(this);
		try {
			Bundle mBundle = getArguments();
			inStance = this;
			if (mBundle != null) {
				ObjectDetailGroup mObjectDetailGroup = (ObjectDetailGroup) mBundle
						.getSerializable(Define.KEY_CRITERIA);

                Button btUpdateOU = (Button) v.findViewById(R.id.btUpdateOU);
				btUpdateOU.setText(getResources()
						.getString(R.string.addNewItem));
				mJobDal = new JobDal(getActivity());

				mJobDal.open();

				lstObjs = mJobDal.getListCollectedInfo(TABLE_NAME,
						mObjectDetailGroup.getId(),
						Session.loginUser.getStaffCode());
				Log.e("LOG", "lstObjs = " + lstObjs.size());
				mAdapter = new AdapterCollectedInfo(getActivity(), lstObjs);
                TextView mTextView = (TextView) v.findViewById(R.id.sms11);
				if (lstObjs.size() > 0) {
					mTextView.setVisibility(View.GONE);
				} else {
					mTextView.setVisibility(View.VISIBLE);
				}
				mAdapter.setmClickViewDetailListener(this);
				mAdapter.setmOnButtonListener(this);
				lvCriteria.setAdapter(mAdapter);

				btUpdateOU.setOnClickListener(this);

				btUpdateOU.setVisibility(View.VISIBLE);

			}

			mJobDal.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		String name = lstObjs.get(arg2).getValue();

		Log.e("TAG", "Name = " + name);

		Bundle mBundle = new Bundle();
		mBundle.putSerializable(Define.KEY_CRITERIA, mObjectDetailGroup);
		mBundle.putSerializable(Define.KEY_COLLECTED_INFO, null);
		FragmentUpdateCriteria mFragmentUpdateCriteria = new FragmentUpdateCriteria();
		mFragmentUpdateCriteria.setArguments(mBundle);
		ReplaceFragment.replaceFragment(getActivity(), mFragmentUpdateCriteria,
				true);
		// FragmentManager fragmentManager = getFragmentManager();
		// fragmentManager.beginTransaction()
		// .replace(R.id.frame_container, mFragmentUpdateCriteria)
		// .commit();

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {

		case R.id.btnHome:
			CommonActivity.callphone(getActivity(), Constant.phoneNumber);
			break;
		case R.id.relaBackHome:
			getActivity().onBackPressed();
			break;
		case R.id.btUpdateOU:
			FragmentUpdateCriteria mFragmentUpdateCriteria = new FragmentUpdateCriteria();
			mBundle.putSerializable(Define.KEY_CRITERIA, mObjectDetailGroup);
			mBundle.putSerializable(Define.KEY_COLLECTED_INFO, null);
			mBundle.putSerializable(Define.GET_VALUE, false);
			mFragmentUpdateCriteria.setArguments(mBundle);
			ReplaceFragment.replaceFragment(getActivity(),
					mFragmentUpdateCriteria, false);
			break;
		}

	}

	@Override
	public void onClickView(int position) {
		Log.e("TAG", "XEM CHI TIET = " + position);
		Bundle mBundle = new Bundle();
		mBundle.putSerializable(Define.KEY_CRITERIA, mObjectDetailGroup);
		mBundle.putSerializable(Define.KEY_COLLECTED_INFO,
				lstObjs.get(position));
		FragmentUpdateCriteria mFragmentUpdateCriteria = new FragmentUpdateCriteria();
		mFragmentUpdateCriteria.setArguments(mBundle);
		ReplaceFragment.replaceFragment(getActivity(), mFragmentUpdateCriteria,
				false);
	}

	private final OnClickListener delete = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (CommonActivity.isNetworkConnected(getActivity())) {

				DeleteCollectMarketInfo mCollectMarketInfo = new DeleteCollectMarketInfo(
						postisionDelete, getActivity());
				mCollectMarketInfo.execute();

			} else {
				String title = getString(R.string.app_name);
				Dialog dialog1 = CommonActivity.createAlertDialog(
						getActivity(),
						getResources().getString(R.string.errorNetwork), title);
				dialog1.show();
			}

		}
	};

	@Override
	public void onClickButton(int position) {
		postisionDelete = position;
		Log.e("TAG", "XOA PHAN TU");
		String sms = getResources().getString(R.string.confirmDelete);
		Dialog dialog = CommonActivity.createDialog(getActivity(),

		getResources()

		.getString(R.string.confirmDelete),

		getResources().getString(R.string.app_name),

		getResources().getString(R.string.ok), getResources()

		.getString(R.string.cancel), delete, null);

		dialog.show();
	}

	private class DeleteCollectMarketInfo extends
			AsyncTask<String, String, String> {
		final ProgressDialog progress;
		private final String title = getString(R.string.app_name);
		private final int position;
		private String sms = "";
		private final Context context;

		public DeleteCollectMarketInfo(int position, Context context) {
			this.context = context;
			this.progress = new ProgressDialog(getActivity());
			this.progress.setMessage(getResources()
					.getString(R.string.waitting));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
			this.position = position;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {

			try {

				BCCSGateway input = new BCCSGateway();

				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_deleteCollectedObject");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:deleteCollectedObject>");
				rawData.append("<collectInput>");
				HashMap<String, String> param = new HashMap<>();
				HashMap<String, String> paramToken = new HashMap<>();
				JobDal mDal = new JobDal(getActivity());
				mDal.open();
				Log.e("TAG", "TEN ::: " + lstObjs.get(position).getValue());
				ArrayList<CollectedObjectInfo> arrObject = new ArrayList<>();
				arrObject = mDal.getListDetailsCollectedInfo(
						Define.collected_object_info, lstObjs.get(position)
								.getId(), Session.loginUser.getStaffCode());
				mDal.close();
				for (CollectedObjectInfo item : arrObject) {
					Log.e("TAG", "TEN ::: " + item.getValue());
					// Tao request gui len serve

					HashMap<String, String> rawDataItem = new HashMap<>();
					// rawDataItem.put("areaCode", aObj.getAreaCode());

					String value = item.getValue();
					value = StringUtils.xmlEscapeText(value);

					rawDataItem.put("id", lstObjs.get(position).getId());
					rawDataItem.put("groupId", item.getGroupId());
					rawDataItem.put("value", value);
					param.put("lstCollected", input.buildXML(rawDataItem));
					rawData.append(input.buildXML(param));

				}

				paramToken.put("token", Session.getToken());

				rawData.append(input.buildXML(paramToken));
				rawData.append("</collectInput>");
				rawData.append("</ws:deleteCollectedObject>");

				Log.i("LOG_TAG", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG_TAG", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,getActivity(),"mbccs_deleteCollectedObject");
				Log.i("LOG", response);
				CommonOutput output = input.parseGWResponse(response);
				if (!output.getError().equals("0")) {
					Log.e("LOI", "LOI " + output.getDescription());
					sms = output.getDescription();
					return Constant.ERROR_CODE;
				}

				String original = output.getOriginal();

				VSAMenuHandler handler = (VSAMenuHandler) CommonActivity
						.parseXMLHandler(new VSAMenuHandler(), original);
				output = handler.getItem();
				if (!output.getErrorCode().equals("0")) {
					Log.e("LOI", "lOI " + output.getDescription());
					sms = output.getDescription();
					return output.getErrorCode();
				}

				if (output.getToken() != null && !output.getToken().isEmpty()) {
					Session.setToken(output.getToken());
				}
				return Constant.SUCCESS_CODE;

			} catch (Exception e) {

				e.printStackTrace();
				sms = getResources().getString(R.string.exception)
						+ e.toString();
				return Constant.ERROR_CODE;
			}

		}

		@Override
		protected void onPostExecute(String result) {

			Dialog dialog = null;
            switch (result) {
                case Constant.SUCCESS_CODE:
                    GetMaxOraRowScnDal dal = new GetMaxOraRowScnDal(context);
                    try {

                        dal.open();
                        OjectSyn obSyn = new OjectSyn("collected_object_info",
                                dal.getMaxOrarow("max_ora_rowscn",
                                        "collected_object_info"));
                        SynchronizationOneData synData = new SynchronizationOneData(
                                getActivity(), obSyn, false);

                        synData.execute();

                        // lstObjs.remove(position);
                        lstObjs.remove(lstObjs.get(position));
                        Log.e("TAG", "Vi tri = " + position + " lstObjs = "
                                + lstObjs.size());
                        //mAdapter.notifyDataSetChanged();

                        mAdapter = new AdapterCollectedInfo(getActivity(), lstObjs);
                        lvCriteria.setAdapter(mAdapter);
                        mAdapter.setmClickViewDetailListener(inStance);
                        mAdapter.setmOnButtonListener(inStance);

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (dal != null) {
                            dal.close();
                        }
                    }
                    dialog = CommonActivity.createAlertDialog(getActivity(),
                            getResources().getString(R.string.delete_updatesucess),
                            title);
                    dialog.show();


                    break;
                case Constant.INVALID_TOKEN2:
                    CommonActivity.BackFromLogin(getActivity(), sms, "");

                    break;
                default:

                    dialog = CommonActivity.createAlertDialog(getActivity(), sms,
                            title);
                    dialog.show();
                    break;
            }

			super.onPostExecute(result);
			progress.dismiss();

		}

	}

	@Override
	public void onTaskCompleted() {
		// reload data
		Bundle mBundle = getArguments();
		AreaObj mAreaObj = (AreaObj) mBundle.getSerializable(Define.KEY_AREA);
		lstObjs = mJobDal.getListCollectedInfo(TABLE_NAME,
				mObjectDetailGroup.getId(), mAreaObj.getAreaCode());
		mAdapter.notifyDataSetChanged();
	}

}
