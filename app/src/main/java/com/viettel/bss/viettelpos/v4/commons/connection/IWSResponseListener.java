package com.viettel.bss.viettelpos.v4.commons.connection;

import com.viettel.bss.viettelpos.v4.commons.CommonOutput;

public interface IWSResponseListener<T> {
	T handlerResponse(CommonOutput data);
}
