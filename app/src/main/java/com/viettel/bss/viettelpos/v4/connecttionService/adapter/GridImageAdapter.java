package com.viettel.bss.viettelpos.v4.connecttionService.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.SquareImageView;
import com.viettel.bss.viettelpos.v4.customview.obj.FileObj;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

class GridImageAdapter extends BaseAdapter{

	private final Context context ;
	private final ArrayList<FileObj> arrFileObjs;
	
	public GridImageAdapter(ArrayList<FileObj> arrFileObjs,Context mContext){
		this.arrFileObjs = arrFileObjs;
		this.context = mContext;
	}
	@Override
	public int getCount() {
		if(arrFileObjs != null){
			return arrFileObjs.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		return arrFileObjs.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	class ViewHolder{
		private SquareImageView imageView;
		private TextView txtname;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		View mView = convertView;
		ViewHolder hoder = null;
		if(mView == null){
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			mView = inflater.inflate(R.layout.grid_item_image, arg2, false);
			hoder= new ViewHolder();
			hoder.imageView = (SquareImageView) mView.findViewById(R.id.picture);
			hoder.txtname = (TextView) mView.findViewById(R.id.textimage);
			mView.setTag(hoder);
		}else{
			hoder = (ViewHolder) mView.getTag();
		}
		final FileObj fileObj = arrFileObjs.get(arg0);
		try{
			Bitmap bitmapImage = BitmapFactory.decodeFile(fileObj.getPath());
	        Drawable drawableImage = new BitmapDrawable(bitmapImage);
	        hoder.imageView.setBackgroundDrawable(drawableImage);
		}catch(Exception e){
			e.printStackTrace();
		}
		hoder.txtname.setText(fileObj.getName());
		
		
		return mView;
	}

	
	
	
}
