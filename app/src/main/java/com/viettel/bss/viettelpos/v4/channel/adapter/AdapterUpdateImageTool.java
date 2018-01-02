package com.viettel.bss.viettelpos.v4.channel.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.channel.object.ObjectCatBO;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

import java.util.ArrayList;

public class AdapterUpdateImageTool extends BaseAdapter{

	private final Context mContext;
	private ArrayList<ObjectCatBO> lstObjectCatBOs = new ArrayList<>();
	
	private final OnChangeImageTool onChangeImageTool;
	private final OnCancelImageTool onCancelImageTool;
	private final OnChangeInfoImageTool onChangeInfoImageTool;
	
	
	public interface OnChangeImageTool{
		void onChangeImageTool(ObjectCatBO objectCatBO, int possition);
	}
	
	public interface OnCancelImageTool{
		void onCancelImageTool(ObjectCatBO objectCatBO, int posstion);
	}
	 public interface OnChangeInfoImageTool{
		 void onChangeInfoImageTool(ObjectCatBO objectCatBO, int posstion);
	 }
	
	public AdapterUpdateImageTool(Context context,ArrayList<ObjectCatBO> arrObjectCatBOs,OnChangeImageTool onChangeImageTool,OnCancelImageTool onCancelImageTool, OnChangeInfoImageTool onChangeInfoImageTool){
		this.mContext = context;
		this.lstObjectCatBOs = arrObjectCatBOs;
		this.onCancelImageTool = onCancelImageTool;
		this.onChangeImageTool = onChangeImageTool;
		this.onChangeInfoImageTool = onChangeInfoImageTool;
	}
	
	@Override
	public int getCount() {
		return lstObjectCatBOs.size();
	}

	@Override
	public Object getItem(int arg0) {
		if(lstObjectCatBOs != null && !lstObjectCatBOs.isEmpty()){
			return lstObjectCatBOs.get(arg0);
		}else{
			return 0;
		}
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}
	
	
	class ViewHolder{
		private TextView txtnameDB;
		private ImageView imgDB;
		private LinearLayout btnDelete;
		private TextView txteror;
		
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		
		View mView = convertView;
		ViewHolder hoder = null;
		
		if(mView == null){
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater
					.inflate(R.layout.item_chanel_image_sign, arg2, false);
			hoder = new ViewHolder();
			hoder.txtnameDB = (TextView) mView
					.findViewById(R.id.nameDiemBan);
			hoder.imgDB = (ImageView) mView
					.findViewById(R.id.imageDiemban);
			hoder.btnDelete = (LinearLayout) mView
					.findViewById(R.id.btndelete);
			hoder.txteror = (TextView) mView.findViewById(R.id.txteror);
			mView.setTag(hoder);
		}else{
			hoder = (ViewHolder) mView.getTag();
		}
		
		final ObjectCatBO objectCatBO = this.lstObjectCatBOs.get(position);
		
		hoder.txtnameDB.setText(objectCatBO.getName());
		
		if (objectCatBO.getBmpImage() != null) {
			Drawable drawable = new BitmapDrawable(mContext.getResources(),
					objectCatBO.getBmpImage());
			hoder.imgDB.setBackground(drawable);
		} else {
				// neu co anh roi thi de anh chinh sua
			hoder.imgDB.setBackgroundResource(R.drawable.edits);
		}
		
		if (!CommonActivity.isNullOrEmpty(objectCatBO.getStatusObj())) {
			if (objectCatBO.getBmpImage() != null) {
				hoder.btnDelete.setVisibility(View.VISIBLE);
				hoder.txteror.setVisibility(View.GONE);
			} else {
				hoder.btnDelete.setVisibility(View.VISIBLE);
				hoder.txteror.setVisibility(View.VISIBLE);
			}
		}else{
			hoder.btnDelete.setVisibility(View.INVISIBLE);
			hoder.txteror.setVisibility(View.GONE);
		}
		
		mView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				onChangeInfoImageTool.onChangeInfoImageTool(objectCatBO, position);
				
			}
		});
		hoder.txtnameDB.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				onChangeInfoImageTool.onChangeInfoImageTool(objectCatBO, position);
			}
		});
		
		hoder.imgDB.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (!CommonActivity.isNullOrEmpty(objectCatBO.getStatusObj())) {
					onChangeImageTool.onChangeImageTool(objectCatBO, position);
				}else{
					CommonActivity.createAlertDialog(mContext,
							mContext.getString(R.string.checkthongtinbienhieu),
							mContext.getString(R.string.app_name)).show();
				}
			
			}
		});
		
		hoder.btnDelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				onCancelImageTool.onCancelImageTool(objectCatBO, position);
			}
		});
		
		
		
	
		return mView;
	}

	
	
}
