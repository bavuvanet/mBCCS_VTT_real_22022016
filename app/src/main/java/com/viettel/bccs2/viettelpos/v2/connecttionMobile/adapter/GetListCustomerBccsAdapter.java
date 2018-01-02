package com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter;

import java.util.List;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustIdentityDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GetListCustomerBccsAdapter extends ArrayAdapter<CustIdentityDTO> {

	private final Activity activity;
	private View.OnClickListener imageListenner;

	public GetListCustomerBccsAdapter(Activity activity,
			List<CustIdentityDTO> arraylist, View.OnClickListener imageListenner) {
		super(activity, R.layout.connection_customer_editable_item, arraylist);
		this.activity = activity;
		this.imageListenner = imageListenner;
		if (this.imageListenner == null) {
			Log.d(this.getClass().getName(), "this.imageListenner NULL");
		} else {

		}
	}

	private class ViewHolder {
		TextView txtname;
		TextView txtsocmt;
		TextView txtdatebirh;
		TextView txtdiachi;
		ImageView imagecheck;
		TextView txtngaycap;
		TextView tvIdIssuePlace;
		ImageView imgEdit;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		CustIdentityDTO custIdentityDTO = getItem(position);

		ViewHolder holder;
		View mView = view;

		if (mView == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			mView = inflater.inflate(
					R.layout.connection_customer_editable_item, viewGroup,
					false);
			holder = new ViewHolder();
			holder.imagecheck = (ImageView) mView
					.findViewById(R.id.chk_customer);
			holder.txtname = (TextView) mView.findViewById(R.id.txtnameCus);
			holder.txtsocmt = (TextView) mView.findViewById(R.id.txtsocmt);
			holder.txtdatebirh = (TextView) mView
					.findViewById(R.id.txtdatebirth);
			holder.txtdiachi = (TextView) mView.findViewById(R.id.txtdiachi);
			holder.txtngaycap = (TextView) mView.findViewById(R.id.txtngaycap);
			holder.tvIdIssuePlace = (TextView) mView
					.findViewById(R.id.tvIdIssuePlace);
			holder.imgEdit = (ImageView) mView.findViewById(R.id.imgEdit);

			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}

		if (custIdentityDTO != null && custIdentityDTO.getCustomer() != null) {
			holder.txtname.setText(custIdentityDTO.getCustomer().getName());
			holder.txtsocmt.setText(activity.getResources().getString(
					R.string.socmtCus)
					+ " " + custIdentityDTO.getIdNo());

			if (!CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer()
					.getBirthDate())) {

//				if(!CommonActivity.isNullOrEmpty(birthDate)){
//					if(birthDate.length() >= 10){
//						if(birthDate.substring(0, 10).split("-").length >= 3){
//							birthDate = StringUtils.convertDate(birthDate);
//						}
//					}
//				}
				if(custIdentityDTO.getCustomer().getCustId() != null){
					holder.txtdatebirh.setText(activity.getResources().getString(
							R.string.ngaysinh)
							+ " : " + StringUtils.convertDate(custIdentityDTO.getCustomer().getBirthDate()));
				}else{
					holder.txtdatebirh.setText(activity.getResources().getString(
							R.string.ngaysinh)
							+ " : " + custIdentityDTO.getCustomer().getBirthDate());
				}
				
			

			}
			if (!CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer()
					.getAddress())) {
				holder.txtdiachi.setText(activity.getResources().getString(
						R.string.diachi)
						+ " : " + custIdentityDTO.getCustomer().getAddress());
			}

			if (custIdentityDTO.getCustomer().getCustId() != null && !CommonActivity.isNullOrEmpty(custIdentityDTO.getIdIssueDate())) {
				holder.txtngaycap.setText(activity.getResources().getString(
						R.string.ngaycapseach)
						+ " : " + StringUtils.convertDate(custIdentityDTO.getIdIssueDate()));
			}else{
				holder.txtngaycap.setText(activity.getResources().getString(
						R.string.ngaycapseach)
						+ " : " + custIdentityDTO.getIdIssueDate());
			}
			if (!CommonActivity
					.isNullOrEmpty(custIdentityDTO.getIdIssuePlace())) {
				holder.tvIdIssuePlace.setText(activity.getResources()
						.getString(R.string.txt_supply)
						+ " "
						+ custIdentityDTO.getIdIssuePlace());
			}

			if (imageListenner != null && custIdentityDTO.getCustomer() != null && !CommonActivity.isNullOrEmpty(custIdentityDTO.getCustomer().getCustId())) {

//				menu.tdttkh
				SharedPreferences preferences = activity.getSharedPreferences(Define.PRE_NAME, Activity.MODE_PRIVATE);

				String name = preferences.getString(Define.KEY_MENU_NAME, "");
				if(name.contains(";menu.tdttkh;")){
					holder.imgEdit.setVisibility(View.VISIBLE);
					holder.imgEdit.setTag(custIdentityDTO);
					holder.imgEdit.setOnClickListener(imageListenner);
				}else{
					holder.imgEdit.setVisibility(View.GONE);
				}

			} else {
				holder.imgEdit.setVisibility(View.GONE);
			}
		}
		if (custIdentityDTO.isIscheckCus()) {
			holder.imagecheck.setBackgroundResource(R.drawable.gachnoselected);
		} else {
			holder.imagecheck.setBackgroundResource(R.drawable.gachnoselect);
		}
		return mView;
	}
}
