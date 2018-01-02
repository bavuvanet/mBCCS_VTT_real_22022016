package com.viettel.bss.viettelpos.v4.sale.adapter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.sale.object.IsdnOwnerObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;



public class AdapterIsdnOwnerObjectIsdn extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private Activity mActivity;
	private ArrayList<IsdnOwnerObject> lstIsdn;
	private OnclickLockIsdn onclickLockIsdn;
	private int LOAD_MORE = 1;
	private int ITEM_VIEW = 2;

	public interface OnclickLockIsdn {
		void onclickLock(IsdnOwnerObject isdnOwnerObject);
	}
	public AdapterIsdnOwnerObjectIsdn(Activity activity,ArrayList<IsdnOwnerObject> arrIsdn ,OnclickLockIsdn onclickLockIsdn) {
		this.mActivity = activity;
		this.lstIsdn = arrIsdn;
		this.onclickLockIsdn = onclickLockIsdn;
	}

	@Override
	public int getItemViewType(int position) {
		return lstIsdn.get(position) == null ? LOAD_MORE : ITEM_VIEW;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = null;
		if(viewType == ITEM_VIEW) {
			view = LayoutInflater.from(parent.getContext())
					.inflate(R.layout.item_isdn_owner, parent, false);
			return new ViewHolder(view);
		} else {
			view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_item, parent, false);
			return new LoadingViewHolder(view);
		}
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {

		final IsdnOwnerObject item = lstIsdn.get(position);
		if(viewHolder instanceof ViewHolder) {
			ViewHolder holder = (ViewHolder) viewHolder;
			if (item != null) {
				if (!CommonActivity.isNullOrEmpty(item.getStockModelName())) {
					holder.tvStockmodelName.setText(item.getStockModelName());
				}
				holder.tv_phoneNumber.setText(item.getIsdn());
				holder.tv_container.setText(item.getOwnerCode() + "-"
						+ item.getOwnerName());
				String statusName = item.getStatus();
				if (statusName.equalsIgnoreCase("1")) {
					holder.tv_status_stockmodel.setText(mActivity.getResources()
							.getString(R.string.isdn_new));
				} else if (statusName.equalsIgnoreCase("2")) {
					holder.tv_status_stockmodel.setText(mActivity.getResources()
							.getString(R.string.isdn_using));

				} else if (statusName.equalsIgnoreCase("3")) {
					holder.tv_status_stockmodel.setText(mActivity.getResources()
							.getString(R.string.isdn_end_use));

				} else if (statusName.equalsIgnoreCase("4")) {
					holder.tv_status_stockmodel.setText(mActivity.getResources()
							.getString(R.string.isdn_start_kit));

				} else if (statusName.equalsIgnoreCase("5")) {
					holder.tv_status_stockmodel.setText(mActivity.getResources()
							.getString(R.string.isdn_lock));

				}
				SharedPreferences preferences = mActivity.getSharedPreferences(
						Define.PRE_NAME, Activity.MODE_PRIVATE);
				String name = preferences.getString(Define.KEY_MENU_NAME, "");
				if (statusName.equalsIgnoreCase("1") || statusName.equalsIgnoreCase("3")) {
					if (name.contains(Constant.VSAMenu.BCCS2IM_QLSO_TRACUUSO_GIUSO)) {
						holder.btn_lockIsdn.setVisibility(View.VISIBLE);
						holder.btn_lockIsdn.setBackgroundResource(R.drawable.ic_lock);
					} else {
						holder.btn_lockIsdn.setVisibility(View.GONE);
					}
				} else {
					if ("5".equalsIgnoreCase(statusName)) {
						holder.btn_lockIsdn.setVisibility(View.VISIBLE);
						holder.btn_lockIsdn.setBackgroundResource(R.drawable.ic_lock_open);
					} else {
						holder.btn_lockIsdn.setVisibility(View.GONE);
					}
				}
			}

			holder.btn_lockIsdn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					onclickLockIsdn.onclickLock(item);
				}
			});
		}


	}


	@Override
	public int getItemCount() {
		return lstIsdn.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		@BindView(R.id.tv_phoneNumber)
		TextView tv_phoneNumber;
		@BindView(R.id.tv_status_stockmodel)
		TextView tv_status_stockmodel;
		@BindView(R.id.tv_container)
		TextView tv_container;
		@BindView(R.id.btn_lockIsdn)
		AppCompatImageButton btn_lockIsdn;
		@BindView(R.id.tvStockmodelName)
		TextView tvStockmodelName;
		public ViewHolder(View v) {
			super(v);
			ButterKnife.bind(this, v);
		}
	}

	public class LoadingViewHolder extends RecyclerView.ViewHolder{

		@BindView(R.id.progressBar)
		ProgressBar progressBar;

		public LoadingViewHolder(View v){
			super(v);
			ButterKnife.bind(this, v);

		}
	}
}
