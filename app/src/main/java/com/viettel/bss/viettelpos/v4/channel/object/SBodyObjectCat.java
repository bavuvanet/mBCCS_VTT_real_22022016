package com.viettel.bss.viettelpos.v4.channel.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
@Namespace(prefix = "S", reference = "http://schemas.xmlsoap.org/soap/envelope/")
public class SBodyObjectCat {
	@Element(name = "viewManageAssetHistoryResponse")
	private ViewManageAssetHistoryResponse viewManager;
	
	public ViewManageAssetHistoryResponse getViewManager() {
		return viewManager;
	}

	public void setViewManager(ViewManageAssetHistoryResponse viewManager) {
		this.viewManager = viewManager;
	}
	
}
