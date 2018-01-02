package com.viettel.bss.viettelpos.v4.customer.adapter;

import java.io.File;
import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.FileUtils;
import com.viettel.bss.viettelpos.v4.commons.ZipUtils;
import com.viettel.bss.viettelpos.v4.customer.object.ProfileUploadObj;
import com.viettel.bss.viettelpos.v4.sale.object.StockModel;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class AdapterUpdateCV extends BaseAdapter {
	private final Activity mActivity;
	private final ArrayList<ProfileUploadObj> listProfileUploadObj;
	private final OnClickListener imageListenner;
	private final OnchangeImageFile changeImageFile;

	public interface OnchangeImageFile {
		void onchangeImageFileZip(ArrayList<File> listFile, int position, String imageId,
                                  ProfileUploadObj profileUploadObj);
	}

	public interface OnChangeQuantity {
		void onChangeQuantity(StockModel stockModel);
	}

	public AdapterUpdateCV(Activity mActivity, ArrayList<ProfileUploadObj> listProfileUploadObj,
			OnClickListener imageListenner, OnchangeImageFile changeImageFile) {
		this.mActivity = mActivity;
		this.listProfileUploadObj = listProfileUploadObj;
		this.imageListenner = imageListenner;
		this.changeImageFile = changeImageFile;

	}

	@Override
	public int getCount() {
		return listProfileUploadObj.size();
	}

	@Override
	public Object getItem(int position) {
		return listProfileUploadObj.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public static class ViewHolder {
		public TextView txtCVName;
		public ImageButton imgCV;
		public int position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ProfileUploadObj profileUploadObj = listProfileUploadObj.get(position);
		View mView = convertView;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = mActivity.getLayoutInflater();
			mView = inflater.inflate(R.layout.item_customer_cv, parent, false);
			holder = new ViewHolder();
			holder.txtCVName = (TextView) mView.findViewById(R.id.txtCVName);
			holder.imgCV = (ImageButton) mView.findViewById(R.id.imgCV);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		holder.imgCV.setTag(position + "");
		holder.position = position;

		Log.d(Constant.TAG,
				"AdapterUpdateCV getView profileUploadObj.getRecordName(): " + profileUploadObj.getRecordName());
		holder.txtCVName.setText(profileUploadObj.getRecordName());
		File imgFile = new File(profileUploadObj.getFilePath());
		if (imgFile.exists()) {
			String strPath = profileUploadObj.getFilePath().substring(profileUploadObj.getFilePath().lastIndexOf("."));
			if (!strPath.equalsIgnoreCase(".zip")) {
				Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
				holder.imgCV.setImageBitmap(myBitmap);
				
				ArrayList<File> listFileUpload = new ArrayList<>();
				listFileUpload.add(imgFile);
				changeImageFile.onchangeImageFileZip(listFileUpload, position, holder.imgCV.getTag() + "",
						profileUploadObj);
			} else {
				// extract file zip here
				try {
					File folder = new File(Constant.MBCCS_TEMP_FOLDER_IMG_PROFILE_EXTRACT);
					if (!folder.exists()) {
						folder.mkdir();
					}

					File fileZip = new File(profileUploadObj.getFilePath());
					ZipUtils.unzipWithFolder(fileZip, folder);
					ArrayList<File> listFileUnzip = FileUtils.listFilesForFolder(folder);
					ArrayList<File> listFileUnzipCoppy = FileUtils.backUplistFileUnzip(listFileUnzip);
					File imageFile = listFileUnzipCoppy.get(0);
					Bitmap myBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
					holder.imgCV.setImageBitmap(myBitmap);
					changeImageFile.onchangeImageFileZip(listFileUnzipCoppy, position, holder.imgCV.getTag() + "",
							profileUploadObj);
				} catch (Exception e) {
					Log.d(Constant.TAG, "AdapterUpdateCV getView Exception ", e);
				}
			}
		}
		holder.imgCV.setOnClickListener(imageListenner);
		return mView;
	}

}
