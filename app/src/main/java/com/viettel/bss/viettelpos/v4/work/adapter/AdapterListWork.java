package com.viettel.bss.viettelpos.v4.work.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.work.object.WorkObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("ResourceAsColor")
public class AdapterListWork extends BaseAdapter {
	private ArrayList<WorkObject> lisWorkObjects = new ArrayList<>();
	private final Context mContext;

	public AdapterListWork(ArrayList<WorkObject> arr, Context context) {
		this.lisWorkObjects = arr;
		mContext = context;
	}

	@Override
	public int getCount() {

		return lisWorkObjects.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	static class ViewHolder {

		TextView txtchitiet;
		ImageView iconItem;
		TextView txttime;
	}

	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		String selectDateNow = new StringBuilder().append(year).append("-")
				.append(month).append("-").append(day).toString();
		Log.d("selectDateNow", selectDateNow);
		Calendar cal = Calendar.getInstance();
		int yearTerm = cal.get(Calendar.YEAR);
		int monthTerm = cal.get(Calendar.MONTH) + 1;
		int dayTerm = cal.get(Calendar.DAY_OF_MONTH) + 1;
		String selectDateTerm = String.valueOf(yearTerm) +
                "-" + monthTerm + "-" + dayTerm;
		Log.d("selectDateTerm", selectDateTerm);
		Date dateNow = null;
		Date dateNowTerm = null;

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			dateNow = sdf.parse(selectDateNow);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			dateNowTerm = sdf.parse(selectDateTerm);
		} catch (Exception e) {
			e.printStackTrace();
		}

		final WorkObject workObject = this.lisWorkObjects.get(position);
		View mView = arg1;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.update_job_item, arg2, false);
			holder = new ViewHolder();

			holder.txtchitiet = (TextView) mView
					.findViewById(R.id.textdetailitemwork);
			holder.iconItem = (ImageView) mView.findViewById(R.id.icon_item);
			holder.txttime = (TextView) mView.findViewById(R.id.texttime);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		Date dateend = null;
		String[] splitDate = {};
		String startdate = "";
		String dateStartSp = workObject.getStartDate();
		Log.d("dateStartSp", dateStartSp);
		String[] splitDateEnd = {};
		String startdateEnd = "";
		String dateStartEnd = workObject.getEndDate();
		Log.d("dateStartEnd", dateStartEnd);
		if (dateStartSp != null && !dateStartSp.isEmpty()) {
			splitDate = dateStartSp.split("-");
			if (splitDate.length == 3) {
				startdate = splitDate[2] + "/" + splitDate[1] + "/"
						+ splitDate[0];
			}
		}
		if (dateStartEnd != null && !dateStartEnd.isEmpty()) {
			splitDateEnd = dateStartEnd.split("-");
			if (splitDate.length == 3) {
				startdateEnd = splitDateEnd[2] + "/" + splitDateEnd[1] + "/"
						+ splitDateEnd[0];
			}
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Log.d("dateStartEnd", dateStartEnd);
			if (dateStartEnd != null && !dateStartEnd.isEmpty()) {
				dateend = sdf.parse(dateStartEnd);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		// check cong viec qua han(backlog) (tr.schedule_date_to) <= date
		// ('now')
		if (dateend != null) {
			holder.txtchitiet.setText(workObject.getWorkid() + " - "
					+ workObject.getNameWork());
			holder.txttime.setText(startdate + " - " + startdateEnd);
			if (dateend.before(dateNow) || dateend.compareTo(dateNow) == 0) {
				holder.iconItem.setVisibility(View.VISIBLE);
				holder.iconItem
						.setBackgroundResource(R.drawable.quahanicon);
			} else {
				// ===check cong viec toi han(term) (tr.schedule_date_to) = date
				// ('now', '+1 day')
				if (dateend.compareTo(dateNowTerm) == 0) {
					holder.iconItem.setVisibility(View.VISIBLE);
					holder.iconItem.setBackgroundResource(R.drawable.toihanicon);
				} else {
					holder.iconItem.setVisibility(View.INVISIBLE);
				}
			}

		} else {
			holder.txtchitiet.setText(workObject.getWorkid() + " - "
					+ workObject.getNameWork());
			holder.txttime.setText(startdate + " - " + startdateEnd);
			holder.iconItem.setVisibility(View.INVISIBLE);
		}

		return mView;
	}

}
