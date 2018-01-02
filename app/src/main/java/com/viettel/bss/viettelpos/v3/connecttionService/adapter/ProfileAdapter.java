package com.viettel.bss.viettelpos.v3.connecttionService.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.customer.object.FormBean;
import com.viettel.bss.viettelpos.v4.customer.object.ProfileUpload;

public class ProfileAdapter extends BaseAdapter {

	private Activity context;
	private List<ProfileUpload> lstProfile;

	private OnChangeImage onChangeImage;

	public ProfileAdapter(Activity context, List<ProfileUpload> lstProfile,
			OnChangeImage onChangeImage) {
		super();
		this.context = context;
		this.lstProfile = lstProfile;
		this.onChangeImage = onChangeImage;
	}

	public interface OnChangeImage {
		public void onChangeImage(ProfileUpload item);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (lstProfile == null) {
			return 0;
		}
		return lstProfile.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		if (lstProfile == null) {
			return null;
		}
		return lstProfile.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	private class ViewHolder {
		public Spinner spnProfile;
		public ImageView imgProfile;
	}

	@SuppressLint({ "InflateParams" })
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// android.os.Debug.waitForDebugger();
		View rowView = arg1;
		ViewHolder holder;
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.layout_profile_item, null);
			holder = new ViewHolder();
			holder.spnProfile = (Spinner) rowView.findViewById(R.id.spnProfile);
			holder.imgProfile = (ImageView) rowView
					.findViewById(R.id.imgProfile);
			rowView.setTag(holder);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}

		// configure view holder

		final ProfileUpload item = lstProfile.get(arg0);
		if (!CommonActivity.isNullOrEmpty(item.getLstFile())) {
			Uri uri = Uri.fromFile(item.getLstFile().get(0));
			holder.imgProfile.setImageURI(uri);
		} else {
			holder.imgProfile.setImageResource(R.drawable.logo_viettel_03);
		}
		ArrayAdapter<FormBean> adapter = new ArrayAdapter<FormBean>(context,
				R.layout.spinner_item, R.id.spinner_value, item.getLstProfile());
		holder.spnProfile.setAdapter(adapter);
		holder.spnProfile
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						item.setSelectProfile(item.getLstProfile().get(arg2));
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});
		holder.imgProfile.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				onChangeImage.onChangeImage(item);
			}
		});
		return rowView;
	}
}
