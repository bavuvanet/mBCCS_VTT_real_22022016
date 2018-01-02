package com.viettel.bss.viettelpos.v4.commons;

import java.io.ByteArrayOutputStream;

class Hex {
	private byte[] m_value = null;
	public static final String ERROR_ODD_NUMBER_OF_DIGITS = "Odd number of digits in hex string";
	public static final String ERROR_BAD_CHARACTER_IN_HEX_STRING = "Bad character or insufficient number of characters in hex string";
	private static final int[] DEC = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -1, -1, -1, -1,
			-1, -1, 10, 11, 12, 13, 14, 15, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			10, 11, 12, 13, 14, 15, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1 };

	public Hex() {
	}

	public Hex(String string) {
		this.m_value = decode(string);
	}

	public byte[] getBytes() {
		if (this.m_value == null) {
			return new byte[0];
		}
		byte[] bytes = new byte[this.m_value.length];
		System.arraycopy(this.m_value, 0, bytes, 0, this.m_value.length);
		return bytes;
	}

	public String toString() {
		return encode(this.m_value);
	}

	public boolean equals(Object object) {
		String s1 = object.toString();
		String s2 = toString();
		return s1.equals(s2);
	}

	private static byte[] decode(String digits) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		for (int i = 0; i < digits.length(); i += 2) {
			char c1 = digits.charAt(i);
			if (i + 1 >= digits.length()) {
				throw new IllegalArgumentException(
						"Odd number of digits in hex string");
			}
			char c2 = digits.charAt(i + 1);
			byte b = 0;
			if ((c1 >= '0') && (c1 <= '9')) {
				b = (byte) (b + (c1 - '0') * 16);
			} else if ((c1 >= 'a') && (c1 <= 'f')) {
				b = (byte) (b + (c1 - 'a' + 10) * 16);
			} else if ((c1 >= 'A') && (c1 <= 'F')) {
				b = (byte) (b + (c1 - 'A' + 10) * 16);
			} else {
				throw new IllegalArgumentException(
						"Bad character or insufficient number of characters in hex string");
			}
			if ((c2 >= '0') && (c2 <= '9')) {
				b = (byte) (b + c2 - '0');
			} else if ((c2 >= 'a') && (c2 <= 'f')) {
				b = (byte) (b + c2 - 'a' + 10);
			} else if ((c2 >= 'A') && (c2 <= 'F')) {
				b = (byte) (b + c2 - 'A' + 10);
			} else {
				throw new IllegalArgumentException(
						"Bad character or insufficient number of characters in hex string");
			}
			baos.write(b);
		}
		return baos.toByteArray();
	}

	public static String encode(byte[] bytes) {
		StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte aByte : bytes) {
            sb.append(convertDigit(aByte >> 4));
            sb.append(convertDigit(aByte & 0xF));
        }
		return sb.toString();
	}

	public static int convert2Int(byte[] hex) {
		if (hex.length < 4) {
			return 0;
		}
		if (DEC[hex[0]] < 0) {
			throw new IllegalArgumentException(
					"Bad character or insufficient number of characters in hex string");
		}
		int len = DEC[hex[0]];
		len <<= 4;
		if (DEC[hex[1]] < 0) {
			throw new IllegalArgumentException(
					"Bad character or insufficient number of characters in hex string");
		}
		len += DEC[hex[1]];
		len <<= 4;
		if (DEC[hex[2]] < 0) {
			throw new IllegalArgumentException(
					"Bad character or insufficient number of characters in hex string");
		}
		len += DEC[hex[2]];
		len <<= 4;
		if (DEC[hex[3]] < 0) {
			throw new IllegalArgumentException(
					"Bad character or insufficient number of characters in hex string");
		}
		len += DEC[hex[3]];
		return len;
	}

	private static char convertDigit(int value) {
		value &= 15;
		if (value >= 10) {
			return (char) (value - 10 + 97);
		}
		return (char) (value + 48);
	}
}
