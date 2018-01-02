package com.viettel.bss.viettelpos.v4.commons;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.text.format.Formatter;
import android.util.Log;

public class Network {
	/**
	 * TuanTD7
	 * 
	 * @since:
	 */

	public static String getLocalIpAddress() {
	    try {
	        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
	            NetworkInterface intf = en.nextElement();
	            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
	                InetAddress inetAddress = enumIpAddr.nextElement();
	                if (!inetAddress.isLoopbackAddress()) {
	                    String ip = Formatter.formatIpAddress(inetAddress.hashCode());
	                    Log.i(Constant.TAG, "***** IP="+ ip);
	                    return ip;
	                }
	            }
	        }
	    } catch (SocketException ex) {
	        Log.e(Constant.TAG, ex.toString(), ex);
	    }
	    return null;
	}
}
