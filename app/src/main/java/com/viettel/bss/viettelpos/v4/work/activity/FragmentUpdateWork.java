package com.viettel.bss.viettelpos.v4.work.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
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
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.dialog.FixedHoloDatePickerDialog;
import com.viettel.bss.viettelpos.v4.work.adapter.AdapterListWork;
import com.viettel.bss.viettelpos.v4.work.dal.JobDal;
import com.viettel.bss.viettelpos.v4.work.object.WorkObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("ValidFragment")
public class FragmentUpdateWork extends Fragment
		implements
			OnItemClickListener,
			OnClickListener {

	private Button btnHome;
    private TextView txtstartdate, txtenddate;
	private ListView listWork;
	public AdapterListWork lisAdapterListWork = null;
	ArrayList<WorkObject> lisWorkObjects = new ArrayList<>();
	private Date datestart = null;
	private Date dateend = null;
	public static FragmentUpdateWork instance = null;
	private String selectDateTo = "";
	private String selectDateEnd = "";
	private String slectDateToEnd = "";// nam/thang/ngay
	private String slectDateTo = "";// "nam-thang-ngay"
	private Integer checkBunder = 0;
	SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/mm/yyyy");
	SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-mm-dd");
	@Override
	public void onResume() {
        MainActivity.getInstance().setTitleActionBar(R.string.updateWork);

		if(lisWorkObjects.size() > 0 && lisWorkObjects != null){
			lisAdapterListWork = new AdapterListWork(lisWorkObjects,
					getActivity());
			listWork.setAdapter(lisAdapterListWork);
			lisAdapterListWork.notifyDataSetChanged();
		}
		super.onResume();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.d("TAG", "onActivityCreated FragmentUpdateWork");

		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		Log.d("TAG", "onAttach FragmentUpdateWork");
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d("TAG", "onCreateView FragmentUpdateWork");
		Bundle bundle = getArguments();
		if(bundle != null){
			checkBunder = bundle.getInt(Define.KEY_JOB);
			Log.d("checkBunder", ""+checkBunder);
		}
		View mView = inflater.inflate(R.layout.update_job_layout, container,
				false);
		unit(mView);
		return mView;
	}
	private void unit(View view) {
		instance = this;
		final Calendar c = Calendar.getInstance();
		//c.add(Calendar.MONTH, 1);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
        Button btntimkiem = (Button) view.findViewById(R.id.btnsearch);
		txtstartdate = (TextView) view.findViewById(R.id.txtstartdate);
		txtenddate = (TextView) view.findViewById(R.id.txtenddate);
		listWork = (ListView) view.findViewById(R.id.listwork);
		slectDateTo = "1" + "/" +
                month + "/" + year + " ";
		Log.d("slectDateTo", slectDateTo);
		selectDateTo = String.valueOf(year) + "-" +
                month + "-" + 01;
		Log.d("selectDateTo", selectDateTo);
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			datestart = sdf.parse(selectDateTo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		txtstartdate.setText(slectDateTo);
		// ===== set ngay for end ===========
		slectDateToEnd = String.valueOf(day) + "/" +
                month + "/" + year + " ";
		Log.d("slectDateTo", slectDateToEnd);
		txtenddate.setText(slectDateToEnd);
		selectDateEnd = String.valueOf(year) + "-" +
                month + "-" + day;
		Log.d("selectDateEnd", selectDateEnd);
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			dateend = sdf.parse(selectDateEnd);
		} catch (Exception e) {
			e.printStackTrace();
		}

		listWork.setOnItemClickListener(this);
		btntimkiem.setOnClickListener(this);
		
		if(checkBunder.compareTo(2) == 0){
			try{
				JobDal jobDal = new JobDal(getActivity());
				jobDal.open();
				lisWorkObjects = jobDal.getListAllJob();
				lisAdapterListWork = new AdapterListWork(lisWorkObjects, getActivity());
				listWork.setAdapter(lisAdapterListWork);
				jobDal.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
//		if (lisWorkObjects != null && lisWorkObjects.size() > 0) {
//			lisAdapterListWork = new AdapterListWork(lisWorkObjects,
//					getActivity());
//			listWork.setAdapter(lisAdapterListWork);
//		}
		txtstartdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				DialogFragment datePickerFragment = new DatePickerCustomFragment();
				datePickerFragment.show(getActivity().getFragmentManager(),
						"datepicker");

			}
		});
		txtenddate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				DialogFragment datePickerFragmentEnd = new DatePickerCustomFragmentEnd();
				datePickerFragmentEnd.show(getActivity().getFragmentManager(),
						"datepicker");
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btnsearch :
				
				if (datestart != null && dateend != null) {
					if (datestart.after(dateend)) {
						if(lisWorkObjects.size() > 0 && lisAdapterListWork != null){
							lisWorkObjects.clear();
							lisAdapterListWork.notifyDataSetChanged();
						}
						Toast.makeText(
								getActivity(),
								getResources().getString(
										R.string.checktimeupdatejob),
								Toast.LENGTH_SHORT).show();
					} else if (datestart.compareTo(dateend) == 0) {
						// ==========tim kiem cong viec =======
						
						
						if(CommonActivity.isNetworkConnected(getActivity())){
							SearchListWorkAsyn seListWorkAsyn = new SearchListWorkAsyn(
									getActivity(), txtstartdate.getText()
											.toString(), txtenddate.getText()
											.toString());
							seListWorkAsyn.execute();	
						}else{
							Dialog dialog = CommonActivity.createAlertDialog(getActivity(), getResources().getString(R.string.errorNetwork), getResources().getString(R.string.app_name));
							dialog.show();
						}
				
					}
				} else if (txtstartdate.getText().toString().isEmpty()
						&& txtenddate.getText().toString().isEmpty()) {
					Toast.makeText(getActivity(),
							getResources().getString(R.string.ischeckinfo),
							Toast.LENGTH_SHORT).show();
				} else if (txtenddate.getText().toString().isEmpty()) {
					Toast.makeText(getActivity(),
							getResources().getString(R.string.notendate),
							Toast.LENGTH_SHORT).show();
				} else if (txtstartdate.getText().toString().isEmpty()) {
					Toast.makeText(getActivity(),
							getResources().getString(R.string.notstartdate),
							Toast.LENGTH_SHORT).show();
				}
				if (!txtstartdate.getText().toString().isEmpty()
						&& !txtenddate.getText().toString().isEmpty()
						&& datestart.before(dateend)) {
					
					// ==========tim kiem cong viec =======
					if(CommonActivity.isNetworkConnected(getActivity())){
						SearchListWorkAsyn seListWorkAsyn = new SearchListWorkAsyn(
								getActivity(), txtstartdate.getText()
										.toString(), txtenddate.getText()
										.toString());
						seListWorkAsyn.execute();	
					}else{
						Dialog dialog = CommonActivity.createAlertDialog(getActivity(), getResources().getString(R.string.errorNetwork), getResources().getString(R.string.app_name));
						dialog.show();
					}
				}

				break;

			case R.id.btnHome :
				CommonActivity.callphone(getActivity(), Constant.phoneNumber);
				break;
			case R.id.relaBackHome :
				getActivity().onBackPressed();
				break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Bundle mBundle = new Bundle();
		mBundle.putSerializable(Define.KEY_WORKOBJECT, lisWorkObjects.get(arg2));
		FragmentDetailsWork mFragmentDetailsWork = new FragmentDetailsWork();
		mFragmentDetailsWork.setArguments(mBundle);
		ReplaceFragment.replaceFragment(getActivity(), mFragmentDetailsWork,
				true);
	}

	// ============== asyn tim kiem tu lieu ============
	public class SearchListWorkAsyn
			extends
				AsyncTask<String, Void, ArrayList<WorkObject>> {
		final ProgressDialog progress;
		private Context context = null;
		String startdate = "";
		String endDate = "";

		public SearchListWorkAsyn(Context context, String startdate,
				String endDate) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.startdate = startdate;
			this.endDate = endDate;
			this.progress.setMessage(context.getResources().getString(
					R.string.searching));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<WorkObject> doInBackground(String... params) {
			ArrayList<WorkObject> lisWorkObjects = new ArrayList<>();
			JobDal jobDal = new JobDal(context);
			jobDal.open();
			try {
				lisWorkObjects = jobDal.getListPonitJob(startdate, endDate);
				jobDal.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return lisWorkObjects;
		}

		@Override
		protected void onPostExecute(ArrayList<WorkObject> result) {
			progress.dismiss();
			if (result != null) {
				if (result.size() > 0) {
					// =============set data for list view ===========
					lisWorkObjects = result;
					lisAdapterListWork = new AdapterListWork(lisWorkObjects,
							context);
					listWork.setAdapter(lisAdapterListWork);
					lisAdapterListWork.notifyDataSetChanged();
				} else {
					// =========ko co data ===================	
					lisWorkObjects = result;
					lisAdapterListWork = new AdapterListWork(result,
							context);
					listWork.setAdapter(lisAdapterListWork);
				//	lisAdapterListWork.notifyDataSetChanged();
				Dialog dialog = CommonActivity
				.createAlertDialog(getActivity(), getResources().getString(
						R.string.notsearchwork), getResources().getString(R.string.searchwork));
				dialog.show();
					
				}
			}

		}

	}

	public class DatePickerCustomFragmentEnd extends DialogFragment
			implements
				DatePickerDialog.OnDateSetListener {

		public DatePickerCustomFragmentEnd() {
			super();

		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
		//	c.add(Calendar.MONTH, 1);
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			return new FixedHoloDatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			
			slectDateToEnd = String.valueOf(day) + "/" +
                    (month + 1) + "/" + year + " ";
			Log.d("slectDateTo", slectDateToEnd);
			txtenddate.setText(slectDateToEnd);
			selectDateEnd = String.valueOf(year) + "-" +
                    (month + 1) + "-" + day;
			Log.d("selectDateEnd", selectDateEnd);
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				dateend = sdf.parse(selectDateEnd);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@SuppressLint("SimpleDateFormat")
	public class DatePickerCustomFragment extends DialogFragment
			implements
				DatePickerDialog.OnDateSetListener {

		public DatePickerCustomFragment() {
			super();

		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
		//	c.add(Calendar.MONTH, 1);
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			return new FixedHoloDatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {

			slectDateTo = String.valueOf(day) + "/" +
                    (month + 1) + "/" + year + " ";
			Log.d("slectDateTo", slectDateTo);
			selectDateTo = String.valueOf(year) + "-" +
                    (month + 1) + "-" + day;
			Log.d("selectDateTo", selectDateTo);
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				datestart = sdf.parse(selectDateTo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			txtstartdate.setText(slectDateTo);

		}
	}

}
