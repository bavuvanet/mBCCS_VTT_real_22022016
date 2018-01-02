package com.viettel.bss.viettelpos.v4.commons;

import com.viettel.bss.viettelpos.v4.object.KeyPairs;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class PreferenceUtils {
	public static void saveKeyPairInfo(Context context, KeyPairs keypair,
			String serial) {
		if ((keypair.getClientPrivateKey() != null)
				&& (keypair.getViettelPublicKey() != null)) {
			SharedPreferences preferences = context.getSharedPreferences(
					Constant.SHARED_PREF_KEYPAIR, Constant.PRIVATE);
			SharedPreferences.Editor editor = preferences.edit();
			{
				editor.putString(serial + "_" + Constant.SHARED_PRIVATEKEY,
						keypair.getClientPrivateKey());
				editor.putString(serial + "_" + Constant.SHARED_PUBLICKEY,
						keypair.getViettelPublicKey());
				editor.apply();
			}
		} else {
			Log.e("saveKeyPairInfo", "He's dead, Viettel");
		}
	}
	
    /**
     * getKeyPairInfo
     * @param context
     * @param serial
     * @return
     */
    public static KeyPairs getKeyPairInfo(Context context, String serial) {
        KeyPairs keyPair = new KeyPairs();
        SharedPreferences preferences = context.getSharedPreferences(
        		Constant.SHARED_PREF_KEYPAIR, Constant.PRIVATE);
        keyPair.setClientPrivateKey(preferences.getString(
        		serial + "_" + Constant.SHARED_PRIVATEKEY, Constant.INVALID_STRING));
        keyPair.setViettelPublicKey(preferences.getString(
        		serial + "_" + Constant.SHARED_PUBLICKEY, Constant.INVALID_STRING));
        return keyPair;
    }
    
    /**
     * isKeyPairExist
     * @param context
     * @param serial
     * @return
     */
    public static boolean isKeyPairExist(Context context, String serial) {
        SharedPreferences preferences = context.getSharedPreferences(
        		Constant.SHARED_PREF_KEYPAIR, Constant.PRIVATE);
        String privateKey = preferences.getString(serial + "_" + Constant.SHARED_PRIVATEKEY,
        		Constant.INVALID_STRING);
        String publicKey = preferences.getString(serial + "_" + Constant.SHARED_PUBLICKEY,
        		Constant.INVALID_STRING);

        return ((null != privateKey) && (null != publicKey));
    }
}
