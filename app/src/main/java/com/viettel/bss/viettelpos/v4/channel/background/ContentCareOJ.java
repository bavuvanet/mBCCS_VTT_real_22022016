package com.viettel.bss.viettelpos.v4.channel.background;

import java.util.ArrayList;

import android.location.Location;

import com.viettel.bss.viettelpos.v4.channel.object.ChannelContentCareOJ;
import com.viettel.bss.viettelpos.v4.login.object.Staff;

class ContentCareOJ {
	// new
	// VisitChannelAsyncTask(
	// getActivity(),
	// mStaff, arrOutPut,
	// myLocation)
	// .execute();
	private static Staff mStaff;
	private static ArrayList<ChannelContentCareOJ> arrChannelContent;
	private static Location myLocation;
	public static void setmStaff(Staff mStaff) {
		ContentCareOJ.mStaff = mStaff;
	}
	public static Staff getmStaff() {
		return mStaff;
	}
	public static void setMyLocation(Location myLocation) {
		ContentCareOJ.myLocation = myLocation;
	}
	public static Location getMyLocation() {
		return myLocation;
	}
	public static void setArrChannelContent(
			ArrayList<ChannelContentCareOJ> arrChannelContent) {
		ContentCareOJ.arrChannelContent = arrChannelContent;
	}
	public static ArrayList<ChannelContentCareOJ> getArrChannelContent() {
		return arrChannelContent;
	}
}
