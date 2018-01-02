package com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferTypeDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferingDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.ui.NDSpinner;

public class ThongTinHHAdapterBCCS extends BaseAdapter {
	private final ArrayList<ProductOfferTypeDTO> arrCustomerOJs;
	private ArrayList<ProductOfferingDTO> arrayAPStockModelBeanBO;
	private final Context mContext;
	private AdapterSpinerTTHHBCCS mAdapterSpinerTTHH;
	private ViewHolder holder = null;

	private final OnChangeSpinerProduct onChangeSpiner;

	public ThongTinHHAdapterBCCS(ArrayList<ProductOfferTypeDTO> arrCustomerOJs, Context context,
			OnChangeSpinerProduct onChangeSpiner) {
		this.arrCustomerOJs = arrCustomerOJs;
		mContext = context;
		this.onChangeSpiner = onChangeSpiner;
		arrayAPStockModelBeanBO = new ArrayList<>();

	}

	@Override
	public int getCount() {
		return arrCustomerOJs.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arrCustomerOJs.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	static class ViewHolder {
		TextView txtTitleHangHoa, txtserial;
		NDSpinner spinner;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub

		View mView = arg1;

		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_thong_tin_hang_hoa, arg2, false);
			holder = new ViewHolder();
			holder.txtTitleHangHoa = (TextView) mView.findViewById(R.id.txtTitleHH);
			holder.spinner = (NDSpinner) mView.findViewById(R.id.spHangHoa);
			holder.txtserial = (TextView) mView.findViewById(R.id.txtserial);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}

		final ProductOfferTypeDTO objThongTinHH = arrCustomerOJs.get(arg0);
		arrayAPStockModelBeanBO = objThongTinHH.getProductOfferings();

		// arrayAPStockModelBeanBO.add(0, new ObjAPStockModelBeanBO("", "", "",
		// mContext.getResources().getString(R.string.selectproduct), "", "",
		// "", ""));
		mAdapterSpinerTTHH = new AdapterSpinerTTHHBCCS(arrayAPStockModelBeanBO, mContext);

		holder.txtTitleHangHoa.setText(objThongTinHH.getName());
		holder.spinner.setAdapter(mAdapterSpinerTTHH);

		holder.spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

					Log.d("onItemSelected", "possition : " + arg2);
					onChangeSpiner.OnChangeSpinerSelectProduct(objThongTinHH,
							objThongTinHH.getProductOfferings().get(arg2), arg2, holder.spinner, mAdapterSpinerTTHH);

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
//				return;
				 onChangeSpiner.OnChangeSpinerSelectProduct(objThongTinHH,(ProductOfferingDTO)
				 arg0.getSelectedItem(),
				 arg0.getSelectedItemPosition(), holder.spinner,
				 mAdapterSpinerTTHH);
				 Log.d("onNothingSelected", "onNothingSelected");

			}
		});
		if (!CommonActivity.isNullOrEmpty(objThongTinHH.getSerial())) {
			holder.txtserial.setText(objThongTinHH.getSerial());
			holder.txtserial.setVisibility(View.GONE);

		} else {
			holder.txtserial.setVisibility(View.GONE);
			holder.txtserial.setText("");
		}
		return mView;
		// return null;
	}

	public interface OnChangeSpinerProduct {
		// public void OnChangeSpinerSelect(ObjThongTinHH objThongTinHH);

		void OnChangeSpinerSelectProduct(ProductOfferTypeDTO productOfferTypeDTO,
                                         ProductOfferingDTO objThongTinHH, int arg2, Spinner spinner,
                                         AdapterSpinerTTHHBCCS adapterSpinerTTHHBCCS);

	}

}
