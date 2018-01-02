package com.viettel.bss.viettelpos.v4.activity;

import android.os.Parcel;
import android.os.Parcelable;

public class Token implements Parcelable {

    private final String key;
    private final String value;

    public Token(String key, String value) {
        this.key = key;
        this.value = value;
    }

    private Token(Parcel in) {
    	key = in.readString();
    	value = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(key);
        out.writeString(value);
    }

    public static final Parcelable.Creator<Token> CREATOR
            = new Parcelable.Creator<Token>() {
        public Token createFromParcel(Parcel in) {
            return new Token(in);
        }

        public Token[] newArray(int size) {
            return new Token[size];
        }
    };

	private String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.format("{\"Token\":{\"key\":\"%s\", \"value\":\"%s\"}}", key, value);
	}

	@Override
	public boolean equals(Object o) {
        return o instanceof Token && this.key.equalsIgnoreCase(((Token) o).getKey());
    }
}
