package com.viettel.bss.viettelpos.v4.work.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.channel.adapter.AdapterOwnerStaff;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.login.object.Staff;
import com.viettel.bss.viettelpos.v4.work.dal.JobDal;

import java.util.ArrayList;

public class FragmentListStaffJobAssignment extends Fragment implements Define,
		OnScrollListener, OnItemClickListener, OnClickListener {

	private ArrayList<Staff> arrayStaff;
	private ListView lvListStaff;
	private AdapterOwnerStaff mAdapterStaff;
	private JobDal mJobDal;

	private boolean loadmore = false, loadOk = false;
	private int pos = 0;
	private LoadmoreStaff mLoadmoreStaff;
	private View footer;
	private View mView;
	private Button btnHome;
	private String inputSearch = null;
	private EditText searchChannel;
	private String inputSearchName;
	private Boolean search = false;
    private Boolean isViewRouter;
	private TextView sms11;

	@Override
	public void onAttach(Activity activity) {

		super.onAttach(activity);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d("TAG1", "onActivityCreated FragmentListChannel ");
		if (!isViewRouter) {
			MainActivity.getInstance().setTitleActionBar(getResources().getString(
					R.string.text_menu_assignment));
		} else {
            MainActivity.getInstance().setTitleActionBar(getResources().getString(
					R.string.showRouter));
		}
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d("TAG1", "onCreateView FragmentListChannel");
        Bundle mBundle = getArguments();
		if (mBundle != null) {
			isViewRouter = (Boolean) mBundle.getSerializable(VIEW_ROUTER);
		}
		if (mView == null) {
			mJobDal = new JobDal(getActivity());

			mView = inflater.inflate(R.layout.list_staff_job_manager,
					container, false);
			Unit(mView, savedInstanceState);
		}
		return mView;
	}

	@Override
	public void onStart() {
		Log.d("TAG1", "onStart FragmentListChannel");
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	@Override
	public void onResume() {
		Log.d("TAG1", "onResume FragmentListChannel");
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onPause() {
		Log.d("TAG1", "onPause FragmentListChannel");
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onStop() {
		Log.d("TAG1", "onStop FragmentListChannel");
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d("TAG1", "onCreate FragmentListChannel");
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		Log.d("TAG1", "onDestroy FragmentListChannel");
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		Log.d("TAG1", "onDetach FragmentListChannel");
		try {
			mJobDal.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		super.onDetach();
	}

	@Override
	public void onDestroyView() {
		Log.d("TAG1", "onDestroyView FragmentListChannel");
		if (mLoadmoreStaff != null)
			mLoadmoreStaff.cancel(true);
		super.onDestroyView();
	}

	@SuppressLint("InflateParams")
    private void Unit(View v, Bundle mBundle) {
		try {
			lvListStaff = (ListView) v.findViewById(R.id.lv_staff_job_manager);

			arrayStaff = new ArrayList<>();
			/*
			 * if (mBundle != null) { arrayStaff =
			 * mBundle.getParcelableArrayList(KEY_LIST_STAFF); }
			 */
//			arrayStaff.clear();
//			arrayStaff = mJobDal.getListStaffManager(Constant.TABLE_STAFF, pos,
//					COUNTLOAD, 14L, inputSearchName);
//			
//			Log.e("TAG", "KICH THUOC =  " + arrayStaff.size());
//			for (Staff item : arrayStaff) {
//				Log.e("TAG", "TEN " + item.getNameStaff());
//			}
			sms11 = (TextView) v.findViewById(R.id.sms11);
			sms11.setVisibility(View.GONE);
			LayoutInflater inflater = getActivity().getLayoutInflater();
			footer = inflater.inflate(R.layout.footer_layout, null, false);
//			lvListStaff.addFooterView(footer, null, false);
			mLoadmoreStaff = new LoadmoreStaff();
			mLoadmoreStaff.execute();
			lvListStaff.setOnScrollListener(this);
			lvListStaff.setOnItemClickListener(this);
			mAdapterStaff = new AdapterOwnerStaff(arrayStaff, getActivity());
			lvListStaff.setAdapter(mAdapterStaff);
			/**
			 * Search
			 */
			searchChannel = (EditText) v.findViewById(R.id.searchStaff);
			
			searchChannel.addTextChangedListener(new TextWatcher() {
				@Override
				public void afterTextChanged(Editable s) {

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {

				}

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					
					if(count + before > 0){
					inputSearchName = searchChannel.getText().toString();
						new SearchAsyn().execute();
					}

				}

			});

		} catch (Exception e) {
			e.printStackTrace();

		}

	}
	private void hideKeyboard() {
		// Check if no view has focus:

		if (searchChannel != null) {
			InputMethodManager inputManager = (InputMethodManager) getActivity()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(searchChannel.getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	private void showKeyboard() {
		// Check if no view has focus:

		if (searchChannel != null) {
			InputMethodManager inputManager = (InputMethodManager) getActivity()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(searchChannel.getWindowToken(),
					InputMethodManager.SHOW_FORCED);
		}
	}

	private class SearchAsyn extends AsyncTask<Void, Void, ArrayList<Staff>> {

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
		}

		@Override
		protected ArrayList<Staff> doInBackground(Void... params) {

			return mJobDal.getListStaffManager(Constant.TABLE_STAFF, pos,
					COUNTLOAD, 14L, inputSearchName);
		}

		@Override
		protected void onPostExecute(final ArrayList<Staff> result) {
			try {
				if (result != null) {

					if (arrayStaff.size() > 0)
						arrayStaff.clear();

					for (int i = 0; i < result.size(); i++) {

						Staff staff = result.get(i);
						Log.i("TAG", "::" + staff.getNameStaff());
						arrayStaff.add(staff);
					}

					if (result.size() < COUNTLOAD) {
						lvListStaff.removeFooterView(footer);
						loadOk = true;
					}

					if (arrayStaff.size() > 0) {
						sms11.setVisibility(View.GONE);
					} else {
						sms11.setVisibility(View.VISIBLE);
					}
					mAdapterStaff.notifyDataSetChanged();

				} else {
					Log.i("TAG", "khong tim duoc du lieu");
				}

				super.onPostExecute(result);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private class LoadmoreStaff extends AsyncTask<Void, Void, ArrayList<Staff>> {

		@Override
		protected ArrayList<Staff> doInBackground(Void... params) {

			return mJobDal.getListStaffManager(Constant.TABLE_STAFF, pos,
					COUNTLOAD, 14L, inputSearchName);
		}

		@Override
		protected void onPostExecute(final ArrayList<Staff> result) {
			if (result != null) {

				for (int i = 0; i < result.size(); i++) {
					Log.e("tag", "result.get(i) = "
							+ result.get(i).getNameStaff());
					arrayStaff.add(result.get(i));
				}
				if (result.size() < COUNTLOAD) {
					lvListStaff.removeFooterView(footer);
					loadOk = true;
				}
				pos = arrayStaff.size() - 1;
				getActivity().runOnUiThread(runnableUdapteAdapter);

			}

			super.onPostExecute(result);
		}
	}

	private final Runnable runnableUdapteAdapter = new Runnable() {
		@Override
		public void run() {
			mAdapterStaff.notifyDataSetChanged();

            loadmore = loadOk;
		}
	};

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {

		int lastitemScreen = firstVisibleItem + visibleItemCount;
		Log.e("", "lastitemScreen = " + lastitemScreen + " totalItemCount "
				+ totalItemCount);
		if ((lastitemScreen == totalItemCount) && (!loadmore)) {
			loadmore = true;
			Log.d("TAG1", "onScroll");
//			mLoadmoreStaff = new LoadmoreStaff();
//			mLoadmoreStaff.execute();
		}

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (arrayStaff.get(arg2) != null) {
			Bundle mBundle = new Bundle();
			mBundle.putSerializable(KEY_STAFF, arrayStaff.get(arg2));
			hideKeyboard();
			if (!isViewRouter) {
				FragmentListSalePoint mListMenuManager = new FragmentListSalePoint();
				mListMenuManager.setArguments(mBundle);
				ReplaceFragment.replaceFragment(getActivity(),
						mListMenuManager, false);
			} else {
				if (CommonActivity.isNetworkConnected(getActivity())) {

					FragmentViewRouterJob viewRouterJob = new FragmentViewRouterJob();
					viewRouterJob.setArguments(mBundle);
					ReplaceFragment.replaceFragment(getActivity(), viewRouterJob,
							false);
				} else {
					String title = getString(R.string.app_name);
					Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
							getResources().getString(R.string.errorNetwork), title);
					dialog.show();
				}
				
			}
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relaBackHome:
			getActivity().onBackPressed();
			break;
		case R.id.btnHome:
			CommonActivity.callphone(getActivity(), Constant.phoneNumber);
			break;
		default:
			break;
		}

	}

}
