package com.viettel.bss.viettelpos.v4.ui.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.FileUtils;

public class ImageLoader {

	private final MemoryCache memoryCache = new MemoryCache();
	private final FileCache fileCache;
	private final Map<ImageView, String> imageViews = Collections
			.synchronizedMap(new WeakHashMap<ImageView, String>());
	
	private final ExecutorService executorService;

	public ImageLoader(Context context) {
		fileCache = new FileCache(context);
		executorService = Executors.newSingleThreadExecutor();
	}

	private InputStream input = null;
	private Bitmap bMap = null;

    public boolean displayImage(String url, ImageView imageView, int count) {
        int max_count = 2;
        if (count >= max_count) {
			return false;
		}
		imageViews.put(imageView, url);
		Bitmap bitmap = memoryCache.get(url);// ok

		// bitmap = null khi url khong co trong memoryCache
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
			Log.d(Constant.TAG, "getBitmap MemoryCache FOUND url: " + url + " bytes: " + bitmap.getByteCount());
			return true;
		} else {
			Log.d(Constant.TAG, "getBitmap MemoryCache NOTFOUND url: " + url);
			queuePhoto(url, imageView);
		}
		return false;
	}

	private void queuePhoto(String url, ImageView imageView) {
		PhotoToLoad pLoad = new PhotoToLoad(url, imageView);
		PhotosLoader photosLoader = new PhotosLoader(pLoad);
		executorService.submit(photosLoader);
	}

	private Bitmap getBitmap(String url) {
		File f = fileCache.getFile(url);

		// from SD cache
		Bitmap b = decodeFile(f);
		if (b != null) {
			Log.d(Constant.TAG, "getBitmap FileCache FOUND url: " + url + " bytes: " + b.getByteCount());
			return b;
		} else {
			Log.d(Constant.TAG, "getBitmap FileCache NOTFOUND url: " + url);
		}
		
		HttpURLConnection conn = null;
		InputStream is = null;
		OutputStream os = null;
		// from web
		try {
			Bitmap bitmap = null;
			URL imageUrl = new URL(url);
			conn = (HttpURLConnection) imageUrl.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			conn.setInstanceFollowRedirects(true);
			
			Log.d(Constant.TAG, "ContentLength: " + conn.getContentLength());
			
			is = conn.getInputStream();
			os = new FileOutputStream(f);
			FileUtils.copyStream(is, os);

			bitmap = decodeFile(f);
			os.close();
			is.close();
			return bitmap;
		} catch (Throwable ex) {
			ex.printStackTrace();
			if (ex instanceof OutOfMemoryError)
				memoryCache.clear();
			return null;
		} finally {

			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	// decodes image and scales it to reduce memory consumption
	private Bitmap decodeFile(File f) {
		try {
			// decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// Find the correct scale value. It should be the power of 2.
			final int REQUIRED_SIZE = Constant.MAX_SIZE_CROP_IMG;
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE
						|| height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}

			// decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException ignored) {
		}
		return null;
	}

	// Task for the queue
	private class PhotoToLoad {
		private final String url;
		private final ImageView imageView;

		public PhotoToLoad(String _url, ImageView _imageView) {
			url = _url;
			imageView = _imageView;
		}
	}

	private class PhotosLoader implements Runnable {
		
		private final PhotoToLoad photoToLoad;

		public PhotosLoader(PhotoToLoad photoToLoad) {
			this.photoToLoad = photoToLoad;
		}

		@Override
		public void run() {
			// su dung img ban dau

			if (imageViewReused(photoToLoad)) {
				return;
			}
			Bitmap bmp = getBitmap(photoToLoad.url);
			memoryCache.put(photoToLoad.url, bmp);
			if (imageViewReused(photoToLoad))
				return;
			BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
			Activity a = (Activity) photoToLoad.imageView.getContext();
			a.runOnUiThread(bd);
		}
	}

	private boolean imageViewReused(PhotoToLoad photoToLoad) {
		String tag = imageViews.get(photoToLoad.imageView);
        return tag == null || !tag.equals(photoToLoad.url);
    }

	// Used to display bitmap in the UI thread
	private class BitmapDisplayer implements Runnable {
		private final Bitmap bitmap;
		private final PhotoToLoad photoToLoad;

		public BitmapDisplayer(Bitmap b, PhotoToLoad p) {
			bitmap = b;
			photoToLoad = p;
		}

		@Override
		public void run() {
			if (imageViewReused(photoToLoad))
				return;
            int stub_id = R.drawable.logo_vt;
            if (bitmap != null)
				photoToLoad.imageView.setImageBitmap(bitmap);
			else
				photoToLoad.imageView.setImageResource(stub_id);
		}
	}

	public void clearCache() {
		memoryCache.clear();// ok
		fileCache.clear();
	}

}
