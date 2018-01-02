package com.viettel.bss.viettelpos.v4.connecttionService.adapter;

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

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ObjAPStockModelBeanBO;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ObjThongTinHH;
import com.viettel.bss.viettelpos.v4.customview.adapter.AdapterSpinerTTHH;

public class ThongTinHHAdapter extends BaseAdapter {
	private final ArrayList<ObjThongTinHH> arrCustomerOJs;
	private ArrayList<ObjAPStockModelBeanBO> arrayAPStockModelBeanBO;
	private final Context mContext;
	private AdapterSpinerTTHH mAdapterSpinerTTHH;

	private final OnChangeSpiner onChangeSpiner;
	public ThongTinHHAdapter(ArrayList<ObjThongTinHH> arrCustomerOJs,
			Context context, OnChangeSpiner onChangeSpiner) {
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
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	static class ViewHolder {
		TextView txtTitleHangHoa;
		Spinner spinner;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		
		View mView = arg1;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_thong_tin_hang_hoa, arg2,
					false);
			holder = new ViewHolder();
			holder.txtTitleHangHoa = (TextView) mView
					.findViewById(R.id.txtTitleHH);
			holder.spinner = (Spinner) mView.findViewById(R.id.spHangHoa);

			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		

		final ObjThongTinHH objThongTinHH = arrCustomerOJs.get(arg0);
		arrayAPStockModelBeanBO = objThongTinHH.getList();
	//	arrayAPStockModelBeanBO.add(0, new ObjAPStockModelBeanBO("", "", "", mContext.getResources().getString(R.string.selectproduct), "", "", "", ""));
		mAdapterSpinerTTHH = new AdapterSpinerTTHH(arrayAPStockModelBeanBO, mContext);
		
		holder.txtTitleHangHoa.setText(objThongTinHH.getStockTypeName());
		holder.spinner.setAdapter(mAdapterSpinerTTHH);
		holder.spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Log.d("onItemSelected", "possition : " + arg2);
				if(arg2 > 0){
					onChangeSpiner.OnChangeSpinerSelect(objThongTinHH,arg2);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
				Log.d("onNothingSelected", "onNothingSelected");
				
				onChangeSpiner.OnChangeSpinerSelect(objThongTinHH,0);
				
			}
		});

		return mView;
		// return null;
	}

	public interface OnChangeSpiner {
//		public void OnChangeSpinerSelect(ObjThongTinHH objThongTinHH);

		void OnChangeSpinerSelect(ObjThongTinHH objThongTinHH, int arg2);

	}
}
