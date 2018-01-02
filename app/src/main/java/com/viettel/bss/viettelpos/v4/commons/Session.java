package com.viettel.bss.viettelpos.v4.commons;

import com.viettel.bss.viettelpos.v4.sale.object.Staff;

public class Session {
	public static String userName;
    public static String passWord;
	public static String serial;

	public static Staff loginUser;
	public static String token;
	public static String province = "";
	public static String district = "";
	public static Boolean isSync = true;
	public static Boolean isSyncRunning = false;
	public static Boolean KPI_REQUEST = false;
	private static String ttnsCode = "";
	private static String fixErrorVersion = "";
	private static String publicKey = "";
	private static String privateKey = "";
//	private static RemoteServiceConnection serviceConnection = new RemoteServiceConnection();

	public static String getToken() {
		return token;
	}

	public static void setToken(final String value) {
		// setToken(null, value);
		token = value;
	}

	public static String getTtnsCode() {
		return ttnsCode;
	}

	public static void setTtnsCode(String ttnsCode) {
		Session.ttnsCode = ttnsCode;
	}

	public static String getFixErrorVersion() {
		return fixErrorVersion;
	}

	public static void setFixErrorVersion(String fixErrorVersion) {
		Session.fixErrorVersion = fixErrorVersion;
	}

	public static String getPublicKey() {
		return publicKey;
	}

	public static void setPublicKey(String publicKey) {
		Session.publicKey = publicKey;
	}

	public static String getPrivateKey() {
		return privateKey;
	}

	public static void setPrivateKey(String privateKey) {
		Session.privateKey = privateKey;
	}

	

}
