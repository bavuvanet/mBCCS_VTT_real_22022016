package com.viettel.bss.viettelpos.v4.channel.object;

import java.io.Serializable;

public class ChannelTypeOJ implements Serializable {
	private Long channel_type_id;
	private String channel_type_name;
	public  ChannelTypeOJ(Long channel_type_id, String channel_type_name){
		this.channel_type_id = channel_type_id;
		this.channel_type_name = channel_type_name;
	}
	public Long getType_channel_id() {
		return channel_type_id;
	}
	public void setType_channel_id(Long type_channel_id) {
		this.channel_type_id = type_channel_id;
	}
	public String getType_channel_name() {
		return channel_type_name;
	}
	public void setType_channel_name(String type_channel_name) {
		this.channel_type_name = type_channel_name;
	}


}
