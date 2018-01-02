package com.viettel.bss.viettelpos.v4.commons;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class SecurityUtil {
	private Cipher _cipher;
	private byte[] _hexhars;
	private static final String _customPassword = "mBCCS#2014";
	private String _algorithm;
	private static final byte[] keyDefault = { 77, 64, 110, 72, 102, 103, -23,
			67, 54, -128, 74, 57, 48, 84, 86, 51 };

	public SecurityUtil() {
		this("config_default.properties", "AES");
	}

	public SecurityUtil(String _configFilePath) {
		this(_configFilePath, "AES");
	}

	private SecurityUtil(String configFilePath, String algorithm) {
		this._algorithm = algorithm;
		try {
			this._cipher = Cipher.getInstance("AES");

			this._hexhars = new byte[] { 100, 97, 50, 102, 55, 53, 54, 52, 56,
					57, 49, 98, 99, 48, 101, 51 };
		} catch (NoSuchAlgorithmException | NoSuchPaddingException ignored) {
		}
    }

	public String getCustomPassword() {
		return _customPassword;
	}

	private String getAlgorithm() {
		return this._algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this._algorithm = algorithm;
	}

	private SecretKeySpec generateSecretKeySpec(byte[] keyDefault,
			String algorithm) {
		byte[] keyAlgorithm = keyDefault.clone();

		int i = 0;

		for (char c : _customPassword.toCharArray()) {
			keyAlgorithm[i] = (byte) (keyDefault[i] + c);

			++i;

			if (i == 16) {
				i = 0;
			}
		}

        return new SecretKeySpec(keyAlgorithm, algorithm);
	}

	private byte[] _doCrypt(byte[] inputs, int mode) throws Exception {
		Key key = generateSecretKeySpec(keyDefault, getAlgorithm());

		this._cipher.init(mode, key);

		return this._cipher.doFinal(inputs);
	}

	private void _doCrypt(String inputFile, String outputFile, int mode) {
		try {
			FileInputStream inputStream = new FileInputStream(inputFile);

			OutputStream out = new FileOutputStream(outputFile);

			int bytesRead = 0;

			byte[] buffer = new byte[8120];

			while ((bytesRead = inputStream.read(buffer, 0, 8120)) != -1) {
				byte[] cloneBuffer = new byte[bytesRead];

				if (bytesRead < buffer.length) {
					System.arraycopy(buffer, 0, cloneBuffer, 0, bytesRead);
				}

				out.write(_doCrypt(cloneBuffer, mode));
			}

			inputStream.close();

			out.close();
		} catch (Exception ignored) {
		}
	}

	private byte[] encrypt(byte[] inputs) throws Exception {
		return _doCrypt(inputs, 1);
	}

	private byte[] decrypt(byte[] inputs) throws Exception {
		return _doCrypt(inputs, 2);
	}

	public String encrypt(String str) throws Exception {
		return toHexa(encrypt(str.getBytes()));
	}

	public String decrypt(String str) throws Exception {
		return new String(decrypt(stringToBytes(str)));
	}

	public void encryptFile(String originFile, String outputEncryptedFile) {
		_doCrypt(originFile, outputEncryptedFile, 1);
	}

	public void decryptFile(String inputEncryptedFile, String outputFile) {
		_doCrypt(inputEncryptedFile, outputFile, 2);
	}

	private String decryptFile(InputStream inputStream) {
		StringBuilder sb = new StringBuilder();
		try {
			int bytesRead = 0;

			byte[] buffer = new byte[8120];

			while ((bytesRead = inputStream.read(buffer, 0, 8120)) != -1) {
				byte[] cloneBuffer = new byte[bytesRead];

				if (bytesRead < buffer.length) {
					System.arraycopy(buffer, 0, cloneBuffer, 0, bytesRead);
				}

				sb.append(new String(decrypt(cloneBuffer)));
			}

			inputStream.close();
		} catch (Exception ignored) {
		}

		return sb.toString();
	}

	public String decryptFile(String encryptedFilePath) {
		StringBuilder sb = new StringBuilder();
		try {
			FileInputStream inputStream = new FileInputStream(encryptedFilePath);

			sb.append(decryptFile(inputStream));
		} catch (FileNotFoundException ignored) {
		}

		return sb.toString();
	}

	private String toHexa(byte[] b) {
		StringBuilder s = new StringBuilder(2 * b.length);

        for (byte aB : b) {
            int v = aB & 0xFF;

            s.append((char) this._hexhars[(v >> 4)]);

            s.append((char) this._hexhars[(v & 0xF)]);
        }

		return s.toString();
	}

	private byte[] stringToBytes(String s) {
		if (s.length() % 2 != 0) {
			s = "0".concat(s);
		}

		int len = s.length() / 2;

		byte[] b = new byte[len];

		for (int i = 0; i < len; ++i) {
			b[i] = (byte) (indexInHexhars(s.charAt(i * 2)) << 4);
			b[i] = (byte) (b[i] | indexInHexhars(s.charAt(i * 2 + 1)) & 0xF);
		}

		return b;
	}

	private int indexInHexhars(char b) {
		int i = 0;

		for (i = 0; i < this._hexhars.length; ++i) {
			if (this._hexhars[i] == (byte) b) {
				break;
			}
		}

		return i;
	}

}
