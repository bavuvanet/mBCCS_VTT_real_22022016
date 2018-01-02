package com.viettel.bss.viettelpos.v4.sale.object;

import java.io.Serializable;

public class ChannelTypeObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long channelTypeId;
	private String name;
	public Long getChannelTypeId() {
		return channelTypeId;
	}
	public void setChannelTypeId(Long channelTypeId) {
		this.channelTypeId = channelTypeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
