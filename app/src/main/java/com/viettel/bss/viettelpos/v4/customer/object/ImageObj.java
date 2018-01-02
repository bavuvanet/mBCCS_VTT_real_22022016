package com.viettel.bss.viettelpos.v4.customer.object;

import android.widget.ImageView;

public class ImageObj {
	private boolean isSetIm;
	private ImageView image;

	public ImageObj(boolean isSetIm, ImageView image) {
		super();
		this.isSetIm = isSetIm;
		this.image = image;
	}

	public ImageObj() {
		super();
	}

	public boolean isSetIm() {
		return isSetIm;
	}

	public void setSetIm(boolean isSetIm) {
		this.isSetIm = isSetIm;
	}

	public ImageView getImage() {
		return image;
	}

	public void setImage(ImageView image) {
		this.image = image;
	}

}
