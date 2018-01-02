package com.viettel.bss.viettelpos.v4.connecttionMobile.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.squareup.picasso.Picasso;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.customview.obj.RecordPrepaid;

public class RecordPrepaidAdapter extends ArrayAdapter<ArrayList<RecordPrepaid>> {
	private final Activity context;
	private  List<ArrayList<RecordPrepaid>> lstArrayLists;
	private final OnClickListener imageListenner;
	
	public class ViewHolder {
		public Spinner spUploadImage;
		public ImageButton ibUploadImage;
	}

	public RecordPrepaidAdapter(Activity context, List<ArrayList<RecordPrepaid>> _arrayOfArrayList, OnClickListener _imageListenner) {
		super(context, R.layout.item_spinner_image, _arrayOfArrayList);
        this.lstArrayLists = _arrayOfArrayList;
		this.context = context;
		this.imageListenner = _imageListenner;
	}

    @Nullable
    @Override
    public ArrayList<RecordPrepaid> getItem(int position) {
        return lstArrayLists.get(position);
    }

	@Override
	public int getPosition(@Nullable ArrayList<RecordPrepaid> item) {
		return super.getPosition(item);
	}

	@SuppressLint("InflateParams")
    @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		// reuse views
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.item_spinner_image, null);
			// configure view holder
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.spUploadImage = (Spinner) rowView
					.findViewById(R.id.spUploadImage);
			viewHolder.ibUploadImage = (ImageButton) rowView
					.findViewById(R.id.ibUploadImage);
			rowView.setTag(viewHolder);
		}

		ArrayList<RecordPrepaid> recordPrepaids = getItem(position);
		int imageId = Integer.parseInt(recordPrepaids.get(0).getId());
//		List<String> strings = new ArrayList<>();
//		for (RecordPrepaid recordPrepaid : recordPrepaids) {
//			strings.add(recordPrepaid.getName());
//		}
		
//		Log.d("getView", "position: " + position + " " + recordPrepaids + " " + recordPrepaids.size());
		
		// fill data
		ViewHolder holder = (ViewHolder) rowView.getTag();
		
		ArrayAdapter<RecordPrepaid> adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, recordPrepaids);
		holder.spUploadImage.setAdapter(adapter);

		for(RecordPrepaid recordPrepaid : recordPrepaids){
			if(!CommonActivity.isNullOrEmpty(recordPrepaid)
					&& !CommonActivity.isNullOrEmpty(recordPrepaid.getProfileRecord())
					&& !CommonActivity.isNullOrEmpty(recordPrepaid.getProfileRecord().getUrl())){
				holder.spUploadImage.setSelection(recordPrepaids.indexOf(recordPrepaid));
//				if(!CommonActivity.isNullOrEmpty(recordPrepaid.getProfileRecord().getBitmap())){
//					holder.ibUploadImage.setImageBitmap(recordPrepaid.getProfileRecord().getBitmap());
//				} else {
					Picasso.with(context).load(new File(recordPrepaid.getProfileRecord().getUrl())).centerCrop().resize(100, 100)
							.into(holder.ibUploadImage);
//				}
				break;
			}else{
				holder.ibUploadImage.setBackgroundResource(R.drawable.logo_vt);
			}
		}



		
		holder.spUploadImage.setId(imageId);
		holder.ibUploadImage.setId(imageId);
		
		holder.ibUploadImage.setOnClickListener(imageListenner);
		
		return rowView;
	}
}
