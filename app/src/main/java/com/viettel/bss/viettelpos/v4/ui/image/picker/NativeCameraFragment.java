/*
 * Copyright (c) 2014 Rex St. John on behalf of AirPair.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.viettel.bss.viettelpos.v4.ui.image.picker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.ui.image.model.Image;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Take a picture directly from inside the app using this fragment.
 *
 * Reference: http://developer.android.com/training/camera/cameradirect.html
 * Reference:
 * http://stackoverflow.com/questions/7942378/android-camera-will-not-work-
 * startpreview-fails Reference:
 * http://stackoverflow.com/questions/10913181/camera-preview-is-not-restarting
 *
 * Created by Rex St. John (on behalf of AirPair.com) on 3/4/14.
 */
public class NativeCameraFragment extends Fragment {

	// Native camera.
	private Camera mCamera;

	// View to display the camera output.
	private CameraPreview mPreview;

	// Reference to the containing view.
	private View mCameraView;

	private ProgressDialog mProgressDialog;

	private ImageButton captureButton;

	private Activity activity;
	
	private FrameLayout camera_preview;
	

	/**
	 * Default empty constructor.
	 */
	public NativeCameraFragment() {
		super();
	}

	/**
	 * Static factory method
	 * 
	 * @param sectionNumber
	 * @return
	 */
	public static NativeCameraFragment newInstance(int sectionNumber) {
		NativeCameraFragment fragment = new NativeCameraFragment();
		Bundle args = new Bundle();
		// args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle state) {
		super.onCreate(state);
		activity = getActivity();
		mProgressDialog = new ProgressDialog(activity);
		mProgressDialog.setMessage(getString(R.string.progress_title));
		mProgressDialog.setIndeterminate(true);
		mProgressDialog.setCancelable(false);
	}

	/**
	 * OnCreateView fragment override
	 * 
	 * @param inflater
	 * @param container
	 * @param savedInstanceState
	 * @return
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_native_camera, container, false);
		camera_preview = (FrameLayout) view.findViewById(R.id.camera_preview);
		
		// Create our Preview view and set it as the content of our activity.
		boolean opened = safeCameraOpenInView(view);

		if (!opened) {
			Log.d("CameraGuide", "Error, Camera failed to open");
			return view;
		}

		// Trap the capture button.
		captureButton = (ImageButton) view.findViewById(R.id.button_capture);
		captureButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// get an image from the camera
				if (captureButton.isEnabled()) {
					captureButton.setEnabled(false);
					mProgressDialog.show();
					mCamera.takePicture(null, null, mPicture);
				}
			}
		});

		return view;
	}

	/**
	 * Recommended "safe" way to open the camera.
	 * 
	 * @param view
	 * @return
	 */
	private boolean safeCameraOpenInView(View view) {
		boolean qOpened = false;
		releaseCameraAndPreview();
		mCamera = getCameraInstance();
		mCameraView = view;
		qOpened = (mCamera != null);

		Log.d("Camera Guide", "safeCameraOpenInView " + qOpened);
		if (qOpened) {
			mPreview = new CameraPreview(activity.getBaseContext(), mCamera, view);
			camera_preview.removeAllViews();
			camera_preview.addView(mPreview);
			mPreview.startCameraPreview();
		} 
		return qOpened;
	}

