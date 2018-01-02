package com.viettel.bss.viettelpos.v3.connecttionService.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v3.connecttionService.model.ProductCatalogDTO;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class GetServiceTypeAdapter extends BaseAdapter{
	private Context mContext;
	private ArrayList<ProductCatalogDTO> arrPakageVasBeans = new ArrayList<ProductCatalogDTO>();
	private OnChangeSoLuongService onChangeSoLuong;
	
	public OnChangeCheckQuantityService onChangeCheckQuantityService;


	public interface OnChangeCheckQuantityService{
		
		public void onChangeCheckQuantityService(ProductCatalogDTO productCatalogDTO, TextView view );
		
	}
	public interface OnChangeSoLuongService {
		public void onChangeSoluongListener(ProductCatalogDTO productCatalogDTO);
	}
	
	public GetServiceTypeAdapter(ArrayList<ProductCatalogDTO> arr , Context context , OnChangeSoLuongService onChangeSoLuongService , OnChangeCheckQuantityService mOnChangeCheckQuantityService){
	
		this.mContext = context;
		this.arrPakageVasBeans = arr;
		this.onChangeSoLuong = onChangeSoLuongService;
		this.onChangeCheckQuantityService = mOnChangeCheckQuantityService;
	}
	
	@Override
	public int getCount() {
		return arrPakageVasBeans.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arrPakageVasBeans.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	
	private class ViewHolder{
		private TextView textName;
		private CheckBox checkBox;
		private TextView tvQuantitySaling;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		final ViewHolder holder;
		View mView = arg1;
		if(mView == null){
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_grid_service, arg2,
					false);
			holder = new ViewHolder();
			holder.textName = (TextView) mView.findViewById(R.id.textnamePakage);
			holder.checkBox = (CheckBox) mView.findViewById(R.id.checkPakage);
			holder.tvQuantitySaling = (TextView) mView.findViewById(R.id.tvQuantitySaling);
			mView.setTag(holder);
		}else{
			holder = (ViewHolder) mView.getTag();
		}
		final ProductCatalogDTO pakageVasBean = arrPakageVasBeans.get(arg0);
		if(!CommonActivity.isNullOrEmpty(pakageVasBean.getDescription())){
			holder.textName.setText(pakageVasBean.getDescription());
		}else{
			holder.textName.setText(pakageVasBean.getName());
		}

	
		holder.checkBox.setChecked(pakageVasBean.isCheck());
		holder.checkBox.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				onChangeCheckQuantityService.onChangeCheckQuantityService(pakageVasBean, holder.tvQuantitySaling);
			}
		});
		
		if(pakageVasBean.isCheckEnable()){
			holder.checkBox.setEnabled(false);
		}else{
			holder.checkBox.setEnabled(true);
		}

		holder.tvQuantitySaling.setText(pakageVasBean.getQuantity() + "");
		holder.tvQuantitySaling.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				onChangeSoLuong.onChangeSoluongListener(pakageVasBean);
				
			}
		});
		
		
		return mView;
	}

}
