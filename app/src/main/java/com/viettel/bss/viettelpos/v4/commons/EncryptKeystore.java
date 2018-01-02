package com.viettel.bss.viettelpos.v4.commons;

import android.annotation.SuppressLint;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.KeyStore;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

/**
 * Created by Toancx on 12/29/2016.
 */

public class EncryptKeystore {
    @SuppressLint("NewApi")
    private static void createNewKeys() throws Exception {

        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);
        if (keyStore.containsAlias("key_pass_word")) {
            return;
        }
        KeyGenerator keyGenerator = KeyGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        keyGenerator.init(new KeyGenParameterSpec.Builder("key_pass_word",
                KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build());

        keyGenerator.generateKey();
    }

    public static String[] encrypt(String plainText) {
        try {
            createNewKeys();
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);

            SecretKey key = (SecretKey) keyStore.getKey("key_pass_word", null);
            if (key == null) {

                return null;
            }

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            // String iv = Base64.encodeToString(cipher.getIV(),
            // Base64.DEFAULT);
            cipher.init(Cipher.ENCRYPT_MODE, key);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            CipherOutputStream cipherOutputStream = new CipherOutputStream(
                    outputStream, cipher);
            cipherOutputStream.write(plainText.getBytes("UTF-8"));
            cipherOutputStream.close();

            byte[] vals = outputStream.toByteArray();
            String[] result = new String[2];
            result[0] = Base64.encodeToString(vals, Base64.DEFAULT) + "";
            result[1] = Base64.encodeToString(cipher.getIV(), Base64.DEFAULT);
            return result;
        } catch (Exception e) {
            Log.e("Exception ", "exception", e);
        }
        return null;
    }

    @SuppressLint("NewApi")
    public static String decrypt(String cipherText, String IV) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);
        SecretKey key = (SecretKey) keyStore.getKey("key_pass_word", null);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, key,
                new GCMParameterSpec(128, Base64.decode(IV, Base64.DEFAULT)));
        CipherInputStream cipherInputStream = new CipherInputStream(
                new ByteArrayInputStream(Base64.decode(cipherText,
                        Base64.DEFAULT)), cipher);
        ArrayList<Byte> values = new ArrayList<>();
        int nextByte;
        while ((nextByte = cipherInputStream.read()) != -1) {
            values.add((byte) nextByte);
        }

        byte[] bytes = new byte[values.size()];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = values.get(i);
        }

        return new String(bytes, 0, bytes.length, "UTF-8");
    }
}