	/**
	 * Safe method for getting a camera instance.
	 * 
	 * @return
	 */
	private static Camera getCameraInstance() {
		Camera c = null;
		try {
			c = Camera.open(); // attempt to get a Camera instance
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c; // returns null if camera is unavailable
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		releaseCameraAndPreview();
	}

	/**
	 * Clear any existing preview / camera.
	 */
	private void releaseCameraAndPreview() {

		if (mCamera != null) {
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
		}
		if (mPreview != null) {
			mPreview.destroyDrawingCache();
			mPreview.mCamera = null;
			mPreview.mHolder = null;
			mPreview.mCameraView = null;
			mPreview = null;
		}
	}

	/**
	 * Surface on which the camera projects it's capture results. This is
	 * derived both from Google's docs and the excellent StackOverflow answer
	 * provided below.
	 *
	 * Reference / Credit:
	 * http://stackoverflow.com/questions/7942378/android-camera-will-not-work-
	 * startpreview-fails
	 */
	class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

		// SurfaceHolder
		private SurfaceHolder mHolder;

		// Our Camera.
		private Camera mCamera;

		// Parent Context.
		private final Context mContext;

		// Camera Sizing (For rotation, orientation changes)
		private Camera.Size mPreviewSize;

		// List of supported preview sizes
		private List<Camera.Size> mSupportedPreviewSizes;

		// Flash modes supported by this camera
		private List<String> mSupportedFlashModes;

		// View holding this camera.
		private View mCameraView;

		public CameraPreview(Context context, Camera camera, View cameraView) {
			super(context);

			// Capture the context
			mCameraView = cameraView;
			mContext = context;
			setCamera(camera);

			// Install a SurfaceHolder.Callback so we get notified when the
			// underlying surface is created and destroyed.
			mHolder = getHolder();
			mHolder.addCallback(this);
			mHolder.setKeepScreenOn(true);
			// deprecated setting, but required on Android versions prior to 3.0
			mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}

		/**
		 * Begin the preview of the camera input.
		 */
		public void startCameraPreview() {
			try {
				Log.d(Constant.TAG, "startCameraPreview() parameters");
				Camera.Parameters parameters = mCamera.getParameters();
				parameters.setRotation(90); //set rotation to save the picture
				mCamera.setDisplayOrientation(90);
				mCamera.setParameters(parameters);
				mCamera.setPreviewDisplay(mHolder);
				mCamera.startPreview();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * Extract supported preview and flash modes from the camera.
		 * 
		 * @param camera
		 */
		private void setCamera(Camera camera) {
			// Source:
			// http://stackoverflow.com/questions/7942378/android-camera-will-not-work-startpreview-fails
			mCamera = camera;
			mSupportedPreviewSizes = mCamera.getParameters().getSupportedPreviewSizes();
			mSupportedFlashModes = mCamera.getParameters().getSupportedFlashModes();

			// Set the camera to Auto Flash mode.
			if (mSupportedFlashModes != null && mSupportedFlashModes.contains(Camera.Parameters.FLASH_MODE_AUTO)) {
				Camera.Parameters parameters = mCamera.getParameters();
				parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
				mCamera.setParameters(parameters);
			} 

			requestLayout();
		}

		/**
		 * The Surface has been created, now tell the camera where to draw the
		 * preview.
		 * 
		 * @param holder
		 */
		public void surfaceCreated(SurfaceHolder holder) {
			try {
				mCamera.setPreviewDisplay(holder);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Dispose of the camera preview.
		 * 
		 * @param holder
		 */
		public void surfaceDestroyed(SurfaceHolder holder) {
			if (mCamera != null) {
				mCamera.stopPreview();
			}
		}

		/**
		 * React to surface changed events
		 * 
		 * @param holder
		 * @param format
		 * @param w
		 * @param h
		 */
		public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
			// If your preview can change or rotate, take care of those events
			// here.
			// Make sure to stop the preview before resizing or reformatting it.
			

			if (mHolder.getSurface() == null) {
				// preview surface does not exist
				return;
			}

			// stop preview before making changes
			try {
				Camera.Parameters parameters = mCamera.getParameters();
				
				Log.d(Constant.TAG, "surfaceChanged() parameters: " + parameters.toString());

				// Set the auto-focus mode to "continuous"
				parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);

				// Preview size must exist.
				if (mPreviewSize != null) {
					Camera.Size previewSize = mPreviewSize;
					parameters.setPreviewSize(previewSize.width, previewSize.height);
				}

//				mCamera.setParameters(parameters);
//				mCamera.setDisplayOrientation(90);
				mCamera.startPreview();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * Calculate the measurements of the layout
		 * 
		 * @param widthMeasureSpec
		 * @param heightMeasureSpec
		 */
		@Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			// Source:
			// http://stackoverflow.com/questions/7942378/android-camera-will-not-work-startpreview-fails
			final int width = resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec);
			final int height = resolveSize(getSuggestedMinimumHeight(), heightMeasureSpec);
			setMeasuredDimension(width, height);

			if (mSupportedPreviewSizes != null) {
				mPreviewSize = getOptimalPreviewSize(mSupportedPreviewSizes, width, height);
			}
		}

		/**
		 * Update the layout based on rotation and orientation changes.
		 * 
		 * @param changed
		 * @param left
		 * @param top
		 * @param right
		 * @param bottom
		 */
		@Override
		protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
			// Source:
			// http://stackoverflow.com/questions/7942378/android-camera-will-not-work-startpreview-fails
			if (changed) {
				final int width = right - left;
				final int height = bottom - top;

				int previewWidth = width;
				int previewHeight = height;

				if (mPreviewSize != null) {
					Display display = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE))
							.getDefaultDisplay();

					Log.d(Constant.TAG, "onLayout() rotation: " + display.getRotation());
					
					switch (display.getRotation()) {
					case Surface.ROTATION_0:
						previewWidth = mPreviewSize.height;
						previewHeight = mPreviewSize.width;
						mCamera.setDisplayOrientation(90);
						break;
					case Surface.ROTATION_90:
						previewWidth = mPreviewSize.width;
						previewHeight = mPreviewSize.height;
						mCamera.setDisplayOrientation(90);
						break;
					case Surface.ROTATION_180:
						previewWidth = mPreviewSize.height;
						previewHeight = mPreviewSize.width;
						mCamera.setDisplayOrientation(90);
						break;
					case Surface.ROTATION_270:
						previewWidth = mPreviewSize.width;
						previewHeight = mPreviewSize.height;
						mCamera.setDisplayOrientation(90);
						break;
					}
				}

				final int scaledChildHeight = previewHeight * width / previewWidth;
				mCameraView.layout(0, height - scaledChildHeight, width, height);
			}
		}

