package com.viettel.bss.viettelpos.v4.customer.adapter;

import java.util.ArrayList;
import java.util.List;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.customer.object.CorporationBO;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GetCorporationBOAdapter extends BaseAdapter {

	private Context mContext;
	private List<CorporationBO> arrCorporationBO = new ArrayList<CorporationBO>();;
	private List<CorporationBO> arraylist = new ArrayList<CorporationBO>();

	private OnDeleteCorporationBO onDeleteCorporationBO;
	private OnEditCorporationBO onEditCorporationBO;

	private OnAceptCorporationBO onAceptCorporationBO;

	public interface OnAceptCorporationBO {
		public void onAceptCorporationBO(CorporationBO corporationBO);
	}

	public interface OnDeleteCorporationBO {
		public void onDeleteCorporationBO(CorporationBO corporationBO);
	}

	public interface OnEditCorporationBO {
		public void onEditCorporationBO(CorporationBO corporationBO);
	}

	public GetCorporationBOAdapter(List<CorporationBO> arr, Context context, OnEditCorporationBO onEditCorporationBO,
			OnDeleteCorporationBO onDeleteCorporationBO, OnAceptCorporationBO onAceptCorporationBO) {
		if (arr != null) {
			this.arraylist.addAll(arr);
			this.arrCorporationBO.addAll(arr);
		}
		this.onDeleteCorporationBO = onDeleteCorporationBO;
		this.onEditCorporationBO = onEditCorporationBO;
		this.onAceptCorporationBO = onAceptCorporationBO;

		mContext = context;
	}

	@Override
	public int getCount() {
		return arrCorporationBO.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arrCorporationBO.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	class ViewHolder {
		TextView txtnamedn;
		TextView txtsogpkd;
		TextView txtmst;
		TextView txtdatebirth;
		TextView txtdiachi;
		TextView txtstatus;
		LinearLayout imgEdit;
		LinearLayout imgDelete;
		LinearLayout imgCheckOrNot;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder;
		View mView = arg1;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_comporation, arg2, false);
			holder = new ViewHolder();
			holder.txtnamedn = (TextView) mView.findViewById(R.id.txtnamedn);
			holder.txtsogpkd = (TextView) mView.findViewById(R.id.txtsogpkd);
			holder.txtmst = (TextView) mView.findViewById(R.id.txtmst);
			holder.txtdatebirth = (TextView) mView.findViewById(R.id.txtdatebirth);
			holder.txtdiachi = (TextView) mView.findViewById(R.id.txtdiachi);
			holder.imgEdit = (LinearLayout) mView.findViewById(R.id.imgEdit);
			holder.imgDelete = (LinearLayout) mView.findViewById(R.id.imgDelete);

			holder.imgCheckOrNot = (LinearLayout) mView.findViewById(R.id.imgCheckOrNot);
			holder.txtstatus = (TextView) mView.findViewById(R.id.txtstatus);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}

		final CorporationBO corporationBO = this.arrCorporationBO.get(arg0);
		holder.txtnamedn.setText(corporationBO.getCorporationName());
		holder.txtsogpkd.setText(corporationBO.getCorpBussinessLiensce());
		holder.txtmst.setText(corporationBO.getCorpTaxCode());
		if (!CommonActivity.isNullOrEmpty(corporationBO.getStatusStr())) {
			holder.txtstatus.setText(corporationBO.getStatusStr());
		}

		// if(!CommonActivity.isNullOrEmptyArray(corporationBO.getLstCategoryInfor())){
		// for (CorporationCategoryBO corporationCategoryBO :
		// corporationBO.getLstCategoryInfor()) {
		// if("FOUND_DATE".equals(corporationCategoryBO.getInforCode())){
		holder.txtdatebirth.setText(StringUtils.convertDate(corporationBO.getEstablishDate()));
		// }
		// if("DC".equals(corporationCategoryBO.getInforCode())){
		holder.txtdiachi.setText(corporationBO.getAddress());
		// }
		// }
		// }

		SharedPreferences preferences = mContext.getSharedPreferences(Define.PRE_NAME, Activity.MODE_PRIVATE);

		String name = preferences.getString(Define.KEY_MENU_NAME, "");

		if (name.contains(";menu.update.corporationcus;")) {
			holder.imgEdit.setVisibility(View.VISIBLE);
		} else {
			holder.imgEdit.setVisibility(View.GONE);
		}

		if (name.contains(";menu.delete.corporationcus;")) {
			holder.imgDelete.setVisibility(View.VISIBLE);
		} else {
			holder.imgDelete.setVisibility(View.GONE);
		}
		if (name.contains(";menu.approve.corporationcus;")) {
			holder.imgCheckOrNot.setVisibility(View.VISIBLE);
		} else {
			holder.imgCheckOrNot.setVisibility(View.GONE);
		}

		holder.imgEdit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				onEditCorporationBO.onEditCorporationBO(corporationBO);
			}
		});

		holder.imgDelete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				onDeleteCorporationBO.onDeleteCorporationBO(corporationBO);
			}
		});

		holder.imgCheckOrNot.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				onAceptCorporationBO.onAceptCorporationBO(corporationBO);
			}
		});

		return mView;
	}

}
