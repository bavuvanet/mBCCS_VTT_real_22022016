package com.viettel.bss.viettelpos.v4.contract.adapter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.AccountDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustIdentityDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ListContractAdapter extends ArrayAdapter<AccountDTO> {

	private final Activity activity;

	public ListContractAdapter(Activity activity, List<AccountDTO> arraylist) {
		super(activity, R.layout.item_contract, arraylist);
		this.activity = activity;

	}

	private class ViewHolder {
		TextView txtSoHopDong;
		TextView txtThuebaoDaiDien;
		TextView txtNgayKy;
		TextView txtTrangThai;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		AccountDTO accountDTO = getItem(position);

		ViewHolder holder;
		View mView = view;

		if (mView == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			mView = inflater.inflate(
					R.layout.item_contract, viewGroup,
					false);
			holder = new ViewHolder();

			holder.txtSoHopDong = (TextView) mView.findViewById(R.id.txtSoHopDong);
			holder.txtThuebaoDaiDien = (TextView) mView.findViewById(R.id.txtThuebaoDaiDien);
			holder.txtNgayKy = (TextView) mView.findViewById(R.id.txtNgayKy);
			holder.txtTrangThai = (TextView) mView.findViewById(R.id.txtTrangThai);


			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}

		holder.txtSoHopDong.setText(accountDTO.getAccountNo());
		holder.txtThuebaoDaiDien.setText(accountDTO.getIsdn());
		if(!CommonActivity.isNullOrEmpty(accountDTO.getSignDate())){

			holder.txtNgayKy.setText(StringUtils.convertDate(accountDTO.getSignDate()));
		}

//		holder.txtNgayKy.setText(accountDTO.getSignDate());



		holder.txtTrangThai.setText(accountDTO.getStatusText());


		return mView;
	}
}
