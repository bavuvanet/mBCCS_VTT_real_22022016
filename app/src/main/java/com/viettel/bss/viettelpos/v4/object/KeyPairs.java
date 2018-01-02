package com.viettel.bss.viettelpos.v4.object;

import java.io.Serializable;

import com.viettel.bss.viettelpos.v4.commons.Constant;

public class KeyPairs implements Serializable {
	/**
    *
    */
	private static final long serialVersionUID = 1L;
	private String clientPrivateKey = null;
	private String viettelPublicKey = null;

	public KeyPairs() {
	}

	public KeyPairs(String ppKeys) {
		String[] arrResponse;
		if (ppKeys != null) {
			arrResponse = ppKeys.split(Constant.STANDARD_CONNECT_CHAR);
			if (arrResponse.length > 0) {
				for (String anArrResponse : arrResponse) {
					if (anArrResponse.contains("client_private_key=")) {
						this.setClientPrivateKey(anArrResponse.substring(
								"client_private_key=".length(),
								anArrResponse.length()));
					} else if (anArrResponse.contains("viettel_public_key=")) {
						this.setViettelPublicKey(anArrResponse.substring(
								"viettel_public_key=".length(),
								anArrResponse.length()));
					}
				}
			}
		}
	}

	public String getClientPrivateKey() {
		return clientPrivateKey;
	}

	public void setClientPrivateKey(String privatekey) {
		this.clientPrivateKey = privatekey;
	}

	public String getViettelPublicKey() {
		return viettelPublicKey;
	}

	public void setViettelPublicKey(String publickey) {
		this.viettelPublicKey = publickey;
	}
}
