package com.viettel.bss.viettelpos.v4.ui.image.picker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by jay on 21/4/15.
 */
class PagerAdapter2Fragments extends FragmentStatePagerAdapter {

	/**
	 * Number of tabs to be show. Change this value when a tab is added/removed
	 */
	private static final int TAB_COUNT = 1;
	private static final int TAB_CAMERA = 1;
	private static final int TAB_GALLERY = 0;
	private GalleryFragment galleryFragment;

	public PagerAdapter2Fragments(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case TAB_GALLERY:
			if (galleryFragment == null) {
				galleryFragment = new GalleryFragment();
			}
			return galleryFragment;
		case TAB_CAMERA:
            return NativeCameraFragment.newInstance(0);
		}
		return null;
	}

	@Override
	public int getCount() {
		return TAB_COUNT;
	}

	/**
	 * On a call to NotifyDataSetChanged it checks if the item position is
	 * POSITION_UNCHANGED or POSITION_NONE if we return NONE here based on some
	 * criteria it reloads only that specific fragment
	 */
	@Override
	public int getItemPosition(Object object) {
		return POSITION_UNCHANGED;
	}
}