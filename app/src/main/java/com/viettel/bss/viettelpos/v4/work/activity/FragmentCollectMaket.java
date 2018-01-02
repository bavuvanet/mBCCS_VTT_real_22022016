package com.viettel.bss.viettelpos.v4.work.activity;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.dal.AreaDal;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.work.adapter.AdapterArea;
import com.viettel.bss.viettelpos.v4.work.object.MonthObject;

import java.util.ArrayList;

public class FragmentCollectMaket extends Fragment implements
		OnItemClickListener, OnClickListener {
    private ArrayList<MonthObject> arrayListMonth = new ArrayList<>();

	private AdapterArea mAdapterArea;
	private Button btnHome;
	private Dialog dialogMonth = null;
	private EditText searchLocation;
	private String inputSearch;
    private final ArrayList<AreaObj> lstAreaObjs = new ArrayList<>();

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.d("TAG", "onActivityCreated FragmentChannelManager");
        MainActivity.getInstance().setTitleActionBar(R.string.collect_maket);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		Log.d("TAG", "onAttach FragmentChannelManager");
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d("TAG", "onCreateView FragmentChannelManager");
		View mView = inflater.inflate(R.layout.layout_collect_maket, container,
				false);
		LoadMore mLoadMore = new LoadMore(getActivity());
		mLoadMore.execute();
		unit(mView);

		return mView;
	}

	@Override
	public void onDestroy() {
		Log.d("TAG", "onDestroy FragmentChannelManager");
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		Log.d("TAG", "onDestroyView FragmentChannelManager");

		super.onDestroyView();
	}

	@Override
	public void onDetach() {
		Log.d("TAG", "onDetach FragmentChannelManager");
		// TODO Auto-generated method stub
		super.onDetach();
	}

	@Override
	public void onPause() {
		Log.d("TAG", "onPause FragmentChannelManager");
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onResume() {
		Log.d("TAG", "onResume FragmentChannelManager");
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onStart() {
		Log.d("TAG", "onStart FragmentChannelManager");
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onStop() {
		Log.d("TAG", "onStop FragmentChannelManager");
		// TODO Auto-generated method stub
		super.onStop();
	}

	private void unit(View v) {
		try {
            ListView lvArea = (ListView) v.findViewById(R.id.lv_collect_maket);
            AreaDal dal = new AreaDal(getActivity());
			searchLocation = (EditText) v.findViewById(R.id.searchLocation);
			searchLocation.addTextChangedListener(new TextWatcher() {
				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					String inputSearch = searchLocation.getText().toString();
					Log.e("TAG", "inputSearch = " + inputSearch);
					if (inputSearch != null && inputSearch != "") {
						new SearchAsyn(getActivity()).execute(inputSearch);
					}

				}

			});
			// lstAreaObjs = dal.getAllArea1();
			mAdapterArea = new AdapterArea(getActivity(), lstAreaObjs);
			lvArea.setAdapter(mAdapterArea);
			lvArea.setOnItemClickListener(this);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private class SearchAsyn extends AsyncTask<String, String, ArrayList<AreaObj>> {
		private final Context mContext;

		public SearchAsyn(Context mContext) {
			super();
			this.mContext = mContext;
		}

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
		}

		@Override
		protected ArrayList<AreaObj> doInBackground(String... params) {

			try {
				AreaDal mdal = new AreaDal(mContext);
				mdal.open();
				ArrayList<AreaObj> tmp = mdal.getAllArea1(params[0]);
				mdal.close();
				return tmp;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(final ArrayList<AreaObj> result) {
			try {
				if (result != null) {

					if (lstAreaObjs.size() > 0)
						lstAreaObjs.clear();

					for (int i = 0; i < result.size(); i++) {

						AreaObj mAreaObj = result.get(i);
						Log.i("TAG", "::" + mAreaObj.getName());
						lstAreaObjs.add(mAreaObj);
					}

					mAdapterArea.notifyDataSetChanged();

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

	private class LoadMore extends AsyncTask<Void, Void, ArrayList<AreaObj>> {
		private final Context mContext;

		public LoadMore(Context mContext) {
			super();
			this.mContext = mContext;
		}

		@Override
		protected ArrayList<AreaObj> doInBackground(Void... params) {

			try {
				AreaDal mdal = new AreaDal(mContext);
				mdal.open();
				ArrayList<AreaObj> tmp = mdal.getAllArea1(null);
				mdal.close();
				return tmp;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(final ArrayList<AreaObj> result) {
			if (result != null) {
				if (lstAreaObjs.size() > 0) {
					lstAreaObjs.clear();
				}
				for (int i = 0; i < result.size(); i++) {

					AreaObj mAreaObj = result.get(i);
					Log.i("TAG", "::" + mAreaObj.getName());
					lstAreaObjs.add(mAreaObj);
				}
				mAdapterArea.notifyDataSetChanged();
			}
			super.onPostExecute(result);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Bundle mBundle = new Bundle();
		mBundle.putSerializable(Define.KEY_AREA, lstAreaObjs.get(arg2));
		FragmentCriteria mFragmentCriteria = new FragmentCriteria();
		mFragmentCriteria.setArguments(mBundle);
		ReplaceFragment.replaceFragment(getActivity(), mFragmentCriteria, true);

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {

		case R.id.btnHome:
			CommonActivity.callphone(getActivity(), Constant.phoneNumber);
//			FragmentMenuHome fragmentMenuHome = new FragmentMenuHome();
//			ReplaceFragment.replaceFragment(getActivity(), fragmentMenuHome,
//					true);
			break;
		case R.id.relaBackHome:
			getActivity().onBackPressed();
			break;
		}
	}

}