		/**
		 *
		 * @param sizes
		 * @param width
		 * @param height
		 * @return
		 */
		private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int width, int height) {
			// Source:
			// http://stackoverflow.com/questions/7942378/android-camera-will-not-work-startpreview-fails
			Camera.Size optimalSize = null;

			final double ASPECT_TOLERANCE = 0.1;
			double targetRatio = (double) height / width;

			// Try to find a size match which suits the whole screen minus the
			// menu on the left.
			for (Camera.Size size : sizes) {

				if (size.height != width)
					continue;
				double ratio = (double) size.width / size.height;
				if (ratio <= targetRatio + ASPECT_TOLERANCE && ratio >= targetRatio - ASPECT_TOLERANCE) {
					optimalSize = size;
				}
			}

			// If we cannot find the one that matches the aspect ratio, ignore
			// the requirement.
			if (optimalSize == null) {
				// TODO : Backup in case we don't get a size.
			}

			return optimalSize;
		}
	}

	private String getPhotoFilename() {
		String ts = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
		return ("mBCCS_" + ts + ".jpg");
	}

	/**
	 * Picture Callback for handling a picture capture and saving it out to a
	 * file.
	 */
	private final Camera.PictureCallback mPicture = new Camera.PictureCallback() {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			Thread th = new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
				}
			});
//			th.start();
			
			try {
				Matrix matrix = new Matrix();
				matrix.postRotate(90);
//				Bitmap bitmap = FileUtils.decodeBitmapFromByte(data, 300, 300);
				Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
				
				Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap , 0, 0, bitmap .getWidth(), bitmap .getHeight(), matrix, true);
				
				Log.d(Constant.TAG, "Picture data: " + data.length + " rotatedBitmap: " + rotatedBitmap.getByteCount());
				
				String path = MediaStore.Images.Media.insertImage(activity.getContentResolver(), rotatedBitmap,
						getPhotoFilename(), null);
				if(path == null) {
	                activity.runOnUiThread(new Runnable() {
	                    @Override
	                    public void run() {
	                        Toast.makeText(activity, getString(R.string.photo_save_error_toast), Toast.LENGTH_SHORT).show();
	                        captureButton.setEnabled(true);
	                        mProgressDialog.dismiss();
	                    }
	                });
	            } else {
	            	Log.d(Constant.TAG, "Picture path: " + path);
	                Uri contentUri = Uri.parse(path);
	                final Image image =  Image.getImageFromContentUri(activity, contentUri);

	                // run the media scanner service
	                // MediaScannerConnection.scanFile(getActivity(), new String[]{path}, new String[]{"image/jpeg"}, null);
	                activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, contentUri));

	                // the current method is an async. call.
	                // so make changes to the UI on the main thread.
	                activity.runOnUiThread(new Runnable() {
	                    @Override
	                    public void run() {
	                        ((ImagePickerActivity) getActivity()).addImage(image);
	                        captureButton.setEnabled(true);
	                        mProgressDialog.dismiss();
	                    }
	                });
	                
	            	Log.d(Constant.TAG, "Picture has been saved uri: " + contentUri.getPath());
	            }

				// Restart the camera preview.
				safeCameraOpenInView(mCameraView);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

}
