package com.viettel.bss.viettelpos.v4.channel.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.channel.object.ObjectCatBO;
import com.viettel.bss.viettelpos.v4.channel.object.ObjectTypeBO;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AdapterUpdateImageMonth extends BaseExpandableListAdapter {
	private final LayoutInflater inflater;
	private final Activity activity;
	private final ArrayList<ObjectTypeBO> lstObjectTypeBOs;

	// khai bao 2 interface ----chon thong tin bien hieu
	private final OnChangeInfoSignImageMonth onChangeInfoSignImage;
	private final OnchangeSignImageMonth onchangeSignImage;
	private final OnCancelSignImageMonth onCancelSignImage;

	public interface OnChangeInfoSignImageMonth {
		void onChangeInfoSignImageMonth(ObjectCatBO objectCatBO, int groupPosition, int childPosition);
	}

	// interface change image
	public interface OnchangeSignImageMonth {
		void onChangeSignImageMonth(ObjectCatBO objectCatBO, int groupPosition, int childPosition);
	}

	// interface cancel item update image
	public interface OnCancelSignImageMonth {
		void onCancelSignImageMonth(ObjectCatBO objectCatBO, int groupPosition, int childPosition);
	}

	public AdapterUpdateImageMonth(Activity activity,
			ArrayList<ObjectTypeBO> arrObjectTypeBOs,
			OnChangeInfoSignImageMonth onChangeInfoSignImage,
			OnchangeSignImageMonth onchangeSignImage,
			OnCancelSignImageMonth onCancelSignImage) {
		this.activity = activity;
		this.lstObjectTypeBOs = arrObjectTypeBOs;
		this.onchangeSignImage = onchangeSignImage;
		this.onChangeInfoSignImage = onChangeInfoSignImage;
		this.onCancelSignImage = onCancelSignImage;
		this.inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return lstObjectTypeBOs.get(groupPosition).getArrObjectCatBOs()
				.get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@SuppressLint("InflateParams")
    @SuppressWarnings("deprecation")
	@Override
	public View getChildView(final 
			int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		final ObjectCatBO objectCatBO = this.lstObjectTypeBOs
				.get(groupPosition).getArrObjectCatBOs().get(childPosition);
		if (convertView == null) {

			convertView = inflater.inflate(R.layout.item_chanel_image_sign,
					null);
		}
		TextView txtnameDB = (TextView) convertView
				.findViewById(R.id.nameDiemBan);
		ImageView imgDB = (ImageView) convertView
				.findViewById(R.id.imageDiemban);
		LinearLayout btnDelete = (LinearLayout) convertView
				.findViewById(R.id.btndelete);
		TextView txteror = (TextView) convertView.findViewById(R.id.txteror);
		txtnameDB.setText(objectCatBO.getName());

		// if (!CommonActivity.isNullOrEmpty(objectCatBO.getRequestType())
		// && !CommonActivity.isNullOrEmpty(objectCatBO.getImageLink())) {
		// btnDelete.setVisibility(View.VISIBLE);
		// } else {
		// btnDelete.setVisibility(View.GONE);
		// }

		if (objectCatBO.getBmpImage() != null) {
			Drawable drawable = new BitmapDrawable(activity.getResources(),
					objectCatBO.getBmpImage());
			imgDB.setBackgroundDrawable(drawable);
		} else {
				// neu co anh roi thi de anh chinh sua
				imgDB.setBackgroundResource(R.drawable.edits);
		}

		if (!CommonActivity.isNullOrEmpty(objectCatBO.getStatusObj())) {
			if (objectCatBO.getBmpImage() != null) {
				btnDelete.setVisibility(View.VISIBLE);
				txteror.setVisibility(View.GONE);
			} else {
				btnDelete.setVisibility(View.VISIBLE);
//				btnDelete.setVisibility(View.GONE);
				txteror.setVisibility(View.VISIBLE);
			}
		}else{
			btnDelete.setVisibility(View.INVISIBLE);
			txteror.setVisibility(View.GONE);
		}

		txtnameDB.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				onChangeInfoSignImage.onChangeInfoSignImageMonth(objectCatBO,groupPosition,childPosition);
			}
		});

		imgDB.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (!CommonActivity.isNullOrEmpty(objectCatBO.getStatusObj())) {
					onchangeSignImage.onChangeSignImageMonth(objectCatBO,groupPosition,childPosition);
				} else {
					CommonActivity.createAlertDialog(activity,
							activity.getString(R.string.checkthongtinbienhieu),
							activity.getString(R.string.app_name)).show();
				}
			}
		});
		
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				onChangeInfoSignImage.onChangeInfoSignImageMonth(objectCatBO,groupPosition,childPosition);
				
			}
		});
		
		btnDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				onCancelSignImage.onCancelSignImageMonth(objectCatBO,groupPosition,childPosition);
			}
		});
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return lstObjectTypeBOs.get(groupPosition).getArrObjectCatBOs().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return lstObjectTypeBOs.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return lstObjectTypeBOs.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@SuppressLint("InflateParams")
    @Override
	public View getGroupView(final int groupPosition, final boolean isExpanded,
			View convertView, ViewGroup parent) {
		ObjectTypeBO objectTypeBO = this.lstObjectTypeBOs.get(groupPosition);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.report_item_chanel, null);
		}
		TextView name = (TextView) convertView.findViewById(R.id.tvreport);
		Resources r = activity.getResources();
		float px20 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20,
				r.getDisplayMetrics());
		name.setCompoundDrawables(null, null, null, null);
		name.setText(Html.fromHtml(objectTypeBO.getBroadTypeName()));

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return true;
	}

}
