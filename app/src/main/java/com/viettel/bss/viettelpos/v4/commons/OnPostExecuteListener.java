package com.viettel.bss.viettelpos.v4.commons;


public interface OnPostExecuteListener<T> {

	void onPostExecute(T result, String errorCode, String description);
}
