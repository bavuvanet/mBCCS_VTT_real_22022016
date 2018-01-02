package com.viettel.bss.viettelpos.v4.activity;

import android.widget.ArrayAdapter;

public interface CommonLoadMoreDialogAsyncTaskFactory<T> {

	public CommonLoadMoreDialogAsyncTask<T> createInstance(ArrayAdapter<T> adapter);
}
