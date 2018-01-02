package com.viettel.bss.viettelpos.v4.work.object;

import java.io.Serializable;

public class OjUri implements Serializable {
	private String url;
    private String name;
	public OjUri(String url, String name) {
		this.url = url;
		this.name = name;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrl() {
		return url;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
}
