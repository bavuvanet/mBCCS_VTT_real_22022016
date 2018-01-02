package com.viettel.bss.viettelpos.v4.charge.adapter;

import java.util.List;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.MVerifyBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ReportVerifyAdapter extends ArrayAdapter<MVerifyBean> {
	private final Context context;

	public ReportVerifyAdapter(Context context, List<MVerifyBean> values) {
		super(context, R.layout.item_report_verify, values);
		this.context = context;
	}

	static class ViewHolder {
		TextView staffCode;
		TextView rate;
		TextView totalAssign;
		TextView totalFalse;
		TextView totalInTime;
		TextView totalNoVerify;
		TextView totalNotAssign;
		TextView totalNotVerify;
		TextView totalOutTime;
		TextView totalOverTime;
		TextView totalTrue;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		ViewHolder holder = null;
		if (rowView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.item_report_verify, parent, false);
			holder = new ViewHolder();

			holder.staffCode = (TextView) rowView.findViewById(R.id.staffCode);
			holder.rate = (TextView) rowView.findViewById(R.id.rate);
			holder.totalAssign = (TextView) rowView.findViewById(R.id.totalAssign);
			holder.totalFalse = (TextView) rowView.findViewById(R.id.totalFalse);
			holder.totalInTime = (TextView) rowView.findViewById(R.id.totalInTime);
			holder.totalNoVerify = (TextView) rowView.findViewById(R.id.totalNoVerify);
			holder.totalNotAssign = (TextView) rowView.findViewById(R.id.totalNotAssign);
			holder.totalNotVerify = (TextView) rowView.findViewById(R.id.totalNotVerify);
			holder.totalOutTime = (TextView) rowView.findViewById(R.id.totalOutTime);
			holder.totalOverTime = (TextView) rowView.findViewById(R.id.totalOverTime);
			holder.totalTrue = (TextView) rowView.findViewById(R.id.totalTrue);

			rowView.setTag(holder);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}

		MVerifyBean mVerifyBean = getItem(position);

		holder.staffCode.setText(mVerifyBean.getStaffCode());
		holder.rate.setText(mVerifyBean.getRate());
		holder.totalAssign.setText(mVerifyBean.getTotalAssign());
		holder.totalFalse.setText(mVerifyBean.getTotalFalse());
		holder.totalInTime.setText(mVerifyBean.getTotalInTime());
		holder.totalNoVerify.setText(mVerifyBean.getTotalNoVerify());
		holder.totalNotAssign.setText(mVerifyBean.getTotalNotAssign());
		holder.totalNotVerify.setText(mVerifyBean.getTotalNotVerify());
		holder.totalOutTime.setText(mVerifyBean.getTotalOutTime());
		holder.totalOverTime.setText(mVerifyBean.getTotalOverTime());
		holder.totalTrue.setText(mVerifyBean.getTotalTrue());

		return rowView;
	}
}
