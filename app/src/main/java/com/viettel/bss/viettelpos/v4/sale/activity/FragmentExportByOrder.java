package com.viettel.bss.viettelpos.v4.sale.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.sale.object.BankplusOrderBO;

public class FragmentExportByOrder extends Fragment implements OnClickListener {

    private View view;

	private Button btnHome;
	private TextView txtNameActionBar;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
        MainActivity.getInstance().setTitleActionBar(R.string.sale_by_order);
		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.sale_export_by_order_handler,
					container, false);
			Bundle bundle = getArguments();
			if (bundle == null) {
				return view;
			}
            BankplusOrderBO orderBO = (BankplusOrderBO) bundle.getSerializable("orderBO");
			if (orderBO == null) {
				return view;
			}
            TextView tvChannelCode = (TextView) view.findViewById(R.id.tvChannelCode);
            TextView tvOrderId = (TextView) view.findViewById(R.id.tvOrderId);
			tvOrderId.setText(getResources().getString(R.string.order) + " "
					+ orderBO.getOrderCode());
			tvChannelCode.setText(orderBO.getStaffCode() + " - "
					+ orderBO.getStaffName());
		}
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnHome:
			CommonActivity.callphone(getActivity(), Constant.phoneNumber);
			break;
		case R.id.relaBackHome:
			getActivity().onBackPressed();
			break;
		default:
			break;
		}
	}

}
