package com.viettel.bss.viettelpos.v4.customview.adapter;

import java.util.List;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.customer.object.CorporationCategoryBO;
import com.viettel.bss.viettelpos.v4.work.object.DataComboboxBeanJson;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GetCatagoryBeanCoporationAdapter extends
		ArrayAdapter<CorporationCategoryBO> {
	private Context mContext;
	
	private OnDeleteCoporationBeans onDeleteCoporationBeans;
	private OnAddCategotyCoporationBeans onAddCategotyCoporationBeans;
	
	public interface OnDeleteCoporationBeans {
		public void onDeleteCoporationBeans(CorporationCategoryBO catagoryInforBeans);
	}

	public interface OnAddCategotyCoporationBeans {
		public void onAddCatagoryCoporationBeans(CorporationCategoryBO catagoryInforBeans);
	}


	public GetCatagoryBeanCoporationAdapter(
			List<CorporationCategoryBO> lstCatagoryInfors, Context context , OnDeleteCoporationBeans onDeleteCoporationBeans, OnAddCategotyCoporationBeans onAddCategotyCoporationBeans) {
		super(context, R.layout.category_coporation_item, lstCatagoryInfors);
		mContext = context;
		this.onAddCategotyCoporationBeans = onAddCategotyCoporationBeans;
		this.onDeleteCoporationBeans = onDeleteCoporationBeans;
		
	}

	public class ViewHolder {
		TextView txtpstn;
		TextView tvvalue;
		View view;
		ImageView btnNext;
		LinearLayout imgAdd;
		LinearLayout imgDelete;
		RelativeLayout lnparent;
	}

	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {
		ViewHolder holder;
		View mView = arg1;

		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.category_coporation_item, arg2, false);
			holder = new ViewHolder();
			holder.txtpstn = (TextView) mView.findViewById(R.id.tvconectorcode);
			holder.tvvalue = (TextView) mView.findViewById(R.id.tvvalue);
			holder.view = (View) mView.findViewById(R.id.view1);
			holder.btnNext = (ImageView) mView.findViewById(R.id.btnNext);
			holder.imgAdd = (LinearLayout) mView.findViewById(R.id.imgAdd);
			holder.imgDelete = (LinearLayout) mView.findViewById(R.id.imgDelete);
			holder.lnparent = (RelativeLayout) mView.findViewById(R.id.lnparent);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}

		final CorporationCategoryBO catagoryInforBeans = getItem(position);
		if ("1".equals(catagoryInforBeans.getIsRequire())) {
			
			if(!CommonActivity.isNullOrEmptyArray(catagoryInforBeans.getLstCatagoryInfors())){
				
				for (CorporationCategoryBO item : catagoryInforBeans.getLstCatagoryInfors()) {
					boolean result = true;
					if (!CommonActivity.isNullOrEmptyArray(item.getLstCatagoryInfors())) {
						for (CorporationCategoryBO itemChild : item.getLstCatagoryInfors()) {
							if ("1".equals(itemChild.getIsRequire())
									&& CommonActivity.isNullOrEmpty(itemChild.getInforValue())) {
								result = false;
							}
						}
					} else {
						if ("1".equals(item.getIsRequire()) && CommonActivity.isNullOrEmpty(item.getInforValue())) {
							result = false;
						}
					}
					if(!result){
						holder.lnparent.setBackgroundColor(mContext.getResources().getColor(R.color.table_row_f));
					}else{
						holder.lnparent.setBackgroundColor(mContext.getResources().getColor(R.color.white));
					}
				}
			}else{
				if (CommonActivity.isNullOrEmpty(catagoryInforBeans.getInforValue())) {
					holder.lnparent.setBackgroundColor(mContext.getResources().getColor(R.color.table_row_f));
				}else{
					holder.lnparent.setBackgroundColor(mContext.getResources().getColor(R.color.white));
				}
			}
			
			holder.txtpstn
					.setText(Html.fromHtml(catagoryInforBeans.getInforName() +"<font color=\"#DF0101\">" + mContext.getString(R.string.checkrequire) + "</font>"));
		} else {
			holder.txtpstn.setText(catagoryInforBeans.getInforName());
			holder.lnparent.setBackgroundColor(mContext.getResources().getColor(R.color.white));
		}
		if(!CommonActivity.isNullOrEmptyArray(catagoryInforBeans.getLstCatagoryInfors())){
			
			if("TTKH".equals(catagoryInforBeans.getInforCode())){
				for (CorporationCategoryBO coCategoryBO : catagoryInforBeans.getLstCatagoryInfors()) {
					if("TDN".equals(coCategoryBO.getInforCode()) && !CommonActivity.isNullOrEmpty(coCategoryBO.getInforValue())){
						catagoryInforBeans.setInforValue(coCategoryBO.getInforValue());
						break;
					}
				}
			}
			if("NGUOITXLH".equals(catagoryInforBeans.getInforCode())){
				for (CorporationCategoryBO coCategoryBO : catagoryInforBeans.getLstCatagoryInfors()) {
					if("NAME3".equals(coCategoryBO.getInforCode()) && !CommonActivity.isNullOrEmpty(coCategoryBO.getInforValue())){
						catagoryInforBeans.setInforValue(coCategoryBO.getInforValue());
						break;
					}
				}
			}
			if("NGUOITD".equals(catagoryInforBeans.getInforCode())){
				for (CorporationCategoryBO coCategoryBO : catagoryInforBeans.getLstCatagoryInfors()) {
					if("NAME2".equals(coCategoryBO.getInforCode()) && !CommonActivity.isNullOrEmpty(coCategoryBO.getInforValue())){
						catagoryInforBeans.setInforValue(coCategoryBO.getInforValue());
						break;
					}
				}
			}
			if("LEADER".equals(catagoryInforBeans.getInforCode())){
				for (CorporationCategoryBO coCategoryBO : catagoryInforBeans.getLstCatagoryInfors()) {
					if("NAME".equals(coCategoryBO.getInforCode()) && !CommonActivity.isNullOrEmpty(coCategoryBO.getInforValue())){
						catagoryInforBeans.setInforValue(coCategoryBO.getInforValue());
						break;
					}
				}
			}
			
			
			holder.txtpstn.setTypeface(null, Typeface.BOLD);
			if ("1".equals(catagoryInforBeans.getType())) {
				if(catagoryInforBeans.isApprove()){
					holder.imgAdd.setVisibility(View.GONE);
					holder.imgDelete.setVisibility(View.GONE);
				}else{
					holder.imgAdd.setVisibility(View.VISIBLE);
					holder.imgDelete.setVisibility(View.VISIBLE);
				}
			
				holder.btnNext.setVisibility(View.GONE);
			} else {
				holder.imgAdd.setVisibility(View.GONE);
				holder.imgDelete.setVisibility(View.GONE);
				holder.btnNext.setVisibility(View.VISIBLE);
			}
		}else{
			holder.txtpstn.setTypeface(null, Typeface.NORMAL);
			holder.btnNext.setVisibility(View.GONE);
			if ("1".equals(catagoryInforBeans.getType())) {
				if(catagoryInforBeans.isApprove()){
					holder.imgAdd.setVisibility(View.GONE);
					holder.imgDelete.setVisibility(View.GONE);
				}else{
					holder.imgAdd.setVisibility(View.VISIBLE);
					holder.imgDelete.setVisibility(View.VISIBLE);
				}
			} else {
				holder.imgAdd.setVisibility(View.GONE);
				holder.imgDelete.setVisibility(View.GONE);
			}
		
		}
		
		if (!CommonActivity.isNullOrEmpty(catagoryInforBeans.getInforValue())) {
			holder.view.setVisibility(View.VISIBLE);
			holder.tvvalue.setVisibility(View.VISIBLE);
			if ("COMBOBOX".equals(catagoryInforBeans.getDataType())) {
				if (!CommonActivity.isNullOrEmpty(catagoryInforBeans
						.getInforValue())) {
					if(catagoryInforBeans.getLstDataCombo() != null && catagoryInforBeans.getLstDataCombo().size() > 0){
						for (DataComboboxBeanJson item : catagoryInforBeans.getLstDataCombo()) {
							if(catagoryInforBeans.getInforValue().equals(item.getCode())){
								holder.view.setVisibility(View.VISIBLE);
								holder.tvvalue.setVisibility(View.VISIBLE);
								holder.tvvalue.setText(item.getName());
							}
						}
					}
				} else {
					holder.view.setVisibility(View.GONE);
					holder.tvvalue.setVisibility(View.GONE);
				}
			} else {
				if("MULTISELECT".equals(catagoryInforBeans.getDataType())){
					if(!CommonActivity.isNullOrEmpty(catagoryInforBeans.getInforValue())){
						holder.view.setVisibility(View.VISIBLE);
						holder.tvvalue.setVisibility(View.VISIBLE);
						holder.tvvalue.setText((catagoryInforBeans.getInforValue().trim().split(";").length)+ "");
					}else{
						holder.view.setVisibility(View.GONE);
						holder.tvvalue.setVisibility(View.GONE);
					}
				}else{
					if("VON".equals(catagoryInforBeans.getInforCode()) || "CHI_PHI".equals(catagoryInforBeans.getInforCode())){
						holder.tvvalue.setText(StringUtils.formatMoney(catagoryInforBeans.getInforValue()));
					}else{
						holder.tvvalue.setText(catagoryInforBeans.getInforValue());
					}
					
					
				}
				
			}

		} else {
			holder.tvvalue.setVisibility(View.GONE);
			holder.view.setVisibility(View.GONE);
		}
		
		holder.imgAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				onAddCategotyCoporationBeans.onAddCatagoryCoporationBeans(catagoryInforBeans);
				
			}
		});
		
		holder.imgDelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				onDeleteCoporationBeans.onDeleteCoporationBeans(catagoryInforBeans);
			}
		});
		
		return mView;
	}

}
