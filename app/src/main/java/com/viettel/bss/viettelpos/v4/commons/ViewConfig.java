package com.viettel.bss.viettelpos.v4.commons;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.login.object.Staff;
import com.viettel.bss.viettelpos.v4.ui.image.SerialBitmap;

public class ViewConfig {
	public static Bitmap getScaleBitmap(Bitmap bm, float percent) {
		return Bitmap.createScaledBitmap(bm,
				Math.round(bm.getWidth() * percent),
				Math.round(bm.getHeight() * percent), true);
	}

	public static void setStaffBitmap(Staff mStaff, ImageView view) {
		// TODO Auto-generated method stub
		SerialBitmap serialBitmap = mStaff.getSerialBitmap();
		if (serialBitmap != null) {
			Bitmap bm = serialBitmap.getBitmap();
			if (bm != null) {
				Log.e(tag, "serial bitmap ok");
				view.setImageBitmap(bm);
			} else {
				Log.e(tag, "serial bitmap nulllllllllllllllllll");
			}

		} else {
			view.setImageResource(R.drawable.logo_vt);
		}
	}

	private static final String tag = "view config";
}
